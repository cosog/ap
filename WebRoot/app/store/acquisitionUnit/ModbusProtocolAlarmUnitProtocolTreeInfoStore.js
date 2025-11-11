Ext.define('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitProtocolTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.modbusProtocolAlarmUnitProtocolTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/modbusProtocolAddrMappingTreeData',
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
        	var deviceTypeIds='';
        	var tabTreeGridPanelSelection= Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getSelectionModel().getSelection();
        	if(tabTreeGridPanelSelection.length>0){
        		deviceTypeIds=foreachAndSearchTabChildId(tabTreeGridPanelSelection[0]);
        	}
        	var new_params = {
        			deviceTypeIds: deviceTypeIds
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        load: function (store, options, eOpts) {
        	var gridPanel = Ext.getCmp("AlarmUnitProtocolTreeGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.tree.Panel', {
                    id: "AlarmUnitProtocolTreeGridPanel_Id",
                    border: false,
                    animate: true,
                    enableDD: false,
                    useArrows: false,
                    rootVisible: false,
                    autoScroll: true,
                    forceFit: true,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + loginUserLanguageResource.emptyMsg + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: [{
                    	xtype: 'treecolumn',
                    	text: loginUserLanguageResource.protocolList,
                        flex: 8,
                        align: 'left',
                        dataIndex: 'text',
                        renderer: function (value) {
                            if (isNotVal(value)) {
                                return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                            }
                        }
                    },{
                        header: 'id',
                        hidden: true,
                        dataIndex: 'id'
                    }],
                    listeners: {
                    	checkchange: function (node, checked) {
                    		
                        },
                        selectionchange ( view, selected, eOpts ){
                        	
                        },
                        select( v, record, index, eOpts ){
                        	Ext.getCmp("ModbusProtocolAlarmUnitProtocolSelectRow_Id").setValue(index);
                        	Ext.getCmp("UnitConfigProtocolSelectCode_Id").setValue(record.data.code);

                        	Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").setValue(0);
                        	Ext.getCmp("AlarmUnitTreeSelectUnitId_Id").setValue(0);
                        	var treePanel=Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id");
                    		if(isNotVal(treePanel)){
                    			treePanel.getStore().load();
                    		}else{
                    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitTreeInfoStore');
                    		}
                        },
                        beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                        	
                        }
                    }

                });
                var panel = Ext.getCmp("ModbusProtocolAlarmUnitProtocolListPanel_Id");
                panel.add(gridPanel);
            }
            var selectedRow=parseInt(Ext.getCmp("ModbusProtocolAlarmUnitProtocolSelectRow_Id").getValue());
            var selectedCode=Ext.getCmp("UnitConfigProtocolSelectCode_Id").getValue();
            
            if(isNotVal(selectedCode)){
            	for(var i=0;i<store.data.length;i++){
            		if(store.getAt(i).data.code==selectedCode){
            			selectedRow=i;
            			break;
            		}
            	}
            }else{
            	for(var i=0;i<store.data.length;i++){
            		if(store.getAt(i).data.classes>0){
            			selectedRow=i;
            			break;
            		}
            	}
            }
            gridPanel.getSelectionModel().deselectAll(true);
            gridPanel.getSelectionModel().select(selectedRow, true);
        }
    }
});