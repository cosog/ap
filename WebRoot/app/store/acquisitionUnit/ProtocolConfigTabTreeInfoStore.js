Ext.define('AP.store.acquisitionUnit.ProtocolConfigTabTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.ProtocolConfigTabTreeInfoStore',
    model: 'AP.model.role.RightTabTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/roleManagerController/constructProtocolConfigTabTreeGridTree',
        actionMethods: {
            read: 'POST'
        },
        reader: {
            type: 'json',
            keepRawData: true
        }
    },
    listeners: {
        beforeload: function (store, options) {
        },
        load: function (store, options, eOpts) {
        	var get_rawData = store.proxy.reader.rawData;
            var treeGridPanel = Ext.getCmp("ProtocolConfigTabTreeGridView_Id");
            if (!isNotVal(treeGridPanel)) {
                var treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ProtocolConfigTabTreeGridView_Id",
                    border: false,
                    layout: "fit",
                    loadMask: true,
                    rootVisible: false,
                    useArrows: true,
                    draggable: false,
                    forceFit: false,
                    onlyLeafCheckable: false, // 所有结点可选，如果不需要checkbox,该属性去掉
                    singleExpand: false,
//                    selType: 'checkboxmodel',
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: [{
                    	xtype: 'treecolumn',
                    	text: '标签列表',
                    	flex: 8,
                    	align: 'left',
                    	dataIndex: 'text'
                    },{
                    	header: 'tabIdaa',
                    	hidden: true,
                    	dataIndex: 'tabId'
                    }],
                    listeners: {
                        selectionchange: function (sm, selected) {
                        	
                        },
                        itemdblclick: function (grid, record, item, index, e, eOpts) {
                        	
                        },
                        itemclick: function (view,record,item,ndex,e,eOpts) {
                        	
                        },
                        select( v, record, index, eOpts ){
                        	var tabPanel = Ext.getCmp("ScadaDriverConfigTabPanel_Id");

                        	if(tabPanel.getActiveTab().id=="ScadaDriverModbusProtocolConfigTabPanel_Id"){
                        		Ext.getCmp("ModbusProtocolAddrMappingConfigSelectRow_Id").setValue(0);
                        		Ext.getCmp("ModbusProtocolAddrMappingItemsSelectRow_Id").setValue(0);
                        		
                        		var treeGridPanel = Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id");
                                if (isNotVal(treeGridPanel)) {
                                	treeGridPanel.getStore().load();
                                }else{
                                	Ext.create('AP.store.acquisitionUnit.ModbusProtocolTreeInfoStore');
                                }
                        	}else if(tabPanel.getActiveTab().id=="ScadaDriverModbusUnitConfigTabPanel_Id"){
                        		var activeId = Ext.getCmp("ModbusProtocolUnitConfigTabPanel_Id").getActiveTab().id;
                        		if(activeId=="ModbusProtocolAcqUnitConfigTabPanel_Id"){
                        			Ext.getCmp("ModbusProtocolAcqGroupConfigSelectRow_Id").setValue(0);
                        			var treePanel=Ext.getCmp("ModbusProtocolAcqGroupConfigTreeGridPanel_Id");
                            		if(isNotVal(treePanel)){
                            			treePanel.getStore().load();
                            		}else{
                            			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAcqUnitTreeInfoStore');
                            		}
                            	}else if(activeId=="ModbusProtocolAlarmUnitConfigTabPanel_Id"){
                            		Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").setValue(0);
                            		var treePanel=Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id");
                            		if(isNotVal(treePanel)){
                            			treePanel.getStore().load();
                            		}else{
                            			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitTreeInfoStore');
                            		}
                            	}else if(activeId=="ModbusProtocolDisplayUnitConfigTabPanel_Id"){
                            		Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").setValue(0);
                            		var treePanel=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id");
                            		if(isNotVal(treePanel)){
                            			treePanel.getStore().load();
                            		}else{
                            			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayUnitTreeInfoStore');
                            		}
                            	}else if(activeId=="ModbusProtocolReportUnitConfigTabPanel_Id"){
                            		Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").setValue(0);
                            		var treeGridPanel = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id");
                                    if (isNotVal(treeGridPanel)) {
                                    	treeGridPanel.getStore().load();
                                    }else{
                                    	Ext.create('AP.store.acquisitionUnit.ModbusProtocolReportUnitTreeInfoStore');
                                    }
                            	}
                        	}else if(tabPanel.getActiveTab().id=="ScadaDriverModbusInstanceConfigTabPanel_Id"){
                        		var activeId = Ext.getCmp("ModbusProtocolInstanceConfigTabPanel_Id").getActiveTab().id;
                        		if(activeId=="ModbusProtocolAcqInstanceConfigTabPanel_Id"){
                        			Ext.getCmp("ScadaProtocolModbusInstanceConfigSelectRow_Id").setValue(0);
                        			var treePanel=Ext.getCmp("ModbusProtocolInstanceConfigTreeGridPanel_Id");
                            		if(isNotVal(treePanel)){
                            			treePanel.getStore().load();
                            		}else{
                            			Ext.create('AP.store.acquisitionUnit.ModbusProtocolInstanceTreeInfoStore');
                            		}
                            	}else if(activeId=="ModbusProtocolDisplayInstanceConfigTabPanel_Id"){
                            		Ext.getCmp("ModbusProtocolDisplayInstanceTreeSelectRow_Id").setValue(0);
                            		var treePanel=Ext.getCmp("ModbusProtocolDisplayInstanceConfigTreeGridPanel_Id");
                            		if(isNotVal(treePanel)){
                            			treePanel.getStore().load();
                            		}else{
                            			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayInstanceTreeInfoStore');
                            		}
                            	}else if(activeId=="ModbusProtocolAlarmInstanceConfigTabPanel_Id"){
                            		Ext.getCmp("ModbusProtocolAlarmInstanceTreeSelectRow_Id").setValue(0);
                            		var treePanel=Ext.getCmp("ModbusProtocolAlarmInstanceConfigTreeGridPanel_Id");
                            		if(isNotVal(treePanel)){
                            			treePanel.getStore().load();
                            		}else{
                            			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmInstanceTreeInfoStore');
                            		}
                            	}else if(activeId=="ModbusProtocolSMSInstanceConfigTabPanel_Id"){
                            		Ext.getCmp("ModbusProtocolReportInstanceTreeSelectRow_Id").setValue(0);
                            		var gridPanel=Ext.getCmp("ModbusProtocolSMSInstanceGridPanel_Id");
                            		if(isNotVal(gridPanel)){
                            			gridPanel.getStore().load();
                            		}else{
                            			Ext.create('AP.store.acquisitionUnit.ModbusProtocolSMSInstanceStore');
                            		}
                            	}
                        	}
                        }
                    }

                });
                var panel = Ext.getCmp("ProtocolConfigTabTreePanel_Id");
                panel.add(treeGridPanel);
            }
            if(store.data.length>0){
            	treeGridPanel.getSelectionModel().select(0, true);
            }
        }
    }
});