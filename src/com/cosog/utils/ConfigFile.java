package com.cosog.utils;

public class ConfigFile {
	
	private Ap ap;

    private Ac ac;

    private Ad ad;

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
	
	public static class RedisConfig{
		private String addr;
		
		private int port;
		
		private String password;
		
		private int maxActive;
		
		private int maxIdle;
		
		private int maxWait;
		
		private int timeOut;
		
		private int maxMemory;
		
		private String maxMemoryPolicy;
		
		public String getAddr() {
			return addr;
		}
		public void setAddr(String addr) {
			this.addr = addr;
		}
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public int getMaxActive() {
			return maxActive;
		}
		public void setMaxActive(int maxActive) {
			this.maxActive = maxActive;
		}
		public int getMaxIdle() {
			return maxIdle;
		}
		public void setMaxIdle(int maxIdle) {
			this.maxIdle = maxIdle;
		}
		public int getMaxWait() {
			return maxWait;
		}
		public void setMaxWait(int maxWait) {
			this.maxWait = maxWait;
		}
		public int getTimeOut() {
			return timeOut;
		}
		public void setTimeOut(int timeOut) {
			this.timeOut = timeOut;
		}
		public int getMaxMemory() {
			return maxMemory;
		}
		public void setMaxMemory(int maxMemory) {
			this.maxMemory = maxMemory;
		}
		public String getMaxMemoryPolicy() {
			return maxMemoryPolicy;
		}
		public void setMaxMemoryPolicy(String maxMemoryPolicy) {
			this.maxMemoryPolicy = maxMemoryPolicy;
		}
	}
	
	public static class ThreadPoolConfig
	{

	    private int corePoolSize;

	    private int maximumPoolSize;

	    private int keepAliveTime;

	    private int wattingCount;

		public int getCorePoolSize() {
			return corePoolSize;
		}

		public void setCorePoolSize(int corePoolSize) {
			this.corePoolSize = corePoolSize;
		}

		public int getMaximumPoolSize() {
			return maximumPoolSize;
		}

		public void setMaximumPoolSize(int maximumPoolSize) {
			this.maximumPoolSize = maximumPoolSize;
		}

		public int getKeepAliveTime() {
			return keepAliveTime;
		}

		public void setKeepAliveTime(int keepAliveTime) {
			this.keepAliveTime = keepAliveTime;
		}

		public int getWattingCount() {
			return wattingCount;
		}

		public void setWattingCount(int wattingCount) {
			this.wattingCount = wattingCount;
		}
	}
	
	public static class ThreadPool
	{
		ThreadPoolConfig initIdAndIpPort;
		ThreadPoolConfig dataSynchronization;
		ThreadPoolConfig calculateMaintaining;
		ThreadPoolConfig totalCalculateMaintaining;
		ThreadPoolConfig timingTotalCalculate;
		ThreadPoolConfig outerDatabaseSync;
		ThreadPoolConfig dataWriteBack;
		public ThreadPoolConfig getInitIdAndIpPort() {
			return initIdAndIpPort;
		}
		public void setInitIdAndIpPort(ThreadPoolConfig initIdAndIpPort) {
			this.initIdAndIpPort = initIdAndIpPort;
		}
		public ThreadPoolConfig getDataSynchronization() {
			return dataSynchronization;
		}
		public void setDataSynchronization(ThreadPoolConfig dataSynchronization) {
			this.dataSynchronization = dataSynchronization;
		}
		public ThreadPoolConfig getCalculateMaintaining() {
			return calculateMaintaining;
		}
		public void setCalculateMaintaining(ThreadPoolConfig calculateMaintaining) {
			this.calculateMaintaining = calculateMaintaining;
		}
		public ThreadPoolConfig getTotalCalculateMaintaining() {
			return totalCalculateMaintaining;
		}
		public void setTotalCalculateMaintaining(ThreadPoolConfig totalCalculateMaintaining) {
			this.totalCalculateMaintaining = totalCalculateMaintaining;
		}
		public ThreadPoolConfig getOuterDatabaseSync() {
			return outerDatabaseSync;
		}
		public void setOuterDatabaseSync(ThreadPoolConfig outerDatabaseSync) {
			this.outerDatabaseSync = outerDatabaseSync;
		}
		public ThreadPoolConfig getDataWriteBack() {
			return dataWriteBack;
		}
		public void setDataWriteBack(ThreadPoolConfig dataWriteBack) {
			this.dataWriteBack = dataWriteBack;
		}
		public ThreadPoolConfig getTimingTotalCalculate() {
			return timingTotalCalculate;
		}
		public void setTimingTotalCalculate(ThreadPoolConfig timingTotalCalculate) {
			this.timingTotalCalculate = timingTotalCalculate;
		}
	}
	
