package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 *  <p>描述：抽油机井 实体类  tbl_srpdevice</p>
 *  
 * @author zhao  2021-12-17
 *
 */
@Entity
@Table(name = "tbl_srpdevice")
public class SRPDeviceInformation implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer orgId;
	private String wellName;
	private Integer deviceType;
	private Integer applicationScenarios;
	private String instanceCode;
	private String displayInstanceCode;
	private String reportInstanceCode;
	private String alarmInstanceCode;
	private String tcpType;
	private String signInId;
	private String ipPort;
	private String slave;
	private Integer peakDelay;
	private Integer status;
	private Integer sortNum;
	

	// Constructors
	/** default constructor */
	public SRPDeviceInformation() {
	}

	/** full constructor */
	public SRPDeviceInformation(Integer id, Integer orgId, String wellName, Integer deviceType,
			Integer applicationScenarios, 
			String instanceCode, String alarmInstanceCode,String displayInstanceCode,String reportInstanceCode,  
			String tcpType, String signInId,String ipPort, String slave,
			Integer sortNum) {
		super();
		this.id = id;
		this.orgId = orgId;
		this.wellName = wellName;
		this.deviceType = deviceType;
		this.applicationScenarios = applicationScenarios;
		this.instanceCode = instanceCode;
		this.displayInstanceCode = displayInstanceCode;
		this.reportInstanceCode = reportInstanceCode;
		this.alarmInstanceCode = alarmInstanceCode;
		this.tcpType = tcpType;
		this.signInId = signInId;
		this.ipPort = ipPort;
		this.slave = slave;
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

	@Column(name = "wellName", nullable = false, length = 50)
	public String getWellName() {
		return wellName;
	}

	public void setWellName(String wellName) {
		this.wellName = wellName;
	}

	@Column(name = "deviceType", precision = 22, scale = 0)
	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	@Column(name = "applicationScenarios", precision = 22, scale = 0)
	public Integer getApplicationScenarios() {
		return applicationScenarios;
	}

	public void setApplicationScenarios(Integer applicationScenarios) {
		this.applicationScenarios = applicationScenarios;
	}

	@Column(name = "instanceCode", nullable = true, length = 50)
	public String getInstanceCode() {
		return instanceCode;
	}

	public void setInstanceCode(String instanceCode) {
		this.instanceCode = instanceCode;
	}
	
	@Column(name = "displayInstanceCode", nullable = true, length = 50)
	public String getDisplayInstanceCode() {
		return displayInstanceCode;
	}

	public void setDisplayInstanceCode(String displayInstanceCode) {
		this.displayInstanceCode = displayInstanceCode;
	}

	@Column(name = "alarmInstanceCode", nullable = true, length = 50)
	public String getAlarmInstanceCode() {
		return alarmInstanceCode;
	}

	public void setAlarmInstanceCode(String alarmInstanceCode) {
		this.alarmInstanceCode = alarmInstanceCode;
	}

	@Column(name = "signInId", nullable = true, length = 50)
	public String getSignInId() {
		return signInId;
	}

	public void setSignInId(String signInId) {
		this.signInId = signInId;
	}

	@Column(name = "slave", nullable = true, length = 50)
	public String getSlave() {
		return slave;
	}

	public void setSlave(String slave) {
		this.slave = slave;
	}

	@Column(name = "sortNum", precision = 22, scale = 0)
	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	@Column(name = "status", precision = 22, scale = 0)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "TCPTYPE", nullable = true, length = 50)
	public String getTcpType() {
		return tcpType;
	}

	public void setTcpType(String tcpType) {
		this.tcpType = tcpType;
	}

	@Column(name = "peakDelay", precision = 22, scale = 0)
	public Integer getPeakDelay() {
		return peakDelay;
	}

	public void setPeakDelay(Integer peakDelay) {
		this.peakDelay = peakDelay;
	}

	@Column(name = "ipPort", nullable = true, length = 50)
	public String getIpPort() {
		return ipPort;
	}

	public void setIpPort(String ipPort) {
		this.ipPort = ipPort;
	}

	@Column(name = "reportInstanceCode", nullable = true, length = 50)
	public String getReportInstanceCode() {
		return reportInstanceCode;
	}

	public void setReportInstanceCode(String reportInstanceCode) {
		this.reportInstanceCode = reportInstanceCode;
	}
}