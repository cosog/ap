var deviceInfoHandsontableHelper = null;
var pumpingModelHandsontableHelper = null;
var productionHandsontableHelper = null;
var pumpingInfoHandsontableHelper = null;
var videoInfoHandsontableHelper = null;
Ext.define('AP.view.well.DeviceInfoPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.deviceInfoPanel',
    id: 'DeviceInfoPanel_Id',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var deviceListCombStore = new Ext.data.JsonStore({
            pageSize: defaultWellComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/wellInformationManagerController/loadWellComboxList',
                type: "ajax",
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json',
                    rootProperty: 'list',
                    totalProperty: 'totals'
                }
            },
            autoLoad: true,
            listeners: {
                beforeload: function (store, options) {
                    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var wellName='';
                    if(isNotVal(Ext.getCmp('deviceListComb_Id'))){
                    	wellName = Ext.getCmp('deviceListComb_Id').getValue();
                    }
                    
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceType: 101,
                        wellName: wellName
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });

        var deviceListDeviceCombo = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.wellName,
                id: "deviceListComb_Id",
                labelWidth: 35,
                width: 145,
                labelAlign: 'left',
                queryMode: 'remote',
                typeAhead: true,
                store: deviceListCombStore,
                autoSelect: false,
                editable: true,
                triggerAction: 'all',
                displayField: "boxval",
                valueField: "boxkey",
                pageSize: comboxPagingStatus,
                minChars: 0,
                emptyText: cosog.string.all,
                blankText: cosog.string.all,
                listeners: {
                    expand: function (sm, selections) {
                    	deviceListDeviceCombo.getStore().loadPage(1); // 加载井下拉框的store
                    },
                    select: function (combo, record, index) {
                        try {
                            CreateAndLoadDeviceInfoTable();
                        } catch (ex) {
                            Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                        }
                    }
                }
            });
        
        Ext.apply(this, {
            tbar: [{
                xtype: 'button',
                text: cosog.string.refresh,
                iconCls: 'note-refresh',
                hidden:false,
                handler: function (v, o) {
                	CreateAndLoadDeviceInfoTable();
                }
    		},'-',deviceListDeviceCombo,{
                id: 'DeviceSelectRow_Id',
                xtype: 'textfield',
                value: 0,
                hidden: true
            },{
                id: 'DeviceSelectEndRow_Id',
                xtype: 'textfield',
                value: 0,
                hidden: true
            }, '-', {
                xtype: 'button',
                text: cosog.string.search,
                iconCls: 'search',
                hidden: false,
                handler: function (v, o) {
                    CreateAndLoadDeviceInfoTable();
                }
            },'-',{
                xtype: 'button',
                text: cosog.string.exportExcel,
                iconCls: 'export',
                hidden: false,
                handler: function (v, o) {
                	var window = Ext.create("AP.view.well.ExportDeviceInfoWindow");
                    Ext.getCmp("ExportDeviceInfoDeviceType_Id").setValue(101);
                    window.show();
                }
            },'-',{
                id: 'DeviceTotalCount_Id',
                xtype: 'component',
                hidden: false,
                tpl: cosog.string.totalCount + ': {count}',
                style: 'margin-right:15px'
            },'->', {
    			xtype: 'button',
                text: '添加设备',
                iconCls: 'add',
                handler: function (v, o) {
                	var selectedOrgName="";
                	var selectedOrgId="";
                	var IframeViewStore = Ext.getCmp("IframeView_Id").getStore();
            		var count=IframeViewStore.getCount();
                	var IframeViewSelection = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
                	if (IframeViewSelection.length > 0) {
                		selectedOrgName=foreachAndSearchOrgAbsolutePath(IframeViewStore.data.items,IframeViewSelection[0].data.orgId);
                		selectedOrgId=IframeViewSelection[0].data.orgId;
                		
                	} else {
                		if(count>0){
                			selectedOrgName=IframeViewStore.getAt(0).data.text;
                			selectedOrgId=IframeViewStore.getAt(0).data.orgId;
                		}
                	}
                	
                	var window = Ext.create("AP.view.well.DeviceInfoWindow", {
                        title: '添加设备'
                    });
                    window.show();
                    Ext.getCmp("deviceWinOgLabel_Id").setHtml("设备将添加到【<font color=red>"+selectedOrgName+"</font>】下,请确认<br/>&nbsp;");
                    Ext.getCmp("deviceType_Id").setValue(101);
                    Ext.getCmp("deviceOrg_Id").setValue(selectedOrgId);
                    Ext.getCmp("addFormDevice_Id").show();
                    Ext.getCmp("updateFormDevice_Id").hide();
                    return false;
    			}
    		}, '-',{
    			xtype: 'button',
    			id: 'deleteDeviceNameBtn_Id',
    			text: '删除设备',
    			iconCls: 'delete',
    			handler: function (v, o) {
    				var startRow= Ext.getCmp("DeviceSelectRow_Id").getValue();
    				var endRow= Ext.getCmp("DeviceSelectEndRow_Id").getValue();
    				var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
    				if(startRow!='' && endRow!=''){
    					startRow=parseInt(startRow);
    					endRow=parseInt(endRow);
    					var deleteInfo='是否删除第'+(startRow+1)+"行~第"+(endRow+1)+"行数据";
    					if(startRow==endRow){
    						deleteInfo='是否删除第'+(startRow+1)+"行数据";
    					}
    					
    					Ext.Msg.confirm(cosog.string.yesdel, deleteInfo, function (btn) {
    			            if (btn == "yes") {
    			            	for(var i=startRow;i<=endRow;i++){
    	    						var rowdata = deviceInfoHandsontableHelper.hot.getDataAtRow(i);
    	    						if (rowdata[0] != null && parseInt(rowdata[0])>0) {
    	    		                    deviceInfoHandsontableHelper.delidslist.push(rowdata[0]);
    	    		                }
    	    					}
    	    					var saveData={};
    	    	            	saveData.updatelist=[];
    	    	            	saveData.insertlist=[];
    	    	            	saveData.delidslist=deviceInfoHandsontableHelper.delidslist;
    	    	            	Ext.Ajax.request({
    	    	                    method: 'POST',
    	    	                    url: context + '/wellInformationManagerController/saveWellHandsontableData',
    	    	                    success: function (response) {
    	    	                        rdata = Ext.JSON.decode(response.responseText);
    	    	                        if (rdata.success) {
    	    	                        	Ext.MessageBox.alert("信息", "删除成功");
    	    	                            //保存以后重置全局容器
    	    	                            deviceInfoHandsontableHelper.clearContainer();
    	    	                            Ext.getCmp("DeviceSelectRow_Id").setValue(0);
    	    	                        	Ext.getCmp("DeviceSelectEndRow_Id").setValue(0);
    	    	                            CreateAndLoadDeviceInfoTable();
    	    	                        } else {
    	    	                            Ext.MessageBox.alert("信息", "数据保存失败");
    	    	                        }
    	    	                    },
    	    	                    failure: function () {
    	    	                        Ext.MessageBox.alert("信息", "请求失败");
    	    	                        deviceInfoHandsontableHelper.clearContainer();
    	    	                    },
    	    	                    params: {
    	    	                        data: JSON.stringify(saveData),
    	    	                        orgId: leftOrg_Id,
    	    	                        deviceType: 101
    	    	                    }
    	    	                });
    			            }
    			        });
    				}else{
    					Ext.MessageBox.alert("信息","请先选中要删除的行");
    				}
    			}
    		},"-", {
                xtype: 'button',
                itemId: 'saveDeviceDataBtnId',
                id: 'saveDeviceDataBtn_Id',
                text: cosog.string.save,
                iconCls: 'save',
                handler: function (v, o) {
                    deviceInfoHandsontableHelper.saveData();
                }
            },"-",{
    			xtype: 'button',
                text: '批量添加',
                iconCls: 'batchAdd',
                hidden: false,
                handler: function (v, o) {
                	var selectedOrgName="";
                	var selectedOrgId="";
                	var IframeViewStore = Ext.getCmp("IframeView_Id").getStore();
            		var count=IframeViewStore.getCount();
                	var IframeViewSelection = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
                	if (IframeViewSelection.length > 0) {
                		selectedOrgName=foreachAndSearchOrgAbsolutePath(IframeViewStore.data.items,IframeViewSelection[0].data.orgId);
                		selectedOrgId=IframeViewSelection[0].data.orgId;
                	} else {
                		if(count>0){
                			selectedOrgName=IframeViewStore.getAt(0).data.text;
                			selectedOrgId=IframeViewStore.getAt(0).data.orgId;
                		}
                	}
                	
                	var window = Ext.create("AP.view.well.BatchAddDeviceWindow", {
                        title: '抽油机井批量添加'
                    });
                    Ext.getCmp("batchAddDeviceWinOgLabel_Id").setHtml("设备将添加到【<font color=red>"+selectedOrgName+"</font>】下,请确认");
                    Ext.getCmp("batchAddDeviceType_Id").setValue(101);
                    Ext.getCmp("batchAddDeviceOrg_Id").setValue(selectedOrgId);
                    window.show();
                    return false;
    			}
    		},{
    			xtype: 'button',
    			text:'excel导入',
    			iconCls: 'upload',
    			hidden:true,
    			handler: function (v, o) {
    				var selectedOrgName="";
                	var selectedOrgId="";
                	var IframeViewStore = Ext.getCmp("IframeView_Id").getStore();
            		var count=IframeViewStore.getCount();
                	var IframeViewSelection = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
                	if (IframeViewSelection.length > 0) {
                		selectedOrgName=foreachAndSearchOrgAbsolutePath(IframeViewStore.data.items,IframeViewSelection[0].data.orgId);
                		selectedOrgId=IframeViewSelection[0].data.orgId;
                	} else {
                		if(count>0){
                			selectedOrgName=IframeViewStore.getAt(0).data.text;
                			selectedOrgId=IframeViewStore.getAt(0).data.orgId;
                		}
                	}
    				
    				var window = Ext.create("AP.view.well.ExcelImportDeviceWindow", {
                        title: '设备导入'
                    });
    				Ext.getCmp("excelImportDeviceWinOgLabel_Id").setHtml("设备将添加到【<font color=red>"+selectedOrgName+"</font>】下,请确认");
                    Ext.getCmp("excelImportDeviceType_Id").setValue(101);
                    Ext.getCmp("excelImportDeviceOrg_Id").setValue(selectedOrgId);
                    window.show();
    			}
    		},'-', {
    			xtype: 'button',
    			text:'设备隶属迁移',
    			iconCls: 'move',
    			handler: function (v, o) {
    				var window = Ext.create("AP.view.well.DeviceOrgChangeWindow", {
                        title: '设备隶属迁移'
                    });
                    window.show();
                    Ext.getCmp('DeviceOrgChangeWinDeviceType_Id').setValue(101);
                    Ext.create("AP.store.well.DeviceOrgChangeDeviceListStore");
                    Ext.create("AP.store.well.DeviceOrgChangeOrgListStore");
    			}
    		}],
            layout: 'border',
            items: [{
            	region: 'center',
        		title:'设备列表',
        		header: true,
        		layout: 'fit',
        		id:'DeviceTablePanel_id',
            	html: '<div class="DeviceContainer" style="width:100%;height:100%;"><div class="con" id="DeviceTableDiv_id" style="width:100%;height:100%;"></div></div>',
                listeners: {
                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if (deviceInfoHandsontableHelper != null && deviceInfoHandsontableHelper.hot != null && deviceInfoHandsontableHelper.hot != undefined) {
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                        	deviceInfoHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                        }
                    }
                }
            },{
            	region: 'east',
                width: '50%',
                layout: 'border',
                split: true,
                collapsible: true,
                header:false,
                hidden:onlyMonitor,
                items: [{
                	region: 'center',
                	header:false,
                	layout: 'border',
                	items: [{
                    	region: 'center',
                		title:'生产数据',
                    	id:'ProductionDataInfoPanel_Id',
                    	split: true,
                    	collapsible: false,
                    	html: '<div class="AdditionalInfoContainer" style="width:100%;height:100%;"><div class="con" id="AdditionalInfoTableDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if (productionHandsontableHelper != null && productionHandsontableHelper.hot != null && productionHandsontableHelper.hot != undefined) {
//                            		productionHandsontableHelper.hot.refreshDimensions();
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		productionHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                                }
                            }
                        }
                	},{
                		region: 'east',
                        width: '40%',
                        layout: 'border',
                        split: true,
                        collapsible: true,
                        header:false,
                        items: [{
                        	region: 'center',
                        	title:'抽油机型号选择',
                            id:'PumpingModelListPanel_Id',
                            split: true,
                            collapsible: true,
                            html: '<div class="PumpingModelListContainer" style="width:100%;height:100%;"><div class="con" id="PumpingModelListTableDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if (pumpingModelHandsontableHelper != null && pumpingModelHandsontableHelper.hot != null && pumpingModelHandsontableHelper.hot != undefined) {
//                                		pumpingModelHandsontableHelper.hot.refreshDimensions();
                                		var newWidth=width;
                                		var newHeight=height;
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                		pumpingModelHandsontableHelper.hot.updateSettings({
                                			width:newWidth,
                                			height:newHeight
                                		});
                                    }
                                }
                            }
                        },{
                        	region: 'south',
                        	height:'50%',
                        	split: true,
                            collapsible: true,
                        	title:'抽油机详情',
                        	id:'PumpingInfoPanel_Id',
                            split: true,
                            collapsible: true,
                            html: '<div class="PumpingInfoContainer" style="width:100%;height:100%;"><div class="con" id="PumpingInfoTableDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if (pumpingInfoHandsontableHelper != null && pumpingInfoHandsontableHelper.hot != null && pumpingInfoHandsontableHelper.hot != undefined) {
//                                		pumpingInfoHandsontableHelper.hot.refreshDimensions();
                                		var newWidth=width;
                                		var newHeight=height;
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                		pumpingInfoHandsontableHelper.hot.updateSettings({
                                			width:newWidth,
                                			height:newHeight
                                		});
                                    }
                                }
                            }
                        }]
                	}]
                },{
                	region: 'south',
                	height:'23%',
                	title:'视频配置',
                	id:'VideoInfoPanel_Id',
                	hidden: !IoTConfig,
                	split: true,
                	collapsible: true,
                	tbar:['->',{
                        xtype: 'button',
                        text: '编辑视频密钥',
                        iconCls: 'save',
                        disabled: loginUserRoleVideoKeyEdit!=1,
                        handler: function (v, o) {
                        	var VideoKeyInfoWindow = Ext.create("AP.view.well.VideoKeyInfoWindow");
                        	VideoKeyInfoWindow.show();
                        }
                    }],
                	html: '<div class="VideoInfoContainer" style="width:100%;height:100%;"><div class="con" id="VideoInfoTableDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	if (videoInfoHandsontableHelper != null && videoInfoHandsontableHelper.hot != null && videoInfoHandsontableHelper.hot != undefined) {
//                        		videoInfoHandsontableHelper.hot.refreshDimensions();
                        		var newWidth=width;
                        		var newHeight=height;
                        		var header=thisPanel.getHeader();
                        		if(header){
                        			newHeight=newHeight-header.lastBox.height-2;
                        		}
                        		videoInfoHandsontableHelper.hot.updateSettings({
                        			width:newWidth,
                        			height:newHeight
                        		});
                            }
                        }
                    }
                }]
            }],
            listeners: {
                beforeclose: function (panel, eOpts) {
                	
                }
            }
        })
        this.callParent(arguments);
    }
});

