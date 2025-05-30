package com.cosog.model.gridmodel;

import java.util.List;

public class WellHandsontableChangedData {

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

	    private String orgName="";

	    private String deviceName="";
	    
	    private String deviceTypeName_zh_CN;
	    
		private String deviceTypeName_en;
		
		private String deviceTypeName_ru;
	    
	    private String applicationScenariosName="";
	    
	    private String calculateTypeName="";

	    private String instanceName="";
	    
	    private String displayInstanceName="";
	    
	    private String reportInstanceName="";
	    
	    private String alarmInstanceName="";
	    
	    private String tcpType="";

	    private String signInId="";
	    
	    private String ipPort="";

	    private String slave="";
	    
	    private String peakDelay="";
	    
	    private String videoUrl1="";
	    
	    private String videoKeyName1="";
	    
	    private String videoUrl2="";
	    
	    private String videoKeyName2="";
	    
	    private String statusName="";
	    
	    private String commissioningDate="";
	    
	    private String sortNum="";
	    
	    private String crudeOilDensity="";
	    
	    private String waterDensity="";
	    
	    private String naturalGasRelativeDensity="";
	    
	    private String saturationPressure="";
	    
	    private String reservoirDepth="";
	    
	    private String reservoirTemperature="";
	    
	    private String tubingPressure="";
	    
	    private String casingPressure="";
	    
	    private String wellHeadTemperature="";
	    
	    private String waterCut="";
	    
	    private String productionGasOilRatio="";
	    
	    private String producingfluidLevel="";
	    
	    private String pumpSettingDepth="";
	    
	    private String pumpType="";
	    
	    private String barrelType="";
	    
	    private String pumpGrade="";
	    
	    private String pumpBoreDiameter="";
	    
	    private String plungerLength="";
	    
	    private String tubingStringInsideDiameter="";
	    
	    private String casingStringInsideDiameter="";
	    
	    private String rodGrade1="";
	    
	    private String rodOutsideDiameter1="";
	    
	    private String rodInsideDiameter1="";
	    
	    private String rodLength1="";
	    
	    private String rodGrade2="";
	    
	    private String rodOutsideDiameter2="";
	    
	    private String rodInsideDiameter2="";
	    
	    private String rodLength2="";
	    
	    private String rodGrade3="";
	    
	    private String rodOutsideDiameter3="";
	    
	    private String rodInsideDiameter3="";
	    
	    private String rodLength3="";
	    
	    private String rodGrade4="";
	    
	    private String rodOutsideDiameter4="";
	    
	    private String rodInsideDiameter4="";
	    
	    private String rodLength4="";
	    
	    private String manualInterventionResultName="";
	    
	    private String netGrossRatio="";
	    
	    private String netGrossValue="";
	    
	    private String levelCorrectValue="";
	    
	    private String manufacturer="";
	    
	    private String model="";
	    
	    private String stroke="";
	    
	    private String crankRotationDirection="";
	    
	    private String offsetAngleOfCrank="";
	    
	    private String crankGravityRadius="";
	    
	    private String singleCrankWeight="";
	    
	    private String singleCrankPinWeight="";
	    
	    private String structuralUnbalance="";
	    
	    private String balanceWeight="";
	    
	    private String balancePosition="";
	    
	    private String barrelLength="";
	    
	    private String barrelSeries="";
	    
	    private String rotorDiameter="";
	    
	    private String QPR="";
	    
	    private int saveSign;
	    
