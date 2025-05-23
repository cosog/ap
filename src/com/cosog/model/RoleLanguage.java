package com.cosog.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_LANGUAGE2ROLE")
public class RoleLanguage implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String matrix;
	private Integer language;
	private Integer roleId;

	public RoleLanguage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** full constructor */
	public RoleLanguage(Integer roleId, Integer language, String matrix) {
		this.roleId = roleId;
		this.language = language;
		this.matrix = matrix;
	}

	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "MATRIX", nullable = false, length = 8)
	public String getMatrix() {
		return this.matrix;
	}
	
	public void setMatrix(String matrix) {
		this.matrix = matrix;
	}

	@Column(name = "LANGUAGE", nullable = false, precision = 22, scale = 0)
	public Integer getLanguage() {
		return this.language;
	}
	
	public void setLanguage(Integer language) {
		this.language = language;
	}

	@Column(name = "ROLEID", nullable = false, precision = 22, scale = 0)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
}