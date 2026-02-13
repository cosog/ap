//var singleWellRangeReportTemplateHandsontableHelper = null;
//var singleWellRangeReportTemplateContentHandsontableHelper = null;
//var reportUnitPropertiesHandsontableHelper = null;
//
//var productionReportTemplateHandsontableHelper = null;
//var productionReportTemplateContentHandsontableHelper = null;
//
//var singleWellDailyReportTemplateHandsontableHelper = null;
//var singleWellDailyReportTemplateContentHandsontableHelper = null;

Ext.define('AP.view.acquisitionUnit.ModbusProtocolReportUnitConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolReportUnitConfigInfoView',
    layout: "fit",
    id: 'modbusProtocolReportUnitConfigInfoViewId',
    border: false,
    initComponent: function () {
        var me = this;
        var ModbusProtocolReportUnitClasses0ConfigInfoView=Ext.create('AP.view.acquisitionUnit.ModbusProtocolReportUnitClasses0ConfigInfoView');
//        var ModbusProtocolReportUnitClasses1ConfigInfoView=Ext.create('AP.view.acquisitionUnit.ModbusProtocolReportUnitClasses1ConfigInfoView');
        Ext.apply(me, {
            items: [{
                tbar: [{
                    id: 'ModbusProtocolReportUnitConfigSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    id: 'ReportUnitTreeSelectUnitId_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    id: 'ReportUnitTreeSelectUnitClasses_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                },{
                    id: 'AddNewReportUnitName_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    xtype: 'button',
                    text: loginUserLanguageResource.refresh,
                    iconCls: 'note-refresh',
                    hidden: false,
                    handler: function (v, o) {
                        var treeGridPanel = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id");
                        if (isNotVal(treeGridPanel)) {
                            treeGridPanel.getStore().load();
                        } else {
                            Ext.create('AP.store.acquisitionUnit.ModbusProtocolReportUnitTreeInfoStore');
                        }
                    }
        		}, '->', {
                    xtype: 'button',
                    text: loginUserLanguageResource.addReportUnit,
                    disabled: loginUserProtocolConfigModuleRight.editFlag != 1,
                    iconCls: 'add',
                    handler: function (v, o) {
                        addReportUnitInfo();
                    }
        		}, "-", {
                    xtype: 'button',
                    text: loginUserLanguageResource.save,
                    disabled: loginUserProtocolConfigModuleRight.editFlag != 1,
                    iconCls: 'save',
                    handler: function (v, o) {
                        SaveReportUnitData();
                    }
                },"-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.exportData,
        			iconCls: 'export',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.acquisitionUnit.ExportProtocolReportUnitWindow");
                        window.show();
        			}
                },"-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.importData,
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'import',
        			handler: function (v, o) {
        				var selectedDeviceTypeName="";
        				var selectedDeviceTypeId="";
        				var tabTreeStore = Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getStore();
        				var count=tabTreeStore.getCount();
        				var tabTreeSelection = Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getSelectionModel().getSelection();
        				var rec=null;
        				if (tabTreeSelection.length > 0) {
        					rec=tabTreeSelection[0];
        					selectedDeviceTypeName=foreachAndSearchTabAbsolutePath(tabTreeStore.data.items,tabTreeSelection[0].data.deviceTypeId);
        					selectedDeviceTypeId=tabTreeSelection[0].data.deviceTypeId;
        				} else {
        					if(count>0){
        						rec=orgTreeStore.getAt(0);
        						selectedDeviceTypeName=orgTreeStore.getAt(0).data.text;
        						selectedDeviceTypeId=orgTreeStore.getAt(0).data.deviceTypeId;
        					}
        				}
        				var window = Ext.create("AP.view.acquisitionUnit.ImportReportUnitWindow");
                        window.show();
        				Ext.getCmp("ImportReportUnitWinTabLabel_Id").setHtml("单元将导入到【<font color=red>"+selectedDeviceTypeName+"</font>】标签下,"+loginUserLanguageResource.pleaseConfirm+"<br/>&nbsp;");
//        			    Ext.getCmp("ImportAlarmUnitWinTabLabel_Id").show();
        			    
        			    Ext.getCmp('ImportReportUnitWinDeviceType_Id').setValue(selectedDeviceTypeId);
        				
        			}
                }],
                layout: "border",
                items: [{
                	region: 'west',
                    width: '20%',
                    title: loginUserLanguageResource.reportUnitConfig,
                    layout: 'fit',
                    id: "ModbusProtocolReportUnitConfigPanel_Id",
                    border: false,
                	collapsible: true,
                    split: true
                },{
                	region: 'center',
                	border: false,
                	header: false,
                	xtype: 'tabpanel',
                    id:"ReportUnitConfigRightTabPanel_Id",
                    activeTab: 1,
                    tabBar:{
                		items: [{
                            xtype: 'tbfill'
                        },{
                        	xtype: 'label',
                        	id: 'ReportUnitConfigInformationLabel_Id',
                        	hidden:true,
                        	html: ''
                        },{
                        	xtype: 'label',
                        	hidden:false,
                        	html: '&nbsp;'
                        }]
                	},
                    items:[{
                    	id:"ReportUnitPropertiesConfigRightTabPanel_Id",
                    	title:loginUserLanguageResource.properties,
                    	layout: 'fit',
                        html: '<div class="ModbusProtocolReportUnitPropertiesTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolReportUnitPropertiesTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                if (reportUnitPropertiesHandsontableHelper != null && reportUnitPropertiesHandsontableHelper.hot != null && reportUnitPropertiesHandsontableHelper.hot != undefined) {
                                    var newWidth = width;
                                    var newHeight = height;
                                    var header = thisPanel.getHeader();
                                    if (header) {
                                        newHeight = newHeight - header.lastBox.height - 2;
                                    }
                                    reportUnitPropertiesHandsontableHelper.hot.updateSettings({
                                        width: newWidth,
                                        height: newHeight
                                    });
                                }
                            }
                        }
                    },{
                    	id:"ModbusProtocolReportUnitReportTemplateTabPanel_Id",
                    	border: false,
                        title:loginUserLanguageResource.config,
                        iconCls: 'check3',
                        layout: "fit"
//                        items: [ModbusProtocolReportUnitClasses0ConfigInfoView]
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
            				var record= Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getSelectionModel().getSelection()[0];
                        	if(newCard.id=="ReportUnitPropertiesConfigRightTabPanel_Id"){
                        		CreateProtocolReportUnitPropertiesInfoTable(record.data);
                        	}else if(newCard.id=="ModbusProtocolReportUnitReportTemplateTabPanel_Id"){
                        		if(record.data.classes == 1){
                        			var tabPanel = Ext.getCmp("ReportUnitConfigRightTabPanel_Id");
                            		var showInfo=tabPanel.getActiveTab().title;
                            		if(isNotVal(record.data.text)){
                            			showInfo="【<font color=red>"+record.data.text+"</font>】"+showInfo+"&nbsp;"
                            		}
                            		Ext.getCmp("ReportUnitConfigInformationLabel_Id").setHtml(showInfo);
                            	    Ext.getCmp("ReportUnitConfigInformationLabel_Id").show();
                        		}
                        		
                        		var selectedUnitCode='';
                            	var selectedUnitId=0;
                            	if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			selectedUnitCode=record.data.children[0].code;
                            			selectedUnitId=record.data.children[0].id;
                            		}
                            	}else if(record.data.classes==1){
                            		selectedUnitCode=record.data.code;
                            		selectedUnitId=record.data.id;
                            	}
                            	
                            	var tabPanel = Ext.getCmp("ModbusProtocolReportUnitReportConfigPanel_Id");
                				var activeId = tabPanel.getActiveTab().id;
                				if(activeId=="ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id"){
                					var singleWellReportActiveId=Ext.getCmp("ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id").getActiveTab().id;
                					if(singleWellReportActiveId=='ModbusProtocolReportUnitSingleWellDailyReportTemplatePanel_Id'){
                						var ReportUnitSingleWellDailyReportTemplateListGridPanel=Ext.getCmp("ReportUnitSingleWellDailyReportTemplateListGridPanel_Id");
                                    	if (isNotVal(ReportUnitSingleWellDailyReportTemplateListGridPanel)) {
                                    		ReportUnitSingleWellDailyReportTemplateListGridPanel.getStore().load();
                                    	}else{
                                    		Ext.create('AP.store.acquisitionUnit.ModbusProtocolSingleWellDailyReportTemplateStore')
                                    	}
                                    	
                					}else if(singleWellReportActiveId=='ModbusProtocolReportUnitSingleWellRangeReportTemplatePanel_Id'){
                						var ReportUnitSingleWellRangeReportTemplateListGridPanel=Ext.getCmp("ReportUnitSingleWellRangeReportTemplateListGridPanel_Id");
                                    	if (isNotVal(ReportUnitSingleWellRangeReportTemplateListGridPanel)) {
                                    		ReportUnitSingleWellRangeReportTemplateListGridPanel.getStore().load();
                                    	}else{
                                    		Ext.create('AP.store.acquisitionUnit.ModbusProtocolSingleWellRangeReportTemplateStore')
                                    	}
                					}
                				}else if(activeId=="ModbusProtocolReportUnitProductionReportTemplatePanel_Id"){
                					var ReportUnitProductionReportTemplateListGridPanel=Ext.getCmp("ReportUnitProductionReportTemplateListGridPanel_Id");
                                	if (isNotVal(ReportUnitProductionReportTemplateListGridPanel)) {
                                		ReportUnitProductionReportTemplateListGridPanel.getStore().load();
                                	}else{
                                		Ext.create('AP.store.acquisitionUnit.ModbusProtocolProductionReportTemplateStore')
                                	}
                				}
                        	}
                        }
                    }
                }]
            }]
        });
        this.callParent(arguments);
    }
});

function CreateSingleWellRangeReportTemplateInfoTable(name, calculateType, code) {
    Ext.getCmp("ReportUnitSingleWellRangeReportTemplateTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/acquisitionUnitManagerController/getReportTemplateData',
        success: function (response) {
            Ext.getCmp("ReportUnitSingleWellRangeReportTemplateTableInfoPanel_Id").getEl().unmask();
            Ext.getCmp("ReportUnitSingleWellRangeReportTemplateTableInfoPanel_Id").setTitle(name+'/'+loginUserLanguageResource.deviceDailyReportTemplate);
            var result = Ext.JSON.decode(response.responseText);

            if (singleWellRangeReportTemplateHandsontableHelper != null) {
                if (singleWellRangeReportTemplateHandsontableHelper.hot != undefined) {
                    singleWellRangeReportTemplateHandsontableHelper.hot.destroy();
                }
                singleWellRangeReportTemplateHandsontableHelper = null;
            }

            if (singleWellRangeReportTemplateHandsontableHelper == null || singleWellRangeReportTemplateHandsontableHelper.hot == undefined) {
                singleWellRangeReportTemplateHandsontableHelper = SingleWellRangeReportTemplateHandsontableHelper.createNew("ReportUnitSingleWellRangeReportTemplateTableInfoDiv_id", "ReportUnitSingleWellRangeReportTemplateTableInfoContainer", result);
                singleWellRangeReportTemplateHandsontableHelper.createTable();
            }
        },
        failure: function () {
            Ext.getCmp("ReportUnitSingleWellRangeReportTemplateTableInfoPanel_Id").getEl().unmask();
            Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
        },
        params: {
            reportType: 0,
            calculateType: calculateType,
            code: code
        }
    });
}

