package com.cosog.model.drive;

import java.io.Serializable;

import com.cosog.utils.StringManagerUtils;

public class AcquisitionItemInfo implements Comparable<AcquisitionItemInfo>,Serializable{
	private static final long serialVersionUID = 1L;
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
	
	public int sort;
	
	public String alarmLimit;
	public String hystersis;
	public String alarmInfo;
	public int alarmType;
	public String alarmDelay;
	public String retriggerTime;
	public int isSendMessage;
	public int isSendMail;
	public boolean triggerAlarm;
	
	public int type;
	public int displayItemId;
	public AcquisitionItemInfo() {
		super();
	}

	public AcquisitionItemInfo(int addr, String column, String title,String rawTitle, String value, String rawValue, String dataType,
			String unit, int alarmLevel, String resolutionMode, String bitIndex, int sort, String alarmLimit,
			String hystersis, String alarmInfo, int alarmType, String alarmDelay, String retriggerTime, int isSendMessage, int isSendMail,int type,int displayItemId) {
		super();
		this.addr = addr;
		this.column = column;
		this.title = title;
		this.rawTitle = rawTitle;
		this.value = value;
		this.rawValue = rawValue;
		this.dataType = dataType;
		this.unit = unit;
		this.alarmLevel = alarmLevel;
		this.resolutionMode = resolutionMode;
		this.bitIndex = bitIndex;
		this.sort = sort;
		this.alarmLimit = alarmLimit;
		this.hystersis = hystersis;
		this.alarmInfo = alarmInfo;
		this.alarmType = alarmType;
		this.alarmDelay = alarmDelay;
		this.retriggerTime = retriggerTime;
		this.isSendMessage = isSendMessage;
		this.isSendMail = isSendMail;
		this.type=type;
		this.displayItemId=displayItemId;
	}

	@Override
	public int compareTo(AcquisitionItemInfo acquisitionItemInfo) {//重写Comparable接口的compareTo方法   按照sort升序 addr升序 bitIndex升序
		int r=0;
		if(this.sort>acquisitionItemInfo.getSort()){
			r= 1;
		}else if(this.sort<acquisitionItemInfo.getSort()){
			r= -1;
		}else{
			if(this.displayItemId>acquisitionItemInfo.getDisplayItemId()){
				r= 1;
			}else if(this.displayItemId<acquisitionItemInfo.getDisplayItemId()){
				r= -1;
			}else{
				if(this.type>acquisitionItemInfo.getType()){
					r= 1;
				}else if(this.type<acquisitionItemInfo.getType()){
					r= -1;
				}else{
					if(this.type==0 && acquisitionItemInfo.getType()==0){
						if(this.addr>acquisitionItemInfo.getAddr()){
							r= 1;
						}else if(this.addr<acquisitionItemInfo.getAddr()){
							r= -1;
						}else{
							if(StringManagerUtils.stringToInteger(this.bitIndex)>StringManagerUtils.stringToInteger(acquisitionItemInfo.getBitIndex())){
								r= 1;
							}else{
								r= -1;
							}
						}
					}else{
						r=this.column.compareTo(acquisitionItemInfo.getColumn());
					}
				}
			}
		}
		return r;
	}



	public int getAddr() {
		return addr;
	}
	public void setAddr(int addr) {
		this.addr = addr;
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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





	public String getRawValue() {
		return rawValue;
	}





	public void setRawValue(String rawValue) {
		this.rawValue = rawValue;
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

	public String getAlarmDelay() {
		return alarmDelay;
	}

	public void setAlarmDelay(String alarmDelay) {
		this.alarmDelay = alarmDelay;
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

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getRawTitle() {
		return rawTitle;
	}

	public void setRawTitle(String rawTitle) {
		this.rawTitle = rawTitle;
	}

	public String getRetriggerTime() {
		return retriggerTime;
	}

	public void setRetriggerTime(String retriggerTime) {
		this.retriggerTime = retriggerTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean getTriggerAlarm() {
		return triggerAlarm;
	}

	public void setTriggerAlarm(boolean triggerAlarm) {
		this.triggerAlarm = triggerAlarm;
	}

	public int getDisplayItemId() {
		return displayItemId;
	}

	public void setDisplayItemId(int displayItemId) {
		this.displayItemId = displayItemId;
	}
	
}
