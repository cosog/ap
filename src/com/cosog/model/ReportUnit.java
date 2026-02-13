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
	private Integer calculateType;
	private String singleWellRangeReportTemplate;
	private String singleWellDailyReportTemplate;
	private String productionReportTemplate;
	private Integer classes;
	private Integer sort;
	// Constructors

	/** default constructor */
	public ReportUnit() {
	}

	/** full constructor */
	public ReportUnit(Integer id, String unitCode, String unitName,Integer calculateType, String singleWellRangeReportTemplate,String singleWellDailyReportTemplate,
			String productionReportTemplate, Integer classes, Integer sort) {
		super();
		this.id = id;
		this.unitCode = unitCode;
		this.unitName = unitName;
		this.calculateType = calculateType;
		this.singleWellRangeReportTemplate = singleWellRangeReportTemplate;
		this.singleWellDailyReportTemplate = singleWellDailyReportTemplate;
		this.productionReportTemplate = productionReportTemplate;
		this.classes = classes;
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

	@Column(name = "singleWellRangeReportTemplate", length = 40)
	public String getSingleWellRangeReportTemplate() {
		return singleWellRangeReportTemplate;
	}

	public void setSingleWellRangeReportTemplate(String singleWellRangeReportTemplate) {
		this.singleWellRangeReportTemplate = singleWellRangeReportTemplate;
	}

	@Column(name = "productionReportTemplate", length = 40)
	public String getProductionReportTemplate() {
		return productionReportTemplate;
	}

	public void setProductionReportTemplate(String productionReportTemplate) {
		this.productionReportTemplate = productionReportTemplate;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "singleWellDailyReportTemplate", length = 40)
	public String getSingleWellDailyReportTemplate() {
		return singleWellDailyReportTemplate;
	}

	public void setSingleWellDailyReportTemplate(String singleWellDailyReportTemplate) {
		this.singleWellDailyReportTemplate = singleWellDailyReportTemplate;
	}

	@Column(name = "calculateType")
	public Integer getCalculateType() {
		return calculateType;
	}

	public void setCalculateType(Integer calculateType) {
		this.calculateType = calculateType;
	}

	@Column(name = "classes")
	public Integer getClasses() {
		return classes;
	}

	public void setClasses(Integer classes) {
		this.classes = classes;
	}

}