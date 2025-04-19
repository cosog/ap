//var protocolDisplayUnitAcqItemsConfigHandsontableHelper=null;
//var protocolDisplayUnitCalItemsConfigHandsontableHelper=null;
//var protocolDisplayUnitCtrlItemsConfigHandsontableHelper=null;
//var protocolDisplayUnitInputItemsConfigHandsontableHelper=null;
//var protocolDisplayUnitPropertiesHandsontableHelper=null;
Ext.define('AP.view.acquisitionUnit.ModbusProtocolDisplayUnitConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolDisplayUnitConfigInfoView',
    layout: "fit",
    id:'modbusProtocolDisplayUnitConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	Ext.apply(me, {
    		items: [{
            	tbar: [{
                    id: 'ModbusProtocolDisplayUnitConfigSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    xtype: 'button',
                    text: loginUserLanguageResource.refresh,
                    iconCls: 'note-refresh',
                    hidden:false,
                    handler: function (v, o) {
                    	var treeGridPanel = Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id");
                        if (isNotVal(treeGridPanel)) {
                        	treeGridPanel.getStore().load();
                        }else{
                        	Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayUnitTreeInfoStore');
                        }
                    }
        		},'->',{
        			xtype: 'button',
                    text: loginUserLanguageResource.addDisplayUnit,
                    disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                    iconCls: 'add',
                    handler: function (v, o) {
                    	addDisplayUnitInfo();
        			}
        		},"-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.save,
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'save',
        			handler: function (v, o) {
        				SaveModbusProtocolDisplayUnitConfigTreeData();
        			}
                },"-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.exportData,
        			iconCls: 'export',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.acquisitionUnit.ExportProtocolDisplayUnitWindow");
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
        				var window = Ext.create("AP.view.acquisitionUnit.ImportDisplayUnitWindow");
                        window.show();
        				Ext.getCmp("ImportDisplayUnitWinTabLabel_Id").setHtml("单元将导入到【<font color=red>"+selectedDeviceTypeName+"</font>】标签下,"+loginUserLanguageResource.pleaseConfirm+"<br/>&nbsp;");
//        			    Ext.getCmp("ImportAlarmUnitWinTabLabel_Id").show();
        			    
        			    Ext.getCmp('ImportDisplayUnitWinDeviceType_Id').setValue(selectedDeviceTypeId);
        				
        			}
                }],
                layout: "border",
                items: [{
                	border: true,
                	region: 'west',
                	width:'20%',
                    layout: "border",
                    border: true,
                    header: false,
                    split: true,
                    collapsible: true,
                    collapseDirection: 'left',
                    hideMode:'offsets',
                    items: [{
                    	region: 'center',
                    	title:loginUserLanguageResource.displayUnitConfig,
                    	layout: 'fit',
                    	id:"ModbusProtocolDisplayUnitConfigPanel_Id"
                    },{
                    	region: 'south',
                    	height:'42%',
                    	title:loginUserLanguageResource.properties,
                    	collapsible: true,
                        split: true,
                    	layout: 'fit',
                        html:'<div class="ModbusProtocolDisplayUnitPropertiesTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolDisplayUnitPropertiesTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolDisplayUnitPropertiesHandsontableHelper!=null && protocolDisplayUnitPropertiesHandsontableHelper.hot!=undefined){
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolDisplayUnitPropertiesHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                    }]
                },{
                	border: true,
            		region: 'center',
            		layout: "border",
            		items: [{
                		region: 'center',
                		title:loginUserLanguageResource.acquisitionItemConfig,
                		tbar:[{
                            xtype: 'button',
                            text: loginUserLanguageResource.selectAll,
                            id: 'displayUnitAcqItemConfigSelectAllBtn',
                            disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                            pressed: false,
                            handler: function (v, o) {
                            	var rowCount = protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.countRows();
                            	var updateData=[];
                            	var selected=true;
                                for(var i=0;i<rowCount;i++){
                                	updateData.push([i,'realtimeData',selected]);
                                	updateData.push([i,'historyData',selected]);
                                }
                                protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.setDataAtRowProp(updateData);
                            }
                        },{
                            xtype: 'button',
                            text: loginUserLanguageResource.deselectAll,
                            id: 'displayUnitAcqItemConfigDeselectAllBtn',
                            disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                            pressed: false,
                            handler: function (v, o) {
                            	var rowCount = protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.countRows();
                            	var updateData=[];
                            	var selected=false;
                                for(var i=0;i<rowCount;i++){
                                	updateData.push([i,'realtimeData',selected]);
                                	updateData.push([i,'historyData',selected]);
                                }
                                protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.setDataAtRowProp(updateData);
                            }
                        }],
                		id:"ModbusProtocolDisplayUnitAcqItemsConfigTableInfoPanel_Id",
                        layout: 'fit',
                        html:'<div class="ModbusProtocolDisplayUnitAcqItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolDisplayUnitAcqItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolDisplayUnitAcqItemsConfigHandsontableHelper!=null && protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot!=undefined){
                            		var newWidth=width;
                            		var newHeight=height-22-1;//减去tbar
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                	},{
                		region: 'south',
                    	height:'50%',
                    	title:loginUserLanguageResource.controlItemConfig,
                    	tbar:[{
                            xtype: 'button',
                            text: loginUserLanguageResource.selectAll,
                            id: 'displayUnitCtrlItemConfigSelectAllBtn',
                            disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                            pressed: false,
                            handler: function (v, o) {
                            	var rowCount = protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.countRows();
                            	var updateData=[];
                            	var selected=true;
                                for(var i=0;i<rowCount;i++){
                                	var data=[i,'checked',selected];
                                	updateData.push(data);
                                }
                                protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.setDataAtRowProp(updateData);
                            }
                        },{
                            xtype: 'button',
                            text: loginUserLanguageResource.deselectAll,
                            id: 'displayUnitCtrlItemConfigDeselectAllBtn',
                            disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                            pressed: false,
                            handler: function (v, o) {
                            	var rowCount = protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.countRows();
                            	var updateData=[];
                            	var selected=false;
                                for(var i=0;i<rowCount;i++){
                                	var data=[i,'checked',selected];
                                	updateData.push(data);
                                }
                                protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.setDataAtRowProp(updateData);
                            }
                        }],
                		id:"ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoPanel_Id",
                        layout: 'fit',
                        collapsible: true,
                        split: true,
                        html:'<div class="ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolDisplayUnitCtrlItemsConfigHandsontableHelper!=null && protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot!=undefined){
                            		var newWidth=width;
                            		var newHeight=height-22-1;//减去tbar
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                	}]
                }]
            }]
    	});
        this.callParent(arguments);
    }
});

function CreateProtocolDisplayUnitAcqItemsConfigInfoTable(protocolName, classes, code, unitId, acqUnitId, unitName,calculateType) {
    Ext.getCmp("ModbusProtocolDisplayUnitAcqItemsConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/acquisitionUnitManagerController/getProtocolDisplayUnitAcqItemsConfigData',
        success: function (response) {
            Ext.getCmp("ModbusProtocolDisplayUnitAcqItemsConfigTableInfoPanel_Id").getEl().unmask();
            var result = Ext.JSON.decode(response.responseText);
            if (classes == 2) {
                Ext.getCmp("ModbusProtocolDisplayUnitAcqItemsConfigTableInfoPanel_Id").setTitle(unitName + '/'+loginUserLanguageResource.acquisitionItemConfig);
            } else {
                Ext.getCmp("ModbusProtocolDisplayUnitAcqItemsConfigTableInfoPanel_Id").setTitle(loginUserLanguageResource.acquisitionItemConfig);
            }
            if (protocolDisplayUnitAcqItemsConfigHandsontableHelper == null || protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot == undefined) {
                protocolDisplayUnitAcqItemsConfigHandsontableHelper = ProtocolDisplayUnitAcqItemsConfigHandsontableHelper.createNew("ModbusProtocolDisplayUnitAcqItemsConfigTableInfoDiv_id");
                var colHeaders = "[" 
                	+"['','','','','','',{label: '"+loginUserLanguageResource.realtimeMonitoring+"', colspan: 7},{label: '"+loginUserLanguageResource.historyQuery+"', colspan: 7},'','','','','','','']," 
                	+"['','','','','','',{label: '"+loginUserLanguageResource.deviceOverview+"', colspan: 2},{label: '"+loginUserLanguageResource.dynamicData+"', colspan: 4},'"+loginUserLanguageResource.trendCurve+"',{label: '"+loginUserLanguageResource.deviceOverview+"', colspan: 2},{label: '"+loginUserLanguageResource.historyData+"', colspan: 4},'"+loginUserLanguageResource.trendCurve+"','','','','','','','']," 
                	+"['','"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.dataSource+"','"+loginUserLanguageResource.unit+"','"+loginUserLanguageResource.showLevel+"'," 
                	
                	+"'"+loginUserLanguageResource.deviceOverview+"','"+loginUserLanguageResource.columnSort+"',"
                	+"'"+loginUserLanguageResource.dynamicData+"','"+loginUserLanguageResource.columnSort+"','"+loginUserLanguageResource.foregroundColor+"','"+loginUserLanguageResource.backgroundColor+"','"+loginUserLanguageResource.curveConfig+"'," 
                	
                	
                	+"'"+loginUserLanguageResource.deviceOverview+"','"+loginUserLanguageResource.columnSort+"',"
                	+"'"+loginUserLanguageResource.historyData+"','"+loginUserLanguageResource.columnSort+"','"+loginUserLanguageResource.foregroundColor+"','"+loginUserLanguageResource.backgroundColor+"','"+loginUserLanguageResource.curveConfig+"'," 
                	+"'','','','','','','']"
                	+"]";
                var columns = "[" 
                    +"{data:'checked',type:'checkbox'}," 
                    +"{data:'id'}," 
                    +"{data:'title'}," 
                    +"{data:'dataSource'}," 
                    +"{data:'unit'}," 
                    +"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
                    
                    +"{data:'realtimeOverview',type:'checkbox'}," 
                    +"{data:'realtimeOverviewSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
                    
                    +"{data:'realtimeData',type:'checkbox'}," 
                    +"{data:'realtimeSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
                    +"{data:'realtimeColor'}," 
                    +"{data:'realtimeBgColor'}," 
                    +"{data:'realtimeCurveConfShowValue'}," //12
                    
                    +"{data:'historyOverview',type:'checkbox'}," 
                    +"{data:'historyOverviewSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
                    +"{data:'historyData',type:'checkbox'}," 
                    +"{data:'historySort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
                    +"{data:'historyColor'}," 
                    +"{data:'historyBgColor'}," 
                    +"{data:'historyCurveConfShowValue'}," //19
                    +"{data:'realtimeCurveConf'}," 
                    +"{data:'historyCurveConf'}," 
                    +"{data:'resolutionMode',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.switchingValue+"', '"+loginUserLanguageResource.enumValue+"','"+loginUserLanguageResource.numericValue+"']}," 
                    +"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
                    +"{data:'bitIndex'}," 
                    +"{data:'type'}," 
                    +"{data:'code'}" 
                    +"]";

                protocolDisplayUnitAcqItemsConfigHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
                protocolDisplayUnitAcqItemsConfigHandsontableHelper.columns = Ext.JSON.decode(columns);
                protocolDisplayUnitAcqItemsConfigHandsontableHelper.createTable(result.totalRoot);
            } else {
                protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.loadData(result.totalRoot);
            }
        },
        failure: function () {
            Ext.getCmp("ModbusProtocolDisplayUnitAcqItemsConfigTableInfoPanel_Id").getEl().unmask();
            Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
        },
        params: {
            protocolName: protocolName,
            classes: classes,
            code: code,
            unitId: unitId,
            acqUnitId: acqUnitId,
            calculateType: calculateType
        }
    });
};

