package com.cosog.model.drive;

import java.util.List;

public class ModbusProtocolReportInstanceSaveData {

	int id;
	String code;
	String oldName;
	private String name_zh_CN;
	private String name_en;
	private String name_ru;
	String unitName;
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getName_zh_CN() {
		return name_zh_CN;
	}

	public void setName_zh_CN(String name_zh_CN) {
		this.name_zh_CN = name_zh_CN;
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public String getName_ru() {
		return name_ru;
	}

	public void setName_ru(String name_ru) {
		this.name_ru = name_ru;
	}
}
