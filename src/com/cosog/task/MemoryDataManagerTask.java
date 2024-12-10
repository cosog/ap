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
import com.cosog.model.DataMapping;
import com.cosog.model.DataSourceConfig;
import com.cosog.model.DataWriteBackConfig;
import com.cosog.model.KeyValue;
import com.cosog.model.ProtocolRunStatusConfig;
import com.cosog.model.RealtimeTotalInfo;
import com.cosog.model.ReportTemplate;
import com.cosog.model.VideoKey;
import com.cosog.model.WorkType;
import com.cosog.model.RealtimeTotalInfo.TotalItem;
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
import com.cosog.task.MemoryDataManagerTask.CalItem;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.PCPDeviceTodayData;
import com.cosog.model.calculate.RPCCalculateRequestData;
import com.cosog.model.calculate.RPCCalculateResponseData;
import com.cosog.model.calculate.RPCDeviceInfo;
import com.cosog.model.calculate.RPCDeviceTodayData;
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
		
//		loadPCPCalculateItem();
//		System.out.println("加载PCP计算项完成");
//		loadPCPInputItem();
//		System.out.println("加载PCP输入项完成");
//		loadPCPTotalCalculateItem();
//		System.out.println("加载PCP汇总项完成");
//		loadPCPTimingTotalCalculateItem();
//		System.out.println("加载PCP定时汇总项完成");
		
//		loadRPCCalculateItem();
//		System.out.println("加载RPC计算项完成");
//		loadRPCInputItem();
//		System.out.println("加载RPC输入项完成");
//		loadRPCTotalCalculateItem();
//		System.out.println("加载RPC汇总项完成");
//		loadRPCTimingTotalCalculateItem();
//		System.out.println("加载RPC定时汇总项完成");
		
		loadAcqInstanceOwnItemById("","update");
		System.out.println("加载采控实例信息完成");
		loadAlarmInstanceOwnItemById("","update");
		System.out.println("加载报警实例信息完成");
		loadDisplayInstanceOwnItemById("","update");
		System.out.println("加载显示实例信息完成");
		
		loadDeviceInfo(null,0,"update");
		System.out.println("加载设备信息完成");
		
