package com.cosog.model.drive;

import java.util.List;

public class AcqGroup {

	private String ID;
	
	private byte Slave;
	
	private int GroupSN;
	
	private List<Integer> Addr;
	
	private List<List<Object>> Value;

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

	public int getGroupSN() {
		return GroupSN;
	}

	public void setGroupSN(int groupSN) {
		GroupSN = groupSN;
	}

	public List<Integer> getAddr() {
		return Addr;
	}

	public void setAddr(List<Integer> addr) {
		Addr = addr;
	}

	public List<List<Object>> getValue() {
		return Value;
	}

	public void setValue(List<List<Object>> value) {
		Value = value;
	}
	
}
