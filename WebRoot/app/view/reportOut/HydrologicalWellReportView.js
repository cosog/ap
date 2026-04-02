var hydrologicalWellReportHelper=null;
Ext.define("AP.view.reportOut.HydrologicalWellReportView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.HydrologicalWellReportView',
    layout: 'fit',
    id: 'HydrologicalWellReportView_Id',
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
            	url: context + '/reportDataMamagerController/loadHydrologicalWellReportDeviceComboxList',
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
                    var deviceName = Ext.getCmp('HydrologicalWellReportPanelDeviceListCombo_Id').getValue();
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
                    id: "HydrologicalWellReportPanelDeviceListCombo_Id",
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
                        	Ext.getCmp("HydrologicalWellReportDeviceListSelectRow_Id").setValue(-1);
                        	Ext.getCmp("HydrologicalWellReportDeviceListGridPanel_Id").getStore().load();
                        }
                    }
                });
        Ext.apply(me, {
            layout: 'border',
            border: false,
            tbar: [{
                xtype: 'button',
                text: loginUserLanguageResource.refresh,
                iconCls: 'note-refresh',
                hidden:false,
                handler: function (v, o) {
                	var gridPanel = Ext.getCmp("HydrologicalWellReportDeviceListGridPanel_Id");
        			if (isNotVal(gridPanel)) {
        				gridPanel.getStore().load();
        			}else{
        				Ext.create('AP.store.reportOut.HydrologicalWellReportDeviceListStore');
        			}
                }
    		},'-',deviceCombo,'-', {
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: loginUserLanguageResource.date,
                labelWidth: getLabelWidth(loginUserLanguageResource.date,loginUserLanguage),
                width: (getLabelWidth(loginUserLanguageResource.date,loginUserLanguage)+100),
                format: 'Y-m-d',
                id: 'HydrologicalWellReportStartDate_Id',
                listeners: {
                	select: function (combo, record, index) {
                		
                	}
                }
            },{
                xtype: 'datefield',
                anchor: '100%',
                hidden: false,
                fieldLabel: loginUserLanguageResource.timeTo,
                labelWidth: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage),
                width: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage)+95,
                format: 'Y-m-d ',
                id: 'HydrologicalWellReportEndDate_Id',
                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                		
                	}
                }
            },'-',{
                xtype: 'button',
                text: loginUserLanguageResource.search,
                iconCls: 'search',
                hidden:false,
                handler: function (v, o) {
                	CreateHydrologicalWellReportTable();
                	CreateHydrologicalWellReportCurve();
                }
    		},'-',{
                xtype: 'button',
                text: loginUserLanguageResource.bulkExportData,
                iconCls: 'export',
                handler: function (v, o) {
                	batchExportHydrologicalWellReportData();
                }
            },{
            	id: 'HydrologicalWellReportDeviceListSelectRow_Id',
            	xtype: 'textfield',
                value: -1,
                hidden: true
            }],
            items: [{
            	region: 'west',
            	width: '20%',
            	title: loginUserLanguageResource.deviceList,
            	id: 'HydrologicalWellReportDeviceListPanel_Id',
            	collapsible: true, // 是否可折叠
                collapsed:false,//是否折叠
                split: true, // 竖折叠条
            	layout: "fit"
            },{
            	region: 'center',
            	xtype: 'tabpanel',
            	id:"HydrologicalWellReportTabPanel_Id",
                activeTab: 0,
                border: false,
                tabPosition: 'top',
                items: [{
                	id:'HydrologicalWellReportTabPanel1_id',
                	title:loginUserLanguageResource.fiveMinutes,
                	iconCls: 'check3',
                	layout: 'border',
                    border: false,
                    items:[{
                    	region: 'west',
                    	width: '40%',
                    	title:loginUserLanguageResource.reportData,
                    	collapsible: true, // 是否可折叠
                        collapsed:false,//是否折叠
                        split: true, // 竖折叠条
                    	layout: "fit",
                    	tbar:[{
                            xtype: 'button',
                            text: loginUserLanguageResource.forward,
                            iconCls: 'forward',
                            id:'HydrologicalWellReport1ForwardBtn_Id',
                            handler: function (v, o) {
                            	var str = Ext.getCmp("HydrologicalWellReport1Date_Id").rawValue;
                            	var startDate = new Date(Date.parse(str .replace(/-/g, '/')));
                            	var day=-1;
                            	var value = startDate.getTime();
                            	value += day * (24 * 3600 * 1000);
                            	var endDate = new Date(value);
                            	Ext.getCmp("HydrologicalWellReport1Date_Id").setValue(endDate);
                            	CreateHydrologicalWellReportTable();
                            	CreateHydrologicalWellReportCurve();
                            }
                        },'-',{
                            xtype: 'datefield',
                            anchor: '100%',
                            hidden: false,
                            editable:false,
                            readOnly:true,
                            width: 90,
                            format: 'Y-m-d ',
                            id: 'HydrologicalWellReport1Date_Id',
                            listeners: {
                            	change ( thisField, newValue, oldValue, eOpts )  {
                            		var startDateStr=Ext.getCmp("HydrologicalWellReportStartDate_Id").rawValue;
                            		var endDateStr=Ext.getCmp("HydrologicalWellReportEndDate_Id").rawValue;
                            		var reportDateStr=Ext.getCmp("HydrologicalWellReport1Date_Id").rawValue;
                            		
                            		var startDate = new Date(Date.parse(startDateStr .replace(/-/g, '/'))).getTime();
                            		var endDate = new Date(Date.parse(endDateStr .replace(/-/g, '/'))).getTime();
                            		var reportDate = new Date(Date.parse(reportDateStr .replace(/-/g, '/'))).getTime();
                            		
                            		
                            		if(reportDate>startDate){
                            			Ext.getCmp("HydrologicalWellReport1ForwardBtn_Id").enable();
                            		}else{
                            			Ext.getCmp("HydrologicalWellReport1ForwardBtn_Id").disable();
                            		}
                            		
                            		if(reportDate<endDate){
                            			Ext.getCmp("HydrologicalWellReport1BackwardsBtn_Id").enable();
                            		}else{
                            			Ext.getCmp("HydrologicalWellReport1BackwardsBtn_Id").disable();
                            		}
                            	}
                            }
                        },'-',{
                            xtype: 'button',
                            text: loginUserLanguageResource.backward,
                            id:'HydrologicalWellReport1BackwardsBtn_Id',
                            iconCls: 'backwards',
                            handler: function (v, o) {
                            	var str = Ext.getCmp("HydrologicalWellReport1Date_Id").rawValue;
                            	var startDate = new Date(Date.parse(str .replace(/-/g, '/')));
                            	var day=1;
                            	var value = startDate .getTime();
                            	value += day * (24 * 3600 * 1000);
                            	var endDate = new Date(value);
                            	Ext.getCmp("HydrologicalWellReport1Date_Id").setValue(endDate);
                            	CreateHydrologicalWellReportTable();
                            	CreateHydrologicalWellReportCurve();
                            }
                        }, '->',{
                            xtype: 'button',
                            text: loginUserLanguageResource.exportData,
                            iconCls: 'export',
                            handler: function (v, o) {
                            	ExportHydrologicalWellReportData();
                            }
                        },'-',{
                            xtype: 'button',
                            text: loginUserLanguageResource.save,
                            iconCls: 'save',
                            disabled: loginUserRoleReportEdit!=1,
                            hidden:false,
                            handler: function (v, o) {
                            	hydrologicalWellReportHelper.saveData();
                            }
                        },'-', {
                            id: 'HydrologicalWellReport1TotalCount_Id',
                            xtype: 'component',
                            tpl: loginUserLanguageResource.totalCount + ': {count}',
                            style: 'margin-right:15px'
                        }],
                        html:'<div class="HydrologicalWellReport1Container" style="width:100%;height:100%;"><div class="con" id="HydrologicalWellReport1Div_id"></div></div>',
                        listeners: {
                        	resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        		if(hydrologicalWellReportHelper!=null && hydrologicalWellReportHelper.hot!=undefined){
                        			var newWidth=width;
                            		var newHeight=height-22-1;//减去工具条高度
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		hydrologicalWellReportHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                        	}
                        }
                    },{
                    	region: 'center',
                    	layout: "fit",
                    	title:loginUserLanguageResource.reportCurve,
                    	id:'HydrologicalWellReport1CurvePanel_id',
                        html: '<div id="HydrologicalWellReport1CurveDiv_Id" style="width:100%;height:100%;"></div>',
                        listeners: {
                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                if ($("#HydrologicalWellReport1CurveDiv_Id").highcharts() != undefined) {
                                	highchartsResize("HydrologicalWellReport1CurveDiv_Id");
                                }
                            }
                        }
                    }]
                },{
                	id:'HydrologicalWellReportTabPanel2_id',
                	title:loginUserLanguageResource.oneHour,
                	layout: 'border',
                    border: false,
                    items:[{
                    	region: 'west',
                    	width: '40%',
                    	title:loginUserLanguageResource.reportData,
                    	collapsible: true, // 是否可折叠
                        collapsed:false,//是否折叠
                        split: true, // 竖折叠条
                    	layout: "fit",
                    	tbar:[{
                            xtype: 'button',
                            text: loginUserLanguageResource.forward,
                            iconCls: 'forward',
                            id:'HydrologicalWellReport2ForwardBtn_Id',
                            handler: function (v, o) {
                            	var str = Ext.getCmp("HydrologicalWellReport2Date_Id").rawValue;
                            	var startDate = new Date(Date.parse(str .replace(/-/g, '/')));
                            	var day=-1;
                            	var value = startDate.getTime();
                            	value += day * (24 * 3600 * 1000);
                            	var endDate = new Date(value);
                            	Ext.getCmp("HydrologicalWellReport2Date_Id").setValue(endDate);
                            	CreateHydrologicalWellReportTable();
                            	CreateHydrologicalWellReportCurve();
                            }
                        },'-',{
                            xtype: 'datefield',
                            anchor: '100%',
                            hidden: false,
                            editable:false,
                            readOnly:true,
                            width: 90,
                            format: 'Y-m-d ',
                            id: 'HydrologicalWellReport2Date_Id',
                            listeners: {
                            	change ( thisField, newValue, oldValue, eOpts )  {
                            		var startDateStr=Ext.getCmp("HydrologicalWellReportStartDate_Id").rawValue;
                            		var endDateStr=Ext.getCmp("HydrologicalWellReportEndDate_Id").rawValue;
                            		var reportDateStr=Ext.getCmp("HydrologicalWellReport2Date_Id").rawValue;
                            		
                            		var startDate = new Date(Date.parse(startDateStr .replace(/-/g, '/'))).getTime();
                            		var endDate = new Date(Date.parse(endDateStr .replace(/-/g, '/'))).getTime();
                            		var reportDate = new Date(Date.parse(reportDateStr .replace(/-/g, '/'))).getTime();
                            		
                            		
                            		if(reportDate>startDate){
                            			Ext.getCmp("HydrologicalWellReport2ForwardBtn_Id").enable();
                            		}else{
                            			Ext.getCmp("HydrologicalWellReport2ForwardBtn_Id").disable();
                            		}
                            		
                            		if(reportDate<endDate){
                            			Ext.getCmp("HydrologicalWellReport2BackwardsBtn_Id").enable();
                            		}else{
                            			Ext.getCmp("HydrologicalWellReport2BackwardsBtn_Id").disable();
                            		}
                            	}
                            }
                        },'-',{
                            xtype: 'button',
                            text: loginUserLanguageResource.backward,
                            id:'HydrologicalWellReport2BackwardsBtn_Id',
                            iconCls: 'backwards',
                            handler: function (v, o) {
                            	var str = Ext.getCmp("HydrologicalWellReport2Date_Id").rawValue;
                            	var startDate = new Date(Date.parse(str .replace(/-/g, '/')));
                            	var day=1;
                            	var value = startDate .getTime();
                            	value += day * (24 * 3600 * 1000);
                            	var endDate = new Date(value);
                            	Ext.getCmp("HydrologicalWellReport2Date_Id").setValue(endDate);
                            	CreateHydrologicalWellReportTable();
                            	CreateHydrologicalWellReportCurve();
                            }
                        }, '->',{
                            xtype: 'button',
                            text: loginUserLanguageResource.exportData,
                            iconCls: 'export',
                            handler: function (v, o) {
                            	ExportHydrologicalWellReportData();
                            }
                        },'-',{
                            xtype: 'button',
                            text: loginUserLanguageResource.save,
                            iconCls: 'save',
                            disabled: loginUserRoleReportEdit!=1,
                            hidden:false,
                            handler: function (v, o) {
                            	hydrologicalWellReportHelper.saveData();
                            }
                        },'-', {
                            id: 'HydrologicalWellReport2TotalCount_Id',
                            xtype: 'component',
                            tpl: loginUserLanguageResource.totalCount + ': {count}',
                            style: 'margin-right:15px'
                        }],
                        html:'<div class="HydrologicalWellReport2Container" style="width:100%;height:100%;"><div class="con" id="HydrologicalWellReport2Div_id"></div></div>',
                        listeners: {
                        	resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        		if(hydrologicalWellReportHelper!=null && hydrologicalWellReportHelper.hot!=undefined){
                        			var newWidth=width;
                            		var newHeight=height-22-1;//减去工具条高度
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		hydrologicalWellReportHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                        	}
                        }
                    },{
                    	region: 'center',
                    	layout: "fit",
                    	title:loginUserLanguageResource.reportCurve,
                    	id:'HydrologicalWellReport2CurvePanel_id',
                        html: '<div id="HydrologicalWellReport2CurveDiv_Id" style="width:100%;height:100%;"></div>',
                        listeners: {
                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                if ($("#HydrologicalWellReport2CurveDiv_Id").highcharts() != undefined) {
                                	highchartsResize("HydrologicalWellReport2CurveDiv_Id");
                                }
                            }
                        }
                    }]
                },{
                	id:'HydrologicalWellReportTabPanel3_id',
                	title:loginUserLanguageResource.sixHours,
                	layout: 'border',
                    border: false,
                    items:[{
                    	region: 'west',
                    	width: '40%',
                    	title:loginUserLanguageResource.reportData,
                    	collapsible: true, // 是否可折叠
                        collapsed:false,//是否折叠
                        split: true, // 竖折叠条
                    	layout: "fit",
                    	tbar:['->',{
                            xtype: 'button',
                            text: loginUserLanguageResource.exportData,
                            iconCls: 'export',
                            handler: function (v, o) {
                            	ExportHydrologicalWellReportData();
                            }
                        },'-',{
                            xtype: 'button',
                            text: loginUserLanguageResource.save,
                            iconCls: 'save',
                            disabled: loginUserRoleReportEdit!=1,
                            hidden:false,
                            handler: function (v, o) {
                            	hydrologicalWellReportHelper.saveData();
                            }
                        },'-', {
                            id: 'HydrologicalWellReport3TotalCount_Id',
                            xtype: 'component',
                            tpl: loginUserLanguageResource.totalCount + ': {count}',
                            style: 'margin-right:15px'
                        }],
                        html:'<div class="HydrologicalWellReport3Container" style="width:100%;height:100%;"><div class="con" id="HydrologicalWellReport3Div_id"></div></div>',
                        listeners: {
                        	resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        		if(hydrologicalWellReportHelper!=null && hydrologicalWellReportHelper.hot!=undefined){
                        			var newWidth=width;
                            		var newHeight=height-22-1;//减去工具条高度
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		hydrologicalWellReportHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                        	}
                        }
                    },{
                    	region: 'center',
                    	layout: "fit",
                    	title:loginUserLanguageResource.reportCurve,
                    	id:'HydrologicalWellReport3CurvePanel_id',
                        html: '<div id="HydrologicalWellReport3CurveDiv_Id" style="width:100%;height:100%;"></div>',
                        listeners: {
                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                if ($("#HydrologicalWellReport3CurveDiv_Id").highcharts() != undefined) {
                                	highchartsResize("HydrologicalWellReport3CurveDiv_Id");
                                }
                            }
                        }
                    }]
                },{
                	id:'HydrologicalWellReportTabPanel4_id',
                	title:loginUserLanguageResource.twelveHours,
                	layout: 'border',
                    border: false,
                    items:[{
                    	region: 'west',
                    	width: '40%',
                    	title:loginUserLanguageResource.reportData,
                    	collapsible: true, // 是否可折叠
                        collapsed:false,//是否折叠
                        split: true, // 竖折叠条
                    	layout: "fit",
                    	tbar:['->',{
                            xtype: 'button',
                            text: loginUserLanguageResource.exportData,
                            iconCls: 'export',
                            handler: function (v, o) {
                            	ExportHydrologicalWellReportData();
                            }
                        },'-',{
                            xtype: 'button',
                            text: loginUserLanguageResource.save,
                            iconCls: 'save',
                            disabled: loginUserRoleReportEdit!=1,
                            hidden:false,
                            handler: function (v, o) {
                            	hydrologicalWellReportHelper.saveData();
                            }
                        },'-', {
                            id: 'HydrologicalWellReport4TotalCount_Id',
                            xtype: 'component',
                            tpl: loginUserLanguageResource.totalCount + ': {count}',
                            style: 'margin-right:15px'
                        }],
                        html:'<div class="HydrologicalWellReport4Container" style="width:100%;height:100%;"><div class="con" id="HydrologicalWellReport4Div_id"></div></div>',
                        listeners: {
                        	resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        		if(hydrologicalWellReportHelper!=null && hydrologicalWellReportHelper.hot!=undefined){
                        			var newWidth=width;
                            		var newHeight=height-22-1;//减去工具条高度
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		hydrologicalWellReportHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                        	}
                        }
                    },{
                    	region: 'center',
                    	layout: "fit",
                    	title:loginUserLanguageResource.reportCurve,
                    	id:'HydrologicalWellReport4CurvePanel_id',
                        html: '<div id="HydrologicalWellReport4CurveDiv_Id" style="width:100%;height:100%;"></div>',
                        listeners: {
                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                if ($("#HydrologicalWellReport4CurveDiv_Id").highcharts() != undefined) {
                                	highchartsResize("HydrologicalWellReport4CurveDiv_Id");
                                }
                            }
                        }
                    }]
                },{
                	id:'HydrologicalWellReportTabPanel5_id',
                	title:loginUserLanguageResource.twentyFourHours,
                	layout: 'border',
                    border: false,
                    items:[{
                    	region: 'west',
                    	width: '40%',
                    	title:loginUserLanguageResource.reportData,
                    	collapsible: true, // 是否可折叠
                        collapsed:false,//是否折叠
                        split: true, // 竖折叠条
                    	layout: "fit",
                    	tbar:['->',{
                            xtype: 'button',
                            text: loginUserLanguageResource.exportData,
                            iconCls: 'export',
                            handler: function (v, o) {
                            	ExportHydrologicalWellReportData();
                            }
                        },'-',{
                            xtype: 'button',
                            text: loginUserLanguageResource.save,
                            iconCls: 'save',
                            disabled: loginUserRoleReportEdit!=1,
                            hidden:false,
                            handler: function (v, o) {
                            	hydrologicalWellReportHelper.saveData();
                            }
                        },'-', {
                            id: 'HydrologicalWellReport5TotalCount_Id',
                            xtype: 'component',
                            tpl: loginUserLanguageResource.totalCount + ': {count}',
                            style: 'margin-right:15px'
                        }],
                        html:'<div class="HydrologicalWellReport5Container" style="width:100%;height:100%;"><div class="con" id="HydrologicalWellReport5Div_id"></div></div>',
                        listeners: {
                        	resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        		if(hydrologicalWellReportHelper!=null && hydrologicalWellReportHelper.hot!=undefined){
                        			var newWidth=width;
                            		var newHeight=height-22-1;//减去工具条高度
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		hydrologicalWellReportHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                        	}
                        }
                    },{
                    	region: 'center',
                    	layout: "fit",
                    	title:loginUserLanguageResource.reportCurve,
                    	id:'HydrologicalWellReport5CurvePanel_id',
                        html: '<div id="HydrologicalWellReport5CurveDiv_Id" style="width:100%;height:100%;"></div>',
                        listeners: {
                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                if ($("#HydrologicalWellReport5CurveDiv_Id").highcharts() != undefined) {
                                	highchartsResize("HydrologicalWellReport5CurveDiv_Id");
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
        			tabchange: function (tabPanel, newCard, oldCard, obj) {
        				CreateHydrologicalWellReportTable();
                    	CreateHydrologicalWellReportCurve();
        			}
                }
            }]

        });
        me.callParent(arguments);
    }
});

