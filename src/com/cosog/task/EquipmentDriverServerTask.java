package com.cosog.task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cosog.model.AlarmShowStyle;
import com.cosog.model.CommStatus;
import com.cosog.model.drive.InitId;
import com.cosog.model.drive.InitInstance;
import com.cosog.model.drive.InitProtocol;
import com.cosog.model.drive.InitializedDeviceInfo;
import com.cosog.model.drive.KafkaConfig;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.utils.AcquisitionItemColumnsMap;
import com.cosog.utils.Config;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.EquipmentDriveMap;
import com.cosog.utils.JDBCUtil;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.SerializeObjectUnils;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import redis.clients.jedis.Jedis;

@Component("EquipmentDriverServerTask")  
public class EquipmentDriverServerTask {
	public static ServerSocket serverSocket=null;
	public static boolean adStatus=false;
	
	private static EquipmentDriverServerTask instance=new EquipmentDriverServerTask();
	
	public static EquipmentDriverServerTask getInstance(){
		return instance;
	}
	
	@SuppressWarnings({ "static-access", "unused" })
//	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void driveServerTast() throws SQLException, ParseException,InterruptedException, IOException{
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String allOfflineUrl=Config.getInstance().configFile.getAp().getServer().getUrl()+"/api/acq/allDeviceOffline";
		String probeUrl=Config.getInstance().configFile.getAd().getProbe().getInit();
		
		initWellCommStatus();
		MemoryDataManagerTask.loadMemoryData();
		
		
		
//		String path="";
//		StringManagerUtils stringManagerUtils=new StringManagerUtils();
//		
//		path=stringManagerUtils.getFilePath("test3.json","example/");
//		String onLineData=stringManagerUtils.readFile(path,"utf-8");
//		
//		path=stringManagerUtils.getFilePath("test4.json","example/");
//		String offLineData=stringManagerUtils.readFile(path,"utf-8");
//		
//		String url=Config.getInstance().configFile.getAp().getServer().getUrl()+"/api/acq/group";
//		String onlineUrl=Config.getInstance().configFile.getAp().getServer().getUrl()+"/api/acq/uponline";
//		String onlineUrl2=Config.getInstance().configFile.getAp().getServer().getUrl()+"/api/acq/downonline";
//		int i=0;
//		while(true){
//			if(i%2==0){
//				StringManagerUtils.sendPostMethod(onlineUrl, onLineData,"utf-8",0,0);
//				StringManagerUtils.sendPostMethod(onlineUrl2, onLineData,"utf-8",0,0);
//			}else{
//				StringManagerUtils.sendPostMethod(onlineUrl, offLineData,"utf-8",0,0);
//				StringManagerUtils.sendPostMethod(onlineUrl2, offLineData,"utf-8",0,0);
//			}
//			i++;
//			
////			StringManagerUtils.sendPostMethod(onlineUrl, onLineData,"utf-8",0,0);
//			
//			Thread.sleep(1000*5);
//		}
		
		initServerConfig();
		initProtocolConfig("","");
		initInstanceConfig(null,"");
		initSMSInstanceConfig(null,"");
		initSMSDevice(null,"");
		initRPCDriverAcquisitionInfoConfig(null,"");
		initPCPDriverAcquisitionInfoConfig(null,"");
		
		boolean sendMsg=false;
		exampleDataManage();
		do{
			String responseData=StringManagerUtils.sendPostMethod(probeUrl, "","utf-8",0,0);
			type = new TypeToken<DriverProbeResponse>() {}.getType();
			DriverProbeResponse driverProbeResponse=gson.fromJson(responseData, type);
			
			String Ver="";
			if(driverProbeResponse!=null){
				sendMsg=false;
				if(!driverProbeResponse.getHttpServerInitStatus()){
					initServerConfig();
				}
				if(!driverProbeResponse.getProtocolInitStatus()){
					initProtocolConfig("","");
				}
				if(!driverProbeResponse.getInstanceInitStatus()){
					initInstanceConfig(null,"");
					initSMSInstanceConfig(null,"");
				}
				if(!driverProbeResponse.getSMSInitStatus()){
					initSMSDevice(null,"");
				}
				if(!driverProbeResponse.getIDInitStatus()){
					//清空内存
					Map<String, Object> dataModelMap = DataModelMap.getMapObject();
					Map<String,InitializedDeviceInfo> initializedDeviceList=(Map<String,InitializedDeviceInfo>) dataModelMap.get("InitializedDeviceList");
					if(initializedDeviceList!=null){
						dataModelMap.remove("InitializedDeviceList");
						initializedDeviceList=new HashMap<String,InitializedDeviceInfo>();
						dataModelMap.put("InitializedDeviceList", initializedDeviceList);
					}
					
					initRPCDriverAcquisitionInfoConfig(null,"");
					initPCPDriverAcquisitionInfoConfig(null,"");
				}
				Ver=driverProbeResponse.getVer();
			}else{
				if(!sendMsg){
					StringManagerUtils.sendPostMethod(allOfflineUrl, "","utf-8",0,0);
					sendMsg=true;
				}
			}
			Thread.sleep(1000*1);
		}while(true);
	}
	
	@SuppressWarnings({ "static-access", "unused" })
//	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void adRPCDriveServerTast() throws SQLException, ParseException,InterruptedException, IOException{
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String allRPCOfflineUrl=Config.getInstance().configFile.getAp().getServer().getUrl()+"/api/acq/allDeviceRPCStatusOffline";
		String probeUrl_rpc=Config.getInstance().configFile.getAd_rpc().getProbe().getInit();
		initWellRPCCommStatus();
		
