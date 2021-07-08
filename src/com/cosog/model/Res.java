package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ScRes entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SC_RES")
public class Res implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer resId;
	private String resCode;
	private Integer resParent;
	private Integer resSeq;
	private String resName;
	private String resMemo;
	private Integer resLevel;

	// Constructors

	/** default constructor */
	public Res() {
	}

	/** full constructor */
	public Res(String resCode, Integer resParent, Integer resSeq,
			String resName, String resMemo, Integer resLevel) {
		this.resCode = resCode;
		this.resParent = resParent;
		this.resSeq = resSeq;
		this.resName = resName;
		this.resMemo = resMemo;
		this.resLevel = resLevel;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "RES_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getResId() {
		return this.resId;
	}

	public void setResId(Integer resId) {
		this.resId = resId;
	}

	@Column(name = "RES_CODE", length = 200)
	public String getResCode() {
		return this.resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	@Column(name = "RES_PARENT", precision = 22, scale = 0)
	public Integer getResParent() {
		return this.resParent;
	}

	public void setResParent(Integer resParent) {
		this.resParent = resParent;
	}

	@Column(name = "RES_SEQ", precision = 22, scale = 0)
	public Integer getResSeq() {
		return this.resSeq;
	}

	public void setResSeq(Integer resSeq) {
		this.resSeq = resSeq;
	}

	@Column(name = "RES_NAME", length = 200)
	public String getResName() {
		return this.resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	@Column(name = "RES_MEMO", length = 300)
	public String getResMemo() {
		return this.resMemo;
	}

	public void setResMemo(String resMemo) {
		this.resMemo = resMemo;
	}

	@Column(name = "RES_LEVEL", precision = 22, scale = 0)
	public Integer getResLevel() {
		return this.resLevel;
	}

	public void setResLevel(Integer resLevel) {
		this.resLevel = resLevel;
	}

}