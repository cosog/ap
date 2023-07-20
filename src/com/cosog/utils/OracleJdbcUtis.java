package com.cosog.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.orm.hibernate5.SessionFactoryUtils;

import com.cosog.model.DataSourceConfig;
import com.cosog.task.MemoryDataManagerTask;

import oracle.sql.CLOB;

public class OracleJdbcUtis {

	private static BasicDataSource outerDataSource=null;
	
	private static void initOuterDataSource(){
		
		DataSourceConfig dataSourceConfig=MemoryDataManagerTask.getDataSourceConfig();
		
		if(dataSourceConfig!=null && dataSourceConfig.isEnable()){
			
			outerDataSource = new BasicDataSource();
			
			outerDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");

			outerDataSource.setUrl("jdbc:oracle:thin:@"+dataSourceConfig.getIP()+":"+dataSourceConfig.getPort()+"/"+dataSourceConfig.getInstanceName()+"");

			outerDataSource.setUsername(dataSourceConfig.getUser());

			outerDataSource.setPassword(dataSourceConfig.getPassword());

			outerDataSource.setInitialSize(5); // 初始化连接数

			outerDataSource.setMaxIdle(10); // 最大空闲连接数

			outerDataSource.setMinIdle(5); // 最小空闲连接数

			outerDataSource.setMaxIdle(100); // 最大连接数
		}
	}
	
	public static Connection getOuterConnection() throws SQLException{
		Connection conn=null;
		if(outerDataSource==null){
			initOuterDataSource();
		}
		if(outerDataSource!=null){
			conn=outerDataSource.getConnection();
		}
		return conn;
	}
	
	
	public static Connection getConnection(){  
        try{  
            
        	String driver=Config.getInstance().configFile.getAp().getDatasource().getDriver();
            String url = Config.getInstance().configFile.getAp().getDatasource().getDriverUrl(); 
            String username = Config.getInstance().configFile.getAp().getDatasource().getUser();
            String password = Config.getInstance().configFile.getAp().getDatasource().getPassword();  
            Class.forName(driver).newInstance();  
            Connection conn = DriverManager.getConnection(url, username, password);  
            return conn;  
        }  
        catch (Exception e){  
            StringManagerUtils.printLog(e.getMessage());  
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
                StringManagerUtils.printLog("closeDBConnectionError!");  
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
                StringManagerUtils.printLog("closeDBConnectionError!");  
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
	
	public static int executeSqlUpdateClob(Connection conn,PreparedStatement ps,String sql,List<String> values) throws SQLException {
		int n = 0;
		for(int i=0;i<values.size();i++){
			CLOB clob   = oracle.sql.CLOB.createTemporary(conn, false,oracle.sql.CLOB.DURATION_SESSION);  
			clob.putString(1,  values.get(i)); 
			ps.setClob(i+1, clob);  
		}
		n=ps.executeUpdate();  
//		ps.close();  
//		conn.commit(); 
		return n;
	}
}
