package com.cosog.model.drive;

import com.google.gson.JsonDeserializer;  // 注意是这个接口
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.JsonDeserializationContext;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AcqGroupDeserializer implements JsonDeserializer<AcqGroup> {
    
    @Override
    public AcqGroup deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        AcqGroup acqGroup = new AcqGroup();
        
        // 基本字段
        acqGroup.setID(getStringSafe(jsonObject, "ID"));
        acqGroup.setSlave(getByteSafe(jsonObject, "Slave"));
        acqGroup.setGroupSN(getIntSafe(jsonObject, "GroupSN"));
        acqGroup.setRawData(getStringSafe(jsonObject, "RawData"));
        
        // Addr
        acqGroup.setAddr(parseAddr(jsonObject));
        
        // Value - 安全解析，处理null
        acqGroup.setValue(parseValueSafe(jsonObject));
        
        return acqGroup;
    }
    
    /**
     * 安全解析Value，处理各种null情况
     */
    private List<List<Object>> parseValueSafe(JsonObject jsonObject) {
        List<List<Object>> result = new ArrayList<>();
        
        JsonElement valueElement = jsonObject.get("Value");
        if (valueElement == null || valueElement.isJsonNull()) {
            return result;
        }
        
        try {
            // 获取原始JSON字符串用于精确解析
            String valueJson = valueElement.toString();
            JsonReader reader = new JsonReader(new StringReader(valueJson));
            return parseOuterArraySafe(reader);
        } catch (Exception e) {
            e.printStackTrace();
            // 降级方案
            return parseValueFallback(valueElement);
        }
    }
    
    /**
     * 解析外层数组，安全处理null
     */
    private List<List<Object>> parseOuterArraySafe(JsonReader reader) throws Exception {
        List<List<Object>> outerList = new ArrayList<>();
        
        reader.beginArray();
        while (reader.hasNext()) {
            JsonToken token = reader.peek();
            
            if (token == JsonToken.NULL) {
                // 外层数组元素为null
                reader.nextNull();
                outerList.add(null);
                continue;
            }
            
            if (token == JsonToken.BEGIN_ARRAY) {
                // 解析内层数组
                List<Object> innerList = parseInnerArraySafe(reader);
                outerList.add(innerList);
            } else {
                // 如果遇到非数组元素，跳过
                reader.skipValue();
                outerList.add(null);
            }
        }
        reader.endArray();
        
        return outerList;
    }
    
    /**
     * 解析内层数组，安全处理null
     */
    private List<Object> parseInnerArraySafe(JsonReader reader) throws Exception {
        List<Object> innerList = new ArrayList<>();
        
        reader.beginArray();
        while (reader.hasNext()) {
            JsonToken token = reader.peek();
            
            switch (token) {
                case NULL:
                    reader.nextNull();
                    innerList.add(null);
                    break;
                    
                case BEGIN_ARRAY:
                    // 嵌套数组，递归解析
                    innerList.add(parseInnerArraySafe(reader));
                    break;
                    
                case BEGIN_OBJECT:
                    innerList.add(parseObjectSafe(reader));
                    break;
                    
                case STRING:
                    innerList.add(reader.nextString());
                    break;
                    
                case BOOLEAN:
                    innerList.add(reader.nextBoolean());
                    break;
                    
                case NUMBER:
                    // 关键：使用nextString()获取原始数字字符串
                    String numStr = reader.nextString();
                    innerList.add(parseNumber(numStr));
                    break;
                    
                default:
                    reader.skipValue();
                    innerList.add(null);
                    break;
            }
        }
        reader.endArray();
        
        return innerList;
    }
    
    /**
     * 解析对象
     */
    private Map<String, Object> parseObjectSafe(JsonReader reader) throws Exception {
        Map<String, Object> map = new LinkedHashMap<>();
        reader.beginObject();
        
        while (reader.hasNext()) {
            String key = reader.nextName();
            JsonToken token = reader.peek();
            
            switch (token) {
                case NULL:
                    reader.nextNull();
                    map.put(key, null);
                    break;
                case BEGIN_ARRAY:
                    map.put(key, parseInnerArraySafe(reader));
                    break;
                case BEGIN_OBJECT:
                    map.put(key, parseObjectSafe(reader));
                    break;
                case STRING:
                    map.put(key, reader.nextString());
                    break;
                case BOOLEAN:
                    map.put(key, reader.nextBoolean());
                    break;
                case NUMBER:
                    String numStr = reader.nextString();
                    map.put(key, parseNumber(numStr));
                    break;
                default:
                    reader.skipValue();
                    map.put(key, null);
                    break;
            }
        }
        reader.endObject();
        return map;
    }
    
    /**
     * 解析数字，自动判断类型
     */
    private Object parseNumber(String numStr) {
        if (numStr == null || numStr.isEmpty()) {
            return null;
        }
        
        try {
            // 判断是否为整数
            if (!numStr.contains(".") && !numStr.toLowerCase().contains("e")) {
                try {
                    long longVal = Long.parseLong(numStr);
                    // 如果在int范围内，返回int
                    if (longVal >= Integer.MIN_VALUE && longVal <= Integer.MAX_VALUE) {
                        return (int) longVal;
                    }
                    return longVal;
                } catch (NumberFormatException e) {
                    // 可能超出long范围，使用BigInteger
                    return new BigDecimal(numStr);
                }
            } else {
                // 浮点数，使用BigDecimal保持精度
                return new BigDecimal(numStr);
            }
        } catch (NumberFormatException e) {
            // 如果解析失败，返回字符串
            return numStr;
        }
    }
    
    /**
     * 降级方案：使用JsonElement解析
     */
    private List<List<Object>> parseValueFallback(JsonElement valueElement) {
        List<List<Object>> result = new ArrayList<>();
        
        if (valueElement == null || valueElement.isJsonNull()) {
            return result;
        }
        
        try {
            JsonArray outerArray = valueElement.getAsJsonArray();
            for (JsonElement outerElem : outerArray) {
                if (outerElem == null || outerElem.isJsonNull()) {
                    result.add(null);
                    continue;
                }
                
                List<Object> innerList = new ArrayList<>();
                JsonArray innerArray = outerElem.getAsJsonArray();
                for (JsonElement innerElem : innerArray) {
                    if (innerElem == null || innerElem.isJsonNull()) {
                        innerList.add(null);
                    } else if (innerElem.isJsonPrimitive()) {
                        JsonPrimitive primitive = innerElem.getAsJsonPrimitive();
                        if (primitive.isBoolean()) {
                            innerList.add(primitive.getAsBoolean());
                        } else if (primitive.isString()) {
                            innerList.add(primitive.getAsString());
                        } else if (primitive.isNumber()) {
                            // 使用BigDecimal
                            innerList.add(new BigDecimal(primitive.getAsString()));
                        }
                    } else {
                        innerList.add(innerElem);
                    }
                }
                result.add(innerList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    // ========== 辅助方法 ==========
    
    private String getStringSafe(JsonObject obj, String key) {
        JsonElement element = obj.get(key);
        return (element == null || element.isJsonNull()) ? "" : element.getAsString();
    }
    
    private byte getByteSafe(JsonObject obj, String key) {
        JsonElement element = obj.get(key);
        return (element == null || element.isJsonNull()) ? 0 : element.getAsByte();
    }
    
    private int getIntSafe(JsonObject obj, String key) {
        JsonElement element = obj.get(key);
        return (element == null || element.isJsonNull()) ? 0 : element.getAsInt();
    }
    
    private List<Integer> parseAddr(JsonObject jsonObject) {
        List<Integer> addrList = new ArrayList<>();
        JsonElement addrElement = jsonObject.get("Addr");
        if (addrElement == null || addrElement.isJsonNull()) {
            return addrList;
        }
        
        try {
            JsonArray addrArray = addrElement.getAsJsonArray();
            for (JsonElement elem : addrArray) {
                if (elem != null && !elem.isJsonNull()) {
                    addrList.add(elem.getAsInt());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addrList;
    }
}
