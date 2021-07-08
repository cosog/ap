package com.cosog.controller.data;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.commons.lang.StringUtils;

import com.cosog.controller.base.BaseController;
import com.cosog.model.User;
import com.cosog.model.data.SystemdataInfo;
import com.cosog.service.data.SystemdataInfoService;
import com.cosog.utils.Page;

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
		//HttpServletResponse response = ServletActionContext.getResponse();
		//HttpServletRequest request = ServletActionContext.getRequest();
		String typeName = request.getParameter("typeName");
		String findName = request.getParameter("sysName");
		// 分页
		this.pager = new Page("pagerForm", request);
		// 当前登录用户
		User userInfo = this.findCurrentUserInfo();
		List<SystemdataInfo> systemdatainfoList = systemdataInfoService.findSystemdataInfoPageListById(pager, userInfo, typeName, findName);
		systemdataInfoService.initDataDictionaryPutInCache();
		// 处理乱码。
		response.setCharacterEncoding("utf-8");
		String data=this.getArrayTojsonPage(systemdatainfoList,"dataDictionary");
		// 输出json数据。
		response.getWriter().write(data);

	}

	/**
	 * 创建字典信息
	 */
	@RequestMapping("/addSystemdataInfo")
	public void addSystemdataInfo(@ModelAttribute SystemdataInfo systemdataInfo) throws Exception {
		//HttpServletResponse response = ServletActionContext.getResponse();
		//HttpServletRequest request = ServletActionContext.getRequest();
		String jsonaddstr = "";
		systemdataInfoService.initDataDictionaryPutInCache();
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
		// 输出json数据。
		response.getWriter().write(jsonaddstr);
	}

	/**
	 * 修改字典信息
	 */
	@RequestMapping("/editSystemdataInfo")
	public void editSystemdataInfo(@ModelAttribute SystemdataInfo systemdataInfo) throws Exception {
		//HttpServletResponse response = ServletActionContext.getResponse();
		//HttpServletRequest request = ServletActionContext.getRequest();
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
		systemdataInfoService.initDataDictionaryPutInCache();
		// 处理乱码。
		response.setCharacterEncoding("utf-8");
		// 输出json数据。
		response.getWriter().write(jsonaddstr);
	}

	/**
	 * 删除字典信息
	 */
	@RequestMapping("/deleteSystemdataInfoById")
	public void deleteSystemdataInfoById() throws Exception {
		//HttpServletResponse response = ServletActionContext.getResponse();
		//HttpServletRequest request = ServletActionContext.getRequest();
		String jsondelete = "";
		String getSysDaId = request.getParameter("paramsId");
		systemdataInfoService.initDataDictionaryPutInCache();
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
		// 输出json数据。
		response.getWriter().write(jsondelete);
	}

}
