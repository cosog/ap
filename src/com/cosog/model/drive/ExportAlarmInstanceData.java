package com.cosog.model.drive;

public class ExportAlarmInstanceData {

	private int Id;

    private String Name;

    private String Code;

    private int Sort;

    private int UnitId;

    private String UnitName;
    
    private String Protocol;

    public void setId(int Id){
        this.Id = Id;
    }
    public int getId(){
        return this.Id;
    }
    public void setName(String Name){
        this.Name = Name;
    }
    public String getName(){
        return this.Name;
    }
    public void setCode(String Code){
        this.Code = Code;
    }
    public String getCode(){
        return this.Code;
    }
    
    public void setSort(int Sort){
        this.Sort = Sort;
    }
    public int getSort(){
        return this.Sort;
    }
    public void setUnitId(int UnitId){
        this.UnitId = UnitId;
    }
    public int getUnitId(){
        return this.UnitId;
    }
    public void setUnitName(String UnitName){
        this.UnitName = UnitName;
    }
    public String getUnitName(){
        return this.UnitName;
    }
	public String getProtocol() {
		return Protocol;
	}
	public void setProtocol(String protocol) {
		Protocol = protocol;
	}
}
