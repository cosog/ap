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
//                        	if(selected.length>0&&selected[0].data.classes==1){
//                        		CreateProtocolItemsConfigInfoTable(selected[0].data.text);
//                        	}else if(selected.length>0&&selected[0].data.classes==2||selected.length>0&&selected[0].data.classes==3){
//                        		CreateProtocolItemsConfigInfoTable(selected[0].data.protocol);
//                        	}
//                        	else if(selected.length>0&&selected[0].data.classes==3){
//                        		showAcquisitionGroupOwnItems(selected[0].data.code);
//                        	}
                        },select( v, record, index, eOpts ){
                        	Ext.getCmp("ScadaProtocolModbusConfigSelectRow_Id").setValue(index);
                        	if(record.data.classes==1){
                        		CreateProtocolItemsConfigInfoTable(record.data.text);
                        	}else if(record.data.classes==2||record.data.classes==3){
                        		CreateProtocolItemsConfigInfoTable(record.data.protocol);
                        	}
                        	if(record.data.classes==3){
                        		showAcquisitionGroupOwnItems(record.data.code);
                        	}
                        	CreateProtocolConfigPropertiesInfoTable(record.data);
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