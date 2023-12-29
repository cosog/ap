package com.cosog.model.calculate;

import java.io.Serializable;
import java.util.List;

import com.cosog.model.drive.ModbusProtocolConfig.ItemsMeaning;

public class DisplayInstanceOwnItem implements Serializable {
	private static final long serialVersionUID = 1L;

	public String instanceCode;
	public String protocol;
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
		public int sort;
		public String realtimeCurveConf;
		public String historyCurveConf;
		public int type;
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
		public int getSort() {
			return sort;
		}
		public void setSort(int sort) {
			this.sort = sort;
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
			return this.sort-displayItem.getSort();   // 根据值或者位升序排列，降序修改相减顺序即可
		}
	}
	
	public String getInstanceCode() {
		return instanceCode;
	}
	public void setInstanceCode(String instanceCode) {
		this.instanceCode = instanceCode;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
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
