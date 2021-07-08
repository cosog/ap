package com.cosog.service.back;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.cosog.service.base.BaseService;

@Service("strokeService")
public class StrokeService<T> extends BaseService<T> {

	public Serializable insertObject(Object object) {
		return this.getBaseDao().insertObject(object);
	}

	public Object update(Object obj) {
		return this.getBaseDao().modifyObject(obj);
	}

	public Serializable deleteObject(String obj) {
		String hql = " delete from T_STROKE where jlbh=" + obj + " ";
		return this.getBaseDao().deleteObject(hql);
	}

	public List<T> findBysccj() throws Exception {
		String sql = " select distinct(p.sccj) from  T_STROKE s ,t_pumpingunit p  where 1=1  and s.cyjbh=p.jlbh  ";
		return this.getBaseDao().find(sql);
	}
	public String queryStrokeParams(String sccj, String cyjxh,String type) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		if (type.equalsIgnoreCase("sccj")) {
			sql = " select distinct p.sccj as sccj,sccj as dm from  T_STROKE s ,t_pumpingunit p  where 1=1  and s.cyjbh=p.jlbh  ";
		} else if (type.equalsIgnoreCase("cyjxh")) {
			sql = " select distinct p.cyjxh,p.cyjxh as dm from  T_STROKE s ,t_pumpingunit p  where 1=1  and s.cyjbh=p.jlbh ";
		} 
		if (StringUtils.isNotBlank(sccj)) {
			sql += " and p.sccj like '%" + sccj + "%'";
		}
		if (StringUtils.isNotBlank(cyjxh)) {
			sql += " and p.cyjxh like '%" + cyjxh + "%'";
		}
		

		if (type.equalsIgnoreCase("sccj")) {
			sql += " order by p.sccj desc";
		} else if (type.equalsIgnoreCase("cyjxh")) {
			sql += " order by p.cyjxh";
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
	
	public String queryStrokeWinParams(String sccj, String cyjxh,String type) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		if (type.equalsIgnoreCase("sccj")) {
			sql = " select distinct p.sccj as sccj,sccj as dm from  t_pumpingunit p  where 1=1  ";
		} else if (type.equalsIgnoreCase("cyjxh")) {
			sql = " select distinct p.cyjxh,p.cyjxh as dm from  t_pumpingunit p  where 1=1 ";
		} 
		if (StringUtils.isNotBlank(sccj)) {
			sql += " and p.sccj like '%" + sccj + "%'";
		}
		if (StringUtils.isNotBlank(cyjxh)) {
			sql += " and p.cyjxh like '%" + cyjxh + "%'";
		}
		

		if (type.equalsIgnoreCase("sccj")) {
			sql += " order by p.sccj desc";
		} else if (type.equalsIgnoreCase("cyjxh")) {
			sql += " order by p.cyjxh";
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
	
	public List<T> findByLiIdst(String sccj) throws Exception {
		String sql = " select p.sccj,p.cyjxh,s.cyjbh from  T_STROKE s ,t_pumpingunit p  where 1=1  and s.cyjbh=p.jlbh  ";
		if (sccj != null && !sccj.equals("")) {
			sql += " and p.sccj like '" + sccj + "'";
		}
		sql += " order by p.cyjxh  ";
		return this.getBaseDao().find(sql);
	}

	public List<T> findAllLIst(int offset, int pageSize, String sccj,
			String cyjxh) throws Exception {
		String tempHql = " select s.jlbh,s.cyjbh ,p.sccj,p.cyjxh,s.cc,s.qbkj ,s.wzjnjys  from  T_STROKE s ,t_pumpingunit p  where 1=1  and s.cyjbh=p.jlbh  ";
		if (sccj != null && !sccj.equals("")) {
			tempHql += " and p.sccj like '%" + sccj + "%'  ";
		}
		if (cyjxh != null && !cyjxh.equals("")) {
			tempHql += "  and  p.cyjxh like '%" + cyjxh + "%' ";
		}
		String hql = tempHql;
		return this.getBaseDao().getSQLListForPage(offset, pageSize, hql);
	}

	public int rowCount(String sccj, String cyjxh) {
		String tempHql = " select * from ( ";
		tempHql += " select s.jlbh,s.cyjbh ,p.sccj,p.cyjxh,s.cc,s.qbkj,s.wzjnjys from  T_STROKE s ,t_pumpingunit p  where 1=1  and s.cyjbh=p.jlbh  ";
		if (sccj != null && !sccj.equals("") && sccj.trim().length() > 0) {
			tempHql += " and p.sccj like '%" + sccj + "%'  ";
		}
		if (cyjxh != null && !cyjxh.equals("") && cyjxh.trim().length() > 0) {
			tempHql += "  and  p.cyjxh like '%" + cyjxh + "%' ";
		}
		String hql = tempHql + "  ) ";
		return this.getBaseDao().getCountSQLRows(
				"select count(*) as nums from (" + hql + ")");
	}

}
