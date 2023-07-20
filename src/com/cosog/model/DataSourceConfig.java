package com.cosog.model;

public class DataSourceConfig {

	private boolean Enable;

    private String IP;

    private int Port;

    private String InstanceName;

    private String User;

    private String Password;

    private DiagramTable DiagramTable;

    private CommStatusTable CommStatusTable;

    private RunStatusTable RunStatusTable;

    private ReservoirTable ReservoirTable;

    private RodStringTable RodStringTable;

    private TubingStringTable TubingStringTable;

    private CasingStringTable CasingStringTable;

    private PumpTable PumpTable;

    private ProductionTable ProductionTable;
	
	public static class ColumnInfo
	{
	    private String Column;

	    private String Type;

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
	}
	
	public static class FESDiagramTableColumns
	{
	    private ColumnInfo WellName;

	    private ColumnInfo AcqTime;

	    private ColumnInfo Stroke;

	    private ColumnInfo SPM;

	    private ColumnInfo PointCount;

	    private ColumnInfo S;

	    private ColumnInfo F;

	    private ColumnInfo I;

	    private ColumnInfo KWatt;

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

		public ColumnInfo getStroke() {
			return Stroke;
		}

		public void setStroke(ColumnInfo stroke) {
			Stroke = stroke;
		}

		public ColumnInfo getSPM() {
			return SPM;
		}

		public void setSPM(ColumnInfo sPM) {
			SPM = sPM;
		}

		public ColumnInfo getPointCount() {
			return PointCount;
		}

		public void setPointCount(ColumnInfo pointCount) {
			PointCount = pointCount;
		}

		public ColumnInfo getS() {
			return S;
		}

		public void setS(ColumnInfo s) {
			S = s;
		}

		public ColumnInfo getF() {
			return F;
		}

		public void setF(ColumnInfo f) {
			F = f;
		}

		public ColumnInfo getI() {
			return I;
		}

		public void setI(ColumnInfo i) {
			I = i;
		}

		public ColumnInfo getKWatt() {
			return KWatt;
		}

		public void setKWatt(ColumnInfo kWatt) {
			KWatt = kWatt;
		}
	}
	
	public static class DiagramTable
	{
	    private String Name;

	    private boolean Enable;

	    private FESDiagramTableColumns Columns;

	    public void setName(String Name){
	        this.Name = Name;
	    }
	    public String getName(){
	        return this.Name;
	    }
	    public void setEnable(boolean Enable){
	        this.Enable = Enable;
	    }
	    public boolean getEnable(){
	        return this.Enable;
	    }
	    public void setColumns(FESDiagramTableColumns Columns){
	        this.Columns = Columns;
	    }
	    public FESDiagramTableColumns getColumns(){
	        return this.Columns;
	    }
	}
	
	public static class CommStatusTableColumns
	{
	    private ColumnInfo WellName;

	    private ColumnInfo CommStatus;

		public ColumnInfo getWellName() {
			return WellName;
		}

		public void setWellName(ColumnInfo wellName) {
			WellName = wellName;
		}

		public ColumnInfo getCommStatus() {
			return CommStatus;
		}

		public void setCommStatus(ColumnInfo commStatus) {
			CommStatus = commStatus;
		}
	}
	
	public static class CommStatusTable
	{
	    private String Name;

	    private boolean Enable;

	    private CommStatusTableColumns Columns;

	    public void setName(String Name){
	        this.Name = Name;
	    }
	    public String getName(){
	        return this.Name;
	    }
	    public void setEnable(boolean Enable){
	        this.Enable = Enable;
	    }
	    public boolean getEnable(){
	        return this.Enable;
	    }
	    public void setColumns(CommStatusTableColumns Columns){
	        this.Columns = Columns;
	    }
	    public CommStatusTableColumns getColumns(){
	        return this.Columns;
	    }
	}
	
	public static class RunStatusTableColumns
	{
	    private ColumnInfo WellName;

	    private ColumnInfo RunStatus;

		public ColumnInfo getWellName() {
			return WellName;
		}

