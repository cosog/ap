package com.gao.controller.monitor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.MonitorWaterInjectionWellParams;
import com.gao.model.User;
import com.gao.service.monitor.MonitorWaterInjectionWellParamsManagerService;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;

/**
 * @author gao
 * 
 */
@Controller
@RequestMapping("/monitorWaterInjectionWellParamsManagerController")
@Scope("prototype")
public class MonitorWaterInjectionWellParamsManagerController extends BaseController {

	private static Log log = LogFactory
			.getLog(MonitorWaterInjectionWellParamsManagerController.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private MonitorWaterInjectionWellParamsManagerService<MonitorWaterInjectionWellParams> service;
	private String orgId;



	public String getOrgId() {
		return orgId;
	}



	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}


	@RequestMapping("/queryMonitorWaterParams")
	public String queryMonitorWaterParams() throws Exception {
		String jh = ParamUtils.getParameter(request, "jh");
		String bjlx = ParamUtils.getParameter(request, "bjlx");
		String type = ParamUtils.getParameter(request, "type");
		orgId=ParamUtils.getParameter(request, "orgId");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = ""+user.getUserorgids();
			}
		}
		String json = this.service.loadMonitorWaterInjectionParams(orgId, jh,
				bjlx, type);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh loadMonitorWaterInjectionParams is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	@RequestMapping("/queryMonitorWaterHistoryParams")
	public String queryMonitorWaterHistoryParams() throws Exception {
		String jh = ParamUtils.getParameter(request, "jh");
		String bjlx = ParamUtils.getParameter(request, "bjlx");
		String type = ParamUtils.getParameter(request, "type");
		orgId=ParamUtils.getParameter(request, "orgId");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = ""+user.getUserorgids();
			}
		}
		String json = this.service.loadMonitorWaterInjectionHistoryParams(orgId, jh,
				bjlx, type);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh queryMonitorWaterHistoryParams is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
}
