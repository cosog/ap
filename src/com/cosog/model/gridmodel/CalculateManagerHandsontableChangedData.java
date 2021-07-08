package com.cosog.model.gridmodel;

import java.util.List;

public class CalculateManagerHandsontableChangedData {

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

	    private String wellName;

	    private String acqTime;
	    
	    private String crudeoilDensity="0.86";
	    
	    private String waterDensity="1";
	    
	    private String naturalGasRelativeDensity="0.7";
	    
	    private String saturationPressure="0";
	    
	    private String reservoirDepth="0";
	    
	    private String reservoirTemperature="0";

	    private String tubingPressure="0.5";

	    private String casingPressure="0.5";

	    private String wellHeadFluidTemperature="35";

	    private String weightWaterCut="0";

	    private String productionGasOilRatio="10";

	    private String producingFluidLevel;

	    private String pumpSettingDepth;
	    
	    private String pumpGrade="1";
	    
	    private String pumpTypeName="管式泵";

	    private String barrelTypeName="整筒泵";

	    private String pumpboreDiameter="38";

	    private String plungerLength="1.2";

	    private String barrelLength="2";

	    private String barrelSeries="0";

	    private String rotorDiameter="0";

	    private String QPR="0";

	    private String tubingStringInsideDiameter="62";

	    private String casingStringInsideDiameter="139.7";

	    private String rodGrade1="D";

	    private String rodOutsideDiameter1="25";

	    private String rodInsideDiameter1="0";

	    private String rodLength1="0";

	    private String rodGrade2="D";

	    private String rodOutsideDiameter2="25";

	    private String rodInsideDiameter2="0";

	    private String rodLength2="0";

	    private String rodGrade3="D";

	    private String rodOutsideDiameter3="25";

	    private String rodInsideDiameter3="0";

	    private String rodLength3="0";

	    private String rodGrade4="D";

	    private String rodOutsideDiameter4="25";

	    private String rodInsideDiameter4="0";

	    private String rodLength4="0";
	    
	    private String anchoringStateName="未锚定";

	    private String netGrossRatio="1";

	    private String resultStatus;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getWellName() {
			return wellName;
		}

		public void setWellName(String wellName) {
			this.wellName = wellName;
		}

		public String getAcqTime() {
			return acqTime;
		}

		public void setAcqTime(String acqTime) {
			this.acqTime = acqTime;
		}

		public String getCrudeoilDensity() {
			return crudeoilDensity;
		}

		public void setCrudeoilDensity(String crudeoilDensity) {
			this.crudeoilDensity = crudeoilDensity;
		}

		public String getWaterDensity() {
			return waterDensity;
		}

		public void setWaterDensity(String waterDensity) {
			this.waterDensity = waterDensity;
		}

		public String getNaturalGasRelativeDensity() {
			return naturalGasRelativeDensity;
		}

		public void setNaturalGasRelativeDensity(String naturalGasRelativeDensity) {
			this.naturalGasRelativeDensity = naturalGasRelativeDensity;
		}

		public String getSaturationPressure() {
			return saturationPressure;
		}

		public void setSaturationPressure(String saturationPressure) {
			this.saturationPressure = saturationPressure;
		}

		public String getReservoirDepth() {
			return reservoirDepth;
		}

		public void setReservoirDepth(String reservoirDepth) {
			this.reservoirDepth = reservoirDepth;
		}

		public String getReservoirTemperature() {
			return reservoirTemperature;
		}

		public void setReservoirTemperature(String reservoirTemperature) {
			this.reservoirTemperature = reservoirTemperature;
		}

		public String getTubingPressure() {
			return tubingPressure;
		}

		public void setTubingPressure(String tubingPressure) {
			this.tubingPressure = tubingPressure;
		}

		public String getCasingPressure() {
			return casingPressure;
		}

		public void setCasingPressure(String casingPressure) {
			this.casingPressure = casingPressure;
		}

		public String getWellHeadFluidTemperature() {
			return wellHeadFluidTemperature;
		}

		public void setWellHeadFluidTemperature(String wellHeadFluidTemperature) {
			this.wellHeadFluidTemperature = wellHeadFluidTemperature;
		}

		public String getWeightWaterCut() {
			return weightWaterCut;
		}

		public void setWeightWaterCut(String weightWaterCut) {
			this.weightWaterCut = weightWaterCut;
		}

		public String getProductionGasOilRatio() {
			return productionGasOilRatio;
		}

		public void setProductionGasOilRatio(String productionGasOilRatio) {
			this.productionGasOilRatio = productionGasOilRatio;
		}

		public String getProducingFluidLevel() {
			return producingFluidLevel;
		}

		public void setProducingFluidLevel(String producingFluidLevel) {
			this.producingFluidLevel = producingFluidLevel;
		}

		public String getPumpSettingDepth() {
			return pumpSettingDepth;
		}

		public void setPumpSettingDepth(String pumpSettingDepth) {
			this.pumpSettingDepth = pumpSettingDepth;
		}

		public String getPumpGrade() {
			return pumpGrade;
		}

		public void setPumpGrade(String pumpGrade) {
			this.pumpGrade = pumpGrade;
		}

		public String getPumpboreDiameter() {
			return pumpboreDiameter;
		}

		public void setPumpboreDiameter(String pumpboreDiameter) {
			this.pumpboreDiameter = pumpboreDiameter;
		}

		public String getPlungerLength() {
			return plungerLength;
		}

