package com.cosog.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cosog.model.calculate.AppRunStatusProbeResonanceData;
import com.cosog.model.calculate.CPUProbeResponseData;
import com.cosog.model.calculate.DeviceInfo;
import com.cosog.model.calculate.MemoryProbeResponseData;
import com.cosog.model.drive.InitializedDeviceInfo;
import com.cosog.task.MemoryDataManagerTask.RedisInfo;
import com.cosog.utils.AdvancedMemoryMonitorUtils;
import com.cosog.utils.CalculateUtils;
import com.cosog.utils.Config;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.StringManagerUtils;
import com.cosog.websocket.config.WebSocketByJavax;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

@Component("ResourceMonitoringTask")  
public class ResourceMonitoringTask {
    
    private static int deviceAmount=0;
	
    private static int acRunStatus=0;
    private static String acVersion="";
    private static int acLicense=0;
	
    private static int adRunStatus=0;
    private static String adVersion="";
    private static int adLicense=0;
    private static boolean licenseSign=false;
	
    private static String cpuUsedPercent="";
    private static String cpuUsedPercentValue="";
    private static int cpuUsedPercentAlarmLevel=0;
	
    private static String memUsedPercent="";
    private static String memUsedPercentValue="";
    private static String totalMemoryUsage="";
    private static int memUsedPercentAlarmLevel=0;
    
    private static int redisStatus=1; 
    private static String redisVersion="";
    
    private static String tableSpaceName="";
    
    private static TableSpaceInfo tableSpaceInfo=null;
    
    private static int save_cycle=60*10;
    private static String lastSaveTime="";
    
