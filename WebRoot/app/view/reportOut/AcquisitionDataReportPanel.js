var singleWellAcquisitionDataReportHelper=null;
var productionAcquisitionDataReportHelper=null;
Ext.define("AP.view.reportOut.AcquisitionDataReportPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.AcquisitionDataReportPanel',
    layout: 'fit',
    id: 'AcquisitionDataReportPanel_Id',
    border: false,
    initComponent: function () {
    	var me = this;
    	
    	var deviceCombStore = new Ext.data.JsonStore({
        	pageSize:defaultWellComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
            	url: context + '/reportDataMamagerController/loadAcquisitionDataReportDeviceComboxList',
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
                    var deviceName = Ext.getCmp('AcquisitionDataReportPanelDeviceListCombo_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceName: deviceName
                    };
                    Ext.apply(store.proxy.extraParams,new_params);
                }
            }
        });
        
        var deviceCombo = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: loginUserLanguageResource.deviceName,
                    id: "AcquisitionDataReportPanelDeviceListCombo_Id",
                    labelWidth: getLabelWidth(loginUserLanguageResource.deviceName,loginUserLanguage),
                    width: (getLabelWidth(loginUserLanguageResource.deviceName,loginUserLanguage)+110),
                    labelAlign: 'left',
                    queryMode: 'remote',
                    typeAhead: true,
                    store: deviceCombStore,
                    autoSelect: false,
                    editable: true,
                    triggerAction: 'all',
                    displayField: "boxval",
                    valueField: "boxkey",
                    pageSize:comboxPagingStatus,
                    minChars:0,
                    emptyText: '--'+loginUserLanguageResource.all+'--',
                    blankText: '--'+loginUserLanguageResource.all+'--',
                    listeners: {
                        expand: function (sm, selections) {
                            deviceCombo.getStore().loadPage(1); // 加载井下拉框的store
                        },
                        select: function (combo, record, index) {
                        	Ext.getCmp("SingleWellAcquisitionDataReportDeviceListSelectRow_Id").setValue(-1);
                        	Ext.getCmp("SingleWellAcquisitionDataReportDeviceListGridPanel_Id").getStore().load();
                        }
                    }
                });
    	
    	
    	
    	Ext.apply(me, {
        	items: [{
        		xtype: 'tabpanel',
        		id:"AcquisitionDataReportTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'left',
        		items: [{
    				title: loginUserLanguageResource.singleDeviceReport,
    				id:'SingleWellAcquisitionDataReportTabPanel_Id',
    				iconCls: 'check3',
    				tbar: [{
    	                xtype: 'button',
    	                text: loginUserLanguageResource.refresh,
    	                iconCls: 'note-refresh',
    	                hidden:false,
    	                handler: function (v, o) {
    	                	var gridPanel = Ext.getCmp("SingleWellAcquisitionDataReportDeviceListGridPanel_Id");
    	        			if (isNotVal(gridPanel)) {
    	        				gridPanel.getStore().load();
    	        			}else{
    	        				Ext.create('AP.store.reportOut.SingleWellAcquisitionDataReportWellListStore');
    	        			}
    	                }
    	    		},'-',deviceCombo,'-', {
    	                xtype: 'datefield',
    	                anchor: '100%',
    	                fieldLabel: loginUserLanguageResource.date,
    	                labelWidth: getLabelWidth(loginUserLanguageResource.date,loginUserLanguage),
    	                width: (getLabelWidth(loginUserLanguageResource.date,loginUserLanguage)+100),
    	                format: 'Y-m-d',
    	                editable:false,
    	                id: 'SingleWellAcquisitionDataReportStartDate_Id',
    	                listeners: {
    	                	select: function (combo, record, index) {
    	                        try {
    	                        	CreateSingleWellAcquisitionDataReportTable();
    	                        	CreateSingleWellAcquisitionDataReportCurve();
    	                        } catch (ex) {
    	                            Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.dataQueryFailure);
    	                        }
    	                    }
    	                }
    	            },{
    	                xtype: 'datefield',
    	                anchor: '100%',
    	                hidden: false,
    	                fieldLabel: loginUserLanguageResource.timeTo,
    	                labelWidth: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage),
    	                width: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage)+95,
    	                format: 'Y-m-d',
    	                editable:false,
    	                id: 'SingleWellAcquisitionDataReportEndDate_Id',
    	                value: new Date(),
    	                listeners: {
    	                	select: function (combo, record, index) {
    	                        try {
    	                        	CreateSingleWellAcquisitionDataReportTable();
    	                        	CreateSingleWellAcquisitionDataReportCurve();
    	                        } catch (ex) {
    	                            Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.dataQueryFailure);
    	                        }
    	                    }
    	                }
    	            },'-',{
    	                xtype: 'button',
    	                text: loginUserLanguageResource.search,
    	                iconCls: 'search',
    	                hidden:false,
    	                handler: function (v, o) {
    	                	CreateSingleWellAcquisitionDataReportTable();
    	                	CreateSingleWellAcquisitionDataReportCurve();
    	                }
    	    		},'-',{
    	                xtype: 'button',
    	                text: loginUserLanguageResource.bulkExportData,
    	                iconCls: 'export',
    	                handler: function (v, o) {
    	                	batchExportSingleWellAcquisitionDataReportData();
    	                }
    	            },{
    	            	id: 'SingleWellAcquisitionDataReportDeviceListSelectRow_Id',
    	            	xtype: 'textfield',
    	                value: -1,
    	                hidden: true
    	             }],
    	            layout: 'border',
    	            border: false,
    	            items: [{
    	            	region: 'west',
    	            	width: '20%',
    	            	title: loginUserLanguageResource.deviceList,
    	            	id: 'SingleWellAcquisitionDataReportWellListPanel_Id',
    	            	collapsible: true, // 是否可折叠
    	                collapsed:false,//是否折叠
    	                split: true, // 竖折叠条
    	            	layout: "fit"
    	            },{
    	            	region: 'center',
    	            	layout:'border',
                    	border: false,
                    	items:[{
                    		region:'north',
                    		height:'50%',
                    		layout:'fit',
                    		title:loginUserLanguageResource.reportCurve,
                    		collapsible: true, // 是否可折叠
                            collapsed:false,//是否折叠
                            split: true, // 竖折叠条
                            id:'SingleWellAcquisitionDataReportCurvePanel_id',
                            autoScroll: true,
                            items:[{
                            	layout: 'fit',
                                minHeight: otherCardMinHeight,   // 图表最小可视高度
                                autoScroll: false,                // 禁止内部滚动
                                border: false,html: '<div id="SingleWellAcquisitionDataReportCurveDiv_Id" style="width:100%;height:100%;"></div>',
                                listeners: {
                                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        if ($("#SingleWellAcquisitionDataReportCurveDiv_Id").highcharts() != undefined) {
                                        	highchartsResize("SingleWellAcquisitionDataReportCurveDiv_Id");
                                        }
                                    }
                                }
                            }]
                    	},{
                    		region: 'center',
                    		title:loginUserLanguageResource.reportData,
                            layout: "fit",
                        	id:'SingleWellAcquisitionDataReportPanel_id',
                        	tbar:['->',{
                                xtype: 'button',
                                text: loginUserLanguageResource.exportData,
                                iconCls: 'export',
                                handler: function (v, o) {
                                	ExportSingleWellAcquisitionDataReportData();
                                }
                            },'-',{
                                xtype: 'button',
                                text: loginUserLanguageResource.save,
                                iconCls: 'save',
                                disabled: loginUserRoleReportEdit!=1,
                                handler: function (v, o) {
                                	singleWellAcquisitionDataReportHelper.saveData();
                                }
                            },'-', {
                                id: 'SingleWellAcquisitionDataReportTotalCount_Id',
                                xtype: 'component',
                                tpl: loginUserLanguageResource.totalCount + ': {count}',
                                style: 'margin-right:15px'
                            }],
                            html:'<div class="SingleWellAcquisitionDataReportContainer" style="width:100%;height:100%;"><div class="con" id="SingleWellAcquisitionDataReportDiv_id"></div></div>',
                            listeners: {
                            	resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            		if(singleWellAcquisitionDataReportHelper!=null && singleWellAcquisitionDataReportHelper.hot!=undefined){
                            			var newWidth=width;
                            			var newHeight=height-22-1;//减去工具条高度
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                		singleWellAcquisitionDataReportHelper.hot.updateSettings({
                                			width:newWidth,
                                			height:newHeight
                                		});
                                	}
                            	}
                            }
                    	}]
    	            }]
    			},{
    				title: loginUserLanguageResource.areaReport,
    				id:'ProductionAcquisitionDataReportTabPanel_Id',
    				layout: "border",
    				border: false,
    				tbar: [{
    	                xtype: 'button',
    	                text: loginUserLanguageResource.refresh,
    	                iconCls: 'note-refresh',
    	                hidden:false,
    	                handler: function (v, o) {
    	                	Ext.getCmp("ProductionAcquisitionDataReportDate_Id").setValue("");
                        	Ext.getCmp("ProductionAcquisitionDataReportDate_Id").setRawValue("");
                        	CreateProductionAcquisitionDataReportTable();
                        	CreateProductionAcquisitionDataReportCurve();
    	                }
    	    		},'-', {
    	                xtype: 'datefield',
    	                anchor: '100%',
    	                fieldLabel: loginUserLanguageResource.date,
    	                labelWidth: getLabelWidth(loginUserLanguageResource.date,loginUserLanguage),
    	                width: (getLabelWidth(loginUserLanguageResource.date,loginUserLanguage)+100),
    	                format: 'Y-m-d',
    	                editable:false,
    	                id: 'ProductionAcquisitionDataReportStartDate_Id',
//    	                value: new Date(),
    	                listeners: {
    	                	select: function (combo, record, index) {
    	                        try {
    	                        	Ext.getCmp("ProductionAcquisitionDataReportDate_Id").setValue("");
    	                        	Ext.getCmp("ProductionAcquisitionDataReportDate_Id").setRawValue("");
    	                        	CreateProductionAcquisitionDataReportTable();
    	                        	CreateProductionAcquisitionDataReportCurve();
    	                        } catch (ex) {
    	                            Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.dataQueryFailure);
    	                        }
    	                    }
    	                }
    	            },{
    	                xtype: 'datefield',
    	                anchor: '100%',
    	                hidden: false,
    	                fieldLabel: loginUserLanguageResource.timeTo,
    	                labelWidth: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage),
    	                width: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage)+95,
    	                format: 'Y-m-d',
    	                editable:false,
    	                id: 'ProductionAcquisitionDataReportEndDate_Id',
    	                value: new Date(),
    	                listeners: {
    	                	select: function (combo, record, index) {
    	                        try {
    	                        	Ext.getCmp("ProductionAcquisitionDataReportDate_Id").setValue("");
    	                        	Ext.getCmp("ProductionAcquisitionDataReportDate_Id").setRawValue("");
    	                        	CreateProductionAcquisitionDataReportTable();
    	                        	CreateProductionAcquisitionDataReportCurve();
    	                        } catch (ex) {
    	                            Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.dataQueryFailure);
    	                        }
    	                    }
    	                }
    	            },'-',{
    	                xtype: 'button',
    	                text: loginUserLanguageResource.search,
    	                iconCls: 'search',
    	                hidden:false,
    	                handler: function (v, o) {
    	                	Ext.getCmp("ProductionAcquisitionDataReportDate_Id").setValue("");
    	                	Ext.getCmp("ProductionAcquisitionDataReportDate_Id").setRawValue("");
    	                	CreateProductionAcquisitionDataReportTable();
    	                	CreateProductionAcquisitionDataReportCurve();
    	                }
    	    		},{
    	            	id: 'ProductionAcquisitionDataReportInstanceListSelectRow_Id',
    	            	xtype: 'textfield',
    	                value: -1,
    	                hidden: true
    	            }],
    	            items:[{
                		region:'north',
                		height:'50%',
                		layout:'fit',
                		title:loginUserLanguageResource.reportCurve,
                		collapsible: true, // 是否可折叠
                        collapsed:false,//是否折叠
                        split: true, // 竖折叠条
                        border: false,
                        id:'ProductionAcquisitionDataReportCurvePanel_id',
                        autoScroll: true,
                        items:[{
                        	layout: 'fit',
                            minHeight: otherCardMinHeight,   // 图表最小可视高度
                            autoScroll: false,                // 禁止内部滚动
                            border: false,
                            html: '<div id="ProductionAcquisitionDataReportCurveDiv_Id" style="width:100%;height:100%;"></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                    if ($("#ProductionAcquisitionDataReportCurveDiv_Id").highcharts() != undefined) {
                                    	highchartsResize("ProductionAcquisitionDataReportCurveDiv_Id");
                                    }
                                }
                            }
                        }]
                	},{
                		region: 'center',
                		title:loginUserLanguageResource.reportData,
                        layout: "fit",
                    	id:'ProductionAcquisitionDataReportPanel_id',
                        border: false,
                        tbar:[{
                            xtype: 'button',
                            text: loginUserLanguageResource.forward,
                            iconCls: 'forward',
                            id:'ProductionAcquisitionDataReportForwardBtn_Id',
                            handler: function (v, o) {
                            	var str = Ext.getCmp("ProductionAcquisitionDataReportDate_Id").rawValue;
                            	var startDate = new Date(Date.parse(str .replace(/-/g, '/')));
                            	var day=-1;
                            	var value = startDate.getTime();
                            	value += day * (24 * 3600 * 1000);
                            	var endDate = new Date(value);
                            	Ext.getCmp("ProductionAcquisitionDataReportDate_Id").setValue(endDate);
                            	CreateProductionAcquisitionDataReportTable();
                            }
                        },'-',{
                            xtype: 'datefield',
                            anchor: '100%',
                            hidden: false,
                            editable:false,
                            readOnly:true,
                            width: 90,
                            format: 'Y-m-d',
                            editable:false,
                            id: 'ProductionAcquisitionDataReportDate_Id',
                            listeners: {
                            	change ( thisField, newValue, oldValue, eOpts )  {
                            		var startDateStr=Ext.getCmp("ProductionAcquisitionDataReportStartDate_Id").rawValue;
                            		var endDateStr=Ext.getCmp("ProductionAcquisitionDataReportEndDate_Id").rawValue;
                            		var reportDateStr=Ext.getCmp("ProductionAcquisitionDataReportDate_Id").rawValue;
                            		
                            		var startDate = new Date(Date.parse(startDateStr .replace(/-/g, '/'))).getTime();
                            		var endDate = new Date(Date.parse(endDateStr .replace(/-/g, '/'))).getTime();
                            		var reportDate = new Date(Date.parse(reportDateStr .replace(/-/g, '/'))).getTime();
                            		
                            		
                            		if(reportDate>startDate){
                            			Ext.getCmp("ProductionAcquisitionDataReportForwardBtn_Id").enable();
                            		}else{
                            			Ext.getCmp("ProductionAcquisitionDataReportForwardBtn_Id").disable();
                            		}
                            		
                            		if(reportDate<endDate){
                            			Ext.getCmp("ProductionAcquisitionDataReportBackwardsBtn_Id").enable();
                            		}else{
                            			Ext.getCmp("ProductionAcquisitionDataReportBackwardsBtn_Id").disable();
                            		}
                            	}
                            }
                        },'-',{
                            xtype: 'button',
                            text: loginUserLanguageResource.backward,
                            id:'ProductionAcquisitionDataReportBackwardsBtn_Id',
                            iconCls: 'backwards',
                            handler: function (v, o) {
                            	var str = Ext.getCmp("ProductionAcquisitionDataReportDate_Id").rawValue;
                            	var startDate = new Date(Date.parse(str .replace(/-/g, '/')));
                            	var day=1;
                            	var value = startDate .getTime();
                            	value += day * (24 * 3600 * 1000);
                            	var endDate = new Date(value);
                            	Ext.getCmp("ProductionAcquisitionDataReportDate_Id").setValue(endDate);
                            	CreateProductionAcquisitionDataReportTable();
                            }
                        },'->', {
                            xtype: 'button',
                            text: loginUserLanguageResource.exportData,
                            iconCls: 'export',
                            handler: function (v, o) {
                            	var timestamp=new Date().getTime();
                            	var key='exportProductionAcquisitionDataReportData'+timestamp;
                            	
                            	var selectedOrgName="";
                            	var selectedOrgId="";
                            	var deviceType=getDeviceTypeFromTabId("AcquisitionDataReportRootTabPanel");
                            	var IframeViewStore = Ext.getCmp("IframeView_Id").getStore();
                            	var count=IframeViewStore.getCount();
                            	var IframeViewSelection = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
                            	if (IframeViewSelection.length > 0) {
                            		selectedOrgName=IframeViewSelection[0].data.text;
                            		selectedOrgId=IframeViewSelection[0].data.orgId;
                            	} else {
                            		if(count>0){
                            			selectedOrgName=IframeViewStore.getAt(0).data.text;
                            			selectedOrgId=IframeViewStore.getAt(0).data.orgId;
                            		}
                            	}
                            	
                            	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                                var startDate = Ext.getCmp('ProductionAcquisitionDataReportStartDate_Id').rawValue;
                                var endDate = Ext.getCmp('ProductionAcquisitionDataReportEndDate_Id').rawValue;
                                var reportDate = Ext.getCmp('ProductionAcquisitionDataReportDate_Id').rawValue;
                            	
                            	var url=context + '/reportDataMamagerController/exportProductionAcquisitionDataReportData?deviceType='+deviceType
                            	+'&reportType=1'
                            	+'&selectedOrgName='+URLencode(URLencode(selectedOrgName))
                            	+'&startDate='+startDate
                            	+'&endDate='+endDate
                            	+'&reportDate='+reportDate
                            	+'&orgId='+orgId
                            	+'&key='+key;
                            	exportDataMask(key,"AcquisitionDataReportPanel_Id",loginUserLanguageResource.loadingData);
                            	document.location.href = url;
                            }
                        },'-',{
                            xtype: 'button',
                            text: loginUserLanguageResource.bulkExportData,
                            iconCls: 'export',
                            hidden:true,
                            handler: function (v, o) {
                            	var timestamp=new Date().getTime();
                            	var key='batchExportProductionAcquisitionDataReportData'+timestamp;
                            	
                            	var selectedOrgName="";
                            	var selectedOrgId="";
                            	var deviceType=getDeviceTypeFromTabId("AcquisitionDataReportRootTabPanel");
                            	var IframeViewStore = Ext.getCmp("IframeView_Id").getStore();
                            	var count=IframeViewStore.getCount();
                            	var IframeViewSelection = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
                            	if (IframeViewSelection.length > 0) {
                            		selectedOrgName=IframeViewSelection[0].data.text;
                            		selectedOrgId=IframeViewSelection[0].data.orgId;
                            	} else {
                            		if(count>0){
                            			selectedOrgName=IframeViewStore.getAt(0).data.text;
                            			selectedOrgId=IframeViewStore.getAt(0).data.orgId;
                            		}
                            	}
                            	
                            	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                                var startDate = Ext.getCmp('ProductionAcquisitionDataReportStartDate_Id').rawValue;
                                var endDate = Ext.getCmp('ProductionAcquisitionDataReportEndDate_Id').rawValue;
                                var reportDate = Ext.getCmp('ProductionAcquisitionDataReportDate_Id').rawValue;
                            	
                            	var url=context + '/reportDataMamagerController/batchExportProductionAcquisitionDataReportData?deviceType='+deviceType
                            	+'&reportType=1'
                            	+'&selectedOrgName='+URLencode(URLencode(selectedOrgName))
                            	+'&startDate='+startDate
                            	+'&endDate='+endDate
                            	+'&reportDate='+reportDate
                            	+'&orgId='+orgId
                            	+'&key='+key;
                            	//exportDataMask(key,"AcquisitionDataReportPanel_Id",loginUserLanguageResource.loadingData);
                            	//document.location.href = url;
                            }
                        },'-', {
                            xtype: 'button',
                            text: loginUserLanguageResource.save,
                            iconCls: 'save',
                            disabled: loginUserRoleReportEdit!=1,
                            handler: function (v, o) {
                            	productionAcquisitionDataReportHelper.saveData();
                            }
                        },'-', {
                            id: 'ProductionAcquisitionDataReportTotalCount_Id',
                            xtype: 'component',
                            tpl: loginUserLanguageResource.totalCount + ': {count}',
                            style: 'margin-right:15px'
                        }],
                        html:'<div class="ProductionAcquisitionDataReportContainer" style="width:100%;height:100%;"><div class="con" id="ProductionAcquisitionDataReportDiv_id"></div></div>',
                        listeners: {
                        	resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        		if(productionAcquisitionDataReportHelper!=null && productionAcquisitionDataReportHelper.hot!=undefined){
//                        			productionAcquisitionDataReportHelper.hot.refreshDimensions();
                        			var newWidth=width;
                            		var newHeight=height-22-1;//减去工具条高度
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		productionAcquisitionDataReportHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                        	}
                        }
                	}]
    			}],
        		listeners: {
        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        				if(oldCard!=undefined){
                			oldCard.setIconCls(null);
                	    }
                	    if(newCard!=undefined){
                	    	newCard.setIconCls('check3');				
                	    }
        			},
        			tabchange: function (tabPanel, newCard,oldCard, obj) {
        				Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
        				if(newCard.id=="SingleWellAcquisitionDataReportTabPanel_Id"){
        					Ext.getCmp('AcquisitionDataReportPanelDeviceListCombo_Id').setRawValue('');
        					Ext.getCmp('AcquisitionDataReportPanelDeviceListCombo_Id').setValue('');
        					var gridPanel = Ext.getCmp("SingleWellAcquisitionDataReportDeviceListGridPanel_Id");
        					if (isNotVal(gridPanel)) {
        						gridPanel.getStore().load();
        					}else{
        						Ext.create('AP.store.reportOut.SingleWellAcquisitionDataReportWellListStore');
        					}
        				}else if(newCard.id=="ProductionAcquisitionDataReportTabPanel_Id"){
        					CreateProductionAcquisitionDataReportTable();
        					CreateProductionAcquisitionDataReportCurve();
        				}
        			}
        		}
            }]
        });
        me.callParent(arguments);
    }
});