//		String path="";
//		StringManagerUtils stringManagerUtils=new StringManagerUtils();
//		
//		path=stringManagerUtils.getFilePath("test3.json","example/");
//		String onLineData=stringManagerUtils.readFile(path,"utf-8");
//		
//		path=stringManagerUtils.getFilePath("test4.json","example/");
//		String offLineData=stringManagerUtils.readFile(path,"utf-8");
//		
//		String url=Config.getInstance().configFile.getAp().getServer().getUrl()+"/api/acq/group";
//		String onlineUrl=Config.getInstance().configFile.getAp().getServer().getUrl()+"/api/acq/uponline";
//		String onlineUrl2=Config.getInstance().configFile.getAp().getServer().getUrl()+"/api/acq/downonline";
//		int i=0;
//		while(true){
//			if(i%2==0){
//				StringManagerUtils.sendPostMethod(onlineUrl, onLineData,"utf-8",0,0);
//				StringManagerUtils.sendPostMethod(onlineUrl2, onLineData,"utf-8",0,0);
//			}else{
//				StringManagerUtils.sendPostMethod(onlineUrl, offLineData,"utf-8",0,0);
//				StringManagerUtils.sendPostMethod(onlineUrl2, offLineData,"utf-8",0,0);
//			}
//			i++;
//			
////			StringManagerUtils.sendPostMethod(onlineUrl, onLineData,"utf-8",0,0);
//			
//			Thread.sleep(1000*5);
//		}
		
		initRPCServerConfig();
		boolean sendMsg_rpc=false;
		do{
			String responseData=StringManagerUtils.sendPostMethod(probeUrl_rpc, "","utf-8",0,0);
			type = new TypeToken<DriverProbeResponse>() {}.getType();
			DriverProbeResponse driverProbeResponse_rpc=gson.fromJson(responseData, type);
			
			if(driverProbeResponse_rpc!=null){
				sendMsg_rpc=false;
				if(!driverProbeResponse_rpc.getHttpServerInitStatus()){
					initRPCServerConfig();
				}
			}else{
				if(!sendMsg_rpc){
					StringManagerUtils.sendPostMethod(allRPCOfflineUrl, "","utf-8",0,0);
					sendMsg_rpc=true;
				}
			}
			Thread.sleep(1000*1);
		}while(true);
	}
	
	public static class ExampleDataManageThread extends Thread{
		private String deviceName;
		private int cycle;
		private int wait;
		public ExampleDataManageThread(String deviceName,int cycle,int wait) {
			super();
			this.deviceName = deviceName;
			this.cycle = cycle;
			this.wait = wait;
		}
		public void run(){
			try {
				Thread.sleep(1000*wait);
				String url=Config.getInstance().configFile.getAp().getServer().getUrl()+"/api/acq/group";
				
				String path="";
				StringManagerUtils stringManagerUtils=new StringManagerUtils();
				path=stringManagerUtils.getFilePath(deviceName+"_01.json","example/");
				String data=stringManagerUtils.readFile(path,"utf-8");
				
				path=stringManagerUtils.getFilePath(deviceName+"_02.json","example/");
				String data2=stringManagerUtils.readFile(path,"utf-8");
				
				int i=0;
				while(true){
					if(i%2==0){
						StringManagerUtils.sendPostMethod(url, data,"utf-8",0,0);
					}else{
						StringManagerUtils.sendPostMethod(url, data2,"utf-8",0,0);
					}
					i++;
					Thread.sleep(1000*cycle);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void exampleDataManage(){
		if(Config.getInstance().configFile.getAp().getOthers().getSimulateAcqEnable()){
			try {
				int sendCycle=Config.getInstance().configFile.getAp().getOthers().getSendCycle();
				int timeDifference=Config.getInstance().configFile.getAp().getOthers().getTimeDifference();
				new ExampleDataManageThread("rpc01",sendCycle,timeDifference*0).start();
				new ExampleDataManageThread("rpc02",sendCycle,timeDifference*1).start();
				new ExampleDataManageThread("rpc03",sendCycle,timeDifference*2).start();
				new ExampleDataManageThread("rpc04",sendCycle,timeDifference*3).start();
				new ExampleDataManageThread("rpc05",sendCycle,timeDifference*4).start();
				new ExampleDataManageThread("rpc06",sendCycle,timeDifference*5).start();
				new ExampleDataManageThread("rpc07",sendCycle,timeDifference*6).start();
				new ExampleDataManageThread("rpc08",sendCycle,timeDifference*7).start();
//				new ExampleDataManageThread("rpc09",sendCycle,timeDifference*8).start();
//				new ExampleDataManageThread("rpc10",sendCycle,timeDifference*9).start();
				
				new ExampleDataManageThread("pcp01",sendCycle,timeDifference*10).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static class DriverProbeResponse{
		public boolean ProtocolInitStatus;
		public boolean InstanceInitStatus;
		public boolean IDInitStatus;
		public boolean HttpServerInitStatus;
		public boolean SMSInitStatus;
		public String Ver;
		
		public boolean getProtocolInitStatus() {
			return ProtocolInitStatus;
		}
		public void setProtocolInitStatus(boolean protocolInitStatus) {
			ProtocolInitStatus = protocolInitStatus;
		}
		public boolean getIDInitStatus() {
			return IDInitStatus;
		}
		public void setIDInitStatus(boolean iDInitStatus) {
			IDInitStatus = iDInitStatus;
		}
		public boolean getHttpServerInitStatus() {
			return HttpServerInitStatus;
		}
		public void setHttpServerInitStatus(boolean httpServerInitStatus) {
			HttpServerInitStatus = httpServerInitStatus;
		}
		public String getVer() {
			return Ver;
		}
		public void setVer(String ver) {
			Ver = ver;
		}
		public boolean getInstanceInitStatus() {
			return InstanceInitStatus;
		}
		public void setInstanceInitStatus(boolean instanceInitStatus) {
			InstanceInitStatus = instanceInitStatus;
		}
		public boolean getSMSInitStatus() {
			return SMSInitStatus;
		}
		public void setSMSInitStatus(boolean sMSInitStatus) {
			SMSInitStatus = sMSInitStatus;
		}
	}
	
	@SuppressWarnings({ "static-access", "resource" })
	public static int syncDataMappingTable(){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		int result=0;
		int dataSaveMode=1;
		Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
		Map<String,String> rpcDeviceAcquisitionItemColumns=acquisitionItemColumnsMap.get("rpcDeviceAcquisitionItemColumns");
		Map<String,String> pcpDeviceAcquisitionItemColumns=acquisitionItemColumnsMap.get("pcpDeviceAcquisitionItemColumns");
		
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return -1;
        }
		
		try {
			String delSql="delete from tbl_datamapping t where t.mappingmode<>"+dataSaveMode;
			pstmt = conn.prepareStatement(delSql);
			result=pstmt.executeUpdate();
			if(result>0){//字段映射模式改变，删除历史数据
				String delRPCHis="truncate table tbl_rpcacqdata_hist";
				String delPCPHis="truncate table tbl_pcpacqdata_hist";
				pstmt = conn.prepareStatement(delRPCHis);
				result=pstmt.executeUpdate();
				pstmt = conn.prepareStatement(delPCPHis);
				result=pstmt.executeUpdate();
			}
			
			Map<String,String> mappingTableRecordMap=new LinkedHashMap<String,String>();
			String sql="select t.name,t.mappingcolumn,t.protocoltype,t.mappingmode from tbl_datamapping t where t.protocoltype=0 order by t.id";
			if(rpcDeviceAcquisitionItemColumns!=null){
				//同步抽油机字段映射表
				pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					if(dataSaveMode==0){//以地址为准
						mappingTableRecordMap.put(rs.getString(2), rs.getString(1));//以地址为准
					}else{
						mappingTableRecordMap.put(rs.getString(1), rs.getString(2));//以名称为准
					}
				}
				
				//如驱动配置中不存在，删除记录
				for(String key : mappingTableRecordMap.keySet()) {
					if(!StringManagerUtils.existOrNot(rpcDeviceAcquisitionItemColumns,key,mappingTableRecordMap.get(key),false)){
						String deleteSql="";
						if(dataSaveMode==0){//以地址为准
							deleteSql="delete from tbl_datamapping t where t.name='"+mappingTableRecordMap.get(key)+"' and t.mappingcolumn='"+key+"' and t.protocoltype=0";
						}else{
							deleteSql="delete from tbl_datamapping t where t.name='"+key+"' and t.mappingcolumn='"+mappingTableRecordMap.get(key)+"' and t.protocoltype=0";
						}
						pstmt = conn.prepareStatement(deleteSql);
						pstmt.executeUpdate();
						result++;
					}
				}
				
				//如数据库中不存在，添加记录
				for(String key : rpcDeviceAcquisitionItemColumns.keySet()) {
					if(!StringManagerUtils.existOrNot(mappingTableRecordMap,key,rpcDeviceAcquisitionItemColumns.get(key),false)){
						String addSql="";
						if(dataSaveMode==0){//以地址为准
							addSql="insert into tbl_datamapping(name,mappingcolumn,protocoltype,mappingmode) values('"+rpcDeviceAcquisitionItemColumns.get(key)+"','"+key+"',0,"+dataSaveMode+")";
						}else{
							addSql="insert into tbl_datamapping(name,mappingcolumn,protocoltype,mappingmode) values('"+key+"','"+rpcDeviceAcquisitionItemColumns.get(key)+"',0,"+dataSaveMode+")";
						}
						pstmt = conn.prepareStatement(addSql);
						pstmt.executeUpdate();
						result++;
					}
				}
			}
			
			
			if(pcpDeviceAcquisitionItemColumns!=null){
				//同步螺杆泵字段映射表
				sql="select t.name,t.mappingcolumn,t.protocoltype,t.mappingmode from tbl_datamapping t where t.protocoltype=1 order by t.id";
				pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				mappingTableRecordMap=new HashMap<String,String>();
				while(rs.next()){
					if(dataSaveMode==0){//以地址为准
						mappingTableRecordMap.put(rs.getString(2), rs.getString(1));//以地址为准
					}else{
						mappingTableRecordMap.put(rs.getString(1), rs.getString(2));//以名称为准
					}
				}
				//如驱动配置中不存在，删除记录
				for(String key : mappingTableRecordMap.keySet()) {
					if(!StringManagerUtils.existOrNot(pcpDeviceAcquisitionItemColumns,key,mappingTableRecordMap.get(key),false)){
						String deleteSql="";
						if(dataSaveMode==0){//以地址为准
							deleteSql="delete from tbl_datamapping t where t.name='"+mappingTableRecordMap.get(key)+"' and t.mappingcolumn='"+key+"' and t.protocoltype=1";
						}else{
							deleteSql="delete from tbl_datamapping t where t.name='"+key+"' and t.mappingcolumn='"+mappingTableRecordMap.get(key)+"' and t.protocoltype=1";
						}
						pstmt = conn.prepareStatement(deleteSql);
						pstmt.executeUpdate();
						result++;
					}
				}
				//如数据库中不存在，添加记录
				for(String key : pcpDeviceAcquisitionItemColumns.keySet()) {
					if(!StringManagerUtils.existOrNot(mappingTableRecordMap,key,pcpDeviceAcquisitionItemColumns.get(key),false)){
						String addSql="";
						if(dataSaveMode==0){//以地址为准
							addSql="insert into tbl_datamapping(name,mappingcolumn,protocoltype,mappingmode) values('"+pcpDeviceAcquisitionItemColumns.get(key)+"','"+key+"',1,"+dataSaveMode+")";
						}else{
							addSql="insert into tbl_datamapping(name,mappingcolumn,protocoltype,mappingmode) values('"+key+"','"+pcpDeviceAcquisitionItemColumns.get(key)+"',1,"+dataSaveMode+")";
						}
						pstmt = conn.prepareStatement(addSql);
						pstmt.executeUpdate();
						result++;
					}
				}
			}
			
			MemoryDataManagerTask.loadProtocolMappingColumn();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		
		return result;
	}
	

	public static int loadAcquisitionItemColumns(){
		int dataSaveMode=1;
		if(dataSaveMode==0){
			loadAcquisitionItemAddrColumns();
		}else{
			loadAcquisitionItemNameColumns();
		}
		syncDataMappingTable();
		return 0;
	}
	
	public static int loadAcquisitionItemAddrColumns(){
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		
		Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
		Map<String,String> rpcDeviceAcquisitionItemColumns=new LinkedHashMap<String,String>();
		Map<String,String> pcpDeviceAcquisitionItemColumns=new LinkedHashMap<String,String>();
		
		for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
			if(modbusProtocolConfig.getProtocol().get(i).getDeviceType()==0){
				for(int j=0;j<modbusProtocolConfig.getProtocol().get(i).getItems().size();j++){
					if(!StringManagerUtils.existOrNot(rpcDeviceAcquisitionItemColumns, "ADDR"+modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getAddr(),false)){
						rpcDeviceAcquisitionItemColumns.put("ADDR"+modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getAddr(), modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle());
					}
				}
			}else{
				for(int j=0;j<modbusProtocolConfig.getProtocol().get(i).getItems().size();j++){
					if(!StringManagerUtils.existOrNot(pcpDeviceAcquisitionItemColumns, "ADDR"+modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getAddr(),false)){
						pcpDeviceAcquisitionItemColumns.put("ADDR"+modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getAddr(), modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle());
					}
				}
			}
		}
		acquisitionItemColumnsMap.put("rpcDeviceAcquisitionItemColumns", rpcDeviceAcquisitionItemColumns);
		acquisitionItemColumnsMap.put("pcpDeviceAcquisitionItemColumns", pcpDeviceAcquisitionItemColumns);
		return 0;
	}
	
	public static int loadAcquisitionItemNameColumns(){
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		
		Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
		Map<String,String> rpcDeviceAcquisitionItemColumns=new LinkedHashMap<String,String>();
		Map<String,String> pcpDeviceAcquisitionItemColumns=new LinkedHashMap<String,String>();
		
		for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
			if(modbusProtocolConfig.getProtocol().get(i).getDeviceType()==0){
				for(int j=0;j<modbusProtocolConfig.getProtocol().get(i).getItems().size();j++){
					if(!StringManagerUtils.existOrNot(rpcDeviceAcquisitionItemColumns, modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle(),false)){
						String itemName=modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle();
						String itemColumn="";
						if(!StringManagerUtils.existOrNotByValue(rpcDeviceAcquisitionItemColumns,StringManagerUtils.protocolItemNameToCol(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle()),false)){
							itemColumn=StringManagerUtils.protocolItemNameToCol(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle());
						}else{
							for(int index=1;1==1;index++){
								if(!StringManagerUtils.existOrNot(rpcDeviceAcquisitionItemColumns,StringManagerUtils.protocolItemNameToCol(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle()+index),false)){
									itemColumn=StringManagerUtils.protocolItemNameToCol(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle())+index;
									break;
								}
							}
						}
						rpcDeviceAcquisitionItemColumns.put(itemName, itemColumn);
					}else{
						
					}
				}
			}else{
				for(int j=0;j<modbusProtocolConfig.getProtocol().get(i).getItems().size();j++){
					if(!StringManagerUtils.existOrNot(pcpDeviceAcquisitionItemColumns, modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle(),false)){
						String itemName=modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle();
						String itemColumn="";
						if(!StringManagerUtils.existOrNotByValue(pcpDeviceAcquisitionItemColumns,StringManagerUtils.protocolItemNameToCol(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle()),false)){
							itemColumn=StringManagerUtils.protocolItemNameToCol(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle());
						}else{
							for(int index=1;1==1;index++){
								if(!StringManagerUtils.existOrNot(pcpDeviceAcquisitionItemColumns,StringManagerUtils.protocolItemNameToCol(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle()+index),false)){
									itemColumn=StringManagerUtils.protocolItemNameToCol(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle())+index;
									break;
								}
							}
						}
						pcpDeviceAcquisitionItemColumns.put(itemName, itemColumn);
					}else{
						
					}
				}
			}
		}
		acquisitionItemColumnsMap.put("rpcDeviceAcquisitionItemColumns", rpcDeviceAcquisitionItemColumns);
		acquisitionItemColumnsMap.put("pcpDeviceAcquisitionItemColumns", pcpDeviceAcquisitionItemColumns);
		return 0;
	}
	
	public static int loadAcquisitionItemColumns(int deviceType){
		int dataSaveMode=1;
		if(dataSaveMode==0){
			loadAcquisitionItemAddrColumns(deviceType);
		}else{
			loadAcquisitionItemNameColumns(deviceType);
		}
		syncDataMappingTable();
		return 0;
	}
	
	public static int loadAcquisitionItemAddrColumns(int deviceType){
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		
		Collections.sort(modbusProtocolConfig.getProtocol());
		
		Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
		Map<String,String> acquisitionItemColumns=new LinkedHashMap<String,String>();
		
		for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
			if(modbusProtocolConfig.getProtocol().get(i).getDeviceType()==deviceType){
				for(int j=0;j<modbusProtocolConfig.getProtocol().get(i).getItems().size();j++){
					if(!StringManagerUtils.existOrNot(acquisitionItemColumns, "ADDR"+modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getAddr(),false)){
						acquisitionItemColumns.put("ADDR"+modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getAddr(), modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle());
					}
				}
			}
			if(deviceType==0){
				acquisitionItemColumnsMap.put("rpcDeviceAcquisitionItemColumns", acquisitionItemColumns);
			}else{
				acquisitionItemColumnsMap.put("pcpDeviceAcquisitionItemColumns", acquisitionItemColumns);
			}
		}
		return 0;
	}
	
	public static int loadAcquisitionItemNameColumns(int deviceType){
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		
		Collections.sort(modbusProtocolConfig.getProtocol());
		
		Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
		Map<String,String> acquisitionItemColumns=new LinkedHashMap<String,String>();
		
		for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
			if(modbusProtocolConfig.getProtocol().get(i).getDeviceType()==deviceType){
				for(int j=0;j<modbusProtocolConfig.getProtocol().get(i).getItems().size();j++){
					if(!StringManagerUtils.existOrNot(acquisitionItemColumns, modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle(),false)){
						String itemName=modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle();
						String itemColumn="";
						String mappingColumn=StringManagerUtils.protocolItemNameToCol(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle());
						if((!StringManagerUtils.existOrNotByValue(acquisitionItemColumns,mappingColumn,false))&&(!StringManagerUtils.databaseColumnFiter(mappingColumn))){
							itemColumn=mappingColumn;
						}else{
							for(int index=1;1==1;index++){
								if((!StringManagerUtils.existOrNot(acquisitionItemColumns,mappingColumn+index,false))&&(!StringManagerUtils.databaseColumnFiter(mappingColumn+index))){
									itemColumn=mappingColumn+index;
									break;
								}
							}
						}
						acquisitionItemColumns.put(itemName, itemColumn);
					}else{
						
					}
				}
			}
			if(deviceType==0){
				acquisitionItemColumnsMap.put("rpcDeviceAcquisitionItemColumns", acquisitionItemColumns);
			}else{
				acquisitionItemColumnsMap.put("pcpDeviceAcquisitionItemColumns", acquisitionItemColumns);
			}
		}
		return 0;
	}
	
	public static int initAcquisitionItemDataBaseColumns(){
		int result=initAcquisitionItemDataBaseColumns("tbl_rpcacqdata_hist",0);
		result=initAcquisitionItemDataBaseColumns("tbl_rpcacqdata_latest",0);
		result=initAcquisitionItemDataBaseColumns("tbl_pcpacqdata_hist",1);
		result=initAcquisitionItemDataBaseColumns("tbl_pcpacqdata_latest",1);
		return result;
	}
	
	public static int initAcquisitionItemDataBaseColumns(String tableName,int deviceType){
		//rpcDeviceAcquisitionItemColumns  pcpDeviceAcquisitionItemColumns
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		String fiterColumnsArr[]={
				"ID"
		};
		int result=0;
		int dataSaveMode=1;
		String columnsKey="rpcDeviceAcquisitionItemColumns";
		if(deviceType==1){
			columnsKey="pcpDeviceAcquisitionItemColumns";
		}
		Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
		if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
			loadAcquisitionItemColumns(deviceType);
		}
		Map<String,String> acquisitionItemColumns=acquisitionItemColumnsMap.get(columnsKey);
		List<String> acquisitionItemDataBaseColumns=new ArrayList<String>();
		String sql="select t.COLUMN_NAME from user_tab_cols t where t.TABLE_NAME=UPPER('"+tableName+"') "
				//+ " and UPPER(t.COLUMN_NAME) not in('ID','WELLID','ACQTIME','COMMSTATUS','COMMTIME','COMMTIMEEFFICIENCY','COMMRANGE','RUNSTATUS','RUNTIMEEFFICIENCY','RUNTIME','RUNRANGE')  "
				+ " order by t.COLUMN_ID";
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return -1;
        }
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				String columnName=rs.getString(1);
				if((!StringManagerUtils.databaseColumnFiter(columnName)) && columnName.toUpperCase().startsWith("C_")){
					acquisitionItemDataBaseColumns.add(columnName);
				}
			}
			//如驱动配置中不存在，删除字段
			for(int i=0;i<acquisitionItemDataBaseColumns.size();i++){
				if(dataSaveMode==0){
					if(!StringManagerUtils.existOrNot(acquisitionItemColumns,acquisitionItemDataBaseColumns.get(i),false)){
						String deleteColumsSql="alter table "+tableName+" drop column "+acquisitionItemDataBaseColumns.get(i);
						pstmt = conn.prepareStatement(deleteColumsSql);
						pstmt.executeUpdate();
						result++;
						StringManagerUtils.printLog(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":表"+tableName+"删除字段:"+acquisitionItemDataBaseColumns.get(i));
					}
				}else{
					if(!StringManagerUtils.existOrNotByValue(acquisitionItemColumns,acquisitionItemDataBaseColumns.get(i),false)){
						String deleteColumsSql="alter table "+tableName+" drop column "+acquisitionItemDataBaseColumns.get(i);
						pstmt = conn.prepareStatement(deleteColumsSql);
						pstmt.executeUpdate();
						result++;
						StringManagerUtils.printLog(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":表"+tableName+"删除字段:"+acquisitionItemDataBaseColumns.get(i));
					}
				}
			}
			//如数据库中不存在，添加字段
			for(String key : acquisitionItemColumns.keySet()) {
				if(!StringManagerUtils.existOrNot(acquisitionItemDataBaseColumns,dataSaveMode==0?key:acquisitionItemColumns.get(key),false)){
					String addColumsSql="alter table "+tableName+" add "+(dataSaveMode==0?key:acquisitionItemColumns.get(key))+" VARCHAR2(4000)";
					pstmt = conn.prepareStatement(addColumsSql);
					pstmt.executeUpdate();
					StringManagerUtils.printLog(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":表"+tableName+"添加字段:"+(dataSaveMode==0?key:acquisitionItemColumns.get(key)));
					result++;
				}
			}
			StringManagerUtils.printLog(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"-"+tableName+"同步数据库字段");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		
		return result;
	}
	
	public static int initDataDictionary(String dataDictionaryId,int deviceType){
		int result=0;
		int dataSaveMode=1;
		String columnsKey="rpcDeviceAcquisitionItemColumns";
		if(deviceType==1){
			columnsKey="pcpDeviceAcquisitionItemColumns";
		}
		Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
		if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
			loadAcquisitionItemColumns(deviceType);
		}
		Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
		
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		
		Collections.sort(modbusProtocolConfig.getProtocol());
		List<String> acquisitionItemColumns=new ArrayList<String>();
		List<String> acquisitionItemsName=new ArrayList<String>();
		List<String> dataTypeList=new ArrayList<String>();
		for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
			if(modbusProtocolConfig.getProtocol().get(i).getDeviceType()==deviceType){
				for(int j=0;j<modbusProtocolConfig.getProtocol().get(i).getItems().size();j++){
					String col=dataSaveMode==0?("addr"+modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getAddr()):(loadedAcquisitionItemColumnsMap.get(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle()));
					if((!"w".equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getRWType()))//非只写
						&&(!StringManagerUtils.existOrNot(acquisitionItemColumns, col,false))){
						String unit=modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getUnit();
						acquisitionItemColumns.add(col);
						acquisitionItemsName.add(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle()+(StringManagerUtils.isNotNull(unit)?("("+unit+")"):""));
						dataTypeList.add(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getIFDataType());
					}
				}
