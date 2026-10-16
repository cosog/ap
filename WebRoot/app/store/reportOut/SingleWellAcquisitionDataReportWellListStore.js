Ext.define('AP.store.reportOut.SingleWellAcquisitionDataReportWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.singleWellAcquisitionDataReportWellListStore',
    fields: ['id','deviceName'],
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/reportDataMamagerController/getAcquisitionDataReportDeviceList',
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
        	if(Ext.getCmp("SingleWellAcquisitionDataReportWellListPanel_Id")!=undefined){
                //获得列表数
                var get_rawData = store.proxy.reader.rawData;
                var arrColumns = get_rawData.columns;
                var gridPanel = Ext.getCmp("SingleWellAcquisitionDataReportDeviceListGridPanel_Id");
                if (!isNotVal(gridPanel)) {
                    var column = createSingleWellAcquisitionDataReportWellListDataColumn(arrColumns);
                    var newColumns = Ext.JSON.decode(column);
                    gridPanel = Ext.create('Ext.grid.Panel', {
                        id: "SingleWellAcquisitionDataReportDeviceListGridPanel_Id",
                        border: false,
                        autoLoad: false,
                        columnLines: true,
                        forceFit: true,
                        viewConfig: {
                        	emptyText: "<div class='con_div_' id='div_dataactiveid'>" + Ext.String.htmlEncode("<" + loginUserLanguageResource.emptyMsg + ">") + "</div>"
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
                        		Ext.getCmp("SingleWellAcquisitionDataReportDeviceListSelectRow_Id").setValue(index);
                        		
                        		var combDeviceName=Ext.getCmp('AcquisitionDataReportPanelDeviceListCombo_Id').getValue();
                        		if(combDeviceName!=''){
                            		Ext.getCmp("selectedDeviceId_global").setValue(record.data.id);
                        		}
                        		
                        		CreateSingleWellAcquisitionDataReportTable();
                        		CreateSingleWellAcquisitionDataReportCurve();
                            }
                        }
                    });
                    var deviceListPanel = Ext.getCmp("SingleWellAcquisitionDataReportWellListPanel_Id");
                    
                    if(isNotVal(deviceListPanel)){
                    	deviceListPanel.add(gridPanel);
                    }
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
                	Ext.getCmp("SingleWellAcquisitionDataReportDeviceListSelectRow_Id").setValue(-1);
                	
                	if(singleWellAcquisitionDataReportHelper!=null){
            			if(singleWellAcquisitionDataReportHelper.hot!=undefined){
            				singleWellAcquisitionDataReportHelper.hot.destroy();
            			}
            			singleWellAcquisitionDataReportHelper=null;
            		}
                	$("#SingleWellAcquisitionDataReportDiv_id").html('');
                    $("#SingleWellAcquisitionDataReportCurveDiv_Id").html('');
                }
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceName = Ext.getCmp('AcquisitionDataReportPanelDeviceListCombo_Id').getValue();
        	var deviceType=getDeviceTypeFromTabId("AcquisitionDataReportRootTabPanel");
            var new_params = {
                    orgId: orgId,
                    deviceName:deviceName,
                    deviceType:deviceType
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        }
    }
});