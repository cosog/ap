package com.cosog.model.calculate;

public class ElectricCalculateResponseData {
	
	 	private String WellName;

	    private int ResultStatus;

	    private String AcqTime;

	    private int RunStatus;

	    private int ResultCode;

	    private float Timer;
	    
	    private String ETResultString="";
	    
	    private String CurrentString="";
	    
	    private String VoltageString="";

	    private AlarmItems AlarmItems;

	    private ElectricLimit ElectricLimit;

	    public void setWellName(String WellName){
	        this.WellName = WellName;
	    }
	    public String getWellName(){
	        return this.WellName;
	    }
	    public void setResultStatus(int ResultStatus){
	        this.ResultStatus = ResultStatus;
	    }
	    public int getResultStatus(){
	        return this.ResultStatus;
	    }
	    public void setAcqTime(String AcqTime){
	        this.AcqTime = AcqTime;
	    }
	    public String getAcqTime(){
	        return this.AcqTime;
	    }
	    public void setRunStatus(int RunStatus){
	        this.RunStatus = RunStatus;
	    }
	    public int getRunStatus(){
	        return this.RunStatus;
	    }
	    public void setResultCode(int ResultCode){
	        this.ResultCode = ResultCode;
	    }
	    public int getResultCode(){
	        return this.ResultCode;
	    }
	    public void setTimer(float Timer){
	        this.Timer = Timer;
	    }
	    public float getTimer(){
	        return this.Timer;
	    }
	    public void setAlarmItems(AlarmItems AlarmItems){
	        this.AlarmItems = AlarmItems;
	    }
	    public AlarmItems getAlarmItems(){
	        return this.AlarmItems;
	    }
	    public void setElectricLimit(ElectricLimit ElectricLimit){
	        this.ElectricLimit = ElectricLimit;
	    }
	    public ElectricLimit getElectricLimit(){
	        return this.ElectricLimit;
	    }

	public static class AlarmStatus
	{
	    private int MaxValueStatus;

	    private int MinValueStatus;

	    private int ZeroLevelStatus;

	    private int BalacneStatus;

	    public void setMaxValueStatus(int MaxValueStatus){
	        this.MaxValueStatus = MaxValueStatus;
	    }
	    public int getMaxValueStatus(){
	        return this.MaxValueStatus;
	    }
	    public void setMinValueStatus(int MinValueStatus){
	        this.MinValueStatus = MinValueStatus;
	    }
	    public int getMinValueStatus(){
	        return this.MinValueStatus;
	    }
	    public void setZeroLevelStatus(int ZeroLevelStatus){
	        this.ZeroLevelStatus = ZeroLevelStatus;
	    }
	    public int getZeroLevelStatus(){
	        return this.ZeroLevelStatus;
	    }
	    public void setBalacneStatus(int BalacneStatus){
	        this.BalacneStatus = BalacneStatus;
	    }
	    public int getBalacneStatus(){
	        return this.BalacneStatus;
	    }
	}
	
	public static class AlarmItems
	{
	    private AlarmStatus CurrentA;

	    private AlarmStatus CurrentB;

	    private AlarmStatus CurrentC;

	    private AlarmStatus VoltageA;

	    private AlarmStatus VoltageB;

	    private AlarmStatus VoltageC;

	    public void setCurrentA(AlarmStatus CurrentA){
	        this.CurrentA = CurrentA;
	    }
	    public AlarmStatus getCurrentA(){
	        return this.CurrentA;
	    }
	    public void setCurrentB(AlarmStatus CurrentB){
	        this.CurrentB = CurrentB;
	    }
	    public AlarmStatus getCurrentB(){
	        return this.CurrentB;
	    }
	    public void setCurrentC(AlarmStatus CurrentC){
	        this.CurrentC = CurrentC;
	    }
	    public AlarmStatus getCurrentC(){
	        return this.CurrentC;
	    }
	    public void setVoltageA(AlarmStatus VoltageA){
	        this.VoltageA = VoltageA;
	    }
	    public AlarmStatus getVoltageA(){
	        return this.VoltageA;
	    }
	    public void setVoltageB(AlarmStatus VoltageB){
	        this.VoltageB = VoltageB;
	    }
	    public AlarmStatus getVoltageB(){
	        return this.VoltageB;
	    }
	    public void setVoltageC(AlarmStatus VoltageC){
	        this.VoltageC = VoltageC;
	    }
	    public AlarmStatus getVoltageC(){
	        return this.VoltageC;
	    }
	}
	
	public static class Limit
	{
	    private float Max;

	    private float Min;
	    
	    private float Zero;

	    public void setMax(float Max){
	        this.Max = Max;
	    }
	    public float getMax(){
	        return this.Max;
	    }
	    public void setMin(float Min){
	        this.Min = Min;
	    }
	    public float getMin(){
	        return this.Min;
	    }
		public float getZero() {
			return Zero;
		}
		public void setZero(float zero) {
			Zero = zero;
		}
	}
	
	public class ElectricLimit
	{
	    private Limit CurrentA;

	    private Limit CurrentB;

	    private Limit CurrentC;

	    private Limit VoltageA;

	    private Limit VoltageB;

	    private Limit VoltageC;

	    public void setCurrentA(Limit CurrentA){
	        this.CurrentA = CurrentA;
	    }
	    public Limit getCurrentA(){
	        return this.CurrentA;
	    }
	    public void setCurrentB(Limit CurrentB){
	        this.CurrentB = CurrentB;
	    }
	    public Limit getCurrentB(){
	        return this.CurrentB;
	    }
	    public void setCurrentC(Limit CurrentC){
	        this.CurrentC = CurrentC;
	    }
	    public Limit getCurrentC(){
	        return this.CurrentC;
	    }
	    public void setVoltageA(Limit VoltageA){
	        this.VoltageA = VoltageA;
	    }
	    public Limit getVoltageA(){
	        return this.VoltageA;
	    }
	    public void setVoltageB(Limit VoltageB){
	        this.VoltageB = VoltageB;
	    }
	    public Limit getVoltageB(){
	        return this.VoltageB;
	    }
	    public void setVoltageC(Limit VoltageC){
	        this.VoltageC = VoltageC;
	    }
	    public Limit getVoltageC(){
	        return this.VoltageC;
	    }
	}

	public String getETResultString() {
		return ETResultString;
	}
	public void setETResultString(String eTResultString) {
		ETResultString = eTResultString;
	}
	public String getCurrentString() {
		return CurrentString;
	}
	public void setCurrentString(String currentString) {
		CurrentString = currentString;
	}
	public String getVoltageString() {
		return VoltageString;
	}
	public void setVoltageString(String voltageString) {
		VoltageString = voltageString;
	}
}
