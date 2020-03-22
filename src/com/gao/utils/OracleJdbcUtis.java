package com.gao.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleJdbcUtis {

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
            System.out.println(e.getMessage());  
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
            System.out.println(e.getMessage());  
            return null;  
        }  
    }
	
	public static void closeDBConnection(Connection conn,PreparedStatement pstmt,ResultSet rs){  
        if(conn != null){  
            try{           
            	if(pstmt!=null)
            		pstmt.close();  
            	if(rs!=null)
            		rs.close();
                conn.close();  
            }catch(SQLException e){  
                System.out.println("closeDBConnectionError!");  
                e.printStackTrace();  
            }finally{  
                try{
                	if(pstmt!=null)
                		pstmt.close();  
                	if(rs!=null)
                		rs.close();
                }catch(SQLException e){  
                    e.printStackTrace();  
                }  
                conn = null;  
            }  
        }  
    }
	
	public static void closeDBConnection(Connection conn,Statement stmt,PreparedStatement pstmt,ResultSet rs){  
        if(conn != null){  
            try{
            	if(stmt!=null){
            		stmt.close();
            	}
            	if(pstmt!=null)
            		pstmt.close();  
            	if(rs!=null)
            		rs.close();
                conn.close();  
            }catch(SQLException e){  
                System.out.println("closeDBConnectionError!");  
                e.printStackTrace();  
            }finally{  
                try{
                	if(stmt!=null){
                		stmt.close();
                	}
                	if(pstmt!=null)
                		pstmt.close();  
                	if(rs!=null)
                		rs.close();
                }catch(SQLException e){  
                    e.printStackTrace();  
                }  
                conn = null;  
            }  
        }  
    }
}
