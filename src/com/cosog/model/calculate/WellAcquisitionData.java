package com.cosog.model.calculate;

import java.util.List;

public class WellAcquisitionData {

	private String WellName;
	
	private Integer wellId;
	
	private Integer LiftingType;
	
	private Integer ProdDataId;
	
	private String AcqTime;

    private ProductionParameter ProductionParameter;

    private Electric Electric;
    
    private ScrewPump ScrewPump;

    private Diagram Diagram;

    public void setWellName(String WellName){
        this.WellName = WellName;
    }
    public String getWellName(){
        return this.WellName;
    }
    public void setAcqTime(String AcqTime){
        this.AcqTime = AcqTime;
    }
    public String getAcqTime(){
        return this.AcqTime;
    }
    public void setProductionParameter(ProductionParameter ProductionParameter){
        this.ProductionParameter = ProductionParameter;
    }
    public ProductionParameter getProductionParameter(){
        return this.ProductionParameter;
    }
    public void setElectric(Electric Electric){
        this.Electric = Electric;
    }
    public Electric getElectric(){
        return this.Electric;
    }
    public void setDiagram(Diagram Diagram){
        this.Diagram = Diagram;
    }
    public Diagram getDiagram(){
        return this.Diagram;
    }
	
	public static class ProductionParameter
	{
	    private float TubingPressure;

	    private float CasingPressure;

	    private float BackPressure;

	    private float WellHeadFluidTemperature;
	    
	    private float bpszpl;

	    private float bpyxpl;

	    public void setTubingPressure(float TubingPressure){
	        this.TubingPressure = TubingPressure;
	    }
	    public float getTubingPressure(){
	        return this.TubingPressure;
	    }
	    public void setCasingPressure(float CasingPressure){
	        this.CasingPressure = CasingPressure;
	    }
	    public float getCasingPressure(){
	        return this.CasingPressure;
	    }
	    public void setBackPressure(float BackPressure){
	        this.BackPressure = BackPressure;
	    }
	    public float getBackPressure(){
	        return this.BackPressure;
	    }
	    public void setWellHeadFluidTemperature(float WellHeadFluidTemperature){
	        this.WellHeadFluidTemperature = WellHeadFluidTemperature;
	    }
	    public float getWellHeadFluidTemperature(){
	        return this.WellHeadFluidTemperature;
	    }
		public float getBpszpl() {
			return bpszpl;
		}
		public void setBpszpl(float bpszpl) {
			this.bpszpl = bpszpl;
		}
		public float getBpyxpl() {
			return bpyxpl;
		}
		public void setBpyxpl(float bpyxpl) {
			this.bpyxpl = bpyxpl;
		}
	}
	
	public static class Electric
	{
	    private float CurrentA;

	    private float CurrentB;

	    private float CurrentC;

	    private float VoltageA;

	    private float VoltageB;

	    private float VoltageC;

	    private float ActivePowerConsumption;

	    private float ReactivePowerConsumption;

	    private float ActivePower;

	    private float ReactivePower;

	    private float ReversePower;

	    private float PowerFactor;

	    public void setCurrentA(float CurrentA){
	        this.CurrentA = CurrentA;
	    }
	    public float getCurrentA(){
	        return this.CurrentA;
	    }
	    public void setCurrentB(float CurrentB){
	        this.CurrentB = CurrentB;
	    }
	    public float getCurrentB(){
	        return this.CurrentB;
	    }
	    public void setCurrentC(float CurrentC){
	        this.CurrentC = CurrentC;
	    }
	    public float getCurrentC(){
	        return this.CurrentC;
	    }
	    public void setVoltageA(float VoltageA){
	        this.VoltageA = VoltageA;
	    }
	    public float getVoltageA(){
	        return this.VoltageA;
	    }
	    public void setVoltageB(float VoltageB){
	        this.VoltageB = VoltageB;
	    }
	    public float getVoltageB(){
	        return this.VoltageB;
	    }
	    public void setVoltageC(float VoltageC){
	        this.VoltageC = VoltageC;
	    }
	    public float getVoltageC(){
	        return this.VoltageC;
	    }
	    public void setActivePowerConsumption(float ActivePowerConsumption){
	        this.ActivePowerConsumption = ActivePowerConsumption;
	    }
	    public float getActivePowerConsumption(){
	        return this.ActivePowerConsumption;
	    }
	    public void setReactivePowerConsumption(float ReactivePowerConsumption){
	        this.ReactivePowerConsumption = ReactivePowerConsumption;
	    }
	    public float getReactivePowerConsumption(){
	        return this.ReactivePowerConsumption;
	    }
	    public void setActivePower(float ActivePower){
	        this.ActivePower = ActivePower;
	    }
	    public float getActivePower(){
	        return this.ActivePower;
	    }
	    public void setReactivePower(float ReactivePower){
	        this.ReactivePower = ReactivePower;
	    }
	    public float getReactivePower(){
	        return this.ReactivePower;
	    }
	    public void setReversePower(float ReversePower){
	        this.ReversePower = ReversePower;
	    }
	    public float getReversePower(){
	        return this.ReversePower;
	    }
	    public void setPowerFactor(float PowerFactor){
	        this.PowerFactor = PowerFactor;
	    }
	    public float getPowerFactor(){
	        return this.PowerFactor;
	    }
	}
	
