Ext.define('AP.store.acquisitionUnit.ExportProtocolAlarmUnitTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.exportProtocolAlarmUnitTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/exportProtocolAlarmUnitTreeData',
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
        	var deviceType=0;
        	var protocolName="";
        	var protocolCode="";
        	var exportProtocolTreeGridPanelSelection=Ext.getCmp("ExportProtocolTreeGridPanel_Id").getSelectionModel().getSelection();
        	if(exportProtocolTreeGridPanelSelection.length>0 && exportProtocolTreeGridPanelSelection[0].data.classes==1){
        		deviceType=exportProtocolTreeGridPanelSelection[0].data.deviceType;
        		protocolName=exportProtocolTreeGridPanelSelection[0].data.text;
        		protocolCode=exportProtocolTreeGridPanelSelection[0].data.code;
        	}
        	var new_params = {
        			deviceType: deviceType,
        			protocolName: protocolName,
        			protocolCode: protocolCode
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        load: function (store, options, eOpts) {
        	var gridPanel = Ext.getCmp("ExportProtocolAlarmUnitTreeGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ExportProtocolAlarmUnitTreeGridPanel_Id",
//                    layout: "fit",
                    border: false,
                    animate: true,
                    enableDD: false,
                    useArrows: false,
                    rootVisible: false,
                    autoScroll: true,
                    forceFit: true,
                    selType: 'checkboxmodel',
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: [{
                    	xtype: 'treecolumn',
                    	text: '报警单元列表',
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
                        	
                        },select( v, record, index, eOpts ){
                        	
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                        	
                        }
                    }

                });
                var panel = Ext.getCmp("ProtocolExportAlarmUnitPanel_Id");
                panel.add(gridPanel);
            }
            gridPanel.getSelectionModel().deselectAll(true);
            gridPanel.getSelectionModel().selectAll(true);
        }
    }
});