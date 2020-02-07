package com.gao.service.back;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gao.model.gridmodel.PumpGridPanelData;
import com.gao.service.base.BaseService;
import com.gao.service.base.CommonDataService;
import com.gao.utils.Page;
import com.gao.utils.StringManagerUtils;

import net.sf.json.JSONObject;

@Service("PumpingunitService")
public class PumpingunitService<T> extends BaseService<T> {
	@Autowired
	private CommonDataService commonDataService;
	public void insertObject(Object object) {
		getBaseDao().addObject(object);
	}

	public void update(Object obj) {
		getBaseDao().saveOrUpdateObject(obj);
	}

	public void deleteObject(String obj) throws Exception {
		String hql = " delete from Pumpingunit where jlbh in(" + obj + ") ";
		getBaseDao().bulkObjectDelete(hql);
	}

	public String queryPumpUnitParams(String sccj, String cyjxh, String type)
			throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		if (type.equalsIgnoreCase("sccj")) {
			sql = " select distinct(t.manufacturer) as sccj ,t.manufacturer as dm  from t_pumpingunit t  where 1= 1  ";
		} else if (type.equalsIgnoreCase("cyjxh")) {
			sql = " select t.model as sccj ,t.model as dm  from t_pumpingunit t  where 1= 1";
		}
		if (StringManagerUtils.isNotNull(sccj)) {
			sql += " and t.manufacturer like '%" + sccj + "%'";
		}
		if (StringManagerUtils.isNotNull(cyjxh)) {
			sql += " and t.model like '%" + cyjxh + "%'";
		}

		if (type.equalsIgnoreCase("sccj")) {
			sql += " order by t.manufacturer ";
		} else if (type.equalsIgnoreCase("cyjxh")) {
			sql += " order by t.manufacturer,t.model";
		}
		try {
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(sql);
			result_json.append("{\"totals\":"+totals+",\"list\":[{boxkey:\"\",boxval:\"选择全部\"},");
			String get_key = "";
			String get_val = "";
			for (Object o : list) {
				Object[] obj = (Object[]) o;
				get_key = obj[0] + "";
				get_val = (String) obj[1];
				result_json.append("{boxkey:\"" + get_key + "\",");
				result_json.append("boxval:\"" + get_val + "\"},");
			}
			result_json.deleteCharAt(result_json.length() - 1);
			result_json.append("]}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_json.toString();
	}

	public List<T> findByLiIdst(String sccj) throws Exception {
		String sql = " select  sccj,cyjxh from t_pumpingunit t where 1=1 ";
		if (sccj != null && !sccj.equals("")) {
			sql += "  and sccj like '%" + sccj + "%' ";
		}
		return this.getBaseDao().getfindByIdList(sql);
	}

	public List<T> findBysccjList() throws Exception {
		String sql = " select  distinct( sccj) from t_pumpingunit t  order by t.sccj ";
		return this.getBaseDao().getfindByIdList(sql);
	}

	public List<T> findBycyjxhList(String sccj) throws Exception {
		String sql = " select jlbh,cyjxh from t_pumpingunit t where 1= 1 ";
		if (sccj != null && !sccj.equals("")) {
			sql += " and sccj like '" + sccj + "' ";
		}
		sql += "order by t.cyjxh";
		return this.getBaseDao().getfindByIdList(sql);
	}

	public String findAllLIst(int offset, int pageSize, String sccj,String cyjxh,Page pager) throws Exception {
		String sql="select t.jlbh as id,t.manufacturer as sccj,t.model as cyjxh,t.ratedpolishedrodload as xdedzh,t.gearreducerratedtorque as jsqednj,t.singlecrankweight as dkqbzl,"
				+ " t.crankgravityradius as qbzxbj,t.structuralunbalance as jgbphz,t.singlebalanceweight as dkphkzl,t.offsetangleofcrank as qbpzj,"
				+ " c1.itemname as xzfxmc,t.balancemaxmovespace as zdtzjl,t.maxcnt as bpphks,c2.itemname as cyjlxmc,"
				+ " t.beltefficiency as pdxl,t.gearreducerefficiency as jsxxl,t.fourbarlinkageefficiency as slgxl,t.motorefficiency as djxl "
				+ " from t_pumpingunit t,t_code c1,t_code c2 "
				+ " where c1.itemcode='XZFX' and t.crankrotationdirection=c1.itemvalue and c2.itemcode='CYJLX' and t.type=c2.itemvalue";
		if (StringManagerUtils.isNotNull(sccj)) {
			sql+= " and t.manufacturer like '%" + sccj + "%'";
		}
		if (StringManagerUtils.isNotNull(cyjxh)) {
			sql+=" and t.model like '%" + cyjxh + "%' ";
		}
		sql+=" order by t.manufacturer,t.model";
		String columns=commonDataService.showTableHeadersColumns("pumpingUnit");
		return this.findPageBySqlEntity(sql, columns, pager);
	}

	public int rowCount(String sccj, String cyjxh) {
		String tempHql = " select * from ( ";
		tempHql += " select * from t_pumpingunit p where 1= 1  ";
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

	public void savePumpingUnitEditerGridData(JSONObject jsonObject) throws SQLException {
		this.getBaseDao().savePumpingUnitEditerGridData(jsonObject);
	}
}