	    private String saveStr;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getOrgName() {
			return orgName;
		}

		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}

		public String getSortNum() {
			return sortNum;
		}

		public void setSortNum(String sortNum) {
			this.sortNum = sortNum;
		}

		public String getSignInId() {
			return signInId;
		}

		public void setSignInId(String signInId) {
			this.signInId = signInId;
		}

		public String getSlave() {
			return slave;
		}

		public void setSlave(String slave) {
			this.slave = slave;
		}

		public String getInstanceName() {
			return instanceName;
		}

		public void setInstanceName(String instanceName) {
			this.instanceName = instanceName;
		}

		public String getAlarmInstanceName() {
			return alarmInstanceName;
		}

		public void setAlarmInstanceName(String alarmInstanceName) {
			this.alarmInstanceName = alarmInstanceName;
		}

		public int getSaveSign() {
			return saveSign;
		}

		public void setSaveSign(int saveSign) {
			this.saveSign = saveSign;
		}

		public String getSaveStr() {
			return saveStr;
		}

		public void setSaveStr(String saveStr) {
			this.saveStr = saveStr;
		}

		public String getStatusName() {
			return statusName;
		}

		public void setStatusName(String statusName) {
			this.statusName = statusName;
		}

		public String getDisplayInstanceName() {
			return displayInstanceName;
		}

		public void setDisplayInstanceName(String displayInstanceName) {
			this.displayInstanceName = displayInstanceName;
		}

		public String getCrudeOilDensity() {
			return crudeOilDensity;
		}

		public void setCrudeOilDensity(String crudeOilDensity) {
			this.crudeOilDensity = crudeOilDensity;
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

		public String getWellHeadTemperature() {
			return wellHeadTemperature;
		}

		public void setWellHeadTemperature(String wellHeadTemperature) {
			this.wellHeadTemperature = wellHeadTemperature;
		}

		public String getWaterCut() {
			return waterCut;
		}

		public void setWaterCut(String waterCut) {
			this.waterCut = waterCut;
		}

		public String getProductionGasOilRatio() {
			return productionGasOilRatio;
		}

		public void setProductionGasOilRatio(String productionGasOilRatio) {
			this.productionGasOilRatio = productionGasOilRatio;
		}

		public String getProducingfluidLevel() {
			return producingfluidLevel;
		}

		public void setProducingfluidLevel(String producingfluidLevel) {
			this.producingfluidLevel = producingfluidLevel;
		}

		public String getPumpSettingDepth() {
			return pumpSettingDepth;
		}

		public void setPumpSettingDepth(String pumpSettingDepth) {
			this.pumpSettingDepth = pumpSettingDepth;
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

		public String getPumpGrade() {
			return pumpGrade;
		}

		public void setPumpGrade(String pumpGrade) {
			this.pumpGrade = pumpGrade;
		}

		public String getPumpBoreDiameter() {
			return pumpBoreDiameter;
		}

		public void setPumpBoreDiameter(String pumpBoreDiameter) {
			this.pumpBoreDiameter = pumpBoreDiameter;
		}

		public String getPlungerLength() {
			return plungerLength;
		}

		public void setPlungerLength(String plungerLength) {
			this.plungerLength = plungerLength;
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

		public String getNetGrossRatio() {
			return netGrossRatio;
		}

		public void setNetGrossRatio(String netGrossRatio) {
			this.netGrossRatio = netGrossRatio;
		}

		public String getManufacturer() {
			return manufacturer;
		}

		public void setManufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public String getStroke() {
			return stroke;
		}

		public void setStroke(String stroke) {
			this.stroke = stroke;
		}

		public String getCrankRotationDirection() {
			return crankRotationDirection;
		}

		public void setCrankRotationDirection(String crankRotationDirection) {
			this.crankRotationDirection = crankRotationDirection;
		}

		public String getOffsetAngleOfCrank() {
			return offsetAngleOfCrank;
		}

		public void setOffsetAngleOfCrank(String offsetAngleOfCrank) {
			this.offsetAngleOfCrank = offsetAngleOfCrank;
		}

		public String getCrankGravityRadius() {
			return crankGravityRadius;
		}

		public void setCrankGravityRadius(String crankGravityRadius) {
			this.crankGravityRadius = crankGravityRadius;
		}

		public String getSingleCrankWeight() {
			return singleCrankWeight;
		}

		public void setSingleCrankWeight(String singleCrankWeight) {
			this.singleCrankWeight = singleCrankWeight;
		}

		public String getSingleCrankPinWeight() {
			return singleCrankPinWeight;
		}

		public void setSingleCrankPinWeight(String singleCrankPinWeight) {
			this.singleCrankPinWeight = singleCrankPinWeight;
		}

		public String getStructuralUnbalance() {
			return structuralUnbalance;
		}

		public void setStructuralUnbalance(String structuralUnbalance) {
			this.structuralUnbalance = structuralUnbalance;
		}

		public String getBalanceWeight() {
			return balanceWeight;
		}

		public void setBalanceWeight(String balanceWeight) {
			this.balanceWeight = balanceWeight;
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

		public String getBalancePosition() {
			return balancePosition;
		}

		public void setBalancePosition(String balancePosition) {
			this.balancePosition = balancePosition;
		}

		public String getNetGrossValue() {
			return netGrossValue;
		}

		public void setNetGrossValue(String netGrossValue) {
			this.netGrossValue = netGrossValue;
		}

		public String getTcpType() {
			return tcpType;
		}

		public void setTcpType(String tcpType) {
			this.tcpType = tcpType;
		}

		public String getPeakDelay() {
			return peakDelay;
		}

		public void setPeakDelay(String peakDelay) {
			this.peakDelay = peakDelay;
		}

		public String getVideoUrl1() {
			return videoUrl1;
		}

		public void setVideoUrl1(String videoUrl1) {
			this.videoUrl1 = videoUrl1;
		}

		public String getVideoUrl2() {
			return videoUrl2;
		}

		public void setVideoUrl2(String videoUrl2) {
			this.videoUrl2 = videoUrl2;
		}

		public String getIpPort() {
			return ipPort;
		}

		public void setIpPort(String ipPort) {
			this.ipPort = ipPort;
		}

		public String getReportInstanceName() {
			return reportInstanceName;
		}

		public void setReportInstanceName(String reportInstanceName) {
			this.reportInstanceName = reportInstanceName;
		}

		public String getManualInterventionResultName() {
			return manualInterventionResultName;
		}

		public void setManualInterventionResultName(String manualInterventionResultName) {
			this.manualInterventionResultName = manualInterventionResultName;
		}

		public String getLevelCorrectValue() {
			return levelCorrectValue;
		}

		public void setLevelCorrectValue(String levelCorrectValue) {
			this.levelCorrectValue = levelCorrectValue;
		}

		public String getVideoKeyName1() {
			return videoKeyName1;
		}

		public void setVideoKeyName1(String videoKeyName1) {
			this.videoKeyName1 = videoKeyName1;
		}

		public String getVideoKeyName2() {
			return videoKeyName2;
		}

		public void setVideoKeyName2(String videoKeyName2) {
			this.videoKeyName2 = videoKeyName2;
		}

		public String getDeviceName() {
			return deviceName;
		}

		public void setDeviceName(String deviceName) {
			this.deviceName = deviceName;
		}

		public String getApplicationScenariosName() {
			return applicationScenariosName;
		}

		public void setApplicationScenariosName(String applicationScenariosName) {
			this.applicationScenariosName = applicationScenariosName;
		}

		public String getDeviceTypeName_zh_CN() {
			return deviceTypeName_zh_CN;
		}

		public void setDeviceTypeName_zh_CN(String deviceTypeName_zh_CN) {
			this.deviceTypeName_zh_CN = deviceTypeName_zh_CN;
		}

		public String getDeviceTypeName_en() {
			return deviceTypeName_en;
		}

		public void setDeviceTypeName_en(String deviceTypeName_en) {
			this.deviceTypeName_en = deviceTypeName_en;
		}

		public String getDeviceTypeName_ru() {
			return deviceTypeName_ru;
		}

		public void setDeviceTypeName_ru(String deviceTypeName_ru) {
			this.deviceTypeName_ru = deviceTypeName_ru;
		}

		public String getCommissioningDate() {
			return commissioningDate;
		}

		public void setCommissioningDate(String commissioningDate) {
			this.commissioningDate = commissioningDate;
		}

		public String getCalculateTypeName() {
			return calculateTypeName;
		}

		public void setCalculateTypeName(String calculateTypeName) {
			this.calculateTypeName = calculateTypeName;
		}
	    
	}
}