function CreateAndLoadDeviceInfoTable(isNew) {
	if(isNew&&deviceInfoHandsontableHelper!=null){
		if (deviceInfoHandsontableHelper.hot != undefined) {
			deviceInfoHandsontableHelper.hot.destroy();
		}
		deviceInfoHandsontableHelper = null;
	}
    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
    var wellInformationName_Id = Ext.getCmp('deviceListComb_Id').getValue();
    Ext.getCmp("DeviceTablePanel_id").el.mask(cosog.string.loading).show();
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/wellInformationManagerController/doWellInformationShow',
        success: function (response) {
        	Ext.getCmp("DeviceTablePanel_id").getEl().unmask();
        	var result = Ext.JSON.decode(response.responseText);
            if (deviceInfoHandsontableHelper == null || deviceInfoHandsontableHelper.hot == null || deviceInfoHandsontableHelper.hot == undefined) {
                deviceInfoHandsontableHelper = DeviceInfoHandsontableHelper.createNew("DeviceTableDiv_id");
                deviceInfoHandsontableHelper.dataLength=result.totalCount;
                var colHeaders = "[";
                var columns = "[";

                for (var i = 0; i < result.columns.length; i++) {
                    colHeaders += "'" + result.columns[i].header + "'";
                    if (result.columns[i].dataIndex.toUpperCase() === "orgName".toUpperCase()) {
                        columns += "{data:'" + result.columns[i].dataIndex + "',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Org(val, callback,this.row, this.col,deviceInfoHandsontableHelper);}}";
                    } else if (result.columns[i].dataIndex.toUpperCase() === "liftingTypeName".toUpperCase()) {
                        if (pcpHidden) {
                            columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['抽油机井']}";
                        } else {
                            columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['抽油机井', '螺杆泵井']}";
                        }
                    } else if (result.columns[i].dataIndex.toUpperCase() === "instanceName".toUpperCase()) {
                        var source = "[";
                        for (var j = 0; j < result.instanceDropdownData.length; j++) {
                            source += "\'" + result.instanceDropdownData[j] + "\'";
                            if (j < result.instanceDropdownData.length - 1) {
                                source += ",";
                            }
                        }
                        source += "]";
                        columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
                    } else if (result.columns[i].dataIndex.toUpperCase() === "displayInstanceName".toUpperCase()) {
                        var source = "[";
                        for (var j = 0; j < result.displayInstanceDropdownData.length; j++) {
                            source += "\'" + result.displayInstanceDropdownData[j] + "\'";
                            if (j < result.displayInstanceDropdownData.length - 1) {
                                source += ",";
                            }
                        }
                        source += "]";
                        columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
                    } else if (result.columns[i].dataIndex.toUpperCase() === "reportInstanceName".toUpperCase()) {
                        var source = "[";
                        for (var j = 0; j < result.reportInstanceDropdownData.length; j++) {
                            source += "\'" + result.reportInstanceDropdownData[j] + "\'";
                            if (j < result.reportInstanceDropdownData.length - 1) {
                                source += ",";
                            }
                        }
                        source += "]";
                        columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
                    } else if (result.columns[i].dataIndex.toUpperCase() === "alarmInstanceName".toUpperCase()) {
                        var source = "[";
                        for (var j = 0; j < result.alarmInstanceDropdownData.length; j++) {
                            source += "\'" + result.alarmInstanceDropdownData[j] + "\'";
                            if (j < result.alarmInstanceDropdownData.length - 1) {
                                source += ",";
                            }
                        }
                        source += "]";
                        columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
                    }else if (result.columns[i].dataIndex.toUpperCase() === "applicationScenariosName".toUpperCase()) {
                        var source = "[";
                        for (var j = 0; j < result.applicationScenariosDropdownData.length; j++) {
                            source += "\'" + result.applicationScenariosDropdownData[j] + "\'";
                            if (j < result.applicationScenariosDropdownData.length - 1) {
                                source += ",";
                            }
                        }
                        source += "]";
                        columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
                    } else if (result.columns[i].dataIndex.toUpperCase() === "statusName".toUpperCase()) {
                    	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['使能', '失效']}";
                    } else if (result.columns[i].dataIndex.toUpperCase() === "tcpType".toUpperCase()) {
                    	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['TCP Server', 'TCP Client']}";
                    } else if (result.columns[i].dataIndex.toUpperCase() === "sortNum".toUpperCase() 
                    		||result.columns[i].dataIndex.toUpperCase() === "slave".toUpperCase()
                    		||result.columns[i].dataIndex.toUpperCase() === "peakDelay".toUpperCase()) {
                        columns += "{data:'" + result.columns[i].dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,deviceInfoHandsontableHelper);}}";
                    } else if (result.columns[i].dataIndex.toUpperCase() === "ipPort".toUpperCase()) {
                    	columns += "{data:'" + result.columns[i].dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_IpPort_Nullable(val, callback,this.row, this.col,deviceInfoHandsontableHelper);}}";
                    } else {
                        columns += "{data:'" + result.columns[i].dataIndex + "'}";
                    }
                    if (i < result.columns.length - 1) {
                        colHeaders += ",";
                        columns += ",";
                    }
                }
                colHeaders += "]";
                columns += "]";
                deviceInfoHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
                deviceInfoHandsontableHelper.columns = Ext.JSON.decode(columns);
                if(result.totalRoot.length==0){
                	deviceInfoHandsontableHelper.hiddenRows = [0];
                	deviceInfoHandsontableHelper.createTable([{}]);
                }else{
                	deviceInfoHandsontableHelper.hiddenRows = [];
                	deviceInfoHandsontableHelper.createTable(result.totalRoot);
                }
            } else {
            	deviceInfoHandsontableHelper.dataLength=result.totalCount;
            	if(result.totalRoot.length==0){
            		deviceInfoHandsontableHelper.hiddenRows = [0];
            		deviceInfoHandsontableHelper.hot.loadData([{}]);
            	}else{
            		deviceInfoHandsontableHelper.hiddenRows = [];
            		deviceInfoHandsontableHelper.hot.loadData(result.totalRoot);
            	}
            }
            if(deviceInfoHandsontableHelper.hiddenRows.length>0){
            	const plugin = deviceInfoHandsontableHelper.hot.getPlugin('hiddenRows');
            	plugin.hideRows(deviceInfoHandsontableHelper.hiddenRows);
            	deviceInfoHandsontableHelper.hot.render();
            }
            if(result.totalRoot.length==0){
            	Ext.getCmp("DeviceSelectRow_Id").setValue('');
            	Ext.getCmp("DeviceSelectEndRow_Id").setValue('');
//            	CreateAndLoadPumoingModelInfoTable(0,'');
//            	CreateAndLoadProductionDataTable(0,'');
//            	CreateAndLoadVideoInfoTable(0,'');
            }else{
            	var selectedDeviceId=parseInt(Ext.getCmp("selectedDeviceId_global").getValue());
            	var selectRow=0;
            	for(var i=0;i<result.totalRoot.length;i++){
            		if(result.totalRoot[i].id==selectedDeviceId){
            			selectRow=i;
            			break;
            		}
            	}
            	Ext.getCmp("DeviceSelectRow_Id").setValue(selectRow);
            	var rowdata = deviceInfoHandsontableHelper.hot.getDataAtRow(selectRow);
            	
            	var combDeviceName=Ext.getCmp('deviceListComb_Id').getValue();
        		if(combDeviceName!=''){
            		Ext.getCmp("selectedDeviceId_global").setValue(rowdata[0]);
        		}

//            	CreateAndLoadPumoingModelInfoTable(rowdata[0],rowdata[1]);
//            	CreateAndLoadProductionDataTable(rowdata[0],rowdata[1]);
//            	CreateAndLoadVideoInfoTable(rowdata[0],rowdata[1]);
            }
            Ext.getCmp("DeviceTotalCount_Id").update({
                count: result.totalCount
            });
        },
        failure: function () {
        	Ext.getCmp("DeviceTablePanel_id").getEl().unmask();
        	Ext.MessageBox.alert("错误", "与后台联系的时候出了问题");
        },
        params: {
            wellInformationName: wellInformationName_Id,
            deviceType: 101,
            recordCount: 50,
            orgId: leftOrg_Id,
            page: 1,
            limit: 10000
        }
    });
};

