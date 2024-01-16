package com.cosog.model.gridmodel;

public class AdditionalInformationSaveData {
	private int deviceId;
	private int type;
	private String data;
	public AdditionalInformationSaveData(int deviceId, int type, String data) {
		super();
		this.deviceId = deviceId;
		this.type = type;
		this.data = data;
	}
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
