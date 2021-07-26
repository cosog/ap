Ext.define('AP.store.kafkaConfig.A9RawWaterCutDataListStore', {
    extend: 'Ext.data.Store',
    fields: ['wellName','acqTime','singal','ver'],
    autoLoad: true,
    pageSize: 50,
    proxy: {
    	type: 'ajax',
        url: context + '/kafkaConfigController/getA9RawWaterCutDataList',
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
        load: function (store, sEops) {
        	var get_rawData = store.proxy.reader.rawData;
            var arrColumns = get_rawData.columns;
            var column = createA9RawDataColumn(arrColumns);
            var newColumns = Ext.JSON.decode(column);
            var bbar = new Ext.PagingToolbar({
            	store: store,
            	displayInfo: true,
            	displayMsg: '当前 {0}~{1}条  共 {2} 条'
	        });
            var gridPanel = Ext.getCmp("A9RawWaterCutDataGridPanel_Id");
            if (!isNotVal(gridPanel)) {
            	gridPanel= Ext.create('Ext.grid.Panel', {
                    id: 'A9RawWaterCutDataGridPanel_Id',
                    border: false,
                    forceFit: true,
                    bbar: bbar,
                    store:store,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    columnLines: true,
                    columns:newColumns,
                    height: document.documentElement.clientHeight,
                    listeners: {
                        selectionchange: function (view, selected, o) {
                            if (selected.length > 0) {
                            	if(isNaN(selected[0].id)){
                            		$("#A9RawWaterCutDataCurveChartDiv1_Id").html('');
                                	$("#A9RawWaterCutDataCurveChartDiv2_Id").html('');
                            	}else{
                            		//请求图形数据
                            		Ext.create("AP.store.kafkaConfig.A9RawWaterCutCurveDataStore");
                            	}
                            }
                            
                        },
                        itemdblclick: function (view,record,item,index,e,eOpts) {
                        	var deviceId = Ext.getCmp('A9RawDataDeviceCom_Id').getValue();
                    		if(deviceId==null||deviceId==""){
                    			Ext.getCmp("a9RawDataTblPanel_Id").setTitle("设备历史");
                    			Ext.getCmp("A9RawDataAllBtn_Id").show();
                        		Ext.getCmp("A9RawDataHisBtn_Id").hide();
                        		Ext.getCmp("A9RawDataStartDate_Id").show();
                        		Ext.getCmp("A9RawDataEndDate_Id").show();
                            	
                    			Ext.getCmp('A9RawDataDeviceCom_Id').setValue(record.data.deviceId);
                            	Ext.getCmp('A9RawDataDeviceCom_Id').setRawValue(record.data.deviceId);
                            	
                            	Ext.getCmp('A9RawWaterCutDataGridPanel_Id').getStore().load();
                    		}
                        },
                    	select: function(grid, record, index, eOpts) {
                        	Ext.getCmp('A9RawDataListSelectRow_Id').setValue(index);
                        }
                    }
                });
            	Ext.getCmp("A9RawWaterCutDataListPanel_Id").add(gridPanel);
            }
            var startDate=Ext.getCmp('A9RawDataStartDate_Id').rawValue;
            if(startDate==''||null==startDate){
            	Ext.getCmp("A9RawDataStartDate_Id").setValue(get_rawData.start_date==undefined?get_rawData.startDate:get_rawData.start_date);
            }
            if(get_rawData.totalCount>0){
            	var gridPanel = Ext.getCmp("A9RawWaterCutDataGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getSelectionModel().deselectAll(true);
                	var A9RawDataListSelectRow=Ext.getCmp('A9RawDataListSelectRow_Id').getValue();
                	var selectedDiveceId = Ext.getCmp('A9RawDataDeviceCom_Id').getValue();
                	if(selectedDiveceId==null||selectedDiveceId==""){
                		gridPanel.getSelectionModel().select(parseInt(A9RawDataListSelectRow), true);
                	}else{
                		gridPanel.getSelectionModel().select(0, true);
                	}
                }
            }else{
            	$("#A9RawWaterCutDataCurveChartDiv1_Id").html('');
            	$("#A9RawWaterCutDataCurveChartDiv2_Id").html('');
            }
        },
        beforeload: function (store, options) {
            var deviceId = Ext.getCmp('A9RawDataDeviceCom_Id').getValue();
            var startDate=Ext.getCmp('A9RawDataStartDate_Id').rawValue;
            var endDate=Ext.getCmp('A9RawDataEndDate_Id').rawValue;
            var new_params = {
            	deviceId: deviceId,
                startDate:startDate,
                endDate:endDate
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});