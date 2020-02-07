package com.gao.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 晋城报表---单井日报 ReportSingleWellDaily entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "V_CBM_001_11_SINGLEWELLDAILY")
public class ReportSingleWellDaily implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer jlbh;
	private String jz;
	private String jc;
	private String jhh;
	private String jh;
	private Date scri;
	private Date tcri;
	private Integer sj;
	private String cpfs;
	private String dl;
	private Double qmsg;
	private Double mdsd;
	private Integer bj;
	private Double bx;
	private Double ccnj;
	private Double cczs;
	private Double ty;
	private Double gy;
	private Double ly;
	private Double dym;
	private String mthl;
	private Integer cq;
	private Double cs;
	private Integer ylq;
	private Double yls;
	private Integer lq;
	private Double ls;
	private Double cmd;
	private Double bs;
	private String bz;

	// Constructors

	/** default constructor */
	public ReportSingleWellDaily() {
	}

	@Id
	@GeneratedValue
	@Column(name = "JLBH", nullable = false, precision = 22, scale = 0)
	public Integer getJlbh() {
		return this.jlbh;
	}

	public void setJlbh(Integer jlbh) {
		this.jlbh = jlbh;
	}

	@Column(name = "JZ", length = 200)
	public String getJz() {
		return this.jz;
	}

	public void setJz(String jz) {
		this.jz = jz;
	}

	@Column(name = "JC", length = 200)
	public String getJc() {
		return this.jc;
	}

	public void setJc(String jc) {
		this.jc = jc;
	}

	@Column(name = "JHH", length = 200)
	public String getJhh() {
		return this.jhh;
	}

	public void setJhh(String jhh) {
		this.jhh = jhh;
	}

	@Column(name = "JH", nullable = false, length = 200)
	public String getJh() {
		return this.jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SCRI", length = 7)
	public Date getScri() {
		return this.scri;
	}

	public void setScri(Date scri) {
		this.scri = scri;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TCRI", length = 7)
	public Date getTcri() {
		return this.tcri;
	}

	public void setTcri(Date tcri) {
		this.tcri = tcri;
	}

	@Column(name = "SJ", precision = 22, scale = 0)
	public Integer getSj() {
		return this.sj;
	}

	public void setSj(Integer sj) {
		this.sj = sj;
	}

	@Column(name = "CPFS", length = 200)
	public String getCpfs() {
		return this.cpfs;
	}

	public void setCpfs(String cpfs) {
		this.cpfs = cpfs;
	}

	@Column(name = "DL", length = 200)
	public String getDl() {
		return this.dl;
	}

	public void setDl(String dl) {
		this.dl = dl;
	}

	@Column(name = "QMSG", precision = 8)
	public Double getQmsg() {
		return this.qmsg;
	}

	public void setQmsg(Double qmsg) {
		this.qmsg = qmsg;
	}

	@Column(name = "MDSD", precision = 8)
	public Double getMdsd() {
		return this.mdsd;
	}

	public void setMdsd(Double mdsd) {
		this.mdsd = mdsd;
	}

	@Column(name = "BJ", precision = 22, scale = 0)
	public Integer getBj() {
		return this.bj;
	}

	public void setBj(Integer bj) {
		this.bj = bj;
	}

	@Column(name = "BX", precision = 8)
	public Double getBx() {
		return this.bx;
	}

	public void setBx(Double bx) {
		this.bx = bx;
	}

	@Column(name = "CCNJ", precision = 8, scale = 1)
	public Double getCcnj() {
		return this.ccnj;
	}

	public void setCcnj(Double ccnj) {
		this.ccnj = ccnj;
	}

	@Column(name = "CCZS", precision = 8, scale = 1)
	public Double getCczs() {
		return this.cczs;
	}

	public void setCczs(Double cczs) {
		this.cczs = cczs;
	}

	@Column(name = "TY", precision = 8, scale = 3)
	public Double getTy() {
		return this.ty;
	}

	public void setTy(Double ty) {
		this.ty = ty;
	}

	@Column(name = "GY", precision = 8, scale = 3)
	public Double getGy() {
		return this.gy;
	}

	public void setGy(Double gy) {
		this.gy = gy;
	}

	@Column(name = "LY")
	public Double getLy() {
		return this.ly;
	}

	public void setLy(Double ly) {
		this.ly = ly;
	}

	@Column(name = "DYM")
	public Double getDym() {
		return this.dym;
	}

	public void setDym(Double dym) {
		this.dym = dym;
	}

	@Column(name = "MTHL", length = 500)
	public String getMthl() {
		return this.mthl;
	}

	public void setMthl(String mthl) {
		this.mthl = mthl;
	}

	@Column(name = "CQ")
	public Integer getCq() {
		return this.cq;
	}

	public void setCq(Integer cq) {
		this.cq = cq;
	}

	@Column(name = "CS")
	public Double getCs() {
		return this.cs;
	}

	public void setCs(Double cs) {
		this.cs = cs;
	}

	@Column(name = "YLQ")
	public Integer getYlq() {
		return this.ylq;
	}

	public void setYlq(Integer ylq) {
		this.ylq = ylq;
	}

	@Column(name = "YLS", precision = 8, scale = 1)
	public Double getYls() {
		return this.yls;
	}

	public void setYls(Double yls) {
		this.yls = yls;
	}

	@Column(name = "LQ", precision = 22, scale = 0)
	public Integer getLq() {
		return this.lq;
	}

	public void setLq(Integer lq) {
		this.lq = lq;
	}

	@Column(name = "LS")
	public Double getLs() {
		return this.ls;
	}

	public void setLs(Double ls) {
		this.ls = ls;
	}

	@Column(name = "CMD")
	public Double getCmd() {
		return this.cmd;
	}

	public void setCmd(Double cmd) {
		this.cmd = cmd;
	}

	@Column(name = "BS", precision = 8)
	public Double getBs() {
		return this.bs;
	}

	public void setBs(Double bs) {
		this.bs = bs;
	}

	@Column(name = "BZ", length = 500)
	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}