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
	private String name;
	private Integer parentId;
	private Integer sortNum;
	public DeviceTypeInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeviceTypeInfo(Integer id, String name, Integer parentId, Integer sortNum) {
		super();
		this.id = id;
		this.name = name;
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

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
