package com.cosog.service.kafkaConfig;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.data.DataDictionary;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.utils.Config;
import com.cosog.utils.ConfigFile;
import com.cosog.utils.Page;
import com.cosog.utils.StringManagerUtils;

import oracle.sql.CLOB;

@Service("kafkaConfigService")
public class KafkaConfigService<T> extends BaseService<T>  {
	@Autowired
	private CommonDataService service;
	
	public String loadDeviceComboxList(Page pager,String deviceName) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		StringBuffer sqlCuswhere = new StringBuffer();
		String sql = " select t.deviceId,t.deviceId as deviceName from tbl_a9rawdata_latest t where 1=1";
		
		if (StringManagerUtils.isNotNull(deviceName)) {
			sql += " and t.deviceId like '%" + deviceName + "%'";
		}
		sql += " order by t.deviceId";
		sqlCuswhere.append("select * from   ( select a.*,rownum as rn from (");
		sqlCuswhere.append(""+sql);
		int maxvalue=pager.getLimit()+pager.getStart();
		sqlCuswhere.append(" ) a where  rownum <="+maxvalue+") b");
		sqlCuswhere.append(" where rn >"+pager.getStart());
		String finalsql=sqlCuswhere.toString();
		try {
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(finalsql);
			result_json.append("{\"totals\":"+totals+",\"list\":[{boxkey:\"\",boxval:\"选择全部\"},");
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
			result_json.append("]}");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_json.toString();
	}
	
