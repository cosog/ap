package com.cosog.controller.report;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.model.User;
import com.cosog.model.calculate.CommResponseData;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.report.ReportDataManagerService;
import com.cosog.utils.Config;
import com.cosog.utils.ConfigFile;
import com.cosog.utils.Constants;
import com.cosog.utils.Page;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <p>描述：采出井日报表Action</p>
 * 
 * @author gao 2014-06-04
 * 
 */
@Controller
@RequestMapping("/reportDataMamagerController")
@Scope("prototype")
public class ReportDataMamagerController extends BaseController {
	private static Log log = LogFactory.getLog(ReportDataMamagerController.class);
	private static final long serialVersionUID = 1L;
	private String deviceName = "";
	private String calculateDate;
	private String calculateEndDate;
	private int limit = 10;
	private String orgId;
	private int page = 1;
	@Autowired
	private CommonDataService commonDataService;
	@Autowired
	private ReportDataManagerService<?> reportDataManagerService;
	
	@RequestMapping("/getSingleWellRangeReportData")
	public String getSingleWellRangeReportData() throws Exception {
		log.debug("reportOutputWell enter==");
		Vector<String> v = new Vector<String>();
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate= ParamUtils.getParameter(request, "endDate");
		String reportType = ParamUtils.getParameter(request, "reportType");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String tableName="tbl_dailycalculationdata";
		
		if (!StringManagerUtils.isNotNull(endDate)) {
			String sql = " select * from (select  to_char(t.calDate,'yyyy-mm-dd') from "+tableName+" t where 1=1";
			if(StringManagerUtils.isNotNull(deviceId)){
				sql+= " and t.deviceId="+deviceId+" ";
			}	
			sql+= "order by calDate desc) where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-10);
		}
		String json = "";
		this.pager = new Page("pagerForm", request);
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		if(user!=null){
			json = reportDataManagerService.getSingleWellRangeReportData(pager, orgId,deviceType,reportType, deviceId, deviceName,calculateType, startDate,endDate,user.getUserNo(),user.getLanguageName());
		}
		
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.write(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping("/exportSingleWellRangeReportData")
	public String exportSingleWellRangeReportData() throws Exception {
		log.debug("reportOutputWell enter==");
		Vector<String> v = new Vector<String>();
		HttpSession session=request.getSession();
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String deviceName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceName"),"utf-8");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate= ParamUtils.getParameter(request, "endDate");
		String reportType = ParamUtils.getParameter(request, "reportType");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		String key = ParamUtils.getParameter(request, "key");
		
		User user=null;
		if(session!=null){
			user = (User) session.getAttribute("userLogin");
			session.removeAttribute(key);
			session.setAttribute(key, 0);
		}
		
		String tableName="tbl_dailycalculationdata";
		
		if (!StringManagerUtils.isNotNull(endDate)) {
			String sql = " select * from (select  to_char(t.calDate,'yyyy-mm-dd') from "+tableName+" t where 1=1";
			if(StringManagerUtils.isNotNull(deviceId)){
				sql+= " and t.deviceId="+deviceId+" ";
			}	
			sql+= "order by calDate desc) where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-10);
		}
		String json = "";
		this.pager = new Page("pagerForm", request);
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		
		String fileName = deviceName;
		String title = deviceName+"生产报表";
        if(StringManagerUtils.isNotNull(deviceName)){
        	fileName+=deviceName;
        }
        fileName+="生产报表-"+startDate;
        if(!startDate.equalsIgnoreCase(endDate)){
        	fileName+="~"+endDate;
        }
		boolean bool = reportDataManagerService.exportSingleWellRangeReportData(user,response,pager, orgId,deviceType,reportType, deviceId, deviceName,calculateType, startDate,endDate,user.getUserNo(),user.getLanguageName());
		if(session!=null){
			session.setAttribute(key, 1);
		}
		
		return null;
	}
	
	@RequestMapping("/batchExportSingleWellRangeReportData")
	public String batchExportSingleWellRangeReportData() throws Exception {
		log.debug("reportOutputWell enter==");
		Vector<String> v = new Vector<String>();
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceName"),"utf-8");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate= ParamUtils.getParameter(request, "endDate");
		String reportType = ParamUtils.getParameter(request, "reportType");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String deviceTypeName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceTypeName"),"utf-8");
		String key = ParamUtils.getParameter(request, "key");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		session.removeAttribute(key);
		session.setAttribute(key, 0);
		String tableName="tbl_dailycalculationdata";
		
		if (!StringManagerUtils.isNotNull(endDate)) {
			String sql = " select * from (select  to_char(t.calDate,'yyyy-mm-dd') from "+tableName+" t where 1=1";
			sql+= "order by calDate desc) where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-10);
		}
		String json = "";
		this.pager = new Page("pagerForm", request);
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		
		boolean bool = reportDataManagerService.batchExportSingleWellRangeReportData(user,response,pager, orgId,deviceType,deviceTypeName,reportType, deviceName, startDate,endDate,user.getUserNo(),user.getLanguageName());
		session.setAttribute(key, 1);
		return null;
	}
	
	@RequestMapping("/getSingleWellDailyReportData")
	public String getSingleWellDailyReportData() throws Exception {
		log.debug("reportOutputWell enter==");
		Vector<String> v = new Vector<String>();
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate= ParamUtils.getParameter(request, "endDate");
		String reportDate= ParamUtils.getParameter(request, "reportDate");
		String reportType = ParamUtils.getParameter(request, "reportType");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		String reportInterval = ParamUtils.getParameter(request, "interval");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String tableName="tbl_dailycalculationdata";
		String timingcalculationTableName="tbl_timingcalculationdata";
		if(StringManagerUtils.stringToInteger(calculateType)==1){
			timingcalculationTableName="tbl_srptimingcalculationdata";
		}else if(StringManagerUtils.stringToInteger(calculateType)==2){
			timingcalculationTableName="tbl_pcptimingcalculationdata";
		}
		
		if (!StringManagerUtils.isNotNull(endDate)) {
			String sql = " select * from (select  to_char(t.calDate,'yyyy-mm-dd') from "+tableName+" t where 1=1";
			if(StringManagerUtils.isNotNull(deviceId)){
				sql+= " and t.deviceId="+deviceId+" ";
			}	
			sql+= "order by calDate desc) where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-10);
		}
		if(!StringManagerUtils.isNotNull(reportDate)){
			int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
			CommResponseData.Range startRange= StringManagerUtils.getTimeRange(startDate,offsetHour);
			CommResponseData.Range endRange= StringManagerUtils.getTimeRange(endDate,offsetHour);
			List<String> defaultTimeList= StringManagerUtils.getDaliyTimeList(endDate,offsetHour,StringManagerUtils.stringToInteger(reportInterval));
			
			String sql = "select * from ("
					+ " select  to_char(t.calTime-"+offsetHour+"/24,'yyyy-mm-dd hh24:mi:ss') "
					+ " from "+timingcalculationTableName+" t "
					+ " where t.caltime between to_date('"+startRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endRange.getEndTime()+"','yyyy-mm-dd hh24:mi:ss')"
					+ " ";
			if(StringManagerUtils.stringToInteger(reportInterval)>1){
				sql+=" and to_char(t.calTime,'hh24:mi:ss') in ("+StringManagerUtils.joinStringArr2(defaultTimeList, ",")+")";
			}
			if(StringManagerUtils.isNotNull(deviceId)){
				sql+= " and t.deviceId="+deviceId+" ";
			}	
			sql+= "order by calTime desc) where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				String[] calTimeArr=list.get(0).toString().split(" ");
				reportDate = calTimeArr[0];
				if(calTimeArr.length==2 && "00:00:00".equalsIgnoreCase(calTimeArr[1])){
					reportDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(reportDate),-1);
				}
			} else {
				reportDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),0);
			}
		}
		String json = "";
		this.pager = new Page("pagerForm", request);
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		if(user!=null){
			json = reportDataManagerService.getSingleWellDailyReportData(pager, orgId,deviceType,reportType, deviceId, deviceName,calculateType, startDate,endDate,reportDate,reportInterval,user.getUserNo(),user.getLanguageName());
		}
		
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.write(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping("/exportSingleWellDailyReportData")
	public String exportSingleWellDailyReportData() throws Exception {
		log.debug("reportOutputWell enter==");
		Vector<String> v = new Vector<String>();
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String deviceName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceName"),"utf-8");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate= ParamUtils.getParameter(request, "endDate");
		String reportDate= ParamUtils.getParameter(request, "reportDate");
		String reportType = ParamUtils.getParameter(request, "reportType");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		String reportInterval = ParamUtils.getParameter(request, "interval");
		String key = ParamUtils.getParameter(request, "key");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		session.removeAttribute(key);
		session.setAttribute(key, 0);
		String tableName="tbl_srpdailycalculationdata";
		String timingcalculationTableName="tbl_timingcalculationdata";
		if(StringManagerUtils.stringToInteger(calculateType)==1){
			timingcalculationTableName="tbl_srptimingcalculationdata";
		}else if(StringManagerUtils.stringToInteger(calculateType)==2){
			timingcalculationTableName="tbl_pcptimingcalculationdata";
		}
		
		if (!StringManagerUtils.isNotNull(endDate)) {
			String sql = " select * from (select  to_char(t.calDate,'yyyy-mm-dd') from "+tableName+" t where 1=1";
			if(StringManagerUtils.isNotNull(deviceId)){
				sql+= " and t.deviceId="+deviceId+" ";
			}	
			sql+= "order by calDate desc) where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-10);
		}
		if(!StringManagerUtils.isNotNull(reportDate)){
			int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
			CommResponseData.Range startRange= StringManagerUtils.getTimeRange(startDate,offsetHour);
			CommResponseData.Range endRange= StringManagerUtils.getTimeRange(endDate,offsetHour);
			List<String> defaultTimeList= StringManagerUtils.getDaliyTimeList(endDate,offsetHour,StringManagerUtils.stringToInteger(reportInterval));
			
			String sql = "select * from ("
					+ " select  to_char(t.calTime-"+offsetHour+"/24,'yyyy-mm-dd hh24:mi:ss') "
					+ " from "+timingcalculationTableName+" t "
					+ " where t.caltime between to_date('"+startRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endRange.getEndTime()+"','yyyy-mm-dd hh24:mi:ss')"
					+ " ";
			if(StringManagerUtils.stringToInteger(reportInterval)>1){
				sql+=" and to_char(t.calTime,'hh24:mi:ss') in ("+StringManagerUtils.joinStringArr2(defaultTimeList, ",")+")";
			}
			if(StringManagerUtils.isNotNull(deviceId)){
				sql+= " and t.deviceId="+deviceId+" ";
			}	
			sql+= "order by calTime desc) where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				String[] calTimeArr=list.get(0).toString().split(" ");
				reportDate = calTimeArr[0];
				if(calTimeArr.length==2 && "00:00:00".equalsIgnoreCase(calTimeArr[1])){
					reportDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(reportDate),-1);
				}
			} else {
				reportDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),0);
			}
		}
		String json = "";
		this.pager = new Page("pagerForm", request);
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		
		String fileName = deviceName;
		String title = deviceName+"单日生产报表";
        if(StringManagerUtils.isNotNull(deviceName)){
        	fileName+=deviceName;
        }
        fileName+="生产报表-"+startDate;
        if(!startDate.equalsIgnoreCase(endDate)){
        	fileName+="~"+endDate;
        }
		boolean bool = reportDataManagerService.exportSingleWellDailyReportData(user,response,pager, orgId,deviceType,reportType, deviceId, deviceName,calculateType, startDate,endDate,reportDate,reportInterval,user.getUserNo(),user.getLanguageName());
		session.setAttribute(key, 1);
		return null;
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping("/batchExportSingleWellDailyReportData")
	public String batchExportSingleWellDailyReportData() throws Exception {
		HttpSession session=request.getSession();
		
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceName"),"utf-8");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate= ParamUtils.getParameter(request, "endDate");
		String reportDate= ParamUtils.getParameter(request, "reportDate");
		String reportType = ParamUtils.getParameter(request, "reportType");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String deviceTypeName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceTypeName"),"utf-8");
		
		String reportInterval = ParamUtils.getParameter(request, "interval");
		String key = ParamUtils.getParameter(request, "key");
		User user = (User) session.getAttribute("userLogin");
		session.removeAttribute(key);
		session.setAttribute(key, 0);
		String tableName="tbl_dailycalculationdata";
		String timingcalculationTableName="tbl_timingcalculationdata";
		
		if (!StringManagerUtils.isNotNull(endDate)) {
			String sql = " select * from (select  to_char(t.calDate,'yyyy-mm-dd') from "+tableName+" t where 1=1";
			sql+= "order by calDate desc) where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-10);
		}
		if(!StringManagerUtils.isNotNull(reportDate)){
			int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
			CommResponseData.Range startRange= StringManagerUtils.getTimeRange(startDate,offsetHour);
			CommResponseData.Range endRange= StringManagerUtils.getTimeRange(endDate,offsetHour);
			List<String> defaultTimeList= StringManagerUtils.getDaliyTimeList(endDate,offsetHour,StringManagerUtils.stringToInteger(reportInterval));
			
			String sql = "select * from ("
					+ " select  to_char(t.calTime-"+offsetHour+"/24,'yyyy-mm-dd hh24:mi:ss') "
					+ " from "+timingcalculationTableName+" t "
					+ " where t.caltime between to_date('"+startRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endRange.getEndTime()+"','yyyy-mm-dd hh24:mi:ss')"
					+ " ";
			if(StringManagerUtils.stringToInteger(reportInterval)>1){
				sql+=" and to_char(t.calTime,'hh24:mi:ss') in ("+StringManagerUtils.joinStringArr2(defaultTimeList, ",")+")";
			}
			sql+= "order by calTime desc) where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				String[] calTimeArr=list.get(0).toString().split(" ");
				reportDate = calTimeArr[0];
				if(calTimeArr.length==2 && "00:00:00".equalsIgnoreCase(calTimeArr[1])){
					reportDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(reportDate),-1);
				}
			} else {
				reportDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),0);
			}
		}
		this.pager = new Page("pagerForm", request);
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		reportDataManagerService.batchExportSingleWellDailyReportData(user,response,pager, orgId,deviceType,deviceTypeName,reportType, deviceName, startDate,endDate,reportDate,reportInterval,user.getUserNo(),user.getLanguageName());
		session.setAttribute(key, 1);
		return null;
	}
	
	@RequestMapping("/getProductionDailyReportData")
	public String getProductionDailyReportData() throws Exception {
		log.debug("reportOutputWell enter==");
		Vector<String> v = new Vector<String>();
		orgId = ParamUtils.getParameter(request, "orgId");
		String instanceCode = ParamUtils.getParameter(request, "instanceCode");
		String unitId = ParamUtils.getParameter(request, "unitId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String selectedOrgName = ParamUtils.getParameter(request, "selectedOrgName");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate= ParamUtils.getParameter(request, "endDate");
		String reportDate= ParamUtils.getParameter(request, "reportDate");
		String reportType = ParamUtils.getParameter(request, "reportType");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String tableName="tbl_dailycalculationdata";
		
		if (!StringManagerUtils.isNotNull(endDate)) {
			String sql = " select * from (select  to_char(t.calDate,'yyyy-mm-dd') from "+tableName+" t where 1=1 ";
			sql+= " order by calDate desc) where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				endDate = list.get(0).toString();
			} else {
				int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
				endDate=StringManagerUtils.getCurrentTime();
				if(!StringManagerUtils.timeMatchDate(StringManagerUtils.getCurrentTime("yyyy-MM-dd"), endDate, offsetHour)){
					endDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-1);
				}
			}
		}
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-10);
		}
		if(!StringManagerUtils.isNotNull(reportDate)){
			String sql = " select * from "
					+ " (select  to_char(t.calDate,'yyyy-mm-dd') from "+tableName+" t "
					+ " where t.commstatus=1 "
					+ " and t.calDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')"
					+ " order by calDate desc) "
					+ " where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				reportDate = list.get(0).toString();
			} else {
				reportDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),0);
			}
		}
		String json = "";
		this.pager = new Page("pagerForm", request);
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		if(user!=null){
			json = reportDataManagerService.getProductionDailyReportData(pager, orgId,selectedOrgName,deviceType,reportType, instanceCode,unitId, deviceName, startDate,endDate,reportDate,user.getUserNo(),language);
		}
		
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.write(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/exportProductionDailyReportData")
	public String exportProductionDailyReportData() throws Exception {
		log.debug("reportOutputWell enter==");
		Vector<String> v = new Vector<String>();
		orgId = ParamUtils.getParameter(request, "orgId");
		String instanceCode = ParamUtils.getParameter(request, "instanceCode");
		String unitId = ParamUtils.getParameter(request, "unitId");
		String deviceName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceName"),"utf-8");
		String selectedOrgName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "selectedOrgName"),"utf-8");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate= ParamUtils.getParameter(request, "endDate");
		String reportDate= ParamUtils.getParameter(request, "reportDate");
		String reportType = ParamUtils.getParameter(request, "reportType");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String key = ParamUtils.getParameter(request, "key");
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		session.removeAttribute(key);
		session.setAttribute(key, 0);
		String tableName="tbl_dailycalculationdata";
		
		if (!StringManagerUtils.isNotNull(endDate)) {
			String sql = " select * from (select  to_char(t.calDate,'yyyy-mm-dd') from "+tableName+" t where 1=1";
			sql+= " order by calDate desc) where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				endDate = list.get(0).toString();
			} else {
				int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
				endDate=StringManagerUtils.getCurrentTime();
				if(!StringManagerUtils.timeMatchDate(StringManagerUtils.getCurrentTime("yyyy-MM-dd"), endDate, offsetHour)){
					endDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-1);
				}
			}
		}
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-10);
		}
		if(!StringManagerUtils.isNotNull(reportDate)){
			String sql = " select * from "
					+ " (select  to_char(t.calDate,'yyyy-mm-dd') from "+tableName+" t "
					+ " where t.commstatus=1 "
					+ " and t.calDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')"
					+ " order by calDate desc) "
					+ " where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				reportDate = list.get(0).toString();
			} else {
				reportDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),0);
			}
		}
		String json = "";
		this.pager = new Page("pagerForm", request);
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		
		
		boolean bool = reportDataManagerService.exportProductionDailyReportData(user,response,pager, orgId,selectedOrgName,deviceType,reportType, instanceCode,unitId, deviceName, startDate,endDate,reportDate,user.getUserNo(),language);
		session.setAttribute(key, 1);
		return null;
	}
	
	@RequestMapping("/batchExportProductionDailyReportData")
	public String batchExportProductionDailyReportData() throws Exception {
		log.debug("reportOutputWell enter==");
		Vector<String> v = new Vector<String>();
		orgId = ParamUtils.getParameter(request, "orgId");
		String selectedOrgName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "selectedOrgName"),"utf-8");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate= ParamUtils.getParameter(request, "endDate");
		String reportDate= ParamUtils.getParameter(request, "reportDate");
		String reportType = ParamUtils.getParameter(request, "reportType");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String key = ParamUtils.getParameter(request, "key");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		session.removeAttribute(key);
		session.setAttribute(key, 0);
		String tableName="tbl_dailycalculationdata";
		
		if (!StringManagerUtils.isNotNull(endDate)) {
			String sql = " select * from (select  to_char(t.calDate,'yyyy-mm-dd') from "+tableName+" t where 1=1";
			sql+= "order by calDate desc) where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				endDate = list.get(0).toString();
			} else {
				int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
				endDate=StringManagerUtils.getCurrentTime();
				if(!StringManagerUtils.timeMatchDate(StringManagerUtils.getCurrentTime("yyyy-MM-dd"), endDate, offsetHour)){
					endDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-1);
				}
			}
		}
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-10);
		}
		if(!StringManagerUtils.isNotNull(reportDate)){
			String sql = " select * from "
					+ " (select  to_char(t.calDate,'yyyy-mm-dd') from "+tableName+" t "
					+ " where t.commstatus=1 "
					+ " and t.calDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')"
					+ " order by calDate desc) "
					+ " where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				reportDate = list.get(0).toString();
			} else {
				reportDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),0);
			}
		}
		String json = "";
		this.pager = new Page("pagerForm", request);
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		
		
		boolean bool = reportDataManagerService.batchExportProductionDailyReportData(user,response,pager, orgId,selectedOrgName,deviceType,reportType,reportDate,user.getUserNo(),language);
		session.setAttribute(key, 1);
		return null;
	}
	
	@RequestMapping("/getSingleWellRangeReportCurveData")
	public String getSingleWellRangeReportCurveData() throws Exception {
		log.debug("reportOutputWell enter==");
		Vector<String> v = new Vector<String>();
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate= ParamUtils.getParameter(request, "endDate");
		String reportType = ParamUtils.getParameter(request, "reportType");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String tableName="tbl_dailycalculationdata";
		
		if (!StringManagerUtils.isNotNull(endDate)) {
			String sql = " select * from (select  to_char(t.calDate,'yyyy-mm-dd') from "+tableName+" t where 1=1";
			if(StringManagerUtils.isNotNull(deviceId)){
				sql+= " and t.deviceId="+deviceId+" ";
			}	
			sql+= "order by calDate desc) where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-10);
		}
		String json = "";
		this.pager = new Page("pagerForm", request);
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		if(user!=null){
			json = reportDataManagerService.getSingleWellRangeReportCurveData(pager, orgId,deviceType,reportType, deviceId, deviceName,calculateType, startDate,endDate,user.getUserNo());
		}
		
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.write(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getSingleWellDailyReportCurveData")
	public String getSingleWellDailyReportCurveData() throws Exception {
		log.debug("reportOutputWell enter==");
		Vector<String> v = new Vector<String>();
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate= ParamUtils.getParameter(request, "endDate");
		String reportDate= ParamUtils.getParameter(request, "reportDate");
		String reportType = ParamUtils.getParameter(request, "reportType");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		String reportInterval = ParamUtils.getParameter(request, "interval");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String tableName="tbl_dailycalculationdata";
		String timingcalculationTableName="tbl_timingcalculationdata";
		if(StringManagerUtils.stringToInteger(calculateType)==1){
			timingcalculationTableName="tbl_srptimingcalculationdata";
		}else if(StringManagerUtils.stringToInteger(calculateType)==2){
			timingcalculationTableName="tbl_pcptimingcalculationdata";
		}
		
		if (!StringManagerUtils.isNotNull(endDate)) {
			String sql = " select * from (select  to_char(t.calDate,'yyyy-mm-dd') from "+tableName+" t where 1=1";
			if(StringManagerUtils.isNotNull(deviceId)){
				sql+= " and t.deviceId="+deviceId+" ";
			}	
			sql+= "order by calDate desc) where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-10);
		}
		if(!StringManagerUtils.isNotNull(reportDate)){
			int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
			CommResponseData.Range startRange= StringManagerUtils.getTimeRange(startDate,offsetHour);
			CommResponseData.Range endRange= StringManagerUtils.getTimeRange(endDate,offsetHour);
			List<String> defaultTimeList= StringManagerUtils.getDaliyTimeList(endDate,offsetHour,StringManagerUtils.stringToInteger(reportInterval));
			
			String sql = "select * from ("
					+ " select  to_char(t.calTime-"+offsetHour+"/24,'yyyy-mm-dd hh24:mi:ss') "
					+ " from "+timingcalculationTableName+" t "
					+ " where t.caltime between to_date('"+startRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endRange.getEndTime()+"','yyyy-mm-dd hh24:mi:ss')"
					+ " ";
			if(StringManagerUtils.stringToInteger(reportInterval)>1){
				sql+=" and to_char(t.calTime,'hh24:mi:ss') in ("+StringManagerUtils.joinStringArr2(defaultTimeList, ",")+")";
			}
			if(StringManagerUtils.isNotNull(deviceId)){
				sql+= " and t.deviceId="+deviceId+" ";
			}	
			sql+= "order by calTime desc) where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				String[] calTimeArr=list.get(0).toString().split(" ");
				reportDate = calTimeArr[0];
				if(calTimeArr.length==2 && "00:00:00".equalsIgnoreCase(calTimeArr[1])){
					reportDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(reportDate),-1);
				}
			} else {
				reportDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),0);
			}
		}
		String json = "";
		this.pager = new Page("pagerForm", request);
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		if(user!=null){
			json = reportDataManagerService.getSingleWellDailyReportCurveData(pager, orgId,deviceType,reportType, deviceId, deviceName,calculateType, startDate,endDate,reportDate,reportInterval,user.getUserNo());
		}
		
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.write(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getProductionDailyReportCurveData")
	public String getProductionDailyReportCurveData() throws Exception {
		log.debug("reportOutputWell enter==");
		Vector<String> v = new Vector<String>();
		orgId = ParamUtils.getParameter(request, "orgId");
		String instanceCode = ParamUtils.getParameter(request, "instanceCode");
		String unitId = ParamUtils.getParameter(request, "unitId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String selectedOrgName = ParamUtils.getParameter(request, "selectedOrgName");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate= ParamUtils.getParameter(request, "endDate");
		String reportType = ParamUtils.getParameter(request, "reportType");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		
		String tableName="tbl_dailycalculationdata";
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		
		if (!StringManagerUtils.isNotNull(endDate)) {
			String sql = " select * from (select  to_char(t.calDate,'yyyy-mm-dd') from "+tableName+" t where 1=1";
			sql+= "order by calDate desc) where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-10);
		}
		String json = "";
		this.pager = new Page("pagerForm", request);
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		if(user!=null){
			json = reportDataManagerService.getProductionDailyReportCurveData(pager, orgId,selectedOrgName,deviceType,reportType, unitId,instanceCode, deviceName, startDate,endDate,user.getUserNo(),language);
		}
		
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.write(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getReportQueryCurveSetData")
	public String getReportQueryCurveSetData() throws Exception {
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String reportType = ParamUtils.getParameter(request, "reportType");
		this.pager = new Page("pagerForm", request);
		if(user!=null){
			json = reportDataManagerService.getReportQueryCurveSetData(deviceId,deviceType,reportType,user.getUserNo());
		}
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/setReportDataGraphicInfo")
	public String setReportDataGraphicInfo() throws Exception {
		String json = "{success:false}";
		HttpSession session=request.getSession();
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String reportType = ParamUtils.getParameter(request, "reportType");
		String graphicSetData = ParamUtils.getParameter(request, "graphicSetData");
		this.pager = new Page("pagerForm", request);
		int result = reportDataManagerService.setReportDataGraphicInfo(deviceId,deviceType,reportType,graphicSetData);
		json = "{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveSingleWellRangeDailyReportData")
	public String saveSingleWellRangeDailyReportData() throws Exception {
		String json = "{success:false}";
		HttpSession session=request.getSession();
		String data = ParamUtils.getParameter(request, "data");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		int result = reportDataManagerService.saveSingleWellRangeDailyReportData(deviceId,deviceName,deviceType,data);
		json = "{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveSingleWellDailyDailyReportData")
	public String saveSingleWellDailyDailyReportData() throws Exception {
		String json = "{success:false}";
		HttpSession session=request.getSession();
		String data = ParamUtils.getParameter(request, "data");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		int result = reportDataManagerService.saveSingleWellDailyDailyReportData(deviceId,deviceName,deviceType,data);
		json = "{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/showTouchtest")
	public String showTouchtest() throws Exception {
		String json = "{success:true,totals:4,items:[{\"name\":\"Jean Luc2\",\"email\":\"jeanluc.picard@enterprise.com\",\"phone\": \"555-111-1111\"},{\"name\":\"Worf2\",\"email\":\"worf.moghsson@enterprise.com\",\"phone\":\"555-222-2222\"},{\"name\":\"Deanna2\",\"email\":\"deanna.troi@enterprise.com\",\"phone\":\"555-333-3333\" },{\"name\":\"Data2\",\"email\":\"mr.data@enterprise.com\",\"phone\":\"555-444-4444\"}]}";
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.write(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**<p>描述：采出井日报表Excel文件</p>
	 * 
	 * @return null
	 * @throws Exception
	 *            
	 */
	@RequestMapping("/exportExcelReportPumpingUnitData")
	public String exportExcelReportPumpingUnitData() throws Exception {
		// TODO Auto-generated method stub
		log.debug("ReportPumpingUnit enter==");
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		heads = java.net.URLDecoder.decode(heads, "utf-8");
		fields = java.net.URLDecoder.decode(fields, "utf-8");
		Vector<String> v = new Vector<String>();
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceName"),"utf-8");
		calculateDate = ParamUtils.getParameter(request, "calculateDate");
		orgId=this.findCurrentUserOrgIdInfo(orgId);
		deviceName = java.net.URLDecoder.decode(deviceName, "utf-8");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		
		if (StringManagerUtils.isNotNull(deviceName)) {
			v.add(deviceName);
		}else{
			v.add(null);
		}
		String addDate = "";
		if (!"".equals(calculateDate) && null != calculateDate && calculateDate.length() > 0) {
			v.add(calculateDate);
			addDate = StringManagerUtils.formatStringDate(StringManagerUtils.addDate(StringManagerUtils.stringToDate(calculateDate)));
			v.add(addDate);
		} else {
			v.add(StringManagerUtils.getCurrentTime());
			addDate = StringManagerUtils.formatStringDate(StringManagerUtils.addDate(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime())));
			v.add(addDate);
		}
		// String ReportPumpingUnit = "采出---抽油机井---日报表";
		this.commonDataService.exportDataExcel(response, fileName, "采出井日报数据", null, heads, fields, orgId, "pumpUnitDayReport", v,language);
		return null;
	}
	
	@RequestMapping("/getDeviceList")
	public String getDeviceList() throws Exception {
		String json = "";
		String orgId = ParamUtils.getParameter(request, "orgId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		json = reportDataManagerService.getDeviceList(orgId,deviceName,deviceType,language);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getReportTemplateList")
	public String getReportTemplateList() throws Exception {
		String json = "";
		String orgId = ParamUtils.getParameter(request, "orgId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String reportType = ParamUtils.getParameter(request, "reportType");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		json = reportDataManagerService.getReportTemplateList(orgId,deviceName,deviceType,reportType,language);
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
	
	@RequestMapping("/getReportInstanceList")
	public String getReportInstanceList() throws Exception {
		String json = "";
		String orgId = ParamUtils.getParameter(request, "orgId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String reportType = ParamUtils.getParameter(request, "reportType");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		json = reportDataManagerService.getReportInstanceList(orgId,deviceName,deviceType,language);
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

	@RequestMapping("/getSessionFlag")
	public String getSessionFlag() throws Exception {
		HttpSession session=request.getSession();
		String key = ParamUtils.getParameter(request, "key");
		String flag=(session!=null && session.getAttribute(key)!=null)?(session.getAttribute(key).toString()):"0";
		JSONObject json = new JSONObject();
		json.put("flag",flag);
		String jsonStr=json.toString();
		if("1".equalsIgnoreCase(flag)){
			session.removeAttribute(key);
		}
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(jsonStr);
		pw.flush();
		pw.close();
		return null;
	}

	public static String formatStringDate(Calendar Month) {
		Month = Calendar.getInstance();
		String time = null;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		time = sd.format(Month.getTime());
		return time;
	}


	
	public String getDeviceName() {
		return deviceName;
	}

	public void setDevieName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getCalculateDate() {
		return calculateDate;
	}

	public void setCalculateDate(String calculateDate) {
		this.calculateDate = calculateDate;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getCalculateEndDate() {
		return calculateEndDate;
	}

	public void setCalculateEndDate(String calculateEndDate) {
		this.calculateEndDate = calculateEndDate;
	}
}
