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
	private Integer realtimeSort;
	private Integer historySort;
	private Integer bitIndex;
	private String realtimeCurveConf;
	private String historyCurveConf;
	
	private String realtimeColor;
	private String realtimeBgColor;
	
	private String historyColor;
	private String historyBgColor;
	
	private Integer type;
	
	private Integer realtimeOverview;
	private Integer  realtimeOverviewSort;
	private Integer realtimeData;
	
	private Integer historyOverview;
	private Integer  historyOverviewSort;
	private Integer historyData;

	public DisplayUnitItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** full constructor */
	public DisplayUnitItem(Integer id, Integer unitId, Integer itemId, String itemName, String itemCode,
			String matrix, Integer showLevel, 
			Integer realtimeSort, Integer historySort, 
			Integer bitIndex,  
			String realtimeCurveConf, String historyCurveConf,
			String realtimeColor, String realtimeBgColor,
			String historyColor, String historyBgColor,
			Integer realtimeOverview,Integer  realtimeOverviewSort,Integer realtimeData,
			Integer historyOverview,Integer  historyOverviewSort,Integer historyData
			) {
		super();
		this.id = id;
		this.unitId = unitId;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemCode = itemCode;
		this.matrix = matrix;
		this.showLevel = showLevel;
		this.realtimeSort = realtimeSort;
		this.historySort=historySort;
		this.bitIndex = bitIndex;
		this.realtimeCurveConf = realtimeCurveConf;
		this.historyCurveConf = historyCurveConf;
		this.realtimeColor = realtimeColor;
		this.realtimeBgColor = realtimeBgColor;
		this.historyColor = historyColor;
		this.historyBgColor = historyBgColor;
		
		this.realtimeOverview = realtimeOverview;
		this.realtimeOverviewSort = realtimeOverviewSort;
		this.realtimeData = realtimeData;
		this.historyOverview = historyOverview;
		this.historyOverviewSort = historyOverviewSort;
		this.historyData = historyData;
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
	@Column(name = "realtimeSort")
	public Integer getRealtimeSort() {
		return realtimeSort;
	}

	public void setRealtimeSort(Integer realtimeSort) {
		this.realtimeSort = realtimeSort;
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

	@Column(name = "realtimeCurveConf", nullable = true, length = 8)
	public String getRealtimeCurveConf() {
		return realtimeCurveConf;
	}

	public void setRealtimeCurveConf(String realtimeCurveConf) {
		this.realtimeCurveConf = realtimeCurveConf;
	}

	@Column(name = "historyCurveConf", nullable = true, length = 8)
	public String getHistoryCurveConf() {
		return historyCurveConf;
	}

	public void setHistoryCurveConf(String historyCurveConf) {
		this.historyCurveConf = historyCurveConf;
	}

	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "historySort")
	public Integer getHistorySort() {
		return historySort;
	}

	public void setHistorySort(Integer historySort) {
		this.historySort = historySort;
	}

	@Column(name = "realtimeColor", nullable = true, length = 8)
	public String getRealtimeColor() {
		return realtimeColor;
	}

	public void setRealtimeColor(String realtimeColor) {
		this.realtimeColor = realtimeColor;
	}

	@Column(name = "realtimeBgColor", nullable = true, length = 8)
	public String getRealtimeBgColor() {
		return realtimeBgColor;
	}

	public void setRealtimeBgColor(String realtimeBgColor) {
		this.realtimeBgColor = realtimeBgColor;
	}

	@Column(name = "historyColor", nullable = true, length = 8)
	public String getHistoryColor() {
		return historyColor;
	}

	public void setHistoryColor(String historyColor) {
		this.historyColor = historyColor;
	}

	@Column(name = "historyBgColor", nullable = true, length = 8)
	public String getHistoryBgColor() {
		return historyBgColor;
	}

	public void setHistoryBgColor(String historyBgColor) {
		this.historyBgColor = historyBgColor;
	}

	@Column(name = "realtimeOverview")
	public Integer getRealtimeOverview() {
		return realtimeOverview;
	}

	public void setRealtimeOverview(Integer realtimeOverview) {
		this.realtimeOverview = realtimeOverview;
	}

	@Column(name = "realtimeOverviewSort")
	public Integer getRealtimeOverviewSort() {
		return realtimeOverviewSort;
	}

	public void setRealtimeOverviewSort(Integer realtimeOverviewSort) {
		this.realtimeOverviewSort = realtimeOverviewSort;
	}

	@Column(name = "realtimeData")
	public Integer getRealtimeData() {
		return realtimeData;
	}

	public void setRealtimeData(Integer realtimeData) {
		this.realtimeData = realtimeData;
	}

	@Column(name = "historyOverview")
	public Integer getHistoryOverview() {
		return historyOverview;
	}

	public void setHistoryOverview(Integer historyOverview) {
		this.historyOverview = historyOverview;
	}

	@Column(name = "historyOverviewSort")
	public Integer getHistoryOverviewSort() {
		return historyOverviewSort;
	}

	public void setHistoryOverviewSort(Integer historyOverviewSort) {
		this.historyOverviewSort = historyOverviewSort;
	}

	@Column(name = "historyData")
	public Integer getHistoryData() {
		return historyData;
	}

	public void setHistoryData(Integer historyData) {
		this.historyData = historyData;
	}
}