package com.cosog.controller.monitor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.model.MonitorPumpingUnitParams;
import com.cosog.model.User;
import com.cosog.service.monitor.MonitorPumpingUnitParamsManagerService;
import com.cosog.utils.Page;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;

/**
 * <p>
 * 采出井监测数据下拉菜单数据信Action
 * </p>
 * 
 * @author gao 2014-06-03
 * 
 */
@Controller
@RequestMapping("/monitorPumpingUnitParamsManagerController")
@Scope("prototype")
public class MonitorPumpingUnitParamsManagerController extends BaseController {

	private static Log log = LogFactory.getLog(MonitorPumpingUnitParamsManagerController.class);
	private static final long serialVersionUID = 1L;
	@Autowired
	private MonitorPumpingUnitParamsManagerService<MonitorPumpingUnitParams> service;
	private String orgId;
	private String jhh;
	private String jh;
	private String bjlx;
	
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getJhh() {
		return jhh;
	}

	public void setJhh(String jhh) {
		this.jhh = jhh;
	}

	public String getJh() {
		return jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	public String getBjlx() {
		return bjlx;
	}

	public void setBjlx(String bjlx) {
		this.bjlx = bjlx;
	}

	/**
	 * <p>
	 * 描述：实现采出井历史查询下拉菜单多级联动方法
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryMonitorHistoryParams")
	public String queryMonitorHistoryParams() throws Exception {

//		String jhh = ParamUtils.getParameter(request, "jhh");
//		String jh = ParamUtils.getParameter(request, "jh");
//		String bjlx = ParamUtils.getParameter(request, "bjlx");
		String type = ParamUtils.getParameter(request, "type");
		orgId = ParamUtils.getParameter(request, "orgId");
		orgId=this.findCurrentUserOrgIdInfo(orgId);
		String json = this.service.loadMonitorHistoryParams(orgId, jhh, jh, bjlx, type);
//		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
//		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}

	
}
