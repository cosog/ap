package com.cosog.utils;

import java.util.List;

public class OEMConfigFile {
	private Oem oem;
	
	private Report report;
	
	private DatabaseMaintenance databaseMaintenance;
	
	private Email email;
	
	private Others others;
	
	public Oem getOem() {
		return oem;
	}
	
	public void setOem(Oem oem) {
		this.oem = oem;
	}
	
	public Report getReport() {
		return report;
	}
	
	public void setReport(Report report) {
		this.report = report;
	}

	public DatabaseMaintenance getDatabaseMaintenance() {
		return databaseMaintenance;
	}

	public void setDatabaseMaintenance(DatabaseMaintenance databaseMaintenance) {
		this.databaseMaintenance = databaseMaintenance;
	}
	
	public Email getEmail() {
		return email;
	}
	
	public void setEmail(Email email) {
		this.email = email;
	}
	
	public Others getOthers() {
		return others;
	}
	
	public void setOthers(Others others) {
		this.others = others;
	}
	
	public static class Oem
	{
	    
	    private String languageResourcePath;
	    
	    private String logo;
	    
	    private String favicon;
	    
	    private String loginBackgroundImage;
	    
	    private String loginCSS;
	    
	    private String bannerCSS;
	    
	    private String helpButtonIcon;
	    
	    private String exitButtonIcon;
	    
	    private String zoomInButtonIcon;
	    
	    private String zoomoutButtonIcon;
	    
	    private String staticResourceTimestamp;
	    
	    private String helpDocument;
	    
	    private String helpDocumentTimestamp;

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
		public String getStaticResourceTimestamp() {
			return staticResourceTimestamp;
		}
		public void setStaticResourceTimestamp(String staticResourceTimestamp) {
			this.staticResourceTimestamp = staticResourceTimestamp;
		}
		public String getHelpDocument() {
			return helpDocument;
		}
		public void setHelpDocument(String helpDocument) {
			this.helpDocument = helpDocument;
		}
		public String getHelpDocumentTimestamp() {
			return helpDocumentTimestamp;
		}
		public void setHelpDocumentTimestamp(String helpDocumentTimestamp) {
			this.helpDocumentTimestamp = helpDocumentTimestamp;
		}
		public String getZoomInButtonIcon() {
			return zoomInButtonIcon;
		}
		public void setZoomInButtonIcon(String zoomInButtonIcon) {
			this.zoomInButtonIcon = zoomInButtonIcon;
		}
		public String getZoomoutButtonIcon() {
			return zoomoutButtonIcon;
		}
		public void setZoomoutButtonIcon(String zoomoutButtonIcon) {
			this.zoomoutButtonIcon = zoomoutButtonIcon;
		}
		public String getLanguageResourcePath() {
			return languageResourcePath;
		}
		public void setLanguageResourcePath(String languageResourcePath) {
			this.languageResourcePath = languageResourcePath;
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
	    private String deviceShowName;
	    
	    private int timeEfficiencyUnit;
		
		private boolean cache;

	    private String loginLanguage;

	    private int pageSize;

	    private boolean syncOrAsync;

	    private boolean expandedAll;

	    private int defaultComboxSize;

	    private int defaultGraghSize;
	    
	    private int vacuateThreshold;

	    private String productionUnit;
	    
	    private int resourceMonitoringSaveData;
	    
	    private int resourceMonitoringVacuateThreshold;

	    private boolean iot;

	    private String scene;
	    
	    private String module;
	    
	    private boolean showVideo;

	    private boolean showLogo;

	    private boolean printLog;
	    
	    private boolean exportAdInitData;
	    
	    private int rangeLimit;
	    
	    private int exportLimit;

	    private boolean simulateAcqEnable;
	    
	    private int sendCycle;
	    
	    private int timeDifference;
	    
	    private String otherStaticResourceTimestamp;

	    public void setCache(boolean cache){
	        this.cache = cache;
	    }
	    public boolean getCache(){
	        return this.cache;
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
		public String getOtherStaticResourceTimestamp() {
			return otherStaticResourceTimestamp;
		}
		public void setOtherStaticResourceTimestamp(String otherStaticResourceTimestamp) {
			this.otherStaticResourceTimestamp = otherStaticResourceTimestamp;
		}
		public String getScene() {
			return scene;
		}
		public void setScene(String scene) {
			this.scene = scene;
		}
		public String getModule() {
			return module;
		}
		public void setModule(String module) {
			this.module = module;
		}
		public boolean isIot() {
			return iot;
		}
		public void setIot(boolean iot) {
			this.iot = iot;
		}
		public String getDeviceShowName() {
			return deviceShowName;
		}
		public void setDeviceShowName(String deviceShowName) {
			this.deviceShowName = deviceShowName;
		}
		public boolean getShowVideo() {
			return showVideo;
		}
		public void setShowVideo(boolean showVideo) {
			this.showVideo = showVideo;
		}
		public boolean isExportAdInitData() {
			return exportAdInitData;
		}
		public void setExportAdInitData(boolean exportAdInitData) {
			this.exportAdInitData = exportAdInitData;
		}
		public int getRangeLimit() {
			return rangeLimit;
		}
		public void setRangeLimit(int rangeLimit) {
			this.rangeLimit = rangeLimit;
		}
		public int getResourceMonitoringSaveData() {
			return resourceMonitoringSaveData;
		}
		public void setResourceMonitoringSaveData(int resourceMonitoringSaveData) {
			this.resourceMonitoringSaveData = resourceMonitoringSaveData;
		}
		public int getResourceMonitoringVacuateThreshold() {
			return resourceMonitoringVacuateThreshold;
		}
		public void setResourceMonitoringVacuateThreshold(int resourceMonitoringVacuateThreshold) {
			this.resourceMonitoringVacuateThreshold = resourceMonitoringVacuateThreshold;
		}
		public String getLoginLanguage() {
			return loginLanguage;
		}
		public void setLoginLanguage(String loginLanguage) {
			this.loginLanguage = loginLanguage;
		}
		public int getTimeEfficiencyUnit() {
			return timeEfficiencyUnit;
		}
		public void setTimeEfficiencyUnit(int timeEfficiencyUnit) {
			this.timeEfficiencyUnit = timeEfficiencyUnit;
		}
	}
	
	public static class Report
	{
		private String template;
		
		private int offsetHour;
		
		private int interval;
		
		private int delay;

		public int getOffsetHour() {
			return offsetHour;
		}

		public void setOffsetHour(int offsetHour) {
			this.offsetHour = offsetHour;
		}

		public int getInterval() {
			return interval;
		}

		public void setInterval(int interval) {
			this.interval = interval;
		}

		public int getDelay() {
			return delay;
		}

		public void setDelay(int delay) {
			this.delay = delay;
		}

		public String getTemplate() {
			return template;
		}

		public void setTemplate(String template) {
			this.template = template;
		}
	}
	
	public static class DatabaseMaintenance{
		
		private int cycle;
		
		private String startTime;
		
		private int retentionTime;
		
		public int getCycle() {
			return cycle;
		}
		
		public void setCycle(int cycle) {
			this.cycle = cycle;
		}
		
		public String getStartTime() {
			return startTime;
		}
		
		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}
		
		public int getRetentionTime() {
			return retentionTime;
		}
		
		public void setRetentionTime(int retentionTime) {
			this.retentionTime = retentionTime;
		}
	}
}
