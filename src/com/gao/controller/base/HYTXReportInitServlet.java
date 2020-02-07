package com.gao.controller.base;

import java.util.Map;

import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.gao.utils.Config;
import com.gao.utils.CryptUtil;
import com.gao.utils.DataModelMap;
import com.gao.utils.MACAddress;
import com.gao.utils.StringManagerUtils;

/**<p>描述：AppInitServlet 应用程序初始化.</p>
 * 
 * @author gao 2014-06-06
 *@version 1.0
 */
@SuppressWarnings("unused")
public class HYTXReportInitServlet extends HttpServlet {
	private final Logger log = Logger.getLogger(HYTXReportInitServlet.class);

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8805220166440472951L;

	
	private String contextPath;
	private String locale = null;

	/*
	 * 执行应用程序的初始化操作 1.字典数据的初始化，读取配置文件，参看web.xml配置 2. 3.
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() {
		String totalCalculationurl=Config.getProjectAccessPath()+"/calculateDataController/totalCalculationHYTX";
		String oilWellReportTotalUrl=Config.getProjectAccessPath()+"/HYTXDataInterfaceController/oilWellDailyReportTotal";
		String waterWellReportTotalUrl=Config.getProjectAccessPath()+"/HYTXDataInterfaceController/waterWellDailyReportTotal";
		String totalCalculationresult=StringManagerUtils.sendPostMethod(totalCalculationurl, "","utf-8");//发送产量汇总请求
		String oilWellReportTotalResult=StringManagerUtils.sendPostMethod(oilWellReportTotalUrl, "","utf-8");//发送油井日报表汇总请求
		String waterWellReportTotalResult=StringManagerUtils.sendPostMethod(waterWellReportTotalUrl, "","utf-8");//发送油井日报表汇总请求
	}

	
}
