package com.gao.service.back;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gao.model.ReservoirProperty;
import com.gao.model.WellInformation;
import com.gao.model.gridmodel.ResProHandsontableChangedData;
import com.gao.model.gridmodel.ReservoirPropertyGridPanelData;
import com.gao.service.base.BaseService;
import com.gao.service.base.CommonDataService;
import com.gao.utils.Page;
import com.gao.utils.PagingConstants;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;

/**
 * <p>
 * 描述：油气藏物性数据Service类
 * </p>
 * 
 * @author gao 2014-06-10
 * @param <T>
 * @version 1.0
 */
@Service("reservoirPropertyManagerService")
@SuppressWarnings("rawtypes")
public class ReservoirPropertyManagerService<T> extends BaseService<T> {
	@Autowired
	private CommonDataService service;
	public List<T> loadReservoirPropertys(Class<T> clazz) {
		String queryString = "SELECT u FROM ReservoirProperty u order by u.id ";
		return getBaseDao().getObjects(queryString);
	}

	public List<T> queryReservoirPropertys(Class<T> clazz, String ReservoirPropertyName) {
		if (ReservoirPropertyName == null || "".equals(ReservoirPropertyName))
			return loadReservoirPropertys(clazz);

		String queryString = "SELECT u FROM ReservoirProperty u WHERE u.ReservoirPropertyName like '%" + ReservoirPropertyName + "%' order by u.id asc";
		return getBaseDao().getObjects(queryString);
	}

