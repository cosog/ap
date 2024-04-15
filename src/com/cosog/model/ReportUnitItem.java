package com.cosog.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_report_items2unit_conf")
public class ReportUnitItem implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer itemId;
	private String itemName;
	private String itemCode;
	private Integer unitId;
	private String matrix;
	private Integer showLevel;
	private Integer sort;
	
	private Integer sumSign;
	private Integer averageSign;
	
	private String reportCurveConf;
	
	private Integer curveStatType;
	
	private Integer dataType;
	private Integer reportType;
	private Integer totalType;
	private Integer prec;
	
	private String dataSource;

	public ReportUnitItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** full constructor */
	public ReportUnitItem(Integer id, Integer itemId, String itemName, String itemCode, Integer unitId, String matrix,
			Integer showLevel, Integer sort, 
			String reportCurveConf, Integer dataType, Integer reportType, Integer totalType,  
			Integer prec) {
		super();
		this.id = id;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemCode = itemCode;
		this.unitId = unitId;
		this.matrix = matrix;
		this.showLevel = showLevel;
		this.sort = sort;
		this.reportCurveConf = reportCurveConf;
		this.dataType = dataType;
		this.reportType = reportType;
		this.totalType = totalType;
		this.prec=prec;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "matrix", nullable = true, length = 8)
	public String getMatrix() {
		return this.matrix;
	}
	
	public void setMatrix(String matrix) {
		this.matrix = matrix;
	}
	
	@Column(name = "itemid", nullable = true, precision = 22, scale = 0)
	public Integer getItemId() {
		return this.itemId;
	}
	
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@Column(name = "itemName", nullable = true, length = 100)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "itemCode", nullable = true, length = 8)
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "showLevel")
	public Integer getShowLevel() {
		return showLevel;
	}

	public void setShowLevel(Integer showLevel) {
		this.showLevel = showLevel;
	}

	@Column(name = "reportCurveConf", nullable = true, length = 8)
	public String getReportCurveConf() {
		return reportCurveConf;
	}

	public void setReportCurveConf(String reportCurveConf) {
		this.reportCurveConf = reportCurveConf;
	}
	
	@Column(name = "dataType")
	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	@Column(name = "reportType")
	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	@Column(name = "unitId")
	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	@Column(name = "sumSign")
	public Integer getSumSign() {
		return sumSign;
	}

	public void setSumSign(Integer sumSign) {
		this.sumSign = sumSign;
	}

	@Column(name = "averageSign")
	public Integer getAverageSign() {
		return averageSign;
	}

	public void setAverageSign(Integer averageSign) {
		this.averageSign = averageSign;
	}

	@Column(name = "curveStatType")
	public Integer getCurveStatType() {
		return curveStatType;
	}

	public void setCurveStatType(Integer curveStatType) {
		this.curveStatType = curveStatType;
	}

	@Column(name = "prec")
	public Integer getPrec() {
		return prec;
	}

	public void setPrec(Integer prec) {
		this.prec = prec;
	}

	@Column(name = "totalType")
	public Integer getTotalType() {
		return totalType;
	}

	public void setTotalType(Integer totalType) {
		this.totalType = totalType;
	}

	@Column(name = "dataSource")
	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
}