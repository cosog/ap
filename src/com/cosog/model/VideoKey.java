package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *  <p>描述：视频密钥 实体类  TBL_VIDEOKEY</p>
 *  
 * @author zhao  2023-07-04
 *
 */
@Entity
@Table(name = "TBL_VIDEOKEY")
public class VideoKey implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer orgId;
	private String account;
	private String appkey;
	private String secret;

	// Constructors

	/** default constructor */
	public VideoKey() {
	}

	/** full constructor */
	public VideoKey(Integer id, Integer orgId, String account, String appkey, String secret) {
		super();
		this.id = id;
		this.orgId = orgId;
		this.account = account;
		this.appkey = appkey;
		this.secret = secret;
	}

	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ORGID", nullable = false, length = 10)
	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name = "ACCOUNT", nullable = false, length = 200)
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "APPKEY", nullable = false, length = 400)
	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	@Column(name = "SECRET", nullable = false, length = 400)
	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
}