		public void setWellName(ColumnInfo wellName) {
			WellName = wellName;
		}

		public ColumnInfo getRunStatus() {
			return RunStatus;
		}

		public void setRunStatus(ColumnInfo runStatus) {
			RunStatus = runStatus;
		}
	}
	
	public class RunStatusTable
	{
	    private String Name;

	    private boolean Enable;

	    private RunStatusTableColumns Columns;

	    public void setName(String Name){
	        this.Name = Name;
	    }
	    public String getName(){
	        return this.Name;
	    }
	    public void setEnable(boolean Enable){
	        this.Enable = Enable;
	    }
	    public boolean getEnable(){
	        return this.Enable;
	    }
	    public void setColumns(RunStatusTableColumns Columns){
	        this.Columns = Columns;
	    }
	    public RunStatusTableColumns getColumns(){
	        return this.Columns;
	    }
	}
	
	public static class ReservoirTableColumns
	{
	    private ColumnInfo WellName;

	    private ColumnInfo Depth;
	    
	    private ColumnInfo Temperature;

		public ColumnInfo getWellName() {
			return WellName;
		}

		public void setWellName(ColumnInfo wellName) {
			WellName = wellName;
		}

		public ColumnInfo getDepth() {
			return Depth;
		}

		public void setDepth(ColumnInfo depth) {
			Depth = depth;
		}

		public ColumnInfo getTemperature() {
			return Temperature;
		}

		public void setTemperature(ColumnInfo temperature) {
			Temperature = temperature;
		}
	}
	
	public static class ReservoirTable
	{
	    private String Name;

	    private boolean Enable;

	    private ReservoirTableColumns Columns;

	    public void setName(String Name){
	        this.Name = Name;
	    }
	    public String getName(){
	        return this.Name;
	    }
	    public void setEnable(boolean Enable){
	        this.Enable = Enable;
	    }
	    public boolean getEnable(){
	        return this.Enable;
	    }
	    public void setColumns(ReservoirTableColumns Columns){
	        this.Columns = Columns;
	    }
	    public ReservoirTableColumns getColumns(){
	        return this.Columns;
	    }
	}
	
	public static class RodStringTableColumns
	{
	    private ColumnInfo WellName;

	    private ColumnInfo Grade1;
	    
	    private ColumnInfo OutsideDiameter1;
	    
	    private ColumnInfo InsideDiameter1;
	    
	    private ColumnInfo Length1;
	    
	    private ColumnInfo Grade2;
	    
	    private ColumnInfo OutsideDiameter2;
	    
	    private ColumnInfo InsideDiameter2;
	    
	    private ColumnInfo Length2;
	    
	    private ColumnInfo Grade3;
	    
	    private ColumnInfo OutsideDiameter3;
	    
	    private ColumnInfo InsideDiameter3;
	    
	    private ColumnInfo Length3;
	    
	    private ColumnInfo Grade4;
	    
	    private ColumnInfo OutsideDiameter4;
	    
	    private ColumnInfo InsideDiameter4;
	    
	    private ColumnInfo Length4;

		public ColumnInfo getWellName() {
			return WellName;
		}

		public void setWellName(ColumnInfo wellName) {
			WellName = wellName;
		}

		public ColumnInfo getGrade1() {
			return Grade1;
		}

		public void setGrade1(ColumnInfo grade1) {
			Grade1 = grade1;
		}

		public ColumnInfo getOutsideDiameter1() {
			return OutsideDiameter1;
		}

		public void setOutsideDiameter1(ColumnInfo outsideDiameter1) {
			OutsideDiameter1 = outsideDiameter1;
		}

		public ColumnInfo getInsideDiameter1() {
			return InsideDiameter1;
		}

		public void setInsideDiameter1(ColumnInfo insideDiameter1) {
			InsideDiameter1 = insideDiameter1;
		}

		public ColumnInfo getLength1() {
			return Length1;
		}

		public void setLength1(ColumnInfo length1) {
			Length1 = length1;
		}