var ProtocolDisplayUnitAcqItemsConfigHandsontableHelper = {
    createNew: function (divid) {
        var protocolDisplayUnitAcqItemsConfigHandsontableHelper = {};
        protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot1 = '';
        protocolDisplayUnitAcqItemsConfigHandsontableHelper.divid = divid;
        protocolDisplayUnitAcqItemsConfigHandsontableHelper.validresult = true; //数据校验
        protocolDisplayUnitAcqItemsConfigHandsontableHelper.colHeaders = [];
        protocolDisplayUnitAcqItemsConfigHandsontableHelper.columns = [];
        protocolDisplayUnitAcqItemsConfigHandsontableHelper.AllData = [];

        protocolDisplayUnitAcqItemsConfigHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            if (value!=null && value!="") {
                var arr = (value+"").split(';');
                if (arr.length == 3) {
                    td.style.backgroundColor = '#' + arr[2];
                }
            }
            td.style.whiteSpace = 'nowrap'; //文本不换行
            td.style.overflow = 'hidden'; //超出部分隐藏
            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
        }
        
        protocolDisplayUnitAcqItemsConfigHandsontableHelper.addReadOnlyCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            var bg='rgb(245, 245, 245)';
            if (value!=null && value!="") {
                var arr = (value+"").split(';');
                if (arr.length == 3) {
                	bg = '#' + arr[2];
                }
            }
            td.style.backgroundColor = bg;
            td.style.whiteSpace = 'nowrap'; //文本不换行
            td.style.overflow = 'hidden'; //超出部分隐藏
            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
        }

        protocolDisplayUnitAcqItemsConfigHandsontableHelper.addCellBgColor = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            if (value!=null && value!="") {
                td.style.backgroundColor = '#' + value;
            }
            td.style.whiteSpace = 'nowrap'; //文本不换行
            td.style.overflow = 'hidden'; //超出部分隐藏
            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
        }
        
        protocolDisplayUnitAcqItemsConfigHandsontableHelper.addReadOnlyCellBgColor = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            if (value!=null && value!="") {
                td.style.backgroundColor = '#' + value;
            }else{
            	td.style.backgroundColor = 'rgb(245, 245, 245)';
            }
            td.style.whiteSpace = 'nowrap'; //文本不换行
            td.style.overflow = 'hidden'; //超出部分隐藏
            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
        }

        protocolDisplayUnitAcqItemsConfigHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.whiteSpace = 'nowrap'; //文本不换行
            td.style.overflow = 'hidden'; //超出部分隐藏
            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
        }
        
        protocolDisplayUnitAcqItemsConfigHandsontableHelper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
        	if(protocolDisplayUnitAcqItemsConfigHandsontableHelper.columns[col].type=='checkbox'){
        		protocolDisplayUnitAcqItemsConfigHandsontableHelper.addCheckboxReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
        	}else if(protocolDisplayUnitAcqItemsConfigHandsontableHelper.columns[col].type=='dropdown'){
        		protocolDisplayUnitAcqItemsConfigHandsontableHelper.addDropdownReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
        	}else{
        		protocolDisplayUnitAcqItemsConfigHandsontableHelper.addTextReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
        	}
        }
        
        protocolDisplayUnitAcqItemsConfigHandsontableHelper.addCheckboxReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
            td.style.backgroundColor = 'rgb(245, 245, 245)';
        }
        
        protocolDisplayUnitAcqItemsConfigHandsontableHelper.addDropdownReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
            td.style.backgroundColor = 'rgb(245, 245, 245)';
            td.style.whiteSpace='nowrap'; //文本不换行
        	td.style.overflow='hidden';//超出部分隐藏
        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
        }
        
        protocolDisplayUnitAcqItemsConfigHandsontableHelper.addTextReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(245, 245, 245)';
            td.style.whiteSpace='nowrap'; //文本不换行
        	td.style.overflow='hidden';//超出部分隐藏
        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
        }

        protocolDisplayUnitAcqItemsConfigHandsontableHelper.createTable = function (data) {
            $('#' + protocolDisplayUnitAcqItemsConfigHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + protocolDisplayUnitAcqItemsConfigHandsontableHelper.divid);
            protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot = new Handsontable(hotElement, {
                licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                data: data,
                hiddenColumns: {
                    columns: [0,6,7,13,14,20, 21, 22, 23, 24, 25, 26],
                    indicators: false,
                    copyPasteEnabled: false
                },
                colWidths: [25, 50, 140, 80, 80, 80, 80, 80, 60, 80, 80, 80, 80, 80, 80, 100, 80, 80, 80, 100],
                columns: protocolDisplayUnitAcqItemsConfigHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: false, //显示行头
//                colHeaders: protocolDisplayUnitAcqItemsConfigHandsontableHelper.colHeaders, //显示列头
                nestedHeaders:protocolDisplayUnitAcqItemsConfigHandsontableHelper.colHeaders,//显示列头
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
                    var protocolConfigModuleEditFlag = parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
                    if (protocolConfigModuleEditFlag == 1) {
                        var ScadaDriverModbusConfigSelectRow = Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
                        if (ScadaDriverModbusConfigSelectRow != '') {
                            var selectedItem = Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
                            if (selectedItem.data.classes != 2) {
                                cellProperties.readOnly = true;
                                cellProperties.renderer=protocolDisplayUnitAcqItemsConfigHandsontableHelper.addReadOnlyBg;
                            } else {
                                if (visualColIndex >= 1 && visualColIndex <= 4) {
                                    cellProperties.readOnly = true;
                                    cellProperties.renderer=protocolDisplayUnitAcqItemsConfigHandsontableHelper.addReadOnlyBg;
                                }else if (visualColIndex == 12 || visualColIndex == 19) {
                                    cellProperties.renderer = protocolDisplayUnitAcqItemsConfigHandsontableHelper.addCurveBg;
                                } else if (visualColIndex == 10 || visualColIndex == 11 || visualColIndex == 17 || visualColIndex == 18) {
                                    cellProperties.renderer = protocolDisplayUnitAcqItemsConfigHandsontableHelper.addCellBgColor;
                                } else {
                                    if (protocolDisplayUnitAcqItemsConfigHandsontableHelper.columns[visualColIndex].type != 'dropdown' &&
                                        protocolDisplayUnitAcqItemsConfigHandsontableHelper.columns[visualColIndex].type != 'checkbox') {
                                        cellProperties.renderer = protocolDisplayUnitAcqItemsConfigHandsontableHelper.addCellStyle;
                                    }
                                }
                            }
                        }
                    } else {
                        cellProperties.readOnly = true;
                        if (visualColIndex == 12 || visualColIndex == 19) {
                            cellProperties.renderer = protocolDisplayUnitAcqItemsConfigHandsontableHelper.addReadOnlyCurveBg;
                        } else if (visualColIndex == 10 || visualColIndex == 11 || visualColIndex == 17 || visualColIndex == 18) {
                            cellProperties.renderer = protocolDisplayUnitAcqItemsConfigHandsontableHelper.addReadOnlyCellBgColor;
                        } else {
                        	cellProperties.renderer=protocolDisplayUnitAcqItemsConfigHandsontableHelper.addReadOnlyBg;
                        }
                    }

                    return cellProperties;
                },
                afterBeginEditing: function (row, column) {
                    var row1 = protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRow(row);
                    if (row1[0] && (column == 12 || column == 19)) {
                        var ScadaDriverModbusConfigSelectRow = Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
                        if (ScadaDriverModbusConfigSelectRow != '') {
                            var selectedItem = Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
                            if (selectedItem.data.classes == 2) {
                                var CurveConfigWindow = Ext.create("AP.view.acquisitionUnit.CurveConfigWindow");

                                Ext.getCmp("curveConfigSelectedTableType_Id").setValue(0); //采集项表
                                Ext.getCmp("curveConfigSelectedRow_Id").setValue(row);
                                Ext.getCmp("curveConfigSelectedCol_Id").setValue(column);

                                CurveConfigWindow.show();

                                var curveConfig = null;
                                if (column == 12 && isNotVal(row1[20])) {
                                    curveConfig = row1[20];
                                } else if (column == 19 && isNotVal(row1[21])) {
                                    curveConfig = row1[21];
                                }
                                var value = 'ff0000';

                                if (isNotVal(curveConfig)) {
                                    Ext.getCmp("curveConfigSort_Id").setValue(curveConfig.sort);
                                    Ext.getCmp("curveConfigLineWidth_Id").setValue(curveConfig.lineWidth);
                                    Ext.getCmp("curveConfigDashStyleComb_Id").setValue(curveConfig.dashStyle);
                                    Ext.getCmp("curveConfigYAxisOppositeComb_Id").setValue(curveConfig.yAxisOpposite);

                                    Ext.getCmp('curveConfigColor_id').setValue(curveConfig.color);
                                    var Color0 = Ext.getCmp('curveConfigColor_id').color;
                                    Ext.getCmp('curveConfigColor_id').inputEl.applyStyles({
                                        background: '#' + curveConfig.color,
                                    });
                                } else {
                                    Ext.getCmp('curveConfigColor_id').setValue(value);
                                    var Color0 = Ext.getCmp('curveConfigColor_id').color;
                                    Ext.getCmp('curveConfigColor_id').inputEl.applyStyles({
                                        background: '#' + value
                                    });
                                }
                            }
                        }
                    } else if (row1[0] && (column == 10 || column == 11 || column == 17 || column == 18)) {
                        var CellColorSelectWindow = Ext.create("AP.view.acquisitionUnit.CellColorSelectWindow");

                        Ext.getCmp("cellColorSelectedTableType_Id").setValue(231); //采集项表
                        Ext.getCmp("cellColorSelectedRow_Id").setValue(row);
                        Ext.getCmp("cellColorSelectedCol_Id").setValue(column);

                        CellColorSelectWindow.show();

                        var value = row1[column];
                        if (value == null || value == '') {
                            value = 'ff0000';
                        }
                        Ext.getCmp('CellColorSelectWindowColor_id').setValue(value);
                        var BackgroundColor = Ext.getCmp('CellColorSelectWindowColor_id').color;
                        BackgroundColor.a = 1;
                        Ext.getCmp('CellColorSelectWindowColor_id').setColor(BackgroundColor);
                    }
                },
                afterOnCellMouseOver: function (event, coords, TD) {
                    if (protocolDisplayUnitAcqItemsConfigHandsontableHelper.columns[coords.col].type != 'checkbox' &&
                        protocolDisplayUnitAcqItemsConfigHandsontableHelper != null &&
                        protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot != '' &&
                        protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot != undefined &&
                        protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtCell != undefined) {
                        var rawValue = protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtCell(coords.row, coords.col);
                        if (isNotVal(rawValue)) {
                            var showValue = rawValue;
                            var rowChar = 90;
                            var maxWidth = rowChar * 10;
                            if (rawValue.length > rowChar) {
                                showValue = '';
                                let arr = [];
                                let index = 0;
                                while (index < rawValue.length) {
                                    arr.push(rawValue.slice(index, index += rowChar));
                                }
                                for (var i = 0; i < arr.length; i++) {
                                    showValue += arr[i];
                                    if (i < arr.length - 1) {
                                        showValue += '<br>';
                                    }
                                }
                            }
                            if (!isNotVal(TD.tip)) {
                                var height = 28;
                                TD.tip = Ext.create('Ext.tip.ToolTip', {
                                    target: event.target,
                                    maxWidth: maxWidth,
                                    html: showValue,
                                    listeners: {
                                        hide: function (thisTip, eOpts) {},
                                        close: function (thisTip, eOpts) {}
                                    }
                                });
                            } else {
                                TD.tip.setHtml(showValue);
                            }
                        }
                    }
                }
            });
        }
        //保存数据
        protocolDisplayUnitAcqItemsConfigHandsontableHelper.saveData = function () {}
        protocolDisplayUnitAcqItemsConfigHandsontableHelper.clearContainer = function () {
            protocolDisplayUnitAcqItemsConfigHandsontableHelper.AllData = [];
        }
        return protocolDisplayUnitAcqItemsConfigHandsontableHelper;
    }
};


