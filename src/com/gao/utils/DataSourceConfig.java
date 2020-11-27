package com.gao.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DataSourceConfig {
	public static DataSourceConfig dataSourceConfig=null;
	
	@SuppressWarnings("static-access")
	public static DataSourceConfig getInstance(){
		if(dataSourceConfig==null){
			Gson gson = new Gson();
			StringManagerUtils stringManagerUtils=new StringManagerUtils();
			String path=stringManagerUtils.getFilePath("config.json","dataSource/");
			String configData=stringManagerUtils.readFile(path,"utf-8");
			java.lang.reflect.Type type = new TypeToken<DataSourceConfig>() {}.getType();
			dataSourceConfig=gson.fromJson(configData, type);
		}
		return dataSourceConfig;
	}
	
	 private String Code;
	 private String IP;
	 private int Port;
	 private int Type;
	 private String Version;
	 private String InstanceName;
	 private String User;
	 private String Password;
	 private DiagramTable DiagramTable;
	 private ReservoirTable ReservoirTable;
	 private RodStringTable RodStringTable;
	 private TubingStringTable TubingStringTable;
	 private CasingStringTable CasingStringTable;
	 private PumpTable PumpTable;
	 private ProductionTable ProductionTable;
	
	public static class Column
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
	
	public static class DiagramTableColumns
	{
	    private Column WellName;

	    private Column AcqTime;

	    private Column Stroke;

	    private Column SPM;

	    private Column PointCount;

	    private Column S;

	    private Column F;

	    private Column I;

	    private Column KWatt;

	    public void setWellName(Column WellName){
	        this.WellName = WellName;
	    }
	    public Column getWellName(){
	        return this.WellName;
	    }
	    public void setAcqTime(Column AcqTime){
	        this.AcqTime = AcqTime;
	    }
	    public Column getAcqTime(){
	        return this.AcqTime;
	    }
	    public void setStroke(Column Stroke){
	        this.Stroke = Stroke;
	    }
	    public Column getStroke(){
	        return this.Stroke;
	    }
	    public void setSPM(Column SPM){
	        this.SPM = SPM;
	    }
	    public Column getSPM(){
	        return this.SPM;
	    }
	    public void setPointCount(Column PointCount){
	        this.PointCount = PointCount;
	    }
	    public Column getPointCount(){
	        return this.PointCount;
	    }
	    public void setS(Column S){
	        this.S = S;
	    }
	    public Column getS(){
	        return this.S;
	    }
	    public void setF(Column F){
	        this.F = F;
	    }
	    public Column getF(){
	        return this.F;
	    }
	    public void setI(Column I){
	        this.I = I;
	    }
	    public Column getI(){
	        return this.I;
	    }
	    public void setKWatt(Column KWatt){
	        this.KWatt = KWatt;
	    }
	    public Column getKWatt(){
	        return this.KWatt;
	    }
	}
	
	public static class ReservoirTableColumns
	{
	    private Column WellName;

	    private Column Depth;

	    private Column Temperature;

		public Column getWellName() {
			return WellName;
		}

		public void setWellName(Column wellName) {
			WellName = wellName;
		}

		public Column getDepth() {
			return Depth;
		}

		public void setDepth(Column depth) {
			Depth = depth;
		}

		public Column getTemperature() {
			return Temperature;
		}

		public void setTemperature(Column temperature) {
			Temperature = temperature;
		}
	}
	
	public static class RodStringTableColumns
	{
	    private Column WellName;

	    private Column Grade1;
	    private Column OutsideDiameter1;
	    private Column InsideDiameter1;
	    private Column Length1;
	    
	    private Column Grade2;
	    private Column OutsideDiameter2;
	    private Column InsideDiameter2;
	    private Column Length2;
	    
	    private Column Grade3;
	    private Column OutsideDiameter3;
	    private Column InsideDiameter3;
	    private Column Length3;
		public Column getWellName() {
			return WellName;
		}
		public void setWellName(Column wellName) {
			WellName = wellName;
		}
		public Column getGrade1() {
			return Grade1;
		}
		public void setGrade1(Column grade1) {
			Grade1 = grade1;
		}
		public Column getOutsideDiameter1() {
			return OutsideDiameter1;
		}
		public void setOutsideDiameter1(Column outsideDiameter1) {
			OutsideDiameter1 = outsideDiameter1;
		}
		public Column getInsideDiameter1() {
			return InsideDiameter1;
		}
		public void setInsideDiameter1(Column insideDiameter1) {
			InsideDiameter1 = insideDiameter1;
		}
		public Column getLength1() {
			return Length1;
		}
		public void setLength1(Column length1) {
			Length1 = length1;
		}
		public Column getGrade2() {
			return Grade2;
		}
		public void setGrade2(Column grade2) {
			Grade2 = grade2;
		}
		public Column getOutsideDiameter2() {
			return OutsideDiameter2;
		}
		public void setOutsideDiameter2(Column outsideDiameter2) {
			OutsideDiameter2 = outsideDiameter2;
		}
		public Column getInsideDiameter2() {
			return InsideDiameter2;
		}
		public void setInsideDiameter2(Column insideDiameter2) {
			InsideDiameter2 = insideDiameter2;
		}
		public Column getLength2() {
			return Length2;
		}
		public void setLength2(Column length2) {
			Length2 = length2;
		}
		public Column getGrade3() {
			return Grade3;
		}
		public void setGrade3(Column grade3) {
			Grade3 = grade3;
		}
		public Column getOutsideDiameter3() {
			return OutsideDiameter3;
		}
		public void setOutsideDiameter3(Column outsideDiameter3) {
			OutsideDiameter3 = outsideDiameter3;
		}
		public Column getInsideDiameter3() {
			return InsideDiameter3;
		}
		public void setInsideDiameter3(Column insideDiameter3) {
			InsideDiameter3 = insideDiameter3;
		}
		public Column getLength3() {
			return Length3;
		}
		public void setLength3(Column length3) {
			Length3 = length3;
		}
	}
	
	public static class TubingStringTableColumns
	{
	    private Column WellName;

	    private Column InsideDiameter;

		public Column getWellName() {
			return WellName;
		}

		public void setWellName(Column wellName) {
			WellName = wellName;
		}

		public Column getInsideDiameter() {
			return InsideDiameter;
		}

		public void setInsideDiameter(Column insideDiameter) {
			InsideDiameter = insideDiameter;
		}
	}
	
	public static class CasingStringTableColumns
	{
	    private Column WellName;

	    private Column InsideDiameter;

		public Column getWellName() {
			return WellName;
		}

		public void setWellName(Column wellName) {
			WellName = wellName;
		}

		public Column getInsideDiameter() {
			return InsideDiameter;
		}

		public void setInsideDiameter(Column insideDiameter) {
			InsideDiameter = insideDiameter;
		}
	}
	
	public static class PumpTableColumns
	{
	    private Column WellName;

	    private Column BarrelType;
	    private Column PumpGrade;
	    private Column BarrelLength;
	    private Column PlungerLength;
	    private Column PumpBoreDiameter;
		public Column getWellName() {
			return WellName;
		}
		public void setWellName(Column wellName) {
			WellName = wellName;
		}
		public Column getBarrelType() {
			return BarrelType;
		}
		public void setBarrelType(Column barrelType) {
			BarrelType = barrelType;
		}
		public Column getPumpGrade() {
			return PumpGrade;
		}
		public void setPumpGrade(Column pumpGrade) {
			PumpGrade = pumpGrade;
		}
		public Column getBarrelLength() {
			return BarrelLength;
		}
		public void setBarrelLength(Column barrelLength) {
			BarrelLength = barrelLength;
		}
		public Column getPlungerLength() {
			return PlungerLength;
		}
		public void setPlungerLength(Column plungerLength) {
			PlungerLength = plungerLength;
		}
		public Column getPumpBoreDiameter() {
			return PumpBoreDiameter;
		}
		public void setPumpBoreDiameter(Column pumpBoreDiameter) {
			PumpBoreDiameter = pumpBoreDiameter;
		}
	}
	
	public static class ProductionTableColumns
	{
	    private Column WellName;

	    private Column WaterCut;
	    private Column ProductionGasOilRatio;
	    private Column TubingPressure;
	    private Column CasingPressure;
	    private Column BackPressure;
	    private Column WellHeadFluidTemperature;
	    private Column ProducingfluidLevel;
	    private Column PumpSettingDepth;
		public Column getWellName() {
			return WellName;
		}
		public void setWellName(Column wellName) {
			WellName = wellName;
		}
		public Column getWaterCut() {
			return WaterCut;
		}
		public void setWaterCut(Column waterCut) {
			WaterCut = waterCut;
		}
		public Column getProductionGasOilRatio() {
			return ProductionGasOilRatio;
		}
		public void setProductionGasOilRatio(Column productionGasOilRatio) {
			ProductionGasOilRatio = productionGasOilRatio;
		}
		public Column getTubingPressure() {
			return TubingPressure;
		}
		public void setTubingPressure(Column tubingPressure) {
			TubingPressure = tubingPressure;
		}
		public Column getCasingPressure() {
			return CasingPressure;
		}
		public void setCasingPressure(Column casingPressure) {
			CasingPressure = casingPressure;
		}
		public Column getBackPressure() {
			return BackPressure;
		}
		public void setBackPressure(Column backPressure) {
			BackPressure = backPressure;
		}
		public Column getWellHeadFluidTemperature() {
			return WellHeadFluidTemperature;
		}
		public void setWellHeadFluidTemperature(Column wellHeadFluidTemperature) {
			WellHeadFluidTemperature = wellHeadFluidTemperature;
		}
		public Column getProducingfluidLevel() {
			return ProducingfluidLevel;
		}
		public void setProducingfluidLevel(Column producingfluidLevel) {
			ProducingfluidLevel = producingfluidLevel;
		}
		public Column getPumpSettingDepth() {
			return PumpSettingDepth;
		}
		public void setPumpSettingDepth(Column pumpSettingDepth) {
			PumpSettingDepth = pumpSettingDepth;
		}
	}
	
	public static class DiagramTable
	{
		private String Name;
	    private DiagramTableColumns Columns;
	    public void setColumns(DiagramTableColumns Columns){
	        this.Columns = Columns;
	    }
	    public DiagramTableColumns getColumns(){
	        return this.Columns;
	    }
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
	}
	
	public static class ReservoirTable
	{
		private String Name;
	    private ReservoirTableColumns Columns;
	    public void setColumns(ReservoirTableColumns Columns){
	        this.Columns = Columns;
	    }
	    public ReservoirTableColumns getColumns(){
	        return this.Columns;
	    }
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
	}
	
	public static class RodStringTable
	{
		private String Name;
	    private RodStringTableColumns Columns;
	    public void setColumns(RodStringTableColumns Columns){
	        this.Columns = Columns;
	    }
	    public RodStringTableColumns getColumns(){
	        return this.Columns;
	    }
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
	}
	
	public static class TubingStringTable
	{
		private String Name;
	    private TubingStringTableColumns Columns;
	    public void setColumns(TubingStringTableColumns Columns){
	        this.Columns = Columns;
	    }
	    public TubingStringTableColumns getColumns(){
	        return this.Columns;
	    }
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
	}
	
	public static class CasingStringTable
	{
		private String Name;
	    private CasingStringTableColumns Columns;
	    public void setColumns(CasingStringTableColumns Columns){
	        this.Columns = Columns;
	    }
	    public CasingStringTableColumns getColumns(){
	        return this.Columns;
	    }
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
	}
	
	public static class PumpTable
	{
		private String Name;
	    private PumpTableColumns Columns;
	    public void setColumns(PumpTableColumns Columns){
	        this.Columns = Columns;
	    }
	    public PumpTableColumns getColumns(){
	        return this.Columns;
	    }
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
	}
	
	public static class ProductionTable
	{
		private String Name;
	    private ProductionTableColumns Columns;
	    public void setColumns(ProductionTableColumns Columns){
	        this.Columns = Columns;
	    }
	    public ProductionTableColumns getColumns(){
	        return this.Columns;
	    }
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
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

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
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

	public void setTubingStringTable(TubingStringTable tubingStringrTable) {
		TubingStringTable = tubingStringrTable;
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
