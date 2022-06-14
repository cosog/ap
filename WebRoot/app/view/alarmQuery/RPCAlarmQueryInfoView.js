Ext.define("AP.view.alarmQuery.RPCAlarmQueryInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.rpcAlarmQueryInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var RPCFESDiagramResultAlarmInfoView = Ext.create('AP.view.alarmQuery.RPCFESDiagramResultAlarmInfoView');
        var RPCRunStatusAlarmInfoView = Ext.create('AP.view.alarmQuery.RPCRunStatusAlarmInfoView');
        var CommunicationAlarmInfoView = Ext.create('AP.view.alarmQuery.RPCCommunicationAlarmInfoView');
        var NumericValueAlarmInfoView = Ext.create('AP.view.alarmQuery.RPCNumericValueAlarmInfoView');
        var EnumValueAlarmInfoView = Ext.create('AP.view.alarmQuery.RPCEnumValueAlarmInfoView');
        var SwitchingValueAlarmInfoView = Ext.create('AP.view.alarmQuery.RPCSwitchingValueAlarmInfoView');
        Ext.apply(me, {
        	items: [{
        		xtype: 'tabpanel',
        		id:"RPCAlarmQueryTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'left',
        		items: [{
    				title: '<div style="color:#000000;font-size:11px;font-family:SimSun">工况诊断报警</div>',
    				id:'RPCFESDiagramResultAlarmInfoPanel_Id',
    				items: [RPCFESDiagramResultAlarmInfoView],
    				layout: "fit",
    				border: false
    			},{
    				title: '<div style="color:#000000;font-size:11px;font-family:SimSun">运行状态报警</div>',
    				id:'RPCRunStatusAlarmInfoPanel_Id',
    				items: [RPCRunStatusAlarmInfoView],
    				layout: "fit",
    				border: false
    			},{
        			title: '<div style="color:#000000;font-size:11px;font-family:SimSun">通信状态报警</div>',
        			id:'RPCCommunicationAlarmInfoPanel_Id',
        			items: [CommunicationAlarmInfoView],
        			layout: "fit",
        			border: false
        		},{
        			title: '<div style="color:#000000;font-size:11px;font-family:SimSun">数据量报警</div>',
        			id:'RPCNumericValueAlarmInfoPanel_Id',
        			items: [NumericValueAlarmInfoView],
        			layout: "fit",
        			border: false
        		},{
        			title: '<div style="color:#000000;font-size:11px;font-family:SimSun">枚举量报警</div>',
        			id:'RPCEnumValueAlarmInfoPanel_Id',
        			items: [EnumValueAlarmInfoView],
        			layout: "fit",
        			border: false
        		},{
        			title: '<div style="color:#000000;font-size:11px;font-family:SimSun">开关量报警</div>',
        			id:'RPCSwitchingValueAlarmInfoPanel_Id',
        			items: [SwitchingValueAlarmInfoView],
        			layout: "fit",
        			border: false
        		}],
        		listeners: {
        			tabchange: function (tabPanel, newCard,oldCard, obj) {
        				Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
        				if(newCard.id=="RPCFESDiagramResultAlarmInfoPanel_Id"){
        					var gridPanel = Ext.getCmp("RPCFESDiagramResultAlarmOverviewGridPanel_Id");
        					if (isNotVal(gridPanel)) {
        						gridPanel.getStore().load();
        					}else{
        						Ext.create('AP.store.alarmQuery.RPCFESDiagramResultAlarmOverviewStore');
        					}
        				}else if(newCard.id=="RPCRunStatusAlarmInfoPanel_Id"){
        					var gridPanel = Ext.getCmp("RPCRunStatusAlarmOverviewGridPanel_Id");
        					if (isNotVal(gridPanel)) {
        						gridPanel.getStore().load();
        					}else{
        						Ext.create('AP.store.alarmQuery.RPCRunStatusAlarmOverviewStore');
        					}
        				}else if(newCard.id=="RPCCommunicationAlarmInfoPanel_Id"){
        					var gridPanel = Ext.getCmp("RPCCommunicationAlarmOverviewGridPanel_Id");
        					if (isNotVal(gridPanel)) {
        						gridPanel.getStore().load();
        					}else{
        						Ext.create('AP.store.alarmQuery.RPCCommunicationAlarmOverviewStore');
        					}
        				}else if(newCard.id=="RPCNumericValueAlarmInfoPanel_Id"){
        					var gridPanel = Ext.getCmp("RPCNumericValueAlarmOverviewGridPanel_Id");
        					if (isNotVal(gridPanel)) {
        						gridPanel.getStore().load();
        					}else{
        						Ext.create('AP.store.alarmQuery.RPCNumericValueAlarmOverviewStore');
        					}
        				}else if(newCard.id=="RPCEnumValueAlarmInfoPanel_Id"){
        					var gridPanel = Ext.getCmp("RPCEnumValueAlarmOverviewGridPanel_Id");
        					if (isNotVal(gridPanel)) {
        						gridPanel.getStore().load();
        					}else{
        						Ext.create('AP.store.alarmQuery.RPCEnumValueAlarmOverviewStore');
        					}
        				}else if(newCard.id=="RPCSwitchingValueAlarmInfoPanel_Id"){
        					var gridPanel = Ext.getCmp("RPCSwitchingValueAlarmOverviewGridPanel_Id");
        					if (isNotVal(gridPanel)) {
        						gridPanel.getStore().load();
        					}else{
        						Ext.create('AP.store.alarmQuery.RPCSwitchingValueAlarmOverviewStore');
        					}
        				}
        			}
        		}
            	}]
        });
        me.callParent(arguments);
    }
});