var SingleWellRangeReportTemplateHandsontableHelper = {
    createNew: function (divid, containerid, templateData) {
        var singleWellRangeReportTemplateHandsontableHelper = {};
        singleWellRangeReportTemplateHandsontableHelper.templateData = templateData;
        singleWellRangeReportTemplateHandsontableHelper.get_data = {};
        singleWellRangeReportTemplateHandsontableHelper.data = [];
        singleWellRangeReportTemplateHandsontableHelper.hot = '';
        singleWellRangeReportTemplateHandsontableHelper.container = document.getElementById(divid);

        singleWellRangeReportTemplateHandsontableHelper.colWidths=[];
        if(loginUserLanguage=='zh_CN'){
        	singleWellRangeReportTemplateHandsontableHelper.colWidths=singleWellRangeReportTemplateHandsontableHelper.templateData.columnWidths_zh_CN
        }else if(loginUserLanguage=='en'){
        	singleWellRangeReportTemplateHandsontableHelper.colWidths=singleWellRangeReportTemplateHandsontableHelper.templateData.columnWidths_en
	    }else if(loginUserLanguage=='ru'){
        	singleWellRangeReportTemplateHandsontableHelper.colWidths=singleWellRangeReportTemplateHandsontableHelper.templateData.columnWidths_ru
	    }

        singleWellRangeReportTemplateHandsontableHelper.initData = function () {
            singleWellRangeReportTemplateHandsontableHelper.data = [];
            if(singleWellRangeReportTemplateHandsontableHelper.templateData.header!=undefined){
            	for (var i = 0; i < singleWellRangeReportTemplateHandsontableHelper.templateData.header.length; i++) {
                    if(loginUserLanguage=='zh_CN'){
                    	singleWellRangeReportTemplateHandsontableHelper.data.push(singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].title_zh_CN);
            		}else if(loginUserLanguage=='en'){
            			singleWellRangeReportTemplateHandsontableHelper.data.push(singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].title_en);
            		}else if(loginUserLanguage=='ru'){
            			singleWellRangeReportTemplateHandsontableHelper.data.push(singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].title_ru);
            		}
                }
            }
        }

        singleWellRangeReportTemplateHandsontableHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            if (singleWellRangeReportTemplateHandsontableHelper != null && singleWellRangeReportTemplateHandsontableHelper.hot != null) {
                for (var i = 0; i < singleWellRangeReportTemplateHandsontableHelper.templateData.header.length; i++) {
                    if (row == i) {
                        if (isNotVal(singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle)) {
                            if (isNotVal(singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight)) {
                                td.style.fontWeight = singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight;
                            }
                            if (isNotVal(singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize)) {
                                td.style.fontSize = singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize;
                            }
                            if (isNotVal(singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily)) {
//                                td.style.fontFamily = singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily;
                            }
                            if (isNotVal(singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height)) {
                                td.style.height = singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height;
                            }
                            if (isNotVal(singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color)) {
                                td.style.color = singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color;
                            }
                            if (isNotVal(singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor)) {
                                td.style.backgroundColor = singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor;
                            }
                            if (isNotVal(singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign)) {
                                td.style.textAlign = singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign;
                            }
                        }
                        break;
                    }
                }
            }
        }


        singleWellRangeReportTemplateHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
            if (row <= 2 && row >= 1) {
                td.style.fontWeight = 'bold';
                td.style.fontSize = '13px';
                td.style.color = 'rgb(0, 0, 51)';
//                td.style.fontFamily = 'SimSun'; //SimHei-黑体 SimSun-宋体
            }
        }

        singleWellRangeReportTemplateHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            if (row < 1) {
                td.style.fontWeight = 'bold';
                td.style.fontSize = '25px';
//                td.style.fontFamily = 'SimSun';
                td.style.height = '50px';
            }
        }

        singleWellRangeReportTemplateHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
            if (row < 3) {
                td.style.fontWeight = 'bold';
                td.style.fontSize = '5px';
//                td.style.fontFamily = 'SimHei';
            }
        }


        singleWellRangeReportTemplateHandsontableHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(183, 222, 232)';
        }

        singleWellRangeReportTemplateHandsontableHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(216, 228, 188)';
        }

        singleWellRangeReportTemplateHandsontableHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.display = 'none';
        }

        // 实现标题居中
        singleWellRangeReportTemplateHandsontableHelper.titleCenter = function () {
            $(containerid).width($($('.wtHider')[0]).width());
        }

        singleWellRangeReportTemplateHandsontableHelper.createTable = function () {
            singleWellRangeReportTemplateHandsontableHelper.container.innerHTML = "";
            singleWellRangeReportTemplateHandsontableHelper.hot = new Handsontable(singleWellRangeReportTemplateHandsontableHelper.container, {
                licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                data: singleWellRangeReportTemplateHandsontableHelper.data,
                fixedRowsTop: singleWellRangeReportTemplateHandsontableHelper.templateData.fixedRowsTop,
                fixedRowsBottom: singleWellRangeReportTemplateHandsontableHelper.templateData.fixedRowsBottom,
                //	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
                rowHeaders: false,
                colHeaders: false,
                rowHeights: singleWellRangeReportTemplateHandsontableHelper.templateData.rowHeights,
                colWidths: singleWellRangeReportTemplateHandsontableHelper.colWidths,
//                colWidths:[80, 100,80,80,140,100,100,100,100,100,100,1000],
                rowHeaders(index) {
                    return 'Row ' + (index + 1);
                },
                colHeaders(index) {
                    return 'Col ' + (index + 1);
                },
                stretchH: 'all',
                columnSorting: true, //允许排序
                allowInsertRow: false,
                sortIndicator: true,
                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                filters: true,
                renderAllRows: true,
                search: true,
                mergeCells: singleWellRangeReportTemplateHandsontableHelper.templateData.mergeCells,
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
                    cellProperties.readOnly = true;
                    cellProperties.renderer = singleWellRangeReportTemplateHandsontableHelper.addStyle;
                    return cellProperties;
                },
                afterOnCellMouseOver: function(event, coords, TD){
                	if(coords.col>=0 && coords.row>=0 && singleWellRangeReportTemplateHandsontableHelper!=null
                		&& singleWellRangeReportTemplateHandsontableHelper.hot!=''
                		&& singleWellRangeReportTemplateHandsontableHelper.hot!=undefined 
                		&& singleWellRangeReportTemplateHandsontableHelper.hot.getDataAtCell!=undefined){
                		var rawValue=singleWellRangeReportTemplateHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
        singleWellRangeReportTemplateHandsontableHelper.getData = function (data) {

        }

        var init = function () {
            singleWellRangeReportTemplateHandsontableHelper.initData();
        }

        init();
        return singleWellRangeReportTemplateHandsontableHelper;
    }
};

function CreateSingleWellDailyReportTemplateInfoTable(name, calculateType, code) {
    Ext.getCmp("ReportUnitSingleWellDailyReportTemplateTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/acquisitionUnitManagerController/getReportTemplateData',
        success: function (response) {
            Ext.getCmp("ReportUnitSingleWellDailyReportTemplateTableInfoPanel_Id").getEl().unmask();
            Ext.getCmp("ReportUnitSingleWellDailyReportTemplateTableInfoPanel_Id").setTitle(name+'/'+loginUserLanguageResource.deviceHourlyReportTemplate);
            var result = Ext.JSON.decode(response.responseText);

            if (singleWellDailyReportTemplateHandsontableHelper != null) {
                if (singleWellDailyReportTemplateHandsontableHelper.hot != undefined) {
                    singleWellDailyReportTemplateHandsontableHelper.hot.destroy();
                }
                singleWellDailyReportTemplateHandsontableHelper = null;
            }

            if (singleWellDailyReportTemplateHandsontableHelper == null || singleWellDailyReportTemplateHandsontableHelper.hot == undefined) {
                singleWellDailyReportTemplateHandsontableHelper = SingleWellDailyReportTemplateHandsontableHelper.createNew("ReportUnitSingleWellDailyReportTemplateTableInfoDiv_id", "ReportUnitSingleWellDailyReportTemplateTableInfoContainer", result);
                singleWellDailyReportTemplateHandsontableHelper.createTable();
            }
        },
        failure: function () {
            Ext.getCmp("ReportUnitSingleWellDailyReportTemplateTableInfoPanel_Id").getEl().unmask();
            Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
        },
        params: {
            reportType: 2,
            calculateType: calculateType,
            code: code
        }
    });
}

var SingleWellDailyReportTemplateHandsontableHelper = {
    createNew: function (divid, containerid, templateData) {
        var singleWellDailyReportTemplateHandsontableHelper = {};
        singleWellDailyReportTemplateHandsontableHelper.templateData = templateData;
        singleWellDailyReportTemplateHandsontableHelper.get_data = {};
        singleWellDailyReportTemplateHandsontableHelper.data = [];
        singleWellDailyReportTemplateHandsontableHelper.hot = '';
        singleWellDailyReportTemplateHandsontableHelper.container = document.getElementById(divid);

        singleWellDailyReportTemplateHandsontableHelper.colWidths=[];
        if(loginUserLanguage=='zh_CN'){
        	singleWellDailyReportTemplateHandsontableHelper.colWidths=singleWellDailyReportTemplateHandsontableHelper.templateData.columnWidths_zh_CN
        }else if(loginUserLanguage=='en'){
        	singleWellDailyReportTemplateHandsontableHelper.colWidths=singleWellDailyReportTemplateHandsontableHelper.templateData.columnWidths_en
	    }else if(loginUserLanguage=='ru'){
        	singleWellDailyReportTemplateHandsontableHelper.colWidths=singleWellDailyReportTemplateHandsontableHelper.templateData.columnWidths_ru
	    }

        singleWellDailyReportTemplateHandsontableHelper.initData = function () {
            singleWellDailyReportTemplateHandsontableHelper.data = [];
            if(singleWellDailyReportTemplateHandsontableHelper.templateData.header!=undefined){
            	for (var i = 0; i < singleWellDailyReportTemplateHandsontableHelper.templateData.header.length; i++) {
            		if(loginUserLanguage=='zh_CN'){
            			singleWellDailyReportTemplateHandsontableHelper.data.push(singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].title_zh_CN);
            		}else if(loginUserLanguage=='en'){
            			singleWellDailyReportTemplateHandsontableHelper.data.push(singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].title_en);
            		}else if(loginUserLanguage=='ru'){
            			singleWellDailyReportTemplateHandsontableHelper.data.push(singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].title_ru);
            		} 
                }
            }
        }

        singleWellDailyReportTemplateHandsontableHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            if (singleWellDailyReportTemplateHandsontableHelper != null && singleWellDailyReportTemplateHandsontableHelper.hot != null) {
                for (var i = 0; i < singleWellDailyReportTemplateHandsontableHelper.templateData.header.length; i++) {
                    if (row == i) {
                        if (isNotVal(singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle)) {
                            if (isNotVal(singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight)) {
                                td.style.fontWeight = singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight;
                            }
                            if (isNotVal(singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize)) {
                                td.style.fontSize = singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize;
                            }
                            if (isNotVal(singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily)) {
//                                td.style.fontFamily = singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily;
                            }
                            if (isNotVal(singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height)) {
                                td.style.height = singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height;
                            }
                            if (isNotVal(singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color)) {
                                td.style.color = singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color;
                            }
                            if (isNotVal(singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor)) {
                                td.style.backgroundColor = singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor;
                            }
                            if (isNotVal(singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign)) {
                                td.style.textAlign = singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign;
                            }
                        }
                        break;
                    }
                }
            }
        }


        singleWellDailyReportTemplateHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
            if (row <= 2 && row >= 1) {
                td.style.fontWeight = 'bold';
                td.style.fontSize = '13px';
                td.style.color = 'rgb(0, 0, 51)';
//                td.style.fontFamily = 'SimSun'; //SimHei-黑体 SimSun-宋体
            }
        }

        singleWellDailyReportTemplateHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            if (row < 1) {
                td.style.fontWeight = 'bold';
                td.style.fontSize = '25px';
//                td.style.fontFamily = 'SimSun';
                td.style.height = '50px';
            }
        }

        singleWellDailyReportTemplateHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
            if (row < 3) {
                td.style.fontWeight = 'bold';
                td.style.fontSize = '5px';
//                td.style.fontFamily = 'SimHei';
            }
        }


        singleWellDailyReportTemplateHandsontableHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(183, 222, 232)';
        }

        singleWellDailyReportTemplateHandsontableHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(216, 228, 188)';
        }

        singleWellDailyReportTemplateHandsontableHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.display = 'none';
        }

        // 实现标题居中
        singleWellDailyReportTemplateHandsontableHelper.titleCenter = function () {
            $(containerid).width($($('.wtHider')[0]).width());
        }

        singleWellDailyReportTemplateHandsontableHelper.createTable = function () {
            singleWellDailyReportTemplateHandsontableHelper.container.innerHTML = "";
            singleWellDailyReportTemplateHandsontableHelper.hot = new Handsontable(singleWellDailyReportTemplateHandsontableHelper.container, {
                licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                data: singleWellDailyReportTemplateHandsontableHelper.data,
                fixedRowsTop: singleWellDailyReportTemplateHandsontableHelper.templateData.fixedRowsTop,
                fixedRowsBottom: singleWellDailyReportTemplateHandsontableHelper.templateData.fixedRowsBottom,
                //	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
                rowHeaders: false,
                colHeaders: false,
                rowHeights: singleWellDailyReportTemplateHandsontableHelper.templateData.rowHeights,
                colWidths: singleWellDailyReportTemplateHandsontableHelper.colWidths,
//                colWidths: [80, 100, 80, 80, 120, 120, 100, 100, 100, 100, 100, 100, 160, 1000],
                //					rowHeaders: true, //显示行头
                rowHeaders(index) {
                    return 'Row ' + (index + 1);
                },
                //					colHeaders: true, //显示列头
                colHeaders(index) {
                    return 'Col ' + (index + 1);
                },
                stretchH: 'all',
                columnSorting: true, //允许排序
                allowInsertRow: false,
                sortIndicator: true,
                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                filters: true,
                renderAllRows: true,
                search: true,
                mergeCells: singleWellDailyReportTemplateHandsontableHelper.templateData.mergeCells,
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
                    cellProperties.readOnly = true;
                    cellProperties.renderer = singleWellDailyReportTemplateHandsontableHelper.addStyle;
                    return cellProperties;
                },
                afterOnCellMouseOver: function(event, coords, TD){
                	if(coords.col>=0 && coords.row>=0 && singleWellDailyReportTemplateHandsontableHelper!=null
                		&& singleWellDailyReportTemplateHandsontableHelper.hot!=''
                		&& singleWellDailyReportTemplateHandsontableHelper.hot!=undefined 
                		&& singleWellDailyReportTemplateHandsontableHelper.hot.getDataAtCell!=undefined){
                		var rawValue=singleWellDailyReportTemplateHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
        singleWellDailyReportTemplateHandsontableHelper.getData = function (data) {

        }

        var init = function () {
            singleWellDailyReportTemplateHandsontableHelper.initData();
        }

        init();
        return singleWellDailyReportTemplateHandsontableHelper;
    }
};

function renderReportUnitContentConfig(instance, td, row, col, prop, value, cellProperties) {
    td.innerHTML = "<a href='#' onclick=reportUnitContentConfig(" + row + "," + col + "," + value + ")><span>"+loginUserLanguageResource.config+"..."+"</span></a>";
}

function reportUnitContentConfig(row, col, value) {
    var reportType = getReportType();
    var calculateType = 0;
    var unitId = 0;
    var unitName = '';
    var classes = 0;
    var reportTemplateListGridPanel="";
    
    if(reportType==0){
    	reportTemplateListGridPanel="ReportUnitSingleWellRangeReportTemplateListGridPanel_Id";
    }else if(reportType==1){
    	reportTemplateListGridPanel="ReportUnitProductionReportTemplateListGridPanel_Id";
    }else if(reportType==2){
    	reportTemplateListGridPanel="ReportUnitSingleWellDailyReportTemplateListGridPanel_Id";
    }
    
    var reportUnitConfigTreeSelection = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getSelectionModel().getSelection();
    if (reportUnitConfigTreeSelection.length > 0) {
        var record = reportUnitConfigTreeSelection[0];
        classes = record.data.classes;
        if (classes == 0) {
            if (isNotVal(record.data.children) && record.data.children.length > 0) {
                unitId = record.data.children[0].id;
                unitName = record.data.children[0].text;
            }
        } else if (classes == 1) {
            unitId = record.data.id;
            unitName = record.data.text;
        }
        calculateType = record.data.calculateType;
    }

    var templateSelection = Ext.getCmp(reportTemplateListGridPanel).getSelectionModel().getSelection();
    var templateCode = "";
    if (templateSelection.length > 0) {
        templateCode = templateSelection[0].data.templateCode;
    }

    var window = Ext.create("AP.view.acquisitionUnit.ReportUnitContentConfigWindow", {
        title: loginUserLanguageResource.reportContentConfig
    });

    Ext.getCmp("ReportUnitContentConfig_Classes").setValue(classes);
    Ext.getCmp("ReportUnitContentConfig_UnitId").setValue(unitId);
    Ext.getCmp("ReportUnitContentConfig_UnitName").setValue(unitName);

    Ext.getCmp("ReportUnitContentConfig_ReportType").setValue(reportType);
    Ext.getCmp("ReportUnitContentConfig_CalculateType").setValue(calculateType);
    Ext.getCmp("ReportUnitContentConfig_SelectedRow").setValue(row);
    Ext.getCmp("ReportUnitContentConfig_SelectedCol").setValue(col);
    Ext.getCmp("ReportUnitContentConfig_TemplateCode").setValue(templateCode);

    window.show();

}

function CreateSingleWellRangeReportTotalItemsInfoTable() {
    Ext.getCmp("ReportUnitSingleWellRangeReportContentConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();

    var calculateType = 0;
    var unitId = 0;
    var unitName = '';
    var classes = 0;
    var reportUnitConfigTreeSelection = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getSelectionModel().getSelection();
    if (reportUnitConfigTreeSelection.length > 0) {
        var record = reportUnitConfigTreeSelection[0];
        if (record.data.classes == 0) {
            if (isNotVal(record.data.children) && record.data.children.length > 0) {
                unitId = record.data.children[0].id;
            }
        } else if (record.data.classes == 1) {
            unitId = record.data.id;
        }
        calculateType = record.data.calculateType;
        unitName = record.data.text;
        classes = record.data.classes;
    }

    var templateSelection = Ext.getCmp("ReportUnitSingleWellRangeReportTemplateListGridPanel_Id").getSelectionModel().getSelection();
    var templateCode = "";
    if (templateSelection.length > 0) {
        templateCode = templateSelection[0].data.templateCode;
    }

    Ext.Ajax.request({
        method: 'POST',
        url: context + '/acquisitionUnitManagerController/getReportUnitTotalCalItemsConfigData',
        success: function (response) {
            Ext.getCmp("ReportUnitSingleWellRangeReportContentConfigTableInfoPanel_Id").getEl().unmask();
            var result = Ext.JSON.decode(response.responseText);
            if (classes == 0) {
                Ext.getCmp("ReportUnitSingleWellRangeReportContentConfigTableInfoPanel_Id").setTitle(loginUserLanguageResource.deviceDailyReportContentConfig);
            } else {
                Ext.getCmp("ReportUnitSingleWellRangeReportContentConfigTableInfoPanel_Id").setTitle(unitName + '/'+loginUserLanguageResource.deviceDailyReportContentConfig);
            }
            if (singleWellRangeReportTemplateContentHandsontableHelper == null || singleWellRangeReportTemplateContentHandsontableHelper.hot == undefined) {
                singleWellRangeReportTemplateContentHandsontableHelper = SingleWellRangeReportTemplateContentHandsontableHelper.createNew("ReportUnitSingleWellRangeReportContentConfigTableInfoDiv_id");
                var colHeaders = [loginUserLanguageResource.idx, loginUserLanguageResource.dataColumnName,loginUserLanguageResource.dataColumn, loginUserLanguageResource.unit, loginUserLanguageResource.dataSource, loginUserLanguageResource.totalType, loginUserLanguageResource.showLevel, loginUserLanguageResource.prec, loginUserLanguageResource.reportCurve, loginUserLanguageResource.config];
                var columns = [
                    {
                        data: 'id'
                    },
                    {
                        data: 'headerName'
                    },
                    {
                        data: 'itemName'
                    },
                    {
                        data: 'unit'
                    },
                    {
                        data: 'dataSource'
                    },
                    {
                        data: 'totalType'
                    },
                    {
                        data: 'showLevel'
                    },
                    {
                        data: 'prec'
                    },
                    {
                        data: 'reportCurveConfShowValue'
                    },
                    {
                        data: 'config',
                        renderer: renderReportUnitContentConfig
                    }
						];
                singleWellRangeReportTemplateContentHandsontableHelper.colHeaders = colHeaders;
                singleWellRangeReportTemplateContentHandsontableHelper.columns = columns;
                singleWellRangeReportTemplateContentHandsontableHelper.createTable(result.totalRoot);
            } else {
                singleWellRangeReportTemplateContentHandsontableHelper.hot.loadData(result.totalRoot);
            }
        },
        failure: function () {
            Ext.getCmp("ReportUnitSingleWellRangeReportContentConfigTableInfoPanel_Id").getEl().unmask();
            Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
        },
        params: {
            calculateType: calculateType,
            reportType: 0,
            unitId: unitId,
            templateCode: templateCode,
            classes: classes
        }
    });
};

var SingleWellRangeReportTemplateContentHandsontableHelper = {
    createNew: function (divid) {
        var singleWellRangeReportTemplateContentHandsontableHelper = {};
        singleWellRangeReportTemplateContentHandsontableHelper.hot1 = '';
        singleWellRangeReportTemplateContentHandsontableHelper.divid = divid;
        singleWellRangeReportTemplateContentHandsontableHelper.validresult = true; //数据校验
        singleWellRangeReportTemplateContentHandsontableHelper.colHeaders = [];
        singleWellRangeReportTemplateContentHandsontableHelper.columns = [];
        singleWellRangeReportTemplateContentHandsontableHelper.AllData = [];

        singleWellRangeReportTemplateContentHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            if (value != null) {
                var arr = value.split(';');
                if (arr.length == 3) {
                    td.style.backgroundColor = '#' + arr[2];
                }
            }
            td.style.whiteSpace='nowrap'; //文本不换行
        	td.style.overflow='hidden';//超出部分隐藏
        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
        }
        
        singleWellRangeReportTemplateContentHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.whiteSpace='nowrap'; //文本不换行
        	td.style.overflow='hidden';//超出部分隐藏
        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
        }

        singleWellRangeReportTemplateContentHandsontableHelper.createTable = function (data) {
            $('#' + singleWellRangeReportTemplateContentHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + singleWellRangeReportTemplateContentHandsontableHelper.divid);
            singleWellRangeReportTemplateContentHandsontableHelper.hot = new Handsontable(hotElement, {
                licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                data: data,
                hiddenColumns: {
                    columns: [],
                    indicators: false,
                    copyPasteEnabled: false
                },
                colWidths: [50, 150, 150, 80, 90, 120, 80, 60, 110, 80],
                columns: singleWellRangeReportTemplateContentHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: false, //显示行头
                colHeaders: singleWellRangeReportTemplateContentHandsontableHelper.colHeaders, //显示列头
                columnSorting: true, //允许排序
                sortIndicator: true,
                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                filters: true,
                renderAllRows: true,
                search: true,
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
                    cellProperties.readOnly = true;
                    if (visualColIndex == 8) {
                        cellProperties.renderer = singleWellRangeReportTemplateContentHandsontableHelper.addCurveBg;
                    }else{
                    	if(singleWellRangeReportTemplateContentHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& singleWellRangeReportTemplateContentHandsontableHelper.columns[visualColIndex].type!='checkbox'
	    	            		&& prop.toUpperCase() != "config".toUpperCase()){
	                    	cellProperties.renderer = singleWellRangeReportTemplateContentHandsontableHelper.addCellStyle;
	    	            }
                    }
                    return cellProperties;
                },
                afterBeginEditing: function (row, column) {
                    if (singleWellRangeReportTemplateContentHandsontableHelper != null && singleWellRangeReportTemplateContentHandsontableHelper.hot != undefined) {
                        if (column == 9) {
                            alert("配置此列内容!");
                        }
                    }
                },
                afterOnCellMouseOver: function(event, coords, TD){
                	if(coords.col>=0 && coords.row>=0 && singleWellRangeReportTemplateContentHandsontableHelper.columns[coords.col].type!='checkbox' 
                		&& singleWellRangeReportTemplateContentHandsontableHelper!=null
                		&& singleWellRangeReportTemplateContentHandsontableHelper.hot!=''
                		&& singleWellRangeReportTemplateContentHandsontableHelper.hot!=undefined 
                		&& singleWellRangeReportTemplateContentHandsontableHelper.hot.getDataAtCell!=undefined){
                		var rawValue=singleWellRangeReportTemplateContentHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
        singleWellRangeReportTemplateContentHandsontableHelper.saveData = function () {}
        singleWellRangeReportTemplateContentHandsontableHelper.clearContainer = function () {
            singleWellRangeReportTemplateContentHandsontableHelper.AllData = [];
        }
        return singleWellRangeReportTemplateContentHandsontableHelper;
    }
};

function CreateSingleWellDailyReportTotalItemsInfoTable() {
    Ext.getCmp("ReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
    
    var calculateType = 0;
    var unitId = 0;
    var unitName = '';
    var classes = 0;
    var reportUnitConfigTreeSelection = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getSelectionModel().getSelection();
    if (reportUnitConfigTreeSelection.length > 0) {
        var record = reportUnitConfigTreeSelection[0];
        if (record.data.classes == 0) {
            if (isNotVal(record.data.children) && record.data.children.length > 0) {
                unitId = record.data.children[0].id;
            }
        } else if (record.data.classes == 1) {
            unitId = record.data.id;
        }
        calculateType = record.data.calculateType;
        unitName = record.data.text;
        classes = record.data.classes;
    }

    var templateSelection = Ext.getCmp("ReportUnitSingleWellDailyReportTemplateListGridPanel_Id").getSelectionModel().getSelection();
    var templateCode = "";
    if (templateSelection.length > 0) {
        templateCode = templateSelection[0].data.templateCode;
    }
    
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/acquisitionUnitManagerController/getReportUnitTotalItemsConfigColInfoData',
        success: function (response) {
            Ext.getCmp("ReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id").getEl().unmask();
            var result = Ext.JSON.decode(response.responseText);
            if (classes == 0) {
                Ext.getCmp("ReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id").setTitle(loginUserLanguageResource.deviceHourlyReportContentConfig);
            } else {
                Ext.getCmp("ReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id").setTitle(unitName + '/'+loginUserLanguageResource.deviceHourlyReportContentConfig);
            }
            if (singleWellDailyReportTemplateContentHandsontableHelper == null || singleWellDailyReportTemplateContentHandsontableHelper.hot == undefined) {
                singleWellDailyReportTemplateContentHandsontableHelper = SingleWellDailyReportTemplateContentHandsontableHelper.createNew("ReportUnitSingleWellDailyReportContentConfigTableInfoDiv_id");
                var colHeaders = [loginUserLanguageResource.idx, loginUserLanguageResource.dataColumnName,loginUserLanguageResource.dataColumn, loginUserLanguageResource.unit, loginUserLanguageResource.dataSource, loginUserLanguageResource.totalType, loginUserLanguageResource.showLevel, loginUserLanguageResource.prec, loginUserLanguageResource.reportCurve, loginUserLanguageResource.config];
                var columns = [{
                        data: 'id'
                    },
                    {
                        data: 'headerName'
                    },
                    {
                        data: 'itemName'
                    },
                    {
                        data: 'unit'
                    },
                    {
                        data: 'dataSource'
                    },
                    {
                        data: 'totalType'
                    },
                    {
                        data: 'showLevel'
                    },
                    {
                        data: 'prec'
                    },
                    {
                        data: 'reportCurveConfShowValue'
                    },
                    {
                        data: 'config',
                        renderer: renderReportUnitContentConfig
                    }];
                singleWellDailyReportTemplateContentHandsontableHelper.colHeaders = colHeaders;
                singleWellDailyReportTemplateContentHandsontableHelper.columns = columns;
                singleWellDailyReportTemplateContentHandsontableHelper.createTable(result.totalRoot);
            } else {
                singleWellDailyReportTemplateContentHandsontableHelper.hot.loadData(result.totalRoot);
            }
        },
        failure: function () {
            Ext.getCmp("ReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id").getEl().unmask();
            Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
        },
        params: {
            calculateType: calculateType,
            reportType: 2,
            unitId: unitId,
            templateCode: templateCode,
            classes: classes
        }
    });
};

var SingleWellDailyReportTemplateContentHandsontableHelper = {
    createNew: function (divid) {
        var singleWellDailyReportTemplateContentHandsontableHelper = {};
        singleWellDailyReportTemplateContentHandsontableHelper.hot1 = '';
        singleWellDailyReportTemplateContentHandsontableHelper.divid = divid;
        singleWellDailyReportTemplateContentHandsontableHelper.validresult = true; //数据校验
        singleWellDailyReportTemplateContentHandsontableHelper.colHeaders = [];
        singleWellDailyReportTemplateContentHandsontableHelper.columns = [];
        singleWellDailyReportTemplateContentHandsontableHelper.AllData = [];

        singleWellDailyReportTemplateContentHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            if (value != null) {
                var arr = value.split(';');
                if (arr.length == 3) {
                    td.style.backgroundColor = '#' + arr[2];
                }
            }
            td.style.whiteSpace='nowrap'; //文本不换行
        	td.style.overflow='hidden';//超出部分隐藏
        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
        }
        
        singleWellDailyReportTemplateContentHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.whiteSpace='nowrap'; //文本不换行
        	td.style.overflow='hidden';//超出部分隐藏
        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
        }

        singleWellDailyReportTemplateContentHandsontableHelper.createTable = function (data) {
            $('#' + singleWellDailyReportTemplateContentHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + singleWellDailyReportTemplateContentHandsontableHelper.divid);
            singleWellDailyReportTemplateContentHandsontableHelper.hot = new Handsontable(hotElement, {
                licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                data: data,
                hiddenColumns: {
                    columns: [],
                    indicators: false,
                    copyPasteEnabled: false
                },
                colWidths: [50, 150, 150, 80, 90, 120, 80, 60, 110, 80],
                columns: singleWellDailyReportTemplateContentHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: false, //显示行头
                colHeaders: singleWellDailyReportTemplateContentHandsontableHelper.colHeaders, //显示列头
                columnSorting: true, //允许排序
                sortIndicator: true,
                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                filters: true,
                renderAllRows: true,
                search: true,
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
                    
                    cellProperties.readOnly = true;
                    if (visualColIndex == 8) {
                        cellProperties.renderer = singleWellDailyReportTemplateContentHandsontableHelper.addCurveBg;
                    }
                    else{
                    	if(singleWellDailyReportTemplateContentHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& singleWellDailyReportTemplateContentHandsontableHelper.columns[visualColIndex].type!='checkbox'
	    	            		&& prop.toUpperCase()!='config'.toUpperCase()){
	                    	cellProperties.renderer = singleWellDailyReportTemplateContentHandsontableHelper.addCellStyle;
	    	            }
                    }
                    return cellProperties;
                },
                afterOnCellMouseOver: function(event, coords, TD){
                	if(coords.col>=0 && coords.row>=0 && singleWellDailyReportTemplateContentHandsontableHelper.columns[coords.col].type!='checkbox' 
                		&& singleWellDailyReportTemplateContentHandsontableHelper!=null
                		&& singleWellDailyReportTemplateContentHandsontableHelper.hot!=''
                		&& singleWellDailyReportTemplateContentHandsontableHelper.hot!=undefined 
                		&& singleWellDailyReportTemplateContentHandsontableHelper.hot.getDataAtCell!=undefined){
                		var rawValue=singleWellDailyReportTemplateContentHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
        singleWellDailyReportTemplateContentHandsontableHelper.saveData = function () {}
        singleWellDailyReportTemplateContentHandsontableHelper.clearContainer = function () {
            singleWellDailyReportTemplateContentHandsontableHelper.AllData = [];
        }
        return singleWellDailyReportTemplateContentHandsontableHelper;
    }
};

function CreateProtocolReportUnitPropertiesInfoTable(data) {
    var root = [];
    
    if (data.classes == 0) {
        var item1 = {};
        item1.id = 1;
        item1.title = loginUserLanguageResource.rootNode;
        item1.value = loginUserLanguageResource.unitList;
        root.push(item1);
    } else if (data.classes == 1) {
    	var tabPanel = Ext.getCmp("ReportUnitConfigRightTabPanel_Id");
    	var showInfo=tabPanel.getActiveTab().title;
    	if(isNotVal(data.text)){
    		showInfo="【<font color=red>"+data.text+"</font>】"+showInfo+"&nbsp;"
    	}
    	Ext.getCmp("ReportUnitConfigInformationLabel_Id").setHtml(showInfo);
        Ext.getCmp("ReportUnitConfigInformationLabel_Id").show();
    	
    	
        var item1 = {};
        item1.id = 1;
        item1.title = loginUserLanguageResource.unitName;
        item1.value = data.text;
        root.push(item1);

        var item2 = {};
        item2.id = 2;
        item2.title = loginUserLanguageResource.calculateType;
        item2.value = data.calculateTypeName;
        root.push(item2);

        var item3 = {};
        item3.id = 3;
        item3.title = loginUserLanguageResource.sortNum;
        item3.value = data.sort;
        root.push(item3);
    }

    if (reportUnitPropertiesHandsontableHelper == null || reportUnitPropertiesHandsontableHelper.hot == undefined) {
        reportUnitPropertiesHandsontableHelper = ReportUnitPropertiesHandsontableHelper.createNew("ModbusProtocolReportUnitPropertiesTableInfoDiv_id");
        var colHeaders = "['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.variable+"']";
        var columns = "[{data:'id'},{data:'title'},{data:'value'}]";
        reportUnitPropertiesHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
        reportUnitPropertiesHandsontableHelper.columns = Ext.JSON.decode(columns);
        reportUnitPropertiesHandsontableHelper.classes = data.classes;
        reportUnitPropertiesHandsontableHelper.createTable(root);
    } else {
        reportUnitPropertiesHandsontableHelper.classes = data.classes;
        reportUnitPropertiesHandsontableHelper.hot.loadData(root);
    }
};

var ReportUnitPropertiesHandsontableHelper = {
    createNew: function (divid) {
        var reportUnitPropertiesHandsontableHelper = {};
        reportUnitPropertiesHandsontableHelper.hot = '';
        reportUnitPropertiesHandsontableHelper.classes = null;
        reportUnitPropertiesHandsontableHelper.divid = divid;
        reportUnitPropertiesHandsontableHelper.validresult = true; //数据校验
        reportUnitPropertiesHandsontableHelper.colHeaders = [];
        reportUnitPropertiesHandsontableHelper.columns = [];
        reportUnitPropertiesHandsontableHelper.AllData = [];

        reportUnitPropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(245, 245, 245)';
            td.style.whiteSpace='nowrap'; //文本不换行
        	td.style.overflow='hidden';//超出部分隐藏
        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
        }
        
        reportUnitPropertiesHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.whiteSpace='nowrap'; //文本不换行
        	td.style.overflow='hidden';//超出部分隐藏
        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
        }

        reportUnitPropertiesHandsontableHelper.createTable = function (data) {
            $('#' + reportUnitPropertiesHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + reportUnitPropertiesHandsontableHelper.divid);
            reportUnitPropertiesHandsontableHelper.hot = new Handsontable(hotElement, {
                licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                data: data,
                colWidths: [1, 8, 10],
                columns: reportUnitPropertiesHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: false, //显示行头
                colHeaders: reportUnitPropertiesHandsontableHelper.colHeaders, //显示列头
                columnSorting: true, //允许排序
                sortIndicator: true,
                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                filters: true,
                renderAllRows: true,
                search: true,
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
                    var protocolConfigModuleEditFlag = parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
                    if (protocolConfigModuleEditFlag == 1) {
                        if (visualColIndex == 0 || visualColIndex == 1) {
                            cellProperties.readOnly = true;
                            cellProperties.renderer = reportUnitPropertiesHandsontableHelper.addBoldBg;
                        }
                        if (reportUnitPropertiesHandsontableHelper.classes === 0) {
                            cellProperties.readOnly = true;
                            cellProperties.renderer = reportUnitPropertiesHandsontableHelper.addBoldBg;
                        } else if (reportUnitPropertiesHandsontableHelper.classes === 1) {
                        	if (visualColIndex === 0 || visualColIndex === 1) {
                        		cellProperties.readOnly = true;
                                cellProperties.renderer = reportUnitPropertiesHandsontableHelper.addBoldBg;
                        	}else if (visualColIndex === 2 && visualRowIndex === 1) {
                                this.type = 'dropdown';
                                this.source = [loginUserLanguageResource.nothing, loginUserLanguageResource.SRPCalculate, loginUserLanguageResource.PCPCalculate];
                                this.strict = true;
                                this.allowInvalid = false;
                            }else{
                            	if (visualColIndex === 2 && visualRowIndex === 0) {
                                    this.validator = function (val, callback) {
                                        return handsontableDataCheck_NotNull(val, callback, row, col, reportUnitPropertiesHandsontableHelper);
                                    }
                                } else if (visualColIndex === 2 && visualRowIndex === 2) {
                                    this.validator = function (val, callback) {
                                        return handsontableDataCheck_Num_Nullable(val, callback, row, col, reportUnitPropertiesHandsontableHelper);
                                    }
                                }
                            	cellProperties.renderer = reportUnitPropertiesHandsontableHelper.addCellStyle;
                            }
                        }
                    } else {
                        cellProperties.readOnly = true;
                        cellProperties.renderer = reportUnitPropertiesHandsontableHelper.addBoldBg;
                    }

                    return cellProperties;
                },
                afterOnCellMouseOver: function(event, coords, TD){
                	if(coords.col>=0 && coords.row>=0 && reportUnitPropertiesHandsontableHelper.columns[coords.col].type!='checkbox' 
                		&& reportUnitPropertiesHandsontableHelper!=null
                		&& reportUnitPropertiesHandsontableHelper.hot!=''
                		&& reportUnitPropertiesHandsontableHelper.hot!=undefined 
                		&& reportUnitPropertiesHandsontableHelper.hot.getDataAtCell!=undefined){
                		var rawValue=reportUnitPropertiesHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
        reportUnitPropertiesHandsontableHelper.saveData = function () {}
        reportUnitPropertiesHandsontableHelper.clearContainer = function () {
            reportUnitPropertiesHandsontableHelper.AllData = [];
        }
        return reportUnitPropertiesHandsontableHelper;
    }
};

function CreateProductionReportTemplateInfoTable(name, calculateType, code) {
    Ext.getCmp("ModbusProtocolReportUnitProductionTemplateTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/acquisitionUnitManagerController/getReportTemplateData',
        success: function (response) {
            Ext.getCmp("ModbusProtocolReportUnitProductionTemplateTableInfoPanel_Id").getEl().unmask();
            Ext.getCmp("ModbusProtocolReportUnitProductionTemplateTableInfoPanel_Id").setTitle(name+'/'+loginUserLanguageResource.areaDailyReportTemplate);
            var result = Ext.JSON.decode(response.responseText);

            if (productionReportTemplateHandsontableHelper != null) {
                if (productionReportTemplateHandsontableHelper.hot != undefined) {
                    productionReportTemplateHandsontableHelper.hot.destroy();
                }
                productionReportTemplateHandsontableHelper = null;
            }

            if (productionReportTemplateHandsontableHelper == null || productionReportTemplateHandsontableHelper.hot == undefined) {
                productionReportTemplateHandsontableHelper = ProductionReportTemplateHandsontableHelper.createNew("ModbusProtocolReportUnitProductionTemplateTableInfoDiv_id", "ModbusProtocolReportUnitProductionTemplateTableInfoContainer", result);
                productionReportTemplateHandsontableHelper.createTable();
            }
        },
        failure: function () {
            Ext.getCmp("ModbusProtocolReportUnitProductionTemplateTableInfoPanel_Id").getEl().unmask();
            Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
        },
        params: {
            reportType: 1,
            name: name,
            calculateType: calculateType,
            code: code
        }
    });
}

var ProductionReportTemplateHandsontableHelper = {
    createNew: function (divid, containerid, templateData) {
        var productionReportTemplateHandsontableHelper = {};
        productionReportTemplateHandsontableHelper.templateData = templateData;
        productionReportTemplateHandsontableHelper.get_data = {};
        productionReportTemplateHandsontableHelper.data = [];
        productionReportTemplateHandsontableHelper.hot = '';
        productionReportTemplateHandsontableHelper.container = document.getElementById(divid);

        productionReportTemplateHandsontableHelper.colWidths=[];
        if(loginUserLanguage=='zh_CN'){
        	productionReportTemplateHandsontableHelper.colWidths=productionReportTemplateHandsontableHelper.templateData.columnWidths_zh_CN
        }else if(loginUserLanguage=='en'){
        	productionReportTemplateHandsontableHelper.colWidths=productionReportTemplateHandsontableHelper.templateData.columnWidths_en
	    }else if(loginUserLanguage=='ru'){
        	productionReportTemplateHandsontableHelper.colWidths=productionReportTemplateHandsontableHelper.templateData.columnWidths_ru
	    }

        productionReportTemplateHandsontableHelper.initData = function () {
            productionReportTemplateHandsontableHelper.data = [];
            if(productionReportTemplateHandsontableHelper.templateData.header!=undefined){
            	for (var i = 0; i < productionReportTemplateHandsontableHelper.templateData.header.length; i++) {
                    if(loginUserLanguage=='zh_CN'){
                    	productionReportTemplateHandsontableHelper.data.push(productionReportTemplateHandsontableHelper.templateData.header[i].title_zh_CN);
            		}else if(loginUserLanguage=='en'){
            			productionReportTemplateHandsontableHelper.data.push(productionReportTemplateHandsontableHelper.templateData.header[i].title_en);
            		}else if(loginUserLanguage=='ru'){
            			productionReportTemplateHandsontableHelper.data.push(productionReportTemplateHandsontableHelper.templateData.header[i].title_ru);
            		} 
                }
            }
        }

        productionReportTemplateHandsontableHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            if (productionReportTemplateHandsontableHelper != null && productionReportTemplateHandsontableHelper.hot != null) {
                for (var i = 0; i < productionReportTemplateHandsontableHelper.templateData.header.length; i++) {
                    if (row == i) {
                        if (isNotVal(productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle)) {
                            if (isNotVal(productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight)) {
                                td.style.fontWeight = productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight;
                            }
                            if (isNotVal(productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize)) {
                                td.style.fontSize = productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize;
                            }
                            if (isNotVal(productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily)) {
//                                td.style.fontFamily = productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily;
                            }
                            if (isNotVal(productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height)) {
                                td.style.height = productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height;
                            }
                            if (isNotVal(productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color)) {
                                td.style.color = productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color;
                            }
                            if (isNotVal(productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor)) {
                                td.style.backgroundColor = productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor;
                            }
                            if (isNotVal(productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign)) {
                                td.style.textAlign = productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign;
                            }
                        }
                        break;
                    }
                }
            }
        }


        productionReportTemplateHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
            if (row <= 2 && row >= 1) {
                td.style.fontWeight = 'bold';
                td.style.fontSize = '13px';
                td.style.color = 'rgb(0, 0, 51)';
//                td.style.fontFamily = 'SimSun'; //SimHei-黑体 SimSun-宋体
            }
        }

        productionReportTemplateHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            if (row < 1) {
                td.style.fontWeight = 'bold';
                td.style.fontSize = '25px';
//                td.style.fontFamily = 'SimSun';
                td.style.height = '50px';
            }
        }

        productionReportTemplateHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
            if (row < 3) {
                td.style.fontWeight = 'bold';
                td.style.fontSize = '5px';
//                td.style.fontFamily = 'SimHei';
            }
        }


        productionReportTemplateHandsontableHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(183, 222, 232)';
        }

        productionReportTemplateHandsontableHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(216, 228, 188)';
        }

        productionReportTemplateHandsontableHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.display = 'none';
        }

        // 实现标题居中
        productionReportTemplateHandsontableHelper.titleCenter = function () {
            $(containerid).width($($('.wtHider')[0]).width());
        }

        productionReportTemplateHandsontableHelper.createTable = function () {
            productionReportTemplateHandsontableHelper.container.innerHTML = "";
            productionReportTemplateHandsontableHelper.hot = new Handsontable(productionReportTemplateHandsontableHelper.container, {
                licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                data: productionReportTemplateHandsontableHelper.data,
                fixedRowsTop: productionReportTemplateHandsontableHelper.templateData.fixedRowsTop,
                fixedRowsBottom: productionReportTemplateHandsontableHelper.templateData.fixedRowsBottom,
                //	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
                rowHeaders: false,
                colHeaders: false,
                rowHeights: productionReportTemplateHandsontableHelper.templateData.rowHeights,
                colWidths: productionReportTemplateHandsontableHelper.colWidths,
                rowHeaders(index) {
                    return 'Row ' + (index + 1);
                },
                colHeaders(index) {
                    return 'Col ' + (index + 1);
                },
                stretchH: 'all',
                columnSorting: true, //允许排序
                allowInsertRow: false,
                sortIndicator: true,
                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                filters: true,
                renderAllRows: true,
                search: true,
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
                mergeCells: productionReportTemplateHandsontableHelper.templateData.mergeCells,
                cells: function (row, col, prop) {
                    var cellProperties = {};
                    var visualRowIndex = this.instance.toVisualRow(row);
                    var visualColIndex = this.instance.toVisualColumn(col);
                    cellProperties.readOnly = true;
                    cellProperties.renderer = productionReportTemplateHandsontableHelper.addStyle;
                    return cellProperties;
                },
                afterOnCellMouseOver: function(event, coords, TD){
                	if(coords.col>=0 && coords.row>=0 && productionReportTemplateHandsontableHelper!=null
                		&& productionReportTemplateHandsontableHelper.hot!=''
                		&& productionReportTemplateHandsontableHelper.hot!=undefined 
                		&& productionReportTemplateHandsontableHelper.hot.getDataAtCell!=undefined){
                		var rawValue=productionReportTemplateHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
        productionReportTemplateHandsontableHelper.getData = function (data) {

        }

        var init = function () {
            productionReportTemplateHandsontableHelper.initData();
        }

        init();
        return productionReportTemplateHandsontableHelper;
    }
};

function CreateproductionReportTotalItemsInfoTable(calculateType, unitId, unitName, classes) {
    Ext.getCmp("ModbusProtocolProductionReportUnitContentConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
    
    var calculateType = 0;
    var unitId = 0;
    var unitName = '';
    var classes = 0;
    var reportUnitConfigTreeSelection = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getSelectionModel().getSelection();
    if (reportUnitConfigTreeSelection.length > 0) {
        var record = reportUnitConfigTreeSelection[0];
        if (record.data.classes == 0) {
            if (isNotVal(record.data.children) && record.data.children.length > 0) {
                unitId = record.data.children[0].id;
            }
        } else if (record.data.classes == 1) {
            unitId = record.data.id;
        }
        calculateType = record.data.calculateType;
        unitName = record.data.text;
        classes = record.data.classes;
    }

    var templateSelection = Ext.getCmp("ReportUnitProductionReportTemplateListGridPanel_Id").getSelectionModel().getSelection();
    var templateCode = "";
    if (templateSelection.length > 0) {
        templateCode = templateSelection[0].data.templateCode;
    }
    
    
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/acquisitionUnitManagerController/getReportUnitTotalCalItemsConfigData',
        success: function (response) {
            Ext.getCmp("ModbusProtocolProductionReportUnitContentConfigTableInfoPanel_Id").getEl().unmask();
            var result = Ext.JSON.decode(response.responseText);
            if (classes == 0) {
                Ext.getCmp("ModbusProtocolProductionReportUnitContentConfigTableInfoPanel_Id").setTitle(loginUserLanguageResource.areaDailyReportContentConfig);
            } else {
                Ext.getCmp("ModbusProtocolProductionReportUnitContentConfigTableInfoPanel_Id").setTitle(unitName + '/'+loginUserLanguageResource.areaDailyReportContentConfig);
            }
            if (productionReportTemplateContentHandsontableHelper == null || productionReportTemplateContentHandsontableHelper.hot == undefined) {
                productionReportTemplateContentHandsontableHelper = ProductionReportTemplateContentHandsontableHelper.createNew("ModbusProtocolProductionReportUnitContentConfigTableInfoDiv_id");
                var colHeaders = [loginUserLanguageResource.idx,loginUserLanguageResource.dataColumnName,loginUserLanguageResource.dataColumn,loginUserLanguageResource.unit,loginUserLanguageResource.dataSource,loginUserLanguageResource.totalType,loginUserLanguageResource.showLevel,loginUserLanguageResource.prec,loginUserLanguageResource.sumSign,loginUserLanguageResource.averageSign,loginUserLanguageResource.reportCurve,loginUserLanguageResource.curveStatType, loginUserLanguageResource.config];
                var columns = [
                    {data:'id'},
                    {data:'headerName'},
                    {data:'itemName'},
                    {data:'unit'},
                    {data:'dataSource'},
                    {data:'totalType'},
                    {data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,productionReportTemplateContentHandsontableHelper);}},
                    {data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,productionReportTemplateContentHandsontableHelper);}},
                    {data:'sumSign',type:'checkbox'},
                    {data:'averageSign',type:'checkbox'},
                    {data:'reportCurveConfShowValue'},
                    {data:'curveStatType',type:'dropdown',strict:true,allowInvalid:false,source:[loginUserLanguageResource.curveStatType_sum, loginUserLanguageResource.curveStatType_avg]},
                    {data: 'config',renderer: renderReportUnitContentConfig}
                    ];
                productionReportTemplateContentHandsontableHelper.colHeaders = colHeaders;
                productionReportTemplateContentHandsontableHelper.columns = columns;
                productionReportTemplateContentHandsontableHelper.createTable(result.totalRoot);
            } else {
                productionReportTemplateContentHandsontableHelper.hot.loadData(result.totalRoot);
            }
        },
        failure: function () {
            Ext.getCmp("ModbusProtocolProductionReportUnitContentConfigTableInfoPanel_Id").getEl().unmask();
            Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
        },
        params: {
            calculateType: calculateType,
            reportType: 1,
            unitId: unitId,
            templateCode: templateCode,
            classes: classes
        }
    });
};