		public ColumnInfo getGrade2() {
			return Grade2;
		}

		public void setGrade2(ColumnInfo grade2) {
			Grade2 = grade2;
		}

		public ColumnInfo getOutsideDiameter2() {
			return OutsideDiameter2;
		}

		public void setOutsideDiameter2(ColumnInfo outsideDiameter2) {
			OutsideDiameter2 = outsideDiameter2;
		}

		public ColumnInfo getInsideDiameter2() {
			return InsideDiameter2;
		}

		public void setInsideDiameter2(ColumnInfo insideDiameter2) {
			InsideDiameter2 = insideDiameter2;
		}

		public ColumnInfo getLength2() {
			return Length2;
		}

		public void setLength2(ColumnInfo length2) {
			Length2 = length2;
		}

		public ColumnInfo getGrade3() {
			return Grade3;
		}

		public void setGrade3(ColumnInfo grade3) {
			Grade3 = grade3;
		}

		public ColumnInfo getOutsideDiameter3() {
			return OutsideDiameter3;
		}

		public void setOutsideDiameter3(ColumnInfo outsideDiameter3) {
			OutsideDiameter3 = outsideDiameter3;
		}

		public ColumnInfo getInsideDiameter3() {
			return InsideDiameter3;
		}

		public void setInsideDiameter3(ColumnInfo insideDiameter3) {
			InsideDiameter3 = insideDiameter3;
		}

		public ColumnInfo getLength3() {
			return Length3;
		}

		public void setLength3(ColumnInfo length3) {
			Length3 = length3;
		}

		public ColumnInfo getGrade4() {
			return Grade4;
		}

		public void setGrade4(ColumnInfo grade4) {
			Grade4 = grade4;
		}

		public ColumnInfo getOutsideDiameter4() {
			return OutsideDiameter4;
		}

		public void setOutsideDiameter4(ColumnInfo outsideDiameter4) {
			OutsideDiameter4 = outsideDiameter4;
		}

		public ColumnInfo getInsideDiameter4() {
			return InsideDiameter4;
		}

		public void setInsideDiameter4(ColumnInfo insideDiameter4) {
			InsideDiameter4 = insideDiameter4;
		}

		public ColumnInfo getLength4() {
			return Length4;
		}

		public void setLength4(ColumnInfo length4) {
			Length4 = length4;
		}
	}
	
	public static class RodStringTable
	{
	    private String Name;

	    private boolean Enable;

	    private RodStringTableColumns Columns;

	    public void setName(String Name){
	        this.Name = Name;
	    }
	    public String getName(){
	        return this.Name;
	    }
	    public void setEnable(boolean Enable){
	        this.Enable = Enable;
	    }
	    public boolean getEnable(){
	        return this.Enable;
	    }
	    public void setColumns(RodStringTableColumns Columns){
	        this.Columns = Columns;
	    }
	    public RodStringTableColumns getColumns(){
	        return this.Columns;
	    }
	}
	
	public static class TubingStringTableColumns
	{
	    private ColumnInfo WellName;

	    private ColumnInfo InsideDiameter;

		public ColumnInfo getWellName() {
			return WellName;
		}

		public void setWellName(ColumnInfo wellName) {
			WellName = wellName;
		}

		public ColumnInfo getInsideDiameter() {
			return InsideDiameter;
		}

		public void setInsideDiameter(ColumnInfo insideDiameter) {
			InsideDiameter = insideDiameter;
		}
	}
	
	public static class TubingStringTable
	{
	    private String Name;

	    private boolean Enable;

	    private TubingStringTableColumns Columns;

	    public void setName(String Name){
	        this.Name = Name;
	    }
	    public String getName(){
	        return this.Name;
	    }
	    public void setEnable(boolean Enable){
	        this.Enable = Enable;
	    }
	    public boolean getEnable(){
	        return this.Enable;
	    }
	    public void setColumns(TubingStringTableColumns Columns){
	        this.Columns = Columns;
	    }
	    public TubingStringTableColumns getColumns(){
	        return this.Columns;
	    }
	}
	
