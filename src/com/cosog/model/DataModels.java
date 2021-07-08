package com.cosog.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author gao
 * 
 */
public class DataModels implements Serializable {

	private static final long serialVersionUID = 1L;

	private String columns;
	private String condSqls;
	private List<String> dataType;
	private List<String> hidden;

	private String direction;

	private List<String> dynamics;

	private List<String> fields;
	private List<String> headers;
	private Boolean loading = true;
	private String orderBys;
	private Integer pageSize;
	private List<String> paramType;
	private String tables;
	private List<String> width;
	private String jssj="";

	public String getJssj() {
		return jssj;
	}

	public void setJssj(String jssj) {
		this.jssj = jssj;
	}

	public DataModels() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getColumns() {
		return columns;
	}

	public String getCondSqls() {
		return condSqls;
	}

	public List<String> getDataType() {
		return dataType;
	}

	public String getDirection() {
		return direction;
	}

	public List<String> getDynamics() {
		return dynamics;
	}

	public List<String> getFields() {
		return fields;
	}

	public List<String> getHeaders() {
		return headers;
	}

	public List<String> getHidden() {
		return hidden;
	}

	public Boolean getLoading() {
		return loading;
	}

	public String getOrderBys() {
		return orderBys;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public List<String> getParamType() {
		return paramType;
	}

	public String getTables() {
		return tables;
	}

	public List<String> getWidth() {
		return width;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public void setCondSqls(String condSqls) {
		this.condSqls = condSqls;
	}

	public void setDataType(List<String> dataType) {
		this.dataType = dataType;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void setDynamics(List<String> dynamics) {
		this.dynamics = dynamics;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public void setHidden(List<String> hidden) {
		this.hidden = hidden;
	}

	public void setLoading(Boolean loading) {
		this.loading = loading;
	}

	public void setOrderBys(String orderBys) {
		this.orderBys = orderBys;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setParamType(List<String> paramType) {
		this.paramType = paramType;
	}

	public void setTables(String tables) {
		this.tables = tables;
	}

	public void setWidth(List<String> width) {
		this.width = width;
	}

}
