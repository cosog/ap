package com.gao.tast;

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

import com.gao.utils.Config;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("calculateDataManagerTast")  
public class CalculateDataManagerTast {
	private static Connection conn = null;   
    private static PreparedStatement pstmt = null;  
    private static ResultSet rs = null;  
	
	
	@Scheduled(cron = "0/1 * * * * ?")
	public void checkAndSendCalculateRequset() throws SQLException, UnsupportedEncodingException, ParseException{
		String sql="select count(1) from tbl_rpc_diagram_hist t where resultstatus in (0,2) and t.productiondataid >0";
		String url=Config.getProjectAccessPath()+"/calculateDataController/getBatchCalculateTime";
		String result="无未计算数据";
		int count=getCount(sql);
		closeDBConnection();
		if(count>0){
			System.out.println("发现未计算数据");
			result=StringManagerUtils.sendPostMethod(url, "","utf-8");
		}
	}
	
	/**
	 * 汇总计算
	 * */
//	@Scheduled(cron = "0/1 * * * * ?")
	@Scheduled(cron = "0 0 1/24 * * ?")
//	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void totalCalculationTast() throws SQLException, UnsupportedEncodingException, ParseException{
		String url=Config.getProjectAccessPath()+"/calculateDataController/FSDiagramDailyCalculation";
		String discreteDailyCalculationUrl=Config.getProjectAccessPath()+"/calculateDataController/DiscreteDailyCalculation";
		String rpmDailyCalculationUrl=Config.getProjectAccessPath()+"/calculateDataController/PCPRPMDailyCalculation";
		String PCPDiscreteDailyCalculationUrl=Config.getProjectAccessPath()+"/calculateDataController/PCPDiscreteDailyCalculation";
		@SuppressWarnings("unused")
		String result="";
		result=StringManagerUtils.sendPostMethod(url, "","utf-8");
		result=StringManagerUtils.sendPostMethod(discreteDailyCalculationUrl, "","utf-8");
		result=StringManagerUtils.sendPostMethod(rpmDailyCalculationUrl, "","utf-8");
		result=StringManagerUtils.sendPostMethod(PCPDiscreteDailyCalculationUrl, "","utf-8");
	}
	
	//离散数据实时汇总
	@Scheduled(cron = "0 30 0/1 * * ?")
	public void discreteTotalCalculationTast() throws SQLException, UnsupportedEncodingException, ParseException{
		String currentDate=StringManagerUtils.getCurrentTime();
		String discreteDailyCalculationUrl=Config.getProjectAccessPath()+"/calculateDataController/DiscreteDailyCalculation?date="+currentDate;
		String PCPDiscreteDailyCalculationUrl=Config.getProjectAccessPath()+"/calculateDataController/PCPDiscreteDailyCalculation?date="+currentDate;
		@SuppressWarnings("unused")
		String result="";
		result=StringManagerUtils.sendPostMethod(discreteDailyCalculationUrl, "","utf-8");
		result=StringManagerUtils.sendPostMethod(PCPDiscreteDailyCalculationUrl, "","utf-8");
	}
	
	/**
	 * 取对接数据库的数据
	 * */
//	@Scheduled(cron = "0 0/1 * * * ?")
//	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void getOuterDataRequset() throws SQLException, UnsupportedEncodingException, ParseException{
//		System.out.println("取功图:"+StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
		String url=Config.getProjectAccessPath()+"/graphicalUploadController/getOuterSurfaceCardData";
		StringManagerUtils.sendPostMethod(url, "","utf-8");
	}
	
	public static Connection getConnection(){
        try{
            String driver=Config.getDriver();
            String url = Config.getDriverUrl();
            String username = Config.getUser();
            String password = Config.getPassword();
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        }
        catch (Exception e){
            return null;
        }
    }
	
	public static Connection getOuterConnection(){  
        try{
            String driver=Config.getOuterDriver();
            String url = Config.getOuterDriverUrl(); 
            String username = Config.getOuterUser();
            String password = Config.getOuterPassword();  
            Class.forName(driver).newInstance();  
            Connection conn = DriverManager.getConnection(url, username, password);  
            return conn;  
        }  
        catch (Exception e){ 
            return null;  
        }  
    }
	
	public static void closeDBConnection(){  
        if(conn != null){  
            try{           
                pstmt.close();  
                rs.close();
                conn.close();  
            }catch(SQLException e){
                e.printStackTrace();  
            }finally{  
                try{  
                    pstmt.close();  
                    rs.close();
                }catch(SQLException e){  
                    e.printStackTrace();  
                }  
                conn = null;  
            }  
        }  
    }
	
	public static  int getCount(String sql) throws SQLException{  
        int result=0;
        conn=getConnection();
        if(conn==null){
        	return -1;
        }
		pstmt = conn.prepareStatement(sql); 
		rs=pstmt.executeQuery();
		while(rs.next()){
			result=rs.getInt(1);
		}
		closeDBConnection();
        return result;
    }
	
	//获取json文件路径
	public  String getWebJsonFilePath(String index4Str) {
        URL url = getClass().getProtectionDomain().getCodeSource()
                .getLocation();
        String path = url.toString();
        int index = path.indexOf(index4Str);
        if (index == -1) {
            index = path.indexOf("WEB-INF");
        }
 
        if (index == -1) {
            index = path.indexOf("bin");
        }
 
        path = path.substring(0, index);
        if (path.startsWith("zip")) {// 当class文件在war中时，此时返回zip:D:/...这样的路径
            path = path.substring(4);
        } else if (path.startsWith("file")) {// 当class文件在class文件中时，此时返回file:/D:/...这样的路径
            path = path.substring(6);
        } else if (path.startsWith("jar")) {// 当class文件在jar文件里面时，此时返回jar:file:/D:/...这样的路径
            path = path.substring(10);
        }
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        path=path+"data/"+index4Str;
        return path;
    }
	
	//读文件，返回字符串
	public String ReadFile(String path) {
	    File file = new File(path);
	    BufferedReader reader = null;
	    String laststr = "";
	    try {
	        reader = new BufferedReader(new FileReader(file));
	        String tempString = null;
	        @SuppressWarnings("unused")
			int line = 1;
	        //一次读入一行，直到读入null为文件结束
	        while ((tempString = reader.readLine()) != null) {
	            //显示行号
	            //System.out.println("line " + line + ": " + tempString);
	            laststr = laststr + tempString;
	            line++;
	        }
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e1) {}
	        }
	    }
	    return laststr;
	}
	
}
