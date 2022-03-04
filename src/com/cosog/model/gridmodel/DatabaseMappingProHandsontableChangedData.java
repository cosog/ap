package com.cosog.model.gridmodel;

import java.util.List;

public class DatabaseMappingProHandsontableChangedData {
	
	private List<String> delidslist;

    private List<Updatelist> updatelist;

    private List<Updatelist> insertlist;

    public void setDelidslist(List<String> delidslist){
        this.delidslist = delidslist;
    }
    public List<String> getDelidslist(){
        return this.delidslist;
    }
    public void setUpdatelist(List<Updatelist> updatelist){
        this.updatelist = updatelist;
    }
    public List<Updatelist> getUpdatelist(){
        return this.updatelist;
    }
    public void setInsertlist(List<Updatelist> insertlist){
        this.insertlist = insertlist;
    }
    public List<Updatelist> getInsertlist(){
        return this.insertlist;
    }
    
	public static class Updatelist
	{
	    private String id;

	    private String itemName;

	    private String itemColumn;
	    
	    private String saveTable;
	    
	    private String saveColumn;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getItemName() {
			return itemName;
		}

		public void setItemName(String itemName) {
			this.itemName = itemName;
		}

		public String getItemColumn() {
			return itemColumn;
		}

		public void setItemColumn(String itemColumn) {
			this.itemColumn = itemColumn;
		}

		public String getSaveTable() {
			return saveTable;
		}

		public void setSaveTable(String saveTable) {
			this.saveTable = saveTable;
		}

		public String getSaveColumn() {
			return saveColumn;
		}

		public void setSaveColumn(String saveColumn) {
			this.saveColumn = saveColumn;
		}
	}
}
