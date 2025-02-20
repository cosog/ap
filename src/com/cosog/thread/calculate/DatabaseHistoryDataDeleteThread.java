package com.cosog.thread.calculate;

import com.cosog.utils.Config;
import com.cosog.utils.DatabaseMaintenanceCounterUtils;
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


	@Override
	public void run(){
		if(StringManagerUtils.isNotNull(acqTime)){
			int cycle=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getCycle();
			String startTime=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getStartTime();
			int retentionTime=Config.getInstance().configFile.getAp().getDatabaseMaintenance().getRetentionTime();
			
			String delSql="delete from tbl_acqdata_hist t where t.acqtime<to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')-"+retentionTime+" and t.deviceid="+deviceId;
			int r=OracleJdbcUtis.executeSqlUpdate(delSql);
			
			delSql="delete from tbl_acqrawdata t where t.acqtime<to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')-"+retentionTime+" and t.deviceid="+deviceId;
			r=OracleJdbcUtis.executeSqlUpdate(delSql);
			
			delSql="delete from tbl_alarminfo_hist t where t.alarmtime<to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')-"+retentionTime+" and t.deviceid="+deviceId;
			r=OracleJdbcUtis.executeSqlUpdate(delSql);
			
			delSql="delete from tbl_dailytotalcalculate_hist t where t.acqtime<to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')-"+retentionTime+" and t.deviceid="+deviceId;
			r=OracleJdbcUtis.executeSqlUpdate(delSql);
			
			delSql="delete from tbl_dailycalculationdata t where t.caldate<to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')-"+retentionTime+" and t.deviceid="+deviceId;
			r=OracleJdbcUtis.executeSqlUpdate(delSql);
			
			delSql="delete from tbl_timingcalculationdata t where t.caltime<to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')-"+retentionTime+" and t.deviceid="+deviceId;
			r=OracleJdbcUtis.executeSqlUpdate(delSql);
			
			if(calculateType==1){
				delSql="delete from tbl_srpacqdata_hist t where t.acqtime<to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')-"+retentionTime+" and t.deviceid="+deviceId;
				r=OracleJdbcUtis.executeSqlUpdate(delSql);
				
				delSql="delete from tbl_srpdailycalculationdata t where t.caldate<to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')-"+retentionTime+" and t.deviceid="+deviceId;
				r=OracleJdbcUtis.executeSqlUpdate(delSql);
				
				delSql="delete from tbl_srptimingcalculationdata t where t.caltime<to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')-"+retentionTime+" and t.deviceid="+deviceId;
				r=OracleJdbcUtis.executeSqlUpdate(delSql);
				
			}else if(calculateType==2){
				delSql="delete from tbl_pcpacqdata_hist t where t.acqtime<to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')-"+retentionTime+" and t.deviceid="+deviceId;
				r=OracleJdbcUtis.executeSqlUpdate(delSql);
				
				delSql="delete from tbl_pcpdailycalculationdata t where t.caldate<to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')-"+retentionTime+" and t.deviceid="+deviceId;
				r=OracleJdbcUtis.executeSqlUpdate(delSql);
				
				delSql="delete from tbl_pcptimingcalculationdata t where t.caltime<to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')-"+retentionTime+" and t.deviceid="+deviceId;
				r=OracleJdbcUtis.executeSqlUpdate(delSql);
			}
		}
		DatabaseMaintenanceCounterUtils.countDown();
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
