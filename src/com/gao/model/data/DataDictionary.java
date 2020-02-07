package com.gao.model.data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 描述：数据字典对象信息，封装前台需要的json列信息
 * </p>
 * 
 * @author gao 2014-06-09
 * @version 1.0
 * 
 */
public class DataDictionary implements Serializable {
	private static final long serialVersionUID = 1L;

	private String order;
	private List<String> params;
	private List<String> headers;
	private List<String> fields;
	private String colunn;
	private String head;
	private String sql;
	private String dataValue;
	private String tableHeader;
	private String group;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DataDictionary) {
			DataDictionary dd = (DataDictionary) obj;
			return (colunn.equals(dd.colunn)) && (head.equals(dd.head));
		}
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		return colunn.hashCode();
	}

	public String getColunn() {
		return colunn;
	}

	public void setColunn(String colunn) {
		this.colunn = colunn;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public List<String> getParams() {
		return params;
	}

	public String getSql() {
		return sql;
	}

	public String getTableHeader() {
		return tableHeader;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setTableHeader(String tableHeader) {
		this.tableHeader = tableHeader;
	}
	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
}
