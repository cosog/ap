package com.cosog.model;

public class DataWriteBackConfig {

	private boolean Enable;

    private String IP;

    private int Port;

    private String InstanceName;

    private int Version;

    private String User;

    private String Password;

    private String WriteType;

    private DiagramResultConfig DiagramResult;
	
	public static class ColumnInfo
	{
	    private String Column;

	    private String Type;
	    
	    private float Ratio;

	    private boolean Enable;

	    public void setColumn(String Column){
	        this.Column = Column;
	    }
	    public String getColumn(){
	        return this.Column;
	    }
	    public void setType(String Type){
	        this.Type = Type;
	    }
	    public String getType(){
	        return this.Type;
	    }
	    public void setEnable(boolean Enable){
	        this.Enable = Enable;
	    }
	    public boolean getEnable(){
	        return this.Enable;
	    }
		public float getRatio() {
			return Ratio;
		}
		public void setRatio(float ratio) {
			Ratio = ratio;
		}
	}
	
	public static class DiagramResultColumn
	{
	    private ColumnInfo WellName;

	    private ColumnInfo AcqTime;

	    private ColumnInfo FMax;

	    private ColumnInfo FMin;
	    
	    private ColumnInfo UpperLoadLine;
	    
	    private ColumnInfo LowerLoadLine;

	    private ColumnInfo FullnessCoefficient;
	    
	    private ColumnInfo NoLiquidFullnessCoefficient;

	    private ColumnInfo ResultName;

	    private ColumnInfo ResultCode;

	    private ColumnInfo OptimizationSuggestion;

	    private ColumnInfo LiquidVolumetricProduction;

	    private ColumnInfo OilVolumetricProduction;

	    private ColumnInfo WaterVolumetricProduction;

	    private ColumnInfo LiquidWeightProduction;
	    
	    private ColumnInfo OilWeightProduction;

	    private ColumnInfo WaterWeightProduction;
	    
	    private ColumnInfo TheoreticalProduction;
	    
	    private ColumnInfo PumpEff;
	    
	    private ColumnInfo CalcProducingfluidLevel;
	    
	    private ColumnInfo LevelDifferenceValue;
	    
	    private ColumnInfo Submergence;
	    
	    private ColumnInfo DownStrokeIMax;
	    
	    private ColumnInfo UpStrokeIMax;
	    
	    private ColumnInfo IDegreeBalance;
	    
	    private ColumnInfo DownStrokeWattMax;
	    
	    private ColumnInfo UpStrokeWattMax;
	    
	    private ColumnInfo WattDegreeBalance;
	    
	    private ColumnInfo DeltaRadius;
	    
	    private ColumnInfo WellDownSystemEfficiency;
	    
	    private ColumnInfo SurfaceSystemEfficiency;
	    
	    private ColumnInfo SystemEfficiency;
	    
	    private ColumnInfo EnergyPer100mLift;

		public ColumnInfo getWellName() {
			return WellName;
		}

		public void setWellName(ColumnInfo wellName) {
			WellName = wellName;
		}

		public ColumnInfo getAcqTime() {
			return AcqTime;
		}

		public void setAcqTime(ColumnInfo acqTime) {
			AcqTime = acqTime;
		}

		public ColumnInfo getFMax() {
			return FMax;
		}

		public void setFMax(ColumnInfo fMax) {
			FMax = fMax;
		}

		public ColumnInfo getFMin() {
			return FMin;
		}

		public void setFMin(ColumnInfo fMin) {
			FMin = fMin;
		}

		public ColumnInfo getFullnessCoefficient() {
			return FullnessCoefficient;
		}

		public void setFullnessCoefficient(ColumnInfo fullnessCoefficient) {
			FullnessCoefficient = fullnessCoefficient;
		}

		public ColumnInfo getResultName() {
			return ResultName;
		}

		public void setResultName(ColumnInfo reusltName) {
			ResultName = reusltName;
		}

		public ColumnInfo getResultCode() {
			return ResultCode;
		}

