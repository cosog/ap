package com.cosog.task;

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

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.cosog.model.AccessToken;
import com.cosog.model.AlarmShowStyle;
import com.cosog.model.AuxiliaryDeviceAddInfo;
import com.cosog.model.DataMapping;
import com.cosog.model.DataSourceConfig;
import com.cosog.model.DataWriteBackConfig;
import com.cosog.model.ProtocolRunStatusConfig;
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
		cleanData();
		
		loadProtocolConfig("");
		
		loadProtocolRunStatusConfig();
		
		loadReportTemplateConfig();
		
		MemoryDataManagerTask.loadPCPCalculateItem();
		MemoryDataManagerTask.loadPCPInputItem();
		MemoryDataManagerTask.loadPCPTotalCalculateItem();
		MemoryDataManagerTask.loadPCPTimingTotalCalculateItem();
		
		MemoryDataManagerTask.loadRPCCalculateItem();
		MemoryDataManagerTask.loadRPCInputItem();
		MemoryDataManagerTask.loadRPCTotalCalculateItem();
		MemoryDataManagerTask.loadRPCTimingTotalCalculateItem();
		
		loadAcqInstanceOwnItemById("","update");
		loadAlarmInstanceOwnItemById("","update");
		loadDisplayInstanceOwnItemById("","update");
		
		loadDeviceInfo(null,0,"update");
		
		loadDeviceRealtimeAcqData(null);
		
		loadTodayFESDiagram(null,0);
		loadTodayRPMData(null,0);
	}
	
	public static void cleanData(){
		Jedis jedis=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			jedis.del("modbusProtocolConfig".getBytes());
			jedis.del("ProtocolMappingColumn".getBytes());
			jedis.del("ProtocolMappingColumnByTitle".getBytes());
			jedis.del("CalculateColumnInfo".getBytes());
			jedis.del("ProtocolRunStatusConfig".getBytes());
			jedis.del("DeviceInfo".getBytes());
			jedis.del("RPCDeviceTodayData".getBytes());
			jedis.del("PCPDeviceInfo".getBytes());
			jedis.del("PCPDeviceTodayData".getBytes());
			jedis.del("AcqInstanceOwnItem".getBytes());
			jedis.del("DisplayInstanceOwnItem".getBytes());
			jedis.del("AlarmInstanceOwnItem".getBytes());
			jedis.del("rpcCalItemList".getBytes());
			jedis.del("pcpCalItemList".getBytes());
			jedis.del("acqTotalCalItemList".getBytes());
			jedis.del("rpcTotalCalItemList".getBytes());
			jedis.del("pcpTotalCalItemList".getBytes());
			jedis.del("acqTimingTotalCalItemList".getBytes());
			jedis.del("rpcTimingTotalCalItemList".getBytes());
			jedis.del("pcpTimingTotalCalItemList".getBytes());
			jedis.del("rpcInputItemList".getBytes());
			jedis.del("pcpInputItemList".getBytes());
			jedis.del("UserInfo".getBytes());
			jedis.del("RPCWorkType".getBytes());
			jedis.del("RPCWorkTypeByName".getBytes());
			jedis.del("AlarmShowStyle".getBytes());
			jedis.del("UIKitAccessToken".getBytes());
			jedis.del("ReportTemplateConfig".getBytes());
			jedis.del("DeviceRealtimeAcqData".getBytes());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null && jedis.isConnected() ){
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
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		
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
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		
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
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				try {
					String itemsStr=StringManagerUtils.CLOBtoString2(rs.getClob(4));
					if(!StringManagerUtils.isNotNull(itemsStr)){
						itemsStr="[]";
					}
					protocolBuff=new StringBuffer();
					protocolBuff.append("{");
					protocolBuff.append("\"Id\":\""+rs.getInt(1)+"\",");
					protocolBuff.append("\"Name\":\""+rs.getString(2)+"\",");
					protocolBuff.append("\"Code\":\""+rs.getString(3)+"\",");
					protocolBuff.append("\"Sort\":"+rs.getInt(5)+",");
					protocolBuff.append("\"DeviceType\":"+rs.getInt(6)+",");
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
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		
		if(modbusProtocolConfig!=null&&modbusProtocolConfig.getProtocol()!=null&&modbusProtocolConfig.getProtocol().size()>0){
			Collections.sort(modbusProtocolConfig.getProtocol());
		}
		
		try {
			jedis = RedisUtil.jedisPool.getResource();
			jedis.set("modbusProtocolConfig".getBytes(), SerializeObjectUnils.serialize(modbusProtocolConfig));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		
		StringManagerUtils.printLog("驱动加载结束");
	}
	
	public static List<String> getAcqTableColumn(String tableName){
		List<String> tableColumnList=new ArrayList<>();
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		String sql="select t.COLUMN_NAME from user_tab_cols t "
				+ " where t.TABLE_NAME=UPPER('"+tableName+"') "
				+ " and  UPPER(t.COLUMN_NAME) like 'C\\_%'escape '\\'"
				+ " order by t.COLUMN_ID";
		try {
			conn=OracleJdbcUtis.getConnection();
			if(conn!=null){
	        	pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					String columnName=rs.getString(1);
					tableColumnList.add(columnName);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return tableColumnList;
	}
	
	public static int loadAcqTableColumn(){
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		List<String> tableColumnList=new ArrayList<>();
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		int result=0;
		String sql="select t.COLUMN_NAME from user_tab_cols t "
				+ " where t.TABLE_NAME=UPPER('TBL_ACQDATA_LATEST') "
				+ " and  UPPER(t.COLUMN_NAME) like 'C\\_%'escape '\\'"
				+ " order by t.COLUMN_ID";
		try {
			conn=OracleJdbcUtis.getConnection();
			if(conn==null){
				result= -1;
	        }else{
	        	pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					String columnName=rs.getString(1);
					tableColumnList.add(columnName);
				}
	        }
		} catch (SQLException e) {
			result= -1;
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		dataModelMap.put("acqTableColumnList", tableColumnList);
		return result;
	}
	
	public static List<String> getAcqTableColumn(){
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		List<String> tableColumnList=new ArrayList<>();
		if(!dataModelMap.containsKey("acqTableColumnList") || dataModelMap.get("acqTableColumnList")==null){
			loadAcqTableColumn();
		}
		tableColumnList=(List<String>) dataModelMap.get("acqTableColumnList");
		return tableColumnList;
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
	
	public static void loadProtocolMappingColumn(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		
		Jedis jedis=null;
		try {
			Map<String, Object> dataModelMap=DataModelMap.getMapObject();
			Map<String,DataMapping> loadProtocolMappingColumnMap=new LinkedHashMap<String,DataMapping>();
			
			String sql="select t.id,t.name,t.mappingcolumn,t.calcolumn,t.protocoltype,t.mappingmode,t.repetitiontimes from TBL_DATAMAPPING t order by t.protocoltype,t.id";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				DataMapping dataMapping=new DataMapping();
				dataMapping.setId(rs.getInt(1));
				dataMapping.setName(rs.getString(2));
				dataMapping.setMappingColumn(rs.getString(3));
				dataMapping.setCalColumn(rs.getString(4));
				dataMapping.setProtocolType(rs.getInt(5));
				dataMapping.setMappingMode(rs.getInt(6));
				dataMapping.setRepetitionTimes(rs.getInt(7));
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
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadProtocolMappingColumnByTitle(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		Jedis jedis=null;
		try {
			Map<String, Object> dataModelMap=DataModelMap.getMapObject();
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=new LinkedHashMap<String,DataMapping>();
			
			String sql="select t.id,t.name,t.mappingcolumn,t.calcolumn,t.protocoltype,t.mappingmode,t.repetitiontimes from TBL_DATAMAPPING t order by t.protocoltype,t.id";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				DataMapping dataMapping=new DataMapping();
				dataMapping.setId(rs.getInt(1));
				dataMapping.setName(rs.getString(2));
				dataMapping.setMappingColumn(rs.getString(3));
				dataMapping.setCalColumn(rs.getString(4));
				dataMapping.setProtocolType(rs.getInt(5));
				dataMapping.setMappingMode(rs.getInt(6));
				dataMapping.setRepetitionTimes(rs.getInt(7));
				loadProtocolMappingColumnByTitleMap.put(dataMapping.getName(), dataMapping);
			}
			dataModelMap.put("ProtocolMappingColumnByTitle", loadProtocolMappingColumnByTitleMap);
			
			jedis = RedisUtil.jedisPool.getResource();
			if(jedis!=null){
				jedis.del("ProtocolMappingColumn".getBytes());
				for (Map.Entry<String, DataMapping> entry : loadProtocolMappingColumnByTitleMap.entrySet()) {
				    String key = entry.getKey();
				    DataMapping dataMapping = entry.getValue();
				    jedis.hset("ProtocolMappingColumnByTitle".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(dataMapping));//哈希(Hash)
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static ProtocolRunStatusConfig getProtocolRunStatusConfig(String key){
		ProtocolRunStatusConfig protocolRunStatusConfig=null;
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("ProtocolRunStatusConfig".getBytes())){
				MemoryDataManagerTask.loadProtocolRunStatusConfig();
			}
			if(jedis.hget("ProtocolRunStatusConfig".getBytes(), key.getBytes())!=null){
				protocolRunStatusConfig=(ProtocolRunStatusConfig)SerializeObjectUnils.unserizlize(jedis.hget("ProtocolRunStatusConfig".getBytes(), key.getBytes()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		return protocolRunStatusConfig;
	}
	
	public static void loadProtocolRunStatusConfig(){
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
			jedis.del("ProtocolRunStatusConfig".getBytes());
			String sql="select t.id,t.protocol,t.itemname,t.itemmappingcolumn,"
					+ " t.resolutionmode,t.runvalue,t.stopvalue,t.runcondition,t.stopcondition,"
					+ " t.protocoltype "
					+ " from tbl_runstatusconfig t order by t.protocoltype,t.id";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				ProtocolRunStatusConfig protocolRunStatusConfig=new ProtocolRunStatusConfig();
				protocolRunStatusConfig.setId(rs.getInt(1));
				protocolRunStatusConfig.setProtocol(rs.getString(2));
				protocolRunStatusConfig.setItemName(rs.getString(3));
				protocolRunStatusConfig.setItemMappingColumn(rs.getString(4));
				protocolRunStatusConfig.setResolutionMode(rs.getInt(5));
				
				String runValueStr=rs.getString(6);
				String stopValueStr=rs.getString(7);
				
				String runConditionStr=rs.getString(8);
				String stopConditionStr=rs.getString(9);
				
				protocolRunStatusConfig.setProtocolType(rs.getInt(10));
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
				
				String key=protocolRunStatusConfig.getProtocol()+"_"+protocolRunStatusConfig.getItemName();
				jedis.hset("ProtocolRunStatusConfig".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(protocolRunStatusConfig));//哈希(Hash)
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
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
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static DeviceInfo getDeviceInfo(String deviceId){
		Jedis jedis=null;
		DeviceInfo deviceInfo=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("DeviceInfo".getBytes())){
				MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
			}
			deviceInfo=(DeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("DeviceInfo".getBytes(), deviceId.getBytes()));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		return deviceInfo;
	}
	
	public static List<DeviceInfo> getDeviceInfo(){
		Jedis jedis=null;
		List<DeviceInfo> list=new ArrayList<>();
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("DeviceInfo".getBytes())){
				MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
			}
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
			if(jedis!=null&&jedis.isConnected()){
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
						
						jedis.hdel("DeviceRealtimeAcqData".getBytes(), wellList.get(i).getBytes());
					}
				}else if(condition==1){
					for(int i=0;i<wellList.size();i++){
						if(jedis.exists("DeviceInfo".getBytes())){
							List<byte[]> deviceInfoByteList =jedis.hvals("DeviceInfo".getBytes());
							for(int j=0;j<deviceInfoByteList.size();j++){
								DeviceInfo deviceInfo=(DeviceInfo)SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
								if(wellList.get(i).equalsIgnoreCase(deviceInfo.getWellName())){
									jedis.hdel("DeviceInfo".getBytes(), (deviceInfo.getId()+"").getBytes());
									jedis.hdel("RPCDeviceTodayData".getBytes(), (deviceInfo.getId()+"").getBytes());
									jedis.hdel("PCPDeviceTodayData".getBytes(), (deviceInfo.getId()+"").getBytes());
									jedis.hdel("DeviceRealtimeAcqData".getBytes(), (deviceInfo.getId()+"").getBytes());
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
					auxiliaryDeviceAddInfo.setMasterId(StringManagerUtils.stringTransferInteger(obj[0]+""));
					auxiliaryDeviceAddInfo.setDeviceId(StringManagerUtils.stringTransferInteger(obj[1]+""));
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
					deviceInfo.setWellName(rs.getString(4));
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
									if("旋转方向".equalsIgnoreCase(thisAuxiliaryDeviceAddInfoList.get(i).getItemName())){
										deviceInfo.getRpcCalculateRequestData().getPumpingUnit().setCrankRotationDirection(thisAuxiliaryDeviceAddInfoList.get(i).getItemValue());
									}else if("曲柄偏置角".equalsIgnoreCase(thisAuxiliaryDeviceAddInfoList.get(i).getItemName())){
										deviceInfo.getRpcCalculateRequestData().getPumpingUnit().setOffsetAngleOfCrank(StringManagerUtils.stringToFloat(thisAuxiliaryDeviceAddInfoList.get(i).getItemValue()));
									}else if("曲柄重心半径".equalsIgnoreCase(thisAuxiliaryDeviceAddInfoList.get(i).getItemName())){
										deviceInfo.getRpcCalculateRequestData().getPumpingUnit().setCrankGravityRadius(StringManagerUtils.stringToFloat(thisAuxiliaryDeviceAddInfoList.get(i).getItemValue()));
									}else if("单块曲柄重量".equalsIgnoreCase(thisAuxiliaryDeviceAddInfoList.get(i).getItemName())){
										deviceInfo.getRpcCalculateRequestData().getPumpingUnit().setSingleCrankWeight(StringManagerUtils.stringToFloat(thisAuxiliaryDeviceAddInfoList.get(i).getItemValue()));
									}else if("单块曲柄销重量".equalsIgnoreCase(thisAuxiliaryDeviceAddInfoList.get(i).getItemName())){
										deviceInfo.getRpcCalculateRequestData().getPumpingUnit().setSingleCrankPinWeight(StringManagerUtils.stringToFloat(thisAuxiliaryDeviceAddInfoList.get(i).getItemValue()));
									}else if("结构不平衡重".equalsIgnoreCase(thisAuxiliaryDeviceAddInfoList.get(i).getItemName())){
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
						if(deviceInfo.getId()==StringManagerUtils.stringTransferInteger(obj[1]+"")){
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
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static void updateDeviceRealtimeAcqData(String deviceId,Map<String,Map<String,String>> realtimeDataTimeMap){
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			jedis.hset("DeviceRealtimeAcqData".getBytes(), deviceId.getBytes(), SerializeObjectUnils.serialize(realtimeDataTimeMap));
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static Map<String,Map<String,String>> getDeviceRealtimeAcqDataById(String deviceId){
		Jedis jedis=null;
		Map<String,Map<String,String>> realtimeDataTimeMap=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			String key=deviceId;
			if(!jedis.hexists("DeviceRealtimeAcqData".getBytes(),key.getBytes())){
				List<String> deviceIdList=new ArrayList<>();
				deviceIdList.add(deviceId);
				loadDeviceRealtimeAcqData(deviceIdList);
			}
			if(jedis.hexists("DeviceRealtimeAcqData".getBytes(),key.getBytes())){
				realtimeDataTimeMap =(Map<String,Map<String,String>>) SerializeObjectUnils.unserizlize(jedis.hget("DeviceRealtimeAcqData".getBytes(), key.getBytes()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		return realtimeDataTimeMap;
	}
	
	public static void loadDeviceRealtimeAcqData(List<String> deviceIdList){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Jedis jedis=null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		try {
			conn=OracleJdbcUtis.getConnection();
			if(conn==null){
	        	return;
	        }
			jedis = RedisUtil.jedisPool.getResource();
			
			if(!jedis.exists("rpcCalItemList".getBytes())){
				MemoryDataManagerTask.loadRPCCalculateItem();
			}
			if(!jedis.exists("pcpCalItemList".getBytes())){
				MemoryDataManagerTask.loadPCPCalculateItem();
			}
			
			
			List<byte[]> rpcCalItemSet= jedis.zrange("rpcCalItemList".getBytes(), 0, -1);
			List<CalItem> rpcCalItemList=new ArrayList<>();
			for(byte[] rpcCalItemByteArr:rpcCalItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(rpcCalItemByteArr);
				if(calItem.getDataType()==2){
					if(!"TodayKWattH".equalsIgnoreCase(calItem.getCode())){
						rpcCalItemList.add(calItem);
					}
				}
			}
			
			List<byte[]> pcpCalItemSet= jedis.zrange("pcpCalItemList".getBytes(), 0, -1);
			List<CalItem> pcpCalItemList=new ArrayList<>();
			for(byte[] pcpCalItemByteArr:pcpCalItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(pcpCalItemByteArr);
				if(calItem.getDataType()==2){
					if(!"TodayKWattH".equalsIgnoreCase(calItem.getCode())){
						pcpCalItemList.add(calItem);
					}
				}
			}
			
			String date=StringManagerUtils.getCurrentTime();
			
			//先进行初始化
			if(deviceIdList!=null && deviceIdList.size()>0){
				for(int i=0;i<deviceIdList.size();i++){
					if(!jedis.hexists("DeviceRealtimeAcqData".getBytes(),deviceIdList.get(i).getBytes())){
						Map<String,Map<String,String>> realtimeDataTimeMap=new LinkedHashMap<>();
						jedis.hset("DeviceRealtimeAcqData".getBytes(), deviceIdList.get(i).getBytes(), SerializeObjectUnils.serialize(realtimeDataTimeMap));
					}
				}
			}else{
				List<DeviceInfo> deviceInfoList = MemoryDataManagerTask.getDeviceInfo();
				if(deviceInfoList!=null && deviceInfoList.size()>0){
					for(DeviceInfo deviceInfo:deviceInfoList){
						String key=deviceInfo.getId()+"";
						if(!jedis.hexists("DeviceRealtimeAcqData".getBytes(),key.getBytes())){
							Map<String,Map<String,String>> realtimeDataTimeMap=new LinkedHashMap<>();
							jedis.hset("DeviceRealtimeAcqData".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(realtimeDataTimeMap));
						}
					}
				}
			}
			
			Map<String, Object> dataModelMap=DataModelMap.getMapObject();
			if(!dataModelMap.containsKey("ProtocolMappingColumn")){
				MemoryDataManagerTask.loadProtocolMappingColumn();
			}
			Map<String,DataMapping> loadProtocolMappingColumnMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumn");
			List<String> queryAcqColumns=new ArrayList<>();
			List<String> tableColumnList=MemoryDataManagerTask.getAcqTableColumn("tbl_acqdata_hist");
			String sql="select t.deviceId,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime";
			for(String column:tableColumnList){
				if(loadProtocolMappingColumnMap.containsKey(column)){
					sql+=",t."+column;
					queryAcqColumns.add(column);
				}
			}
			sql+=" from tbl_acqdata_hist t "
				+ " where t.acqTime between to_date('"+date+"','yyyy-mm-dd') and to_date('"+date+"','yyyy-mm-dd')+1";
			if(deviceIdList!=null && deviceIdList.size()>0){
				sql+=" and t.deviceId in("+StringUtils.join(deviceIdList, ",")+")";
			}
			sql+=" order by t.deviceId,t.acqTime";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				int deviceId=rs.getInt(1);
				String acqTime=rs.getString(2);
				String key=deviceId+"";
				if(!jedis.hexists("DeviceRealtimeAcqData".getBytes(),key.getBytes())){
					Map<String,Map<String,String>> realtimeDataTimeMap=new LinkedHashMap<>();
					Map<String,String> everyDataMap =new LinkedHashMap<>();
					
					for(int i=0;i<queryAcqColumns.size();i++){
						everyDataMap.put(queryAcqColumns.get(i).toUpperCase(), rs.getString(i+3));
					}
					realtimeDataTimeMap.put(acqTime, everyDataMap);
					jedis.hset("DeviceRealtimeAcqData".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(realtimeDataTimeMap));
				}else{
					Map<String,Map<String,String>> realtimeDataTimeMap =(Map<String,Map<String,String>>) SerializeObjectUnils.unserizlize(jedis.hget("DeviceRealtimeAcqData".getBytes(), key.getBytes()));
					Map<String,String> everyDataMap =new LinkedHashMap<>();
					for(int i=0;i<queryAcqColumns.size();i++){
						everyDataMap.put(queryAcqColumns.get(i).toUpperCase(), rs.getString(i+3));
					}
					realtimeDataTimeMap.put(acqTime, everyDataMap);
					jedis.hset("DeviceRealtimeAcqData".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(realtimeDataTimeMap));
				}
			}
			rs.close();
			pstmt.close();
			
			//加载功图计算数据
			sql="select t.deviceId,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,t.productiondata";
			for(CalItem calItem:rpcCalItemList){
				sql+=",t."+calItem.getCode();
			}	
			sql+= " from viw_rpcacqdata_hist t"
					+ " where t.acqTime between to_date('"+date+"','yyyy-mm-dd') and to_date('"+date+"','yyyy-mm-dd')+1";
			if(deviceIdList!=null && deviceIdList.size()>0){
				sql+=" and t.deviceId in("+StringUtils.join(deviceIdList, ",")+")";
			}
			sql+=" order by t.deviceId,t.acqTime";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				int deviceId=rs.getInt(1);
				String acqTime=rs.getString(2);
				String key=deviceId+"";
				String productionDataStr=rs.getString(3);
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
				
				if(!jedis.hexists("DeviceRealtimeAcqData".getBytes(),key.getBytes())){
					Map<String,Map<String,String>> realtimeDataTimeMap=new LinkedHashMap<>();
					Map<String,String> everyDataMap =new LinkedHashMap<>();
					
					for(int i=0;i<rpcCalItemList.size();i++){
						everyDataMap.put(rpcCalItemList.get(i).getCode().toUpperCase(), rs.getString(i+4));
					}

					Iterator<Map.Entry<String,String>> productionDataMapIterator = productionDataMap.entrySet().iterator();
					while(productionDataMapIterator.hasNext()){
						Map.Entry<String,String> entry = productionDataMapIterator.next();
						everyDataMap.put(entry.getKey().toUpperCase(), entry.getValue());
					}
					
					realtimeDataTimeMap.put(acqTime, everyDataMap);
					jedis.hset("DeviceRealtimeAcqData".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(realtimeDataTimeMap));
				}else{
					Map<String,Map<String,String>> realtimeDataTimeMap =(Map<String,Map<String,String>>) SerializeObjectUnils.unserizlize(jedis.hget("DeviceRealtimeAcqData".getBytes(), key.getBytes()));
					Map<String,String> everyDataMap =realtimeDataTimeMap.get(acqTime);
					if(everyDataMap!=null){
						for(int i=0;i<rpcCalItemList.size();i++){
							everyDataMap.put(rpcCalItemList.get(i).getCode().toUpperCase(), rs.getString(i+4));
						}
						
						Iterator<Map.Entry<String,String>> productionDataMapIterator = productionDataMap.entrySet().iterator();
						while(productionDataMapIterator.hasNext()){
							Map.Entry<String,String> entry = productionDataMapIterator.next();
							everyDataMap.put(entry.getKey().toUpperCase(), entry.getValue());
						}
						
						realtimeDataTimeMap.put(acqTime, everyDataMap);
						jedis.hset("DeviceRealtimeAcqData".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(realtimeDataTimeMap));
					}
				}
			}
			rs.close();
			pstmt.close();
			
			//加载转速计产数据
			sql="select t.deviceId,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,t.productiondata";
			for(CalItem calItem:pcpCalItemList){
				sql+=",t."+calItem.getCode();
			}	
			sql+= " from viw_pcpacqdata_hist t"
					+ " where t.acqTime between to_date('"+date+"','yyyy-mm-dd') and to_date('"+date+"','yyyy-mm-dd')+1";
			if(deviceIdList!=null && deviceIdList.size()>0){
				sql+=" and t.deviceId in("+StringUtils.join(deviceIdList, ",")+")";
			}
			sql+=" order by t.deviceId,t.acqTime";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				int deviceId=rs.getInt(1);
				String acqTime=rs.getString(2);
				String key=deviceId+"";
				
				String productionDataStr=rs.getString(3);
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
				
				if(!jedis.hexists("DeviceRealtimeAcqData".getBytes(),key.getBytes())){
					Map<String,Map<String,String>> realtimeDataTimeMap=new LinkedHashMap<>();
					Map<String,String> everyDataMap =new LinkedHashMap<>();
					
					for(int i=0;i<pcpCalItemList.size();i++){
						everyDataMap.put(pcpCalItemList.get(i).getCode().toUpperCase(), rs.getString(i+4));
					}
					
					Iterator<Map.Entry<String,String>> productionDataMapIterator = productionDataMap.entrySet().iterator();
					while(productionDataMapIterator.hasNext()){
						Map.Entry<String,String> entry = productionDataMapIterator.next();
						everyDataMap.put(entry.getKey().toUpperCase(), entry.getValue());
					}
					
					realtimeDataTimeMap.put(acqTime, everyDataMap);
					jedis.hset("DeviceRealtimeAcqData".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(realtimeDataTimeMap));
				}else{
					Map<String,Map<String,String>> realtimeDataTimeMap =(Map<String,Map<String,String>>) SerializeObjectUnils.unserizlize(jedis.hget("DeviceRealtimeAcqData".getBytes(), key.getBytes()));
					Map<String,String> everyDataMap =realtimeDataTimeMap.get(acqTime);
					if(everyDataMap!=null){
						for(int i=0;i<pcpCalItemList.size();i++){
							everyDataMap.put(pcpCalItemList.get(i).getCode().toUpperCase(), rs.getString(i+4));
						}
						
						Iterator<Map.Entry<String,String>> productionDataMapIterator = productionDataMap.entrySet().iterator();
						while(productionDataMapIterator.hasNext()){
							Map.Entry<String,String> entry = productionDataMapIterator.next();
							everyDataMap.put(entry.getKey().toUpperCase(), entry.getValue());
						}
						
						realtimeDataTimeMap.put(acqTime, everyDataMap);
						jedis.hset("DeviceRealtimeAcqData".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(realtimeDataTimeMap));
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static void loadDeviceInfoByPumpingAuxiliaryId(String auxiliaryId,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select distinct(t.id) from tbl_device t,tbl_auxiliary2master t2 where t.id = t2.masterid and t2.auxiliaryid in("+auxiliaryId+")";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadDeviceInfoByPumpingId(String pumpingModelId,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select t.id from tbl_device t,tbl_pumpingmodel t2 where t.pumpingmodelid=t2.id and t2.id= "+pumpingModelId;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadDeviceInfoByInstanceId(String instanceId,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select t.id from tbl_device t,tbl_protocolinstance t2 where t.instancecode=t2.code and t2.id= "+instanceId;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadDeviceInfoByInstanceCode(String instanceCode,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select t.id from tbl_device t where t.instancecode= '"+instanceCode+"'";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadDeviceInfoByDisplayInstanceId(String instanceId,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select t.id from tbl_device t,tbl_protocoldisplayinstance t2 where t.displayinstancecode=t2.code and t2.id= "+instanceId;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadDeviceInfoByDisplayInstanceCode(String instanceCode,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select t.id from tbl_device t where t.displayinstancecode= '"+instanceCode+"'";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadDeviceInfoByAlarmInstanceId(String instanceId,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select t.id from tbl_device t,tbl_protocolalarminstance t2 where t.alarminstancecode=t2.code and t2.id= "+instanceId;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadDeviceInfoByAlarmInstanceCode(String instanceCode,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select t.id from tbl_device t where t.alarminstancecode= '"+instanceCode+"'";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadDeviceInfo(wellList,0,method);
		}
	}
	
	public static AcqInstanceOwnItem getAcqInstanceOwnItemByCode(String instanceCode){
		AcqInstanceOwnItem acqInstanceOwnItem=null;
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("AcqInstanceOwnItem".getBytes())){
				MemoryDataManagerTask.loadAcqInstanceOwnItemById("","update");
			}
			if(jedis.hexists("AcqInstanceOwnItem".getBytes(), instanceCode.getBytes())){
				acqInstanceOwnItem=(AcqInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AcqInstanceOwnItem".getBytes(), instanceCode.getBytes()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
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
					if(jedis.hexists("AcqInstanceOwnItem".getBytes(), rs.getString(1).getBytes())){
						byte[]byt=  jedis.hget("AcqInstanceOwnItem".getBytes(), rs.getString(1).getBytes());
						Object obj = SerializeObjectUnils.unserizlize(byt);
						if (obj instanceof AcqInstanceOwnItem) {
							acqInstanceOwnItem=(AcqInstanceOwnItem) obj;
				         }
					}else{
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
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadAcqInstanceOwnItemByCode(String instanceCode,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocolinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(instanceCode)){
				instanceSql+=" and t.code='"+instanceCode+"'";
			}
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadAcqInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadAcqInstanceOwnItemByName(String instanceName,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocolinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(instanceName)){
				instanceSql+=" and t.name='"+instanceName+"'";
			}
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadAcqInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadAcqInstanceOwnItemByGroupId(String groupId,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocolinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(groupId)){
				instanceSql+=" and t.unitid in (select t6.unitid from tbl_acq_group2unit_conf t6 where t6.groupid="+groupId+")";
			}
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadAcqInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadAcqInstanceOwnItemByUnitId(String unitId,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocolinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(unitId)){
				instanceSql+=" and t.unitid ="+unitId;
			}
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadAcqInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadAcqInstanceOwnItemByProtocolName(String protocolName,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocolinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(protocolName)){
				instanceSql+=" and t.unitid in( select t2.id from tbl_acq_unit_conf t2 where t2.protocol='"+protocolName+"' )";
			}
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadAcqInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static DisplayInstanceOwnItem getDisplayInstanceOwnItemByCode(String instanceCode){
		DisplayInstanceOwnItem displayInstanceOwnItem=null;
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("DisplayInstanceOwnItem".getBytes())){
				MemoryDataManagerTask.loadDisplayInstanceOwnItemById("","update");
			}
			if(jedis!=null&&jedis.hexists("DisplayInstanceOwnItem".getBytes(), instanceCode.getBytes())){
				displayInstanceOwnItem=(DisplayInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("DisplayInstanceOwnItem".getBytes(), instanceCode.getBytes()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
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
					+ "decode(t.showlevel,null,9999,t.showlevel) as showlevel,decode(t.sort,null,9999,t.sort) as sort,"
					+ "t.realtimecurveconf,t.historycurveconf,"
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
    				if(jedis.hexists("DisplayInstanceOwnItem".getBytes(), rs.getString(1).getBytes())){
    					byte[]byt=  jedis.hget("DisplayInstanceOwnItem".getBytes(), rs.getString(1).getBytes());
    					Object obj = SerializeObjectUnils.unserizlize(byt);
    					if (obj instanceof DisplayInstanceOwnItem) {
    						displayInstanceOwnItem=(DisplayInstanceOwnItem) obj;
    			         }
    				}else{
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
    				displayItem.setSort(rs.getInt(9));
    				displayItem.setRealtimeCurveConf(rs.getString(10)+"");
    				displayItem.setHistoryCurveConf(rs.getString(11)+"");
    				displayItem.setType(rs.getInt(12));
    				int index=-1;
    				for(int i=0;i<displayInstanceOwnItem.getItemList().size();i++){
    					if(displayItem.getItemCode().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(i).getItemCode()) && displayItem.getType()==displayInstanceOwnItem.getItemList().get(i).getType()){
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
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadDisplayInstanceOwnItemByUnitId(String unitId,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocoldisplayinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(unitId)){
				instanceSql+=" and t.displayunitid="+unitId;
			}
			
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadDisplayInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadDisplayInstanceOwnItemByAcqGroupId(String groupId,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String sql="select t4.id "
					+ " from tbl_acq_unit_conf t,tbl_acq_group2unit_conf t2,tbl_acq_group_conf t3,tbl_protocoldisplayinstance t4,tbl_display_unit_conf t5 "
					+ " where t.id=t2.unitid and t2.groupid=t3.id and t5.acqunitid=t.id and t4.displayunitid=t5.id"
					+ " and t3.id="+groupId+"";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadDisplayInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadDisplayInstanceOwnItemByAcqUnitId(String unitId,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String sql="select t2.id "
					+ " from tbl_acq_unit_conf t,tbl_protocoldisplayinstance t2,tbl_display_unit_conf t3 "
					+ " where t3.acqunitid=t.id and t2.displayunitid=t3.id"
					+ " and t.id="+unitId+"";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadDisplayInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadDisplayInstanceOwnItemByCode(String instanceCode,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocoldisplayinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(instanceCode)){
				instanceSql+=" and t.code='"+instanceCode+"'";
			}
			
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadDisplayInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadDisplayInstanceOwnItemByName(String instanceName,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocoldisplayinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(instanceName)){
				instanceSql+=" and t.name='"+instanceName+"'";
			}
			
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadDisplayInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadDisplayInstanceOwnItemByProtocolName(String protocolName,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocoldisplayinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(protocolName)){
				instanceSql+=" and t.displayunitid in( select t2.id from tbl_display_unit_conf t2,tbl_acq_unit_conf t3 where t2.acqunitid=t3.id and t3.protocol='"+protocolName+"' )";
			}
			
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadDisplayInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static AlarmInstanceOwnItem getAlarmInstanceOwnItemByCode(String instanceCode){
		AlarmInstanceOwnItem alarmInstanceOwnItem=null;
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("AlarmInstanceOwnItem".getBytes())){
				MemoryDataManagerTask.loadAlarmInstanceOwnItemById("","update");
			}
			if(jedis!=null&&jedis.hexists("AlarmInstanceOwnItem".getBytes(), instanceCode.getBytes())){
				alarmInstanceOwnItem=(AlarmInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AlarmInstanceOwnItem".getBytes(), instanceCode.getBytes()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
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
					+ "t.value,t.upperlimit,t.lowerlimit,t.hystersis,t.delay,decode(t.alarmsign,0,0,t.alarmlevel) as alarmlevel,t.alarmsign,t.type,t.issendmessage,t.issendmail "
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
					if(jedis.hexists("AlarmInstanceOwnItem".getBytes(), rs.getString(1).getBytes())){
						byte[]byt=  jedis.hget("AlarmInstanceOwnItem".getBytes(), rs.getString(1).getBytes());
						Object obj = SerializeObjectUnils.unserizlize(byt);
						if (obj instanceof AlarmInstanceOwnItem) {
							alarmInstanceOwnItem=(AlarmInstanceOwnItem) obj;
				         }
					}else{
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
					
					alarmItem.setAlarmLevel(rs.getInt(14));
					alarmItem.setAlarmSign(rs.getInt(15));
					
					alarmItem.setType(rs.getInt(16));

					alarmItem.setIsSendMessage(rs.getInt(17));
					alarmItem.setIsSendMail(rs.getInt(18));
					
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
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static void loadAlarmInstanceOwnItemByCode(String instanceCode,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		int result=0;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocolalarminstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(instanceCode)){
				instanceSql+=" and t.code='"+instanceCode+"'";
			}
			
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadAlarmInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadAlarmInstanceOwnItemByName(String instanceName,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		int result=0;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocolalarminstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(instanceName)){
				instanceSql+=" and t.name='"+instanceName+"'";
			}
			
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadAlarmInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadAlarmInstanceOwnItemByUnitId(String unitId,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocolalarminstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(unitId)){
				instanceSql+=" and t.alarmunitid="+unitId;
			}
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadAlarmInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadAlarmInstanceOwnItemByProtocolName(String protocolName,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocolalarminstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(protocolName)){
				instanceSql+=" and t.alarmunitid in (select t2.id from tbl_alarm_unit_conf t2 where t2.protocol='"+protocolName+"')";
			}
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadAlarmInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static List<CalItem> getRPCCalculateItem(){
		Jedis jedis=null;
		List<CalItem> calItemList=new ArrayList<>();
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("rpcCalItemList".getBytes())){
				MemoryDataManagerTask.loadRPCCalculateItem();
			}
			List<byte[]> calItemSet= jedis.zrange("rpcCalItemList".getBytes(), 0, -1);
			for(byte[] rpcCalItemByteArr:calItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(rpcCalItemByteArr);
				calItemList.add(calItem);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		return calItemList;
	}
	
	public static void loadRPCCalculateItem(){
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			//有序集合
			jedis.zadd("rpcCalItemList".getBytes(),1, SerializeObjectUnils.serialize(new CalItem("在线时间","CommTime","h",2,"自定义","在线时间")));
			jedis.zadd("rpcCalItemList".getBytes(),2, SerializeObjectUnils.serialize(new CalItem("在线时率","CommTimeEfficiency","小数",2,"自定义","在线时率")));
			jedis.zadd("rpcCalItemList".getBytes(),3, SerializeObjectUnils.serialize(new CalItem("在线区间","CommRange","",1,"自定义","在线区间")));
			
			jedis.zadd("rpcCalItemList".getBytes(),4, SerializeObjectUnils.serialize(new CalItem("运行时间","RunTime","h",2,"自定义","运行时间")));
			jedis.zadd("rpcCalItemList".getBytes(),5, SerializeObjectUnils.serialize(new CalItem("运行时率","RunTimeEfficiency","小数",2,"自定义","运行时率")));
			jedis.zadd("rpcCalItemList".getBytes(),6, SerializeObjectUnils.serialize(new CalItem("运行区间","RunRange","",1,"自定义","运行区间")));
			
			
			jedis.zadd("rpcCalItemList".getBytes(),7, SerializeObjectUnils.serialize(new CalItem("工况","ResultName","",1,"自定义","工况")));
			jedis.zadd("rpcCalItemList".getBytes(),8, SerializeObjectUnils.serialize(new CalItem("最大载荷","FMax","kN",2,"自定义","最大载荷")));
			jedis.zadd("rpcCalItemList".getBytes(),9, SerializeObjectUnils.serialize(new CalItem("最小载荷","FMin","kN",2,"自定义","最小载荷")));
			
			jedis.zadd("rpcCalItemList".getBytes(),10, SerializeObjectUnils.serialize(new CalItem("计算冲程","Stroke","",2,"自定义","功图计算冲程")));
			jedis.zadd("rpcCalItemList".getBytes(),11, SerializeObjectUnils.serialize(new CalItem("柱塞冲程","PlungerStroke","m",2,"自定义","柱塞冲程")));
			jedis.zadd("rpcCalItemList".getBytes(),12, SerializeObjectUnils.serialize(new CalItem("柱塞有效冲程","AvailablePlungerStroke","m",2,"自定义","柱塞有效冲程")));
			jedis.zadd("rpcCalItemList".getBytes(),13, SerializeObjectUnils.serialize(new CalItem("抽空柱塞有效冲程","NoLiquidAvailablePlungerStroke","m",2,"自定义","抽空柱塞有效冲程")));
			
			jedis.zadd("rpcCalItemList".getBytes(),14, SerializeObjectUnils.serialize(new CalItem("充满系数","FullnessCoefficient","",2,"自定义","充满系数")));
			jedis.zadd("rpcCalItemList".getBytes(),15, SerializeObjectUnils.serialize(new CalItem("抽空充满系数","NoLiquidFullnessCoefficient","",2,"自定义","抽空充满系数")));
			
			jedis.zadd("rpcCalItemList".getBytes(),16, SerializeObjectUnils.serialize(new CalItem("理论上载荷","UpperLoadLine","kN",2,"自定义","理论上载荷")));
			jedis.zadd("rpcCalItemList".getBytes(),17, SerializeObjectUnils.serialize(new CalItem("理论下载荷","LowerLoadLine","kN",2,"自定义","理论下载荷")));
			jedis.zadd("rpcCalItemList".getBytes(),18, SerializeObjectUnils.serialize(new CalItem("考虑沉没压力的理论上载荷","UpperLoadLineOfExact","kN",2,"自定义","考虑沉没压力的理论上载荷")));
			
			
			jedis.zadd("rpcCalItemList".getBytes(),19, SerializeObjectUnils.serialize(new CalItem("理论排量","TheoreticalProduction","m^3/d",2,"自定义","理论排量")));
			
			jedis.zadd("rpcCalItemList".getBytes(),20, SerializeObjectUnils.serialize(new CalItem("瞬时产液量","LiquidVolumetricProduction","m^3/d",2,"自定义","瞬时产液量")));
			jedis.zadd("rpcCalItemList".getBytes(),21, SerializeObjectUnils.serialize(new CalItem("瞬时产油量","OilVolumetricProduction","m^3/d",2,"自定义","瞬时产油量")));
			jedis.zadd("rpcCalItemList".getBytes(),22, SerializeObjectUnils.serialize(new CalItem("瞬时产水量","WaterVolumetricProduction","m^3/d",2,"自定义","瞬时产水量")));
			jedis.zadd("rpcCalItemList".getBytes(),23, SerializeObjectUnils.serialize(new CalItem("柱塞有效冲程计算产量","AvailablePlungerStrokeProd_v","m^3/d",2,"自定义","柱塞有效冲程计算产量")));
			jedis.zadd("rpcCalItemList".getBytes(),24, SerializeObjectUnils.serialize(new CalItem("泵间隙漏失量","PumpClearanceleakProd_v","m^3/d",2,"自定义","泵间隙漏失量")));
			jedis.zadd("rpcCalItemList".getBytes(),25, SerializeObjectUnils.serialize(new CalItem("游动凡尔漏失量","TVLeakVolumetricProduction","m^3/d",2,"自定义","游动凡尔漏失量")));
			jedis.zadd("rpcCalItemList".getBytes(),26, SerializeObjectUnils.serialize(new CalItem("固定凡尔漏失量","SVLeakVolumetricProduction","m^3/d",2,"自定义","固定凡尔漏失量")));
			jedis.zadd("rpcCalItemList".getBytes(),27, SerializeObjectUnils.serialize(new CalItem("气影响","GasInfluenceProd_v","m^3/d",2,"自定义","气影响")));
			jedis.zadd("rpcCalItemList".getBytes(),28, SerializeObjectUnils.serialize(new CalItem("日累计产液量","LiquidVolumetricProduction_l","m^3/d",2,"自定义","日累计产液量")));
			jedis.zadd("rpcCalItemList".getBytes(),29, SerializeObjectUnils.serialize(new CalItem("日累计产油量","OilVolumetricProduction_l","m^3/d",2,"自定义","日累计产油量")));
			jedis.zadd("rpcCalItemList".getBytes(),30, SerializeObjectUnils.serialize(new CalItem("日累计产水量","WaterVolumetricProduction_l","m^3/d",2,"自定义","日累计产水量")));
			
			jedis.zadd("rpcCalItemList".getBytes(),31, SerializeObjectUnils.serialize(new CalItem("瞬时产液量","LiquidWeightProduction","t/d",2,"自定义","瞬时产液量")));
			jedis.zadd("rpcCalItemList".getBytes(),32, SerializeObjectUnils.serialize(new CalItem("瞬时产油量","OilWeightProduction","t/d",2,"自定义","瞬时产油量")));
			jedis.zadd("rpcCalItemList".getBytes(),33, SerializeObjectUnils.serialize(new CalItem("瞬时产水量","WaterWeightProduction","t/d",2,"自定义","瞬时产水量")));
			jedis.zadd("rpcCalItemList".getBytes(),34, SerializeObjectUnils.serialize(new CalItem("柱塞有效冲程计算产量","AvailablePlungerStrokeProd_w","t/d",2,"自定义","柱塞有效冲程计算产量")));
			jedis.zadd("rpcCalItemList".getBytes(),35, SerializeObjectUnils.serialize(new CalItem("泵间隙漏失量","PumpClearanceleakProd_w","t/d",2,"自定义","泵间隙漏失量")));
			jedis.zadd("rpcCalItemList".getBytes(),36, SerializeObjectUnils.serialize(new CalItem("游动凡尔漏失量","TVLeakWeightProduction","t/d",2,"自定义","游动凡尔漏失量")));
			jedis.zadd("rpcCalItemList".getBytes(),37, SerializeObjectUnils.serialize(new CalItem("固定凡尔漏失量","SVLeakWeightProduction","t/d",2,"自定义","固定凡尔漏失量")));
			jedis.zadd("rpcCalItemList".getBytes(),38, SerializeObjectUnils.serialize(new CalItem("气影响","GasInfluenceProd_w","t/d",2,"自定义","气影响")));
			jedis.zadd("rpcCalItemList".getBytes(),39, SerializeObjectUnils.serialize(new CalItem("日累计产液量","LiquidWeightProduction_l","t/d",2,"自定义","日累计产液量")));
			jedis.zadd("rpcCalItemList".getBytes(),40, SerializeObjectUnils.serialize(new CalItem("日累计产油量","OilWeightProduction_l","t/d",2,"自定义","日累计产油量")));
			jedis.zadd("rpcCalItemList".getBytes(),41, SerializeObjectUnils.serialize(new CalItem("日累计产水量","WaterWeightProduction_l","t/d",2,"自定义","日累计产水量")));
			
			jedis.zadd("rpcCalItemList".getBytes(),42, SerializeObjectUnils.serialize(new CalItem("有功功率","AverageWatt","kW",2,"自定义","有功功率")));
			jedis.zadd("rpcCalItemList".getBytes(),43, SerializeObjectUnils.serialize(new CalItem("光杆功率","PolishRodPower","kW",2,"自定义","光杆功率")));
			jedis.zadd("rpcCalItemList".getBytes(),44, SerializeObjectUnils.serialize(new CalItem("水功率","WaterPower","kW",2,"自定义","水功率")));
			
			jedis.zadd("rpcCalItemList".getBytes(),45, SerializeObjectUnils.serialize(new CalItem("地面效率","SurfaceSystemEfficiency","",2,"自定义","地面效率")));
			jedis.zadd("rpcCalItemList".getBytes(),46, SerializeObjectUnils.serialize(new CalItem("井下效率","WellDownSystemEfficiency","",2,"自定义","井下效率")));
			jedis.zadd("rpcCalItemList".getBytes(),47, SerializeObjectUnils.serialize(new CalItem("系统效率","SystemEfficiency","",2,"自定义","系统效率")));
			jedis.zadd("rpcCalItemList".getBytes(),48, SerializeObjectUnils.serialize(new CalItem("功图面积","Area","",2,"自定义","功图面积")));
			jedis.zadd("rpcCalItemList".getBytes(),49, SerializeObjectUnils.serialize(new CalItem("吨液百米耗电量","EnergyPer100mLift","kW· h/100m· t",2,"自定义","吨液百米耗电量")));
			
			
			jedis.zadd("rpcCalItemList".getBytes(),50, SerializeObjectUnils.serialize(new CalItem("抽油杆伸长量","RodFlexLength","m",2,"自定义","抽油杆伸长量")));
			jedis.zadd("rpcCalItemList".getBytes(),51, SerializeObjectUnils.serialize(new CalItem("油管伸缩量","TubingFlexLength","m",2,"自定义","油管伸缩量")));
			jedis.zadd("rpcCalItemList".getBytes(),52, SerializeObjectUnils.serialize(new CalItem("惯性载荷增量","InertiaLength","m",2,"自定义","惯性载荷增量")));
			jedis.zadd("rpcCalItemList".getBytes(),53, SerializeObjectUnils.serialize(new CalItem("冲程损失系数","PumpEff1","",2,"自定义","冲程损失系数")));
			jedis.zadd("rpcCalItemList".getBytes(),54, SerializeObjectUnils.serialize(new CalItem("充满系数","PumpEff2","",2,"自定义","充满系数")));
			jedis.zadd("rpcCalItemList".getBytes(),55, SerializeObjectUnils.serialize(new CalItem("间隙漏失系数","PumpEff3","",2,"自定义","间隙漏失系数")));
			jedis.zadd("rpcCalItemList".getBytes(),56, SerializeObjectUnils.serialize(new CalItem("液体收缩系数","PumpEff4","",2,"自定义","液体收缩系数")));
			jedis.zadd("rpcCalItemList".getBytes(),57, SerializeObjectUnils.serialize(new CalItem("总泵效","PumpEff","",2,"自定义","总泵效")));
			
			jedis.zadd("rpcCalItemList".getBytes(),58, SerializeObjectUnils.serialize(new CalItem("泵入口压力","PumpIntakeP","MPa",2,"自定义","泵入口压力")));
			jedis.zadd("rpcCalItemList".getBytes(),59, SerializeObjectUnils.serialize(new CalItem("泵入口温度","PumpIntakeT","℃",2,"自定义","泵入口温度")));
			jedis.zadd("rpcCalItemList".getBytes(),60, SerializeObjectUnils.serialize(new CalItem("泵入口就地气液比","PumpIntakeGOL","m^3/m^3",2,"自定义","泵入口就地气液比")));
			jedis.zadd("rpcCalItemList".getBytes(),61, SerializeObjectUnils.serialize(new CalItem("泵入口粘度","PumpIntakeVisl","mPa·s",2,"自定义","泵入口粘度")));
			jedis.zadd("rpcCalItemList".getBytes(),62, SerializeObjectUnils.serialize(new CalItem("泵入口原油体积系数","PumpIntakeBo","",2,"自定义","泵入口原油体积系数")));
			
			jedis.zadd("rpcCalItemList".getBytes(),63, SerializeObjectUnils.serialize(new CalItem("泵出口压力","PumpOutletP","MPa",2,"自定义","泵出口压力")));
			jedis.zadd("rpcCalItemList".getBytes(),64, SerializeObjectUnils.serialize(new CalItem("泵出口温度","PumpOutletT","℃",2,"自定义","泵出口温度")));
			jedis.zadd("rpcCalItemList".getBytes(),65, SerializeObjectUnils.serialize(new CalItem("泵出口就地气液比","PumpOutletGOL","m^3/m^3",2,"自定义","泵出口就地气液比")));
			jedis.zadd("rpcCalItemList".getBytes(),66, SerializeObjectUnils.serialize(new CalItem("泵出口粘度","PumpOutletVisl","mPa·s",2,"自定义","泵出口粘度")));
			jedis.zadd("rpcCalItemList".getBytes(),67, SerializeObjectUnils.serialize(new CalItem("泵出口原油体积系数","PumpOutletBo","",2,"自定义","泵出口原油体积系数")));
			
			jedis.zadd("rpcCalItemList".getBytes(),68, SerializeObjectUnils.serialize(new CalItem("上冲程最大电流","UpStrokeIMax","A",2,"自定义","上冲程最大电流")));
			jedis.zadd("rpcCalItemList".getBytes(),69, SerializeObjectUnils.serialize(new CalItem("下冲程最大电流","DownStrokeIMax","A",2,"自定义","下冲程最大电流")));
			jedis.zadd("rpcCalItemList".getBytes(),70, SerializeObjectUnils.serialize(new CalItem("上冲程最大功率","UpStrokeWattMax","kW",2,"自定义","上冲程最大功率")));
			jedis.zadd("rpcCalItemList".getBytes(),71, SerializeObjectUnils.serialize(new CalItem("下冲程最大功率","DownStrokeWattMax","kW",2,"自定义","下冲程最大功率")));
			jedis.zadd("rpcCalItemList".getBytes(),72, SerializeObjectUnils.serialize(new CalItem("电流平衡度","IDegreeBalance","%",2,"自定义","电流平衡度")));
			jedis.zadd("rpcCalItemList".getBytes(),73, SerializeObjectUnils.serialize(new CalItem("功率平衡度","WattDegreeBalance","%",2,"自定义","功率平衡度")));
			jedis.zadd("rpcCalItemList".getBytes(),74, SerializeObjectUnils.serialize(new CalItem("移动距离","DeltaRadius","m",2,"自定义","移动距离")));
			
			jedis.zadd("rpcCalItemList".getBytes(),75, SerializeObjectUnils.serialize(new CalItem("液面校正差值","LevelDifferenceValue","MPa",2,"自定义","反演液面校正值")));
			jedis.zadd("rpcCalItemList".getBytes(),76, SerializeObjectUnils.serialize(new CalItem("反演动液面","CalcProducingfluidLevel","m",2,"自定义","反演动液面")));
			
			jedis.zadd("rpcCalItemList".getBytes(),77, SerializeObjectUnils.serialize(new CalItem("沉没度","Submergence","m",2,"自定义","沉没度")));
			
//			jedis.zadd("rpcCalItemList".getBytes(),78, SerializeObjectUnils.serialize(new CalItem("日用电量","TodayKWattH","kW·h",2,"计算","日用电量")));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static List<CalItem> getPCPCalculateItem(){
		Jedis jedis=null;
		List<CalItem> calItemList=new ArrayList<>();
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("pcpCalItemList".getBytes())){
				MemoryDataManagerTask.loadPCPCalculateItem();
			}
			List<byte[]> calItemSet= jedis.zrange("pcpCalItemList".getBytes(), 0, -1);
			for(byte[] rpcCalItemByteArr:calItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(rpcCalItemByteArr);
				calItemList.add(calItem);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		return calItemList;
	}
	
	public static void loadPCPCalculateItem(){
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			//有序集合
			jedis.zadd("pcpCalItemList".getBytes(),1, SerializeObjectUnils.serialize(new CalItem("在线时间","CommTime","h",2,"自定义","在线时间")));
			jedis.zadd("pcpCalItemList".getBytes(),2, SerializeObjectUnils.serialize(new CalItem("在线时率","CommTimeEfficiency","小数",2,"自定义","在线时率")));
			jedis.zadd("pcpCalItemList".getBytes(),3, SerializeObjectUnils.serialize(new CalItem("在线区间","CommRange","",1,"自定义","在线区间")));
			
			jedis.zadd("pcpCalItemList".getBytes(),4, SerializeObjectUnils.serialize(new CalItem("运行时间","RunTime","h",2,"自定义","运行时间")));
			jedis.zadd("pcpCalItemList".getBytes(),5, SerializeObjectUnils.serialize(new CalItem("运行时率","RunTimeEfficiency","小数",2,"自定义","运行时率")));
			jedis.zadd("pcpCalItemList".getBytes(),6, SerializeObjectUnils.serialize(new CalItem("运行区间","RunRange","",1,"自定义","运行区间")));
			
			jedis.zadd("pcpCalItemList".getBytes(),7, SerializeObjectUnils.serialize(new CalItem("理论排量","TheoreticalProduction","m^3/d",2,"自定义","理论排量")));
			
			jedis.zadd("pcpCalItemList".getBytes(),8, SerializeObjectUnils.serialize(new CalItem("瞬时产液量","LiquidVolumetricProduction","m^3/d",2,"自定义","瞬时产液量")));
			jedis.zadd("pcpCalItemList".getBytes(),9, SerializeObjectUnils.serialize(new CalItem("瞬时产油量","OilVolumetricProduction","m^3/d",2,"自定义","瞬时产油量")));
			jedis.zadd("pcpCalItemList".getBytes(),10, SerializeObjectUnils.serialize(new CalItem("瞬时产水量","WaterVolumetricProduction","m^3/d",2,"自定义","瞬时产水量")));
			jedis.zadd("pcpCalItemList".getBytes(),11, SerializeObjectUnils.serialize(new CalItem("日累计产液量","LiquidVolumetricProduction_l","m^3/d",2,"自定义","日累计产液量")));
			jedis.zadd("pcpCalItemList".getBytes(),12, SerializeObjectUnils.serialize(new CalItem("日累计产油量","OilVolumetricProduction_l","m^3/d",2,"自定义","日累计产油量")));
			jedis.zadd("pcpCalItemList".getBytes(),13, SerializeObjectUnils.serialize(new CalItem("日累计产水量","WaterVolumetricProduction_l","m^3/d",2,"自定义","日累计产水量")));
			
			jedis.zadd("pcpCalItemList".getBytes(),14, SerializeObjectUnils.serialize(new CalItem("瞬时产液量","LiquidWeightProduction","t/d",2,"自定义","瞬时产液量")));
			jedis.zadd("pcpCalItemList".getBytes(),15, SerializeObjectUnils.serialize(new CalItem("瞬时产油量","OilWeightProduction","t/d",2,"自定义","瞬时产油量")));
			jedis.zadd("pcpCalItemList".getBytes(),16, SerializeObjectUnils.serialize(new CalItem("瞬时产水量","WaterWeightProduction","t/d",2,"自定义","瞬时产水量")));
			jedis.zadd("pcpCalItemList".getBytes(),17, SerializeObjectUnils.serialize(new CalItem("日累计产液量","LiquidWeightProduction_l","t/d",2,"自定义","日累计产液量")));
			jedis.zadd("pcpCalItemList".getBytes(),18, SerializeObjectUnils.serialize(new CalItem("日累计产油量","OilWeightProduction_l","t/d",2,"自定义","日累计产油量")));
			jedis.zadd("pcpCalItemList".getBytes(),19, SerializeObjectUnils.serialize(new CalItem("日累计产水量","WaterWeightProduction_l","t/d",2,"自定义","日累计产水量")));
			
			jedis.zadd("pcpCalItemList".getBytes(),20, SerializeObjectUnils.serialize(new CalItem("有功功率","AverageWatt","kW",2,"自定义","有功功率")));
			jedis.zadd("pcpCalItemList".getBytes(),21, SerializeObjectUnils.serialize(new CalItem("水功率","WaterPower","kW",2,"自定义","水功率")));
			jedis.zadd("pcpCalItemList".getBytes(),22, SerializeObjectUnils.serialize(new CalItem("系统效率","SystemEfficiency","",2,"自定义","系统效率")));
			
			jedis.zadd("pcpCalItemList".getBytes(),23, SerializeObjectUnils.serialize(new CalItem("容积效率","PumpEff1","",2,"自定义","容积效率")));
			jedis.zadd("pcpCalItemList".getBytes(),24, SerializeObjectUnils.serialize(new CalItem("液体收缩系数","PumpEff2","",2,"自定义","液体收缩系数")));
			jedis.zadd("pcpCalItemList".getBytes(),25, SerializeObjectUnils.serialize(new CalItem("总泵效","PumpEff","",2,"自定义","总泵效")));
			
			jedis.zadd("pcpCalItemList".getBytes(),26, SerializeObjectUnils.serialize(new CalItem("泵入口压力","PumpIntakeP","MPa",2,"自定义","泵入口压力")));
			jedis.zadd("pcpCalItemList".getBytes(),27, SerializeObjectUnils.serialize(new CalItem("泵入口温度","PumpIntakeT","℃",2,"自定义","泵入口温度")));
			jedis.zadd("pcpCalItemList".getBytes(),28, SerializeObjectUnils.serialize(new CalItem("泵入口就地气液比","PumpIntakeGOL","m^3/m^3",2,"自定义","泵入口就地气液比")));
			jedis.zadd("pcpCalItemList".getBytes(),29, SerializeObjectUnils.serialize(new CalItem("泵入口粘度","PumpIntakeVisl","mPa·s",2,"自定义","泵入口粘度")));
			jedis.zadd("pcpCalItemList".getBytes(),30, SerializeObjectUnils.serialize(new CalItem("泵入口原油体积系数","PumpIntakeBo","",2,"自定义","泵入口原油体积系数")));
			
			jedis.zadd("pcpCalItemList".getBytes(),31, SerializeObjectUnils.serialize(new CalItem("泵出口压力","PumpOutletP","MPa",2,"自定义","泵出口压力")));
			jedis.zadd("pcpCalItemList".getBytes(),32, SerializeObjectUnils.serialize(new CalItem("泵出口温度","PumpOutletT","℃",2,"自定义","泵出口温度")));
			jedis.zadd("pcpCalItemList".getBytes(),33, SerializeObjectUnils.serialize(new CalItem("泵出口就地气液比","PumpOutletGOL","m^3/m^3",2,"自定义","泵出口就地气液比")));
			jedis.zadd("pcpCalItemList".getBytes(),34, SerializeObjectUnils.serialize(new CalItem("泵出口粘度","PumpOutletVisl","mPa·s",2,"自定义","泵出口粘度")));
			jedis.zadd("pcpCalItemList".getBytes(),35, SerializeObjectUnils.serialize(new CalItem("泵出口原油体积系数","PumpOutletBo","",2,"自定义","泵出口原油体积系数")));
			
//			jedis.zadd("pcpCalItemList".getBytes(),36, SerializeObjectUnils.serialize(new CalItem("日用电量","TodayKWattH","kW·h",2,"计算","日用电量")));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static void loadAcqTotalCalculateItem(){
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			//有序集合
			jedis.zadd("acqTotalCalItemList".getBytes(),1, SerializeObjectUnils.serialize(new CalItem("井名","DeviceName","",1,"录入","井名")));//1-字符串 2-数值 3-日期 4-日期时间
			jedis.zadd("acqTotalCalItemList".getBytes(),2, SerializeObjectUnils.serialize(new CalItem("日期","CalDate","",3,"计算","日期")));
			
			jedis.zadd("acqTotalCalItemList".getBytes(),3, SerializeObjectUnils.serialize(new CalItem("在线时间","CommTime","h",2,"计算","在线时间,通信计算所得")));
			jedis.zadd("acqTotalCalItemList".getBytes(),4, SerializeObjectUnils.serialize(new CalItem("在线时率","CommTimeEfficiency","小数",2,"计算","在线时率,通信计算所得")));
			jedis.zadd("acqTotalCalItemList".getBytes(),5, SerializeObjectUnils.serialize(new CalItem("在线区间","CommRange","",1,"计算","在线区间,通信计算所得")));
			
			jedis.zadd("acqTotalCalItemList".getBytes(),6, SerializeObjectUnils.serialize(new CalItem("运行时间","RunTime","h",2,"计算","运行时间,时率计算所得")));
			jedis.zadd("acqTotalCalItemList".getBytes(),7, SerializeObjectUnils.serialize(new CalItem("运行时率","RunTimeEfficiency","小数",2,"计算","运行时率,时率计算所得")));
			jedis.zadd("acqTotalCalItemList".getBytes(),8, SerializeObjectUnils.serialize(new CalItem("运行区间","RunRange","",1,"计算","运行区间,时率计算所得")));
			
			jedis.zadd("acqTotalCalItemList".getBytes(),9, SerializeObjectUnils.serialize(new CalItem("备注","Remark","",1,"录入","备注")));
			
			jedis.zadd("acqTotalCalItemList".getBytes(),10, SerializeObjectUnils.serialize(new CalItem("备用1","reservedcol1","",1,"录入","备用1")));
			jedis.zadd("acqTotalCalItemList".getBytes(),11, SerializeObjectUnils.serialize(new CalItem("备用2","reservedcol2","",1,"录入","备用2")));
			jedis.zadd("acqTotalCalItemList".getBytes(),12, SerializeObjectUnils.serialize(new CalItem("备用3","reservedcol3","",1,"录入","备用3")));
			jedis.zadd("acqTotalCalItemList".getBytes(),13, SerializeObjectUnils.serialize(new CalItem("备用4","reservedcol4","",1,"录入","备用4")));
			jedis.zadd("acqTotalCalItemList".getBytes(),14, SerializeObjectUnils.serialize(new CalItem("备用5","reservedcol5","",1,"录入","备用5")));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static void loadRPCTotalCalculateItem(){
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			//有序集合
			jedis.zadd("rpcTotalCalItemList".getBytes(),1, SerializeObjectUnils.serialize(new CalItem("井名","DeviceName","",1,"录入","井名")));//1-字符串 2-数值 3-日期 4-日期时间
			jedis.zadd("rpcTotalCalItemList".getBytes(),2, SerializeObjectUnils.serialize(new CalItem("日期","CalDate","",3,"计算","日期")));
			
			jedis.zadd("rpcTotalCalItemList".getBytes(),3, SerializeObjectUnils.serialize(new CalItem("在线时间","CommTime","h",2,"计算","在线时间,通信计算所得")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),4, SerializeObjectUnils.serialize(new CalItem("在线时率","CommTimeEfficiency","小数",2,"计算","在线时率,通信计算所得")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),5, SerializeObjectUnils.serialize(new CalItem("在线区间","CommRange","",1,"计算","在线区间,通信计算所得")));
			
			jedis.zadd("rpcTotalCalItemList".getBytes(),6, SerializeObjectUnils.serialize(new CalItem("运行时间","RunTime","h",2,"计算","运行时间,时率计算所得")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),7, SerializeObjectUnils.serialize(new CalItem("运行时率","RunTimeEfficiency","小数",2,"计算","运行时率,时率计算所得")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),8, SerializeObjectUnils.serialize(new CalItem("运行区间","RunRange","",1,"计算","运行区间,时率计算所得")));
			
			jedis.zadd("rpcTotalCalItemList".getBytes(),9, SerializeObjectUnils.serialize(new CalItem("工况","ResultName","",1,"计算","功图工况")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),10, SerializeObjectUnils.serialize(new CalItem("优化建议","OptimizationSuggestion","",1,"计算","优化建议")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),11, SerializeObjectUnils.serialize(new CalItem("冲程","Stroke","m",2,"计算","冲程")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),12, SerializeObjectUnils.serialize(new CalItem("冲次","SPM","次/min",2,"计算","冲次")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),13, SerializeObjectUnils.serialize(new CalItem("最大载荷","FMax","kN",2,"计算","最大载荷")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),14, SerializeObjectUnils.serialize(new CalItem("最小载荷","FMin","kN",2,"计算","最小载荷")));
			
			jedis.zadd("rpcTotalCalItemList".getBytes(),15, SerializeObjectUnils.serialize(new CalItem("充满系数","FullnessCoefficient","",2,"计算","充满系数")));
			
			jedis.zadd("rpcTotalCalItemList".getBytes(),16, SerializeObjectUnils.serialize(new CalItem("理论排量","TheoreticalProduction","m^3/d",2,"计算","理论排量")));
			
			jedis.zadd("rpcTotalCalItemList".getBytes(),17, SerializeObjectUnils.serialize(new CalItem("日累计产液量","LiquidVolumetricProduction","m^3/d",2,"计算","日累计产液量,功图汇总计算所得")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),18, SerializeObjectUnils.serialize(new CalItem("日累计产油量","OilVolumetricProduction","m^3/d",2,"计算","日累计产油量,功图汇总计算所得")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),19, SerializeObjectUnils.serialize(new CalItem("日累计产水量","WaterVolumetricProduction","m^3/d",2,"计算","日累计产水量,功图汇总计算或者累计产水量计算所得")));
//			jedis.zadd("rpcTotalCalItemList".getBytes(),20, SerializeObjectUnils.serialize(new CalItem("日累计产气量","GasVolumetricProduction","m^3/d",2,"计算","日累计产气量，累计产气量计算所得")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),21, SerializeObjectUnils.serialize(new CalItem("体积含水率","VolumeWaterCut","%",2,"计算","体积含水率")));
			
			jedis.zadd("rpcTotalCalItemList".getBytes(),22, SerializeObjectUnils.serialize(new CalItem("日累计产液量","LiquidWeightProduction","t/d",2,"计算","日累计产液量,功图汇总计算所得")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),23, SerializeObjectUnils.serialize(new CalItem("日累计产油量","OilWeightProduction","t/d",2,"计算","日累计产油量,功图汇总计算所得")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),24, SerializeObjectUnils.serialize(new CalItem("日累计产水量","WaterWeightProduction","t/d",2,"计算","日累计产水量,功图汇总计算或者累计产水量计算所得")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),25, SerializeObjectUnils.serialize(new CalItem("重量含水率","WeightWaterCut","%",2,"计算","重量含水率")));
			
			jedis.zadd("rpcTotalCalItemList".getBytes(),26, SerializeObjectUnils.serialize(new CalItem("地面效率","SurfaceSystemEfficiency","",2,"计算","地面效率")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),27, SerializeObjectUnils.serialize(new CalItem("井下效率","WellDownSystemEfficiency","",2,"计算","井下效率")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),28, SerializeObjectUnils.serialize(new CalItem("系统效率","SystemEfficiency","",2,"计算","系统效率")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),29, SerializeObjectUnils.serialize(new CalItem("吨液百米耗电量","EnergyPer100mLift","kW· h/100m· t",2,"计算","吨液百米耗电量")));
			
			jedis.zadd("rpcTotalCalItemList".getBytes(),30, SerializeObjectUnils.serialize(new CalItem("冲程损失系数","PumpEff1","",2,"计算","冲程损失系数")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),31, SerializeObjectUnils.serialize(new CalItem("充满系数","PumpEff2","",2,"计算","充满系数")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),32, SerializeObjectUnils.serialize(new CalItem("间隙漏失系数","PumpEff3","",2,"计算","间隙漏失系数")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),33, SerializeObjectUnils.serialize(new CalItem("液体收缩系数","PumpEff4","",2,"计算","液体收缩系数")));
			
			jedis.zadd("rpcTotalCalItemList".getBytes(),34, SerializeObjectUnils.serialize(new CalItem("容积效率","PumpEff1","",2,"计算","容积效率")));
			
			jedis.zadd("rpcTotalCalItemList".getBytes(),35, SerializeObjectUnils.serialize(new CalItem("总泵效","PumpEff","",2,"计算","总泵效")));
			
			jedis.zadd("rpcTotalCalItemList".getBytes(),36, SerializeObjectUnils.serialize(new CalItem("转速","RPM","r/min",2,"计算","转速")));
			
			jedis.zadd("rpcTotalCalItemList".getBytes(),37, SerializeObjectUnils.serialize(new CalItem("电流平衡度","IDegreeBalance","%",2,"计算","电流平衡度")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),38, SerializeObjectUnils.serialize(new CalItem("功率平衡度","WattDegreeBalance","%",2,"计算","功率平衡度")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),39, SerializeObjectUnils.serialize(new CalItem("移动距离","DeltaRadius","m",2,"计算","移动距离")));
			
//			jedis.zadd("rpcTotalCalItemList".getBytes(),40, SerializeObjectUnils.serialize(new CalItem("日用电量","TodayKWattH","kW·h",2,"计算","日用电量,累计用电量计算所得")));
//			jedis.zadd("rpcTotalCalItemList".getBytes(),42, SerializeObjectUnils.serialize(new CalItem("累计用电量","TotalKWattH","kW·h",2,"计算","累计用电量,当日最新采集数据")));
//			
//			jedis.zadd("rpcTotalCalItemList".getBytes(),42, SerializeObjectUnils.serialize(new CalItem("累计产气量","TotalGasVolumetricProduction","m^3",2,"计算","累计产气量,当日最新采集数据")));
//			jedis.zadd("rpcTotalCalItemList".getBytes(),43, SerializeObjectUnils.serialize(new CalItem("累计产水量","TotalWaterVolumetricProduction","m^3",2,"计算","累计产水量,当日最新采集数据")));
			
			jedis.zadd("rpcTotalCalItemList".getBytes(),44, SerializeObjectUnils.serialize(new CalItem("泵挂","PumpSettingDepth","m",2,"计算","泵挂")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),45, SerializeObjectUnils.serialize(new CalItem("动液面","ProducingfluidLevel","m",2,"计算","动液面")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),46, SerializeObjectUnils.serialize(new CalItem("反演动液面","CalcProducingfluidLevel","m",2,"计算","反演动液面")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),47, SerializeObjectUnils.serialize(new CalItem("沉没度","Submergence","m",2,"计算","沉没度")));
			
			jedis.zadd("rpcTotalCalItemList".getBytes(),48, SerializeObjectUnils.serialize(new CalItem("油压","TubingPressure","MPa",2,"计算","油压")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),49, SerializeObjectUnils.serialize(new CalItem("套压","CasingPressure","MPa",2,"计算","套压")));
			jedis.zadd("rpcTotalCalItemList".getBytes(),50, SerializeObjectUnils.serialize(new CalItem("井底压力","BottomHolePressure","MPa",2,"计算","井底压力")));
			
			jedis.zadd("rpcTotalCalItemList".getBytes(),51, SerializeObjectUnils.serialize(new CalItem("备注","Remark","",1,"录入","备注")));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static void loadPCPTotalCalculateItem(){
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			//有序集合
			jedis.zadd("pcpTotalCalItemList".getBytes(),1, SerializeObjectUnils.serialize(new CalItem("井名","DeviceName","",1,"录入","井名")));
			jedis.zadd("pcpTotalCalItemList".getBytes(),2, SerializeObjectUnils.serialize(new CalItem("日期","CalDate","",3,"计算","日期")));
			
			jedis.zadd("pcpTotalCalItemList".getBytes(),3, SerializeObjectUnils.serialize(new CalItem("在线时间","CommTime","h",2,"计算","在线时间,通信计算所得")));
			jedis.zadd("pcpTotalCalItemList".getBytes(),4, SerializeObjectUnils.serialize(new CalItem("在线时率","CommTimeEfficiency","小数",2,"计算","在线时率,通信计算所得")));
			jedis.zadd("pcpTotalCalItemList".getBytes(),5, SerializeObjectUnils.serialize(new CalItem("在线区间","CommRange","",1,"计算","在线区间,通信计算所得")));
			
			jedis.zadd("pcpTotalCalItemList".getBytes(),6, SerializeObjectUnils.serialize(new CalItem("运行时间","RunTime","h",2,"计算","运行时间,时率计算所得")));
			jedis.zadd("pcpTotalCalItemList".getBytes(),7, SerializeObjectUnils.serialize(new CalItem("运行时率","RunTimeEfficiency","小数",2,"计算","运行时率,时率计算所得")));
			jedis.zadd("pcpTotalCalItemList".getBytes(),8, SerializeObjectUnils.serialize(new CalItem("运行区间","RunRange","",1,"计算","运行区间,时率计算所得")));
			
			jedis.zadd("pcpTotalCalItemList".getBytes(),9, SerializeObjectUnils.serialize(new CalItem("转速","RPM","r/min",2,"计算","转速")));
			
			jedis.zadd("pcpTotalCalItemList".getBytes(),10, SerializeObjectUnils.serialize(new CalItem("理论排量","TheoreticalProduction","m^3/d",2,"计算","理论排量")));
			
			jedis.zadd("pcpTotalCalItemList".getBytes(),11, SerializeObjectUnils.serialize(new CalItem("日累计产液量","LiquidVolumetricProduction","m^3/d",2,"计算","日累计产液量,转速汇总计算所得")));
			jedis.zadd("pcpTotalCalItemList".getBytes(),12, SerializeObjectUnils.serialize(new CalItem("日累计产油量","OilVolumetricProduction","m^3/d",2,"计算","日累计产油量,转速汇总计算所得")));
			jedis.zadd("pcpTotalCalItemList".getBytes(),13, SerializeObjectUnils.serialize(new CalItem("日累计产水量","WaterVolumetricProduction","m^3/d",2,"计算","日累计产水量,转速汇总计算或者累计产水量计算所得")));
//			jedis.zadd("pcpTotalCalItemList".getBytes(),14, SerializeObjectUnils.serialize(new CalItem("日累计产气量","GasVolumetricProduction","m^3/d",2,"计算","日累计产气量,累计产气量计算所得")));
			jedis.zadd("pcpTotalCalItemList".getBytes(),15, SerializeObjectUnils.serialize(new CalItem("体积含水率","VolumeWaterCut","%",2,"计算","体积含水率")));
			
			jedis.zadd("pcpTotalCalItemList".getBytes(),16, SerializeObjectUnils.serialize(new CalItem("日累计产液量","LiquidWeightProduction","t/d",2,"计算","日累计产液量,转速汇总计算所得")));
			jedis.zadd("pcpTotalCalItemList".getBytes(),17, SerializeObjectUnils.serialize(new CalItem("日累计产油量","OilWeightProduction","t/d",2,"计算","日累计产油量,转速汇总计算所得")));
			jedis.zadd("pcpTotalCalItemList".getBytes(),18, SerializeObjectUnils.serialize(new CalItem("日累计产水量","WaterWeightProduction","t/d",2,"计算","日累计产水量,转速汇总计算或者累计产水量计算所得")));
			jedis.zadd("pcpTotalCalItemList".getBytes(),19, SerializeObjectUnils.serialize(new CalItem("重量含水率","WeightWaterCut","%",2,"计算","重量含水率")));
			
			jedis.zadd("pcpTotalCalItemList".getBytes(),20, SerializeObjectUnils.serialize(new CalItem("系统效率","SystemEfficiency","",2,"计算","系统效率")));
			jedis.zadd("pcpTotalCalItemList".getBytes(),21, SerializeObjectUnils.serialize(new CalItem("吨液百米耗电量","EnergyPer100mLift","kW· h/100m· t",2,"计算","吨液百米耗电量")));
			
			jedis.zadd("pcpTotalCalItemList".getBytes(),22, SerializeObjectUnils.serialize(new CalItem("容积效率","PumpEff1","",2,"计算","容积效率")));
			jedis.zadd("pcpTotalCalItemList".getBytes(),23, SerializeObjectUnils.serialize(new CalItem("液体收缩系数","PumpEff2","",2,"计算","液体收缩系数")));
			jedis.zadd("pcpTotalCalItemList".getBytes(),24, SerializeObjectUnils.serialize(new CalItem("总泵效","PumpEff","",2,"计算","总泵效")));
			
//			jedis.zadd("pcpTotalCalItemList".getBytes(),25, SerializeObjectUnils.serialize(new CalItem("日用电量","TodayKWattH","kW·h",2,"计算","日用电量,累计用电量计算所得")));
//			jedis.zadd("pcpTotalCalItemList".getBytes(),26, SerializeObjectUnils.serialize(new CalItem("累计用电量","TotalKWattH","kW·h",2,"计算","累计用电量,当日最新采集数据")));
//			
//			jedis.zadd("pcpTotalCalItemList".getBytes(),27, SerializeObjectUnils.serialize(new CalItem("累计产气量","TotalGasVolumetricProduction","m^3",2,"计算","累计产气量,当日最新采集数据")));
//			jedis.zadd("pcpTotalCalItemList".getBytes(),28, SerializeObjectUnils.serialize(new CalItem("累计产水量","TotalWaterVolumetricProduction","m^3",2,"计算","累计产水量,当日最新采集数据")));
			
			jedis.zadd("pcpTotalCalItemList".getBytes(),29, SerializeObjectUnils.serialize(new CalItem("泵挂","PumpSettingDepth","m",2,"计算","泵挂")));
			jedis.zadd("pcpTotalCalItemList".getBytes(),30, SerializeObjectUnils.serialize(new CalItem("动液面","ProducingfluidLevel","m",2,"计算","动液面")));
			jedis.zadd("pcpTotalCalItemList".getBytes(),31, SerializeObjectUnils.serialize(new CalItem("沉没度","Submergence","m",2,"计算","沉没度")));
			
			jedis.zadd("pcpTotalCalItemList".getBytes(),32, SerializeObjectUnils.serialize(new CalItem("油压","TubingPressure","MPa",2,"计算","油压")));
			jedis.zadd("pcpTotalCalItemList".getBytes(),33, SerializeObjectUnils.serialize(new CalItem("套压","CasingPressure","MPa",2,"计算","套压")));
			jedis.zadd("pcpTotalCalItemList".getBytes(),34, SerializeObjectUnils.serialize(new CalItem("井底压力","BottomHolePressure","MPa",2,"计算","井底压力")));
			
			jedis.zadd("pcpTotalCalItemList".getBytes(),35, SerializeObjectUnils.serialize(new CalItem("备注","Remark","",1,"录入","备注")));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static void loadAcqTimingTotalCalculateItem(){
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			//有序集合
			jedis.zadd("acqTimingTotalCalItemList".getBytes(),1, SerializeObjectUnils.serialize(new CalItem("井名","DeviceName","",1,"录入","井名")));//1-字符串 2-数值 3-日期 4-日期时间
			jedis.zadd("acqTimingTotalCalItemList".getBytes(),2, SerializeObjectUnils.serialize(new CalItem("时间","CalTime","",4,"计算","时间")));
			
			jedis.zadd("acqTimingTotalCalItemList".getBytes(),3, SerializeObjectUnils.serialize(new CalItem("在线时间","CommTime","h",2,"计算","在线时间,通信计算所得")));
			jedis.zadd("acqTimingTotalCalItemList".getBytes(),4, SerializeObjectUnils.serialize(new CalItem("在线时率","CommTimeEfficiency","小数",2,"计算","在线时率,通信计算所得")));
			jedis.zadd("acqTimingTotalCalItemList".getBytes(),5, SerializeObjectUnils.serialize(new CalItem("在线区间","CommRange","",1,"计算","在线区间,通信计算所得")));
			
			jedis.zadd("acqTimingTotalCalItemList".getBytes(),6, SerializeObjectUnils.serialize(new CalItem("运行时间","RunTime","h",2,"计算","运行时间,时率计算所得")));
			jedis.zadd("acqTimingTotalCalItemList".getBytes(),7, SerializeObjectUnils.serialize(new CalItem("运行时率","RunTimeEfficiency","小数",2,"计算","运行时率,时率计算所得")));
			jedis.zadd("acqTimingTotalCalItemList".getBytes(),8, SerializeObjectUnils.serialize(new CalItem("运行区间","RunRange","",1,"计算","运行区间,时率计算所得")));
			
			
			
			jedis.zadd("acqTimingTotalCalItemList".getBytes(),9, SerializeObjectUnils.serialize(new CalItem("备注","Remark","",1,"录入","备注")));
			
			jedis.zadd("acqTimingTotalCalItemList".getBytes(),10, SerializeObjectUnils.serialize(new CalItem("备用1","reservedcol1","",1,"录入","备用1")));
			jedis.zadd("acqTimingTotalCalItemList".getBytes(),11, SerializeObjectUnils.serialize(new CalItem("备用2","reservedcol2","",1,"录入","备用2")));
			jedis.zadd("acqTimingTotalCalItemList".getBytes(),12, SerializeObjectUnils.serialize(new CalItem("备用3","reservedcol3","",1,"录入","备用3")));
			jedis.zadd("acqTimingTotalCalItemList".getBytes(),13, SerializeObjectUnils.serialize(new CalItem("备用4","reservedcol4","",1,"录入","备用4")));
			jedis.zadd("acqTimingTotalCalItemList".getBytes(),14, SerializeObjectUnils.serialize(new CalItem("备用5","reservedcol5","",1,"录入","备用5")));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static void loadRPCTimingTotalCalculateItem(){
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			//有序集合
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),1, SerializeObjectUnils.serialize(new CalItem("井名","DeviceName","",1,"录入","井名")));//1-字符串 2-数值 3-日期 4-日期时间
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),2, SerializeObjectUnils.serialize(new CalItem("时间","CalTime","",4,"计算","时间")));
			
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),3, SerializeObjectUnils.serialize(new CalItem("在线时间","CommTime","h",2,"计算","在线时间,通信计算所得")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),4, SerializeObjectUnils.serialize(new CalItem("在线时率","CommTimeEfficiency","小数",2,"计算","在线时率,通信计算所得")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),5, SerializeObjectUnils.serialize(new CalItem("在线区间","CommRange","",1,"计算","在线区间,通信计算所得")));
			
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),6, SerializeObjectUnils.serialize(new CalItem("运行时间","RunTime","h",2,"计算","运行时间,时率计算所得")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),7, SerializeObjectUnils.serialize(new CalItem("运行时率","RunTimeEfficiency","小数",2,"计算","运行时率,时率计算所得")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),8, SerializeObjectUnils.serialize(new CalItem("运行区间","RunRange","",1,"计算","运行区间,时率计算所得")));
			
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),9, SerializeObjectUnils.serialize(new CalItem("工况","ResultName","",1,"计算","功图工况")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),10, SerializeObjectUnils.serialize(new CalItem("优化建议","OptimizationSuggestion","",1,"计算","优化建议")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),11, SerializeObjectUnils.serialize(new CalItem("冲程","Stroke","m",2,"计算","冲程")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),12, SerializeObjectUnils.serialize(new CalItem("冲次","SPM","次/min",2,"计算","冲次")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),13, SerializeObjectUnils.serialize(new CalItem("最大载荷","FMax","kN",2,"计算","最大载荷")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),14, SerializeObjectUnils.serialize(new CalItem("最小载荷","FMin","kN",2,"计算","最小载荷")));
			
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),15, SerializeObjectUnils.serialize(new CalItem("充满系数","FullnessCoefficient","",2,"计算","充满系数")));
			
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),16, SerializeObjectUnils.serialize(new CalItem("理论排量","TheoreticalProduction","m^3/d",2,"计算","理论排量")));
			
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),17, SerializeObjectUnils.serialize(new CalItem("日累计产液量","LiquidVolumetricProduction","m^3/d",2,"计算","日累计产液量,功图汇总计算所得")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),18, SerializeObjectUnils.serialize(new CalItem("日累计产油量","OilVolumetricProduction","m^3/d",2,"计算","日累计产油量,功图汇总计算所得")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),19, SerializeObjectUnils.serialize(new CalItem("日累计产水量","WaterVolumetricProduction","m^3/d",2,"计算","日累计产水量,功图汇总计算或者累计产水量计算所得")));
//			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),20, SerializeObjectUnils.serialize(new CalItem("日累计产气量","GasVolumetricProduction","m^3/d",2,"计算","日累计产气量，累计产气量计算所得")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),21, SerializeObjectUnils.serialize(new CalItem("体积含水率","VolumeWaterCut","%",2,"计算","体积含水率")));
			
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),22, SerializeObjectUnils.serialize(new CalItem("日累计产液量","LiquidWeightProduction","t/d",2,"计算","日累计产液量,功图汇总计算所得")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),23, SerializeObjectUnils.serialize(new CalItem("日累计产油量","OilWeightProduction","t/d",2,"计算","日累计产油量,功图汇总计算所得")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),24, SerializeObjectUnils.serialize(new CalItem("日累计产水量","WaterWeightProduction","t/d",2,"计算","日累计产水量,功图汇总计算或者累计产水量计算所得")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),25, SerializeObjectUnils.serialize(new CalItem("重量含水率","WeightWaterCut","%",2,"计算","重量含水率")));
			
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),26, SerializeObjectUnils.serialize(new CalItem("地面效率","SurfaceSystemEfficiency","",2,"计算","地面效率")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),27, SerializeObjectUnils.serialize(new CalItem("井下效率","WellDownSystemEfficiency","",2,"计算","井下效率")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),28, SerializeObjectUnils.serialize(new CalItem("系统效率","SystemEfficiency","",2,"计算","系统效率")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),29, SerializeObjectUnils.serialize(new CalItem("吨液百米耗电量","EnergyPer100mLift","kW· h/100m· t",2,"计算","吨液百米耗电量")));
			
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),30, SerializeObjectUnils.serialize(new CalItem("冲程损失系数","PumpEff1","",2,"计算","冲程损失系数")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),31, SerializeObjectUnils.serialize(new CalItem("充满系数","PumpEff2","",2,"计算","充满系数")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),32, SerializeObjectUnils.serialize(new CalItem("间隙漏失系数","PumpEff3","",2,"计算","间隙漏失系数")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),33, SerializeObjectUnils.serialize(new CalItem("液体收缩系数","PumpEff4","",2,"计算","液体收缩系数")));
			
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),34, SerializeObjectUnils.serialize(new CalItem("容积效率","PumpEff1","",2,"计算","容积效率")));
			
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),35, SerializeObjectUnils.serialize(new CalItem("总泵效","PumpEff","",2,"计算","总泵效")));
			
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),36, SerializeObjectUnils.serialize(new CalItem("转速","RPM","r/min",2,"计算","转速")));
			
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),37, SerializeObjectUnils.serialize(new CalItem("电流平衡度","IDegreeBalance","%",2,"计算","电流平衡度")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),38, SerializeObjectUnils.serialize(new CalItem("功率平衡度","WattDegreeBalance","%",2,"计算","功率平衡度")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),39, SerializeObjectUnils.serialize(new CalItem("移动距离","DeltaRadius","m",2,"计算","移动距离")));
			
//			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),40, SerializeObjectUnils.serialize(new CalItem("日用电量","TodayKWattH","kW·h",2,"计算","日用电量,累计用电量计算所得")));
//			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),41, SerializeObjectUnils.serialize(new CalItem("累计用电量","TotalKWattH","kW·h",2,"计算","累计用电量,当日最新采集数据")));
//			
//			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),42, SerializeObjectUnils.serialize(new CalItem("累计产气量","TotalGasVolumetricProduction","m^3",2,"计算","累计产气量,当日最新采集数据")));
//			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),43, SerializeObjectUnils.serialize(new CalItem("累计产水量","TotalWaterVolumetricProduction","m^3",2,"计算","累计产水量,当日最新采集数据")));
			
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),44, SerializeObjectUnils.serialize(new CalItem("瞬时产液量","RealtimeLiquidVolumetricProduction","m^3/d",2,"计算","瞬时产液量")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),45, SerializeObjectUnils.serialize(new CalItem("瞬时产油量","RealtimeOilVolumetricProduction","m^3/d",2,"计算","瞬时产油量")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),46, SerializeObjectUnils.serialize(new CalItem("瞬时产水量","RealtimeWaterVolumetricProduction","m^3/d",2,"计算","瞬时产水量")));
//			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),47, SerializeObjectUnils.serialize(new CalItem("瞬时产气量","RealtimeGasVolumetricProduction","m^3/d",2,"计算","瞬时产气量")));
			
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),48, SerializeObjectUnils.serialize(new CalItem("瞬时产液量","RealtimeLiquidWeightProduction","t/d",2,"计算","瞬时产液量")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),49, SerializeObjectUnils.serialize(new CalItem("瞬时产油量","RealtimeOilWeightProduction","t/d",2,"计算","瞬时产油量")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),50, SerializeObjectUnils.serialize(new CalItem("瞬时产水量","RealtimeWaterWeightProduction","t/d",2,"计算","瞬时产水量")));
			
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),51, SerializeObjectUnils.serialize(new CalItem("泵挂","PumpSettingDepth","m",2,"计算","泵挂")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),52, SerializeObjectUnils.serialize(new CalItem("动液面","ProducingfluidLevel","m",2,"计算","动液面")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),53, SerializeObjectUnils.serialize(new CalItem("反演动液面","CalcProducingfluidLevel","m",2,"计算","反演动液面")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),54, SerializeObjectUnils.serialize(new CalItem("沉没度","Submergence","m",2,"计算","沉没度")));
			
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),55, SerializeObjectUnils.serialize(new CalItem("油压","TubingPressure","MPa",2,"计算","油压")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),56, SerializeObjectUnils.serialize(new CalItem("套压","CasingPressure","MPa",2,"计算","套压")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),57, SerializeObjectUnils.serialize(new CalItem("井底压力","BottomHolePressure","MPa",2,"计算","井底压力")));
			
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),58, SerializeObjectUnils.serialize(new CalItem("备注","Remark","",1,"录入","备注")));
			
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),59, SerializeObjectUnils.serialize(new CalItem("备用1","reservedcol1","",1,"录入","备用1")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),60, SerializeObjectUnils.serialize(new CalItem("备用2","reservedcol2","",1,"录入","备用2")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),61, SerializeObjectUnils.serialize(new CalItem("备用3","reservedcol3","",1,"录入","备用3")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),62, SerializeObjectUnils.serialize(new CalItem("备用4","reservedcol4","",1,"录入","备用4")));
			jedis.zadd("rpcTimingTotalCalItemList".getBytes(),63, SerializeObjectUnils.serialize(new CalItem("备用5","reservedcol5","",1,"录入","备用5")));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static void loadPCPTimingTotalCalculateItem(){
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			//有序集合
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),1, SerializeObjectUnils.serialize(new CalItem("井名","DeviceName","",1,"录入","井名")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),2, SerializeObjectUnils.serialize(new CalItem("时间","CalTime","",4,"计算","时间")));
			
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),3, SerializeObjectUnils.serialize(new CalItem("在线时间","CommTime","h",2,"计算","在线时间,通信计算所得")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),4, SerializeObjectUnils.serialize(new CalItem("在线时率","CommTimeEfficiency","小数",2,"计算","在线时率,通信计算所得")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),5, SerializeObjectUnils.serialize(new CalItem("在线区间","CommRange","",1,"计算","在线区间,通信计算所得")));
			
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),6, SerializeObjectUnils.serialize(new CalItem("运行时间","RunTime","h",2,"计算","运行时间,时率计算所得")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),7, SerializeObjectUnils.serialize(new CalItem("运行时率","RunTimeEfficiency","小数",2,"计算","运行时率,时率计算所得")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),8, SerializeObjectUnils.serialize(new CalItem("运行区间","RunRange","",1,"计算","运行区间,时率计算所得")));
			
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),9, SerializeObjectUnils.serialize(new CalItem("转速","RPM","r/min",2,"计算","转速")));
			
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),10, SerializeObjectUnils.serialize(new CalItem("理论排量","TheoreticalProduction","m^3/d",2,"计算","理论排量")));
			
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),11, SerializeObjectUnils.serialize(new CalItem("日累计产液量","LiquidVolumetricProduction","m^3/d",2,"计算","日产液量,转速汇总计算所得")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),12, SerializeObjectUnils.serialize(new CalItem("日累计产油量","OilVolumetricProduction","m^3/d",2,"计算","日产油量,转速汇总计算所得")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),13, SerializeObjectUnils.serialize(new CalItem("日累计产水量","WaterVolumetricProduction","m^3/d",2,"计算","日产水量,转速汇总计算或者累计产水量计算所得")));
//			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),14, SerializeObjectUnils.serialize(new CalItem("日累计产气量","GasVolumetricProduction","m^3/d",2,"计算","日产气量,累计产气量计算所得")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),15, SerializeObjectUnils.serialize(new CalItem("体积含水率","VolumeWaterCut","%",2,"计算","体积含水率")));
			
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),16, SerializeObjectUnils.serialize(new CalItem("日累计产液量","LiquidWeightProduction","t/d",2,"计算","日产液量,转速汇总计算所得")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),17, SerializeObjectUnils.serialize(new CalItem("日累计产油量","OilWeightProduction","t/d",2,"计算","日产油量,转速汇总计算所得")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),18, SerializeObjectUnils.serialize(new CalItem("日累计产水量","WaterWeightProduction","t/d",2,"计算","日产水量,转速汇总计算或者累计产水量计算所得")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),19, SerializeObjectUnils.serialize(new CalItem("重量含水率","WeightWaterCut","%",2,"计算","重量含水率")));
			
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),20, SerializeObjectUnils.serialize(new CalItem("系统效率","SystemEfficiency","",2,"计算","系统效率")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),21, SerializeObjectUnils.serialize(new CalItem("吨液百米耗电量","EnergyPer100mLift","kW· h/100m· t",2,"计算","吨液百米耗电量")));
			
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),22, SerializeObjectUnils.serialize(new CalItem("容积效率","PumpEff1","",2,"计算","容积效率")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),23, SerializeObjectUnils.serialize(new CalItem("液体收缩系数","PumpEff2","",2,"计算","液体收缩系数")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),24, SerializeObjectUnils.serialize(new CalItem("总泵效","PumpEff","",2,"计算","总泵效")));
			
