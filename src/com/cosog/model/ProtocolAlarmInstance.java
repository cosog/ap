package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *  <p>描述：协议报警实例 实体类  tbl_protocolinstance</p>
 *  
 * @author zhao  2021-09-12
 *
 */
@Entity
@Table(name = "tbl_protocolalarminstance")
public class ProtocolAlarmInstance implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String code;
	private Integer deviceType;
	private Integer alarmUnitId;
	private Integer sort;

	// Constructors

	/** default constructor */
	public ProtocolAlarmInstance() {
	}

	/** full constructor */
	public ProtocolAlarmInstance(Integer id, String name, String code, Integer deviceType, Integer alarmUnitId,Integer sort) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.deviceType = deviceType;
		this.alarmUnitId = alarmUnitId;
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

	@Column(name = "alarmUnitId", precision = 22, scale = 0)
	public Integer getAlarmUnitId() {
		return alarmUnitId;
	}

	public void setAlarmUnitId(Integer alarmUnitId) {
		this.alarmUnitId = alarmUnitId;
	}

	@Column(name = "sort", precision = 22, scale = 0)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}