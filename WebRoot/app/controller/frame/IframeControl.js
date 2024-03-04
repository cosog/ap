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
		&& module_Code != "RPCDeviceManager"
		&& module_Code != "PCPDeviceManager"
		&& module_Code != "SMSDeviceManagement"
		&& module_Code != "PumpingModelManagement"
		&& module_Code != "AuxiliaryDeviceManager"
		&& module_Code != "DeviceRealTimeMonitoring"
		&& module_Code != "DeviceHistoryQuery"
		&& module_Code != "DailyReport"
		&& module_Code != "LogQuery"
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
		var tabChange=false;
		var selectedDeviceType_global=Ext.getCmp('selectedDeviceType_global').getValue();
		var tabPanel = Ext.getCmp("DeviceManagerTabPanel");
		var activeId = tabPanel.getActiveTab().id;
		
		var tabId=0;
		if(tabPanel.getActiveTab().xtype=='tabpanel'){
			tabId=tabPanel.getActiveTab().activeTab.id.split('_')[1];
		}else{
			tabId=tabPanel.getActiveTab().id.split('_')[1];
		}
		
		if(selectedDeviceType_global!=tabId){
//			tabPanel.setActiveTab("RPCDeviceManagerPanel");
//			tabChange=true;
		}
		if(!tabChange){
			Ext.getCmp("DeviceSelectRow_Id").setValue(0);
	    	Ext.getCmp("DeviceSelectEndRow_Id").setValue(0);
			CreateAndLoadDeviceInfoTable(true);
		}
	}else if(module_Code == "RPCDeviceManager"){
		Ext.getCmp("RPCDeviceSelectRow_Id").setValue(0);
    	Ext.getCmp("RPCDeviceSelectEndRow_Id").setValue(0);
		CreateAndLoadRPCDeviceInfoTable(true);
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
	}else if(module_Code == "DeviceRealTimeMonitoring"){
		var tabChange=false;
		var selectedDeviceType_global=Ext.getCmp('selectedDeviceType_global').getValue();
		var tabPanel = Ext.getCmp("RealTimeMonitoringTabPanel");
		var activeId = tabPanel.getActiveTab().id;
		
		var orgId = Ext.getCmp('leftOrg_Id').getValue();
		var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
		
		
		if(selectedDeviceType_global!=deviceType){
//			tabPanel.setActiveTab("RealTimeMonitoringTabPanel_"+deviceType);
//			tabChange=true;
		}
		if(!tabChange){
			Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(-1);
			realTimeDataRefresh();
		}
	}else if(module_Code == "DeviceHistoryQuery"){
		var tabChange=false;
		var selectedDeviceType_global=Ext.getCmp('selectedDeviceType_global').getValue();
		var tabPanel = Ext.getCmp("HistoryQueryRootTabPanel");
		var activeId = tabPanel.getActiveTab().id;
		
		var orgId = Ext.getCmp('leftOrg_Id').getValue();
		var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
		var deviceCount=getCalculateTypeDeviceCount(orgId,deviceType,1);
		
		if(selectedDeviceType_global!=deviceType){
//			tabPanel.setActiveTab("HistoryQueryRootTabPanel_"+deviceType);
//			tabChange=true;
		}
		
		if(!tabChange){
			Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
			historyDataRefresh();
		}
	}else if(module_Code == "DailyReport"){
		var tabChange=false;
		var selectedDeviceType_global=Ext.getCmp('selectedDeviceType_global').getValue();
		var tabPanel = Ext.getCmp("ProductionWellDailyReportPanel_Id");
		var activeId = tabPanel.getActiveTab().id;
		if(selectedDeviceType_global==0 && activeId!='RPCDailyReportPanel_Id'){
			tabPanel.setActiveTab("RPCDailyReportPanel_Id");
			tabChange=true;
		}else if(selectedDeviceType_global==1 && activeId!='PCPDailyReportPanel_Id'){
			tabPanel.setActiveTab("PCPDailyReportPanel_Id");
			tabChange=true;
		}
		if(!tabChange){
			if(activeId=="RPCDailyReportPanel_Id"){
				var secondActiveId = Ext.getCmp("RPCDailyReportTabPanel").getActiveTab().id;
				if(secondActiveId=="RPCSingleWellDailyReportTabPanel_Id"){
					Ext.getCmp('RPCSingleWellDailyReportPanelWellListCombo_Id').setRawValue('');
					Ext.getCmp('RPCSingleWellDailyReportPanelWellListCombo_Id').setValue('');
					var gridPanel = Ext.getCmp("RPCSingleWellDailyReportGridPanel_Id");
					if (isNotVal(gridPanel)) {
						gridPanel.getStore().load();
					}else{
						Ext.create('AP.store.reportOut.RPCSingleWellDailyReportWellListStore');
					}
				}else if(secondActiveId=="RPCProductionDailyReportTabPanel_Id"){
					Ext.getCmp('RPCProductionDailyReportPanelWellListCombo_Id').setRawValue('');
					Ext.getCmp('RPCProductionDailyReportPanelWellListCombo_Id').setValue('');
					var gridPanel = Ext.getCmp("RPCProductionDailyReportGridPanel_Id");
	    			if (isNotVal(gridPanel)) {
	    				gridPanel.getStore().load();
	    			}else{
	    				Ext.create('AP.store.reportOut.RPCProductionDailyReportInstanceListStore');
	    			}
				}
			}else if(activeId=="PCPDailyReportPanel_Id"){
				var secondActiveId = Ext.getCmp("PCPDailyReportTabPanel").getActiveTab().id;
				if(secondActiveId=="PCPSingleWellDailyReportTabPanel_Id"){
					Ext.getCmp('PCPSingleWellDailyReportPanelWellListCombo_Id').setRawValue('');
					Ext.getCmp('PCPSingleWellDailyReportPanelWellListCombo_Id').setValue('');
					var gridPanel = Ext.getCmp("PCPSingleWellDailyReportGridPanel_Id");
					if (isNotVal(gridPanel)) {
						gridPanel.getStore().load();
					}else{
						Ext.create('AP.store.reportOut.PCPSingleWellDailyReportWellListStore');
					}
				}else if(secondActiveId=="PCPProductionDailyReportTabPanel_Id"){
					Ext.getCmp('PCPProductionDailyReportPanelWellListCombo_Id').setRawValue('');
					Ext.getCmp('PCPProductionDailyReportPanelWellListCombo_Id').setValue('');
					var gridPanel = Ext.getCmp("PCPProductionDailyReportGridPanel_Id");
	    			if (isNotVal(gridPanel)) {
	    				gridPanel.getStore().load();
	    			}else{
	    				Ext.create('AP.store.reportOut.PCPProductionDailyReportInstanceListStore');
	    			}
				}
			}
		}
	}else if(module_Code == "LogQuery"){
		var tabPanel = Ext.getCmp("LogQueryTabPanel");
		var activeId = tabPanel.getActiveTab().id;
		if(activeId=="DeviceOperationLogInfoPanel_Id"){
			var gridPanel = Ext.getCmp("DeviceOperationLogGridPanel_Id");
			if (isNotVal(gridPanel)) {
				gridPanel.getStore().load();
			}else{
				Ext.create('AP.store.log.DeviceOperationLogStore');
			}
		}else if(activeId=="SystemLogInfoPanel_Id"){
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
		}
	}else if(module_Code == "AlarmQuery"){
		var tabChange=false;
		var selectedDeviceType_global=Ext.getCmp('selectedDeviceType_global').getValue();
		var tabPanel = Ext.getCmp("AlarmQueryRootTabPanel");
		var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
		var deviceCount=getCalculateTypeDeviceCount(orgId,deviceType,1);
		
		if(selectedDeviceType_global!=deviceType){
//			tabPanel.setActiveTab("HistoryQueryRootTabPanel_"+deviceType);
//			tabChange=true;
		}
		
		
		
		
//		if(!tabChange){
//			var secondTabPanel = Ext.getCmp("AlarmQuerySecondTabPanel");
//			var secondActiveId = secondTabPanel.getActiveTab().id;
//			if(secondActiveId=="FESDiagramResultAlarmInfoPanel_Id"){
//				Ext.getCmp("FESDiagramResultAlarmOverviewSelectRow_Id").setValue(0);
//				Ext.getCmp("FESDiagramResultAlarmDeviceListComb_Id").setValue('');
//				Ext.getCmp("FESDiagramResultAlarmDeviceListComb_Id").setRawValue('');
//				Ext.getCmp("FESDiagramResultAlarmLevelComb_Id").setValue('');
//				Ext.getCmp("FESDiagramResultAlarmLevelComb_Id").setRawValue('');
//				var gridPanel = Ext.getCmp("FESDiagramResultAlarmOverviewGridPanel_Id");
//				if (isNotVal(gridPanel)) {
//					gridPanel.getStore().loadPage(1);
//				}else{
//					Ext.create('AP.store.alarmQuery.FESDiagramResultAlarmOverviewStore');
//				}
//			}else if(secondActiveId=="RunStatusAlarmInfoPanel_Id"){
//				Ext.getCmp("RunStatusAlarmOverviewSelectRow_Id").setValue(0);
//				Ext.getCmp("RunStatusAlarmDeviceListComb_Id").setValue('');
//				Ext.getCmp("RunStatusAlarmDeviceListComb_Id").setRawValue('');
//				Ext.getCmp("RunStatusAlarmLevelComb_Id").setValue('');
//				Ext.getCmp("RunStatusAlarmLevelComb_Id").setRawValue('');
//				var gridPanel = Ext.getCmp("RunStatusAlarmOverviewGridPanel_Id");
//				if (isNotVal(gridPanel)) {
//					gridPanel.getStore().loadPage(1);
//				}else{
//					Ext.create('AP.store.alarmQuery.RunStatusAlarmOverviewStore');
//				}
//			}else if(secondActiveId=="CommunicationAlarmInfoPanel_Id"){
//				Ext.getCmp("CommunicationAlarmOverviewSelectRow_Id").setValue(0);
//				Ext.getCmp("CommunicationAlarmDeviceListComb_Id").setValue('');
//				Ext.getCmp("CommunicationAlarmDeviceListComb_Id").setRawValue('');
//				Ext.getCmp("CommunicationAlarmLevelComb_Id").setValue('');
//				Ext.getCmp("CommunicationAlarmLevelComb_Id").setRawValue('');
//				var gridPanel = Ext.getCmp("CommunicationAlarmOverviewGridPanel_Id");
//				if (isNotVal(gridPanel)) {
//					gridPanel.getStore().loadPage(1);
//				}else{
//					Ext.create('AP.store.alarmQuery.CommunicationAlarmOverviewStore');
//				}
//			}else if(secondActiveId=="NumericValueAlarmInfoPanel_Id"){
//				Ext.getCmp("NumericValueAlarmOverviewSelectRow_Id").setValue(0);
//				Ext.getCmp("NumericValueAlarmDeviceListComb_Id").setValue('');
//				Ext.getCmp("NumericValueAlarmDeviceListComb_Id").setRawValue('');
//				Ext.getCmp("NumericValueAlarmLevelComb_Id").setValue('');
//				Ext.getCmp("NumericValueAlarmLevelComb_Id").setRawValue('');
//				var gridPanel = Ext.getCmp("NumericValueAlarmOverviewGridPanel_Id");
//				if (isNotVal(gridPanel)) {
//					gridPanel.getStore().loadPage(1);
//				}else{
//					Ext.create('AP.store.alarmQuery.NumericValueAlarmOverviewStore');
//				}
//			}else if(secondActiveId=="EnumValueAlarmInfoPanel_Id"){
//				Ext.getCmp("EnumValueAlarmOverviewSelectRow_Id").setValue(0);
//				Ext.getCmp("EnumValueAlarmDeviceListComb_Id").setValue('');
//				Ext.getCmp("EnumValueAlarmDeviceListComb_Id").setRawValue('');
//				Ext.getCmp("EnumValueAlarmLevelComb_Id").setValue('');
//				Ext.getCmp("EnumValueAlarmLevelComb_Id").setRawValue('');
//				var gridPanel = Ext.getCmp("EnumValueAlarmOverviewGridPanel_Id");
//				if (isNotVal(gridPanel)) {
//					gridPanel.getStore().loadPage(1);
//				}else{
//					Ext.create('AP.store.alarmQuery.EnumValueAlarmOverviewStore');
//				}
//			}else if(secondActiveId=="SwitchingValueAlarmInfoPanel_Id"){
//				Ext.getCmp("SwitchingValueAlarmOverviewSelectRow_Id").setValue(0);
//				Ext.getCmp("SwitchingValueAlarmDeviceListComb_Id").setValue('');
//				Ext.getCmp("SwitchingValueAlarmDeviceListComb_Id").setRawValue('');
//				Ext.getCmp("SwitchingValueAlarmLevelComb_Id").setValue('');
//				Ext.getCmp("SwitchingValueAlarmLevelComb_Id").setRawValue('');
//				var gridPanel = Ext.getCmp("SwitchingValueAlarmOverviewGridPanel_Id");
//				if (isNotVal(gridPanel)) {
//					gridPanel.getStore().loadPage(1);
//				}else{
//					Ext.create('AP.store.alarmQuery.SwitchingValueAlarmOverviewStore');
//				}
//			}
//		}
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
		var tabChange=false;
		var selectedDeviceType_global=Ext.getCmp('selectedDeviceType_global').getValue();
		var tabPanel = Ext.getCmp("CalculateMaintainingTabPanel");
		var activeId = tabPanel.getActiveTab().id;
		if(selectedDeviceType_global==0 && activeId!='RPCCalculateMaintainingInfoPanel_Id'){
			tabPanel.setActiveTab("RPCCalculateMaintainingInfoPanel_Id");
			tabChange=true;
		}else if(selectedDeviceType_global==1 && activeId!='PCPCalculateMaintainingInfoPanel_Id'){
			tabPanel.setActiveTab("PCPCalculateMaintainingInfoPanel_Id");
			tabChange=true;
		}
		if(!tabChange){
			if(activeId=="RPCCalculateMaintainingInfoPanel_Id"){
				refreshRPCCalculateMaintainingData();
			}else if(activeId=="PCPCalculateMaintainingInfoPanel_Id"){
				refreshPCPCalculateMaintainingData();
			}
		}
	}
	if(module_Code != "DeviceRealTimeMonitoring"){
		if(videoPlayrHelper.rpc.player1!=null && videoPlayrHelper.rpc.player1.pluginStatus.state.play){
			videoPlayrHelper.rpc.player1.stop();
		}
		if(videoPlayrHelper.rpc.player2!=null && videoPlayrHelper.rpc.player2.pluginStatus.state.play){
			videoPlayrHelper.rpc.player2.stop();
		}
		if(videoPlayrHelper.pcp.player1!=null && videoPlayrHelper.pcp.player1.pluginStatus.state.play){
			videoPlayrHelper.pcp.player1.stop();
		}
		if(videoPlayrHelper.pcp.player2!=null && videoPlayrHelper.pcp.player2.pluginStatus.state.play){
			videoPlayrHelper.pcp.player2.stop();
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