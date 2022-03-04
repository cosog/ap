package com.cosog.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


public class PCPProductionData{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer wellId;
	private String acqTime;
	private Integer liftingType;
	private Integer displacementType;
	private Double runTime;
	private Double crudeOilDensity;
	private Double waterDensity;
	private Double naturalGasRelativeDensity;
	private Double saturationPressure;
	private Double reservoirDepth;
	private Double reservoirTemperature;
	private Double tubingPressure;
	private Double casingPressure;
	private Double wellHeadFluidTemperature;
	private Double volumeWaterCut;
	private Double weightWaterCut;
	private Double productionGasOilRatio;
	private Double producingfluidLevel;
	private Double pumpSettingDepth;
	private Double barrelLength;
	private Double barrelSeries;
	private Double rotorDiameter;
	private Double QPR;
	
	private Double tubingStringInsideDiameter;
	private Double casingStringInsideDiameter;
	
	private String pumpType;
	private String barrelType;
	
	
	private String rodGrade1;
	private Integer rodOutsideDiameter1;
	private Integer rodInsideDiameter1;
	private Double rodLength1;
	private String rodGrade2;
	private Integer rodOutsideDiameter2;
	private Integer rodInsideDiameter2;
	private Double rodLength2;
	private String rodGrade3;
	private Integer rodOutsideDiameter3;
	private Integer rodInsideDiameter3;
	private Double rodLength3;
	private String rodGrade4;
	private Integer rodOutsideDiameter4;
	private Integer rodInsideDiameter4;
	private Double rodLength4;
	
	
	private Integer anchoringState;
	private Double netGrossRatio;
	private Integer runtimeEfficiencySourceName;
	
	public PCPProductionData() {
		super();
	}

