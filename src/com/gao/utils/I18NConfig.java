package com.gao.utils;

import java.util.ResourceBundle;

/**
 * <p>
 * 描述：国际化配置文件解析类
 * </p>
 * 
 * @author gao 2014-06-10
 * 
 */
public class I18NConfig {
	private static String CONFIG_FILE = "config.messages_";
	private static String wellsPercent = "";
	private static String gklx = "";
	private static String workStatisticsImage = "";
	private static String productImage = "";
	private static String productLevel = "";
	private static String productCyLevel = "";
	private static String productTotal = "";
	private static String wellNums = "";
	private static String productTotalCount = "";
	private static String productAverage = "";
	private static String locale = "zh_CN";
	public static String getProductCyLevel() {
		return productCyLevel;
	}

	public static void setProductCyLevel(String productCyLevel) {
		I18NConfig.productCyLevel = productCyLevel;
	}

	public static String getProductTotal() {
		return productTotal;
	}

	public static void setProductTotal(String productTotal) {
		I18NConfig.productTotal = productTotal;
	}

	public static String getWellNums() {
		return wellNums;
	}

	public static void setWellNums(String wellNums) {
		I18NConfig.wellNums = wellNums;
	}

	public static String getProductTotalCount() {
		return productTotalCount;
	}

	public static void setProductTotalCount(String productTotalCount) {
		I18NConfig.productTotalCount = productTotalCount;
	}

	public static String getProductAverage() {
		return productAverage;
	}

	public static void setProductAverage(String productAverage) {
		I18NConfig.productAverage = productAverage;
	}

	static {
		getconfiguration();
	}

	static void getconfiguration() {
		locale = Config.getInstance().configFile.getOthers().getLanguage();
		ResourceBundle resources = ResourceBundle.getBundle(I18NConfig.CONFIG_FILE + locale);
		workStatisticsImage = resources.getString("cosog.workStatisticsImage");
		gklx = resources.getString("cosog.gklx");
		wellsPercent = resources.getString("cosog.wellsPercent");
		productImage = resources.getString("cosog.productImage");
		productLevel = resources.getString("cosog.productLevel");
		productCyLevel = resources.getString("cosog.productCyLevel");
		productTotal = resources.getString("cosog.productTotal");
		wellNums = resources.getString("cosog.wellNums");
		productTotalCount = resources.getString("cosog.productTotalCount");
		productAverage = resources.getString("cosog.productAverage");


	}

	public static String getProductImage() {
		return productImage;
	}

	public static void setProductImage(String productImage) {
		I18NConfig.productImage = productImage;
	}

	public static String getProductLevel() {
		return productLevel;
	}

	public static void setProductLevel(String productLevel) {
		I18NConfig.productLevel = productLevel;
	}

	public static String getCONFIG_FILE() {
		return CONFIG_FILE;
	}

	public static String getWellsPercent() {
		return wellsPercent;
	}

	public static void setWellsPercent(String wellsPercent) {
		I18NConfig.wellsPercent = wellsPercent;
	}

	public static String getGklx() {
		return gklx;
	}

	public static void setGklx(String gklx) {
		I18NConfig.gklx = gklx;
	}

	public static String getWorkStatisticsImage() {
		return workStatisticsImage;
	}

	public static void setWorkStatisticsImage(String workStatisticsImage) {
		I18NConfig.workStatisticsImage = workStatisticsImage;
	}

	public static void setCONFIG_FILE(String cONFIG_FILE) {
		CONFIG_FILE = cONFIG_FILE;
	}

	public I18NConfig() {
	}

	public static void main(String[] args) {
		System.out.println(I18NConfig.getWorkStatisticsImage());
	}
}
