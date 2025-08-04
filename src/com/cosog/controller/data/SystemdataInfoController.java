package com.cosog.controller.data;

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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.apache.commons.lang.StringUtils;

import com.cosog.controller.base.BaseController;
import com.cosog.model.ExportDataDictionary;
import com.cosog.model.User;
import com.cosog.model.data.SystemdataInfo;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.data.SystemdataInfoService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Constants;
import com.cosog.utils.Page;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.SessionLockHelper;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 系统数据字典表
 * 
 * @author 钱邓水
 * @data 2014-4-10
 */
@Controller
@RequestMapping("/systemdataInfoController")
@Scope("prototype")
public class SystemdataInfoController extends BaseController {

	/**
	 * 需要VersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 实体类
	 */
	private SystemdataInfo systemdataInfo;
	@Autowired
	private CommonDataService service;

	public SystemdataInfo getSystemdataInfo() {
		return systemdataInfo;
	}

	public void setSystemdataInfo(SystemdataInfo systemdataInfo) {
		this.systemdataInfo = systemdataInfo;
	}

	/**
	 * 导入service
	 */
	private SystemdataInfoService systemdataInfoService;

	public SystemdataInfoService getSystemdataInfoService() {
		return systemdataInfoService;
	}

	@Resource
	public void setSystemdataInfoService(SystemdataInfoService systemdataInfoService) {
		this.systemdataInfoService = systemdataInfoService;
	}

	//添加绑定前缀 
	@InitBinder("systemdataInfo")
	public void initBinder2(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("systemdataInfo.");
	}
	
	/**
	 * 查询字典信息
	 */
	@RequestMapping("/findSystemdataInfoByListId")
	public void findSystemdataInfoByListId() throws Exception {
		String typeName = request.getParameter("typeName");
		String findName = request.getParameter("sysName");
		// 分页
		this.pager = new Page("pagerForm", request);
		// 当前登录用户
		User userInfo = this.findCurrentUserInfo();
		List<SystemdataInfo> systemdatainfoList = systemdataInfoService.findSystemdataInfoPageListById(pager, userInfo, typeName, findName);
//		systemdataInfoService.initDataDictionaryPutInCache();
		// 处理乱码。
		response.setCharacterEncoding("utf-8");
		String data=this.getArrayTojsonPage(systemdatainfoList,"","",userInfo.getLanguageName());
		// 输出json数据。
		PrintWriter pw = response.getWriter();
		pw.print(data);
		pw.flush();
		pw.close();
	}
	
