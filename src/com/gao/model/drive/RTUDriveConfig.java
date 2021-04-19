package com.gao.model.drive;

public class RTUDriveConfig {
	private String DriverName;
    
    private String DriverCode;
    
    private int Sort;

    private DataConfig DataConfig;
    
    public void setDriverName(String DriverName){
        this.DriverName = DriverName;
    }
    public String getDriverName(){
        return this.DriverName;
    }
	public int getSort() {
		return Sort;
	}
	public void setSort(int sort) {
		Sort = sort;
	}
    public void setDataConfig(DataConfig DataConfig){
        this.DataConfig = DataConfig;
    }
    public DataConfig getDataConfig(){
        return this.DataConfig;
    }
	public String getDriverCode() {
		return DriverCode;
	}
	public void setDriverCode(String driverCode) {
		DriverCode = driverCode;
	}
	
	public static class Item
	{
	    private int Address;

	    private int DataType;

	    private int Length;

	    private float Zoom;

	    public void setAddress(int Address){
	        this.Address = Address;
	    }
	    public int getAddress(){
	        return this.Address;
	    }
	    public void setDataType(int DataType){
	        this.DataType = DataType;
	    }
	    public int getDataType(){
	        return this.DataType;
	    }
	    public void setLength(int Length){
	        this.Length = Length;
	    }
	    public int getLength(){
	        return this.Length;
	    }
	    public void setZoom(float Zoom){
	        this.Zoom = Zoom;
	    }
	    public float getZoom(){
	        return this.Zoom;
	    }
	}
	
	public static class DataConfig
	{
		private Item DiscreteAcquisitionInterval;
		
	    private Item RunStatus;
	    private Item RunControl;
	    
	    private Item CurrentA;
	    private Item CurrentB;
	    private Item CurrentC;
	    private Item VoltageA;
	    private Item VoltageB;
	    private Item VoltageC;
	    private Item ActivePowerConsumption;
	    private Item ReactivePowerConsumption;
	    private Item ActivePower;
	    private Item ReactivePower;
	    private Item ReversePower;
	    private Item PowerFactor;
	    
	    private Item TubingPressure;
	    private Item CasingPressure;
	    private Item BackPressure;
	    private Item WellHeadFluidTemperature;
	    private Item ProducingfluidLevel;
	    private Item WaterCut;
	    
	    private Item SetFrequency;
	    private Item RunFrequency;
	    
	    private Item RPM;
	    private Item Torque;
	    
	    private Item FSDiagramAcquisitionInterval;
	    private Item FSDiagramSetPointCount;
	    private Item FSDiagramPointCount;
	    private Item AcqTime;
	    private Item SPM;
	    private Item Stroke;
	    
	    private Item SDiagram;
	    private Item FDiagram;
	    private Item ADiagram;
	    private Item PDiagram;
	    
	    private Item IaDiagram;
	    private Item IbDiagram;
	    private Item IcDiagram;
	    
	    
	    private Item BalaceControlStatus;
	    private Item BalanceControlMode;
	    private Item BalanceCalculateMode;
	    private Item BalanceAwayTime;
	    private Item BalanceCloseTime;
	    private Item BalanceStrokeCount;
	    private Item BalanceOperationUpLimit;
	    private Item BalanceOperationDownLimit;
	    private Item BalanceAwayTimePerBeat;
	    private Item BalanceCloseTimePerBeat;
	    
	    private Item CurrentUpLimit;
	    private Item CurrentDownLimit;
	    private Item PowerUpLimit;
	    private Item PowerDownLimit;
	    private Item ImmediatelyAcquisition;
	    
	    private Item UpStrokeIMax;
	    private Item DownStrokeIMax;
	    private Item IDegreeBalance;
	    private Item UpStrokeWattMax;
	    private Item DownStrokeWattMax;
	    private Item WattDegreeBalance;
	    private Item CurrentAMax;
	    private Item CurrentAMin;
	    private Item CurrentBMax;
	    private Item CurrentBMin;
	    private Item CurrentCMax;
	    private Item CurrentCMin;
	    private Item ElectricDiagnosisResult;

