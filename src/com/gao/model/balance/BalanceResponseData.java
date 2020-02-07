package com.gao.model.balance;

import java.util.List;

public class BalanceResponseData {
	
	private String WellName;

    private String AcquisitionTime;

    private CalculationStatus CalculationStatus;

    private Verification Verification;

    private CurrentTorqueCurve CurrentTorqueCurve;

    private MaxValueMethod MaxValueMethod;

    private AveragePowerMethod AveragePowerMethod;

    private MeanSquareRootMethod MeanSquareRootMethod;

    private MotionCurve MotionCurve;

    private PRTF PRTF;

    public void setWellName(String WellName){
        this.WellName = WellName;
    }
    public String getWellName(){
        return this.WellName;
    }
    public void setAcquisitionTime(String AcquisitionTime){
        this.AcquisitionTime = AcquisitionTime;
    }
    public String getAcquisitionTime(){
        return this.AcquisitionTime;
    }
    public void setCalculationStatus(CalculationStatus CalculationStatus){
        this.CalculationStatus = CalculationStatus;
    }
    public CalculationStatus getCalculationStatus(){
        return this.CalculationStatus;
    }
    public void setVerification(Verification Verification){
        this.Verification = Verification;
    }
    public Verification getVerification(){
        return this.Verification;
    }
    public void setCurrentTorqueCurve(CurrentTorqueCurve CurrentTorqueCurve){
        this.CurrentTorqueCurve = CurrentTorqueCurve;
    }
    public CurrentTorqueCurve getCurrentTorqueCurve(){
        return this.CurrentTorqueCurve;
    }
    public void setMaxValueMethod(MaxValueMethod MaxValueMethod){
        this.MaxValueMethod = MaxValueMethod;
    }
    public MaxValueMethod getMaxValueMethod(){
        return this.MaxValueMethod;
    }
    public void setAveragePowerMethod(AveragePowerMethod AveragePowerMethod){
        this.AveragePowerMethod = AveragePowerMethod;
    }
    public AveragePowerMethod getAveragePowerMethod(){
        return this.AveragePowerMethod;
    }
    public void setMeanSquareRootMethod(MeanSquareRootMethod MeanSquareRootMethod){
        this.MeanSquareRootMethod = MeanSquareRootMethod;
    }
    public MeanSquareRootMethod getMeanSquareRootMethod(){
        return this.MeanSquareRootMethod;
    }
    public void setMotionCurve(MotionCurve MotionCurve){
        this.MotionCurve = MotionCurve;
    }
    public MotionCurve getMotionCurve(){
        return this.MotionCurve;
    }
    public void setPRTF(PRTF PRTF){
        this.PRTF = PRTF;
    }
    public PRTF getPRTF(){
        return this.PRTF;
    }

	public static class CalculationStatus
	{
	    private int ResultStatus;

	    public void setResultStatus(int ResultStatus){
	        this.ResultStatus = ResultStatus;
	    }
	    public int getResultStatus(){
	        return this.ResultStatus;
	    }
	}
	
	public static class Verification
	{
	    private int ErrorCounter;

	    private String ErrorString;

	    private int WarningCounter;

	    private String WarningString;

	    public void setErrorCounter(int ErrorCounter){
	        this.ErrorCounter = ErrorCounter;
	    }
	    public int getErrorCounter(){
	        return this.ErrorCounter;
	    }
	    public void setErrorString(String ErrorString){
	        this.ErrorString = ErrorString;
	    }
	    public String getErrorString(){
	        return this.ErrorString;
	    }
	    public void setWarningCounter(int WarningCounter){
	        this.WarningCounter = WarningCounter;
	    }
	    public int getWarningCounter(){
	        return this.WarningCounter;
	    }
	    public void setWarningString(String WarningString){
	        this.WarningString = WarningString;
	    }
	    public String getWarningString(){
	        return this.WarningString;
	    }
	}
	
	public static class NetAnalysis
	{
	    private float MeanSquareRoot;

	    private float UpStrokeMeanSquareRoot;

	    private float DownStrokeMeanSquareRoot;

	    private float UpStrokeAveragePower;

	    private float DownStrokeAveragePower;

	    private float UpStrokeMaxValue;

	    private float DownStrokeMaxValue;

	    private float MeanSquareRootDegreeOfBalance;

