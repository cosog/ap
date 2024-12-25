var deviceInfoHandsontableHelper = null;
var productionHandsontableHelper = null;
var pumpingInfoHandsontableHelper = null;
var videoInfoHandsontableHelper = null;

var deviceAuxiliaryDeviceInfoHandsontableHelper=null;
var deviceAdditionalInfoHandsontableHelper=null;
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
                    var deviceName='';
                    if(isNotVal(Ext.getCmp('deviceListComb_Id'))){
                    	deviceName = Ext.getCmp('deviceListComb_Id').getValue();
                    }
                    
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceType: getDeviceTypeFromTabId("DeviceManagerTabPanel"),
                        deviceName: deviceName
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });

        var deviceListDeviceCombo = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: deviceShowName,
                id: "deviceListComb_Id",
                labelWidth: 8*deviceShowNameLength,
                width: (8*deviceShowNameLength+110),
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
                emptyText: '--'+loginUserLanguageResource.all+'--',
                blankText: '--'+loginUserLanguageResource.all+'--',
                listeners: {
                    expand: function (sm, selections) {
                    	deviceListDeviceCombo.getStore().loadPage(1); // 加载井下拉框的store
                    },
                    select: function (combo, record, index) {
                        try {
                            CreateAndLoadDeviceInfoTable();
                        } catch (ex) {
                            Ext.Msg.alert(loginUserLanguageResource.tip, cosog.string.fail);
                        }
                    }
                }
            });
        
        Ext.apply(this, {
            tbar: [{
                xtype: 'button',
                text: loginUserLanguageResource.refresh,
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
                text: loginUserLanguageResource.search,
                iconCls: 'search',
                hidden: false,
                handler: function (v, o) {
                    CreateAndLoadDeviceInfoTable();
                }
            },'-',{
                xtype: 'button',
                text: loginUserLanguageResource.exportData,
                iconCls: 'export',
                hidden: false,
                handler: function (v, o) {
                	var deviceTypeName=getTabPanelActiveName("DeviceManagerTabPanel");
                    var fields = "";
                    var heads = "";
                    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var deviceType=getDeviceTypeFromTabId("DeviceManagerTabPanel");
                    var deviceName = Ext.getCmp('deviceListComb_Id').getValue();
                    
                    var url = context + '/wellInformationManagerController/exportWellInformationData';
                    for (var i = 0; i < deviceInfoHandsontableHelper.colHeaders.length; i++) {
                        fields += deviceInfoHandsontableHelper.columns[i].data + ",";
                        heads += deviceInfoHandsontableHelper.colHeaders[i] + ","
                    }
                    if (isNotVal(fields)) {
                        fields = fields.substring(0, fields.length - 1);
                        heads = heads.substring(0, heads.length - 1);
                    }
                    
                    var timestamp=new Date().getTime();
                	var key='exportWellInformationData'+deviceType+'_'+timestamp;
                	var maskPanelId='DeviceTablePanel_id';

                    var param = "&fields=" + fields 
                    + "&heads=" + URLencode(URLencode(heads)) 
                    + "&orgId=" + leftOrg_Id 
                    + "&deviceType="+deviceType
                    + "&deviceName=" + URLencode(URLencode(deviceName)) 
                    + "&recordCount=10000" 
                    + "&fileName=" + URLencode(URLencode(deviceTypeName+loginUserLanguageResource.deviceList)) 
                    + "&title=" + URLencode(URLencode(deviceTypeName))
                    + '&key='+key;
                    exportDataMask(key,maskPanelId,cosog.string.loading);
                    openExcelWindow(url + '?flag=true' + param);
                }
            },'-',{
                id: 'DeviceTotalCount_Id',
                xtype: 'component',
                hidden: false,
                tpl: loginUserLanguageResource.totalCount + ': {count}',
                style: 'margin-right:15px'
            },'->', {
    			xtype: 'button',
                text: loginUserLanguageResource.adddDevie,
                iconCls: 'add',
                disabled:loginUserDeviceManagerModuleRight.editFlag!=1,
                handler: function (v, o) {
                	var deviceTypes=getDeviceTypeFromTabId("DeviceManagerTabPanel");
                	var deviceTypeName=getTabPanelActiveName("DeviceManagerTabPanel");
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
                        title: loginUserLanguageResource.adddDevie
                    });
                    window.show();
                    Ext.getCmp("deviceWinOgLabel_Id").setHtml(loginUserLanguageResource.owningOrg+"【<font color=red>"+selectedOrgName+"</font>】,"+loginUserLanguageResource.pleaseConfirm+"<br/>&nbsp;");
                    
                    if(isNumber(deviceTypes)){
                    	Ext.getCmp("addDeviceType_Id").setValue(deviceTypes);
                    }
                    Ext.getCmp("addDeviceOrg_Id").setValue(selectedOrgId);
                    Ext.getCmp("addFormDevice_Id").show();
                    Ext.getCmp("updateFormDevice_Id").hide();
                    return false;
    			}
    		}, '-',{
    			xtype: 'button',
    			id: 'deleteDeviceNameBtn_Id',
    			text: loginUserLanguageResource.deleteDevice,
    			iconCls: 'delete',
    			disabled:loginUserDeviceManagerModuleRight.editFlag!=1,
    			handler: function (v, o) {
    				var startRow= Ext.getCmp("DeviceSelectRow_Id").getValue();
    				var endRow= Ext.getCmp("DeviceSelectEndRow_Id").getValue();
    				var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
    				if(startRow!='' && endRow!=''){
    					startRow=parseInt(startRow);
    					endRow=parseInt(endRow);
    					var deleteInfo=loginUserLanguageResource.confirmDelete;
    					if(startRow==endRow){
    						deleteInfo=loginUserLanguageResource.confirmDelete;
    					}
    					
    					Ext.Msg.confirm(loginUserLanguageResource.confirmDelete, deleteInfo, function (btn) {
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
    	    	                        	Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.deleteSuccessfully);
    	    	                            //保存以后重置全局容器
    	    	                            deviceInfoHandsontableHelper.clearContainer();
    	    	                            Ext.getCmp("DeviceSelectRow_Id").setValue(0);
    	    	                        	Ext.getCmp("DeviceSelectEndRow_Id").setValue(0);
    	    	                            CreateAndLoadDeviceInfoTable();
    	    	                        } else {
    	    	                            Ext.MessageBox.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
    	    	                        }
    	    	                    },
    	    	                    failure: function () {
    	    	                        Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.requestFailure);
    	    	                        deviceInfoHandsontableHelper.clearContainer();
    	    	                    },
    	    	                    params: {
    	    	                        data: JSON.stringify(saveData),
    	    	                        orgId: leftOrg_Id,
    	    	                        deviceType: getDeviceTypeFromTabId("DeviceManagerTabPanel")
    	    	                    }
    	    	                });
    			            }
    			        });
    				}else{
    					Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.checkOne);
    				}
    			}
    		},"-", {
                xtype: 'button',
                itemId: 'saveDeviceDataBtnId',
                id: 'saveDeviceDataBtn_Id',
                text: loginUserLanguageResource.save,
                iconCls: 'save',
                disabled:loginUserDeviceManagerModuleRight.editFlag!=1,
                handler: function (v, o) {
                    deviceInfoHandsontableHelper.saveData();
                }
            },"-",{
    			xtype: 'button',
                text: loginUserLanguageResource.batchAdd,
                iconCls: 'batchAdd',
                hidden: false,
                disabled:loginUserDeviceManagerModuleRight.editFlag!=1,
                handler: function (v, o) {
                	var deviceType=getDeviceTypeFromTabId("DeviceManagerTabPanel");
                	var deviceTypeName=getTabPanelActiveName("DeviceManagerTabPanel");
                	
                	
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
                        title: loginUserLanguageResource.batchAdd
                    });
                    Ext.getCmp("batchAddDeviceWinOrgLabel_Id").setHtml(loginUserLanguageResource.owningOrg+"【<font color=red>"+selectedOrgName+"</font>】,"+loginUserLanguageResource.pleaseConfirm);
                    Ext.getCmp("batchAddDeviceType_Id").setValue(deviceType);
                    Ext.getCmp("batchAddDeviceOrg_Id").setValue(selectedOrgId);
                    window.show();
                    return false;
    			}
    		},{
    			xtype: 'button',
    			text:'excel导入',
    			iconCls: 'upload',
    			hidden:true,
    			disabled:loginUserDeviceManagerModuleRight.editFlag!=1,
    			handler: function (v, o) {
    				var deviceType=getDeviceTypeFromTabId("DeviceManagerTabPanel");
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
    				Ext.getCmp("excelImportDeviceWinOgLabel_Id").setHtml(loginUserLanguageResource.owningOrg+"【<font color=red>"+selectedOrgName+"</font>】,"+loginUserLanguageResource.pleaseConfirm);
                    Ext.getCmp("excelImportDeviceType_Id").setValue(deviceType);
                    Ext.getCmp("excelImportDeviceOrg_Id").setValue(selectedOrgId);
                    window.show();
    			}
    		},'-', {
    			xtype: 'button',
    			text:loginUserLanguageResource.deviceOrgChange,
    			iconCls: 'move',
    			disabled:loginUserDeviceManagerModuleRight.editFlag!=1,
    			handler: function (v, o) {
    				var deviceType=getDeviceTypeFromTabId("DeviceManagerTabPanel");
    				var window = Ext.create("AP.view.well.DeviceOrgChangeWindow", {
                        title: loginUserLanguageResource.deviceOrgChange
                    });
                    window.show();
                    Ext.getCmp('DeviceOrgChangeWinDeviceType_Id').setValue(deviceType);
                    Ext.create("AP.store.well.DeviceOrgChangeDeviceListStore");
                    Ext.create("AP.store.well.DeviceOrgChangeOrgListStore");
                    Ext.create("AP.store.well.DeviceTypeChangeDeviceTypeListStore");
    			}
    		}],
            layout: 'border',
            items: [{
            	region: 'center',
        		title:loginUserLanguageResource.deviceList,
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
            	split: true,
            	collapsible: true,
            	header:false,
            	xtype: 'tabpanel',
            	id:'DeviceAdditionalInformationRabpanel_Id',
            	activeTab: 0,
            	tabBar:{
            		items: [{
                        xtype: 'tbfill'
                    },{
                    	xtype: 'label',
                    	id: 'DeviceAdditionalInformationLabel_Id',
                    	hidden:true,
                    	html: ''
                    },{
                    	xtype: 'label',
                    	hidden:false,
                    	html: '&nbsp;'
                    }]
            	},
            	items: [{
            		title:loginUserLanguageResource.additionalInformation,
            		id:'DeviceAdditionalInfoPanel_Id',
            		iconCls: 'check3',
            		html: '<div class="DeviceAdditionalInfoContainer" style="width:100%;height:100%;"><div class="con" id="DeviceAdditionalInfoTableDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	if (deviceAdditionalInfoHandsontableHelper != null && deviceAdditionalInfoHandsontableHelper.hot != null && deviceAdditionalInfoHandsontableHelper.hot != undefined) {
                        		var newWidth=width;
                        		var newHeight=height;
                        		var header=thisPanel.getHeader();
                        		if(header){
                        			newHeight=newHeight-header.lastBox.height-2;
                        		}
                        		deviceAdditionalInfoHandsontableHelper.hot.updateSettings({
                        			width:newWidth,
                        			height:newHeight
                        		});
                            }
                        }
                    }
            	},{
            		title:loginUserLanguageResource.auxiliaryDevice,
            		id:'DeviceAuxiliaryDevicePanel_Id',
            		html: '<div class="DeviceAuxiliaryDeviceContainer" style="width:100%;height:100%;"><div class="con" id="DeviceAuxiliaryDeviceTableDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	if (deviceAuxiliaryDeviceInfoHandsontableHelper != null && deviceAuxiliaryDeviceInfoHandsontableHelper.hot != null && deviceAuxiliaryDeviceInfoHandsontableHelper.hot != undefined) {
                        		var newWidth=width;
                        		var newHeight=height;
                        		var header=thisPanel.getHeader();
                        		if(header){
                        			newHeight=newHeight-header.lastBox.height-2;
                        		}
                        		deviceAuxiliaryDeviceInfoHandsontableHelper.hot.updateSettings({
                        			width:newWidth,
                        			height:newHeight
                        		});
                            }
                        }
                    }
            	},{
            		title:loginUserLanguageResource.videoConfig,
            		id:'DeviceVideoInfoPanel_Id',
                	hidden: !showVideoConfig,
                	tbar:['->',{
                        xtype: 'button',
                        text: loginUserLanguageResource.editVideoKey,
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
            	},{
            		title:loginUserLanguageResource.calculateDataConfig,
            		id:'DeviceCalculateDataInfoPanel_Id',
            		layout: 'border',
            		split: true,
            		collapsible: true,
            		header:false,
            		hidden:onlyMonitor,
            		tbar:[{
                        xtype: 'radiogroup',
                        fieldLabel: loginUserLanguageResource.calculateType,
                        labelWidth: 60,
                        id: 'DeviceCalculateDataType_Id',
                        cls: 'x-check-group-alt',
                        items: [
                            {boxLabel: loginUserLanguageResource.SRPCalculate,name: 'deviceCalculateDataType',width: getStringLength(loginUserLanguageResource.SRPCalculate)*10, inputValue: 1},
                            {boxLabel: loginUserLanguageResource.PCPCalculate,name: 'deviceCalculateDataType',width: getStringLength(loginUserLanguageResource.PCPCalculate)*10, inputValue: 2},
                            {boxLabel: loginUserLanguageResource.nothing,name: 'deviceCalculateDataType',width: getStringLength(loginUserLanguageResource.nothing)*10, inputValue: 0}
                        ],
                        listeners: {
                        	change: function (radiogroup, newValue, oldValue, eOpts) {
                				var deviceId=0;
                				var deviceName='';
                				var applicationScenarios=0;
                				var DeviceSelectRow= Ext.getCmp("DeviceSelectRow_Id").getValue();
                				if(isNotVal(DeviceSelectRow)){
                					var deviceInfoHandsontableData=deviceInfoHandsontableHelper.hot.getData();
                    	        	if(deviceInfoHandsontableData.length>0){
                    	        		deviceId=deviceInfoHandsontableHelper.hot.getDataAtRowProp(DeviceSelectRow,'id');
                    	        		deviceName=deviceInfoHandsontableHelper.hot.getDataAtRowProp(DeviceSelectRow,'deviceName');
                    	        		var applicationScenariosName=deviceInfoHandsontableHelper.hot.getDataAtRowProp(DeviceSelectRow,'applicationScenariosName');
                    	        		if(applicationScenariosName==loginUserLanguageResource.applicationScenarios1){
                	        				applicationScenarios=1;
                	        			}
                    	        	}
                				}
                				
                				
                				
                				CreateAndLoadProductionDataTable(deviceId,deviceName,applicationScenarios,true);
                				CreateAndLoadPumpingInfoTable(deviceId,deviceName,applicationScenarios,true);
                          	}
                        }
                    }],
            		items: [{
                      	region: 'center',
                		title:loginUserLanguageResource.productionData,
                    	id:'ProductionDataInfoPanel_Id',
                    	split: true,
                    	collapsible: false,
                    	html: '<div class="AdditionalInfoContainer" style="width:100%;height:100%;"><div class="con" id="AdditionalInfoTableDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if (productionHandsontableHelper != null && productionHandsontableHelper.hot != null && productionHandsontableHelper.hot != undefined) {
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
                        id:'PumpingModelConfigInfoPanel_Id',
                        layout: 'border',
                        split: true,
                        collapsible: true,
                        header:false,
                        items: [{
                        	region: 'center',
                        	split: true,
                            collapsible: true,
                        	title:loginUserLanguageResource.pumpingInfo,
                        	id:'PumpingInfoPanel_Id',
                            split: true,
                            collapsible: true,
                            html: '<div class="PumpingInfoContainer" style="width:100%;height:100%;"><div class="con" id="PumpingInfoTableDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if (pumpingInfoHandsontableHelper != null && pumpingInfoHandsontableHelper.hot != null && pumpingInfoHandsontableHelper.hot != undefined) {
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
            	}],
            	listeners: {
        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        				oldCard.setIconCls(null);
        				newCard.setIconCls('check3');
        			},
        			tabchange: function (tabPanel, newCard,oldCard, obj) {
        				var deviceId=0;
        				var deviceName='';
        				var applicationScenarios=0;
        				var DeviceSelectRow= Ext.getCmp("DeviceSelectRow_Id").getValue();
        				if(isNotVal(DeviceSelectRow)){
        					var deviceInfoHandsontableData=deviceInfoHandsontableHelper.hot.getData();
            	        	if(deviceInfoHandsontableData.length>0){
            	        		deviceId=deviceInfoHandsontableHelper.hot.getDataAtRowProp(DeviceSelectRow,'id');
            	        		deviceName=deviceInfoHandsontableHelper.hot.getDataAtRowProp(DeviceSelectRow,'deviceName');
            	        		var applicationScenariosName=deviceInfoHandsontableHelper.hot.getDataAtRowProp(DeviceSelectRow,'applicationScenariosName');
            	        		if(applicationScenariosName==loginUserLanguageResource.applicationScenarios1){
        	        				applicationScenarios=1;
        	        			}
            	        	}
        				}
        				CreateDeviceAdditionalInformationTable(deviceId,deviceName,applicationScenarios);
        			},
        			afterrender: function (panel, eOpts) {
        				
        			}
        		}
            }],
            listeners: {
                beforeclose: function (panel, eOpts) {
                	
                }
            }
        })
        this.callParent(arguments);
    }
});

