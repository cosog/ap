package com.cosog.model.calculate;

import java.io.Serializable;
import java.util.List;

import com.cosog.model.drive.ModbusProtocolConfig.ItemsMeaning;

public class DisplayInstanceOwnItem implements Serializable {
	private static final long serialVersionUID = 1L;

	public String instanceCode;
	public String protocolCode;
	public int unitId;
	public List<DisplayItem> itemList;
	
	public static class DisplayItem implements Serializable , Comparable<DisplayItem>{
		private static final long serialVersionUID = 1L;
		public int itemId;
		public String itemName;
		public String itemCode;
		public int bitIndex;
		public int unitId;
		public int showLevel;
		public int realtimeSort;
		public int historySort;
		
		private String realtimeColor;
		private String realtimeBgColor;
		
		private String historyColor;
		private String historyBgColor;
		
		public String realtimeCurveConf;
		public String historyCurveConf;
		public int type;
		public String itemSourceName;
		public String itemSourceCode;
		
		private int realtimeOverview;
		private int  realtimeOverviewSort;
		private int realtimeData;
		
		private int historyOverview;
		private int  historyOverviewSort;
		private int historyData;
		
		public int getItemId() {
			return itemId;
		}
		public void setItemId(int itemId) {
			this.itemId = itemId;
		}
		public String getItemName() {
			return itemName;
		}
		public void setItemName(String itemName) {
			this.itemName = itemName;
		}
		public String getItemCode() {
			return itemCode;
		}
		public void setItemCode(String itemCode) {
			this.itemCode = itemCode;
		}
		public int getBitIndex() {
			return bitIndex;
		}
		public void setBitIndex(int bitIndex) {
			this.bitIndex = bitIndex;
		}
		public int getUnitId() {
			return unitId;
		}
		public void setUnitId(int unitId) {
			this.unitId = unitId;
		}
		public int getShowLevel() {
			return showLevel;
		}
		public void setShowLevel(int showLevel) {
			this.showLevel = showLevel;
		}
		public int getRealtimeSort() {
			return realtimeSort;
		}
		public void setRealtimeSort(int realtimeSort) {
			this.realtimeSort = realtimeSort;
		}
		
		public String getRealtimeCurveConf() {
			return realtimeCurveConf;
		}
		public void setRealtimeCurveConf(String realtimeCurveConf) {
			this.realtimeCurveConf = realtimeCurveConf;
		}
		
		public String getHistoryCurveConf() {
			return historyCurveConf;
		}
		public void setHistoryCurveConf(String historyCurveConf) {
			this.historyCurveConf = historyCurveConf;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		@Override
		public int compareTo(DisplayItem displayItem) {     //重写Comparable接口的compareTo方法
			return this.realtimeSort-displayItem.getRealtimeSort();   // 根据值或者位升序排列，降序修改相减顺序即可
		}
		public String getItemSourceName() {
			return itemSourceName;
		}
		public void setItemSourceName(String itemSourceName) {
			this.itemSourceName = itemSourceName;
		}
		public String getItemSourceCode() {
			return itemSourceCode;
		}
		public void setItemSourceCode(String itemSourceCode) {
			this.itemSourceCode = itemSourceCode;
		}
		public int getHistorySort() {
			return historySort;
		}
		public void setHistorySort(int historySort) {
			this.historySort = historySort;
		}
		public String getRealtimeColor() {
			return realtimeColor;
		}
		public void setRealtimeColor(String realtimeColor) {
			this.realtimeColor = realtimeColor;
		}
		public String getRealtimeBgColor() {
			return realtimeBgColor;
		}
		public void setRealtimeBgColor(String realtimeBgColor) {
			this.realtimeBgColor = realtimeBgColor;
		}
		public String getHistoryColor() {
			return historyColor;
		}
		public void setHistoryColor(String historyColor) {
			this.historyColor = historyColor;
		}
		public String getHistoryBgColor() {
			return historyBgColor;
		}
		public void setHistoryBgColor(String historyBgColor) {
			this.historyBgColor = historyBgColor;
		}
		public int getRealtimeOverview() {
			return realtimeOverview;
		}
		public void setRealtimeOverview(int realtimeOverview) {
			this.realtimeOverview = realtimeOverview;
		}
		public int getRealtimeOverviewSort() {
			return realtimeOverviewSort;
		}
		public void setRealtimeOverviewSort(int realtimeOverviewSort) {
			this.realtimeOverviewSort = realtimeOverviewSort;
		}
		public int getRealtimeData() {
			return realtimeData;
		}
		public void setRealtimeData(int realtimeData) {
			this.realtimeData = realtimeData;
		}
		public int getHistoryOverview() {
			return historyOverview;
		}
		public void setHistoryOverview(int historyOverview) {
			this.historyOverview = historyOverview;
		}
		public int getHistoryOverviewSort() {
			return historyOverviewSort;
		}
		public void setHistoryOverviewSort(int historyOverviewSort) {
			this.historyOverviewSort = historyOverviewSort;
		}
		public int getHistoryData() {
			return historyData;
		}
		public void setHistoryData(int historyData) {
			this.historyData = historyData;
		}
	}
	
	public String getInstanceCode() {
		return instanceCode;
	}
	public void setInstanceCode(String instanceCode) {
		this.instanceCode = instanceCode;
	}
	public String getProtocolCode() {
		return protocolCode;
	}
	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}
	public List<DisplayItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<DisplayItem> itemList) {
		this.itemList = itemList;
	}
	public int getUnitId() {
		return unitId;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	
}
