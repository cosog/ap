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


	@SuppressWarnings({"static-access" })
	@Override
	public void run(){
		if(StringManagerUtils.isNotNull(acqTime)){
			int cycle=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getCycle();
			String startTime=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getStartTime();
			int retentionTime=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getRetentionTime();
			int singleDeleteTime=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getSingleDeleteTime();
			OEMConfigFile.TableConfig tableConfig=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getTableConfig();
			
			int r=0;
			String delSql="";
			
			if(tableConfig.getAcqdata_hist()){
				deleteData("tbl_acqdata_hist", "acqTime","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
			}
			
			if(tableConfig.getAcqrawdata()){
				deleteData("tbl_acqrawdata", "acqTime","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
			}
			
			if(tableConfig.getAlarminfo_hist()){
				deleteData("tbl_alarminfo_hist", "alarmtime","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
			}
			
			if(tableConfig.getDailytotalcalculate_hist()){
				deleteData("tbl_dailytotalcalculate_hist", "acqtime","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
			}
			
			if(tableConfig.getDailycalculationdata()){
				deleteData("tbl_dailycalculationdata", "caldate","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
			}
			
			if(tableConfig.getTimingcalculationdata()){
				deleteData("tbl_timingcalculationdata", "caltime","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
			}
			if(calculateType==1){
				if(tableConfig.getSrpacqdata_hist()){
					deleteData("tbl_srpacqdata_hist", "acqTime","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
				}
				
				if(tableConfig.getSrpdailycalculationdata()){
					deleteData("tbl_srpdailycalculationdata", "caldate","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
				}
				
				if(tableConfig.getSrptimingcalculationdata()){
					deleteData("tbl_srptimingcalculationdata", "caltime","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
				}
				
			}else if(calculateType==2){
				if(tableConfig.getPcpacqdata_hist()){
					deleteData("tbl_pcpacqdata_hist", "acqTime","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
				}
				
				if(tableConfig.getPcpdailycalculationdata()){
					deleteData("tbl_pcpdailycalculationdata", "caldate","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
				}
				
				if(tableConfig.getPcptimingcalculationdata()){
					deleteData("tbl_pcptimingcalculationdata", "caltime","deviceId",deviceId,acqTime,"yyyy-mm-dd hh24:mi:ss");
				}
			}
		}
		DatabaseMaintenanceCounterUtils.countDown();
	}
	
	public void deleteData(String table,String timeColumn,String deviceColumn,int deviceId,String newestTime,String timeFormat){
		int retentionTime=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getRetentionTime();
		int singleDeleteTime=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getSingleDeleteTime();
		String oldestTimeSql="select to_char(min(t."+timeColumn+"),'yyyy-mm-dd') from "+table+" t where t."+deviceColumn+"="+deviceId;
		String newestDate=newestTime.split(" ")[0];
		List<Object[]> oldestTimeList=OracleJdbcUtis.query(oldestTimeSql);
		if(oldestTimeList.size()>0){
			String oldestDate=oldestTimeList.get(0)+"";
			if(StringManagerUtils.isNotNull(oldestDate)){
				String delSql="";
				do{
					int range=0;
					try {
						range = StringManagerUtils.daysBetween(oldestDate, newestDate, "yyyy-MM-dd");
					} catch (ParseException e) {
						range=0;
						e.printStackTrace();
					}
					
					try {
						if(range-retentionTime>singleDeleteTime){
							delSql="delete from "+table+" t where t."+timeColumn+"<to_date('"+oldestDate+"','"+timeFormat+"')+"+singleDeleteTime+" and t."+deviceColumn+"="+deviceId;
							OracleJdbcUtis.executeSqlUpdate(delSql);
							oldestDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(oldestDate,"yyyy-MM-dd"), singleDeleteTime);
						}else{
							delSql="delete from "+table+" t where t."+timeColumn+"<to_date('"+newestTime+"','"+timeFormat+"')-"+retentionTime+" and t."+deviceColumn+"="+deviceId;
							OracleJdbcUtis.executeSqlUpdate(delSql);
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}while(true);
			}
			
		}
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
