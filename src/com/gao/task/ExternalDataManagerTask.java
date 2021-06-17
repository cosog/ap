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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gao.model.calculate.CommResponseData;
import com.gao.thread.calculate.GetExternalDataThread;
import com.gao.utils.Config;
import com.gao.utils.Config2;
import com.gao.utils.DataSourceConfig;
import com.gao.utils.OracleJdbcUtis;
import com.gao.utils.StringManagerUtils;
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
    
	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void GetExternalFESDiagramData() throws SQLException, ParseException,InterruptedException, IOException{
		String sql="select t1.wellname,v.acqtime "
				+ " from tbl_wellinformation t1 "
				+ " left outer join tbl_rpc_productiondata_latest t2 on t2.wellid=t1.id "
				+ " left outer join (select t3.wellid,max(t3.acqtime) as acqtime from tbl_rpc_diagram_hist t3 group by t3.wellid) v on v.wellid=t1.id "
				+ " where t1.unitcode is null and t1.protocolcode is null and t2.pumpsettingdepth>0 ";
		try {
			conn=OracleJdbcUtis.getConnection();
			if(conn!=null){
				pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					pool.submit(new GetExternalDataThread(rs.getString(1),rs.getString(2)));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
}
