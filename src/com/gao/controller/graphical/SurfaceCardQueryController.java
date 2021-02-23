package com.gao.controller.graphical;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.User;
import com.gao.service.base.CommonDataService;
import com.gao.service.graphical.SurfaceCardService;
import com.gao.utils.Constants;
import com.gao.utils.Page;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;

/**
 * <p>地面功图查询Action类 </p>
 * 
 * @author li 2015-04-14
 * 
 */

@Controller
@RequestMapping("/surfaceCardQueryController")
@Scope("prototype")
public class SurfaceCardQueryController extends BaseController {
	private static Log log = LogFactory.getLog(SurfaceCardQueryController.class);
	private static final long serialVersionUID = 1L;
	private String orgId;
	private String wellName;
	private String startDate;
	private String endDate;

	@Autowired
	private SurfaceCardService<?> service;
	@Autowired
	private CommonDataService commonDataService;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		orgId=ParamUtils.getParameter(request, "orgId");
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		log.debug("orgId=" + orgId + "wellName=-=" + wellName);
		return SUCCESS;
	}

	/**
	 * 描述：查询地面功图数据信息
	 */
	@RequestMapping("/querySurfaceCard")
	public String querySurfaceCard() throws IOException {
		orgId = ParamUtils.getParameter(request, "orgId");
		wellName = ParamUtils.getParameter(request, "wellName");
		startDate = ParamUtils.getParameter(request, "startDate");
		endDate = ParamUtils.getParameter(request, "endDate");
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		this.pager = new Page("pagerForm", request);
		String json = "";
		try {
			if(StringManagerUtils.isNotNull(wellName)&&(!StringManagerUtils.isNotNull(endDate))){
				String sql = " select to_char(max(t.acqTime),'yyyy-mm-dd') from tbl_rpc_diagram_hist t,tbl_wellinformation t2 where t.wellId=t2.id and  t2.wellName='"+wellName+"'  ";
				List list = this.commonDataService.reportDateJssj(sql);
				if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
					endDate = list.get(0).toString();
				} else {
					endDate = StringManagerUtils.getCurrentTime();
				}
			}
			
			if(!StringManagerUtils.isNotNull(startDate)&&StringManagerUtils.isNotNull(endDate)){
				startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-1);
			}
			pager.setStart_date(startDate);
			pager.setEnd_date(endDate);
			
			json = this.service.querySurfaceCard(pager, orgId, wellName, startDate, endDate);
			pw.print(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getWellList")
	public String getWellList() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		wellName = ParamUtils.getParameter(request, "wellName");
		startDate = ParamUtils.getParameter(request, "startDate");
		endDate = ParamUtils.getParameter(request, "endDate");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		if(StringManagerUtils.isNotNull(wellName)&&(!StringManagerUtils.isNotNull(endDate))){
			String sql = " select to_char(max(t.acqTime),'yyyy-mm-dd') from tbl_rpc_diagram_hist t,tbl_wellinformation t2 where t.wellId=t2.id and  t2.wellName='"+wellName+"'  ";
			List list = this.commonDataService.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		
		if(!StringManagerUtils.isNotNull(startDate)&&StringManagerUtils.isNotNull(endDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-1);
		}
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		json = this.service.getWellList(pager,orgId,wellName,startDate,endDate);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getWellName() {
		return wellName;
	}

	public void setWellName(String wellName) {
		this.wellName = wellName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
