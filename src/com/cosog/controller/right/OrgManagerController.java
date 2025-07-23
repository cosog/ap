package com.cosog.controller.right;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cosog.controller.base.BaseController;
import com.cosog.model.Module;
import com.cosog.model.Org;
import com.cosog.model.OrgGridPanelData;
import com.cosog.model.OrgParent;
import com.cosog.model.OrgWellInfoBean;
import com.cosog.model.User;
import com.cosog.model.ExportOrganizationData;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.right.ModuleManagerService;
import com.cosog.service.right.OrgManagerService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Config;
import com.cosog.utils.Constants;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.OrgRecursion;
import com.cosog.utils.Page;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.Recursion;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 组织模块维护管理Action
 * 
 * @author gao 2014-05-08
 * 
 */
@Controller
@RequestMapping("/orgManagerController")
@Scope("prototype")
public class OrgManagerController extends BaseController {

	private static Log log = LogFactory.getLog(OrgManagerController.class);
	private static final long serialVersionUID = -281275682819237996L;
	private List<Org> list;
	private Org org;
	@Autowired
	private OrgManagerService<Org> orgService;
	@Autowired
	private ModuleManagerService<Module> modService;
	@Autowired
	private CommonDataService service;

	//添加绑定前缀
	@InitBinder("org")    
    public void initBinder2(WebDataBinder binder) {    
            binder.setFieldDefaultPrefix("org.");    
    }  
	
	@RequestMapping("/constructOrgRightTree")
	public String constructOrgRightTree() throws IOException {
		String orgName = ParamUtils.getParameter(request, "orgName");
		String orgId = ParamUtils.getParameter(request, "orgId");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		list = this.orgService.loadOrgs(Org.class,orgName,orgId,"");
		String json = "";
		Recursion r = new Recursion(language);
		for (Org org : list) {
			if (!r.hasParent(list, org)) {
				json = r.recursionFn(list, org);
			}
		}
		json = r.modifyStr(json);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();

		return null;
	}
	
	@RequestMapping("/loadOrgComboxTreeData")
	public String loadOrgComboxTreeData() throws IOException {
		String orgId = ParamUtils.getParameter(request, "orgId");
		String currentOrgId = ParamUtils.getParameter(request, "currentOrgId");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		list = this.orgService.loadOrgs(Org.class,"",orgId,currentOrgId);
		String json = "";
		Recursion r = new Recursion(language);
		for (Org org : list) {
			if (!r.hasParent(list, org)) {
				json = r.recursionOrgCombTree(list, org);
			}
		}
		json = r.modifyStr(json);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();

		return null;
	}