	public String getReservoirPropertyList(Map map) {
		// String entity = (String) map.get("entity");
		String reservoirPropertyName = (String) map.get("reservoirPropertyName");
		String ReservoirProperty_Str = "";
		if (reservoirPropertyName != null || !("".equals(reservoirPropertyName))) {
			ReservoirProperty_Str = " and u.yqcbh like '%" + reservoirPropertyName + "%'";
		}
		String hql = "SELECT u FROM ReservoirProperty u where 1=1 " + ReservoirProperty_Str + " order by u.id asc";
		Gson g = new Gson();
		List<ReservoirProperty> list = null;
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String json = "";
		int total = this.getBaseDao().getRecordCountRows(hql);
		try {
			list = this.getBaseDao().getListForPage((Integer) map.get("offset"), (Integer) map.get(PagingConstants.PAGE_SIZE), hql);
			jsonMap.put(PagingConstants.TOTAL, total);
			jsonMap.put(PagingConstants.LIST, list);
			json = g.toJson(jsonMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public String getReservoirPropertyProList(Map map,int recordCount) throws SQLException {
		// String entity = (String) map.get("entity");
		String reservoirPropertyName = (String) map.get("reservoirPropertyName");
		//String orgId = (String) map.get("orgId");
		String ReservoirProperty_Str = "";
		if (StringManagerUtils.isNotNull(reservoirPropertyName)) {
			ReservoirProperty_Str = " and resname like '%" + reservoirPropertyName + "%'";
		}
		//String sql = "select * from (select JLBH,YQCBH,YYMD,SMD,TRQXDMD,YSRJQYB,BHYL,DMTQYYND,YQCYL,YQCZBSD,YQCZBWD,res_name ," + "row_number() over(order by u.jlbh desc) rk from t_reservoirproperty u,sc_res res where res.res_code=u.YQCBH )  where 1=1 and  rk<=" + ((Integer) map.get(PagingConstants.PAGE_SIZE) * (Integer) map.get(PagingConstants.PAGE_NO)) + " and rk >= " + (Integer) map.get(PagingConstants.OFFSET) + ReservoirProperty_Str + "  order by res_name asc";
		//String all = "SELECT JLBH,YQCBH,YYMD,SMD,TRQXDMD,YSRJQYB,BHYL,DMTQYYND,YQCYL,YQCZBSD,YQCZBWD FROM t_reservoirproperty u ,sc_res res where res.res_code=u.yqcbh" + ReservoirProperty_Str + "  order by u.jlbh asc";
		String sql = "select * from (select JLBH as id,YYMD,SMD,TRQXDMD,BHYL,YQCYL,YQCZBSD,YQCZBWD,resname as resName ,row_number() over(order by res.jlbh ) rk "
					+ " from t_reservoirproperty  res order by res.jlbh asc)  "
					+ " where 1=1 "
					+ " and  rk<=" + ((Integer) map.get(PagingConstants.PAGE_SIZE) * (Integer) map.get(PagingConstants.PAGE_NO)) + " "
					+ " and rk >= " + (Integer) map.get(PagingConstants.OFFSET) + ReservoirProperty_Str ;
		String all = "SELECT count(*) FROM t_reservoirproperty u where 1=1" + ReservoirProperty_Str ;
		
		Gson g = new Gson();
		List<ReservoirProperty> list = null;
	//	Map<String, Object> jsonMap = new HashMap<String, Object>();
		String json = "";
		int totals = this.getBaseDao().queryProObjectTotals(all);
		try {
			list = this.getBaseDao().queryProReservoirPropertyDatas(sql);
			String columns=service.showTableHeadersColumns("reservoirProperty");
			StringBuffer strBuf = new StringBuffer();
			strBuf.append("{success:true,");
			strBuf.append("totalCount:"+totals+",");
			strBuf.append("columns:" + columns + ",");
			String data = g.toJson(list);
			strBuf.append("totalRoot:" + data );
			
			strBuf.deleteCharAt(strBuf.length()-1);
			if(list.size()>0){
				strBuf.append(",");
			}
			for(int i=1;i<=recordCount-totals;i++){
				strBuf.append("{\"jlbh\":\"-9999\"},");
			}
			if(list.size()>0||totals<recordCount){
				strBuf.deleteCharAt(strBuf.length()-1);
			}
			strBuf.append("]}");
			json=strBuf.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public String exportReservoirPropertyData(Map map) throws SQLException {
		String reservoirPropertyName = (String) map.get("reservoirPropertyName");
		String ReservoirProperty_Str = "";
		if (StringManagerUtils.isNotNull(reservoirPropertyName)) {
			ReservoirProperty_Str = " and resname like '%" + reservoirPropertyName + "%'";
		}
		String sql = "select JLBH as id,YYMD,SMD,TRQXDMD,BHYL,YQCYL,YQCZBSD,YQCZBWD,resname ,row_number() over(order by res.jlbh ) rk "
					+ " from t_reservoirproperty  res  where 1=1 "+ReservoirProperty_Str+" order by res.jlbh asc ";
		
		Gson g = new Gson();
		List<ReservoirProperty> list = null;
		String data = "[]";
		try {
			list = this.getBaseDao().queryProReservoirPropertyDatas(sql);
			data = g.toJson(list);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	public String getReservoirListData(Page pager,String reservoirPropertyName) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		StringBuffer sqlCuswhere = new StringBuffer();
		String sql = "select t.resname,t.resname as dm from t_reservoirproperty t where 1=1";
		
		if (StringUtils.isNotBlank(reservoirPropertyName)) {
			sql += " and t.resname like '%" + reservoirPropertyName + "%'";
		}
		sql+=" order by t.jlbh";
		sqlCuswhere.append("select * from   ( select a.*,rownum as rn from (");
		sqlCuswhere.append(""+sql);
		int maxvalue=pager.getLimit()+pager.getStart();
		sqlCuswhere.append(" ) a where  rownum <="+maxvalue+") b");
		sqlCuswhere.append(" where rn >"+pager.getStart());
		String finalsql=sqlCuswhere.toString();
		try {
			int totals=this.getTotalCountRows(sql)+1;
			List<?> list = this.findCallSql(finalsql);
			result_json.append("{\"totals\":"+totals+",\"list\":[{boxkey:\"\",boxval:\"选择全部\"}");
			String get_key = "";
			String get_val = "";
			if (null != list && list.size() > 0) {
				for (Object o : list) {
					Object[] obj = (Object[]) o;
					get_key = obj[0] + "";
					get_val = (String) obj[1];
					result_json.append(",{boxkey:\"" + get_key + "\",");
					result_json.append("boxval:\"" + get_val + "\"}");
				}
			}
			result_json.append("]}");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_json.toString();
	}

	public void addReservoirProperty(T ReservoirProperty) throws Exception {
		getBaseDao().addObject(ReservoirProperty);
	}

	public void addProReservoirProperty(ReservoirProperty o) throws Exception {
		getBaseDao().addProReservoirProperty(o);
	}

	/**
	 * <p>
	 * 描述：区块物性数据信息的新增、修改的操作
	 * </p>
	 * 
	 * @param o
	 * @param ids
	 * @param comandType
	 * @throws Exception
	 */
	public void saveOrUpdateorDeleteProReservoirProperty(ReservoirProperty o, String ids, String comandType) throws Exception {
		getBaseDao().saveOrUpdateorDeleteProReservoirProperty(o, ids, comandType);
	}
	
	public void saveReservoirPropertyGridData(ReservoirPropertyGridPanelData o, String ids, String comandType) throws Exception {
		getBaseDao().saveReservoirPropertyGridData(o, ids, comandType);
	}
	
	public void saveReservoirPropertyGridData(ResProHandsontableChangedData resProHandsontableChangedData) throws Exception {
		getBaseDao().saveReservoirPropertyGridData(resProHandsontableChangedData);
	}

	public void modifyReservoirProperty(T ReservoirProperty) throws Exception {
		getBaseDao().updateObject(ReservoirProperty);
	}

	public String loadResMenuList(String resCode, String type) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		if (type.equalsIgnoreCase("res")) {
			sql = " select distinct v.yqcbh,r.resName as jhh from ReservoirProperty v ,Res r where v.yqcbh=r.resCode";
		}
		if (StringUtils.isNotBlank(resCode)) {
			sql += " and v.yqcbh like '%" + resCode + "%'";
		}

		if (type.equalsIgnoreCase("res")) {
			sql += " order by r.resName";
		}
		try {
			List<?> list = this.getBaseDao().getObjects(sql);
			result_json.append("[");
			String get_key = "";
			String get_val = "";
			if (null != list && list.size() > 0) {
				for (Object o : list) {
					Object[] obj = (Object[]) o;
					get_key = (String) obj[0];
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

	public void deleteReservoirProperty(int id, Class<T> clazz) throws Exception {
		T u = getBaseDao().getObject(clazz, id);
		getBaseDao().deleteObject(u);
	}

	/**
	 * <p>
	 * 描述：判断该油藏物性数据信息是否存在
	 * </p>
	 * 
	 * @param resCode
	 *            油气藏编号
	 * @return
	 */
	public boolean judgeResExistsOrNot(String resCode) {
		boolean flag = false;
		if (StringUtils.isNotBlank(resCode)) {
			String queryString = "SELECT u.yqcbh FROM ReservoirProperty u  where  u.yqcbh='" + resCode + "' order by u.yqcbh ";
			List<WellInformation> list = getBaseDao().getObjects(queryString);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}

	public void bulkDelete(final String ids) throws Exception {
		final String hql = "DELETE ReservoirProperty u where u.id in (" + ids + ")";
		getBaseDao().bulkObjectDelete(hql);
	}

	public T getReservoirProperty(Class<T> clazz, int id) {
		return getBaseDao().getObject(clazz, id);
	}

	public void deleteRP(final String ids) throws Exception {
		final String hql = "{call pro_T_001_update_add_delete(?,?,?,?,?,?,?,?,?,?,?,?)}";
		getBaseDao().bulkObjectDelete(hql);
	}
}