var ProductionReportTemplateContentHandsontableHelper = {
    createNew: function (divid) {
        var productionReportTemplateContentHandsontableHelper = {};
        productionReportTemplateContentHandsontableHelper.hot1 = '';
        productionReportTemplateContentHandsontableHelper.divid = divid;
        productionReportTemplateContentHandsontableHelper.validresult = true; //数据校验
        productionReportTemplateContentHandsontableHelper.colHeaders = [];
        productionReportTemplateContentHandsontableHelper.columns = [];
        productionReportTemplateContentHandsontableHelper.AllData = [];

        productionReportTemplateContentHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            if (value != null) {
                var arr = value.split(';');
                if (arr.length == 3) {
                    td.style.backgroundColor = '#' + arr[2];
                }
            }
            td.style.whiteSpace='nowrap'; //文本不换行
        	td.style.overflow='hidden';//超出部分隐藏
        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
        }
        
        productionReportTemplateContentHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.whiteSpace='nowrap'; //文本不换行
        	td.style.overflow='hidden';//超出部分隐藏
        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
        }

        productionReportTemplateContentHandsontableHelper.createTable = function (data) {
            $('#' + productionReportTemplateContentHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + productionReportTemplateContentHandsontableHelper.divid);
            productionReportTemplateContentHandsontableHelper.hot = new Handsontable(hotElement, {
                licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                data: data,
                hiddenColumns: {
//                    columns: [12, 13, 14, 15],
                    indicators: false,
                    copyPasteEnabled: false
                },
                colWidths: [50, 150, 150, 60, 90, 120, 80, 60, 50, 50, 110, 110, 60],
                columns: productionReportTemplateContentHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: false, //显示行头
                colHeaders: productionReportTemplateContentHandsontableHelper.colHeaders, //显示列头
                columnSorting: true, //允许排序
                sortIndicator: true,
                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                filters: true,
                renderAllRows: true,
                search: true,
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
                    if (visualColIndex == 10) {
                        cellProperties.renderer = productionReportTemplateContentHandsontableHelper.addCurveBg;
                    }else{
                    	if(productionReportTemplateContentHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& productionReportTemplateContentHandsontableHelper.columns[visualColIndex].type!='checkbox'
	    	            		&& prop.toUpperCase() != "config".toUpperCase()){
	                    	cellProperties.renderer = productionReportTemplateContentHandsontableHelper.addCellStyle;
	    	            }
                    }
                    cellProperties.readOnly = true;
                    return cellProperties;
                },
                afterOnCellMouseOver: function(event, coords, TD){
                	if(coords.col>=0 && coords.row>=0 && productionReportTemplateContentHandsontableHelper.columns[coords.col].type!='checkbox' 
                		&& productionReportTemplateContentHandsontableHelper!=null
                		&& productionReportTemplateContentHandsontableHelper.hot!=''
                		&& productionReportTemplateContentHandsontableHelper.hot!=undefined 
                		&& productionReportTemplateContentHandsontableHelper.hot.getDataAtCell!=undefined){
                		var rawValue=productionReportTemplateContentHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
        productionReportTemplateContentHandsontableHelper.saveData = function () {}
        productionReportTemplateContentHandsontableHelper.clearContainer = function () {
            productionReportTemplateContentHandsontableHelper.AllData = [];
        }
        return productionReportTemplateContentHandsontableHelper;
    }
};

function SaveReportUnitData() {
    var reportUnitTreeSelectedRow = Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue();
    if (reportUnitTreeSelectedRow != '') {
        var selectedItem = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getStore().getAt(reportUnitTreeSelectedRow);
        
        if (selectedItem.data.classes == 1) { //选中的是单元
        	var saveType=0;
			if(Ext.getCmp("ReportUnitConfigRightTabPanel_Id").getActiveTab().id=='ReportUnitPropertiesConfigRightTabPanel_Id'){
				saveType=0;
			}else if(Ext.getCmp("ReportUnitConfigRightTabPanel_Id").getActiveTab().id=='ModbusProtocolReportUnitReportTemplateTabPanel_Id'){
				saveType=1;
			}
        	
			var reportUnitProperties = {};
			reportUnitProperties.classes = selectedItem.data.classes;
            reportUnitProperties.id = selectedItem.data.id;
            reportUnitProperties.unitCode = selectedItem.data.code;
			
            reportUnitProperties.singleWellRangeReportTemplate = selectedItem.data.singleWellRangeReportTemplate;
	         reportUnitProperties.singleWellDailyReportTemplate = selectedItem.data.singleWellDailyReportTemplate;
	         reportUnitProperties.productionReportTemplate = selectedItem.data.productionReportTemplate;
            
			if(saveType==0){
				var propertiesData = reportUnitPropertiesHandsontableHelper.hot.getData();
				 reportUnitProperties.unitName = isNotVal(propertiesData[0][2])?propertiesData[0][2]:"";
				 reportUnitProperties.calculateType = 0;
				 if (propertiesData[1][2] == loginUserLanguageResource.SRPCalculate) {
					 reportUnitProperties.calculateType = 1;
				 } else if (propertiesData[1][2] == loginUserLanguageResource.PCPCalculate) {
					 reportUnitProperties.calculateType = 2;
		         }
		         reportUnitProperties.sort = isNotVal(propertiesData[2][2])?propertiesData[2][2]:"";
			}else if(saveType==1){
				reportUnitProperties.unitName=selectedItem.data.text;
				reportUnitProperties.calculateType=selectedItem.data.calculateType;
				reportUnitProperties.sort=selectedItem.data.sort;
				
				var tabPanel = Ext.getCmp("ModbusProtocolReportUnitReportConfigPanel_Id");
	            var activeId = tabPanel.getActiveTab().id;
	            if (activeId == "ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id") {
	                var singleWellReportActiveId = Ext.getCmp("ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id").getActiveTab().id;
	                if (singleWellReportActiveId == 'ModbusProtocolReportUnitSingleWellDailyReportTemplatePanel_Id') {
	                    var templateSelection = Ext.getCmp("ReportUnitSingleWellDailyReportTemplateListGridPanel_Id").getSelectionModel().getSelection();
	                    if (templateSelection.length > 0) {
	                        reportUnitProperties.singleWellDailyReportTemplate = templateSelection[0].data.templateCode;
	                    }else{
	                    	reportUnitProperties.singleWellDailyReportTemplate="";
	                    }
	                } else if (singleWellReportActiveId == 'ModbusProtocolReportUnitSingleWellRangeReportTemplatePanel_Id') {
	                    var templateSelection = Ext.getCmp("ReportUnitSingleWellRangeReportTemplateListGridPanel_Id").getSelectionModel().getSelection();
	                    if (templateSelection.length > 0) {
	                        reportUnitProperties.singleWellRangeReportTemplate = templateSelection[0].data.templateCode;
	                    }else{
	                    	reportUnitProperties.singleWellRangeReportTemplate = "";
	                    }
	                }
	            } else if (activeId == "ModbusProtocolReportUnitProductionReportTemplatePanel_Id") {
	                var templateSelection = Ext.getCmp("ReportUnitProductionReportTemplateListGridPanel_Id").getSelectionModel().getSelection();
	                if (templateSelection.length > 0) {
	                    reportUnitProperties.productionReportTemplate = templateSelection[0].data.templateCode;
	                }else{
	                	reportUnitProperties.productionReportTemplate="";
	                }
	            }
			}
            
            SaveModbusProtocolReportUnitData(reportUnitProperties);
        }
    }
};

function SaveModbusProtocolReportUnitData(saveData) {
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/acquisitionUnitManagerController/saveProtocolReportUnitData',
        success: function (response) {
            data = Ext.JSON.decode(response.responseText);
            if (data.success) {
                if (saveData.delidslist != undefined && saveData.delidslist.length > 0) {
                    Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").setValue(0);
                    Ext.getCmp("ReportUnitTreeSelectUnitId_Id").setValue(0);
                    Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.deleteSuccessfully);
                }else{
                	Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.saveSuccessfully);
                }
                Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getStore().load();
            } else {
                Ext.MessageBox.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
            }
        },
        failure: function () {
            Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.requestFailure);
        },
        params: {
            data: JSON.stringify(saveData),
        }
    });
}