	    private float AveragePowerDegreeOfBalance;

	    private float MaxValueDegreeOfBalance;

	    public void setMeanSquareRoot(float MeanSquareRoot){
	        this.MeanSquareRoot = MeanSquareRoot;
	    }
	    public float getMeanSquareRoot(){
	        return this.MeanSquareRoot;
	    }
	    public void setUpStrokeMeanSquareRoot(float UpStrokeMeanSquareRoot){
	        this.UpStrokeMeanSquareRoot = UpStrokeMeanSquareRoot;
	    }
	    public float getUpStrokeMeanSquareRoot(){
	        return this.UpStrokeMeanSquareRoot;
	    }
	    public void setDownStrokeMeanSquareRoot(float DownStrokeMeanSquareRoot){
	        this.DownStrokeMeanSquareRoot = DownStrokeMeanSquareRoot;
	    }
	    public float getDownStrokeMeanSquareRoot(){
	        return this.DownStrokeMeanSquareRoot;
	    }
	    public void setUpStrokeAveragePower(float UpStrokeAveragePower){
	        this.UpStrokeAveragePower = UpStrokeAveragePower;
	    }
	    public float getUpStrokeAveragePower(){
	        return this.UpStrokeAveragePower;
	    }
	    public void setDownStrokeAveragePower(float DownStrokeAveragePower){
	        this.DownStrokeAveragePower = DownStrokeAveragePower;
	    }
	    public float getDownStrokeAveragePower(){
	        return this.DownStrokeAveragePower;
	    }
	    public void setUpStrokeMaxValue(float UpStrokeMaxValue){
	        this.UpStrokeMaxValue = UpStrokeMaxValue;
	    }
	    public float getUpStrokeMaxValue(){
	        return this.UpStrokeMaxValue;
	    }
	    public void setDownStrokeMaxValue(float DownStrokeMaxValue){
	        this.DownStrokeMaxValue = DownStrokeMaxValue;
	    }
	    public float getDownStrokeMaxValue(){
	        return this.DownStrokeMaxValue;
	    }
	    public void setMeanSquareRootDegreeOfBalance(float MeanSquareRootDegreeOfBalance){
	        this.MeanSquareRootDegreeOfBalance = MeanSquareRootDegreeOfBalance;
	    }
	    public float getMeanSquareRootDegreeOfBalance(){
	        return this.MeanSquareRootDegreeOfBalance;
	    }
	    public void setAveragePowerDegreeOfBalance(float AveragePowerDegreeOfBalance){
	        this.AveragePowerDegreeOfBalance = AveragePowerDegreeOfBalance;
	    }
	    public float getAveragePowerDegreeOfBalance(){
	        return this.AveragePowerDegreeOfBalance;
	    }
	    public void setMaxValueDegreeOfBalance(float MaxValueDegreeOfBalance){
	        this.MaxValueDegreeOfBalance = MaxValueDegreeOfBalance;
	    }
	    public float getMaxValueDegreeOfBalance(){
	        return this.MaxValueDegreeOfBalance;
	    }
	}
	
	public static class CurrentTorqueCurve
	{
	    private List<Float> Load;

	    private List<Float> Balance;

	    private List<Float> Crank;

	    private List<Float> Net;

	    private NetAnalysis NetAnalysis;

	    public void setLoad(List<Float> Load){
	        this.Load = Load;
	    }
	    public List<Float> getLoad(){
	        return this.Load;
	    }
	    public void setBalance(List<Float> Balance){
	        this.Balance = Balance;
	    }
	    public List<Float> getBalance(){
	        return this.Balance;
	    }
	    public void setCrank(List<Float> Crank){
	        this.Crank = Crank;
	    }
	    public List<Float> getCrank(){
	        return this.Crank;
	    }
	    public void setNet(List<Float> Net){
	        this.Net = Net;
	    }
	    public List<Float> getNet(){
	        return this.Net;
	    }
	    public void setNetAnalysis(NetAnalysis NetAnalysis){
	        this.NetAnalysis = NetAnalysis;
	    }
	    public NetAnalysis getNetAnalysis(){
	        return this.NetAnalysis;
	    }
	}
	
	public static class TorqueCurve
	{
	    private List<Float> Balance;

	    private List<Float> Net;

	    private NetAnalysis NetAnalysis;

