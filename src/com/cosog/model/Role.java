package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *  <p>描述：角色信息 实体类  tbl_role</p>
 *  
 * @author gao  2014-06-10
 *
 */
@Entity
@Table(name = "tbl_role")
public class Role implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer roleId;
	private String roleName;
	private Integer roleLevel;
	private Integer roleFlag;
	private Integer showLevel;
	private String remark;

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** full constructor */
	public Role(Integer roleId,String roleName, Integer roleLevel, Integer roleFlag,
			Integer showLevel, String remark) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleLevel = roleLevel;
		this.roleFlag = roleFlag;
		this.showLevel = showLevel;
		this.remark = remark;
	}

	@Id
	@GeneratedValue
	@Column(name = "ROLE_ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "ROLE_NAME", nullable = false, length = 40)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "ROLE_LEVEL", nullable = false, length = 10)
	public Integer getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(Integer roleLevel) {
		this.roleLevel = roleLevel;
	}

	@Column(name = "ROLE_FLAG", nullable = false, length = 10)
	public Integer getRoleFlag() {
		return this.roleFlag;
	}

	public void setRoleFlag(Integer roleFlag) {
		this.roleFlag = roleFlag;
	}
	
	@Column(name = "REMARK", nullable = false, length = 10)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "showLevel", nullable = false, length = 10)
	public Integer getShowLevel() {
		return showLevel;
	}

	public void setShowLevel(Integer showLevel) {
		this.showLevel = showLevel;
	}

}