Ext.define('AP.store.dataMaintaining.SRPCalculateMaintainingWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.SRPCalculateMaintainingWellListStore',
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
            var gridPanel = Ext.getCmp("SRPCalculateMaintainingWellListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var column = createCalculateManagerWellListColumn(arrColumns);
                var newColumns = Ext.JSON.decode(column);
                var bbar = new Ext.PagingToolbar({
                	id:'SRPCalculateMaintainingDeviceListGridPagingToolbar',
                	store: store,
                	displayInfo: true
    	        });
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "SRPCalculateMaintainingWellListGridPanel_Id",
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
                    	selectionchange: function (view, selected, o) {
                    		
                    	},
                    	rowclick: function( grid, record, element, index, e, eOpts) {
                    		var deviceId=record.data.id;
                    		Ext.getCmp("selectedDeviceId_global").setValue(deviceId);
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("SRPCalculateMaintainingDeviceListSelectRow_Id").setValue(index);
                    		
                    		var combDeviceName=Ext.getCmp('SRPCalculateMaintainingWellListComBox_Id').getValue();
                    		if(combDeviceName!=''){
                        		Ext.getCmp("selectedDeviceId_global").setValue(record.data.id);
                    		}
                    		
                    		resetSRPCalculateMaintainingQueryParams();
                    		var activeId = Ext.getCmp("SRPCalculateMaintainingTabPanel").getActiveTab().id;
    	        			if(activeId=="SRPCalculateMaintainingPanel"){
    	        				var bbar=Ext.getCmp("SRPFESDiagramCalculateMaintainingBbar");
    	        				if (isNotVal(bbar)) {
    	        					if(bbar.getStore().isEmptyStore){
    	        						var SRPCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.SRPCalculateMaintainingDataStore');
    	        						bbar.setStore(SRPCalculateMaintainingDataStore);
    	        					}else{
    	        						bbar.getStore().loadPage(1);
    	        					}
    	        				}else{
    	        					Ext.create('AP.store.dataMaintaining.SRPCalculateMaintainingDataStore');
    	        				}
    	        			}else if(activeId=="SRPTotalCalculateMaintainingPanel"){
    	        				var gridPanel = Ext.getCmp("SRPTotalCalculateMaintainingDataGridPanel_Id");
    	        	            if (isNotVal(gridPanel)) {
    	        	            	gridPanel.getStore().loadPage(1);
    	        	            }else{
    	        	            	Ext.create("AP.store.dataMaintaining.SRPTotalCalculateMaintainingDataStore");
    	        	            }
    	        			}
                        }
                    }
                });
                var wellListPanel = Ext.getCmp("SRPCalculateMaintainingWellListPanel_Id");
                wellListPanel.add(gridPanel);
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
            	Ext.getCmp("SRPCalculateMaintainingDeviceListSelectRow_Id").setValue(-1);
            	var activeId = Ext.getCmp("SRPCalculateMaintainingTabPanel").getActiveTab().id;
    			if(activeId=="SRPCalculateMaintainingPanel"){
    				var bbar=Ext.getCmp("SRPFESDiagramCalculateMaintainingBbar");
    				if (isNotVal(bbar)) {
    					if(bbar.getStore().isEmptyStore){
    						var SRPCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.SRPCalculateMaintainingDataStore');
    						bbar.setStore(SRPCalculateMaintainingDataStore);
    					}else{
    						bbar.getStore().loadPage(1);
    					}
    				}else{
    					Ext.create('AP.store.dataMaintaining.SRPCalculateMaintainingDataStore');
    				}
    			}else if(activeId=="SRPTotalCalculateMaintainingPanel"){
    				var gridPanel = Ext.getCmp("SRPTotalCalculateMaintainingDataGridPanel_Id");
    	            if (isNotVal(gridPanel)) {
    	            	gridPanel.getStore().loadPage(1);
    	            }else{
    	            	Ext.create("AP.store.dataMaintaining.SRPTotalCalculateMaintainingDataStore");
    	            }
    			}
            }
            Ext.getCmp("CalculateMaintainingRootTabPanel").getEl().unmask();
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceName = Ext.getCmp('SRPCalculateMaintainingWellListComBox_Id').getValue();
        	var deviceType=getDeviceTypeFromTabId("CalculateMaintainingRootTabPanel");
        	var timeType = Ext.getCmp('SRPCalculateMaintainingTimeType_Id').getValue();
            var calculateType=1;//1-抽油机井诊断计产 2-螺杆泵井诊断计产 3-抽油机井汇总计算  4-螺杆泵井汇总计算 5-电参反演地面功图计算
            var new_params = {
            		orgId: orgId,
            		deviceName: deviceName,
                    deviceType:deviceType,
                    timeType:timeType,
                    calculateType:calculateType
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});