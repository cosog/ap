Ext.define('AP.store.kafkaConfig.KafkaConfigWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.kafkaConfigWellListStore',
    fields: ['id','wellName'],
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/kafkaConfigController/getKafkaConfigWellList',
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
            var kafkaConfigGridPanel = Ext.getCmp("kafkaConfigGridPanel_Id");
            if (!isNotVal(kafkaConfigGridPanel)) {
                var column = createKafkaConfigWellListDataColumn(arrColumns);
                var newColumns = Ext.JSON.decode(column);
                kafkaConfigGridPanel = Ext.create('Ext.grid.Panel', {
                    id: "kafkaConfigGridPanel_Id",
                    border: false,
                    autoLoad: true,
                    columnLines: true,
                    forceFit: true,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	selectionchange: function (view, selected, o) {
                    		Ext.getCmp("KafkaConfigDataTextArea_Id").setValue("");
                    		if(selected.length>0){
                        		var kafkaConfigOperationGridPanel=Ext.getCmp("kafkaConfigOperationGridPanel_Id");
                        		if(isNotVal(kafkaConfigOperationGridPanel)){
                        			var operationRecord = kafkaConfigOperationGridPanel.getSelectionModel().getSelection();
                            		var type = operationRecord[0].data.id;
                            		var operationName = operationRecord[0].data.operation;
                            		if(type<=1 || type>=5){
                            			var wellName =selected[0].data.wellName;
                                		var deviceId =selected[0].data.deviceId;
                                		var protocolCode =selected[0].data.protocolCode;
                                		if(isNotVal(deviceId) && protocolCode.toUpperCase()=="KafkaDrive".toUpperCase()){
                                			readDeviceInfo(deviceId,type);
                                		}
                            		}
                        		}
                    		}
                    	},
                    	select: function(grid, record, index, eOpts) {
                        	Ext.getCmp('KafkaConfigWellListSelectRow_Id').setValue(index);
                        }
                    }
                });
                var SurfaceCardTypeListPanel = Ext.getCmp("KafkaConfigWellListPanel_Id");
                SurfaceCardTypeListPanel.add(kafkaConfigGridPanel);
            }
            if(get_rawData.totalCount>0){
            	var kafkaConfigGridPanel = Ext.getCmp("kafkaConfigGridPanel_Id");
                if (isNotVal(kafkaConfigGridPanel)) {
                	kafkaConfigGridPanel.getSelectionModel().deselectAll(true);
                	var KafkaConfigWellListSelectRow=Ext.getCmp('KafkaConfigWellListSelectRow_Id').getValue();
                	kafkaConfigGridPanel.getSelectionModel().select(parseInt(KafkaConfigWellListSelectRow), true);
                }
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
            var wellName = Ext.getCmp('kafkaConfigInfoWellCom_Id').getValue();
            var type = Ext.getCmp('kafkaConfigTypeWellCom_Id').getValue();
            var new_params = {
                    orgId: orgId,
                    wellName: wellName
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});