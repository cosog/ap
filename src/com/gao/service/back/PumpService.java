package com.gao.service.back;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gao.model.Pump;
import com.gao.model.gridmodel.PumpGridPanelData;
import com.gao.service.base.BaseService;
import com.gao.service.base.CommonDataService;
import com.gao.utils.Page;

/**<p>描述：泵处理服务Service</p>
 * 
 * @author gao 20140-06-10
 * @param <T>
 * @version 1.0
 */
@Service("pumpService")
public class PumpService<T> extends BaseService<T> {
	@Autowired
	private CommonDataService commonDataService;
	public void insertObject(Object object) {
		this.addObject(object);
	}
	
	public void saveOrUpdateorDeletePump(Pump p, String ids, String comandType) throws SQLException {
		this.getBaseDao().saveOrUpdateorDeletePump(p, ids, comandType);
	}
	public void savePumpEditerGridData(PumpGridPanelData p, String ids, String comandType) throws SQLException {
		this.getBaseDao().savePumpEditerGridData(p, ids, comandType);
	}

	public Serializable deleteObject(int obj) {
		String hql = " delete from t_pump where jlbh=" + obj + " ";
		return this.getBaseDao().deleteObject(hql);
	}

	public Object update(Pump pump) {
		String hql = " update t_pump set sccj='" + pump.getSccj()
				+ "' ,cybxh='" + pump.getCybxh() + "', blx='" + pump.getBlx()
				+ "', bjb=" + pump.getBjb() + ", bj=" + pump.getBj() + ", zsc="
				+ pump.getZsc() + " where jlbh=" + pump.getJlbh() + " ";
		return this.getBaseDao().updateWellorder(hql);
	}

	public List<T> findBycybxh(String sccj) throws Exception {
		String sql = " select  distinct(cybxh) from t_pump t where 1=1 ";
		if (sccj != null && !sccj.equals("")) {
			sql += "  and sccj like '%" + sccj + "%' ";
		}
		sql += "order by t.cybxh";
		return this.getBaseDao().getfindByIdList(sql);
	}

	public String queryPumpParams(Page pager,String sccj, String cybxh,String type) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer sqlCuswhere = new StringBuffer();
		String sql = "";
		if (type.equalsIgnoreCase("sccj")) {
			sql = " select  distinct t.sccj as sccj ,t.sccj as dm from t_pump t where t.sccj is not null  ";
		} else if (type.equalsIgnoreCase("cybxh")) {
			sql = " select distinct t.cybxh as cybxh,t.cybxh as dm from t_pump t where t.cybxh is not null ";
		} 
		if (StringUtils.isNotBlank(sccj)) {
			sql += " and t.sccj like '%" + sccj + "%'";
		}
		if (StringUtils.isNotBlank(cybxh)) {
			sql += " and t.cybxh like '%" + cybxh + "%'";
		}
		

		if (type.equalsIgnoreCase("sccj")) {
			sql += " order by t.sccj desc";
		} else if (type.equalsIgnoreCase("cybxh")) {
			sql += " order by t.cybxh";
		} 
		sqlCuswhere.append("select * from   ( select a.*,rownum as rn from (");
		sqlCuswhere.append(""+sql);
		int maxvalue=pager.getLimit()+pager.getStart();
		sqlCuswhere.append(" ) a where  rownum <="+maxvalue+") b");
		sqlCuswhere.append(" where rn >"+pager.getStart());
		String finalsql=sqlCuswhere.toString();
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
	
	public List<T> findBySCCJ() throws Exception {
		String sql = " select  distinct(sccj) from t_pump t order by t.sccj ";
		return this.getBaseDao().getfindByIdList(sql);
	}

	public String loadProduceDataOutWell(String sccj,String cybxh, String type)
			throws Exception {
		StringBuffer result_json = new StringBuffer();

		String sql = "";
		if (type.equalsIgnoreCase("sccj")) {
			sql = " select distinct v.sccj as sccj from Pump v where 1=1";
		} else if (type.equalsIgnoreCase("cybxh")) {
			sql = " select distinct v.cybxh as cybxh from Pump v where 1=1";
		}
		if (StringUtils.isNotBlank(sccj)) {
			sql += " and v.sccj like '%" + sccj + "%'";
		}
		if (StringUtils.isNotBlank(cybxh)) {
			sql += " and v.cybxh like '%" + cybxh + "%'";
		}

		try {
			List<?> list = this.getBaseDao().getObjects(sql);
			result_json.append("[");
			String get_key = "";
			String get_val = "";
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					get_key = (String) list.get(i);
					get_val = (String) list.get(i);
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

	public String findAllLIst(int offset, int pageSize, String sccj,String cybxh,Page pager) throws Exception {
		StringBuffer sqlBuffer=new StringBuffer();
		sqlBuffer.append("select u.jlbh,sccj,cybxh,blx,bjb,bj,zsc,c1.itemname as blxName ,c2.itemname as bjbName,c3.itemname as btlxName");
		sqlBuffer.append("  from t_pump u,t_code c1,t_code c2,t_code c3"
				+ " where c1.itemcode='BLX' and c1.itemvalue=u.blx and c2.itemcode='BJB' and c2.itemvalue=u.bjb and c3.itemcode='BTLX' and c3.itemvalue=u.btlx");
		if (sccj != null && !sccj.equals("")) {
			sqlBuffer.append( " and u.sccj like '%" + sccj + "%'  ");
		}
		if (cybxh != null && !cybxh.equals("") && cybxh.trim().length() > 0) {
			sqlBuffer.append( "  and  u.cybxh like '%" + cybxh + "%' ");
		}
		sqlBuffer.append(" order by sccj,cybxh");
		String hql = sqlBuffer.toString();
		String columns=commonDataService.showTableHeadersColumns("pump");
		return this.findPageBySqlEntity(hql, columns, pager);
	}

	public int rowCount(String sccj, String cybxh) {
		String tempHql = " select * from ( ";
		tempHql += " select * from t_pump p where 1= 1  ";
		if (sccj != null && !sccj.equals("") && sccj.trim().length() > 0) {
			tempHql += " and p.sccj like '%" + sccj + "%'  ";
		}
		if (cybxh != null && !cybxh.equals("") && cybxh.trim().length() > 0) {
			tempHql += "  and  p.cybxh like '%" + cybxh + "%' ";
		}
		String hql = tempHql + "  ) ";
		return this.getBaseDao().getCountSQLRows(
				"select count(*) as nums from (" + hql + ")");
	}

}
