package com.cosog.model.drive;

import java.util.List;

public class ExportAlarmUnitData {
	
	private int Id;

    private String UnitCode;

    private String UnitName;

    private String Protocol;

    private String Remark;
    
    private int CalculateType;

    private List<AlarmItem> ItemList;

    public void setId(int Id){
        this.Id = Id;
    }
    public int getId(){
        return this.Id;
    }
    public void setUnitCode(String UnitCode){
        this.UnitCode = UnitCode;
    }
    public String getUnitCode(){
        return this.UnitCode;
    }
    public void setUnitName(String UnitName){
        this.UnitName = UnitName;
    }
    public String getUnitName(){
        return this.UnitName;
    }
    public void setProtocol(String Protocol){
        this.Protocol = Protocol;
    }
    public String getProtocol(){
        return this.Protocol;
    }
    public void setRemark(String Remark){
        this.Remark = Remark;
    }
    public String getRemark(){
        return this.Remark;
    }
    public void setItemList(List<AlarmItem> ItemList){
        this.ItemList = ItemList;
    }
    public List<AlarmItem> getItemList(){
        return this.ItemList;
    }

	public static class AlarmItem
	{
	    private int Id;

	    private String ItemId;

	    private String ItemName;

	    private String ItemCode;

	    private String ItemAddr;

	    private String Value;

	    private String UpperLimit;

	    private String LowerLimit;

	    private String Hystersis;

	    private String Delay;
	    
	    private String RetriggerTime;

	    private int AlarmLevel;

	    private int AlarmSign;

	    private int Type;

	    private String BitIndex;

	    private int SendMessage;

	    private int SendMail;

	    public void setId(int Id){
	        this.Id = Id;
	    }
	    public int getId(){
	        return this.Id;
	    }
	    public void setItemId(String ItemId){
	        this.ItemId = ItemId;
	    }
	    public String getItemId(){
	        return this.ItemId;
	    }
	    public void setItemName(String ItemName){
	        this.ItemName = ItemName;
	    }
	    public String getItemName(){
	        return this.ItemName;
	    }
	    public void setItemCode(String ItemCode){
	        this.ItemCode = ItemCode;
	    }
	    public String getItemCode(){
	        return this.ItemCode;
	    }
	    public void setItemAddr(String ItemAddr){
	        this.ItemAddr = ItemAddr;
	    }
	    public String getItemAddr(){
	        return this.ItemAddr;
	    }
	    public void setValue(String Value){
	        this.Value = Value;
	    }
	    public String getValue(){
	        return this.Value;
	    }
	    public void setUpperLimit(String UpperLimit){
	        this.UpperLimit = UpperLimit;
	    }
	    public String getUpperLimit(){
	        return this.UpperLimit;
	    }
	    public void setLowerLimit(String LowerLimit){
	        this.LowerLimit = LowerLimit;
	    }
	    public String getLowerLimit(){
	        return this.LowerLimit;
	    }
	    public void setHystersis(String Hystersis){
	        this.Hystersis = Hystersis;
	    }
	    public String getHystersis(){
	        return this.Hystersis;
	    }
	    public void setDelay(String Delay){
	        this.Delay = Delay;
	    }
	    public String getDelay(){
	        return this.Delay;
	    }
	    public void setAlarmLevel(int AlarmLevel){
	        this.AlarmLevel = AlarmLevel;
	    }
	    public int getAlarmLevel(){
	        return this.AlarmLevel;
	    }
	    public void setAlarmSign(int AlarmSign){
	        this.AlarmSign = AlarmSign;
	    }
	    public int getAlarmSign(){
	        return this.AlarmSign;
	    }
	    public void setType(int Type){
	        this.Type = Type;
	    }
	    public int getType(){
	        return this.Type;
	    }
	    public void setBitIndex(String BitIndex){
	        this.BitIndex = BitIndex;
	    }
	    public String getBitIndex(){
	        return this.BitIndex;
	    }
	    public void setSendMessage(int SendMessage){
	        this.SendMessage = SendMessage;
	    }
	    public int getSendMessage(){
	        return this.SendMessage;
	    }
	    public void setSendMail(int SendMail){
	        this.SendMail = SendMail;
	    }
	    public int getSendMail(){
	        return this.SendMail;
	    }
		public String getRetriggerTime() {
			return RetriggerTime;
		}
		public void setRetriggerTime(String retriggerTime) {
			RetriggerTime = retriggerTime;
		}
	}

	public int getCalculateType() {
		return CalculateType;
	}
	public void setCalculateType(int calculateType) {
		CalculateType = calculateType;
	}
}
