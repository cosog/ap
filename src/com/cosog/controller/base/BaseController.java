package com.cosog.controller.base;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.model.User;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.data.SystemdataInfoService;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.Page;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;

/**
 * <p>核心的BaseAction</p>
 * <p>Description: 油气生产物联网远程智能监控云平台</p>
 * <p> Created on 2014-5-8 </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: cosog</p>
 * 
 * @author gao
 * @version 1.0
 */
@Controller
@RequestMapping("/baseController")
@Scope("prototype")
public class BaseController extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware, ApplicationAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Map<String, Object> session;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String, Object> application;
	@Autowired
	private CommonDataService service;
	/**
	 * 导入service
	 */
	@Autowired
	private SystemdataInfoService systemdataInfoService;
	Gson gson = new Gson();
	protected Page pager = null;

	/**
	 * 调用登陆用户从session获取当前登录的用户信息(公共方法)
	 * 
	 * @param userid
	 * @return String
	 * @throws PTPECAppException
	 */
	public User findCurrentUserInfo() {
//		HttpServletRequest request = ServletActionContext.getRequest();
		Object obj = request.getSession().getAttribute("userLogin");
		// 返回当前登录用户信息
		return obj == null ? null : (User) obj;
	}

	public String findCurrentUserOrgIdInfo(String orgId) {
		User user = findCurrentUserInfo();
		Map<String, Object> map = DataModelMap.getMapObject();
		if (!StringManagerUtils.isNotNull(orgId)) {
			orgId = (String) map.get("userOrgId");
			if (user != null) {
				String curUser=user.getUserId();
				String	oldUserId = (String) map.get("oldUserId");
				if (!StringManagerUtils.isNotNull(orgId)||!oldUserId.equalsIgnoreCase(curUser)) {
					orgId = "" + user.getUserOrgid();
					orgId = this.systemdataInfoService.findCurrentUserOrgIdInfo(orgId,user.getLanguageName());
					map.remove("userOrgId");
					map.put("userOrgId", orgId);
					map.remove("oldUserId");
					map.put("oldUserId", curUser);
				}
			}
			if(!StringManagerUtils.isNotNull(orgId)){
				orgId="0";
			}
		}
		return orgId;
	}

	/**
	 * 解决无分页转一对多的转json的问题
	 * 
	 * @param oList
	 * @param version
	 *            ：只匹配和version一样的转换，不一样的踢出
	 * @return
	 */
	public String getArrayTojsonPage(List<?> oList) {
		String jsonString = "{\"start\":" + pager.getStart() + ",\"limit\":" + pager.getLimit() + ",\"totalCount\":" + pager.getTotalCount();
		if (null != oList && oList.size() > 0) {
			String jsonStr = "";
			try {
				jsonStr = gson.toJson(oList).toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
			jsonString += ",\"totalRoot\":" + jsonStr + "}";

		} else {
			jsonString += ",\"totalRoot\":[]}";
		}

		return jsonString;
	}
	
	public String getArrayTojsonPage(List<?> oList,String ddicCode) {
		String columns=	service.showTableHeadersColumns(ddicCode);
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{success:true,");
		strBuf.append(" \"start\":" + pager.getStart() + ",\"limit\":" + pager.getLimit() + ",columns:"+columns+",\"totalCount\":" + pager.getTotalCount());
		if (null != oList && oList.size() > 0) {
			String jsonStr = "";
			try {
				jsonStr = gson.toJson(oList).toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
			strBuf.append(",\"totalRoot\":" + jsonStr + "}");

		} else {
			strBuf.append(",\"totalRoot\":[]}");
		}

		return strBuf.toString();
	}
	
	public String getArrayTojsonPage2(List<?> oList,String ddicCode,Page pager) {
		String columns=	service.showTableHeadersColumns(ddicCode);
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{success:true,");
		strBuf.append(" \"start\":" + pager.getStart() + ",\"limit\":" + pager.getLimit() + ",columns:"+columns+",\"totalCount\":" + pager.getTotalCount());
		if (null != oList && oList.size() > 0) {
			String jsonStr = "";
			try {
				jsonStr = gson.toJson(oList).toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
			strBuf.append(",\"totalRoot\":" + jsonStr + "}");

		} else {
			strBuf.append(",\"totalRoot\":[]}");
		}

		return strBuf.toString();
	}
	
	
	public String getArrayTojsonPage(String data,String ddicCode) {
		String columns=	service.showTableHeadersColumns(ddicCode);
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{success:true,");
		strBuf.append("columns:"+columns);
		if (null != data) {
			String jsonStr = "";
			try {
				jsonStr = data;
			} catch (Exception e) {
				e.printStackTrace();
			}
			strBuf.append(",\"children\":" + jsonStr + "}");

		} else {
			strBuf.append(",\"children\":[]}");
		}

		return strBuf.toString();
	}
	public Page getPager() {
		return pager;
	}

	/**
	 * <p> 服务一开始启动就调用该方法，将数据字典信息放入缓存中</p>
	 * 
	 * @author gao 2014-05-08
	 * @return map
	 */
	public Map<String, Object> initDataDictionaryPutInCache() {
		return systemdataInfoService.initDataDictionaryPutInCache();
	}

	public void print(String name, String value) {
	}

	@Override
	public void setApplication(Map<String, Object> application) {
		// TODO Auto-generated method stub
		this.application = application;
	}

	public void setPager(Page pager) {
		this.pager = pager;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;

	}
	
	@ModelAttribute  
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
        this.request = request;  
        this.response = response;
    } 

}
