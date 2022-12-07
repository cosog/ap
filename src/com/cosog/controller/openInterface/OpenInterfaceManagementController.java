package com.cosog.controller.openInterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.model.Org;
import com.cosog.model.User;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.mobile.MobileService;
import com.cosog.service.right.UserManagerService;
import com.cosog.utils.Config;
import com.cosog.utils.Constants;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.Page;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.Recursion;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.UnixPwdCrypt;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;;

/**
 * <P> 描述：开放接口</p>
 * 
 * @author zhao 2018-12-28
 * 
 */
@Controller
@RequestMapping("/open")
@Scope("prototype")
public class OpenInterfaceManagementController extends BaseController{

	private static final long serialVersionUID = 1L;
	@Autowired
	private CommonDataService service;
	@Autowired
	private MobileService<?> mobileService;
	@Autowired
	private UserManagerService<User> userManagerService;
	
	@RequestMapping("/access/userLogin")
	public String userLogin() throws Exception {
		String account = "";
		String password = "";
		
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{\"Account\": \"admin\",\"Password\": \"123456\"}";
		try{
			JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
			try{
				account=jsonObject.getString("Account");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				password=jsonObject.getString("Password");
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		HttpSession session=request.getSession();
		String result="";
		if (!StringManagerUtils.isNotNull(account)) {
			result="{\"Success\":false,\"Msg\":\"用户名不能为空\"}";
		} else if (!StringManagerUtils.isNotNull(password)) {
			result="{\"Success\":false,\"Msg\":\"用户密码不能为空\"}";
		} else {
			User user = this.userManagerService.doLogin(account, StringManagerUtils.stringToMD5(password));
			if (user != null&&user.getUserEnable()==1) {
				result="{\"Success\":true,\"Msg\":\"登录成功\"}";
			}else if(user != null && user.getUserEnable()!=1){
				result="{\"Success\":false,\"Msg\":\"用户" + account + "已被禁用 !\"}";
			} else {
				result="{\"Success\":false,\"Msg\":\"账号或密码错误\"}";
			}
		}
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(result);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/access/getOrganizationData")
	public String getOrganizationData() throws Exception {
		String json = "";
		String UserAccount = "";;
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{\"UserAccount\": \"admin\"}";
		try{
			JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
			UserAccount=jsonObject.getString("UserAccount");
		}catch(Exception e){
			e.printStackTrace();
		}
		List<Org> list = (List<Org>) mobileService.getOrganizationData(Org.class, UserAccount);
		StringBuffer strBuf = new StringBuffer();
		Recursion r = new Recursion();// 递归类，将org集合构建成一棵树形菜单的json
		for (Org org : list) {
			if (!r.hasParent(list, org)) {
				json = r.recursionMobileOrgTree(list, org);
			}
		}
		json = json.replaceAll(",]", "]");
//		if(json.lastIndexOf(",")==json.length()-1){
//			json=json.substring(0, json.length()-1);
//		}
		strBuf.append(json);
		if(strBuf.toString().endsWith(",")){
			strBuf.deleteCharAt(strBuf.length() - 1);
		}
		json = strBuf.toString();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();

		return null;
	}
	
	/******
	 * 统计饼图及柱状图需要的data信息
	 * ***/
	@RequestMapping("/access/oilWell/realtime/statisticsData")
	public String getPumpingRealtimeStatisticsData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{}";
//		data="{\"LiftingType\":1,\"StatType\":2,\"WellList\":[\"rpc01\",\"rpc02\"]}";
		String json = mobileService.getPumpingRealtimeStatisticsDataByWellList(data);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/******
	 * 查询处于某种统计值下的实时井列表及数据
	 * ***/
	@RequestMapping("/access/oilWell/realtime/wellListData")
	public String getOilWellRealtimeWellListData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{}";
//		data="{\"LiftingType\":1,\"StatType\":1,\"StatValue\":\"正常\",\"WellList\":[\"rpc01\",\"rpc02\"]}";
		this.pager = new Page("pagerForm", request);
		String json = mobileService.getOilWellRealtimeWellListData(data,pager);
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
	
	/******
	 * 查询处于某种统计值下的井历史数据
	 * ***/
	@RequestMapping("/access/oilWell/realtime/wellHistoryData")
	public String getOilWellHistoryData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{}";
//		data="{\"LiftingType\":1,\"StatType\":1,\"StatValue\":\"正常\",\"StartDate\":\"2022-10-09 00:00:00\",\"EndDate\":\"2022-10-09 18:00:00\",\"WellName\":\"rpc01\"}";
		this.pager = new Page("pagerForm", request);
		String json = mobileService.getOilWellHistoryData(data,pager);
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
	
	@RequestMapping("/access/oilWell/realtime/wellAnalysisData")
	public String getOilWellAnalysisData()throws Exception{
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{}";
//		data="{\"LiftingType\":1,\"WellName\":\"rpc01\",\"AcqTime\":\"2022-9-30 18:51:49\"}";
		String json = this.mobileService.getOilWellAnalysisData(data);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/access/oilWell/realtime/singleFESDiagramData")
	public String singleFESDiagramData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{\"WellName\":\"rpc01\",\"AcqTime\":\"2022-9-30 18:51:49\"}";
		String json = this.mobileService.singleFESDiagramData(data);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/access/oilWell/realtime/historyFESDiagramData")
	public String historyFESDiagramData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{\"WellName\":\"rpc01\",\"StartDate\":\"2022-10-09 17:20:15\",\"EndDate\":\"2022-10-09 18:00:00\"}";
		String json = this.mobileService.historyFESDiagramData(data);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/******
	 * 全天统计饼图及柱状图需要的data信息
	 * ***/
	@RequestMapping("/access/oilWell/total/statisticsData")
	public String getOilWellTotalStatisticsData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{}";
//		data="{\"LiftingType\":1,\"Date\":\"2022-10-10\",\"StatType\":1,\"WellList\":[\"rpc01\",\"rpc02\"]}";
		String json = mobileService.getOilWellTotalStatisticsData(data);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/******
	 * 查询某天处于某种统计值下的全天井列表及数据
	 * ***/
	@RequestMapping("/access/oilWell/total/wellListData")
	public String getOilWellTotalWellListData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{}";
//		data="{\"LiftingType\":1,\"Date\":\"2022-10-09\",\"StatType\":1,\"StatValue\":\"正常\",\"WellList\":[\"rpc01\",\"rpc02\"]}";
		this.pager = new Page("pagerForm", request);
		String json = mobileService.getOilWellTotalWellListData(data,pager);
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
	
	/******
	 * 查询处于某种统计值下的井全天历史数据
	 * ***/
	@RequestMapping("/access/oilWell/total/wellHistoryData")
	public String getOilWellTotalHistoryData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{}";
//		data="{\"LiftingType\": 1,\"WellName\":\"rpc01\",\"StartDate\": \"2022-10-08\",\"EndDate\": \"2022-10-09\",\"StatType\": 1,\"StatValue\": \"正常\"}";
//		data="{\"LiftingType\": 1,\"StartDate\": \"2021-01-27\",\"EndDate\": \"2021-04-27\",\"StatType\": 1}";
		this.pager = new Page("pagerForm", request);
		String json = mobileService.getOilWellTotalHistoryData(data,pager);
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
}
