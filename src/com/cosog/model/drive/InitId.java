package com.cosog.model.drive;

import java.util.List;

public class InitId {
	
	private String Method;
	
	private String IPPort;
	
	private String ID;

    private byte Slave;

    private String InstanceName;

	public String getMethod() {
		return Method;
	}

	public void setMethod(String method) {
		Method = method;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public byte getSlave() {
		return Slave;
	}

	public void setSlave(byte slave) {
		Slave = slave;
	}

	public String getInstanceName() {
		return InstanceName;
	}

	public void setInstanceName(String instanceName) {
		InstanceName = instanceName;
	}

	public String getIPPort() {
		return IPPort;
	}

	public void setIPPort(String iPPort) {
		IPPort = iPPort;
	}
    
}
