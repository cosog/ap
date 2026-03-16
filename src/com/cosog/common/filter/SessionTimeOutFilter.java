package com.cosog.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.AntPathMatcher;

import com.cosog.controller.right.ExternalLoginController;
import com.cosog.model.User;
import com.cosog.utils.Config;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.StringManagerUtils;

/**
 * <p>描述：用户登录权限控制filter</p>
 * 
 * @author gao 2014-05-09
 * 
 */
public class SessionTimeOutFilter extends HttpServlet implements Filter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(SessionTimeOutFilter.class);

	@SuppressWarnings("unused")
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) resp;
	    
	    // 获取请求的 URI 或 ServletPath
	    String requestURI = request.getRequestURI();          // 例如：/ap/api/acq/id/group
	    String servletPath = request.getServletPath();        // 例如：/api/acq/id/group
	    String contextPath = request.getContextPath();			///例如：/ap
	    String fullPath = requestURI.substring(contextPath.length()); // 去掉上下文路径例如： /api/acq/id/group

	    // 定义不需要拦截的路径（可以是一个集合，支持通配符或正则）
	    // 这里以简单的字符串包含或精确匹配为例
	    AntPathMatcher matcher = new AntPathMatcher();
	    if (matcher.match("/api/**", servletPath)) {
	        // 直接放行，不执行后续的权限检查
	        chain.doFilter(request, response);
	        return;
	    }
	    
	 // 获取 token（从参数或 Header）
	    String token = request.getParameter("token");
	    if (token == null || token.isEmpty()) {
	        token = request.getHeader("X-Access-Token");
	    }

	    if (token != null && !token.isEmpty()) {
	        Map<String, ExternalLoginController.TokenInfo> tokenCache = ExternalLoginController.getTokenCache();
	        ExternalLoginController.TokenInfo tokenInfo = tokenCache.get(token);
	        if (tokenInfo != null && !tokenInfo.isExpired()) {
	            // token 有效，恢复 session
	            HttpSession session = request.getSession(true);
	            User user = tokenInfo.restoreUser(); // 直接获取存储的完整 User 对象
	            if (user != null) {
	                // 恢复所有必要的 session 属性（参考 ExternalTokenFilter 的 restoreSession 方法）
	                session.setAttribute("userLogin", user);
	                session.setAttribute("SESSION_USERNAME", user.getUserName());
	                session.setAttribute("browserLang", user.getLanguageName());
	                
	                // 权限相关属性（可选，因为 User 已包含，但某些旧代码可能直接读 session 属性）
	                session.setAttribute("orgtreeid", user.getOrgtreeid());
	                session.setAttribute("userParentOrgids", user.getUserParentOrgids());
	                session.setAttribute("userOrgIds", user.getUserOrgIds());
	                session.setAttribute("userOrgNames", user.getUserOrgNames());
	                session.setAttribute("allOrgPatentNodeIds", user.getAllOrgPatentNodeIds());
	                session.setAttribute("allModParentNodeIds", user.getAllModParentNodeIds());
	                session.setAttribute("deviceTypeIds", user.getDeviceTypeIds());
	                
	                // 恢复语言资源
	                session.setAttribute("languageResource", user.getLanguageResource());
	                session.setAttribute("languageResourceFirstLower", user.getLanguageResourceFirstLower());
	                
	                // 恢复配置信息
	                session.setAttribute("pageSize", user.getPageSize());
	                session.setAttribute("syncOrAsync", user.getSyncOrAsync());
	                session.setAttribute("defaultComboxSize", user.getDefaultComboxSize());
	                session.setAttribute("defaultGraghSize", user.getDefaultGraghSize());

	                session.setAttribute("tabInfo", tokenInfo.getTabInfo());
	                session.setAttribute("configFile", tokenInfo.getConfigFile());
	                session.setAttribute("oem", tokenInfo.getOem());
	                session.setAttribute("viewProjectName", tokenInfo.getViewProjectName());
	                session.setAttribute("favicon", tokenInfo.getFavicon());
	                session.setAttribute("bannerCSS", tokenInfo.getBannerCSS());
	                session.setAttribute("showLogo", tokenInfo.getShowLogo());
	                session.setAttribute("oemStaticResourceTimestamp", tokenInfo.getOemStaticResourceTimestamp());
	                session.setAttribute("otherStaticResourceTimestamp", tokenInfo.getOtherStaticResourceTimestamp());
	                session.setAttribute("loadingUI", tokenInfo.getLoadingUI());
	                session.setAttribute("helpDocumentUrl", tokenInfo.getHelpDocumentUrl());
	                session.setAttribute("showVideo", tokenInfo.getShowVideo());

	                // 设置 Locale
	                String locale = Config.getInstance().configFile.getAp().getOthers().getLoginLanguage();
	        		if(user!=null && StringManagerUtils.isNotNull(user.getLanguageName())){
	        			locale=user.getLanguageName();
	        		}
	                
	                Locale l = Locale.getDefault(); 
	        		if(locale==null){
	        			l = new Locale("zh", "CN"); 
	        		}else if (locale.equals("zh_CN")) { 
	        			l = new Locale("zh", "CN"); 
	        		} else if (locale.equals("en")) { 
	        			l = new Locale("en", "US"); 
	        		}
	                
	                session.setAttribute("WW_TRANS_I18N_LOCALE", locale);

	                log.info("通过 token 恢复 session 成功，用户: " + user.getUserName());
	            }
	            // 继续请求（此时 session 已恢复）
	            chain.doFilter(req, resp);
	            return;
	        } else {
	            // token 无效或过期，返回 401
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            response.setContentType("application/json;charset=utf-8");
	            response.getWriter().print("{\"success\":false,\"code\":401,\"message\":\"无效或过期的令牌\"}");
	            return;
	        }
	    }
		
	    
	    log.debug("I am a userLogin listener .....");
		HttpSession session = request.getSession(false);
		User user = (session != null) ? (User) session.getAttribute("userLogin") : null;
		
		
		
		String path = request.getServletPath();
		String loginUrl[] = path.split("\\/");
		Map<String, Object> license = DataModelMap.getMapObject();
		String uck = (String) license.get("license");
		// uck="God bless you!";
		log.debug("path==" + path + "user authorization==" + uck);
		// 国际区域
		String browserLang = null;
		String locale=null;
		locale = Config.getInstance().configFile.getAp().getOthers().getLoginLanguage();
		if(user!=null && StringManagerUtils.isNotNull(user.getLanguageName())){
			locale=user.getLanguageName();
		}
		Locale l = Locale.getDefault(); 
		if(locale==null){
			l = new Locale("zh", "CN"); 
		}else if (locale.equals("zh_CN")) { 
			l = new Locale("zh", "CN"); 
		} else if (locale.equals("en")) { 
			l = new Locale("en", "US"); 
		}
		String urlString = loginUrl[loginUrl.length - 1];
		if (uck.equalsIgnoreCase("God bless you!")) {
			if (!path.contains("Controller")){//只过滤Controller
				chain.doFilter(request, response);
			}else {
				//对外开放端口
				if(urlString.equals("toLogin")||urlString.equals("toMain")||urlString.equals("toBackMain")||urlString.equals("login")||urlString.equals("home")
						||urlString.equals("getUserList")||urlString.equals("userLogin")
						||urlString.equals("getBatchCalculateTime")
						||urlString.equals("getPCPBatchCalculateTime")
						||urlString.equals("totalCalculation")
						||urlString.equals("dailyCalculation")
						||urlString.indexOf("FESDiagramDailyCalculation")>=0
						||urlString.indexOf("SRPTimingTotalCalculation")>=0
						||urlString.indexOf("AcquisitionDataTimingTotalCalculation")>=0
						||urlString.indexOf("AcquisitionDataDailyCalculation")>=0
						||urlString.indexOf("initDailyReportData")>=0
						||urlString.indexOf("DiscreteDailyCalculation")>=0
						||urlString.indexOf("RPMDailyCalculation")>=0
						||urlString.indexOf("PCPTimingTotalCalculation")>=0
						||urlString.indexOf("PCPDiscreteDailyCalculation")>=0
						||urlString.indexOf("AcquisitionDataTimingRecord")>=0
						||urlString.equals("getBalanceTorqueCalulate")||urlString.equals("balanceTotalCalculation")
						||urlString.equals("saveRTUAcquisitionData")
						||urlString.indexOf("getOuterSurfaceCardData")>=0
						||urlString.equals("saveMQTTTransferElecDiscreteData")||urlString.equals("saveMQTTTransferElecDiagramData")||urlString.equals("saveMQTTTransferElecDailyData")
						||urlString.equals("saveKafkaUpData")
						||urlString.equals("saveKafkaUpRawData")
						||urlString.equals("saveKafkaUpRawWaterCut")
						||urlString.equals("saveKafkaUpAggrOnlineData")
						||urlString.equals("saveKafkaUpAggrRunStatusData")
						||urlString.equals("reTotalCalculation")
						||urlString.equals("pubSubModelCommCalculation")
						||urlString.equals("initProductionDataDictionary")
						||urlString.equals("getUIKitAccessToken")
						||path.contains("mobileController")
						){
					chain.doFilter(request, response);
				}else if (null == user) {
					response.sendError(999);
					response.setContentType("application/json;charset=utf-8");
					response.setHeader("Cache-Control", "no-cache");
					PrintWriter pw = response.getWriter();
					StringBuffer script = new StringBuffer();
					script.append("{\"success\":false,\"flag\":false}");
					response.getWriter().write(script.toString());
					pw.flush();
					pw.close();
				} else {
					chain.doFilter(request, response);
				}
			}
		} else {
			response.sendError(888);
			response.setContentType("application/json;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			StringBuffer script = new StringBuffer();
			script.append("{\"success\":false,\"flag\":\"unuck\"}");
			response.getWriter().write(script.toString());
			pw.flush();
			pw.close();
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		log.debug("I am a right listener init .....");
	}

}
