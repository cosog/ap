package com.cosog.model.drive;

public class ExportReportInstanceData {

	private int Id;

    private String Name_zh_CN;
    
    private String Name_en;
    
    private String Name_ru;

    private String Code;

    private int Sort;

    private int UnitId;

    private String UnitName;

    public void setId(int Id){
        this.Id = Id;
    }
    public int getId(){
        return this.Id;
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
	public String getName_zh_CN() {
		return Name_zh_CN;
	}
	public void setName_zh_CN(String name_zh_CN) {
		Name_zh_CN = name_zh_CN;
	}
	public String getName_en() {
		return Name_en;
	}
	public void setName_en(String name_en) {
		Name_en = name_en;
	}
	public String getName_ru() {
		return Name_ru;
	}
	public void setName_ru(String name_ru) {
		Name_ru = name_ru;
	}
}
