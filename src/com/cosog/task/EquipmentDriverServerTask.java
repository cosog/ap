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

import com.cosog.model.DataMapping;
import com.cosog.model.calculate.AdOnlineProbeResponseData;
import com.cosog.model.calculate.RPCDeviceInfo;
import com.cosog.model.drive.InitId;
import com.cosog.model.drive.InitInstance;
import com.cosog.model.drive.InitProtocol;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.thread.calculate.DatabaseTableSynColumnThread;
import com.cosog.thread.calculate.InitIdAndIPPortThread;
import com.cosog.thread.calculate.ThreadPool;
import com.cosog.utils.AcquisitionItemColumnsMap;
import com.cosog.utils.AdInitMap;
import com.cosog.utils.Config;
import com.cosog.utils.DataModelMap;
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
	
	private static boolean initSwitch=false;
	private static boolean initEnable=initSwitch&&Config.getInstance().configFile.getAp().getOthers().isIot();
	public static EquipmentDriverServerTask getInstance(){
		return instance;
	}
	
	@SuppressWarnings({ "static-access", "unused" })
	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void driveServerTast(){
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String allOfflineUrl=stringManagerUtils.getProjectUrl()+"/api/acq/allDeviceOffline";
		
		initWellCommStatus();
		if(Config.getInstance().configFile.getAp().getOthers().isIot()){
			initWellCommStatusByOnlineProbe();//检测当前已在线的设备,并更新状态
		}
		initWellDaliyData();
		MemoryDataManagerTask.loadMemoryData();
		
		ThreadPool executor=null;
		try {
			executor = adInit();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(Config.getInstance().configFile.getAp().getOthers().isIot()){
			boolean sendMsg=false;
//			exampleDataManage();
			do{
				DriverProbeResponse driverProbeResponse=adInitProbe();
				String Ver="";
				if(driverProbeResponse!=null){
					sendMsg=false;
					if(!driverProbeResponse.getHttpServerInitStatus()){
						try {
							initServerConfig();
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						driverProbeResponse=adInitProbe();
					}
					if(!driverProbeResponse.getProtocolInitStatus()){
						initProtocolConfig("","");
						driverProbeResponse=adInitProbe();
					}
					if(!driverProbeResponse.getInstanceInitStatus()){
						if(!driverProbeResponse.getProtocolInitStatus()){
							initProtocolConfig("","");
							driverProbeResponse=adInitProbe();
						}
						initInstanceConfig(null,"");
						initSMSInstanceConfig(null,"");
						driverProbeResponse=adInitProbe();
					}
					if(!driverProbeResponse.getSMSInitStatus()){
//						initSMSDevice(null,"");
					}
					if(!( driverProbeResponse.getIDInitStatus() || driverProbeResponse.getIPPortInitStatus() )){
						if(!driverProbeResponse.getInstanceInitStatus()){
							if(!driverProbeResponse.getProtocolInitStatus()){
								initProtocolConfig("","");
								driverProbeResponse=adInitProbe();
							}
							initInstanceConfig(null,"");
							initSMSInstanceConfig(null,"");
							driverProbeResponse=adInitProbe();
						}
						
						if(executor!=null && executor.isCompletedByTaskCount()){
							//清空内存
							AdInitMap.cleanData();
							initDriverAcquisitionInfoConfig(null,0,"");
						}
					}
					Ver=driverProbeResponse.getVer();
				}else{
					if(!sendMsg){
						StringManagerUtils.sendPostMethod(allOfflineUrl, "","utf-8",0,0);
						sendMsg=true;
					}
				}
				try {
					Thread.sleep(1000*1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}while(true);
		}
	}
	
	public static ThreadPool adInit() throws MalformedURLException, InterruptedException{
		initServerConfig();
		initProtocolConfig("","");
		initInstanceConfig(null,"");
		initSMSInstanceConfig(null,"");
		initSMSDevice(null,"");
		initDriverAcquisitionInfoConfig(null,0,"");
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
		return executor;
	}
	
	public static DriverProbeResponse adInitProbe(){
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String probeUrl=Config.getInstance().configFile.getAd().getProbe().getInit();
		String responseData=StringManagerUtils.sendPostMethod(probeUrl, "","utf-8",0,0);
		type = new TypeToken<DriverProbeResponse>() {}.getType();
		DriverProbeResponse driverProbeResponse=gson.fromJson(responseData, type);
		return driverProbeResponse;
	}
	
	public static AdOnlineProbeResponseData adOnlineProbe(){
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String probeUrl=Config.getInstance().configFile.getAd().getProbe().getOnline();
		String responseData=StringManagerUtils.sendPostMethod(probeUrl, "","utf-8",0,0);
		type = new TypeToken<AdOnlineProbeResponseData>() {}.getType();
		AdOnlineProbeResponseData adOnlineProbeResponseData=gson.fromJson(responseData, type);
		return adOnlineProbeResponseData;
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
				String url=Config.getInstance().configFile.getAd().getInit().getServer().getContent().getIdAcqGroupDataPushURL();
				
				String path="";
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
	
	@SuppressWarnings("static-access")
	public static void exampleDataManage(){
		if(Config.getInstance().configFile.getAp().getOthers().getSimulateAcqEnable()){
			try {
				int sendCycle=Config.getInstance().configFile.getAp().getOthers().getSendCycle();
				int timeDifference=Config.getInstance().configFile.getAp().getOthers().getTimeDifference();
//				sendCycle=60;
//				timeDifference=0;
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
				
//				new ExampleDataManageThread("rpc11",sendCycle,timeDifference*0).start();
				
				new ExampleDataManageThread("pcp01",sendCycle,timeDifference*0).start();
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
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		List<String> tableColumnList=MemoryDataManagerTask.getAcqTableColumn();
		List<String> acquisitionItemNameList=MemoryDataManagerTask.getAcquisitionItemNameList();
		int result=0;
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return -1;
        }
		try {
			boolean isDeleteMapping=false;
			List<String> deleteMappingList=new ArrayList<>();
			//删除重复映射
			try {
				boolean isDelete=false;
				String queryDelItemSql="select t2.mappingcolumn,count(1) as cn from TBL_DATAMAPPING t2 group by t2.mappingcolumn";
				pstmt = conn.prepareStatement(queryDelItemSql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					isDelete=true;
					String column=rs.getString(1);
					int colCount=rs.getInt(2);
					if(colCount>1){
						deleteMappingList.add(column);
					}
				}
				if(isDelete){
					String delSql="delete from TBL_DATAMAPPING t where t.mappingcolumn in ( select v.mappingcolumn from  (select t2.mappingcolumn,count(1) as cn from TBL_DATAMAPPING t2 group by t2.mappingcolumn) v where v.cn>1 )";
					pstmt = conn.prepareStatement(delSql);
					result=pstmt.executeUpdate();
					if(result>0){
						isDeleteMapping=true;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//删除不存在的字段映射
			try {
				int colCount=0;
				String queryDelItemSql="select count(1) from tbl_datamapping t where t.mappingcolumn not in("+StringManagerUtils.joinStringArr2(tableColumnList, ",")+")";
				pstmt = conn.prepareStatement(queryDelItemSql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					colCount=rs.getInt(1);
				}
				if(colCount>0){
					String delSql="delete from tbl_datamapping t where t.mappingcolumn not in("+StringManagerUtils.joinStringArr2(tableColumnList, ",")+")";
					pstmt = conn.prepareStatement(delSql);
					result=pstmt.executeUpdate();
					if(result>0){
						isDeleteMapping=true;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//删除不存在的协议项映射
			try {
				boolean isDelete=false;
				String queryDelItemSql="select t.name from tbl_datamapping t where t.name not in("+StringManagerUtils.joinStringArr2(acquisitionItemNameList, ",")+")";
				pstmt = conn.prepareStatement(queryDelItemSql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					isDelete=true;
					String column=rs.getString(1);
					deleteMappingList.add(column);
				}
				
				if(isDelete){
					String delSql="delete from tbl_datamapping t where t.name not in("+StringManagerUtils.joinStringArr2(acquisitionItemNameList, ",")+")";
					pstmt = conn.prepareStatement(delSql);
					result=pstmt.executeUpdate();
					if(result>0){
						isDeleteMapping=true;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			MemoryDataManagerTask.loadProtocolMappingColumnByTitle();
			MemoryDataManagerTask.loadProtocolMappingColumn();
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumnByTitle");
			Map<String,DataMapping> loadProtocolMappingColumnMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumn");
			
			for(int i=0;i<acquisitionItemNameList.size();i++){
				if(!StringManagerUtils.dataMappingKeyExistOrNot(loadProtocolMappingColumnByTitleMap, acquisitionItemNameList.get(i),false)){
					String addMappingColumn="";
					for(int j=0;i<tableColumnList.size();j++){
						if(!StringManagerUtils.dataMappingKeyExistOrNot(loadProtocolMappingColumnMap, tableColumnList.get(j),false)){
							addMappingColumn=tableColumnList.get(j);
							DataMapping dataMapping=new DataMapping();
							dataMapping.setName(acquisitionItemNameList.get(i));
							dataMapping.setMappingColumn(addMappingColumn);
							loadProtocolMappingColumnMap.put(dataMapping.getMappingColumn(), dataMapping);
							break;
						}
					}
					if(StringManagerUtils.isNotNull(addMappingColumn)){
						try {
							String addSql="insert into tbl_datamapping(name,mappingcolumn) values('"+acquisitionItemNameList.get(i)+"','"+addMappingColumn+"')";
							pstmt = conn.prepareStatement(addSql);
							result=pstmt.executeUpdate();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					
				}
			}
			
			//清理不存在的映射数据
			if(deleteMappingList.size()>0){
				DatabaseTableSynColumnThread databaseTableSynColumnThread=new DatabaseTableSynColumnThread(deleteMappingList);
				databaseTableSynColumnThread.start();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		MemoryDataManagerTask.loadProtocolMappingColumnByTitle();
		MemoryDataManagerTask.loadProtocolMappingColumn();
		

		return result;
	}
	
	public static int syncProtocolRunStatusConfig(){
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		if(modbusProtocolConfig==null){
			return 0;
		}
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		int result=0;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return -1;
        }
		try {
			String deleteRunStatusConfigSql="delete from tbl_runstatusconfig t where t.id not in"
					+ "( select t2.id from tbl_runstatusconfig t2,tbl_datamapping t3 "
					+ " where t2.protocoltype=t3.protocoltype and t2.itemname=t3.name "
					+ " and t2.itemmappingcolumn=t3.mappingcolumn "
//					+ "and upper(t3.calcolumn)='RUNSTATUS'"
					+ ")";
			pstmt = conn.prepareStatement(deleteRunStatusConfigSql);
			result=pstmt.executeUpdate();
			MemoryDataManagerTask.loadProtocolRunStatusConfig();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return result;
	}
	

	public static int loadAcquisitionItemColumns(){
//		loadAcquisitionItemNameColumns();
		MemoryDataManagerTask.loadAcquisitionItemNameList();
		syncDataMappingTable();
		return 0;
	}
	
	
	
//	public static int loadAcquisitionItemNameColumns(){
//		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
//		if(modbusProtocolConfig!=null){
//			Collections.sort(modbusProtocolConfig.getProtocol());
//			Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
//			Map<String,String> deviceAcquisitionItemColumns=new LinkedHashMap<String,String>();
//			
//			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
//				Collections.sort(modbusProtocolConfig.getProtocol().get(i).getItems());
//				for(int j=0;j<modbusProtocolConfig.getProtocol().get(i).getItems().size();j++){
//					if(!StringManagerUtils.existOrNot(deviceAcquisitionItemColumns, modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle(),false)){
//						String itemName=modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle();
//						String itemColumn="";
//						if(!StringManagerUtils.existOrNotByValue(deviceAcquisitionItemColumns,StringManagerUtils.protocolItemNameToCol(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle()),false)){
//							itemColumn=StringManagerUtils.protocolItemNameToCol(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle());
//						}else{
//							for(int index=2;1==1;index++){
//								if(!StringManagerUtils.existOrNot(deviceAcquisitionItemColumns,StringManagerUtils.protocolItemNameToCol(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle())+index,false)){
//									itemColumn=StringManagerUtils.protocolItemNameToCol(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle())+index;
//									break;
//								}
//							}
//						}
//						deviceAcquisitionItemColumns.put(itemName, itemColumn);
//					}
//				}
//			}
//			acquisitionItemColumnsMap.put("deviceAcquisitionItemColumns", deviceAcquisitionItemColumns);
//		}
//		return 0;
//	}
	
	public static int initAcquisitionItemDataBaseColumns(){
		int result=initAcquisitionItemDataBaseColumns("tbl_acqdata_hist");
		result=initAcquisitionItemDataBaseColumns("tbl_acqdata_latest");
		result=initAcquisitionItemDataBaseColumns("tbl_timingcalculationdata");
		result=initAcquisitionItemDataBaseColumns("tbl_dailycalculationdata");
		MemoryDataManagerTask.loadAcqTableColumn();
		return result;
	}
	
	public static int initAcquisitionItemDataBaseColumns(String tableName){
		List<String> tableColumnList=MemoryDataManagerTask.getAcqTableColumn(tableName);
		List<String> acquisitionItemNameList=MemoryDataManagerTask.getAcquisitionItemNameList();
		int result=0;
		if(acquisitionItemNameList.size()>tableColumnList.size()){
			Connection conn = null;   
			PreparedStatement pstmt = null;   
			ResultSet rs = null;
			conn=OracleJdbcUtis.getConnection();
			if(conn==null){
	        	return -1;
	        }
			try {
				while(acquisitionItemNameList.size()>tableColumnList.size()){
					int addColumnCount=acquisitionItemNameList.size()-tableColumnList.size();
					int currentColumnCount=tableColumnList.size();
					int startIndex=currentColumnCount+1;
					for(int i=startIndex;i<addColumnCount+startIndex;i++){
						try {
							String addColumnName="C_CLOUMN"+i;
							String addColumsSql="alter table "+tableName+" add "+addColumnName+" VARCHAR2(4000)";
							pstmt = conn.prepareStatement(addColumsSql);
							pstmt.executeUpdate();
							tableColumnList.add(addColumnName);
							StringManagerUtils.printLog(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":表"+tableName+"添加字段:"+addColumnName);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
			}
		}
		
		
		
		
//		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
//		if(modbusProtocolConfig==null){
//			return 0;
//		}
//		Connection conn = null;   
//		PreparedStatement pstmt = null;   
//		ResultSet rs = null;
//		int result=0;
//		String columnsKey="deviceAcquisitionItemColumns";
//		Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
//		if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
//			loadAcquisitionItemColumns();
//		}
//		Map<String,String> acquisitionItemColumns=acquisitionItemColumnsMap.get(columnsKey);
//		List<String> acquisitionItemDataBaseColumns=new ArrayList<String>();
//		String sql="select t.COLUMN_NAME from user_tab_cols t "
//				+ " where t.TABLE_NAME=UPPER('"+tableName+"') "
//				+ " and  UPPER(t.COLUMN_NAME) like 'C\\_%'escape '\\'"
//				+ " order by t.COLUMN_ID";
//		conn=OracleJdbcUtis.getConnection();
//		if(conn==null){
//        	return -1;
//        }
//		try {
//			pstmt = conn.prepareStatement(sql);
//			rs=pstmt.executeQuery();
//			while(rs.next()){
//				String columnName=rs.getString(1);
//				if((!StringManagerUtils.databaseColumnFiter(columnName)) && columnName.toUpperCase().startsWith("C_")){
//					acquisitionItemDataBaseColumns.add(columnName);
//				}
//			}
//			//如驱动配置中不存在，删除字段
//			for(int i=0;i<acquisitionItemDataBaseColumns.size();i++){
//				if(!StringManagerUtils.existOrNotByValue(acquisitionItemColumns,acquisitionItemDataBaseColumns.get(i),false)){
//					String deleteColumsSql="alter table "+tableName+" drop column "+acquisitionItemDataBaseColumns.get(i);
//					pstmt = conn.prepareStatement(deleteColumsSql);
//					pstmt.executeUpdate();
//					result++;
//					StringManagerUtils.printLog(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":表"+tableName+"删除字段:"+acquisitionItemDataBaseColumns.get(i));
//				}
//			}
//			//如数据库中不存在，添加字段
//			for(String key : acquisitionItemColumns.keySet()) {
//				if(!StringManagerUtils.existOrNot(acquisitionItemDataBaseColumns,acquisitionItemColumns.get(key),false)){
//					String addColumsSql="alter table "+tableName+" add "+acquisitionItemColumns.get(key)+" VARCHAR2(4000)";
//					pstmt = conn.prepareStatement(addColumsSql);
//					pstmt.executeUpdate();
//					StringManagerUtils.printLog(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":表"+tableName+"添加字段:"+acquisitionItemColumns.get(key));
//					result++;
//				}
//			}
//			StringManagerUtils.printLog(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"-"+tableName+"同步数据库字段");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally{
//			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
//		}
		
		return result;
	}
	
	@SuppressWarnings("resource")
	public static int initDataDictionary(String dataDictionaryId){
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		if(modbusProtocolConfig==null){
			return 0;
		}
		int result=0;
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumnByTitle");
		
		Collections.sort(modbusProtocolConfig.getProtocol());
		List<String> acquisitionItemColumns=new ArrayList<String>();
		List<String> acquisitionItemsName=new ArrayList<String>();
		List<String> dataTypeList=new ArrayList<String>();
		for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){

			for(int j=0;j<modbusProtocolConfig.getProtocol().get(i).getItems().size();j++){
				String col="";
				if(loadProtocolMappingColumnByTitleMap.containsKey(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle())){
					col=loadProtocolMappingColumnByTitleMap.get(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle()).getMappingColumn();
				}
				if(StringManagerUtils.isNotNull(col) && (!"w".equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getRWType()))//非只写
					&&(!StringManagerUtils.existOrNot(acquisitionItemColumns, col,false))){
					String unit=modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getUnit();
					acquisitionItemColumns.add(col);
					acquisitionItemsName.add(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle()+(StringManagerUtils.isNotNull(unit)?("("+unit+")"):""));
					dataTypeList.add(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getIFDataType());
				}
			}
		
		}
		List<String> dataDictionaryItems=new ArrayList<String>();
		List<String> dataDictionaryItemsName=new ArrayList<String>();
		List<String> dataDictionaryItemsId=new ArrayList<String>();
		List<Integer> dataDictionaryItemsSortList=new ArrayList<Integer>();
		String sql="select t1.dataitemid,t1.cname,t1.ename,t1.sorts "
				+ " from tbl_dist_item t1 where t1.sysdataid=(select t2.sysdataid from tbl_dist_name t2 where t2.sysdataid='"+dataDictionaryId+"') "
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
		String initUrl=Config.getInstance().configFile.getAd().getInit().getProtocol();
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
			
			//同步数据库字段
			initAcquisitionItemDataBaseColumns();
			loadAcquisitionItemColumns();
			//同步数据字典
			initDataDictionary("7f13446d19b4497986980fa16a750f95");//抽油机井实时概览字典
			initDataDictionary("cd7b24562b924d19b556de31256e22a1");//抽油机井历史查询字典
			initDataDictionary("e0f5f3ff8a1f46678c284fba9cc113e8");//螺杆泵井实时概览字典
			initDataDictionary("fb7d070a349c403b8a26d71c12af7a05");//螺杆泵井历史查询字典
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
		String initUrl=Config.getInstance().configFile.getAd().getInit().getInstance();
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
					+ " t.SignInPrefixSuffixHex,t.signinprefix,t.signinsuffix,t.SignInIDHex,"//4~7
					+ " t.HeartbeatPrefixSuffixHex,t.heartbeatprefix,t.heartbeatsuffix,"//8~10
					+ " t.packetsendinterval,"//11
					+ " t2.protocol,t2.unit_code,t4.group_code,t4.grouptiminginterval,t4.type,"//12~16
//					+ " listagg(t5.itemname, ',') within group(order by t5.id ) key "//17
					+ " rtrim(xmlagg(xmlparse(content t5.itemname || ',' wellformed) order by t5.id).getclobval(),',' ) key"
					+ " from tbl_protocolinstance t "
					+ " left outer join tbl_acq_unit_conf t2 on t.unitid=t2.id "
					+ " left outer join tbl_acq_group2unit_conf t3 on t2.id=t3.unitid "
					+ " left outer join tbl_acq_group_conf t4 on t3.groupid=t4.id "
					+ " left outer join tbl_acq_item2group_conf t5 on t4.id=t5.groupid  "
					+ " where 1=1 ";
			if(StringManagerUtils.isNotNull(instances)){
				sql+=" and t.name in("+instances+")";
			}
			sql+= "group by t.name,t.acqprotocoltype,t.ctrlprotocoltype,"
					+ "t.SignInPrefixSuffixHex,t.signinprefix,t.signinsuffix,t.SignInIDHex,"
					+ "t.HeartbeatPrefixSuffixHex,t.heartbeatprefix,t.heartbeatsuffix,"
					+ "t.packetsendinterval,"
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
						initInstance.setProtocolName(rs.getString(12));
						initInstance.setAcqProtocolType(rs.getString(2));
						initInstance.setCtrlProtocolType(rs.getString(3));
						
						initInstance.setSignInPrefixSuffixHex(rs.getInt(4)==1);
						initInstance.setSignInPrefix(rs.getString(5)==null?"":rs.getString(5));
						initInstance.setSignInSuffix(rs.getString(6)==null?"":rs.getString(6));
						initInstance.setSignInIDHex(rs.getInt(7)==1);
						
						initInstance.setHeartbeatPrefixSuffixHex(rs.getInt(8)==1);
						initInstance.setHeartbeatPrefix(rs.getString(9)==null?"":rs.getString(9));
						initInstance.setHeartbeatSuffix(rs.getString(10)==null?"":rs.getString(10));
						
						initInstance.setPacketSendInterval(rs.getInt(11));
						
						initInstance.setAcqGroup(new ArrayList<InitInstance.Group>());
						initInstance.setCtrlGroup(new ArrayList<InitInstance.Group>());
					}
					if(StringManagerUtils.isNotNull(rs.getString(14))){
						InitInstance.Group group=new InitInstance.Group();
						group.setGroupTimingInterval(rs.getInt(15));
						group.setAddr(new ArrayList<Integer>());
						int groupType=rs.getInt(16);
						String itemsStr="";
						try {
							itemsStr=StringManagerUtils.CLOBtoString2(rs.getClob(17));
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						if(StringManagerUtils.isNotNull(itemsStr)){
							String[] itemsArr=itemsStr.split(",");
							for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
								if(modbusProtocolConfig.getProtocol().get(i).getName().equalsIgnoreCase(rs.getString(12))){
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
				StringManagerUtils.printLog("实例初始化sql："+sql);
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
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		
		String initUrl=Config.getInstance().configFile.getAd().getInit().getInstance();
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
			if(conn==null || modbusProtocolConfig==null){
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
	public static int initDriverAcquisitionInfoConfig(List<String> wellList,int condition,String method){
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
			if(!jedis.exists("DeviceInfo".getBytes())){
				MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
			}
			List<byte[]> deviceInfoByteList =jedis.hvals("DeviceInfo".getBytes());
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
	
	public static int initDriverAcquisitionInfoConfigByProtocolName(String protocolName,String method){
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

			sql="select t.id from tbl_device t where t.instancecode in ( select t2.code from tbl_protocolinstance t2,tbl_acq_unit_conf t3 where t2.unitid=t3.id and t3.protocol='"+protocolName+"' )";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
			if(wellList.size()>0){
				initDriverAcquisitionInfoConfig(wellList,0,method);
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
			String sql="select t.id from tbl_device t where t.instancecode='"+instanceCode+"'";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
			if(wellList.size()>0){
				initDriverAcquisitionInfoConfig(wellList,0,method);
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
			String sql="select t.id from tbl_device t,tbl_protocolinstance t2 where t.instancecode=t2.code and t2.id="+instanceId;
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
			if(wellList.size()>0){
				initDriverAcquisitionInfoConfig(wellList,0,method);
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
			String sql="select t.id from tbl_device t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 where t.instancecode=t2.code and t2.unitid=t3.id and t3.id="+unitId;
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
			if(wellList.size()>0){
				initDriverAcquisitionInfoConfig(wellList,0,method);
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
			String sql="select t.id from tbl_device t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 ,tbl_acq_group2unit_conf t4,tbl_acq_group_conf t5 "
					+ " where t.instancecode=t2.code and t2.unitid=t3.id and t3.id=t4.unitid and t4.groupid=t5.id and t5.id="+groupId;
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
			if(wellList.size()>0){
				initDriverAcquisitionInfoConfig(wellList,0,method);
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
		String initUrl=Config.getInstance().configFile.getAd().getInit().getSMS();
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
		String initUrl=Config.getInstance().configFile.getAd().getInit().getServer().getUrl();
		StringBuffer json_buff = new StringBuffer();
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		try {

			json_buff.append("{");
			json_buff.append("\"IDOnlineStatusPushURL\":\""+Config.getInstance().configFile.getAd().getInit().getServer().getContent().getIdOnlineStatusPushURL()+"\",");
			json_buff.append("\"IDAcqGroupDataPushURL\":\""+Config.getInstance().configFile.getAd().getInit().getServer().getContent().getIdAcqGroupDataPushURL()+"\",");
			json_buff.append("\"IPPortOnlineStatusPushURL\":\""+Config.getInstance().configFile.getAd().getInit().getServer().getContent().getIpPortOnlineStatusPushURL()+"\",");
			json_buff.append("\"IPPortAcqGroupDataPushURL\":\""+Config.getInstance().configFile.getAd().getInit().getServer().getContent().getIpPortAcqGroupDataPushURL()+"\"");
			json_buff.append("}");
			StringManagerUtils.printLog("服务初始化："+json_buff.toString());
			if(initEnable){
				StringManagerUtils.sendPostMethod(initUrl,json_buff.toString(),"utf-8",0,0);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void initWellCommStatusByOnlineProbe(){
		AdOnlineProbeResponseData adOnlineProbeResponseData =adOnlineProbe();
		if(adOnlineProbeResponseData!=null){
			if(adOnlineProbeResponseData.getOnlineID()!=null && adOnlineProbeResponseData.getOnlineID().size()>0 ){
				String initRPCCommSql="update tbl_acqdata_latest t set t.commstatus=1 "
						+ " where t.commstatus<>1 "
						+ " and t.deviceid in ( select t2.id from tbl_device t2 where t2.tcptype='TCP Client' and t2.signinid in("+StringManagerUtils.joinStringArr2(adOnlineProbeResponseData.getOnlineID(), ",")+") )";
				try {
					JDBCUtil.updateRecord(initRPCCommSql, null);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(adOnlineProbeResponseData.getOnlineIPPort()!=null && adOnlineProbeResponseData.getOnlineIPPort().size()>0 ){
				String initRPCCommSql="update tbl_acqdata_latest t set t.commstatus=1 "
						+ " where t.commstatus<>1 "
						+ " and t.deviceid in ( select t2.id from tbl_device t2 where t2.tcptype='TCP Server' and t2.ipport in("+StringManagerUtils.joinStringArr2(adOnlineProbeResponseData.getOnlineIPPort(), ",")+") )";
				try {
					JDBCUtil.updateRecord(initRPCCommSql, null);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	public static int initWellCommStatus(){
		String initRPCCommSql="update tbl_acqdata_latest t set t.commstatus=0 ";
		int result=0;
		try {
			result = JDBCUtil.updateRecord(initRPCCommSql, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static int initWellDaliyData(){
		
		int result=0;
		try {
			result = JDBCUtil.callProcedure("prd_init_device_daily", null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static int initWellRPCCommStatus(){
		String initCommSql="update tbl_rpcacqdata_latest t set t.upcommstatus=0,t.downcommstatus=0";
		int result=0;
		try {
			result = JDBCUtil.updateRecord(initCommSql, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("static-access")
	public static void sendDeviceOfflineInfo(List<String> wellList){
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
			if(!jedis.exists("DeviceInfo".getBytes())){
				MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
			}
			deviceInfoByteList=jedis.hvals("DeviceInfo".getBytes());
			for(int i=0;i<deviceInfoByteList.size();i++){
				Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
				StringBuffer json_buff = new StringBuffer();
				if (obj instanceof RPCDeviceInfo) {
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
