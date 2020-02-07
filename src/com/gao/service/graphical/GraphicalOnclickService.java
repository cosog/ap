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
import com.gao.utils.StringManagerUtils;

/**
 * <p>图形点击查看Service</p>
 * li 2015-06-18
 * @param <T>
 * @param <T>
 */

@Service("graphicalOnclickService")
public class GraphicalOnclickService<T>  extends BaseService<T>{
	/*
	 * 查询点击的功图数据
	 * */
	public String querySurfaceCardOnclick(int id) throws SQLException, IOException{
		byte[] bytes; 
		String gtsj="";
		String gtdata = "";
		BufferedInputStream bis = null;
        StringBuffer dataSbf = new StringBuffer();
        StringBuffer gtsjStr = new StringBuffer();
        String sql="";
        sql="select jh, to_char(gtcjsj,'yyyy-mm-dd hh24:mi:ss') as cjsj, gtsj, llszh, llxzh, zdzh, zxzh, "
      		+"gtcc, gtcc1, rcyl, gklx from v_fsdiagramhistory "
      		+"where id in (" + id + ")";
		List<?> list=this.GetGtData(sql);
		if(list.size()>0){
			Object[] obj=(Object[])list.get(0);
			if(obj[2]!=null && !obj[2].toString().equals("null")){
				SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[2]);
				CLOB realClob = (CLOB) proxy.getWrappedClob(); 
				gtsj=StringManagerUtils.CLOBtoString(realClob);
				
//				SerializableBlobProxy proxy = (SerializableBlobProxy )Proxy.getInvocationHandler(obj[2]);
//				BLOB blob = (BLOB) proxy.getWrappedBlob(); 
//				bis = new BufferedInputStream(blob.getBinaryStream());  
//			    bytes = new byte[(int)blob.length()];  
//			    int len = bytes.length;  
//			    int offest = 0;  
//			    int read = 0;  
//			    while(offest<len&&(read=bis.read(bytes, offest, len-offest))>0){  
//			        offest+=read;  
//			    } 
//			    gtsj=new String(bytes);
		        String arrgtsj[]=gtsj.replaceAll("\r\n", "\n").split("\n");
		        gtsjStr.append(arrgtsj[2] + ","); // 功图点数
		        for(int j=5;j<arrgtsj.length;j++){
		        	gtsjStr.append(arrgtsj[j] + ",");
		        }
		        gtdata = "";
		        gtdata = gtsjStr.deleteCharAt(gtsjStr.length() - 1) + ""; // 功图点数，位移1，载荷1，位移2，载荷2.。。
			}
			
	        dataSbf.append("{success:true,");
	        dataSbf.append("jh:\""+obj[0]+"\",");
	        dataSbf.append("cjsj:\""+obj[1]+"\",");
	        dataSbf.append("llszh:\""+obj[3]+"\",");
	        dataSbf.append("llxzh:\""+obj[4]+"\",");
	        dataSbf.append("zdzh:\""+obj[5]+"\",");
	        dataSbf.append("zxzh:\""+obj[6]+"\",");
	        dataSbf.append("cch:\""+obj[7]+"\",");
	        dataSbf.append("cci:\""+obj[8]+"\",");
	        dataSbf.append("rcyl:\""+obj[9]+"\",");
	        dataSbf.append("gklx:\""+obj[10]+"\",");
	        dataSbf.append("gtsj:\""+gtdata+"\" ");
	        dataSbf.append("}");
		}else{
			dataSbf.append("{success:true,");
	        dataSbf.append("jh:\"\",");
	        dataSbf.append("cjsj:\"\",");
	        dataSbf.append("llszh:\"\",");
	        dataSbf.append("llxzh:\"\",");
	        dataSbf.append("zdzh:\"\",");
	        dataSbf.append("zxzh:\"\",");
	        dataSbf.append("cch:\"\",");
	        dataSbf.append("cci:\"\",");
	        dataSbf.append("rcyl:\"\",");
	        dataSbf.append("gklx:\"\",");
	        dataSbf.append("gtsj:\"\" ");
	        dataSbf.append("}");
		}
		return dataSbf.toString();
	}
	
	/*
	 * 查询点击的泵功图数据
	 * */
	public String queryPumpCardOnclick(int id) throws SQLException, IOException{
		byte[] bytes; 
		BufferedInputStream bis = null;
        String bgt="";
        StringBuffer dataSbf = new StringBuffer();
        StringBuffer bgtStr = new StringBuffer();
        String sql="select t007.jh as jh, to_char(t033.gtcjsj,'yyyy-mm-dd hh24:mi:ss') as cjsj, t033.bgt as bgt "+
			       "from t_outputwellhistory t033,t_wellinformation t007 "+
			       "where t033.jlbh in (" + id + ") and t033.jbh=t007.jlbh";
		List<?> list=this.GetGtData(sql);
		if(list.size()>0){
			Object[] obj=(Object[])list.get(0);
			SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[2]);
			CLOB realClob = (CLOB) proxy.getWrappedClob(); 
			bgt=StringManagerUtils.CLOBtoString(realClob);
//			SerializableBlobProxy proxy = (SerializableBlobProxy )Proxy.getInvocationHandler(obj[2]);
//			BLOB blob = (BLOB) proxy.getWrappedBlob(); 
//			bis = new BufferedInputStream(blob.getBinaryStream());  
//		    bytes = new byte[(int)blob.length()];  
//		    int len = bytes.length;  
//		    int offest = 0;  
//		    int read = 0;  
//		    while(offest<len&&(read=bis.read(bytes, offest, len-offest))>0){  
//		        offest+=read;  
//		    } 
//		    bgt=new String(bytes);
	        String arrbgt[]=bgt.split(";");  // 以;为界放入数组
	        for(int i=2;i<(arrbgt.length);i++){
	        	String arrbgtdata[]=arrbgt[i].split(",");  // 以,为界放入数组
	        	for(int j=0;j<arrbgtdata.length;j+=2){
		        	bgtStr.append(arrbgtdata[j]+ ","); // 目前位移、载荷位置相反且是100的关系
		        	if((j+1)<arrbgtdata.length){
		        		bgtStr.append(arrbgtdata[j+1] + ",");
		        	}else{
		        		bgtStr.append(",");
		        	}
		        	
		        }
	        	bgtStr.deleteCharAt(bgtStr.length() - 1);
	        	bgtStr.append("#");
	        }
	        if(bgtStr.length()>0){
	        	bgtStr=bgtStr.deleteCharAt(bgtStr.length() - 1);
	        }
	        String bgtdata = bgtStr.toString();
	        dataSbf.append("{success:true,");
	        dataSbf.append("jh:\""+obj[0]+"\",");           // 井名
	        dataSbf.append("cjsj:\""+obj[1]+"\",");         // 时间
	        dataSbf.append("bgtsj:\""+bgtdata+"\"");        // 曲线数据
	        dataSbf.append("}");
		}else{
			dataSbf.append("{success:true,");
			dataSbf.append("jh:\"\",");
	        dataSbf.append("cjsj:\"\",");
	        dataSbf.append("bgtsj:\"\"");
	        dataSbf.append("}");
		}
		return dataSbf.toString();
	}
}