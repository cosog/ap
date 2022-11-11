Ext.define("AP.view.alarmQuery.PCPAlarmQueryInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.pcpAlarmQueryInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var PCPRunStatusAlarmInfoView = Ext.create('AP.view.alarmQuery.PCPRunStatusAlarmInfoView');
        var CommunicationAlarmInfoView = Ext.create('AP.view.alarmQuery.PCPCommunicationAlarmInfoView');
        var NumericValueAlarmInfoView = Ext.create('AP.view.alarmQuery.PCPNumericValueAlarmInfoView');
        var EnumValueAlarmInfoView = Ext.create('AP.view.alarmQuery.PCPEnumValueAlarmInfoView');
        var SwitchingValueAlarmInfoView = Ext.create('AP.view.alarmQuery.PCPSwitchingValueAlarmInfoView');
        Ext.apply(me, {
        	items: [{
        		xtype: 'tabpanel',
        		id:"PCPAlarmQueryTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'left',
        		items: [{
    				title: '<div style="color:#000000;font-size:11px;font-family:SimSun">运行状态报警</div>',
    				id:'PCPRunStatusAlarmInfoPanel_Id',
    				items: [PCPRunStatusAlarmInfoView],
    				layout: "fit",
    				border: false
    			},{
        				title: '<div style="color:#000000;font-size:11px;font-family:SimSun">通信状态报警</div>',
        				id:'PCPCommunicationAlarmInfoPanel_Id',
        				items: [CommunicationAlarmInfoView],
        				layout: "fit",
        				border: false
        			},{
        				title: '<div style="color:#000000;font-size:11px;font-family:SimSun">数值量报警</div>',
        				id:'PCPNumericValueAlarmInfoPanel_Id',
        				items: [NumericValueAlarmInfoView],
        				layout: "fit",
        				border: false
        			},{
        				title: '<div style="color:#000000;font-size:11px;font-family:SimSun">枚举量报警</div>',
        				id:'PCPEnumValueAlarmInfoPanel_Id',
        				items: [EnumValueAlarmInfoView],
        				layout: "fit",
        				border: false
        			},{
        				title: '<div style="color:#000000;font-size:11px;font-family:SimSun">开关量报警</div>',
        				id:'PCPSwitchingValueAlarmInfoPanel_Id',
        				items: [SwitchingValueAlarmInfoView],
        				layout: "fit",
        				border: false
        			}],
        			listeners: {
        				tabchange: function (tabPanel, newCard,oldCard, obj) {
        					Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
        					if(newCard.id=="PCPRunStatusAlarmInfoPanel_Id"){
        						var gridPanel = Ext.getCmp("PCPRunStatusAlarmOverviewGridPanel_Id");
        						if (isNotVal(gridPanel)) {
        							gridPanel.getStore().loadPage(1);
        						}else{
        							Ext.create('AP.store.alarmQuery.PCPRunStatusAlarmOverviewStore');
        						}
        					}else if(newCard.id=="PCPCommunicationAlarmInfoPanel_Id"){
        						var gridPanel = Ext.getCmp("PCPCommunicationAlarmOverviewGridPanel_Id");
        						if (isNotVal(gridPanel)) {
        							gridPanel.getStore().loadPage(1);
        						}else{
        							Ext.create('AP.store.alarmQuery.PCPCommunicationAlarmOverviewStore');
        						}
        					}else if(newCard.id=="PCPNumericValueAlarmInfoPanel_Id"){
        						var gridPanel = Ext.getCmp("PCPNumericValueAlarmOverviewGridPanel_Id");
        						if (isNotVal(gridPanel)) {
        							gridPanel.getStore().loadPage(1);
        						}else{
        							Ext.create('AP.store.alarmQuery.PCPNumericValueAlarmOverviewStore');
        						}
        					}else if(newCard.id=="PCPEnumValueAlarmInfoPanel_Id"){
        						var gridPanel = Ext.getCmp("PCPEnumValueAlarmOverviewGridPanel_Id");
        						if (isNotVal(gridPanel)) {
        							gridPanel.getStore().loadPage(1);
        						}else{
        							Ext.create('AP.store.alarmQuery.PCPEnumValueAlarmOverviewStore');
        						}
        					}else if(newCard.id=="PCPSwitchingValueAlarmInfoPanel_Id"){
        						var gridPanel = Ext.getCmp("PCPSwitchingValueAlarmOverviewGridPanel_Id");
        						if (isNotVal(gridPanel)) {
        							gridPanel.getStore().loadPage(1);
        						}else{
        							Ext.create('AP.store.alarmQuery.PCPSwitchingValueAlarmOverviewStore');
        						}
        					}
        				}
        			}
            	}]
        });
        me.callParent(arguments);
    }
});