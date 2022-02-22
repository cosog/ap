package com.cosog.model;

public class CommStatus {

	public String deviceName;
	
	public int deviceType;
	
	public int commStatus;
	
	public int orgId;
	
	public int deviceStatus;

	public CommStatus() {
		super();
	}

	public CommStatus(String deviceName, int deviceType, int commStatus, int orgId,int deviceStatus) {
		super();
		this.deviceName = deviceName;
		this.deviceType = deviceType;
		this.commStatus = commStatus;
		this.orgId = orgId;
		this.deviceStatus = deviceStatus;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	public int getCommStatus() {
		return commStatus;
	}

	public void setCommStatus(int commStatus) {
		this.commStatus = commStatus;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public int getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(int deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
}
