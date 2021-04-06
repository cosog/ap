package com.gao.controller.right;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




//import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.Module;
import com.gao.model.Org;
import com.gao.model.User;
import com.gao.service.data.SystemdataInfoService;
import com.gao.service.right.ModuleManagerService;
import com.gao.service.right.OrgManagerService;
import com.gao.service.right.UserManagerService;
import com.gao.utils.Config;
import com.gao.utils.Config2;
import com.gao.utils.Constants;
import com.gao.utils.Page;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;
import com.gao.utils.UnixPwdCrypt;
import com.opensymphony.xwork2.ActionContext;

/**
 * <P> 描述：用户登录管理</p>
 * 
 * @author gao 2014-05-08
 * 
 */
@Controller
@RequestMapping("/userLoginManagerController")
@Scope("prototype")
public class UserLoginManagerController extends BaseController {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(UserLoginManagerController.class);
	private UserManagerService<User> service;
	private User user;
	// 注入OrgManagerService
	@Autowired
	private OrgManagerService<?> orgManagerService;
	@Autowired
	private SystemdataInfoService systemdataInfoService;
	@Autowired
	private OrgManagerService<Org> orgService;
	@Autowired
	private ModuleManagerService<Module> modService;
	/**
	 * 管理员用户登录时，判断该用户是否合法并且为管理员权限
	 * @author gao 2014-05-08
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/adminLogin")
	public String adminLogin() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String userId = ParamUtils.getParameter(request, "userId");
		String userPwd = ParamUtils.getParameter(request, "userPwd");
		String imgCode = ParamUtils.getParameter(request, "imgCode");
		String sessionCode = (String) session.get("sessionCode");
		String username = URLDecoder.decode(userId, "UTF-8");
		log.debug("userId" + username);
		String userPass = URLDecoder.decode(userPwd, "UTF-8");
		String code = URLDecoder.decode(imgCode, "UTF-8");
		HttpSession session = request.getSession(true);
		// 功图图形服务内外网判断
//		String picUrl = "";
		String ip = request.getServerName();
//		picUrl = Config2.getPicUrlByClientIp(ip, Config2.getUrl_filter());
		String locale=Config.getInstance().configFile.getOthers().getLanguage();
		Locale l = Locale.getDefault(); 
		if(locale==null){ 
		l = new Locale("zh", "CN"); 
		}else if (locale.equals("zh_CN")) { 
		l = new Locale("zh", "CN"); 
		} else if (locale.equals("en")) { 
		l = new Locale("en", "US"); 
		} 
		ActionContext.getContext().setLocale(l);   
		session.setAttribute("WW_TRANS_I18N_LOCALE", l);
		session.setAttribute("browserLang", locale);
		session.setAttribute("WEBSOCKET_USERID", username);
        if(locale.equalsIgnoreCase("zh_CN")){
		if (null == username || "".equals(username)) {
			out.print("{success:true,flag:false,'msg':'<font color=\"purple\">用户名不能为空!</font>'}");
		} else if (null == userPass || "".equals(userPass)) {
			out.print("{success:true,flag:false,'msg':'<font color=\"purple\">用户密码不能为空!</font>'}");
		} else if (null == code || "".equals(code)) {
			out.print("{success:true,flag:false,'msg':'<font color=\"purple\">请输入验证码!!</font>'}");
		} else if (!(sessionCode.equals(imgCode))) {
			out.print("{success:true,flag:false,'msg':'<font color=\"purple\">抱歉输入的验证码有误!</font>'}");
		} else {
			user = this.service.doLogin(username, UnixPwdCrypt.crypt("dogVSgod", userPass));
			if(user!=null){
			if (user.getUserType() == 1 || user.getUserType() == 2||user.getUserType() == 3) {
//				user.setPicUrl(picUrl);// 通过session传到前台
				int pageSize = Config.getInstance().configFile.getOthers().getPageSize();
				user.setPageSize(pageSize + "");
				user.setOrgtreeid(findUserToOrgString(user.getUserOrgid()));
				session.setAttribute("userLogin", user);
				out.print("{success:true,flag:'admin'}");
			} else if (user != null && user.getUserType() == 0) {
				out.print("{success:true,flag:'normal','msg':'<font color=\"red\"> 您是普通用户，请从其前台登录界面登录!</font>'}");
			} 
			}else {
				out.print("{success:true,flag:false,'msg':'<font color=\"red\">用户" + username + "的账号或密码错误  !</font>'}");
			}
		}
        }else if(locale.equalsIgnoreCase("en")){
        	if (null == username || "".equals(username)) {
    			out.print("{success:true,flag:false,'msg':'<font color=\"purple\">The user name cannot be empty!</font>'}");
    		} else if (null == userPass || "".equals(userPass)) {
    			out.print("{success:true,flag:false,'msg':'<font color=\"purple\">The user password can not be empty!</font>'}");
    		} else if (null == code || "".equals(code)) {
    			out.print("{success:true,flag:false,'msg':'<font color=\"purple\">Please enter the verification code!</font>'}");
    		} else if (!(sessionCode.equals(imgCode))) {
    			out.print("{success:true,flag:false,'msg':'<font color=\"purple\">I am sorry to verify code entered is incorrect!</font>'}");
    		} else {
    			user = this.service.doLogin(username, UnixPwdCrypt.crypt("dogVSgod", userPass));
    			if(user!=null){
    				if (user.getUserType() == 1 || user.getUserType() == 2||user.getUserType() == 3) {
    			if ( user.getUserType() == 1 || user.getUserType() == 2||user.getUserType() == 3) {
//    				user.setPicUrl(picUrl);// 通过session传到前台
    				int pageSize = Config.getInstance().configFile.getOthers().getPageSize();
    				user.setPageSize(pageSize + "");
    				user.setOrgtreeid(findUserToOrgString(user.getUserOrgid()));
    				session.setAttribute("userLogin", user);
    				out.print("{success:true,flag:'admin'}");
    			} else if (user != null && user.getUserType() == 0) {
    				out.print("{success:true,flag:'normal','msg':'<font color=\"red\"> You are a regular user, please login interface login from the front!</font>'}");
    			} 
    		}else {
        			out.print("{success:true,flag:false,'msg':'<font color=\"red\">User "+ username +"\\'s account or password is wrong !</font>'}");
        			}
    		
    			}
    			}
        }
		return null;

	}

	/**
	 * 登录用户所属组织编码
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/findUserToOrgString")
	public String findUserToOrgString(Integer userOrgId) {
		String getOrgCode = orgManagerService.findOrgById(userOrgId);
		return getOrgCode;
	}

	

	/**
	 *描述：重置密码，修改当前用户的登录密码
	 *
	 * @author  gao 2014-05-08
	 * @throws Exception
	 */
	@RequestMapping("/resetPwdmessage")
	public String resetPwdmessage() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		//HttpServletRequest request = ServletActionContext.getRequest();
		//HttpServletResponse response = ServletActionContext.getResponse();
		String newPassword = request.getParameter("newPassword");
		String oldPassword = request.getParameter("oldPassword");
		String jsonLogin = "";
		User userInfo = (User) request.getSession().getAttribute("userLogin");
		// 用户不存在
		if (null != userInfo) {
			String getUpwd = userInfo.getUserPwd();
			String getOld = UnixPwdCrypt.crypt("dogVSgod", oldPassword);
			if (getOld.equals(getUpwd)) {
				userInfo.setUserPwd(UnixPwdCrypt.crypt("dogVSgod", newPassword));
				//service.edit(userInfo);
				this.service.modifyUser(userInfo);
				jsonLogin = "{success:true,flag:true,error:true,msg:'<font color=blue>密码修改成功。</font>'}";
			} else {
				jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>您输入的旧密码有误！</font>'}";
			}

		} else {
			jsonLogin = "{success:true,flag:false}";
		}
		// 处理乱码。
		response.setCharacterEncoding("utf-8");
		// 输出json数据。
		out.print(jsonLogin);
		return null;
	}


	/** <p>描述：用户注销操作调用该方法</p>
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/userExit")
	public String userExit() throws IOException {
		PrintWriter out = response.getWriter();
		request.getSession().removeAttribute("userLogin");
		out.print("{success:true,flag:true}");
		return null;

	}

	/**
	 * < p>描述：普通用户登录控制方法</p>
	 * @author gao 2014-05-08
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userLogin")
	public String userLogin() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		// String result = null;
		PrintWriter out = response.getWriter();
		String userId = ParamUtils.getParameter(request, "userId");
		String userPwd = ParamUtils.getParameter(request, "userPwd");
		String flag = ParamUtils.getParameter(request, "flag");
		String autoLogin = ParamUtils.getParameter(request, "autoLogin");
		// String imgCode = ParamUtils.getParameter(request, "imgCode");
		String username = URLDecoder.decode(userId, "UTF-8");
		log.debug("userId" + username);
		String userPass = URLDecoder.decode(userPwd, "UTF-8");
		// 功图图形服务内外网判断
		String picUrl = "";
		String ip = request.getServerName();
		String clientIp=request.getHeader("x-forwarded-for");
        if(clientIp==null || clientIp.length()==0 || "unknown".equalsIgnoreCase(clientIp)){
        	clientIp=request.getHeader("Proxy-Client-IP");
        }
        if(clientIp==null || clientIp.length()==0 || "unknown".equalsIgnoreCase(clientIp)){
        	clientIp=request.getHeader("WL-Proxy-Client-IP");
        }
        if(clientIp==null || clientIp.length()==0 || "unknown".equalsIgnoreCase(clientIp)){
        	clientIp=request.getHeader("X-Real-IP");
        }
        if(clientIp==null || clientIp.length()==0 || "unknown".equalsIgnoreCase(clientIp)){
        	clientIp=request.getRemoteAddr();
        }
		
//		picUrl = Config2.getPicUrlByClientIp(ip, Config2.getUrl_filter());
		// String code = URLDecoder.decode(imgCode, "UTF-8");
//		HttpSession session = ServletActionContext.getRequest().getSession(true);
		HttpSession session=request.getSession();
		String locale=Config.getInstance().configFile.getOthers().getLanguage();
		Locale l = Locale.getDefault(); 
		
		if(flag!=null && flag.equals("1")){
			Cookie cookie = new Cookie("cookieuser", userId+"-"+userPwd);
			cookie.setMaxAge(60*60*24*365); //cookie 保存一年
			response.addCookie(cookie);
			session.setAttribute("flag", flag);
		}else{
			Cookie cookie = new Cookie("cookieuser",userId+"-"+null);
			cookie.setMaxAge(60*60*24*365); //cookie 保存一年
			response.addCookie(cookie);
			session.setAttribute("flag", flag);
		}
		
		if(locale==null){ 
		l = new Locale("zh", "CN"); 
		}else if (locale.equals("zh_CN")) { 
		l = new Locale("zh", "CN"); 
		} else if (locale.equals("en")) { 
		l = new Locale("en", "US"); 
		} 
//		ActionContext.getContext().setLocale(l);   
		session.setAttribute("WW_TRANS_I18N_LOCALE", l);
		session.setAttribute("browserLang", locale);
		if (null == username || "".equals(username)) {
			out.print("{success:true,flag:false,'msg':'<font color=\"purple\">用户名不能为空!</font>'}");
		} else if (null == userPass || "".equals(userPass)) {
			out.print("{success:true,flag:false,'msg':'<font color=\"purple\">用户密码不能为空!</font>'}");
		} else {
			if("1".equals(autoLogin)){
				user = this.service.doLogin(username, userPass);
			}else{
				user = this.service.doLogin(username, UnixPwdCrypt.crypt("dogVSgod", userPass));
			}
			if (user != null) {
				user.setPicUrl(picUrl);// 通过session传到前台
				int pageSize = Config.getInstance().configFile.getOthers().getPageSize();
				boolean SyncOrAsync=Config.getInstance().configFile.getOthers().getSyncOrAsync();
				int defaultComboxSize=Config.getInstance().configFile.getOthers().getDefaultComboxSize();
				int defaultGraghSize=Config.getInstance().configFile.getOthers().getDefaultGraghSize();
				user.setPageSize(pageSize + "");
				user.setSyncOrAsync(SyncOrAsync+"");
				user.setDefaultComboxSize(defaultComboxSize+"");
				user.setDefaultGraghSize(defaultGraghSize+"");
				user.setOrgtreeid(findUserToOrgString(user.getUserOrgid()));
				user.setUserParentOrgids(orgService.findParentIds(user.getUserOrgid()));
				user.setUserorgids(orgService.findChildIds(user.getUserOrgid()));
				user.setUserOrgNames(orgService.findChildNames(user.getUserOrgid()));
				user.setAllOrgPatentNodeIds(orgService.fingAllOrgParentNodeIds());
				user.setAllModParentNodeIds(modService.fingAllModParentNodeIds());
				session.setAttribute("userLogin", user);
				session.setAttribute("SESSION_USERNAME", username);
				out.print("{success:true,flag:'normal'}");
			} else {
				if(locale.equalsIgnoreCase("zh_CN")){
				out.print("{success:true,flag:false,'msg':'<font color=\"purple\">用户" + username + "的账号或密码错误 !</font>' }");
				}else if(locale.equalsIgnoreCase("en")){
					out.print("{success:true,flag:false,'msg':'<font color=\"purple\">User "+ username +"\\'s account or password is wrong!</font>' }");
				}
			}
		}
		return null;

	}
	
	/**<p>描述：功图批量上传工具权限控制</p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dynUploadLogin")
	public String dynUploadLogin() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String userId = ParamUtils.getParameter(request, "userId");
		String userPwd = ParamUtils.getParameter(request, "userPwd");
		String username = URLDecoder.decode(userId, "UTF-8");
		log.debug("userId" + username);
		String userPass = URLDecoder.decode(userPwd, "UTF-8");
		if (null == username || "".equals(username)) {
			out.print("");
		} else if (null == userPass || "".equals(userPass)) {
			out.print("");
		} else {
			user = this.service.doLogin(username, UnixPwdCrypt.crypt("dogVSgod", userPass));
			if (user != null&&user.getUserType()==3) {
				String 	orgId = this.systemdataInfoService.findCurrentUserOrgIdInfo(user.getUserOrgid()+"");
				if(!StringManagerUtils.isNotNull(orgId)){
					orgId="0";
				}
				out.print(orgId);
			} else {
				out.print("");
			}
		}
		return null;

	}
	
	@RequestMapping("/getUserList")
	public String getUserList() throws Exception {
		String json = "";
		
		json = service.getUserList();
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		log.warn("doAlarmsSetShow json*********=" + json);
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}

	@Resource
	public void setService(UserManagerService<User> service) {
		this.service = service;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
}
