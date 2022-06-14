Ext.define("AP.view.reportOut.ReportOutDailyReportView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.reportOutDayReportView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var RPMDailyReportPanel = Ext.create('AP.view.reportOut.RPMDailyReportPanel');
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
                    items: [RPMDailyReportPanel]
                }, {
                    title: cosog.string.screwPump,
                    id:'PCPDailyReportPanel_Id',
                    layout: "fit",
                    border: false,
                    hidden: pcpHidden,
                    items:[PCPDailyReportPanel]
                }],
                listeners: {
                    tabchange: function (tabPanel, newCard,oldCard, obj) {
                    	if(newCard.id=="RPCDailyReportPanel_Id"){
                    		var gridPanel = Ext.getCmp("RPCDailyReportGridPanel_Id");
                			if (isNotVal(gridPanel)) {
                				gridPanel.getStore().load();
                			}else{
                				Ext.create('AP.store.reportOut.RPCDailyReportWellListStore');
                			}
                    	}else if(newCard.id=="PCPDailyReportPanel_Id"){
                    		var gridPanel = Ext.getCmp("PPCDailyReportGridPanel_Id");
                			if (isNotVal(gridPanel)) {
                				gridPanel.getStore().load();
                			}else{
                				Ext.create('AP.store.reportOut.PCPDailyReportWellListStore');
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