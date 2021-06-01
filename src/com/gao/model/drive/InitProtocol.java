package com.gao.model.drive;

import java.util.List;

public class InitProtocol {

	private String ProtocolName;
    
    private String ProtocolType;
    
    private String StoreMode;
    
    private String SignInPrefix;
    
    private String SignInSuffix;
    
    private String HeartbeatPrefix;
    
    private String HeartbeatSuffix;
    
    private List<Integer> Addr;
    
    private List<Integer> Quantity;
    
    private List<String> DataType;
    
    private List<String> RWType;
    
    private List<Float> Ratio;
    
    private List<String> AcqMode;

	public String getProtocolName() {
		return ProtocolName;
	}

	public void setProtocolName(String protocolName) {
		ProtocolName = protocolName;
	}

	public String getProtocolType() {
		return ProtocolType;
	}

	public void setProtocolType(String protocolType) {
		ProtocolType = protocolType;
	}

	public String getStoreMode() {
		return StoreMode;
	}

	public void setStoreMode(String storeMode) {
		StoreMode = storeMode;
	}

	public String getSignInPrefix() {
		return SignInPrefix;
	}

	public void setSignInPrefix(String signInPrefix) {
		SignInPrefix = signInPrefix;
	}

	public String getSignInSuffix() {
		return SignInSuffix;
	}

	public void setSignInSuffix(String signInSuffix) {
		SignInSuffix = signInSuffix;
	}

	public String getHeartbeatPrefix() {
		return HeartbeatPrefix;
	}

	public void setHeartbeatPrefix(String heartbeatPrefix) {
		HeartbeatPrefix = heartbeatPrefix;
	}

	public String getHeartbeatSuffix() {
		return HeartbeatSuffix;
	}

	public void setHeartbeatSuffix(String heartbeatSuffix) {
		HeartbeatSuffix = heartbeatSuffix;
	}

	public List<Integer> getAddr() {
		return Addr;
	}

	public void setAddr(List<Integer> addr) {
		Addr = addr;
	}

	public List<Integer> getQuantity() {
		return Quantity;
	}

	public void setQuantity(List<Integer> quantity) {
		Quantity = quantity;
	}

	public List<String> getDataType() {
		return DataType;
	}

	public void setDataType(List<String> dataType) {
		DataType = dataType;
	}

	public List<String> getRWType() {
		return RWType;
	}

	public void setRWType(List<String> rWType) {
		RWType = rWType;
	}

	public List<Float> getRatio() {
		return Ratio;
	}

	public void setRatio(List<Float> ratio) {
		Ratio = ratio;
	}

	public List<String> getAcqMode() {
		return AcqMode;
	}

	public void setAcqMode(List<String> acqMode) {
		AcqMode = acqMode;
	}
	
}
