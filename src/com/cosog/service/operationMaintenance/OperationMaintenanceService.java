package com.cosog.service.operationMaintenance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.User;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.StringManagerUtils;

@Service("operationMaintenanceService")
public class OperationMaintenanceService<T> extends BaseService<T>  {
	@Autowired
	private CommonDataService service;
	
	public String batchExportModuleList(User user){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		String language=user!=null?user.getLanguageName():"";
		int languageValue=user!=null?user.getLanguage():0;
		int roleId=user!=null?user.getUserType():0;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
		String userModule="select t.md_code,t2.rm_matrix from tbl_module t ,tbl_module2role t2 where t.md_id=t2.rm_moduleid and t2.rm_roleid="+roleId;
		List<?> moduleList=this.findCallSql(userModule);
		List<String> userModuleCodeList=new ArrayList<>();
		Map<String,Boolean> userModuleEditMap=new HashMap<>();
		for(int i=0;i<moduleList.size();i++){
			Object[] obj = (Object[]) moduleList.get(i);
			userModuleCodeList.add(obj[0]+"");
			String rights=obj[1]+"";
			boolean editabled=false;
			if(StringManagerUtils.isNotNull(rights)){
				String[] rightArr=rights.split(",");
				if(rightArr.length==3){
					editabled=StringManagerUtils.stringToInteger(rightArr[1])==1;
				}
			}
			userModuleEditMap.put(obj[0]+"", editabled);
			
		}
		
		tree_json.append("[");
		
		
		
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\"模块\",");
		tree_json.append("\"code\":\"module\",");
		if(userModuleEditMap.containsKey("ModuleManagement")&& userModuleEditMap.get("ModuleManagement") ){
			tree_json.append("\"checked\": false,");
		}
		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\"字典\",");
		tree_json.append("\"code\":\"dataDictionary\",");
		if(userModuleEditMap.containsKey("DataDictionaryManagement")&& userModuleEditMap.get("DataDictionaryManagement") ){
			tree_json.append("\"checked\": false,");
		}
		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\"组织\",");
		tree_json.append("\"code\":\"organization\",");
		if(userModuleEditMap.containsKey("OrganizationAndUserManagement")&& userModuleEditMap.get("OrganizationAndUserManagement") ){
			tree_json.append("\"checked\": false,");
		}
		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\"角色\",");
		tree_json.append("\"code\":\"role\",");
		if(userModuleEditMap.containsKey("RoleManagement")&& userModuleEditMap.get("RoleManagement") ){
			tree_json.append("\"checked\": false,");
		}
		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\"用户\",");
		tree_json.append("\"code\":\"user\",");
		if(userModuleEditMap.containsKey("OrganizationAndUserManagement")&& userModuleEditMap.get("OrganizationAndUserManagement") ){
			tree_json.append("\"checked\": false,");
		}
		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\"协议\",");
		tree_json.append("\"code\":\"protocol\",");
		if(userModuleEditMap.containsKey("DriverManagement")&& userModuleEditMap.get("DriverManagement") ){
			tree_json.append("\"checked\": false,");
		}
		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\"采控单元\",");
		tree_json.append("\"code\":\"acqUnit\",");
		if(userModuleEditMap.containsKey("DriverManagement")&& userModuleEditMap.get("DriverManagement") ){
			tree_json.append("\"checked\": false,");
		}
		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\"显示单元\",");
		tree_json.append("\"code\":\"displayUnit\",");
		if(userModuleEditMap.containsKey("DriverManagement")&& userModuleEditMap.get("DriverManagement") ){
			tree_json.append("\"checked\": false,");
		}
		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\"报警单元\",");
		tree_json.append("\"code\":\"alarmUnit\",");
		if(userModuleEditMap.containsKey("DriverManagement")&& userModuleEditMap.get("DriverManagement") ){
			tree_json.append("\"checked\": false,");
		}
		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\"报表单元\",");
		tree_json.append("\"code\":\"reportUnit\",");
		if(userModuleEditMap.containsKey("DriverManagement")&& userModuleEditMap.get("DriverManagement") ){
			tree_json.append("\"checked\": false,");
		}
		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\"采控实例\",");
		tree_json.append("\"code\":\"acqInstance\",");
		if(userModuleEditMap.containsKey("DriverManagement")&& userModuleEditMap.get("DriverManagement") ){
			tree_json.append("\"checked\": false,");
		}
		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\"显示实例\",");
		tree_json.append("\"code\":\"displayInstance\",");
		if(userModuleEditMap.containsKey("DriverManagement")&& userModuleEditMap.get("DriverManagement") ){
			tree_json.append("\"checked\": false,");
		}
		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\"报警实例\",");
		tree_json.append("\"code\":\"alarmInstance\",");
		if(userModuleEditMap.containsKey("DriverManagement")&& userModuleEditMap.get("DriverManagement") ){
			tree_json.append("\"checked\": false,");
		}
		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\"报表实例\",");
		tree_json.append("\"code\":\"reportInstance\",");
		if(userModuleEditMap.containsKey("DriverManagement")&& userModuleEditMap.get("DriverManagement") ){
			tree_json.append("\"checked\": false,");
		}
		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\"辅件设备\",");
		tree_json.append("\"code\":\"auxiliaryDevice\",");
		if(userModuleEditMap.containsKey("AuxiliaryDeviceManager")&& userModuleEditMap.get("AuxiliaryDeviceManager") ){
			tree_json.append("\"checked\": false,");
		}
		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\"主设备\",");
		tree_json.append("\"code\":\"primaryDevice\",");
		if(userModuleEditMap.containsKey("DeviceManagement")&& userModuleEditMap.get("DeviceManagement") ){
			tree_json.append("\"checked\": false,");
		}
		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		result_json.append("{\"classes\":0,\"text\":\"数据列表\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		
		return result_json.toString();
	}
}