function CreateProtocolDisplayUnitCalItemsConfigInfoTable(deviceType,classes,unitId,unitName,calculateType){
	Ext.getCmp("ModbusProtocolDisplayUnitCalItemsConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolDisplayUnitCalItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolDisplayUnitCalItemsConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(classes==2){
				Ext.getCmp("ModbusProtocolDisplayUnitCalItemsConfigTableInfoPanel_Id").setTitle(unitName+'/'+loginUserLanguageResource.calculateItemConfig);
			}else{
				Ext.getCmp("ModbusProtocolDisplayUnitCalItemsConfigTableInfoPanel_Id").setTitle(loginUserLanguageResource.calculateItemConfig);
			}
			if(protocolDisplayUnitCalItemsConfigHandsontableHelper==null || protocolDisplayUnitCalItemsConfigHandsontableHelper.hot==undefined){
				protocolDisplayUnitCalItemsConfigHandsontableHelper = ProtocolDisplayUnitCalItemsConfigHandsontableHelper.createNew("ModbusProtocolDisplayUnitCalItemsConfigTableInfoDiv_id");
				var colHeaders="[" 
					+"['','','','','',{label: '"+loginUserLanguageResource.realtimeMonitoring+"', colspan: 4},{label: '"+loginUserLanguageResource.historyQuery+"', colspan: 4},'','','','']," 
					+"['','','','','',{label: '"+loginUserLanguageResource.dynamicData+"', colspan: 3},'"+loginUserLanguageResource.trendCurve+"',{label: '"+loginUserLanguageResource.historyData+"', colspan: 3},'"+loginUserLanguageResource.trendCurve+"','','','','']," 
					+"['','"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.unit+"','"+loginUserLanguageResource.showLevel+"'," 
					+"'"+loginUserLanguageResource.columnSort+"','"+loginUserLanguageResource.foregroundColor+"','"+loginUserLanguageResource.backgroundColor+"','"+loginUserLanguageResource.curveConfig+"'," 
					+"'"+loginUserLanguageResource.columnSort+"','"+loginUserLanguageResource.foregroundColor+"','"+loginUserLanguageResource.backgroundColor+"','"+loginUserLanguageResource.curveConfig+"'," 
					+"'','','','"+loginUserLanguageResource.dataSource+"']" 
					+"]";
				var columns="[" 
						+"{data:'checked',type:'checkbox'}," 
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitCalItemsConfigHandsontableHelper);}}," 
						+"{data:'realtimeSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitCalItemsConfigHandsontableHelper);}}," 
						+"{data:'realtimeColor'}," 
	                    +"{data:'realtimeBgColor'}," 
	                    +"{data:'realtimeCurveConfShowValue'},"
						+"{data:'historySort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitCalItemsConfigHandsontableHelper);}}," 
						+"{data:'historyColor'}," 
	                    +"{data:'historyBgColor'}," 
						+"{data:'historyCurveConfShowValue'},"
						+"{data:'realtimeCurveConf'},"
						+"{data:'historyCurveConf'},"
						+"{data:'code'},"
						+"{data:'dataSource'}"
						+"]";
				protocolDisplayUnitCalItemsConfigHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolDisplayUnitCalItemsConfigHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolDisplayUnitCalItemsConfigHandsontableHelper.createTable(result.totalRoot);
			}else{
				protocolDisplayUnitCalItemsConfigHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolDisplayUnitCalItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			deviceType:deviceType,
			classes:classes,
			unitId:unitId,
			calculateType:calculateType
        }
	});
};

