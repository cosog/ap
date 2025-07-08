package com.cosog.task;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cosog.model.AccessToken;
import com.cosog.model.AlarmShowStyle;
import com.cosog.model.AuxiliaryDeviceAddInfo;
import com.cosog.model.Code;
import com.cosog.model.DataMapping;
import com.cosog.model.DataSourceConfig;
import com.cosog.model.DataWriteBackConfig;
import com.cosog.model.KeyValue;
import com.cosog.model.ProtocolRunStatusConfig;
import com.cosog.model.RealtimeTotalInfo;
import com.cosog.model.ReportTemplate;
import com.cosog.model.VideoKey;
import com.cosog.model.WorkType;
import com.cosog.model.calculate.AcqInstanceOwnItem;
import com.cosog.model.calculate.AcqInstanceOwnItem.AcqItem;
import com.cosog.model.calculate.AlarmInstanceOwnItem;
import com.cosog.model.calculate.AlarmInstanceOwnItem.AlarmItem;
import com.cosog.model.calculate.CalculateColumnInfo;
import com.cosog.model.calculate.CalculateColumnInfo.CalculateColumn;
import com.cosog.model.calculate.DeviceInfo;
import com.cosog.model.calculate.DisplayInstanceOwnItem;
import com.cosog.model.calculate.DisplayInstanceOwnItem.DisplayItem;
import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.PCPCalculateResponseData;
import com.cosog.model.drive.AcquisitionItemInfo;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.PCPDeviceTodayData;
import com.cosog.model.calculate.SRPCalculateRequestData;
import com.cosog.model.calculate.SRPCalculateResponseData;
import com.cosog.model.calculate.SRPDeviceInfo;
import com.cosog.model.calculate.SRPDeviceTodayData;
import com.cosog.model.calculate.UserInfo;
import com.cosog.utils.Config;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.RedisUtil;
import com.cosog.utils.SerializeObjectUnils;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.excel.ExcelUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import redis.clients.jedis.Jedis;

@Component("LoadingMemoryData")  
public class MemoryDataManagerTask {

	private static MemoryDataManagerTask instance=new MemoryDataManagerTask();
	
	private static List<String> languageList=null;
	
	public static MemoryDataManagerTask getInstance(){
		return instance;
	}
	
//	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public static void loadMemoryData(){
		MemoryDataManagerTask.loadLanguageResource();
		
		cleanData();
		
		loadProtocolConfig("");
		System.out.println("加载协议完成");
		
		loadProtocolRunStatusConfig();
		System.out.println("加载协议运行状态配置完成");
		
		loadReportTemplateConfig();
		System.out.println("加载报表模板完成");
		
		loadAcqInstanceOwnItemById("","update");
		System.out.println("加载采控实例信息完成");
		loadAlarmInstanceOwnItemById("","update");
		System.out.println("加载报警实例信息完成");
		loadDisplayInstanceOwnItemById("","update");
		System.out.println("加载显示实例信息完成");
		
		loadDeviceInfo(null,0,"update");
		System.out.println("加载设备信息完成");
		
		MemoryDataManagerTask.loadDeviceRealtimeTotalData(null,0);
		System.out.println("加载实时汇总数据完成");
		
