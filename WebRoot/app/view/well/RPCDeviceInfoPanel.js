//抽油机井
var rpcDeviceInfoHandsontableHelper = null;
var rpcPumpingModelHandsontableHelper = null;
var rpcProductionHandsontableHelper = null;
var rpcPumpingInfoHandsontableHelper = null;
var rpcVideoInfoHandsontableHelper = null;
Ext.define('AP.view.well.RPCDeviceInfoPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.rpcDeviceInfoPanel',
    id: 'RPCDeviceInfoPanel_Id',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var rpcCombStore = new Ext.data.JsonStore({
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
                    var wellName = Ext.getCmp('rpcDeviceListComb_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceType: 101,
                        wellName: wellName
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });

        var rpcDeviceCombo = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.wellName,
                id: "rpcDeviceListComb_Id",
                labelWidth: 35,
                width: 145,
                labelAlign: 'left',
                queryMode: 'remote',
                typeAhead: true,
                store: rpcCombStore,
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
                        rpcDeviceCombo.getStore().loadPage(1); // 加载井下拉框的store
                    },
                    select: function (combo, record, index) {
                        try {
                            CreateAndLoadRPCDeviceInfoTable();
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
                	CreateAndLoadRPCDeviceInfoTable();
                }
    		},'-',rpcDeviceCombo,{
                id: 'RPCDeviceSelectRow_Id',
                xtype: 'textfield',
                value: 0,
                hidden: true
            },{
                id: 'RPCDeviceSelectEndRow_Id',
                xtype: 'textfield',
                value: 0,
                hidden: true
            }, '-', {
                xtype: 'button',
                text: cosog.string.search,
                iconCls: 'search',
                hidden: false,
                handler: function (v, o) {
                    CreateAndLoadRPCDeviceInfoTable();
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
                id: 'RPCDeviceTotalCount_Id',
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
                	
                	var window = Ext.create("AP.view.well.RPCDeviceInfoWindow", {
                        title: '添加设备'
                    });
                    window.show();
                    Ext.getCmp("rpcDeviceWinOgLabel_Id").setHtml("设备将添加到【<font color=red>"+selectedOrgName+"</font>】下,请确认<br/>&nbsp;");
                    Ext.getCmp("rpcDeviceType_Id").setValue(101);
                    Ext.getCmp("rpcDeviceOrg_Id").setValue(selectedOrgId);
                    Ext.getCmp("addFormRPCDevice_Id").show();
                    Ext.getCmp("updateFormRPCDevice_Id").hide();
                    return false;
    			}
    		}, '-',{
    			xtype: 'button',
    			id: 'deleteRPCDeviceNameBtn_Id',
    			text: '删除设备',
    			iconCls: 'delete',
    			handler: function (v, o) {
    				var startRow= Ext.getCmp("RPCDeviceSelectRow_Id").getValue();
    				var endRow= Ext.getCmp("RPCDeviceSelectEndRow_Id").getValue();
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
    	    						var rowdata = rpcDeviceInfoHandsontableHelper.hot.getDataAtRow(i);
    	    						if (rowdata[0] != null && parseInt(rowdata[0])>0) {
    	    		                    rpcDeviceInfoHandsontableHelper.delidslist.push(rowdata[0]);
    	    		                }
    	    					}
    	    					var saveData={};
    	    	            	saveData.updatelist=[];
    	    	            	saveData.insertlist=[];
    	    	            	saveData.delidslist=rpcDeviceInfoHandsontableHelper.delidslist;
    	    	            	Ext.Ajax.request({
    	    	                    method: 'POST',
    	    	                    url: context + '/wellInformationManagerController/saveWellHandsontableData',
    	    	                    success: function (response) {
    	    	                        rdata = Ext.JSON.decode(response.responseText);
    	    	                        if (rdata.success) {
    	    	                        	Ext.MessageBox.alert("信息", "删除成功");
    	    	                            //保存以后重置全局容器
    	    	                            rpcDeviceInfoHandsontableHelper.clearContainer();
    	    	                            Ext.getCmp("RPCDeviceSelectRow_Id").setValue(0);
    	    	                        	Ext.getCmp("RPCDeviceSelectEndRow_Id").setValue(0);
    	    	                            CreateAndLoadRPCDeviceInfoTable();
    	    	                        } else {
    	    	                            Ext.MessageBox.alert("信息", "数据保存失败");
    	    	                        }
    	    	                    },
    	    	                    failure: function () {
    	    	                        Ext.MessageBox.alert("信息", "请求失败");
    	    	                        rpcDeviceInfoHandsontableHelper.clearContainer();
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
                itemId: 'saveRPCDeviceDataBtnId',
                id: 'saveRPCDeviceDataBtn_Id',
                text: cosog.string.save,
                iconCls: 'save',
                handler: function (v, o) {
                    rpcDeviceInfoHandsontableHelper.saveData();
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
                    Ext.getCmp("batchAddDeviceWinOrgLabel_Id").setHtml("设备将添加到【<font color=red>"+selectedOrgName+"</font>】下,请确认");
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
        		title:'抽油机井列表',
        		header: true,
        		layout: 'fit',
        		id:'RPCDeviceTablePanel_id',
            	html: '<div class="RPCDeviceContainer" style="width:100%;height:100%;"><div class="con" id="RPCDeviceTableDiv_id" style="width:100%;height:100%;"></div></div>',
                listeners: {
                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if (rpcDeviceInfoHandsontableHelper != null && rpcDeviceInfoHandsontableHelper.hot != null && rpcDeviceInfoHandsontableHelper.hot != undefined) {
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                        	rpcDeviceInfoHandsontableHelper.hot.updateSettings({
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
                    	id:'RPCProductionDataInfoPanel_Id',
                    	split: true,
                    	collapsible: false,
                    	html: '<div class="RPCAdditionalInfoContainer" style="width:100%;height:100%;"><div class="con" id="RPCAdditionalInfoTableDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if (rpcProductionHandsontableHelper != null && rpcProductionHandsontableHelper.hot != null && rpcProductionHandsontableHelper.hot != undefined) {
//                            		rpcProductionHandsontableHelper.hot.refreshDimensions();
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		rpcProductionHandsontableHelper.hot.updateSettings({
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
                            id:'RPCPumpingModelListPanel_Id',
                            split: true,
                            collapsible: true,
                            html: '<div class="RPCPumpingModelListContainer" style="width:100%;height:100%;"><div class="con" id="RPCPumpingModelListTableDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if (rpcPumpingModelHandsontableHelper != null && rpcPumpingModelHandsontableHelper.hot != null && rpcPumpingModelHandsontableHelper.hot != undefined) {
//                                		rpcPumpingModelHandsontableHelper.hot.refreshDimensions();
                                		var newWidth=width;
                                		var newHeight=height;
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                		rpcPumpingModelHandsontableHelper.hot.updateSettings({
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
                        	id:'RPCPumpingInfoPanel_Id',
                            split: true,
                            collapsible: true,
                            html: '<div class="RPCPumpingInfoContainer" style="width:100%;height:100%;"><div class="con" id="RPCPumpingInfoTableDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if (rpcPumpingInfoHandsontableHelper != null && rpcPumpingInfoHandsontableHelper.hot != null && rpcPumpingInfoHandsontableHelper.hot != undefined) {
//                                		rpcPumpingInfoHandsontableHelper.hot.refreshDimensions();
                                		var newWidth=width;
                                		var newHeight=height;
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                		rpcPumpingInfoHandsontableHelper.hot.updateSettings({
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
                	id:'RPCVideoInfoPanel_Id',
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
                	html: '<div class="RPCVideoInfoContainer" style="width:100%;height:100%;"><div class="con" id="RPCVideoInfoTableDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	if (rpcVideoInfoHandsontableHelper != null && rpcVideoInfoHandsontableHelper.hot != null && rpcVideoInfoHandsontableHelper.hot != undefined) {
//                        		rpcVideoInfoHandsontableHelper.hot.refreshDimensions();
                        		var newWidth=width;
                        		var newHeight=height;
                        		var header=thisPanel.getHeader();
                        		if(header){
                        			newHeight=newHeight-header.lastBox.height-2;
                        		}
                        		rpcVideoInfoHandsontableHelper.hot.updateSettings({
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

function CreateAndLoadRPCDeviceInfoTable(isNew) {
	if(isNew&&rpcDeviceInfoHandsontableHelper!=null){
		if (rpcDeviceInfoHandsontableHelper.hot != undefined) {
			rpcDeviceInfoHandsontableHelper.hot.destroy();
		}
		rpcDeviceInfoHandsontableHelper = null;
	}
    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
    var wellInformationName_Id = Ext.getCmp('rpcDeviceListComb_Id').getValue();
    Ext.getCmp("RPCDeviceTablePanel_id").el.mask(cosog.string.loading).show();
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/wellInformationManagerController/doWellInformationShow',
        success: function (response) {
        	Ext.getCmp("RPCDeviceTablePanel_id").getEl().unmask();
        	var result = Ext.JSON.decode(response.responseText);
            if (rpcDeviceInfoHandsontableHelper == null || rpcDeviceInfoHandsontableHelper.hot == null || rpcDeviceInfoHandsontableHelper.hot == undefined) {
                rpcDeviceInfoHandsontableHelper = RPCDeviceInfoHandsontableHelper.createNew("RPCDeviceTableDiv_id");
                rpcDeviceInfoHandsontableHelper.dataLength=result.totalCount;
                var colHeaders = "[";
                var columns = "[";

                for (var i = 0; i < result.columns.length; i++) {
                    colHeaders += "'" + result.columns[i].header + "'";
                    if (result.columns[i].dataIndex.toUpperCase() === "orgName".toUpperCase()) {
                        columns += "{data:'" + result.columns[i].dataIndex + "',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Org(val, callback,this.row, this.col,rpcDeviceInfoHandsontableHelper);}}";
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
                        columns += "{data:'" + result.columns[i].dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,rpcDeviceInfoHandsontableHelper);}}";
                    } else if (result.columns[i].dataIndex.toUpperCase() === "ipPort".toUpperCase()) {
                    	columns += "{data:'" + result.columns[i].dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_IpPort_Nullable(val, callback,this.row, this.col,rpcDeviceInfoHandsontableHelper);}}";
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
                rpcDeviceInfoHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
                rpcDeviceInfoHandsontableHelper.columns = Ext.JSON.decode(columns);
                if(result.totalRoot.length==0){
                	rpcDeviceInfoHandsontableHelper.hiddenRows = [0];
                	rpcDeviceInfoHandsontableHelper.createTable([{}]);
                }else{
                	rpcDeviceInfoHandsontableHelper.hiddenRows = [];
                	rpcDeviceInfoHandsontableHelper.createTable(result.totalRoot);
                }
            } else {
            	rpcDeviceInfoHandsontableHelper.dataLength=result.totalCount;
            	if(result.totalRoot.length==0){
            		rpcDeviceInfoHandsontableHelper.hiddenRows = [0];
            		rpcDeviceInfoHandsontableHelper.hot.loadData([{}]);
            	}else{
            		rpcDeviceInfoHandsontableHelper.hiddenRows = [];
            		rpcDeviceInfoHandsontableHelper.hot.loadData(result.totalRoot);
            	}
            }
            if(rpcDeviceInfoHandsontableHelper.hiddenRows.length>0){
            	const plugin = rpcDeviceInfoHandsontableHelper.hot.getPlugin('hiddenRows');
            	plugin.hideRows(rpcDeviceInfoHandsontableHelper.hiddenRows);
            	rpcDeviceInfoHandsontableHelper.hot.render();
            }
            if(result.totalRoot.length==0){
            	Ext.getCmp("RPCDeviceSelectRow_Id").setValue('');
            	Ext.getCmp("RPCDeviceSelectEndRow_Id").setValue('');
            	CreateAndLoadRPCPumoingModelInfoTable(0,'');
            	CreateAndLoadRPCProductionDataTable(0,'');
            	CreateAndLoadRPCVideoInfoTable(0,'');
            }else{
            	var selectedDeviceId=parseInt(Ext.getCmp("selectedRPCDeviceId_global").getValue());
            	var selectRow=0;
            	for(var i=0;i<result.totalRoot.length;i++){
            		if(result.totalRoot[i].id==selectedDeviceId){
            			selectRow=i;
            			break;
            		}
            	}
            	Ext.getCmp("RPCDeviceSelectRow_Id").setValue(selectRow);
            	var rowdata = rpcDeviceInfoHandsontableHelper.hot.getDataAtRow(selectRow);
            	
            	var combDeviceName=Ext.getCmp('rpcDeviceListComb_Id').getValue();
        		if(combDeviceName!=''){
            		Ext.getCmp("selectedRPCDeviceId_global").setValue(rowdata[0]);
        		}

            	CreateAndLoadRPCPumoingModelInfoTable(rowdata[0],rowdata[1]);
            	CreateAndLoadRPCProductionDataTable(rowdata[0],rowdata[1]);
            	CreateAndLoadRPCVideoInfoTable(rowdata[0],rowdata[1]);
            }
            Ext.getCmp("RPCDeviceTotalCount_Id").update({
                count: result.totalCount
            });
        },
        failure: function () {
        	Ext.getCmp("RPCDeviceTablePanel_id").getEl().unmask();
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

var RPCDeviceInfoHandsontableHelper = {
    createNew: function (divid) {
        var rpcDeviceInfoHandsontableHelper = {};
        rpcDeviceInfoHandsontableHelper.hot = '';
        rpcDeviceInfoHandsontableHelper.divid = divid;
        rpcDeviceInfoHandsontableHelper.validresult = true; //数据校验
        rpcDeviceInfoHandsontableHelper.colHeaders = [];
        rpcDeviceInfoHandsontableHelper.columns = [];
        rpcDeviceInfoHandsontableHelper.dataLength = 0;
        rpcDeviceInfoHandsontableHelper.hiddenRows = [];

        rpcDeviceInfoHandsontableHelper.AllData = {};
        rpcDeviceInfoHandsontableHelper.updatelist = [];
        rpcDeviceInfoHandsontableHelper.delidslist = [];
        rpcDeviceInfoHandsontableHelper.insertlist = [];
        rpcDeviceInfoHandsontableHelper.editWellNameList = [];

        rpcDeviceInfoHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
        }

        rpcDeviceInfoHandsontableHelper.createTable = function (data) {
            $('#' + rpcDeviceInfoHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + rpcDeviceInfoHandsontableHelper.divid);
            rpcDeviceInfoHandsontableHelper.hot = new Handsontable(hotElement, {
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
                columns: rpcDeviceInfoHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
//                autoWrapRow: true,
                rowHeaders: true, //显示行头
                colHeaders: rpcDeviceInfoHandsontableHelper.colHeaders, //显示列头
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
                    if(rpcDeviceInfoHandsontableHelper.dataLength==0){
                    	cellProperties.readOnly = true;
                    }else if(rpcDeviceInfoHandsontableHelper.hot!=undefined && rpcDeviceInfoHandsontableHelper.hot.getDataAtCell!=undefined){
                    	var columns=rpcDeviceInfoHandsontableHelper.columns;
                    	if(prop.toUpperCase() === "signInId".toUpperCase() || prop.toUpperCase() === "ipPort".toUpperCase()){
                    		var tcpTypeColIndex=-1;
                    		for(var i=0;i<columns.length;i++){
                    			if(columns[i].data.toUpperCase() === "tcpType".toUpperCase()){
                    				tcpTypeColIndex=i;
                    				break;
                            	}
                    		}
                    		if(tcpTypeColIndex>=0){
                    			var tcpType=rpcDeviceInfoHandsontableHelper.hot.getDataAtCell(row,tcpTypeColIndex);
//                    			var cell = rpcDeviceInfoHandsontableHelper.hot.getCell(row, col);  
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
                		Ext.getCmp("RPCDeviceSelectRow_Id").setValue('');
                    	Ext.getCmp("RPCDeviceSelectEndRow_Id").setValue('');
                    	CreateAndLoadRPCPumoingModelInfoTable(0,'');
                    	CreateAndLoadRPCProductionDataTable(0,'');
                    	CreateAndLoadRPCVideoInfoTable(0,'');
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
                    	
                    	var selectedRow=Ext.getCmp("RPCDeviceSelectRow_Id").getValue();
                    	if(selectedRow!=startRow){
                    		var row1=rpcDeviceInfoHandsontableHelper.hot.getDataAtRow(startRow);
                        	var recordId=0;
                        	var deviceName='';
                        	if(isNotVal(row1[0])){
                        		recordId=row1[0];
                        	}
                        	if(isNotVal(row1[1])){
                        		deviceName=row1[1];
                        	}
                        	
                        	CreateAndLoadRPCPumoingModelInfoTable(recordId,deviceName);
                        	CreateAndLoadRPCProductionDataTable(recordId,deviceName);
                        	CreateAndLoadRPCVideoInfoTable(recordId,deviceName);
                        	
                        	Ext.getCmp("selectedRPCDeviceId_global").setValue(recordId);
                    	}
                    	Ext.getCmp("RPCDeviceSelectRow_Id").setValue(startRow);
                    	Ext.getCmp("RPCDeviceSelectEndRow_Id").setValue(endRow);
                	}
                },
                afterDestroy: function () {
                },
                beforeRemoveRow: function (index, amount) {
                    var ids = [];
                    //封装id成array传入后台
                    if (amount != 0) {
                        for (var i = index; i < amount + index; i++) {
                            var rowdata = rpcDeviceInfoHandsontableHelper.hot.getDataAtRow(i);
                            ids.push(rowdata[0]);
                        }
                        rpcDeviceInfoHandsontableHelper.delExpressCount(ids);
                        rpcDeviceInfoHandsontableHelper.screening();
                    }
                },
                afterChange: function (changes, source) {
                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
                    if (changes != null) {
                        for (var i = 0; i < changes.length; i++) {
                            var params = [];
                            var index = changes[i][0]; //行号码
                            var rowdata = rpcDeviceInfoHandsontableHelper.hot.getDataAtRow(index);
                            params.push(rowdata[0]);
                            params.push(changes[i][1]);
                            params.push(changes[i][2]);
                            params.push(changes[i][3]);

                            //仅当单元格发生改变的时候,id!=null,说明是更新
                            if (params[2] != params[3] && params[0] != null && params[0] > 0) {
                                var data = "{";
                                for (var j = 0; j < rpcDeviceInfoHandsontableHelper.columns.length; j++) {
                                    data += rpcDeviceInfoHandsontableHelper.columns[j].data + ":'" + rowdata[j] + "'";
                                    if (j < rpcDeviceInfoHandsontableHelper.columns.length - 1) {
                                        data += ","
                                    }
                                }
                                data += "}"
                                rpcDeviceInfoHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
                                
                                if(params[1] == "applicationScenariosName"){
                                	const plugin = rpcProductionHandsontableHelper.hot.getPlugin('hiddenRows');
                                	var hiddenRows=[0,3,9,10];
                                	if(sceneConfig=='cbm' || (sceneConfig=='all'&& params[3] == "煤层气井") ){
                                		plugin.hideRows(hiddenRows);
                                		rpcProductionHandsontableHelper.hot.setDataAtCell(4,1,'煤层中部深度(m)');
                                		rpcProductionHandsontableHelper.hot.setDataAtCell(5,1,'煤层中部温度(℃)');
                                	}else{
                                		plugin.showRows(hiddenRows);
                                		rpcProductionHandsontableHelper.hot.setDataAtCell(4,1,'油层中部深度(m)');
                                		rpcProductionHandsontableHelper.hot.setDataAtCell(5,1,'油层中部温度(℃)');
                                	}
                                	rpcProductionHandsontableHelper.hot.render();
                                }
                            }
                        }
                    }
                }
            });
        }
        //插入的数据的获取
        rpcDeviceInfoHandsontableHelper.insertExpressCount = function () {
            var idsdata = rpcDeviceInfoHandsontableHelper.hot.getDataAtCol(0); //所有的id
            for (var i = 0; i < idsdata.length; i++) {
                //id=null时,是插入数据,此时的i正好是行号
                if (idsdata[i] == null || idsdata[i] < 0) {
                    //获得id=null时的所有数据封装进data
                    var rowdata = rpcDeviceInfoHandsontableHelper.hot.getDataAtRow(i);
                    //var collength = hot.countCols();
                    if (rowdata != null) {
                        var data = "{";
                        for (var j = 0; j < rpcDeviceInfoHandsontableHelper.columns.length; j++) {
                            data += rpcDeviceInfoHandsontableHelper.columns[j].data + ":'" + rowdata[j] + "'";
                            if (j < rpcDeviceInfoHandsontableHelper.columns.length - 1) {
                                data += ","
                            }
                        }
                        data += "}"
                        rpcDeviceInfoHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
                    }
                }
            }
            if (rpcDeviceInfoHandsontableHelper.insertlist.length != 0) {
                rpcDeviceInfoHandsontableHelper.AllData.insertlist = rpcDeviceInfoHandsontableHelper.insertlist;
            }
        }
        //保存数据
        rpcDeviceInfoHandsontableHelper.saveData = function () {
        	var rpcDeviceInfoHandsontableData=rpcDeviceInfoHandsontableHelper.hot.getData();
        	if(rpcDeviceInfoHandsontableData.length>0){
        		var leftOrg_Name=Ext.getCmp("leftOrg_Name").getValue();
            	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
                //插入的数据的获取
                rpcDeviceInfoHandsontableHelper.insertExpressCount();
                //获取设备ID
                var RPCDeviceSelectRow= Ext.getCmp("RPCDeviceSelectRow_Id").getValue();
                var rowdata = rpcDeviceInfoHandsontableHelper.hot.getDataAtRow(RPCDeviceSelectRow);
            	var deviceId=rowdata[0];
            	var applicationScenariosIndex=-1;
            	var applicationScenarios=1;
            	
            	for (var i = 0; i < rpcDeviceInfoHandsontableHelper.columns.length; i++) {
            		if(rpcDeviceInfoHandsontableHelper.columns[i].data.toUpperCase()=='applicationScenariosName'.toUpperCase()){
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
                if(rpcProductionHandsontableHelper!=null && rpcProductionHandsontableHelper.hot!=undefined){
            		var productionHandsontableData=rpcProductionHandsontableHelper.hot.getData();
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
                	if(rpcPumpingModelHandsontableHelper!=null && rpcPumpingModelHandsontableHelper.hot!=undefined){
                		var rpcPumpingModelHandsontableData=rpcPumpingModelHandsontableHelper.hot.getData();
                    	for(var i=0;i<rpcPumpingModelHandsontableData.length;i++){
                    		if (rpcPumpingModelHandsontableData[i][0]) {
                            	pumpingModelId = rpcPumpingModelHandsontableData[i][4];
                            	break;
                            }
                    	}
                	}
            	}
                
                //抽油机详情
                var balanceInfo={};
                var stroke="";
                if(rpcPumpingInfoHandsontableHelper!=null && rpcPumpingInfoHandsontableHelper.hot!=undefined){
                	var rpcPumpingInfoHandsontableData=rpcPumpingInfoHandsontableHelper.hot.getData();
                	stroke=rpcPumpingInfoHandsontableData[0][2];
                	balanceInfo.EveryBalance=[];
                	for(var i=3;i<rpcPumpingInfoHandsontableData.length;i++){
                		if(isNotVal(rpcPumpingInfoHandsontableData[i][1]) || isNotVal(rpcPumpingInfoHandsontableData[i][2])){
                    		var EveryBalance={};
                    		EveryBalance.Position=rpcPumpingInfoHandsontableData[i][1];
                    		EveryBalance.Weight=rpcPumpingInfoHandsontableData[i][2];
                    		balanceInfo.EveryBalance.push(EveryBalance);
                    	}
                	}
                }
                //视频信息
                var videoUrl1='';
                var videoUrl2='';
                var videoKeyName1='';
                var videoKeyName2='';
                
                if(rpcVideoInfoHandsontableHelper!=null && rpcVideoInfoHandsontableHelper.hot!=undefined){
                	var rpcVideoInfoHandsontableData=rpcVideoInfoHandsontableHelper.hot.getData();
                	videoUrl1=rpcVideoInfoHandsontableData[0][2];
                	videoKeyName1=rpcVideoInfoHandsontableData[0][3];
                	videoUrl2=rpcVideoInfoHandsontableData[1][2];
                	videoKeyName2=rpcVideoInfoHandsontableData[1][3];
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
                            	rpcDeviceInfoHandsontableHelper.clearContainer();
                            	CreateAndLoadRPCDeviceInfoTable();
                            }
                        } else {
                            Ext.MessageBox.alert("信息", "数据保存失败");
                        }
                    },
                    failure: function () {
                        Ext.MessageBox.alert("信息", "请求失败");
                        rpcDeviceInfoHandsontableHelper.clearContainer();
                    },
                    params: {
                    	deviceId: deviceId,
                    	data: JSON.stringify(rpcDeviceInfoHandsontableHelper.AllData),
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
        rpcDeviceInfoHandsontableHelper.editWellName = function () {
            //插入的数据的获取
            if (rpcDeviceInfoHandsontableHelper.editWellNameList.length > 0 && rpcDeviceInfoHandsontableHelper.validresult) {
                Ext.Ajax.request({
                    method: 'POST',
                    url: context + '/wellInformationManagerController/editWellName',
                    success: function (response) {
                        rdata = Ext.JSON.decode(response.responseText);
                        if (rdata.success) {
                            Ext.MessageBox.alert("信息", "保存成功");
                            rpcDeviceInfoHandsontableHelper.clearContainer();
                            CreateAndLoadRPCDeviceInfoTable();
                        } else {
                            Ext.MessageBox.alert("信息", "数据保存失败");
                        }
                    },
                    failure: function () {
                        Ext.MessageBox.alert("信息", "请求失败");
                        rpcDeviceInfoHandsontableHelper.clearContainer();
                    },
                    params: {
                        data: JSON.stringify(rpcDeviceInfoHandsontableHelper.editWellNameList),
                        deviceType:101
                    }
                });
            } else {
                if (!rpcDeviceInfoHandsontableHelper.validresult) {
                    Ext.MessageBox.alert("信息", "数据类型错误");
                } else {
                    Ext.MessageBox.alert("信息", "无数据变化");
                }
            }
        }


        //删除的优先级最高
        rpcDeviceInfoHandsontableHelper.delExpressCount = function (ids) {
            //传入的ids.length不可能为0
            $.each(ids, function (index, id) {
                if (id != null) {
                    rpcDeviceInfoHandsontableHelper.delidslist.push(id);
                }
            });
            rpcDeviceInfoHandsontableHelper.AllData.delidslist = rpcDeviceInfoHandsontableHelper.delidslist;
        }

        //updatelist数据更新
        rpcDeviceInfoHandsontableHelper.screening = function () {
            if (rpcDeviceInfoHandsontableHelper.updatelist.length != 0 && rpcDeviceInfoHandsontableHelper.delidslist.lentgh != 0) {
                for (var i = 0; i < rpcDeviceInfoHandsontableHelper.delidslist.length; i++) {
                    for (var j = 0; j < rpcDeviceInfoHandsontableHelper.updatelist.length; j++) {
                        if (rpcDeviceInfoHandsontableHelper.updatelist[j].id == rpcDeviceInfoHandsontableHelper.delidslist[i]) {
                            //更新updatelist
                            rpcDeviceInfoHandsontableHelper.updatelist.splice(j, 1);
                        }
                    }
                }
                //把updatelist封装进AllData
                rpcDeviceInfoHandsontableHelper.AllData.updatelist = rpcDeviceInfoHandsontableHelper.updatelist;
            }
        }

        //更新数据
        rpcDeviceInfoHandsontableHelper.updateExpressCount = function (data) {
            if (JSON.stringify(data) != "{}") {
                var flag = true;
                //判断记录是否存在,更新数据     
                $.each(rpcDeviceInfoHandsontableHelper.updatelist, function (index, node) {
                    if (node.id == data.id) {
                        //此记录已经有了
                        flag = false;
                        //用新得到的记录替换原来的,不用新增
                        rpcDeviceInfoHandsontableHelper.updatelist[index] = data;
                    }
                });
                flag && rpcDeviceInfoHandsontableHelper.updatelist.push(data);
                //封装
                rpcDeviceInfoHandsontableHelper.AllData.updatelist = rpcDeviceInfoHandsontableHelper.updatelist;
            }
        }

        rpcDeviceInfoHandsontableHelper.clearContainer = function () {
            rpcDeviceInfoHandsontableHelper.AllData = {};
            rpcDeviceInfoHandsontableHelper.updatelist = [];
            rpcDeviceInfoHandsontableHelper.delidslist = [];
            rpcDeviceInfoHandsontableHelper.insertlist = [];
            rpcDeviceInfoHandsontableHelper.editWellNameList = [];
        }

        return rpcDeviceInfoHandsontableHelper;
    }
};

function CreateAndLoadRPCPumoingModelInfoTable(deviceId,deviceName,isNew){
	if(isNew&&rpcPumpingModelHandsontableHelper!=null){
		if(rpcPumpingModelHandsontableHelper.hot!=undefined){
			rpcPumpingModelHandsontableHelper.hot.destroy();
		}
		rpcPumpingModelHandsontableHelper=null;
	}
	Ext.getCmp("RPCPumpingModelListPanel_Id").el.mask(cosog.string.loading).show();
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/wellInformationManagerController/getRPCPumpingModelList',
		success:function(response) {
			Ext.getCmp("RPCPumpingModelListPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			var panelTitle='抽油机型号选择';
			if(isNotVal(deviceName)){
				panelTitle="抽油机井【<font color='red'>"+deviceName+"</font>】抽油机型号选择";
			}
			Ext.getCmp("RPCPumpingModelListPanel_Id").setTitle(panelTitle);
			if(rpcPumpingModelHandsontableHelper==null || rpcPumpingModelHandsontableHelper.hot==undefined){
				rpcPumpingModelHandsontableHelper = RPCPumpingModelHandsontableHelper.createNew("RPCPumpingModelListTableDiv_id");
				var colHeaders="['','序号','厂家','型号','','','']";
				var columns="[{data:'checked',type:'checkbox'},{data:'id'},{data:'manufacturer'},{data:'model'},{data:'realId'},{data:'stroke'},{data:'balanceWeight'}]";
				
				rpcPumpingModelHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				rpcPumpingModelHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					rpcPumpingModelHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					rpcPumpingModelHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				rpcPumpingModelHandsontableHelper.hot.loadData(result.totalRoot);
			}
			
			rpcPumpingModelHandsontableHelper.deviceId=deviceId;
	        rpcPumpingModelHandsontableHelper.deviceName=deviceName;
	        rpcPumpingModelHandsontableHelper.isNew=isNew;
			
			CreateAndLoadRPCPumpingInfoTable(deviceId,deviceName,isNew);
		},
		failure:function(){
			Ext.getCmp("RPCPumpingModelListPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			deviceId:deviceId,
			deviceType:101
        }
	});
};

var RPCPumpingModelHandsontableHelper = {
		createNew: function (divid) {
	        var rpcPumpingModelHandsontableHelper = {};
	        rpcPumpingModelHandsontableHelper.hot1 = '';
	        rpcPumpingModelHandsontableHelper.divid = divid;
	        rpcPumpingModelHandsontableHelper.validresult=true;//数据校验
	        rpcPumpingModelHandsontableHelper.colHeaders=[];
	        rpcPumpingModelHandsontableHelper.columns=[];
	        rpcPumpingModelHandsontableHelper.AllData=[];
	        
	        rpcPumpingModelHandsontableHelper.deviceId;
	        rpcPumpingModelHandsontableHelper.deviceName;
	        rpcPumpingModelHandsontableHelper.isNew;
	        
	        rpcPumpingModelHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        rpcPumpingModelHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        rpcPumpingModelHandsontableHelper.createTable = function (data) {
	        	$('#'+rpcPumpingModelHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+rpcPumpingModelHandsontableHelper.divid);
	        	rpcPumpingModelHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [4,5,6],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	        		colWidths: [25,30,30,80],
	                columns:rpcPumpingModelHandsontableHelper.columns,
	                columns:rpcPumpingModelHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:rpcPumpingModelHandsontableHelper.colHeaders,//显示列头
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
	                	var checkboxColData=rpcPumpingModelHandsontableHelper.hot.getDataAtCol(0);
	                	var rowdata = rpcPumpingModelHandsontableHelper.hot.getDataAtRow(selectedRow);
	                	for(var i=0;i<checkboxColData.length;i++){
                			if(i!=selectedRow&&checkboxColData[i]){
                				rpcPumpingModelHandsontableHelper.hot.setDataAtCell(i,0,false);
                			}
                		}
	                	rpcPumpingModelHandsontableHelper.hot.setDataAtCell(selectedRow,0,true);
	        			CreateAndLoadRPCPumpingInfoTable(rpcPumpingModelHandsontableHelper.deviceId,rpcPumpingModelHandsontableHelper.deviceName,rpcPumpingModelHandsontableHelper.isNew);
	                	
	                }
	        	});
	        }
	        //保存数据
	        rpcPumpingModelHandsontableHelper.saveData = function () {}
	        rpcPumpingModelHandsontableHelper.clearContainer = function () {
	        	rpcPumpingModelHandsontableHelper.AllData = [];
	        }
	        return rpcPumpingModelHandsontableHelper;
	    }
};

function CreateAndLoadRPCProductionDataTable(deviceId,deviceName,isNew){
	if(isNew&&rpcProductionHandsontableHelper!=null){
		if(rpcProductionHandsontableHelper.hot!=undefined){
			rpcProductionHandsontableHelper.hot.destroy();
		}
		rpcProductionHandsontableHelper=null;
	}
	Ext.getCmp("RPCProductionDataInfoPanel_Id").el.mask(cosog.string.loading).show();
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/wellInformationManagerController/getDeviceProductionDataInfo',
		success:function(response) {
			Ext.getCmp("RPCProductionDataInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			var applicationScenarios=result.applicationScenarios;
			var panelTitle='生产数据';
			if(isNotVal(deviceName)){
				panelTitle="抽油机井【<font color='red'>"+deviceName+"</font>】生产数据";
			}
			Ext.getCmp("RPCProductionDataInfoPanel_Id").setTitle(panelTitle);
			if(rpcProductionHandsontableHelper==null || rpcProductionHandsontableHelper.hot==undefined){
				rpcProductionHandsontableHelper = RPCProductionHandsontableHelper.createNew("RPCAdditionalInfoTableDiv_id");
				rpcProductionHandsontableHelper.resultList = result.resultNameList;
				var colHeaders="['序号','名称','变量']";
				var columns="[{data:'id'}," 
					+"{data:'itemName'}," 
					+"{data:'itemValue',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,rpcProductionHandsontableHelper);}}" 
					+"]";
				rpcProductionHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				rpcProductionHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					rpcProductionHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					rpcProductionHandsontableHelper.pumpGrade=result.totalRoot[13].itemValue;
					rpcProductionHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				rpcProductionHandsontableHelper.resultList = result.resultNameList;
				if(result.totalRoot.length==0){
					rpcProductionHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					rpcProductionHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
			
			var hiddenRows=[];
			if(applicationScenarios==0){
				hiddenRows=[0,3,9,10];
			}
			const plugin = rpcProductionHandsontableHelper.hot.getPlugin('hiddenRows');
        	plugin.hideRows(hiddenRows);
        	rpcProductionHandsontableHelper.hot.render();
		},
		failure:function(){
			Ext.getCmp("RPCProductionDataInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			deviceId:deviceId,
			deviceType:101
        }
	});
};

var RPCProductionHandsontableHelper = {
	    createNew: function (divid) {
	        var rpcProductionHandsontableHelper = {};
	        rpcProductionHandsontableHelper.hot = '';
	        rpcProductionHandsontableHelper.divid = divid;
	        rpcProductionHandsontableHelper.colHeaders = [];
	        rpcProductionHandsontableHelper.columns = [];
	        rpcProductionHandsontableHelper.resultList = [];
	        rpcProductionHandsontableHelper.pumpGrade = '';
	        rpcProductionHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	        }
	        
	        rpcProductionHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }

	        rpcProductionHandsontableHelper.createTable = function (data) {
	            $('#' + rpcProductionHandsontableHelper.divid).empty();
	            var hotElement = document.querySelector('#' + rpcProductionHandsontableHelper.divid);
	            rpcProductionHandsontableHelper.hot = new Handsontable(hotElement, {
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
	                columns: rpcProductionHandsontableHelper.columns,
	                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false, //显示行头
	                colHeaders: rpcProductionHandsontableHelper.colHeaders, //显示列头
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
							cellProperties.renderer = rpcProductionHandsontableHelper.addBoldBg;
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
	                    	if(isNotVal(rpcProductionHandsontableHelper.hot)){
	                    		barrelType=rpcProductionHandsontableHelper.hot.getDataAtCell(13,2);
	                    	}else{
	                    		barrelType=rpcProductionHandsontableHelper.pumpGrade;
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
	                    	this.source = rpcProductionHandsontableHelper.resultList;
	                    	this.strict = true;
	                    	this.allowInvalid = false;
	                    }
	                    
	                    return cellProperties;
	                }
	            });
	        }
	        return rpcProductionHandsontableHelper;
	    }
	};

function CreateAndLoadRPCPumpingInfoTable(deviceId,deviceName,isNew){
	if(isNew&&rpcPumpingInfoHandsontableHelper!=null){
		if(rpcPumpingInfoHandsontableHelper.hot!=undefined){
			rpcPumpingInfoHandsontableHelper.hot.destroy();
		}
		rpcPumpingInfoHandsontableHelper=null;
	}
	Ext.getCmp("RPCPumpingInfoPanel_Id").el.mask(cosog.string.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/wellInformationManagerController/getDevicePumpingInfo',
		success:function(response) {
			Ext.getCmp("RPCPumpingInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			var panelTitle='抽油机详情';
			if(isNotVal(deviceName)){
				panelTitle="抽油机井【<font color='red'>"+deviceName+"</font>】抽油机详情";
			}
			Ext.getCmp("RPCPumpingInfoPanel_Id").setTitle(panelTitle);
			if(rpcPumpingInfoHandsontableHelper==null || rpcPumpingInfoHandsontableHelper.hot==undefined){
				rpcPumpingInfoHandsontableHelper = RPCPumpingInfoHandsontableHelper.createNew("RPCPumpingInfoTableDiv_id");
				
				var checkboxColData=rpcPumpingModelHandsontableHelper.hot.getDataAtCol(0);
	        	for(var i=0;i<checkboxColData.length;i++){
	    			if(checkboxColData[i]){
	    				var rowdata = rpcPumpingModelHandsontableHelper.hot.getDataAtRow(i);
	    				if(rpcPumpingInfoHandsontableHelper!=null && rpcPumpingInfoHandsontableHelper.hot!=undefined){
	    	        		rpcPumpingInfoHandsontableHelper.strokeList = rowdata[5];
	    	    	        rpcPumpingInfoHandsontableHelper.balanceWeightList = rowdata[6];
	    	        	}
	    				break;
	    			}
	    		}
				
				var colHeaders="['序号','名称','变量','']";
				var columns="[{data:'id'}," 
					+"{data:'itemValue1',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,rpcPumpingInfoHandsontableHelper);}}," 
					+"{data:'itemValue2',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,rpcPumpingInfoHandsontableHelper);}}" 
					+"]";
				rpcPumpingInfoHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				rpcPumpingInfoHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					rpcPumpingInfoHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{}]);
				}else{
					rpcPumpingInfoHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				var checkboxColData=rpcPumpingModelHandsontableHelper.hot.getDataAtCol(0);
	        	for(var i=0;i<checkboxColData.length;i++){
	    			if(checkboxColData[i]){
	    				var rowdata = rpcPumpingModelHandsontableHelper.hot.getDataAtRow(i);
	    				if(rpcPumpingInfoHandsontableHelper!=null && rpcPumpingInfoHandsontableHelper.hot!=undefined){
	    	        		rpcPumpingInfoHandsontableHelper.strokeList = rowdata[5];
	    	    	        rpcPumpingInfoHandsontableHelper.balanceWeightList = rowdata[6];
	    	        	}
	    				break;
	    			}
	    		}
				if(result.totalRoot.length==0){
					rpcPumpingInfoHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{}]);
				}else{
					rpcPumpingInfoHandsontableHelper.hot.loadData(result.totalRoot);
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

var RPCPumpingInfoHandsontableHelper = {
	    createNew: function (divid) {
	        var rpcPumpingInfoHandsontableHelper = {};
	        rpcPumpingInfoHandsontableHelper.hot = '';
	        rpcPumpingInfoHandsontableHelper.divid = divid;
	        rpcPumpingInfoHandsontableHelper.colHeaders = [];
	        rpcPumpingInfoHandsontableHelper.columns = [];
	        rpcPumpingInfoHandsontableHelper.strokeList = [];
	        rpcPumpingInfoHandsontableHelper.balanceWeightList = [];
	        rpcPumpingInfoHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	        }
	        
	        rpcPumpingInfoHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        rpcPumpingInfoHandsontableHelper.processingStrokeData = function (instance, td, row, col, prop, value, cellProperties) {
	        	var showValue='';
	        	for(var i=0;i<rpcPumpingInfoHandsontableHelper.strokeList.length;i++){
	        		if(parseFloat(value)==rpcPumpingInfoHandsontableHelper.strokeList[i]){
	        			showValue=value;
	        			break;
	        		}
	        	}
	        	value=showValue;
	        }

	        rpcPumpingInfoHandsontableHelper.createTable = function (data) {
	            $('#' + rpcPumpingInfoHandsontableHelper.divid).empty();
	            var hotElement = document.querySelector('#' + rpcPumpingInfoHandsontableHelper.divid);
	            rpcPumpingInfoHandsontableHelper.hot = new Handsontable(hotElement, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: data,
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
//	                colWidths: [25,30,30,80],
	                columns: rpcPumpingInfoHandsontableHelper.columns,
	                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false, //显示行头
	                colHeaders: rpcPumpingInfoHandsontableHelper.colHeaders, //显示列头
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
							cellProperties.renderer = rpcPumpingInfoHandsontableHelper.addBoldBg;
		                }
	                    if (visualColIndex === 2 && visualRowIndex===0) {
	                    	this.type = 'dropdown';
	                    	this.source = rpcPumpingInfoHandsontableHelper.strokeList;
	                    	this.strict = true;
	                    	this.allowInvalid = false;
//	                    	cellProperties.renderer = rpcPumpingInfoHandsontableHelper.processingStrokeData;
	                    }
//	                    
	                    if (visualColIndex === 2 && visualRowIndex>=3) {
	                    	this.type = 'dropdown';
	                    	this.source = rpcPumpingInfoHandsontableHelper.balanceWeightList;
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
	        return rpcPumpingInfoHandsontableHelper;
	    }
	};

function CreateAndLoadRPCVideoInfoTable(deviceId,deviceName,isNew){
//	if(isNew&&rpcVideoInfoHandsontableHelper!=null){
//		if(rpcVideoInfoHandsontableHelper.hot!=undefined){
//			rpcVideoInfoHandsontableHelper.hot.destroy();
//		}
//		rpcVideoInfoHandsontableHelper=null;
//	}
	if(rpcVideoInfoHandsontableHelper!=null && rpcVideoInfoHandsontableHelper.hot!=undefined){
		rpcVideoInfoHandsontableHelper.hot.destroy();
		rpcVideoInfoHandsontableHelper=null;
	}
	rpcVideoInfoHandsontableHelper=null;
	Ext.getCmp("RPCVideoInfoPanel_Id").el.mask(cosog.string.loading).show();
	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/wellInformationManagerController/getDeviceVideoInfo',
		success:function(response) {
			Ext.getCmp("RPCVideoInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			var panelTitle='视频配置';
			if(isNotVal(deviceName)){
				panelTitle="抽油机井【<font color='red'>"+deviceName+"</font>】视频配置";
			}
			Ext.getCmp("RPCVideoInfoPanel_Id").setTitle(panelTitle);
			if(rpcVideoInfoHandsontableHelper==null || rpcVideoInfoHandsontableHelper.hot==undefined){
				rpcVideoInfoHandsontableHelper = RPCVideoInfoHandsontableHelper.createNew("RPCVideoInfoTableDiv_id");
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
				
				rpcVideoInfoHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				rpcVideoInfoHandsontableHelper.columns=Ext.JSON.decode(columns);
				rpcVideoInfoHandsontableHelper.colWidths=colWidths;
				if(result.totalRoot.length==0){
					rpcVideoInfoHandsontableHelper.createTable([{},{},{},{}]);
				}else{
					rpcVideoInfoHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					rpcVideoInfoHandsontableHelper.hot.loadData([{},{},{},{}]);
				}else{
					rpcVideoInfoHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("RPCVideoInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			deviceId:deviceId,
			deviceType:101,
			orgId: leftOrg_Id
        }
	});
};

var RPCVideoInfoHandsontableHelper = {
	    createNew: function (divid) {
	        var rpcVideoInfoHandsontableHelper = {};
	        rpcVideoInfoHandsontableHelper.hot = '';
	        rpcVideoInfoHandsontableHelper.divid = divid;
	        rpcVideoInfoHandsontableHelper.colHeaders = [];
	        rpcVideoInfoHandsontableHelper.columns = [];
	        rpcVideoInfoHandsontableHelper.colWidths=[];
	        rpcVideoInfoHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	        }
	        
	        rpcVideoInfoHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }

	        rpcVideoInfoHandsontableHelper.createTable = function (data) {
	            $('#' + rpcVideoInfoHandsontableHelper.divid).empty();
	            var hotElement = document.querySelector('#' + rpcVideoInfoHandsontableHelper.divid);
	            rpcVideoInfoHandsontableHelper.hot = new Handsontable(hotElement, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: data,
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: rpcVideoInfoHandsontableHelper.colWidths,
	                columns: rpcVideoInfoHandsontableHelper.columns,
	                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false, //显示行头
	                colHeaders: rpcVideoInfoHandsontableHelper.colHeaders, //显示列头
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
							cellProperties.renderer = rpcVideoInfoHandsontableHelper.addBoldBg;
		                }
	                    return cellProperties;
	                }
	            });
	        }
	        return rpcVideoInfoHandsontableHelper;
	    }
	};