var DeviceInfoHandsontableHelper = {
    createNew: function (divid) {
        var deviceInfoHandsontableHelper = {};
        deviceInfoHandsontableHelper.hot = '';
        deviceInfoHandsontableHelper.divid = divid;
        deviceInfoHandsontableHelper.validresult = true; //数据校验
        deviceInfoHandsontableHelper.colHeaders = [];
        deviceInfoHandsontableHelper.columns = [];
        deviceInfoHandsontableHelper.dataLength = 0;
        deviceInfoHandsontableHelper.hiddenRows = [];

        deviceInfoHandsontableHelper.AllData = {};
        deviceInfoHandsontableHelper.updatelist = [];
        deviceInfoHandsontableHelper.delidslist = [];
        deviceInfoHandsontableHelper.insertlist = [];
        deviceInfoHandsontableHelper.editWellNameList = [];

        deviceInfoHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
        }

        deviceInfoHandsontableHelper.createTable = function (data) {
            $('#' + deviceInfoHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + deviceInfoHandsontableHelper.divid);
            deviceInfoHandsontableHelper.hot = new Handsontable(hotElement, {
            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            	data: data,
                hiddenColumns: {
                    columns: [0],
                    indicators: false,
                    copyPasteEnabled: false
                },
                hiddenRows: {
                    rows: [],
                    indicators: false,
                    copyPasteEnabled: false
                },
                columns: deviceInfoHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
//                autoWrapRow: true,
                rowHeaders: true, //显示行头
                colHeaders: deviceInfoHandsontableHelper.colHeaders, //显示列头
                columnSorting: true, //允许排序
                allowInsertRow:false,
                sortIndicator: true,
                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                filters: true,
                renderAllRows: true,
                search: true,
                cells: function (row, col, prop) {
                    var cellProperties = {};
                    var visualRowIndex = this.instance.toVisualRow(row);
                    var visualColIndex = this.instance.toVisualColumn(col);
                    if(deviceInfoHandsontableHelper.dataLength==0){
                    	cellProperties.readOnly = true;
                    }else if(deviceInfoHandsontableHelper.hot!=undefined && deviceInfoHandsontableHelper.hot.getDataAtCell!=undefined){
                    	var columns=deviceInfoHandsontableHelper.columns;
                    	if(prop.toUpperCase() === "signInId".toUpperCase() || prop.toUpperCase() === "ipPort".toUpperCase()){
                    		var tcpTypeColIndex=-1;
                    		for(var i=0;i<columns.length;i++){
                    			if(columns[i].data.toUpperCase() === "tcpType".toUpperCase()){
                    				tcpTypeColIndex=i;
                    				break;
                            	}
                    		}
                    		if(tcpTypeColIndex>=0){
                    			var tcpType=deviceInfoHandsontableHelper.hot.getDataAtCell(row,tcpTypeColIndex);
//                    			var cell = deviceInfoHandsontableHelper.hot.getCell(row, col);  
                    			if(tcpType=='' || tcpType==null){
                    				cellProperties.readOnly = false;
                    			}else{
                    				if(prop.toUpperCase() === "signInId".toUpperCase()){
                    					if(tcpType.toUpperCase() === "TCP Client".toUpperCase() || tcpType.toUpperCase() === "TCPClient".toUpperCase()){
                    						cellProperties.readOnly = false;
                    					}else{
                    						cellProperties.readOnly = true;
//                    						cell.style.background = "#f5f5f5";
                    					}
                    				}else if(prop.toUpperCase() === "ipPort".toUpperCase()){
                    					if(tcpType.toUpperCase() === "TCP Server".toUpperCase() || tcpType.toUpperCase() === "TCPServer".toUpperCase()){
                    						cellProperties.readOnly = false;
                    					}else{
                    						cellProperties.readOnly = true;
//                    						cell.style.background = "#f5f5f5";
                    					}
                    				}
                    			}
                    		}
                    	}else if(prop.toUpperCase() === "allPath".toUpperCase() || prop.toUpperCase() === "productionDataUpdateTime".toUpperCase()){
                    		cellProperties.readOnly = true;
                    	}
                    }
                    
                    return cellProperties;
                },
                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
                	if(row<0 && row2<0){//只选中表头
                		Ext.getCmp("DeviceSelectRow_Id").setValue('');
                    	Ext.getCmp("DeviceSelectEndRow_Id").setValue('');
//                    	CreateAndLoadPumoingModelInfoTable(0,'');
//                    	CreateAndLoadProductionDataTable(0,'');
//                    	CreateAndLoadVideoInfoTable(0,'');
                	}else{
                		if(row<0){
                    		row=0;
                    	}
                    	if(row2<0){
                    		row2=0;
                    	}
                    	var startRow=row;
                    	var endRow=row2;
                    	if(row>row2){
                    		startRow=row2;
                        	endRow=row;
                    	}
                    	
                    	var selectedRow=Ext.getCmp("DeviceSelectRow_Id").getValue();
                    	if(selectedRow!=startRow){
                    		var row1=deviceInfoHandsontableHelper.hot.getDataAtRow(startRow);
                        	var recordId=0;
                        	var deviceName='';
                        	if(isNotVal(row1[0])){
                        		recordId=row1[0];
                        	}
                        	if(isNotVal(row1[1])){
                        		deviceName=row1[1];
                        	}
                        	
//                        	CreateAndLoadPumoingModelInfoTable(recordId,deviceName);
//                        	CreateAndLoadProductionDataTable(recordId,deviceName);
//                        	CreateAndLoadVideoInfoTable(recordId,deviceName);
                        	
                        	Ext.getCmp("selectedDeviceId_global").setValue(recordId);
                    	}
                    	Ext.getCmp("DeviceSelectRow_Id").setValue(startRow);
                    	Ext.getCmp("DeviceSelectEndRow_Id").setValue(endRow);
                	}
                },
                afterDestroy: function () {
                },
                beforeRemoveRow: function (index, amount) {
                    var ids = [];
                    //封装id成array传入后台
                    if (amount != 0) {
                        for (var i = index; i < amount + index; i++) {
                            var rowdata = deviceInfoHandsontableHelper.hot.getDataAtRow(i);
                            ids.push(rowdata[0]);
                        }
                        deviceInfoHandsontableHelper.delExpressCount(ids);
                        deviceInfoHandsontableHelper.screening();
                    }
                },
                afterChange: function (changes, source) {
                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
                    if (changes != null) {
                        for (var i = 0; i < changes.length; i++) {
                            var params = [];
                            var index = changes[i][0]; //行号码
                            var rowdata = deviceInfoHandsontableHelper.hot.getDataAtRow(index);
                            params.push(rowdata[0]);
                            params.push(changes[i][1]);
                            params.push(changes[i][2]);
                            params.push(changes[i][3]);

                            //仅当单元格发生改变的时候,id!=null,说明是更新
                            if (params[2] != params[3] && params[0] != null && params[0] > 0) {
                                var data = "{";
                                for (var j = 0; j < deviceInfoHandsontableHelper.columns.length; j++) {
                                    data += deviceInfoHandsontableHelper.columns[j].data + ":'" + rowdata[j] + "'";
                                    if (j < deviceInfoHandsontableHelper.columns.length - 1) {
                                        data += ","
                                    }
                                }
                                data += "}"
                                deviceInfoHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
                                
                                if(params[1] == "applicationScenariosName"){
                                	const plugin = productionHandsontableHelper.hot.getPlugin('hiddenRows');
                                	var hiddenRows=[0,3,9,10];
                                	if(sceneConfig=='cbm' || (sceneConfig=='all'&& params[3] == "煤层气井") ){
                                		plugin.hideRows(hiddenRows);
                                		productionHandsontableHelper.hot.setDataAtCell(4,1,'煤层中部深度(m)');
                                		productionHandsontableHelper.hot.setDataAtCell(5,1,'煤层中部温度(℃)');
                                	}else{
                                		plugin.showRows(hiddenRows);
                                		productionHandsontableHelper.hot.setDataAtCell(4,1,'油层中部深度(m)');
                                		productionHandsontableHelper.hot.setDataAtCell(5,1,'油层中部温度(℃)');
                                	}
                                	productionHandsontableHelper.hot.render();
                                }
                            }
                        }
                    }
                }
            });
        }
        //插入的数据的获取
        deviceInfoHandsontableHelper.insertExpressCount = function () {
            var idsdata = deviceInfoHandsontableHelper.hot.getDataAtCol(0); //所有的id
            for (var i = 0; i < idsdata.length; i++) {
                //id=null时,是插入数据,此时的i正好是行号
                if (idsdata[i] == null || idsdata[i] < 0) {
                    //获得id=null时的所有数据封装进data
                    var rowdata = deviceInfoHandsontableHelper.hot.getDataAtRow(i);
                    //var collength = hot.countCols();
                    if (rowdata != null) {
                        var data = "{";
                        for (var j = 0; j < deviceInfoHandsontableHelper.columns.length; j++) {
                            data += deviceInfoHandsontableHelper.columns[j].data + ":'" + rowdata[j] + "'";
                            if (j < deviceInfoHandsontableHelper.columns.length - 1) {
                                data += ","
                            }
                        }
                        data += "}"
                        deviceInfoHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
                    }
                }
            }
            if (deviceInfoHandsontableHelper.insertlist.length != 0) {
                deviceInfoHandsontableHelper.AllData.insertlist = deviceInfoHandsontableHelper.insertlist;
            }
        }
        //保存数据
        deviceInfoHandsontableHelper.saveData = function () {
        	var deviceInfoHandsontableData=deviceInfoHandsontableHelper.hot.getData();
        	if(deviceInfoHandsontableData.length>0){
        		var leftOrg_Name=Ext.getCmp("leftOrg_Name").getValue();
            	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
                //插入的数据的获取
                deviceInfoHandsontableHelper.insertExpressCount();
                //获取设备ID
                var DeviceSelectRow= Ext.getCmp("DeviceSelectRow_Id").getValue();
                var rowdata = deviceInfoHandsontableHelper.hot.getDataAtRow(DeviceSelectRow);
            	var deviceId=rowdata[0];
            	var applicationScenariosIndex=-1;
            	var applicationScenarios=1;
            	
            	for (var i = 0; i < deviceInfoHandsontableHelper.columns.length; i++) {
            		if(deviceInfoHandsontableHelper.columns[i].data.toUpperCase()=='applicationScenariosName'.toUpperCase()){
            			applicationScenariosIndex=i;
            			break;
            		}
            	}
            	if(applicationScenariosIndex>=0){
            		var applicationScenariosName=rowdata[applicationScenariosIndex];
            		if(applicationScenariosName==="煤层气井"){
            			applicationScenarios=0;
            		}
            	}
            	
                //生产数据
                var deviceProductionData={};
                var manualInterventionResultName='不干预';
                if(productionHandsontableHelper!=null && productionHandsontableHelper.hot!=undefined){
            		var productionHandsontableData=productionHandsontableHelper.hot.getData();
            		deviceProductionData.FluidPVT={};
            		if(applicationScenarios==1 && isNumber(parseFloat(productionHandsontableData[0][2]))){
            			deviceProductionData.FluidPVT.CrudeOilDensity=parseFloat(productionHandsontableData[0][2]);
            		}
            		if(isNumber(parseFloat(productionHandsontableData[1][2]))){
            			deviceProductionData.FluidPVT.WaterDensity=parseFloat(productionHandsontableData[1][2]);
            		}
            		if(isNumber(parseFloat(productionHandsontableData[2][2]))){
            			deviceProductionData.FluidPVT.NaturalGasRelativeDensity=parseFloat(productionHandsontableData[2][2]);
            		}
            		if(applicationScenarios==1 && isNumber(parseFloat(productionHandsontableData[3][2]))){
            			deviceProductionData.FluidPVT.SaturationPressure=parseFloat(productionHandsontableData[3][2]);
            		}
            		
            		deviceProductionData.Reservoir={};
            		if(isNumber(parseFloat(productionHandsontableData[4][2]))){
            			deviceProductionData.Reservoir.Depth=parseFloat(productionHandsontableData[4][2]);
            		}
            		if(isNumber(parseFloat(productionHandsontableData[5][2]))){
            			deviceProductionData.Reservoir.Temperature=parseFloat(productionHandsontableData[5][2]);
            		}
            		
            		deviceProductionData.Production={};
            		if(isNumber(parseFloat(productionHandsontableData[6][2]))){
            			deviceProductionData.Production.TubingPressure=parseFloat(productionHandsontableData[6][2]);
            		}
            		if(isNumber(parseFloat(productionHandsontableData[7][2]))){
            			deviceProductionData.Production.CasingPressure=parseFloat(productionHandsontableData[7][2]);
            		}
            		if(isNumber(parseFloat(productionHandsontableData[8][2]))){
            			deviceProductionData.Production.WellHeadTemperature=parseFloat(productionHandsontableData[8][2]);
            		}
            		
            		if(applicationScenarios==0){
            			deviceProductionData.Production.WaterCut=100;
            		}else if(applicationScenarios==1 && isNumber(parseFloat(productionHandsontableData[9][2]))){
            			deviceProductionData.Production.WaterCut=parseFloat(productionHandsontableData[9][2]);
            		}
            		
            		if(isNumber(parseFloat(productionHandsontableData[10][2]))){
            			deviceProductionData.Production.ProductionGasOilRatio=parseFloat(productionHandsontableData[10][2]);
            		}
            		if(isNumber(parseFloat(productionHandsontableData[11][2]))){
            			deviceProductionData.Production.ProducingfluidLevel=parseFloat(productionHandsontableData[11][2]);
            		}
            		if(isNumber(parseFloat(productionHandsontableData[12][2]))){
            			deviceProductionData.Production.PumpSettingDepth=parseFloat(productionHandsontableData[12][2]);
            		}
            		
            		deviceProductionData.Pump={};
//            		var PumpType=productionHandsontableData[13][2];
//            		if(productionHandsontableData[13][2]=='杆式泵'){
//            			PumpType='R';
//            		}else if(productionHandsontableData[13][2]=='管式泵'){
//            			PumpType='T';
//            		}
//            		if(isNotVal(PumpType)){
//            			deviceProductionData.Pump.PumpType=PumpType;
//            		}
            		deviceProductionData.Pump.PumpType='T';
            		
            		var BarrelType=productionHandsontableData[13][2];
            		if(productionHandsontableData[13][2]=='组合泵'){
            			BarrelType='L';
            		}else if(productionHandsontableData[13][2]=='整筒泵'){
            			BarrelType='H';
            		}
            		if(isNotVal(BarrelType)){
            			deviceProductionData.Pump.BarrelType=BarrelType;
            		}
            		if(isNumber(parseInt(productionHandsontableData[14][2]))){
            			deviceProductionData.Pump.PumpGrade=parseInt(productionHandsontableData[14][2]);
            		}
            		if(isNumber(parseInt(productionHandsontableData[15][2]))){
            			deviceProductionData.Pump.PumpBoreDiameter=parseInt(productionHandsontableData[15][2])*0.001;
            		}
            		if(isNumber(parseFloat(productionHandsontableData[16][2]))){
            			deviceProductionData.Pump.PlungerLength=parseFloat(productionHandsontableData[16][2]);
            		}
            		
            		deviceProductionData.TubingString={};
            		deviceProductionData.TubingString.EveryTubing=[];
            		var EveryTubing={};
            		if(isNumber(parseInt(productionHandsontableData[17][2]))){
            			EveryTubing.InsideDiameter=parseInt(productionHandsontableData[17][2])*0.001;
            		}
            		deviceProductionData.TubingString.EveryTubing.push(EveryTubing);
            		
            		deviceProductionData.CasingString={};
            		deviceProductionData.CasingString.EveryCasing=[];
            		var EveryCasing={};
            		if(isNumber(parseInt(productionHandsontableData[18][2]))){
            			EveryCasing.InsideDiameter=parseInt(productionHandsontableData[18][2])*0.001;
            		}
            		deviceProductionData.CasingString.EveryCasing.push(EveryCasing);
            		
            		deviceProductionData.RodString={};
            		deviceProductionData.RodString.EveryRod=[];
            		
            		if(isNotVal(productionHandsontableData[19][2]) 
            				&& isNumber(parseInt(productionHandsontableData[20][2])) 
            				&& (productionHandsontableData[21][2]=='' || isNumber(parseInt(productionHandsontableData[21][2])) )
            				&& isNumber(parseInt(productionHandsontableData[22][2]))){
            			var Rod1={};
                		if(isNotVal(productionHandsontableData[19][2])){
                			Rod1.Grade=productionHandsontableData[19][2];
                		}
                		if(isNumber(parseInt(productionHandsontableData[20][2]))){
                			Rod1.OutsideDiameter=parseInt(productionHandsontableData[20][2])*0.001;
                		}
                		if(isNumber(parseInt(productionHandsontableData[21][2]))){
                			Rod1.InsideDiameter=parseInt(productionHandsontableData[21][2])*0.001;
                		}
                		if(isNumber(parseInt(productionHandsontableData[22][2]))){
                			Rod1.Length=parseInt(productionHandsontableData[22][2]);
                		}
                		deviceProductionData.RodString.EveryRod.push(Rod1);
            		}
            		
            		if(isNotVal(productionHandsontableData[23][2]) 
            				&& isNumber(parseInt(productionHandsontableData[24][2])) 
            				&& (productionHandsontableData[25][2]=='' || isNumber(parseInt(productionHandsontableData[25][2])) )
            				&& isNumber(parseInt(productionHandsontableData[26][2]))){
            			var Rod2={};
                		if(isNotVal(productionHandsontableData[23][2])){
                			Rod2.Grade=productionHandsontableData[23][2];
                		}
                		if(isNumber(parseInt(productionHandsontableData[24][2]))){
                			Rod2.OutsideDiameter=parseInt(productionHandsontableData[24][2])*0.001;
                		}
                		if(isNumber(parseInt(productionHandsontableData[25][2]))){
                			Rod2.InsideDiameter=parseInt(productionHandsontableData[25][2])*0.001;
                		}
                		if(isNumber(parseInt(productionHandsontableData[26][2]))){
                			Rod2.Length=parseInt(productionHandsontableData[26][2]);
                		}
                		deviceProductionData.RodString.EveryRod.push(Rod2);
            		}
            		
            		if(isNotVal(productionHandsontableData[27][2]) 
            				&& isNumber(parseInt(productionHandsontableData[28][2])) 
            				&& (productionHandsontableData[29][2]=='' || isNumber(parseInt(productionHandsontableData[29][2])) )
            				&& isNumber(parseInt(productionHandsontableData[30][2]))){
            			var Rod3={};
                		if(isNotVal(productionHandsontableData[27][2])){
                			Rod3.Grade=productionHandsontableData[27][2];
                		}
                		if(isNumber(parseInt(productionHandsontableData[28][2]))){
                			Rod3.OutsideDiameter=parseInt(productionHandsontableData[28][2])*0.001;
                		}
                		if(isNumber(parseInt(productionHandsontableData[29][2]))){
                			Rod3.InsideDiameter=parseInt(productionHandsontableData[29][2])*0.001;
                		}
                		if(isNumber(parseInt(productionHandsontableData[30][2]))){
                			Rod3.Length=parseInt(productionHandsontableData[30][2]);
                		}
                		deviceProductionData.RodString.EveryRod.push(Rod3);
            		}
            		
            		if(isNotVal(productionHandsontableData[31][2]) 
            				&& isNumber(parseInt(productionHandsontableData[32][2])) 
            				&& (productionHandsontableData[33][2]=='' || isNumber(parseInt(productionHandsontableData[33][2])) )
            				&& isNumber(parseInt(productionHandsontableData[34][2]))){
            			var Rod4={};
                		if(isNotVal(productionHandsontableData[31][2])){
                			Rod4.Grade=productionHandsontableData[31][2];
                		}
                		if(isNumber(parseInt(productionHandsontableData[32][2]))){
                			Rod4.OutsideDiameter=parseInt(productionHandsontableData[32][2])*0.001;
                		}
                		if(isNumber(parseInt(productionHandsontableData[33][2]))){
                			Rod4.InsideDiameter=parseInt(productionHandsontableData[33][2])*0.001;
                		}
                		if(isNumber(parseInt(productionHandsontableData[34][2]))){
                			Rod4.Length=parseInt(productionHandsontableData[34][2]);
                		}
                		deviceProductionData.RodString.EveryRod.push(Rod4);
            		}
            		
            		
            		deviceProductionData.ManualIntervention={};
            		manualInterventionResultName=productionHandsontableData[35][2];
            		if(isNumber(parseFloat(productionHandsontableData[36][2]))){
            			deviceProductionData.ManualIntervention.NetGrossRatio=parseFloat(productionHandsontableData[36][2]);
            		}
            		if(isNumber(parseFloat(productionHandsontableData[37][2]))){
            			deviceProductionData.ManualIntervention.NetGrossValue=parseFloat(productionHandsontableData[37][2]);
            		}
            		if(isNumber(parseFloat(productionHandsontableData[38][2]))){
            			deviceProductionData.ManualIntervention.LevelCorrectValue=parseFloat(productionHandsontableData[38][2]);
            		}
            	}
                
                //获取抽油机型号配置数据
                var pumpingModelId='';
                if(isNotVal(deviceId) && parseInt(deviceId)>0 ){
                	if(pumpingModelHandsontableHelper!=null && pumpingModelHandsontableHelper.hot!=undefined){
                		var pumpingModelHandsontableData=pumpingModelHandsontableHelper.hot.getData();
                    	for(var i=0;i<pumpingModelHandsontableData.length;i++){
                    		if (pumpingModelHandsontableData[i][0]) {
                            	pumpingModelId = pumpingModelHandsontableData[i][4];
                            	break;
                            }
                    	}
                	}
            	}
                
                //抽油机详情
                var balanceInfo={};
                var stroke="";
                if(pumpingInfoHandsontableHelper!=null && pumpingInfoHandsontableHelper.hot!=undefined){
                	var pumpingInfoHandsontableData=pumpingInfoHandsontableHelper.hot.getData();
                	stroke=pumpingInfoHandsontableData[0][2];
                	balanceInfo.EveryBalance=[];
                	for(var i=3;i<pumpingInfoHandsontableData.length;i++){
                		if(isNotVal(pumpingInfoHandsontableData[i][1]) || isNotVal(pumpingInfoHandsontableData[i][2])){
                    		var EveryBalance={};
                    		EveryBalance.Position=pumpingInfoHandsontableData[i][1];
                    		EveryBalance.Weight=pumpingInfoHandsontableData[i][2];
                    		balanceInfo.EveryBalance.push(EveryBalance);
                    	}
                	}
                }
                //视频信息
                var videoUrl1='';
                var videoUrl2='';
                var videoKeyName1='';
                var videoKeyName2='';
                
                if(videoInfoHandsontableHelper!=null && videoInfoHandsontableHelper.hot!=undefined){
                	var videoInfoHandsontableData=videoInfoHandsontableHelper.hot.getData();
                	videoUrl1=videoInfoHandsontableData[0][2];
                	videoKeyName1=videoInfoHandsontableData[0][3];
                	videoUrl2=videoInfoHandsontableData[1][2];
                	videoKeyName2=videoInfoHandsontableData[1][3];
                }
            	Ext.Ajax.request({
                    method: 'POST',
                    url: context + '/wellInformationManagerController/saveWellHandsontableData',
                    success: function (response) {
                        rdata = Ext.JSON.decode(response.responseText);
                        if (rdata.success) {
                        	var saveInfo='保存成功';
                        	if(rdata.collisionCount>0){//数据冲突
                        		saveInfo='保存成功'+rdata.successCount+'条记录,保存失败:<font color="red">'+rdata.collisionCount+'</font>条记录';
                        		for(var i=0;i<rdata.list.length;i++){
                        			saveInfo+='<br/><font color="red"> '+rdata.list[i]+'</font>';
                        		}
                        	}
                        	Ext.MessageBox.alert("信息", saveInfo);
                            if(rdata.successCount>0){
                            	deviceInfoHandsontableHelper.clearContainer();
                            	CreateAndLoadDeviceInfoTable();
                            }
                        } else {
                            Ext.MessageBox.alert("信息", "数据保存失败");
                        }
                    },
                    failure: function () {
                        Ext.MessageBox.alert("信息", "请求失败");
                        deviceInfoHandsontableHelper.clearContainer();
                    },
                    params: {
                    	deviceId: deviceId,
                    	data: JSON.stringify(deviceInfoHandsontableHelper.AllData),
                    	pumpingModelId: pumpingModelId,
                    	stroke: stroke,
                    	balanceInfo: JSON.stringify(balanceInfo),
                        deviceProductionData: JSON.stringify(deviceProductionData),
                        manualInterventionResultName: manualInterventionResultName,
                        videoUrl1:videoUrl1,
                        videoKeyName1:videoKeyName1,
                        videoUrl2:videoUrl2,
                        videoKeyName2:videoKeyName2,
                        orgId: leftOrg_Id,
                        deviceType: 101
                    }
                });
        	}else{
        		Ext.MessageBox.alert("信息", "无记录保存！");
        	}
        }

        //修改井名
        deviceInfoHandsontableHelper.editWellName = function () {
            //插入的数据的获取
            if (deviceInfoHandsontableHelper.editWellNameList.length > 0 && deviceInfoHandsontableHelper.validresult) {
                Ext.Ajax.request({
                    method: 'POST',
                    url: context + '/wellInformationManagerController/editWellName',
                    success: function (response) {
                        rdata = Ext.JSON.decode(response.responseText);
                        if (rdata.success) {
                            Ext.MessageBox.alert("信息", "保存成功");
                            deviceInfoHandsontableHelper.clearContainer();
                            CreateAndLoadDeviceInfoTable();
                        } else {
                            Ext.MessageBox.alert("信息", "数据保存失败");
                        }
                    },
                    failure: function () {
                        Ext.MessageBox.alert("信息", "请求失败");
                        deviceInfoHandsontableHelper.clearContainer();
                    },
                    params: {
                        data: JSON.stringify(deviceInfoHandsontableHelper.editWellNameList),
                        deviceType:101
                    }
                });
            } else {
                if (!deviceInfoHandsontableHelper.validresult) {
                    Ext.MessageBox.alert("信息", "数据类型错误");
                } else {
                    Ext.MessageBox.alert("信息", "无数据变化");
                }
            }
        }


        //删除的优先级最高
        deviceInfoHandsontableHelper.delExpressCount = function (ids) {
            //传入的ids.length不可能为0
            $.each(ids, function (index, id) {
                if (id != null) {
                    deviceInfoHandsontableHelper.delidslist.push(id);
                }
            });
            deviceInfoHandsontableHelper.AllData.delidslist = deviceInfoHandsontableHelper.delidslist;
        }

        //updatelist数据更新
        deviceInfoHandsontableHelper.screening = function () {
            if (deviceInfoHandsontableHelper.updatelist.length != 0 && deviceInfoHandsontableHelper.delidslist.lentgh != 0) {
                for (var i = 0; i < deviceInfoHandsontableHelper.delidslist.length; i++) {
                    for (var j = 0; j < deviceInfoHandsontableHelper.updatelist.length; j++) {
                        if (deviceInfoHandsontableHelper.updatelist[j].id == deviceInfoHandsontableHelper.delidslist[i]) {
                            //更新updatelist
                            deviceInfoHandsontableHelper.updatelist.splice(j, 1);
                        }
                    }
                }
                //把updatelist封装进AllData
                deviceInfoHandsontableHelper.AllData.updatelist = deviceInfoHandsontableHelper.updatelist;
            }
        }

        //更新数据
        deviceInfoHandsontableHelper.updateExpressCount = function (data) {
            if (JSON.stringify(data) != "{}") {
                var flag = true;
                //判断记录是否存在,更新数据     
                $.each(deviceInfoHandsontableHelper.updatelist, function (index, node) {
                    if (node.id == data.id) {
                        //此记录已经有了
                        flag = false;
                        //用新得到的记录替换原来的,不用新增
                        deviceInfoHandsontableHelper.updatelist[index] = data;
                    }
                });
                flag && deviceInfoHandsontableHelper.updatelist.push(data);
                //封装
                deviceInfoHandsontableHelper.AllData.updatelist = deviceInfoHandsontableHelper.updatelist;
            }
        }

        deviceInfoHandsontableHelper.clearContainer = function () {
            deviceInfoHandsontableHelper.AllData = {};
            deviceInfoHandsontableHelper.updatelist = [];
            deviceInfoHandsontableHelper.delidslist = [];
            deviceInfoHandsontableHelper.insertlist = [];
            deviceInfoHandsontableHelper.editWellNameList = [];
        }

        return deviceInfoHandsontableHelper;
    }
};

