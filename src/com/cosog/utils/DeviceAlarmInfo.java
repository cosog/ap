package com.cosog.utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

public class DeviceAlarmInfo {
	public int deviceId;
	
	public Map<String,AlarmInfo> alarmInfoMap;
	
	public Map<String,ScheduledExecutorService> alarmInfoTimerMap;
	
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
}
