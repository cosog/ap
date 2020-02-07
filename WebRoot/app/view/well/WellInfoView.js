Ext.define("AP.view.well.WellInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.wellInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var WellInfoPanel = Ext.create('AP.view.well.WellInfoPanel');
        Ext.apply(me, {
            items: [WellInfoPanel]
//        	items: [{
//        		xtype: 'tabpanel',
//        		activeTab: 0,
//        		border: false,
//        		tabPosition: 'bottom',
//        		items: [{
//        			title: cosog.string.pumpUnit,
//        			layout: "fit",
//        			border: false,
//        			items: [WellInfoPanel]
//        		},{
//        			title: cosog.string.screwPump,
//        			layout: "fit",
//        			border: false
//        		}],
//        		listeners: {
//        			tabchange: function (tabPanel, newCard,oldCard, obj) {
//        				Ext.getCmp("bottomTab_Id").setValue(newCard.id); //
//        			},
//        			delay: 500
//        		}
//        	}]
        });
        me.callParent(arguments);
    }

});