function CreateAndLoadPumoingModelInfoTable(deviceId,deviceName,isNew){
	if(isNew&&pumpingModelHandsontableHelper!=null){
		if(pumpingModelHandsontableHelper.hot!=undefined){
			pumpingModelHandsontableHelper.hot.destroy();
		}
		pumpingModelHandsontableHelper=null;
	}
	Ext.getCmp("PumpingModelListPanel_Id").el.mask(cosog.string.loading).show();
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/wellInformationManagerController/getPumpingModelList',
		success:function(response) {
			Ext.getCmp("PumpingModelListPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			var panelTitle='抽油机型号选择';
			if(isNotVal(deviceName)){
				panelTitle="抽油机井【<font color='red'>"+deviceName+"</font>】抽油机型号选择";
			}
			Ext.getCmp("PumpingModelListPanel_Id").setTitle(panelTitle);
			if(pumpingModelHandsontableHelper==null || pumpingModelHandsontableHelper.hot==undefined){
				pumpingModelHandsontableHelper = PumpingModelHandsontableHelper.createNew("PumpingModelListTableDiv_id");
				var colHeaders="['','序号','厂家','型号','','','']";
				var columns="[{data:'checked',type:'checkbox'},{data:'id'},{data:'manufacturer'},{data:'model'},{data:'realId'},{data:'stroke'},{data:'balanceWeight'}]";
				
				pumpingModelHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				pumpingModelHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					pumpingModelHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					pumpingModelHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				pumpingModelHandsontableHelper.hot.loadData(result.totalRoot);
			}
			
			pumpingModelHandsontableHelper.deviceId=deviceId;
	        pumpingModelHandsontableHelper.deviceName=deviceName;
	        pumpingModelHandsontableHelper.isNew=isNew;
			
			CreateAndLoadPumpingInfoTable(deviceId,deviceName,isNew);
		},
		failure:function(){
			Ext.getCmp("PumpingModelListPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			deviceId:deviceId,
			deviceType:101
        }
	});
};

