package com.gao.controller.data;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.User;
import com.gao.model.data.DataitemsInfo;
import com.gao.service.data.DataitemsInfoService;
import com.gao.service.data.SystemdataInfoService;
import com.gao.utils.Page;

/**
 * 系统数据字典数据项值表
 * 
 * @author 钱邓水
 * @data 2014-4-10
 */
@Controller
@RequestMapping("/dataitemsInfoController")
@Scope("prototype")
public class DataitemsInfoController extends BaseController {

	/**
	 * 需要VersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 实体类
	 */
	private DataitemsInfo dataitemsInfo;

	public DataitemsInfo getDataitemsInfo() {
		return dataitemsInfo;
	}

	public void setDataitemsInfo(DataitemsInfo dataitemsInfo) {
		this.dataitemsInfo = dataitemsInfo;
	}

	/**
	 * 导入service
	 */
	@Autowired
	private SystemdataInfoService systemdataInfoService;
	private DataitemsInfoService dataitemsInfoService;

	public DataitemsInfoService getDataitemsInfoService() {
		return dataitemsInfoService;
	}

	@Resource
	public void setDataitemsInfoService(DataitemsInfoService dataitemsInfoService) {
		this.dataitemsInfoService = dataitemsInfoService;
	}

	//添加绑定前缀 
	@InitBinder("dataitemsInfo")
	public void initBinder2(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("dataitemsInfo.");
	}
	
	/**
	 * 查询数据项值信息
	 */
	@RequestMapping("/findDataitemsInfoByListId")
	public void findDataitemsInfoByListId() throws Exception {
		//HttpServletResponse response = ServletActionContext.getResponse();
		//HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String getSysId = request.getParameter("sysId");
			String getdataTabId = request.getParameter("dataTabId");
			String getdataName = request.getParameter("dataName");
			// 分页
			this.pager = new Page("pagerForm", request);
			User userInfo = this.findCurrentUserInfo();
			List<DataitemsInfo> dataitemsInfoList = dataitemsInfoService.findDataitemsInfoPageListById(pager, userInfo, getSysId, getdataTabId, getdataName);

			// 处理乱码。
			response.setCharacterEncoding("utf-8");
			// 输出json数据。
			response.getWriter().write(this.getArrayTojsonPage(dataitemsInfoList));
			// 异常抛出
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 创建字典信息
	 */
	@RequestMapping("/addDataitemsInfo")
	public void addDataitemsInfo(@ModelAttribute DataitemsInfo dataitemsInfo) throws Exception {
		//HttpServletResponse response = ServletActionContext.getResponse();
		//HttpServletRequest request = ServletActionContext.getRequest();
		String jsonaddstr = "";
		//systemdataInfoService.initDataDictionaryPutInCache();
		if (null != dataitemsInfo && !"".equals(dataitemsInfo)) {
			// 当前登录用户
			User userInfo = this.findCurrentUserInfo();
			// 添加项值
			String sysId = request.getParameter("sysId");
			jsonaddstr = dataitemsInfoService.saveDataitemsInfo(dataitemsInfo, userInfo, sysId);
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
	@RequestMapping("/editDataitemsInfo")
	public void editDataitemsInfo(@ModelAttribute DataitemsInfo dataitemsInfo) throws Exception {
		//HttpServletResponse response = ServletActionContext.getResponse();
		String jsonaddstr = "";
		if (null != dataitemsInfo && !"".equals(dataitemsInfo)) {
			// 当前登录用户
			User userInfo = this.findCurrentUserInfo();
			jsonaddstr = dataitemsInfoService.editDataitemsInfo(dataitemsInfo, userInfo);
			// jsonaddstr = "{success:true,msg:true}";
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
	 * 删除数据项信息
	 */
	@RequestMapping("/deleteDataitemsInfoById")
	public void deleteDataitemsInfoById() throws Exception {
		//HttpServletResponse response = ServletActionContext.getResponse();
		//HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String jsondelete = "";
			String getDatatId = request.getParameter("paramsId");
			systemdataInfoService.initDataDictionaryPutInCache();
			if (StringUtils.isNotBlank(getDatatId)) {
				boolean boo = dataitemsInfoService.deleteDataitemsInfoById(this.findCurrentUserInfo(), getDatatId);
				if (boo) {
					jsondelete = "{success:true,flag:true}";
				} else {
					jsondelete = "{success:true,flag:false}";
				}
			} else {
				jsondelete = "{success:false,flag:false}";
			}
			// 处理乱码。
			response.setCharacterEncoding("utf-8");
			// 输出json数据。
			response.getWriter().write(jsondelete);
			// 异常抛出
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
