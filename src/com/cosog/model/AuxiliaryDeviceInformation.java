package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 *  <p>描述：辅件设备信息 实体类  tbl_auxiliarydevice</p>
 *  
 * @author zhao  2021-12-17
 *
 */
@Entity
@Table(name = "tbl_auxiliarydevice")
public class AuxiliaryDeviceInformation implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Integer type;
	private String manufacturer;
	private String model;
	private String remark;
	private Integer sort;
	

	// Constructors
	/** default constructor */
	public AuxiliaryDeviceInformation() {
	}

	/** full constructor */
	public AuxiliaryDeviceInformation(Integer id, String name, Integer type, String model, String remark,
			Integer sort) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.model = model;
		this.remark = remark;
		this.sort = sort;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "type", precision = 22, scale = 0)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "model", nullable = false, length = 200)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name = "remark", nullable = false, length = 2000)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "sort", precision = 22, scale = 0)
	public Integer getSort() {
		return sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "manufacturer", nullable = false, length = 50)
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
}