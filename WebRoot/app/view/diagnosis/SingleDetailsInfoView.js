Ext.define("AP.view.diagnosis.SingleDetailsInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.DiagnosisSingleDetailsInfoView', //别名
    layout: 'fit',
    iframe: true,
    border: false,
    referenceHolder: true,
    initComponent: function () {
    	var RPCSingleDetailsInfoView = Ext.create('AP.view.diagnosis.RPCSingleDetailsInfoView');
        var PCPSingleDetailsInfoView = Ext.create('AP.view.diagnosis.PCPSingleDetailsInfoView');
        Ext.apply(this, {
            items: [{
                xtype: 'tabpanel',
                id:"ProductionWellRealtimeAnalisisPanel",
                activeTab: 0,
                border: false,
                tabPosition: 'bottom',
                items: [{
                    title: cosog.string.pumpUnit,
                    id:'RPCSingleDetailsInfoPanel_Id',
                    layout: "fit",
                    border: false,
                    items: RPCSingleDetailsInfoView
                }, {
                    title: cosog.string.screwPump,
                    id:'PCPSingleDetailsInfoPanel_Id',
                    layout: "fit",
                    border: false,
                    items:PCPSingleDetailsInfoView
                }],
                listeners: {
                    tabchange: function (tabPanel, newCard, oldCard, obj) {
                    	if(newCard.id=="RPCSingleDetailsInfoPanel_Id"){
                    		loadFSDiagramAnalysisSingleStatData();
                    	}else if(newCard.id=="PCPSingleDetailsInfoPanel_Id"){
                    		loadPCPRPMAnalysisSingleStatData();
                    	}
                    }
                }
             }]
        });
        this.callParent(arguments);
    }
});