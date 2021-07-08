package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 后台管理-数据配置－设备管理-抽油机1
 * Pumpingunit entity. @ding
 */
@Entity
@Table(name = "T_PUMPINGUNIT")
public class Pumpingunit implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer jlbh;
	private String sccj;
	private String cyjxh;
	private Double xdedzh;
	private Double jsqednj;
	private Double lgc;
	private Double qbc;
	private Double hbc;
	private Double dkqbzl;
	private Double qbzxbj;
	private Double xdjl;
	private Double hg;
	private Double jgbphz;
	private String dkphkzl;
	private Double qbpzj;
	private Double jsxcdb;
	private Double jsxpdlzj;
	private Integer xzfx;
	private Double zdtzjl;
	private Integer njysjsxz;
	private Integer phkzdks;
	private Integer cyjlx;
	private Double xpglr;
	private Double xpgldkzl;
	private Integer xpglzdks;
	private Double pdxl;
	private Double jsxxl;
	private Double slgxl;

	// Constructors

	/** default constructor */
	public Pumpingunit() {
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

	@Column(name = "SCCJ", length = 200)
	public String getSccj() {
		return this.sccj;
	}

	public void setSccj(String sccj) {
		this.sccj = sccj;
	}

	@Column(name = "CYJXH", length = 200)
	public String getCyjxh() {
		return this.cyjxh;
	}

	public void setCyjxh(String cyjxh) {
		this.cyjxh = cyjxh;
	}

	@Column(name = "XDEDZH", precision = 8)
	public Double getXdedzh() {
		return this.xdedzh;
	}

	public void setXdedzh(Double xdedzh) {
		this.xdedzh = xdedzh;
	}

	@Column(name = "JSQEDNJ", precision = 8)
	public Double getJsqednj() {
		return this.jsqednj;
	}

	public void setJsqednj(Double jsqednj) {
		this.jsqednj = jsqednj;
	}

	@Column(name = "LGC", precision = 8)
	public Double getLgc() {
		return this.lgc;
	}

	public void setLgc(Double lgc) {
		this.lgc = lgc;
	}

	@Column(name = "QBC", precision = 8)
	public Double getQbc() {
		return this.qbc;
	}

	public void setQbc(Double qbc) {
		this.qbc = qbc;
	}

	@Column(name = "HBC", precision = 8)
	public Double getHbc() {
		return this.hbc;
	}

	public void setHbc(Double hbc) {
		this.hbc = hbc;
	}

	@Column(name = "DKQBZL", precision = 8)
	public Double getDkqbzl() {
		return this.dkqbzl;
	}

	public void setDkqbzl(Double dkqbzl) {
		this.dkqbzl = dkqbzl;
	}

	@Column(name = "QBZXBJ", precision = 8)
	public Double getQbzxbj() {
		return this.qbzxbj;
	}

	public void setQbzxbj(Double qbzxbj) {
		this.qbzxbj = qbzxbj;
	}

	@Column(name = "XDJL", precision = 8)
	public Double getXdjl() {
		return this.xdjl;
	}

	public void setXdjl(Double xdjl) {
		this.xdjl = xdjl;
	}

	@Column(name = "HG", precision = 8)
	public Double getHg() {
		return this.hg;
	}

	public void setHg(Double hg) {
		this.hg = hg;
	}

	@Column(name = "JGBPHZ", precision = 8)
	public Double getJgbphz() {
		return this.jgbphz;
	}

	public void setJgbphz(Double jgbphz) {
		this.jgbphz = jgbphz;
	}

	@Column(name = "DKPHKZL", length = 800)
	public String getDkphkzl() {
		return this.dkphkzl;
	}

	public void setDkphkzl(String dkphkzl) {
		this.dkphkzl = dkphkzl;
	}

	@Column(name = "QBPZJ", precision = 8)
	public Double getQbpzj() {
		return this.qbpzj;
	}

	public void setQbpzj(Double qbpzj) {
		this.qbpzj = qbpzj;
	}

	@Column(name = "JSXCDB", precision = 8)
	public Double getJsxcdb() {
		return this.jsxcdb;
	}

	public void setJsxcdb(Double jsxcdb) {
		this.jsxcdb = jsxcdb;
	}

	@Column(name = "JSXPDLZJ", precision = 8)
	public Double getJsxpdlzj() {
		return this.jsxpdlzj;
	}

	public void setJsxpdlzj(Double jsxpdlzj) {
		this.jsxpdlzj = jsxpdlzj;
	}

	@Column(name = "XZFX", precision = 22, scale = 0)
	public Integer getXzfx() {
		return this.xzfx;
	}

	public void setXzfx(Integer xzfx) {
		this.xzfx = xzfx;
	}

	@Column(name = "ZDTZJL", precision = 8)
	public Double getZdtzjl() {
		return this.zdtzjl;
	}

	public void setZdtzjl(Double zdtzjl) {
		this.zdtzjl = zdtzjl;
	}

	@Column(name = "NJYSJSXZ", precision = 22, scale = 0)
	public Integer getNjysjsxz() {
		return this.njysjsxz;
	}

	public void setNjysjsxz(Integer njysjsxz) {
		this.njysjsxz = njysjsxz;
	}

	@Column(name = "PHKZDKS", precision = 22, scale = 0)
	public Integer getPhkzdks() {
		return this.phkzdks;
	}

	public void setPhkzdks(Integer phkzdks) {
		this.phkzdks = phkzdks;
	}

	@Column(name = "CYJLX", precision = 22, scale = 0)
	public Integer getCyjlx() {
		return this.cyjlx;
	}

	public void setCyjlx(Integer cyjlx) {
		this.cyjlx = cyjlx;
	}

	@Column(name = "XPGLR", precision = 8)
	public Double getXpglr() {
		return this.xpglr;
	}

	public void setXpglr(Double xpglr) {
		this.xpglr = xpglr;
	}

	@Column(name = "XPGLDKZL", precision = 8)
	public Double getXpgldkzl() {
		return this.xpgldkzl;
	}

	public void setXpgldkzl(Double xpgldkzl) {
		this.xpgldkzl = xpgldkzl;
	}

	@Column(name = "XPGLZDKS", precision = 22, scale = 0)
	public Integer getXpglzdks() {
		return this.xpglzdks;
	}

	public void setXpglzdks(Integer xpglzdks) {
		this.xpglzdks = xpglzdks;
	}

	@Column(name = "PDXL", precision = 8)
	public Double getPdxl() {
		return this.pdxl;
	}

	public void setPdxl(Double pdxl) {
		this.pdxl = pdxl;
	}

	@Column(name = "JSXXL", precision = 8)
	public Double getJsxxl() {
		return this.jsxxl;
	}

	public void setJsxxl(Double jsxxl) {
		this.jsxxl = jsxxl;
	}

	@Column(name = "SLGXL", precision = 8)
	public Double getSlgxl() {
		return this.slgxl;
	}

	public void setSlgxl(Double slgxl) {
		this.slgxl = slgxl;
	}

}