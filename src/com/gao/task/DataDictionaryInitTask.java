package com.gao.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import com.gao.model.calculate.AppRunStatusProbeResonanceData;
import com.gao.model.calculate.CPUProbeResponseData;
import com.gao.model.calculate.CommResponseData;
import com.gao.model.calculate.MemoryProbeResponseData;
import com.gao.model.drive.KafkaConfig;
import com.gao.utils.Config;
import com.gao.utils.Config2;
import com.gao.utils.OracleJdbcUtis;
import com.gao.utils.StringManagerUtils;
import com.gao.websocket.config.WebSocketByJavax;
import com.gao.websocket.handler.SpringWebSocketHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("DataDictionaryInitTask")  
public class DataDictionaryInitTask {
    
	@SuppressWarnings({ "static-access", "unused" })
	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void initProductionDataDictionary(){
		String url=Config.getInstance().configFile.getServer().getAccessPath()+"/dataitemsInfoController/initProductionDataDictionary";
		String result=StringManagerUtils.sendPostMethod(url, "","utf-8");
	}
}
