Ext.define("AP.view.alarmQuery.AlarmQueryInfoPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.alarmQueryInfoPanel', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var FESDiagramResultAlarmInfoPanel = Ext.create('AP.view.alarmQuery.FESDiagramResultAlarmInfoPanel');
        var RunStatusAlarmInfoPanel = Ext.create('AP.view.alarmQuery.RunStatusAlarmInfoPanel');
        var CommunicationAlarmInfoPanel = Ext.create('AP.view.alarmQuery.CommunicationAlarmInfoPanel');
        var NumericValueAlarmInfoPanel = Ext.create('AP.view.alarmQuery.NumericValueAlarmInfoPanel');
        var EnumValueAlarmInfoPanel = Ext.create('AP.view.alarmQuery.EnumValueAlarmInfoPanel');
        var SwitchingValueAlarmInfoPanel = Ext.create('AP.view.alarmQuery.SwitchingValueAlarmInfoPanel');
        Ext.apply(me, {
        	items: [{
        		xtype: 'tabpanel',
        		id:"AlarmQuerySecondTabPanel",
        		activeTab: onlyMonitor?1:0,
        		border: false,
//        		tabPosition: 'left',
        		tabPosition: 'top',
        		items: [{
    				title: '<div style="color:#000000;font-size:11px;font-family:SimSun">工况诊断报警</div>',
    				id:'FESDiagramResultAlarmInfoTabPanel_Id',
    				hidden:onlyMonitor,
    				items: [FESDiagramResultAlarmInfoPanel],
    				layout: "fit",
    				border: false
    			},{
    				title: '<div style="color:#000000;font-size:11px;font-family:SimSun">运行状态报警</div>',
    				id:'RunStatusAlarmInfoTabPanel_Id',
    				items: [RunStatusAlarmInfoPanel],
    				layout: "fit",
    				border: false
    			},{
        			title: '<div style="color:#000000;font-size:11px;font-family:SimSun">通信状态报警</div>',
        			id:'CommunicationAlarmInfoTabPanel_Id',
        			items: [CommunicationAlarmInfoPanel],
        			layout: "fit",
        			border: false
        		},{
        			title: '<div style="color:#000000;font-size:11px;font-family:SimSun">数据量报警</div>',
        			id:'NumericValueAlarmInfoTabPanel_Id',
        			items: [NumericValueAlarmInfoPanel],
        			layout: "fit",
        			border: false
        		},{
        			title: '<div style="color:#000000;font-size:11px;font-family:SimSun">枚举量报警</div>',
        			id:'EnumValueAlarmInfoTabPanel_Id',
        			items: [EnumValueAlarmInfoPanel],
        			layout: "fit",
        			border: false
        		},{
        			title: '<div style="color:#000000;font-size:11px;font-family:SimSun">开关量报警</div>',
        			id:'SwitchingValueAlarmInfoTabPanel_Id',
        			items: [SwitchingValueAlarmInfoPanel],
        			layout: "fit",
        			border: false
        		}],
        		listeners: {
        			tabchange: function (tabPanel, newCard,oldCard, obj) {
//        				if(newCard.id=="FESDiagramResultAlarmInfoTabPanel_Id"){
//        					var gridPanel = Ext.getCmp("FESDiagramResultAlarmOverviewGridPanel_Id");
//        					if (isNotVal(gridPanel)) {
//        						gridPanel.getStore().loadPage(1);
//        					}else{
//        						Ext.create('AP.store.alarmQuery.FESDiagramResultAlarmOverviewStore');
//        					}
//        				}else if(newCard.id=="RunStatusAlarmInfoTabPanel_Id"){
//        					var gridPanel = Ext.getCmp("RunStatusAlarmOverviewGridPanel_Id");
//        					if (isNotVal(gridPanel)) {
//        						gridPanel.getStore().loadPage(1);
//        					}else{
//        						Ext.create('AP.store.alarmQuery.RunStatusAlarmOverviewStore');
//        					}
//        				}else if(newCard.id=="CommunicationAlarmInfoTabPanel_Id"){
//        					var gridPanel = Ext.getCmp("CommunicationAlarmOverviewGridPanel_Id");
//        					if (isNotVal(gridPanel)) {
//        						gridPanel.getStore().loadPage(1);
//        					}else{
//        						Ext.create('AP.store.alarmQuery.CommunicationAlarmOverviewStore');
//        					}
//        				}else if(newCard.id=="NumericValueAlarmInfoTabPanel_Id"){
//        					var gridPanel = Ext.getCmp("NumericValueAlarmOverviewGridPanel_Id");
//        					if (isNotVal(gridPanel)) {
//        						gridPanel.getStore().loadPage(1);
//        					}else{
//        						Ext.create('AP.store.alarmQuery.NumericValueAlarmOverviewStore');
//        					}
//        				}else if(newCard.id=="EnumValueAlarmInfoTabPanel_Id"){
//        					var gridPanel = Ext.getCmp("EnumValueAlarmOverviewGridPanel_Id");
//        					if (isNotVal(gridPanel)) {
//        						gridPanel.getStore().loadPage(1);
//        					}else{
//        						Ext.create('AP.store.alarmQuery.EnumValueAlarmOverviewStore');
//        					}
//        				}else if(newCard.id=="SwitchingValueAlarmInfoTabPanel_Id"){
//        					var gridPanel = Ext.getCmp("SwitchingValueAlarmOverviewGridPanel_Id");
//        					if (isNotVal(gridPanel)) {
//        						gridPanel.getStore().loadPage(1);
//        					}else{
//        						Ext.create('AP.store.alarmQuery.SwitchingValueAlarmOverviewStore');
//        					}
//        				}
        			}
        		}
            	}]
        });
        me.callParent(arguments);
    }
});