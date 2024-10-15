package com.cosog.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_alarm_item2unit_conf")
public class AlarmUnitItem implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer unitId;
	private Integer itemId;
	private String itemName;
	private String itemCode;
	private Integer itemAddr;
	private Integer type;
	private Float value;
	private Integer bitIndex;
	private Float upperLimit;
	private Float lowerLimit;
	private Float hystersis;
	private Integer delay;
	private Integer retriggerTime;
	private Integer alarmLevel;
	private Integer alarmSign;
	private Integer isSendMessage;
	private Integer isSendMail;

	public AlarmUnitItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** full constructor */
	public AlarmUnitItem(Integer id, Integer unitId, Integer itemId, String itemName, String itemCode,
			Integer itemAddr, Integer type, Float value, Integer bitIndex, Float upperLimit, Float lowerLimit,
			Float hystersis, Integer delay, Integer retriggerTime, 
			Integer alarmLevel, Integer alarmSign, Integer isSendMessage, Integer isSendMail) {
		super();
		this.id = id;
		this.unitId = unitId;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemCode = itemCode;
		this.itemAddr = itemAddr;
		this.type = type;
		this.value = value;
		this.bitIndex = bitIndex;
		this.upperLimit = upperLimit;
		this.lowerLimit = lowerLimit;
		this.hystersis = hystersis;
		this.delay = delay;
		this.retriggerTime = retriggerTime;
		this.alarmLevel = alarmLevel;
		this.alarmSign = alarmSign;
		this.isSendMessage = isSendMessage;
		this.isSendMail = isSendMail;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "unitid", nullable = false, precision = 22, scale = 0)
	public Integer getUnitId() {
		return this.unitId;
	}
	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
	
	@Column(name = "itemid", nullable = true, precision = 22, scale = 0)
	public Integer getItemId() {
		return this.itemId;
	}
	
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@Column(name = "itemName", nullable = true, length = 8)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "itemCode", nullable = true, length = 8)
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name = "itemAddr", nullable = true, precision = 22, scale = 0)
	public Integer getItemAddr() {
		return itemAddr;
	}

	public void setItemAddr(Integer itemAddr) {
		this.itemAddr = itemAddr;
	}

	@Column(name = "upperLimit")
	public Float getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(Float upperLimit) {
		this.upperLimit = upperLimit;
	}

	@Column(name = "lowerLimit")
	public Float getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(Float lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	@Column(name = "hystersis")
	public Float getHystersis() {
		return hystersis;
	}

	public void setHystersis(Float hystersis) {
		this.hystersis = hystersis;
	}

	@Column(name = "delay")
	public Integer getDelay() {
		return delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	@Column(name = "alarmLevel")
	public Integer getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	@Column(name = "alarmSign")
	public Integer getAlarmSign() {
		return alarmSign;
	}

	public void setAlarmSign(Integer alarmSign) {
		this.alarmSign = alarmSign;
	}

	@Column(name = "type", nullable = true, precision = 22, scale = 0)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "value")
	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	@Column(name = "bitIndex", nullable = true, precision = 22, scale = 0)
	public Integer getBitIndex() {
		return bitIndex;
	}

	public void setBitIndex(Integer bitIndex) {
		this.bitIndex = bitIndex;
	}

	@Column(name = "isSendMessage")
	public Integer getIsSendMessage() {
		return isSendMessage;
	}

	public void setIsSendMessage(Integer isSendMessage) {
		this.isSendMessage = isSendMessage;
	}

	@Column(name = "isSendMail")
	public Integer getIsSendMail() {
		return isSendMail;
	}

	public void setIsSendMail(Integer isSendMail) {
		this.isSendMail = isSendMail;
	}

	@Column(name = "RETRIGGERTIME")
	public Integer getRetriggerTime() {
		return retriggerTime;
	}

	public void setRetriggerTime(Integer retriggerTime) {
		this.retriggerTime = retriggerTime;
	}

	
}