var ProtocolDisplayUnitCalItemsConfigHandsontableHelper = {
		createNew: function (divid) {
	        var protocolDisplayUnitCalItemsConfigHandsontableHelper = {};
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.hot1 = '';
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.divid = divid;
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.validresult=true;//数据校验
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.colHeaders=[];
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.columns=[];
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.AllData=[];
	        
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null && value!=""){
	            	var arr=value.split(';');
	            	if(arr.length==3){
	            		td.style.backgroundColor = '#'+arr[2];
	            	}
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.addReadOnlyCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            var bg='rgb(245, 245, 245)';
	            if(value!=null && value!=""){
	            	var arr=value.split(';');
	            	if(arr.length==3){
	            		bg = '#'+arr[2];
	            	}
	            }
	            td.style.backgroundColor = bg;
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.addCellBgColor = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (value!=null && value!="") {
	                td.style.backgroundColor = '#' + value;
	            }
	            td.style.whiteSpace = 'nowrap'; //文本不换行
	            td.style.overflow = 'hidden'; //超出部分隐藏
	            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.addReadOnlyCellBgColor = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (value!=null && value!="") {
	                td.style.backgroundColor = '#' + value;
	            }else{
	            	td.style.backgroundColor = 'rgb(245, 245, 245)';
	            }
	            td.style.whiteSpace = 'nowrap'; //文本不换行
	            td.style.overflow = 'hidden'; //超出部分隐藏
	            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(protocolDisplayUnitCalItemsConfigHandsontableHelper.columns[col].type=='checkbox'){
	        		protocolDisplayUnitCalItemsConfigHandsontableHelper.addCheckboxReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else if(protocolDisplayUnitCalItemsConfigHandsontableHelper.columns[col].type=='dropdown'){
	        		protocolDisplayUnitCalItemsConfigHandsontableHelper.addDropdownReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else{
	        		protocolDisplayUnitCalItemsConfigHandsontableHelper.addTextReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}
	        }
	        
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.addCheckboxReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.addDropdownReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.addTextReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolDisplayUnitCalItemsConfigHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolDisplayUnitCalItemsConfigHandsontableHelper.divid);
	        	protocolDisplayUnitCalItemsConfigHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [13,14,15],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [25,50,140,80,60, 80,80,80,100, 80,80,80,100, 80,80,80,80],
	                columns:protocolDisplayUnitCalItemsConfigHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
//	                colHeaders:protocolDisplayUnitCalItemsConfigHandsontableHelper.colHeaders,//显示列头
	                nestedHeaders:protocolDisplayUnitCalItemsConfigHandsontableHelper.colHeaders,//显示列头
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
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag==1){
	                    	var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
		                	if(ScadaDriverModbusConfigSelectRow!=''){
		                		var selectedItem=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
		                		if(selectedItem.data.classes!=2){
		                			cellProperties.readOnly = true;
		                			cellProperties.renderer=protocolDisplayUnitCalItemsConfigHandsontableHelper.addReadOnlyBg;
		                		}else{
		                			if ((visualColIndex >=1 && visualColIndex<=3) || visualColIndex==16) {
		    							cellProperties.readOnly = true;
		    							cellProperties.renderer=protocolDisplayUnitCalItemsConfigHandsontableHelper.addReadOnlyBg;
		    		                }else if(visualColIndex==8||visualColIndex==12){
		    		                	cellProperties.renderer = protocolDisplayUnitCalItemsConfigHandsontableHelper.addCurveBg;
		    		                }else if (visualColIndex == 6 || visualColIndex == 7 || visualColIndex == 10 || visualColIndex == 11) {
		                                cellProperties.renderer = protocolDisplayUnitCalItemsConfigHandsontableHelper.addCellBgColor;
		                            }else{
		    		                	if(protocolDisplayUnitCalItemsConfigHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    		    	            	&& protocolDisplayUnitCalItemsConfigHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		    		                    	cellProperties.renderer = protocolDisplayUnitCalItemsConfigHandsontableHelper.addCellStyle;
		    		    	            }
		    		                }
		                		}
		                	}
	                    }else{
	                    	cellProperties.readOnly = true;
	                    	if(visualColIndex==8||visualColIndex==12){
			                	cellProperties.renderer = protocolDisplayUnitCalItemsConfigHandsontableHelper.addReadOnlyCurveBg;
			                }else if (visualColIndex == 6 || visualColIndex == 7 || visualColIndex == 10 || visualColIndex == 11) {
	                            cellProperties.renderer = protocolDisplayUnitCalItemsConfigHandsontableHelper.addReadOnlyCellBgColor;
	                        }else{
			                	if(protocolDisplayUnitCalItemsConfigHandsontableHelper.columns[visualColIndex].type!='dropdown' 
			    	            	&& protocolDisplayUnitCalItemsConfigHandsontableHelper.columns[visualColIndex].type!='checkbox'){
			                		cellProperties.renderer=protocolDisplayUnitCalItemsConfigHandsontableHelper.addReadOnlyBg;
			    	            }
			                }
	                    }
	                    
	                    return cellProperties;
	                },
	                afterBeginEditing:function(row,column){
	                	var row1=protocolDisplayUnitCalItemsConfigHandsontableHelper.hot.getDataAtRow(row);
	                	if(row1[0] && (column==8||column==12)){
	                		var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
	                		if(ScadaDriverModbusConfigSelectRow!=''){
	                			var selectedItem=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
	                			if(selectedItem.data.classes==2){
	                				var CurveConfigWindow=Ext.create("AP.view.acquisitionUnit.CurveConfigWindow");
	                				
	                				Ext.getCmp("curveConfigSelectedTableType_Id").setValue(1);//采集项表
	                				Ext.getCmp("curveConfigSelectedRow_Id").setValue(row);
	                				Ext.getCmp("curveConfigSelectedCol_Id").setValue(column);
	                				
	                				CurveConfigWindow.show();
	                				
	                				var curveConfig=null;
	                				if(column==8 && isNotVal(row1[13])){
	                					curveConfig=row1[13];
	                				}else if(column==12 && isNotVal(row1[14])){
	                					curveConfig=row1[14];
	                				}
	                				var value=row1[column];
	                				if(value==null||value==''){
	                					value='ff0000';
	                				}
	                				
	                				if(isNotVal(curveConfig)){
	                					Ext.getCmp("curveConfigSort_Id").setValue(curveConfig.sort);
	                					Ext.getCmp("curveConfigLineWidth_Id").setValue(curveConfig.lineWidth);
	                					Ext.getCmp("curveConfigDashStyleComb_Id").setValue(curveConfig.dashStyle);
	                					Ext.getCmp("curveConfigYAxisOppositeComb_Id").setValue(curveConfig.yAxisOpposite);
	                		        	
	                		        	Ext.getCmp('curveConfigColor_id').setValue(curveConfig.color);
	                		            var Color0=Ext.getCmp('curveConfigColor_id').color;
	                		            Ext.getCmp('curveConfigColor_id').inputEl.applyStyles({
	                		            	background: '#'+curveConfig.color
	                		            });
	                				}else{
	                					Ext.getCmp('curveConfigColor_id').setValue(value);
	                		            var Color0=Ext.getCmp('curveConfigColor_id').color;
	                		            Ext.getCmp('curveConfigColor_id').inputEl.applyStyles({
	                		            	background: '#'+value
	                		            });
	                				}
	                			}
	                		}
	                	}else if (row1[0] && (column == 6 || column == 7 || column == 10 || column == 11)) {
	                        var CellColorSelectWindow = Ext.create("AP.view.acquisitionUnit.CellColorSelectWindow");

	                        Ext.getCmp("cellColorSelectedTableType_Id").setValue(232); //采集项表
	                        Ext.getCmp("cellColorSelectedRow_Id").setValue(row);
	                        Ext.getCmp("cellColorSelectedCol_Id").setValue(column);

	                        CellColorSelectWindow.show();

	                        var value = row1[column];
	                        if (value == null || value == '') {
	                            value = 'ff0000';
	                        }
	                        Ext.getCmp('CellColorSelectWindowColor_id').setValue(value);
	                        var BackgroundColor = Ext.getCmp('CellColorSelectWindowColor_id').color;
	                        BackgroundColor.a = 1;
	                        Ext.getCmp('CellColorSelectWindowColor_id').setColor(BackgroundColor);
	                    }else{
	                		return false;
	                	}
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolDisplayUnitCalItemsConfigHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolDisplayUnitCalItemsConfigHandsontableHelper!=null
	                		&& protocolDisplayUnitCalItemsConfigHandsontableHelper.hot!=''
	                		&& protocolDisplayUnitCalItemsConfigHandsontableHelper.hot!=undefined 
	                		&& protocolDisplayUnitCalItemsConfigHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolDisplayUnitCalItemsConfigHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.saveData = function () {}
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.clearContainer = function () {
	        	protocolDisplayUnitCalItemsConfigHandsontableHelper.AllData = [];
	        }
	        return protocolDisplayUnitCalItemsConfigHandsontableHelper;
	    }
};

function CreateProtocolDisplayUnitCtrlItemsConfigInfoTable(protocolName,classes,code,unitId,acqUnitId,unitName){
	Ext.getCmp("ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolDisplayUnitCtrlItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(classes==2){
				Ext.getCmp("ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoPanel_Id").setTitle(unitName+'/'+loginUserLanguageResource.controlItemConfig);
			}else{
				Ext.getCmp("ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoPanel_Id").setTitle(loginUserLanguageResource.controlItemConfig);
			}
			if(protocolDisplayUnitCtrlItemsConfigHandsontableHelper==null || protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot==undefined){
				protocolDisplayUnitCtrlItemsConfigHandsontableHelper = ProtocolDisplayUnitCtrlItemsConfigHandsontableHelper.createNew("ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoDiv_id");
				var colHeaders="['','"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.unit+"','"+loginUserLanguageResource.showLevel+"','"+loginUserLanguageResource.columnSort+"']";
				var columns="[" 
						+"{data:'checked',type:'checkbox'}," 
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitCtrlItemsConfigHandsontableHelper);}}," 
						+"{data:'realtimeSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitCtrlItemsConfigHandsontableHelper);}}," 
						+"{data:'resolutionMode',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.switchingValue+"', '"+loginUserLanguageResource.enumValue+"','"+loginUserLanguageResource.numericValue+"']}," 
						+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolDisplayUnitCtrlItemsConfigHandsontableHelper);}},"
						+"{data:'bitIndex'}"
						+"]";
				
				protocolDisplayUnitCtrlItemsConfigHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolDisplayUnitCtrlItemsConfigHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolDisplayUnitCtrlItemsConfigHandsontableHelper.createTable(result.totalRoot);
			}else{
				protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			classes:classes,
			code:code,
			unitId:unitId,
			acqUnitId:acqUnitId
        }
	});
};

