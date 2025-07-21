package com.cosog.thread.calculate;

import java.text.ParseException;
import java.util.List;

import com.cosog.utils.Config;
import com.cosog.utils.DatabaseMaintenanceCounterUtils;
import com.cosog.utils.OEMConfigFile;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.StringManagerUtils;

public class DatabaseHistoryDataDeleteThread implements Runnable{
	private int deviceId;
	private int calculateType;
	private String acqTime;

	public DatabaseHistoryDataDeleteThread(int deviceId,int calculateType,String acqTime) {
		super();
		this.deviceId = deviceId;
		this.calculateType = calculateType;
		this.acqTime = acqTime;
	}


	@SuppressWarnings({"static-access", "unused" })
	@Override
	public void run(){
//		int index=0;
//		while(index<100000){
//			index++;
//			StringManagerUtils.printLog("DatabaseHistoryDataDeleteThread,deviceId:"+deviceId+",index:"+index);
//			if (Thread.interrupted()) {
//                StringManagerUtils.printLog("线程"+deviceId + " 检测到中断请求");
//                break;
//            }
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				StringManagerUtils.printLog("线程"+deviceId + " 阻塞时检测到中断请求");
//				Thread.currentThread().interrupt();
//				break;
//			}
//		}
		
		
		if(StringManagerUtils.isNotNull(acqTime)){
			int cycle=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getCycle();
			String startTime=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getStartTime();
			int retentionTime=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getRetentionTime();
			int singleDeleteTime=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getSingleDeleteTime();
			OEMConfigFile.TableConfig tableConfig=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getTableConfig();
			
			int r=0;
			String delSql="";
			
			if (!Thread.interrupted()) {
				if(tableConfig.getAcqdata_hist()){
					deleteData("tbl_acqdata_hist", "acqTime","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
				}
			}
			
			if (!Thread.interrupted()) {
				if(tableConfig.getAcqrawdata()){
					deleteData("tbl_acqrawdata", "acqTime","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
				}
			}
			
			if (!Thread.interrupted()) {
				if(tableConfig.getAlarminfo_hist()){
					deleteData("tbl_alarminfo_hist", "alarmtime","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
				}
			}
			
			if (!Thread.interrupted()) {
				if(tableConfig.getDailytotalcalculate_hist()){
					deleteData("tbl_dailytotalcalculate_hist", "acqtime","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
				}
			}
			
			if (!Thread.interrupted()) {
				if(tableConfig.getDailycalculationdata()){
					deleteData("tbl_dailycalculationdata", "caldate","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
				}
			}
			
			if (!Thread.interrupted()) {
				if(tableConfig.getTimingcalculationdata()){
					deleteData("tbl_timingcalculationdata", "caltime","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
				}
			}
			
			if(calculateType==1){
				if (!Thread.interrupted()) {
					if(tableConfig.getSrpacqdata_hist()){
						deleteData("tbl_srpacqdata_hist", "acqTime","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
					}
				}
				if (!Thread.interrupted()) {
					if(tableConfig.getSrpdailycalculationdata()){
						deleteData("tbl_srpdailycalculationdata", "caldate","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
					}
				}
				if (!Thread.interrupted()) {
					if(tableConfig.getSrptimingcalculationdata()){
						deleteData("tbl_srptimingcalculationdata", "caltime","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
					}
				}
			}else if(calculateType==2){
				if (!Thread.interrupted()) {
					if(tableConfig.getPcpacqdata_hist()){
						deleteData("tbl_pcpacqdata_hist", "acqTime","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
					}
				}
				if (!Thread.interrupted()) {
					if(tableConfig.getPcpdailycalculationdata()){
						deleteData("tbl_pcpdailycalculationdata", "caldate","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
					}
				}
				if (!Thread.interrupted()) {
					if(tableConfig.getPcptimingcalculationdata()){
						deleteData("tbl_pcptimingcalculationdata", "caltime","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
					}
				}
			}
		}
//		StringManagerUtils.printLog("timingDeleteDatabaseHistoryData,deviceId:"+deviceId+", finished!");
		System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":timingDeleteDatabaseHistoryData,deviceId:"+deviceId+", finished!");
		DatabaseMaintenanceCounterUtils.countDown();
	}
	
	@SuppressWarnings("static-access")
	public void deleteData(String table,String timeColumn,String deviceColumn,int deviceId,String newestTime,String timeFormat){
		int delCount=0;
		int r=0;
		int retentionTime=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getRetentionTime();
		int singleDeleteTime=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getSingleDeleteTime();
		int singleDeleteRecord=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getSingleDeleteRecord();
		String oldestTimeSql="select to_char(min(t."+timeColumn+"),'yyyy-mm-dd') from "+table+" t where t."+deviceColumn+"="+deviceId;
		String newestDate=newestTime.split(" ")[0];
		List<Object[]> oldestTimeList=OracleJdbcUtis.query(oldestTimeSql);
		if(oldestTimeList.size()>0){
			String oldestDate=oldestTimeList.get(0)[0]+"";
			if(StringManagerUtils.isNotNull(oldestDate)){
				String delSql="";
				do{
					int range=0;
					try {
						range = StringManagerUtils.daysBetween(oldestDate, newestDate, "yyyy-MM-dd");
					} catch (ParseException e) {
						range=0;
					}
					
					try {
						if(range>retentionTime){
							if(range-retentionTime>singleDeleteTime){
								String sql="select count(1) from "+table+" t where t."+timeColumn+"<to_date('"+oldestDate+"','yyyy-mm-dd')+"+singleDeleteTime+" and t."+deviceColumn+"="+deviceId;
								List<Object[]> list=OracleJdbcUtis.query(sql);
								int count=StringManagerUtils.stringToInteger(list.get(0)[0]+"");
								if(count>singleDeleteRecord){
									do{
										int delRecord=count<singleDeleteRecord?count:singleDeleteRecord;
										delSql="delete from "+table+" t where t.id in ( "
												+ "select v.id from (select t2.id from "+table+" t2 "
												+ " where t2."+timeColumn+"<to_date('"+oldestDate+"','yyyy-mm-dd')+"+singleDeleteTime+" "
												+ " and t2."+deviceColumn+"="+deviceId 
												+ " order by t2."+timeColumn+") v"
												+ " where rownum<="+delRecord
												+ " )";
										r=OracleJdbcUtis.executeSqlUpdate(delSql);
										if(r>0){
											delCount+=r;
										}
										count-=delRecord;
										Thread.yield();
									}while(!Thread.interrupted() && count>0);
								}else{
									delSql="delete from "+table+" t where t."+timeColumn+"<to_date('"+oldestDate+"','yyyy-mm-dd')+"+singleDeleteTime+" and t."+deviceColumn+"="+deviceId;
									r=OracleJdbcUtis.executeSqlUpdate(delSql);
									if(r>0){
										delCount+=r;
									}
								}
								oldestDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(oldestDate,"yyyy-MM-dd"), singleDeleteTime);
							}else {
								String sql="select count(1) from "+table+" t where t."+timeColumn+"<to_date('"+newestDate+"','yyyy-mm-dd')-"+retentionTime+" and t."+deviceColumn+"="+deviceId;
								List<Object[]> list=OracleJdbcUtis.query(sql);
								int count=StringManagerUtils.stringToInteger(list.get(0)[0]+"");
								if(count>singleDeleteRecord){
									do{
										int delRecord=count<singleDeleteRecord?count:singleDeleteRecord;
										delSql="delete from "+table+" t where t.id in ( "
												+ "select v.id from (select t2.id from "+table+" t2 "
												+ " where t2."+timeColumn+"<to_date('"+newestDate+"','yyyy-mm-dd')-"+retentionTime+" "
												+ " and t2."+deviceColumn+"="+deviceId 
												+ " order by t2."+timeColumn+") v"
												+ " where rownum<="+delRecord
												+ " )";
										r=OracleJdbcUtis.executeSqlUpdate(delSql);
										if(r>0){
											delCount+=r;
										}
										count-=delRecord;
										Thread.yield();
									}while(!Thread.interrupted() && count>0);
								}else{
									delSql="delete from "+table+" t where t."+timeColumn+"<to_date('"+newestDate+"','yyyy-mm-dd')-"+retentionTime+" and t."+deviceColumn+"="+deviceId;
									r=OracleJdbcUtis.executeSqlUpdate(delSql);
									if(r>0){
										delCount+=r;
									}
								}
								break;
							}
						}else{
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
					Thread.yield();
				}while(!Thread.interrupted());
			}
		}
//		StringManagerUtils.printLog("timingDeleteDatabaseHistoryData,deviceId:"+deviceId+",table:"+table+",delCount:"+delCount+", finished!");
		System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":timingDeleteDatabaseHistoryData,deviceId:"+deviceId+",table:"+table+",delCount:"+delCount+", finished!");
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public int getCalculateType() {
		return calculateType;
	}

	public void setCalculateType(int calculateType) {
		this.calculateType = calculateType;
	}

	public String getAcqTime() {
		return acqTime;
	}

	public void setAcqTime(String acqTime) {
		this.acqTime = acqTime;
	}
}