	    public void setRunStatus(Item RunStatus){
	        this.RunStatus = RunStatus;
	    }
	    public Item getRunStatus(){
	        return this.RunStatus;
	    }
	    public void setRunControl(Item RunControl){
	        this.RunControl = RunControl;
	    }
	    public Item getRunControl(){
	        return this.RunControl;
	    }
	    public void setCurrentA(Item CurrentA){
	        this.CurrentA = CurrentA;
	    }
	    public Item getCurrentA(){
	        return this.CurrentA;
	    }
	    public void setCurrentB(Item CurrentB){
	        this.CurrentB = CurrentB;
	    }
	    public Item getCurrentB(){
	        return this.CurrentB;
	    }
	    public void setCurrentC(Item CurrentC){
	        this.CurrentC = CurrentC;
	    }
	    public Item getCurrentC(){
	        return this.CurrentC;
	    }
		public Item getVoltageA() {
			return VoltageA;
		}
		public void setVoltageA(Item voltageA) {
			VoltageA = voltageA;
		}
		public Item getVoltageB() {
			return VoltageB;
		}
		public void setVoltageB(Item voltageB) {
			VoltageB = voltageB;
		}
		public Item getVoltageC() {
			return VoltageC;
		}
		public void setVoltageC(Item voltageC) {
			VoltageC = voltageC;
		}
		public Item getActivePowerConsumption() {
			return ActivePowerConsumption;
		}
		public void setActivePowerConsumption(Item activePowerConsumption) {
			ActivePowerConsumption = activePowerConsumption;
		}
		public Item getReactivePowerConsumption() {
			return ReactivePowerConsumption;
		}
		public void setReactivePowerConsumption(Item reactivePowerConsumption) {
			ReactivePowerConsumption = reactivePowerConsumption;
		}
		public Item getActivePower() {
			return ActivePower;
		}
		public void setActivePower(Item activePower) {
			ActivePower = activePower;
		}
		public Item getReactivePower() {
			return ReactivePower;
		}
		public void setReactivePower(Item reactivePower) {
			ReactivePower = reactivePower;
		}
		public Item getReversePower() {
			return ReversePower;
		}
		public void setReversePower(Item reversePower) {
			ReversePower = reversePower;
		}
		public Item getPowerFactor() {
			return PowerFactor;
		}
		public void setPowerFactor(Item powerFactor) {
			PowerFactor = powerFactor;
		}
		public Item getTubingPressure() {
			return TubingPressure;
		}
		public void setTubingPressure(Item tubingPressure) {
			TubingPressure = tubingPressure;
		}
		public Item getCasingPressure() {
			return CasingPressure;
		}
		public void setCasingPressure(Item casingPressure) {
			CasingPressure = casingPressure;
		}
		public Item getBackPressure() {
			return BackPressure;
		}
		public void setBackPressure(Item backPressure) {
			BackPressure = backPressure;
		}
		public Item getWellHeadFluidTemperature() {
			return WellHeadFluidTemperature;
		}
		public void setWellHeadFluidTemperature(Item wellHeadFluidTemperature) {
			WellHeadFluidTemperature = wellHeadFluidTemperature;
		}
		public Item getSetFrequency() {
			return SetFrequency;
		}
		public void setSetFrequency(Item SetFrequency) {
			this.SetFrequency = SetFrequency;
		}
		public Item getRunFrequency() {
			return RunFrequency;
		}
		public void setRunFrequency(Item RunFrequency) {
			this.RunFrequency = RunFrequency;
		}
		public Item getRPM() {
			return RPM;
		}
		public void setRPM(Item rPM) {
			RPM = rPM;
		}
		public Item getTorque() {
			return Torque;
		}
		public void setTorque(Item torque) {
			Torque = torque;
		}
		public Item getFSDiagramPointCount() {
			return FSDiagramPointCount;
		}
		public void setFSDiagramPointCount(Item fSDiagramPointCount) {
			FSDiagramPointCount = fSDiagramPointCount;
		}
		public Item getAcqTime() {
			return AcqTime;
		}
		public void setAcqTime(Item acqTime) {
			AcqTime = acqTime;
		}
		public Item getSPM() {
			return SPM;
		}
		public void setSPM(Item sPM) {
			SPM = sPM;
		}
		public Item getStroke() {
			return Stroke;
		}
		public void setStroke(Item stroke) {
			Stroke = stroke;
		}
		public Item getSDiagram() {
			return SDiagram;
		}
		public void setSDiagram(Item sDiagram) {
			SDiagram = sDiagram;
		}
		public Item getFDiagram() {
			return FDiagram;
		}
		public void setFDiagram(Item fDiagram) {
			FDiagram = fDiagram;
		}
		public Item getADiagram() {
			return ADiagram;
		}
		public void setADiagram(Item aDiagram) {
			ADiagram = aDiagram;
		}
		public Item getPDiagram() {
			return PDiagram;
		}
		public void setPDiagram(Item pDiagram) {
			PDiagram = pDiagram;
		}
		public Item getProducingfluidLevel() {
			return ProducingfluidLevel;
		}
		public void setProducingfluidLevel(Item producingfluidLevel) {
			ProducingfluidLevel = producingfluidLevel;
		}
		public Item getWaterCut() {
			return WaterCut;
		}
		public void setWaterCut(Item waterCut) {
			WaterCut = waterCut;
		}
		public Item getFSDiagramAcquisitionInterval() {
			return FSDiagramAcquisitionInterval;
		}
		public void setFSDiagramAcquisitionInterval(Item fSDiagramAcquisitionInterval) {
			FSDiagramAcquisitionInterval = fSDiagramAcquisitionInterval;
		}
		public Item getFSDiagramSetPointCount() {
			return FSDiagramSetPointCount;
		}
		public void setFSDiagramSetPointCount(Item fSDiagramSetPointCount) {
			FSDiagramSetPointCount = fSDiagramSetPointCount;
		}
		public Item getBalaceControlStatus() {
			return BalaceControlStatus;
		}
		public void setBalaceControlStatus(Item balaceControlStatus) {
			BalaceControlStatus = balaceControlStatus;
		}
		public Item getBalanceControlMode() {
			return BalanceControlMode;
		}
		public void setBalanceControlMode(Item balanceControlMode) {
			BalanceControlMode = balanceControlMode;
		}
		public Item getBalanceCalculateMode() {
			return BalanceCalculateMode;
		}
		public void setBalanceCalculateMode(Item balanceCalculateMode) {
			BalanceCalculateMode = balanceCalculateMode;
		}
		public Item getBalanceAwayTime() {
			return BalanceAwayTime;
		}
		public void setBalanceAwayTime(Item balanceAwayTime) {
			BalanceAwayTime = balanceAwayTime;
		}
		public Item getBalanceCloseTime() {
			return BalanceCloseTime;
		}
		public void setBalanceCloseTime(Item balanceCloseTime) {
			BalanceCloseTime = balanceCloseTime;
		}
		public Item getBalanceStrokeCount() {
			return BalanceStrokeCount;
		}
		public void setBalanceStrokeCount(Item balanceStrokeCount) {
			BalanceStrokeCount = balanceStrokeCount;
		}
		public Item getBalanceOperationUpLimit() {
			return BalanceOperationUpLimit;
		}
		public void setBalanceOperationUpLimit(Item balanceOperationUpLimit) {
			BalanceOperationUpLimit = balanceOperationUpLimit;
		}
		public Item getBalanceOperationDownLimit() {
			return BalanceOperationDownLimit;
		}
		public void setBalanceOperationDownLimit(Item balanceOperationDownLimit) {
			BalanceOperationDownLimit = balanceOperationDownLimit;
		}
		public Item getBalanceAwayTimePerBeat() {
			return BalanceAwayTimePerBeat;
		}
		public void setBalanceAwayTimePerBeat(Item balanceAwayTimePerBeat) {
			BalanceAwayTimePerBeat = balanceAwayTimePerBeat;
		}
		public Item getBalanceCloseTimePerBeat() {
			return BalanceCloseTimePerBeat;
		}
		public void setBalanceCloseTimePerBeat(Item balanceCloseTimePerBeat) {
			BalanceCloseTimePerBeat = balanceCloseTimePerBeat;
		}
		public Item getDiscreteAcquisitionInterval() {
			return DiscreteAcquisitionInterval;
		}
		public void setDiscreteAcquisitionInterval(Item discreteAcquisitionInterval) {
			DiscreteAcquisitionInterval = discreteAcquisitionInterval;
		}
		public Item getIaDiagram() {
			return IaDiagram;
		}
		public void setIaDiagram(Item iaDiagram) {
			IaDiagram = iaDiagram;
		}
		public Item getIbDiagram() {
			return IbDiagram;
		}
		public void setIbDiagram(Item ibDiagram) {
			IbDiagram = ibDiagram;
		}
		public Item getIcDiagram() {
			return IcDiagram;
		}
		public void setIcDiagram(Item icDiagram) {
			IcDiagram = icDiagram;
		}
		public Item getCurrentUpLimit() {
			return CurrentUpLimit;
		}
		public void setCurrentUpLimit(Item currentUpLimit) {
			CurrentUpLimit = currentUpLimit;
		}
		public Item getCurrentDownLimit() {
			return CurrentDownLimit;
		}
		public void setCurrentDownLimit(Item currentDownLimit) {
			CurrentDownLimit = currentDownLimit;
		}
		public Item getPowerUpLimit() {
			return PowerUpLimit;
		}
		public void setPowerUpLimit(Item powerUpLimit) {
			PowerUpLimit = powerUpLimit;
		}
		public Item getPowerDownLimit() {
			return PowerDownLimit;
		}
		public void setPowerDownLimit(Item powerDownLimit) {
			PowerDownLimit = powerDownLimit;
		}
		public Item getImmediatelyAcquisition() {
			return ImmediatelyAcquisition;
		}
		public void setImmediatelyAcquisition(Item immediatelyAcquisition) {
			ImmediatelyAcquisition = immediatelyAcquisition;
		}
		public Item getUpStrokeIMax() {
			return UpStrokeIMax;
		}
		public void setUpStrokeIMax(Item upStrokeIMax) {
			UpStrokeIMax = upStrokeIMax;
		}
		public Item getDownStrokeIMax() {
			return DownStrokeIMax;
		}
		public void setDownStrokeIMax(Item downStrokeIMax) {
			DownStrokeIMax = downStrokeIMax;
		}
		public Item getIDegreeBalance() {
			return IDegreeBalance;
		}
		public void setIDegreeBalance(Item iDegreeBalance) {
			IDegreeBalance = iDegreeBalance;
		}
		public Item getUpStrokeWattMax() {
			return UpStrokeWattMax;
		}
		public void setUpStrokeWattMax(Item upStrokeWattMax) {
			UpStrokeWattMax = upStrokeWattMax;
		}
		public Item getDownStrokeWattMax() {
			return DownStrokeWattMax;
		}
		public void setDownStrokeWattMax(Item downStrokeWattMax) {
			DownStrokeWattMax = downStrokeWattMax;
		}
		public Item getWattDegreeBalance() {
			return WattDegreeBalance;
		}
		public void setWattDegreeBalance(Item wattDegreeBalance) {
			WattDegreeBalance = wattDegreeBalance;
		}
		public Item getCurrentAMax() {
			return CurrentAMax;
		}
		public void setCurrentAMax(Item currentAMax) {
			CurrentAMax = currentAMax;
		}
		public Item getCurrentAMin() {
			return CurrentAMin;
		}
		public void setCurrentAMin(Item currentAMin) {
			CurrentAMin = currentAMin;
		}
		public Item getCurrentBMax() {
			return CurrentBMax;
		}
		public void setCurrentBMax(Item currentBMax) {
			CurrentBMax = currentBMax;
		}
		public Item getCurrentBMin() {
			return CurrentBMin;
		}
		public void setCurrentBMin(Item currentBMin) {
			CurrentBMin = currentBMin;
		}
		public Item getCurrentCMax() {
			return CurrentCMax;
		}
		public void setCurrentCMax(Item currentCMax) {
			CurrentCMax = currentCMax;
		}
		public Item getCurrentCMin() {
			return CurrentCMin;
		}
		public void setCurrentCMin(Item currentCMin) {
			CurrentCMin = currentCMin;
		}
		public Item getElectricDiagnosisResult() {
			return ElectricDiagnosisResult;
		}
		public void setElectricDiagnosisResult(Item electricDiagnosisResult) {
			ElectricDiagnosisResult = electricDiagnosisResult;
		}
	}
}