	public static class Ap
	{
	    private Datasource datasource;
	    
	    private RedisConfig redis;
	    
	    private ThreadPool threadPool;
	    
	    private String oemConfigFile;

	    private OEMConfigFile.Oem oem;
	    
	    private OEMConfigFile.Report report;
	    
	    private OEMConfigFile.DatabaseMaintenance databaseMaintenance;

	    private OEMConfigFile.Email email;
	    
	    private OEMConfigFile.ModuleContentConfig moduleContent;
	    
	    private OEMConfigFile.Others others;

	    public void setDatasource(Datasource datasource){
	        this.datasource = datasource;
	    }
	    public Datasource getDatasource(){
	        return this.datasource;
	    }
	    public void setOem(OEMConfigFile.Oem oem){
	        this.oem = oem;
	    }
	    public OEMConfigFile.Oem getOem(){
	        return this.oem;
	    }
	    public void setEmail(OEMConfigFile.Email email){
	        this.email = email;
	    }
	    public OEMConfigFile.Email getEmail(){
	        return this.email;
	    }
	    public void setOthers(OEMConfigFile.Others others){
	        this.others = others;
	    }
	    public OEMConfigFile.Others getOthers(){
	        return this.others;
	    }
		public RedisConfig getRedis() {
			return redis;
		}
		public void setRedis(RedisConfig redis) {
			this.redis = redis;
		}
		public ThreadPool getThreadPool() {
			return threadPool;
		}
		public void setThreadPool(ThreadPool threadPool) {
			this.threadPool = threadPool;
		}
		public OEMConfigFile.Report getReport() {
			return report;
		}
		public void setReport(OEMConfigFile.Report report) {
			this.report = report;
		}
		public String getOemConfigFile() {
			return oemConfigFile;
		}
		public void setOemConfigFile(String oemConfigFile) {
			this.oemConfigFile = oemConfigFile;
		}
		public OEMConfigFile.DatabaseMaintenance getDatabaseMaintenance() {
			return databaseMaintenance;
		}
		public void setDatabaseMaintenance(OEMConfigFile.DatabaseMaintenance databaseMaintenance) {
			this.databaseMaintenance = databaseMaintenance;
		}
		public OEMConfigFile.ModuleContentConfig getModuleContent() {
			return moduleContent;
		}
		public void setModuleContent(OEMConfigFile.ModuleContentConfig moduleContent) {
			this.moduleContent = moduleContent;
		}
	}
	
	public static class AcProbe
	{
	    private String app;

	    private String mem;

	    private String disk;

	    private String host;

	    private String cpu;

