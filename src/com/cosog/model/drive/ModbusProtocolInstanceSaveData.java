package com.cosog.model.drive;

import java.util.List;

public class ModbusProtocolInstanceSaveData {

	public int id;
	public String code;
	public String oldName;
	public String name;
	public int unitId;
	public String unitName;
	
	public String acqProtocolType;
	public String ctrlProtocolType;
	
	public int signInPrefixSuffixHex;
	public String signInPrefix;
	public String signInSuffix;
	public int signInIDHex;
	
	public int heartbeatPrefixSuffixHex;
	public String heartbeatPrefix;
	public String heartbeatSuffix;
	
	public String packetSendInterval;
	
	public String sort;
	
	public String protocol;
	public String protocolDeviceTypeAllPath;
	
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

	public String getPacketSendInterval() {
		return packetSendInterval;
	}

	public void setPacketSendInterval(String packetSendInterval) {
		this.packetSendInterval = packetSendInterval;
	}

	public int getSignInPrefixSuffixHex() {
		return signInPrefixSuffixHex;
	}

	public void setSignInPrefixSuffixHex(int signInPrefixSuffixHex) {
		this.signInPrefixSuffixHex = signInPrefixSuffixHex;
	}

	public int getSignInIDHex() {
		return signInIDHex;
	}

	public void setSignInIDHex(int signInIDHex) {
		this.signInIDHex = signInIDHex;
	}

	public int getHeartbeatPrefixSuffixHex() {
		return heartbeatPrefixSuffixHex;
	}

	public void setHeartbeatPrefixSuffixHex(int heartbeatPrefixSuffixHex) {
		this.heartbeatPrefixSuffixHex = heartbeatPrefixSuffixHex;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getProtocolDeviceTypeAllPath() {
		return protocolDeviceTypeAllPath;
	}

	public void setProtocolDeviceTypeAllPath(String protocolDeviceTypeAllPath) {
		this.protocolDeviceTypeAllPath = protocolDeviceTypeAllPath;
	}
}
