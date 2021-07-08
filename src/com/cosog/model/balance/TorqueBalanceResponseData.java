package com.cosog.model.balance;

import java.util.List;

public class TorqueBalanceResponseData {
	
	private String WellName;

    private float Stroke;

    private float SPM;

    private CalculationStatus CalculationStatus;

    private DataVerificationCounter DataVerificationCounter;

    private NetTorqueMeanSquareRootMethod NetTorqueMeanSquareRootMethod;

    private NetTorqueMaxValueMethod NetTorqueMaxValueMethod;

    private MotionCurve MotionCurve;

    private SystemEfficiency SystemEfficiency;

    public void setWellName(String WellName){
        this.WellName = WellName;
    }
    public String getWellName(){
        return this.WellName;
    }
    public void setStroke(float Stroke){
        this.Stroke = Stroke;
    }
    public float getStroke(){
        return this.Stroke;
    }
    public void setSPM(float SPM){
        this.SPM = SPM;
    }
    public float getSPM(){
        return this.SPM;
    }
    public void setCalculationStatus(CalculationStatus CalculationStatus){
        this.CalculationStatus = CalculationStatus;
    }
    public CalculationStatus getCalculationStatus(){
        return this.CalculationStatus;
    }
    public void setDataVerificationCounter(DataVerificationCounter DataVerificationCounter){
        this.DataVerificationCounter = DataVerificationCounter;
    }
    public DataVerificationCounter getDataVerificationCounter(){
        return this.DataVerificationCounter;
    }
    public void setNetTorqueMeanSquareRootMethod(NetTorqueMeanSquareRootMethod NetTorqueMeanSquareRootMethod){
        this.NetTorqueMeanSquareRootMethod = NetTorqueMeanSquareRootMethod;
    }
    public NetTorqueMeanSquareRootMethod getNetTorqueMeanSquareRootMethod(){
        return this.NetTorqueMeanSquareRootMethod;
    }
    public void setNetTorqueMaxValueMethod(NetTorqueMaxValueMethod NetTorqueMaxValueMethod){
        this.NetTorqueMaxValueMethod = NetTorqueMaxValueMethod;
    }
    public NetTorqueMaxValueMethod getNetTorqueMaxValueMethod(){
        return this.NetTorqueMaxValueMethod;
    }
    public void setMotionCurve(MotionCurve MotionCurve){
        this.MotionCurve = MotionCurve;
    }
    public MotionCurve getMotionCurve(){
        return this.MotionCurve;
    }
    public void setSystemEfficiency(SystemEfficiency SystemEfficiency){
        this.SystemEfficiency = SystemEfficiency;
    }
    public SystemEfficiency getSystemEfficiency(){
        return this.SystemEfficiency;
    }

	public static class CalculationStatus
	{
	    private int ResultStatus;

	    private int ResultCode;

	    public void setResultStatus(int ResultStatus){
	        this.ResultStatus = ResultStatus;
	    }
	    public int getResultStatus(){
	        return this.ResultStatus;
	    }
	    public void setResultCode(int ResultCode){
	        this.ResultCode = ResultCode;
	    }
	    public int getResultCode(){
	        return this.ResultCode;
	    }
	}
	
	public static class DataVerificationCounter
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
	    private int CNT;

	    private float MaxValue;

	    private int IndexMaxValue;

	    private float MinValue;

	    private int IndexMinValue;

	    private float AverageValue;

	    private float MeanSquareRoot;

	    private float UpStrokeMaxValue;

	    private int IndexUpStrokeMaxValue;

	    private float DownStrokeMaxValue;

	    private int IndexDownStrokeMaxValue;

	    private float UpStrokeAverageValue;

	    private float DownStrokeAverageValue;

