package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cosog.utils.StringManagerUtils;
/**
 *  <p>描述：报警组实体类  tbl_alarm_unit_conf</p>
 *  
 * @author zhao  2021-09-10
 *
 */
@Entity
@Table(name = "tbl_alarm_unit_conf")
public class AlarmUnit implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String unitCode;
	private String unitName;
	private String protocol;
	
	private String remark;

	// Constructors

	/** default constructor */
	public AlarmUnit() {
	}

	/** full constructor */
	public AlarmUnit(String unitCode, String unitName,String remark) {
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

	@Column(name = "Unit_CODE", nullable = true, length = 20)
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
	
	@Column(name = "REMARK", nullable = true, length = 10)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		if(!StringManagerUtils.isNotNull(remark)){
			this.remark = "";
		}else{
			this.remark = remark;
		}
	}
	
	@Column(name = "protocol", nullable = true, length = 10)
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

}