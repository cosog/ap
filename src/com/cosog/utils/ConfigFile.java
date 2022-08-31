package com.cosog.utils;

public class ConfigFile {
	
	private Ap ap;

    private Ac ac;

    private Ad ad;
    
    private Ad_rpc ad_rpc;

    public void setAp(Ap ap){
        this.ap = ap;
    }
    public Ap getAp(){
        return this.ap;
    }
    public void setAc(Ac ac){
        this.ac = ac;
    }
    public Ac getAc(){
        return this.ac;
    }
    public void setAd(Ad ad){
        this.ad = ad;
    }
    public Ad getAd(){
        return this.ad;
    }
	
	public static class Server
	{
	    private String url;

	    public void setUrl(String url){
	        this.url = url;
	    }
	    public String getUrl(){
	        return this.url;
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
	
	public static class Oem
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
	
	public static class SnedAccount
	{
	    private String account;

	    private String password;

	    private String smtpHost;

	    private int smtpPort;

	    public void setAccount(String account){
	        this.account = account;
	    }
	    public String getAccount(){
	        return this.account;
	    }
	    public void setPassword(String password){
	        this.password = password;
	    }
	    public String getPassword(){
	        return this.password;
	    }
	    public void setSmtpHost(String smtpHost){
	        this.smtpHost = smtpHost;
	    }
	    public String getSmtpHost(){
	        return this.smtpHost;
	    }
	    public void setSmtpPort(int smtpPort){
	        this.smtpPort = smtpPort;
	    }
	    public int getSmtpPort(){
	        return this.smtpPort;
	    }
	}
	
	public static class Email
	{
	    private SnedAccount snedAccount;

	    public void setSnedAccount(SnedAccount snedAccount){
	        this.snedAccount = snedAccount;
	    }
	    public SnedAccount getSnedAccount(){
	        return this.snedAccount;
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

	    private String productionUnit;

	    private boolean pcpHidden;

	    private boolean showLogo;

	    private boolean printLog;

	    private boolean simulateAcqEnable;
	    
	    private int sendCycle;
	    
	    private int timeDifference;

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
	    public void setProductionUnit(String productionUnit){
	        this.productionUnit = productionUnit;
	    }
	    public String getProductionUnit(){
	        return this.productionUnit;
	    }
	    public void setPcpHidden(boolean pcpHidden){
	        this.pcpHidden = pcpHidden;
	    }
	    public boolean getPcpHidden(){
	        return this.pcpHidden;
	    }
	    public void setShowLogo(boolean showLogo){
	        this.showLogo = showLogo;
	    }
	    public boolean getShowLogo(){
	        return this.showLogo;
	    }
	    public void setPrintLog(boolean printLog){
	        this.printLog = printLog;
	    }
	    public boolean getPrintLog(){
	        return this.printLog;
	    }
	    public void setSimulateAcqEnable(boolean simulateAcqEnable){
	        this.simulateAcqEnable = simulateAcqEnable;
	    }
	    public boolean getSimulateAcqEnable(){
	        return this.simulateAcqEnable;
	    }
		public int getTimeDifference() {
			return timeDifference;
		}
		public void setTimeDifference(int timeDifference) {
			this.timeDifference = timeDifference;
		}
		public int getSendCycle() {
			return sendCycle;
		}
		public void setSendCycle(int sendCycle) {
			this.sendCycle = sendCycle;
		}
	}
	
	public static class Ap
	{
	    private Server server;

	    private Datasource datasource;

	    private Oem oem;

	    private Email email;

	    private Others others;

	    public void setServer(Server server){
	        this.server = server;
	    }
	    public Server getServer(){
	        return this.server;
	    }
	    public void setDatasource(Datasource datasource){
	        this.datasource = datasource;
	    }
	    public Datasource getDatasource(){
	        return this.datasource;
	    }
	    public void setOem(Oem oem){
	        this.oem = oem;
	    }
	    public Oem getOem(){
	        return this.oem;
	    }
	    public void setEmail(Email email){
	        this.email = email;
	    }
	    public Email getEmail(){
	        return this.email;
	    }
	    public void setOthers(Others others){
	        this.others = others;
	    }
	    public Others getOthers(){
	        return this.others;
	    }
	}
	
	public static class AcProbe
	{
	    private String[] app;

	    private String[] mem;

	    private String[] disk;

	    private String[] host;

	    private String[] cpu;

	    public void setApp(String[] app){
	        this.app = app;
	    }
	    public String[] getApp(){
	        return this.app;
	    }
	    public void setMem(String[] mem){
	        this.mem = mem;
	    }
	    public String[] getMem(){
	        return this.mem;
	    }
	    public void setDisk(String[] disk){
	        this.disk = disk;
	    }
	    public String[] getDisk(){
	        return this.disk;
	    }
	    public void setHost(String[] host){
	        this.host = host;
	    }
	    public String[] getHost(){
	        return this.host;
	    }
	    public void setCpu(String[] cpu){
	        this.cpu = cpu;
	    }
	    public String[] getCpu(){
	        return this.cpu;
	    }
	}
	
	public static class Inversion
	{
	    private boolean enable;

	    private String[] url;

	    public void setEnable(boolean enable){
	        this.enable = enable;
	    }
	    public boolean getEnable(){
	        return this.enable;
	    }
	    public void setUrl(String[] url){
	        this.url = url;
	    }
	    public String[] getUrl(){
	        return this.url;
	    }
	}
	
	public static class ESDiagram
	{
	    private Inversion inversion;

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
	    private String[] wellboreTrajectory;

	    private String[] fa2fs;

	    public void setWellboreTrajectory(String[] wellboreTrajectory){
	        this.wellboreTrajectory = wellboreTrajectory;
	    }
	    public String[] getWellboreTrajectory(){
	        return this.wellboreTrajectory;
	    }
	    public void setFa2fs(String[] fa2fs){
	        this.fa2fs = fa2fs;
	    }
	    public String[] getFa2fs(){
	        return this.fa2fs;
	    }
	}
	
	public static class Ac
	{
	    private AcProbe probe;

	    private String[] FESDiagram;

	    private ESDiagram ESDiagram;

	    private String[] pcpProduction;

	    private String[] communication;

	    private String[] run;

	    private String[] energy;

	    private TotalCalculation totalCalculation;

	    private Plugin plugin;

	    public void setProbe(AcProbe probe){
	        this.probe = probe;
	    }
	    public AcProbe getProbe(){
	        return this.probe;
	    }
	    public void setFESDiagram(String[] FESDiagram){
	        this.FESDiagram = FESDiagram;
	    }
	    public String[] getFESDiagram(){
	        return this.FESDiagram;
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
	    public void setPlugin(Plugin plugin){
	        this.plugin = plugin;
	    }
	    public Plugin getPlugin(){
	        return this.plugin;
	    }
	}
	
	public static class AdProbe
	{
	    private String init;

	    private String app;

	    private String cpu;

	    private String mem;

	    private String disk;

	    private String host;

	    public void setInit(String init){
	        this.init = init;
	    }
	    public String getInit(){
	        return this.init;
	    }
	    public void setApp(String app){
	        this.app = app;
	    }
	    public String getApp(){
	        return this.app;
	    }
	    public void setCpu(String cpu){
	        this.cpu = cpu;
	    }
	    public String getCpu(){
	        return this.cpu;
	    }
	    public void setMem(String mem){
	        this.mem = mem;
	    }
	    public String getMem(){
	        return this.mem;
	    }
	    public void setDisk(String disk){
	        this.disk = disk;
	    }
	    public String getDisk(){
	        return this.disk;
	    }
	    public void setHost(String host){
	        this.host = host;
	    }
	    public String getHost(){
	        return this.host;
	    }
	}
	
	public static class Ad
	{
	    private String server;

	    private String protocol;

	    private String instance;

	    private String id;
	    
	    private String ipPort;

	    private String SMS;

	    private String readAddr;
	    
	    private String readAddr_ipPort;

	    private String writeAddr;
	    
	    private String writeAddr_ipPort;

	    private String writeSMS;

	    private AdProbe probe;

	    public void setServer(String server){
	        this.server = server;
	    }
	    public String getServer(){
	        return this.server;
	    }
	    public void setProtocol(String protocol){
	        this.protocol = protocol;
	    }
	    public String getProtocol(){
	        return this.protocol;
	    }
	    public void setInstance(String instance){
	        this.instance = instance;
	    }
	    public String getInstance(){
	        return this.instance;
	    }
	    public void setId(String id){
	        this.id = id;
	    }
	    public String getId(){
	        return this.id;
	    }
	    public void setSMS(String SMS){
	        this.SMS = SMS;
	    }
	    public String getSMS(){
	        return this.SMS;
	    }
	    public void setReadAddr(String readAddr){
	        this.readAddr = readAddr;
	    }
	    public String getReadAddr(){
	        return this.readAddr;
	    }
	    public void setWriteAddr(String writeAddr){
	        this.writeAddr = writeAddr;
	    }
	    public String getWriteAddr(){
	        return this.writeAddr;
	    }
	    public void setWriteSMS(String writeSMS){
	        this.writeSMS = writeSMS;
	    }
	    public String getWriteSMS(){
	        return this.writeSMS;
	    }
	    public void setProbe(AdProbe probe){
	        this.probe = probe;
	    }
	    public AdProbe getProbe(){
	        return this.probe;
	    }
		public String getIpPort() {
			return ipPort;
		}
		public void setIpPort(String ipPort) {
			this.ipPort = ipPort;
		}
		public String getReadAddr_ipPort() {
			return readAddr_ipPort;
		}
		public void setReadAddr_ipPort(String readAddr_ipPort) {
			this.readAddr_ipPort = readAddr_ipPort;
		}
		public String getWriteAddr_ipPort() {
			return writeAddr_ipPort;
		}
		public void setWriteAddr_ipPort(String writeAddr_ipPort) {
			this.writeAddr_ipPort = writeAddr_ipPort;
		}
	}
	
	public static class Ad_rpc
	{
	    private String server;

	    private String readTopicReq;

	    private String writeTopicModel;

	    private String writeTopicConf;
	    
	    private String writeTopicRtc;
	    
	    private String writeTopicDog;
	    
	    private String writeTopicStopRpc;

	    private AdProbe probe;

		public String getServer() {
			return server;
		}

		public void setServer(String server) {
			this.server = server;
		}

		public String getReadTopicReq() {
			return readTopicReq;
		}

		public void setReadTopicReq(String readTopicReq) {
			this.readTopicReq = readTopicReq;
		}

		public String getWriteTopicModel() {
			return writeTopicModel;
		}

		public void setWriteTopicModel(String writeTopicModel) {
			this.writeTopicModel = writeTopicModel;
		}

		public String getWriteTopicConf() {
			return writeTopicConf;
		}

		public void setWriteTopicConf(String writeTopicConf) {
			this.writeTopicConf = writeTopicConf;
		}

		public AdProbe getProbe() {
			return probe;
		}

		public void setProbe(AdProbe probe) {
			this.probe = probe;
		}

		public String getWriteTopicRtc() {
			return writeTopicRtc;
		}

		public void setWriteTopicRtc(String writeTopicRtc) {
			this.writeTopicRtc = writeTopicRtc;
		}

		public String getWriteTopicDog() {
			return writeTopicDog;
		}

		public void setWriteTopicDog(String writeTopicDog) {
			this.writeTopicDog = writeTopicDog;
		}

		public String getWriteTopicStopRpc() {
			return writeTopicStopRpc;
		}

		public void setWriteTopicStopRpc(String writeTopicStopRpc) {
			this.writeTopicStopRpc = writeTopicStopRpc;
		}
	}

	public Ad_rpc getAd_rpc() {
		return ad_rpc;
	}
	public void setAd_rpc(Ad_rpc ad_rpc) {
		this.ad_rpc = ad_rpc;
	}
}