	    public void setCNT(int CNT){
	        this.CNT = CNT;
	    }
	    public int getCNT(){
	        return this.CNT;
	    }
	    public void setMaxValue(float MaxValue){
	        this.MaxValue = MaxValue;
	    }
	    public float getMaxValue(){
	        return this.MaxValue;
	    }
	    public void setIndexMaxValue(int IndexMaxValue){
	        this.IndexMaxValue = IndexMaxValue;
	    }
	    public int getIndexMaxValue(){
	        return this.IndexMaxValue;
	    }
	    public void setMinValue(float MinValue){
	        this.MinValue = MinValue;
	    }
	    public float getMinValue(){
	        return this.MinValue;
	    }
	    public void setIndexMinValue(int IndexMinValue){
	        this.IndexMinValue = IndexMinValue;
	    }
	    public int getIndexMinValue(){
	        return this.IndexMinValue;
	    }
	    public void setAverageValue(float AverageValue){
	        this.AverageValue = AverageValue;
	    }
	    public float getAverageValue(){
	        return this.AverageValue;
	    }
	    public void setMeanSquareRoot(float MeanSquareRoot){
	        this.MeanSquareRoot = MeanSquareRoot;
	    }
	    public float getMeanSquareRoot(){
	        return this.MeanSquareRoot;
	    }
	    public void setUpStrokeMaxValue(float UpStrokeMaxValue){
	        this.UpStrokeMaxValue = UpStrokeMaxValue;
	    }
	    public float getUpStrokeMaxValue(){
	        return this.UpStrokeMaxValue;
	    }
	    public void setIndexUpStrokeMaxValue(int IndexUpStrokeMaxValue){
	        this.IndexUpStrokeMaxValue = IndexUpStrokeMaxValue;
	    }
	    public int getIndexUpStrokeMaxValue(){
	        return this.IndexUpStrokeMaxValue;
	    }
	    public void setDownStrokeMaxValue(float DownStrokeMaxValue){
	        this.DownStrokeMaxValue = DownStrokeMaxValue;
	    }
	    public float getDownStrokeMaxValue(){
	        return this.DownStrokeMaxValue;
	    }
	    public void setIndexDownStrokeMaxValue(int IndexDownStrokeMaxValue){
	        this.IndexDownStrokeMaxValue = IndexDownStrokeMaxValue;
	    }
	    public int getIndexDownStrokeMaxValue(){
	        return this.IndexDownStrokeMaxValue;
	    }
	    public void setUpStrokeAverageValue(float UpStrokeAverageValue){
	        this.UpStrokeAverageValue = UpStrokeAverageValue;
	    }
	    public float getUpStrokeAverageValue(){
	        return this.UpStrokeAverageValue;
	    }
	    public void setDownStrokeAverageValue(float DownStrokeAverageValue){
	        this.DownStrokeAverageValue = DownStrokeAverageValue;
	    }
	    public float getDownStrokeAverageValue(){
	        return this.DownStrokeAverageValue;
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
	
	public static class EveryBalance
	{
	    private String Name;

	    private float Position;

	    private float Weight;

	    public void setName(String Name){
	        this.Name = Name;
	    }
	    public String getName(){
	        return this.Name;
	    }
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
	
	public static class CurrentBalance
	{
	    private int MaxCNT;

	    private int ValidCNT;

	    private float BalanceTorqueAll;

	    private float CrankTorqueAll;

	    private float BalanceWeightAll;

	    private boolean MoveStatus;

	    private List<EveryBalance> EveryBalance;

	    public void setMaxCNT(int MaxCNT){
	        this.MaxCNT = MaxCNT;
	    }
	    public int getMaxCNT(){
	        return this.MaxCNT;
	    }
	    public void setValidCNT(int ValidCNT){
	        this.ValidCNT = ValidCNT;
	    }
	    public int getValidCNT(){
	        return this.ValidCNT;
	    }
	    public void setBalanceTorqueAll(float BalanceTorqueAll){
	        this.BalanceTorqueAll = BalanceTorqueAll;
	    }
	    public float getBalanceTorqueAll(){
	        return this.BalanceTorqueAll;
	    }
	    public void setCrankTorqueAll(float CrankTorqueAll){
	        this.CrankTorqueAll = CrankTorqueAll;
	    }
	    public float getCrankTorqueAll(){
	        return this.CrankTorqueAll;
	    }
	    public void setBalanceWeightAll(float BalanceWeightAll){
	        this.BalanceWeightAll = BalanceWeightAll;
	    }
	    public float getBalanceWeightAll(){
	        return this.BalanceWeightAll;
	    }
	    public void setMoveStatus(boolean MoveStatus){
	        this.MoveStatus = MoveStatus;
	    }
	    public boolean getMoveStatus(){
	        return this.MoveStatus;
	    }
	    public void setEveryBalance(List<EveryBalance> EveryBalance){
	        this.EveryBalance = EveryBalance;
	    }
	    public List<EveryBalance> getEveryBalance(){
	        return this.EveryBalance;
	    }
	}
	
	public static class OptimizitionTorqueCurve
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
	
	public static class OptimizationBalance
	{
	    private int MaxCNT;

	    private int ValidCNT;

	    private float BalanceTorqueAll;

	    private float CrankTorqueAll;

	    private float BalanceWeightAll;

	    private boolean MoveStatus;

	    private List<EveryBalance> EveryBalance;

	    public void setMaxCNT(int MaxCNT){
	        this.MaxCNT = MaxCNT;
	    }
	    public int getMaxCNT(){
	        return this.MaxCNT;
	    }
	    public void setValidCNT(int ValidCNT){
	        this.ValidCNT = ValidCNT;
	    }
	    public int getValidCNT(){
	        return this.ValidCNT;
	    }
	    public void setBalanceTorqueAll(float BalanceTorqueAll){
	        this.BalanceTorqueAll = BalanceTorqueAll;
	    }
	    public float getBalanceTorqueAll(){
	        return this.BalanceTorqueAll;
	    }
	    public void setCrankTorqueAll(float CrankTorqueAll){
	        this.CrankTorqueAll = CrankTorqueAll;
	    }
	    public float getCrankTorqueAll(){
	        return this.CrankTorqueAll;
	    }
	    public void setBalanceWeightAll(float BalanceWeightAll){
	        this.BalanceWeightAll = BalanceWeightAll;
	    }
	    public float getBalanceWeightAll(){
	        return this.BalanceWeightAll;
	    }
	    public void setMoveStatus(boolean MoveStatus){
	        this.MoveStatus = MoveStatus;
	    }
	    public boolean getMoveStatus(){
	        return this.MoveStatus;
	    }
	    public void setEveryBalance(List<EveryBalance> EveryBalance){
	        this.EveryBalance = EveryBalance;
	    }
	    public List<EveryBalance> getEveryBalance(){
	        return this.EveryBalance;
	    }
	}
	
	public static class NetTorqueMeanSquareRootMethod
	{
	    private float DeltaRadius;

	    private int DeltaBlock;

	    private float DeltaDegreeOfBalance;

	    private float DeltaBalanceTorque;

	    private float DeltaTorqueMeanSquareRoot;
	    
	    private float SamePositionRadius;

	    private float CurrentDegreeOfBalance;

	    private CurrentTorqueCurve CurrentTorqueCurve;

	    private CurrentBalance CurrentBalance;

	    private float OptimizitionDegreeOfBalance;

	    private OptimizitionTorqueCurve OptimizitionTorqueCurve;

	    private OptimizationBalance OptimizationBalance;

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
	    public void setDeltaDegreeOfBalance(float DeltaDegreeOfBalance){
	        this.DeltaDegreeOfBalance = DeltaDegreeOfBalance;
	    }
	    public float getDeltaDegreeOfBalance(){
	        return this.DeltaDegreeOfBalance;
	    }
	    public void setDeltaBalanceTorque(float DeltaBalanceTorque){
	        this.DeltaBalanceTorque = DeltaBalanceTorque;
	    }
	    public float getDeltaBalanceTorque(){
	        return this.DeltaBalanceTorque;
	    }
	    public void setDeltaTorqueMeanSquareRoot(float DeltaTorqueMeanSquareRoot){
	        this.DeltaTorqueMeanSquareRoot = DeltaTorqueMeanSquareRoot;
	    }
	    public float getDeltaTorqueMeanSquareRoot(){
	        return this.DeltaTorqueMeanSquareRoot;
	    }
	    public void setCurrentDegreeOfBalance(float CurrentDegreeOfBalance){
	        this.CurrentDegreeOfBalance = CurrentDegreeOfBalance;
	    }
	    public float getCurrentDegreeOfBalance(){
	        return this.CurrentDegreeOfBalance;
	    }
	    public void setCurrentTorqueCurve(CurrentTorqueCurve CurrentTorqueCurve){
	        this.CurrentTorqueCurve = CurrentTorqueCurve;
	    }
	    public CurrentTorqueCurve getCurrentTorqueCurve(){
	        return this.CurrentTorqueCurve;
	    }
	    public void setCurrentBalance(CurrentBalance CurrentBalance){
	        this.CurrentBalance = CurrentBalance;
	    }
	    public CurrentBalance getCurrentBalance(){
	        return this.CurrentBalance;
	    }
	    public void setOptimizitionDegreeOfBalance(float OptimizitionDegreeOfBalance){
	        this.OptimizitionDegreeOfBalance = OptimizitionDegreeOfBalance;
	    }
	    public float getOptimizitionDegreeOfBalance(){
	        return this.OptimizitionDegreeOfBalance;
	    }
	    public void setOptimizitionTorqueCurve(OptimizitionTorqueCurve OptimizitionTorqueCurve){
	        this.OptimizitionTorqueCurve = OptimizitionTorqueCurve;
	    }
	    public OptimizitionTorqueCurve getOptimizitionTorqueCurve(){
	        return this.OptimizitionTorqueCurve;
	    }
	    public void setOptimizationBalance(OptimizationBalance OptimizationBalance){
	        this.OptimizationBalance = OptimizationBalance;
	    }
	    public OptimizationBalance getOptimizationBalance(){
	        return this.OptimizationBalance;
	    }
		public float getSamePositionRadius() {
			return SamePositionRadius;
		}
		public void setSamePositionRadius(float samePositionRadius) {
			SamePositionRadius = samePositionRadius;
		}
	}
	
	public static class NetTorqueMaxValueMethod
	{
	    private float DeltaRadius;

	    private int DeltaBlock;

	    private float DeltaDegreeOfBalance;

	    private float DeltaBalanceTorque;

	    private float DeltaTorqueMeanSquareRoot;
	    
	    private float SamePositionRadius;

	    private float CurrentDegreeOfBalance;

	    private CurrentTorqueCurve CurrentTorqueCurve;

	    private CurrentBalance CurrentBalance;

	    private float OptimizitionDegreeOfBalance;

	    private OptimizitionTorqueCurve OptimizitionTorqueCurve;

	    private OptimizationBalance OptimizationBalance;

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
	    public void setDeltaDegreeOfBalance(float DeltaDegreeOfBalance){
	        this.DeltaDegreeOfBalance = DeltaDegreeOfBalance;
	    }
	    public float getDeltaDegreeOfBalance(){
	        return this.DeltaDegreeOfBalance;
	    }
	    public void setDeltaBalanceTorque(float DeltaBalanceTorque){
	        this.DeltaBalanceTorque = DeltaBalanceTorque;
	    }
	    public float getDeltaBalanceTorque(){
	        return this.DeltaBalanceTorque;
	    }
	    public void setDeltaTorqueMeanSquareRoot(float DeltaTorqueMeanSquareRoot){
	        this.DeltaTorqueMeanSquareRoot = DeltaTorqueMeanSquareRoot;
	    }
	    public float getDeltaTorqueMeanSquareRoot(){
	        return this.DeltaTorqueMeanSquareRoot;
	    }
	    public void setCurrentDegreeOfBalance(float CurrentDegreeOfBalance){
	        this.CurrentDegreeOfBalance = CurrentDegreeOfBalance;
	    }
	    public float getCurrentDegreeOfBalance(){
	        return this.CurrentDegreeOfBalance;
	    }
	    public void setCurrentTorqueCurve(CurrentTorqueCurve CurrentTorqueCurve){
	        this.CurrentTorqueCurve = CurrentTorqueCurve;
	    }
	    public CurrentTorqueCurve getCurrentTorqueCurve(){
	        return this.CurrentTorqueCurve;
	    }
	    public void setCurrentBalance(CurrentBalance CurrentBalance){
	        this.CurrentBalance = CurrentBalance;
	    }
	    public CurrentBalance getCurrentBalance(){
	        return this.CurrentBalance;
	    }
	    public void setOptimizitionDegreeOfBalance(float OptimizitionDegreeOfBalance){
	        this.OptimizitionDegreeOfBalance = OptimizitionDegreeOfBalance;
	    }
	    public float getOptimizitionDegreeOfBalance(){
	        return this.OptimizitionDegreeOfBalance;
	    }
	    public void setOptimizitionTorqueCurve(OptimizitionTorqueCurve OptimizitionTorqueCurve){
	        this.OptimizitionTorqueCurve = OptimizitionTorqueCurve;
	    }
	    public OptimizitionTorqueCurve getOptimizitionTorqueCurve(){
	        return this.OptimizitionTorqueCurve;
	    }
	    public void setOptimizationBalance(OptimizationBalance OptimizationBalance){
	        this.OptimizationBalance = OptimizationBalance;
	    }
	    public OptimizationBalance getOptimizationBalance(){
	        return this.OptimizationBalance;
	    }
		public float getSamePositionRadius() {
			return SamePositionRadius;
		}
		public void setSamePositionRadius(float samePositionRadius) {
			SamePositionRadius = samePositionRadius;
		}
	}
	
	public static class MotionCurve
	{
	    private List<Float> CrankAngle;

	    private List<Float> S;

	    private List<Float> V;

	    private List<Float> A;

	    private List<Float> PR;

	    private List<Float> TF;

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
	
	public static class SystemEfficiency
	{
	    private float FourBarLinkageEfficiency;

	    public void setFourBarLinkageEfficiency(float FourBarLinkageEfficiency){
	        this.FourBarLinkageEfficiency = FourBarLinkageEfficiency;
	    }
	    public float getFourBarLinkageEfficiency(){
	        return this.FourBarLinkageEfficiency;
	    }
	}
}
