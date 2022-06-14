var batchAddDeviceCollisionDataHandsontableHelper=null;
var batchAddDeviceOverlayDataHandsontableHelper=null;
Ext.define("AP.view.well.BatchAddDeviceCollisionDataWindow", {
    extend: 'Ext.window.Window',
    id:'BatchAddDeviceCollisionDataWindow_Id',
    alias: 'widget.batchAddDeviceCollisionDataWindow',
    layout: 'fit',
    title:'设备批量添加-冲突数据',
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 1400,
    minWidth: 1400,
    height: 600,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	tbar: [{
                xtype: 'label',
                id: 'batchAddDeviceCollisionInfoLabel_Id',
                margin: '0 0 0 0',
                html: ''
            },{
                xtype: "hidden",
                fieldLabel: '设备类型',
                id: 'batchAddCollisionDeviceType_Id',
                value: ''
            },{
                xtype: "hidden",
                fieldLabel: '所属组织',
                id: 'batchAddCollisionDeviceOrg_Id',
                value: ''
            },'->',{
                xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                handler: function (v, o) {
                	var orgId = Ext.getCmp('batchAddCollisionDeviceOrg_Id').getValue();
                    var deviceType = Ext.getCmp('batchAddCollisionDeviceType_Id').getValue();
                    var isCheckout=0;
                    var saveDate={};
                    saveDate.updatelist=[];
                    if(batchAddDeviceCollisionDataHandsontableHelper!=null&&batchAddDeviceCollisionDataHandsontableHelper.hot!=null&&batchAddDeviceCollisionDataHandsontableHelper.hot!=undefined){
                    	var batchAddData=batchAddDeviceCollisionDataHandsontableHelper.hot.getData();
                        for(var i=0;i<batchAddData.length;i++){
                        	if(isNotVal(batchAddData[i][1])){
                        		var data = "{";
                                for (var j = 0; j < batchAddDeviceCollisionDataHandsontableHelper.columns.length; j++) {
                                    data += batchAddDeviceCollisionDataHandsontableHelper.columns[j].data + ":'" + batchAddData[i][j] + "'";
                                    if (j < batchAddDeviceCollisionDataHandsontableHelper.columns.length - 1) {
                                        data += ",";
                                    }
                                }
                                data += "}";
                                var record=Ext.JSON.decode(data);
                                record.id=i;
                                saveDate.updatelist.push(record);
                        	}
                        }
                    }
                    if(batchAddDeviceOverlayDataHandsontableHelper!=null&&batchAddDeviceOverlayDataHandsontableHelper.hot!=null&&batchAddDeviceOverlayDataHandsontableHelper.hot!=undefined){
                    	var batchAddData=batchAddDeviceOverlayDataHandsontableHelper.hot.getData();
                        for(var i=0;i<batchAddData.length;i++){
                        	if(isNotVal(batchAddData[i][1])){
                        		var data = "{";
                                for (var j = 0; j < batchAddDeviceOverlayDataHandsontableHelper.columns.length; j++) {
                                    data += batchAddDeviceOverlayDataHandsontableHelper.columns[j].data + ":'" + batchAddData[i][j] + "'";
                                    if (j < batchAddDeviceOverlayDataHandsontableHelper.columns.length - 1) {
                                        data += ",";
                                    }
                                }
                                data += "}";
                                var record=Ext.JSON.decode(data);
                                record.id=i;
                                saveDate.updatelist.push(record);
                        	}
                        }
                    }
                    
                    Ext.Ajax.request({
                        method: 'POST',
                        url: context + '/wellInformationManagerController/batchAddDevice',
                        success: function (response) {
                        	if(parseInt(deviceType)==101){
                        		CreateAndLoadRPCDeviceInfoTable();
                        	}else if(parseInt(deviceType)==201){
                        		CreateAndLoadPCPDeviceInfoTable();
                        	}
                        	rdata = Ext.JSON.decode(response.responseText);
                            if (rdata.success&&rdata.collisionCount==0&&rdata.overlayCount==0) {
                            	Ext.getCmp("BatchAddDeviceCollisionDataWindow_Id").close();
                            	Ext.MessageBox.alert("信息", "保存成功");
                            }else if(rdata.success&&(rdata.collisionCount>0 || rdata.overlayCount>0)){
                                Ext.getCmp("batchAddCollisionDeviceType_Id").setValue(deviceType);
                                Ext.getCmp("batchAddCollisionDeviceOrg_Id").setValue(orgId);
                                if(rdata.collisionCount==0){
                                	if(batchAddDeviceCollisionDataHandsontableHelper!=null){
                    					if(batchAddDeviceCollisionDataHandsontableHelper.hot!=undefined){
                    						batchAddDeviceCollisionDataHandsontableHelper.hot.destroy();
                    					}
                    					batchAddDeviceCollisionDataHandsontableHelper=null;
                    				}
                                	Ext.getCmp("BatchAddDeviceCollisionDataPanel_Id").hide();
                                	Ext.getCmp("BatchAddDeviceOverlayDataPanel_Id").setHeight('100%');
                                	Ext.getCmp("BatchAddDeviceOverlayDataPanel_Id").show();
                                }else if(rdata.overlayCount==0){
                                	if(batchAddDeviceOverlayDataHandsontableHelper!=null){
                    					if(batchAddDeviceOverlayDataHandsontableHelper.hot!=undefined){
                    						batchAddDeviceOverlayDataHandsontableHelper.hot.destroy();
                    					}
                    					batchAddDeviceOverlayDataHandsontableHelper=null;
                    				}
                                	Ext.getCmp("BatchAddDeviceOverlayDataPanel_Id").hide();
                                	Ext.getCmp("BatchAddDeviceCollisionDataPanel_Id").setHeight('100%');
                                	Ext.getCmp("BatchAddDeviceCollisionDataPanel_Id").show();
                                }
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
                            batchAddDeviceHandsontableHelper.clearContainer();
                        },
                        params: {
                            data: JSON.stringify(saveDate),
                            orgId: orgId,
                            deviceType: deviceType,
                            isCheckout: isCheckout
                        }
                    });
                    
                    
                    
                    
                }
            }],
            layout: 'border',
            items: [{
            	region: 'north',
            	height: '50%',
            	id:'BatchAddDeviceCollisionDataPanel_Id',
            	title: '冲突数据(<font color=red>冲突数据无法保存，请排查冲突内容</font>)',
            	html: '<div id="BatchAddDeviceCollisionDataTableDiv_Id" style="width:100%;height:100%;"></div>',
            	listeners: {
            		resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    	if(batchAddDeviceCollisionDataHandsontableHelper!=null&&batchAddDeviceCollisionDataHandsontableHelper.hot!=null&&batchAddDeviceCollisionDataHandsontableHelper.hot!=undefined){
                    		batchAddDeviceCollisionDataHandsontableHelper.hot.refreshDimensions();
                    	}
                    }
            	}
            },{
            	region: 'south',
            	height: '50%',
            	id:'BatchAddDeviceOverlayDataPanel_Id',
            	title: '已有记录(<font color=red>继续保存，表中数据将覆盖已有记录</font>)',
            	html: '<div id="BatchAddDeviceOverlayDataTableDiv_Id" style="width:100%;height:100%;"></div>',
            	listeners: {
            		resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    	if(batchAddDeviceOverlayDataHandsontableHelper!=null&&batchAddDeviceOverlayDataHandsontableHelper.hot!=null&&batchAddDeviceOverlayDataHandsontableHelper.hot!=undefined){
                    		batchAddDeviceOverlayDataHandsontableHelper.hot.refreshDimensions();
                    	}
                    }
            	}
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	if(batchAddDeviceCollisionDataHandsontableHelper!=null){
    					if(batchAddDeviceCollisionDataHandsontableHelper.hot!=undefined){
    						batchAddDeviceCollisionDataHandsontableHelper.hot.destroy();
    					}
    					batchAddDeviceCollisionDataHandsontableHelper=null;
    				}
                	if(batchAddDeviceOverlayDataHandsontableHelper!=null){
    					if(batchAddDeviceOverlayDataHandsontableHelper.hot!=undefined){
    						batchAddDeviceOverlayDataHandsontableHelper.hot.destroy();
    					}
    					batchAddDeviceOverlayDataHandsontableHelper=null;
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
function CreateAndLoadBatchAddDeviceCollisionDataTable(result) {
	if (batchAddDeviceCollisionDataHandsontableHelper == null || batchAddDeviceCollisionDataHandsontableHelper.hot == null || batchAddDeviceCollisionDataHandsontableHelper.hot == undefined) {
        batchAddDeviceCollisionDataHandsontableHelper = BatchAddDeviceCollisionDataHandsontableHelper.createNew("BatchAddDeviceCollisionDataTableDiv_Id");
        var colHeaders = "[";
        var columns = "[";

        for (var i = 0; i < result.columns.length; i++) {
            colHeaders += "'" + result.columns[i].header + "'";
            if (result.columns[i].dataIndex.toUpperCase() === "orgName".toUpperCase()) {
                columns += "{data:'" + result.columns[i].dataIndex + "',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Org(val, callback,this.row, this.col,batchAddDeviceCollisionDataHandsontableHelper);}}";
            } else if (result.columns[i].dataIndex.toUpperCase() === "liftingTypeName".toUpperCase()) {
                if (pcpHidden) {
                    columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['抽油机']}";
                } else {
                    columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['抽油机', '螺杆泵']}";
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
            } else if (result.columns[i].dataIndex.toUpperCase() === "sortNum".toUpperCase()) {
                columns += "{data:'" + result.columns[i].dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,batchAddDeviceCollisionDataHandsontableHelper);}}";
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
        colHeaders += ",'冲突信息'";
        columns += ",{data:'dataInfo'}";
        colHeaders += "]";
        columns += "]";
        batchAddDeviceCollisionDataHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
        batchAddDeviceCollisionDataHandsontableHelper.columns = Ext.JSON.decode(columns);
        batchAddDeviceCollisionDataHandsontableHelper.createTable(result.collisionList);
    } else {
    	batchAddDeviceCollisionDataHandsontableHelper.hot.loadData(result.collisionList);
    }
};

var BatchAddDeviceCollisionDataHandsontableHelper = {
    createNew: function (divid) {
        var batchAddDeviceCollisionDataHandsontableHelper = {};
        batchAddDeviceCollisionDataHandsontableHelper.hot = '';
        batchAddDeviceCollisionDataHandsontableHelper.divid = divid;
        batchAddDeviceCollisionDataHandsontableHelper.validresult = true; //数据校验
        batchAddDeviceCollisionDataHandsontableHelper.colHeaders = [];
        batchAddDeviceCollisionDataHandsontableHelper.columns = [];

        batchAddDeviceCollisionDataHandsontableHelper.AllData = {};
        batchAddDeviceCollisionDataHandsontableHelper.updatelist = [];
        batchAddDeviceCollisionDataHandsontableHelper.delidslist = [];
        batchAddDeviceCollisionDataHandsontableHelper.insertlist = [];

        batchAddDeviceCollisionDataHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
        }
        
        batchAddDeviceCollisionDataHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.color = '#ff0000';
        }

        batchAddDeviceCollisionDataHandsontableHelper.createTable = function (data) {
            $('#' + batchAddDeviceCollisionDataHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + batchAddDeviceCollisionDataHandsontableHelper.divid);
            batchAddDeviceCollisionDataHandsontableHelper.hot = new Handsontable(hotElement, {
            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            	data: data,
                hiddenColumns: {
                    columns: [0],
                    indicators: false
                },
                columns: batchAddDeviceCollisionDataHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: true, //显示行头
                colHeaders: batchAddDeviceCollisionDataHandsontableHelper.colHeaders, //显示列头
                columnSorting: true, //允许排序
                allowInsertRow:false,
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
                    if(batchAddDeviceCollisionDataHandsontableHelper.columns[visualColIndex].data.toUpperCase()=='dataInfo'.toUpperCase()){
                    	cellProperties.readOnly = true;
                    	cellProperties.renderer = batchAddDeviceCollisionDataHandsontableHelper.addBoldBg;
                    }
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
        batchAddDeviceCollisionDataHandsontableHelper.saveData = function () {}

        batchAddDeviceCollisionDataHandsontableHelper.clearContainer = function () {
            batchAddDeviceCollisionDataHandsontableHelper.AllData = {};
            batchAddDeviceCollisionDataHandsontableHelper.updatelist = [];
            batchAddDeviceCollisionDataHandsontableHelper.delidslist = [];
            batchAddDeviceCollisionDataHandsontableHelper.insertlist = [];
        }

        return batchAddDeviceCollisionDataHandsontableHelper;
    }
};

function CreateAndLoadBatchAddDeviceOverlayDataTable(result) {
	if (batchAddDeviceOverlayDataHandsontableHelper == null || batchAddDeviceOverlayDataHandsontableHelper.hot == null || batchAddDeviceOverlayDataHandsontableHelper.hot == undefined) {
        batchAddDeviceOverlayDataHandsontableHelper = BatchAddDeviceOverlayDataHandsontableHelper.createNew("BatchAddDeviceOverlayDataTableDiv_Id");
        var colHeaders = "[";
        var columns = "[";

        for (var i = 0; i < result.columns.length; i++) {
            colHeaders += "'" + result.columns[i].header + "'";
            if (result.columns[i].dataIndex.toUpperCase() === "orgName".toUpperCase()) {
                columns += "{data:'" + result.columns[i].dataIndex + "',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Org(val, callback,this.row, this.col,batchAddDeviceOverlayDataHandsontableHelper);}}";
            } else if (result.columns[i].dataIndex.toUpperCase() === "liftingTypeName".toUpperCase()) {
                if (pcpHidden) {
                    columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['抽油机']}";
                } else {
                    columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['抽油机', '螺杆泵']}";
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
            } else if (result.columns[i].dataIndex.toUpperCase() === "sortNum".toUpperCase()) {
                columns += "{data:'" + result.columns[i].dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,batchAddDeviceOverlayDataHandsontableHelper);}}";
            } else if (result.columns[i].dataIndex.toUpperCase() === "statusName".toUpperCase()) {
            	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['使能', '失效']}";
            } else {
                columns += "{data:'" + result.columns[i].dataIndex + "'}";
            }
            if (i < result.columns.length - 1) {
                colHeaders += ",";
                columns += ",";
            }
        }
        colHeaders += ",'覆盖信息'";
        columns += ",{data:'dataInfo'}";
        colHeaders += "]";
        columns += "]";
        batchAddDeviceOverlayDataHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
        batchAddDeviceOverlayDataHandsontableHelper.columns = Ext.JSON.decode(columns);
        batchAddDeviceOverlayDataHandsontableHelper.createTable(result.overlayList);
    } else {
    	batchAddDeviceOverlayDataHandsontableHelper.hot.loadData(result.overlayList);
    }
};

