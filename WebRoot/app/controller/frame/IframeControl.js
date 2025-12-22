/*******************************************************************************
 * 定义 IframeControl AP 为在app.js里定义的命名空间
 * 
 * @argument views： 改控制器里使用的view 为frame.IframeView
 * @cfg refs： 通过别名来引用 改view iframeView init：初始化改控制器 渲染iframeViewd 的时候，
 *      配置一个itemclick 事件
 */
Ext.define('AP.controller.frame.IframeControl', {
			extend : 'Ext.app.Controller',
			views : ['frame.IframeView'],
			refs : [{
						ref : 'iframeView',
						selector : 'iframeView'
					}],
			init : function() {
				this.control({
							'iframeView' : {
//								itemclick : extTreeItemsClk,
								selectionchange:extTreeItemsSelectedChange,
								afterrender : function() {
									Ext.fly('loading_div_id').remove(); 
								}
							}
						})
			}
		});
/*******************************************************************************
 * 点击左侧组织树时，调用该方法。向后台传入一个组织的ID值orgId
 ******************************************************************************/
function extTreeItemsSelectedChange(view, selected, o) {
	if (selected.length > 0) {
		var rec=selected[0];
		selectresult=[];
		var org_Id = foreachAndSearchOrgChildId(rec);// 获取到当前点击的组织ID
		selectReeTextRsult = [];
		var org_Name=selectEachTreeText(rec);
		if(!(org_Id.indexOf(userOrg_Ids)>0)){
			Ext.getCmp("leftOrg_Id").setValue("");// 将org_Id的值赋值给IframeView里的组织隐藏域
			Ext.getCmp("leftOrg_Id").setValue(org_Id);// 将org_Id的值赋值给IframeView里的组织隐藏域
		}
		if(!(org_Name.indexOf(userOrg_Names)>0)){
			Ext.getCmp("leftOrg_Name").setValue("");// 将org_Id的值赋值给IframeView里的组织隐藏域
			Ext.getCmp("leftOrg_Name").setValue(org_Name);// 将org_Id的值赋值给IframeView里的组织隐藏域
		}
		Ext.getCmp("orgTreeSelectedCoordX_Id").setValue(rec.data.orgCoordX);// 将组织X坐标的值赋值给IframeView里的组织隐藏域
		Ext.getCmp("orgTreeSelectedCoordY_Id").setValue(rec.data.orgCoordY);// 将组织Y坐标的值赋值给IframeView里的组织隐藏域
		Ext.getCmp("orgTreeSelectedShowLevel_Id").setValue(rec.data.orgShowLevel);// 将组织显示级别的值赋值给IframeView里的组织隐藏域
		var module_Code = Ext.getCmp("topModule_Id").getValue();// 拿到IframeView
		var secondTab_Code=getSecondTapCode(module_Code);
		refreshPanel(org_Id,secondTab_Code,rec);
	}
	
	// Ext.getCmp("frame_west").collapse(Ext.Component.DIRECTION_LEFT,true);
	return false;
};

/*******************************************************************************
 * 
 * 功能模块公用的方法，每次点击上部的功能模块时，调用该方法
 * 
 * @argument o：为对应模块view的URL
 * @argument mdCode：为当前模块的编码 例如monitor_MonitorPumpingUnit
 * @argument tab_url:动态创建底部的tab的url 传入改参数来动态的拼接出action的URL MonitorOutAllText
 *           MonitorPumpingUnit 诸如此类的变量全是是 vary.js文件定义的全局变量
 ******************************************************************************/
function addPanelEps(o, mdCode, tab_url) {

	var leftOrg_Id = Ext.getCmp("leftOrg_Id");
	
	if (!Ext.isEmpty(leftOrg_Id)) {
		leftOrg_Id = leftOrg_Id.getValue();
	}
	
	var secondTab_Code=getSecondTapCode(mdCode);
	
	refreshPanel(leftOrg_Id,secondTab_Code);
	return false;
}


