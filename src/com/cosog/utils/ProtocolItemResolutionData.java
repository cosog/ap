package com.cosog.utils;

public class ProtocolItemResolutionData  implements Comparable<ProtocolItemResolutionData>{
	public String rawColumnName="";
	public String columnName="";
	public String value="";
	public String rawValue="";
	public String addr="";
	public String column="";
	public String columnDataType="";
	public String resolutionMode="";
	public String bitIndex="";
	public String unit="";
	public int sort;
	public int type;
	
	public ProtocolItemResolutionData() {
		super();
	}
	public ProtocolItemResolutionData(String rawColumnName,String columnName, String value,String rawValue, String addr, String column,
			String columnDataType, String resolutionMode,String bitIndex,String unit,int sort,int type) {
		super();
		this.rawColumnName = rawColumnName;
		this.columnName = columnName;
		this.value = value;
		this.rawValue = rawValue;
		this.addr = addr;
		this.column = column;
		this.columnDataType = columnDataType;
		this.resolutionMode = resolutionMode;
		this.bitIndex = bitIndex;
		this.unit=unit;
		this.sort=sort;
		this.type=type;
	}
	
	public ProtocolItemResolutionData(String rawColumnName,String columnName, String value,String rawValue, String addr, String column,
			String columnDataType, String resolutionMode,String bitIndex,String unit) {
		super();
		this.rawColumnName = rawColumnName;
		this.columnName = columnName;
		this.value = value;
		this.rawValue = rawValue;
		this.addr = addr;
		this.column = column;
		this.columnDataType = columnDataType;
		this.resolutionMode = resolutionMode;
		this.bitIndex = bitIndex;
		this.unit=unit;
	}
	
	@Override
	public int compareTo(ProtocolItemResolutionData protocolItemResolutionData) {//重写Comparable接口的compareTo方法   按照sort升序 addr升序 bitIndex升序
		int r=0;
		if(this.sort>protocolItemResolutionData.getSort()){
			r= 1;
		}else if(this.sort<protocolItemResolutionData.getSort()){
			r= -1;
		}else{
			if(this.type==0 && protocolItemResolutionData.getType()==0){
				if(StringManagerUtils.stringToInteger(this.addr)>StringManagerUtils.stringToInteger(protocolItemResolutionData.getAddr())){
					r= 1;
				}else if(StringManagerUtils.stringToInteger(this.addr)<StringManagerUtils.stringToInteger(protocolItemResolutionData.getAddr())){
					r= -1;
				}else{
					if(StringManagerUtils.stringToInteger(this.bitIndex)>StringManagerUtils.stringToInteger(protocolItemResolutionData.getBitIndex())){
						r= 1;
					}else{
						r= -1;
					}
				}
			}else{
				
			}
		}
		return r;
	}
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getColumnDataType() {
		return columnDataType;
	}
	public void setColumnDataType(String columnDataType) {
		this.columnDataType = columnDataType;
	}
	public String getResolutionMode() {
		return resolutionMode;
	}
	public void setResolutionMode(String resolutionMode) {
		this.resolutionMode = resolutionMode;
	}
	public String getRawValue() {
		return rawValue;
	}
	public void setRawValue(String rawValue) {
		this.rawValue = rawValue;
	}
	public String getBitIndex() {
		return bitIndex;
	}
	public void setBitIndex(String bitIndex) {
		this.bitIndex = bitIndex;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getRawColumnName() {
		return rawColumnName;
	}
	public void setRawColumnName(String rawColumnName) {
		this.rawColumnName = rawColumnName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
