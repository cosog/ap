package com.cosog.model.drive;

import java.util.ArrayList;
import java.util.List;

import com.cosog.model.DataMapping;
import com.cosog.model.ProtocolRunStatusConfig;
import com.cosog.model.drive.ModbusProtocolConfig.Items;
import com.cosog.model.drive.ModbusProtocolConfig.Protocol;

public class ExportProtocolConfig {

	private Protocol Protocol;
	
	private List<AcqUnit> AcqUnitList;
	
	private List<DisplayUnit> DisplayUnitList;
	
	private List<AlarmUnit> AlarmUnitList;
	
	private List<AcqInstance> AcqInstanceList;
	
	private List<DisplayInstance> DisplayInstanceList;
	
	private List<AlarmInstance> AlarmInstanceList;
	
	private List<DataMapping> DataMappingList;
	
	private List<ProtocolRunStatusConfig> ProtocolRunStatusConfigList;

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

	public List<DisplayUnit> getDisplayUnitList() {
		return DisplayUnitList;
	}

	public void setDisplayUnitList(List<DisplayUnit> displayUnitList) {
		DisplayUnitList = displayUnitList;
	}

	public List<AlarmUnit> getAlarmUnitList() {
		return AlarmUnitList;
	}

	public void setAlarmUnitList(List<AlarmUnit> alarmUnitList) {
		AlarmUnitList = alarmUnitList;
	}

	public List<AcqInstance> getAcqInstanceList() {
		return AcqInstanceList;
	}

	public void setAcqInstanceList(List<AcqInstance> acqInstanceList) {
		AcqInstanceList = acqInstanceList;
	}

	public List<DisplayInstance> getDisplayInstanceList() {
		return DisplayInstanceList;
	}

	public void setDisplayInstanceList(List<DisplayInstance> displayInstanceList) {
		DisplayInstanceList = displayInstanceList;
	}

	public List<AlarmInstance> getAlarmInstanceList() {
		return AlarmInstanceList;
	}

