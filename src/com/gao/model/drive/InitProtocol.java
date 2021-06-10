package com.gao.model.drive;

import java.util.ArrayList;
import java.util.List;

public class InitProtocol {

	private String Method;
	
	private String ProtocolName;
    
    private String ProtocolType;
    
    private String StoreMode;
    
    private String SignInPrefix;
    
    private String SignInSuffix;
    
    private String HeartbeatPrefix;
    
    private String HeartbeatSuffix;
    
    private List<Integer> Addr;
    
    private List<Integer> Quantity;
    
    private List<String> SrcDataType;
    
    private List<String> DestDataType;
    
    private List<String> RWType;
    
    private List<Float> Ratio;
    
    private List<String> AcqMode;
	
    public InitProtocol() {
		super();
	}
    
    public InitProtocol(ModbusProtocolConfig.Protocol protocolConfig) {
    	this.setProtocolName(protocolConfig.getName());
    	this.setProtocolType(protocolConfig.getType()==0?"tcp_pasv":(protocolConfig.getType()==1?"rtu_pasv":"rtu_active"));
    	this.setStoreMode(protocolConfig.getStoreMode()==0?"big":"little");
    	this.setSignInPrefix(protocolConfig.getSignInPrefix());
    	this.setSignInSuffix(protocolConfig.getSignInSuffix());
    	this.setHeartbeatPrefix(protocolConfig.getHeartbeatPrefix());
    	this.setHeartbeatSuffix(protocolConfig.getHeartbeatSuffix());
    	this.Addr=new ArrayList<Integer>();
    	this.Quantity=new ArrayList<Integer>();
    	this.SrcDataType=new ArrayList<String>();
    	this.DestDataType=new ArrayList<String>();
    	this.RWType=new ArrayList<String>();
    	this.Ratio=new ArrayList<Float>();
    	this.AcqMode=new ArrayList<String>();
    	for(int i=0;i<protocolConfig.getItems().size();i++){
    		this.Addr.add(protocolConfig.getItems().get(i).getAddr());
    		this.Quantity.add(protocolConfig.getItems().get(i).getQuantity());
    		this.SrcDataType.add(protocolConfig.getItems().get(i).getSrcDataType());
    		this.DestDataType.add(protocolConfig.getItems().get(i).getDestDataType());
    		this.RWType.add(protocolConfig.getItems().get(i).getRWType()?"r":"rw");
    		this.Ratio.add(protocolConfig.getItems().get(i).getRatio());
    		this.AcqMode.add(protocolConfig.getItems().get(i).getAcqMode()?"active":"pasv");
    	}
	}
    
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
	
	public List<String> getSrcDataType() {
		return SrcDataType;
	}

	public void setSrcDataType(List<String> srcDataType) {
		SrcDataType = srcDataType;
	}

	public List<String> getDestDataType() {
		return DestDataType;
	}

	public void setDestDataType(List<String> destDataType) {
		DestDataType = destDataType;
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

	public String getMethod() {
		return Method;
	}

	public void setMethod(String method) {
		Method = method;
	}
	
}
