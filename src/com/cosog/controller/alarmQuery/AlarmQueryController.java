package com.cosog.controller.alarmQuery;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.controller.realTimeMonitoring.RealTimeMonitoringController;
import com.cosog.model.User;
import com.cosog.service.alarmQuery.AlarmQueryService;
import com.cosog.service.base.CommonDataService;
import com.cosog.utils.Constants;
import com.cosog.utils.Page;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;

@Controller
@RequestMapping("/alarmQueryController")
@Scope("prototype")
public class AlarmQueryController extends BaseController{

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(RealTimeMonitoringController.class);
	@Autowired
	private AlarmQueryService<?> alarmQueryService;
	@Autowired
	private CommonDataService service;
	
	private String limit;
	private String msg = "";
	private String deviceType;
	private String operationType;
	private String page;
	private String orgId;
	private String startDate;
	private String endDate;
	private String alarmType;
	private String alarmLevel;
	private String isSendMessage;
	private int totals;
	
	@RequestMapping("/getAlarmData")
	public String getAlarmData() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		alarmType = ParamUtils.getParameter(request, "alarmType");
		alarmLevel = ParamUtils.getParameter(request, "alarmLevel");
		isSendMessage = ParamUtils.getParameter(request, "isSendMessage");
		startDate = ParamUtils.getParameter(request, "startDate");
		endDate = ParamUtils.getParameter(request, "endDate");
		this.pager = new Page("pagerForm", request);
		
		String tableName="viw_rpcalarminfo_hist";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			tableName="viw_pcpalarminfo_hist";
		}
		if(!StringManagerUtils.isNotNull(orgId)){
			User user=null;
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId=user.getUserorgids();
			}
		}
		if(!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.alarmtime),'yyyy-mm-dd hh24:mi:ss') from "+tableName+" t where  t.alarmType="+alarmType;
			List list = this.service.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			}
			if(!StringManagerUtils.isNotNull(startDate)){
				startDate=endDate.split(" ")[0]+" 00:00:00";
			}
		}
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		json = alarmQueryService.getAlarmData(orgId,deviceType,deviceId,deviceName,alarmType,alarmLevel,isSendMessage,pager);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportAlarmData")
	public String exportAlarmData() throws Exception {
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String deviceName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceName"),"utf-8");
		alarmType = ParamUtils.getParameter(request, "alarmType");
		alarmLevel = ParamUtils.getParameter(request, "alarmLevel");
		isSendMessage = ParamUtils.getParameter(request, "isSendMessage");
		startDate = ParamUtils.getParameter(request, "startDate");
		endDate = ParamUtils.getParameter(request, "endDate");
		
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		
		this.pager = new Page("pagerForm", request);
		String tableName="viw_rpcalarminfo_hist";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			tableName="viw_pcpalarminfo_hist";
		}
		if(!StringManagerUtils.isNotNull(orgId)){
			User user=null;
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId=user.getUserorgids();
			}
		}
		if(!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.alarmtime),'yyyy-mm-dd hh24:mi:ss') from "+tableName+" t where  t.alarmType="+alarmType;
			List list = this.service.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			}
			if(!StringManagerUtils.isNotNull(startDate)){
				startDate=endDate.split(" ")[0]+" 00:00:00";
			}
		}
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		boolean bool = alarmQueryService.exportAlarmData(response,fileName,title, heads, fields,orgId,deviceType,deviceId,deviceName,alarmType,alarmLevel,isSendMessage,pager);
		return null;
	}
	
	@RequestMapping("/getAlarmOverviewData")
	public String getAlarmOverviewData() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		alarmType = ParamUtils.getParameter(request, "alarmType");
		alarmLevel = ParamUtils.getParameter(request, "alarmLevel");
		isSendMessage = ParamUtils.getParameter(request, "isSendMessage");
		this.pager = new Page("pagerForm", request);
		if(!StringManagerUtils.isNotNull(orgId)){
			User user=null;
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId=user.getUserorgids();
			}
		}
		json = alarmQueryService.getAlarmOverviewData(orgId,deviceType,deviceName,alarmType,alarmLevel,isSendMessage,pager);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportAlarmOverviewData")
	public String exportAlarmOverviewData() throws Exception {
		String json = "";
		String result="{\"success\":true}";
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		String deviceName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceName"),"utf-8");
		alarmType = ParamUtils.getParameter(request, "alarmType");
		alarmLevel = ParamUtils.getParameter(request, "alarmLevel");
		isSendMessage = ParamUtils.getParameter(request, "isSendMessage");
		
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		
		this.pager = new Page("pagerForm", request);
		if(!StringManagerUtils.isNotNull(orgId)){
			User user=null;
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId=user.getUserorgids();
			}
		}
		boolean bool = alarmQueryService.exportAlarmOverviewData(response,fileName,title, heads, fields,orgId,deviceType,deviceName,alarmType,alarmLevel,isSendMessage,pager);
		return null;
	}
	
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getTotals() {
		return totals;
	}
	public void setTotals(int totals) {
		this.totals = totals;
	}


	public String getOperationType() {
		return operationType;
	}


	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}



	public String getAlarmLevel() {
		return alarmLevel;
	}



	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public String getIsSendMessage() {
		return isSendMessage;
	}

	public void setIsSendMessage(String isSendMessage) {
		this.isSendMessage = isSendMessage;
	}
	
}