	@RequestMapping("/findSystemdataInfo")
	public String findSystemdataInfo() throws Exception {
		String typeName = request.getParameter("typeName");
		String findName = request.getParameter("sysName");
		String dictDeviceType=ParamUtils.getParameter(request, "dictDeviceType");
		this.pager = new Page("pagerForm", request);
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String json="{}";
		if(user!=null){
			json=systemdataInfoService.findSystemdataInfo(dictDeviceType,user,typeName,findName,pager);
		}
		
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportDataDictionaryCompleteData")
	public String exportDataDictionaryCompleteData() throws IOException {
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
		
		String json = this.systemdataInfoService.exportDataDictionaryCompleteData(user);
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
	 * 创建字典信息
	 */
	@RequestMapping("/addSystemdataInfo")
	public void addSystemdataInfo(@ModelAttribute SystemdataInfo systemdataInfo) throws Exception {
		String jsonaddstr = "";
		if (null != systemdataInfo && !"".equals(systemdataInfo)) {
			// 当前登录用户
			User userInfo = this.findCurrentUserInfo();
			// 添加项值
			String paramsdtblstringId = request.getParameter("paramsdtblstringId");
			jsonaddstr = systemdataInfoService.saveSystemdataInfo(systemdataInfo, userInfo, paramsdtblstringId);

		} else {
			jsonaddstr = "{success:true,msg:false}";
		}
		// 处理乱码。
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(jsonaddstr);
		pw.flush();
		pw.close();
	}

	/**
	 * 修改字典信息
	 */
	@RequestMapping("/editSystemdataInfo")
	public void editSystemdataInfo(@ModelAttribute SystemdataInfo systemdataInfo) throws Exception {
		String jsonaddstr = "";
		if (null != systemdataInfo && !"".equals(systemdataInfo)) {
			String getParamsId=request.getParameter("hideSysDataValName");
			// 当前登录用户
			User userInfo = this.findCurrentUserInfo();
			// 添加项值
			jsonaddstr = systemdataInfoService.editSystemdataInfo(systemdataInfo, userInfo,getParamsId);
		} else {
			jsonaddstr = "{success:true,msg:false}";
		}
//		systemdataInfoService.initDataDictionaryPutInCache();
		// 处理乱码。
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(jsonaddstr);
		pw.flush();
		pw.close();
	}
	
	@RequestMapping("/updateDataDictionaryInfo")
	public String updateDataDictionaryInfo() throws IOException {
		String result = "{success:true,flag:true}";
		try {
			String sysdataid = ParamUtils.getParameter(request, "sysdataid");
			String name = ParamUtils.getParameter(request, "name");
			String code = ParamUtils.getParameter(request, "code");
			String sorts = ParamUtils.getParameter(request, "sorts");
			String moduleName = ParamUtils.getParameter(request, "moduleName");
			User userInfo = this.findCurrentUserInfo();
			int r=this.systemdataInfoService.updateDataDictionaryInfo(sysdataid,name,code,sorts,moduleName,userInfo.getLanguageName());
			if(r==1){
				result = "{success:true,flag:true}";
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

	/**
	 * 删除字典信息
	 */
	@RequestMapping("/deleteSystemdataInfoById")
	public void deleteSystemdataInfoById() throws Exception {
		String jsondelete = "";
		String getSysDaId = request.getParameter("paramsId");
//		systemdataInfoService.initDataDictionaryPutInCache();
		if (!StringUtils.isBlank(getSysDaId)) {
			boolean boo = systemdataInfoService.deleteSystemdataInfoById(findCurrentUserInfo(), getSysDaId);
			if (boo) {
				jsondelete = "{success:true,flag:true}";
			} else {
				jsondelete = "{success:false,flag:false}";
			}
		} else {
			jsondelete = "{success:true,flag:false}";
		}
		// 处理乱码。
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(jsondelete);
		pw.flush();
		pw.close();
	}

	@RequestMapping("/uploadImportedDataDictionaryFile")
	public String uploadImportedDataDictionaryFile(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request) throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringBuffer result_json = new StringBuffer();
		boolean flag=false;
		String key="uploadImportedDataDictionaryFile"+(user!=null?user.getUserNo():0);
		
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
		
		String code="";
		try{
			Map<String, Object> result = gson.fromJson(fileContent, new TypeToken<Map<String, Object>>(){}.getType());
			code =result.containsKey("Code")?((String) result.get("Code")):"";
			if("DataDictionary".equalsIgnoreCase(code)){
				if (result.containsKey("List")) {
					List<ExportDataDictionary> uploadFileList=new ArrayList<>();
					List<Map<String, Object>> listData = (List<Map<String, Object>>) result.get("List");
			        for (Map<String, Object> item : listData) {
			            String itemJson = gson.toJson(item);
			            ExportDataDictionary exportDataDictionary = gson.fromJson(itemJson, ExportDataDictionary.class);
			            uploadFileList.add(exportDataDictionary);
			        }
			        if(uploadFileList!=null){
						flag=true;
						session.setAttribute(key, uploadFileList);
					}
				}
			}
		}catch(Exception e){
			flag=false;
			e.printStackTrace();
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
	
	@RequestMapping("/getUploadedDataDictionaryTreeData")
	public String getUploadedDataDictionaryTreeData() throws IOException {
		HttpSession session=request.getSession();
		List<ExportDataDictionary> uploadDataDictionaryList=null;
		User user = (User) session.getAttribute("userLogin");
		String language=user!=null?user.getLanguageName():"";
		String key="uploadImportedDataDictionaryFile"+(user!=null?user.getUserNo():0);
		try{
			if(session.getAttribute(key)!=null){
				uploadDataDictionaryList=(List<ExportDataDictionary>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		String json = this.systemdataInfoService.getUploadedDataDictionaryTreeData(uploadDataDictionaryList,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveAllImportedDataDictionary")
	public String saveAllImportedDataDictionary() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadImportedDataDictionaryFile"+(user!=null?user.getUserNo():0);
		List<ExportDataDictionary> uploadDataDictionaryList=null;
		
		String language=user!=null?user.getLanguageName():"";
		try{
			if(session.getAttribute(key)!=null){
				uploadDataDictionaryList=(List<ExportDataDictionary>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		int r=this.systemdataInfoService.saveAllImportedDataDictionary(uploadDataDictionaryList,user);
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
}
