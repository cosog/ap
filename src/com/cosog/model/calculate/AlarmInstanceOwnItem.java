package com.cosog.model.calculate;

import java.io.Serializable;
import java.util.List;

public class AlarmInstanceOwnItem implements Serializable {
	private static final long serialVersionUID = 1L;

	public String instanceCode;
	public String protocol;
	public int unitId;
	public List<AlarmItem> itemList;
	
	public static class AlarmItem implements Serializable {
		private static final long serialVersionUID = 1L;
		public int itemId;
		public String itemName;
		public String itemCode;
		public int itemAddr;
		public int unitId;
		public int bitIndex;
		
		public float value;
		public float upperLimit;
		public float lowerLimit;
		public float hystersis;
		public int delay;
		
		public int alarmLevel;
		public int alarmSign;
		public int type;
		
		public int isSendMessage;
		public int isSendMail;
		public int getItemId() {
			return itemId;
		}
		public void setItemId(int itemId) {
			this.itemId = itemId;
		}
		public String getItemName() {
			return itemName;
		}
		public void setItemName(String itemName) {
			this.itemName = itemName;
		}
		public String getItemCode() {
			return itemCode;
		}
		public void setItemCode(String itemCode) {
			this.itemCode = itemCode;
		}
		public int getItemAddr() {
			return itemAddr;
		}
		public void setItemAddr(int itemAddr) {
			this.itemAddr = itemAddr;
		}
		public int getUnitId() {
			return unitId;
		}
		public void setUnitId(int unitId) {
			this.unitId = unitId;
		}
		public int getBitIndex() {
			return bitIndex;
		}
		public void setBitIndex(int bitIndex) {
			this.bitIndex = bitIndex;
		}
		public float getValue() {
			return value;
		}
		public void setValue(float value) {
			this.value = value;
		}
		public float getUpperLimit() {
			return upperLimit;
		}
		public void setUpperLimit(float upperLimit) {
			this.upperLimit = upperLimit;
		}
		public float getLowerLimit() {
			return lowerLimit;
		}
		public void setLowerLimit(float lowerLimit) {
			this.lowerLimit = lowerLimit;
		}
		public float getHystersis() {
			return hystersis;
		}
		public void setHystersis(float hystersis) {
			this.hystersis = hystersis;
		}
		public int getDelay() {
			return delay;
		}
		public void setDelay(int delay) {
			this.delay = delay;
		}
		public int getAlarmLevel() {
			return alarmLevel;
		}
		public void setAlarmLevel(int alarmLevel) {
			this.alarmLevel = alarmLevel;
		}
		public int getAlarmSign() {
			return alarmSign;
		}
		public void setAlarmSign(int alarmSign) {
			this.alarmSign = alarmSign;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
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
	}
	
	public String getInstanceCode() {
		return instanceCode;
	}
	public void setInstanceCode(String instanceCode) {
		this.instanceCode = instanceCode;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public int getUnitId() {
		return unitId;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	public List<AlarmItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<AlarmItem> itemList) {
		this.itemList = itemList;
	}
}