var BatchAddDeviceOverlayDataHandsontableHelper = {
    createNew: function (divid) {
        var batchAddDeviceOverlayDataHandsontableHelper = {};
        batchAddDeviceOverlayDataHandsontableHelper.hot = '';
        batchAddDeviceOverlayDataHandsontableHelper.divid = divid;
        batchAddDeviceOverlayDataHandsontableHelper.validresult = true; //数据校验
        batchAddDeviceOverlayDataHandsontableHelper.colHeaders = [];
        batchAddDeviceOverlayDataHandsontableHelper.columns = [];

        batchAddDeviceOverlayDataHandsontableHelper.AllData = {};
        batchAddDeviceOverlayDataHandsontableHelper.updatelist = [];
        batchAddDeviceOverlayDataHandsontableHelper.delidslist = [];
        batchAddDeviceOverlayDataHandsontableHelper.insertlist = [];

        batchAddDeviceOverlayDataHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
        }
        
        batchAddDeviceOverlayDataHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.color = '#ff0000';
        }

        batchAddDeviceOverlayDataHandsontableHelper.createTable = function (data) {
            $('#' + batchAddDeviceOverlayDataHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + batchAddDeviceOverlayDataHandsontableHelper.divid);
            batchAddDeviceOverlayDataHandsontableHelper.hot = new Handsontable(hotElement, {
            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            	data: data,
                hiddenColumns: {
                    columns: [0],
                    indicators: false
                },
                columns: batchAddDeviceOverlayDataHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: true, //显示行头
                colHeaders: batchAddDeviceOverlayDataHandsontableHelper.colHeaders, //显示列头
                columnSorting: true, //允许排序
                allowInsertRow:false,
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
                    if(batchAddDeviceOverlayDataHandsontableHelper.columns[visualColIndex].data.toUpperCase()=='dataInfo'.toUpperCase()){
                    	cellProperties.readOnly = true;
                    	cellProperties.renderer = batchAddDeviceOverlayDataHandsontableHelper.addBoldBg;
                    }
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
        batchAddDeviceOverlayDataHandsontableHelper.saveData = function () {}

        batchAddDeviceOverlayDataHandsontableHelper.clearContainer = function () {
            batchAddDeviceOverlayDataHandsontableHelper.AllData = {};
            batchAddDeviceOverlayDataHandsontableHelper.updatelist = [];
            batchAddDeviceOverlayDataHandsontableHelper.delidslist = [];
            batchAddDeviceOverlayDataHandsontableHelper.insertlist = [];
        }

        return batchAddDeviceOverlayDataHandsontableHelper;
    }
};