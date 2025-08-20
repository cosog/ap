package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *  <p>描述：协议显示实例 实体类  tbl_protocoldisplayinstance</p>
 *  
 * @author zhao  2022-03-25
 *
 */
@Entity
@Table(name = "tbl_protocoldisplayinstance")
public class ProtocolDisplayInstance implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String code;
	private Integer displayUnitId;
	private Integer sort;

	// Constructors

	/** default constructor */
	public ProtocolDisplayInstance() {
	}

	/** full constructor */
	public ProtocolDisplayInstance(Integer id, String name, String code, Integer displayUnitId,Integer sort) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.displayUnitId = displayUnitId;
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

	@Column(name = "code", nullable = true, length = 50)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "displayUnitId", precision = 22, scale = 0)
	public Integer getDisplayUnitId() {
		return displayUnitId;
	}

	public void setDisplayUnitId(Integer displayUnitId) {
		this.displayUnitId = displayUnitId;
	}

	@Column(name = "sort", precision = 22, scale = 0)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}