	    public void setBalance(List<Float> Balance){
	        this.Balance = Balance;
	    }
	    public List<Float> getBalance(){
	        return this.Balance;
	    }
	    public void setNet(List<Float> Net){
	        this.Net = Net;
	    }
	    public List<Float> getNet(){
	        return this.Net;
	    }
	    public void setNetAnalysis(NetAnalysis NetAnalysis){
	        this.NetAnalysis = NetAnalysis;
	    }
	    public NetAnalysis getNetAnalysis(){
	        return this.NetAnalysis;
	    }
	}
	
	public static class EveryBalance
	{
	    private float Position;

	    private float Weight;

	    public void setPosition(float Position){
	        this.Position = Position;
	    }
	    public float getPosition(){
	        return this.Position;
	    }
	    public void setWeight(float Weight){
	        this.Weight = Weight;
	    }
	    public float getWeight(){
	        return this.Weight;
	    }
	}
	
	public static class Balance
	{
	    private int MaxCNT;

	    private List<EveryBalance> EveryBalance;

	    public void setMaxCNT(int MaxCNT){
	        this.MaxCNT = MaxCNT;
	    }
	    public int getMaxCNT(){
	        return this.MaxCNT;
	    }
	    public void setEveryBalance(List<EveryBalance> EveryBalance){
	        this.EveryBalance = EveryBalance;
	    }
	    public List<EveryBalance> getEveryBalance(){
	        return this.EveryBalance;
	    }
	}
	
	public static class MaxValueMethod
	{
	    private float DeltaRadius;

	    private int DeltaBlock;

	    private float DeltaMaxValueDOB;
	    private float PercentageOfDifferenceMSR;
	    private float DeltaPowerDOB;

	    private TorqueCurve TorqueCurve;

	    private Balance Balance;

	    public void setDeltaRadius(float DeltaRadius){
	        this.DeltaRadius = DeltaRadius;
	    }
	    public float getDeltaRadius(){
	        return this.DeltaRadius;
	    }
	    public void setDeltaBlock(int DeltaBlock){
	        this.DeltaBlock = DeltaBlock;
	    }
	    public int getDeltaBlock(){
	        return this.DeltaBlock;
	    }
	    public void setTorqueCurve(TorqueCurve TorqueCurve){
	        this.TorqueCurve = TorqueCurve;
	    }
	    public TorqueCurve getTorqueCurve(){
	        return this.TorqueCurve;
	    }
	    public void setBalance(Balance Balance){
	        this.Balance = Balance;
	    }
	    public Balance getBalance(){
	        return this.Balance;
	    }
		public float getDeltaMaxValueDOB() {
			return DeltaMaxValueDOB;
		}
		public void setDeltaMaxValueDOB(float deltaMaxValueDOB) {
			DeltaMaxValueDOB = deltaMaxValueDOB;
		}
		public float getPercentageOfDifferenceMSR() {
			return PercentageOfDifferenceMSR;
		}
		public void setPercentageOfDifferenceMSR(float percentageOfDifferenceMSR) {
			PercentageOfDifferenceMSR = percentageOfDifferenceMSR;
		}
		public float getDeltaPowerDOB() {
			return DeltaPowerDOB;
		}
		public void setDeltaPowerDOB(float deltaPowerDOB) {
			DeltaPowerDOB = deltaPowerDOB;
		}
	}
	
	public static class AveragePowerMethod
	{
	    private float DeltaRadius;

	    private int DeltaBlock;

	    private float DeltaMaxValueDOB;
	    private float PercentageOfDifferenceMSR;
	    private float DeltaPowerDOB;

	    private TorqueCurve TorqueCurve;

	    private Balance Balance;

