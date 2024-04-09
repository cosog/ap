package com.cosog.task;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cosog.model.calculate.CommResponseData;
import com.cosog.utils.Config;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.StringManagerUtils;

@Component("calculateDataManagerTast")  
public class CalculateDataManagerTask {
	public static ScheduledExecutorService AcquisitionDataTotalCalculationExecutor=null;
	public static ScheduledExecutorService RPCTotalCalculationExecutor=null;
	public static ScheduledExecutorService PCPTotalCalculationExecutor=null;
	public static ScheduledExecutorService timingInitDailyReportDataExecutor=null;
	public static ScheduledExecutorService AcquisitionTimingCalculateExecutor=null;
	public static ScheduledExecutorService RPCTimingCalculateExecutor=null;
	public static ScheduledExecutorService PCPTimingCalculateExecutor=null;
	
//	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void timer(){
		timingInitDailyReportData();
		
		
		RPCTotalCalculation();
		PCPTotalCalculation();
		
		AcquisitionTimingCalculate();
		RPCTimingCalculate();
		PCPTimingCalculate();
	}
	
//	@Scheduled(cron = "0/1 * * * * ?")
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
	
//	@Scheduled(cron = "0/1 * * * * ?")
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
	
	public static void AcquisitionDataTotalCalculationTast() throws SQLException, UnsupportedEncodingException, ParseException{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String url=stringManagerUtils.getProjectUrl()+"/calculateDataController/AcquisitionDataDailyCalculation";
		String result=StringManagerUtils.sendPostMethod(url, "","utf-8",0,0);
	}
	
	public static void AcquisitionDataTotalCalculation(){
		AcquisitionDataTotalCalculationExecutor = Executors.newScheduledThreadPool(1);
		long interval=24 * 60 * 60 * 1000;
		long initDelay = StringManagerUtils.getTimeMillis(Config.getInstance().configFile.getAp().getReport().getOffsetHour()+":00:00")+ Config.getInstance().configFile.getAp().getReport().getDelay() * 60 * 1000 - System.currentTimeMillis();
		while(initDelay<0){
        	initDelay=interval + initDelay;
        }
		AcquisitionDataTotalCalculationExecutor.scheduleAtFixedRate(new Thread(new Runnable() {
            @Override
            public void run() {
            	try {
            		AcquisitionDataTotalCalculationTast();
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }), initDelay, interval, TimeUnit.MILLISECONDS);
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
	
	public static void AcquisitionDataTimingTotalCalculation(String timeStr){
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		long time=StringManagerUtils.stringToTimeStamp(timeStr, "yyyy-MM-dd HH:mm:ss");
		String url=stringManagerUtils.getProjectUrl()+"/calculateDataController/AcquisitionDataTimingTotalCalculation?time="+time;
		String result=StringManagerUtils.sendPostMethod(url, "","utf-8",0,0);
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
		String url=stringManagerUtils.getProjectUrl()+"/calculateDataController/initDailyReportData?calculateType=1";
		String result=StringManagerUtils.sendPostMethod(url, "","utf-8",0,0);
		url=stringManagerUtils.getProjectUrl()+"/calculateDataController/initDailyReportData?calculateType=2";
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
	
	public static void AcquisitionTimingCalculate() {
		AcquisitionTimingCalculateExecutor = Executors.newScheduledThreadPool(1);
        long interval = Config.getInstance().configFile.getAp().getReport().getInterval() * 60 * 60 * 1000;
//        interval=5 * 60 * 1000;
        long initDelay = StringManagerUtils.getTimeMillis(Config.getInstance().configFile.getAp().getReport().getOffsetHour()+":00:00") - System.currentTimeMillis();
//        initDelay=StringManagerUtils.getTimeMillis("08:00:00") - System.currentTimeMillis();
        while(initDelay<0){
        	initDelay=interval + initDelay;
        }
        AcquisitionTimingCalculateExecutor.scheduleAtFixedRate(new Thread(new Runnable() {
            @Override
            public void run() {
                String timeStr=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
            	try {
            		AcquisitionDataTimingTotalCalculation(timeStr);
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }), initDelay, interval, TimeUnit.MILLISECONDS);
    }
	
	public static void RPCTimingCalculate() {
		RPCTimingCalculateExecutor = Executors.newScheduledThreadPool(1);
        long interval = Config.getInstance().configFile.getAp().getReport().getInterval() * 60 * 60 * 1000;
//        interval=5 * 60 * 1000;
        long initDelay = StringManagerUtils.getTimeMillis(Config.getInstance().configFile.getAp().getReport().getOffsetHour()+":00:00") - System.currentTimeMillis();
//        initDelay=StringManagerUtils.getTimeMillis("08:00:00") - System.currentTimeMillis();
        while(initDelay<0){
        	initDelay=interval + initDelay;
        }
        RPCTimingCalculateExecutor.scheduleAtFixedRate(new Thread(new Runnable() {
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
		PCPTimingCalculateExecutor = Executors.newScheduledThreadPool(1);
        long interval = Config.getInstance().configFile.getAp().getReport().getInterval() * 60 * 60 * 1000;
//        interval=5 * 60 * 1000;
        long initDelay = StringManagerUtils.getTimeMillis(Config.getInstance().configFile.getAp().getReport().getOffsetHour()+":00:00") - System.currentTimeMillis();
//        initDelay=StringManagerUtils.getTimeMillis("10:00:00") - System.currentTimeMillis();
        while(initDelay<0){
        	initDelay=interval + initDelay;
        }
        PCPTimingCalculateExecutor.scheduleAtFixedRate(new Thread(new Runnable() {
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
	
	public static void acquisitionDataTotalCalculate(String deviceId,String date){
		List<String> tableColumnList=MemoryDataManagerTask.getAcqTableColumn("tbl_acqdata_hist");
		List<String> totalTableColumnList=MemoryDataManagerTask.getAcqTableColumn("tbl_dailycalculationdata");
		CommResponseData.Range dateTimeRange= StringManagerUtils.getTimeRange(date,Config.getInstance().configFile.getAp().getReport().getOffsetHour());
		
		List<String> columnList=new ArrayList<>();
		for(int i=0;i<tableColumnList.size();i++){
			if(StringManagerUtils.existOrNot(totalTableColumnList, tableColumnList.get(i), false)){
				columnList.add(tableColumnList.get(i));
			}
		}
		if(columnList.size()>0){
			String sql="select deviceid";
			String newestDataSql="select deviceid";
			String oldestDataSql="select deviceid";
			for(int i=0;i<columnList.size();i++){
				String column=columnList.get(i);
				sql+=",max(t."+column+")||';'||min(t."+column+")||';'||round(avg(t."+column+"),2) as "+column+"";
				newestDataSql+=",t."+column;
				oldestDataSql+=",t."+column;
			}
			
			sql+=" from tbl_acqdata_hist t "
				+ " where t.acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+dateTimeRange.getEndTime()+"','yyyy-mm-dd hh24:mi:ss') ";
			newestDataSql+=" from tbl_acqdata_hist t"
					+ " where t.acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+dateTimeRange.getEndTime()+"','yyyy-mm-dd hh24:mi:ss') ";
			oldestDataSql+=" from tbl_acqdata_hist t"
					+ " where t.acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+dateTimeRange.getEndTime()+"','yyyy-mm-dd hh24:mi:ss') ";
			if(StringManagerUtils.isNotNull(deviceId)){
				sql+=" and t.deviceid="+deviceId;
				newestDataSql+=" and t.deviceid="+deviceId;
				oldestDataSql+=" and t.deviceid="+deviceId;
			}else{
				newestDataSql+=" and t.acqtime=(select min(t2.acqtime) from tbl_acqdata_hist t2 "
						+ " where t2.deviceid=t.deviceid"
						+ " and t2.acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+dateTimeRange.getEndTime()+"','yyyy-mm-dd hh24:mi:ss') "
						+ " )";
				oldestDataSql+=" and t.acqtime=(select max(t2.acqtime) from tbl_acqdata_hist t2 "
						+ " where t2.deviceid=t.deviceid"
						+ " and t2.acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+dateTimeRange.getEndTime()+"','yyyy-mm-dd hh24:mi:ss') "
						+ " )";
			}
			
			
			if(StringManagerUtils.isNotNull(deviceId)){
				newestDataSql+=" order by t.acqtime";
				newestDataSql="select * from("+newestDataSql+") where rownum=1";
				oldestDataSql+=" order by t.acqtime";
				oldestDataSql="select * from("+oldestDataSql+") where rownum=1";
			}
			
			
			sql+="group by t.deviceid";
			
			
			List<Object[]> totalList=OracleJdbcUtis.query(sql);
			List<Object[]> newestValueList=OracleJdbcUtis.query(newestDataSql);
			List<Object[]> oldestValueList=OracleJdbcUtis.query(oldestDataSql);
			
			for(int i=0;i<totalList.size();i++){
				Object[] obj=totalList.get(i);
				String deviceIdStr=obj[0]+"";
				Object[] newestValueObj=null;
				Object[]oldestValueObj=null;
				for(int j=0;j<newestValueList.size();j++){
					if(deviceIdStr.equalsIgnoreCase(newestValueList.get(j)[0]+"")){
						newestValueObj=newestValueList.get(j);
						break;
					}
				}
				for(int j=0;j<oldestValueList.size();j++){
					if(deviceIdStr.equalsIgnoreCase(oldestValueList.get(j)[0]+"")){
						oldestValueObj=oldestValueList.get(j);
						break;
					}
				}
				String updatesql="update tbl_dailycalculationdata set t.deviceid="+deviceIdStr+"";
				for(int j=1;j<obj.length;j++){
					String oldestValue=oldestValueObj==null?"":(oldestValueObj[j]+"");
					String newestValue=oldestValueObj==null?"":(newestValueObj[j]+"");
					String tatalValue=obj[j]+";"+oldestValue+";"+newestValue;
					String colnum=columnList.get(j-1);
					updatesql+=",t."+colnum+"='"+tatalValue+"'";
				}
				updatesql+=" where t.deviceid="+deviceIdStr+" and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
				OracleJdbcUtis.executeSqlUpdate(updatesql);
			}
		}
	}
	
	public static void acquisitionDataTimingTotalCalculate(String deviceId,String timeStr){
		List<String> tableColumnList=MemoryDataManagerTask.getAcqTableColumn("tbl_acqdata_hist");
		List<String> totalTableColumnList=MemoryDataManagerTask.getAcqTableColumn("tbl_dailycalculationdata");
		int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
		int interval = Config.getInstance().configFile.getAp().getReport().getInterval();
		String date=timeStr.split(" ")[0];
		if(!StringManagerUtils.timeMatchDate(timeStr, date, offsetHour)){
			date=StringManagerUtils.addDay(StringManagerUtils.stringToDate(date),-1);
		}
		CommResponseData.Range dateTimeRange= StringManagerUtils.getTimeRange(date,offsetHour);
		
		List<String> columnList=new ArrayList<>();
		for(int i=0;i<tableColumnList.size();i++){
			if(StringManagerUtils.existOrNot(totalTableColumnList, tableColumnList.get(i), false)){
				columnList.add(tableColumnList.get(i));
			}
		}
		if(columnList.size()>0){
			String sql="select deviceid";
			String newestDataSql="select deviceid";
			String oldestDataSql="select deviceid";
			for(int i=0;i<columnList.size();i++){
				String column=columnList.get(i);
				sql+=",max(t."+column+")||';'||min(t."+column+")||';'||round(avg(t."+column+"),2) as "+column+"";
				newestDataSql+=",t."+column;
				oldestDataSql+=",t."+column;
			}
			
			sql+=" from tbl_acqdata_hist t "
				+ " where t.acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') ";
			newestDataSql+=" from tbl_acqdata_hist t"
					+ " where t.acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') ";
			oldestDataSql+=" from tbl_acqdata_hist t"
					+ " where t.acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') ";
			if(StringManagerUtils.isNotNull(deviceId)){
				sql+=" and t.deviceid="+deviceId;
				newestDataSql+=" and t.deviceid="+deviceId;
				oldestDataSql+=" and t.deviceid="+deviceId;
			}else{
				newestDataSql+=" and t.acqtime=(select min(t2.acqtime) from tbl_acqdata_hist t2 "
						+ " where t2.deviceid=t.deviceid"
						+ " and t2.acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') "
						+ " )";
				oldestDataSql+=" and t.acqtime=(select max(t2.acqtime) from tbl_acqdata_hist t2 "
						+ " where t2.deviceid=t.deviceid"
						+ " and t2.acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') "
						+ " )";
			}
			
			
			if(StringManagerUtils.isNotNull(deviceId)){
				newestDataSql+=" order by t.acqtime";
				newestDataSql="select * from("+newestDataSql+") where rownum=1";
				oldestDataSql+=" order by t.acqtime";
				oldestDataSql="select * from("+oldestDataSql+") where rownum=1";
			}
			
			
			sql+="group by t.deviceid";
			
			
			List<Object[]> totalList=OracleJdbcUtis.query(sql);
			List<Object[]> newestValueList=OracleJdbcUtis.query(newestDataSql);
			List<Object[]> oldestValueList=OracleJdbcUtis.query(oldestDataSql);
			
			for(int i=0;i<totalList.size();i++){
				Object[] obj=totalList.get(i);
				String deviceIdStr=obj[0]+"";
				Object[] newestValueObj=null;
				Object[]oldestValueObj=null;
				for(int j=0;j<newestValueList.size();j++){
					if(deviceIdStr.equalsIgnoreCase(newestValueList.get(j)[0]+"")){
						newestValueObj=newestValueList.get(j);
						break;
					}
				}
				for(int j=0;j<oldestValueList.size();j++){
					if(deviceIdStr.equalsIgnoreCase(oldestValueList.get(j)[0]+"")){
						oldestValueObj=oldestValueList.get(j);
						break;
					}
				}
				String updatesql="update tbl_dailycalculationdata set t.deviceid="+deviceIdStr+"";
				for(int j=1;j<obj.length;j++){
					String oldestValue=oldestValueObj==null?"":(oldestValueObj[j]+"");
					String newestValue=oldestValueObj==null?"":(newestValueObj[j]+"");
					String tatalValue=obj[j]+";"+oldestValue+";"+newestValue;
					String colnum=columnList.get(j-1);
					updatesql+=",t."+colnum+"='"+tatalValue+"'";
				}
				updatesql+=" where t.deviceid="+deviceIdStr+" and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
				OracleJdbcUtis.executeSqlUpdate(updatesql);
			}
		}
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
		if(RPCTimingCalculateExecutor!=null && !RPCTimingCalculateExecutor.isShutdown()){
			RPCTimingCalculateExecutor.shutdownNow();
		}
		if(PCPTimingCalculateExecutor!=null && !PCPTimingCalculateExecutor.isShutdown()){
			PCPTimingCalculateExecutor.shutdownNow();
		}
		if(AcquisitionDataTotalCalculationExecutor!=null && !AcquisitionDataTotalCalculationExecutor.isShutdown()){
			AcquisitionDataTotalCalculationExecutor.shutdownNow();
		}
		if(AcquisitionTimingCalculateExecutor!=null && !AcquisitionTimingCalculateExecutor.isShutdown()){
			AcquisitionTimingCalculateExecutor.shutdownNow();
		}
		
		StringManagerUtils.printLog("scheduledDestory!");
	}
}
