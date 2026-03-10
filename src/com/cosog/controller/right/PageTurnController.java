package com.cosog.controller.right;

import java.net.URLDecoder;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.model.DeviceTypeInfo;
import com.cosog.model.Module;
import com.cosog.model.Org;
import com.cosog.model.User;
import com.cosog.service.data.SystemdataInfoService;
import com.cosog.service.right.ModuleManagerService;
import com.cosog.service.right.OrgManagerService;
import com.cosog.service.right.TabInfoManagerService;
import com.cosog.service.right.UserManagerService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Config;
import com.cosog.utils.ConfigFile;
import com.cosog.utils.OrgRecursion;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.SessionLockHelper;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.DeviceTypeInfoRecursion;
import com.google.gson.Gson;

@Controller
@RequestMapping({"/","/login"})
@Scope("prototype")
public class PageTurnController extends BaseController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private TabInfoManagerService<DeviceTypeInfo> tabInfoManagerService;
	@Autowired
	private UserManagerService<User> service;
	@Autowired
	private OrgManagerService<?> orgManagerService;
	@Autowired
	private SystemdataInfoService systemdataInfoService;
	@Autowired
	private OrgManagerService<Org> orgService;
	@Autowired
	private ModuleManagerService<Module> modService;
	
	@RequestMapping({"/login","toLogin"})
	public String toLogin() throws Exception {
		Gson gson=new Gson();
		@SuppressWarnings("static-access")
		ConfigFile configFile=Config.getInstance().configFile;
		String loginLanguage=configFile.getAp().getOthers().getLoginLanguage();
		
		String loginCSS=configFile.getAp().getOem().getLoginCSS();
		if(!loginCSS.endsWith("/")){
			loginCSS+="/";
		}
		
		String languageResourceStr=MemoryDataManagerTask.getLanguageResourceStr(loginLanguage);
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(loginLanguage);
		String viewProjectName="";
		if(languageResourceMap.containsKey("projectName")){
			viewProjectName=languageResourceMap.get("projectName");
		}
		
		loginCSS+="login"+(StringManagerUtils.isNotNull(loginLanguage)?("-"+loginLanguage):"")+".css";
		
		response.setContentType("text/html;charset=utf-8");
		HttpSession session=request.getSession();
		session.setAttribute("configFile", gson.toJson(configFile));
		session.setAttribute("oem", gson.toJson(configFile.getAp().getOem()));
		session.setAttribute("viewProjectName", viewProjectName);
		session.setAttribute("favicon", configFile.getAp().getOem().getFavicon());
		session.setAttribute("loginCSS", loginCSS);
		session.setAttribute("showLogo", configFile.getAp().getOthers().getShowLogo());
		session.setAttribute("oemStaticResourceTimestamp", configFile.getAp().getOem().getStaticResourceTimestamp());
		session.setAttribute("otherStaticResourceTimestamp", configFile.getAp().getOthers().getOtherStaticResourceTimestamp());
		session.setAttribute("loginLanguageResource", languageResourceStr);
		return "Login";
		
//		return "forward:/Login.jsp";
		
//		return "redirect:/Login.jsp";
	}
	@RequestMapping("/toTouchLogin")
	public String toTouchLogin() throws Exception {
		return "touchLogin";
	}
	@RequestMapping({"/home","/toMain"})
	public String toMain() throws Exception {
		Gson gson=new Gson();
		@SuppressWarnings("static-access")
		ConfigFile configFile=Config.getInstance().configFile;
		response.setContentType("text/html;charset=utf-8");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		List<?> list = this.tabInfoManagerService.queryTabs(DeviceTypeInfo.class,user);
		String loadingUI="Loading UI…";
		
		String loginLanguage=configFile.getAp().getOthers().getLoginLanguage();
		String viewProjectName="";
		String helpDocumentUrl=configFile.getAp().getOem().getHelpDocument();
		String bannerCSS=configFile.getAp().getOem().getBannerCSS();
		
		if(!helpDocumentUrl.endsWith("/")){
			helpDocumentUrl+="/";
		}
		if(!bannerCSS.endsWith("/")){
			bannerCSS+="/";
		}
		
		
		if(user!=null){
			loginLanguage=user.getLanguageName();
		}
		helpDocumentUrl+="help"+(StringManagerUtils.isNotNull(loginLanguage)?("-"+loginLanguage):"")+".html";
		bannerCSS+="banner"+(StringManagerUtils.isNotNull(loginLanguage)?("-"+loginLanguage):"")+".css";
		
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(loginLanguage);
		if(languageResourceMap.containsKey("projectName")){
			viewProjectName=languageResourceMap.get("projectName");
		}
		
		if(languageResourceMap.containsKey("loadingTheUI")){
			loadingUI=languageResourceMap.get("loadingTheUI");
		}
		
		String tabInfoJson = "";
		DeviceTypeInfoRecursion r = new DeviceTypeInfoRecursion();
		if (list != null) {
			for (Object tabinfo : list) {
				Object[] obj = (Object[]) tabinfo;
				if (!r.hasParent(list, obj)) {
					tabInfoJson = r.recursionTabFn(list, obj);
				}
			}
		}
		tabInfoJson = r.modifyTabStr(tabInfoJson);
		tabInfoJson = this.tabInfoManagerService.getArrayTojsonPage(tabInfoJson);
		
		session.setAttribute("tabInfo", tabInfoJson);
		
		session.setAttribute("configFile", gson.toJson(configFile));
		session.setAttribute("oem", gson.toJson(configFile.getAp().getOem()));
		session.setAttribute("viewProjectName", viewProjectName);
		session.setAttribute("favicon", configFile.getAp().getOem().getFavicon());
		session.setAttribute("bannerCSS", bannerCSS);
		session.setAttribute("showLogo", configFile.getAp().getOthers().getShowLogo());
		session.setAttribute("oemStaticResourceTimestamp", configFile.getAp().getOem().getStaticResourceTimestamp());
		session.setAttribute("otherStaticResourceTimestamp", configFile.getAp().getOthers().getOtherStaticResourceTimestamp());
		session.setAttribute("loadingUI", loadingUI);
		session.setAttribute("helpDocumentUrl", helpDocumentUrl);
		session.setAttribute("showVideo", configFile.getAp().getModuleContent().getPrimaryDevice().getVideoConfig());
		return "app/main";
//		return "forward:/app/main.jsp";
//		return "redirect:/app/main.jsp";
	}
	
	@RequestMapping({"/userLogin"})
	public String userLogin() throws Exception {
		response.setContentType("text/html;charset=utf-8");
	    
	    String userId = ParamUtils.getParameter(request, "userId");
		String userPwd = ParamUtils.getParameter(request, "userPwd");
		String username = URLDecoder.decode(userId, "UTF-8");
		String userPass = URLDecoder.decode(userPwd, "UTF-8");
		HttpSession session=request.getSession();
		ConfigFile configFile=Config.getInstance().configFile;
		String locale=configFile.getAp().getOthers().getLoginLanguage();
		Locale l = Locale.getDefault(); 
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(locale);
		String clientIp=StringManagerUtils.getIpAddr(request);
		String result="";
		Gson gson=new Gson();
		if (null == username || "".equals(username)) {
//			out.print("{success:true,flag:false,'msg':'<font color=\"purple\">用户名不能为空!</font>'}");
		} else if (null == userPass || "".equals(userPass)) {
//			out.print("{success:true,flag:false,'msg':'<font color=\"purple\">用户密码不能为空!</font>'}");
		} else {
			User user = this.service.doLogin(username, StringManagerUtils.stringToMD5(userPass));
			if (user != null && user.getUserEnable()==1) {
				service.setUserLanguage(user);
				service.setUserRoleRight(user);
				locale=user.getLanguageName().toLowerCase().replace("zh_cn", "zh_CN");
				String languageResourceStr=MemoryDataManagerTask.getLanguageResourceStr(locale);
				String languageResourceFirstLower=MemoryDataManagerTask.getLanguageResourceStr_FirstLetterLowercase(locale);
				languageResourceMap=MemoryDataManagerTask.getLanguageResource(locale);
				
				int pageSize = Config.getInstance().configFile.getAp().getOthers().getPageSize();
				boolean SyncOrAsync=Config.getInstance().configFile.getAp().getOthers().getSyncOrAsync();
				int defaultComboxSize=Config.getInstance().configFile.getAp().getOthers().getDefaultComboxSize();
				int defaultGraghSize=Config.getInstance().configFile.getAp().getOthers().getDefaultGraghSize();
				user.setPageSize(pageSize + "");
				user.setSyncOrAsync(SyncOrAsync+"");
				user.setDefaultComboxSize(defaultComboxSize+"");
				user.setDefaultGraghSize(defaultGraghSize+"");
				user.setOrgtreeid(orgManagerService.findOrgById(user.getUserOrgid()));
				user.setUserParentOrgids(orgService.findParentIds(user.getUserOrgid()));
				user.setUserOrgIds(orgService.findChildIds(user.getUserOrgid()));
				user.setUserOrgNames(orgService.findChildNames(user.getUserOrgid(),user.getLanguageName()));
				user.setAllOrgPatentNodeIds(orgService.fingAllOrgParentNodeIds());
				user.setAllModParentNodeIds(modService.fingAllModParentNodeIds());
				user.setDeviceTypeIds(tabInfoManagerService.queryTabs(user));
				user.setLoginIp(clientIp);
				user.setLanguageResource(languageResourceStr);
				user.setLanguageResourceFirstLower(languageResourceFirstLower);
				user.setLoginTime(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
				
				session.setAttribute("userLogin", user);
				session.setAttribute("SESSION_USERNAME", username);
				
				if(locale==null){ 
					l = new Locale("zh", "CN"); 
				}else if (locale.equals("zh_CN")) { 
					l = new Locale("zh", "CN"); 
				}else if (locale.equals("en")) { 
					l = new Locale("en", "US"); 
				}else if (locale.equals("ru")) { 
					l = new Locale("ru", "RU"); 
				}
				session.setAttribute("WW_TRANS_I18N_LOCALE", l);
				session.setAttribute("browserLang", locale);
				
//				out.print("{success:true,flag:'normal'}");
				
				
				
				List<?> list = this.tabInfoManagerService.queryTabs(DeviceTypeInfo.class,user);
				String loadingUI="Loading UI…";
				
				String loginLanguage=configFile.getAp().getOthers().getLoginLanguage();
				String viewProjectName="";
				String helpDocumentUrl=configFile.getAp().getOem().getHelpDocument();
				String bannerCSS=configFile.getAp().getOem().getBannerCSS();
				
				if(!helpDocumentUrl.endsWith("/")){
					helpDocumentUrl+="/";
				}
				if(!bannerCSS.endsWith("/")){
					bannerCSS+="/";
				}
				
				
				if(user!=null){
					loginLanguage=user.getLanguageName();
				}
				helpDocumentUrl+="help"+(StringManagerUtils.isNotNull(loginLanguage)?("-"+loginLanguage):"")+".html";
				bannerCSS+="banner"+(StringManagerUtils.isNotNull(loginLanguage)?("-"+loginLanguage):"")+".css";
				
				if(languageResourceMap.containsKey("projectName")){
					viewProjectName=languageResourceMap.get("projectName");
				}
				
				if(languageResourceMap.containsKey("loadingTheUI")){
					loadingUI=languageResourceMap.get("loadingTheUI");
				}
				
				String tabInfoJson = "";
				DeviceTypeInfoRecursion r = new DeviceTypeInfoRecursion();
				if (list != null) {
					for (Object tabinfo : list) {
						Object[] obj = (Object[]) tabinfo;
						if (!r.hasParent(list, obj)) {
							tabInfoJson = r.recursionTabFn(list, obj);
						}
					}
				}
				tabInfoJson = r.modifyTabStr(tabInfoJson);
				tabInfoJson = this.tabInfoManagerService.getArrayTojsonPage(tabInfoJson);
				
				session.setAttribute("tabInfo", tabInfoJson);
				
				session.setAttribute("configFile", gson.toJson(configFile));
				session.setAttribute("oem", gson.toJson(configFile.getAp().getOem()));
				session.setAttribute("viewProjectName", viewProjectName);
				session.setAttribute("favicon", configFile.getAp().getOem().getFavicon());
				session.setAttribute("bannerCSS", bannerCSS);
				session.setAttribute("showLogo", configFile.getAp().getOthers().getShowLogo());
				session.setAttribute("oemStaticResourceTimestamp", configFile.getAp().getOem().getStaticResourceTimestamp());
				session.setAttribute("otherStaticResourceTimestamp", configFile.getAp().getOthers().getOtherStaticResourceTimestamp());
				session.setAttribute("loadingUI", loadingUI);
				session.setAttribute("helpDocumentUrl", helpDocumentUrl);
				session.setAttribute("showVideo", configFile.getAp().getModuleContent().getPrimaryDevice().getVideoConfig());
				
				
				
				
				
				SessionLockHelper.putSession(session);
				this.service.saveSystemLog(user,0,"外部系统登录");
				result="app/main";
			}else if(user != null && user.getUserEnable()!=1){
//				out.print("{success:true,flag:false,'msg':'<font color=\"purple\">"+languageResourceMap.get("disabledUser")+"</font>' }");
			} else {
//				out.print("{success:true,flag:false,'msg':'<font color=\"purple\">"+languageResourceMap.get("accountOrPasswordError")+"</font>' }");
			}
		}
		return result;
	}
	
	
	
	@RequestMapping("/toTouchMain")
	public String toTouchMain() throws Exception {
		return "toTouchMain";
	}
	@RequestMapping("/toBackLogin")
	public String toBackLogin() throws Exception {
		return "AdminLogin";
	}
	@RequestMapping("/toBackMain")
	public String toBackMain() throws Exception {
		return "app/back";
	}
}
