package com.cosog.model.drive;

import java.util.List;

public class ExportAcqUnitData {

	private int Id;

    private String UnitCode;

    private String UnitName;

    private String ProtocolCode;
    
    private String ProtocolName;
    
    private String ProtocolDeviceType;

    private String Remark;

    private List<AcqGroup> GroupList;

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
    public void setRemark(String Remark){
        this.Remark = Remark;
    }
    public String getRemark(){
        return this.Remark;
    }
    public void setGroupList(List<AcqGroup> GroupList){
        this.GroupList = GroupList;
    }
    public List<AcqGroup> getGroupList(){
        return this.GroupList;
    }
	
	public static class AcqItem
	{
	    private int Id;

	    private int ItemId;

	    private String ItemName;

	    private String ItemCode;

	    private int BitIndex;

	    private String Matrix;

	    private int DailyTotalCalculate;

	    private String DailyTotalCalculateName;

	    public void setId(int Id){
	        this.Id = Id;
	    }
	    public int getId(){
	        return this.Id;
	    }
	    public void setItemId(int ItemId){
	        this.ItemId = ItemId;
	    }
	    public int getItemId(){
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
	    public void setBitIndex(int BitIndex){
	        this.BitIndex = BitIndex;
	    }
	    public int getBitIndex(){
	        return this.BitIndex;
	    }
	    public void setMatrix(String Matrix){
	        this.Matrix = Matrix;
	    }
	    public String getMatrix(){
	        return this.Matrix;
	    }
	    public void setDailyTotalCalculate(int DailyTotalCalculate){
	        this.DailyTotalCalculate = DailyTotalCalculate;
	    }
	    public int getDailyTotalCalculate(){
	        return this.DailyTotalCalculate;
	    }
	    public void setDailyTotalCalculateName(String DailyTotalCalculateName){
	        this.DailyTotalCalculateName = DailyTotalCalculateName;
	    }
	    public String getDailyTotalCalculateName(){
	        return this.DailyTotalCalculateName;
	    }
	}
	
	public static class AcqGroup
	{
	    private int Id;

	    private String GroupCode;

	    private String GroupName;

	    private int GroupTimingInterval;

	    private int GroupSavingInterval;

	    private String ProtocolCode;
	    
	    private String ProtocolName;
	    
	    private String ProtocolDeviceType;

	    private int Type;

	    private String Remark;

	    private List<AcqItem> ItemList;

	    public void setId(int Id){
	        this.Id = Id;
	    }
	    public int getId(){
	        return this.Id;
	    }
	    public void setGroupCode(String GroupCode){
	        this.GroupCode = GroupCode;
	    }
	    public String getGroupCode(){
	        return this.GroupCode;
	    }
	    public void setGroupName(String GroupName){
	        this.GroupName = GroupName;
	    }
	    public String getGroupName(){
	        return this.GroupName;
	    }
	    public void setGroupTimingInterval(int GroupTimingInterval){
	        this.GroupTimingInterval = GroupTimingInterval;
	    }
	    public int getGroupTimingInterval(){
	        return this.GroupTimingInterval;
	    }
	    public void setGroupSavingInterval(int GroupSavingInterval){
	        this.GroupSavingInterval = GroupSavingInterval;
	    }
	    public int getGroupSavingInterval(){
	        return this.GroupSavingInterval;
	    }
	    public void setType(int Type){
	        this.Type = Type;
	    }
	    public int getType(){
	        return this.Type;
	    }
	    public void setRemark(String Remark){
	        this.Remark = Remark;
	    }
	    public String getRemark(){
	        return this.Remark;
	    }
	    public void setItemList(List<AcqItem> ItemList){
	        this.ItemList = ItemList;
	    }
	    public List<AcqItem> getItemList(){
	        return this.ItemList;
	    }
		public String getProtocolCode() {
			return ProtocolCode;
		}
		public void setProtocolCode(String protocolCode) {
			ProtocolCode = protocolCode;
		}
		public String getProtocolName() {
			return ProtocolName;
		}
		public void setProtocolName(String protocolName) {
			ProtocolName = protocolName;
		}
		public String getProtocolDeviceType() {
			return ProtocolDeviceType;
		}
		public void setProtocolDeviceType(String protocolDeviceType) {
			ProtocolDeviceType = protocolDeviceType;
		}
	}

	public String getProtocolCode() {
		return ProtocolCode;
	}
	public void setProtocolCode(String protocolCode) {
		ProtocolCode = protocolCode;
	}
	public String getProtocolName() {
		return ProtocolName;
	}
	public void setProtocolName(String protocolName) {
		ProtocolName = protocolName;
	}
	public String getProtocolDeviceType() {
		return ProtocolDeviceType;
	}
	public void setProtocolDeviceType(String protocolDeviceType) {
		ProtocolDeviceType = protocolDeviceType;
	}
}
