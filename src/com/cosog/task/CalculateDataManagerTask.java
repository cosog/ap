package com.cosog.task;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cosog.model.DataMapping;
import com.cosog.model.KeyValue;
import com.cosog.model.calculate.AcqInstanceOwnItem;
import com.cosog.model.calculate.CommResponseData;
import com.cosog.model.calculate.DeviceInfo;
import com.cosog.model.calculate.DeviceInfo.DailyTotalItem;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.utils.Config;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("calculateDataManagerTast")  
public class CalculateDataManagerTask {
	public static ScheduledExecutorService AcquisitionDataTotalCalculationExecutor=null;
	public static ScheduledExecutorService RPCTotalCalculationExecutor=null;
	public static ScheduledExecutorService PCPTotalCalculationExecutor=null;
	public static ScheduledExecutorService timingInitDailyReportDataExecutor=null;
	public static ScheduledExecutorService AcquisitionTimingCalculateExecutor=null;
	public static ScheduledExecutorService RPCTimingCalculateExecutor=null;
	public static ScheduledExecutorService PCPTimingCalculateExecutor=null;
	
	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void timer(){
		long time=StringManagerUtils.stringToTimeStamp("2024-05-14 12:00:00", "yyyy-MM-dd HH:mm:ss");
		//报表初始化
		timingInitDailyReportData();
		
		//跨天汇总
		AcquisitionDataTotalCalculation();
		RPCTotalCalculation();
		PCPTotalCalculation();
		
//		AcquisitionTimingCalculate();
//		RPCTimingCalculate();
//		PCPTimingCalculate();
	}
	
