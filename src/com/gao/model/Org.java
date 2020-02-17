package com.gao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  <p>描述：组织信息 实体类  tbl_org</p>
 *  
 * @author gao  2014-06-10
 *
 */
@Entity
@Table(name = "tbl_org")
public class Org {
	private Integer orgId;
	private String orgCode;
	private String orgName;
	private String orgMemo;
	private Integer orgParent;
	private Integer orgSeq;
	private String orgFlag;
	private Integer orgRealid;
	private Integer orgLevel;
	private String orgType;
	private Double orgCoordX;
	private Double orgCoordY;
	private Integer showlevel;


	public Org() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue
	@Column(name = "org_id")
	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name = "org_code")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "org_name" )
	//@Type(type="com.gao.utils.GBKString")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "org_memo")
	//@Type(type="com.gao.utils.GBKString")
	public String getOrgMemo() {
		return orgMemo;
	}

	public void setOrgMemo(String orgMemo) {
		this.orgMemo = orgMemo;
	}

	@Column(name = "org_parent")
	public Integer getOrgParent() {
		return orgParent;
	}

	public void setOrgParent(Integer orgParent) {
		this.orgParent = orgParent;
	}

	@Column(name = "org_seq")
	public Integer getOrgSeq() {
		return orgSeq;
	}

	public void setOrgSeq(Integer orgSeq) {
		this.orgSeq = orgSeq;
	}

	@Column(name = "org_flag")
	public String getOrgFlag() {
		return orgFlag;
	}

	public void setOrgFlag(String orgFlag) {
		this.orgFlag = orgFlag;
	}

	@Column(name = "org_realid")
	public Integer getOrgRealid() {
		return orgRealid;
	}

	public void setOrgRealid(Integer orgRealid) {
		this.orgRealid = orgRealid;
	}

	@Column(name = "org_level")
	public Integer getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	@Column(name = "org_type")
	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	
	@Column(name = "org_coordx")
	public Double getOrgCoordX() {
		return orgCoordX;
	}

	public void setOrgCoordX(Double orgCoordX) {
		this.orgCoordX = orgCoordX;
	}
	
	@Column(name = "org_coordy")
	public Double getOrgCoordY() {
		return orgCoordY;
	}

	public void setOrgCoordY(Double orgCoordY) {
		this.orgCoordY = orgCoordY;
	}

	@Column(name = "show_level")
	public Integer getShowlevel() {
		return showlevel;
	}

	public void setShowlevel(Integer showlevel) {
		this.showlevel = showlevel;
	}

}
