package com.cosog.thread.calculate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.springframework.context.annotation.Bean;

import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.StringManagerUtils;
import com.cosog.websocket.config.WebSocketByJavax;

public class DatabaseTableSynColumnThread extends Thread{

	private List<String> deleteMappingList;

	

	public DatabaseTableSynColumnThread(List<String> deleteMappingList) {
		super();
		this.deleteMappingList = deleteMappingList;
	}

	public void run(){
		if(deleteMappingList!=null && deleteMappingList.size()>0){
			List<String> tableColumnList=MemoryDataManagerTask.getAcqTableColumn();
			Connection conn = null;   
			PreparedStatement pstmt = null;   
			ResultSet rs = null;
			conn=OracleJdbcUtis.getConnection();
			if(conn!=null){
				try {
					int updateColCount=0;
					for(int i=0;i<deleteMappingList.size();i++){
						
						if(StringManagerUtils.existOrNot(tableColumnList, deleteMappingList.get(i), false)){
							String updateSql="update tbl_acqdata_latest t set t."+deleteMappingList.get(i)+"=null where t."+deleteMappingList.get(i)+" is not null";
							pstmt = conn.prepareStatement(updateSql);
							int result=pstmt.executeUpdate();
							
							updateSql="update tbl_acqdata_hist t set t."+deleteMappingList.get(i)+"=null where t."+deleteMappingList.get(i)+" is not null";
							pstmt = conn.prepareStatement(updateSql);
							result=pstmt.executeUpdate();
							
							updateSql="update tbl_timingcalculationdata t set t."+deleteMappingList.get(i)+"=null where t."+deleteMappingList.get(i)+" is not null";
							pstmt = conn.prepareStatement(updateSql);
							result=pstmt.executeUpdate();
							
							updateSql="update tbl_dailycalculationdata t set t."+deleteMappingList.get(i)+"=null where t."+deleteMappingList.get(i)+" is not null";
							pstmt = conn.prepareStatement(updateSql);
							result=pstmt.executeUpdate();
						}
						updateColCount++;
						try {
							String sendMessage="清理数据库内容,进度"+updateColCount+"/"+deleteMappingList.size()+",当前处理字段:"+deleteMappingList.get(i)+",";
							String sendData="{"
									+ "\"functionCode\":\"DatabaseDataCleaning\","
									+ "\"message\":\""+sendMessage+"\","
									+ "\"updateColCount\":"+updateColCount+","
									+ "\"count\":"+deleteMappingList.size()+""
									+ "}";
							infoHandler().sendMessageToBy("ApWebSocketClient", sendData);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
				}
	        }
		}
	}

	@Bean
    public static WebSocketByJavax infoHandler() {
        return new WebSocketByJavax();
    }

	public List<String> getDeleteMappingList() {
		return deleteMappingList;
	}



	public void setDeleteMappingList(List<String> deleteMappingList) {
		this.deleteMappingList = deleteMappingList;
	}
	


	
}
