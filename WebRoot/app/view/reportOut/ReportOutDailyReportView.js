Ext.define("AP.view.reportOut.ReportOutDailyReportView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.reportOutDayReportView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var RPCDailyReportPanel = Ext.create('AP.view.reportOut.RPCDailyReportPanel');
        var PCPDailyReportPanel = Ext.create('AP.view.reportOut.PCPDailyReportPanel');
        Ext.apply(me, {
            items: [{
                xtype: 'tabpanel',
                id:'ProductionWellDailyReportPanel_Id',
                activeTab: 0,
                border: false,
                tabPosition: 'bottom',
                items: [{
                    title: cosog.string.pumpUnit,
                    id: 'RPCDailyReportPanel_Id',
                    layout: "fit",
                    border: false,
                    items: [RPCDailyReportPanel]
                }, {
                    title: cosog.string.screwPump,
                    id:'PCPDailyReportPanel_Id',
                    layout: "fit",
                    border: false,
                    hidden: pcpHidden||onlyMonitor,
                    items:[PCPDailyReportPanel]
                }],
                listeners: {
                    tabchange: function (tabPanel, newCard,oldCard, obj) {
                    	if(newCard.id=="RPCDailyReportPanel_Id"){
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
                    	}else if(newCard.id=="PCPDailyReportPanel_Id"){
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
                        Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
                    },
                    delay: 500
                }
       }]
        });
        me.callParent(arguments);
    }
});