var grantReportUnitContentItemsPermission2 = function (unitId, reportType, calculateType, sort, itemsConfigData) {
    var addUrl = context + '/acquisitionUnitManagerController/grantReportUnitContentItemsPermission';
    // 添加条件
    var saveData = {};
    saveData.itemList = [];
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (!isNotVal(unitId)) {
        return false
    }
    Ext.Array.each(itemsConfigData, function (name, index, countriesItSelf) {
        if ((itemsConfigData[index][0] + '') === 'true') {
            var item = {};
            item.matrix = '0,0,0';
            if (reportType == 0) {
                item.itemName = itemsConfigData[index][2];
                item.totalType = 0;
                item.dataSource = itemsConfigData[index][4];
                item.itemShowLevel = itemsConfigData[index][6];
                item.itemSort = sort;

                var reportCurveConfig = null;
                var reportCurveConfigStr = "";
                if (isNotVal(itemsConfigData[index][8]) && isNotVal(itemsConfigData[index][9])) {
                    reportCurveConfig = itemsConfigData[index][9];
                    reportCurveConfigStr = JSON.stringify(reportCurveConfig);
                }


                item.reportCurveConf = reportCurveConfigStr;

                item.itemCode = itemsConfigData[index][10];
                item.dataType = itemsConfigData[index][11];

                if (item.dataType == 2) {
                    item.itemPrec = itemsConfigData[index][8];

                    if (item.dataSource == loginUserLanguageResource.acquisition) {
                        if (itemsConfigData[index][5] == loginUserLanguageResource.maxValue) {
                            item.totalType = 1;
                        } else if (itemsConfigData[index][5] == loginUserLanguageResource.minValue) {
                            item.totalType = 2;
                        } else if (itemsConfigData[index][5] == loginUserLanguageResource.avgValue) {
                            item.totalType = 3;
                        } else if (itemsConfigData[index][5] == loginUserLanguageResource.newestValue) {
                            item.totalType = 4;
                        } else if (itemsConfigData[index][5] == loginUserLanguageResource.oldestValue) {
                            item.totalType = 5;
                        } else if (itemsConfigData[index][5] == loginUserLanguageResource.dailyTotalValue) {
                            item.totalType = 6;
                        }
                    }
                }
            } else if (reportType == 2) {
                item.itemName = itemsConfigData[index][2];
                item.totalType = 0;
                item.dataSource = itemsConfigData[index][4];
                item.itemShowLevel = itemsConfigData[index][6];
                item.itemSort = itemsConfigData[index][7];

                var reportCurveConfig = null;
                var reportCurveConfigStr = "";
                if (isNotVal(itemsConfigData[index][9]) && isNotVal(itemsConfigData[index][10])) {
                    reportCurveConfig = itemsConfigData[index][10];
                    reportCurveConfigStr = JSON.stringify(reportCurveConfig);
                }


                item.reportCurveConf = reportCurveConfigStr;

                item.itemCode = itemsConfigData[index][11];
                item.dataType = itemsConfigData[index][12];

                if (item.dataType == 2) {
                    item.itemPrec = itemsConfigData[index][8];

                    if (item.dataSource == loginUserLanguageResource.acquisition) {
                        if (itemsConfigData[index][5] == loginUserLanguageResource.maxValue) {
                            item.totalType = 1;
                        } else if (itemsConfigData[index][5] == loginUserLanguageResource.minValue) {
                            item.totalType = 2;
                        } else if (itemsConfigData[index][5] == loginUserLanguageResource.avgValue) {
                            item.totalType = 3;
                        } else if (itemsConfigData[index][5] == loginUserLanguageResource.newestValue) {
                            item.totalType = 4;
                        } else if (itemsConfigData[index][5] == loginUserLanguageResource.oldestValue) {
                            item.totalType = 5;
                        } else if (itemsConfigData[index][5] == loginUserLanguageResource.dailyTotalValue) {
                            item.totalType = 6;
                        }
                    }
                }
            } else if (reportType == 1) {
                item.itemName = itemsConfigData[index][2];

                item.totalType = 0;
                item.dataSource = itemsConfigData[index][4];
                item.itemShowLevel = itemsConfigData[index][6];
                item.itemSort = itemsConfigData[index][7];

                item.sumSign = '0';
                if ((itemsConfigData[index][9] + '') === 'true') {
                    item.sumSign = '1';
                }

                item.averageSign = '0';
                if ((itemsConfigData[index][10] + '') === 'true') {
                    item.averageSign = '1';
                }

                var reportCurveConfig = null;
                var reportCurveConfigStr = "";
                if (isNotVal(itemsConfigData[index][11]) && isNotVal(itemsConfigData[index][13])) {
                    reportCurveConfig = itemsConfigData[index][13];
                    reportCurveConfigStr = JSON.stringify(reportCurveConfig);
                }


                item.reportCurveConf = reportCurveConfigStr;

                item.curveStatType = itemsConfigData[index][12];
                if (itemsConfigData[index][12] == loginUserLanguageResource.curveStatType_sum) {
                    item.curveStatType = '1';
                } else if (itemsConfigData[index][12] == loginUserLanguageResource.curveStatType_avg) {
                    item.curveStatType = '2';
                } else if (itemsConfigData[index][12] == loginUserLanguageResource.curveStatType_max) {
                    item.curveStatType = '3';
                } else if (itemsConfigData[index][12] == loginUserLanguageResource.curveStatType_min) {
                    item.curveStatType = '4';
                }

                item.itemCode = itemsConfigData[index][14];
                item.dataType = itemsConfigData[index][15] + "";

                if (item.dataType == 2) {
                    item.itemPrec = itemsConfigData[index][8];

                    if (item.dataSource == loginUserLanguageResource.acquisition) {
                        if (itemsConfigData[index][5] == loginUserLanguageResource.maxValue) {
                            item.totalType = 1;
                        } else if (itemsConfigData[index][5] == loginUserLanguageResource.minValue) {
                            item.totalType = 2;
                        } else if (itemsConfigData[index][5] == loginUserLanguageResource.avgValue) {
                            item.totalType = 3;
                        } else if (itemsConfigData[index][5] == loginUserLanguageResource.newestValue) {
                            item.totalType = 4;
                        } else if (itemsConfigData[index][5] == loginUserLanguageResource.oldestValue) {
                            item.totalType = 5;
                        } else if (itemsConfigData[index][5] == loginUserLanguageResource.dailyTotalValue) {
                            item.totalType = 6;
                        }
                    }
                }
            }
            saveData.itemList.push(item);
        }
    });
    Ext.Ajax.request({
        url: addUrl,
        method: "POST",
        params: {
            unitId: unitId,
            reportType: reportType,
            calculateType: calculateType,
            sort: sort,
            saveData: JSON.stringify(saveData)
        },
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (result.msg == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
                if (reportType == 0) {
                    CreateReportUnitContentConfigColInfoTable();
                    CreateReportUnitContentConfigTable();
                    CreateSingleWellRangeReportTotalItemsInfoTable();
                } else if (reportType == 1) {

                } else if (reportType == 2) {

                }


            }
            if (result.msg == false) {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>SORRY！" + loginUserLanguageResource.saveFailure + "</font>");
            }
        },
        failure: function () {
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + " </font>】:" + loginUserLanguageResource.contactAdmin);
        }
    });
    return false;
}


