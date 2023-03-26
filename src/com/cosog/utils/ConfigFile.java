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
	}
	
	public static class Oem
	{
	    private String title;

	    private String profile;

	    private String copy;

	    private String linkaddress;

	    private String linkshow;
	    
	    private String logo;
	    
	    private String favicon;
	    
	    private String loginBackgroundImage;
	    
	    private String loginCSS;
	    
	    private String bannerCSS;
	    
	    private String helpButtonIcon;
	    
	    private String exitButtonIcon;

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
		public String getLoginCSS() {
			return loginCSS;
		}
		public void setLoginCSS(String loginCSS) {
			this.loginCSS = loginCSS;
		}
		public String getBannerCSS() {
			return bannerCSS;
		}
		public void setBannerCSS(String bannerCSS) {
			this.bannerCSS = bannerCSS;
		}
		public String getLoginBackgroundImage() {
			return loginBackgroundImage;
		}
		public void setLoginBackgroundImage(String loginBackgroundImage) {
			this.loginBackgroundImage = loginBackgroundImage;
		}
		public String getFavicon() {
			return favicon;
		}
		public void setFavicon(String favicon) {
			this.favicon = favicon;
		}
		public String getLogo() {
			return logo;
		}
		public void setLogo(String logo) {
			this.logo = logo;
		}
		public String getHelpButtonIcon() {
			return helpButtonIcon;
		}
		public void setHelpButtonIcon(String helpButtonIcon) {
			this.helpButtonIcon = helpButtonIcon;
		}
		public String getExitButtonIcon() {
			return exitButtonIcon;
		}
		public void setExitButtonIcon(String exitButtonIcon) {
			this.exitButtonIcon = exitButtonIcon;
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
	
	public static class Video{
		
		private String appKey;
		
		private String secret;

		public String getAppKey() {
			return appKey;
		}

		public void setAppKey(String appKey) {
			this.appKey = appKey;
		}

		public String getSecret() {
			return secret;
		}

		public void setSecret(String secret) {
			this.secret = secret;
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
	    
	    private int vacuateThreshold;

	    private String productionUnit;

	    private boolean pcpHidden;
	    
	    private boolean onlyMonitor;

	    private boolean showLogo;

	    private boolean printLog;
	    
	    private int exportLimit;

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
		public int getExportLimit() {
			return exportLimit;
		}
		public void setExportLimit(int exportLimit) {
			this.exportLimit = exportLimit;
		}
		public int getVacuateThreshold() {
			return vacuateThreshold;
		}
		public void setVacuateThreshold(int vacuateThreshold) {
			this.vacuateThreshold = vacuateThreshold;
		}
		public boolean isOnlyMonitor() {
			return onlyMonitor;
		}
		public void setOnlyMonitor(boolean onlyMonitor) {
			this.onlyMonitor = onlyMonitor;
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
	}
	
	public static class Ap
	{
	    private Datasource datasource;
	    
	    private RedisConfig redis;

	    private Oem oem;

	    private Email email;
	    
	    private Video video;
	    
	    private ThreadPool threadPool;

	    private Others others;

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
		public Video getVideo() {
			return video;
		}
		public void setVideo(Video video) {
			this.video = video;
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
		private String ip;
		
		private int port;
		
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
		private String ip;
		
		private int port;
		
		private Ad_Init init;

	    private Ad_rw rw;

	    private AdProbe probe;
	    
	    private Ad_rpc rpc;
	    
	    public void setProbe(AdProbe probe){
	        this.probe = probe;
	    }
	    public AdProbe getProbe(){
	        return this.probe;
	    }
		public Ad_rpc getRpc() {
			return rpc;
		}
		public void setRpc(Ad_rpc rpc) {
			this.rpc = rpc;
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
	
	public static class Ad_InitServer{
		private String url;
		
		private Ad_ServerInitContent[] content;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Ad_ServerInitContent[] getContent() {
			return content;
		}

		public void setContent(Ad_ServerInitContent[] content) {
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
	
	public static class Ad_rpc
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
