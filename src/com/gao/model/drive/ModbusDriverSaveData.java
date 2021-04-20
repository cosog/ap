package com.gao.model.drive;

import java.util.List;
import java.util.ArrayList;

public class ModbusDriverSaveData {

	private String DriverName;
	
	private int TcpServerPort;
	
	public int getTcpServerPort() {
		return TcpServerPort;
	}
	public void setTcpServerPort(int tcpServerPort) {
		TcpServerPort = tcpServerPort;
	}
	public String getTcpServerHeartbeatPacket() {
		return TcpServerHeartbeatPacket;
	}
	public void setTcpServerHeartbeatPacket(String tcpServerHeartbeatPacket) {
		TcpServerHeartbeatPacket = tcpServerHeartbeatPacket;
	}

	private String TcpServerHeartbeatPacket;

    private List<DataConfig> DataConfig;
    
    public void setDriverName(String DriverName){
        this.DriverName = DriverName;
    }
    public String getDriverName(){
        return this.DriverName;
    }
    public void setDataConfig(List<DataConfig> DataConfig){
        this.DataConfig = DataConfig;
    }
    public List<DataConfig> getDataConfig(){
        return this.DataConfig;
    }
	
	public static class DataConfig
	{
	    private String Name;

	    private int Address;

	    private int Length;

	    private String DataType;

	    private float Zoom;

	    public void setName(String Name){
	        this.Name = Name;
	    }
	    public String getName(){
	        return this.Name;
	    }
	    public void setAddress(int Address){
	        this.Address = Address;
	    }
	    public int getAddress(){
	        return this.Address;
	    }
	    public void setLength(int Length){
	        this.Length = Length;
	    }
	    public int getLength(){
	        return this.Length;
	    }
	    public void setDataType(String DataType){
	        this.DataType = DataType;
	    }
	    public String getDataType(){
	        return this.DataType;
	    }
	    public void setZoom(float Zoom){
	        this.Zoom = Zoom;
	    }
	    public float getZoom(){
	        return this.Zoom;
	    }
	}
}
