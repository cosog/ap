package com.cosog.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_acq_item2group_conf")
public class AcquisitionGroupItem implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer groupId;
	private Integer itemId;
	private String itemName;
	private String itemCode;
	private String matrix;
	private Integer bitIndex;
	private Integer dailyTotalCalculate;
	private String dailyTotalCalculateName;

	public AcquisitionGroupItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** full constructor */
	public AcquisitionGroupItem(Integer id, Integer groupId, Integer itemId, String itemName, String itemCode,
			String matrix, Integer bitIndex, 
			Integer dailyTotalCalculate, String dailyTotalCalculateName) {
		super();
		this.id = id;
		this.groupId = groupId;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemCode = itemCode;
		this.matrix = matrix;
		this.bitIndex = bitIndex;
		this.dailyTotalCalculate = dailyTotalCalculate;
		this.dailyTotalCalculateName = dailyTotalCalculateName;
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

	@Column(name = "matrix", nullable = true, length = 8)
	public String getMatrix() {
		return this.matrix;
	}
	
	public void setMatrix(String matrix) {
		this.matrix = matrix;
	}

	@Column(name = "groupid", nullable = false, precision = 22, scale = 0)
	public Integer getGroupId() {
		return this.groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
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

	@Column(name = "bitIndex")
	public Integer getBitIndex() {
		return bitIndex;
	}

	public void setBitIndex(Integer bitIndex) {
		this.bitIndex = bitIndex;
	}

	@Column(name = "dailyTotalCalculate")
	public Integer getDailyTotalCalculate() {
		return dailyTotalCalculate;
	}

	public void setDailyTotalCalculate(Integer dailyTotalCalculate) {
		this.dailyTotalCalculate = dailyTotalCalculate;
	}

	@Column(name = "dailyTotalCalculateName")
	public String getDailyTotalCalculateName() {
		return dailyTotalCalculateName;
	}

	public void setDailyTotalCalculateName(String dailyTotalCalculateName) {
		this.dailyTotalCalculateName = dailyTotalCalculateName;
	}
}