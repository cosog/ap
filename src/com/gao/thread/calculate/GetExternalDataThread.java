package com.gao.thread.calculate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.gao.model.calculate.PCPCalculateRequestData;
import com.gao.model.calculate.PCPCalculateResponseData;
import com.gao.model.calculate.RPCCalculateRequestData;
import com.gao.model.calculate.RPCCalculateResponseData;
import com.gao.model.calculate.CommResponseData;
import com.gao.model.calculate.ElectricCalculateResponseData;
import com.gao.model.calculate.TimeEffResponseData;
import com.gao.service.base.CommonDataService;
import com.gao.service.datainterface.CalculateDataService;
import com.gao.service.graphicalupload.GraphicalUploadService;
import com.gao.utils.Config;
import com.gao.utils.Config2;
import com.gao.utils.DataSourceConfig;
import com.gao.utils.OracleJdbcUtis;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import oracle.jdbc.OracleConnection;
import oracle.sql.CLOB;

public class GetExternalDataThread extends Thread{

	private String wellName;
	private String acqTime;
	
	private Connection conn = null;   
    private PreparedStatement pstmt = null;  
    private ResultSet rs = null;  
    
    private boolean exit=false;

	public GetExternalDataThread( String wellName, String acqTime) {
		super();
		this.wellName = wellName;
		this.acqTime = acqTime;
	}

	public void run(){
		while(!exit){
			try {
				DataSourceConfig dataSourceConfig=DataSourceConfig.getInstance();
				conn= OracleJdbcUtis.getOuterConnection();
				if(conn!=null){
					int record=100;
					String outerSql=""
							+ " select t."+dataSourceConfig.getDiagramTable().getColumns().getWellName().getColumn()+",to_char(t."+dataSourceConfig.getDiagramTable().getColumns().getAcqTime().getColumn()+",'yyyy-mm-dd hh24:mi:ss'),"
							+ " t."+dataSourceConfig.getDiagramTable().getColumns().getStroke().getColumn()+","
							+ " t."+dataSourceConfig.getDiagramTable().getColumns().getSPM().getColumn()+","
							+ " t."+dataSourceConfig.getDiagramTable().getColumns().getPointCount().getColumn()+","
							+ " t."+dataSourceConfig.getDiagramTable().getColumns().getS().getColumn()+","
							+ " t."+dataSourceConfig.getDiagramTable().getColumns().getF().getColumn()+","
							+ " t."+dataSourceConfig.getDiagramTable().getColumns().getI().getColumn()+","
							+ " t."+dataSourceConfig.getDiagramTable().getColumns().getKWatt().getColumn()
							+ " from "+dataSourceConfig.getDiagramTable().getName()+" t  "
							+ " where 1=1 ";
					if(!StringManagerUtils.isNotNull(acqTime)){
						record=1;
						outerSql+=" and t.dyna_create_time > to_date('"+StringManagerUtils.getCurrentTime()+"','yyyy-mm-dd')-30 ";
					}else{
						outerSql+= " and t.dyna_create_time > to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss') ";
					}
//					outerSql+= " and t.dyna_create_time < to_date('2020-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
					outerSql+=" and t.well_common_name='"+wellName+"' "
							+ " order by t.dyna_create_time ";
					outerSql="select v.* from ( "+outerSql+ " ) v where rownum<="+record+"";
//					System.out.println("outerSql-"+wellName+":"+outerSql);
					pstmt = conn.prepareStatement(outerSql);
					rs=pstmt.executeQuery();
					while(rs.next()){
						try{
							wellName=rs.getString(1)==null?"":rs.getString(1);
							String acqTimeStr=rs.getString(2)==null?"":rs.getString(2);
							float stroke;
							float frequency;
							if(rs.getObject(3)==null){
								stroke=0;
							}else{
								stroke=rs.getFloat(3);
							}
							if(rs.getObject(4)==null){
								frequency=0;
							}else{
								frequency=rs.getFloat(4);
							}
							
							int point;
							String SStr=rs.getString(6)==null?"":rs.getString(6).replaceAll(";", ",");
							String FStr=rs.getString(7)==null?"":rs.getString(7).replaceAll(";", ",");
							String AStr=rs.getString(8)==null?"":rs.getString(8).replaceAll(";", ",");
							String PStr=rs.getString(9)==null?"":rs.getString(9).replaceAll(";", ",");
							point=SStr.split(",").length;
							System.out.println(acqTimeStr);
							
							CallableStatement cs=null;
							CLOB sClob=new CLOB((OracleConnection) conn);
							sClob = oracle.sql.CLOB.createTemporary(conn,false,1);
							sClob.putString(1, SStr);
							
							CLOB fClob=new CLOB((OracleConnection) conn);
							fClob = oracle.sql.CLOB.createTemporary(conn,false,1);
							fClob.putString(1, FStr);
							
							CLOB wattClob=new CLOB((OracleConnection) conn);
							wattClob = oracle.sql.CLOB.createTemporary(conn,false,1);
							wattClob.putString(1, PStr);
							
							CLOB iClob=new CLOB((OracleConnection) conn);
							iClob = oracle.sql.CLOB.createTemporary(conn,false,1);
							iClob.putString(1, AStr);
							
							try {
								cs = conn.prepareCall("{call prd_save_rpc_uploaddiagram(?,?,?,?,?,?,?,?)}");
								cs.setString(1, wellName);
								cs.setString(2, acqTime);
								cs.setFloat(3, stroke);
								cs.setFloat(4, frequency);
								cs.setClob(5, sClob);
								cs.setClob(6, fClob);
								cs.setClob(7, wattClob);
								cs.setClob(8, iClob);
								
								cs.executeUpdate();
							} catch (SQLException e) {
								e.printStackTrace();
							}finally{
								if(cs!=null)
									cs.close();
								conn.close();
							}
						}catch(Exception ee){
							ee.printStackTrace();
							continue;
						}
					}
				}
				
				OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
				Thread.sleep(60*1*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public String getWellName() {
		return wellName;
	}

	public void setWellName(String wellName) {
		this.wellName = wellName;
	}

	public String getAcqTime() {
		return acqTime;
	}

	public void setAcqTime(String acqTime) {
		this.acqTime = acqTime;
	}

	
}