	    public void setApp(String app){
	        this.app = app;
	    }
	    public String getApp(){
	        return this.app;
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
	    public void setCpu(String cpu){
	        this.cpu = cpu;
	    }
	    public String getCpu(){
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
	
	public static class Ac
	{
		
		private AcProbe probe;

	    private String FESDiagram;

	    private String RPM;

	    private String communication;

	    private String run;

	    private String energy;

	    private String totalCalculation;

	    public void setProbe(AcProbe probe){
	        this.probe = probe;
	    }
	    public AcProbe getProbe(){
	        return this.probe;
	    }
	    public void setFESDiagram(String FESDiagram){
	        this.FESDiagram = FESDiagram;
	    }
	    public String getFESDiagram(){
	        return this.FESDiagram;
	    }
	    public void setCommunication(String communication){
	        this.communication = communication;
	    }
	    public String getCommunication(){
	        return this.communication;
	    }
	    public void setRun(String run){
	        this.run = run;
	    }
	    public String getRun(){
	        return this.run;
	    }
	    public void setEnergy(String energy){
	        this.energy = energy;
	    }
	    public String getEnergy(){
	        return this.energy;
	    }
		public String getRPM() {
			return RPM;
		}
		public void setRPM(String rPM) {
			RPM = rPM;
		}
		public String getTotalCalculation() {
			return totalCalculation;
		}
		public void setTotalCalculation(String totalCalculation) {
			this.totalCalculation = totalCalculation;
		}
	}
	
	public static class AdProbe
	{
	    private String init;

	    private String app;
	    
	    private String online;

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
		public String getOnline() {
			return online;
		}
		public void setOnline(String online) {
			this.online = online;
		}
	}
	
	public static class Ad
	{
		
		private Ad_Init init;

	    private Ad_rw rw;

	    private AdProbe probe;
	    
	    private Ad_srp srp;
	    
	    public void setProbe(AdProbe probe){
	        this.probe = probe;
	    }
	    public AdProbe getProbe(){
	        return this.probe;
	    }
		public Ad_rw getRw() {
			return rw;
		}
		public void setRw(Ad_rw rw) {
			this.rw = rw;
		}
		public Ad_Init getInit() {
			return init;
		}
		public void setInit(Ad_Init init) {
			this.init = init;
		}
		public Ad_srp getSrp() {
			return srp;
		}
		public void setSrp(Ad_srp srp) {
			this.srp = srp;
		}
	}
	
	public static class Ad_ServerInitContent{
		private String ip;
		
		private int port;
		
		private String projectName;

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}
	}
	
	public static class InitServerContent{
		private String idOnlineStatusPushURL;
		private String idAcqGroupDataPushURL;
		private String ipPortOnlineStatusPushURL;
		private String ipPortAcqGroupDataPushURL;
		public String getIdOnlineStatusPushURL() {
			return idOnlineStatusPushURL;
		}
		public void setIdOnlineStatusPushURL(String idOnlineStatusPushURL) {
			this.idOnlineStatusPushURL = idOnlineStatusPushURL;
		}
		public String getIdAcqGroupDataPushURL() {
			return idAcqGroupDataPushURL;
		}
		public void setIdAcqGroupDataPushURL(String idAcqGroupDataPushURL) {
			this.idAcqGroupDataPushURL = idAcqGroupDataPushURL;
		}
		public String getIpPortOnlineStatusPushURL() {
			return ipPortOnlineStatusPushURL;
		}
		public void setIpPortOnlineStatusPushURL(String ipPortOnlineStatusPushURL) {
			this.ipPortOnlineStatusPushURL = ipPortOnlineStatusPushURL;
		}
		public String getIpPortAcqGroupDataPushURL() {
			return ipPortAcqGroupDataPushURL;
		}
		public void setIpPortAcqGroupDataPushURL(String ipPortAcqGroupDataPushURL) {
			this.ipPortAcqGroupDataPushURL = ipPortAcqGroupDataPushURL;
		}
	}
	
	public static class Ad_InitServer{
		private String url;
		
		private InitServerContent content;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public InitServerContent getContent() {
			return content;
		}

		public void setContent(InitServerContent content) {
			this.content = content;
		}
		
	}
	
	public static class Ad_Init{
		private Ad_InitServer server;
		
		private String protocol;
		
		private String instance;
		
		private String id;
		
		private String ipPort;
		
		private String SMS;

		public Ad_InitServer getServer() {
			return server;
		}

		public void setServer(Ad_InitServer server) {
			this.server = server;
		}

		public String getProtocol() {
			return protocol;
		}

		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}

		public String getInstance() {
			return instance;
		}

		public void setInstance(String instance) {
			this.instance = instance;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getIpPort() {
			return ipPort;
		}

		public void setIpPort(String ipPort) {
			this.ipPort = ipPort;
		}

		public String getSMS() {
			return SMS;
		}

		public void setSMS(String sMS) {
			SMS = sMS;
		}
	}
	
	public static class Ad_rw{
		private String readAddr;
		
		private String readAddr_ipPort;
		
		private String writeAddr;
		
		private String writeAddr_ipPort;
		
		private String writeSMS;

		public String getReadAddr() {
			return readAddr;
		}

		public void setReadAddr(String readAddr) {
			this.readAddr = readAddr;
		}

		public String getReadAddr_ipPort() {
			return readAddr_ipPort;
		}

		public void setReadAddr_ipPort(String readAddr_ipPort) {
			this.readAddr_ipPort = readAddr_ipPort;
		}

		public String getWriteAddr() {
			return writeAddr;
		}

		public void setWriteAddr(String writeAddr) {
			this.writeAddr = writeAddr;
		}

		public String getWriteAddr_ipPort() {
			return writeAddr_ipPort;
		}

		public void setWriteAddr_ipPort(String writeAddr_ipPort) {
			this.writeAddr_ipPort = writeAddr_ipPort;
		}

		public String getWriteSMS() {
			return writeSMS;
		}

		public void setWriteSMS(String writeSMS) {
			this.writeSMS = writeSMS;
		}
	}
	
	public static class Ad_srp
	{
	    private String readTopicReq;

	    private String writeTopicModel;

	    private String writeTopicConf;
	    
	    private String writeTopicRtc;
	    
	    private String writeTopicDog;
	    
	    private String writeTopicStopRpc;

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
}
