package com.cosog.controller.right;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.model.DiagnosisAnalysisOnly;
import com.cosog.model.DiagnosisAnalysisStatistics;
import com.cosog.model.User;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.diagnosis.DiagnosisAnalysisOnlyService;
import com.cosog.utils.Config;
import com.cosog.utils.ConfigFile;
import com.cosog.utils.I18NConfig;
import com.cosog.utils.LicenseMap;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.Page;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.UnixPwdCrypt;
import com.google.gson.Gson;

/**
 * 工况诊断 （单张功图诊断分析）- action类
 * 
 * @author gao 2014-05-09
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/login")
@Scope("prototype")
public class PageTurnController extends BaseController {
	private static final long serialVersionUID = 1L;
	@RequestMapping("/showIndex")
	public String showIndex() throws Exception {
		return "app/page/home";
	}
	@RequestMapping("/showBackIndex")
	public String showBackIndex() throws Exception {
		return "app/page/backHome";
	}
	@RequestMapping("/toLogin")
	public String toLogin() throws Exception {
		Gson gson=new Gson();
		ConfigFile configFile=Config.getInstance().configFile;
		response.setContentType("text/html;charset=utf-8");
		HttpSession session=request.getSession();
		session.setAttribute("dataSourceSN", LicenseMap.dataSourceSN);
		session.setAttribute("pcp", LicenseMap.pcp);
		session.setAttribute("rawWaterCut", LicenseMap.rawWaterCut);
		session.setAttribute("dynamicCurve", LicenseMap.dynamicCurve);
		session.setAttribute("electricalHidden", LicenseMap.electricalHidden);
		session.setAttribute("configFile", gson.toJson(configFile));
		session.setAttribute("viewInformation", gson.toJson(configFile.getViewInformation()));
		session.setAttribute("viewProjectName", configFile.getViewInformation().getTitle());
		return "Login";
	}
	@RequestMapping("/toTouchLogin")
	public String toTouchLogin() throws Exception {
		return "touchLogin";
	}
	@RequestMapping("/toMain")
	public String toMain() throws Exception {
		Gson gson=new Gson();
		ConfigFile configFile=Config.getInstance().configFile;
		response.setContentType("text/html;charset=utf-8");
		HttpSession session=request.getSession();
		session.setAttribute("dataSourceSN", LicenseMap.dataSourceSN);
		session.setAttribute("pcp", LicenseMap.pcp);
		session.setAttribute("rawWaterCut", LicenseMap.rawWaterCut);
		session.setAttribute("dynamicCurve", LicenseMap.dynamicCurve);
		session.setAttribute("electricalHidden", LicenseMap.electricalHidden);
		session.setAttribute("configFile", gson.toJson(configFile));
		session.setAttribute("viewInformation", gson.toJson(configFile.getViewInformation()));
		session.setAttribute("viewProjectName", configFile.getViewInformation().getTitle());
		return "app/main";
	}
	@RequestMapping("/toTouchMain")
	public String toTouchMain() throws Exception {
		return "toTouchMain";
	}
	@RequestMapping("/toBackLogin")
	public String toBackLogin() throws Exception {
		return "AdminLogin";
	}
	@RequestMapping("/toBackMain")
	public String toBackMain() throws Exception {
		return "app/back";
	}
}
