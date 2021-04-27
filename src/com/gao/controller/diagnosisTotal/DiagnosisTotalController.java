package com.gao.controller.diagnosisTotal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.DiagnosisAnalysisOnly;
import com.gao.model.User;
import com.gao.service.base.CommonDataService;
import com.gao.service.diagnosisTotal.DiagnosisTotalService;
import com.gao.utils.Constants;
import com.gao.utils.Page;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;

/**
 * 工况诊断 （单张功图诊断分析）- action类
 * 
 * @author gao 2014-05-09
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/diagnosisTotalController")
@Scope("prototype")
public class DiagnosisTotalController extends BaseController {
	private static Log log = LogFactory.getLog(DiagnosisTotalController.class);
	private static final long serialVersionUID = 1L;
	@Autowired
	private DiagnosisTotalService<?> diagnosisTotalService;
	@Autowired
	private CommonDataService service;
	private List<DiagnosisAnalysisOnly> list = null;
	private int page;
	private int limit;
	private int totals;
	private String wellName;
	private String orgId;
	private int id;

	/**
	 * <P>
	 * 描述:单张功图诊断的井名列表信息
	 * </p>
	 * 
	 * @author gao 2014-05-09
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@RequestMapping("/getDiagnosisTotalData")
	public String GetDiagnosisTotalData() throws Exception {
		orgId = ParamUtils.getParameter(request, "orgId");
		if (!StringManagerUtils.isNotNull(orgId)) {
			User user=null;
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		wellName = ParamUtils.getParameter(request, "wellName");
		String totalDate = ParamUtils.getParameter(request, "totalDate");
		String wellType = ParamUtils.getParameter(request, "wellType");
		String statValue = ParamUtils.getParameter(request, "statValue");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate = ParamUtils.getParameter(request, "endDate");
		String type = ParamUtils.getParameter(request, "type");
		this.pager = new Page("pagerForm", request);
		String tableName="tbl_rpc_total_day";
		if("200".equals(wellType)){
			tableName="tbl_rpc_total_day";
		}else{
			tableName="tbl_pcp_total_day";
		}
		if(!StringManagerUtils.isNotNull(totalDate)){
			String sql = " select to_char(max(t.calculatedate),'yyyy-mm-dd') from "+tableName+" t ";
			List list = this.service.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				totalDate = list.get(0).toString();
			} else {
				totalDate = StringManagerUtils.getCurrentTime();
			}
		}
		if(StringManagerUtils.isNotNull(wellName)&&!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.calculatedate),'yyyy-mm-dd') from "+tableName+" t where t.jbh=( select t2.jlbh from tbl_wellinformation t2 where t2.wellName='"+wellName+"' ) ";
			List list = this.service.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-30);
		}
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		String json =diagnosisTotalService.GetDiagnosisTotalData(orgId, wellName,totalDate, pager,wellType,statValue,startDate,endDate,type);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/exportDiagnosisTotalDataExcel")
	public String exportDiagnosisTotalDataExcel() throws Exception {
		String json = "";
		this.pager = new Page("pagerForm", request);
		orgId = ParamUtils.getParameter(request, "orgId");
		wellName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "wellName"),"utf-8");
		this.pager = new Page("pagerForm", request);
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		
		String totalDate = ParamUtils.getParameter(request, "totalDate");
		String statValue = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "statValue"),"utf-8");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate = ParamUtils.getParameter(request, "endDate");
		String type = ParamUtils.getParameter(request, "type");
		String wellType = ParamUtils.getParameter(request, "wellType");
		String tableName="tbl_rpc_total_day";
		if(StringManagerUtils.stringToInteger(wellType)>=400 && StringManagerUtils.stringToInteger(wellType)<500){
			tableName="tbl_pcp_total_day";
		}
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		if(StringManagerUtils.isNotNull(wellName)&&!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.calculatedate),'yyyy-mm-dd') from "+tableName+" t where t.wellId=( select t2.id from tbl_wellinformation t2 where t2.wellName='"+wellName+"' ) ";
			List list = this.service.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
			if(!StringManagerUtils.isNotNull(startDate)){
				startDate=endDate;
			}
		}
		
		json =diagnosisTotalService.exportDiagnosisTotalDataExcel(orgId, wellName,totalDate, pager,wellType,statValue,startDate,endDate,type);
		
		
		this.service.exportGridPanelData(response,fileName,title, heads, fields,json);
		return null;
	}
	
	/**<p>描述：获取全天评价统计数据</p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDiagnosisTotalStatistics")
	public String GetDiagnosisTotalStatistics() throws Exception {
		String json = "";
		orgId=ParamUtils.getParameter(request, "orgId");
		String totalDate = ParamUtils.getParameter(request, "totalDate");
		String type = ParamUtils.getParameter(request, "type");
		String wellType = ParamUtils.getParameter(request, "wellType");
		this.pager = new Page("pagerForm", request);
		
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		
		if(!StringManagerUtils.isNotNull(totalDate)){
			String sql = " select to_char(max(t.calculatedate),'yyyy-mm-dd') from tbl_rpc_total_day t ";
			List list = this.service.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				totalDate = list.get(0).toString();
			} else {
				totalDate = StringManagerUtils.getCurrentTime();
			}
		}
		
		json = diagnosisTotalService.GetDiagnosisTotalStatistics(orgId,type,wellType,totalDate);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
//		log.warn("doAlarmsSetShow json*********=" + json);
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/**
	 * 描述：查询叠加功图
	 */
	@RequestMapping("/getFSDiagramOverlayData")
	public String getFSDiagramOverlayData() throws IOException {
		orgId = ParamUtils.getParameter(request, "orgId");
		wellName = ParamUtils.getParameter(request, "wellName");
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		String calculateDate = ParamUtils.getParameter(request, "calculateDate");
		this.pager = new Page("pagerForm", request);
		String json = "";
		try {
			json = this.diagnosisTotalService.getFSDiagramOverlayData(pager, orgId, wellName, calculateDate);
			pw.print(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDiagnosisTotalCurveData")
	public String getDiagnosisTotalCurveData() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		wellName = ParamUtils.getParameter(request, "wellName");
		String endDate = ParamUtils.getParameter(request, "endDate");
		String startDate = ParamUtils.getParameter(request, "startDate");
		HttpSession session=request.getSession();
		if (!StringUtils.isNotBlank(orgId)) {
			User user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		this.pager = new Page("pagerForm", request);
		if(!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.calculateDate),'yyyy-mm-dd') from viw_rpc_total_day t  where wellName= '"+wellName+"' ";
			List list = this.service.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		
		if (!StringManagerUtils.isNotNull(startDate)) {
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-30);
		} else {
			
		}
		
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		json =  this.diagnosisTotalService.getDiagnosisTotalCurveData(pager, orgId, wellName, startDate,endDate);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getDiagnosisTotalDynamicCurveData")
	public String getDiagnosisTotalDynamicCurveData() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		wellName = ParamUtils.getParameter(request, "wellName");
		String selectedWellName = ParamUtils.getParameter(request, "selectedWellName");
		String calculateDate = ParamUtils.getParameter(request, "calculateDate");
		String endDate = ParamUtils.getParameter(request, "endDate");
		String startDate = ParamUtils.getParameter(request, "startDate");
		HttpSession session=request.getSession();
		if (!StringUtils.isNotBlank(orgId)) {
			User user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		this.pager = new Page("pagerForm", request);
		if(!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.calculateDate),'yyyy-mm-dd') from tbl_rpc_total_day t  where wellName= '"+wellName+"' ";
			List list = this.service.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		
		if (!StringManagerUtils.isNotNull(startDate)) {
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-30);
		} else {
			
		}
		
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		json =  this.diagnosisTotalService.getDiagnosisTotalDynamicCurveData(wellName,selectedWellName,calculateDate, startDate,endDate);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getAnalysisAndAcqAndControlData")
	public String getAnalysisAndAcqAndControlData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		this.pager = new Page("pagerForm", request);
		String json =diagnosisTotalService.getAnalysisAndAcqAndControlData(id);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getPCPAnalysisAndAcqAndControlData")
	public String getPCPAnalysisAndAcqAndControlData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		this.pager = new Page("pagerForm", request);
		String json =diagnosisTotalService.getPCPAnalysisAndAcqAndControlData(id);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getDiagnosisTotalDataCurveData")
	public String getDiagnosisTotalDataCurveData() throws Exception {
		String json = "";
		wellName = ParamUtils.getParameter(request, "wellName");
		String endDate = ParamUtils.getParameter(request, "endDate");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String itemName = ParamUtils.getParameter(request, "itemName");
		String itemCode = ParamUtils.getParameter(request, "itemCode");
		String wellType = ParamUtils.getParameter(request, "wellType");
		String tableName="viw_rpc_total_day";
		if("400".equals(wellType)){//螺杆泵
			tableName="viw_pcp_total_day";
		}
		this.pager = new Page("pagerForm", request);
		if(!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.calculateDate),'yyyy-mm-dd') from "+tableName+" t  where wellName= '"+wellName+"' ";
			List list = this.service.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		
		if (!StringManagerUtils.isNotNull(startDate)) {
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-30);
		} else {
			
		}
		
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		if("400".equals(wellType)){//螺杆泵
			json =  this.diagnosisTotalService.getPCPDiagnosisDailyCurveData(wellName, startDate,endDate,itemName,itemCode);
		}else{
			json =  this.diagnosisTotalService.getRPCDiagnosisDailyCurveData(wellName, startDate,endDate,itemName,itemCode);
		}
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getScrewPumpDailyAnalysiCurveData")
	public String getScrewPumpDailyAnalysiCurveData()throws Exception{
		String calculateDate = ParamUtils.getParameter(request, "calculateDate");
		wellName = ParamUtils.getParameter(request, "wellName");
		String json = "";
		json = this.diagnosisTotalService.getScrewPumpDailyAnalysiCurveData(calculateDate,wellName);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}

	public List<DiagnosisAnalysisOnly> getList() {
		return list;
	}

	public void setList(List<DiagnosisAnalysisOnly> list) {
		this.list = list;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotals() {
		return totals;
	}

	public void setTotals(int totals) {
		this.totals = totals;
	}

	public String getwellName() {
		return wellName;
	}

	public void setwellName(String wellName) {
		this.wellName = wellName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}
}
