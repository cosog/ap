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
		if(selectedDeviceType_global==0 && activeId!='RPCDeviceManagerPanel'){
			tabPanel.setActiveTab("RPCDeviceManagerPanel");
			tabChange=true;
		}else if(selectedDeviceType_global==1 && activeId!='PCPDeviceManagerPanel'){
			tabPanel.setActiveTab("PCPDeviceManagerPanel");
			tabChange=true;
		}
		if(!tabChange){
			if(activeId=="RPCDeviceManagerPanel"){
				Ext.getCmp("RPCDeviceSelectRow_Id").setValue(0);
		    	Ext.getCmp("RPCDeviceSelectEndRow_Id").setValue(0);
				CreateAndLoadRPCDeviceInfoTable(true);
			}else if(activeId=="PCPDeviceManagerPanel"){
				Ext.getCmp("PCPDeviceSelectRow_Id").setValue(0);
		    	Ext.getCmp("PCPDeviceSelectEndRow_Id").setValue(0);
				CreateAndLoadPCPDeviceInfoTable(true);
			}
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
		if(selectedDeviceType_global==0 && activeId!='RPCRealTimeMonitoringInfoPanel_Id'){
			tabPanel.setActiveTab("RPCRealTimeMonitoringInfoPanel_Id");
			tabChange=true;
		}else if(selectedDeviceType_global==1 && activeId!='PCPRealTimeMonitoringInfoPanel_Id'){
			tabPanel.setActiveTab("PCPRealTimeMonitoringInfoPanel_Id");
			tabChange=true;
		}
		if(!tabChange){
			if(activeId=="RPCRealTimeMonitoringInfoPanel_Id"){
				Ext.getCmp("RPCRealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(-1);
				var statTabActiveId = Ext.getCmp("RPCRealTimeMonitoringStatTabPanel").getActiveTab().id;
				if(statTabActiveId=="RPCRealTimeMonitoringFESdiagramResultStatGraphPanel_Id"){
					loadAndInitFESdiagramResultStat(true);
				}else if(statTabActiveId=="RPCRealTimeMonitoringStatGraphPanel_Id"){
					loadAndInitCommStatusStat(true);
				}else if(statTabActiveId=="RPCRealTimeMonitoringRunStatusStatGraphPanel_Id"){
					loadAndInitRunStatusStat(true);
				}else if(statTabActiveId=="RPCRealTimeMonitoringDeviceTypeStatGraphPanel_Id"){
					loadAndInitDeviceTypeStat(true);
				}
				Ext.getCmp('RealTimeMonitoringRPCDeviceListComb_Id').setValue('');
				Ext.getCmp('RealTimeMonitoringRPCDeviceListComb_Id').setRawValue('');
				
				refreshRealtimeDeviceListDataByPage(parseInt(Ext.getCmp("selectedRPCDeviceId_global").getValue()),0,Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id"),'AP.store.realTimeMonitoring.RPCRealTimeMonitoringWellListStore');
			}else if(activeId=="PCPRealTimeMonitoringInfoPanel_Id"){
				var statTabActiveId = Ext.getCmp("PCPRealTimeMonitoringStatTabPanel").getActiveTab().id;
				if(statTabActiveId=="PCPRealTimeMonitoringStatGraphPanel_Id"){
					loadAndInitCommStatusStat(true);
				}else if(statTabActiveId=="PCPRealTimeMonitoringRunStatusStatGraphPanel_Id"){
					loadAndInitRunStatusStat(true);
				}else if(statTabActiveId=="PCPRealTimeMonitoringDeviceTypeStatGraphPanel_Id"){
					loadAndInitDeviceTypeStat(true);
				}
				Ext.getCmp('RealTimeMonitoringPCPDeviceListComb_Id').setValue('');
				Ext.getCmp('RealTimeMonitoringPCPDeviceListComb_Id').setRawValue('');
				
				refreshRealtimeDeviceListDataByPage(parseInt(Ext.getCmp("selectedPCPDeviceId_global").getValue()),1,Ext.getCmp("PCPRealTimeMonitoringListGridPanel_Id"),'AP.store.realTimeMonitoring.PCPRealTimeMonitoringWellListStore');
			}
		}
	}else if(module_Code == "DeviceHistoryQuery"){
		var tabChange=false;
		var selectedDeviceType_global=Ext.getCmp('selectedDeviceType_global').getValue();
		var tabPanel = Ext.getCmp("HistoryQueryTabPanel");
		var activeId = tabPanel.getActiveTab().id;
		if(selectedDeviceType_global==0 && activeId!='RPCHistoryQueryInfoPanel_Id'){
			tabPanel.setActiveTab("RPCHistoryQueryInfoPanel_Id");
			tabChange=true;
		}else if(selectedDeviceType_global==1 && activeId!='PCPHistoryQueryInfoPanel_Id'){
			tabPanel.setActiveTab("PCPHistoryQueryInfoPanel_Id");
			tabChange=true;
		}
		if(!tabChange){
			var realtimeTurnToHisyorySign=Ext.getCmp("realtimeTurnToHisyorySign_Id").getValue();
			
			if(activeId=="RPCHistoryQueryInfoPanel_Id"){
				Ext.getCmp("RPCHistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
				var statTabActiveId = Ext.getCmp("RPCHistoryQueryStatTabPanel").getActiveTab().id;
				if(statTabActiveId=="RPCHistoryQueryFESdiagramResultStatGraphPanel_Id"){
					loadAndInitHistoryQueryFESdiagramResultStat(true);
				}else if(statTabActiveId=="RPCHistoryQueryStatGraphPanel_Id"){
					loadAndInitHistoryQueryCommStatusStat(true);
				}else if(statTabActiveId=="RPCHistoryQueryRunStatusStatGraphPanel_Id"){
					loadAndInitHistoryQueryRunStatusStat(true);
				}else if(statTabActiveId=="RPCHistoryQueryDeviceTypeStatGraphPanel_Id"){
					loadAndInitHistoryQueryDeviceTypeStat(true);
				}
				
				if(isNotVal(realtimeTurnToHisyorySign)){//如果是实时跳转
					Ext.getCmp("realtimeTurnToHisyorySign_Id").setValue('');
				}else{
					Ext.getCmp('HistoryQueryRPCDeviceListComb_Id').setValue('');
					Ext.getCmp('HistoryQueryRPCDeviceListComb_Id').setRawValue('');
				}
				
				refreshHistoryDeviceListDataByPage(parseInt(Ext.getCmp("selectedRPCDeviceId_global").getValue()),0,Ext.getCmp("RPCHistoryQueryDeviceListGridPanel_Id"),'AP.store.historyQuery.RPCHistoryQueryWellListStore');
			}else if(activeId=="PCPHistoryQueryInfoPanel_Id"){
				Ext.getCmp("PCPHistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
				var statTabActiveId = Ext.getCmp("PCPHistoryQueryStatTabPanel").getActiveTab().id;
				if(statTabActiveId=="PCPHistoryQueryStatGraphPanel_Id"){
					loadAndInitHistoryQueryCommStatusStat(true);
				}else if(statTabActiveId=="PCPHistoryQueryRunStatusStatGraphPanel_Id"){
					loadAndInitHistoryQueryRunStatusStat(true);
				}else if(statTabActiveId=="PCPHistoryQueryDeviceTypeStatGraphPanel_Id"){
					loadAndInitHistoryQueryDeviceTypeStat(true);
				}
				if(isNotVal(realtimeTurnToHisyorySign)){//如果是实时跳转
					Ext.getCmp("realtimeTurnToHisyorySign_Id").setValue('');
				}else{
					Ext.getCmp('HistoryQueryPCPDeviceListComb_Id').setValue('');
					Ext.getCmp('HistoryQueryPCPDeviceListComb_Id').setRawValue('');
				}
				refreshHistoryDeviceListDataByPage(parseInt(Ext.getCmp("selectedPCPDeviceId_global").getValue()),1,Ext.getCmp("PCPHistoryQueryDeviceListGridPanel_Id"),'AP.store.historyQuery.PCPHistoryQueryWellListStore');
			}
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
		var tabPanel = Ext.getCmp("AlarmQueryTabPanel");
		var activeId = tabPanel.getActiveTab().id;
		if(selectedDeviceType_global==0 && activeId!='RPCAlarmQueryPanel_Id'){
			tabPanel.setActiveTab("RPCAlarmQueryPanel_Id");
			tabChange=true;
		}else if(selectedDeviceType_global==1 && activeId!='PCPAlarmQueryPanel_Id'){
			tabPanel.setActiveTab("PCPAlarmQueryPanel_Id");
			tabChange=true;
		}
		if(!tabChange){
			if(activeId=="RPCAlarmQueryPanel_Id"){
				var secondTabPanel = Ext.getCmp("RPCAlarmQueryTabPanel");
				var secondActiveId = secondTabPanel.getActiveTab().id;
				if(secondActiveId=="RPCFESDiagramResultAlarmInfoPanel_Id"){
					Ext.getCmp("RPCFESDiagramResultAlarmOverviewSelectRow_Id").setValue(0);
					Ext.getCmp("RPCFESDiagramResultAlarmDeviceListComb_Id").setValue('');
					Ext.getCmp("RPCFESDiagramResultAlarmDeviceListComb_Id").setRawValue('');
					Ext.getCmp("RPCFESDiagramResultAlarmLevelComb_Id").setValue('');
					Ext.getCmp("RPCFESDiagramResultAlarmLevelComb_Id").setRawValue('');
					var gridPanel = Ext.getCmp("RPCFESDiagramResultAlarmOverviewGridPanel_Id");
					if (isNotVal(gridPanel)) {
						gridPanel.getStore().loadPage(1);
					}else{
						Ext.create('AP.store.alarmQuery.RPCFESDiagramResultAlarmOverviewStore');
					}
				}else if(secondActiveId=="RPCRunStatusAlarmInfoPanel_Id"){
					Ext.getCmp("RPCRunStatusAlarmOverviewSelectRow_Id").setValue(0);
					Ext.getCmp("RPCRunStatusAlarmDeviceListComb_Id").setValue('');
					Ext.getCmp("RPCRunStatusAlarmDeviceListComb_Id").setRawValue('');
					Ext.getCmp("RPCRunStatusAlarmLevelComb_Id").setValue('');
					Ext.getCmp("RPCRunStatusAlarmLevelComb_Id").setRawValue('');
					var gridPanel = Ext.getCmp("RPCRunStatusAlarmOverviewGridPanel_Id");
					if (isNotVal(gridPanel)) {
						gridPanel.getStore().loadPage(1);
					}else{
						Ext.create('AP.store.alarmQuery.RPCRunStatusAlarmOverviewStore');
					}
				}else if(secondActiveId=="RPCCommunicationAlarmInfoPanel_Id"){
					Ext.getCmp("RPCCommunicationAlarmOverviewSelectRow_Id").setValue(0);
					Ext.getCmp("RPCCommunicationAlarmDeviceListComb_Id").setValue('');
					Ext.getCmp("RPCCommunicationAlarmDeviceListComb_Id").setRawValue('');
					Ext.getCmp("RPCCommunicationAlarmLevelComb_Id").setValue('');
					Ext.getCmp("RPCCommunicationAlarmLevelComb_Id").setRawValue('');
					var gridPanel = Ext.getCmp("RPCCommunicationAlarmOverviewGridPanel_Id");
					if (isNotVal(gridPanel)) {
						gridPanel.getStore().loadPage(1);
					}else{
						Ext.create('AP.store.alarmQuery.RPCCommunicationAlarmOverviewStore');
					}
				}else if(secondActiveId=="RPCNumericValueAlarmInfoPanel_Id"){
					Ext.getCmp("RPCNumericValueAlarmOverviewSelectRow_Id").setValue(0);
					Ext.getCmp("RPCNumericValueAlarmDeviceListComb_Id").setValue('');
					Ext.getCmp("RPCNumericValueAlarmDeviceListComb_Id").setRawValue('');
					Ext.getCmp("RPCNumericValueAlarmLevelComb_Id").setValue('');
					Ext.getCmp("RPCNumericValueAlarmLevelComb_Id").setRawValue('');
					var gridPanel = Ext.getCmp("RPCNumericValueAlarmOverviewGridPanel_Id");
					if (isNotVal(gridPanel)) {
						gridPanel.getStore().loadPage(1);
					}else{
						Ext.create('AP.store.alarmQuery.RPCNumericValueAlarmOverviewStore');
					}
				}else if(secondActiveId=="RPCEnumValueAlarmInfoPanel_Id"){
					Ext.getCmp("RPCEnumValueAlarmOverviewSelectRow_Id").setValue(0);
					Ext.getCmp("RPCEnumValueAlarmDeviceListComb_Id").setValue('');
					Ext.getCmp("RPCEnumValueAlarmDeviceListComb_Id").setRawValue('');
					Ext.getCmp("RPCEnumValueAlarmLevelComb_Id").setValue('');
					Ext.getCmp("RPCEnumValueAlarmLevelComb_Id").setRawValue('');
					var gridPanel = Ext.getCmp("RPCEnumValueAlarmOverviewGridPanel_Id");
					if (isNotVal(gridPanel)) {
						gridPanel.getStore().loadPage(1);
					}else{
						Ext.create('AP.store.alarmQuery.RPCEnumValueAlarmOverviewStore');
					}
				}else if(secondActiveId=="RPCSwitchingValueAlarmInfoPanel_Id"){
					Ext.getCmp("RPCSwitchingValueAlarmOverviewSelectRow_Id").setValue(0);
					Ext.getCmp("RPCSwitchingValueAlarmDeviceListComb_Id").setValue('');
					Ext.getCmp("RPCSwitchingValueAlarmDeviceListComb_Id").setRawValue('');
					Ext.getCmp("RPCSwitchingValueAlarmLevelComb_Id").setValue('');
					Ext.getCmp("RPCSwitchingValueAlarmLevelComb_Id").setRawValue('');
					var gridPanel = Ext.getCmp("RPCSwitchingValueAlarmOverviewGridPanel_Id");
					if (isNotVal(gridPanel)) {
						gridPanel.getStore().loadPage(1);
					}else{
						Ext.create('AP.store.alarmQuery.RPCSwitchingValueAlarmOverviewStore');
					}
				}
			}else if(activeId=="PCPAlarmQueryPanel_Id"){
				var secondTabPanel = Ext.getCmp("PCPAlarmQueryTabPanel");
				var secondActiveId = secondTabPanel.getActiveTab().id;
				if(secondActiveId=="PCPCommunicationAlarmInfoPanel_Id"){
					Ext.getCmp("PCPCommunicationAlarmOverviewSelectRow_Id").setValue(0);
					Ext.getCmp("PCPCommunicationAlarmDeviceListComb_Id").setValue('');
					Ext.getCmp("PCPCommunicationAlarmDeviceListComb_Id").setRawValue('');
					Ext.getCmp("PCPCommunicationAlarmLevelComb_Id").setValue('');
					Ext.getCmp("PCPCommunicationAlarmLevelComb_Id").setRawValue('');
					var gridPanel = Ext.getCmp("PCPCommunicationAlarmOverviewGridPanel_Id");
					if (isNotVal(gridPanel)) {
						gridPanel.getStore().loadPage(1);
					}else{
						Ext.create('AP.store.alarmQuery.PCPCommunicationAlarmOverviewStore');
					}
				}else if(secondActiveId=="PCPRunStatusAlarmInfoPanel_Id"){
					Ext.getCmp("PCPRunStatusAlarmOverviewSelectRow_Id").setValue(0);
					Ext.getCmp("PCPRunStatusAlarmDeviceListComb_Id").setValue('');
					Ext.getCmp("PCPRunStatusAlarmDeviceListComb_Id").setRawValue('');
					Ext.getCmp("PCPRunStatusAlarmLevelComb_Id").setValue('');
					Ext.getCmp("PCPRunStatusAlarmLevelComb_Id").setRawValue('');
					var gridPanel = Ext.getCmp("PCPRunStatusAlarmOverviewGridPanel_Id");
					if (isNotVal(gridPanel)) {
						gridPanel.getStore().loadPage(1);
					}else{
						Ext.create('AP.store.alarmQuery.PCPRunStatusAlarmOverviewStore');
					}
				}else if(secondActiveId=="PCPNumericValueAlarmInfoPanel_Id"){
					Ext.getCmp("PCPNumericValueAlarmOverviewSelectRow_Id").setValue(0);
					Ext.getCmp("PCPNumericValueAlarmDeviceListComb_Id").setValue('');
					Ext.getCmp("PCPNumericValueAlarmDeviceListComb_Id").setRawValue('');
					Ext.getCmp("PCPNumericValueAlarmLevelComb_Id").setValue('');
					Ext.getCmp("PCPNumericValueAlarmLevelComb_Id").setRawValue('');
					var gridPanel = Ext.getCmp("PCPNumericValueAlarmOverviewGridPanel_Id");
					if (isNotVal(gridPanel)) {
						gridPanel.getStore().loadPage(1);
					}else{
						Ext.create('AP.store.alarmQuery.PCPNumericValueAlarmOverviewStore');
					}
				}else if(secondActiveId=="PCPEnumValueAlarmInfoPanel_Id"){
					Ext.getCmp("PCPEnumValueAlarmOverviewSelectRow_Id").setValue(0);
					Ext.getCmp("PCPEnumValueAlarmDeviceListComb_Id").setValue('');
					Ext.getCmp("PCPEnumValueAlarmDeviceListComb_Id").setRawValue('');
					Ext.getCmp("PCPEnumValueAlarmLevelComb_Id").setValue('');
					Ext.getCmp("PCPEnumValueAlarmLevelComb_Id").setRawValue('');
					var gridPanel = Ext.getCmp("PCPEnumValueAlarmOverviewGridPanel_Id");
					if (isNotVal(gridPanel)) {
						gridPanel.getStore().loadPage(1);
					}else{
						Ext.create('AP.store.alarmQuery.PCPEnumValueAlarmOverviewStore');
					}
				}else if(secondActiveId=="PCPSwitchingValueAlarmInfoPanel_Id"){
					Ext.getCmp("PCPSwitchingValueAlarmOverviewSelectRow_Id").setValue(0);
					Ext.getCmp("PCPSwitchingValueAlarmDeviceListComb_Id").setValue('');
					Ext.getCmp("PCPSwitchingValueAlarmDeviceListComb_Id").setRawValue('');
					Ext.getCmp("PCPSwitchingValueAlarmLevelComb_Id").setValue('');
					Ext.getCmp("PCPSwitchingValueAlarmLevelComb_Id").setRawValue('');
					var gridPanel = Ext.getCmp("PCPSwitchingValueAlarmOverviewGridPanel_Id");
					if (isNotVal(gridPanel)) {
						gridPanel.getStore().loadPage(1);
					}else{
						Ext.create('AP.store.alarmQuery.PCPSwitchingValueAlarmOverviewStore');
					}
				}
			}
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