    @SuppressWarnings("unused")
//    @Scheduled(fixedRate = 1000*60*60*24*365*100)
	@Scheduled(cron = "0 30 23 * * ?")
	public void checkAndSendDBMonitoring(){
    	tableSpaceInfo=new TableSpaceInfo("","",0, 0, 0, 0, 0,0,new ArrayList<>(),new HashMap<>());
		try{
			tableSpaceInfo= getTableSpaceInfo();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		if(tableSpaceInfo.getConnStatus()==1){
			TableSpaceInfo lastTableSpaceInfo=getNewestTableSpaceInfo();
			
			Gson gson=new Gson();
			String sql="insert into tbl_dbmonitoring(acqTime,connstatus,tablespaceusedsize,tablespaceusedpercent,tablesize) "
					+ "values "
					+ "(to_date('"+tableSpaceInfo.getAcqTime()+"','yyyy-mm-dd hh24:mi:ss'),"+tableSpaceInfo.getConnStatus()+","+tableSpaceInfo.getUsed()+","+tableSpaceInfo.getUsedPercent()+",?)";
			List<String> clobDataList=new ArrayList<>();
			clobDataList.add(gson.toJson(tableSpaceInfo.getTableSizeList()));
			try {
				int r=OracleJdbcUtis.executeSqlUpdateClob(sql, clobDataList);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(lastTableSpaceInfo.getConnStatus()==1){
				StringBuffer result_json = new StringBuffer();
				StringBuffer tableInfo_json = new StringBuffer();
				result_json.append("{");
				result_json.append("\"currentTime\":\""+tableSpaceInfo.getAcqTime()+"\",");
				result_json.append("\"lastTime\":\""+lastTableSpaceInfo.getAcqTime()+"\",");
				result_json.append("\"currentUsed\":"+tableSpaceInfo.getUsed()+",");
				result_json.append("\"lastUsed\":"+lastTableSpaceInfo.getUsed()+",");
				result_json.append("\"usedDiff\":"+StringManagerUtils.doubleToString(tableSpaceInfo.getUsed()-lastTableSpaceInfo.getUsed(),2)+",");
				result_json.append("\"currentUsedPercent\":"+tableSpaceInfo.getUsedPercent()+",");
				result_json.append("\"lastUsedPercent\":"+lastTableSpaceInfo.getUsedPercent()+",");
				result_json.append("\"usedPercentDiff\":"+StringManagerUtils.doubleToString(tableSpaceInfo.getUsedPercent()-lastTableSpaceInfo.getUsedPercent(),2)+",");
				
				double currentTableSizeSum=0;
				double lastTableSizeSum=0;
				tableInfo_json.append("[");
				
				for(TableSize tableSize:tableSpaceInfo.getTableSizeList()){
					TableSize lastTableSize=lastTableSpaceInfo.getTableSizeMap().get(tableSize.getTableName());
					if(lastTableSize!=null){
						tableInfo_json.append("{");
						tableInfo_json.append("\"tableName\":\""+tableSize.getTableName()+"\",");
						
						tableInfo_json.append("\"currentSize\":"+tableSize.getTableTotalSize()+",");
						tableInfo_json.append("\"laseSize\":"+lastTableSize.getTableTotalSize()+",");
						tableInfo_json.append("\"sizeDiff\":"+StringManagerUtils.doubleToString(tableSize.getTableTotalSize()-lastTableSize.getTableTotalSize(),2)+",");
						
						tableInfo_json.append("\"currentCount\":"+tableSize.getCount()+",");
						tableInfo_json.append("\"laseCount\":"+lastTableSize.getCount()+",");
						tableInfo_json.append("\"countDiff\":"+StringManagerUtils.doubleToString(tableSize.getCount()-lastTableSize.getCount(),2)+"");
						
						tableInfo_json.append("},");
						
						currentTableSizeSum+=tableSize.getTableTotalSize();
						lastTableSizeSum+=lastTableSize.getTableTotalSize();
					}
				}
				if(tableInfo_json.toString().endsWith(",")){
					tableInfo_json.deleteCharAt(tableInfo_json.length() - 1);
				}
				
				tableInfo_json.append("]");
				
				result_json.append("\"currentTableSizeSum\":"+StringManagerUtils.doubleToString(currentTableSizeSum,2)+",");
				result_json.append("\"lastTableSizeSum\":"+StringManagerUtils.doubleToString(lastTableSizeSum,2)+",");
				result_json.append("\"tableList\":"+tableInfo_json+"");
				
				result_json.append("}");
				
				System.out.println("表空间使用率变化:"+result_json.toString());
			}
		}
		
		String sendData="{"
				+ "\"functionCode\":\"DBMonitoringData\","
				+ "\"dbConnStatus\":"+tableSpaceInfo.getConnStatus()+","
				+ "\"tableSpaceSize\":\""+(tableSpaceInfo.getUsed()+"Mb")+"\","
				+ "\"tableSpaceUsedPercent\":\""+(tableSpaceInfo.getUsedPercent()+"%")+"\","
				+ "\"tableSpaceUsedPercentAlarmLevel\":"+tableSpaceInfo.getAlarmLevel()+""
				+ "}";
		try {
			infoHandler().sendMessageToBy("ApWebSocketClient", sendData);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	@SuppressWarnings({ "static-access", "unused" })
	 @Scheduled(cron = "0/2 * * * * ?")
	public void checkAndSendResourceMonitoring(){
		String timeStr=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String probeMemUrl=Config.getInstance().configFile.getAd().getProbe().getMem();
		String probeCPUUrl=Config.getInstance().configFile.getAd().getProbe().getCpu();
		
		if(!Config.getInstance().configFile.getAp().getOthers().getIot() ){
			probeMemUrl=Config.getInstance().configFile.getAc().getProbe().getMem();
			probeCPUUrl=Config.getInstance().configFile.getAc().getProbe().getCpu();
		}
		
		String adAllOfflineUrl=stringManagerUtils.getProjectUrl()+"/api/acq/allDeviceOffline";
		String adStatusUrl=Config.getInstance().configFile.getAd().getProbe().getApp();
		
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		
		int resourceMonitoringSaveData=Config.getInstance().configFile.getAp().getOthers().getResourceMonitoringSaveData();
		
		try{
			deviceAmount=MemoryDataManagerTask.getDeviceInfoCount();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		if(tableSpaceInfo==null || tableSpaceInfo.getConnStatus()==0 || tableSpaceInfo.getUsedPercent()==0){
			tableSpaceInfo=new TableSpaceInfo("","",0, 0, 0, 0, 0,0,new ArrayList<>(),new HashMap<>());
			try{
				tableSpaceInfo= getTableSpaceInfo();
			}catch(Exception e){
				e.printStackTrace();
			}
			if(tableSpaceInfo.getConnStatus()==1){
				String sql="insert into tbl_dbmonitoring(acqTime,connstatus,tablespaceusedsize,tablespaceusedpercent,tablesize) "
						+ "values "
						+ "(to_date('"+tableSpaceInfo.getAcqTime()+"','yyyy-mm-dd hh24:mi:ss'),"+tableSpaceInfo.getConnStatus()+","+tableSpaceInfo.getUsed()+","+tableSpaceInfo.getUsedPercent()+",?)";
				List<String> clobDataList=new ArrayList<>();
				clobDataList.add(gson.toJson(tableSpaceInfo.getTableSizeList()));
				try {
					int r=OracleJdbcUtis.executeSqlUpdateClob(sql, clobDataList);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		RedisInfo redisInfo=MemoryDataManagerTask.getJedisInfo();
		redisVersion=redisInfo.getVersion();
		long cacheMaxMemory=redisInfo.getMaxmemory();
		long cacheUsedMemory=redisInfo.getUsed_memory();
		
		String cacheMaxMemory_human=StringManagerUtils.floatToString((cacheMaxMemory)/((float)(1024*1024)), 2);
		String cacheUsedMemory_human=StringManagerUtils.floatToString(cacheUsedMemory/((float)(1024*1024)), 2);
		
		
		
		if(redisInfo.getStatus()==1){
			if(redisStatus==0){
				MemoryDataManagerTask.loadMemoryData();
			}
		}
		redisStatus=redisInfo.getStatus();
		
		//ac状态检测
		try{
			AppRunStatusProbeResonanceData acStatusProbeResonanceData=CalculateUtils.appProbe("",10);
			if(acStatusProbeResonanceData!=null){
				acRunStatus=1;
				acVersion=acStatusProbeResonanceData.getVer();
				acLicense=acStatusProbeResonanceData.getLicenseNumber();
			}else{
				acRunStatus=0;
				acVersion="";
				acLicense=0;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(acLicense>0&&deviceAmount>acLicense){
			licenseSign=true;
		}else{
			licenseSign=false;
		}
		
		//ad状态检测
		adRunStatus=0;
		adVersion="";
		adLicense=0;
		if(Config.getInstance().configFile.getAp().getOthers().getIot() ){
			try{
				String adStatusProbeResponseDataStr=StringManagerUtils.sendPostMethod(adStatusUrl, "","utf-8",10,0);
				type = new TypeToken<AppRunStatusProbeResonanceData>() {}.getType();
				AppRunStatusProbeResonanceData adStatusProbeResonanceData=gson.fromJson(adStatusProbeResponseDataStr, type);
				if(adStatusProbeResonanceData!=null){
					adRunStatus=1;
					adVersion=adStatusProbeResonanceData.getVer();
					adLicense=adStatusProbeResonanceData.getLicenseNumber();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		//服务器资源监测
		cpuUsedPercent="";
		cpuUsedPercentValue="";
		cpuUsedPercentAlarmLevel=0;
		
		memUsedPercent="";
		memUsedPercentValue="";
		memUsedPercentAlarmLevel=0;
		totalMemoryUsage="";
		if( ((!Config.getInstance().configFile.getAp().getOthers().getIot()) && acRunStatus==1)
				|| (Config.getInstance().configFile.getAp().getOthers().getIot() && adRunStatus==1) 
				){
			String CPUProbeResponseDataStr=StringManagerUtils.sendPostMethod(probeCPUUrl, "","utf-8",10,0);
			String MemoryProbeResponseDataStr=StringManagerUtils.sendPostMethod(probeMemUrl, "","utf-8",10,0);
			type = new TypeToken<CPUProbeResponseData>() {}.getType();
			CPUProbeResponseData cpuProbeResponseData=gson.fromJson(CPUProbeResponseDataStr, type);
			type = new TypeToken<MemoryProbeResponseData>() {}.getType();
			MemoryProbeResponseData memoryProbeResponseData=gson.fromJson(MemoryProbeResponseDataStr, type);
			if(cpuProbeResponseData!=null){
				cpuUsedPercent="";
				cpuUsedPercentValue="";
				cpuUsedPercentAlarmLevel=0;
				
				for(int i=0;i<cpuProbeResponseData.getPercent().size();i++){
					if(cpuProbeResponseData.getPercent().get(i)>=60 && cpuProbeResponseData.getPercent().get(i)<80 && cpuUsedPercentAlarmLevel<1){
						cpuUsedPercentAlarmLevel=1;
					}else if(cpuProbeResponseData.getPercent().get(i)>=80 && cpuUsedPercentAlarmLevel<2){
						cpuUsedPercentAlarmLevel=2;
					}
					
					cpuUsedPercent+=cpuProbeResponseData.getPercent().get(i)+"%";
					cpuUsedPercentValue+=cpuProbeResponseData.getPercent().get(i);
					if(i<cpuProbeResponseData.getPercent().size()-1){
						cpuUsedPercent+=";";
						cpuUsedPercentValue+=";";
					}
				}
			}else{
				cpuUsedPercent="";
				cpuUsedPercentValue="";
				cpuUsedPercentAlarmLevel=0;
			}
			if(memoryProbeResponseData!=null){
				memUsedPercent="";
				memUsedPercentValue="";
				memUsedPercentAlarmLevel=0;
				totalMemoryUsage="";
				if(memoryProbeResponseData.getUsedPercent()>=60 && memoryProbeResponseData.getUsedPercent()<80){
					memUsedPercentAlarmLevel=1;
				}else if(memoryProbeResponseData.getUsedPercent()>=80){
					memUsedPercentAlarmLevel=2;
				}
				memUsedPercent=memoryProbeResponseData.getUsedPercent()+"%";
				memUsedPercentValue=memoryProbeResponseData.getUsedPercent()+"";
				totalMemoryUsage=memoryProbeResponseData.getUsed()+"";
			}else{
				memUsedPercent="";
				memUsedPercentValue="";
				memUsedPercentAlarmLevel=0;
				totalMemoryUsage="";
			}
		}
		
		long tomcatPhysicalMemory=getTomcatMemoryWithOshi();
		
		long JVMMemory=AdvancedMemoryMonitorUtils.getJVMMemory();
		long JVMHeapMemory=AdvancedMemoryMonitorUtils.getJVMHeapMemory();
		long JVMNonHeapMemory=AdvancedMemoryMonitorUtils.getJVMNonHeapMemory();
		
		long oraclePhysicalMemory=getOraclePhysicalMemory();
		
		boolean save=false;
		long timeDiff=StringManagerUtils.getTimeDifference(lastSaveTime, timeStr, "yyyy-MM-dd HH:mm:ss");
		if(timeDiff>=save_cycle*1000){
			save=true;
		}
		if(save){
			lastSaveTime=timeStr;
			Connection conn = null;
			CallableStatement cs= null;
			try{
				conn=OracleJdbcUtis.getConnection();
				cs= null;
				if(conn!=null){
					cs = conn.prepareCall("{call prd_save_resourcemonitoring(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
					cs.setString(1,timeStr );
					cs.setInt(2, acRunStatus);
					cs.setString(3, acVersion);
					cs.setInt(4, adRunStatus);
					cs.setString(5, adVersion);
					cs.setString(6, cpuUsedPercentValue);
					cs.setString(7, memUsedPercentValue);
					cs.setFloat(8, tableSpaceInfo.getUsedPercent());
					cs.setInt(9, redisStatus);
					cs.setLong(10, cacheMaxMemory);
					cs.setLong(11, cacheUsedMemory);
					cs.setInt(12, resourceMonitoringSaveData);
					cs.setString(13, totalMemoryUsage);
					cs.setLong(14, tomcatPhysicalMemory);
					cs.setLong(15, JVMMemory);
					cs.setLong(16, JVMHeapMemory);
					cs.setLong(17, JVMNonHeapMemory);
					cs.setLong(18, oraclePhysicalMemory);
					cs.executeUpdate();
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(cs!=null){
					try {
						cs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(conn!=null){
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		
		String sendData="{"
				+ "\"functionCode\":\"ResourceMonitoringData\","
				+ "\"acRunStatus\":\""+acRunStatus+"\","
				+ "\"acVersion\":\""+acVersion+"\","
				+ "\"cpuUsedPercent\":\""+cpuUsedPercent+"\","
				+ "\"cpuUsedPercentAlarmLevel\":"+cpuUsedPercentAlarmLevel+","
				+ "\"memUsedPercent\":\""+memUsedPercent+"\","
				+ "\"memUsedPercentAlarmLevel\":"+memUsedPercentAlarmLevel+","
				+ "\"adRunStatus\":\""+adRunStatus+"\","
				+ "\"adVersion\":\""+adVersion+"\","
				+ "\"dbConnStatus\":"+tableSpaceInfo.getConnStatus()+","
				+ "\"tableSpaceSize\":\""+(tableSpaceInfo.getUsed()+"Mb")+"\","
				+ "\"tableSpaceUsedPercent\":\""+(tableSpaceInfo.getUsedPercent()+"%")+"\","
				+ "\"tableSpaceUsedPercentAlarmLevel\":"+tableSpaceInfo.getAlarmLevel()+","
				+ "\"licenseSign\":"+licenseSign+","
				+ "\"deviceAmount\":"+deviceAmount+","
				+ "\"license\":"+acLicense+","
				+ "\"redisVersion\":\""+redisVersion+"\","
				+ "\"redisStatus\":\""+redisStatus+"\","
				+ "\"cacheMaxMemory\":\""+cacheMaxMemory_human+"\","
				+ "\"cacheUsedMemory\":\""+cacheUsedMemory_human+"\""
				+ "}";
		try {
			infoHandler().sendMessageToBy("ApWebSocketClient", sendData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Bean
    public static WebSocketByJavax infoHandler() {
        return new WebSocketByJavax();
    }
	
	public static  float getTableSpaceSize() throws SQLException{
        float result=0;
        String sql="SELECT tablespace_name,file_id,file_name,round(bytes / (1024 * 1024), 2) total_space "
        		+ " FROM dba_data_files t"
        		+ " where tablespace_name='AP_JF_DATA'";
        List<Object[]> list=OracleJdbcUtis.query(sql);
        
		for(Object[] obj:list){
			result=StringManagerUtils.stringToFloat(obj[3]+"");
		}
        return result;
    }
	
	public static  TableSpaceInfo getTableSpaceInfo(){
		String timeStr=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		Connection conn=null;
		TableSpaceInfo tableSpaceInfo=new TableSpaceInfo("","", 0, 0, 0, 0, 0,0,new ArrayList<>(),new HashMap<>());
		String userName=Config.getInstance().configFile.getAp().getDatasource().getUser();
        try{
        	conn=OracleJdbcUtis.getConnection();
            if(conn==null){
            	tableSpaceInfo=new TableSpaceInfo("","", 0, 0, 0, 0, 0,0,new ArrayList<>(),new HashMap<>());
            	return tableSpaceInfo;
            }else{
            	conn.close();  
            }
            tableSpaceInfo.setConnStatus(1);
            
            
    		if(!StringManagerUtils.isNotNull(tableSpaceName)){
    			try{
        			String tableSpaceSql="select default_tablespace from dba_users where username=upper('"+userName+"')";
        			List<Object[]> tableSpaceObjList=OracleJdbcUtis.query(tableSpaceSql);
        			if(tableSpaceObjList.size()>0){
        				tableSpaceName=tableSpaceObjList.get(0)[0].toString();
        			}
    			}catch(Exception e){
    	        	e.printStackTrace();
    	        }finally{
    	        	
    	        }
    		}
            
            try{
            	String sql="select usedsize_mb,maxsize_mb,used_percent2 from ( "
        				+ " select tablespace_name, "
        				+ " round(sum(maxbytes)/1024/1024，2) AS maxsize_mb, "
        				+ " round(sum(bytes)/1024/1024，2) AS size_mb, "
        				+ " round(sum(freebytes)/1024/1024，2) AS freesize_mb, "
        				+ " round(sum(bytes)/1024/1024-sum(freebytes)/1024/1024，2) as usedsize_mb, "
        				+ " round( ( round(sum(bytes)/1024/1024，2)-round(sum(freebytes)/1024/1024，2) )/round(sum(bytes)/1024/1024，2)*100，2) as used_percent, "
        				+ " round( ( round(sum(bytes)/1024/1024，2)-round(sum(freebytes)/1024/1024，2) )/round(sum(maxbytes)/1024/1024，2)*100，2) as used_percent2 "
        				+ " from( "
        				+ " select t1.file_name,t1.file_id,t1.tablespace_name,t1.bytes,t1.status,t1.autoextensible, "
        				+ " decode(t1.autoextensible,'YES',t1.maxbytes,t1.bytes) as maxbytes,t1.user_bytes,t2.bytes as freebytes "
        				+ " from dba_data_files t1 "
        				+ " left outer join (select file_id,sum(bytes) as bytes from dba_free_space group by file_Id) t2 on t1.file_id=t2.file_id"
//        				+ " where t1.bytes<t1.maxbytes"
        				+ " ) v "
        				+ " group by v.tablespace_name "
        				+ " ) "
        				+ " where upper(tablespace_name) = upper('"+tableSpaceName+"')";
            	List<Object[]> tableSpaceObjList=OracleJdbcUtis.query(sql);
    			if(tableSpaceObjList.size()>0){
    				tableSpaceInfo.setAcqTime(timeStr);
    				tableSpaceInfo.setTableSpaceName(tableSpaceName);
        			tableSpaceInfo.setUsed(StringManagerUtils.stringToFloat(tableSpaceObjList.get(0)[0]+""));
        			tableSpaceInfo.setTotal(StringManagerUtils.stringToFloat(tableSpaceObjList.get(0)[1]+""));
        			tableSpaceInfo.setFree(tableSpaceInfo.getTotal()-tableSpaceInfo.getUsed());
        			tableSpaceInfo.setUsedPercent(StringManagerUtils.stringToFloat(tableSpaceObjList.get(0)[2]+""));
        			
        			if(tableSpaceInfo.getUsedPercent()>=60 && tableSpaceInfo.getUsedPercent()<80){
        				tableSpaceInfo.setAlarmLevel(1);
        			}else if(tableSpaceInfo.getUsedPercent()>=80){
        				tableSpaceInfo.setAlarmLevel(2);
        			}else{
        				tableSpaceInfo.setAlarmLevel(0);
        			}
    			}
			}catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	
	        }
    		
            
            try{
            	String tableSizeSql="SELECT t.owner,t.table_name,"
                		+ " ROUND(SUM(CASE WHEN s.segment_type IN ('TABLE', 'TABLE PARTITION') THEN s.bytes ELSE 0 END) / 1024 / 1024, 2) AS table_data_mb,"
                		+ " ROUND(SUM(CASE WHEN s.segment_type IN ('LOBSEGMENT', 'LOB PARTITION') THEN s.bytes ELSE 0 END) / 1024 / 1024, 2) AS lob_segment_mb,"
                		+ " ROUND(SUM(CASE WHEN s.segment_type IN ('INDEX', 'INDEX PARTITION', 'LOBINDEX') THEN s.bytes ELSE 0 END) / 1024 / 1024, 2) AS index_mb,"
                		+ " ROUND(SUM(decode(s.bytes,null,0,s.bytes) ) / 1024 / 1024, 2) AS total_mb"
                		+ " FROM dba_tables t"
                		+ " LEFT JOIN "
                		+ " dba_segments s ON (s.owner = t.owner AND s.segment_name = t.table_name)"
                		+ " OR (s.owner = t.owner AND s.segment_name IN (SELECT segment_name FROM dba_lobs WHERE owner = t.owner AND table_name = t.table_name))"
                		+ " OR (s.owner = t.owner AND s.segment_name IN (SELECT index_name FROM dba_indexes WHERE owner = t.owner AND table_name = t.table_name UNION SELECT index_name FROM dba_lobs WHERE owner = t.owner AND table_name = t.table_name))"
                		+ " WHERE t.owner = upper('"+userName+"')"
                		+ " GROUP BY t.owner, t.table_name"
                		+ " ORDER BY table_name";
            	
            	List<Object[]> tableSizeObjList=OracleJdbcUtis.query(tableSizeSql);
            	for(int i=0;i<tableSizeObjList.size();i++){
            		TableSize tableSize=new TableSize();
        			tableSize.setTableName(tableSizeObjList.get(i)[1]+"");
        			tableSize.setTableDataSize(StringManagerUtils.stringToDouble(tableSizeObjList.get(i)[2]+""));
        			tableSize.setTableLobSize(StringManagerUtils.stringToDouble(tableSizeObjList.get(i)[3]+""));
        			tableSize.setTableIndexSize(StringManagerUtils.stringToDouble(tableSizeObjList.get(i)[4]+""));
        			tableSize.setTableTotalSize(StringManagerUtils.stringToDouble(tableSizeObjList.get(i)[5]+""));
        			tableSize.setCount(DatabaseMaintenanceTask.getDataBaseTableCount(tableSize.getTableName()));
        			if(tableSpaceInfo.getTableSizeList()==null){
        				tableSpaceInfo.setTableSizeList(new ArrayList<>());
        			}
        			tableSpaceInfo.getTableSizeList().add(tableSize);
        			
        			if(tableSpaceInfo.getTableSizeMap()==null){
        				tableSpaceInfo.setTableSizeMap(new HashMap<>());
        			}
        			tableSpaceInfo.getTableSizeMap().put(tableSize.getTableName(), tableSize);
        			Thread.yield();
            	}
			}catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	
	        }
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	
        }
        return tableSpaceInfo;
    }
	
	
	public static  TableSpaceInfo getNewestTableSpaceInfo(){
		TableSpaceInfo tableSpaceInfo=new TableSpaceInfo("","", 0, 0, 0, 0, 0,0,new ArrayList<>(),new HashMap<>());
		
		if(!StringManagerUtils.isNotNull(tableSpaceName)){
			try{
				String userName=Config.getInstance().configFile.getAp().getDatasource().getUser().toUpperCase();
				String tableSpaceSql="select default_tablespace from dba_users where username='"+userName+"'";
    			List<Object[]> tableSpaceObjList=OracleJdbcUtis.query(tableSpaceSql);
    			if(tableSpaceObjList.size()>0){
    				tableSpaceName=tableSpaceObjList.get(0)[0].toString();
    			}
			}catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	
	        }
		}
		
		String sql="select to_char(acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,connstatus,tablespaceusedsize,tablespaceusedpercent,tablesize from(select t.acqtime,t.connstatus,t.tablespaceusedsize,t.tablespaceusedpercent,t.tablesize from TBL_DBMONITORING t order by t.acqtime desc ) where rownum=1";
		List<Object[]> list=OracleJdbcUtis.query(sql);
		if(list.size()>0){
			tableSpaceInfo.setAcqTime(list.get(0)[0]+"");
			tableSpaceInfo.setTableSpaceName(tableSpaceName);
			tableSpaceInfo.setConnStatus(StringManagerUtils.stringToInteger(list.get(0)[1]+""));
			tableSpaceInfo.setUsed(StringManagerUtils.stringToFloat(list.get(0)[2]+""));
			tableSpaceInfo.setUsedPercent(StringManagerUtils.stringToFloat(list.get(0)[3]+""));
			String tableSizeStr=list.get(0)[4]+"";
			
			if(tableSpaceInfo.getUsedPercent()>=60 && tableSpaceInfo.getUsedPercent()<80){
				tableSpaceInfo.setAlarmLevel(1);
			}else if(tableSpaceInfo.getUsedPercent()>=80){
				tableSpaceInfo.setAlarmLevel(2);
			}else{
				tableSpaceInfo.setAlarmLevel(0);
			}
			
			if(tableSpaceInfo.getTableSizeList()==null){
				tableSpaceInfo.setTableSizeList(new ArrayList<>());
			}
			
			if(tableSpaceInfo.getTableSizeMap()==null){
				tableSpaceInfo.setTableSizeMap(new HashMap<>());
			}
			
			if(StringManagerUtils.isNotNull(tableSizeStr)){
				Gson gson = new Gson();
				java.lang.reflect.Type type = new TypeToken<List<TableSize>>(){}.getType();
		        List<TableSize> tableSizeList = gson.fromJson(tableSizeStr, type);
		        if(tableSizeList!=null){
		        	for(TableSize tableSize:tableSizeList){
		        		tableSpaceInfo.getTableSizeList().add(tableSize);
		        		tableSpaceInfo.getTableSizeMap().put(tableSize.getTableName(), tableSize);
		        	}
		        }
			}
		}
		
		return tableSpaceInfo;
		
    }
	
	public static  int getDeviceAmount2() throws IOException, SQLException{
		int deviceAmount=0;
		Map<String, Object> dataModelMap = DataModelMap.getMapObject();
		Map<Integer,InitializedDeviceInfo> initializedDeviceList=(Map<Integer,InitializedDeviceInfo>) dataModelMap.get("InitializedDeviceList");
		if(initializedDeviceList!=null){
			deviceAmount=initializedDeviceList.size();
		}
		return deviceAmount;
	}
	
	@SuppressWarnings("resource")
	public static  int getDeviceAmount() throws IOException, SQLException{
		int deviceAmount=0;
        boolean jedisStatus=MemoryDataManagerTask.getJedisStatus();
		try {
			if(jedisStatus){
				List<DeviceInfo> deviceList=MemoryDataManagerTask.getDeviceInfo();
				deviceAmount=(deviceList!=null?deviceList.size():0);
			}else{
	        	String sql="select count(1) from tbl_device t";
	        	List<Object[]> list=OracleJdbcUtis.query(sql);
	        	if(list.size()>0){
	        		deviceAmount=StringManagerUtils.stringToInteger(list.get(0)[0]+"");
	        	}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return deviceAmount;
	}
	
	public static class TableSize{
		public String tableName;
		public double tableDataSize;
		public double tableLobSize;
		public double tableIndexSize;
		public double tableTotalSize;
		public long count;
		public String getTableName() {
			return tableName;
		}
		public void setTableName(String tableName) {
			this.tableName = tableName;
		}
		public double getTableDataSize() {
			return tableDataSize;
		}
		public void setTableDataSize(double tableDataSize) {
			this.tableDataSize = tableDataSize;
		}
		public double getTableLobSize() {
			return tableLobSize;
		}
		public void setTableLobSize(double tableLobSize) {
			this.tableLobSize = tableLobSize;
		}
		public double getTableIndexSize() {
			return tableIndexSize;
		}
		public void setTableIndexSize(double tableIndexSize) {
			this.tableIndexSize = tableIndexSize;
		}
		public double getTableTotalSize() {
			return tableTotalSize;
		}
		public void setTableTotalSize(double tableTotalSize) {
			this.tableTotalSize = tableTotalSize;
		}
		public long getCount() {
			return count;
		}
		public void setCount(long count) {
			this.count = count;
		}
		
	}
	
	public static class TableSpaceInfo{
		public String acqTime;
		public String tableSpaceName="";
		public float total=0;
		public float free=0;
		public float used=0;
		public float usedPercent=0;
		public int alarmLevel=0;
		public int connStatus=0;
		public List<TableSize> tableSizeList;
		public Map<String,TableSize> tableSizeMap;
		public TableSpaceInfo() {
			super();
		}
		public TableSpaceInfo(String acqTime,String tableSpaceName,int connStatus, float total, float free, float used, float usedPercent,int alarmLevel,List<TableSize> tableSizeList,Map<String,TableSize> tableSizeMap) {
			super();
			this.acqTime = acqTime;
			this.tableSpaceName = tableSpaceName;
			this.connStatus = connStatus;
			this.total = total;
			this.free = free;
			this.used = used;
			this.usedPercent = usedPercent;
			this.alarmLevel = alarmLevel;
			this.tableSizeList = tableSizeList;
			this.tableSizeMap = tableSizeMap;
		}
		public String getTableSpaceName() {
			return tableSpaceName;
		}
		public void setTableSpaceName(String tableSpaceName) {
			this.tableSpaceName = tableSpaceName;
		}
		public float getTotal() {
			return total;
		}
		public void setTotal(float total) {
			this.total = total;
		}
		public float getFree() {
			return free;
		}
		public void setFree(float free) {
			this.free = free;
		}
		public float getUsed() {
			return used;
		}
		public void setUsed(float used) {
			this.used = used;
		}
		public float getUsedPercent() {
			return usedPercent;
		}
		public void setUsedPercent(float usedPercent) {
			this.usedPercent = usedPercent;
		}
		public int getAlarmLevel() {
			return alarmLevel;
		}
		public void setAlarmLevel(int alarmLevel) {
			this.alarmLevel = alarmLevel;
		}
		public int getConnStatus() {
			return connStatus;
		}
		public void setConnStatus(int connStatus) {
			this.connStatus = connStatus;
		}
		public List<TableSize> getTableSizeList() {
			return tableSizeList;
		}
		public void setTableSizeList(List<TableSize> tableSizeList) {
			this.tableSizeList = tableSizeList;
		}
		public String getAcqTime() {
			return acqTime;
		}
		public void setAcqTime(String acqTime) {
			this.acqTime = acqTime;
		}
		public Map<String, TableSize> getTableSizeMap() {
			return tableSizeMap;
		}
		public void setTableSizeMap(Map<String, TableSize> tableSizeMap) {
			this.tableSizeMap = tableSizeMap;
		}
	}
	
	public static long getOraclePhysicalMemory() {
	    try {
	        String os = System.getProperty("os.name").toLowerCase();
	        
	        if (os.contains("win")) {
	            return getOracleMemoryWindows();
	        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
	            return getOracleMemoryLinux();
	        } else {
	            System.err.println("Unsupported operating system: " + os);
	            return -1;
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        return -1;
	    }
	}

	// Windows专用方法
	private static long getOracleMemoryWindows() throws Exception {
	    Process process = Runtime.getRuntime().exec(
	        "wmic process where \"name like '%oracle%'\" get WorkingSetSize");
	    
	    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	    
	    long totalMemory = 0;
	    String line;
	    boolean firstLine = true;
	    
	    while ((line = reader.readLine()) != null) {
	        if (!firstLine && !line.trim().isEmpty()) {
	            try {
	                totalMemory += Long.parseLong(line.trim());
	            } catch (NumberFormatException e) {
	                // 忽略
	            }
	        }
	        firstLine = false;
	    }
	    
	    process.waitFor();
	    return totalMemory;
	}

	// Linux专用方法
	private static long getOracleMemoryLinux() throws Exception {
	    long totalMemory = 0;
	    
	    // 方法1: 使用ps命令获取所有Oracle相关进程的RSS内存
	    Process psProcess = Runtime.getRuntime().exec(
	        new String[]{"sh", "-c", "ps -eo pid,rss,comm | grep -i oracle | grep -v grep"});
	    
	    BufferedReader psReader = new BufferedReader(
	        new InputStreamReader(psProcess.getInputStream()));
	    
	    String line;
	    while ((line = psReader.readLine()) != null) {
	        if (!line.trim().isEmpty()) {
	            String[] parts = line.trim().split("\\s+");
	            if (parts.length >= 2) {
	                try {
	                    // RSS是以KB为单位的，需要转换为字节
	                    long memoryKB = Long.parseLong(parts[1]);
	                    totalMemory += memoryKB * 1024;
	                } catch (NumberFormatException e) {
	                    // 忽略格式错误的行
	                }
	            }
	        }
	    }
	    
	    psProcess.waitFor();
	    return totalMemory;
	}

	public static int getAcRunStatus() {
		return acRunStatus;
	}

	public static void setAcRunStatus(int acRunStatus) {
		ResourceMonitoringTask.acRunStatus = acRunStatus;
	}

	public static String getAcVersion() {
		return acVersion;
	}

	public static void setAcVersion(String acVersion) {
		ResourceMonitoringTask.acVersion = acVersion;
	}

	public static int getAcLicense() {
		return acLicense;
	}

	public static void setAcLicense(int acLicense) {
		ResourceMonitoringTask.acLicense = acLicense;
	}

	public static int getAdRunStatus() {
		return adRunStatus;
	}

	public static void setAdRunStatus(int adRunStatus) {
		ResourceMonitoringTask.adRunStatus = adRunStatus;
	}

	public static String getAdVersion() {
		return adVersion;
	}

	public static void setAdVersion(String adVersion) {
		ResourceMonitoringTask.adVersion = adVersion;
	}

	public static int getAdLicense() {
		return adLicense;
	}

	public static void setAdLicense(int adLicense) {
		ResourceMonitoringTask.adLicense = adLicense;
	}

	public static boolean isLicenseSign() {
		return licenseSign;
	}

	public static void setLicenseSign(boolean licenseSign) {
		ResourceMonitoringTask.licenseSign = licenseSign;
	}

	public static String getCpuUsedPercent() {
		return cpuUsedPercent;
	}

	public static void setCpuUsedPercent(String cpuUsedPercent) {
		ResourceMonitoringTask.cpuUsedPercent = cpuUsedPercent;
	}

	public static String getCpuUsedPercentValue() {
		return cpuUsedPercentValue;
	}

	public static void setCpuUsedPercentValue(String cpuUsedPercentValue) {
		ResourceMonitoringTask.cpuUsedPercentValue = cpuUsedPercentValue;
	}

	public static int getCpuUsedPercentAlarmLevel() {
		return cpuUsedPercentAlarmLevel;
	}

	public static void setCpuUsedPercentAlarmLevel(int cpuUsedPercentAlarmLevel) {
		ResourceMonitoringTask.cpuUsedPercentAlarmLevel = cpuUsedPercentAlarmLevel;
	}

	public static String getMemUsedPercent() {
		return memUsedPercent;
	}

	public static void setMemUsedPercent(String memUsedPercent) {
		ResourceMonitoringTask.memUsedPercent = memUsedPercent;
	}

	public static String getMemUsedPercentValue() {
		return memUsedPercentValue;
	}

	public static void setMemUsedPercentValue(String memUsedPercentValue) {
		ResourceMonitoringTask.memUsedPercentValue = memUsedPercentValue;
	}

	public static int getMemUsedPercentAlarmLevel() {
		return memUsedPercentAlarmLevel;
	}

	public static void setMemUsedPercentAlarmLevel(int memUsedPercentAlarmLevel) {
		ResourceMonitoringTask.memUsedPercentAlarmLevel = memUsedPercentAlarmLevel;
	}

	public static void setDeviceAmount(int deviceAmount) {
		ResourceMonitoringTask.deviceAmount = deviceAmount;
	}

	public static int getRedisStatus() {
		return redisStatus;
	}

	public static void setRedisStatus(int redisStatus) {
		ResourceMonitoringTask.redisStatus = redisStatus;
	}

	public static String getRedisVersion() {
		return redisVersion;
	}

	public static void setRedisVersion(String redisVersion) {
		ResourceMonitoringTask.redisVersion = redisVersion;
	}

	public static void setTableSpaceInfo(TableSpaceInfo tableSpaceInfo) {
		ResourceMonitoringTask.tableSpaceInfo = tableSpaceInfo;
	}
	
	public static long getTomcatMemoryWithOshi() {
		long r=0;
		try {
			SystemInfo systemInfo = new SystemInfo();
	        int pid = systemInfo.getOperatingSystem().getProcessId();
	        OSProcess process = systemInfo.getOperatingSystem().getProcess(pid);
	        GlobalMemory memory = systemInfo.getHardware().getMemory();
	        r=process.getResidentSetSize();
		} catch (Exception e) {
        	r=-1;
        }
        return r;
        
//        System.out.println("=== 与任务管理器对应关系 ===");
//        System.out.println("进程PID: " + pid);
//        System.out.println();
//        
//        // 进程内存指标
//        System.out.println("【进程内存使用】");
//        System.out.println("工作集(内存): " + formatBytes(process.getResidentSetSize()) + 
//                          " ← 任务管理器'内存'列");
//        System.out.println("提交大小: " + formatBytes(process.getVirtualSize()) + 
//                          " ← 任务管理器'提交大小'列");
//        
//        System.out.println();
//        System.out.println("【系统内存信息】");
//        System.out.println("总物理内存: " + formatBytes(memory.getTotal()) + 
//                          " ← 性能标签页'总物理内存'");
//        System.out.println("可用内存: " + formatBytes(memory.getAvailable()) + 
//                          " ← 性能标签页'可用物理内存'");
//        System.out.println("已使用内存: " + formatBytes(memory.getTotal() - memory.getAvailable()) + 
//                          " ← 性能标签页'已使用物理内存'");
//        
//        // 计算内存使用百分比
//        double memoryUsagePercent = (memory.getTotal() - memory.getAvailable()) * 100.0 / memory.getTotal();
//        System.out.println("内存使用率: " + String.format("%.1f%%", memoryUsagePercent) + 
//                          " ← 性能标签页内存使用率");
    }
	
//	private static String formatBytes(long bytes) {
//        if (bytes < 1024) return bytes + " B";
//        int exp = (int) (Math.log(bytes) / Math.log(1024));
//        char pre = "KMGTPE".charAt(exp-1);
//        return String.format("%.2f %sB", bytes / Math.pow(1024, exp), pre);
//    }
    
    public static long getTomcatMemoryFromTaskManager() {
    	long memoryKB=0;
        try {
            // 获取Tomcat进程的PID
            String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
            long pid = Long.parseLong(processName.split("@")[0]);
            
            // 使用Windows任务管理器命令
            Process process = Runtime.getRuntime().exec("tasklist /fi \"PID eq " + pid + "\" /fo csv /nh");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(String.valueOf(pid))) {
                	line=line.replaceAll("\",\"", "&&").replaceAll("\"", "").replaceAll(",", "").replace(" K", "");
                	line=line.replaceAll("&&",",");
//                	"javaw.exe","16424","Console","1","1,593,152 K"
                	
                    // 解析CSV格式的输出
                    String[] parts = line.split(",");
                    if (parts.length >= 5) {
                        String memoryStr = parts[4].replace("\"", "").replace(" K", "").trim();
                        memoryKB = Long.parseLong(memoryStr);
                    }
                }
            }
            reader.close();
            
        } catch (Exception e) {
        	memoryKB=-1;
            e.printStackTrace();
        }
        return memoryKB;
    }
}
