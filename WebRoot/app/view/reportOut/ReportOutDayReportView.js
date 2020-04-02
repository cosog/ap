Ext.define("AP.view.reportOut.ReportOutDayReportView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.reportOutDayReportView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var reportPumpingUnitPanel = Ext.create('AP.view.reportOut.ReportPumpingUnitPanel');
        var ScrewPumpDailyReportPanel = Ext.create('AP.view.reportOut.ScrewPumpDailyReportPanel');
        Ext.apply(me, {
            items: [{
                xtype: 'tabpanel',
                id:'ProductionWellDailyReportPanel_Id',
                activeTab: 0,
                border: false,
                tabPosition: 'bottom',
                items: [{
                    title: cosog.string.pumpUnit,
                    id: 'ReportPumpingUnitDayReport',
                    layout: "fit",
                    border: false,
                    items: [reportPumpingUnitPanel]
                }, {
                    title: cosog.string.screwPump,
                    id:'screwPumpDailyReportPanel_Id',
                    layout: "fit",
                    border: false,
                    hidden: pcpHidden,
                    items:[ScrewPumpDailyReportPanel]
                }],
                listeners: {
                    tabchange: function (tabPanel, newCard,oldCard, obj) {
                    	if(newCard.id=="ReportPumpingUnitDayReport"){
                    		CreateDiagnosisDailyReportTable();
                    	}else if(newCard.id=="screwPumpDailyReportPanel_Id"){
                    		CreateScrewPumpDailyReportTable();
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