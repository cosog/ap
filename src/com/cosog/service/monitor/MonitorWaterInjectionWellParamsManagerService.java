package com.cosog.service.monitor;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.cosog.service.base.BaseService;

/**<p>描述：注入井监测下拉菜单类</p>
 * 
 * @author gao 2014-06-06
 *
 * @param <T>
 */
@Service("monitorWaterInjectionWellParamsManagerService")
public class MonitorWaterInjectionWellParamsManagerService<T> extends BaseService<T> {

	public String loadMonitorWaterInjectionParams(String orgId, String jh,
			String bjlx, String type) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		if (type.equalsIgnoreCase("jh")) {
			sql = " select distinct v.jh as jh,v.jh as dm from MonitorWaterInjectionWellParams v ,Org as o where 1=1 and v.dwbh=o.orgCode and o.orgId in("
					+ orgId + ")";
		} else if (type.equalsIgnoreCase("bjlx")) {
			sql = " select distinct v.dm,v.bjlx from MonitorWaterInjectionWellParams v ,Org as o where 1=1 and v.dwbh=o.orgCode and o.orgId in("
					+ orgId + ") ";
		}
		if (StringUtils.isNotBlank(jh)) {
			sql += " and v.jh like '%" + jh + "%'";
		}
		if (StringUtils.isNotBlank(bjlx)) {
			sql += " and v.bjlx like '%" + bjlx + "%'";
		}
		if (type.equalsIgnoreCase("jh")) {
			sql += " order by v.jh";
		} else if (type.equalsIgnoreCase("bjlx")) {
			sql += " order by v.bjlx desc";
		}
		try {
			List<?> list = this.find(sql);
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
				if (result_json.toString().endsWith(",")) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
			}
			result_json.append("]");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_json.toString();
	}
	/**<p>描述：注入井历史查询下拉菜单方法</p>
	 * @param orgId
	 * @param jh
	 * @param bjlx
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public String loadMonitorWaterInjectionHistoryParams(String orgId, String jh,
			String bjlx, String type) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		if (type.equalsIgnoreCase("jh")) {
			sql = " select distinct v.jh as jh,v.jh as dm from v_002_22_dynamicmonitorhis_cn v  where 1=1 and v.org_id in ("
					+ orgId + ")";
		} else if (type.equalsIgnoreCase("bjlx")) {
			sql = " select distinct v.bjztdm as dm,v.bjztmc as mc from v_002_22_dynamicmonitorhis_cn v  where 1=1 and v.org_id in("
					+ orgId + ") ";
		}
		if (StringUtils.isNotBlank(jh)) {
			sql += " and v.jh like '%" + jh + "%'";
		}
		if (StringUtils.isNotBlank(bjlx)) {
			sql += " and v.bjztdm = " + bjlx ;
		}
		if (type.equalsIgnoreCase("jh")) {
			sql += " order by v.jh";
		} else if (type.equalsIgnoreCase("bjlx")) {
			sql += " order by v.bjztdm ";
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
				if (result_json.toString().endsWith(",")) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
			}
			result_json.append("]");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_json.toString();
	}
}