getSecondTapCode=function(mdCode){
	var secondTab_Code="";
	var secondBottomTab_Id=Ext.getCmp("secondBottomTab_Id");
	if(isNotVal(secondBottomTab_Id)){
		secondBottomTab_Id=secondBottomTab_Id.getValue();
		}
	var productBottomTab_Id=Ext.getCmp("productBottomTab_Id");
	if(isNotVal(productBottomTab_Id)){
		productBottomTab_Id=productBottomTab_Id.getValue();
		}
	var imageBottomTab_Id=Ext.getCmp("imageBottomTab_Id");
	if(isNotVal(imageBottomTab_Id)){
		imageBottomTab_Id=imageBottomTab_Id.getValue();
		}
	
	try {
		var modules = mdCode.split("_");
		if (modules.length > 0) {
				Ext.getCmp("topModule_Id").setValue("");// 给IframeView里的模块隐藏域赋值
				Ext.getCmp("bottomTab_Id").setValue("");// 给IframeView里的模块隐藏域赋值
				//Ext.getCmp("secondBottomTab_Id").setValue("");// 给IframeView里的第二个tab的模块隐藏域赋值
				Ext.getCmp("topModule_Id").setValue(mdCode);// 给IframeView里的模块隐藏域赋值
				Ext.getCmp("bottomTab_Id").setValue(modules[1]);// 给IframeView里的模块隐藏域赋值
				if (modules.length > 2) {
					var aa=modules[1].indexOf("Diagnosis");
					if(modules[1].indexOf("Diagnosis")>-1){
						if(secondBottomTab_Id==(modules[2])){
							Ext.getCmp("secondBottomTab_Id").setValue("");// 给IframeView里的第二个tab的模块隐藏域赋值
							Ext.getCmp("secondBottomTab_Id").setValue(modules[2]);// 给IframeView里的第二个tab的模块隐藏域赋值
						}
						secondTab_Code=secondBottomTab_Id;
					}
					if(modules[1].indexOf("Compute")>-1){
						if(productBottomTab_Id==(modules[2])){
							Ext.getCmp("productBottomTab_Id").setValue("");// 给IframeView里的第二个tab的模块隐藏域赋值
							Ext.getCmp("productBottomTab_Id").setValue(modules[2]);// 给IframeView里的第二个tab的模块隐藏域赋值
						}
						secondTab_Code=productBottomTab_Id;
					}
					if(modules[1].indexOf("Image")>-1){
						if(imageBottomTab_Id==(modules[2])){
							Ext.getCmp("imageBottomTab_Id").setValue("");// 给IframeView里的第二个tab的模块隐藏域赋值
							Ext.getCmp("imageBottomTab_Id").setValue(modules[2]);// 给IframeView里的第二个tab的模块隐藏域赋值
						}
						secondTab_Code=imageBottomTab_Id;
					}
				}else{
					secondTab_Code="";
				}
		}
	} catch (e) {
		//Ext.Msg.alert("exception", " name: " + e.name + " \n message: "
						//+ e.message + " \n lineNumber: " + e.lineNumber
						//+ " \n fileName: " + e.fileName + " \n stack: "
						//+ e.stack);
	}
	return secondTab_Code;
	
};