//				break;
			}
		}
		List<String> dataDictionaryItems=new ArrayList<String>();
		List<String> dataDictionaryItemsName=new ArrayList<String>();
		List<String> dataDictionaryItemsId=new ArrayList<String>();
		List<Integer> dataDictionaryItemsSortList=new ArrayList<Integer>();
		String sql="select t1.dataitemid,t1.cname,t1.ename,t1.sorts "
				+ " from tbl_dist_item t1 where t1.sysdataid=(select t2.sysdataid from tbl_dist_name t2 where t2.sysdataid='"+dataDictionaryId+"') "
//				+ " and UPPER(t1.cname) not in('序号','井名','通信状态','采集时间','设备类型')  "
				+ " order by t1.sorts";
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return -1;
        }
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			int maxSortNum=1;
			while(rs.next()){
				if((!StringManagerUtils.databaseColumnFiter(rs.getString(3))) && rs.getString(3).toUpperCase().startsWith("C_")){
					dataDictionaryItemsId.add(rs.getString(1));
					dataDictionaryItemsName.add(rs.getString(2));
					dataDictionaryItems.add(rs.getString(3));
					dataDictionaryItemsSortList.add(rs.getInt(4));
				}
			}
			
			//删除协议中不存在的字典项
			StringBuffer delItemIds = new StringBuffer();
			for(int i=0;i<dataDictionaryItems.size();i++){
				if(!StringManagerUtils.existOrNot(acquisitionItemColumns,dataDictionaryItems.get(i),false)){
					delItemIds.append("'"+dataDictionaryItemsId.get(i)+"',");
					dataDictionaryItemsSortList.set(i, 0);
				}
			}
			if(delItemIds.toString().endsWith(",")){
				delItemIds.deleteCharAt(delItemIds.length() - 1);
			}
			if(StringManagerUtils.isNotNull(delItemIds.toString())){
				String delDataDict="delete from tbl_dist_item t where t.dataitemid in("+delItemIds.toString()+")";
				pstmt = conn.prepareStatement(delDataDict);
				pstmt.executeUpdate();
				StringManagerUtils.printLog("删除协议中不存在的字典项:"+delItemIds.toString());
			}
			for(int i=0;i<dataDictionaryItemsSortList.size();i++){
				if(dataDictionaryItemsSortList.get(i)>maxSortNum){
					maxSortNum=dataDictionaryItemsSortList.get(i);
				}
			}
			if(maxSortNum<5){
				maxSortNum=5;
			}
			
			//如数字典中不存在，添加字典项
			for(int i=0;i<acquisitionItemColumns.size();i++){
				if(!StringManagerUtils.existOrNot(dataDictionaryItems,acquisitionItemColumns.get(i),false)){
					maxSortNum+=1;
					int status=0;
					if(dataTypeList.get(i).toUpperCase().contains("FLOAT")){
						status=1;
					}
					String addDataDict="insert into tbl_dist_item(sysdataid,cname,ename,sorts,status)"
							+ " values('"+dataDictionaryId+"','"+acquisitionItemsName.get(i)+"','"+acquisitionItemColumns.get(i)+"',"+(maxSortNum)+","+status+")";
					pstmt = conn.prepareStatement(addDataDict);
					pstmt.executeUpdate();
					result++;
				}else{//如果存在，判断名称是否改变
					String tiemColumn=acquisitionItemColumns.get(i);
					String itemName=acquisitionItemsName.get(i);
					for(int j=0;j<dataDictionaryItems.size();j++){
						if(tiemColumn.equalsIgnoreCase(dataDictionaryItems.get(j))){
							if(!itemName.equalsIgnoreCase(dataDictionaryItemsName.get(j))){//如果名称改变
								String addDataDict="update tbl_dist_item t set t.cname='"+itemName+"' where t.dataitemid='"+dataDictionaryItemsId.get(j)+"'";
								pstmt = conn.prepareStatement(addDataDict);
								pstmt.executeUpdate();
							}
							break;
						}
					}
				}
			}
			
			
			StringManagerUtils.printLog("同步数据字典:"+dataDictionaryId);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return result;
	}
	
	public static void initProtocolConfig(String protocolName,String method){
		if(!StringManagerUtils.isNotNull(method)){
			method="update";
		}
		String initUrl=Config.getInstance().configFile.getAd().getProtocol();
		Gson gson = new Gson();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		InitProtocol initProtocol=null;
		if(modbusProtocolConfig!=null){
			if("delete".equalsIgnoreCase(method)){
				initProtocol=new InitProtocol();
				initProtocol.setProtocolName(protocolName);
				initProtocol.setMethod(method);
				StringManagerUtils.printLog("删除协议："+gson.toJson(initProtocol));
				StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initProtocol),"utf-8",0,0);
			}else{
				if(StringManagerUtils.isNotNull(protocolName)){
					for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
						if(protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getName())){
							initProtocol=new InitProtocol(modbusProtocolConfig.getProtocol().get(i));
							initProtocol.setMethod(method);
							StringManagerUtils.printLog("协议初始化："+gson.toJson(initProtocol));
							StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initProtocol),"utf-8",0,0);
							break;
						}
					}
				}else{
					for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
						initProtocol=new InitProtocol(modbusProtocolConfig.getProtocol().get(i));
						initProtocol.setMethod(method);
						StringManagerUtils.printLog("协议初始化："+gson.toJson(initProtocol));
						StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initProtocol),"utf-8",0,0);
					}
				}
			}
			loadAcquisitionItemColumns();
			//同步数据库字段
			initAcquisitionItemDataBaseColumns();
			//同步数据字典
			initDataDictionary("7f13446d19b4497986980fa16a750f95",0);//抽油机实时概览字典
			initDataDictionary("cd7b24562b924d19b556de31256e22a1",0);//抽油机历史查询字典
			initDataDictionary("e0f5f3ff8a1f46678c284fba9cc113e8",1);//螺杆泵实时概览字典
			initDataDictionary("fb7d070a349c403b8a26d71c12af7a05",1);//螺杆泵历史查询字典
		}
	}
	
	public static int initInstanceConfigByProtocolName(String protocolName,String method){
		String sql="select t.name from tbl_protocolinstance t,tbl_acq_unit_conf t2 where t.unitid=t2.id and t2.protocol='"+protocolName+"'";
		List<String> instanceList=new ArrayList<String>();
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return -1;
        }
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				instanceList.add(rs.getString(1));
			}
			if(instanceList.size()>0){
				initInstanceConfig(instanceList,method);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return 0;
	}
	
	public static int initInstanceConfigByAcqUnitName(String unitName,String method){
		String sql="select t.name from tbl_protocolinstance t,tbl_acq_unit_conf t2 where t.unitid=t2.id and t2.unit_name='"+unitName+"'";
		List<String> instanceList=new ArrayList<String>();
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return -1;
        }
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				instanceList.add(rs.getString(1));
			}
			if(instanceList.size()>0){
				initInstanceConfig(instanceList,method);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return 0;
	}
	
	public static int initInstanceConfigByAcqUnitId(String unitId,String method){
		String sql="select t.name from tbl_protocolinstance t where t.unitid="+unitId;
		List<String> instanceList=new ArrayList<String>();
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return -1;
        }
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				instanceList.add(rs.getString(1));
			}
			if(instanceList.size()>0){
				initInstanceConfig(instanceList,method);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return 0;
	}
	
	public static int initInstanceConfigByAcqGroupName(String groupName,String method){
		String sql="select distinct(t.name) from tbl_protocolinstance t,tbl_acq_unit_conf t2,tbl_acq_group2unit_conf t3,tbl_acq_group_conf t4 "
				+ " where t.unitid=t2.id and t2.id=t3.unitid and t3.groupid=t4.id and t4.group_name='"+groupName+"'";
		List<String> instanceList=new ArrayList<String>();
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return -1;
        }
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				instanceList.add(rs.getString(1));
			}
			if(instanceList.size()>0){
				initInstanceConfig(instanceList,method);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return 0;
	}
	
	public static int initInstanceConfigByAcqGroupId(String groupId,String method){
		String sql="select distinct(t.name) from tbl_protocolinstance t,tbl_acq_unit_conf t2,tbl_acq_group2unit_conf t3,tbl_acq_group_conf t4 where t.unitid=t2.id and t2.id=t3.unitid and t3.groupid=t4.id and t4.id="+groupId;
		List<String> instanceList=new ArrayList<String>();
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return -1;
        }
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				instanceList.add(rs.getString(1));
			}
			if(instanceList.size()>0){
				initInstanceConfig(instanceList,method);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return 0;
	}
	
	
	public static int initInstanceConfig(List<String> instanceList,String method){
		String initUrl=Config.getInstance().configFile.getAd().getInstance();
		Gson gson = new Gson();
		int result=0;
		String instances=StringManagerUtils.joinStringArr2(instanceList, ",");
		if(!StringManagerUtils.isNotNull(method)){
			method="update";
		}
		
		if("delete".equalsIgnoreCase(method)){
			for(int i=0;instanceList!=null&&i<instanceList.size();i++){
				InitInstance initInstance=new InitInstance();
				initInstance.setInstanceName(instanceList.get(i));
				initInstance.setMethod(method);
				StringManagerUtils.printLog("删除实例："+gson.toJson(initInstance));
				StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initInstance),"utf-8",0,0);
			}
		}else{
			String sql="select t.name,t.acqprotocoltype,t.ctrlprotocoltype,"
					+ "t.signinprefix,t.signinsuffix,t.heartbeatprefix,t.heartbeatsuffix,"
					+ "t.packetsendinterval,"
					+ "t2.protocol,t2.unit_code,t4.group_code,t4.grouptiminginterval,t4.type,"
					+ "listagg(t5.itemname, ',') within group(order by t5.id ) key "
					+ " from tbl_protocolinstance t "
					+ " left outer join tbl_acq_unit_conf t2 on t.unitid=t2.id "
					+ " left outer join tbl_acq_group2unit_conf t3 on t2.id=t3.unitid "
					+ " left outer join tbl_acq_group_conf t4 on t3.groupid=t4.id "
					+ " left outer join tbl_acq_item2group_conf t5 on t4.id=t5.groupid  "
					+ " where 1=1 ";
			if(StringManagerUtils.isNotNull(instances)){
				sql+=" and t.name in("+instances+")";
			}
			sql+= "group by t.name,t.acqprotocoltype,t.ctrlprotocoltype,t.signinprefix,t.signinsuffix,t.heartbeatprefix,t.heartbeatsuffix,t.packetsendinterval,"
					+ "t2.protocol,t2.unit_code,t4.group_code,t4.grouptiminginterval,t4.type";
			Map<String,InitInstance> InstanceListMap=new HashMap<String,InitInstance>();
			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
			Connection conn = null;   
			PreparedStatement pstmt = null;   
			ResultSet rs = null;
			conn=OracleJdbcUtis.getConnection();
			if(conn==null || modbusProtocolConfig==null){
	        	return -1;
	        }
			try {
				pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					InitInstance initInstance=InstanceListMap.get(rs.getString(1));
					if(initInstance==null){
						initInstance=new InitInstance();
						initInstance.setMethod(method);
						initInstance.setInstanceName(rs.getString(1));
						initInstance.setProtocolName(rs.getString(9));
						initInstance.setAcqProtocolType(rs.getString(2));
						initInstance.setCtrlProtocolType(rs.getString(3));
						
						initInstance.setPrefixSuffixHex(true);
						
						initInstance.setSignInPrefix(rs.getString(4)==null?"":rs.getString(4));
						initInstance.setSignInSuffix(rs.getString(5)==null?"":rs.getString(5));
						
						initInstance.setHeartbeatPrefix(rs.getString(6)==null?"":rs.getString(6));
						initInstance.setHeartbeatSuffix(rs.getString(7)==null?"":rs.getString(7));
						
						initInstance.setPacketSendInterval(rs.getInt(8));
						
						initInstance.setAcqGroup(new ArrayList<InitInstance.Group>());
						initInstance.setCtrlGroup(new ArrayList<InitInstance.Group>());
					}
					if(StringManagerUtils.isNotNull(rs.getString(11))){
						InitInstance.Group group=new InitInstance.Group();
						group.setGroupTimingInterval(rs.getInt(12));
						group.setAddr(new ArrayList<Integer>());
						int groupType=rs.getInt(13);
						if(StringManagerUtils.isNotNull(rs.getString(14))){
							String[] itemsArr=rs.getString(14).split(",");
							for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
								if(modbusProtocolConfig.getProtocol().get(i).getName().equalsIgnoreCase(rs.getString(9))){
									for(int j=0;j<itemsArr.length;j++){
										for(int k=0;k<modbusProtocolConfig.getProtocol().get(i).getItems().size();k++){
											if(itemsArr[j].equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle())){
												if(!StringManagerUtils.existOrNot(group.getAddr(), modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getAddr())){
													String rwType=modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getRWType();
													if((groupType==1&&(!"r".equalsIgnoreCase(rwType))) || (groupType==0&&(!"w".equalsIgnoreCase(rwType)))){
														group.getAddr().add(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getAddr());
													}
												}
												break;
											}
										}
									}
									Collections.sort(group.getAddr());
									break;
								}
							}
						}
						if(groupType==1){//控制组
							initInstance.getCtrlGroup().add(group);
						}else{
							initInstance.getAcqGroup().add(group);
						}
					}
					InstanceListMap.put(rs.getString(1), initInstance);
				}
				result=InstanceListMap.size();
				for(Entry<String, InitInstance> entry:InstanceListMap.entrySet()){
					try {
						StringManagerUtils.printLog("实例初始化："+gson.toJson(entry.getValue()));
						StringManagerUtils.sendPostMethod(initUrl, gson.toJson(entry.getValue()),"utf-8",0,0);
					}catch (Exception e) {
						continue;
					}
				}
			} catch (SQLException e) {
				StringManagerUtils.printLog("ID初始化sql："+sql);
				e.printStackTrace();
			} finally{
				OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
			}
		}
		return result;
	}
	
	//初始化短信实例
	public static int initSMSInstanceConfig(List<String> instanceList,String method){
		String initUrl=Config.getInstance().configFile.getAd().getInstance();
		Gson gson = new Gson();
		int result=0;
		String instances=StringManagerUtils.joinStringArr2(instanceList, ",");
		if(!StringManagerUtils.isNotNull(method)){
			method="update";
		}
		
		if("delete".equalsIgnoreCase(method)){
			for(int i=0;instanceList!=null&&i<instanceList.size();i++){
				InitInstance initInstance=new InitInstance();
				initInstance.setInstanceName(instanceList.get(i));
				initInstance.setMethod(method);
				StringManagerUtils.printLog("删除短信实例："+gson.toJson(initInstance));
				StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initInstance),"utf-8",0,0);
			}
		}else{
			String sql="select t.id,t.name,t.code,t.acqprotocoltype,t.ctrlprotocoltype,t.sort from tbl_protocolsmsinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(instances)){
				sql+=" and t.name in("+instances+")";
			}
			sql+= " order by t.sort";
			Connection conn = null;   
			PreparedStatement pstmt = null;   
			ResultSet rs = null;
			conn=OracleJdbcUtis.getConnection();
			if(conn==null){
	        	return -1;
	        }
			try {
				pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					InitInstance initInstance=new InitInstance();
					initInstance.setMethod(method);
					initInstance.setInstanceName(rs.getString(2));
					initInstance.setAcqProtocolType(rs.getString(4));
					initInstance.setCtrlProtocolType(rs.getString(5));
					StringManagerUtils.printLog("短信实例初始化："+gson.toJson(initInstance));
					StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initInstance),"utf-8",0,0);
				}
				
			} catch (SQLException e) {
				StringManagerUtils.printLog("ID短信实例初始化sql："+sql);
				e.printStackTrace();
			} finally{
				OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
			}
		}
		return result;
	}
	
	public static int initRPCDriverAcquisitionInfoConfig(List<String> wellList,String method){
		String initUrl=Config.getInstance().configFile.getAd().getId();
		Gson gson = new Gson();
		int result=0;
		
		Map<String, Object> dataModelMap = DataModelMap.getMapObject();
		Map<String,InitializedDeviceInfo> initializedDeviceList=(Map<String,InitializedDeviceInfo>) dataModelMap.get("InitializedDeviceList");
		if(initializedDeviceList==null){
			initializedDeviceList=new HashMap<String,InitializedDeviceInfo>();
		}
		
		String wellName=StringManagerUtils.joinStringArr2(wellList, ",");
		if(!StringManagerUtils.isNotNull(method)){
			method="update";
		}
		String sql="select t.wellname,t.signinid,t.slave,t2.name,t.devicetype,t.id,t.orgid,t.status "
				+ " from tbl_rpcdevice t,tbl_protocolinstance t2 "
				+ " where t.instancecode=t2.code ";
//		if("update".equalsIgnoreCase(method)){
//			sql+= " and t.signinid is not null and t.slave is not null and t.status=1";
//		}	
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and t.wellname in("+wellName+")";
		}
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null ){
        	return -1;
        }
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				InitializedDeviceInfo initialized=initializedDeviceList.get(0+"_"+rs.getInt(6));
				int status=rs.getInt(8);
				if("update".equalsIgnoreCase(method)&&status==1){
					if(initialized==null&&StringManagerUtils.isNotNull(rs.getString(2))&& rs.getInt(3)>0 &&StringManagerUtils.isNotNull(rs.getString(4))){//如果未初始化
						InitId initId=new InitId();
						initId.setMethod("update");
						initId.setID(rs.getString(2));
						initId.setSlave((byte) rs.getInt(3));
						initId.setInstanceName(rs.getString(4));
						StringManagerUtils.printLog("抽油机ID初始化："+gson.toJson(initId));
						String response=StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
						if(StringManagerUtils.isNotNull(response)){
							InitializedDeviceInfo initializedDeviceInfo=new InitializedDeviceInfo(rs.getInt(7),rs.getInt(6),rs.getString(1),rs.getInt(5),rs.getString(2),(byte) rs.getInt(3),rs.getString(4));
							initializedDeviceList.put(0+"_"+initializedDeviceInfo.getDeviceId(), initializedDeviceInfo);
						}
					}else if(initialized!=null){
						//如果已经初始化但注册包ID、设备从地址、实例有一项为空，删除设备
						if( (!StringManagerUtils.isNotNull(rs.getString(2))) || rs.getInt(3)==0 || (!StringManagerUtils.isNotNull(rs.getString(4))) ){
							InitId initId=new InitId();
							initId.setMethod("delete");
							initId.setID(initialized.getSigninid());
							initId.setSlave(initialized.getSlave());
							initId.setInstanceName(initialized.getInstanceName());
							StringManagerUtils.printLog("抽油机ID初始化："+gson.toJson(initId));
							String response=StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
							if(StringManagerUtils.isNotNull(response)){
								initializedDeviceList.remove(0+"_"+rs.getInt(6));
							}
						}
						//如果已经初始化但信息有变化
						else if(! (initialized.getSigninid().equalsIgnoreCase(rs.getString(2))&&initialized.getSlave()==(byte) rs.getInt(3)&& initialized.getInstanceName().equalsIgnoreCase(rs.getString(4)))   ){
							//删掉原有初始化
							InitId initId=new InitId();
							initId.setMethod("delete");
							initId.setID(initialized.getSigninid());
							initId.setSlave(initialized.getSlave());
							initId.setInstanceName(initialized.getInstanceName());
							StringManagerUtils.printLog("抽油机ID初始化："+gson.toJson(initId));
							StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
							//重新初始化
							initId.setMethod("update");
							initId.setID(rs.getString(2));
							initId.setSlave((byte) rs.getInt(3));
							initId.setInstanceName(rs.getString(4));
							StringManagerUtils.printLog("抽油机ID初始化："+gson.toJson(initId));
							String response=StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
							if(StringManagerUtils.isNotNull(response)){
								InitializedDeviceInfo initializedDeviceInfo=new InitializedDeviceInfo(rs.getInt(7),rs.getInt(6),rs.getString(1),rs.getInt(5),rs.getString(2),(byte) rs.getInt(3),rs.getString(4));
								initializedDeviceList.put(0+"_"+initializedDeviceInfo.getDeviceId(), initializedDeviceInfo);
							}
						}
					}
				}else{
					if(initialized!=null){
						//删掉原有初始化
						InitId initId=new InitId();
						initId.setMethod("delete");
						initId.setID(initialized.getSigninid());
						initId.setSlave(initialized.getSlave());
						initId.setInstanceName(initialized.getInstanceName());
						StringManagerUtils.printLog("抽油机ID初始化："+gson.toJson(initId));
						StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
						initializedDeviceList.remove(0+"_"+rs.getInt(6));
					}
				}
			}
			dataModelMap.put("InitializedDeviceList", initializedDeviceList);
		} catch (SQLException e) {
			StringManagerUtils.printLog("ID初始化sql："+sql);
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return result;
	}
	
	public static int initRPCDriverAcquisitionInfoConfigById(List<String> wellIdList,String method){
		String initUrl=Config.getInstance().configFile.getAd().getId();
		Gson gson = new Gson();
		int result=0;
		
		Map<String, Object> dataModelMap = DataModelMap.getMapObject();
		Map<String,InitializedDeviceInfo> initializedDeviceList=(Map<String,InitializedDeviceInfo>) dataModelMap.get("InitializedDeviceList");
		if(initializedDeviceList==null){
			initializedDeviceList=new HashMap<String,InitializedDeviceInfo>();
		}
		
		String wellId=StringUtils.join(wellIdList, ",");
		if(!StringManagerUtils.isNotNull(method)){
			method="update";
		}
		String sql="select t.wellname,t.signinid,t.slave,t2.name,t.devicetype,t.id,t.orgid,t.status "
				+ " from tbl_rpcdevice t left outer join tbl_protocolinstance t2  on t.instancecode=t2.code "
				+ " where 1=1 ";
//		if("update".equalsIgnoreCase(method)){
//			sql+= " and t.signinid is not null and t.slave is not null and t.status=1";
//		}	
		if(StringManagerUtils.isNotNull(wellId)){
			sql+=" and t.id in("+wellId+")";
		}
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null ){
        	return -1;
        }
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				InitializedDeviceInfo initialized=initializedDeviceList.get(0+"_"+rs.getInt(6));
				int status=rs.getInt(8);
				if("update".equalsIgnoreCase(method)&&status==1){
					if(initialized==null&&StringManagerUtils.isNotNull(rs.getString(2))&& rs.getInt(3)>0 &&StringManagerUtils.isNotNull(rs.getString(4))){//如果未初始化
						InitId initId=new InitId();
						initId.setMethod("update");
						initId.setID(rs.getString(2));
						initId.setSlave((byte) rs.getInt(3));
						initId.setInstanceName(rs.getString(4));
						StringManagerUtils.printLog("抽油机ID初始化："+gson.toJson(initId));
						String response=StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
						if(StringManagerUtils.isNotNull(response)){
							InitializedDeviceInfo initializedDeviceInfo=new InitializedDeviceInfo(rs.getInt(7),rs.getInt(6),rs.getString(1),rs.getInt(5),rs.getString(2),(byte) rs.getInt(3),rs.getString(4));
							initializedDeviceList.put(0+"_"+initializedDeviceInfo.getDeviceId(), initializedDeviceInfo);
						}
					}else if(initialized!=null){
						//如果已经初始化但注册包ID、设备从地址、实例有一项为空，删除设备
						if( (!StringManagerUtils.isNotNull(rs.getString(2))) || rs.getInt(3)==0 || (!StringManagerUtils.isNotNull(rs.getString(4))) ){
							InitId initId=new InitId();
							initId.setMethod("delete");
							initId.setID(initialized.getSigninid());
							initId.setSlave(initialized.getSlave());
							initId.setInstanceName(initialized.getInstanceName());
							StringManagerUtils.printLog("抽油机ID初始化："+gson.toJson(initId));
							String response=StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
							if(StringManagerUtils.isNotNull(response)){
								initializedDeviceList.remove(0+"_"+rs.getInt(6));
							}
						}
						//如果已经初始化但信息有变化
						else if(! (initialized.getSigninid().equalsIgnoreCase(rs.getString(2))&&initialized.getSlave()==(byte) rs.getInt(3)&& initialized.getInstanceName().equalsIgnoreCase(rs.getString(4)))   ){
							//删掉原有初始化
							InitId initId=new InitId();
							initId.setMethod("delete");
							initId.setID(initialized.getSigninid());
							initId.setSlave(initialized.getSlave());
							initId.setInstanceName(initialized.getInstanceName());
							StringManagerUtils.printLog("抽油机ID初始化："+gson.toJson(initId));
							StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
							//重新初始化
							initId.setMethod("update");
							initId.setID(rs.getString(2));
							initId.setSlave((byte) rs.getInt(3));
							initId.setInstanceName(rs.getString(4));
							StringManagerUtils.printLog("抽油机ID初始化："+gson.toJson(initId));
							String response=StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
							if(StringManagerUtils.isNotNull(response)){
								InitializedDeviceInfo initializedDeviceInfo=new InitializedDeviceInfo(rs.getInt(7),rs.getInt(6),rs.getString(1),rs.getInt(5),rs.getString(2),(byte) rs.getInt(3),rs.getString(4));
								initializedDeviceList.put(0+"_"+initializedDeviceInfo.getDeviceId(), initializedDeviceInfo);
							}
						}
					}
				}else{
					if(initialized!=null){
						//删掉原有初始化
						InitId initId=new InitId();
						initId.setMethod("delete");
						initId.setID(initialized.getSigninid());
						initId.setSlave(initialized.getSlave());
						initId.setInstanceName(initialized.getInstanceName());
						StringManagerUtils.printLog("抽油机ID初始化："+gson.toJson(initId));
						StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
						initializedDeviceList.remove(0+"_"+rs.getInt(6));
					}
				}
			}
			dataModelMap.put("InitializedDeviceList", initializedDeviceList);
		} catch (SQLException e) {
			StringManagerUtils.printLog("ID初始化sql："+sql);
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return result;
	}
	
	public static int initPCPDriverAcquisitionInfoConfig(List<String> wellList,String method){
		String initUrl=Config.getInstance().configFile.getAd().getId();
		Gson gson = new Gson();
		int result=0;
		
		Map<String, Object> dataModelMap = DataModelMap.getMapObject();
		Map<String,InitializedDeviceInfo> initializedDeviceList=(Map<String,InitializedDeviceInfo>) dataModelMap.get("InitializedDeviceList");
		if(initializedDeviceList==null){
			initializedDeviceList=new HashMap<String,InitializedDeviceInfo>();
		}
		
		String wellName=StringManagerUtils.joinStringArr2(wellList, ",");
		if(!StringManagerUtils.isNotNull(method)){
			method="update";
		}
		String sql="select t.wellname,t.signinid,t.slave,t2.name,t.devicetype,t.id,t.orgid,status "
				+ " from tbl_pcpdevice t,tbl_protocolinstance t2 "
				+ " where t.instancecode=t2.code ";
//		if("update".equalsIgnoreCase(method)){
//			sql+= " and t.signinid is not null and t.slave is not null and t.status=1";
//		}	
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and t.wellname in("+wellName+")";
		}
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null ){
        	return -1;
        }
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				InitializedDeviceInfo initialized=initializedDeviceList.get(1+"_"+rs.getInt(6));
				int status=rs.getInt(8);
				if("update".equalsIgnoreCase(method)&&status==1){
					if(initialized==null&&StringManagerUtils.isNotNull(rs.getString(2))&& rs.getInt(3)>0 &&StringManagerUtils.isNotNull(rs.getString(4))){//如果未初始化
						InitId initId=new InitId();
						initId.setMethod("update");
						initId.setID(rs.getString(2));
						initId.setSlave((byte) rs.getInt(3));
						initId.setInstanceName(rs.getString(4));
						StringManagerUtils.printLog("螺杆泵ID初始化："+gson.toJson(initId));
						String response=StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
						if(StringManagerUtils.isNotNull(response)){
							InitializedDeviceInfo initializedDeviceInfo=new InitializedDeviceInfo(rs.getInt(7),rs.getInt(6),rs.getString(1),rs.getInt(5),rs.getString(2),(byte) rs.getInt(3),rs.getString(4));
							initializedDeviceList.put(1+"_"+initializedDeviceInfo.getDeviceId(), initializedDeviceInfo);
						}
					}else if(initialized!=null){
						//如果已经初始化但注册包ID、设备从地址、实例有一项为空，删除设备
						if( (!StringManagerUtils.isNotNull(rs.getString(2))) || rs.getInt(3)==0 || (!StringManagerUtils.isNotNull(rs.getString(4))) ){
							InitId initId=new InitId();
							initId.setMethod("delete");
							initId.setID(initialized.getSigninid());
							initId.setSlave(initialized.getSlave());
							initId.setInstanceName(initialized.getInstanceName());
							StringManagerUtils.printLog("螺杆泵ID初始化："+gson.toJson(initId));
							String response=StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
							if(StringManagerUtils.isNotNull(response)){
								initializedDeviceList.remove(1+"_"+rs.getInt(6));
							}
						}
						//如果已经初始化但信息有变化
						else if(! (initialized.getSigninid().equalsIgnoreCase(rs.getString(2))&&initialized.getSlave()==(byte) rs.getInt(3)&& initialized.getInstanceName().equalsIgnoreCase(rs.getString(4)))   ){
							//删掉原有初始化
							InitId initId=new InitId();
							initId.setMethod("delete");
							initId.setID(initialized.getSigninid());
							initId.setSlave(initialized.getSlave());
							initId.setInstanceName(initialized.getInstanceName());
							StringManagerUtils.printLog("螺杆泵ID初始化："+gson.toJson(initId));
							StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
							//重新初始化
							initId.setMethod("update");
							initId.setID(rs.getString(2));
							initId.setSlave((byte) rs.getInt(3));
							initId.setInstanceName(rs.getString(4));
							StringManagerUtils.printLog("螺杆泵ID初始化："+gson.toJson(initId));
							String response=StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
							if(StringManagerUtils.isNotNull(response)){
								InitializedDeviceInfo initializedDeviceInfo=new InitializedDeviceInfo(rs.getInt(7),rs.getInt(6),rs.getString(1),rs.getInt(5),rs.getString(2),(byte) rs.getInt(3),rs.getString(4));
								initializedDeviceList.put(1+"_"+initializedDeviceInfo.getDeviceId(), initializedDeviceInfo);
							}
						}
					}
				}else{
					if(initialized!=null){
						//删掉原有初始化
						InitId initId=new InitId();
						initId.setMethod("delete");
						initId.setID(initialized.getSigninid());
						initId.setSlave(initialized.getSlave());
						initId.setInstanceName(initialized.getInstanceName());
						StringManagerUtils.printLog("螺杆泵ID初始化："+gson.toJson(initId));
						StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
						initializedDeviceList.remove(1+"_"+rs.getInt(6));
					}
				}
			}
			dataModelMap.put("InitializedDeviceList", initializedDeviceList);
		} catch (SQLException e) {
			StringManagerUtils.printLog("ID初始化sql："+sql);
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return result;
	}
	
	public static int initPCPDriverAcquisitionInfoConfigById(List<String> wellIdList,String method){
		String initUrl=Config.getInstance().configFile.getAd().getId();
		Gson gson = new Gson();
		int result=0;
		
		Map<String, Object> dataModelMap = DataModelMap.getMapObject();
		Map<String,InitializedDeviceInfo> initializedDeviceList=(Map<String,InitializedDeviceInfo>) dataModelMap.get("InitializedDeviceList");
		if(initializedDeviceList==null){
			initializedDeviceList=new HashMap<String,InitializedDeviceInfo>();
		}
		
		String wellId=StringUtils.join(wellIdList, ",");
		if(!StringManagerUtils.isNotNull(method)){
			method="update";
		}
		String sql="select t.wellname,t.signinid,t.slave,t2.name,t.devicetype,t.id,t.orgid,t.status "
				+ " from tbl_pcpdevice t left outer join tbl_protocolinstance t2  on t.instancecode=t2.code "
				+ " where 1=1 ";
//		if("update".equalsIgnoreCase(method)){
//			sql+= " and t.signinid is not null and t.slave is not null and t.status=1";
//		}	
		if(StringManagerUtils.isNotNull(wellId)){
			sql+=" and t.id in("+wellId+")";
		}
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null ){
        	return -1;
        }
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				InitializedDeviceInfo initialized=initializedDeviceList.get(1+"_"+rs.getInt(6));
				int status=rs.getInt(8);
				if("update".equalsIgnoreCase(method)&&status==1){
					if(initialized==null&&StringManagerUtils.isNotNull(rs.getString(2))&& rs.getInt(3)>0 &&StringManagerUtils.isNotNull(rs.getString(4))){//如果未初始化
						InitId initId=new InitId();
						initId.setMethod("update");
						initId.setID(rs.getString(2));
						initId.setSlave((byte) rs.getInt(3));
						initId.setInstanceName(rs.getString(4));
						StringManagerUtils.printLog("螺杆泵ID初始化："+gson.toJson(initId));
						String response=StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
						if(StringManagerUtils.isNotNull(response)){
							InitializedDeviceInfo initializedDeviceInfo=new InitializedDeviceInfo(rs.getInt(7),rs.getInt(6),rs.getString(1),rs.getInt(5),rs.getString(2),(byte) rs.getInt(3),rs.getString(4));
							initializedDeviceList.put(1+"_"+initializedDeviceInfo.getDeviceId(), initializedDeviceInfo);
						}
					}else if(initialized!=null){
						//如果已经初始化但注册包ID、设备从地址、实例有一项为空，删除设备
						if( (!StringManagerUtils.isNotNull(rs.getString(2))) || rs.getInt(3)==0 || (!StringManagerUtils.isNotNull(rs.getString(4))) ){
							InitId initId=new InitId();
							initId.setMethod("delete");
							initId.setID(initialized.getSigninid());
							initId.setSlave(initialized.getSlave());
							initId.setInstanceName(initialized.getInstanceName());
							StringManagerUtils.printLog("螺杆泵ID初始化："+gson.toJson(initId));
							String response=StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
							if(StringManagerUtils.isNotNull(response)){
								initializedDeviceList.remove(1+"_"+rs.getInt(6));
							}
						}
						//如果已经初始化但信息有变化
						else if(! (initialized.getSigninid().equalsIgnoreCase(rs.getString(2))&&initialized.getSlave()==(byte) rs.getInt(3)&& initialized.getInstanceName().equalsIgnoreCase(rs.getString(4)))   ){
							//删掉原有初始化
							InitId initId=new InitId();
							initId.setMethod("delete");
							initId.setID(initialized.getSigninid());
							initId.setSlave(initialized.getSlave());
							initId.setInstanceName(initialized.getInstanceName());
							StringManagerUtils.printLog("螺杆泵ID初始化："+gson.toJson(initId));
							StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
							//重新初始化
							initId.setMethod("update");
							initId.setID(rs.getString(2));
							initId.setSlave((byte) rs.getInt(3));
							initId.setInstanceName(rs.getString(4));
							StringManagerUtils.printLog("螺杆泵ID初始化："+gson.toJson(initId));
							String response=StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
							if(StringManagerUtils.isNotNull(response)){
								InitializedDeviceInfo initializedDeviceInfo=new InitializedDeviceInfo(rs.getInt(7),rs.getInt(6),rs.getString(1),rs.getInt(5),rs.getString(2),(byte) rs.getInt(3),rs.getString(4));
								initializedDeviceList.put(1+"_"+initializedDeviceInfo.getDeviceId(), initializedDeviceInfo);
							}
						}
					}
				}else{
					if(initialized!=null){
						//删掉原有初始化
						InitId initId=new InitId();
						initId.setMethod("delete");
						initId.setID(initialized.getSigninid());
						initId.setSlave(initialized.getSlave());
						initId.setInstanceName(initialized.getInstanceName());
						StringManagerUtils.printLog("螺杆泵ID初始化："+gson.toJson(initId));
						StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
						initializedDeviceList.remove(1+"_"+rs.getInt(6));
					}
				}
			}
			dataModelMap.put("InitializedDeviceList", initializedDeviceList);
		} catch (SQLException e) {
			StringManagerUtils.printLog("ID初始化sql："+sql);
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return result;
	}
	
	@SuppressWarnings("resource")
	public static int initDriverAcquisitionInfoConfigByProtocolName(String protocolName,int deviceType,String method){
		List<String> wellList=new ArrayList<String>();
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		String sql="";
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return -1;
        }
		try {
			if(deviceType==0){
				sql="select t.wellname from tbl_rpcdevice t where t.instancecode in ( select t2.code from tbl_protocolinstance t2,tbl_acq_unit_conf t3 where t2.unitid=t3.id and t3.protocol='"+protocolName+"' )";
				pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					wellList.add(rs.getString(1));
				}
				if(wellList.size()>0){
					initRPCDriverAcquisitionInfoConfig(wellList,method);
				}
			}else if(deviceType==1){
				wellList=new ArrayList<String>();
				sql="select t.wellname from tbl_pcpdevice t where t.instancecode in ( select t2.code from tbl_protocolinstance t2,tbl_acq_unit_conf t3 where t2.unitid=t3.id and t3.protocol='"+protocolName+"' )";
				pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					wellList.add(rs.getString(1));
				}
				if(wellList.size()>0){
					initPCPDriverAcquisitionInfoConfig(wellList,method);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return 0;
	}
	
	@SuppressWarnings("resource")
	public static int initDriverAcquisitionInfoConfigByProtocolInstance(String instanceCode,String method){
		List<String> wellList=new ArrayList<String>();
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return -1;
        }
		try {
			String sql="select t.wellname from tbl_rpcdevice t where t.instancecode='"+instanceCode+"'";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getString(1));
			}
			if(wellList.size()>0){
				initRPCDriverAcquisitionInfoConfig(wellList,method);
			}
			
			wellList=new ArrayList<String>();
			sql="select t.wellname from tbl_pcpdevice t where t.instancecode='"+instanceCode+"'";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getString(1));
			}
			if(wellList.size()>0){
				initPCPDriverAcquisitionInfoConfig(wellList,method);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return 0;
	}
	
	public static int initDriverAcquisitionInfoConfigByProtocolInstanceId(String instanceId,String method){
		List<String> wellList=new ArrayList<String>();
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return -1;
        }
		try {
			String sql="select t.wellname from tbl_rpcdevice t,tbl_protocolinstance t2 where t.instancecode=t2.code and t2.id="+instanceId;
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getString(1));
			}
			if(wellList.size()>0){
				initRPCDriverAcquisitionInfoConfig(wellList,method);
			}
			
			wellList=new ArrayList<String>();
			sql="select t.wellname from tbl_pcpdevice t,tbl_protocolinstance t2 where t.instancecode=t2.code and t2.id="+instanceId;
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getString(1));
			}
			if(wellList.size()>0){
				initPCPDriverAcquisitionInfoConfig(wellList,method);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return 0;
	}
	
	@SuppressWarnings("resource")
	public static int initDriverAcquisitionInfoConfigByAcqUnitId(String unitId,String method){
		List<String> wellList=new ArrayList<String>();
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return -1;
        }
		try {
			String sql="select t.wellname from tbl_rpcdevice t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 where t.instancecode=t2.code and t2.unitid=t3.id and t3.id="+unitId;
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getString(1));
			}
			if(wellList.size()>0){
				initRPCDriverAcquisitionInfoConfig(wellList,method);
			}
			
			wellList=new ArrayList<String>();
			sql="select t.wellname from tbl_pcpdevice t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 where t.instancecode=t2.code and t2.unitid=t3.id and t3.id="+unitId;
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getString(1));
			}
			if(wellList.size()>0){
				initPCPDriverAcquisitionInfoConfig(wellList,method);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return 0;
	}
	
	@SuppressWarnings("resource")
	public static int initPumpDriverAcquisitionInfoConfigByAcqGroupId(String groupId,String method){
		List<String> wellList=new ArrayList<String>();
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return -1;
        }
		try {
			String sql="select t.id from tbl_rpcdevice t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 ,tbl_acq_group2unit_conf t4,tbl_acq_group_conf t5 "
					+ " where t.instancecode=t2.code and t2.unitid=t3.id and t3.id=t4.unitid and t4.groupid=t5.id and t5.id="+groupId;
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getString(1));
			}
			if(wellList.size()>0){
				initRPCDriverAcquisitionInfoConfigById(wellList,method);
			}
			
			wellList=new ArrayList<String>();
			sql="select t.id from tbl_pcpdevice t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 ,tbl_acq_group2unit_conf t4,tbl_acq_group_conf t5 "
					+ " where t.instancecode=t2.code and t2.unitid=t3.id and t3.id=t4.unitid and t4.groupid=t5.id and t5.id="+groupId;
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getString(1));
			}
			if(wellList.size()>0){
				initPCPDriverAcquisitionInfoConfigById(wellList,method);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return 0;
	}
	
	public static int initSMSDevice(List<String> wellList,String method){
		String initUrl=Config.getInstance().configFile.getAd().getSMS();
		Gson gson = new Gson();
		int result=0;
		String wellName=StringManagerUtils.joinStringArr2(wellList, ",");
		if(!StringManagerUtils.isNotNull(method)){
			method="update";
		}
		String sql="select t.wellname,t.signinid,t2.name "
				+ " from tbl_smsdevice t,tbl_protocolsmsinstance t2 "
				+ " where t.instancecode=t2.code ";
		if("update".equalsIgnoreCase(method)){
			sql+= " and t.signinid is not null";
		}	
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and t.wellname in("+wellName+")";
		}
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null ){
        	return -1;
        }
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				InitId initId=new InitId();
				initId.setMethod(method);
				initId.setID(rs.getString(2));
				initId.setInstanceName(rs.getString(3));
				StringManagerUtils.printLog("短信设备初始化："+gson.toJson(initId));
				StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
			}
		} catch (SQLException e) {
			StringManagerUtils.printLog("ID初始化sql："+sql);
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return result;
	}
	
	public static int initSMSDeviceByInstanceCode(String instanceCode,String method){
		String sql="select t.wellname from tbl_smsdevice t where t.instancecode='"+instanceCode+"'";
		List<String> wellList=new ArrayList<String>();
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return -1;
        }
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getString(1));
			}
			if(wellList.size()>0){
				initSMSDevice(wellList,method);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return 0;
	}
	
	public static int initSMSDeviceByInstanceId(String instanceId,String method){
		String sql="select t.wellname from tbl_smsdevice t,tbl_protocolsmsinstance t2 where t.instancecode=t2.code and t2.id="+instanceId;
		List<String> wellList=new ArrayList<String>();
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return -1;
        }
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getString(1));
			}
			if(wellList.size()>0){
				initSMSDevice(wellList,method);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return 0;
	}
	
	public static void initServerConfig() throws MalformedURLException{
		String accessPath=Config.getInstance().configFile.getAp().getServer().getUrl();
		String initUrl=Config.getInstance().configFile.getAd().getServer();
		StringBuffer json_buff = new StringBuffer();
		URL url = new URL(accessPath);
		String host=url.getHost();
		int port=url.getPort();
		String projectName="";
		String path = url.getPath();
		String[] pathArr=path.split("/");
		if(pathArr.length>=2){
			projectName=pathArr[1];
		}
		json_buff.append("{");
		json_buff.append("\"IP\":\""+host+"\",");
		json_buff.append("\"Port\":\""+port+"\",");
		json_buff.append("\"ProjectName\":\""+projectName+"\"");
		json_buff.append("}");
		StringManagerUtils.printLog("服务始化："+json_buff.toString());
		StringManagerUtils.sendPostMethod(initUrl,json_buff.toString(),"utf-8",0,0);
	}
	
	public static void initRPCServerConfig() throws MalformedURLException{
		String accessPath=Config.getInstance().configFile.getAp().getServer().getUrl();
		String initUrl=Config.getInstance().configFile.getAd_rpc().getServer();
		StringBuffer json_buff = new StringBuffer();
		URL url = new URL(accessPath);
		String host=url.getHost();
		int port=url.getPort();
		String projectName="";
		String path = url.getPath();
		String[] pathArr=path.split("/");
		if(pathArr.length>=2){
			projectName=pathArr[1];
		}
		json_buff.append("{");
		json_buff.append("\"IP\":\""+host+"\",");
		json_buff.append("\"Port\":\""+port+"\",");
		json_buff.append("\"ProjectName\":\""+projectName+"\"");
		json_buff.append("}");
		StringManagerUtils.printLog("ad_rpc服务始化："+json_buff.toString());
		StringManagerUtils.sendPostMethod(initUrl,json_buff.toString(),"utf-8",0,0);
	}
	
	public static int initWellCommStatus(){
		String intRPCCommSql="update tbl_rpcacqdata_latest t set t.commstatus=0 ";
		String intPCPCommSql="update tbl_pcpacqdata_latest t set t.commstatus=0 ";
		int result=0;
		try {
			result = JDBCUtil.updateRecord(intRPCCommSql, null);
			result = JDBCUtil.updateRecord(intPCPCommSql, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static int initWellRPCCommStatus(){
		String intCommSql="update tbl_rpcacqdata_latest t set t.upcommstatus=0,t.downcommstatus=0";
		int result=0;
		try {
			result = JDBCUtil.updateRecord(intCommSql, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
