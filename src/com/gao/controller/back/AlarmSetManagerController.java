package com.gao.controller.back;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.DistreteAlarmLimit;
import com.gao.model.User;
import com.gao.model.WorkStatusAlarm;
import com.gao.service.back.AlarmSetManagerService;
import com.gao.service.base.CommonDataService;
import com.gao.tast.EquipmentDriverServerTast;
import com.gao.utils.Constants;
import com.gao.utils.EquipmentDriveMap;
import com.gao.utils.Page;
import com.gao.utils.PagingConstants;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**<p>描述：后台管理-报警设置Action</p>
 * 
 * @author gao 2014-06-06
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/alarmSetManagerController")
@Scope("prototype")
public class AlarmSetManagerController extends BaseController {
	private static Log log = LogFactory.getLog(AlarmSetManagerController.class);
	private static final long serialVersionUID = -281275682819237996L;
	private WorkStatusAlarm alarm;
//	private DistreteAlarmLimit limit;

	@Autowired
	private AlarmSetManagerService<WorkStatusAlarm> alarmSetManagerService;
	@Autowired
	private CommonDataService commonDataService;
    
	private String orgId;
	private String jh;


	//添加前缀绑定
//	@InitBinder("alarm")    
//    public void initBinder(WebDataBinder binder) {    
//            binder.setFieldDefaultPrefix("alarm.");    
//    }
	
	/**<p>描述：修改报警设置信息</p>
	 * 
	 * @return
	 */
	@RequestMapping("/doAlarmsSetEdit")
	public String doAlarmsSetEdit(@ModelAttribute WorkStatusAlarm alarm) {
		String result="";
		try {
			this.alarmSetManagerService.modifyAlarmSet(alarm);
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.getWriter().print(result);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = "{success:true,msg:false}";
			e.printStackTrace();
		}
		return null;
	}

