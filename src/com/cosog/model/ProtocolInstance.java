package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *  <p>描述：协议实例 实体类  tbl_protocolinstance</p>
 *  
 * @author zhao  2021-08-30
 *
 */
@Entity
@Table(name = "tbl_protocolinstance")
public class ProtocolInstance implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String code;
	private Integer deviceType;
	private Integer unitId;
	private String acqProtocolType;
	private String ctrlProtocolType;
	private String signInPrefix;
	private String signInSuffix;
	private String heartbeatPrefix;
	private String heartbeatSuffix;
	private Integer packetSendInterval;
	private Integer sort;

	// Constructors

	/** default constructor */
	public ProtocolInstance() {
	}

	/** full constructor */
	public ProtocolInstance(Integer id, String name, String code, Integer deviceType, Integer unitId,
			String acqProtocolType, String ctrlProtocolType, String signInPrefix, String signInSuffix,
			String heartbeatPrefix, String heartbeatSuffix, Integer sort) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.deviceType = deviceType;
		this.unitId = unitId;
		this.acqProtocolType = acqProtocolType;
		this.ctrlProtocolType = ctrlProtocolType;
		this.signInPrefix = signInPrefix;
		this.signInSuffix = signInSuffix;
		this.heartbeatPrefix = heartbeatPrefix;
		this.heartbeatSuffix = heartbeatSuffix;
		this.sort = sort;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "code", nullable = true, length = 50)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "devicetype", precision = 22, scale = 0)
	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	@Column(name = "unitId", precision = 22, scale = 0)
	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	@Column(name = "acqProtocolType", nullable = false, length = 50)
	public String getAcqProtocolType() {
		return acqProtocolType;
	}

	public void setAcqProtocolType(String acqProtocolType) {
		this.acqProtocolType = acqProtocolType;
	}

	@Column(name = "ctrlProtocolType", nullable = false, length = 50)
	public String getCtrlProtocolType() {
		return ctrlProtocolType;
	}

	public void setCtrlProtocolType(String ctrlProtocolType) {
		this.ctrlProtocolType = ctrlProtocolType;
	}

	@Column(name = "signInPrefix", nullable = true, length = 50)
	public String getSignInPrefix() {
		return signInPrefix;
	}

	public void setSignInPrefix(String signInPrefix) {
		this.signInPrefix = signInPrefix;
	}

	@Column(name = "signInSuffix", nullable = true, length = 50)
	public String getSignInSuffix() {
		return signInSuffix;
	}

	public void setSignInSuffix(String signInSuffix) {
		this.signInSuffix = signInSuffix;
	}

	@Column(name = "heartbeatPrefix", nullable = true, length = 50)
	public String getHeartbeatPrefix() {
		return heartbeatPrefix;
	}

	public void setHeartbeatPrefix(String heartbeatPrefix) {
		this.heartbeatPrefix = heartbeatPrefix;
	}

	@Column(name = "heartbeatSuffix", nullable = true, length = 50)
	public String getHeartbeatSuffix() {
		return heartbeatSuffix;
	}

	public void setHeartbeatSuffix(String heartbeatSuffix) {
		this.heartbeatSuffix = heartbeatSuffix;
	}

	@Column(name = "sort", precision = 22, scale = 0)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getPacketSendInterval() {
		return packetSendInterval;
	}

	public void setPacketSendInterval(Integer packetSendInterval) {
		this.packetSendInterval = packetSendInterval;
	}
	
	

}