function CreateDeviceAdditionalInformationTable(deviceId,deviceName,applicationScenarios,isNew){
	var showInfo='';
	
	var tabPanel = Ext.getCmp("DeviceAdditionalInformationRabpanel_Id");
	var activeId=tabPanel.getActiveTab().id;
	if(activeId=='DeviceAdditionalInfoPanel_Id'){
		CreateAndLoadDeviceAdditionalInfoTable(deviceId,deviceName,isNew);
		if(isNotVal(deviceName)){
			showInfo="【<font color=red>"+deviceName+"</font>】"+loginUserLanguageResource.additionalInformation+"&nbsp;"
		}
	}else if(activeId=='DeviceAuxiliaryDevicePanel_Id'){
		CreateAndLoadDeviceAuxiliaryDeviceInfoTable(deviceId,deviceName,isNew);
		if(isNotVal(deviceName)){
			showInfo="【<font color=red>"+deviceName+"</font>】"+loginUserLanguageResource.auxiliaryDevice+"&nbsp;"
		}
	}else if(activeId=='DeviceVideoInfoPanel_Id'){
		CreateAndLoadVideoInfoTable(deviceId,deviceName,isNew);
		if(isNotVal(deviceName)){
			showInfo="【<font color=red>"+deviceName+"</font>】"+loginUserLanguageResource.videoConfig+"&nbsp;"
		}
	}else if(activeId=='DeviceCalculateDataInfoPanel_Id'){
		var deviceCalculateDataType=Ext.getCmp("DeviceCalculateDataType_Id").getValue().deviceCalculateDataType;
		var calculateType=getDeviceCalculateType(deviceId);
		if(calculateType!=deviceCalculateDataType){
			Ext.getCmp('DeviceCalculateDataType_Id').setValue({deviceCalculateDataType:calculateType});
		}else{
			CreateAndLoadProductionDataTable(deviceId,deviceName,applicationScenarios,isNew);
			CreateAndLoadPumpingInfoTable(deviceId,deviceName,applicationScenarios,isNew);
		}
		if(isNotVal(deviceName)){
			showInfo="【<font color=red>"+deviceName+"</font>】"+loginUserLanguageResource.calculateDataConfig+"&nbsp;"
		}
	}
	Ext.getCmp("DeviceAdditionalInformationLabel_Id").setHtml(showInfo);
    Ext.getCmp("DeviceAdditionalInformationLabel_Id").show();
}