	    public void setDeltaRadius(float DeltaRadius){
	        this.DeltaRadius = DeltaRadius;
	    }
	    public float getDeltaRadius(){
	        return this.DeltaRadius;
	    }
	    public void setDeltaBlock(int DeltaBlock){
	        this.DeltaBlock = DeltaBlock;
	    }
	    public int getDeltaBlock(){
	        return this.DeltaBlock;
	    }
	    public void setTorqueCurve(TorqueCurve TorqueCurve){
	        this.TorqueCurve = TorqueCurve;
	    }
	    public TorqueCurve getTorqueCurve(){
	        return this.TorqueCurve;
	    }
	    public void setBalance(Balance Balance){
	        this.Balance = Balance;
	    }
	    public Balance getBalance(){
	        return this.Balance;
	    }
		public float getDeltaMaxValueDOB() {
			return DeltaMaxValueDOB;
		}
		public void setDeltaMaxValueDOB(float deltaMaxValueDOB) {
			DeltaMaxValueDOB = deltaMaxValueDOB;
		}
		public float getPercentageOfDifferenceMSR() {
			return PercentageOfDifferenceMSR;
		}
		public void setPercentageOfDifferenceMSR(float percentageOfDifferenceMSR) {
			PercentageOfDifferenceMSR = percentageOfDifferenceMSR;
		}
		public float getDeltaPowerDOB() {
			return DeltaPowerDOB;
		}
		public void setDeltaPowerDOB(float deltaPowerDOB) {
			DeltaPowerDOB = deltaPowerDOB;
		}
	}
	
	public static class MeanSquareRootMethod
	{
	    private float DeltaRadius;

	    private int DeltaBlock;

	    private float DeltaMaxValueDOB;
	    private float PercentageOfDifferenceMSR;
	    private float DeltaPowerDOB;

	    private TorqueCurve TorqueCurve;

	    private Balance Balance;

	    public void setDeltaRadius(float DeltaRadius){
	        this.DeltaRadius = DeltaRadius;
	    }
	    public float getDeltaRadius(){
	        return this.DeltaRadius;
	    }
	    public void setDeltaBlock(int DeltaBlock){
	        this.DeltaBlock = DeltaBlock;
	    }
	    public int getDeltaBlock(){
	        return this.DeltaBlock;
	    }
	    public void setTorqueCurve(TorqueCurve TorqueCurve){
	        this.TorqueCurve = TorqueCurve;
	    }
	    public TorqueCurve getTorqueCurve(){
	        return this.TorqueCurve;
	    }
	    public void setBalance(Balance Balance){
	        this.Balance = Balance;
	    }
	    public Balance getBalance(){
	        return this.Balance;
	    }
		public float getDeltaMaxValueDOB() {
			return DeltaMaxValueDOB;
		}
		public void setDeltaMaxValueDOB(float deltaMaxValueDOB) {
			DeltaMaxValueDOB = deltaMaxValueDOB;
		}
		public float getPercentageOfDifferenceMSR() {
			return PercentageOfDifferenceMSR;
		}
		public void setPercentageOfDifferenceMSR(float percentageOfDifferenceMSR) {
			PercentageOfDifferenceMSR = percentageOfDifferenceMSR;
		}
		public float getDeltaPowerDOB() {
			return DeltaPowerDOB;
		}
		public void setDeltaPowerDOB(float deltaPowerDOB) {
			DeltaPowerDOB = deltaPowerDOB;
		}
	}
	
	public static class MotionCurve
	{
	    private List<Float> CrankAngle;

	    private List<Float> S;

	    private List<Float> V;

	    private List<Float> A;

	    public void setCrankAngle(List<Float> CrankAngle){
	        this.CrankAngle = CrankAngle;
	    }
	    public List<Float> getCrankAngle(){
	        return this.CrankAngle;
	    }
	    public void setS(List<Float> S){
	        this.S = S;
	    }
	    public List<Float> getS(){
	        return this.S;
	    }
	    public void setV(List<Float> V){
	        this.V = V;
	    }
	    public List<Float> getV(){
	        return this.V;
	    }
	    public void setA(List<Float> A){
	        this.A = A;
	    }
	    public List<Float> getA(){
	        return this.A;
	    }
	}

	public static class PRTF
	{
	    private List<Float> CrankAngle;

	    private List<Float> PR;

	    private List<Float> TF;

	    public void setCrankAngle(List<Float> CrankAngle){
	        this.CrankAngle = CrankAngle;
	    }
	    public List<Float> getCrankAngle(){
	        return this.CrankAngle;
	    }
	    public void setPR(List<Float> PR){
	        this.PR = PR;
	    }
	    public List<Float> getPR(){
	        return this.PR;
	    }
	    public void setTF(List<Float> TF){
	        this.TF = TF;
	    }
	    public List<Float> getTF(){
	        return this.TF;
	    }
	}
}
