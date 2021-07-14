package com.cosog.task;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cosog.model.calculate.CommResponseData;
import com.cosog.thread.calculate.GetExternalDataThread;
import com.cosog.utils.Config;
import com.cosog.utils.Config2;
import com.cosog.utils.DataSourceConfig;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("externalDataManagerTask")  
public class ExternalDataManagerTask {
	private static Connection conn = null;   
    private static PreparedStatement pstmt = null;  
    private static ResultSet rs = null;  
    
    private static Connection conn_outer = null;   
    private static PreparedStatement pstmt_outer = null;  
    private static ResultSet rs_outer = null; 

    private ExecutorService pool = Executors.newCachedThreadPool();
    
    @Scheduled(cron = "0 0/1 * * * ?")
	public void GetExternalFESDiagramData() throws SQLException, ParseException,InterruptedException, IOException{
		String sql="select t1.wellname,to_char(v.acqtime,'yyyy-mm-dd hh24:mi:ss') "
				+ " from tbl_wellinformation t1 "
				+ " left outer join tbl_rpc_productiondata_latest t2 on t2.wellid=t1.id "
				+ " left outer join (select t3.wellid,max(t3.acqtime) as acqtime from tbl_rpc_diagram_hist t3 group by t3.wellid) v on v.wellid=t1.id "
				+ " where t1.unitcode is null and t1.protocolcode is null "
				+ " and t2.pumpsettingdepth>0 ";
		try {
			conn=OracleJdbcUtis.getConnection();
			conn_outer= OracleJdbcUtis.getOuterConnection();
			if(conn!=null&&conn_outer!=null){
				pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				if(pool==null||pool.isShutdown()){
					pool = Executors.newCachedThreadPool();
				}
				while(rs.next()){
					pool.submit(new GetExternalDataThread(rs.getString(1),rs.getString(2),conn,conn_outer));
				}
				pool.shutdown();
				while(true){
					if(pool.isTerminated()){
						System.out.println("所有的子线程都结束了！");
						break;
					}
					Thread.sleep(1000);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
			OracleJdbcUtis.closeDBConnection(conn_outer, pstmt_outer, rs_outer);
		}
	}
}
