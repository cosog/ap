package com.gao.service.back;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gao.model.Outputwellproduction;
import com.gao.model.WellInformation;
import com.gao.service.base.BaseService;
import com.gao.utils.PagingConstants;
import com.google.gson.Gson;

@Service("outputwellproductionService")
public class OutputwellproductionService<T> extends BaseService<T> {

	public Serializable insertObject(Object object) {
		return this.getBaseDao().insertObject(object);
	}

	public Object update(int jbh, double rcyl, String strDate) {
		String hql = " update  Outputwellproduction o set o.rcyl=" + rcyl + " , o.cjsj=to_date('" + strDate + "','yyyy-MM-dd HH24:MI:ss') where  jbh = " + jbh + "";
		return this.getBaseDao().modifyByObject(hql);
	}

	public Serializable deleteObject(String obj) {
		String hql = " delete from t_outputwellproduction where jlbh in (" + obj + ")";
		return this.getBaseDao().deleteObject(hql);
	}

	public void saveOrUpdateorDeleteProOutputwellproduction(Outputwellproduction w, String ids, String comandType) throws Exception {
		getBaseDao().saveOrUpdateorDeleteProOutputwellPro(w, ids, comandType);
	}

	public List<T> loadWellInformationID(Class<T> clazz) {
		String queryString = "SELECT u.jlbh,u.jh FROM WellInformation u order by u.jlbh ";
		return getBaseDao().getObjects(queryString);
	}

