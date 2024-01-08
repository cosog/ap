var batchAddAuxiliaryDeviceHandsontableHelper=null;
Ext.define("AP.view.well.BatchAddAuxiliaryDeviceWindow", {
    extend: 'Ext.window.Window',
    id:'BatchAddAuxiliaryDeviceWindow_Id',
    alias: 'widget.batchAddAuxiliaryDeviceWindow',
    layout: 'fit',
    title:'辅件设备批量添加',
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
                xtype: 'label',
                id: 'batchAddAuxiliaryDeviceWinOgLabel_Id',
                margin: '0 0 0 0',
                html: ''
            },{
                xtype: "hidden",
                fieldLabel: '设备类型',
                id: 'batchAddAuxiliaryDeviceType_Id',
                value: ''
            },{
                xtype: "hidden",
                fieldLabel: '设备类型',
                id: 'batchAddAuxiliaryDeviceOrg_Id',
                value: ''
            },'->',{
                xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                handler: function (v, o) {
                	batchAddAuxiliaryDeviceHandsontableHelper.saveData();
                }
            }],
            layout: 'border',
            items: [{
            	region: 'center',
            	html: '<div id="BatchAddAuxiliaryDeviceTableDiv_Id" style="width:100%;height:100%;"></div>',
            	listeners: {
            		resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    	if(batchAddAuxiliaryDeviceHandsontableHelper!=null&&batchAddAuxiliaryDeviceHandsontableHelper.hot!=null&&batchAddAuxiliaryDeviceHandsontableHelper.hot!=undefined){
                    		batchAddAuxiliaryDeviceHandsontableHelper.hot.refreshDimensions();
                    	}else{
                    		CreateAndLoadBatchAddAuxiliaryDeviceTable();
                    	}
                    }
            	}
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	if(batchAddAuxiliaryDeviceHandsontableHelper!=null){
    					if(batchAddAuxiliaryDeviceHandsontableHelper.hot!=undefined){
    						batchAddAuxiliaryDeviceHandsontableHelper.hot.destroy();
    					}
    					batchAddAuxiliaryDeviceHandsontableHelper=null;
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
function CreateAndLoadBatchAddAuxiliaryDeviceTable(isNew) {
	if(isNew&&batchAddAuxiliaryDeviceHandsontableHelper!=null){
		if (batchAddAuxiliaryDeviceHandsontableHelper.hot != undefined) {
			batchAddAuxiliaryDeviceHandsontableHelper.hot.destroy();
		}
		batchAddAuxiliaryDeviceHandsontableHelper = null;
	}
    var orgId = Ext.getCmp('batchAddAuxiliaryDeviceOrg_Id').getValue();
    var deviceType = Ext.getCmp('batchAddAuxiliaryDeviceType_Id').getValue();
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/wellInformationManagerController/getBatchAddAuxiliaryDeviceTableInfo',
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (batchAddAuxiliaryDeviceHandsontableHelper == null || batchAddAuxiliaryDeviceHandsontableHelper.hot == null || batchAddAuxiliaryDeviceHandsontableHelper.hot == undefined) {
                batchAddAuxiliaryDeviceHandsontableHelper = BatchAddAuxiliaryDeviceHandsontableHelper.createNew("BatchAddAuxiliaryDeviceTableDiv_Id");
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
                batchAddAuxiliaryDeviceHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
                batchAddAuxiliaryDeviceHandsontableHelper.columns = Ext.JSON.decode(columns);
                batchAddAuxiliaryDeviceHandsontableHelper.createTable(result.totalRoot);
            } else {
            	batchAddAuxiliaryDeviceHandsontableHelper.hot.loadData(result.totalRoot);
            }
        },
        failure: function () {
            Ext.MessageBox.alert("错误", "与后台联系的时候出了问题");
        },
        params: {
            recordCount: 50
        }
    });
};

