package com.cosog.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cosog.utils.Config2;

public class PageHandler {
	private int currentPage; 
	private int nextPage; 
	private int priviousPage; 
	private int pageCount; 
	private int recordCount; 
	private int pageSize = Config.getInstance().configFile.getOthers().getPageSize(); 
	private ResultSet rs = null;

	public PageHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PageHandler(int currentPage, int recordCount, int pageSize) {
		super();
		this.currentPage = currentPage;
		this.recordCount = recordCount;
		this.pageSize = pageSize;
		this.pageCount = getPageCount();
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public void setPriviousPage(int priviousPage) {
		this.priviousPage = priviousPage;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the priviousPage
	 */
	public int getPriviousPage() {
		if (currentPage > 1) {
			priviousPage = currentPage - 1;
		} else {
			priviousPage = currentPage;
		}
		return priviousPage;
	}

	/**
	 * @return the nextPage
	 */
	public int getNextPage() {
		if (currentPage > pageCount) {
			nextPage = 1;
		} else {
			nextPage = currentPage + 1;
		}
		return nextPage;
	}

	/**
	 * ������ҳ��
	 */
	public int getPageCount() {
		if (recordCount == 0)
			return 0;
		if (pageSize == 0)
			return 1;

		if (recordCount % pageSize == 0) {
			pageCount = recordCount / pageSize;
		} else {
			pageCount = recordCount / pageSize + 1;
		}

		return pageCount;
	}


	public int getPageRowsCount() {
		if (pageSize == 0)
			return recordCount;
		if (recordCount == 0)
			return 0;
		if (currentPage != pageCount)
			return pageSize;

		return recordCount - (pageCount - 1) * pageSize;
	}

	/**
	 * ת��ָ��ҳ
	 */
	public void gotoPage(int page, int pageSize) {
		// PageHandler pageHandler = new PageHandler();
		if (rs == null)
			return;
		if (page < 1)
			page = 1;
		if (page > getPageCount())
			page = getPageCount();
		int row = (page - 1) * pageSize;
		try {
			if (row == 0) {
				rs.beforeFirst();
			} else {
				rs.absolute(row);
			}
			currentPage = page;
		} catch (SQLException e) {
			e.printStackTrace(); // TODO:
		}
	}

	public static void main(String[] args) {
		PageHandler pageHelper = new PageHandler(1, 5, 4);
		StringManagerUtils.printLog("还剩下记录：" + pageHelper.getPageRowsCount());

	}
}