//		loadDeviceRealtimeAcqData(null);
//		System.out.println("加载设备实时数据完成");
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
		
		cleanData("rpcCalItemList_en");
		cleanData("rpcCalItemList_ru");
		cleanData("rpcCalItemList_zh_CN");
		
		cleanData("pcpCalItemList_en");
		cleanData("pcpCalItemList_ru");
		cleanData("pcpCalItemList_zh_CN");
		
		cleanData("acqTotalCalItemList_en");
		cleanData("acqTotalCalItemList_ru");
		cleanData("acqTotalCalItemList_zh_CN");
		
		cleanData("rpcTotalCalItemList_en");
		cleanData("rpcTotalCalItemList_ru");
		cleanData("rpcTotalCalItemList_zh_CN");
		
		cleanData("pcpTotalCalItemList_en");
		cleanData("pcpTotalCalItemList_ru");
		cleanData("pcpTotalCalItemList_zh_CN");
		
		cleanData("acqTimingTotalCalItemList_en");
		cleanData("acqTimingTotalCalItemList_ru");
		cleanData("acqTimingTotalCalItemList_zh_CN");
		
		cleanData("rpcTimingTotalCalItemList_en");
		cleanData("rpcTimingTotalCalItemList_ru");
		cleanData("rpcTimingTotalCalItemList_zh_CN");
		
		cleanData("pcpTimingTotalCalItemList_en");
		cleanData("pcpTimingTotalCalItemList_ru");
		cleanData("pcpTimingTotalCalItemList_zh_CN");
		
		cleanData("rpcInputItemList_en");
		cleanData("rpcInputItemList_ru");
		cleanData("rpcInputItemList_zh_CN");
		
		cleanData("pcpInputItemList_en");
		cleanData("pcpInputItemList_ru");
		cleanData("pcpInputItemList_zh_CN");
		
		cleanData("UserInfo");
		cleanData("RPCWorkType");
		cleanData("RPCWorkTypeByName");
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
		cleanData("RPCDeviceTodayData");
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
			String sql="select t.id,t.name,t.code,t.items,t.sort,t.devicetype from TBL_PROTOCOL t where 1=1 ";
			if(StringManagerUtils.isNotNull(protocolName)){
				sql+=" and t.name='"+protocolName+"'";
			}
			sql+= "order by t.sort,t.id";
			List<Object[]> list=OracleJdbcUtis.query(sql);
			for(Object[] obj:list){
				try {
					String itemsStr=obj[3]+"";
					if(!StringManagerUtils.isNotNull(itemsStr)){
						itemsStr="[]";
					}
					protocolBuff=new StringBuffer();
					protocolBuff.append("{");
					protocolBuff.append("\"Id\":\""+StringManagerUtils.stringToInteger(obj[0]+"")+"\",");
					protocolBuff.append("\"Name\":\""+obj[1]+"\",");
					protocolBuff.append("\"Code\":\""+obj[2]+"\",");
					protocolBuff.append("\"Sort\":"+StringManagerUtils.stringToInteger(obj[4]+"")+",");
					protocolBuff.append("\"DeviceType\":"+StringManagerUtils.stringToInteger(obj[5]+"")+",");
					protocolBuff.append("\"Items\":"+itemsStr+"");
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
	
	public static List<String> getAcquisitionItemNameList(){
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		List<String> acquisitionItemNameList=new ArrayList<>();
		if(!dataModelMap.containsKey("acquisitionItemNameList") || dataModelMap.get("acquisitionItemNameList")==null){
			loadAcquisitionItemNameList();
		}
		acquisitionItemNameList=(List<String>) dataModelMap.get("acquisitionItemNameList");
		return acquisitionItemNameList;
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
			
			String sql="select t.id,t.name,t.mappingcolumn,t.calcolumn,t.protocoltype,t.mappingmode,t.repetitiontimes from TBL_DATAMAPPING t order by t.protocoltype,t.id";
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
	
	
	public static Map<String,DataMapping> getProtocolMappingColumnByTitle(){
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		if(!dataModelMap.containsKey("ProtocolMappingColumnByTitle")){
			MemoryDataManagerTask.loadProtocolMappingColumnByTitle();
		}
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=null;
		if(dataModelMap.containsKey("ProtocolMappingColumnByTitle")){
			loadProtocolMappingColumnByTitleMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumnByTitle");
		}	
				
		return loadProtocolMappingColumnByTitleMap;
	}
	
	public static void loadProtocolMappingColumnByTitle(){
		Jedis jedis=null;
		try {
			Map<String, Object> dataModelMap=DataModelMap.getMapObject();
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=new LinkedHashMap<String,DataMapping>();
			
			String sql="select t.id,t.name,t.mappingcolumn,t.calcolumn,t.protocoltype,t.mappingmode,t.repetitiontimes from TBL_DATAMAPPING t order by t.protocoltype,t.id";
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
				loadProtocolMappingColumnByTitleMap.put(dataMapping.getName(), dataMapping);
			}
			dataModelMap.put("ProtocolMappingColumnByTitle", loadProtocolMappingColumnByTitleMap);
			
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis!=null){
				for (Map.Entry<String, DataMapping> entry : loadProtocolMappingColumnByTitleMap.entrySet()) {
				    String key = entry.getKey();
				    DataMapping dataMapping = entry.getValue();
				    jedis.hset("ProtocolMappingColumnByTitle".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(dataMapping));//哈希(Hash)
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
						jedis.hdel("RPCDeviceTodayData".getBytes(), wellList.get(i).getBytes());
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
									jedis.hdel("RPCDeviceTodayData".getBytes(), (deviceInfo.getId()+"").getBytes());
									jedis.hdel("PCPDeviceTodayData".getBytes(), (deviceInfo.getId()+"").getBytes());
									jedis.del(("DeviceRealtimeAcqData_"+deviceInfo.getId()).getBytes());
									break;
								}
							}
						}
					}
				}
			}else{
				String sql="select t.id,t.orgid,t.orgName,"
						+ "t.devicename,"
						+ "t.devicetype,t.devicetypename,"
						+ "t.applicationscenarios,t.applicationScenariosName,"
						+ "t.calculateType,"
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
						+ " left outer join tbl_rpcacqdata_latest t3 on t3.deviceid=t.id "
						+ " left outer join tbl_pcpacqdata_latest t4 on t4.deviceid=t.id "
						+ " where 1=1 ";
				String auxiliaryDeviceSql="select t.id,t3.id as auxiliarydeviceid,t3.manufacturer,t3.model,t4.itemname,t4.itemvalue "
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
					auxiliaryDeviceAddInfo.setItemName(obj[4]+"");
					auxiliaryDeviceAddInfo.setItemValue(obj[5]+"");
					auxiliaryDeviceAddInfoList.add(auxiliaryDeviceAddInfo);
				}
				while(rs.next()){
					DeviceInfo deviceInfo=new DeviceInfo();
					
					deviceInfo.setId(rs.getInt(1));
					deviceInfo.setOrgId(rs.getInt(2));
					deviceInfo.setOrgName(rs.getString(3));
					deviceInfo.setDeviceName(rs.getString(4));
					deviceInfo.setDeviceType(rs.getInt(5));
					deviceInfo.setDeviceTypeName(rs.getString(6));
					deviceInfo.setApplicationScenarios(rs.getInt(7));
					deviceInfo.setApplicationScenariosName(rs.getString(8));
					
					deviceInfo.setCalculateType(rs.getInt(9));
					
					deviceInfo.setTcpType(rs.getString(10)+"");
					deviceInfo.setSignInId(rs.getString(11)+"");
					deviceInfo.setIpPort(rs.getString(12)+"");
					deviceInfo.setSlave(rs.getString(13)+"");
					deviceInfo.setPeakDelay(rs.getInt(14));
					
					deviceInfo.setVideoUrl1(rs.getString(15)+"");
					deviceInfo.setVideoKey1(rs.getInt(16));
					deviceInfo.setVideoUrl2(rs.getString(17)+"");
					deviceInfo.setVideoKey2(rs.getInt(18));
					
					deviceInfo.setInstanceCode(rs.getString(19)+"");
					deviceInfo.setInstanceName(rs.getString(20)+"");
					deviceInfo.setAlarmInstanceCode(rs.getString(21)+"");
					deviceInfo.setAlarmInstanceName(rs.getString(22)+"");
					deviceInfo.setDisplayInstanceCode(rs.getString(23)+"");
					deviceInfo.setDisplayInstanceName(rs.getString(24)+"");
					deviceInfo.setStatus(rs.getInt(25));
					deviceInfo.setStatusName(rs.getString(26)+"");
					
					String productionData=rs.getString(27)+"";
					String balanceInfo=rs.getString(28)+"";
					float stroke=rs.getFloat(29);
					
					if(StringManagerUtils.isNotNull(productionData)){
						if(deviceInfo.getCalculateType()==1){//功图计算
							type = new TypeToken<RPCCalculateRequestData>() {}.getType();
							RPCCalculateRequestData rpcProductionData=gson.fromJson(productionData, type);
							
							List<AuxiliaryDeviceAddInfo> thisAuxiliaryDeviceAddInfoList=new ArrayList<>();
							String manufacturer="";
							String model="";
							for(int i=0;i<auxiliaryDeviceAddInfoList.size();i++){
								if(auxiliaryDeviceAddInfoList.get(i).getMasterId()==deviceInfo.getId()){
									thisAuxiliaryDeviceAddInfoList.add(auxiliaryDeviceAddInfoList.get(i));
								}
							}
							
							deviceInfo.setRpcCalculateRequestData(new RPCCalculateRequestData());
							deviceInfo.getRpcCalculateRequestData().init();
							
							deviceInfo.getRpcCalculateRequestData().setFluidPVT(rpcProductionData.getFluidPVT());
							deviceInfo.getRpcCalculateRequestData().setReservoir(rpcProductionData.getReservoir());
							deviceInfo.getRpcCalculateRequestData().setTubingString(rpcProductionData.getTubingString());
							deviceInfo.getRpcCalculateRequestData().setCasingString(rpcProductionData.getCasingString());
							deviceInfo.getRpcCalculateRequestData().setRodString(rpcProductionData.getRodString());
							deviceInfo.getRpcCalculateRequestData().setPump(rpcProductionData.getPump());
							deviceInfo.getRpcCalculateRequestData().setProduction(rpcProductionData.getProduction());
							
							deviceInfo.getRpcCalculateRequestData().setManualIntervention(rpcProductionData.getManualIntervention());
							
							if(thisAuxiliaryDeviceAddInfoList.size()>0){
								deviceInfo.getRpcCalculateRequestData().setPumpingUnit(new RPCCalculateRequestData.PumpingUnit());
								for(int i=0;i<thisAuxiliaryDeviceAddInfoList.size();i++ ){
									manufacturer=thisAuxiliaryDeviceAddInfoList.get(i).getManufacturer();
									model=thisAuxiliaryDeviceAddInfoList.get(i).getModel();
									if("crankRotationDirection".equalsIgnoreCase(thisAuxiliaryDeviceAddInfoList.get(i).getItemName())){
										deviceInfo.getRpcCalculateRequestData().getPumpingUnit().setCrankRotationDirection(thisAuxiliaryDeviceAddInfoList.get(i).getItemValue());
									}else if("offsetAngleOfCrank".equalsIgnoreCase(thisAuxiliaryDeviceAddInfoList.get(i).getItemName())){
										deviceInfo.getRpcCalculateRequestData().getPumpingUnit().setOffsetAngleOfCrank(StringManagerUtils.stringToFloat(thisAuxiliaryDeviceAddInfoList.get(i).getItemValue()));
									}else if("crankGravityRadius".equalsIgnoreCase(thisAuxiliaryDeviceAddInfoList.get(i).getItemName())){
										deviceInfo.getRpcCalculateRequestData().getPumpingUnit().setCrankGravityRadius(StringManagerUtils.stringToFloat(thisAuxiliaryDeviceAddInfoList.get(i).getItemValue()));
									}else if("singleCrankWeight".equalsIgnoreCase(thisAuxiliaryDeviceAddInfoList.get(i).getItemName())){
										deviceInfo.getRpcCalculateRequestData().getPumpingUnit().setSingleCrankWeight(StringManagerUtils.stringToFloat(thisAuxiliaryDeviceAddInfoList.get(i).getItemValue()));
									}else if("singleCrankPinWeight".equalsIgnoreCase(thisAuxiliaryDeviceAddInfoList.get(i).getItemName())){
										deviceInfo.getRpcCalculateRequestData().getPumpingUnit().setSingleCrankPinWeight(StringManagerUtils.stringToFloat(thisAuxiliaryDeviceAddInfoList.get(i).getItemValue()));
									}else if("structuralUnbalance".equalsIgnoreCase(thisAuxiliaryDeviceAddInfoList.get(i).getItemName())){
										deviceInfo.getRpcCalculateRequestData().getPumpingUnit().setStructuralUnbalance(StringManagerUtils.stringToFloat(thisAuxiliaryDeviceAddInfoList.get(i).getItemValue()));
									}
								}
								deviceInfo.getRpcCalculateRequestData().getPumpingUnit().setManufacturer(manufacturer);
								deviceInfo.getRpcCalculateRequestData().getPumpingUnit().setModel(model);
								deviceInfo.getRpcCalculateRequestData().getPumpingUnit().setStroke(stroke);
								
								type = new TypeToken<RPCCalculateRequestData.Balance>() {}.getType();
								RPCCalculateRequestData.Balance balance=gson.fromJson(balanceInfo, type);
								if(balance!=null){
									deviceInfo.getRpcCalculateRequestData().getPumpingUnit().setBalance(balance);
								}
							}else{
								deviceInfo.getRpcCalculateRequestData().setPumpingUnit(null);
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
						deviceInfo.setRpcCalculateRequestData(null);
						deviceInfo.setPcpCalculateRequestData(null);
					}
					deviceInfo.setSortNum(rs.getInt(30));
					
					deviceInfo.setAcqTime(rs.getString(31));
					deviceInfo.setSaveTime("");
					
					deviceInfo.setCommStatus(rs.getInt(32));
					deviceInfo.setCommTime(rs.getFloat(33));
					deviceInfo.setCommEff(rs.getFloat(34));
					deviceInfo.setCommRange(StringManagerUtils.CLOBtoString2(rs.getClob(35)));
					
					deviceInfo.setOnLineAcqTime(rs.getString(31));
					deviceInfo.setOnLineCommStatus(rs.getInt(32));
					deviceInfo.setOnLineCommTime(rs.getFloat(33));
					deviceInfo.setOnLineCommEff(rs.getFloat(34));
					deviceInfo.setOnLineCommRange(StringManagerUtils.CLOBtoString2(rs.getClob(35)));
					
					deviceInfo.setRunStatusAcqTime(rs.getString(31));
					deviceInfo.setRunStatus(rs.getInt(36));
					deviceInfo.setRunTime(rs.getFloat(37));
					deviceInfo.setRunEff(rs.getFloat(38));
					deviceInfo.setRunRange(StringManagerUtils.CLOBtoString2(rs.getClob(39)));
					
					deviceInfo.setResultStatus(rs.getInt(40));
					deviceInfo.setResultCode(rs.getInt(41));
					
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
	
	public static void loadDeviceRealtimeAcqData(List<String> deviceIdList,String language){
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			List<CalItem> rpcCalItemList=MemoryDataManagerTask.getRPCCalculateItem(language);
			List<CalItem> pcpCalItemList=MemoryDataManagerTask.getPCPCalculateItem(language);
			String date=StringManagerUtils.getCurrentTime();
			
			String sql="select t.deviceId,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,t.acqdata";
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
				
				type = new TypeToken<List<KeyValue>>() {}.getType();
				List<KeyValue> acqDataList=gson.fromJson(acqData, type);
				
				String key="DeviceRealtimeAcqData_"+deviceId;
				
				Map<String,String> everyDataMap =new HashMap<>();
				if(acqDataList!=null){
					for(int i=0;i<acqDataList.size();i++){
						everyDataMap.put(acqDataList.get(i).getKey().toUpperCase(), acqDataList.get(i).getValue());
					}
				}
				jedis.hset(key.getBytes(), acqTime.getBytes(), SerializeObjectUnils.serialize(everyDataMap));
				
			}
			
			//加载功图计算数据
			sql="select t.deviceId,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,t.productiondata";
			for(CalItem calItem:rpcCalItemList){
				sql+=",t."+calItem.getCode();
			}	
			sql+= " from viw_rpcacqdata_hist t"
					+ " where t.acqTime between to_date('"+date+"','yyyy-mm-dd') and to_date('"+date+"','yyyy-mm-dd')+1";
			if(deviceIdList!=null && deviceIdList.size()>0){
				if(deviceIdList.size()==1){
					sql+=" and t.deviceId="+deviceIdList.get(0);
				}else{
					sql+=" and t.deviceId in("+StringUtils.join(deviceIdList, ",")+")";
				}
			}
			sql+=" order by t.deviceId,t.acqTime";
			
			List<Object[]> queryRPCCalDataList=OracleJdbcUtis.query(sql);
			for(Object[] obj:queryRPCCalDataList){
				int deviceId=StringManagerUtils.stringToInteger(obj[0]+"");
				String acqTime=obj[1]+"";
				String key="DeviceRealtimeAcqData_"+deviceId;
				String productionDataStr=obj[2]+"";
				type = new TypeToken<RPCCalculateRequestData>() {}.getType();
				RPCCalculateRequestData productionData=gson.fromJson(productionDataStr, type);
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
					for(int i=0;i<rpcCalItemList.size();i++){
						everyDataMap.put(rpcCalItemList.get(i).getCode().toUpperCase(), obj[i+3]+"");
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
					for(int i=0;i<rpcCalItemList.size();i++){
						everyDataMap.put(rpcCalItemList.get(i).getCode().toUpperCase(), obj[i+3]+"");
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
				sql+=",t."+calItem.getCode();
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
					+ "t.type "
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
	
	public static List<CalItem> getAcqCalculateItem(String language){
		Jedis jedis=null;
		List<CalItem> calItemList=new ArrayList<>();
		String key="acqCalItemList"+language;
		if(!existsKey(key)){
			MemoryDataManagerTask.loadAcqCalculateItem(language);
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			List<byte[]> calItemSet= jedis.zrange(key.getBytes(), 0, -1);
			for(byte[] rpcCalItemByteArr:calItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(rpcCalItemByteArr);
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
		String key="acqCalItemList_"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
			}
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTime"),"CommTime","h",2,languageResourceMap.get("custom"),languageResourceMap.get("commTime"))));
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTimeEfficiency"),"CommTimeEfficiency",timeEfficiencyUnit,2,languageResourceMap.get("custom"),languageResourceMap.get("commTimeEfficiency"))));
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commRange"),"CommRange","",1,languageResourceMap.get("custom"),languageResourceMap.get("commRange"))));
			
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTime"),"RunTime","h",2,languageResourceMap.get("custom"),languageResourceMap.get("runTime"))));
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTimeEfficiency"),"RunTimeEfficiency",timeEfficiencyUnit,2,languageResourceMap.get("custom"),languageResourceMap.get("runTimeEfficiency"))));
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runRange"),"RunRange","",1,languageResourceMap.get("custom"),languageResourceMap.get("runRange"))));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static List<CalItem> getRPCCalculateItem(String language){
		Jedis jedis=null;
		String key="rpcCalItemList_"+language;
		List<CalItem> calItemList=new ArrayList<>();
		if(!existsKey(key)){
			MemoryDataManagerTask.loadRPCCalculateItem(language);
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			List<byte[]> calItemSet= jedis.zrange(key.getBytes(), 0, -1);
			for(byte[] rpcCalItemByteArr:calItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(rpcCalItemByteArr);
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
	
	public static void loadRPCCalculateItem(String language){
		Jedis jedis=null;
		String key="rpcCalItemList_"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
			}
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTime"),"CommTime","h",2,languageResourceMap.get("custom"),languageResourceMap.get("commTime"))));
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTimeEfficiency"),"CommTimeEfficiency",timeEfficiencyUnit,2,languageResourceMap.get("custom"),languageResourceMap.get("commTimeEfficiency"))));
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commRange"),"CommRange","",1,languageResourceMap.get("custom"),languageResourceMap.get("commRange"))));
			
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTime"),"RunTime","h",2,languageResourceMap.get("custom"),languageResourceMap.get("runTime"))));
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTimeEfficiency"),"RunTimeEfficiency",timeEfficiencyUnit,2,languageResourceMap.get("custom"),languageResourceMap.get("runTimeEfficiency"))));
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runRange"),"RunRange","",1,languageResourceMap.get("custom"),languageResourceMap.get("runRange"))));
			
			
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("resultName"),"ResultName","",1,languageResourceMap.get("custom"),languageResourceMap.get("resultName"))));
			jedis.zadd(key.getBytes(),8, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("fMax"),"FMax","kN",2,languageResourceMap.get("custom"),languageResourceMap.get("fMax"))));
			jedis.zadd(key.getBytes(),9, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("fMin"),"FMin","kN",2,languageResourceMap.get("custom"),languageResourceMap.get("fMin"))));
			
			jedis.zadd(key.getBytes(),10, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("calculateStroke"),"Stroke","",2,languageResourceMap.get("custom"),"功图计算冲程")));
			jedis.zadd(key.getBytes(),11, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("plungerStroke"),"PlungerStroke","m",2,languageResourceMap.get("custom"),languageResourceMap.get("plungerStroke"))));
			jedis.zadd(key.getBytes(),12, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("availablePlungerStroke"),"AvailablePlungerStroke","m",2,languageResourceMap.get("custom"),languageResourceMap.get("availablePlungerStroke"))));
			jedis.zadd(key.getBytes(),13, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("noLiquidAvailablePlungerStroke"),"NoLiquidAvailablePlungerStroke","m",2,languageResourceMap.get("custom"),languageResourceMap.get("noLiquidAvailablePlungerStroke"))));
			
			jedis.zadd(key.getBytes(),14, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("fullnessCoefficient"),"FullnessCoefficient","",2,languageResourceMap.get("custom"),languageResourceMap.get("fullnessCoefficient"))));
			jedis.zadd(key.getBytes(),15, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("noLiquidFullnessCoefficient"),"NoLiquidFullnessCoefficient","",2,languageResourceMap.get("custom"),languageResourceMap.get("noLiquidFullnessCoefficient"))));
			
			jedis.zadd(key.getBytes(),16, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("upperLoadLine"),"UpperLoadLine","kN",2,languageResourceMap.get("custom"),languageResourceMap.get("upperLoadLine"))));
			jedis.zadd(key.getBytes(),17, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("lowerLoadLine"),"LowerLoadLine","kN",2,languageResourceMap.get("custom"),languageResourceMap.get("lowerLoadLine"))));
			jedis.zadd(key.getBytes(),18, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("upperLoadLineOfExact"),"UpperLoadLineOfExact","kN",2,languageResourceMap.get("custom"),languageResourceMap.get("upperLoadLineOfExact"))));
			
			
			jedis.zadd(key.getBytes(),19, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("theoreticalProduction"),"TheoreticalProduction","m^3/d",2,languageResourceMap.get("custom"),languageResourceMap.get("theoreticalProduction"))));
			
			jedis.zadd(key.getBytes(),20, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidVolumetricProduction"),"LiquidVolumetricProduction","m^3/d",2,languageResourceMap.get("custom"),languageResourceMap.get("liquidVolumetricProduction"))));
			jedis.zadd(key.getBytes(),21, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilVolumetricProduction"),"OilVolumetricProduction","m^3/d",2,languageResourceMap.get("custom"),languageResourceMap.get("oilVolumetricProduction"))));
			jedis.zadd(key.getBytes(),22, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterVolumetricProduction"),"WaterVolumetricProduction","m^3/d",2,languageResourceMap.get("custom"),languageResourceMap.get("waterVolumetricProduction"))));
			jedis.zadd(key.getBytes(),23, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("availablePlungerStrokeProd_v"),"AvailablePlungerStrokeProd_v","m^3/d",2,languageResourceMap.get("custom"),languageResourceMap.get("availablePlungerStrokeProd_v"))));
			jedis.zadd(key.getBytes(),24, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpClearanceleakProd_v"),"PumpClearanceleakProd_v","m^3/d",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpClearanceleakProd_v"))));
			jedis.zadd(key.getBytes(),25, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("TVLeakVolumetricProduction"),"TVLeakVolumetricProduction","m^3/d",2,languageResourceMap.get("custom"),languageResourceMap.get("TVLeakVolumetricProduction"))));
			jedis.zadd(key.getBytes(),26, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("SVLeakVolumetricProduction"),"SVLeakVolumetricProduction","m^3/d",2,languageResourceMap.get("custom"),languageResourceMap.get("SVLeakVolumetricProduction"))));
			jedis.zadd(key.getBytes(),27, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("gasInfluenceProd_v"),"GasInfluenceProd_v","m^3/d",2,languageResourceMap.get("custom"),languageResourceMap.get("gasInfluenceProd_v"))));
			jedis.zadd(key.getBytes(),28, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidVolumetricProduction_l"),"LiquidVolumetricProduction_l","m^3/d",2,languageResourceMap.get("custom"),languageResourceMap.get("liquidVolumetricProduction_l"))));
			jedis.zadd(key.getBytes(),29, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilVolumetricProduction_l"),"OilVolumetricProduction_l","m^3/d",2,languageResourceMap.get("custom"),languageResourceMap.get("oilVolumetricProduction_l"))));
			jedis.zadd(key.getBytes(),30, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterVolumetricProduction_l"),"WaterVolumetricProduction_l","m^3/d",2,languageResourceMap.get("custom"),languageResourceMap.get("waterVolumetricProduction_l"))));
			
			jedis.zadd(key.getBytes(),31, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidWeightProduction"),"LiquidWeightProduction","t/d",2,languageResourceMap.get("custom"),languageResourceMap.get("liquidWeightProduction"))));
			jedis.zadd(key.getBytes(),32, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilWeightProduction"),"OilWeightProduction","t/d",2,languageResourceMap.get("custom"),languageResourceMap.get("oilWeightProduction"))));
			jedis.zadd(key.getBytes(),33, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterWeightProduction"),"WaterWeightProduction","t/d",2,languageResourceMap.get("custom"),languageResourceMap.get("waterWeightProduction"))));
			jedis.zadd(key.getBytes(),34, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("availablePlungerStrokeProd_w"),"AvailablePlungerStrokeProd_w","t/d",2,languageResourceMap.get("custom"),languageResourceMap.get("availablePlungerStrokeProd_w"))));
			jedis.zadd(key.getBytes(),35, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpClearanceleakProd_w"),"PumpClearanceleakProd_w","t/d",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpClearanceleakProd_w"))));
			jedis.zadd(key.getBytes(),36, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("TVLeakWeightProduction"),"TVLeakWeightProduction","t/d",2,languageResourceMap.get("custom"),languageResourceMap.get("TVLeakWeightProduction"))));
			jedis.zadd(key.getBytes(),37, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("SVLeakWeightProduction"),"SVLeakWeightProduction","t/d",2,languageResourceMap.get("custom"),languageResourceMap.get("SVLeakWeightProduction"))));
			jedis.zadd(key.getBytes(),38, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("gasInfluenceProd_w"),"GasInfluenceProd_w","t/d",2,languageResourceMap.get("custom"),languageResourceMap.get("gasInfluenceProd_w"))));
			jedis.zadd(key.getBytes(),39, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidWeightProduction_l"),"LiquidWeightProduction_l","t/d",2,languageResourceMap.get("custom"),languageResourceMap.get("liquidWeightProduction_l"))));
			jedis.zadd(key.getBytes(),40, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilWeightProduction_l"),"OilWeightProduction_l","t/d",2,languageResourceMap.get("custom"),languageResourceMap.get("oilWeightProduction_l"))));
			jedis.zadd(key.getBytes(),41, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterWeightProduction_l"),"WaterWeightProduction_l","t/d",2,languageResourceMap.get("custom"),languageResourceMap.get("waterWeightProduction_l"))));
			
			jedis.zadd(key.getBytes(),42, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("averageWatt"),"AverageWatt","kW",2,languageResourceMap.get("custom"),languageResourceMap.get("averageWatt"))));
			jedis.zadd(key.getBytes(),43, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("polishRodPower"),"PolishRodPower","kW",2,languageResourceMap.get("custom"),languageResourceMap.get("polishRodPower"))));
			jedis.zadd(key.getBytes(),44, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterPower"),"WaterPower","kW",2,languageResourceMap.get("custom"),languageResourceMap.get("waterPower"))));
			
			jedis.zadd(key.getBytes(),45, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("surfaceSystemEfficiency"),"SurfaceSystemEfficiency","",2,languageResourceMap.get("custom"),languageResourceMap.get("surfaceSystemEfficiency"))));
			jedis.zadd(key.getBytes(),46, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("wellDownSystemEfficiency"),"WellDownSystemEfficiency","",2,languageResourceMap.get("custom"),languageResourceMap.get("wellDownSystemEfficiency"))));
			jedis.zadd(key.getBytes(),47, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("systemEfficiency"),"SystemEfficiency","",2,languageResourceMap.get("custom"),languageResourceMap.get("systemEfficiency"))));
			jedis.zadd(key.getBytes(),48, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("area"),"Area","",2,languageResourceMap.get("custom"),languageResourceMap.get("area"))));
			jedis.zadd(key.getBytes(),49, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("energyPer100mLift"),"EnergyPer100mLift","kW· h/100m· t",2,languageResourceMap.get("custom"),languageResourceMap.get("energyPer100mLift"))));
			
			
			jedis.zadd(key.getBytes(),50, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("rodFlexLength"),"RodFlexLength","m",2,languageResourceMap.get("custom"),languageResourceMap.get("rodFlexLength"))));
			jedis.zadd(key.getBytes(),51, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("tubingFlexLength"),"TubingFlexLength","m",2,languageResourceMap.get("custom"),languageResourceMap.get("tubingFlexLength"))));
			jedis.zadd(key.getBytes(),52, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("inertiaLength"),"InertiaLength","m",2,languageResourceMap.get("custom"),languageResourceMap.get("inertiaLength"))));
			jedis.zadd(key.getBytes(),53, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff1"),"PumpEff1","",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpEff1"))));
			jedis.zadd(key.getBytes(),54, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff2"),"PumpEff2","",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpEff2"))));
			jedis.zadd(key.getBytes(),55, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff3"),"PumpEff3","",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpEff3"))));
			jedis.zadd(key.getBytes(),56, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff4"),"PumpEff4","",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpEff4"))));
			jedis.zadd(key.getBytes(),57, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff"),"PumpEff","",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpEff"))));
			
			jedis.zadd(key.getBytes(),58, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpIntakeP"),"PumpIntakeP","MPa",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpIntakeP"))));
			jedis.zadd(key.getBytes(),59, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpIntakeT"),"PumpIntakeT","℃",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpIntakeT"))));
			jedis.zadd(key.getBytes(),60, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpIntakeGOL"),"PumpIntakeGOL","m^3/m^3",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpIntakeGOL"))));
			jedis.zadd(key.getBytes(),61, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpIntakeVisl"),"PumpIntakeVisl","mPa·s",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpIntakeVisl"))));
			jedis.zadd(key.getBytes(),62, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpIntakeBo"),"PumpIntakeBo","",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpIntakeBo"))));
			
			jedis.zadd(key.getBytes(),63, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpOutletP"),"PumpOutletP","MPa",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpOutletP"))));
			jedis.zadd(key.getBytes(),64, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpOutletT"),"PumpOutletT","℃",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpOutletT"))));
			jedis.zadd(key.getBytes(),65, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpOutletGOL"),"PumpOutletGOL","m^3/m^3",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpOutletGOL"))));
			jedis.zadd(key.getBytes(),66, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpOutletVisl"),"PumpOutletVisl","mPa·s",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpOutletVisl"))));
			jedis.zadd(key.getBytes(),67, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpOutletBo"),"PumpOutletBo","",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpOutletBo"))));
			
			jedis.zadd(key.getBytes(),68, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("upStrokeIMax"),"UpStrokeIMax","A",2,languageResourceMap.get("custom"),languageResourceMap.get("upStrokeIMax"))));
			jedis.zadd(key.getBytes(),69, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("downStrokeIMax"),"DownStrokeIMax","A",2,languageResourceMap.get("custom"),languageResourceMap.get("downStrokeIMax"))));
			jedis.zadd(key.getBytes(),70, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("upStrokeWattMax"),"UpStrokeWattMax","kW",2,languageResourceMap.get("custom"),languageResourceMap.get("upStrokeWattMax"))));
			jedis.zadd(key.getBytes(),71, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("downStrokeWattMax"),"DownStrokeWattMax","kW",2,languageResourceMap.get("custom"),languageResourceMap.get("downStrokeWattMax"))));
			jedis.zadd(key.getBytes(),72, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("iDegreeBalance"),"IDegreeBalance","%",2,languageResourceMap.get("custom"),languageResourceMap.get("iDegreeBalance"))));
			jedis.zadd(key.getBytes(),73, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("wattDegreeBalance"),"WattDegreeBalance","%",2,languageResourceMap.get("custom"),languageResourceMap.get("wattDegreeBalance"))));
			jedis.zadd(key.getBytes(),74, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("deltaRadius"),"DeltaRadius","m",2,languageResourceMap.get("custom"),languageResourceMap.get("deltaRadius"))));
			
			jedis.zadd(key.getBytes(),75, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("levelDifferenceValue"),"LevelDifferenceValue","MPa",2,languageResourceMap.get("custom"),languageResourceMap.get("levelDifferenceValue"))));
			jedis.zadd(key.getBytes(),76, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("calcProducingfluidLevel"),"CalcProducingfluidLevel","m",2,languageResourceMap.get("custom"),languageResourceMap.get("calcProducingfluidLevel"))));
			
			jedis.zadd(key.getBytes(),77, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("submergence"),"Submergence","m",2,languageResourceMap.get("custom"),languageResourceMap.get("submergence"))));
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
		String key="pcpCalItemList_"+language;
		if(!existsKey(key)){
			MemoryDataManagerTask.loadPCPCalculateItem(language);
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			List<byte[]> calItemSet= jedis.zrange(key.getBytes(), 0, -1);
			for(byte[] rpcCalItemByteArr:calItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(rpcCalItemByteArr);
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
		String key="pcpCalItemList_"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
			}
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTime"),"CommTime","h",2,languageResourceMap.get("custom"),languageResourceMap.get("commTime"))));
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTimeEfficiency"),"CommTimeEfficiency",timeEfficiencyUnit,2,languageResourceMap.get("custom"),languageResourceMap.get("commTimeEfficiency"))));
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commRange"),"CommRange","",1,languageResourceMap.get("custom"),languageResourceMap.get("commRange"))));
			
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTime"),"RunTime","h",2,languageResourceMap.get("custom"),languageResourceMap.get("runTime"))));
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTimeEfficiency"),"RunTimeEfficiency",timeEfficiencyUnit,2,languageResourceMap.get("custom"),languageResourceMap.get("runTimeEfficiency"))));
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runRange"),"RunRange","",1,languageResourceMap.get("custom"),languageResourceMap.get("runRange"))));
			
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("theoreticalProduction"),"TheoreticalProduction","m^3/d",2,languageResourceMap.get("custom"),languageResourceMap.get("theoreticalProduction"))));
			
			jedis.zadd(key.getBytes(),8, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidVolumetricProduction"),"LiquidVolumetricProduction","m^3/d",2,languageResourceMap.get("custom"),languageResourceMap.get("liquidVolumetricProduction"))));
			jedis.zadd(key.getBytes(),9, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilVolumetricProduction"),"OilVolumetricProduction","m^3/d",2,languageResourceMap.get("custom"),languageResourceMap.get("oilVolumetricProduction"))));
			jedis.zadd(key.getBytes(),10, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterVolumetricProduction"),"WaterVolumetricProduction","m^3/d",2,languageResourceMap.get("custom"),languageResourceMap.get("waterVolumetricProduction"))));
			jedis.zadd(key.getBytes(),11, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidVolumetricProduction_l"),"LiquidVolumetricProduction_l","m^3/d",2,languageResourceMap.get("custom"),languageResourceMap.get("liquidVolumetricProduction_l"))));
			jedis.zadd(key.getBytes(),12, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilVolumetricProduction_l"),"OilVolumetricProduction_l","m^3/d",2,languageResourceMap.get("custom"),languageResourceMap.get("oilVolumetricProduction_l"))));
			jedis.zadd(key.getBytes(),13, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterVolumetricProduction_l"),"WaterVolumetricProduction_l","m^3/d",2,languageResourceMap.get("custom"),languageResourceMap.get("waterVolumetricProduction_l"))));
			
			jedis.zadd(key.getBytes(),14, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidWeightProduction"),"LiquidWeightProduction","t/d",2,languageResourceMap.get("custom"),languageResourceMap.get("liquidWeightProduction"))));
			jedis.zadd(key.getBytes(),15, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilWeightProduction"),"OilWeightProduction","t/d",2,languageResourceMap.get("custom"),languageResourceMap.get("oilWeightProduction"))));
			jedis.zadd(key.getBytes(),16, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterWeightProduction"),"WaterWeightProduction","t/d",2,languageResourceMap.get("custom"),languageResourceMap.get("waterWeightProduction"))));
			jedis.zadd(key.getBytes(),17, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidWeightProduction_l"),"LiquidWeightProduction_l","t/d",2,languageResourceMap.get("custom"),languageResourceMap.get("liquidWeightProduction_l"))));
			jedis.zadd(key.getBytes(),18, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilWeightProduction_l"),"OilWeightProduction_l","t/d",2,languageResourceMap.get("custom"),languageResourceMap.get("oilWeightProduction_l"))));
			jedis.zadd(key.getBytes(),19, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterWeightProduction_l"),"WaterWeightProduction_l","t/d",2,languageResourceMap.get("custom"),languageResourceMap.get("waterWeightProduction_l"))));
			
			jedis.zadd(key.getBytes(),20, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("averageWatt"),"AverageWatt","kW",2,languageResourceMap.get("custom"),languageResourceMap.get("averageWatt"))));
			jedis.zadd(key.getBytes(),21, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterPower"),"WaterPower","kW",2,languageResourceMap.get("custom"),languageResourceMap.get("waterPower"))));
			jedis.zadd(key.getBytes(),22, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("systemEfficiency"),"SystemEfficiency","",2,languageResourceMap.get("custom"),languageResourceMap.get("systemEfficiency"))));
			
			jedis.zadd(key.getBytes(),23, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff1_pcp"),"PumpEff1","",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpEff1_pcp"))));
			jedis.zadd(key.getBytes(),24, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff2_pcp"),"PumpEff2","",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpEff2_pcp"))));
			jedis.zadd(key.getBytes(),25, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff"),"PumpEff","",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpEff"))));
			
			jedis.zadd(key.getBytes(),26, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpIntakeP"),"PumpIntakeP","MPa",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpIntakeP"))));
			jedis.zadd(key.getBytes(),27, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpIntakeT"),"PumpIntakeT","℃",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpIntakeT"))));
			jedis.zadd(key.getBytes(),28, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpIntakeGOL"),"PumpIntakeGOL","m^3/m^3",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpIntakeGOL"))));
			jedis.zadd(key.getBytes(),29, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpIntakeVisl"),"PumpIntakeVisl","mPa·s",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpIntakeVisl"))));
			jedis.zadd(key.getBytes(),30, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpIntakeBo"),"PumpIntakeBo","",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpIntakeBo"))));
			
			jedis.zadd(key.getBytes(),31, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpOutletP"),"PumpOutletP","MPa",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpOutletP"))));
			jedis.zadd(key.getBytes(),32, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpOutletT"),"PumpOutletT","℃",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpOutletT"))));
			jedis.zadd(key.getBytes(),33, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpOutletGOL"),"PumpOutletGOL","m^3/m^3",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpOutletGOL"))));
			jedis.zadd(key.getBytes(),34, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpOutletVisl"),"PumpOutletVisl","mPa·s",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpOutletVisl"))));
			jedis.zadd(key.getBytes(),35, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpOutletBo"),"PumpOutletBo","",2,languageResourceMap.get("custom"),languageResourceMap.get("pumpOutletBo"))));
			
//			jedis.zadd(key.getBytes(),36, SerializeObjectUnils.serialize(new CalItem("日用电量","TodayKWattH","kW·h",2,languageResourceMap.get("calculate"),"日用电量")));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static List<CalItem> getAcqTotalCalculateItem(String language){
		Jedis jedis=null;
		List<CalItem> calItemList=new ArrayList<>();
		String key="acqTotalCalItemList_"+language;
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
		String key="acqTotalCalItemList_"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
			}
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(Config.getInstance().configFile.getAp().getOthers().getDeviceShowName(),"DeviceName","",1,languageResourceMap.get("input"),Config.getInstance().configFile.getAp().getOthers().getDeviceShowName())));//1-字符串 2-数值 3-日期 4-日期时间
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem("日期","CalDate","",3,languageResourceMap.get("calculate"),"日期")));
			
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTime"),"CommTime","h",2,languageResourceMap.get("calculate"),"在线时间,通信计算所得")));
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTimeEfficiency"),"CommTimeEfficiency",timeEfficiencyUnit,2,languageResourceMap.get("calculate"),"在线时率,通信计算所得")));
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commRange"),"CommRange","",1,languageResourceMap.get("calculate"),"在线区间,通信计算所得")));
			
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTime"),"RunTime","h",2,languageResourceMap.get("calculate"),"运行时间,时率计算所得")));
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTimeEfficiency"),"RunTimeEfficiency",timeEfficiencyUnit,2,languageResourceMap.get("calculate"),"运行时率,时率计算所得")));
			jedis.zadd(key.getBytes(),8, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runRange"),"RunRange","",1,languageResourceMap.get("calculate"),"运行区间,时率计算所得")));
			
			jedis.zadd(key.getBytes(),9, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("remark"),"Remark","",1,languageResourceMap.get("input"),languageResourceMap.get("remark"))));
			
			jedis.zadd(key.getBytes(),10, SerializeObjectUnils.serialize(new CalItem("备用1","reservedcol1","",1,languageResourceMap.get("input"),"备用1")));
			jedis.zadd(key.getBytes(),11, SerializeObjectUnils.serialize(new CalItem("备用2","reservedcol2","",1,languageResourceMap.get("input"),"备用2")));
			jedis.zadd(key.getBytes(),12, SerializeObjectUnils.serialize(new CalItem("备用3","reservedcol3","",1,languageResourceMap.get("input"),"备用3")));
			jedis.zadd(key.getBytes(),13, SerializeObjectUnils.serialize(new CalItem("备用4","reservedcol4","",1,languageResourceMap.get("input"),"备用4")));
			jedis.zadd(key.getBytes(),14, SerializeObjectUnils.serialize(new CalItem("备用5","reservedcol5","",1,languageResourceMap.get("input"),"备用5")));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static List<CalItem> getRPCTotalCalculateItem(String language){
		Jedis jedis=null;
		List<CalItem> calItemList=new ArrayList<>();
		String key="rpcTotalCalItemList_"+language;
		if(!existsKey(key)){
			MemoryDataManagerTask.loadRPCTotalCalculateItem(language);
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			List<byte[]> calItemSet= jedis.zrange("rpcTotalCalItemList".getBytes(), 0, -1);
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
	
	public static void loadRPCTotalCalculateItem(String language){
		Jedis jedis=null;
		String key="rpcTotalCalItemList_"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
			}
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(Config.getInstance().configFile.getAp().getOthers().getDeviceShowName(),"DeviceName","",1,languageResourceMap.get("input"),Config.getInstance().configFile.getAp().getOthers().getDeviceShowName())));//1-字符串 2-数值 3-日期 4-日期时间
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem("日期","CalDate","",3,languageResourceMap.get("calculate"),"日期")));
			
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTime"),"CommTime","h",2,languageResourceMap.get("calculate"),"在线时间,通信计算所得")));
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTimeEfficiency"),"CommTimeEfficiency",timeEfficiencyUnit,2,languageResourceMap.get("calculate"),"在线时率,通信计算所得")));
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commRange"),"CommRange","",1,languageResourceMap.get("calculate"),"在线区间,通信计算所得")));
			
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTime"),"RunTime","h",2,languageResourceMap.get("calculate"),"运行时间,时率计算所得")));
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTimeEfficiency"),"RunTimeEfficiency",timeEfficiencyUnit,2,languageResourceMap.get("calculate"),"运行时率,时率计算所得")));
			jedis.zadd(key.getBytes(),8, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runRange"),"RunRange","",1,languageResourceMap.get("calculate"),"运行区间,时率计算所得")));
			
			jedis.zadd(key.getBytes(),9, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("resultName"),"ResultName","",1,languageResourceMap.get("calculate"),"功图工况")));
			jedis.zadd(key.getBytes(),10, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("optimizationSuggestion"),"OptimizationSuggestion","",1,languageResourceMap.get("calculate"),languageResourceMap.get("optimizationSuggestion"))));
			jedis.zadd(key.getBytes(),11, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("stroke"),"Stroke","m",2,languageResourceMap.get("calculate"),languageResourceMap.get("stroke"))));
			jedis.zadd(key.getBytes(),12, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("SPM"),"SPM","次/min",2,languageResourceMap.get("calculate"),languageResourceMap.get("SPM"))));
			jedis.zadd(key.getBytes(),13, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("fMax"),"FMax","kN",2,languageResourceMap.get("calculate"),languageResourceMap.get("fMax"))));
			jedis.zadd(key.getBytes(),14, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("fMin"),"FMin","kN",2,languageResourceMap.get("calculate"),languageResourceMap.get("fMin"))));
			
			jedis.zadd(key.getBytes(),15, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("fullnessCoefficient"),"FullnessCoefficient","",2,languageResourceMap.get("calculate"),languageResourceMap.get("fullnessCoefficient"))));
			
			jedis.zadd(key.getBytes(),16, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("theoreticalProduction"),"TheoreticalProduction","m^3/d",2,languageResourceMap.get("calculate"),languageResourceMap.get("theoreticalProduction"))));
			
			jedis.zadd(key.getBytes(),17, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidVolumetricProduction_l"),"LiquidVolumetricProduction","m^3/d",2,languageResourceMap.get("calculate"),"日累计产液量,功图汇总计算所得")));
			jedis.zadd(key.getBytes(),18, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilVolumetricProduction_l"),"OilVolumetricProduction","m^3/d",2,languageResourceMap.get("calculate"),"日累计产油量,功图汇总计算所得")));
			jedis.zadd(key.getBytes(),19, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterVolumetricProduction_l"),"WaterVolumetricProduction","m^3/d",2,languageResourceMap.get("calculate"),"日累计产水量,功图汇总计算或者累计产水量计算所得")));
//			jedis.zadd(key.getBytes(),20, SerializeObjectUnils.serialize(new CalItem("日累计产气量","GasVolumetricProduction","m^3/d",2,languageResourceMap.get("calculate"),"日累计产气量，累计产气量计算所得")));
			jedis.zadd(key.getBytes(),21, SerializeObjectUnils.serialize(new CalItem("体积含水率","VolumeWaterCut","%",2,languageResourceMap.get("calculate"),"体积含水率")));
			
			jedis.zadd(key.getBytes(),22, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidWeightProduction_l"),"LiquidWeightProduction","t/d",2,languageResourceMap.get("calculate"),"日累计产液量,功图汇总计算所得")));
			jedis.zadd(key.getBytes(),23, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilWeightProduction_l"),"OilWeightProduction","t/d",2,languageResourceMap.get("calculate"),"日累计产油量,功图汇总计算所得")));
			jedis.zadd(key.getBytes(),24, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterWeightProduction_l"),"WaterWeightProduction","t/d",2,languageResourceMap.get("calculate"),"日累计产水量,功图汇总计算或者累计产水量计算所得")));
			jedis.zadd(key.getBytes(),25, SerializeObjectUnils.serialize(new CalItem("重量含水率","WeightWaterCut","%",2,languageResourceMap.get("calculate"),"重量含水率")));
			
			jedis.zadd(key.getBytes(),26, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("surfaceSystemEfficiency"),"SurfaceSystemEfficiency","",2,languageResourceMap.get("calculate"),languageResourceMap.get("surfaceSystemEfficiency"))));
			jedis.zadd(key.getBytes(),27, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("wellDownSystemEfficiency"),"WellDownSystemEfficiency","",2,languageResourceMap.get("calculate"),languageResourceMap.get("wellDownSystemEfficiency"))));
			jedis.zadd(key.getBytes(),28, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("systemEfficiency"),"SystemEfficiency","",2,languageResourceMap.get("calculate"),languageResourceMap.get("systemEfficiency"))));
			jedis.zadd(key.getBytes(),29, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("energyPer100mLift"),"EnergyPer100mLift","kW· h/100m· t",2,languageResourceMap.get("calculate"),languageResourceMap.get("energyPer100mLift"))));
			
			jedis.zadd(key.getBytes(),30, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff1"),"PumpEff1","",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpEff1"))));
			jedis.zadd(key.getBytes(),31, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff2"),"PumpEff2","",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpEff2"))));
			jedis.zadd(key.getBytes(),32, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff3"),"PumpEff3","",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpEff3"))));
			jedis.zadd(key.getBytes(),33, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff4"),"PumpEff4","",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpEff4"))));
			
			jedis.zadd(key.getBytes(),34, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff1_pcp"),"PumpEff1","",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpEff1_pcp"))));
			
			jedis.zadd(key.getBytes(),35, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff"),"PumpEff","",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpEff"))));
			
			jedis.zadd(key.getBytes(),36, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("RPM"),"RPM","r/min",2,languageResourceMap.get("calculate"),languageResourceMap.get("RPM"))));
			
			jedis.zadd(key.getBytes(),37, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("iDegreeBalance"),"IDegreeBalance","%",2,languageResourceMap.get("calculate"),languageResourceMap.get("iDegreeBalance"))));
			jedis.zadd(key.getBytes(),38, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("wattDegreeBalance"),"WattDegreeBalance","%",2,languageResourceMap.get("calculate"),languageResourceMap.get("wattDegreeBalance"))));
			jedis.zadd(key.getBytes(),39, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("deltaRadius"),"DeltaRadius","m",2,languageResourceMap.get("calculate"),languageResourceMap.get("deltaRadius"))));
			