var ProtocolDisplayUnitCtrlItemsConfigHandsontableHelper = {
		createNew: function (divid) {
	        var protocolDisplayUnitCtrlItemsConfigHandsontableHelper = {};
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot1 = '';
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.divid = divid;
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.validresult=true;//数据校验
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.colHeaders=[];
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.columns=[];
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.AllData=[];
	        
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(protocolDisplayUnitCtrlItemsConfigHandsontableHelper.columns[col].type=='checkbox'){
	        		protocolDisplayUnitCtrlItemsConfigHandsontableHelper.addCheckboxReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else if(protocolDisplayUnitCtrlItemsConfigHandsontableHelper.columns[col].type=='dropdown'){
	        		protocolDisplayUnitCtrlItemsConfigHandsontableHelper.addDropdownReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else{
	        		protocolDisplayUnitCtrlItemsConfigHandsontableHelper.addTextReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}
	        }
	        
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.addCheckboxReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.addDropdownReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.addTextReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolDisplayUnitCtrlItemsConfigHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolDisplayUnitCtrlItemsConfigHandsontableHelper.divid);
	        	protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [6,7,8],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [25,50,140,80,60,60],
	                columns:protocolDisplayUnitCtrlItemsConfigHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolDisplayUnitCtrlItemsConfigHandsontableHelper.colHeaders,//显示列头
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
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag==1){
	                    	var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
		                	if(ScadaDriverModbusConfigSelectRow!=''){
		                		var selectedItem=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
		                		if(selectedItem.data.classes!=2){
		                			cellProperties.readOnly = true;
		                			cellProperties.renderer=protocolDisplayUnitCtrlItemsConfigHandsontableHelper.addReadOnlyBg;
		                		}else{
		                			if (visualColIndex >=1 && visualColIndex<=3) {
		    							cellProperties.readOnly = true;
		    							cellProperties.renderer=protocolDisplayUnitCtrlItemsConfigHandsontableHelper.addReadOnlyBg;
		    		                }else{
		    		                	if(protocolDisplayUnitCtrlItemsConfigHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    		    	            	&& protocolDisplayUnitCtrlItemsConfigHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		    		                    	cellProperties.renderer = protocolDisplayUnitCtrlItemsConfigHandsontableHelper.addCellStyle;
		    		    	            }
		    		                }
		                		}
		                	}
	                    }else{
	                    	cellProperties.readOnly = true;
	                    	cellProperties.renderer=protocolDisplayUnitCtrlItemsConfigHandsontableHelper.addReadOnlyBg;
	                    }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolDisplayUnitCtrlItemsConfigHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolDisplayUnitCtrlItemsConfigHandsontableHelper!=null
	                		&& protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot!=''
	                		&& protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot!=undefined 
	                		&& protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.saveData = function () {}
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.clearContainer = function () {
	        	protocolDisplayUnitCtrlItemsConfigHandsontableHelper.AllData = [];
	        }
	        return protocolDisplayUnitCtrlItemsConfigHandsontableHelper;
	    }
};

function CreateProtocolDisplayUnitInputItemsConfigInfoTable(deviceType,classes,unitId,unitName,calculateType){
	Ext.getCmp("ModbusProtocolDisplayUnitInputItemsConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolDisplayUnitInputItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolDisplayUnitInputItemsConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(classes==2){
				Ext.getCmp("ModbusProtocolDisplayUnitInputItemsConfigTableInfoPanel_Id").setTitle(unitName+'/'+loginUserLanguageResource.inputItemConfig);
			}else{
				Ext.getCmp("ModbusProtocolDisplayUnitInputItemsConfigTableInfoPanel_Id").setTitle(loginUserLanguageResource.inputItemConfig);
			}
			if(protocolDisplayUnitInputItemsConfigHandsontableHelper==null || protocolDisplayUnitInputItemsConfigHandsontableHelper.hot==undefined){
				protocolDisplayUnitInputItemsConfigHandsontableHelper = ProtocolDisplayUnitInputItemsConfigHandsontableHelper.createNew("ModbusProtocolDisplayUnitInputItemsConfigTableInfoDiv_id");
				var colHeaders="[" 
					+"['','','','','',{label: '"+loginUserLanguageResource.realtimeMonitoring+"', colspan: 4},{label: '"+loginUserLanguageResource.historyQuery+"', colspan: 4},'','','']," 
					+"['','','','','',{label: '"+loginUserLanguageResource.dynamicData+"', colspan: 3},'"+loginUserLanguageResource.trendCurve+"',{label: '"+loginUserLanguageResource.historyData+"', colspan: 3},'"+loginUserLanguageResource.trendCurve+"','','','']," 
					+"['','"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.unit+"','"+loginUserLanguageResource.showLevel+"'," 
					+"'"+loginUserLanguageResource.columnSort+"','"+loginUserLanguageResource.foregroundColor+"','"+loginUserLanguageResource.backgroundColor+"','"+loginUserLanguageResource.curveConfig+"'," 
					+"'"+loginUserLanguageResource.columnSort+"','"+loginUserLanguageResource.foregroundColor+"','"+loginUserLanguageResource.backgroundColor+"','"+loginUserLanguageResource.curveConfig+"'," 
					+"'','','']" 
					+"]";
				var columns="[" 
						+"{data:'checked',type:'checkbox'}," 
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitInputItemsConfigHandsontableHelper);}}," 
						+"{data:'realtimeSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitInputItemsConfigHandsontableHelper);}}," 
						+"{data:'realtimeColor'}," 
	                    +"{data:'realtimeBgColor'}," 
						+"{data:'realtimeCurveConfShowValue'},"
						+"{data:'historySort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitInputItemsConfigHandsontableHelper);}}," 
						+"{data:'historyColor'}," 
	                    +"{data:'historyBgColor'}," 
						+"{data:'historyCurveConfShowValue'},"
						+"{data:'realtimeCurveConf'},"
						+"{data:'historyCurveConf'},"
						+"{data:'code'}"
						+"]";
				protocolDisplayUnitInputItemsConfigHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolDisplayUnitInputItemsConfigHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolDisplayUnitInputItemsConfigHandsontableHelper.createTable(result.totalRoot);
			}else{
				protocolDisplayUnitInputItemsConfigHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolDisplayUnitInputItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			deviceType:deviceType,
			classes:classes,
			unitId:unitId,
			calculateType:calculateType
        }
	});
};