function getDeviceCalculateType(deviceId){
	var calculateType=0;
	Ext.Ajax.request({
		method:'POST',
		async :  false,
		url:context + '/wellInformationManagerController/getDeviceCalculateType',
		success:function(response) {
			calculateType = Ext.JSON.decode(response.responseText).calculateType;
		},
		failure:function(){
		},
		params: {
            deviceId:deviceId
        }
	});
	return calculateType;
}

function getApplicationScenariosType(deviceId){
	var applicationScenarios=0;
	Ext.Ajax.request({
		method:'POST',
		async :  false,
		url:context + '/wellInformationManagerController/getApplicationScenariosType',
		success:function(response) {
			applicationScenarios = Ext.JSON.decode(response.responseText).calculateType;
		},
		failure:function(){
		},
		params: {
            deviceId:deviceId
        }
	});
	return applicationScenarios;
}

function getDeviceAdditionalInformationType(){
	var type=-1;
	var tabPanel = Ext.getCmp("DeviceAdditionalInformationRabpanel_Id");
	var activeId=tabPanel.getActiveTab().id
	if(activeId=='DeviceAdditionalInfoPanel_Id'){
		type=0;
	}else if(activeId=='DeviceAuxiliaryDevicePanel_Id'){
		type=1;
	}else if(activeId=='DeviceVideoInfoPanel_Id'){
		type=2;
	}else if(activeId=='DeviceCalculateDataInfoPanel_Id'){
		type=3;
	}
	return type;
}

