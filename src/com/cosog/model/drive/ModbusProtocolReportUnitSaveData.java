package com.cosog.model.drive;

import java.util.List;

public class ModbusProtocolReportUnitSaveData {

	int id;
	String unitCode;
	String unitName;
	String singlewellReportTemplate;
	String productionReportTemplate;
	int deviceType=0;
	String sort;
	
	private List<String> delidslist;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getSinglewellReportTemplate() {
		return singlewellReportTemplate;
	}

	public void setSinglewellReportTemplate(String singlewellReportTemplate) {
		this.singlewellReportTemplate = singlewellReportTemplate;
	}

	public String getProductionReportTemplate() {
		return productionReportTemplate;
	}

	public void setProductionReportTemplate(String productionReportTemplate) {
		this.productionReportTemplate = productionReportTemplate;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
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
}