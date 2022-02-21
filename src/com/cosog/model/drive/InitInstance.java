package com.cosog.model.drive;

import java.util.List;


public class InitInstance {

	private String Method;
	
	private String InstanceName;
	
	private String ProtocolName;
    
    private String AcqProtocolType;
    
    private String CtrlProtocolType;
    
    private String SignInPrefix;
    
    private String SignInSuffix;
    
    private String HeartbeatPrefix;
    
    private String HeartbeatSuffix;
    
    private List<Group> AcqGroup;
    
    private List<Group> CtrlGroup;
	
    public InitInstance() {
		super();
	}
    
    public static class Group
	{
	    private List<Integer> Addr;

	    private int Interval;

	    public void setAddr(List<Integer> Addr){
	        this.Addr = Addr;
	    }
	    public List<Integer> getAddr(){
	        return this.Addr;
	    }
	    public void setInterval(int Interval){
	        this.Interval = Interval;
	    }
	    public int getInterval(){
	        return this.Interval;
	    }
	}

	public String getMethod() {
		return Method;
	}

	public void setMethod(String method) {
		Method = method;
	}

	public String getInstanceName() {
		return InstanceName;
	}

	public void setInstanceName(String instanceName) {
		InstanceName = instanceName;
	}

	public String getProtocolName() {
		return ProtocolName;
	}

	public void setProtocolName(String protocolName) {
		ProtocolName = protocolName;
	}

	public String getAcqProtocolType() {
		return AcqProtocolType;
	}

	public void setAcqProtocolType(String acqProtocolType) {
		AcqProtocolType = acqProtocolType;
	}

	public String getCtrlProtocolType() {
		return CtrlProtocolType;
	}

	public void setCtrlProtocolType(String ctrlProtocolType) {
		CtrlProtocolType = ctrlProtocolType;
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

	public List<Group> getAcqGroup() {
		return AcqGroup;
	}

	public void setAcqGroup(List<Group> acqGroup) {
		AcqGroup = acqGroup;
	}

	public List<Group> getCtrlGroup() {
		return CtrlGroup;
	}

	public void setCtrlGroup(List<Group> ctrlGroup) {
		CtrlGroup = ctrlGroup;
	}
	
}