	public String getKafkaConfigWellList(String orgId,String wellName){
		StringBuffer result_json = new StringBuffer();
		String sql="select t.id,t.signinid,t.wellname,t.protocolCode,"
				+ " dis.commStatus,dis.commStatusName,dis.commAlarmLevel "
				+ " from tbl_wellinformation t "
				+ " left outer join viw_rpc_comprehensive_latest dis on dis.wellid=t.id"
				+ " where t.orgid in ("+orgId+")";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and t.wellName='"+wellName+"'";
		}
		sql+=" order by t.sortnum,t.wellname";
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(sql);
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"设备ID\",\"dataIndex\":\"deviceId\",width:120 ,children:[] },"
				+ "{ \"header\":\"通信状态\",\"dataIndex\":\"commStatusName\",width:80 ,children:[] },"
				+ "{ \"header\":\"井名\",\"dataIndex\":\"wellName\" ,width:120 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"deviceId\":\""+obj[1]+"\",");
			result_json.append("\"wellName\":\""+obj[2]+"\",");
			result_json.append("\"protocolCode\":\""+obj[3]+"\",");
			result_json.append("\"commStatus\":"+obj[4]+",");
			result_json.append("\"commStatusName\":\""+obj[5]+"\",");
			result_json.append("\"commAlarmLevel\":"+obj[6]+"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	@SuppressWarnings("unused")
	public String getA9RowDataList(Page pager,String deviceId,String startDate,String endDate) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql="select t.id,t.deviceId,well.wellName,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,t.signal,t.deviceVer,"
				+ " dis.commStatus,dis.commStatusName,dis.commAlarmLevel "
				+ " from tbl_a9rawdata_latest t "
				+ " left outer join tbl_wellinformation well on well.signinid=t.deviceId"
				+ " left outer join viw_rpc_comprehensive_latest dis on dis.wellid=well.id"
				+ " order by well.sortnum,well.wellName,t.deviceId";
		String sqlHis="select t.id,t.deviceId,well.wellName,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,t.signal,t.deviceVer,"
				+ " 1 as commStatus,'在线' as commStatusName,0 as commAlarmLevel"
				+ " from tbl_a9rawdata_hist t "
				+ " left outer join tbl_wellinformation well on well.signinid=t.deviceId"
				+ " where 1=1";
		String firstHisRecordCommStatus="select dis.commStatus,dis.commStatusName,dis.commAlarmLevel "
				+ " from tbl_wellinformation t,viw_rpc_comprehensive_latest dis"
				+ " where t.id=dis.wellId and t.signinid='"+deviceId+"'";
		String finalSql="";
		String sqlAll="";
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"设备ID\",\"dataIndex\":\"deviceId\",width:120 ,children:[] },"
				+ "{ \"header\":\"通信状态\",\"dataIndex\":\"commStatusName\",width:80 ,children:[] },"
				+ "{ \"header\":\"井名\",\"dataIndex\":\"wellName\",width:120 ,children:[] },"
				+ "{ \"header\":\"采集时间\",\"dataIndex\":\"acqTime\",width:130 ,children:[] },"
				+ "{ \"header\":\"信号强度\",\"dataIndex\":\"signal\" ,children:[] },"
				+ "{ \"header\":\"设备版本\",\"dataIndex\":\"deviceVer\",children:[] }"
				+ "]";
		sqlHis+=" and t.acqTime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') + 1 "
				+ "and  t.deviceId = '" + deviceId.trim() + "' order by t.acqTime desc";
		List<?> firstHisRecordCommStatusList=null;
		if(StringManagerUtils.isNotNull(deviceId.trim())){
			sqlAll=sqlHis;
			firstHisRecordCommStatusList = this.findCallSql(firstHisRecordCommStatus);
		}else{
			sqlAll=sql;
		}
		
		int totals=this.getTotalCountRows(sqlAll);
		int maxvalue=pager.getLimit()+pager.getStart();
		finalSql="select * from   ( select a.*,rownum as rn from ("+sqlAll+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		List<?> list = this.findCallSql(finalSql);
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"start_date\":\""+pager.getStart_date()+"\",");
		result_json.append("\"end_date\":\""+pager.getEnd_date()+"\",");
		result_json.append("\"totalRoot\":[");
		
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"deviceId\":\""+obj[1]+"\",");
			result_json.append("\"wellName\":\""+obj[2]+"\",");
			result_json.append("\"acqTime\":\""+obj[3]+"\",");
			result_json.append("\"signal\":\""+obj[4]+"\",");
			result_json.append("\"deviceVer\":\""+obj[5]+"\",");
			
			if(i==0&&pager.getStart()==0 &&firstHisRecordCommStatusList!=null&&firstHisRecordCommStatusList.size()>0){
				Object[] objComm=(Object[]) firstHisRecordCommStatusList.get(0);
				result_json.append("\"commStatus\":"+objComm[0]+",");
				result_json.append("\"commStatusName\":\""+objComm[1]+"\",");
				result_json.append("\"commAlarmLevel\":"+objComm[2]+"},");
			}else{
				if(StringManagerUtils.isNotNull(deviceId.trim())){
					result_json.append("\"commStatus\":0,");
					result_json.append("\"commStatusName\":\"\",");
					result_json.append("\"commAlarmLevel\":0},");
				}else{
					result_json.append("\"commStatus\":"+obj[6]+",");
					result_json.append("\"commStatusName\":\""+obj[7]+"\",");
					result_json.append("\"commAlarmLevel\":"+obj[8]+"},");
				}
				
				
				
			}	
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
//		String getResult = this.findCustomPageBySqlEntity(sqlAll,finalSql, columns, 20 + "", pager);
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getA9RawCurveChartsData(String id,String deviceId,String selectedDeviceId) throws SQLException, IOException{
        StringBuffer dataSbf = new StringBuffer();
        String tableName="tbl_a9rawdata_latest";
        if(StringManagerUtils.isNotNull(selectedDeviceId)){
        	tableName="tbl_a9rawdata_hist";
        }else{
        	tableName="tbl_a9rawdata_latest";
        }
        String sql="select to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,t.interval,t.a,t.f,t.watt,t.i from "+tableName+" t where 1=1 ";
        if(StringManagerUtils.isNotNull(selectedDeviceId)){
        	sql+=" and t.id="+id;
        }else{
        	sql+=" and t.deviceId='"+deviceId+"'";
        }
        
        String acqTime="";
        String intervalCurveData="";
		String aCurveData="";
		String fCurveData="";
		String wattCurveData="";
		String iCurveData="";
		List<?> list=this.findCallSql(sql);
		if(list.size()>0){
			Object[] obj=(Object[])list.get(0);
			SerializableClobProxy   proxy=null;
			CLOB realClob=null;
			acqTime=obj[0]+"";
			if(obj[1]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[1]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				intervalCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[2]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[2]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				aCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[3]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[3]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				fCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[4]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[4]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				wattCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[5]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[5]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				iCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
		}
		dataSbf.append("{success:true,");
        dataSbf.append("deviceId:\""+deviceId+"\",");
        dataSbf.append("acqTime:\""+acqTime+"\",");
        dataSbf.append("intervalCurveData:\""+intervalCurveData+"\",");
        dataSbf.append("aCurveData:\""+aCurveData+"\",");
        dataSbf.append("fCurveData:\""+fCurveData+"\",");
        dataSbf.append("wattCurveData:\""+wattCurveData+"\",");
        dataSbf.append("iCurveData:\""+iCurveData+"\"");
        dataSbf.append("}");
		return dataSbf.toString().replaceAll("null", "");
	}
	
	public String getKafkaConfigOperationList(){
		StringBuffer result_json = new StringBuffer();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"操作\",\"dataIndex\":\"operation\" ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		result_json.append("{\"id\":1,\"operation\":\"驱动配置\"},");
		result_json.append("{\"id\":2,\"operation\":\"启抽\"},");
		result_json.append("{\"id\":3,\"operation\":\"停抽\"},");
		result_json.append("{\"id\":4,\"operation\":\"RTU软重启\"},");
		result_json.append("{\"id\":5,\"operation\":\"频率下行\"},");
		result_json.append("{\"id\":6,\"operation\":\"时钟下行\"},");
		result_json.append("{\"id\":7,\"operation\":\"模型下行\"}");
		
		result_json.append("]}");
		return result_json.toString();
	}
}
