package com.cosog.utils;

import java.util.List;

public class OEMConfigFile {
	private Oem oem;
	
	private Report report;
	
	private DatabaseMaintenance databaseMaintenance;
	
	private Email email;
	
	private ModuleContentConfig moduleContent;
	
	private DataVacuate dataVacuate;
	
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
	    
	    private String switchButtonIcon;
	    
	    private String switchDisabledButtonIcon;
	    
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
		public String getSwitchButtonIcon() {
			return switchButtonIcon;
		}
		public void setSwitchButtonIcon(String switchButtonIcon) {
			this.switchButtonIcon = switchButtonIcon;
		}
		public String getSwitchDisabledButtonIcon() {
			return switchDisabledButtonIcon;
		}
		public void setSwitchDisabledButtonIcon(String switchDisabledButtonIcon) {
			this.switchDisabledButtonIcon = switchDisabledButtonIcon;
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
	    
//	    private int vacuateThreshold;

	    private String productionUnit;
	    
	    private int resourceMonitoringSaveData;
	    
	    private int resourceMonitoringVacuateThreshold;

	    private boolean iot;

	    private String scene;
	    
	    private String module;

	    private boolean showLogo;

	    private boolean printLog;
	    
	    private boolean exportAdInitData;
	    
	    private boolean saveAcqRawData;
	    
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
//		public int getVacuateThreshold() {
//			return vacuateThreshold;
//		}
//		public void setVacuateThreshold(int vacuateThreshold) {
//			this.vacuateThreshold = vacuateThreshold;
//		}
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
		public boolean getIot() {
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
		public boolean getExportAdInitData() {
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
		public boolean getSaveAcqRawData() {
			return saveAcqRawData;
		}
		public void setSaveAcqRawData(boolean saveAcqRawData) {
			this.saveAcqRawData = saveAcqRawData;
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
	
	public static class DatabaseMaintenanceTableConfig{
		
		private boolean enabled;
		
		private int retentionTime;

		public boolean getEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public int getRetentionTime() {
			return retentionTime;
		}

		public void setRetentionTime(int retentionTime) {
			this.retentionTime = retentionTime;
		}
	}
	
	public static class TableConfig{
		
		private DatabaseMaintenanceTableConfig acqdata_hist;
		
		private DatabaseMaintenanceTableConfig acqrawdata;
		
		private DatabaseMaintenanceTableConfig alarminfo_hist;
		
		private DatabaseMaintenanceTableConfig dailytotalcalculate_hist;
		
		private DatabaseMaintenanceTableConfig dailycalculationdata;
		
		private DatabaseMaintenanceTableConfig timingcalculationdata;
		
		private DatabaseMaintenanceTableConfig srpacqdata_hist;
		
		private DatabaseMaintenanceTableConfig srpdailycalculationdata;
		
		private DatabaseMaintenanceTableConfig srptimingcalculationdata;
		
		private DatabaseMaintenanceTableConfig pcpacqdata_hist;
		
		private DatabaseMaintenanceTableConfig pcpdailycalculationdata;
		
		private DatabaseMaintenanceTableConfig pcptimingcalculationdata;
		
		private DatabaseMaintenanceTableConfig acqdata_vacuate;
		
		private DatabaseMaintenanceTableConfig srpacqdata_vacuate;
		
		private DatabaseMaintenanceTableConfig pcpacqdata_vacuate;

		public DatabaseMaintenanceTableConfig getAcqdata_hist() {
			return acqdata_hist;
		}

		public void setAcqdata_hist(DatabaseMaintenanceTableConfig acqdata_hist) {
			this.acqdata_hist = acqdata_hist;
		}

		public DatabaseMaintenanceTableConfig getAcqrawdata() {
			return acqrawdata;
		}

		public void setAcqrawdata(DatabaseMaintenanceTableConfig acqrawdata) {
			this.acqrawdata = acqrawdata;
		}

		public DatabaseMaintenanceTableConfig getAlarminfo_hist() {
			return alarminfo_hist;
		}

		public void setAlarminfo_hist(DatabaseMaintenanceTableConfig alarminfo_hist) {
			this.alarminfo_hist = alarminfo_hist;
		}

		public DatabaseMaintenanceTableConfig getDailytotalcalculate_hist() {
			return dailytotalcalculate_hist;
		}

		public void setDailytotalcalculate_hist(DatabaseMaintenanceTableConfig dailytotalcalculate_hist) {
			this.dailytotalcalculate_hist = dailytotalcalculate_hist;
		}

		public DatabaseMaintenanceTableConfig getDailycalculationdata() {
			return dailycalculationdata;
		}

		public void setDailycalculationdata(DatabaseMaintenanceTableConfig dailycalculationdata) {
			this.dailycalculationdata = dailycalculationdata;
		}

		public DatabaseMaintenanceTableConfig getTimingcalculationdata() {
			return timingcalculationdata;
		}

		public void setTimingcalculationdata(DatabaseMaintenanceTableConfig timingcalculationdata) {
			this.timingcalculationdata = timingcalculationdata;
		}

		public DatabaseMaintenanceTableConfig getSrpacqdata_hist() {
			return srpacqdata_hist;
		}

		public void setSrpacqdata_hist(DatabaseMaintenanceTableConfig srpacqdata_hist) {
			this.srpacqdata_hist = srpacqdata_hist;
		}

		public DatabaseMaintenanceTableConfig getSrpdailycalculationdata() {
			return srpdailycalculationdata;
		}

		public void setSrpdailycalculationdata(DatabaseMaintenanceTableConfig srpdailycalculationdata) {
			this.srpdailycalculationdata = srpdailycalculationdata;
		}

		public DatabaseMaintenanceTableConfig getSrptimingcalculationdata() {
			return srptimingcalculationdata;
		}

		public void setSrptimingcalculationdata(DatabaseMaintenanceTableConfig srptimingcalculationdata) {
			this.srptimingcalculationdata = srptimingcalculationdata;
		}

		public DatabaseMaintenanceTableConfig getPcpacqdata_hist() {
			return pcpacqdata_hist;
		}

		public void setPcpacqdata_hist(DatabaseMaintenanceTableConfig pcpacqdata_hist) {
			this.pcpacqdata_hist = pcpacqdata_hist;
		}

		public DatabaseMaintenanceTableConfig getPcpdailycalculationdata() {
			return pcpdailycalculationdata;
		}

		public void setPcpdailycalculationdata(DatabaseMaintenanceTableConfig pcpdailycalculationdata) {
			this.pcpdailycalculationdata = pcpdailycalculationdata;
		}

		public DatabaseMaintenanceTableConfig getPcptimingcalculationdata() {
			return pcptimingcalculationdata;
		}

		public void setPcptimingcalculationdata(DatabaseMaintenanceTableConfig pcptimingcalculationdata) {
			this.pcptimingcalculationdata = pcptimingcalculationdata;
		}

		public DatabaseMaintenanceTableConfig getAcqdata_vacuate() {
			return acqdata_vacuate;
		}

		public void setAcqdata_vacuate(DatabaseMaintenanceTableConfig acqdata_vacuate) {
			this.acqdata_vacuate = acqdata_vacuate;
		}

		public DatabaseMaintenanceTableConfig getSrpacqdata_vacuate() {
			return srpacqdata_vacuate;
		}

		public void setSrpacqdata_vacuate(DatabaseMaintenanceTableConfig srpacqdata_vacuate) {
			this.srpacqdata_vacuate = srpacqdata_vacuate;
		}

		public DatabaseMaintenanceTableConfig getPcpacqdata_vacuate() {
			return pcpacqdata_vacuate;
		}

		public void setPcpacqdata_vacuate(DatabaseMaintenanceTableConfig pcpacqdata_vacuate) {
			this.pcpacqdata_vacuate = pcpacqdata_vacuate;
		}
	}
	
	public static class DatabaseMaintenance{
		
		private int cycle;
		
		private String startTime;
		
		private String endTime;
		
		private int singleDeleteTime;
		
		private int singleDeleteRecord;
		
		private TableConfig tableConfig;
		
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

		public int getSingleDeleteTime() {
			return singleDeleteTime;
		}

		public void setSingleDeleteTime(int singleDeleteTime) {
			this.singleDeleteTime = singleDeleteTime;
		}

		public TableConfig getTableConfig() {
			return tableConfig;
		}

		public void setTableConfig(TableConfig tableConfig) {
			this.tableConfig = tableConfig;
		}

		public int getSingleDeleteRecord() {
			return singleDeleteRecord;
		}

		public void setSingleDeleteRecord(int singleDeleteRecord) {
			this.singleDeleteRecord = singleDeleteRecord;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
	}
	
	public static class DataDictionaryContentConfig{
		
		private int calculateData;
		
		private int inputData;

		public int getCalculateData() {
			return calculateData;
		}

		public void setCalculateData(int calculateData) {
			this.calculateData = calculateData;
		}

		public int getInputData() {
			return inputData;
		}

		public void setInputData(int inputData) {
			this.inputData = inputData;
		}
	}
	
	public static class RealTimeMonitoringContentConfig{
		
		private boolean FESDiagramResultData;

		public boolean getFESDiagramResultData() {
			return FESDiagramResultData;
		}

		public void setFESDiagramResultData(boolean fESDiagramResultData) {
			FESDiagramResultData = fESDiagramResultData;
		}
	}
	
	public static class HistoryQueryContentConfig{
		
		private boolean FESDiagramResultData;

		public boolean getFESDiagramResultData() {
			return FESDiagramResultData;
		}

		public void setFESDiagramResultData(boolean fESDiagramResultData) {
			FESDiagramResultData = fESDiagramResultData;
		}
	}
	
	public static class DataMaintainingContentConfig{
		
		private boolean FESDiagramResultData;
		
		private boolean RPMResultData;

		public boolean getFESDiagramResultData() {
			return FESDiagramResultData;
		}

		public void setFESDiagramResultData(boolean fESDiagramResultData) {
			FESDiagramResultData = fESDiagramResultData;
		}

		public boolean getRPMResultData() {
			return RPMResultData;
		}

		public void setRPMResultData(boolean rPMResultData) {
			RPMResultData = rPMResultData;
		}
	}
	
	public static class PrimaryDeviceContentConfig{
		
		private boolean deviceExtendInformation;
		
		private boolean additionalInformation;
		
		private boolean auxiliaryDevice;
		
		private boolean videoConfig;
		
		private boolean calculateDataConfig;
		
		private boolean FSDiagramConstruction;
		
		private boolean systemParameterConfiguration;

		public boolean getDeviceExtendInformation() {
			return deviceExtendInformation;
		}

		public void setDeviceExtendInformation(boolean deviceExtendInformation) {
			this.deviceExtendInformation = deviceExtendInformation;
		}

		public boolean getAdditionalInformation() {
			return additionalInformation;
		}

		public void setAdditionalInformation(boolean additionalInformation) {
			this.additionalInformation = additionalInformation;
		}

		public boolean getAuxiliaryDevice() {
			return auxiliaryDevice;
		}

		public void setAuxiliaryDevice(boolean auxiliaryDevice) {
			this.auxiliaryDevice = auxiliaryDevice;
		}

		public boolean getVideoConfig() {
			return videoConfig;
		}

		public void setVideoConfig(boolean videoConfig) {
			this.videoConfig = videoConfig;
		}

		public boolean getCalculateDataConfig() {
			return calculateDataConfig;
		}

		public void setCalculateDataConfig(boolean calculateDataConfig) {
			this.calculateDataConfig = calculateDataConfig;
		}

		public boolean getFSDiagramConstruction() {
			return FSDiagramConstruction;
		}

		public void setFSDiagramConstruction(boolean FSDiagramConstruction) {
			this.FSDiagramConstruction = FSDiagramConstruction;
		}

		public boolean getSystemParameterConfiguration() {
			return systemParameterConfiguration;
		}

		public void setSystemParameterConfiguration(boolean systemParameterConfiguration) {
			this.systemParameterConfiguration = systemParameterConfiguration;
		}
	}
	
	public static class ModuleContentConfig{
		
		private DataDictionaryContentConfig dataDictionary;
		
		private RealTimeMonitoringContentConfig realTimeMonitoring;
		
		private HistoryQueryContentConfig historyQuery;
		
		private PrimaryDeviceContentConfig primaryDevice;
		
		private DataMaintainingContentConfig dataMaintaining;

		public DataDictionaryContentConfig getDataDictionary() {
			return dataDictionary;
		}

		public void setDataDictionary(DataDictionaryContentConfig dataDictionary) {
			this.dataDictionary = dataDictionary;
		}

		public RealTimeMonitoringContentConfig getRealTimeMonitoring() {
			return realTimeMonitoring;
		}

		public void setRealTimeMonitoring(RealTimeMonitoringContentConfig realTimeMonitoring) {
			this.realTimeMonitoring = realTimeMonitoring;
		}

		public HistoryQueryContentConfig getHistoryQuery() {
			return historyQuery;
		}

		public void setHistoryQuery(HistoryQueryContentConfig historyQuery) {
			this.historyQuery = historyQuery;
		}

		public PrimaryDeviceContentConfig getPrimaryDevice() {
			return primaryDevice;
		}

		public void setPrimaryDevice(PrimaryDeviceContentConfig primaryDevice) {
			this.primaryDevice = primaryDevice;
		}

		public DataMaintainingContentConfig getDataMaintaining() {
			return dataMaintaining;
		}

		public void setDataMaintaining(DataMaintainingContentConfig dataMaintaining) {
			this.dataMaintaining = dataMaintaining;
		}
		
	}
	
	public static class DataVacuate{
		
		private int vacuateRecord;
		
		private float saveInterval;
		
		private int saveIntervalWaveRange;
		
		private int vacuateThreshold;

		public int getVacuateRecord() {
			return vacuateRecord;
		}

		public void setVacuateRecord(int vacuateRecord) {
			this.vacuateRecord = vacuateRecord;
		}

		public float getSaveInterval() {
			return saveInterval;
		}

		public void setSaveInterval(float saveInterval) {
			this.saveInterval = saveInterval;
		}

		public int getVacuateThreshold() {
			return vacuateThreshold;
		}

		public void setVacuateThreshold(int vacuateThreshold) {
			this.vacuateThreshold = vacuateThreshold;
		}

		public int getSaveIntervalWaveRange() {
			return saveIntervalWaveRange;
		}

		public void setSaveIntervalWaveRange(int saveIntervalWaveRange) {
			this.saveIntervalWaveRange = saveIntervalWaveRange;
		}
	}

	public ModuleContentConfig getModuleContent() {
		return moduleContent;
	}

	public void setModuleContent(ModuleContentConfig moduleContent) {
		this.moduleContent = moduleContent;
	}

	public DataVacuate getDataVacuate() {
		return dataVacuate;
	}

	public void setDataVacuate(DataVacuate dataVacuate) {
		this.dataVacuate = dataVacuate;
	}
}
