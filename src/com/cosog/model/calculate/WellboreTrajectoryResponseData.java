package com.cosog.model.calculate;

import java.util.List;

public class WellboreTrajectoryResponseData {
	
	private String WellName;

    private CalculationStatus CalculationStatus;

    private Verification Verification;

    private WellboreTrajectory WellboreTrajectory;

    public void setWellName(String WellName){
        this.WellName = WellName;
    }
    public String getWellName(){
        return this.WellName;
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
    public void setWellboreTrajectory(WellboreTrajectory WellboreTrajectory){
        this.WellboreTrajectory = WellboreTrajectory;
    }
    public WellboreTrajectory getWellboreTrajectory(){
        return this.WellboreTrajectory;
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
	
	public static class WellboreTrajectory
	{
	    private int CNT;

	    private List<Float> MeasuringDepth;

	    private List<Float> VerticalDepth;

	    private List<Float> DeviationAngle;

	    private List<Float> AzimuthAngle;

	    private List<Float> X;

	    private List<Float> Y;

	    private List<Float> Z;

	    private String ArrayStr;

		public int getCNT() {
			return CNT;
		}

		public void setCNT(int cNT) {
			CNT = cNT;
		}

		public List<Float> getMeasuringDepth() {
			return MeasuringDepth;
		}

		public void setMeasuringDepth(List<Float> measuringDepth) {
			MeasuringDepth = measuringDepth;
		}

		public List<Float> getVerticalDepth() {
			return VerticalDepth;
		}

		public void setVerticalDepth(List<Float> verticalDepth) {
			VerticalDepth = verticalDepth;
		}

		public List<Float> getDeviationAngle() {
			return DeviationAngle;
		}

		public void setDeviationAngle(List<Float> deviationAngle) {
			DeviationAngle = deviationAngle;
		}

		public List<Float> getAzimuthAngle() {
			return AzimuthAngle;
		}

		public void setAzimuthAngle(List<Float> azimuthAngle) {
			AzimuthAngle = azimuthAngle;
		}

		public List<Float> getX() {
			return X;
		}

		public void setX(List<Float> x) {
			X = x;
		}

		public List<Float> getY() {
			return Y;
		}

		public void setY(List<Float> y) {
			Y = y;
		}

		public List<Float> getZ() {
			return Z;
		}

		public void setZ(List<Float> z) {
			Z = z;
		}

		public String getArrayStr() {
			return ArrayStr;
		}

		public void setArrayStr(String arrayStr) {
			ArrayStr = arrayStr;
		}
	}

}