	public PCPProductionData(Integer id, Integer wellId, String acqTime, Integer liftingType, Integer displacementType,
			Double runTime, Double crudeOilDensity, Double waterDensity, Double naturalGasRelativeDensity,
			Double saturationPressure, Double reservoirDepth, Double reservoirTemperature, Double tubingPressure,
			Double casingPressure, Double wellHeadFluidTemperature, Double volumeWaterCut, Double weightWaterCut,
			Double productionGasOilRatio, Double producingfluidLevel, Double pumpSettingDepth, Double barrelLength,
			Double barrelSeries, Double rotorDiameter, Double qPR, Double tubingStringInsideDiameter,
			Double casingStringInsideDiameter, String pumpType, String barrelType, String rodGrade1,
			Integer rodOutsideDiameter1, Integer rodInsideDiameter1, Double rodLength1, String rodGrade2,
			Integer rodOutsideDiameter2, Integer rodInsideDiameter2, Double rodLength2, String rodGrade3,
			Integer rodOutsideDiameter3, Integer rodInsideDiameter3, Double rodLength3, String rodGrade4,
			Integer rodOutsideDiameter4, Integer rodInsideDiameter4, Double rodLength4, Integer anchoringState,
			Double netGrossRatio, Integer runtimeEfficiencySourceName) {
		super();
		this.id = id;
		this.wellId = wellId;
		this.acqTime = acqTime;
		this.liftingType = liftingType;
		this.displacementType = displacementType;
		this.runTime = runTime;
		this.crudeOilDensity = crudeOilDensity;
		this.waterDensity = waterDensity;
		this.naturalGasRelativeDensity = naturalGasRelativeDensity;
		this.saturationPressure = saturationPressure;
		this.reservoirDepth = reservoirDepth;
		this.reservoirTemperature = reservoirTemperature;
		this.tubingPressure = tubingPressure;
		this.casingPressure = casingPressure;
		this.wellHeadFluidTemperature = wellHeadFluidTemperature;
		this.volumeWaterCut = volumeWaterCut;
		this.weightWaterCut = weightWaterCut;
		this.productionGasOilRatio = productionGasOilRatio;
		this.producingfluidLevel = producingfluidLevel;
		this.pumpSettingDepth = pumpSettingDepth;
		this.barrelLength = barrelLength;
		this.barrelSeries = barrelSeries;
		this.rotorDiameter = rotorDiameter;
		QPR = qPR;
		this.tubingStringInsideDiameter = tubingStringInsideDiameter;
		this.casingStringInsideDiameter = casingStringInsideDiameter;
		this.pumpType = pumpType;
		this.barrelType = barrelType;
		this.rodGrade1 = rodGrade1;
		this.rodOutsideDiameter1 = rodOutsideDiameter1;
		this.rodInsideDiameter1 = rodInsideDiameter1;
		this.rodLength1 = rodLength1;
		this.rodGrade2 = rodGrade2;
		this.rodOutsideDiameter2 = rodOutsideDiameter2;
		this.rodInsideDiameter2 = rodInsideDiameter2;
		this.rodLength2 = rodLength2;
		this.rodGrade3 = rodGrade3;
		this.rodOutsideDiameter3 = rodOutsideDiameter3;
		this.rodInsideDiameter3 = rodInsideDiameter3;
		this.rodLength3 = rodLength3;
		this.rodGrade4 = rodGrade4;
		this.rodOutsideDiameter4 = rodOutsideDiameter4;
		this.rodInsideDiameter4 = rodInsideDiameter4;
		this.rodLength4 = rodLength4;
		this.anchoringState = anchoringState;
		this.netGrossRatio = netGrossRatio;
		this.runtimeEfficiencySourceName = runtimeEfficiencySourceName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getWellId() {
		return wellId;
	}

	public void setWellId(Integer wellId) {
		this.wellId = wellId;
	}

	public String getAcqTime() {
		return acqTime;
	}

	public void setAcqTime(String acqTime) {
		this.acqTime = acqTime;
	}

	public Integer getLiftingType() {
		return liftingType;
	}

	public void setLiftingType(Integer liftingType) {
		this.liftingType = liftingType;
	}

	public Integer getDisplacementType() {
		return displacementType;
	}

	public void setDisplacementType(Integer displacementType) {
		this.displacementType = displacementType;
	}

	public Double getRunTime() {
		return runTime;
	}

	public void setRunTime(Double runTime) {
		this.runTime = runTime;
	}

	public Double getCrudeOilDensity() {
		return crudeOilDensity;
	}

	public void setCrudeOilDensity(Double crudeOilDensity) {
		this.crudeOilDensity = crudeOilDensity;
	}

	public Double getWaterDensity() {
		return waterDensity;
	}

	public void setWaterDensity(Double waterDensity) {
		this.waterDensity = waterDensity;
	}

	public Double getNaturalGasRelativeDensity() {
		return naturalGasRelativeDensity;
	}

	public void setNaturalGasRelativeDensity(Double naturalGasRelativeDensity) {
		this.naturalGasRelativeDensity = naturalGasRelativeDensity;
	}

	public Double getSaturationPressure() {
		return saturationPressure;
	}

	public void setSaturationPressure(Double saturationPressure) {
		this.saturationPressure = saturationPressure;
	}

	public Double getReservoirDepth() {
		return reservoirDepth;
	}

	public void setReservoirDepth(Double reservoirDepth) {
		this.reservoirDepth = reservoirDepth;
	}

	public Double getReservoirTemperature() {
		return reservoirTemperature;
	}

	public void setReservoirTemperature(Double reservoirTemperature) {
		this.reservoirTemperature = reservoirTemperature;
	}

	public Double getTubingPressure() {
		return tubingPressure;
	}

	public void setTubingPressure(Double tubingPressure) {
		this.tubingPressure = tubingPressure;
	}

	public Double getCasingPressure() {
		return casingPressure;
	}

	public void setCasingPressure(Double casingPressure) {
		this.casingPressure = casingPressure;
	}

	public Double getWellHeadFluidTemperature() {
		return wellHeadFluidTemperature;
	}

	public void setWellHeadFluidTemperature(Double wellHeadFluidTemperature) {
		this.wellHeadFluidTemperature = wellHeadFluidTemperature;
	}

	public Double getVolumeWaterCut() {
		return volumeWaterCut;
	}

	public void setVolumeWaterCut(Double volumeWaterCut) {
		this.volumeWaterCut = volumeWaterCut;
	}

	public Double getWeightWaterCut() {
		return weightWaterCut;
	}

	public void setWeightWaterCut(Double weightWaterCut) {
		this.weightWaterCut = weightWaterCut;
	}

	public Double getProductionGasOilRatio() {
		return productionGasOilRatio;
	}

	public void setProductionGasOilRatio(Double productionGasOilRatio) {
		this.productionGasOilRatio = productionGasOilRatio;
	}

	public Double getProducingfluidLevel() {
		return producingfluidLevel;
	}

	public void setProducingfluidLevel(Double producingfluidLevel) {
		this.producingfluidLevel = producingfluidLevel;
	}

	public Double getPumpSettingDepth() {
		return pumpSettingDepth;
	}

	public void setPumpSettingDepth(Double pumpSettingDepth) {
		this.pumpSettingDepth = pumpSettingDepth;
	}

	public Double getBarrelLength() {
		return barrelLength;
	}

	public void setBarrelLength(Double barrelLength) {
		this.barrelLength = barrelLength;
	}

	public Double getBarrelSeries() {
		return barrelSeries;
	}

	public void setBarrelSeries(Double barrelSeries) {
		this.barrelSeries = barrelSeries;
	}

	public Double getRotorDiameter() {
		return rotorDiameter;
	}

	public void setRotorDiameter(Double rotorDiameter) {
		this.rotorDiameter = rotorDiameter;
	}

	public Double getQPR() {
		return QPR;
	}

	public void setQPR(Double qPR) {
		QPR = qPR;
	}

	public Double getTubingStringInsideDiameter() {
		return tubingStringInsideDiameter;
	}

	public void setTubingStringInsideDiameter(Double tubingStringInsideDiameter) {
		this.tubingStringInsideDiameter = tubingStringInsideDiameter;
	}

	public Double getCasingStringInsideDiameter() {
		return casingStringInsideDiameter;
	}

	public void setCasingStringInsideDiameter(Double casingStringInsideDiameter) {
		this.casingStringInsideDiameter = casingStringInsideDiameter;
	}

	public String getPumpType() {
		return pumpType;
	}

	public void setPumpType(String pumpType) {
		this.pumpType = pumpType;
	}

	public String getBarrelType() {
		return barrelType;
	}

	public void setBarrelType(String barrelType) {
		this.barrelType = barrelType;
	}

	public String getRodGrade1() {
		return rodGrade1;
	}

	public void setRodGrade1(String rodGrade1) {
		this.rodGrade1 = rodGrade1;
	}

	public Integer getRodOutsideDiameter1() {
		return rodOutsideDiameter1;
	}

	public void setRodOutsideDiameter1(Integer rodOutsideDiameter1) {
		this.rodOutsideDiameter1 = rodOutsideDiameter1;
	}

	public Integer getRodInsideDiameter1() {
		return rodInsideDiameter1;
	}

	public void setRodInsideDiameter1(Integer rodInsideDiameter1) {
		this.rodInsideDiameter1 = rodInsideDiameter1;
	}

	public Double getRodLength1() {
		return rodLength1;
	}

	public void setRodLength1(Double rodLength1) {
		this.rodLength1 = rodLength1;
	}

	public String getRodGrade2() {
		return rodGrade2;
	}

	public void setRodGrade2(String rodGrade2) {
		this.rodGrade2 = rodGrade2;
	}

	public Integer getRodOutsideDiameter2() {
		return rodOutsideDiameter2;
	}

	public void setRodOutsideDiameter2(Integer rodOutsideDiameter2) {
		this.rodOutsideDiameter2 = rodOutsideDiameter2;
	}

	public Integer getRodInsideDiameter2() {
		return rodInsideDiameter2;
	}

	public void setRodInsideDiameter2(Integer rodInsideDiameter2) {
		this.rodInsideDiameter2 = rodInsideDiameter2;
	}

	public Double getRodLength2() {
		return rodLength2;
	}

	public void setRodLength2(Double rodLength2) {
		this.rodLength2 = rodLength2;
	}

	public String getRodGrade3() {
		return rodGrade3;
	}

	public void setRodGrade3(String rodGrade3) {
		this.rodGrade3 = rodGrade3;
	}

	public Integer getRodOutsideDiameter3() {
		return rodOutsideDiameter3;
	}

	public void setRodOutsideDiameter3(Integer rodOutsideDiameter3) {
		this.rodOutsideDiameter3 = rodOutsideDiameter3;
	}

	public Integer getRodInsideDiameter3() {
		return rodInsideDiameter3;
	}

	public void setRodInsideDiameter3(Integer rodInsideDiameter3) {
		this.rodInsideDiameter3 = rodInsideDiameter3;
	}

	public Double getRodLength3() {
		return rodLength3;
	}

	public void setRodLength3(Double rodLength3) {
		this.rodLength3 = rodLength3;
	}

	public String getRodGrade4() {
		return rodGrade4;
	}

	public void setRodGrade4(String rodGrade4) {
		this.rodGrade4 = rodGrade4;
	}

	public Integer getRodOutsideDiameter4() {
		return rodOutsideDiameter4;
	}

	public void setRodOutsideDiameter4(Integer rodOutsideDiameter4) {
		this.rodOutsideDiameter4 = rodOutsideDiameter4;
	}

	public Integer getRodInsideDiameter4() {
		return rodInsideDiameter4;
	}

	public void setRodInsideDiameter4(Integer rodInsideDiameter4) {
		this.rodInsideDiameter4 = rodInsideDiameter4;
	}

	public Double getRodLength4() {
		return rodLength4;
	}

	public void setRodLength4(Double rodLength4) {
		this.rodLength4 = rodLength4;
	}

	public Integer getAnchoringState() {
		return anchoringState;
	}

	public void setAnchoringState(Integer anchoringState) {
		this.anchoringState = anchoringState;
	}

	public Double getNetGrossRatio() {
		return netGrossRatio;
	}

	public void setNetGrossRatio(Double netGrossRatio) {
		this.netGrossRatio = netGrossRatio;
	}

	public Integer getRuntimeEfficiencySourceName() {
		return runtimeEfficiencySourceName;
	}

	public void setRuntimeEfficiencySourceName(Integer runtimeEfficiencySourceName) {
		this.runtimeEfficiencySourceName = runtimeEfficiencySourceName;
	}
}