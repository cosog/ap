/*******************************************************************************
 * 全天评价视图
 *
 * @author zhao
 *
 */
var cycleGtPage = 1;
var totalGtPage = 1;

Ext.define("AP.view.diagnosisTotal.DiagnosisTotalInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.diagnosisTotalInfoView', // 定义别名
    layout: 'fit',
    iframe: true,
    border: false,
    referenceHolder: true,
    initComponent: function () {
        var PumpingDailyAnalysisView = Ext.create('AP.view.diagnosisTotal.PumpingDailyAnalysisView');
        var ScrewPumpDailyAnalysisView = Ext.create('AP.view.diagnosisTotal.ScrewPumpDailyAnalysisView');
        Ext.apply(this, {
            items: [{
                xtype: 'tabpanel',
                id:"ProductionWellDailyAnalisisPanel",
                activeTab: 0,
                border: false,
                tabPosition: 'bottom',
                items: [{
                    title: cosog.string.pumpUnit,
                    id:'pumpUnitDailyAnalysisPanel_Id',
                    layout: "fit",
                    border: false,
                    items: [PumpingDailyAnalysisView]
                }, {
                    title: cosog.string.screwPump,
                    id:'screwPumpDailyAnalysisPanel_Id',
                    layout: "fit",
                    border: false,
                    hidden: pcpHidden,
                    items:[ScrewPumpDailyAnalysisView]
                }],
                listeners: {
                    tabchange: function (tabPanel, newCard, oldCard, obj) {
                    	if(newCard.id=="pumpUnitDailyAnalysisPanel_Id"){
                    		loadTotalStatData();
                    	}else if(newCard.id=="screwPumpDailyAnalysisPanel_Id"){
                    		loadScrewPumpDailyStatData();
                    	}
                    }
                }
             }]
        });
        this.callParent(arguments);
    }

});