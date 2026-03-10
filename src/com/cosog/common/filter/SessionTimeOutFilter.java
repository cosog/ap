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
	    
	    // 获取请求的来源
        String origin = request.getHeader("Origin");
	    
	    if (origin != null && !origin.isEmpty()) {
            // 关键：必须设置为具体的 origin，不能是 *
            response.setHeader("Access-Control-Allow-Origin", origin);
            // 允许携带凭证（cookie/session）
            response.setHeader("Access-Control-Allow-Credentials", "true");
            // 允许的 HTTP 方法
            response.setHeader("Access-Control-Allow-Methods", 
                "POST, GET, OPTIONS, DELETE, PUT");
            // 允许的请求头
            response.setHeader("Access-Control-Allow-Headers", 
                "x-requested-with, Content-Type, Accept, Authorization, Cookie");
            // 暴露的响应头（如果需要前端读取 cookie）
            response.setHeader("Access-Control-Expose-Headers", 
                "Set-Cookie, Cookie");
            // 预检请求缓存时间（1小时）
            response.setHeader("Access-Control-Max-Age", "3600");
        }
        
        // 处理 OPTIONS 预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        
		
		log.debug("I am a userLogin listener .....");
		HttpSession session = request.getSession();
		String path = request.getServletPath();
		
		
		// 如果是外部系统访问且携带Token，优先验证Token
	    String token = request.getParameter("token");
	    if (token != null && !token.isEmpty()) {
	        // Token验证由ExternalTokenFilter处理，这里跳过
	        chain.doFilter(req, resp);
	        return;
	    }
		
		User user = (User) session.getAttribute("userLogin");
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
		//ActionContext.getContext().setLocale(l);   
//		request.getSession().setAttribute("WW_TRANS_I18N_LOCALE", l);
//		request.getSession().setAttribute("browserLang",locale);
//		// 取会话中的语言版本
//		browserLang = (String) request.getSession().getAttribute("locale");
//		// 如果会话中没有指定，则取浏览器的语言版本
//		if(browserLang==null){
//			browserLang=locale;
//		}else if(locale==null){
//			browserLang = request.getLocale().toString();
//		}
//		request.setAttribute("locale", browserLang);
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
					script.append("{success:false,flag:false}");
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
			script.append("{success:false,flag:'unuck'}");
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
