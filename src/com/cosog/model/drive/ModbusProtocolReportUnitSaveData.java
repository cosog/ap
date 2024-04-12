package com.cosog.model.drive;

import java.util.List;

public class ModbusProtocolReportUnitSaveData {

	int id;
	String unitCode;
	String unitName;
	String calculateType;
	String singleWellRangeReportTemplate;
	String singleWellDailyReportTemplate;
	String productionReportTemplate;
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

	public String getSingleWellRangeReportTemplate() {
		return singleWellRangeReportTemplate;
	}

	public void setSingleWellRangeReportTemplate(String singleWellRangeReportTemplate) {
		this.singleWellRangeReportTemplate = singleWellRangeReportTemplate;
	}

	public String getSingleWellDailyReportTemplate() {
		return singleWellDailyReportTemplate;
	}

	public void setSingleWellDailyReportTemplate(String singleWellDailyReportTemplate) {
		this.singleWellDailyReportTemplate = singleWellDailyReportTemplate;
	}

	public String getProductionReportTemplate() {
		return productionReportTemplate;
	}

	public void setProductionReportTemplate(String productionReportTemplate) {
		this.productionReportTemplate = productionReportTemplate;
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

	public String getCalculateType() {
		return calculateType;
	}

	public void setCalculateType(String calculateType) {
		this.calculateType = calculateType;
	}
}
