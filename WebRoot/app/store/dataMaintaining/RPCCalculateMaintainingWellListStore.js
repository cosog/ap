Ext.define('AP.store.dataMaintaining.RPCCalculateMaintainingWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.RPCCalculateMaintainingWellListStore',
    fields: ['id','wellName'],
    autoLoad: true,
    pageSize: 10000,
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
            var gridPanel = Ext.getCmp("RPCCalculateMaintainingWellListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var column = createCalculateManagerWellListColumn(arrColumns);
                var newColumns = Ext.JSON.decode(column);
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "RPCCalculateMaintainingWellListGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: false,
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
                    		Ext.getCmp("selectedRPCDeviceId_global").setValue(deviceId);
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("RPCCalculateMaintainingDeviceListSelectRow_Id").setValue(index);
                    		
                    		var combDeviceName=Ext.getCmp('RPCCalculateMaintainingWellListComBox_Id').getValue();
                    		if(combDeviceName!=''){
                        		Ext.getCmp("selectedRPCDeviceId_global").setValue(record.data.id);
                    		}
                    		
                    		resetRPCCalculateMaintainingQueryParams();
                    		var activeId = Ext.getCmp("RPCCalculateMaintainingTabPanel").getActiveTab().id;
    	        			if(activeId=="RPCCalculateMaintainingPanel"){
    	        				var bbar=Ext.getCmp("RPCFESDiagramCalculateMaintainingBbar");
    	        				if (isNotVal(bbar)) {
    	        					if(bbar.getStore().isEmptyStore){
    	        						var RPCCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.RPCCalculateMaintainingDataStore');
    	        						bbar.setStore(RPCCalculateMaintainingDataStore);
    	        					}else{
    	        						bbar.getStore().loadPage(1);
    	        					}
    	        				}else{
    	        					Ext.create('AP.store.dataMaintaining.RPCCalculateMaintainingDataStore');
    	        				}
    	        			}else if(activeId=="RPCTotalCalculateMaintainingPanel"){
    	        				var gridPanel = Ext.getCmp("RPCTotalCalculateMaintainingDataGridPanel_Id");
    	        	            if (isNotVal(gridPanel)) {
    	        	            	gridPanel.getStore().loadPage(1);
    	        	            }else{
    	        	            	Ext.create("AP.store.dataMaintaining.RPCTotalCalculateMaintainingDataStore");
    	        	            }
    	        			}
                        }
                    }
                });
                var wellListPanel = Ext.getCmp("RPCCalculateMaintainingWellListPanel_Id");
                wellListPanel.add(gridPanel);
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
            	Ext.getCmp("RPCCalculateMaintainingDeviceListSelectRow_Id").setValue(-1);
            	var activeId = Ext.getCmp("RPCCalculateMaintainingTabPanel").getActiveTab().id;
    			if(activeId=="RPCCalculateMaintainingPanel"){
    				var bbar=Ext.getCmp("RPCFESDiagramCalculateMaintainingBbar");
    				if (isNotVal(bbar)) {
    					if(bbar.getStore().isEmptyStore){
    						var RPCCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.RPCCalculateMaintainingDataStore');
    						bbar.setStore(RPCCalculateMaintainingDataStore);
    					}else{
    						bbar.getStore().loadPage(1);
    					}
    				}else{
    					Ext.create('AP.store.dataMaintaining.RPCCalculateMaintainingDataStore');
    				}
    			}else if(activeId=="RPCTotalCalculateMaintainingPanel"){
    				var gridPanel = Ext.getCmp("RPCTotalCalculateMaintainingDataGridPanel_Id");
    	            if (isNotVal(gridPanel)) {
    	            	gridPanel.getStore().loadPage(1);
    	            }else{
    	            	Ext.create("AP.store.dataMaintaining.RPCTotalCalculateMaintainingDataStore");
    	            }
    			}
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var wellName = Ext.getCmp('RPCCalculateMaintainingWellListComBox_Id').getValue();
        	var deviceType=0;
            var calculateType=1;//1-抽油机井诊断计产 2-螺杆泵井诊断计产 3-抽油机井汇总计算  4-螺杆泵井汇总计算 5-电参反演地面功图计算
            var new_params = {
            		orgId: orgId,
            		wellName: wellName,
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