package com.gao.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import com.gao.model.calculate.AppRunStatusProbeResonanceData;
import com.gao.model.calculate.CPUProbeResponseData;
import com.gao.model.calculate.CommResponseData;
import com.gao.model.calculate.MemoryProbeResponseData;
import com.gao.model.drive.KafkaConfig;
import com.gao.utils.Config;
import com.gao.utils.Config2;
import com.gao.utils.OracleJdbcUtis;
import com.gao.utils.StringManagerUtils;
import com.gao.websocket.config.WebSocketByJavax;
import com.gao.websocket.handler.SpringWebSocketHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("ResourceMonitoringTask")  
public class ResourceMonitoringTask {
	private static Connection conn = null;   
    private static PreparedStatement pstmt = null;  
    private static ResultSet rs = null;  
    
    private static Connection conn_outer = null;   
    private static PreparedStatement pstmt_outer = null;  
    private static ResultSet rs_outer = null; 
	
    private static CallableStatement cs= null; 
    
	@SuppressWarnings("static-access")
	@Scheduled(cron = "0/1 * * * * ?")
	public void checkAndSendResourceMonitoring() throws SQLException, UnsupportedEncodingException, ParseException{
		String probeAppUrl=Config.getInstance().configFile.getAgileCalculate().getProbe().getApp()[0];
		String probeMemUrl=Config.getInstance().configFile.getAgileCalculate().getProbe().getMem()[0];
		String probeCPUUrl=Config.getInstance().configFile.getAgileCalculate().getProbe().getCpu()[0];
		String appRunStatus="停止";
		int appRunStatusValue=0;
		String appVersion="";
		String cpuUsedPercent="";
		String cpuUsedPercentValue="";
		String memUsedPercent="";
		String memUsedPercentValue="";
		TableSpaceInfo tableSpaceInfo= getTableSpaceInfo();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String appProbeResponseDataStr=StringManagerUtils.sendPostMethod(probeAppUrl, "","utf-8");
		type = new TypeToken<AppRunStatusProbeResonanceData>() {}.getType();
		AppRunStatusProbeResonanceData appRunStatusProbeResonanceData=gson.fromJson(appProbeResponseDataStr, type);
		if(appRunStatusProbeResonanceData!=null){
			appRunStatus="运行";
			appRunStatusValue=1;
			appVersion=appRunStatusProbeResonanceData.getVer();
			String CPUProbeResponseDataStr=StringManagerUtils.sendPostMethod(probeCPUUrl, "","utf-8");
			String MemoryProbeResponseDataStr=StringManagerUtils.sendPostMethod(probeMemUrl, "","utf-8");
			type = new TypeToken<CPUProbeResponseData>() {}.getType();
			CPUProbeResponseData cpuProbeResponseData=gson.fromJson(CPUProbeResponseDataStr, type);
			type = new TypeToken<MemoryProbeResponseData>() {}.getType();
			MemoryProbeResponseData memoryProbeResponseData=gson.fromJson(MemoryProbeResponseDataStr, type);
			if(cpuProbeResponseData!=null){
				for(int i=0;i<cpuProbeResponseData.getPercent().size();i++){
					cpuUsedPercent+=cpuProbeResponseData.getPercent().get(i)+"%";
					cpuUsedPercentValue+=cpuProbeResponseData.getPercent().get(i);
					if(i<cpuProbeResponseData.getPercent().size()-1){
						cpuUsedPercent+=";";
						cpuUsedPercentValue+=";";
					}
				}
			}
			if(memoryProbeResponseData!=null){
				memUsedPercent=memoryProbeResponseData.getUsedPercent()+"%";
				memUsedPercentValue=memoryProbeResponseData.getUsedPercent()+"";
			}
		}
		conn=OracleJdbcUtis.getConnection();
		if(conn!=null){
			cs = conn.prepareCall("{call prd_save_resourcemonitoring(?,?,?,?,?,?)}");
			cs.setString(1, StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
			cs.setInt(2, appRunStatusValue);
			cs.setString(3, appVersion);
			cs.setString(4, cpuUsedPercentValue);
			cs.setString(5, memUsedPercentValue);
			cs.setFloat(6, tableSpaceInfo.getUsed());
			cs.executeUpdate();
			if(cs!=null){
				cs.close();
			}
			if(conn!=null){
				conn.close();
			}
		}
		String sendData="{"
				+ "\"appRunStatus\":\""+appRunStatus+"\","
				+ "\"appVersion\":\""+appVersion+"\","
				+ "\"cpuUsedPercent\":\""+cpuUsedPercent+"\","
				+ "\"memUsedPercent\":\""+memUsedPercent+"\","
				+ "\"tableSpaceSize\":\""+(tableSpaceInfo.getUsed()+"Mb")+"\","
				+ "\"tableSpaceUsedPercent\":\""+(tableSpaceInfo.getUsedPercent2()+"%")+"\""
				+ "}";
		try {
			infoHandler().sendMessageToUserByModule("FSDiagramAnalysis_FSDiagramAnalysisSingleDetails", new TextMessage(sendData));
			infoHandler2().sendMessageToBy("FSDiagramAnalysis_FSDiagramAnalysisSingleDetails", sendData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Bean
    public static SpringWebSocketHandler infoHandler() {
        return new SpringWebSocketHandler();
    }
	
	@Bean
    public static WebSocketByJavax infoHandler2() {
        return new WebSocketByJavax();
    }
	
	public static  float getTableSpaceSize() throws SQLException{  
        float result=0;
        String sql="SELECT tablespace_name,file_id,file_name,round(bytes / (1024 * 1024), 2) total_space "
        		+ " FROM dba_data_files t"
        		+ " where tablespace_name='AGILE_DATA'";
        conn=OracleJdbcUtis.getConnection();
        if(conn==null){
        	return -1;
        }
		pstmt = conn.prepareStatement(sql); 
		rs=pstmt.executeQuery();
		while(rs.next()){
			result=rs.getFloat(4);
		}
		OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
        return result;
    }
	
	public static  TableSpaceInfo getTableSpaceInfo() throws SQLException{
        String sql="SELECT a.tablespace_name,"
        		+ "round(total / (1024 * 1024), 2) total,"
        		+ "round(free / (1024 * 1024), 2) free,"
        		+ "round((total - free) / (1024 * 1024), 2) used,"
        		+ " round((total - free) / total, 4) * 100 usedpercent,"
        		+ " round((total - free) / (1024*1024*1024*32), 4) * 100 usedpercent2 "
        		+ " FROM  "
        		+ " (SELECT tablespace_name, SUM(bytes) free FROM dba_free_space GROUP BY tablespace_name) a,  "
        		+ " (SELECT file_id,tablespace_name, SUM(bytes) total FROM dba_data_files GROUP BY file_id,tablespace_name) b   "
        		+ " WHERE a.tablespace_name = b.tablespace_name "
        		+ " and Upper(a.tablespace_name) like 'AGILE_DATA%' "
        		+ " order by b.file_id ";
        TableSpaceInfo tableSpaceInfo=new TableSpaceInfo();
        conn=OracleJdbcUtis.getConnection();
        if(conn==null){
        	return tableSpaceInfo;
        }
		pstmt = conn.prepareStatement(sql); 
		rs=pstmt.executeQuery();
		while(rs.next()){
			tableSpaceInfo.setTableSpaceName(rs.getString(1));
			tableSpaceInfo.setTotal(tableSpaceInfo.getTotal()+1024*32);
			tableSpaceInfo.setFree(tableSpaceInfo.getFree()+rs.getFloat(3));
			tableSpaceInfo.setUsed(tableSpaceInfo.getUsed()+rs.getFloat(4));
			tableSpaceInfo.setUsedPercent2(StringManagerUtils.stringToFloat((tableSpaceInfo.getUsed()*100/tableSpaceInfo.getTotal())+"", 2));
		}
		OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
        return tableSpaceInfo;
    }
	
	public static class TableSpaceInfo{
		public String tableSpaceName;
		public float total=0;
		public float free=0;
		public float used=0;
		public float usedPercent=0;
		public float usedPercent2=0;
		public TableSpaceInfo() {
			super();
		}
		public TableSpaceInfo(String tableSpaceName, float total, float free, float used, float usedPercent,
				float usedPercent2) {
			super();
			this.tableSpaceName = tableSpaceName;
			this.total = total;
			this.free = free;
			this.used = used;
			this.usedPercent = usedPercent;
			this.usedPercent2 = usedPercent2;
		}
		public float getUsedPercent2() {
			return usedPercent2;
		}
		public void setUsedPercent2(float usedPercent2) {
			this.usedPercent2 = usedPercent2;
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
	}
}