function CreateAndLoadDeviceInfoTable(isNew) {
	if(isNew&&deviceInfoHandsontableHelper!=null){
		if (deviceInfoHandsontableHelper.hot != undefined) {
			deviceInfoHandsontableHelper.hot.destroy();
		}
		deviceInfoHandsontableHelper = null;
	}
    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
    var deviceType=getDeviceTypeFromTabId("DeviceManagerTabPanel");
    var deviceName = Ext.getCmp('deviceListComb_Id').getValue();
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
                    } else if (result.columns[i].dataIndex.toUpperCase() === "deviceTypeName".toUpperCase()) {
                        var source = "[";
                        for (var j = 0; j < result.deviceTypeDropdownData.length; j++) {
                            source += "\'" + result.deviceTypeDropdownData[j] + "\'";
                            if (j < result.deviceTypeDropdownData.length - 1) {
                                source += ",";
                            }
                        }
                        source += "]";
                        columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
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
                    	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.enable+"', '"+loginUserLanguageResource.disable+"']}";
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
            	deviceInfoHandsontableHelper.hot.deselectCell();
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
            	deviceInfoHandsontableHelper.hot.selectCell(0,'deviceName');
            	CreateDeviceAdditionalInformationTable(0,'',0);
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
            	deviceInfoHandsontableHelper.hot.selectCell(selectRow,'deviceName');
        		
        		var recordId=deviceInfoHandsontableHelper.hot.getDataAtRowProp(selectRow,'id');
            	var deviceName=deviceInfoHandsontableHelper.hot.getDataAtRowProp(selectRow,'deviceName');
            	var applicationScenarios=0;
            	var applicationScenariosName= deviceInfoHandsontableHelper.hot.getDataAtRowProp(selectRow,'applicationScenariosName');
            	if(applicationScenariosName==loginUserLanguageResource.applicationScenarios1){
    				applicationScenarios=1;
    			}
            	
            	var combDeviceName=Ext.getCmp('deviceListComb_Id').getValue();
        		if(combDeviceName!=''){
            		Ext.getCmp("selectedDeviceId_global").setValue(recordId);
        		}
            	
        		CreateDeviceAdditionalInformationTable(recordId,deviceName,applicationScenarios);
            }
            Ext.getCmp("DeviceTotalCount_Id").update({
                count: result.totalCount
            });
        },
        failure: function () {
        	Ext.getCmp("DeviceTablePanel_id").getEl().unmask();
        	Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
        },
        params: {
        	deviceName: deviceName,
            deviceType: deviceType,
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
        
        deviceInfoHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.whiteSpace='nowrap'; //文本不换行
        	td.style.overflow='hidden';//超出部分隐藏
        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
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
                outsideClickDeselects:false,
                cells: function (row, col, prop) {
                    var cellProperties = {};
                    var visualRowIndex = this.instance.toVisualRow(row);
                    var visualColIndex = this.instance.toVisualColumn(col);
                    
                    var DeviceManagerModuleEditFlag=parseInt(Ext.getCmp("DeviceManagerModuleEditFlag").getValue());
                    if(DeviceManagerModuleEditFlag==1){
                    	if(deviceInfoHandsontableHelper.dataLength==0){
                        	cellProperties.readOnly = true;
                        }else{
                        	if(prop.toUpperCase() === "allPath".toUpperCase() || prop.toUpperCase() === "productionDataUpdateTime".toUpperCase()){
                        		cellProperties.readOnly = true;
                        	}else if(prop.toUpperCase() === "deviceTypeName".toUpperCase()){
                        		var deviceTypes=getDeviceTypeFromTabId("DeviceManagerTabPanel");
                        		if(isNumber(deviceTypes)){
                        			cellProperties.readOnly = true;
                        		}
                        	}else if(prop.toUpperCase() === "signInId".toUpperCase() || prop.toUpperCase() === "ipPort".toUpperCase()){
                        		if(deviceInfoHandsontableHelper.hot!=undefined && deviceInfoHandsontableHelper.hot.getDataAtCell!=undefined){
                        			var columns=deviceInfoHandsontableHelper.columns;

                            		var tcpTypeColIndex=-1;
                            		for(var i=0;i<columns.length;i++){
                            			if(columns[i].data.toUpperCase() === "tcpType".toUpperCase()){
                            				tcpTypeColIndex=i;
                            				break;
                                    	}
                            		}
                            		if(tcpTypeColIndex>=0){
                            			var tcpType=deviceInfoHandsontableHelper.hot.getDataAtCell(row,tcpTypeColIndex);
//                            			var cell = deviceInfoHandsontableHelper.hot.getCell(row, col);  
                            			if(tcpType=='' || tcpType==null){
                            				cellProperties.readOnly = false;
                            			}else{
                            				if(prop.toUpperCase() === "signInId".toUpperCase()){
                            					if(tcpType.toUpperCase() === "TCP Client".toUpperCase() || tcpType.toUpperCase() === "TCPClient".toUpperCase()){
                            						cellProperties.readOnly = false;
                            					}else{
                            						cellProperties.readOnly = true;
                            					}
                            				}else if(prop.toUpperCase() === "ipPort".toUpperCase()){
                            					if(tcpType.toUpperCase() === "TCP Server".toUpperCase() || tcpType.toUpperCase() === "TCPServer".toUpperCase()){
                            						cellProperties.readOnly = false;
                            					}else{
                            						cellProperties.readOnly = true;
                            					}
                            				}
                            			}
                            		}
                        		}
                        	}
                        	if(deviceInfoHandsontableHelper.columns[visualColIndex].type == undefined || deviceInfoHandsontableHelper.columns[visualColIndex].type!='dropdown'){
                        		cellProperties.renderer = deviceInfoHandsontableHelper.addCellStyle;
                        	}
                        }
                    }else{
                    	cellProperties.readOnly = true;
                    	cellProperties.renderer = deviceInfoHandsontableHelper.addCellStyle;
                    }
                    return cellProperties;
                },
                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
                	if(row<0 && row2<0){//只选中表头
                		Ext.getCmp("DeviceSelectRow_Id").setValue('');
                    	Ext.getCmp("DeviceSelectEndRow_Id").setValue('');
                    	CreateDeviceAdditionalInformationTable(0,'',0);
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
                    		Ext.getCmp("DeviceSelectRow_Id").setValue(startRow);
                        	Ext.getCmp("DeviceSelectEndRow_Id").setValue(endRow);
                        	var recordId=deviceInfoHandsontableHelper.hot.getDataAtRowProp(startRow,'id');
                        	var deviceName=deviceInfoHandsontableHelper.hot.getDataAtRowProp(startRow,'deviceName');
                        	var applicationScenarios=0;
                        	var applicationScenariosName= deviceInfoHandsontableHelper.hot.getDataAtRowProp(startRow,'applicationScenariosName');
                        	if(applicationScenariosName==loginUserLanguageResource.applicationScenarios1){
    	        				applicationScenarios=1;
    	        			}
                        	CreateDeviceAdditionalInformationTable(recordId,deviceName,applicationScenarios);
                        	Ext.getCmp("selectedDeviceId_global").setValue(recordId);
                    	}else{
                    		Ext.getCmp("DeviceSelectRow_Id").setValue(startRow);
                        	Ext.getCmp("DeviceSelectEndRow_Id").setValue(endRow);
                    	}
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
                                	if(productionHandsontableHelper != null && productionHandsontableHelper.hot != null && productionHandsontableHelper.hot != undefined){
                            			const plugin = productionHandsontableHelper.hot.getPlugin('hiddenRows');
                                    	var hiddenRows=[0,3,9,10];
                                    	if(params[3] == loginUserLanguageResource.applicationScenarios0){
                                    		plugin.hideRows(hiddenRows);
                                    		productionHandsontableHelper.hot.setDataAtCell(4,1,loginUserLanguageResource.reservoirDepth_cbm+'(m)');
                                    		productionHandsontableHelper.hot.setDataAtCell(5,1,loginUserLanguageResource.reservoirTemperature_cbm+'(℃)');
                                    		productionHandsontableHelper.hot.setDataAtCell(6,1,loginUserLanguageResource.tubingPressure_cbm+'(MPa)');
                                    	}else{
                                    		plugin.showRows(hiddenRows);
                                    		productionHandsontableHelper.hot.setDataAtCell(4,1,loginUserLanguageResource.reservoirDepth+'(m)');
                                    		productionHandsontableHelper.hot.setDataAtCell(5,1,loginUserLanguageResource.reservoirTemperature+'(℃)');
                                    		productionHandsontableHelper.hot.setDataAtCell(6,1,loginUserLanguageResource.tubingPressure+'(MPa)');
                                    	}
                                    	productionHandsontableHelper.hot.render();
                            		}
                                	
                                }
                            }
                        }
                    }
                },afterOnCellMouseOver: function(event, coords, TD){
                	if(deviceInfoHandsontableHelper!=null&&deviceInfoHandsontableHelper.hot!=''&&deviceInfoHandsontableHelper.hot!=undefined && deviceInfoHandsontableHelper.hot.getDataAtCell!=undefined){
                		var rawValue=deviceInfoHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
                		if(isNotVal(rawValue)){
            				var showValue=rawValue;
        					var rowChar=90;
        					var maxWidth=rowChar*10;
        					if(rawValue.length>rowChar){
        						showValue='';
        						let arr = [];
        						let index = 0;
        						while(index<rawValue.length){
        							arr.push(rawValue.slice(index,index +=rowChar));
        						}
        						for(var i=0;i<arr.length;i++){
        							showValue+=arr[i];
        							if(i<arr.length-1){
        								showValue+='<br>';
        							}
        						}
        					}
            				if(!isNotVal(TD.tip)){
            					var height=28;
            					TD.tip = Ext.create('Ext.tip.ToolTip', {
	                			    target: event.target,
	                			    maxWidth:maxWidth,
	                			    html: showValue,
	                			    listeners: {
	                			    	hide: function (thisTip, eOpts) {
	                                	},
	                                	close: function (thisTip, eOpts) {
	                                	}
	                                }
	                			});
            				}else{
            					TD.tip.setHtml(showValue);
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
            	var deviceType=getDeviceTypeFromTabId("DeviceManagerTabPanel");
            	var additionalInformationType=getDeviceAdditionalInformationType();
                //插入的数据的获取
                deviceInfoHandsontableHelper.insertExpressCount();
                //获取设备ID
                var DeviceSelectRow= Ext.getCmp("DeviceSelectRow_Id").getValue();
                
                var deviceId=deviceInfoHandsontableHelper.hot.getDataAtRowProp(DeviceSelectRow,'id');
        		var deviceName=deviceInfoHandsontableHelper.hot.getDataAtRowProp(DeviceSelectRow,'deviceName');
        		var applicationScenariosName=deviceInfoHandsontableHelper.hot.getDataAtRowProp(DeviceSelectRow,'applicationScenariosName');
        		var applicationScenarios=0;
        		if(applicationScenariosName==loginUserLanguageResource.applicationScenarios1){
    				applicationScenarios=1;
    			}
            	
            	
            	var deviceAdditionalInformationData={};
            	deviceAdditionalInformationData.deviceId=deviceId;
            	deviceAdditionalInformationData.type=additionalInformationType;
            	deviceAdditionalInformationData.data="";
            	if(additionalInformationType==0){
            		var additionalInfoList=[];
            		if(deviceAdditionalInfoHandsontableHelper!=null && deviceAdditionalInfoHandsontableHelper.hot!=undefined){
                		var additionalInfoData=deviceAdditionalInfoHandsontableHelper.hot.getData();
                    	Ext.Array.each(additionalInfoData, function (name, index, countriesItSelf) {
                            if (isNotVal(additionalInfoData[index][1])) {
                            	var additionalInfo={};
                            	additionalInfo.itemName=additionalInfoData[index][1];
                            	additionalInfo.itemValue=isNotVal(additionalInfoData[index][2])?additionalInfoData[index][2]:"";
                            	additionalInfo.itemUnit=isNotVal(additionalInfoData[index][3])?additionalInfoData[index][3]:"";
                            	additionalInfoList.push(additionalInfo);
                            }
                        });
                	}
            		if(additionalInfoList.length>0){
            			deviceAdditionalInformationData.data=JSON.stringify(additionalInfoList);
            		}
            	}else if(additionalInformationType==1){
            		auxiliaryDevice=[];
                	if(deviceAuxiliaryDeviceInfoHandsontableHelper!=null && deviceAuxiliaryDeviceInfoHandsontableHelper.hot!=undefined){
                		var auxiliaryDeviceData=deviceAuxiliaryDeviceInfoHandsontableHelper.hot.getData();
                    	Ext.Array.each(auxiliaryDeviceData, function (name, index, countriesItSelf) {
                            if (auxiliaryDeviceData[index][0]) {
                            	var auxiliaryDeviceId = auxiliaryDeviceData[index][7];
                            	auxiliaryDevice.push(auxiliaryDeviceId);
                            }
                        });
                	}
                	if(auxiliaryDevice.length>0){
            			deviceAdditionalInformationData.data=JSON.stringify(auxiliaryDevice);
            		}
            	}else if(additionalInformationType==2){
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
                    var videoInfoList=[];
                    videoInfoList.push(videoUrl1);
                    videoInfoList.push(videoUrl2);
                    videoInfoList.push(videoKeyName1);
                    videoInfoList.push(videoKeyName2);
                    deviceAdditionalInformationData.data=JSON.stringify(videoInfoList);
            	}else if(additionalInformationType==3){
            		var deviceCalculateDataType=Ext.getCmp("DeviceCalculateDataType_Id").getValue().deviceCalculateDataType;
            		if(deviceCalculateDataType==1){//指定为功图计算
            			//生产数据
                        var deviceProductionData={};
                        var manualInterventionResultName=loginUserLanguageResource.noIntervention;
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
                    		deviceProductionData.Pump.PumpType='T';
                    		
                    		var BarrelType=productionHandsontableData[13][2];
                    		if(productionHandsontableData[13][2]==loginUserLanguageResource.barrelType_L){
                    			BarrelType='L';
                    		}else if(productionHandsontableData[13][2]==loginUserLanguageResource.barrelType_H){
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
                    				&& isNotVal(productionHandsontableData[20][2]) 
                    				&& isNumber(parseInt(productionHandsontableData[21][2])) 
                    				&& (productionHandsontableData[22][2]=='' || isNumber(parseInt(productionHandsontableData[22][2])) )
                    				&& isNumber(parseInt(productionHandsontableData[23][2]))){
                    			var Rod1={};
                    			if(isNotVal(productionHandsontableData[19][2])){
                        			if(productionHandsontableData[19][2]==loginUserLanguageResource.rodStringTypeValue1){
                        				Rod1.Type=1;
                        			}else if(productionHandsontableData[19][2]==loginUserLanguageResource.rodStringTypeValue2){
                        				Rod1.Type=2;
                        			}else if(productionHandsontableData[19][2]==loginUserLanguageResource.rodStringTypeValue3){
                        				Rod1.Type=3;
                        			}
                        		}
                        		if(isNotVal(productionHandsontableData[20][2])){
                        			Rod1.Grade=productionHandsontableData[20][2];
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[21][2]))){
                        			Rod1.OutsideDiameter=parseInt(productionHandsontableData[21][2])*0.001;
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[22][2]))){
                        			Rod1.InsideDiameter=parseInt(productionHandsontableData[22][2])*0.001;
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[23][2]))){
                        			Rod1.Length=parseInt(productionHandsontableData[23][2]);
                        		}
                        		deviceProductionData.RodString.EveryRod.push(Rod1);
                    		}
                    		
                    		if(isNotVal(productionHandsontableData[24][2]) 
                    				&& isNotVal(productionHandsontableData[25][2]) 
                    				&& isNumber(parseInt(productionHandsontableData[26][2])) 
                    				&& (productionHandsontableData[27][2]=='' || isNumber(parseInt(productionHandsontableData[27][2])) )
                    				&& isNumber(parseInt(productionHandsontableData[28][2]))){
                    			var Rod2={};
                    			if(isNotVal(productionHandsontableData[24][2])){
                    				if(productionHandsontableData[24][2]==loginUserLanguageResource.rodStringTypeValue1){
                    					Rod2.Type=1;
                        			}else if(productionHandsontableData[24][2]==loginUserLanguageResource.rodStringTypeValue2){
                        				Rod2.Type=2;
                        			}else if(productionHandsontableData[24][2]==loginUserLanguageResource.rodStringTypeValue3){
                        				Rod2.Type=3;
                        			}
                        		}
                        		if(isNotVal(productionHandsontableData[25][2])){
                        			Rod2.Grade=productionHandsontableData[25][2];
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[26][2]))){
                        			Rod2.OutsideDiameter=parseInt(productionHandsontableData[26][2])*0.001;
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[27][2]))){
                        			Rod2.InsideDiameter=parseInt(productionHandsontableData[27][2])*0.001;
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[28][2]))){
                        			Rod2.Length=parseInt(productionHandsontableData[28][2]);
                        		}
                        		deviceProductionData.RodString.EveryRod.push(Rod2);
                    		}
                    		
                    		if(isNotVal(productionHandsontableData[29][2]) 
                    				&& isNotVal(productionHandsontableData[30][2]) 
                    				&& isNumber(parseInt(productionHandsontableData[31][2])) 
                    				&& (productionHandsontableData[32][2]=='' || isNumber(parseInt(productionHandsontableData[32][2])) )
                    				&& isNumber(parseInt(productionHandsontableData[33][2]))){
                    			var Rod3={};
                        		if(isNotVal(productionHandsontableData[29][2])){
                        			if(productionHandsontableData[29][2]==loginUserLanguageResource.rodStringTypeValue1){
                        				Rod3.Type=1;
                        			}else if(productionHandsontableData[29][2]==loginUserLanguageResource.rodStringTypeValue2){
                        				Rod3.Type=2;
                        			}else if(productionHandsontableData[29][2]==loginUserLanguageResource.rodStringTypeValue3){
                        				Rod3.Type=3;
                        			}
                        		}
                        		if(isNotVal(productionHandsontableData[30][2])){
                        			Rod3.Grade=productionHandsontableData[30][2];
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[31][2]))){
                        			Rod3.OutsideDiameter=parseInt(productionHandsontableData[31][2])*0.001;
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[32][2]))){
                        			Rod3.InsideDiameter=parseInt(productionHandsontableData[32][2])*0.001;
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[33][2]))){
                        			Rod3.Length=parseInt(productionHandsontableData[33][2]);
                        		}
                        		deviceProductionData.RodString.EveryRod.push(Rod3);
                    		}
                    		
                    		if(isNotVal(productionHandsontableData[34][2]) 
                    				&& isNotVal(productionHandsontableData[35][2]) 
                    				&& isNumber(parseInt(productionHandsontableData[36][2])) 
                    				&& (productionHandsontableData[37][2]=='' || isNumber(parseInt(productionHandsontableData[37][2])) )
                    				&& isNumber(parseInt(productionHandsontableData[38][2]))){
                    			var Rod4={};
                        		if(isNotVal(productionHandsontableData[34][2])){
                        			if(productionHandsontableData[34][2]==loginUserLanguageResource.rodStringTypeValue1){
                        				Rod4.Type=1;
                        			}else if(productionHandsontableData[34][2]==loginUserLanguageResource.rodStringTypeValue2){
                        				Rod4.Type=2;
                        			}else if(productionHandsontableData[34][2]==loginUserLanguageResource.rodStringTypeValue3){
                        				Rod4.Type=3;
                        			}
                        		}
                        		if(isNotVal(productionHandsontableData[35][2])){
                        			Rod4.Grade=productionHandsontableData[35][2];
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[36][2]))){
                        			Rod4.OutsideDiameter=parseInt(productionHandsontableData[36][2])*0.001;
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[37][2]))){
                        			Rod4.InsideDiameter=parseInt(productionHandsontableData[37][2])*0.001;
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[38][2]))){
                        			Rod4.Length=parseInt(productionHandsontableData[38][2]);
                        		}
                        		deviceProductionData.RodString.EveryRod.push(Rod4);
                    		}
                    		
                    		
                    		deviceProductionData.ManualIntervention={};
                    		manualInterventionResultName=productionHandsontableData[39][2];
                    		if(isNumber(parseFloat(productionHandsontableData[40][2]))){
                    			deviceProductionData.ManualIntervention.NetGrossRatio=parseFloat(productionHandsontableData[40][2]);
                    		}
                    		if(isNumber(parseFloat(productionHandsontableData[41][2]))){
                    			deviceProductionData.ManualIntervention.NetGrossValue=parseFloat(productionHandsontableData[41][2]);
                    		}
                    		if(isNumber(parseFloat(productionHandsontableData[42][2]))){
                    			deviceProductionData.ManualIntervention.LevelCorrectValue=parseFloat(productionHandsontableData[42][2]);
                    		}
                    	}
                        
                        //获取抽油机型号配置数据
                        var pumpingModelId=0;
                        
                        //抽油机详情
                        var balanceInfo={};
                        var stroke="";
                        if(pumpingInfoHandsontableHelper!=null && pumpingInfoHandsontableHelper.hot!=undefined){
                        	var pumpingInfoHandsontableData=pumpingInfoHandsontableHelper.hot.getData();
                        	stroke=pumpingInfoHandsontableData[3][2];
                        	balanceInfo.EveryBalance=[];
                        	for(var i=6;i<pumpingInfoHandsontableData.length;i++){
                        		if(isNotVal(pumpingInfoHandsontableData[i][1]) || isNotVal(pumpingInfoHandsontableData[i][2])){
                            		var EveryBalance={};
                            		EveryBalance.Position=pumpingInfoHandsontableData[i][1];
                            		EveryBalance.Weight=pumpingInfoHandsontableData[i][2];
                            		balanceInfo.EveryBalance.push(EveryBalance);
                            	}
                        	}
                        }
                        
                        var productionInfoList=[];
                        productionInfoList.push(deviceCalculateDataType);
                		productionInfoList.push(JSON.stringify(deviceProductionData));
                		productionInfoList.push(pumpingModelId);
                		productionInfoList.push(stroke);
                		productionInfoList.push(JSON.stringify(balanceInfo));
                		productionInfoList.push(manualInterventionResultName);
                		productionInfoList.push(applicationScenarios);
                        deviceAdditionalInformationData.data=JSON.stringify(productionInfoList);
            		}else if(deviceCalculateDataType==2){//指定为转速计产
            			var deviceProductionData={};
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
                    		if(applicationScenarios==1 && isNumber(parseFloat(productionHandsontableData[10][2]))){
                    			deviceProductionData.Production.ProductionGasOilRatio=parseFloat(productionHandsontableData[10][2]);
                    		}
                    		if(isNumber(parseFloat(productionHandsontableData[11][2]))){
                    			deviceProductionData.Production.ProducingfluidLevel=parseFloat(productionHandsontableData[11][2]);
                    		}
                    		if(isNumber(parseFloat(productionHandsontableData[12][2]))){
                    			deviceProductionData.Production.PumpSettingDepth=parseFloat(productionHandsontableData[12][2]);
                    		}
                    		
                    		deviceProductionData.Pump={};
                    		if(isNumber(parseFloat(productionHandsontableData[13][2]))){
                    			deviceProductionData.Pump.BarrelLength=parseFloat(productionHandsontableData[13][2]);
                    		}
                    		if(isNumber(parseInt(productionHandsontableData[14][2]))){
                    			deviceProductionData.Pump.BarrelSeries=parseInt(productionHandsontableData[14][2]);
                    		}
                    		if(isNumber(parseFloat(productionHandsontableData[15][2]))){
                    			deviceProductionData.Pump.RotorDiameter=parseFloat(productionHandsontableData[15][2])*0.001;
                    		}
                    		if(isNumber(parseFloat(productionHandsontableData[16][2]))){
                    			deviceProductionData.Pump.QPR=parseFloat(productionHandsontableData[16][2])*0.001*0.001;
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
                    				&& isNotVal(productionHandsontableData[20][2]) 
                    				&& isNumber(parseInt(productionHandsontableData[21][2])) 
                    				&& (productionHandsontableData[22][2]=='' || isNumber(parseInt(productionHandsontableData[22][2])) )
                    				&& isNumber(parseInt(productionHandsontableData[23][2]))){
                    			var Rod1={};
                    			if(isNotVal(productionHandsontableData[19][2])){
                        			if(productionHandsontableData[19][2]==loginUserLanguageResource.rodStringTypeValue1){
                        				Rod1.Type=1;
                        			}else if(productionHandsontableData[19][2]==loginUserLanguageResource.rodStringTypeValue2){
                        				Rod1.Type=2;
                        			}else if(productionHandsontableData[19][2]==loginUserLanguageResource.rodStringTypeValue3){
                        				Rod1.Type=3;
                        			}
                        		}
                        		if(isNotVal(productionHandsontableData[20][2])){
                        			Rod1.Grade=productionHandsontableData[20][2];
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[21][2]))){
                        			Rod1.OutsideDiameter=parseInt(productionHandsontableData[21][2])*0.001;
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[22][2]))){
                        			Rod1.InsideDiameter=parseInt(productionHandsontableData[22][2])*0.001;
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[23][2]))){
                        			Rod1.Length=parseInt(productionHandsontableData[23][2]);
                        		}
                        		deviceProductionData.RodString.EveryRod.push(Rod1);
                    		}
                    		
                    		if(isNotVal(productionHandsontableData[24][2]) 
                    				&& isNotVal(productionHandsontableData[25][2]) 
                    				&& isNumber(parseInt(productionHandsontableData[26][2])) 
                    				&& (productionHandsontableData[27][2]=='' || isNumber(parseInt(productionHandsontableData[27][2])) )
                    				&& isNumber(parseInt(productionHandsontableData[28][2]))){
                    			var Rod2={};
                    			if(isNotVal(productionHandsontableData[24][2])){
                    				if(productionHandsontableData[24][2]==loginUserLanguageResource.rodStringTypeValue1){
                    					Rod2.Type=1;
                        			}else if(productionHandsontableData[24][2]==loginUserLanguageResource.rodStringTypeValue2){
                        				Rod2.Type=2;
                        			}else if(productionHandsontableData[24][2]==loginUserLanguageResource.rodStringTypeValue3){
                        				Rod2.Type=3;
                        			}
                        		}
                        		if(isNotVal(productionHandsontableData[25][2])){
                        			Rod2.Grade=productionHandsontableData[25][2];
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[26][2]))){
                        			Rod2.OutsideDiameter=parseInt(productionHandsontableData[26][2])*0.001;
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[27][2]))){
                        			Rod2.InsideDiameter=parseInt(productionHandsontableData[27][2])*0.001;
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[28][2]))){
                        			Rod2.Length=parseInt(productionHandsontableData[28][2]);
                        		}
                        		deviceProductionData.RodString.EveryRod.push(Rod2);
                    		}
                    		
                    		if(isNotVal(productionHandsontableData[29][2]) 
                    				&& isNotVal(productionHandsontableData[30][2]) 
                    				&& isNumber(parseInt(productionHandsontableData[31][2])) 
                    				&& (productionHandsontableData[32][2]=='' || isNumber(parseInt(productionHandsontableData[32][2])) )
                    				&& isNumber(parseInt(productionHandsontableData[33][2]))){
                    			var Rod3={};
                        		if(isNotVal(productionHandsontableData[29][2])){
                        			if(productionHandsontableData[29][2]==loginUserLanguageResource.rodStringTypeValue1){
                        				Rod3.Type=1;
                        			}else if(productionHandsontableData[29][2]==loginUserLanguageResource.rodStringTypeValue2){
                        				Rod3.Type=2;
                        			}else if(productionHandsontableData[29][2]==loginUserLanguageResource.rodStringTypeValue3){
                        				Rod3.Type=3;
                        			}
                        		}
                        		if(isNotVal(productionHandsontableData[30][2])){
                        			Rod3.Grade=productionHandsontableData[30][2];
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[31][2]))){
                        			Rod3.OutsideDiameter=parseInt(productionHandsontableData[31][2])*0.001;
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[32][2]))){
                        			Rod3.InsideDiameter=parseInt(productionHandsontableData[32][2])*0.001;
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[33][2]))){
                        			Rod3.Length=parseInt(productionHandsontableData[33][2]);
                        		}
                        		deviceProductionData.RodString.EveryRod.push(Rod3);
                    		}
                    		
                    		if(isNotVal(productionHandsontableData[34][2]) 
                    				&& isNotVal(productionHandsontableData[35][2]) 
                    				&& isNumber(parseInt(productionHandsontableData[36][2])) 
                    				&& (productionHandsontableData[37][2]=='' || isNumber(parseInt(productionHandsontableData[37][2])) )
                    				&& isNumber(parseInt(productionHandsontableData[38][2]))){
                    			var Rod4={};
                        		if(isNotVal(productionHandsontableData[34][2])){
                        			if(productionHandsontableData[34][2]==loginUserLanguageResource.rodStringTypeValue1){
                        				Rod4.Type=1;
                        			}else if(productionHandsontableData[34][2]==loginUserLanguageResource.rodStringTypeValue2){
                        				Rod4.Type=2;
                        			}else if(productionHandsontableData[34][2]==loginUserLanguageResource.rodStringTypeValue3){
                        				Rod4.Type=3;
                        			}
                        		}
                        		if(isNotVal(productionHandsontableData[35][2])){
                        			Rod4.Grade=productionHandsontableData[35][2];
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[36][2]))){
                        			Rod4.OutsideDiameter=parseInt(productionHandsontableData[36][2])*0.001;
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[37][2]))){
                        			Rod4.InsideDiameter=parseInt(productionHandsontableData[37][2])*0.001;
                        		}
                        		if(isNumber(parseInt(productionHandsontableData[38][2]))){
                        			Rod4.Length=parseInt(productionHandsontableData[38][2]);
                        		}
                        		deviceProductionData.RodString.EveryRod.push(Rod4);
                    		}
                    		deviceProductionData.ManualIntervention={};
                    		if(isNumber(parseFloat(productionHandsontableData[39][2]))){
                    			deviceProductionData.ManualIntervention.NetGrossRatio=parseFloat(productionHandsontableData[39][2]);
                    		}
                    		if(isNumber(parseFloat(isNumber(parseFloat(productionHandsontableData[40][2]))))){
                    			deviceProductionData.ManualIntervention.NetGrossValue=parseFloat(productionHandsontableData[40][2]);
                    		}
                    	}
                        var productionInfoList=[];
                        productionInfoList.push(deviceCalculateDataType);
                		productionInfoList.push(JSON.stringify(deviceProductionData));
                		productionInfoList.push(applicationScenarios);
                        deviceAdditionalInformationData.data=JSON.stringify(productionInfoList);
            		}else{
            			var productionInfoList=[];
                        productionInfoList.push(deviceCalculateDataType);
                        productionInfoList.push(applicationScenarios);
                        deviceAdditionalInformationData.data=JSON.stringify(productionInfoList);
            		}
            	}
            	
            	Ext.Ajax.request({
                    method: 'POST',
                    url: context + '/wellInformationManagerController/saveWellHandsontableData',
                    success: function (response) {
                        rdata = Ext.JSON.decode(response.responseText);
                        if (rdata.success) {
                        	var saveInfo=loginUserLanguageResource.saveSuccessfully;
                        	if(rdata.collisionCount>0){//数据冲突
                        		saveInfo=loginUserLanguageResource.saveSuccessfully+":"+rdata.successCount+','+loginUserLanguageResource.saveFailure+':<font color="red">'+rdata.collisionCount+'</font>';
                        		for(var i=0;i<rdata.list.length;i++){
                        			saveInfo+='<br/><font color="red"> '+rdata.list[i]+'</font>';
                        		}
                        	}
                        	Ext.MessageBox.alert(loginUserLanguageResource.message, saveInfo);
                            if(rdata.successCount>0){
                            	deviceInfoHandsontableHelper.clearContainer();
                            	CreateAndLoadDeviceInfoTable();
                            }
                        } else {
                            Ext.MessageBox.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
                        }
                    },
                    failure: function () {
                        Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.requestFailure);
                        deviceInfoHandsontableHelper.clearContainer();
                    },
                    params: {
                    	deviceId: deviceId,
                    	data: JSON.stringify(deviceInfoHandsontableHelper.AllData),
                    	deviceAdditionalInformationData: JSON.stringify(deviceAdditionalInformationData),
                        orgId: leftOrg_Id,
                        deviceType: deviceType
                    }
                });
        	}else{
        		Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.noDataChange);
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
                            Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.saveSuccessfully);
                            deviceInfoHandsontableHelper.clearContainer();
                            CreateAndLoadDeviceInfoTable();
                        } else {
                            Ext.MessageBox.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
                        }
                    },
                    failure: function () {
                        Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.requestFailure);
                        deviceInfoHandsontableHelper.clearContainer();
                    },
                    params: {
                        data: JSON.stringify(deviceInfoHandsontableHelper.editWellNameList),
                        deviceType:getDeviceTypeFromTabId("DeviceManagerTabPanel")
                    }
                });
            } else {
                if (!deviceInfoHandsontableHelper.validresult) {
                    Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.dataTypeError);
                } else {
                    Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.noDataChange);
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

