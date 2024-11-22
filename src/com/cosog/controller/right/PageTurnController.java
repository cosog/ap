package com.cosog.controller.right;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.model.DeviceTypeInfo;
import com.cosog.model.User;
import com.cosog.service.right.TabInfoManagerService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Config;
import com.cosog.utils.ConfigFile;
import com.cosog.utils.OrgRecursion;
import com.cosog.utils.DeviceTypeInfoRecursion;
import com.google.gson.Gson;

@Controller
@RequestMapping("/login")
@Scope("prototype")
public class PageTurnController extends BaseController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private TabInfoManagerService<DeviceTypeInfo> tabInfoManagerService;
	@RequestMapping("/toLogin")
	public String toLogin() throws Exception {
		Gson gson=new Gson();
		@SuppressWarnings("static-access")
		ConfigFile configFile=Config.getInstance().configFile;
		String loginLanguage=configFile.getAp().getOthers().getLoginLanguage();
		String languageResourceStr=MemoryDataManagerTask.getLanguageResourceStr(loginLanguage);
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(loginLanguage);
		String viewProjectName="";
		if(languageResourceMap.containsKey("projectName")){
			viewProjectName=languageResourceMap.get("projectName");
		}
		
		response.setContentType("text/html;charset=utf-8");
		HttpSession session=request.getSession();
		session.setAttribute("configFile", gson.toJson(configFile));
		session.setAttribute("oem", gson.toJson(configFile.getAp().getOem()));
		session.setAttribute("viewProjectName", viewProjectName);
		session.setAttribute("favicon", configFile.getAp().getOem().getFavicon());
		session.setAttribute("loginCSS", configFile.getAp().getOem().getLoginCSS());
		session.setAttribute("showLogo", configFile.getAp().getOthers().getShowLogo());
		session.setAttribute("oemStaticResourceTimestamp", configFile.getAp().getOem().getStaticResourceTimestamp());
		session.setAttribute("otherStaticResourceTimestamp", configFile.getAp().getOthers().getOtherStaticResourceTimestamp());
		session.setAttribute("loginLanguageResource", languageResourceStr);
		return "Login";
	}
	@RequestMapping("/toTouchLogin")
	public String toTouchLogin() throws Exception {
		return "touchLogin";
	}
	@RequestMapping("/toMain")
	public String toMain() throws Exception {
		Gson gson=new Gson();
		@SuppressWarnings("static-access")
		ConfigFile configFile=Config.getInstance().configFile;
		response.setContentType("text/html;charset=utf-8");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		List<?> list = this.tabInfoManagerService.queryTabs(DeviceTypeInfo.class,user);
		
		
		String loginLanguage=configFile.getAp().getOthers().getLoginLanguage();
		String viewProjectName="";
		if(user!=null){
			loginLanguage=user.getLanguageName();
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(loginLanguage);
			if(languageResourceMap.containsKey("projectName")){
				viewProjectName=languageResourceMap.get("projectName");
			}
		}
		String languageResourceStr=MemoryDataManagerTask.getLanguageResourceStr(loginLanguage);
		
		
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
		session.setAttribute("bannerCSS", configFile.getAp().getOem().getBannerCSS());
		session.setAttribute("showLogo", configFile.getAp().getOthers().getShowLogo());
		session.setAttribute("showVideo", configFile.getAp().getOthers().getShowVideo());
		session.setAttribute("oemStaticResourceTimestamp", configFile.getAp().getOem().getStaticResourceTimestamp());
		session.setAttribute("otherStaticResourceTimestamp", configFile.getAp().getOthers().getOtherStaticResourceTimestamp());
		return "app/main";
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
