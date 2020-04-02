package com.gao.controller.mobile;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.Org;
import com.gao.model.User;
import com.gao.service.base.CommonDataService;
import com.gao.service.mobile.MobileService;
import com.gao.service.right.UserManagerService;
import com.gao.utils.Config;
import com.gao.utils.DataModelMap;
import com.gao.utils.Page;
import com.gao.utils.ParamUtils;
import com.gao.utils.Recursion;
import com.gao.utils.StringManagerUtils;
import com.gao.utils.UnixPwdCrypt;;

/**
 * <P> 描述：移动端接口</p>
 * 
 * @author zhao 2018-12-28
 * 
 */
@Controller
@RequestMapping("/mobileController")
@Scope("prototype")
public class MobileController extends BaseController{

	private static final long serialVersionUID = 1L;
	@Autowired
	private CommonDataService service;
	@Autowired
	private MobileService<?> mobileService;
	@Autowired
	private UserManagerService<User> userManagerService;
	
	@RequestMapping("/userLogin")
	public String userLogin() throws Exception {
		String account = URLDecoder.decode(ParamUtils.getParameter(request, "account"), "UTF-8");
		String password = URLDecoder.decode(ParamUtils.getParameter(request, "password"), "UTF-8");
		HttpSession session=request.getSession();
		String result="";
		if (!StringManagerUtils.isNotNull(account)) {
			result="{\"success\":false,\"msg\":\"用户名不能为空\"}";
		} else if (!StringManagerUtils.isNotNull(password)) {
			result="{\"success\":false,\"msg\":\"用户密码不能为空\"}";
		} else {
			User user = this.userManagerService.doLogin(account, UnixPwdCrypt.crypt("dogVSgod", password));
			if (user != null) {
				result="{\"success\":true,\"msg\":\"登录成功\"}";
			} else {
				result="{\"success\":false,\"msg\":\"账号或密码错误\"}";
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
	@RequestMapping("/getOrganizationData")
	public String getOrganizationData() throws Exception {
		StringBuffer orgIdString = new StringBuffer();
		String userAccount = URLDecoder.decode(ParamUtils.getParameter(request, "account"), "UTF-8");
		List<Org> list = (List<Org>) mobileService.getOrganizationData(Org.class, userAccount);
		
		String json = "";
		StringBuffer strBuf = new StringBuffer();
		Recursion r = new Recursion();// 递归类，将org集合构建成一棵树形菜单的json
		for (Org org : list) {
			if (!r.hasParent(list, org)) {
				json = r.recursionMObileOrgTree(list, org);
			}
		}
		json = json.replaceAll(",]", "]");
		if(json.lastIndexOf(",")==json.length()-1){
			json=json.substring(0, json.length()-1);
		}
		strBuf.append(json);
		json = strBuf.toString();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();

		return null;
	}
	
	@RequestMapping("/oilWell/realtime/statisticsData")
	public String getPumpingRealtimeStatisticsData() throws Exception {
		String orgId=URLDecoder.decode(ParamUtils.getParameter(request, "orgId"), "UTF-8");
		String orgName=URLDecoder.decode(ParamUtils.getParameter(request, "orgName"), "UTF-8");
		if (!StringManagerUtils.isNotNull(orgId)) {
			orgId="1";
		}
		String liftingType = ParamUtils.getParameter(request, "liftingType");
		if (!StringManagerUtils.isNotNull(liftingType)) {
			liftingType="1";//默认为抽油机
		}
		String json = "{}";
		
		/******
		 * 饼图及柱状图需要的data信息
		 * ***/
//		json = mobileService.getPumpingRealtimeStatisticsDataByOrgName(orgName,liftingType);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/oilWell/realtime/wellListData")
	public String getPumpingRealtimeWellListData() throws Exception {
		String orgId = URLDecoder.decode(ParamUtils.getParameter(request, "orgId"), "UTF-8");
		String orgName=URLDecoder.decode(ParamUtils.getParameter(request, "orgName"), "UTF-8");
		String statType = URLDecoder.decode(ParamUtils.getParameter(request, "statType"), "UTF-8");
		String statValue = URLDecoder.decode(ParamUtils.getParameter(request, "statValue"), "UTF-8");
		String wellName = URLDecoder.decode(ParamUtils.getParameter(request, "wellName"), "UTF-8");
		String liftingType = URLDecoder.decode(ParamUtils.getParameter(request, "liftingType"), "UTF-8"); 
		if (!StringManagerUtils.isNotNull(orgId)) {
			orgId="1";
		}
		if (!StringManagerUtils.isNotNull(liftingType)) {
			liftingType="1";//默认为抽油机
		}
		
//		String json = mobileService.getPumpingRealtimeWellListDataByOrgName(orgName, statType, statValue,wellName,liftingType);
		String json = "{}";
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
	
	@RequestMapping("/oilWell/realtime/wellStatus")
	public String getPumpingRealtimeWellStatusData() throws Exception {
		String orgId = URLDecoder.decode(ParamUtils.getParameter(request, "orgId"), "UTF-8");
		if(!StringManagerUtils.isNotNull(orgId)){
			
		}
		orgId="246,247";
//		String json = mobileService.getPumpingRealtimeWellStatusData(orgId);
		String json = "{}";
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
	
	@RequestMapping("/oilWell/realtime/wellAnalysisData")
	public String getPumpingRealtimeWellAnalysisData()throws Exception{
		String wellName = URLDecoder.decode(ParamUtils.getParameter(request, "wellName"), "UTF-8"); 
		String json = "{}";
//		json = this.mobileService.getPumpingRealtimeWellAnalysisData(wellName);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
}