		public void setResultCode(ColumnInfo reusltCode) {
			ResultCode = reusltCode;
		}

		public ColumnInfo getOptimizationSuggestion() {
			return OptimizationSuggestion;
		}

		public void setOptimizationSuggestion(ColumnInfo optimizationSuggestion) {
			OptimizationSuggestion = optimizationSuggestion;
		}

		public ColumnInfo getLiquidVolumetricProduction() {
			return LiquidVolumetricProduction;
		}

		public void setLiquidVolumetricProduction(ColumnInfo liquidVolumetricProduction) {
			LiquidVolumetricProduction = liquidVolumetricProduction;
		}

		public ColumnInfo getOilVolumetricProduction() {
			return OilVolumetricProduction;
		}

		public void setOilVolumetricProduction(ColumnInfo oilVolumetricProduction) {
			OilVolumetricProduction = oilVolumetricProduction;
		}

		public ColumnInfo getWaterVolumetricProduction() {
			return WaterVolumetricProduction;
		}

		public void setWaterVolumetricProduction(ColumnInfo waterVolumetricProduction) {
			WaterVolumetricProduction = waterVolumetricProduction;
		}

		public ColumnInfo getLiquidWeightProduction() {
			return LiquidWeightProduction;
		}

		public void setLiquidWeightProduction(ColumnInfo liquidWeightProduction) {
			LiquidWeightProduction = liquidWeightProduction;
		}

		public ColumnInfo getWaterWeightProduction() {
			return WaterWeightProduction;
		}

		public void setWaterWeightProduction(ColumnInfo waterWeightProduction) {
			WaterWeightProduction = waterWeightProduction;
		}

		public ColumnInfo getOilWeightProduction() {
			return OilWeightProduction;
		}

		public void setOilWeightProduction(ColumnInfo oilWeightProduction) {
			OilWeightProduction = oilWeightProduction;
		}

		public ColumnInfo getUpperLoadLine() {
			return UpperLoadLine;
		}

		public void setUpperLoadLine(ColumnInfo upperLoadLine) {
			UpperLoadLine = upperLoadLine;
		}

		public ColumnInfo getLowerLoadLine() {
			return LowerLoadLine;
		}

		public void setLowerLoadLine(ColumnInfo lowerLoadLine) {
			LowerLoadLine = lowerLoadLine;
		}

		public ColumnInfo getNoLiquidFullnessCoefficient() {
			return NoLiquidFullnessCoefficient;
		}

		public void setNoLiquidFullnessCoefficient(ColumnInfo noLiquidFullnessCoefficient) {
			NoLiquidFullnessCoefficient = noLiquidFullnessCoefficient;
		}

		public ColumnInfo getTheoreticalProduction() {
			return TheoreticalProduction;
		}

		public void setTheoreticalProduction(ColumnInfo theoreticalProduction) {
			TheoreticalProduction = theoreticalProduction;
		}

		public ColumnInfo getPumpEff() {
			return PumpEff;
		}

		public void setPumpEff(ColumnInfo pumpEff) {
			PumpEff = pumpEff;
		}

		public ColumnInfo getCalcProducingfluidLevel() {
			return CalcProducingfluidLevel;
		}

		public void setCalcProducingfluidLevel(ColumnInfo calcProducingfluidLevel) {
			CalcProducingfluidLevel = calcProducingfluidLevel;
		}

		public ColumnInfo getLevelDifferenceValue() {
			return LevelDifferenceValue;
		}

		public void setLevelDifferenceValue(ColumnInfo levelDifferenceValue) {
			LevelDifferenceValue = levelDifferenceValue;
		}

		public ColumnInfo getSubmergence() {
			return Submergence;
		}

		public void setSubmergence(ColumnInfo submergence) {
			Submergence = submergence;
		}

		public ColumnInfo getDownStrokeIMax() {
			return DownStrokeIMax;
		}

		public void setDownStrokeIMax(ColumnInfo downStrokeIMax) {
			DownStrokeIMax = downStrokeIMax;
		}

