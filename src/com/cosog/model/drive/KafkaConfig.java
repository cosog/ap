package com.cosog.model.drive;

public class KafkaConfig {
	
	private String ProtocolName;

    private String ProtocolCode;
    
    private String Version;

    private int Sort;

    private Server Server;

    private Topic Topic;

    public void setProtocolName(String ProtocolName){
        this.ProtocolName = ProtocolName;
    }
    public String getProtocolName(){
        return this.ProtocolName;
    }
    public void setProtocolCode(String ProtocolCode){
        this.ProtocolCode = ProtocolCode;
    }
    public String getProtocolCode(){
        return this.ProtocolCode;
    }
    public void setSort(int Sort){
        this.Sort = Sort;
    }
    public int getSort(){
        return this.Sort;
    }
    public void setServer(Server Server){
        this.Server = Server;
    }
    public Server getServer(){
        return this.Server;
    }
    public void setTopic(Topic Topic){
        this.Topic = Topic;
    }
    public Topic getTopic(){
        return this.Topic;
    }

	public static class Server
	{
	    private String IP;

	    private int Port;

	    public void setIP(String IP){
	        this.IP = IP;
	    }
	    public String getIP(){
	        return this.IP;
	    }
	    public void setPort(int Port){
	        this.Port = Port;
	    }
	    public int getPort(){
	        return this.Port;
	    }
	}
	
	public static class Up
	{
	    private String NormData;

	    private String RawData;

	    private String Config;

	    private String Model;

	    private String Freq;

	    private String RTC;

	    private String Online;

	    private String RunStatus;

	    public void setNormData(String NormData){
	        this.NormData = NormData;
	    }
	    public String getNormData(){
	        return this.NormData;
	    }
	    public void setRawData(String RawData){
	        this.RawData = RawData;
	    }
	    public String getRawData(){
	        return this.RawData;
	    }
	    public void setConfig(String Config){
	        this.Config = Config;
	    }
	    public String getConfig(){
	        return this.Config;
	    }
	    public void setModel(String Model){
	        this.Model = Model;
	    }
	    public String getModel(){
	        return this.Model;
	    }
	    public void setFreq(String Freq){
	        this.Freq = Freq;
	    }
	    public String getFreq(){
	        return this.Freq;
	    }
	    public void setRTC(String RTC){
	        this.RTC = RTC;
	    }
	    public String getRTC(){
	        return this.RTC;
	    }
	    public void setOnline(String Online){
	        this.Online = Online;
	    }
	    public String getOnline(){
	        return this.Online;
	    }
	    public void setRunStatus(String RunStatus){
	        this.RunStatus = RunStatus;
	    }
	    public String getRunStatus(){
	        return this.RunStatus;
	    }
	}
	
	public static class Down
	{
	    private String Model;

	    private String Model_FluidPVT;

	    private String Model_Reservoir;

	    private String Model_WellboreTrajectory;

	    private String Model_RodString;

	    private String Model_TubingString;

	    private String Model_Pump;

	    private String Model_TailtubingString;

	    private String Model_CasingString;

	    private String Model_PumpingUnit;

	    private String Model_SystemEfficiency;

	    private String Model_Production;

	    private String Model_FeatureDB;

	    private String Model_CalculationMethod;

	    private String Model_ManualIntervention;

	    private String Config;
	    
	    private String StartRPC;

	    private String StopRPC;

	    private String DogRestart;

	    private String Freq;

	    private String RTC;

	    private String Req;

	    private String A9;

	    private String AC;

