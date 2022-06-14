package com.cosog.controller.calculateManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.model.User;
import com.cosog.model.gridmodel.CalculateManagerHandsontableChangedData;
import com.cosog.model.gridmodel.ElecInverCalculateManagerHandsontableChangedData;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.calculateManager.CalculateManagerService;
import com.cosog.utils.Config;
import com.cosog.utils.Page;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.CLOB;

/**
 * 计算结果管理controller
 * 
 * @author zhao 2018-11-30
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/calculateManagerController")
@Scope("prototype")
public class CalculateManagerController extends BaseController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private CalculateManagerService<?> calculateManagerService;
	@Autowired
	private CommonDataService service;
	private int page;
	private int limit;
	private int totals;
	private String wellName;
	private String orgId;
	
	@RequestMapping("/getCalculateResultData")
	public String getCalculateResultData() throws Exception {
		orgId = ParamUtils.getParameter(request, "orgId");
		wellName = ParamUtils.getParameter(request, "wellName");
		
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate = ParamUtils.getParameter(request, "endDate");
		String calculateSign = ParamUtils.getParameter(request, "calculateSign");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
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
		if(StringManagerUtils.stringToInteger(deviceType)!=0){
			tableName="tbl_pcpacqdata_hist";
		}
		if(!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.acqTime),'yyyy-mm-dd') from "+tableName+" t";
			List list = this.service.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),0);
		}
//		startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-120);
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		
		String json = calculateManagerService.getCalculateResultData(orgId, wellName, pager,deviceType,startDate,endDate,calculateSign,calculateType);
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
	
	@RequestMapping("/getWellList")
	public String getWellList() throws Exception {
		orgId = ParamUtils.getParameter(request, "orgId");
		wellName = ParamUtils.getParameter(request, "wellName");
		
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate = ParamUtils.getParameter(request, "endDate");
		String calculateSign = ParamUtils.getParameter(request, "calculateSign");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
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
		if(StringManagerUtils.stringToInteger(deviceType)!=0){
			tableName="tbl_pcpacqdata_hist";
		}
		if(!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.acqTime),'yyyy-mm-dd') from "+tableName+" t";
			List list = this.service.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),0);
		}
//		startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-120);
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		
		String json = calculateManagerService.getWellList(orgId, wellName, pager,deviceType,startDate,endDate,calculateSign,calculateType);
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
	
	@SuppressWarnings("unused")
	@RequestMapping("/saveRecalculateData")
	public String saveRecalculateData() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String orgid=user.getUserorgids();
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		Gson gson = new Gson();
		String json ="{success:true}";
		if("1".equals(calculateType) || "2".equals(calculateType)){
			java.lang.reflect.Type type = new TypeToken<CalculateManagerHandsontableChangedData>() {}.getType();
			CalculateManagerHandsontableChangedData calculateManagerHandsontableChangedData=gson.fromJson(data, type);
			if("0".equals(deviceType)){
				this.calculateManagerService.saveReCalculateData(calculateManagerHandsontableChangedData);
			}else if("1".equals(deviceType)){
				this.calculateManagerService.saveRPMReCalculateData(calculateManagerHandsontableChangedData);
			}
			json ="{success:true}";
		}else if("5".equals(calculateType)){
			
		}
		
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		
		return null;
	}
	
	
	/**
	 * <p>
	 * 描述：计算维护模块模块计算标志下拉框
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getCalculateStatusList")
	public String getCalculateStatusList() throws Exception {
		orgId = ParamUtils.getParameter(request, "orgId");
		String welName = ParamUtils.getParameter(request, "welName");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate = ParamUtils.getParameter(request, "endDate");
		if (!StringManagerUtils.isNotNull(orgId)) {
			User user = null;
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserOrgid();
			}
		}
		String tableName="tbl_rpcacqdata_hist";
		if(StringManagerUtils.stringToInteger(deviceType)!=0){
			tableName="tbl_pcpacqdata_hist";
		}
		if(!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.acqTime),'yyyy-mm-dd') from "+tableName+" t";
			List list = this.service.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),0);
		}
		String json = this.calculateManagerService.getCalculateStatusList(orgId,welName,deviceType,startDate,endDate);
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
	
	/**
	 * <p>
	 * 描述：关联当前生产数据重新计算历史
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/recalculateByProductionData")
	public String recalculateByProductionData() throws Exception {
		orgId = ParamUtils.getParameter(request, "orgId");
		String wellName = ParamUtils.getParameter(request, "wellName");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate = ParamUtils.getParameter(request, "endDate");
		String calculateSign = ParamUtils.getParameter(request, "calculateSign");
		if (!StringManagerUtils.isNotNull(orgId)) {
			User user = null;
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserOrgid();
			}
		}
		this.calculateManagerService.recalculateByProductionData(orgId,wellName,deviceType,startDate,endDate,calculateSign);
		String json ="{success:true}";
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
	
	/**
	 * <p>
	 * 描述：导出功图诊断计产请求数据
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportCalculateRequestData")
	public String exportCalculateRequestData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd_HHmmss");//设置日期格式
		
		String wellName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "wellName"),"utf-8");
		String recordId=ParamUtils.getParameter(request, "recordId");
		String acqTime=ParamUtils.getParameter(request, "acqTime");
		String calculateType=ParamUtils.getParameter(request, "calculateType");
		String json=calculateManagerService.getCalculateRequestData(recordId,wellName, acqTime,calculateType);
		
		Date date = df.parse(acqTime);
		acqTime=df2.format(date);
		
		String fileName="请求数据-"+wellName+"-"+acqTime+".json";
		if("1".equals(calculateType)){
			fileName="请求数据-"+wellName+"-"+acqTime+".json";
		}if("2".equals(calculateType)){
			fileName="转速计产请求数据-"+wellName+"-"+acqTime+".json";
		}else if("5".equals(calculateType)){
			fileName="反演请求数据-"+wellName+"-"+acqTime+".json";
		}
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		try {
        	response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
            InputStream in = new FileInputStream(file);
            int len = 0;
            byte[] buffer = new byte[1024];
            OutputStream out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer,0,len);
            }
            in.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		StringManagerUtils.deleteFile(path);
		return null;
	}
	
	@RequestMapping("/getTotalCalculateResultData")
	public String getTotalCalculateResultData() throws Exception {
		orgId = ParamUtils.getParameter(request, "orgId");
		wellName = ParamUtils.getParameter(request, "wellName");
		
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate = ParamUtils.getParameter(request, "endDate");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String tableName="tbl_rpcdailycalculationdata";
		if(StringManagerUtils.stringToInteger(deviceType)!=0){
			tableName="tbl_pcpdailycalculationdata";
		}
		if(!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.caldate),'yyyy-mm-dd') from "+tableName+" t";
			List list = this.service.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),0);
		}
//		startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-120);
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		
		String json = calculateManagerService.getTotalCalculateResultData(orgId, wellName, pager,deviceType,startDate,endDate,calculateType);
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
	
	@RequestMapping("/reTotalCalculate")
	public String reTotalCalculate() throws Exception {
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String reCalculateDate = ParamUtils.getParameter(request, "reCalculateDate");
		String json = calculateManagerService.reTotalCalculate(deviceType,reCalculateDate);
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
	
	@RequestMapping("/exportTotalCalculateRequestData")
	public String exportTotalCalculateRequestData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		
		String recordId=ParamUtils.getParameter(request, "recordId");
		String wellName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "wellName"),"utf-8");
		String wellId=ParamUtils.getParameter(request, "wellId");
		String calDate=ParamUtils.getParameter(request, "calDate");
		String deviceType=ParamUtils.getParameter(request, "deviceType");
		String json=calculateManagerService.exportTotalCalculateRequestData(deviceType,recordId,wellId,wellName,calDate);
		String fileName="汇总请求数据-"+wellName+"-"+calDate+".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		try {
        	response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
            InputStream in = new FileInputStream(file);
            int len = 0;
            byte[] buffer = new byte[1024];
            OutputStream out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer,0,len);
            }
            in.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		StringManagerUtils.deleteFile(path);
		return null;
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
	
	

	public String getWellName() {
		return wellName;
	}

	public void setWellName(String wellName) {
		this.wellName = wellName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}