//			jedis.zadd(key.getBytes(),40, SerializeObjectUnils.serialize(new CalItem("日用电量","TodayKWattH","kW·h",2,languageResourceMap.get("calculate"),"日用电量,累计用电量计算所得")));
//			jedis.zadd(key.getBytes(),42, SerializeObjectUnils.serialize(new CalItem("累计用电量","TotalKWattH","kW·h",2,languageResourceMap.get("calculate"),"累计用电量,当日最新采集数据")));
//			
//			jedis.zadd(key.getBytes(),42, SerializeObjectUnils.serialize(new CalItem("累计产气量","TotalGasVolumetricProduction","m^3",2,languageResourceMap.get("calculate"),"累计产气量,当日最新采集数据")));
//			jedis.zadd(key.getBytes(),43, SerializeObjectUnils.serialize(new CalItem("累计产水量","TotalWaterVolumetricProduction","m^3",2,languageResourceMap.get("calculate"),"累计产水量,当日最新采集数据")));
			
			jedis.zadd(key.getBytes(),44, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpSettingDepth"),"PumpSettingDepth","m",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpSettingDepth"))));
			jedis.zadd(key.getBytes(),45, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("producingfluidLevel"),"ProducingfluidLevel","m",2,languageResourceMap.get("calculate"),languageResourceMap.get("producingfluidLevel"))));
			jedis.zadd(key.getBytes(),46, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("calcProducingfluidLevel"),"CalcProducingfluidLevel","m",2,languageResourceMap.get("calculate"),languageResourceMap.get("calcProducingfluidLevel"))));
			jedis.zadd(key.getBytes(),47, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("submergence"),"Submergence","m",2,languageResourceMap.get("calculate"),languageResourceMap.get("submergence"))));
			
			jedis.zadd(key.getBytes(),48, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("tubingPressure"),"TubingPressure","MPa",2,languageResourceMap.get("calculate"),languageResourceMap.get("tubingPressure"))));
			jedis.zadd(key.getBytes(),49, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("casingPressure"),"CasingPressure","MPa",2,languageResourceMap.get("calculate"),languageResourceMap.get("casingPressure"))));
			jedis.zadd(key.getBytes(),50, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("bottomHolePressure"),"BottomHolePressure","MPa",2,languageResourceMap.get("calculate"),languageResourceMap.get("bottomHolePressure"))));
			
			jedis.zadd(key.getBytes(),51, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("remark"),"Remark","",1,languageResourceMap.get("input"),languageResourceMap.get("remark"))));
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
		String key="pcpTotalCalItemList_"+language;
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
		String key="pcpTotalCalItemList_"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
			}
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(Config.getInstance().configFile.getAp().getOthers().getDeviceShowName(),"DeviceName","",1,languageResourceMap.get("input"),Config.getInstance().configFile.getAp().getOthers().getDeviceShowName())));
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem("日期","CalDate","",3,languageResourceMap.get("calculate"),"日期")));
			
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTime"),"CommTime","h",2,languageResourceMap.get("calculate"),"在线时间,通信计算所得")));
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTimeEfficiency"),"CommTimeEfficiency",timeEfficiencyUnit,2,languageResourceMap.get("calculate"),"在线时率,通信计算所得")));
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commRange"),"CommRange","",1,languageResourceMap.get("calculate"),"在线区间,通信计算所得")));
			
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTime"),"RunTime","h",2,languageResourceMap.get("calculate"),"运行时间,时率计算所得")));
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTimeEfficiency"),"RunTimeEfficiency",timeEfficiencyUnit,2,languageResourceMap.get("calculate"),"运行时率,时率计算所得")));
			jedis.zadd(key.getBytes(),8, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runRange"),"RunRange","",1,languageResourceMap.get("calculate"),"运行区间,时率计算所得")));
			
			jedis.zadd(key.getBytes(),9, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("RPM"),"RPM","r/min",2,languageResourceMap.get("calculate"),languageResourceMap.get("RPM"))));
			
			jedis.zadd(key.getBytes(),10, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("theoreticalProduction"),"TheoreticalProduction","m^3/d",2,languageResourceMap.get("calculate"),languageResourceMap.get("theoreticalProduction"))));
			
			jedis.zadd(key.getBytes(),11, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidVolumetricProduction_l"),"LiquidVolumetricProduction","m^3/d",2,languageResourceMap.get("calculate"),"日累计产液量,转速汇总计算所得")));
			jedis.zadd(key.getBytes(),12, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilVolumetricProduction_l"),"OilVolumetricProduction","m^3/d",2,languageResourceMap.get("calculate"),"日累计产油量,转速汇总计算所得")));
			jedis.zadd(key.getBytes(),13, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterVolumetricProduction_l"),"WaterVolumetricProduction","m^3/d",2,languageResourceMap.get("calculate"),"日累计产水量,转速汇总计算或者累计产水量计算所得")));
//			jedis.zadd(key.getBytes(),14, SerializeObjectUnils.serialize(new CalItem("日累计产气量","GasVolumetricProduction","m^3/d",2,languageResourceMap.get("calculate"),"日累计产气量,累计产气量计算所得")));
			jedis.zadd(key.getBytes(),15, SerializeObjectUnils.serialize(new CalItem("体积含水率","VolumeWaterCut","%",2,languageResourceMap.get("calculate"),"体积含水率")));
			
			jedis.zadd(key.getBytes(),16, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidWeightProduction_l"),"LiquidWeightProduction","t/d",2,languageResourceMap.get("calculate"),"日累计产液量,转速汇总计算所得")));
			jedis.zadd(key.getBytes(),17, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilWeightProduction_l"),"OilWeightProduction","t/d",2,languageResourceMap.get("calculate"),"日累计产油量,转速汇总计算所得")));
			jedis.zadd(key.getBytes(),18, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterWeightProduction_l"),"WaterWeightProduction","t/d",2,languageResourceMap.get("calculate"),"日累计产水量,转速汇总计算或者累计产水量计算所得")));
			jedis.zadd(key.getBytes(),19, SerializeObjectUnils.serialize(new CalItem("重量含水率","WeightWaterCut","%",2,languageResourceMap.get("calculate"),"重量含水率")));
			
			jedis.zadd(key.getBytes(),20, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("systemEfficiency"),"SystemEfficiency","",2,languageResourceMap.get("calculate"),languageResourceMap.get("systemEfficiency"))));
			jedis.zadd(key.getBytes(),21, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("energyPer100mLift"),"EnergyPer100mLift","kW· h/100m· t",2,languageResourceMap.get("calculate"),languageResourceMap.get("energyPer100mLift"))));
			
			jedis.zadd(key.getBytes(),22, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff1_pcp"),"PumpEff1","",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpEff1_pcp"))));
			jedis.zadd(key.getBytes(),23, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff2_pcp"),"PumpEff2","",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpEff2_pcp"))));
			jedis.zadd(key.getBytes(),24, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff"),"PumpEff","",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpEff"))));
			
