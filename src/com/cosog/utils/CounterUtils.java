package com.cosog.utils;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.LongAdder;

import org.apache.log4j.Logger;

public class CounterUtils {
	public static LongAdder count = new LongAdder();
	public static LongAdder exceptionCalCount = new LongAdder();
	public static CountDownLatch countDownLatch;
	private static final Logger logger = Logger.getLogger(CounterUtils.class.getName());
    public static void incr() {
        count.increment();
    }
    public static void reset() {
    	count.reset();
    }
    public static long sum(){
    	return count.sum();
    }
    
    public static void exceptionCalCountIncr() {
    	exceptionCalCount.increment();
    }
    public static void exceptionCalCountReset() {
    	exceptionCalCount.reset();
    }
    public static long exceptionCalCountSum(){
    	return exceptionCalCount.sum();
    }
    
    public static void initCountDownLatch(int count){
    	countDownLatch=new CountDownLatch(count);
    }
    
    public static void countDown(){
    	countDownLatch.countDown();
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
            	long lastCount=0;
            	long currentCount=sum();
            	
            	long expLastCount=0;
            	long expCurrentCount=exceptionCalCountSum();
            	
            	Map<String, Object> map = DataModelMap.getMapObject();
            	if(map.containsKey("CounterExecuteCount")){
            		lastCount=(long) map.get("CounterExecuteCount");
            		long tpm=currentCount-lastCount;
            		
            		expLastCount=(long) map.get("CounterExecuteExpCount");
            		long exptpm=expCurrentCount-expLastCount;
            		
            		StringManagerUtils.printLog("飞舟历史数据同步速度 TPM:"+tpm);
            	}
            	map.put("CounterExecuteCount", currentCount);
            	map.put("CounterExecuteExpCount", expCurrentCount);
            }
        };
        timer.schedule(timerTask, 1000*2, 1000*60); // 两秒后每分钟执行一次
    }
}
