package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 后台管理-数据配置－设备管理-抽油泵
 * Pump entity. @author ding
 */
@Entity
@Table(name = "T_PUMP")
public class Pump implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer jlbh;
	private String sccj;
	private String cybxh;
	private Integer blx;
	private Integer bjb;
	private Double bj;
	private Double zsc;
	// Constructors
	/** default constructor */
	public Pump() {
	}

	/** minimal constructor */
	public Pump(Integer jlbh) {
		this.jlbh = jlbh;
	}

	public void initPump(){
		if(this.sccj==null)
			this.sccj="";
		if(this.cybxh==null)
			this.cybxh="";
		if(this.blx==null)
			this.blx=1;
		if(this.bjb==null)
			this.bjb=1;
		if(this.bj==null)
			this.bj=0.0;
		if(this.zsc==null)
			this.zsc=0.0;
	}
	
	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "JLBH", nullable = false, insertable = true, updatable = true, length = 32)
	public Integer getJlbh() {
		return this.jlbh;
	}

	public void setJlbh(Integer jlbh) {
		this.jlbh = jlbh;
	}

	@Column(name = "SCCJ", length = 200)
	public String getSccj() {
		return this.sccj;
	}

	public void setSccj(String sccj) {
		this.sccj = sccj;
	}

	@Column(name = "CYBXH", length = 200)
	public String getCybxh() {
		return this.cybxh;
	}

	public void setCybxh(String cybxh) {
		this.cybxh = cybxh;
	}

	@Column(name = "BLX", precision = 22, scale = 0)
	public Integer getBlx() {
		return this.blx;
	}

	public void setBlx(Integer blx) {
		this.blx = blx;
	}

	@Column(name = "BJB", precision = 22, scale = 0)
	public Integer getBjb() {
		return this.bjb;
	}

	public void setBjb(Integer bjb) {
		this.bjb = bjb;
	}

	@Column(name = "BJ", precision = 8)
	public Double getBj() {
		return this.bj;
	}

	public void setBj(Double bj) {
		this.bj = bj;
	}

	@Column(name = "ZSC", precision = 8)
	public Double getZsc() {
		return this.zsc;
	}

	public void setZsc(Double zsc) {
		this.zsc = zsc;
	}

}