//			jedis.zadd(key.getBytes(),25, SerializeObjectUnils.serialize(new CalItem("日用电量","TodayKWattH","kW·h",2,languageResourceMap.get("calculate"),"日用电量,累计用电量计算所得")));
//			jedis.zadd(key.getBytes(),26, SerializeObjectUnils.serialize(new CalItem("累计用电量","TotalKWattH","kW·h",2,languageResourceMap.get("calculate"),"累计用电量,当日最新采集数据")));
//			
//			jedis.zadd(key.getBytes(),27, SerializeObjectUnils.serialize(new CalItem("累计产气量","TotalGasVolumetricProduction","m^3",2,languageResourceMap.get("calculate"),"累计产气量,当日最新采集数据")));
//			jedis.zadd(key.getBytes(),28, SerializeObjectUnils.serialize(new CalItem("累计产水量","TotalWaterVolumetricProduction","m^3",2,languageResourceMap.get("calculate"),"累计产水量,当日最新采集数据")));
			
			jedis.zadd(key.getBytes(),29, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpSettingDepth"),"PumpSettingDepth","m",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpSettingDepth"))));
			jedis.zadd(key.getBytes(),30, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("producingfluidLevel"),"ProducingfluidLevel","m",2,languageResourceMap.get("calculate"),languageResourceMap.get("producingfluidLevel"))));
			jedis.zadd(key.getBytes(),31, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("submergence"),"Submergence","m",2,languageResourceMap.get("calculate"),languageResourceMap.get("submergence"))));
			
			jedis.zadd(key.getBytes(),32, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("tubingPressure"),"TubingPressure","MPa",2,languageResourceMap.get("calculate"),languageResourceMap.get("tubingPressure"))));
			jedis.zadd(key.getBytes(),33, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("casingPressure"),"CasingPressure","MPa",2,languageResourceMap.get("calculate"),languageResourceMap.get("casingPressure"))));
			jedis.zadd(key.getBytes(),34, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("bottomHolePressure"),"BottomHolePressure","MPa",2,languageResourceMap.get("calculate"),languageResourceMap.get("bottomHolePressure"))));
			
			jedis.zadd(key.getBytes(),35, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("remark"),"Remark","",1,languageResourceMap.get("input"),languageResourceMap.get("remark"))));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static List<CalItem> getAcqTimingTotalCalculateItem(String language){
		Jedis jedis=null;
		List<CalItem> calItemList=new ArrayList<>();
		String key="acqTimingTotalCalItemList_"+language;
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
		String key="acqTimingTotalCalItemList_"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
			}
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(Config.getInstance().configFile.getAp().getOthers().getDeviceShowName(),"DeviceName","",1,languageResourceMap.get("input"),Config.getInstance().configFile.getAp().getOthers().getDeviceShowName())));//1-字符串 2-数值 3-日期 4-日期时间
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem("时间","CalTime","",4,languageResourceMap.get("calculate"),"时间")));
			
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTime"),"CommTime","h",2,languageResourceMap.get("calculate"),"在线时间,通信计算所得")));
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTimeEfficiency"),"CommTimeEfficiency",timeEfficiencyUnit,2,languageResourceMap.get("calculate"),"在线时率,通信计算所得")));
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commRange"),"CommRange","",1,languageResourceMap.get("calculate"),"在线区间,通信计算所得")));
			
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTime"),"RunTime","h",2,languageResourceMap.get("calculate"),"运行时间,时率计算所得")));
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTimeEfficiency"),"RunTimeEfficiency",timeEfficiencyUnit,2,languageResourceMap.get("calculate"),"运行时率,时率计算所得")));
			jedis.zadd(key.getBytes(),8, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runRange"),"RunRange","",1,languageResourceMap.get("calculate"),"运行区间,时率计算所得")));
			
			
			
			jedis.zadd(key.getBytes(),9, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("remark"),"Remark","",1,languageResourceMap.get("input"),languageResourceMap.get("remark"))));
			
			jedis.zadd(key.getBytes(),10, SerializeObjectUnils.serialize(new CalItem("备用1","reservedcol1","",1,languageResourceMap.get("input"),"备用1")));
			jedis.zadd(key.getBytes(),11, SerializeObjectUnils.serialize(new CalItem("备用2","reservedcol2","",1,languageResourceMap.get("input"),"备用2")));
			jedis.zadd(key.getBytes(),12, SerializeObjectUnils.serialize(new CalItem("备用3","reservedcol3","",1,languageResourceMap.get("input"),"备用3")));
			jedis.zadd(key.getBytes(),13, SerializeObjectUnils.serialize(new CalItem("备用4","reservedcol4","",1,languageResourceMap.get("input"),"备用4")));
			jedis.zadd(key.getBytes(),14, SerializeObjectUnils.serialize(new CalItem("备用5","reservedcol5","",1,languageResourceMap.get("input"),"备用5")));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static List<CalItem> getRPCTimingTotalCalculateItem(String language){
		Jedis jedis=null;
		List<CalItem> calItemList=new ArrayList<>();
		String key="rpcTimingTotalCalItemList_"+language;
		if(!existsKey(key)){
			MemoryDataManagerTask.loadRPCTimingTotalCalculateItem(language);
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
	
	public static void loadRPCTimingTotalCalculateItem(String language){
		Jedis jedis=null;
		String key="rpcTimingTotalCalItemList_"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
			}
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(Config.getInstance().configFile.getAp().getOthers().getDeviceShowName(),"DeviceName","",1,languageResourceMap.get("input"),Config.getInstance().configFile.getAp().getOthers().getDeviceShowName())));//1-字符串 2-数值 3-日期 4-日期时间
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem("时间","CalTime","",4,languageResourceMap.get("calculate"),"时间")));
			
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTime"),"CommTime","h",2,languageResourceMap.get("calculate"),"在线时间,通信计算所得")));
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTimeEfficiency"),"CommTimeEfficiency",timeEfficiencyUnit,2,languageResourceMap.get("calculate"),"在线时率,通信计算所得")));
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commRange"),"CommRange","",1,languageResourceMap.get("calculate"),"在线区间,通信计算所得")));
			
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTime"),"RunTime","h",2,languageResourceMap.get("calculate"),"运行时间,时率计算所得")));
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTimeEfficiency"),"RunTimeEfficiency",timeEfficiencyUnit,2,languageResourceMap.get("calculate"),"运行时率,时率计算所得")));
			jedis.zadd(key.getBytes(),8, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runRange"),"RunRange","",1,languageResourceMap.get("calculate"),"运行区间,时率计算所得")));
			
			jedis.zadd(key.getBytes(),9, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("resultName"),"ResultName","",1,languageResourceMap.get("calculate"),languageResourceMap.get("resultName"))));
			jedis.zadd(key.getBytes(),10, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("optimizationSuggestion"),"OptimizationSuggestion","",1,languageResourceMap.get("calculate"),languageResourceMap.get("optimizationSuggestion"))));
			jedis.zadd(key.getBytes(),11, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("stroke"),"Stroke","m",2,languageResourceMap.get("calculate"),languageResourceMap.get("stroke"))));
			jedis.zadd(key.getBytes(),12, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("SPM"),"SPM","次/min",2,languageResourceMap.get("calculate"),languageResourceMap.get("SPM"))));
			jedis.zadd(key.getBytes(),13, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("fMax"),"FMax","kN",2,languageResourceMap.get("calculate"),languageResourceMap.get("fMax"))));
			jedis.zadd(key.getBytes(),14, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("fMin"),"FMin","kN",2,languageResourceMap.get("calculate"),languageResourceMap.get("fMin"))));
			
			jedis.zadd(key.getBytes(),15, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("fullnessCoefficient"),"FullnessCoefficient","",2,languageResourceMap.get("calculate"),languageResourceMap.get("fullnessCoefficient"))));
			
			jedis.zadd(key.getBytes(),16, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("theoreticalProduction"),"TheoreticalProduction","m^3/d",2,languageResourceMap.get("calculate"),languageResourceMap.get("theoreticalProduction"))));
			
			jedis.zadd(key.getBytes(),17, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidVolumetricProduction_l"),"LiquidVolumetricProduction","m^3/d",2,languageResourceMap.get("calculate"),"日累计产液量,功图汇总计算所得")));
			jedis.zadd(key.getBytes(),18, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilVolumetricProduction_l"),"OilVolumetricProduction","m^3/d",2,languageResourceMap.get("calculate"),"日累计产油量,功图汇总计算所得")));
			jedis.zadd(key.getBytes(),19, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterVolumetricProduction_l"),"WaterVolumetricProduction","m^3/d",2,languageResourceMap.get("calculate"),"日累计产水量,功图汇总计算或者累计产水量计算所得")));
//			jedis.zadd(key.getBytes(),20, SerializeObjectUnils.serialize(new CalItem("日累计产气量","GasVolumetricProduction","m^3/d",2,languageResourceMap.get("calculate"),"日累计产气量，累计产气量计算所得")));
			jedis.zadd(key.getBytes(),21, SerializeObjectUnils.serialize(new CalItem("体积含水率","VolumeWaterCut","%",2,languageResourceMap.get("calculate"),"体积含水率")));
			
			jedis.zadd(key.getBytes(),22, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidWeightProduction_l"),"LiquidWeightProduction","t/d",2,languageResourceMap.get("calculate"),"日累计产液量,功图汇总计算所得")));
			jedis.zadd(key.getBytes(),23, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilWeightProduction_l"),"OilWeightProduction","t/d",2,languageResourceMap.get("calculate"),"日累计产油量,功图汇总计算所得")));
			jedis.zadd(key.getBytes(),24, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterWeightProduction_l"),"WaterWeightProduction","t/d",2,languageResourceMap.get("calculate"),"日累计产水量,功图汇总计算或者累计产水量计算所得")));
			jedis.zadd(key.getBytes(),25, SerializeObjectUnils.serialize(new CalItem("重量含水率","WeightWaterCut","%",2,languageResourceMap.get("calculate"),"重量含水率")));
			
			jedis.zadd(key.getBytes(),26, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("surfaceSystemEfficiency"),"SurfaceSystemEfficiency","",2,languageResourceMap.get("calculate"),languageResourceMap.get("surfaceSystemEfficiency"))));
			jedis.zadd(key.getBytes(),27, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("wellDownSystemEfficiency"),"WellDownSystemEfficiency","",2,languageResourceMap.get("calculate"),languageResourceMap.get("wellDownSystemEfficiency"))));
			jedis.zadd(key.getBytes(),28, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("systemEfficiency"),"SystemEfficiency","",2,languageResourceMap.get("calculate"),languageResourceMap.get("systemEfficiency"))));
			jedis.zadd(key.getBytes(),29, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("energyPer100mLift"),"EnergyPer100mLift","kW· h/100m· t",2,languageResourceMap.get("calculate"),languageResourceMap.get("energyPer100mLift"))));
			
			jedis.zadd(key.getBytes(),30, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff1"),"PumpEff1","",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpEff1"))));
			jedis.zadd(key.getBytes(),31, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff2"),"PumpEff2","",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpEff2"))));
			jedis.zadd(key.getBytes(),32, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff3"),"PumpEff3","",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpEff3"))));
			jedis.zadd(key.getBytes(),33, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff4"),"PumpEff4","",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpEff4"))));
			
			jedis.zadd(key.getBytes(),34, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff1_pcp"),"PumpEff1","",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpEff1_pcp"))));
			
			jedis.zadd(key.getBytes(),35, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff"),"PumpEff","",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpEff"))));
			
			jedis.zadd(key.getBytes(),36, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("RPM"),"RPM","r/min",2,languageResourceMap.get("calculate"),languageResourceMap.get("RPM"))));
			
			jedis.zadd(key.getBytes(),37, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("iDegreeBalance"),"IDegreeBalance","%",2,languageResourceMap.get("calculate"),languageResourceMap.get("iDegreeBalance"))));
			jedis.zadd(key.getBytes(),38, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("wattDegreeBalance"),"WattDegreeBalance","%",2,languageResourceMap.get("calculate"),languageResourceMap.get("wattDegreeBalance"))));
			jedis.zadd(key.getBytes(),39, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("deltaRadius"),"DeltaRadius","m",2,languageResourceMap.get("calculate"),languageResourceMap.get("deltaRadius"))));
			
