package com.gao.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

//import org.apache.commons.lang.xwork.StringUtils;



import com.gao.model.data.DataDictionary;
import com.gao.model.data.DataitemsInfo;

/**
 * <p>
 * 描述：数据字典数据里工具类</>
 * 
 * @author gao 2014-06-10
 * @version 1.0
 * 
 */
public class DataDicUtils {
	private static StringBuffer allBuffer = null;
	static {
		allBuffer = new StringBuffer();
	}

	public static void test() {
		String str = "test=ss=qq";
		StringTokenizer st = new StringTokenizer(str, "=", false);

		while (st.hasMoreElements()) {
			String o = (String) st.nextElement();
//			System.out.println(o + "$$" + st.countTokens());

		}

	}

	public static void main(String[] args) {
		test();
	}

	/**
	 * <p>
	 * 描述：清空StringBuffer缓冲区信息
	 * </p>
	 */
	public static void emptyBuffer() {
		allBuffer.delete(0, allBuffer.length());
	}

	/**
	 * <p>
	 * 描述：递归的拼接表头子节点的数据信息
	 * </p>
	 * 
	 * @param key
	 *            当前节点
	 * @param map
	 *            该节点对应的子节点数据信息
	 * @return String 拼接好后的表头数据信息
	 */
	public static String createChildHeader(String key, Map<String, List<DataDictionary>> map) {
		List<DataDictionary> dataList = null;
		dataList = map.get(key);// 取出第二层子节点
		// dataList=removeDuplicate(dataList);
		for (DataDictionary val : dataList) {
			String columnString = val.getColunn().trim();
			if (map.containsKey(columnString)) { // 判断第二层的子节点是否还存在子节点
				allBuffer.append(" { header: \"" + val.getHead() + " \",dataIndex:\""+val.getColunn().trim()+"\",children:[");
				createChildHeader(columnString, map);// 递归调用获取子节点信息
				allBuffer.append("] },");
			} else {
				// 不存在子节点
				allBuffer.append(" { header: \"" + val.getHead() + " \", dataIndex:\"" + columnString.trim() +"\"");
				if(StringManagerUtils.isNotNull(val.getDataValue())&&!"null".equals(val.getDataValue())){
				allBuffer.append(","+val.getDataValue());
				}
				allBuffer.append( ",children:[] },");
			}

		}
		allBuffer.deleteCharAt(allBuffer.length() - 1);
		return allBuffer.toString();

	}

	/**
	 * <p>
	 * 描述：将数据字典中含有多表头的数据信息封装成map对象
	 * </p>
	 * 
	 * @author gao 2014-06-10
	 * @param data
	 *            数据字典集合信息
	 * @return Map<String, Set<DataDictionary>> map 数据对象
	 */
	public static Map<String, List<DataDictionary>> initData(List<DataitemsInfo> data) {
		Map<String, List<DataDictionary>> map = new HashMap<String, List<DataDictionary>>();
		List<DataDictionary> set = null;
		DataDictionary ddic = null;
		for (DataitemsInfo str : data) {
			String strArr[] = str.getEname().split("_");
			String rootVal = strArr[0];
			if (strArr.length > 1 && strArr[1].equals("root")) {
				for (DataitemsInfo d : data) {
					String sdataArr[] = d.getEname().split("#");
					if (sdataArr[0].equals(rootVal)) {
						String colString = sdataArr[sdataArr.length - 1];
						if (colString.indexOf(" as ") > 0) {
							colString = colString.substring(colString.indexOf(" as ") + 3);
						}
						String secondValString = sdataArr[sdataArr.length - 2];
						if (map.containsKey(secondValString)) {
							set = map.get(secondValString);
							ddic = new DataDictionary();
							ddic.setColunn(colString);
							ddic.setHead(d.getCname());
							//if(StringUtils.isNotBlank(d.getDatavalue())&&!d.getDatavalue().equals("null")){
							if(StringManagerUtils.isNotNull(d.getDatavalue())&&!"null".equals(d.getDatavalue())){
							ddic.setDataValue(d.getDatavalue());
							}
							set.add(ddic);
							map.remove(secondValString);
							map.put(secondValString, set);

						} else {
							ddic = new DataDictionary();
							set = new ArrayList<DataDictionary>();
							ddic.setColunn(colString);
							ddic.setHead(d.getCname());
							//if(StringUtils.isNotBlank(d.getDatavalue())&&!d.getDatavalue().equals("null")){
							if(StringManagerUtils.isNotNull(d.getDatavalue())&&!"null".equals(d.getDatavalue())){
								ddic.setDataValue(d.getDatavalue());
								}
							set.add(ddic);
							map.put(secondValString, set);
						}
					}
				}
			}
		}
		return map;
	}

	// 去除set中重复数据的方法
	@SuppressWarnings("unused")
	private static Set<DataDictionary> removeDuplicate(Set<DataDictionary> set) {
		Map<String, DataDictionary> map = new HashMap<String, DataDictionary>();
		Set<DataDictionary> tempSet = new HashSet<DataDictionary>();
		for (DataDictionary p : set) {
			if (map.get(p.getColunn()) == null) {
				map.put(p.getColunn(), p);
			} else {
				tempSet.add(p);
			}
		}
		set.removeAll(tempSet);
		return set;
	}

	@SuppressWarnings("unused")
	public static int getMaxValue(String arr[]) {
		int len = 0;
		if (arr.length > 0) {
			int index = 0;
			len = arr[0].split("#").length;
			for (int i = 0; i < arr.length; i++) {
				int arLen = arr[i].split("#").length;
				if (arLen > len) {
					len = arLen;
					index = i;
				}
			}
//			System.out.println("数组中最大值是" + len);
		}
		return len;
	}
}
