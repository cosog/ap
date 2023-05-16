Ext.define('AP.store.historyQuery.PCPHistoryQueryWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.pcpHistoryQueryWellListStore',
    fields: ['id','commStatus','commStatusName','wellName'],
    autoLoad: false,
    pageSize: 50,
    proxy: {
        type: 'ajax',
        url: context + '/historyQueryController/getHistoryQueryDeviceList',
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
            var column = createHistoryQueryDeviceListColumn(arrColumns);
            Ext.getCmp("PCPHistoryQueryWellListColumnStr_Id").setValue(column);
            Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(get_rawData.AlarmShowStyle));
            var gridPanel = Ext.getCmp("PCPHistoryQueryDeviceListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = Ext.JSON.decode(column);
                var bbar = new Ext.PagingToolbar({
                	id:'PCPHistoryQueryDeviceListGridPagingToolbar',
                	store: store,
                	displayInfo: true,
                	displayMsg: '共 {2}条'
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "PCPHistoryQueryDeviceListGridPanel_Id",
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
                    	},
                    	rowclick: function( grid, record, element, index, e, eOpts) {
                    		var deviceId=record.data.id;
                    		Ext.getCmp("selectedPCPDeviceId_global").setValue(deviceId);
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("PCPHistoryQueryInfoDeviceListSelectRow_Id").setValue(index);
                    		
                    		var combDeviceName=Ext.getCmp('HistoryQueryPCPDeviceListComb_Id').getValue();
                    		if(combDeviceName!=''){
                        		Ext.getCmp("selectedPCPDeviceId_global").setValue(record.data.id);
                    		}
                    		
                    		Ext.getCmp('PCPHistoryQueryStartDate_Id').setValue('');
                    		Ext.getCmp('PCPHistoryQueryStartDate_Id').setRawValue('');
                    		Ext.getCmp('PCPHistoryQueryEndDate_Id').setValue('');
                    		Ext.getCmp('PCPHistoryQueryEndDate_Id').setRawValue('');
                    		var PCPHistoryQueryDataGridPanel = Ext.getCmp("PCPHistoryQueryDataGridPanel_Id");
                            if (isNotVal(PCPHistoryQueryDataGridPanel)) {
                            	PCPHistoryQueryDataGridPanel.getStore().loadPage(1);
                            }else{
                            	Ext.create("AP.store.historyQuery.PCPHistoryDataStore");
                            }
                    	}
                    }
                });
                var panel = Ext.getCmp("PCPHistoryQueryInfoDeviceListPanel_Id");
                panel.add(gridPanel);
            }
            if(get_rawData.totalCount>0){
            	var selectRow=0;
            	var selectedDeviceId=parseInt(Ext.getCmp("selectedPCPDeviceId_global").getValue());
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
            	Ext.getCmp("PCPHistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
            	var PCPHistoryQueryDataGridPanel = Ext.getCmp("PCPHistoryQueryDataGridPanel_Id");
                if (isNotVal(PCPHistoryQueryDataGridPanel)) {
                	PCPHistoryQueryDataGridPanel.getStore().loadPage(1);
                }else{
                	Ext.create("AP.store.historyQuery.PCPHistoryDataStore");
                }
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceName=Ext.getCmp('HistoryQueryPCPDeviceListComb_Id').getValue();
        	var commStatusStatValue=Ext.getCmp("PCPHistoryQueryStatSelectCommStatus_Id").getValue();
        	var runStatusStatValue=Ext.getCmp("PCPHistoryQueryStatSelectRunStatus_Id").getValue();
			var deviceTypeStatValue=Ext.getCmp("PCPHistoryQueryStatSelectDeviceType_Id").getValue();
            var new_params = {
                    orgId: orgId,
                    deviceType:1,
                    deviceName:deviceName,
                    commStatusStatValue:commStatusStatValue,
                    runStatusStatValue:runStatusStatValue,
                    deviceTypeStatValue:deviceTypeStatValue
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});