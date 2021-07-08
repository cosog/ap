package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SC_MODULE_ACTION")
public class ModuleAction implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer mcId;
	private Integer mcModuleid;
	private String mcActioncode;

	// Constructors

	/** default constructor */
	public ModuleAction() {
	}

	/** full constructor */
	public ModuleAction(Integer mcModuleid, String mcActioncode) {
		this.mcModuleid = mcModuleid;
		this.mcActioncode = mcActioncode;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "MC_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getMcId() {
		return this.mcId;
	}

	public void setMcId(Integer mcId) {
		this.mcId = mcId;
	}

	@Column(name = "MC_MODULEID", precision = 22, scale = 0)
	public Integer getMcModuleid() {
		return this.mcModuleid;
	}

	public void setMcModuleid(Integer mcModuleid) {
		this.mcModuleid = mcModuleid;
	}

	@Column(name = "MC_ACTIONCODE", length = 100)
	public String getMcActioncode() {
		return this.mcActioncode;
	}

	public void setMcActioncode(String mcActioncode) {
		this.mcActioncode = mcActioncode;
	}

}