	/**<p>描述：显示报警设置信息列表</p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doAlarmsSetShow")
	public String doAlarmsSetShow() throws Exception {
		String json = "";
		this.pager = new Page("pagerForm", request);
		json = commonDataService.findAlarmSetDataById(pager);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
//		log.warn("doAlarmsSetShow json*********=" + json);
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/**<p>描述：显示平衡状态报警信息列表</p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getBalanceAlarmStatusData")
	public String getBalanceAlarmStatusData() throws Exception {
		String json = "";
		this.pager = new Page("pagerForm", request);
		json = this.alarmSetManagerService.getBalanceAlarmStatusData(pager);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
//		log.warn("doAlarmsSetShow json*********=" + json);
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/**<p>描述：统计配置</p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doStatItemsSetShow")
	public String doStatItemsSetShow() throws IOException {
		String json = "";
		this.pager = new Page("pagerForm", request);
		String type = ParamUtils.getParameter(request, "type");
		int recordCount =StringManagerUtils.stringToInteger(ParamUtils.getParameter(request, "recordCount"));
		json = this.alarmSetManagerService.doStatItemsSetShow(pager,type,recordCount);
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
	
	@RequestMapping("/doStatItemsSetSave")
	public String doStatItemsSetSave() throws Exception {

		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String orgid=user.getUserorgids();
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "");
		String type = ParamUtils.getParameter(request, "type");
		this.alarmSetManagerService.doStatItemsSetSave(type,data);
		String json ="{success:true}";
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
	
	/**<p>描述：显示报警设置下拉菜单数据信息</p>
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadAlarmType")
	public String loadAlarmType() throws Exception {

		String type = ParamUtils.getParameter(request, "type");
		String json = this.alarmSetManagerService.loadAlarmSetType(type);
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
	
	/**<p>描述：显示报警设置信息列表</p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAlarmSetSingleWellList")
	public String getAlarmSetSingleWellList() throws Exception {
		String json = "";
		this.pager = new Page("pagerForm", request);
		orgId=ParamUtils.getParameter(request, "orgId");
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		if(!StringManagerUtils.isNotNull(jh)){
			jh = ParamUtils.getParameter(request, "jh");
		}
		json = alarmSetManagerService.getAlarmSetSingleWellList(jh,orgId,pager);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
//		log.warn("doAlarmsSetShow json*********=" + json);
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/updateDiscreteAlarmSet")
	public String updateDiscreteAlarmSet() {
		try {
			// wellInformation.setRgzsjd("00:00 --00:00");
			//this.wellInformationManagerService.saveOrUpdateorDeleteProWellInformation(wellInformation, "no", "modify");
			String jlbh = ParamUtils.getParameter(request, "jlbh");
			String jbh = ParamUtils.getParameter(request, "jbh");
			String currentamax = ParamUtils.getParameter(request, "currentamax");
			String currentamin = ParamUtils.getParameter(request, "currentamin");
			String voltageamax = ParamUtils.getParameter(request, "voltageamax");
			String voltageamin = ParamUtils.getParameter(request, "voltageamin");
			String activepoweramax = ParamUtils.getParameter(request, "activepoweramax");
			String activepoweramin = ParamUtils.getParameter(request, "activepoweramin");
			
			String currentbmax = ParamUtils.getParameter(request, "currentbmax");
			String currentbmin = ParamUtils.getParameter(request, "currentbmin");
			String voltagebmax = ParamUtils.getParameter(request, "voltagebmax");
			String voltagebmin = ParamUtils.getParameter(request, "voltagebmin");
			String activepowerbmax = ParamUtils.getParameter(request, "activepowerbmax");
			String activepowerbmin = ParamUtils.getParameter(request, "activepowerbmin");
			
			String currentcmax = ParamUtils.getParameter(request, "currentcmax");
			String currentcmin = ParamUtils.getParameter(request, "currentcmin");
			String voltagecmax = ParamUtils.getParameter(request, "voltagecmax");
			String voltagecmin = ParamUtils.getParameter(request, "voltagecmin");
			String activepowercmax = ParamUtils.getParameter(request, "activepowercmax");
			String activepowercmin = ParamUtils.getParameter(request, "activepowercmin");
			
			String currentavgmax = ParamUtils.getParameter(request, "currentavgmax");
			String currentavgmin = ParamUtils.getParameter(request, "currentavgmin");
			String voltageavgmax = ParamUtils.getParameter(request, "voltageavgmax");
			String voltageavgmin = ParamUtils.getParameter(request, "voltageavgmin");
			String activepowersummax = ParamUtils.getParameter(request, "activepowersummax");
			String activepowersummin = ParamUtils.getParameter(request, "activepowersummin");
			DistreteAlarmLimit limit=new DistreteAlarmLimit();
			limit.setJlbh(Integer.parseInt(jlbh));
			limit.setJbh(Integer.parseInt(jbh));
			
			limit.setCurrentamax(Double.parseDouble(currentamax));
			limit.setCurrentamin(Double.parseDouble(currentamin));
			limit.setVoltageamax(Double.parseDouble(voltageamax));
			limit.setVoltageamin(Double.parseDouble(voltageamin));
			limit.setActivepoweramax(Double.parseDouble(activepoweramax));
			limit.setActivepoweramin(Double.parseDouble(activepoweramin));
			
			limit.setCurrentbmax(Double.parseDouble(currentbmax));
			limit.setCurrentbmin(Double.parseDouble(currentbmin));
			limit.setVoltagebmax(Double.parseDouble(voltagebmax));
			limit.setVoltagebmin(Double.parseDouble(voltagebmin));
			limit.setActivepowerbmax(Double.parseDouble(activepowerbmax));
			limit.setActivepowerbmin(Double.parseDouble(activepowerbmin));
			
			limit.setCurrentcmax(Double.parseDouble(currentcmax));
			limit.setCurrentcmin(Double.parseDouble(currentcmin));
			limit.setVoltagecmax(Double.parseDouble(voltagecmax));
			limit.setVoltagecmin(Double.parseDouble(voltagecmin));
			limit.setActivepowercmax(Double.parseDouble(activepowercmax));
			limit.setActivepowercmin(Double.parseDouble(activepowercmin));
			
			limit.setCurrentavgmax(Double.parseDouble(currentavgmax));
			limit.setCurrentavgmin(Double.parseDouble(currentavgmin));
			limit.setVoltageavgmax(Double.parseDouble(voltageavgmax));
			limit.setVoltageavgmin(Double.parseDouble(voltageavgmin));
			limit.setActivepowersummax(Double.parseDouble(activepowersummax));
			limit.setActivepowersummin(Double.parseDouble(activepowersummin));
			this.alarmSetManagerService.saveOrUpdateorDeleteDiscreteAlarmSet(limit, "no", "modify");
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			String result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.getWriter().print(result);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取报警级别颜色方法
	 * 
	 * @return null
	 * @throws Exception
	 * @author zhao 2016-3-8
	 * 
	 */
	@RequestMapping("/getAlarmLevelColor")
	public String getAlarmLevelColor() throws Exception {
		String json=alarmSetManagerService.getAlarmLevelColor();
//		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/**
	 * 设置报警级别对应颜色方法
	 * 
	 * @return null
	 * @throws Exception
	 * @author zhao 2016-3-8
	 * 
	 */
	@RequestMapping("/setAlarmLevelColor")
	public String setAlarmLevelColor() throws Exception {
		String alarmLevelBackgroundColor0=request.getParameter("alarmLevelBackgroundColor0");
		String alarmLevelBackgroundColor1=request.getParameter("alarmLevelBackgroundColor1");
		String alarmLevelBackgroundColor2=request.getParameter("alarmLevelBackgroundColor2");
		String alarmLevelBackgroundColor3=request.getParameter("alarmLevelBackgroundColor3");
		
		String alarmLevelColor0=request.getParameter("alarmLevelColor0");
		String alarmLevelColor1=request.getParameter("alarmLevelColor1");
		String alarmLevelColor2=request.getParameter("alarmLevelColor2");
		String alarmLevelColor3=request.getParameter("alarmLevelColor3");
		
		String alarmLevelOpacity0=request.getParameter("alarmLevelOpacity0");
		String alarmLevelOpacity1=request.getParameter("alarmLevelOpacity1");
		String alarmLevelOpacity2=request.getParameter("alarmLevelOpacity2");
		String alarmLevelOpacity3=request.getParameter("alarmLevelOpacity3");
		
		
		if(!StringManagerUtils.isNotNull(alarmLevelBackgroundColor0))
			alarmLevelBackgroundColor0="00FF00";
		if(!StringManagerUtils.isNotNull(alarmLevelBackgroundColor1))
			alarmLevelBackgroundColor1="FF0000";
		if(!StringManagerUtils.isNotNull(alarmLevelBackgroundColor2))
			alarmLevelBackgroundColor2="FF6600";
		if(!StringManagerUtils.isNotNull(alarmLevelBackgroundColor3))
			alarmLevelBackgroundColor3="FFCC00";
		
		if(!StringManagerUtils.isNotNull(alarmLevelColor0))
			alarmLevelColor0="FFFFFF";
		if(!StringManagerUtils.isNotNull(alarmLevelColor1))
			alarmLevelColor1="FFFFFF";
		if(!StringManagerUtils.isNotNull(alarmLevelColor2))
			alarmLevelColor2="FFFFFF";
		if(!StringManagerUtils.isNotNull(alarmLevelColor3))
			alarmLevelColor3="FFFFFF";
		
		if(!StringManagerUtils.isNotNull(alarmLevelOpacity0))
			alarmLevelOpacity0="1";
		if(!StringManagerUtils.isNotNull(alarmLevelOpacity1))
			alarmLevelOpacity1="1";
		if(!StringManagerUtils.isNotNull(alarmLevelOpacity2))
			alarmLevelOpacity2="1";
		if(!StringManagerUtils.isNotNull(alarmLevelOpacity3))
			alarmLevelOpacity3="1";
		String json="";
		try {
			alarmSetManagerService.setAlarmLevelColor(alarmLevelBackgroundColor0, alarmLevelBackgroundColor1, alarmLevelBackgroundColor2, alarmLevelBackgroundColor3,
					alarmLevelColor0,alarmLevelColor1,alarmLevelColor2,alarmLevelColor3,
					alarmLevelOpacity0,alarmLevelOpacity1,alarmLevelOpacity2,alarmLevelOpacity3);
			EquipmentDriverServerTast.initAlarmStyle();
			json="{success:true,msg:true}";
		} catch (Exception e) {
			json = "{success:true,msg:false}";
			e.printStackTrace();
		}
//		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	public WorkStatusAlarm getAlarm() {
		return alarm;
	}

	public void setAlarm(WorkStatusAlarm alarm) {
		this.alarm = alarm;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getJh() {
		return jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

//	public DistreteAlarmLimit getLimit() {
//		return limit;
//	}
//
//	public void setLimit(DistreteAlarmLimit limit) {
//		this.limit = limit;
//	}
}