package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
	private String orgName_zh_CN;
	private String orgName_en;
	private String orgName_ru;
	private String orgMemo;
	private Integer orgParent;
	private Integer orgSeq;
	public Org() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
//	@GeneratedValue(
//	        strategy = GenerationType.SEQUENCE, 
//	        generator = "org_seq"
//	    )
//    @SequenceGenerator(
//        name = "org_seq",
//        sequenceName = "SEQ_ORG",
//        allocationSize = 1,
//        initialValue = 1
//    )
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

	@Column(name = "ORG_NAME_ZH_CN" )
	public String getOrgName_zh_CN() {
		return orgName_zh_CN;
	}

	public void setOrgName_zh_CN(String orgName_zh_CN) {
		this.orgName_zh_CN = orgName_zh_CN;
	}

	@Column(name = "ORG_NAME_EN" )
	public String getOrgName_en() {
		return orgName_en;
	}

	public void setOrgName_en(String orgName_en) {
		this.orgName_en = orgName_en;
	}

	@Column(name = "ORG_NAME_RU" )
	public String getOrgName_ru() {
		return orgName_ru;
	}

	public void setOrgName_ru(String orgName_ru) {
		this.orgName_ru = orgName_ru;
	}
}
