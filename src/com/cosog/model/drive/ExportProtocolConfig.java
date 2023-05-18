package com.cosog.model.drive;

import java.util.ArrayList;
import java.util.List;

import com.cosog.model.drive.ModbusProtocolConfig.Items;
import com.cosog.model.drive.ModbusProtocolConfig.Protocol;

public class ExportProtocolConfig {

	private Protocol Protocol;
	
	private List<AcqUnit> AcqUnitList;

	public Protocol getProtocol() {
		return Protocol;
	}

	public void setProtocol(Protocol Protocol) {
		this.Protocol = Protocol;
	}
	
	public List<AcqUnit> getAcqUnitList() {
		return AcqUnitList;
	}

	public void setAcqUnitList(List<AcqUnit> acqUnitList) {
		AcqUnitList = acqUnitList;
	}
	
	public static class AcqItem{
		private Integer Id;
		private Integer GroupId;
		private Integer ItemId;
		private String ItemName;
		private String ItemCode;
		private String Matrix;
		private Integer BitIndex;
		public Integer getId() {
			return Id;
		}
		public void setId(Integer id) {
			Id = id;
		}
		public Integer getGroupId() {
			return GroupId;
		}
		public void setGroupId(Integer groupId) {
			GroupId = groupId;
		}
		public Integer getItemId() {
			return ItemId;
		}
		public void setItemId(Integer itemId) {
			ItemId = itemId;
		}
		public String getItemName() {
			return ItemName;
		}
		public void setItemName(String itemName) {
			ItemName = itemName;
		}
		public String getItemCode() {
			return ItemCode;
		}
		public void setItemCode(String itemCode) {
			ItemCode = itemCode;
		}
		public String getMatrix() {
			return Matrix;
		}
		public void setMatrix(String matrix) {
			Matrix = matrix;
		}
		public Integer getBitIndex() {
			return BitIndex;
		}
		public void setBitIndex(Integer bitIndex) {
			BitIndex = bitIndex;
		}
	}
	
	public static class AcqGroup{
		private Integer Id;
		private String GroupCode;
		private String GroupName;
		private Integer GroupTimingInterval;
		private Integer GroupSavingInterval;
		private String Protocol;
		private Integer Type;
		private String Remark;
		private List<AcqItem> AcqItemList;
		public Integer getId() {
			return Id;
		}
		public void setId(Integer id) {
			Id = id;
		}
		public String getGroupCode() {
			return GroupCode;
		}
		public void setGroupCode(String groupCode) {
			GroupCode = groupCode;
		}
		public String getGroupName() {
			return GroupName;
		}
		public void setGroupName(String groupName) {
			GroupName = groupName;
		}
		public Integer getGroupTimingInterval() {
			return GroupTimingInterval;
		}
		public void setGroupTimingInterval(Integer groupTimingInterval) {
			GroupTimingInterval = groupTimingInterval;
		}
		public Integer getGroupSavingInterval() {
			return GroupSavingInterval;
		}
		public void setGroupSavingInterval(Integer groupSavingInterval) {
			GroupSavingInterval = groupSavingInterval;
		}
		public String getProtocol() {
			return Protocol;
		}
		public void setProtocol(String protocol) {
			Protocol = protocol;
		}
		public Integer getType() {
			return Type;
		}
		public void setType(Integer type) {
			Type = type;
		}
		public String getRemark() {
			return Remark;
		}
		public void setRemark(String remark) {
			Remark = remark;
		}
		public List<AcqItem> getAcqItemList() {
			return AcqItemList;
		}
		public void setAcqItemList(List<AcqItem> acqItemList) {
			AcqItemList = acqItemList;
		}
	}
	
	public static class AcqUnit{
		private Integer Id;
		private String UnitCode;
		private String UnitName;
		private String Protocol;
		private String Remark;
		private List<AcqGroup> AcqGroupList;
		
		public Integer getId() {
			return Id;
		}
		public void setId(Integer id) {
			Id = id;
		}
		public String getUnitCode() {
			return UnitCode;
		}
		public void setUnitCode(String unitCode) {
			UnitCode = unitCode;
		}
		public String getUnitName() {
			return UnitName;
		}
		public void setUnitName(String unitName) {
			UnitName = unitName;
		}
		public String getProtocol() {
			return Protocol;
		}
		public void setProtocol(String protocol) {
			Protocol = protocol;
		}
		public String getRemark() {
			return Remark;
		}
		public void setRemark(String remark) {
			Remark = remark;
		}
		public List<AcqGroup> getAcqGroupList() {
			return AcqGroupList;
		}
		public void setAcqGroupList(List<AcqGroup> acqGroupList) {
			AcqGroupList = acqGroupList;
		}
		
	}
	
	public void init(){
		this.setProtocol(new Protocol());
		List<Items> Items=new ArrayList<>();
		this.getProtocol().setItems(Items);
		
		
	}

	
}
