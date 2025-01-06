package com.cosog.model.calculate;

import java.io.Serializable;
import java.util.List;

import com.cosog.utils.StringManagerUtils;

public class CalculateColumnInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<CalculateColumn> SRPCalculateColumnList;

//	private List<CalculateColumn> PCPCalculateColumnList;
	
	public static class CalculateColumn  implements Comparable<CalculateColumn>,Serializable{
		
		private static final long serialVersionUID = 1L;
		
	    private String Name;

	    private String Code;

	    private int Sort;

	    public CalculateColumn(String name, String code, int sort) {
			super();
			Name = name;
			Code = code;
			Sort = sort;
		}

		@Override
		public int compareTo(CalculateColumn calculateColumn) {     //重写Comparable接口的compareTo方法
			return this.Sort-calculateColumn.getSort();   // 根据排序序号升序排列，降序修改相减顺序即可
		}

		public String getName() {
			return Name;
		}

		public void setName(String name) {
			Name = name;
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
	}
	
//	public List<CalculateColumn> getPCPCalculateColumnList() {
//		return PCPCalculateColumnList;
//	}
//	
//	public void setPCPCalculateColumnList(List<CalculateColumn> pCPCalculateColumnList) {
//		PCPCalculateColumnList = pCPCalculateColumnList;
//	}

	public List<CalculateColumn> getSRPCalculateColumnList() {
		return SRPCalculateColumnList;
	}

	public void setSRPCalculateColumnList(List<CalculateColumn> srpCalculateColumnList) {
		SRPCalculateColumnList = srpCalculateColumnList;
	}
	
	
}
