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
	public String getCalculateResultData(String orgId, String wellName, Page pager,String wellType,String startDate,String endDate,String calculateSign)
			throws Exception {
		DataDictionary ddic = null;
		String columns= "";
		String sql="";
		String finalSql="";
		String sqlAll="";
		String ddicName="calculateManager";
		StringBuffer result_json = new StringBuffer();
		if("200".equals(wellType)){
			ddicName="calculateManager";
		}else if("400".equals(wellType)){
			ddicName="screwPumpCalculateManager";
		}
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		
		columns = ddic.getTableHeader();
		sql="select t.id,t.wellName,to_char(t.acquisitionTime,'yyyy-mm-dd hh24:mi:ss'),t.workingConditionName,t.liquidProduction,t.oilProduction,"
			+ "t.crudeoilDensity,t.waterDensity,t.naturalGasRelativeDensity,"
			+ "t.saturationPressure,t.reservoirDepth,t.reservoirTemperature,"
			+ "t.tubingPressure,t.casingPressure,t.wellHeadFluidTemperature,t.waterCut,t.productionGasOilRatio,t.producingFluidLevel,"
			+ "t.pumpSettingDepth,t.pumpGrade,t.pumpboreDiameter,t.plungerLength,"
			+ "t.tubingStringInsideDiameter,t.casingStringInsideDiameter,"
			+ "t.anchoringStateName,t.netGrossRatio,t.resultStatus,"
			+ "t.rodstring"
			+ " from viw_rpc_calculatemain t where t.orgid in("+orgId+") "
			+ " and t.acquisitionTime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
		
		
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  t.wellName = '" + wellName.trim() + "' ";
		}
		if(StringManagerUtils.isNotNull(calculateSign)){
			sql+=" and  t.resultstatus = " + calculateSign + " ";
		}
		sql+=" order by t.acquisitionTime desc, t.wellName";
		int maxvalue=pager.getLimit()+pager.getStart();
		finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(finalSql);
		
		result_json.append("{\"success\":true,\"totalCount\":"+totals+",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			String rodString=obj[27]+"";
			String[] rodStringArr={};
			if(StringManagerUtils.isNotNull(rodString)){
				rodStringArr=rodString.split(";");
			}
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"wellName\":\""+obj[1]+"\",");
			result_json.append("\"acquisitionTime\":\""+obj[2]+"\",");
			result_json.append("\"workingConditionName\":\""+obj[3]+"\",");
			result_json.append("\"liquidProduction\":\""+obj[4]+"\",");
			result_json.append("\"oilProduction\":\""+obj[5]+"\",");
			result_json.append("\"crudeoilDensity\":\""+obj[6]+"\",");
			result_json.append("\"waterDensity\":\""+obj[7]+"\",");
			result_json.append("\"naturalGasRelativeDensity\":\""+obj[8]+"\",");
			result_json.append("\"saturationPressure\":\""+obj[9]+"\",");
			result_json.append("\"reservoirDepth\":\""+obj[10]+"\",");
			result_json.append("\"reservoirTemperature\":\""+obj[11]+"\",");
			result_json.append("\"tubingPressure\":\""+obj[12]+"\",");
			result_json.append("\"casingPressure\":\""+obj[13]+"\",");
			result_json.append("\"wellHeadFluidTemperature\":\""+obj[14]+"\",");
			result_json.append("\"waterCut\":\""+obj[15]+"\",");
			result_json.append("\"productionGasOilRatio\":\""+obj[16]+"\",");
			result_json.append("\"producingFluidLevel\":\""+obj[17]+"\",");
			result_json.append("\"pumpSettingDepth\":\""+obj[18]+"\",");
			result_json.append("\"pumpGrade\":\""+obj[19]+"\",");
			result_json.append("\"pumpboreDiameter\":\""+obj[20]+"\",");
			result_json.append("\"plungerLength\":\""+obj[21]+"\",");
			result_json.append("\"tubingStringInsideDiameter\":\""+obj[22]+"\",");
			result_json.append("\"casingStringInsideDiameter\":\""+obj[23]+"\",");
			
			for(int j=0;j<rodStringArr.length;j++){
				String[] everyRod=rodStringArr[j].split(",");
				int arrLength=everyRod.length;
				result_json.append("\"rodGrade"+(j+1)+"\":\""+(arrLength>=1?everyRod[0]:"")+"\",");
				result_json.append("\"rodOutsideDiameter"+(j+1)+"\":\""+(arrLength>=2?everyRod[1]:"")+"\",");
				result_json.append("\"rodInsideDiameter"+(j+1)+"\":\""+(arrLength>=3?everyRod[2]:"")+"\",");
				result_json.append("\"rodLength"+(j+1)+"\":\""+(arrLength>=4?everyRod[3]:"")+"\",");
			}
			
			result_json.append("\"anchoringStateName\":\""+obj[24]+"\",");
			result_json.append("\"netGrossRatio\":\""+obj[25]+"\",");
			result_json.append("\"resultStatus\":\""+obj[26]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json = result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		
//		String getResult = this.findCustomPageBySqlEntity(sql,finalSql, columns, 20 + "", pager);
		String json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public void saveRecalculateData(CalculateManagerHandsontableChangedData calculateManagerHandsontableChangedData) throws Exception {
		getBaseDao().saveRecalculateData(calculateManagerHandsontableChangedData);
	}
	
	
	public String getCalculateStatusList(String orgId, String wellName, String wellType,String startDate,String endDate)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql="";
		String tableName="";
		if("200".equals(wellType)){
			tableName="tbl_rpc_diagram_hist";
		}
		sql="select distinct(decode(t.resultstatus,2,0,t.resultstatus)),t2.itemname "
				+ " from "+tableName+" t,tbl_code t2,tbl_wellinformation t3 "
				+ " where t.wellid=t3.id and t.resultstatus=t2.itemvalue and t2.itemcode='JSBZ'"
				+ " and t3.orgid in("+orgId+") "
				+ " and to_date(to_char(t.acquisitionTime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')";
		
		if(StringManagerUtils.isNotNull(wellType)){
			sql+=" and t3.liftingtype>="+wellType+" and t3.liftingtype<("+wellType+"+100) ";
		}
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  t3.wellName = '" + wellName.trim() + "' ";
		}
		
		sql+=" order by decode(t.resultstatus,2,0,t.resultstatus)";
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
	
	public int recalculateByProductionData(String orgId, String wellName, String wellType,String startDate,String endDate,String calculateSign)throws Exception {
		String updateSql="update tbl_rpc_diagram_hist t "
				+ " set (productiondataid,resultstatus)=(select t2.id,2 from tbl_rpc_productiondata_hist t2,tbl_rpc_productiondata_latest t3 where t2.wellid=t3.wellid and t2.acquisitiontime=t3.acquisitiontime and t2.wellid=t.wellid) "
				+ " where t.acquisitiontime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
		if(StringManagerUtils.isNotNull(calculateSign)){
			updateSql+=" and t.resultstatus in ("+calculateSign+")";
		}
		if(StringManagerUtils.isNotNull(wellName)){
			updateSql+=" and t.wellid=(select well.id from tbl_wellinformation well where well.wellname='"+wellName+"')";
		}
		return getBaseDao().executeSqlUpdate(updateSql);
	}
	
	public BaseDao getDao() {
		return dao;
	}

	@Resource
	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
}