	public static class CasingStringTableColumns
	{
	    private ColumnInfo WellName;

	    private ColumnInfo InsideDiameter;

		public ColumnInfo getWellName() {
			return WellName;
		}

		public void setWellName(ColumnInfo wellName) {
			WellName = wellName;
		}

		public ColumnInfo getInsideDiameter() {
			return InsideDiameter;
		}

		public void setInsideDiameter(ColumnInfo insideDiameter) {
			InsideDiameter = insideDiameter;
		}
	}
	
	public static class CasingStringTable
	{
	    private String Name;

	    private boolean Enable;

	    private CasingStringTableColumns Columns;

	    public void setName(String Name){
	        this.Name = Name;
	    }
	    public String getName(){
	        return this.Name;
	    }
	    public void setEnable(boolean Enable){
	        this.Enable = Enable;
	    }
	    public boolean getEnable(){
	        return this.Enable;
	    }
	    public void setColumns(CasingStringTableColumns Columns){
	        this.Columns = Columns;
	    }
	    public CasingStringTableColumns getColumns(){
	        return this.Columns;
	    }
	}
	
	public static class PumpTableColumns
	{
	    private ColumnInfo WellName;

	    private ColumnInfo BarrelType;

	    private ColumnInfo PumpGrade;
	    
	    private ColumnInfo BarrelLength;
	    
	    private ColumnInfo PlungerLength;
	    
	    private ColumnInfo PumpBoreDiameter;

		public ColumnInfo getWellName() {
			return WellName;
		}

		public void setWellName(ColumnInfo wellName) {
			WellName = wellName;
		}

		public ColumnInfo getBarrelType() {
			return BarrelType;
		}

		public void setBarrelType(ColumnInfo barrelType) {
			BarrelType = barrelType;
		}

		public ColumnInfo getPumpGrade() {
			return PumpGrade;
		}

		public void setPumpGrade(ColumnInfo pumpGrade) {
			PumpGrade = pumpGrade;
		}

		public ColumnInfo getBarrelLength() {
			return BarrelLength;
		}

		public void setBarrelLength(ColumnInfo barrelLength) {
			BarrelLength = barrelLength;
		}

		public ColumnInfo getPlungerLength() {
			return PlungerLength;
		}

		public void setPlungerLength(ColumnInfo plungerLength) {
			PlungerLength = plungerLength;
		}

		public ColumnInfo getPumpBoreDiameter() {
			return PumpBoreDiameter;
		}

		public void setPumpBoreDiameter(ColumnInfo pumpBoreDiameter) {
			PumpBoreDiameter = pumpBoreDiameter;
		}
	}
	
	public static class PumpTable
	{
	    private String Name;

	    private boolean Enable;

	    private PumpTableColumns Columns;

	    public void setName(String Name){
	        this.Name = Name;
	    }
	    public String getName(){
	        return this.Name;
	    }
	    public void setEnable(boolean Enable){
	        this.Enable = Enable;
	    }
	    public boolean getEnable(){
	        return this.Enable;
	    }
	    public void setColumns(PumpTableColumns Columns){
	        this.Columns = Columns;
	    }
	    public PumpTableColumns getColumns(){
	        return this.Columns;
	    }
	}
	
	public static class ProductionTableColumns
	{
	    private ColumnInfo WellName;

	    private ColumnInfo WaterCut;

	    private ColumnInfo ProductionGasOilRatio;
	    
	    private ColumnInfo TubingPressure;
	    
	    private ColumnInfo CasingPressure;
	    
	    private ColumnInfo BackPressure;

	    private ColumnInfo WellHeadFluidTemperature;
	    
	    private ColumnInfo ProducingfluidLevel;
	    
	    private ColumnInfo PumpSettingDepth;

		public ColumnInfo getWellName() {
			return WellName;
		}

		public void setWellName(ColumnInfo wellName) {
			WellName = wellName;
		}

		public ColumnInfo getWaterCut() {
			return WaterCut;
		}

