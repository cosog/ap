Ext.define("AP.view.reportOut.PCPDailyReportPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.PCPDailyReportPanel',
    layout: 'fit',
    id: 'PCPDailyReportPanel_View',
    border: false,
    initComponent: function () {
    	var me = this;
    	var PCPSingleWellDailyReportPanel = Ext.create('AP.view.reportOut.PCPSingleWellDailyReportPanel');
    	var PCPProductionDailyReportPanel = Ext.create('AP.view.reportOut.PCPProductionDailyReportPanel');
    	Ext.apply(me, {
        	items: [{
        		xtype: 'tabpanel',
        		id:"PCPDailyReportTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'left',
        		items: [{
    				title: '<div style="color:#000000;font-size:11px;font-family:SimSun">单井日报</div>',
    				id:'PCPSingleWellDailyReportTabPanel_Id',
    				items: [PCPSingleWellDailyReportPanel],
    				layout: "fit",
    				border: false
    			},{
    				title: '<div style="color:#000000;font-size:11px;font-family:SimSun">区域日报</div>',
    				id:'PCPProductionDailyReportTabPanel_Id',
    				items: [PCPProductionDailyReportPanel],
    				layout: "fit",
    				border: false
    			}],
        		listeners: {
        			tabchange: function (tabPanel, newCard,oldCard, obj) {
        				Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
        				if(newCard.id=="PCPSingleWellDailyReportTabPanel_Id"){
        					Ext.getCmp('PCPSingleWellDailyReportPanelWellListCombo_Id').setRawValue('');
        					Ext.getCmp('PCPSingleWellDailyReportPanelWellListCombo_Id').setValue('');
        					var gridPanel = Ext.getCmp("PCPSingleWellDailyReportGridPanel_Id");
        					if (isNotVal(gridPanel)) {
        						gridPanel.getStore().load();
        					}else{
        						Ext.create('AP.store.reportOut.PCPSingleWellDailyReportWellListStore');
        					}
        				}else if(newCard.id=="PCPProductionDailyReportTabPanel_Id"){
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
            }]
        });
        me.callParent(arguments);
    }
});