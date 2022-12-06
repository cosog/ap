Ext.define('AP.store.acquisitionUnit.ModbusProtocolSMSInstanceStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.modbusProtocolSMSInstanceStore',
    fields: ['id','name','code','acqProtocolType','acqProtocolType','acqProtocolTypeName','ctrlProtocolType','ctrlProtocolTypeName','sort'],
    autoLoad: true,
    pageSize: 50,
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/getSMSInstanceList',
        actionMethods: {
            read: 'POST'
        },
    reader: {
            type: 'json',
            rootProperty: 'totalRoot',
            totalProperty: 'totalCount',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, record, f, op, o) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var arrColumns = get_rawData.columns;
            var gridPanel = Ext.getCmp("ModbusProtocolSMSInstanceGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var column = createProtocolSMSInstanceColumn(arrColumns);
                var newColumns = Ext.JSON.decode(column);
                var bbar = new Ext.PagingToolbar({
                	store: store,
                	displayInfo: true,
                	displayMsg: '共 {2}条'
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "ModbusProtocolSMSInstanceGridPanel_Id",
                    border: false,
                    autoLoad: false,
//                    bbar: bbar,
                    columnLines: true,
                    forceFit: true,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    selType: 'checkboxmodel',
                    listeners: {
                    	selectionchange: function (view, selected, o) {
                    		
                    	},
                    	select: function(grid, record, index, eOpts) {
                    	},
                    	itemdblclick: function () {
                    		modifyModbusProtocolSMSInstanceConfigData();
                        }
                    }
                });
                var panel = Ext.getCmp("modbusProtocolSMSInstanceConfigPanelId");
                panel.add(gridPanel);
            }
        },
        beforeload: function (store, options) {
        	var name='';
            var new_params = {
            		name: name
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});