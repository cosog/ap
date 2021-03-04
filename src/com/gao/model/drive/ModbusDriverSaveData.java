package com.gao.model.drive;

import java.util.List;
import java.util.ArrayList;

public class ModbusDriverSaveData {

	private String DriverName;

    private String Protocol;

    private int Port;

    private String HeartbeatPacket;

    private List<DataConfig> DataConfig;
    
    public void setDriverName(String DriverName){
        this.DriverName = DriverName;
    }
    public String getDriverName(){
        return this.DriverName;
    }
    public void setProtocol(String Protocol){
        this.Protocol = Protocol;
    }
    public String getProtocol(){
        return this.Protocol;
    }
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