	@SuppressWarnings("static-access")
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
		Thread.currentThread().yield();
	}
	
	@SuppressWarnings("static-access")
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
		Thread.currentThread().yield();
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
		String url=stringManagerUtils.getProjectUrl()+"/calculateDataController/initDailyReportData?calculateType=0";
		String result=StringManagerUtils.sendPostMethod(url, "","utf-8",0,0);
		url=stringManagerUtils.getProjectUrl()+"/calculateDataController/initDailyReportData?calculateType=1";
		result=StringManagerUtils.sendPostMethod(url, "","utf-8",0,0);
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
	
	public static void acquisitionDataTotalCalculate(String deviceIdStr,String date){
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle();
		Map<String,DataMapping> loadProtocolMappingColumnMap=MemoryDataManagerTask.getProtocolMappingColumn();
		CommResponseData.Range dateTimeRange= StringManagerUtils.getTimeRange(date,Config.getInstance().configFile.getAp().getReport().getOffsetHour());
		String sql="select t.deviceId,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,t.acqdata";
		String newestDailyTotalDataSql="select t.id,t.deviceid,t.acqtime,t.itemcolumn,t.itemname,t.totalvalue,t.todayvalue "
				+ " from tbl_dailytotalcalculate_hist t,"
				+ " (select deviceid,max(acqtime) as acqtime,itemcolumn  "
				+ "  from tbl_dailytotalcalculate_hist "
				+ "  where acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+dateTimeRange.getEndTime()+"','yyyy-mm-dd hh24:mi:ss') "
				+ "  group by deviceid,itemcolumn) v "
				+ " where t.deviceid=v.deviceid and t.acqtime=v.acqtime and t.itemcolumn=v.itemcolumn"
				+ " and t.acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+dateTimeRange.getEndTime()+"','yyyy-mm-dd hh24:mi:ss') ";
		sql+=" from tbl_acqdata_hist t "
			+ " where t.acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+dateTimeRange.getEndTime()+"','yyyy-mm-dd hh24:mi:ss') "
			+ " and t.checksign=1";
		if(StringManagerUtils.isNotNull(deviceIdStr)){
			sql+=" and t.deviceid="+deviceIdStr;
			newestDailyTotalDataSql+=" and t.deviceid="+deviceIdStr;
		}
		sql+="order by t.deviceid,t.acqTime";
		newestDailyTotalDataSql+=" order by t.deviceid";
		
		List<Object[]> totalList=OracleJdbcUtis.query(sql);
		List<Object[]> newestDailyTotalDataList=OracleJdbcUtis.query(newestDailyTotalDataSql);
		
		Map< Integer,Map<String,List<KeyValue>> > acqDataMap=new LinkedHashMap<>();
		
		for(int i=0;i<totalList.size();i++){
			Object[] obj=totalList.get(i);
			int deviceId=StringManagerUtils.stringToInteger(obj[0]+"");
			String acqTime=obj[1]+"";
			String acqData=obj[2]+"";
			
			type = new TypeToken<List<KeyValue>>() {}.getType();
			List<KeyValue> acqDataList=gson.fromJson(acqData, type);
			if(acqDataList!=null){
				if(acqDataMap.containsKey(deviceId)){
					Map<String,List<KeyValue>> deviceAcqDataMap=acqDataMap.get(deviceId);
					deviceAcqDataMap.put(acqTime, acqDataList);
					acqDataMap.put(deviceId, deviceAcqDataMap);
				}else{
					Map<String,List<KeyValue>> deviceAcqDataMap=new LinkedHashMap<>();
					deviceAcqDataMap.put(acqTime, acqDataList);
					acqDataMap.put(deviceId, deviceAcqDataMap);
				}
			}
			
		}
		
		Iterator<Map.Entry< Integer,Map<String,List<KeyValue>> >> iterator = acqDataMap.entrySet().iterator();
		while (iterator.hasNext()) {
			 Map.Entry< Integer,Map<String,List<KeyValue>> > entry = iterator.next();
			 int deviceId = entry.getKey();
			 
			 DeviceInfo deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId+"");
			 AcqInstanceOwnItem acqInstanceOwnItem=null;
			 ModbusProtocolConfig.Protocol protocol=null;
			 if(deviceInfo!=null){
				 acqInstanceOwnItem=MemoryDataManagerTask.getAcqInstanceOwnItemByCode(deviceInfo.getInstanceCode());
				 if(acqInstanceOwnItem!=null){
					 protocol=MemoryDataManagerTask.getProtocolByName(acqInstanceOwnItem.getProtocol());
				 }
			 }
			 
			 Map<String,List<KeyValue>> deviceAcqDataMap = entry.getValue();
			 
			 List<KeyValue> deviceTotalDataList=new ArrayList<>();
			 
			 Map<String,List<String>> itemDataMap=new LinkedHashMap<>();
			 
			 Iterator<Map.Entry<String,List<KeyValue>>> deviceAcqDataIterator = deviceAcqDataMap.entrySet().iterator();
			 while (deviceAcqDataIterator.hasNext()) {
				 Map.Entry<String,List<KeyValue>> deviceAcqDataEntry = deviceAcqDataIterator.next();
				 String acqTime=deviceAcqDataEntry.getKey();
				 List<KeyValue> deviceAcqDataList=deviceAcqDataEntry.getValue();
				 
				 if(deviceAcqDataList!=null){
					 for(KeyValue keyValue:deviceAcqDataList){
						 if(itemDataMap.containsKey(keyValue.getKey())){
							 List<String> itemDataList=itemDataMap.get(keyValue.getKey());
							 itemDataList.add(keyValue.getValue());
							 itemDataMap.put(keyValue.getKey(), itemDataList);
						 }else{
							 List<String> itemDataList=new ArrayList<>();
							 itemDataList.add(keyValue.getValue());
							 itemDataMap.put(keyValue.getKey(), itemDataList);
						 }
					 }
				 }
			 }
			 
			 if(itemDataMap.size()>0){
				 Iterator<Map.Entry<String,List<String>>> itemDataMapIterator = itemDataMap.entrySet().iterator();
				 while (itemDataMapIterator.hasNext()) {
					 Map.Entry<String,List<String>> itemDataEntry = itemDataMapIterator.next();
					 String itemCode=itemDataEntry.getKey();
					 
					 ModbusProtocolConfig.Items item=null;
					 DataMapping dataMapping=null;
					 if(loadProtocolMappingColumnMap!=null){
						 dataMapping=loadProtocolMappingColumnMap.get(itemCode);
						 if(dataMapping!=null){
							 item=MemoryDataManagerTask.getProtocolItem(protocol,  dataMapping.getName());
						 }
					 }
					 
					 List<String> itemDataList=itemDataEntry.getValue();
					 String maxValue=" ",minValue=" ",avgValue=" ",newestValue=" ",oldestValue=" ",dailyTotalValue=" ";
					 String tatalValue="";
					 
					 if(itemDataList!=null && itemDataList.size()>0 ){
						 oldestValue=itemDataList.get(0);
						 newestValue=itemDataList.get(itemDataList.size()-1);
						 
						 maxValue=itemDataList.get(0);
						 minValue=itemDataList.get(0);
						 
						 float sumValue=0;
						 int count=0;
						 for(String itemDataStr:itemDataList){
							 if(StringManagerUtils.isNotNull(itemDataStr)){
								 float itemData=StringManagerUtils.stringToFloat(itemDataStr);
								 sumValue+=itemData;
								 count++;
								 if(StringManagerUtils.stringToFloat(maxValue)<itemData){
									 maxValue=itemDataStr;
								 }
								 if(StringManagerUtils.stringToFloat(minValue)>itemData){
									 minValue=itemDataStr;
								 }
							 }
						 }
						 if(count>0){
							 avgValue=StringManagerUtils.stringToFloat((sumValue/count)+"",3)+"";
						 }
					 }
					 
					 
					 String totalColumn=(itemCode+"_total").toUpperCase();
					 for(Object[] newestDailyTotalDataObj:newestDailyTotalDataList){
						if(deviceId==StringManagerUtils.stringToInteger(newestDailyTotalDataObj[1]+"") && totalColumn.equalsIgnoreCase(newestDailyTotalDataObj[3]+"")){
							dailyTotalValue=newestDailyTotalDataObj[6]+"";
							break;
						}
					}
					 
					 if(item!=null && item.getQuantity()==1 
								&& ("int".equalsIgnoreCase(item.getIFDataType()) || "float".equalsIgnoreCase(item.getIFDataType()) || "float32".equalsIgnoreCase(item.getIFDataType()) || "float64".equalsIgnoreCase(item.getIFDataType())  )
								){
						tatalValue=(maxValue+";"+minValue+";"+avgValue+";"+oldestValue+";"+newestValue+";"+dailyTotalValue).replaceAll("null", "");
					}else{
						tatalValue=newestValue;
					}
					 
					KeyValue keyValue=new KeyValue(itemCode,tatalValue);
					deviceTotalDataList.add(keyValue);
				}
			 }
			 
			 String updatesql="update tbl_dailycalculationdata t set t.calData=?  where t.deviceid="+deviceId+" and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
			 List<String> totalDataClobCont=new ArrayList<String>();
			 totalDataClobCont.add(new Gson().toJson(deviceTotalDataList));
			 try {
				OracleJdbcUtis.executeSqlUpdateClob(updatesql, totalDataClobCont);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	}
	
	public static void acquisitionDataTimingTotalCalculate(String deviceIdStr,String timeStr){
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String date=timeStr.split(" ")[0];
		
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle();
		Map<String,DataMapping> loadProtocolMappingColumnMap=MemoryDataManagerTask.getProtocolMappingColumn();
		
		CommResponseData.Range dateTimeRange= StringManagerUtils.getTimeRange(date,Config.getInstance().configFile.getAp().getReport().getOffsetHour());
		String sql="select t.deviceId,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,t.acqdata";
		String newestDailyTotalDataSql="select t.id,t.deviceid,t.acqtime,t.itemcolumn,t.itemname,t.totalvalue,t.todayvalue "
				+ " from tbl_dailytotalcalculate_hist t,"
				+ " (select deviceid,max(acqtime) as acqtime,itemcolumn  "
				+ "  from tbl_dailytotalcalculate_hist "
				+ "  where acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') "
				+ "  group by deviceid,itemcolumn) v "
				+ " where t.deviceid=v.deviceid and t.acqtime=v.acqtime and t.itemcolumn=v.itemcolumn"
				+ " and t.acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') "
				+ " ";
		sql+=" from tbl_acqdata_hist t "
				+ " where t.acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') "
				+ " and t.checksign=1";
		if(StringManagerUtils.isNotNull(deviceIdStr)){
			sql+=" and t.deviceid="+deviceIdStr;
			newestDailyTotalDataSql+=" and t.deviceid="+deviceIdStr;
		}
		sql+="order by t.deviceid,t.acqTime";
		newestDailyTotalDataSql+=" order by t.deviceid";
		
		List<Object[]> totalList=OracleJdbcUtis.query(sql);
		List<Object[]> newestDailyTotalDataList=OracleJdbcUtis.query(newestDailyTotalDataSql);
		
		Map< Integer,Map<String,List<KeyValue>> > acqDataMap=new LinkedHashMap<>();
		
		for(int i=0;i<totalList.size();i++){
			Object[] obj=totalList.get(i);
			int deviceId=StringManagerUtils.stringToInteger(obj[0]+"");
			String acqTime=obj[1]+"";
			String acqData=obj[2]+"";
			
			type = new TypeToken<List<KeyValue>>() {}.getType();
			List<KeyValue> acqDataList=gson.fromJson(acqData, type);
			if(acqDataList!=null){
				if(acqDataMap.containsKey(deviceId)){
					Map<String,List<KeyValue>> deviceAcqDataMap=acqDataMap.get(deviceId);
					deviceAcqDataMap.put(acqTime, acqDataList);
					acqDataMap.put(deviceId, deviceAcqDataMap);
				}else{
					Map<String,List<KeyValue>> deviceAcqDataMap=new LinkedHashMap<>();
					deviceAcqDataMap.put(acqTime, acqDataList);
					acqDataMap.put(deviceId, deviceAcqDataMap);
				}
			}
			
		}
		
		Iterator<Map.Entry< Integer,Map<String,List<KeyValue>> >> iterator = acqDataMap.entrySet().iterator();
		while (iterator.hasNext()) {
			 Map.Entry< Integer,Map<String,List<KeyValue>> > entry = iterator.next();
			 int deviceId = entry.getKey();
			 DeviceInfo deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId+"");
			 AcqInstanceOwnItem acqInstanceOwnItem=null;
			 ModbusProtocolConfig.Protocol protocol=null;
			 if(deviceInfo!=null){
				 acqInstanceOwnItem=MemoryDataManagerTask.getAcqInstanceOwnItemByCode(deviceInfo.getInstanceCode());
				 if(acqInstanceOwnItem!=null){
					 protocol=MemoryDataManagerTask.getProtocolByName(acqInstanceOwnItem.getProtocol());
				 }
			 }
			 
			 Map<String,List<KeyValue>> deviceAcqDataMap = entry.getValue();
			 
			 List<KeyValue> deviceTotalDataList=new ArrayList<>();
			 
			 Map<String,List<String>> itemDataMap=new LinkedHashMap<>();
			 
			 Iterator<Map.Entry<String,List<KeyValue>>> deviceAcqDataIterator = deviceAcqDataMap.entrySet().iterator();
			 while (deviceAcqDataIterator.hasNext()) {
				 Map.Entry<String,List<KeyValue>> deviceAcqDataEntry = deviceAcqDataIterator.next();
				 String acqTime=deviceAcqDataEntry.getKey();
				 List<KeyValue> deviceAcqDataList=deviceAcqDataEntry.getValue();
				 
				 if(deviceAcqDataList!=null){
					 for(KeyValue keyValue:deviceAcqDataList){
						 if(itemDataMap.containsKey(keyValue.getKey())){
							 List<String> itemDataList=itemDataMap.get(keyValue.getKey());
							 itemDataList.add(keyValue.getValue());
							 itemDataMap.put(keyValue.getKey(), itemDataList);
						 }else{
							 List<String> itemDataList=new ArrayList<>();
							 itemDataList.add(keyValue.getValue());
							 itemDataMap.put(keyValue.getKey(), itemDataList);
						 }
					 }
				 }
			 }
			 
			 if(itemDataMap.size()>0){
				 Iterator<Map.Entry<String,List<String>>> itemDataMapIterator = itemDataMap.entrySet().iterator();
				 while (itemDataMapIterator.hasNext()) {
					 Map.Entry<String,List<String>> itemDataEntry = itemDataMapIterator.next();
					 String itemCode=itemDataEntry.getKey();
					
					 ModbusProtocolConfig.Items item=null;
					 DataMapping dataMapping=null;
					 if(loadProtocolMappingColumnMap!=null){
						 dataMapping=loadProtocolMappingColumnMap.get(itemCode);
						 if(dataMapping!=null){
							 item=MemoryDataManagerTask.getProtocolItem(protocol,  dataMapping.getName());
						 }
					 }
					 
					 List<String> itemDataList=itemDataEntry.getValue();
					 String maxValue=" ",minValue=" ",avgValue=" ",newestValue=" ",oldestValue=" ",dailyTotalValue=" ";
					 String tatalValue="";
					 
					 if(itemDataList!=null && itemDataList.size()>0 ){
						 oldestValue=itemDataList.get(0);
						 newestValue=itemDataList.get(itemDataList.size()-1);
						 
						 maxValue=itemDataList.get(0);
						 minValue=itemDataList.get(0);
						 
						 float sumValue=0;
						 int count=0;
						 for(String itemDataStr:itemDataList){
							 if(StringManagerUtils.isNotNull(itemDataStr)){
								 float itemData=StringManagerUtils.stringToFloat(itemDataStr);
								 sumValue+=itemData;
								 count++;
								 if(StringManagerUtils.stringToFloat(maxValue)<itemData){
									 maxValue=itemDataStr;
								 }
								 if(StringManagerUtils.stringToFloat(minValue)>itemData){
									 minValue=itemDataStr;
								 }
							 }
						 }
						 if(count>0){
							 avgValue=StringManagerUtils.stringToFloat((sumValue/count)+"",3)+"";
						 }
					 }
					 
					 String totalColumn=(itemCode+"_total").toUpperCase();
					 for(Object[] newestDailyTotalDataObj:newestDailyTotalDataList){
						if(deviceId==StringManagerUtils.stringToInteger(newestDailyTotalDataObj[1]+"") && totalColumn.equalsIgnoreCase(newestDailyTotalDataObj[3]+"")){
							dailyTotalValue=newestDailyTotalDataObj[6]+"";
							break;
						}
					}
					if(item!=null && item.getQuantity()==1 
							&& ("int".equalsIgnoreCase(item.getIFDataType()) || "float".equalsIgnoreCase(item.getIFDataType()) || "float32".equalsIgnoreCase(item.getIFDataType()) || "float64".equalsIgnoreCase(item.getIFDataType())  )
							){
						tatalValue=(maxValue+";"+minValue+";"+avgValue+";"+oldestValue+";"+newestValue+";"+dailyTotalValue).replaceAll("null", "");
					}else{
						tatalValue=newestValue;
					}
					 
					KeyValue keyValue=new KeyValue(itemCode,tatalValue);
					deviceTotalDataList.add(keyValue);
				}
			 }
			 
			 String updatesql="update tbl_timingcalculationdata t set t.calData=?  where t.deviceid="+deviceId+" and t.caltime=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss')";
			 List<String> totalDataClobCont=new ArrayList<String>();
			 totalDataClobCont.add(new Gson().toJson(deviceTotalDataList));
			 try {
				OracleJdbcUtis.executeSqlUpdateClob(updatesql, totalDataClobCont);
			} catch (SQLException e) {
				e.printStackTrace();
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
