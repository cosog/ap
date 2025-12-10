package com.cosog.utils;

import java.util.ArrayList;
import java.util.List;

//import org.apache.commons.lang.xwork.*;
import javax.servlet.http.HttpServletRequest;

public class Page {

	private String bzgtbh = "";
	private List<?> data1;
	private String end_date = "";
	private String jh = "";
	private String jhh = "";
	private String orgExist = "";
	private String jssj = "";
	private int limit = 25; // 每页显示记录数numPerPage ->每页显示数: limit
	private int page = 1; // 当前页号 currentPage->当前第几页，从1开始: page
	@SuppressWarnings("unused")
	private HttpServletRequest request;
	// 排序字符串
	private String sort;
	private int start = 0; // 记录开始

	private String start_date = "";

	private int totalCount = 0; // 总记录数

	// 查询条件集合
	@SuppressWarnings("rawtypes")
	protected List whereList;

	public Page(HttpServletRequest request) {
		this("pagerForm", request);
	}

	public Page(String etTableId) {
	}

	public Page(String etTableId, HttpServletRequest request) {
		try {
			this.request = request;
			if (request.getParameter("limit") != null)
				this.setLimit(Integer.parseInt(request.getParameter("limit")));
			if (request.getParameter("start") != null)
				this.setStart(Integer.parseInt(request.getParameter("start")));
			if (request.getParameter("page") != null)
				this.setPage(Integer.parseInt(request.getParameter("page")));

		} catch (Exception e) {
			// 100007 设置分页信息错误
			StringManagerUtils.printLog(e.getMessage(),2);
		}
	}
	
	public String getOrgExist() {
		return orgExist;
	}

	public void setOrgExist(String orgExist) {
		this.orgExist = orgExist;
	}

	public String getBzgtbh() {
		return bzgtbh;
	}

	public List<?> getData1() {
		return data1;
	}

	public String getEnd_date() {
		return end_date;
	}

	public String getJh() {
		return jh;
	}

	public String getJssj() {
		return jssj;
	}

	public int getLimit() {
		return limit;
	}

	public int getPage() {
		return page;
	}

	public String getSort() {
		return sort;
	}

	public int getStart() {
		return start;
	}

	public String getStart_date() {
		return start_date;
	}

	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @return 返回查询的条件List集合
	 */
	@SuppressWarnings("rawtypes")
	public List getWhere() {
		return whereList;
	}

	@SuppressWarnings("rawtypes")
	public List getWhereList() {
		return whereList;
	}

	public void setBzgtbh(String bzgtbh) {
		this.bzgtbh = bzgtbh;
	}

	public void setData1(List<?> data1) {
		this.data1 = data1;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	public void setJssj(String jssj) {
		this.jssj = jssj;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @param where
	 *            作为条件查询的条件串
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setWhere(String where) {
		if (!StringManagerUtils.isNotNull(where)) {
			return;
		}
		if (null == whereList) {
			whereList = new ArrayList();
		}
		whereList.add(where);

	}

	@SuppressWarnings("rawtypes")
	public void setWhereList(List whereList) {
		this.whereList = whereList;
	}

	public String getJhh() {
		return jhh;
	}

	public void setJhh(String jhh) {
		this.jhh = jhh;
	}
}