var ProtocolDisplayUnitInputItemsConfigHandsontableHelper = {
		createNew: function (divid) {
	        var protocolDisplayUnitInputItemsConfigHandsontableHelper = {};
	        protocolDisplayUnitInputItemsConfigHandsontableHelper.hot1 = '';
	        protocolDisplayUnitInputItemsConfigHandsontableHelper.divid = divid;
	        protocolDisplayUnitInputItemsConfigHandsontableHelper.validresult=true;//数据校验
	        protocolDisplayUnitInputItemsConfigHandsontableHelper.colHeaders=[];
	        protocolDisplayUnitInputItemsConfigHandsontableHelper.columns=[];
	        protocolDisplayUnitInputItemsConfigHandsontableHelper.AllData=[];
	        
	        protocolDisplayUnitInputItemsConfigHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null && value!=""){
	            	var arr=value.split(';');
	            	if(arr.length==3){
	            		td.style.backgroundColor = '#'+arr[2];
	            	}
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayUnitInputItemsConfigHandsontableHelper.addReadOnlyCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            var bg='rgb(245, 245, 245)';
	            if(value!=null && value!=""){
	            	var arr=value.split(';');
	            	if(arr.length==3){
	            		bg = '#'+arr[2];
	            	}
	            }
	            td.style.backgroundColor = bg;
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayUnitInputItemsConfigHandsontableHelper.addCellBgColor = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (value!=null && value!="") {
	                td.style.backgroundColor = '#' + value;
	            }
	            td.style.whiteSpace = 'nowrap'; //文本不换行
	            td.style.overflow = 'hidden'; //超出部分隐藏
	            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayUnitInputItemsConfigHandsontableHelper.addReadOnlyCellBgColor = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (value!=null && value!="") {
	                td.style.backgroundColor = '#' + value;
	            }else{
	            	td.style.backgroundColor = 'rgb(245, 245, 245)';
	            }
	            td.style.whiteSpace = 'nowrap'; //文本不换行
	            td.style.overflow = 'hidden'; //超出部分隐藏
	            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayUnitInputItemsConfigHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayUnitInputItemsConfigHandsontableHelper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(protocolDisplayUnitInputItemsConfigHandsontableHelper.columns[col].type=='checkbox'){
	        		protocolDisplayUnitInputItemsConfigHandsontableHelper.addCheckboxReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else if(protocolDisplayUnitInputItemsConfigHandsontableHelper.columns[col].type=='dropdown'){
	        		protocolDisplayUnitInputItemsConfigHandsontableHelper.addDropdownReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else{
	        		protocolDisplayUnitInputItemsConfigHandsontableHelper.addTextReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}
	        }
	        
	        protocolDisplayUnitInputItemsConfigHandsontableHelper.addCheckboxReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolDisplayUnitInputItemsConfigHandsontableHelper.addDropdownReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayUnitInputItemsConfigHandsontableHelper.addTextReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayUnitInputItemsConfigHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolDisplayUnitInputItemsConfigHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolDisplayUnitInputItemsConfigHandsontableHelper.divid);
	        	protocolDisplayUnitInputItemsConfigHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [13,14,15],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [25,50,140,80,60,80,80,80,100,80,80,80,100],
	                columns:protocolDisplayUnitInputItemsConfigHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
//	                colHeaders:protocolDisplayUnitInputItemsConfigHandsontableHelper.colHeaders,//显示列头
	                nestedHeaders:protocolDisplayUnitInputItemsConfigHandsontableHelper.colHeaders,//显示列头
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
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag==1){
	                    	var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
		                	if(ScadaDriverModbusConfigSelectRow!=''){
		                		var selectedItem=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
		                		if(selectedItem.data.classes!=2){
		                			cellProperties.readOnly = true;
		                			cellProperties.renderer=protocolDisplayUnitInputItemsConfigHandsontableHelper.addReadOnlyBg;
		                		}else{
		                			if (visualColIndex >=1 && visualColIndex<=3) {
		    							cellProperties.readOnly = true;
		    							cellProperties.renderer=protocolDisplayUnitInputItemsConfigHandsontableHelper.addReadOnlyBg;
		    		                }else if(visualColIndex==8||visualColIndex==12){
		    		                	cellProperties.renderer = protocolDisplayUnitInputItemsConfigHandsontableHelper.addCurveBg;
		    		                } else if (visualColIndex == 6 || visualColIndex == 7 || visualColIndex == 10 || visualColIndex == 11) {
		                                cellProperties.renderer = protocolDisplayUnitInputItemsConfigHandsontableHelper.addCellBgColor;
		                            }else{
		    		                	if(protocolDisplayUnitInputItemsConfigHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    		    	            	&& protocolDisplayUnitInputItemsConfigHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		    		                    	cellProperties.renderer = protocolDisplayUnitInputItemsConfigHandsontableHelper.addCellStyle;
		    		    	            }
		    		                }
		                		}
		                	}
	                    }else{
	                    	cellProperties.readOnly = true;
	                    	if(visualColIndex==8||visualColIndex==12){
			                	cellProperties.renderer = protocolDisplayUnitInputItemsConfigHandsontableHelper.addReadOnlyCurveBg;
			                } else if (visualColIndex == 6 || visualColIndex == 7 || visualColIndex == 10 || visualColIndex == 11) {
	                            cellProperties.renderer = protocolDisplayUnitInputItemsConfigHandsontableHelper.addReadOnlyCellBgColor;
	                        }else{
	                        	cellProperties.renderer=protocolDisplayUnitInputItemsConfigHandsontableHelper.addReadOnlyBg;
	                        }
	                    }
	                    
	                    return cellProperties;
	                },
	                afterBeginEditing:function(row,column){
	                	var row1=protocolDisplayUnitInputItemsConfigHandsontableHelper.hot.getDataAtRow(row);
	                	if(row1[0] && (column==8||column==12)){
	                		var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
	                		if(ScadaDriverModbusConfigSelectRow!=''){
	                			var selectedItem=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
	                			if(selectedItem.data.classes==2){
	                				var CurveConfigWindow=Ext.create("AP.view.acquisitionUnit.CurveConfigWindow");
	                				
	                				Ext.getCmp("curveConfigSelectedTableType_Id").setValue(3);//录入项表
	                				Ext.getCmp("curveConfigSelectedRow_Id").setValue(row);
	                				Ext.getCmp("curveConfigSelectedCol_Id").setValue(column);
	                				
	                				CurveConfigWindow.show();
	                				
	                				var curveConfig=null;
	                				if(column==8 && isNotVal(row1[13])){
	                					curveConfig=row1[13];
	                				}else if(column==12 && isNotVal(row1[14])){
	                					curveConfig=row1[14];
	                				}
	                				var value=row1[column];
	                				if(value==null||value==''){
	                					value='ff0000';
	                				}
	                				
	                				if(isNotVal(curveConfig)){
	                					Ext.getCmp("curveConfigSort_Id").setValue(curveConfig.sort);
	                					Ext.getCmp("curveConfigLineWidth_Id").setValue(curveConfig.lineWidth);
	                					Ext.getCmp("curveConfigDashStyleComb_Id").setValue(curveConfig.dashStyle);
	                					Ext.getCmp("curveConfigYAxisOppositeComb_Id").setValue(curveConfig.yAxisOpposite);
	                		        	
	                		        	Ext.getCmp('curveConfigColor_id').setValue(curveConfig.color);
	                		            var Color0=Ext.getCmp('curveConfigColor_id').color;
	                		            Ext.getCmp('curveConfigColor_id').inputEl.applyStyles({
	                		            	background: '#'+curveConfig.color
	                		            });
	                				}else{
	                					Ext.getCmp('curveConfigColor_id').setValue(value);
	                		            var Color0=Ext.getCmp('curveConfigColor_id').color;
	                		            Ext.getCmp('curveConfigColor_id').inputEl.applyStyles({
	                		            	background: '#'+value
	                		            });
	                				}
	                			}
	                		}
	                	}else if (row1[0] && (column == 6 || column == 7 || column == 10 || column == 11)) {
	                        var CellColorSelectWindow = Ext.create("AP.view.acquisitionUnit.CellColorSelectWindow");

	                        Ext.getCmp("cellColorSelectedTableType_Id").setValue(234); //录入项表
	                        Ext.getCmp("cellColorSelectedRow_Id").setValue(row);
	                        Ext.getCmp("cellColorSelectedCol_Id").setValue(column);

	                        CellColorSelectWindow.show();

	                        var value = row1[column];
	                        if (value == null || value == '') {
	                            value = 'ff0000';
	                        }
	                        Ext.getCmp('CellColorSelectWindowColor_id').setValue(value);
	                        var BackgroundColor = Ext.getCmp('CellColorSelectWindowColor_id').color;
	                        BackgroundColor.a = 1;
	                        Ext.getCmp('CellColorSelectWindowColor_id').setColor(BackgroundColor);
	                    }
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolDisplayUnitInputItemsConfigHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolDisplayUnitInputItemsConfigHandsontableHelper!=null
	                		&& protocolDisplayUnitInputItemsConfigHandsontableHelper.hot!=''
	                		&& protocolDisplayUnitInputItemsConfigHandsontableHelper.hot!=undefined 
	                		&& protocolDisplayUnitInputItemsConfigHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolDisplayUnitInputItemsConfigHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        protocolDisplayUnitInputItemsConfigHandsontableHelper.saveData = function () {}
	        protocolDisplayUnitInputItemsConfigHandsontableHelper.clearContainer = function () {
	        	protocolDisplayUnitInputItemsConfigHandsontableHelper.AllData = [];
	        }
	        return protocolDisplayUnitInputItemsConfigHandsontableHelper;
	    }
};

function CreateProtocolDisplayUnitConfigPropertiesInfoTable(data){
	var root=[];
	if(data.classes==0){
		var item1={};
		item1.id=1;
		item1.title=loginUserLanguageResource.rootNode;
		item1.value=loginUserLanguageResource.unitList;
		root.push(item1);
	}else if(data.classes==1){
		var item1={};
		item1.id=1;
		item1.title=loginUserLanguageResource.protocolName;
		item1.value=data.text;
		root.push(item1);
	}else if(data.classes==2){
		var item1={};
		item1.id=1;
		item1.title=loginUserLanguageResource.unitName;
		item1.value=data.text;
		root.push(item1);
		
		var item2={};
		item2.id=2;
		item2.title=loginUserLanguageResource.acqUnit;
		item2.value=data.acqUnitName;
		root.push(item2);

		var item3={};
		item3.id=3;
		item3.title=loginUserLanguageResource.calculateType;
		item3.value=data.calculateTypeName;
		root.push(item3);
		
		
		var item4={};
		item4.id=4;
		item4.title=loginUserLanguageResource.remark;
		item4.value=data.remark;
		root.push(item4);
	}
	
	if(protocolDisplayUnitPropertiesHandsontableHelper==null || protocolDisplayUnitPropertiesHandsontableHelper.hot==undefined){
		protocolDisplayUnitPropertiesHandsontableHelper = ProtocolDisplayUnitPropertiesHandsontableHelper.createNew("ModbusProtocolDisplayUnitPropertiesTableInfoDiv_id");
		var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.variable+"']";
		var columns="[{data:'id'},{data:'title'},{data:'value'}]";
		protocolDisplayUnitPropertiesHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
		protocolDisplayUnitPropertiesHandsontableHelper.columns=Ext.JSON.decode(columns);
		protocolDisplayUnitPropertiesHandsontableHelper.classes=data.classes;
		protocolDisplayUnitPropertiesHandsontableHelper.createTable(root);
	}else{
		protocolDisplayUnitPropertiesHandsontableHelper.classes=data.classes;
		protocolDisplayUnitPropertiesHandsontableHelper.hot.loadData(root);
	}
};

var ProtocolDisplayUnitPropertiesHandsontableHelper = {
		createNew: function (divid) {
	        var protocolDisplayUnitPropertiesHandsontableHelper = {};
	        protocolDisplayUnitPropertiesHandsontableHelper.hot = '';
	        protocolDisplayUnitPropertiesHandsontableHelper.classes =null;
	        protocolDisplayUnitPropertiesHandsontableHelper.divid = divid;
	        protocolDisplayUnitPropertiesHandsontableHelper.validresult=true;//数据校验
	        protocolDisplayUnitPropertiesHandsontableHelper.colHeaders=[];
	        protocolDisplayUnitPropertiesHandsontableHelper.columns=[];
	        protocolDisplayUnitPropertiesHandsontableHelper.AllData=[];
	        
	        protocolDisplayUnitPropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayUnitPropertiesHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayUnitPropertiesHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolDisplayUnitPropertiesHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolDisplayUnitPropertiesHandsontableHelper.divid);
	        	protocolDisplayUnitPropertiesHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [2,3,5],
	                columns:protocolDisplayUnitPropertiesHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolDisplayUnitPropertiesHandsontableHelper.colHeaders,//显示列头
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
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag==1){
	                    	if(protocolDisplayUnitPropertiesHandsontableHelper.classes===0 || protocolDisplayUnitPropertiesHandsontableHelper.classes===1){
								cellProperties.readOnly = true;
								cellProperties.renderer = protocolDisplayUnitPropertiesHandsontableHelper.addBoldBg;
			                }else if(protocolDisplayUnitPropertiesHandsontableHelper.classes===2){
			                	if (visualColIndex ==0 || visualColIndex ==1) {
									cellProperties.readOnly = true;
									cellProperties.renderer = protocolDisplayUnitPropertiesHandsontableHelper.addBoldBg;
				                }else{
				                	if(visualColIndex === 2 && visualRowIndex===0){
				                    	this.validator=function (val, callback) {
				                    	    return handsontableDataCheck_NotNull(val, callback, row, col, protocolDisplayUnitPropertiesHandsontableHelper);
				                    	}
				                    	cellProperties.renderer = protocolDisplayUnitPropertiesHandsontableHelper.addCellStyle;
				                    } else if (visualColIndex === 2 && visualRowIndex === 2) {
		                                this.type = 'dropdown';
		                                this.source = [loginUserLanguageResource.nothing, loginUserLanguageResource.SRPCalculate, loginUserLanguageResource.PCPCalculate];
		                                this.strict = true;
		                                this.allowInvalid = false;
		                            }else{
		                            	cellProperties.renderer = protocolDisplayUnitPropertiesHandsontableHelper.addCellStyle;
		                            }
				                }
			                }
	                    }else{
	                    	cellProperties.readOnly = true;
							cellProperties.renderer = protocolDisplayUnitPropertiesHandsontableHelper.addBoldBg;
	                    }
	                    
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolDisplayUnitPropertiesHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolDisplayUnitPropertiesHandsontableHelper!=null
	                		&& protocolDisplayUnitPropertiesHandsontableHelper.hot!=''
	                		&& protocolDisplayUnitPropertiesHandsontableHelper.hot!=undefined 
	                		&& protocolDisplayUnitPropertiesHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolDisplayUnitPropertiesHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        protocolDisplayUnitPropertiesHandsontableHelper.saveData = function () {}
	        protocolDisplayUnitPropertiesHandsontableHelper.clearContainer = function () {
	        	protocolDisplayUnitPropertiesHandsontableHelper.AllData = [];
	        }
	        return protocolDisplayUnitPropertiesHandsontableHelper;
	    }
};


