Ext.define("AP.view.reportOut.RPCDailyReportPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.RPCDailyReportPanel',
    layout: 'fit',
    id: 'RPCDailyReportPanel_View',
    border: false,
    initComponent: function () {
    	var me = this;
    	var RPCSingleWellDailyReportPanel = Ext.create('AP.view.reportOut.RPCSingleWellDailyReportPanel');
    	var RPCProductionDailyReportPanel = Ext.create('AP.view.reportOut.RPCProductionDailyReportPanel');
    	Ext.apply(me, {
        	items: [{
        		xtype: 'tabpanel',
        		id:"RPCDailyReportTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'left',
        		items: [{
    				title: '<div style="color:#000000;font-size:11px;font-family:SimSun">单井报表</div>',
    				id:'RPCSingleWellDailyReportTabPanel_Id',
    				items: [RPCSingleWellDailyReportPanel],
    				layout: "fit",
    				border: false
    			},{
    				title: '<div style="color:#000000;font-size:11px;font-family:SimSun">区域报表</div>',
    				id:'RPCProductionDailyReportTabPanel_Id',
    				items: [RPCProductionDailyReportPanel],
    				layout: "fit",
    				border: false
    			}],
        		listeners: {
        			tabchange: function (tabPanel, newCard,oldCard, obj) {
        				Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
        				if(newCard.id=="RPCSingleWellDailyReportTabPanel_Id"){
        					Ext.getCmp('RPCSingleWellDailyReportPanelWellListCombo_Id').setRawValue('');
        					Ext.getCmp('RPCSingleWellDailyReportPanelWellListCombo_Id').setValue('');
        					var gridPanel = Ext.getCmp("RPCSingleWellDailyReportGridPanel_Id");
        					if (isNotVal(gridPanel)) {
        						gridPanel.getStore().load();
        					}else{
        						Ext.create('AP.store.reportOut.RPCSingleWellDailyReportWellListStore');
        					}
        				}else if(newCard.id=="RPCProductionDailyReportTabPanel_Id"){
        					Ext.getCmp('RPCProductionDailyReportPanelWellListCombo_Id').setRawValue('');
        					Ext.getCmp('RPCProductionDailyReportPanelWellListCombo_Id').setValue('');
        					var gridPanel = Ext.getCmp("RPCProductionDailyReportGridPanel_Id");
        	    			if (isNotVal(gridPanel)) {
        	    				gridPanel.getStore().load();
        	    			}else{
        	    				Ext.create('AP.store.reportOut.RPCProductionDailyReportInstanceListStore');
        	    			}
        				}
        			}
        		}
            }]
        });
        me.callParent(arguments);
    }
});