package com.cosog.service.report;

import java.io.File;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

import com.cosog.model.CellEditData;
import com.cosog.model.CurveConf;
import com.cosog.model.DataMapping;
import com.cosog.model.KeyValue;
import com.cosog.model.ReportTemplate;
import com.cosog.model.ReportUnitItem;
import com.cosog.model.User;
import com.cosog.model.ReportTemplate.Template;
import com.cosog.model.calculate.DisplayInstanceOwnItem;
import com.cosog.model.gridmodel.GraphicSetData;
import com.cosog.model.gridmodel.GraphicSetData.Graphic;
import com.cosog.service.base.BaseService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.AcquisitionItemColumnsMap;
import com.cosog.utils.Config;
import com.cosog.utils.ConfigFile;
import com.cosog.utils.Page;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.excel.ExcelUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONObject;
import oracle.sql.CLOB;

@Service("reportProductionWellService")
public class ReportDataManagerService<T> extends BaseService<T> {

	// 得到总行数
	public Integer getTotalRows(String orgId) {
		//String orgIds = this.getUserOrgIds(orgId);
		String hql = " select v.jh From ReportProductionWell as v ,Org as o where 1=1 and v.dwbh=o.orgCode and o.orgId in("
				+ orgId + ")";
		return this.getBaseDao().getRecordCountRows(hql);
	}
	
