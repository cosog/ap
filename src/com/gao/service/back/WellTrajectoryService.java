package com.gao.service.back;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gao.model.WellTrajectory;
import com.gao.service.base.BaseService;

@Service("wellTrajectoryService")
public class WellTrajectoryService<T> extends BaseService<T> {

	public List<WellTrajectory> scanWellTrajectoryInfo(String orgCode,String resCode,String jh) throws SQLException {
		return this.getBaseDao().scanWellTrajectoryInfo(orgCode,resCode,jh);
	}

	public List<WellTrajectory> getWellTrajectoryJhList() {
		return this.getBaseDao().getWellTrajectoryJhList();
	}

	public List<WellTrajectory> getWellJhList() {
		return this.getBaseDao().getWellJhList();
	}
	public String queryWellTrajectoryParams(String orgCode, String resCode,String type) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		if (type.equalsIgnoreCase("res")) {
			sql = " select  distinct p.yqcbh,r.res_name  from t_wellinformation p,t_outputwellproduction w ,sc_res r where p.jlbh=w.jbh  and p.yqcbh=r.res_code ";
		} else if (type.equalsIgnoreCase("jh")) {
			sql = " select  distinct p.jh as jh ,p.jh as dm from t_welltrajectory w ,t_wellinformation p where p.jlbh=w.jbh ";
		} 
		if (StringUtils.isNotBlank(orgCode)) {
			sql += " and p.dwbh like '%" + orgCode + "%'";
		}
		if (StringUtils.isNotBlank(resCode)) {
			sql += " and p.yqcbh like '%" + resCode + "%'";
		}
		if (type.equalsIgnoreCase("res")) {
			sql += " order by p.yqcbh ";
		} else if (type.equalsIgnoreCase("jh")) {
			sql += " order by p.jh";
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
	
	public boolean addOrUpdateWellTrajectoryByJbh(WellTrajectory wtvo, String jh) {
		try {
			this.getBaseDao().addOrUpdateWellTrajectoryByJbh(wtvo, jh);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public void updateWellTrajectory(WellTrajectory well, int line, int jbh)
			throws Exception {
		getBaseDao().updateWellTrajectoryById(well, line, jbh);
	}

	public void deleteWellTrajectory(String track, int line, int jbh)
			throws Exception {
		getBaseDao().deleteWellTrajectoryInfoById(track, line, jbh);
	}
}
