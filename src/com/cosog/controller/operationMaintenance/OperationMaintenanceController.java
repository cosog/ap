package com.cosog.controller.operationMaintenance;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.model.DeviceTypeInfo;
import com.cosog.model.Role;
import com.cosog.model.User;
import com.cosog.model.drive.TotalCalItemsToReportUnitSaveData;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.operationMaintenance.OperationMaintenanceService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Config;
import com.cosog.utils.Constants;
import com.cosog.utils.DeviceTypeInfoRecursion;
import com.cosog.utils.OEMConfigFile;
import com.cosog.utils.Page;
import com.cosog.utils.PagingConstants;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@Controller
@RequestMapping("/operationMaintenanceController")
@Scope("prototype")
public class OperationMaintenanceController  extends BaseController {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(OperationMaintenanceController.class);
	@Autowired
	private CommonDataService service;
	@Autowired
	private OperationMaintenanceService operationMaintenanceService;
	@Autowired
	private OperationMaintenanceService<DeviceTypeInfo> deviceTypeMaintenanceService;
	
	
	@SuppressWarnings("unused")
	@RequestMapping("/loadOemConfigInfo")
	public String loadOemConfigInfo() throws Exception {
		String json = "";
		Gson gson=new Gson();
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		OEMConfigFile configFile=Config.getInstance().oemConfigFile;
		if(configFile!=null){
			json="{\"success\":true,\"configFile\":"+gson.toJson(configFile)+"}";
		}else{
			json="{\"success\":false}";
		}
		
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@SuppressWarnings({ "unused", "static-access" })
	@RequestMapping("/updateOemConfigInfo")
	public String updateOemConfigInfo() throws Exception {
		String json = "";
		Gson gson=new Gson();
		java.lang.reflect.Type type=null;
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		
		String loginLanguage = ParamUtils.getParameter(request, "operationMaintenance.loginLanguage");
		String showLogo = ParamUtils.getParameter(request, "operationMaintenance.showLogo");
		String printLog = ParamUtils.getParameter(request, "operationMaintenance.printLog");
		String printAdLog = ParamUtils.getParameter(request, "operationMaintenance.printAdLog");
		String printExceptionLog = ParamUtils.getParameter(request, "operationMaintenance.printExceptionLog");
		String timeEfficiencyUnit = ParamUtils.getParameter(request, "operationMaintenance.timeEfficiencyUnit");
		String resourceMonitoringSaveData = ParamUtils.getParameter(request, "operationMaintenance.resourceMonitoringSaveData");
		String exportLimit = ParamUtils.getParameter(request, "operationMaintenance.exportLimit");
		String simulateAcqEnable = ParamUtils.getParameter(request, "operationMaintenance.simulateAcqEnable");
		String sendCycle = ParamUtils.getParameter(request, "operationMaintenance.sendCycle");
		
		
		String configFileStr = ParamUtils.getParameter(request, "configFile");
		
		type = new TypeToken<OEMConfigFile>() {}.getType();
		OEMConfigFile updatedOEMConfigFile=gson.fromJson(configFileStr, type);
		
		boolean r=false;
		
		if(updatedOEMConfigFile!=null){
			if(updatedOEMConfigFile.getOthers()!=null){
				Config.getInstance().oemConfigFile.getOthers().setLoginLanguage(updatedOEMConfigFile.getOthers().getLoginLanguage());
				Config.getInstance().oemConfigFile.getOthers().setShowLogo(updatedOEMConfigFile.getOthers().getShowLogo());
				Config.getInstance().oemConfigFile.getOthers().setPrintLog(updatedOEMConfigFile.getOthers().getPrintLog());
				Config.getInstance().oemConfigFile.getOthers().setPrintAdLog(updatedOEMConfigFile.getOthers().getPrintAdLog());
				Config.getInstance().oemConfigFile.getOthers().setPrintExceptionLog(updatedOEMConfigFile.getOthers().getPrintExceptionLog());
				Config.getInstance().oemConfigFile.getOthers().setTimeEfficiencyUnit(updatedOEMConfigFile.getOthers().getTimeEfficiencyUnit());
				Config.getInstance().oemConfigFile.getOthers().setResourceMonitoringSaveData(updatedOEMConfigFile.getOthers().getResourceMonitoringSaveData());
				Config.getInstance().oemConfigFile.getOthers().setExportLimit(updatedOEMConfigFile.getOthers().getExportLimit());
				Config.getInstance().oemConfigFile.getOthers().setSimulateAcqEnable(updatedOEMConfigFile.getOthers().getSimulateAcqEnable());
				Config.getInstance().oemConfigFile.getOthers().setSendCycle(updatedOEMConfigFile.getOthers().getSendCycle());
			}
			
			if(updatedOEMConfigFile.getDataVacuate()!=null){
				Config.getInstance().oemConfigFile.getDataVacuate().setVacuateRecord(updatedOEMConfigFile.getDataVacuate().getVacuateRecord());
				Config.getInstance().oemConfigFile.getDataVacuate().setSaveInterval(updatedOEMConfigFile.getDataVacuate().getSaveInterval());
				Config.getInstance().oemConfigFile.getDataVacuate().setVacuateThreshold(updatedOEMConfigFile.getDataVacuate().getVacuateThreshold());
			}
			
			if(updatedOEMConfigFile.getDatabaseMaintenance()!=null){
				Config.getInstance().oemConfigFile.getDatabaseMaintenance().setCycle(updatedOEMConfigFile.getDatabaseMaintenance().getCycle());
				Config.getInstance().oemConfigFile.getDatabaseMaintenance().setStartTime(updatedOEMConfigFile.getDatabaseMaintenance().getStartTime());
				Config.getInstance().oemConfigFile.getDatabaseMaintenance().setEndTime(updatedOEMConfigFile.getDatabaseMaintenance().getEndTime());
				
				if(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig()!=null){
					if(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getAcqdata_hist()!=null){
						Config.getInstance().oemConfigFile.getDatabaseMaintenance().getTableConfig().getAcqdata_hist().setEnabled(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getAcqdata_hist().getEnabled());
						Config.getInstance().oemConfigFile.getDatabaseMaintenance().getTableConfig().getAcqdata_hist().setRetentionTime(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getAcqdata_hist().getRetentionTime());
					}
					
					if(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getAcqrawdata()!=null){
						Config.getInstance().oemConfigFile.getDatabaseMaintenance().getTableConfig().getAcqrawdata().setEnabled(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getAcqrawdata().getEnabled());
						Config.getInstance().oemConfigFile.getDatabaseMaintenance().getTableConfig().getAcqrawdata().setRetentionTime(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getAcqrawdata().getRetentionTime());
					}
					
					if(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getAlarminfo_hist()!=null){
						Config.getInstance().oemConfigFile.getDatabaseMaintenance().getTableConfig().getAlarminfo_hist().setEnabled(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getAlarminfo_hist().getEnabled());
						Config.getInstance().oemConfigFile.getDatabaseMaintenance().getTableConfig().getAlarminfo_hist().setRetentionTime(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getAlarminfo_hist().getRetentionTime());
					}
					
					if(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getDailytotalcalculate_hist()!=null){
						Config.getInstance().oemConfigFile.getDatabaseMaintenance().getTableConfig().getDailytotalcalculate_hist().setEnabled(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getDailytotalcalculate_hist().getEnabled());
						Config.getInstance().oemConfigFile.getDatabaseMaintenance().getTableConfig().getDailytotalcalculate_hist().setRetentionTime(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getDailytotalcalculate_hist().getRetentionTime());
					}
					
					if(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getDailycalculationdata()!=null){
						Config.getInstance().oemConfigFile.getDatabaseMaintenance().getTableConfig().getDailycalculationdata().setEnabled(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getDailycalculationdata().getEnabled());
						Config.getInstance().oemConfigFile.getDatabaseMaintenance().getTableConfig().getDailycalculationdata().setRetentionTime(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getDailycalculationdata().getRetentionTime());
					}
					
					if(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getTimingcalculationdata()!=null){
						Config.getInstance().oemConfigFile.getDatabaseMaintenance().getTableConfig().getTimingcalculationdata().setEnabled(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getTimingcalculationdata().getEnabled());
						Config.getInstance().oemConfigFile.getDatabaseMaintenance().getTableConfig().getTimingcalculationdata().setRetentionTime(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getTimingcalculationdata().getRetentionTime());
					}
					
					if(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getAcqdata_vacuate()!=null){
						Config.getInstance().oemConfigFile.getDatabaseMaintenance().getTableConfig().getAcqdata_vacuate().setEnabled(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getAcqdata_vacuate().getEnabled());
						Config.getInstance().oemConfigFile.getDatabaseMaintenance().getTableConfig().getAcqdata_vacuate().setRetentionTime(updatedOEMConfigFile.getDatabaseMaintenance().getTableConfig().getAcqdata_vacuate().getRetentionTime());
					}
				}
			}
			
			r=Config.getInstance().updateConfig();
			r=Config.getInstance().updateOemConfigFile(Config.getInstance().oemConfigFile);
		}
		
		
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		OEMConfigFile configFile=Config.getInstance().oemConfigFile;
		json = "{success:true,msg:"+r+"}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/batchExportModuleList")
	public String batchExportModuleList() throws IOException {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if (user != null) {
			language = "" + user.getLanguageName();
		}
		String json = operationMaintenanceService.batchExportModuleList(user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/importedFilePermissionVerification")
	public String importedFilePermissionVerification() throws IOException {
		HttpSession session=request.getSession();
		String code = ParamUtils.getParameter(request, "code");
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if (user != null) {
			language = "" + user.getLanguageName();
		}
		String json = operationMaintenanceService.importedFilePermissionVerification(user,code);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getOperationMaintenanceMonitorCurveData")
	public String getOperationMaintenanceMonitorCurveData() throws Exception {
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate = ParamUtils.getParameter(request, "endDate");
		
		if(user!=null){
			if(!StringManagerUtils.isNotNull(endDate)){
				String sql = " select to_char(max(t.acqTime)+1/(24*60),'yyyy-mm-dd hh24:mi:ss') from TBL_RESOURCEMONITORING t ";
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
			json = operationMaintenanceService.getOperationMaintenanceMonitorCurveData(startDate,endDate,user);
		}
		
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/constructDeviceTypeTreeData")
	public String constructDeviceTypeTreeData() throws Exception {
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		List<Integer> languageList=null;
		if(user!=null){
			language=user.getLanguageName();
			languageList=user.getLanguageList();
		}
		DeviceTypeInfoRecursion r = new DeviceTypeInfoRecursion();
		List<DeviceTypeInfo> list = deviceTypeMaintenanceService.operationMaintenanceService(DeviceTypeInfo.class,user);
		boolean flag = false;
		for (DeviceTypeInfo tabInfo : list) {
			if (!r.hasParent(list, tabInfo)) {
				flag = true;
				json = r.recursionMobileTabMaintenanceTreeFn(list, tabInfo,language);
			}

		}
		if (flag == false && list.size() > 0) {
			for (DeviceTypeInfo tabInfo : list) {
				json = r.recursionTabMaintenanceTreeFn(list, tabInfo,language);
			}

		}
		json = r.modifyStr(json).replaceAll("null", "");
		
		json="{\"success\": true,"
				+ "\"showChineseName\":"+StringManagerUtils.existOrNot(languageList, 1)+","
				+ "\"showEnglishName\":"+StringManagerUtils.existOrNot(languageList, 2)+","
				+ "\"showRussianName\":"+StringManagerUtils.existOrNot(languageList, 3)+","
				+ "\"children\":"+json
				+"}";
		
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.debug("constructRightModuleTreeGridTree json==" + json);
		pw.flush();
		pw.close();
		return null;
	}

	@RequestMapping("/saveDeviceTypeMaintenanceData")
	public String saveDeviceTypeMaintenanceData() throws Exception {
		String result ="{success:true,msg:false}";
		HttpSession session=request.getSession();
		Gson gson=new Gson();
		java.lang.reflect.Type type=null;
		String data = ParamUtils.getParameter(request, "data");
		
		type = new TypeToken<List<DeviceTypeInfo>>() {}.getType();
		List<DeviceTypeInfo> list=gson.fromJson(data, type);
		
		
		if(list!=null){
			for(DeviceTypeInfo deviceTypeInfo:list){
				this.deviceTypeMaintenanceService.modifyDeviceType(deviceTypeInfo);
			}
		}
		
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(result);
		log.debug("saveDeviceTypeMaintenanceData json==" + result);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getCalculationModelData")
	public String getCalculationModelData() throws IOException, SQLException {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		
		String json = deviceTypeMaintenanceService.getCalculationModelData(user);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
}
