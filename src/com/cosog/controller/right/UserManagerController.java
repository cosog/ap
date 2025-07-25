package com.cosog.controller.right;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cosog.common.exception.ActionException;
import com.cosog.controller.base.BaseController;
import com.cosog.model.ExportOrganizationData;
import com.cosog.model.ExportUserData;
import com.cosog.model.Module;
import com.cosog.model.Org;
import com.cosog.model.User;
import com.cosog.model.calculate.UserInfo;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.data.SystemdataInfoService;
import com.cosog.service.right.ModuleManagerService;
import com.cosog.service.right.OrgManagerService;
import com.cosog.service.right.UserManagerService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Config;
import com.cosog.utils.Constants;
import com.cosog.utils.Message;
import com.cosog.utils.OrgRecursion;
import com.cosog.utils.Page;
import com.cosog.utils.PagingConstants;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.SessionLockHelper;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.UnixPwdCrypt;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * <p>
 * 描述：用户管理模块的相关操作
 * </p>
 * 
 * @author gao 2014-05-08
 * 
 */
@Controller
@RequestMapping("/userManagerController")
@Scope("prototype")
public class UserManagerController extends BaseController {
	private static Log log = LogFactory.getLog(UserManagerController.class);
	private static final long serialVersionUID = -281275682819237996L;
	private String limit;
	private String orgId;
	private String page;
	private User user;
	private String[] userNos;
	private List<User> users;
	@Autowired
	private UserManagerService<User> userService;
	@Autowired
	private OrgManagerService<?> orgManagerService;
	@Autowired
	private OrgManagerService<Org> orgService;
	@Autowired
	private ModuleManagerService<Module> modService;
	@Autowired
	private CommonDataService service;
	
	//添加绑定前缀 
	@InitBinder("user")
	public void initBinderByUser(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("user.");
	}
	
