package com.gao.service.report;

import java.util.List;
import org.springframework.stereotype.Service;

import com.gao.service.base.BaseService;
import com.gao.utils.Page;
import com.gao.utils.StringManagerUtils;

@Service("reportProductionWellService")
public class ReportProductionWellService<T> extends BaseService<T> {

	// 得到总行数
	public Integer getTotalRows(String orgId) {
		//String orgIds = this.getUserOrgIds(orgId);
		String hql = " select v.jh From ReportProductionWell as v ,Org as o where 1=1 and v.dwbh=o.orgCode and o.orgId in("
				+ orgId + ")";
		return this.getBaseDao().getRecordCountRows(hql);
	}

	public List<T> loadReportPumpingUnitOutWellNearestDay(int offset,
			int pageSize, Integer orgId) throws Exception {
		String orgIds = this.getBaseDao().getUserOrgIds(orgId);
		String hql = "select  distinct(v.jssj) as jssj  From ReportProductionWell   v, Org as o where 1=1 and v.dwbh=o.orgCode and o.orgId in("
				+ orgIds + ")  order by v.jssj desc";
		return getBaseDao().getListForPage(offset, pageSize, hql);
	}

	// 得到列表页面
	public List<T> loadReportAllOutDataParams(int offset, int pageSize,
			Integer orgId) throws Exception {
		String orgIds = this.getBaseDao().getUserOrgIds(orgId);
		String hql = "select  distinct(v.jh) as jh  From ReportProductionWell   v, Org as o where 1=1 and v.dwbh=o.orgCode and o.orgId in("
				+ orgIds + ")  order by v.jh";
		return getBaseDao().getListForPage(offset, pageSize, hql);
	}

	public List<?> getReportProductionWellJhPage(int intPage, int pageSize,
			String orgId, String jh) throws Exception {
		//String orgIds = this.getBaseDao().getUserOrgIds(orgId);
		String tempHql = " select  v.jh as jh from  tbl_wellinformation v  ,tbl_org  g,t_wellorder t where 1=1 and v.jlbh=t.jlbh and  v.jlx=101 and v.dwbh=g.org_code  and g.org_id in ("
					+ orgId + ") order by t.pxbh,v.jh";

		if (jh.trim().length() > 0) {
			tempHql += "   and v.jh like  '%" + jh + "%' ";
		}
		String hql = tempHql;
		return this.getBaseDao().findCallSql(hql);
	}

	public List<T> getReportProductionWellJssjPage(int intPage, int pageSize,
			String orgId, String jssj) throws Exception {
	//	String orgIds = this.getBaseDao().getUserOrgIds(orgId);
		String tempHql = " select  distinct(to_char(v.jssj,'YYYY-MM-DD')) as jssj   From ReportProductionWell  as  v ,Org as o where 1=1 and v.dwbh=o.orgCode and o.orgId in("
				+ orgId + ") ";
		if (jssj != null) {
			tempHql += " and  v.jssj like '%" + jssj + "%' ";
		}
		String tempHqls = tempHql
				+ "  order by to_char(v.jssj,'YYYY-MM-DD') desc ";
		String hql = tempHqls;
		return this.getBaseDao().getListForPage(intPage, pageSize, hql);
	}

