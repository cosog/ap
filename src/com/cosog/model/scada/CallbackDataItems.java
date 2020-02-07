package com.cosog.model.scada;

public class CallbackDataItems {
	public String DataItem;
	public String Value;
	public String TableName;
	public String ColumnName;
	public int SJLX;
	
	public CallbackDataItems() {
		super();
	}
	public CallbackDataItems(String dataItem, String value, String tableName,
			String columnName,int sjlx) {
		super();
		DataItem = dataItem;
		Value = value;
		TableName = tableName;
		ColumnName = columnName;
		SJLX=sjlx;
	}
	public String getDataItem() {
		return DataItem;
	}
	public void setDataItem(String dataItem) {
		DataItem = dataItem;
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	public String getTableName() {
		return TableName;
	}
	public void setTableName(String tableName) {
		TableName = tableName;
	}
	public String getColumnName() {
		return ColumnName;
	}
	public void setColumnName(String columnName) {
		ColumnName = columnName;
	}
	public int getSJLX() {
		return SJLX;
	}
	public void setSJLX(int sJLX) {
		SJLX = sJLX;
	}
	
}