	public static class ScrewPump
	{
	    private float RPM;

	    private float Torque;

		public float getRPM() {
			return RPM;
		}

		public void setRPM(float rPM) {
			RPM = rPM;
		}

		public float getTorque() {
			return Torque;
		}

		public void setTorque(float torque) {
			Torque = torque;
		}
	}
	
	public static class Diagram
	{
	    private String AcqTime;
	    
	    private Integer AcquisitionCycle;

	    private float SPM;

	    private float Stroke;
	    
	    private float HaveBalanceData=0;
	    
	    private float UpStrokeIMax;
	    
	    private float DownStrokeIMax;
	    
	    private float IDegreeBalance;
	    
	    private float UpStrokeWattMax;
	    
	    private float DownStrokeWattMax;
	    
	    private float WattDegreeBalance;

	    private List<Float> F;

	    private List<Float> S;

	    private List<Float> A;

	    private List<Float> P;
	    
	    private List<Float> Ia;
	    
	    private List<Float> Ib;
	    
	    private List<Float> Ic;

	    public void setAcqTime(String AcqTime){
	        this.AcqTime = AcqTime;
	    }
	    public String getAcqTime(){
	        return this.AcqTime;
	    }
	    public void setSPM(float SPM){
	        this.SPM = SPM;
	    }
	    public float getSPM(){
	        return this.SPM;
	    }
	    public void setStroke(float Stroke){
	        this.Stroke = Stroke;
	    }
	    public float getStroke(){
	        return this.Stroke;
	    }
	    public void setF(List<Float> F){
	        this.F = F;
	    }
	    public List<Float> getF(){
	        return this.F;
	    }
	    public void setS(List<Float> S){
	        this.S = S;
	    }
	    public List<Float> getS(){
	        return this.S;
	    }
	    public void setA(List<Float> A){
	        this.A = A;
	    }
	    public List<Float> getA(){
	        return this.A;
	    }
	    public void setP(List<Float> P){
	        this.P = P;
	    }
	    public List<Float> getP(){
	        return this.P;
	    }
		public Integer getAcquisitionCycle() {
			return AcquisitionCycle;
		}
		public void setAcquisitionCycle(Integer acquisitionCycle) {
			AcquisitionCycle = acquisitionCycle;
		}
		public float getUpStrokeIMax() {
			return UpStrokeIMax;
		}
		public void setUpStrokeIMax(float upStrokeIMax) {
			UpStrokeIMax = upStrokeIMax;
		}
		public float getDownStrokeIMax() {
			return DownStrokeIMax;
		}
		public void setDownStrokeIMax(float downStrokeIMax) {
			DownStrokeIMax = downStrokeIMax;
		}
		public float getIDegreeBalance() {
			return IDegreeBalance;
		}
		public void setIDegreeBalance(float iDegreeBalance) {
			IDegreeBalance = iDegreeBalance;
		}
		public float getUpStrokeWattMax() {
			return UpStrokeWattMax;
		}
		public void setUpStrokeWattMax(float upStrokeWattMax) {
			UpStrokeWattMax = upStrokeWattMax;
		}
		public float getDownStrokeWattMax() {
			return DownStrokeWattMax;
		}
		public void setDownStrokeWattMax(float downStrokeWattMax) {
			DownStrokeWattMax = downStrokeWattMax;
		}
		public float getWattDegreeBalance() {
			return WattDegreeBalance;
		}
		public void setWattDegreeBalance(float wattDegreeBalance) {
			WattDegreeBalance = wattDegreeBalance;
		}
		public List<Float> getIa() {
			return Ia;
		}
		public void setIa(List<Float> ia) {
			Ia = ia;
		}
		public List<Float> getIb() {
			return Ib;
		}
		public void setIb(List<Float> ib) {
			Ib = ib;
		}
		public List<Float> getIc() {
			return Ic;
		}
		public void setIc(List<Float> ic) {
			Ic = ic;
		}
		public float getHaveBalanceData() {
			return HaveBalanceData;
		}
		public void setHaveBalanceData(float haveBalanceData) {
			HaveBalanceData = haveBalanceData;
		}
	}

	public ScrewPump getScrewPump() {
		return ScrewPump;
	}
	public void setScrewPump(ScrewPump screwPump) {
		ScrewPump = screwPump;
	}
	public Integer getProdDataId() {
		return ProdDataId;
	}
	public void setProdDataId(Integer prodDataId) {
		ProdDataId = prodDataId;
	}
	public Integer getWellId() {
		return wellId;
	}
	public void setWellId(Integer wellId) {
		this.wellId = wellId;
	}
	
	public Integer getLiftingType() {
		return LiftingType;
	}
	public void setLiftingType(Integer liftingType) {
		LiftingType = liftingType;
	}

}
