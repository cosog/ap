package com.gao.controller.monitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.Vector;

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
import com.gao.model.DataModels;
import com.gao.model.MonitorWaterInjectionWell;
import com.gao.model.User;
import com.gao.service.base.CommonDataService;
import com.gao.utils.Page;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;

/**
 * 动态监测模块
 * 
 * @author gao
 * @data 2014-5-08
 */
@Controller
@RequestMapping("/monitorDataController")
@Scope("prototype")
public class MonitorDataController extends BaseController {

	private static Log log = LogFactory.getLog(MonitorDataController.class);
	private static final long serialVersionUID = 1L;
	private String bjlx = "";
	@Autowired
	private CommonDataService commonDataService;
	private DataModels data;
	private String endDate;
	private String jh = "";
	private String jhh = "";
	private int limit;
	private List<MonitorWaterInjectionWell> list;
	private String orgId;
	private int page = 1;
	private String params;
	private String startDate;

	/**
	 * 用来导出采出井实时监测数据 Excel文件
	 * 
	 * @return null
	 * @throws Exception
	 * @author gao 2014-5-8
	 */
	@RequestMapping("/exportExcelMonitorData")
	public String exportExcelMonitorData() throws Exception {
		// TODO Auto-generated method stub
		String heads = ParamUtils.getParameter(request, "heads");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName=java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		jhh = ParamUtils.getParameter(request, "jhh");
		jh = ParamUtils.getParameter(request, "jh");
		jhh = java.net.URLDecoder.decode(jhh, "utf-8");
		jh = java.net.URLDecoder.decode(jh, "utf-8");
		bjlx = ParamUtils.getParameter(request, "bjlx");
		Vector<String> v = new Vector<String>();
		if (StringManagerUtils.isNotNull(jhh)) {
			v.add(jhh);
		}else{
			v.add(null);
		}
		if (StringManagerUtils.isNotNull(jh)) {
			v.add(jh);
		}else{
			v.add(null);
		}
		if (StringManagerUtils.isNotNull(bjlx)) {
			v.add(bjlx);
		}else{
			v.add(null);
		}
		orgId = ParamUtils.getParameter(request, "orgId");
		orgId=this.findCurrentUserOrgIdInfo(orgId);
		String path = request.getSession().getServletContext().getRealPath("//");
//		HttpServletResponse response = ServletActionContext.getResponse();
		Vector<String> imageUrl = new Vector<String>();
		imageUrl.add(path + "/images/zh_CN/SurfaceCard.png");
		imageUrl.add(path + "/images/zh_CN/PumpCard.png");
		imageUrl.add(path + "/images/zh_CN/ElectricCurve.png");
		this.commonDataService.exportDataExcel(response, fileName, "采出井实时监测数据", imageUrl, heads, fields, orgId, "pumpUnitMonitor", v);

		return null;

	}

