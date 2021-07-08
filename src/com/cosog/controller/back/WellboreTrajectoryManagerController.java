package com.cosog.controller.back;

import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.model.User;
import com.cosog.service.back.WellboreTrajectoryManagerService;
import com.cosog.service.base.CommonDataService;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;

@Controller
@RequestMapping("/wellboreTrajectoryManagerController")
@Scope("prototype")
public class WellboreTrajectoryManagerController extends BaseController {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(WellboreTrajectoryManagerController.class);
	@Autowired
	private WellboreTrajectoryManagerService<?> wellboreTrajectoryManagerService;
	@Autowired
	private CommonDataService service;
	private String wellName;
	private String page;
	private String orgId;
	
	@RequestMapping("/getWellboreTrajectoryList")
	public String getWellboreTrajectoryList() throws Exception {
		orgId=ParamUtils.getParameter(request, "orgId");
		wellName=ParamUtils.getParameter(request, "wellName");
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String json = wellboreTrajectoryManagerService.getWellboreTrajectoryList(orgId,wellName);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getWellboreTrajectoryDetailsData")
	public String getWellboreTrajectoryDetailsData() throws Exception {
		orgId=ParamUtils.getParameter(request, "orgId");
		wellName=ParamUtils.getParameter(request, "wellName");
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String json = wellboreTrajectoryManagerService.getWellboreTrajectoryDetailsData(wellName);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveWellboreTrajectoryData")
	public String saveWellboreTrajectoryData() throws Exception {
		HttpSession session=request.getSession();
		String wellName = ParamUtils.getParameter(request, "wellName");
		String wellboreTrajectoryData = ParamUtils.getParameter(request, "wellboreTrajectoryData");
		String json = this.wellboreTrajectoryManagerService.saveWellboreTrajectoryData(wellName,wellboreTrajectoryData);
		this.wellboreTrajectoryManagerService.downKafkaWellboreTrajectoryData(wellName,wellboreTrajectoryData);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	public String getWellName() {
		return wellName;
	}
	public void setWellName(String wellName) {
		this.wellName = wellName;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}
