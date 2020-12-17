Ext.define("AP.view.acquisitionUnit.ScadaConfigInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.scadaConfigInfoView', //别名
    layout: 'fit',
    iframe: true,
    border: false,
    referenceHolder: true,
    initComponent: function () {
    	var AcquisitionUnitInfoView = Ext.create('AP.view.acquisitionUnit.AcquisitionUnitInfoView');
        var DriverConfigInfoView = Ext.create('AP.view.acquisitionUnit.DriverConfigInfoView');
        Ext.apply(this, {
            items: [{
                xtype: 'tabpanel',
                id:"ScadaConfigTabPanel_Id",
                activeTab: 0,
                border: false,
                tabPosition: 'bottom',
                items: [{
                    title: '驱动配置',
                    id:'DriverConfigInfoPanel_Id',
                    layout: "fit",
                    border: false,
                    items:DriverConfigInfoView
                },{
                    title: '单元配置',
                    id:'AcquisitionUnitInfoPanel_Id',
                    layout: "fit",
                    border: false,
                    items: AcquisitionUnitInfoView
                }],
                listeners: {
                    tabchange: function (tabPanel, newCard, oldCard, obj) {
                    	if(newCard.id=="DriverConfigInfoPanel_Id"){
//                    		loadFSDiagramAnalysisSingleStatData();
                    	}else if(newCard.id=="AcquisitionUnitInfoPanel_Id"){
                    		var AcquisitionUnitInfoStore= Ext.create('AP.store.acquisitionUnit.AcquisitionUnitInfoStore');
                    		var AcquisitionGroupInfoStore= Ext.create('AP.store.acquisitionUnit.AcquisitionGroupInfoStore');
                            var AcquisitionItemsTreeInfoStore= Ext.create('AP.store.acquisitionUnit.AcquisitionItemsTreeInfoStore');
                    	}
                    }
                }
             }]
        });
        this.callParent(arguments);
    }
});