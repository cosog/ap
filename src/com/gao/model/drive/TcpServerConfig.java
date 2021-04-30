package com.gao.model.drive;

public class TcpServerConfig {
	private int Port;
    
    private String HeartbeatPrefix;
    
    private String HeartbeatSuffix;

    public void setPort(int Port){
        this.Port = Port;
    }
    public int getPort(){
        return this.Port;
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
}
