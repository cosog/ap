Ext.define('AP.store.dataMaintaining.AcquisitionDataMaintainingWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.AcquisitionDataMaintainingWellListStore',
    fields: ['id','deviceName'],
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/calculateManagerController/getWellList',
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
            var gridPanel = Ext.getCmp("AcquisitionDataMaintainingDeviceListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var column = createCalculateManagerWellListColumn(arrColumns);
                var newColumns = Ext.JSON.decode(column);
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "AcquisitionDataMaintainingDeviceListGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: false,
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
                    		Ext.getCmp("AcquisitionDataMaintainingDeviceListSelectRow_Id").setValue(index);
                    		
                    		var combDeviceName=Ext.getCmp('PCPCalculateMaintainingWellListComBox_Id').getValue();
                    		if(combDeviceName!=''){
                        		Ext.getCmp("selectedDeviceId_global").setValue(record.data.id);
                    		}
                    		
                    		resetAcquisitionDataMaintainingQueryParams();
                    		var activeId = Ext.getCmp("AcquisitionDataMaintainingTabPanel").getActiveTab().id;
    	        			if(activeId=="RealtimeAcquisitionDataMaintainingPanel"){
    	        				var bbar=Ext.getCmp("RealtimeAcquisitionDataMaintainingBbar");
    	        				if (isNotVal(bbar)) {
    	        					if(bbar.getStore().isEmptyStore){
    	        						var RealtimeAcquisitionDataMaintainingDataStore=Ext.create('AP.store.dataMaintaining.RealtimeAcquisitionDataMaintainingDataStore');
    	        						bbar.setStore(RealtimeAcquisitionDataMaintainingDataStore);
    	        					}else{
    	        						bbar.getStore().loadPage(1);
    	        					}
    	        				}else{
    	        					Ext.create('AP.store.dataMaintaining.RealtimeAcquisitionDataMaintainingDataStore');
    	        				}
    	        			}else if(activeId=="HistoryAcquisitionDataMaintainingPanel"){
    	        				var bbar=Ext.getCmp("HistoryAcquisitionDataMaintainingBbar");
    	        				if (isNotVal(bbar)) {
    	        					if(bbar.getStore().isEmptyStore){
    	        						var HistoryAcquisitionDataMaintainingDataStore=Ext.create('AP.store.dataMaintaining.HistoryAcquisitionDataMaintainingDataStore');
    	        						bbar.setStore(HistoryAcquisitionDataMaintainingDataStore);
    	        					}else{
    	        						bbar.getStore().loadPage(1);
    	        					}
    	        				}else{
    	        					Ext.create('AP.store.dataMaintaining.HistoryAcquisitionDataMaintainingDataStore');
    	        				}
    	        			}
                        }
                    }
                });
                var deviceListPanel = Ext.getCmp("AcquisitionDataMaintainingDeviceListPanel_Id");
                deviceListPanel.add(gridPanel);
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
            	Ext.getCmp("AcquisitionDataMaintainingDeviceListSelectRow_Id").setValue(-1);
            	
//            	var activeId = Ext.getCmp("AcquisitionDataMaintainingTabPanel").getActiveTab().id;
//    			if(activeId=="RealtimeAcquisitionDataMaintainingPanel"){
//    				var bbar=Ext.getCmp("RealtimeAcquisitionDataMaintainingBbar");
//    				if (isNotVal(bbar)) {
//    					if(bbar.getStore().isEmptyStore){
//    						var RealtimeAcquisitionDataMaintainingDataStore=Ext.create('AP.store.dataMaintaining.RealtimeAcquisitionDataMaintainingDataStore');
//    						bbar.setStore(RealtimeAcquisitionDataMaintainingDataStore);
//    					}else{
//    						bbar.getStore().loadPage(1);
//    					}
//    				}else{
//    					Ext.create('AP.store.dataMaintaining.RealtimeAcquisitionDataMaintainingDataStore');
//    				}
//    			}else if(activeId=="HistoryAcquisitionDataMaintainingPanel"){
//    				var bbar=Ext.getCmp("HistoryAcquisitionDataMaintainingBbar");
//    				if (isNotVal(bbar)) {
//    					if(bbar.getStore().isEmptyStore){
//    						var HistoryAcquisitionDataMaintainingDataStore=Ext.create('AP.store.dataMaintaining.HistoryAcquisitionDataMaintainingDataStore');
//    						bbar.setStore(HistoryAcquisitionDataMaintainingDataStore);
//    					}else{
//    						bbar.getStore().loadPage(1);
//    					}
//    				}else{
//    					Ext.create('AP.store.dataMaintaining.HistoryAcquisitionDataMaintainingDataStore');
//    				}
//    			}
            }
            Ext.getCmp("CalculateMaintainingRootTabPanel").getEl().unmask();
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceName = Ext.getCmp('AcquisitionDataMaintainingDeviceListComBox_Id').getValue();
        	var deviceType=getDeviceTypeFromTabId("CalculateMaintainingRootTabPanel");
            var calculateType=0;
            var new_params = {
            		orgId: orgId,
            		deviceName: deviceName,
                    deviceType:deviceType,
                    calculateType:calculateType
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});