function CreateAndLoadProductionDataTable(deviceId,deviceName,applicationScenarios,isNew){
	var deviceCalculateDataType=Ext.getCmp("DeviceCalculateDataType_Id").getValue().deviceCalculateDataType;
	
	if(productionHandsontableHelper!=null){
		if(productionHandsontableHelper.hot!=undefined){
			productionHandsontableHelper.hot.destroy();
		}
		productionHandsontableHelper=null;
	}
	if(deviceCalculateDataType!=0){
		Ext.getCmp("ProductionDataInfoPanel_Id").el.mask(cosog.string.loading).show();
		
		Ext.Ajax.request({
			method:'POST',
			url:context + '/wellInformationManagerController/getDeviceProductionDataInfo',
			success:function(response) {
				Ext.getCmp("ProductionDataInfoPanel_Id").getEl().unmask();
				var result =  Ext.JSON.decode(response.responseText);
				
				var panelTitle=loginUserLanguageResource.productionData;
				if(isNotVal(deviceName)){
					panelTitle="【<font color='red'>"+deviceName+"</font>】"+loginUserLanguageResource.productionData;
				}
				Ext.getCmp("ProductionDataInfoPanel_Id").setTitle(panelTitle);
				if(productionHandsontableHelper==null || productionHandsontableHelper.hot==undefined){
					productionHandsontableHelper = ProductionHandsontableHelper.createNew("AdditionalInfoTableDiv_id");
					productionHandsontableHelper.resultList = result.resultNameList;
					var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.variable+"']";
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
				
				var hiddenRows=[0,3,9,10];
				const plugin = productionHandsontableHelper.hot.getPlugin('hiddenRows');
				if(applicationScenarios==0){
					
				}
				if(applicationScenarios==0){
            		plugin.hideRows(hiddenRows);
            		productionHandsontableHelper.hot.setDataAtCell(4,1,loginUserLanguageResource.reservoirDepth_cbm+'(m)');
            		productionHandsontableHelper.hot.setDataAtCell(5,1,loginUserLanguageResource.reservoirTemperature_cbm+'(℃)');
            		productionHandsontableHelper.hot.setDataAtCell(6,1,loginUserLanguageResource.tubingPressure_cbm+'(MPa)');
            	}else if(applicationScenarios==1){
            		plugin.showRows(hiddenRows);
            		productionHandsontableHelper.hot.setDataAtCell(4,1,loginUserLanguageResource.reservoirDepth+'(m)');
            		productionHandsontableHelper.hot.setDataAtCell(5,1,loginUserLanguageResource.reservoirTemperature+'(℃)');
            		productionHandsontableHelper.hot.setDataAtCell(6,1,loginUserLanguageResource.tubingPressure+'(MPa)');
            	}
	        	productionHandsontableHelper.hot.render();
			},
			failure:function(){
				Ext.getCmp("ProductionDataInfoPanel_Id").getEl().unmask();
				Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
			},
			params: {
				deviceId:deviceId,
				deviceCalculateDataType:deviceCalculateDataType,
				deviceType:getDeviceTypeFromTabId("DeviceManagerTabPanel")
	        }
		});
	}else{
		var panelTitle=loginUserLanguageResource.productionData;
		if(isNotVal(deviceName)){
			panelTitle="【<font color='red'>"+deviceName+"</font>】"+loginUserLanguageResource.productionData;
		}
		Ext.getCmp("ProductionDataInfoPanel_Id").setTitle(panelTitle);
	}
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
	        
	        productionHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        productionHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
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
	                    var DeviceManagerModuleEditFlag=parseInt(Ext.getCmp("DeviceManagerModuleEditFlag").getValue());
	                    var deviceCalculateDataType=Ext.getCmp("DeviceCalculateDataType_Id").getValue().deviceCalculateDataType;
	                    
	                    if(DeviceManagerModuleEditFlag==1){
	                    	if (visualColIndex !=2) {
								cellProperties.readOnly = true;
								cellProperties.renderer = productionHandsontableHelper.addCellStyle;
			                }else if(visualRowIndex==39 && visualColIndex==2){
//		                    	cellProperties.readOnly = true;
		                    	cellProperties.renderer = productionHandsontableHelper.addCellStyle;
		                    }
		                    
		                    if (visualColIndex === 2 && visualRowIndex===13 && deviceCalculateDataType==1) {
		                    	this.type = 'dropdown';
		                    	this.source = [loginUserLanguageResource.barrelType_L, loginUserLanguageResource.barrelType_H];
		                    	this.strict = true;
		                    	this.allowInvalid = false;
		                    }
		                    
		                    if (visualColIndex === 2 && visualRowIndex===14 && deviceCalculateDataType==1) {
		                    	var barrelType='';
		                    	if(isNotVal(productionHandsontableHelper.hot)){
		                    		barrelType=productionHandsontableHelper.hot.getDataAtCell(13,2);
		                    	}else{
		                    		barrelType=productionHandsontableHelper.pumpGrade;
		                    	}
		                    	var pumpGradeList=['1','2','3','4','5'];
		                    	if(barrelType===loginUserLanguageResource.barrelType_L){
		                    		pumpGradeList=['1','2','3'];
		                    	}
		                    	this.type = 'dropdown';
		                    	this.source = pumpGradeList;
		                    	this.strict = true;
		                    	this.allowInvalid = false;
		                    }
		                    
		                    if (visualColIndex === 2 && (visualRowIndex===19 || visualRowIndex===24||visualRowIndex===29||visualRowIndex===32)) {
		                    	this.type = 'dropdown';
		                    	this.source = [loginUserLanguageResource.rodStringTypeValue1,loginUserLanguageResource.rodStringTypeValue2,loginUserLanguageResource.rodStringTypeValue3];
		                    	this.strict = true;
		                    	this.allowInvalid = false;
		                    }
		                    
		                    if (visualColIndex === 2 && (visualRowIndex===20 || visualRowIndex===25||visualRowIndex===30||visualRowIndex===33)) {
		                    	this.type = 'dropdown';
		                    	this.source = ['A','B','C','K','D','KD','HL','HY'];
		                    	this.strict = true;
		                    	this.allowInvalid = false;
		                    }
		                    
		                    if (visualColIndex === 2 && visualRowIndex===39 && deviceCalculateDataType==1) {
		                    	this.type = 'dropdown';
		                    	this.source = productionHandsontableHelper.resultList;
		                    	this.strict = true;
		                    	this.allowInvalid = false;
		                    }
	                    }else{
	                    	cellProperties.readOnly = true;
	                    	if (visualColIndex !=2) {
	                    		cellProperties.renderer = productionHandsontableHelper.addCellStyle;
	                    	}
	                    }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(productionHandsontableHelper!=null&&productionHandsontableHelper.hot!=''&&productionHandsontableHelper.hot!=undefined && productionHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=productionHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	            });
	        }
	        return productionHandsontableHelper;
	    }
	};

