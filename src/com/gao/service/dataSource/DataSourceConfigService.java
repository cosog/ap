package com.gao.service.dataSource;

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
import com.gao.utils.DataSourceConfig;
import com.gao.utils.Page;
import com.gao.utils.StringManagerUtils;

import oracle.sql.CLOB;

@Service("dataSourceConfigService")
public class DataSourceConfigService<T> extends BaseService<T>  {
	@Autowired
	private CommonDataService service;
	
	public String getKafkaConfigWellList(){
		StringBuffer result_json = new StringBuffer();
		DataSourceConfig dataSourceConfig=DataSourceConfig.getInstance();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"项\",\"dataIndex\":\"item\",width:120 ,children:[] },"
				+ "{ \"header\":\"值状态\",\"dataIndex\":\"value\",width:80 ,children:[] }"
				+ "]";
		
		String diagramTableColumns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"字段名称\",\"dataIndex\":\"item\",width:120 ,children:[] },"
				+ "{ \"header\":\"字段代码\",\"dataIndex\":\"columnName\",width:80 ,children:[] },"
				+ "{ \"header\":\"字段类型\",\"dataIndex\":\"columnType\",width:80 ,children:[] }"
				+ "]";
		
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",\"diagramTableColumns\":"+diagramTableColumns+",");
		result_json.append("\"totalRoot\":[");
		if(dataSourceConfig!=null){
			result_json.append("{\"id\":1,\"item\":\"IP\",\"value\":\""+dataSourceConfig.getIP()+"\"},");
			result_json.append("{\"id\":2,\"item\":\"Port\",\"value\":\""+dataSourceConfig.getPort()+"\"},");
			result_json.append("{\"id\":3,\"item\":\"数据库类型\",\"value\":\""+(dataSourceConfig.getType()==0?"Oracle":"Sql Server")+"\"},");
			result_json.append("{\"id\":4,\"item\":\"数据库名称\",\"value\":\""+dataSourceConfig.getInstanceName()+"\"},");
			result_json.append("{\"id\":5,\"item\":\"用户名\",\"value\":\""+dataSourceConfig.getUser()+"\"},");
			result_json.append("{\"id\":6,\"item\":\"密码\",\"value\":\""+dataSourceConfig.getPassword()+"\"}");
			
//			result_json.append("{\"id\":7,\"item\":\"功图数据表\",\"value\":\""+dataSourceConfig.getDiagramTable().getName()+"\"},");
//			result_json.append("{\"id\":8,\"item\":\"油层数据表\",\"value\":\""+dataSourceConfig.getReservoirTable().getName()+"\"},");
//			result_json.append("{\"id\":9,\"item\":\"杆柱组合数据表\",\"value\":\""+dataSourceConfig.getRodStringTable().getName()+"\"},");
//			result_json.append("{\"id\":10,\"item\":\"油管数据表\",\"value\":\""+dataSourceConfig.getTubingStringTable().getName()+"\"},");
//			result_json.append("{\"id\":11,\"item\":\"套管数据表\",\"value\":\""+dataSourceConfig.getCasingStringTable().getName()+"\"},");
//			result_json.append("{\"id\":12,\"item\":\"动态数据表\",\"value\":\""+dataSourceConfig.getProductionTable().getName()+"\"},");
//			result_json.append("{\"id\":13,\"item\":\"泵数据表\",\"value\":\""+dataSourceConfig.getPumpTable().getName()+"\"}");
		}
		result_json.append("],");
		result_json.append("\"columnRoot\":[");
		if(dataSourceConfig!=null){
			result_json.append("{\"id\":1,\"item\":\"井名\",\"columnName\":\""+dataSourceConfig.getDiagramTable().getColumns().getWellName().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getDiagramTable().getColumns().getWellName().getType()+"\"},");
			result_json.append("{\"id\":2,\"item\":\"采集时间\",\"columnName\":\""+dataSourceConfig.getDiagramTable().getColumns().getAcqTime().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getDiagramTable().getColumns().getAcqTime().getType()+"\"},");
			result_json.append("{\"id\":3,\"item\":\"冲程\",\"columnName\":\""+dataSourceConfig.getDiagramTable().getColumns().getStroke().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getDiagramTable().getColumns().getStroke().getType()+"\"},");
			result_json.append("{\"id\":4,\"item\":\"冲次\",\"columnName\":\""+dataSourceConfig.getDiagramTable().getColumns().getSPM().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getDiagramTable().getColumns().getSPM().getType()+"\"},");
			result_json.append("{\"id\":5,\"item\":\"功图点数\",\"columnName\":\""+dataSourceConfig.getDiagramTable().getColumns().getPointCount().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getDiagramTable().getColumns().getPointCount().getType()+"\"},");
			result_json.append("{\"id\":6,\"item\":\"位移\",\"columnName\":\""+dataSourceConfig.getDiagramTable().getColumns().getS().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getDiagramTable().getColumns().getS().getType()+"\"},");
			result_json.append("{\"id\":7,\"item\":\"载荷\",\"columnName\":\""+dataSourceConfig.getDiagramTable().getColumns().getF().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getDiagramTable().getColumns().getF().getType()+"\"},");
			result_json.append("{\"id\":8,\"item\":\"电流\",\"columnName\":\""+dataSourceConfig.getDiagramTable().getColumns().getI().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getDiagramTable().getColumns().getI().getType()+"\"},");
			result_json.append("{\"id\":9,\"item\":\"功率\",\"columnName\":\""+dataSourceConfig.getDiagramTable().getColumns().getKWatt().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getDiagramTable().getColumns().getKWatt().getType()+"\"},");
			//油层数据表
			result_json.append("{\"id\":1,\"item\":\"井名\",\"columnName\":\""+dataSourceConfig.getReservoirTable().getColumns().getWellName().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getReservoirTable().getColumns().getWellName().getType()+"\"},");
			result_json.append("{\"id\":2,\"item\":\"油层中部深度\",\"columnName\":\""+dataSourceConfig.getReservoirTable().getColumns().getDepth().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getReservoirTable().getColumns().getDepth().getType()+"\"},");
			result_json.append("{\"id\":3,\"item\":\"油层中部温度\",\"columnName\":\""+dataSourceConfig.getReservoirTable().getColumns().getTemperature().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getReservoirTable().getColumns().getTemperature().getType()+"\"},");
			//杆柱组合
			result_json.append("{\"id\":1,\"item\":\"井名\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getWellName().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getWellName().getType()+"\"},");
			result_json.append("{\"id\":2,\"item\":\"一级杆级别\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getGrade1().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getGrade1().getType()+"\"},");
			result_json.append("{\"id\":3,\"item\":\"一级杆外径\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getOutsideDiameter1().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getOutsideDiameter1().getType()+"\"},");
			result_json.append("{\"id\":4,\"item\":\"一级杆内径\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getInsideDiameter1().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getInsideDiameter1().getType()+"\"},");
			result_json.append("{\"id\":5,\"item\":\"一级杆长度\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getLength1().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getLength1().getType()+"\"},");
			
			result_json.append("{\"id\":6,\"item\":\"二级杆级别\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getGrade2().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getGrade2().getType()+"\"},");
			result_json.append("{\"id\":7,\"item\":\"二级杆外径\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getOutsideDiameter2().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getOutsideDiameter2().getType()+"\"},");
			result_json.append("{\"id\":8,\"item\":\"二级杆内径\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getInsideDiameter2().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getInsideDiameter2().getType()+"\"},");
			result_json.append("{\"id\":9,\"item\":\"二级杆长度\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getLength2().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getLength2().getType()+"\"},");
			
			result_json.append("{\"id\":10,\"item\":\"三级杆级别\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getGrade3().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getGrade3().getType()+"\"},");
			result_json.append("{\"id\":11,\"item\":\"三级杆外径\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getOutsideDiameter3().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getOutsideDiameter3().getType()+"\"},");
			result_json.append("{\"id\":12,\"item\":\"三级杆内径\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getInsideDiameter3().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getInsideDiameter3().getType()+"\"},");
			result_json.append("{\"id\":13,\"item\":\"三级杆长度\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getLength3().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getLength3().getType()+"\"},");
			//油管组合数据
			result_json.append("{\"id\":1,\"item\":\"井名\",\"columnName\":\""+dataSourceConfig.getTubingStringTable().getColumns().getWellName().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getTubingStringTable().getColumns().getWellName().getType()+"\"},");
			result_json.append("{\"id\":2,\"item\":\"油管内径\",\"columnName\":\""+dataSourceConfig.getTubingStringTable().getColumns().getInsideDiameter().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getTubingStringTable().getColumns().getInsideDiameter().getType()+"\"},");
			//套管组合数据
			result_json.append("{\"id\":1,\"item\":\"井名\",\"columnName\":\""+dataSourceConfig.getCasingStringTable().getColumns().getWellName().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getCasingStringTable().getColumns().getWellName().getType()+"\"},");
			result_json.append("{\"id\":2,\"item\":\"套管内径\",\"columnName\":\""+dataSourceConfig.getCasingStringTable().getColumns().getInsideDiameter().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getCasingStringTable().getColumns().getInsideDiameter().getType()+"\"},");
			//泵数据
			result_json.append("{\"id\":1,\"item\":\"井名\",\"columnName\":\""+dataSourceConfig.getPumpTable().getColumns().getWellName().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getPumpTable().getColumns().getWellName().getType()+"\"},");
			result_json.append("{\"id\":2,\"item\":\"泵级别级别\",\"columnName\":\""+dataSourceConfig.getPumpTable().getColumns().getPumpGrade().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getPumpTable().getColumns().getPumpGrade().getType()+"\"},");
			result_json.append("{\"id\":3,\"item\":\"泵径\",\"columnName\":\""+dataSourceConfig.getPumpTable().getColumns().getPumpBoreDiameter().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getPumpTable().getColumns().getPumpBoreDiameter().getType()+"\"},");
			result_json.append("{\"id\":4,\"item\":\"柱塞长\",\"columnName\":\""+dataSourceConfig.getPumpTable().getColumns().getPlungerLength().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getPumpTable().getColumns().getPlungerLength().getType()+"\"},");
			//动态数据
			result_json.append("{\"id\":1,\"item\":\"井名\",\"columnName\":\""+dataSourceConfig.getProductionTable().getColumns().getWellName().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getProductionTable().getColumns().getWellName().getType()+"\"},");
			result_json.append("{\"id\":2,\"item\":\"含水率\",\"columnName\":\""+dataSourceConfig.getProductionTable().getColumns().getWaterCut().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getProductionTable().getColumns().getWaterCut().getType()+"\"},");
			result_json.append("{\"id\":3,\"item\":\"生产汽油比\",\"columnName\":\""+dataSourceConfig.getProductionTable().getColumns().getProductionGasOilRatio().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getProductionTable().getColumns().getProductionGasOilRatio().getType()+"\"},");
			result_json.append("{\"id\":4,\"item\":\"油压\",\"columnName\":\""+dataSourceConfig.getProductionTable().getColumns().getTubingPressure().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getProductionTable().getColumns().getTubingPressure().getType()+"\"},");
			result_json.append("{\"id\":5,\"item\":\"套压\",\"columnName\":\""+dataSourceConfig.getProductionTable().getColumns().getCasingPressure().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getProductionTable().getColumns().getCasingPressure().getType()+"\"},");
			result_json.append("{\"id\":6,\"item\":\"井口流温\",\"columnName\":\""+dataSourceConfig.getProductionTable().getColumns().getWellHeadFluidTemperature().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getProductionTable().getColumns().getWellHeadFluidTemperature().getType()+"\"},");
			result_json.append("{\"id\":7,\"item\":\"动液面\",\"columnName\":\""+dataSourceConfig.getProductionTable().getColumns().getProducingfluidLevel().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getProductionTable().getColumns().getProducingfluidLevel().getType()+"\"},");
			result_json.append("{\"id\":8,\"item\":\"泵挂\",\"columnName\":\""+dataSourceConfig.getProductionTable().getColumns().getPumpSettingDepth().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getProductionTable().getColumns().getPumpSettingDepth().getType()+"\"}");
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString();
	}
}
