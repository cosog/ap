package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 *  <p>描述：短信设备信息 实体类  tbl_smsdevice</p>
 *  
 * @author zhao  2021-12-17
 *
 */
@Entity
@Table(name = "tbl_smsdevice")
public class SmsDeviceInformation implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer orgId;
	private String deviceName;
	private String instanceCode;
	private String signInId;
	private Integer sortNum;
	

	// Constructors
	/** default constructor */
	public SmsDeviceInformation() {
	}

	/** full constructor */
	public SmsDeviceInformation(Integer id, Integer orgId, String deviceName, String instanceCode, String signInId,
			Integer sortNum) {
		super();
		this.id = id;
		this.orgId = orgId;
		this.deviceName = deviceName;
		this.instanceCode = instanceCode;
		this.signInId = signInId;
		this.sortNum = sortNum;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "orgId", precision = 22, scale = 0)
	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name = "deviceName", nullable = false, length = 50)
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	@Column(name = "instanceCode", nullable = true, length = 50)
	public String getInstanceCode() {
		return instanceCode;
	}

	public void setInstanceCode(String instanceCode) {
		this.instanceCode = instanceCode;
	}

	@Column(name = "signInId", nullable = true, length = 50)
	public String getSignInId() {
		return signInId;
	}

	public void setSignInId(String signInId) {
		this.signInId = signInId;
	}

	@Column(name = "sortNum", precision = 22, scale = 0)
	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
}