	public List<T> getMonthReportProductionWellJssjPage(int intPage,
			int pageSize, String orgId, String jssj) throws Exception {
		//String orgIds = this.getBaseDao().getUserOrgIds(orgId);
		String tempHql = " select  distinct(to_char(v.jssj,'YYYY-MM')) as jssj   From ReportProductionWell  as  v ,Org as o where 1=1 and v.dwbh=o.orgCode and o.orgId in("
				+ orgId + ") ";
		if (jssj != null) {
			tempHql += " and  v.jssj like '%" + jssj + "%' ";
		}
		String tempHqls = tempHql
				+ "  order by to_char(v.jssj,'YYYY-MM') desc ";
		String hql = tempHqls;
		return this.getBaseDao().getListForPage(intPage, pageSize, hql);
	}

	
	public String showDiagnosisDailyReportData(Page pager, String orgId,String wellName,String calculateDate,String wellType)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql="select t.id, t.wellName,to_char(t.calculateDate,'yyyy-mm-dd') as calculateDate,"
				+ " t.commTime,t.commRange, t.commTimeEfficiency,"
				+ " t.runTime,t.runRange, t.runTimeEfficiency,"
				+ " t.workingConditionName,t.optimizationSuggestion,"
				+ " t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,t.waterCut,t.fullnesscoEfficient,"
				+ " t.wattDegreeBalanceLevel,t.wattDegreeBalance,t.iDegreeBalanceLevel,t.iDegreeBalance,t.deltaRadius,"
				+ " t.systemEfficiency,t.surfaceSystemEfficiency,t.welldownSystemEfficiency,t.powerConsumptionPerthm,"
				+ " t.todayWattEnergy,"
				+ " t.rpm,t.pumpSettingDepth,t.producingFluidLevel,t.submergence,"
				+ " remark"
				+ " from v_dailydata t where t.org_id in ("+orgId+") and t.calculateDate=to_date('"+calculateDate+"','yyyy-mm-dd') ";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  t.wellName='"+wellName+"'";
		}
		if(StringManagerUtils.isNotNull(wellType)){
			sql+=" and liftingtype>="+wellType+" and liftingtype<("+wellType+"+99) ";
		}
		sql+=" order by t.sortNum, t.wellName";
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(sql);
		String columns= "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50},"
				+ "{ \"header\":\"井名\",\"dataIndex\":\"wellName\"},"
				+ "{ \"header\":\"日期\",\"dataIndex\":\"calculateDate\",width:100},"
				
				+ "{ \"header\":\"通信时间(h)\",\"dataIndex\":\"commTime\"},"
				+ "{ \"header\":\"在线区间\",\"dataIndex\":\"commRange\"},"
				+ "{ \"header\":\"在线时率(%)\",\"dataIndex\":\"commTimeEfficiency\"},"
				
				+ "{ \"header\":\"运行时间(h)\",\"dataIndex\":\"runTime\"},"
				+ "{ \"header\":\"运行区间\",\"dataIndex\":\"commRange\"},"
				+ "{ \"header\":\"运行时率(%)\",\"dataIndex\":\"runTimeEfficiency\"},";
		if("200".equals(wellType)){//抽油机
			columns += "{ \"header\":\"功图工况\",\"dataIndex\":\"workingConditionName\"},"
					+ "{ \"header\":\"优化建议\",\"dataIndex\":\"optimizationSuggestion\",width:120},";
		}
					
		columns+= "{ \"header\":\"产液量(t/d)\",\"dataIndex\":\"liquidWeightProduction\"},"
			+ "{ \"header\":\"产油量(t/d)\",\"dataIndex\":\"oilWeightProduction\"},"
			+ "{ \"header\":\"产水量(t/d)\",\"dataIndex\":\"waterWeightProduction\"},"
			+ "{ \"header\":\"含水率(%)\",\"dataIndex\":\"waterCut\"},";
		if("200".equals(wellType)){//抽油机
			columns+=  "{ \"header\":\"充满系数\",\"dataIndex\":\"fullnesscoEfficient\"},"
					+ "{ \"header\":\"功率平衡状态\",\"dataIndex\":\"wattDegreeBalanceLevel\"},"
					+ "{ \"header\":\"功率平衡度(%)\",\"dataIndex\":\"wattDegreeBalance\"},"
					+ "{ \"header\":\"电流平衡状态\",\"dataIndex\":\"iDegreeBalanceLevel\"},"
					+ "{ \"header\":\"电流平衡度(%)\",\"dataIndex\":\"iDegreeBalance\"},"
					+ "{ \"header\":\"移动距离(m)\",\"dataIndex\":\"deltaRadius\"},"
					+ "{ \"header\":\"系统效率(%)\",\"dataIndex\":\"systemEfficiency\"},"
					+ "{ \"header\":\"地面效率(%)\",\"dataIndex\":\"surfaceSystemEfficiency\"},"
					+ "{ \"header\":\"井下效率(%)\",\"dataIndex\":\"welldownSystemEfficiency\"},";
		}else if("400".equals(wellType)){//螺杆泵
			columns+= "{ \"header\":\"转速(r/min)\",\"dataIndex\":\"rpm\"},"
			+ "{ \"header\":\"泵挂(m)\",\"dataIndex\":\"bg\"},"
			+ "{ \"header\":\"动液面(m)\",\"dataIndex\":\"dym\"},"
			+ "{ \"header\":\"沉没度(m)\",\"dataIndex\":\"cmd\"},"
			+ "{ \"header\":\"系统效率(%)\",\"dataIndex\":\"xtxl\"},";
		}
			
		columns+= "{ \"header\":\"吨液百米耗电量(kW·h/100·t)\",\"dataIndex\":\"powerConsumptionPerthm\"},"
			+ "{ \"header\":\"日用电量(kW·h)\",\"dataIndex\":\"todayWattEnergy\"},"
			+ "{ \"header\":\"备注\",\"dataIndex\":\"remark\"}"
			+ "]";
		
		result_json.append("{ \"success\":true,\"wellName\":\""+wellName+"\",\"calculateDate\":\""+calculateDate+"\",\"columns\":"+columns+",");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"wellName\":\""+obj[1]+"\",");
			result_json.append("\"calculateDate\":\""+obj[2]+"\",");
			result_json.append("\"commTime\":\""+obj[3]+"\",");
			result_json.append("\"commRange\":\""+obj[4]+"\",");
			result_json.append("\"commTimeEfficiency\":\""+obj[5]+"\",");
			result_json.append("\"runTime\":\""+obj[6]+"\",");
			result_json.append("\"runRange\":\""+obj[7]+"\",");
			result_json.append("\"runTimeEfficiency\":\""+obj[8]+"\",");
			
			result_json.append("\"optimizationSuggestion\":\""+obj[10]+"\",");
			result_json.append("\"liquidWeightProduction\":\""+obj[11]+"\",");
			result_json.append("\"oilWeightProduction\":\""+obj[12]+"\",");
			result_json.append("\"waterWeightProduction\":\""+obj[13]+"\",");
			result_json.append("\"waterCut\":\""+obj[14]+"\",");
			result_json.append("\"systemEfficiency\":\""+obj[21]+"\",");
			result_json.append("\"powerConsumptionPerthm\":\""+obj[24]+"\",");
			result_json.append("\"todayWattEnergy\":\""+obj[25]+"\",");
			if("200".equals(wellType)){//抽油机
				result_json.append("\"workingConditionName\":\""+obj[9]+"\",");
				result_json.append("\"fullnesscoEfficient\":\""+obj[15]+"\",");
				result_json.append("\"wattDegreeBalanceLevel\":\""+obj[16]+"\",");
				result_json.append("\"wattDegreeBalance\":\""+obj[17]+"\",");
				result_json.append("\"iDegreeBalanceLevel\":\""+obj[18]+"\",");
				result_json.append("\"iDegreeBalance\":\""+obj[19]+"\",");
				result_json.append("\"deltaRadius\":\""+obj[20]+"\",");
				result_json.append("\"surfaceSystemEfficiency\":\""+obj[22]+"\",");
				result_json.append("\"welldownSystemEfficiency\":\""+obj[23]+"\",");
			}else if("400".equals(wellType)){//螺杆泵
				result_json.append("\"rpm\":\""+obj[26]+"\",");
				result_json.append("\"pumpSettingDepth\":\""+obj[27]+"\",");
				result_json.append("\"producingFluidLevel\":\""+obj[28]+"\",");
				result_json.append("\"submergence\":\""+obj[29]+"\",");
			}
			result_json.append("\"remark\":\""+obj[30]+"\"},");
		}
		if(list.size()>0){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String exportDiagnosisDailyReportExcelData(Page pager, String orgId,String wellName,String calculateDate,String wellType)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql="select t.id, t.wellName,to_char(t.calculateDate,'yyyy-mm-dd') as calculateDate,"
				+ " t.commTime,t.commRange, t.commTimeEfficiency,"
				+ " t.runTime,t.runRange, t.runTimeEfficiency,"
				+ " t.workingConditionName,t.optimizationSuggestion,"
				+ " t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,t.waterCut,t.fullnesscoEfficient,"
				+ " t.wattDegreeBalanceLevel,t.wattDegreeBalance,t.iDegreeBalanceLevel,t.iDegreeBalance,t.deltaRadius,"
				+ " t.systemEfficiency,t.surfaceSystemEfficiency,t.welldownSystemEfficiency,t.powerConsumptionPerthm,"
				+ " t.todayWattEnergy,"
				+ " t.rpm,t.pumpSettingDepth,t.producingFluidLevel,t.submergence,"
				+ " remark"
				+ " from v_dailydata t where t.org_id in ("+orgId+") and t.calculateDate=to_date('"+calculateDate+"','yyyy-mm-dd') ";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  t.wellName='"+wellName+"'";
		}
		if(StringManagerUtils.isNotNull(wellType)){
			sql+=" and liftingtype>="+wellType+" and liftingtype<("+wellType+"+99) ";
		}
		sql+=" order by t.sortNum, t.wellName";
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(sql);
		
		result_json.append("[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"wellName\":\""+obj[1]+"\",");
			result_json.append("\"calculateDate\":\""+obj[2]+"\",");
			result_json.append("\"commTime\":\""+obj[3]+"\",");
			result_json.append("\"commRange\":\""+obj[4]+"\",");
			result_json.append("\"commTimeEfficiency\":\""+obj[5]+"\",");
			result_json.append("\"runTime\":\""+obj[6]+"\",");
			result_json.append("\"runRange\":\""+obj[7]+"\",");
			result_json.append("\"runTimeEfficiency\":\""+obj[8]+"\",");
			
			result_json.append("\"optimizationSuggestion\":\""+obj[10]+"\",");
			result_json.append("\"liquidWeightProduction\":\""+obj[11]+"\",");
			result_json.append("\"oilWeightProduction\":\""+obj[12]+"\",");
			result_json.append("\"waterWeightProduction\":\""+obj[13]+"\",");
			result_json.append("\"waterCut\":\""+obj[14]+"\",");
			result_json.append("\"systemEfficiency\":\""+obj[21]+"\",");
			result_json.append("\"powerConsumptionPerthm\":\""+obj[24]+"\",");
			result_json.append("\"todayWattEnergy\":\""+obj[25]+"\",");
			if("200".equals(wellType)){//抽油机
				result_json.append("\"workingConditionName\":\""+obj[9]+"\",");
				result_json.append("\"fullnesscoEfficient\":\""+obj[15]+"\",");
				result_json.append("\"wattDegreeBalanceLevel\":\""+obj[16]+"\",");
				result_json.append("\"wattDegreeBalance\":\""+obj[17]+"\",");
				result_json.append("\"iDegreeBalanceLevel\":\""+obj[18]+"\",");
				result_json.append("\"iDegreeBalance\":\""+obj[19]+"\",");
				result_json.append("\"deltaRadius\":\""+obj[20]+"\",");
				result_json.append("\"surfaceSystemEfficiency\":\""+obj[22]+"\",");
				result_json.append("\"welldownSystemEfficiency\":\""+obj[23]+"\",");
			}else if("400".equals(wellType)){//螺杆泵
				result_json.append("\"rpm\":\""+obj[26]+"\",");
				result_json.append("\"pumpSettingDepth\":\""+obj[27]+"\",");
				result_json.append("\"producingFluidLevel\":\""+obj[28]+"\",");
				result_json.append("\"submergence\":\""+obj[29]+"\",");
			}
			result_json.append("\"remark\":\""+obj[30]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
}