	@RequestMapping("/loadUserComboxList")
	public String loadUserComboxList() throws Exception {
		this.pager=new Page("pageForm",request);
		String userId = ParamUtils.getParameter(request, "userId");
		orgId=ParamUtils.getParameter(request, "orgId");
		User user = null;
		HttpSession session=request.getSession();
		user = (User) session.getAttribute("userLogin");
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserOrgIds();
			}
		}
		String json = this.userService.loadUserComboxList(pager,orgId,userId, user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
		
	/**
	 * <p>根据传入的orgId 显示某个组织下的用户信息</p>
	 * 
	 * @return Null
	 * @throws IOException
	 * @author gao  2014-05-08
	 * 
	 * 
	 */
	@RequestMapping("/doShowRightUsers")
	public String doShowRightUsers() throws IOException {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		orgId = findCurrentUserOrgIdInfo(orgId);
		Gson g = new Gson();
		users = userService.queryUsersByOrgId(orgId, User.class);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(PagingConstants.TOTAL, 300000);
		jsonMap.put(PagingConstants.LIST, users);
		json = g.toJson(jsonMap);
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/invalidateSession")
	public String invalidateSession() throws IOException {
		String json = "";
		try{
			HttpSession session=request.getSession();
			session.invalidate();
			json = "{success:true,msg:true}";
		}catch(Exception e){
			json = "{success:true,false}";
		}
		
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}

	/** <P>描述：用户管理——新增用户</p>
	 * @author gao 2014-05-08
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/doUserAdd")
	public String doUserAdd(@ModelAttribute User user) throws IOException {
		String result = "{success:true,msg:false}";
		try {
			User loginUser = null;
			HttpSession session=request.getSession();
			loginUser = (User) session.getAttribute("userLogin");
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(loginUser!=null?loginUser.getLanguageName():"");
			String loginLanguageName=Config.getInstance().configFile.getAp().getOthers().getLoginLanguage();
			String languageValue=MemoryDataManagerTask.getCodeValue("LANGUAGE",loginLanguageName,loginLanguageName);
			
			List<Integer> userRoleLanguageList=this.userService.getLanguageList(user!=null?user.getUserType():0);
			
			
			String emailContent=languageResourceMap.get("userAccount")+":"+user.getUserId()+"<br/>"+languageResourceMap.get("userPassword")+":"+user.getUserPwd();
			String emailTopic=languageResourceMap.get("addUser");
			List<String> receivingEMailAccount=new ArrayList<String>();
//			user.setUserPwd(UnixPwdCrypt.crypt("dogVSgod", user.getUserPwd()));
			user.setUserPwd(StringManagerUtils.stringToMD5(user.getUserPwd()));
			user.setUserRegtime(new Date());
			
			
			if(userRoleLanguageList.size()==0 || StringManagerUtils.existOrNot(userRoleLanguageList, StringManagerUtils.stringToInteger(languageValue))){
				user.setLanguage(StringManagerUtils.stringToInteger(languageValue));
			}else{
				user.setLanguage(userRoleLanguageList.get(0));
			}
			
			this.userService.addUser(user);
			
			List<String> userList=new ArrayList<String>();
			userList.add(user.getUserId());
			MemoryDataManagerTask.loadUserInfo(userList,1,"update");
			result = "{success:true,msg:true}";
			if(StringManagerUtils.isMailLegal(user.getUserInEmail())){
				receivingEMailAccount.add(user.getUserInEmail());
				StringManagerUtils.sendEMail(emailTopic, emailContent, receivingEMailAccount);
			}
			
			result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.getWriter().print(result);
		} catch (Exception e) {
			response.getWriter().print(result);
			e.printStackTrace();
			new ActionException("新增用户失败！");
		}
		return null;
	}

	/** <P>描述：用户管理——批量删除用户信息</p>
	 * @return
	 */
	@RequestMapping("/doUserBulkDelete")
	public String doUserBulkDelete() {
		try {
			// log.debug("userNos==" + userNos.length);
			String userNos = ParamUtils.getParameter(request, "paramsId");
			String userIds = ParamUtils.getParameter(request, "delUserId");
			this.userService.bulkDelete(userNos);
			if(StringManagerUtils.isNotNull(userNos)){
				String[] userNoArr=userNos.split(",");
				for(int i=0;i<userNoArr.length;i++){
					if(StringManagerUtils.isNotNull(userNoArr[i])){
						SessionLockHelper.destroySessionByUserNo(StringManagerUtils.stringToInteger(userNoArr[i]));
					}
				}
			}
			//销毁已
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			String result = "{success:true,flag:true}";
			response.getWriter().print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/doUserDelete")
	public String doUserDelete() {
		try {
			int myUserNo = ParamUtils.getIntParameter(request, "userNo", 0);
			log.debug("userNo==" + myUserNo);
			user.setUserName(ParamUtils.getParameter(request, "userName"));
			user.setUserId(ParamUtils.getParameter(request, "userId"));
			user.setUserPwd(ParamUtils.getParameter(request, "userPwd"));
			user.setUserPhone(ParamUtils.getParameter(request, "userPhone"));
			user.setUserInEmail(ParamUtils.getParameter(request, "userInEmail"));
			user.setUserOrgid(303);
			user.setUserRegtime(StringManagerUtils.stringToTimeStamp(ParamUtils.getParameter(request, "userRegtime")));
			this.userService.deleteUser(myUserNo, User.class);
			String result = "ok";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.getWriter().print(JSONObject.fromObject(new Message(result)));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <P>用户模块管理——编辑用户信息</p>
	 * @author  gao 2014-05-08
	 * @return
	 */
	@RequestMapping("/doUserEdit")
	public String doUserEdit(@ModelAttribute User user) {
		try {
			log.debug("edit user success==" + user.getUserNo());
			String emailContent="账号:"+user.getUserId()+"<br/>密码:"+user.getUserPwd();
			String emailTopic="用户修改";
			List<String> receivingEMailAccount=new ArrayList<String>();
			
			String userOldPass = ParamUtils.getParameter(request, "userPass");
			if (!userOldPass.equals(user.getUserPwd())) {
//				String newPass = UnixPwdCrypt.crypt("dogVSgod", user.getUserPwd());
				String newPass = StringManagerUtils.stringToMD5(user.getUserPwd());
				user.setUserPwd(newPass);
			}
			HttpSession session=request.getSession();
			User prttentuser = (User) session.getAttribute("userLogin");
			if(user.getUserNo()==prttentuser.getUserNo()){
				user.setUserType(prttentuser.getUserType());
				user.setUserEnable(prttentuser.getUserEnable());
			}
			this.userService.modifyUser(user);
			
			List<String> userList=new ArrayList<String>();
			userList.add(user.getUserNo()+"");
			MemoryDataManagerTask.loadUserInfo(userList,0,"update");
			
			String result = "{success:true,msg:true}";
			if(user.getUserNo()==prttentuser.getUserNo()){
				prttentuser.setUserOrgid(user.getUserOrgid());
			}
			
			if(StringManagerUtils.isMailLegal(user.getUserInEmail())){
				receivingEMailAccount.add(user.getUserInEmail());
				StringManagerUtils.sendEMail(emailTopic, emailContent, receivingEMailAccount);
			}
			
			if(user.getUserEnable()==0){
				SessionLockHelper.destroySessionByUserNo(user.getUserNo());
			}
			
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
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/doUserEditPassword")
	public String doUserEditPassword(@ModelAttribute User user) throws IOException {
		String result = "{success:true,flag:true}";
		try {
			log.debug("edit user password success==" + user.getUserNo());
			String emailContent="账号:"+user.getUserId()+"<br/>新密码:"+user.getUserPwd();
			String emailTopic="用户密码修改";
			List<String> receivingEMailAccount=new ArrayList<String>();
			
			String newPass = StringManagerUtils.stringToMD5(user.getUserPwd());
			user.setUserPwd(newPass);
			
			int r=this.userService.updateUserPassword(user);
			if(r<1){
				result = "{success:true,flag:false}";
			}else{
				String email=this.userService.getUserEmail(user);
				if(StringManagerUtils.isMailLegal(email)){
					receivingEMailAccount.add(email);
					StringManagerUtils.sendEMail(emailTopic, emailContent, receivingEMailAccount);
				}
			}
		} catch (Exception e) {
			result = "{success:false,flag:false}";
			e.printStackTrace();
		}
		response.setCharacterEncoding(Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		response.setCharacterEncoding(Constants.ENCODING_UTF8);
		response.getWriter().print(result);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/updateUserInfo")
	public String updateUserInfo() throws IOException {
		String result = "{success:true,flag:true}";
		try {
			boolean isLoginedUser=false;
			String userNo = ParamUtils.getParameter(request, "userNo");
			String userName = ParamUtils.getParameter(request, "userName");
			String userId = ParamUtils.getParameter(request, "userId");
			String userTypeName = ParamUtils.getParameter(request, "userTypeName");
			String userPhone = ParamUtils.getParameter(request, "userPhone");
			String userInEmail = ParamUtils.getParameter(request, "userInEmail");
			String userQuickLoginName = ParamUtils.getParameter(request, "userQuickLoginName");
			String receiveSMSName = ParamUtils.getParameter(request, "receiveSMSName");
			String receiveMailName = ParamUtils.getParameter(request, "receiveMailName");
			String userEnableName = ParamUtils.getParameter(request, "userEnableName");
			String userLanguageName = ParamUtils.getParameter(request, "userLanguageName");
			
			User user=new User();
			user.setUserNo(StringManagerUtils.stringToInteger(userNo));
			user.setUserName(userName);
			user.setUserId(userId);
			user.setUserTypeName(userTypeName);
			user.setLanguageName(userLanguageName);
			user.setUserPhone(userPhone);
			user.setUserInEmail(userInEmail);
			user.setUserQuickLogin("true".equalsIgnoreCase(userQuickLoginName)?1:0);
			user.setReceiveSMS("true".equalsIgnoreCase(receiveSMSName)?1:0);
			user.setReceiveMail("true".equalsIgnoreCase(receiveMailName)?1:0);
			user.setUserEnable("true".equalsIgnoreCase(userEnableName)?1:0);
			
			log.debug("edit user ==" + user.getUserNo());
			String emailContent="账号:"+user.getUserId()+"信息改变。<br/>";
			String emailTopic="用户修改";
			List<String> receivingEMailAccount=new ArrayList<String>();
			
			
//			this.userService.modifyUser(user);
			HttpSession session=request.getSession();
			User loginUser = (User) session.getAttribute("userLogin");
			//如果是当前登录用户
			if(user.getUserNo()==loginUser.getUserNo()){
				isLoginedUser=true;
				user.setUserType(loginUser.getUserType());
				user.setUserEnable(loginUser.getUserEnable());
			}
			boolean userIdChange=false;
			if(!isLoginedUser){
				users=userService.queryUsersByNo(user.getUserNo(), User.class);
				for(User u : users){
					if(!u.getUserId().equalsIgnoreCase(user.getUserId())){
						userIdChange=true;
						break;
					}
				}
			}
			int r=this.userService.updateUserInfo(user,isLoginedUser);
			if(r==1){
				List<String> userList=new ArrayList<String>();
				userList.add(user.getUserNo()+"");
				MemoryDataManagerTask.loadUserInfo(userList,0,"update");
				
				if(StringManagerUtils.isMailLegal(user.getUserInEmail())){
					receivingEMailAccount.add(user.getUserInEmail());
					StringManagerUtils.sendEMail(emailTopic, emailContent, receivingEMailAccount);
				}
				if(user.getUserEnable()==0||userIdChange){
					SessionLockHelper.destroySessionByUserNo(user.getUserNo());
				}
			}else if(r==2){
				result = "{success:true,flag:false}";
			}else{
				result = "{success:false,flag:false}";
			}
		} catch (Exception e) {
			result = "{success:false,flag:false}";
			e.printStackTrace();
		}
		response.setCharacterEncoding(Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		response.setCharacterEncoding(Constants.ENCODING_UTF8);
		response.getWriter().print(result);
		pw.flush();
		pw.close();
		return null;
	}

	/** <P>用户模块管理——创建Ext需要的json数据信息</p>	 
	 * @author  gao 2014-05-08
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping("/doUserShow")
	public String doUserShow() throws IOException, SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		HttpSession session=request.getSession();
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "20" : limit);
		String userName = ParamUtils.getParameter(request, "userName");
		orgId = ParamUtils.getParameter(request, "orgId");
		User user = (User) session.getAttribute("userLogin");
		if(!StringManagerUtils.isNotNull(orgId)){
			orgId = "" + user.getUserOrgIds();
		}
		int offset = (intPage - 1) * pageSize;
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("userName", userName);
		this.pager = new Page("pagerForm", request);
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		String json = userService.doUserShow(pager, map,orgId,user);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportUserCompleteData")
	public String exportUserCompleteData() throws IOException {
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		int recordCount =StringManagerUtils.stringToInteger(ParamUtils.getParameter(request, "recordCount"));
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String key = ParamUtils.getParameter(request, "key");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		
		if(session!=null){
			session.removeAttribute(key);
			session.setAttribute(key, 0);
		}
		
		String json = this.userService.exportUserCompleteData(user);
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		fileName+=".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(user!=null){
				this.service.saveSystemLog(user,4,languageResourceMap.get("exportFile")+":"+fileName);
			}
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
            in = new FileInputStream(file);
            int len = 0;
            byte[] buffer = new byte[1024];
            out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer,0,len);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
        	if(in!=null){
        		in.close();
        	}
        	if(out!=null){
        		out.close();
        	}
        	if(session!=null){
    			session.setAttribute(key, 1);
    		}
        }
		StringManagerUtils.deleteFile(path);
		return null;
	}
	
	/** <P>判断在用户表中是否存在当前新加的用户账号</p>	 
	 * @author  gao 2014-05-08
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/judgeUserExistOrNot")
	public String judgeUserExistOrNot() throws IOException {
		String userId = ParamUtils.getParameter(request, "userId");
		String userNo = ParamUtils.getParameter(request, "userNo");
		boolean flag = this.userService.judgeUserExistsOrNot(userId,userNo);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		String json = "";
		if (flag) {
			json = "{success:true,msg:'1'}";
		} else {
			json = "{success:true,msg:'0'}";
		}
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/**<p>描述：获取用户类型的下拉菜单数据信息</p>
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadUserType")
	public String loadUserType() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String type = ParamUtils.getParameter(request, "type");
		String json = this.userService.loadUserType(user);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/loadLanguageList")
	public String loadLanguageList() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = this.userService.loadLanguageList(user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getUserRoleModules")
	public String getUserRoleModules() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String json="";
		if(user!=null){
			json = this.userService.getUserRoleModules(user);
//			json = user.getModuleList();
		}
		
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getUserOrgChangeUserList")
	public String getUserOrgChangeUserList() throws Exception {
		this.pager=new Page("pageForm",request);
		String userName = ParamUtils.getParameter(request, "userName");
		orgId=ParamUtils.getParameter(request, "orgId");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserOrgIds();
			}
		}
		String json = this.userService.getUserOrgChangeUserList(pager,orgId, userName,user,language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/changeUserOrg")
	public String changeUserOrg() throws Exception {
		this.pager=new Page("pageForm",request);
		String selectedUserId = ParamUtils.getParameter(request, "selectedUserId");
		String selectedOrgId=ParamUtils.getParameter(request, "selectedOrgId");
		this.userService.changeUserOrg(selectedUserId,selectedOrgId);
		String json = "{\"success\":true}";
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/switchUserLanguage")
	public String switchUserLanguage() throws Exception {
		this.pager=new Page("pageForm",request);
		String languageValue = ParamUtils.getParameter(request, "languageValue");
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		
		int r=0;
		if(user!=null){
			r=this.userService.switchUserLanguage(user, languageValue);
			if(r>0){
				UserInfo userInfo=MemoryDataManagerTask.getUserInfoByNo(user.getUserNo()+"");
				Locale l = Locale.getDefault(); 
				String locale=user.getLanguageName().toLowerCase().replace("zh_cn", "zh_CN");
				if(locale==null){ 
					l = new Locale("zh", "CN"); 
				}else if (locale.equals("zh_CN")) { 
					l = new Locale("zh", "CN"); 
				}else if (locale.equals("en")) { 
					l = new Locale("en", "US"); 
				}else if (locale.equals("ru")) { 
					l = new Locale("ru", "RU"); 
				}
				
				String languageResourceStr=MemoryDataManagerTask.getLanguageResourceStr(locale);
				String languageResourceFirstLower=MemoryDataManagerTask.getLanguageResourceStr_FirstLetterLowercase(locale);
				user.setLanguageResource(languageResourceStr);
				user.setLanguageResourceFirstLower(languageResourceFirstLower);
				
				session.setAttribute("userLogin", user);
				session.setAttribute("WW_TRANS_I18N_LOCALE", l);
				session.setAttribute("browserLang", locale);
				
				if(userInfo!=null){
					userInfo.setLanguage(user.getLanguage());
					if(userInfo.getLanguage()==1){
						userInfo.setLanguageName("zh_CN");
					}else if(userInfo.getLanguage()==2){
						userInfo.setLanguageName("en");
					}else if(userInfo.getLanguage()==3){
						userInfo.setLanguageName("ru");
					}
					MemoryDataManagerTask.updateUserInfo(userInfo);
				}
			}
		}
		String json = "{\"success\":true}";
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/uploadImportedUserFile")
	public String uploadImportedUserFile(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request) throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringBuffer result_json = new StringBuffer();
		boolean flag=false;
		String key="uploadUserFile"+(user!=null?user.getUserNo():0);
		
		session.removeAttribute(key);
		
		String json = "";
		String fileContent="";
		if(files.length>0 && (!files[0].isEmpty())){
			try{
				byte[] buffer = files[0].getBytes();
				fileContent = new String(buffer, "UTF-8");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		type = new TypeToken<List<ExportUserData>>() {}.getType();
		List<ExportUserData> uploadUserList=gson.fromJson(fileContent, type);
		if(uploadUserList!=null){
			flag=true;
			session.setAttribute(key, uploadUserList);
		}
		result_json.append("{ \"success\":true,\"flag\":"+flag+"}");
		
		json=result_json.toString();
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getUploadedUserTreeData")
	public String getUploadedUserTreeData() throws IOException {
		HttpSession session=request.getSession();
		List<ExportUserData> uploadUserList=null;
		User user = (User) session.getAttribute("userLogin");
		String language=user!=null?user.getLanguageName():"";
		String key="uploadUserFile"+(user!=null?user.getUserNo():0);
		try{
			if(session.getAttribute(key)!=null){
				uploadUserList=(List<ExportUserData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		String json = userService.getUploadedUserTreeData(uploadUserList,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveAllImportedUser")
	public String saveAllImportedUser() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadUserFile"+(user!=null?user.getUserNo():0);
		List<ExportUserData> uploadUserList=null;
		
		String language=user!=null?user.getLanguageName():"";
		try{
			if(session.getAttribute(key)!=null){
				uploadUserList=(List<ExportUserData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		int r=userService.saveAllImportedUser(uploadUserList,user);
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	public String getLimit() {
		return limit;
	}

	public String getOrgId() {
		return orgId;
	}

	public String getPage() {
		return page;
	}

	public User getUser() {
		return user;
	}

	public String[] getUserNos() {
		return userNos;
	}

	public List<User> getUsers() {
		return users;
	}



	public void setLimit(String limit) {
		this.limit = limit;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUserNos(String[] userNos) {
		this.userNos = userNos;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}