package com.gao.service.abnormal;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gao.dao.BaseDao;
import com.gao.model.WellWorkStatus;
import com.gao.service.base.BaseService;
import com.gao.service.base.CommonDataService;
import com.gao.utils.Page;
import com.google.gson.Gson;

/**
 * 工况诊断（统计）- service层
 * 
 * @author ding
 * 
 */
@Service("abnormalWellManagerService")
public class AbnormalWellManagerService<T> extends BaseService<T>{
	private BaseDao dao;
	@Autowired
	private CommonDataService service;
	

	public String getAbnormalWellStaJson(Page pager, String sql)
			throws Exception {
		String json="";
		String columns = service.showTableHeadersColumns("abnormalWellDiaSta");
		WellWorkStatus abnormalWellSta;
		List<WellWorkStatus> abnormalWellStaList=new ArrayList<WellWorkStatus>();
		List<T> list= dao.getAllPageBySql(sql,pager);
		for(int i=0;i<list.size();i++){
			abnormalWellSta=new WellWorkStatus();
			Object[] obj=(Object[])list.get(i);
			abnormalWellSta.setId(Integer.parseInt(obj[0].toString()));
			abnormalWellSta.setGkmc(obj[1].toString());
			abnormalWellSta.setTotal(Integer.parseInt(obj[2].toString()));
			abnormalWellStaList.add(abnormalWellSta);
		}
		Gson g = new Gson();
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{success:true,");
		strBuf.append("totalCount:"+pager.getTotalCount()+",");
		if(abnormalWellStaList.size()>0){
			strBuf.append("firstGkmc:\""+abnormalWellStaList.get(0).getGkmc()+"\",");
		}else{
			strBuf.append("firstGkmc:\"\",");
		}
		strBuf.append("columns:" + columns + ",");
		String data = g.toJson(abnormalWellStaList);
		strBuf.append("list:" + data + "}");
		json=strBuf.toString();
		return json;
	}
	
	public String queryAbnormalWellJh(String finalsql,String sql){
		StringBuffer result_json = new StringBuffer();
		try {
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(finalsql);
			result_json.append("{\"totals\":"+totals+",\"list\":[{boxkey:\"\",boxval:\"选择全部\"},");
			String get_key = "";
			String get_val = "";
			if (null != list && list.size() > 0) {
				for (Object o : list) {
					Object[] obj = (Object[]) o;
					get_key = obj[0] + "";
					get_val = (String) obj[1];
					result_json.append("{boxkey:\"" + get_key + "\",");
					result_json.append("boxval:\"" + get_val + "\"},");
				}
				if (result_json.toString().length() > 1) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
			}
			result_json.append("]}");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_json.toString();
	}

	public String loadAbnormalGklx() throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "select t.gklx,t.gkmc from t_workstatus t where gklx>=1200 order by gklx";
		try {
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(sql);
			result_json.append("{\"totals\":"+totals+",\"list\":[");
			String get_key = "";
			String get_val = "";
			if (null != list && list.size() > 0) {
				for (Object o : list) {
					Object[] obj = (Object[]) o;
					get_key = obj[0] + "";
					get_val = (String) obj[1];
					result_json.append("{boxkey:\"" + get_key + "\",");
					result_json.append("boxval:\"" + get_val + "\"},");
				}
				if (result_json.toString().length() > 1) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
			}
			result_json.append("]}");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_json.toString();
	}
	
	public String getDefaultGkmc(String sql) throws Exception {
		String defaultGkmc="";
		try {
			
			List<?> list = this.findCallSql(sql);
			defaultGkmc=list.get(0).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultGkmc;
	}
	
	public void updateorDeleteWorkstatusInformation(int jlbh,int gklx,int sxfw,int gkjgly,String s_kssj ,String s_jssj,
			String ids, String comandType) throws Exception {
		getBaseDao().updateorDeleteWorkstatusInformation(jlbh,gklx,sxfw,gkjgly,s_kssj ,s_jssj, ids, comandType);
	}


	public BaseDao getDao() {
		return dao;
	}

	@Resource
	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

}
