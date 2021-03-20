package com.gao.service.report;

import java.util.List;
import org.springframework.stereotype.Service;

import com.gao.service.base.BaseService;
import com.gao.utils.Config;
import com.gao.utils.ConfigFile;
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

	
	public String showRPCDailyReportData(Page pager, String orgId,String wellName,String calculateDate,String calculateEndDate)throws Exception {
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		String sql="select t.id, t.wellName,to_char(t.calculateDate,'yyyy-mm-dd') as calculateDate,"
				+ " t.commTime,t.commRange, t.commTimeEfficiency,"
				+ " t.runTime,t.runRange, t.runTimeEfficiency,"
				+ " t.workingConditionName,t.optimizationSuggestion,";
		if(configFile.getOthers().getProductionUnit()==0){
			sql+=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,t.waterCut_W,";
		}else{
			sql+=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,t.waterCut,";
		}
		
		
		sql+= " t.fullnesscoEfficient,"
			+ " t.wattDegreeBalanceLevel,t.wattDegreeBalance,t.iDegreeBalanceLevel,t.iDegreeBalance,t.deltaRadius,"
			+ " t.systemEfficiency,t.surfaceSystemEfficiency,t.welldownSystemEfficiency,t.powerConsumptionPerthm,"
			+ " t.todayKWattH,"
			+ " remark"
			+ " from "
			+ " viw_rpc_total_day t where t.org_id in ("+orgId+") "
			+ " and t.calculateDate between to_date('"+calculateDate+"','yyyy-mm-dd') and to_date('"+calculateEndDate+"','yyyy-mm-dd') ";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  t.wellName='"+wellName+"'";
		}
		sql+=" order by t.sortNum, t.wellName,t.calculateDate";
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
				+ "{ \"header\":\"运行区间\",\"dataIndex\":\"runRange\"},"
				+ "{ \"header\":\"运行时率(%)\",\"dataIndex\":\"runTimeEfficiency\"},"
				+ "{ \"header\":\"功图工况\",\"dataIndex\":\"workingConditionName\"},"
				+ "{ \"header\":\"优化建议\",\"dataIndex\":\"optimizationSuggestion\",width:120},"
				+ "{ \"header\":\"产液量(t/d)\",\"dataIndex\":\"liquidProduction\"},"
				+ "{ \"header\":\"产油量(t/d)\",\"dataIndex\":\"oilProduction\"},"
				+ "{ \"header\":\"产水量(t/d)\",\"dataIndex\":\"waterProduction\"},"
				+ "{ \"header\":\"含水率(%)\",\"dataIndex\":\"waterCut\"},"
				+ "{ \"header\":\"充满系数\",\"dataIndex\":\"fullnesscoEfficient\"},"
				+ "{ \"header\":\"功率平衡状态\",\"dataIndex\":\"wattDegreeBalanceLevel\"},"
				+ "{ \"header\":\"功率平衡度(%)\",\"dataIndex\":\"wattDegreeBalance\"},"
				+ "{ \"header\":\"电流平衡状态\",\"dataIndex\":\"iDegreeBalanceLevel\"},"
				+ "{ \"header\":\"电流平衡度(%)\",\"dataIndex\":\"iDegreeBalance\"},"
				+ "{ \"header\":\"移动距离(m)\",\"dataIndex\":\"deltaRadius\"},"
				+ "{ \"header\":\"系统效率(%)\",\"dataIndex\":\"systemEfficiency\"},"
				+ "{ \"header\":\"地面效率(%)\",\"dataIndex\":\"surfaceSystemEfficiency\"},"
				+ "{ \"header\":\"井下效率(%)\",\"dataIndex\":\"welldownSystemEfficiency\"},"
				+ "{ \"header\":\"吨液百米耗电量(kW·h/100·t)\",\"dataIndex\":\"powerConsumptionPerthm\"},"
				+ "{ \"header\":\"日用电量(kW·h)\",\"dataIndex\":\"todayKWattH\"},"
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
			result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[4])+"\",");
			result_json.append("\"commTimeEfficiency\":\""+obj[5]+"\",");
			result_json.append("\"runTime\":\""+obj[6]+"\",");
			result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[7])+"\",");
			result_json.append("\"runTimeEfficiency\":\""+obj[8]+"\",");
			
			result_json.append("\"optimizationSuggestion\":\""+obj[10]+"\",");
			result_json.append("\"liquidProduction\":\""+obj[11]+"\",");
			result_json.append("\"oilProduction\":\""+obj[12]+"\",");
			result_json.append("\"waterProduction\":\""+obj[13]+"\",");
			result_json.append("\"waterCut\":\""+obj[14]+"\",");
			result_json.append("\"systemEfficiency\":\""+obj[21]+"\",");
			result_json.append("\"powerConsumptionPerthm\":\""+obj[24]+"\",");
			result_json.append("\"todayKWattH\":\""+obj[25]+"\",");
			
			result_json.append("\"workingConditionName\":\""+obj[9]+"\",");
			result_json.append("\"fullnesscoEfficient\":\""+obj[15]+"\",");
			result_json.append("\"wattDegreeBalanceLevel\":\""+obj[16]+"\",");
			result_json.append("\"wattDegreeBalance\":\""+obj[17]+"\",");
			result_json.append("\"iDegreeBalanceLevel\":\""+obj[18]+"\",");
			result_json.append("\"iDegreeBalance\":\""+obj[19]+"\",");
			result_json.append("\"deltaRadius\":\""+obj[20]+"\",");
			result_json.append("\"surfaceSystemEfficiency\":\""+obj[22]+"\",");
			result_json.append("\"welldownSystemEfficiency\":\""+obj[23]+"\",");
			result_json.append("\"remark\":\""+obj[26]+"\"},");
		}
		if(list.size()>0){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String exportRPCDailyReportData(Page pager, String orgId,String wellName,String calculateDate,String calculateEndDate)throws Exception {
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		String sql="select t.id, t.wellName,to_char(t.calculateDate,'yyyy-mm-dd') as calculateDate,"
				+ " t.commTime,t.commRange, t.commTimeEfficiency,"
				+ " t.runTime,t.runRange, t.runTimeEfficiency,"
				+ " t.workingConditionName,t.optimizationSuggestion,";
		if(configFile.getOthers().getProductionUnit()==0){
			sql+=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,t.waterCut_W,";
		}else{
			sql+=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,t.waterCut,";
		}
		
		
		sql+= " t.fullnesscoEfficient,"
			+ " t.wattDegreeBalanceLevel,t.wattDegreeBalance,t.iDegreeBalanceLevel,t.iDegreeBalance,t.deltaRadius,"
			+ " t.systemEfficiency,t.surfaceSystemEfficiency,t.welldownSystemEfficiency,t.powerConsumptionPerthm,"
			+ " t.todayKWattH,"
			+ " remark"
			+ " from "
			+ " viw_rpc_total_day t where t.org_id in ("+orgId+") "
			+ " and t.calculateDate between to_date('"+calculateDate+"','yyyy-mm-dd') and to_date('"+calculateEndDate+"','yyyy-mm-dd') ";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  t.wellName='"+wellName+"'";
		}
		sql+=" order by t.sortNum, t.wellName,t.calculateDate";
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(sql);
		
		result_json.append("[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"wellName\":\""+obj[1]+"\",");
			result_json.append("\"calculateDate\":\""+obj[2]+"\",");
			result_json.append("\"commTime\":\""+obj[3]+"\",");
			result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[4])+"\",");
			result_json.append("\"commTimeEfficiency\":\""+obj[5]+"\",");
			result_json.append("\"runTime\":\""+obj[6]+"\",");
			result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[7])+"\",");
			result_json.append("\"runTimeEfficiency\":\""+obj[8]+"\",");
			
			result_json.append("\"optimizationSuggestion\":\""+obj[10]+"\",");
			result_json.append("\"liquidProduction\":\""+obj[11]+"\",");
			result_json.append("\"oilProduction\":\""+obj[12]+"\",");
			result_json.append("\"waterProduction\":\""+obj[13]+"\",");
			result_json.append("\"waterCut\":\""+obj[14]+"\",");
			result_json.append("\"systemEfficiency\":\""+obj[21]+"\",");
			result_json.append("\"powerConsumptionPerthm\":\""+obj[24]+"\",");
			result_json.append("\"todayKWattH\":\""+obj[25]+"\",");
			
			result_json.append("\"workingConditionName\":\""+obj[9]+"\",");
			result_json.append("\"fullnesscoEfficient\":\""+obj[15]+"\",");
			result_json.append("\"wattDegreeBalanceLevel\":\""+obj[16]+"\",");
			result_json.append("\"wattDegreeBalance\":\""+obj[17]+"\",");
			result_json.append("\"iDegreeBalanceLevel\":\""+obj[18]+"\",");
			result_json.append("\"iDegreeBalance\":\""+obj[19]+"\",");
			result_json.append("\"deltaRadius\":\""+obj[20]+"\",");
			result_json.append("\"surfaceSystemEfficiency\":\""+obj[22]+"\",");
			result_json.append("\"welldownSystemEfficiency\":\""+obj[23]+"\",");
			result_json.append("\"remark\":\""+obj[26]+"\"},");
		}
		if(list.size()>0){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String showPCPDailyReportData(Page pager, String orgId,String wellName,String calculateDate,String calculateEndDate)throws Exception {
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		String sql="select t.id, t.wellName,to_char(t.calculateDate,'yyyy-mm-dd') as calculateDate,"
				+ " t.commTime,t.commRange, t.commTimeEfficiency,"
				+ " t.runTime,t.runRange, t.runTimeEfficiency,";
		if(configFile.getOthers().getProductionUnit()==0){
			sql+=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,t.waterCut_W,";
		}else{
			sql+=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,t.waterCut,";
		}
		sql+= " t.rpm,"
				+ " t.pumpSettingDepth,t.producingFluidLevel,t.submergence,"
				+ " t.systemEfficiency,t.powerConsumptionPerthm,"
				+ " t.todayKWattH,"
				+ " remark"
				+ " from viw_pcp_total_day t "
				+ " where t.org_id in ("+orgId+") "
				+ " and t.calculateDate between to_date('"+calculateDate+"','yyyy-mm-dd') and to_date('"+calculateEndDate+"','yyyy-mm-dd')";
		
		
		
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  t.wellName='"+wellName+"'";
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
				+ "{ \"header\":\"运行区间\",\"dataIndex\":\"runRange\"},"
				+ "{ \"header\":\"运行时率(%)\",\"dataIndex\":\"runTimeEfficiency\"},"
				
				+ "{ \"header\":\"产液量(t/d)\",\"dataIndex\":\"liquidProduction\"},"
				+ "{ \"header\":\"产油量(t/d)\",\"dataIndex\":\"oilProduction\"},"
				+ "{ \"header\":\"产水量(t/d)\",\"dataIndex\":\"waterProduction\"},"
				+ "{ \"header\":\"含水率(%)\",\"dataIndex\":\"waterCut\"},"
				+ "{ \"header\":\"转速(r/min)\",\"dataIndex\":\"rpm\"},"
				+ "{ \"header\":\"泵挂(m)\",\"dataIndex\":\"pumpSettingDepth\"},"
				+ "{ \"header\":\"动液面(m)\",\"dataIndex\":\"producingFluidLevel\"},"
				+ "{ \"header\":\"沉没度(m)\",\"dataIndex\":\"submergence\"},"
				
				+ "{ \"header\":\"系统效率(%)\",\"dataIndex\":\"systemEfficiency\"},"
				+ "{ \"header\":\"吨液百米耗电量(kW·h/100·t)\",\"dataIndex\":\"powerConsumptionPerthm\"},"
				+ "{ \"header\":\"日用电量(kW·h)\",\"dataIndex\":\"todayKWattH\"},"
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
			result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[4])+"\",");
			result_json.append("\"commTimeEfficiency\":\""+obj[5]+"\",");
			result_json.append("\"runTime\":\""+obj[6]+"\",");
			result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[7])+"\",");
			result_json.append("\"runTimeEfficiency\":\""+obj[8]+"\",");
		
			result_json.append("\"liquidProduction\":\""+obj[9]+"\",");
			result_json.append("\"oilProduction\":\""+obj[10]+"\",");
			result_json.append("\"waterProduction\":\""+obj[11]+"\",");
			result_json.append("\"waterCut\":\""+obj[12]+"\",");
			result_json.append("\"rpm\":\""+obj[13]+"\",");
			result_json.append("\"pumpSettingDepth\":\""+obj[14]+"\",");
			result_json.append("\"producingFluidLevel\":\""+obj[15]+"\",");
			result_json.append("\"submergence\":\""+obj[16]+"\",");
			
			result_json.append("\"systemEfficiency\":\""+obj[17]+"\",");
			result_json.append("\"powerConsumptionPerthm\":\""+obj[18]+"\",");
			result_json.append("\"todayKWattH\":\""+obj[19]+"\",");
			result_json.append("\"remark\":\""+obj[20]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String exportPCPDailyReportData(Page pager, String orgId,String wellName,String calculateDate,String calculateEndDate)throws Exception {
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		String sql="select t.id, t.wellName,to_char(t.calculateDate,'yyyy-mm-dd') as calculateDate,"
				+ " t.commTime,t.commRange, t.commTimeEfficiency,"
				+ " t.runTime,t.runRange, t.runTimeEfficiency,";
		if(configFile.getOthers().getProductionUnit()==0){
			sql+=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,t.waterCut_W,";
		}else{
			sql+=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,t.waterCut,";
		}
		sql+= " t.rpm,"
				+ " t.pumpSettingDepth,t.producingFluidLevel,t.submergence,"
				+ " t.systemEfficiency,t.powerConsumptionPerthm,"
				+ " t.todayKWattH,"
				+ " remark"
				+ " from viw_pcp_total_day t "
				+ " where t.org_id in ("+orgId+") "
				+ " and t.calculateDate between to_date('"+calculateDate+"','yyyy-mm-dd') and to_date('"+calculateEndDate+"','yyyy-mm-dd')";
		
		
		
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  t.wellName='"+wellName+"'";
		}
		sql+=" order by t.sortNum, t.wellName";
		List<?> list = this.findCallSql(sql);
		
		result_json.append("[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"wellName\":\""+obj[1]+"\",");
			result_json.append("\"calculateDate\":\""+obj[2]+"\",");
			result_json.append("\"commTime\":\""+obj[3]+"\",");
			result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[4])+"\",");
			result_json.append("\"commTimeEfficiency\":\""+obj[5]+"\",");
			result_json.append("\"runTime\":\""+obj[6]+"\",");
			result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[7])+"\",");
			result_json.append("\"runTimeEfficiency\":\""+obj[8]+"\",");
		
			result_json.append("\"liquidProduction\":\""+obj[9]+"\",");
			result_json.append("\"oilProduction\":\""+obj[10]+"\",");
			result_json.append("\"waterProduction\":\""+obj[11]+"\",");
			result_json.append("\"waterCut\":\""+obj[12]+"\",");
			result_json.append("\"rpm\":\""+obj[13]+"\",");
			result_json.append("\"pumpSettingDepth\":\""+obj[14]+"\",");
			result_json.append("\"producingFluidLevel\":\""+obj[15]+"\",");
			result_json.append("\"submergence\":\""+obj[16]+"\",");
			
			result_json.append("\"systemEfficiency\":\""+obj[17]+"\",");
			result_json.append("\"powerConsumptionPerthm\":\""+obj[18]+"\",");
			result_json.append("\"todayKWattH\":\""+obj[19]+"\",");
			result_json.append("\"remark\":\""+obj[20]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getWellList(String orgId,String wellName,String wellType){
		StringBuffer result_json = new StringBuffer();
		String sql="select t.id,t.wellname"
				+ " from tbl_wellinformation t "
				+ " where  t.orgid in ("+orgId+")";
		if(StringManagerUtils.isNotNull(wellType)){
			sql+=" and t.liftingtype>="+wellType+" and t.liftingtype<"+wellType+"+100";
		}
		
		
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and t.wellName='"+wellName+"'";
		}
		sql+=" order by t.sortnum,t.wellname";
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(sql);
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"井名\",\"dataIndex\":\"wellName\" ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"wellName\":\""+obj[1]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
}
