package com.cosog.service.report;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import com.cosog.model.ReportTemplate;
import com.cosog.model.ReportUnitItem;
import com.cosog.service.base.BaseService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Config;
import com.cosog.utils.ConfigFile;
import com.cosog.utils.Page;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.excel.ExcelUtils;
import com.google.gson.Gson;

import net.sf.json.JSONObject;

@Service("reportProductionWellService")
public class ReportDataManagerService<T> extends BaseService<T> {

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

	
	public String getRPCDailyReportData(Page pager, String orgId,String wellName,String startDate,String endDate)throws Exception {
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		String sql="select t.id, t.wellName,to_char(t.caldate,'yyyy-mm-dd') as calDate,"
				+ " t.commTime,t.commRange, t.commTimeEfficiency,"
				+ " t.runTime,t.runRange, t.runTimeEfficiency,"
				+ " t.resultName,t.optimizationSuggestion,";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
			sql+=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,t.weightWaterCut,";
		}else{
			sql+=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,t.volumeWaterCut,";
		}
		sql+= " t.fullnesscoEfficient,"
			+ " t.wattDegreeBalance,t.iDegreeBalance,t.deltaRadius,"
			+ " t.systemEfficiency,t.surfaceSystemEfficiency,t.welldownSystemEfficiency,t.energyPer100mLift,"
			+ " t.todayKWattH,"
			+ " remark"
			+ " from viw_rpcdailycalculationdata t "
			+ " where t.org_id in ("+orgId+") "
			+ " and t.calDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  t.wellName='"+wellName+"'";
		}
		sql+=" order by t.sortNum, t.wellName,t.calDate";
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
				+ "{ \"header\":\"功图工况\",\"dataIndex\":\"resultName\"},"
				+ "{ \"header\":\"优化建议\",\"dataIndex\":\"optimizationSuggestion\",width:120},"
				+ "{ \"header\":\"产液量(t/d)\",\"dataIndex\":\"liquidProduction\"},"
				+ "{ \"header\":\"产油量(t/d)\",\"dataIndex\":\"oilProduction\"},"
				+ "{ \"header\":\"产水量(t/d)\",\"dataIndex\":\"waterProduction\"},"
				+ "{ \"header\":\"含水率(%)\",\"dataIndex\":\"waterCut\"},"
				+ "{ \"header\":\"充满系数\",\"dataIndex\":\"fullnesscoEfficient\"},"
				+ "{ \"header\":\"功率平衡度(%)\",\"dataIndex\":\"wattDegreeBalance\"},"
				+ "{ \"header\":\"电流平衡度(%)\",\"dataIndex\":\"iDegreeBalance\"},"
				+ "{ \"header\":\"移动距离(m)\",\"dataIndex\":\"deltaRadius\"},"
				+ "{ \"header\":\"系统效率(%)\",\"dataIndex\":\"systemEfficiency\"},"
				+ "{ \"header\":\"地面效率(%)\",\"dataIndex\":\"surfaceSystemEfficiency\"},"
				+ "{ \"header\":\"井下效率(%)\",\"dataIndex\":\"welldownSystemEfficiency\"},"
				+ "{ \"header\":\"吨液百米耗电量(kW·h/100·t)\",\"dataIndex\":\"energyPer100mLift\"},"
				+ "{ \"header\":\"日用电量(kW·h)\",\"dataIndex\":\"todayKWattH\"},"
				+ "{ \"header\":\"备注\",\"dataIndex\":\"remark\"}"
				+ "]";
		
		result_json.append("{ \"success\":true,\"wellName\":\""+wellName+"\",\"startDate\":\""+startDate+"\",\"endDate\":\""+endDate+"\",\"columns\":"+columns+",");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"totalRoot\":[");
		float sumCommTime=0;
        float sumRunTime=0;
        float sumLiquidProduction=0;
        float sumOilProduction=0;
        float sumWaterProduction=0;
        
        float averageCommTime=0;
        float averageRunTime=0;
        float averageLiquidProduction=0;
        float averageOilProduction=0;
        float averageWaterProduction=0;
        
        int commTimeRecords=0;
        int runTimeRecords=0;
        int liquidProductionRecords=0;
        int oilProductionRecords=0;
        int waterProductionRecords=0;
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			
			sumCommTime+=StringManagerUtils.stringToFloat(obj[3]+"");
 		   	sumRunTime+=StringManagerUtils.stringToFloat(obj[6]+"");
 		   	sumLiquidProduction+=StringManagerUtils.stringToFloat(obj[11]+"");
 		   	sumOilProduction+=StringManagerUtils.stringToFloat(obj[12]+"");
 		   	sumWaterProduction+=StringManagerUtils.stringToFloat(obj[13]+"");
 		   	
 		   	if(StringManagerUtils.stringToFloat(obj[3]+"")>0){
 		   		commTimeRecords+=1;
 		   	}
 		   	if(StringManagerUtils.stringToFloat(obj[6]+"")>0){
 		   		runTimeRecords+=1;
 		   	}
 		   	if(StringManagerUtils.stringToFloat(obj[11]+"")>0){
 		   		liquidProductionRecords+=1;
 		   	}
 		   	if(StringManagerUtils.stringToFloat(obj[12]+"")>0){
 		   		oilProductionRecords+=1;
 		   	}
 		   	if(StringManagerUtils.stringToFloat(obj[13]+"")>0){
 		   		waterProductionRecords+=1;
 		   	}
    	    result_json.append("{\"id\":"+obj[0]+",");
    	    result_json.append("\"wellName\":\""+obj[1]+"\",");
			result_json.append("\"calculateDate\":\""+obj[2]+"\",");
			result_json.append("\"commTime\":\""+obj[3]+"\",");
			result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[4])+"\",");
			result_json.append("\"commTimeEfficiency\":\""+obj[5]+"\",");
			result_json.append("\"runTime\":\""+obj[6]+"\",");
			result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[7])+"\",");
			result_json.append("\"runTimeEfficiency\":\""+obj[8]+"\",");
			result_json.append("\"resultName\":\""+obj[9]+"\",");
			result_json.append("\"optimizationSuggestion\":\""+obj[10]+"\",");
			result_json.append("\"liquidProduction\":\""+obj[11]+"\",");
			result_json.append("\"oilProduction\":\""+obj[12]+"\",");
			result_json.append("\"waterProduction\":\""+obj[13]+"\",");
			result_json.append("\"waterCut\":\""+obj[14]+"\",");
			result_json.append("\"fullnesscoEfficient\":\""+obj[15]+"\",");
			result_json.append("\"wattDegreeBalance\":\""+obj[16]+"\",");
			result_json.append("\"iDegreeBalance\":\""+obj[17]+"\",");
			result_json.append("\"deltaRadius\":\""+obj[18]+"\",");
			result_json.append("\"systemEfficiency\":\""+obj[19]+"\",");
			result_json.append("\"surfaceSystemEfficiency\":\""+obj[20]+"\",");
			result_json.append("\"welldownSystemEfficiency\":\""+obj[21]+"\",");
			result_json.append("\"energyPer100mLift\":\""+obj[22]+"\",");
			result_json.append("\"todayKWattH\":\""+obj[23]+"\",");
			result_json.append("\"remark\":\""+obj[24]+"\"},");
		}
		
		if(commTimeRecords>0){
			averageCommTime=StringManagerUtils.stringToFloat(sumCommTime/commTimeRecords+"",2);
		}
		if(runTimeRecords>0){
			averageRunTime=StringManagerUtils.stringToFloat(sumRunTime/runTimeRecords+"",2);
		}
		if(liquidProductionRecords>0){
			averageLiquidProduction=StringManagerUtils.stringToFloat(sumLiquidProduction/liquidProductionRecords+"",2);
		}
		if(oilProductionRecords>0){
			averageOilProduction=StringManagerUtils.stringToFloat(sumOilProduction/oilProductionRecords+"",2);
		}
		if(waterProductionRecords>0){
			averageWaterProduction=StringManagerUtils.stringToFloat(sumWaterProduction/waterProductionRecords+"",2);
		}
		
		
		result_json.append("{\"id\":\"合计\",");
	    result_json.append("\"wellName\":\"\",");
		result_json.append("\"calculateDate\":\"\",");
		result_json.append("\"commTime\":\""+StringManagerUtils.stringToFloat(sumCommTime+"",2)+"\",");
		result_json.append("\"commRange\":\"\",");
		result_json.append("\"commTimeEfficiency\":\"\",");
		result_json.append("\"runTime\":\""+StringManagerUtils.stringToFloat(sumRunTime+"",2)+"\",");
		result_json.append("\"runRange\":\"\",");
		result_json.append("\"runTimeEfficiency\":\"\",");
		
		result_json.append("\"optimizationSuggestion\":\"\",");
		result_json.append("\"liquidProduction\":\""+StringManagerUtils.stringToFloat(sumLiquidProduction+"",2)+"\",");
		result_json.append("\"oilProduction\":\""+StringManagerUtils.stringToFloat(sumOilProduction+"",2)+"\",");
		result_json.append("\"waterProduction\":\""+StringManagerUtils.stringToFloat(sumWaterProduction+"",2)+"\",");
		result_json.append("\"waterCut\":\"\",");
		result_json.append("\"systemEfficiency\":\"\",");
		result_json.append("\"energyPer100mLift\":\"\",");
		result_json.append("\"todayKWattH\":\"\",");
		
		result_json.append("\"resultName\":\"\",");
		result_json.append("\"fullnesscoEfficient\":\"\",");
		result_json.append("\"wattDegreeBalance\":\"\",");
		result_json.append("\"iDegreeBalance\":\"\",");
		result_json.append("\"deltaRadius\":\"\",");
		result_json.append("\"surfaceSystemEfficiency\":\"\",");
		result_json.append("\"welldownSystemEfficiency\":\"\",");
		result_json.append("\"remark\":\"\"},");
		
		result_json.append("{\"id\":\"平均\",");
	    result_json.append("\"wellName\":\"\",");
		result_json.append("\"calculateDate\":\"\",");
		result_json.append("\"commTime\":\""+averageCommTime+"\",");
		result_json.append("\"commRange\":\"\",");
		result_json.append("\"commTimeEfficiency\":\"\",");
		result_json.append("\"runTime\":\""+averageRunTime+"\",");
		result_json.append("\"runRange\":\"\",");
		result_json.append("\"runTimeEfficiency\":\"\",");
		
		result_json.append("\"optimizationSuggestion\":\"\",");
		result_json.append("\"liquidProduction\":\""+averageLiquidProduction+"\",");
		result_json.append("\"oilProduction\":\""+averageOilProduction+"\",");
		result_json.append("\"waterProduction\":\""+averageWaterProduction+"\",");
		result_json.append("\"waterCut\":\"\",");
		result_json.append("\"systemEfficiency\":\"\",");
		result_json.append("\"energyPer100mLift\":\"\",");
		result_json.append("\"todayKWattH\":\"\",");
		
		result_json.append("\"resultName\":\"\",");
		result_json.append("\"fullnesscoEfficient\":\"\",");
		result_json.append("\"wattDegreeBalance\":\"\",");
		result_json.append("\"iDegreeBalance\":\"\",");
		result_json.append("\"deltaRadius\":\"\",");
		result_json.append("\"surfaceSystemEfficiency\":\"\",");
		result_json.append("\"welldownSystemEfficiency\":\"\",");
		result_json.append("\"remark\":\"\"}");
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String exportRPCDailyReportData(Page pager, String orgId,String wellName,String startDate,String endDate)throws Exception {
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		String sql="select t.id, t.wellName,to_char(t.caldate,'yyyy-mm-dd') as calDate,"
				+ " t.commTime,t.commRange, t.commTimeEfficiency,"
				+ " t.runTime,t.runRange, t.runTimeEfficiency,"
				+ " t.resultName,t.optimizationSuggestion,";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
			sql+=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,t.weightWaterCut,";
		}else{
			sql+=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,t.volumeWaterCut,";
		}
		sql+= " t.fullnesscoEfficient,"
			+ " t.wattDegreeBalance,t.iDegreeBalance,t.deltaRadius,"
			+ " t.systemEfficiency,t.surfaceSystemEfficiency,t.welldownSystemEfficiency,t.energyPer100mLift,"
			+ " t.todayKWattH,"
			+ " remark"
			+ " from viw_rpcdailycalculationdata t "
			+ " where t.org_id in ("+orgId+") "
			+ " and t.calDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  t.wellName='"+wellName+"'";
		}
		sql+=" order by t.sortNum, t.wellName,t.calDate";
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
			result_json.append("\"resultName\":\""+obj[9]+"\",");
			result_json.append("\"optimizationSuggestion\":\""+obj[10]+"\",");
			result_json.append("\"liquidProduction\":\""+obj[11]+"\",");
			result_json.append("\"oilProduction\":\""+obj[12]+"\",");
			result_json.append("\"waterProduction\":\""+obj[13]+"\",");
			result_json.append("\"waterCut\":\""+obj[14]+"\",");
			result_json.append("\"fullnesscoEfficient\":\""+obj[15]+"\",");
			result_json.append("\"wattDegreeBalance\":\""+obj[16]+"\",");
			result_json.append("\"iDegreeBalance\":\""+obj[17]+"\",");
			result_json.append("\"deltaRadius\":\""+obj[18]+"\",");
			result_json.append("\"systemEfficiency\":\""+obj[19]+"\",");
			result_json.append("\"surfaceSystemEfficiency\":\""+obj[20]+"\",");
			result_json.append("\"welldownSystemEfficiency\":\""+obj[21]+"\",");
			result_json.append("\"energyPer100mLift\":\""+obj[22]+"\",");
			result_json.append("\"todayKWattH\":\""+obj[23]+"\",");
			result_json.append("\"remark\":\""+obj[24]+"\"},");
		}
		if(list.size()>0){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public boolean exportRPCDailyReportData(HttpServletResponse response,String fileName,String title,
			Page pager, String orgId,String wellName,String startDate,String endDate)throws Exception {
		try{
			StringBuffer result_json = new StringBuffer();
			ConfigFile configFile=Config.getInstance().configFile;
			int maxvalue=configFile.getAp().getOthers().getExportLimit();
			String productionUnit="t/d"; 
	        if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
	        	productionUnit="t/d"; 
			}else{
				productionUnit="m^3/d"; 
			}
			String sql="select t.id, t.wellName,to_char(t.caldate,'yyyy-mm-dd') as calDate,"
					+ " t.commTime,t.commRange, t.commTimeEfficiency,"
					+ " t.runTime,t.runRange, t.runTimeEfficiency,"
					+ " t.resultName,t.optimizationSuggestion,";
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				sql+=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,t.weightWaterCut,";
			}else{
				sql+=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,t.volumeWaterCut,";
			}
			sql+= " t.fullnesscoEfficient,"
				+ " t.wattDegreeBalance,t.iDegreeBalance,t.deltaRadius,"
				+ " t.systemEfficiency,t.surfaceSystemEfficiency,t.welldownSystemEfficiency,t.energyPer100mLift,"
				+ " t.todayKWattH,"
				+ " remark"
				+ " from viw_rpcdailycalculationdata t "
				+ " where t.org_id in ("+orgId+") "
				+ " and t.calDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')";
			if(StringManagerUtils.isNotNull(wellName)){
				sql+=" and  t.wellName='"+wellName+"'";
			}
			sql+=" order by t.sortNum, t.wellName,t.calDate";
			String finalSql="select a.* from ("+sql+" ) a where  rownum <="+maxvalue;
			
			List<List<Object>> sheetDataList = new ArrayList<>();
			List<Object> titleRow = new ArrayList<>();
			List<Object> headerRow1 = new ArrayList<>();
			List<Object> headerRow2 = new ArrayList<>();
			for(int i=0;i<25;i++){
				if(i==0){
					titleRow.add(title);
				}else{
					titleRow.add(ExcelUtils.COLUMN_MERGE);
				}
			}
			headerRow1.add("序号");
			headerRow1.add("井名");
			headerRow1.add("日期");
			headerRow1.add("通信");
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add("时率");
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add("工况");
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add("产量");
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add("平衡");
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add("效率");
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add("日用电量(kW·h)");
			headerRow1.add("备注");
			
			headerRow2.add(ExcelUtils.ROW_MERGE);
			headerRow2.add(ExcelUtils.ROW_MERGE);
			headerRow2.add(ExcelUtils.ROW_MERGE);
			headerRow2.add("在线时间(h)");
			headerRow2.add("在线区间");
			headerRow2.add("在线时率(小数)");
			headerRow2.add("运行时间(h)");
			headerRow2.add("运行区间");
			headerRow2.add("运行时率(小数)");
			headerRow2.add("功图工况");
			headerRow2.add("优化建议");
			headerRow2.add("产液量("+productionUnit+")");
			headerRow2.add("产油量("+productionUnit+")");
			headerRow2.add("产水量("+productionUnit+")");
			headerRow2.add("含水率(%)");
			headerRow2.add("充满系数(小数)");
			headerRow2.add("功率平衡度(%)");
			headerRow2.add("电流平衡度(%)");
			headerRow2.add("移动距离(cm)");
			headerRow2.add("系统效率(%)");
			headerRow2.add("地面效率(%)");
			headerRow2.add("井下效率(%)");
			headerRow2.add("吨液百米耗电量(kW·h/100·t)");
			headerRow2.add(ExcelUtils.ROW_MERGE);
			headerRow2.add(ExcelUtils.ROW_MERGE);
			
			sheetDataList.add(titleRow);
			sheetDataList.add(headerRow1);
			sheetDataList.add(headerRow2);
			
			List<?> list = this.findCallSql(finalSql);
			Object[] obj=null;
			List<Object> record=null;
			JSONObject jsonObject=null;
			float sumCommTime=0,sumRunTime=0,sumLiquidProduction=0,sumOilProduction=0,sumWaterProduction=0;
	        int commTimeRecords=0,runTimeRecords=0,liquidProductionRecords=0,oilProductionRecords=0,waterProductionRecords=0;
	        float averageCommTime=0,averageRunTime=0,averageLiquidProduction=0,averageOilProduction=0,averageWaterProduction=0;
			for(int i=0;i<list.size();i++){
				obj=(Object[]) list.get(i);
				record = new ArrayList<>();
				result_json = new StringBuffer();
				result_json.append("{\"id\":"+(i+1)+",");
	    	    result_json.append("\"wellName\":\""+obj[1]+"\",");
				result_json.append("\"calculateDate\":\""+obj[2]+"\",");
				result_json.append("\"commTime\":\""+obj[3]+"\",");
				result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[4])+"\",");
				result_json.append("\"commTimeEfficiency\":\""+obj[5]+"\",");
				result_json.append("\"runTime\":\""+obj[6]+"\",");
				result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[7])+"\",");
				result_json.append("\"runTimeEfficiency\":\""+obj[8]+"\",");
				result_json.append("\"resultName\":\""+obj[9]+"\",");
				result_json.append("\"optimizationSuggestion\":\""+obj[10]+"\",");
				result_json.append("\"liquidProduction\":\""+obj[11]+"\",");
				result_json.append("\"oilProduction\":\""+obj[12]+"\",");
				result_json.append("\"waterProduction\":\""+obj[13]+"\",");
				result_json.append("\"waterCut\":\""+obj[14]+"\",");
				result_json.append("\"fullnesscoEfficient\":\""+obj[15]+"\",");
				result_json.append("\"wattDegreeBalance\":\""+obj[16]+"\",");
				result_json.append("\"iDegreeBalance\":\""+obj[17]+"\",");
				result_json.append("\"deltaRadius\":\""+obj[18]+"\",");
				result_json.append("\"systemEfficiency\":\""+obj[19]+"\",");
				result_json.append("\"surfaceSystemEfficiency\":\""+obj[20]+"\",");
				result_json.append("\"welldownSystemEfficiency\":\""+obj[21]+"\",");
				result_json.append("\"energyPer100mLift\":\""+obj[22]+"\",");
				result_json.append("\"todayKWattH\":\""+obj[23]+"\",");
				result_json.append("\"remark\":\""+obj[24]+"\"}");
				
				jsonObject = JSONObject.fromObject(result_json.toString().replaceAll("null", ""));
				record.add(jsonObject.getString("id"));
				record.add(jsonObject.getString("wellName"));
				record.add(jsonObject.getString("calculateDate"));
				record.add(jsonObject.getString("commTime"));
				record.add(jsonObject.getString("commRange"));
				record.add(jsonObject.getString("commTimeEfficiency"));
				record.add(jsonObject.getString("runTime"));
				record.add(jsonObject.getString("runRange"));
				record.add(jsonObject.getString("runTimeEfficiency"));
				record.add(jsonObject.getString("resultName"));
				record.add(jsonObject.getString("optimizationSuggestion"));
				record.add(jsonObject.getString("liquidProduction"));
				record.add(jsonObject.getString("oilProduction"));
				record.add(jsonObject.getString("waterProduction"));
				record.add(jsonObject.getString("waterCut"));
				record.add(jsonObject.getString("fullnesscoEfficient"));
				record.add(jsonObject.getString("wattDegreeBalance"));
				record.add(jsonObject.getString("iDegreeBalance"));
				record.add(jsonObject.getString("deltaRadius"));
				record.add(jsonObject.getString("systemEfficiency"));
				record.add(jsonObject.getString("surfaceSystemEfficiency"));
				record.add(jsonObject.getString("welldownSystemEfficiency"));
				record.add(jsonObject.getString("energyPer100mLift"));
				record.add(jsonObject.getString("todayKWattH"));
				record.add(jsonObject.getString("remark"));
				
				sheetDataList.add(record);
				
				sumCommTime+=StringManagerUtils.stringToFloat(jsonObject.getString("commTime"));
     		   	sumRunTime+=StringManagerUtils.stringToFloat(jsonObject.getString("runTime"));
     		   	sumLiquidProduction+=StringManagerUtils.stringToFloat(jsonObject.getString("liquidProduction"));
     		   	sumOilProduction+=StringManagerUtils.stringToFloat(jsonObject.getString("oilProduction"));
     		   	sumWaterProduction+=StringManagerUtils.stringToFloat(jsonObject.getString("waterProduction"));
         	   
     		   	if(StringManagerUtils.stringToFloat(jsonObject.getString("commTime"))>0){
         		   commTimeRecords+=1;
     		   	}
     		   	if(StringManagerUtils.stringToFloat(jsonObject.getString("runTime"))>0){
         		   runTimeRecords+=1;
     		   	}
     		   	if(StringManagerUtils.stringToFloat(jsonObject.getString("liquidProduction"))>0){
         		   liquidProductionRecords+=1;
     		   	}
     		   	if(StringManagerUtils.stringToFloat(jsonObject.getString("oilProduction"))>0){
         		   oilProductionRecords+=1;
     		   	}
     		   	if(StringManagerUtils.stringToFloat(jsonObject.getString("waterProduction"))>0){
         		   waterProductionRecords+=1;
     		   	}
			}
			
			sumCommTime=StringManagerUtils.stringToFloat(sumCommTime+"",2);
 		   	sumRunTime=StringManagerUtils.stringToFloat(sumRunTime+"",2);
 		   	sumLiquidProduction=StringManagerUtils.stringToFloat(sumLiquidProduction+"",2);
 		   	sumOilProduction=StringManagerUtils.stringToFloat(sumOilProduction+"",2);
 		   	sumWaterProduction=StringManagerUtils.stringToFloat(sumWaterProduction+"",2);
 		   	if(commTimeRecords>0){
			   averageCommTime=StringManagerUtils.stringToFloat(sumCommTime/commTimeRecords+"",2);
 		   	}
 		   	if(runTimeRecords>0){
			   averageRunTime=StringManagerUtils.stringToFloat(sumRunTime/runTimeRecords+"",2);
 		   	}
 		   	if(liquidProductionRecords>0){
			   averageLiquidProduction=StringManagerUtils.stringToFloat(sumLiquidProduction/liquidProductionRecords+"",2);
 		   	}
 		   	if(oilProductionRecords>0){
			   averageOilProduction=StringManagerUtils.stringToFloat(sumOilProduction/oilProductionRecords+"",2);
 		   	}
 		   	if(waterProductionRecords>0){
			   averageWaterProduction=StringManagerUtils.stringToFloat(sumWaterProduction/waterProductionRecords+"",2);
 		   	}
 		   	
 		   	record = new ArrayList<>();
 		   	record.add("合计");
			record.add("");
			record.add("");
			record.add(sumCommTime);
			record.add("");
			record.add("");
			record.add(sumRunTime);
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add(sumLiquidProduction);
			record.add(sumOilProduction);
			record.add(sumWaterProduction);
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			sheetDataList.add(record);
			
			record = new ArrayList<>();
 		   	record.add("平均");
			record.add("");
			record.add("");
			record.add(averageCommTime);
			record.add("");
			record.add("");
			record.add(averageRunTime);
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add(averageLiquidProduction);
			record.add(averageOilProduction);
			record.add(averageWaterProduction);
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			sheetDataList.add(record);
			
			ExcelUtils.exportDataWithTitleAndHead(response, fileName, title, sheetDataList, null, null);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public String showPCPDailyReportData(Page pager, String orgId,String wellName,String startDate,String endDate)throws Exception {
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		String sql="select t.id, t.wellName,to_char(t.calDate,'yyyy-mm-dd') as calDate,"
				+ " t.commTime,t.commRange, t.commTimeEfficiency,"
				+ " t.runTime,t.runRange, t.runTimeEfficiency,";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
			sql+=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,t.weightWaterCut,";
		}else{
			sql+=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,t.volumeWaterCut,";
		}
		sql+= " t.rpm,"
				+ " t.systemEfficiency,t.energyPer100mLift,"
				+ " t.todayKWattH,"
				+ " remark"
				+ " from viw_pcpdailycalculationdata t "
				+ " where t.org_id in ("+orgId+") "
				+ " and t.calDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  t.wellName='"+wellName+"'";
		}
		sql+=" order by t.sortNum, t.wellName,t.calDate";
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(sql);
		
		float sumCommTime=0;
        float sumRunTime=0;
        float sumLiquidProduction=0;
        float sumOilProduction=0;
        float sumWaterProduction=0;
        
        float averageCommTime=0;
        float averageRunTime=0;
        float averageLiquidProduction=0;
        float averageOilProduction=0;
        float averageWaterProduction=0;
        
        int commTimeRecords=0;
        int runTimeRecords=0;
        int liquidProductionRecords=0;
        int oilProductionRecords=0;
        int waterProductionRecords=0;
		
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
				
				
				+ "{ \"header\":\"系统效率(%)\",\"dataIndex\":\"systemEfficiency\"},"
				+ "{ \"header\":\"吨液百米耗电量(kW·h/100·t)\",\"dataIndex\":\"energyPer100mLift\"},"
				+ "{ \"header\":\"日用电量(kW·h)\",\"dataIndex\":\"todayKWattH\"},"
				+ "{ \"header\":\"备注\",\"dataIndex\":\"remark\"}"
				
		        + "]";
		result_json.append("{ \"success\":true,\"wellName\":\""+wellName+"\",\"startDate\":\""+startDate+"\",\"endDate\":\""+endDate+"\",\"columns\":"+columns+",");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			
			sumCommTime+=StringManagerUtils.stringToFloat(obj[3]+"");
 		   	sumRunTime+=StringManagerUtils.stringToFloat(obj[6]+"");
 		   	sumLiquidProduction+=StringManagerUtils.stringToFloat(obj[9]+"");
 		   	sumOilProduction+=StringManagerUtils.stringToFloat(obj[10]+"");
 		   	sumWaterProduction+=StringManagerUtils.stringToFloat(obj[11]+"");
 		   	
 		   	if(StringManagerUtils.stringToFloat(obj[3]+"")>0){
 		   		commTimeRecords+=1;
 		   	}
 		   	if(StringManagerUtils.stringToFloat(obj[6]+"")>0){
 		   		runTimeRecords+=1;
 		   	}
 		   	if(StringManagerUtils.stringToFloat(obj[9]+"")>0){
 		   		liquidProductionRecords+=1;
 		   	}
 		   	if(StringManagerUtils.stringToFloat(obj[10]+"")>0){
 		   		oilProductionRecords+=1;
 		   	}
 		   	if(StringManagerUtils.stringToFloat(obj[11]+"")>0){
 		   		waterProductionRecords+=1;
 		   	}
			
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
			
			result_json.append("\"systemEfficiency\":\""+obj[14]+"\",");
			result_json.append("\"energyPer100mLift\":\""+obj[15]+"\",");
			result_json.append("\"todayKWattH\":\""+obj[16]+"\",");
			result_json.append("\"remark\":\""+obj[17]+"\"},");
		}
		
		if(commTimeRecords>0){
			averageCommTime=StringManagerUtils.stringToFloat(sumCommTime/commTimeRecords+"",2);
		}
		if(runTimeRecords>0){
			averageRunTime=StringManagerUtils.stringToFloat(sumRunTime/runTimeRecords+"",2);
		}
		if(liquidProductionRecords>0){
			averageLiquidProduction=StringManagerUtils.stringToFloat(sumLiquidProduction/liquidProductionRecords+"",2);
		}
		if(oilProductionRecords>0){
			averageOilProduction=StringManagerUtils.stringToFloat(sumOilProduction/oilProductionRecords+"",2);
		}
		if(waterProductionRecords>0){
			averageWaterProduction=StringManagerUtils.stringToFloat(sumWaterProduction/waterProductionRecords+"",2);
		}
		
		result_json.append("{\"id\":\"合计\",");
		result_json.append("\"wellName\":\"\",");
		result_json.append("\"calculateDate\":\"\",");
		result_json.append("\"commTime\":\""+StringManagerUtils.stringToFloat(sumCommTime+"",2)+"\",");
		result_json.append("\"commRange\":\"\",");
		result_json.append("\"commTimeEfficiency\":\"\",");
		result_json.append("\"runTime\":\""+StringManagerUtils.stringToFloat(sumRunTime+"",2)+"\",");
		result_json.append("\"runRange\":\"\",");
		result_json.append("\"runTimeEfficiency\":\"\",");
	
		result_json.append("\"liquidProduction\":\""+StringManagerUtils.stringToFloat(sumLiquidProduction+"",2)+"\",");
		result_json.append("\"oilProduction\":\""+StringManagerUtils.stringToFloat(sumOilProduction+"",2)+"\",");
		result_json.append("\"waterProduction\":\""+StringManagerUtils.stringToFloat(sumWaterProduction+"",2)+"\",");
		result_json.append("\"waterCut\":\"\",");
		result_json.append("\"rpm\":\"\",");
		
		result_json.append("\"systemEfficiency\":\"\",");
		result_json.append("\"energyPer100mLift\":\"\",");
		result_json.append("\"todayKWattH\":\"\",");
		result_json.append("\"remark\":\"\"},");
		
		
		result_json.append("{\"id\":\"平均\",");
		result_json.append("\"wellName\":\"\",");
		result_json.append("\"calculateDate\":\"\",");
		result_json.append("\"commTime\":\""+averageCommTime+"\",");
		result_json.append("\"commRange\":\"\",");
		result_json.append("\"commTimeEfficiency\":\"\",");
		result_json.append("\"runTime\":\""+averageRunTime+"\",");
		result_json.append("\"runRange\":\"\",");
		result_json.append("\"runTimeEfficiency\":\"\",");
	
		result_json.append("\"liquidProduction\":\""+averageLiquidProduction+"\",");
		result_json.append("\"oilProduction\":\""+averageOilProduction+"\",");
		result_json.append("\"waterProduction\":\""+averageWaterProduction+"\",");
		result_json.append("\"waterCut\":\"\",");
		result_json.append("\"rpm\":\"\",");
		
		result_json.append("\"systemEfficiency\":\"\",");
		result_json.append("\"energyPer100mLift\":\"\",");
		result_json.append("\"todayKWattH\":\"\",");
		result_json.append("\"remark\":\"\"}");
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getRPCSingleWellDailyReportData(Page pager, String orgId,String wellId,String wellName,String startDate,String endDate,int userNo)throws Exception {
		StringBuffer result_json = new StringBuffer();
		Gson gson =new Gson();
		ConfigFile configFile=Config.getInstance().configFile;
		String reportTemplateCode="";
		ReportTemplate.Template template=null;
		String reportTemplateCodeSql="select t2.unitcode from tbl_rpcdevice t,tbl_protocolreportinstance t2 where t.reportinstancecode=t2.code and t.id="+wellId+"";
		List<?> reportTemplateCodeList = this.findCallSql(reportTemplateCodeSql);
		if(reportTemplateCodeList.size()>0){
			reportTemplateCode=reportTemplateCodeList.get(0).toString().replaceAll("null", "");
		}
		if(StringManagerUtils.isNotNull(reportTemplateCode)){
			template=MemoryDataManagerTask.getReportTemplateByCode(reportTemplateCode);
		}
		if(template!=null){
			result_json.append("{\"success\":true,\"template\":"+gson.toJson(template)+",\"data\":[");
			
			String reportItemSql="select t.itemname,t.itemcode,t.sort,t.datatype "
					+ " from TBL_REPORT_ITEMS2UNIT_CONF t "
					+ " where t.unitcode='"+reportTemplateCode+"' "
					+ " and t.sort>=0"
					+ " and t.showlevel is null or t.showlevel>(select r.showlevel from tbl_user u,tbl_role r where u.user_type=r.role_level and u.user_no="+userNo+")"
					+ " order by t.sort";
			String reportCurveItemSql="select t.itemname,t.unitcode,t.reportcurve,t.reportcurvecolor,t.datatype "
					+ " from TBL_REPORT_ITEMS2UNIT_CONF t "
					+ " where t.unitcode='"+reportTemplateCode+"' "
					+ " and t.sort>=0"
					+ " and t.reportcurve>0 "
					+ " and t.showlevel is null or t.showlevel>(select r.showlevel from tbl_user u,tbl_role r where u.user_type=r.role_level and u.user_no="+userNo+")"
					+ " order by t.reportcurve";
			List<ReportUnitItem> reportItemList=new ArrayList<ReportUnitItem>();
			List<ReportUnitItem> reportCurveItemList=new ArrayList<ReportUnitItem>();
			List<?> reportItemQuertList = this.findCallSql(reportItemSql);
			List<?> reportCurveItemQuertList = this.findCallSql(reportCurveItemSql);
			for(int i=0;i<reportItemQuertList.size();i++){
				Object[] reportItemObj=(Object[]) reportItemQuertList.get(i);
				ReportUnitItem reportUnitItem=new ReportUnitItem();
				reportUnitItem.setItemName(reportItemObj[0]+"");
				reportUnitItem.setItemCode(reportItemObj[1]+"");
				reportUnitItem.setSort(StringManagerUtils.stringToInteger(reportItemObj[2]+""));
				reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportItemObj[3]+""));
				reportItemList.add(reportUnitItem);
			}
			for(int i=0;i<reportCurveItemQuertList.size();i++){
				Object[] reportCurveItemObj=(Object[]) reportCurveItemQuertList.get(i);
				ReportUnitItem reportUnitItem=new ReportUnitItem();
				reportUnitItem.setItemName(reportCurveItemObj[0]+"");
				reportUnitItem.setItemCode(reportCurveItemObj[1]+"");
				reportUnitItem.setReportCurve(StringManagerUtils.stringToInteger(reportCurveItemObj[2]+""));
				reportUnitItem.setReportCurveColor(reportCurveItemObj[3]+"");
				reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportCurveItemObj[4]+""));
				reportCurveItemList.add(reportUnitItem);
			}
			
			StringBuffer sqlBuff = new StringBuffer();
			StringBuffer cueveSqlBuff = new StringBuffer();
			sqlBuff.append("select id");
			cueveSqlBuff.append("select id");
			for(int i=0;i<reportItemList.size();i++){
				if(reportItemList.get(i).getDataType()==3){
					sqlBuff.append(",to_char(t."+reportItemList.get(i).getItemCode()+",'yyyy-mm-dd hh24:mi:ss') as "+reportItemList.get(i).getItemCode()+"");
				}else if(reportItemList.get(i).getDataType()==4){
					sqlBuff.append(",to_char(t."+reportItemList.get(i).getItemCode()+",'yyyy-mm-dd hh24:mi:ss') as "+reportItemList.get(i).getItemCode()+"");
				}else{
					sqlBuff.append(","+reportItemList.get(i).getItemCode()+"");
				}
			}
			sqlBuff.append(" from VIW_RPCDAILYCALCULATIONDATA t where t.org_id in ("+orgId+") and t.wellid="+wellId+" ");
			sqlBuff.append(" and t.calDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')");
			sqlBuff.append(" order by t.calDate");
			
			for(int i=0;i<reportCurveItemList.size();i++){
				cueveSqlBuff.append(","+reportItemList.get(i).getItemCode()+"");
			}
			cueveSqlBuff.append(" from VIW_RPCDAILYCALCULATIONDATA t where t.org_id in ("+orgId+") and t.wellid="+wellId+" ");
			cueveSqlBuff.append(" and t.calDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')");
			cueveSqlBuff.append(" order by t.calDate");
			
			List<?> reportDataList = this.findCallSql(sqlBuff.toString());
			List<?> reportCurveDataList = this.findCallSql(cueveSqlBuff.toString());
			
			result_json.append("]}");
		}else{
			result_json.append("{\"success\":false,\"template\":{},\"data\":[]}");
		}
		
		
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getPCPSingleWellDailyReportData(Page pager, String orgId,String wellId,String wellName,String startDate,String endDate,int userNo)throws Exception {
		StringBuffer result_json = new StringBuffer();
		Gson gson =new Gson();
		ConfigFile configFile=Config.getInstance().configFile;
		String reportTemplateCode="";
		ReportTemplate.Template template=null;
		String reportTemplateCodeSql="select t2.unitcode from tbl_rpcdevice t,tbl_protocolreportinstance t2 where t.reportinstancecode=t2.code and t.id="+wellId+"";
		List<?> reportTemplateCodeList = this.findCallSql(reportTemplateCodeSql);
		if(reportTemplateCodeList.size()>0){
			reportTemplateCode=reportTemplateCodeList.get(0).toString().replaceAll("null", "");
		}
		if(StringManagerUtils.isNotNull(reportTemplateCode)){
			template=MemoryDataManagerTask.getReportTemplateByCode(reportTemplateCode);
		}
		if(template!=null){
			result_json.append("{\"success\":true,\"template\":"+gson.toJson(template)+",\"data\":[");
			
			String reportItemSql="select t.itemname,t.itemcode,t.sort,t.datatype "
					+ " from TBL_REPORT_ITEMS2UNIT_CONF t "
					+ " where t.unitcode='"+reportTemplateCode+"' "
					+ " and t.sort>=0"
					+ " and t.showlevel is null or t.showlevel>(select r.showlevel from tbl_user u,tbl_role r where u.user_type=r.role_level and u.user_no="+userNo+")"
					+ " order by t.sort";
			String reportCurveItemSql="select t.itemname,t.unitcode,t.reportcurve,t.reportcurvecolor,t.datatype "
					+ " from TBL_REPORT_ITEMS2UNIT_CONF t "
					+ " where t.unitcode='"+reportTemplateCode+"' "
					+ " and t.sort>=0"
					+ " and t.reportcurve>0 "
					+ " and t.showlevel is null or t.showlevel>(select r.showlevel from tbl_user u,tbl_role r where u.user_type=r.role_level and u.user_no="+userNo+")"
					+ " order by t.reportcurve";
			List<ReportUnitItem> reportItemList=new ArrayList<ReportUnitItem>();
			List<ReportUnitItem> reportCurveItemList=new ArrayList<ReportUnitItem>();
			List<?> reportItemQuertList = this.findCallSql(reportItemSql);
			List<?> reportCurveItemQuertList = this.findCallSql(reportCurveItemSql);
			for(int i=0;i<reportItemQuertList.size();i++){
				Object[] reportItemObj=(Object[]) reportItemQuertList.get(i);
				ReportUnitItem reportUnitItem=new ReportUnitItem();
				reportUnitItem.setItemName(reportItemObj[0]+"");
				reportUnitItem.setItemCode(reportItemObj[1]+"");
				reportUnitItem.setSort(StringManagerUtils.stringToInteger(reportItemObj[2]+""));
				reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportItemObj[3]+""));
				reportItemList.add(reportUnitItem);
			}
			for(int i=0;i<reportCurveItemQuertList.size();i++){
				Object[] reportCurveItemObj=(Object[]) reportCurveItemQuertList.get(i);
				ReportUnitItem reportUnitItem=new ReportUnitItem();
				reportUnitItem.setItemName(reportCurveItemObj[0]+"");
				reportUnitItem.setItemCode(reportCurveItemObj[1]+"");
				reportUnitItem.setReportCurve(StringManagerUtils.stringToInteger(reportCurveItemObj[2]+""));
				reportUnitItem.setReportCurveColor(reportCurveItemObj[3]+"");
				reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportCurveItemObj[4]+""));
				reportCurveItemList.add(reportUnitItem);
			}
			
			StringBuffer sqlBuff = new StringBuffer();
			StringBuffer cueveSqlBuff = new StringBuffer();
			sqlBuff.append("select id");
			cueveSqlBuff.append("select id");
			for(int i=0;i<reportItemList.size();i++){
				if(reportItemList.get(i).getDataType()==3){
					sqlBuff.append(",to_char(t."+reportItemList.get(i).getItemCode()+",'yyyy-mm-dd hh24:mi:ss') as "+reportItemList.get(i).getItemCode()+"");
				}else if(reportItemList.get(i).getDataType()==4){
					sqlBuff.append(",to_char(t."+reportItemList.get(i).getItemCode()+",'yyyy-mm-dd hh24:mi:ss') as "+reportItemList.get(i).getItemCode()+"");
				}else{
					sqlBuff.append(","+reportItemList.get(i).getItemCode()+"");
				}
			}
			sqlBuff.append(" from VIW_PCPDAILYCALCULATIONDATA t where t.org_id in ("+orgId+") and t.wellid="+wellId+" ");
			sqlBuff.append(" and t.calDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')");
			sqlBuff.append(" order by t.calDate");
			
			for(int i=0;i<reportCurveItemList.size();i++){
				cueveSqlBuff.append(","+reportItemList.get(i).getItemCode()+"");
			}
			cueveSqlBuff.append(" from VIW_PCPDAILYCALCULATIONDATA t where t.org_id in ("+orgId+") and t.wellid="+wellId+" ");
			cueveSqlBuff.append(" and t.calDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')");
			cueveSqlBuff.append(" order by t.calDate");
			
			List<?> reportDataList = this.findCallSql(sqlBuff.toString());
			List<?> reportCurveDataList = this.findCallSql(cueveSqlBuff.toString());
			
			result_json.append("]}");
		}else{
			result_json.append("{\"success\":true,\"template\":{},\"data\":[]}");
		}
		
		
		return result_json.toString().replaceAll("null", "");
	}
	
	public String exportPCPDailyReportData(Page pager, String orgId,String wellName,String startDate,String endDate)throws Exception {
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		String sql="select t.id, t.wellName,to_char(t.calDate,'yyyy-mm-dd') as calDate,"
				+ " t.commTime,t.commRange, t.commTimeEfficiency,"
				+ " t.runTime,t.runRange, t.runTimeEfficiency,";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
			sql+=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,t.weightWaterCut,";
		}else{
			sql+=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,t.volumeWaterCut,";
		}
		sql+= " t.rpm,"
				+ " t.systemEfficiency,t.energyPer100mLift,"
				+ " t.todayKWattH,"
				+ " remark"
				+ " from viw_pcpdailycalculationdata t "
				+ " where t.org_id in ("+orgId+") "
				+ " and t.calDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  t.wellName='"+wellName+"'";
		}
		sql+=" order by t.sortNum, t.wellName,t.calDate";
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
			
			result_json.append("\"systemEfficiency\":\""+obj[14]+"\",");
			result_json.append("\"energyPer100mLift\":\""+obj[15]+"\",");
			result_json.append("\"todayKWattH\":\""+obj[16]+"\",");
			result_json.append("\"remark\":\""+obj[17]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public boolean exportPCPDailyReportData(HttpServletResponse response,String fileName,String title,
			Page pager, String orgId,String wellName,String startDate,String endDate)throws Exception {
		try{
			StringBuffer result_json = new StringBuffer();
			ConfigFile configFile=Config.getInstance().configFile;
			int maxvalue=configFile.getAp().getOthers().getExportLimit();
			String productionUnit="t/d"; 
	        if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
	        	productionUnit="t/d"; 
			}else{
				productionUnit="m^3/d"; 
			}
	        String sql="select t.id, t.wellName,to_char(t.calDate,'yyyy-mm-dd') as calDate,"
					+ " t.commTime,t.commRange, t.commTimeEfficiency,"
					+ " t.runTime,t.runRange, t.runTimeEfficiency,";
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				sql+=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,t.weightWaterCut,";
			}else{
				sql+=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,t.volumeWaterCut,";
			}
			sql+= " t.rpm,"
					+ " t.systemEfficiency,t.energyPer100mLift,"
					+ " t.todayKWattH,"
					+ " remark"
					+ " from viw_pcpdailycalculationdata t "
					+ " where t.org_id in ("+orgId+") "
					+ " and t.calDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')";
			if(StringManagerUtils.isNotNull(wellName)){
				sql+=" and  t.wellName='"+wellName+"'";
			}
			sql+=" order by t.sortNum, t.wellName,t.calDate";
			String finalSql="select a.* from ("+sql+" ) a where  rownum <="+maxvalue;
			
			List<List<Object>> sheetDataList = new ArrayList<>();
			List<Object> titleRow = new ArrayList<>();
			List<Object> headerRow1 = new ArrayList<>();
			List<Object> headerRow2 = new ArrayList<>();
			for(int i=0;i<18;i++){
				if(i==0){
					titleRow.add(title);
				}else{
					titleRow.add(ExcelUtils.COLUMN_MERGE);
				}
			}
			headerRow1.add("序号");
			headerRow1.add("井名");
			headerRow1.add("日期");
			headerRow1.add("通信");
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add("时率");
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add("产量");
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add("效率");
			headerRow1.add(ExcelUtils.COLUMN_MERGE);
			headerRow1.add("日用电量(kW·h)");
			headerRow1.add("备注");
			
			headerRow2.add(ExcelUtils.ROW_MERGE);
			headerRow2.add(ExcelUtils.ROW_MERGE);
			headerRow2.add(ExcelUtils.ROW_MERGE);
			headerRow2.add("在线时间(h)");
			headerRow2.add("在线区间");
			headerRow2.add("在线时率(小数)");
			headerRow2.add("运行时间(h)");
			headerRow2.add("运行区间");
			headerRow2.add("运行时率(小数)");
			headerRow2.add("产液量("+productionUnit+")");
			headerRow2.add("产油量("+productionUnit+")");
			headerRow2.add("产水量("+productionUnit+")");
			headerRow2.add("含水率(%)");
			headerRow2.add("转速(r/min)");
			headerRow2.add("系统效率(%)");
			headerRow2.add("吨液百米耗电量(kW·h/100·t)");
			headerRow2.add(ExcelUtils.ROW_MERGE);
			headerRow2.add(ExcelUtils.ROW_MERGE);
			
			sheetDataList.add(titleRow);
			sheetDataList.add(headerRow1);
			sheetDataList.add(headerRow2);
			
			List<?> list = this.findCallSql(finalSql);
			Object[] obj=null;
			List<Object> record=null;
			JSONObject jsonObject=null;
			float sumCommTime=0,sumRunTime=0,sumLiquidProduction=0,sumOilProduction=0,sumWaterProduction=0;
	        int commTimeRecords=0,runTimeRecords=0,liquidProductionRecords=0,oilProductionRecords=0,waterProductionRecords=0;
	        float averageCommTime=0,averageRunTime=0,averageLiquidProduction=0,averageOilProduction=0,averageWaterProduction=0;
			for(int i=0;i<list.size();i++){
				obj=(Object[]) list.get(i);
				record = new ArrayList<>();
				result_json = new StringBuffer();
				result_json.append("{\"id\":"+(i+1)+",");
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
				
				result_json.append("\"systemEfficiency\":\""+obj[14]+"\",");
				result_json.append("\"energyPer100mLift\":\""+obj[15]+"\",");
				result_json.append("\"todayKWattH\":\""+obj[16]+"\",");
				result_json.append("\"remark\":\""+obj[17]+"\"}");
				
				jsonObject = JSONObject.fromObject(result_json.toString().replaceAll("null", ""));
				record.add(jsonObject.getString("id"));
				record.add(jsonObject.getString("wellName"));
				record.add(jsonObject.getString("calculateDate"));
				record.add(jsonObject.getString("commTime"));
				record.add(jsonObject.getString("commRange"));
				record.add(jsonObject.getString("commTimeEfficiency"));
				record.add(jsonObject.getString("runTime"));
				record.add(jsonObject.getString("runRange"));
				record.add(jsonObject.getString("runTimeEfficiency"));
				record.add(jsonObject.getString("liquidProduction"));
				record.add(jsonObject.getString("oilProduction"));
				record.add(jsonObject.getString("waterProduction"));
				record.add(jsonObject.getString("waterCut"));
				record.add(jsonObject.getString("rpm"));
				record.add(jsonObject.getString("systemEfficiency"));
				record.add(jsonObject.getString("energyPer100mLift"));
				record.add(jsonObject.getString("todayKWattH"));
				record.add(jsonObject.getString("remark"));
				
				sheetDataList.add(record);
				
				sumCommTime+=StringManagerUtils.stringToFloat(jsonObject.getString("commTime"));
     		   	sumRunTime+=StringManagerUtils.stringToFloat(jsonObject.getString("runTime"));
     		   	sumLiquidProduction+=StringManagerUtils.stringToFloat(jsonObject.getString("liquidProduction"));
     		   	sumOilProduction+=StringManagerUtils.stringToFloat(jsonObject.getString("oilProduction"));
     		   	sumWaterProduction+=StringManagerUtils.stringToFloat(jsonObject.getString("waterProduction"));
         	   
     		   	if(StringManagerUtils.stringToFloat(jsonObject.getString("commTime"))>0){
         		   commTimeRecords+=1;
     		   	}
     		   	if(StringManagerUtils.stringToFloat(jsonObject.getString("runTime"))>0){
         		   runTimeRecords+=1;
     		   	}
     		   	if(StringManagerUtils.stringToFloat(jsonObject.getString("liquidProduction"))>0){
         		   liquidProductionRecords+=1;
     		   	}
     		   	if(StringManagerUtils.stringToFloat(jsonObject.getString("oilProduction"))>0){
         		   oilProductionRecords+=1;
     		   	}
     		   	if(StringManagerUtils.stringToFloat(jsonObject.getString("waterProduction"))>0){
         		   waterProductionRecords+=1;
     		   	}
			}
			
			sumCommTime=StringManagerUtils.stringToFloat(sumCommTime+"",2);
 		   	sumRunTime=StringManagerUtils.stringToFloat(sumRunTime+"",2);
 		   	sumLiquidProduction=StringManagerUtils.stringToFloat(sumLiquidProduction+"",2);
 		   	sumOilProduction=StringManagerUtils.stringToFloat(sumOilProduction+"",2);
 		   	sumWaterProduction=StringManagerUtils.stringToFloat(sumWaterProduction+"",2);
 		   	if(commTimeRecords>0){
			   averageCommTime=StringManagerUtils.stringToFloat(sumCommTime/commTimeRecords+"",2);
 		   	}
 		   	if(runTimeRecords>0){
			   averageRunTime=StringManagerUtils.stringToFloat(sumRunTime/runTimeRecords+"",2);
 		   	}
 		   	if(liquidProductionRecords>0){
			   averageLiquidProduction=StringManagerUtils.stringToFloat(sumLiquidProduction/liquidProductionRecords+"",2);
 		   	}
 		   	if(oilProductionRecords>0){
			   averageOilProduction=StringManagerUtils.stringToFloat(sumOilProduction/oilProductionRecords+"",2);
 		   	}
 		   	if(waterProductionRecords>0){
			   averageWaterProduction=StringManagerUtils.stringToFloat(sumWaterProduction/waterProductionRecords+"",2);
 		   	}
 		   	
 		   	record = new ArrayList<>();
 		   	record.add("合计");
			record.add("");
			record.add("");
			record.add(sumCommTime);
			record.add("");
			record.add("");
			record.add(sumRunTime);
			record.add("");
			record.add("");
			record.add(sumLiquidProduction);
			record.add(sumOilProduction);
			record.add(sumWaterProduction);
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			sheetDataList.add(record);
			
			record = new ArrayList<>();
 		   	record.add("平均");
			record.add("");
			record.add("");
			record.add(averageCommTime);
			record.add("");
			record.add("");
			record.add(averageRunTime);
			record.add("");
			record.add("");
			record.add(averageLiquidProduction);
			record.add(averageOilProduction);
			record.add(averageWaterProduction);
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			sheetDataList.add(record);
			
			ExcelUtils.exportDataWithTitleAndHead(response, fileName, title, sheetDataList, null, null);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public String getWellList(String orgId,String wellName,String deviceType){
		StringBuffer result_json = new StringBuffer();
		String tableName="tbl_rpcdevice";
		if(StringManagerUtils.stringToInteger(deviceType)!=0){
			tableName="tbl_pcpdevice";
		}
		String sql="select t.id,t.wellname"
				+ " from "+tableName+" t "
				+ " where  t.orgid in ("+orgId+")";
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
