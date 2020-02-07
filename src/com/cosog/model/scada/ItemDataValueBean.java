package com.cosog.model.scada;

public class ItemDataValueBean {
	private String ItemData;
	private float value;
	
	public ItemDataValueBean(String itemData, float value) {
		super();
		ItemData = itemData;
		this.value = value;
	}
	public ItemDataValueBean() {
		super();
	}
	public String getItemData() {
		return ItemData;
	}
	public void setItemData(String itemData) {
		ItemData = itemData;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	
}
