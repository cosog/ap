package com.cosog.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cosog.model.DataMapping;
import com.cosog.model.KeyValue;
import com.cosog.thread.calculate.HCHistoryDataSyncThread;
import com.cosog.thread.calculate.InitIdAndIPPortThread;
import com.cosog.thread.calculate.ThreadPool;
import com.cosog.utils.Config;
import com.cosog.utils.HeiChaoCounterUtils;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;

@Component("SyncHCDataTast")  
public class SyncHCDataTast {
	private static SyncHCDataTast instance=new SyncHCDataTast();
	public static SyncHCDataTast getInstance(){
		return instance;
	}
	
//	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void syncFBAcqData(){
		loadTableColumn("TBL_RPCACQDATA_LATEST","oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@127.0.0.1:1521/orclpdb", "ap_hc", "Ap201#","tbl_pumpacqdata_latestcolumn");
		loadTableColumn("TBL_RPCACQDATA_HIST","oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@127.0.0.1:1521/orclpdb", "ap_hc", "Ap201#","tbl_pumpacqdata_histcolumn");
		loadHCMappingData();
		
//		syncHCRealtimeData(0);
//		syncHCRealtimeData(1);
		
		syncHCHistoryData();
	}
	
	public static int syncHCRealtimeData(int type){
		int result=0;
		int r=0;
		try{
			
			String deviceTable="tbl_rpcdevice";
			String realtimeTable="tbl_rpcacqdata_latest";
			if(type==1){
				deviceTable="tbl_pcpdevice";
				realtimeTable="tbl_pcpacqdata_latest";
			}
			
			Gson gson = new Gson();
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle();
			Map<String,DataMapping> loadProtocolMappingColumnMap=MemoryDataManagerTask.getProtocolMappingColumn();
			
			Map<String,String> HCMappingColumnMap=getFBMappingData();
			
			List<String> tableColumnList=getTableColumn(realtimeTable,"oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@127.0.0.1:1521/orclpdb", "ap_hc", "Ap201#",realtimeTable+"column");
			
			List<String> queryTableColumnList=new ArrayList<>();
			for(String columm:tableColumnList){
				for (String mappingColumn : HCMappingColumnMap.keySet()) {
					 if(columm.equalsIgnoreCase(mappingColumn)){
						 queryTableColumnList.add(mappingColumn);
						 break;
					 }
				}
			}
			
			String sql="select t.id,t.wellname,"
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
					+ "t2.commstatus,t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "t2.runstatus,t2.runtimeefficiency,t2.runtime,t2.runrange";
			for(String columm:queryTableColumnList){
				sql+=",t2."+columm;
			}
			sql+=" from "+deviceTable+" t, "+realtimeTable+" t2 "
				+ " where t.id=t2.wellid and t2.acqtime is not null "
				+ " order by t.id";
			List<Object[]> queryRealtimeDataList=OracleJdbcUtis.query(sql,"oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@127.0.0.1:1521/orclpdb", "ap_hc", "Ap201#");
			
			String updateSql="";
			String insertSql="";
			String insertColumns="";
			String insertValues="";
			String updateClobSql="";
			List<String> clobDataList=null;
			for(Object[] obj:queryRealtimeDataList){
				try{
					List<KeyValue> dataList=new ArrayList<>();
					
					int deviceId=StringManagerUtils.stringToInteger(obj[0]+"");
					int saveDeviceId=deviceId;
					if(type==1){
						saveDeviceId+=10000;
					}
					
					String deviceName=obj[1]+"";
					String acqtime=obj[2]+"";
					int commStatus=StringManagerUtils.stringToInteger(obj[3]+"");
					String commTime=obj[4]+"";
					String commTimeEfficiency=obj[5]+"";
					String commRange=obj[6]+"";
					int runStatus=StringManagerUtils.stringToInteger(obj[7]+"");
					String runTime=obj[8]+"";
					String runTimeEfficiency=obj[9]+"";
					String runRange=obj[10]+"";
					String acqData="";
					for(int i=0;i<queryTableColumnList.size();i++){
						String column_hc=queryTableColumnList.get(i);
						if(HCMappingColumnMap.containsKey(column_hc)){
							String value=obj[i+11]+"";
							String name=HCMappingColumnMap.get(column_hc);
							if(loadProtocolMappingColumnByTitleMap.containsKey(name) && StringManagerUtils.isNotNull(value)){
								String mappingColumn=loadProtocolMappingColumnByTitleMap.get(name).getMappingColumn();
								KeyValue keyValue=new KeyValue(mappingColumn,value);
								dataList.add(keyValue);
							}
						}
					}
					if(dataList.size()>0){
						acqData=gson.toJson(dataList);
					}
					
					updateSql="update tbl_acqdata_latest t set t.acqtime=to_date('"+acqtime+"','yyyy-mm-dd hh24:mi:ss'), t.commStatus="+commStatus+",t.commTime="+commTime+",commTimeEfficiency="+commTimeEfficiency+","
							+ "runStatus="+runStatus+",runTime="+runTime+",runTimeEfficiency="+runTimeEfficiency;
					insertSql="";
					insertColumns="deviceId,acqtime,commStatus,commTime,commTimeEfficiency,runStatus,runTime,runTimeEfficiency";
					insertValues=saveDeviceId+",to_date('"+acqtime+"','yyyy-mm-dd hh24:mi:ss'),"+commStatus+","+commTime+","+commTimeEfficiency+","+runStatus+","+runTime+","+runTimeEfficiency;
					
					
					updateSql+=" where t.deviceId="+saveDeviceId;
					insertSql= "insert into tbl_acqdata_latest ("+insertColumns+") values ("+insertValues+")";
					
					r=OracleJdbcUtis.executeSqlUpdate(updateSql);
					if(r==0){
						r=OracleJdbcUtis.executeSqlUpdate(insertSql);
					}
					
					updateClobSql="update tbl_acqdata_latest t set t.commRange=?,t.runRange=?,t.acqData=? where t.deviceId="+saveDeviceId;
					clobDataList=new ArrayList<>();
					clobDataList.add(commRange);
					clobDataList.add(runRange);
					clobDataList.add(acqData);
					OracleJdbcUtis.executeSqlUpdateClob(updateClobSql, clobDataList);
					
					result+=r;
				}catch(Exception e){
					e.printStackTrace();
					continue;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		if(type==0){
			System.out.println("泵设备实时数据同步完成");
		}else if(type==1){
			System.out.println("管设备实时数据同步完成");
		}
		
		return result;
	}
	
	public static void syncHCHistoryData(){
		String sql="select t.id,t.devicename,to_char(v.acqtime,'yyyy-mm-dd hh24:mi:ss') "
				+ " from tbl_device t "
				+ " left outer join (select t2.deviceid,max(t2.acqtime) as acqtime from tbl_acqdata_hist t2 group by t2.deviceid) v on t.id=v.deviceid "
				+ " where 1=1"
				+ " and t.id > 10482"
				+ " order by t.id";
		ThreadPool executor = new ThreadPool("syncHCHistoryData",
				10, 
				20, 
				5, 
				TimeUnit.SECONDS, 
				0);
		HeiChaoCounterUtils.reset();//加法计数器清零
		HeiChaoCounterUtils.calculateSpeedTimer();//创建统计计算速度计时器
		do{
			List<Object[]> deviceList=OracleJdbcUtis.query(sql);
			HeiChaoCounterUtils.initCountDownLatch(deviceList.size());
			
			long sum1=HeiChaoCounterUtils.sum();
			long calculateStartTime=System.nanoTime();
			for(Object[] obj:deviceList){
				int deviceId=StringManagerUtils.stringToInteger(obj[0]+"");
				String acqtime=obj[2]+"";
				executor.execute(new HCHistoryDataSyncThread(deviceId, acqtime));
			}
			try {
				HeiChaoCounterUtils.await();//等待所有线程执行完毕
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long calculateEndTime=System.nanoTime();
			long sum2=HeiChaoCounterUtils.sum();
			System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":Thread complete，execute count:"+(sum2-sum1)+",time:"+StringManagerUtils.getTimeDiff(calculateStartTime, calculateEndTime));
		}while(true);
		
		
		
		
	}
	
	public static List<String > getTableColumn(String tableName,String driver,String url,String username,String password,String key){
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		if(!dataModelMap.containsKey(key)){
			loadTableColumn(tableName,driver,url,username,password,key);
		}
		List<String> tableColumnList=null;
		if(dataModelMap.containsKey(key)){
			tableColumnList=(List<String>) dataModelMap.get(key);
		}else{
			tableColumnList=new ArrayList<>();
		}
		return tableColumnList;
	}
	
	public static void loadTableColumn(String tableName,String driver,String url,String username,String password,String key){
		List<String> tableColumnList=new ArrayList<>();
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		String sql="select t.COLUMN_NAME from user_tab_cols t "
				+ " where t.TABLE_NAME=UPPER('"+tableName+"') "
				+ " order by t.COLUMN_ID";
		try {
			conn = OracleJdbcUtis.getConnection(driver, url, username, password);   
			if(conn!=null){
	        	pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					String columnName=rs.getString(1);
					if(columnName.toUpperCase().startsWith("C_")){
						tableColumnList.add(columnName);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		dataModelMap.put(key, tableColumnList);
	}
	
	public static Map<String,String> getFBMappingData(){
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		if(!dataModelMap.containsKey("HCMappingColumnMap")){
			loadHCMappingData();
		}
		Map<String,String> HCMappingColumnMap=null;
		if(dataModelMap.containsKey("HCMappingColumnMap")){
			HCMappingColumnMap=(Map<String,String>) dataModelMap.get("HCMappingColumnMap");
		}else{
			HCMappingColumnMap=new LinkedHashMap<String,String>();
		}
		return HCMappingColumnMap;
	}
	
	public static void loadHCMappingData(){
		Map<String,String> HCMappingColumnMap=new LinkedHashMap<String,String>();
		
		String hcMappingSql="select t.mappingcolumn,t.name "
				+ " from TBL_DATAMAPPING t "
				+ " where t.name is not null "
				+ " and t.mappingcolumn is not null "
				+ " and t.protocoltype=0"
				+ " order by t.mappingcolumn ";
		List<Object[]> queryFBMappingDataList=OracleJdbcUtis.query(hcMappingSql,"oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@127.0.0.1:1521/orclpdb", "ap_hc", "Ap201#");
		for(Object[] obj:queryFBMappingDataList){
			HCMappingColumnMap.put(obj[0]+"", obj[1]+"");
		}
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		dataModelMap.put("HCMappingColumnMap", HCMappingColumnMap);
	}
}