var PumpingModelHandsontableHelper = {
		createNew: function (divid) {
	        var pumpingModelHandsontableHelper = {};
	        pumpingModelHandsontableHelper.hot1 = '';
	        pumpingModelHandsontableHelper.divid = divid;
	        pumpingModelHandsontableHelper.validresult=true;//数据校验
	        pumpingModelHandsontableHelper.colHeaders=[];
	        pumpingModelHandsontableHelper.columns=[];
	        pumpingModelHandsontableHelper.AllData=[];
	        
	        pumpingModelHandsontableHelper.deviceId;
	        pumpingModelHandsontableHelper.deviceName;
	        pumpingModelHandsontableHelper.isNew;
	        
	        pumpingModelHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        pumpingModelHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        pumpingModelHandsontableHelper.createTable = function (data) {
	        	$('#'+pumpingModelHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+pumpingModelHandsontableHelper.divid);
	        	pumpingModelHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [4,5,6],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	        		colWidths: [25,30,30,80],
	                columns:pumpingModelHandsontableHelper.columns,
	                columns:pumpingModelHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:pumpingModelHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    if (visualColIndex >0) {
							cellProperties.readOnly = true;
		                }
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	var selectedRow=row;
	                	var selectedCol=column;
	                	if(row>row2){
	                		selectedRow=row2;
	                	}
	                	if(column>column2){
	                		selectedCol=column2;
	                	}
	                	var checkboxColData=pumpingModelHandsontableHelper.hot.getDataAtCol(0);
	                	var rowdata = pumpingModelHandsontableHelper.hot.getDataAtRow(selectedRow);
	                	for(var i=0;i<checkboxColData.length;i++){
                			if(i!=selectedRow&&checkboxColData[i]){
                				pumpingModelHandsontableHelper.hot.setDataAtCell(i,0,false);
                			}
                		}
	                	pumpingModelHandsontableHelper.hot.setDataAtCell(selectedRow,0,true);
	        			CreateAndLoadPumpingInfoTable(pumpingModelHandsontableHelper.deviceId,pumpingModelHandsontableHelper.deviceName,pumpingModelHandsontableHelper.isNew);
	                	
	                }
	        	});
	        }
	        //保存数据
	        pumpingModelHandsontableHelper.saveData = function () {}
	        pumpingModelHandsontableHelper.clearContainer = function () {
	        	pumpingModelHandsontableHelper.AllData = [];
	        }
	        return pumpingModelHandsontableHelper;
	    }
};

