package com.gao.controller.back;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.gao.model.Org;
import com.gao.model.User;
import com.gao.model.gridmodel.WellGridPanelData;
import com.gao.model.gridmodel.WellHandsontableChangedData;
import com.gao.model.WellInformation;
import com.gao.model.calculate.PCPCalculateResponseData;
import com.gao.model.drive.KafkaConfig;
import com.gao.service.back.WellInformationManagerService;
import com.gao.service.back.WellboreTrajectoryManagerService;
import com.gao.service.base.CommonDataService;
import com.gao.task.EquipmentDriverServerTask;
import com.gao.utils.Constants;
import com.gao.utils.Page;
import com.gao.utils.PagingConstants;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
		//HttpServletResponse response = ServletActionContext.getResponse();
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
		//HttpServletResponse response = ServletActionContext.getResponse();
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
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
//		log.warn("jh json is ==" + json);
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
