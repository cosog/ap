package com.gao.service.back;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

import org.hibernate.engine.jdbc.SerializableBlobProxy;
import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gao.model.Params;
import com.gao.model.ProductionOutWellInfo;
import com.gao.model.WellInformation;
import com.gao.model.data.DataDictionary;
import com.gao.model.gridmodel.ProductionOutGridPanelData;
import com.gao.model.gridmodel.WellProHandsontableChangedData;
import com.gao.service.base.BaseService;
import com.gao.service.base.CommonDataService;
import com.gao.service.data.DataitemsInfoService;
import com.gao.utils.Page;
import com.gao.utils.PageHandler;
import com.gao.utils.StringManagerUtils;

/**
 * <p>
 * 描述：生产数据管理服务Service
 * </p>
 * 
 * @author gao 2014-06-10
 * @param <T>
 */
@Service("productionDataManagerService")
@SuppressWarnings({ "rawtypes" })
public class ProductionDataManagerService<T> extends BaseService<T> {
	@Autowired
	private CommonDataService service;
	@Autowired
	private DataitemsInfoService dataitemsInfoService;

	public String getProductionOutDataProList(Map map, Page pager) {
		// String entity = (String) map.get("entity");
		String jh = (String) map.get("jh");
		String orgCode = (String) map.get("orgCode");
		String resCode = (String) map.get("resCode");
		String jh_Str = "";
		String orgCode_Str = "";
		String orgId = (String) map.get("orgId");
		if (StringUtils.isNotBlank(orgCode)) {
			orgCode_Str = " and w.dwbh like '%" + orgCode + "%'";
		}
		String resCode_Str = "";
		if (StringUtils.isNotBlank(resCode)) {
			resCode_Str = " and w.yqcbh like '%" + resCode + "%'";
		}
		if (jh != null && StringUtils.isNotBlank(jh)) {
			jh_Str = " and w.jh like '%" + jh + "%'";
		}
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("select op.jlbh, op.jbh, w.jh, op.jslx,c9.itemname as jslxName, op.qtlx,c8.itemname as qtlxName,op.sfpfcl,c7.itemname as sfpfclName,");
		strBuf.append(" op.ccjzt,c6.itemname as ccjztName,op.hsld as hsl, op.yy,op.ty, op.hy,dy.dym,op.bg,op.jklw,op.scqyb, p.bj,p.bjb,c5.itemname as bjbName,p.zsc,p.sccj,");
		strBuf.append(" p.cybxh,op.ygnj,op.yctgnj,op.yjgj,op.yjgjb,op.yjgcd,op.ejgj,op.ejgjb,op.ejgcd,");
		strBuf.append("  op.sjgj,op.sjgjb,op.sjgcd,op.rcql,op.mdzt,c1.itemname as mdztName,op.jmb,op.bzgtbh,op.bzdntbh ");
		strBuf.append(" from ");
		strBuf.append("  tbl_code c1 ,");
		strBuf.append("  tbl_code c5,tbl_code c6,tbl_code c7,tbl_code c8,tbl_code c9    ");
		strBuf.append(",tbl_rpc_productiondata_hist op left outer join  tbl_wellinformation  w  on w.jlbh = op.jbh  ");
		strBuf.append(" left outer join t_dynamicliquidlevel   dy  on op.dymbh = dy.jlbh ");
		strBuf.append(" left outer join  t_pump p    on  p.jlbh = op.bbh ");
		strBuf.append(" left outer join  tbl_org o    on  o.org_code=w.dwbh ");
		strBuf.append("  where   ");
		strBuf.append("   c1.itemcode='MDZT' and c1.itemvalue=op.mdzt  ");
		strBuf.append("   and c5.itemcode='BJB' and c5.itemvalue=p.bjb  and c6.itemcode='CCJZT' and c6.itemvalue=op.ccjzt   ");
		strBuf.append("  and c7.itemcode='SFPFCL' and c7.itemvalue=op.sfpfcl and c8.sjbdm='tbl_rpc_productiondata_hist' and c8.itemcode='QTLX' and c8.itemvalue=op.qtlx ");
		strBuf.append("  and c9.itemcode='LiftingType' and c9.itemvalue=op.jslx ");
		strBuf.append(orgCode_Str + resCode_Str + jh_Str);
		strBuf.append("and  o.org_id in("+orgId+")");
		strBuf.append(" order by w.jh");
		String json = "";
		try {
			String columns = service.showTableHeadersColumns("produceOutData");
			json = this.findPageBySqlEntity(strBuf.toString(), columns, pager);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	
	public String getProductionOutData(Map map, Page pager,int recordCount,String wellType) {
		// String entity = (String) map.get("entity");
		String jh = (String) map.get("jh");
		String orgCode = (String) map.get("orgCode");
		String resCode = (String) map.get("resCode");
		String jh_Str = "";
		String orgCode_Str = "";
		String orgId = (String) map.get("orgId");
		if (StringUtils.isNotBlank(orgCode)) {
			orgCode_Str = " and w.dwbh like '%" + orgCode + "%'";
		}
		String resCode_Str = "";
		if (StringUtils.isNotBlank(resCode)) {
			resCode_Str = " and w.yqcbh like '%" + resCode + "%'";
		}
		if (jh != null && StringUtils.isNotBlank(jh)) {
			jh_Str = " and w.jh like '%" + jh + "%'";
		}
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("select op.jlbh as id,w.jh,op.jbh,op.scsj,c9.itemname as jslxName,c8.itemname as qtlxName,c7.itemname as sfpfclName,");
		strBuf.append(" c6.itemname as ccjztName,op.hsld as hsl, op.yy,op.ty, op.hy,dy.dym,op.bg,op.jklw,op.scqyb, op.bj,c3.itemname as blxName, op.bjb,op.zsc,op.blx,c2.itemname as btlxName,");
		strBuf.append(" op.ygnj,op.yctgnj,op.yjgj,op.yjgnj,op.yjgjb,op.yjgcd,op.ejgj,op.ejgnj,op.ejgjb,op.ejgcd,");
		strBuf.append(" op.sjgj,op.sjgnj,op.sjgjb,op.sjgcd,op.sijgj,op.sijgnj,op.sijgjb,op.sijgcd,c1.itemname as mdztName,op.jmb,op.bzgtbh,op.bzdntbh,to_char(op.cjsj@'yyyy-mm-dd hh24:mi:ss') as cjsj ");
		strBuf.append(" from ");
		strBuf.append("  tbl_code c1 ,");
		strBuf.append("  tbl_code c2 ,");
		strBuf.append("  tbl_code c3 ,");
		strBuf.append("  tbl_code c5,tbl_code c6,tbl_code c7,tbl_code c8,tbl_code c9    ");
		strBuf.append(",tbl_rpc_productiondata_hist op left outer join  tbl_wellinformation  w  on w.jlbh = op.jbh  ");
		strBuf.append(" left outer join t_wellorder t019     on w.jh=t019.jh ");
		strBuf.append(" left outer join t_dynamicliquidlevel   dy  on op.dymbh = dy.jlbh ");
		//strBuf.append(" left outer join  t_pump p    on  p.jlbh = op.bbh ");
		strBuf.append(" left outer join  tbl_org o    on  o.org_code=w.dwbh ");
		strBuf.append("  where   ");
		strBuf.append("   c1.itemcode='MDZT' and c1.itemvalue=op.mdzt  ");
		strBuf.append("   and c2.itemcode='BTLX' and c2.itemvalue=op.btlx");
		strBuf.append("   and c3.itemcode='BLX' and c3.itemvalue=op.blx");
		strBuf.append("   and c5.itemcode='BJB' and c5.itemvalue=op.bjb  and c6.itemcode='CCJZT' and c6.itemvalue=op.ccjzt   ");
		strBuf.append("  and c7.itemcode='SFPFCL' and c7.itemvalue=op.sfpfcl and c8.sjbdm='tbl_rpc_productiondata_hist' and c8.itemcode='QTLX' and c8.itemvalue=op.qtlx ");
		strBuf.append("  and c9.itemcode='LiftingType' and c9.itemvalue=w.jslx ");
		strBuf.append("  and w.jslx>="+wellType+" and w.jslx<("+wellType+"+99) ");
		strBuf.append(orgCode_Str + resCode_Str + jh_Str);
		strBuf.append("and  o.org_id in("+orgId+")");
		strBuf.append(" order by t019.pxbh, w.jh");
		String json = "";
		try {
			String columns = service.showTableHeadersColumns("produceOutData");
			
//			json = this.findPageBySqlEntity(strBuf.toString(), columns, pager);
			json = this.findPageBySqlEntity(recordCount,strBuf.toString(), columns, pager);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public String getProductionWellProductionData(String wellName,String orgId, Page pager,int recordCount,String wellType) {
		DataDictionary ddic = null;
		String columns= "";
		String sql="";
		StringBuffer result_json = new StringBuffer();
		if("200".equalsIgnoreCase(wellType)){
			columns=service.showTableHeadersColumns("produceOutData");
		}else if("400".equalsIgnoreCase(wellType)){
			columns=service.showTableHeadersColumns("screwPumpProductionData");
		}
		
		sql="select id,wellName,runTime,"
			+ "crudeOilDensity,waterDensity,naturalGasRelativeDensity,saturationPressure,reservoirDepth,reservoirTemperature,"
			+ "tubingPressure,casingPressure,wellHeadFluidTemperature,waterCut_W,productionGasOilRatio,producingfluidLevel,pumpSettingDepth,"
			+ "pumpGrade,pumpBoreDiameter,plungerLength,"
			+ "barrelLength,barrelSeries,rotorDiameter,QPR,"
			+ "tubingStringInsideDiameter,casingStringInsideDiameter,"
			+ "rodString,"
			+ "anchoringStateName,netGrossRatio,to_char(acquisitionTime,'yyyy-mm-dd hh24:mi:ss') "
			+ "from viw_rpc_productiondata_latest t "
			+ "where t.org_id in("+orgId+")  "
			+ "and t.liftingtype>="+wellType+" and t.liftingtype<("+wellType+"+99) ";
		if (StringManagerUtils.isNotNull(wellName)) {
			sql+= " and t.wellname like '%" + wellName + "%'";
		}
		
		sql+= "order by t.sortnum, t.wellname";
		
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",\"totalRoot\":[");
		
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			String rodString=obj[25]+"";
			String[] rodStringArr={};
			if(StringManagerUtils.isNotNull(rodString)){
				rodStringArr=rodString.split(";");
			}
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"wellName\":\""+obj[1]+"\",");
			result_json.append("\"runTime\":\""+obj[2]+"\",");
			result_json.append("\"crudeOilDensity\":\""+obj[3]+"\",");
			result_json.append("\"waterDensity\":\""+obj[4]+"\",");
			result_json.append("\"naturalGasRelativeDensity\":\""+obj[5]+"\",");
			result_json.append("\"saturationPressure\":\""+obj[6]+"\",");
			result_json.append("\"reservoirDepth\":\""+obj[7]+"\",");
			result_json.append("\"reservoirTemperature\":\""+obj[8]+"\",");
			result_json.append("\"tubingPressure\":\""+obj[9]+"\",");
			result_json.append("\"casingPressure\":\""+obj[10]+"\",");
			result_json.append("\"wellHeadFluidTemperature\":\""+obj[11]+"\",");
			result_json.append("\"waterCut_W\":\""+obj[12]+"\",");
			result_json.append("\"productionGasOilRatio\":\""+obj[13]+"\",");
			result_json.append("\"producingfluidLevel\":\""+obj[14]+"\",");
			result_json.append("\"pumpSettingDepth\":\""+obj[15]+"\",");
			result_json.append("\"pumpGrade\":\""+obj[16]+"\",");
			result_json.append("\"pumpBoreDiameter\":\""+obj[17]+"\",");
			result_json.append("\"plungerLength\":\""+obj[18]+"\",");
			result_json.append("\"barrelLength\":\""+obj[19]+"\",");
			result_json.append("\"barrelSeries\":\""+obj[20]+"\",");
			result_json.append("\"rotorDiameter\":\""+obj[21]+"\",");
			result_json.append("\"QPR\":\""+obj[22]+"\",");
			result_json.append("\"tubingStringInsideDiameter\":\""+obj[23]+"\",");
			result_json.append("\"casingStringInsideDiameter\":\""+obj[24]+"\",");
			
			for(int j=0;j<rodStringArr.length;j++){
				String[] everyRod=rodStringArr[j].split(",");
				int arrLength=everyRod.length;
				result_json.append("\"rodGrade"+(j+1)+"\":\""+(arrLength>=1?everyRod[0]:"")+"\",");
				result_json.append("\"rodOutsideDiameter"+(j+1)+"\":\""+(arrLength>=2?everyRod[1]:"")+"\",");
				result_json.append("\"rodInsideDiameter"+(j+1)+"\":\""+(arrLength>=3?everyRod[2]:"")+"\",");
				result_json.append("\"rodLength"+(j+1)+"\":\""+(arrLength>=4?everyRod[3]:"")+"\",");
			}
			
			result_json.append("\"anchoringStateName\":\""+obj[26]+"\",");
			result_json.append("\"netGrossRatio\":\""+obj[27]+"\",");
			result_json.append("\"acquisitionTime\":\""+obj[28]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json = result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		
		
		
		
		
		
		
		
//		if("200".equalsIgnoreCase(wellType)){
//			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId("produceOutData");
//		}else if("400".equalsIgnoreCase(wellType)){
//			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId("screwPumpProductionData");
//		}
//		columns = ddic.getTableHeader();
//		sql=ddic.getSql()+" from viw_rpc_productiondata_latest t where t.org_id in("+orgId+")  and t.liftingtype>="+wellType+" and t.liftingtype<("+wellType+"+99) ";
//		
//		
//		if (StringManagerUtils.isNotNull(wellName)) {
//			sql+= " and t.wellname like '%" + wellName + "%'";
//		}
//		sql+="order by t.sortnum, t.wellname";
//		
//		String json  = this.findPageBySqlEntity(recordCount,sql, columns, pager);
		String json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	
	public String exportWellProdInformationData(String wellName,String orgId,Page pager,String wellType) {
		StringBuffer result_json = new StringBuffer();
		String sql="select id,wellName,runTime,"
			+ "crudeOilDensity,waterDensity,naturalGasRelativeDensity,saturationPressure,reservoirDepth,reservoirTemperature,"
			+ "tubingPressure,casingPressure,wellHeadFluidTemperature,waterCut_W,productionGasOilRatio,producingfluidLevel,pumpSettingDepth,"
			+ "pumpGrade,pumpBoreDiameter,plungerLength,"
			+ "barrelLength,barrelSeries,rotorDiameter,QPR,"
			+ "tubingStringInsideDiameter,casingStringInsideDiameter,"
			+ "rodString,"
			+ "anchoringStateName,netGrossRatio,to_char(acquisitionTime,'yyyy-mm-dd hh24:mi:ss') "
			+ "from viw_rpc_productiondata_latest t "
			+ "where t.org_id in("+orgId+")  "
			+ "and t.liftingtype>="+wellType+" and t.liftingtype<("+wellType+"+99) ";
		if (StringManagerUtils.isNotNull(wellName)) {
			sql+= " and t.wellname like '%" + wellName + "%'";
		}
		
		sql+= "order by t.sortnum, t.wellname";
		
		List<?> list = this.findCallSql(sql);
		result_json.append("[");
		
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			String rodString=obj[25]+"";
			String[] rodStringArr={};
			if(StringManagerUtils.isNotNull(rodString)){
				rodStringArr=rodString.split(";");
			}
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"wellName\":\""+obj[1]+"\",");
			result_json.append("\"runTime\":\""+obj[2]+"\",");
			result_json.append("\"crudeOilDensity\":\""+obj[3]+"\",");
			result_json.append("\"waterDensity\":\""+obj[4]+"\",");
			result_json.append("\"naturalGasRelativeDensity\":\""+obj[5]+"\",");
			result_json.append("\"saturationPressure\":\""+obj[6]+"\",");
			result_json.append("\"reservoirDepth\":\""+obj[7]+"\",");
			result_json.append("\"reservoirTemperature\":\""+obj[8]+"\",");
			result_json.append("\"tubingPressure\":\""+obj[9]+"\",");
			result_json.append("\"casingPressure\":\""+obj[10]+"\",");
			result_json.append("\"wellHeadFluidTemperature\":\""+obj[11]+"\",");
			result_json.append("\"waterCut_W\":\""+obj[12]+"\",");
			result_json.append("\"productionGasOilRatio\":\""+obj[13]+"\",");
			result_json.append("\"producingfluidLevel\":\""+obj[14]+"\",");
			result_json.append("\"pumpSettingDepth\":\""+obj[15]+"\",");
			result_json.append("\"pumpGrade\":\""+obj[16]+"\",");
			result_json.append("\"pumpBoreDiameter\":\""+obj[17]+"\",");
			result_json.append("\"plungerLength\":\""+obj[18]+"\",");
			result_json.append("\"barrelLength\":\""+obj[19]+"\",");
			result_json.append("\"barrelSeries\":\""+obj[20]+"\",");
			result_json.append("\"rotorDiameter\":\""+obj[21]+"\",");
			result_json.append("\"QPR\":\""+obj[22]+"\",");
			result_json.append("\"tubingStringInsideDiameter\":\""+obj[23]+"\",");
			result_json.append("\"casingStringInsideDiameter\":\""+obj[24]+"\",");
			
			for(int j=0;j<rodStringArr.length;j++){
				String[] everyRod=rodStringArr[j].split(",");
				int arrLength=everyRod.length;
				result_json.append("\"rodGrade"+(j+1)+"\":\""+(arrLength>=1?everyRod[0]:"")+"\",");
				result_json.append("\"rodOutsideDiameter"+(j+1)+"\":\""+(arrLength>=2?everyRod[1]:"")+"\",");
				result_json.append("\"rodInsideDiameter"+(j+1)+"\":\""+(arrLength>=3?everyRod[2]:"")+"\",");
				result_json.append("\"rodLength"+(j+1)+"\":\""+(arrLength>=4?everyRod[3]:"")+"\",");
			}
			
			result_json.append("\"anchoringStateName\":\""+obj[26]+"\",");
			result_json.append("\"netGrossRatio\":\""+obj[27]+"\",");
			result_json.append("\"acquisitionTime\":\""+obj[28]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json = result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		String json=result_json.toString().replaceAll("null", "");
		return json;
	}
	

	/**
	 * <p>
	 * 描述：加载采出井生产数据下拉菜单数据信息
	 * </p>
	 * 
	 * @parm type 当前参数类型
	 * @return
	 * @throws Exception
	 */
	public String loadMenuTypeData(String type) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		sql = " select t.itemvalue,t.itemname from tbl_code t where  itemcode='" + type + "'";
		try {
			List<?> list = this.getfindByIdList(sql);
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

	/**
	 * <p>
	 * 描述：加载采出井生产数据躯替类型下拉菜单数据信息
	 * </p>
	 * 
	 * @parm type 当前参数类型
	 * @return
	 * @throws Exception
	 */
	public String loadQtlxTypeData(String type) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		sql = " select t.itemvalue,t.itemname from tbl_code t where   sjbdm='tbl_rpc_productiondata_hist' and itemcode='QTLX'";
		try {
			List<?> list = this.getfindByIdList(sql);
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

	public String queryProduceOutDataInfoParams(String orgCode, String resCode, String type) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		if (type.equalsIgnoreCase("res")) {
			sql = " select  distinct p.yqcbh,r.res_name  from tbl_wellinformation p,tbl_rpc_productiondata_hist w ,sc_res r where p.jlbh=w.jbh  and p.yqcbh=r.res_code ";
		} else if (type.equalsIgnoreCase("jh")) {
			sql = " select  p.jh as jh ,p.jh as dm from tbl_wellinformation p,tbl_rpc_productiondata_hist w ,t_wellorder t where 1=1 and p.jh=t.jh and p.jlbh=w.jbh ";
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
			sql += " order by t.pxbh, p.jh";
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

	/**<p>描述：获取举升类型的下拉菜单树形数据服务方法</p>
	 * 
	 * @return String json数据信息
	 * @throws Exception
	 */
	public String showLiftTypeTree() throws Exception {
		String sql = "select t.itemvalue as id,t.itemname as text from tbl_code t where t.itemcode='LiftingType'  order  by itemvalue";
		List<?> list = this.findCallSql(sql);
		StringBuffer result_json = new StringBuffer();
		String get_key = "";
		Map<String, List<Params>> map = this.showMapData(list);//把数据封装成map对象
		//TreeMap<String, Set<Params>> treemap = new TreeMap<String, Set<Params>>(map);
		result_json.append("[");
		if (null != list && list.size() > 0) {
			for (Object o : list) {
				Object[] obj = (Object[]) o;
				get_key = obj[0] + "";
				double remainder = Double.parseDouble(get_key) % 100.00;
				if (remainder < 1) {
					List<Params> set = map.get(get_key.trim());
					int size = set.size();
					if (size > 1) {
						for (Iterator iterator = set.iterator(); iterator.hasNext();) {
							Params params = (Params) iterator.next();
							if (get_key.trim().equals(params.getFields())) {
								result_json.append("{");
								result_json.append("id:'" + params.getFields() + "',");
								result_json.append("text:'" + params.getHeads() + "',");
								result_json.append("expanded:true,");
								result_json.append("children:[");
							} else {
								result_json.append("{ id:'" + params.getFields() + "',");
								result_json.append(" text:'" + params.getHeads() + "',");
								result_json.append(" leaf:true },");
							}
						}
						result_json.deleteCharAt(result_json.length() - 1);
						result_json.append("] },");
					} else {
						for (Iterator iterator = set.iterator(); iterator.hasNext();) {
							Params params = (Params) iterator.next();
							result_json.append(" {id:'" + params.getFields() + "',");
							result_json.append("text:'" + params.getHeads() + "',");
							result_json.append(" leaf:true },");
						}
					}
				}

			}

			if (result_json.toString().length() > 1) {
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("]");

		}
		return result_json.toString();
	}

	/** <p>描述：将list数据集合封装成为map对象</p>
	 * @param list
	 * @return Map<String, List<Params>> map 
	 */
	public Map<String, List<Params>> showMapData(List<?> list) {
		Map<String, List<Params>> map = new LinkedHashMap<String, List<Params>>();
		String get_key = "";
		String get_val = "";
		String key = "";
		List<Params> set = null;
		Params params = null;
		if (null != list && list.size() > 0) {
			for (Object o : list) {
				Object[] obj = (Object[]) o;
				get_key = obj[0] + "";
				get_val = obj[1] + "";
				double remainder = Double.parseDouble(get_key) % 100.00;
				if (remainder < 1)
					key = get_key;
				char curChar = get_key.charAt(0);
				char keyChar = key.charAt(0);
				boolean equalFlag = curChar == keyChar;
				if (remainder < 1 || equalFlag) {
					if (map.containsKey(key)) {
						set = map.get(key);
						params = new Params();
						params.setFields(get_key);
						params.setHeads(get_val);
						set.add(params);
						map.remove(key);
						map.put(key, set);

					} else {
						params = new Params();
						set = new ArrayList<Params>();
						params.setFields(get_key);
						params.setHeads(get_val);
						set.add(params);
						map.put(key, set);
					}
				}
			}
		}
		return map;

	}

	public List<T> fingProductionDataByJhList(String orgCode) throws Exception {
		String sql = " select  distinct (h.jh) from tbl_rpc_productiondata_hist w ,tbl_wellinformation h where h.jlbh=w.jbh  ";
		if (StringUtils.isNotBlank(orgCode)) {
			sql += " and h.dwbh like '%" + orgCode + "%'";
		}
		sql += " order by h.jh";
		return this.getBaseDao().getfindByIdList(sql);
	}

	public List<T> loadproductionOutWellID(Class<T> clazz) {
		String queryString = "SELECT u.jlbh,u.jh FROM WellInformation u where u.jlx like '1%' order by u.jh ";
		return getBaseDao().getObjects(queryString);
	}

	/**
	 * <p>
	 * 描述：判断当前井名是否已经存在了生产数据信息
	 * </p>
	 * 
	 * @param jbh
	 * @return
	 */
	public boolean judgeWellExistsOrNot(String jbh) {
		boolean flag = false;
		if (StringUtils.isNotBlank(jbh)) {
			String queryString = "SELECT u.jbh FROM Outputwellproduction u  where  u.jbh='" + jbh + "' order by u.jbh ";
			List<WellInformation> list = getBaseDao().getObjects(queryString);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}

	public boolean doBzgtBhEdit(ProductionOutWellInfo pro) {
		boolean flag = false;
		if (StringUtils.isNotBlank(pro.getJbh() + "")) {
			String queryString = "  update  tbl_rpc_productiondata_hist t  set t.bzgtbh=" + pro.getBzgtbh() + " where  t.jbh=" + pro.getJbh();
			String object = this.getBaseDao().deleteCallSql(queryString) + "";
			if (object == "1" || object.equals("1"))
				flag = true;

		}
		return flag;
	}

	
	
	public void saveProductionDataEditerGridData(WellProHandsontableChangedData wellProHandsontableChangedData, String ids) throws Exception {
		getBaseDao().saveProductionDataEditerGridData(wellProHandsontableChangedData, ids);
	}
}