function CreateAndLoadPumpingInfoTable(deviceId,deviceName,applicationScenarios,isNew){
	var deviceCalculateDataType=Ext.getCmp("DeviceCalculateDataType_Id").getValue().deviceCalculateDataType;
	if(pumpingInfoHandsontableHelper!=null){
		if(pumpingInfoHandsontableHelper.hot!=undefined){
			pumpingInfoHandsontableHelper.hot.destroy();
		}
		pumpingInfoHandsontableHelper=null;
	}
	
	if(deviceCalculateDataType==2){
		Ext.getCmp("PumpingModelConfigInfoPanel_Id").hide();;
	}else{
		Ext.getCmp("PumpingModelConfigInfoPanel_Id").show();;
	}
	
	if(deviceCalculateDataType==1){
		Ext.getCmp("PumpingInfoPanel_Id").el.mask(cosog.string.loading).show();
		Ext.Ajax.request({
			method:'POST',
			url:context + '/wellInformationManagerController/getDevicePumpingInfo',
			success:function(response) {
				Ext.getCmp("PumpingInfoPanel_Id").getEl().unmask();
				var result =  Ext.JSON.decode(response.responseText);
				var panelTitle=loginUserLanguageResource.pumpingInfo;
				if(isNotVal(deviceName)){
					panelTitle="【<font color='red'>"+deviceName+"</font>】"+loginUserLanguageResource.pumpingInfo;
				}
				Ext.getCmp("PumpingInfoPanel_Id").setTitle(panelTitle);
				if(pumpingInfoHandsontableHelper==null || pumpingInfoHandsontableHelper.hot==undefined){
					pumpingInfoHandsontableHelper = PumpingInfoHandsontableHelper.createNew("PumpingInfoTableDiv_id");
					
					pumpingInfoHandsontableHelper.strokeList = result.strokeArrStr;
	    	        pumpingInfoHandsontableHelper.balanceWeightList = result.balanceInfoArrStr;
					
					var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.variable+"','']";
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
					pumpingInfoHandsontableHelper.strokeList = result.strokeArrStr;
	    	        pumpingInfoHandsontableHelper.balanceWeightList = result.balanceInfoArrStr;
					if(result.totalRoot.length==0){
						pumpingInfoHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{}]);
					}else{
						pumpingInfoHandsontableHelper.hot.loadData(result.totalRoot);
					}
				}
			},
			failure:function(){
				Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
			},
			params: {
				deviceId:deviceId,
				deviceType: getDeviceTypeFromTabId("DeviceManagerTabPanel")
	        }
		});
	}
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
	        
	        pumpingInfoHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
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
	                	label:loginUserLanguageResource.idx
	                },{
	                	label:loginUserLanguageResource.name
	                },{
	                	label:loginUserLanguageResource.variable,
	                	colspan:2
	                }]],
	                mergeCells: [{
	                	"row": 4,
                        "col": 1,
                        "rowspan": 1,
                        "colspan": 2
	                }],
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    var DeviceManagerModuleEditFlag=parseInt(Ext.getCmp("DeviceManagerModuleEditFlag").getValue());
	                    if(DeviceManagerModuleEditFlag==1){
	                    	if (visualRowIndex<=2 || (visualRowIndex>=4&&visualRowIndex<=5) ||(visualRowIndex==3&&visualColIndex==1) ) {
								cellProperties.readOnly = true;
								cellProperties.renderer = pumpingInfoHandsontableHelper.addCellStyle;
			                }
		                    if (visualColIndex === 2 && visualRowIndex===3) {
		                    	this.type = 'dropdown';
		                    	this.source = pumpingInfoHandsontableHelper.strokeList;
		                    	this.strict = true;
		                    	this.allowInvalid = false;
		                    }
		                    if (visualColIndex === 2 && visualRowIndex>=6) {
		                    	this.type = 'dropdown';
		                    	this.source = pumpingInfoHandsontableHelper.balanceWeightList;
		                    	this.strict = true;
		                    	this.allowInvalid = true;
		                    }
	                    }else{
	                    	cellProperties.readOnly = true;
	                    	if (visualRowIndex<=2 || (visualRowIndex>=4&&visualRowIndex<=5) ||(visualRowIndex==3&&visualColIndex==1) ) {
								cellProperties.renderer = pumpingInfoHandsontableHelper.addCellStyle;
			                }
	                    }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(pumpingInfoHandsontableHelper!=null&&pumpingInfoHandsontableHelper.hot!=''&&pumpingInfoHandsontableHelper.hot!=undefined && pumpingInfoHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=pumpingInfoHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	            });
	        }
	        return pumpingInfoHandsontableHelper;
	    }
	};

