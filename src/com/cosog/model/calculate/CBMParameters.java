package com.cosog.model.calculate;

import java.io.Serializable;

public class CBMParameters  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String Block;

    private String WellCategory;

    private String WellType;

    private String ProductionInterval;

    private String CompletedInterval;

    private String DewateringMethod;

    private String Screen;

    private String PumpSettingDepth;

    private String PumpBoreDiameter;

    private String PressureGaugeDepth;

    private String CoalSeamRoofDepth;

    private String CoalSeamFloorDepth;
    
    public void setBlock(String Block){
        this.Block = Block;
    }
    public String getBlock(){
        return this.Block;
    }
    public void setWellCategory(String WellCategory){
        this.WellCategory = WellCategory;
    }
    public String getWellCategory(){
        return this.WellCategory;
    }
    public void setWellType(String WellType){
        this.WellType = WellType;
    }
    public String getWellType(){
        return this.WellType;
    }
    public void setProductionInterval(String ProductionInterval){
        this.ProductionInterval = ProductionInterval;
    }
    public String getProductionInterval(){
        return this.ProductionInterval;
    }
    public void setCompletedInterval(String CompletedInterval){
        this.CompletedInterval = CompletedInterval;
    }
    public String getCompletedInterval(){
        return this.CompletedInterval;
    }
    public void setDewateringMethod(String DewateringMethod){
        this.DewateringMethod = DewateringMethod;
    }
    public String getDewateringMethod(){
        return this.DewateringMethod;
    }
    public void setScreen(String Screen){
        this.Screen = Screen;
    }
    public String getScreen(){
        return this.Screen;
    }
    public void setPumpSettingDepth(String PumpSettingDepth){
        this.PumpSettingDepth = PumpSettingDepth;
    }
    public String getPumpSettingDepth(){
        return this.PumpSettingDepth;
    }
    public void setPumpBoreDiameter(String PumpBoreDiameter){
        this.PumpBoreDiameter = PumpBoreDiameter;
    }
    public String getPumpBoreDiameter(){
        return this.PumpBoreDiameter;
    }
    public void setPressureGaugeDepth(String PressureGaugeDepth){
        this.PressureGaugeDepth = PressureGaugeDepth;
    }
    public String getPressureGaugeDepth(){
        return this.PressureGaugeDepth;
    }
    public void setCoalSeamRoofDepth(String CoalSeamRoofDepth){
        this.CoalSeamRoofDepth = CoalSeamRoofDepth;
    }
    public String getCoalSeamRoofDepth(){
        return this.CoalSeamRoofDepth;
    }
    public void setCoalSeamFloorDepth(String CoalSeamFloorDepth){
        this.CoalSeamFloorDepth = CoalSeamFloorDepth;
    }
    public String getCoalSeamFloorDepth(){
        return this.CoalSeamFloorDepth;
    }

}
