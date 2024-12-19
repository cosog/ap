package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *  <p>描述：模块信息 实体类  tbl_module</p>
 *  
 * @author gao  2014-06-10
 *
 */
@Entity
@Table(name = "tbl_module")
public  class Module implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer mdId;
	private Integer mdParentid;
	private String mdName_zh_CN;
	private String mdName_en;
	private String mdName_ru;
	private String mdShowname_zh_CN;
	private String mdShowname_en;
	private String mdShowname_ru;
	private String mdUrl;
	private String mdCode;
	private Integer mdSeq;
	private Integer mdLevel;
	private Integer mdFlag;
	private String mdIcon;
	private Integer mdType;
	private String mdControl;

	public Module() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue
	@Column(name = "MD_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getMdId() {
		return this.mdId;
	}

	public void setMdId(Integer mdId) {
		this.mdId = mdId;
	}

	@Column(name = "MD_PARENTID", nullable = false, precision = 22, scale = 0)
	public Integer getMdParentid() {
		return this.mdParentid;
	}

	public void setMdParentid(Integer mdParentid) {
		this.mdParentid = mdParentid;
	}

	@Column(name = "MD_URL", length = 200)
	public String getMdUrl() {
		return this.mdUrl;
	}

	public void setMdUrl(String mdUrl) {
		this.mdUrl = mdUrl;
	}

	@Column(name = "MD_CODE", length = 200)
	public String getMdCode() {
		return this.mdCode;
	}

	public void setMdCode(String mdCode) {
		this.mdCode = mdCode;
	}

	@Column(name = "MD_SEQ", precision = 22, scale = 0)
	public Integer getMdSeq() {
		return this.mdSeq;
	}

	public void setMdSeq(Integer mdSeq) {
		this.mdSeq = mdSeq;
	}

	@Column(name = "MD_LEVEL", precision = 22, scale = 0)
	public Integer getMdLevel() {
		return this.mdLevel;
	}

	public void setMdLevel(Integer mdLevel) {
		this.mdLevel = mdLevel;
	}

	@Column(name = "MD_FLAG", precision = 22, scale = 0)
	public Integer getMdFlag() {
		return this.mdFlag;
	}

	public void setMdFlag(Integer mdFlag) {
		this.mdFlag = mdFlag;
	}

	@Column(name = "MD_ICON", length = 100)
	public String getMdIcon() {
		return this.mdIcon;
	}

	public void setMdIcon(String mdIcon) {
		this.mdIcon = mdIcon;
	}

	@Column(name = "MD_TYPE", precision = 22, scale = 0)
	public Integer getMdType() {
		return this.mdType;
	}

	public void setMdType(Integer mdType) {
		this.mdType = mdType;
	}

	@Column(name = "MD_CONTROL", length = 100)
	public String getMdControl() {
		return this.mdControl;
	}

	public void setMdControl(String mdControl) {
		this.mdControl = mdControl;
	}

	@Column(name = "MD_NAME_ZH_CN", nullable = false, length = 100)
	public String getMdName_zh_CN() {
		return mdName_zh_CN;
	}

	public void setMdName_zh_CN(String mdName_zh_CN) {
		this.mdName_zh_CN = mdName_zh_CN;
	}

	@Column(name = "MD_NAME_EN", nullable = false, length = 100)
	public String getMdName_en() {
		return mdName_en;
	}

	public void setMdName_en(String mdName_en) {
		this.mdName_en = mdName_en;
	}

	@Column(name = "MD_NAME_RU", nullable = false, length = 100)
	public String getMdName_ru() {
		return mdName_ru;
	}

	public void setMdName_ru(String mdName_ru) {
		this.mdName_ru = mdName_ru;
	}

	@Column(name = "MD_SHOWNAME_ZH_CN", length = 100)
	public String getMdShowname_zh_CN() {
		return mdShowname_zh_CN;
	}

	public void setMdShowname_zh_CN(String mdShowname_zh_CN) {
		this.mdShowname_zh_CN = mdShowname_zh_CN;
	}

	@Column(name = "MD_SHOWNAME_EN", length = 100)
	public String getMdShowname_en() {
		return mdShowname_en;
	}

	public void setMdShowname_en(String mdShowname_en) {
		this.mdShowname_en = mdShowname_en;
	}

	@Column(name = "MD_SHOWNAME_RU", length = 100)
	public String getMdShowname_ru() {
		return mdShowname_ru;
	}

	public void setMdShowname_ru(String mdShowname_ru) {
		this.mdShowname_ru = mdShowname_ru;
	}

}