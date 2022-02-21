package com.cosog.model.drive;

import java.util.List;

public class InitializedDeviceInfo {
	
	public int orgId;
	
	public int deviceId;
	
	public String deviceName;
	
	public int deviceType;
	
	private String signinid;

    private byte slave;

    private String instanceName;

	public InitializedDeviceInfo(int orgId,int deviceId, String deviceName, int deviceType, String signinid, byte slave,
			String instanceName) {
		super();
		this.orgId = orgId;
		this.deviceId = deviceId;
		this.deviceName = deviceName;
		this.deviceType = deviceType;
		this.signinid = signinid;
		this.slave = slave;
		this.instanceName = instanceName;
	}

	public InitializedDeviceInfo() {
		super();
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
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

	public String getSigninid() {
		return signinid;
	}

	public void setSigninid(String signinid) {
		this.signinid = signinid;
	}

	public byte getSlave() {
		return slave;
	}

	public void setSlave(byte slave) {
		this.slave = slave;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
    
}
