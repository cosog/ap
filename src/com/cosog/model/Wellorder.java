package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  <p>描述：井名排序 实体类  T_WELLORDER</p>
 *  
 * @author gao  2014-06-10
 *
 */
@Entity
@Table(name = "T_WELLORDER")
public class Wellorder implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer jlbh;
	private String jh;
	private Integer pxbh;

	// Constructors

	/** default constructor */
	public Wellorder() {
	}

	/** minimal constructor */
	public Wellorder(Integer jlbh) {
		this.jlbh = jlbh;
	}

	/** full constructor */
	public Wellorder(Integer jlbh, String jh, Integer pxbh) {
		this.jlbh = jlbh;
		this.jh = jh;
		this.pxbh = pxbh;
	}

	// Property accessors
	@Id
	@Column(name = "JLBH", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getJlbh() {
		return this.jlbh;
	}

	public void setJlbh(Integer jlbh) {
		this.jlbh = jlbh;
	}

	@Column(name = "JH", length = 200)
	public String getJh() {
		return this.jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	@Column(name = "PXBH", precision = 22, scale = 0)
	public Integer getPxbh() {
		return this.pxbh;
	}

	public void setPxbh(Integer pxbh) {
		this.pxbh = pxbh;
	}

}