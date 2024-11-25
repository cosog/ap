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
    				title: loginUserLanguageResource.singleDeviceReport,
    				id:'SingleWellDailyReportTabPanel_Id',
    				items: [SingleWellDailyReportPanel],
    				iconCls: 'check3',
    				layout: "fit",
    				border: false
    			},{
    				title: loginUserLanguageResource.areaReport,
    				id:'ProductionDailyReportTabPanel_Id',
    				items: [ProductionDailyReportPanel],
    				layout: "fit",
    				border: false
    			}],
        		listeners: {
        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        				oldCard.setIconCls(null);
        				newCard.setIconCls('check3');
        			},
        			tabchange: function (tabPanel, newCard,oldCard, obj) {
        				Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
        				if(newCard.id=="SingleWellDailyReportTabPanel_Id"){
        					Ext.getCmp('SingleWellDailyReportPanelWellListCombo_Id').setRawValue('');
        					Ext.getCmp('SingleWellDailyReportPanelWellListCombo_Id').setValue('');
        					var gridPanel = Ext.getCmp("SingleWellDailyReportGridPanel_Id");
        					if (isNotVal(gridPanel)) {
        						gridPanel.getStore().load();
        					}else{
        						Ext.create('AP.store.reportOut.SingleWellDailyReportWellListStore');
        					}
        				}else if(newCard.id=="ProductionDailyReportTabPanel_Id"){
        					Ext.getCmp('ProductionDailyReportPanelWellListCombo_Id').setRawValue('');
        					Ext.getCmp('ProductionDailyReportPanelWellListCombo_Id').setValue('');
        					var gridPanel = Ext.getCmp("ProductionDailyReportGridPanel_Id");
        	    			if (isNotVal(gridPanel)) {
        	    				gridPanel.getStore().load();
        	    			}else{
        	    				Ext.create('AP.store.reportOut.ProductionDailyReportInstanceListStore');
        	    			}
        				}
        			}
        		}
            }]
        });
        me.callParent(arguments);
    }
});