function CreateAndLoadProductionDataTable(deviceId,deviceName,isNew){
	if(isNew&&productionHandsontableHelper!=null){
		if(productionHandsontableHelper.hot!=undefined){
			productionHandsontableHelper.hot.destroy();
		}
		productionHandsontableHelper=null;
	}
	Ext.getCmp("ProductionDataInfoPanel_Id").el.mask(cosog.string.loading).show();
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/wellInformationManagerController/getDeviceProductionDataInfo',
		success:function(response) {
			Ext.getCmp("ProductionDataInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			var applicationScenarios=result.applicationScenarios;
			var panelTitle='生产数据';
			if(isNotVal(deviceName)){
				panelTitle="抽油机井【<font color='red'>"+deviceName+"</font>】生产数据";
			}
			Ext.getCmp("ProductionDataInfoPanel_Id").setTitle(panelTitle);
			if(productionHandsontableHelper==null || productionHandsontableHelper.hot==undefined){
				productionHandsontableHelper = ProductionHandsontableHelper.createNew("AdditionalInfoTableDiv_id");
				productionHandsontableHelper.resultList = result.resultNameList;
				var colHeaders="['序号','名称','变量']";
				var columns="[{data:'id'}," 
					+"{data:'itemName'}," 
					+"{data:'itemValue',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,productionHandsontableHelper);}}" 
					+"]";
				productionHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				productionHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					productionHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					productionHandsontableHelper.pumpGrade=result.totalRoot[13].itemValue;
					productionHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				productionHandsontableHelper.resultList = result.resultNameList;
				if(result.totalRoot.length==0){
					productionHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					productionHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
			
			var hiddenRows=[];
			if(applicationScenarios==0){
				hiddenRows=[0,3,9,10];
			}
			const plugin = productionHandsontableHelper.hot.getPlugin('hiddenRows');
        	plugin.hideRows(hiddenRows);
        	productionHandsontableHelper.hot.render();
		},
		failure:function(){
			Ext.getCmp("ProductionDataInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			deviceId:deviceId,
			deviceType:101
        }
	});
};

var ProductionHandsontableHelper = {
	    createNew: function (divid) {
	        var productionHandsontableHelper = {};
	        productionHandsontableHelper.hot = '';
	        productionHandsontableHelper.divid = divid;
	        productionHandsontableHelper.colHeaders = [];
	        productionHandsontableHelper.columns = [];
	        productionHandsontableHelper.resultList = [];
	        productionHandsontableHelper.pumpGrade = '';
	        productionHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	        }
	        
	        productionHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }

	        productionHandsontableHelper.createTable = function (data) {
	            $('#' + productionHandsontableHelper.divid).empty();
	            var hotElement = document.querySelector('#' + productionHandsontableHelper.divid);
	            productionHandsontableHelper.hot = new Handsontable(hotElement, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: data,
	            	colWidths: [50,100,100],
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                hiddenRows: {
	                    rows: [],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                columns: productionHandsontableHelper.columns,
	                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false, //显示行头
	                colHeaders: productionHandsontableHelper.colHeaders, //显示列头
	                columnSorting: true, //允许排序
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    if (visualColIndex !=2) {
							cellProperties.readOnly = true;
							cellProperties.renderer = productionHandsontableHelper.addBoldBg;
		                }else if(visualRowIndex==39 && visualColIndex==2){
	                    	cellProperties.readOnly = true;
	                    }
	                    
//	                    if (visualColIndex === 2 && visualRowIndex===13) {
//	                    	this.type = 'dropdown';
//	                    	this.source = ['杆式泵','管式泵'];
//	                    	this.strict = true;
//	                    	this.allowInvalid = false;
//	                    }
	                    
	                    if (visualColIndex === 2 && visualRowIndex===13) {
	                    	this.type = 'dropdown';
	                    	this.source = ['组合泵','整筒泵'];
	                    	this.strict = true;
	                    	this.allowInvalid = false;
	                    }
	                    
	                    if (visualColIndex === 2 && visualRowIndex===14) {
	                    	var barrelType='';
	                    	if(isNotVal(productionHandsontableHelper.hot)){
	                    		barrelType=productionHandsontableHelper.hot.getDataAtCell(13,2);
	                    	}else{
	                    		barrelType=productionHandsontableHelper.pumpGrade;
	                    	}
	                    	var pumpGradeList=['1','2','3','4','5'];
	                    	if(barrelType==='组合泵'){
	                    		pumpGradeList=['1','2','3'];
	                    	}
	                    	this.type = 'dropdown';
	                    	this.source = pumpGradeList;
	                    	this.strict = true;
	                    	this.allowInvalid = false;
	                    }
	                    
	                    if (visualColIndex === 2 && (visualRowIndex===19 || visualRowIndex===23||visualRowIndex===27||visualRowIndex===31)) {
	                    	this.type = 'dropdown';
	                    	this.source = ['A','B','C','K','D','KD','HL','HY'];
	                    	this.strict = true;
	                    	this.allowInvalid = false;
	                    }
	                    
	                    if (visualColIndex === 2 && visualRowIndex===35) {
	                    	this.type = 'dropdown';
	                    	this.source = productionHandsontableHelper.resultList;
	                    	this.strict = true;
	                    	this.allowInvalid = false;
	                    }
	                    
	                    return cellProperties;
	                }
	            });
	        }
	        return productionHandsontableHelper;
	    }
	};

