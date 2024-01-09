package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  <p>描述：tab信息 实体类  TBL_TABINFO</p>
 *  
 * @author zhao  2024-01-09
 *
 */
@Entity
@Table(name = "TBL_TABINFO")
public class TabInfo {
	private Integer id;
	private String tabName;
	private Integer parentId;
	private Integer sortNum;
	public TabInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TabInfo(Integer id, String tabName, Integer parentId, Integer sortNum) {
		super();
		this.id = id;
		this.tabName = tabName;
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

	@Column(name = "tabName")
	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
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