	public String getSingleWellRangeReportData(Page pager, String orgId,String deviceType,String reportType,String deviceId,
			String deviceName,String calculateType,
			String startDate,String endDate,int userNo,String language)throws Exception {
		StringBuffer result_json = new StringBuffer();
		Gson gson =new Gson();
		java.lang.reflect.Type type=null;
		String reportTemplateCode="";
		String reportUnitId="";
		int reportUnitCalculateType=0;
		String deviceTableName="tbl_device";
		String viewName="VIW_DAILYCALCULATIONDATA";
		String calTotalTableName="";
		ReportTemplate.Template template=null;
		
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
		int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
		String timeEfficiencyUnit=languageResourceMap.get("decimals");
		int timeEfficiencyZoom=1;
		if(timeEfficiencyUnitType==2){
			timeEfficiencyUnit="%";
			timeEfficiencyZoom=100;
		}
		
		String reportTemplateCodeSql="select t3.id,t3.singleWellRangeReportTemplate,t3.productionreporttemplate,t3.calculateType "
				+ " from "+deviceTableName+" t,tbl_protocolreportinstance t2,tbl_report_unit_conf t3 "
				+ " where t.reportinstancecode=t2.code and t2.unitid=t3.id "
				+ " and t.id="+deviceId;
		List<?> reportTemplateCodeList = this.findCallSql(reportTemplateCodeSql);
		if(reportTemplateCodeList.size()>0){
			Object[] obj=(Object[]) reportTemplateCodeList.get(0);
			reportUnitId=(obj[0]+"").replaceAll("null", "");
			if(StringManagerUtils.stringToInteger(reportType)==0){
				reportTemplateCode=(obj[1]+"").replaceAll("null", "");
			}else{
				reportTemplateCode=(obj[2]+"").replaceAll("null", "");
			}
			reportUnitCalculateType=StringManagerUtils.stringToInteger(obj[3]+"");
		}
		
		if(reportUnitCalculateType==1){
			calTotalTableName="VIW_SRPDAILYCALCULATIONDATA";
		}else if(reportUnitCalculateType==2){
			calTotalTableName="VIW_PCPDAILYCALCULATIONDATA";
		}
		
		
		
		if(StringManagerUtils.isNotNull(reportTemplateCode)){
			if(StringManagerUtils.stringToInteger(reportType)==0){
				template=MemoryDataManagerTask.getSingleWellRangeReportTemplateByCode(reportTemplateCode);
			}else{
				template=MemoryDataManagerTask.getProductionReportTemplateByCode(reportTemplateCode);
			}
		}
		if(template!=null){
			int columnCount=0;
			if("zh_CN".equalsIgnoreCase(language)){
				if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_zh_CN()!=null){
					columnCount=template.getHeader().get(0).getTitle_zh_CN().size();
					for(int i=0;i<template.getHeader().get(0).getTitle_zh_CN().size();i++){
						String header=template.getHeader().get(0).getTitle_zh_CN().get(i);
						if(StringManagerUtils.isNotNull(header)){
							template.getHeader().get(0).getTitle_zh_CN().set(i, header.replaceAll("deviceNameLabel", deviceName));
						}
					}
				}
			}else if("en".equalsIgnoreCase(language)){
				if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_en()!=null){
					columnCount=template.getHeader().get(0).getTitle_en().size();
					for(int i=0;i<template.getHeader().get(0).getTitle_en().size();i++){
						String header=template.getHeader().get(0).getTitle_en().get(i);
						if(StringManagerUtils.isNotNull(header)){
							template.getHeader().get(0).getTitle_en().set(i, header.replaceAll("deviceNameLabel", deviceName));
						}
					}
				}
			}else if("ru".equalsIgnoreCase(language)){
				if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_ru()!=null){
					columnCount=template.getHeader().get(0).getTitle_ru().size();
					for(int i=0;i<template.getHeader().get(0).getTitle_ru().size();i++){
						String header=template.getHeader().get(0).getTitle_ru().get(i);
						if(StringManagerUtils.isNotNull(header)){
							template.getHeader().get(0).getTitle_ru().set(i, header.replaceAll("deviceNameLabel", deviceName));
						}
					}
				}
			}
			
			
			
			if(template.getHeader().size()>0){
				String labelInfoStr="";
				String[] labelInfoArr=null;
				String labelInfoSql="select t.headerlabelinfo from TBL_DAILYCALCULATIONDATA t "
						+ " where t.deviceId="+deviceId+" "
						+ " and t.caldate=( select max(t2.caldate) from TBL_DAILYCALCULATIONDATA t2 where t2.deviceId=t.deviceId and t2.headerLabelInfo is not null)";
				List<?> labelInfoList = this.findCallSql(labelInfoSql);
				if(labelInfoList.size()>0){
					labelInfoStr=labelInfoList.get(0).toString();
					if(StringManagerUtils.isNotNull(labelInfoStr)){
						labelInfoArr=labelInfoStr.split(",");
					}
				}
				if(labelInfoArr!=null && labelInfoArr.length>0){
					for(int i=0;i<labelInfoArr.length;i++){
						if("label".equals(labelInfoArr[i])){
							labelInfoArr[i]="";
						}
						for(int j=0;j<template.getHeader().size();j++){
							boolean exit=false;
							
							if("zh_CN".equalsIgnoreCase(language)){
								if(template.getHeader().get(j).getTitle_zh_CN()!=null){
									for(int k=0;k<template.getHeader().get(j).getTitle_zh_CN().size();k++){
										if(template.getHeader().get(j).getTitle_zh_CN().get(k).indexOf("label")>=0){
											template.getHeader().get(j).getTitle_zh_CN().set(k, template.getHeader().get(j).getTitle_zh_CN().get(k).replaceFirst("label", labelInfoArr[i]));
											exit=true;
											break;
										}
									}
								}
							}else if("en".equalsIgnoreCase(language)){
								if(template.getHeader().get(j).getTitle_en()!=null){
									for(int k=0;k<template.getHeader().get(j).getTitle_en().size();k++){
										if(template.getHeader().get(j).getTitle_en().get(k).indexOf("label")>=0){
											template.getHeader().get(j).getTitle_en().set(k, template.getHeader().get(j).getTitle_en().get(k).replaceFirst("label", labelInfoArr[i]));
											exit=true;
											break;
										}
									}
								}
							}else if("ru".equalsIgnoreCase(language)){
								if(template.getHeader().get(j).getTitle_ru()!=null){
									for(int k=0;k<template.getHeader().get(j).getTitle_ru().size();k++){
										if(template.getHeader().get(j).getTitle_ru().get(k).indexOf("label")>=0){
											template.getHeader().get(j).getTitle_ru().set(k, template.getHeader().get(j).getTitle_ru().get(k).replaceFirst("label", labelInfoArr[i]));
											exit=true;
											break;
										}
									}
								}
							}
							
							
							
							if(exit){
								break;
							}
						}
					}
				}
			}
			
			String templateStr=gson.toJson(template).replace("label", "");
			if(timeEfficiencyUnitType==2){
				templateStr=templateStr.replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)");
			}else{
				templateStr=templateStr.replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)");
			}
			result_json.append("{\"success\":true,\"template\":"+templateStr+",");
			
			List<List<String>> dataList=new ArrayList<>();
			String reportItemSql="select t.itemname,t.itemcode,t.sort,t.datatype,t.prec,t.totalType,t.dataSource "
					+ " from TBL_REPORT_ITEMS2UNIT_CONF t "
					+ " where t.unitid="+reportUnitId+" "
					+ " and t.reportType="+reportType
					+ " and t.sort>=0"
					+ " and t.sort<="+columnCount
					+ " and t.showlevel is null or t.showlevel>=(select r.showlevel from tbl_user u,tbl_role r where u.user_type=r.role_level and u.user_no="+userNo+")"
					+ " order by t.sort";
			List<ReportUnitItem> reportAcqItemList=new ArrayList<>();
			List<ReportUnitItem> reportOtherItemList=new ArrayList<>();
			
			List<?> reportItemQuertList = this.findCallSql(reportItemSql);
			for(int i=0;i<reportItemQuertList.size();i++){
				Object[] reportItemObj=(Object[]) reportItemQuertList.get(i);
				ReportUnitItem reportUnitItem=new ReportUnitItem();
				reportUnitItem.setItemName(reportItemObj[0]+"");
				reportUnitItem.setItemCode(reportItemObj[1]+"");
				reportUnitItem.setSort(StringManagerUtils.stringToInteger(reportItemObj[2]+""));
				reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportItemObj[3]+""));
				String precStr=(reportItemObj[4]+"").replaceAll("null", "");
				if(StringManagerUtils.isNumber(precStr)){
					reportUnitItem.setPrec(StringManagerUtils.stringToInteger(precStr));
				}else{
					reportUnitItem.setPrec(-1);
				}
				reportUnitItem.setTotalType(StringManagerUtils.stringToInteger(reportItemObj[5]+""));
				reportUnitItem.setDataSource(reportItemObj[6]+"");
				
				if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("acquisition"), reportUnitItem.getDataSource(), false)){
					reportAcqItemList.add(reportUnitItem);
				}else{
					reportOtherItemList.add(reportUnitItem);
				}
			}
			
			StringBuffer sqlBuff = new StringBuffer();
			sqlBuff.append("select t.id");
			if(reportAcqItemList.size()>0){
				sqlBuff.append(",t.caldata");
			}
			for(int i=0;i<reportOtherItemList.size();i++){
				String tableAlias="t";
				String column=reportOtherItemList.get(i).getItemCode();
				if(reportUnitCalculateType>0){
					if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("calculate"), reportOtherItemList.get(i).getDataSource(), false)){
						if(StringManagerUtils.generalCalColumnFiter(column)){
							tableAlias="t";
						}else{
							tableAlias="t2";
						}
					}
				}
				if(reportOtherItemList.get(i).getDataType()==3){
					sqlBuff.append(",to_char("+tableAlias+"."+column+"@'yyyy-mm-dd') as "+column+"");
				}else if(reportOtherItemList.get(i).getDataType()==4){
					sqlBuff.append(",to_char("+tableAlias+"."+column+"@'hh24:mi') as "+column+"");
				}else if(reportOtherItemList.get(i).getDataType()==2 && reportOtherItemList.get(i).getPrec()>=0){
					if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
						column=column+"*"+timeEfficiencyZoom;
					}
					sqlBuff.append(",round("+tableAlias+"."+column+","+reportOtherItemList.get(i).getPrec()+")");
				}else{
					if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
						column=column+"*"+timeEfficiencyZoom;
					}else if("resultName".equalsIgnoreCase(column)){
						column="resultCode as resultName";
					}else if("optimizationSuggestion".equalsIgnoreCase(column)){
						column="resultCode as optimizationSuggestion";
					}
					sqlBuff.append(","+tableAlias+"."+column+"");
				}
			}
			sqlBuff.append(" from "+viewName+" t ");
			if(reportUnitCalculateType>0){
				sqlBuff.append(","+calTotalTableName+" t2");
				
			}
			sqlBuff.append(" where 1=1");
			
			if(reportUnitCalculateType>0){
				sqlBuff.append(" and t.deviceId=t2.deviceId and t.calDate=t2.calDate");
			}
			
			sqlBuff.append(" and t.org_id in ("+orgId+") and t.deviceId="+deviceId+" ");
			sqlBuff.append(" and t.calDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')");
			sqlBuff.append(" order by t.calDate");
			
			
			
			
			List<String> allColList=new ArrayList<String>();;
			
			List<?> reportDataList = this.findCallSql(sqlBuff.toString().replaceAll("@", ","));
			for(int i=0;i<reportDataList.size();i++){
				Object[] reportDataObj=(Object[]) reportDataList.get(i);
				String recordId=reportDataObj[0]+"";
				List<String> everyDaya=new ArrayList<String>();
				for(int j=0;j<columnCount;j++){
					everyDaya.add("");
				}
				everyDaya.set(0, (i+1)+"");
				everyDaya.add(recordId);
				
				int startIndex=1;
				if(reportAcqItemList.size()>0){
					startIndex=2;
					String calData=StringManagerUtils.CLOBObjectToString(reportDataObj[1]);
					type = new TypeToken<List<KeyValue>>() {}.getType();
					List<KeyValue> calDataList=gson.fromJson(calData, type);
					
					for(ReportUnitItem reportUnitItem:reportAcqItemList){
						String addValue="";
						if(calDataList!=null){
							for(KeyValue keyValue:calDataList){
								if(reportUnitItem.getItemCode().equalsIgnoreCase(keyValue.getKey())){
									addValue=keyValue.getValue();
									break;
								}
							}
						}
						
						if(StringManagerUtils.isNotNull(addValue)){
							String[] totalValueArr=addValue.split(";");
							addValue="";
							if(reportUnitItem.getTotalType()==1 && totalValueArr.length>=1){
								addValue=totalValueArr[0];
							}else if(reportUnitItem.getTotalType()==2 && totalValueArr.length>=2){
								addValue=totalValueArr[1];
							}else if(reportUnitItem.getTotalType()==3 && totalValueArr.length>=3){
								addValue=totalValueArr[2];
							}else if(reportUnitItem.getTotalType()==4 && totalValueArr.length>=4){
								addValue=totalValueArr[3];
							}else if(reportUnitItem.getTotalType()==5 && totalValueArr.length>=5){
								addValue=totalValueArr[4];
							}else if(reportUnitItem.getTotalType()==6 && totalValueArr.length>=6){
								addValue=totalValueArr[5];
							}
						}
						everyDaya.set(reportUnitItem.getSort()-1, addValue);
					}
				}
				
				for(int j=0;j<reportOtherItemList.size();j++){
					if(reportOtherItemList.get(j).getSort()>=1){
						String addValue="";
						String column=reportOtherItemList.get(j).getItemCode();
						if(reportDataObj[j+startIndex] instanceof CLOB || reportDataObj[j+startIndex] instanceof Clob){
							addValue=StringManagerUtils.CLOBObjectToString(reportDataObj[j+startIndex]);
						}else{
							addValue=reportDataObj[j+startIndex]+"";
						}
						
						if("resultName".equalsIgnoreCase(column)){
							addValue=MemoryDataManagerTask.getWorkTypeByCode(addValue,language)!=null?MemoryDataManagerTask.getWorkTypeByCode(addValue,language).getResultName():"";
						}else if("optimizationSuggestion".equalsIgnoreCase(column)){
							addValue=MemoryDataManagerTask.getWorkTypeByCode(addValue,language)!=null?MemoryDataManagerTask.getWorkTypeByCode(addValue,language).getOptimizationSuggestion():"";
						}
						
						everyDaya.set(reportOtherItemList.get(j).getSort()-1, addValue.replaceAll("null", ""));
					}
				}
				dataList.add(everyDaya);
				
				if(allColList.size()==0){
					for(int j=0;j<columnCount;j++){
						allColList.add("\"\"");
					}
					allColList.add("\"recordId\"");
					for(int j=0;j<reportAcqItemList.size();j++){
						if(reportAcqItemList.get(j).getSort()>=1){
							allColList.set(reportAcqItemList.get(j).getSort()-1, "\""+reportAcqItemList.get(j).getItemCode()+"\"");
						}
					}
					for(int j=0;j<reportOtherItemList.size();j++){
						if(reportOtherItemList.get(j).getSort()>=1){
							allColList.set(reportOtherItemList.get(j).getSort()-1, "\""+reportOtherItemList.get(j).getItemCode()+"\"");
						}
					}
				}
			}
			result_json.append("\"data\":"+gson.toJson(dataList)+",\"columns\":"+allColList.toString());
		}else{
			result_json.append("{\"success\":false,\"template\":{},\"data\":[],\"columns\":[]");
		}
		result_json.append(",\"deviceName\":\""+deviceName+"\"");
		result_json.append(",\"startDate\":\""+startDate+"\"");
		result_json.append(",\"endDate\":\""+endDate+"\"");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public boolean exportSingleWellRangeReportData(User user,HttpServletResponse response,
			Page pager,String orgId,String deviceType,String reportType,
			String deviceId,String deviceName,String calculateType,
			String startDate,String endDate,int userNo,String language)throws Exception {
		try{
			StringBuffer result_json = new StringBuffer();
			List<List<Object>> sheetDataList = new ArrayList<>();
			Gson gson =new Gson();
			java.lang.reflect.Type type=null;
			String reportTemplateCode="";
			String reportUnitId="";
			int reportUnitCalculateType=0;
			String deviceTableName="tbl_device";
			String viewName="VIW_DAILYCALCULATIONDATA";
			String calTotalTableName="";
			int headerRowCount=0;
			String title=deviceName+"区间生产报表";
			String fileName=deviceName+"区间生产报表";
			ReportTemplate.Template template=null;
			
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			int timeEfficiencyZoom=1;
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
				timeEfficiencyZoom=100;
			}
			
			String reportTemplateCodeSql="select t3.id,t3.singleWellRangeReportTemplate,t3.productionreporttemplate,t3.calculateType "
					+ " from "+deviceTableName+" t,tbl_protocolreportinstance t2,tbl_report_unit_conf t3 "
					+ " where t.reportinstancecode=t2.code and t2.unitid=t3.id "
					+ " and t.id="+deviceId;
			List<?> reportTemplateCodeList = this.findCallSql(reportTemplateCodeSql);
			if(reportTemplateCodeList.size()>0){
				Object[] obj=(Object[]) reportTemplateCodeList.get(0);
				reportUnitId=(obj[0]+"").replaceAll("null", "");
				if(StringManagerUtils.stringToInteger(reportType)==0){
					reportTemplateCode=(obj[1]+"").replaceAll("null", "");
				}else{
					reportTemplateCode=(obj[2]+"").replaceAll("null", "");
				}
				reportUnitCalculateType=StringManagerUtils.stringToInteger(obj[3]+"");
			}
			
			if(reportUnitCalculateType==1){
				calTotalTableName="VIW_SRPDAILYCALCULATIONDATA";
			}else if(reportUnitCalculateType==2){
				calTotalTableName="VIW_PCPDAILYCALCULATIONDATA";
			}
			if(StringManagerUtils.isNotNull(reportTemplateCode)){
				template=MemoryDataManagerTask.getSingleWellRangeReportTemplateByCode(reportTemplateCode);
			}
			
			
			if(template!=null){
				int columnCount=0;
				headerRowCount=template.getHeader().size();
				if("zh_CN".equalsIgnoreCase(language)){
					if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_zh_CN()!=null){
						columnCount=template.getHeader().get(0).getTitle_zh_CN().size();
						for(int i=0;i<template.getHeader().get(0).getTitle_zh_CN().size();i++){
							String header=template.getHeader().get(0).getTitle_zh_CN().get(i);
							if(StringManagerUtils.isNotNull(header)){
								title=header.replaceAll("deviceNameLabel", deviceName);
							}
						}
					}
				}else if("en".equalsIgnoreCase(language)){
					if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_en()!=null){
						columnCount=template.getHeader().get(0).getTitle_en().size();
						for(int i=0;i<template.getHeader().get(0).getTitle_en().size();i++){
							String header=template.getHeader().get(0).getTitle_en().get(i);
							if(StringManagerUtils.isNotNull(header)){
								title=header.replaceAll("deviceNameLabel", deviceName);
							}
						}
					}
				}else if("ru".equalsIgnoreCase(language)){
					if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_ru()!=null){
						columnCount=template.getHeader().get(0).getTitle_ru().size();
						for(int i=0;i<template.getHeader().get(0).getTitle_ru().size();i++){
							String header=template.getHeader().get(0).getTitle_ru().get(i);
							if(StringManagerUtils.isNotNull(header)){
								title=header.replaceAll("deviceNameLabel", deviceName);
							}
						}
					}
				}
				
				if(template.getHeader().size()>0){
					String labelInfoStr="";
					String[] labelInfoArr=null;
					String labelInfoSql="select t.headerlabelinfo from TBL_DAILYCALCULATIONDATA t "
							+ " where t.deviceId="+deviceId+" "
							+ " and t.caldate=( select max(t2.caldate) from TBL_DAILYCALCULATIONDATA t2 where t2.headerLabelInfo is not null and t2.deviceId="+deviceId+")";
					List<?> labelInfoList = this.findCallSql(labelInfoSql);
					if(labelInfoList.size()>0){
						labelInfoStr=labelInfoList.get(0).toString();
						if(StringManagerUtils.isNotNull(labelInfoStr)){
							labelInfoArr=labelInfoStr.split(",");
						}
					}
					if(labelInfoArr!=null && labelInfoArr.length>0){
						for(int i=0;i<labelInfoArr.length;i++){
							if("label".equals(labelInfoArr[i])){
								labelInfoArr[i]="";
							}
							for(int j=0;j<template.getHeader().size();j++){
								boolean exit=false;
								
								if("zh_CN".equalsIgnoreCase(language)){
									if(template.getHeader().get(j).getTitle_zh_CN()!=null){
										for(int k=0;k<template.getHeader().get(j).getTitle_zh_CN().size();k++){
											if(template.getHeader().get(j).getTitle_zh_CN().get(k).indexOf("label")>=0){
												template.getHeader().get(j).getTitle_zh_CN().set(k, template.getHeader().get(j).getTitle_zh_CN().get(k).replaceFirst("label", labelInfoArr[i]));
												exit=true;
												break;
											}
										}
									}
								}else if("en".equalsIgnoreCase(language)){
									if(template.getHeader().get(j).getTitle_en()!=null){
										for(int k=0;k<template.getHeader().get(j).getTitle_en().size();k++){
											if(template.getHeader().get(j).getTitle_en().get(k).indexOf("label")>=0){
												template.getHeader().get(j).getTitle_en().set(k, template.getHeader().get(j).getTitle_en().get(k).replaceFirst("label", labelInfoArr[i]));
												exit=true;
												break;
											}
										}
									}
								}else if("ru".equalsIgnoreCase(language)){
									if(template.getHeader().get(j).getTitle_ru()!=null){
										for(int k=0;k<template.getHeader().get(j).getTitle_ru().size();k++){
											if(template.getHeader().get(j).getTitle_ru().get(k).indexOf("label")>=0){
												template.getHeader().get(j).getTitle_ru().set(k, template.getHeader().get(j).getTitle_ru().get(k).replaceFirst("label", labelInfoArr[i]));
												exit=true;
												break;
											}
										}
									}
								}
								
								if(exit){
									break;
								}
							}
						}
					}
					for(int j=0;j<template.getHeader().size();j++){
						if("zh_CN".equalsIgnoreCase(language)){
							if(template.getHeader().get(j).getTitle_zh_CN()!=null){
								for(int k=0;k<template.getHeader().get(j).getTitle_zh_CN().size();k++){
									if(template.getHeader().get(j).getTitle_zh_CN().get(k).indexOf("label")>=0){
										template.getHeader().get(j).getTitle_zh_CN().set(k, template.getHeader().get(j).getTitle_zh_CN().get(k).replaceAll("label", ""));
									}
									if(template.getHeader().get(j).getTitle_zh_CN().get(k).indexOf("在线时率")>=0 || template.getHeader().get(j).getTitle_zh_CN().get(k).indexOf("运行时率")>=0){
										if(timeEfficiencyUnitType==2){
											template.getHeader().get(j).getTitle_zh_CN().set(k, template.getHeader().get(j).getTitle_zh_CN().get(k).replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)"));
										}else{
											template.getHeader().get(j).getTitle_zh_CN().set(k, template.getHeader().get(j).getTitle_zh_CN().get(k).replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)"));
										}
									}
								}
							}
						}else if("en".equalsIgnoreCase(language)){
							if(template.getHeader().get(j).getTitle_en()!=null){
								for(int k=0;k<template.getHeader().get(j).getTitle_en().size();k++){
									if(template.getHeader().get(j).getTitle_en().get(k).indexOf("label")>=0){
										template.getHeader().get(j).getTitle_en().set(k, template.getHeader().get(j).getTitle_en().get(k).replaceAll("label", ""));
									}
									if(template.getHeader().get(j).getTitle_en().get(k).indexOf("在线时率")>=0 || template.getHeader().get(j).getTitle_en().get(k).indexOf("运行时率")>=0){
										if(timeEfficiencyUnitType==2){
											template.getHeader().get(j).getTitle_en().set(k, template.getHeader().get(j).getTitle_en().get(k).replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)"));
										}else{
											template.getHeader().get(j).getTitle_en().set(k, template.getHeader().get(j).getTitle_en().get(k).replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)"));
										}
									}
								}
							}
						}else if("ru".equalsIgnoreCase(language)){
							if(template.getHeader().get(j).getTitle_ru()!=null){
								for(int k=0;k<template.getHeader().get(j).getTitle_ru().size();k++){
									if(template.getHeader().get(j).getTitle_ru().get(k).indexOf("label")>=0){
										template.getHeader().get(j).getTitle_ru().set(k, template.getHeader().get(j).getTitle_ru().get(k).replaceAll("label", ""));
									}
									if(template.getHeader().get(j).getTitle_ru().get(k).indexOf("在线时率")>=0 || template.getHeader().get(j).getTitle_ru().get(k).indexOf("运行时率")>=0){
										if(timeEfficiencyUnitType==2){
											template.getHeader().get(j).getTitle_ru().set(k, template.getHeader().get(j).getTitle_ru().get(k).replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)"));
										}else{
											template.getHeader().get(j).getTitle_ru().set(k, template.getHeader().get(j).getTitle_ru().get(k).replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)"));
										}
									}
								}
							}
						}
					}
				}
				fileName="";
				fileName+=title+"-"+startDate;
		        if(!startDate.equalsIgnoreCase(endDate)){
		        	fileName+="~"+endDate;
		        }
				
				List<List<String>> dataList=new ArrayList<>();
				String reportItemSql="select t.itemname,t.itemcode,t.sort,t.datatype,t.prec,t.totalType,t.dataSource "
						+ " from TBL_REPORT_ITEMS2UNIT_CONF t "
						+ " where t.unitid="+reportUnitId+" "
						+ " and t.reportType="+reportType
						+ " and t.sort>=0"
						+ " and t.sort<="+columnCount
						+ " and t.showlevel is null or t.showlevel>=(select r.showlevel from tbl_user u,tbl_role r where u.user_type=r.role_level and u.user_no="+userNo+")"
						+ " order by t.sort";
				List<ReportUnitItem> reportAcqItemList=new ArrayList<>();
				List<ReportUnitItem> reportOtherItemList=new ArrayList<>();
				
				List<?> reportItemQuertList = this.findCallSql(reportItemSql);
				for(int i=0;i<reportItemQuertList.size();i++){
					Object[] reportItemObj=(Object[]) reportItemQuertList.get(i);
					ReportUnitItem reportUnitItem=new ReportUnitItem();
					reportUnitItem.setItemName(reportItemObj[0]+"");
					reportUnitItem.setItemCode(reportItemObj[1]+"");
					reportUnitItem.setSort(StringManagerUtils.stringToInteger(reportItemObj[2]+""));
					reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportItemObj[3]+""));
					String precStr=(reportItemObj[4]+"").replaceAll("null", "");
					if(StringManagerUtils.isNumber(precStr)){
						reportUnitItem.setPrec(StringManagerUtils.stringToInteger(precStr));
					}else{
						reportUnitItem.setPrec(-1);
					}
					reportUnitItem.setTotalType(StringManagerUtils.stringToInteger(reportItemObj[5]+""));
					reportUnitItem.setDataSource(reportItemObj[6]+"");
					
					if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("acquisition"), reportUnitItem.getDataSource(), false)){
						reportAcqItemList.add(reportUnitItem);
					}else{
						reportOtherItemList.add(reportUnitItem);
					}
				}
				
				StringBuffer sqlBuff = new StringBuffer();
				sqlBuff.append("select t.id");
				if(reportAcqItemList.size()>0){
					sqlBuff.append(",t.caldata");
				}
				for(int i=0;i<reportOtherItemList.size();i++){
					String tableAlias="t";
					String column=reportOtherItemList.get(i).getItemCode();
					if(reportUnitCalculateType>0){
						if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("calculate"), reportOtherItemList.get(i).getDataSource(), false)){
							if(StringManagerUtils.generalCalColumnFiter(column)){
								tableAlias="t";
							}else{
								tableAlias="t2";
							}
						}
					}
					if(reportOtherItemList.get(i).getDataType()==3){
						sqlBuff.append(",to_char("+tableAlias+"."+column+"@'yyyy-mm-dd') as "+column+"");
					}else if(reportOtherItemList.get(i).getDataType()==4){
						sqlBuff.append(",to_char("+tableAlias+"."+column+"@'hh24:mi') as "+column+"");
					}else if(reportOtherItemList.get(i).getDataType()==2 && reportOtherItemList.get(i).getPrec()>=0){
						if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
							column=column+"*"+timeEfficiencyZoom;
						}
						sqlBuff.append(",round("+tableAlias+"."+column+","+reportOtherItemList.get(i).getPrec()+")");
					}else{
						if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
							column=column+"*"+timeEfficiencyZoom;
						}else if("resultName".equalsIgnoreCase(column)){
							column="resultCode as resultName";
						}else if("optimizationSuggestion".equalsIgnoreCase(column)){
							column="resultCode as optimizationSuggestion";
						}
						sqlBuff.append(","+tableAlias+"."+column+"");
					}
				}
				sqlBuff.append(" from "+viewName+" t ");
				if(reportUnitCalculateType>0){
					sqlBuff.append(","+calTotalTableName+" t2");
					
				}
				sqlBuff.append(" where 1=1");
				
				if(reportUnitCalculateType>0){
					sqlBuff.append(" and t.deviceId=t2.deviceId and t.calDate=t2.calDate");
				}
				
				sqlBuff.append(" and t.org_id in ("+orgId+") and t.deviceId="+deviceId+" ");
				sqlBuff.append(" and t.calDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')");
				sqlBuff.append(" order by t.calDate");
				
				List<?> reportDataList = this.findCallSql(sqlBuff.toString().replaceAll("@", ","));
				for(int i=0;i<reportDataList.size();i++){
					Object[] reportDataObj=(Object[]) reportDataList.get(i);
					String recordId=reportDataObj[0]+"";
					List<String> everyDaya=new ArrayList<String>();
					for(int j=0;j<columnCount;j++){
						everyDaya.add("");
					}
					everyDaya.set(0, (i+1)+"");
					
					int startIndex=1;
					if(reportAcqItemList.size()>0){
						startIndex=2;
						String calData=StringManagerUtils.CLOBObjectToString(reportDataObj[1]);
						type = new TypeToken<List<KeyValue>>() {}.getType();
						List<KeyValue> calDataList=gson.fromJson(calData, type);
						
						for(ReportUnitItem reportUnitItem:reportAcqItemList){
							String addValue="";
							if(calDataList!=null){
								for(KeyValue keyValue:calDataList){
									if(reportUnitItem.getItemCode().equalsIgnoreCase(keyValue.getKey())){
										addValue=keyValue.getValue();
										break;
									}
								}
							}
							
							if(StringManagerUtils.isNotNull(addValue)){
								String[] totalValueArr=addValue.split(";");
								addValue="";
								if(reportUnitItem.getTotalType()==1 && totalValueArr.length>=1){
									addValue=totalValueArr[0];
								}else if(reportUnitItem.getTotalType()==2 && totalValueArr.length>=2){
									addValue=totalValueArr[1];
								}else if(reportUnitItem.getTotalType()==3 && totalValueArr.length>=3){
									addValue=totalValueArr[2];
								}else if(reportUnitItem.getTotalType()==4 && totalValueArr.length>=4){
									addValue=totalValueArr[3];
								}else if(reportUnitItem.getTotalType()==5 && totalValueArr.length>=5){
									addValue=totalValueArr[4];
								}else if(reportUnitItem.getTotalType()==6 && totalValueArr.length>=6){
									addValue=totalValueArr[5];
								}
							}
							everyDaya.set(reportUnitItem.getSort()-1, addValue);
						}
					}
					
					for(int j=0;j<reportOtherItemList.size();j++){
						if(reportOtherItemList.get(j).getSort()>=1){
							String addValue="";
							String column=reportOtherItemList.get(j).getItemCode();
							if(reportDataObj[j+startIndex] instanceof CLOB || reportDataObj[j+startIndex] instanceof Clob){
								addValue=StringManagerUtils.CLOBObjectToString(reportDataObj[j+startIndex]);
							}else{
								addValue=reportDataObj[j+startIndex]+"";
							}
							if("resultName".equalsIgnoreCase(column)){
								addValue=MemoryDataManagerTask.getWorkTypeByCode(addValue,language)!=null?MemoryDataManagerTask.getWorkTypeByCode(addValue,language).getResultName():"";
							}else if("optimizationSuggestion".equalsIgnoreCase(column)){
								addValue=MemoryDataManagerTask.getWorkTypeByCode(addValue,language)!=null?MemoryDataManagerTask.getWorkTypeByCode(addValue,language).getOptimizationSuggestion():"";
							}
							everyDaya.set(reportOtherItemList.get(j).getSort()-1, addValue.replaceAll("null", ""));
						}
					}
					dataList.add(everyDaya);
				}
				
				for(int i=0;i<template.getHeader().size();i++){
					List<Object> record = new ArrayList<>();
					if("zh_CN".equalsIgnoreCase(language)){
						for(int j=0;j<template.getHeader().get(i).getTitle_zh_CN().size();j++){
							if(i==0 && j==0){
								record.add(title);
							}else{
								record.add(template.getHeader().get(i).getTitle_zh_CN().get(j));
							}
						}
					}else if("en".equalsIgnoreCase(language)){
						for(int j=0;j<template.getHeader().get(i).getTitle_en().size();j++){
							if(i==0 && j==0){
								record.add(title);
							}else{
								record.add(template.getHeader().get(i).getTitle_en().get(j));
							}
						}
					}else if("ru".equalsIgnoreCase(language)){
						for(int j=0;j<template.getHeader().get(i).getTitle_ru().size();j++){
							if(i==0 && j==0){
								record.add(title);
							}else{
								record.add(template.getHeader().get(i).getTitle_ru().get(j));
							}
						}
					}
					
					sheetDataList.add(record);
				}
				for(int i=0;i<dataList.size();i++){
					List<Object> record = new ArrayList<>();
					for(int j=0;j<dataList.get(i).size();j++){
						record.add(dataList.get(i).get(j));
					}
					sheetDataList.add(record);
				}
				if(template.getMergeCells()!=null && template.getMergeCells().size()>0){
					for(int i=0;i<template.getMergeCells().size();i++){
						if(template.getMergeCells().get(i).getRowspan()==1&&template.getMergeCells().get(i).getColspan()>1){
							for(int j=template.getMergeCells().get(i).getCol();j<template.getMergeCells().get(i).getCol()+template.getMergeCells().get(i).getColspan();j++){
								String value=sheetDataList.get(template.getMergeCells().get(i).getRow()).get(j)+"";
								if(!StringManagerUtils.isNotNull(value)){
									sheetDataList.get(template.getMergeCells().get(i).getRow()).set(j, ExcelUtils.COLUMN_MERGE);
								}
							}
						}else if(template.getMergeCells().get(i).getRowspan()>1&&template.getMergeCells().get(i).getColspan()==1){
							for(int j=template.getMergeCells().get(i).getRow();j<template.getMergeCells().get(i).getRow()+template.getMergeCells().get(i).getRowspan();j++){
								String value=sheetDataList.get(j).get(template.getMergeCells().get(i).getCol())+"";
								if(!StringManagerUtils.isNotNull(value)){
									sheetDataList.get(j).set(template.getMergeCells().get(i).getCol(), ExcelUtils.ROW_MERGE);
								}
							}
						}
					}
				}
			}
			ExcelUtils.exportDataWithTitleAndHead(response, fileName, title, sheetDataList, null, null,headerRowCount,template,language);
			if(user!=null){
				saveSystemLog(user,4,"导出文件:"+fileName);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean batchExportSingleWellRangeReportData(User user,HttpServletResponse response,
			Page pager,String orgId,String deviceType,String deviceTypeName,
			String reportType,
			String deviceName,String startDate,String endDate,int userNo,String language)throws Exception {
		try{
			List<List<List<Object>>> sheetList =new ArrayList<>();
			List<String> sheetNameList =new ArrayList<>();
			List<String> titleList =new ArrayList<>();
			List<ReportTemplate.Template> sheetTemplateList=new ArrayList<>();
			
			Gson gson =new Gson();
			java.lang.reflect.Type type=null;
			
			int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
			int interval=Config.getInstance().configFile.getAp().getReport().getInterval();
			String fileName=deviceTypeName+"日报表-"+startDate+"~"+endDate;
			
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			int timeEfficiencyZoom=1;
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
				timeEfficiencyZoom=100;
			}
			
			String deviceTableName="tbl_device";
			String calTotalTableName="";
			
			String wellListSql="select t.id,t.deviceName,t3.id as unitid,t3.singlewellrangereporttemplate,t.calculateType,t3.calculateType as reportUnitCalculateType "
					+ " from "+deviceTableName+" t "
					+ " left outer join tbl_protocolreportinstance t2 on t.reportinstancecode=t2.code"
					+ " left outer join tbl_report_unit_conf t3 on t3.id=t2.unitid"
					+ " where t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNum(deviceType)){
				wellListSql+= " and t.devicetype="+deviceType;
			}else{
				wellListSql+= " and t.devicetype in ("+deviceType+")";
			}
			if(StringManagerUtils.isNotNull(deviceName)){
				wellListSql+=" and t.deviceName='"+deviceName+"'";
			}
			wellListSql+=" order by t.sortnum,t.deviceName";
			
			List<?> wellList = this.findCallSql(wellListSql);
			
			for(int i=0;i<wellList.size();i++){
				Object[] obj=(Object[]) wellList.get(i);
				String deviceId=obj[0]+"";
				deviceName=obj[1]+"";
				String reportUnitId=(obj[2]+"").replaceAll("null", "");
				String reportTemplateCode=(obj[3]+"").replaceAll("null", "");
				int reportUnitCalculateType=StringManagerUtils.stringToInteger(obj[5]+"");
				int calculateType=StringManagerUtils.stringToInteger(obj[4]+"");
				String sheetName=deviceName+StringManagerUtils.timeFormatConverter(startDate, "yyyy-MM-dd", "MM.dd")+"~"+StringManagerUtils.timeFormatConverter(endDate, "yyyy-MM-dd", "MM.dd");
				String title="";
				
				String viewName="VIW_DAILYCALCULATIONDATA";
				calTotalTableName="";
				if(reportUnitCalculateType==1){
					calTotalTableName="VIW_SRPDAILYCALCULATIONDATA";
				}else if(reportUnitCalculateType==2){
					calTotalTableName="VIW_PCPDAILYCALCULATIONDATA";
				}
				
				List<List<Object>> sheetDataList = new ArrayList<>();
				
				
				ReportTemplate.Template template=null;
				
				if(StringManagerUtils.isNotNull(reportTemplateCode)){
					template=MemoryDataManagerTask.getSingleWellRangeReportTemplateByCode(reportTemplateCode);
				}
				
				if(template!=null){
					sheetNameList.add(sheetName);
					sheetTemplateList.add(template);
					
					int columnCount=0;
					
					if("zh_CN".equalsIgnoreCase(language)){
						if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_zh_CN()!=null){
							columnCount=template.getHeader().get(0).getTitle_zh_CN().size();
							for(int j=0;j<template.getHeader().get(0).getTitle_zh_CN().size();j++){
								String header=template.getHeader().get(0).getTitle_zh_CN().get(j);
								if(StringManagerUtils.isNotNull(header)){
									title=header.replaceAll("deviceNameLabel", deviceName);
								}
							}
						}
					}else if("en".equalsIgnoreCase(language)){
						if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_en()!=null){
							columnCount=template.getHeader().get(0).getTitle_en().size();
							for(int j=0;j<template.getHeader().get(0).getTitle_en().size();j++){
								String header=template.getHeader().get(0).getTitle_en().get(j);
								if(StringManagerUtils.isNotNull(header)){
									title=header.replaceAll("deviceNameLabel", deviceName);
								}
							}
						}
					}else if("ru".equalsIgnoreCase(language)){
						if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_ru()!=null){
							columnCount=template.getHeader().get(0).getTitle_ru().size();
							for(int j=0;j<template.getHeader().get(0).getTitle_ru().size();j++){
								String header=template.getHeader().get(0).getTitle_ru().get(j);
								if(StringManagerUtils.isNotNull(header)){
									title=header.replaceAll("deviceNameLabel", deviceName);
								}
							}
						}
					}
					
					
					
					titleList.add(title);
					
					if(template.getHeader().size()>0){
						String labelInfoStr="";
						String[] labelInfoArr=null;
						String labelInfoSql="select t.headerlabelinfo from TBL_DAILYCALCULATIONDATA t "
								+ " where t.deviceId="+deviceId+" "
								+ " and t.caldate=( select max(t2.caldate) from TBL_DAILYCALCULATIONDATA t2 where t2.headerLabelInfo is not null and t2.deviceId="+deviceId+")";
						List<?> labelInfoList = this.findCallSql(labelInfoSql);
						if(labelInfoList.size()>0){
							labelInfoStr=labelInfoList.get(0).toString();
							if(StringManagerUtils.isNotNull(labelInfoStr)){
								labelInfoArr=labelInfoStr.split(",");
							}
						}
						if(labelInfoArr!=null && labelInfoArr.length>0){
							for(int j=0;j<labelInfoArr.length;j++){
								if("label".equals(labelInfoArr[j])){
									labelInfoArr[j]="";
								}
								for(int l=0;l<template.getHeader().size();l++){
									boolean exit=false;
									if("zh_CN".equalsIgnoreCase(language)){
										if(template.getHeader().get(l).getTitle_zh_CN()!=null){
											for(int k=0;k<template.getHeader().get(l).getTitle_zh_CN().size();k++){
												if(template.getHeader().get(l).getTitle_zh_CN().get(k).indexOf("label")>=0){
													template.getHeader().get(l).getTitle_zh_CN().set(k, template.getHeader().get(l).getTitle_zh_CN().get(k).replaceFirst("label", labelInfoArr[j]));
													exit=true;
													break;
												}
											}
										}
									}else if("en".equalsIgnoreCase(language)){
										if(template.getHeader().get(l).getTitle_en()!=null){
											for(int k=0;k<template.getHeader().get(l).getTitle_en().size();k++){
												if(template.getHeader().get(l).getTitle_en().get(k).indexOf("label")>=0){
													template.getHeader().get(l).getTitle_en().set(k, template.getHeader().get(l).getTitle_en().get(k).replaceFirst("label", labelInfoArr[j]));
													exit=true;
													break;
												}
											}
										}
									}else if("ru".equalsIgnoreCase(language)){
										if(template.getHeader().get(l).getTitle_ru()!=null){
											for(int k=0;k<template.getHeader().get(l).getTitle_ru().size();k++){
												if(template.getHeader().get(l).getTitle_ru().get(k).indexOf("label")>=0){
													template.getHeader().get(l).getTitle_ru().set(k, template.getHeader().get(l).getTitle_ru().get(k).replaceFirst("label", labelInfoArr[j]));
													exit=true;
													break;
												}
											}
										}
									}
									
									
									if(exit){
										break;
									}
								}
							}
						}
						for(int j=0;j<template.getHeader().size();j++){
							if("zh_CN".equalsIgnoreCase(language)){
								if(template.getHeader().get(j).getTitle_zh_CN()!=null){
									for(int k=0;k<template.getHeader().get(j).getTitle_zh_CN().size();k++){
										if(template.getHeader().get(j).getTitle_zh_CN().get(k).indexOf("label")>=0){
											template.getHeader().get(j).getTitle_zh_CN().set(k, template.getHeader().get(j).getTitle_zh_CN().get(k).replaceAll("label", ""));
										}
										if(template.getHeader().get(j).getTitle_zh_CN().get(k).indexOf("在线时率")>=0 || template.getHeader().get(j).getTitle_zh_CN().get(k).indexOf("运行时率")>=0){
											if(timeEfficiencyUnitType==2){
												template.getHeader().get(j).getTitle_zh_CN().set(k, template.getHeader().get(j).getTitle_zh_CN().get(k).replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)"));
											}else{
												template.getHeader().get(j).getTitle_zh_CN().set(k, template.getHeader().get(j).getTitle_zh_CN().get(k).replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)"));
											}
										}
									}
								}
							}else if("en".equalsIgnoreCase(language)){
								if(template.getHeader().get(j).getTitle_en()!=null){
									for(int k=0;k<template.getHeader().get(j).getTitle_en().size();k++){
										if(template.getHeader().get(j).getTitle_en().get(k).indexOf("label")>=0){
											template.getHeader().get(j).getTitle_en().set(k, template.getHeader().get(j).getTitle_en().get(k).replaceAll("label", ""));
										}
										if(template.getHeader().get(j).getTitle_en().get(k).indexOf("在线时率")>=0 || template.getHeader().get(j).getTitle_en().get(k).indexOf("运行时率")>=0){
											if(timeEfficiencyUnitType==2){
												template.getHeader().get(j).getTitle_en().set(k, template.getHeader().get(j).getTitle_en().get(k).replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)"));
											}else{
												template.getHeader().get(j).getTitle_en().set(k, template.getHeader().get(j).getTitle_en().get(k).replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)"));
											}
										}
									}
								}
							}else if("ru".equalsIgnoreCase(language)){
								if(template.getHeader().get(j).getTitle_ru()!=null){
									for(int k=0;k<template.getHeader().get(j).getTitle_ru().size();k++){
										if(template.getHeader().get(j).getTitle_ru().get(k).indexOf("label")>=0){
											template.getHeader().get(j).getTitle_ru().set(k, template.getHeader().get(j).getTitle_ru().get(k).replaceAll("label", ""));
										}
										if(template.getHeader().get(j).getTitle_ru().get(k).indexOf("在线时率")>=0 || template.getHeader().get(j).getTitle_ru().get(k).indexOf("运行时率")>=0){
											if(timeEfficiencyUnitType==2){
												template.getHeader().get(j).getTitle_ru().set(k, template.getHeader().get(j).getTitle_ru().get(k).replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)"));
											}else{
												template.getHeader().get(j).getTitle_ru().set(k, template.getHeader().get(j).getTitle_ru().get(k).replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)"));
											}
										}
									}
								}
							}
							
							
						}
					}
					
					List<List<String>> dataList=new ArrayList<>();
					
					String reportItemSql="select t.itemname,t.itemcode,t.sort,t.datatype,t.prec,t.totalType,t.dataSource "
							+ " from TBL_REPORT_ITEMS2UNIT_CONF t "
							+ " where t.unitid="+reportUnitId+" "
							+ " and t.reportType="+reportType
							+ " and t.sort>=0"
							+ " and t.sort<="+columnCount
							+ " and t.showlevel is null or t.showlevel>=(select r.showlevel from tbl_user u,tbl_role r where u.user_type=r.role_level and u.user_no="+userNo+")"
							+ " order by t.sort";
					List<ReportUnitItem> reportAcqItemList=new ArrayList<>();
					List<ReportUnitItem> reportOtherItemList=new ArrayList<>();
					List<?> reportItemQuertList = this.findCallSql(reportItemSql);
					for(int j=0;j<reportItemQuertList.size();j++){
						Object[] reportItemObj=(Object[]) reportItemQuertList.get(j);
						ReportUnitItem reportUnitItem=new ReportUnitItem();
						reportUnitItem.setItemName(reportItemObj[0]+"");
						reportUnitItem.setItemCode(reportItemObj[1]+"");
						reportUnitItem.setSort(StringManagerUtils.stringToInteger(reportItemObj[2]+""));
						reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportItemObj[3]+""));
						String precStr=(reportItemObj[4]+"").replaceAll("null", "");
						if(StringManagerUtils.isNumber(precStr)){
							reportUnitItem.setPrec(StringManagerUtils.stringToInteger(precStr));
						}else{
							reportUnitItem.setPrec(-1);
						}
						reportUnitItem.setTotalType(StringManagerUtils.stringToInteger(reportItemObj[5]+""));
						reportUnitItem.setDataSource(reportItemObj[6]+"");
						
						if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("acquisition"), reportUnitItem.getDataSource(), false)){
							reportAcqItemList.add(reportUnitItem);
						}else{
							reportOtherItemList.add(reportUnitItem);
						}
					}
					
					StringBuffer sqlBuff = new StringBuffer();
					sqlBuff.append("select t.id");
					if(reportAcqItemList.size()>0){
						sqlBuff.append(",t.caldata");
					}
					for(ReportUnitItem reportUnitItem:reportOtherItemList){
						String tableAlias="t";
						String column=reportUnitItem.getItemCode();
						if(reportUnitCalculateType>0){
							if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("calculate"), reportUnitItem.getDataSource(), false)){
								if(StringManagerUtils.generalCalColumnFiter(column)){
									tableAlias="t";
								}else{
									tableAlias="t2";
								}
							}
						}
						if(reportUnitItem.getDataType()==3){
							sqlBuff.append(",to_char("+tableAlias+"."+column+"@'yyyy-mm-dd') as "+column+"");
						}else if(reportUnitItem.getDataType()==4){
							sqlBuff.append(",to_char("+tableAlias+"."+column+"@'hh24:mi') as "+column+"");
						}else if(reportUnitItem.getDataType()==2 && reportUnitItem.getPrec()>=0){
							if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
								column=column+"*"+timeEfficiencyZoom;
							}
							sqlBuff.append(",round("+tableAlias+"."+column+","+reportUnitItem.getPrec()+")");
						}else{
							if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
								column=column+"*"+timeEfficiencyZoom;
							}
							sqlBuff.append(","+tableAlias+"."+column+"");
						}
					}
					sqlBuff.append(" from "+viewName+" t ");
					if(reportUnitCalculateType>0){
						sqlBuff.append(","+calTotalTableName+" t2");
						
					}
					sqlBuff.append(" where 1=1");
					
					if(reportUnitCalculateType>0){
						sqlBuff.append(" and t.deviceId=t2.deviceId and t.calDate=t2.calDate");
					}
					
					sqlBuff.append(" and t.org_id in ("+orgId+") and t.deviceId="+deviceId+" ");
					sqlBuff.append(" and t.calDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')");
					sqlBuff.append(" order by t.calDate");
					
					List<?> reportDataList = this.findCallSql(sqlBuff.toString().replaceAll("@", ","));
					for(int k=0;k<reportDataList.size();k++){
						Object[] reportDataObj=(Object[]) reportDataList.get(k);
						String recordId=reportDataObj[0]+"";
						List<String> everyDaya=new ArrayList<String>();
						for(int j=0;j<columnCount;j++){
							everyDaya.add("");
						}
						everyDaya.set(0, (k+1)+"");
						
						int startIndex=1;
						if(reportAcqItemList.size()>0){
							startIndex=2;
							String calData=StringManagerUtils.CLOBObjectToString(reportDataObj[1]);
							type = new TypeToken<List<KeyValue>>() {}.getType();
							List<KeyValue> calDataList=gson.fromJson(calData, type);
							
							for(ReportUnitItem reportUnitItem:reportAcqItemList){
								String addValue="";
								if(calDataList!=null){
									for(KeyValue keyValue:calDataList){
										if(reportUnitItem.getItemCode().equalsIgnoreCase(keyValue.getKey())){
											addValue=keyValue.getValue();
											break;
										}
									}
								}
								
								if(StringManagerUtils.isNotNull(addValue)){
									String[] totalValueArr=addValue.split(";");
									addValue="";
									if(reportUnitItem.getTotalType()==1 && totalValueArr.length>=1){
										addValue=totalValueArr[0];
									}else if(reportUnitItem.getTotalType()==2 && totalValueArr.length>=2){
										addValue=totalValueArr[1];
									}else if(reportUnitItem.getTotalType()==3 && totalValueArr.length>=3){
										addValue=totalValueArr[2];
									}else if(reportUnitItem.getTotalType()==4 && totalValueArr.length>=4){
										addValue=totalValueArr[3];
									}else if(reportUnitItem.getTotalType()==5 && totalValueArr.length>=5){
										addValue=totalValueArr[4];
									}else if(reportUnitItem.getTotalType()==6 && totalValueArr.length>=6){
										addValue=totalValueArr[5];
									}
								}
								everyDaya.set(reportUnitItem.getSort()-1, addValue);
							}
						}
						
						for(int j=0;j<reportOtherItemList.size();j++){
							if(reportOtherItemList.get(j).getSort()>=1){
								String addValue="";
								if(reportDataObj[j+startIndex] instanceof CLOB || reportDataObj[j+startIndex] instanceof Clob){
									addValue=StringManagerUtils.CLOBObjectToString(reportDataObj[j+startIndex]);
								}else{
									addValue=reportDataObj[j+startIndex]+"";
								}
								everyDaya.set(reportOtherItemList.get(j).getSort()-1, addValue.replaceAll("null", ""));
							}
						}
						dataList.add(everyDaya);
					}
					
					for(int k=0;k<template.getHeader().size();k++){
						List<Object> record = new ArrayList<>();
						if("zh_CN".equalsIgnoreCase(language)){
							for(int j=0;j<template.getHeader().get(k).getTitle_zh_CN().size();j++){
								if(k==0 && j==0){
									record.add(title);
								}else{
									record.add(template.getHeader().get(k).getTitle_zh_CN().get(j));
								}
							}
						}else if("en".equalsIgnoreCase(language)){
							for(int j=0;j<template.getHeader().get(k).getTitle_en().size();j++){
								if(k==0 && j==0){
									record.add(title);
								}else{
									record.add(template.getHeader().get(k).getTitle_en().get(j));
								}
							}
						}else if("ru".equalsIgnoreCase(language)){
							for(int j=0;j<template.getHeader().get(k).getTitle_ru().size();j++){
								if(k==0 && j==0){
									record.add(title);
								}else{
									record.add(template.getHeader().get(k).getTitle_ru().get(j));
								}
							}
						}
						
						sheetDataList.add(record);
					}
					for(int k=0;k<dataList.size();k++){
						List<Object> record = new ArrayList<>();
						for(int j=0;j<dataList.get(k).size();j++){
							record.add(dataList.get(k).get(j));
						}
						sheetDataList.add(record);
					}
					if(template.getMergeCells()!=null && template.getMergeCells().size()>0){
						for(int k=0;k<template.getMergeCells().size();k++){
							if(template.getMergeCells().get(k).getRowspan()==1&&template.getMergeCells().get(k).getColspan()>1){
								for(int j=template.getMergeCells().get(k).getCol();j<template.getMergeCells().get(k).getCol()+template.getMergeCells().get(k).getColspan();j++){
									String value=sheetDataList.get(template.getMergeCells().get(k).getRow()).get(j)+"";
									if(!StringManagerUtils.isNotNull(value)){
										sheetDataList.get(template.getMergeCells().get(k).getRow()).set(j, ExcelUtils.COLUMN_MERGE);
									}
								}
							}else if(template.getMergeCells().get(k).getRowspan()>1&&template.getMergeCells().get(k).getColspan()==1){
								for(int j=template.getMergeCells().get(k).getRow();j<template.getMergeCells().get(k).getRow()+template.getMergeCells().get(k).getRowspan();j++){
									String value=sheetDataList.get(j).get(template.getMergeCells().get(k).getCol())+"";
									if(!StringManagerUtils.isNotNull(value)){
										sheetDataList.get(j).set(template.getMergeCells().get(k).getCol(), ExcelUtils.ROW_MERGE);
									}
								}
							}
						}
					}
					sheetList.add(sheetDataList);
				}
			}
			
			ExcelUtils.exportDataWithTitleAndHead(response, fileName, titleList,sheetNameList, sheetList, null, null,sheetTemplateList,language);
			if(user!=null){
				saveSystemLog(user,4,"导出文件:"+fileName);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public String getSingleWellDailyReportData(Page pager, String orgId,String deviceType,String reportType,
			String deviceId,String deviceName,String calculateType,
			String startDate,String endDate,String reportDate,
			String reportInterval,
			int userNo,String language)throws Exception {
		StringBuffer result_json = new StringBuffer();
		int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
		Gson gson =new Gson();
		java.lang.reflect.Type type=null;
		String reportTemplateCode="";
		String reportUnitId="";
		int reportUnitCalculateType=0;
		String deviceTableName="tbl_device";
		String viewName="VIW_TIMINGCALCULATIONDATA";
		String calTotalTableName="";
		
		List<List<String>> dataList=new ArrayList<>();
		int totalCount=0;
		int timeColIndex=-99;
		int deviceNameColIndex=-99;
		String maxTimeStr="";
		List<String> defaultTimeList= StringManagerUtils.getTimeRangeList(reportDate,offsetHour,StringManagerUtils.stringToInteger(reportInterval));
		
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
		int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
		String timeEfficiencyUnit=languageResourceMap.get("decimals");
		int timeEfficiencyZoom=1;
		if(timeEfficiencyUnitType==2){
			timeEfficiencyUnit="%";
			timeEfficiencyZoom=100;
		}
		
		ReportTemplate.Template template=null;
		String reportTemplateCodeSql="select t3.id,t3.singleWellDailyReportTemplate,t3.productionreporttemplate,t3.calculateType "
				+ " from "+deviceTableName+" t,tbl_protocolreportinstance t2,tbl_report_unit_conf t3 "
				+ " where t.reportinstancecode=t2.code and t2.unitid=t3.id "
				+ " and t.id="+deviceId;
		List<?> reportTemplateCodeList = this.findCallSql(reportTemplateCodeSql);
		if(reportTemplateCodeList.size()>0){
			Object[] obj=(Object[]) reportTemplateCodeList.get(0);
			reportUnitId=(obj[0]+"").replaceAll("null", "");
			reportTemplateCode=(obj[1]+"").replaceAll("null", "");
			reportUnitCalculateType=StringManagerUtils.stringToInteger(obj[3]+"");
		}
		if(reportUnitCalculateType==1){
			calTotalTableName="VIW_SRPTIMINGCALCULATIONDATA";
		}else if(reportUnitCalculateType==2){
			calTotalTableName="VIW_PCPTIMINGCALCULATIONDATA";
		}
		if(StringManagerUtils.isNotNull(reportTemplateCode)){
			template=MemoryDataManagerTask.getSingleWellDailyReportTemplateByCode(reportTemplateCode);
		}
		if(template!=null){
			int columnCount=0;
			if("zh_CN".equalsIgnoreCase(language)){
				if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_zh_CN()!=null){
					columnCount=template.getHeader().get(0).getTitle_zh_CN().size();
					for(int i=0;i<template.getHeader().get(0).getTitle_zh_CN().size();i++){
						String header=template.getHeader().get(0).getTitle_zh_CN().get(i);
						if(StringManagerUtils.isNotNull(header)){
							template.getHeader().get(0).getTitle_zh_CN().set(i, header.replaceAll("deviceNameLabel", deviceName));
						}
					}
				}
			}else if("en".equalsIgnoreCase(language)){
				if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_en()!=null){
					columnCount=template.getHeader().get(0).getTitle_en().size();
					for(int i=0;i<template.getHeader().get(0).getTitle_en().size();i++){
						String header=template.getHeader().get(0).getTitle_en().get(i);
						if(StringManagerUtils.isNotNull(header)){
							template.getHeader().get(0).getTitle_en().set(i, header.replaceAll("deviceNameLabel", deviceName));
						}
					}
				}
			}else if("ru".equalsIgnoreCase(language)){
				if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_ru()!=null){
					columnCount=template.getHeader().get(0).getTitle_ru().size();
					for(int i=0;i<template.getHeader().get(0).getTitle_ru().size();i++){
						String header=template.getHeader().get(0).getTitle_ru().get(i);
						if(StringManagerUtils.isNotNull(header)){
							template.getHeader().get(0).getTitle_ru().set(i, header.replaceAll("deviceNameLabel", deviceName));
						}
					}
				}
			}
			
			
			
			if(template.getHeader().size()>0){
				String labelInfoStr="";
				String[] labelInfoArr=null;
				String labelInfoSql="select t.headerlabelinfo from TBL_TIMINGCALCULATIONDATA t "
						+ " where t.deviceId="+deviceId+" "
						+ " and t.caltime=( select max(t2.caltime) from TBL_TIMINGCALCULATIONDATA t2 where t2.deviceId=t.deviceId and t2.headerLabelInfo is not null)";
				List<?> labelInfoList = this.findCallSql(labelInfoSql);
				if(labelInfoList.size()>0){
					labelInfoStr=labelInfoList.get(0).toString();
					if(StringManagerUtils.isNotNull(labelInfoStr)){
						labelInfoArr=labelInfoStr.split(",");
					}
				}
				if(labelInfoArr!=null && labelInfoArr.length>0){
					for(int i=0;i<labelInfoArr.length;i++){
						if("label".equals(labelInfoArr[i])){
							labelInfoArr[i]="";
						}
						for(int j=0;j<template.getHeader().size();j++){
							boolean exit=false;
							if("zh_CN".equalsIgnoreCase(language)){
								if(template.getHeader().get(j).getTitle_zh_CN()!=null){
									for(int k=0;k<template.getHeader().get(j).getTitle_zh_CN().size();k++){
										if(template.getHeader().get(j).getTitle_zh_CN().get(k).indexOf("label")>=0){
											template.getHeader().get(j).getTitle_zh_CN().set(k, template.getHeader().get(j).getTitle_zh_CN().get(k).replaceFirst("label", labelInfoArr[i]));
											exit=true;
											break;
										}
									}
								}
							}else if("en".equalsIgnoreCase(language)){
								if(template.getHeader().get(j).getTitle_en()!=null){
									for(int k=0;k<template.getHeader().get(j).getTitle_en().size();k++){
										if(template.getHeader().get(j).getTitle_en().get(k).indexOf("label")>=0){
											template.getHeader().get(j).getTitle_en().set(k, template.getHeader().get(j).getTitle_en().get(k).replaceFirst("label", labelInfoArr[i]));
											exit=true;
											break;
										}
									}
								}
							}else if("ru".equalsIgnoreCase(language)){
								if(template.getHeader().get(j).getTitle_ru()!=null){
									for(int k=0;k<template.getHeader().get(j).getTitle_ru().size();k++){
										if(template.getHeader().get(j).getTitle_ru().get(k).indexOf("label")>=0){
											template.getHeader().get(j).getTitle_ru().set(k, template.getHeader().get(j).getTitle_ru().get(k).replaceFirst("label", labelInfoArr[i]));
											exit=true;
											break;
										}
									}
								}
							}
							
							if(exit){
								break;
							}
						}
					}
				}
			}
			
			String templateStr=gson.toJson(template).replace("label", "");
			if(timeEfficiencyUnitType==2){
				templateStr=templateStr.replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)");
			}else{
				templateStr=templateStr.replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)");
			}
			
			result_json.append("{\"success\":true,\"template\":"+templateStr+",");
			
			String reportItemSql="select t.itemname,t.itemcode,t.sort,t.datatype,t.prec,t.totalType,t.dataSource "
					+ " from TBL_REPORT_ITEMS2UNIT_CONF t "
					+ " where t.unitid="+reportUnitId+" "
					+ " and t.reportType="+reportType
					+ " and t.sort>=0"
					+ " and t.sort<="+columnCount
					+ " and t.showlevel is null or t.showlevel>=(select r.showlevel from tbl_user u,tbl_role r where u.user_type=r.role_level and u.user_no="+userNo+")"
					+ " order by t.sort";
			List<ReportUnitItem> reportAcqItemList=new ArrayList<>();
			List<ReportUnitItem> reportOtherItemList=new ArrayList<>();
			List<?> reportItemQuertList = this.findCallSql(reportItemSql);
			for(int i=0;i<reportItemQuertList.size();i++){
				Object[] reportItemObj=(Object[]) reportItemQuertList.get(i);
				ReportUnitItem reportUnitItem=new ReportUnitItem();
				reportUnitItem.setItemName(reportItemObj[0]+"");
				reportUnitItem.setItemCode(reportItemObj[1]+"");
				reportUnitItem.setSort(StringManagerUtils.stringToInteger(reportItemObj[2]+""));
				reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportItemObj[3]+""));
				String precStr=(reportItemObj[4]+"").replaceAll("null", "");
				if(StringManagerUtils.isNumber(precStr)){
					reportUnitItem.setPrec(StringManagerUtils.stringToInteger(precStr));
				}else{
					reportUnitItem.setPrec(-1);
				}
				reportUnitItem.setTotalType(StringManagerUtils.stringToInteger(reportItemObj[5]+""));
				reportUnitItem.setDataSource(reportItemObj[6]+"");
				if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("acquisition"), reportUnitItem.getDataSource(), false)){
					reportAcqItemList.add(reportUnitItem);
				}else{
					reportOtherItemList.add(reportUnitItem);
				}
			}
			
			StringBuffer sqlBuff = new StringBuffer();
			sqlBuff.append("select t.id,to_char(t.calTime@'yyyy-mm-dd hh24:mi:ss')");
			
			if(reportAcqItemList.size()>0){
				sqlBuff.append(",t.caldata");
			}
			
			for(int i=0;i<reportOtherItemList.size();i++){
				String tableAlias="t";
				String column=reportOtherItemList.get(i).getItemCode();
				if(reportUnitCalculateType>0){
					if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("calculate"), reportOtherItemList.get(i).getDataSource(), false)){
						if(StringManagerUtils.generalCalColumnFiter(column)){
							tableAlias="t";
						}else{
							tableAlias="t2";
						}
					}
				}
				if(reportOtherItemList.get(i).getDataType()==3){
					sqlBuff.append(",to_char("+tableAlias+"."+column+"@'yyyy-mm-dd') as "+column+"");
				}else if(reportOtherItemList.get(i).getDataType()==4){
					sqlBuff.append(",to_char("+tableAlias+"."+column+"@'hh24:mi') as "+column+"");
				}else if(reportOtherItemList.get(i).getDataType()==2 && reportOtherItemList.get(i).getPrec()>=0){
					if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
						column=column+"*"+timeEfficiencyZoom;
					}
					sqlBuff.append(",round("+tableAlias+"."+column+","+reportOtherItemList.get(i).getPrec()+")");
				}else{
					if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
						column=column+"*"+timeEfficiencyZoom;
					}else if("resultName".equalsIgnoreCase(column)){
						column="resultCode as resultName";
					}else if("optimizationSuggestion".equalsIgnoreCase(column)){
						column="resultCode as optimizationSuggestion";
					}
					sqlBuff.append(","+tableAlias+"."+column+"");
				}
				
				if(timeColIndex<0 && "calTime".equalsIgnoreCase(column)){
					timeColIndex=reportOtherItemList.get(i).getSort()-1;
				}
				
				if(deviceNameColIndex<0 && "deviceName".equalsIgnoreCase(column)){
					deviceNameColIndex=reportOtherItemList.get(i).getSort()-1;
				}
			}
			
			
			sqlBuff.append(" from "+viewName+" t ");
			if(reportUnitCalculateType>0){
				sqlBuff.append(" left outer join "+calTotalTableName+" t2 on t.deviceId=t2.deviceId and t.calTime=t2.calTime ");
			}
			sqlBuff.append(" where 1=1");
			sqlBuff.append(" and t.org_id in ("+orgId+") and t.deviceId="+deviceId+" ");
			sqlBuff.append(" and t.calTime > to_date('"+reportDate+"','yyyy-mm-dd')+"+offsetHour+"/24 and t.calTime<= to_date('"+reportDate+"','yyyy-mm-dd')+"+offsetHour+"/24+1");
			
			if(StringManagerUtils.stringToInteger(reportInterval)>1){
				sqlBuff.append(" and to_char(t.calTime,'yyyy-mm-dd hh24:mi:ss') in ("+StringManagerUtils.joinStringArr2(defaultTimeList, ",")+")");
			}
			
			sqlBuff.append(" order by t.calTime");
			
			List<String> allColList=new ArrayList<String>();
			
			String sql=sqlBuff.toString().replaceAll("@", ",");
			List<?> reportDataList = this.findCallSql(sql);
			totalCount=reportDataList.size();
			for(int i=0;i<reportDataList.size();i++){
				Object[] reportDataObj=(Object[]) reportDataList.get(i);
				String recordId=reportDataObj[0]+"";
				maxTimeStr=reportDataObj[1]+"";
				
				List<String> everyDaya=new ArrayList<String>();
				for(int j=0;j<columnCount;j++){
					everyDaya.add("");
				}
				everyDaya.set(0, (i+1)+"");
				everyDaya.add(recordId);
				
				int startIndex=2;
				if(reportAcqItemList.size()>0){
					startIndex=3;
					String calData=StringManagerUtils.CLOBObjectToString(reportDataObj[2]);
					type = new TypeToken<List<KeyValue>>() {}.getType();
					List<KeyValue> calDataList=gson.fromJson(calData, type);
					
					for(ReportUnitItem reportUnitItem:reportAcqItemList){
						String addValue="";
						if(calDataList!=null){
							for(KeyValue keyValue:calDataList){
								if(reportUnitItem.getItemCode().equalsIgnoreCase(keyValue.getKey())){
									addValue=keyValue.getValue();
									break;
								}
							}
						}
						
						if(StringManagerUtils.isNotNull(addValue)){
							String[] totalValueArr=addValue.split(";");
							addValue="";
							if(reportUnitItem.getTotalType()==1 && totalValueArr.length>=1){
								addValue=totalValueArr[0];
							}else if(reportUnitItem.getTotalType()==2 && totalValueArr.length>=2){
								addValue=totalValueArr[1];
							}else if(reportUnitItem.getTotalType()==3 && totalValueArr.length>=3){
								addValue=totalValueArr[2];
							}else if(reportUnitItem.getTotalType()==4 && totalValueArr.length>=4){
								addValue=totalValueArr[3];
							}else if(reportUnitItem.getTotalType()==5 && totalValueArr.length>=5){
								addValue=totalValueArr[4];
							}else if(reportUnitItem.getTotalType()==6 && totalValueArr.length>=6){
								addValue=totalValueArr[5];
							}
						}
						everyDaya.set(reportUnitItem.getSort()-1, addValue);
					}
				}
				
				for(int j=0;j<reportOtherItemList.size();j++){
					if(reportOtherItemList.get(j).getSort()>=1){
						String addValue="";
						String column=reportOtherItemList.get(j).getItemCode();
						if(reportDataObj[j+startIndex] instanceof CLOB || reportDataObj[j+startIndex] instanceof Clob){
							addValue=StringManagerUtils.CLOBObjectToString(reportDataObj[j+startIndex]);
						}else{
							addValue=reportDataObj[j+startIndex]+"";
						}
						
						if("resultName".equalsIgnoreCase(column)){
							addValue=MemoryDataManagerTask.getWorkTypeByCode(addValue,language)!=null?MemoryDataManagerTask.getWorkTypeByCode(addValue,language).getResultName():"";
						}else if("optimizationSuggestion".equalsIgnoreCase(column)){
							addValue=MemoryDataManagerTask.getWorkTypeByCode(addValue,language)!=null?MemoryDataManagerTask.getWorkTypeByCode(addValue,language).getOptimizationSuggestion():"";
						}
						
						everyDaya.set(reportOtherItemList.get(j).getSort()-1, addValue.replaceAll("null", ""));
					}
				}
				dataList.add(everyDaya);
				
				
				if(allColList.size()==0){
					for(int j=0;j<columnCount;j++){
						allColList.add("\"\"");
					}
					allColList.add("\"recordId\"");
					for(int j=0;j<reportAcqItemList.size();j++){
						if(reportAcqItemList.get(j).getSort()>=1){
							allColList.set(reportAcqItemList.get(j).getSort()-1, "\""+reportAcqItemList.get(j).getItemCode()+"\"");
						}
					}
					for(int j=0;j<reportOtherItemList.size();j++){
						if(reportOtherItemList.get(j).getSort()>=1){
							allColList.set(reportOtherItemList.get(j).getSort()-1, "\""+reportOtherItemList.get(j).getItemCode()+"\"");
						}
					}
				}
			}
			
			//补充记录
			long timeDiff=StringManagerUtils.getTimeDifference(maxTimeStr, defaultTimeList.get(defaultTimeList.size()-1), "yyyy-MM-dd HH:mm:ss");
			if(timeDiff>0){
				int rownum=totalCount+1;
				for(int i=0;i<defaultTimeList.size();i++){
					if(StringManagerUtils.getTimeDifference(maxTimeStr, defaultTimeList.get(i), "yyyy-MM-dd HH:mm:ss")>0){
						List<String> everyDaya=new ArrayList<String>();
						for(int j=0;j<columnCount;j++){
							everyDaya.add("");
						}
						everyDaya.set(0, rownum+"");
						everyDaya.add("-99");
						
						if(timeColIndex>=0){
							everyDaya.set(timeColIndex,StringManagerUtils.timeFormatConverter(defaultTimeList.get(i), "yyyy-MM-dd HH:mm:ss", "HH:mm"));
						}
						if(deviceNameColIndex>=0){
							everyDaya.set(deviceNameColIndex,deviceName);
						}
						dataList.add(everyDaya);
						rownum++;
					}
				}
			}
			result_json.append("\"data\":"+gson.toJson(dataList)+",\"columns\":"+allColList.toString());
		}else{
			result_json.append("{\"success\":false,\"template\":{},\"data\":[],\"columns\":[]");
		}
		result_json.append(",\"deviceName\":\""+deviceName+"\"");
		result_json.append(",\"startDate\":\""+startDate+"\"");
		result_json.append(",\"endDate\":\""+endDate+"\"");
		result_json.append(",\"reportDate\":\""+reportDate+"\"");
		result_json.append(",\"totalCount\":"+totalCount+"");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public boolean exportSingleWellDailyReportData(User user,HttpServletResponse response,
			Page pager,String orgId,String deviceType,String reportType,
			String deviceId,String deviceName,String calculateType,
			String startDate,String endDate,String reportDate,String reportInterval,
			int userNo,String language)throws Exception {
		try{
			List<List<Object>> sheetDataList = new ArrayList<>();
			int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
			int interval=Config.getInstance().configFile.getAp().getReport().getInterval();
			int headerRowCount=0;
			String title=deviceName+"单日生产报表";
			String fileName=deviceName+"单日生产报表";
			
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			int timeEfficiencyZoom=1;
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
				timeEfficiencyZoom=100;
			}
			
			Gson gson =new Gson();
			java.lang.reflect.Type type=null;
			
			String reportTemplateCode="";
			String reportUnitId="";
			int reportUnitCalculateType=0;
			String deviceTableName="tbl_device";
			String viewName="VIW_TIMINGCALCULATIONDATA";
			String calTotalTableName="";
			
			int totalCount=0;
			int timeColIndex=-99;
			int deviceNameColIndex=-99;
			String maxTimeStr="";
			List<String> defaultTimeList= StringManagerUtils.getTimeRangeList(reportDate,offsetHour,StringManagerUtils.stringToInteger(reportInterval));
			
			ReportTemplate.Template template=null;
			String reportTemplateCodeSql="select t3.id,t3.singleWellDailyReportTemplate,t3.productionreporttemplate,t3.calculateType "
					+ " from "+deviceTableName+" t,tbl_protocolreportinstance t2,tbl_report_unit_conf t3 "
					+ " where t.reportinstancecode=t2.code and t2.unitid=t3.id "
					+ " and t.id="+deviceId;
			List<?> reportTemplateCodeList = this.findCallSql(reportTemplateCodeSql);
			if(reportTemplateCodeList.size()>0){
				Object[] obj=(Object[]) reportTemplateCodeList.get(0);
				reportUnitId=(obj[0]+"").replaceAll("null", "");
				reportTemplateCode=(obj[1]+"").replaceAll("null", "");
				reportUnitCalculateType=StringManagerUtils.stringToInteger(obj[3]+"");
			}
			if(reportUnitCalculateType==1){
				calTotalTableName="VIW_SRPTIMINGCALCULATIONDATA";
			}else if(reportUnitCalculateType==2){
				calTotalTableName="VIW_PCPTIMINGCALCULATIONDATA";
			}
			if(StringManagerUtils.isNotNull(reportTemplateCode)){
				template=MemoryDataManagerTask.getSingleWellDailyReportTemplateByCode(reportTemplateCode);
			}
			if(template!=null){
				int columnCount=0;
				headerRowCount=template.getHeader().size();
				if("zh_CN".equalsIgnoreCase(language)){
					if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_zh_CN()!=null){
						columnCount=template.getHeader().get(0).getTitle_zh_CN().size();
						for(int i=0;i<template.getHeader().get(0).getTitle_zh_CN().size();i++){
							String header=template.getHeader().get(0).getTitle_zh_CN().get(i);
							if(StringManagerUtils.isNotNull(header)){
								title=header.replaceAll("deviceNameLabel", deviceName);
							}
						}
					}
				}else if("en".equalsIgnoreCase(language)){
					if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_en()!=null){
						columnCount=template.getHeader().get(0).getTitle_en().size();
						for(int i=0;i<template.getHeader().get(0).getTitle_en().size();i++){
							String header=template.getHeader().get(0).getTitle_en().get(i);
							if(StringManagerUtils.isNotNull(header)){
								title=header.replaceAll("deviceNameLabel", deviceName);
							}
						}
					}
				}else if("ru".equalsIgnoreCase(language)){
					if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_ru()!=null){
						columnCount=template.getHeader().get(0).getTitle_ru().size();
						for(int i=0;i<template.getHeader().get(0).getTitle_ru().size();i++){
							String header=template.getHeader().get(0).getTitle_ru().get(i);
							if(StringManagerUtils.isNotNull(header)){
								title=header.replaceAll("deviceNameLabel", deviceName);
							}
						}
					}
				}
				
				
				
				if(template.getHeader().size()>0){
					String labelInfoStr="";
					String[] labelInfoArr=null;
					String labelInfoSql="select t.headerlabelinfo from TBL_TIMINGCALCULATIONDATA t "
							+ " where t.deviceId="+deviceId+" "
							+ " and t.caltime=( select max(t2.caltime) from TBL_TIMINGCALCULATIONDATA t2 where t2.deviceId=t.deviceId and t2.headerLabelInfo is not null)";
					List<?> labelInfoList = this.findCallSql(labelInfoSql);
					if(labelInfoList.size()>0){
						labelInfoStr=labelInfoList.get(0).toString();
						if(StringManagerUtils.isNotNull(labelInfoStr)){
							labelInfoArr=labelInfoStr.split(",");
						}
					}
					if(labelInfoArr!=null && labelInfoArr.length>0){
						for(int i=0;i<labelInfoArr.length;i++){
							if("label".equals(labelInfoArr[i])){
								labelInfoArr[i]="";
							}
							for(int j=0;j<template.getHeader().size();j++){
								boolean exit=false;
								if("zh_CN".equalsIgnoreCase(language)){
									if(template.getHeader().get(j).getTitle_zh_CN()!=null){
										for(int k=0;k<template.getHeader().get(j).getTitle_zh_CN().size();k++){
											if(template.getHeader().get(j).getTitle_zh_CN().get(k).indexOf("label")>=0){
												template.getHeader().get(j).getTitle_zh_CN().set(k, template.getHeader().get(j).getTitle_zh_CN().get(k).replaceFirst("label", labelInfoArr[i]));
												exit=true;
												break;
											}
										}
									}
								}else if("en".equalsIgnoreCase(language)){
									if(template.getHeader().get(j).getTitle_en()!=null){
										for(int k=0;k<template.getHeader().get(j).getTitle_en().size();k++){
											if(template.getHeader().get(j).getTitle_en().get(k).indexOf("label")>=0){
												template.getHeader().get(j).getTitle_en().set(k, template.getHeader().get(j).getTitle_en().get(k).replaceFirst("label", labelInfoArr[i]));
												exit=true;
												break;
											}
										}
									}
								}else if("ru".equalsIgnoreCase(language)){
									if(template.getHeader().get(j).getTitle_ru()!=null){
										for(int k=0;k<template.getHeader().get(j).getTitle_ru().size();k++){
											if(template.getHeader().get(j).getTitle_ru().get(k).indexOf("label")>=0){
												template.getHeader().get(j).getTitle_ru().set(k, template.getHeader().get(j).getTitle_ru().get(k).replaceFirst("label", labelInfoArr[i]));
												exit=true;
												break;
											}
										}
									}
								}
								
								if(exit){
									break;
								}
							}
						}
					}
					for(int j=0;j<template.getHeader().size();j++){
						if("zh_CN".equalsIgnoreCase(language)){
							if(template.getHeader().get(j).getTitle_zh_CN()!=null){
								for(int k=0;k<template.getHeader().get(j).getTitle_zh_CN().size();k++){
									if(template.getHeader().get(j).getTitle_zh_CN().get(k).indexOf("label")>=0){
										template.getHeader().get(j).getTitle_zh_CN().set(k, template.getHeader().get(j).getTitle_zh_CN().get(k).replaceAll("label", ""));
									}
									if(template.getHeader().get(j).getTitle_zh_CN().get(k).indexOf("在线时率")>=0 || template.getHeader().get(j).getTitle_zh_CN().get(k).indexOf("运行时率")>=0){
										if(timeEfficiencyUnitType==2){
											template.getHeader().get(j).getTitle_zh_CN().set(k, template.getHeader().get(j).getTitle_zh_CN().get(k).replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)"));
										}else{
											template.getHeader().get(j).getTitle_zh_CN().set(k, template.getHeader().get(j).getTitle_zh_CN().get(k).replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)"));
										}
									}
								}
							}
						}else if("en".equalsIgnoreCase(language)){
							if(template.getHeader().get(j).getTitle_en()!=null){
								for(int k=0;k<template.getHeader().get(j).getTitle_en().size();k++){
									if(template.getHeader().get(j).getTitle_en().get(k).indexOf("label")>=0){
										template.getHeader().get(j).getTitle_en().set(k, template.getHeader().get(j).getTitle_en().get(k).replaceAll("label", ""));
									}
									if(template.getHeader().get(j).getTitle_en().get(k).indexOf("在线时率")>=0 || template.getHeader().get(j).getTitle_en().get(k).indexOf("运行时率")>=0){
										if(timeEfficiencyUnitType==2){
											template.getHeader().get(j).getTitle_en().set(k, template.getHeader().get(j).getTitle_en().get(k).replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)"));
										}else{
											template.getHeader().get(j).getTitle_en().set(k, template.getHeader().get(j).getTitle_en().get(k).replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)"));
										}
									}
								}
							}
						}else if("ru".equalsIgnoreCase(language)){
							if(template.getHeader().get(j).getTitle_ru()!=null){
								for(int k=0;k<template.getHeader().get(j).getTitle_ru().size();k++){
									if(template.getHeader().get(j).getTitle_ru().get(k).indexOf("label")>=0){
										template.getHeader().get(j).getTitle_ru().set(k, template.getHeader().get(j).getTitle_ru().get(k).replaceAll("label", ""));
									}
									if(template.getHeader().get(j).getTitle_ru().get(k).indexOf("在线时率")>=0 || template.getHeader().get(j).getTitle_ru().get(k).indexOf("运行时率")>=0){
										if(timeEfficiencyUnitType==2){
											template.getHeader().get(j).getTitle_ru().set(k, template.getHeader().get(j).getTitle_ru().get(k).replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)"));
										}else{
											template.getHeader().get(j).getTitle_ru().set(k, template.getHeader().get(j).getTitle_ru().get(k).replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)"));
										}
									}
								}
							}
						}
					}
				}
				fileName="";
				fileName+=title+"-"+reportDate;
				
				List<List<String>> dataList=new ArrayList<>();
				String reportItemSql="select t.itemname,t.itemcode,t.sort,t.datatype,t.prec,t.totalType,t.dataSource "
						+ " from TBL_REPORT_ITEMS2UNIT_CONF t "
						+ " where t.unitid="+reportUnitId+" "
						+ " and t.reportType="+reportType
						+ " and t.sort>=0"
						+ " and t.sort<="+columnCount
						+ " and t.showlevel is null or t.showlevel>=(select r.showlevel from tbl_user u,tbl_role r where u.user_type=r.role_level and u.user_no="+userNo+")"
						+ " order by t.sort";
				List<ReportUnitItem> reportAcqItemList=new ArrayList<>();
				List<ReportUnitItem> reportOtherItemList=new ArrayList<>();
				List<?> reportItemQuertList = this.findCallSql(reportItemSql);
				for(int i=0;i<reportItemQuertList.size();i++){
					Object[] reportItemObj=(Object[]) reportItemQuertList.get(i);
					ReportUnitItem reportUnitItem=new ReportUnitItem();
					reportUnitItem.setItemName(reportItemObj[0]+"");
					reportUnitItem.setItemCode(reportItemObj[1]+"");
					reportUnitItem.setSort(StringManagerUtils.stringToInteger(reportItemObj[2]+""));
					reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportItemObj[3]+""));
					String precStr=(reportItemObj[4]+"").replaceAll("null", "");
					if(StringManagerUtils.isNumber(precStr)){
						reportUnitItem.setPrec(StringManagerUtils.stringToInteger(precStr));
					}else{
						reportUnitItem.setPrec(-1);
					}
					reportUnitItem.setTotalType(StringManagerUtils.stringToInteger(reportItemObj[5]+""));
					reportUnitItem.setDataSource(reportItemObj[6]+"");
					if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("acquisition"), reportUnitItem.getDataSource(), false)){
						reportAcqItemList.add(reportUnitItem);
					}else{
						reportOtherItemList.add(reportUnitItem);
					}
				}
				
				StringBuffer sqlBuff = new StringBuffer();
				sqlBuff.append("select t.id,to_char(t.calTime@'yyyy-mm-dd hh24:mi:ss')");
				
				if(reportAcqItemList.size()>0){
					sqlBuff.append(",t.caldata");
				}
				
				for(int i=0;i<reportOtherItemList.size();i++){
					String tableAlias="t";
					String column=reportOtherItemList.get(i).getItemCode();
					if(reportUnitCalculateType>0){
						if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("calculate"), reportOtherItemList.get(i).getDataSource(), false)){
							if(StringManagerUtils.generalCalColumnFiter(column)){
								tableAlias="t";
							}else{
								tableAlias="t2";
							}
						}
					}
					if(reportOtherItemList.get(i).getDataType()==3){
						sqlBuff.append(",to_char("+tableAlias+"."+column+"@'yyyy-mm-dd') as "+column+"");
					}else if(reportOtherItemList.get(i).getDataType()==4){
						sqlBuff.append(",to_char("+tableAlias+"."+column+"@'hh24:mi') as "+column+"");
					}else if(reportOtherItemList.get(i).getDataType()==2 && reportOtherItemList.get(i).getPrec()>=0){
						if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
							column=column+"*"+timeEfficiencyZoom;
						}
						sqlBuff.append(",round("+tableAlias+"."+column+","+reportOtherItemList.get(i).getPrec()+")");
					}else{
						if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
							column=column+"*"+timeEfficiencyZoom;
						}else if("resultName".equalsIgnoreCase(column)){
							column="resultCode as resultName";
						}else if("optimizationSuggestion".equalsIgnoreCase(column)){
							column="resultCode as optimizationSuggestion";
						}
						sqlBuff.append(","+tableAlias+"."+column+"");
					}
					
					if(timeColIndex<0 && "calTime".equalsIgnoreCase(column)){
						timeColIndex=reportOtherItemList.get(i).getSort()-1;
					}
					
					if(deviceNameColIndex<0 && "deviceName".equalsIgnoreCase(column)){
						deviceNameColIndex=reportOtherItemList.get(i).getSort()-1;
					}
				}
				
				
				sqlBuff.append(" from "+viewName+" t ");
				if(reportUnitCalculateType>0){
					sqlBuff.append(" left outer join "+calTotalTableName+" t2 on t.deviceId=t2.deviceId and t.calTime=t2.calTime ");
				}
				sqlBuff.append(" where 1=1");
				sqlBuff.append(" and t.org_id in ("+orgId+") and t.deviceId="+deviceId+" ");
				sqlBuff.append(" and t.calTime > to_date('"+reportDate+"','yyyy-mm-dd')+"+offsetHour+"/24 and t.calTime<= to_date('"+reportDate+"','yyyy-mm-dd')+"+offsetHour+"/24+1");
				
				if(StringManagerUtils.stringToInteger(reportInterval)>1){
					sqlBuff.append(" and to_char(t.calTime,'yyyy-mm-dd hh24:mi:ss') in ("+StringManagerUtils.joinStringArr2(defaultTimeList, ",")+")");
				}
				
				sqlBuff.append(" order by t.calTime");
				
				List<?> reportDataList = this.findCallSql(sqlBuff.toString().replaceAll("@", ","));
				totalCount=reportDataList.size();
				for(int i=0;i<reportDataList.size();i++){
					Object[] reportDataObj=(Object[]) reportDataList.get(i);
					String recordId=reportDataObj[0]+"";
					maxTimeStr=reportDataObj[1]+"";
					List<String> everyDaya=new ArrayList<String>();
					for(int j=0;j<columnCount;j++){
						everyDaya.add("");
					}
					everyDaya.set(0, (i+1)+"");
					int startIndex=2;
					if(reportAcqItemList.size()>0){
						startIndex=3;
						String calData=StringManagerUtils.CLOBObjectToString(reportDataObj[2]);
						type = new TypeToken<List<KeyValue>>() {}.getType();
						List<KeyValue> calDataList=gson.fromJson(calData, type);
						
						for(ReportUnitItem reportUnitItem:reportAcqItemList){
							String addValue="";
							if(calDataList!=null){
								for(KeyValue keyValue:calDataList){
									if(reportUnitItem.getItemCode().equalsIgnoreCase(keyValue.getKey())){
										addValue=keyValue.getValue();
										break;
									}
								}
							}
							
							if(StringManagerUtils.isNotNull(addValue)){
								String[] totalValueArr=addValue.split(";");
								addValue="";
								if(reportUnitItem.getTotalType()==1 && totalValueArr.length>=1){
									addValue=totalValueArr[0];
								}else if(reportUnitItem.getTotalType()==2 && totalValueArr.length>=2){
									addValue=totalValueArr[1];
								}else if(reportUnitItem.getTotalType()==3 && totalValueArr.length>=3){
									addValue=totalValueArr[2];
								}else if(reportUnitItem.getTotalType()==4 && totalValueArr.length>=4){
									addValue=totalValueArr[3];
								}else if(reportUnitItem.getTotalType()==5 && totalValueArr.length>=5){
									addValue=totalValueArr[4];
								}else if(reportUnitItem.getTotalType()==6 && totalValueArr.length>=6){
									addValue=totalValueArr[5];
								}
							}
							everyDaya.set(reportUnitItem.getSort()-1, addValue);
						}
					}
					
					for(int j=0;j<reportOtherItemList.size();j++){
						if(reportOtherItemList.get(j).getSort()>=1){
							String addValue="";
							String column=reportOtherItemList.get(j).getItemCode();
							if(reportDataObj[j+startIndex] instanceof CLOB || reportDataObj[j+startIndex] instanceof Clob){
								addValue=StringManagerUtils.CLOBObjectToString(reportDataObj[j+startIndex]);
							}else{
								addValue=reportDataObj[j+startIndex]+"";
							}
							
							if("resultName".equalsIgnoreCase(column)){
								addValue=MemoryDataManagerTask.getWorkTypeByCode(addValue,language)!=null?MemoryDataManagerTask.getWorkTypeByCode(addValue,language).getResultName():"";
							}else if("optimizationSuggestion".equalsIgnoreCase(column)){
								addValue=MemoryDataManagerTask.getWorkTypeByCode(addValue,language)!=null?MemoryDataManagerTask.getWorkTypeByCode(addValue,language).getOptimizationSuggestion():"";
							}
							everyDaya.set(reportOtherItemList.get(j).getSort()-1, addValue.replaceAll("null", ""));
						}
					}
					dataList.add(everyDaya);
				}
				
				//补充记录
				long timeDiff=StringManagerUtils.getTimeDifference(maxTimeStr, defaultTimeList.get(defaultTimeList.size()-1), "yyyy-MM-dd HH:mm:ss");
				if(timeDiff>0){
					int rownum=totalCount+1;
					for(int i=0;i<defaultTimeList.size();i++){
						if(StringManagerUtils.getTimeDifference(maxTimeStr, defaultTimeList.get(i), "yyyy-MM-dd HH:mm:ss")>0){
							List<String> everyDaya=new ArrayList<String>();
							for(int j=0;j<columnCount;j++){
								everyDaya.add("");
							}
							everyDaya.set(0, rownum+"");
							
							if(timeColIndex>=0){
								everyDaya.set(timeColIndex,StringManagerUtils.timeFormatConverter(defaultTimeList.get(i), "yyyy-MM-dd HH:mm:ss", "HH:mm"));
							}
							if(deviceNameColIndex>=0){
								everyDaya.set(deviceNameColIndex,deviceName);
							}
							dataList.add(everyDaya);
							rownum++;
						}
					}
				}
				
				for(int i=0;i<template.getHeader().size();i++){
					List<Object> record = new ArrayList<>();
					if("zh_CN".equalsIgnoreCase(language)){
						for(int j=0;j<template.getHeader().get(i).getTitle_zh_CN().size();j++){
							if(i==0 && j==0){
								record.add(title);
							}else{
								record.add(template.getHeader().get(i).getTitle_zh_CN().get(j));
							}
						}
					}else if("en".equalsIgnoreCase(language)){
						for(int j=0;j<template.getHeader().get(i).getTitle_en().size();j++){
							if(i==0 && j==0){
								record.add(title);
							}else{
								record.add(template.getHeader().get(i).getTitle_en().get(j));
							}
						}
					}else if("ru".equalsIgnoreCase(language)){
						for(int j=0;j<template.getHeader().get(i).getTitle_ru().size();j++){
							if(i==0 && j==0){
								record.add(title);
							}else{
								record.add(template.getHeader().get(i).getTitle_ru().get(j));
							}
						}
					}
					
					sheetDataList.add(record);
				}
				for(int i=0;i<dataList.size();i++){
					List<Object> record = new ArrayList<>();
					for(int j=0;j<dataList.get(i).size();j++){
						record.add(dataList.get(i).get(j));
					}
					sheetDataList.add(record);
				}
				if(template.getMergeCells()!=null && template.getMergeCells().size()>0){
					for(int i=0;i<template.getMergeCells().size();i++){
						if(template.getMergeCells().get(i).getRowspan()==1&&template.getMergeCells().get(i).getColspan()>1){
							for(int j=template.getMergeCells().get(i).getCol();j<template.getMergeCells().get(i).getCol()+template.getMergeCells().get(i).getColspan();j++){
								String value=sheetDataList.get(template.getMergeCells().get(i).getRow()).get(j)+"";
								if(!StringManagerUtils.isNotNull(value)){
									sheetDataList.get(template.getMergeCells().get(i).getRow()).set(j, ExcelUtils.COLUMN_MERGE);
								}
							}
						}else if(template.getMergeCells().get(i).getRowspan()>1&&template.getMergeCells().get(i).getColspan()==1){
							for(int j=template.getMergeCells().get(i).getRow();j<template.getMergeCells().get(i).getRow()+template.getMergeCells().get(i).getRowspan();j++){
								String value=sheetDataList.get(j).get(template.getMergeCells().get(i).getCol())+"";
								if(!StringManagerUtils.isNotNull(value)){
									sheetDataList.get(j).set(template.getMergeCells().get(i).getCol(), ExcelUtils.ROW_MERGE);
								}
							}
						}
					}
				}
			}
			ExcelUtils.exportDataWithTitleAndHead(response, fileName, title, sheetDataList, null, null,headerRowCount,template,language);
			if(user!=null){
				saveSystemLog(user,4,"导出文件:"+fileName);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean batchExportSingleWellDailyReportData(User user,HttpServletResponse response,
			Page pager,String orgId,
			String deviceType,String deviceTypeName,
			String reportType,
			String deviceName,
			String startDate,String endDate,String reportDate,String reportInterval,
			int userNo,String language)throws Exception {
		try{
			List<List<List<Object>>> sheetList =new ArrayList<>();
			List<String> sheetNameList =new ArrayList<>();
			List<String> titleList =new ArrayList<>();
			List<ReportTemplate.Template> sheetTemplateList=new ArrayList<>();
			
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			int timeEfficiencyZoom=1;
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
				timeEfficiencyZoom=100;
			}
			
			Gson gson =new Gson();
			java.lang.reflect.Type type=null;
			
			int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
			int interval=Config.getInstance().configFile.getAp().getReport().getInterval();
			
			List<String> defaultTimeList= StringManagerUtils.getTimeRangeList(reportDate,offsetHour,StringManagerUtils.stringToInteger(reportInterval));
			
			String fileName=deviceTypeName+"班报表-"+reportDate;
			
			String deviceTableName="tbl_device";
			String calTotalTableName="";
			String wellListSql="select t.id,t.deviceName,t3.id as unitid,t3.singlewelldailyreporttemplate,t.calculateType,t3.calculateType as reportUnitCalculateType"
					+ " from "+deviceTableName+" t "
					+ " left outer join tbl_protocolreportinstance t2 on t.reportinstancecode=t2.code"
					+ " left outer join tbl_report_unit_conf t3 on t3.id=t2.unitid"
					+ " where t.orgid in ("+orgId+")";
			if(StringManagerUtils.isNum(deviceType)){
				wellListSql+= " and t.devicetype="+deviceType;
			}else{
				wellListSql+= " and t.devicetype in ("+deviceType+")";
			}
			if(StringManagerUtils.isNotNull(deviceName)){
				wellListSql+=" and t.deviceName='"+deviceName+"'";
			}
			wellListSql+=" order by t.sortnum,t.deviceName";
			
			List<?> wellList = this.findCallSql(wellListSql);
			
			for(int i=0;i<wellList.size();i++){
				Object[] obj=(Object[]) wellList.get(i);
				String deviceId=obj[0]+"";
				deviceName=obj[1]+"";
				String reportUnitId=(obj[2]+"").replaceAll("null", "");
				String reportTemplateCode=(obj[3]+"").replaceAll("null", "");
				int reportUnitCalculateType=StringManagerUtils.stringToInteger(obj[5]+"");
				int calculateType=StringManagerUtils.stringToInteger(obj[4]+"");
				
				String viewName="VIW_TIMINGCALCULATIONDATA";
				calTotalTableName="";
				if(reportUnitCalculateType==1){
					calTotalTableName="VIW_SRPTIMINGCALCULATIONDATA";
				}else if(reportUnitCalculateType==2){
					calTotalTableName="VIW_PCPTIMINGCALCULATIONDATA";
				}
				
				
				String sheetName=deviceName+StringManagerUtils.timeFormatConverter(reportDate, "yyyy-MM-dd", "MM.dd");
				String title="";
				
				List<List<Object>> sheetDataList = new ArrayList<>();
				
				int totalCount=0;
				int timeColIndex=-99;
				int deviceNameColIndex=-99;
				String maxTimeStr="";
				
				ReportTemplate.Template template=null;
				
				if(StringManagerUtils.isNotNull(reportTemplateCode)){
					template=MemoryDataManagerTask.getSingleWellDailyReportTemplateByCode(reportTemplateCode);
				}
				if(template!=null){
					sheetNameList.add(sheetName);
					sheetTemplateList.add(template);
					
					int columnCount=0;
					if("zh_CN".equalsIgnoreCase(language)){
						if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_zh_CN()!=null){
							columnCount=template.getHeader().get(0).getTitle_zh_CN().size();
							for(int j=0;j<template.getHeader().get(0).getTitle_zh_CN().size();j++){
								String header=template.getHeader().get(0).getTitle_zh_CN().get(j);
								if(StringManagerUtils.isNotNull(header)){
									title=header.replaceAll("deviceNameLabel", deviceName);
								}
							}
						}
					}else if("en".equalsIgnoreCase(language)){
						if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_en()!=null){
							columnCount=template.getHeader().get(0).getTitle_en().size();
							for(int j=0;j<template.getHeader().get(0).getTitle_en().size();j++){
								String header=template.getHeader().get(0).getTitle_en().get(j);
								if(StringManagerUtils.isNotNull(header)){
									title=header.replaceAll("deviceNameLabel", deviceName);
								}
							}
						}
					}else if("ru".equalsIgnoreCase(language)){
						if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_ru()!=null){
							columnCount=template.getHeader().get(0).getTitle_ru().size();
							for(int j=0;j<template.getHeader().get(0).getTitle_ru().size();j++){
								String header=template.getHeader().get(0).getTitle_ru().get(j);
								if(StringManagerUtils.isNotNull(header)){
									title=header.replaceAll("deviceNameLabel", deviceName);
								}
							}
						}
					}
					
					titleList.add(title);
					
					if(template.getHeader().size()>0){
						String labelInfoStr="";
						String[] labelInfoArr=null;
						String labelInfoSql="select t.headerlabelinfo from TBL_TIMINGCALCULATIONDATA t "
								+ " where t.deviceId="+deviceId+" "
								+ " and t.caltime=( select max(t2.caltime) from TBL_TIMINGCALCULATIONDATA t2 where t2.deviceId=t.deviceId and t2.headerLabelInfo is not null)";
						List<?> labelInfoList = this.findCallSql(labelInfoSql);
						if(labelInfoList.size()>0){
							labelInfoStr=labelInfoList.get(0).toString();
							if(StringManagerUtils.isNotNull(labelInfoStr)){
								labelInfoArr=labelInfoStr.split(",");
							}
						}
						if(labelInfoArr!=null && labelInfoArr.length>0){
							for(int j=0;j<labelInfoArr.length;j++){
								if("label".equals(labelInfoArr[j])){
									labelInfoArr[j]="";
								}
								for(int l=0;l<template.getHeader().size();l++){
									boolean exit=false;
									if("zh_CN".equalsIgnoreCase(language)){
										if(template.getHeader().get(l).getTitle_zh_CN()!=null){
											for(int k=0;k<template.getHeader().get(l).getTitle_zh_CN().size();k++){
												if(template.getHeader().get(l).getTitle_zh_CN().get(k).indexOf("label")>=0){
													template.getHeader().get(l).getTitle_zh_CN().set(k, template.getHeader().get(l).getTitle_zh_CN().get(k).replaceFirst("label", labelInfoArr[j]));
													exit=true;
													break;
												}
											}
										}
									}else if("en".equalsIgnoreCase(language)){
										if(template.getHeader().get(l).getTitle_en()!=null){
											for(int k=0;k<template.getHeader().get(l).getTitle_en().size();k++){
												if(template.getHeader().get(l).getTitle_en().get(k).indexOf("label")>=0){
													template.getHeader().get(l).getTitle_en().set(k, template.getHeader().get(l).getTitle_en().get(k).replaceFirst("label", labelInfoArr[j]));
													exit=true;
													break;
												}
											}
										}
									}else if("ru".equalsIgnoreCase(language)){
										if(template.getHeader().get(l).getTitle_ru()!=null){
											for(int k=0;k<template.getHeader().get(l).getTitle_ru().size();k++){
												if(template.getHeader().get(l).getTitle_ru().get(k).indexOf("label")>=0){
													template.getHeader().get(l).getTitle_ru().set(k, template.getHeader().get(l).getTitle_ru().get(k).replaceFirst("label", labelInfoArr[j]));
													exit=true;
													break;
												}
											}
										}
									}
									
									if(exit){
										break;
									}
								}
							}
						}
						for(int j=0;j<template.getHeader().size();j++){
							if("zh_CN".equalsIgnoreCase(language)){
								if(template.getHeader().get(j).getTitle_zh_CN()!=null){
									for(int k=0;k<template.getHeader().get(j).getTitle_zh_CN().size();k++){
										if(template.getHeader().get(j).getTitle_zh_CN().get(k).indexOf("label")>=0){
											template.getHeader().get(j).getTitle_zh_CN().set(k, template.getHeader().get(j).getTitle_zh_CN().get(k).replaceAll("label", ""));
										}
										if(template.getHeader().get(j).getTitle_zh_CN().get(k).indexOf("在线时率")>=0 || template.getHeader().get(j).getTitle_zh_CN().get(k).indexOf("运行时率")>=0){
											if(timeEfficiencyUnitType==2){
												template.getHeader().get(j).getTitle_zh_CN().set(k, template.getHeader().get(j).getTitle_zh_CN().get(k).replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)"));
											}else{
												template.getHeader().get(j).getTitle_zh_CN().set(k, template.getHeader().get(j).getTitle_zh_CN().get(k).replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)"));
											}
										}
									}
								}
							}else if("en".equalsIgnoreCase(language)){
								if(template.getHeader().get(j).getTitle_en()!=null){
									for(int k=0;k<template.getHeader().get(j).getTitle_en().size();k++){
										if(template.getHeader().get(j).getTitle_en().get(k).indexOf("label")>=0){
											template.getHeader().get(j).getTitle_en().set(k, template.getHeader().get(j).getTitle_en().get(k).replaceAll("label", ""));
										}
										if(template.getHeader().get(j).getTitle_en().get(k).indexOf("在线时率")>=0 || template.getHeader().get(j).getTitle_en().get(k).indexOf("运行时率")>=0){
											if(timeEfficiencyUnitType==2){
												template.getHeader().get(j).getTitle_en().set(k, template.getHeader().get(j).getTitle_en().get(k).replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)"));
											}else{
												template.getHeader().get(j).getTitle_en().set(k, template.getHeader().get(j).getTitle_en().get(k).replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)"));
											}
										}
									}
								}
							}else if("ru".equalsIgnoreCase(language)){
								if(template.getHeader().get(j).getTitle_ru()!=null){
									for(int k=0;k<template.getHeader().get(j).getTitle_ru().size();k++){
										if(template.getHeader().get(j).getTitle_ru().get(k).indexOf("label")>=0){
											template.getHeader().get(j).getTitle_ru().set(k, template.getHeader().get(j).getTitle_ru().get(k).replaceAll("label", ""));
										}
										if(template.getHeader().get(j).getTitle_ru().get(k).indexOf("在线时率")>=0 || template.getHeader().get(j).getTitle_ru().get(k).indexOf("运行时率")>=0){
											if(timeEfficiencyUnitType==2){
												template.getHeader().get(j).getTitle_ru().set(k, template.getHeader().get(j).getTitle_ru().get(k).replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)"));
											}else{
												template.getHeader().get(j).getTitle_ru().set(k, template.getHeader().get(j).getTitle_ru().get(k).replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)"));
											}
										}
									}
								}
							}
						}
					}
					
					List<List<String>> dataList=new ArrayList<>();
					String reportItemSql="select t.itemname,t.itemcode,t.sort,t.datatype,t.prec,t.totalType,t.dataSource "
							+ " from TBL_REPORT_ITEMS2UNIT_CONF t "
							+ " where t.unitid="+reportUnitId+" "
							+ " and t.reportType="+reportType
							+ " and t.sort>=0"
							+ " and t.sort<="+columnCount
							+ " and t.showlevel is null or t.showlevel>=(select r.showlevel from tbl_user u,tbl_role r where u.user_type=r.role_level and u.user_no="+userNo+")"
							+ " order by t.sort";
					List<ReportUnitItem> reportAcqItemList=new ArrayList<>();
					List<ReportUnitItem> reportOtherItemList=new ArrayList<>();
					List<?> reportItemQuertList = this.findCallSql(reportItemSql);
					for(int j=0;j<reportItemQuertList.size();j++){
						Object[] reportItemObj=(Object[]) reportItemQuertList.get(j);
						ReportUnitItem reportUnitItem=new ReportUnitItem();
						reportUnitItem.setItemName(reportItemObj[0]+"");
						reportUnitItem.setItemCode(reportItemObj[1]+"");
						reportUnitItem.setSort(StringManagerUtils.stringToInteger(reportItemObj[2]+""));
						reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportItemObj[3]+""));
						String precStr=(reportItemObj[4]+"").replaceAll("null", "");
						if(StringManagerUtils.isNumber(precStr)){
							reportUnitItem.setPrec(StringManagerUtils.stringToInteger(precStr));
						}else{
							reportUnitItem.setPrec(-1);
						}
						reportUnitItem.setTotalType(StringManagerUtils.stringToInteger(reportItemObj[5]+""));
						reportUnitItem.setDataSource(reportItemObj[6]+"");
						if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("acquisition"), reportUnitItem.getDataSource(), false)){
							reportAcqItemList.add(reportUnitItem);
						}else{
							reportOtherItemList.add(reportUnitItem);
						}
					}
					
					StringBuffer sqlBuff = new StringBuffer();
					sqlBuff.append("select t.id,to_char(t.calTime@'yyyy-mm-dd hh24:mi:ss')");
					
					if(reportAcqItemList.size()>0){
						sqlBuff.append(",t.caldata");
					}
					
					for(ReportUnitItem reportUnitItem:reportOtherItemList){
						String tableAlias="t";
						String column=reportUnitItem.getItemCode();
						if(reportUnitCalculateType>0){
							if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("calculate"), reportUnitItem.getDataSource(), false)){
								if(StringManagerUtils.generalCalColumnFiter(column)){
									tableAlias="t";
								}else{
									tableAlias="t2";
								}
							}
						}
						if(reportUnitItem.getDataType()==3){
							sqlBuff.append(",to_char("+tableAlias+"."+column+"@'yyyy-mm-dd') as "+column+"");
						}else if(reportUnitItem.getDataType()==4){
							sqlBuff.append(",to_char("+tableAlias+"."+column+"@'hh24:mi') as "+column+"");
						}else if(reportUnitItem.getDataType()==2 && reportUnitItem.getPrec()>=0){
							if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
								column=column+"*"+timeEfficiencyZoom;
							}
							sqlBuff.append(",round("+tableAlias+"."+column+","+reportUnitItem.getPrec()+")");
						}else{
							if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
								column=column+"*"+timeEfficiencyZoom;
							}
							sqlBuff.append(","+tableAlias+"."+column+"");
						}
						
						if(timeColIndex<0 && "calTime".equalsIgnoreCase(column)){
							timeColIndex=reportUnitItem.getSort()-1;
						}
						
						if(deviceNameColIndex<0 && "deviceName".equalsIgnoreCase(column)){
							deviceNameColIndex=reportUnitItem.getSort()-1;
						}
					}
					
					
					sqlBuff.append(" from "+viewName+" t ");
					if(reportUnitCalculateType>0){
						sqlBuff.append(" left outer join "+calTotalTableName+" t2 on t.deviceId=t2.deviceId and t.calTime=t2.calTime ");
					}
					sqlBuff.append(" where 1=1");
					sqlBuff.append(" and t.org_id in ("+orgId+") and t.deviceId="+deviceId+" ");
					sqlBuff.append(" and t.calTime > to_date('"+reportDate+"','yyyy-mm-dd')+"+offsetHour+"/24 and t.calTime<= to_date('"+reportDate+"','yyyy-mm-dd')+"+offsetHour+"/24+1");
					
					if(StringManagerUtils.stringToInteger(reportInterval)>1){
						sqlBuff.append(" and to_char(t.calTime,'yyyy-mm-dd hh24:mi:ss') in ("+StringManagerUtils.joinStringArr2(defaultTimeList, ",")+")");
					}
					
					sqlBuff.append(" order by t.calTime");
					
					List<?> reportDataList = this.findCallSql(sqlBuff.toString().replaceAll("@", ","));
					totalCount=reportDataList.size();
					for(int k=0;k<reportDataList.size();k++){
						Object[] reportDataObj=(Object[]) reportDataList.get(k);
						String recordId=reportDataObj[0]+"";
						maxTimeStr=reportDataObj[1]+"";
						List<String> everyDaya=new ArrayList<String>();
						for(int j=0;j<columnCount;j++){
							everyDaya.add("");
						}
						everyDaya.set(0, (k+1)+"");
						
						int startIndex=2;
						if(reportAcqItemList.size()>0){
							startIndex=3;
							String calData=StringManagerUtils.CLOBObjectToString(reportDataObj[2]);
							type = new TypeToken<List<KeyValue>>() {}.getType();
							List<KeyValue> calDataList=gson.fromJson(calData, type);
							
							for(ReportUnitItem reportUnitItem:reportAcqItemList){
								String addValue="";
								if(calDataList!=null){
									for(KeyValue keyValue:calDataList){
										if(reportUnitItem.getItemCode().equalsIgnoreCase(keyValue.getKey())){
											addValue=keyValue.getValue();
											break;
										}
									}
								}
								
								if(StringManagerUtils.isNotNull(addValue)){
									String[] totalValueArr=addValue.split(";");
									addValue="";
									if(reportUnitItem.getTotalType()==1 && totalValueArr.length>=1){
										addValue=totalValueArr[0];
									}else if(reportUnitItem.getTotalType()==2 && totalValueArr.length>=2){
										addValue=totalValueArr[1];
									}else if(reportUnitItem.getTotalType()==3 && totalValueArr.length>=3){
										addValue=totalValueArr[2];
									}else if(reportUnitItem.getTotalType()==4 && totalValueArr.length>=4){
										addValue=totalValueArr[3];
									}else if(reportUnitItem.getTotalType()==5 && totalValueArr.length>=5){
										addValue=totalValueArr[4];
									}else if(reportUnitItem.getTotalType()==6 && totalValueArr.length>=6){
										addValue=totalValueArr[5];
									}
								}
								everyDaya.set(reportUnitItem.getSort()-1, addValue);
							}
						}
						
						for(int j=0;j<reportOtherItemList.size();j++){
							if(reportOtherItemList.get(j).getSort()>=1){
								String addValue="";
								if(reportDataObj[j+startIndex] instanceof CLOB || reportDataObj[j+startIndex] instanceof Clob){
									addValue=StringManagerUtils.CLOBObjectToString(reportDataObj[j+startIndex]);
								}else{
									addValue=reportDataObj[j+startIndex]+"";
								}
								everyDaya.set(reportOtherItemList.get(j).getSort()-1, addValue.replaceAll("null", ""));
							}
						}
						dataList.add(everyDaya);
					}
					
					//补充记录
					long timeDiff=StringManagerUtils.getTimeDifference(maxTimeStr, defaultTimeList.get(defaultTimeList.size()-1), "yyyy-MM-dd HH:mm:ss");
					if(timeDiff>0){
						int rownum=totalCount+1;
						for(int k=0;k<defaultTimeList.size();k++){
							if(StringManagerUtils.getTimeDifference(maxTimeStr, defaultTimeList.get(k), "yyyy-MM-dd HH:mm:ss")>0){
								List<String> everyDaya=new ArrayList<String>();
								for(int j=0;j<columnCount;j++){
									everyDaya.add("");
								}
								everyDaya.set(0, rownum+"");
								
								if(timeColIndex>=0){
									everyDaya.set(timeColIndex,StringManagerUtils.timeFormatConverter(defaultTimeList.get(k), "yyyy-MM-dd HH:mm:ss", "HH:mm"));
								}
								if(deviceNameColIndex>=0){
									everyDaya.set(deviceNameColIndex,deviceName);
								}
								dataList.add(everyDaya);
								rownum++;
							}
						}
					}
					
					for(int k=0;k<template.getHeader().size();k++){
						List<Object> record = new ArrayList<>();
						if("zh_CN".equalsIgnoreCase(language)){
							for(int j=0;j<template.getHeader().get(k).getTitle_zh_CN().size();j++){
								if(k==0 && j==0){
									record.add(title);
								}else{
									record.add(template.getHeader().get(k).getTitle_zh_CN().get(j));
								}
							}
						}else if("en".equalsIgnoreCase(language)){
							for(int j=0;j<template.getHeader().get(k).getTitle_en().size();j++){
								if(k==0 && j==0){
									record.add(title);
								}else{
									record.add(template.getHeader().get(k).getTitle_en().get(j));
								}
							}
						}else if("ru".equalsIgnoreCase(language)){
							for(int j=0;j<template.getHeader().get(k).getTitle_ru().size();j++){
								if(k==0 && j==0){
									record.add(title);
								}else{
									record.add(template.getHeader().get(k).getTitle_ru().get(j));
								}
							}
						}
						
						sheetDataList.add(record);
					}
					for(int k=0;k<dataList.size();k++){
						List<Object> record = new ArrayList<>();
						for(int j=0;j<dataList.get(k).size();j++){
							record.add(dataList.get(k).get(j));
						}
						sheetDataList.add(record);
					}
					if(template.getMergeCells()!=null && template.getMergeCells().size()>0){
						for(int k=0;k<template.getMergeCells().size();k++){
							if(template.getMergeCells().get(k).getRowspan()==1&&template.getMergeCells().get(k).getColspan()>1){
								for(int j=template.getMergeCells().get(k).getCol();j<template.getMergeCells().get(k).getCol()+template.getMergeCells().get(k).getColspan();j++){
									String value=sheetDataList.get(template.getMergeCells().get(k).getRow()).get(j)+"";
									if(!StringManagerUtils.isNotNull(value)){
										sheetDataList.get(template.getMergeCells().get(k).getRow()).set(j, ExcelUtils.COLUMN_MERGE);
									}
								}
							}else if(template.getMergeCells().get(k).getRowspan()>1&&template.getMergeCells().get(k).getColspan()==1){
								for(int j=template.getMergeCells().get(k).getRow();j<template.getMergeCells().get(k).getRow()+template.getMergeCells().get(k).getRowspan();j++){
									String value=sheetDataList.get(j).get(template.getMergeCells().get(k).getCol())+"";
									if(!StringManagerUtils.isNotNull(value)){
										sheetDataList.get(j).set(template.getMergeCells().get(k).getCol(), ExcelUtils.ROW_MERGE);
									}
								}
							}
						}
					}
					
					sheetList.add(sheetDataList);
				}
			}
			
			ExcelUtils.exportDataWithTitleAndHead(response, fileName, titleList,sheetNameList, sheetList, null, null,sheetTemplateList,language);
			if(user!=null){
				saveSystemLog(user,4,"导出文件:"+fileName);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public String getProductionDailyReportData(Page pager, String orgId,String selectedOrgName,String deviceType,String reportType,
			String instanceCode,String unitId,String deviceName,String startDate,String endDate,String reportDate,int userNo,String language)throws Exception {
		StringBuffer result_json = new StringBuffer();
		Gson gson =new Gson();
		java.lang.reflect.Type type=null;
		String reportUnitId="";
		String reportTemplateCode="";
		int reportUnitCalculateType=0;
		String deviceTableName="tbl_device";
		String viewName="VIW_DAILYCALCULATIONDATA";
		String calTotalTableName="";
		
		ReportTemplate.Template template=null;
		
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
		int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
		String timeEfficiencyUnit=languageResourceMap.get("decimals");
		int timeEfficiencyZoom=1;
		if(timeEfficiencyUnitType==2){
			timeEfficiencyUnit="%";
			timeEfficiencyZoom=100;
		}
		
		String reportTemplateCodeSql="select t3.id,t3.singleWellRangeReportTemplate,t3.productionreporttemplate,t3.calculateType "
				+ " from "+deviceTableName+" t,tbl_protocolreportinstance t2,tbl_report_unit_conf t3 "
				+ " where t.reportinstancecode=t2.code and t2.unitid=t3.id "
				+ " and t3.id="+unitId;
		List<?> reportTemplateCodeList = this.findCallSql(reportTemplateCodeSql);
		if(reportTemplateCodeList.size()>0){
			Object[] obj=(Object[]) reportTemplateCodeList.get(0);
			reportUnitId=(obj[0]+"").replaceAll("null", "");
			reportTemplateCode=(obj[2]+"").replaceAll("null", "");
			reportUnitCalculateType=StringManagerUtils.stringToInteger(obj[3]+"");
		}
		
		if(reportUnitCalculateType==1){
			calTotalTableName="VIW_SRPDAILYCALCULATIONDATA";
		}else if(reportUnitCalculateType==2){
			calTotalTableName="VIW_PCPDAILYCALCULATIONDATA";
		}
		
		if(StringManagerUtils.isNotNull(reportTemplateCode)){
			template=MemoryDataManagerTask.getProductionReportTemplateByCode(reportTemplateCode);
		}
		if(template!=null){
			int columnCount=0;
			if("zh_CN".equalsIgnoreCase(language)){
				if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_zh_CN()!=null){
					columnCount=template.getHeader().get(0).getTitle_zh_CN().size();
					for(int i=0;i<template.getHeader().get(0).getTitle_zh_CN().size();i++){
						String header=template.getHeader().get(0).getTitle_zh_CN().get(i);
						if(StringManagerUtils.isNotNull(header)){
							template.getHeader().get(0).getTitle_zh_CN().set(i, header.replaceAll("orgNameLabel", selectedOrgName));
						}
					}
				}
			}else if("en".equalsIgnoreCase(language)){
				if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_en()!=null){
					columnCount=template.getHeader().get(0).getTitle_en().size();
					for(int i=0;i<template.getHeader().get(0).getTitle_en().size();i++){
						String header=template.getHeader().get(0).getTitle_en().get(i);
						if(StringManagerUtils.isNotNull(header)){
							template.getHeader().get(0).getTitle_en().set(i, header.replaceAll("orgNameLabel", selectedOrgName));
						}
					}
				}
			}else if("ru".equalsIgnoreCase(language)){
				if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_ru()!=null){
					columnCount=template.getHeader().get(0).getTitle_ru().size();
					for(int i=0;i<template.getHeader().get(0).getTitle_ru().size();i++){
						String header=template.getHeader().get(0).getTitle_ru().get(i);
						if(StringManagerUtils.isNotNull(header)){
							template.getHeader().get(0).getTitle_ru().set(i, header.replaceAll("orgNameLabel", selectedOrgName));
						}
					}
				}
			}
			
			
			String templateStr=gson.toJson(template).replace("label", "");
			if(timeEfficiencyUnitType==2){
				templateStr=templateStr.replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)");
			}else{
				templateStr=templateStr.replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)");
			}
			result_json.append("{\"success\":true,\"template\":"+templateStr+",");
			
			List<List<String>> dataList=new ArrayList<>();
			List<List<String>> statDataList=new ArrayList<>();
			List<List<String>> statDataShowValueList=new ArrayList<>();
			List<String> sumDataList=new ArrayList<>();
			List<String> avgDataList=new ArrayList<>();
			List<String> sumShowDataList=new ArrayList<>();
			List<String> avgShowDataList=new ArrayList<>();
			List<Integer> avgRecordsList=new ArrayList<>();
			for(int j=0;j<columnCount;j++){
				sumDataList.add("");
				avgDataList.add("");
				avgRecordsList.add(0);
				
				sumShowDataList.add("");
				avgShowDataList.add("");
			}
			sumDataList.set(0, languageResourceMap.get("sumValue"));
			avgDataList.set(0, languageResourceMap.get("avgValue"));
			
			sumShowDataList.set(0, languageResourceMap.get("sumValue"));
			avgShowDataList.set(0, languageResourceMap.get("avgValue"));
			
			statDataList.add(sumDataList);
			statDataList.add(avgDataList);
			
			statDataShowValueList.add(sumShowDataList);
			statDataShowValueList.add(avgShowDataList);
			
			String reportItemSql="select t.itemname,t.itemcode,t.sort,t.datatype,t.sumsign,t.averagesign,t.prec,t.totalType,t.dataSource "
					+ " from TBL_REPORT_ITEMS2UNIT_CONF t "
					+ " where t.unitid="+unitId+" "
					+ " and t.reportType="+reportType
					+ " and t.sort>=0"
					+ " and t.sort<="+columnCount
					+ " and t.showlevel is null or t.showlevel>=(select r.showlevel from tbl_user u,tbl_role r where u.user_type=r.role_level and u.user_no="+userNo+")"
					+ " order by t.sort";
			List<ReportUnitItem> reportAcqItemList=new ArrayList<>();
			List<ReportUnitItem> reportOtherItemList=new ArrayList<>();
			List<?> reportItemQuertList = this.findCallSql(reportItemSql);
			for(int i=0;i<reportItemQuertList.size();i++){
				Object[] reportItemObj=(Object[]) reportItemQuertList.get(i);
				ReportUnitItem reportUnitItem=new ReportUnitItem();
				reportUnitItem.setItemName(reportItemObj[0]+"");
				reportUnitItem.setItemCode(reportItemObj[1]+"");
				reportUnitItem.setSort(StringManagerUtils.stringToInteger(reportItemObj[2]+""));
				reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportItemObj[3]+""));
				
				reportUnitItem.setSumSign((reportItemObj[4]!=null && StringManagerUtils.isNumber(reportItemObj[4]+"") )?StringManagerUtils.stringToInteger(reportItemObj[4]+""):null);
				reportUnitItem.setAverageSign( (reportItemObj[5]!=null && StringManagerUtils.isNumber(reportItemObj[5]+"") )?StringManagerUtils.stringToInteger(reportItemObj[5]+""):null);
				
				
				String precStr=(reportItemObj[6]+"").replaceAll("null", "");
				if(StringManagerUtils.isNumber(precStr)){
					reportUnitItem.setPrec(StringManagerUtils.stringToInteger(precStr));
				}else{
					reportUnitItem.setPrec(-1);
				}
				
				reportUnitItem.setTotalType(StringManagerUtils.stringToInteger(reportItemObj[7]+""));
				reportUnitItem.setDataSource(reportItemObj[8]+"");
				
				if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("acquisition"), reportUnitItem.getDataSource(), false)){
					reportAcqItemList.add(reportUnitItem);
				}else{
					reportOtherItemList.add(reportUnitItem);
				}
			}
			
			StringBuffer sqlBuff = new StringBuffer();
			sqlBuff.append("select t.id");
			
			if(reportAcqItemList.size()>0){
				sqlBuff.append(",t.caldata");
			}
			for(int i=0;i<reportOtherItemList.size();i++){
				String tableAlias="t";
				String column=reportOtherItemList.get(i).getItemCode();
				if(reportUnitCalculateType>0){
					if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("calculate"), reportOtherItemList.get(i).getDataSource(), false)){
						if(StringManagerUtils.generalCalColumnFiter(column)){
							tableAlias="t";
						}else{
							tableAlias="t2";
						}
					}
				}
				if(reportOtherItemList.get(i).getDataType()==3){
					sqlBuff.append(",to_char("+tableAlias+"."+column+"@'yyyy-mm-dd') as "+column+"");
				}else if(reportOtherItemList.get(i).getDataType()==4){
					sqlBuff.append(",to_char("+tableAlias+"."+column+"@'hh24:mi') as "+column+"");
				}else if(reportOtherItemList.get(i).getDataType()==2 && reportOtherItemList.get(i).getPrec()>=0){
					if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
						column=column+"*"+timeEfficiencyZoom;
					}
					sqlBuff.append(",round("+tableAlias+"."+column+","+reportOtherItemList.get(i).getPrec()+")");
				}else{
					if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
						column=column+"*"+timeEfficiencyZoom;
					}else if("resultName".equalsIgnoreCase(column)){
						column="resultCode as resultName";
					}else if("optimizationSuggestion".equalsIgnoreCase(column)){
						column="resultCode as optimizationSuggestion";
					}
					sqlBuff.append(","+tableAlias+"."+column+"");
				}
			}
			sqlBuff.append(" from "+viewName+" t");
			if(reportUnitCalculateType>0){
				sqlBuff.append(" left outer join "+calTotalTableName+" t2 on t.deviceId=t2.deviceId and t.calDate=t2.calDate");
			}
			sqlBuff.append(" where t.org_id in ("+orgId+")");
			if(StringManagerUtils.isNum(deviceType)){
				sqlBuff.append("and t.deviceType="+deviceType);
			}else{
				sqlBuff.append("and t.deviceType in("+deviceType+")");
			}
			sqlBuff.append(" and t.reportinstancecode='"+instanceCode+"' ");
			
			sqlBuff.append(" and t.calDate = to_date('"+reportDate+"','yyyy-mm-dd')");
			sqlBuff.append(" order by t.sortnum");
			List<String> allColList=new ArrayList<String>();;
			
			List<?> reportDataList = this.findCallSql(sqlBuff.toString().replaceAll("@", ","));
			for(int i=0;i<reportDataList.size();i++){
				Object[] reportDataObj=(Object[]) reportDataList.get(i);
				String recordId=reportDataObj[0]+"";
				List<String> everyDaya=new ArrayList<String>();
				for(int j=0;j<columnCount;j++){
					everyDaya.add("");
				}
				everyDaya.set(0, (i+1)+"");
				everyDaya.add(recordId);
				
				
				int startIndex=1;
				if(reportAcqItemList.size()>0){
					startIndex=2;
					String calData=StringManagerUtils.CLOBObjectToString(reportDataObj[1]);
					type = new TypeToken<List<KeyValue>>() {}.getType();
					List<KeyValue> calDataList=gson.fromJson(calData, type);
					
					for(ReportUnitItem reportUnitItem:reportAcqItemList){
						String addValue="";
						if(calDataList!=null){
							for(KeyValue keyValue:calDataList){
								if(reportUnitItem.getItemCode().equalsIgnoreCase(keyValue.getKey())){
									addValue=keyValue.getValue();
									break;
								}
							}
						}
						
						if(StringManagerUtils.isNotNull(addValue)){
							String[] totalValueArr=addValue.split(";");
							addValue="";
							if(reportUnitItem.getTotalType()==1 && totalValueArr.length>=1){
								addValue=totalValueArr[0];
							}else if(reportUnitItem.getTotalType()==2 && totalValueArr.length>=2){
								addValue=totalValueArr[1];
							}else if(reportUnitItem.getTotalType()==3 && totalValueArr.length>=3){
								addValue=totalValueArr[2];
							}else if(reportUnitItem.getTotalType()==4 && totalValueArr.length>=4){
								addValue=totalValueArr[3];
							}else if(reportUnitItem.getTotalType()==5 && totalValueArr.length>=5){
								addValue=totalValueArr[4];
							}else if(reportUnitItem.getTotalType()==6 && totalValueArr.length>=6){
								addValue=totalValueArr[5];
							}
						}
						everyDaya.set(reportUnitItem.getSort()-1, addValue);
						
						//求和
						if(StringManagerUtils.isNumber(addValue)){
							avgRecordsList.set(reportUnitItem.getSort()-1, avgRecordsList.get(reportUnitItem.getSort()-1)+1);
							String currentSum=statDataList.get(0).get(reportUnitItem.getSort()-1);
							if(StringManagerUtils.isNumber(currentSum)){
								statDataList.get(0).set(reportUnitItem.getSort()-1, StringManagerUtils.stringToFloat((StringManagerUtils.stringToFloat(addValue)+StringManagerUtils.stringToFloat(currentSum))+"", 2)+"" );
							}else{
								statDataList.get(0).set(reportUnitItem.getSort()-1, StringManagerUtils.stringToFloat(StringManagerUtils.stringToFloat(addValue)+"",2)+"");
							}
							
							if(reportUnitItem.getSumSign()!=null && reportUnitItem.getSumSign()==1){
								if(StringManagerUtils.isNumber(currentSum)){
									statDataShowValueList.get(0).set(reportUnitItem.getSort()-1, StringManagerUtils.stringToFloat((StringManagerUtils.stringToFloat(addValue)+StringManagerUtils.stringToFloat(currentSum))+"", 2)+"" );
								}else{
									statDataShowValueList.get(0).set(reportUnitItem.getSort()-1, StringManagerUtils.stringToFloat(StringManagerUtils.stringToFloat(addValue)+"",2)+"");
								}
							}
						}
					
						//求平均						
						if(reportUnitItem.getAverageSign()!=null && reportUnitItem.getAverageSign()==1){
							String sumStr=statDataList.get(0).get(reportUnitItem.getSort()-1);
							int avgRecordCount=avgRecordsList.get(reportUnitItem.getSort()-1)==0?1:avgRecordsList.get(reportUnitItem.getSort()-1);
							float avg=StringManagerUtils.stringToFloat(sumStr)/avgRecordCount;
							statDataList.get(1).set(reportUnitItem.getSort()-1, StringManagerUtils.stringToFloat(avg+"",2)+"");
							statDataShowValueList.get(1).set(reportUnitItem.getSort()-1, StringManagerUtils.stringToFloat(avg+"",2)+"");
						}
						
					}
				}
				
				
				
				for(int j=0;j<reportOtherItemList.size();j++){
					if(reportOtherItemList.get(j).getSort()>=1){
						String addValue="";
						String column=reportOtherItemList.get(j).getItemCode();
						if(reportDataObj[j+startIndex] instanceof CLOB || reportDataObj[j+startIndex] instanceof Clob){
							addValue=StringManagerUtils.CLOBObjectToString(reportDataObj[j+startIndex]);
						}else{
							addValue=reportDataObj[j+startIndex]+"";
						}
						
						if(reportOtherItemList.get(j).getItemCode().equalsIgnoreCase("ProducingfluidLevel")){
							if(StringManagerUtils.isNumber(addValue) && StringManagerUtils.stringToFloat(addValue)<0){
								addValue="";
							}
						}
						
						if("resultName".equalsIgnoreCase(column)){
							addValue=MemoryDataManagerTask.getWorkTypeByCode(addValue,language)!=null?MemoryDataManagerTask.getWorkTypeByCode(addValue,language).getResultName():"";
						}else if("optimizationSuggestion".equalsIgnoreCase(column)){
							addValue=MemoryDataManagerTask.getWorkTypeByCode(addValue,language)!=null?MemoryDataManagerTask.getWorkTypeByCode(addValue,language).getOptimizationSuggestion():"";
						}
						
						everyDaya.set(reportOtherItemList.get(j).getSort()-1, addValue.replaceAll("null", ""));
						
						//求和
						if(StringManagerUtils.isNumber(addValue)){
							avgRecordsList.set(reportOtherItemList.get(j).getSort()-1, avgRecordsList.get(reportOtherItemList.get(j).getSort()-1)+1);
							String currentSum=statDataList.get(0).get(reportOtherItemList.get(j).getSort()-1);
							if(StringManagerUtils.isNumber(currentSum)){
								statDataList.get(0).set(reportOtherItemList.get(j).getSort()-1, StringManagerUtils.stringToFloat((StringManagerUtils.stringToFloat(addValue)+StringManagerUtils.stringToFloat(currentSum))+"", 2)+"" );
							}else{
								statDataList.get(0).set(reportOtherItemList.get(j).getSort()-1, StringManagerUtils.stringToFloat(StringManagerUtils.stringToFloat(addValue)+"",2)+"");
							}
							
							if(reportOtherItemList.get(j).getSumSign()!=null && reportOtherItemList.get(j).getSumSign()==1){
								if(StringManagerUtils.isNumber(currentSum)){
									statDataShowValueList.get(0).set(reportOtherItemList.get(j).getSort()-1, StringManagerUtils.stringToFloat((StringManagerUtils.stringToFloat(addValue)+StringManagerUtils.stringToFloat(currentSum))+"", 2)+"" );
								}else{
									statDataShowValueList.get(0).set(reportOtherItemList.get(j).getSort()-1, StringManagerUtils.stringToFloat(StringManagerUtils.stringToFloat(addValue)+"",2)+"");
								}
							}
						}
					
						//求平均						
						if(reportOtherItemList.get(j).getAverageSign()!=null && reportOtherItemList.get(j).getAverageSign()==1){
							String sumStr=statDataList.get(0).get(reportOtherItemList.get(j).getSort()-1);
							int avgRecordCount=avgRecordsList.get(reportOtherItemList.get(j).getSort()-1)==0?1:avgRecordsList.get(reportOtherItemList.get(j).getSort()-1);
							float avg=StringManagerUtils.stringToFloat(sumStr)/avgRecordCount;
							statDataList.get(1).set(reportOtherItemList.get(j).getSort()-1, StringManagerUtils.stringToFloat(avg+"",2)+"");
							statDataShowValueList.get(1).set(reportOtherItemList.get(j).getSort()-1, StringManagerUtils.stringToFloat(avg+"",2)+"");
						}
					}
				}
				dataList.add(everyDaya);
			}
			
			if(allColList.size()==0){
				for(int j=0;j<columnCount;j++){
					allColList.add("\"\"");
				}
				allColList.add("\"recordId\"");
				for(int j=0;j<reportAcqItemList.size();j++){
					if(reportAcqItemList.get(j).getSort()>=1){
						allColList.set(reportAcqItemList.get(j).getSort()-1, "\""+reportAcqItemList.get(j).getItemCode()+"\"");
					}
				}
				for(int j=0;j<reportOtherItemList.size();j++){
					if(reportOtherItemList.get(j).getSort()>=1){
						allColList.set(reportOtherItemList.get(j).getSort()-1, "\""+reportOtherItemList.get(j).getItemCode()+"\"");
					}
				}
			}
			result_json.append("\"data\":"+gson.toJson(dataList)+",\"statData\":"+gson.toJson(statDataShowValueList)+",\"columns\":"+allColList.toString());
		}else{
			result_json.append("{\"success\":false,\"template\":{},\"data\":[],\"statData\":[],\"columns\":[]");
		}
		result_json.append(",\"deviceName\":\""+deviceName+"\"");
		result_json.append(",\"startDate\":\""+startDate+"\"");
		result_json.append(",\"endDate\":\""+endDate+"\"");
		result_json.append(",\"reportDate\":\""+reportDate+"\"");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public boolean exportProductionDailyReportData(User user,HttpServletResponse response,
			Page pager,String orgId,String selectedOrgName,
			String deviceType,String reportType,
			String instanceCode,String  unitId,String deviceName,String startDate,String endDate,String reportDate,int userNo,String language)throws Exception {
		try{
			StringBuffer result_json = new StringBuffer();
			List<List<Object>> sheetDataList = new ArrayList<>();
			Gson gson =new Gson();
			java.lang.reflect.Type type=null;
			String title=selectedOrgName+"日报表";
			String fileName=selectedOrgName+"日报表";
			int headerRowCount=0;
			
			String reportUnitId="";
			String reportTemplateCode="";
			int reportUnitCalculateType=0;
			String deviceTableName="tbl_device";
			String viewName="VIW_DAILYCALCULATIONDATA";
			String calTotalTableName="";
			
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			int timeEfficiencyZoom=1;
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
				timeEfficiencyZoom=100;
			}
			
			ReportTemplate.Template template=null;
			String reportTemplateCodeSql="select t3.id,t3.singleWellRangeReportTemplate,t3.productionreporttemplate,t3.calculateType "
					+ " from "+deviceTableName+" t,tbl_protocolreportinstance t2,tbl_report_unit_conf t3 "
					+ " where t.reportinstancecode=t2.code and t2.unitid=t3.id "
					+ " and t3.id="+unitId;
			List<?> reportTemplateCodeList = this.findCallSql(reportTemplateCodeSql);
			if(reportTemplateCodeList.size()>0){
				Object[] obj=(Object[]) reportTemplateCodeList.get(0);
				reportUnitId=(obj[0]+"").replaceAll("null", "");
				reportTemplateCode=(obj[2]+"").replaceAll("null", "");
				reportUnitCalculateType=StringManagerUtils.stringToInteger(obj[3]+"");
			}
			
			if(reportUnitCalculateType==1){
				calTotalTableName="VIW_SRPDAILYCALCULATIONDATA";
			}else if(reportUnitCalculateType==2){
				calTotalTableName="VIW_PCPDAILYCALCULATIONDATA";
			}
			
			if(StringManagerUtils.isNotNull(reportTemplateCode)){
				if(StringManagerUtils.stringToInteger(reportType)==0){
					template=MemoryDataManagerTask.getSingleWellRangeReportTemplateByCode(reportTemplateCode);
				}else{
					template=MemoryDataManagerTask.getProductionReportTemplateByCode(reportTemplateCode);
				}
			}
			if(template!=null){
				int columnCount=0;
				headerRowCount=template.getHeader().size();
				if("zh_CN".equalsIgnoreCase(language)){
					if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_zh_CN()!=null){
						columnCount=template.getHeader().get(0).getTitle_zh_CN().size();
						for(int i=0;i<template.getHeader().get(0).getTitle_zh_CN().size();i++){
							String header=template.getHeader().get(0).getTitle_zh_CN().get(i);
							if(StringManagerUtils.isNotNull(header)){
								title=header.replaceAll("orgNameLabel", selectedOrgName);
								template.getHeader().get(0).getTitle_zh_CN().set(i, header.replaceAll("orgNameLabel", selectedOrgName));
							}
						}
					}
					
					for(int j=0;j<template.getHeader().size();j++){
						if(template.getHeader().get(j).getTitle_zh_CN()!=null){
							for(int k=0;k<template.getHeader().get(j).getTitle_zh_CN().size();k++){
								if(template.getHeader().get(j).getTitle_zh_CN().get(k).indexOf("在线时率")>=0 || template.getHeader().get(j).getTitle_zh_CN().get(k).indexOf("运行时率")>=0){
									if(timeEfficiencyUnitType==2){
										template.getHeader().get(j).getTitle_zh_CN().set(k, template.getHeader().get(j).getTitle_zh_CN().get(k).replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)"));
									}else{
										template.getHeader().get(j).getTitle_zh_CN().set(k, template.getHeader().get(j).getTitle_zh_CN().get(k).replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)"));
									}
								}
							}
						}
					}
				}else if("en".equalsIgnoreCase(language)){
					if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_en()!=null){
						columnCount=template.getHeader().get(0).getTitle_en().size();
						for(int i=0;i<template.getHeader().get(0).getTitle_en().size();i++){
							String header=template.getHeader().get(0).getTitle_en().get(i);
							if(StringManagerUtils.isNotNull(header)){
								title=header.replaceAll("orgNameLabel", selectedOrgName);
								template.getHeader().get(0).getTitle_en().set(i, header.replaceAll("orgNameLabel", selectedOrgName));
							}
						}
					}
					
					for(int j=0;j<template.getHeader().size();j++){
						if(template.getHeader().get(j).getTitle_en()!=null){
							for(int k=0;k<template.getHeader().get(j).getTitle_en().size();k++){
								if(template.getHeader().get(j).getTitle_en().get(k).indexOf("在线时率")>=0 || template.getHeader().get(j).getTitle_en().get(k).indexOf("运行时率")>=0){
									if(timeEfficiencyUnitType==2){
										template.getHeader().get(j).getTitle_en().set(k, template.getHeader().get(j).getTitle_en().get(k).replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)"));
									}else{
										template.getHeader().get(j).getTitle_en().set(k, template.getHeader().get(j).getTitle_en().get(k).replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)"));
									}
								}
							}
						}
					}
				}else if("ru".equalsIgnoreCase(language)){
					if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_ru()!=null){
						columnCount=template.getHeader().get(0).getTitle_ru().size();
						for(int i=0;i<template.getHeader().get(0).getTitle_ru().size();i++){
							String header=template.getHeader().get(0).getTitle_ru().get(i);
							if(StringManagerUtils.isNotNull(header)){
								title=header.replaceAll("orgNameLabel", selectedOrgName);
								template.getHeader().get(0).getTitle_ru().set(i, header.replaceAll("orgNameLabel", selectedOrgName));
							}
						}
					}
					
					for(int j=0;j<template.getHeader().size();j++){
						if(template.getHeader().get(j).getTitle_ru()!=null){
							for(int k=0;k<template.getHeader().get(j).getTitle_ru().size();k++){
								if(template.getHeader().get(j).getTitle_ru().get(k).indexOf("在线时率")>=0 || template.getHeader().get(j).getTitle_ru().get(k).indexOf("运行时率")>=0){
									if(timeEfficiencyUnitType==2){
										template.getHeader().get(j).getTitle_ru().set(k, template.getHeader().get(j).getTitle_ru().get(k).replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)"));
									}else{
										template.getHeader().get(j).getTitle_ru().set(k, template.getHeader().get(j).getTitle_ru().get(k).replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)"));
									}
								}
							}
						}
					}
				}
				
				
				fileName="";
				fileName+=title+"-"+reportDate;
				
				List<List<String>> dataList=new ArrayList<>();
				List<List<String>> statDataList=new ArrayList<>();
				List<List<String>> statDataShowValueList=new ArrayList<>();
				List<String> sumDataList=new ArrayList<>();
				List<String> avgDataList=new ArrayList<>();
				List<String> sumShowDataList=new ArrayList<>();
				List<String> avgShowDataList=new ArrayList<>();
				List<Integer> avgRecordsList=new ArrayList<>();
				for(int j=0;j<columnCount;j++){
					sumDataList.add("");
					avgDataList.add("");
					avgRecordsList.add(0);
					
					sumShowDataList.add("");
					avgShowDataList.add("");
				}
				sumDataList.set(0, languageResourceMap.get("sumValue"));
				avgDataList.set(0, languageResourceMap.get("avgValue"));
				
				sumShowDataList.set(0, languageResourceMap.get("sumValue"));
				avgShowDataList.set(0, languageResourceMap.get("avgValue"));
				
				statDataList.add(sumDataList);
				statDataList.add(avgDataList);
				
				statDataShowValueList.add(sumShowDataList);
				statDataShowValueList.add(avgShowDataList);
				String reportItemSql="select t.itemname,t.itemcode,t.sort,t.datatype,t.sumsign,t.averagesign,t.prec,t.totalType,t.dataSource "
						+ " from TBL_REPORT_ITEMS2UNIT_CONF t "
						+ " where t.unitid="+unitId+" "
						+ " and t.reportType="+reportType
						+ " and t.sort>=0"
						+ " and t.sort<="+columnCount
						+ " and t.showlevel is null or t.showlevel>=(select r.showlevel from tbl_user u,tbl_role r where u.user_type=r.role_level and u.user_no="+userNo+")"
						+ " order by t.sort";
				List<ReportUnitItem> reportAcqItemList=new ArrayList<>();
				List<ReportUnitItem> reportOtherItemList=new ArrayList<>();
				List<?> reportItemQuertList = this.findCallSql(reportItemSql);
				for(int i=0;i<reportItemQuertList.size();i++){
					Object[] reportItemObj=(Object[]) reportItemQuertList.get(i);
					ReportUnitItem reportUnitItem=new ReportUnitItem();
					reportUnitItem.setItemName(reportItemObj[0]+"");
					reportUnitItem.setItemCode(reportItemObj[1]+"");
					reportUnitItem.setSort(StringManagerUtils.stringToInteger(reportItemObj[2]+""));
					reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportItemObj[3]+""));
					
					reportUnitItem.setSumSign((reportItemObj[4]!=null && StringManagerUtils.isNumber(reportItemObj[4]+"") )?StringManagerUtils.stringToInteger(reportItemObj[4]+""):null);
					reportUnitItem.setAverageSign( (reportItemObj[5]!=null && StringManagerUtils.isNumber(reportItemObj[5]+"") )?StringManagerUtils.stringToInteger(reportItemObj[5]+""):null);
					
					
					String precStr=(reportItemObj[6]+"").replaceAll("null", "");
					if(StringManagerUtils.isNumber(precStr)){
						reportUnitItem.setPrec(StringManagerUtils.stringToInteger(precStr));
					}else{
						reportUnitItem.setPrec(-1);
					}
					
					reportUnitItem.setTotalType(StringManagerUtils.stringToInteger(reportItemObj[7]+""));
					reportUnitItem.setDataSource(reportItemObj[8]+"");
					
					if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("acquisition"), reportUnitItem.getDataSource(), false)){
						reportAcqItemList.add(reportUnitItem);
					}else{
						reportOtherItemList.add(reportUnitItem);
					}
				}
				
				StringBuffer sqlBuff = new StringBuffer();
				sqlBuff.append("select t.id");
				
				if(reportAcqItemList.size()>0){
					sqlBuff.append(",t.caldata");
				}
				for(int i=0;i<reportOtherItemList.size();i++){
					String tableAlias="t";
					String column=reportOtherItemList.get(i).getItemCode();
					if(reportUnitCalculateType>0){
						if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("calculate"), reportOtherItemList.get(i).getDataSource(), false)){
							if(StringManagerUtils.generalCalColumnFiter(column)){
								tableAlias="t";
							}else{
								tableAlias="t2";
							}
						}
					}
					if(reportOtherItemList.get(i).getDataType()==3){
						sqlBuff.append(",to_char("+tableAlias+"."+column+"@'yyyy-mm-dd') as "+column+"");
					}else if(reportOtherItemList.get(i).getDataType()==4){
						sqlBuff.append(",to_char("+tableAlias+"."+column+"@'hh24:mi') as "+column+"");
					}else if(reportOtherItemList.get(i).getDataType()==2 && reportOtherItemList.get(i).getPrec()>=0){
						if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
							column=column+"*"+timeEfficiencyZoom;
						}
						sqlBuff.append(",round("+tableAlias+"."+column+","+reportOtherItemList.get(i).getPrec()+")");
					}else{
						if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
							column=column+"*"+timeEfficiencyZoom;
						}else if("resultName".equalsIgnoreCase(column)){
							column="resultCode as resultName";
						}else if("optimizationSuggestion".equalsIgnoreCase(column)){
							column="resultCode as optimizationSuggestion";
						}
						sqlBuff.append(","+tableAlias+"."+column+"");
					}
				}
				sqlBuff.append(" from "+viewName+" t");
				if(reportUnitCalculateType>0){
					sqlBuff.append(" left outer join "+calTotalTableName+" t2 on t.deviceId=t2.deviceId and t.calDate=t2.calDate");
				}
				sqlBuff.append(" where t.org_id in ("+orgId+")");
				if(StringManagerUtils.isNum(deviceType)){
					sqlBuff.append("and t.deviceType="+deviceType);
				}else{
					sqlBuff.append("and t.deviceType in("+deviceType+")");
				}
				sqlBuff.append(" and t.reportinstancecode='"+instanceCode+"' ");
				
				sqlBuff.append(" and t.calDate = to_date('"+reportDate+"','yyyy-mm-dd')");
				sqlBuff.append(" order by t.sortnum");
				
				List<?> reportDataList = this.findCallSql(sqlBuff.toString().replaceAll("@", ","));
				for(int i=0;i<reportDataList.size();i++){
					Object[] reportDataObj=(Object[]) reportDataList.get(i);
					String recordId=reportDataObj[0]+"";
					List<String> everyDaya=new ArrayList<String>();
					for(int j=0;j<columnCount;j++){
						everyDaya.add("");
					}
					everyDaya.set(0, (i+1)+"");
					
					int startIndex=1;
					if(reportAcqItemList.size()>0){
						startIndex=2;
						String calData=StringManagerUtils.CLOBObjectToString(reportDataObj[1]);
						type = new TypeToken<List<KeyValue>>() {}.getType();
						List<KeyValue> calDataList=gson.fromJson(calData, type);
						
						for(ReportUnitItem reportUnitItem:reportAcqItemList){
							String addValue="";
							if(calDataList!=null){
								for(KeyValue keyValue:calDataList){
									if(reportUnitItem.getItemCode().equalsIgnoreCase(keyValue.getKey())){
										addValue=keyValue.getValue();
										break;
									}
								}
							}
							
							if(StringManagerUtils.isNotNull(addValue)){
								String[] totalValueArr=addValue.split(";");
								addValue="";
								if(reportUnitItem.getTotalType()==1 && totalValueArr.length>=1){
									addValue=totalValueArr[0];
								}else if(reportUnitItem.getTotalType()==2 && totalValueArr.length>=2){
									addValue=totalValueArr[1];
								}else if(reportUnitItem.getTotalType()==3 && totalValueArr.length>=3){
									addValue=totalValueArr[2];
								}else if(reportUnitItem.getTotalType()==4 && totalValueArr.length>=4){
									addValue=totalValueArr[3];
								}else if(reportUnitItem.getTotalType()==5 && totalValueArr.length>=5){
									addValue=totalValueArr[4];
								}else if(reportUnitItem.getTotalType()==6 && totalValueArr.length>=6){
									addValue=totalValueArr[5];
								}
							}
							everyDaya.set(reportUnitItem.getSort()-1, addValue.replaceAll("null", ""));
							
							//求和
							if(StringManagerUtils.isNumber(addValue)){
								avgRecordsList.set(reportUnitItem.getSort()-1, avgRecordsList.get(reportUnitItem.getSort()-1)+1);
								String currentSum=statDataList.get(0).get(reportUnitItem.getSort()-1);
								if(StringManagerUtils.isNumber(currentSum)){
									statDataList.get(0).set(reportUnitItem.getSort()-1, StringManagerUtils.stringToFloat((StringManagerUtils.stringToFloat(addValue)+StringManagerUtils.stringToFloat(currentSum))+"", 2)+"" );
								}else{
									statDataList.get(0).set(reportUnitItem.getSort()-1, StringManagerUtils.stringToFloat(StringManagerUtils.stringToFloat(addValue)+"",2)+"");
								}
								
								if(reportUnitItem.getSumSign()!=null && reportUnitItem.getSumSign()==1){
									if(StringManagerUtils.isNumber(currentSum)){
										statDataShowValueList.get(0).set(reportUnitItem.getSort()-1, StringManagerUtils.stringToFloat((StringManagerUtils.stringToFloat(addValue)+StringManagerUtils.stringToFloat(currentSum))+"", 2)+"" );
									}else{
										statDataShowValueList.get(0).set(reportUnitItem.getSort()-1, StringManagerUtils.stringToFloat(StringManagerUtils.stringToFloat(addValue)+"",2)+"");
									}
								}
							}
						
							//求平均						
							if(reportUnitItem.getAverageSign()!=null && reportUnitItem.getAverageSign()==1){
								String sumStr=statDataList.get(0).get(reportUnitItem.getSort()-1);
								int avgRecordCount=avgRecordsList.get(reportUnitItem.getSort()-1)==0?1:avgRecordsList.get(reportUnitItem.getSort()-1);
								float avg=StringManagerUtils.stringToFloat(sumStr)/avgRecordCount;
								statDataList.get(1).set(reportUnitItem.getSort()-1, StringManagerUtils.stringToFloat(avg+"",2)+"");
								statDataShowValueList.get(1).set(reportUnitItem.getSort()-1, StringManagerUtils.stringToFloat(avg+"",2)+"");
							}
							
						}
					}
					
					
					
					for(int j=0;j<reportOtherItemList.size();j++){
						if(reportOtherItemList.get(j).getSort()>=1){
							String addValue="";
							String column=reportOtherItemList.get(j).getItemCode();
							if(reportDataObj[j+startIndex] instanceof CLOB || reportDataObj[j+startIndex] instanceof Clob){
								addValue=StringManagerUtils.CLOBObjectToString(reportDataObj[j+startIndex]);
							}else{
								addValue=reportDataObj[j+startIndex]+"";
							}
							
							if(reportOtherItemList.get(j).getItemCode().equalsIgnoreCase("ProducingfluidLevel")){
								if(StringManagerUtils.isNumber(addValue) && StringManagerUtils.stringToFloat(addValue)<0){
									addValue="";
								}
							}
							if("resultName".equalsIgnoreCase(column)){
								addValue=MemoryDataManagerTask.getWorkTypeByCode(addValue,language)!=null?MemoryDataManagerTask.getWorkTypeByCode(addValue,language).getResultName():"";
							}else if("optimizationSuggestion".equalsIgnoreCase(column)){
								addValue=MemoryDataManagerTask.getWorkTypeByCode(addValue,language)!=null?MemoryDataManagerTask.getWorkTypeByCode(addValue,language).getOptimizationSuggestion():"";
							}
							everyDaya.set(reportOtherItemList.get(j).getSort()-1, addValue.replaceAll("null", ""));
							
							//求和
							if(StringManagerUtils.isNumber(addValue)){
								avgRecordsList.set(reportOtherItemList.get(j).getSort()-1, avgRecordsList.get(reportOtherItemList.get(j).getSort()-1)+1);
								String currentSum=statDataList.get(0).get(reportOtherItemList.get(j).getSort()-1);
								if(StringManagerUtils.isNumber(currentSum)){
									statDataList.get(0).set(reportOtherItemList.get(j).getSort()-1, StringManagerUtils.stringToFloat((StringManagerUtils.stringToFloat(addValue)+StringManagerUtils.stringToFloat(currentSum))+"", 2)+"" );
								}else{
									statDataList.get(0).set(reportOtherItemList.get(j).getSort()-1, StringManagerUtils.stringToFloat(StringManagerUtils.stringToFloat(addValue)+"",2)+"");
								}
								
								if(reportOtherItemList.get(j).getSumSign()!=null && reportOtherItemList.get(j).getSumSign()==1){
									if(StringManagerUtils.isNumber(currentSum)){
										statDataShowValueList.get(0).set(reportOtherItemList.get(j).getSort()-1, StringManagerUtils.stringToFloat((StringManagerUtils.stringToFloat(addValue)+StringManagerUtils.stringToFloat(currentSum))+"", 2)+"" );
									}else{
										statDataShowValueList.get(0).set(reportOtherItemList.get(j).getSort()-1, StringManagerUtils.stringToFloat(StringManagerUtils.stringToFloat(addValue)+"",2)+"");
									}
								}
							}
						
							//求平均						
							if(reportOtherItemList.get(j).getAverageSign()!=null && reportOtherItemList.get(j).getAverageSign()==1){
								String sumStr=statDataList.get(0).get(reportOtherItemList.get(j).getSort()-1);
								int avgRecordCount=avgRecordsList.get(reportOtherItemList.get(j).getSort()-1)==0?1:avgRecordsList.get(reportOtherItemList.get(j).getSort()-1);
								float avg=StringManagerUtils.stringToFloat(sumStr)/avgRecordCount;
								statDataList.get(1).set(reportOtherItemList.get(j).getSort()-1, StringManagerUtils.stringToFloat(avg+"",2)+"");
								statDataShowValueList.get(1).set(reportOtherItemList.get(j).getSort()-1, StringManagerUtils.stringToFloat(avg+"",2)+"");
							}
						}
					}
					dataList.add(everyDaya);
				}
				
				for(int i=0;i<template.getHeader().size();i++){
					List<Object> record = new ArrayList<>();
					if("zh_CN".equalsIgnoreCase(language)){
						for(int j=0;j<template.getHeader().get(i).getTitle_zh_CN().size();j++){
							record.add(template.getHeader().get(i).getTitle_zh_CN().get(j));
						}
					}else if("en".equalsIgnoreCase(language)){
						for(int j=0;j<template.getHeader().get(i).getTitle_en().size();j++){
							record.add(template.getHeader().get(i).getTitle_en().get(j));
						}
					}else if("ru".equalsIgnoreCase(language)){
						for(int j=0;j<template.getHeader().get(i).getTitle_ru().size();j++){
							record.add(template.getHeader().get(i).getTitle_ru().get(j));
						}
					}
					
					sheetDataList.add(record);
				}
				for(int i=0;i<dataList.size();i++){
					List<Object> record = new ArrayList<>();
					for(int j=0;j<dataList.get(i).size();j++){
						record.add(dataList.get(i).get(j));
					}
					sheetDataList.add(record);
				}
				
				for(int i=0;i<statDataShowValueList.size();i++){
					List<Object> record = new ArrayList<>();
					for(int j=0;j<statDataShowValueList.get(i).size();j++){
						record.add(statDataShowValueList.get(i).get(j));
					}
					sheetDataList.add(record);
				}
				
				if(template.getMergeCells()!=null && template.getMergeCells().size()>0){
					for(int i=0;i<template.getMergeCells().size();i++){
						if(template.getMergeCells().get(i).getRowspan()==1&&template.getMergeCells().get(i).getColspan()>1){
							for(int j=template.getMergeCells().get(i).getCol();j<template.getMergeCells().get(i).getCol()+template.getMergeCells().get(i).getColspan();j++){
								String value=sheetDataList.get(template.getMergeCells().get(i).getRow()).get(j)+"";
								if(!StringManagerUtils.isNotNull(value)){
									sheetDataList.get(template.getMergeCells().get(i).getRow()).set(j, ExcelUtils.COLUMN_MERGE);
								}
							}
						}else if(template.getMergeCells().get(i).getRowspan()>1&&template.getMergeCells().get(i).getColspan()==1){
							for(int j=template.getMergeCells().get(i).getRow();j<template.getMergeCells().get(i).getRow()+template.getMergeCells().get(i).getRowspan();j++){
								String value=sheetDataList.get(j).get(template.getMergeCells().get(i).getCol())+"";
								if(!StringManagerUtils.isNotNull(value)){
									sheetDataList.get(j).set(template.getMergeCells().get(i).getCol(), ExcelUtils.ROW_MERGE);
								}
							}
						}
					}
				}
			}
			ExcelUtils.exportDataWithTitleAndHead(response, fileName, title, sheetDataList, null, null,headerRowCount,template,language);
			if(user!=null){
				saveSystemLog(user,4,"导出文件:"+fileName);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean batchExportProductionDailyReportData(User user,HttpServletResponse response,
			Page pager,String orgId,String selectedOrgName,
			String deviceType,String reportType,
			String reportDate,int userNo,String language)throws Exception {
		try{
			List<List<List<Object>>> sheetList =new ArrayList<>();
			List<String> sheetNameList =new ArrayList<>();
			List<String> titleList =new ArrayList<>();
			List<ReportTemplate.Template> sheetTemplateList=new ArrayList<>();
			String fileName=selectedOrgName+"日报表-"+reportDate;
			
			Gson gson =new Gson();
			java.lang.reflect.Type type=null;
			
			String deviceTableName="tbl_device";
			String viewName="VIW_DAILYCALCULATIONDATA";
			String calTotalTableName="";
			
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			int timeEfficiencyZoom=1;
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
				timeEfficiencyZoom=100;
			}
			
			String reportTemplateCodeSql="select t2.id,t2.productionreporttemplate,t.name,t.code,t2.calculateType "
					+ " from tbl_protocolreportinstance t,tbl_report_unit_conf t2 "
					+ " where t.unitid=t2.id "
					+ " and t.code in ( select t3.reportinstancecode from tbl_device t3 where t3.deviceType in("+deviceType+") )"
					+ " order by t.sort";
			List<?> reportTemplateCodeList = this.findCallSql(reportTemplateCodeSql);
			
			for(int i=0;i<reportTemplateCodeList.size();i++){
				Object[] obj=(Object[]) reportTemplateCodeList.get(i);
				String unitId=obj[0]+"";
				String reportTemplateCode=(obj[1]+"").replaceAll("null", "");
				String reportInstanceName=(obj[2]+"").replaceAll("null", "");
				String reportInstanceCode=(obj[3]+"").replaceAll("null", "");
				int reportUnitCalculateType=StringManagerUtils.stringToInteger(obj[4]+"");
				
				
				calTotalTableName="";
				if(reportUnitCalculateType==1){
					calTotalTableName="VIW_SRPDAILYCALCULATIONDATA";
				}else if(reportUnitCalculateType==2){
					calTotalTableName="VIW_PCPDAILYCALCULATIONDATA";
				}
				
				String sheetName=reportInstanceName+""+StringManagerUtils.timeFormatConverter(reportDate, "yyyy-MM-dd", "MM.dd");
				String title="";
				
				List<List<Object>> sheetDataList = new ArrayList<>();
				
				ReportTemplate.Template template=null;
				
				if(StringManagerUtils.isNotNull(reportTemplateCode)){
					template=MemoryDataManagerTask.getProductionReportTemplateByCode(reportTemplateCode);
				}
				
				if(template!=null){
					sheetNameList.add(sheetName);
					sheetTemplateList.add(template);
					
					int columnCount=0;
					if("zh_CN".equalsIgnoreCase(language)){
						if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_zh_CN()!=null){
							columnCount=template.getHeader().get(0).getTitle_zh_CN().size();
							for(int j=0;j<template.getHeader().get(0).getTitle_zh_CN().size();j++){
								String header=template.getHeader().get(0).getTitle_zh_CN().get(j);
								if(StringManagerUtils.isNotNull(header)){
									title=header.replaceAll("orgNameLabel", selectedOrgName);
									template.getHeader().get(0).getTitle_zh_CN().set(j, title);
								}
							}
						}
						for(int j=0;j<template.getHeader().size();j++){
							if(template.getHeader().get(j).getTitle_zh_CN()!=null){
								for(int k=0;k<template.getHeader().get(j).getTitle_zh_CN().size();k++){
									if(template.getHeader().get(j).getTitle_zh_CN().get(k).indexOf("在线时率")>=0 || template.getHeader().get(j).getTitle_zh_CN().get(k).indexOf("运行时率")>=0){
										if(timeEfficiencyUnitType==2){
											template.getHeader().get(j).getTitle_zh_CN().set(k, template.getHeader().get(j).getTitle_zh_CN().get(k).replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)"));
										}else{
											template.getHeader().get(j).getTitle_zh_CN().set(k, template.getHeader().get(j).getTitle_zh_CN().get(k).replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)"));
										}
									}
								}
							}
						}
					}else if("en".equalsIgnoreCase(language)){
						if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_en()!=null){
							columnCount=template.getHeader().get(0).getTitle_en().size();
							for(int j=0;j<template.getHeader().get(0).getTitle_en().size();j++){
								String header=template.getHeader().get(0).getTitle_en().get(j);
								if(StringManagerUtils.isNotNull(header)){
									title=header.replaceAll("orgNameLabel", selectedOrgName);
									template.getHeader().get(0).getTitle_en().set(j, title);
								}
							}
						}
						for(int j=0;j<template.getHeader().size();j++){
							if(template.getHeader().get(j).getTitle_en()!=null){
								for(int k=0;k<template.getHeader().get(j).getTitle_en().size();k++){
									if(template.getHeader().get(j).getTitle_en().get(k).indexOf("在线时率")>=0 || template.getHeader().get(j).getTitle_en().get(k).indexOf("运行时率")>=0){
										if(timeEfficiencyUnitType==2){
											template.getHeader().get(j).getTitle_en().set(k, template.getHeader().get(j).getTitle_en().get(k).replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)"));
										}else{
											template.getHeader().get(j).getTitle_en().set(k, template.getHeader().get(j).getTitle_en().get(k).replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)"));
										}
									}
								}
							}
						}
					}else if("ru".equalsIgnoreCase(language)){
						if(template.getHeader().size()>0 && template.getHeader().get(0).getTitle_ru()!=null){
							columnCount=template.getHeader().get(0).getTitle_ru().size();
							for(int j=0;j<template.getHeader().get(0).getTitle_ru().size();j++){
								String header=template.getHeader().get(0).getTitle_ru().get(j);
								if(StringManagerUtils.isNotNull(header)){
									title=header.replaceAll("orgNameLabel", selectedOrgName);
									template.getHeader().get(0).getTitle_ru().set(j, title);
								}
							}
						}
						for(int j=0;j<template.getHeader().size();j++){
							if(template.getHeader().get(j).getTitle_ru()!=null){
								for(int k=0;k<template.getHeader().get(j).getTitle_ru().size();k++){
									if(template.getHeader().get(j).getTitle_ru().get(k).indexOf("在线时率")>=0 || template.getHeader().get(j).getTitle_ru().get(k).indexOf("运行时率")>=0){
										if(timeEfficiencyUnitType==2){
											template.getHeader().get(j).getTitle_ru().set(k, template.getHeader().get(j).getTitle_ru().get(k).replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)"));
										}else{
											template.getHeader().get(j).getTitle_ru().set(k, template.getHeader().get(j).getTitle_ru().get(k).replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)"));
										}
									}
								}
							}
						}
					}
					
					titleList.add(title);
					
					
					
					List<List<String>> dataList=new ArrayList<>();
					List<List<String>> statDataList=new ArrayList<>();
					List<List<String>> statDataShowValueList=new ArrayList<>();
					List<String> sumDataList=new ArrayList<>();
					List<String> avgDataList=new ArrayList<>();
					List<String> sumShowDataList=new ArrayList<>();
					List<String> avgShowDataList=new ArrayList<>();
					List<Integer> avgRecordsList=new ArrayList<>();
					for(int j=0;j<columnCount;j++){
						sumDataList.add("");
						avgDataList.add("");
						avgRecordsList.add(0);
						
						sumShowDataList.add("");
						avgShowDataList.add("");
					}
					sumDataList.set(0, languageResourceMap.get("sumValue"));
					avgDataList.set(0, languageResourceMap.get("avgValue"));
					
					sumShowDataList.set(0, languageResourceMap.get("sumValue"));
					avgShowDataList.set(0, languageResourceMap.get("avgValue"));
					
					statDataList.add(sumDataList);
					statDataList.add(avgDataList);
					
					statDataShowValueList.add(sumShowDataList);
					statDataShowValueList.add(avgShowDataList);
					
					String reportItemSql="select t.itemname,t.itemcode,t.sort,t.datatype,t.sumsign,t.averagesign,t.prec,t.totalType,t.dataSource "
							+ " from TBL_REPORT_ITEMS2UNIT_CONF t "
							+ " where t.unitid="+unitId+" "
							+ " and t.reportType="+reportType
							+ " and t.sort>=0"
							+ " and t.sort<="+columnCount
							+ " and t.showlevel is null or t.showlevel>=(select r.showlevel from tbl_user u,tbl_role r where u.user_type=r.role_level and u.user_no="+userNo+")"
							+ " order by t.sort";
					List<ReportUnitItem> reportAcqItemList=new ArrayList<>();
					List<ReportUnitItem> reportOtherItemList=new ArrayList<>();
					List<?> reportItemQuertList = this.findCallSql(reportItemSql);
					for(int j=0;j<reportItemQuertList.size();j++){
						Object[] reportItemObj=(Object[]) reportItemQuertList.get(j);
						ReportUnitItem reportUnitItem=new ReportUnitItem();
						reportUnitItem.setItemName(reportItemObj[0]+"");
						reportUnitItem.setItemCode(reportItemObj[1]+"");
						reportUnitItem.setSort(StringManagerUtils.stringToInteger(reportItemObj[2]+""));
						reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportItemObj[3]+""));
						
						reportUnitItem.setSumSign((reportItemObj[4]!=null && StringManagerUtils.isNumber(reportItemObj[4]+"") )?StringManagerUtils.stringToInteger(reportItemObj[4]+""):null);
						reportUnitItem.setAverageSign( (reportItemObj[5]!=null && StringManagerUtils.isNumber(reportItemObj[5]+"") )?StringManagerUtils.stringToInteger(reportItemObj[5]+""):null);
						
						
						String precStr=(reportItemObj[6]+"").replaceAll("null", "");
						if(StringManagerUtils.isNumber(precStr)){
							reportUnitItem.setPrec(StringManagerUtils.stringToInteger(precStr));
						}else{
							reportUnitItem.setPrec(-1);
						}
						
						reportUnitItem.setTotalType(StringManagerUtils.stringToInteger(reportItemObj[7]+""));
						reportUnitItem.setDataSource(reportItemObj[8]+"");
						
						if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("acquisition"), reportUnitItem.getDataSource(), false)){
							reportAcqItemList.add(reportUnitItem);
						}else{
							reportOtherItemList.add(reportUnitItem);
						}
					}
					
					StringBuffer sqlBuff = new StringBuffer();
					
					sqlBuff.append("select t.id");
					
					if(reportAcqItemList.size()>0){
						sqlBuff.append(",t.caldata");
					}
					
					for(ReportUnitItem reportUnitItem:reportOtherItemList){
						String tableAlias="t";
						String column=reportUnitItem.getItemCode();
						if(reportUnitCalculateType>0){
							if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("calculate"), reportUnitItem.getDataSource(), false)){
								if(StringManagerUtils.generalCalColumnFiter(column)){
									tableAlias="t";
								}else{
									tableAlias="t2";
								}
							}
						}
						if(reportUnitItem.getDataType()==3){
							sqlBuff.append(",to_char("+tableAlias+"."+column+"@'yyyy-mm-dd') as "+column+"");
						}else if(reportUnitItem.getDataType()==4){
							sqlBuff.append(",to_char("+tableAlias+"."+column+"@'hh24:mi') as "+column+"");
						}else if(reportUnitItem.getDataType()==2 && reportUnitItem.getPrec()>=0){
							if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
								column=column+"*"+timeEfficiencyZoom;
							}
							sqlBuff.append(",round("+tableAlias+"."+column+","+reportUnitItem.getPrec()+")");
						}else{
							if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
								column=column+"*"+timeEfficiencyZoom;
							}
							sqlBuff.append(","+tableAlias+"."+column+"");
						}
					}
					sqlBuff.append(" from "+viewName+" t");
					if(reportUnitCalculateType>0){
						sqlBuff.append(","+calTotalTableName+" t2");
						
					}
					sqlBuff.append(" where 1=1");
					
					if(reportUnitCalculateType>0){
						sqlBuff.append(" and t.deviceId=t2.deviceId and t.calDate=t2.calDate");
					}
					
					sqlBuff.append(" and t.org_id in ("+orgId+")");
					if(StringManagerUtils.isNum(deviceType)){
						sqlBuff.append("and t.deviceType="+deviceType);
					}else{
						sqlBuff.append("and t.deviceType in("+deviceType+")");
					}
					sqlBuff.append(" and t.reportinstancecode='"+reportInstanceCode+"' ");
					
					sqlBuff.append(" and t.calDate = to_date('"+reportDate+"','yyyy-mm-dd')");
					sqlBuff.append(" order by t.sortnum");
					
					List<?> reportDataList = this.findCallSql(sqlBuff.toString().replaceAll("@", ","));
					for(int k=0;k<reportDataList.size();k++){
						Object[] reportDataObj=(Object[]) reportDataList.get(k);
						String recordId=reportDataObj[0]+"";
						List<String> everyDaya=new ArrayList<String>();
						for(int j=0;j<columnCount;j++){
							everyDaya.add("");
						}
						everyDaya.set(0, (i+1)+"");
						
						int startIndex=1;
						if(reportAcqItemList.size()>0){
							startIndex=2;
							String calData=StringManagerUtils.CLOBObjectToString(reportDataObj[1]);
							type = new TypeToken<List<KeyValue>>() {}.getType();
							List<KeyValue> calDataList=gson.fromJson(calData, type);
							
							for(ReportUnitItem reportUnitItem:reportAcqItemList){
								String addValue="";
								if(calDataList!=null){
									for(KeyValue keyValue:calDataList){
										if(reportUnitItem.getItemCode().equalsIgnoreCase(keyValue.getKey())){
											addValue=keyValue.getValue();
											break;
										}
									}
								}
								
								if(StringManagerUtils.isNotNull(addValue)){
									String[] totalValueArr=addValue.split(";");
									addValue="";
									if(reportUnitItem.getTotalType()==1 && totalValueArr.length>=1){
										addValue=totalValueArr[0];
									}else if(reportUnitItem.getTotalType()==2 && totalValueArr.length>=2){
										addValue=totalValueArr[1];
									}else if(reportUnitItem.getTotalType()==3 && totalValueArr.length>=3){
										addValue=totalValueArr[2];
									}else if(reportUnitItem.getTotalType()==4 && totalValueArr.length>=4){
										addValue=totalValueArr[3];
									}else if(reportUnitItem.getTotalType()==5 && totalValueArr.length>=5){
										addValue=totalValueArr[4];
									}else if(reportUnitItem.getTotalType()==6 && totalValueArr.length>=6){
										addValue=totalValueArr[5];
									}
								}
								everyDaya.set(reportUnitItem.getSort()-1, addValue);
								
								//求和
								if(StringManagerUtils.isNumber(addValue)){
									avgRecordsList.set(reportUnitItem.getSort()-1, avgRecordsList.get(reportUnitItem.getSort()-1)+1);
									String currentSum=statDataList.get(0).get(reportUnitItem.getSort()-1);
									if(StringManagerUtils.isNumber(currentSum)){
										statDataList.get(0).set(reportUnitItem.getSort()-1, StringManagerUtils.stringToFloat((StringManagerUtils.stringToFloat(addValue)+StringManagerUtils.stringToFloat(currentSum))+"", 2)+"" );
									}else{
										statDataList.get(0).set(reportUnitItem.getSort()-1, StringManagerUtils.stringToFloat(StringManagerUtils.stringToFloat(addValue)+"",2)+"");
									}
									
									if(reportUnitItem.getSumSign()!=null && reportUnitItem.getSumSign()==1){
										if(StringManagerUtils.isNumber(currentSum)){
											statDataShowValueList.get(0).set(reportUnitItem.getSort()-1, StringManagerUtils.stringToFloat((StringManagerUtils.stringToFloat(addValue)+StringManagerUtils.stringToFloat(currentSum))+"", 2)+"" );
										}else{
											statDataShowValueList.get(0).set(reportUnitItem.getSort()-1, StringManagerUtils.stringToFloat(StringManagerUtils.stringToFloat(addValue)+"",2)+"");
										}
									}
								}
							
								//求平均						
								if(reportUnitItem.getAverageSign()!=null && reportUnitItem.getAverageSign()==1){
									String sumStr=statDataList.get(0).get(reportUnitItem.getSort()-1);
									int avgRecordCount=avgRecordsList.get(reportUnitItem.getSort()-1)==0?1:avgRecordsList.get(reportUnitItem.getSort()-1);
									float avg=StringManagerUtils.stringToFloat(sumStr)/avgRecordCount;
									statDataList.get(1).set(reportUnitItem.getSort()-1, StringManagerUtils.stringToFloat(avg+"",2)+"");
									statDataShowValueList.get(1).set(reportUnitItem.getSort()-1, StringManagerUtils.stringToFloat(avg+"",2)+"");
								}
								
							}
						}
						
						
						
						for(int j=0;j<reportOtherItemList.size();j++){
							if(reportOtherItemList.get(j).getSort()>=1){
								String addValue="";
								if(reportDataObj[j+startIndex] instanceof CLOB || reportDataObj[j+startIndex] instanceof Clob){
									addValue=StringManagerUtils.CLOBObjectToString(reportDataObj[j+startIndex]);
								}else{
									addValue=reportDataObj[j+startIndex]+"";
								}
								
								if(reportOtherItemList.get(j).getItemCode().equalsIgnoreCase("ProducingfluidLevel")){
									if(StringManagerUtils.isNumber(addValue) && StringManagerUtils.stringToFloat(addValue)<0){
										addValue="";
									}
								}
								
								everyDaya.set(reportOtherItemList.get(j).getSort()-1, addValue.replaceAll("null", ""));
								
								//求和
								if(StringManagerUtils.isNumber(addValue)){
									avgRecordsList.set(reportOtherItemList.get(j).getSort()-1, avgRecordsList.get(reportOtherItemList.get(j).getSort()-1)+1);
									String currentSum=statDataList.get(0).get(reportOtherItemList.get(j).getSort()-1);
									if(StringManagerUtils.isNumber(currentSum)){
										statDataList.get(0).set(reportOtherItemList.get(j).getSort()-1, StringManagerUtils.stringToFloat((StringManagerUtils.stringToFloat(addValue)+StringManagerUtils.stringToFloat(currentSum))+"", 2)+"" );
									}else{
										statDataList.get(0).set(reportOtherItemList.get(j).getSort()-1, StringManagerUtils.stringToFloat(StringManagerUtils.stringToFloat(addValue)+"",2)+"");
									}
									
									if(reportOtherItemList.get(j).getSumSign()!=null && reportOtherItemList.get(j).getSumSign()==1){
										if(StringManagerUtils.isNumber(currentSum)){
											statDataShowValueList.get(0).set(reportOtherItemList.get(j).getSort()-1, StringManagerUtils.stringToFloat((StringManagerUtils.stringToFloat(addValue)+StringManagerUtils.stringToFloat(currentSum))+"", 2)+"" );
										}else{
											statDataShowValueList.get(0).set(reportOtherItemList.get(j).getSort()-1, StringManagerUtils.stringToFloat(StringManagerUtils.stringToFloat(addValue)+"",2)+"");
										}
									}
								}
							
								//求平均						
								if(reportOtherItemList.get(j).getAverageSign()!=null && reportOtherItemList.get(j).getAverageSign()==1){
									String sumStr=statDataList.get(0).get(reportOtherItemList.get(j).getSort()-1);
									int avgRecordCount=avgRecordsList.get(reportOtherItemList.get(j).getSort()-1)==0?1:avgRecordsList.get(reportOtherItemList.get(j).getSort()-1);
									float avg=StringManagerUtils.stringToFloat(sumStr)/avgRecordCount;
									statDataList.get(1).set(reportOtherItemList.get(j).getSort()-1, StringManagerUtils.stringToFloat(avg+"",2)+"");
									statDataShowValueList.get(1).set(reportOtherItemList.get(j).getSort()-1, StringManagerUtils.stringToFloat(avg+"",2)+"");
								}
							}
						}
						dataList.add(everyDaya);
					}
					
					for(int k=0;k<template.getHeader().size();k++){
						List<Object> record = new ArrayList<>();
						if("zh_CN".equalsIgnoreCase(language)){
							for(int j=0;j<template.getHeader().get(k).getTitle_zh_CN().size();j++){
								if(k==0 && j==0){
									record.add(title);
								}else{
									record.add(template.getHeader().get(k).getTitle_zh_CN().get(j));
								}
							}
						}else if("en".equalsIgnoreCase(language)){
							for(int j=0;j<template.getHeader().get(k).getTitle_en().size();j++){
								if(k==0 && j==0){
									record.add(title);
								}else{
									record.add(template.getHeader().get(k).getTitle_en().get(j));
								}
							}
						}else if("ru".equalsIgnoreCase(language)){
							for(int j=0;j<template.getHeader().get(k).getTitle_ru().size();j++){
								if(k==0 && j==0){
									record.add(title);
								}else{
									record.add(template.getHeader().get(k).getTitle_ru().get(j));
								}
							}
						}
						
						sheetDataList.add(record);
					}
					for(int k=0;k<dataList.size();k++){
						List<Object> record = new ArrayList<>();
						for(int j=0;j<dataList.get(k).size();j++){
							record.add(dataList.get(k).get(j));
						}
						sheetDataList.add(record);
					}
					
					for(int k=0;k<statDataShowValueList.size();k++){
						List<Object> record = new ArrayList<>();
						for(int j=0;j<statDataShowValueList.get(k).size();j++){
							record.add(statDataShowValueList.get(k).get(j));
						}
						sheetDataList.add(record);
					}
					
					if(template.getMergeCells()!=null && template.getMergeCells().size()>0){
						for(int k=0;k<template.getMergeCells().size();k++){
							if(template.getMergeCells().get(k).getRowspan()==1&&template.getMergeCells().get(k).getColspan()>1){
								for(int j=template.getMergeCells().get(k).getCol();j<template.getMergeCells().get(k).getCol()+template.getMergeCells().get(k).getColspan();j++){
									String value=sheetDataList.get(template.getMergeCells().get(k).getRow()).get(j)+"";
									if(!StringManagerUtils.isNotNull(value)){
										sheetDataList.get(template.getMergeCells().get(k).getRow()).set(j, ExcelUtils.COLUMN_MERGE);
									}
								}
							}else if(template.getMergeCells().get(k).getRowspan()>1&&template.getMergeCells().get(k).getColspan()==1){
								for(int j=template.getMergeCells().get(k).getRow();j<template.getMergeCells().get(k).getRow()+template.getMergeCells().get(k).getRowspan();j++){
									String value=sheetDataList.get(j).get(template.getMergeCells().get(k).getCol())+"";
									if(!StringManagerUtils.isNotNull(value)){
										sheetDataList.get(j).set(template.getMergeCells().get(k).getCol(), ExcelUtils.ROW_MERGE);
									}
								}
							}
						}
					}
					
					sheetList.add(sheetDataList);
				}
			}
			
			ExcelUtils.exportDataWithTitleAndHead(response, fileName, titleList,sheetNameList, sheetList, null, null,sheetTemplateList,language);
			if(user!=null){
				saveSystemLog(user,4,"导出文件:"+fileName);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public String getSingleWellRangeReportCurveData(Page pager, String orgId,String deviceType,String reportType,
			String deviceId,String deviceName,String calculateType,
			String startDate,String endDate,int userNo)throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer itemsBuff = new StringBuffer();
		StringBuffer itemsCodeBuff = new StringBuffer();
		StringBuffer curveConfBuff = new StringBuffer();
		
		Gson gson =new Gson();
		ConfigFile configFile=Config.getInstance().configFile;
		String reportTemplateCode="";
		String reportUnitId="";
		int reportUnitCalculateType=0;
		String deviceTableName="tbl_device";
		String tableName="TBL_DAILYCALCULATIONDATA";
		String viewName="VIW_DAILYCALCULATIONDATA";
		String calTotalTableName="";
		String graphicSet="{}";
		
		String graphicSetTableName="tbl_devicegraphicset";
		result_json.append("{\"success\":true,");
		
		String graphicSetSql="select t.graphicstyle from "+graphicSetTableName+" t where t.deviceid="+deviceId;
		List<?> graphicSetList = this.findCallSql(graphicSetSql);
		if(graphicSetList.size()>0){
			graphicSet=graphicSetList.get(0).toString().replaceAll("\r\n", "").replaceAll("\n", "");
		}
		
		String reportTemplateCodeSql="select t3.id,t3.singleWellDailyReportTemplate,t3.productionreporttemplate,t3.calculateType "
				+ " from "+deviceTableName+" t,tbl_protocolreportinstance t2,tbl_report_unit_conf t3 "
				+ " where t.reportinstancecode=t2.code and t2.unitid=t3.id "
				+ " and t.id="+deviceId;
		List<?> reportTemplateCodeList = this.findCallSql(reportTemplateCodeSql);
		if(reportTemplateCodeList.size()>0){
			Object[] obj=(Object[]) reportTemplateCodeList.get(0);
			reportUnitId=(obj[0]+"").replaceAll("null", "");
			reportTemplateCode=(obj[1]+"").replaceAll("null", "");
			reportUnitCalculateType=StringManagerUtils.stringToInteger(obj[3]+"");
		}
		
		if(reportUnitCalculateType==1){
			calTotalTableName="VIW_SRPDAILYCALCULATIONDATA";
		}else if(reportUnitCalculateType==2){
			calTotalTableName="VIW_PCPDAILYCALCULATIONDATA";
		}
		
		String reportCurveItemSql="select t.itemname,t.itemcode,t.reportcurveconf,t.datatype,t.totalType,t.dataSource "
				+ " from TBL_REPORT_ITEMS2UNIT_CONF t,tbl_protocolreportinstance t2,"+deviceTableName+" t3 "
				+ " where t.unitid=t2.unitid and t2.code=t3.reportinstancecode"
				+ " and t3.id="+deviceId
				+ " and t.sort>=0"
				+ " and t.reportType= "+reportType
				+ " and t.reportcurveconf is not null "
				+ " and (t.showlevel is null or t.showlevel>=(select r.showlevel from tbl_user u,tbl_role r where u.user_type=r.role_level and u.user_no="+userNo+"))"
				+ " order by t.sort,t.id";
		List<ReportUnitItem> reportCurveItemList=new ArrayList<ReportUnitItem>();
		List<?> reportCurveItemQuertList = this.findCallSql(reportCurveItemSql);
		for(int i=0;i<reportCurveItemQuertList.size();i++){
			Object[] reportCurveItemObj=(Object[]) reportCurveItemQuertList.get(i);
			ReportUnitItem reportUnitItem=new ReportUnitItem();
			reportUnitItem.setItemName(reportCurveItemObj[0]+"");
			reportUnitItem.setItemCode(reportCurveItemObj[1]+"");
			reportUnitItem.setReportCurveConf((reportCurveItemObj[2]+"").replaceAll("null", ""));
			reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportCurveItemObj[3]+""));
			reportUnitItem.setTotalType(StringManagerUtils.stringToInteger(reportCurveItemObj[4]+""));
			reportUnitItem.setDataSource(reportCurveItemObj[5]+"");
			if(reportUnitItem.getDataType()==2){
				reportCurveItemList.add(reportUnitItem);
			}
		}
		
		Collections.sort(reportCurveItemList,new Comparator<ReportUnitItem>(){
			@Override
			public int compare(ReportUnitItem item1,ReportUnitItem item2){
				Gson gson = new Gson();
				java.lang.reflect.Type type=null;
				int sort1=0;
				int sort2=0;
				type = new TypeToken<CurveConf>() {}.getType();
				CurveConf curveConfObj1=gson.fromJson(item1.getReportCurveConf(), type);
				
				type = new TypeToken<CurveConf>() {}.getType();
				CurveConf curveConfObj2=gson.fromJson(item2.getReportCurveConf(), type);
				
				if(curveConfObj1!=null){
					sort1=curveConfObj1.getSort();
				}
				
				if(curveConfObj2!=null){
					sort2=curveConfObj2.getSort();
				}
				
				int diff=sort1-sort2;
				if(diff>0){
					return 1;
				}else if(diff<0){
					return -1;
				}
				return 0;
			}
		});
		
		
		itemsBuff.append("[");
		for(int i=0;i<reportCurveItemList.size();i++){
			itemsBuff.append("\""+reportCurveItemList.get(i).getItemName()+"\",");
		}
		if (itemsBuff.toString().endsWith(",")) {
			itemsBuff.deleteCharAt(itemsBuff.length() - 1);
		}
		itemsBuff.append("]");
		
		itemsCodeBuff.append("[");
		for(int i=0;i<reportCurveItemList.size();i++){
			itemsCodeBuff.append("\""+reportCurveItemList.get(i).getItemCode()+"\",");
		}
		if (itemsCodeBuff.toString().endsWith(",")) {
			itemsCodeBuff.deleteCharAt(itemsCodeBuff.length() - 1);
		}
		itemsCodeBuff.append("]");
		
		curveConfBuff.append("[");
		for(int i=0;i<reportCurveItemList.size();i++){
			curveConfBuff.append(""+reportCurveItemList.get(i).getReportCurveConf()+",");
		}
		if (curveConfBuff.toString().endsWith(",")) {
			curveConfBuff.deleteCharAt(curveConfBuff.length() - 1);
		}
		curveConfBuff.append("]");
		
		result_json.append("\"deviceName\":\""+deviceName+"\","
				+ "\"startDate\":\""+startDate+"\","
				+ "\"endDate\":\""+endDate+"\","
				+ "\"curveItems\":"+itemsBuff+","
				+ "\"curveItemCodes\":"+itemsCodeBuff+","
				+ "\"curveConf\":"+curveConfBuff+","
				+ "\"graphicSet\":"+graphicSet+","
				+ "\"list\":[");
		if(reportCurveItemList.size()>0){
			StringBuffer cueveSqlBuff = new StringBuffer();
			cueveSqlBuff.append("select t.id,to_char(t.calDate,'yyyy-mm-dd') as calDate");
			
			for(int i=0;i<reportCurveItemList.size();i++){
				String tableAlias="t";
				if(reportUnitCalculateType>0){
					if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("acquisition"), reportCurveItemList.get(i).getDataSource(), false)){
						tableAlias="t2";
					}else if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("calculate"), reportCurveItemList.get(i).getDataSource(), false)){
						if(StringManagerUtils.generalCalColumnFiter(reportCurveItemList.get(i).getItemCode())){
							tableAlias="t";
						}else{
							tableAlias="t3";
						}
					}
				}
				
				cueveSqlBuff.append(","+tableAlias+"."+reportCurveItemList.get(i).getItemCode()+"");
			}
			cueveSqlBuff.append(" from "+viewName+" t,"+tableName+" t2 ");
			if(reportUnitCalculateType>0){
				cueveSqlBuff.append(","+calTotalTableName+" t3");
				
			}
			cueveSqlBuff.append(" where t.id=t2.id");
			
			if(reportUnitCalculateType>0){
				cueveSqlBuff.append(" and t.deviceId=t3.deviceId and t.calDate=t3.calDate");
			}
			cueveSqlBuff.append(" and t.calDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')");
			cueveSqlBuff.append(" and t.deviceid="+deviceId);
			cueveSqlBuff.append(" order by t.calDate");
			
			List<?> reportCurveDataList = this.findCallSql(cueveSqlBuff.toString().replaceAll("@", ","));
			for(int i=0;i<reportCurveDataList.size();i++){
				Object[] obj=(Object[]) reportCurveDataList.get(i);
				result_json.append("{\"calDate\":\"" + obj[1] + "\",\"data\":[");
				for(int j=2;j<obj.length;j++){
					String addValue=obj[j]+"";
					if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("acquisition"), reportCurveItemList.get(j-2).getDataSource(), false)){
						if(StringManagerUtils.isNotNull(addValue)){
							String[] totalValueArr=addValue.split(";");
							
							addValue="";
							if(reportCurveItemList.get(j).getTotalType()==1 && totalValueArr.length>=1){
								addValue=totalValueArr[0];
							}else if(reportCurveItemList.get(j).getTotalType()==2 && totalValueArr.length>=2){
								addValue=totalValueArr[1];
							}else if(reportCurveItemList.get(j).getTotalType()==3 && totalValueArr.length>=3){
								addValue=totalValueArr[2];
							}else if(reportCurveItemList.get(j).getTotalType()==4 && totalValueArr.length>=4){
								addValue=totalValueArr[3];
							}else if(reportCurveItemList.get(j).getTotalType()==5 && totalValueArr.length>=5){
								addValue=totalValueArr[4];
							}else if(reportCurveItemList.get(j).getTotalType()==6 && totalValueArr.length>=6){
								addValue=totalValueArr[5];
							}
						}
					}
					result_json.append(addValue+",");
				}
				if (result_json.toString().endsWith(",")) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
				result_json.append("]},");
			}
			if (result_json.toString().endsWith(",")) {
				result_json.deleteCharAt(result_json.length() - 1);
			}
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getSingleWellDailyReportCurveData(Page pager, String orgId,String deviceType,String reportType,
			String deviceId,String deviceName,String calculateType,
			String startDate,String endDate,String reportDate,String reportInterval,
			int userNo)throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer itemsBuff = new StringBuffer();
		StringBuffer itemsCodeBuff = new StringBuffer();
		StringBuffer curveConfBuff = new StringBuffer();
		int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
		
		List<String> defaultTimeList= StringManagerUtils.getTimeRangeList(reportDate,offsetHour,StringManagerUtils.stringToInteger(reportInterval));
		
		Gson gson =new Gson();
		ConfigFile configFile=Config.getInstance().configFile;
		String reportTemplateCode="";
		String reportUnitId="";
		int reportUnitCalculateType=0;
		String graphicSet="{}";
		
		String graphicSetTableName="tbl_devicegraphicset";
		String deviceTableName="tbl_device";
		String tableName="TBL_TIMINGCALCULATIONDATA";
		String viewName="VIW_TIMINGCALCULATIONDATA";
		String calTotalTableName="";
		
		
		result_json.append("{\"success\":true,");
		String graphicSetSql="select t.graphicstyle from "+graphicSetTableName+" t where t.deviceId="+deviceId;
		List<?> graphicSetList = this.findCallSql(graphicSetSql);
		if(graphicSetList.size()>0){
			graphicSet=graphicSetList.get(0).toString().replaceAll("\r\n", "").replaceAll("\n", "");
		}
		
		String reportTemplateCodeSql="select t3.id,t3.singleWellDailyReportTemplate,t3.productionreporttemplate,t3.calculateType "
				+ " from "+deviceTableName+" t,tbl_protocolreportinstance t2,tbl_report_unit_conf t3 "
				+ " where t.reportinstancecode=t2.code and t2.unitid=t3.id "
				+ " and t.id="+deviceId;
		List<?> reportTemplateCodeList = this.findCallSql(reportTemplateCodeSql);
		if(reportTemplateCodeList.size()>0){
			Object[] obj=(Object[]) reportTemplateCodeList.get(0);
			reportUnitId=(obj[0]+"").replaceAll("null", "");
			reportTemplateCode=(obj[1]+"").replaceAll("null", "");
			reportUnitCalculateType=StringManagerUtils.stringToInteger(obj[3]+"");
		}
		
		if(reportUnitCalculateType==1){
			calTotalTableName="VIW_SRPTIMINGCALCULATIONDATA";
		}else if(reportUnitCalculateType==2){
			calTotalTableName="VIW_PCPTIMINGCALCULATIONDATA";
		}
		
		String reportCurveItemSql="select t.itemname,t.itemcode,t.reportcurveconf,t.datatype,t.prec,t.totalType,t.dataSource "
				+ " from TBL_REPORT_ITEMS2UNIT_CONF t,tbl_protocolreportinstance t2,"+deviceTableName+" t3 "
				+ " where t.unitid=t2.unitid and t2.code=t3.reportinstancecode"
				+ " and t3.id="+deviceId
				+ " and t.sort>=0"
				+ " and t.reportType= "+reportType
				+ " and t.reportcurveconf is not null "
				+ " and (t.showlevel is null or t.showlevel>=(select r.showlevel from tbl_user u,tbl_role r where u.user_type=r.role_level and u.user_no="+userNo+"))"
				+ " order by t.sort,t.id";
		List<ReportUnitItem> reportCurveItemList=new ArrayList<ReportUnitItem>();
		List<?> reportCurveItemQuertList = this.findCallSql(reportCurveItemSql);
		for(int i=0;i<reportCurveItemQuertList.size();i++){
			Object[] reportCurveItemObj=(Object[]) reportCurveItemQuertList.get(i);
			ReportUnitItem reportUnitItem=new ReportUnitItem();
			reportUnitItem.setItemName(reportCurveItemObj[0]+"");
			reportUnitItem.setItemCode(reportCurveItemObj[1]+"");
			reportUnitItem.setReportCurveConf((reportCurveItemObj[2]+"").replaceAll("null", ""));
			reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportCurveItemObj[3]+""));
			reportUnitItem.setTotalType(StringManagerUtils.stringToInteger(reportCurveItemObj[5]+""));
			reportUnitItem.setDataSource(reportCurveItemObj[6]+"");
			if(reportUnitItem.getDataType()==2){
				reportCurveItemList.add(reportUnitItem);
			}
		}
		
		Collections.sort(reportCurveItemList,new Comparator<ReportUnitItem>(){
			@Override
			public int compare(ReportUnitItem item1,ReportUnitItem item2){
				Gson gson = new Gson();
				java.lang.reflect.Type type=null;
				int sort1=0;
				int sort2=0;
				type = new TypeToken<CurveConf>() {}.getType();
				CurveConf curveConfObj1=gson.fromJson(item1.getReportCurveConf(), type);
				
				type = new TypeToken<CurveConf>() {}.getType();
				CurveConf curveConfObj2=gson.fromJson(item2.getReportCurveConf(), type);
				
				if(curveConfObj1!=null){
					sort1=curveConfObj1.getSort();
				}
				
				if(curveConfObj2!=null){
					sort2=curveConfObj2.getSort();
				}
				
				int diff=sort1-sort2;
				if(diff>0){
					return 1;
				}else if(diff<0){
					return -1;
				}
				return 0;
			}
		});
		
		
		itemsBuff.append("[");
		for(int i=0;i<reportCurveItemList.size();i++){
			itemsBuff.append("\""+reportCurveItemList.get(i).getItemName()+"\",");
		}
		if (itemsBuff.toString().endsWith(",")) {
			itemsBuff.deleteCharAt(itemsBuff.length() - 1);
		}
		itemsBuff.append("]");
		
		itemsCodeBuff.append("[");
		for(int i=0;i<reportCurveItemList.size();i++){
			itemsCodeBuff.append("\""+reportCurveItemList.get(i).getItemCode()+"\",");
		}
		if (itemsCodeBuff.toString().endsWith(",")) {
			itemsCodeBuff.deleteCharAt(itemsCodeBuff.length() - 1);
		}
		itemsCodeBuff.append("]");
		
		curveConfBuff.append("[");
		for(int i=0;i<reportCurveItemList.size();i++){
			curveConfBuff.append(""+reportCurveItemList.get(i).getReportCurveConf()+",");
		}
		if (curveConfBuff.toString().endsWith(",")) {
			curveConfBuff.deleteCharAt(curveConfBuff.length() - 1);
		}
		curveConfBuff.append("]");
		
		result_json.append("\"deviceName\":\""+deviceName+"\","
				+ "\"startDate\":\""+startDate+"\","
				+ "\"endDate\":\""+endDate+"\","
				+ "\"reportDate\":\""+reportDate+"\","
				+ "\"curveItems\":"+itemsBuff+","
				+ "\"curveItemCodes\":"+itemsCodeBuff+","
				+ "\"curveConf\":"+curveConfBuff+","
				+ "\"graphicSet\":"+graphicSet+","
				+ "\"list\":[");
		if(reportCurveItemList.size()>0){
			StringBuffer cueveSqlBuff = new StringBuffer();
			
			cueveSqlBuff.append("select t.id,to_char(t.calTime,'yyyy-mm-dd hh24:mi:ss') as calTime");
			for(int i=0;i<reportCurveItemList.size();i++){
				String tableAlias="t";
				if(reportUnitCalculateType>0){
					if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("acquisition"), reportCurveItemList.get(i).getDataSource(), false)){
						tableAlias="t2";
					}else if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("calculate"), reportCurveItemList.get(i).getDataSource(), false)){
						if(StringManagerUtils.generalCalColumnFiter(reportCurveItemList.get(i).getItemCode())){
							tableAlias="t";
						}else{
							tableAlias="t3";
						}
					}
				}
				
				cueveSqlBuff.append(","+tableAlias+"."+reportCurveItemList.get(i).getItemCode()+"");
			}
			cueveSqlBuff.append(" from "+viewName+" t,"+tableName+" t2 ");
			if(reportUnitCalculateType>0){
				cueveSqlBuff.append(","+calTotalTableName+" t3");
				
			}
			cueveSqlBuff.append(" where t.id=t2.id");
			
			if(reportUnitCalculateType>0){
				cueveSqlBuff.append(" and t.deviceId=t3.deviceId and t.calTime=t3.calTime");
			}
			cueveSqlBuff.append(" and t.calTime > to_date('"+reportDate+"','yyyy-mm-dd')+"+offsetHour+"/24 and t.calTime<= to_date('"+reportDate+"','yyyy-mm-dd')+"+offsetHour+"/24+1");
			if(StringManagerUtils.stringToInteger(reportInterval)>1){
				cueveSqlBuff.append(" and to_char(t.calTime,'yyyy-mm-dd hh24:mi:ss') in ("+StringManagerUtils.joinStringArr2(defaultTimeList, ",")+")");
			}
			cueveSqlBuff.append(" and t.deviceid="+deviceId);
			cueveSqlBuff.append(" order by t.calTime");
			
			List<?> reportCurveDataList = this.findCallSql(cueveSqlBuff.toString().replaceAll("@", ","));
			for(int i=0;i<reportCurveDataList.size();i++){
				Object[] obj=(Object[]) reportCurveDataList.get(i);
				result_json.append("{\"calDate\":\"" + obj[1] + "\",\"data\":[");
				for(int j=2;j<obj.length;j++){
					String addValue=obj[j]+"";
					int idx=j-2;
					if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("acquisition"), reportCurveItemList.get(idx).getDataSource(), false)){
						if(StringManagerUtils.isNotNull(addValue)){
							String[] totalValueArr=addValue.split(";");
							addValue="";
							if(reportCurveItemList.get(idx).getTotalType()==1 && totalValueArr.length>=1){
								addValue=totalValueArr[0];
							}else if(reportCurveItemList.get(idx).getTotalType()==2 && totalValueArr.length>=2){
								addValue=totalValueArr[1];
							}else if(reportCurveItemList.get(idx).getTotalType()==3 && totalValueArr.length>=3){
								addValue=totalValueArr[2];
							}else if(reportCurveItemList.get(idx).getTotalType()==4 && totalValueArr.length>=4){
								addValue=totalValueArr[3];
							}else if(reportCurveItemList.get(idx).getTotalType()==5 && totalValueArr.length>=5){
								addValue=totalValueArr[4];
							}else if(reportCurveItemList.get(idx).getTotalType()==6 && totalValueArr.length>=6){
								addValue=totalValueArr[5];
							}
						}
					}
					
					result_json.append(addValue+",");
				}
				if (result_json.toString().endsWith(",")) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
				result_json.append("]},");
			}
			if (result_json.toString().endsWith(",")) {
				result_json.deleteCharAt(result_json.length() - 1);
			}
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProductionDailyReportCurveData(Page pager, String orgId,String selectedOrgName,String deviceType,
			String reportType,
			String unitId,String instanceCode,
			String deviceName,
			String startDate,String endDate,
			int userNo,String language)throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer itemsBuff = new StringBuffer();
		StringBuffer itemsCodeBuff = new StringBuffer();
		StringBuffer curveConfBuff = new StringBuffer();
		
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
		Gson gson =new Gson();
		ConfigFile configFile=Config.getInstance().configFile;
		
		String graphicSet="{}";
		
		
		String reportTemplateCode="";
		String reportUnitId="";
		int reportUnitCalculateType=0;
		String deviceTableName="tbl_device";
		String tableName="TBL_DAILYCALCULATIONDATA";
		String viewName="VIW_DAILYCALCULATIONDATA";
		String calTotalTableName="";
		
		result_json.append("{\"success\":true,");
		String reportTemplateCodeSql="select t3.id,t3.singleWellRangeReportTemplate,t3.productionreporttemplate,t3.calculateType "
				+ " from "+deviceTableName+" t,tbl_protocolreportinstance t2,tbl_report_unit_conf t3 "
				+ " where t.reportinstancecode=t2.code and t2.unitid=t3.id "
				+ " and t3.id="+unitId;
		List<?> reportTemplateCodeList = this.findCallSql(reportTemplateCodeSql);
		if(reportTemplateCodeList.size()>0){
			Object[] obj=(Object[]) reportTemplateCodeList.get(0);
			reportUnitId=(obj[0]+"").replaceAll("null", "");
			reportTemplateCode=(obj[2]+"").replaceAll("null", "");
			reportUnitCalculateType=StringManagerUtils.stringToInteger(obj[3]+"");
		}
		
		if(reportUnitCalculateType==1){
			calTotalTableName="VIW_SRPDAILYCALCULATIONDATA";
		}else if(reportUnitCalculateType==2){
			calTotalTableName="VIW_PCPDAILYCALCULATIONDATA";
		}
		
		String reportCurveItemSql="select t.itemname,t.itemcode,t.reportcurveconf,t.datatype,t.curvestattype,t.totalType,t.dataSource "
				+ " from TBL_REPORT_ITEMS2UNIT_CONF t "
				+ " where t.unitid= "+unitId
				+ " and t.sort>=0"
				+ " and t.reportType= "+reportType
				+ " and t.reportcurveconf is not null "
				+ " and (t.showlevel is null or t.showlevel>=(select r.showlevel from tbl_user u,tbl_role r where u.user_type=r.role_level and u.user_no="+userNo+"))"
				+ " order by t.sort,t.id";
		List<ReportUnitItem> reportCurveItemList=new ArrayList<ReportUnitItem>();
		List<?> reportCurveItemQuertList = this.findCallSql(reportCurveItemSql);
		for(int i=0;i<reportCurveItemQuertList.size();i++){
			Object[] reportCurveItemObj=(Object[]) reportCurveItemQuertList.get(i);
			ReportUnitItem reportUnitItem=new ReportUnitItem();
			reportUnitItem.setItemName(reportCurveItemObj[0]+"");
			reportUnitItem.setItemCode(reportCurveItemObj[1]+"");
			reportUnitItem.setReportCurveConf((reportCurveItemObj[2]+"").replaceAll("null", ""));
			reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportCurveItemObj[3]+""));
			
			reportUnitItem.setTotalType(StringManagerUtils.stringToInteger(reportCurveItemObj[5]+""));
			reportUnitItem.setDataSource(reportCurveItemObj[6]+"");
			
			String curveStatTypeStr=reportCurveItemObj[4]+"";
			reportUnitItem.setCurveStatType(StringManagerUtils.isNumber(curveStatTypeStr)?StringManagerUtils.stringToInteger(curveStatTypeStr):null);
			if(StringManagerUtils.isNumber(curveStatTypeStr) && reportUnitItem.getDataType()==2 ){
				reportCurveItemList.add(reportUnitItem);
			}
		}
		
		Collections.sort(reportCurveItemList,new Comparator<ReportUnitItem>(){
			@Override
			public int compare(ReportUnitItem item1,ReportUnitItem item2){
				Gson gson = new Gson();
				java.lang.reflect.Type type=null;
				int sort1=0;
				int sort2=0;
				type = new TypeToken<CurveConf>() {}.getType();
				CurveConf curveConfObj1=gson.fromJson(item1.getReportCurveConf(), type);
				
				type = new TypeToken<CurveConf>() {}.getType();
				CurveConf curveConfObj2=gson.fromJson(item2.getReportCurveConf(), type);
				
				if(curveConfObj1!=null){
					sort1=curveConfObj1.getSort();
				}
				
				if(curveConfObj2!=null){
					sort2=curveConfObj2.getSort();
				}
				
				int diff=sort1-sort2;
				if(diff>0){
					return 1;
				}else if(diff<0){
					return -1;
				}
				return 0;
			}
		});
		
		itemsBuff.append("[");
		for(int i=0;i<reportCurveItemList.size();i++){
			String statTypeName="";
			if(reportCurveItemList.get(i).getCurveStatType()==1){
				statTypeName=languageResourceMap.get("curveStatType_sum");
			}else if(reportCurveItemList.get(i).getCurveStatType()==2){
				statTypeName=languageResourceMap.get("curveStatType_avg");
			}else if(reportCurveItemList.get(i).getCurveStatType()==3){
				statTypeName=languageResourceMap.get("curveStatType_max");
			}else if(reportCurveItemList.get(i).getCurveStatType()==4){
				statTypeName=languageResourceMap.get("curveStatType_min");
			}else{
				statTypeName=languageResourceMap.get("curveStatType_sum");
			}
			
			itemsBuff.append("\""+reportCurveItemList.get(i).getItemName()+statTypeName+"\",");
		}
		if (itemsBuff.toString().endsWith(",")) {
			itemsBuff.deleteCharAt(itemsBuff.length() - 1);
		}
		itemsBuff.append("]");
		
		itemsCodeBuff.append("[");
		for(int i=0;i<reportCurveItemList.size();i++){
			itemsCodeBuff.append("\""+reportCurveItemList.get(i).getItemCode()+"\",");
		}
		if (itemsCodeBuff.toString().endsWith(",")) {
			itemsCodeBuff.deleteCharAt(itemsCodeBuff.length() - 1);
		}
		itemsCodeBuff.append("]");
		
		curveConfBuff.append("[");
		for(int i=0;i<reportCurveItemList.size();i++){
			curveConfBuff.append(""+reportCurveItemList.get(i).getReportCurveConf()+",");
		}
		if (curveConfBuff.toString().endsWith(",")) {
			curveConfBuff.deleteCharAt(curveConfBuff.length() - 1);
		}
		curveConfBuff.append("]");
		
		result_json.append("\"deviceName\":\""+deviceName+"\","
				+ "\"selectedOrgName\":\""+selectedOrgName+"\","
				+ "\"startDate\":\""+startDate+"\","
				+ "\"endDate\":\""+endDate+"\","
				+ "\"curveItems\":"+itemsBuff+","
				+ "\"curveItemCodes\":"+itemsCodeBuff+","
				+ "\"curveConf\":"+curveConfBuff+","
				+ "\"graphicSet\":"+graphicSet+","
				+ "\"list\":[");
		if(reportCurveItemList.size()>0){
			boolean acqSign=false;
			for(ReportUnitItem reportUnitItem:reportCurveItemList){
				if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("acquisition"), reportUnitItem.getDataSource(), false)){
					acqSign=true;
					break;
				}
			}
			
			if(!acqSign){
				StringBuffer cueveSqlBuff = new StringBuffer();
				cueveSqlBuff.append("select to_char(t.calDate,'yyyy-mm-dd') as calDate");
				for(int i=0;i<reportCurveItemList.size();i++){
					String tableAlias="t";
					if(reportUnitCalculateType>0){
						if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("acquisition"), reportCurveItemList.get(i).getDataSource(), false)){
							tableAlias="t2";
						}else if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("calculate"), reportCurveItemList.get(i).getDataSource(), false)){
							if(StringManagerUtils.generalCalColumnFiter(reportCurveItemList.get(i).getItemCode())){
								tableAlias="t";
							}else{
								tableAlias="t3";
							}
						}
					}
					
					
					String statType="";
					if(reportCurveItemList.get(i).getCurveStatType()==1){
						statType="sum";
					}else if(reportCurveItemList.get(i).getCurveStatType()==2){
						statType="avg";
					}else if(reportCurveItemList.get(i).getCurveStatType()==3){
						statType="max";
					}else if(reportCurveItemList.get(i).getCurveStatType()==4){
						statType="min";
					}else{
						statType="sum";
					}
					cueveSqlBuff.append(","+statType+"("+tableAlias+"."+reportCurveItemList.get(i).getItemCode()+")"+"");
				}

				cueveSqlBuff.append(" from "+viewName+" t,"+tableName+" t2 ");
				if(reportUnitCalculateType>0){
					cueveSqlBuff.append(","+calTotalTableName+" t3");
					
				}
				cueveSqlBuff.append(" where t.id=t2.id");
				
				if(reportUnitCalculateType>0){
					cueveSqlBuff.append(" and t.deviceId=t3.deviceId and t.calDate=t3.calDate");
				}
				cueveSqlBuff.append(" and t.org_id in ("+orgId+") ");
				cueveSqlBuff.append(" and t.reportinstancecode='"+instanceCode+"'");
				
				if(StringManagerUtils.isNum(deviceType)){
					cueveSqlBuff.append(" and t.deviceType="+deviceType+"");
				}else{
					cueveSqlBuff.append(" and t.deviceType in("+deviceType+")");
				}
				
				
				cueveSqlBuff.append(" and t.calDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')");
				cueveSqlBuff.append(" group by t.calDate");
				cueveSqlBuff.append(" order by t.calDate");
				
				List<?> reportCurveDataList = this.findCallSql(cueveSqlBuff.toString().replaceAll("@", ","));
				for(int i=0;i<reportCurveDataList.size();i++){
					Object[] obj=(Object[]) reportCurveDataList.get(i);
					result_json.append("{\"calDate\":\"" + obj[0] + "\",\"data\":[");
					for(int j=1;j<obj.length;j++){
						result_json.append(obj[j]+",");
					}
					if (result_json.toString().endsWith(",")) {
						result_json.deleteCharAt(result_json.length() - 1);
					}
					result_json.append("]},");
				}
				if (result_json.toString().endsWith(",")) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
			}else{
				StringBuffer cueveSqlBuff = new StringBuffer();
				cueveSqlBuff.append("select to_char(calDate,'yyyy-mm-dd') as calDate");
				for(int i=0;i<reportCurveItemList.size();i++){
					String tableAlias="t";
					if(reportUnitCalculateType>0){
						if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("acquisition"), reportCurveItemList.get(i).getDataSource(), false)){
							tableAlias="t2";
						}else if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("calculate"), reportCurveItemList.get(i).getDataSource(), false)){
							if(StringManagerUtils.generalCalColumnFiter(reportCurveItemList.get(i).getItemCode())){
								tableAlias="t";
							}else{
								tableAlias="t3";
							}
						}
					}
					cueveSqlBuff.append(","+tableAlias+"."+reportCurveItemList.get(i).getItemCode()+"");
				}

				cueveSqlBuff.append(" from "+viewName+" t,"+tableName+" t2 ");
				if(reportUnitCalculateType>0){
					cueveSqlBuff.append(","+calTotalTableName+" t3");
					
				}
				cueveSqlBuff.append(" where t.id=t2.id");
				
				if(reportUnitCalculateType>0){
					cueveSqlBuff.append(" and t.deviceId=t3.deviceId and t.calTime=t3.calTime");
				}
				cueveSqlBuff.append(" and t.org_id in ("+orgId+") ");
				cueveSqlBuff.append(" and t.reportinstancecode='"+instanceCode+"'");
				cueveSqlBuff.append(" and t.calDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')");
				cueveSqlBuff.append(" order by t.calDate");
				List<?> reportCurveDataList = this.findCallSql(cueveSqlBuff.toString().replaceAll("@", ","));
				
				Map<String,List<Object[]>> reportCurveDataMap=new HashMap<>();
				for(int i=0;i<reportCurveDataList.size();i++){
					Object[] obj=(Object[]) reportCurveDataList.get(i);
					String calDate=obj[0]+"";
					if(reportCurveDataMap.containsKey(calDate)){
						reportCurveDataMap.get(calDate).add(obj);
					}else{
						List<Object[]> list=new ArrayList<>();
						list.add(obj);
						reportCurveDataMap.put(calDate, list);
					}
				}
				
				Iterator<Map.Entry<String,List<Object[]>>> iterator = reportCurveDataMap.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<String,List<Object[]>> entry = iterator.next();
					String calDate=entry.getKey();
					List<Object[]> dataList = entry.getValue();
					List<Float> totalDataList=new ArrayList<>();
					
					int curveCount=reportCurveItemList.size();
					for(int i=0;i<curveCount;i++){
						int totalCount=dataList.size();
						float sum=0;
						float avg=0;
						for(int j=0;j<dataList.size();j++){
							String addValue=dataList.get(j)[i+1]+"";
							if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("acquisition"), reportCurveItemList.get(i).getDataSource(), false)){
								String[] totalValueArr=addValue.split(";");
								addValue="";
								if(reportCurveItemList.get(j).getTotalType()==1 && totalValueArr.length>=1){
									addValue=totalValueArr[0];
								}else if(reportCurveItemList.get(j).getTotalType()==2 && totalValueArr.length>=2){
									addValue=totalValueArr[1];
								}else if(reportCurveItemList.get(j).getTotalType()==3 && totalValueArr.length>=3){
									addValue=totalValueArr[2];
								}else if(reportCurveItemList.get(j).getTotalType()==4 && totalValueArr.length>=4){
									addValue=totalValueArr[3];
								}else if(reportCurveItemList.get(j).getTotalType()==5 && totalValueArr.length>=5){
									addValue=totalValueArr[4];
								}else if(reportCurveItemList.get(j).getTotalType()==6 && totalValueArr.length>=6){
									addValue=totalValueArr[5];
								}
							}
							if(StringManagerUtils.isNum(addValue) && StringManagerUtils.isNumber(addValue)){
								sum+=(StringManagerUtils.stringToFloat(addValue));
							}
						}
						if(totalCount>0){
							avg=sum/totalCount;
						}
						if(reportCurveItemList.get(i).getCurveStatType()==1){
							totalDataList.add(sum);
						}else if(reportCurveItemList.get(i).getCurveStatType()==2){
							totalDataList.add(avg);
						}else{
							totalDataList.add(sum);
						}
					}
					
					result_json.append("{\"calDate\":\"" + calDate + "\",\"data\":[");
					for(int i=0;i<totalDataList.size();i++){
						result_json.append(totalDataList.get(i)+",");
					}
					if (result_json.toString().endsWith(",")) {
						result_json.deleteCharAt(result_json.length() - 1);
					}
					result_json.append("]},");
					
				}
				if (result_json.toString().endsWith(",")) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
			}
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getReportQueryCurveSetData(String deviceId,String deviceType,String reportType,int userNo)throws Exception {
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String deviceTableName="tbl_device";
		String graphicSetTableName="tbl_devicegraphicset";
		String graphicSetSql="select t.graphicstyle from "+graphicSetTableName+" t where t.deviceId="+deviceId;
		String curveItemsSql="select t.itemname,t.itemcode,t.reportcurveconf,t.datatype "
				+ " from TBL_REPORT_ITEMS2UNIT_CONF t,tbl_protocolreportinstance t2,"+deviceTableName+" t3 "
				+ " where t.unitid=t2.unitid and t2.code=t3.reportinstancecode"
				+ " and t3.id="+deviceId
				+ " and t.sort>=0"
				+ " and t.reportType="+reportType
				+ " and t.reportcurveconf is not null "
				+ " and (t.showlevel is null or t.showlevel>=(select r.showlevel from tbl_user u,tbl_role r where u.user_type=r.role_level and u.user_no="+userNo+"))"
				+ " order by t.sort";
		List<?> graphicSetList = this.findCallSql(graphicSetSql);
		List<?> curveItemList = this.findCallSql(curveItemsSql);
		List<ReportUnitItem> reportCurveItemList=new ArrayList<ReportUnitItem>();
		List<GraphicSetData.Graphic> graphicList=null;
		if(graphicSetList.size()>0){
			String graphicSet=graphicSetList.get(0).toString().replaceAll("\r\n", "").replaceAll("\n", "");
			type = new TypeToken<GraphicSetData>() {}.getType();
			GraphicSetData graphicSetData=gson.fromJson(graphicSet, type);
			if(graphicSetData!=null){
				if(StringManagerUtils.stringToInteger(reportType)==0){
					if(graphicSetData.getReport()!=null){
						graphicList=graphicSetData.getReport();
					}
				}else if(StringManagerUtils.stringToInteger(reportType)==2){
					if(graphicSetData.getDailyReport()!=null){
						graphicList=graphicSetData.getDailyReport();
					}
				}
			}
		}
		
		for(int i=0;i<curveItemList.size();i++){
			Object[] reportCurveItemObj=(Object[]) curveItemList.get(i);
			ReportUnitItem reportUnitItem=new ReportUnitItem();
			reportUnitItem.setItemName(reportCurveItemObj[0]+"");
			reportUnitItem.setItemCode(reportCurveItemObj[1]+"");
			reportUnitItem.setReportCurveConf((reportCurveItemObj[2]+"").replaceAll("null", ""));
			reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportCurveItemObj[3]+""));
			if(reportUnitItem.getDataType()==2){
				reportCurveItemList.add(reportUnitItem);
			}
		}
		
		Collections.sort(reportCurveItemList,new Comparator<ReportUnitItem>(){
			@Override
			public int compare(ReportUnitItem item1,ReportUnitItem item2){
				Gson gson = new Gson();
				java.lang.reflect.Type type=null;
				int sort1=0;
				int sort2=0;
				type = new TypeToken<CurveConf>() {}.getType();
				CurveConf curveConfObj1=gson.fromJson(item1.getReportCurveConf(), type);
				
				type = new TypeToken<CurveConf>() {}.getType();
				CurveConf curveConfObj2=gson.fromJson(item2.getReportCurveConf(), type);
				
				if(curveConfObj1!=null){
					sort1=curveConfObj1.getSort();
				}
				
				if(curveConfObj2!=null){
					sort2=curveConfObj2.getSort();
				}
				
				int diff=sort1-sort2;
				if(diff>0){
					return 1;
				}else if(diff<0){
					return -1;
				}
				return 0;
			}
		});
		
		
		result_json.append("{\"success\":true,\"totalCount\":"+curveItemList.size()+",\"totalRoot\":[");
		for(int i=0;i<reportCurveItemList.size();i++){
			String curveName=reportCurveItemList.get(i).getItemName();
			String itemCode=reportCurveItemList.get(i).getItemCode();
			String itemType="3";//3-汇总项
			String yAxisMaxValue="";
			String yAxisMinValue="";
			result_json.append("{\"curveName\":\"" + curveName + "\",\"itemCode\":\"" + itemCode + "\",\"itemType\":\""+itemType+"\",");
			
			
			if(graphicList!=null && graphicList.size()>0){
				for(int j=0;j<graphicList.size();j++){
					if(itemCode.equalsIgnoreCase(graphicList.get(j).getItemCode())){
						yAxisMaxValue=graphicList.get(j).getyAxisMaxValue();
						yAxisMinValue=graphicList.get(j).getyAxisMinValue();
						break;
					}
				}
			}
			
			result_json.append("\"yAxisMaxValue\":\""+yAxisMaxValue+"\",");
			result_json.append("\"yAxisMinValue\":\""+yAxisMinValue+"\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		
		result_json.append("]}");
		return result_json.toString();
	}
	
	public int setReportDataGraphicInfo(String deviceId,String deviceType,String reportType,String graphicSetSaveDataStr)throws Exception {
		int result=0;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		
		if(StringManagerUtils.stringToInteger(deviceId)>0){
			String deviceTableName="tbl_device";
			String graphicSetTableName="tbl_devicegraphicset";
			
			type = new TypeToken<GraphicSetData>() {}.getType();
			GraphicSetData graphicSetSaveData=gson.fromJson(graphicSetSaveDataStr, type);
			String graphicSetSql="select t.graphicstyle from "+graphicSetTableName+" t where t.deviceId="+deviceId;
			List<?> graphicSetList = this.findCallSql(graphicSetSql);
			GraphicSetData graphicSetData=null;
			if(graphicSetList.size()>0){
				String graphicSet=graphicSetList.get(0).toString().replaceAll("\r\n", "").replaceAll("\n", "");
				type = new TypeToken<GraphicSetData>() {}.getType();
				graphicSetData=gson.fromJson(graphicSet, type);
			}
			String saveStr=graphicSetSaveDataStr;
			if(graphicSetData!=null){
				if(StringManagerUtils.stringToInteger(reportType)==0){
					if(graphicSetData.getReport()!=null&&graphicSetData.getReport().size()>0){
						for(int i=0;i<graphicSetSaveData.getReport().size();i++){
							boolean isExit=false;
							for(int j=0;j<graphicSetData.getReport().size();j++){
								if(graphicSetSaveData.getReport().get(i).getItemCode().equalsIgnoreCase(graphicSetData.getReport().get(j).getItemCode())){
									isExit=true;
									graphicSetData.getReport().get(j).setyAxisMaxValue(graphicSetSaveData.getReport().get(i).getyAxisMaxValue());
									graphicSetData.getReport().get(j).setyAxisMinValue(graphicSetSaveData.getReport().get(i).getyAxisMinValue());
									break;
								}
							}
							if(!isExit){
								graphicSetData.getReport().add(graphicSetSaveData.getReport().get(i));
							}
						}
					}else{
						graphicSetData.setReport(graphicSetSaveData.getReport());
					}
				}else if(StringManagerUtils.stringToInteger(reportType)==2){
					if(graphicSetData.getDailyReport()!=null&&graphicSetData.getDailyReport().size()>0){
						for(int i=0;i<graphicSetSaveData.getDailyReport().size();i++){
							boolean isExit=false;
							for(int j=0;j<graphicSetData.getDailyReport().size();j++){
								if(graphicSetSaveData.getDailyReport().get(i).getItemCode().equalsIgnoreCase(graphicSetData.getDailyReport().get(j).getItemCode())){
									isExit=true;
									graphicSetData.getDailyReport().get(j).setyAxisMaxValue(graphicSetSaveData.getDailyReport().get(i).getyAxisMaxValue());
									graphicSetData.getDailyReport().get(j).setyAxisMinValue(graphicSetSaveData.getDailyReport().get(i).getyAxisMinValue());
									break;
								}
							}
							if(!isExit){
								graphicSetData.getDailyReport().add(graphicSetSaveData.getDailyReport().get(i));
							}
						}
					}else{
						graphicSetData.setDailyReport(graphicSetSaveData.getDailyReport());
					}
				}
				saveStr=gson.toJson(graphicSetData);
			}
			String sql="select t.deviceId from "+graphicSetTableName+" t where t.deviceId="+deviceId;
			String updateSql="";
			List<?> list = this.findCallSql(sql);
			if(list.size()>0){
				updateSql="update "+graphicSetTableName+" t set t.graphicstyle='"+saveStr+"' where t.deviceId="+deviceId;
			}else{
				updateSql="insert into "+graphicSetTableName+" (deviceId,graphicstyle) values("+deviceId+",'"+saveStr+"')";
			}
			result=this.getBaseDao().updateOrDeleteBySql(updateSql);
		}
		return result;
	}
	
	public int saveSingleWellRangeDailyReportData(String deviceId,String deviceName,String deviceType,String data) {
		int result=0;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		type = new TypeToken<CellEditData>() {}.getType();
		CellEditData cellEditData=gson.fromJson(data, type);
		
		if(cellEditData!=null && cellEditData.getContentUpdateList().size()>0){
			String tableName="tbl_dailycalculationdata";
			String updateSql="";
			for(int i=0;i<cellEditData.getContentUpdateList().size();i++){
				try {
					if(StringManagerUtils.isNotNull(cellEditData.getContentUpdateList().get(i).getColumn())){
						String updateValue=cellEditData.getContentUpdateList().get(i).getNewValue();
						if(!cellEditData.getContentUpdateList().get(i).getHeader()){
							if( (!StringManagerUtils.isNum(updateValue)) && (!StringManagerUtils.isNumber(updateValue)) ){
								updateValue="'"+updateValue+"'";
							}
							updateSql="update "+tableName+" t set t."+cellEditData.getContentUpdateList().get(i).getColumn()+"="+updateValue+" where t.id="+cellEditData.getContentUpdateList().get(i).getRecordId();
						}else{
							updateValue=updateValue.replaceAll(" ", " ").replaceAll("：", ":");
							List<String> updateValueList=new ArrayList<>();
							String[] arr = new String[updateValue.length()];
							StringBuffer updateValueBuff = new StringBuffer();
							StringBuffer labelBuff = new StringBuffer();
							for (int j = 0; j < updateValue.length(); j++) {
						        arr[j] = String.valueOf(updateValue.charAt(j));
						    }
							for (int j = 0; j < arr.length; j++) {
								if(!" ".equals(arr[j])){
									updateValueList.add(arr[j]);
								}else{
									if(updateValueList.size()>1 && ( !" ".equals(updateValueList.get(updateValueList.size()-1)) ) && ( !":".equals(updateValueList.get(updateValueList.size()-1)) )){
										updateValueList.add(arr[j]);
									}
								}
							}
							for(int j=0;j<updateValueList.size();j++){
								updateValueBuff.append(updateValueList.get(j));
							}
							String[] labelArr=updateValueBuff.toString().split(" ");
							for(int j=0;j<labelArr.length;j++){
								String[] everyLabelArr=labelArr[j].split(":");
								if(everyLabelArr.length==2){
									labelBuff.append(everyLabelArr[1]+",");
								}else{
									labelBuff.append(",");
								}
							}
							if(labelBuff.toString().endsWith(",")){
								labelBuff.deleteCharAt(labelBuff.length() - 1);
							}
							updateSql="update "+tableName+" t set t.headerlabelinfo='"+labelBuff.toString()+"' where t.deviceId="+deviceId+" and t.caldate=( select max(t2.caldate) from "+tableName+" t2 where t2.deviceId=t.deviceId )";
						}
						if(StringManagerUtils.isNotNull(updateSql)){
							result+=this.getBaseDao().updateOrDeleteBySql(updateSql);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		return result;
	}
	
	public int saveSingleWellDailyDailyReportData(String deviceId,String deviceName,String deviceType,String data) {
		int result=0;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		type = new TypeToken<CellEditData>() {}.getType();
		CellEditData cellEditData=gson.fromJson(data, type);
		
		if(cellEditData!=null && cellEditData.getContentUpdateList().size()>0){
			String tableName="tbl_timingcalculationdata";
			String updateSql="";
			for(int i=0;i<cellEditData.getContentUpdateList().size();i++){
				try {
					if(StringManagerUtils.isNotNull(cellEditData.getContentUpdateList().get(i).getColumn())){
						String updateValue=cellEditData.getContentUpdateList().get(i).getNewValue();
						if(!cellEditData.getContentUpdateList().get(i).getHeader()){
							if( (!StringManagerUtils.isNum(updateValue)) && (!StringManagerUtils.isNumber(updateValue)) ){
								updateValue="'"+updateValue+"'";
							}
							updateSql="update "+tableName+" t set t."+cellEditData.getContentUpdateList().get(i).getColumn()+"="+updateValue+" where t.id="+cellEditData.getContentUpdateList().get(i).getRecordId();
						}else{
							updateValue=updateValue.replaceAll(" ", " ").replaceAll("：", ":");
							List<String> updateValueList=new ArrayList<>();
							String[] arr = new String[updateValue.length()];
							StringBuffer updateValueBuff = new StringBuffer();
							StringBuffer labelBuff = new StringBuffer();
							for (int j = 0; j < updateValue.length(); j++) {
						        arr[j] = String.valueOf(updateValue.charAt(j));
						    }
							for (int j = 0; j < arr.length; j++) {
								if(!" ".equals(arr[j])){
									updateValueList.add(arr[j]);
								}else{
									if(updateValueList.size()>1 && ( !" ".equals(updateValueList.get(updateValueList.size()-1)) ) && ( !":".equals(updateValueList.get(updateValueList.size()-1)) )){
										updateValueList.add(arr[j]);
									}
								}
							}
							for(int j=0;j<updateValueList.size();j++){
								updateValueBuff.append(updateValueList.get(j));
							}
							String[] labelArr=updateValueBuff.toString().split(" ");
							for(int j=0;j<labelArr.length;j++){
								String[] everyLabelArr=labelArr[j].split(":");
								if(everyLabelArr.length==2){
									labelBuff.append(everyLabelArr[1]+",");
								}else{
									labelBuff.append(",");
								}
							}
							if(labelBuff.toString().endsWith(",")){
								labelBuff.deleteCharAt(labelBuff.length() - 1);
							}
							updateSql="update "+tableName+" t set t.headerlabelinfo='"+labelBuff.toString()+"' where t.deviceId="+deviceId+" and t.caltime=( select max(t2.caltime) from "+tableName+" t2 where t2.deviceId=t.deviceId )";
						}
						if(StringManagerUtils.isNotNull(updateSql)){
							result+=this.getBaseDao().updateOrDeleteBySql(updateSql);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		return result;
	}
	
	public String getDeviceList(String orgId,String deviceName,String deviceType,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String tableName="tbl_device";
		String sql="select t.id,t.deviceName,t.calculateType"
				+ " from "+tableName+" t "
				+ " where  t.orgid in ("+orgId+")";
		if(StringManagerUtils.isNum(deviceType)){
			sql+= " and t.devicetype="+deviceType;
		}else{
			sql+= " and t.devicetype in ("+deviceType+")";
		}
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and t.deviceName='"+deviceName+"'";
		}
		sql+=" order by t.sortnum,t.deviceName";
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(sql);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("deviceName")+"\",\"dataIndex\":\"deviceName\" ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"deviceName\":\""+obj[1]+"\",");
			result_json.append("\"calculateType\":\""+obj[2]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getReportTemplateList(String orgId,String deviceName,String deviceType,String reportType,String language){
		StringBuffer result_json = new StringBuffer();
		ReportTemplate reportTemplate=MemoryDataManagerTask.getReportTemplateConfig();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String tableName="tbl_device";
		String sql="select t3.singleWellRangeReportTemplate,t3.productionreporttemplate "
				+ " from tbl_device t,tbl_protocolreportinstance t2,tbl_report_unit_conf t3 "
				+ " where t.reportinstancecode=t2.code and t2.unitid=t3.id "
				+ " and t.orgid in("+orgId+")";
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and t.deviceName='"+deviceName+"'";
		}
		sql+=" group by t3.singleWellRangeReportTemplate,t3.productionreporttemplate";
		
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("template")+"\",\"dataIndex\":\"templateName\" ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		int totalCount=0;
		if(reportTemplate!=null){
			List<?> list = this.findCallSql(sql);
			List<Template> templateList=reportTemplate.getSingleWellRangeReportTemplate();
			if(StringManagerUtils.stringToInteger(reportType)==1){
				templateList=reportTemplate.getProductionReportTemplate();
			}
			for(int i=0;i<templateList.size();i++){
				for(int j=0;j<list.size();j++){
					Object[] obj=(Object[]) list.get(j);
					String templateCode=obj[0]+"";
					if(StringManagerUtils.stringToInteger(reportType)==1){
						templateCode=obj[1]+"";
					}
					if(templateCode.equalsIgnoreCase(templateList.get(i).getTemplateCode())){
						totalCount++;
						result_json.append("{\"id\":"+totalCount+",");
						result_json.append("\"templateName\":\""+templateList.get(i).getTemplateName()+"\",");
						result_json.append("\"templateCode\":\""+templateList.get(i).getTemplateCode()+"\"},");
						break;
					}
				}
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("],\"totalCount\":"+totalCount+"}");
		return result_json.toString();
	}
	
	public String getReportInstanceList(String orgId,String deviceName,String deviceType,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String tableName="tbl_device";
		String sql="select  t2.id,t2.name,t2.code,t2.unitid,t2.sort "
				+ " from "+tableName+" t,tbl_protocolreportinstance t2 "
				+ " where t.reportinstancecode=t2.code "
				+ " and t.orgid in("+orgId+")";
		if(StringManagerUtils.isNum(deviceType)){
			sql+= " and t.devicetype="+deviceType;
		}else{
			sql+= " and t.devicetype in ("+deviceType+")";
		}
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and t.deviceName='"+deviceName+"'";
		}
		sql+=" group by t2.id,t2.name,t2.code,t2.unitid,t2.sort order by t2.sort";
		List<?> list = this.findCallSql(sql);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("reportInstance")+"\",\"dataIndex\":\"instanceName\" ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",\"totalCount\":"+list.size()+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"instanceName\":\""+obj[1]+"\",");
			result_json.append("\"instanceCode\":\""+obj[2]+"\",");
			result_json.append("\"unitId\":\""+obj[3]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
}
