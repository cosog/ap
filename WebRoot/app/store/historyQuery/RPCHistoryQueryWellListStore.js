Ext.define('AP.store.historyQuery.RPCHistoryQueryWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.rpcHistoryQueryWellListStore',
    fields: ['id','commStatus','commStatusName','wellName'],
    autoLoad: true,
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
            Ext.getCmp("RPCHistoryQueryWellListColumnStr_Id").setValue(column);
            Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(get_rawData.AlarmShowStyle));
            var gridPanel = Ext.getCmp("RPCHistoryQueryDeviceListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = Ext.JSON.decode(column);
                var bbar = new Ext.PagingToolbar({
                	store: store,
                	displayInfo: true,
                	displayMsg: '共 {2}条'
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "RPCHistoryQueryDeviceListGridPanel_Id",
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
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("RPCHistoryQueryInfoDeviceListSelectRow_Id").setValue(index);
                    		Ext.getCmp('RPCHistoryQueryStartDate_Id').setValue('');
                    		Ext.getCmp('RPCHistoryQueryStartDate_Id').setRawValue('');
                    		Ext.getCmp('RPCHistoryQueryEndDate_Id').setValue('');
                    		Ext.getCmp('RPCHistoryQueryEndDate_Id').setRawValue('');
                    		
                    		var tabPanel = Ext.getCmp("RPCHistoryQueryTabPanel");
            				var activeId = tabPanel.getActiveTab().id;
            				if(activeId=="RPCHistoryDataTabPanel"){
        						Ext.getCmp("RPCHistoryDataExportBtn_Id").show();
        						Ext.getCmp("SurfaceCardTotalCount_Id").hide();
        						var gridPanel = Ext.getCmp("RPCHistoryQueryDataGridPanel_Id");
                                if (isNotVal(gridPanel)) {
                                	gridPanel.getStore().loadPage(1);
                                }else{
                                	Ext.create("AP.store.historyQuery.RPCHistoryDataStore");
                                }
        					}else if(activeId=="RPCHistoryDiagramTabPanel"){
        						Ext.getCmp("RPCHistoryDataExportBtn_Id").hide();
        						Ext.getCmp("SurfaceCardTotalCount_Id").show();
        						loadSurfaceCardList(1);
        					}else if(activeId=="RPCHistoryDiagramOverlayTabPanel"){
        						Ext.getCmp("RPCHistoryDataExportBtn_Id").hide();
        						Ext.getCmp("SurfaceCardTotalCount_Id").hide();
        						
        						var gridPanel = Ext.getCmp("RPCHistoryQueryFSdiagramOverlayGrid_Id");
                                if (isNotVal(gridPanel)) {
                                	gridPanel.getStore().load();
                                }else{
                                	Ext.create("AP.store.historyQuery.RPCHistoryQueryDiagramOverlayStore");
                                }
        					}
                    	}
                    }
                });
                var panel = Ext.getCmp("RPCHistoryQueryInfoDeviceListPanel_Id");
                panel.add(gridPanel);
            }
            if(get_rawData.totalCount>0){
            	gridPanel.getSelectionModel().deselectAll(true);
            	gridPanel.getSelectionModel().select(0, true);
            }else{
            	Ext.getCmp("RPCHistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
            	
            	
				var activeId = Ext.getCmp("RPCHistoryQueryTabPanel").getActiveTab().id;
				if(activeId=="RPCHistoryDataTabPanel"){
					var gridPanel = Ext.getCmp("RPCHistoryQueryDataGridPanel_Id");
                    if (isNotVal(gridPanel)) {
                    	gridPanel.getStore().loadPage(1);
                    }else{
                    	Ext.create("AP.store.historyQuery.RPCHistoryDataStore");
                    }
				}else if(activeId=="RPCHistoryDiagramTabPanel"){
					loadSurfaceCardList(1);
				}else if(activeId=="RPCHistoryDiagramOverlayTabPanel"){
					var gridPanel = Ext.getCmp("RPCHistoryQueryFSdiagramOverlayGrid_Id");
                    if (isNotVal(gridPanel)) {
                    	gridPanel.getStore().load();
                    }else{
                    	Ext.create("AP.store.historyQuery.RPCHistoryQueryDiagramOverlayStore");
                    }
				}
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceName=Ext.getCmp('HistoryQueryRPCDeviceListComb_Id').getValue();
        	var FESdiagramResultStatValue=Ext.getCmp("RPCHistoryQueryStatSelectFESdiagramResult_Id").getValue();
        	var commStatusStatValue=Ext.getCmp("RPCHistoryQueryStatSelectCommStatus_Id").getValue();
        	var runStatusStatValue=Ext.getCmp("RPCHistoryQueryStatSelectRunStatus_Id").getValue();
			var deviceTypeStatValue=Ext.getCmp("RPCHistoryQueryStatSelectDeviceType_Id").getValue();
            var new_params = {
                    orgId: orgId,
                    deviceType:0,
                    deviceName:deviceName,
                    FESdiagramResultStatValue:FESdiagramResultStatValue,
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