//			jedis.zadd(key.getBytes(),40, SerializeObjectUnils.serialize(new CalItem("日用电量","TodayKWattH","kW·h",2,languageResourceMap.get("calculate"),"日用电量,累计用电量计算所得")));
//			jedis.zadd(key.getBytes(),41, SerializeObjectUnils.serialize(new CalItem("累计用电量","TotalKWattH","kW·h",2,languageResourceMap.get("calculate"),"累计用电量,当日最新采集数据")));
//			
//			jedis.zadd(key.getBytes(),42, SerializeObjectUnils.serialize(new CalItem("累计产气量","TotalGasVolumetricProduction","m^3",2,languageResourceMap.get("calculate"),"累计产气量,当日最新采集数据")));
//			jedis.zadd(key.getBytes(),43, SerializeObjectUnils.serialize(new CalItem("累计产水量","TotalWaterVolumetricProduction","m^3",2,languageResourceMap.get("calculate"),"累计产水量,当日最新采集数据")));
			
			jedis.zadd(key.getBytes(),44, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidVolumetricProduction"),"RealtimeLiquidVolumetricProduction","m^3/d",2,languageResourceMap.get("calculate"),languageResourceMap.get("liquidVolumetricProduction"))));
			jedis.zadd(key.getBytes(),45, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilVolumetricProduction"),"RealtimeOilVolumetricProduction","m^3/d",2,languageResourceMap.get("calculate"),languageResourceMap.get("oilVolumetricProduction"))));
			jedis.zadd(key.getBytes(),46, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterVolumetricProduction"),"RealtimeWaterVolumetricProduction","m^3/d",2,languageResourceMap.get("calculate"),languageResourceMap.get("waterVolumetricProduction"))));
//			jedis.zadd(key.getBytes(),47, SerializeObjectUnils.serialize(new CalItem("瞬时产气量","RealtimeGasVolumetricProduction","m^3/d",2,languageResourceMap.get("calculate"),"瞬时产气量")));
			
			jedis.zadd(key.getBytes(),48, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidWeightProduction"),"RealtimeLiquidWeightProduction","t/d",2,languageResourceMap.get("calculate"),languageResourceMap.get("liquidWeightProduction"))));
			jedis.zadd(key.getBytes(),49, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilWeightProduction"),"RealtimeOilWeightProduction","t/d",2,languageResourceMap.get("calculate"),languageResourceMap.get("oilWeightProduction"))));
			jedis.zadd(key.getBytes(),50, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterWeightProduction"),"RealtimeWaterWeightProduction","t/d",2,languageResourceMap.get("calculate"),languageResourceMap.get("waterWeightProduction"))));
			
			jedis.zadd(key.getBytes(),51, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpSettingDepth"),"PumpSettingDepth","m",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpSettingDepth"))));
			jedis.zadd(key.getBytes(),52, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("producingfluidLevel"),"ProducingfluidLevel","m",2,languageResourceMap.get("calculate"),languageResourceMap.get("producingfluidLevel"))));
			jedis.zadd(key.getBytes(),53, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("calcProducingfluidLevel"),"CalcProducingfluidLevel","m",2,languageResourceMap.get("calculate"),languageResourceMap.get("calcProducingfluidLevel"))));
			jedis.zadd(key.getBytes(),54, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("submergence"),"Submergence","m",2,languageResourceMap.get("calculate"),languageResourceMap.get("submergence"))));
			
			jedis.zadd(key.getBytes(),55, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("tubingPressure"),"TubingPressure","MPa",2,languageResourceMap.get("calculate"),languageResourceMap.get("tubingPressure"))));
			jedis.zadd(key.getBytes(),56, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("casingPressure"),"CasingPressure","MPa",2,languageResourceMap.get("calculate"),languageResourceMap.get("casingPressure"))));
			jedis.zadd(key.getBytes(),57, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("bottomHolePressure"),"BottomHolePressure","MPa",2,languageResourceMap.get("calculate"),languageResourceMap.get("bottomHolePressure"))));
			
			jedis.zadd(key.getBytes(),58, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("remark"),"Remark","",1,languageResourceMap.get("input"),languageResourceMap.get("remark"))));
			
			jedis.zadd(key.getBytes(),59, SerializeObjectUnils.serialize(new CalItem("备用1","reservedcol1","",1,languageResourceMap.get("input"),"备用1")));
			jedis.zadd(key.getBytes(),60, SerializeObjectUnils.serialize(new CalItem("备用2","reservedcol2","",1,languageResourceMap.get("input"),"备用2")));
			jedis.zadd(key.getBytes(),61, SerializeObjectUnils.serialize(new CalItem("备用3","reservedcol3","",1,languageResourceMap.get("input"),"备用3")));
			jedis.zadd(key.getBytes(),62, SerializeObjectUnils.serialize(new CalItem("备用4","reservedcol4","",1,languageResourceMap.get("input"),"备用4")));
			jedis.zadd(key.getBytes(),63, SerializeObjectUnils.serialize(new CalItem("备用5","reservedcol5","",1,languageResourceMap.get("input"),"备用5")));
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
		String key="pcpTimingTotalCalItemList_"+language;
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
		String key="pcpTimingTotalCalItemList_"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
			}
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(Config.getInstance().configFile.getAp().getOthers().getDeviceShowName(),"DeviceName","",1,languageResourceMap.get("input"),Config.getInstance().configFile.getAp().getOthers().getDeviceShowName())));
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem("时间","CalTime","",4,languageResourceMap.get("calculate"),"时间")));
			
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTime"),"CommTime","h",2,languageResourceMap.get("calculate"),"在线时间,通信计算所得")));
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commTimeEfficiency"),"CommTimeEfficiency",timeEfficiencyUnit,2,languageResourceMap.get("calculate"),"在线时率,通信计算所得")));
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("commRange"),"CommRange","",1,languageResourceMap.get("calculate"),"在线区间,通信计算所得")));
			
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTime"),"RunTime","h",2,languageResourceMap.get("calculate"),"运行时间,时率计算所得")));
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runTimeEfficiency"),"RunTimeEfficiency",timeEfficiencyUnit,2,languageResourceMap.get("calculate"),"运行时率,时率计算所得")));
			jedis.zadd(key.getBytes(),8, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("runRange"),"RunRange","",1,languageResourceMap.get("calculate"),"运行区间,时率计算所得")));
			
			jedis.zadd(key.getBytes(),9, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("RPM"),"RPM","r/min",2,languageResourceMap.get("calculate"),languageResourceMap.get("RPM"))));
			
			jedis.zadd(key.getBytes(),10, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("theoreticalProduction"),"TheoreticalProduction","m^3/d",2,languageResourceMap.get("calculate"),languageResourceMap.get("theoreticalProduction"))));
			
			jedis.zadd(key.getBytes(),11, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidVolumetricProduction_l"),"LiquidVolumetricProduction","m^3/d",2,languageResourceMap.get("calculate"),"日产液量,转速汇总计算所得")));
			jedis.zadd(key.getBytes(),12, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilVolumetricProduction_l"),"OilVolumetricProduction","m^3/d",2,languageResourceMap.get("calculate"),"日产油量,转速汇总计算所得")));
			jedis.zadd(key.getBytes(),13, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterVolumetricProduction_l"),"WaterVolumetricProduction","m^3/d",2,languageResourceMap.get("calculate"),"日产水量,转速汇总计算或者累计产水量计算所得")));
//			jedis.zadd(key.getBytes(),14, SerializeObjectUnils.serialize(new CalItem("日累计产气量","GasVolumetricProduction","m^3/d",2,languageResourceMap.get("calculate"),"日产气量,累计产气量计算所得")));
			jedis.zadd(key.getBytes(),15, SerializeObjectUnils.serialize(new CalItem("体积含水率","VolumeWaterCut","%",2,languageResourceMap.get("calculate"),"体积含水率")));
			
			jedis.zadd(key.getBytes(),16, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidWeightProduction_l"),"LiquidWeightProduction","t/d",2,languageResourceMap.get("calculate"),"日产液量,转速汇总计算所得")));
			jedis.zadd(key.getBytes(),17, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilWeightProduction_l"),"OilWeightProduction","t/d",2,languageResourceMap.get("calculate"),"日产油量,转速汇总计算所得")));
			jedis.zadd(key.getBytes(),18, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterWeightProduction_l"),"WaterWeightProduction","t/d",2,languageResourceMap.get("calculate"),"日产水量,转速汇总计算或者累计产水量计算所得")));
			jedis.zadd(key.getBytes(),19, SerializeObjectUnils.serialize(new CalItem("重量含水率","WeightWaterCut","%",2,languageResourceMap.get("calculate"),"重量含水率")));
			
			jedis.zadd(key.getBytes(),20, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("systemEfficiency"),"SystemEfficiency","",2,languageResourceMap.get("calculate"),languageResourceMap.get("systemEfficiency"))));
			jedis.zadd(key.getBytes(),21, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("energyPer100mLift"),"EnergyPer100mLift","kW· h/100m· t",2,languageResourceMap.get("calculate"),languageResourceMap.get("energyPer100mLift"))));
			
			jedis.zadd(key.getBytes(),22, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff1_pcp"),"PumpEff1","",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpEff1_pcp"))));
			jedis.zadd(key.getBytes(),23, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff2_pcp"),"PumpEff2","",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpEff2_pcp"))));
			jedis.zadd(key.getBytes(),24, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpEff"),"PumpEff","",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpEff"))));
			
