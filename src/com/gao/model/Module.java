package com.gao.model;

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
	private String mdName;
	private String mdShowname;
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

	/** minimal constructor */
	public Module(Integer mdParentid, String mdName) {
		this.mdParentid = mdParentid;
		this.mdName = mdName;
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

	@Column(name = "MD_NAME", nullable = false, length = 100)
	public String getMdName() {
		return this.mdName;
	}

	public void setMdName(String mdName) {
		this.mdName = mdName;
	}

	@Column(name = "MD_SHOWNAME", length = 100)
	public String getMdShowname() {
		return this.mdShowname;
	}

	public void setMdShowname(String mdShowname) {
		this.mdShowname = mdShowname;
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

}