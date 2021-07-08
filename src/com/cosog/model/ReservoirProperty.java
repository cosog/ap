package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "T_RESERVOIRPROPERTY")
public class ReservoirProperty implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer jlbh;
	private String resName;
	private Double yymd;
	private Double smd;
	private Double trqxdmd;
	//private Double ysrjqyb;
	private Double bhyl;
	//private Double dmtqyynd;
	private Double yqcyl;
	private Double yqczbsd;
	private Double yqczbwd;

	// Constructors

	/** default constructor */
	public ReservoirProperty() {
	}

	/** full constructor */
	public ReservoirProperty(String resName, Double yymd, Double smd,
			Double trqxdmd, 
			//Double ysrjqyb, 
			Double bhyl, 
			//Double dmtqyynd,
			Double yqcyl, Double yqczbsd, Double yqczbwd) {
		this.resName=resName;
		this.yymd = yymd;
		this.smd = smd;
		this.trqxdmd = trqxdmd;
		//this.ysrjqyb = ysrjqyb;
		this.bhyl = bhyl;
		//this.dmtqyynd = dmtqyynd;
		this.yqcyl = yqcyl;
		this.yqczbsd = yqczbsd;
		this.yqczbwd = yqczbwd;
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

	@Column(name = "RESNAME", length = 200)
	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}


	@Column(name = "YYMD", precision = 8)
	public Double getYymd() {
		return this.yymd;
	}

	public void setYymd(Double yymd) {
		this.yymd = yymd;
	}

	@Column(name = "SMD", precision = 8)
	public Double getSmd() {
		return this.smd;
	}

	public void setSmd(Double smd) {
		this.smd = smd;
	}

	@Column(name = "TRQXDMD", precision = 8)
	public Double getTrqxdmd() {
		return this.trqxdmd;
	}

	public void setTrqxdmd(Double trqxdmd) {
		this.trqxdmd = trqxdmd;
	}

//	@Column(name = "YSRJQYB", precision = 8)
//	public Double getYsrjqyb() {
//		return this.ysrjqyb;
//	}

//	public void setYsrjqyb(Double ysrjqyb) {
//		this.ysrjqyb = ysrjqyb;
//	}

	@Column(name = "BHYL", precision = 8)
	public Double getBhyl() {
		return this.bhyl;
	}

	public void setBhyl(Double bhyl) {
		this.bhyl = bhyl;
	}

//	@Column(name = "DMTQYYND", precision = 8)
//	public Double getDmtqyynd() {
//		return this.dmtqyynd;
//	}

//	public void setDmtqyynd(Double dmtqyynd) {
//		this.dmtqyynd = dmtqyynd;
//	}

	@Column(name = "YQCYL", precision = 8)
	public Double getYqcyl() {
		return this.yqcyl;
	}

	public void setYqcyl(Double yqcyl) {
		this.yqcyl = yqcyl;
	}

	@Column(name = "YQCZBSD", precision = 8)
	public Double getYqczbsd() {
		return this.yqczbsd;
	}

	public void setYqczbsd(Double yqczbsd) {
		this.yqczbsd = yqczbsd;
	}

	@Column(name = "YQCZBWD", precision = 8)
	public Double getYqczbwd() {
		return this.yqczbwd;
	}

	public void setYqczbwd(Double yqczbwd) {
		this.yqczbwd = yqczbwd;
	}

}