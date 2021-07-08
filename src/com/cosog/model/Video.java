package com.cosog.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
//@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "T_013_VIDEO")
public class Video implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer jlbh;
	private String dwbh;
	private String yqcbh;
	private String jc;
	private String spdz;
	private String yt;
	private String sxt;
	private String bb;
	private String jj;
	private String gq;

	public Video() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue
	public Integer getJlbh() {
		return jlbh;
	}

	public void setJlbh(Integer jlbh) {
		this.jlbh = jlbh;
	}

	public String getDwbh() {
		return dwbh;
	}

	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
	}

	public String getYqcbh() {
		return yqcbh;
	}

	public void setYqcbh(String yqcbh) {
		this.yqcbh = yqcbh;
	}

	public String getJc() {
		return jc;
	}

	public void setJc(String jc) {
		this.jc = jc;
	}

	public String getSpdz() {
		return spdz;
	}

	public void setSpdz(String spdz) {
		this.spdz = spdz;
	}

	public String getYt() {
		return yt;
	}

	public void setYt(String yt) {
		this.yt = yt;
	}

	public String getSxt() {
		return sxt;
	}

	public void setSxt(String sxt) {
		this.sxt = sxt;
	}

	public String getBb() {
		return bb;
	}

	public void setBb(String bb) {
		this.bb = bb;
	}

	public String getJj() {
		return jj;
	}

	public void setJj(String jj) {
		this.jj = jj;
	}

	public String getGq() {
		return gq;
	}

	public void setGq(String gq) {
		this.gq = gq;
	}
}