	    public void setModel(String Model){
	        this.Model = Model;
	    }
	    public String getModel(){
	        return this.Model;
	    }
	    public void setModel_FluidPVT(String Model_FluidPVT){
	        this.Model_FluidPVT = Model_FluidPVT;
	    }
	    public String getModel_FluidPVT(){
	        return this.Model_FluidPVT;
	    }
	    public void setModel_Reservoir(String Model_Reservoir){
	        this.Model_Reservoir = Model_Reservoir;
	    }
	    public String getModel_Reservoir(){
	        return this.Model_Reservoir;
	    }
	    public void setModel_WellboreTrajectory(String Model_WellboreTrajectory){
	        this.Model_WellboreTrajectory = Model_WellboreTrajectory;
	    }
	    public String getModel_WellboreTrajectory(){
	        return this.Model_WellboreTrajectory;
	    }
	    public void setModel_RodString(String Model_RodString){
	        this.Model_RodString = Model_RodString;
	    }
	    public String getModel_RodString(){
	        return this.Model_RodString;
	    }
	    public void setModel_TubingString(String Model_TubingString){
	        this.Model_TubingString = Model_TubingString;
	    }
	    public String getModel_TubingString(){
	        return this.Model_TubingString;
	    }
	    public void setModel_Pump(String Model_Pump){
	        this.Model_Pump = Model_Pump;
	    }
	    public String getModel_Pump(){
	        return this.Model_Pump;
	    }
	    public void setModel_TailtubingString(String Model_TailtubingString){
	        this.Model_TailtubingString = Model_TailtubingString;
	    }
	    public String getModel_TailtubingString(){
	        return this.Model_TailtubingString;
	    }
	    public void setModel_CasingString(String Model_CasingString){
	        this.Model_CasingString = Model_CasingString;
	    }
	    public String getModel_CasingString(){
	        return this.Model_CasingString;
	    }
	    public void setModel_PumpingUnit(String Model_PumpingUnit){
	        this.Model_PumpingUnit = Model_PumpingUnit;
	    }
	    public String getModel_PumpingUnit(){
	        return this.Model_PumpingUnit;
	    }
	    public void setModel_SystemEfficiency(String Model_SystemEfficiency){
	        this.Model_SystemEfficiency = Model_SystemEfficiency;
	    }
	    public String getModel_SystemEfficiency(){
	        return this.Model_SystemEfficiency;
	    }
	    public void setModel_Production(String Model_Production){
	        this.Model_Production = Model_Production;
	    }
	    public String getModel_Production(){
	        return this.Model_Production;
	    }
	    public void setModel_FeatureDB(String Model_FeatureDB){
	        this.Model_FeatureDB = Model_FeatureDB;
	    }
	    public String getModel_FeatureDB(){
	        return this.Model_FeatureDB;
	    }
	    public void setModel_CalculationMethod(String Model_CalculationMethod){
	        this.Model_CalculationMethod = Model_CalculationMethod;
	    }
	    public String getModel_CalculationMethod(){
	        return this.Model_CalculationMethod;
	    }
	    public void setModel_ManualIntervention(String Model_ManualIntervention){
	        this.Model_ManualIntervention = Model_ManualIntervention;
	    }
	    public String getModel_ManualIntervention(){
	        return this.Model_ManualIntervention;
	    }
	    public void setConfig(String Config){
	        this.Config = Config;
	    }
	    public String getConfig(){
	        return this.Config;
	    }
	    public void setStopRPC(String StopRPC){
	        this.StopRPC = StopRPC;
	    }
	    public String getStopRPC(){
	        return this.StopRPC;
	    }
	    public void setDogRestart(String DogRestart){
	        this.DogRestart = DogRestart;
	    }
	    public String getDogRestart(){
	        return this.DogRestart;
	    }
	    public void setFreq(String Freq){
	        this.Freq = Freq;
	    }
	    public String getFreq(){
	        return this.Freq;
	    }
	    public void setRTC(String RTC){
	        this.RTC = RTC;
	    }
	    public String getRTC(){
	        return this.RTC;
	    }
	    public void setReq(String Req){
	        this.Req = Req;
	    }
	    public String getReq(){
	        return this.Req;
	    }
	    public void setA9(String A9){
	        this.A9 = A9;
	    }
	    public String getA9(){
	        return this.A9;
	    }
	    public void setAC(String AC){
	        this.AC = AC;
	    }
	    public String getAC(){
	        return this.AC;
	    }
		public String getStartRPC() {
			return StartRPC;
		}
		public void setStartRPC(String startRPC) {
			StartRPC = startRPC;
		}
	}

	public static class Topic
	{
	    private Up Up;

	    private Down Down;

	    public void setUp(Up Up){
	        this.Up = Up;
	    }
	    public Up getUp(){
	        return this.Up;
	    }
	    public void setDown(Down Down){
	        this.Down = Down;
	    }
	    public Down getDown(){
	        return this.Down;
	    }
	}

	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
}
