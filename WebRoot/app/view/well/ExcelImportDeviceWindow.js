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
                    fieldLabel: loginUserLanguageResource.uploadFile,
                    labelWidth: 60,
                    width:'100%',
                    msgTarget: 'side',
                    allowBlank: false,
                    anchor: '100%',
                    draggable:true,
                    buttonText: loginUserLanguageResource.selectUploadFile,
                    accept:'.xls',
                    listeners:{
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
                fieldLabel: loginUserLanguageResource.deviceType,
                id: 'excelImportDeviceType_Id',
                value: ''
            },{
                xtype: "hidden",
                fieldLabel: loginUserLanguageResource.deviceType,
                id: 'excelImportDeviceOrg_Id',
                value: ''
            },'->',{
    	    	xtype: 'button',
                text: loginUserLanguageResource.save,
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
            		resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(importDeviceHandsontableHelper!=null&&importDeviceHandsontableHelper.hot!=null&&importDeviceHandsontableHelper.hot!=undefined){
//                    		importDeviceHandsontableHelper.hot.refreshDimensions();
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		importDeviceHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
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
                        	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.applicationScenarios1+"', '"+loginUserLanguageResource.applicationScenarios0+"']}";
                        } else if (result.columns[i].dataIndex.toUpperCase() === "sortNum".toUpperCase()) {
                            columns += "{data:'" + result.columns[i].dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importDeviceHandsontableHelper);}}";
                        } else if (result.columns[i].dataIndex.toUpperCase() === "statusName".toUpperCase()) {
                        	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.enable+"', '"+loginUserLanguageResource.disable+"']}";
                        } else if (result.columns[i].dataIndex.toUpperCase() === "pumpType".toUpperCase()) {
                        	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['杆式泵', '管式泵']}";
                        } else if (result.columns[i].dataIndex.toUpperCase() === "barrelType".toUpperCase()) {
                        	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.barrelType_L+"', '"+loginUserLanguageResource.barrelType_H+"']}";
                        } else if (result.columns[i].dataIndex.toUpperCase() === "pumpGrade".toUpperCase()) {
                        	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['1', '2', '3', '4', '5']}";
                        } else if (result.columns[i].dataIndex.toUpperCase() === "rodGrade1".toUpperCase() || result.columns[i].dataIndex.toUpperCase() === "rodGrade2".toUpperCase()
                        		|| result.columns[i].dataIndex.toUpperCase() === "rodGrade3".toUpperCase() || result.columns[i].dataIndex.toUpperCase() === "rodGrade4".toUpperCase()) {
                        	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['A', 'B', 'C', 'D', 'K', 'KD', 'HL', 'HY']}";
                        } else if (result.columns[i].dataIndex.toUpperCase() === "crankRotationDirection".toUpperCase()) {
                        	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.clockwise+"', '"+loginUserLanguageResource.anticlockwise+"']}";
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
            	Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.uploadFail+"</font>】"+loginUserLanguageResource.uploadFileTooLarge);
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
                    indicators: false,
                    copyPasteEnabled: false
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
                }
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
                		CreateAndLoadSRPDeviceInfoTable();
                	}else if(parseInt(deviceType)==201){
                		CreateAndLoadPCPDeviceInfoTable();
                	}
                	rdata = Ext.JSON.decode(response.responseText);
                	if (rdata.success&&rdata.collisionCount==0&&rdata.overlayCount==0) {
                    	Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.saveSuccessfully);
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
                        Ext.MessageBox.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
                    }
                },
                failure: function () {
                    Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.requestFailure);
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