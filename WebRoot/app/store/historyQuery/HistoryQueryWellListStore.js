Ext.define('AP.store.historyQuery.HistoryQueryWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.historyQueryWellListStore',
    fields: ['id','commStatus','commStatusName','wellName'],
    autoLoad: false,
    pageSize: defaultPageSize,
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
            var column = createHistoryDeviceOverviewColumnObject(arrColumns);
            Ext.getCmp("HistoryQueryWellListColumnStr_Id").setValue(JSON.stringify(column));
            Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(get_rawData.AlarmShowStyle));
            var gridPanel = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = column;
                var bbar = new Ext.PagingToolbar({
                	id:'HistoryQueryDeviceListGridPagingToolbar',
                	store: store,
//                	displayMsg: '共 {2}条',
                	displayInfo: true
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "HistoryQueryDeviceListGridPanel_Id",
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
                    	},
                    	rowclick: function( grid, record, element, index, e, eOpts) {
                    		var deviceId=record.data.id;
                    		Ext.getCmp("selectedDeviceId_global").setValue(deviceId);
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").setValue(index);
                    		
                    		updateDeviceHistoryQueryData(record);
                    	}
                    }
                });
                var panel = Ext.getCmp("HistoryQueryInfoDeviceListPanel_Id");
                panel.add(gridPanel);
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
            	Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
            	
//            	var tabPanel = Ext.getCmp("HistoryQueryCenterTabPanel");
//            	var activeId = tabPanel.getActiveTab()!=undefined?tabPanel.getActiveTab().id:'';
//				if(activeId=="HistoryDataTabPanel"){
//					tabPanel.remove("HistoryQueryTiledDiagramPanel");
//					tabPanel.remove("HistoryDiagramOverlayTabPanel");
//					
//					var HistoryQueryDataGridPanel = Ext.getCmp("HistoryQueryDataGridPanel_Id");
//                    if (isNotVal(HistoryQueryDataGridPanel)) {
//                    	HistoryQueryDataGridPanel.getStore().loadPage(1);
//                    }else{
//                    	Ext.create("AP.store.historyQuery.HistoryDataStore");
//                    }
//				}else{
//					tabPanel.remove("HistoryQueryTiledDiagramPanel");
//					tabPanel.remove("HistoryDiagramOverlayTabPanel");
//				}
//				
//				tabPanel.hide();
            	
            	var record={
            			data:{
            				deviceName:'',
            				deviceId:0
            			}
            	};
            	updateDeviceHistoryQueryData(record);
            }
            Ext.getCmp("HistoryQueryRootTabPanel").getEl().unmask();
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceName=Ext.getCmp('HistoryQueryDeviceListComb_Id').getValue();
        	var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
        	var FESdiagramResultStatValue=Ext.getCmp("HistoryQueryStatSelectFESdiagramResult_Id").getValue();
        	var commStatusStatValue=Ext.getCmp("HistoryQueryStatSelectCommStatus_Id").getValue();
        	var runStatusStatValue=Ext.getCmp("HistoryQueryStatSelectRunStatus_Id").getValue();
			var deviceTypeStatValue=Ext.getCmp("HistoryQueryStatSelectDeviceType_Id").getValue();
			var dictDeviceType=deviceType;
        	if(dictDeviceType.includes(",")){
        		dictDeviceType=getDeviceTypeFromTabId_first("HistoryQueryRootTabPanel");
        	}
            var new_params = {
                    orgId: orgId,
                    deviceType:deviceType,
                    deviceName:deviceName,
                    dictDeviceType:dictDeviceType,
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