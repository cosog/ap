package com.cosog.model;

/**
 * <p>描述：key-Value 形的Model实体类</p>
 * 
 * @author gao 2014-06-25
 *
 */
public class KeyValue {
	private String key;
	private String value;
	
	public KeyValue() {
		super();
	}
	public KeyValue(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