		loadTodayFESDiagram(null,0);
		System.out.println("加载设备当天功图数据完成");
		loadTodayRPMData(null,0);
		System.out.println("加载设备当天转速数据完成");
	}
	
	public static String getMemoryUsage(String key){
		long size=0;
		Jedis jedis=null;
		String r="";
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis.exists(key.getBytes())){
				size=jedis.memoryUsage(key.getBytes());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		r=StringManagerUtils.byteToHumanStr(size);
		return r;
	}
	
	public static boolean getJedisStatus(){
		boolean r=false;
		Jedis jedis=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			r=true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return r;
	}
	
	public static String getJedisVersion(){
		String redisVersion="";
		Jedis jedis=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			String info = jedis.info();
			String[] infoArr=info.replaceAll("\r\n", "\n").split("\n");
			for(int i=0;i<infoArr.length;i++){
				if(infoArr[i].startsWith("redis_version:")){
					String[] versionArr=infoArr[i].split(":");
					if(versionArr.length==2){
						redisVersion=versionArr[1];
					}
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return redisVersion;
	}
	
	public static void redisInit(){
		Jedis jedis=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			jedis.configSet("maxmemory", "2048MB");
			jedis.configSet("maxmemory-policy", "allkeys-random");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static RedisInfo getJedisInfo(){
		RedisInfo redisInfo= new RedisInfo();
		redisInfo.setStatus(0);
		redisInfo.setVersion("");
		
		String redisVersion="";
		Jedis jedis=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			redisInfo.setStatus(1);
			String info = jedis.info();
			String[] infoArr=info.replaceAll("\r\n", "\n").split("\n");
			for(int i=0;i<infoArr.length;i++){
				if(infoArr[i].startsWith("redis_version:")){
					String[] versionArr=infoArr[i].split(":");
					if(versionArr.length==2){
						redisVersion=versionArr[1];
						redisInfo.setVersion(redisVersion);
					}
				}else if(infoArr[i].startsWith("os:")){
					String[] arr=infoArr[i].split(":");
					if(arr.length==2){
						redisInfo.setOs(arr[1]);
					}
				}else if(infoArr[i].startsWith("arch_bits:")){
					String[] arr=infoArr[i].split(":");
					if(arr.length==2){
						redisInfo.setArch_bits(StringManagerUtils.stringToInteger(arr[1]));
					}
				}else if(infoArr[i].startsWith("tcp_port:")){
					String[] arr=infoArr[i].split(":");
					if(arr.length==2){
						redisInfo.setTcp_port(StringManagerUtils.stringToInteger(arr[1]));
					}
				}else if(infoArr[i].startsWith("used_memory:")){
					String[] arr=infoArr[i].split(":");
					if(arr.length==2){
						redisInfo.setUsed_memory(StringManagerUtils.stringToLong(arr[1]));
					}
				}else if(infoArr[i].startsWith("used_memory_human:")){
					String[] arr=infoArr[i].split(":");
					if(arr.length==2){
						redisInfo.setUsed_memory_human(arr[1]);
					}
				}else if(infoArr[i].startsWith("maxmemory:")){
					String[] arr=infoArr[i].split(":");
					if(arr.length==2){
						redisInfo.setMaxmemory(StringManagerUtils.stringToLong(arr[1]));
					}
				}else if(infoArr[i].startsWith("maxmemory_human:")){
					String[] arr=infoArr[i].split(":");
					if(arr.length==2){
						redisInfo.setMaxmemory_human(arr[1]);
					}
				}else if(infoArr[i].startsWith("total_system_memory:")){
					String[] arr=infoArr[i].split(":");
					if(arr.length==2){
						redisInfo.setTotal_system_memory(StringManagerUtils.stringToLong(arr[1]));
					}
				}else if(infoArr[i].startsWith("total_system_memory_human:")){
					String[] arr=infoArr[i].split(":");
					if(arr.length==2){
						redisInfo.setTotal_system_memory_human(arr[1]);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return redisInfo;
	}
	
	public static void cleanData(String key){
		Jedis jedis=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis.exists(key.getBytes())){
				jedis.del(key.getBytes());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static void cleanData(){
		cleanData("modbusProtocolConfig");
		cleanData("ProtocolMappingColumn");
		cleanData("ProtocolMappingColumnByTitle");
		cleanData("CalculateColumnInfo");
		cleanData("ProtocolRunStatusConfig");
		
		cleanData("AcqInstanceOwnItem");
		cleanData("DisplayInstanceOwnItem");
		cleanData("AlarmInstanceOwnItem");
		
		cleanData("acqCalItemList-en");
		cleanData("acqCalItemList-ru");
		cleanData("acqCalItemList-zh_CN");
		
		cleanData("srpCalItemList-en");
		cleanData("srpCalItemList-ru");
		cleanData("srpCalItemList-zh_CN");
		
		cleanData("pcpCalItemList-en");
		cleanData("pcpCalItemList-ru");
		cleanData("pcpCalItemList-zh_CN");
		
		cleanData("acqTotalCalItemList-en");
		cleanData("acqTotalCalItemList-ru");
		cleanData("acqTotalCalItemList-zh_CN");
		
		cleanData("srpTotalCalItemList-en");
		cleanData("srpTotalCalItemList-ru");
		cleanData("srpTotalCalItemList-zh_CN");
		
		cleanData("pcpTotalCalItemList-en");
		cleanData("pcpTotalCalItemList-ru");
		cleanData("pcpTotalCalItemList-zh_CN");
		
		cleanData("acqTimingTotalCalItemList-en");
		cleanData("acqTimingTotalCalItemList-ru");
		cleanData("acqTimingTotalCalItemList-zh_CN");
		
		cleanData("srpTimingTotalCalItemList-en");
		cleanData("srpTimingTotalCalItemList-ru");
		cleanData("srpTimingTotalCalItemList-zh_CN");
		
		cleanData("pcpTimingTotalCalItemList-en");
		cleanData("pcpTimingTotalCalItemList-ru");
		cleanData("pcpTimingTotalCalItemList-zh_CN");
		
		cleanData("srpInputItemList-en");
		cleanData("srpInputItemList-ru");
		cleanData("srpInputItemList-zh_CN");
		
		cleanData("pcpInputItemList-en");
		cleanData("pcpInputItemList-ru");
		cleanData("pcpInputItemList-zh_CN");
		
		cleanData("UserInfo");
		cleanData("SRPWorkType");
		cleanData("SRPWorkTypeByName");
		cleanData("AlarmShowStyle");
		cleanData("UIKitAccessToken");
		cleanData("ReportTemplateConfig");
		
		
		List<DeviceInfo> deviceInfoList = MemoryDataManagerTask.getDeviceInfo();
		if(deviceInfoList!=null){
			for(DeviceInfo deviceInfo:deviceInfoList){
				cleanData("DeviceRealtimeAcqData_"+deviceInfo.getId());
			}
			
		}
		
		cleanData("DeviceRealtimeTotalData");
		cleanData("SRPDeviceTodayData");
		cleanData("PCPDeviceTodayData");
		cleanData("DeviceInfo");
	}
	
	public static boolean existsKey(String key){
		boolean r=false;
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			r=jedis.exists(key.getBytes());
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return r;
	}
	
	public static void updateProtocolConfig(ModbusProtocolConfig modbusProtocolConfig){
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			jedis.set("modbusProtocolConfig".getBytes(), SerializeObjectUnils.serialize(modbusProtocolConfig));
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public static void loadProtocolConfig(String protocolName){
		StringManagerUtils.printLog("驱动加载开始");
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		Gson gson = new Gson();
		String path="";
		String protocolConfigData="";
		java.lang.reflect.Type type=null;
		//添加Modbus协议配置
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfigWithoutReLoad();
		
		if(modbusProtocolConfig==null){
			modbusProtocolConfig=new ModbusProtocolConfig();
			modbusProtocolConfig.setProtocol(new ArrayList<ModbusProtocolConfig.Protocol>());
		}else if(modbusProtocolConfig!=null&&modbusProtocolConfig.getProtocol()==null){
			modbusProtocolConfig.setProtocol(new ArrayList<ModbusProtocolConfig.Protocol>());
		}
		try {
			StringBuffer protocolBuff=null;
			String sql="select t.id,t.name,t.code,t.items,t.sort,t.devicetype,t.language,t.extendedField "
					+ " from TBL_PROTOCOL t "
					+ " where 1=1 ";
			if(StringManagerUtils.isNotNull(protocolName)){
				sql+=" and t.name='"+protocolName+"'";
			}
			sql+= "order by t.sort,t.id";
			List<Object[]> list=OracleJdbcUtis.query(sql);
			for(Object[] obj:list){
				try {
					String itemsStr=obj[3]+"";
					String extendedFieldStr=obj[7]+"";
					if(!StringManagerUtils.isNotNull(itemsStr)){
						itemsStr="[]";
					}
					if(!StringManagerUtils.isNotNull(extendedFieldStr)){
						extendedFieldStr="[]";
					}
					protocolBuff=new StringBuffer();
					protocolBuff.append("{");
					protocolBuff.append("\"Id\":\""+StringManagerUtils.stringToInteger(obj[0]+"")+"\",");
					protocolBuff.append("\"Name\":\""+obj[1]+"\",");
					protocolBuff.append("\"Code\":\""+obj[2]+"\",");
					protocolBuff.append("\"Sort\":"+StringManagerUtils.stringToInteger(obj[4]+"")+",");
					protocolBuff.append("\"DeviceType\":"+StringManagerUtils.stringToInteger(obj[5]+"")+",");
					protocolBuff.append("\"Language\":"+StringManagerUtils.stringToInteger(obj[6]+"")+",");
					protocolBuff.append("\"Items\":"+itemsStr+",");
					protocolBuff.append("\"ExtendedFields\":"+extendedFieldStr+"");
					protocolBuff.append("}");
					
					type = new TypeToken<ModbusProtocolConfig.Protocol>() {}.getType();
					ModbusProtocolConfig.Protocol protocol=gson.fromJson(protocolBuff.toString(), type);
					
					if(protocol!=null){
						if(protocol.getItems()==null){
							protocol.setItems(new ArrayList<>());
						}else{
							Collections.sort(protocol.getItems());
						}
						boolean isExist=false;
						for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
							if(protocol.getCode().equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getCode())){
								isExist=true;
								modbusProtocolConfig.getProtocol().set(i, protocol);
								break;
							}
						}
						if(!isExist){
							modbusProtocolConfig.getProtocol().add(protocol);
						}
					}
				}catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(modbusProtocolConfig!=null&&modbusProtocolConfig.getProtocol()!=null&&modbusProtocolConfig.getProtocol().size()>0){
			Collections.sort(modbusProtocolConfig.getProtocol());
		}
		
		updateProtocolConfig(modbusProtocolConfig);
		
		StringManagerUtils.printLog("驱动加载结束");
	}
	
	public static void loadAcquisitionItemNameList(){
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		List<String> acquisitionItemNameList=new ArrayList<>();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		if(modbusProtocolConfig!=null){
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				Collections.sort(modbusProtocolConfig.getProtocol().get(i).getItems());
				for(int j=0;j<modbusProtocolConfig.getProtocol().get(i).getItems().size();j++){
					if(!StringManagerUtils.existOrNot(acquisitionItemNameList, modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle(),false)){
						acquisitionItemNameList.add(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle());
					}
				}
			}
		}
		dataModelMap.put("acquisitionItemNameList", acquisitionItemNameList);
	}
	
	public static void loadProtocolExtendedFieldNameList(){
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		List<String> protocolExtendedFieldNameList=new ArrayList<>();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		if(modbusProtocolConfig!=null){
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				for(int j=0;j<modbusProtocolConfig.getProtocol().get(i).getExtendedFields().size();j++){
					if(!StringManagerUtils.existOrNot(protocolExtendedFieldNameList, modbusProtocolConfig.getProtocol().get(i).getExtendedFields().get(j).getTitle(),false)){
						protocolExtendedFieldNameList.add(modbusProtocolConfig.getProtocol().get(i).getExtendedFields().get(j).getTitle());
					}
				}
			}
		}
		dataModelMap.put("protocolExtendedFieldNameList", protocolExtendedFieldNameList);
	}
	
	public static List<String> getAcquisitionItemNameList(){
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		List<String> acquisitionItemNameList=new ArrayList<>();
		if(!dataModelMap.containsKey("acquisitionItemNameList") || dataModelMap.get("acquisitionItemNameList")==null){
			loadAcquisitionItemNameList();
		}
		acquisitionItemNameList=(List<String>) dataModelMap.get("acquisitionItemNameList");
		return acquisitionItemNameList;
	}
	
	public static List<String> getProtocolExtendedFieldNameList(){
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		List<String> protocolExtendedFieldNameList=new ArrayList<>();
		if(!dataModelMap.containsKey("protocolExtendedFieldNameList") || dataModelMap.get("protocolExtendedFieldNameList")==null){
			loadProtocolExtendedFieldNameList();
		}
		protocolExtendedFieldNameList=(List<String>) dataModelMap.get("protocolExtendedFieldNameList");
		return protocolExtendedFieldNameList;
	}
	
	public static DataMapping getDataMappingByCalColumn(String calColumn){
		DataMapping dataMapping=null;
		try{
			Map<String,DataMapping> loadProtocolMappingColumnMap=getProtocolMappingColumn();
			for (String key : loadProtocolMappingColumnMap.keySet()) {
				if(calColumn.equalsIgnoreCase(loadProtocolMappingColumnMap.get(key).getCalColumn())){
					dataMapping=loadProtocolMappingColumnMap.get(key);
					break;
				}
	        }
		}catch(Exception e){
			dataMapping=null;
		}
		return dataMapping;
	}
	
	public static Map<String,DataMapping> getProtocolMappingColumn(){
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		if(!dataModelMap.containsKey("ProtocolMappingColumn")){
			MemoryDataManagerTask.loadProtocolMappingColumn();
		}
		Map<String,DataMapping> loadProtocolMappingColumnMap=null;
		if(dataModelMap.containsKey("ProtocolMappingColumn")){
			loadProtocolMappingColumnMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumn");
		}	
				
		return loadProtocolMappingColumnMap;
	}
	
	public static void loadProtocolMappingColumn(){
		
		Jedis jedis=null;
		try {
			Map<String, Object> dataModelMap=DataModelMap.getMapObject();
			Map<String,DataMapping> loadProtocolMappingColumnMap=new LinkedHashMap<String,DataMapping>();
			
			String sql="select t.id,t.name,t.mappingcolumn,t.calcolumn,t.protocoltype,t.mappingmode,t.repetitiontimes,t.calculateEnable "
					+ "from TBL_DATAMAPPING t "
					+ "order by t.protocoltype,t.id";
			List<Object[]> list=OracleJdbcUtis.query(sql);
			for(Object[] obj:list){
				DataMapping dataMapping=new DataMapping();
				dataMapping.setId(StringManagerUtils.stringToInteger(obj[0]+""));
				dataMapping.setName(obj[1]+"");
				dataMapping.setMappingColumn(obj[2]+"");
				dataMapping.setCalColumn(obj[3]+"");
				dataMapping.setProtocolType(StringManagerUtils.stringToInteger(obj[4]+""));
				dataMapping.setMappingMode(StringManagerUtils.stringToInteger(obj[5]+""));
				dataMapping.setRepetitionTimes(StringManagerUtils.stringToInteger(obj[6]+""));
				dataMapping.setCalculateEnable(StringManagerUtils.stringToInteger(obj[7]+""));
				loadProtocolMappingColumnMap.put(dataMapping.getMappingColumn(), dataMapping);
			}
			dataModelMap.put("ProtocolMappingColumn", loadProtocolMappingColumnMap);
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis!=null){
				jedis.del("ProtocolMappingColumn".getBytes());
				for (Map.Entry<String, DataMapping> entry : loadProtocolMappingColumnMap.entrySet()) {
				    String key = entry.getKey();
				    DataMapping dataMapping = entry.getValue();
				    jedis.hset("ProtocolMappingColumn".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(dataMapping));//哈希(Hash)
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	
	public static Map<String,DataMapping> getProtocolMappingColumnByTitle(int type){
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		String rootKey="ProtocolMappingColumnByTitle";
		if(type==1){
			rootKey="ProtocolExtendedFieldMappingColumnByTitle";
		}
		if(!dataModelMap.containsKey(rootKey)){
			MemoryDataManagerTask.loadProtocolMappingColumnByTitle(type);
		}
		Map<String,DataMapping> map=null;
		if(dataModelMap.containsKey(rootKey)){
			map=(Map<String, DataMapping>) dataModelMap.get(rootKey);
		}	
				
		return map;
	}
	
	public static void loadProtocolMappingColumnByTitle(int type){//0-普通协议字段  1-拓展字段
		Jedis jedis=null;
		try {
			Map<String, Object> dataModelMap=DataModelMap.getMapObject();
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=new LinkedHashMap<String,DataMapping>();
			String rootKey="ProtocolMappingColumnByTitle";
			if(type==1){
				rootKey="ProtocolExtendedFieldMappingColumnByTitle";
			}
			
			String sql="select t.id,t.name,t.mappingcolumn,t.calcolumn,t.protocoltype,t.mappingmode,t.repetitiontimes,t.calculateEnable "
					+ "from TBL_DATAMAPPING t "
					+ "where 1=1";
			if(type==1){
				sql+=" and upper(t.mappingcolumn) like 'EXTENDEDFIELD_CLOUMN%'";
			}else{
				sql+=" and upper(t.mappingcolumn) like 'C_CLOUMN%'";
			}
			sql+= "order by t.protocoltype,t.id";
			List<Object[]> list=OracleJdbcUtis.query(sql);
			
			for(Object[] obj:list){
				DataMapping dataMapping=new DataMapping();
				dataMapping.setId(StringManagerUtils.stringToInteger(obj[0]+""));
				dataMapping.setName(obj[1]+"");
				dataMapping.setMappingColumn(obj[2]+"");
				dataMapping.setCalColumn(obj[3]+"");
				dataMapping.setProtocolType(StringManagerUtils.stringToInteger(obj[4]+""));
				dataMapping.setMappingMode(StringManagerUtils.stringToInteger(obj[5]+""));
				dataMapping.setRepetitionTimes(StringManagerUtils.stringToInteger(obj[6]+""));
				dataMapping.setCalculateEnable(StringManagerUtils.stringToInteger(obj[7]+""));
				loadProtocolMappingColumnByTitleMap.put(dataMapping.getName(), dataMapping);
			}
			dataModelMap.put(rootKey, loadProtocolMappingColumnByTitleMap);
			
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis!=null){
				for (Map.Entry<String, DataMapping> entry : loadProtocolMappingColumnByTitleMap.entrySet()) {
				    String key = entry.getKey();
				    DataMapping dataMapping = entry.getValue();
				    jedis.hset(rootKey.getBytes(), key.getBytes(), SerializeObjectUnils.serialize(dataMapping));//哈希(Hash)
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static ProtocolRunStatusConfig getProtocolRunStatusConfig(String key){
		ProtocolRunStatusConfig protocolRunStatusConfig=null;
		Jedis jedis=null;
		if(!existsKey("ProtocolRunStatusConfig")){
			MemoryDataManagerTask.loadProtocolRunStatusConfig();
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis.hget("ProtocolRunStatusConfig".getBytes(), key.getBytes())!=null){
				protocolRunStatusConfig=(ProtocolRunStatusConfig)SerializeObjectUnils.unserizlize(jedis.hget("ProtocolRunStatusConfig".getBytes(), key.getBytes()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return protocolRunStatusConfig;
	}
	
	public static void loadProtocolRunStatusConfig(){
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			jedis.del("ProtocolRunStatusConfig".getBytes());
			String sql="select t.id,t.protocol,t.itemname,t.itemmappingcolumn,"
					+ " t.resolutionmode,t.runvalue,t.stopvalue,t.runcondition,t.stopcondition,"
					+ " t.protocoltype "
					+ " from tbl_runstatusconfig t order by t.protocoltype,t.id";
			List<Object[]> list=OracleJdbcUtis.query(sql);
			for(Object[] obj:list){
				ProtocolRunStatusConfig protocolRunStatusConfig=new ProtocolRunStatusConfig();
				protocolRunStatusConfig.setId(StringManagerUtils.stringToInteger(obj[0]+""));
				protocolRunStatusConfig.setProtocol(obj[1]+"");
				protocolRunStatusConfig.setItemName(obj[2]+"");
				protocolRunStatusConfig.setItemMappingColumn(obj[3]+"");
				protocolRunStatusConfig.setResolutionMode(StringManagerUtils.stringToInteger(obj[4]+""));
				
				String runValueStr=obj[5]+"";
				String stopValueStr=obj[6]+"";
				
				String runConditionStr=obj[7]+"";
				String stopConditionStr=obj[8]+"";
				
				protocolRunStatusConfig.setProtocolType(StringManagerUtils.stringToInteger(obj[9]+""));
				protocolRunStatusConfig.setRunValue(new ArrayList<Integer>());
				protocolRunStatusConfig.setStopValue(new ArrayList<Integer>());
				
				protocolRunStatusConfig.setRunConditionList(new ArrayList<ProtocolRunStatusConfig.RunStatusCondition>());
				protocolRunStatusConfig.setStopConditionList(new ArrayList<ProtocolRunStatusConfig.RunStatusCondition>());
				
				if(StringManagerUtils.isNotNull(runValueStr)){
					String[] runValueArr=runValueStr.split(",");
					for(int i=0;i<runValueArr.length;i++){
						if(StringManagerUtils.isNum(runValueArr[i])){
							protocolRunStatusConfig.getRunValue().add(StringManagerUtils.stringToInteger(runValueArr[i]));
						}
					}
				}
				if(StringManagerUtils.isNotNull(stopValueStr)){
					String[] stopValueArr=stopValueStr.split(",");
					for(int i=0;i<stopValueArr.length;i++){
						if(StringManagerUtils.isNum(stopValueArr[i])){
							protocolRunStatusConfig.getStopValue().add(StringManagerUtils.stringToInteger(stopValueArr[i]));
						}
					}
				}
				
				if(StringManagerUtils.isNotNull(runConditionStr)){//>=,0;
					String[] runConditionStrArr=runConditionStr.split(";");
					for(int i=0;i<runConditionStrArr.length;i++){
						if(StringManagerUtils.isNotNull(runConditionStrArr[i])){
							String[] runConditionArr= runConditionStrArr[i].split(",");
							if(runConditionArr.length==2 && StringManagerUtils.isNumber(runConditionArr[1])){
								ProtocolRunStatusConfig.RunStatusCondition runCondition=new ProtocolRunStatusConfig.RunStatusCondition();
								if(">".equalsIgnoreCase(runConditionArr[0])){
									runCondition.setLogic(1);
								}else if(">=".equalsIgnoreCase(runConditionArr[0])){
									runCondition.setLogic(2);
								}else if("<=".equalsIgnoreCase(runConditionArr[0])){
									runCondition.setLogic(3);
								}else if("<".equalsIgnoreCase(runConditionArr[0])){
									runCondition.setLogic(4);
								}
								runCondition.setValue(StringManagerUtils.stringToFloat(runConditionArr[1]));
								protocolRunStatusConfig.getRunConditionList().add(runCondition);
							}
						}
					}
				}
				
				if(StringManagerUtils.isNotNull(stopConditionStr)){//>=,0;
					String[] stopConditionStrArr=stopConditionStr.split(";");
					for(int i=0;i<stopConditionStrArr.length;i++){
						if(StringManagerUtils.isNotNull(stopConditionStrArr[i])){
							String[] stopConditionArr= stopConditionStrArr[i].split(",");
							if(stopConditionArr.length==2 && StringManagerUtils.isNumber(stopConditionArr[1])){
								ProtocolRunStatusConfig.RunStatusCondition stopCondition=new ProtocolRunStatusConfig.RunStatusCondition();
								if(">".equalsIgnoreCase(stopConditionArr[0])){
									stopCondition.setLogic(1);
								}else if(">=".equalsIgnoreCase(stopConditionArr[0])){
									stopCondition.setLogic(2);
								}else if("<=".equalsIgnoreCase(stopConditionArr[0])){
									stopCondition.setLogic(3);
								}else if("<".equalsIgnoreCase(stopConditionArr[0])){
									stopCondition.setLogic(4);
								}
								stopCondition.setValue(StringManagerUtils.stringToFloat(stopConditionArr[1]));
								protocolRunStatusConfig.getStopConditionList().add(stopCondition);
							}
						}
					}
				}
				
				String key=(protocolRunStatusConfig.getProtocol()+"_"+protocolRunStatusConfig.getItemMappingColumn()).toUpperCase();
				jedis.hset("ProtocolRunStatusConfig".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(protocolRunStatusConfig));//哈希(Hash)
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static void updateDeviceInfo(DeviceInfo deviceInfo){
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("DeviceInfo".getBytes())){
				MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
			}
			if(jedis!=null && jedis.hexists("DeviceInfo".getBytes(), (deviceInfo.getId()+"").getBytes())){
				jedis.hset("DeviceInfo".getBytes(), (deviceInfo.getId()+"").getBytes(), SerializeObjectUnils.serialize(deviceInfo));
			}else if(!jedis.hexists("DeviceInfo".getBytes(), (deviceInfo.getId()+"").getBytes())){
				List<String> deviceList=new ArrayList<String>();
				deviceList.add(deviceInfo.getId()+"");
				loadDeviceInfo(deviceList,0,"update");
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static DeviceInfo getDeviceInfo(String deviceId){
		Jedis jedis=null;
		DeviceInfo deviceInfo=null;
		if(!existsKey("DeviceInfo")){
			MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis.hexists("DeviceInfo".getBytes(), deviceId.getBytes())){
				deviceInfo=(DeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("DeviceInfo".getBytes(), deviceId.getBytes()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return deviceInfo;
	}
	
	public static DeviceInfo getDeviceInfo(String signInId,int slave){
		List<DeviceInfo> deviceInfoList=getDeviceInfo();
		DeviceInfo deviceInfo=null;
		if(deviceInfoList!=null){
			for(int i=0;i<deviceInfoList.size();i++){
				if(signInId.equalsIgnoreCase(deviceInfoList.get(i).getSignInId()) && slave==StringManagerUtils.stringToInteger(deviceInfoList.get(i).getSlave())){
					deviceInfo=deviceInfoList.get(i);
					break;
				}
			}
		}
		return deviceInfo;
	}
	
	public static DeviceInfo getDeviceInfoByIPPort(String IPPort,int slave){
		List<DeviceInfo> deviceInfoList=getDeviceInfo();
		DeviceInfo deviceInfo=null;
		if(deviceInfoList!=null){
			for(int i=0;i<deviceInfoList.size();i++){
				if(IPPort.equalsIgnoreCase(deviceInfoList.get(i).getIpPort()) && slave==StringManagerUtils.stringToInteger(deviceInfoList.get(i).getSlave())){
					deviceInfo=deviceInfoList.get(i);
					break;
				}
			}
		}
		return deviceInfo;
	}
	
	public static List<DeviceInfo> getDeviceInfo(String[] ids){
		Jedis jedis=null;
		List<DeviceInfo> list=new ArrayList<>();
		if(!existsKey("DeviceInfo")){
			MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			List<byte[]> deviceInfoByteList =jedis.hvals("DeviceInfo".getBytes());
			for(int i=0;i<deviceInfoByteList.size();i++){
				Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
				if (obj instanceof DeviceInfo) {
					DeviceInfo deviceInfo=(DeviceInfo)obj;
					if(StringManagerUtils.existOrNot(ids, deviceInfo.getId()+"")){
						list.add(deviceInfo);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return list;
	}
	
	public static List<DeviceInfo> getDeviceInfoByOrgIdArr(String[] orgIdArr){
		Jedis jedis=null;
		List<DeviceInfo> list=new ArrayList<>();
		if(!existsKey("DeviceInfo")){
			MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			List<byte[]> deviceInfoByteList =jedis.hvals("DeviceInfo".getBytes());
			for(int i=0;i<deviceInfoByteList.size();i++){
				Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
				if (obj instanceof DeviceInfo) {
					DeviceInfo deviceInfo=(DeviceInfo)obj;
					if(StringManagerUtils.existOrNot(orgIdArr, deviceInfo.getOrgId()+"")){
						list.add(deviceInfo);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return list;
	}
	
	public static List<DeviceInfo> getDeviceInfo(){
		Jedis jedis=null;
		List<DeviceInfo> list=new ArrayList<>();
		if(!existsKey("DeviceInfo")){
			MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			List<byte[]> deviceInfoByteList =jedis.hvals("DeviceInfo".getBytes());
			for(int i=0;i<deviceInfoByteList.size();i++){
				Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
				if (obj instanceof DeviceInfo) {
					DeviceInfo deviceInfo=(DeviceInfo)obj;
					list.add(deviceInfo);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return list;
	}
	
	public static void loadDeviceInfo(List<String> wellList,int condition,String method){//condition 0 -设备ID 1-设备名称
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			String wells="";
			if(condition==0){
				wells=StringUtils.join(wellList, ",");
			}else{
				wells=StringManagerUtils.joinStringArr2(wellList, ",");
			}
			if("delete".equalsIgnoreCase(method)){
				if(condition==0){
					for(int i=0;i<wellList.size();i++){
						jedis.hdel("DeviceInfo".getBytes(), wellList.get(i).getBytes());
						jedis.hdel("SRPDeviceTodayData".getBytes(), wellList.get(i).getBytes());
						jedis.hdel("PCPDeviceTodayData".getBytes(), wellList.get(i).getBytes());
						
						jedis.del(("DeviceRealtimeAcqData_"+wellList.get(i)).getBytes());
					}
				}else if(condition==1){
					for(int i=0;i<wellList.size();i++){
						List<DeviceInfo> deviceInfoList = MemoryDataManagerTask.getDeviceInfo();
						if(deviceInfoList!=null){
							for(int j=0;j<deviceInfoList.size();j++){
								DeviceInfo deviceInfo=deviceInfoList.get(i);
								if(wellList.get(i).equalsIgnoreCase(deviceInfo.getDeviceName())){
									jedis.hdel("DeviceInfo".getBytes(), (deviceInfo.getId()+"").getBytes());
									jedis.hdel("SRPDeviceTodayData".getBytes(), (deviceInfo.getId()+"").getBytes());
									jedis.hdel("PCPDeviceTodayData".getBytes(), (deviceInfo.getId()+"").getBytes());
									jedis.del(("DeviceRealtimeAcqData_"+deviceInfo.getId()).getBytes());
									break;
								}
							}
						}
					}
				}
			}else{
				String sql="select t.id,"
						+ "t.orgid,"
						+ "t.orgName_zh_cn,t.orgName_en,t.orgName_ru,"//3~5
						+ "t.devicename,"
						+ "t.devicetype,"
						+ "t.devicetypename_zh_cn,t.devicetypename_en,devicetypename_ru,"//8~10
						+ "t.applicationscenarios,"//11
						+ "t.calculateType,"//12
						+ "t.tcptype,t.signinid,t.ipport,t.slave,t.peakdelay,"
						+ "t.videourl1,t.videokeyid1,t.videourl2,t.videokeyid2,"
						+ "t.instancecode,t.instancename,t.alarminstancecode,t.alarminstancename,t.displayinstancecode,t.displayinstancename,"
						+ "t.status,t.statusName,"
						+ "t.productiondata,t.balanceinfo,t.stroke,"
						+ "t.sortnum,"
						+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss'),"
						+ "t2.commstatus,t2.commtime,t2.commtimeefficiency,t2.commrange,"
						+ "decode(t2.runstatus,null,2,t2.runstatus),t2.runtime,t2.runtimeefficiency,t2.runrange,"
						+ " decode(t.calculateType,1,t3.resultstatus,2,t4.resultstatus,0),decode(t3.resultcode,null,0,t3.resultcode) as resultcode"
						+ " from viw_device t"
						+ " left outer join tbl_acqdata_latest t2 on t2.deviceid=t.id "
						+ " left outer join tbl_srpacqdata_latest t3 on t3.deviceid=t.id "
						+ " left outer join tbl_pcpacqdata_latest t4 on t4.deviceid=t.id "
						+ " where 1=1 ";
				String auxiliaryDeviceSql="select t.id,t3.id as auxiliarydeviceid,t3.manufacturer,t3.model,t4.itemcode,t4.itemvalue "
						+ " from tbl_device t,tbl_auxiliary2master t2,tbl_auxiliarydevice t3,tbl_auxiliarydeviceaddinfo t4 "
						+ " where t.id=t2.masterid and t2.auxiliaryid=t3.id and t3.id=t4.deviceid "
						+ " and t3.specifictype=1";
				String dailyTotalSql="select t.id,t.deviceid,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,t.itemcolumn,t.itemName,t.totalvalue,t.todayvalue "
						+ "from TBL_DAILYTOTALCALCULATE_LATEST t,tbl_device t2 "
						+ "where t.deviceid=t2.id";
				if(StringManagerUtils.isNotNull(wells)){
					if(condition==0){
						sql+=" and t.id in("+wells+")";
						auxiliaryDeviceSql+=" and t.id in("+wells+")";
						dailyTotalSql+=" and t2.id in("+wells+")";
					}else{
						sql+=" and t.devicename in("+wells+")";
						auxiliaryDeviceSql+=" and t.devicename in("+wells+")";
						dailyTotalSql+=" and t2.devicename in("+wells+")";
					}
				}
				sql+=" order by t.sortNum,t.devicename";
				dailyTotalSql+=" order by t.deviceid,t.itemcolumn";
				pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				
				List<Object[]> auxiliaryDeviceList=OracleJdbcUtis.query(auxiliaryDeviceSql);
				List<Object[]> dailyTotalList=OracleJdbcUtis.query(dailyTotalSql);
				
				List<AuxiliaryDeviceAddInfo> auxiliaryDeviceAddInfoList=new ArrayList<>();
				for(Object[] obj:auxiliaryDeviceList){
					AuxiliaryDeviceAddInfo auxiliaryDeviceAddInfo=new AuxiliaryDeviceAddInfo();
					auxiliaryDeviceAddInfo.setMasterId(StringManagerUtils.stringToInteger(obj[0]+""));
					auxiliaryDeviceAddInfo.setDeviceId(StringManagerUtils.stringToInteger(obj[1]+""));
					auxiliaryDeviceAddInfo.setManufacturer(obj[2]+"");
					auxiliaryDeviceAddInfo.setModel(obj[3]+"");
					auxiliaryDeviceAddInfo.setItemCode(obj[4]+"");
					auxiliaryDeviceAddInfo.setItemValue(obj[5]+"");
					auxiliaryDeviceAddInfoList.add(auxiliaryDeviceAddInfo);
				}
				while(rs.next()){
					DeviceInfo deviceInfo=new DeviceInfo();
					
					deviceInfo.setId(rs.getInt(1));
					deviceInfo.setOrgId(rs.getInt(2));
					deviceInfo.setOrgName_zh_CN(rs.getString(3));
					deviceInfo.setOrgName_en(rs.getString(4));
					deviceInfo.setOrgName_ru(rs.getString(5));
					deviceInfo.setDeviceName(rs.getString(6));
					deviceInfo.setDeviceType(rs.getInt(7));
					deviceInfo.setDeviceTypeName_zh_CN(rs.getString(8));
					deviceInfo.setDeviceTypeName_en(rs.getString(9));
					deviceInfo.setDeviceTypeName_ru(rs.getString(10));
					
					deviceInfo.setApplicationScenarios(rs.getInt(11));
					deviceInfo.setApplicationScenariosName(getCodeName("APPLICATIONSCENARIOS",deviceInfo.getApplicationScenarios()+"", Config.getInstance().configFile.getAp().getOthers().getLoginLanguage()));
					
					deviceInfo.setCalculateType(rs.getInt(12));
					
					deviceInfo.setTcpType(rs.getString(13)+"");
					deviceInfo.setSignInId(rs.getString(14)+"");
					deviceInfo.setIpPort(rs.getString(15)+"");
					deviceInfo.setSlave(rs.getString(16)+"");
					deviceInfo.setPeakDelay(rs.getInt(17));
					
					deviceInfo.setVideoUrl1(rs.getString(18)+"");
					deviceInfo.setVideoKey1(rs.getInt(19));
					deviceInfo.setVideoUrl2(rs.getString(20)+"");
					deviceInfo.setVideoKey2(rs.getInt(21));
					
					deviceInfo.setInstanceCode(rs.getString(22)+"");
					deviceInfo.setInstanceName(rs.getString(23)+"");
					deviceInfo.setAlarmInstanceCode(rs.getString(24)+"");
					deviceInfo.setAlarmInstanceName(rs.getString(25)+"");
					deviceInfo.setDisplayInstanceCode(rs.getString(26)+"");
					deviceInfo.setDisplayInstanceName(rs.getString(27)+"");
					deviceInfo.setStatus(rs.getInt(28));
					deviceInfo.setStatusName(rs.getString(29)+"");
					
					String productionData=rs.getString(30)+"";
					String balanceInfo=rs.getString(31)+"";
					float stroke=rs.getFloat(32);
					
					if(StringManagerUtils.isNotNull(productionData)){
						if(deviceInfo.getCalculateType()==1){//功图计算
							type = new TypeToken<SRPCalculateRequestData>() {}.getType();
							SRPCalculateRequestData srpProductionData=gson.fromJson(productionData, type);
							
							List<AuxiliaryDeviceAddInfo> thisAuxiliaryDeviceAddInfoList=new ArrayList<>();
							String manufacturer="";
							String model="";
							for(int i=0;i<auxiliaryDeviceAddInfoList.size();i++){
								if(auxiliaryDeviceAddInfoList.get(i).getMasterId()==deviceInfo.getId()){
									thisAuxiliaryDeviceAddInfoList.add(auxiliaryDeviceAddInfoList.get(i));
								}
							}
							
							deviceInfo.setSrpCalculateRequestData(new SRPCalculateRequestData());
							deviceInfo.getSrpCalculateRequestData().init();
							
							deviceInfo.getSrpCalculateRequestData().setFluidPVT(srpProductionData.getFluidPVT());
							deviceInfo.getSrpCalculateRequestData().setReservoir(srpProductionData.getReservoir());
							deviceInfo.getSrpCalculateRequestData().setTubingString(srpProductionData.getTubingString());
							deviceInfo.getSrpCalculateRequestData().setCasingString(srpProductionData.getCasingString());
							deviceInfo.getSrpCalculateRequestData().setRodString(srpProductionData.getRodString());
							deviceInfo.getSrpCalculateRequestData().setPump(srpProductionData.getPump());
							deviceInfo.getSrpCalculateRequestData().setProduction(srpProductionData.getProduction());
							
							deviceInfo.getSrpCalculateRequestData().setManualIntervention(srpProductionData.getManualIntervention());
							
							if(srpProductionData.getFESDiagram()!=null){
								deviceInfo.getSrpCalculateRequestData().getFESDiagram().setSrc(srpProductionData.getFESDiagram().getSrc());
							}
							
							if(thisAuxiliaryDeviceAddInfoList.size()>0){
								deviceInfo.getSrpCalculateRequestData().setPumpingUnit(new SRPCalculateRequestData.PumpingUnit());
								for(int i=0;i<thisAuxiliaryDeviceAddInfoList.size();i++ ){
									manufacturer=thisAuxiliaryDeviceAddInfoList.get(i).getManufacturer();
									model=thisAuxiliaryDeviceAddInfoList.get(i).getModel();
									if("structureType".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
										deviceInfo.getSrpCalculateRequestData().getPumpingUnit().setStructureType(StringManagerUtils.stringToInteger(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
									}else if("crankRotationDirection".equalsIgnoreCase(thisAuxiliaryDeviceAddInfoList.get(i).getItemCode())){
										deviceInfo.getSrpCalculateRequestData().getPumpingUnit().setCrankRotationDirection(thisAuxiliaryDeviceAddInfoList.get(i).getItemValue());
									}else if("offsetAngleOfCrank".equalsIgnoreCase(thisAuxiliaryDeviceAddInfoList.get(i).getItemCode())){
										deviceInfo.getSrpCalculateRequestData().getPumpingUnit().setOffsetAngleOfCrank(StringManagerUtils.stringToFloat(thisAuxiliaryDeviceAddInfoList.get(i).getItemValue()));
									}else if("crankGravityRadius".equalsIgnoreCase(thisAuxiliaryDeviceAddInfoList.get(i).getItemCode())){
										deviceInfo.getSrpCalculateRequestData().getPumpingUnit().setCrankGravityRadius(StringManagerUtils.stringToFloat(thisAuxiliaryDeviceAddInfoList.get(i).getItemValue()));
									}else if("singleCrankWeight".equalsIgnoreCase(thisAuxiliaryDeviceAddInfoList.get(i).getItemCode())){
										deviceInfo.getSrpCalculateRequestData().getPumpingUnit().setSingleCrankWeight(StringManagerUtils.stringToFloat(thisAuxiliaryDeviceAddInfoList.get(i).getItemValue()));
									}else if("singleCrankPinWeight".equalsIgnoreCase(thisAuxiliaryDeviceAddInfoList.get(i).getItemCode())){
										deviceInfo.getSrpCalculateRequestData().getPumpingUnit().setSingleCrankPinWeight(StringManagerUtils.stringToFloat(thisAuxiliaryDeviceAddInfoList.get(i).getItemValue()));
									}else if("structuralUnbalance".equalsIgnoreCase(thisAuxiliaryDeviceAddInfoList.get(i).getItemCode())){
										deviceInfo.getSrpCalculateRequestData().getPumpingUnit().setStructuralUnbalance(StringManagerUtils.stringToFloat(thisAuxiliaryDeviceAddInfoList.get(i).getItemValue()));
									}
								}
								deviceInfo.getSrpCalculateRequestData().getPumpingUnit().setManufacturer(manufacturer);
								deviceInfo.getSrpCalculateRequestData().getPumpingUnit().setModel(model);
								deviceInfo.getSrpCalculateRequestData().getPumpingUnit().setStroke(stroke);
								
								type = new TypeToken<SRPCalculateRequestData.Balance>() {}.getType();
								SRPCalculateRequestData.Balance balance=gson.fromJson(balanceInfo, type);
								if(balance!=null){
									deviceInfo.getSrpCalculateRequestData().getPumpingUnit().setBalance(balance);
								}
							}else{
								deviceInfo.getSrpCalculateRequestData().setPumpingUnit(null);
							}
						}else if(deviceInfo.getCalculateType()==2){//转速计产
							type = new TypeToken<PCPCalculateRequestData>() {}.getType();
							PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
							deviceInfo.setPcpCalculateRequestData(new PCPCalculateRequestData());
							deviceInfo.getPcpCalculateRequestData().init();
							
							deviceInfo.getPcpCalculateRequestData().setFluidPVT(pcpProductionData.getFluidPVT());
							deviceInfo.getPcpCalculateRequestData().setReservoir(pcpProductionData.getReservoir());
							deviceInfo.getPcpCalculateRequestData().setTubingString(pcpProductionData.getTubingString());
							deviceInfo.getPcpCalculateRequestData().setCasingString(pcpProductionData.getCasingString());
							deviceInfo.getPcpCalculateRequestData().setRodString(pcpProductionData.getRodString());
							deviceInfo.getPcpCalculateRequestData().setPump(pcpProductionData.getPump());
							deviceInfo.getPcpCalculateRequestData().setProduction(pcpProductionData.getProduction());
							
							deviceInfo.getPcpCalculateRequestData().setManualIntervention(pcpProductionData.getManualIntervention());
						}
					}else{
						deviceInfo.setSrpCalculateRequestData(null);
						deviceInfo.setPcpCalculateRequestData(null);
					}
					deviceInfo.setSortNum(rs.getInt(33));
					
					deviceInfo.setAcqTime(rs.getString(34));
					deviceInfo.setSaveTime("");
					
					deviceInfo.setCommStatus(rs.getInt(35));
					deviceInfo.setCommTime(rs.getFloat(36));
					deviceInfo.setCommEff(rs.getFloat(37));
					deviceInfo.setCommRange(StringManagerUtils.CLOBtoString2(rs.getClob(38)));
					
					deviceInfo.setOnLineAcqTime(deviceInfo.getAcqTime());
					deviceInfo.setOnLineCommStatus(deviceInfo.getCommStatus());
					deviceInfo.setOnLineCommTime(deviceInfo.getCommTime());
					deviceInfo.setOnLineCommEff(deviceInfo.getCommEff());
					deviceInfo.setOnLineCommRange(deviceInfo.getCommRange());
					
					deviceInfo.setRunStatusAcqTime(deviceInfo.getAcqTime());
					deviceInfo.setRunStatus(rs.getInt(39));
					deviceInfo.setRunTime(rs.getFloat(40));
					deviceInfo.setRunEff(rs.getFloat(41));
					deviceInfo.setRunRange(StringManagerUtils.CLOBtoString2(rs.getClob(42)));
					
					deviceInfo.setResultStatus(rs.getInt(43));
					deviceInfo.setResultCode(rs.getInt(44));
					
					//日汇总数据
					deviceInfo.setDailyTotalItemMap(new HashMap<>());
					for(Object[] obj:dailyTotalList){
						if(deviceInfo.getId()==StringManagerUtils.stringToInteger(obj[1]+"")){
							DeviceInfo.DailyTotalItem dailyTotalItem=new DeviceInfo.DailyTotalItem();
							dailyTotalItem.setAcqTime(obj[2]+"");
							dailyTotalItem.setItemColumn((obj[3]+"").toUpperCase());
							dailyTotalItem.setItemName((obj[4]+"").toUpperCase());
							dailyTotalItem.setTotalValue(StringManagerUtils.stringToFloat(obj[5]+""));
							dailyTotalItem.setTodayValue(StringManagerUtils.stringToFloat(obj[6]+""));
							deviceInfo.getDailyTotalItemMap().put(dailyTotalItem.getItemColumn(), dailyTotalItem);
						}
					}
					
					String key=deviceInfo.getId()+"";
					jedis.hset("DeviceInfo".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(deviceInfo));//哈希(Hash)
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static void updateDeviceRealtimeAcqData(String deviceId,String acqTime,Map<String,String> realtimeData,String language){
		Jedis jedis=null;
		String key="DeviceRealtimeAcqData_"+deviceId;
		
		if(!existsKey(key)){
			List<String> deviceIdList=new ArrayList<>();
			deviceIdList.add(deviceId);
			long t1=System.nanoTime();
			loadDeviceRealtimeAcqData(deviceIdList,language);
			long t2=System.nanoTime();
			String memoryUsage =getMemoryUsage(key);
			System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":"+"加载设备"+deviceId+"当天数据至内存,数据大小:"+memoryUsage+",耗时:"+StringManagerUtils.getTimeDiff(t1, t2));
		}
		
		try {
			jedis = RedisUtil.jedisPool.getResource();
			jedis.hset(key.getBytes(),acqTime.getBytes(), SerializeObjectUnils.serialize(realtimeData));
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static int delDeviceRealtimeAcqData(String deviceId,String date){
		Jedis jedis=null;
		int r=0;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			String key="DeviceRealtimeAcqData_"+deviceId;
			Set<byte[]> keySet=jedis.hkeys(key.getBytes());
			if(keySet!=null && keySet.size()>0){
				for(byte[] arr:keySet){
					String acqTime=new String(arr);
					if(!StringManagerUtils.timeMatchDate(acqTime, date, 0)){
						jedis.hdel(key.getBytes(), acqTime.getBytes());
						r++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			r=-1;
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return r;
	}
	
	public static int delDeviceRealtimeAcqData(String deviceId,String[] acqTimeArr){
		Jedis jedis=null;
		int r=0;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			String key="DeviceRealtimeAcqData_"+deviceId;
			Set<byte[]> keySet=jedis.hkeys(key.getBytes());
			if(keySet!=null && keySet.size()>0){
				for(byte[] arr:keySet){
					String acqTime=new String(arr);
					if(StringManagerUtils.existOrNot(acqTimeArr, acqTime, false)){
						jedis.hdel(key.getBytes(), acqTime.getBytes());
						r++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return r;
	}
	
	public static Map<String,Map<String,String>> getDeviceRealtimeAcqDataById(String deviceId,String language){
		Jedis jedis=null;
		Map<String,Map<String,String>> realtimeDataTimeMap=new TreeMap<>();
		String key="DeviceRealtimeAcqData_"+deviceId;
		if(!existsKey(key)){
			List<String> deviceIdList=new ArrayList<>();
			deviceIdList.add(deviceId);
			long t1=System.nanoTime();
			loadDeviceRealtimeAcqData(deviceIdList,language);
			long t2=System.nanoTime();
			String memoryUsage =getMemoryUsage(key);
			System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":"+"加载设备"+deviceId+"当天数据至内存,数据大小:"+memoryUsage+",耗时:"+StringManagerUtils.getTimeDiff(t1, t2));
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis.exists(key.getBytes())){
				Map<byte[], byte[]> memDataMap= jedis.hgetAll(key.getBytes());
				if(memDataMap!=null && memDataMap.size()>0){
					Iterator<Map.Entry<byte[], byte[]>> it = memDataMap.entrySet().iterator();
					while(it.hasNext()){
						Map.Entry<byte[], byte[]> entry = it.next();
						String acqTime=new String(entry.getKey());
						Map<String,String> everyDataMap=(Map<String,String>) SerializeObjectUnils.unserizlize(entry.getValue());
						realtimeDataTimeMap.put(acqTime, everyDataMap);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return realtimeDataTimeMap;
	}
	
	public static List<Map<String,String>> getDeviceRealtimeAcqDataListById(String deviceId,String language,int sortType){
		Jedis jedis=null;
		List<Map<String,String>> realtimeDataList=new ArrayList<>();
		Map<String,Map<String,String>> realtimeDataTimeMap=new TreeMap<>();//默认升序
		if(sortType==1){//降序
			realtimeDataTimeMap=new TreeMap<>(Collections.reverseOrder());
		}
		
		String key="DeviceRealtimeAcqData_"+deviceId;
		if(!existsKey(key)){
			List<String> deviceIdList=new ArrayList<>();
			deviceIdList.add(deviceId);
			long t1=System.nanoTime();
			loadDeviceRealtimeAcqData(deviceIdList,language);
			long t2=System.nanoTime();
			String memoryUsage =getMemoryUsage(key);
			System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":"+"加载设备"+deviceId+"当天数据至内存,数据大小:"+memoryUsage+",耗时:"+StringManagerUtils.getTimeDiff(t1, t2));
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis.exists(key.getBytes())){
				Map<byte[], byte[]> memDataMap= jedis.hgetAll(key.getBytes());
				if(memDataMap!=null && memDataMap.size()>0){
					Iterator<Map.Entry<byte[], byte[]>> it = memDataMap.entrySet().iterator();
					while(it.hasNext()){
						Map.Entry<byte[], byte[]> entry = it.next();
						String acqTime=new String(entry.getKey());
						Map<String,String> everyDataMap=(Map<String,String>) SerializeObjectUnils.unserizlize(entry.getValue());
						if(everyDataMap.containsKey("commStatus") && StringManagerUtils.stringToInteger(everyDataMap.get("commStatus"))==1 && everyDataMap.containsKey("acqTime")){
							realtimeDataTimeMap.put(everyDataMap.get("acqTime"), everyDataMap);
							
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		Iterator<Map.Entry<String,Map<String,String>>> iterator = realtimeDataTimeMap.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<String,Map<String,String>> entry = iterator.next();
			realtimeDataList.add(entry.getValue());
		}
		return realtimeDataList;
	}
	
	public static void loadDeviceRealtimeAcqData(List<String> deviceIdList,String language){
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			List<CalItem> srpCalItemList=MemoryDataManagerTask.getSRPCalculateItem(language);
			List<CalItem> pcpCalItemList=MemoryDataManagerTask.getPCPCalculateItem(language);
			String date=StringManagerUtils.getCurrentTime();
			
			String sql="select t.deviceId,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,t.acqdata,t.commStatus,t.checksign,t.id";
			sql+=" from tbl_acqdata_hist t "
				+ " where t.acqTime between to_date('"+date+"','yyyy-mm-dd') and to_date('"+date+"','yyyy-mm-dd')+1";
			if(deviceIdList!=null && deviceIdList.size()>0){
				if(deviceIdList.size()==1){
					sql+=" and t.deviceId="+deviceIdList.get(0);
				}else{
					sql+=" and t.deviceId in("+StringUtils.join(deviceIdList, ",")+")";
				}
			}
			sql+=" order by t.deviceId,t.acqTime";
			
			List<Object[]> queryDataList=OracleJdbcUtis.query(sql);
			
			for(Object[] obj:queryDataList){
				int deviceId=StringManagerUtils.stringToInteger(obj[0]+"");
				String acqTime=obj[1]+"";
				String acqData=obj[2]+"";
				String commStatus=obj[3]+"";
				String checkSign=obj[4]+"";
				
				type = new TypeToken<List<KeyValue>>() {}.getType();
				List<KeyValue> acqDataList=gson.fromJson(acqData, type);
				
				String key="DeviceRealtimeAcqData_"+deviceId;
				
				Map<String,String> everyDataMap =new HashMap<>();
				everyDataMap.put("deviceId", deviceId+"");
				everyDataMap.put("acqTime", acqTime);
				everyDataMap.put("commStatus", commStatus);
				everyDataMap.put("checkSign", checkSign);
				if(acqDataList!=null){
					for(int i=0;i<acqDataList.size();i++){
						everyDataMap.put(acqDataList.get(i).getKey().toUpperCase(), acqDataList.get(i).getValue());
					}
				}
				jedis.hset(key.getBytes(), acqTime.getBytes(), SerializeObjectUnils.serialize(everyDataMap));
			}
			
			//加载功图计算数据
			sql="select t.deviceId,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,t.productiondata";
			for(CalItem calItem:srpCalItemList){
				String columnCode=calItem.getCode();
				if("resultName".equalsIgnoreCase(columnCode)){
					columnCode="resultCode";
				}else if("runstatusName".equalsIgnoreCase(columnCode)){
					columnCode="runstatus";
				}
				sql+=",t."+columnCode;
			}	
			sql+= " from viw_srpacqdata_hist t"
					+ " where t.acqTime between to_date('"+date+"','yyyy-mm-dd') and to_date('"+date+"','yyyy-mm-dd')+1";
			if(deviceIdList!=null && deviceIdList.size()>0){
				if(deviceIdList.size()==1){
					sql+=" and t.deviceId="+deviceIdList.get(0);
				}else{
					sql+=" and t.deviceId in("+StringUtils.join(deviceIdList, ",")+")";
				}
			}
			sql+=" order by t.deviceId,t.acqTime";
			
			List<Object[]> querySRPCalDataList=OracleJdbcUtis.query(sql);
			for(Object[] obj:querySRPCalDataList){
				int deviceId=StringManagerUtils.stringToInteger(obj[0]+"");
				String acqTime=obj[1]+"";
				String key="DeviceRealtimeAcqData_"+deviceId;
				String productionDataStr=obj[2]+"";
				type = new TypeToken<SRPCalculateRequestData>() {}.getType();
				SRPCalculateRequestData productionData=gson.fromJson(productionDataStr, type);
				Map<String,String> productionDataMap=new LinkedHashMap<>();
				if(productionData!=null){
					if(productionData.getFluidPVT()!=null){
						productionDataMap.put("CrudeOilDensity".toUpperCase(), productionData.getFluidPVT().getCrudeOilDensity()+"");
						productionDataMap.put("WaterDensity".toUpperCase(), productionData.getFluidPVT().getWaterDensity()+"");
						productionDataMap.put("NaturalGasRelativeDensity".toUpperCase(), productionData.getFluidPVT().getNaturalGasRelativeDensity()+"");
						productionDataMap.put("SaturationPressure".toUpperCase(), productionData.getFluidPVT().getSaturationPressure()+"");
					}
					if(productionData.getReservoir()!=null){
						productionDataMap.put("ReservoirDepth".toUpperCase(), productionData.getReservoir().getDepth()+"");
						productionDataMap.put("ReservoirTemperature".toUpperCase(), productionData.getReservoir().getTemperature()+"");
					}
					if(productionData.getProduction()!=null){
						productionDataMap.put("TubingPressure".toUpperCase(), productionData.getProduction().getTubingPressure()+"");
						productionDataMap.put("CasingPressure".toUpperCase(), productionData.getProduction().getCasingPressure()+"");
						productionDataMap.put("WellHeadTemperature".toUpperCase(), productionData.getProduction().getWellHeadTemperature()+"");
						productionDataMap.put("WaterCut".toUpperCase(), productionData.getProduction().getWaterCut()+"");
						productionDataMap.put("ProductionGasOilRatio".toUpperCase(), productionData.getProduction().getProductionGasOilRatio()+"");
						productionDataMap.put("ProducingfluidLevel".toUpperCase(), productionData.getProduction().getProducingfluidLevel()+"");
						productionDataMap.put("PumpSettingDepth".toUpperCase(), productionData.getProduction().getPumpSettingDepth()+"");
						
					}
					if(productionData.getPump()!=null){
						productionDataMap.put("PumpBoreDiameter".toUpperCase(), productionData.getPump().getPumpBoreDiameter()+"");
					}
					if(productionData.getManualIntervention()!=null){
						productionDataMap.put("LevelCorrectValue".toUpperCase(), productionData.getManualIntervention().getLevelCorrectValue()+"");
					}
				}
				
				if(!jedis.hexists(key.getBytes(),acqTime.getBytes())){
					Map<String,String> everyDataMap =new HashMap<>();
					for(int i=0;i<srpCalItemList.size();i++){
						everyDataMap.put(srpCalItemList.get(i).getCode().toUpperCase(), obj[i+3]+"");
					}
					Iterator<Map.Entry<String,String>> productionDataMapIterator = productionDataMap.entrySet().iterator();
					while(productionDataMapIterator.hasNext()){
						Map.Entry<String,String> entry = productionDataMapIterator.next();
						everyDataMap.put(entry.getKey().toUpperCase(), entry.getValue());
					}
					jedis.hset(key.getBytes(),acqTime.getBytes(), SerializeObjectUnils.serialize(everyDataMap));
				}else{
					Map<String,String> everyDataMap =null;
					byte[] data=jedis.hget(key.getBytes(),acqTime.getBytes());
					if(data!=null){
						everyDataMap=(Map<String,String>) SerializeObjectUnils.unserizlize(data);
						
					}
					
					if(everyDataMap==null){
						everyDataMap =new HashMap<>();
					}
					for(int i=0;i<srpCalItemList.size();i++){
						everyDataMap.put(srpCalItemList.get(i).getCode().toUpperCase(), obj[i+3]+"");
					}
					
					Iterator<Map.Entry<String,String>> productionDataMapIterator = productionDataMap.entrySet().iterator();
					while(productionDataMapIterator.hasNext()){
						Map.Entry<String,String> entry = productionDataMapIterator.next();
						everyDataMap.put(entry.getKey().toUpperCase(), entry.getValue());
					}
					jedis.hset(key.getBytes(),acqTime.getBytes(), SerializeObjectUnils.serialize(everyDataMap));
				}
			}
			
			//加载转速计产数据
			sql="select t.deviceId,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,t.productiondata";	
			for(CalItem calItem:pcpCalItemList){
				String columnCode=calItem.getCode();
				if("runstatusName".equalsIgnoreCase(columnCode)){
					columnCode="runstatus";
				}
				sql+=",t."+columnCode;
			}
			
			sql+= " from viw_pcpacqdata_hist t"
					+ " where t.acqTime between to_date('"+date+"','yyyy-mm-dd') and to_date('"+date+"','yyyy-mm-dd')+1";
			if(deviceIdList!=null && deviceIdList.size()>0){
				if(deviceIdList.size()==1){
					sql+=" and t.deviceId="+deviceIdList.get(0);
				}else{
					sql+=" and t.deviceId in("+StringUtils.join(deviceIdList, ",")+")";
				}
			}
			sql+=" order by t.deviceId,t.acqTime";
			
			List<Object[]> queryPCPCalDataList=OracleJdbcUtis.query(sql);
			for(Object[] obj:queryPCPCalDataList){
				int deviceId=StringManagerUtils.stringToInteger(obj[0]+"");
				String acqTime=obj[1]+"";
				String key="DeviceRealtimeAcqData_"+deviceId;
				String productionDataStr=obj[2]+"";
				type = new TypeToken<PCPCalculateRequestData>() {}.getType();
				PCPCalculateRequestData productionData=gson.fromJson(productionDataStr, type);
				Map<String,String> productionDataMap=new LinkedHashMap<>();
				if(productionData!=null){
					if(productionData.getFluidPVT()!=null){
						productionDataMap.put("CrudeOilDensity".toUpperCase(), productionData.getFluidPVT().getCrudeOilDensity()+"");
						productionDataMap.put("WaterDensity".toUpperCase(), productionData.getFluidPVT().getWaterDensity()+"");
						productionDataMap.put("NaturalGasRelativeDensity".toUpperCase(), productionData.getFluidPVT().getNaturalGasRelativeDensity()+"");
						productionDataMap.put("SaturationPressure".toUpperCase(), productionData.getFluidPVT().getSaturationPressure()+"");
					}
					if(productionData.getReservoir()!=null){
						productionDataMap.put("ReservoirDepth".toUpperCase(), productionData.getReservoir().getDepth()+"");
						productionDataMap.put("ReservoirTemperature".toUpperCase(), productionData.getReservoir().getTemperature()+"");
					}
					if(productionData.getProduction()!=null){
						productionDataMap.put("TubingPressure".toUpperCase(), productionData.getProduction().getTubingPressure()+"");
						productionDataMap.put("CasingPressure".toUpperCase(), productionData.getProduction().getCasingPressure()+"");
						productionDataMap.put("WellHeadTemperature".toUpperCase(), productionData.getProduction().getWellHeadTemperature()+"");
						productionDataMap.put("WaterCut".toUpperCase(), productionData.getProduction().getWaterCut()+"");
						productionDataMap.put("ProductionGasOilRatio".toUpperCase(), productionData.getProduction().getProductionGasOilRatio()+"");
						productionDataMap.put("ProducingfluidLevel".toUpperCase(), productionData.getProduction().getProducingfluidLevel()+"");
						productionDataMap.put("PumpSettingDepth".toUpperCase(), productionData.getProduction().getPumpSettingDepth()+"");
					}
				}
				
				
				if(!jedis.hexists(key.getBytes(),acqTime.getBytes())){
					Map<String,String> everyDataMap =new HashMap<>();
					for(int i=0;i<pcpCalItemList.size();i++){
						everyDataMap.put(pcpCalItemList.get(i).getCode().toUpperCase(), obj[i+3]+"");
					}
					Iterator<Map.Entry<String,String>> productionDataMapIterator = productionDataMap.entrySet().iterator();
					while(productionDataMapIterator.hasNext()){
						Map.Entry<String,String> entry = productionDataMapIterator.next();
						everyDataMap.put(entry.getKey().toUpperCase(), entry.getValue());
					}
					jedis.hset(key.getBytes(),acqTime.getBytes(), SerializeObjectUnils.serialize(everyDataMap));
				}else{
					Map<String,String> everyDataMap =null;
					byte[] data=jedis.hget(key.getBytes(),acqTime.getBytes());
					if(data!=null){
						everyDataMap=(Map<String,String>) SerializeObjectUnils.unserizlize(data);
						
					}
					if(everyDataMap==null){
						everyDataMap =new HashMap<>();
					}
					for(int i=0;i<pcpCalItemList.size();i++){
						everyDataMap.put(pcpCalItemList.get(i).getCode().toUpperCase(), obj[i+3]+"");
					}
					
					Iterator<Map.Entry<String,String>> productionDataMapIterator = productionDataMap.entrySet().iterator();
					while(productionDataMapIterator.hasNext()){
						Map.Entry<String,String> entry = productionDataMapIterator.next();
						everyDataMap.put(entry.getKey().toUpperCase(), entry.getValue());
					}
					jedis.hset(key.getBytes(),acqTime.getBytes(), SerializeObjectUnils.serialize(everyDataMap));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static void loadDeviceInfoByPumpingAuxiliaryId(String auxiliaryId,String method){
		List<String> wellList =new ArrayList<String>();
		String sql="select distinct(t.id) from tbl_device t,tbl_auxiliary2master t2 where t.id = t2.masterid and t2.auxiliaryid in("+auxiliaryId+")";
		try {
			List<Object[]> list=OracleJdbcUtis.query(sql);
			for(Object[] obj:list){
				wellList.add(obj[0]+"");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(wellList.size()>0){
			loadDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadDeviceInfoByPumpingId(String pumpingModelId,String method){
		List<String> wellList =new ArrayList<String>();
		String sql="select t.id from tbl_device t,tbl_pumpingmodel t2 where t.pumpingmodelid=t2.id and t2.id= "+pumpingModelId;
		try {
			List<Object[]> list=OracleJdbcUtis.query(sql);
			for(Object[] obj:list){
				wellList.add(obj[0]+"");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(wellList.size()>0){
			loadDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadDeviceInfoByInstanceId(String instanceId,String method){
		List<String> wellList =new ArrayList<String>();
		String sql="select t.id from tbl_device t,tbl_protocolinstance t2 where t.instancecode=t2.code and t2.id= "+instanceId;
		try {
			List<Object[]> list=OracleJdbcUtis.query(sql);
			for(Object[] obj:list){
				wellList.add(obj[0]+"");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(wellList.size()>0){
			loadDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadDeviceInfoByInstanceCode(String instanceCode,String method){
		List<String> wellList =new ArrayList<String>();
		String sql="select t.id from tbl_device t where t.instancecode= '"+instanceCode+"'";
		try {
			List<Object[]> list=OracleJdbcUtis.query(sql);
			for(Object[] obj:list){
				wellList.add(obj[0]+"");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(wellList.size()>0){
			loadDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadDeviceInfoByDisplayInstanceId(String instanceId,String method){
		List<String> wellList =new ArrayList<String>();
		String sql="select t.id from tbl_device t,tbl_protocoldisplayinstance t2 where t.displayinstancecode=t2.code and t2.id= "+instanceId;
		try {
			List<Object[]> list=OracleJdbcUtis.query(sql);
			for(Object[] obj:list){
				wellList.add(obj[0]+"");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(wellList.size()>0){
			loadDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadDeviceInfoByDisplayInstanceCode(String instanceCode,String method){
		List<String> wellList =new ArrayList<String>();
		String sql="select t.id from tbl_device t where t.displayinstancecode= '"+instanceCode+"'";
		try {
			List<Object[]> list=OracleJdbcUtis.query(sql);
			for(Object[] obj:list){
				wellList.add(obj[0]+"");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(wellList.size()>0){
			loadDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadDeviceInfoByAlarmInstanceId(String instanceId,String method){
		List<String> wellList =new ArrayList<String>();
		String sql="select t.id from tbl_device t,tbl_protocolalarminstance t2 where t.alarminstancecode=t2.code and t2.id= "+instanceId;
		try {
			List<Object[]> list=OracleJdbcUtis.query(sql);
			for(Object[] obj:list){
				wellList.add(obj[0]+"");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(wellList.size()>0){
			loadDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadDeviceInfoByAlarmInstanceCode(String instanceCode,String method){
		List<String> wellList =new ArrayList<String>();
		String sql="select t.id from tbl_device t where t.alarminstancecode= '"+instanceCode+"'";
		try {
			List<Object[]> list=OracleJdbcUtis.query(sql);
			for(Object[] obj:list){
				wellList.add(obj[0]+"");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(wellList.size()>0){
			loadDeviceInfo(wellList,0,method);
		}
	}
	
	public static AcqInstanceOwnItem.AcqItem getAcqItemByCode(AcqInstanceOwnItem acqInstanceOwnItem,String code){
		AcqInstanceOwnItem.AcqItem acqItem=null;
		if(acqInstanceOwnItem!=null && acqInstanceOwnItem.getItemList()!=null){
			for(AcqInstanceOwnItem.AcqItem item:acqInstanceOwnItem.getItemList()){
				if(code.equalsIgnoreCase(item.getItemCode())){
					acqItem=item;
				}
			}
		}
		return acqItem;
	}
	
	
	public static AcqInstanceOwnItem getAcqInstanceOwnItemByCode(String instanceCode){
		AcqInstanceOwnItem acqInstanceOwnItem=null;
		Jedis jedis=null;
		if(!existsKey("AcqInstanceOwnItem")){
			MemoryDataManagerTask.loadAcqInstanceOwnItemById("","update");
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis.hexists("AcqInstanceOwnItem".getBytes(), instanceCode.getBytes())){
				acqInstanceOwnItem=(AcqInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AcqInstanceOwnItem".getBytes(), instanceCode.getBytes()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return acqInstanceOwnItem;
	}
	
	public static void loadAcqInstanceOwnItemById(String instanceId,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			String instanceSql="select t.code from tbl_protocolinstance t where 1=1 ";
			String sql="select t5.code as instanceCode,t5.acqprotocoltype,t5.ctrlprotocoltype,"
					+ "t4.protocol,t3.unitid,"
					+ "t2.grouptiminginterval,t2.groupsavinginterval,"
					+ "t.id as itemid,t.itemname,t6.mappingcolumn as itemcode,t.bitindex,t.groupid,"
					+ "t.dailyTotalCalculate,t.dailyTotalCalculateName "
					+ " from tbl_acq_item2group_conf t,tbl_acq_group_conf t2,tbl_acq_group2unit_conf t3,tbl_acq_unit_conf t4,tbl_protocolinstance t5,"
					+ " tbl_datamapping t6 "
					+ " where t.groupid=t2.id and t2.id=t3.groupid and t3.unitid=t4.id and t4.id=t5.unitid and t2.type=0 and t.itemname=t6.name";
			if(StringManagerUtils.isNotNull(instanceId)){
				instanceSql+=" and t.id="+instanceId;
				sql+=" and t5.id ="+instanceId;
			}
			sql+=" order by t5.code, t.groupid,t.id";
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				if(jedis.exists("AcqInstanceOwnItem".getBytes())){
					if(jedis.hexists("AcqInstanceOwnItem".getBytes(), rs.getString(1).getBytes())){
						jedis.hdel("AcqInstanceOwnItem".getBytes(), rs.getString(1).getBytes());
					}
				}
			}
			if(pstmt!=null)
        		pstmt.close();  
        	if(rs!=null)
        		rs.close();
			if(!"delete".equalsIgnoreCase(method)){
				pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					AcqInstanceOwnItem acqInstanceOwnItem=null;
					try{
						if(jedis.hexists("AcqInstanceOwnItem".getBytes(), rs.getString(1).getBytes())){
							byte[]byt=  jedis.hget("AcqInstanceOwnItem".getBytes(), rs.getString(1).getBytes());
							if(byt!=null){
								Object obj = SerializeObjectUnils.unserizlize(byt);
								if (obj instanceof AcqInstanceOwnItem) {
									acqInstanceOwnItem=(AcqInstanceOwnItem) obj;
						        }
							}
						}else{
							acqInstanceOwnItem=new AcqInstanceOwnItem();
						}
					}catch(Exception e){
						System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":"+e);
					}
					if(acqInstanceOwnItem==null){
						acqInstanceOwnItem=new AcqInstanceOwnItem();
					}
					
					acqInstanceOwnItem.setInstanceCode(rs.getString(1)+"");
					acqInstanceOwnItem.setAcqProtocolType(rs.getString(2)+"");
					acqInstanceOwnItem.setCtrlProtocolType(rs.getString(3)+"");
					
					acqInstanceOwnItem.setProtocol(rs.getString(4)+"");
					acqInstanceOwnItem.setUnitId(rs.getInt(5));
					acqInstanceOwnItem.setGroupTimingInterval(rs.getInt(6));
					acqInstanceOwnItem.setGroupSavingInterval(rs.getInt(7));
					
					if(acqInstanceOwnItem.getItemList()==null){
						acqInstanceOwnItem.setItemList(new ArrayList<AcqItem>());
					}
					AcqItem acqItem=new AcqItem();
					acqItem.setItemId(rs.getInt(8));
					acqItem.setItemName(rs.getString(9)+"");
					acqItem.setItemCode(rs.getString(10)+"");
					acqItem.setBitIndex(rs.getInt(11));
					acqItem.setGroupId(rs.getInt(12));
					acqItem.setDailyTotalCalculate(rs.getInt(13));
					acqItem.setDailyTotalCalculateName(rs.getString(14)+"");
					int index=-1;
					for(int i=0;i<acqInstanceOwnItem.getItemList().size();i++){
						if(acqItem.getItemId()==acqInstanceOwnItem.getItemList().get(i).getItemId()){
							index=i;
							break;
						}
					}
					if(index>=0){
						acqInstanceOwnItem.getItemList().set(index, acqItem);
					}else{
						acqInstanceOwnItem.getItemList().add(acqItem);
					}
					
					String key=acqInstanceOwnItem.getInstanceCode();
					jedis.hset("AcqInstanceOwnItem".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(acqInstanceOwnItem));//哈希(Hash)
				}
			}
		}catch (Exception e) {
			System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":"+e);
		} finally{
			if(jedis!=null){
				jedis.close();
			}
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadAcqInstanceOwnItemByCode(String instanceCode,String method){
		try {
			String instanceSql="select t.id from tbl_protocolinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(instanceCode)){
				instanceSql+=" and t.code='"+instanceCode+"'";
			}
			List<Object[]> list=OracleJdbcUtis.query(instanceSql);
			for(Object[] obj:list){
				loadAcqInstanceOwnItemById(obj[0]+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadAcqInstanceOwnItemByName(String instanceName,String method){
		try {
			String instanceSql="select t.id from tbl_protocolinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(instanceName)){
				instanceSql+=" and t.name='"+instanceName+"'";
			}
			List<Object[]> list=OracleJdbcUtis.query(instanceSql);
			for(Object[] obj:list){
				loadAcqInstanceOwnItemById(obj[0]+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadAcqInstanceOwnItemByGroupId(String groupId,String method){
		try {
			String instanceSql="select t.id from tbl_protocolinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(groupId)){
				instanceSql+=" and t.unitid in (select t6.unitid from tbl_acq_group2unit_conf t6 where t6.groupid="+groupId+")";
			}
			List<Object[]> list=OracleJdbcUtis.query(instanceSql);
			for(Object[] obj:list){
				loadAcqInstanceOwnItemById(obj[0]+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadAcqInstanceOwnItemByUnitId(String unitId,String method){
		try {
			String instanceSql="select t.id from tbl_protocolinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(unitId)){
				instanceSql+=" and t.unitid ="+unitId;
			}
			List<Object[]> list=OracleJdbcUtis.query(instanceSql);
			for(Object[] obj:list){
				loadAcqInstanceOwnItemById(obj[0]+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadAcqInstanceOwnItemByProtocolName(String protocolName,String method){
		try {
			String instanceSql="select t.id from tbl_protocolinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(protocolName)){
				instanceSql+=" and t.unitid in( select t2.id from tbl_acq_unit_conf t2 where t2.protocol='"+protocolName+"' )";
			}
			List<Object[]> list=OracleJdbcUtis.query(instanceSql);
			for(Object[] obj:list){
				loadAcqInstanceOwnItemById(obj[0]+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static DisplayInstanceOwnItem getDisplayInstanceOwnItemByCode(String instanceCode){
		DisplayInstanceOwnItem displayInstanceOwnItem=null;
		Jedis jedis=null;
		if(!existsKey("DisplayInstanceOwnItem")){
			MemoryDataManagerTask.loadDisplayInstanceOwnItemById("","update");
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis!=null&&jedis.hexists("DisplayInstanceOwnItem".getBytes(), instanceCode.getBytes())){
				displayInstanceOwnItem=(DisplayInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("DisplayInstanceOwnItem".getBytes(), instanceCode.getBytes()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return displayInstanceOwnItem;
	} 
	
	public static void loadDisplayInstanceOwnItemById(String instanceId,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			String instanceSql="select t.code from tbl_protocoldisplayinstance t where 1=1 ";
			String sql="select t3.code as instanceCode,t2.protocol,t.unitid,t.id as itemid,t.itemname,t.itemcode,t.bitindex,"
					+ "decode(t.showlevel,null,9999,t.showlevel) as showlevel,"
					+ "decode(t.realtimeSort,null,9999,t.realtimeSort) as realtimeSort,"
					+ "decode(t.historySort,null,9999,t.historySort) as historySort,"
					+ "t.realtimecurveconf,t.historycurveconf,"
					+ "t.realtimeColor,t.realtimeBgColor,t.historyColor,t.historyBgColor, "
					+ "t.type,"
					+ "t.realtimeOverview,t.realtimeOverviewSort,t.realtimeData, "
					+ "t.historyOverview,t.historyOverviewSort,t.historyData "
					+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2,tbl_protocoldisplayinstance t3 "
					+ " where t.unitid=t2.id and t2.id=t3.displayunitid";
			if(StringManagerUtils.isNotNull(instanceId)){
				instanceSql+=" and t.id="+instanceId;
				sql+=" and t3.id="+instanceId;
			}
			sql+=" order by t3.code, t.unitid,t.id";
			
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				if(jedis.exists("DisplayInstanceOwnItem".getBytes())){
					if(jedis.hexists("DisplayInstanceOwnItem".getBytes(), rs.getString(1).getBytes())){
						jedis.hdel("DisplayInstanceOwnItem".getBytes(), rs.getString(1).getBytes());
					}
				}
			}
			if(pstmt!=null)
        		pstmt.close();  
        	if(rs!=null)
        		rs.close();
        	if(!"delete".equalsIgnoreCase(method)){
        		pstmt = conn.prepareStatement(sql);
    			rs=pstmt.executeQuery();
    			while(rs.next()){
    				DisplayInstanceOwnItem displayInstanceOwnItem=null;
    				try{
    					if(jedis.hexists("DisplayInstanceOwnItem".getBytes(), rs.getString(1).getBytes())){
        					byte[]byt=  jedis.hget("DisplayInstanceOwnItem".getBytes(), rs.getString(1).getBytes());
        					if(byt!=null){
        						Object obj = SerializeObjectUnils.unserizlize(byt);
            					if (obj instanceof DisplayInstanceOwnItem) {
            						displayInstanceOwnItem=(DisplayInstanceOwnItem) obj;
            			        }
        					}
        				}else{
        					displayInstanceOwnItem=new DisplayInstanceOwnItem();
        				}
					}catch(Exception e){
						System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":"+e);
					}
    				if(displayInstanceOwnItem==null){
    					displayInstanceOwnItem=new DisplayInstanceOwnItem();
    				}
    				
    				displayInstanceOwnItem.setInstanceCode(rs.getString(1));
    				displayInstanceOwnItem.setProtocol(rs.getString(2));
    				displayInstanceOwnItem.setUnitId(rs.getInt(3));
    				
    				if(displayInstanceOwnItem.getItemList()==null){
    					displayInstanceOwnItem.setItemList(new ArrayList<DisplayItem>());
    				}
    				DisplayItem displayItem=new DisplayItem();
    				displayItem.setUnitId(rs.getInt(3));
    				displayItem.setItemId(rs.getInt(4));
    				displayItem.setItemName(rs.getString(5)+"");
    				displayItem.setItemCode(rs.getString(6)+"");
    				displayItem.setBitIndex(rs.getInt(7));
    				displayItem.setShowLevel(rs.getInt(8));
    				displayItem.setRealtimeSort(rs.getInt(9));
    				displayItem.setHistorySort(rs.getInt(10));
    				displayItem.setRealtimeCurveConf(rs.getString(11)+"");
    				displayItem.setHistoryCurveConf(rs.getString(12)+"");
    				displayItem.setRealtimeColor(rs.getString(13)+"");
    				displayItem.setRealtimeBgColor(rs.getString(14)+"");
    				displayItem.setHistoryColor(rs.getString(15)+"");
    				displayItem.setHistoryBgColor(rs.getString(16)+"");
    				displayItem.setType(rs.getInt(17));
    				
    				
    				displayItem.setRealtimeOverview(rs.getInt(18));
    				displayItem.setRealtimeOverviewSort(rs.getInt(19));
    				displayItem.setRealtimeData(rs.getInt(20));
    				
    				displayItem.setHistoryOverview(rs.getInt(21));
    				displayItem.setHistoryOverviewSort(rs.getInt(22));
    				displayItem.setHistoryData(rs.getInt(23));
    				
    				int index=-1;
    				for(int i=0;i<displayInstanceOwnItem.getItemList().size();i++){
    					if(displayItem.getItemCode().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(i).getItemCode()) 
    							&& displayItem.getItemName().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(i).getItemName()) 
    							&& displayItem.getType()==displayInstanceOwnItem.getItemList().get(i).getType()
    							&& displayItem.getBitIndex()==displayInstanceOwnItem.getItemList().get(i).getBitIndex()
    							){
    						index=i;
    						break;
    					}
    				}
    				if(index>=0){
    					displayInstanceOwnItem.getItemList().set(index, displayItem);
    				}else{
    					displayInstanceOwnItem.getItemList().add(displayItem);
    				}
    				Collections.sort(displayInstanceOwnItem.getItemList());
    				String key=displayInstanceOwnItem.getInstanceCode();
    				jedis.hset("DisplayInstanceOwnItem".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(displayInstanceOwnItem));//哈希(Hash)
    			}
        	}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadDisplayInstanceOwnItemByUnitId(String unitId,String method){
		try {
			String instanceSql="select t.id from tbl_protocoldisplayinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(unitId)){
				instanceSql+=" and t.displayunitid="+unitId;
			}
			List<Object[]> list=OracleJdbcUtis.query(instanceSql);
			for(Object[] obj:list){
				loadDisplayInstanceOwnItemById(obj[0]+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadDisplayInstanceOwnItemByAcqGroupId(String groupId,String method){
		try {
			String sql="select t4.id "
					+ " from tbl_acq_unit_conf t,tbl_acq_group2unit_conf t2,tbl_acq_group_conf t3,tbl_protocoldisplayinstance t4,tbl_display_unit_conf t5 "
					+ " where t.id=t2.unitid and t2.groupid=t3.id and t5.acqunitid=t.id and t4.displayunitid=t5.id"
					+ " and t3.id="+groupId+"";
			List<Object[]> list=OracleJdbcUtis.query(sql);
			for(Object[] obj:list){
				loadDisplayInstanceOwnItemById(obj[0]+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadDisplayInstanceOwnItemByAcqUnitId(String unitId,String method){
		try {
			String sql="select t2.id "
					+ " from tbl_acq_unit_conf t,tbl_protocoldisplayinstance t2,tbl_display_unit_conf t3 "
					+ " where t3.acqunitid=t.id and t2.displayunitid=t3.id"
					+ " and t.id="+unitId+"";
			List<Object[]> list=OracleJdbcUtis.query(sql);
			for(Object[] obj:list){
				loadDisplayInstanceOwnItemById(obj[0]+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadDisplayInstanceOwnItemByCode(String instanceCode,String method){
		try {
			String instanceSql="select t.id from tbl_protocoldisplayinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(instanceCode)){
				instanceSql+=" and t.code='"+instanceCode+"'";
			}
			List<Object[]> list=OracleJdbcUtis.query(instanceSql);
			for(Object[] obj:list){
				loadDisplayInstanceOwnItemById(obj[0]+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadDisplayInstanceOwnItemByName(String instanceName,String method){
		try {
			String instanceSql="select t.id from tbl_protocoldisplayinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(instanceName)){
				instanceSql+=" and t.name='"+instanceName+"'";
			}
			List<Object[]> list=OracleJdbcUtis.query(instanceSql);
			for(Object[] obj:list){
				loadDisplayInstanceOwnItemById(obj[0]+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadDisplayInstanceOwnItemByProtocolName(String protocolName,String method){
		try {
			String instanceSql="select t.id from tbl_protocoldisplayinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(protocolName)){
				instanceSql+=" and t.displayunitid in( select t2.id from tbl_display_unit_conf t2,tbl_acq_unit_conf t3 where t2.acqunitid=t3.id and t3.protocol='"+protocolName+"' )";
			}
			List<Object[]> list=OracleJdbcUtis.query(instanceSql);
			for(Object[] obj:list){
				loadDisplayInstanceOwnItemById(obj[0]+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static AlarmInstanceOwnItem getAlarmInstanceOwnItemByCode(String instanceCode){
		AlarmInstanceOwnItem alarmInstanceOwnItem=null;
		Jedis jedis=null;
		if(!existsKey("AlarmInstanceOwnItem")){
			MemoryDataManagerTask.loadAlarmInstanceOwnItemById("","update");
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis!=null&&jedis.hexists("AlarmInstanceOwnItem".getBytes(), instanceCode.getBytes())){
				alarmInstanceOwnItem=(AlarmInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AlarmInstanceOwnItem".getBytes(), instanceCode.getBytes()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return alarmInstanceOwnItem;
	}
	
	public static void loadAlarmInstanceOwnItemById(String instanceId,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			String instanceSql="select t.code from tbl_protocolalarminstance t where 1=1 ";
			String sql="select t3.code as instanceCode,t.unitid,t2.protocol,"
					+ " t.id as itemId,t.itemname,t.itemcode,t.itemaddr,t.bitindex,"
					+ " t.value,t.upperlimit,t.lowerlimit,t.hystersis,t.delay,t.retriggerTime,"
					+ " decode(t.alarmsign,0,0,t.alarmlevel) as alarmlevel,t.alarmsign,t.type,"
					+ " t.issendmessage,t.issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_protocolalarminstance t3 "
					+ " where t.unitid=t2.id and t2.id=t3.alarmunitid";
			if(StringManagerUtils.isNotNull(instanceId)){
				instanceSql+=" and t.id="+instanceId;
				sql+=" and t3.id="+instanceId;
			}
			sql+=" order by t3.code, t.unitid,t.id";
			
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				if(jedis.exists("AlarmInstanceOwnItem".getBytes())){
					if(jedis.hexists("AlarmInstanceOwnItem".getBytes(), rs.getString(1).getBytes())){
						jedis.hdel("AlarmInstanceOwnItem".getBytes(), rs.getString(1).getBytes());
					}
				}
			}
			if(!"delete".equalsIgnoreCase(method)){
				if(pstmt!=null)
	        		pstmt.close();  
	        	if(rs!=null)
	        		rs.close();
				pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					AlarmInstanceOwnItem alarmInstanceOwnItem=null;
					try{
						if(jedis.hexists("AlarmInstanceOwnItem".getBytes(), rs.getString(1).getBytes())){
							byte[]byt=  jedis.hget("AlarmInstanceOwnItem".getBytes(), rs.getString(1).getBytes());
							if(byt!=null){
								Object obj = SerializeObjectUnils.unserizlize(byt);
								if (obj instanceof AlarmInstanceOwnItem) {
									alarmInstanceOwnItem=(AlarmInstanceOwnItem) obj;
						        }
							}
						}else{
							alarmInstanceOwnItem=new AlarmInstanceOwnItem();
						}
					}catch(Exception e){
						System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":"+e);
					}
					if(alarmInstanceOwnItem==null){
						alarmInstanceOwnItem=new AlarmInstanceOwnItem();
					}
					
					alarmInstanceOwnItem.setInstanceCode(rs.getString(1));
					alarmInstanceOwnItem.setUnitId(rs.getInt(2));
					alarmInstanceOwnItem.setProtocol(rs.getString(3));
					
					if(alarmInstanceOwnItem.getItemList()==null){
						alarmInstanceOwnItem.setItemList(new ArrayList<AlarmItem>());
					}
					AlarmItem alarmItem=new AlarmItem();
					alarmItem.setUnitId(rs.getInt(2));
					alarmItem.setItemId(rs.getInt(4));
					alarmItem.setItemName(rs.getString(5));
					alarmItem.setItemCode(rs.getString(6));
					alarmItem.setItemAddr(rs.getInt(7));
					alarmItem.setBitIndex(rs.getInt(8));
					
					alarmItem.setValue(rs.getFloat(9));
					alarmItem.setUpperLimit(rs.getFloat(10));
					alarmItem.setLowerLimit(rs.getFloat(11));
					alarmItem.setHystersis(rs.getFloat(12));
					alarmItem.setDelay(rs.getInt(13));
					alarmItem.setRetriggerTime(rs.getInt(14));
					
					alarmItem.setAlarmLevel(rs.getInt(15));
					alarmItem.setAlarmSign(rs.getInt(16));
					
					alarmItem.setType(rs.getInt(17));

					alarmItem.setIsSendMessage(rs.getInt(18));
					alarmItem.setIsSendMail(rs.getInt(19));
					
					int index=-1;
					for(int i=0;i<alarmInstanceOwnItem.getItemList().size();i++){
						if(alarmItem.getItemId()==alarmInstanceOwnItem.getItemList().get(i).getItemId()){
							index=i;
							break;
						}
					}
					if(index>=0){
						alarmInstanceOwnItem.getItemList().set(index, alarmItem);
					}else{
						alarmInstanceOwnItem.getItemList().add(alarmItem);
					}
					
					String key=alarmInstanceOwnItem.getInstanceCode();
					jedis.hset("AlarmInstanceOwnItem".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(alarmInstanceOwnItem));//哈希(Hash)
					
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static void loadAlarmInstanceOwnItemByCode(String instanceCode,String method){
		try {
			String instanceSql="select t.id from tbl_protocolalarminstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(instanceCode)){
				instanceSql+=" and t.code='"+instanceCode+"'";
			}
			List<Object[]> list=OracleJdbcUtis.query(instanceSql);
			for(Object[] obj:list){
				loadAlarmInstanceOwnItemById(obj[0]+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadAlarmInstanceOwnItemByName(String instanceName,String method){
		try {
			String instanceSql="select t.id from tbl_protocolalarminstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(instanceName)){
				instanceSql+=" and t.name='"+instanceName+"'";
			}
			List<Object[]> list=OracleJdbcUtis.query(instanceSql);
			for(Object[] obj:list){
				loadAlarmInstanceOwnItemById(obj[0]+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadAlarmInstanceOwnItemByUnitId(String unitId,String method){
		try {
			String instanceSql="select t.id from tbl_protocolalarminstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(unitId)){
				instanceSql+=" and t.alarmunitid="+unitId;
			}
			List<Object[]> list=OracleJdbcUtis.query(instanceSql);
			for(Object[] obj:list){
				loadAlarmInstanceOwnItemById(obj[0]+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadAlarmInstanceOwnItemByProtocolName(String protocolName,String method){
		try {
			String instanceSql="select t.id from tbl_protocolalarminstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(protocolName)){
				instanceSql+=" and t.alarmunitid in (select t2.id from tbl_alarm_unit_conf t2 where t2.protocol='"+protocolName+"')";
			}
			List<Object[]> list=OracleJdbcUtis.query(instanceSql);
			for(Object[] obj:list){
				loadAlarmInstanceOwnItemById(obj[0]+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getCalItemNameByCode(String oldName,String code,String language){
		String name="";
		CalItem c=getCalItemByCode(code,language);
		if(c!=null){
			name=c.getName();
		}else{
			name=oldName;
		}
		return name;
	}
	
	public static CalItem getCalItemByCode(String code,String language){
		CalItem c=null;
		List<CalItem> list= getAcqCalculateItem(language);
		c=getSingleCalItemByCode(code,list);
		if(c==null){
			list= getSRPCalculateItem(language);
			c=getSingleCalItemByCode(code,list);
		}
		if(c==null){
			list= getPCPCalculateItem(language);
			c=getSingleCalItemByCode(code,list);
		}
		return c;
	}
	
	public static CalItem getCalItemByNameAndUnit(String name,String unit,String language){
		CalItem c=null;
		List<CalItem> list= getAcqCalculateItem(language);
		c=getSingleCalItemByNameAndUnit(name,unit,list);
		if(c==null){
			list= getSRPCalculateItem(language);
			c=getSingleCalItemByNameAndUnit(name,unit,list);
		}
		if(c==null){
			list= getPCPCalculateItem(language);
			c=getSingleCalItemByNameAndUnit(name,unit,list);
		}
		return c;
	}
	
	public static String calItemLanguageSwitchover(String name,String language){
		String r="";
		int type=0;
		String code="";
		if(languageList!=null && languageList.size()>0){
			for(String l:languageList){
				List<CalItem> list= getAcqCalculateItem(l);
				CalItem c=getSingleCalItem(name,list);
				if(c!=null){
					type=1;
					code=c.getCode();
					break;
				}
				
				list= getSRPCalculateItem(l);
				c=getSingleCalItem(name,list);
				if(c!=null){
					type=2;
					code=c.getCode();
					break;
				}
				
				list= getPCPCalculateItem(l);
				c=getSingleCalItem(name,list);
				if(c!=null){
					type=3;
					code=c.getCode();
					break;
				}
			}
		}
		if(type>0){
			if(type==1){
				List<CalItem> list= getAcqCalculateItem(language);
				CalItem c=getSingleCalItemByCode(code,list);
				if(c!=null){
					r=c.getName();
				}
			}else if(type==2){
				List<CalItem> list= getSRPCalculateItem(language);
				CalItem c=getSingleCalItemByCode(code,list);
				if(c!=null){
					r=c.getName();
				}
			}else if(type==3){
				List<CalItem> list= getPCPCalculateItem(language);
				CalItem c=getSingleCalItemByCode(code,list);
				if(c!=null){
					r=c.getName();
				}
			}
		}else{
			r=name;
		}
		return r;
	}
	
	public static CalItem getSingleCalItemByCode(String code,List<CalItem> list){
		CalItem item=null;
		if(list!=null){
			for(CalItem calItem:list){
				if(code.equalsIgnoreCase(calItem.getCode())){
					item=calItem;
					break;
				}
			}
		}
		return item;
	}
	
	public static CalItem getSingleCalItem(String name,List<CalItem> list){
		CalItem item=null;
		if(list!=null){
			for(CalItem calItem:list){
				if(name.equalsIgnoreCase(calItem.getName())){
					item=calItem;
					break;
				}
			}
		}
		return item;
	}
	
	public static CalItem getSingleCalItemByNameAndUnit(String name,String unit,List<CalItem> list){
		CalItem item=null;
		if(list!=null && name!=null){
			for(CalItem calItem:list){
				if(StringManagerUtils.isNotNull(unit)){
					if(name.equalsIgnoreCase(calItem.getName()) && unit.equalsIgnoreCase(calItem.getUnit())){
						item=calItem;
						break;
					}
				}else{
					if(name.equalsIgnoreCase(calItem.getName())){
						item=calItem;
						break;
					}
				}
				
			}
		}
		return item;
	}
	
	public static List<CalItem> getAcqCalculateItem(String language){
		Jedis jedis=null;
		List<CalItem> calItemList=new ArrayList<>();
		String key="acqCalItemList-"+language;
		if(!existsKey(key)){
			MemoryDataManagerTask.loadAcqCalculateItem(language);
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			List<byte[]> calItemSet= jedis.zrange(key.getBytes(), 0, -1);
			for(byte[] srpCalItemByteArr:calItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(srpCalItemByteArr);
				calItemList.add(calItem);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return calItemList;
	}
	
	public static void loadAcqCalculateItem(String language){
		Jedis jedis=null;
		String key="acqCalItemList-"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
			}
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTime"),"CommTime","h",2,1,languageResourceMap.get("commTime"))));
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTimeEfficiency"),"CommTimeEfficiency",timeEfficiencyUnit,2,1,languageResourceMap.get("commTimeEfficiency"))));
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commRange"),"CommRange","",1,1,languageResourceMap.get("commRange"))));
			
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runStatus"),"RunStatusName","",2,1,languageResourceMap.get("runStatus"))));
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTime"),"RunTime","h",2,1,languageResourceMap.get("runTime"))));
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTimeEfficiency"),"RunTimeEfficiency",timeEfficiencyUnit,2,1,languageResourceMap.get("runTimeEfficiency"))));
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runRange"),"RunRange","",1,1,languageResourceMap.get("runRange"))));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static List<CalItem> getSRPCalculateItem(String language){
		Jedis jedis=null;
		String key="srpCalItemList-"+language;
		List<CalItem> calItemList=new ArrayList<>();
		if(!existsKey(key)){
			MemoryDataManagerTask.loadSRPCalculateItem(language);
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			List<byte[]> calItemSet= jedis.zrange(key.getBytes(), 0, -1);
			for(byte[] srpCalItemByteArr:calItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(srpCalItemByteArr);
				calItemList.add(calItem);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return calItemList;
	}
	
	public static void loadSRPCalculateItem(String language){
		Jedis jedis=null;
		String key="srpCalItemList-"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
			}
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTime"),"CommTime","h",2,1,languageResourceMap.get("commTime"))));
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTimeEfficiency"),"CommTimeEfficiency",timeEfficiencyUnit,2,1,languageResourceMap.get("commTimeEfficiency"))));
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commRange"),"CommRange","",1,1,languageResourceMap.get("commRange"))));
			
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runStatus"),"RunStatusName","",2,1,languageResourceMap.get("runStatus"))));
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTime"),"RunTime","h",2,1,languageResourceMap.get("runTime"))));
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTimeEfficiency"),"RunTimeEfficiency",timeEfficiencyUnit,2,1,languageResourceMap.get("runTimeEfficiency"))));
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runRange"),"RunRange","",1,1,languageResourceMap.get("runRange"))));
			
			
			jedis.zadd(key.getBytes(),8, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("resultName"),"ResultName","",1,1,languageResourceMap.get("resultName"))));
			jedis.zadd(key.getBytes(),9, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("fMax"),"FMax","kN",2,1,languageResourceMap.get("fMax"))));
			jedis.zadd(key.getBytes(),10, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("fMin"),"FMin","kN",2,1,languageResourceMap.get("fMin"))));
			
			jedis.zadd(key.getBytes(),11, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("calculateStroke"),"Stroke","",2,1,languageResourceMap.get("calculateStroke"))));
			jedis.zadd(key.getBytes(),12, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("plungerStroke"),"PlungerStroke","m",2,1,languageResourceMap.get("plungerStroke"))));
			jedis.zadd(key.getBytes(),13, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("availablePlungerStroke"),"AvailablePlungerStroke","m",2,1,languageResourceMap.get("availablePlungerStroke"))));
			jedis.zadd(key.getBytes(),14, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("noLiquidAvailablePlungerStroke"),"NoLiquidAvailablePlungerStroke","m",2,1,languageResourceMap.get("noLiquidAvailablePlungerStroke"))));
			
			jedis.zadd(key.getBytes(),15, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("fullnessCoefficient"),"FullnessCoefficient","",2,1,languageResourceMap.get("fullnessCoefficient"))));
			jedis.zadd(key.getBytes(),16, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("noLiquidFullnessCoefficient"),"NoLiquidFullnessCoefficient","",2,1,languageResourceMap.get("noLiquidFullnessCoefficient"))));
			
			jedis.zadd(key.getBytes(),17, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("upperLoadLine"),"UpperLoadLine","kN",2,1,languageResourceMap.get("upperLoadLine"))));
			jedis.zadd(key.getBytes(),18, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("lowerLoadLine"),"LowerLoadLine","kN",2,1,languageResourceMap.get("lowerLoadLine"))));
			jedis.zadd(key.getBytes(),19, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("upperLoadLineOfExact"),"UpperLoadLineOfExact","kN",2,1,languageResourceMap.get("upperLoadLineOfExact"))));
			
			
			jedis.zadd(key.getBytes(),20, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("theoreticalProduction"),"TheoreticalProduction","m^3/d",2,1,languageResourceMap.get("theoreticalProduction"))));
			
			jedis.zadd(key.getBytes(),21, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidVolumetricProduction"),"LiquidVolumetricProduction","m^3/d",2,1,languageResourceMap.get("liquidVolumetricProduction"))));
			jedis.zadd(key.getBytes(),22, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilVolumetricProduction"),"OilVolumetricProduction","m^3/d",2,1,languageResourceMap.get("oilVolumetricProduction"))));
			jedis.zadd(key.getBytes(),23, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterVolumetricProduction"),"WaterVolumetricProduction","m^3/d",2,1,languageResourceMap.get("waterVolumetricProduction"))));
			jedis.zadd(key.getBytes(),24, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("availablePlungerStrokeProd_v"),"AvailablePlungerStrokeProd_v","m^3/d",2,1,languageResourceMap.get("availablePlungerStrokeProd_v"))));
			jedis.zadd(key.getBytes(),25, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpClearanceleakProd_v"),"PumpClearanceleakProd_v","m^3/d",2,1,languageResourceMap.get("pumpClearanceleakProd_v"))));
			jedis.zadd(key.getBytes(),26, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("TVLeakVolumetricProduction"),"TVLeakVolumetricProduction","m^3/d",2,1,languageResourceMap.get("TVLeakVolumetricProduction"))));
			jedis.zadd(key.getBytes(),27, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("SVLeakVolumetricProduction"),"SVLeakVolumetricProduction","m^3/d",2,1,languageResourceMap.get("SVLeakVolumetricProduction"))));
			jedis.zadd(key.getBytes(),28, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("gasInfluenceProd_v"),"GasInfluenceProd_v","m^3/d",2,1,languageResourceMap.get("gasInfluenceProd_v"))));
			jedis.zadd(key.getBytes(),29, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidVolumetricProduction_l"),"LiquidVolumetricProduction_l","m^3/d",2,1,languageResourceMap.get("liquidVolumetricProduction_l"))));
			jedis.zadd(key.getBytes(),30, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilVolumetricProduction_l"),"OilVolumetricProduction_l","m^3/d",2,1,languageResourceMap.get("oilVolumetricProduction_l"))));
			jedis.zadd(key.getBytes(),31, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterVolumetricProduction_l"),"WaterVolumetricProduction_l","m^3/d",2,1,languageResourceMap.get("waterVolumetricProduction_l"))));
			
			jedis.zadd(key.getBytes(),32, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidWeightProduction"),"LiquidWeightProduction","t/d",2,1,languageResourceMap.get("liquidWeightProduction"))));
			jedis.zadd(key.getBytes(),33, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilWeightProduction"),"OilWeightProduction","t/d",2,1,languageResourceMap.get("oilWeightProduction"))));
			jedis.zadd(key.getBytes(),34, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterWeightProduction"),"WaterWeightProduction","t/d",2,1,languageResourceMap.get("waterWeightProduction"))));
			jedis.zadd(key.getBytes(),35, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("availablePlungerStrokeProd_w"),"AvailablePlungerStrokeProd_w","t/d",2,1,languageResourceMap.get("availablePlungerStrokeProd_w"))));
			jedis.zadd(key.getBytes(),36, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpClearanceleakProd_w"),"PumpClearanceleakProd_w","t/d",2,1,languageResourceMap.get("pumpClearanceleakProd_w"))));
			jedis.zadd(key.getBytes(),37, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("TVLeakWeightProduction"),"TVLeakWeightProduction","t/d",2,1,languageResourceMap.get("TVLeakWeightProduction"))));
			jedis.zadd(key.getBytes(),38, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("SVLeakWeightProduction"),"SVLeakWeightProduction","t/d",2,1,languageResourceMap.get("SVLeakWeightProduction"))));
			jedis.zadd(key.getBytes(),39, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("gasInfluenceProd_w"),"GasInfluenceProd_w","t/d",2,1,languageResourceMap.get("gasInfluenceProd_w"))));
			jedis.zadd(key.getBytes(),40, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidWeightProduction_l"),"LiquidWeightProduction_l","t/d",2,1,languageResourceMap.get("liquidWeightProduction_l"))));
			jedis.zadd(key.getBytes(),41, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilWeightProduction_l"),"OilWeightProduction_l","t/d",2,1,languageResourceMap.get("oilWeightProduction_l"))));
			jedis.zadd(key.getBytes(),42, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterWeightProduction_l"),"WaterWeightProduction_l","t/d",2,1,languageResourceMap.get("waterWeightProduction_l"))));
			
			jedis.zadd(key.getBytes(),43, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("averageWatt"),"AverageWatt","kW",2,1,languageResourceMap.get("averageWatt"))));
			jedis.zadd(key.getBytes(),44, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("polishRodPower"),"PolishRodPower","kW",2,1,languageResourceMap.get("polishRodPower"))));
			jedis.zadd(key.getBytes(),45, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterPower"),"WaterPower","kW",2,1,languageResourceMap.get("waterPower"))));
			
			jedis.zadd(key.getBytes(),46, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("surfaceSystemEfficiency"),"SurfaceSystemEfficiency","",2,1,languageResourceMap.get("surfaceSystemEfficiency"))));
			jedis.zadd(key.getBytes(),47, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("wellDownSystemEfficiency"),"WellDownSystemEfficiency","",2,1,languageResourceMap.get("wellDownSystemEfficiency"))));
			jedis.zadd(key.getBytes(),48, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("systemEfficiency"),"SystemEfficiency","",2,1,languageResourceMap.get("systemEfficiency"))));
			jedis.zadd(key.getBytes(),49, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("area"),"Area","",2,1,languageResourceMap.get("area"))));
			jedis.zadd(key.getBytes(),50, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("energyPer100mLift"),"EnergyPer100mLift","kW· h/100m· t",2,1,languageResourceMap.get("energyPer100mLift"))));
			
			
			jedis.zadd(key.getBytes(),51, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("rodFlexLength"),"RodFlexLength","m",2,1,languageResourceMap.get("rodFlexLength"))));
			jedis.zadd(key.getBytes(),52, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("tubingFlexLength"),"TubingFlexLength","m",2,1,languageResourceMap.get("tubingFlexLength"))));
			jedis.zadd(key.getBytes(),53, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("inertiaLength"),"InertiaLength","m",2,1,languageResourceMap.get("inertiaLength"))));
			jedis.zadd(key.getBytes(),54, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff1"),"PumpEff1","",2,1,languageResourceMap.get("pumpEff1"))));
			jedis.zadd(key.getBytes(),55, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff2"),"PumpEff2","",2,1,languageResourceMap.get("pumpEff2"))));
			jedis.zadd(key.getBytes(),56, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff3"),"PumpEff3","",2,1,languageResourceMap.get("pumpEff3"))));
			jedis.zadd(key.getBytes(),57, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff4"),"PumpEff4","",2,1,languageResourceMap.get("pumpEff4"))));
			jedis.zadd(key.getBytes(),58, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff"),"PumpEff","",2,1,languageResourceMap.get("pumpEff"))));
			
			jedis.zadd(key.getBytes(),59, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpIntakeP"),"PumpIntakeP","MPa",2,1,languageResourceMap.get("pumpIntakeP"))));
			jedis.zadd(key.getBytes(),60, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpIntakeT"),"PumpIntakeT","℃",2,1,languageResourceMap.get("pumpIntakeT"))));
			jedis.zadd(key.getBytes(),61, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpIntakeGOL"),"PumpIntakeGOL","m^3/m^3",2,1,languageResourceMap.get("pumpIntakeGOL"))));
			jedis.zadd(key.getBytes(),62, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpIntakeVisl"),"PumpIntakeVisl","mPa·s",2,1,languageResourceMap.get("pumpIntakeVisl"))));
			jedis.zadd(key.getBytes(),63, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpIntakeBo"),"PumpIntakeBo","",2,1,languageResourceMap.get("pumpIntakeBo"))));
			
			jedis.zadd(key.getBytes(),64, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpOutletP"),"PumpOutletP","MPa",2,1,languageResourceMap.get("pumpOutletP"))));
			jedis.zadd(key.getBytes(),65, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpOutletT"),"PumpOutletT","℃",2,1,languageResourceMap.get("pumpOutletT"))));
			jedis.zadd(key.getBytes(),66, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpOutletGOL"),"PumpOutletGOL","m^3/m^3",2,1,languageResourceMap.get("pumpOutletGOL"))));
			jedis.zadd(key.getBytes(),67, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpOutletVisl"),"PumpOutletVisl","mPa·s",2,1,languageResourceMap.get("pumpOutletVisl"))));
			jedis.zadd(key.getBytes(),68, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpOutletBo"),"PumpOutletBo","",2,1,languageResourceMap.get("pumpOutletBo"))));
			
			jedis.zadd(key.getBytes(),69, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("upStrokeIMax"),"UpStrokeIMax","A",2,1,languageResourceMap.get("upStrokeIMax"))));
			jedis.zadd(key.getBytes(),70, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("downStrokeIMax"),"DownStrokeIMax","A",2,1,languageResourceMap.get("downStrokeIMax"))));
			jedis.zadd(key.getBytes(),71, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("upStrokeWattMax"),"UpStrokeWattMax","kW",2,1,languageResourceMap.get("upStrokeWattMax"))));
			jedis.zadd(key.getBytes(),72, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("downStrokeWattMax"),"DownStrokeWattMax","kW",2,1,languageResourceMap.get("downStrokeWattMax"))));
			jedis.zadd(key.getBytes(),73, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("iDegreeBalance"),"IDegreeBalance","%",2,1,languageResourceMap.get("iDegreeBalance"))));
			jedis.zadd(key.getBytes(),74, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("wattDegreeBalance"),"WattDegreeBalance","%",2,1,languageResourceMap.get("wattDegreeBalance"))));
			jedis.zadd(key.getBytes(),75, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("deltaRadius"),"DeltaRadius","m",2,1,languageResourceMap.get("deltaRadius"))));
			
			jedis.zadd(key.getBytes(),76, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("levelDifferenceValue"),"LevelDifferenceValue","MPa",2,1,languageResourceMap.get("levelDifferenceValue"))));
			jedis.zadd(key.getBytes(),77, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("calcProducingfluidLevel"),"CalcProducingfluidLevel","m",2,1,languageResourceMap.get("calcProducingfluidLevel"))));
			
			jedis.zadd(key.getBytes(),78, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("submergence"),"Submergence","m",2,1,languageResourceMap.get("submergence"))));
			
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static List<CalItem> getPCPCalculateItem(String language){
		Jedis jedis=null;
		List<CalItem> calItemList=new ArrayList<>();
		String key="pcpCalItemList-"+language;
		if(!existsKey(key)){
			MemoryDataManagerTask.loadPCPCalculateItem(language);
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			List<byte[]> calItemSet= jedis.zrange(key.getBytes(), 0, -1);
			for(byte[] srpCalItemByteArr:calItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(srpCalItemByteArr);
				calItemList.add(calItem);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return calItemList;
	}
	
	public static void loadPCPCalculateItem(String language){
		Jedis jedis=null;
		String key="pcpCalItemList-"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
			}
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTime"),"CommTime","h",2,1,languageResourceMap.get("commTime"))));
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTimeEfficiency"),"CommTimeEfficiency",timeEfficiencyUnit,2,1,languageResourceMap.get("commTimeEfficiency"))));
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commRange"),"CommRange","",1,1,languageResourceMap.get("commRange"))));
			
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runStatus"),"RunStatusName","",2,1,languageResourceMap.get("runStatus"))));
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTime"),"RunTime","h",2,1,languageResourceMap.get("runTime"))));
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTimeEfficiency"),"RunTimeEfficiency",timeEfficiencyUnit,2,1,languageResourceMap.get("runTimeEfficiency"))));
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runRange"),"RunRange","",1,1,languageResourceMap.get("runRange"))));
			
			jedis.zadd(key.getBytes(),8, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("theoreticalProduction"),"TheoreticalProduction","m^3/d",2,1,languageResourceMap.get("theoreticalProduction"))));
			
			jedis.zadd(key.getBytes(),9, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidVolumetricProduction"),"LiquidVolumetricProduction","m^3/d",2,1,languageResourceMap.get("liquidVolumetricProduction"))));
			jedis.zadd(key.getBytes(),10, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilVolumetricProduction"),"OilVolumetricProduction","m^3/d",2,1,languageResourceMap.get("oilVolumetricProduction"))));
			jedis.zadd(key.getBytes(),11, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterVolumetricProduction"),"WaterVolumetricProduction","m^3/d",2,1,languageResourceMap.get("waterVolumetricProduction"))));
			jedis.zadd(key.getBytes(),12, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidVolumetricProduction_l"),"LiquidVolumetricProduction_l","m^3/d",2,1,languageResourceMap.get("liquidVolumetricProduction_l"))));
			jedis.zadd(key.getBytes(),13, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilVolumetricProduction_l"),"OilVolumetricProduction_l","m^3/d",2,1,languageResourceMap.get("oilVolumetricProduction_l"))));
			jedis.zadd(key.getBytes(),14, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterVolumetricProduction_l"),"WaterVolumetricProduction_l","m^3/d",2,1,languageResourceMap.get("waterVolumetricProduction_l"))));
			
			jedis.zadd(key.getBytes(),15, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidWeightProduction"),"LiquidWeightProduction","t/d",2,1,languageResourceMap.get("liquidWeightProduction"))));
			jedis.zadd(key.getBytes(),16, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilWeightProduction"),"OilWeightProduction","t/d",2,1,languageResourceMap.get("oilWeightProduction"))));
			jedis.zadd(key.getBytes(),17, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterWeightProduction"),"WaterWeightProduction","t/d",2,1,languageResourceMap.get("waterWeightProduction"))));
			jedis.zadd(key.getBytes(),18, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidWeightProduction_l"),"LiquidWeightProduction_l","t/d",2,1,languageResourceMap.get("liquidWeightProduction_l"))));
			jedis.zadd(key.getBytes(),19, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilWeightProduction_l"),"OilWeightProduction_l","t/d",2,1,languageResourceMap.get("oilWeightProduction_l"))));
			jedis.zadd(key.getBytes(),20, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterWeightProduction_l"),"WaterWeightProduction_l","t/d",2,1,languageResourceMap.get("waterWeightProduction_l"))));
			
			jedis.zadd(key.getBytes(),21, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("averageWatt"),"AverageWatt","kW",2,1,languageResourceMap.get("averageWatt"))));
			jedis.zadd(key.getBytes(),22, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterPower"),"WaterPower","kW",2,1,languageResourceMap.get("waterPower"))));
			jedis.zadd(key.getBytes(),23, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("systemEfficiency"),"SystemEfficiency","",2,1,languageResourceMap.get("systemEfficiency"))));
			
			jedis.zadd(key.getBytes(),24, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff1_pcp"),"PumpEff1","",2,1,languageResourceMap.get("pumpEff1_pcp"))));
			jedis.zadd(key.getBytes(),25, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff2_pcp"),"PumpEff2","",2,1,languageResourceMap.get("pumpEff2_pcp"))));
			jedis.zadd(key.getBytes(),26, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff"),"PumpEff","",2,1,languageResourceMap.get("pumpEff"))));
			
			jedis.zadd(key.getBytes(),27, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpIntakeP"),"PumpIntakeP","MPa",2,1,languageResourceMap.get("pumpIntakeP"))));
			jedis.zadd(key.getBytes(),28, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpIntakeT"),"PumpIntakeT","℃",2,1,languageResourceMap.get("pumpIntakeT"))));
			jedis.zadd(key.getBytes(),29, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpIntakeGOL"),"PumpIntakeGOL","m^3/m^3",2,1,languageResourceMap.get("pumpIntakeGOL"))));
			jedis.zadd(key.getBytes(),30, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpIntakeVisl"),"PumpIntakeVisl","mPa·s",2,1,languageResourceMap.get("pumpIntakeVisl"))));
			jedis.zadd(key.getBytes(),31, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpIntakeBo"),"PumpIntakeBo","",2,1,languageResourceMap.get("pumpIntakeBo"))));
			
			jedis.zadd(key.getBytes(),32, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpOutletP"),"PumpOutletP","MPa",2,1,languageResourceMap.get("pumpOutletP"))));
			jedis.zadd(key.getBytes(),33, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpOutletT"),"PumpOutletT","℃",2,1,languageResourceMap.get("pumpOutletT"))));
			jedis.zadd(key.getBytes(),34, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpOutletGOL"),"PumpOutletGOL","m^3/m^3",2,1,languageResourceMap.get("pumpOutletGOL"))));
			jedis.zadd(key.getBytes(),35, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpOutletVisl"),"PumpOutletVisl","mPa·s",2,1,languageResourceMap.get("pumpOutletVisl"))));
			jedis.zadd(key.getBytes(),36, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpOutletBo"),"PumpOutletBo","",2,1,languageResourceMap.get("pumpOutletBo"))));
			
//			jedis.zadd(key.getBytes(),36, SerializeObjectUnils.serialize(new CalItem("日用电量","TodayKWattH","kW·h",2,1,"日用电量")));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static String getTotalCalItemNameByCode(String oldName,String code,String language){
		String name="";
		CalItem c=getTotalCalItemByCode(code,language);
		if(c!=null){
			name=c.getName();
		}else{
			name=oldName;
		}
		return name;
	}
	
	public static CalItem getTotalCalItemByCode(String code,String language){
		CalItem c=null;
		List<CalItem> list= getAcqTotalCalculateItem(language);
		c=getSingleCalItemByCode(code,list);
		if(c==null){
			list= getSRPTotalCalculateItem(language);
			c=getSingleCalItemByCode(code,list);
		}
		if(c==null){
			list= getPCPTotalCalculateItem(language);
			c=getSingleCalItemByCode(code,list);
		}
		return c;
	}
	
	public static String totalCalItemLanguageSwitchover(String name,String language){
		String r="";
		int type=0;
		String code="";
		if(languageList!=null && languageList.size()>0){
			for(String l:languageList){
				List<CalItem> list= getAcqTotalCalculateItem(l);
				CalItem c=getSingleCalItem(name,list);
				if(c!=null){
					type=1;
					code=c.getCode();
					break;
				}
				
				list= getSRPTotalCalculateItem(l);
				c=getSingleCalItem(name,list);
				if(c!=null){
					type=2;
					code=c.getCode();
					break;
				}
				
				list= getPCPTotalCalculateItem(l);
				c=getSingleCalItem(name,list);
				if(c!=null){
					type=3;
					code=c.getCode();
					break;
				}
			}
		}
		if(type>0){
			if(type==1){
				List<CalItem> list= getAcqTotalCalculateItem(language);
				CalItem c=getSingleCalItemByCode(code,list);
				if(c!=null){
					r=c.getName();
				}
			}else if(type==2){
				List<CalItem> list= getSRPTotalCalculateItem(language);
				CalItem c=getSingleCalItemByCode(code,list);
				if(c!=null){
					r=c.getName();
				}
			}else if(type==3){
				List<CalItem> list= getPCPTotalCalculateItem(language);
				CalItem c=getSingleCalItemByCode(code,list);
				if(c!=null){
					r=c.getName();
				}
			}
		}else{
			r=name;
		}
		return r;
	}
	
	public static List<CalItem> getAcqTotalCalculateItem(String language){
		Jedis jedis=null;
		List<CalItem> calItemList=new ArrayList<>();
		String key="acqTotalCalItemList-"+language;
		if(!existsKey(key)){
			MemoryDataManagerTask.loadAcqTotalCalculateItem(language);
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			List<byte[]> calItemSet= jedis.zrange(key.getBytes(), 0, -1);
			for(byte[] calItemByteArr:calItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(calItemByteArr);
				calItemList.add(calItem);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return calItemList;
	}
	
	public static void loadAcqTotalCalculateItem(String language){
		Jedis jedis=null;
		String key="acqTotalCalItemList-"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
			}
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("deviceName"),"DeviceName","",1,2,languageResourceMap.get("deviceName"))));//1-字符串 2-数值 3-日期 4-日期时间
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("date"),"CalDate","",3,1,languageResourceMap.get("date"))));
			
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTime"),"CommTime","h",2,1,languageResourceMap.get("commTime"))));
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTimeEfficiency"),"CommTimeEfficiency",timeEfficiencyUnit,2,1,languageResourceMap.get("commTimeEfficiency"))));
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commRange"),"CommRange","",1,1,languageResourceMap.get("commRange"))));
			
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTime"),"RunTime","h",2,1,languageResourceMap.get("runTime"))));
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTimeEfficiency"),"RunTimeEfficiency",timeEfficiencyUnit,2,1,languageResourceMap.get("runTimeEfficiency"))));
			jedis.zadd(key.getBytes(),8, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runRange"),"RunRange","",1,1,languageResourceMap.get("runRange"))));
			
			jedis.zadd(key.getBytes(),9, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("remark"),"Remark","",1,2,languageResourceMap.get("remark"))));
			
			jedis.zadd(key.getBytes(),10, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservedCol1"),"reservedcol1","",1,2,languageResourceMap.get("reservedCol1"))));
			jedis.zadd(key.getBytes(),11, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservedCol2"),"reservedcol2","",1,2,languageResourceMap.get("reservedCol2"))));
			jedis.zadd(key.getBytes(),12, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservedCol3"),"reservedcol3","",1,2,languageResourceMap.get("reservedCol3"))));
			jedis.zadd(key.getBytes(),13, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservedCol4"),"reservedcol4","",1,2,languageResourceMap.get("reservedCol4"))));
			jedis.zadd(key.getBytes(),14, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservedCol5"),"reservedcol5","",1,2,languageResourceMap.get("reservedCol5"))));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static List<CalItem> getSRPTotalCalculateItem(String language){
		Jedis jedis=null;
		List<CalItem> calItemList=new ArrayList<>();
		String key="srpTotalCalItemList-"+language;
		if(!existsKey(key)){
			MemoryDataManagerTask.loadSRPTotalCalculateItem(language);
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			List<byte[]> calItemSet= jedis.zrange(key.getBytes(), 0, -1);
			for(byte[] calItemByteArr:calItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(calItemByteArr);
				calItemList.add(calItem);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return calItemList;
	}
	
	public static void loadSRPTotalCalculateItem(String language){
		Jedis jedis=null;
		String key="srpTotalCalItemList-"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
			}
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("deviceName"),"DeviceName","",1,2,languageResourceMap.get("deviceName"))));//1-字符串 2-数值 3-日期 4-日期时间
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("date"),"CalDate","",3,1,languageResourceMap.get("date"))));
			
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTime"),"CommTime","h",2,1,languageResourceMap.get("commTime"))));
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTimeEfficiency"),"CommTimeEfficiency",timeEfficiencyUnit,2,1,languageResourceMap.get("commTimeEfficiency"))));
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commRange"),"CommRange","",1,1,languageResourceMap.get("commRange"))));
			
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTime"),"RunTime","h",2,1,languageResourceMap.get("runTime"))));
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTimeEfficiency"),"RunTimeEfficiency",timeEfficiencyUnit,2,1,languageResourceMap.get("runTimeEfficiency"))));
			jedis.zadd(key.getBytes(),8, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runRange"),"RunRange","",1,1,languageResourceMap.get("runRange"))));
			
			jedis.zadd(key.getBytes(),9, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("resultName"),"ResultName","",1,1,languageResourceMap.get("resultName"))));
			jedis.zadd(key.getBytes(),10, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("optimizationSuggestion"),"OptimizationSuggestion","",1,1,languageResourceMap.get("optimizationSuggestion"))));
			jedis.zadd(key.getBytes(),11, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("stroke"),"Stroke","m",2,1,languageResourceMap.get("stroke"))));
			jedis.zadd(key.getBytes(),12, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("SPM"),"SPM","1/min",2,1,languageResourceMap.get("SPM"))));
			jedis.zadd(key.getBytes(),13, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("fMax"),"FMax","kN",2,1,languageResourceMap.get("fMax"))));
			jedis.zadd(key.getBytes(),14, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("fMin"),"FMin","kN",2,1,languageResourceMap.get("fMin"))));
			
			jedis.zadd(key.getBytes(),15, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("fullnessCoefficient"),"FullnessCoefficient","",2,1,languageResourceMap.get("fullnessCoefficient"))));
			
			jedis.zadd(key.getBytes(),16, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("theoreticalProduction"),"TheoreticalProduction","m^3/d",2,1,languageResourceMap.get("theoreticalProduction"))));
			
			jedis.zadd(key.getBytes(),17, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidVolumetricProduction_l"),"LiquidVolumetricProduction","m^3/d",2,1,"日累计产液量,功图汇总计算所得")));
			jedis.zadd(key.getBytes(),18, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilVolumetricProduction_l"),"OilVolumetricProduction","m^3/d",2,1,"日累计产油量,功图汇总计算所得")));
			jedis.zadd(key.getBytes(),19, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterVolumetricProduction_l"),"WaterVolumetricProduction","m^3/d",2,1,"日累计产水量,功图汇总计算或者累计产水量计算所得")));
//			jedis.zadd(key.getBytes(),20, SerializeObjectUnils.serialize(new CalItem("日累计产气量","GasVolumetricProduction","m^3/d",2,1,"日累计产气量，累计产气量计算所得")));
			jedis.zadd(key.getBytes(),21, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("volumeWaterCut"),"VolumeWaterCut","%",2,1,languageResourceMap.get("volumeWaterCut"))));
			
			jedis.zadd(key.getBytes(),22, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidWeightProduction_l"),"LiquidWeightProduction","t/d",2,1,"日累计产液量,功图汇总计算所得")));
			jedis.zadd(key.getBytes(),23, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilWeightProduction_l"),"OilWeightProduction","t/d",2,1,"日累计产油量,功图汇总计算所得")));
			jedis.zadd(key.getBytes(),24, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterWeightProduction_l"),"WaterWeightProduction","t/d",2,1,"日累计产水量,功图汇总计算或者累计产水量计算所得")));
			jedis.zadd(key.getBytes(),25, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("weightWaterCut"),"WeightWaterCut","%",2,1,languageResourceMap.get("weightWaterCut"))));
			
			jedis.zadd(key.getBytes(),26, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("surfaceSystemEfficiency"),"SurfaceSystemEfficiency","",2,1,languageResourceMap.get("surfaceSystemEfficiency"))));
			jedis.zadd(key.getBytes(),27, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("wellDownSystemEfficiency"),"WellDownSystemEfficiency","",2,1,languageResourceMap.get("wellDownSystemEfficiency"))));
			jedis.zadd(key.getBytes(),28, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("systemEfficiency"),"SystemEfficiency","",2,1,languageResourceMap.get("systemEfficiency"))));
			jedis.zadd(key.getBytes(),29, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("energyPer100mLift"),"EnergyPer100mLift","kW· h/100m· t",2,1,languageResourceMap.get("energyPer100mLift"))));
			
			jedis.zadd(key.getBytes(),30, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff1"),"PumpEff1","",2,1,languageResourceMap.get("pumpEff1"))));
			jedis.zadd(key.getBytes(),31, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff2"),"PumpEff2","",2,1,languageResourceMap.get("pumpEff2"))));
			jedis.zadd(key.getBytes(),32, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff3"),"PumpEff3","",2,1,languageResourceMap.get("pumpEff3"))));
			jedis.zadd(key.getBytes(),33, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff4"),"PumpEff4","",2,1,languageResourceMap.get("pumpEff4"))));
			
			jedis.zadd(key.getBytes(),34, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff1_pcp"),"PumpEff1","",2,1,languageResourceMap.get("pumpEff1_pcp"))));
			
			jedis.zadd(key.getBytes(),35, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff"),"PumpEff","",2,1,languageResourceMap.get("pumpEff"))));
			
			jedis.zadd(key.getBytes(),36, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("RPM"),"RPM","r/min",2,1,languageResourceMap.get("RPM"))));
			
			jedis.zadd(key.getBytes(),37, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("iDegreeBalance"),"IDegreeBalance","%",2,1,languageResourceMap.get("iDegreeBalance"))));
			jedis.zadd(key.getBytes(),38, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("wattDegreeBalance"),"WattDegreeBalance","%",2,1,languageResourceMap.get("wattDegreeBalance"))));
			jedis.zadd(key.getBytes(),39, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("deltaRadius"),"DeltaRadius","m",2,1,languageResourceMap.get("deltaRadius"))));
			
//			jedis.zadd(key.getBytes(),40, SerializeObjectUnils.serialize(new CalItem("日用电量","TodayKWattH","kW·h",2,1,"日用电量,累计用电量计算所得")));
//			jedis.zadd(key.getBytes(),42, SerializeObjectUnils.serialize(new CalItem("累计用电量","TotalKWattH","kW·h",2,1,"累计用电量,当日最新采集数据")));
//			
//			jedis.zadd(key.getBytes(),42, SerializeObjectUnils.serialize(new CalItem("累计产气量","TotalGasVolumetricProduction","m^3",2,1,"累计产气量,当日最新采集数据")));
//			jedis.zadd(key.getBytes(),43, SerializeObjectUnils.serialize(new CalItem("累计产水量","TotalWaterVolumetricProduction","m^3",2,1,"累计产水量,当日最新采集数据")));
			
			jedis.zadd(key.getBytes(),44, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpSettingDepth"),"PumpSettingDepth","m",2,1,languageResourceMap.get("pumpSettingDepth"))));
			jedis.zadd(key.getBytes(),45, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("producingfluidLevel"),"ProducingfluidLevel","m",2,1,languageResourceMap.get("producingfluidLevel"))));
			jedis.zadd(key.getBytes(),46, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("calcProducingfluidLevel"),"CalcProducingfluidLevel","m",2,1,languageResourceMap.get("calcProducingfluidLevel"))));
			jedis.zadd(key.getBytes(),47, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("submergence"),"Submergence","m",2,1,languageResourceMap.get("submergence"))));
			
			jedis.zadd(key.getBytes(),48, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("tubingPressure"),"TubingPressure","MPa",2,1,languageResourceMap.get("tubingPressure"))));
			jedis.zadd(key.getBytes(),49, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("casingPressure"),"CasingPressure","MPa",2,1,languageResourceMap.get("casingPressure"))));
			jedis.zadd(key.getBytes(),50, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("bottomHolePressure"),"BottomHolePressure","MPa",2,1,languageResourceMap.get("bottomHolePressure"))));
			
			jedis.zadd(key.getBytes(),51, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("remark"),"Remark","",1,2,languageResourceMap.get("remark"))));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static List<CalItem> getPCPTotalCalculateItem(String language){
		Jedis jedis=null;
		List<CalItem> calItemList=new ArrayList<>();
		String key="pcpTotalCalItemList-"+language;
		if(!existsKey(key)){
			MemoryDataManagerTask.loadPCPTotalCalculateItem(language);
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			List<byte[]> calItemSet= jedis.zrange(key.getBytes(), 0, -1);
			for(byte[] calItemByteArr:calItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(calItemByteArr);
				calItemList.add(calItem);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return calItemList;
	}
	
	public static void loadPCPTotalCalculateItem(String language){
		Jedis jedis=null;
		String key="pcpTotalCalItemList-"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
			}
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("deviceName"),"DeviceName","",1,2,languageResourceMap.get("deviceName"))));
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("date"),"CalDate","",3,1,languageResourceMap.get("date"))));
			
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTime"),"CommTime","h",2,1,languageResourceMap.get("commTime"))));
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTimeEfficiency"),"CommTimeEfficiency",timeEfficiencyUnit,2,1,languageResourceMap.get("commTimeEfficiency"))));
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commRange"),"CommRange","",1,1,languageResourceMap.get("commRange"))));
			
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTime"),"RunTime","h",2,1,languageResourceMap.get("runTime"))));
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTimeEfficiency"),"RunTimeEfficiency",timeEfficiencyUnit,2,1,languageResourceMap.get("runTimeEfficiency"))));
			jedis.zadd(key.getBytes(),8, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runRange"),"RunRange","",1,1,languageResourceMap.get("runRange"))));
			
			jedis.zadd(key.getBytes(),9, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("RPM"),"RPM","r/min",2,1,languageResourceMap.get("RPM"))));
			
			jedis.zadd(key.getBytes(),10, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("theoreticalProduction"),"TheoreticalProduction","m^3/d",2,1,languageResourceMap.get("theoreticalProduction"))));
			
			jedis.zadd(key.getBytes(),11, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidVolumetricProduction_l"),"LiquidVolumetricProduction","m^3/d",2,1,languageResourceMap.get("liquidVolumetricProduction_l"))));
			jedis.zadd(key.getBytes(),12, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilVolumetricProduction_l"),"OilVolumetricProduction","m^3/d",2,1,languageResourceMap.get("oilVolumetricProduction_l"))));
			jedis.zadd(key.getBytes(),13, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterVolumetricProduction_l"),"WaterVolumetricProduction","m^3/d",2,1,languageResourceMap.get("waterVolumetricProduction_l"))));
//			jedis.zadd(key.getBytes(),14, SerializeObjectUnils.serialize(new CalItem("日累计产气量","GasVolumetricProduction","m^3/d",2,1,"日累计产气量,累计产气量计算所得")));
			jedis.zadd(key.getBytes(),15, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("volumeWaterCut"),"VolumeWaterCut","%",2,1,languageResourceMap.get("volumeWaterCut"))));
			
			jedis.zadd(key.getBytes(),16, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidWeightProduction_l"),"LiquidWeightProduction","t/d",2,1,languageResourceMap.get("liquidWeightProduction_l"))));
			jedis.zadd(key.getBytes(),17, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilWeightProduction_l"),"OilWeightProduction","t/d",2,1,languageResourceMap.get("oilWeightProduction_l"))));
			jedis.zadd(key.getBytes(),18, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterWeightProduction_l"),"WaterWeightProduction","t/d",2,1,languageResourceMap.get("waterWeightProduction_l"))));
			jedis.zadd(key.getBytes(),19, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("weightWaterCut"),"WeightWaterCut","%",2,1,languageResourceMap.get("weightWaterCut"))));
			
			jedis.zadd(key.getBytes(),20, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("systemEfficiency"),"SystemEfficiency","",2,1,languageResourceMap.get("systemEfficiency"))));
			jedis.zadd(key.getBytes(),21, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("energyPer100mLift"),"EnergyPer100mLift","kW· h/100m· t",2,1,languageResourceMap.get("energyPer100mLift"))));
			
			jedis.zadd(key.getBytes(),22, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff1_pcp"),"PumpEff1","",2,1,languageResourceMap.get("pumpEff1_pcp"))));
			jedis.zadd(key.getBytes(),23, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff2_pcp"),"PumpEff2","",2,1,languageResourceMap.get("pumpEff2_pcp"))));
			jedis.zadd(key.getBytes(),24, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff"),"PumpEff","",2,1,languageResourceMap.get("pumpEff"))));
			
//			jedis.zadd(key.getBytes(),25, SerializeObjectUnils.serialize(new CalItem("日用电量","TodayKWattH","kW·h",2,1,"日用电量,累计用电量计算所得")));
//			jedis.zadd(key.getBytes(),26, SerializeObjectUnils.serialize(new CalItem("累计用电量","TotalKWattH","kW·h",2,1,"累计用电量,当日最新采集数据")));
//			
//			jedis.zadd(key.getBytes(),27, SerializeObjectUnils.serialize(new CalItem("累计产气量","TotalGasVolumetricProduction","m^3",2,1,"累计产气量,当日最新采集数据")));
//			jedis.zadd(key.getBytes(),28, SerializeObjectUnils.serialize(new CalItem("累计产水量","TotalWaterVolumetricProduction","m^3",2,1,"累计产水量,当日最新采集数据")));
			
			jedis.zadd(key.getBytes(),29, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpSettingDepth"),"PumpSettingDepth","m",2,1,languageResourceMap.get("pumpSettingDepth"))));
			jedis.zadd(key.getBytes(),30, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("producingfluidLevel"),"ProducingfluidLevel","m",2,1,languageResourceMap.get("producingfluidLevel"))));
			jedis.zadd(key.getBytes(),31, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("submergence"),"Submergence","m",2,1,languageResourceMap.get("submergence"))));
			
			jedis.zadd(key.getBytes(),32, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("tubingPressure"),"TubingPressure","MPa",2,1,languageResourceMap.get("tubingPressure"))));
			jedis.zadd(key.getBytes(),33, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("casingPressure"),"CasingPressure","MPa",2,1,languageResourceMap.get("casingPressure"))));
			jedis.zadd(key.getBytes(),34, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("bottomHolePressure"),"BottomHolePressure","MPa",2,1,languageResourceMap.get("bottomHolePressure"))));
			
			jedis.zadd(key.getBytes(),35, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("remark"),"Remark","",1,2,languageResourceMap.get("remark"))));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static String getTimingTotalCalItemNameByCode(String oldName,String code,String language){
		String name="";
		CalItem c=getTimingTotalCalItemByCode(code,language);
		if(c!=null){
			name=c.getName();
		}else{
			name=oldName;
		}
		return name;
	}
	
	public static CalItem getTimingTotalCalItemByCode(String code,String language){
		CalItem c=null;
		List<CalItem> list= getAcqTimingTotalCalculateItem(language);
		c=getSingleCalItemByCode(code,list);
		if(c==null){
			list= getSRPTimingTotalCalculateItem(language);
			c=getSingleCalItemByCode(code,list);
		}
		if(c==null){
			list= getPCPTimingTotalCalculateItem(language);
			c=getSingleCalItemByCode(code,list);
		}
		return c;
	}
	
	public static String timingTotalCalItemLanguageSwitchover(String name,String language){
		String r="";
		int type=0;
		String code="";
		if(languageList!=null && languageList.size()>0){
			for(String l:languageList){
				List<CalItem> list= getAcqTimingTotalCalculateItem(l);
				CalItem c=getSingleCalItem(name,list);
				if(c!=null){
					type=1;
					code=c.getCode();
					break;
				}
				
				list= getSRPTimingTotalCalculateItem(l);
				c=getSingleCalItem(name,list);
				if(c!=null){
					type=2;
					code=c.getCode();
					break;
				}
				
				list= getPCPTimingTotalCalculateItem(l);
				c=getSingleCalItem(name,list);
				if(c!=null){
					type=3;
					code=c.getCode();
					break;
				}
			}
		}
		if(type>0){
			if(type==1){
				List<CalItem> list= getAcqTimingTotalCalculateItem(language);
				CalItem c=getSingleCalItemByCode(code,list);
				if(c!=null){
					r=c.getName();
				}
			}else if(type==2){
				List<CalItem> list= getSRPTimingTotalCalculateItem(language);
				CalItem c=getSingleCalItemByCode(code,list);
				if(c!=null){
					r=c.getName();
				}
			}else if(type==3){
				List<CalItem> list= getPCPTimingTotalCalculateItem(language);
				CalItem c=getSingleCalItemByCode(code,list);
				if(c!=null){
					r=c.getName();
				}
			}
		}else{
			r=name;
		}
		return r;
	}
	
	public static List<CalItem> getAcqTimingTotalCalculateItem(String language){
		Jedis jedis=null;
		List<CalItem> calItemList=new ArrayList<>();
		String key="acqTimingTotalCalItemList-"+language;
		if(!existsKey(key)){
			MemoryDataManagerTask.loadAcqTimingTotalCalculateItem(language);
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			List<byte[]> calItemSet= jedis.zrange(key.getBytes(), 0, -1);
			for(byte[] calItemByteArr:calItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(calItemByteArr);
				calItemList.add(calItem);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return calItemList;
	}
	
	public static void loadAcqTimingTotalCalculateItem(String language){
		Jedis jedis=null;
		String key="acqTimingTotalCalItemList-"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
			}
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("deviceName"),"DeviceName","",1,2,languageResourceMap.get("deviceName"))));//1-字符串 2-数值 3-日期 4-日期时间
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("time"),"CalTime","",4,1,languageResourceMap.get("time"))));
			
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTime"),"CommTime","h",2,1,languageResourceMap.get("commTime"))));
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTimeEfficiency"),"CommTimeEfficiency",timeEfficiencyUnit,2,1,languageResourceMap.get("commTimeEfficiency"))));
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commRange"),"CommRange","",1,1,languageResourceMap.get("commRange"))));
			
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTime"),"RunTime","h",2,1,languageResourceMap.get("runTime"))));
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTimeEfficiency"),"RunTimeEfficiency",timeEfficiencyUnit,2,1,languageResourceMap.get("runTimeEfficiency"))));
			jedis.zadd(key.getBytes(),8, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runRange"),"RunRange","",1,1,languageResourceMap.get("runRange"))));
			
			
			
			jedis.zadd(key.getBytes(),9, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("remark"),"Remark","",1,2,languageResourceMap.get("remark"))));
			
			jedis.zadd(key.getBytes(),10, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservedCol1"),"reservedcol1","",1,2,languageResourceMap.get("reservedCol1"))));
			jedis.zadd(key.getBytes(),11, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservedCol2"),"reservedcol2","",1,2,languageResourceMap.get("reservedCol2"))));
			jedis.zadd(key.getBytes(),12, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservedCol3"),"reservedcol3","",1,2,languageResourceMap.get("reservedCol3"))));
			jedis.zadd(key.getBytes(),13, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservedCol4"),"reservedcol4","",1,2,languageResourceMap.get("reservedCol4"))));
			jedis.zadd(key.getBytes(),14, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservedCol5"),"reservedcol5","",1,2,languageResourceMap.get("reservedCol5"))));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static List<CalItem> getSRPTimingTotalCalculateItem(String language){
		Jedis jedis=null;
		List<CalItem> calItemList=new ArrayList<>();
		String key="srpTimingTotalCalItemList-"+language;
		if(!existsKey(key)){
			MemoryDataManagerTask.loadSRPTimingTotalCalculateItem(language);
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			List<byte[]> calItemSet= jedis.zrange(key.getBytes(), 0, -1);
			for(byte[] calItemByteArr:calItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(calItemByteArr);
				calItemList.add(calItem);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return calItemList;
	}
	
	public static void loadSRPTimingTotalCalculateItem(String language){
		Jedis jedis=null;
		String key="srpTimingTotalCalItemList-"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
			}
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("deviceName"),"DeviceName","",1,2,languageResourceMap.get("deviceName"))));//1-字符串 2-数值 3-日期 4-日期时间
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("time"),"CalTime","",4,1,languageResourceMap.get("time"))));
			
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTime"),"CommTime","h",2,1,languageResourceMap.get("commTime"))));
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTimeEfficiency"),"CommTimeEfficiency",timeEfficiencyUnit,2,1,languageResourceMap.get("commTimeEfficiency"))));
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commRange"),"CommRange","",1,1,languageResourceMap.get("commRange"))));
			
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTime"),"RunTime","h",2,1,languageResourceMap.get("runTime"))));
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTimeEfficiency"),"RunTimeEfficiency",timeEfficiencyUnit,2,1,languageResourceMap.get("runTimeEfficiency"))));
			jedis.zadd(key.getBytes(),8, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runRange"),"RunRange","",1,1,languageResourceMap.get("runRange"))));
			
			jedis.zadd(key.getBytes(),9, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("resultName"),"ResultName","",1,1,languageResourceMap.get("resultName"))));
			jedis.zadd(key.getBytes(),10, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("optimizationSuggestion"),"OptimizationSuggestion","",1,1,languageResourceMap.get("optimizationSuggestion"))));
			jedis.zadd(key.getBytes(),11, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("stroke"),"Stroke","m",2,1,languageResourceMap.get("stroke"))));
			jedis.zadd(key.getBytes(),12, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("SPM"),"SPM","1/min",2,1,languageResourceMap.get("SPM"))));
			jedis.zadd(key.getBytes(),13, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("fMax"),"FMax","kN",2,1,languageResourceMap.get("fMax"))));
			jedis.zadd(key.getBytes(),14, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("fMin"),"FMin","kN",2,1,languageResourceMap.get("fMin"))));
			
			jedis.zadd(key.getBytes(),15, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("fullnessCoefficient"),"FullnessCoefficient","",2,1,languageResourceMap.get("fullnessCoefficient"))));
			
			jedis.zadd(key.getBytes(),16, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("theoreticalProduction"),"TheoreticalProduction","m^3/d",2,1,languageResourceMap.get("theoreticalProduction"))));
			
			jedis.zadd(key.getBytes(),17, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidVolumetricProduction_l"),"LiquidVolumetricProduction","m^3/d",2,1,"日累计产液量,功图汇总计算所得")));
			jedis.zadd(key.getBytes(),18, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilVolumetricProduction_l"),"OilVolumetricProduction","m^3/d",2,1,"日累计产油量,功图汇总计算所得")));
			jedis.zadd(key.getBytes(),19, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterVolumetricProduction_l"),"WaterVolumetricProduction","m^3/d",2,1,"日累计产水量,功图汇总计算或者累计产水量计算所得")));
//			jedis.zadd(key.getBytes(),20, SerializeObjectUnils.serialize(new CalItem("日累计产气量","GasVolumetricProduction","m^3/d",2,1,"日累计产气量，累计产气量计算所得")));
			jedis.zadd(key.getBytes(),21, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("volumeWaterCut"),"VolumeWaterCut","%",2,1,languageResourceMap.get("volumeWaterCut"))));
			
			jedis.zadd(key.getBytes(),22, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidWeightProduction_l"),"LiquidWeightProduction","t/d",2,1,"日累计产液量,功图汇总计算所得")));
			jedis.zadd(key.getBytes(),23, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilWeightProduction_l"),"OilWeightProduction","t/d",2,1,"日累计产油量,功图汇总计算所得")));
			jedis.zadd(key.getBytes(),24, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterWeightProduction_l"),"WaterWeightProduction","t/d",2,1,"日累计产水量,功图汇总计算或者累计产水量计算所得")));
			jedis.zadd(key.getBytes(),25, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("weightWaterCut"),"WeightWaterCut","%",2,1,languageResourceMap.get("weightWaterCut"))));
			
			jedis.zadd(key.getBytes(),26, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("surfaceSystemEfficiency"),"SurfaceSystemEfficiency","",2,1,languageResourceMap.get("surfaceSystemEfficiency"))));
			jedis.zadd(key.getBytes(),27, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("wellDownSystemEfficiency"),"WellDownSystemEfficiency","",2,1,languageResourceMap.get("wellDownSystemEfficiency"))));
			jedis.zadd(key.getBytes(),28, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("systemEfficiency"),"SystemEfficiency","",2,1,languageResourceMap.get("systemEfficiency"))));
			jedis.zadd(key.getBytes(),29, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("energyPer100mLift"),"EnergyPer100mLift","kW· h/100m· t",2,1,languageResourceMap.get("energyPer100mLift"))));
			
			jedis.zadd(key.getBytes(),30, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff1"),"PumpEff1","",2,1,languageResourceMap.get("pumpEff1"))));
			jedis.zadd(key.getBytes(),31, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff2"),"PumpEff2","",2,1,languageResourceMap.get("pumpEff2"))));
			jedis.zadd(key.getBytes(),32, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff3"),"PumpEff3","",2,1,languageResourceMap.get("pumpEff3"))));
			jedis.zadd(key.getBytes(),33, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff4"),"PumpEff4","",2,1,languageResourceMap.get("pumpEff4"))));
			
			jedis.zadd(key.getBytes(),34, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff1_pcp"),"PumpEff1","",2,1,languageResourceMap.get("pumpEff1_pcp"))));
			
			jedis.zadd(key.getBytes(),35, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff"),"PumpEff","",2,1,languageResourceMap.get("pumpEff"))));
			
			jedis.zadd(key.getBytes(),36, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("RPM"),"RPM","r/min",2,1,languageResourceMap.get("RPM"))));
			
			jedis.zadd(key.getBytes(),37, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("iDegreeBalance"),"IDegreeBalance","%",2,1,languageResourceMap.get("iDegreeBalance"))));
			jedis.zadd(key.getBytes(),38, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("wattDegreeBalance"),"WattDegreeBalance","%",2,1,languageResourceMap.get("wattDegreeBalance"))));
			jedis.zadd(key.getBytes(),39, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("deltaRadius"),"DeltaRadius","m",2,1,languageResourceMap.get("deltaRadius"))));
			
//			jedis.zadd(key.getBytes(),40, SerializeObjectUnils.serialize(new CalItem("日用电量","TodayKWattH","kW·h",2,1,"日用电量,累计用电量计算所得")));
//			jedis.zadd(key.getBytes(),41, SerializeObjectUnils.serialize(new CalItem("累计用电量","TotalKWattH","kW·h",2,1,"累计用电量,当日最新采集数据")));
//			
//			jedis.zadd(key.getBytes(),42, SerializeObjectUnils.serialize(new CalItem("累计产气量","TotalGasVolumetricProduction","m^3",2,1,"累计产气量,当日最新采集数据")));
//			jedis.zadd(key.getBytes(),43, SerializeObjectUnils.serialize(new CalItem("累计产水量","TotalWaterVolumetricProduction","m^3",2,1,"累计产水量,当日最新采集数据")));
			
			jedis.zadd(key.getBytes(),44, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidVolumetricProduction"),"RealtimeLiquidVolumetricProduction","m^3/d",2,1,languageResourceMap.get("liquidVolumetricProduction"))));
			jedis.zadd(key.getBytes(),45, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilVolumetricProduction"),"RealtimeOilVolumetricProduction","m^3/d",2,1,languageResourceMap.get("oilVolumetricProduction"))));
			jedis.zadd(key.getBytes(),46, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterVolumetricProduction"),"RealtimeWaterVolumetricProduction","m^3/d",2,1,languageResourceMap.get("waterVolumetricProduction"))));
//			jedis.zadd(key.getBytes(),47, SerializeObjectUnils.serialize(new CalItem("瞬时产气量","RealtimeGasVolumetricProduction","m^3/d",2,1,"瞬时产气量")));
			
			jedis.zadd(key.getBytes(),48, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidWeightProduction"),"RealtimeLiquidWeightProduction","t/d",2,1,languageResourceMap.get("liquidWeightProduction"))));
			jedis.zadd(key.getBytes(),49, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilWeightProduction"),"RealtimeOilWeightProduction","t/d",2,1,languageResourceMap.get("oilWeightProduction"))));
			jedis.zadd(key.getBytes(),50, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterWeightProduction"),"RealtimeWaterWeightProduction","t/d",2,1,languageResourceMap.get("waterWeightProduction"))));
			
			jedis.zadd(key.getBytes(),51, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpSettingDepth"),"PumpSettingDepth","m",2,1,languageResourceMap.get("pumpSettingDepth"))));
			jedis.zadd(key.getBytes(),52, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("producingfluidLevel"),"ProducingfluidLevel","m",2,1,languageResourceMap.get("producingfluidLevel"))));
			jedis.zadd(key.getBytes(),53, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("calcProducingfluidLevel"),"CalcProducingfluidLevel","m",2,1,languageResourceMap.get("calcProducingfluidLevel"))));
			jedis.zadd(key.getBytes(),54, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("submergence"),"Submergence","m",2,1,languageResourceMap.get("submergence"))));
			
			jedis.zadd(key.getBytes(),55, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("tubingPressure"),"TubingPressure","MPa",2,1,languageResourceMap.get("tubingPressure"))));
			jedis.zadd(key.getBytes(),56, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("casingPressure"),"CasingPressure","MPa",2,1,languageResourceMap.get("casingPressure"))));
			jedis.zadd(key.getBytes(),57, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("bottomHolePressure"),"BottomHolePressure","MPa",2,1,languageResourceMap.get("bottomHolePressure"))));
			
			jedis.zadd(key.getBytes(),58, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("remark"),"Remark","",1,2,languageResourceMap.get("remark"))));
			
			jedis.zadd(key.getBytes(),59, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservedCol1"),"reservedcol1","",1,2,languageResourceMap.get("reservedCol1"))));
			jedis.zadd(key.getBytes(),60, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservedCol2"),"reservedcol2","",1,2,languageResourceMap.get("reservedCol2"))));
			jedis.zadd(key.getBytes(),61, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservedCol3"),"reservedcol3","",1,2,languageResourceMap.get("reservedCol3"))));
			jedis.zadd(key.getBytes(),62, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservedCol4"),"reservedcol4","",1,2,languageResourceMap.get("reservedCol4"))));
			jedis.zadd(key.getBytes(),63, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservedCol5"),"reservedcol5","",1,2,languageResourceMap.get("reservedCol5"))));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static List<CalItem> getPCPTimingTotalCalculateItem(String language){
		Jedis jedis=null;
		List<CalItem> calItemList=new ArrayList<>();
		String key="pcpTimingTotalCalItemList-"+language;
		if(!existsKey(key)){
			MemoryDataManagerTask.loadPCPTimingTotalCalculateItem(language);
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			List<byte[]> calItemSet= jedis.zrange(key.getBytes(), 0, -1);
			for(byte[] calItemByteArr:calItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(calItemByteArr);
				calItemList.add(calItem);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return calItemList;
	}
	
	public static void loadPCPTimingTotalCalculateItem(String language){
		Jedis jedis=null;
		String key="pcpTimingTotalCalItemList-"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
			}
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("deviceName"),"DeviceName","",1,2,languageResourceMap.get("deviceName"))));
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("time"),"CalTime","",4,1,languageResourceMap.get("time"))));
			
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTime"),"CommTime","h",2,1,languageResourceMap.get("commTime"))));
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTimeEfficiency"),"CommTimeEfficiency",timeEfficiencyUnit,2,1,languageResourceMap.get("commTimeEfficiency"))));
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commRange"),"CommRange","",1,1,languageResourceMap.get("commRange"))));
			
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTime"),"RunTime","h",2,1,languageResourceMap.get("runTime"))));
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTimeEfficiency"),"RunTimeEfficiency",timeEfficiencyUnit,2,1,languageResourceMap.get("runTimeEfficiency"))));
			jedis.zadd(key.getBytes(),8, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runRange"),"RunRange","",1,1,languageResourceMap.get("runRange"))));
			
			jedis.zadd(key.getBytes(),9, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("RPM"),"RPM","r/min",2,1,languageResourceMap.get("RPM"))));
			
			jedis.zadd(key.getBytes(),10, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("theoreticalProduction"),"TheoreticalProduction","m^3/d",2,1,languageResourceMap.get("theoreticalProduction"))));
			
			jedis.zadd(key.getBytes(),11, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidVolumetricProduction_l"),"LiquidVolumetricProduction","m^3/d",2,1,languageResourceMap.get("liquidVolumetricProduction_l"))));
			jedis.zadd(key.getBytes(),12, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilVolumetricProduction_l"),"OilVolumetricProduction","m^3/d",2,1,languageResourceMap.get("oilVolumetricProduction_l"))));
			jedis.zadd(key.getBytes(),13, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterVolumetricProduction_l"),"WaterVolumetricProduction","m^3/d",2,1,languageResourceMap.get("waterVolumetricProduction_l"))));
//			jedis.zadd(key.getBytes(),14, SerializeObjectUnils.serialize(new CalItem("日累计产气量","GasVolumetricProduction","m^3/d",2,1,"日产气量,累计产气量计算所得")));
			jedis.zadd(key.getBytes(),15, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("volumeWaterCut"),"VolumeWaterCut","%",2,1,languageResourceMap.get("volumeWaterCut"))));
			
			jedis.zadd(key.getBytes(),16, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidWeightProduction_l"),"LiquidWeightProduction","t/d",2,1,languageResourceMap.get("liquidWeightProduction_l"))));
			jedis.zadd(key.getBytes(),17, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilWeightProduction_l"),"OilWeightProduction","t/d",2,1,languageResourceMap.get("oilWeightProduction_l"))));
			jedis.zadd(key.getBytes(),18, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterWeightProduction_l"),"WaterWeightProduction","t/d",2,1,languageResourceMap.get("waterWeightProduction_l"))));
			jedis.zadd(key.getBytes(),19, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("weightWaterCut"),"WeightWaterCut","%",2,1,languageResourceMap.get("weightWaterCut"))));
			
			jedis.zadd(key.getBytes(),20, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("systemEfficiency"),"SystemEfficiency","",2,1,languageResourceMap.get("systemEfficiency"))));
			jedis.zadd(key.getBytes(),21, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("energyPer100mLift"),"EnergyPer100mLift","kW· h/100m· t",2,1,languageResourceMap.get("energyPer100mLift"))));
			
			jedis.zadd(key.getBytes(),22, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff1_pcp"),"PumpEff1","",2,1,languageResourceMap.get("pumpEff1_pcp"))));
			jedis.zadd(key.getBytes(),23, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff2_pcp"),"PumpEff2","",2,1,languageResourceMap.get("pumpEff2_pcp"))));
			jedis.zadd(key.getBytes(),24, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff"),"PumpEff","",2,1,languageResourceMap.get("pumpEff"))));
			
			jedis.zadd(key.getBytes(),29, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidVolumetricProduction"),"RealtimeLiquidVolumetricProduction","m^3",2,1,languageResourceMap.get("liquidVolumetricProduction"))));
			jedis.zadd(key.getBytes(),30, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilVolumetricProduction"),"RealtimeOilVolumetricProduction","m^3",2,1,languageResourceMap.get("oilVolumetricProduction"))));
			jedis.zadd(key.getBytes(),31, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterVolumetricProduction"),"RealtimeWaterVolumetricProduction","m^3",2,1,languageResourceMap.get("waterVolumetricProduction"))));

			jedis.zadd(key.getBytes(),33, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidWeightProduction"),"RealtimeLiquidWeightProduction","t/d",2,1,languageResourceMap.get("liquidWeightProduction"))));
			jedis.zadd(key.getBytes(),34, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilWeightProduction"),"RealtimeOilWeightProduction","t/d",2,1,languageResourceMap.get("oilWeightProduction"))));
			jedis.zadd(key.getBytes(),35, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterWeightProduction"),"RealtimeWaterWeightProduction","t/d",2,1,languageResourceMap.get("waterWeightProduction"))));
			
			jedis.zadd(key.getBytes(),36, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpSettingDepth"),"PumpSettingDepth","m",2,1,languageResourceMap.get("pumpSettingDepth"))));
			jedis.zadd(key.getBytes(),37, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("producingfluidLevel"),"ProducingfluidLevel","m",2,1,languageResourceMap.get("producingfluidLevel"))));
			jedis.zadd(key.getBytes(),38, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("submergence"),"Submergence","m",2,1,languageResourceMap.get("submergence"))));
			
			jedis.zadd(key.getBytes(),39, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("tubingPressure"),"TubingPressure","MPa",2,1,languageResourceMap.get("tubingPressure"))));
			jedis.zadd(key.getBytes(),40, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("casingPressure"),"CasingPressure","MPa",2,1,languageResourceMap.get("casingPressure"))));
			jedis.zadd(key.getBytes(),41, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("bottomHolePressure"),"BottomHolePressure","MPa",2,1,languageResourceMap.get("bottomHolePressure"))));
			
			jedis.zadd(key.getBytes(),42, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("remark"),"Remark","",1,2,languageResourceMap.get("remark"))));
			
			jedis.zadd(key.getBytes(),43, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservedCol1"),"reservedcol1","",1,2,languageResourceMap.get("reservedCol1"))));
			jedis.zadd(key.getBytes(),44, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservedCol2"),"reservedcol2","",1,2,languageResourceMap.get("reservedCol2"))));
			jedis.zadd(key.getBytes(),45, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservedCol3"),"reservedcol3","",1,2,languageResourceMap.get("reservedCol3"))));
			jedis.zadd(key.getBytes(),46, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservedCol4"),"reservedcol4","",1,2,languageResourceMap.get("reservedCol4"))));
			jedis.zadd(key.getBytes(),47, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservedCol5"),"reservedcol5","",1,2,languageResourceMap.get("reservedCol5"))));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	
	public static String getInputItemNameByCode(String oldName,String code,String language){
		String name="";
		CalItem c=getInputItemByCode(code,language);
		if(c!=null){
			name=c.getName();
		}else{
			name=oldName;
		}
		return name;
	}
	
	public static CalItem getInputItemByCode(String code,String language){
		CalItem c=null;
		List<CalItem> list= getSRPInputItem(language);
		c=getSingleCalItemByCode(code,list);
		if(c==null){
			list= getPCPInputItem(language);
			c=getSingleCalItemByCode(code,list);
		}
		return c;
	}
	
	public static CalItem getInputItemByNameAndUnit(String name,String unit,String language){
		CalItem c=null;
		List<CalItem> list= getSRPInputItem(language);
		c=getSingleCalItemByNameAndUnit(name,unit,list);
		if(c==null){
			list= getPCPInputItem(language);
			c=getSingleCalItemByNameAndUnit(name,unit,list);
		}
		return c;
	}
	
	public static String inputItemLanguageSwitchover(String name,String language){
		String r="";
		int type=0;
		String code="";
		if(languageList!=null && languageList.size()>0){
			for(String l:languageList){
				List<CalItem> list= getSRPInputItem(l);
				CalItem c=getSingleCalItem(name,list);
				if(c!=null){
					type=1;
					code=c.getCode();
					break;
				}
				
				list= getPCPInputItem(l);
				c=getSingleCalItem(name,list);
				if(c!=null){
					type=2;
					code=c.getCode();
					break;
				}
			}
		}
		if(type>0){
			if(type==1){
				List<CalItem> list= getSRPInputItem(language);
				CalItem c=getSingleCalItemByCode(code,list);
				if(c!=null){
					r=c.getName();
				}
			}else if(type==2){
				List<CalItem> list= getPCPInputItem(language);
				CalItem c=getSingleCalItemByCode(code,list);
				if(c!=null){
					r=c.getName();
				}
			}
		}else{
			r=name;
		}
		return r;
	}
	
	
	public static List<CalItem> getSRPInputItem(String language){
		Jedis jedis=null;
		String key="srpInputItemList-"+language;
		List<CalItem> calItemList=new ArrayList<>();
		if(!existsKey(key)){
			MemoryDataManagerTask.loadSRPInputItem(language);
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			List<byte[]> calItemSet= jedis.zrange(key.getBytes(), 0, -1);
			for(byte[] calItemByteArr:calItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(calItemByteArr);
				calItemList.add(calItem);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return calItemList;
	}
	
	public static void loadSRPInputItem(String language){
		Jedis jedis=null;
		String key="srpInputItemList-"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("crudeOilDensity"),"CrudeOilDensity","g/cm^3",2,2,languageResourceMap.get("crudeOilDensity"))));
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterDensity"),"WaterDensity","g/cm^3",2,2,languageResourceMap.get("waterDensity"))));
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("naturalGasRelativeDensity"),"NaturalGasRelativeDensity","",2,2,languageResourceMap.get("naturalGasRelativeDensity"))));
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("saturationPressure"),"SaturationPressure","MPa",2,2,languageResourceMap.get("saturationPressure"))));
			
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservoirDepth"),"ReservoirDepth","m",2,2,languageResourceMap.get("reservoirDepth"))));
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservoirTemperature"),"ReservoirTemperature","℃",2,2,languageResourceMap.get("reservoirTemperature"))));
			
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("tubingPressure"),"TubingPressure","MPa",2,2,languageResourceMap.get("tubingPressure"))));
			jedis.zadd(key.getBytes(),8, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("casingPressure"),"CasingPressure","MPa",2,2,languageResourceMap.get("casingPressure"))));
			jedis.zadd(key.getBytes(),9, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("wellHeadTemperature"),"WellHeadTemperature","℃",2,2,languageResourceMap.get("wellHeadTemperature"))));
			jedis.zadd(key.getBytes(),10, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterCut"),"WaterCut","%",2,2,languageResourceMap.get("waterCut"))));
			jedis.zadd(key.getBytes(),11, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("productionGasOilRatio"),"ProductionGasOilRatio","m^3/t",2,2,languageResourceMap.get("productionGasOilRatio"))));
			
			jedis.zadd(key.getBytes(),12, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("producingfluidLevel"),"ProducingfluidLevel","m",2,2,languageResourceMap.get("producingfluidLevel"))));
			jedis.zadd(key.getBytes(),13, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpSettingDepth"),"PumpSettingDepth","m",2,2,languageResourceMap.get("pumpSettingDepth"))));
			
			jedis.zadd(key.getBytes(),14, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpBoreDiameter"),"PumpBoreDiameter","mm",2,2,languageResourceMap.get("pumpBoreDiameter"))));
			
			jedis.zadd(key.getBytes(),15, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("levelCorrectValue"),"LevelCorrectValue","MPa",2,2,languageResourceMap.get("levelCorrectValue"))));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static List<CalItem> getPCPInputItem(String language){
		Jedis jedis=null;
		String key="pcpInputItemList-"+language;
		List<CalItem> calItemList=new ArrayList<>();
		if(!existsKey(key)){
			MemoryDataManagerTask.loadPCPInputItem(language);
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			List<byte[]> calItemSet= jedis.zrange(key.getBytes(), 0, -1);
			for(byte[] calItemByteArr:calItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(calItemByteArr);
				calItemList.add(calItem);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return calItemList;
	}
	
	public static void loadPCPInputItem(String language){
		Jedis jedis=null;
		String key="pcpInputItemList-"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("crudeOilDensity"),"CrudeOilDensity","g/cm^3",2,2,languageResourceMap.get("crudeOilDensity"))));
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterDensity"),"WaterDensity","g/cm^3",2,2,languageResourceMap.get("waterDensity"))));
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("naturalGasRelativeDensity"),"NaturalGasRelativeDensity","",2,2,languageResourceMap.get("naturalGasRelativeDensity"))));
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("saturationPressure"),"SaturationPressure","MPa",2,2,languageResourceMap.get("saturationPressure"))));
			
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservoirDepth"),"ReservoirDepth","m",2,2,languageResourceMap.get("reservoirDepth"))));
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservoirTemperature"),"ReservoirTemperature","℃",2,2,languageResourceMap.get("reservoirTemperature"))));
			
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("tubingPressure"),"TubingPressure","MPa",2,2,languageResourceMap.get("tubingPressure"))));
			jedis.zadd(key.getBytes(),8, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("casingPressure"),"CasingPressure","MPa",2,2,languageResourceMap.get("casingPressure"))));
			jedis.zadd(key.getBytes(),9, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("wellHeadTemperature"),"WellHeadTemperature","℃",2,2,languageResourceMap.get("wellHeadTemperature"))));
			jedis.zadd(key.getBytes(),10, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterCut"),"WaterCut","%",2,2,languageResourceMap.get("waterCut"))));
			jedis.zadd(key.getBytes(),11, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("productionGasOilRatio"),"ProductionGasOilRatio","m^3/t",2,2,languageResourceMap.get("productionGasOilRatio"))));
			
			jedis.zadd(key.getBytes(),12, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("producingfluidLevel"),"ProducingfluidLevel","m",2,2,languageResourceMap.get("producingfluidLevel"))));
			jedis.zadd(key.getBytes(),13, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpSettingDepth"),"PumpSettingDepth","m",2,2,languageResourceMap.get("pumpSettingDepth"))));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static UserInfo getUserInfoByNo(String userNo){
		UserInfo userInfo=null;
		Jedis jedis=null;
		if(!existsKey("UserInfo")){
			MemoryDataManagerTask.loadUserInfo(null,0,"update");
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis.hexists("UserInfo".getBytes(), userNo.getBytes())){
				userInfo=(UserInfo) SerializeObjectUnils.unserizlize(jedis.hget("UserInfo".getBytes(), userNo.getBytes()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return userInfo;
	}
	
	public static UserInfo getUserInfoByAccount(String userAccount){
		UserInfo userInfo=null;
		Jedis jedis=null;
		if(!existsKey("UserInfo")){
			MemoryDataManagerTask.loadUserInfo(null,0,"update");
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			
			List<byte[]> byteList =jedis.hvals("UserInfo".getBytes());
			for(int i=0;i<byteList.size();i++){
				Object obj = SerializeObjectUnils.unserizlize(byteList.get(i));
				if (obj instanceof UserInfo) {
					UserInfo user=(UserInfo)obj;
					if(userAccount.equals(user.getUserId())){
						userInfo=user;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return userInfo;
	}
	public static void updateUserInfo(UserInfo userInfo){
		Jedis jedis=null;
		try {
			if(userInfo!=null){
				jedis = RedisUtil.jedisPool.getResource();
				jedis.hset("UserInfo".getBytes(), (userInfo.getUserNo()+"").getBytes(), SerializeObjectUnils.serialize(userInfo));//哈希(Hash)
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static void loadUserInfo(List<String> userList,int condition,String method){//condition 0 -用户id 1-用户账号
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if("delete".equalsIgnoreCase(method)){
				if(userList!=null){
					if(condition==0){
						for(int i=0;i<userList.size();i++){
							jedis.hdel("UserInfo".getBytes(), userList.get(i).getBytes());
						}
					}else if(condition==1){
						for(int i=0;i<userList.size();i++){
							if(jedis.exists("UserInfo".getBytes())){
								List<byte[]> userByteList =jedis.hvals("UserInfo".getBytes());
								for(int j=0;j<userByteList.size();j++){
									UserInfo userInfo=(UserInfo)SerializeObjectUnils.unserizlize(userByteList.get(i));
									if(userList.get(i).equalsIgnoreCase(userInfo.getUserId())){
										jedis.hdel("UserInfo".getBytes(), (userInfo.getUserNo()+"").getBytes());
										break;
									}
								}
							}
						}
					}
				}
				return;
			}
			
			String users="";
			Map<String,Code> languageCodeMap=MemoryDataManagerTask.getCodeMap("LANGUAGE",Config.getInstance().configFile.getAp().getOthers().getLoginLanguage());
			if(condition==0){
				users=StringUtils.join(userList, ",");
			}else{
				users=StringManagerUtils.joinStringArr2(userList, ",");
			}
			
			String sql="select t.user_no,t.user_id,t.user_name,t.user_pwd,t.user_orgid,"
					+ " t.user_in_email,t.user_phone,"
					+ " t.user_quicklogin,t.user_enable,t.user_receivesms,t.user_receivemail,"
					+ " t.user_type,"
					+ " t2.role_name,t2.role_level,t2.showlevel,"
					+ " t.user_language"
					+ " from tbl_user t,tbl_role t2 "
					+ " where t.user_type=t2.role_id ";
			if(StringManagerUtils.isNotNull(users)){
				if(condition==0){
					sql+=" and t.user_no in("+users+")";
				}else{
					sql+=" and t.user_id in("+users+")";
				}
			}
			sql+= " order by t.user_no";
			List<Object[]> list=OracleJdbcUtis.query(sql);
			for(Object[] obj:list){
				UserInfo userInfo=new UserInfo();
				userInfo.setUserNo(StringManagerUtils.stringToInteger(obj[0]+""));
				userInfo.setUserId(obj[1]+"");
				userInfo.setUserName(obj[2]+"");
				userInfo.setUserPwd(obj[3]+"");
				userInfo.setUserOrgid(StringManagerUtils.stringToInteger(obj[4]+""));
				
				userInfo.setUserInEmail(obj[5]+"");
				userInfo.setUserPhone(obj[6]+"");
				
				userInfo.setUserQuickLogin(StringManagerUtils.stringToInteger(obj[7]+""));
				userInfo.setUserEnable(StringManagerUtils.stringToInteger(obj[8]+""));
				userInfo.setReceiveSMS(StringManagerUtils.stringToInteger(obj[9]+""));
				userInfo.setReceiveMail(StringManagerUtils.stringToInteger(obj[10]+""));
				
				userInfo.setUserType(StringManagerUtils.stringToInteger(obj[11]+""));
				userInfo.setRoleName(obj[12]+"");
				userInfo.setRoleLevel(StringManagerUtils.stringToInteger(obj[13]+""));
				userInfo.setRoleShowLevel(StringManagerUtils.stringToInteger(obj[14]+""));
				
				userInfo.setLanguage(StringManagerUtils.stringToInteger(obj[15]+""));
				if(userInfo.getLanguage()==1){
					userInfo.setLanguageName("zh_CN");
				}else if(userInfo.getLanguage()==2){
					userInfo.setLanguageName("en");
				}else if(userInfo.getLanguage()==3){
					userInfo.setLanguageName("ru");
				}
				
				userInfo.setOrgChildrenNode(new ArrayList<>());
				userInfo.setDeviceTypeChildrenNode(new ArrayList<>());
				
				String orgChildrenNodeSql="select org_id from tbl_org t start with t.org_id="+userInfo.getUserOrgid()+" connect by t.org_parent= prior t.org_id";
				String deviceTypeChildrenNodeSql="select t.id "
						+ "from tbl_devicetypeinfo t,tbl_role r,tbl_devicetype2role rd "
						+ "where t.id=rd.rd_devicetypeid and rd.rd_roleid=r.role_id "
						+ "and r.role_id="+userInfo.getUserType();
				List<Object[]> orgChildrenNodeList=OracleJdbcUtis.query(orgChildrenNodeSql);
				List<Object[]> deviceTypeChildrenNodeList=OracleJdbcUtis.query(deviceTypeChildrenNodeSql);
				for(Object[] orgChildrenNodeObj:orgChildrenNodeList){
					userInfo.getOrgChildrenNode().add(StringManagerUtils.stringToInteger(orgChildrenNodeObj[0]+""));
				}
				for(Object[] deviceTypeChildrenNodeObj:deviceTypeChildrenNodeList){
					userInfo.getDeviceTypeChildrenNode().add(StringManagerUtils.stringToInteger(deviceTypeChildrenNodeObj[0]+""));
				}
				
				String key=userInfo.getUserNo()+"";
				jedis.hset("UserInfo".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(userInfo));//哈希(Hash)
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static void loadUserInfoByRoleId(String roleId,String method){//condition 0 -用户id 1-用户账号
		List<String> roleList=new ArrayList<String>();
        String sql="select t.user_no from TBL_USER t where t.user_type="+roleId;
        try {
        	List<Object[]> list=OracleJdbcUtis.query(sql);
			for(Object[] obj:list){
				roleList.add(obj[0]+"");
			}
			if(roleList.size()>0){
				loadUserInfo(roleList,0,method);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadUserInfoByOrgId(String orgId,String method){//condition 0 -用户id 1-用户账号
		List<String> roleList=new ArrayList<String>();
        String sql="select t.user_no from TBL_USER t where t.user_orgid="+orgId;
        try {
        	List<Object[]> list=OracleJdbcUtis.query(sql);
			for(Object[] obj:list){
				roleList.add(obj[0]+"");
			}
			if(roleList.size()>0){
				loadUserInfo(roleList,0,method);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public static void loadSRPWorkType(){
//		Jedis jedis=null;
//		try {
//			jedis = RedisUtil.jedisPool.getResource();
//			String sql="select t.id,t.resultcode,t.resultname,t.resultdescription,t.optimizationsuggestion,t.remark "
//					+ " from TBL_SRP_WORKTYPE t order by t.resultcode";
//			List<Object[]> list=OracleJdbcUtis.query(sql);
//			for(Object[] obj:list){
//				WorkType workType=new WorkType();
//				workType.setId(StringManagerUtils.stringToInteger(obj[0]+""));
//				workType.setResultCode(StringManagerUtils.stringToInteger(obj[1]+""));
//				workType.setResultName(obj[2]+"");
//				workType.setResultDescription(obj[3]+"");
//				workType.setOptimizationSuggestion(obj[4]+"");
//				workType.setRemark(obj[5]+"");
//				String key=workType.getResultCode()+"";
//				String keyByName=workType.getResultName()+"";
//				
//				jedis.hset("SRPWorkType".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(workType));//哈希(Hash)
//				jedis.hset("SRPWorkTypeByName".getBytes(), keyByName.getBytes(), SerializeObjectUnils.serialize(workType));//哈希(Hash)
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//		} finally{
//			if(jedis!=null){
//				jedis.close();
//			}
//		}
//	}
	
//	public static WorkType getWorkTypeByCode(String resultCode){
//		WorkType workType=null;
//		Jedis jedis=null;
//		if(!existsKey("SRPWorkType")){
//			MemoryDataManagerTask.loadSRPWorkType();
//		}
//		try {
//			jedis = RedisUtil.jedisPool.getResource();
//			if(jedis.hexists("SRPWorkType".getBytes(), (resultCode).getBytes())){
//				workType=(WorkType) SerializeObjectUnils.unserizlize(jedis.hget("SRPWorkType".getBytes(), (resultCode).getBytes()));
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//		} finally{
//			if(jedis!=null){
//				jedis.close();
//			}
//		}
//		return workType;
//	}
	
//	public static int getResultCodeByName(String resultName){
//		int resultCode=0;
//		Jedis jedis=null;
//		if(!existsKey("SRPWorkTypeByName")){
//			MemoryDataManagerTask.loadSRPWorkType();
//		}
//		try {
//			jedis = RedisUtil.jedisPool.getResource();
//			if(jedis.hexists("SRPWorkTypeByName".getBytes(), (resultName).getBytes())){
//				WorkType workType=(WorkType) SerializeObjectUnils.unserizlize(jedis.hget("SRPWorkTypeByName".getBytes(), (resultName).getBytes()));
//				resultCode=workType.getResultCode();
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//		} finally{
//			if(jedis!=null){
//				jedis.close();
//			}
//		}
//		return resultCode;
//	}
	
//	public static WorkType getWorkTypeByName(String resultName){
//		WorkType workType=null;
//		Jedis jedis=null;
//		if(!existsKey("SRPWorkTypeByName")){
//			MemoryDataManagerTask.loadSRPWorkType();
//		}
//		try {
//			jedis = RedisUtil.jedisPool.getResource();
//			if(jedis.hexists("SRPWorkTypeByName".getBytes(), (resultName).getBytes())){
//				workType=(WorkType) SerializeObjectUnils.unserizlize(jedis.hget("SRPWorkTypeByName".getBytes(), (resultName).getBytes()));
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//		} finally{
//			if(jedis!=null){
//				jedis.close();
//			}
//		}
//		return workType;
//	}
	
	public static AlarmShowStyle getAlarmShowStyle(){
		AlarmShowStyle alarmShowStyle=null;
		Jedis jedis=null;
		if(!existsKey("AlarmShowStyle")){
			MemoryDataManagerTask.initAlarmStyle();
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			alarmShowStyle=(AlarmShowStyle) SerializeObjectUnils.unserizlize(jedis.get("AlarmShowStyle".getBytes()));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return alarmShowStyle;
	}
	
	@SuppressWarnings("resource")
	public static AlarmShowStyle initAlarmStyle(){
		Connection conn = null;   
		PreparedStatement pstmt = null;  
		Statement stmt = null;  
		ResultSet rs = null;
		Jedis jedis=null;
		AlarmShowStyle alarmShowStyle=null;
		try {
			alarmShowStyle=new AlarmShowStyle();
			
			String sql="select v1.itemvalue as alarmLevel,v1.itemname as backgroundColor,v2.itemname as color,v3.itemname as opacity from "
					+ " (select * from tbl_code t where t.itemcode='ALARMBACKCOLOR' ) v1,"
					+ " (select * from tbl_code t where t.itemcode='ALARMFORECOLOR' ) v2,"
					+ " (select * from tbl_code t where t.itemcode='ALARMCOLOROPACITY' ) v3 "
					+ " where v1.itemvalue=v2.itemvalue and v1.itemvalue=v3.itemvalue "
					+ " order by v1.itemvalue ";
			String commAlarmSql="select v1.itemvalue as alarmLevel,v1.itemname as backgroundColor,v2.itemname as color,v3.itemname as opacity from "
					+ " (select * from tbl_code t where t.itemcode='COMMALARMBACKCOLOR' ) v1,"
					+ " (select * from tbl_code t where t.itemcode='COMMALARMFORECOLOR' ) v2,"
					+ " (select * from tbl_code t where t.itemcode='COMMALARMCOLOROPACITY' ) v3 "
					+ " where v1.itemvalue=v2.itemvalue and v1.itemvalue=v3.itemvalue "
					+ " order by v1.itemvalue ";
			String runAlarmSql="select v1.itemvalue as alarmLevel,v1.itemname as backgroundColor,v2.itemname as color,v3.itemname as opacity from "
					+ " (select * from tbl_code t where t.itemcode='RUNALARMBACKCOLOR' ) v1,"
					+ " (select * from tbl_code t where t.itemcode='RUNALARMFORECOLOR' ) v2,"
					+ " (select * from tbl_code t where t.itemcode='RUNALARMCOLOROPACITY' ) v3 "
					+ " where v1.itemvalue=v2.itemvalue and v1.itemvalue=v3.itemvalue "
					+ " order by v1.itemvalue ";
			conn=OracleJdbcUtis.getConnection();
			if(conn==null){
				return null;
			}
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				if(rs.getInt(1)==0){
					alarmShowStyle.getData().getNormal().setValue(rs.getInt(1));
					alarmShowStyle.getData().getNormal().setBackgroundColor(rs.getString(2));
					alarmShowStyle.getData().getNormal().setColor(rs.getString(3));
					alarmShowStyle.getData().getNormal().setOpacity(rs.getString(4));
				}else if(rs.getInt(1)==100){
					alarmShowStyle.getData().getFirstLevel().setValue(rs.getInt(1));
					alarmShowStyle.getData().getFirstLevel().setBackgroundColor(rs.getString(2));
					alarmShowStyle.getData().getFirstLevel().setColor(rs.getString(3));
					alarmShowStyle.getData().getFirstLevel().setOpacity(rs.getString(4));
				}else if(rs.getInt(1)==200){
					alarmShowStyle.getData().getSecondLevel().setValue(rs.getInt(1));
					alarmShowStyle.getData().getSecondLevel().setBackgroundColor(rs.getString(2));
					alarmShowStyle.getData().getSecondLevel().setColor(rs.getString(3));
					alarmShowStyle.getData().getSecondLevel().setOpacity(rs.getString(4));
				}else if(rs.getInt(1)==300){
					alarmShowStyle.getData().getThirdLevel().setValue(rs.getInt(1));
					alarmShowStyle.getData().getThirdLevel().setBackgroundColor(rs.getString(2));
					alarmShowStyle.getData().getThirdLevel().setColor(rs.getString(3));
					alarmShowStyle.getData().getThirdLevel().setOpacity(rs.getString(4));
				}	
			}
			pstmt = conn.prepareStatement(commAlarmSql); 
			rs=pstmt.executeQuery();
			while(rs.next()){
				if(rs.getInt(1)==0){
					alarmShowStyle.getComm().getOffline().setValue(rs.getInt(1));
					alarmShowStyle.getComm().getOffline().setBackgroundColor(rs.getString(2));
					alarmShowStyle.getComm().getOffline().setColor(rs.getString(3));
					alarmShowStyle.getComm().getOffline().setOpacity(rs.getString(4));
				}else if(rs.getInt(1)==1){
					alarmShowStyle.getComm().getOnline().setValue(rs.getInt(1));
					alarmShowStyle.getComm().getOnline().setBackgroundColor(rs.getString(2));
					alarmShowStyle.getComm().getOnline().setColor(rs.getString(3));
					alarmShowStyle.getComm().getOnline().setOpacity(rs.getString(4));
				}else if(rs.getInt(1)==2){
					alarmShowStyle.getComm().getGoOnline().setValue(rs.getInt(1));
					alarmShowStyle.getComm().getGoOnline().setBackgroundColor(rs.getString(2));
					alarmShowStyle.getComm().getGoOnline().setColor(rs.getString(3));
					alarmShowStyle.getComm().getGoOnline().setOpacity(rs.getString(4));
				}
			}
			pstmt = conn.prepareStatement(runAlarmSql); 
			rs=pstmt.executeQuery();
			while(rs.next()){
				if(rs.getInt(1)==0){
					alarmShowStyle.getRun().getStop().setValue(rs.getInt(1));
					alarmShowStyle.getRun().getStop().setBackgroundColor(rs.getString(2));
					alarmShowStyle.getRun().getStop().setColor(rs.getString(3));
					alarmShowStyle.getRun().getStop().setOpacity(rs.getString(4));
				}else if(rs.getInt(1)==1){
					alarmShowStyle.getRun().getRun().setValue(rs.getInt(1));
					alarmShowStyle.getRun().getRun().setBackgroundColor(rs.getString(2));
					alarmShowStyle.getRun().getRun().setColor(rs.getString(3));
					alarmShowStyle.getRun().getRun().setOpacity(rs.getString(4));
				}else if(rs.getInt(1)==2){
					alarmShowStyle.getRun().getNoData().setValue(rs.getInt(1));
					alarmShowStyle.getRun().getNoData().setBackgroundColor(rs.getString(2));
					alarmShowStyle.getRun().getNoData().setColor(rs.getString(3));
					alarmShowStyle.getRun().getNoData().setOpacity(rs.getString(4));
				}
			}
			
			jedis = RedisUtil.jedisPool.getResource();
			jedis.set("AlarmShowStyle".getBytes(), SerializeObjectUnils.serialize(alarmShowStyle));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return alarmShowStyle;
	}
	
	public static ModbusProtocolConfig getModbusProtocolConfig(){
		Jedis jedis=null;
		ModbusProtocolConfig modbusProtocolConfig=null;
		if(!existsKey("modbusProtocolConfig")){
			MemoryDataManagerTask.loadProtocolConfig("");
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis.exists("modbusProtocolConfig".getBytes())){
				modbusProtocolConfig=(ModbusProtocolConfig)SerializeObjectUnils.unserizlize(jedis.get("modbusProtocolConfig".getBytes()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return modbusProtocolConfig;
	}
	
	public static ModbusProtocolConfig getModbusProtocolConfigWithoutReLoad(){
		Jedis jedis=null;
		ModbusProtocolConfig modbusProtocolConfig=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis.exists("modbusProtocolConfig".getBytes())){
				modbusProtocolConfig=(ModbusProtocolConfig)SerializeObjectUnils.unserizlize(jedis.get("modbusProtocolConfig".getBytes()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return modbusProtocolConfig;
	}
	
	public static ModbusProtocolConfig.Items getProtocolItem(ModbusProtocolConfig.Protocol protocol,String itemName){
		ModbusProtocolConfig.Items rtnItem=null;
		if(StringManagerUtils.isNotNull(itemName) && protocol!=null && protocol.getItems()!=null && protocol.getItems().size()>0){
			for(ModbusProtocolConfig.Items item:protocol.getItems()){
				if(itemName.equalsIgnoreCase(item.getTitle())){
					rtnItem=item;
					break;
				}
			}
		}
		return rtnItem;
	}
	
	public static ModbusProtocolConfig.ExtendedField getProtocolExtendedField(ModbusProtocolConfig.Protocol protocol,String itemName){
		ModbusProtocolConfig.ExtendedField r=null;
		if(StringManagerUtils.isNotNull(itemName) && protocol!=null && protocol.getExtendedFields()!=null && protocol.getExtendedFields().size()>0){
			for(ModbusProtocolConfig.ExtendedField extendedField:protocol.getExtendedFields()){
				if(itemName.equalsIgnoreCase(extendedField.getTitle())){
					r=extendedField;
					break;
				}
			}
		}
		return r;
	}
	
	public static ModbusProtocolConfig.Protocol getProtocolByCode(String protocolCode){
		ModbusProtocolConfig modbusProtocolConfig=getModbusProtocolConfig();
		ModbusProtocolConfig.Protocol protocol=null;
		if(StringManagerUtils.isNotNull(protocolCode)){
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(protocolCode.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getCode())){
					protocol=modbusProtocolConfig.getProtocol().get(i);
					break;
				}
			}
		}
		return protocol;
	}
	
	public static ModbusProtocolConfig.Protocol getProtocolByName(String protocolName){
		ModbusProtocolConfig modbusProtocolConfig=getModbusProtocolConfig();
		ModbusProtocolConfig.Protocol protocol=null;
		if(StringManagerUtils.isNotNull(protocolName)){
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getName())){
					protocol=modbusProtocolConfig.getProtocol().get(i);
					break;
				}
			}
		}
		return protocol;
	}
	
	public static SRPDeviceTodayData getSRPDeviceTodayDataById(int deviceId){
		SRPDeviceTodayData deviceTodayData=null;
		Jedis jedis=null;
		String key=deviceId+"";
		if(!existsKey("SRPDeviceTodayData")){
			MemoryDataManagerTask.loadTodayFESDiagram(null,0);
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis.hexists("SRPDeviceTodayData".getBytes(), key.getBytes())){
				deviceTodayData=(SRPDeviceTodayData) SerializeObjectUnils.unserizlize(jedis.hget("SRPDeviceTodayData".getBytes(), key.getBytes()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return deviceTodayData;
	}
	
	public static void updateSRPDeviceTodayDataDeviceInfo(SRPDeviceTodayData deviceTodayData){
		if(deviceTodayData!=null){
			Jedis jedis=null;
			if(!existsKey("SRPDeviceTodayData")){
				MemoryDataManagerTask.loadTodayFESDiagram(null,0);
			}
			try {
				jedis = RedisUtil.jedisPool.getResource();
				jedis.hset("SRPDeviceTodayData".getBytes(), (deviceTodayData.getId()+"").getBytes(), SerializeObjectUnils.serialize(deviceTodayData));
			}catch (Exception e) {
				e.printStackTrace();
			} finally{
				if(jedis!=null){
					jedis.close();
				}
			}
		}
	}
	
	public static void updateDeviceRealtimeTotalData(RealtimeTotalInfo realtimeTotalInfo){
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			jedis.hset("DeviceRealtimeTotalData".getBytes(), (realtimeTotalInfo.getDeviceId()+"").getBytes(), SerializeObjectUnils.serialize(realtimeTotalInfo));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static RealtimeTotalInfo getDeviceRealtimeTotalDataById(String deviceId){
		Jedis jedis=null;
		RealtimeTotalInfo realtimeTotalInfo=null;
		if(!existsKey("DeviceRealtimeTotalData")){
			MemoryDataManagerTask.loadDeviceRealtimeTotalData(null,0);
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis.hexists("DeviceRealtimeTotalData".getBytes(), deviceId.getBytes())){
				realtimeTotalInfo=(RealtimeTotalInfo) SerializeObjectUnils.unserizlize(jedis.hget("DeviceRealtimeTotalData".getBytes(), deviceId.getBytes()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return realtimeTotalInfo;
	}
	
	public static RealtimeTotalInfo getDeviceRealtimeTotalDataFromDbById(String deviceId){
		RealtimeTotalInfo realtimeTotalInfo=null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String sql="select t2.id,t2.devicename,to_char(t.caltime,'yyyy-mm-dd hh24:mi:ss'),"
				+ " t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,"
				+ " t.runstatus,t.runtimeefficiency,t.runtime,t.runrange,"
				+ " t.caldata "
				+ " from tbl_realtimetotalcalculationdata t,tbl_device t2 "
				+ " where t.deviceid=t2.id"
				+ " and t2.id ="+deviceId;
		List<Object[]> list=OracleJdbcUtis.query(sql);
		if(list.size()==0){
			sql="select t2.id,t2.devicename,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss'),"
					+ " t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,"
					+ " t.runstatus,t.runtimeefficiency,t.runtime,t.runrange,"
					+ " '' "
					+ " from tbl_device t2 "
					+ " left outer join tbl_acqdata_latest t on t.deviceid=t2.id"
					+ " where t2.id ="+deviceId;
			list=OracleJdbcUtis.query(sql);
		}
		if(list.size()>0){
			Object[] obj=list.get(0);
			realtimeTotalInfo=new RealtimeTotalInfo();
			String deviceIdStr=obj[0]+"";
			if(StringManagerUtils.isNum(deviceIdStr)){
				String calData=obj[11]+"";
				String key=deviceIdStr;
				type = new TypeToken<Map<String,RealtimeTotalInfo.TotalItem>>() {}.getType();
				Map<String,RealtimeTotalInfo.TotalItem> calDataMap=gson.fromJson(calData, type);
				
				realtimeTotalInfo.setDeviceId(StringManagerUtils.stringToInteger(deviceIdStr));
				realtimeTotalInfo.setDeviceName(obj[1]+"");
				realtimeTotalInfo.setAcqTime(obj[2]+"");
				
				realtimeTotalInfo.setCommStatus(StringManagerUtils.stringToInteger(obj[3]+""));
				realtimeTotalInfo.setCommTime(StringManagerUtils.stringToFloat(obj[4]+""));
				realtimeTotalInfo.setCommEff(StringManagerUtils.stringToFloat(obj[5]+""));
				realtimeTotalInfo.setCommRange(obj[6]+"");
				
				realtimeTotalInfo.setOnLineCommStatus(StringManagerUtils.stringToInteger(obj[3]+""));
				realtimeTotalInfo.setOnLineCommTime(StringManagerUtils.stringToFloat(obj[4]+""));
				realtimeTotalInfo.setOnLineCommEff(StringManagerUtils.stringToFloat(obj[5]+""));
				realtimeTotalInfo.setOnLineCommRange(obj[6]+"");
				
				realtimeTotalInfo.setRunStatus(StringManagerUtils.stringToInteger(obj[7]+""));
				realtimeTotalInfo.setRunTime(StringManagerUtils.stringToFloat(obj[8]+""));
				realtimeTotalInfo.setRunEff(StringManagerUtils.stringToFloat(obj[9]+""));
				realtimeTotalInfo.setRunRange(obj[10]+"");
				
				realtimeTotalInfo.setTotalItemMap(calDataMap);
			}
		}
		return realtimeTotalInfo;
	}
	
	public static List<RealtimeTotalInfo> getDeviceRealtimeTotalData(){
		Jedis jedis=null;
		List<RealtimeTotalInfo> list =new ArrayList<>();
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("DeviceRealtimeTotalData".getBytes())){
				MemoryDataManagerTask.loadDeviceRealtimeTotalData(null,0);
			}
			List<byte[]> byteList =jedis.hvals("DeviceRealtimeTotalData".getBytes());
			for(int i=0;i<byteList.size();i++){
				Object obj = SerializeObjectUnils.unserizlize(byteList.get(i));
				if (obj instanceof RealtimeTotalInfo) {
					RealtimeTotalInfo realtimeTotalInfo=(RealtimeTotalInfo)obj;
					list.add(realtimeTotalInfo);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return list;
	}
	
	public static void loadDeviceRealtimeTotalData(List<String> wellList,int condition){//condition 0 -设备ID 1-设备名称
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			
			String wells="";
			if(condition==0){
				wells=StringUtils.join(wellList, ",");
			}else{
				wells=StringManagerUtils.joinStringArr2(wellList, ",");
			}	
					
			String sql="select t2.id,t2.devicename,to_char(t.caltime,'yyyy-mm-dd hh24:mi:ss'),"
					+ " t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,"
					+ " t.runstatus,t.runtimeefficiency,t.runtime,t.runrange,"
					+ " t.caldata "
					+ " from tbl_realtimetotalcalculationdata t,tbl_device t2 "
					+ " where t.deviceid=t2.id";
			if(StringManagerUtils.isNotNull(wells)){
				if(condition==0){
					sql+=" and t2.id in("+wells+")";
				}else{
					sql+=" and t2.deviceName in("+wells+")";
				}
			}
			sql+= " order by t2.id";

			List<Object[]> list=OracleJdbcUtis.query(sql);
			
			for(Object[] obj:list){
				RealtimeTotalInfo realtimeTotalInfo=new RealtimeTotalInfo();
				String deviceIdStr=obj[0]+"";
				if(StringManagerUtils.isNum(deviceIdStr)){
					String calData=obj[11]+"";
					String key=deviceIdStr;
					type = new TypeToken<Map<String,RealtimeTotalInfo.TotalItem>>() {}.getType();
					Map<String,RealtimeTotalInfo.TotalItem> calDataMap=gson.fromJson(calData, type);
					
					realtimeTotalInfo.setDeviceId(StringManagerUtils.stringToInteger(deviceIdStr));
					realtimeTotalInfo.setDeviceName(obj[1]+"");
					realtimeTotalInfo.setAcqTime(obj[2]+"");
					
					realtimeTotalInfo.setCommStatus(StringManagerUtils.stringToInteger(obj[3]+""));
					realtimeTotalInfo.setCommTime(StringManagerUtils.stringToFloat(obj[4]+""));
					realtimeTotalInfo.setCommEff(StringManagerUtils.stringToFloat(obj[5]+""));
					realtimeTotalInfo.setCommRange(obj[6]+"");
					
					realtimeTotalInfo.setOnLineCommStatus(StringManagerUtils.stringToInteger(obj[3]+""));
					realtimeTotalInfo.setOnLineCommTime(StringManagerUtils.stringToFloat(obj[4]+""));
					realtimeTotalInfo.setOnLineCommEff(StringManagerUtils.stringToFloat(obj[5]+""));
					realtimeTotalInfo.setOnLineCommRange(obj[6]+"");
					
					realtimeTotalInfo.setRunStatus(StringManagerUtils.stringToInteger(obj[7]+""));
					realtimeTotalInfo.setRunTime(StringManagerUtils.stringToFloat(obj[8]+""));
					realtimeTotalInfo.setRunEff(StringManagerUtils.stringToFloat(obj[9]+""));
					realtimeTotalInfo.setRunRange(obj[10]+"");
					
					realtimeTotalInfo.setTotalItemMap(calDataMap);
					
					jedis.hset("DeviceRealtimeTotalData".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(realtimeTotalInfo));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	
	public static void loadTodayFESDiagram(List<String> wellList,int condition){//condition 0 -设备ID 1-设备名称
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
			String currentDate=StringManagerUtils.getCurrentTime("yyyy-MM-dd");
			String currentTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			if(!StringManagerUtils.timeMatchDate(currentTime, currentDate, Config.getInstance().configFile.getAp().getReport().getOffsetHour())){
				currentDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(currentDate),-1);
			}
			
			String wells="";
			if(condition==0){
				wells=StringUtils.join(wellList, ",");
			}else{
				wells=StringManagerUtils.joinStringArr2(wellList, ",");
			}	
					
			String sql=" select t2.id as wellId,t2.devicename,"//2
					+ " to_char(t.fesdiagramacqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"//3
					+ " t.stroke,t.spm,t.fmax,t.fmin,t.fullnesscoefficient,t.resultcode,"//9
					+ " t.theoreticalproduction,"//10
					+ " t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,"//13
					+ " t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,"//16
					+ " t.wattdegreebalance,t.idegreebalance,t.deltaradius,"//19
					+ " t.systemefficiency,t.surfacesystemefficiency,t.welldownsystemefficiency,t.energyper100mlift,"//23
					+ " t.pumpeff1,t.pumpeff2,t.pumpeff3,t.pumpeff4,t.pumpeff,"//28
					+ " t.productiondata, "//29
					+"  t.calcProducingfluidLevel, "//30
					+"  t.levelDifferenceValue, "//31
					+ " t.submergence "//32
					+ " from tbl_srpacqdata_hist t,tbl_device t2 "
					+ " where t.deviceid=t2.id  "
					+ " and t2.calculateType=1"
					+ " and t.resultstatus=1"
					+ " and t.fesdiagramacqtime between to_date('"+currentDate+"','yyyy-mm-dd')+"+offsetHour+"/24 and to_date('"+currentDate+"','yyyy-mm-dd')+"+offsetHour+"/24+1 ";
			if(StringManagerUtils.isNotNull(wells)){
				if(condition==0){
					sql+=" and t2.id in("+wells+")";
				}else{
					sql+=" and t2.deviceName in("+wells+")";
				}
			}
			sql+= "order by t2.id, t.fesdiagramacqtime";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				int deviceId=rs.getInt(1);
				String key=deviceId+"";
				SRPCalculateResponseData responseData =new SRPCalculateResponseData(); 
				responseData.init();
				
				responseData.setWellName(rs.getString(2));
				responseData.getFESDiagram().setAcqTime(rs.getString(3));
				responseData.getFESDiagram().setStroke(rs.getFloat(4));
				responseData.getFESDiagram().setSPM(rs.getFloat(5));
				responseData.getFESDiagram().getFMax().add(rs.getFloat(6));
				responseData.getFESDiagram().getFMin().add(rs.getFloat(7));
				responseData.getFESDiagram().setFullnessCoefficient(rs.getFloat(8));
				responseData.getCalculationStatus().setResultCode(rs.getInt(9));
				
				responseData.getProduction().setTheoreticalProduction(rs.getFloat(10));
				responseData.getProduction().setLiquidVolumetricProduction(rs.getFloat(11));
				responseData.getProduction().setOilVolumetricProduction(rs.getFloat(12));
				responseData.getProduction().setWaterVolumetricProduction(rs.getFloat(13));
				responseData.getProduction().setLiquidWeightProduction(rs.getFloat(14));
				responseData.getProduction().setOilWeightProduction(rs.getFloat(15));
				responseData.getProduction().setWaterWeightProduction(rs.getFloat(16));
				
				responseData.getFESDiagram().setWattDegreeBalance(rs.getFloat(17));
				responseData.getFESDiagram().setIDegreeBalance(rs.getFloat(18));
				responseData.getFESDiagram().setDeltaRadius(rs.getFloat(19));
				
				responseData.getSystemEfficiency().setSystemEfficiency(rs.getFloat(20));
				responseData.getSystemEfficiency().setSurfaceSystemEfficiency(rs.getFloat(21));
				responseData.getSystemEfficiency().setWellDownSystemEfficiency(rs.getFloat(22));
				responseData.getSystemEfficiency().setEnergyPer100mLift(rs.getFloat(23));
				
				responseData.getPumpEfficiency().setPumpEff1(rs.getFloat(24));
				responseData.getPumpEfficiency().setPumpEff2(rs.getFloat(25));
				responseData.getPumpEfficiency().setPumpEff3(rs.getFloat(26));
				responseData.getPumpEfficiency().setPumpEff4(rs.getFloat(27));
				responseData.getPumpEfficiency().setPumpEff(rs.getFloat(28));
				
				String productionData=rs.getString(29)+"";
				if(StringManagerUtils.isNotNull(productionData)){
					type = new TypeToken<SRPDeviceInfo>() {}.getType();
					SRPDeviceInfo srpProductionData=gson.fromJson(productionData, type);
					if(srpProductionData!=null){
						responseData.getProduction().setWaterCut(srpProductionData.getProduction().getWaterCut());
						responseData.getProduction().setWeightWaterCut(srpProductionData.getProduction().getWeightWaterCut());
						responseData.getProduction().setTubingPressure(srpProductionData.getProduction().getTubingPressure());
						responseData.getProduction().setCasingPressure(srpProductionData.getProduction().getCasingPressure());
						responseData.getProduction().setPumpSettingDepth(srpProductionData.getProduction().getPumpSettingDepth());
						responseData.getProduction().setProducingfluidLevel(srpProductionData.getProduction().getProducingfluidLevel());
					}
				}
				
				responseData.getProduction().setCalcProducingfluidLevel(rs.getFloat(30));
				responseData.getProduction().setLevelDifferenceValue(rs.getFloat(31));
				responseData.getProduction().setSubmergence(rs.getFloat(32));
				
				if(jedis.hexists("SRPDeviceTodayData".getBytes(), key.getBytes())){
					SRPDeviceTodayData deviceTodayData =(SRPDeviceTodayData) SerializeObjectUnils.unserizlize(jedis.hget("SRPDeviceTodayData".getBytes(), key.getBytes()));
					deviceTodayData.getSRPCalculateList().add(responseData);
					jedis.hset("SRPDeviceTodayData".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(deviceTodayData));
				}else{
					SRPDeviceTodayData deviceTodayData=new SRPDeviceTodayData();
					deviceTodayData.setId(deviceId);
					deviceTodayData.setSRPCalculateList(new ArrayList<SRPCalculateResponseData>());
					deviceTodayData.setAcquisitionItemInfoList(new ArrayList<AcquisitionItemInfo>());
					deviceTodayData.getSRPCalculateList().add(responseData);
					jedis.hset("SRPDeviceTodayData".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(deviceTodayData));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	
	public static PCPDeviceTodayData getPCPDeviceTodayDataById(int deviceId){
		PCPDeviceTodayData deviceTodayData=null;
		Jedis jedis=null;
		String key=deviceId+"";
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("PCPDeviceTodayData".getBytes())){
				MemoryDataManagerTask.loadTodayRPMData(null,0);
			}
			if(jedis.hexists("PCPDeviceTodayData".getBytes(), key.getBytes())){
				deviceTodayData=(PCPDeviceTodayData) SerializeObjectUnils.unserizlize(jedis.hget("PCPDeviceTodayData".getBytes(), key.getBytes()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return deviceTodayData;
	}
	
	public static void updatePCPDeviceTodayDataDeviceInfo(PCPDeviceTodayData deviceTodayData){
		if(deviceTodayData!=null){
			Jedis jedis=null;
			try {
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("PCPDeviceTodayData".getBytes())){
					MemoryDataManagerTask.loadTodayRPMData(null,0);
				}
				jedis.hset("PCPDeviceTodayData".getBytes(), (deviceTodayData.getId()+"").getBytes(), SerializeObjectUnils.serialize(deviceTodayData));
			}catch (Exception e) {
				e.printStackTrace();
			} finally{
				if(jedis!=null){
					jedis.close();
				}
			}
		}
	}
	
	public static void loadTodayRPMData(List<String> wellList,int condition){//condition 0 -设备ID 1-设备名称
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
			String currentDate=StringManagerUtils.getCurrentTime("yyyy-MM-dd");
			String currentTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			if(!StringManagerUtils.timeMatchDate(currentTime, currentDate, Config.getInstance().configFile.getAp().getReport().getOffsetHour())){
				currentDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(currentDate),-1);
			}
			String wells="";
			if(condition==0){
				wells=StringUtils.join(wellList, ",");
			}else{
				wells=StringManagerUtils.joinStringArr2(wellList, ",");
			}	
					
			String sql="select t2.id,t2.devicename,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
					+ "t.rpm,t.resultcode,"
					+ "t.theoreticalproduction,"
					+ "t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,"
					+ "t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,"
					+ "t.systemefficiency,"
					+ "t.pumpeff1,t.pumpeff2,t.pumpeff,"
					+ "t.productiondata,"
					+ "t.submergence "
					+ " from tbl_pcpacqdata_hist t,tbl_device t2 "
					+ " where t.deviceid=t2.id and t.resultstatus=1 "
					+ " and t.acqtime between to_date('"+currentDate+"','yyyy-mm-dd')+"+offsetHour+"/24 and to_date('"+currentDate+"','yyyy-mm-dd')+"+offsetHour+"/24+1";
			if(StringManagerUtils.isNotNull(wells)){
				if(condition==0){
					sql+=" and t2.id in("+wells+")";
				}else{
					sql+=" and t2.deviceName in("+wells+")";
				}
				
			}
			sql+= "order by t2.id, t.acqtime";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				int deviceId=rs.getInt(1);
				String key=deviceId+"";
				
				PCPCalculateResponseData responseData =new PCPCalculateResponseData(); 
				responseData.init();
				
				responseData.setWellName(rs.getString(2));
				responseData.setAcqTime(rs.getString(3));
				
				responseData.setRPM(rs.getFloat(4));
				responseData.getCalculationStatus().setResultCode(rs.getInt(5));
				
				responseData.getProduction().setTheoreticalProduction(rs.getFloat(6));
				responseData.getProduction().setLiquidVolumetricProduction(rs.getFloat(7));
				responseData.getProduction().setOilVolumetricProduction(rs.getFloat(8));
				responseData.getProduction().setWaterVolumetricProduction(rs.getFloat(9));
				responseData.getProduction().setLiquidWeightProduction(rs.getFloat(10));
				responseData.getProduction().setOilWeightProduction(rs.getFloat(11));
				responseData.getProduction().setWaterWeightProduction(rs.getFloat(12));
				
				
				responseData.getSystemEfficiency().setSystemEfficiency(rs.getFloat(13));
				
				responseData.getPumpEfficiency().setPumpEff1(rs.getFloat(14));
				responseData.getPumpEfficiency().setPumpEff2(rs.getFloat(15));
				responseData.getPumpEfficiency().setPumpEff(rs.getFloat(16));
				
				String productionData=rs.getString(17)+"";
				if(StringManagerUtils.isNotNull(productionData)){
					type = new TypeToken<PCPDeviceInfo>() {}.getType();
					PCPDeviceInfo pcpProductionData=gson.fromJson(productionData, type);
					if(pcpProductionData!=null){
						responseData.getProduction().setWaterCut(pcpProductionData.getProduction().getWaterCut());
						responseData.getProduction().setWeightWaterCut(pcpProductionData.getProduction().getWeightWaterCut());
						responseData.getProduction().setTubingPressure(pcpProductionData.getProduction().getTubingPressure());
						responseData.getProduction().setCasingPressure(pcpProductionData.getProduction().getCasingPressure());
						responseData.getProduction().setPumpSettingDepth(pcpProductionData.getProduction().getPumpSettingDepth());
						responseData.getProduction().setProducingfluidLevel(pcpProductionData.getProduction().getProducingfluidLevel());
					}
				}
				responseData.getProduction().setSubmergence(rs.getFloat(18));
				
				if(jedis.hexists("PCPDeviceTodayData".getBytes(), key.getBytes())){
					PCPDeviceTodayData deviceTodayData =(PCPDeviceTodayData) SerializeObjectUnils.unserizlize(jedis.hget("PCPDeviceTodayData".getBytes(), key.getBytes()));
					deviceTodayData.getPCPCalculateList().add(responseData);
					jedis.hset("PCPDeviceTodayData".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(deviceTodayData));
				}else{
					PCPDeviceTodayData deviceTodayData=new PCPDeviceTodayData();
					deviceTodayData.setId(deviceId);
					deviceTodayData.setPCPCalculateList(new ArrayList<PCPCalculateResponseData>());
					deviceTodayData.setAcquisitionItemInfoList(new ArrayList<AcquisitionItemInfo>());
					deviceTodayData.getPCPCalculateList().add(responseData);
					jedis.hset("PCPDeviceTodayData".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(deviceTodayData));
				}
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public static void loadUIKitAccessToken(List<Integer> idList,String method ){
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			String ids=StringUtils.join(idList, ",");
			if("delete".equalsIgnoreCase(method)){
				for(int i=0;i<idList.size();i++){
					jedis.hdel("UIKitAccessToken".getBytes(), idList.get(i).toString().getBytes());
				}
			}else{
				String sql="select t.id,t.orgid,t.account,t.appkey,t.secret from TBL_VIDEOKEY t "
						+ " where 1=1 ";
				if(StringManagerUtils.isNotNull(ids)){
					sql+=" and t.id in("+ids+")";
				}
				sql+=" order by t.id";
				List<Object[]> list=OracleJdbcUtis.query(sql);
				for(Object[] obj:list){
					VideoKey videoKey=new VideoKey();
					videoKey.setId(StringManagerUtils.stringToInteger(obj[0]+""));
					videoKey.setOrgId(StringManagerUtils.stringToInteger(obj[1]+""));
					videoKey.setAccount(obj[2]+"");
					videoKey.setAppkey(obj[3]+"");
					videoKey.setSecret(obj[4]+"");
					
					String appKey=videoKey.getAppkey();
					String appSecret=videoKey.getSecret();
					String url="https://open.ys7.com/api/lapp/token/get";
					String requestData="{\"appKey\":\""+appKey+"\",\"appSecret\":\""+appSecret+"\"}";
					String responseData=StringManagerUtils.sendPostMethod(url+"?appKey="+appKey+"&appSecret="+appSecret, requestData,"utf-8",0,0);
					type = new TypeToken<AccessToken>() {}.getType();
					AccessToken accessToken=gson.fromJson(responseData, type);
					
					String key=videoKey.getId()+"";
					jedis.hset("UIKitAccessToken".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(accessToken));//哈希(Hash)
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public static void loadUIKitAccessTokenByName(List<String> nameList,String method ){
		List<Integer> idList =new ArrayList<>();
		String names=StringManagerUtils.joinStringArr2(nameList, ",");
		String sql="select t.id from TBL_VIDEOKEY t "
				+ " where 1=1 ";
		if(StringManagerUtils.isNotNull(names)){
			sql+=" and t.account in("+names+")";
		}
		sql+=" order by t.id";
		
		try {
			List<Object[]> list=OracleJdbcUtis.query(sql);
			for(Object[] obj:list){
				idList.add(StringManagerUtils.stringToInteger(obj[0]+""));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(idList.size()>0){
			loadUIKitAccessToken(idList,method);
		}
	}
	
	public static AccessToken getUIKitAccessTokenById(int id){
		Jedis jedis=null;
		AccessToken accessToken=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("UIKitAccessToken".getBytes())){
				loadUIKitAccessToken(null,"update");
			}else{
				if(jedis.hexists("UIKitAccessToken".getBytes(),(id+"").getBytes())){
					accessToken=(AccessToken)SerializeObjectUnils.unserizlize(jedis.hget("UIKitAccessToken".getBytes(),(id+"").getBytes()));
				}
				if(accessToken==null || (accessToken!=null&& "200".equalsIgnoreCase(accessToken.getCode()) &&new Date().getTime()>accessToken.getData().getExpireTime() )        ){
					List<Integer> loadList=new ArrayList<>();
					loadList.add(id);
					loadUIKitAccessToken(loadList,"update");
					if(jedis.hexists("UIKitAccessToken".getBytes(),(id+"").getBytes())){
						accessToken=(AccessToken)SerializeObjectUnils.unserizlize(jedis.hget("UIKitAccessToken".getBytes(),(id+"").getBytes()));
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return accessToken;
	}
	
	public static class RedisInfo{
		public int status;
		public String version;
		public String os;
		public int arch_bits;
		public int tcp_port;
		public long used_memory;
		public String used_memory_human;
		public long maxmemory;
		public String maxmemory_human;
		public long total_system_memory;
		public String total_system_memory_human;
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		public String getOs() {
			return os;
		}
		public void setOs(String os) {
			this.os = os;
		}
		public int getArch_bits() {
			return arch_bits;
		}
		public void setArch_bits(int arch_bits) {
			this.arch_bits = arch_bits;
		}
		public long getUsed_memory() {
			return used_memory;
		}
		public void setUsed_memory(long used_memory) {
			this.used_memory = used_memory;
		}
		public String getUsed_memory_human() {
			return used_memory_human;
		}
		public void setUsed_memory_human(String used_memory_human) {
			this.used_memory_human = used_memory_human;
		}
		public int getTcp_port() {
			return tcp_port;
		}
		public void setTcp_port(int tcp_port) {
			this.tcp_port = tcp_port;
		}
		public long getMaxmemory() {
			return maxmemory;
		}
		public void setMaxmemory(long maxmemory) {
			this.maxmemory = maxmemory;
		}
		public String getMaxmemory_human() {
			return maxmemory_human;
		}
		public void setMaxmemory_human(String maxmemory_human) {
			this.maxmemory_human = maxmemory_human;
		}
		public long getTotal_system_memory() {
			return total_system_memory;
		}
		public void setTotal_system_memory(long total_system_memory) {
			this.total_system_memory = total_system_memory;
		}
		public String getTotal_system_memory_human() {
			return total_system_memory_human;
		}
		public void setTotal_system_memory_human(String total_system_memory_human) {
			this.total_system_memory_human = total_system_memory_human;
		}
	}
	
	public static class CalItem implements Serializable {
		private static final long serialVersionUID = 1L;
		public String name;
		public String code;
		public String unit;
		public int dataSource;
		public int dataType;
		public String remark;
		
		public CalItem(String name,String code, String unit, int dataType,int dataSource,String remark) {
			super();
			this.name = name;
			this.code = code;
			this.unit = unit;
			this.dataType = dataType;
			this.remark = remark;
			this.dataSource = dataSource;
		}
		public CalItem(String name,String unit) {
			super();
			this.name = name;
			this.unit = unit;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUnit() {
			return unit;
		}
		public void setUnit(String unit) {
			this.unit = unit;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public int getDataType() {
			return dataType;
		}
		public void setDataType(int dataType) {
			this.dataType = dataType;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public int getDataSource() {
			return dataSource;
		}
		public void setDataSource(int dataSource) {
			this.dataSource = dataSource;
		}
	}
	
	public static String getLanguageResourceStr(String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageMap=getLanguageResource(language);
		result_json.append("{");
		if(languageMap!=null){
			Iterator<Map.Entry<String, String>> iterator = languageMap.entrySet().iterator();
			while (iterator.hasNext()) {
			    Map.Entry<String, String> entry = iterator.next();
			    String key = entry.getKey();
			    String value = entry.getValue();
			    result_json.append("\""+key+"\":\""+value+"\",");
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("}");
		return result_json.toString();
	}
	
	
	public static String getLanguageResourceStr_FirstLetterLowercase(String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageMap=getLanguageResource(language);
		result_json.append("{");
		if(languageMap!=null){
			Iterator<Map.Entry<String, String>> iterator = languageMap.entrySet().iterator();
			while (iterator.hasNext()) {
			    Map.Entry<String, String> entry = iterator.next();
			    String key = entry.getKey();
			    String value=StringManagerUtils.stringFormat2(entry.getValue());
			    result_json.append("\""+key+"\":\""+value+"\",");
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("}");
		return result_json.toString();
	}
	
	
	public static String getLanguageResourceItem(String language,String code){
		String value="";
		Map<String,String> languageMap=getLanguageResource(language);
		if(languageMap.containsKey(code)){
			value=languageMap.get(code);
		}
		return value;
	}
	
	public static Map<String,String> getLanguageResource(String language){
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		Map<String,String> languageMap=new LinkedHashMap<>();
		String key="languageResource-"+language;
		if(!dataModelMap.containsKey(key)){
			loadLanguageResource(language);
		}
		if(dataModelMap.containsKey(key)){
			languageMap=(Map<String, String>) dataModelMap.get(key);
		}
		return languageMap;
	}
	
	
	@SuppressWarnings("unchecked")
	public static Map<String,Map<String,Code>> getCode(String language){
		String key="codeLanguageResource-"+language;
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		Map<String,Map<String,Code>> codeMap=new LinkedHashMap<>();
		if(!dataModelMap.containsKey(key)){
			loadLanguageResource(language);
		}
		if(dataModelMap.containsKey(key)){
			codeMap=(Map<String,Map<String,Code>>) dataModelMap.get(key);
		}
		return codeMap;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,Code> getCodeMap(String itemCode,String language){
		String key="codeLanguageResource-"+language;
		Map<String,Code> code=new LinkedHashMap<>();
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		Map<String,Map<String,Code>> codeMap=new LinkedHashMap<>();
		if(!dataModelMap.containsKey(key)){
			loadLanguageResource(language);
		}
		if(dataModelMap.containsKey(key)){
			codeMap=(Map<String,Map<String,Code>>) dataModelMap.get(key);
			if(codeMap.containsKey(itemCode)){
				code=codeMap.get(itemCode);
			}
		}
		return code;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,Code> getCodeMap(String itemCode,String values,String language){
		String key="codeLanguageResource-"+language;
		String[] valueArr=values.split(",");
		Map<String,Code> resultCodeMap=new LinkedHashMap<>();
		Map<String,Code> code=new LinkedHashMap<>();
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		Map<String,Map<String,Code>> codeMap=new LinkedHashMap<>();
		if(!dataModelMap.containsKey(key)){
			loadLanguageResource(language);
		}
		if(dataModelMap.containsKey(key)){
			codeMap=(Map<String,Map<String,Code>>) dataModelMap.get(key);
			if(codeMap.containsKey(itemCode)){
				code=codeMap.get(itemCode);
			}
		}
		if(StringManagerUtils.isNotNull(values) && valueArr!=null && valueArr.length>0){
			for(int i=0;i<valueArr.length;i++){
				if(code.containsKey(valueArr[i])){
					resultCodeMap.put(valueArr[i], code.get(valueArr[i]));
				}
			}
			
//			Iterator<Map.Entry<String,Code>> it = code.entrySet().iterator();
//			while(it.hasNext()){
//				Map.Entry<String,Code> entry = it.next();
//				Code c=entry.getValue();
//				if(StringManagerUtils.existOrNot(valueArr, c.getItemvalue()+"")){
//					resultCodeMap.put(c.getItemvalue()+"", c);
//				}
//			}
		}else{
			resultCodeMap=code;
		}
		return resultCodeMap;
	}
	
	public static Code getCode(String itemCode,String itemValue,String language){
		Map<String,Code> codeMap=getCodeMap(itemCode,language);
		Code code=null;
		if(codeMap.containsKey(itemValue)){
			code=codeMap.get(itemValue);
		}
		return code;
	}
	
	public static Code getCodeByItemName(String itemCode,String itemName,String language){
		Map<String,Code> codeMap=getCodeMap(itemCode,language);
		Code code=null;
		Iterator<Map.Entry<String,Code>> it = codeMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Code> entry = it.next();
			Code c=entry.getValue();
			if(itemName.equalsIgnoreCase(c.getItemname())){
				code=c;
				break;
			}
		}
		return code;
	}
	
	public static String getCodeName(String itemCode,String itemValue,String language){
		String codeName="";
		Code code=getCode(itemCode,itemValue,language);
		if(code!=null){
			codeName=code.getItemname();
		}
		return codeName;
	}
	
	public static String getCodeValue(String itemCode,String itemName,String language){
		String itemValue="";
		Code code=getCodeByItemName(itemCode,itemName,language);
		if(code!=null){
			itemValue=code.getItemvalue()+"";
		}
		
		return itemValue;
	}
	
	public static void loadLanguageResource(){
		loadLanguageResource("zh_CN");
		loadLanguageResource("en");
		loadLanguageResource("ru");
	}
	
	public static void loadLanguageResource(String language){
		language=language.toLowerCase().replace("zh_cn", "zh_CN");
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		Map<String,String> languageMap=new LinkedHashMap<>();
		Map<String,Map<String,Code>> codeMap=new LinkedHashMap<>();
		
		Map<String,WorkType> workTypeMap=new LinkedHashMap<>();
		
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String languageResourcePath=Config.getInstance().configFile.getAp().getOem().getLanguageResourcePath();
		String path=stringManagerUtils.getFilePath(languageResourcePath+"/locale-"+language+".xlsx");
		File file=StringManagerUtils.getFile(path);
		try {
//			JSONArray arr=ExcelUtils.readFile(file);
			Map<String, JSONArray> sheetMap= ExcelUtils.readFileManySheet(file);
			Iterator<Map.Entry<String, JSONArray>> iterator = sheetMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, JSONArray> entry = iterator.next();
			    String key = entry.getKey();
			    if(key.equalsIgnoreCase("general") || key.equalsIgnoreCase("oem") || key.equalsIgnoreCase("calculate") || key.equalsIgnoreCase("function")){
			    	JSONArray arr = entry.getValue();
				    for(int i=0;i<arr.size();i++){
						JSONObject obj=arr.getJSONObject(i);
						String item=obj.getString("FIELD");
						if(StringManagerUtils.isNotNull(item)){
							String value=obj.getString("LANGUAGE");
							if(!key.equalsIgnoreCase("oem")){
								value=StringManagerUtils.stringFormat(value);
							}
							languageMap.put(item, value);
						}
					}
			    }else if(key.equalsIgnoreCase("tbl_code")){
			    	JSONArray arr = entry.getValue();
				    for(int i=0;i<arr.size();i++){
						JSONObject obj=arr.getJSONObject(i);
						String itemCode=obj.getString("ITEMCODE");
						String itemName=obj.getString("ITEMNAME");
						
						if(!"LANGUAGE".equalsIgnoreCase(itemCode)){
							itemName=StringManagerUtils.stringFormat(itemName);
						}
						
						int itemValue=obj.getIntValue("ITEMVALUE");
						String tableCode=obj.getString("TABLECODE");
						String remark=obj.getString("REMARK");
						Code code=new Code();
						code.setItemcode(itemCode);
						code.setItemname(itemName);
						code.setItemvalue(itemValue);
						code.setTablecode(tableCode);
						code.setRemark(remark);
						if(codeMap.containsKey(itemCode)){
							Map<String,Code> itemMap=codeMap.get(itemCode);
							itemMap.put(itemValue+"", code);
						}else{
							Map<String,Code> itemMap=new LinkedHashMap<>();
							itemMap.put(itemValue+"", code);
							codeMap.put(itemCode, itemMap);
						}
					}
			    }else if(key.equalsIgnoreCase("worktype")){
			    	JSONArray arr = entry.getValue();
			    	for(int i=0;i<arr.size();i++){
			    		JSONObject obj=arr.getJSONObject(i);
			    		WorkType workType=new WorkType();
			    		int resultCode=obj.getIntValue("RESULTCODE"); 
			    		String resultName=StringManagerUtils.stringFormat(obj.getString("RESULTNAME")); 
			    		String resultDescription=StringManagerUtils.stringFormat(obj.getString("RESULTDESCRIPTION"));
			    		String optimizationSuggestion=StringManagerUtils.stringFormat(obj.getString("OPTIMIZATIONSUGGESTION"));
			    		String remark=StringManagerUtils.stringFormat(obj.getString("REMARK"));
			    		
			    		workType.setResultCode(resultCode);
						workType.setResultName(resultName);
						workType.setResultDescription(resultDescription);
						workType.setOptimizationSuggestion(optimizationSuggestion);
						workType.setRemark(remark);
						workTypeMap.put(resultCode+"", workType);
			    	}
			    }
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dataModelMap.put("languageResource-"+language, languageMap);
		dataModelMap.put("codeLanguageResource-"+language, codeMap);
		dataModelMap.put("workTypeLanguageResource-"+language, workTypeMap);
		
		if(languageList==null){
			languageList=new ArrayList<>();
		}
		if(!StringManagerUtils.existOrNot(languageList, language, false)){
			languageList.add(language);
		}
	}
	
	public static List<String> getLanguageResourceValueList(String code){
		List<String> languageResourceValueList=new ArrayList<>();
		if(languageList!=null && languageList.size()>0){
			for(String language:languageList){
				Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
				if(languageResourceMap.containsKey(code)){
					languageResourceValueList.add(languageResourceMap.get(code));
				}
			}
		}
		return languageResourceValueList;
	}
	
	public static String itemLanguageSwitchover(String value,String language){
		String r=value;
		String code="";
		
		if(languageList!=null && languageList.size()>0){
			for(String l:languageList){
				Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(l);
				Iterator<Map.Entry<String,String>> it = languageResourceMap.entrySet().iterator();
				while(it.hasNext()){
					Map.Entry<String,String> entry = it.next();
					String c=entry.getValue();
					String v=entry.getValue();
					if(v.equals(value)){
						code=c;
						break;
					}
					if(StringManagerUtils.isNotNull(code)){
						break;
					}
				}
			}
		}
		
		if(StringManagerUtils.isNotNull(code)){
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			r=languageResourceMap.get(code);
		}
		
		return r;
	}
	
	public static Map<String,WorkType> getWorkTypeMap(String language){
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		Map<String,WorkType> workTypeMap=new LinkedHashMap<>();
		String key="workTypeLanguageResource-"+language;
		if(!dataModelMap.containsKey(key)){
			loadLanguageResource(language);
		}
		if(dataModelMap.containsKey(key)){
			workTypeMap=(Map<String, WorkType>) dataModelMap.get(key);
		}
		return workTypeMap;
	}
	
	public static WorkType getWorkTypeByCode(String resultCode,String language){
		WorkType workType=null;
		Map<String,WorkType> workTypeMap=getWorkTypeMap(language);
		workType=workTypeMap.get(resultCode);
		return workType;
	}
	
	public static WorkType getWorkTypeByName(String resultName,String language){
		WorkType workType=null;
		Map<String,WorkType> workTypeMap=getWorkTypeMap(language);
		Iterator<Map.Entry<String, WorkType>> it = workTypeMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, WorkType> entry = it.next();
			String resultCode=new String(entry.getKey());
			WorkType w=entry.getValue();
			if(resultName.equalsIgnoreCase(w.getResultName())){
				workType=w;
				break;
			}
		}
		
		return workType;
	}
	
	public static String getWorkTypeName(String resultCode,String language){
		String name="";
		WorkType workType=getWorkTypeByCode(resultCode,language);
		if(workType!=null){
			name=workType.getResultName();
		}
		return name;
	}
	
	public static int getWorkTypeCode(String resultName,String language){
		int code=0;
		WorkType workType=getWorkTypeByName(resultName,language);
		if(workType!=null){
			code=workType.getResultCode();
		}
		return code;
	}
	
	@SuppressWarnings("static-access")
	public static void loadReportTemplateConfig(){
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		Gson gson = new Gson();
		String path="";
		String configData="";
		java.lang.reflect.Type type=null;
		
		String template=Config.getInstance().configFile.getAp().getReport().getTemplate();
//		path=stringManagerUtils.getFilePath("reportTemplate.json","reportTemplate/");
		path=stringManagerUtils.getFilePath(template);
		configData=stringManagerUtils.readFile(path,"utf-8");
		type = new TypeToken<ReportTemplate>() {}.getType();
		ReportTemplate reportTemplate=gson.fromJson(configData, type);
		if(reportTemplate==null){
			reportTemplate=new ReportTemplate();
			reportTemplate.setSingleWellDailyReportTemplate(new ArrayList<ReportTemplate.Template>());
			reportTemplate.setSingleWellRangeReportTemplate(new ArrayList<ReportTemplate.Template>());
			reportTemplate.setProductionReportTemplate(new ArrayList<ReportTemplate.Template>());
		}else{
			if(reportTemplate.getSingleWellDailyReportTemplate()==null){
				reportTemplate.setSingleWellDailyReportTemplate(new ArrayList<ReportTemplate.Template>());
			}else if(reportTemplate.getSingleWellDailyReportTemplate().size()>0){
				Collections.sort(reportTemplate.getSingleWellDailyReportTemplate());
			}
			if(reportTemplate.getSingleWellRangeReportTemplate()==null){
				reportTemplate.setSingleWellRangeReportTemplate(new ArrayList<ReportTemplate.Template>());
			}else if(reportTemplate.getSingleWellRangeReportTemplate().size()>0){
				Collections.sort(reportTemplate.getSingleWellRangeReportTemplate());
			}
			if(reportTemplate.getProductionReportTemplate()==null){
				reportTemplate.setProductionReportTemplate(new ArrayList<ReportTemplate.Template>());
			}else if(reportTemplate.getProductionReportTemplate().size()>0){
				Collections.sort(reportTemplate.getProductionReportTemplate());
			}
		}
		
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			jedis.set("ReportTemplateConfig".getBytes(), SerializeObjectUnils.serialize(reportTemplate));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static ReportTemplate getReportTemplateConfig(){
		Jedis jedis=null;
		ReportTemplate reportTemplate=null;
		if(!existsKey("ReportTemplateConfig")){
			MemoryDataManagerTask.loadReportTemplateConfig();
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			reportTemplate=(ReportTemplate)SerializeObjectUnils.unserizlize(jedis.get("ReportTemplateConfig".getBytes()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return reportTemplate;
	}
	
	public static ReportTemplate.Template getSingleWellRangeReportTemplateByCode(String code){
		Jedis jedis=null;
		ReportTemplate reportTemplate=null;
		ReportTemplate.Template template=null;
		if(!existsKey("ReportTemplateConfig")){
			MemoryDataManagerTask.loadReportTemplateConfig();
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			reportTemplate=(ReportTemplate)SerializeObjectUnils.unserizlize(jedis.get("ReportTemplateConfig".getBytes()));
			if(reportTemplate!=null && reportTemplate.getSingleWellRangeReportTemplate()!=null && reportTemplate.getSingleWellRangeReportTemplate().size()>0){
				for(int i=0;i<reportTemplate.getSingleWellRangeReportTemplate().size();i++){
					if(code.equalsIgnoreCase(reportTemplate.getSingleWellRangeReportTemplate().get(i).getTemplateCode())){
						template=reportTemplate.getSingleWellRangeReportTemplate().get(i);
						break;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return template;
	}
	
	public static String getSingleWellRangeReportTemplateCodeFromName(String name){
		String code="";
		ReportTemplate reportTemplate=getReportTemplateConfig();
		if(reportTemplate!=null && reportTemplate.getSingleWellRangeReportTemplate()!=null && reportTemplate.getSingleWellRangeReportTemplate().size()>0){
			for(int i=0;i<reportTemplate.getSingleWellRangeReportTemplate().size();i++){
				if(name.equalsIgnoreCase(reportTemplate.getSingleWellRangeReportTemplate().get(i).getTemplateName())){
					code=reportTemplate.getSingleWellRangeReportTemplate().get(i).getTemplateCode();
					break;
				}
			}
		}
		return code;
	}
	
	public static String getSingleWellRangeReportTemplateNameFromCode(String code){
		String name="";
		ReportTemplate reportTemplate=getReportTemplateConfig();
		if(reportTemplate!=null && reportTemplate.getSingleWellRangeReportTemplate()!=null && reportTemplate.getSingleWellRangeReportTemplate().size()>0){
			for(int i=0;i<reportTemplate.getSingleWellRangeReportTemplate().size();i++){
				if(code.equalsIgnoreCase(reportTemplate.getSingleWellRangeReportTemplate().get(i).getTemplateCode())){
					name=reportTemplate.getSingleWellRangeReportTemplate().get(i).getTemplateName();
					break;
				}
			}
		}
		return name;
	}
	
	public static ReportTemplate.Template getSingleWellDailyReportTemplateByCode(String code){
		Jedis jedis=null;
		ReportTemplate reportTemplate=null;
		ReportTemplate.Template template=null;
		if(!existsKey("ReportTemplateConfig")){
			MemoryDataManagerTask.loadReportTemplateConfig();
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			reportTemplate=(ReportTemplate)SerializeObjectUnils.unserizlize(jedis.get("ReportTemplateConfig".getBytes()));
			if(reportTemplate!=null && reportTemplate.getSingleWellDailyReportTemplate()!=null && reportTemplate.getSingleWellDailyReportTemplate().size()>0){
				for(int i=0;i<reportTemplate.getSingleWellDailyReportTemplate().size();i++){
					if(code.equalsIgnoreCase(reportTemplate.getSingleWellDailyReportTemplate().get(i).getTemplateCode())){
						template=reportTemplate.getSingleWellDailyReportTemplate().get(i);
						break;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return template;
	}
	
	public static String getSingleWellDailyReportTemplateCodeFromName(String name){
		String code="";
		ReportTemplate reportTemplate=getReportTemplateConfig();
		if(reportTemplate!=null && reportTemplate.getSingleWellDailyReportTemplate()!=null && reportTemplate.getSingleWellDailyReportTemplate().size()>0){
			for(int i=0;i<reportTemplate.getSingleWellDailyReportTemplate().size();i++){
				if(name.equalsIgnoreCase(reportTemplate.getSingleWellDailyReportTemplate().get(i).getTemplateName())){
					code=reportTemplate.getSingleWellDailyReportTemplate().get(i).getTemplateCode();
					break;
				}
			}
		}
		return code;
	}
	
	public static String getSingleWellDailyReportTemplateNameFromCode(String code){
		String name="";
		ReportTemplate reportTemplate=getReportTemplateConfig();
		if(reportTemplate!=null && reportTemplate.getSingleWellDailyReportTemplate()!=null && reportTemplate.getSingleWellDailyReportTemplate().size()>0){
			for(int i=0;i<reportTemplate.getSingleWellDailyReportTemplate().size();i++){
				if(code.equalsIgnoreCase(reportTemplate.getSingleWellDailyReportTemplate().get(i).getTemplateCode())){
					name=reportTemplate.getSingleWellDailyReportTemplate().get(i).getTemplateName();
					break;
				}
			}
		}
		return name;
	}
	
	public static ReportTemplate.Template getProductionReportTemplateByCode(String code){
		Jedis jedis=null;
		ReportTemplate reportTemplate=null;
		ReportTemplate.Template template=null;
		if(!existsKey("ReportTemplateConfig")){
			MemoryDataManagerTask.loadReportTemplateConfig();
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			reportTemplate=(ReportTemplate)SerializeObjectUnils.unserizlize(jedis.get("ReportTemplateConfig".getBytes()));
			if(reportTemplate!=null && reportTemplate.getProductionReportTemplate()!=null && reportTemplate.getProductionReportTemplate().size()>0){
				for(int i=0;i<reportTemplate.getProductionReportTemplate().size();i++){
					if(code.equalsIgnoreCase(reportTemplate.getProductionReportTemplate().get(i).getTemplateCode())){
						template=reportTemplate.getProductionReportTemplate().get(i);
						break;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return template;
	}
	
	public static String getProductionReportTemplateCodeFromName(String name){
		String code="";
		ReportTemplate reportTemplate=getReportTemplateConfig();
		if(reportTemplate!=null && reportTemplate.getProductionReportTemplate()!=null && reportTemplate.getProductionReportTemplate().size()>0){
			for(int i=0;i<reportTemplate.getProductionReportTemplate().size();i++){
				if(name.equalsIgnoreCase(reportTemplate.getProductionReportTemplate().get(i).getTemplateName())){
					code=reportTemplate.getProductionReportTemplate().get(i).getTemplateCode();
					break;
				}
			}
		}
		return code;
	}
	
	public static String getProductionReportTemplateNameFromCode(String code){
		String name="";
		ReportTemplate reportTemplate=getReportTemplateConfig();
		if(reportTemplate!=null && reportTemplate.getProductionReportTemplate()!=null && reportTemplate.getProductionReportTemplate().size()>0){
			for(int i=0;i<reportTemplate.getProductionReportTemplate().size();i++){
				if(code.equalsIgnoreCase(reportTemplate.getProductionReportTemplate().get(i).getTemplateCode())){
					name=reportTemplate.getProductionReportTemplate().get(i).getTemplateName();
					break;
				}
			}
		}
		return name;
	}
	
	public static CalculateColumnInfo getCalColumnsInfo(String language){
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		CalculateColumnInfo calculateColumnInfo=null;
		List<CalculateColumn> srpCalculateColumnList=new ArrayList<CalculateColumn>();
		List<CalculateColumn> pcpCalculateColumnList=new ArrayList<CalculateColumn>();
		calculateColumnInfo=new CalculateColumnInfo();
		calculateColumnInfo.setSRPCalculateColumnList(srpCalculateColumnList);
//		calculateColumnInfo.setPCPCalculateColumnList(pcpCalculateColumnList);
		
		//抽油机井
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("systemTime"),"write_SystemTime",1) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("systemDate"),"write_SystemDate",2) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("runStatus"),"RunStatus",3) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("IA"),"IA",4) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("IB"),"IB",5) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("IC"),"IC",6) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("VA"),"VA",7) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("VB"),"VB",8) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("VC"),"VC",9) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("totalKWattH"),"TotalKWattH",10) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("totalKVarH"),"TotalKVarH",11) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("Watt3"),"Watt3",12) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("Var3"),"Var3",13) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("PF3"),"PF3",14) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("setFrequency"),"SetFrequency",15) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("runFrequency"),"RunFrequency",16) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("tubingPressure"),"TubingPressure",17) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("casingPressure"),"CasingPressure",18) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("wellHeadTemperature"),"WellHeadTemperature",19) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("bottomHolePressure"),"BottomHolePressure",20) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("bottomHoleTemperature"),"BottomHoleTemperature",21) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("producingfluidLevel"),"ProducingfluidLevel",22) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("waterCut"),"VolumeWaterCut",23) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("FESDiagramAcqCount"),"FESDiagramAcqCount",24) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("FESDiagramAcqtime"),"FESDiagramAcqtime",25) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("SPM"),"SPM",26) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("stroke"),"Stroke",27) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("position_Curve"),"Position_Curve",28) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("load_Curve"),"Load_Curve",29) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("current_Curve"),"Current_Curve",30) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("power_Curve"),"Power_Curve",31) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("RPM"),"RPM",32) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("realtimeLiquidVolumetricProduction"),"RealtimeLiquidVolumetricProduction",33) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("realtimeOilVolumetricProduction"),"RealtimeOilVolumetricProduction",34) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("realtimeOilVolumetricProduction"),"RealtimeOilVolumetricProduction",35) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("realtimeLiquidWeightProduction"),"RealtimeLiquidWeightProduction",37) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("realtimeOilWeightProduction"),"RealtimeOilWeightProduction",38) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("realtimeWaterWeightProduction"),"RealtimeWaterWeightProduction",39) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("resultName"),"ResultCode",40) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("fMax"),"FMax",41) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("fMin"),"FMin",42) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("fullnessCoefficient"),"FullnessCoefficient",43) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("upperLoadLine"),"UpperLoadLine",44) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("lowerLoadLine"),"LowerLoadLine",45) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("theoreticalProduction"),"TheoreticalProduction",46) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("liquidVolumetricProductionWithUnit"),"LiquidVolumetricProduction",47) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("oilVolumetricProductionWithUnit"),"OilVolumetricProduction",48) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("waterVolumetricProductionWithUnit"),"WaterVolumetricProduction",49) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("availablePlungerStrokeProdWithUnit_v"),"AvailablePlungerStrokeProd_v",51) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("pumpClearanceleakProdWithUnit_v"),"PumpClearanceLeakProd_v",52) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("TVLeakVolumetricProductionWithUnit"),"TVLeakVolumetricProduction",53) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("SVLeakVolumetricProductionWithUnit"),"SVLeakVolumetricProduction",54) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("gasInfluenceProdWithUnit_v"),"GasInfluenceProd_v",55) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("liquidWeightProductionWithUnit"),"LiquidWeightProduction",56) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("oilWeightProductionWithUnit"),"OilWeightProduction",57) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("waterWeightProductionWithUnit"),"WaterWeightProduction",58) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("availablePlungerStrokeProdWithUnit_w"),"AvailablePlungerStrokeProd_w",59) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("pumpClearanceleakProdWithUnit_w"),"PumpClearanceLeakProd_w",60) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("TVLeakWeightProductionWithUnit"),"TVLeakWeightProduction",61) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("SVLeakWeightProductionWithUnit"),"SVLeakWeightProduction",62) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("gasInfluenceProdWithUnit_w"),"GasInfluenceProd_w",63) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("averageWatt"),"AverageWatt",64) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("polishRodPower"),"PolishRodPower",65) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("waterPower"),"WaterPower",66) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("surfaceSystemEfficiency"),"SurfaceSystemEfficiency",67) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("wellDownSystemEfficiency"),"WellDownSystemEfficiency",68) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("systemEfficiency"),"SystemEfficiency",69) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("energyPer100mLift"),"EnergyPer100mLift",70) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("area"),"Area",71) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("rodFlexLength"),"RodFlexLength",72) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("tubingFlexLength"),"TubingFlexLength",73) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("inertiaLength"),"InertiaLength",74) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("pumpEff1"),"PumpEff1",75) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("pumpEff2"),"PumpEff2",76) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("pumpEff3"),"PumpEff3",77) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("pumpEff4"),"PumpEff4",78) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("pumpEff1_pcp"),"PumpEff1",79) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("pumpEff"),"PumpEff",80) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("calcProducingfluidLevel"),"CalcProducingfluidLevel",81) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("levelDifferenceValue"),"LevelDifferenceValue",82) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("upStrokeWattMax"),"UpStrokeWattMax",83) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("downStrokeWattMax"),"DownStrokeWattMax",84) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("wattDegreeBalance"),"WattDegreeBalance",85) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("upStrokeIMax"),"UpStrokeIMax",86) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("downStrokeIMax"),"DownStrokeIMax",87) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("iDegreeBalance"),"IDegreeBalance",88) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("deltaRadius"),"DeltaRadius",89) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("submergence"),"Submergence",90) );
		
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_CrudeOilDensity"),"write_CrudeOilDensity",91) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_WaterDensity"),"write_WaterDensity",92) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_NaturalGasRelativeDensity"),"write_NaturalGasRelativeDensity",93) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_SaturationPressure"),"write_SaturationPressure",94) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_ReservoirDepth"),"write_ReservoirDepth",95) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_ReservoirTemperature"),"write_ReservoirTemperature",96) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_ReservoirDepth_cbm"),"write_ReservoirDepth_cbm",97) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_ReservoirTemperature_cbm"),"write_ReservoirTemperature_cbm",98) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_TubingPressure"),"write_TubingPressure",99) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_TubingPressure_cbm"),"write_TubingPressure_cbm",100) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_CasingPressure"),"write_CasingPressure",101) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_WellHeadTemperature"),"write_WellHeadTemperature",102) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_WaterCut"),"write_WaterCut",103) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_ProductionGasOilRatio"),"write_ProductionGasOilRatio",104) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_ProducingfluidLevel"),"write_ProducingfluidLevel",105) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_PumpSettingDepth"),"write_PumpSettingDepth",106) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_BarrelType"),"write_BarrelType",107) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_PumpGrade"),"write_PumpGrade",108) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_PumpBoreDiameter"),"write_PumpBoreDiameter",109) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_PlungerLength"),"write_PlungerLength",110) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_TubingStringInsideDiameter"),"write_TubingStringInsideDiameter",111) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_CasingStringOutsideDiameter"),"write_CasingStringOutsideDiameter",112) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RodStringType1"),"write_RodStringType1",113) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RodGrade1"),"write_RodGrade1",114) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RodStringOutsideDiameter1"),"write_RodStringOutsideDiameter1",115) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RodStringInsideDiameter1"),"write_RodStringInsideDiameter1",116) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RodStringLength1"),"write_RodStringLength1",117) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RodStringType2"),"write_RodStringType2",118) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RodGrade2"),"write_RodGrade2",119) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RodStringOutsideDiameter2"),"write_RodStringOutsideDiameter2",120) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RodStringInsideDiameter2"),"write_RodStringInsideDiameter2",121) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RodStringLength2"),"write_RodStringLength2",122) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RodStringType3"),"write_RodStringType3",123) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RodGrade3"),"write_RodGrade3",124) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RodStringOutsideDiameter3"),"write_RodStringOutsideDiameter3",125) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RodStringInsideDiameter3"),"write_RodStringInsideDiameter3",126) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RodStringLength3"),"write_RodStringLength3",127) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RodStringType4"),"write_RodStringType4",128) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RodGrade4"),"write_RodGrade4",129) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RodStringOutsideDiameter4"),"write_RodStringOutsideDiameter4",130) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RodStringInsideDiameter4"),"write_RodStringInsideDiameter4",131) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RodStringLength4"),"write_RodStringLength4",132) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_ManualInterventionCode"),"write_ManualInterventionCode",133) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_NetGrossRatio"),"write_NetGrossRatio",134) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_NetGrossValue"),"write_NetGrossValue",135) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_LevelCorrectValue"),"write_LevelCorrectValue",136) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_PumpingUnitStructure"),"write_PumpingUnitStructure",137) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_Stroke"),"write_Stroke",138) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_CrankRotationDirection"),"write_CrankRotationDirection",139) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_OffsetAngleOfCrank"),"write_OffsetAngleOfCrank",140) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_CrankGravityRadius"),"write_CrankGravityRadius",141) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_SingleCrankWeight"),"write_SingleCrankWeight",142) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_SingleCrankPinWeight"),"write_SingleCrankPinWeight",143) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_StructuralUnbalance"),"write_StructuralUnbalance",144) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_BalancePosition1"),"write_BalancePosition1",145) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_BalancePosition2"),"write_BalancePosition2",146) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_BalancePosition3"),"write_BalancePosition3",147) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_BalancePosition4"),"write_BalancePosition4",148) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_BalancePosition5"),"write_BalancePosition5",149) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_BalancePosition6"),"write_BalancePosition6",150) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_BalancePosition7"),"write_BalancePosition7",151) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_BalancePosition8"),"write_BalancePosition8",152) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_BalanceWeight1"),"write_BalanceWeight1",153) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_BalanceWeight2"),"write_BalanceWeight2",154) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_BalanceWeight3"),"write_BalanceWeight3",155) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_BalanceWeight4"),"write_BalanceWeight4",156) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_BalanceWeight5"),"write_BalanceWeight5",157) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_BalanceWeight6"),"write_BalanceWeight6",158) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_BalanceWeight7"),"write_BalanceWeight7",159) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_BalanceWeight8"),"write_BalanceWeight8",160) );
		
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_TransmissionRatio"),"write_TransmissionRatio",161) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_PRTFPointCount"),"write_PRTFPointCount",162) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_CrankAngle"),"write_CrankAngle",163) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_PR"),"write_PR",164) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_TF"),"write_TF",165) );
		
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_CrankDIInitAngle"),"write_CrankDIInitAngle",166) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_InterpolationCNT"),"write_InterpolationCNT",167) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_SurfaceSystemEfficiency"),"write_SurfaceSystemEfficiency",168) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_WattTimes"),"write_WattTimes",169) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_ITimes"),"write_ITimes",170) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_FSDiagramTimes"),"write_FSDiagramTimes",171) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_FSDiagramLeftTimes"),"write_FSDiagramLeftTimes",172) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_FSDiagramRightTimes"),"write_FSDiagramRightTimes",173) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_LeftPercent"),"write_LeftPercent",174) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_RightPercent"),"write_RightPercent",175) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_PositiveXWatt"),"write_PositiveXWatt",176) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_NegativeXWatt"),"write_NegativeXWatt",177) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_PRTFSrc"),"write_PRTFSrc",178) );
		calculateColumnInfo.getSRPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("write_BoardDataSource"),"write_BoardDataSource",179) );
		
		return calculateColumnInfo;
	}
	
	public static String getCalculateColumnFromName(int deviceType,String name,String language){
		if(!StringManagerUtils.isNotNull(name)){
			return "";
		}
		CalculateColumnInfo calculateColumnInfo=getCalColumnsInfo(language);
		List<CalculateColumn> calculateColumnList=calculateColumnInfo.getSRPCalculateColumnList();
		String code="";
		for(int i=0;i<calculateColumnList.size();i++){
			if(name.equalsIgnoreCase(calculateColumnList.get(i).getName())){
				code=calculateColumnList.get(i).getCode();
				break;
			}
		}
		return code;
	}
	
	public static String getCalculateColumnNameFromCode(String code,String language){
		if(!StringManagerUtils.isNotNull(code)){
			return "";
		}
		CalculateColumnInfo calculateColumnInfo=getCalColumnsInfo(language);
		List<CalculateColumn> calculateColumnList=calculateColumnInfo.getSRPCalculateColumnList();
		String name="";
//		if(deviceType!=0){
//			calculateColumnList=calculateColumnInfo.getPCPCalculateColumnList();
//		}
		for(int i=0;i<calculateColumnList.size();i++){
			if(code.equalsIgnoreCase(calculateColumnList.get(i).getCode())){
				name=calculateColumnList.get(i).getName();
				break;
			}
		}
		return name;
	}
	
	public static void loadDataSourceConfig(){
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		
		String path=stringManagerUtils.getFilePath("dataSource.json","dataSource/");
		String data=stringManagerUtils.readFile(path,"utf-8");
		
		type = new TypeToken<DataSourceConfig>() {}.getType();
		DataSourceConfig dataSourceConfig=gson.fromJson(data, type);
		
		Map<String, Object> map = DataModelMap.getMapObject();
		map.put("dataSourceConfig", dataSourceConfig);
	}
	
	public static DataSourceConfig getDataSourceConfig(){
		Map<String, Object> map = DataModelMap.getMapObject();
		DataSourceConfig dataSourceConfig=(DataSourceConfig) map.get("dataSourceConfig");
		if(dataSourceConfig==null){
			loadDataSourceConfig();
			dataSourceConfig=(DataSourceConfig) map.get("dataSourceConfig");
		}
		return dataSourceConfig;
	}
	
	public static void loadDataWriteBackConfig(){
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		
		String path=stringManagerUtils.getFilePath("writeBackConfig.json","dataSource/");
		String data=stringManagerUtils.readFile(path,"utf-8");
		
		type = new TypeToken<DataWriteBackConfig>() {}.getType();
		DataWriteBackConfig dataWriteBackConfig=gson.fromJson(data, type);
		
		Map<String, Object> map = DataModelMap.getMapObject();
		map.put("dataWriteBackConfig", dataWriteBackConfig);
	}
	
	public static DataWriteBackConfig getDataWriteBackConfig(){
		Map<String, Object> map = DataModelMap.getMapObject();
		DataWriteBackConfig dataWriteBackConfig=(DataWriteBackConfig) map.get("dataWriteBackConfig");
		if(dataWriteBackConfig==null){
			loadDataWriteBackConfig();
			dataWriteBackConfig=(DataWriteBackConfig) map.get("dataWriteBackConfig");
		}
		return dataWriteBackConfig;
	}
}
