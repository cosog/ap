Ext.define('AP.store.acquisitionUnit.ModbusProtocolDisplayUnitProtocolTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.modbusProtocolDisplayUnitProtocolTreeInfoStore',
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
        	var gridPanel = Ext.getCmp("DisplayUnitProtocolTreeGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.tree.Panel', {
                    id: "DisplayUnitProtocolTreeGridPanel_Id",
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
                        	Ext.getCmp("ModbusProtocolDisplayUnitProtocolSelectRow_Id").setValue(index);
                        	Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").setValue(0);
                        	Ext.getCmp("DisplayUnitTreeSelectUnitId_Id").setValue(0);
                        	var treePanel=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id");
                    		if(isNotVal(treePanel)){
                    			treePanel.getStore().load();
                    		}else{
                    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayUnitTreeInfoStore');
                    		}
                        },
                        beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                        	
                        }
                    }

                });
                var panel = Ext.getCmp("ModbusProtocolDisplayUnitProtocolListPanel_Id");
                panel.add(gridPanel);
            }
            var selectedRow=parseInt(Ext.getCmp("ModbusProtocolDisplayUnitProtocolSelectRow_Id").getValue());
            if(selectedRow==0){
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