function SaveModbusProtocolDisplayUnitConfigTreeData(){
	var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
	if(ScadaDriverModbusConfigSelectRow!=''){
		var selectedItem=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
		var propertiesData=protocolDisplayUnitPropertiesHandsontableHelper.hot.getData();
		var displayUnitProperties={};
		if(selectedItem.data.classes==2){//选中的是单元
			displayUnitProperties.classes=selectedItem.data.classes;
			displayUnitProperties.id=selectedItem.data.id;
			displayUnitProperties.unitCode=selectedItem.data.code;
			displayUnitProperties.unitName=propertiesData[0][2];
			displayUnitProperties.acqUnitId=selectedItem.data.acqUnitId;
			displayUnitProperties.acqUnitName=propertiesData[1][2];
			
			displayUnitProperties.calculateType = 0;
            if (propertiesData[2][2] == loginUserLanguageResource.SRPCalculate) {
            	displayUnitProperties.calculateType = 1;
            } else if (propertiesData[2][2] == loginUserLanguageResource.PCPCalculate) {
            	displayUnitProperties.calculateType = 2;
            }
            
			displayUnitProperties.remark=propertiesData[3][2];
		}
		if(selectedItem.data.classes==2){//保存单元
			var displayUnitSaveData={};
			displayUnitSaveData.updatelist=[];
			displayUnitSaveData.updatelist.push(displayUnitProperties);
			saveDisplayUnitConfigData(displayUnitSaveData,selectedItem.data.protocol,selectedItem.parentNode.data.deviceType);
			grantDisplayAcqItemsPermission();
//			grantDisplayCalItemsPermission();
//			grantDisplayInputItemsPermission();
			grantDisplayCtrlItemsPermission();
		}
	}
};

function saveDisplayUnitConfigData(displayUnitSaveData,protocol,deviceType){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/saveDisplayUnitHandsontableData',
		success:function(response) {
			rdata=Ext.JSON.decode(response.responseText);
			if (rdata.success) {
            	Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.saveSuccessfully);
            	if(displayUnitSaveData.delidslist!=undefined && displayUnitSaveData.delidslist.length>0){
            		Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").setValue(0);
            	}
            	Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().load();
            } else {
            	Ext.MessageBox.alert(loginUserLanguageResource.message,"<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
            }
		},
		failure:function(){
			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
            displayUnitConfigHandsontableHelper.clearContainer();
		},
		params: {
        	data: JSON.stringify(displayUnitSaveData),
        	protocol: protocol,
        	deviceType:deviceType
        }
	});
}

var grantDisplayItemsPermission = function () {
	var DisplayUnitConfigTreeSelectRow= Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
	if (protocolDisplayUnitAcqItemsConfigHandsontableHelper == null 
			|| protocolDisplayUnitCalItemsConfigHandsontableHelper == null
			|| protocolDisplayUnitCtrlItemsConfigHandsontableHelper == null
			|| protocolDisplayUnitInputItemsConfigHandsontableHelper == null
			|| DisplayUnitConfigTreeSelectRow=='') {
        return false;
    }
	var addUrl = context + '/acquisitionUnitManagerController/grantItemsToDisplayUnitPermission'
	var selectedItem=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(DisplayUnitConfigTreeSelectRow);
	var acqItemsData = protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getData();
	var calItemsData = protocolDisplayUnitCalItemsConfigHandsontableHelper.hot.getData();
	var ctrlItemsData = protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.getData();
	var inputItemsData = protocolDisplayUnitInputItemsConfigHandsontableHelper.hot.getData();
	
	var unitCode = selectedItem.data.code;
    var unitId = selectedItem.data.id;
    var protocol=selectedItem.data.protocol;
    if (!isNotVal(unitCode)) {
        return false
    }
}

