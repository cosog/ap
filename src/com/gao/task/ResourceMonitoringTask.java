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
	
	
//	@Scheduled(cron = "0/1 * * * * ?")
	public void checkAndSendResourceMonitoring() throws SQLException, UnsupportedEncodingException, ParseException{
		String probeAppUrl=Config.getInstance().configFile.getAgileCalculate().getProbe().getApp()[0];
		String probeMemUrl=Config.getInstance().configFile.getAgileCalculate().getProbe().getMem()[0];
		String probeCPUUrl=Config.getInstance().configFile.getAgileCalculate().getProbe().getCpu()[0];
		
		String appRunStatus="停止";
		String appVersion="";
		String cpuUsedPercent="";
		String memUsedPercent="";
		String tableSpaceSize=getTableSpaceSize()+"Mb";
		
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		
		String appProbeResponseDataStr=StringManagerUtils.sendPostMethod(probeAppUrl, "","utf-8");
		type = new TypeToken<AppRunStatusProbeResonanceData>() {}.getType();
		AppRunStatusProbeResonanceData appRunStatusProbeResonanceData=gson.fromJson(appProbeResponseDataStr, type);
		
		
		if(appRunStatusProbeResonanceData!=null){
			appRunStatus="运行";
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
					if(i<cpuProbeResponseData.getPercent().size()-1){
						cpuUsedPercent+=";";
					}
				}
			}
			if(memoryProbeResponseData!=null){
				memUsedPercent=memoryProbeResponseData.getUsedPercent()+"%";
			}
		}
		
		String sendData="{"
				+ "\"appRunStatus\":\""+appRunStatus+"\","
				+ "\"appVersion\":\""+appVersion+"\","
				+ "\"cpuUsedPercent\":\""+cpuUsedPercent+"\","
				+ "\"memUsedPercent\":\""+memUsedPercent+"\","
				+ "\"tableSpaceSize\":\""+tableSpaceSize+"\""
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