	/**
	 * <p>
	 * 描述：前台组织树形菜单数据
	 * </p>
	 * 
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/constructOrgTree")
	public String constructOrgTree() throws IOException {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		if (user != null) {
			StringBuffer orgIdString = new StringBuffer();
			boolean cache = Config.getInstance().configFile.getAp().getOthers().getCache();
			if (cache) {
				Map<String, Object> map = DataModelMap.getMapObject();
				log.warn("用户拥有的组织启用缓存...");
				User oldUser = (User) map.get("oldUser");
				String curUserId = user.getUserId();
				String oldUserId = "";
				if (oldUser != null) {
					oldUserId = oldUser.getUserId();
				}
				if (map.get("orgTree") != null && oldUserId.equalsIgnoreCase(curUserId)) {
					list = (List<Org>) map.get("orgTree");
				} else {
					list = orgService.loadOrgAndChildTreeListById(Org.class, user.getUserOrgid());
					map.put("oldUser", "");
					map.put("oldUser", user);
					map.put("orgTree", list);
				}
			}else{
				log.warn("用户拥有的组织未启用缓存...");
				list = orgService.loadOrgAndChildTreeListById(Org.class, user.getUserOrgid());
			}

			for (Org o : list) {
				orgIdString.append(o.getOrgId() + ",");
			}
			if (StringManagerUtils.isNotNull(orgIdString.toString())) {
				orgIdString.deleteCharAt(orgIdString.length() - 1);
				user.setUserorgids(orgIdString.toString());
			}
		}
		String json = "";
		StringBuffer strBuf = new StringBuffer();
		Recursion r = new Recursion(language);// 递归类，将org集合构建成一棵树形菜单的json
		if (user != null) {
			for (Org org : list) {
				if (!r.hasParent(list, org)) {
					json = r.recursionFn(list, org);
				}
			}
			json = r.modifyOrgStr(json);
			strBuf.append(json);
		} else {
			strBuf.append("{success:true,flag:true,\"msg\":\"用户会话已经过期!\"}");
		}
		json = strBuf.toString();
		pw.print(json);
		pw.flush();
		pw.close();

		return null;
	}

	/**
	 * <p>
	 * 描述：创建组织管理的treePanel json数据信息
	 * </p>
	 * 
	 * @author gao 2014-05-08
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/constructOrgTreeGridTree")
	public String constructOrgTreeGridTree() throws IOException {
		String orgName = ParamUtils.getParameter(request, "orgName");
		String orgId = ParamUtils.getParameter(request, "orgId");
		String dictDeviceType=ParamUtils.getParameter(request, "dictDeviceType");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		List<?> list = this.orgService.queryOrgs(Org.class, orgName,orgId,language);
		String json = "";
		OrgRecursion r = new OrgRecursion();
		if (list != null) {
			for (Object org : list) {
				Object[] obj = (Object[]) org;
				if (!r.hasParent(list, obj)) {
					json = r.recursionOrgFn(list, obj);
				}
			}
		}
		json = r.modifyOrgStr(json);
		json = this.getArrayTojsonPage(json, "",dictDeviceType,language);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportOrganizationCompleteData")
	public String exportOrganizationCompleteData() throws IOException {
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
		
		String json = this.orgService.exportOrganizationCompleteData(user);
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

	/**
	 * <p>
	 * 描述：新增组织数据信息
	 * </p>
	 * 
	 * @author gao 2014-05-08
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/doOrgAdd")
	public String doOrgAdd(@ModelAttribute Org org ) throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {
			HttpSession session=request.getSession();
			User userInfo = this.findCurrentUserInfo();
			if (org.getOrgParent() == null || org.getOrgParent()==0) {
				String sql = "select t.org_id from tbl_org t where t.org_parent=0 and rownum=1";
				List list = this.service.findCallSql(sql);
				if(list.size()>0){
					org.setOrgParent(StringManagerUtils.stringToInteger(list.get(0)+""));
				}else{
					org.setOrgParent(0);
				}
			}
			
			if(userInfo.getLanguage()==1){
				org.setOrgName_en(org.getOrgName_zh_CN());
				org.setOrgName_ru(org.getOrgName_zh_CN());
			}else if(userInfo.getLanguage()==2){
				org.setOrgName_zh_CN(org.getOrgName_en());
				org.setOrgName_ru(org.getOrgName_en());
			}else if(userInfo.getLanguage()==3){
				org.setOrgName_zh_CN(org.getOrgName_ru());
				org.setOrgName_en(org.getOrgName_ru());
			}
			
			this.orgService.addOrg(org);
			result = "{success:true,msg:true}";
			Map<String, Object> map = DataModelMap.getMapObject();
			
			userInfo.setUserParentOrgids(orgService.findParentIds(userInfo.getUserOrgid()));
			userInfo.setUserorgids(orgService.findChildIds(userInfo.getUserOrgid()));
			userInfo.setUserOrgNames(orgService.findChildNames(userInfo.getUserOrgid(),userInfo.getLanguageName()));
			userInfo.setAllOrgPatentNodeIds(orgService.fingAllOrgParentNodeIds());
			session.setAttribute("userLogin", userInfo);
			
			list = orgService.findloadOrgTreeListById(Org.class, userInfo.getUserorgids());
			map.put("oldUser", "");
			map.put("oldUser", userInfo);
			map.put("orgTree", list);
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			out.print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "{success:false,msg:false}";
			out.print(result);
		}
		return null;
	}

	/**
	 * <p>
	 * 描述：批量删除组织数据信息
	 * </p>
	 * 
	 * @author gao 2014-05-08
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/doOrgBulkDelete")
	public String doOrgBulkDelete() {
		try {
			String OrgIds = ParamUtils.getParameter(request, "paramsId");
			int deleteCount=0;
			if(StringManagerUtils.isNotNull(OrgIds)){
				deleteCount=this.orgService.bulkDelete(OrgIds);
			}
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			String result = "{success:true,flag:true,\"deleteCount\":"+deleteCount+"}";
			Map<String, Object> map = DataModelMap.getMapObject();
			HttpSession session=request.getSession();
			User userInfo = this.findCurrentUserInfo();
			userInfo.setUserParentOrgids(orgService.findParentIds(userInfo.getUserOrgid()));
			userInfo.setUserorgids(orgService.findChildIds(userInfo.getUserOrgid()));
			userInfo.setUserOrgNames(orgService.findChildNames(userInfo.getUserOrgid(),userInfo.getLanguageName()));
			userInfo.setAllOrgPatentNodeIds(orgService.fingAllOrgParentNodeIds());
			session.setAttribute("userLogin", userInfo);
			list = orgService.findloadOrgTreeListById(Org.class, userInfo.getUserorgids());
			map.put("oldUser", "");
			map.put("oldUser", userInfo);
			map.put("orgTree", list);
			response.getWriter().print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p>
	 * 描述：编辑组织数据信息
	 * </p>
	 * 
	 * @author gao 2014-05-08
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/doOrgEdit")
	public String doOrgEdit(@ModelAttribute Org org) {
		try {
			HttpSession session=request.getSession();
			User userInfo = this.findCurrentUserInfo();
			if (org.getOrgParent() == null) {
				org.setOrgParent(0);
			}
			if(userInfo!=null){
				if(userInfo.getLanguage()==1){
					org.setOrgName_en(org.getOrgName_zh_CN());
					org.setOrgName_ru(org.getOrgName_zh_CN());
				}else if(userInfo.getLanguage()==2){
					org.setOrgName_zh_CN(org.getOrgName_en());
					org.setOrgName_ru(org.getOrgName_en());
				}else if(userInfo.getLanguage()==3){
					org.setOrgName_zh_CN(org.getOrgName_ru());
					org.setOrgName_en(org.getOrgName_ru());
				}
			}
			this.orgService.modifyOrg(org);
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			String result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			Map<String, Object> map = DataModelMap.getMapObject();
			
			userInfo.setUserParentOrgids(orgService.findParentIds(userInfo.getUserOrgid()));
			userInfo.setUserorgids(orgService.findChildIds(userInfo.getUserOrgid()));
			userInfo.setUserOrgNames(orgService.findChildNames(userInfo.getUserOrgid(),userInfo.getLanguageName()));
			userInfo.setAllOrgPatentNodeIds(orgService.fingAllOrgParentNodeIds());
			session.setAttribute("userLogin", userInfo);
			list = orgService.findloadOrgTreeListById(Org.class, userInfo.getUserorgids());
			map.put("oldUser", "");
			map.put("oldUser", userInfo);
			map.put("orgTree", list);
			response.getWriter().print(result);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		List<?> list = this.orgService.loadParentOrgs(Org.class,language);
		log.debug("OrgManagerAction list==" + list.size());
		OrgParent op = null;
		List<OrgParent> olist = new ArrayList<OrgParent>();
		for (int i = 0; i < list.size(); i++) {

			// 使用对象数组
			Object[] objArray = (Object[]) list.get(i);
			// 最后使用forEach迭代obj对象
			op = new OrgParent();
			op.setOrgId((Integer) objArray[0]);
			op.setOrgName(objArray[1].toString());
			olist.add(op);

		}
		Gson g = new Gson();
		String json = g.toJson(olist);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}

	@RequestMapping("/findByPrimary")
	public String findByPrimary() {
		try {
			Integer parentId = ParamUtils.getIntAttribute(request, "parentId", 0);
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			String result = "{success:true,msg:true" + ",orgLevel:" + this.orgService.findByPrimary(parentId).get(0) + "}";
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

	/**
	 * <p>
	 * 描述：当前组织编码是否存在
	 * </p>
	 * 
	 * @return
	 */
	@RequestMapping("/findCurrentOrgCodeIsNotExist")
	public String findCurrentOrgCodeIsNotExist() {
		try {

			String orgCode = "0000";
			orgCode = ParamUtils.getParameter(request, "orgCode");
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			List<?> codes = this.orgService.findCurrentOrgCodeIsNotExist(orgCode);
			String newCode = "0000";
			if (codes.size() > 0) {
				newCode = (String) codes.get(0);
				int code = Integer.parseInt(newCode) + 1;
				newCode = code + "";
			}
			String result = "{success:true,msg:true" + ",orgCode:\"" + newCode + "\"}";
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

	@RequestMapping("/findMaxNum")
	public String findMaxNum() {
		try {
			Integer orgLevel1 = ParamUtils.getIntAttribute(request, "orgLevel", 1);
			int maxId = this.orgService.findMaxNum(orgLevel1).intValue();
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			String result = "{success:true,msg:true,maxId:" + (maxId) + "}";
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

	/**
	 * <p>
	 * 描述：获取当前父级组织id有几个子节点
	 * </p>
	 * 
	 * @author gao 2014-05-09
	 * @return null
	 */
	@RequestMapping("/findOrgChildrenByparentId")
	public String findOrgChildrenByparentId() {
		try {

			// Integer parentId = ParamUtils.getIntAttribute(request,
			// "parentId", 0);
			Integer parentId = Integer.parseInt(request.getParameter("parentId"));
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			list = this.orgService.findOrgChildrenByparentId(parentId);
			String orgCode = "";
			log.debug("parentId==" + parentId);
			if (parentId != 0) {
				orgCode = this.orgService.findCurrentOrgCodeByparentId(parentId).get(0).getOrgCode();
			} else {
				orgCode = "0" + (list.size() + 1);
			}

			String result = "{success:true,msg:true,childNodes:" + list.size() + ",orgCode:'" + orgCode + "'}";
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

	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadWellInfoOrgs")
	public String loadWellInfoOrgs() throws Exception {
		// TODO Auto-generated method stub
		List<?> list = this.orgService.loadWellInfoOrgs(Org.class);
		log.debug("loadWellInfoOrgs list==" + list.size());
		OrgWellInfoBean op = null;
		List<OrgWellInfoBean> olist = new ArrayList<OrgWellInfoBean>();
		for (int i = 0; i < list.size(); i++) {

			// 使用对象数组
			Object[] objArray = (Object[]) list.get(i);
			// 最后使用forEach迭代obj对象
			op = new OrgWellInfoBean();
			op.setOrgCode(((String) objArray[0]));
			op.setOrgName(objArray[1].toString());
			olist.add(op);
		}
		Gson g = new Gson();
		String json = g.toJson(olist);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/changeOrgParent")
	public String changeOrgParent() throws Exception {
		this.pager=new Page("pageForm",request);
		String selectedCurrentOrgId = ParamUtils.getParameter(request, "selectedCurrentOrgId");
		String selectedDestinationOrgId=ParamUtils.getParameter(request, "selectedDestinationOrgId");
		int result=this.orgService.changeOrgParent(selectedCurrentOrgId,selectedDestinationOrgId);
		String json = "{\"success\":true,\"resultStatus\":"+result+"}";
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/uploadImportedOrganizationFile")
	public String uploadImportedOrganizationFile(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request) throws Exception {
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringBuffer result_json = new StringBuffer();
		boolean flag=false;
		String key="uploadOrganizationFile";
		
		HttpSession session=request.getSession();
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
		
		type = new TypeToken<List<ExportOrganizationData>>() {}.getType();
		List<ExportOrganizationData> uploadOrganizationList=gson.fromJson(fileContent, type);
		if(uploadOrganizationList!=null){
			flag=true;
			session.setAttribute(key, uploadOrganizationList);
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
	
	@RequestMapping("/getUploadedOrganizationTreeData")
	public String getUploadedOrganizationTreeData() throws IOException {
		HttpSession session=request.getSession();
		String key="uploadOrganizationFile";
		List<ExportOrganizationData> uploadOrganizationList=null;
		User user = (User) session.getAttribute("userLogin");
		String language=user!=null?user.getLanguageName():"";
		try{
			if(session.getAttribute(key)!=null){
				uploadOrganizationList=(List<ExportOrganizationData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		String json = "";
		OrgRecursion r = new OrgRecursion();
		if (uploadOrganizationList != null) {
			for (ExportOrganizationData e : uploadOrganizationList) {
				if (!r.hasParent(uploadOrganizationList, e)) {
					json = r.recursionOrgFn(uploadOrganizationList, e,language);
				}
			}
		}
		json = r.modifyOrgStr(json);
		json = this.getArrayTojsonPage(json, "","0",language);
		
//		String json = orgService.getUploadedOrganizationTreeData(uploadOrganizationList,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}

	public List<Org> getList() {
		return list;
	}

	public Org getOrg() {
		return org;
	}

	public void setList(List<Org> list) {
		this.list = list;
	}

	public void setOrg(Org org) {
		this.org = org;
	}
}
