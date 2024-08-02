Ext.define('AP.store.reportOut.SingleWellDailyReportWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.SingleWellDailyReportWellListStore',
    fields: ['id','deviceName'],
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/reportDataMamagerController/getDeviceList',
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
            var gridPanel = Ext.getCmp("SingleWellDailyReportGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var column = createSingleWellDailyReportWellListDataColumn(arrColumns);
                var newColumns = Ext.JSON.decode(column);
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "SingleWellDailyReportGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: true,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	selectionchange: function (view, selected, o) {
                    	},
                    	rowclick: function( grid, record, element, index, e, eOpts) {
                    		var deviceId=record.data.id;
                    		Ext.getCmp("selectedDeviceId_global").setValue(deviceId);
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("SingleWellDailyReportDeviceListSelectRow_Id").setValue(index);
                    		
                    		var combDeviceName=Ext.getCmp('SingleWellDailyReportPanelWellListCombo_Id').getValue();
                    		if(combDeviceName!=''){
                        		Ext.getCmp("selectedDeviceId_global").setValue(record.data.id);
                    		}
                    		
                    		CreateSingleWellReportTable();
                    		CreateSingleWellReportCurve();
                        }
                    }
                });
                var SingleWellDailyReportWellListPanel = Ext.getCmp("SingleWellDailyReportWellListPanel_Id");
                SingleWellDailyReportWellListPanel.add(gridPanel);
            }
            if(get_rawData.totalCount>0){
            	var selectRow=0;
            	var selectedDeviceId=parseInt(Ext.getCmp("selectedDeviceId_global").getValue());
    			if(selectedDeviceId>0){
    				for(var i=0;i<store.data.items.length;i++){
            			if(selectedDeviceId==store.data.items[i].data.id){
            				selectRow=i;
            				break;
            			}
            		}
    			}
            	gridPanel.getSelectionModel().deselectAll(true);
            	gridPanel.getSelectionModel().select(selectRow, true);
            }else{
            	Ext.getCmp("SingleWellDailyReportDeviceListSelectRow_Id").setValue(-1);
            	
            	var SingleWellReportTabPanelActiveId=Ext.getCmp("SingleWellReportTabPanel_Id").getActiveTab().id;
            	if(SingleWellReportTabPanelActiveId=='SingleWellDailyReportTabPanel_id'){
            		if(singleWellDailyReportHelper!=null){
            			if(singleWellDailyReportHelper.hot!=undefined){
            				singleWellDailyReportHelper.hot.destroy();
            			}
            			singleWellDailyReportHelper=null;
            		}
                	$("#SingleWellDailyReportDiv_id").html('');
                    $("#SingleWellDailyReportCurveDiv_Id").html('');
            	}else if(SingleWellReportTabPanelActiveId=='SingleWellRangeReportTabPanel_id'){
            		if(singleWellRangeReportHelper!=null){
            			if(singleWellRangeReportHelper.hot!=undefined){
            				singleWellRangeReportHelper.hot.destroy();
            			}
            			singleWellRangeReportHelper=null;
            		}
                	$("#SingleWellRangeReportDiv_id").html('');
                    $("#SingleWellRangeReportCurveDiv_Id").html('');
            	}
            }
            Ext.getCmp("ProductionReportRootTabPanel").getEl().unmask();
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceName = Ext.getCmp('SingleWellDailyReportPanelWellListCombo_Id').getValue();
        	var deviceType=getDeviceTypeFromTabId("ProductionReportRootTabPanel");
            var new_params = {
                    orgId: orgId,
                    deviceName:deviceName,
                    deviceType:deviceType
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        }
    }
});