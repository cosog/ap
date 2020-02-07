package com.gao.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *  <p>描述：权限信息 实体类  SC_RIGHT</p>
 *  
 * @author gao  2014-06-10
 *
 */
@Entity
@Table(name = "SC_RIGHT")
public class Right implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer rtRightid;
	private String rtRolecode;
	private Integer rtUserNo;

	public Right() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue
	@Column(name = "RT_RIGHTID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getRtRightid() {
		return this.rtRightid;
	}

	@Column(name = "RT_ROLECODE", nullable = false, length = 20)
	public String getRtRolecode() {
		return this.rtRolecode;
	}

	@Column(name = "RT_USERNO", nullable = false, precision = 22, scale = 0)
	public Integer getRtUserNo() {
		return rtUserNo;
	}

	public void setRtRightid(Integer rtRightid) {
		this.rtRightid = rtRightid;
	}

	public void setRtRolecode(String rtRolecode) {
		this.rtRolecode = rtRolecode;
	}

	public void setRtUserNo(Integer rtUserNo) {
		this.rtUserNo = rtUserNo;
	}

}