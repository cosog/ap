package com.cosog.model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;;

public class CellEditData {
	
	private List<CellEdit> contentUpdateList;

	public List<CellEdit> getContentUpdateList() {
		return contentUpdateList;
	}

	public void setContentUpdateList(List<CellEdit> contentUpdateList) {
		this.contentUpdateList = contentUpdateList;
	}
	
	public static class CellEdit{
		private int editRow;
		private int editCol;
		private String column;
		private String recordId;
		private String oldValue;
		private String newValue;
		private boolean header;
		public int getEditRow() {
			return editRow;
		}
		public void setEditRow(int editRow) {
			this.editRow = editRow;
		}
		public int getEditCol() {
			return editCol;
		}
		public void setEditCol(int editCol) {
			this.editCol = editCol;
		}
		public String getColumn() {
			return column;
		}
		public void setColumn(String column) {
			this.column = column;
		}
		public String getRecordId() {
			return recordId;
		}
		public void setRecordId(String recordId) {
			this.recordId = recordId;
		}
		public String getOldValue() {
			return oldValue;
		}
		public void setOldValue(String oldValue) {
			this.oldValue = oldValue;
		}
		public String getNewValue() {
			return newValue;
		}
		public void setNewValue(String newValue) {
			this.newValue = newValue;
		}
		public boolean getHeader() {
			return header;
		}
		public void setHeader(boolean header) {
			this.header = header;
		}
	}
}