function CreateAndLoadPumpingInfoTable(deviceId,deviceName,isNew){
	if(isNew&&pumpingInfoHandsontableHelper!=null){
		if(pumpingInfoHandsontableHelper.hot!=undefined){
			pumpingInfoHandsontableHelper.hot.destroy();
		}
		pumpingInfoHandsontableHelper=null;
	}
	Ext.getCmp("PumpingInfoPanel_Id").el.mask(cosog.string.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/wellInformationManagerController/getDevicePumpingInfo',
		success:function(response) {
			Ext.getCmp("PumpingInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			var panelTitle='抽油机详情';
			if(isNotVal(deviceName)){
				panelTitle="抽油机井【<font color='red'>"+deviceName+"</font>】抽油机详情";
			}
			Ext.getCmp("PumpingInfoPanel_Id").setTitle(panelTitle);
			if(pumpingInfoHandsontableHelper==null || pumpingInfoHandsontableHelper.hot==undefined){
				pumpingInfoHandsontableHelper = PumpingInfoHandsontableHelper.createNew("PumpingInfoTableDiv_id");
				
				var checkboxColData=pumpingModelHandsontableHelper.hot.getDataAtCol(0);
	        	for(var i=0;i<checkboxColData.length;i++){
	    			if(checkboxColData[i]){
	    				var rowdata = pumpingModelHandsontableHelper.hot.getDataAtRow(i);
	    				if(pumpingInfoHandsontableHelper!=null && pumpingInfoHandsontableHelper.hot!=undefined){
	    	        		pumpingInfoHandsontableHelper.strokeList = rowdata[5];
	    	    	        pumpingInfoHandsontableHelper.balanceWeightList = rowdata[6];
	    	        	}
	    				break;
	    			}
	    		}
				
				var colHeaders="['序号','名称','变量','']";
				var columns="[{data:'id'}," 
					+"{data:'itemValue1',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,pumpingInfoHandsontableHelper);}}," 
					+"{data:'itemValue2',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,pumpingInfoHandsontableHelper);}}" 
					+"]";
				pumpingInfoHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				pumpingInfoHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					pumpingInfoHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{}]);
				}else{
					pumpingInfoHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				var checkboxColData=pumpingModelHandsontableHelper.hot.getDataAtCol(0);
	        	for(var i=0;i<checkboxColData.length;i++){
	    			if(checkboxColData[i]){
	    				var rowdata = pumpingModelHandsontableHelper.hot.getDataAtRow(i);
	    				if(pumpingInfoHandsontableHelper!=null && pumpingInfoHandsontableHelper.hot!=undefined){
	    	        		pumpingInfoHandsontableHelper.strokeList = rowdata[5];
	    	    	        pumpingInfoHandsontableHelper.balanceWeightList = rowdata[6];
	    	        	}
	    				break;
	    			}
	    		}
				if(result.totalRoot.length==0){
					pumpingInfoHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{}]);
				}else{
					pumpingInfoHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			deviceId:deviceId,
			deviceType:101
        }
	});
};

