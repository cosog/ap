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

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gao.model.calculate.CommResponseData;
import com.gao.utils.Config;
import com.gao.utils.Config2;
import com.gao.utils.DataSourceConfig;
import com.gao.utils.OracleJdbcUtis;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("calculateDataManagerTast")  
public class CalculateDataManagerTask {
	private static Connection conn = null;   
    private static PreparedStatement pstmt = null;  
    private static ResultSet rs = null;  
    
    private static Connection conn_outer = null;   
    private static PreparedStatement pstmt_outer = null;  
    private static ResultSet rs_outer = null; 
	
	
	@Scheduled(cron = "0/1 * * * * ?")
	public void checkAndSendCalculateRequset() throws SQLException, UnsupportedEncodingException, ParseException{
		//判断SDK是否启动
		String probeUrl=Config.getInstance().configFile.getAgileCalculate().getProbe().getApp()[0];
		if(StringManagerUtils.checkHttpConnection(probeUrl)){
			String sql="select count(1) from tbl_rpc_diagram_hist t where resultstatus in (0,2) and t.productiondataid >0";
			String url=Config.getInstance().configFile.getServer().getAccessPath()+"/calculateDataController/getBatchCalculateTime";
			String result="无未计算数据";
			int count=getCount(sql);
			if(count>0){
				System.out.println("发现未计算数据");
				result=StringManagerUtils.sendPostMethod(url, "","utf-8");
			}
		}
	}
	
	/**
	 * 汇总计算
	 * */
	@Scheduled(cron = "0 0 1/24 * * ?")
	public void totalCalculationTast() throws SQLException, UnsupportedEncodingException, ParseException{
		String url=Config.getInstance().configFile.getServer().getAccessPath()+"/calculateDataController/dailyCalculation";
		String result=StringManagerUtils.sendPostMethod(url, "","utf-8");
	}
	
	//离散数据实时汇总
	@Scheduled(cron = "0 30 0/1 * * ?")
	public void discreteTotalCalculationTast() throws SQLException, UnsupportedEncodingException, ParseException{
		String currentDate=StringManagerUtils.getCurrentTime();
		@SuppressWarnings("static-access")
		String discreteDailyCalculationUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/calculateDataController/DiscreteDailyCalculation?date="+currentDate;
		@SuppressWarnings("static-access")
		String PCPDiscreteDailyCalculationUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/calculateDataController/PCPDiscreteDailyCalculation?date="+currentDate;
		@SuppressWarnings("unused")
		String result="";
		result=StringManagerUtils.sendPostMethod(discreteDailyCalculationUrl, "","utf-8");
		result=StringManagerUtils.sendPostMethod(PCPDiscreteDailyCalculationUrl, "","utf-8");
	}
	
	//订阅发布模式通信计算
	@SuppressWarnings({ "static-access", "unused" })
	@Scheduled(cron = "0 0/1 * * * ?")
	public void pubSubModelCommCalculationTast() throws SQLException, UnsupportedEncodingException, ParseException{
		String url=Config.getInstance().configFile.getServer().getAccessPath()+"/calculateDataController/pubSubModelCommCalculation";
		String result="";
		result=StringManagerUtils.sendPostMethod(url, "","utf-8");
	}
	
	public static  int getCount(String sql) throws SQLException{  
        int result=0;
        conn=OracleJdbcUtis.getConnection();
        if(conn==null){
        	return -1;
        }
		pstmt = conn.prepareStatement(sql); 
		rs=pstmt.executeQuery();
		while(rs.next()){
			result=rs.getInt(1);
		}
		OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
        return result;
    }
}
