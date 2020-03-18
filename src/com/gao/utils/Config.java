package com.gao.utils;

import java.util.ResourceBundle;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 * <p>
 * 描述：数据库配置文件解析类
 * </p>
 * 
 * @author gao 2014-06-10
 * 
 */
public class Config {
	private static String cache = "true";
	private static String CONFIG_FILE = "config.config";
	private static String dbtype = "";
	private static String dialect = "";
	private static String driver = "";
	private static String driverUrl = "";
	private static String language = "en_US";
	private static String location = "";
	private static int pageSize = 25;
	private static String password = "";
	private static String pic_inner1 = "";
	private static String pic_inner2 = "";
	private static String pic_outer = "";
	private static String serialNumber = "";
	private static String url_filter = "";
	private static String user = "";
	private static String outerDriverUrl = "";
	private static String outerDriver = "";
	private static String outerDialect = "";
	private static String outerUser = "";
	private static String outerPassword = "";
	private static String pumpingunitElecCalculateHttpServerURL="";
	private static String screwpumpElecCalculateHttpServerURL="";
	private static String calculateHttpServerURL="";
	private static String screwPumpCalculateHttpServerURL="";
	private static String totalCalculateHttpServerURL="";
	
	private static String balanceTorqueHttpServerURL="";
	private static String balancePowerHttpServerURL="";
	private static String balanceCycleHttpServerURL="";
	
	private static String timeEfficiencyHttpServerURL="";
	private static String commHttpServerURL="";
	private static String energyHttpServerURL="";
	
	private static String fa2fsHttpServerURL="";
	
	private static String inversionSwitch;
	private static String electricToFSDiagramHttpServerURL;
	private static String electricToFSDiagramAutoHttpServerURL;
	
	private static String timerCorrectionStart="";
	private static String timerCorrectionEnd="";
	private static String timerCorrectionLimit="";
	
	private static String projectAccessPath="";
	
	private static int SyncOrAsync=0;
	private static int expandedAll=0;
	private static int defaultComboxSize=100;
	private static int defaultGraghSize=60;
	
	private static String mqttServerUrl = "";
	static {
		getconfiguration();
	}

	public static String getCache() {
		return cache;
	}

	public static String getCONFIG_FILE() {
		return CONFIG_FILE;
	}
	
