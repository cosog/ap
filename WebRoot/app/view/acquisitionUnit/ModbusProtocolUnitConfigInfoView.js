Ext.define('AP.view.acquisitionUnit.ModbusProtocolUnitConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolUnitConfigInfoView',
    layout: "fit",
    id:'modbusProtocolUnitConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	var ModbusProtocolAcqUnitConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolAcqUnitConfigInfoView');
//    	var ModbusProtocolAlarmUnitConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolAlarmUnitConfigInfoView');
//    	var ModbusProtocolDisplayUnitConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolDisplayUnitConfigInfoView');
//    	var ModbusProtocolReportUnitConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolReportUnitConfigInfoView');
    	Ext.apply(me, {
    		items: [{
    			xtype: 'tabpanel',
                id:"ModbusProtocolUnitConfigTabPanel_Id",
                activeTab: 0,
                border: false,
                tabPosition: 'left',
                items: [{
                	title:'采控单元',
                	id:'ModbusProtocolAcqUnitConfigTabPanel_Id',
                	items: [ModbusProtocolAcqUnitConfigInfoView],
    				layout: "fit",
    				iconCls: 'check2',
    				border: false
                },{
                	title:'显示单元',
                	id:'ModbusProtocolDisplayUnitConfigTabPanel_Id',
//                	items: [ModbusProtocolDisplayUnitConfigInfoView],
    				layout: "fit",
    				border: false
                },{
                	title:'报警单元',
                	id:'ModbusProtocolAlarmUnitConfigTabPanel_Id',
//                	items: [ModbusProtocolAlarmUnitConfigInfoView],
    				layout: "fit",
    				border: false
                },{
                	title:'报表单元',
                	id:'ModbusProtocolReportUnitConfigTabPanel_Id',
//                	items: [ModbusProtocolReportUnitConfigInfoView],
    				layout: "fit",
    				border: false
                }],
                listeners: {
                	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        				oldCard.setIconCls(null);
        				newCard.setIconCls('check2');
        				
        				if(newCard.id=="ModbusProtocolAlarmUnitConfigTabPanel_Id"){
        					var ModbusProtocolAlarmUnitConfigInfoView=Ext.getCmp("modbusProtocolAlarmUnitConfigInfoViewId");
        					if(!isNotVal(ModbusProtocolAlarmUnitConfigInfoView)){
        						ModbusProtocolAlarmUnitConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolAlarmUnitConfigInfoView');
        						Ext.getCmp("ModbusProtocolAlarmUnitConfigTabPanel_Id").add(ModbusProtocolAlarmUnitConfigInfoView);
        					}
        				}else if(newCard.id=="ModbusProtocolDisplayUnitConfigTabPanel_Id"){
        					var ModbusProtocolDisplayUnitConfigInfoView=Ext.getCmp("modbusProtocolDisplayUnitConfigInfoViewId");
        					if(!isNotVal(ModbusProtocolDisplayUnitConfigInfoView)){
        						ModbusProtocolDisplayUnitConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolDisplayUnitConfigInfoView');
        						Ext.getCmp("ModbusProtocolDisplayUnitConfigTabPanel_Id").add(ModbusProtocolDisplayUnitConfigInfoView);
        					}
        				}else if(newCard.id=="ModbusProtocolReportUnitConfigTabPanel_Id"){
        					var ModbusProtocolReportUnitConfigInfoView=Ext.getCmp("modbusProtocolReportUnitConfigInfoViewId");
        					if(!isNotVal(ModbusProtocolReportUnitConfigInfoView)){
        						ModbusProtocolReportUnitConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolReportUnitConfigInfoView');
        						Ext.getCmp("ModbusProtocolReportUnitConfigTabPanel_Id").add(ModbusProtocolReportUnitConfigInfoView);
        					}
        				}
        			},
        			tabchange: function (tabPanel, newCard, oldCard, obj) {
                    	if(newCard.id=="ModbusProtocolAcqUnitConfigTabPanel_Id"){
                			Ext.getCmp("ModbusProtocolAcqGroupConfigSelectRow_Id").setValue(0);
                			var treePanel=Ext.getCmp("ModbusProtocolAcqGroupConfigTreeGridPanel_Id");
                    		if(isNotVal(treePanel)){
                    			treePanel.getStore().load();
                    		}else{
                    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAcqUnitTreeInfoStore');
                    		}
                    	}else if(newCard.id=="ModbusProtocolAlarmUnitConfigTabPanel_Id"){
                    		Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").setValue(0);
                    		var treePanel=Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id");
                    		if(isNotVal(treePanel)){
                    			treePanel.getStore().load();
                    		}else{
                    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitTreeInfoStore');
                    		}
                    	}else if(newCard.id=="ModbusProtocolDisplayUnitConfigTabPanel_Id"){
                    		Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").setValue(0);
                    		var treePanel=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id");
                    		if(isNotVal(treePanel)){
                    			treePanel.getStore().load();
                    		}else{
                    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayUnitTreeInfoStore');
                    		}
                    	}else if(newCard.id=="ModbusProtocolReportUnitConfigTabPanel_Id"){
                    		Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").setValue(0);
                    		var treeGridPanel = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id");
                            if (isNotVal(treeGridPanel)) {
                            	treeGridPanel.getStore().load();
                            }else{
                            	Ext.create('AP.store.acquisitionUnit.ModbusProtocolReportUnitTreeInfoStore');
                            }
                    	}
                    },afterrender: function (comp,eOpts) {
                    	
                    }
                }
    		}]
    	});
        this.callParent(arguments);
    }
});
