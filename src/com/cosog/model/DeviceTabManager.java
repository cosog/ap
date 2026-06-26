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
	private String name_zh_CN;
	private String name_en;
	private String name_ru;
	private Integer calculateType;
	private String config;
	private Integer sort;

	// Constructors

	/** default constructor */
	public DeviceTabManager() {
	}

	/** full constructor */
	public DeviceTabManager(Integer id, String name_zh_CN, String name_en, String name_ru, Integer calculateType, String config, Integer sort) {
		super();
		this.id = id;
		this.name_zh_CN = name_zh_CN;
		this.name_en = name_en;
		this.name_ru = name_ru;
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

	@Column(name = "name_zh_CN")
	public String getName_zh_CN() {
		return name_zh_CN;
	}

	public void setName_zh_CN(String name_zh_CN) {
		this.name_zh_CN = name_zh_CN;
	}

	@Column(name = "name_en")
	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	@Column(name = "name_ru")
	public String getName_ru() {
		return name_ru;
	}

	public void setName_ru(String name_ru) {
		this.name_ru = name_ru;
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