function createHydrologicalWellReportDeviceListDataColumn(columnInfo) {
    var myArr = columnInfo;

    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var width_ = "";
        var lock_ = "";
        var hidden_ = "";
        if (attr.hidden == true) {
            hidden_ = ",hidden:true";
        }
        if (isNotVal(attr.lock)) {
            //lock_ = ",locked:" + attr.lock;
        }
        if (isNotVal(attr.width)) {
            width_ = ",width:" + attr.width;
        }
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' "+width_;
        if (attr.dataIndex == 'id') {
            myColumns += ",xtype: 'rownumberer',sortable : false,locked:false";
        }else if (attr.dataIndex.toUpperCase()=='commStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceCommStatusColor(value,o,p,e);}";
        }else if (attr.dataIndex.toUpperCase()=='slave'.toUpperCase()) {
            myColumns += ",sortable : false,locked:true,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
        } else if (attr.dataIndex.toUpperCase() == 'acqTime'.toUpperCase()) {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}";
        } else {
            myColumns += hidden_ + lock_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};


function CreateHydrologicalWellReportTable(){
	if(hydrologicalWellReportHelper!=null){
		if(hydrologicalWellReportHelper.hot!=undefined){
			hydrologicalWellReportHelper.hot.destroy();
		}
		hydrologicalWellReportHelper=null;
	}
	
	
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var startDate = Ext.getCmp('HydrologicalWellReportStartDate_Id').rawValue;
    var endDate = Ext.getCmp('HydrologicalWellReportEndDate_Id').rawValue;
    var reportDate = '';
    
    var deviceName='';
    var deviceId=0;
    var calculateType=0;
    var selectRow= Ext.getCmp("HydrologicalWellReportDeviceListSelectRow_Id").getValue();
    if(Ext.getCmp("HydrologicalWellReportDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
    	deviceName=Ext.getCmp("HydrologicalWellReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
    	deviceId=Ext.getCmp("HydrologicalWellReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
    	calculateType=Ext.getCmp("HydrologicalWellReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.calculateType;
    }
    
    var divId='HydrologicalWellReport1Div_id';
    var containerId='HydrologicalWellReport1Container';
    var totalCountId='HydrologicalWellReport1TotalCount_Id';
    var timeType=1;
    
    var tabPanel = Ext.getCmp("HydrologicalWellReportTabPanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=='HydrologicalWellReportTabPanel1_id'){
		divId='HydrologicalWellReport1Div_id';
		containerId='HydrologicalWellReport1Container';
		totalCountId='HydrologicalWellReport1TotalCount_Id';
		timeType=1;
		
		reportDate = Ext.getCmp('HydrologicalWellReport1Date_Id').rawValue;
		
	}else if(activeId=='HydrologicalWellReportTabPanel2_id'){
		divId='HydrologicalWellReport2Div_id';
		containerId='HydrologicalWellReport2Container';
		totalCountId='HydrologicalWellReport2TotalCount_Id';
		timeType=2;
		
		reportDate = Ext.getCmp('HydrologicalWellReport2Date_Id').rawValue;
	}else if(activeId=='HydrologicalWellReportTabPanel3_id'){
		divId='HydrologicalWellReport3Div_id';
		containerId='HydrologicalWellReport3Container';
		totalCountId='HydrologicalWellReport3TotalCount_Id';
		timeType=3;
	}else if(activeId=='HydrologicalWellReportTabPanel4_id'){
		divId='HydrologicalWellReport4Div_id';
		containerId='HydrologicalWellReport4Container';
		totalCountId='HydrologicalWellReport4TotalCount_Id';
		timeType=4;
	}else if(activeId=='HydrologicalWellReportTabPanel5_id'){
		divId='HydrologicalWellReport5Div_id';
		containerId='HydrologicalWellReport5Container';
		totalCountId='HydrologicalWellReport5TotalCount_Id';
		timeType=5;
	}
    
    
    if(Ext.getCmp("HydrologicalWellReportDeviceListPanel_Id")!=undefined){
        Ext.getCmp("HydrologicalWellReportDeviceListPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reportDataMamagerController/getHydrologicalWellReportData',
		success:function(response) {
			if(Ext.getCmp("HydrologicalWellReportDeviceListPanel_Id")!=undefined){
				Ext.getCmp("HydrologicalWellReportDeviceListPanel_Id").getEl().unmask();
			}
			var result =  Ext.JSON.decode(response.responseText);
			
			var startDate=Ext.getCmp('HydrologicalWellReportStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(result.startDate);
            }
            var endDate=Ext.getCmp('HydrologicalWellReportEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(result.endDate);
            }
            
            if(timeType==1){
            	var reportDate = Ext.getCmp('HydrologicalWellReport1Date_Id');
                if(reportDate.rawValue==''||null==reportDate.rawValue){
                	reportDate.setValue(result.reportDate);
                }
            }else if(timeType==2){
            	var reportDate = Ext.getCmp('HydrologicalWellReport2Date_Id');
                if(reportDate.rawValue==''||null==reportDate.rawValue){
                	reportDate.setValue(result.reportDate);
                }
            }
			
			if(result.success){
				if(hydrologicalWellReportHelper==null || hydrologicalWellReportHelper.hot==undefined){
//					if(timeType==1 || timeType==2){
//						result.template.mergeCells.push({
//				            "row": result.template.header.length,
//				            "col": 0,
//				            "rowspan": result.data.length,
//				            "colspan": 1
//				        });
//					}
					hydrologicalWellReportHelper = HydrologicalWellReportHelper.createNew(divId,containerId,result.template,result.data,result.columns);
					hydrologicalWellReportHelper.createTable();
				}
			}else{
				$("#"+divId).html('');
			}
			
			Ext.getCmp(totalCountId).update({count: result.data.length});
		},
		failure:function(){
			if(Ext.getCmp("HydrologicalWellReportDeviceListPanel_Id")!=undefined){
				Ext.getCmp("HydrologicalWellReportDeviceListPanel_Id").getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			orgId: orgId,
			deviceId:deviceId,
			deviceName: deviceName,
			startDate: startDate,
			endDate: endDate,
			reportDate:reportDate,
			timeType: timeType
        }
	});
};


var HydrologicalWellReportHelper = {
	    createNew: function (divId, containerid,templateData,contentData,columns) {
	        var hydrologicalWellReportHelper = {};
	        hydrologicalWellReportHelper.templateData=templateData;
	        hydrologicalWellReportHelper.contentData=contentData;
	        hydrologicalWellReportHelper.columns=columns;
	        hydrologicalWellReportHelper.get_data = {};
	        hydrologicalWellReportHelper.data=[];
	        hydrologicalWellReportHelper.sourceData=[];
	        hydrologicalWellReportHelper.hot = '';
	        hydrologicalWellReportHelper.container = document.getElementById(divId);
	        hydrologicalWellReportHelper.columnCount=0;
	        hydrologicalWellReportHelper.editData={};
	        hydrologicalWellReportHelper.contentUpdateList = [];
	        
	        hydrologicalWellReportHelper.colWidths=[];
	        if(loginUserLanguage=='zh_CN'){
	        	hydrologicalWellReportHelper.colWidths=hydrologicalWellReportHelper.templateData.columnWidths_zh_CN
	        }else if(loginUserLanguage=='en'){
	        	hydrologicalWellReportHelper.colWidths=hydrologicalWellReportHelper.templateData.columnWidths_en
		    }else if(loginUserLanguage=='ru'){
	        	hydrologicalWellReportHelper.colWidths=hydrologicalWellReportHelper.templateData.columnWidths_ru
		    }
	        
	        hydrologicalWellReportHelper.initData=function(){
	        	hydrologicalWellReportHelper.data=[];
	        	for(var i=0;i<hydrologicalWellReportHelper.templateData.header.length;i++){
	        		if(loginUserLanguage=='zh_CN'){
	        			hydrologicalWellReportHelper.templateData.header[i].title=hydrologicalWellReportHelper.templateData.header[i].title_zh_CN;
	        		}else if(loginUserLanguage=='en'){
	        			hydrologicalWellReportHelper.templateData.header[i].title=hydrologicalWellReportHelper.templateData.header[i].title_en;
	        		}else if(loginUserLanguage=='ru'){
	        			hydrologicalWellReportHelper.templateData.header[i].title=hydrologicalWellReportHelper.templateData.header[i].title_ru;
	        		}
	        		
	        		hydrologicalWellReportHelper.templateData.header[i].title.push('');
	        		hydrologicalWellReportHelper.columnCount=hydrologicalWellReportHelper.templateData.header[i].title.length;
	        		
	        		var valueArr=[];
	        		var sourceValueArr=[];
	        		for(var j=0;j<hydrologicalWellReportHelper.templateData.header[i].title.length;j++){
	        			valueArr.push(hydrologicalWellReportHelper.templateData.header[i].title[j]);
	        			sourceValueArr.push(hydrologicalWellReportHelper.templateData.header[i].title[j]);
	        		}
	        		
	        		hydrologicalWellReportHelper.data.push(valueArr);
	        		hydrologicalWellReportHelper.sourceData.push(sourceValueArr);
		        }
	        	for(var i=0;i<hydrologicalWellReportHelper.contentData.length;i++){
	        		var valueArr=[];
	        		var sourceValueArr=[];
	        		for(var j=0;j<hydrologicalWellReportHelper.contentData[i].length;j++){
	        			valueArr.push(hydrologicalWellReportHelper.contentData[i][j]);
	        			sourceValueArr.push(hydrologicalWellReportHelper.contentData[i][j]);
	        		}
	        		
	        		hydrologicalWellReportHelper.data.push(valueArr);
		        	hydrologicalWellReportHelper.sourceData.push(sourceValueArr);
		        }
	        	for(var i=hydrologicalWellReportHelper.templateData.header.length;i<hydrologicalWellReportHelper.data.length;i++){
	        		for(var j=0;j<hydrologicalWellReportHelper.data[i].length;j++){
	        			var editable=false
	        			for(var k=0;k<hydrologicalWellReportHelper.templateData.editable.length;k++){
	        				if( i>=hydrologicalWellReportHelper.templateData.editable[k].startRow 
	                				&& i<=hydrologicalWellReportHelper.templateData.editable[k].endRow
	                				&& j>=hydrologicalWellReportHelper.templateData.editable[k].startColumn 
	                				&& j<=hydrologicalWellReportHelper.templateData.editable[k].endColumn
	                		){
	        					editable=true;
	        					break;
	                		}
	        			}
	        			
	        			var value=hydrologicalWellReportHelper.data[i][j];
		                if((!editable)&&value.length>12){
		                	value=value.substring(0, 11)+"...";
		                	hydrologicalWellReportHelper.data[i][j]=value;
		                }
	        		}
	        	}
	        }
	        
	        hydrologicalWellReportHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(hydrologicalWellReportHelper!=null && hydrologicalWellReportHelper.hot!=null){
	        		for(var i=0;i<hydrologicalWellReportHelper.templateData.header.length;i++){
		        		if(row==i){
		        			if(isNotVal(hydrologicalWellReportHelper.templateData.header[i].tdStyle)){
		        				if(isNotVal(hydrologicalWellReportHelper.templateData.header[i].tdStyle.fontWeight)){
		        					td.style.fontWeight = hydrologicalWellReportHelper.templateData.header[i].tdStyle.fontWeight;
		        				}
		        				if(isNotVal(hydrologicalWellReportHelper.templateData.header[i].tdStyle.fontSize)){
		        					td.style.fontSize = hydrologicalWellReportHelper.templateData.header[i].tdStyle.fontSize;
		        				}
		        				if(isNotVal(hydrologicalWellReportHelper.templateData.header[i].tdStyle.fontFamily)){
//		        					td.style.fontFamily = hydrologicalWellReportHelper.templateData.header[i].tdStyle.fontFamily;
		        				}
		        				if(isNotVal(hydrologicalWellReportHelper.templateData.header[i].tdStyle.height)){
		        					td.style.height = hydrologicalWellReportHelper.templateData.header[i].tdStyle.height;
		        				}
		        				if(isNotVal(hydrologicalWellReportHelper.templateData.header[i].tdStyle.color)){
		        					td.style.color = hydrologicalWellReportHelper.templateData.header[i].tdStyle.color;
		        				}
		        				if(isNotVal(hydrologicalWellReportHelper.templateData.header[i].tdStyle.backgroundColor)){
		        					td.style.backgroundColor = hydrologicalWellReportHelper.templateData.header[i].tdStyle.backgroundColor;
		        				}
		        				if(isNotVal(hydrologicalWellReportHelper.templateData.header[i].tdStyle.textAlign)){
		        					td.style.textAlign = hydrologicalWellReportHelper.templateData.header[i].tdStyle.textAlign;
		        				}
		        			}
		        			break;
		        		}
		        	}
	        		if(row>=hydrologicalWellReportHelper.templateData.header.length){
	        			td.style.whiteSpace='nowrap'; //文本不换行
		            	td.style.overflow='hidden';//超出部分隐藏
		            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        		}
	        		
	        	}
	        }
	        
	        hydrologicalWellReportHelper.addEditableColor = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.color='#ff0000'; 
	             td.style.whiteSpace='nowrap'; //文本不换行
	             td.style.overflow='hidden';//超出部分隐藏
	             td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        hydrologicalWellReportHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        hydrologicalWellReportHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        hydrologicalWellReportHelper.createTable = function () {
	            hydrologicalWellReportHelper.container.innerHTML = "";
	            hydrologicalWellReportHelper.hot = new Handsontable(hydrologicalWellReportHelper.container, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: hydrologicalWellReportHelper.data,
	            	hiddenColumns: {
	                    columns: [hydrologicalWellReportHelper.columnCount-1],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
//	            	columns:hydrologicalWellReportHelper.columns,
	            	fixedRowsTop:hydrologicalWellReportHelper.templateData.fixedRowsTop, 
	                fixedRowsBottom: hydrologicalWellReportHelper.templateData.fixedRowsBottom,
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: hydrologicalWellReportHelper.templateData.rowHeights,
					colWidths: hydrologicalWellReportHelper.colWidths,
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
	                mergeCells: hydrologicalWellReportHelper.templateData.mergeCells,
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
	                    cellProperties.renderer = hydrologicalWellReportHelper.addStyle;
	                    cellProperties.readOnly = true;
	                    if(hydrologicalWellReportHelper.templateData.editable!=null && hydrologicalWellReportHelper.templateData.editable.length>0){
	                    	for(var i=0;i<hydrologicalWellReportHelper.templateData.editable.length;i++){
	                    		if( row>=hydrologicalWellReportHelper.templateData.editable[i].startRow 
	                    				&& row<=hydrologicalWellReportHelper.templateData.editable[i].endRow
	                    				&& col>=hydrologicalWellReportHelper.templateData.editable[i].startColumn 
	                    				&& col<=hydrologicalWellReportHelper.templateData.editable[i].endColumn
	                    		){
	                    			cellProperties.readOnly = false;
	                    			cellProperties.renderer = hydrologicalWellReportHelper.addEditableColor;
	                    		}
	                    	}
	                    }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 && hydrologicalWellReportHelper!=null&&hydrologicalWellReportHelper.hot!=''&&hydrologicalWellReportHelper.hot!=undefined && hydrologicalWellReportHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=hydrologicalWellReportHelper.sourceData[coords.row][coords.col];
	                		if(coords.row>=hydrologicalWellReportHelper.templateData.header.length){
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
	                	if(hydrologicalWellReportHelper!=null&&hydrologicalWellReportHelper.hot!=''&&hydrologicalWellReportHelper.hot!=undefined && hydrologicalWellReportHelper.hot.getDataAtCell!=undefined){
	                		var value=hydrologicalWellReportHelper.sourceData[coords.row][coords.col];
	                		if(coords.row>=hydrologicalWellReportHelper.templateData.header.length){
//	                			TD.outerHTML='<td class="htDimmed">'+TD.innerText+'</td>';
	                		}
	                	}
	                },
	                afterChange: function (changes, source) {
	                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
	                    if (hydrologicalWellReportHelper!=null && hydrologicalWellReportHelper.hot!=undefined && hydrologicalWellReportHelper.hot!='' && changes != null) {
	                        for (var i = 0; i < changes.length; i++) {
	                            var params = [];
	                            var index = changes[i][0]; //行号码
	                            var rowdata = hydrologicalWellReportHelper.hot.getDataAtRow(index);
	                            
	                            var editCellInfo={};
	                            var editRow=changes[i][0];
	                            var editCol=changes[i][1];
	                            var column=hydrologicalWellReportHelper.columns[editCol];
	                            
	                            editCellInfo.editRow=editRow;
	                            editCellInfo.editCol=editCol;
	                            editCellInfo.column=column;
	                            editCellInfo.recordId=rowdata[rowdata.length-1]
	                            editCellInfo.oldValue=changes[i][2];
	                            editCellInfo.newValue=changes[i][3];
	                            editCellInfo.header=false;
	                            if(editCellInfo.editRow<hydrologicalWellReportHelper.templateData.header.length){
	                            	editCellInfo.header=true;
	                            }
	                            
	                            var isExit=false;
	                            for(var j=0;j<hydrologicalWellReportHelper.contentUpdateList.length;j++){
	                            	if(editCellInfo.editRow==hydrologicalWellReportHelper.contentUpdateList[j].editRow && editCellInfo.editCol==hydrologicalWellReportHelper.contentUpdateList[j].editCol){
	                            		hydrologicalWellReportHelper.contentUpdateList[j].newValue=editCellInfo.newValue;
	                            		isExit=true;
	                            		break;
	                            	}
	                            }
	                            if(!isExit){
	                            	hydrologicalWellReportHelper.contentUpdateList.push(editCellInfo);
	                            }
	                        }
	                    }
	                }
	            });
	        }
	        hydrologicalWellReportHelper.getData = function (data) {
	        	
	        }
	        
	        hydrologicalWellReportHelper.saveData = function () {
	        	if(hydrologicalWellReportHelper.contentUpdateList.length>0){
	        		hydrologicalWellReportHelper.editData.contentUpdateList=hydrologicalWellReportHelper.contentUpdateList;
	        		var deviceName='';
	        	    var deviceId=0;
	        	    var selectRow= Ext.getCmp("HydrologicalWellReportDeviceListSelectRow_Id").getValue();
	        	    if(Ext.getCmp("HydrologicalWellReportDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
	        	    	deviceName=Ext.getCmp("HydrologicalWellReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
	        	    	deviceId=Ext.getCmp("HydrologicalWellReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
	        	    }
//	        		alert(JSON.stringify(hydrologicalWellReportHelper.editData));
	        		Ext.Ajax.request({
	                    method: 'POST',
	                    url: context + '/reportDataMamagerController/saveHydrologicalWellReportData',
	                    success: function (response) {
	                        rdata = Ext.JSON.decode(response.responseText);
	                        if (rdata.success) {
	                        	Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.saveSuccessfully);
	                        	hydrologicalWellReportHelper.clearContainer();
	                        	CreateHydrologicalWellReportTable();
	                        	CreateHydrologicalWellReportCurve();
	                        } else {
	                        	hydrologicalWellReportHelper.clearContainer();
	                        	Ext.MessageBox.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
	                        }
	                    },
	                    failure: function () {
	                        Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.requestFailure);
	                    },
	                    params: {
	                    	deviceId:deviceId,
	                    	deviceName:deviceName,
	                    	data: JSON.stringify(hydrologicalWellReportHelper.editData)
	                    }
	                });
	        	}else{
	        		Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.noDataChange);
	        	}
	        }
	        hydrologicalWellReportHelper.clearContainer = function () {
	        	hydrologicalWellReportHelper.editData={};
            	hydrologicalWellReportHelper.contentUpdateList=[];
	        }

	        var init = function () {
	        	hydrologicalWellReportHelper.initData();
	        }

	        init();
	        return hydrologicalWellReportHelper;
	    }
	};


function CreateHydrologicalWellReportCurve(){
    var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var startDate = Ext.getCmp('HydrologicalWellReportStartDate_Id').rawValue;
    var endDate = Ext.getCmp('HydrologicalWellReportEndDate_Id').rawValue;
    var reportDate = '';
    
    var deviceName='';
    var deviceId=0;
    var calculateType=0;
    var selectRow= Ext.getCmp("HydrologicalWellReportDeviceListSelectRow_Id").getValue();
    if(Ext.getCmp("HydrologicalWellReportDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
    	deviceName=Ext.getCmp("HydrologicalWellReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
    	deviceId=Ext.getCmp("HydrologicalWellReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
    	calculateType=Ext.getCmp("HydrologicalWellReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.calculateType;
    }
    
    var divId='HydrologicalWellReport1CurveDiv_Id';
    var panelId='HydrologicalWellReport1CurvePanel_id';
    
    var timeType=1;
    
    var tabPanel = Ext.getCmp("HydrologicalWellReportTabPanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=='HydrologicalWellReportTabPanel1_id'){
		divId='HydrologicalWellReport1CurveDiv_Id';
		panelId='HydrologicalWellReport1CurvePanel_id';
		timeType=1;
		reportDate = Ext.getCmp('HydrologicalWellReport1Date_Id').rawValue;
	}else if(activeId=='HydrologicalWellReportTabPanel2_id'){
		divId='HydrologicalWellReport2CurveDiv_Id';
		panelId='HydrologicalWellReport2CurvePanel_id';
		timeType=2;
		reportDate = Ext.getCmp('HydrologicalWellReport2Date_Id').rawValue;
	}else if(activeId=='HydrologicalWellReportTabPanel3_id'){
		divId='HydrologicalWellReport3CurveDiv_Id';
		panelId='HydrologicalWellReport3CurvePanel_id';
		timeType=3;
	}else if(activeId=='HydrologicalWellReportTabPanel4_id'){
		divId='HydrologicalWellReport4CurveDiv_Id';
		panelId='HydrologicalWellReport4CurvePanel_id';
		timeType=4;
	}else if(activeId=='HydrologicalWellReportTabPanel5_id'){
		divId='HydrologicalWellReport5CurveDiv_Id';
		panelId='HydrologicalWellReport5CurvePanel_id';
		timeType=5;
	}
    
    
    if(Ext.getCmp(panelId)!=undefined){
        Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reportDataMamagerController/getHydrologicalWellReportCurveData',
		success:function(response) {
			if(Ext.getCmp(panelId)!=undefined){
				Ext.getCmp(panelId).getEl().unmask();
			}
			var result =  Ext.JSON.decode(response.responseText);
			
			var startDate=Ext.getCmp('HydrologicalWellReportStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(result.startDate);
            }
            var endDate=Ext.getCmp('HydrologicalWellReportEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(result.endDate);
            }
            
            if(timeType==1){
            	var reportDate = Ext.getCmp('HydrologicalWellReport1Date_Id');
                if(reportDate.rawValue==''||null==reportDate.rawValue){
                	reportDate.setValue(result.reportDate);
                }
            }else if(timeType==2){
            	var reportDate = Ext.getCmp('HydrologicalWellReport2Date_Id');
                if(reportDate.rawValue==''||null==reportDate.rawValue){
                	reportDate.setValue(result.reportDate);
                }
            }
            
		    var data = result.list;
		    var graphicSet=result.graphicSet;
		    var timeFormat='%H:%M';
		    
		    var defaultColors=["#7cb5ec", "#434348", "#90ed7d", "#f7a35c", "#8085e9", "#f15c80", "#e4d354", "#2b908f", "#f45b5b", "#91e8e1"];
		    var tickInterval = 1;
		    tickInterval = Math.floor(data.length / 10) + 1;
		    if(tickInterval<100){
		    	tickInterval=100;
		    }
		    var title = result.deviceName+ "-" + loginUserLanguageResource.hourlyReportCurve+ "-"+result.reportDate;
		    
		    if(timeType==1 || timeType==2){
		    	title = result.deviceName+ "-" + loginUserLanguageResource.hydrologicalWellReportCurveTitle+ "-"+result.reportDate;
		    }else{
		    	title = result.deviceName+ "-" + loginUserLanguageResource.hydrologicalWellReportCurveTitle+ "-"+result.startDate+"~"+result.endDate;
		    }
		    
		    
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
		        if(JSON.stringify(graphicSet) != "{}" && isNotVal(graphicSet.HydrologicalWellReport) ){
			    	for(var j=0;j<graphicSet.HydrologicalWellReport.length;j++){
			    		if(graphicSet.HydrologicalWellReport[j].itemCode!=undefined && graphicSet.HydrologicalWellReport[j].itemCode.toUpperCase()==result.curveItemCodes[i].toUpperCase()){
			    			if(isNotVal(graphicSet.HydrologicalWellReport[j].yAxisMaxValue)){
					    		maxValue=parseFloat(graphicSet.HydrologicalWellReport[j].yAxisMaxValue);
					    	}
					    	if(isNotVal(graphicSet.HydrologicalWellReport[j].yAxisMinValue)){
					    		minValue=parseFloat(graphicSet.HydrologicalWellReport[j].yAxisMinValue);
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
		                opposite:opposite
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
		    initHydrologicalWellReportCurveChartFn(series, tickInterval, divId, title, '', '', yAxis, color_all,true,timeFormat);
		},
		failure:function(){
			if(Ext.getCmp(panelId)!=undefined){
				Ext.getCmp(panelId).getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			orgId: orgId,
			deviceId:deviceId,
			deviceName: deviceName,
			startDate: startDate,
			endDate: endDate,
			reportDate: reportDate,
			timeType:timeType
        }
	});
};

function initHydrologicalWellReportCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, yAxis, color,legend,timeFormat) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
		var dafaultMenuItem = Highcharts.getOptions().exporting.buttons.contextButton.menuItems;
		Highcharts.setOptions({
	        global: {
	            useUTC: false
	        }
	    });

	    var mychart = new Highcharts.Chart({
	        chart: {
	            renderTo: divId,
	            type: 'spline',
	            shadow: true,
	            borderWidth: 0,
	            zoomType: 'xy'
	        },
	        credits: {
	            enabled: false
	        },
	        title: {
	            text: title
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
	                    return Highcharts.dateFormat(timeFormat, this.value);
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
	            sourceWidth: $("#"+divId)[0].offsetWidth,
	            sourceHeight: $("#"+divId)[0].offsetHeight,
	            buttons: {
	            	contextButton: {
	            		menuItems:[dafaultMenuItem[0],dafaultMenuItem[1],dafaultMenuItem[2],dafaultMenuItem[3],dafaultMenuItem[4],dafaultMenuItem[5],dafaultMenuItem[6],dafaultMenuItem[7],
	            			,dafaultMenuItem[2],{
	            				text: loginUserLanguageResource.diagramSet,
	            				onclick: function() {
	            					var window = Ext.create("AP.view.reportOut.ReportCurveSetWindow", {
	                                    title: loginUserLanguageResource.reportDiagramSet
	                                });
	                                window.show();
	            				}
	            			}]
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
	            borderWidth: 0
	        },
	        series: series
	    });
	}
	
};

function ExportHydrologicalWellReportData(){
	var timestamp=new Date().getTime();
	var key='ExportHydrologicalWellReportData'+timestamp;
	
	
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var startDate = Ext.getCmp('HydrologicalWellReportStartDate_Id').rawValue;
    var endDate = Ext.getCmp('HydrologicalWellReportEndDate_Id').rawValue;
    var reportDate = '';
    
    var deviceName='';
    var deviceId=0;
    var selectRow= Ext.getCmp("HydrologicalWellReportDeviceListSelectRow_Id").getValue();
    if(Ext.getCmp("HydrologicalWellReportDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
    	deviceName=Ext.getCmp("HydrologicalWellReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
    	deviceId=Ext.getCmp("HydrologicalWellReportDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
    }
    var timeType=1;
    var panelId='HydrologicalWellReportTabPanel1_id';
    var tabPanel = Ext.getCmp("HydrologicalWellReportTabPanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=='HydrologicalWellReportTabPanel1_id'){
		timeType=1;
		reportDate = Ext.getCmp('HydrologicalWellReport1Date_Id').rawValue;
		panelId='HydrologicalWellReportTabPanel1_id';
	}else if(activeId=='HydrologicalWellReportTabPanel2_id'){
		timeType=2;
		reportDate = Ext.getCmp('HydrologicalWellReport2Date_Id').rawValue;
		panelId='HydrologicalWellReportTabPanel2_id';
	}else if(activeId=='HydrologicalWellReportTabPanel3_id'){
		timeType=3;
		panelId='HydrologicalWellReportTabPanel3_id';
	}else if(activeId=='HydrologicalWellReportTabPanel4_id'){
		timeType=4;
		panelId='HydrologicalWellReportTabPanel4_id';
	}else if(activeId=='HydrologicalWellReportTabPanel5_id'){
		timeType=5;
		panelId='HydrologicalWellReportTabPanel5_id';
	}
	
	

	var url=context + '/reportDataMamagerController/exportHydrologicalWellReportData?timeType='+timeType
	+'&deviceName='+URLencode(URLencode(deviceName))
	+'&deviceId='+deviceId
	+'&startDate='+startDate
	+'&endDate='+endDate
	+'&reportDate='+reportDate
	+'&key='+key;
	exportDataMask(key,panelId,loginUserLanguageResource.loading);
	document.location.href = url;
}

function batchExportHydrologicalWellReportData(){
	var timestamp=new Date().getTime();
	var key='batchExportHydrologicalWellReportData'+timestamp;
	
	
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var startDate = Ext.getCmp('HydrologicalWellReportStartDate_Id').rawValue;
    var endDate = Ext.getCmp('HydrologicalWellReportEndDate_Id').rawValue;
    var reportDate = '';
    
    var deviceName=Ext.getCmp('HydrologicalWellReportPanelDeviceListCombo_Id').getValue();
   
    var timeType=1;
    var panelId='HydrologicalWellReportTabPanel1_id';
    var tabPanel = Ext.getCmp("HydrologicalWellReportTabPanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=='HydrologicalWellReportTabPanel1_id'){
		timeType=1;
		reportDate = Ext.getCmp('HydrologicalWellReport1Date_Id').rawValue;
		panelId='HydrologicalWellReportTabPanel1_id';
	}else if(activeId=='HydrologicalWellReportTabPanel2_id'){
		timeType=2;
		reportDate = Ext.getCmp('HydrologicalWellReport2Date_Id').rawValue;
		panelId='HydrologicalWellReportTabPanel2_id';
	}else if(activeId=='HydrologicalWellReportTabPanel3_id'){
		timeType=3;
		panelId='HydrologicalWellReportTabPanel3_id';
	}else if(activeId=='HydrologicalWellReportTabPanel4_id'){
		timeType=4;
		panelId='HydrologicalWellReportTabPanel4_id';
	}else if(activeId=='HydrologicalWellReportTabPanel5_id'){
		timeType=5;
		panelId='HydrologicalWellReportTabPanel5_id';
	}
	
	

	var url=context + '/reportDataMamagerController/batchExportHydrologicalWellReportData?timeType='+timeType
	+'&deviceName='+URLencode(URLencode(deviceName))
	+'&startDate='+startDate
	+'&endDate='+endDate
	+'&reportDate='+reportDate
	+'&orgId='+orgId
	+'&key='+key;
	exportDataMask(key,panelId,loginUserLanguageResource.loading);
	document.location.href = url;
}