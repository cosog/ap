package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *  <p>描述：报表实例 实体类  tbl_protocolreportinstance</p>
 *  
 * @author zhao  2022-03-25
 *
 */
@Entity
@Table(name = "tbl_protocolreportinstance")
public class ProtocolReportInstance implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name_zh_CN;
	private String name_en;
	private String name_ru;
	private String code;
	private Integer unitId;
	private Integer sort;

	// Constructors

	/** default constructor */
	public ProtocolReportInstance() {
	}

	/** full constructor */
	public ProtocolReportInstance(Integer id, String name_zh_CN, String name_en, String name_ru, String code, Integer unitId,Integer sort) {
		super();
		this.id = id;
		this.name_zh_CN = name_zh_CN;
		this.name_en = name_en;
		this.name_ru = name_ru;
		this.code = code;
		this.unitId = unitId;
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

	@Column(name = "code", nullable = true, length = 50)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "sort", precision = 22, scale = 0)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "unitId", precision = 22, scale = 0)
	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	@Column(name = "name_zh_CN", nullable = false, length = 50)
	public String getName_zh_CN() {
		return name_zh_CN;
	}

	public void setName_zh_CN(String name_zh_CN) {
		this.name_zh_CN = name_zh_CN;
	}

	@Column(name = "name_en", nullable = false, length = 50)
	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	@Column(name = "name_ru", nullable = false, length = 50)
	public String getName_ru() {
		return name_ru;
	}

	public void setName_ru(String name_ru) {
		this.name_ru = name_ru;
	}
}