package com.cosog.model.calculate;

import java.io.Serializable;
import java.util.List;

public class AcqInstanceOwnItem  implements Serializable {
	private static final long serialVersionUID = 1L;
	public String instanceCode;
	public int deviceType;
	public String protocol;
	public int unitId;
	private int groupTimingInterval;
	private int groupSavingInterval;
	public List<AcqItem> itemList;
	
	public static class AcqItem implements Serializable {
		private static final long serialVersionUID = 1L;
		public int groupId;
		public int itemId;
		public String itemName;
		public String itemCode;
		public int bitIndex;
		public int getGroupId() {
			return groupId;
		}
		public void setGroupId(int groupId) {
			this.groupId = groupId;
		}
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
	}
	
	public String getInstanceCode() {
		return instanceCode;
	}
	public void setInstanceCode(String instanceCode) {
		this.instanceCode = instanceCode;
	}
	public int getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public int getUnitId() {
		return unitId;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	public List<AcqItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<AcqItem> itemList) {
		this.itemList = itemList;
	}
	public int getGroupTimingInterval() {
		return groupTimingInterval;
	}
	public void setGroupTimingInterval(int groupTimingInterval) {
		this.groupTimingInterval = groupTimingInterval;
	}
	public int getGroupSavingInterval() {
		return groupSavingInterval;
	}
	public void setGroupSavingInterval(int groupSavingInterval) {
		this.groupSavingInterval = groupSavingInterval;
	}
}
