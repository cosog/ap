package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *  <p>描述：短信实例 实体类  tbl_protocolsmsinstance</p>
 *  
 * @author zhao  2021-09-18
 *
 */
@Entity
@Table(name = "tbl_protocolsmsinstance")
public class ProtocolSMSInstance implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String code;
	private String acqProtocolType;
	private String ctrlProtocolType;
	private Integer sort;

	// Constructors

	/** default constructor */
	public ProtocolSMSInstance() {
	}

	/** full constructor */
	public ProtocolSMSInstance(Integer id, String name, String code, String acqProtocolType, String ctrlProtocolType,
			Integer sort) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.acqProtocolType = acqProtocolType;
		this.ctrlProtocolType = ctrlProtocolType;
		this.sort = sort;
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

	@Column(name = "acqProtocolType", nullable = false, length = 50)
	public String getAcqProtocolType() {
		return acqProtocolType;
	}

	public void setAcqProtocolType(String acqProtocolType) {
		this.acqProtocolType = acqProtocolType;
	}

	@Column(name = "ctrlProtocolType", nullable = false, length = 50)
	public String getCtrlProtocolType() {
		return ctrlProtocolType;
	}

	public void setCtrlProtocolType(String ctrlProtocolType) {
		this.ctrlProtocolType = ctrlProtocolType;
	}

	@Column(name = "sort", precision = 22, scale = 0)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	

}