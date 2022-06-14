package com.cosog.model.calculate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cosog.model.calculate.RPCCalculateResponseData;
import com.cosog.model.drive.AcquisitionItemInfo;
import com.cosog.model.calculate.RPCCalculateRequestData.*;

public class RPCDeviceInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer orgId;
	private String orgName;
	private String wellName;
	private Integer deviceType;
	private String deviceTypeName;
	private Integer applicationScenarios;
	private String applicationScenariosName;
	private String instanceCode;
	private String instanceName;
	private String displayInstanceCode;
	private String displayInstanceName;
	private String alarmInstanceCode;
	private String alarmInstanceName;
	private String signInId;
	private String slave;
	private String videoUrl;
	private Integer status;
	private String statusName;
	private Integer pumpingModelId;
	private Integer sortNum;

    private FluidPVT FluidPVT;

    private Reservoir Reservoir;

    private RodString RodString;

    private TubingString TubingString;

    private Pump Pump;

    private CasingString CasingString;

    private PumpingUnit PumpingUnit;

    private Production Production;

    private ManualIntervention ManualIntervention;

	private String acqTime;
	
	private String saveTime;
	
	private List<AcquisitionItemInfo> acquisitionItemInfoList;
	
	private Integer commStatus;
	
	private float commTime;
	
	private float commEff;
	
	private String commRange;
	
	private Integer runStatus;
	
	private float runTime;
	
	private float runEff;
	
	private String runRange;
	
	private float totalKWattH;
	
	private float todayKWattH;
	
	private Integer resultCode;
	
	private Integer resultStatus;
	
	private List<RPCCalculateResponseData> RPCCalculateList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getWellName() {
		return wellName;
	}

	public void setWellName(String wellName) {
		this.wellName = wellName;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public Integer getApplicationScenarios() {
		return applicationScenarios;
	}

	public void setApplicationScenarios(Integer applicationScenarios) {
		this.applicationScenarios = applicationScenarios;
	}

	public String getInstanceCode() {
		return instanceCode;
	}

	public void setInstanceCode(String instanceCode) {
		this.instanceCode = instanceCode;
	}

	public String getDisplayInstanceCode() {
		return displayInstanceCode;
	}

	public void setDisplayInstanceCode(String displayInstanceCode) {
		this.displayInstanceCode = displayInstanceCode;
	}

	public String getAlarmInstanceCode() {
		return alarmInstanceCode;
	}

	public void setAlarmInstanceCode(String alarmInstanceCode) {
		this.alarmInstanceCode = alarmInstanceCode;
	}

	public String getSignInId() {
		return signInId;
	}

	public void setSignInId(String signInId) {
		this.signInId = signInId;
	}

	public String getSlave() {
		return slave;
	}

	public void setSlave(String slave) {
		this.slave = slave;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public FluidPVT getFluidPVT() {
		return FluidPVT;
	}

	public void setFluidPVT(FluidPVT fluidPVT) {
		FluidPVT = fluidPVT;
	}

	public Reservoir getReservoir() {
		return Reservoir;
	}

	public void setReservoir(Reservoir reservoir) {
		Reservoir = reservoir;
	}

	public RodString getRodString() {
		return RodString;
	}

	public void setRodString(RodString rodString) {
		RodString = rodString;
	}

	public TubingString getTubingString() {
		return TubingString;
	}

	public void setTubingString(TubingString tubingString) {
		TubingString = tubingString;
	}

	public Pump getPump() {
		return Pump;
	}

	public void setPump(Pump pump) {
		Pump = pump;
	}

	public CasingString getCasingString() {
		return CasingString;
	}

	public void setCasingString(CasingString casingString) {
		CasingString = casingString;
	}

	public PumpingUnit getPumpingUnit() {
		return PumpingUnit;
	}

	public void setPumpingUnit(PumpingUnit pumpingUnit) {
		PumpingUnit = pumpingUnit;
	}

	public Production getProduction() {
		return Production;
	}

	public void setProduction(Production production) {
		Production = production;
	}

	public ManualIntervention getManualIntervention() {
		return ManualIntervention;
	}

	public void setManualIntervention(ManualIntervention manualIntervention) {
		ManualIntervention = manualIntervention;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public String getApplicationScenariosName() {
		return applicationScenariosName;
	}

	public void setApplicationScenariosName(String applicationScenariosName) {
		this.applicationScenariosName = applicationScenariosName;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getDisplayInstanceName() {
		return displayInstanceName;
	}

	public void setDisplayInstanceName(String displayInstanceName) {
		this.displayInstanceName = displayInstanceName;
	}

	public String getAlarmInstanceName() {
		return alarmInstanceName;
	}

	public void setAlarmInstanceName(String alarmInstanceName) {
		this.alarmInstanceName = alarmInstanceName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getAcqTime() {
		return acqTime;
	}

	public void setAcqTime(String acqTime) {
		this.acqTime = acqTime;
	}

	public List<AcquisitionItemInfo> getAcquisitionItemInfoList() {
		return acquisitionItemInfoList;
	}

	public void setAcquisitionItemInfoList(List<AcquisitionItemInfo> acquisitionItemInfoList) {
		this.acquisitionItemInfoList = acquisitionItemInfoList;
	}

	public Integer getCommStatus() {
		return commStatus;
	}

	public void setCommStatus(Integer commStatus) {
		this.commStatus = commStatus;
	}

	public Integer getPumpingModelId() {
		return pumpingModelId;
	}

	public void setPumpingModelId(Integer pumpingModelId) {
		this.pumpingModelId = pumpingModelId;
	}

	public float getCommTime() {
		return commTime;
	}

	public void setCommTime(float commTime) {
		this.commTime = commTime;
	}

	public float getCommEff() {
		return commEff;
	}

	public void setCommEff(float commEff) {
		this.commEff = commEff;
	}

	public String getCommRange() {
		return commRange;
	}

	public void setCommRange(String commRange) {
		this.commRange = commRange;
	}

	public Integer getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(Integer runStatus) {
		this.runStatus = runStatus;
	}

	public float getRunTime() {
		return runTime;
	}

	public void setRunTime(float runTime) {
		this.runTime = runTime;
	}

	public float getRunEff() {
		return runEff;
	}

	public void setRunEff(float runEff) {
		this.runEff = runEff;
	}

	public String getRunRange() {
		return runRange;
	}

	public void setRunRange(String runRange) {
		this.runRange = runRange;
	}

	public float getTotalKWattH() {
		return totalKWattH;
	}

	public void setTotalKWattH(float totalKWattH) {
		this.totalKWattH = totalKWattH;
	}

	public float getTodayKWattH() {
		return todayKWattH;
	}

	public void setTodayKWattH(float todayKWattH) {
		this.todayKWattH = todayKWattH;
	}

	public String getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}

	public List<RPCCalculateResponseData> getRPCCalculateList() {
		if(RPCCalculateList==null){
			this.setRPCCalculateList(new ArrayList<RPCCalculateResponseData>());
		}
		return this.RPCCalculateList;
	}

	public void setRPCCalculateList(List<RPCCalculateResponseData> rPCCalculateList) {
		RPCCalculateList = rPCCalculateList;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public Integer getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(Integer resultStatus) {
		this.resultStatus = resultStatus;
	}

}
