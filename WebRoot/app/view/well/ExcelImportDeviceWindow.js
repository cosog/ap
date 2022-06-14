var importDeviceHandsontableHelper=null;
Ext.define("AP.view.well.ExcelImportDeviceWindow", {
    extend: 'Ext.window.Window',
    id:'ExcelImportDeviceWindow_Id',
    alias: 'widget.excelImportDeviceWindow',
    layout: 'fit',
    title:'设备导入',
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 1200,
    minWidth: 1200,
    height: 600,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	tbar: [{
        		xtype:'form',
        		id:'DeviceInfoImportForm_Id',
        		width: 300,
        	    bodyPadding: 0,
        	    frame: true,
        	    items: [{
        	    	xtype: 'filefield',
                	id:'DeviceInfoImportFilefield_Id',
                    name: 'file',
                    fieldLabel: '上传文件',
                    labelWidth: 60,
                    width:'100%',
                    msgTarget: 'side',
                    allowBlank: false,
                    anchor: '100%',
                    draggable:true,
                    buttonText: '请选择上传文件',
                    accept:'.xls',
                    listeners:{
//                        afterrender:function(cmp){
//                            cmp.fileInputEl.set({
//                                multiple:'multiple'
//                            });
//                        },
                        change:function(cmp){
                        	submitImportedDeviceFile();
                        }
                    }
        	    }]
    		},{
                xtype: 'label',
                id: 'excelImportDeviceWinOgLabel_Id',
                margin: '0 0 0 0',
                html: ''
            },{
                xtype: "hidden",
                fieldLabel: '设备类型',
                id: 'excelImportDeviceType_Id',
                value: ''
            },{
                xtype: "hidden",
                fieldLabel: '设备类型',
                id: 'excelImportDeviceOrg_Id',
                value: ''
            },'->',{
    	    	xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                handler: function (v, o) {
                	importDeviceHandsontableHelper.saveData();
                }
    	    }],
            layout: 'border',
            items: [{
            	region: 'center',
            	html: '<div id="excelImportedDeviceTableDiv_Id" style="width:100%;height:100%;"></div>',
            	listeners: {
            		resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    	if(importDeviceHandsontableHelper!=null&&importDeviceHandsontableHelper.hot!=null&&importDeviceHandsontableHelper.hot!=undefined){
                    		importDeviceHandsontableHelper.hot.refreshDimensions();
                    	}
                    }
            	}
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	if(importDeviceHandsontableHelper!=null){
    					if(importDeviceHandsontableHelper.hot!=undefined){
    						importDeviceHandsontableHelper.hot.destroy();
    					}
    					importDeviceHandsontableHelper=null;
    				}
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function submitImportedDeviceFile() {
	var form = Ext.getCmp("DeviceInfoImportForm_Id");
	var deviceType=Ext.getCmp("excelImportDeviceType_Id").getValue();
    if(form.isValid()) {
        form.submit({
            url: context + '/wellInformationManagerController/getImportedDeviceFile?deviceType='+deviceType,
            timeout: 1000*60*10,
            method:'post',
            waitMsg: '文件上传中...',
            success: function(response, action) {
            	var result = action.result;
                if (importDeviceHandsontableHelper == null || importDeviceHandsontableHelper.hot == null || importDeviceHandsontableHelper.hot == undefined) {
                    importDeviceHandsontableHelper = ImportDeviceHandsontableHelper.createNew("excelImportedDeviceTableDiv_Id");
                    var colHeaders = "[";
                    var columns = "[";

                    for (var i = 0; i < result.columns.length; i++) {
                        colHeaders += "'" + result.columns[i].header + "'";
                        if (result.columns[i].dataIndex.toUpperCase() === "orgName".toUpperCase()) {
                            columns += "{data:'" + result.columns[i].dataIndex + "',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Org(val, callback,this.row, this.col,importDeviceHandsontableHelper);}}";
                        } 
//                        else if (result.columns[i].dataIndex.toUpperCase() === "instanceName".toUpperCase()) {
//                            var source = "[";
//                            for (var j = 0; j < result.instanceDropdownData.length; j++) {
//                                source += "\'" + result.instanceDropdownData[j] + "\'";
//                                if (j < result.instanceDropdownData.length - 1) {
//                                    source += ",";
//                                }
//                            }
//                            source += "]";
//                            columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
//                        } 
//                        else if (result.columns[i].dataIndex.toUpperCase() === "displayInstanceName".toUpperCase()) {
//                            var source = "[";
//                            for (var j = 0; j < result.displayInstanceDropdownData.length; j++) {
//                                source += "\'" + result.displayInstanceDropdownData[j] + "\'";
//                                if (j < result.displayInstanceDropdownData.length - 1) {
//                                    source += ",";
//                                }
//                            }
//                            source += "]";
//                            columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
//                        } 
//                        else if (result.columns[i].dataIndex.toUpperCase() === "alarmInstanceName".toUpperCase()) {
//                            var source = "[";
//                            for (var j = 0; j < result.alarmInstanceDropdownData.length; j++) {
//                                source += "\'" + result.alarmInstanceDropdownData[j] + "\'";
//                                if (j < result.alarmInstanceDropdownData.length - 1) {
//                                    source += ",";
//                                }
//                            }
//                            source += "]";
//                            columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
//                        }
                        else if (result.columns[i].dataIndex.toUpperCase() === "applicationScenariosName".toUpperCase()) {
                        	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['油井', '煤层气井']}";
                        } else if (result.columns[i].dataIndex.toUpperCase() === "sortNum".toUpperCase()) {
                            columns += "{data:'" + result.columns[i].dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importDeviceHandsontableHelper);}}";
                        } else if (result.columns[i].dataIndex.toUpperCase() === "statusName".toUpperCase()) {
                        	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['使能', '失效']}";
                        } else if (result.columns[i].dataIndex.toUpperCase() === "pumpType".toUpperCase()) {
                        	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['杆式泵', '管式泵']}";
                        } else if (result.columns[i].dataIndex.toUpperCase() === "barrelType".toUpperCase()) {
                        	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['组合泵', '整筒泵']}";
                        } else if (result.columns[i].dataIndex.toUpperCase() === "pumpGrade".toUpperCase()) {
                        	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['1', '2', '3', '4', '5']}";
                        } else if (result.columns[i].dataIndex.toUpperCase() === "rodGrade1".toUpperCase() || result.columns[i].dataIndex.toUpperCase() === "rodGrade2".toUpperCase()
                        		|| result.columns[i].dataIndex.toUpperCase() === "rodGrade3".toUpperCase() || result.columns[i].dataIndex.toUpperCase() === "rodGrade4".toUpperCase()) {
                        	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['A', 'B', 'C', 'D', 'K', 'KD', 'HL', 'HY']}";
                        } else if (result.columns[i].dataIndex.toUpperCase() === "crankRotationDirection".toUpperCase()) {
                        	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['顺时针', '逆时针']}";
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
                    importDeviceHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
                    importDeviceHandsontableHelper.columns = Ext.JSON.decode(columns);
                    importDeviceHandsontableHelper.createTable(result.totalRoot);
                } else {
                	importDeviceHandsontableHelper.hot.loadData(result.totalRoot);
                }
            },
            failure : function() {
            	Ext.Msg.alert("提示", "【<font color=red>上传失败 </font>】文件内容过大，请减少内容后重新上传！");
			}
        });
    }
    return false;
};

