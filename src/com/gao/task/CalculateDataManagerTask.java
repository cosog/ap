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
	
	
//	@Scheduled(cron = "0/1 * * * * ?")
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
//	@Scheduled(cron = "0 0 1/24 * * ?")
//	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void totalCalculationTast() throws SQLException, UnsupportedEncodingException, ParseException{
//		String url=Config.getInstance().configFile.getServer().getAccessPath()+"/calculateDataController/FSDiagramDailyCalculation";
//		String discreteDailyCalculationUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/calculateDataController/DiscreteDailyCalculation";
//		String rpmDailyCalculationUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/calculateDataController/PCPRPMDailyCalculation";
//		String PCPDiscreteDailyCalculationUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/calculateDataController/PCPDiscreteDailyCalculation";
//		@SuppressWarnings("unused")
//		String result="";
//		result=StringManagerUtils.sendPostMethod(url, "","utf-8");
//		result=StringManagerUtils.sendPostMethod(discreteDailyCalculationUrl, "","utf-8");
//		result=StringManagerUtils.sendPostMethod(rpmDailyCalculationUrl, "","utf-8");
//		result=StringManagerUtils.sendPostMethod(PCPDiscreteDailyCalculationUrl, "","utf-8");
		
		String url=Config.getInstance().configFile.getServer().getAccessPath()+"/calculateDataController/dailyCalculation";
		String result=StringManagerUtils.sendPostMethod(url, "","utf-8");
	}
	
	//离散数据实时汇总
//	@Scheduled(cron = "0 30 0/1 * * ?")
	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void discreteTotalCalculationTast() throws SQLException, UnsupportedEncodingException, ParseException{
		String currentDate=StringManagerUtils.getCurrentTime();
//		currentDate="2021-03-10";
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
//	@Scheduled(cron = "0 0/1 * * * ?")
//	@Scheduled(cron = "0/30 * * * * ?")
	public void pubSubModelCommCalculationTast() throws SQLException, UnsupportedEncodingException, ParseException{
		String url=Config.getInstance().configFile.getServer().getAccessPath()+"/calculateDataController/pubSubModelCommCalculation";
		String result="";
		result=StringManagerUtils.sendPostMethod(url, "","utf-8");
	}
	
	/**
	 * 取对接数据库的数据
	 * */
//	@Scheduled(cron = "0 0/1 * * * ?")
//	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void getOuterDataRequset() throws SQLException, UnsupportedEncodingException, ParseException{
		
		String sql="select t.id,t2.pumpsettingdepth,to_char(t3.acqTime,'yyyy-mm-dd hh24:mi:ss') "
				+ " from tbl_wellinformation t  "
				+ " left outer join tbl_rpc_productiondata_latest t2 on t2.wellid=t.id "
				+ " left outer join tbl_rpc_diagram_latest t3 on t3.wellid=t.id ";
		try {
			conn_outer=OracleJdbcUtis.getConnection();
			if(conn_outer!=null){
				pstmt_outer = conn_outer.prepareStatement(sql);
				rs_outer=pstmt_outer.executeQuery();
				while(rs_outer.next()){
					int wellId=rs_outer.getInt(1);
					String pumpsettingdepth=rs_outer.getString(2);
					if(StringManagerUtils.isNotNull(pumpsettingdepth)&&StringManagerUtils.stringToFloat(pumpsettingdepth)>0){//如果录入了生产数据
						String url=Config.getInstance().configFile.getServer().getAccessPath()+"/graphicalUploadController/getOuterSurfaceCardData?wellId="+wellId;
						StringManagerUtils.sendPostMethod(url, "","utf-8");
//						Thread.sleep(1000*5);
					}
				}
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			OracleJdbcUtis.closeDBConnection(conn_outer, pstmt_outer, rs_outer);
		}finally{
			OracleJdbcUtis.closeDBConnection(conn_outer, pstmt_outer, rs_outer);
		}
		
	}
	
//	public static Connection getConnection(){
//        try{
//        	String driver=Config.getInstance().configFile.getSpring().getDatasource().getDriver();
//            String url = Config.getInstance().configFile.getSpring().getDatasource().getDriverUrl(); 
//            String username = Config.getInstance().configFile.getSpring().getDatasource().getUser();
//            String password = Config.getInstance().configFile.getSpring().getDatasource().getPassword();  
//            Class.forName(driver).newInstance();
//            Connection conn = DriverManager.getConnection(url, username, password);
//            return conn;
//        }
//        catch (Exception e){
//            return null;
//        }
//    }
//	
//	public static Connection getOuterConnection(){  
//        try{
//        	String driver=Config.getInstance().configFile.getDockDataSource().get(0).getDriver();
//            String url = Config.getInstance().configFile.getDockDataSource().get(0).getDriverUrl(); 
//            String username = Config.getInstance().configFile.getDockDataSource().get(0).getUser();
//            String password = Config.getInstance().configFile.getDockDataSource().get(0).getPassword();  
//            Class.forName(driver).newInstance();  
//            Connection conn = DriverManager.getConnection(url, username, password);  
//            return conn;  
//        }  
//        catch (Exception e){ 
//            return null;  
//        }  
//    }
//	
//	public static void closeDBConnection(){  
//        if(conn != null){  
//            try{           
//                pstmt.close();  
//                rs.close();
//                conn.close();  
//            }catch(SQLException e){
//                e.printStackTrace();  
//            }finally{  
//                try{  
//                    pstmt.close();  
//                    rs.close();
//                }catch(SQLException e){  
//                    e.printStackTrace();  
//                }  
//                conn = null;  
//            }  
//        }  
//    }
	
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
	
	//获取json文件路径
	public  String getWebJsonFilePath(String index4Str) {
        URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
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
