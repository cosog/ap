package com.gao.utils;

import java.util.List;

public class ConfigFile {
	
	private Server server;

    private Spring spring;

    private List<Datasource> dockDataSource;

    private AgileCalculate agileCalculate;

    private Mqtt mqtt;
    
    private Kafka kafka;

    private ViewInformation viewInformation;

    private Others others;

    public void setServer(Server server){
        this.server = server;
    }
    public Server getServer(){
        return this.server;
    }
    public void setSpring(Spring spring){
        this.spring = spring;
    }
    public Spring getSpring(){
        return this.spring;
    }
    public void setDockDataSource(List<Datasource> dockDataSource){
        this.dockDataSource = dockDataSource;
    }
    public List<Datasource> getDockDataSource(){
        return this.dockDataSource;
    }
    public void setAgileCalculate(AgileCalculate agileCalculate){
        this.agileCalculate = agileCalculate;
    }
    public AgileCalculate getAgileCalculate(){
        return this.agileCalculate;
    }
    public void setMqtt(Mqtt mqtt){
        this.mqtt = mqtt;
    }
    public Mqtt getMqtt(){
        return this.mqtt;
    }
	public Kafka getKafka() {
		return kafka;
	}
	public void setKafka(Kafka kafka) {
		this.kafka = kafka;
	}
    public void setViewInformation(ViewInformation viewInformation){
        this.viewInformation = viewInformation;
    }
    public ViewInformation getViewInformation(){
        return this.viewInformation;
    }
    public void setOthers(Others others){
        this.others = others;
    }
    public Others getOthers(){
        return this.others;
    }
	
	public static class Server
	{
	    private String accessPath;
	    
	    public void setAccessPath(String accessPath){
	        this.accessPath = accessPath;
	    }
	    public String getAccessPath(){
	        return this.accessPath;
	    }
	}
	
	public static class Datasource
	{
	    private String driverUrl;

	    private String driver;

	    private String dialect;

	    private String user;

	    private String password;

	    public void setDriverUrl(String driverUrl){
	        this.driverUrl = driverUrl;
	    }
	    public String getDriverUrl(){
	        return this.driverUrl;
	    }
	    public void setDriver(String driver){
	        this.driver = driver;
	    }
	    public String getDriver(){
	        return this.driver;
	    }
	    public void setDialect(String dialect){
	        this.dialect = dialect;
	    }
	    public String getDialect(){
	        return this.dialect;
	    }
	    public void setUser(String user){
	        this.user = user;
	    }
	    public String getUser(){
	        return this.user;
	    }
	    public void setPassword(String password){
	        this.password = password;
	    }
	    public String getPassword(){
	        return this.password;
	    }
	}
	
	public static class Spring
	{
	    private Datasource datasource;

	    public void setDatasource(Datasource datasource){
	        this.datasource = datasource;
	    }
	    public Datasource getDatasource(){
	        return this.datasource;
	    }
	}
	
	public static class InversionUrl
	{
	    private String[] motorauto;

	    private String[] motorsemiauto;

	    private String[] beam;

	    public void setMotorauto(String[] motorauto){
	        this.motorauto = motorauto;
	    }
	    public String[] getMotorauto(){
	        return this.motorauto;
	    }
	    public void setMotorsemiauto(String[] motorsemiauto){
	        this.motorsemiauto = motorsemiauto;
	    }
	    public String[] getMotorsemiauto(){
	        return this.motorsemiauto;
	    }
	    public void setBeam(String[] beam){
	        this.beam = beam;
	    }
	    public String[] getBeam(){
	        return this.beam;
	    }
	}
	
	public static class Inversion
	{
	    private boolean inversionSwitch;

	    private InversionUrl url;

	    private String timerCorrectionStart;

	    private String imerCorrectionEnd;

	    private int timerCorrectionLimit;

	    public void setInversionSwitch(boolean inversionSwitch){
	        this.inversionSwitch = inversionSwitch;
	    }
	    public boolean getInversionSwitch(){
	        return this.inversionSwitch;
	    }
	    public void setUrl(InversionUrl url){
	        this.url = url;
	    }
	    public InversionUrl getUrl(){
	        return this.url;
	    }
	    public void setTimerCorrectionStart(String timerCorrectionStart){
	        this.timerCorrectionStart = timerCorrectionStart;
	    }
	    public String getTimerCorrectionStart(){
	        return this.timerCorrectionStart;
	    }
	    public void setImerCorrectionEnd(String imerCorrectionEnd){
	        this.imerCorrectionEnd = imerCorrectionEnd;
	    }
	    public String getImerCorrectionEnd(){
	        return this.imerCorrectionEnd;
	    }
	    public void setTimerCorrectionLimit(int timerCorrectionLimit){
	        this.timerCorrectionLimit = timerCorrectionLimit;
	    }
	    public int getTimerCorrectionLimit(){
	        return this.timerCorrectionLimit;
	    }
	}

	public static class ESDiagram
	{
	    private String[] balance;

	    private Inversion inversion;

	    public void setBalance(String[] balance){
	        this.balance = balance;
	    }
	    public String[] getBalance(){
	        return this.balance;
	    }
	    public void setInversion(Inversion inversion){
	        this.inversion = inversion;
	    }
	    public Inversion getInversion(){
	        return this.inversion;
	    }
	}
	
	public static class TotalCalculation
	{
	    private String[] well;

	    public void setWell(String[] well){
	        this.well = well;
	    }
	    public String[] getWell(){
	        return this.well;
	    }
	}
	
	public static class Plugin
	{
		private String wellboreTrajectory;
		
		private String fa2fs;
		
