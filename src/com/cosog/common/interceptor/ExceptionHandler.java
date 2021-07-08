package com.cosog.common.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import com.cosog.common.exception.BaseException;
import com.cosog.utils.Constants;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 
 * @author : Bless
 * @version : 1.00 Create Time : 2011-3-23-上午10:25:23 Description : 达运 异常拦截�?
 *          History �?Editor version Time Operation Description*
 * 
 */
public class ExceptionHandler extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation actioninvocation) {

		String result = null; // Action的返回�?
		try {
			// 运行被拦截的Action,期间如果发生异常会被catch�?
			result = actioninvocation.invoke();
			return result;
		} catch (Exception e) {
			/**
			 * 处理异常
			 */
			String errorMsg = "未知错误";

			if (e instanceof BaseException) {
				// OSPM异常
				BaseException be = (BaseException) e;
				be.printStackTrace();
				if (be.getMessage() != null
						|| Constants.BLANK.equals(be.getMessage().trim())) {
					errorMsg = be.getMessage().trim();
				}
			} else if (e instanceof RuntimeException) {
				// 未知的运行时异常
				RuntimeException re = (RuntimeException) e;
				re.printStackTrace();
			} else {
				// 未知的严重异�?
				e.printStackTrace();
			}

			HttpServletRequest request = (HttpServletRequest) actioninvocation
					.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);

			/**
			 * 发�?错误消息到页�?
			 */
			request.setAttribute("errorMsg", errorMsg);

			/**
			 * log4j记录日志
			 */
			Log log = LogFactory
					.getLog(actioninvocation.getAction().getClass());
			if (e.getCause() != null) {
				log.error(errorMsg, e);
			} else {
				log.error(errorMsg, e);
			}

			return "error";
		}// ...end of catch
	}
}
