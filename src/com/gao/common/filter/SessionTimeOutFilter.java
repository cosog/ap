package com.gao.common.filter;

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

import com.gao.model.User;
import com.gao.utils.Config;
import com.gao.utils.DataModelMap;

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
		log.debug("I am a userLogin listener .....");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = null;
		session = request.getSession();
		String path = request.getServletPath();
		User user = (User) session.getAttribute("userLogin");
		String loginUrl[] = path.split("\\/");
		Map<String, Object> license = DataModelMap.getMapObject();
		String uck = (String) license.get("license");
		// uck="God bless you!";
		log.debug("path==" + path + "user authorization==" + uck);
		// 国际区域
		String browserLang = null;
		String locale=null;
		locale = Config.getLanguage();
		Locale l = Locale.getDefault(); 
		if(locale==null){ 
		l = new Locale("zh", "CN"); 
		}else if (locale.equals("zh_CN")) { 
		l = new Locale("zh", "CN"); 
		} else if (locale.equals("en")) { 
		l = new Locale("en", "US"); 
		} 
		//ActionContext.getContext().setLocale(l);   
		 request.getSession().setAttribute("WW_TRANS_I18N_LOCALE", l);
		   request.getSession().setAttribute("browserLang",locale);
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
//			if (urlString.equals("toLogin")
//					||urlString.equals("toBackMain")
//					||urlString.equals("toMain")
//					||urlString.equals("getUserList")||urlString.equals("userLogin")
//					||urlString.endsWith(".js")||urlString.endsWith(".css")||urlString.endsWith(".jsp")
//					||urlString.endsWith(".html")||urlString.endsWith(".htm")||urlString.endsWith(".dhtml")||urlString.endsWith(".xhtml")||urlString.endsWith(".shtml")||urlString.endsWith(".shtm")
//					||urlString.endsWith(".ico")||urlString.endsWith(".jpg")||urlString.endsWith(".png")||urlString.endsWith(".gif")
//					||urlString.endsWith(".jpeg")||urlString.endsWith(".svg")||urlString.endsWith(".bmp")
//					||urlString.endsWith(".json")
//					||urlString.equals("getBatchCalculateTime")||urlString.equals("totalCalculation")
//					||urlString.equals("getBalanceTorqueCalulate")||urlString.equals("balanceTotalCalculation")
//					||urlString.equals("saveRTUAcquisitionData")
//					){
//				chain.doFilter(request, response);
//			} 
			
			
			if (!path.contains("Controller")){//只过滤Controller
				chain.doFilter(request, response);
			}else {
				//对外开放端口
				if(urlString.equals("toLogin")||urlString.equals("toMain")||urlString.equals("toBackMain")
						||urlString.equals("getUserList")||urlString.equals("userLogin")
						||urlString.equals("getBatchCalculateTime")
						||urlString.equals("totalCalculation")
						||urlString.indexOf("FSDiagramDailyCalculation")>=0
						||urlString.indexOf("DiscreteDailyCalculation")>=0
						||urlString.indexOf("PCPRPMDailyCalculation")>=0
						||urlString.indexOf("PCPDiscreteDailyCalculation")>=0
						||urlString.equals("getBalanceTorqueCalulate")||urlString.equals("balanceTotalCalculation")
						||urlString.equals("saveRTUAcquisitionData")||urlString.equals("getOuterSurfaceCardData")
						||urlString.equals("saveMQTTTransferElecDiscreteData")||urlString.equals("saveMQTTTransferElecDiagramData")||urlString.equals("saveMQTTTransferElecDailyData")
						||urlString.equals("reTotalCalculation")
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