//			jedis.zadd(key.getBytes(),25, SerializeObjectUnils.serialize(new CalItem("日用电量","TodayKWattH","kW·h",2,languageResourceMap.get("calculate"),"日用电量,累计用电量计算所得")));
//			jedis.zadd(key.getBytes(),26, SerializeObjectUnils.serialize(new CalItem("累计用电量","TotalKWattH","kW·h",2,languageResourceMap.get("calculate"),"累计用电量,当日最新采集数据")));
//			
//			jedis.zadd(key.getBytes(),27, SerializeObjectUnils.serialize(new CalItem("累计产气量","TotalGasVolumetricProduction","m^3",2,languageResourceMap.get("calculate"),"累计产气量,当日最新采集数据")));
//			jedis.zadd(key.getBytes(),28, SerializeObjectUnils.serialize(new CalItem("累计产水量","TotalWaterVolumetricProduction","m^3",2,languageResourceMap.get("calculate"),"累计产水量,当日最新采集数据")));
			
			jedis.zadd(key.getBytes(),29, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidVolumetricProduction"),"RealtimeLiquidVolumetricProduction","m^3",2,languageResourceMap.get("calculate"),languageResourceMap.get("liquidVolumetricProduction"))));
			jedis.zadd(key.getBytes(),30, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilVolumetricProduction"),"RealtimeOilVolumetricProduction","m^3",2,languageResourceMap.get("calculate"),languageResourceMap.get("oilVolumetricProduction"))));
			jedis.zadd(key.getBytes(),31, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterVolumetricProduction"),"RealtimeWaterVolumetricProduction","m^3",2,languageResourceMap.get("calculate"),languageResourceMap.get("waterVolumetricProduction"))));
//			jedis.zadd(key.getBytes(),32, SerializeObjectUnils.serialize(new CalItem("瞬时产气量","RealtimeGasVolumetricProduction","m^3",2,languageResourceMap.get("calculate"),"瞬时产气量")));
			
			jedis.zadd(key.getBytes(),33, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("liquidWeightProduction"),"RealtimeLiquidWeightProduction","t/d",2,languageResourceMap.get("calculate"),languageResourceMap.get("liquidWeightProduction"))));
			jedis.zadd(key.getBytes(),34, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("oilWeightProduction"),"RealtimeOilWeightProduction","t/d",2,languageResourceMap.get("calculate"),languageResourceMap.get("oilWeightProduction"))));
			jedis.zadd(key.getBytes(),35, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterWeightProduction"),"RealtimeWaterWeightProduction","t/d",2,languageResourceMap.get("calculate"),languageResourceMap.get("waterWeightProduction"))));
			
			jedis.zadd(key.getBytes(),36, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpSettingDepth"),"PumpSettingDepth","m",2,languageResourceMap.get("calculate"),languageResourceMap.get("pumpSettingDepth"))));
			jedis.zadd(key.getBytes(),37, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("producingfluidLevel"),"ProducingfluidLevel","m",2,languageResourceMap.get("calculate"),languageResourceMap.get("producingfluidLevel"))));
			jedis.zadd(key.getBytes(),38, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("submergence"),"Submergence","m",2,languageResourceMap.get("calculate"),languageResourceMap.get("submergence"))));
			
			jedis.zadd(key.getBytes(),39, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("tubingPressure"),"TubingPressure","MPa",2,languageResourceMap.get("calculate"),languageResourceMap.get("tubingPressure"))));
			jedis.zadd(key.getBytes(),40, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("casingPressure"),"CasingPressure","MPa",2,languageResourceMap.get("calculate"),languageResourceMap.get("casingPressure"))));
			jedis.zadd(key.getBytes(),41, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("bottomHolePressure"),"BottomHolePressure","MPa",2,languageResourceMap.get("calculate"),languageResourceMap.get("bottomHolePressure"))));
			
			jedis.zadd(key.getBytes(),42, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("remark"),"Remark","",1,languageResourceMap.get("input"),languageResourceMap.get("remark"))));
			
			jedis.zadd(key.getBytes(),43, SerializeObjectUnils.serialize(new CalItem("备用1","reservedcol1","",1,languageResourceMap.get("input"),"备用1")));
			jedis.zadd(key.getBytes(),44, SerializeObjectUnils.serialize(new CalItem("备用2","reservedcol2","",1,languageResourceMap.get("input"),"备用2")));
			jedis.zadd(key.getBytes(),45, SerializeObjectUnils.serialize(new CalItem("备用3","reservedcol3","",1,languageResourceMap.get("input"),"备用3")));
			jedis.zadd(key.getBytes(),46, SerializeObjectUnils.serialize(new CalItem("备用4","reservedcol4","",1,languageResourceMap.get("input"),"备用4")));
			jedis.zadd(key.getBytes(),47, SerializeObjectUnils.serialize(new CalItem("备用5","reservedcol5","",1,languageResourceMap.get("input"),"备用5")));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static List<CalItem> getRPCInputItem(String language){
		Jedis jedis=null;
		String key="rpcInputItemList_"+language;
		List<CalItem> calItemList=new ArrayList<>();
		if(!existsKey(key)){
			MemoryDataManagerTask.loadRPCInputItem(language);
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
	
	public static void loadRPCInputItem(String language){
		Jedis jedis=null;
		String key="rpcInputItemList_"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("crudeOilDensity"),"CrudeOilDensity","g/cm^3",2,languageResourceMap.get("input"),languageResourceMap.get("crudeOilDensity"))));
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterDensity"),"WaterDensity","g/cm^3",2,languageResourceMap.get("input"),languageResourceMap.get("waterDensity"))));
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("naturalGasRelativeDensity"),"NaturalGasRelativeDensity","",2,languageResourceMap.get("input"),languageResourceMap.get("naturalGasRelativeDensity"))));
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("saturationPressure"),"SaturationPressure","MPa",2,languageResourceMap.get("input"),languageResourceMap.get("saturationPressure"))));
			
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservoirDepth"),"ReservoirDepth","m",2,languageResourceMap.get("input"),languageResourceMap.get("reservoirDepth"))));
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservoirTemperature"),"ReservoirTemperature","℃",2,languageResourceMap.get("input"),languageResourceMap.get("reservoirTemperature"))));
			
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("tubingPressure"),"TubingPressure","MPa",2,languageResourceMap.get("input"),languageResourceMap.get("tubingPressure"))));
			jedis.zadd(key.getBytes(),8, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("casingPressure"),"CasingPressure","MPa",2,languageResourceMap.get("input"),languageResourceMap.get("casingPressure"))));
			jedis.zadd(key.getBytes(),9, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("wellHeadTemperature"),"WellHeadTemperature","℃",2,languageResourceMap.get("input"),languageResourceMap.get("wellHeadTemperature"))));
			jedis.zadd(key.getBytes(),10, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterCut"),"WaterCut","%",2,languageResourceMap.get("input"),languageResourceMap.get("waterCut"))));
			jedis.zadd(key.getBytes(),11, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("productionGasOilRatio"),"ProductionGasOilRatio","m^3/t",2,languageResourceMap.get("input"),languageResourceMap.get("productionGasOilRatio"))));
			
			jedis.zadd(key.getBytes(),12, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("producingfluidLevel"),"ProducingfluidLevel","m",2,languageResourceMap.get("input"),languageResourceMap.get("producingfluidLevel"))));
			jedis.zadd(key.getBytes(),13, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpSettingDepth"),"PumpSettingDepth","m",2,languageResourceMap.get("input"),languageResourceMap.get("pumpSettingDepth"))));
			
			jedis.zadd(key.getBytes(),14, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpBoreDiameter"),"PumpBoreDiameter","mm",2,languageResourceMap.get("input"),languageResourceMap.get("pumpBoreDiameter"))));
			
			jedis.zadd(key.getBytes(),15, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("levelCorrectValue"),"LevelCorrectValue","MPa",2,languageResourceMap.get("input"),languageResourceMap.get("levelCorrectValue"))));
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
		String key="pcpInputItemList"+language;
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
		String key="pcpInputItemList"+language;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			jedis = RedisUtil.jedisPool.getResource();
			//有序集合
			jedis.zadd(key.getBytes(),1, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("crudeOilDensity"),"CrudeOilDensity","g/cm^3",2,languageResourceMap.get("input"),languageResourceMap.get("crudeOilDensity"))));
			jedis.zadd(key.getBytes(),2, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterDensity"),"WaterDensity","g/cm^3",2,languageResourceMap.get("input"),languageResourceMap.get("waterDensity"))));
			jedis.zadd(key.getBytes(),3, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("naturalGasRelativeDensity"),"NaturalGasRelativeDensity","",2,languageResourceMap.get("input"),languageResourceMap.get("naturalGasRelativeDensity"))));
			jedis.zadd(key.getBytes(),4, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("saturationPressure"),"SaturationPressure","MPa",2,languageResourceMap.get("input"),languageResourceMap.get("saturationPressure"))));
			
			jedis.zadd(key.getBytes(),5, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservoirDepth"),"ReservoirDepth","m",2,languageResourceMap.get("input"),languageResourceMap.get("reservoirDepth"))));
			jedis.zadd(key.getBytes(),6, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("reservoirTemperature"),"ReservoirTemperature","℃",2,languageResourceMap.get("input"),languageResourceMap.get("reservoirTemperature"))));
			
			jedis.zadd(key.getBytes(),7, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("tubingPressure"),"TubingPressure","MPa",2,languageResourceMap.get("input"),languageResourceMap.get("tubingPressure"))));
			jedis.zadd(key.getBytes(),8, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("casingPressure"),"CasingPressure","MPa",2,languageResourceMap.get("input"),languageResourceMap.get("casingPressure"))));
			jedis.zadd(key.getBytes(),9, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("wellHeadTemperature"),"WellHeadTemperature","℃",2,languageResourceMap.get("input"),languageResourceMap.get("wellHeadTemperature"))));
			jedis.zadd(key.getBytes(),10, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("waterCut"),"WaterCut","%",2,languageResourceMap.get("input"),languageResourceMap.get("waterCut"))));
			jedis.zadd(key.getBytes(),11, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("productionGasOilRatio"),"ProductionGasOilRatio","m^3/t",2,languageResourceMap.get("input"),languageResourceMap.get("productionGasOilRatio"))));
			
			jedis.zadd(key.getBytes(),12, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("producingfluidLevel"),"ProducingfluidLevel","m",2,languageResourceMap.get("input"),languageResourceMap.get("producingfluidLevel"))));
			jedis.zadd(key.getBytes(),13, SerializeObjectUnils.serialize(new CalItem(languageResourceMap.get("pumpSettingDepth"),"PumpSettingDepth","m",2,languageResourceMap.get("input"),languageResourceMap.get("pumpSettingDepth"))));
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
					+ " t.user_language,c.itemname as languageName"
					+ " from tbl_user t,tbl_role t2,tbl_code c "
					+ " where t.user_type=t2.role_id "
					+ " and upper(c.itemCode)='LANGUAGE' and t.user_language=c.itemValue     ";
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
				userInfo.setLanguageName(obj[16]+"");
				
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
	
	public static void loadRPCWorkType(){
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			String sql="select t.id,t.resultcode,t.resultname,t.resultdescription,t.optimizationsuggestion,t.remark "
					+ " from TBL_RPC_WORKTYPE t order by t.resultcode";
			List<Object[]> list=OracleJdbcUtis.query(sql);
			for(Object[] obj:list){
				WorkType workType=new WorkType();
				workType.setId(StringManagerUtils.stringToInteger(obj[0]+""));
				workType.setResultCode(StringManagerUtils.stringToInteger(obj[1]+""));
				workType.setResultName(obj[2]+"");
				workType.setResultDescription(obj[3]+"");
				workType.setOptimizationSuggestion(obj[4]+"");
				workType.setRemark(obj[5]+"");
				String key=workType.getResultCode()+"";
				String keyByName=workType.getResultName()+"";
				
				jedis.hset("RPCWorkType".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(workType));//哈希(Hash)
				jedis.hset("RPCWorkTypeByName".getBytes(), keyByName.getBytes(), SerializeObjectUnils.serialize(workType));//哈希(Hash)
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public static WorkType getWorkTypeByCode(String resultCode){
		WorkType workType=null;
		Jedis jedis=null;
		if(!existsKey("RPCWorkType")){
			MemoryDataManagerTask.loadRPCWorkType();
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis.hexists("RPCWorkType".getBytes(), (resultCode).getBytes())){
				workType=(WorkType) SerializeObjectUnils.unserizlize(jedis.hget("RPCWorkType".getBytes(), (resultCode).getBytes()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return workType;
	}
	
	public static int getResultCodeByName(String resultName){
		int resultCode=0;
		Jedis jedis=null;
		if(!existsKey("RPCWorkTypeByName")){
			MemoryDataManagerTask.loadRPCWorkType();
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis.hexists("RPCWorkTypeByName".getBytes(), (resultName).getBytes())){
				WorkType workType=(WorkType) SerializeObjectUnils.unserizlize(jedis.hget("RPCWorkTypeByName".getBytes(), (resultName).getBytes()));
				resultCode=workType.getResultCode();
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return resultCode;
	}
	
	public static WorkType getWorkTypeByName(String resultName){
		WorkType workType=null;
		Jedis jedis=null;
		if(!existsKey("RPCWorkTypeByName")){
			MemoryDataManagerTask.loadRPCWorkType();
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis.hexists("RPCWorkTypeByName".getBytes(), (resultName).getBytes())){
				workType=(WorkType) SerializeObjectUnils.unserizlize(jedis.hget("RPCWorkTypeByName".getBytes(), (resultName).getBytes()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return workType;
	}
	
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
					+ " (select * from tbl_code t where t.itemcode='BJYS' ) v1,"
					+ " (select * from tbl_code t where t.itemcode='BJQJYS' ) v2,"
					+ " (select * from tbl_code t where t.itemcode='BJYSTMD' ) v3 "
					+ " where v1.itemvalue=v2.itemvalue and v1.itemvalue=v3.itemvalue "
					+ " order by v1.itemvalue ";
			String commAlarmSql="select v1.itemvalue as alarmLevel,v1.itemname as backgroundColor,v2.itemname as color,v3.itemname as opacity from "
					+ " (select * from tbl_code t where t.itemcode='TXBJYS' ) v1,"
					+ " (select * from tbl_code t where t.itemcode='TXBJQJYS' ) v2,"
					+ " (select * from tbl_code t where t.itemcode='TXBJYSTMD' ) v3 "
					+ " where v1.itemvalue=v2.itemvalue and v1.itemvalue=v3.itemvalue "
					+ " order by v1.itemvalue ";
			String runAlarmSql="select v1.itemvalue as alarmLevel,v1.itemname as backgroundColor,v2.itemname as color,v3.itemname as opacity from "
					+ " (select * from tbl_code t where t.itemcode='YXBJYS' ) v1,"
					+ " (select * from tbl_code t where t.itemcode='YXBJQJYS' ) v2,"
					+ " (select * from tbl_code t where t.itemcode='YXBJYSTMD' ) v3 "
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
	
	public static RPCDeviceTodayData getRPCDeviceTodayDataById(int deviceId){
		RPCDeviceTodayData deviceTodayData=null;
		Jedis jedis=null;
		String key=deviceId+"";
		if(!existsKey("RPCDeviceTodayData")){
			MemoryDataManagerTask.loadTodayFESDiagram(null,0);
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis.hexists("RPCDeviceTodayData".getBytes(), key.getBytes())){
				deviceTodayData=(RPCDeviceTodayData) SerializeObjectUnils.unserizlize(jedis.hget("RPCDeviceTodayData".getBytes(), key.getBytes()));
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
	
	public static void updateRPCDeviceTodayDataDeviceInfo(RPCDeviceTodayData deviceTodayData){
		Jedis jedis=null;
		if(!existsKey("RPCDeviceTodayData")){
			MemoryDataManagerTask.loadTodayFESDiagram(null,0);
		}
		try {
			jedis = RedisUtil.jedisPool.getResource();
			jedis.hset("RPCDeviceTodayData".getBytes(), (deviceTodayData.getId()+"").getBytes(), SerializeObjectUnils.serialize(deviceTodayData));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
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
					+ " from tbl_rpcacqdata_hist t,tbl_device t2 "
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
				RPCCalculateResponseData responseData =new RPCCalculateResponseData(); 
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
					type = new TypeToken<RPCDeviceInfo>() {}.getType();
					RPCDeviceInfo rpcProductionData=gson.fromJson(productionData, type);
					if(rpcProductionData!=null){
						responseData.getProduction().setWaterCut(rpcProductionData.getProduction().getWaterCut());
						responseData.getProduction().setWeightWaterCut(rpcProductionData.getProduction().getWeightWaterCut());
						responseData.getProduction().setTubingPressure(rpcProductionData.getProduction().getTubingPressure());
						responseData.getProduction().setCasingPressure(rpcProductionData.getProduction().getCasingPressure());
						responseData.getProduction().setPumpSettingDepth(rpcProductionData.getProduction().getPumpSettingDepth());
						responseData.getProduction().setProducingfluidLevel(rpcProductionData.getProduction().getProducingfluidLevel());
					}
				}
				
				responseData.getProduction().setCalcProducingfluidLevel(rs.getFloat(30));
				responseData.getProduction().setLevelDifferenceValue(rs.getFloat(31));
				responseData.getProduction().setSubmergence(rs.getFloat(32));
				
				if(jedis.hexists("RPCDeviceTodayData".getBytes(), key.getBytes())){
					RPCDeviceTodayData deviceTodayData =(RPCDeviceTodayData) SerializeObjectUnils.unserizlize(jedis.hget("RPCDeviceTodayData".getBytes(), key.getBytes()));
					deviceTodayData.getRPCCalculateList().add(responseData);
					jedis.hset("RPCDeviceTodayData".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(deviceTodayData));
				}else{
					RPCDeviceTodayData deviceTodayData=new RPCDeviceTodayData();
					deviceTodayData.setId(deviceId);
					deviceTodayData.setRPCCalculateList(new ArrayList<RPCCalculateResponseData>());
					deviceTodayData.setAcquisitionItemInfoList(new ArrayList<AcquisitionItemInfo>());
					deviceTodayData.getRPCCalculateList().add(responseData);
					jedis.hset("RPCDeviceTodayData".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(deviceTodayData));
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
		public String dataSource;
		public int dataType;
		public String remark;
		
		public CalItem(String name,String code, String unit, int dataType,String dataSource,String remark) {
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
		public String getDataSource() {
			return dataSource;
		}
		public void setDataSource(String dataSource) {
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
			loadLanguageResource(key);
		}
		if(dataModelMap.containsKey(key)){
			languageMap=(Map<String, String>) dataModelMap.get(key);
		}
		return languageMap;
	}
	
	public static void loadLanguageResource(){
		loadLanguageResource("zh_CN");
		loadLanguageResource("en");
		loadLanguageResource("ru");
	}
	
	public static void loadLanguageResource(String language){
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		Map<String,String> languageMap=new LinkedHashMap<>();
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
			    JSONArray arr = entry.getValue();
			    for(int i=0;i<arr.size();i++){
					JSONObject obj=arr.getJSONObject(i);
					String item=obj.getString("ITEM");
					String value=obj.getString("VALUE");
					if(StringManagerUtils.isNotNull(item)){
						languageMap.put(item, value);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dataModelMap.put("languageResource-"+language, languageMap);
		
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
		List<CalculateColumn> rpcCalculateColumnList=new ArrayList<CalculateColumn>();
		List<CalculateColumn> pcpCalculateColumnList=new ArrayList<CalculateColumn>();
		calculateColumnInfo=new CalculateColumnInfo();
		calculateColumnInfo.setRPCCalculateColumnList(rpcCalculateColumnList);
		calculateColumnInfo.setPCPCalculateColumnList(pcpCalculateColumnList);
		
		//抽油机井
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("运行状态","RunStatus",1) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("A相电流","IA",2) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("B相电流","IB",3) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("C相电流","IC",4) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("A相电压","VA",5) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("B相电压","VB",6) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("C相电压","VC",7) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("有功功耗","TotalKWattH",8) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("无功功耗","TotalKVarH",9) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("averageWatt"),"Watt3",10) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("无功功率","Var3",11) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("功率因数","PF3",12) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("变频设置频率","SetFrequency",13) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("变频运行频率","RunFrequency",14) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("tubingPressure"),"TubingPressure",15) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("casingPressure"),"CasingPressure",16) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("wellHeadTemperature"),"WellHeadTemperature",17) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("bottomHolePressure"),"BottomHolePressure",18) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("井底温度","BottomHoleTemperature",19) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("producingfluidLevel"),"ProducingfluidLevel",20) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("waterCut"),"VolumeWaterCut",21) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("功图实测点数","FESDiagramAcqCount",22) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("功图采集时间","FESDiagramAcqtime",23) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("SPM"),"SPM",24) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("stroke"),"Stroke",25) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("功图数据-位移","Position_Curve",26) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("功图数据-载荷","Load_Curve",27) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("功图数据-电流","Current_Curve",28) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("功图数据-功率","Power_Curve",29) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("螺杆泵转速","RPM",30) );
		
//		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("累计产气量","TotalGasVolumetricProduction",31) );
//		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("累计产水量","TotalWaterVolumetricProduction",32) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("瞬时产液量（方）","RealtimeLiquidVolumetricProduction",33) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("瞬时产油量（方）","RealtimeOilVolumetricProduction",34) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("瞬时产水量（方）","RealtimeWaterVolumetricProduction",35) );
//		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("瞬时产气量（方）","RealtimeGasVolumetricProduction",36) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("瞬时产液量（吨）","RealtimeLiquidWeightProduction",37) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("瞬时产油量（吨）","RealtimeOilWeightProduction",38) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("瞬时产水量（吨）","RealtimeWaterWeightProduction",39) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("功图工况","ResultCode",40) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("fMax"),"FMax",41) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("fMin"),"FMin",42) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("功图充满系数","FullnessCoefficient",43) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("upperLoadLine"),"UpperLoadLine",44) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("lowerLoadLine"),"LowerLoadLine",45) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("theoreticalProduction"),"TheoreticalProduction",46) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("日产液量（方）","LiquidVolumetricProduction",47) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("日产油量（方）","OilVolumetricProduction",48) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("日产水量（方）","WaterVolumetricProduction",49) );
//		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("日产气量（方）","GasVolumetricProduction",50) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("柱塞有效冲程计算产量（方）","AvailablePlungerStrokeProd_v",51) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("泵间隙漏失量（方）","PumpClearanceLeakProd_v",52) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("游动凡尔漏失量（方）","TVLeakVolumetricProduction",53) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("固定凡尔漏失量（方）","SVLeakVolumetricProduction",54) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("气影响（方）","GasInfluenceProd_v",55) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("日产液量（吨）","LiquidWeightProduction",56) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("日产油量（吨）","OilWeightProduction",57) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("日产水量（吨）","WaterWeightProduction",58) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("柱塞有效冲程计算产量（吨）","AvailablePlungerStrokeProd_w",59) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("泵间隙漏失量（吨）","PumpClearanceLeakProd_w",60) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("游动凡尔漏失量（吨）","TVLeakWeightProduction",61) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("固定凡尔漏失量（吨）","SVLeakWeightProduction",62) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("气影响（吨）","GasInfluenceProd_w",63) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("平均总有功功率","AverageWatt",64) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("polishRodPower"),"PolishRodPower",65) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("waterPower"),"WaterPower",66) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("surfaceSystemEfficiency"),"SurfaceSystemEfficiency",67) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("wellDownSystemEfficiency"),"WellDownSystemEfficiency",68) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("systemEfficiency"),"SystemEfficiency",69) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("energyPer100mLift"),"EnergyPer100mLift",70) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("area"),"Area",71) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("rodFlexLength"),"RodFlexLength",72) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("tubingFlexLength"),"TubingFlexLength",73) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("inertiaLength"),"InertiaLength",74) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("pumpEff1"),"PumpEff1",75) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("pumpEff2"),"PumpEff2",76) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("pumpEff3"),"PumpEff3",77) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("pumpEff4"),"PumpEff4",78) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("pumpEff1_pcp"),"PumpEff1",79) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("pumpEff"),"PumpEff",80) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("calcProducingfluidLevel"),"CalcProducingfluidLevel",81) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("levelDifferenceValue"),"LevelDifferenceValue",82) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("上冲程功率最大值","UpStrokeWattMax",83) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("下冲程功率最大值","DownStrokeWattMax",84) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("wattDegreeBalance"),"WattDegreeBalance",85) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("上冲程电流最大值","UpStrokeIMax",86) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("下冲程电流最大值","DownStrokeIMax",87) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("iDegreeBalance"),"IDegreeBalance",88) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("deltaRadius"),"DeltaRadius",89) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("submergence"),"Submergence",90) );
		
		//螺杆泵井
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("运行状态","RunStatus",1) );
		
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("A相电流","IA",2) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("B相电流","IB",3) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("C相电流","IC",4) );
		
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("A相电压","VA",5) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("B相电压","VB",6) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("C相电压","VC",7) );
		
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("有功功耗","TotalKWattH",8) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("无功功耗","TotalKVarH",9) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("averageWatt"),"Watt3",10) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("无功功率","Var3",11) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("功率因数","PF3",12) );
		
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("变频设置频率","SetFrequency",13) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("变频运行频率","RunFrequency",14) );
		
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("tubingPressure"),"TubingPressure",15) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("casingPressure"),"CasingPressure",16) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("wellHeadTemperature"),"WellHeadTemperature",17) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("bottomHolePressure"),"BottomHolePressure",18) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("井底温度","BottomHoleTemperature",19) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("producingfluidLevel"),"ProducingfluidLevel",20) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn(languageResourceMap.get("waterCut"),"VolumeWaterCut",21) );
		
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("螺杆泵转速","RPM",22) );
		
//		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("累计产气量","TotalGasVolumetricProduction",23) );
//		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("累计产水量","TotalWaterVolumetricProduction",24) );
		
		
		
		return calculateColumnInfo;
	}
	
	public static String getCalculateColumnFromName(int deviceType,String name,String language){
		if(!StringManagerUtils.isNotNull(name)){
			return "";
		}
		CalculateColumnInfo calculateColumnInfo=getCalColumnsInfo(language);
		List<CalculateColumn> calculateColumnList=calculateColumnInfo.getRPCCalculateColumnList();
		String code="";
		if(deviceType!=0){
			calculateColumnList=calculateColumnInfo.getPCPCalculateColumnList();
		}
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
		List<CalculateColumn> calculateColumnList=calculateColumnInfo.getRPCCalculateColumnList();
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
