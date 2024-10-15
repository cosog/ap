package com.cosog.model.drive;

import java.util.List;

public class ModbusProtocolAlarmUnitSaveData {

	int id;
	int resolutionMode;
	String unitCode;
	String unitName;
	String oldUnitName;
	String protocol;
	String remark;
	
	String alarmItemName;
	int alarmItemAddr;
	
	private List<String> delidslist;
	
	private List<AlarmItems> alarmItems;
	
	public static class AlarmItems
	{
	    private String itemName;
	    
	    private String itemCode;

	    private int itemAddr;

	    private String upperLimit;

	    private String lowerLimit;

	    private String hystersis;

	    private String delay;
	    
	    private String retriggerTime;

	    private String alarmLevel;

	    private String alarmSign;
	    
	    private int type;
	    
	    private String value;
	    
	    private int bitIndex;
	    
	    private String isSendMessage;
	    
	    private String isSendMail;

	    public void setItemName(String itemName){
	        this.itemName = itemName;
	    }
	    public String getItemName(){
	        return this.itemName;
	    }
	    public void setItemAddr(int itemAddr){
	        this.itemAddr = itemAddr;
	    }
	    public int getItemAddr(){
	        return this.itemAddr;
	    }
	    public void setUpperLimit(String upperLimit){
	        this.upperLimit = upperLimit;
	    }
	    public String getUpperLimit(){
	        return this.upperLimit;
	    }
	    public void setLowerLimit(String lowerLimit){
	        this.lowerLimit = lowerLimit;
	    }
	    public String getLowerLimit(){
	        return this.lowerLimit;
	    }
	    public void setHystersis(String hystersis){
	        this.hystersis = hystersis;
	    }
	    public String getHystersis(){
	        return this.hystersis;
	    }
	    public void setDelay(String delay){
	        this.delay = delay;
	    }
	    public String getDelay(){
	        return this.delay;
	    }
	    public void setAlarmLevel(String alarmLevel){
	        this.alarmLevel = alarmLevel;
	    }
	    public String getAlarmLevel(){
	        return this.alarmLevel;
	    }
	    public void setAlarmSign(String alarmSign){
	        this.alarmSign = alarmSign;
	    }
	    public String getAlarmSign(){
	        return this.alarmSign;
	    }
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public int getBitIndex() {
			return bitIndex;
		}
		public void setBitIndex(int bitIndex) {
			this.bitIndex = bitIndex;
		}
		public String getIsSendMessage() {
			return isSendMessage;
		}
		public void setIsSendMessage(String isSendMessage) {
			this.isSendMessage = isSendMessage;
		}
		public String getIsSendMail() {
			return isSendMail;
		}
		public void setIsSendMail(String isSendMail) {
			this.isSendMail = isSendMail;
		}
		public String getItemCode() {
			return itemCode;
		}
		public void setItemCode(String itemCode) {
			this.itemCode = itemCode;
		}
		public String getRetriggerTime() {
			return retriggerTime;
		}
		public void setRetriggerTime(String retriggerTime) {
			this.retriggerTime = retriggerTime;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getOldUnitName() {
		return oldUnitName;
	}

	public void setOldUnitName(String oldUnitName) {
		this.oldUnitName = oldUnitName;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<String> getDelidslist() {
		return delidslist;
	}

	public void setDelidslist(List<String> delidslist) {
		this.delidslist = delidslist;
	}

	public List<AlarmItems> getAlarmItems() {
		return alarmItems;
	}

	public void setAlarmItems(List<AlarmItems> alarmItems) {
		this.alarmItems = alarmItems;
	}

	public int getResolutionMode() {
		return resolutionMode;
	}

	public void setResolutionMode(int resolutionMode) {
		this.resolutionMode = resolutionMode;
	}

	public String getAlarmItemName() {
		return alarmItemName;
	}

	public void setAlarmItemName(String alarmItemName) {
		this.alarmItemName = alarmItemName;
	}

	public int getAlarmItemAddr() {
		return alarmItemAddr;
	}

	public void setAlarmItemAddr(int alarmItemAddr) {
		this.alarmItemAddr = alarmItemAddr;
	}
}