	public static PropertySourcesPlaceholderConfigurer properties() {
	      PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
	      YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
	      yaml.setResources(new ClassPathResource("appConfig.yml"));
	      propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject());
	      return propertySourcesPlaceholderConfigurer;
	}

	static void getconfiguration() {
		ResourceBundle resources = ResourceBundle.getBundle(Config.CONFIG_FILE);
		driverUrl = resources.getString("driverUrl");
		driver = resources.getString("driver");
		dialect = resources.getString("dialect");
		cache = resources.getString("cache");
		user = resources.getString("user");
		password = resources.getString("password");
		
		outerDriverUrl = resources.getString("outerDriverUrl");
		outerDriver = resources.getString("outerDriver");
		outerDialect = resources.getString("outerDialect");
		outerUser = resources.getString("outerUser");
		outerPassword = resources.getString("outerPassword");
		
		serialNumber = resources.getString("serialnumber");
		language = resources.getString("language");
		pic_inner1 = resources.getString("pic_inner1");
		pic_inner2 = resources.getString("pic_inner2");
		pic_outer = resources.getString("pic_outer");
		url_filter = resources.getString("url_filter");
		dbtype = resources.getString("dbtype");
		location = resources.getString("location");
		pumpingunitElecCalculateHttpServerURL = resources.getString("pumpingunitElecCalculateHttpServerURL");
		screwpumpElecCalculateHttpServerURL = resources.getString("screwpumpElecCalculateHttpServerURL");
		calculateHttpServerURL=resources.getString("calculateHttpServerURL");
		screwPumpCalculateHttpServerURL=resources.getString("screwPumpCalculateHttpServerURL");
		totalCalculateHttpServerURL=resources.getString("totalCalculateHttpServerURL");
		
		balanceTorqueHttpServerURL = resources.getString("balanceTorqueHttpServerURL");
		balancePowerHttpServerURL=resources.getString("balancePowerHttpServerURL");
		balanceCycleHttpServerURL=resources.getString("balanceCycleHttpServerURL");
		
		timeEfficiencyHttpServerURL=resources.getString("timeEfficiencyHttpServerURL");
		
		commHttpServerURL=resources.getString("commHttpServerURL");
		
		energyHttpServerURL=resources.getString("energyHttpServerURL");
		
		fa2fsHttpServerURL=resources.getString("fa2fsHttpServerURL");
		inversionSwitch=resources.getString("inversionSwitch");
		electricToFSDiagramHttpServerURL=resources.getString("electricToFSDiagramHttpServerURL");
		electricToFSDiagramAutoHttpServerURL=resources.getString("electricToFSDiagramAutoHttpServerURL");
		projectAccessPath=resources.getString("projectAccessPath");
		
		mqttServerUrl=resources.getString("mqttServerUrl");
		
		timerCorrectionStart=resources.getString("timerCorrectionStart");
		timerCorrectionEnd=resources.getString("timerCorrectionEnd");
		timerCorrectionLimit=resources.getString("timerCorrectionLimit");
		
		String pagestr = resources.getString("pageSize");
		if (pagestr != null) {
			pageSize = Integer.parseInt(pagestr);
		}
		String ayncstr=resources.getString("SyncOrAsync");
		if (ayncstr != null) {
			SyncOrAsync = Integer.parseInt(ayncstr);
		}
		String expandedstr=resources.getString("expandedAll");
		if (expandedstr != null) {
			expandedAll = Integer.parseInt(expandedstr);
		}
		String defaultComboxSizestr=resources.getString("defaultComboxSize");
		if (defaultComboxSizestr != null) {
			defaultComboxSize = Integer.parseInt(defaultComboxSizestr);
		}
		String defaultGraghSizestr=resources.getString("defaultGraghSize");
		if (defaultGraghSizestr != null) {
			defaultGraghSize = Integer.parseInt(defaultGraghSizestr);
		}
	}

	public static String getDbtype() {
		return dbtype;
	}

	public static String getDialect() {
		return dialect;
	}

	public static String getDriver() {
		return driver;
	}

	public static String getDriverUrl() {
		return driverUrl;
	}

	public static String getLanguage() {
		return language;
	}

	public static String getLocation() {
		return location;
	}

	public static int getPageSize() {
		return pageSize;
	}

	public static String getPassword() {
		return password;
	}

	public static String getPic_inner1() {
		return pic_inner1;
	}

	public static String getPic_inner2() {
		return pic_inner2;
	}

	public static String getPic_outer() {
		return pic_outer;
	}

	public static String getPicUrlByClientIp(String ip, String url_filter) {
		String[] filters = url_filter.split(",");
		boolean IsInner = false;
		String pic_urlString = Config.getPic_inner1();
		for (String myIp : filters) {
			if (ip.startsWith(myIp)) {
				if (ip.startsWith("10.66")) {
					IsInner = true;
					pic_urlString = Config.getPic_inner2();
				} else {
					IsInner = true;
					pic_urlString = Config.getPic_inner1();
				}
			}

		}
		if (!IsInner) {
			pic_urlString = Config.getPic_outer();
		}
		return pic_urlString;
	}

	public static String getSerialNumber() {
		return serialNumber;
	}

	public static String getUrl_filter() {
		return url_filter;
	}

	public static String getUser() {
		return user;
	}

	public static boolean judgeInnerOrOuter(String ip, String url_filter) {
		boolean IsInnerOrOuter = false;
		String[] filters = url_filter.split(",");
		for (String myIp : filters) {
			if (ip.startsWith(myIp)) {
				IsInnerOrOuter = true;
			}
		}
		return IsInnerOrOuter;
	}

	public static boolean judgeInnerOrOuter1(String ip, String url_filter) {
		boolean IsInnerOrOuter = false;
		String[] filters = url_filter.split(",");
		for (String myIp : filters) {
			if (ip.startsWith(myIp)) {
				IsInnerOrOuter = true;
			}
		}
		return IsInnerOrOuter;
	}

	public static void setCache(String cache) {
		Config.cache = cache;
	}

	public static void setCONFIG_FILE(String cONFIG_FILE) {
		CONFIG_FILE = cONFIG_FILE;
	}

	public static void setDbtype(String dbtype) {
		Config.dbtype = dbtype;
	}

	public static void setDialect(String dialect) {
		Config.dialect = dialect;
	}

	public static void setDriver(String driver) {
		Config.driver = driver;
	}

	public static void setDriverUrl(String driverUrl) {
		Config.driverUrl = driverUrl;
	}

	public static void setLanguage(String language) {
		Config.language = language;
	}

	public static void setLocation(String location) {
		Config.location = location;
	}

	public static void setPageSize(int pageSize) {
		Config.pageSize = pageSize;
	}

	public static void setPassword(String password) {
		Config.password = password;
	}

	public static void setPic_inner1(String pic_inner1) {
		Config.pic_inner1 = pic_inner1;
	}

	public static void setPic_inner2(String pic_inner2) {
		Config.pic_inner2 = pic_inner2;
	}

	public static void setPic_outer(String pic_outer) {
		Config.pic_outer = pic_outer;
	}

	public static void setSerialNumber(String serialNumber) {
		Config.serialNumber = serialNumber;
	}

	public static void setUrl_filter(String url_filter) {
		Config.url_filter = url_filter;
	}

	public static void setUser(String user) {
		Config.user = user;
	}

	public Config() {
	}

	public static int getSyncOrAsync() {
		return SyncOrAsync;
	}

	public static void setSyncOrAsync(int syncOrAsync) {
		SyncOrAsync = syncOrAsync;
	}

	public static int getExpandedAll() {
		return expandedAll;
	}

	public static void setExpandedAll(int expandedAll) {
		Config.expandedAll = expandedAll;
	}

	public static int getDefaultComboxSize() {
		return defaultComboxSize;
	}

	public static void setDefaultComboxSize(int defaultComboxSize) {
		Config.defaultComboxSize = defaultComboxSize;
	}

	public static int getDefaultGraghSize() {
		return defaultGraghSize;
	}

	public static void setDefaultGraghSize(int defaultGraghSize) {
		Config.defaultGraghSize = defaultGraghSize;
	}

	
	public static String getCalculateHttpServerURL() {
		return calculateHttpServerURL;
	}

	public static void setCalculateHttpServerURL(String calculateHttpServerURL) {
		Config.calculateHttpServerURL = calculateHttpServerURL;
	}

	

	public static String getOuterDriverUrl() {
		return outerDriverUrl;
	}

	public static void setOuterDriverUrl(String outerDriverUrl) {
		Config.outerDriverUrl = outerDriverUrl;
	}

	public static String getOuterUser() {
		return outerUser;
	}

	public static void setOuterUser(String outerUser) {
		Config.outerUser = outerUser;
	}

	public static String getOuterPassword() {
		return outerPassword;
	}

	public static void setOuterPassword(String outerPassword) {
		Config.outerPassword = outerPassword;
	}

	public static String getOuterDriver() {
		return outerDriver;
	}

	public static void setOuterDriver(String outerDriver) {
		Config.outerDriver = outerDriver;
	}

	public static String getOuterDialect() {
		return outerDialect;
	}

	public static void setOuterDialect(String outerDialect) {
		Config.outerDialect = outerDialect;
	}

	public static String getPumpingunitElecCalculateHttpServerURL() {
		return pumpingunitElecCalculateHttpServerURL;
	}

	public static void setPumpingunitElecCalculateHttpServerURL(String pumpingunitElecCalculateHttpServerURL) {
		Config.pumpingunitElecCalculateHttpServerURL = pumpingunitElecCalculateHttpServerURL;
	}

	public static String getProjectAccessPath() {
		return projectAccessPath;
	}

	public static void setProjectAccessPath(String projectAccessPath) {
		Config.projectAccessPath = projectAccessPath;
	}

	public static String getTotalCalculateHttpServerURL() {
		return totalCalculateHttpServerURL;
	}

	public static void setTotalCalculateHttpServerURL(String totalCalculateHttpServerURL) {
		Config.totalCalculateHttpServerURL = totalCalculateHttpServerURL;
	}

	public static String getBalanceTorqueHttpServerURL() {
		return balanceTorqueHttpServerURL;
	}

	public static void setBalanceTorqueHttpServerURL(String balanceTorqueHttpServerURL) {
		Config.balanceTorqueHttpServerURL = balanceTorqueHttpServerURL;
	}

	public static String getBalancePowerHttpServerURL() {
		return balancePowerHttpServerURL;
	}

	public static void setBalancePowerHttpServerURL(String balancePowerHttpServerURL) {
		Config.balancePowerHttpServerURL = balancePowerHttpServerURL;
	}

	public static String getBalanceCycleHttpServerURL() {
		return balanceCycleHttpServerURL;
	}

	public static void setBalanceCycleHttpServerURL(String balanceCycleHttpServerURL) {
		Config.balanceCycleHttpServerURL = balanceCycleHttpServerURL;
	}

	public static String getFa2fsHttpServerURL() {
		return fa2fsHttpServerURL;
	}

	public static void setFa2fsHttpServerURL(String fa2fsHttpServerURL) {
		Config.fa2fsHttpServerURL = fa2fsHttpServerURL;
	}

	public static String getTimeEfficiencyHttpServerURL() {
		return timeEfficiencyHttpServerURL;
	}

	public static void setTimeEfficiencyHttpServerURL(String timeEfficiencyHttpServerURL) {
		Config.timeEfficiencyHttpServerURL = timeEfficiencyHttpServerURL;
	}

	public static String getScrewpumpElecCalculateHttpServerURL() {
		return screwpumpElecCalculateHttpServerURL;
	}

	public static void setScrewpumpElecCalculateHttpServerURL(String screwpumpElecCalculateHttpServerURL) {
		Config.screwpumpElecCalculateHttpServerURL = screwpumpElecCalculateHttpServerURL;
	}

	public static String getCommHttpServerURL() {
		return commHttpServerURL;
	}

	public static void setCommHttpServerURL(String commHttpServerURL) {
		Config.commHttpServerURL = commHttpServerURL;
	}

	public static String getElectricToFSDiagramHttpServerURL() {
		return electricToFSDiagramHttpServerURL;
	}

	public static void setElectricToFSDiagramHttpServerURL(String electricToFSDiagramHttpServerURL) {
		Config.electricToFSDiagramHttpServerURL = electricToFSDiagramHttpServerURL;
	}

	public static String getScrewPumpCalculateHttpServerURL() {
		return screwPumpCalculateHttpServerURL;
	}

	public static void setScrewPumpCalculateHttpServerURL(String screwPumpCalculateHttpServerURL) {
		Config.screwPumpCalculateHttpServerURL = screwPumpCalculateHttpServerURL;
	}

	public static String getMqttServerUrl() {
		return mqttServerUrl;
	}

	public static void setMqttServerUrl(String mqttServerUrl) {
		Config.mqttServerUrl = mqttServerUrl;
	}

	public static String getInversionSwitch() {
		return inversionSwitch;
	}

	public static void setInversionSwitch(String inversionSwitch) {
		Config.inversionSwitch = inversionSwitch;
	}

	public static String getElectricToFSDiagramAutoHttpServerURL() {
		return electricToFSDiagramAutoHttpServerURL;
	}

	public static void setElectricToFSDiagramAutoHttpServerURL(String electricToFSDiagramAutoHttpServerURL) {
		Config.electricToFSDiagramAutoHttpServerURL = electricToFSDiagramAutoHttpServerURL;
	}

	public static String getTimerCorrectionStart() {
		return timerCorrectionStart;
	}

	public static void setTimerCorrectionStart(String timerCorrectionStart) {
		Config.timerCorrectionStart = timerCorrectionStart;
	}

	public static String getTimerCorrectionEnd() {
		return timerCorrectionEnd;
	}

	public static void setTimerCorrectionEnd(String timerCorrectionEnd) {
		Config.timerCorrectionEnd = timerCorrectionEnd;
	}

	public static String getTimerCorrectionLimit() {
		return timerCorrectionLimit;
	}

	public static void setTimerCorrectionLimit(String timerCorrectionLimit) {
		Config.timerCorrectionLimit = timerCorrectionLimit;
	}

	public static String getEnergyHttpServerURL() {
		return energyHttpServerURL;
	}

	public static void setEnergyHttpServerURL(String energyHttpServerURL) {
		Config.energyHttpServerURL = energyHttpServerURL;
	}
}
