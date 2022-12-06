package com.cosog.controller.historyQuery;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.controller.realTimeMonitoring.RealTimeMonitoringController;
import com.cosog.model.User;
import com.cosog.model.data.DataDictionary;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.data.DataitemsInfoService;
import com.cosog.service.historyQuery.HistoryQueryService;
import com.cosog.utils.Constants;
import com.cosog.utils.Page;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;

@Controller
@RequestMapping("/historyQueryController")
@Scope("prototype")
public class HistoryQueryController extends BaseController  {

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(RealTimeMonitoringController.class);
	@Autowired
	private HistoryQueryService<?> historyQueryService;
	@Autowired
	private CommonDataService service;
	@Autowired
	private DataitemsInfoService dataitemsInfoService;
	private String limit;
	private String msg = "";
	private String deviceType;
	private String FESdiagramResultStatValue;
	private String commStatusStatValue;
	private String runStatusStatValue;
	private String deviceTypeStatValue;
	private String page;
	private String orgId;
	private String startDate;
	private String endDate;
	private int totals;
	
	@RequestMapping("/getHistoryQueryCommStatusStatData")
	public String getHistoryQueryCommStatusStatData() throws Exception {
		String json = "";
		
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		deviceTypeStatValue = ParamUtils.getParameter(request, "deviceTypeStatValue");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		json = historyQueryService.getHistoryQueryCommStatusStatData(orgId,deviceType,deviceTypeStatValue);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getHistoryQueryDeviceTypeStatData")
	public String getHistoryQueryDeviceTypeStatData() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		commStatusStatValue = ParamUtils.getParameter(request, "commStatusStatValue");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		json = historyQueryService.getHistoryQueryDeviceTypeStatData(orgId,deviceType,commStatusStatValue);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getHistoryQueryDeviceListDataPage")
	public String getHistoryQueryDeviceListDataPage() throws Exception {
		String json = "";
		int dataPage=1;
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		FESdiagramResultStatValue = ParamUtils.getParameter(request, "FESdiagramResultStatValue");
		commStatusStatValue = ParamUtils.getParameter(request, "commStatusStatValue");
		runStatusStatValue = ParamUtils.getParameter(request, "runStatusStatValue");
		deviceTypeStatValue = ParamUtils.getParameter(request, "deviceTypeStatValue");
		limit = ParamUtils.getParameter(request, "limit");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		dataPage = historyQueryService.getHistoryQueryDeviceListDataPage(orgId,deviceId,deviceName,deviceType,FESdiagramResultStatValue,commStatusStatValue,runStatusStatValue,deviceTypeStatValue,limit);
		json="{\"success\":true,\"dataPage\":"+dataPage+"}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getHistoryQueryDeviceList")
	public String getHistoryQueryDeviceList() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		FESdiagramResultStatValue = ParamUtils.getParameter(request, "FESdiagramResultStatValue");
		commStatusStatValue = ParamUtils.getParameter(request, "commStatusStatValue");
		runStatusStatValue = ParamUtils.getParameter(request, "runStatusStatValue");
		deviceTypeStatValue = ParamUtils.getParameter(request, "deviceTypeStatValue");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		json = historyQueryService.getHistoryQueryDeviceList(orgId,deviceName,deviceType,FESdiagramResultStatValue,commStatusStatValue,runStatusStatValue,deviceTypeStatValue,pager);
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
	
	@RequestMapping("/exportHistoryQueryDeviceListExcel")
	public String exportHistoryQueryDeviceListExcel() throws Exception {
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceName"),"utf-8");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		FESdiagramResultStatValue = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "FESdiagramResultStatValue"),"utf-8");
		commStatusStatValue = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "commStatusStatValue"),"utf-8");
		runStatusStatValue = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "runStatusStatValue"),"utf-8");
		deviceTypeStatValue = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceTypeStatValue"),"utf-8");
		
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		
		boolean bool = historyQueryService.exportHistoryQueryDeviceListData(response,fileName,title, heads, fields,orgId,deviceName,deviceType,FESdiagramResultStatValue,commStatusStatValue,runStatusStatValue,deviceTypeStatValue,pager);
		return null;
	}
	
	@RequestMapping("/getDeviceHistoryData")
	public String getDeviceHistoryData() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		startDate = ParamUtils.getParameter(request, "startDate");
		endDate = ParamUtils.getParameter(request, "endDate");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		
		String tableName="tbl_rpcacqdata_hist";
		String deviceTableName="tbl_rpcdevice";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			tableName="tbl_pcpacqdata_hist";
			deviceTableName="tbl_pcpdevice";
		}
		if(StringManagerUtils.isNotNull(deviceId)&&!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.acqTime),'yyyy-mm-dd hh24:mi:ss') from "+tableName+" t where t.wellId= "+deviceId;
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
		if(StringManagerUtils.stringToInteger(deviceType)==0){
			json = historyQueryService.getDeviceHistoryData(orgId,deviceId,deviceName,deviceType,pager);
		}else{
			json = historyQueryService.getPCPDeviceHistoryData(orgId,deviceId,deviceName,deviceType,pager);
		}
		
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
	
	@RequestMapping("/exportHistoryQueryDataExcel")
	public String exportHistoryQueryDataExcel() throws Exception {
		String json="{\"success\":true,\"flag\":true}";
		boolean bool=false;
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceName"),"utf-8");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		startDate = ParamUtils.getParameter(request, "startDate");
		endDate = ParamUtils.getParameter(request, "endDate");
		
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		
		DataDictionary ddic = null;
		String ddicName="historyQuery_RPCHistoryData";
		if(StringManagerUtils.stringToInteger(deviceType)!=0){
			ddicName="historyQuery_PCPHistoryData";
		}
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		heads=StringUtils.join(ddic.getHeaders(), ",");
		fields=StringUtils.join(ddic.getFields(), ",");
		
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		
		String tableName="tbl_rpcacqdata_hist";
		String deviceTableName="tbl_rpcdevice";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			tableName="tbl_pcpacqdata_hist";
			deviceTableName="tbl_pcpdevice";
		}
		if(StringManagerUtils.isNotNull(deviceId)&&!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.acqTime),'yyyy-mm-dd hh24:mi:ss') from "+tableName+" t where t.wellId="+deviceId;
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
		
		if(StringManagerUtils.stringToInteger(deviceType)==0){
			bool = historyQueryService.exportDeviceHistoryData(response,fileName,title, heads, fields,orgId,deviceId,deviceName,deviceType,pager);
			
		}else{
			bool = historyQueryService.exportPCPDeviceHistoryData(response,fileName,title, heads, fields,orgId,deviceId,deviceName,deviceType,pager);
		}
		if(!bool){
			json="{\"success\":true,\"flag\":false}";
		}
		