var ImportDeviceHandsontableHelper = {
    createNew: function (divid) {
        var importDeviceHandsontableHelper = {};
        importDeviceHandsontableHelper.hot = '';
        importDeviceHandsontableHelper.divid = divid;
        importDeviceHandsontableHelper.validresult = true; //数据校验
        importDeviceHandsontableHelper.colHeaders = [];
        importDeviceHandsontableHelper.columns = [];

        importDeviceHandsontableHelper.AllData = {};
        importDeviceHandsontableHelper.updatelist = [];
        importDeviceHandsontableHelper.delidslist = [];
        importDeviceHandsontableHelper.insertlist = [];

        importDeviceHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
        }

        importDeviceHandsontableHelper.createTable = function (data) {
            $('#' + importDeviceHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + importDeviceHandsontableHelper.divid);
            importDeviceHandsontableHelper.hot = new Handsontable(hotElement, {
            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            	data: data,
                hiddenColumns: {
                    columns: [0],
                    indicators: false
                },
                columns: importDeviceHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: true, //显示行头
                colHeaders: importDeviceHandsontableHelper.colHeaders, //显示列头
                columnSorting: true, //允许排序
                contextMenu: {
                    items: {
                        "row_above": {
                            name: '向上插入一行',
                        },
                        "row_below": {
                            name: '向下插入一行',
                        },
                        "col_left": {
                            name: '向左插入一列',
                        },
                        "col_right": {
                            name: '向右插入一列',
                        },
                        "remove_row": {
                            name: '删除行',
                        },
                        "remove_col": {
                            name: '删除列',
                        },
                        "merge_cell": {
                            name: '合并单元格',
                        },
                        "copy": {
                            name: '复制',
                        },
                        "cut": {
                            name: '剪切',
                        },
                        "paste": {
                            name: '粘贴',
                            disabled: function () {
                            },
                            callback: function () {
                            }
                        }
                    }
                }, //右键菜单展示
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
                    return cellProperties;
                },
                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
                	
                },
                afterDestroy: function () {
                },
                beforeRemoveRow: function (index, amount) {},
                afterChange: function (changes, source) {}
            });
        }
        //保存数据
        importDeviceHandsontableHelper.saveData = function () {
        	var orgId = Ext.getCmp('excelImportDeviceOrg_Id').getValue();
            var deviceType = Ext.getCmp('excelImportDeviceType_Id').getValue();
            var isCheckout=1;
            var saveDate={};
            saveDate.updatelist=[];
            var excelImportData=importDeviceHandsontableHelper.hot.getData();
            for(var i=0;i<excelImportData.length;i++){
            	if(isNotVal(excelImportData[i][1])){
            		var data = "{";
                    for (var j = 0; j < importDeviceHandsontableHelper.columns.length; j++) {
                        data += importDeviceHandsontableHelper.columns[j].data + ":'" + excelImportData[i][j] + "'";
                        if (j < importDeviceHandsontableHelper.columns.length - 1) {
                            data += ",";
                        }
                    }
                    data += "}";
                    var record=Ext.JSON.decode(data);
                    record.id=i;
                    saveDate.updatelist.push(record);
            	}
            }
        	Ext.Ajax.request({
                method: 'POST',
                url: context + '/wellInformationManagerController/excelImportDevice',
                success: function (response) {
                    Ext.getCmp("ExcelImportDeviceWindow_Id").close();
                    if(parseInt(deviceType)==101){
                		CreateAndLoadRPCDeviceInfoTable();
                	}else if(parseInt(deviceType)==201){
                		CreateAndLoadPCPDeviceInfoTable();
                	}
                	rdata = Ext.JSON.decode(response.responseText);
                	if (rdata.success&&rdata.collisionCount==0&&rdata.overlayCount==0) {
                    	Ext.MessageBox.alert("信息", "保存成功");
                        //保存以后重置全局容器
                        importDeviceHandsontableHelper.clearContainer();
                    }else if(rdata.success&&(rdata.collisionCount>0 || rdata.overlayCount>0)){
                    	var window = Ext.create("AP.view.well.BatchAddDeviceCollisionDataWindow", {
                            title: '异常数据处理'
                        });
                        Ext.getCmp("excelImportCollisionDeviceType_Id").setValue(deviceType);
                        Ext.getCmp("excelImportCollisionDeviceOrg_Id").setValue(orgId);
                        if(rdata.collisionCount==0){
                        	Ext.getCmp("BatchAddDeviceCollisionDataPanel_Id").hide();
                        	Ext.getCmp("BatchAddDeviceOverlayDataPanel_Id").setHeight('100%');
                        }else if(rdata.overlayCount==0){
                        	Ext.getCmp("BatchAddDeviceOverlayDataPanel_Id").hide();
                        	Ext.getCmp("BatchAddDeviceCollisionDataPanel_Id").setHeight('100%');
                        }
                        window.show();
                        if(rdata.collisionCount>0){
                        	CreateAndLoadBatchAddDeviceCollisionDataTable(rdata);
                        }
                        if(rdata.overlayCount>0){
                        	CreateAndLoadBatchAddDeviceOverlayDataTable(rdata);
                        }
                    } else {
                        Ext.MessageBox.alert("信息", "数据保存失败");
                    }
                },
                failure: function () {
                    Ext.MessageBox.alert("信息", "请求失败");
                    importDeviceHandsontableHelper.clearContainer();
                },
                params: {
                    data: JSON.stringify(saveDate),
                    orgId: orgId,
                    deviceType: deviceType,
                    isCheckout: isCheckout
                }
            });
        }

        importDeviceHandsontableHelper.clearContainer = function () {
            importDeviceHandsontableHelper.AllData = {};
            importDeviceHandsontableHelper.updatelist = [];
            importDeviceHandsontableHelper.delidslist = [];
            importDeviceHandsontableHelper.insertlist = [];
        }

        return importDeviceHandsontableHelper;
    }
};