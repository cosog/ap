package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *  <p>描述：计算模式 实体类  TBL_TABMANAGER_DEVICE</p>
 *  
 * @author zhao  2025-12-15
 *
 */
@Entity
@Table(name = "TBL_TABMANAGER_DEVICE")
public class DeviceTabManager implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Integer calculateType;
	private String config;
	private Integer sort;

	// Constructors

	/** default constructor */
	public DeviceTabManager() {
	}

	/** full constructor */
	public DeviceTabManager(Integer id, String name, Integer calculateType, String config, Integer sort) {
		super();
		this.id = id;
		this.name = name;
		this.calculateType = calculateType;
		this.config = config;
		this.sort = sort;
	}

	@Id
//	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "calculateType")
	public Integer getCalculateType() {
		return calculateType;
	}

	public void setCalculateType(Integer calculateType) {
		this.calculateType = calculateType;
	}

	@Column(name = "config")
	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}