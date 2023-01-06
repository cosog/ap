package com.cosog.task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cosog.model.AccessToken;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.RPCDeviceInfo;
import com.cosog.model.drive.InitId;
import com.cosog.model.drive.InitInstance;
import com.cosog.model.drive.InitProtocol;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.thread.calculate.InitIdAndIPPortThread;
import com.cosog.thread.calculate.ThreadPool;
import com.cosog.utils.AcquisitionItemColumnsMap;
import com.cosog.utils.AdInitMap;
import com.cosog.utils.Config;
import com.cosog.utils.ConfigFile;
import com.cosog.utils.JDBCUtil;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.RedisUtil;
import com.cosog.utils.SerializeObjectUnils;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import redis.clients.jedis.Jedis;


@Component("EquipmentDriverServerTask")  
public class EquipmentDriverServerTask {
	public static ServerSocket serverSocket=null;
	
	private static EquipmentDriverServerTask instance=new EquipmentDriverServerTask();
	
	private static boolean initEnable=false;
	
	public static EquipmentDriverServerTask getInstance(){
		return instance;
	}
	
	@SuppressWarnings({ "static-access", "unused" })
	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void driveServerTast() throws SQLException, ParseException,InterruptedException, IOException{
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		
		String allOfflineUrl=stringManagerUtils.getProjectUrl()+"/api/acq/allDeviceOffline";
		String probeUrl=StringManagerUtils.getRequesrUrl(Config.getInstance().configFile.getAd().getIp(), Config.getInstance().configFile.getAd().getPort(), Config.getInstance().configFile.getAd().getProbe().getInit());
		
		initWellCommStatus();
		MemoryDataManagerTask.loadMemoryData();
//		
//		
//		path=stringManagerUtils.getFilePath("test3.json","example/");
//		String onLineData=stringManagerUtils.readFile(path,"utf-8");
//		
//		path=stringManagerUtils.getFilePath("test4.json","example/");
//		String offLineData=stringManagerUtils.readFile(path,"utf-8");
//		
//		path=stringManagerUtils.getFilePath("rpc01_01.json","example/");
//		String testData=stringManagerUtils.readFile(path,"utf-8");
//		
//		String url=stringManagerUtils.getProjectUrl()+"/api/acq/id/group";
//		String onlineUrl=stringManagerUtils.getProjectUrl()+"/api/acq/online";
//		
//		int i=0;
//		while(true){
//			if(i%2==0){
//				StringManagerUtils.sendPostMethod(onlineUrl, onLineData,"utf-8",0,0);
//			}else{
//				StringManagerUtils.sendPostMethod(onlineUrl, offLineData,"utf-8",0,0);
//			}
//			i++;
//			Thread.sleep(1000*5);
////			StringManagerUtils.sendPostMethod(onlineUrl, onLineData,"utf-8",0,0);
//			StringManagerUtils.sendPostMethod(url, testData,"utf-8",0,0);
//			Thread.sleep(1000*5);
//		}
		
		initServerConfig();
		initProtocolConfig("","");
		initInstanceConfig(null,"");
		initSMSInstanceConfig(null,"");
		initSMSDevice(null,"");
		initRPCDriverAcquisitionInfoConfig(null,0,"");
		initPCPDriverAcquisitionInfoConfig(null,0,"");
		ThreadPool executor = new ThreadPool("adInit",
				Config.getInstance().configFile.getAp().getThreadPool().getInitIdAndIpPort().getCorePoolSize(), 
				Config.getInstance().configFile.getAp().getThreadPool().getInitIdAndIpPort().getMaximumPoolSize(), 
				Config.getInstance().configFile.getAp().getThreadPool().getInitIdAndIpPort().getKeepAliveTime(), 
				TimeUnit.SECONDS, 
				Config.getInstance().configFile.getAp().getThreadPool().getInitIdAndIpPort().getWattingCount());
		while (!executor.isCompletedByTaskCount()) {
			System.out.println(executor.getExecutor().getTaskCount()+","+executor.getExecutor().getCompletedTaskCount());
			Thread.sleep(1000*1);
	    }
		System.out.println("线程池任务执行完毕！");
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
//					initSMSDevice(null,"");
				}
				if(!( driverProbeResponse.getIDInitStatus() || driverProbeResponse.getIPPortInitStatus() )){
					if(executor.isCompletedByTaskCount()){
						//清空内存
						AdInitMap.cleanData();
						
						initRPCDriverAcquisitionInfoConfig(null,0,"");
						initPCPDriverAcquisitionInfoConfig(null,0,"");
					}
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
		@SuppressWarnings("static-access")
		public void run(){
			try {
				Thread.currentThread().sleep(1000*wait);
				StringManagerUtils stringManagerUtils=new StringManagerUtils();
				String url=stringManagerUtils.getProjectUrl()+"/api/acq/group";
				
				String path="";
				path=stringManagerUtils.getFilePath(deviceName+"_01.json","example/");
				String data=stringManagerUtils.readFile(path,"utf-8");
				
				path=stringManagerUtils.getFilePath(deviceName+"_02.json","example/");
				String data2=stringManagerUtils.readFile(path,"utf-8");
				
//				Gson gson=new Gson();
//				java.lang.reflect.Type type = new TypeToken<AcqGroup>() {}.getType();
//				AcqGroup acqGroup1=gson.fromJson(data, type);
//				AcqGroup acqGroup2=gson.fromJson(data2, type);
//				
//				String acqTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
//				if(acqGroup1!=null&&acqGroup1.getAddr()!=null&&acqGroup1.getAddr().size()>0){
//					
//				}
				
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
	
	@SuppressWarnings("static-access")
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
		public boolean IPPortInitStatus;
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
		public boolean getIPPortInitStatus() {
			return IPPortInitStatus;
		}
		public void setIPPortInitStatus(boolean iPPortInitStatus) {
			IPPortInitStatus = iPPortInitStatus;
		}
	}
	
	@SuppressWarnings({ "resource" })
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
				//同步抽油机井字段映射表
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
				//同步螺杆泵井字段映射表
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
		if(modbusProtocolConfig==null){
			return 0;
		}
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
		String sql="select t.COLUMN_NAME from user_tab_cols t where t.TABLE_NAME=UPPER('"+tableName+"') order by t.COLUMN_ID";
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
	
	@SuppressWarnings("resource")
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
				int sort=rs.getInt(4);
				if((!StringManagerUtils.databaseColumnFiter(rs.getString(3))) && rs.getString(3).toUpperCase().startsWith("C_")){
					dataDictionaryItemsId.add(rs.getString(1));
					dataDictionaryItemsName.add(rs.getString(2));
					dataDictionaryItems.add(rs.getString(3));
					dataDictionaryItemsSortList.add(sort);
				}
				if(sort>maxSortNum){
					maxSortNum=sort;
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
	
	@SuppressWarnings("static-access")
	public static void initProtocolConfig(String protocolName,String method){
		if(!StringManagerUtils.isNotNull(method)){
			method="update";
		}
		String initUrl=StringManagerUtils.getRequesrUrl(Config.getInstance().configFile.getAd().getIp(), Config.getInstance().configFile.getAd().getPort(), Config.getInstance().configFile.getAd().getInit().getProtocol());
		Gson gson = new Gson();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		InitProtocol initProtocol=null;
		if(modbusProtocolConfig!=null){
			if("delete".equalsIgnoreCase(method)){
				initProtocol=new InitProtocol();
				initProtocol.setProtocolName(protocolName);
				initProtocol.setMethod(method);
				StringManagerUtils.printLog("删除协议："+gson.toJson(initProtocol));
				if(initEnable){
					StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initProtocol),"utf-8",0,0);
				}
			}else{
				if(StringManagerUtils.isNotNull(protocolName)){
					for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
						if(protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getName())){
							initProtocol=new InitProtocol(modbusProtocolConfig.getProtocol().get(i));
							initProtocol.setMethod(method);
							StringManagerUtils.printLog("协议初始化："+gson.toJson(initProtocol));
							if(initEnable){
								StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initProtocol),"utf-8",0,0);
							}
							break;
						}
					}
				}else{
					for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
						initProtocol=new InitProtocol(modbusProtocolConfig.getProtocol().get(i));
						initProtocol.setMethod(method);
						StringManagerUtils.printLog("协议初始化："+gson.toJson(initProtocol));
						if(initEnable){
							StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initProtocol),"utf-8",0,0);
						}
					}
				}
			}
			loadAcquisitionItemColumns();
			//同步数据库字段
			initAcquisitionItemDataBaseColumns();
			//同步数据字典
			initDataDictionary("7f13446d19b4497986980fa16a750f95",0);//抽油机井实时概览字典
			initDataDictionary("cd7b24562b924d19b556de31256e22a1",0);//抽油机井历史查询字典
			initDataDictionary("e0f5f3ff8a1f46678c284fba9cc113e8",1);//螺杆泵井实时概览字典
			initDataDictionary("fb7d070a349c403b8a26d71c12af7a05",1);//螺杆泵井历史查询字典
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
	
	
	@SuppressWarnings("static-access")
	public static int initInstanceConfig(List<String> instanceList,String method){
		String initUrl=StringManagerUtils.getRequesrUrl(Config.getInstance().configFile.getAd().getIp(), Config.getInstance().configFile.getAd().getPort(), Config.getInstance().configFile.getAd().getInit().getInstance());
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
				if(initEnable){
					StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initInstance),"utf-8",0,0);
				}
			}
		}else{
			String sql="select t.name,t.acqprotocoltype,t.ctrlprotocoltype,"//1~3
					+ "t.signinprefix,t.signinsuffix,t.heartbeatprefix,t.heartbeatsuffix,"//4~7
					+ "t.packetsendinterval,t.prefixsuffixhex,"//8~9
					+ "t2.protocol,t2.unit_code,t4.group_code,t4.grouptiminginterval,t4.type,"//10~14
					+ "listagg(t5.itemname, ',') within group(order by t5.id ) key "//15
					+ " from tbl_protocolinstance t "
					+ " left outer join tbl_acq_unit_conf t2 on t.unitid=t2.id "
					+ " left outer join tbl_acq_group2unit_conf t3 on t2.id=t3.unitid "
					+ " left outer join tbl_acq_group_conf t4 on t3.groupid=t4.id "
					+ " left outer join tbl_acq_item2group_conf t5 on t4.id=t5.groupid  "
					+ " where 1=1 ";
			if(StringManagerUtils.isNotNull(instances)){
				sql+=" and t.name in("+instances+")";
			}
			sql+= "group by t.name,t.acqprotocoltype,t.ctrlprotocoltype,t.signinprefix,t.signinsuffix,t.heartbeatprefix,t.heartbeatsuffix,t.packetsendinterval,t.prefixsuffixhex,"
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
						initInstance.setProtocolName(rs.getString(10));
						initInstance.setAcqProtocolType(rs.getString(2));
						initInstance.setCtrlProtocolType(rs.getString(3));
						
						
						boolean prefixSuffixHex=rs.getInt(9)==1;
						initInstance.setPrefixSuffixHex(prefixSuffixHex);
						
						initInstance.setSignInPrefix(rs.getString(4)==null?"":rs.getString(4));
						initInstance.setSignInSuffix(rs.getString(5)==null?"":rs.getString(5));
						
						initInstance.setHeartbeatPrefix(rs.getString(6)==null?"":rs.getString(6));
						initInstance.setHeartbeatSuffix(rs.getString(7)==null?"":rs.getString(7));
						
						initInstance.setPacketSendInterval(rs.getInt(8));
						
						initInstance.setAcqGroup(new ArrayList<InitInstance.Group>());
						initInstance.setCtrlGroup(new ArrayList<InitInstance.Group>());
					}
					if(StringManagerUtils.isNotNull(rs.getString(12))){
						InitInstance.Group group=new InitInstance.Group();
						group.setGroupTimingInterval(rs.getInt(13));
						group.setAddr(new ArrayList<Integer>());
						int groupType=rs.getInt(14);
						if(StringManagerUtils.isNotNull(rs.getString(15))){
							String[] itemsArr=rs.getString(15).split(",");
							for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
								if(modbusProtocolConfig.getProtocol().get(i).getName().equalsIgnoreCase(rs.getString(10))){
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
						if(initEnable){
							StringManagerUtils.sendPostMethod(initUrl, gson.toJson(entry.getValue()),"utf-8",0,0);
						}
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
	@SuppressWarnings("static-access")
	public static int initSMSInstanceConfig(List<String> instanceList,String method){
		String initUrl=StringManagerUtils.getRequesrUrl(Config.getInstance().configFile.getAd().getIp(), Config.getInstance().configFile.getAd().getPort(), Config.getInstance().configFile.getAd().getInit().getInstance());
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
				if(initEnable){
					StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initInstance),"utf-8",0,0);
				}
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
					if(initEnable){
						StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initInstance),"utf-8",0,0);
					}
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
	
	@SuppressWarnings({ "static-access" })
	public static int initRPCDriverAcquisitionInfoConfig(List<String> wellList,int condition,String method){
		Jedis jedis=null;
		try{
			ThreadPool executor = new ThreadPool("adInit",
					Config.getInstance().configFile.getAp().getThreadPool().getInitIdAndIpPort().getCorePoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getInitIdAndIpPort().getMaximumPoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getInitIdAndIpPort().getKeepAliveTime(), 
					TimeUnit.SECONDS, 
					Config.getInstance().configFile.getAp().getThreadPool().getInitIdAndIpPort().getWattingCount());
			
			jedis = RedisUtil.jedisPool.getResource();
			if(!StringManagerUtils.isNotNull(method)){
				method="update";
			}
			if(!jedis.exists("RPCDeviceInfo".getBytes())){
				MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
			}
			List<byte[]> deviceInfoByteList =jedis.hvals("RPCDeviceInfo".getBytes());
			for(int i=0;i<deviceInfoByteList.size();i++){
				boolean matching=false;
				RPCDeviceInfo rpcDeviceInfo=(RPCDeviceInfo)SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
				if(wellList==null){
					matching=true;
				}else{
					if(condition==0){
						if(StringManagerUtils.existOrNot(wellList, rpcDeviceInfo.getId()+"", false)){
							matching=true;
						}
					}else if(condition==1){
						if(StringManagerUtils.existOrNot(wellList, rpcDeviceInfo.getWellName()+"", false)){
							matching=true;
						}
					}
				}
				
				if(matching){
					executor.execute(new InitIdAndIPPortThread(rpcDeviceInfo, null, 0,initEnable,method));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return 0;
	}
	
	@SuppressWarnings("static-access")
	public static int initPCPDriverAcquisitionInfoConfig(List<String> wellList,int condition,String method){
		Jedis jedis=null;
		try{
			ThreadPool executor = new ThreadPool("adInit",Config.getInstance().configFile.getAp().getThreadPool().getInitIdAndIpPort().getCorePoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getInitIdAndIpPort().getMaximumPoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getInitIdAndIpPort().getKeepAliveTime(), 
					TimeUnit.SECONDS, 
					Config.getInstance().configFile.getAp().getThreadPool().getInitIdAndIpPort().getWattingCount());
			jedis = RedisUtil.jedisPool.getResource();
			if(!StringManagerUtils.isNotNull(method)){
				method="update";
			}
			if(!jedis.exists("PCPDeviceInfo".getBytes())){
				MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
			}
			List<byte[]> deviceInfoByteList =jedis.hvals("PCPDeviceInfo".getBytes());
			for(int i=0;i<deviceInfoByteList.size();i++){
				boolean matching=false;
				PCPDeviceInfo pcpDeviceInfo=(PCPDeviceInfo)SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
				if(wellList==null){
					matching=true;
				}else{
					if(condition==0){
						if(StringManagerUtils.existOrNot(wellList, pcpDeviceInfo.getId()+"", false)){
							matching=true;
						}
					}else if(condition==1){
						if(StringManagerUtils.existOrNot(wellList, pcpDeviceInfo.getWellName()+"", false)){
							matching=true;
						}
					}
				}
				
				if(matching){
					executor.execute(new InitIdAndIPPortThread(null, pcpDeviceInfo, 1,initEnable,method));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return 0;
	}
	
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
				sql="select t.id from tbl_rpcdevice t where t.instancecode in ( select t2.code from tbl_protocolinstance t2,tbl_acq_unit_conf t3 where t2.unitid=t3.id and t3.protocol='"+protocolName+"' )";
				pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					wellList.add(rs.getInt(1)+"");
				}
				if(wellList.size()>0){
					initRPCDriverAcquisitionInfoConfig(wellList,0,method);
				}
			}else if(deviceType==1){
				wellList=new ArrayList<String>();
				sql="select t.id from tbl_pcpdevice t where t.instancecode in ( select t2.code from tbl_protocolinstance t2,tbl_acq_unit_conf t3 where t2.unitid=t3.id and t3.protocol='"+protocolName+"' )";
				pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					wellList.add(rs.getInt(1)+"");
				}
				if(wellList.size()>0){
					initPCPDriverAcquisitionInfoConfig(wellList,0,method);
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
			String sql="select t.id from tbl_rpcdevice t where t.instancecode='"+instanceCode+"'";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
			if(wellList.size()>0){
				initRPCDriverAcquisitionInfoConfig(wellList,0,method);
			}
			
			wellList=new ArrayList<String>();
			sql="select t.id from tbl_pcpdevice t where t.instancecode='"+instanceCode+"'";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
			if(wellList.size()>0){
				initPCPDriverAcquisitionInfoConfig(wellList,0,method);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return 0;
	}
	
	@SuppressWarnings("resource")
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
			String sql="select t.id from tbl_rpcdevice t,tbl_protocolinstance t2 where t.instancecode=t2.code and t2.id="+instanceId;
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
			if(wellList.size()>0){
				initRPCDriverAcquisitionInfoConfig(wellList,0,method);
			}
			
			wellList=new ArrayList<String>();
			sql="select t.id from tbl_pcpdevice t,tbl_protocolinstance t2 where t.instancecode=t2.code and t2.id="+instanceId;
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
			if(wellList.size()>0){
				initPCPDriverAcquisitionInfoConfig(wellList,0,method);
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
			String sql="select t.id from tbl_rpcdevice t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 where t.instancecode=t2.code and t2.unitid=t3.id and t3.id="+unitId;
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
			if(wellList.size()>0){
				initRPCDriverAcquisitionInfoConfig(wellList,0,method);
			}
			
			wellList=new ArrayList<String>();
			sql="select t.id from tbl_pcpdevice t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 where t.instancecode=t2.code and t2.unitid=t3.id and t3.id="+unitId;
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
			if(wellList.size()>0){
				initPCPDriverAcquisitionInfoConfig(wellList,0,method);
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
				wellList.add(rs.getInt(1)+"");
			}
			if(wellList.size()>0){
				initRPCDriverAcquisitionInfoConfig(wellList,0,method);
			}
			
			wellList=new ArrayList<String>();
			sql="select t.id from tbl_pcpdevice t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 ,tbl_acq_group2unit_conf t4,tbl_acq_group_conf t5 "
					+ " where t.instancecode=t2.code and t2.unitid=t3.id and t3.id=t4.unitid and t4.groupid=t5.id and t5.id="+groupId;
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
			if(wellList.size()>0){
				initPCPDriverAcquisitionInfoConfig(wellList,0,method);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return 0;
	}
	
	@SuppressWarnings("static-access")
	public static int initSMSDevice(List<String> wellList,String method){
		String initUrl=StringManagerUtils.getRequesrUrl(Config.getInstance().configFile.getAd().getIp(), Config.getInstance().configFile.getAd().getPort(), Config.getInstance().configFile.getAd().getInit().getSMS());
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
				if(initEnable){
					StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
				}
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
		String initUrl=StringManagerUtils.getRequesrUrl(Config.getInstance().configFile.getAd().getIp(), Config.getInstance().configFile.getAd().getPort(), Config.getInstance().configFile.getAd().getInit().getServer().getUrl());
		StringBuffer json_buff = new StringBuffer();
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		try {
//			String host = StringManagerUtils.getLocalIPAddress();
////			host = "127.0.0.1";
//			int port=StringManagerUtils.getHttpPort();
//			String projectName=stringManagerUtils.getProjectName();
			ConfigFile.Ad_ServerInitContent[] content=Config.getInstance().configFile.getAd().getInit().getServer().getContent();
			for(int i=0;i<content.length;i++){
				json_buff.append("{");
				json_buff.append("\"IP\":\""+content[i].getIp()+"\",");
				json_buff.append("\"Port\":\""+content[i].getPort()+"\",");
				json_buff.append("\"ProjectName\":\""+content[i].getProjectName()+"\"");
				json_buff.append("}");
				StringManagerUtils.printLog("服务初始化："+json_buff.toString());
				if(initEnable){
					StringManagerUtils.sendPostMethod(initUrl,json_buff.toString(),"utf-8",0,0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	@SuppressWarnings("static-access")
	public static void sendDeviceOfflineInfo(List<String> wellList,int deviceType){
		if(wellList==null || wellList.size()==0){
			return;
		}
		Jedis jedis=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			StringManagerUtils stringManagerUtils=new StringManagerUtils();
			List<byte[]> deviceInfoByteList=null;
			String url=stringManagerUtils.getProjectUrl()+"/api/acq/id/online";
			String key="ID";
			if(deviceType==0){
				if(!jedis.exists("RPCDeviceInfo".getBytes())){
					MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
				}
				deviceInfoByteList=jedis.hvals("RPCDeviceInfo".getBytes());
			}else if(deviceType==1){
				if(!jedis.exists("PCPDeviceInfo".getBytes())){
					MemoryDataManagerTask.loadPCPDeviceInfo(null,0,"update");
				}
				deviceInfoByteList=jedis.hvals("PCPDeviceInfo".getBytes());
			}
			for(int i=0;i<deviceInfoByteList.size();i++){
				Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
				StringBuffer json_buff = new StringBuffer();
				if (deviceType==0 && obj instanceof RPCDeviceInfo) {
					RPCDeviceInfo deviceInfo=(RPCDeviceInfo)obj;
					if(StringManagerUtils.existOrNot(wellList, deviceInfo.getId()+"", false)){
						if("TCPServer".equalsIgnoreCase(deviceInfo.getTcpType().replaceAll(" ", ""))){
							url=stringManagerUtils.getProjectUrl()+"/api/acq/ipport/online";
							key="IPPort";
						}else{
							url=stringManagerUtils.getProjectUrl()+"/api/acq/id/online";
							key="ID";
						}
						json_buff.append("{");
						json_buff.append("\""+key+"\":\""+deviceInfo.getSignInId()+"\",");
						json_buff.append("\"Slave\":\""+deviceInfo.getSlave()+"\",");
						json_buff.append("\"Status\":false");
						json_buff.append("}");
						StringManagerUtils.sendPostMethod(url, json_buff.toString(),"utf-8",0,0);
					}
				}else if(deviceType==1 && obj instanceof PCPDeviceInfo){
					PCPDeviceInfo deviceInfo=(PCPDeviceInfo)obj;
					if(StringManagerUtils.existOrNot(wellList, deviceInfo.getId()+"", false)){
						if("TCPServer".equalsIgnoreCase(deviceInfo.getTcpType().replaceAll(" ", ""))){
							url=stringManagerUtils.getProjectUrl()+"/api/acq/ipport/online";
							key="IPPort";
						}else{
							url=stringManagerUtils.getProjectUrl()+"/api/acq/id/online";
							key="ID";
						}
						json_buff.append("{");
						json_buff.append("\""+key+"\":\""+deviceInfo.getSignInId()+"\",");
						json_buff.append("\"Slave\":\""+deviceInfo.getSlave()+"\",");
						json_buff.append("\"Status\":false");
						json_buff.append("}");
						StringManagerUtils.sendPostMethod(url, json_buff.toString(),"utf-8",0,0);
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
	}
}
