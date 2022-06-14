package com.cosog.model;

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
}
