package com.cosog.model.calculate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DeviceInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer orgId;
	private String orgName;
	private String deviceName;
	private Integer deviceType;
	private String deviceTypeName_zh_CN;
	private String deviceTypeName_en;
	private String deviceTypeName_ru;
	private Integer calculateType;
	private Integer applicationScenarios;
	private String applicationScenariosName;
	private String instanceCode;
	private String instanceName;
	private String displayInstanceCode;
	private String displayInstanceName;
	private String alarmInstanceCode;
	private String alarmInstanceName;
	private String tcpType;
	private String signInId;
	private String ipPort;
	private String slave;
	private Integer PeakDelay;
	private String videoUrl1;
	private String videoUrl2;
	private Integer videoKey1;
	private Integer videoKey2;
	
	private Integer status;
	private String statusName;
	private Integer sortNum;


	private String acqTime;
	
	private String saveTime;
	
	private Integer commStatus;
	
	private float commTime;
	
	private float commEff;
	
	private String commRange;
	
	private String onLineAcqTime;
	
	private Integer onLineCommStatus;
	
	private float onLineCommTime;
	
	private float onLineCommEff;
	
	private String onLineCommRange;
	
	private String runStatusAcqTime;
	
	private Integer runStatus;
	
	private float runTime;
	
	private float runEff;
	
	private String runRange;
	
	private RPCCalculateRequestData rpcCalculateRequestData;
	
	private PCPCalculateRequestData pcpCalculateRequestData;
	
	private Map<String,DailyTotalItem> dailyTotalItemMap;
	
	private Integer resultCode;
	
	private Integer resultStatus;
	
	public static class DailyTotalItem implements Serializable {
		private static final long serialVersionUID = 1L;
		private String itemColumn;
		private String itemName;
		private String acqTime;
		private float totalValue;
		private float todayValue;
		public String getItemColumn() {
			return itemColumn;
		}
		public void setItemColumn(String itemColumn) {
			this.itemColumn = itemColumn;
		}
		public String getAcqTime() {
			return acqTime;
		}
		public void setAcqTime(String acqTime) {
			this.acqTime = acqTime;
		}
		public float getTotalValue() {
			return totalValue;
		}
		public void setTotalValue(float totalValue) {
			this.totalValue = totalValue;
		}
		public float getTodayValue() {
			return todayValue;
		}
		public void setTodayValue(float todayValue) {
			this.todayValue = todayValue;
		}
		public String getItemName() {
			return itemName;
		}
		public void setItemName(String itemName) {
			this.itemName = itemName;
		}
	}

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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

	public Integer getCommStatus() {
		return commStatus;
	}

	public void setCommStatus(Integer commStatus) {
		this.commStatus = commStatus;
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

	public String getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}

//	public List<RPCCalculateResponseData> getRPCCalculateList() {
//		if(RPCCalculateList==null){
//			this.setRPCCalculateList(new ArrayList<RPCCalculateResponseData>());
//		}
//		return this.RPCCalculateList;
//	}
//
//	public void setRPCCalculateList(List<RPCCalculateResponseData> rPCCalculateList) {
//		RPCCalculateList = rPCCalculateList;
//	}

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

	public Integer getOnLineCommStatus() {
		return onLineCommStatus;
	}

	public void setOnLineCommStatus(Integer onLineCommStatus) {
		this.onLineCommStatus = onLineCommStatus;
	}

	public float getOnLineCommTime() {
		return onLineCommTime;
	}

	public void setOnLineCommTime(float onLineCommTime) {
		this.onLineCommTime = onLineCommTime;
	}

	public float getOnLineCommEff() {
		return onLineCommEff;
	}

	public void setOnLineCommEff(float onLineCommEff) {
		this.onLineCommEff = onLineCommEff;
	}

	public String getOnLineCommRange() {
		return onLineCommRange;
	}

	public void setOnLineCommRange(String onLineCommRange) {
		this.onLineCommRange = onLineCommRange;
	}

	public String getOnLineAcqTime() {
		return onLineAcqTime;
	}

	public void setOnLineAcqTime(String onLineAcqTime) {
		this.onLineAcqTime = onLineAcqTime;
	}

	public String getTcpType() {
		return tcpType;
	}

	public void setTcpType(String tcpType) {
		this.tcpType = tcpType;
	}

	public Integer getPeakDelay() {
		return PeakDelay;
	}

	public void setPeakDelay(Integer peakDelay) {
		PeakDelay = peakDelay;
	}

	public String getIpPort() {
		return ipPort;
	}

	public void setIpPort(String ipPort) {
		this.ipPort = ipPort;
	}

	public String getRunStatusAcqTime() {
		return runStatusAcqTime;
	}

	public void setRunStatusAcqTime(String runStatusAcqTime) {
		this.runStatusAcqTime = runStatusAcqTime;
	}

	public String getVideoUrl1() {
		return videoUrl1;
	}

	public void setVideoUrl1(String videoUrl1) {
		this.videoUrl1 = videoUrl1;
	}

	public String getVideoUrl2() {
		return videoUrl2;
	}

	public void setVideoUrl2(String videoUrl2) {
		this.videoUrl2 = videoUrl2;
	}

	public Integer getVideoKey1() {
		return videoKey1;
	}

	public void setVideoKey1(Integer videoKey1) {
		this.videoKey1 = videoKey1;
	}

	public Integer getVideoKey2() {
		return videoKey2;
	}

	public void setVideoKey2(Integer videoKey2) {
		this.videoKey2 = videoKey2;
	}

	public Integer getCalculateType() {
		return calculateType;
	}

	public void setCalculateType(Integer calculateType) {
		this.calculateType = calculateType;
	}

	public RPCCalculateRequestData getRpcCalculateRequestData() {
		return rpcCalculateRequestData;
	}

	public void setRpcCalculateRequestData(RPCCalculateRequestData rpcCalculateRequestData) {
		this.rpcCalculateRequestData = rpcCalculateRequestData;
	}

	public PCPCalculateRequestData getPcpCalculateRequestData() {
		return pcpCalculateRequestData;
	}

	public void setPcpCalculateRequestData(PCPCalculateRequestData pcpCalculateRequestData) {
		this.pcpCalculateRequestData = pcpCalculateRequestData;
	}

	public Map<String, DailyTotalItem> getDailyTotalItemMap() {
		return dailyTotalItemMap;
	}

	public void setDailyTotalItemMap(Map<String, DailyTotalItem> dailyTotalItemMap) {
		this.dailyTotalItemMap = dailyTotalItemMap;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceTypeName_zh_CN() {
		return deviceTypeName_zh_CN;
	}

	public void setDeviceTypeName_zh_CN(String deviceTypeName_zh_CN) {
		this.deviceTypeName_zh_CN = deviceTypeName_zh_CN;
	}

	public String getDeviceTypeName_en() {
		return deviceTypeName_en;
	}

	public void setDeviceTypeName_en(String deviceTypeName_en) {
		this.deviceTypeName_en = deviceTypeName_en;
	}

	public String getDeviceTypeName_ru() {
		return deviceTypeName_ru;
	}

	public void setDeviceTypeName_ru(String deviceTypeName_ru) {
		this.deviceTypeName_ru = deviceTypeName_ru;
	}

}