		public ColumnInfo getUpStrokeIMax() {
			return UpStrokeIMax;
		}

		public void setUpStrokeIMax(ColumnInfo upStrokeIMax) {
			UpStrokeIMax = upStrokeIMax;
		}

		public ColumnInfo getIDegreeBalance() {
			return IDegreeBalance;
		}

		public void setIDegreeBalance(ColumnInfo iDegreeBalance) {
			IDegreeBalance = iDegreeBalance;
		}

		public ColumnInfo getDownStrokeWattMax() {
			return DownStrokeWattMax;
		}

		public void setDownStrokeWattMax(ColumnInfo downStrokeWattMax) {
			DownStrokeWattMax = downStrokeWattMax;
		}

		public ColumnInfo getUpStrokeWattMax() {
			return UpStrokeWattMax;
		}

		public void setUpStrokeWattMax(ColumnInfo upStrokeWattMax) {
			UpStrokeWattMax = upStrokeWattMax;
		}

		public ColumnInfo getWattDegreeBalance() {
			return WattDegreeBalance;
		}

		public void setWattDegreeBalance(ColumnInfo wattDegreeBalance) {
			WattDegreeBalance = wattDegreeBalance;
		}

		public ColumnInfo getDeltaRadius() {
			return DeltaRadius;
		}

		public void setDeltaRadius(ColumnInfo deltaRadius) {
			DeltaRadius = deltaRadius;
		}

		public ColumnInfo getWellDownSystemEfficiency() {
			return WellDownSystemEfficiency;
		}

		public void setWellDownSystemEfficiency(ColumnInfo wellDownSystemEfficiency) {
			WellDownSystemEfficiency = wellDownSystemEfficiency;
		}

		public ColumnInfo getSurfaceSystemEfficiency() {
			return SurfaceSystemEfficiency;
		}

		public void setSurfaceSystemEfficiency(ColumnInfo surfaceSystemEfficiency) {
			SurfaceSystemEfficiency = surfaceSystemEfficiency;
		}

		public ColumnInfo getSystemEfficiency() {
			return SystemEfficiency;
		}

		public void setSystemEfficiency(ColumnInfo systemEfficiency) {
			SystemEfficiency = systemEfficiency;
		}

		public ColumnInfo getEnergyPer100mLift() {
			return EnergyPer100mLift;
		}

		public void setEnergyPer100mLift(ColumnInfo energyPer100mLift) {
			EnergyPer100mLift = energyPer100mLift;
		}
	}
	
	public static class DiagramResultConfig
	{
	    private String TableName;

	    private boolean Enable;

	    private DiagramResultColumn Columns;

	    public void setTableName(String TableName){
	        this.TableName = TableName;
	    }
	    public String getTableName(){
	        return this.TableName;
	    }
	    public void setEnable(boolean Enable){
	        this.Enable = Enable;
	    }
	    public boolean getEnable(){
	        return this.Enable;
	    }
	    public void setColumns(DiagramResultColumn Columns){
	        this.Columns = Columns;
	    }
	    public DiagramResultColumn getColumns(){
	        return this.Columns;
	    }
	}

	public boolean isEnable() {
		return Enable;
	}

	public void setEnable(boolean enable) {
		Enable = enable;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public int getPort() {
		return Port;
	}

	public void setPort(int port) {
		Port = port;
	}

	public String getInstanceName() {
		return InstanceName;
	}

	public void setInstanceName(String instanceName) {
		InstanceName = instanceName;
	}

	public int getVersion() {
		return Version;
	}

	public void setVersion(int version) {
		Version = version;
	}

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		User = user;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getWriteType() {
		return WriteType;
	}

	public void setWriteType(String writeType) {
		WriteType = writeType;
	}

	public DiagramResultConfig getDiagramResult() {
		return DiagramResult;
	}

	public void setDiagramResult(DiagramResultConfig diagramResult) {
		DiagramResult = diagramResult;
	}
}
