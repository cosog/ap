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
	public int deviceId;
	
	public String deviceName;
	
	public int deviceType;
	
	public String deviceTypeName;
	
	public Map<String,AlarmInfo> alarmInfoMap;
	
	public Map<String,ScheduledExecutorService> alarmInfoTimerMap;
	
	public void addTimer(String alarmKey,int delay,AlarmInfo alarmInfo){
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
		executorService.schedule(new Thread(new Runnable() {
            @Override
            public void run() {
            	String time=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
            	Map<String, String> alarmInfoMap=AlarmInfoMap.getMapObject();
            	
//            	SaveDelayAlarmData(deviceName,deviceType+"",time,alarmInfo);
            	
            	boolean isSendSMS=alarmInfo.getIsSendMail()==1;
        		boolean isSendMail=alarmInfo.getIsSendMessage()==1;
        		StringBuffer SMSContent = new StringBuffer();
        		StringBuffer EMailContent = new StringBuffer();
        		SMSContent.append(deviceTypeName+deviceName+"于"+alarmInfo.getAlarmTime()+"发生报警:");
				String alarmLevelName="";
				if(alarmInfo.getAlarmLevel()==100){
					alarmLevelName="一级报警";
				}else if(alarmInfo.getAlarmLevel()==200){
					alarmLevelName="二级报警";
				}else if(alarmInfo.getAlarmLevel()==300){
					alarmLevelName="三级报警";
				}
				String key=deviceId+","+deviceType+","+alarmInfo.getColumn()+","+alarmInfo.getAlarmInfo();
				String lastAlarmTime=alarmInfoMap.get(key);
				
				long timeDiff=StringManagerUtils.getTimeDifference(lastAlarmTime, time, "yyyy-MM-dd HH:mm:ss");
				if(timeDiff>alarmInfo.getRetriggerTime()*1000){
					SaveDelayAlarmData(deviceName,deviceType+"",time,alarmInfo);
					alarmInfoMap.put(key, time);
					if(alarmInfo.getIsSendMessage()==1){//如果该报警项发送短信
						if(alarmInfo.getAlarmType()==3){//开关量报警
							SMSContent.append(alarmInfo.getTitle()+":"+alarmInfo.getAlarmInfo()+",报警级别:"+alarmLevelName);
						}else if(alarmInfo.getAlarmType()==2){//枚举量报警
							SMSContent.append(alarmInfo.getTitle()+":"+alarmInfo.getAlarmInfo()+",报警级别:"+alarmLevelName);
						}else if(alarmInfo.getAlarmType()==1){//数值量报警
							SMSContent.append(alarmInfo.getTitle()+alarmInfo.getAlarmInfo()
									+",报警值"+alarmInfo.getValue()
									+",限值"+alarmInfo.getAlarmLimit()
									+",回差"+alarmInfo.getHystersis()
									+",报警级别:"+alarmLevelName
									+";");
						}else{
							SMSContent.append(alarmInfo.getAlarmInfo()+",报警级别:"+alarmLevelName);
						}
					}
					if(alarmInfo.getIsSendMail()==1){//如果该报警项发送邮件
						if(alarmInfo.getAlarmType()==3){//开关量报警
							EMailContent.append(alarmInfo.getTitle()+":"+alarmInfo.getAlarmInfo()+",报警级别:"+alarmLevelName);
						}else if(alarmInfo.getAlarmType()==2){//枚举量报警
							EMailContent.append(alarmInfo.getTitle()+":"+alarmInfo.getAlarmInfo()+",报警级别:"+alarmLevelName);
						}else if(alarmInfo.getAlarmType()==1){//数值量报警
							EMailContent.append(alarmInfo.getTitle()+alarmInfo.getAlarmInfo()
									+",报警值"+alarmInfo.getValue()
									+",限值"+alarmInfo.getAlarmLimit()
									+",回差"+alarmInfo.getHystersis()
									+",报警级别:"+alarmLevelName
									+";");
						}else{
							EMailContent.append(alarmInfo.getAlarmInfo()+",报警级别:"+alarmLevelName);
						}
					}
				}
				if(isSendSMS || isSendMail){
					try {
						sendAlarmSMS(deviceName,deviceType+"",deviceTypeName,isSendSMS,isSendMail,SMSContent.toString(),EMailContent.toString());
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				//执行完后退出并删除定时器
            	if(!executorService.isShutdown()){
            	   executorService.shutdown();
            	}
            	alarmInfoTimerMap.remove(alarmKey);
            }
        }), delay,TimeUnit.SECONDS);
		alarmInfoTimerMap.put(alarmKey,executorService);
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
	
	public int SaveDelayAlarmData(String deviceName,String deviceType,String acqTime,AlarmInfo alarmInfo){
		Connection conn = null;
	    CallableStatement cs= null;
	    int r=0;
	    try{
			conn=OracleJdbcUtis.getConnection();
			if(conn!=null){
				cs = conn.prepareCall("{call prd_save_alarminfo(?,?,?,?,?,?,?,?,?,?,?,?)}");
				cs.setString(1, deviceName);
				cs.setString(2, deviceType);
				cs.setString(3, acqTime);
				cs.setString(4, alarmInfo.getTitle());
				cs.setInt(5, alarmInfo.getAlarmType());
				cs.setString(6, alarmInfo.getRawValue());
				cs.setString(7, alarmInfo.getAlarmInfo());
				cs.setString(8, alarmInfo.getAlarmLimit()+"");
				cs.setString(9, alarmInfo.getHystersis()+"");
				cs.setInt(10, alarmInfo.getAlarmLevel());
				cs.setInt(11, alarmInfo.getIsSendMessage());
				cs.setInt(12, alarmInfo.getIsSendMail());
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
		
		public float alarmLimit;
		
		public float hystersis;
		
		public String alarmInfo;
		
		public int alarmType;
		
		public int delay;
		
		public int retriggerTime;
		
		public int isSendMessage;
		
		public int isSendMail;
		
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
		public float getAlarmLimit() {
			return alarmLimit;
		}
		public void setAlarmLimit(float alarmLimit) {
			this.alarmLimit = alarmLimit;
		}
		public float getHystersis() {
			return hystersis;
		}
		public void setHystersis(float hystersis) {
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
		public int getDelay() {
			return delay;
		}
		public void setDelay(int delay) {
			this.delay = delay;
		}
		public int getRetriggerTime() {
			return retriggerTime;
		}
		public void setRetriggerTime(int retriggerTime) {
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

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}
}
