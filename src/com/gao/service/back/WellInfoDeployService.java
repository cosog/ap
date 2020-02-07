package com.gao.service.back;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gao.service.base.BaseService;

/***
 * <p>单井配置信息</p>
 * @author ding 2014-06-06
 * @version 1.0
 */
@Service("wellInfoDeployService")
public class WellInfoDeployService<T> extends BaseService<T> {
	private Integer jbh;

	/**
	 * <p>删除数据</p>
	 * @param jlbh ID
	 * @return
	 */
	public Serializable deleteObject(String jlbh) {
		String sql="";
		String[] ids = jlbh.split(",");
		for (int i = 0; i < ids.length; i++) {
			sql = " delete from T_CBM_002_WELLINFO	where jlbh in(" + ids[i] + ") ";
			this.getBaseDao().updateObject(sql);
		}
		return 0;
	}
	
	

	public Integer getTotalRows(String gqmc, String jqzmc, String jh) {
		String sqltemp = " select * from (  ";
		sqltemp += " select * from V_CBM_001_WELLDEPLOYINFO  w where 1=1 ";
		if (gqmc.trim().length() > 0 && gqmc != null && !gqmc.equals("")) {
			sqltemp += " and w.gqmc = '" + gqmc + "' ";
		}
		if (jqzmc.trim().length() > 0 && jqzmc != null && !jqzmc.equals("")) {
			sqltemp += " and w.jqzmc = '" + jqzmc + "' ";
		}
		if (jh.trim().length() > 0 && jh != null && !jh.equals("")) {
			sqltemp += " and w.jh = '" + jh + "' ";
		}
		sqltemp += " ) ";
		String sql = sqltemp;
		return this.getBaseDao().getCountRows(sql);
	}
	
	/**
	 * <p>获取页面显示的数据</p>
	 * @param offset  最小页
	 * @param pageSize  最大页
	 * @param gqmc 工区名称
	 * @param jqzmc 集气站名称
	 * @param jh   井名
	 * @return
	 * @throws Exception
	 */
	public List<T> findPageListShow(int offset, int pageSize, String gqmc,
			String jqzmc, String jh) throws Exception {
		String sqltemp = " select w FROM WellInfoDeploy as w where 1=1";
		if (gqmc.trim().length() > 0 && gqmc != null && !gqmc.equals("")) {
			sqltemp += " and w.gqmc = '" + gqmc + "' ";
		}
		if (jqzmc.trim().length() > 0 && jqzmc != null && !jqzmc.equals("")) {
			sqltemp += " and w.jqzmc = '" + jqzmc + "' ";
		}
		if (jh.trim().length() > 0 && jh != null && !jh.equals("")) {
			sqltemp += " and w.jh = '" + jh + "' ";
		}
		String sql = sqltemp;
		return getBaseDao().getListForPage(offset, pageSize, sql);
	}
	

	public List<T> findByJhList(String gqmc, String jqzmc) throws Exception {
		String tempsql = " select distinct( t.jh) as jh from V_CBM_000_ORG t  where 1=1  ";
		if (gqmc != null && !gqmc.equals("") && gqmc.trim().length() > 0) {
			tempsql += " and t.gqmc = '" + gqmc + "'";
		};
		if (jqzmc != null && !jqzmc.equals("") && jqzmc.trim().length() > 0) {
			tempsql += " and t.jqzmc = '" + jqzmc + "'";
		};
		String sql = tempsql + " order by jh ";
		return getBaseDao().getfindByIdList(sql);
	}

	public List<T> findByWell_JqzmcList(String gqmc) throws Exception {
		String tempsql = " select distinct( t.jqzmc) as jqzmc from V_CBM_000_ORG t where 1=1 ";
		if (gqmc != null && !gqmc.equals("") && gqmc.trim().length() > 0) {
			tempsql += " and t.gqmc = '" + gqmc + "'";
		};
		String sql = tempsql+ " order by jqzmc ";
		return getBaseDao().getfindByIdList(sql);
	}

	public List<T> findByWell_GqmcList() throws Exception {
		String sql = " select distinct( t.gqmc) as gqmc from V_CBM_000_ORG t order by gqmc  ";
		return getBaseDao().getfindByIdList(sql);
	}

	public List<T> findByWell_JqzmcAllList(String gqmc) throws Exception {
		String tempsql = " select distinct(jqzmc) as jqzmc FROM V_CBM_000_ORG  where 1=1 ";
		if (gqmc.trim().length() > 0 && gqmc != null) {
			tempsql += " and gqmc like '%" + gqmc + "%'";
		}
		String sql = tempsql+" order by jqzmc ";
		return getBaseDao().getfindByIdList(sql);
	}

	public List<T> findByWell_JHAllList(String gqmc, String jqzmc)
			throws Exception {
		String tempsql = " select distinct(jh) as jh FROM V_CBM_000_ORG  where 1=1 ";
		if (gqmc.trim().length() > 0 && gqmc != null) {
			tempsql += " and gqmc = '" + gqmc + "'";
		}
		if (jqzmc.trim().length() > 0 && jqzmc != null) {
			tempsql += " and jqzmc = '" + jqzmc + "'";
		}
		String sql = tempsql +" order by jh ";
		return getBaseDao().getfindByIdList(sql);
	}

	
	

}
