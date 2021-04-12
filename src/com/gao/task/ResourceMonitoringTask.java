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
		float tableSpaceSize=getTableSpaceSize();
		
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
			cs.setFloat(6, tableSpaceSize);
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
				+ "\"tableSpaceSize\":\""+(tableSpaceSize+"Mb")+"\""
				+ "}";
		infoHandler().sendMessageToUserByModule("FSDiagramAnalysis_FSDiagramAnalysisSingleDetails", new TextMessage(sendData));
	}
	
	@Bean//这个注解会从Spring容器拿出Bean
    public static SpringWebSocketHandler infoHandler() {
        return new SpringWebSocketHandler();
    }
	
	public static  float getTableSpaceSize() throws SQLException{  
        float result=0;
        String sql="SELECT tablespace_name,file_id,file_name,round(bytes / (1024 * 1024), 2) total_space FROM dba_data_files where tablespace_name='AGILE_DATA'";
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
	
}