var grantDisplayAcqItemsPermission = function () {
	var DisplayUnitConfigTreeSelectRow= Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
	if (protocolDisplayUnitAcqItemsConfigHandsontableHelper == null ||DisplayUnitConfigTreeSelectRow=='') {
        return false;
    }
	var selectedItem=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(DisplayUnitConfigTreeSelectRow);
//    var acqItemsData = protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getData();
    var rowCount = protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.countRows();
    var addUrl = context + '/acquisitionUnitManagerController/grantAcqItemsToDisplayUnitPermission'
    // 添加条件
    var addjson = [];
    var addItemRealtimeSort=[];
    var matrixData = "";
    var matrixDataArr = "";
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    
    var unitCode = selectedItem.data.code;
    var unitId = selectedItem.data.id;
    var protocol=selectedItem.data.protocol;
    if (!isNotVal(unitCode)) {
        return false
    }

    
    var columns = "[" 
        +"{data:'checked',type:'checkbox'}," 
        +"{data:'id'}," 
        +"{data:'title'}," 
        +"{data:'dataSource'}," 
        +"{data:'unit'}," 
        +"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
        
        +"{data:'realtimeOverview',type:'checkbox'}," 
        +"{data:'realtimeOverviewSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
        
        +"{data:'realtimeData',type:'checkbox'}," 
        +"{data:'realtimeSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
        +"{data:'realtimeColor'}," 
        +"{data:'realtimeBgColor'}," 
        +"{data:'realtimeCurveConfShowValue'}," //12
        
        +"{data:'historyOverview',type:'checkbox'}," 
        +"{data:'historyOverviewSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
        +"{data:'historyData',type:'checkbox'}," 
        +"{data:'historySort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
        +"{data:'historyColor'}," 
        +"{data:'historyBgColor'}," 
        +"{data:'historyCurveConfShowValue'}," //19
        +"{data:'realtimeCurveConf'}," 
        +"{data:'historyCurveConf'}," 
        +"{data:'resolutionMode',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.switchingValue+"', '"+loginUserLanguageResource.enumValue+"','"+loginUserLanguageResource.numericValue+"']}," 
        +"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
        +"{data:'bitIndex'}," 
        +"{data:'type'}," 
        +"{data:'code'}" 
        +"]";
    
    for(var i=0;i<rowCount;i++) {
    	
    	var realtimeDataSign=protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'realtimeData');
    	var historyDataSign=protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'historyData');
    	
        if (realtimeDataSign || historyDataSign) {
        	var itemName = protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'title');
        	
        	var itemShowLevel = protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'showLevel');
        	
        	var realtimeOverview=protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'realtimeOverview')?1:0;
        	var realtimeOverviewSort=protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'realtimeOverviewSort');
        	var realtimeData=realtimeDataSign?1:0;
        	
        	var itemRealtimeSort = protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'realtimeSort');
        	var realtimeColor= protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'realtimeColor');
        	var realtimeBgColor= protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'realtimeBgColor');
        	
        	var realtimeCurveConfigStr="";
        	var realtimeCurveConfig=null;
			if(isNotVal(protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'realtimeCurveConfShowValue')) && isNotVal(protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'realtimeCurveConf'))){
				realtimeCurveConfig=protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'realtimeCurveConf');
				realtimeCurveConfigStr=JSON.stringify(realtimeCurveConfig);
			}
			
			var historyOverview=protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'historyOverview')?1:0;
        	var historyOverviewSort=protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'historyOverviewSort');
        	var historyData=historyDataSign?1:0;
        	
        	var itemHistorySort = protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'historySort');
        	var historyColor= protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'historyColor');
        	var historyBgColor= protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'historyBgColor');
        	var historyCurveConfigStr="";
			var historyCurveConfig=null;
			if(isNotVal(protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'historyCurveConfShowValue')) && isNotVal(protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'historyCurveConf'))){
				historyCurveConfig=protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'historyCurveConf');
				historyCurveConfigStr=JSON.stringify(historyCurveConfig);
			}
        	
        	var resolutionMode = protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'resolutionMode');
        	var itemAddr = protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'addr');
        	var bitIndex = protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'bitIndex');
        	
        	var type = protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'type');
        	
        	var itemCode = protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'code');
        	
            addjson.push(itemName);
            addItemRealtimeSort.push(itemRealtimeSort);
            var matrix_value = '0,0,0';
            matrixData += itemName + "##"
            + realtimeOverview+ "##"
            + realtimeOverviewSort+ "##"
            + realtimeData+ "##"
            + itemRealtimeSort+ "##"
            + realtimeColor+ "##"
            + realtimeBgColor+ "##" //6
            + historyOverview+ "##"
            + historyOverviewSort+ "##"
            + historyData+ "##"
            + itemHistorySort+ "##"
            + historyColor+ "##"
            + historyBgColor+ "##" //12
            + itemShowLevel+ "##" 
            + realtimeCurveConfigStr+ "##" 
            + historyCurveConfigStr + "##" 
            + resolutionMode+ "##"
            + itemAddr + "##" 
            + bitIndex +"##"
            + type +"##"
            + itemCode +"##" //20
            + matrix_value+ "|";
        }
    }

    matrixData = matrixData.substring(0, matrixData.length - 1);
    var addparams = "" + addjson.join(",");
    var addRealtimeSortParams = "" + addItemRealtimeSort.join(",");
    var matrixCodes_ = "" + matrixData;
    Ext.Ajax.request({
        url: addUrl,
        method: "POST",
        async :  false,
        params: {
            protocol :protocol,
            unitId:unitId,
            itemType:0,
            matrixCodes: matrixCodes_
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

var grantDisplayCalItemsPermission = function () {
	var DisplayUnitConfigTreeSelectRow= Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
	if (protocolDisplayUnitCalItemsConfigHandsontableHelper == null ||DisplayUnitConfigTreeSelectRow=='') {
        return false;
    }
	var selectedItem=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(DisplayUnitConfigTreeSelectRow);
    var calItemsData = protocolDisplayUnitCalItemsConfigHandsontableHelper.hot.getData();
    var addUrl = context + '/acquisitionUnitManagerController/grantCalItemsToDisplayUnitPermission'
    // 添加条件
    var addjson = [];
    var addItemRealtimeSort=[];
    var matrixData = "";
    var matrixDataArr = "";
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    
    var unitCode = selectedItem.data.code;
    var unitId = selectedItem.data.id;
    var protocol=selectedItem.data.protocol;
    if (!isNotVal(unitCode)) {
        return false
    }

    Ext.Array.each(calItemsData, function (name, index, countriesItSelf) {
        if ((calItemsData[index][0]+'')==='true') {
        	var itemName = calItemsData[index][2];
        	
        	var itemShowLevel = calItemsData[index][4];
        	
        	var itemRealtimeSort = calItemsData[index][5];
        	var realtimeColor= calItemsData[index][6];
        	var realtimeBgColor= calItemsData[index][7];
        	var realtimeCurveConfigStr="";
        	var realtimeCurveConfig=null;
			if(isNotVal(calItemsData[index][8]) && isNotVal(calItemsData[index][13])){
				realtimeCurveConfig=calItemsData[index][13];
				realtimeCurveConfigStr=JSON.stringify(realtimeCurveConfig);
			}
        	
        	var itemHistorySort = calItemsData[index][9];
        	var historyColor= calItemsData[index][10];
        	var historyBgColor= calItemsData[index][11];
        	var historyCurveConfigStr="";
			var historyCurveConfig=null;
			if(isNotVal(calItemsData[index][12]) && isNotVal(calItemsData[index][14])){
				historyCurveConfig=calItemsData[index][14];
				historyCurveConfigStr=JSON.stringify(historyCurveConfig);
			}
        	
        	var itemCode = calItemsData[index][15];
        	
            addjson.push(itemCode);
            addItemRealtimeSort.push(itemRealtimeSort);
            var matrix_value = '0,0,0';
            matrixData += itemName + "##"
            + itemCode+ "##"
            + itemRealtimeSort+ "##"
            + realtimeColor+ "##"
            + realtimeBgColor+ "##"
            + itemHistorySort+ "##"
            + historyColor+ "##"
            + historyBgColor+ "##"
            + itemShowLevel+ "##" 
            + realtimeCurveConfigStr+ "##" 
            + historyCurveConfigStr + "##" 
            + matrix_value+ "|";
        }
    });
    matrixData = matrixData.substring(0, matrixData.length - 1);
    var addparams = "" + addjson.join(",");
    var addRealtimeSortParams = "" + addItemRealtimeSort.join(",");
    var matrixCodes_ = "" + matrixData;
    Ext.Ajax.request({
        url: addUrl,
        method: "POST",
        async :  false,
        params: {
            params: addparams,
            realtimeSorts: addRealtimeSortParams,
            protocol :protocol,
            unitCode: unitCode,
            unitId:unitId,
            itemType:1,
            matrixCodes: matrixCodes_
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

var grantDisplayCtrlItemsPermission = function () {
	var DisplayUnitConfigTreeSelectRow= Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
	if (protocolDisplayUnitCtrlItemsConfigHandsontableHelper == null ||DisplayUnitConfigTreeSelectRow=='') {
        return false;
    }
	var selectedItem=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(DisplayUnitConfigTreeSelectRow);
    var ctrlItemsData = protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.getData();
    var addUrl = context + '/acquisitionUnitManagerController/grantCtrlItemsToDisplayUnitPermission'
    // 添加条件
    var addjson = [];
    var addItemRealtimeSort=[];
    var matrixData = "";
    var matrixDataArr = "";
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    
    var unitCode = selectedItem.data.code;
    var unitId = selectedItem.data.id;
    var protocol=selectedItem.data.protocol;
    if (!isNotVal(unitCode)) {
        return false
    }

    Ext.Array.each(ctrlItemsData, function (name, index, countriesItSelf) {
        if ((ctrlItemsData[index][0]+'')==='true') {
        	var itemName = ctrlItemsData[index][2];
        	
        	var itemShowLevel = ctrlItemsData[index][4];
        	var itemRealtimeSort = ctrlItemsData[index][5];
        	
        	var resolutionMode = ctrlItemsData[index][6];
        	var itemAddr = ctrlItemsData[index][7];
        	var bitIndex=ctrlItemsData[index][8];
            
            addjson.push(itemName);
            addItemRealtimeSort.push(itemRealtimeSort);
            var matrix_value = '0,0,0';
            matrixData += itemName + ":"
            + itemRealtimeSort+ ":"
            + itemShowLevel+ ":" 
            
            + resolutionMode+ ":"
            + itemAddr + ":" 
            + bitIndex +":"
            
            + matrix_value+ "|";
        }
    });

    matrixData = matrixData.substring(0, matrixData.length - 1);
    var addparams = "" + addjson.join(",");
    var addRealtimeSortParams = "" + addItemRealtimeSort.join(",");
    var matrixCodes_ = "" + matrixData;
    Ext.Ajax.request({
        url: addUrl,
        method: "POST",
        async :  false,
        params: {
            params: addparams,
            realtimeSorts: addRealtimeSortParams,
            protocol :protocol,
            unitCode: unitCode,
            unitId:unitId,
            itemType:2,
            matrixCodes: matrixCodes_
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

var grantDisplayInputItemsPermission = function () {
	var DisplayUnitConfigTreeSelectRow= Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
	if (protocolDisplayUnitInputItemsConfigHandsontableHelper == null ||DisplayUnitConfigTreeSelectRow=='') {
        return false;
    }
	var selectedItem=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(DisplayUnitConfigTreeSelectRow);
    var inputItemsData = protocolDisplayUnitInputItemsConfigHandsontableHelper.hot.getData();
    var addUrl = context + '/acquisitionUnitManagerController/grantInputItemsToDisplayUnitPermission'
    // 添加条件
    var addjson = [];
    var addItemRealtimeSort=[];
    var matrixData = "";
    var matrixDataArr = "";
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    
    var unitCode = selectedItem.data.code;
    var unitId = selectedItem.data.id;
    var protocol=selectedItem.data.protocol;
    if (!isNotVal(unitCode)) {
        return false
    }

    Ext.Array.each(inputItemsData, function (name, index, countriesItSelf) {
        if ((inputItemsData[index][0]+'')==='true') {
        	var itemName = inputItemsData[index][2];
        	
        	var itemShowLevel = inputItemsData[index][4];
        	
        	var itemRealtimeSort = inputItemsData[index][5];
        	var realtimeColor= inputItemsData[index][6];
        	var realtimeBgColor= inputItemsData[index][7];
        	var realtimeCurveConfigStr="";
        	var realtimeCurveConfig=null;
			if(isNotVal(inputItemsData[index][8]) && isNotVal(inputItemsData[index][13])){
				realtimeCurveConfig=inputItemsData[index][13];
				realtimeCurveConfigStr=JSON.stringify(realtimeCurveConfig);
			}
        	
        	var itemHistorySort = inputItemsData[index][9];
        	var historyColor= inputItemsData[index][10];
        	var historyBgColor= inputItemsData[index][11];
        	var historyCurveConfigStr="";
			var historyCurveConfig=null;
			if(isNotVal(inputItemsData[index][12]) && isNotVal(inputItemsData[index][14])){
				historyCurveConfig=inputItemsData[index][14];
				historyCurveConfigStr=JSON.stringify(historyCurveConfig);
			}
        	
        	var itemCode = inputItemsData[index][15];
        	
            addjson.push(itemCode);
            addItemRealtimeSort.push(itemRealtimeSort);
            var matrix_value = '0,0,0';
            matrixData += itemName + "##"
            + itemCode+ "##"
            + itemRealtimeSort+ "##"
            + realtimeColor+ "##"
            + realtimeBgColor+ "##"
            + itemHistorySort+ "##"
            + historyColor+ "##"
            + historyBgColor+ "##"
            + itemShowLevel+ "##" 
            + realtimeCurveConfigStr+ "##" 
            + historyCurveConfigStr + "##" 
            + matrix_value+ "|";
        }
    });
    matrixData = matrixData.substring(0, matrixData.length - 1);
    var addparams = "" + addjson.join(",");
    var addRealtimeSortParams = "" + addItemRealtimeSort.join(",");
    var matrixCodes_ = "" + matrixData;
    Ext.Ajax.request({
        url: addUrl,
        method: "POST",
        async :  false,
        params: {
            params: addparams,
            realtimeSorts: addRealtimeSortParams,
            protocol :protocol,
            unitCode: unitCode,
            unitId:unitId,
            itemType:3,
            matrixCodes: matrixCodes_
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