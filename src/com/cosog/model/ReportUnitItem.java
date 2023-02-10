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
	private String unitCode;
	private String matrix;
	private Integer showLevel;
	private Integer sort;
	private Integer reportCurve;
	private String reportCurveColor;
	private Integer dataType;

	public ReportUnitItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** full constructor */
	public ReportUnitItem(Integer id, Integer itemId, String itemName, String itemCode, String unitCode, String matrix,
			Integer showLevel, Integer sort, Integer reportCurve, String reportCurveColor, Integer dataType) {
		super();
		this.id = id;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemCode = itemCode;
		this.unitCode = unitCode;
		this.matrix = matrix;
		this.showLevel = showLevel;
		this.sort = sort;
		this.reportCurve = reportCurve;
		this.reportCurveColor = reportCurveColor;
		this.dataType = dataType;
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

	@Column(name = "unitCode", nullable = true, length = 100)
	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
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

	@Column(name = "reportCurve")
	public Integer getReportCurve() {
		return reportCurve;
	}

	public void setReportCurve(Integer reportCurve) {
		this.reportCurve = reportCurve;
	}

	@Column(name = "reportCurveColor", nullable = true, length = 8)
	public String getReportCurveColor() {
		return reportCurveColor;
	}

	public void setReportCurveColor(String reportCurveColor) {
		this.reportCurveColor = reportCurveColor;
	}
	
	@Column(name = "dataType")
	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	
}