var PumpingInfoHandsontableHelper = {
	    createNew: function (divid) {
	        var pumpingInfoHandsontableHelper = {};
	        pumpingInfoHandsontableHelper.hot = '';
	        pumpingInfoHandsontableHelper.divid = divid;
	        pumpingInfoHandsontableHelper.colHeaders = [];
	        pumpingInfoHandsontableHelper.columns = [];
	        pumpingInfoHandsontableHelper.strokeList = [];
	        pumpingInfoHandsontableHelper.balanceWeightList = [];
	        pumpingInfoHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	        }
	        
	        pumpingInfoHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        pumpingInfoHandsontableHelper.processingStrokeData = function (instance, td, row, col, prop, value, cellProperties) {
	        	var showValue='';
	        	for(var i=0;i<pumpingInfoHandsontableHelper.strokeList.length;i++){
	        		if(parseFloat(value)==pumpingInfoHandsontableHelper.strokeList[i]){
	        			showValue=value;
	        			break;
	        		}
	        	}
	        	value=showValue;
	        }

	        pumpingInfoHandsontableHelper.createTable = function (data) {
	            $('#' + pumpingInfoHandsontableHelper.divid).empty();
	            var hotElement = document.querySelector('#' + pumpingInfoHandsontableHelper.divid);
	            pumpingInfoHandsontableHelper.hot = new Handsontable(hotElement, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: data,
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
//	                colWidths: [25,30,30,80],
	                columns: pumpingInfoHandsontableHelper.columns,
	                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false, //显示行头
	                colHeaders: pumpingInfoHandsontableHelper.colHeaders, //显示列头
	                columnSorting: true, //允许排序
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                nestedHeaders:[[{
	                	label:'序号'
	                },{
	                	label:'名称'
	                },{
	                	label:'变量',
	                	colspan:2
	                }]],
	                mergeCells: [{
	                	"row": 1,
                        "col": 1,
                        "rowspan": 1,
                        "colspan": 2
	                }],
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    if ( (visualRowIndex==0&&visualColIndex==1) || (visualRowIndex>=1&&visualRowIndex<=2)    ) {
							cellProperties.readOnly = true;
							cellProperties.renderer = pumpingInfoHandsontableHelper.addBoldBg;
		                }
	                    if (visualColIndex === 2 && visualRowIndex===0) {
	                    	this.type = 'dropdown';
	                    	this.source = pumpingInfoHandsontableHelper.strokeList;
	                    	this.strict = true;
	                    	this.allowInvalid = false;
//	                    	cellProperties.renderer = pumpingInfoHandsontableHelper.processingStrokeData;
	                    }
//	                    
	                    if (visualColIndex === 2 && visualRowIndex>=3) {
	                    	this.type = 'dropdown';
	                    	this.source = pumpingInfoHandsontableHelper.balanceWeightList;
	                    	this.strict = true;
	                    	this.allowInvalid = true;
	                    }
//	                    
//	                    if (visualColIndex === 2 && visualRowIndex===15) {
//	                    	this.type = 'dropdown';
//	                    	this.source = ['1','2','3','4','5'];
//	                    	this.strict = true;
//	                    	this.allowInvalid = false;
//	                    }
//	                    
//	                    if (visualColIndex === 2 && (visualRowIndex===20 || visualRowIndex===24||visualRowIndex===28||visualRowIndex===32)) {
//	                    	this.type = 'dropdown';
//	                    	this.source = ['A','B','C','K','D','KD','HL','HY'];
//	                    	this.strict = true;
//	                    	this.allowInvalid = false;
//	                    }
	                    
	                    return cellProperties;
	                }
	            });
	        }
	        return pumpingInfoHandsontableHelper;
	    }
	};

function CreateAndLoadVideoInfoTable(deviceId,deviceName,isNew){
//	if(isNew&&videoInfoHandsontableHelper!=null){
//		if(videoInfoHandsontableHelper.hot!=undefined){
//			videoInfoHandsontableHelper.hot.destroy();
//		}
//		videoInfoHandsontableHelper=null;
//	}
	if(videoInfoHandsontableHelper!=null && videoInfoHandsontableHelper.hot!=undefined){
		videoInfoHandsontableHelper.hot.destroy();
		videoInfoHandsontableHelper=null;
	}
	videoInfoHandsontableHelper=null;
	Ext.getCmp("VideoInfoPanel_Id").el.mask(cosog.string.loading).show();
	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/wellInformationManagerController/getDeviceVideoInfo',
		success:function(response) {
			Ext.getCmp("VideoInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			var panelTitle='视频配置';
			if(isNotVal(deviceName)){
				panelTitle="抽油机井【<font color='red'>"+deviceName+"</font>】视频配置";
			}
			Ext.getCmp("VideoInfoPanel_Id").setTitle(panelTitle);
			if(videoInfoHandsontableHelper==null || videoInfoHandsontableHelper.hot==undefined){
				videoInfoHandsontableHelper = VideoInfoHandsontableHelper.createNew("VideoInfoTableDiv_id");
				var colHeaders = "[";
                var columns = "[";
                var colWidths=[];
                for (var i = 0; i < result.columns.length; i++) {
                    colHeaders += "'" + result.columns[i].header + "'";
                    colWidths.push(result.columns[i].flex);
                    if (result.columns[i].dataIndex.toUpperCase() === "videoKey".toUpperCase()) {
                        var source = "[";
                        for (var j = 0; j < result.videoKeyList.length; j++) {
                            source += "\'" + result.videoKeyList[j] + "\'";
                            if (j < result.videoKeyList.length - 1) {
                                source += ",";
                            }
                        }
                        source += "]";
                        columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
                    }else {
                        columns += "{data:'" + result.columns[i].dataIndex + "'}";
                    }
                    if (i < result.columns.length - 1) {
                        colHeaders += ",";
                        columns += ",";
                    }
                }
                colHeaders += "]";
                columns += "]";
				
				videoInfoHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				videoInfoHandsontableHelper.columns=Ext.JSON.decode(columns);
				videoInfoHandsontableHelper.colWidths=colWidths;
				if(result.totalRoot.length==0){
					videoInfoHandsontableHelper.createTable([{},{},{},{}]);
				}else{
					videoInfoHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					videoInfoHandsontableHelper.hot.loadData([{},{},{},{}]);
				}else{
					videoInfoHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("VideoInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			deviceId:deviceId,
			deviceType:101,
			orgId: leftOrg_Id
        }
	});
};

var VideoInfoHandsontableHelper = {
	    createNew: function (divid) {
	        var videoInfoHandsontableHelper = {};
	        videoInfoHandsontableHelper.hot = '';
	        videoInfoHandsontableHelper.divid = divid;
	        videoInfoHandsontableHelper.colHeaders = [];
	        videoInfoHandsontableHelper.columns = [];
	        videoInfoHandsontableHelper.colWidths=[];
	        videoInfoHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	        }
	        
	        videoInfoHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }

	        videoInfoHandsontableHelper.createTable = function (data) {
	            $('#' + videoInfoHandsontableHelper.divid).empty();
	            var hotElement = document.querySelector('#' + videoInfoHandsontableHelper.divid);
	            videoInfoHandsontableHelper.hot = new Handsontable(hotElement, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: data,
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: videoInfoHandsontableHelper.colWidths,
	                columns: videoInfoHandsontableHelper.columns,
	                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false, //显示行头
	                colHeaders: videoInfoHandsontableHelper.colHeaders, //显示列头
	                columnSorting: true, //允许排序
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    if (visualColIndex < 2) {
							cellProperties.readOnly = true;
							cellProperties.renderer = videoInfoHandsontableHelper.addBoldBg;
		                }
	                    return cellProperties;
	                }
	            });
	        }
	        return videoInfoHandsontableHelper;
	    }
	};