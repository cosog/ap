package com.cosog.controller.back;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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

import com.cosog.controller.base.BaseController;
import com.cosog.model.AlarmShowStyle;
import com.cosog.model.User;
import com.cosog.model.drive.ModbusDriverSaveData;
import com.cosog.service.back.AlarmSetManagerService;
import com.cosog.service.base.CommonDataService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Constants;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.EquipmentDriveMap;
import com.cosog.utils.Page;
import com.cosog.utils.PagingConstants;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
//	private DistreteAlarmLimit limit;

	@Autowired
	private AlarmSetManagerService<?> alarmSetManagerService;
	@Autowired
	private CommonDataService commonDataService;
    
	private String orgId;
	private String jh;
	
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
		Gson gson = new Gson();
		Map<String, Object> dataModelMap = DataModelMap.getMapObject();
		AlarmShowStyle alarmShowStyle=(AlarmShowStyle) dataModelMap.get("AlarmShowStyle");
		if(alarmShowStyle==null){
			MemoryDataManagerTask.initAlarmStyle();
			alarmShowStyle=(AlarmShowStyle) dataModelMap.get("AlarmShowStyle");
		}
		
		String data = ParamUtils.getParameter(request, "data");
		java.lang.reflect.Type type = new TypeToken<AlarmShowStyle>() {}.getType();
		AlarmShowStyle alarmShowStyleSaveData=gson.fromJson(data, type);
		String json="";
		try {
			if(alarmShowStyleSaveData!=null){
				
			}
			alarmSetManagerService.setAlarmLevelColor(alarmShowStyleSaveData);
			MemoryDataManagerTask.initAlarmStyle();
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
	
	@RequestMapping("/setAlarmColor")
	public String setAlarmColor() throws Exception {
		Gson gson = new Gson();
		Map<String, Object> dataModelMap = DataModelMap.getMapObject();
		String data = ParamUtils.getParameter(request, "data");
		java.lang.reflect.Type type = new TypeToken<AlarmShowStyle>() {}.getType();
		AlarmShowStyle alarmShowStyleSaveData=gson.fromJson(data, type);
		String json="";
		try {
			if(alarmShowStyleSaveData!=null){
				
			}
			alarmSetManagerService.setAlarmColor(alarmShowStyleSaveData);
			MemoryDataManagerTask.initAlarmStyle();
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