		public String getWellboreTrajectory() {
			return wellboreTrajectory;
		}
		
		public void setWellboreTrajectory(String wellboreTrajectory) {
			this.wellboreTrajectory = wellboreTrajectory;
		}
		
		public String getFa2fs() {
			return fa2fs;
		}
		
		public void setFa2fs(String fa2fs) {
			this.fa2fs = fa2fs;
		}
	}
	
	public static class AgileCalculate
	{
	    private String[] FESDiagram;

	    private String[] FSDiagram;

	    private ESDiagram ESDiagram;

	    private String[] pcpProduction;

	    private String[] communication;

	    private String[] run;

	    private String[] energy;

	    private TotalCalculation totalCalculation;
	    
	    private Plugin plugin;

	    public Plugin getPlugin() {
			return plugin;
		}
		public void setPlugin(Plugin plugin) {
			this.plugin = plugin;
		}
		public void setFESDiagram(String[] FESDiagram){
	        this.FESDiagram = FESDiagram;
	    }
	    public String[] getFESDiagram(){
	        return this.FESDiagram;
	    }
	    public void setFSDiagram(String[] FSDiagram){
	        this.FSDiagram = FSDiagram;
	    }
	    public String[] getFSDiagram(){
	        return this.FSDiagram;
	    }
	    public void setESDiagram(ESDiagram ESDiagram){
	        this.ESDiagram = ESDiagram;
	    }
	    public ESDiagram getESDiagram(){
	        return this.ESDiagram;
	    }
	    public void setPcpProduction(String[] pcpProduction){
	        this.pcpProduction = pcpProduction;
	    }
	    public String[] getPcpProduction(){
	        return this.pcpProduction;
	    }
	    public void setCommunication(String[] communication){
	        this.communication = communication;
	    }
	    public String[] getCommunication(){
	        return this.communication;
	    }
	    public void setRun(String[] run){
	        this.run = run;
	    }
	    public String[] getRun(){
	        return this.run;
	    }
	    public void setEnergy(String[] energy){
	        this.energy = energy;
	    }
	    public String[] getEnergy(){
	        return this.energy;
	    }
	    public void setTotalCalculation(TotalCalculation totalCalculation){
	        this.totalCalculation = totalCalculation;
	    }
	    public TotalCalculation getTotalCalculation(){
	        return this.totalCalculation;
	    }
	}

	public static class Mqtt
	{
	    private String server;

	    public void setServer(String server){
	        this.server = server;
	    }
	    public String getServer(){
	        return this.server;
	    }
	}
	
	public static class Kafka
	{
	    private String server;

	    public void setServer(String server){
	        this.server = server;
	    }
	    public String getServer(){
	        return this.server;
	    }
	}
	
	public static class ViewInformation
	{
	    private String title;

	    private String profile;

	    private String copy;

	    private String linkaddress;

	    private String linkshow;

	    public void setTitle(String title){
	        this.title = title;
	    }
	    public String getTitle(){
	        return this.title;
	    }
	    public void setProfile(String profile){
	        this.profile = profile;
	    }
	    public String getProfile(){
	        return this.profile;
	    }
	    public void setCopy(String copy){
	        this.copy = copy;
	    }
	    public String getCopy(){
	        return this.copy;
	    }
	    public void setLinkaddress(String linkaddress){
	        this.linkaddress = linkaddress;
	    }
	    public String getLinkaddress(){
	        return this.linkaddress;
	    }
	    public void setLinkshow(String linkshow){
	        this.linkshow = linkshow;
	    }
	    public String getLinkshow(){
	        return this.linkshow;
	    }
	}
	
	public static class Others
	{
	    private boolean cache;

	    private String language;

	    private int pageSize;

	    private boolean syncOrAsync;

	    private boolean expandedAll;

	    private int defaultComboxSize;

	    private int defaultGraghSize;
	    
	    private int productionUnit;
	    
	    private boolean pcp;

	    public boolean isPcp() {
			return pcp;
		}
		public void setPcp(boolean pcp) {
			this.pcp = pcp;
		}
		private String serialnumber;

	    public void setCache(boolean cache){
	        this.cache = cache;
	    }
	    public boolean getCache(){
	        return this.cache;
	    }
	    public void setLanguage(String language){
	        this.language = language;
	    }
	    public String getLanguage(){
	        return this.language;
	    }
	    public void setPageSize(int pageSize){
	        this.pageSize = pageSize;
	    }
	    public int getPageSize(){
	        return this.pageSize;
	    }
	    public void setSyncOrAsync(boolean syncOrAsync){
	        this.syncOrAsync = syncOrAsync;
	    }
	    public boolean getSyncOrAsync(){
	        return this.syncOrAsync;
	    }
	    public void setExpandedAll(boolean expandedAll){
	        this.expandedAll = expandedAll;
	    }
	    public boolean getExpandedAll(){
	        return this.expandedAll;
	    }
	    public void setDefaultComboxSize(int defaultComboxSize){
	        this.defaultComboxSize = defaultComboxSize;
	    }
	    public int getDefaultComboxSize(){
	        return this.defaultComboxSize;
	    }
	    public void setDefaultGraghSize(int defaultGraghSize){
	        this.defaultGraghSize = defaultGraghSize;
	    }
	    public int getDefaultGraghSize(){
	        return this.defaultGraghSize;
	    }
	    public void setSerialnumber(String serialnumber){
	        this.serialnumber = serialnumber;
	    }
	    public String getSerialnumber(){
	        return this.serialnumber;
	    }
		public int getProductionUnit() {
			return productionUnit;
		}
		public void setProductionUnit(int productionUnit) {
			this.productionUnit = productionUnit;
		}
	}
}
