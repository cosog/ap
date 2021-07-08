package com.cosog.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 后台管理-数据配置－设备管理-抽油机2 Stroke entity. @author ding
 */
@Entity
@Table(name = "T_STROKE")
public class Stroke implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer jlbh;
	private Integer cyjbh;
	private Double cc;
	private Double qbkj;
	private Blob wzjnjys;

	// Constructors

	/** default constructor */
	public Stroke() {
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "JLBH", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getJlbh() {
		return this.jlbh;
	}

	public void setJlbh(Integer jlbh) {
		this.jlbh = jlbh;
	}

	@Column(name = "CYJBH", length = 200)
	public Integer getCyjbh() {
		return this.cyjbh;
	}

	public void setCyjbh(Integer cyjbh) {
		this.cyjbh = cyjbh;
	}

	@Column(name = "CC", precision = 8)
	public Double getCc() {
		return this.cc;
	}

	public void setCc(Double cc) {
		this.cc = cc;
	}

	@Column(name = "QBKJ", precision = 8)
	public Double getQbkj() {
		return this.qbkj;
	}

	public void setQbkj(Double qbkj) {
		this.qbkj = qbkj;
	}
	
	@Column(name = "WZJNJYS")
	public Blob getWzjnjys() {
		return this.wzjnjys;
	}

	public void setWzjnjys(Blob wzjnjys) {
		this.wzjnjys = wzjnjys;
	}

}