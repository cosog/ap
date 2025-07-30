package com.cosog.model;

import java.util.List;

public class ExportDataDictionary {

	private String Dictionaryid;
	
	private String Name_zh_CN;
	
	private String Name_en;
	
	private String Name_ru;
	
	private String Code;
	
	private int Sort;
	
	private int Status;
	
	private int ModuleId;
	
	List<DataDictionaryItem> Item;
	
	private int saveSign=0;
	
	private String msg="";
	
	private int saveId=0;
	
	public static class DataDictionaryItem{
		
		private String Name_zh_CN;
		
		private String Name_en;
		
		private String Name_ru;
		
		private String Code;
		
		private int DeviceType;
		
		private String DataValue;
		
		private int Sort;
		
		private int ColumnDataSource;
		
		private int DataSource;
		
		private String SataUnit;
		
		private String ConfigItemName;
		
		private int Status;
		
		private int Status_zh_CN;
		
		private int Status_en;
		
		private int Status_ru;

		public String getName_zh_CN() {
			return Name_zh_CN;
		}

		public void setName_zh_CN(String name_zh_CN) {
			Name_zh_CN = name_zh_CN;
		}

		public String getName_en() {
			return Name_en;
		}

		public void setName_en(String name_en) {
			Name_en = name_en;
		}

		public String getName_ru() {
			return Name_ru;
		}

		public void setName_ru(String name_ru) {
			Name_ru = name_ru;
		}

		public String getCode() {
			return Code;
		}

		public void setCode(String code) {
			Code = code;
		}

		public int getDeviceType() {
			return DeviceType;
		}

		public void setDeviceType(int deviceType) {
			DeviceType = deviceType;
		}

		public String getDataValue() {
			return DataValue;
		}

		public void setDataValue(String dataValue) {
			DataValue = dataValue;
		}

		public int getSort() {
			return Sort;
		}

		public void setSort(int sort) {
			Sort = sort;
		}

		public int getColumnDataSource() {
			return ColumnDataSource;
		}

		public void setColumnDataSource(int columnDataSource) {
			ColumnDataSource = columnDataSource;
		}

		public int getDataSource() {
			return DataSource;
		}

		public void setDataSource(int dataSource) {
			DataSource = dataSource;
		}

		public String getSataUnit() {
			return SataUnit;
		}

		public void setSataUnit(String sataUnit) {
			SataUnit = sataUnit;
		}

		public String getConfigItemName() {
			return ConfigItemName;
		}

		public void setConfigItemName(String configItemName) {
			ConfigItemName = configItemName;
		}

		public int getStatus() {
			return Status;
		}

		public void setStatus(int status) {
			Status = status;
		}

		public int getStatus_zh_CN() {
			return Status_zh_CN;
		}

		public void setStatus_zh_CN(int status_zh_CN) {
			Status_zh_CN = status_zh_CN;
		}

		public int getStatus_en() {
			return Status_en;
		}

		public void setStatus_en(int status_en) {
			Status_en = status_en;
		}

		public int getStatus_ru() {
			return Status_ru;
		}

		public void setStatus_ru(int status_ru) {
			Status_ru = status_ru;
		}
	}

	public String getDictionaryid() {
		return Dictionaryid;
	}

	public void setDictionaryid(String dictionaryid) {
		Dictionaryid = dictionaryid;
	}

	public String getName_zh_CN() {
		return Name_zh_CN;
	}

	public void setName_zh_CN(String name_zh_CN) {
		Name_zh_CN = name_zh_CN;
	}

	public String getName_en() {
		return Name_en;
	}

	public void setName_en(String name_en) {
		Name_en = name_en;
	}

	public String getName_ru() {
		return Name_ru;
	}

	public void setName_ru(String name_ru) {
		Name_ru = name_ru;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public int getSort() {
		return Sort;
	}

	public void setSort(int sort) {
		Sort = sort;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public int getModuleId() {
		return ModuleId;
	}

	public void setModuleId(int moduleId) {
		ModuleId = moduleId;
	}

	public List<DataDictionaryItem> getItem() {
		return Item;
	}

	public void setItem(List<DataDictionaryItem> item) {
		Item = item;
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
}
