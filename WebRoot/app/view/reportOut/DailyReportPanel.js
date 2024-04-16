Ext.define("AP.view.reportOut.DailyReportPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.DailyReportPanel',
    layout: 'fit',
    id: 'DailyReportPanel_View',
    border: false,
    initComponent: function () {
    	var me = this;
    	var SingleWellDailyReportPanel = Ext.create('AP.view.reportOut.SingleWellDailyReportPanel');
    	var ProductionDailyReportPanel = Ext.create('AP.view.reportOut.ProductionDailyReportPanel');
    	Ext.apply(me, {
        	items: [{
        		xtype: 'tabpanel',
        		id:"DailyReportTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'left',
        		items: [{
    				title: '<div style="color:#000000;font-size:11px;font-family:SimSun">单井报表</div>',
    				id:'SingleWellDailyReportTabPanel_Id',
    				items: [SingleWellDailyReportPanel],
    				layout: "fit",
    				border: false
    			},{
    				title: '<div style="color:#000000;font-size:11px;font-family:SimSun">区域报表</div>',
    				id:'ProductionDailyReportTabPanel_Id',
    				items: [ProductionDailyReportPanel],
    				layout: "fit",
    				border: false
    			}],
        		listeners: {
        			tabchange: function (tabPanel, newCard,oldCard, obj) {
//        				Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
//        				if(newCard.id=="SingleWellDailyReportTabPanel_Id"){
//        					Ext.getCmp('SingleWellDailyReportPanelWellListCombo_Id').setRawValue('');
//        					Ext.getCmp('SingleWellDailyReportPanelWellListCombo_Id').setValue('');
//        					var gridPanel = Ext.getCmp("SingleWellDailyReportGridPanel_Id");
//        					if (isNotVal(gridPanel)) {
//        						gridPanel.getStore().load();
//        					}else{
//        						Ext.create('AP.store.reportOut.SingleWellDailyReportWellListStore');
//        					}
//        				}else if(newCard.id=="ProductionDailyReportTabPanel_Id"){
//        					Ext.getCmp('ProductionDailyReportPanelWellListCombo_Id').setRawValue('');
//        					Ext.getCmp('ProductionDailyReportPanelWellListCombo_Id').setValue('');
//        					var gridPanel = Ext.getCmp("ProductionDailyReportGridPanel_Id");
//        	    			if (isNotVal(gridPanel)) {
//        	    				gridPanel.getStore().load();
//        	    			}else{
//        	    				Ext.create('AP.store.reportOut.ProductionDailyReportInstanceListStore');
//        	    			}
//        				}
        			}
        		}
            }]
        });
        me.callParent(arguments);
    }
});