function CreateSingleWellAcquisitionDataReportTable(){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var startDate = Ext.getCmp('SingleWellAcquisitionDataReportStartDate_Id').rawValue;
    var endDate = Ext.getCmp('SingleWellAcquisitionDataReportEndDate_Id').rawValue;
    
    var deviceName='';
    var deviceId=0;
    var calculateType=0;
    var deviceType=getDeviceTypeFromTabId("AcquisitionDataReportRootTabPanel");
    var selectRow= Ext.getCmp("SingleWellAcquisitionDataReportDeviceListSelectRow_Id").getValue();
    if(Ext.getCmp("SingleWellAcquisitionDataReportDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
    	deviceName=Ext.getCmp("SingleWellAcquisitionDataReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
    	deviceId=Ext.getCmp("SingleWellAcquisitionDataReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
    	calculateType=Ext.getCmp("SingleWellAcquisitionDataReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.calculateType;
    }
    
    if(Ext.getCmp("SingleWellAcquisitionDataReportPanel_id")!=undefined){
        Ext.getCmp("SingleWellAcquisitionDataReportPanel_id").el.mask(loginUserLanguageResource.loadingData).show();
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reportDataMamagerController/getSingleWellAcquisitionDataRangeReportData',
		success:function(response) {
			if(Ext.getCmp("SingleWellAcquisitionDataReportPanel_id")!=undefined){
				Ext.getCmp("SingleWellAcquisitionDataReportPanel_id").getEl().unmask();
			}
			var result =  Ext.JSON.decode(response.responseText);
			
			var startDate=Ext.getCmp('SingleWellAcquisitionDataReportStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(result.startDate);
            }
            var endDate=Ext.getCmp('SingleWellAcquisitionDataReportEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(result.endDate);
            }
            
			if(singleWellAcquisitionDataReportHelper!=null){
				if(singleWellAcquisitionDataReportHelper.hot!=undefined){
					singleWellAcquisitionDataReportHelper.hot.destroy();
				}
				singleWellAcquisitionDataReportHelper=null;
			}
			if(result.success){
				if(singleWellAcquisitionDataReportHelper==null || singleWellAcquisitionDataReportHelper.hot==undefined){
					singleWellAcquisitionDataReportHelper = SingleWellAcquisitionDataReportHelper.createNew("SingleWellAcquisitionDataReportDiv_id","SingleWellAcquisitionDataReportContainer",result.template,result.data,result.columns);
					singleWellAcquisitionDataReportHelper.createTable();
				}
			}else{
				$("#SingleWellAcquisitionDataReportDiv_id").html('');
			}
			
			Ext.getCmp("SingleWellAcquisitionDataReportTotalCount_Id").update({count: result.data.length});
		},
		failure:function(){
			if(Ext.getCmp("SingleWellAcquisitionDataReportPanel_id")!=undefined){
				Ext.getCmp("SingleWellAcquisitionDataReportPanel_id").getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.ajaxError);
		},
		params: {
			orgId: orgId,
			deviceId:deviceId,
			deviceName: deviceName,
			calculateType: calculateType,
			startDate: startDate,
			endDate: endDate,
			reportType: 0,
            deviceType:deviceType
        }
	});
};


var SingleWellAcquisitionDataReportHelper = {
	    createNew: function (divId, containerid,templateData,contentData,columns) {
	        var singleWellAcquisitionDataReportHelper = {};
	        singleWellAcquisitionDataReportHelper.templateData=templateData;
	        singleWellAcquisitionDataReportHelper.contentData=contentData;
	        singleWellAcquisitionDataReportHelper.columns=columns;
	        singleWellAcquisitionDataReportHelper.get_data = {};
	        singleWellAcquisitionDataReportHelper.data=[];
	        singleWellAcquisitionDataReportHelper.sourceData=[];
	        singleWellAcquisitionDataReportHelper.hot = '';
	        singleWellAcquisitionDataReportHelper.container = document.getElementById(divId);
	        singleWellAcquisitionDataReportHelper.columnCount=0;
	        singleWellAcquisitionDataReportHelper.editData={};
	        singleWellAcquisitionDataReportHelper.contentUpdateList = [];
	        
	        singleWellAcquisitionDataReportHelper.colWidths=[];
	        if(loginUserLanguage=='zh_CN'){
	        	singleWellAcquisitionDataReportHelper.colWidths=singleWellAcquisitionDataReportHelper.templateData.columnWidths_zh_CN
	        }else if(loginUserLanguage=='en'){
	        	singleWellAcquisitionDataReportHelper.colWidths=singleWellAcquisitionDataReportHelper.templateData.columnWidths_en
		    }else if(loginUserLanguage=='ru'){
	        	singleWellAcquisitionDataReportHelper.colWidths=singleWellAcquisitionDataReportHelper.templateData.columnWidths_ru
		    }
	        
	        singleWellAcquisitionDataReportHelper.initData=function(){
	        	singleWellAcquisitionDataReportHelper.data=[];
	        	for(var i=0;i<singleWellAcquisitionDataReportHelper.templateData.header.length;i++){
	        		if(loginUserLanguage=='zh_CN'){
	        			singleWellAcquisitionDataReportHelper.templateData.header[i].title=singleWellAcquisitionDataReportHelper.templateData.header[i].title_zh_CN;
	        		}else if(loginUserLanguage=='en'){
	        			singleWellAcquisitionDataReportHelper.templateData.header[i].title=singleWellAcquisitionDataReportHelper.templateData.header[i].title_en;
	        		}else if(loginUserLanguage=='ru'){
	        			singleWellAcquisitionDataReportHelper.templateData.header[i].title=singleWellAcquisitionDataReportHelper.templateData.header[i].title_ru;
	        		}
	        		
	        		singleWellAcquisitionDataReportHelper.templateData.header[i].title.push('');
	        		singleWellAcquisitionDataReportHelper.columnCount=singleWellAcquisitionDataReportHelper.templateData.header[i].title.length;
	        		
	        		var valueArr=[];
	        		var sourceValueArr=[];
	        		for(var j=0;j<singleWellAcquisitionDataReportHelper.templateData.header[i].title.length;j++){
	        			valueArr.push(singleWellAcquisitionDataReportHelper.templateData.header[i].title[j]);
	        			sourceValueArr.push(singleWellAcquisitionDataReportHelper.templateData.header[i].title[j]);
	        		}
	        		
	        		singleWellAcquisitionDataReportHelper.data.push(valueArr);
	        		singleWellAcquisitionDataReportHelper.sourceData.push(sourceValueArr);
		        }
	        	for(var i=0;i<singleWellAcquisitionDataReportHelper.contentData.length;i++){
	        		var valueArr=[];
	        		var sourceValueArr=[];
	        		for(var j=0;j<singleWellAcquisitionDataReportHelper.contentData[i].length;j++){
	        			valueArr.push(singleWellAcquisitionDataReportHelper.contentData[i][j]);
	        			sourceValueArr.push(singleWellAcquisitionDataReportHelper.contentData[i][j]);
	        		}
	        		
	        		singleWellAcquisitionDataReportHelper.data.push(valueArr);
		        	singleWellAcquisitionDataReportHelper.sourceData.push(sourceValueArr);
		        }
	        	for(var i=singleWellAcquisitionDataReportHelper.templateData.header.length;i<singleWellAcquisitionDataReportHelper.data.length;i++){
	        		for(var j=0;j<singleWellAcquisitionDataReportHelper.data[i].length;j++){
	        			var editable=false
	        			for(var k=0;k<singleWellAcquisitionDataReportHelper.templateData.editable.length;k++){
	        				if( i>=singleWellAcquisitionDataReportHelper.templateData.editable[k].startRow 
	                				&& i<=singleWellAcquisitionDataReportHelper.templateData.editable[k].endRow
	                				&& j>=singleWellAcquisitionDataReportHelper.templateData.editable[k].startColumn 
	                				&& j<=singleWellAcquisitionDataReportHelper.templateData.editable[k].endColumn
	                		){
	        					editable=true;
	        					break;
	                		}
	        			}
	        			
	        			var value=singleWellAcquisitionDataReportHelper.data[i][j];
		                if((!editable)&&value.length>12){
		                	value=value.substring(0, 11)+"...";
		                	singleWellAcquisitionDataReportHelper.data[i][j]=value;
		                }
	        		}
	        	}
	        }
	        
	        singleWellAcquisitionDataReportHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(singleWellAcquisitionDataReportHelper!=null && singleWellAcquisitionDataReportHelper.hot!=null){
	        		for(var i=0;i<singleWellAcquisitionDataReportHelper.templateData.header.length;i++){
		        		if(row==i){
		        			if(isNotVal(singleWellAcquisitionDataReportHelper.templateData.header[i].tdStyle)){
		        				if(isNotVal(singleWellAcquisitionDataReportHelper.templateData.header[i].tdStyle.fontWeight)){
		        					td.style.fontWeight = singleWellAcquisitionDataReportHelper.templateData.header[i].tdStyle.fontWeight;
		        				}
		        				if(isNotVal(singleWellAcquisitionDataReportHelper.templateData.header[i].tdStyle.fontSize)){
		        					td.style.fontSize = singleWellAcquisitionDataReportHelper.templateData.header[i].tdStyle.fontSize;
		        				}
		        				if(isNotVal(singleWellAcquisitionDataReportHelper.templateData.header[i].tdStyle.fontFamily)){
//		        					td.style.fontFamily = singleWellAcquisitionDataReportHelper.templateData.header[i].tdStyle.fontFamily;
		        				}
		        				if(isNotVal(singleWellAcquisitionDataReportHelper.templateData.header[i].tdStyle.height)){
		        					td.style.height = singleWellAcquisitionDataReportHelper.templateData.header[i].tdStyle.height;
		        				}
		        				if(isNotVal(singleWellAcquisitionDataReportHelper.templateData.header[i].tdStyle.color)){
		        					td.style.color = singleWellAcquisitionDataReportHelper.templateData.header[i].tdStyle.color;
		        				}
		        				if(isNotVal(singleWellAcquisitionDataReportHelper.templateData.header[i].tdStyle.backgroundColor)){
		        					td.style.backgroundColor = singleWellAcquisitionDataReportHelper.templateData.header[i].tdStyle.backgroundColor;
		        				}
		        				if(isNotVal(singleWellAcquisitionDataReportHelper.templateData.header[i].tdStyle.textAlign)){
		        					td.style.textAlign = singleWellAcquisitionDataReportHelper.templateData.header[i].tdStyle.textAlign;
		        				}
		        			}
		        			break;
		        		}
		        	}
	        		if(row>=singleWellAcquisitionDataReportHelper.templateData.header.length){
	        			td.style.whiteSpace='nowrap'; //文本不换行
		            	td.style.overflow='hidden';//超出部分隐藏
		            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        		}
	        		
	        	}
	        }
	        
	        singleWellAcquisitionDataReportHelper.addEditableColor = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.color='#ff0000'; 
	             td.style.whiteSpace='nowrap'; //文本不换行
	             td.style.overflow='hidden';//超出部分隐藏
	             td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        singleWellAcquisitionDataReportHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        singleWellAcquisitionDataReportHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        singleWellAcquisitionDataReportHelper.createTable = function () {
	            singleWellAcquisitionDataReportHelper.container.innerHTML = "";
	            singleWellAcquisitionDataReportHelper.hot = new Handsontable(singleWellAcquisitionDataReportHelper.container, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		theme: 'ht-theme-classic',
	            	data: singleWellAcquisitionDataReportHelper.data,
	            	hiddenColumns: {
	                    columns: [singleWellAcquisitionDataReportHelper.columnCount-1],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
//	            	columns:singleWellAcquisitionDataReportHelper.columns,
	            	fixedRowsTop:singleWellAcquisitionDataReportHelper.templateData.fixedRowsTop, 
	                fixedRowsBottom: singleWellAcquisitionDataReportHelper.templateData.fixedRowsBottom,
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: singleWellAcquisitionDataReportHelper.templateData.rowHeights,
					colWidths: singleWellAcquisitionDataReportHelper.colWidths,
//					colWidths: [50, 120, 105, 100, 130, 105, 100, 130, 140, 120, 100, 100, 100, 80, 140, 120, 150, 120, 140, 140, 100, 130, 130, 130, 150, 120, 75],
					rowHeaders: false, //显示行头
//					rowHeaders(index) {
//					    return 'Row ' + (index + 1);
//					},
					colHeaders: false, //显示列头
//					colHeaders(index) {
//					    return 'Col ' + (index + 1);
//					},
					stretchH: 'all',
					columnSorting: true, //允许排序
	                allowInsertRow:false,
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                mergeCells: singleWellAcquisitionDataReportHelper.templateData.mergeCells,
	                contextMenu: {
	                    items: {
	                        "copy": {
	                            name: loginUserLanguageResource.contextMenu_copy
	                        },
	                        "cut": {
	                            name: loginUserLanguageResource.contextMenu_cut
	                        }
	                    }
	                }, 
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    var colConfig = singleWellAcquisitionDataReportHelper.columns[col];
	                    var colType = colConfig.type || 'text'; 
	                    cellProperties.renderer = singleWellAcquisitionDataReportHelper.addStyle;
	                    cellProperties.editor = false;
	                    if(singleWellAcquisitionDataReportHelper.templateData.editable!=null && singleWellAcquisitionDataReportHelper.templateData.editable.length>0){
	                    	for(var i=0;i<singleWellAcquisitionDataReportHelper.templateData.editable.length;i++){
	                    		if( row>=singleWellAcquisitionDataReportHelper.templateData.editable[i].startRow 
	                    				&& row<=singleWellAcquisitionDataReportHelper.templateData.editable[i].endRow
	                    				&& col>=singleWellAcquisitionDataReportHelper.templateData.editable[i].startColumn 
	                    				&& col<=singleWellAcquisitionDataReportHelper.templateData.editable[i].endColumn
	                    		){
	                    			cellProperties.editor = colType;
	                    			cellProperties.renderer = singleWellAcquisitionDataReportHelper.addEditableColor;
	                    		}
	                    	}
	                    }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 && singleWellAcquisitionDataReportHelper!=null&&singleWellAcquisitionDataReportHelper.hot!=''&&singleWellAcquisitionDataReportHelper.hot!=undefined && singleWellAcquisitionDataReportHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=singleWellAcquisitionDataReportHelper.sourceData[coords.row][coords.col];
	                		if(coords.row>=singleWellAcquisitionDataReportHelper.templateData.header.length){
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
	                },
	                afterOnCellMouseOut: function(event, coords, TD){
	                	if(singleWellAcquisitionDataReportHelper!=null&&singleWellAcquisitionDataReportHelper.hot!=''&&singleWellAcquisitionDataReportHelper.hot!=undefined && singleWellAcquisitionDataReportHelper.hot.getDataAtCell!=undefined){
	                		var value=singleWellAcquisitionDataReportHelper.sourceData[coords.row][coords.col];
	                		if(coords.row>=singleWellAcquisitionDataReportHelper.templateData.header.length){
//	                			TD.outerHTML='<td class="htDimmed">'+TD.innerText+'</td>';
	                		}
	                	}
	                },
	                afterChange: function (changes, source) {
	                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
	                    if (singleWellAcquisitionDataReportHelper!=null && singleWellAcquisitionDataReportHelper.hot!=undefined && singleWellAcquisitionDataReportHelper.hot!='' && changes != null) {
	                        for (var i = 0; i < changes.length; i++) {
	                            var params = [];
	                            var index = changes[i][0]; //行号码
	                            var rowdata = singleWellAcquisitionDataReportHelper.hot.getDataAtRow(index);
	                            
	                            var editCellInfo={};
	                            var editRow=changes[i][0];
	                            var editCol=changes[i][1];
	                            var column=singleWellAcquisitionDataReportHelper.columns[editCol];
	                            
	                            editCellInfo.editRow=editRow;
	                            editCellInfo.editCol=editCol;
	                            editCellInfo.column=column;
	                            editCellInfo.recordId=rowdata[rowdata.length-1]
	                            editCellInfo.oldValue=changes[i][2];
	                            editCellInfo.newValue=changes[i][3];
	                            editCellInfo.header=false;
	                            if(editCellInfo.editRow<singleWellAcquisitionDataReportHelper.templateData.header.length){
	                            	editCellInfo.header=true;
	                            }
	                            
	                            var isExit=false;
	                            for(var j=0;j<singleWellAcquisitionDataReportHelper.contentUpdateList.length;j++){
	                            	if(editCellInfo.editRow==singleWellAcquisitionDataReportHelper.contentUpdateList[j].editRow && editCellInfo.editCol==singleWellAcquisitionDataReportHelper.contentUpdateList[j].editCol){
	                            		singleWellAcquisitionDataReportHelper.contentUpdateList[j].newValue=editCellInfo.newValue;
	                            		isExit=true;
	                            		break;
	                            	}
	                            }
	                            if(!isExit){
	                            	singleWellAcquisitionDataReportHelper.contentUpdateList.push(editCellInfo);
	                            }
	                        }
	                    }
	                }
	            });
	        }
	        singleWellAcquisitionDataReportHelper.getData = function (data) {
	        	
	        }
	        
	        singleWellAcquisitionDataReportHelper.saveData = function () {
	        	if(singleWellAcquisitionDataReportHelper.contentUpdateList.length>0){
	        		singleWellAcquisitionDataReportHelper.editData.contentUpdateList=singleWellAcquisitionDataReportHelper.contentUpdateList;
	        		var deviceName='';
	        	    var deviceId=0;
	        	    var deviceType=getDeviceTypeFromTabId("AcquisitionDataReportRootTabPanel");
	        	    var selectRow= Ext.getCmp("SingleWellAcquisitionDataReportDeviceListSelectRow_Id").getValue();
	        	    if(Ext.getCmp("SingleWellAcquisitionDataReportDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
	        	    	deviceName=Ext.getCmp("SingleWellAcquisitionDataReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
	        	    	deviceId=Ext.getCmp("SingleWellAcquisitionDataReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
	        	    }
//	        		alert(JSON.stringify(singleWellAcquisitionDataReportHelper.editData));
	        		Ext.Ajax.request({
	                    method: 'POST',
	                    url: context + '/reportDataMamagerController/saveSingleWellRangeDailyReportData',
	                    success: function (response) {
	                        rdata = Ext.JSON.decode(response.responseText);
	                        if (rdata.success) {
	                        	Ext.MessageBox.alert(loginUserLanguageResource.tip, loginUserLanguageResource.savedSuccessfully);
	                        	singleWellAcquisitionDataReportHelper.clearContainer();
	                        	CreateSingleWellReportTable();
	                        	CreateSingleWellReportCurve();
	                        } else {
	                        	singleWellAcquisitionDataReportHelper.clearContainer();
	                        	Ext.MessageBox.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailed+"</font>");
	                        }
	                    },
	                    failure: function () {
	                        Ext.MessageBox.alert(loginUserLanguageResource.tip, loginUserLanguageResource.requestFailed);
	                    },
	                    params: {
	                    	deviceId:deviceId,
	                    	deviceName:deviceName,
	                    	data: JSON.stringify(singleWellAcquisitionDataReportHelper.editData),
	                        deviceType: deviceType
	                    }
	                });
	        	}else{
	        		Ext.MessageBox.alert(loginUserLanguageResource.tip, loginUserLanguageResource.noDataChange);
	        	}
	        }
	        singleWellAcquisitionDataReportHelper.clearContainer = function () {
	        	singleWellAcquisitionDataReportHelper.editData={};
            	singleWellAcquisitionDataReportHelper.contentUpdateList=[];
	        }

	        var init = function () {
	        	singleWellAcquisitionDataReportHelper.initData();
	        }

	        init();
	        return singleWellAcquisitionDataReportHelper;
	    }
	};

function CreateSingleWellAcquisitionDataReportCurve(){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var startDate = Ext.getCmp('SingleWellAcquisitionDataReportStartDate_Id').rawValue;
    var endDate = Ext.getCmp('SingleWellAcquisitionDataReportEndDate_Id').rawValue;
    
    var deviceName='';
    var deviceId=0;
    var calculateType=0;
    var deviceType=getDeviceTypeFromTabId("AcquisitionDataReportRootTabPanel");
    var selectRow= Ext.getCmp("SingleWellAcquisitionDataReportDeviceListSelectRow_Id").getValue();
    if(Ext.getCmp("SingleWellAcquisitionDataReportDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
    	deviceName=Ext.getCmp("SingleWellAcquisitionDataReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
    	deviceId=Ext.getCmp("SingleWellAcquisitionDataReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
    	calculateType=Ext.getCmp("SingleWellAcquisitionDataReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.calculateType;
    }
    
    if(Ext.getCmp("SingleWellAcquisitionDataReportCurvePanel_id")!=undefined){
        Ext.getCmp("SingleWellAcquisitionDataReportCurvePanel_id").el.mask(loginUserLanguageResource.loadingData).show();
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reportDataMamagerController/getSingleWellRangeReportCurveData',
		success:function(response) {
			if(Ext.getCmp("SingleWellAcquisitionDataReportCurvePanel_id")!=undefined){
				Ext.getCmp("SingleWellAcquisitionDataReportCurvePanel_id").getEl().unmask();
			}
			var result =  Ext.JSON.decode(response.responseText);
			
			var startDate=Ext.getCmp('SingleWellAcquisitionDataReportStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(result.startDate);
            }
            var endDate=Ext.getCmp('SingleWellAcquisitionDataReportEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(result.endDate);
            }
            
		    var data = result.list;
		    var graphicSet=result.graphicSet;
		    var timeFormat='%m-%d';
		    var defaultColors=["#7cb5ec", "#434348", "#90ed7d", "#f7a35c", "#8085e9", "#f15c80", "#e4d354", "#2b908f", "#f45b5b", "#91e8e1"];
		    var tickInterval = 1;
		    tickInterval = Math.floor(data.length / 10) + 1;
		    if(tickInterval<100){
		    	tickInterval=100;
		    }
		    var title = result.deviceName+ (loginUserLanguage.toUpperCase()=='ZH_CN'?"":" ") + loginUserLanguageResourceFirstLower.dailyReportCurve;
		    var legendName =result.curveItems;
		    var curveConf=result.curveConf;
		    var color=[];
		    var color_l=[];
		    var color_r=[];
		    var color_all=[];
		    for(var i=0;i<curveConf.length;i++){
		    	var singleColor=defaultColors[i%defaultColors.length];
		    	if(curveConf[i].color!=''){
		    		singleColor='#'+curveConf[i].color;
		    	}
		    	color.push(singleColor);
		    	
		    	if(curveConf[i].yAxisOpposite){
		    		color_r.push(singleColor);
		    	}else{
		    		color_l.push(singleColor);
		    	}
		    }
		    
		    var series = [];
		    var series_l=[];
		    var series_r=[];
		    var yAxis= [];
		    var yAxis_l= [];
		    var yAxis_r= [];
		   		    
		    for (var i = 0; i < legendName.length; i++) {
		        var maxValue=null;
		        var minValue=null;
		        var allPositive=true;//全部是非负数
		        var allNegative=true;//全部是负值
		        
		        var singleSeries={};
		        singleSeries.name=legendName[i];
		        singleSeries.type='spline';
		        singleSeries.lineWidth=curveConf[i].lineWidth;
		        singleSeries.dashStyle=curveConf[i].dashStyle;
		        singleSeries.marker={enabled: false};
		        singleSeries.yAxis=i;
		        singleSeries.data=[];
		        for (var j = 0; j < data.length; j++) {
		        	var pointData=[];
		        	pointData.push(Date.parse(data[j].calDate.replace(/-/g, '/')));
		        	pointData.push(data[j].data[i]);
		        	singleSeries.data.push(pointData);
		        	if(parseFloat(data[j].data[i])<0){
		            	allPositive=false;
		            }else if(parseFloat(data[j].data[i])>=0){
		            	allNegative=false;
		            }
		        }
		        if(curveConf[i].yAxisOpposite){
		        	series_r.push(singleSeries);
		        }else{
		        	series_l.push(singleSeries);
		        }
		        
		        var opposite=curveConf[i].yAxisOpposite;
		        if(allNegative){
		        	maxValue=0;
		        }else if(allPositive){
		        	minValue=0;
		        }
		        if(JSON.stringify(graphicSet) != "{}" && isNotVal(graphicSet.Report) ){
			    	for(var j=0;j<graphicSet.Report.length;j++){
			    		if(graphicSet.Report[j].itemCode!=undefined && graphicSet.Report[j].itemCode.toUpperCase()==result.curveItemCodes[i].toUpperCase()){
			    			if(isNotVal(graphicSet.Report[j].yAxisMaxValue)){
					    		maxValue=parseFloat(graphicSet.Report[j].yAxisMaxValue);
					    	}
					    	if(isNotVal(graphicSet.Report[j].yAxisMinValue)){
					    		minValue=parseFloat(graphicSet.Report[j].yAxisMinValue);
					    	}
					    	break;
			    		}
			    	}
			    }
		        
		        var singleAxis={
		        		max:maxValue,
		        		min:minValue,
		        		title: {
		                    text: legendName[i],
		                    style: {
		                        color: color[i],
		                    }
		                },
		                labels: {
		                	style: {
		                        color: color[i],
		                    }
		                },
		                opposite:opposite,
		                lineWidth: 1,      // Y 轴主线宽度
			        	tickWidth: 1,      // 刻度线宽度
		                tickLength: 5     // 刻度线长度（可选）
		                
		          };
		        if(curveConf[i].yAxisOpposite){
		        	yAxis_r.push(singleAxis);
		        }else{
		        	yAxis_l.push(singleAxis);
		        }
		        
		    }
		    for(var i=yAxis_l.length-1;i>=0;i--){
		    	yAxis.push(yAxis_l[i]);
		    }
		    for(var i=0;i<yAxis_r.length;i++){
		    	yAxis.push(yAxis_r[i]);
		    }
		    
		    for(var i=0;i<series_l.length;i++){
		    	series_l[i].yAxis=series_l.length-1-i;
		    	series.push(series_l[i]);
		    }
		    for(var i=0;i<series_r.length;i++){
		    	series_r[i].yAxis=series_l.length+i;
		    	series.push(series_r[i]);
		    }
		    
		    for(var i=0;i<color_l.length;i++){
		    	color_all.push(color_l[i]);
		    }
		    for(var i=0;i<color_r.length;i++){
		    	color_all.push(color_r[i]);
		    }
		    initSingleWellAcquisitionDataReportCurveChartFn(series, tickInterval, 'SingleWellAcquisitionDataReportCurveDiv_Id', title, '', '', yAxis, color_all,true,timeFormat);
		},
		failure:function(){
			if(Ext.getCmp("SingleWellAcquisitionDataReportCurvePanel_id")!=undefined){
				Ext.getCmp("SingleWellAcquisitionDataReportCurvePanel_id").getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.ajaxError);
		},
		params: {
			orgId: orgId,
			deviceId:deviceId,
			deviceName: deviceName,
			calculateType: calculateType,
			startDate: startDate,
			endDate: endDate,
			reportType: 0,
            deviceType:deviceType
        }
	});
};

function initSingleWellAcquisitionDataReportCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, yAxis, color,legend,timeFormat) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
	    var mychart = new Highcharts.Chart({
	        chart: {
	            renderTo: divId,
	            type: 'spline',
	            shadow: false,
	            borderWidth: 0,
	            zoomType: 'xy',
	            zooming: {
                    mouseWheel: {
                        enabled: false
                    }
                }
	        },
	        time: {
	            timezoneOffset: new Date().getTimezoneOffset()   // 用户本地时区
	        },
	        credits: {
	            enabled: false
	        },
	        title: {
	            text: title,
	            style: {
	            	fontSize: chartTitleFontSize
	            }
	        },
	        subtitle: {
	            text: subtitle
	        },
	        colors: color,
	        xAxis: {
	            type: 'datetime',
	            title: {
	                text: xtitle
	            },
//	            tickInterval: tickInterval,
	            tickPixelInterval:tickInterval,
	            labels: {
	                formatter: function () {
	                	return this.axis.chart.time.dateFormat(timeFormat, this.value);
	                },
	                autoRotation:true,//自动旋转
	                rotation: -45 //倾斜度，防止数量过多显示不全  
//	                step: 2
	            }
	        },
	        yAxis: yAxis,
	        tooltip: {
	            crosshairs: true, //十字准线
	            shared: true,
	            style: {
	                color: '#333333',
	                fontSize: '12px',
	                padding: '8px'
	            },
	            dateTimeLabelFormats: {
	                millisecond: '%Y-%m-%d %H:%M:%S.%L',
	                second: '%Y-%m-%d %H:%M:%S',
	                minute: '%Y-%m-%d %H:%M',
	                hour: '%Y-%m-%d %H',
	                day: '%Y-%m-%d',
	                week: '%m-%d',
	                month: '%Y-%m',
	                year: '%Y'
	            }
	        },
	        exporting: {
	            enabled: true,
	            filename: title,
	            fallbackToExportServer: false,
	            sourceWidth: $("#"+divId)[0].offsetWidth,
	            sourceHeight: $("#"+divId)[0].offsetHeight,
	            menuItemDefinitions: {
	                chartConfig: {
        				text: loginUserLanguageResource.diagramSet,
        				onclick: function() {
        					var window = Ext.create("AP.view.reportOut.ReportCurveSetWindow", {
                                title: loginUserLanguageResource.reportDiagramSet,
                                unitClasses: 2
                            });
                            window.show();
        				}
        			}
	            },
	            buttons: {
	    	    	contextButton: {
	    	    		menuItems: [
	    	    			'viewFullscreen',
	    	    			'printChart',
	    	    			'separator',
	    	    			'downloadPNG',
	    	    			'downloadJPEG',
	    	    			'downloadSVG',
	    	    			'separator',
	    	    			'downloadXLS',
	    	    			'separator',
	    	    			'chartConfig'
	    	    			]
	    	    		}
	    	    }
	        },
	        plotOptions: {
	            spline: {
//	                lineWidth: 1,
	                fillOpacity: 0.3,
	                marker: {
	                    enabled: true,
	                    radius: 3, //曲线点半径，默认是4
	                    //                            symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
	                    states: {
	                        hover: {
	                            enabled: true,
	                            radius: 6
	                        }
	                    }
	                },
	                shadow: true,
	                events: {
	                	legendItemClick: function(e){
//	                		alert("第"+this.index+"个图例被点击，是否可见："+!this.visible);
//	                		return true;
	                	}
	                }
	            }
	        },
	        legend: {
	            layout: 'horizontal',//horizontal水平 vertical 垂直
	            align: 'center',  //left，center 和 right
	            verticalAlign: 'bottom',//top，middle 和 bottom
	            enabled: legend,
	            borderWidth: 0,
	            itemHiddenStyle: {
	                textDecoration: 'none'
	            }
	        },
	        series: series
	    });
	}
};

function CreateProductionAcquisitionDataReportTable(){
	var selectedOrgName="";
	var selectedOrgId="";
	var deviceType=getDeviceTypeFromTabId("AcquisitionDataReportRootTabPanel");
	var IframeViewStore = Ext.getCmp("IframeView_Id").getStore();
	var count=IframeViewStore.getCount();
	var IframeViewSelection = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
	if (IframeViewSelection.length > 0) {
		selectedOrgName=IframeViewSelection[0].data.text;
		selectedOrgId=IframeViewSelection[0].data.orgId;
	} else {
		if(count>0){
			selectedOrgName=IframeViewStore.getAt(0).data.text;
			selectedOrgId=IframeViewStore.getAt(0).data.orgId;
		}
	}
	
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var startDate = Ext.getCmp('ProductionAcquisitionDataReportStartDate_Id').rawValue;
    var endDate = Ext.getCmp('ProductionAcquisitionDataReportEndDate_Id').rawValue;
    var reportDate = Ext.getCmp('ProductionAcquisitionDataReportDate_Id').rawValue;
    
    
    
    if(Ext.getCmp("ProductionAcquisitionDataReportPanel_id")!=undefined){
        Ext.getCmp("ProductionAcquisitionDataReportPanel_id").el.mask(loginUserLanguageResource.loadingData).show();
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reportDataMamagerController/getProductionAcquisitionDataDailyReportData',
		success:function(response) {
			if(Ext.getCmp("ProductionAcquisitionDataReportPanel_id")!=undefined){
				Ext.getCmp("ProductionAcquisitionDataReportPanel_id").getEl().unmask();
			}
			var result =  Ext.JSON.decode(response.responseText);
			
			var startDate=Ext.getCmp('ProductionAcquisitionDataReportStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(result.startDate);
            }
            var endDate=Ext.getCmp('ProductionAcquisitionDataReportEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(result.endDate);
            }
            var reportDate = Ext.getCmp('ProductionAcquisitionDataReportDate_Id');
            if(reportDate.rawValue==''||null==reportDate.rawValue){
            	reportDate.setValue(result.reportDate);
            }
			if(productionAcquisitionDataReportHelper!=null){
				if(productionAcquisitionDataReportHelper.hot!=undefined){
					productionAcquisitionDataReportHelper.hot.destroy();
				}
				productionAcquisitionDataReportHelper=null;
			}
			if(result.success){
				if(productionAcquisitionDataReportHelper==null || productionAcquisitionDataReportHelper.hot==undefined){
					productionAcquisitionDataReportHelper = ProductionAcquisitionDataReportHelper.createNew("ProductionAcquisitionDataReportDiv_id","ProductionAcquisitionDataReportContainer",result.template,result.data,result.statData,result.columns);
					productionAcquisitionDataReportHelper.createTable();
				}
			}else{
				$("#ProductionAcquisitionDataReportDiv_id").html('');
			}
			
			Ext.getCmp("ProductionAcquisitionDataReportTotalCount_Id").update({count: result.data.length});
		},
		failure:function(){
			if(Ext.getCmp("ProductionAcquisitionDataReportPanel_id")!=undefined){
				Ext.getCmp("ProductionAcquisitionDataReportPanel_id").getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.ajaxError);
		},
		params: {
			orgId: orgId,
			selectedOrgName: selectedOrgName,
			startDate: startDate,
			endDate: endDate,
			reportDate: reportDate,
			reportType: 1,
            deviceType:deviceType
        }
	});
};


var ProductionAcquisitionDataReportHelper = {
	    createNew: function (divId, containerid,templateData,contentData,statData,columns) {
	        var productionAcquisitionDataReportHelper = {};
	        productionAcquisitionDataReportHelper.templateData=templateData;
	        productionAcquisitionDataReportHelper.contentData=contentData;
	        productionAcquisitionDataReportHelper.statData=statData;
	        productionAcquisitionDataReportHelper.columns=columns;
	        productionAcquisitionDataReportHelper.get_data = {};
	        productionAcquisitionDataReportHelper.data=[];
	        productionAcquisitionDataReportHelper.sourceData=[];
	        productionAcquisitionDataReportHelper.hot = '';
	        productionAcquisitionDataReportHelper.container = document.getElementById(divId);
	        productionAcquisitionDataReportHelper.columnCount=0;
	        productionAcquisitionDataReportHelper.editData={};
	        productionAcquisitionDataReportHelper.contentUpdateList = [];
	        
	        productionAcquisitionDataReportHelper.colWidths=[];
	        if(loginUserLanguage=='zh_CN'){
	        	productionAcquisitionDataReportHelper.colWidths=productionAcquisitionDataReportHelper.templateData.columnWidths_zh_CN
	        }else if(loginUserLanguage=='en'){
	        	productionAcquisitionDataReportHelper.colWidths=productionAcquisitionDataReportHelper.templateData.columnWidths_en
		    }else if(loginUserLanguage=='ru'){
	        	productionAcquisitionDataReportHelper.colWidths=productionAcquisitionDataReportHelper.templateData.columnWidths_ru
		    }
	        
	        productionAcquisitionDataReportHelper.initData=function(){
	        	productionAcquisitionDataReportHelper.data=[];
	        	for(var i=0;i<productionAcquisitionDataReportHelper.templateData.header.length;i++){
	        		if(loginUserLanguage=='zh_CN'){
	        			productionAcquisitionDataReportHelper.templateData.header[i].title=productionAcquisitionDataReportHelper.templateData.header[i].title_zh_CN;
	        		}else if(loginUserLanguage=='en'){
	        			productionAcquisitionDataReportHelper.templateData.header[i].title=productionAcquisitionDataReportHelper.templateData.header[i].title_en;
	        		}else if(loginUserLanguage=='ru'){
	        			productionAcquisitionDataReportHelper.templateData.header[i].title=productionAcquisitionDataReportHelper.templateData.header[i].title_ru;
	        		}
	        		productionAcquisitionDataReportHelper.templateData.header[i].title.push('');
	        		productionAcquisitionDataReportHelper.columnCount=productionAcquisitionDataReportHelper.templateData.header[i].title.length;
	        		
	        		var valueArr=[];
	        		var sourceValueArr=[];
	        		for(var j=0;j<productionAcquisitionDataReportHelper.templateData.header[i].title.length;j++){
	        			valueArr.push(productionAcquisitionDataReportHelper.templateData.header[i].title[j]);
	        			sourceValueArr.push(productionAcquisitionDataReportHelper.templateData.header[i].title[j]);
	        		}
	        		
	        		productionAcquisitionDataReportHelper.data.push(valueArr);
	        		productionAcquisitionDataReportHelper.sourceData.push(sourceValueArr);
		        }
	        	for(var i=0;i<productionAcquisitionDataReportHelper.contentData.length;i++){
	        		var valueArr=[];
	        		var sourceValueArr=[];
	        		for(var j=0;j<productionAcquisitionDataReportHelper.contentData[i].length;j++){
	        			valueArr.push(productionAcquisitionDataReportHelper.contentData[i][j]);
	        			sourceValueArr.push(productionAcquisitionDataReportHelper.contentData[i][j]);
	        		}
	        		
	        		productionAcquisitionDataReportHelper.data.push(valueArr);
		        	productionAcquisitionDataReportHelper.sourceData.push(sourceValueArr);
		        }
	        	
	        	for(var i=0;i<productionAcquisitionDataReportHelper.statData.length;i++){
	        		var valueArr=[];
	        		var sourceValueArr=[];
	        		for(var j=0;j<productionAcquisitionDataReportHelper.statData[i].length;j++){
	        			valueArr.push(productionAcquisitionDataReportHelper.statData[i][j]);
	        			sourceValueArr.push(productionAcquisitionDataReportHelper.statData[i][j]);
	        		}
	        		
	        		productionAcquisitionDataReportHelper.data.push(valueArr);
		        	productionAcquisitionDataReportHelper.sourceData.push(sourceValueArr);
		        }
	        	
	        	
	        	
	        	for(var i=productionAcquisitionDataReportHelper.templateData.header.length;i<productionAcquisitionDataReportHelper.data.length;i++){
	        		for(var j=0;j<productionAcquisitionDataReportHelper.data[i].length;j++){
	        			var editable=false
	        			for(var k=0;k<productionAcquisitionDataReportHelper.templateData.editable.length;k++){
	        				if( i>=productionAcquisitionDataReportHelper.templateData.editable[k].startRow 
	                				&& i<=productionAcquisitionDataReportHelper.templateData.editable[k].endRow
	                				&& j>=productionAcquisitionDataReportHelper.templateData.editable[k].startColumn 
	                				&& j<=productionAcquisitionDataReportHelper.templateData.editable[k].endColumn
	                		){
	        					editable=true;
	        					break;
	                		}
	        			}
	        			
	        			var value=productionAcquisitionDataReportHelper.data[i][j];
		                if((!editable)&&value.length>12){
		                	value=value.substring(0, 11)+"...";
		                	productionAcquisitionDataReportHelper.data[i][j]=value;
		                }
	        		}
	        	}
	        }
	        
	        productionAcquisitionDataReportHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(productionAcquisitionDataReportHelper!=null && productionAcquisitionDataReportHelper.hot!=null){
	        		for(var i=0;i<productionAcquisitionDataReportHelper.templateData.header.length;i++){
		        		if(row==i){
		        			if(isNotVal(productionAcquisitionDataReportHelper.templateData.header[i].tdStyle)){
		        				if(isNotVal(productionAcquisitionDataReportHelper.templateData.header[i].tdStyle.fontWeight)){
		        					td.style.fontWeight = productionAcquisitionDataReportHelper.templateData.header[i].tdStyle.fontWeight;
		        				}
		        				if(isNotVal(productionAcquisitionDataReportHelper.templateData.header[i].tdStyle.fontSize)){
		        					td.style.fontSize = productionAcquisitionDataReportHelper.templateData.header[i].tdStyle.fontSize;
		        				}
		        				if(isNotVal(productionAcquisitionDataReportHelper.templateData.header[i].tdStyle.fontFamily)){
//		        					td.style.fontFamily = productionAcquisitionDataReportHelper.templateData.header[i].tdStyle.fontFamily;
		        				}
		        				if(isNotVal(productionAcquisitionDataReportHelper.templateData.header[i].tdStyle.height)){
		        					td.style.height = productionAcquisitionDataReportHelper.templateData.header[i].tdStyle.height;
		        				}
		        				if(isNotVal(productionAcquisitionDataReportHelper.templateData.header[i].tdStyle.color)){
		        					td.style.color = productionAcquisitionDataReportHelper.templateData.header[i].tdStyle.color;
		        				}
		        				if(isNotVal(productionAcquisitionDataReportHelper.templateData.header[i].tdStyle.backgroundColor)){
		        					td.style.backgroundColor = productionAcquisitionDataReportHelper.templateData.header[i].tdStyle.backgroundColor;
		        				}
		        				if(isNotVal(productionAcquisitionDataReportHelper.templateData.header[i].tdStyle.textAlign)){
		        					td.style.textAlign = productionAcquisitionDataReportHelper.templateData.header[i].tdStyle.textAlign;
		        				}
		        			}
		        			break;
		        		}
		        	}
	        		if(row>=productionAcquisitionDataReportHelper.templateData.header.length){
	        			td.style.whiteSpace='nowrap'; //文本不换行
			            td.style.overflow='hidden';//超出部分隐藏
			            td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        		}
	        		
	        	}
	        }
	        
	        productionAcquisitionDataReportHelper.addEditableColor = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.color='#ff0000';
	             td.style.whiteSpace='nowrap'; //文本不换行
	             td.style.overflow='hidden';//超出部分隐藏
	             td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        productionAcquisitionDataReportHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        productionAcquisitionDataReportHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        productionAcquisitionDataReportHelper.createTable = function () {
	            productionAcquisitionDataReportHelper.container.innerHTML = "";
	            productionAcquisitionDataReportHelper.hot = new Handsontable(productionAcquisitionDataReportHelper.container, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		theme: 'ht-theme-classic',
	            	data: productionAcquisitionDataReportHelper.data,
	            	hiddenColumns: {
	                    columns: [productionAcquisitionDataReportHelper.columnCount-1],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
//	            	columns:productionAcquisitionDataReportHelper.columns,
	            	fixedRowsTop:productionAcquisitionDataReportHelper.templateData.fixedRowsTop, 
	                fixedRowsBottom: productionAcquisitionDataReportHelper.templateData.fixedRowsBottom,
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: productionAcquisitionDataReportHelper.templateData.rowHeights,
					colWidths: productionAcquisitionDataReportHelper.colWidths,
//					colWidths: [50, 90, 120, 105, 100, 130, 105, 100, 130, 140, 120, 100, 100, 100, 80, 140, 120, 150, 120, 140, 140, 100, 130, 130, 130, 150, 120, 75],
					rowHeaders: false, //显示行头
//					rowHeaders(index) {
//					    return 'Row ' + (index + 1);
//					},
					colHeaders: false, //显示列头
//					colHeaders(index) {
//					    return 'Col ' + (index + 1);
//					},
					stretchH: 'all',
					columnSorting: true, //允许排序
	                allowInsertRow:false,
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                mergeCells: productionAcquisitionDataReportHelper.templateData.mergeCells,
	                contextMenu: {
	                    items: {
	                        "copy": {
	                            name: loginUserLanguageResource.contextMenu_copy
	                        },
	                        "cut": {
	                            name: loginUserLanguageResource.contextMenu_cut
	                        }
	                    }
	                }, 
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    var colConfig = productionAcquisitionDataReportHelper.columns[col];
	                    var colType = colConfig.type || 'text'; 
	                    cellProperties.renderer = productionAcquisitionDataReportHelper.addStyle;
	                    cellProperties.editor = false;
	                    if(productionAcquisitionDataReportHelper.templateData.editable!=null && productionAcquisitionDataReportHelper.templateData.editable.length>0){
	                    	for(var i=0;i<productionAcquisitionDataReportHelper.templateData.editable.length;i++){
	                    		if( row>=productionAcquisitionDataReportHelper.templateData.editable[i].startRow 
	                    				&& row<=productionAcquisitionDataReportHelper.templateData.editable[i].endRow
	                    				&& col>=productionAcquisitionDataReportHelper.templateData.editable[i].startColumn 
	                    				&& col<=productionAcquisitionDataReportHelper.templateData.editable[i].endColumn
	                    				&& row<productionAcquisitionDataReportHelper.templateData.header.length+productionAcquisitionDataReportHelper.contentData.length
	                    		){
	                    			cellProperties.editor = colType;
	                    			cellProperties.renderer = productionAcquisitionDataReportHelper.addEditableColor;
	                    		}
	                    	}
	                    }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 && productionAcquisitionDataReportHelper!=null&&productionAcquisitionDataReportHelper.hot!=''&&productionAcquisitionDataReportHelper.hot!=undefined && productionAcquisitionDataReportHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=productionAcquisitionDataReportHelper.sourceData[coords.row][coords.col];
	                		if(coords.row>=productionAcquisitionDataReportHelper.templateData.header.length){
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
	                },
	                afterOnCellMouseOut: function(event, coords, TD){
	                	if(productionAcquisitionDataReportHelper!=null&&productionAcquisitionDataReportHelper.hot!=''&&productionAcquisitionDataReportHelper.hot!=undefined && productionAcquisitionDataReportHelper.hot.getDataAtCell!=undefined){
	                		var value=productionAcquisitionDataReportHelper.sourceData[coords.row][coords.col];
	                		if(coords.row>=productionAcquisitionDataReportHelper.templateData.header.length){
//	                			TD.outerHTML='<td class="htDimmed">'+TD.innerText+'</td>';
	                		}
	                	}
	                },
	                afterChange: function (changes, source) {
	                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
	                    if (productionAcquisitionDataReportHelper!=null && productionAcquisitionDataReportHelper.hot!=undefined && productionAcquisitionDataReportHelper.hot!='' && changes != null) {
	                        for (var i = 0; i < changes.length; i++) {
	                            var params = [];
	                            var index = changes[i][0]; //行号码
	                            var rowdata = productionAcquisitionDataReportHelper.hot.getDataAtRow(index);
	                            
	                            var editCellInfo={};
	                            var editRow=changes[i][0];
	                            var editCol=changes[i][1];
	                            var column=productionAcquisitionDataReportHelper.columns[editCol];
	                            
	                            editCellInfo.editRow=editRow;
	                            editCellInfo.editCol=editCol;
	                            editCellInfo.column=column;
	                            editCellInfo.recordId=rowdata[rowdata.length-1]
	                            editCellInfo.oldValue=changes[i][2];
	                            editCellInfo.newValue=changes[i][3];
	                            editCellInfo.header=false;
	                            if(editCellInfo.editRow<productionAcquisitionDataReportHelper.templateData.header.length){
	                            	editCellInfo.header=true;
	                            }
	                            
	                            var isExit=false;
	                            for(var j=0;j<productionAcquisitionDataReportHelper.contentUpdateList.length;j++){
	                            	if(editCellInfo.editRow==productionAcquisitionDataReportHelper.contentUpdateList[j].editRow && editCellInfo.editCol==productionAcquisitionDataReportHelper.contentUpdateList[j].editCol){
	                            		productionAcquisitionDataReportHelper.contentUpdateList[j].newValue=editCellInfo.newValue;
	                            		isExit=true;
	                            		break;
	                            	}
	                            }
	                            if(!isExit){
	                            	productionAcquisitionDataReportHelper.contentUpdateList.push(editCellInfo);
	                            }
	                        }
	                    }
	                }
	            });
	        }
	        productionAcquisitionDataReportHelper.getData = function (data) {
	        	
	        }
	        
	        productionAcquisitionDataReportHelper.saveData = function () {
	        	if(productionAcquisitionDataReportHelper.contentUpdateList.length>0){
	        		productionAcquisitionDataReportHelper.editData.contentUpdateList=productionAcquisitionDataReportHelper.contentUpdateList;
	        		Ext.Ajax.request({
	                    method: 'POST',
	                    url: context + '/reportDataMamagerController/saveSingleWellRangeDailyReportData',
	                    success: function (response) {
	                        rdata = Ext.JSON.decode(response.responseText);
	                        if (rdata.success) {
	                        	Ext.MessageBox.alert(loginUserLanguageResource.tip, loginUserLanguageResource.savedSuccessfully);
	                        	productionAcquisitionDataReportHelper.clearContainer();
	                        	CreateProductionAcquisitionDataReportTable();
	                        	CreateSingleWellAcquisitionDataReportCurve();
	                        } else {
	                        	productionAcquisitionDataReportHelper.clearContainer();
	                        	Ext.MessageBox.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailed+"</font>");
	                        }
	                    },
	                    failure: function () {
	                        Ext.MessageBox.alert(loginUserLanguageResource.tip, loginUserLanguageResource.requestFailed);
	                    },
	                    params: {
	                    	data: JSON.stringify(productionAcquisitionDataReportHelper.editData),
	                        deviceType: 1
	                    }
	                });
	        	}else{
	        		Ext.MessageBox.alert(loginUserLanguageResource.tip, loginUserLanguageResource.noDataChange);
	        	}
	        }
	        productionAcquisitionDataReportHelper.clearContainer = function () {
	        	productionAcquisitionDataReportHelper.editData={};
            	productionAcquisitionDataReportHelper.contentUpdateList=[];
	        }

	        var init = function () {
	        	productionAcquisitionDataReportHelper.initData();
	        }

	        init();
	        return productionAcquisitionDataReportHelper;
	    }
	};

function CreateProductionAcquisitionDataReportCurve(){
	var selectedOrgName="";
	var selectedOrgId="";
	var deviceType=getDeviceTypeFromTabId("AcquisitionDataReportRootTabPanel");
	var IframeViewStore = Ext.getCmp("IframeView_Id").getStore();
	var count=IframeViewStore.getCount();
	var IframeViewSelection = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
	if (IframeViewSelection.length > 0) {
		selectedOrgName=IframeViewSelection[0].data.text;
		selectedOrgId=IframeViewSelection[0].data.orgId;
	} else {
		if(count>0){
			selectedOrgName=IframeViewStore.getAt(0).data.text;
			selectedOrgId=IframeViewStore.getAt(0).data.orgId;
		}
	}
	
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var startDate = Ext.getCmp('ProductionAcquisitionDataReportStartDate_Id').rawValue;
    var endDate = Ext.getCmp('ProductionAcquisitionDataReportEndDate_Id').rawValue;
    
    if(Ext.getCmp("ProductionAcquisitionDataReportCurvePanel_id")!=undefined){
        Ext.getCmp("ProductionAcquisitionDataReportCurvePanel_id").el.mask(loginUserLanguageResource.loadingData).show();
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reportDataMamagerController/getProductionAcquisitionDataDailyReportCurveData',
		success:function(response) {
			if(Ext.getCmp("ProductionAcquisitionDataReportCurvePanel_id")!=undefined){
				Ext.getCmp("ProductionAcquisitionDataReportCurvePanel_id").getEl().unmask();
			}
			var result =  Ext.JSON.decode(response.responseText);
			
			var startDate=Ext.getCmp('ProductionAcquisitionDataReportStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(result.startDate);
            }
            var endDate=Ext.getCmp('ProductionAcquisitionDataReportEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(result.endDate);
            }
            
		    var data = result.list;
		    var graphicSet=result.graphicSet;
		    var timeFormat='%m-%d';
		    var defaultColors=["#7cb5ec", "#434348", "#90ed7d", "#f7a35c", "#8085e9", "#f15c80", "#e4d354", "#2b908f", "#f45b5b", "#91e8e1"];
		    var tickInterval = 1;
		    tickInterval = Math.floor(data.length / 10) + 1;
		    if(tickInterval<100){
		    	tickInterval=100;
		    }
		    var title = result.selectedOrgName+ (loginUserLanguage.toUpperCase()=='ZH_CN'?"":" ") + loginUserLanguageResourceFirstLower.dailyReportCurve;
		    var xTitle=loginUserLanguageResource.date;
		    var legendName =result.curveItems;
		    var curveConf=result.curveConf;
		    var color=[];
		    var color_l=[];
		    var color_r=[];
		    var color_all=[];
		    for(var i=0;i<curveConf.length;i++){
		    	var singleColor=defaultColors[i%defaultColors.length];
		    	if(curveConf[i].color!=''){
		    		singleColor='#'+curveConf[i].color;
		    	}
		    	color.push(singleColor);
		    	
		    	if(curveConf[i].yAxisOpposite){
		    		color_r.push(singleColor);
		    	}else{
		    		color_l.push(singleColor);
		    	}
		    }
		    
		    var series = [];
		    var series_l=[];
		    var series_r=[];
		    var yAxis= [];
		    var yAxis_l= [];
		    var yAxis_r= [];
		   		    
		    for (var i = 0; i < legendName.length; i++) {
		        var maxValue=null;
		        var minValue=null;
		        var allPositive=true;//全部是非负数
		        var allNegative=true;//全部是负值
		        
		        var singleSeries={};
		        singleSeries.name=legendName[i];
		        singleSeries.type='spline';
		        singleSeries.lineWidth=curveConf[i].lineWidth;
		        singleSeries.dashStyle=curveConf[i].dashStyle;
		        singleSeries.marker={enabled: false};
		        singleSeries.yAxis=i;
		        singleSeries.data=[];
		        for (var j = 0; j < data.length; j++) {
		        	var pointData=[];
		        	pointData.push(Date.parse(data[j].calDate.replace(/-/g, '/')));
		        	pointData.push(data[j].data[i]);
		        	singleSeries.data.push(pointData);
		        	if(parseFloat(data[j].data[i])<0){
		            	allPositive=false;
		            }else if(parseFloat(data[j].data[i])>=0){
		            	allNegative=false;
		            }
		        }
		        if(curveConf[i].yAxisOpposite){
		        	series_r.push(singleSeries);
		        }else{
		        	series_l.push(singleSeries);
		        }
		        
		        var opposite=curveConf[i].yAxisOpposite;
		        if(allNegative){
		        	maxValue=0;
		        }else if(allPositive){
		        	minValue=0;
		        }
		        if(JSON.stringify(graphicSet) != "{}" && isNotVal(graphicSet.History) ){
			    	for(var j=0;j<graphicSet.History.length;j++){
			    		if(graphicSet.History[j].itemCode!=undefined && graphicSet.History[j].itemCode.toUpperCase()==result.curveItemCodes[i].toUpperCase()){
			    			if(isNotVal(graphicSet.History[j].yAxisMaxValue)){
					    		maxValue=parseFloat(graphicSet.History[j].yAxisMaxValue);
					    	}
					    	if(isNotVal(graphicSet.History[j].yAxisMinValue)){
					    		minValue=parseFloat(graphicSet.History[j].yAxisMinValue);
					    	}
					    	break;
			    		}
			    	}
			    }
		        
		        var singleAxis={
		        		max:maxValue,
		        		min:minValue,
		        		title: {
		                    text: legendName[i],
		                    style: {
		                        color: color[i],
		                    }
		                },
		                labels: {
		                	style: {
		                        color: color[i],
		                    }
		                },
		                opposite:opposite,
		                lineWidth: 1,      // Y 轴主线宽度
			        	tickWidth: 1,      // 刻度线宽度
		                tickLength: 5     // 刻度线长度（可选）
		          };
		        if(curveConf[i].yAxisOpposite){
		        	yAxis_r.push(singleAxis);
		        }else{
		        	yAxis_l.push(singleAxis);
		        }
		        
		    }
		    for(var i=yAxis_l.length-1;i>=0;i--){
		    	yAxis.push(yAxis_l[i]);
		    }
		    for(var i=0;i<yAxis_r.length;i++){
		    	yAxis.push(yAxis_r[i]);
		    }
		    
		    for(var i=0;i<series_l.length;i++){
		    	series_l[i].yAxis=series_l.length-1-i;
		    	series.push(series_l[i]);
		    }
		    for(var i=0;i<series_r.length;i++){
		    	series_r[i].yAxis=series_l.length+i;
		    	series.push(series_r[i]);
		    }
		    
		    for(var i=0;i<color_l.length;i++){
		    	color_all.push(color_l[i]);
		    }
		    for(var i=0;i<color_r.length;i++){
		    	color_all.push(color_r[i]);
		    }
		    initProductionAcquisitionDataReportCurveChartFn(series, tickInterval, 'ProductionAcquisitionDataReportCurveDiv_Id', title, '', '', yAxis, color_all,true,timeFormat);
		},
		failure:function(){
			if(Ext.getCmp("ProductionAcquisitionDataReportCurvePanel_id")!=undefined){
				Ext.getCmp("ProductionAcquisitionDataReportCurvePanel_id").getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.ajaxError);
		},
		params: {
			orgId: orgId,
			selectedOrgName: selectedOrgName,
			startDate: startDate,
			endDate: endDate,
			reportType: 1,
            deviceType: deviceType
        }
	});
};

function initProductionAcquisitionDataReportCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, yAxis, color,legend,timeFormat) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
	    var mychart = new Highcharts.Chart({
	        chart: {
	            renderTo: divId,
	            type: 'spline',
	            shadow: false,
	            borderWidth: 0,
	            zoomType: 'xy',
	            zooming: {
                    mouseWheel: {
                        enabled: false
                    }
                }
	        },
	        time: {
	            timezoneOffset: new Date().getTimezoneOffset()   // 用户本地时区
	        },
	        credits: {
	            enabled: false
	        },
	        title: {
	            text: title,
	            style: {
	            	fontSize: chartTitleFontSize
	            }
	        },
	        subtitle: {
	            text: subtitle
	        },
	        colors: color,
	        xAxis: {
	            type: 'datetime',
	            title: {
	                text: xtitle
	            },
//	            tickInterval: tickInterval,
	            tickPixelInterval:tickInterval,
	            labels: {
	                formatter: function () {
	                	return this.axis.chart.time.dateFormat(timeFormat, this.value);
	                },
	                autoRotation:true,//自动旋转
	                rotation: -45 //倾斜度，防止数量过多显示不全  
//	                step: 2
	            }
	        },
	        yAxis: yAxis,
	        tooltip: {
	            crosshairs: true, //十字准线
	            shared: true,
	            style: {
	                color: '#333333',
	                fontSize: '12px',
	                padding: '8px'
	            },
	            dateTimeLabelFormats: {
	                millisecond: '%Y-%m-%d %H:%M:%S.%L',
	                second: '%Y-%m-%d %H:%M:%S',
	                minute: '%Y-%m-%d %H:%M',
	                hour: '%Y-%m-%d %H',
	                day: '%Y-%m-%d',
	                week: '%m-%d',
	                month: '%Y-%m',
	                year: '%Y'
	            }
	        },
	        exporting: {
	            enabled: true,
	            filename: title,
	            fallbackToExportServer: false,
	            sourceWidth: $("#"+divId)[0].offsetWidth,
	            sourceHeight: $("#"+divId)[0].offsetHeight,
	            buttons: {
	    	    	contextButton: {
	    	    		menuItems: [
	    	    			'viewFullscreen',
	    	    			'printChart',
	    	    			'separator',
	    	    			'downloadPNG',
	    	    			'downloadJPEG',
	    	    			'downloadSVG',
	    	    			'separator',
	    	    			'downloadCSV',
	    	    			'downloadXLS'
	    	    			]
	    	    		}
	    	    }
	        },
	        plotOptions: {
	            spline: {
//	                lineWidth: 1,
	                fillOpacity: 0.3,
	                marker: {
	                    enabled: true,
	                    radius: 3, //曲线点半径，默认是4
	                    //                            symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
	                    states: {
	                        hover: {
	                            enabled: true,
	                            radius: 6
	                        }
	                    }
	                },
	                shadow: true,
	                events: {
	                	legendItemClick: function(e){
//	                		alert("第"+this.index+"个图例被点击，是否可见："+!this.visible);
//	                		return true;
	                	}
	                }
	            }
	        },
	        legend: {
	            layout: 'horizontal',//horizontal水平 vertical 垂直
	            align: 'center',  //left，center 和 right
	            verticalAlign: 'bottom',//top，middle 和 bottom
	            enabled: legend,
	            borderWidth: 0,
	            itemHiddenStyle: {
	                textDecoration: 'none'
	            }
	        },
	        series: series
	    });
	}
};