refreshPanel=function(leftOrg_Id,secondTab_Code,rec){
	var module_Code = Ext.getCmp("topModule_Id").getValue();// 拿到IframeView
	var tab_Code = Ext.getCmp("bottomTab_Id").getValue();// 拿到IframeView
	//var secondTab_Code = Ext.getCmp("secondBottomTab_Id").getValue();// 拿到第二个tab隐藏域的值
	var tab_Id = Ext.getCmp(tab_Code);
	var url;
	var countId;
	
	if (tab_Id != undefined) {
		tab_Id.show();
	}
	var modules = module_Code.split("_");
	
	
	var panel_Id = "";
	if (module_Code != "OrganizationAndUserManagement"
		&& module_Code != "ProductionReport"
		&& module_Code != "ProductionData"
		&& module_Code != "WellInformation"
		&& module_Code != "SRPDeviceManager"
		&& module_Code != "PCPDeviceManager"
		&& module_Code != "SMSDeviceManagement"
		&& module_Code != "PumpingModelManagement"
		&& module_Code != "AuxiliaryDeviceManager"
		&& module_Code != "SMSDeviceManager"
		&& module_Code != "DeviceRealTimeMonitoring"
		&& module_Code != "DeviceHistoryQuery"
		&& module_Code != "DailyReport"
		&& module_Code != "DeviceOperationLogQuery"
		&& module_Code != "SystemLogQuery"
		&& module_Code != "AlarmQuery"
		&& module_Code != "AlarmSet"
		&& module_Code != "UpstreamAndDownstreamInteraction"
		&& module_Code != "CalculateMaintaining"
//		&& module_Code != "RoleManagement"
			) {
		if (modules.length > 2) {
			if(secondTab_Code!= modules[2]){
				modules[2]=secondTab_Code;
			}
			panel_Id = tab_Code + "_" + secondTab_Code + "_Id";
		} else {
			panel_Id = tab_Code + "_Id";
		}
		var ext_panel = Ext.getCmp(panel_Id);
		if (ext_panel != undefined||tab_Code=="BalanceHistory"||tab_Code=="BalanceCycle") {
			// 工况诊断的统计信息刷新及图形
			if (secondTab_Code == "ResultDistributionStatistic") {
				ext_panel.getStore().load();
			} else if (secondTab_Code == "SingleDinagnosisAnalysis") {
				var leftOrg_Id = Ext.getCmp("leftOrg_Id");
				if (!Ext.isEmpty(leftOrg_Id)) {
					leftOrg_Id = leftOrg_Id.getValue();
				}
				ext_panel.getStore().load();
				var iframe_Id = Ext.get("iframeSingleDyn_Id");
				//iframe_Id.dom.src = context+ "/login/list?orgId="+ leftOrg_Id;
			} else if (secondTab_Code == "DynContrastDinagnosisAnalysis") {
				var leftOrg_Id = Ext.getCmp("leftOrg_Id");
				if (!Ext.isEmpty(leftOrg_Id)) {
					leftOrg_Id = leftOrg_Id.getValue();
				}
				ext_panel.getStore().load();
			} else if (modules[2] == "ProductionStatistics"
					&& secondTab_Code == "ProductionStatistics") {
				var chartImage_Id = Ext.getCmp("ProductionStatisticsChart_TypeId").getValue();// 拿到IframeView
				var ProductionStatisticsChart_TypeId = Ext
						.getCmp("ProductionStatisticsChart_TypeId").getValue();
				if (ProductionStatisticsChart_TypeId == "ColumnInfoCharts_Id") {
					showComputeChart("AP.view.compute.ColumnInfoChart");
				} else if (ProductionStatisticsChart_TypeId == "PieInfoCharts_Id") {
					showComputeChart("AP.view.compute.PieInfoChart");
				}
			} else if (tab_Code == "CurvePumpingUnit") {
				ext_panel.getStore().load();
				Ext.getCmp("CurvePumpingUnit_LineChartId").store.load();
			} else if (tab_Code == "CurveWaterInjectionWell") {
				ext_panel.getStore().load();
				Ext.getCmp("CurveWaterInjectionWell_LineChartId").store.load();
			} else if(tab_Code=="SurfaceCardQuery"){
//				page=1;
//				Ext.create("AP.store.graphicalQuery.SurfaceCardStore");
			} else if(tab_Code=="PumpCardQuery"){
				page=1;
				Ext.create("AP.store.graphicalQuery.PumpCardStore");
			} else if(tab_Code=="RodPressQuery"){
				page=1;
				Ext.create("AP.store.graphicalQuery.RodPressStore");
			} else if(tab_Code=="PumpEfficiencyQuery"){
				page=1;
				Ext.create("AP.store.graphicalQuery.PumpEfficiencyStore");
			} else if(tab_Code=="BalanceHistory"){
				var tabPanel = Ext.getCmp("BalanceHistoryTab_Id");
				var activeId = tabPanel.getActiveTab().id;
				if(activeId=="HistoryTorqueMaxValue_Id"){
					Ext.getCmp("TorqueMaxValueHistoryWellListGrid_Id").getStore().load();
				}
			}else if(tab_Code=="BalanceCycle"){
				var tabPanel = Ext.getCmp("BalanceCycleTab_Id");
				var activeId = tabPanel.getActiveTab().id;
				if(activeId=="CycleTorqueMaxValue_Id"){
					var tabPanel2 = Ext.getCmp("CycleTorqueMaxValueTappanel_Id");
					var activeId2 = tabPanel2.getActiveTab().id;
					if(activeId2=="CycleTorqueMaxValuePanel_Id"){
						Ext.getCmp("TorqueMaxValueCycleWellListGrid_Id").getStore().load();
					}else{
						Ext.getCmp("TorqueMaxValueTotalWellListGrid_Id").getStore().load();
					}
				}
			} else {
				ext_panel.getStore().load();
			}
		} else {
			// Ext.Msg.alert("info", '抱歉，该模块正在开发中... ');
		}
	}else if(module_Code == "OrganizationAndUserManagement"){
		var gridPanel = Ext.getCmp("OrgInfoTreeGridView_Id");
		if (isNotVal(gridPanel)) {
			gridPanel.getStore().load();
		}else{
			Ext.create('AP.store.orgAndUser.OrgInfoStore');
		}
	}
	else if(module_Code == "WellInformation"){
		var selectedDeviceType_global=Ext.getCmp('selectedDeviceType_global').getValue();
		var tabPanel = Ext.getCmp("DeviceManagerTabPanel");
		var deviceType=getDeviceTypeFromTabId("DeviceManagerTabPanel");
		if(selectedDeviceType_global!=deviceType){
			var deviceTypeActiveId=getDeviceTypeActiveId();
			if(tabPanel.getActiveTab().id!=tabPanel.getComponent(deviceTypeActiveId.firstActiveTab).id){
				tabPanel.setActiveTab(deviceTypeActiveId.firstActiveTab);
			}
			if(tabPanel.getActiveTab().xtype=='tabpanel'){
				if(tabPanel.getActiveTab().getActiveTab().id!=tabPanel.getActiveTab().getComponent(deviceTypeActiveId.secondActiveTab).id){
					tabPanel.getActiveTab().setActiveTab(deviceTypeActiveId.secondActiveTab);
				}
			}
		}else{
			deviceManagerDataRefresh();
		}
	}else if(module_Code == "SRPDeviceManager"){
		Ext.getCmp("SRPDeviceSelectRow_Id").setValue(0);
    	Ext.getCmp("SRPDeviceSelectEndRow_Id").setValue(0);
		CreateAndLoadSRPDeviceInfoTable(true);
	}else if(module_Code == "PCPDeviceManager"){
		Ext.getCmp("PCPDeviceSelectRow_Id").setValue(0);
    	Ext.getCmp("PCPDeviceSelectEndRow_Id").setValue(0);
		CreateAndLoadPCPDeviceInfoTable(true);
	}else if(module_Code == "SMSDeviceManagement"){
		CreateAndLoadSMSDeviceInfoTable(true);
	}else if(module_Code == "PumpingModelManagement"){
		CreateAndLoadPumpingModelInfoTable(true);
	}else if(module_Code == "AuxiliaryDeviceManager"){
		CreateAndLoadAuxiliaryDeviceInfoTable(true);
	}else if(module_Code == "SMSDeviceManager"){
		CreateAndLoadSMSDeviceInfoTable(true);
	}else if(module_Code == "DeviceRealTimeMonitoring"){
		var selectedDeviceType_global=Ext.getCmp('selectedDeviceType_global').getValue();
		var tabPanel = Ext.getCmp("RealTimeMonitoringTabPanel");
		var activeId = tabPanel.getActiveTab().id;
		
		var orgId = Ext.getCmp('leftOrg_Id').getValue();
		var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
		
		if(selectedDeviceType_global==0){
			Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(-1);
			realTimeDataRefresh();
		}else{
			if(selectedDeviceType_global!=deviceType){
				var deviceTypeActiveId=getDeviceTypeActiveId();
				if(tabPanel.getActiveTab().id!=tabPanel.getComponent(deviceTypeActiveId.firstActiveTab).id){
					tabPanel.setActiveTab(deviceTypeActiveId.firstActiveTab);
				}
				if(tabPanel.getActiveTab().xtype=='tabpanel'){
					if(tabPanel.getActiveTab().getActiveTab().id!=tabPanel.getActiveTab().getComponent(deviceTypeActiveId.secondActiveTab).id){
						tabPanel.getActiveTab().setActiveTab(deviceTypeActiveId.secondActiveTab);
					}
				}
			}else{
				Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(-1);
				realTimeDataRefresh();
			}
		}
	}else if(module_Code == "DeviceHistoryQuery"){
		var selectedDeviceType_global=Ext.getCmp('selectedDeviceType_global').getValue();
		var tabPanel = Ext.getCmp("HistoryQueryRootTabPanel");
		var activeId = tabPanel.getActiveTab().id;
		
		var orgId = Ext.getCmp('leftOrg_Id').getValue();
		var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
		var deviceCount=getCalculateTypeDeviceCount(orgId,deviceType,1);
		
		if(selectedDeviceType_global!=deviceType){
			var deviceTypeActiveId=getDeviceTypeActiveId();
			if(tabPanel.getActiveTab().id!=tabPanel.getComponent(deviceTypeActiveId.firstActiveTab).id){
				tabPanel.setActiveTab(deviceTypeActiveId.firstActiveTab);
			}
			if(tabPanel.getActiveTab().xtype=='tabpanel'){
				if(tabPanel.getActiveTab().getActiveTab().id!=tabPanel.getActiveTab().getComponent(deviceTypeActiveId.secondActiveTab).id){
					tabPanel.getActiveTab().setActiveTab(deviceTypeActiveId.secondActiveTab);
				}
			}
		}else{
			Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
			historyDataRefresh();
		}
	}else if(module_Code == "DailyReport"){
		var selectedDeviceType_global=Ext.getCmp('selectedDeviceType_global').getValue();
		var deviceType=getDeviceTypeFromTabId("ProductionReportRootTabPanel");
		var tabPanel = Ext.getCmp("ProductionReportRootTabPanel");
		if(selectedDeviceType_global!=deviceType){
			var deviceTypeActiveId=getDeviceTypeActiveId();
			if(tabPanel.getActiveTab().id!=tabPanel.getComponent(deviceTypeActiveId.firstActiveTab).id){
				tabPanel.setActiveTab(deviceTypeActiveId.firstActiveTab);
			}
			if(tabPanel.getActiveTab().xtype=='tabpanel'){
				if(tabPanel.getActiveTab().getActiveTab().id!=tabPanel.getActiveTab().getComponent(deviceTypeActiveId.secondActiveTab).id){
					tabPanel.getActiveTab().setActiveTab(deviceTypeActiveId.secondActiveTab);
				}
			}
		}else{
			reportDataRefresh();
		}
	}else if(module_Code == 'SystemLogQuery'){
		Ext.getCmp('systemLogUserListComb_Id').setValue("");
		Ext.getCmp('systemLogUserListComb_Id').setRawValue("");
		
		Ext.getCmp('systemLogActionListComb_Id').setValue("");
		Ext.getCmp('systemLogActionListComb_Id').setRawValue("");
		
		var gridPanel = Ext.getCmp("SystemLogGridPanel_Id");
		if (isNotVal(gridPanel)) {
			gridPanel.getStore().load();
		}else{
			Ext.create('AP.store.log.SystemLogStore');
		}
	}else if(module_Code == "DeviceOperationLogQuery"){
		var selectedDeviceType_global=Ext.getCmp('selectedDeviceType_global').getValue();
		var deviceType=getDeviceTypeFromTabId("DeviceOperationLogRootTabPanel");
		var tabPanel = Ext.getCmp("DeviceOperationLogRootTabPanel");
		if(selectedDeviceType_global!=deviceType){
			var deviceTypeActiveId=getDeviceTypeActiveId();
			if(tabPanel.getActiveTab().id!=tabPanel.getComponent(deviceTypeActiveId.firstActiveTab).id){
				tabPanel.setActiveTab(deviceTypeActiveId.firstActiveTab);
			}
			if(tabPanel.getActiveTab().xtype=='tabpanel'){
				if(tabPanel.getActiveTab().getActiveTab().id!=tabPanel.getActiveTab().getComponent(deviceTypeActiveId.secondActiveTab).id){
					tabPanel.getActiveTab().setActiveTab(deviceTypeActiveId.secondActiveTab);
				}
			}
		}else{
			deviceOperationLogDataRefresh();
		}
	}else if(module_Code == "AlarmQuery"){
		var selectedDeviceType_global=Ext.getCmp('selectedDeviceType_global').getValue();
		var tabPanel = Ext.getCmp("AlarmQueryRootTabPanel");
		var orgId = Ext.getCmp('leftOrg_Id').getValue();
		var deviceType=getDeviceTypeFromTabId("AlarmQueryRootTabPanel");
		var deviceCount=getCalculateTypeDeviceCount(orgId,deviceType,1);
		
		if(selectedDeviceType_global!=deviceType){
			var deviceTypeActiveId=getDeviceTypeActiveId();
			if(tabPanel.getActiveTab().id!=tabPanel.getComponent(deviceTypeActiveId.firstActiveTab).id){
				tabPanel.setActiveTab(deviceTypeActiveId.firstActiveTab);
			}
			if(tabPanel.getActiveTab().xtype=='tabpanel'){
				if(tabPanel.getActiveTab().getActiveTab().id!=tabPanel.getActiveTab().getComponent(deviceTypeActiveId.secondActiveTab).id){
					tabPanel.getActiveTab().setActiveTab(deviceTypeActiveId.secondActiveTab);
				}
			}
		}else{
			alarmQueryDataRefresh();
		}
	}else if(module_Code == "AlarmSet"){
		getAlarmLevelColor();
	}else if(module_Code == "UpstreamAndDownstreamInteraction"){
		var gridPanel = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id");
		if (isNotVal(gridPanel)) {
			gridPanel.getStore().load();
		}else{
			Ext.create('AP.store.well.UpstreamAndDownstreamInteractionWellListStore');
		}
	}else if(module_Code == "CalculateMaintaining"){
		var selectedDeviceType_global=Ext.getCmp('selectedDeviceType_global').getValue();
		var deviceType=getDeviceTypeFromTabId("CalculateMaintainingRootTabPanel");
		var tabPanel = Ext.getCmp("CalculateMaintainingRootTabPanel");
		if(selectedDeviceType_global!=deviceType){
			var deviceTypeActiveId=getDeviceTypeActiveId();
			if(tabPanel.getActiveTab().id!=tabPanel.getComponent(deviceTypeActiveId.firstActiveTab).id){
				tabPanel.setActiveTab(deviceTypeActiveId.firstActiveTab);
			}
			if(tabPanel.getActiveTab().xtype=='tabpanel'){
				if(tabPanel.getActiveTab().getActiveTab().id!=tabPanel.getActiveTab().getComponent(deviceTypeActiveId.secondActiveTab).id){
					tabPanel.getActiveTab().setActiveTab(deviceTypeActiveId.secondActiveTab);
				}
			}
		}else{
			refreshCalculateMaintainingData();
		}
	}
	if(module_Code != "DeviceRealTimeMonitoring"){
		if(videoPlayrHelper.player1!=null && videoPlayrHelper.player1.pluginStatus.state.play){
			videoPlayrHelper.player1.stop();
		}
		if(videoPlayrHelper.player2!=null && videoPlayrHelper.player2.pluginStatus.state.play){
			videoPlayrHelper.player2.stop();
		}
	}
	
	saveAccessModuleLog(module_Code);
	
	return false;
	
}

