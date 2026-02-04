Ext.define('AP.store.realTimeMonitoring.RealTimeMonitoringWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.realTimeMonitoringWellListStore',
    fields: ['id','commStatus','commStatusName','wellName'],
    autoLoad: false,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/realTimeMonitoringController/getDeviceRealTimeOverview',
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
            var column = createRealTimeMonitoringColumnObject(arrColumns);
            Ext.getCmp("RealTimeMonitoringColumnStr_Id").setValue(JSON.stringify(column));
            Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(get_rawData.AlarmShowStyle));
            var gridPanel = Ext.getCmp("RealTimeMonitoringListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = column;
                var bbar = new Ext.PagingToolbar({
                	id:'RealTimeMonitoringListGridPagingToolbar',
                	store: store,
//                	displayMsg: '共 {2}条',
                	displayInfo: true
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "RealTimeMonitoringListGridPanel_Id",
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
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(index);
                    		updateDeviceMonitoringData(record);
                    	},
//                    	selectionchange: function(model, selectedRecords) {
//                    		if(selectedRecords.length>0){
//                    			const firstSelected = selectedRecords[0];
//                        	    const store = gridPanel.getStore();
//                        	    const rowIndex = store.indexOf(firstSelected);
//                        	    Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(rowIndex);
//                        	    updateDeviceMonitoringData(firstSelected);
//                    		}
//                    		
//                    	},
                    	itemdblclick: function (view,record,item,index,e,eOpts) {
//                    		const selModel = gridPanel.getSelectionModel();
//                    	    const isSelected = selModel.isSelected(record);
//                    		alert(isSelected);
                    		
                    		Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(index);
                    		gotoDeviceHistory(record.data.id,record.data.deviceName,record.data.deviceType);
                    	}
                    }
                });
                var RealTimeMonitoringInfoDeviceListPanel = Ext.getCmp("RealTimeMonitoringInfoDeviceListPanel_Id");
                RealTimeMonitoringInfoDeviceListPanel.add(gridPanel);
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
            	Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(-1);
            	var record={
            			data:{
            				deviceName:'',
            				id:0,
            				videoUrl1:'',
            				videoUrl2:'',
            				videoKeyId1:0,
            				videoKeyId2:0
            			}
            	};
            	updateDeviceMonitoringData(record);
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
        	var deviceTypeName=getTabPanelActiveName("RealTimeMonitoringTabPanel");
        	var deviceName = Ext.getCmp('RealTimeMonitoringDeviceListComb_Id').getValue();
        	var FESdiagramResultStatValue=Ext.getCmp("RealTimeMonitoringStatSelectFESdiagramResult_Id").getValue();
        	var commStatusStatValue=Ext.getCmp("RealTimeMonitoringStatSelectCommStatus_Id").getValue();
        	var runStatusStatValue=Ext.getCmp("RealTimeMonitoringStatSelectRunStatus_Id").getValue();
        	var numStatusStatValue=Ext.getCmp("RealTimeMonitoringStatSelectNumStatus_Id").getValue();
        	var deviceTypeStatValue=Ext.getCmp("RealTimeMonitoringStatSelectDeviceType_Id").getValue();
        	
        	var dictDeviceType=deviceType;
        	if(dictDeviceType.includes(",")){
        		dictDeviceType=getDeviceTypeFromTabId_first("RealTimeMonitoringTabPanel");
        	}
        	
        	
            var new_params = {
                    orgId: orgId,
                    deviceType:deviceType,
                    deviceTypeName:deviceTypeName,
                    deviceName:deviceName,
                    dictDeviceType:dictDeviceType,
                    FESdiagramResultStatValue:FESdiagramResultStatValue,
                    commStatusStatValue:commStatusStatValue,
                    runStatusStatValue:runStatusStatValue,
                    numStatusStatValue:numStatusStatValue,
                    deviceTypeStatValue:deviceTypeStatValue
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});