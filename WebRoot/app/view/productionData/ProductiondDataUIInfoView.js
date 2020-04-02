page = 1;
gtCount = 6; // 功图列表默认加载个数
Ext.define("AP.view.productionData.ProductiondDataUIInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.productiondDataUIInfoView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var PumpingUnitProductionDataPanel = Ext.create('AP.view.productionData.PumpingUnitProductionDataPanel');
        Ext.apply(me, {
            items: [{
        		xtype: 'tabpanel',
        		id:"ProductionWellProductionPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'bottom',
        		items: [{
        				title: cosog.string.pumpUnit,
        				layout: "fit",
        				id:'PumpingUnitProductionDataPanel',
        				border: false,
        				items: [PumpingUnitProductionDataPanel]
        			},{
        				title: cosog.string.screwPump,
        				id:'ScrewPumpProductionDataPanel',
        				layout: "fit",
        				hidden: pcpHidden,
        				border: false
        			}],
        			listeners: {
        				tabchange: function (tabPanel, newCard,oldCard, obj) {
        					Ext.getCmp("bottomTab_Id").setValue(newCard.id); //
        					if(newCard.id=="PumpingUnitProductionDataPanel"){
        						CreateAndLoadWellProTable();
        					}else if(newCard.id=="ScrewPumpProductionDataPanel"){
        						var screwPumpProductionDataJhCombo_Id=Ext.getCmp("screwPumpProductionDataJhCombo_Id");
        						if(screwPumpProductionDataJhCombo_Id==undefined){
        							var ScrewPumpProductionDataPanel = Ext.create('AP.view.productionData.ScrewPumpProductionDataPanel');
        							Ext.getCmp("ScrewPumpProductionDataPanel").add(ScrewPumpProductionDataPanel);
        						}else{
        							CreateAndLoadScrewPumpProTable();
        						}
        					}
        				},
        				delay: 500
        			}
            	}]
        });
        me.callParent(arguments);
    }
});