//保存模块访问日志
function saveAccessModuleLog(module_Code){
	var tabPanel = Ext.getCmp("frame_center_ids");
	var activeTab=tabPanel.setActiveTab();
	if(isNotVal(activeTab)){
		Ext.Ajax.request({
			method:'POST',
			url:context + '/logQueryController/saveAccessModuleLog',
			success:function(response) {
				
			},
			failure:function(){
				
			},
			params: {
				moduleCode: module_Code,
				moduleName: activeTab.title
	        }
		});  
	}
}

function getDeviceTabInstanceInfoByDeviceId(deviceId){
	var r={};
	Ext.Ajax.request({
		method:'POST',
		url:context + '/operationMaintenanceController/getDeviceTabInstanceInfoByDeviceId',
		async:false,
		success:function(response) {
			r=Ext.JSON.decode(response.responseText);
		},
		params: {
			deviceId:deviceId
		}
	});
	return r;
}

function getProjectTabInstanceInfoByDeviceType(deviceType){
	var r={};
	r.DeviceRealTimeMonitoring={
			FESDiagramStatPie: false,
            CommStatusStatPie: false,
            RunStatusStatPie: false	
	};
	r.DeviceHistoryQuery={
			FESDiagramStatPie: false,
            CommStatusStatPie: false,
            RunStatusStatPie: false
	};
	if(r.AlarmQuery==undefined){
		r.AlarmQuery={
				FESDiagramResultAlarm: false,
	            RunStatusAlarm: false,
	            CommStatusAlarm: false,
	            NumericValueAlarm: false,
	            EnumValueAlarm: false,
	            SwitchingValueAlarm: false
		};
	}
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/operationMaintenanceController/getProjectTabInstanceInfoByDeviceType',
		async:false,
		success:function(response) {
			var result=Ext.JSON.decode(response.responseText);
			if(result.config.length>0){
				for(var i=0;i<result.config.length;i++){
					var showRealtimeFESDiagramStatPie=false;
					var showRealtimeCommStatusStatPie=false;
					var showRealtimeRunStatusStatPie=false;
					
					var showHistoryFESDiagramStatPie=false;
					var showHistoryCommStatusStatPie=false;
					var showHistoryRunStatusStatPie=false;
					
					var showAlarmQueryFESDiagramResultAlarm=false;
					var showAlarmQueryRunStatusAlarm=false;
					var showAlarmQueryCommStatusAlarm=false;
					var showAlarmQueryNumericValueAlarm=false;
					var showAlarmQueryEnumValueAlarm=false;
					var showAlarmQuerySwitchingValueAlarm=false;
					
					
					if(result.config[i].DeviceRealTimeMonitoring!=undefined){
						showRealtimeFESDiagramStatPie=result.config[i].DeviceRealTimeMonitoring.FESDiagramStatPie!=undefined?result.config[i].DeviceRealTimeMonitoring.FESDiagramStatPie:false;
						showRealtimeCommStatusStatPie=result.config[i].DeviceRealTimeMonitoring.CommStatusStatPie!=undefined?result.config[i].DeviceRealTimeMonitoring.CommStatusStatPie:false;
						showRealtimeRunStatusStatPie=result.config[i].DeviceRealTimeMonitoring.RunStatusStatPie!=undefined?result.config[i].DeviceRealTimeMonitoring.RunStatusStatPie:false;
					}
					
					if(result.config[i].DeviceHistoryQuery!=undefined){
						showHistoryFESDiagramStatPie=result.config[i].DeviceHistoryQuery.FESDiagramStatPie!=undefined?result.config[i].DeviceHistoryQuery.FESDiagramStatPie:false;
						showHistoryCommStatusStatPie=result.config[i].DeviceHistoryQuery.CommStatusStatPie!=undefined?result.config[i].DeviceHistoryQuery.CommStatusStatPie:false;
						showHistoryRunStatusStatPie=result.config[i].DeviceHistoryQuery.RunStatusStatPie!=undefined?result.config[i].DeviceHistoryQuery.RunStatusStatPie:false;
					}
					
					if(result.config[i].AlarmQuery!=undefined){
						showAlarmQueryFESDiagramResultAlarm=result.config[i].AlarmQuery.FESDiagramResultAlarm!=undefined?result.config[i].AlarmQuery.FESDiagramResultAlarm:false;
						showAlarmQueryRunStatusAlarm=result.config[i].AlarmQuery.RunStatusAlarm!=undefined?result.config[i].AlarmQuery.RunStatusAlarm:false;
						showAlarmQueryCommStatusAlarm=result.config[i].AlarmQuery.CommStatusAlarm!=undefined?result.config[i].AlarmQuery.CommStatusAlarm:false;
						
						showAlarmQueryNumericValueAlarm=result.config[i].AlarmQuery.NumericValueAlarm!=undefined?result.config[i].AlarmQuery.NumericValueAlarm:false;
						showAlarmQueryEnumValueAlarm=result.config[i].AlarmQuery.EnumValueAlarm!=undefined?result.config[i].AlarmQuery.EnumValueAlarm:false;
						showAlarmQuerySwitchingValueAlarm=result.config[i].AlarmQuery.SwitchingValueAlarm!=undefined?result.config[i].AlarmQuery.SwitchingValueAlarm:false;
					}
					
					
					if(r.DeviceRealTimeMonitoring.FESDiagramStatPie==false){
						r.DeviceRealTimeMonitoring.FESDiagramStatPie=showRealtimeFESDiagramStatPie;
					}
					if(r.DeviceRealTimeMonitoring.CommStatusStatPie==false){
						r.DeviceRealTimeMonitoring.CommStatusStatPie=showRealtimeCommStatusStatPie;
					}
					if(r.DeviceRealTimeMonitoring.RunStatusStatPie==false){
						r.DeviceRealTimeMonitoring.RunStatusStatPie=showRealtimeRunStatusStatPie;
					}
					
					if(r.DeviceHistoryQuery.FESDiagramStatPie==false){
						r.DeviceHistoryQuery.FESDiagramStatPie=showHistoryFESDiagramStatPie;
					}
					if(r.DeviceHistoryQuery.CommStatusStatPie==false){
						r.DeviceHistoryQuery.CommStatusStatPie=showHistoryCommStatusStatPie;
					}
					if(r.DeviceHistoryQuery.RunStatusStatPie==false){
						r.DeviceHistoryQuery.RunStatusStatPie=showHistoryRunStatusStatPie;
					}
					
					if(r.AlarmQuery.FESDiagramResultAlarm==false){
						r.AlarmQuery.FESDiagramResultAlarm=showAlarmQueryFESDiagramResultAlarm;
					}
					if(r.AlarmQuery.RunStatusAlarm==false){
						r.AlarmQuery.RunStatusAlarm=showAlarmQueryRunStatusAlarm;
					}
					if(r.AlarmQuery.CommStatusAlarm==false){
						r.AlarmQuery.CommStatusAlarm=showAlarmQueryCommStatusAlarm;
					}
					if(r.AlarmQuery.NumericValueAlarm==false){
						r.AlarmQuery.NumericValueAlarm=showAlarmQueryNumericValueAlarm;
					}
					if(r.AlarmQuery.EnumValueAlarm==false){
						r.AlarmQuery.EnumValueAlarm=showAlarmQueryEnumValueAlarm;
					}
					if(r.AlarmQuery.SwitchingValueAlarm==false){
						r.AlarmQuery.SwitchingValueAlarm=showAlarmQuerySwitchingValueAlarm;
					}
				}
				
			}
		},
		params: {
			deviceType:deviceType
		}
	});
	
	if(r.DeviceRealTimeMonitoring.FESDiagramStatPie==false && r.DeviceRealTimeMonitoring.CommStatusStatPie==false && r.DeviceRealTimeMonitoring.RunStatusStatPie==false){
		r.DeviceRealTimeMonitoring.CommStatusStatPie=true;
		r.DeviceRealTimeMonitoring.RunStatusStatPie=true;
	}
	
	if(r.DeviceHistoryQuery.FESDiagramStatPie==false && r.DeviceHistoryQuery.CommStatusStatPie==false && r.DeviceHistoryQuery.RunStatusStatPie==false){
		r.DeviceHistoryQuery.CommStatusStatPie=true;
		r.DeviceHistoryQuery.RunStatusStatPie=true;
	}
	
	if(r.AlarmQuery.FESDiagramResultAlarm==false 
			&& r.AlarmQuery.RunStatusAlarm==false 
			&& r.AlarmQuery.CommStatusAlarm==false
			&& r.AlarmQuery.NumericValueAlarm==false
			&& r.AlarmQuery.EnumValueAlarm==false
			&& r.AlarmQuery.SwitchingValueAlarm==false
			){
		r.AlarmQuery.RunStatusAlarm=true;
		r.AlarmQuery.CommStatusAlarm=true;
		r.AlarmQuery.NumericValueAlarm=true;
		r.AlarmQuery.EnumValueAlarm=true;
		r.AlarmQuery.SwitchingValueAlarm=true;
	}
	
	return r;
}