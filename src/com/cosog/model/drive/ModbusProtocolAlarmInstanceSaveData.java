package com.cosog.model.drive;

import java.util.List;

public class ModbusProtocolAlarmInstanceSaveData {

	int id;
	String code;
	String oldName;
	String name;
	int alarmUnitId;
	String alarmUnitName;
	String sort;
	
	private List<String> delidslist;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public List<String> getDelidslist() {
		return delidslist;
	}

	public void setDelidslist(List<String> delidslist) {
		this.delidslist = delidslist;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public int getAlarmUnitId() {
		return alarmUnitId;
	}

	public void setAlarmUnitId(int alarmUnitId) {
		this.alarmUnitId = alarmUnitId;
	}

	public String getAlarmUnitName() {
		return alarmUnitName;
	}

	public void setAlarmUnitName(String alarmUnitName) {
		this.alarmUnitName = alarmUnitName;
	}
}
