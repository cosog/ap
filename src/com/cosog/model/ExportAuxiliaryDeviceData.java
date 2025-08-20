package com.cosog.model;

import java.util.List;

public class ExportAuxiliaryDeviceData {

	private int Id;

    private int DeviceType;

    private String DeviceTypeName;

    private String DeviceName;

    private String Manufacturer;

    private String Model;

    private String Remark;

    private int Sort;

    private int SpecificType;

    private PRTF PRTF;

    private List<AdditionalInformation> AdditionalInformationList;
    
    private int saveSign=0;
	
	private String msg="";
	
	private int saveId=0;

    public void setId(int Id){
        this.Id = Id;
    }
    public int getId(){
        return this.Id;
    }
    public void setDeviceType(int DeviceType){
        this.DeviceType = DeviceType;
    }
    public int getDeviceType(){
        return this.DeviceType;
    }
    public void setDeviceTypeName(String DeviceTypeName){
        this.DeviceTypeName = DeviceTypeName;
    }
    public String getDeviceTypeName(){
        return this.DeviceTypeName;
    }
    public void setDeviceName(String DeviceName){
        this.DeviceName = DeviceName;
    }
    public String getDeviceName(){
        return this.DeviceName;
    }
    public void setManufacturer(String Manufacturer){
        this.Manufacturer = Manufacturer;
    }
    public String getManufacturer(){
        return this.Manufacturer;
    }
    public void setModel(String Model){
        this.Model = Model;
    }
    public String getModel(){
        return this.Model;
    }
    public void setRemark(String Remark){
        this.Remark = Remark;
    }
    public String getRemark(){
        return this.Remark;
    }
    public void setSort(int Sort){
        this.Sort = Sort;
    }
    public int getSort(){
        return this.Sort;
    }
    public void setSpecificType(int SpecificType){
        this.SpecificType = SpecificType;
    }
    public int getSpecificType(){
        return this.SpecificType;
    }
    public void setPRTF(PRTF PRTF){
        this.PRTF = PRTF;
    }
    public PRTF getPRTF(){
        return this.PRTF;
    }
    public void setAdditionalInformationList(List<AdditionalInformation> AdditionalInformationList){
        this.AdditionalInformationList = AdditionalInformationList;
    }
    public List<AdditionalInformation> getAdditionalInformationList(){
        return this.AdditionalInformationList;
    }
	
	public static class SinglePRTF
	{
	    private double CrankAngle;

	    private double PR;

	    private double TF;

	    public void setCrankAngle(double CrankAngle){
	        this.CrankAngle = CrankAngle;
	    }
	    public double getCrankAngle(){
	        return this.CrankAngle;
	    }
	    public void setPR(double PR){
	        this.PR = PR;
	    }
	    public double getPR(){
	        return this.PR;
	    }
	    public void setTF(double TF){
	        this.TF = TF;
	    }
	    public double getTF(){
	        return this.TF;
	    }
	}

	public static class StrokePRTF
	{
	    private Double Stroke;

	    private List<SinglePRTF> PRTF;

	    public void setStroke(Double Stroke){
	        this.Stroke = Stroke;
	    }
	    public Double getStroke(){
	        return this.Stroke;
	    }
	    public void setPRTF(List<SinglePRTF> PRTF){
	        this.PRTF = PRTF;
	    }
	    public List<SinglePRTF> getPRTF(){
	        return this.PRTF;
	    }
	}
	
	public static class PRTF
	{
	    private List<StrokePRTF> List;

	    public void setList(List<StrokePRTF> List){
	        this.List = List;
	    }
	    public List<StrokePRTF> getList(){
	        return this.List;
	    }
	}
	
	public static class AdditionalInformation
	{
	    private String ItemName;

	    private String ItemCode;

	    private String ItemValue;

	    private String ItemUnit;

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
	    public void setItemValue(String ItemValue){
	        this.ItemValue = ItemValue;
	    }
	    public String getItemValue(){
	        return this.ItemValue;
	    }
	    public void setItemUnit(String ItemUnit){
	        this.ItemUnit = ItemUnit;
	    }
	    public String getItemUnit(){
	        return this.ItemUnit;
	    }
	}

	public int getSaveSign() {
		return saveSign;
	}
	public void setSaveSign(int saveSign) {
		this.saveSign = saveSign;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getSaveId() {
		return saveId;
	}
	public void setSaveId(int saveId) {
		this.saveId = saveId;
	}
}
