package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *  <p>描述：显示单元实体类  tbl_display_unit_conf</p>
 *  
 * @author zhao  2022-03-23
 *
 */
@Entity
@Table(name = "tbl_display_unit_conf")
public class DisplayUnit implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String unitCode;
	private String unitName;
	private String protocol;
	private Integer acqUnitId;
	private Integer calculateType;
	private String remark;

	// Constructors

	/** default constructor */
	public DisplayUnit() {
	}

	/** full constructor */
	public DisplayUnit(Integer id, String unitCode, String unitName, String protocol, Integer acqUnitId,Integer calculateType,
			String remark) {
		super();
		this.id = id;
		this.unitCode = unitCode;
		this.unitName = unitName;
		this.protocol = protocol;
		this.acqUnitId = acqUnitId;
		this.calculateType = calculateType;
		this.remark = remark;
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

	@Column(name = "PROTOCOL", nullable = false, length = 10)
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	@Column(name = "acqUnitId", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getAcqUnitId() {
		return acqUnitId;
	}

	public void setAcqUnitId(Integer acqUnitId) {
		this.acqUnitId = acqUnitId;
	}

	@Column(name = "calculateType")
	public Integer getCalculateType() {
		return calculateType;
	}

	public void setCalculateType(Integer calculateType) {
		this.calculateType = calculateType;
	}

}