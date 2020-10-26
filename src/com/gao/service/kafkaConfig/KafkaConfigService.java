package com.gao.service.kafkaConfig;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gao.model.data.DataDictionary;
import com.gao.service.base.BaseService;
import com.gao.service.base.CommonDataService;
import com.gao.utils.Config;
import com.gao.utils.ConfigFile;
import com.gao.utils.Page;
import com.gao.utils.StringManagerUtils;

import oracle.sql.CLOB;

@Service("kafkaConfigService")
public class KafkaConfigService<T> extends BaseService<T>  {
	@Autowired
	private CommonDataService service;
	
	public String loadDeviceComboxList(Page pager,String deviceName) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		StringBuffer sqlCuswhere = new StringBuffer();
		String sql = " select t.deviceid,t.deviceid as deviceName from tbl_a9rawdata_latest t where 1=1";
		
		if (StringManagerUtils.isNotNull(deviceName)) {
			sql += " and t.deviceid like '%" + deviceName + "%'";
		}
		sql += " order by t.deviceid";
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
	
	public String getA9RowDataList(Page pager,String deviceId,String startDate,String endDate) throws Exception {
		String sql="select t.id,t.deviceId,to_char(t.acqtime@'yyyy-mm-dd hh24:mi:ss') as acqTime,t.signal,t.deviceVer from tbl_a9rawdata_latest t order by t.deviceId";
		String sqlHis="select t.id,t.deviceId,to_char(t.acqtime@'yyyy-mm-dd hh24:mi:ss') as acqTime,t.signal,t.deviceVer from tbl_a9rawdata_hist t where 1=1";
		String finalSql="";
		String sqlAll="";
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"设备ID\",\"dataIndex\":\"deviceId\",width:120 ,children:[] },"
				+ "{ \"header\":\"采集时间\",\"dataIndex\":\"acqTime\",width:130 ,children:[] },"
				+ "{ \"header\":\"信号强度\",\"dataIndex\":\"signal\" ,children:[] },"
				+ "{ \"header\":\"设备版本\",\"dataIndex\":\"deviceVer\",children:[] }"
				+ "]";
		sqlHis+=" and t.acqTime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') + 1 "
				+ "and  t.deviceId = '" + deviceId.trim() + "' order by t.acqTime desc";
		
		if(StringManagerUtils.isNotNull(deviceId.trim())){
			sqlAll=sqlHis;
		}else{
			sqlAll=sql;
		}
		
		int maxvalue=pager.getLimit()+pager.getStart();
		finalSql="select * from   ( select a.*,rownum as rn from ("+sqlAll+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		String getResult = this.findCustomPageBySqlEntity(sqlAll,finalSql, columns, 20 + "", pager);
		return getResult;
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
}