var grantReportTotalCalItemsPermission = function (calculateType) {
    var reportUnitTreeSelectedRow = Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue();
    var reportType = 0;
    var calItemsData = null;
    var tabPanel = Ext.getCmp("ModbusProtocolReportUnitReportConfigPanel_Id");
    var activeId = tabPanel.getActiveTab().id;
    if (activeId == "ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id") {
        var singleWellReportActiveId = Ext.getCmp("ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id").getActiveTab().id;
        if (singleWellReportActiveId == 'ModbusProtocolReportUnitSingleWellDailyReportTemplatePanel_Id') {
            if (singleWellDailyReportTemplateContentHandsontableHelper == null || reportUnitTreeSelectedRow == '') {
                return false;
            }
            reportType = 2;
            calItemsData = singleWellDailyReportTemplateContentHandsontableHelper.hot.getData();
        } else if (singleWellReportActiveId == 'ModbusProtocolReportUnitSingleWellRangeReportTemplatePanel_Id') {
            if (singleWellRangeReportTemplateContentHandsontableHelper == null || reportUnitTreeSelectedRow == '') {
                return false;
            }
            reportType = 0;
            calItemsData = singleWellRangeReportTemplateContentHandsontableHelper.hot.getData();
        }
    } else if (activeId == "ModbusProtocolReportUnitProductionReportTemplatePanel_Id") {
        if (productionReportTemplateContentHandsontableHelper == null || reportUnitTreeSelectedRow == '') {
            return false;
        }
        reportType = 1;
        calItemsData = productionReportTemplateContentHandsontableHelper.hot.getData();
    }

    var selectedItem = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getStore().getAt(reportUnitTreeSelectedRow);
    var addUrl = context + '/acquisitionUnitManagerController/grantTotalCalItemsToReportUnitPermission';

    // 添加条件
    var saveData = {};
    saveData.itemList = [];
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;

    var unitId = selectedItem.data.id;
    if (!isNotVal(unitId)) {
        return false
    }

    Ext.Array.each(calItemsData, function (name, index, countriesItSelf) {
        if ((calItemsData[index][0] + '') === 'true') {
            var item = {};
            item.matrix = '0,0,0';
            if (reportType == 0) {
                item.itemName = calItemsData[index][2];
                item.totalType = 0;
                item.dataSource = calItemsData[index][4];
                item.itemShowLevel = calItemsData[index][6];
                item.itemSort = calItemsData[index][7];

                var reportCurveConfig = null;
                var reportCurveConfigStr = "";
                if (isNotVal(calItemsData[index][9]) && isNotVal(calItemsData[index][10])) {
                    reportCurveConfig = calItemsData[index][10];
                    reportCurveConfigStr = JSON.stringify(reportCurveConfig);
                }


                item.reportCurveConf = reportCurveConfigStr;

                item.itemCode = calItemsData[index][11];
                item.dataType = calItemsData[index][12];

                if (item.dataType == 2) {
                    item.itemPrec = calItemsData[index][8];

                    if (item.dataSource == loginUserLanguageResource.acquisition) {
                        if (calItemsData[index][5] == loginUserLanguageResource.maxValue) {
                            item.totalType = 1;
                        } else if (calItemsData[index][5] == loginUserLanguageResource.minValue) {
                            item.totalType = 2;
                        } else if (calItemsData[index][5] == loginUserLanguageResource.avgValue) {
                            item.totalType = 3;
                        } else if (calItemsData[index][5] == loginUserLanguageResource.newestValue) {
                            item.totalType = 4;
                        } else if (calItemsData[index][5] == loginUserLanguageResource.oldestValue) {
                            item.totalType = 5;
                        } else if (calItemsData[index][5] == loginUserLanguageResource.dailyTotalValue) {
                            item.totalType = 6;
                        }
                    }
                }
            } else if (reportType == 2) {
                item.itemName = calItemsData[index][2];
                item.totalType = 0;
                item.dataSource = calItemsData[index][4];
                item.itemShowLevel = calItemsData[index][6];
                item.itemSort = calItemsData[index][7];

                var reportCurveConfig = null;
                var reportCurveConfigStr = "";
                if (isNotVal(calItemsData[index][9]) && isNotVal(calItemsData[index][10])) {
                    reportCurveConfig = calItemsData[index][10];
                    reportCurveConfigStr = JSON.stringify(reportCurveConfig);
                }


                item.reportCurveConf = reportCurveConfigStr;

                item.itemCode = calItemsData[index][11];
                item.dataType = calItemsData[index][12];

                if (item.dataType == 2) {
                    item.itemPrec = calItemsData[index][8];

                    if (item.dataSource == loginUserLanguageResource.acquisition) {
                        if (calItemsData[index][5] == loginUserLanguageResource.maxValue) {
                            item.totalType = 1;
                        } else if (calItemsData[index][5] == loginUserLanguageResource.minValue) {
                            item.totalType = 2;
                        } else if (calItemsData[index][5] == loginUserLanguageResource.avgValue) {
                            item.totalType = 3;
                        } else if (calItemsData[index][5] == loginUserLanguageResource.newestValue) {
                            item.totalType = 4;
                        } else if (calItemsData[index][5] == loginUserLanguageResource.oldestValue) {
                            item.totalType = 5;
                        } else if (calItemsData[index][5] == loginUserLanguageResource.dailyTotalValue) {
                            item.totalType = 6;
                        }
                    }
                }
            } else if (reportType == 1) {
                item.itemName = calItemsData[index][2];

                item.totalType = 0;
                item.dataSource = calItemsData[index][4];
                item.itemShowLevel = calItemsData[index][6];
                item.itemSort = calItemsData[index][7];

                item.sumSign = '0';
                if ((calItemsData[index][9] + '') === 'true') {
                    item.sumSign = '1';
                }

                item.averageSign = '0';
                if ((calItemsData[index][10] + '') === 'true') {
                    item.averageSign = '1';
                }

                var reportCurveConfig = null;
                var reportCurveConfigStr = "";
                if (isNotVal(calItemsData[index][11]) && isNotVal(calItemsData[index][13])) {
                    reportCurveConfig = calItemsData[index][13];
                    reportCurveConfigStr = JSON.stringify(reportCurveConfig);
                }


                item.reportCurveConf = reportCurveConfigStr;

                item.curveStatType = calItemsData[index][12];
                if (calItemsData[index][12] == loginUserLanguageResource.curveStatType_sum) {
                    item.curveStatType = '1';
                } else if (calItemsData[index][12] == loginUserLanguageResource.curveStatType_avg) {
                    item.curveStatType = '2';
                } else if (calItemsData[index][12] == loginUserLanguageResource.curveStatType_max) {
                    item.curveStatType = '3';
                } else if (calItemsData[index][12] == loginUserLanguageResource.curveStatType_min) {
                    item.curveStatType = '4';
                }

                item.itemCode = calItemsData[index][14];
                item.dataType = calItemsData[index][15] + "";

                if (item.dataType == 2) {
                    item.itemPrec = calItemsData[index][8];

                    if (item.dataSource == loginUserLanguageResource.acquisition) {
                        if (calItemsData[index][5] == loginUserLanguageResource.maxValue) {
                            item.totalType = 1;
                        } else if (calItemsData[index][5] == loginUserLanguageResource.minValue) {
                            item.totalType = 2;
                        } else if (calItemsData[index][5] == loginUserLanguageResource.avgValue) {
                            item.totalType = 3;
                        } else if (calItemsData[index][5] == loginUserLanguageResource.newestValue) {
                            item.totalType = 4;
                        } else if (calItemsData[index][5] == loginUserLanguageResource.oldestValue) {
                            item.totalType = 5;
                        } else if (calItemsData[index][5] == loginUserLanguageResource.dailyTotalValue) {
                            item.totalType = 6;
                        }
                    }
                }
            }
            saveData.itemList.push(item);
        }
    });
    Ext.Ajax.request({
        url: addUrl,
        method: "POST",
        params: {
            unitId: unitId,
            reportType: reportType,
            calculateType: calculateType,
            saveData: JSON.stringify(saveData)
        },
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (result.msg == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            }
            if (result.msg == false) {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>SORRY！" + loginUserLanguageResource.saveFailure + "</font>");
            }
        },
        failure: function () {
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + " </font>】:" + loginUserLanguageResource.contactAdmin);
        }
    });
    return false;
}

function getReportType() {
    var reportType = 0;
    var tabPanel = Ext.getCmp("ModbusProtocolReportUnitReportConfigPanel_Id");
    var activeId = tabPanel.getActiveTab().id;
    if (activeId == "ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id") {
        var singleWellReportActiveId = Ext.getCmp("ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id").getActiveTab().id;
        if (singleWellReportActiveId == 'ModbusProtocolReportUnitSingleWellDailyReportTemplatePanel_Id') {
            reportType = 2;
        } else if (singleWellReportActiveId == 'ModbusProtocolReportUnitSingleWellRangeReportTemplatePanel_Id') {
            reportType = 0;
        }
    } else if (activeId == "ModbusProtocolReportUnitProductionReportTemplatePanel_Id") {
        reportType = 1;
    }
    return reportType;
}