package com.cosog.utils;

/**
 * <p>Title: Science Management Project</p>
 * <p>Description: Science Management Project</p>
 * <p>Created on 2008-8-26</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: JamieSoft </p>
 * @author Jamie Xu
 * @version 1.0
 */


import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.*;

public class ParamUtils {

	protected final static Logger logger = Logger.getLogger(ParamUtils.class);

	public static String getParameter(HttpServletRequest request,
			String paramName) {
		String temp = request.getParameter(paramName);
		if (temp != null && !temp.equals("")) {
			return StringUtil.convertSingleQuot(temp);
		} else {
			return "";
		}
	}

	/**
	 * genarate escape sequance in html for special str
	 * 
	 * @see HTML 4.01 Specification 5.3.2 Character entity references
	 */
	public static String escape4html(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '"')
				sb.append("&quot;");
			else if (c == '<')
				sb.append("&lt;");
			else if (c == '>')
				sb.append("&gt;");
			else
				sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * genarate escape sequance in javascript for special str
	 * 
	 * @see JavaScript Language 1.1 Specification 2.7.5 Escape Sequences for
	 *      String Literals
	 */
	public static String escape4js(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '/')
				sb.append("///'");

			else if (c == '"')
				sb.append("///");
			else
				sb.append(c);
		}
		return sb.toString();
	}

	public static String getSelectParameter(HttpServletRequest request,
			String paramName) {
		String[] temp = request.getParameterValues(paramName);
		String temp1 = "";
		if (temp != null && !temp.equals("")) {
			for (int i = 0; i < temp.length; i++)
				temp1 += temp[i] + ",";
			return temp1;
		} else {
			return null;
		}
	}

	public static String getParameter(HttpServletRequest request,
			String paramName, boolean emptyStringsOK) {
		String temp = request.getParameter(paramName);
		if (emptyStringsOK) {
			if (temp != null) {
				return temp;
			} else {
				return null;
			}
		} else {
			return getParameter(request, paramName);
		}
	}

	public static boolean getBooleanParameter(HttpServletRequest request,
			String paramName) {
		String temp = request.getParameter(paramName);
		if (temp != null && temp.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	public static int getIntParameter(HttpServletRequest request,
			String paramName, int defaultNum) {
		String temp = request.getParameter(paramName);
		if (temp != null && !temp.equals("")) {
			int num = defaultNum;
			try {
				num = Integer.parseInt(temp);
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	public static Integer getIntegerParameter(HttpServletRequest request,
			String paramName, Integer defaultNum) {
		String temp = request.getParameter(paramName);
		if (temp != null && !temp.equals("")) {
			Integer num = defaultNum;
			try {
				num = new Integer(temp);
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	public static boolean getCheckboxParameter(HttpServletRequest request,
			String paramName) {
		String temp = request.getParameter(paramName);
		if (temp != null && temp.equals("on")) {
			return true;
		} else {
			return false;
		}
	}

	public static String getAttribute(HttpServletRequest request,
			String attribName) {
		String temp = (String) request.getAttribute(attribName);
		if (temp != null && !temp.equals("")) {
			return temp;
		} else {
			return null;
		}
	}
	

	public static boolean getBooleanAttribute(HttpServletRequest request,
			String attribName) {
		String temp = (String) request.getAttribute(attribName);
		if (temp != null && temp.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	public static int getIntAttribute(HttpServletRequest request,
			String attribName, int defaultNum) {
		String temp = (String) request.getAttribute(attribName);
		if (temp != null && !temp.equals("")) {
			int num = defaultNum;
			try {
				num = Integer.parseInt(temp);
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	public static int[] getIntListboxParams(String[] paramVal) {
		if (paramVal == null) {
			return new int[0];
		}
		int[] params = new int[paramVal.length];
		for (int i = 0; i < paramVal.length; i++) {
			try {
				params[i] = Integer.parseInt(paramVal[i]);
			} catch (NumberFormatException nfe) {
			}
		}
		return params;
	}
}
