package com.gao.model.drive;

import java.util.List;

public class InitId {
	
	private String Method;
	
	private String WellName;
	
	private String ID;

    private byte Slave;

    private String ProtocolName;

    private List<Group> Group;

    public void setID(String ID){
        this.ID = ID;
    }
    public String getID(){
        return this.ID;
    }
    public void setSlave(byte Slave){
        this.Slave = Slave;
    }
    public byte getSlave(){
        return this.Slave;
    }
    public void setProtocolName(String ProtocolName){
        this.ProtocolName = ProtocolName;
    }
    public String getProtocolName(){
        return this.ProtocolName;
    }
    public void setGroup(List<Group> Group){
        this.Group = Group;
    }
    public List<Group> getGroup(){
        return this.Group;
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

	public String getWellName() {
		return WellName;
	}
	public void setWellName(String wellName) {
		WellName = wellName;
	}
	public String getMethod() {
		return Method;
	}
	public void setMethod(String method) {
		Method = method;
	}
}
