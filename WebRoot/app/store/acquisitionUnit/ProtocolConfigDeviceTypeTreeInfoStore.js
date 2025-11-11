Ext.define('AP.store.acquisitionUnit.ProtocolConfigDeviceTypeTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.ProtocolConfigDeviceTypeTreeInfoStore',
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
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>"
                    },
                    store: store,
                    columns: [{
                    	xtype: 'treecolumn',
                    	text: loginUserLanguageResource.deviceType,
                    	flex: 8,
                    	align: 'left',
                    	dataIndex: 'text'
                    },{
                    	header: 'deviceTypeIdaa',
                    	hidden: true,
                    	dataIndex: 'deviceTypeId'
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
                        		Ext.getCmp("ModbusProtocolAddrMappingConfigSelectProtocolId_Id").setValue(0);
                        		
                        		var treeGridPanel = Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id");
                                if (isNotVal(treeGridPanel)) {
                                	treeGridPanel.getStore().load();
                                }else{
                                	Ext.create('AP.store.acquisitionUnit.ModbusProtocolTreeInfoStore');
                                }
                        	}else if(tabPanel.getActiveTab().id=="ScadaDriverModbusUnitConfigTabPanel_Id"){
                        		Ext.getCmp("UnitConfigProtocolSelectCode_Id").setValue('');
                        		var activeId = Ext.getCmp("ModbusProtocolUnitConfigTabPanel_Id").getActiveTab().id;
                        		if(activeId=="ModbusProtocolAcqUnitConfigTabPanel_Id"){
                        			Ext.getCmp("ModbusProtocolAcqUnitProtocolSelectRow_Id").setValue(0);
                        			var treePanel=Ext.getCmp("AcqUnitProtocolTreeGridPanel_Id");
                            		if(isNotVal(treePanel)){
                            			treePanel.getStore().load();
                            		}else{
                            			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAcqUnitProtocolTreeInfoStore');
                            		}
                            	}else if(activeId=="ModbusProtocolAlarmUnitConfigTabPanel_Id"){
                            		Ext.getCmp("ModbusProtocolAlarmUnitProtocolSelectRow_Id").setValue(0);
                            		var treePanel=Ext.getCmp("AlarmUnitProtocolTreeGridPanel_Id");
                            		if(isNotVal(treePanel)){
                            			treePanel.getStore().load();
                            		}else{
                            			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitProtocolTreeInfoStore');
                            		}
                            	}else if(activeId=="ModbusProtocolDisplayUnitConfigTabPanel_Id"){
                            		Ext.getCmp("ModbusProtocolDisplayUnitProtocolSelectRow_Id").setValue(0);
                            		var treePanel=Ext.getCmp("DisplayUnitProtocolTreeGridPanel_Id");
                            		if(isNotVal(treePanel)){
                            			treePanel.getStore().load();
                            		}else{
                            			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayUnitProtocolTreeInfoStore');
                            		}
                            	}else if(activeId=="ModbusProtocolReportUnitConfigTabPanel_Id"){
                            		Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").setValue(0);
                            		Ext.getCmp("ReportUnitTreeSelectUnitId_Id").setValue(0);
                            		var treeGridPanel = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id");
                                    if (isNotVal(treeGridPanel)) {
                                    	treeGridPanel.getStore().load();
                                    }else{
                                    	Ext.create('AP.store.acquisitionUnit.ModbusProtocolReportUnitTreeInfoStore');
                                    }
                            	}
                        	}else if(tabPanel.getActiveTab().id=="ScadaDriverModbusInstanceConfigTabPanel_Id"){
                        		Ext.getCmp("InstanceConfigProtocolSelectCode_Id").setValue('');
                        		var activeId = Ext.getCmp("ModbusProtocolInstanceConfigTabPanel_Id").getActiveTab().id;
                        		if(activeId=="ModbusProtocolAcqInstanceConfigTabPanel_Id"){
                        			Ext.getCmp("ModbusProtocolAcqInstanceProtocolSelectRow_Id").setValue(0);
                                	var treePanel=Ext.getCmp("AcqInstanceProtocolTreeGridPanel_Id");
                            		if(isNotVal(treePanel)){
                            			treePanel.getStore().load();
                            		}else{
                            			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAcqInstanceProtocolTreeInfoStore');
                            		}
                            	}else if(activeId=="ModbusProtocolDisplayInstanceConfigTabPanel_Id"){
                            		Ext.getCmp("ModbusProtocolDisplayInstanceProtocolSelectRow_Id").setValue(0);
                            		var treePanel=Ext.getCmp("DisplayInstanceProtocolTreeGridPanel_Id");
                            		if(isNotVal(treePanel)){
                            			treePanel.getStore().load();
                            		}else{
                            			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayInstanceProtocolTreeInfoStore');
                            		}
                            	}else if(activeId=="ModbusProtocolAlarmInstanceConfigTabPanel_Id"){
                            		Ext.getCmp("ModbusProtocolAlarmInstanceProtocolSelectRow_Id").setValue(0);
                            		var treePanel=Ext.getCmp("AlarmInstanceProtocolTreeGridPanel_Id");
                            		if(isNotVal(treePanel)){
                            			treePanel.getStore().load();
                            		}else{
                            			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmInstanceProtocolTreeInfoStore');
                            		}
                            	}else if(activeId=="ModbusProtocolReportInstanceConfigTabPanel_Id"){
                            		Ext.getCmp("ModbusProtocolReportInstanceTreeSelectRow_Id").setValue(0);
                            		Ext.getCmp("ReportInstanceTreeSelectInstanceId_Id").setValue(0);
                            		var treePanel=Ext.getCmp("ModbusProtocolReportInstanceConfigTreeGridPanel_Id");
                            		if(isNotVal(treePanel)){
                            			treePanel.getStore().load();
                            		}else{
                            			Ext.create('AP.store.acquisitionUnit.ModbusProtocolReportInstanceTreeInfoStore');
                            		}
                            	}else if(activeId=="ModbusProtocolSMSInstanceConfigTabPanel_Id"){
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