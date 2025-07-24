package com.cosog.controller.logQuery;

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
import com.cosog.service.base.CommonDataService;
import com.cosog.service.logQuery.LogQueryService;
import com.cosog.utils.Constants;
import com.cosog.utils.Page;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;

@Controller
@RequestMapping("/logQueryController")
@Scope("prototype")
public class LogQueryController extends BaseController{

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(RealTimeMonitoringController.class);
	@Autowired
	private LogQueryService<?> logQueryService;
	@Autowired
	private CommonDataService service;
	
	private String limit;
	private String msg = "";
	private String wellName;
	private String deviceName;
	private String deviceType;
	private String operationType;
	private String page;
	private String orgId;
	private String startDate;
	private String endDate;
	private int totals;
	
	@RequestMapping("/getDeviceOperationLogData")
	public String getDeviceOperationLogData() throws Exception {
		String json = "";
		HttpSession session=request.getSession();
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		deviceName = ParamUtils.getParameter(request, "deviceName");
		operationType = ParamUtils.getParameter(request, "operationType");
		startDate = ParamUtils.getParameter(request, "startDate");
		endDate = ParamUtils.getParameter(request, "endDate");
		String dictDeviceType=ParamUtils.getParameter(request, "dictDeviceType");
		this.pager = new Page("pagerForm", request);
		User user = (User) session.getAttribute("userLogin");
		if(!StringManagerUtils.isNotNull(orgId)){
			if (user != null) {
				orgId = "" + user.getUserOrgIds();
				if(user.getUserOrgid()==0){
					orgId+=",0";
				}
			}
		}
		
		if(!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.createtime)+1/(24*60),'yyyy-mm-dd hh24:mi:ss') from viw_deviceoperationlog t  where t.orgid in ("+orgId+")";
			if(StringManagerUtils.isNotNull(deviceType)){
				if(StringManagerUtils.isNum(deviceType)){
					sql+= " and t.devicetype="+deviceType;
				}else{
					sql+= " and t.devicetype in ("+deviceType+")";
				}
			}
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and t.deviceName='"+deviceName+"'";
			}
			if(StringManagerUtils.isNotNull(operationType)){
				sql+=" and t.action="+operationType;
			}
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
		json = logQueryService.getDeviceOperationLogData(orgId,deviceType,deviceName,operationType,pager,dictDeviceType,user);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportDeviceOperationLogExcelData")
	public String exportDeviceOperationLogExcelData() throws Exception {
		HttpSession session=request.getSession();
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		String dictDeviceType=ParamUtils.getParameter(request, "dictDeviceType");
		deviceName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceName"),"utf-8");
		operationType = ParamUtils.getParameter(request, "operationType");
		startDate = ParamUtils.getParameter(request, "startDate");
		endDate = ParamUtils.getParameter(request, "endDate");
		
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		String key = ParamUtils.getParameter(request, "key");
		
		if(session!=null){
			session.removeAttribute(key);
			session.setAttribute(key, 0);
		}
		this.pager = new Page("pagerForm", request);
		User user = (User) session.getAttribute("userLogin");
		if(!StringManagerUtils.isNotNull(orgId)){
			if (user != null) {
				orgId = "" + user.getUserOrgIds();
				if(user.getUserOrgid()==0){
					orgId+=",0";
				}
			}
		}
		
		
		if(!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.createtime)+1/(24*60),'yyyy-mm-dd hh24:mi:ss') from viw_deviceoperationlog t  where t.orgid in ("+orgId+")";
			if(StringManagerUtils.isNotNull(deviceType)){
				if(StringManagerUtils.isNum(deviceType)){
					sql+= " and t.devicetype="+deviceType;
				}else{
					sql+= " and t.devicetype in ("+deviceType+")";
				}
			}
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and t.deviceName='"+deviceName+"'";
			}
			if(StringManagerUtils.isNotNull(operationType)){
				sql+=" and t.action="+operationType;
			}
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
		boolean bool = logQueryService.exportDeviceOperationLogData(response,fileName,title, heads, fields,orgId,deviceType,dictDeviceType,deviceName,operationType,pager,user);
		if(session!=null){
			session.setAttribute(key, 1);
		}
		return null;
	}
	
	@RequestMapping("/getSystemLogData")
	public String getSystemLogData() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		operationType = ParamUtils.getParameter(request, "operationType");
		startDate = ParamUtils.getParameter(request, "startDate");
		endDate = ParamUtils.getParameter(request, "endDate");
		String dictDeviceType=ParamUtils.getParameter(request, "dictDeviceType");
		String selectUserId = ParamUtils.getParameter(request, "selectUserId");
		this.pager = new Page("pagerForm", request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if (user != null) {
			language = "" + user.getLanguageName();
		}
		if(!StringManagerUtils.isNotNull(orgId)){
			if (user != null) {
				orgId = "" + user.getUserOrgIds();
				if(user.getUserOrgid()==0){
					orgId+=",0";
				}
			}
		}
		
		if(!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.createtime)+1/(24*60),'yyyy-mm-dd hh24:mi:ss') from tbl_systemlog t ";
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
		json = logQueryService.getSystemLogData(orgId,operationType,pager,user,selectUserId,dictDeviceType,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportSystemLogExcelData")
	public String exportSystemLogExcelData() throws Exception {
		HttpSession session=request.getSession();
		orgId = ParamUtils.getParameter(request, "orgId");
		startDate = ParamUtils.getParameter(request, "startDate");
		endDate = ParamUtils.getParameter(request, "endDate");
		
		String selectUserId = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "selectUserId"),"utf-8");
		String operationType = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "operationType"),"utf-8");
		
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		String key = ParamUtils.getParameter(request, "key");
		
		if(session!=null){
			session.removeAttribute(key);
			session.setAttribute(key, 0);
		}
		this.pager = new Page("pagerForm", request);
		User user = (User) session.getAttribute("userLogin");
		if(!StringManagerUtils.isNotNull(orgId)){
			if (user != null) {
				orgId = "" + user.getUserOrgIds();
				if(user.getUserOrgid()==0){
					orgId+=",0";
				}
			}
		}
		if(!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.createtime)+1/(24*60),'yyyy-mm-dd hh24:mi:ss') from tbl_systemlog t ";
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
		boolean bool = logQueryService.exportSystemLogData(response,fileName,title, heads, fields,orgId,operationType,pager,user,selectUserId);
		if(session!=null){
			session.setAttribute(key, 1);
		}
		return null;
	}
	
	@RequestMapping("/saveAccessModuleLog")
	public String saveAccessModuleLog() throws Exception {
		HttpSession session=request.getSession();
		if(session!=null){
			User user = (User) session.getAttribute("userLogin");
			String moduleCode = ParamUtils.getParameter(request, "moduleCode");
			String moduleName = ParamUtils.getParameter(request, "moduleName");
			if(user!=null && StringManagerUtils.isNotNull(moduleName)){
				try {
					logQueryService.saveSystemLog(user, 3, moduleName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print("{\"success\":true}");
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/loadSystemLogActionComboxList")
	public String loadSystemLogActionComboxList() throws Exception {
		this.pager=new Page("pageForm",request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if (user != null) {
			language = "" + user.getLanguageName();
		}
		String json = this.logQueryService.loadSystemLogActionComboxList(language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
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
	public String getWellName() {
		return wellName;
	}
	public void setWellName(String wellName) {
		this.wellName = wellName;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
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
}