	public void setAlarmInstanceList(List<AlarmInstance> alarmInstanceList) {
		AlarmInstanceList = alarmInstanceList;
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
	
	public static class DisplayItem{
		private Integer Id;
		private Integer UnitId;
		private Integer ItemId;
		private String ItemName;
		private String ItemCode;
		private String Matrix;
		private Integer ShowLevel;
		private Integer Sort;
		private Integer BitIndex;
		private String RealtimeCurveConf;
		private String HistoryCurveConf;
		private Integer Type;
		public Integer getId() {
			return Id;
		}
		public void setId(Integer id) {
			Id = id;
		}
		public Integer getUnitId() {
			return UnitId;
		}
		public void setUnitId(Integer unitId) {
			UnitId = unitId;
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
		public Integer getShowLevel() {
			return ShowLevel;
		}
		public void setShowLevel(Integer showLevel) {
			ShowLevel = showLevel;
		}
		public Integer getSort() {
			return Sort;
		}
		public void setSort(Integer sort) {
			Sort = sort;
		}
		public Integer getBitIndex() {
			return BitIndex;
		}
		public void setBitIndex(Integer bitIndex) {
			BitIndex = bitIndex;
		}
		public String getRealtimeCurveConf() {
			return RealtimeCurveConf;
		}
		public void setRealtimeCurveConf(String realtimeCurveConf) {
			RealtimeCurveConf = realtimeCurveConf;
		}
		public String getHistoryCurveConf() {
			return HistoryCurveConf;
		}
		public void setHistoryCurveConf(String historyCurveConf) {
			HistoryCurveConf = historyCurveConf;
		}
		public Integer getType() {
			return Type;
		}
		public void setType(Integer type) {
			Type = type;
		}
	}
	
	public static class DisplayUnit{
		private Integer Id;
		private String UnitCode;
		private String UnitName;
		private String Protocol;
		private Integer AcqUnitId;
		private String Remark;
		private List<DisplayItem> DisplayItemList;
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
		public Integer getAcqUnitId() {
			return AcqUnitId;
		}
		public void setAcqUnitId(Integer acqUnitId) {
			AcqUnitId = acqUnitId;
		}
		public String getRemark() {
			return Remark;
		}
		public void setRemark(String remark) {
			Remark = remark;
		}
		public List<DisplayItem> getDisplayItemList() {
			return DisplayItemList;
		}
		public void setDisplayItemList(List<DisplayItem> displayItemList) {
			DisplayItemList = displayItemList;
		}
	}
	
	public static class AlarmItem{
		private Integer Id;
		private Integer UnitId;
		private Integer ItemId;
		private String ItemName;
		private String ItemCode;
		private Integer ItemAddr;
		private Integer Type;
		private Float Value;
		private Integer BitIndex;
		private Float UpperLimit;
		private Float LowerLimit;
		private Float Hystersis;
		private Integer Delay;
		private Integer AlarmLevel;
		private Integer AlarmSign;
		private Integer IsSendMessage;
		private Integer IsSendMail;
		public Integer getId() {
			return Id;
		}
		public void setId(Integer id) {
			Id = id;
		}
		public Integer getUnitId() {
			return UnitId;
		}
		public void setUnitId(Integer unitId) {
			UnitId = unitId;
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
		public Integer getItemAddr() {
			return ItemAddr;
		}
		public void setItemAddr(Integer itemAddr) {
			ItemAddr = itemAddr;
		}
		public Integer getType() {
			return Type;
		}
		public void setType(Integer type) {
			Type = type;
		}
		public Float getValue() {
			return Value;
		}
		public void setValue(Float value) {
			Value = value;
		}
		public Integer getBitIndex() {
			return BitIndex;
		}
		public void setBitIndex(Integer bitIndex) {
			BitIndex = bitIndex;
		}
		public Float getUpperLimit() {
			return UpperLimit;
		}
		public void setUpperLimit(Float upperLimit) {
			UpperLimit = upperLimit;
		}
		public Float getLowerLimit() {
			return LowerLimit;
		}
		public void setLowerLimit(Float lowerLimit) {
			LowerLimit = lowerLimit;
		}
		public Float getHystersis() {
			return Hystersis;
		}
		public void setHystersis(Float hystersis) {
			Hystersis = hystersis;
		}
		public Integer getDelay() {
			return Delay;
		}
		public void setDelay(Integer delay) {
			Delay = delay;
		}
		public Integer getAlarmLevel() {
			return AlarmLevel;
		}
		public void setAlarmLevel(Integer alarmLevel) {
			AlarmLevel = alarmLevel;
		}
		public Integer getAlarmSign() {
			return AlarmSign;
		}
		public void setAlarmSign(Integer alarmSign) {
			AlarmSign = alarmSign;
		}
		public Integer getIsSendMessage() {
			return IsSendMessage;
		}
		public void setIsSendMessage(Integer isSendMessage) {
			IsSendMessage = isSendMessage;
		}
		public Integer getIsSendMail() {
			return IsSendMail;
		}
		public void setIsSendMail(Integer isSendMail) {
			IsSendMail = isSendMail;
		}
	}
	
	public static class AlarmUnit{
		private Integer Id;
		private String UnitCode;
		private String UnitName;
		private String Protocol;
		private String Remark;
		private List<AlarmItem> AlarmItemList;
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
		public List<AlarmItem> getAlarmItemList() {
			return AlarmItemList;
		}
		public void setAlarmItemList(List<AlarmItem> alarmItemList) {
			AlarmItemList = alarmItemList;
		}
	}
	
	public static class AcqInstance{
		private Integer Id;
		private String Name;
		private String Code;
		private Integer UnitId;
		private String AcqProtocolType;
		private String CtrlProtocolType;
		private Integer SignInPrefixSuffixHex;
		private String SignInPrefix;
		private String SignInSuffix;
		private Integer SignInIDHex;
		private Integer HeartbeatPrefixSuffixHex;
		private String HeartbeatPrefix;
		private String HeartbeatSuffix;
		private Integer PacketSendInterval;
		private Integer Sort;
		public Integer getId() {
			return Id;
		}
		public void setId(Integer id) {
			Id = id;
		}
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
		public String getCode() {
			return Code;
		}
		public void setCode(String code) {
			Code = code;
		}
		public Integer getUnitId() {
			return UnitId;
		}
		public void setUnitId(Integer unitId) {
			UnitId = unitId;
		}
		public String getAcqProtocolType() {
			return AcqProtocolType;
		}
		public void setAcqProtocolType(String acqProtocolType) {
			AcqProtocolType = acqProtocolType;
		}
		public String getCtrlProtocolType() {
			return CtrlProtocolType;
		}
		public void setCtrlProtocolType(String ctrlProtocolType) {
			CtrlProtocolType = ctrlProtocolType;
		}
		public Integer getSignInPrefixSuffixHex() {
			return SignInPrefixSuffixHex;
		}
		public void setSignInPrefixSuffixHex(Integer signInPrefixSuffixHex) {
			SignInPrefixSuffixHex = signInPrefixSuffixHex;
		}
		public String getSignInPrefix() {
			return SignInPrefix;
		}
		public void setSignInPrefix(String signInPrefix) {
			SignInPrefix = signInPrefix;
		}
		public String getSignInSuffix() {
			return SignInSuffix;
		}
		public void setSignInSuffix(String signInSuffix) {
			SignInSuffix = signInSuffix;
		}
		public Integer getSignInIDHex() {
			return SignInIDHex;
		}
		public void setSignInIDHex(Integer signInIDHex) {
			SignInIDHex = signInIDHex;
		}
		public Integer getHeartbeatPrefixSuffixHex() {
			return HeartbeatPrefixSuffixHex;
		}
		public void setHeartbeatPrefixSuffixHex(Integer heartbeatPrefixSuffixHex) {
			HeartbeatPrefixSuffixHex = heartbeatPrefixSuffixHex;
		}
		public String getHeartbeatPrefix() {
			return HeartbeatPrefix;
		}
		public void setHeartbeatPrefix(String heartbeatPrefix) {
			HeartbeatPrefix = heartbeatPrefix;
		}
		public String getHeartbeatSuffix() {
			return HeartbeatSuffix;
		}
		public void setHeartbeatSuffix(String heartbeatSuffix) {
			HeartbeatSuffix = heartbeatSuffix;
		}
		public Integer getPacketSendInterval() {
			return PacketSendInterval;
		}
		public void setPacketSendInterval(Integer packetSendInterval) {
			PacketSendInterval = packetSendInterval;
		}
		public Integer getSort() {
			return Sort;
		}
		public void setSort(Integer sort) {
			Sort = sort;
		}
	}
	
	public static class DisplayInstance{
		private Integer Id;
		private String Name;
		private String Code;
		private Integer DisplayUnitId;
		private Integer Sort;
		public Integer getId() {
			return Id;
		}
		public void setId(Integer id) {
			Id = id;
		}
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
		public String getCode() {
			return Code;
		}
		public void setCode(String code) {
			Code = code;
		}
		public Integer getDisplayUnitId() {
			return DisplayUnitId;
		}
		public void setDisplayUnitId(Integer displayUnitId) {
			DisplayUnitId = displayUnitId;
		}
		public Integer getSort() {
			return Sort;
		}
		public void setSort(Integer sort) {
			Sort = sort;
		}
	}
	
	public static class AlarmInstance{
		private Integer Id;
		private String Name;
		private String Code;
		private Integer AlarmUnitId;
		private Integer Sort;
		public Integer getId() {
			return Id;
		}
		public void setId(Integer id) {
			Id = id;
		}
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
		public String getCode() {
			return Code;
		}
		public void setCode(String code) {
			Code = code;
		}
		public Integer getAlarmUnitId() {
			return AlarmUnitId;
		}
		public void setAlarmUnitId(Integer alarmUnitId) {
			AlarmUnitId = alarmUnitId;
		}
		public Integer getSort() {
			return Sort;
		}
		public void setSort(Integer sort) {
			Sort = sort;
		}
	}
	
	public void init(){
		this.setProtocol(new Protocol());
		List<Items> Items=new ArrayList<>();
		this.getProtocol().setItems(Items);
		
		
	}

	public List<DataMapping> getDataMappingList() {
		return DataMappingList;
	}

	public void setDataMappingList(List<DataMapping> dataMappingList) {
		DataMappingList = dataMappingList;
	}

	public List<ProtocolRunStatusConfig> getProtocolRunStatusConfigList() {
		return ProtocolRunStatusConfigList;
	}

	public void setProtocolRunStatusConfigList(List<ProtocolRunStatusConfig> protocolRunStatusConfigList) {
		ProtocolRunStatusConfigList = protocolRunStatusConfigList;
	}
}