var BatchAddAuxiliaryDeviceHandsontableHelper = {
    createNew: function (divid) {
        var batchAddAuxiliaryDeviceHandsontableHelper = {};
        batchAddAuxiliaryDeviceHandsontableHelper.hot = '';
        batchAddAuxiliaryDeviceHandsontableHelper.divid = divid;
        batchAddAuxiliaryDeviceHandsontableHelper.validresult = true; //数据校验
        batchAddAuxiliaryDeviceHandsontableHelper.colHeaders = [];
        batchAddAuxiliaryDeviceHandsontableHelper.columns = [];

        batchAddAuxiliaryDeviceHandsontableHelper.AllData = {};
        batchAddAuxiliaryDeviceHandsontableHelper.updatelist = [];
        batchAddAuxiliaryDeviceHandsontableHelper.delidslist = [];
        batchAddAuxiliaryDeviceHandsontableHelper.insertlist = [];

        batchAddAuxiliaryDeviceHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
        }

        batchAddAuxiliaryDeviceHandsontableHelper.createTable = function (data) {
            $('#' + batchAddAuxiliaryDeviceHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + batchAddAuxiliaryDeviceHandsontableHelper.divid);
            batchAddAuxiliaryDeviceHandsontableHelper.hot = new Handsontable(hotElement, {
            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            	data: data,
                hiddenColumns: {
                    columns: [0],
                    indicators: false
                },
                columns: batchAddAuxiliaryDeviceHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: true, //显示行头
                colHeaders: batchAddAuxiliaryDeviceHandsontableHelper.colHeaders, //显示列头
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
        batchAddAuxiliaryDeviceHandsontableHelper.saveData = function () {
            var isCheckout=1;
            var saveDate={};
            saveDate.updatelist=[];
            var batchAddData=batchAddAuxiliaryDeviceHandsontableHelper.hot.getData();
            for(var i=0;i<batchAddData.length;i++){
            	if(isNotVal(batchAddData[i][1])){
            		var data = "{";
                    for (var j = 0; j < batchAddAuxiliaryDeviceHandsontableHelper.columns.length; j++) {
                        data += batchAddAuxiliaryDeviceHandsontableHelper.columns[j].data + ":'" + batchAddData[i][j] + "'";
                        if (j < batchAddAuxiliaryDeviceHandsontableHelper.columns.length - 1) {
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
                url: context + '/wellInformationManagerController/batchAddAuxiliaryDevice',
                success: function (response) {
                    Ext.getCmp("BatchAddAuxiliaryDeviceWindow_Id").close();
                    CreateAndLoadAuxiliaryDeviceInfoTable();
                	rdata = Ext.JSON.decode(response.responseText);
                	if (rdata.success&&rdata.overlayCount==0) {
                    	Ext.MessageBox.alert("信息", "保存成功");
                        batchAddAuxiliaryDeviceHandsontableHelper.clearContainer();
                    }else if(rdata.success&&rdata.overlayCount>0){
                    	var window = Ext.create("AP.view.well.BatchAddAuxiliaryDeviceCollisionDataWindow", {
                            title: '异常数据处理'
                        });
                        window.show();
                        CreateAndLoadBatchAddAuxiliaryDeviceOverlayDataTable(rdata);
                    } else {
                        Ext.MessageBox.alert("信息", "数据保存失败");
                    }
                },
                failure: function () {
                    Ext.MessageBox.alert("信息", "请求失败");
                    batchAddAuxiliaryDeviceHandsontableHelper.clearContainer();
                },
                params: {
                    data: JSON.stringify(saveDate),
                    isCheckout: isCheckout
                }
            });
        }

        batchAddAuxiliaryDeviceHandsontableHelper.clearContainer = function () {
            batchAddAuxiliaryDeviceHandsontableHelper.AllData = {};
            batchAddAuxiliaryDeviceHandsontableHelper.updatelist = [];
            batchAddAuxiliaryDeviceHandsontableHelper.delidslist = [];
            batchAddAuxiliaryDeviceHandsontableHelper.insertlist = [];
        }

        return batchAddAuxiliaryDeviceHandsontableHelper;
    }
};