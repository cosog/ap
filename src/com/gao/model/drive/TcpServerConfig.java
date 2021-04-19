package com.gao.model.drive;

public class TcpServerConfig {
	private int Port;

    private String HeartbeatPacket;

    public void setPort(int Port){
        this.Port = Port;
    }
    public int getPort(){
        return this.Port;
    }
    public void setHeartbeatPacket(String HeartbeatPacket){
        this.HeartbeatPacket = HeartbeatPacket;
    }
    public String getHeartbeatPacket(){
        return this.HeartbeatPacket;
    }
}
