package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *  <p>描述：报表单元实体类  tbl_report_unit_conf</p>
 *  
 * @author zhao  2023-03-06
 *
 */
@Entity
@Table(name = "tbl_report_unit_conf")
public class ReportUnit implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String unitCode;
	private String unitName;
	private String singleWellReportTemplate;
	private String productionReportTemplate;
	private Integer deviceType;
	private Integer sort;
	// Constructors

	/** default constructor */
	public ReportUnit() {
	}

	/** full constructor */
	public ReportUnit(Integer id, String unitCode, String unitName, String singleWellReportTemplate,
			String productionReportTemplate, Integer deviceType, Integer sort) {
		super();
		this.id = id;
		this.unitCode = unitCode;
		this.unitName = unitName;
		this.singleWellReportTemplate = singleWellReportTemplate;
		this.productionReportTemplate = productionReportTemplate;
		this.deviceType = deviceType;
		this.sort = sort;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "UNIT_CODE", nullable = false, length = 20)
	public String getUnitCode() {
		return this.unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	@Column(name = "UNIT_NAME", nullable = false, length = 40)
	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@Column(name = "singleWellReportTemplate", length = 40)
	public String getSingleWellReportTemplate() {
		return singleWellReportTemplate;
	}

	public void setSingleWellReportTemplate(String singleWellReportTemplate) {
		this.singleWellReportTemplate = singleWellReportTemplate;
	}

	@Column(name = "productionReportTemplate", length = 40)
	public String getProductionReportTemplate() {
		return productionReportTemplate;
	}

	public void setProductionReportTemplate(String productionReportTemplate) {
		this.productionReportTemplate = productionReportTemplate;
	}

	@Column(name = "deviceType")
	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}