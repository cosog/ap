package com.cosog.task;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cosog.utils.Config;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.StringManagerUtils;

@Component("calculateDataManagerTast")  
public class CalculateDataManagerTask {
	@Scheduled(cron = "0/1 * * * * ?")
	public void checkAndSendCalculateRequset() throws SQLException, UnsupportedEncodingException, ParseException{
		//判断AC程序是否启动
		if(ResourceMonitoringTask.getAcRunStatus()==1){
			String sql="select count(1) from tbl_rpcacqdata_hist t "
					+ " where resultstatus =2 "
					+ " and t.productiondata is not null "
					+ " and t.fesdiagramacqtime is not null ";
			StringManagerUtils stringManagerUtils=new StringManagerUtils();
			String url=stringManagerUtils.getProjectUrl()+"/calculateDataController/getBatchCalculateTime";
			String result="无未计算数据";
			int count=getCount(sql);
			if(count>0){
				System.out.println("发现未计算数据");
				result=StringManagerUtils.sendPostMethod(url, "","utf-8",0,0);
			}
		}
	}
	
	@Scheduled(cron = "0/1 * * * * ?")
	public void checkAndSendPCPCalculateRequset() throws SQLException, UnsupportedEncodingException, ParseException{
		//判断AC程序是否启动
		if(ResourceMonitoringTask.getAcRunStatus()==1){
			String sql="select count(1) from tbl_pcpacqdata_hist t where resultstatus =2 and t.productiondata is not null and t.rpm is not null";
			StringManagerUtils stringManagerUtils=new StringManagerUtils();
			String url=stringManagerUtils.getProjectUrl()+"/calculateDataController/getPCPBatchCalculateTime";
			String result="无未计算数据";
			int count=getCount(sql);
			if(count>0){
				System.out.println("发现未计算数据");
				result=StringManagerUtils.sendPostMethod(url, "","utf-8",0,0);
			}
		}
	}
	
	/**
	 * 抽油机井汇总计算
	 * */
	@SuppressWarnings({ "static-access", "unused" })
	@Scheduled(cron = "0 0 1/24 * * ?")
	public void RPCTotalCalculationTast() throws SQLException, UnsupportedEncodingException, ParseException{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String url=stringManagerUtils.getProjectUrl()+"/calculateDataController/FESDiagramDailyCalculation";
		String result=StringManagerUtils.sendPostMethod(url, "","utf-8",0,0);
	}
	
	/**
	 * 螺杆泵井汇总计算
	 * */
	@SuppressWarnings({ "static-access", "unused" })
	@Scheduled(cron = "0 0 1/24 * * ?")
	public void PCPTotalCalculationTast() throws SQLException, UnsupportedEncodingException, ParseException{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String url=stringManagerUtils.getProjectUrl()+"/calculateDataController/RPMDailyCalculation";
		String result=StringManagerUtils.sendPostMethod(url, "","utf-8",0,0);
	}
	
	public static  int getCount(String sql) throws SQLException{  
        int result=0;
        Connection conn=OracleJdbcUtis.getConnection();
        if(conn==null){
        	return -1;
        }
        PreparedStatement pstmt = conn.prepareStatement(sql); 
        ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			result=rs.getInt(1);
		}
		OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
        return result;
    }
}
