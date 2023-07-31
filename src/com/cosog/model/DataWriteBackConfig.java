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
	}
	
	public static class DiagramResultColumn
	{
	    private ColumnInfo WellName;

	    private ColumnInfo AcqTime;

	    private ColumnInfo FMax;

	    private ColumnInfo FMin;

	    private ColumnInfo FullnessCoefficient;

	    private ColumnInfo ResultName;

	    private ColumnInfo ResultCode;

	    private ColumnInfo OptimizationSuggestion;

	    private ColumnInfo LiquidVolumetricProduction;

	    private ColumnInfo OilVolumetricProduction;

	    private ColumnInfo WaterVolumetricProduction;

	    private ColumnInfo LiquidWeightProduction;
	    
	    private ColumnInfo OilWeightProduction;

	    private ColumnInfo WaterWeightProduction;

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
