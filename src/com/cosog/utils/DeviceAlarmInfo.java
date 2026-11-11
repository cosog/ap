package com.cosog.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.cosog.model.drive.AcquisitionItemInfo;

public class DeviceAlarmInfo {
	
	// ★ 报警触发时的回调接口
    public interface AlarmTriggerCallback {
        void onAlarmTriggered(int deviceId, String alarmKey, AlarmInfo alarmInfo);
    }
    
    // ★ 全局回调实现（由 Controller 启动时注入）
    public static AlarmTriggerCallback alarmTriggerCallback = null;
	
	public int deviceId;
	
	public String deviceName;
	
	public int deviceType;
	
	private String deviceTypeName_zh_CN;
	
	private String deviceTypeName_en;
	
	private String deviceTypeName_ru;
	
	public Map<String,AlarmInfo> alarmInfoMap;
	
	public Map<String,ScheduledExecutorService> alarmInfoTimerMap;
	
	public void addTimer(String alarmKey, int delay, AlarmInfo alarmInfo, String acqTime){
	    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
	    executorService.schedule(new Thread(new Runnable() {
	        @Override
	        public void run() {
	        	// ★ 定时器只负责"计时到期"这个信号（清理 timerMap），
	            //    不再修改 alarmInfo 的 triggerAlarm / alarmStartTime。
	            //
	            // 原因：
	            //   1) 定时器线程没有入库、没有 WebSocket 推送，改状态不会产生任何业务效果；
	            //   2) 旧代码把 alarmStartTime 设为 addTimer 传入的 acqTime（延时开始时刻），
	            //      会让下一次采集 timeDiff≈delay，被 retriggerTime 误判为"持续中"，
	            //      反而把 triggerAlarm 覆盖成 false。
	            //
	            // 真正的报警触发由延时结束后的第一次采集完成：
	            //   DataAlarmProcessing / CalculateDataAlarmProcessing 中通过
	            //   wasPendingDelay 判断"延时刚结束"，强制 triggerAlarm=true，
	            //   再由 DataProcessing 完成入库 + WebSocket 推送 + 前端弹窗响铃。
	            //
	            // 参数说明：alarmInfo、acqTime 保留以兼容调用方签名，但不再使用。
	        	try {
	                if(!executorService.isShutdown()){
	                    executorService.shutdown();
	                }
	                if(alarmInfoTimerMap!=null){
	                    alarmInfoTimerMap.remove(alarmKey);
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }), delay, TimeUnit.SECONDS);
	    alarmInfoTimerMap.put(alarmKey, executorService);
	}
	
	public void sendAlarmSMS(String deviceName,String deviceType,String deviceTypeName,boolean isSendSMS,boolean isSendMail,String SMSContent,String EMailContent) throws SQLException{
		String SMSUrl=Config.getInstance().configFile.getAd().getRw().getWriteSMS();
		String deviceTableName="tbl_device";
		
		String userSql="select u.user_id,u.user_phone,u.user_receivesms,u.user_in_email,u.user_receivemail "
				+ " from tbl_user u,tbl_role r "
				+ " where u.user_enable=1 and u.user_type=r.role_id "
				+ " and ("
				+ "	u.user_orgid in ("
				+ "		select org_id from tbl_org t "
				+ "		start with org_id=( select t2.orgid from "+deviceTableName+" t2 where t2.deviceName='"+deviceName+"' and t2.devicetype="+deviceType+" ) "
				+ "		connect by prior  org_parent=org_id"
				+ " ) or u.user_orgid=0"
				+ " )";
		List<Object[]> list=OracleJdbcUtis.query(userSql);
		List<String> receivingEMailAccount=new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			Object[] obj=list.get(i);
			if(isSendSMS&&"1".equalsIgnoreCase(obj[2]+"") && StringManagerUtils.isNotNull(obj[1]+"") && StringManagerUtils.isPhoneLegal(obj[1]+"")){
				StringBuffer sendContent = new StringBuffer();
				sendContent.append("{\"Mobile\":\""+obj[1]+"\",\"Value\":\""+SMSContent+"\"}");
				StringManagerUtils.sendPostMethod(SMSUrl, sendContent.toString(), "utf-8",0,0);
			}
			if("1".equalsIgnoreCase(obj[4]+"") && StringManagerUtils.isNotNull(obj[3]+"") && StringManagerUtils.isMailLegal(obj[3]+"")){
				receivingEMailAccount.add(obj[3]+"");
			}
		}
		if(isSendMail&&receivingEMailAccount.size()>0){
			StringManagerUtils.sendEMail(deviceTypeName+deviceName+"报警", EMailContent, receivingEMailAccount);
		}
	}
	
	public int SaveDelayAlarmData(int deviceId,String deviceType,String acqTime,String alarmTime,AlarmInfo alarmInfo){
		Connection conn = null;
	    CallableStatement cs= null;
	    int r=0;
	    try{
			conn=OracleJdbcUtis.getConnection();
			if(conn!=null){
				cs = conn.prepareCall("{call prd_save_alarminfo(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				cs.setInt(1, deviceId);
				cs.setString(2, deviceType);
				cs.setString(3, acqTime);
				cs.setString(4, alarmTime);
				cs.setString(5, alarmInfo.getTitle());
				cs.setInt(6, alarmInfo.getAlarmType());
				if(StringManagerUtils.isNum(alarmInfo.getRawValue())){
					cs.setString(7, alarmInfo.getRawValue());
				}else{
					cs.setString(7, null);
				}
				cs.setString(8, alarmInfo.getAlarmInfo());
				if(StringManagerUtils.isNum(alarmInfo.getAlarmLimit())){
					cs.setString(9, alarmInfo.getAlarmLimit());
				}else{
					cs.setString(9, null);
				}
				if(StringManagerUtils.isNum(alarmInfo.getHystersis())){
					cs.setString(10, alarmInfo.getHystersis());
				}else{
					cs.setString(10, null);
				}
				cs.setInt(11, alarmInfo.getAlarmLevel());
				if(StringManagerUtils.isNum(alarmInfo.getDelay())){
					cs.setString(12, alarmInfo.getDelay());
				}else{
					cs.setString(12, null);
				}
				
				if(StringManagerUtils.isNum(alarmInfo.getRetriggerTime())){
					cs.setString(13, alarmInfo.getRetriggerTime());
				}else{
					cs.setString(13, null);
				}
				
				cs.setInt(14, alarmInfo.getIsSendMessage());
				cs.setInt(15, alarmInfo.getIsSendMail());
				
				cs.setString(16, alarmInfo.getColumn());
				cs.setString(17, alarmInfo.getBitIndex());
				r=cs.executeUpdate();
				if(cs!=null){
					cs.close();
				}
				if(conn!=null){
					conn.close();
				}
			}
		}catch(Exception e){
			r=-1;
			e.printStackTrace();
		}finally{
			if(cs!=null){
				try {
					cs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	    return r;
	}
	
	public static class AlarmInfo{
		public String alarmStartTime;
		
		public String alarmTime;
		
		public int addr;
		
		public String column;
		
		public String title;
		
		public String rawTitle;
		
		public String value;
		
		public String rawValue="";
		
		public String dataType;
		
		public String unit;
		
		public int alarmLevel;
		
		public String resolutionMode="";
		
		public String bitIndex="";
		
		public String alarmLimit;
		
		public String hystersis;
		
		public String alarmInfo;
		
		public int alarmType;
		
		public String delay;
		
		public String retriggerTime;
		
		public int isSendMessage;
		
		public int isSendMail;
		
		public boolean triggerAlarm;
		
		public Integer alertId;
		
		public String ackUser; // ★ 新增：确认人
		public String ackIp; // ★ 新增：确认IP
        public String ackTime; // ★ 新增：确认时间
        
        private int suppressStatus = 0;
        private String suppressReason;
        private String suppressUser;
        private String suppressTime;
        private String suppressIp;
        private String suppressEndTime;
        
        private boolean pendingDelay = false;
		
		public String getAlarmTime() {
			return alarmTime;
		}
		public void setAlarmTime(String alarmTime) {
			this.alarmTime = alarmTime;
		}
		public String getColumn() {
			return column;
		}
		public void setColumn(String column) {
			this.column = column;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getRawTitle() {
			return rawTitle;
		}
		public void setRawTitle(String rawTitle) {
			this.rawTitle = rawTitle;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getRawValue() {
			return rawValue;
		}
		public void setRawValue(String rawValue) {
			this.rawValue = rawValue;
		}
		public String getDataType() {
			return dataType;
		}
		public void setDataType(String dataType) {
			this.dataType = dataType;
		}
		public String getUnit() {
			return unit;
		}
		public void setUnit(String unit) {
			this.unit = unit;
		}
		public int getAlarmLevel() {
			return alarmLevel;
		}
		public void setAlarmLevel(int alarmLevel) {
			this.alarmLevel = alarmLevel;
		}
		public String getResolutionMode() {
			return resolutionMode;
		}
		public void setResolutionMode(String resolutionMode) {
			this.resolutionMode = resolutionMode;
		}
		public String getBitIndex() {
			return bitIndex;
		}
		public void setBitIndex(String bitIndex) {
			this.bitIndex = bitIndex;
		}
		public String getAlarmLimit() {
			return alarmLimit;
		}
		public void setAlarmLimit(String alarmLimit) {
			this.alarmLimit = alarmLimit;
		}
		public String getHystersis() {
			return hystersis;
		}
		public void setHystersis(String hystersis) {
			this.hystersis = hystersis;
		}
		public String getAlarmInfo() {
			return alarmInfo;
		}
		public void setAlarmInfo(String alarmInfo) {
			this.alarmInfo = alarmInfo;
		}
		public int getAlarmType() {
			return alarmType;
		}
		public void setAlarmType(int alarmType) {
			this.alarmType = alarmType;
		}
		public String getDelay() {
			return delay;
		}
		public void setDelay(String delay) {
			this.delay = delay;
		}
		public String getRetriggerTime() {
			return retriggerTime;
		}
		public void setRetriggerTime(String retriggerTime) {
			this.retriggerTime = retriggerTime;
		}
		public int getIsSendMessage() {
			return isSendMessage;
		}
		public void setIsSendMessage(int isSendMessage) {
			this.isSendMessage = isSendMessage;
		}
		public int getIsSendMail() {
			return isSendMail;
		}
		public void setIsSendMail(int isSendMail) {
			this.isSendMail = isSendMail;
		}
		public int getAddr() {
			return addr;
		}
		public void setAddr(int addr) {
			this.addr = addr;
		}
		public String getAlarmStartTime() {
			return alarmStartTime;
		}
		public void setAlarmStartTime(String alarmStartTime) {
			this.alarmStartTime = alarmStartTime;
		}
		public boolean getTriggerAlarm() {
			return triggerAlarm;
		}
		public void setTriggerAlarm(boolean triggerAlarm) {
			this.triggerAlarm = triggerAlarm;
		}
		public Integer getAlertId() {
			return alertId;
		}
		public void setAlertId(Integer alertId) {
			this.alertId = alertId;
		}
		public String getAckUser() {
			return ackUser;
		}
		public void setAckUser(String ackUser) {
			this.ackUser = ackUser;
		}
		public String getAckTime() {
			return ackTime;
		}
		public void setAckTime(String ackTime) {
			this.ackTime = ackTime;
		}
		public String getAckIp() {
			return ackIp;
		}
		public void setAckIp(String ackIp) {
			this.ackIp = ackIp;
		}
		public int getSuppressStatus() {
			return suppressStatus;
		}
		public void setSuppressStatus(int suppressStatus) {
			this.suppressStatus = suppressStatus;
		}
		public String getSuppressReason() {
			return suppressReason;
		}
		public void setSuppressReason(String suppressReason) {
			this.suppressReason = suppressReason;
		}
		public String getSuppressUser() {
			return suppressUser;
		}
		public void setSuppressUser(String suppressUser) {
			this.suppressUser = suppressUser;
		}
		public String getSuppressTime() {
			return suppressTime;
		}
		public void setSuppressTime(String suppressTime) {
			this.suppressTime = suppressTime;
		}
		public String getSuppressIp() {
			return suppressIp;
		}
		public void setSuppressIp(String suppressIp) {
			this.suppressIp = suppressIp;
		}
		public String getSuppressEndTime() {
			return suppressEndTime;
		}
		public void setSuppressEndTime(String suppressEndTime) {
			this.suppressEndTime = suppressEndTime;
		}
		public boolean getPendingDelay() {
			return pendingDelay;
		}
		public void setPendingDelay(boolean pendingDelay) {
			this.pendingDelay = pendingDelay;
		}
		
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public Map<String, AlarmInfo> getAlarmInfoMap() {
		return alarmInfoMap;
	}

	public void setAlarmInfoMap(Map<String, AlarmInfo> alarmInfoMap) {
		this.alarmInfoMap = alarmInfoMap;
	}

	public Map<String, ScheduledExecutorService> getAlarmInfoTimerMap() {
		return alarmInfoTimerMap;
	}

	public void setAlarmInfoTimerMap(Map<String, ScheduledExecutorService> alarmInfoTimerMap) {
		this.alarmInfoTimerMap = alarmInfoTimerMap;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceTypeName_zh_CN() {
		return deviceTypeName_zh_CN;
	}

	public void setDeviceTypeName_zh_CN(String deviceTypeName_zh_CN) {
		this.deviceTypeName_zh_CN = deviceTypeName_zh_CN;
	}

	public String getDeviceTypeName_en() {
		return deviceTypeName_en;
	}

	public void setDeviceTypeName_en(String deviceTypeName_en) {
		this.deviceTypeName_en = deviceTypeName_en;
	}

	public String getDeviceTypeName_ru() {
		return deviceTypeName_ru;
	}

	public void setDeviceTypeName_ru(String deviceTypeName_ru) {
		this.deviceTypeName_ru = deviceTypeName_ru;
	}
}
