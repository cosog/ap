package com.cosog.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_display_items2unit_conf")
public class DisplayUnitItem implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer unitId;
	private Integer itemId;
	private String itemName;
	private String itemCode;
	private String matrix;
	private Integer showLevel;
	private Integer sort;
	private Integer bitIndex;
	private Integer realtimeCurve;
	private Integer historyCurve;
	private String realtimeCurveColor;
	private String historyCurveColor;
	private Integer type;

	public DisplayUnitItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** full constructor */
	public DisplayUnitItem(Integer id, Integer unitId, Integer itemId, String itemName, String itemCode,
			String matrix, Integer showLevel, Integer sort, Integer bitIndex, Integer realtimeCurve,
			Integer historyCurve, String realtimeCurveColor, String historyCurveColor) {
		super();
		this.id = id;
		this.unitId = unitId;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemCode = itemCode;
		this.matrix = matrix;
		this.showLevel = showLevel;
		this.sort = sort;
		this.bitIndex = bitIndex;
		this.realtimeCurve = realtimeCurve;
		this.historyCurve = historyCurve;
		this.realtimeCurveColor = realtimeCurveColor;
		this.historyCurveColor = historyCurveColor;
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

	@Column(name = "unitId", nullable = false, precision = 22, scale = 0)
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
	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "bitIndex")
	public Integer getBitIndex() {
		return bitIndex;
	}

	public void setBitIndex(Integer bitIndex) {
		this.bitIndex = bitIndex;
	}

	@Column(name = "showLevel")
	public Integer getShowLevel() {
		return showLevel;
	}

	public void setShowLevel(Integer showLevel) {
		this.showLevel = showLevel;
	}

	@Column(name = "realtimeCurve")
	public Integer getRealtimeCurve() {
		return realtimeCurve;
	}

	public void setRealtimeCurve(Integer realtimeCurve) {
		this.realtimeCurve = realtimeCurve;
	}

	@Column(name = "historyCurve")
	public Integer getHistoryCurve() {
		return historyCurve;
	}

	public void setHistoryCurve(Integer historyCurve) {
		this.historyCurve = historyCurve;
	}

	@Column(name = "realtimeCurveColor", nullable = true, length = 8)
	public String getRealtimeCurveColor() {
		return realtimeCurveColor;
	}

	public void setRealtimeCurveColor(String realtimeCurveColor) {
		this.realtimeCurveColor = realtimeCurveColor;
	}

	@Column(name = "historyCurveColor", nullable = true, length = 8)
	public String getHistoryCurveColor() {
		return historyCurveColor;
	}

	public void setHistoryCurveColor(String historyCurveColor) {
		this.historyCurveColor = historyCurveColor;
	}

	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	
}