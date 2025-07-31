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
	public static ScheduledExecutorService endExecutor=null;
	
	public static ThreadPool threadPoolexecutor=null;
	
	@SuppressWarnings("static-access")
	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void timer(){
		int cycle=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getCycle();
		if(cycle>0){
			timingDatabaseMaintenance();
			stopTimingDatabaseMaintenance();
		}
	}
	
//	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void timingShrinkSpace(){
		System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+",shrink space start！");
		long t1=System.nanoTime();
		shrinkSpace("TBL_ACQDATA_HIST");
		shrinkSpace("TBL_ACQRAWDATA");
		shrinkSpace("TBL_ALARMINFO_HIST");
		shrinkSpace("TBL_DAILYTOTALCALCULATE_HIST");
		shrinkSpace("TBL_DAILYCALCULATIONDATA");
		shrinkSpace("TBL_TIMINGCALCULATIONDATA");
		shrinkSpace("TBL_SRPACQDATA_HIST");
		shrinkSpace("TBL_SRPDAILYCALCULATIONDATA");
		shrinkSpace("TBL_SRPTIMINGCALCULATIONDATA");
		shrinkSpace("TBL_PCPACQDATA_HIST");
		shrinkSpace("TBL_PCPDAILYCALCULATIONDATA");
		shrinkSpace("TBL_PCPTIMINGCALCULATIONDATA");
		shrinkSpace("TBL_ACQDATA_VACUATE");
		shrinkSpace("TBL_SRPACQDATA_VACUATE");
		shrinkSpace("TBL_PCPACQDATA_VACUATE");
		long t2=System.nanoTime();
		System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+",shrink space end,总耗时:"+StringManagerUtils.getTimeDiff(t1, t2));
	}
	
	@SuppressWarnings({ "static-access", "unused" })
	public static void timingDatabaseMaintenance(){
		int cycle=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getCycle();
		String startTime=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getStartTime();
		
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
	
	
//	@Scheduled(cron = "0 0/5 * * * ?")
	public static void timingDeleteDatabaseHistoryData(){
		System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+",timingDeleteDatabaseHistoryData start!");
		long countBeforeDelete=getDataBaseTableCount("tbl_acqdata_hist");
		System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+",The count of tbl_acqdata_hist before delete:"+countBeforeDelete);
		String sql="select t2.id,t2.calculatetype,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime "
				+ " from tbl_device t2 "
				+ " left outer join TBL_ACQDATA_LATEST t on t.deviceid=t2.id "
				+ " where 1=1 "
				+ " order by t2.devicetype,t2.sortnum,t2.id";
		
		threadPoolexecutor = new ThreadPool("timingDeleteDatabaseHistoryData",
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
		System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+",timingDeleteDatabaseHistoryData finished!");
		long countAfterDelete=getDataBaseTableCount("tbl_acqdata_hist");
		System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+",The count of tbl_acqdata_hist after delete:"+countAfterDelete+",The count of tbl_acqdata_hist before delete:"+countBeforeDelete+",difference:"+(countBeforeDelete-countAfterDelete));
		System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+",tbl_acqdata_hist table deleted count:"+DatabaseMaintenanceCounterUtils.sum());
	}
	
	@SuppressWarnings({ "static-access", "unused" })
	public static void stopTimingDatabaseMaintenance(){
		int cycle=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getCycle();
		String endTime=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getEndTime();
		
		long interval=cycle * 24 * 60 * 60 * 1000;
		long initDelay = StringManagerUtils.getTimeMillis(endTime) - System.currentTimeMillis();
		while(initDelay<0){
        	initDelay=interval + initDelay;
        }
		
		endExecutor = Executors.newScheduledThreadPool(1);
		endExecutor.scheduleAtFixedRate(new Thread(new Runnable() {
            @Override
            public void run() {
            	try {
            		stopTimingDeleteDatabaseHistoryData();
				}catch (Exception e) {
					e.printStackTrace();
				}
            }
        }), initDelay, interval, TimeUnit.MILLISECONDS);
	}
	
//	@Scheduled(cron = "30 0/5 * * * ?")
	public static void stopTimingDeleteDatabaseHistoryData(){
		System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+",stopTimingDeleteDatabaseHistoryData!");
		if(threadPoolexecutor!=null && DatabaseMaintenanceCounterUtils.getCount()>0){
			List<Runnable> list=threadPoolexecutor.shutdown();
			for(int i=0;i<list.size();i++){
				DatabaseMaintenanceCounterUtils.countDown();
			}
//			try {
//				Thread.sleep(1000*10);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			try {
				DatabaseMaintenanceCounterUtils.await();//等待所有线程执行完毕
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			threadPoolexecutor.removeThreadPoolExecutor("timingDeleteDatabaseHistoryData");
		}
		System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+",stopTimingDeleteDatabaseHistoryData finished！");
	}
	
	public static void scheduledDestory(){
		if(executor!=null && !executor.isShutdown()){
			executor.shutdownNow();
		}
		if(endExecutor!=null && !endExecutor.isShutdown()){
			endExecutor.shutdownNow();
		}
		
		StringManagerUtils.printLog("DatabaseMaintenanceTask scheduled destory!");
	}
	
	public static long getDataBaseTableCount(String table){
		long r=0;
		String sql="select count(1) from "+table+" t";
		List<Object[]> list=OracleJdbcUtis.query(sql);
		if(list.size()>0){
			r=StringManagerUtils.stringToLong(list.get(0)[0]+"");
		}
		return r;
	}
	
	public int shrinkSpace(String tableName){
		System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+",shrink space start:"+tableName+"！");
		long t1=System.nanoTime();
		@SuppressWarnings("static-access")
		String userName=Config.getInstance().configFile.getAp().getDatasource().getUser();
		String enableRowMovementCommand="ALTER TABLE "+userName+"."+tableName+" ENABLE ROW MOVEMENT";
		String shrinkSpaceCommand="ALTER TABLE "+userName+"."+tableName+" SHRINK SPACE CASCADE";
		String disableRowMovementCommand="ALTER TABLE "+userName+"."+tableName+" DISABLE ROW MOVEMENT";
		
		
		int result=OracleJdbcUtis.executeSqlUpdate(enableRowMovementCommand);
		Thread.yield();
		result=OracleJdbcUtis.executeSqlUpdate(shrinkSpaceCommand);
		Thread.yield();
		result=OracleJdbcUtis.executeSqlUpdate(disableRowMovementCommand);
		
		long t2=System.nanoTime();
		System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+",shrink space end:"+tableName+",耗时:"+StringManagerUtils.getTimeDiff(t1, t2));
		
		return result;
	}
}
