package com.cosog.task;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cosog.utils.Config;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.StringManagerUtils;

@Component("calculateDataManagerTast")  
public class CalculateDataManagerTask {
	public static ScheduledExecutorService RPCTotalCalculationExecutor=null;
	public static ScheduledExecutorService PCPTotalCalculationExecutor=null;
	public static ScheduledExecutorService timingInitDailyReportDataExecutor=null;
	public static ScheduledExecutorService RPCTimingCalculateexecutor=null;
	public static ScheduledExecutorService PCPTimingCalculateexecutor=null;
	
	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void timer(){
		timingInitDailyReportData();
		RPCTotalCalculation();
		PCPTotalCalculation();
		RPCTimingCalculate();
		PCPTimingCalculate();
	}
	
	@Scheduled(cron = "0/1 * * * * ?")
	public void checkAndSendCalculateRequset() throws SQLException, UnsupportedEncodingException, ParseException{
		//判断AC程序是否启动
		if(ResourceMonitoringTask.getAcRunStatus()==1){
			String sql="select count(1) from tbl_rpcacqdata_hist t "
					+ " where 1=1"
					+ " and t.productiondata is not null "
					+ " and t.fesdiagramacqtime is not null "
					+ " and resultstatus =2 ";
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
			String sql="select count(1) from tbl_pcpacqdata_hist t where  t.productiondata is not null and t.rpm is not null and resultstatus =2";
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
	public static void RPCTotalCalculationTast() throws SQLException, UnsupportedEncodingException, ParseException{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String url=stringManagerUtils.getProjectUrl()+"/calculateDataController/FESDiagramDailyCalculation";
		String result=StringManagerUtils.sendPostMethod(url, "","utf-8",0,0);
	}
	//抽油机井跨天汇总
	public static void RPCTotalCalculation(){
		RPCTotalCalculationExecutor = Executors.newScheduledThreadPool(1);
		long interval=24 * 60 * 60 * 1000;
		long initDelay = StringManagerUtils.getTimeMillis(Config.getInstance().configFile.getAp().getReport().getOffsetHour()+":00:00")+ Config.getInstance().configFile.getAp().getReport().getDelay() * 60 * 1000 - System.currentTimeMillis();
		while(initDelay<0){
        	initDelay=interval + initDelay;
        }
		RPCTotalCalculationExecutor.scheduleAtFixedRate(new Thread(new Runnable() {
            @Override
            public void run() {
            	try {
            		RPCTotalCalculationTast();
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }), initDelay, interval, TimeUnit.MILLISECONDS);
	}
	
	public static void RPCTimingTotalCalculation(String timeStr){
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		long time=StringManagerUtils.stringToTimeStamp(timeStr, "yyyy-MM-dd HH:mm:ss");
		String url=stringManagerUtils.getProjectUrl()+"/calculateDataController/RPCTimingTotalCalculation?time="+time;
		String result=StringManagerUtils.sendPostMethod(url, "","utf-8",0,0);
	}
	
	/**
	 * 螺杆泵井汇总计算
	 * */
	public static void PCPTotalCalculationTast() throws SQLException, UnsupportedEncodingException, ParseException{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String url=stringManagerUtils.getProjectUrl()+"/calculateDataController/RPMDailyCalculation";
		String result=StringManagerUtils.sendPostMethod(url, "","utf-8",0,0);
	}
	
	//螺杆泵井跨天汇总
	public static void PCPTotalCalculation(){
		PCPTotalCalculationExecutor = Executors.newScheduledThreadPool(1);
		long interval=24 * 60 * 60 * 1000;
		long initDelay = StringManagerUtils.getTimeMillis(Config.getInstance().configFile.getAp().getReport().getOffsetHour()+":00:00")+ Config.getInstance().configFile.getAp().getReport().getDelay() * 60 * 1000 - System.currentTimeMillis();
		while(initDelay<0){
        	initDelay=interval + initDelay;
        }
		PCPTotalCalculationExecutor.scheduleAtFixedRate(new Thread(new Runnable() {
            @Override
            public void run() {
            	try {
            		PCPTotalCalculationTast();
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }), initDelay, interval, TimeUnit.MILLISECONDS);
	}
	
	@SuppressWarnings("static-access")
	public static void PCPTimingTotalCalculation(String timeStr){
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		long time=StringManagerUtils.stringToTimeStamp(timeStr, "yyyy-MM-dd HH:mm:ss");
		String url=stringManagerUtils.getProjectUrl()+"/calculateDataController/PCPTimingTotalCalculation?time="+time;
		String result=StringManagerUtils.sendPostMethod(url, "","utf-8",0,0);
	}
	
	/**
	 * 报表数据初始化
	 * */
	@SuppressWarnings("static-access")
	public static void initDailyReportDataTast() throws SQLException, UnsupportedEncodingException, ParseException{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String url=stringManagerUtils.getProjectUrl()+"/calculateDataController/initDailyReportData?deviceType=0";
		String result=StringManagerUtils.sendPostMethod(url, "","utf-8",0,0);
		url=stringManagerUtils.getProjectUrl()+"/calculateDataController/initDailyReportData?deviceType=1";
		result=StringManagerUtils.sendPostMethod(url, "","utf-8",0,0);
	}
	
	//跨天初始化报表
	public static void timingInitDailyReportData(){
		timingInitDailyReportDataExecutor = Executors.newScheduledThreadPool(1);
		long interval=24 * 60 * 60 * 1000;
		long initDelay = StringManagerUtils.getTimeMillis(Config.getInstance().configFile.getAp().getReport().getOffsetHour()+":00:00")+ 1 * 60 * 1000 - System.currentTimeMillis();
		while(initDelay<0){
        	initDelay=interval + initDelay;
        }
		timingInitDailyReportDataExecutor.scheduleAtFixedRate(new Thread(new Runnable() {
            @Override
            public void run() {
            	try {
            		initDailyReportDataTast();
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }), initDelay, interval, TimeUnit.MILLISECONDS);
	}
	
	public static void RPCTimingCalculate() {
		RPCTimingCalculateexecutor = Executors.newScheduledThreadPool(1);
        long interval = Config.getInstance().configFile.getAp().getReport().getInterval() * 60 * 60 * 1000;
//        interval=1 * 60 * 1000;
        long initDelay = StringManagerUtils.getTimeMillis(Config.getInstance().configFile.getAp().getReport().getOffsetHour()+":00:00") - System.currentTimeMillis();
//        initDelay=StringManagerUtils.getTimeMillis("08:00:00") - System.currentTimeMillis();
        while(initDelay<0){
        	initDelay=interval + initDelay;
        }
        RPCTimingCalculateexecutor.scheduleAtFixedRate(new Thread(new Runnable() {
            @Override
            public void run() {
                String timeStr=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
            	try {
					RPCTimingTotalCalculation(timeStr);
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }), initDelay, interval, TimeUnit.MILLISECONDS);
    }
	
	public static void PCPTimingCalculate() {
		PCPTimingCalculateexecutor = Executors.newScheduledThreadPool(1);
        long interval = Config.getInstance().configFile.getAp().getReport().getInterval() * 60 * 60 * 1000;
//        interval=1 * 60 * 1000;
        long initDelay = StringManagerUtils.getTimeMillis(Config.getInstance().configFile.getAp().getReport().getOffsetHour()+":00:00") - System.currentTimeMillis();
//        initDelay=StringManagerUtils.getTimeMillis("10:00:00") - System.currentTimeMillis();
        while(initDelay<0){
        	initDelay=interval + initDelay;
        }
        PCPTimingCalculateexecutor.scheduleAtFixedRate(new Thread(new Runnable() {
            @Override
            public void run() {
                String timeStr=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
                try {
					PCPTimingTotalCalculation(timeStr);
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }), initDelay, interval, TimeUnit.MILLISECONDS);
    }
	
	public static  int getCount(String sql){  
        int result=0;
        Connection conn=null;
		PreparedStatement pstmt = null; 
        ResultSet rs=null;
        try{
        	conn=OracleJdbcUtis.getConnection();
            if(conn==null){
            	return -1;
            }
            pstmt = conn.prepareStatement(sql); 
            rs=pstmt.executeQuery();
    		while(rs.next()){
    			result=rs.getInt(1);
    		}
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
        }
        return result;
    }
	
	public static void scheduledDestory(){
		if(RPCTotalCalculationExecutor!=null && !RPCTotalCalculationExecutor.isShutdown()){
			RPCTotalCalculationExecutor.shutdownNow();
		}
		if(PCPTotalCalculationExecutor!=null && !PCPTotalCalculationExecutor.isShutdown()){
			PCPTotalCalculationExecutor.shutdownNow();
		}
		if(timingInitDailyReportDataExecutor!=null && !timingInitDailyReportDataExecutor.isShutdown()){
			timingInitDailyReportDataExecutor.shutdownNow();
		}
		if(RPCTimingCalculateexecutor!=null && !RPCTimingCalculateexecutor.isShutdown()){
			RPCTimingCalculateexecutor.shutdownNow();
		}
		if(PCPTimingCalculateexecutor!=null && !PCPTimingCalculateexecutor.isShutdown()){
			PCPTimingCalculateexecutor.shutdownNow();
		}
		
		StringManagerUtils.printLog("scheduledDestory!");
	}
}