function CreateAndLoadVideoInfoTable(deviceId,deviceName,isNew){
	if(videoInfoHandsontableHelper!=null && videoInfoHandsontableHelper.hot!=undefined){
		videoInfoHandsontableHelper.hot.destroy();
		videoInfoHandsontableHelper=null;
	}
	videoInfoHandsontableHelper=null;
	Ext.getCmp("DeviceVideoInfoPanel_Id").el.mask(cosog.string.loading).show();
	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/wellInformationManagerController/getDeviceVideoInfo',
		success:function(response) {
			Ext.getCmp("DeviceVideoInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			var panelTitle=loginUserLanguageResource.videoConfig;
			if(isNotVal(deviceName)){
				panelTitle="【<font color='red'>"+deviceName+"</font>】"+loginUserLanguageResource.videoConfig;
			}
//			Ext.getCmp("DeviceVideoInfoPanel_Id").setTitle(panelTitle);
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
			Ext.getCmp("DeviceVideoInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			deviceId:deviceId,
			deviceType: getDeviceTypeFromTabId("DeviceManagerTabPanel"),
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
	        
	        videoInfoHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(videoInfoHandsontableHelper.columns[col].type=='checkbox'){
//	        		Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
//		            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        	}else if(videoInfoHandsontableHelper.columns[col].type=='dropdown'){
		            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
		            td.style.whiteSpace='nowrap'; //文本不换行
		        	td.style.overflow='hidden';//超出部分隐藏
		        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
		        }else{
		            Handsontable.renderers.TextRenderer.apply(this, arguments);
		            td.style.whiteSpace='nowrap'; //文本不换行
		        	td.style.overflow='hidden';//超出部分隐藏
		        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
		        }
	        }
	        
	        videoInfoHandsontableHelper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(videoInfoHandsontableHelper.columns[col].type=='checkbox'){
	        		Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
		            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        	}else if(videoInfoHandsontableHelper.columns[col].type=='dropdown'){
		            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
		            td.style.backgroundColor = 'rgb(245, 245, 245)';
		            td.style.whiteSpace='nowrap'; //文本不换行
		        	td.style.overflow='hidden';//超出部分隐藏
		        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
		        }else{
		            Handsontable.renderers.TextRenderer.apply(this, arguments);
		            td.style.backgroundColor = 'rgb(245, 245, 245)';
		            td.style.whiteSpace='nowrap'; //文本不换行
		        	td.style.overflow='hidden';//超出部分隐藏
		        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
		        }
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
	                    var DeviceManagerModuleEditFlag=parseInt(Ext.getCmp("DeviceManagerModuleEditFlag").getValue());
	                    if(DeviceManagerModuleEditFlag==1){
	                    	if (visualColIndex < 2) {
								cellProperties.readOnly = true;
								cellProperties.renderer = videoInfoHandsontableHelper.addReadOnlyBg;
			                }else{
			                	cellProperties.renderer=videoInfoHandsontableHelper.addCellStyle;
			                }
	                    }else{
							cellProperties.readOnly = true;
							cellProperties.renderer = videoInfoHandsontableHelper.addReadOnlyBg;
		                }
	                    
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(videoInfoHandsontableHelper!=null&&videoInfoHandsontableHelper.hot!=''&&videoInfoHandsontableHelper.hot!=undefined && videoInfoHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=videoInfoHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	            });
	        }
	        return videoInfoHandsontableHelper;
	    }
	};


