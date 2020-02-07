package com.gao.controller.abnormal;

import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.DiagnosisAnalysisAll;
import com.gao.model.User;
import com.gao.service.abnormal.AbnormalWellManagerService;
import com.gao.service.base.CommonDataService;
import com.gao.utils.Constants;
import com.gao.utils.Page;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;

/**
 * <p>
 * 不正常井管理 action类
 * </P>
 * 
 * @author zhao 2015-11-09
 * 
 */
@Controller
@RequestMapping("/abnormalWellManagerController")
@Scope("prototype")
public class AbnormalWellManagerController extends BaseController {
	private static Log log = LogFactory.getLog(AbnormalWellManagerController.class);
	private static final long serialVersionUID = 1L;
	private List<DiagnosisAnalysisAll> chartsList = null;
	private Long count;
	@Autowired
	private AbnormalWellManagerService<?> abnormalWellManagerService;
	@Autowired
	private CommonDataService commonDataService;
	private String gkmc;
	private int limit = 100000;
	private String orgId;
	private int page = 1;
	private String query;
	private int totals;

	

	/*
	 *  **<p>描述：创建不正常井工况统计数据信息</p>
	 * 
	 * @author zhao 2015-11-09
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@RequestMapping("/getAbnornalWellSta")
	public String getAbnornalWellSta() throws Exception {
		this.pager = new Page("pagerForm", request);
		String wellType=ParamUtils.getParameter(request, "wellType");
		orgId=ParamUtils.getParameter(request, "orgId");
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String sql="select rownum as id,v.* from( "
				+ " select t030.gkmc as gkmc,count(t033.jlbh) as total ,t030.gklx as gklx"
				+ " from t_outputwellhistory t033,t_wellinformation t007,"
				+ " t_workstatus t030,t_workstatusalarm t031,sc_org org "
				+ " where t033.jbh=t007.jlbh and t033.gklx=t030.gklx and t030.gklx=t031.gklx "
				+ " and t007.dwbh=org.org_code and t033.gtcjsj=(select max(gtcjsj) from t_outputwellhistory t5 where t5.jbh=t033.jbh and t5.jsbz=1 ) "
				+ " and org.org_id in("+orgId+")  ";
		if(wellType.equals("1")){
			sql+=" and t031.bjbz=1 ";
		}
		sql+="group by t030.gkmc,t030.gklx order by t030.gklx) v";
		String json="";
		json = abnormalWellManagerService.getAbnormalWellStaJson(pager, sql);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/*
	 *  **<p>描述：创建不正常井列表数据信息</p>
	 * 
	 * @author zhao 2015-11-09
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@RequestMapping("/getAbnornalWellList")
	public String getAbnornalWellList() throws Exception {
		Vector<String> v = new Vector<String>();
		String wellType=ParamUtils.getParameter(request, "wellType");
		orgId=ParamUtils.getParameter(request, "orgId");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String gkmc=request.getParameter("gkmc");
		String jh = ParamUtils.getParameter(request, "jh");
		if (StringManagerUtils.isNotNull(gkmc)) {
			v.add(gkmc);
		}else{
			if(wellType.equals("1")){
				v.add("供液极差");
			}else{
				v.add("正常");
			}
		}
		if (StringManagerUtils.isNotNull(jh)) {
			v.add(jh);
		}else{
			v.add(null);
		}
		String json = "";
		json = commonDataService.findCommonModuleDataById(pager, orgId, "abnormalWellList", v);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/*
	 *  **<p>描述：不正常井井名下拉框</p>
	 * 
	 * @author zhao 2015-11-09
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@RequestMapping("/queryAbnormalWellJh")
	public String queryAbnormalWellJh() throws Exception {
		StringBuffer sqlCuswhere = new StringBuffer();
		String wellType=ParamUtils.getParameter(request, "wellType");
		orgId=ParamUtils.getParameter(request, "orgId");
		this.pager=new Page("pageForm",request);
		String jh = ParamUtils.getParameter(request, "jh");
		String gkmc = ParamUtils.getParameter(request, "gkmc");
		String queryDefaultSql="";
		User user =null ;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		if(!StringUtils.isNotBlank(gkmc)){
			queryDefaultSql="select v.* from "
					+ " (select t030.gkmc  from t_outputwellhistory t033,t_workstatus t030,t_workstatusalarm t031,t_wellinformation t007,sc_org org "
					+ " where t033.gklx=t030.gklx and t030.gklx=t031.gklx and t007.jlbh=t033.jbh and t007.dwbh=org.org_code "
					+ " and t033.gtcjsj=(select max(gtcjsj) from t_outputwellhistory t5 where t5.jbh=t033.jbh and t5.jsbz=1 ) "
					+ " and org.org_id in("+orgId+") ";
			if(wellType.equals("1")){
				queryDefaultSql+=" and t031.bjbz=1 ";
			}
			queryDefaultSql+= " order by t030.gklx) v where rownum =1";
			gkmc=abnormalWellManagerService.getDefaultGkmc(queryDefaultSql);
		}
		String sql="select t007.jh as jh,t007.jh as dm "
				+ " from  t_outputwellhistory t033,t_workstatus t030,t_workstatusalarm t031,t_wellinformation t007,t_wellorder t019,sc_org org "
				+ " where t033.jbh=t007.jlbh and t033.gklx=t030.gklx and t030.gklx=t031.gklx and t007.dwbh=org.org_code and t007.jh=t019.jh "
				+ " and t033.gtcjsj=(select max(gtcjsj) from t_outputwellhistory t5 where t5.jbh=t033.jbh and t5.jsbz=1 )  "
				+ " and org.org_id in ("+orgId+") ";
		if (StringUtils.isNotBlank(gkmc)) {
			sql += " and t030.gkmc='"+gkmc+"'";
		}
		if (StringUtils.isNotBlank(jh)) {
			sql += " and t007.jh like '%" + jh + "%'";
		}
		sql += " order by t019.pxbh";
		sqlCuswhere.append("select * from   ( select a.*,rownum as rn from (");
		sqlCuswhere.append(""+sql);
		int maxvalue=pager.getLimit()+pager.getStart();
		sqlCuswhere.append(" ) a where  rownum <="+maxvalue+") b");
		sqlCuswhere.append(" where rn >"+pager.getStart());
		String finalsql=sqlCuswhere.toString();
		
		String json = "";
		json=abnormalWellManagerService.queryAbnormalWellJh(finalsql,sql);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/loadAbnormalGklx")
	public String loadAbnormalGklx() throws Exception {
		String json = this.abnormalWellManagerService.loadAbnormalGklx();
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
//		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping("/doAbnormalWellInformationEdit")
	public String doAbnormalWellInformationEdit() {
		try {
			// wellInformation.setRgzsjd("00:00 --00:00");
			String s_jbh = ParamUtils.getParameter(request, "jbh");
			String s_jlbh = ParamUtils.getParameter(request, "jlbh");
			String s_gklx = ParamUtils.getParameter(request, "gklx");
			String s_sxfw =ParamUtils.getParameter(request, "sxfw");
			String s_gkjgly =ParamUtils.getParameter(request, "gkjgly");
			String s_kssj =ParamUtils.getParameter(request, "kssj");
			String s_jssj =ParamUtils.getParameter(request, "jssj");
			//int jbh=Integer.parseInt(s_jbh);
			int jlbh=Integer.parseInt(s_jlbh);
			int gklx=Integer.parseInt(s_gklx);
			int sxfw=Integer.parseInt(s_sxfw);
			int gkjgly=Integer.parseInt(s_gkjgly);
			this.abnormalWellManagerService.updateorDeleteWorkstatusInformation(jlbh,gklx,sxfw,gkjgly,s_kssj ,s_jssj,"no", "modify");
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			String result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.getWriter().print(result);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<DiagnosisAnalysisAll> getChartsList() {
		return chartsList;
	}


	public Long getCount() {
		return count;
	}

	public int getLimit() {
		return limit;
	}

	public String getOrgId() {
		return orgId;
	}

	public int getPage() {
		return page;
	}

	public String getQuery() {
		return query;
	}

	public int getTotals() {
		return totals;
	}

	public void setChartsList(List<DiagnosisAnalysisAll> chartsList) {
		this.chartsList = chartsList;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setTotals(int totals) {
		this.totals = totals;
	}

	public String getGkmc() {
		return gkmc;
	}

	public void setGkmc(String gkmc) {
		this.gkmc = gkmc;
	}

}
