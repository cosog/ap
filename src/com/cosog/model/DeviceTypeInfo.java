package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  <p>描述：tab信息 实体类  TBL_DEVICETYPEINFO</p>
 *  
 * @author zhao  2024-01-09
 *
 */
@Entity
@Table(name = "TBL_DEVICETYPEINFO")
public class DeviceTypeInfo {
	private Integer id;
	private String name_zh_CN;
	private String name_en;
	private String name_ru;
	private Integer parentId;
	private Integer sortNum;
	public DeviceTypeInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeviceTypeInfo(Integer id, String name_zh_CN, String name_en, String name_ru, Integer parentId,
			Integer sortNum) {
		super();
		this.id = id;
		this.name_zh_CN = name_zh_CN;
		this.name_en = name_en;
		this.name_ru = name_ru;
		this.parentId = parentId;
		this.sortNum = sortNum;
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

	@Column(name = "parentId", nullable = false)
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "sortNum")
	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getName_zh_CN() {
		return name_zh_CN;
	}

	public void setName_zh_CN(String name_zh_CN) {
		this.name_zh_CN = name_zh_CN;
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public String getName_ru() {
		return name_ru;
	}

	public void setName_ru(String name_ru) {
		this.name_ru = name_ru;
	}
}
