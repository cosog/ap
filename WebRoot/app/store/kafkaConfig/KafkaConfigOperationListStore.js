Ext.define('AP.store.kafkaConfig.KafkaConfigOperationListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.kafkaConfigOperationListStore',
    fields: ['id','wellName'],
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/kafkaConfigController/getKafkaConfigOperationList',
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
            var kafkaConfigGridPanel = Ext.getCmp("kafkaConfigOperationGridPanel_Id");
            if (!isNotVal(kafkaConfigGridPanel)) {
                var column = createDiagStatisticsColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                kafkaConfigGridPanel = Ext.create('Ext.grid.Panel', {
                    id: "kafkaConfigOperationGridPanel_Id",
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
                    			if(selected[0].data.id==2){
                        			Ext.getCmp("KafkaConfigSendBtn_Id").disable();
                        		}else{
                        			Ext.getCmp("KafkaConfigSendBtn_Id").enable();
                        		}
                    			
                    			if(selected[0].data.id<=1 || selected[0].data.id>=5){
                        			var loaded=false;
                        			var kafkaConfigGridPanel=Ext.getCmp("kafkaConfigGridPanel_Id");
                        			if(isNotVal(kafkaConfigGridPanel)){
                        				loaded=true;
                        				var wellRecord = kafkaConfigGridPanel.getSelectionModel().getSelection();
                                		var type = selected[0].data.id;
                                		var operationName = selected[0].data.operation;
                                		
                                		var wellName =wellRecord[0].data.wellName;
                                		var deviceId =wellRecord[0].data.deviceId;
                                		var driverCode =wellRecord[0].data.driverCode;
                                		
                                		if(isNotVal(deviceId) && driverCode.toUpperCase()=="KafkaDrive".toUpperCase()){
                                			readDeviceInfo(deviceId,type);
                                		}
                        			}
                        		}
                    		}
                    	}
                    }
                });
                var SurfaceCardTypeListPanel = Ext.getCmp("KafkaConfigOperationListPanel_Id");
                SurfaceCardTypeListPanel.add(kafkaConfigGridPanel);
            }
            kafkaConfigGridPanel.getSelectionModel().select(0,true);//选中第一行
        },
        beforeload: function (store, options) {
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});