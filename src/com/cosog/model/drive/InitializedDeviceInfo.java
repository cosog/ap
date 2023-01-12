package com.cosog.model.drive;

import java.util.List;

public class InitializedDeviceInfo {
	
	public int orgId;
	
	public int deviceId;
	
	public String deviceName;
	
	public int deviceType;
	
	private String tcpServer;
	
	private String signinid;
	
	private String ipPort;

    private byte slave;
    
    public int peakDelay;

    private String instanceName;

	public InitializedDeviceInfo(int orgId,int deviceId, String deviceName, int deviceType, String tcpServer, String signinid, String ipPort, byte slave,int peakDelay,
			String instanceName) {
		super();
		this.orgId = orgId;
		this.deviceId = deviceId;
		this.deviceName = deviceName;
		this.deviceType = deviceType;
		this.tcpServer = tcpServer;
		this.signinid = signinid;
		this.ipPort = ipPort;
		this.slave = slave;
		this.peakDelay = peakDelay;
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

	public String getTcpServer() {
		return tcpServer;
	}

	public void setTcpServer(String tcpServer) {
		this.tcpServer = tcpServer;
	}

	public int getPeakDelay() {
		return peakDelay;
	}

	public void setPeakDelay(int peakDelay) {
		this.peakDelay = peakDelay;
	}

	public String getIpPort() {
		return ipPort;
	}

	public void setIpPort(String ipPort) {
		this.ipPort = ipPort;
	}
    
}