function ExportSingleWellAcquisitionDataReportData(){
	var timestamp=new Date().getTime();
	var key='ExportSingleWellAcquisitionDataReportData'+timestamp;
	
	var leftOrg_Id = obtainParams('leftOrg_Id');
	var deviceName = Ext.getCmp('AcquisitionDataReportPanelDeviceListCombo_Id').getValue();
	var startDate = Ext.getCmp('SingleWellAcquisitionDataReportStartDate_Id').rawValue;
	var endDate = Ext.getCmp('SingleWellAcquisitionDataReportEndDate_Id').rawValue;

	var deviceName='';
	var deviceId=0;
	var calculateType=0;
	var deviceType=getDeviceTypeFromTabId("AcquisitionDataReportRootTabPanel");
	var selectRow= Ext.getCmp("SingleWellAcquisitionDataReportDeviceListSelectRow_Id").getValue();
	if(Ext.getCmp("SingleWellAcquisitionDataReportDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
		deviceName=Ext.getCmp("SingleWellAcquisitionDataReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
		deviceId=Ext.getCmp("SingleWellAcquisitionDataReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
		calculateType=Ext.getCmp("SingleWellAcquisitionDataReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.calculateType;
	}

	var url=context + '/reportDataMamagerController/exportSingleWellAcquisitionDataReportData?deviceType='+deviceType
	+'&reportType=0'
	+'&calculateType='+calculateType
	+'&deviceName='+URLencode(URLencode(deviceName))
	+'&deviceId='+deviceId
	+'&startDate='+startDate
	+'&endDate='+endDate
	+'&orgId='+leftOrg_Id
	+'&key='+key;
	exportDataMask(key,"AcquisitionDataReportPanel_Id",loginUserLanguageResource.loadingData);
	document.location.href = url;
}

function batchExportSingleWellAcquisitionDataReportData(){
	var timestamp=new Date().getTime();
	var key='batchExportSingleWellAcquisitionDataReportData'+timestamp;
	
	var leftOrg_Id = obtainParams('leftOrg_Id');
	var deviceName = Ext.getCmp('AcquisitionDataReportPanelDeviceListCombo_Id').getValue();
	var deviceType=getDeviceTypeFromTabId("AcquisitionDataReportRootTabPanel");
	var deviceTypeName=getTabPanelActiveName("AcquisitionDataReportRootTabPanel");
	
	
	
	var startDate = Ext.getCmp('SingleWellAcquisitionDataReportStartDate_Id').rawValue;
	var endDate = Ext.getCmp('SingleWellAcquisitionDataReportEndDate_Id').rawValue;

	var url=context + '/reportDataMamagerController/batchExportSingleWellAcquisitionDataReportData?deviceType='+deviceType
	+'&reportType=0'
	+'&deviceName='+URLencode(URLencode(deviceName))
	+'&deviceTypeName='+URLencode(URLencode(deviceTypeName))
	+'&startDate='+startDate
	+'&endDate='+endDate
	+'&orgId='+leftOrg_Id
	+'&key='+key;
	exportDataMask(key,"AcquisitionDataReportPanel_Id",loginUserLanguageResource.loadingData);
	document.location.href = url;
}