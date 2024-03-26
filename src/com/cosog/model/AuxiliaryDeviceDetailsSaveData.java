package com.cosog.model;

import java.util.List;

public class AuxiliaryDeviceDetailsSaveData {
	private int deviceId;
	private int auxiliaryDeviceSpecificType;
	private List<AuxiliaryDeviceAddInfo> auxiliaryDeviceDetailsList;
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	public int getAuxiliaryDeviceSpecificType() {
		return auxiliaryDeviceSpecificType;
	}
	public void setAuxiliaryDeviceSpecificType(int auxiliaryDeviceSpecificType) {
		this.auxiliaryDeviceSpecificType = auxiliaryDeviceSpecificType;
	}
	public List<AuxiliaryDeviceAddInfo> getAuxiliaryDeviceDetailsList() {
		return auxiliaryDeviceDetailsList;
	}
	public void setAuxiliaryDeviceDetailsList(List<AuxiliaryDeviceAddInfo> auxiliaryDeviceDetailsList) {
		this.auxiliaryDeviceDetailsList = auxiliaryDeviceDetailsList;
	}
	
}
