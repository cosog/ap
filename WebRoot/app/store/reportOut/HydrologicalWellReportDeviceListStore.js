Ext.define('AP.store.reportOut.HydrologicalWellReportDeviceListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.HydrologicalWellReportDeviceListStore',
    fields: ['id','deviceName'],
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/reportDataMamagerController/getHydrologicalWellDeviceList',
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
            var gridPanel = Ext.getCmp("HydrologicalWellReportDeviceListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var column = createHydrologicalWellReportDeviceListDataColumn(arrColumns);
                var newColumns = Ext.JSON.decode(column);
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "HydrologicalWellReportDeviceListGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: true,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>"
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
                    		Ext.getCmp("HydrologicalWellReportDeviceListSelectRow_Id").setValue(index);
                    		
                    		var combDeviceName=Ext.getCmp('HydrologicalWellReportPanelDeviceListCombo_Id').getValue();
                    		if(combDeviceName!=''){
                        		Ext.getCmp("selectedDeviceId_global").setValue(record.data.id);
                    		}
                    		
                    		CreateHydrologicalWellReportTable();
                    		CreateHydrologicalWellReportCurve();
                        }
                    }
                });
                var HydrologicalWellReportDeviceListPanel = Ext.getCmp("HydrologicalWellReportDeviceListPanel_Id");
                HydrologicalWellReportDeviceListPanel.add(gridPanel);
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
            	Ext.getCmp("HydrologicalWellReportDeviceListSelectRow_Id").setValue(-1);
            	
//            	if(singleWellDailyReportHelper!=null){
//        			if(singleWellDailyReportHelper.hot!=undefined){
//        				singleWellDailyReportHelper.hot.destroy();
//        			}
//        			singleWellDailyReportHelper=null;
//        		}
//            	$("#SingleWellDailyReportDiv_id").html('');
//                $("#SingleWellDailyReportCurveDiv_Id").html('');
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceName = Ext.getCmp('HydrologicalWellReportPanelDeviceListCombo_Id').getValue();
            var new_params = {
                    orgId: orgId,
                    deviceName:deviceName
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        }
    }
});