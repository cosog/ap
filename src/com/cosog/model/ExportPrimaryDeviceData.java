package com.cosog.model;

import java.util.List;

import com.cosog.model.calculate.FSDiagramConstructionRequestData;
import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.SRPCalculateRequestData;
import com.cosog.model.gridmodel.GraphicSetData;

public class ExportPrimaryDeviceData {
	
	private int Id;

    private int OrgId;

    private String OrgName;

    private int DeviceType;

    private String DeviceTypeName;

    private String DeviceName;

    private int ApplicationScenarios;

    private String ApplicationScenariosName;

    private String TCPType;

    private String SignInId;

    private String IPPort;

    private String Slave;

    private String PeakDelay;

    private String InstanceCode;

    private String InstanceName;

    private String DisplayInstanceCode;

    private String DisplayInstanceName;

    private String AlarmInstanceCode;

    private String AlarmInstanceName;

    private String ReportInstanceCode;

    private String ReportInstanceName;

    private String VideoKeyId1;

    private String VideoUrl1;

    private String VideoKeyId2;

    private String VideoUrl2;

    private int Status;

    private int CalculateType;

    private String SortNum;

    private String CommissioningDate;

    private SRPCalculateRequestData SRPProductionData;

    private PCPCalculateRequestData PCPProductionData;

    private SRPCalculateRequestData.Balance BalanceInfo;

    private FSDiagramConstructionRequestData ConstructionData;

    private String Stroke;

    private List<AdditionalInformation> AdditionalInformationList;

    private List<AuxiliaryDeviceList> AuxiliaryDeviceList;
    
    private GraphicSetData GraphicSet;
    
    private int saveSign=0;
	
	private String msg="";
	
	private int saveId=0;
	
	public static class AdditionalInformation
	{
	    private String ItemName;

	    private String ItemValue;

	    private String ItemUnit;

