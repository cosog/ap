package com.gao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *  <p>描述：采集类型 实体类  tbl_acq_group_conf</p>
 *  
 * @author zhao  2018-11-02
 *
 */
@Entity
@Table(name = "tbl_acq_group_conf")
public class AcquisitionUnit implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String unitCode;
	private String unitName;
	private String remark;

	// Constructors

	/** default constructor */
	public AcquisitionUnit() {
	}

	/** full constructor */
	public AcquisitionUnit(String unitCode, String unitName,String remark) {
		this.unitCode = unitCode;
		this.unitName = unitName;
		this.remark=remark;
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
	
	@Column(name = "REMARK", nullable = false, length = 10)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}