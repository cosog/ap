Ext.define('AP.store.realTimeMonitoring.RPCRealTimeMonitoringStatStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.rpcRealTimeMonitoringStatStore',
    fields: ['id','item','count','itemCode'],
    autoLoad: true,
    pageSize: 50,
    proxy: {
        type: 'ajax',
        url: context + '/realTimeMonitoringController/getDeviceRealTimeStat',
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
//            var arrColumns = get_rawData.columns;
            Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(get_rawData.AlarmShowStyle));
            var gridPanel = Ext.getCmp("RPCRealTimeMonitoringStatGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "RPCRealTimeMonitoringStatGridPanel_Id",
                    border: false,
                    autoLoad: true,
                    columnLines: true,
                    forceFit: false,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: [{
                        text: '序号',
                        lockable: true,
                        align: 'center',
                        width: 50,
                        xtype: 'rownumberer',
                        sortable: false,
                        locked: false
                    }, {
                        text: '名称',
                        flex:2,
                        lockable: true,
                        align: 'center',
                        sortable: false,
                        dataIndex: 'item',
                        renderer: function (value) {
                        	if(isNotVal(value)){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
			        		}
                        }
                    }, {
                        text: '变量',
                        flex:1,
                        lockable: true,
                        align: 'center',
                        sortable: false,
                        dataIndex: 'count',
                        renderer: function (value, o, p, e) {
                            return adviceStatTableCommStatusColor(value, o, p, e);
                        }
                    }],
                    listeners: {
                    	selectionchange: function (view, selected, o) {
                    		if(selected.length>0){
                    			Ext.getCmp('RealTimeMonitoringRPCDeviceListComb_Id').setValue('');
                    			Ext.getCmp('RealTimeMonitoringRPCDeviceListComb_Id').setRawValue('');
                    			var RPCRealTimeMonitoringListGridPanel = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id");
                    			if (isNotVal(RPCRealTimeMonitoringListGridPanel)) {
                    				RPCRealTimeMonitoringListGridPanel.getSelectionModel().deselectAll(true);
                    				RPCRealTimeMonitoringListGridPanel.getStore().load();
                    			}else{
                    				Ext.create('AP.store.realTimeMonitoring.RPCRealTimeMonitoringWellListStore');
                    			}
                    		}
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		
                    	}
                    }
                });
                var RPCRealTimeMonitoringStatInfoPanel = Ext.getCmp("RPCRealTimeMonitoringStatInfoPanel_Id");
                RPCRealTimeMonitoringStatInfoPanel.add(gridPanel);
            }
            if(get_rawData.totalCount>0){
            	gridPanel.getSelectionModel().deselectAll(true);
            	gridPanel.getSelectionModel().select(0, true);
            }
            
            initRealTimeMonitoringStatPieOrColChat(get_rawData);
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
            var new_params = {
                    orgId: orgId,
                    deviceType:0
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});