//		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
//		response.setHeader("Cache-Control", "no-cache");
//		PrintWriter pw = response.getWriter();
//		pw.print(json);
//		pw.flush();
//		pw.close();
		return null;
	}
	
	@RequestMapping("/getDeviceHistoryDetailsData")
	public String getDeviceHistoryDetailsData() throws Exception {
		String json = "";
		HttpSession session=request.getSession();
		orgId = ParamUtils.getParameter(request, "orgId");
		String recordId = ParamUtils.getParameter(request, "recordId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		this.pager = new Page("pagerForm", request);
		User user = (User) session.getAttribute("userLogin");
		if(user!=null){
			json = historyQueryService.getDeviceHistoryDetailsData(deviceId,deviceName,deviceType,recordId,user.getUserNo());
		}
		
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
	
	@RequestMapping("/getHistoryQueryCurveData")
	public String getHistoryQueryCurveData() throws Exception {
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		startDate = ParamUtils.getParameter(request, "startDate");
		endDate = ParamUtils.getParameter(request, "endDate");
		String deviceTableName="tbl_rpcdevice";
		String tableName="tbl_rpcacqdata_hist";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			deviceTableName="tbl_pcpdevice";
			tableName="tbl_pcpacqdata_hist";
		}
		if(user!=null){
			if(!StringManagerUtils.isNotNull(endDate)){
				String sql = " select to_char(max(t.acqTime),'yyyy-mm-dd hh24:mi:ss') from "+tableName+" t where t.wellId="+deviceId;
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
			
			
			this.pager = new Page("pagerForm", request);
			json = historyQueryService.getHistoryQueryCurveData(deviceId,deviceName,deviceType,startDate,endDate,user.getUserNo());
		}
		
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getHistoryQueryCurveSetData")
	public String getHistoryQueryCurveSetData() throws Exception {
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		json = historyQueryService.getHistoryQueryCurveSetData(deviceId,deviceType);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/setHistoryDataGraphicInfo")
	public String setHistoryDataGraphicInfo() throws Exception {
		String json = "{success:false}";
		HttpSession session=request.getSession();
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		String graphicSetData = ParamUtils.getParameter(request, "graphicSetData");
		this.pager = new Page("pagerForm", request);
		int result = historyQueryService.setHistoryDataGraphicInfo(deviceId,deviceType,graphicSetData);
		json = "{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/querySurfaceCard")
	public String querySurfaceCard() throws IOException {
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		startDate = ParamUtils.getParameter(request, "startDate");
		endDate = ParamUtils.getParameter(request, "endDate");
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		this.pager = new Page("pagerForm", request);
		String tableName="tbl_rpcacqdata_hist";
		String json = "";
		try {
			if(StringManagerUtils.isNotNull(deviceId)&&!StringManagerUtils.isNotNull(endDate)){
				String sql = " select to_char(max(t.acqTime),'yyyy-mm-dd hh24:mi:ss') from "+tableName+" t where t.wellId= "+deviceId;
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
			json = this.historyQueryService.querySurfaceCard(orgId,deviceId,deviceName,deviceType,pager);
			pw.print(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportHistoryQueryFESDiagramDataExcel")
	public String exportHistoryQueryFESDiagramDataExcel() throws IOException {
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceName"),"utf-8");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		startDate = ParamUtils.getParameter(request, "startDate");
		endDate = ParamUtils.getParameter(request, "endDate");
		
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		
		this.pager = new Page("pagerForm", request);
		String tableName="tbl_rpcacqdata_hist";
		if(StringManagerUtils.isNotNull(deviceId)&&!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.acqTime),'yyyy-mm-dd hh24:mi:ss') from "+tableName+" t where t.wellId= "+deviceId;
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
		boolean bool = this.historyQueryService.exportHistoryQueryFESDiagramDataExcel(response,fileName,title, heads, fields,orgId,deviceId,deviceName,pager);
	
		return null;
	}
	
	@RequestMapping("/getFESDiagramOverlayData")
	public String getFESDiagramOverlayData() throws IOException {
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		startDate = ParamUtils.getParameter(request, "startDate");
		endDate = ParamUtils.getParameter(request, "endDate");
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		String calculateDate = ParamUtils.getParameter(request, "calculateDate");
		this.pager = new Page("pagerForm", request);
		String tableName="tbl_rpcacqdata_hist";
		String json = "";
		try {
			if(StringManagerUtils.isNotNull(deviceId)&&!StringManagerUtils.isNotNull(endDate)){
				String sql = " select to_char(max(t.acqTime),'yyyy-mm-dd hh24:mi:ss') from "+tableName+" t where t.wellId= "+deviceId;
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
			json = this.historyQueryService.getFESDiagramOverlayData(orgId,deviceId,deviceName,deviceType,pager);
			pw.print(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportHistoryQueryFESDiagramOverlayDataExcel")
	public String exportHistoryQueryFESDiagramOverlayDataExcel() throws Exception {
		boolean bool=false;
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceName"),"utf-8");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		startDate = ParamUtils.getParameter(request, "startDate");
		endDate = ParamUtils.getParameter(request, "endDate");
		
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		
		DataDictionary ddic = null;
		String ddicName="historyQuery_FESDiagramOverlay";
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		heads=StringUtils.join(ddic.getHeaders(), ",");
		fields=StringUtils.join(ddic.getFields(), ",");
		
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		
		String tableName="tbl_rpcacqdata_hist";
		if(StringManagerUtils.isNotNull(deviceId)&&!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.acqTime),'yyyy-mm-dd hh24:mi:ss') from "+tableName+" t where t.wellId= "+deviceId;
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
		
		bool = historyQueryService.exportFESDiagramOverlayData(response,fileName,title, heads, fields,orgId,deviceId,deviceName,pager);
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
	public int getTotals() {
		return totals;
	}
	public void setTotals(int totals) {
		this.totals = totals;
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

	public String getDeviceTypeStatValue() {
		return deviceTypeStatValue;
	}

	public void setDeviceTypeStatValue(String deviceTypeStatValue) {
		this.deviceTypeStatValue = deviceTypeStatValue;
	}

	public String getCommStatusStatValue() {
		return commStatusStatValue;
	}

	public void setCommStatusStatValue(String commStatusStatValue) {
		this.commStatusStatValue = commStatusStatValue;
	}

	public String getFESdiagramResultStatValue() {
		return FESdiagramResultStatValue;
	}

	public void setFESdiagramResultStatValue(String fESdiagramResultStatValue) {
		FESdiagramResultStatValue = fESdiagramResultStatValue;
	}

	public String getRunStatusStatValue() {
		return runStatusStatValue;
	}

	public void setRunStatusStatValue(String runStatusStatValue) {
		this.runStatusStatValue = runStatusStatValue;
	}
}
