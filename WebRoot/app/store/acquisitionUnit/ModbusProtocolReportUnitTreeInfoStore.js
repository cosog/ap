Ext.define('AP.store.acquisitionUnit.ModbusProtocolReportUnitTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.modbusProtocolReportUnitTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/repoerUnitTreeData',
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
        	var treeGridPanel = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id");
            if (!isNotVal(treeGridPanel)) {
            	treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ModbusProtocolReportUnitConfigTreeGridPanel_Id",
//                    layout: "fit",
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
                    columns: [{
                    	xtype: 'treecolumn',
                    	text: '单元列表',
                        flex: 8,
                        align: 'left',
                        dataIndex: 'text'
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
                        	Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").setValue(index);
                        	var selectedUnitCode='';
                        	if(record.data.classes==0){
                        		if(isNotVal(record.data.children) && record.data.children.length>0){
                        			CreateReportTemplateInfoTable(record.data.children[0].text,record.data.children[0].classes,record.data.children[0].code);
                        			selectedUnitCode=record.data.children[0].code;
                        		}else{
                        			Ext.getCmp("ModbusProtocolReportUnitTemplateTableInfoPanel_Id").setTitle('报表模板');
                        			if(protocolConfigAddrMappingItemsHandsontableHelper!=null && protocolConfigAddrMappingItemsHandsontableHelper.hot!=undefined){
                        				protocolConfigAddrMappingItemsHandsontableHelper.hot.loadData([]);
                        			}
                        		}
                        	}else if(record.data.classes==1){
                        		selectedUnitCode=record.data.code;
                        		CreateReportTemplateInfoTable(record.data.text,record.data.classes,record.data.code);
                        	}
                        	CreateReportTotalItemsInfoTable(record.data.deviceType,selectedUnitCode,record.data.text,record.data.classes);
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                        	
                        }
                    }

                });
                var panel = Ext.getCmp("ModbusProtocolReportUnitConfigPanel_Id");
                panel.add(treeGridPanel);
            }
            treeGridPanel.getSelectionModel().deselectAll(true);
            treeGridPanel.getSelectionModel().select(0, true);
        }
    }
});