		public void setWaterCut(ColumnInfo waterCut) {
			WaterCut = waterCut;
		}

		public ColumnInfo getProductionGasOilRatio() {
			return ProductionGasOilRatio;
		}

		public void setProductionGasOilRatio(ColumnInfo productionGasOilRatio) {
			ProductionGasOilRatio = productionGasOilRatio;
		}

		public ColumnInfo getTubingPressure() {
			return TubingPressure;
		}

		public void setTubingPressure(ColumnInfo tubingPressure) {
			TubingPressure = tubingPressure;
		}

		public ColumnInfo getCasingPressure() {
			return CasingPressure;
		}

		public void setCasingPressure(ColumnInfo casingPressure) {
			CasingPressure = casingPressure;
		}

		public ColumnInfo getBackPressure() {
			return BackPressure;
		}

		public void setBackPressure(ColumnInfo backPressure) {
			BackPressure = backPressure;
		}

		public ColumnInfo getWellHeadFluidTemperature() {
			return WellHeadFluidTemperature;
		}

		public void setWellHeadFluidTemperature(ColumnInfo wellHeadFluidTemperature) {
			WellHeadFluidTemperature = wellHeadFluidTemperature;
		}

		public ColumnInfo getProducingfluidLevel() {
			return ProducingfluidLevel;
		}

		public void setProducingfluidLevel(ColumnInfo producingfluidLevel) {
			ProducingfluidLevel = producingfluidLevel;
		}

		public ColumnInfo getPumpSettingDepth() {
			return PumpSettingDepth;
		}

		public void setPumpSettingDepth(ColumnInfo pumpSettingDepth) {
			PumpSettingDepth = pumpSettingDepth;
		}
	}
	
	public static class ProductionTable
	{
	    private String Name;

	    private boolean Enable;

	    private ProductionTableColumns Columns;

	    public void setName(String Name){
	        this.Name = Name;
	    }
	    public String getName(){
	        return this.Name;
	    }
	    public void setEnable(boolean Enable){
	        this.Enable = Enable;
	    }
	    public boolean getEnable(){
	        return this.Enable;
	    }
	    public void setColumns(ProductionTableColumns Columns){
	        this.Columns = Columns;
	    }
	    public ProductionTableColumns getColumns(){
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

	public DiagramTable getDiagramTable() {
		return DiagramTable;
	}

	public void setDiagramTable(DiagramTable diagramTable) {
		DiagramTable = diagramTable;
	}

	public CommStatusTable getCommStatusTable() {
		return CommStatusTable;
	}

	public void setCommStatusTable(CommStatusTable commStatusTable) {
		CommStatusTable = commStatusTable;
	}

	public RunStatusTable getRunStatusTable() {
		return RunStatusTable;
	}

	public void setRunStatusTable(RunStatusTable runStatusTable) {
		RunStatusTable = runStatusTable;
	}

	public ReservoirTable getReservoirTable() {
		return ReservoirTable;
	}

	public void setReservoirTable(ReservoirTable reservoirTable) {
		ReservoirTable = reservoirTable;
	}

	public RodStringTable getRodStringTable() {
		return RodStringTable;
	}

	public void setRodStringTable(RodStringTable rodStringTable) {
		RodStringTable = rodStringTable;
	}

	public TubingStringTable getTubingStringTable() {
		return TubingStringTable;
	}

	public void setTubingStringTable(TubingStringTable tubingStringTable) {
		TubingStringTable = tubingStringTable;
	}

	public CasingStringTable getCasingStringTable() {
		return CasingStringTable;
	}

	public void setCasingStringTable(CasingStringTable casingStringTable) {
		CasingStringTable = casingStringTable;
	}

	public PumpTable getPumpTable() {
		return PumpTable;
	}

	public void setPumpTable(PumpTable pumpTable) {
		PumpTable = pumpTable;
	}

	public ProductionTable getProductionTable() {
		return ProductionTable;
	}

	public void setProductionTable(ProductionTable productionTable) {
		ProductionTable = productionTable;
	}
}
