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
		session.setAttribute("showVideo", configFile.getAp().getOthers().getShowVideo());
		session.setAttribute("oemStaticResourceTimestamp", configFile.getAp().getOem().getStaticResourceTimestamp());
		session.setAttribute("otherStaticResourceTimestamp", configFile.getAp().getOthers().getOtherStaticResourceTimestamp());
		session.setAttribute("loadingUI", loadingUI);
		session.setAttribute("helpDocumentUrl", helpDocumentUrl);
		return "app/main";
//		return "forward:/app/main.jsp";
//		return "redirect:/app/main.jsp";
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
