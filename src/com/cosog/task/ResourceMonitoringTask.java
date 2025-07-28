package com.cosog.task;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.cosog.utils.CalculateUtils;
import com.cosog.utils.Config;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.StringManagerUtils;
import com.cosog.websocket.config.WebSocketByJavax;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    private static int memUsedPercentAlarmLevel=0;
    
    private static int redisStatus=1; 
    private static String redisVersion="";
    
    private static String tableSpaceName="";
    
    private static TableSpaceInfo tableSpaceInfo=null;
    
    @Scheduled(cron = "0 30 23 * * ?")
	public void checkAndSendDBMonitoring(){
    	tableSpaceInfo=new TableSpaceInfo("",0, 0, 0, 0, 0,0);
		try{
			tableSpaceInfo= getTableSpaceInfo();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(tableSpaceInfo.getConnStatus()==1){
			String timeStr=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			String sql="insert into tbl_dbmonitoring(acqTime,connstatus,tablespaceusedsize,tablespaceusedpercent) "
					+ "values "
					+ "(to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss'),"+tableSpaceInfo.getConnStatus()+","+tableSpaceInfo.getUsed()+","+tableSpaceInfo.getUsedPercent()+")";
			int r=OracleJdbcUtis.executeSqlUpdate(sql);
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
    
    
    
	@SuppressWarnings("static-access")
	@Scheduled(cron = "0/2 * * * * ?")
	public void checkAndSendResourceMonitoring(){
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
			tableSpaceInfo=new TableSpaceInfo("",0, 0, 0, 0, 0,0);
			try{
				tableSpaceInfo= getTableSpaceInfo();
			}catch(Exception e){
				e.printStackTrace();
			}
			if(tableSpaceInfo.getConnStatus()==1){
				String timeStr=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
				String sql="insert into tbl_dbmonitoring(acqTime,connstatus,tablespaceusedsize,tablespaceusedpercent) "
						+ "values "
						+ "(to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss'),"+tableSpaceInfo.getConnStatus()+","+tableSpaceInfo.getUsed()+","+tableSpaceInfo.getUsedPercent()+")";
				int r=OracleJdbcUtis.executeSqlUpdate(sql);
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
				if(memoryProbeResponseData.getUsedPercent()>=60 && memoryProbeResponseData.getUsedPercent()<80){
					memUsedPercentAlarmLevel=1;
				}else if(memoryProbeResponseData.getUsedPercent()>=80){
					memUsedPercentAlarmLevel=2;
				}
				memUsedPercent=memoryProbeResponseData.getUsedPercent()+"%";
				memUsedPercentValue=memoryProbeResponseData.getUsedPercent()+"";
			}else{
				memUsedPercent="";
				memUsedPercentValue="";
				memUsedPercentAlarmLevel=0;
			}
		}
		
		Connection conn = null;
		CallableStatement cs= null;
		try{
			conn=OracleJdbcUtis.getConnection();
			cs= null;
			if(conn!=null){
				cs = conn.prepareCall("{call prd_save_resourcemonitoring(?,?,?,?,?,?,?,?,?,?,?,?)}");
				cs.setString(1, StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
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
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TableSpaceInfo tableSpaceInfo=new TableSpaceInfo("", 0, 0, 0, 0, 0,0);
        try{
        	conn=OracleJdbcUtis.getConnection();
            if(conn==null){
            	tableSpaceInfo=new TableSpaceInfo("", 0, 0, 0, 0, 0,0);
            	return tableSpaceInfo;
            }
            tableSpaceInfo.setConnStatus(1);
    		if(!StringManagerUtils.isNotNull(tableSpaceName)){
    			try{
    				String userName=Config.getInstance().configFile.getAp().getDatasource().getUser();
        			String tableSpaceSql="select default_tablespace from dba_users where username=upper('"+userName+"')";
        			pstmt = conn.prepareStatement(tableSpaceSql); 
        			pstmt.setQueryTimeout(10);
        			rs=pstmt.executeQuery();
        			while(rs.next()){
        				tableSpaceName=rs.getString(1);
        				break;
        			}
    			}catch(Exception e){
    	        	e.printStackTrace();
    	        }finally{
    	        	OracleJdbcUtis.closeDBConnection(pstmt, rs);
    	        }
    			
    		}
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
//    				+ " where t1.bytes<t1.maxbytes"
    				+ " ) v "
    				+ " group by v.tablespace_name "
    				+ " ) "
    				+ " where upper(tablespace_name) = upper('"+tableSpaceName+"')";
            
    		pstmt = conn.prepareStatement(sql); 
    		pstmt.setQueryTimeout(10);
    		rs=pstmt.executeQuery();
    		while(rs.next()){
    			tableSpaceInfo.setTableSpaceName(tableSpaceName);
    			tableSpaceInfo.setUsed(rs.getFloat(1));
    			tableSpaceInfo.setTotal(rs.getFloat(2));
    			tableSpaceInfo.setFree(rs.getFloat(2)-rs.getFloat(1));
    			tableSpaceInfo.setUsedPercent(rs.getFloat(3));
    			
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
        	OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
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
	
	public static class TableSpaceInfo{
		public String tableSpaceName="";
		public float total=0;
		public float free=0;
		public float used=0;
		public float usedPercent=0;
		public int alarmLevel=0;
		public int connStatus=0;
		public TableSpaceInfo() {
			super();
		}
		public TableSpaceInfo(String tableSpaceName,int connStatus, float total, float free, float used, float usedPercent,int alarmLevel) {
			super();
			this.tableSpaceName = tableSpaceName;
			this.connStatus = connStatus;
			this.total = total;
			this.free = free;
			this.used = used;
			this.usedPercent = usedPercent;
			this.alarmLevel = alarmLevel;
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
}
