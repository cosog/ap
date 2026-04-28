package com.cosog.model;

public class CurveConf {
	private int groupId;
	private String groupName;
	private int sort;
	private String color;
	private int lineWidth;
	private String dashStyle;
	private boolean yAxisOpposite;
	public CurveConf() {
		super();
	}
	public CurveConf(int sort, String color, int lineWidth, String dashStyle, boolean yAxisOpposite,int groupId,String groupName) {
		super();
		this.sort = sort;
		this.color = color;
		this.lineWidth = lineWidth;
		this.dashStyle = dashStyle;
		this.yAxisOpposite = yAxisOpposite;
		this.groupId = groupId;
		this.groupName = groupName;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getLineWidth() {
		return lineWidth;
	}
	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}
	public String getDashStyle() {
		return dashStyle;
	}
	public void setDashStyle(String dashStyle) {
		this.dashStyle = dashStyle;
	}
	public boolean getYAxisOpposite() {
		return yAxisOpposite;
	}
	public void setYAxisOpposite(boolean yAxisOpposite) {
		this.yAxisOpposite = yAxisOpposite;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
}
