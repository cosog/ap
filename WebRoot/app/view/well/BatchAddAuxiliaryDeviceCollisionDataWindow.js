var batchAddAuxiliaryDeviceOverlayDataHandsontableHelper=null;
Ext.define("AP.view.well.BatchAddAuxiliaryDeviceCollisionDataWindow", {
    extend: 'Ext.window.Window',
    id:'BatchAddAuxiliaryDeviceCollisionDataWindow_Id',
    alias: 'widget.batchAddAuxiliaryDeviceCollisionDataWindow',
    layout: 'fit',
    title:'辅件设备批量添加-冲突数据',
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
                id: 'batchAddAuxiliaryDeviceCollisionInfoLabel_Id',
                margin: '0 0 0 0',
                html: ''
            },'->',{
                xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                handler: function (v, o) {
                    var isCheckout=0;
                    var saveDate={};
                    saveDate.updatelist=[];
                    if(batchAddAuxiliaryDeviceOverlayDataHandsontableHelper!=null&&batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot!=null&&batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot!=undefined){
                    	var batchAddData=batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot.getData();
                        for(var i=0;i<batchAddData.length;i++){
                        	if(isNotVal(batchAddData[i][1])){
                        		var data = "{";
                                for (var j = 0; j < batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.columns.length; j++) {
                                    data += batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.columns[j].data + ":'" + batchAddData[i][j] + "'";
                                    if (j < batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.columns.length - 1) {
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
                        url: context + '/wellInformationManagerController/batchAddAuxiliaryDevice',
                        success: function (response) {
                        	CreateAndLoadAuxiliaryDeviceInfoTable();
                        	rdata = Ext.JSON.decode(response.responseText);
                            if (rdata.success&&rdata.overlayCount==0) {
                            	Ext.getCmp("BatchAddAuxiliaryDeviceCollisionDataWindow_Id").close();
                            	Ext.MessageBox.alert("信息", "保存成功");
                            }else if(rdata.success&&rdata.overlayCount>0){
                            	CreateAndLoadBatchAddAuxiliaryDeviceOverlayDataTable(rdata);
                            } else {
                                Ext.MessageBox.alert("信息", "数据保存失败");
                            }
                        },
                        failure: function () {
                            Ext.MessageBox.alert("信息", "请求失败");
                        },
                        params: {
                            data: JSON.stringify(saveDate),
                            isCheckout: isCheckout
                        }
                    });
                }
            }],
            layout: 'border',
            items: [{
            	region: 'center',
            	id:'BatchAddAuxiliaryDeviceOverlayDataPanel_Id',
            	title: '已有记录(<font color=red>继续保存，表中数据将覆盖已有记录</font>)',
            	html: '<div id="BatchAddAuxiliaryDeviceOverlayDataTableDiv_Id" style="width:100%;height:100%;"></div>',
            	listeners: {
            		resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    	if(batchAddAuxiliaryDeviceOverlayDataHandsontableHelper!=null&&batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot!=null&&batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot!=undefined){
                    		batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot.refreshDimensions();
                    	}
                    }
            	}
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	if(batchAddAuxiliaryDeviceOverlayDataHandsontableHelper!=null){
    					if(batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot!=undefined){
    						batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot.destroy();
    					}
    					batchAddAuxiliaryDeviceOverlayDataHandsontableHelper=null;
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

function CreateAndLoadBatchAddAuxiliaryDeviceOverlayDataTable(result) {
	if (batchAddAuxiliaryDeviceOverlayDataHandsontableHelper == null || batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot == null || batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot == undefined) {
        batchAddAuxiliaryDeviceOverlayDataHandsontableHelper = BatchAddAuxiliaryDeviceOverlayDataHandsontableHelper.createNew("BatchAddAuxiliaryDeviceOverlayDataTableDiv_Id");
        var colHeaders = "[";
        var columns = "[";
        for (var i = 0; i < result.columns.length; i++) {
            colHeaders += "'" + result.columns[i].header + "'";
            if (result.columns[i].dataIndex.toUpperCase() === "type".toUpperCase()) {
            	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['泵辅件', '管辅件']}";
            } else if (result.columns[i].dataIndex.toUpperCase() === "sort".toUpperCase()) {
                columns += "{data:'" + result.columns[i].dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,batchAddAuxiliaryDeviceHandsontableHelper);}}";
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
        batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
        batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.columns = Ext.JSON.decode(columns);
        batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.createTable(result.overlayList);
    } else {
    	batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot.loadData(result.overlayList);
    }
};

var BatchAddAuxiliaryDeviceOverlayDataHandsontableHelper = {
    createNew: function (divid) {
        var batchAddAuxiliaryDeviceOverlayDataHandsontableHelper = {};
        batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot = '';
        batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.divid = divid;
        batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.validresult = true; //数据校验
        batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.colHeaders = [];
        batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.columns = [];

        batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.AllData = {};
        batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.updatelist = [];
        batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.delidslist = [];
        batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.insertlist = [];

        batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
        }
        
        batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.color = '#ff0000';
        }

        batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.createTable = function (data) {
            $('#' + batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.divid);
            batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot = new Handsontable(hotElement, {
            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            	data: data,
                hiddenColumns: {
                    columns: [0],
                    indicators: false
                },
                columns: batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: true, //显示行头
                colHeaders: batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.colHeaders, //显示列头
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
                    if(batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.columns[visualColIndex].data.toUpperCase()=='dataInfo'.toUpperCase()){
                    	cellProperties.readOnly = true;
                    	cellProperties.renderer = batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.addBoldBg;
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
        batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.saveData = function () {}

        batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.clearContainer = function () {
            batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.AllData = {};
            batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.updatelist = [];
            batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.delidslist = [];
            batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.insertlist = [];
        }

        return batchAddAuxiliaryDeviceOverlayDataHandsontableHelper;
    }
};