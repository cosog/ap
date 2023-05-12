Ext.define('AP.store.reportOut.RPCSingleWellDailyReportWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.RPCSingleWellDailyReportWellListStore',
    fields: ['id','wellName'],
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/reportDataMamagerController/getWellList',
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
            var gridPanel = Ext.getCmp("RPCSingleWellDailyReportGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var column = createRPCSingleWellDailyReportWellListDataColumn(arrColumns);
                var newColumns = Ext.JSON.decode(column);
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "RPCSingleWellDailyReportGridPanel_Id",
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
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("RPCSingleWellDailyReportDeviceListSelectRow_Id").setValue(index);
                    		var deviceId=record.data.id;
                    		Ext.getCmp("selectedRPCDeviceId_global").setValue(deviceId);
                    		CreateRPCSingleWellDailyReportTable();
                    		CreateRPCSingleWellDailyReportCurve();
                        }
                    }
                });
                var RPCSingleWellDailyReportWellListPanel = Ext.getCmp("RPCSingleWellDailyReportWellListPanel_Id");
                RPCSingleWellDailyReportWellListPanel.add(gridPanel);
            }
            if(get_rawData.totalCount>0){
            	var selectRow=0;
            	var selectedDeviceId=parseInt(Ext.getCmp("selectedRPCDeviceId_global").getValue());
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
            	Ext.getCmp("RPCSingleWellDailyReportDeviceListSelectRow_Id").setValue(-1);
            	Ext.getCmp("selectedRPCDeviceId_global").setValue(0);
            	if(rpcSingleWellDailyReportHelper!=null){
    				if(rpcSingleWellDailyReportHelper.hot!=undefined){
    					rpcSingleWellDailyReportHelper.hot.destroy();
    				}
    				rpcSingleWellDailyReportHelper=null;
    			}
            	$("#RPCSingleWellDailyReportDiv_id").html('');
                $("#RPCSingleWellDailyReportCurveDiv_Id").html('');
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var wellName = Ext.getCmp('RPCSingleWellDailyReportPanelWellListCombo_Id').getValue();
            var new_params = {
                    orgId: orgId,
                    deviceType:0
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        }
    }
});