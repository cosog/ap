package com.cosog.model.drive;

import java.util.List;

public class ModbusProtocolInstanceSaveData {

	int id;
	String code;
	String oldName;
	String name;
	int deviceType=0;
	int unitId;
	String unitName;
	
	String acqProtocolType;
	String ctrlProtocolType;
	
	String signInPrefix;
	String signInSuffix;
	
	String heartbeatPrefix;
	String heartbeatSuffix;
	
	String sort;
	
	private List<String> delidslist;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getAcqProtocolType() {
		return acqProtocolType;
	}

	public void setAcqProtocolType(String acqProtocolType) {
		this.acqProtocolType = acqProtocolType;
	}

	public String getCtrlProtocolType() {
		return ctrlProtocolType;
	}

	public void setCtrlProtocolType(String ctrlProtocolType) {
		this.ctrlProtocolType = ctrlProtocolType;
	}

	public String getSignInPrefix() {
		return signInPrefix;
	}

	public void setSignInPrefix(String signInPrefix) {
		this.signInPrefix = signInPrefix;
	}

	public String getSignInSuffix() {
		return signInSuffix;
	}

	public void setSignInSuffix(String signInSuffix) {
		this.signInSuffix = signInSuffix;
	}

	public String getHeartbeatPrefix() {
		return heartbeatPrefix;
	}

	public void setHeartbeatPrefix(String heartbeatPrefix) {
		this.heartbeatPrefix = heartbeatPrefix;
	}

	public String getHeartbeatSuffix() {
		return heartbeatSuffix;
	}

	public void setHeartbeatSuffix(String heartbeatSuffix) {
		this.heartbeatSuffix = heartbeatSuffix;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public List<String> getDelidslist() {
		return delidslist;
	}

	public void setDelidslist(List<String> delidslist) {
		this.delidslist = delidslist;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
}
