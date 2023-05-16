Ext.define('AP.store.dataMaintaining.PCPCalculateMaintainingWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.PCPCalculateMaintainingWellListStore',
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
            var gridPanel = Ext.getCmp("PCPCalculateMaintainingWellListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var column = createCalculateManagerWellListColumn(arrColumns);
                var newColumns = Ext.JSON.decode(column);
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "PCPCalculateMaintainingWellListGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: false,
//                    selType: 'checkboxmodel',
//                    multiSelect: false,
//                    selModel:{
//                    	selType: 'checkboxmodel',
//                    	mode:'SINGLE',//"SINGLE" / "SIMPLE" / "MULTI" 
//                    	checkOnly:false,
//                    	allowDeselect:true,
//                    	onHdMouseDown:function(e,t){
//                    		alert("全选/全不选");
//                    	}
//                    },
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
                    		Ext.getCmp("selectedPCPDeviceId_global").setValue(deviceId);
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("PCPCalculateMaintainingDeviceListSelectRow_Id").setValue(index);
                    		
                    		var combDeviceName=Ext.getCmp('PCPCalculateMaintainingWellListComBox_Id').getValue();
                    		if(combDeviceName!=''){
                        		Ext.getCmp("selectedPCPDeviceId_global").setValue(record.data.id);
                    		}
                    		
                    		resetPCPCalculateMaintainingQueryParams();
                    		var activeId = Ext.getCmp("PCPCalculateMaintainingTabPanel").getActiveTab().id;
    	        			if(activeId=="PCPCalculateMaintainingPanel"){
    	        				var bbar=Ext.getCmp("PCPFESDiagramCalculateMaintainingBbar");
    	        				if (isNotVal(bbar)) {
    	        					if(bbar.getStore().isEmptyStore){
    	        						var PCPCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
    	        						bbar.setStore(PCPCalculateMaintainingDataStore);
    	        					}else{
    	        						bbar.getStore().loadPage(1);
    	        					}
    	        				}else{
    	        					Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
    	        				}
    	        			}else if(activeId=="PCPTotalCalculateMaintainingPanel"){
    	        				var gridPanel = Ext.getCmp("PCPTotalCalculateMaintainingDataGridPanel_Id");
    	        	            if (isNotVal(gridPanel)) {
    	        	            	gridPanel.getStore().loadPage(1);
    	        	            }else{
    	        	            	Ext.create("AP.store.dataMaintaining.PCPTotalCalculateMaintainingDataStore");
    	        	            }
    	        			}
                        }
                    }
                });
                var wellListPanel = Ext.getCmp("PCPCalculateMaintainingWellListPanel_Id");
                wellListPanel.add(gridPanel);
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
            	Ext.getCmp("PCPCalculateMaintainingDeviceListSelectRow_Id").setValue(-1);
            	var activeId = Ext.getCmp("PCPCalculateMaintainingTabPanel").getActiveTab().id;
    			if(activeId=="PCPCalculateMaintainingPanel"){
    				var bbar=Ext.getCmp("PCPFESDiagramCalculateMaintainingBbar");
    				if (isNotVal(bbar)) {
    					if(bbar.getStore().isEmptyStore){
    						var PCPCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
    						bbar.setStore(PCPCalculateMaintainingDataStore);
    					}else{
    						bbar.getStore().loadPage(1);
    					}
    				}else{
    					Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
    				}
    			}else if(activeId=="PCPTotalCalculateMaintainingPanel"){
    				var gridPanel = Ext.getCmp("PCPTotalCalculateMaintainingDataGridPanel_Id");
    	            if (isNotVal(gridPanel)) {
    	            	gridPanel.getStore().loadPage(1);
    	            }else{
    	            	Ext.create("AP.store.dataMaintaining.PCPTotalCalculateMaintainingDataStore");
    	            }
    			}
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var wellName = Ext.getCmp('PCPCalculateMaintainingWellListComBox_Id').getValue();
        	var deviceType=1;
            var calculateType=2;//1-抽油机井诊断计产 2-螺杆泵井诊断计产 3-抽油机井汇总计算  4-螺杆泵井汇总计算 5-电参反演地面功图计算
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