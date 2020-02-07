package com.gao.service.calculateManager;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gao.dao.BaseDao;
import com.gao.model.AlarmShowStyle;
import com.gao.model.DiagnosisAnalysisStatistics;
import com.gao.model.data.DataDictionary;
import com.gao.model.gridmodel.CalculateManagerHandsontableChangedData;
import com.gao.model.gridmodel.WellHandsontableChangedData;
import com.gao.service.base.BaseService;
import com.gao.service.base.CommonDataService;
import com.gao.service.data.DataitemsInfoService;
import com.gao.tast.EquipmentDriverServerTast;
import com.gao.utils.DataModelMap;
import com.gao.utils.Page;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.jdbc.SerializableBlobProxy;
import org.hibernate.engine.jdbc.SerializableClobProxy;

/**
 * <p>工况诊断（单张） --service层</p>
 * 
 * @author gao 2014-06-04
 * 
 */
@Component("calculateManagerService")
public class CalculateManagerService<T> extends BaseService<T> {

	private BaseDao dao;
	@Autowired
	private CommonDataService service;
	@Autowired
	private DataitemsInfoService dataitemsInfoService;
	/**
	 * <p>描述：采出井实时评价井列表</p>
	 * @param orgId
	 * @param jh
	 * @param pager
	 * @return
	 * @throws Exception
	 */
	public String getCalculateResultData(String orgId, String jh, Page pager,String wellType,String startDate,String endDate,String jsbz)
			throws Exception {
		DataDictionary ddic = null;
		String columns= "";
		String sql="";
		String finalSql="";
		String sqlAll="";
		String ddicName="calculateManager";
		if("200".equals(wellType)){
			ddicName="calculateManager";
		}else if("400".equals(wellType)){
			ddicName="screwPumpCalculateManager";
		}
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		
		columns = ddic.getTableHeader();
		sql=ddic.getSql()+" from v_calculateresult t where t.org_id in("+orgId+") "
				+ " and to_date(to_char(t.gtcjsj,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')";
		
		if(StringManagerUtils.isNotNull(wellType)){
			sql+=" and t.jslx>="+wellType+" and t.jslx<("+wellType+"+100) ";
		}
		if(StringManagerUtils.isNotNull(jh)){
			sql+=" and  t.jh = '" + jh.trim() + "' ";
		}
		if(StringManagerUtils.isNotNull(jsbz)){
			sql+=" and  t.jsbz = " + jsbz + " ";
		}
		sql+=" order by t.gtcjsj desc, t.jh";
		int maxvalue=pager.getLimit()+pager.getStart();
		finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		String getResult = this.findCustomPageBySqlEntity(sql,finalSql, columns, 20 + "", pager);
		return getResult;
	}
	
	public void saveRecalculateData(CalculateManagerHandsontableChangedData calculateManagerHandsontableChangedData) throws Exception {
		getBaseDao().saveRecalculateData(calculateManagerHandsontableChangedData);
	}
	
	
	public String getCalculateStatusList(String orgId, String jh, String wellType,String startDate,String endDate)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql="";
		sql="select distinct(decode(t.jsbz,2,0,t.jsbz)),t2.itemname "
				+ " from t_outputwellhistory t,t_code t2,t_wellinformation t3,sc_org org "
				+ " where t.jbh=t3.jlbh and t3.dwbh=org.org_code and t.jsbz=t2.itemvalue and t2.itemcode='JSBZ'"
				+ " and org.org_id in("+orgId+") "
				+ " and to_date(to_char(t.gtcjsj,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')";
		
		if(StringManagerUtils.isNotNull(wellType)){
			sql+=" and t3.jslx>="+wellType+" and t3.jslx<("+wellType+"+100) ";
		}
		if(StringManagerUtils.isNotNull(jh)){
			sql+=" and  t3.jh = '" + jh.trim() + "' ";
		}
		
		sql+=" order by decode(t.jsbz,2,0,t.jsbz)";
		try {
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(sql);
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
	
	public void recalculateByProductionData(String orgId, String jh, String wellType,String startDate,String endDate,String jsbz)throws Exception {
		getBaseDao().recalculateByProductionData(orgId,jh,wellType,startDate,endDate,jsbz);
	}
	
	public BaseDao getDao() {
		return dao;
	}

	@Resource
	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
}
