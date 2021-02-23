package com.gao.service.graphical;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.List;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

import org.hibernate.engine.jdbc.SerializableBlobProxy;
import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.stereotype.Service;

import com.gao.service.base.BaseService;
import com.gao.utils.Config;
import com.gao.utils.ConfigFile;
import com.gao.utils.Page;
import com.gao.utils.PageHandler;
import com.gao.utils.StringManagerUtils;

/**
 * 地面功图查询Service
 * li 2015-04-15
 */

@Service("surfaceCardService")
public class SurfaceCardService <T> extends BaseService<T>{
	
	/*
	 * 查询功图数据
	 * */
	public String querySurfaceCard(Page pager,String orgId,String wellName,String startDate,String endDate) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		int intPage = pager.getPage();
		int limit = pager.getLimit();
		int start = pager.getStart();
		int maxvalue = limit + start;
		String allsql="",sql="";
		String tableName="viw_rpc_diagramquery_latest";
		if(StringManagerUtils.isNotNull(wellName)){
			tableName="viw_rpc_diagramquery_hist";
		}
		String prodCol=" liquidWeightProduction";
		if(configFile.getOthers().getProductionUnit()!=0){
			prodCol=" liquidVolumetricProduction";
		}
		
		allsql="select id, wellName, to_char(acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime, "
				+ " position_curve,load_curve,"
				+ " upperLoadline, lowerloadline, fmax, fmin, stroke, SPM, "+prodCol+", workingConditionName "
				+ " from  "+tableName+""
				+ " where orgid in(" + orgId + ")";
		if(StringManagerUtils.isNotNull(wellName)){
			allsql+= " and wellName='" + wellName + "' ";
			allsql+= " and acqTime between to_date('"+ startDate +"','yyyy-MM-dd') and to_date('"+ endDate +"','yyyy-MM-dd')+1";
			allsql+=" order by acqTime desc";
		}else{
			allsql+=" order by sortnum,wellName";
		}
		
		sql="select b.* from (select a.*,rownum as rn from  ("+ allsql +") a where rownum <= "+ maxvalue +") b where rn > "+ start +"";
		int totals = getTotalCountRows(allsql);//获取总记录数
		List<?> list=this.findCallSql(sql);
		PageHandler handler = new PageHandler(intPage, totals, limit);
		int totalPages = handler.getPageCount(); // 总页数
		dynSbf.append("{\"success\":true,\"totals\":" + totals + ",\"totalPages\":\"" + totalPages + "\",\"start_date\":\""+startDate+"\",\"end_date\":\""+endDate+"\",\"list\":[");
		
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			CLOB realClob=null;
			SerializableClobProxy   proxy=null;
			String DiagramXData="";
	        String DiagramYData="";
	        String pointCount="";
	        if(obj[3]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[3]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				DiagramXData=StringManagerUtils.CLOBtoString(realClob);
				if(StringManagerUtils.isNotNull(DiagramXData)){
					pointCount=DiagramXData.split(",").length+"";
				}
			}
	        if(obj[4]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[4]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				DiagramYData=StringManagerUtils.CLOBtoString(realClob);
			}
	        
			dynSbf.append("{ \"id\":\"" + obj[0] + "\",");
			dynSbf.append("\"wellName\":\"" + obj[1] + "\",");
			dynSbf.append("\"acqTime\":\"" + obj[2] + "\",");
			dynSbf.append("\"pointCount\":\""+pointCount+"\","); 
			dynSbf.append("\"upperLoadLine\":\"" + obj[5] + "\",");
			dynSbf.append("\"lowerLoadLine\":\"" + obj[6] + "\",");
			dynSbf.append("\"fmax\":\""+obj[7]+"\",");
			dynSbf.append("\"fmin\":\""+obj[8]+"\",");
			dynSbf.append("\"stroke\":\""+obj[9]+"\",");
			dynSbf.append("\"spm\":\""+obj[10]+"\",");
			dynSbf.append("\"liquidProduction\":\""+obj[11]+"\",");
			dynSbf.append("\"workingConditionName\":\""+obj[12]+"\",");
			dynSbf.append("\"positionCurveData\":\""+DiagramXData+"\",");         // 工况代码
			dynSbf.append("\"loadCurveData\":\""+DiagramYData+"\"},");         // 工况代码
		}
		if(dynSbf.toString().endsWith(",")){
			dynSbf.deleteCharAt(dynSbf.length() - 1);
		}
		dynSbf.append("]}");
//		System.out.println(dynSbf.toString().replaceAll("null", ""));
		return dynSbf.toString().replaceAll("null", "");
	}
	
	public String getWellList(Page pager,String orgId,String wellName,String startDate,String endDate){
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		int intPage = pager.getPage();
		int limit = pager.getLimit();
		int start = pager.getStart();
		int maxvalue = limit + start;
		String allsql="",sql="";
		String tableName="viw_rpc_diagramquery_latest";
		String prodTitle="产量(t/d)";
		if(StringManagerUtils.isNotNull(wellName)){
			tableName="viw_rpc_diagramquery_hist";
		}
		String prodCol=" liquidWeightProduction";
		if(configFile.getOthers().getProductionUnit()!=0){
			prodCol=" liquidVolumetricProduction";
			prodTitle="产量(m3/d)";
		}
		
		allsql="select id, wellName, to_char(acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime, "
				+ " workingConditionName,"+prodCol+",stroke, SPM,fmax, fmin   "
				+ " from  "+tableName+""
				+ " where orgid in(" + orgId + ")";
		if(StringManagerUtils.isNotNull(wellName)){
			allsql+= " and wellName='" + wellName + "' ";
			allsql+= " and acqTime between to_date('"+ startDate +"','yyyy-MM-dd') and to_date('"+ endDate +"','yyyy-MM-dd')+1";
			allsql+=" order by acqTime desc";
		}else{
			allsql+=" order by sortnum,wellName";
		}
		
		sql="select b.* from (select a.*,rownum as rn from  ("+ allsql +") a where rownum <= "+ maxvalue +") b where rn > "+ start +"";
		int totals = getTotalCountRows(allsql);//获取总记录数
		List<?> list=this.findCallSql(sql);
		
		
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"井名\",\"dataIndex\":\"wellName\" ,children:[] },"
				+ "{ \"header\":\"采集时间\",\"dataIndex\":\"acqTime\",width:150,children:[] },"
				+ "{ \"header\":\"工况\",\"dataIndex\":\"workingConditionName\" ,children:[] },"
				+ "{ \"header\":\""+prodTitle+"\",\"dataIndex\":\"liquidProduction\" ,children:[] },"
				+ "{ \"header\":\"冲程(m)\",\"dataIndex\":\"stroke\" ,children:[] },"
				+ "{ \"header\":\"冲次(1/min)\",\"dataIndex\":\"SPM\" ,children:[] },"
				+ "{ \"header\":\"最大载荷(kN)\",\"dataIndex\":\"fmax\" ,children:[] },"
				+ "{ \"header\":\"最小载荷(kN)\",\"dataIndex\":\"fmin\" ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"wellName\":\""+obj[1]+"\",");
			result_json.append("\"acqTime\":\""+obj[2]+"\",");
			result_json.append("\"workingConditionName\":\""+obj[3]+"\",");
			result_json.append("\"liquidProduction\":\""+obj[4]+"\",");
			result_json.append("\"stroke\":\""+obj[5]+"\",");
			result_json.append("\"SPM\":\""+obj[6]+"\",");
			result_json.append("\"fmax\":\""+obj[7]+"\",");
			result_json.append("\"fmin\":\""+obj[8]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
}