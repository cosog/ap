package com.cosog.model.drive;

import java.util.List;

import com.cosog.model.drive.ModbusProtocolConfig.ExtendedField;
import com.cosog.model.drive.ModbusProtocolConfig.Items;

public class ExportProtocolData {
	
	private int Id;
    
	private String Name;

    private String Code;

    private int Sort;
    
    private int DeviceType;
    
    private int Language;

    private List<Items> Items;
    
    private List<ExtendedField> ExtendedFields;
    
    private RunStatusConfig RunStatus;
    
    private List<DataMapping> DataMappingList;
    
    private int saveSign=0;
	
	private String msg="";
	
	private int saveId=0;
	
	public static class DataMapping{
		
		private String ItemName;
		
		private String ItemMappingColumn;
		
		private String ItemCalculateColumn;
		
		private int CalculateEnable;

		public String getItemName() {
			return ItemName;
		}

		public void setItemName(String itemName) {
			ItemName = itemName;
		}

		public String getItemMappingColumn() {
			return ItemMappingColumn;
		}

		public void setItemMappingColumn(String itemMappingColumn) {
			ItemMappingColumn = itemMappingColumn;
		}

		public String getItemCalculateColumn() {
			return ItemCalculateColumn;
		}

		public void setItemCalculateColumn(String itemCalculateColumn) {
			ItemCalculateColumn = itemCalculateColumn;
		}

		public int getCalculateEnable() {
			return CalculateEnable;
		}

		public void setCalculateEnable(int calculateEnable) {
			CalculateEnable = calculateEnable;
		}
	}
	
	public static class RunStatusConfig{
		
		private String ItemName;
		
		private String ItemMappingColumn;
		
		private String RunValue;
		
		private String StopValue;
		
		private int ResolutionMode;
		
		private String RunCondition;
		
		private String StopCondition;

		public String getItemName() {
			return ItemName;
		}

		public void setItemName(String itemName) {
			ItemName = itemName;
		}

		public String getItemMappingColumn() {
			return ItemMappingColumn;
		}

		public void setItemMappingColumn(String itemMappingColumn) {
			ItemMappingColumn = itemMappingColumn;
		}

		public String getRunValue() {
			return RunValue;
		}

		public void setRunValue(String runValue) {
			RunValue = runValue;
		}

		public String getStopValue() {
			return StopValue;
		}

		public void setStopValue(String stopValue) {
			StopValue = stopValue;
		}

		public int getResolutionMode() {
			return ResolutionMode;
		}

		public void setResolutionMode(int resolutionMode) {
			ResolutionMode = resolutionMode;
		}

		public String getRunCondition() {
			return RunCondition;
		}

		public void setRunCondition(String runCondition) {
			RunCondition = runCondition;
		}

		public String getStopCondition() {
			return StopCondition;
		}

		public void setStopCondition(String stopCondition) {
			StopCondition = stopCondition;
		}
	}

    public void setName(String Name){
        this.Name = Name;
    }
    public String getName(){
        return this.Name;
    }
    public void setCode(String Code){
        this.Code = Code;
    }
    public String getCode(){
        return this.Code;
    }
    public void setSort(int Sort){
        this.Sort = Sort;
    }
    public int getSort(){
        return this.Sort;
    }
    public void setItems(List<Items> Items){
        this.Items = Items;
    }
    public List<Items> getItems(){
        return this.Items;
    }
    
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getDeviceType() {
		return DeviceType;
	}
	public void setDeviceType(int deviceType) {
		DeviceType = deviceType;
	}
	public int getLanguage() {
		return Language;
	}
	public void setLanguage(int language) {
		Language = language;
	}
	public List<ExtendedField> getExtendedFields() {
		return ExtendedFields;
	}
	public void setExtendedFields(List<ExtendedField> extendedFields) {
		ExtendedFields = extendedFields;
	}
	public int getSaveSign() {
		return saveSign;
	}
	public void setSaveSign(int saveSign) {
		this.saveSign = saveSign;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getSaveId() {
		return saveId;
	}
	public void setSaveId(int saveId) {
		this.saveId = saveId;
	}
	public RunStatusConfig getRunStatus() {
		return RunStatus;
	}
	public void setRunStatus(RunStatusConfig runStatus) {
		RunStatus = runStatus;
	}
	public List<DataMapping> getDataMappingList() {
		return DataMappingList;
	}
	public void setDataMappingList(List<DataMapping> dataMappingList) {
		DataMappingList = dataMappingList;
	}
}