	    public void setItemName(String ItemName){
	        this.ItemName = ItemName;
	    }
	    public String getItemName(){
	        return this.ItemName;
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
	
	public static class AuxiliaryDeviceList
	{
	    private int Id;

	    private String Name;

	    private String Manufacturer;

	    private String Model;

	    public void setId(int Id){
	        this.Id = Id;
	    }
	    public int getId(){
	        return this.Id;
	    }
	    public void setName(String Name){
	        this.Name = Name;
	    }
	    public String getName(){
	        return this.Name;
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
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getOrgId() {
		return OrgId;
	}

	public void setOrgId(int orgId) {
		OrgId = orgId;
	}

	public String getOrgName() {
		return OrgName;
	}

	public void setOrgName(String orgName) {
		OrgName = orgName;
	}

	public int getDeviceType() {
		return DeviceType;
	}

	public void setDeviceType(int deviceType) {
		DeviceType = deviceType;
	}

	public String getDeviceTypeName() {
		return DeviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		DeviceTypeName = deviceTypeName;
	}

	public String getDeviceName() {
		return DeviceName;
	}

	public void setDeviceName(String deviceName) {
		DeviceName = deviceName;
	}

	public int getApplicationScenarios() {
		return ApplicationScenarios;
	}

	public void setApplicationScenarios(int applicationScenarios) {
		ApplicationScenarios = applicationScenarios;
	}

	public String getApplicationScenariosName() {
		return ApplicationScenariosName;
	}

	public void setApplicationScenariosName(String applicationScenariosName) {
		ApplicationScenariosName = applicationScenariosName;
	}

	public String getTCPType() {
		return TCPType;
	}

	public void setTCPType(String tCPType) {
		TCPType = tCPType;
	}

	public String getSignInId() {
		return SignInId;
	}

	public void setSignInId(String signInId) {
		SignInId = signInId;
	}

	public String getIPPort() {
		return IPPort;
	}

	public void setIPPort(String iPPort) {
		IPPort = iPPort;
	}

	public String getSlave() {
		return Slave;
	}

	public void setSlave(String slave) {
		Slave = slave;
	}

	public String getPeakDelay() {
		return PeakDelay;
	}

	public void setPeakDelay(String peakDelay) {
		PeakDelay = peakDelay;
	}

	public String getInstanceCode() {
		return InstanceCode;
	}

	public void setInstanceCode(String instanceCode) {
		InstanceCode = instanceCode;
	}

	public String getInstanceName() {
		return InstanceName;
	}

	public void setInstanceName(String instanceName) {
		InstanceName = instanceName;
	}

	public String getDisplayInstanceCode() {
		return DisplayInstanceCode;
	}

	public void setDisplayInstanceCode(String displayInstanceCode) {
		DisplayInstanceCode = displayInstanceCode;
	}

	public String getDisplayInstanceName() {
		return DisplayInstanceName;
	}

	public void setDisplayInstanceName(String displayInstanceName) {
		DisplayInstanceName = displayInstanceName;
	}

	public String getAlarmInstanceCode() {
		return AlarmInstanceCode;
	}

	public void setAlarmInstanceCode(String alarmInstanceCode) {
		AlarmInstanceCode = alarmInstanceCode;
	}

	public String getAlarmInstanceName() {
		return AlarmInstanceName;
	}

	public void setAlarmInstanceName(String alarmInstanceName) {
		AlarmInstanceName = alarmInstanceName;
	}

	public String getReportInstanceCode() {
		return ReportInstanceCode;
	}

	public void setReportInstanceCode(String reportInstanceCode) {
		ReportInstanceCode = reportInstanceCode;
	}

	public String getReportInstanceName() {
		return ReportInstanceName;
	}

	public void setReportInstanceName(String reportInstanceName) {
		ReportInstanceName = reportInstanceName;
	}

	public String getVideoKeyId1() {
		return VideoKeyId1;
	}

	public void setVideoKeyId1(String videoKeyId1) {
		VideoKeyId1 = videoKeyId1;
	}

	public String getVideoUrl1() {
		return VideoUrl1;
	}

	public void setVideoUrl1(String videoUrl1) {
		VideoUrl1 = videoUrl1;
	}

	public String getVideoKeyId2() {
		return VideoKeyId2;
	}

	public void setVideoKeyId2(String videoKeyId2) {
		VideoKeyId2 = videoKeyId2;
	}

	public String getVideoUrl2() {
		return VideoUrl2;
	}

	public void setVideoUrl2(String videoUrl2) {
		VideoUrl2 = videoUrl2;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public int getCalculateType() {
		return CalculateType;
	}

	public void setCalculateType(int calculateType) {
		CalculateType = calculateType;
	}

	public String getSortNum() {
		return SortNum;
	}

	public void setSortNum(String sortNum) {
		SortNum = sortNum;
	}

	public String getCommissioningDate() {
		return CommissioningDate;
	}

	public void setCommissioningDate(String commissioningDate) {
		CommissioningDate = commissioningDate;
	}

	public SRPCalculateRequestData getSRPProductionData() {
		return SRPProductionData;
	}

	public void setSRPProductionData(SRPCalculateRequestData sRPProductionData) {
		SRPProductionData = sRPProductionData;
	}

	public PCPCalculateRequestData getPCPProductionData() {
		return PCPProductionData;
	}

	public void setPCPProductionData(PCPCalculateRequestData pCPProductionData) {
		PCPProductionData = pCPProductionData;
	}

	public SRPCalculateRequestData.Balance getBalanceInfo() {
		return BalanceInfo;
	}

	public void setBalanceInfo(SRPCalculateRequestData.Balance balanceInfo) {
		BalanceInfo = balanceInfo;
	}

	public FSDiagramConstructionRequestData getConstructionData() {
		return ConstructionData;
	}

	public void setConstructionData(FSDiagramConstructionRequestData constructionData) {
		ConstructionData = constructionData;
	}

	public String getStroke() {
		return Stroke;
	}

	public void setStroke(String stroke) {
		Stroke = stroke;
	}

	public List<AdditionalInformation> getAdditionalInformationList() {
		return AdditionalInformationList;
	}

	public void setAdditionalInformationList(List<AdditionalInformation> additionalInformationList) {
		AdditionalInformationList = additionalInformationList;
	}

	public List<AuxiliaryDeviceList> getAuxiliaryDeviceList() {
		return AuxiliaryDeviceList;
	}

	public void setAuxiliaryDeviceList(List<AuxiliaryDeviceList> auxiliaryDeviceList) {
		AuxiliaryDeviceList = auxiliaryDeviceList;
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

	public GraphicSetData getGraphicSet() {
		return GraphicSet;
	}

	public void setGraphicSet(GraphicSetData graphicSet) {
		GraphicSet = graphicSet;
	}
}