	@SuppressWarnings("null")
	public String getOutputwellproductionList(Map<String, Object> map) {
		String jc = (String) map.get("jc");
		String jhh = (String) map.get("jhh");
		String jh = (String) map.get("jh");
		String str_jc = "";
		String str_jhh = "";
		String str_jh = "";
		if (jc != null || !(jc.equals(""))) {
			str_jc = " and w.jc like  '%" + jc + "%' ";
		}
		if (jhh != null || !(jhh.equals(""))) {
			str_jhh = " and w.jhh like  '%" + jhh + "%' ";
		}
		if (jh != null || !(jh.equals(""))) {
			str_jh = " and w.jh  like  '%" + jh + "%' ";
		}
		String outsql = " SELECT  o.jlbh ,w.jh,o.rcyl,o.cjsj  FROM t_outputwellproduction o, t_wellinformation w where 1 = 1 " + " AND o.jbh = w.jlbh " + str_jc + " " + str_jhh + " " + str_jh
				+ " order by o.jlbh ASC ";

		String inhql = " SELECT w.jh  FROM t_outputwellproduction o , t_wellinformation w where 1 = 1 " + " AND o.jbh = w.jlbh  " + str_jc + " " + str_jhh + " " + str_jh
				+ "  order by o.jlbh ASC ";
		List<Outputwellproduction> output = null;
		List<WellInformation> infor = null;
		List<Outputwellproduction> myOutput = null;
		int total = this.getBaseDao().getCountRows(outsql);
		Outputwellproduction out = null;
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String json = "";
		Gson g = new Gson();
		try {
			output = this.getBaseDao().getListPage((Integer) map.get("offset"), (Integer) map.get(PagingConstants.PAGE_SIZE), outsql);
			infor = this.getBaseDao().getListPage((Integer) map.get("offset"), (Integer) map.get(PagingConstants.PAGE_SIZE), inhql);
			for (int i = 0; i < infor.size(); i++) {
				out = new Outputwellproduction();
				// out.setJh(infor.get(i).getJh());
				out.setJbh(output.get(i).getJbh());
				out.setRcyl(output.get(i).getRcyl());
				out.setCjsj(output.get(i).getCjsj());
				myOutput.add(out);
			}
			jsonMap.put(PagingConstants.TOTAL, total);
			jsonMap.put(PagingConstants.LIST, myOutput);
			json = g.toJson(jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public String getOutputwellproductionProList(Map<String, Object> map) throws SQLException {
		// String entity = (String) map.get("entity");
		String outputwellproductionName = (String) map.get("outputwellproductionName");
		String outputwellproduction_Str = "";
		if (outputwellproductionName != null) {
			outputwellproduction_Str = " and jh like '%" + outputwellproductionName + "%'";
		}
		String sql = "select * from (select t.jlbh,t.jbh,t.rcyl ,cjsj ,w.jh  ," + " row_number() over(order by t.jlbh desc) rk from t_outputwellproduction t ,"
				+ "t_wellinformation w where 1=1 and t.jbh=w.jlbh) where 1=1   and  rk<=" + ((Integer) map.get(PagingConstants.PAGE_SIZE) * (Integer) map.get(PagingConstants.PAGE_NO))
				+ " and rk >= " + (Integer) map.get(PagingConstants.OFFSET) + outputwellproduction_Str + "  order by jlbh asc";
		String all = "SELECT t.jlbh,jbh ,rcyl ,cjsj FROM t_outputwellproduction t ,t_wellinformation w where 1=1 and t.jbh=w.jlbh " + outputwellproduction_Str + "  order by jlbh asc";
		Gson g = new Gson();
		List<Outputwellproduction> list = null;
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String json = "";
		int totals = this.getBaseDao().queryProObjectTotals("select count(*) as nums from (" + all + ")");
		try {
			list = this.getBaseDao().queryOutputwellproductionDatas(sql);
			jsonMap.put(PagingConstants.TOTAL, totals);
			jsonMap.put(PagingConstants.LIST, list);
			json = g.toJson(jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public String querySingleWellParam(String jc, String jhh, String jh, String type) throws Exception {
		StringBuffer result_json = new StringBuffer();

		String sql = "";
		if (type.equalsIgnoreCase("jc")) {
			sql = "  select  distinct (w.jc) as jc,w.jc as dm from t_wellinformation w,t_outputwellproduction o where o.jbh=w.jlbh ";
		} else if (type.equalsIgnoreCase("jhh")) {
			sql = " select distinct (w.jhh) as jhh, w.jhh as dm from t_wellinformation w ,t_outputwellproduction o where 1=1 and o.jbh=w.jlbh";
		} else if (type.equalsIgnoreCase("jh")) {
			sql = " select distinct w.jh as jh ,w.jh as dm from t_wellinformation w,t_outputwellproduction o where 1=1 and o.jbh=w.jlbh";
		}
		if (StringUtils.isNotBlank(jc)) {
			sql += " and w.jc like '%" + jc + "%'";
		}
		if (StringUtils.isNotBlank(jhh)) {
			sql += " and w.jhh like '%" + jhh + "%'";
		}
		if (StringUtils.isNotBlank(jh)) {
			sql += " and w.jh like '%" + jh + "%'";
		}

		if (type.equalsIgnoreCase("jc")) {
			sql += " order by w.jc desc";
		} else if (type.equalsIgnoreCase("jhh")) {
			sql += " order by w.jhh";
		} else if (type.equalsIgnoreCase("jh")) {
			sql += " order by w.jh";
		}
		try {
			List<?> list = this.findCallSql(sql);
			result_json.append("[");
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
			result_json.append("]");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_json.toString();
	}

	public List<T> fingWellByJCList() throws Exception {
		String sql = " select  distinct (w.jc) from t_wellinformation w,t_outputwellproduction o where o.jbh=w.jlbh order by w.jc ";
		return this.getBaseDao().getfindByIdList(sql);
	}

	public List<T> fingWellByJhhList(String jc) throws Exception {
		String sql = " select distinct (w.jhh) from t_wellinformation w ,t_outputwellproduction o where 1=1 and o.jbh=w.jlbh ";
		if (jc != null && !jc.equals("")) {
			sql += "  and w.jc like '%" + jc + "%' ";
		}
		String hql = sql + " order by w.jhh ";
		return this.getBaseDao().getfindByIdList(hql);
	}

	public List<T> fingWellByJhList(String jc, String jhh) throws Exception {
		String sql = " select o.jlbh,w.jh from t_wellinformation w,t_outputwellproduction o where 1=1 and o.jbh=w.jlbh ";
		if (jc != null && !jc.equals("")) {
			sql += "  and w.jc like '%" + jc + "%' ";
		}
		if (jhh != null && !jhh.equals("")) {
			sql += "  and w.jhh like '%" + jhh + "%' ";
		}
		String hql = sql + " order by w.jh ";
		return this.getBaseDao().getfindByIdList(hql);
	}

	public List<T> findAllList(int offset, int pageSize, String jc, String jhh, String jh) throws Exception {
		String tempsql = " select o.jlbh,o.jbh,w.jc,w.jhh,w.jh,o.rcyl,to_char(o.cjsj,'YYYY-MM-DD hh24:mi:ss') as cjsj  from t_wellinformation w,t_outputwellproduction o where 1=1 and o.jbh=w.jlbh ";
		if (jc != null && !jc.equals("") && jc.trim().length() > 0) {
			tempsql += " and w.jc like '%" + jc + "%' ";
		}
		if (jhh != null && !jhh.equals("") && jhh.trim().length() > 0) {
			tempsql += " and w.jhh like '%" + jhh + "%' ";
		}
		if (jh != null && !jh.equals("") && jh.trim().length() > 0) {
			tempsql += " and w.jh like '%" + jh + "%' ";
		}
		tempsql += " order by w.jh";
		return this.getBaseDao().getSQLListForPage(offset, pageSize, tempsql);
	}

	public int rowCount(String jc, String jhh, String jh) {
		String tempsql = " select * from ( ";
		tempsql += " select o.jlbh,o.jbh,w.jc,w.jhh,w.jh,o.rcyl,o.cjsj from t_wellinformation w,t_outputwellproduction o where 1=1 and o.jbh=w.jlbh ";
		if (jc != null && !jc.equals("") && jc.trim().length() > 0) {
			tempsql += " and w.jc like '%" + jc + "%' ";
		}
		if (jhh != null && !jhh.equals("") && jhh.trim().length() > 0) {
			tempsql += " and w.jhh like '%" + jhh + "%' ";
		}
		if (jh != null && !jh.equals("") && jh.trim().length() > 0) {
			tempsql += " and w.jh like '%" + jh + "%' ";
		}
		String hql = tempsql + "  ) ";
		return this.getBaseDao().getCountSQLRows("select count(*) from (" + hql + ")");
	}

}
