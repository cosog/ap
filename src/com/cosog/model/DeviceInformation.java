package com.cosog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
/**
 *  <p>描述：设备 实体类  tbl_device</p>
 *  
 * @author zhao  2024-01-12
 *
 */
@Entity
@Table(name = "tbl_device")
public class DeviceInformation implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer orgId;
	private String deviceName;
	private Integer deviceType;
	private Integer applicationScenarios;
	private Integer calculateType;
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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date commissioningDate;
	private Integer sortNum;
	

	// Constructors
	/** default constructor */
	public DeviceInformation() {
	}

	/** full constructor */
	public DeviceInformation(Integer id, Integer orgId, String deviceName, Integer deviceType,
			Integer applicationScenarios,Integer calculateType,
			String instanceCode, String alarmInstanceCode,String displayInstanceCode,String reportInstanceCode,  
			String tcpType, String signInId,String ipPort, String slave,
			Integer sortNum,
			Date commissioningDate) {
		super();
		this.id = id;
		this.orgId = orgId;
		this.deviceName = deviceName;
		this.deviceType = deviceType;
		this.applicationScenarios = applicationScenarios;
		this.calculateType = calculateType;
		this.instanceCode = instanceCode;
		this.displayInstanceCode = displayInstanceCode;
		this.reportInstanceCode = reportInstanceCode;
		this.alarmInstanceCode = alarmInstanceCode;
		this.tcpType = tcpType;
		this.signInId = signInId;
		this.ipPort = ipPort;
		this.slave = slave;
		this.sortNum = sortNum;
		this.commissioningDate = commissioningDate;
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

	@Column(name = "deviceType", precision = 22, scale = 0)
	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
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

	@Column(name = "applicationScenarios", precision = 22, scale = 0)
	public Integer getApplicationScenarios() {
		return applicationScenarios;
	}

	public void setApplicationScenarios(Integer applicationScenarios) {
		this.applicationScenarios = applicationScenarios;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "commissioningDate")
	public Date getCommissioningDate() {
		return commissioningDate;
	}

	public void setCommissioningDate(Date commissioningDate) {
		this.commissioningDate = commissioningDate;
	}

	@Column(name = "calculateType", precision = 22, scale = 0)
	public Integer getCalculateType() {
		return calculateType;
	}

	public void setCalculateType(Integer calculateType) {
		this.calculateType = calculateType;
	}
}