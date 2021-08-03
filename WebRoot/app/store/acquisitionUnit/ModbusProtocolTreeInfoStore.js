Ext.define('AP.store.acquisitionUnit.ModbusProtocolTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.modbusProtocolTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/modbusConfigTreeData',
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
        	var ModbusProtocolConfigTreeGridPanel = Ext.getCmp("ModbusProtocolConfigTreeGridPanel_Id");
            if (!isNotVal(ModbusProtocolConfigTreeGridPanel)) {
                ModbusProtocolConfigTreeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ModbusProtocolConfigTreeGridPanel_Id",
                    layout: "fit",
                    border: false,
                    animate: true,
                    enableDD: false,
                    useArrows: false,
                    rootVisible: false,
                    autoScroll: true,
                    forceFit: true,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: [
                        {
                            xtype: 'treecolumn',
                            text: '协议列表',
                            flex: 8,
                            align: 'left',
                            dataIndex: 'text'
                        },
                        {
                            header: 'id',
                            hidden: true,
                            dataIndex: 'id'
                        }],
                    listeners: {
                    	checkchange: function (node, checked) {
//                            alert("aa");
                        },
                        selectionchange ( view, selected, eOpts ){
                        	if(selected.length>0&&selected[0].data.classes==1){
                        		CreateProtocolItemsConfigInfoTable(selected[0].data.text);
                        	}
                        }
                    }

                });
                var ModbusProtocolConfigPanel = Ext.getCmp("ModbusProtocolConfigPanel_Id");
                ModbusProtocolConfigPanel.add(ModbusProtocolConfigTreeGridPanel);
            }
            ModbusProtocolConfigTreeGridPanel.getSelectionModel().select(0, true);
        }
    }
});