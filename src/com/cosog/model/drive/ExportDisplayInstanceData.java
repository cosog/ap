package com.cosog.model.drive;

public class ExportDisplayInstanceData {

	private int Id;

    private String Name;

    private String Code;

    private int DisplayUnitId;

    private String DisplayUnitName;

    private int Sort;

    private String AcqUnitName;

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
    public void setDisplayUnitId(int DisplayUnitId){
        this.DisplayUnitId = DisplayUnitId;
    }
    public int getDisplayUnitId(){
        return this.DisplayUnitId;
    }
    public void setDisplayUnitName(String DisplayUnitName){
        this.DisplayUnitName = DisplayUnitName;
    }
    public String getDisplayUnitName(){
        return this.DisplayUnitName;
    }
    public void setSort(int Sort){
        this.Sort = Sort;
    }
    public int getSort(){
        return this.Sort;
    }
    public void setAcqUnitName(String AcqUnitName){
        this.AcqUnitName = AcqUnitName;
    }
    public String getAcqUnitName(){
        return this.AcqUnitName;
    }
    public void setProtocol(String Protocol){
        this.Protocol = Protocol;
    }
    public String getProtocol(){
        return this.Protocol;
    }
}
