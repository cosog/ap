package com.cosog.utils;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.LongAdder;

import org.apache.log4j.Logger;

public class DatabaseMaintenanceCounterUtils {
	public static LongAdder acqDataCount = new LongAdder();
	public static LongAdder acqRawDataCount = new LongAdder();
	public static LongAdder alarmDataCount = new LongAdder();
	public static LongAdder dailyTotalCalculateDataCount = new LongAdder();
	public static LongAdder dailyCalculateDataCount = new LongAdder();
	public static LongAdder timingCalculateDataCount = new LongAdder();
	public static LongAdder SRPAcqDataCount = new LongAdder();
	public static LongAdder SRPDailyCalculateDataCount = new LongAdder();
	public static LongAdder SRPTimingCalculateDataCount = new LongAdder();
	public static LongAdder PCPAcqDataCount = new LongAdder();
	public static LongAdder PCPDailyCalculateDataCount = new LongAdder();
	public static LongAdder PCPTimingCalculateDataCount = new LongAdder();
	public static LongAdder acqDataVacuateCount = new LongAdder();
	public static LongAdder SRPAcqDataVacuateCount = new LongAdder();
	public static LongAdder PCPAcqDataVacuateCount = new LongAdder();
	
	public static CountDownLatch countDownLatch;
	private static final Logger logger = Logger.getLogger(DatabaseMaintenanceCounterUtils.class.getName());
    public static void incr(String table) {
    	if(table.equalsIgnoreCase("TBL_ACQDATA_HIST")){
    		acqDataCount.increment();
    	}else if(table.equalsIgnoreCase("TBL_ACQRAWDATA")){
    		acqRawDataCount.increment();
    	}else if(table.equalsIgnoreCase("TBL_ALARMINFO_HIST")){
    		alarmDataCount.increment();
    	}else if(table.equalsIgnoreCase("TBL_DAILYTOTALCALCULATE_HIST")){
    		dailyTotalCalculateDataCount.increment();
    	}else if(table.equalsIgnoreCase("TBL_DAILYCALCULATIONDATA")){
    		dailyCalculateDataCount.increment();
    	}else if(table.equalsIgnoreCase("TBL_TIMINGCALCULATIONDATA")){
    		timingCalculateDataCount.increment();
    	}else if(table.equalsIgnoreCase("TBL_SRPACQDATA_HIST")){
    		SRPAcqDataCount.increment();
    	}else if(table.equalsIgnoreCase("TBL_SRPDAILYCALCULATIONDATA")){
    		SRPDailyCalculateDataCount.increment();
    	}else if(table.equalsIgnoreCase("TBL_SRPTIMINGCALCULATIONDATA")){
    		SRPTimingCalculateDataCount.increment();
    	}else if(table.equalsIgnoreCase("TBL_PCPACQDATA_HIST")){
    		PCPAcqDataCount.increment();
    	}else if(table.equalsIgnoreCase("TBL_PCPDAILYCALCULATIONDATA")){
    		PCPDailyCalculateDataCount.increment();
    	}else if(table.equalsIgnoreCase("TBL_PCPTIMINGCALCULATIONDATA")){
    		PCPTimingCalculateDataCount.increment();
    	}else if(table.equalsIgnoreCase("TBL_ACQDATA_VACUATE")){
    		acqDataVacuateCount.increment();
    	}else if(table.equalsIgnoreCase("TBL_SRPACQDATA_VACUATE")){
    		SRPAcqDataVacuateCount.increment();
    	}else if(table.equalsIgnoreCase("TBL_PCPACQDATA_VACUATE")){
    		PCPAcqDataVacuateCount.increment();
    	}
    }
    public static void decr(String table) {
    	if(table.equalsIgnoreCase("TBL_ACQDATA_HIST")){
    		acqDataCount.decrement();
    	}else if(table.equalsIgnoreCase("TBL_ACQRAWDATA")){
    		acqRawDataCount.decrement();
    	}else if(table.equalsIgnoreCase("TBL_ALARMINFO_HIST")){
    		alarmDataCount.decrement();
    	}else if(table.equalsIgnoreCase("TBL_DAILYTOTALCALCULATE_HIST")){
    		dailyTotalCalculateDataCount.decrement();
    	}else if(table.equalsIgnoreCase("TBL_DAILYCALCULATIONDATA")){
    		dailyCalculateDataCount.decrement();
    	}else if(table.equalsIgnoreCase("TBL_TIMINGCALCULATIONDATA")){
    		timingCalculateDataCount.decrement();
    	}else if(table.equalsIgnoreCase("TBL_SRPACQDATA_HIST")){
    		SRPAcqDataCount.decrement();
    	}else if(table.equalsIgnoreCase("TBL_SRPDAILYCALCULATIONDATA")){
    		SRPDailyCalculateDataCount.decrement();
    	}else if(table.equalsIgnoreCase("TBL_SRPTIMINGCALCULATIONDATA")){
    		SRPTimingCalculateDataCount.decrement();
    	}else if(table.equalsIgnoreCase("TBL_PCPACQDATA_HIST")){
    		PCPAcqDataCount.decrement();
    	}else if(table.equalsIgnoreCase("TBL_PCPDAILYCALCULATIONDATA")){
    		PCPDailyCalculateDataCount.decrement();
    	}else if(table.equalsIgnoreCase("TBL_PCPTIMINGCALCULATIONDATA")){
    		PCPTimingCalculateDataCount.decrement();
    	}else if(table.equalsIgnoreCase("TBL_ACQDATA_VACUATE")){
    		acqDataVacuateCount.decrement();
    	}else if(table.equalsIgnoreCase("TBL_SRPACQDATA_VACUATE")){
    		SRPAcqDataVacuateCount.decrement();
    	}else if(table.equalsIgnoreCase("TBL_PCPACQDATA_VACUATE")){
    		PCPAcqDataVacuateCount.decrement();
    	}
    }
    public static void reset() {
    	acqDataCount.reset();
    	acqRawDataCount.reset();
    	alarmDataCount.reset();
    	dailyTotalCalculateDataCount.reset();
    	dailyCalculateDataCount.reset();
    	timingCalculateDataCount.reset();
    	SRPAcqDataCount.reset();
    	SRPDailyCalculateDataCount.reset();
    	SRPTimingCalculateDataCount.reset();
    	PCPAcqDataCount.reset();
    	PCPDailyCalculateDataCount.reset();
    	PCPTimingCalculateDataCount.reset();
    	acqDataVacuateCount.reset();
    	SRPAcqDataVacuateCount.reset();
    	PCPAcqDataVacuateCount.reset();
    }
    public static void add(long x,String table) {
    	if(table.equalsIgnoreCase("TBL_ACQDATA_HIST")){
    		acqDataCount.add(x);
    	}else if(table.equalsIgnoreCase("TBL_ACQRAWDATA")){
    		acqRawDataCount.add(x);
    	}else if(table.equalsIgnoreCase("TBL_ALARMINFO_HIST")){
    		alarmDataCount.add(x);
    	}else if(table.equalsIgnoreCase("TBL_DAILYTOTALCALCULATE_HIST")){
    		dailyTotalCalculateDataCount.add(x);
    	}else if(table.equalsIgnoreCase("TBL_DAILYCALCULATIONDATA")){
    		dailyCalculateDataCount.add(x);
    	}else if(table.equalsIgnoreCase("TBL_TIMINGCALCULATIONDATA")){
    		timingCalculateDataCount.add(x);
    	}else if(table.equalsIgnoreCase("TBL_SRPACQDATA_HIST")){
    		SRPAcqDataCount.add(x);
    	}else if(table.equalsIgnoreCase("TBL_SRPDAILYCALCULATIONDATA")){
    		SRPDailyCalculateDataCount.add(x);
    	}else if(table.equalsIgnoreCase("TBL_SRPTIMINGCALCULATIONDATA")){
    		SRPTimingCalculateDataCount.add(x);
    	}else if(table.equalsIgnoreCase("TBL_PCPACQDATA_HIST")){
    		PCPAcqDataCount.add(x);
    	}else if(table.equalsIgnoreCase("TBL_PCPDAILYCALCULATIONDATA")){
    		PCPDailyCalculateDataCount.add(x);
    	}else if(table.equalsIgnoreCase("TBL_PCPTIMINGCALCULATIONDATA")){
    		PCPTimingCalculateDataCount.add(x);
    	}else if(table.equalsIgnoreCase("TBL_ACQDATA_VACUATE")){
    		acqDataVacuateCount.add(x);
    	}else if(table.equalsIgnoreCase("TBL_SRPACQDATA_VACUATE")){
    		SRPAcqDataVacuateCount.add(x);
    	}else if(table.equalsIgnoreCase("TBL_PCPACQDATA_VACUATE")){
    		PCPAcqDataVacuateCount.add(x);
    	}
    }
    public static long sum(String table) {
    	long sum=0;
    	if(table.equalsIgnoreCase("TBL_ACQDATA_HIST")){
    		sum=acqDataCount.sum();
    	}else if(table.equalsIgnoreCase("TBL_ACQRAWDATA")){
    		sum=acqRawDataCount.sum();
    	}else if(table.equalsIgnoreCase("TBL_ALARMINFO_HIST")){
    		sum=alarmDataCount.sum();
    	}else if(table.equalsIgnoreCase("TBL_DAILYTOTALCALCULATE_HIST")){
    		sum=dailyTotalCalculateDataCount.sum();
    	}else if(table.equalsIgnoreCase("TBL_DAILYCALCULATIONDATA")){
    		sum=dailyCalculateDataCount.sum();
    	}else if(table.equalsIgnoreCase("TBL_TIMINGCALCULATIONDATA")){
    		sum=timingCalculateDataCount.sum();
    	}else if(table.equalsIgnoreCase("TBL_SRPACQDATA_HIST")){
    		sum=SRPAcqDataCount.sum();
    	}else if(table.equalsIgnoreCase("TBL_SRPDAILYCALCULATIONDATA")){
    		sum=SRPDailyCalculateDataCount.sum();
    	}else if(table.equalsIgnoreCase("TBL_SRPTIMINGCALCULATIONDATA")){
    		sum=SRPTimingCalculateDataCount.sum();
    	}else if(table.equalsIgnoreCase("TBL_PCPACQDATA_HIST")){
    		sum=PCPAcqDataCount.sum();
    	}else if(table.equalsIgnoreCase("TBL_PCPDAILYCALCULATIONDATA")){
    		sum=PCPDailyCalculateDataCount.sum();
    	}else if(table.equalsIgnoreCase("TBL_PCPTIMINGCALCULATIONDATA")){
    		sum=PCPTimingCalculateDataCount.sum();
    	}else if(table.equalsIgnoreCase("TBL_ACQDATA_VACUATE")){
    		sum=acqDataVacuateCount.sum();
    	}else if(table.equalsIgnoreCase("TBL_SRPACQDATA_VACUATE")){
    		sum=SRPAcqDataVacuateCount.sum();
    	}else if(table.equalsIgnoreCase("TBL_PCPACQDATA_VACUATE")){
    		sum=PCPAcqDataVacuateCount.sum();
    	}
    	return sum;
    }
    
    public static void initCountDownLatch(int count){
    	countDownLatch=new CountDownLatch(count);
    }
    
    public static void countDown(){
    	countDownLatch.countDown();
    }
    
    public static long getCount(){
    	return countDownLatch.getCount();
    }
    
    public static void await() throws InterruptedException{
    	countDownLatch.await();
    }
    
    public static void calculateSpeedTimer(){
    	Timer timer = new Timer();
        // 创建定时器任务
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
            	
            }
        };
        timer.schedule(timerTask, 1000*2, 1000*60); // 两秒后每分钟执行一次
    }
}
