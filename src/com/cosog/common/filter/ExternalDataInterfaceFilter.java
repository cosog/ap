package com.cosog.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.cosog.utils.DataModelMap;

/**
 * <p>描述：对外开放数据接口filter</p>
 * 
 * @author zhao 2016-08-16
 * 
 */
public class ExternalDataInterfaceFilter extends HttpServlet implements Filter{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(ExternalDataInterfaceFilter.class);

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.debug("I am ExternalDataInterfaceFilte .....");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = null;
		session = request.getSession();
		String path = request.getServletPath();
		User user = (User) session.getAttribute("userLogin");
		String loginUrl[] = path.split("\\/");
		Map<String, Object> license = DataModelMap.getMapObject();
		String uck = (String) license.get("license");
		
		String urlString = loginUrl[loginUrl.length - 1];
		if (uck.equalsIgnoreCase("God bless you!")) {
			if (urlString.equals("calculateData_getBatchCalculateTime.action")){
				chain.doFilter(request, response);
			} else {
				if (null == user) {
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
		log.debug("I am a ExternalDataInterface listener init .....");
	}

}
