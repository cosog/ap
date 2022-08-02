package com.cosog.model.gridmodel;

import java.util.List;

public class AcquisitionGroupHandsontableChangeData {

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
        private String checked;

        private String id;

        private String groupName;

        private String groupCode;
        
        private String typeName;

        private String groupTimingInterval;

        private String groupSavingInterval;

        private String remark;

        public void setChecked(String checked){
            this.checked = checked;
        }
        public String getChecked(){
            return this.checked;
        }
        public void setId(String id){
            this.id = id;
        }
        public String getId(){
            return this.id;
        }
        public void setGroupName(String groupName){
            this.groupName = groupName;
        }
        public String getGroupName(){
            return this.groupName;
        }
        public void setGroupCode(String groupCode){
            this.groupCode = groupCode;
        }
        public String getGroupCode(){
            return this.groupCode;
        }
        public void setGroupTimingInterval(String groupTimingInterval){
            this.groupTimingInterval = groupTimingInterval;
        }
        public String getGroupTimingInterval(){
            return this.groupTimingInterval;
        }
        public void setGroupSavingInterval(String groupSavingInterval){
            this.groupSavingInterval = groupSavingInterval;
        }
        public String getGroupSavingInterval(){
            return this.groupSavingInterval;
        }
        public void setRemark(String remark){
            this.remark = remark;
        }
        public String getRemark(){
            return this.remark;
        }
		public String getTypeName() {
			return typeName;
		}
		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
    }
}