		public void setPlungerLength(String plungerLength) {
			this.plungerLength = plungerLength;
		}

		public String getBarrelLength() {
			return barrelLength;
		}

		public void setBarrelLength(String barrelLength) {
			this.barrelLength = barrelLength;
		}

		public String getBarrelSeries() {
			return barrelSeries;
		}

		public void setBarrelSeries(String barrelSeries) {
			this.barrelSeries = barrelSeries;
		}

		public String getRotorDiameter() {
			return rotorDiameter;
		}

		public void setRotorDiameter(String rotorDiameter) {
			this.rotorDiameter = rotorDiameter;
		}

		public String getQPR() {
			return QPR;
		}

		public void setQPR(String qPR) {
			QPR = qPR;
		}

		public String getTubingStringInsideDiameter() {
			return tubingStringInsideDiameter;
		}

		public void setTubingStringInsideDiameter(String tubingStringInsideDiameter) {
			this.tubingStringInsideDiameter = tubingStringInsideDiameter;
		}

		public String getCasingStringInsideDiameter() {
			return casingStringInsideDiameter;
		}

		public void setCasingStringInsideDiameter(String casingStringInsideDiameter) {
			this.casingStringInsideDiameter = casingStringInsideDiameter;
		}

		public String getRodGrade1() {
			return rodGrade1;
		}

		public void setRodGrade1(String rodGrade1) {
			this.rodGrade1 = rodGrade1;
		}

		public String getRodOutsideDiameter1() {
			return rodOutsideDiameter1;
		}

		public void setRodOutsideDiameter1(String rodOutsideDiameter1) {
			this.rodOutsideDiameter1 = rodOutsideDiameter1;
		}

		public String getRodInsideDiameter1() {
			return rodInsideDiameter1;
		}

		public void setRodInsideDiameter1(String rodInsideDiameter1) {
			this.rodInsideDiameter1 = rodInsideDiameter1;
		}

		public String getRodLength1() {
			return rodLength1;
		}

		public void setRodLength1(String rodLength1) {
			this.rodLength1 = rodLength1;
		}

		public String getRodGrade2() {
			return rodGrade2;
		}

		public void setRodGrade2(String rodGrade2) {
			this.rodGrade2 = rodGrade2;
		}

		public String getRodOutsideDiameter2() {
			return rodOutsideDiameter2;
		}

		public void setRodOutsideDiameter2(String rodOutsideDiameter2) {
			this.rodOutsideDiameter2 = rodOutsideDiameter2;
		}

		public String getRodInsideDiameter2() {
			return rodInsideDiameter2;
		}

		public void setRodInsideDiameter2(String rodInsideDiameter2) {
			this.rodInsideDiameter2 = rodInsideDiameter2;
		}

		public String getRodLength2() {
			return rodLength2;
		}

		public void setRodLength2(String rodLength2) {
			this.rodLength2 = rodLength2;
		}

		public String getRodGrade3() {
			return rodGrade3;
		}

		public void setRodGrade3(String rodGrade3) {
			this.rodGrade3 = rodGrade3;
		}

		public String getRodOutsideDiameter3() {
			return rodOutsideDiameter3;
		}

		public void setRodOutsideDiameter3(String rodOutsideDiameter3) {
			this.rodOutsideDiameter3 = rodOutsideDiameter3;
		}

		public String getRodInsideDiameter3() {
			return rodInsideDiameter3;
		}

		public void setRodInsideDiameter3(String rodInsideDiameter3) {
			this.rodInsideDiameter3 = rodInsideDiameter3;
		}

		public String getRodLength3() {
			return rodLength3;
		}

		public void setRodLength3(String rodLength3) {
			this.rodLength3 = rodLength3;
		}

		public String getRodGrade4() {
			return rodGrade4;
		}

		public void setRodGrade4(String rodGrade4) {
			this.rodGrade4 = rodGrade4;
		}

		public String getRodOutsideDiameter4() {
			return rodOutsideDiameter4;
		}

		public void setRodOutsideDiameter4(String rodOutsideDiameter4) {
			this.rodOutsideDiameter4 = rodOutsideDiameter4;
		}

		public String getRodInsideDiameter4() {
			return rodInsideDiameter4;
		}

		public void setRodInsideDiameter4(String rodInsideDiameter4) {
			this.rodInsideDiameter4 = rodInsideDiameter4;
		}

		public String getRodLength4() {
			return rodLength4;
		}

		public void setRodLength4(String rodLength4) {
			this.rodLength4 = rodLength4;
		}

		public String getAnchoringStateName() {
			return anchoringStateName;
		}

		public void setAnchoringStateName(String anchoringStateName) {
			this.anchoringStateName = anchoringStateName;
		}

		public String getNetGrossRatio() {
			return netGrossRatio;
		}

		public void setNetGrossRatio(String netGrossRatio) {
			this.netGrossRatio = netGrossRatio;
		}

		public String getResultStatus() {
			return resultStatus;
		}

		public void setResultStatus(String resultStatus) {
			this.resultStatus = resultStatus;
		}

		public String getPumpTypeName() {
			return pumpTypeName;
		}

		public void setPumpTypeName(String pumpTypeName) {
			this.pumpTypeName = pumpTypeName;
		}

		public String getBarrelTypeName() {
			return barrelTypeName;
		}

		public void setBarrelTypeName(String barrelTypeName) {
			this.barrelTypeName = barrelTypeName;
		}
	}
}
