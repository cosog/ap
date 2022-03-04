package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import oracle.sql.BLOB;

/**
 *  <p>描述：工况类型 实体类  tbl_rpc_worktype</p>
 *  
 * @author gao  2014-06-10
 *
 */
@Entity
@Table(name = "tbl_rpc_worktype")
public class Workstatus implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer resultCode;
	private String resultName;
	private String resultDescription;
	private BLOB resultTemplate;
	private String optimizationSuggestion;
	private String remark;
	private int    total;
	// Constructors

	/** default constructor */
	public Workstatus() {
	}

	public Workstatus(Integer id, Integer resultCode, String resultName, String resultDescription, BLOB resultTemplate,
			String optimizationSuggestion, String remark, int total) {
		super();
		this.id = id;
		this.resultCode = resultCode;
		this.resultName = resultName;
		this.resultDescription = resultDescription;
		this.resultTemplate = resultTemplate;
		this.optimizationSuggestion = optimizationSuggestion;
		this.remark = remark;
		this.total = total;
	}
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "resultCode", nullable = false, precision = 22, scale = 0)
	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	@Column(name = "resultName", nullable = false, length = 200)
	public String getResultName() {
		return resultName;
	}

	public void setResultName(String resultName) {
		this.resultName = resultName;
	}

	@Column(name = "resultDescription", length = 200)
	public String getResultDescription() {
		return resultDescription;
	}

	public void setResultDescription(String resultDescription) {
		this.resultDescription = resultDescription;
	}

	@Column(name = "resultTemplate")
	public BLOB getResultTemplate() {
		return resultTemplate;
	}

	public void setResultTemplate(BLOB resultTemplate) {
		this.resultTemplate = resultTemplate;
	}

	@Column(name = "optimizationSuggestion", length = 200)
	public String getOptimizationSuggestion() {
		return optimizationSuggestion;
	}

	public void setOptimizationSuggestion(String optimizationSuggestion) {
		this.optimizationSuggestion = optimizationSuggestion;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}