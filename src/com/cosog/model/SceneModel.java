package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *  <p>描述：场景模型 实体类  TBL_SCENEMODEL</p>
 *  
 * @author zhao  2026-10-12
 *
 */
@Entity
@Table(name = "TBL_SCENEMODEL")
public class SceneModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer orgId;
	private Integer devicetype;
	
	private String name_zh_CN;
	private String name_en;
	private String name_ru;
	
	private Integer sort;

	// Constructors

	/** default constructor */
	public SceneModel() {
	}

	public SceneModel(Integer id, Integer orgId, Integer devicetype, String name_zh_CN, String name_en, String name_ru,
			Integer sort) {
		super();
		this.id = id;
		this.orgId = orgId;
		this.devicetype = devicetype;
		this.name_zh_CN = name_zh_CN;
		this.name_en = name_en;
		this.name_ru = name_ru;
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

	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "orgId")
	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name = "devicetype")
	public Integer getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(Integer devicetype) {
		this.devicetype = devicetype;
	}
}