//			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),25, SerializeObjectUnils.serialize(new CalItem("日用电量","TodayKWattH","kW·h",2,"计算","日用电量,累计用电量计算所得")));
//			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),26, SerializeObjectUnils.serialize(new CalItem("累计用电量","TotalKWattH","kW·h",2,"计算","累计用电量,当日最新采集数据")));
//			
//			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),27, SerializeObjectUnils.serialize(new CalItem("累计产气量","TotalGasVolumetricProduction","m^3",2,"计算","累计产气量,当日最新采集数据")));
//			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),28, SerializeObjectUnils.serialize(new CalItem("累计产水量","TotalWaterVolumetricProduction","m^3",2,"计算","累计产水量,当日最新采集数据")));
			
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),29, SerializeObjectUnils.serialize(new CalItem("瞬时产液量","RealtimeLiquidVolumetricProduction","m^3",2,"计算","瞬时产液量")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),30, SerializeObjectUnils.serialize(new CalItem("瞬时产油量","RealtimeOilVolumetricProduction","m^3",2,"计算","瞬时产油量")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),31, SerializeObjectUnils.serialize(new CalItem("瞬时产水量","RealtimeWaterVolumetricProduction","m^3",2,"计算","瞬时产水量")));
//			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),32, SerializeObjectUnils.serialize(new CalItem("瞬时产气量","RealtimeGasVolumetricProduction","m^3",2,"计算","瞬时产气量")));
			
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),33, SerializeObjectUnils.serialize(new CalItem("瞬时产液量","RealtimeLiquidWeightProduction","t/d",2,"计算","瞬时产液量")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),34, SerializeObjectUnils.serialize(new CalItem("瞬时产油量","RealtimeOilWeightProduction","t/d",2,"计算","瞬时产油量")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),35, SerializeObjectUnils.serialize(new CalItem("瞬时产水量","RealtimeWaterWeightProduction","t/d",2,"计算","瞬时产水量")));
			
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),36, SerializeObjectUnils.serialize(new CalItem("泵挂","PumpSettingDepth","m",2,"计算","泵挂")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),37, SerializeObjectUnils.serialize(new CalItem("动液面","ProducingfluidLevel","m",2,"计算","动液面")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),38, SerializeObjectUnils.serialize(new CalItem("沉没度","Submergence","m",2,"计算","沉没度")));
			
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),39, SerializeObjectUnils.serialize(new CalItem("油压","TubingPressure","MPa",2,"计算","油压")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),40, SerializeObjectUnils.serialize(new CalItem("套压","CasingPressure","MPa",2,"计算","套压")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),41, SerializeObjectUnils.serialize(new CalItem("井底压力","BottomHolePressure","MPa",2,"计算","井底压力")));
			
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),42, SerializeObjectUnils.serialize(new CalItem("备注","Remark","",1,"录入","备注")));
			
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),43, SerializeObjectUnils.serialize(new CalItem("备用1","reservedcol1","",1,"录入","备用1")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),44, SerializeObjectUnils.serialize(new CalItem("备用2","reservedcol2","",1,"录入","备用2")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),45, SerializeObjectUnils.serialize(new CalItem("备用3","reservedcol3","",1,"录入","备用3")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),46, SerializeObjectUnils.serialize(new CalItem("备用4","reservedcol4","",1,"录入","备用4")));
			jedis.zadd("pcpTimingTotalCalItemList".getBytes(),47, SerializeObjectUnils.serialize(new CalItem("备用5","reservedcol5","",1,"录入","备用5")));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static void loadRPCInputItem(){
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			//有序集合
			jedis.zadd("rpcInputItemList".getBytes(),1, SerializeObjectUnils.serialize(new CalItem("原油密度","CrudeOilDensity","g/cm^3",2,"录入","原油密度")));
			jedis.zadd("rpcInputItemList".getBytes(),2, SerializeObjectUnils.serialize(new CalItem("水密度","WaterDensity","g/cm^3",2,"录入","水密度")));
			jedis.zadd("rpcInputItemList".getBytes(),3, SerializeObjectUnils.serialize(new CalItem("天然气相对密度","NaturalGasRelativeDensity","",2,"录入","天然气相对密度")));
			jedis.zadd("rpcInputItemList".getBytes(),4, SerializeObjectUnils.serialize(new CalItem("饱和压力","SaturationPressure","MPa",2,"录入","饱和压力")));
			
			jedis.zadd("rpcInputItemList".getBytes(),5, SerializeObjectUnils.serialize(new CalItem("油层中部深度","ReservoirDepth","m",2,"录入","油层中部深度")));
			jedis.zadd("rpcInputItemList".getBytes(),6, SerializeObjectUnils.serialize(new CalItem("油层中部温度","ReservoirTemperature","℃",2,"录入","油层中部温度")));
			
			jedis.zadd("rpcInputItemList".getBytes(),7, SerializeObjectUnils.serialize(new CalItem("油压","TubingPressure","MPa",2,"录入","油压")));
			jedis.zadd("rpcInputItemList".getBytes(),8, SerializeObjectUnils.serialize(new CalItem("套压","CasingPressure","MPa",2,"录入","套压")));
			jedis.zadd("rpcInputItemList".getBytes(),9, SerializeObjectUnils.serialize(new CalItem("井口温度","WellHeadTemperature","℃",2,"录入","井口温度")));
			jedis.zadd("rpcInputItemList".getBytes(),10, SerializeObjectUnils.serialize(new CalItem("含水率","WaterCut","%",2,"录入","含水率")));
			jedis.zadd("rpcInputItemList".getBytes(),11, SerializeObjectUnils.serialize(new CalItem("生产气油比","ProductionGasOilRatio","m^3/t",2,"录入","生产气油比")));
			
			jedis.zadd("rpcInputItemList".getBytes(),12, SerializeObjectUnils.serialize(new CalItem("动液面","ProducingfluidLevel","m",2,"录入","动液面")));
			jedis.zadd("rpcInputItemList".getBytes(),13, SerializeObjectUnils.serialize(new CalItem("泵挂","PumpSettingDepth","m",2,"录入","泵挂")));
			
			jedis.zadd("rpcInputItemList".getBytes(),14, SerializeObjectUnils.serialize(new CalItem("泵径","PumpBoreDiameter","mm",2,"录入","泵径")));
			
			jedis.zadd("rpcInputItemList".getBytes(),15, SerializeObjectUnils.serialize(new CalItem("反演液面校正值","LevelCorrectValue","MPa",2,"录入","反演液面校正值")));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static void loadPCPInputItem(){
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			//有序集合
			jedis.zadd("pcpInputItemList".getBytes(),1, SerializeObjectUnils.serialize(new CalItem("原油密度","CrudeOilDensity","g/cm^3",2,"录入","原油密度")));
			jedis.zadd("pcpInputItemList".getBytes(),2, SerializeObjectUnils.serialize(new CalItem("水密度","WaterDensity","g/cm^3",2,"录入","水密度")));
			jedis.zadd("pcpInputItemList".getBytes(),3, SerializeObjectUnils.serialize(new CalItem("天然气相对密度","NaturalGasRelativeDensity","",2,"录入","天然气相对密度")));
			jedis.zadd("pcpInputItemList".getBytes(),4, SerializeObjectUnils.serialize(new CalItem("饱和压力","SaturationPressure","MPa",2,"录入","饱和压力")));
			
			jedis.zadd("pcpInputItemList".getBytes(),5, SerializeObjectUnils.serialize(new CalItem("油层中部深度","ReservoirDepth","m",2,"录入","油层中部深度")));
			jedis.zadd("pcpInputItemList".getBytes(),6, SerializeObjectUnils.serialize(new CalItem("油层中部温度","ReservoirTemperature","℃",2,"录入","油层中部温度")));
			
			jedis.zadd("pcpInputItemList".getBytes(),7, SerializeObjectUnils.serialize(new CalItem("油压","TubingPressure","MPa",2,"录入","油压")));
			jedis.zadd("pcpInputItemList".getBytes(),8, SerializeObjectUnils.serialize(new CalItem("套压","CasingPressure","MPa",2,"录入","套压")));
			jedis.zadd("pcpInputItemList".getBytes(),9, SerializeObjectUnils.serialize(new CalItem("井口温度","WellHeadTemperature","℃",2,"录入","井口温度")));
			jedis.zadd("pcpInputItemList".getBytes(),10, SerializeObjectUnils.serialize(new CalItem("含水率","WaterCut","%",2,"录入","含水率")));
			jedis.zadd("pcpInputItemList".getBytes(),11, SerializeObjectUnils.serialize(new CalItem("生产气油比","ProductionGasOilRatio","m^3/t",2,"录入","生产气油比")));
			
			jedis.zadd("pcpInputItemList".getBytes(),12, SerializeObjectUnils.serialize(new CalItem("动液面","ProducingfluidLevel","m",2,"录入","动液面")));
			jedis.zadd("pcpInputItemList".getBytes(),13, SerializeObjectUnils.serialize(new CalItem("泵挂","PumpSettingDepth","m",2,"录入","泵挂")));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static UserInfo getUserInfoByNo(String userNo){
		UserInfo userInfo=null;
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("UserInfo".getBytes())){
				MemoryDataManagerTask.loadUserInfo(null,0,"update");
			}
			if(jedis.hexists("UserInfo".getBytes(), userNo.getBytes())){
				userInfo=(UserInfo) SerializeObjectUnils.unserizlize(jedis.hget("UserInfo".getBytes(), userNo.getBytes()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		return userInfo;
	}
	
	public static void loadUserInfo(List<String> userList,int condition,String method){//condition 0 -用户id 1-用户账号
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
					+ " t2.role_name,t2.role_level,t2.showlevel "
					+ " from tbl_user t,tbl_role t2 "
					+ " where t.user_type=t2.role_id";
			if(StringManagerUtils.isNotNull(users)){
				if(condition==0){
					sql+=" and t.user_no in("+users+")";
				}else{
					sql+=" and t.user_id in("+users+")";
				}
			}
			sql+= " order by t.user_no";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				UserInfo userInfo=new UserInfo();
				userInfo.setUserNo(rs.getInt(1));
				userInfo.setUserId(rs.getString(2));
				userInfo.setUserName(rs.getString(3));
				userInfo.setUserPwd(rs.getString(4));
				userInfo.setUserOrgid(rs.getInt(5));
				
				userInfo.setUserInEmail(rs.getString(6));
				userInfo.setUserPhone(rs.getString(7));
				
				userInfo.setUserQuickLogin(rs.getInt(8));
				userInfo.setUserEnable(rs.getInt(9));
				userInfo.setReceiveSMS(rs.getInt(10));
				userInfo.setReceiveMail(rs.getInt(11));
				
				userInfo.setUserType(rs.getInt(12));
				userInfo.setRoleName(rs.getString(13));
				userInfo.setRoleLevel(rs.getInt(14));
				userInfo.setRoleShowLevel(rs.getInt(15));
				
				String key=userInfo.getUserNo()+"";
				jedis.hset("UserInfo".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(userInfo));//哈希(Hash)
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadUserInfoByRoleId(String roleId,String method){//condition 0 -用户id 1-用户账号
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		List<String> roleList=new ArrayList<String>();
        String sql="select t.user_no from TBL_USER t where t.user_type="+roleId;
        try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				roleList.add(rs.getInt(1)+"");
			}
			if(roleList.size()>0){
				loadUserInfo(roleList,0,method);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadUserInfoByOrgId(String orgId,String method){//condition 0 -用户id 1-用户账号
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		List<String> roleList=new ArrayList<String>();
        String sql="select t.user_no from TBL_USER t where t.user_orgid="+orgId;
        try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				roleList.add(rs.getInt(1)+"");
			}
			if(roleList.size()>0){
				loadUserInfo(roleList,0,method);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadRPCWorkType(){
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
			String sql="select t.id,t.resultcode,t.resultname,t.resultdescription,t.optimizationsuggestion,t.remark "
					+ " from TBL_RPC_WORKTYPE t order by t.resultcode";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				WorkType workType=new WorkType();
				workType.setId(rs.getInt(1));
				workType.setResultCode(rs.getInt(2));
				workType.setResultName(rs.getString(3));
				workType.setResultDescription(rs.getString(4));
				workType.setOptimizationSuggestion(rs.getString(5));
				workType.setRemark(rs.getString(6));
				String key=workType.getResultCode()+"";
				String keyByName=workType.getResultName()+"";
				
				jedis.hset("RPCWorkType".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(workType));//哈希(Hash)
				jedis.hset("RPCWorkTypeByName".getBytes(), keyByName.getBytes(), SerializeObjectUnils.serialize(workType));//哈希(Hash)
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static WorkType getWorkTypeByCode(String resultCode){
		WorkType workType=null;
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("RPCWorkType".getBytes())){
				MemoryDataManagerTask.loadRPCWorkType();
			}
			if(jedis.hexists("RPCWorkType".getBytes(), (resultCode).getBytes())){
				workType=(WorkType) SerializeObjectUnils.unserizlize(jedis.hget("RPCWorkType".getBytes(), (resultCode).getBytes()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		return workType;
	}
	
	public static int getResultCodeByName(String resultName){
		int resultCode=0;
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("RPCWorkTypeByName".getBytes())){
				MemoryDataManagerTask.loadRPCWorkType();
			}
			if(jedis.hexists("RPCWorkTypeByName".getBytes(), (resultName).getBytes())){
				WorkType workType=(WorkType) SerializeObjectUnils.unserizlize(jedis.hget("RPCWorkTypeByName".getBytes(), (resultName).getBytes()));
				resultCode=workType.getResultCode();
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		return resultCode;
	}
	
	public static AlarmShowStyle getAlarmShowStyle(){
		AlarmShowStyle alarmShowStyle=null;
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("AlarmShowStyle".getBytes())){
				MemoryDataManagerTask.initAlarmStyle();
			}
			alarmShowStyle=(AlarmShowStyle) SerializeObjectUnils.unserizlize(jedis.get("AlarmShowStyle".getBytes()));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
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
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return alarmShowStyle;
	}
	
	public static ModbusProtocolConfig getModbusProtocolConfig(){
		Jedis jedis=null;
		ModbusProtocolConfig modbusProtocolConfig=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("modbusProtocolConfig".getBytes())){
				MemoryDataManagerTask.loadProtocolConfig("");
			}
			modbusProtocolConfig=(ModbusProtocolConfig)SerializeObjectUnils.unserizlize(jedis.get("modbusProtocolConfig".getBytes()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
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
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("RPCDeviceTodayData".getBytes())){
				MemoryDataManagerTask.loadTodayFESDiagram(null,0);
			}
			if(jedis.hexists("RPCDeviceTodayData".getBytes(), key.getBytes())){
				deviceTodayData=(RPCDeviceTodayData) SerializeObjectUnils.unserizlize(jedis.hget("RPCDeviceTodayData".getBytes(), key.getBytes()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		return deviceTodayData;
	}
	
	public static void updateRPCDeviceTodayDataDeviceInfo(RPCDeviceTodayData deviceTodayData){
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("RPCDeviceTodayData".getBytes())){
				MemoryDataManagerTask.loadTodayFESDiagram(null,0);
			}
			jedis.hset("RPCDeviceTodayData".getBytes(), (deviceTodayData.getId()+"").getBytes(), SerializeObjectUnils.serialize(deviceTodayData));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
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
			if(jedis!=null&&jedis.isConnected()){
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
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public static void loadUIKitAccessToken(List<Integer> idList,String method ){
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
				pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					VideoKey videoKey=new VideoKey();
					videoKey.setId(rs.getInt(1));
					videoKey.setOrgId(rs.getInt(2));
					videoKey.setAccount(rs.getString(3));
					videoKey.setAppkey(rs.getString(4));
					videoKey.setSecret(rs.getString(5));
					
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
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public static void loadUIKitAccessTokenByName(List<String> nameList,String method ){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Integer> idList =new ArrayList<>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String names=StringManagerUtils.joinStringArr2(nameList, ",");
		String sql="select t.id from TBL_VIDEOKEY t "
				+ " where 1=1 ";
		if(StringManagerUtils.isNotNull(names)){
			sql+=" and t.account in("+names+")";
		}
		sql+=" order by t.id";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				idList.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
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
				accessToken=(AccessToken)SerializeObjectUnils.unserizlize(jedis.hget("UIKitAccessToken".getBytes(),(id+"").getBytes()));
				if(accessToken==null || (accessToken!=null&& "200".equalsIgnoreCase(accessToken.getCode()) &&new Date().getTime()>accessToken.getData().getExpireTime() )        ){
					List<Integer> loadList=new ArrayList<>();
					loadList.add(id);
					loadUIKitAccessToken(loadList,"update");
					accessToken=(AccessToken)SerializeObjectUnils.unserizlize(jedis.hget("UIKitAccessToken".getBytes(),(id+"").getBytes()));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		return accessToken;
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
	
	@SuppressWarnings("static-access")
	public static void loadReportTemplateConfig(){
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		Gson gson = new Gson();
		String path="";
		String configData="";
		java.lang.reflect.Type type=null;
		path=stringManagerUtils.getFilePath("reportTemplate.json","reportTemplate/");
		configData=stringManagerUtils.readFile(path,"utf-8");
		type = new TypeToken<ReportTemplate>() {}.getType();
		ReportTemplate reportTemplate=gson.fromJson(configData, type);
		if(reportTemplate==null){
			reportTemplate=new ReportTemplate();
			reportTemplate.setSingleWellRangeReportTemplate(new ArrayList<ReportTemplate.Template>());
			reportTemplate.setProductionReportTemplate(new ArrayList<ReportTemplate.Template>());
		}else{
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
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static ReportTemplate getReportTemplateConfig(){
		Jedis jedis=null;
		ReportTemplate reportTemplate=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("ReportTemplateConfig".getBytes())){
				MemoryDataManagerTask.loadReportTemplateConfig();
			}
			reportTemplate=(ReportTemplate)SerializeObjectUnils.unserizlize(jedis.get("ReportTemplateConfig".getBytes()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		return reportTemplate;
	}
	
	public static ReportTemplate.Template getSingleWellRangeReportTemplateByCode(String code){
		Jedis jedis=null;
		ReportTemplate reportTemplate=null;
		ReportTemplate.Template template=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("ReportTemplateConfig".getBytes())){
				MemoryDataManagerTask.loadReportTemplateConfig();
			}
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
			if(jedis!=null&&jedis.isConnected()){
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
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("ReportTemplateConfig".getBytes())){
				MemoryDataManagerTask.loadReportTemplateConfig();
			}
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
			if(jedis!=null&&jedis.isConnected()){
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
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("ReportTemplateConfig".getBytes())){
				MemoryDataManagerTask.loadReportTemplateConfig();
			}
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
			if(jedis!=null&&jedis.isConnected()){
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
	
	public static CalculateColumnInfo getCalColumnsInfo(){

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
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("有功功率","Watt3",10) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("无功功率","Var3",11) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("功率因数","PF3",12) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("变频设置频率","SetFrequency",13) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("变频运行频率","RunFrequency",14) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("油压","TubingPressure",15) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("套压","CasingPressure",16) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("井口温度","WellHeadTemperature",17) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("井底压力","BottomHolePressure",18) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("井底温度","BottomHoleTemperature",19) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("动液面","ProducingfluidLevel",20) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("含水率","VolumeWaterCut",21) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("功图实测点数","FESDiagramAcqCount",22) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("功图采集时间","FESDiagramAcqtime",23) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("冲次","SPM",24) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("冲程","Stroke",25) );
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
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("最大载荷","FMax",41) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("最小载荷","FMin",42) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("功图充满系数","FullnessCoefficient",43) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("理论上载荷","UpperLoadLine",44) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("理论下载荷","LowerLoadLine",45) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("理论排量","TheoreticalProduction",46) );
		
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
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("光杆功率","PolishRodPower",65) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("水功率","WaterPower",66) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("地面效率","SurfaceSystemEfficiency",67) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("井下效率","WellDownSystemEfficiency",68) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("系统效率","SystemEfficiency",69) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("吨液百米耗电量","EnergyPer100mLift",70) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("功图面积","Area",71) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("抽油杆伸长量","RodFlexLength",72) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("油管伸缩值","TubingFlexLength",73) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("惯性载荷增量","InertiaLength",74) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("冲程损失系数","PumpEff1",75) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("充满系数","PumpEff2",76) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("间隙漏失系数","PumpEff3",77) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("液体收缩系数","PumpEff4",78) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("容积效率","PumpEff1",79) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("总泵效","PumpEff",80) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("反演动液面","CalcProducingfluidLevel",81) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("液面校正差值","LevelDifferenceValue",82) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("上冲程功率最大值","UpStrokeWattMax",83) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("下冲程功率最大值","DownStrokeWattMax",84) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("功率平衡度","WattDegreeBalance",85) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("上冲程电流最大值","UpStrokeIMax",86) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("下冲程电流最大值","DownStrokeIMax",87) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("电流平衡度","IDegreeBalance",88) );
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("移动距离","DeltaRadius",89) );
		
		calculateColumnInfo.getRPCCalculateColumnList().add( new CalculateColumn("沉没度","Submergence",90) );
		
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
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("有功功率","Watt3",10) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("无功功率","Var3",11) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("功率因数","PF3",12) );
		
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("变频设置频率","SetFrequency",13) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("变频运行频率","RunFrequency",14) );
		
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("油压","TubingPressure",15) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("套压","CasingPressure",16) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("井口温度","WellHeadTemperature",17) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("井底压力","BottomHolePressure",18) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("井底温度","BottomHoleTemperature",19) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("动液面","ProducingfluidLevel",20) );
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("含水率","VolumeWaterCut",21) );
		
		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("螺杆泵转速","RPM",22) );
		
//		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("累计产气量","TotalGasVolumetricProduction",23) );
//		calculateColumnInfo.getPCPCalculateColumnList().add( new CalculateColumn("累计产水量","TotalWaterVolumetricProduction",24) );
		
		
		
		return calculateColumnInfo;
	}
	
	public static String getCalculateColumnFromName(int deviceType,String name){
		if(!StringManagerUtils.isNotNull(name)){
			return "";
		}
		CalculateColumnInfo calculateColumnInfo=getCalColumnsInfo();
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
	
	public static String getCalculateColumnNameFromCode(int deviceType,String code){
		if(!StringManagerUtils.isNotNull(code)){
			return "";
		}
		CalculateColumnInfo calculateColumnInfo=getCalColumnsInfo();
		List<CalculateColumn> calculateColumnList=calculateColumnInfo.getRPCCalculateColumnList();
		String name="";
		if(deviceType!=0){
			calculateColumnList=calculateColumnInfo.getPCPCalculateColumnList();
		}
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
