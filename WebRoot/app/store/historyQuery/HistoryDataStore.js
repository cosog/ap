Ext.define('AP.store.historyQuery.HistoryDataStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.historyDataStore',
    fields: ['id','commStatus','commStatusName','wellName'],
    autoLoad: true,
    pageSize: 50,
    proxy: {
        type: 'ajax',
        url: context + '/historyQueryController/getDeviceHistoryData',
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
            var column = createHistoryQueryColumn(arrColumns);
            Ext.getCmp("HistoryQueryDataColumnStr_Id").setValue(column);
            Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(get_rawData.AlarmShowStyle));
            var gridPanel = Ext.getCmp("HistoryQueryDataGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = Ext.JSON.decode(column);
                var clickColumn={
                		text: '详细', 
                		dataIndex: 'details',
                		locked:true,
                		align:'center',
                		width:50,
                		renderer :function(value,e,o){
                			return iconHistoryQueryDetailsData(value,e,o)
                		} 
                };
                
                
                newColumns.splice(1, 0, clickColumn);
                var bbar = new Ext.PagingToolbar({
                	store: store,
                	displayInfo: true,
                	displayMsg: '当前 {0}~{1}条  共 {2} 条'
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "HistoryQueryDataGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    bbar: bbar,
                    columnLines: true,
                    forceFit: false,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	selectionchange: function (view, selected, o) {},
                    	itemdblclick: function (view,record,item,index,e,eOpts) {
                    		var HistoryQueryDataDetailsWindow = Ext.create("AP.view.historyQuery.HistoryQueryDataDetailsWindow");
                    		Ext.getCmp("HistoryQueryDataDetailsWindowRecord_Id").setValue(record.data.id);
                    		Ext.getCmp("HistoryQueryDataDetailsWindowDeviceId_Id").setValue(record.data.deviceId);
                    		Ext.getCmp("HistoryQueryDataDetailsWindowDeviceName_Id").setValue(record.data.wellName);
                    		HistoryQueryDataDetailsWindow.show();
//                    		CreateDeviceHistoryQueryDataTable(record.data.id,record.data.wellName);
                    	},
                    	select: function(grid, record, index, eOpts) {}
                    }
                });
                var panel = Ext.getCmp("HistoryQueryDataInfoPanel_Id");
                panel.add(gridPanel);
            }
            
            var startDate=Ext.getCmp('HistoryQueryStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(get_rawData.start_date.split(' ')[0]);
            	Ext.getCmp('HistoryQueryStartTime_Hour_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('HistoryQueryStartTime_Minute_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[1]);
            	Ext.getCmp('HistoryQueryStartTime_Second_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[2]);
            }
            var endDate=Ext.getCmp('HistoryQueryEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(get_rawData.end_date.split(' ')[0]);
            	Ext.getCmp('HistoryQueryEndTime_Hour_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('HistoryQueryEndTime_Minute_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[1]);
            	Ext.getCmp('HistoryQueryEndTime_Second_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[2]);
            }
            var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
            deviceHistoryQueryCurve(deviceType);
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceName='';
        	var deviceId=0;
        	var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
        	var selectRow= Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").getValue();
        	if(selectRow>=0){
        		deviceName = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
        		deviceId = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        	}
        	var startDate=Ext.getCmp('HistoryQueryStartDate_Id').rawValue;
        	var startTime_Hour=Ext.getCmp('HistoryQueryStartTime_Hour_Id').getValue();
        	var startTime_Minute=Ext.getCmp('HistoryQueryStartTime_Minute_Id').getValue();
        	var startTime_Second=Ext.getCmp('HistoryQueryStartTime_Second_Id').getValue();

            var endDate=Ext.getCmp('HistoryQueryEndDate_Id').rawValue;
            var endTime_Hour=Ext.getCmp('HistoryQueryEndTime_Hour_Id').getValue();
        	var endTime_Minute=Ext.getCmp('HistoryQueryEndTime_Minute_Id').getValue();
        	var endTime_Second=Ext.getCmp('HistoryQueryEndTime_Second_Id').getValue();
        	
            var new_params = {
            		orgId: orgId,
            		deviceType:deviceType,
            		deviceId:deviceId,
                    deviceName:deviceName,
                    startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
                    endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second)
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});