	/**
	 * 用来导出采出井历史查询Excel文件
	 * 
	 * @return null
	 * @throws Exception
	 * @author gao 2014-5-8
	 * 
	 */
	@RequestMapping("/exportExcelMonitorHistoryData")
	@SuppressWarnings("rawtypes")
	public String exportExcelMonitorHistoryData() throws Exception {
		// TODO Auto-generated method stub
		log.debug("monitor enter==");
		Vector<String> v = new Vector<String>();
		orgId = ParamUtils.getParameter(request, "orgId");
		jhh = ParamUtils.getParameter(request, "jhh");
		jh = ParamUtils.getParameter(request, "jh");
		endDate = ParamUtils.getParameter(request, "endDate");
		startDate = ParamUtils.getParameter(request, "startDate");
		bjlx = ParamUtils.getParameter(request, "bjlx");
		jhh = java.net.URLDecoder.decode(jhh, "utf-8");
		jh = java.net.URLDecoder.decode(jh, "utf-8");
		String heads = ParamUtils.getParameter(request, "heads");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName=java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String start_Date = "";
		String end_Date = "";
		String sql = "select to_char(Max(acquisitionTime),'yyyy-mm-dd')   from tbl_rpc_diagram_hist t";
		List listd = this.commonDataService.reportDateJssj(sql);
		if (listd.size() > 0) {
			end_Date = listd.get(0).toString();
		} else {
			end_Date = StringManagerUtils.getCurrentTime();
		}
		start_Date = end_Date;
		// 动态构建参数集合V
		if (StringManagerUtils.isNotNull(jhh)) {
			v.add(jhh);
		}else{
			v.add(null);
		}
		if (StringManagerUtils.isNotNull(jh)) {
			v.add(jh);
		}else{
			v.add(null);
		}
		if (StringManagerUtils.isNotNull(bjlx)) {
			v.add(bjlx);
		}else{
			v.add(null);
		}
		if (!"".equals(endDate) && null != endDate && endDate.length() > 0) {
		} else {
			// endDate = StringManagerUtils.getCurrentTime();
			endDate = end_Date;
		}
		v.add(StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate)));
		if (!"".equals(startDate) && null != startDate && startDate.length() > 0) {
		} else {
			// startDate =
			// StringManagerUtils.formatStringDate(StringManagerUtils.minusFiveDate(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime())));
			startDate = start_Date;
		}
		v.add(startDate);
		User user = null;
		HttpSession session=request.getSession();
		user = (User) session.getAttribute("userLogin");
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String path = request.getSession().getServletContext().getRealPath("//");
//		HttpServletResponse response = ServletActionContext.getResponse();
		Vector<String> imageUrl = new Vector<String>();
		imageUrl.add(path + "/images/zh_CN/SurfaceCard.png");
		imageUrl.add(path + "/images/zh_CN/PumpCard.png");
		imageUrl.add(path + "/images/zh_CN/ElectricCurve.png");
		this.commonDataService.exportDataWellExcel(response, fileName, "采出井历史查询数据", imageUrl, heads, fields, orgId, "pumpingUnitHis", v);

		return null;

	}

	/**
	 * 导出注水井实时监测Excel报表文件
	 * 
	 * @return null
	 * @throws Exception
	 * @author gao 2014-5-8
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/exportWaterInjectionExcelMonitorData")
	public String exportWaterInjectionExcelMonitorData() throws Exception {
		// TODO Auto-generated method stub
		log.debug("monitor enter==");
		// PrintWriter out = response.getWriter();
		request.setCharacterEncoding("UTF-8");
		String heads = ParamUtils.getParameter(request, "heads");
		String fields = ParamUtils.getParameter(request, "fields");
		heads = java.net.URLDecoder.decode(heads, "utf-8");
		fields = java.net.URLDecoder.decode(fields, "utf-8");
		orgId = ParamUtils.getParameter(request, "orgId");
		jhh = ParamUtils.getParameter(request, "jhh");
		jh = ParamUtils.getParameter(request, "jh");
		endDate = ParamUtils.getParameter(request, "endDate");
		startDate = ParamUtils.getParameter(request, "startDate");
		bjlx = ParamUtils.getParameter(request, "bjlx");
		jh = java.net.URLDecoder.decode(jh, "utf-8");
		Vector<String> v = new Vector<String>();
		if (StringManagerUtils.isNotNull(jh)) {
			v.add(jh);
		}else{
			v.add(null);
		}
		if (StringManagerUtils.isNotNull(bjlx)) {
			v.add(bjlx);
		}else{
			v.add(null);
		}
		String start_Date = "";
		String end_Date = "";
		String sql = "select to_char(Max(t.cjsj),'yyyy-mm-dd')  from v_002_22_dynamicmonitorhis_cn t ";
		List listd = this.commonDataService.reportDateJssj(sql);
		if (listd.size() > 0) {
			end_Date = listd.get(0).toString();
		} else {
			end_Date = StringManagerUtils.getCurrentTime();
		}
		start_Date = StringManagerUtils.formatStringDate(StringManagerUtils.minusFivemDate(StringManagerUtils.stringToDate(end_Date)));

		if (!"".equals(startDate) && null != startDate && startDate.length() > 0) {
		} else {
			// startDate =
			// StringManagerUtils.formatStringDate(StringManagerUtils.minusFiveDate(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime())));
			startDate = start_Date;
		}
		v.add(startDate);
		if (!"".equals(endDate) && null != endDate && endDate.length() > 0) {
		} else {
			// endDate = StringManagerUtils.getCurrentTime();
			endDate = end_Date;
		}
		v.add(StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate)));
		orgId=this.findCurrentUserOrgIdInfo(orgId);

		String path = request.getSession().getServletContext().getRealPath("//");
		Vector<String> imageUrl = new Vector<String>();
		imageUrl.add(path + "/images/zh_CN/SurfaceCard.png");
		imageUrl.add(path + "/images/zh_CN/PumpCard.png");
		imageUrl.add(path + "/images/zh_CN/ElectricCurve.png");
		try {
			this.commonDataService.exportDataExcel(response, "MonitorWaterInjectionWell_CN", "注入井实时监测数据", imageUrl, heads, fields, orgId, "waterInjectionWellMonitor", v);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 导出注水井历史查询Excel报表文件
	 * 
	 * @return null
	 * @throws Exception
	 * @author gao 2014-5-8
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/exportWaterInjectionHistoryExcelMonitorData")
	public String exportWaterInjectionHistoryExcelMonitorData() throws Exception {
		// TODO Auto-generated method stub
		log.debug("monitor enter==");
		// PrintWriter out = response.getWriter();
		request.setCharacterEncoding("UTF-8");
		String heads = ParamUtils.getParameter(request, "heads");
		String fields = ParamUtils.getParameter(request, "fields");
		heads = java.net.URLDecoder.decode(heads, "utf-8");
		fields = java.net.URLDecoder.decode(fields, "utf-8");
		orgId = ParamUtils.getParameter(request, "orgId");
		jhh = ParamUtils.getParameter(request, "jhh");
		jh = ParamUtils.getParameter(request, "jh");
		endDate = ParamUtils.getParameter(request, "endDate");
		startDate = ParamUtils.getParameter(request, "startDate");
		bjlx = ParamUtils.getParameter(request, "bjlx");
		jh = java.net.URLDecoder.decode(jh, "utf-8");
		Vector<String> v = new Vector<String>();
		if (StringManagerUtils.isNotNull(jh)) {
			v.add(jh);
		}else{
			v.add(null);
		}
		if (StringManagerUtils.isNotNull(bjlx)) {
			v.add(bjlx);
		}else{
			v.add(null);
		}
		String start_Date = "";
		String end_Date = "";
		String sql = "select to_char(Max(t.cjsj),'yyyy-mm-dd') from v_002_22_dynamicmonitorhis_cn t  where cjsj is not null  ";
		List listd = this.commonDataService.reportDateJssj(sql);
		if (listd.size() > 0) {
			end_Date = listd.get(0) ==null?StringManagerUtils.getCurrentTime() : listd.get(0).toString();
		} else {
			end_Date = StringManagerUtils.getCurrentTime();
		}
		start_Date=end_Date;
		//start_Date = StringManagerUtils.formatStringDate(StringManagerUtils.minusFivemDate(StringManagerUtils.stringToDate(end_Date)));

		if (!"".equals(startDate) && null != startDate && startDate.length() > 0) {
		} else {
			// startDate =
			// StringManagerUtils.formatStringDate(StringManagerUtils.minusFiveDate(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime())));
			startDate = start_Date;
		}
		v.add(startDate);
		if (!"".equals(endDate) && null != endDate && endDate.length() > 0) {
		} else {
			// endDate = StringManagerUtils.getCurrentTime();
			endDate = end_Date;
		}
		v.add(StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate)));
		orgId=this.findCurrentUserOrgIdInfo(orgId);
		String path = request.getSession().getServletContext().getRealPath("//");
		Vector<String> imageUrl = new Vector<String>();
		imageUrl.add(path + "/images/zh_CN/SurfaceCard.png");
		imageUrl.add(path + "/images/zh_CN/PumpCard.png");
		imageUrl.add(path + "/images/zh_CN/ElectricCurve.png");
		try {
			this.commonDataService.exportDataWellExcel(response, "MonitorWaterInjectionWellHis_CN", "注入井历史监测数据", imageUrl, heads, fields, orgId, "waterInjectionWellHis", v);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 创建动态监测采出井实时监测 grid json 数据的核心方法
	 * 
	 * @return null
	 * @throws Exception
	 * @author gao 2014-5-8
	 * 
	 */
	@RequestMapping("/showMonitorData")
	public String showMonitorData() throws Exception {

		this.pager = new Page("pagerForm", request);
		// TODO Auto-generated method stub
		log.debug("monitor enter==");
		Vector<String> v = new Vector<String>();
		orgId = ParamUtils.getParameter(request, "orgId");
		orgId=this.findCurrentUserOrgIdInfo(orgId);
		jhh = ParamUtils.getParameter(request, "jhh");
		jh = ParamUtils.getParameter(request, "jh");
		bjlx = ParamUtils.getParameter(request, "bjlx");
		// 动态构建参数集合V
		if (StringManagerUtils.isNotNull(jhh)) {
			v.add(jhh);
		}else{
			v.add(null);
		}
		if (StringManagerUtils.isNotNull(jh)) {
			v.add(jh);
		}else{
			v.add(null);
		}
		if (StringManagerUtils.isNotNull(bjlx)) {
			v.add(bjlx);
		}else{
			v.add(null);
		}
		String json = "";
		json = commonDataService.findCommonModuleDataById(pager, orgId, "pumpUnitMonitor", v);
		/****
		 * 动态拼接前台EXT需要的json数据信息 ，包括表头及具体的数据信息
		 * **/
		// map.put("v", v);
		// map.put("orgId", orgId);
		log.debug("BaseParseAction json strBuf===" + json);
//		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
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

	/**
	 * 创建动态监测采出井历史查询 grid json 数据的核心方法
	 * 
	 * @return null
	 * @throws Exception
	 * @author gao 2014-5-8
	 * 
	 */
	/**
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/showMonitorHistoryData")
	public String showMonitorHistoryData() throws Exception {

		// TODO Auto-generated method stub
		Vector<String> v = new Vector<String>();
		// orgId = ParamUtils.getParameter(request, "orgId");
		orgId = ParamUtils.getParameter(request, "orgId");
		jhh = ParamUtils.getParameter(request, "jhh");
		jh = ParamUtils.getParameter(request, "jh");
		endDate = ParamUtils.getParameter(request, "endDate");
		startDate = ParamUtils.getParameter(request, "startDate");
		bjlx = ParamUtils.getParameter(request, "bjlx");
		String start_Date = "";
		String end_Date = "";
		String sql = " select to_char(Max(acquisitionTime),'yyyy-mm-dd')   from tbl_rpc_diagram_hist t where jsbz=1";
		List listd = this.commonDataService.reportDateJssj(sql);
		if (listd.size() > 0&&listd.get(0)!=null&&!listd.get(0).toString().equals("null")) {
			end_Date = listd.get(0).toString();
		} else {
			end_Date = StringManagerUtils.getCurrentTime();
		}
		start_Date = end_Date;
		// start_Date = StringManagerUtils.formatStringDate(StringManagerUtils
		// .minusDate(StringManagerUtils.stringToDate(end_Date)));
		// 动态构建参数集合V
		if (StringManagerUtils.isNotNull(jhh)) {
			v.add(jhh);
		}else{
			v.add(null);
		}
		if (StringManagerUtils.isNotNull(jh)) {
			v.add(jh);
		}else{
			v.add(null);
		}
		if (StringManagerUtils.isNotNull(bjlx)) {
			v.add(bjlx);
		}else{
			v.add(null);
		}
		
		if (!"".equals(endDate) && null != endDate && endDate.length() > 0) {
		} else {
//			 endDate = StringManagerUtils.getCurrentTime();
			endDate = end_Date;
		}
		v.add(StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate)));
		
		if (!"".equals(startDate) && null != startDate && startDate.length() > 0) {
		} else {
			// startDate
			// =StringManagerUtils.formatStringDate(StringManagerUtils.minusDate(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime())));
			startDate = start_Date;
		}
		v.add(startDate);
		String json = "";
		orgId=this.findCurrentUserOrgIdInfo(orgId);
		this.pager = new Page("pagerForm", request);
		pager.setStart_date(start_Date);
		pager.setEnd_date(end_Date);
		json = commonDataService.findMonitorHistoryDataById(pager, orgId, "pumpingUnitHis", v);
		/****
		 * 动态拼接前台EXT需要的json数据信息 ，包括表头及具体的数据信息
		 * **/
//		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
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
	/**
	 * 创建动态监测注入井实时监测 grid json 数据的核心方法
	 * 
	 * @return null
	 * @throws Exception
	 * @author gao 2014-5-8
	 * 
	 */
	@RequestMapping("/showMonitorInWellData")
	public String showMonitorInWellData() throws Exception {
		// TODO Auto-generated method stub
		this.pager = new Page("pagerForm", request);
		Vector<String> v = new Vector<String>();
		// orgId = ParamUtils.getParameter(request, "orgId");
		jh = ParamUtils.getParameter(request, "jh");
		bjlx = ParamUtils.getParameter(request, "bjlx");
		if (StringManagerUtils.isNotNull(jh)) {
			v.add(jh);
		}else{
			v.add(null);
		}
		if (StringManagerUtils.isNotNull(bjlx)) {
			v.add(bjlx);
		}else{
			v.add(null);
		}

		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		orgId=this.findCurrentUserOrgIdInfo(orgId);
		json = commonDataService.findCommonModuleDataById(pager, orgId, "waterInjectionWellMonitor", v);
		log.debug("showMonitorInWellData json strBuf===" + json);
//		HttpServletResponse response = ServletActionContext.getResponse();
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

	/**
	 * 创建动态监测注入井历史查询 grid json 数据的核心方法
	 * 
	 * @return null
	 * @throws Exception
	 * @author gao 2014-5-8
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/showWaterInjectionWellHistoryData")
	public String showWaterInjectionWellHistoryData() throws Exception {

		// TODO Auto-generated method stub
		log.debug("showWaterInjectionWellHistoryData enter==");
		Vector<String> v = new Vector<String>();
		// orgId = ParamUtils.getParameter(request, "orgId");
		orgId = ParamUtils.getParameter(request, "orgId");
		jhh = ParamUtils.getParameter(request, "jhh");
		jh = ParamUtils.getParameter(request, "jh");
		endDate = ParamUtils.getParameter(request, "endDate");
		startDate = ParamUtils.getParameter(request, "startDate");
		bjlx = ParamUtils.getParameter(request, "bjlx");
		String start_Date = "";
		String end_Date = "";
		String sql = " select to_char(Max(t.cjsj),'yyyy-mm-dd') from v_002_22_dynamicmonitorhis_cn t  where cjsj is not null ";
		List listd = this.commonDataService.reportDateJssj(sql);
		if (listd.size() > 0) {
			end_Date = listd.get(0) ==null?StringManagerUtils.getCurrentTime() : listd.get(0).toString();
		} else {
			end_Date = StringManagerUtils.getCurrentTime();
		}
		start_Date=end_Date;
		//start_Date = StringManagerUtils.formatStringDate(StringManagerUtils.minusFivemDate(StringManagerUtils.stringToDate(end_Date)));
		// 动态构建参数集合V

		if (StringManagerUtils.isNotNull(jh)) {
			v.add(jh);
		}else{
			v.add(null);
		}
		if (StringManagerUtils.isNotNull(bjlx)) {
			v.add(bjlx);
		}else{
			v.add(null);
		}
		if (!"".equals(startDate) && null != startDate && startDate.length() > 0) {
		} else {
			// startDate =
			// StringManagerUtils.formatStringDate(StringManagerUtils.minusFiveDate(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime())));
			startDate = start_Date;
		}
		v.add(startDate);
		if (!"".equals(endDate) && null != endDate && endDate.length() > 0) {
		} else {
			// endDate = StringManagerUtils.getCurrentTime();
			endDate = end_Date;
		}
		v.add(StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate)));
		String json = "";
		orgId=this.findCurrentUserOrgIdInfo(orgId);
		this.pager = new Page("pagerForm", request);
		pager.setStart_date(start_Date);
		pager.setEnd_date(end_Date);
		json = commonDataService.findMonitorHistoryDataById(pager, orgId, "waterInjectionWellHis", v);
		/****
		 * 动态拼接前台EXT需要的json数据信息 ，包括表头及具体的数据信息
		 * **/
		log.debug("BaseParseAction json strBuf===" + json);
//		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
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

	@RequestMapping("/showDemo")
	public String showDemo() throws Exception {
		this.pager = new Page("pagerForm", request);
		orgId=ParamUtils.getParameter(request, "orgId");
		Vector<String> v=new Vector<String>();
		User user = null;
		HttpSession session=request.getSession();
		user = (User) session.getAttribute("userLogin");
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String 	json = commonDataService.findCommonMuchHeadModuleDataById(pager, "userInfo", v);
		/****
		 * 动态拼接前台EXT需要的json数据信息 ，包括表头及具体的数据信息
		 * **/
//		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
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
	
	public String getBjlx() {
		return bjlx;
	}

	public DataModels getData() {
		return data;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getJh() {
		return jh;
	}

	public String getJhh() {
		return jhh;
	}

	public int getLimit() {
		return limit;
	}

	public List<MonitorWaterInjectionWell> getList() {
		return list;
	}

	public String getOrgId() {
		return orgId;
	}

	public int getPage() {
		return page;
	}

	public String getParams() {
		return params;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setBjlx(String bjlx) {
		this.bjlx = bjlx;
	}

	public void setData(DataModels data) {
		this.data = data;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	public void setJhh(String jhh) {
		this.jhh = jhh;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setList(List<MonitorWaterInjectionWell> list) {
		this.list = list;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

}
