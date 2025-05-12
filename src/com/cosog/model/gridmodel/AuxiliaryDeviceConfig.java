package com.cosog.model.gridmodel;

import java.util.List;

public class AuxiliaryDeviceConfig {
	private int deviceType;
	private int deviceId;
	private List<Integer> auxiliaryDevice;
	private List<AdditionalInfo> additionalInfoList;
	public int getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}
	
	public List<Integer> getAuxiliaryDevice() {
		return auxiliaryDevice;
	}
	public void setAuxiliaryDevice(List<Integer> auxiliaryDevice) {
		this.auxiliaryDevice = auxiliaryDevice;
	}

	public List<AdditionalInfo> getAdditionalInfoList() {
		return additionalInfoList;
	}
	public void setAdditionalInfoList(List<AdditionalInfo> additionalInfoList) {
		this.additionalInfoList = additionalInfoList;
	}
	
	public static class AdditionalInfo{
		private String itemName;
		private String itemValue;
		private String itemUnit;
		private String overview;
		private String overviewSort;
		public String getItemName() {
			return itemName;
		}
		public void setItemName(String itemName) {
			this.itemName = itemName;
		}
		public String getItemValue() {
			return itemValue;
		}
		public void setItemValue(String itemValue) {
			this.itemValue = itemValue;
		}
		public String getItemUnit() {
			return itemUnit;
		}
		public void setItemUnit(String itemUnit) {
			this.itemUnit = itemUnit;
		}
		public String getOverview() {
			return overview;
		}
		public void setOverview(String overview) {
			this.overview = overview;
		}
		public String getOverviewSort() {
			return overviewSort;
		}
		public void setOverviewSort(String overviewSort) {
			this.overviewSort = overviewSort;
		}
		
	}

	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
}
