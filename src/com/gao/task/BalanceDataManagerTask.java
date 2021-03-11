package com.gao.task;

import java.io.UnsupportedEncodingException;
import java.net.URL;
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
import com.gao.utils.Config2;
import com.gao.utils.StringManagerUtils;

@Component("balanceDataManagerTast")  
public class BalanceDataManagerTask {
	private static Connection conn = null;   
    private static PreparedStatement pstmt = null;  
    private static ResultSet rs = null;  
	
	
//	@Scheduled(cron = "0/1 * * * * ?")
	public void checkAndSendBalanceCalculateRequset() throws SQLException, UnsupportedEncodingException, ParseException{
		String sql="select count(1) from t_201_balanceanalysis t where jsbz=0";
		String url=Config.getInstance().configFile.getServer().getAccessPath()+"/balanceDataAInterfaceController/getBalanceTorqueCalulate";
		String result="无未计算数据";
		int count=getCount(sql);
		closeDBConnection();
		if(count>0){
			System.out.println("发现未计算数据");
			result=StringManagerUtils.sendPostMethod(url, "","utf-8");
		}
//		System.out.println(result);
	}
	
	/**
	 * 汇总计算
	 * */
//	@Scheduled(cron = "0/1 * * * * ?")
//	@Scheduled(cron = "0 0 1/24 * * ?")
	public void totalCalculationTast() throws SQLException, UnsupportedEncodingException, ParseException{
		String url=Config.getInstance().configFile.getServer().getAccessPath()+"/balanceDataAInterfaceController/balanceTotalCalculation";
		@SuppressWarnings("unused")
		String result=StringManagerUtils.sendPostMethod(url, "","utf-8");
	}
	
	/**
	 * 周期计算
	 * */
//	@Scheduled(cron = "0/1 * * * * ?")
//	@Scheduled(cron = "0 0 1/24 * * ?")
	public void cycleCalculationTast() throws SQLException, UnsupportedEncodingException, ParseException{
		String url=Config.getInstance().configFile.getServer().getAccessPath()+"/balanceDataAInterfaceController/balanceCycleCalculation";
		@SuppressWarnings("unused")
		String result=StringManagerUtils.sendPostMethod(url, "","utf-8");
	}
	
	
	public static Connection getConnection(){  
        try{  
            
            String driver=Config.getInstance().configFile.getSpring().getDatasource().getDriver();
            String url = Config.getInstance().configFile.getSpring().getDatasource().getDriverUrl(); 
            String username = Config.getInstance().configFile.getSpring().getDatasource().getUser();
            String password = Config.getInstance().configFile.getSpring().getDatasource().getPassword();  
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
            
            String driver=Config.getInstance().configFile.getDockDataSource().get(0).getDriver();
            String url = Config.getInstance().configFile.getDockDataSource().get(0).getDriverUrl(); 
            String username = Config.getInstance().configFile.getDockDataSource().get(0).getUser();
            String password = Config.getInstance().configFile.getDockDataSource().get(0).getPassword();  
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
	
	
}
