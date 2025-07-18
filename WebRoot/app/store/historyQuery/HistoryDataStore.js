Ext.define('AP.store.historyQuery.HistoryDataStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.historyDataStore',
    fields: ['id','commStatus','commStatusName','wellName'],
    autoLoad: true,
    pageSize: defaultPageSize,
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
        	Ext.getCmp("HistoryQueryDataInfoPanel_Id").getEl().unmask();
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
                		text: loginUserLanguageResource.details, 
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
//                	displayMsg: '当前 {0}~{1}条  共 {2} 条',
                	displayInfo: true
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "HistoryQueryDataGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    bbar: bbar,
                    columnLines: true,
                    forceFit: false,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>"
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
            }
            var endDate=Ext.getCmp('HistoryQueryEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(get_rawData.end_date.split(' ')[0]);
            	Ext.getCmp('HistoryQueryEndTime_Hour_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('HistoryQueryEndTime_Minute_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[1]);
            }
            if(store.currentPage==1){
            	var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
                deviceHistoryQueryCurve(deviceType);
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceName='';
        	var deviceId=0;
        	var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
        	var calculateType=0;
        	var selectRow= Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").getValue();
        	if(Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
        		deviceName = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
        		deviceId = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        		calculateType = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.calculateType;
        	}
        	var startDate=Ext.getCmp('HistoryQueryStartDate_Id').rawValue;
        	var startTime_Hour=Ext.getCmp('HistoryQueryStartTime_Hour_Id').getValue();
        	var startTime_Minute=Ext.getCmp('HistoryQueryStartTime_Minute_Id').getValue();
        	var startTime_Second=0;

            var endDate=Ext.getCmp('HistoryQueryEndDate_Id').rawValue;
            var endTime_Hour=Ext.getCmp('HistoryQueryEndTime_Hour_Id').getValue();
        	var endTime_Minute=Ext.getCmp('HistoryQueryEndTime_Minute_Id').getValue();
        	var endTime_Second=0;
        	
        	Ext.getCmp("HistoryQueryDataInfoPanel_Id").el.mask(loginUserLanguageResource.loading).show();
        	
        	var hours=getHistoryQueryHours();
        	
        	var totalCount=0;
        	if(store.totalCount!=undefined){
        		totalCount=store.totalCount;
        	}
        	
            var new_params = {
            		orgId: orgId,
            		deviceType:deviceType,
            		deviceId:deviceId,
                    deviceName:deviceName,
                    calculateType:calculateType,
                    startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
                    endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),
                    hours:hours,
                    totalCount:totalCount
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});