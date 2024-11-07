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
