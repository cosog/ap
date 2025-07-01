package com.cosog.task;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cosog.thread.calculate.DatabaseHistoryDataDeleteThread;
import com.cosog.thread.calculate.FBHistoryDataSyncThread;
import com.cosog.thread.calculate.ThreadPool;
import com.cosog.utils.Config;
import com.cosog.utils.DatabaseMaintenanceCounterUtils;
import com.cosog.utils.FeiZhouCounterUtils;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.StringManagerUtils;

@Component("databaseMaintenanceTask")
public class DatabaseMaintenanceTask {
	public static ScheduledExecutorService executor=null;
	
	@SuppressWarnings("static-access")
	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void timer(){
		int cycle=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getCycle();
		if(cycle>0){
			timingDatabaseMaintenance();
		}
	}
	
	@SuppressWarnings({ "static-access", "unused" })
	public static void timingDatabaseMaintenance(){
		int cycle=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getCycle();
		String startTime=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getStartTime();
		int retentionTime=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getRetentionTime();
		
		long interval=cycle * 24 * 60 * 60 * 1000;
		long initDelay = StringManagerUtils.getTimeMillis(startTime) - System.currentTimeMillis();
		while(initDelay<0){
        	initDelay=interval + initDelay;
        }
		
		executor = Executors.newScheduledThreadPool(1);
		
		executor.scheduleAtFixedRate(new Thread(new Runnable() {
            @Override
            public void run() {
            	try {
            		timingDeleteDatabaseHistoryData();
				}catch (Exception e) {
					e.printStackTrace();
				}
            }
        }), initDelay, interval, TimeUnit.MILLISECONDS);
	}
	
	public static void timingDeleteDatabaseHistoryData(){
		StringManagerUtils.printLog("timingDeleteDatabaseHistoryData start!");
		String sql="select t.deviceid,t2.calculatetype,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime "
				+ " from TBL_ACQDATA_LATEST t,tbl_device t2 "
				+ " where t.deviceid=t2.id and t.acqtime is not null "
//				+ " and t2.id=62"
				+ " order by t2.devicetype,t2.sortnum,t2.id";
		
		ThreadPool threadPoolexecutor = new ThreadPool("timingDeleteDatabaseHistoryData",
				10, 
				20, 
				5, 
				TimeUnit.SECONDS, 
				0);
		
		DatabaseMaintenanceCounterUtils.reset();//加法计数器清零
		
		List<Object[]> deviceList=OracleJdbcUtis.query(sql);
		DatabaseMaintenanceCounterUtils.initCountDownLatch(deviceList.size());
		
		for(Object[] obj:deviceList){
			int deviceId=StringManagerUtils.stringToInteger(obj[0]+"");
			int calculateType=StringManagerUtils.stringToInteger(obj[1]+"");
			String acqTime=obj[2]+"";
			threadPoolexecutor.execute(new DatabaseHistoryDataDeleteThread(deviceId, calculateType,acqTime));
		}
		try {
			DatabaseMaintenanceCounterUtils.await();//等待所有线程执行完毕
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringManagerUtils.printLog("timingDeleteDatabaseHistoryData finished!");
	}
	
	
	
	public static void scheduledDestory(){
		if(executor!=null && !executor.isShutdown()){
			executor.shutdownNow();
		}
		StringManagerUtils.printLog("DatabaseMaintenanceTask scheduled destory!");
	}
}
