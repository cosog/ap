package com.cosog.model;



import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  <p>描述：码表信息 实体类  tbl_code</p>
 *  
 * @author gao  2014-06-10
 *
 */
@Entity
@Table(name = "tbl_code")
public class Code implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String tablecode;
	private String itemcode;
	private Integer itemvalue;
	private String itemname;
	private String remark;

	
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "tablecode", length = 200)
	public String getTablecode() {
		return this.tablecode;
	}

	public void setTablecode(String tablecode) {
		this.tablecode = tablecode;
	}

	@Column(name = "itemcode", length = 200)
	public String getItemcode() {
		return this.itemcode;
	}

	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}

	@Column(name = "itemvalue", precision = 22, scale = 0)
	public Integer getItemvalue() {
		return this.itemvalue;
	}

	public void setItemvalue(Integer itemvalue) {
		this.itemvalue = itemvalue;
	}

	@Column(name = "itemname", length = 200)
	public String getItemname() {
		return this.itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}