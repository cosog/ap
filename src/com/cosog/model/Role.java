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
	private String roleName_zh_CN;
	private String roleName_en;
	private String roleName_ru;
	private Integer roleLevel;
	private Integer roleVideoKeyEdit;
	private Integer roleLanguageEdit;
	private Integer showLevel;
	private String remark_zh_CN;
	private String remark_en;
	private String remark_ru;

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** full constructor */
	public Role(Integer roleId,
			String roleName_zh_CN, String roleName_en, String roleName_ru, 
			Integer roleLevel,
			Integer roleVideoKeyEdit,
			Integer roleLanguageEdit,
			Integer showLevel, 
			String remark_zh_CN,String remark_en,String remark_ru) {
		super();
		this.roleId = roleId;
		this.roleName_zh_CN = roleName_zh_CN;
		this.roleName_en = roleName_en;
		this.roleName_ru = roleName_ru;
		this.roleLevel = roleLevel;
		this.roleVideoKeyEdit = roleVideoKeyEdit;
		this.roleLanguageEdit = roleLanguageEdit;
		this.showLevel = showLevel;
		this.remark_zh_CN = remark_zh_CN;
		this.remark_en = remark_en;
		this.remark_ru = remark_ru;
	}

	@Id
//	@GeneratedValue
	@Column(name = "ROLE_ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "ROLE_LEVEL", nullable = false, length = 10)
	public Integer getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(Integer roleLevel) {
		this.roleLevel = roleLevel;
	}

	@Column(name = "SHOWLEVEL", nullable = false, length = 10)
	public Integer getShowLevel() {
		return showLevel;
	}

	public void setShowLevel(Integer showLevel) {
		this.showLevel = showLevel;
	}

	@Column(name = "ROLE_VIDEOKEYEDIT", nullable = false, length = 10)
	public Integer getRoleVideoKeyEdit() {
		return roleVideoKeyEdit;
	}

	public void setRoleVideoKeyEdit(Integer roleVideoKeyEdit) {
		this.roleVideoKeyEdit = roleVideoKeyEdit;
	}

	@Column(name = "ROLE_LANGUAGEEDIT", nullable = false, length = 10)
	public Integer getRoleLanguageEdit() {
		return roleLanguageEdit;
	}

	public void setRoleLanguageEdit(Integer roleLanguageEdit) {
		this.roleLanguageEdit = roleLanguageEdit;
	}

	@Column(name = "ROLE_NAME_ZH_CN", nullable = false, length = 40)
	public String getRoleName_zh_CN() {
		return roleName_zh_CN;
	}

	public void setRoleName_zh_CN(String roleName_zh_CN) {
		this.roleName_zh_CN = roleName_zh_CN;
	}

	@Column(name = "ROLE_NAME_EN", nullable = false, length = 40)
	public String getRoleName_en() {
		return roleName_en;
	}

	public void setRoleName_en(String roleName_en) {
		this.roleName_en = roleName_en;
	}

	@Column(name = "ROLE_NAME_RU", nullable = false, length = 40)
	public String getRoleName_ru() {
		return roleName_ru;
	}

	public void setRoleName_ru(String roleName_ru) {
		this.roleName_ru = roleName_ru;
	}

	@Column(name = "REMARK_ZH_CN", nullable = false, length = 10)
	public String getRemark_zh_CN() {
		return remark_zh_CN;
	}

	public void setRemark_zh_CN(String remark_zh_CN) {
		this.remark_zh_CN = remark_zh_CN;
	}

	@Column(name = "REMARK_EN", nullable = false, length = 10)
	public String getRemark_en() {
		return remark_en;
	}

	public void setRemark_en(String remark_en) {
		this.remark_en = remark_en;
	}

	@Column(name = "REMARK_RU", nullable = false, length = 10)
	public String getRemark_ru() {
		return remark_ru;
	}

	public void setRemark_ru(String remark_ru) {
		this.remark_ru = remark_ru;
	}

	

}