function CreateAndLoadDeviceAdditionalInfoTable(deviceId,deviceName,isNew){
	if(deviceAdditionalInfoHandsontableHelper!=null){
		if(deviceAdditionalInfoHandsontableHelper.hot!=undefined){
			deviceAdditionalInfoHandsontableHelper.hot.destroy();
		}
		deviceAdditionalInfoHandsontableHelper=null;
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/wellInformationManagerController/getDeviceAdditionalInfo',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(!isNotVal(deviceName)){
				deviceName='';
			}
			
			if(deviceAdditionalInfoHandsontableHelper==null || deviceAdditionalInfoHandsontableHelper.hot==undefined){
				deviceAdditionalInfoHandsontableHelper = DeviceAdditionalInfoHandsontableHelper.createNew("DeviceAdditionalInfoTableDiv_id");
				var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.variable+"','"+loginUserLanguageResource.unit+"']";
				var columns="[{data:'id'},{data:'itemName'},{data:'itemValue'},{data:'itemUnit'}]";
				
				deviceAdditionalInfoHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				deviceAdditionalInfoHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					deviceAdditionalInfoHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					deviceAdditionalInfoHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					deviceAdditionalInfoHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					deviceAdditionalInfoHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			deviceId:deviceId,
			deviceType: getDeviceTypeFromTabId("DeviceManagerTabPanel")
        }
	});
};

var DeviceAdditionalInfoHandsontableHelper = {
	    createNew: function (divid) {
	        var deviceAdditionalInfoHandsontableHelper = {};
	        deviceAdditionalInfoHandsontableHelper.hot = '';
	        deviceAdditionalInfoHandsontableHelper.divid = divid;
	        deviceAdditionalInfoHandsontableHelper.colHeaders = [];
	        deviceAdditionalInfoHandsontableHelper.columns = [];
	        deviceAdditionalInfoHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	        }
	        
	        deviceAdditionalInfoHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
            	td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }

	        deviceAdditionalInfoHandsontableHelper.createTable = function (data) {
	            $('#' + deviceAdditionalInfoHandsontableHelper.divid).empty();
	            var hotElement = document.querySelector('#' + deviceAdditionalInfoHandsontableHelper.divid);
	            deviceAdditionalInfoHandsontableHelper.hot = new Handsontable(hotElement, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: data,
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: false
	                },
	                columns: deviceAdditionalInfoHandsontableHelper.columns,
	                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true, //显示行头
	                colHeaders: deviceAdditionalInfoHandsontableHelper.colHeaders, //显示列头
	                columnSorting: true, //允许排序
	                contextMenu: {
	                    items: {
	                        "row_above": {
	                            name: loginUserLanguageResource.contextMenu_insertRowAbove,
	                        },
	                        "row_below": {
	                            name: loginUserLanguageResource.contextMenu_insertRowBelow,
	                        },
	                        "col_left": {
	                            name: loginUserLanguageResource.contextMenu_insertColumnLeft,
	                        },
	                        "col_right": {
	                            name: loginUserLanguageResource.contextMenu_insertColumnRight,
	                        },
	                        "remove_row": {
	                            name: loginUserLanguageResource.contextMenu_removeRow,
	                        },
	                        "remove_col": {
	                            name: loginUserLanguageResource.contextMenu_removeColumn,
	                        },
	                        "merge_cell": {
	                            name: loginUserLanguageResource.contextMenu_mergeCell,
	                        },
	                        "copy": {
	                            name: loginUserLanguageResource.contextMenu_copy,
	                        },
	                        "cut": {
	                            name: loginUserLanguageResource.contextMenu_cut,
	                        },
	                        "paste": {
	                            name: loginUserLanguageResource.contextMenu_paste,
	                            disabled: function () {
	                            },
	                            callback: function () {
	                            }
	                        }
	                    }
	                }, 
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
	                    var DeviceManagerModuleEditFlag=parseInt(Ext.getCmp("DeviceManagerModuleEditFlag").getValue());
	                    if(DeviceManagerModuleEditFlag!=1){
	                    	cellProperties.readOnly = true;
//							cellProperties.renderer = deviceAdditionalInfoHandsontableHelper.addBoldBg;
	                    }
	                    cellProperties.renderer = deviceAdditionalInfoHandsontableHelper.addCellStyle;
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(deviceAdditionalInfoHandsontableHelper!=null&&deviceAdditionalInfoHandsontableHelper.hot!=''&&deviceAdditionalInfoHandsontableHelper.hot!=undefined && deviceAdditionalInfoHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=deviceAdditionalInfoHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	            });
	        }
	        return deviceAdditionalInfoHandsontableHelper;
	    }
	};

function CreateAndLoadDeviceAuxiliaryDeviceInfoTable(deviceId,deviceName,isNew){
	if(deviceAuxiliaryDeviceInfoHandsontableHelper!=null){
		if(deviceAuxiliaryDeviceInfoHandsontableHelper.hot!=undefined){
			deviceAuxiliaryDeviceInfoHandsontableHelper.hot.destroy();
		}
		deviceAuxiliaryDeviceInfoHandsontableHelper=null;
	}
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/wellInformationManagerController/getAuxiliaryDevice',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(!isNotVal(deviceName)){
				deviceName='';
			}
			if(deviceAuxiliaryDeviceInfoHandsontableHelper==null || deviceAuxiliaryDeviceInfoHandsontableHelper.hot==undefined){
				deviceAuxiliaryDeviceInfoHandsontableHelper = DeviceAuxiliaryDeviceInfoHandsontableHelper.createNew("DeviceAuxiliaryDeviceTableDiv_id");
				var colHeaders="['','"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.deviceName+"','"+loginUserLanguageResource.manufacturer+"','"+loginUserLanguageResource.model+"','"+loginUserLanguageResource.specificType+"','"+loginUserLanguageResource.specificType+"','ID']";
				var columns="[{data:'checked',type:'checkbox'}," 
						+"{data:'id'}," 
						+"{data:'name'}," 
						+"{data:'manufacturer'}," 
						+"{data:'model'}," 
						+"{data:'specificTypeName'}," 
						+"{data:'specificType'}," 
						+"{data:'realId'}]";
				
				deviceAuxiliaryDeviceInfoHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				deviceAuxiliaryDeviceInfoHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					deviceAuxiliaryDeviceInfoHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					deviceAuxiliaryDeviceInfoHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				deviceAuxiliaryDeviceInfoHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			deviceId:deviceId,
			deviceType: getDeviceTypeFromTabId_first("DeviceManagerTabPanel")
        }
	});
};

var DeviceAuxiliaryDeviceInfoHandsontableHelper = {
		createNew: function (divid) {
	        var deviceAuxiliaryDeviceInfoHandsontableHelper = {};
	        deviceAuxiliaryDeviceInfoHandsontableHelper.hot1 = '';
	        deviceAuxiliaryDeviceInfoHandsontableHelper.divid = divid;
	        deviceAuxiliaryDeviceInfoHandsontableHelper.validresult=true;//数据校验
	        deviceAuxiliaryDeviceInfoHandsontableHelper.colHeaders=[];
	        deviceAuxiliaryDeviceInfoHandsontableHelper.columns=[];
	        deviceAuxiliaryDeviceInfoHandsontableHelper.AllData=[];
	        
	        deviceAuxiliaryDeviceInfoHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        deviceAuxiliaryDeviceInfoHandsontableHelper.createTable = function (data) {
	        	$('#'+deviceAuxiliaryDeviceInfoHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+deviceAuxiliaryDeviceInfoHandsontableHelper.divid);
	        	deviceAuxiliaryDeviceInfoHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [6,7],
	                    indicators: false
	                },
	        		colWidths: [20,20,80,80,80,80],
	                columns:deviceAuxiliaryDeviceInfoHandsontableHelper.columns,
	                columns:deviceAuxiliaryDeviceInfoHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:deviceAuxiliaryDeviceInfoHandsontableHelper.colHeaders,//显示列头
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
	                    var DeviceManagerModuleEditFlag=parseInt(Ext.getCmp("DeviceManagerModuleEditFlag").getValue());
	                    if(DeviceManagerModuleEditFlag==1){
	                    	if (visualColIndex >0) {
								cellProperties.readOnly = true;
			                }
	                    }else{
	                    	cellProperties.readOnly = true;
	                    }
	                    if (visualColIndex >0) {
	                    	cellProperties.renderer = deviceAuxiliaryDeviceInfoHandsontableHelper.addCellStyle;
	                    }
	                    
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	var DeviceManagerModuleEditFlag=parseInt(Ext.getCmp("DeviceManagerModuleEditFlag").getValue());
	                    if(DeviceManagerModuleEditFlag==1){
	                    	if(row==row2 && column==column2 && column==0){
	                    		var selectedRow=row;
			                	var selectedCol=column;
			                	
			                	var checkboxColData=deviceAuxiliaryDeviceInfoHandsontableHelper.hot.getDataAtCol(0);
			                	var specificTypeData=deviceAuxiliaryDeviceInfoHandsontableHelper.hot.getDataAtCol(6);
			                	
			                	var rowdata = deviceAuxiliaryDeviceInfoHandsontableHelper.hot.getDataAtRow(selectedRow);
			                	
			                	if(rowdata[6]==1){
			                		for(var i=0;i<checkboxColData.length;i++){
			                			if(i!=selectedRow&&checkboxColData[i]&&specificTypeData[i]==1){
			                				deviceAuxiliaryDeviceInfoHandsontableHelper.hot.setDataAtCell(i,0,false);
			                			}
			                		}
			                	}
			                	
			                	if(rowdata[0]){
			                		deviceAuxiliaryDeviceInfoHandsontableHelper.hot.setDataAtCell(selectedRow,0,false);
			                	}else{
			                		deviceAuxiliaryDeviceInfoHandsontableHelper.hot.setDataAtCell(selectedRow,0,true);
			                	}
	                    	}
	                    }
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>0 && deviceAuxiliaryDeviceInfoHandsontableHelper!=null&&deviceAuxiliaryDeviceInfoHandsontableHelper.hot!=''&&deviceAuxiliaryDeviceInfoHandsontableHelper.hot!=undefined && deviceAuxiliaryDeviceInfoHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=deviceAuxiliaryDeviceInfoHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	        	});
	        }
	        //保存数据
	        deviceAuxiliaryDeviceInfoHandsontableHelper.saveData = function () {}
	        deviceAuxiliaryDeviceInfoHandsontableHelper.clearContainer = function () {
	        	deviceAuxiliaryDeviceInfoHandsontableHelper.AllData = [];
	        }
	        return deviceAuxiliaryDeviceInfoHandsontableHelper;
	    }
};