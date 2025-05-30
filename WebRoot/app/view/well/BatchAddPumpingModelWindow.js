var batchAddPumpingModelHandsontableHelper=null;
Ext.define("AP.view.well.BatchAddPumpingModelWindow", {
    extend: 'Ext.window.Window',
    id:'BatchAddPumpingModelWindow_Id',
    alias: 'widget.batchAddPumpingModelWindow',
    layout: 'fit',
    title:loginUserLanguageResource.batchAdd,
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
                id: 'batchAddPumpingModelWinOgLabel_Id',
                margin: '0 0 0 0',
                html: ''
            },{
                xtype: "hidden",
                fieldLabel: loginUserLanguageResource.deviceType,
                id: 'batchAddPumpingModelType_Id',
                value: ''
            },{
                xtype: "hidden",
                fieldLabel: loginUserLanguageResource.deviceType,
                id: 'batchAddPumpingModelOrg_Id',
                value: ''
            },'->',{
                xtype: 'button',
                text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function (v, o) {
                	batchAddPumpingModelHandsontableHelper.saveData();
                }
            }],
            layout: 'border',
            items: [{
            	region: 'center',
            	html: '<div id="BatchAddPumpingModelTableDiv_Id" style="width:100%;height:100%;"></div>',
            	listeners: {
            		resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(batchAddPumpingModelHandsontableHelper!=null&&batchAddPumpingModelHandsontableHelper.hot!=null&&batchAddPumpingModelHandsontableHelper.hot!=undefined){
//                    		batchAddPumpingModelHandsontableHelper.hot.refreshDimensions();
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		batchAddPumpingModelHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}else{
                    		CreateAndLoadBatchAddPumpingModelTable();
                    	}
                    }
            	}
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	if(batchAddPumpingModelHandsontableHelper!=null){
    					if(batchAddPumpingModelHandsontableHelper.hot!=undefined){
    						batchAddPumpingModelHandsontableHelper.hot.destroy();
    					}
    					batchAddPumpingModelHandsontableHelper=null;
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
function CreateAndLoadBatchAddPumpingModelTable(isNew) {
	if(isNew&&batchAddPumpingModelHandsontableHelper!=null){
		if (batchAddPumpingModelHandsontableHelper.hot != undefined) {
			batchAddPumpingModelHandsontableHelper.hot.destroy();
		}
		batchAddPumpingModelHandsontableHelper = null;
	}
    var orgId = Ext.getCmp('batchAddPumpingModelOrg_Id').getValue();
    var deviceType = Ext.getCmp('batchAddPumpingModelType_Id').getValue();
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/wellInformationManagerController/getBatchAddPumpingModelTableInfo',
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (batchAddPumpingModelHandsontableHelper == null || batchAddPumpingModelHandsontableHelper.hot == null || batchAddPumpingModelHandsontableHelper.hot == undefined) {
                batchAddPumpingModelHandsontableHelper = BatchAddPumpingModelHandsontableHelper.createNew("BatchAddPumpingModelTableDiv_Id");
                var colHeaders = "[";
                var columns = "[";
                for (var i = 0; i < result.columns.length; i++) {
                    colHeaders += "'" + result.columns[i].header + "'";
                    if (result.columns[i].dataIndex.toUpperCase() === "crankRotationDirection".toUpperCase()) {
                    	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['顺时针', '逆时针']}";
                    } else if (result.columns[i].dataIndex.toUpperCase() === "sort".toUpperCase()) {
                        columns += "{data:'" + result.columns[i].dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,batchAddPumpingModelHandsontableHelper);}}";
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
                batchAddPumpingModelHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
                batchAddPumpingModelHandsontableHelper.columns = Ext.JSON.decode(columns);
                batchAddPumpingModelHandsontableHelper.createTable(result.totalRoot);
            } else {
            	batchAddPumpingModelHandsontableHelper.hot.loadData(result.totalRoot);
            }
        },
        failure: function () {
            Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
        },
        params: {
            recordCount: 50
        }
    });
};

var BatchAddPumpingModelHandsontableHelper = {
    createNew: function (divid) {
        var batchAddPumpingModelHandsontableHelper = {};
        batchAddPumpingModelHandsontableHelper.hot = '';
        batchAddPumpingModelHandsontableHelper.divid = divid;
        batchAddPumpingModelHandsontableHelper.validresult = true; //数据校验
        batchAddPumpingModelHandsontableHelper.colHeaders = [];
        batchAddPumpingModelHandsontableHelper.columns = [];

        batchAddPumpingModelHandsontableHelper.AllData = {};
        batchAddPumpingModelHandsontableHelper.updatelist = [];
        batchAddPumpingModelHandsontableHelper.delidslist = [];
        batchAddPumpingModelHandsontableHelper.insertlist = [];

        batchAddPumpingModelHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
        }

        batchAddPumpingModelHandsontableHelper.createTable = function (data) {
            $('#' + batchAddPumpingModelHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + batchAddPumpingModelHandsontableHelper.divid);
            batchAddPumpingModelHandsontableHelper.hot = new Handsontable(hotElement, {
            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            	data: data,
                hiddenColumns: {
                    columns: [0],
                    indicators: false,
                    copyPasteEnabled: false
                },
                columns: batchAddPumpingModelHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: true, //显示行头
                colHeaders: batchAddPumpingModelHandsontableHelper.colHeaders, //显示列头
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
                        }
//                        ,
//                        "paste": {
//                            name: loginUserLanguageResource.contextMenu_paste,
//                            disabled: function () {
//                            },
//                            callback: function () {
//                            }
//                        }
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
        batchAddPumpingModelHandsontableHelper.saveData = function () {
            var isCheckout=1;
            var saveDate={};
            saveDate.updatelist=[];
            var batchAddData=batchAddPumpingModelHandsontableHelper.hot.getData();
            for(var i=0;i<batchAddData.length;i++){
            	if(isNotVal(batchAddData[i][1])){
            		var data = "{";
                    for (var j = 0; j < batchAddPumpingModelHandsontableHelper.columns.length; j++) {
                        data += batchAddPumpingModelHandsontableHelper.columns[j].data + ":'" + batchAddData[i][j] + "'";
                        if (j < batchAddPumpingModelHandsontableHelper.columns.length - 1) {
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
                url: context + '/wellInformationManagerController/batchAddPumpingModel',
                success: function (response) {
                    Ext.getCmp("BatchAddPumpingModelWindow_Id").close();
                    CreateAndLoadPumpingModelInfoTable();
                	rdata = Ext.JSON.decode(response.responseText);
                	if (rdata.success&&rdata.overlayCount==0) {
                    	Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.saveSuccessfully);
                        batchAddPumpingModelHandsontableHelper.clearContainer();
                    }else if(rdata.success&&rdata.overlayCount>0){
                    	var window = Ext.create("AP.view.well.BatchAddPumpingModelCollisionDataWindow", {
                            title: '异常数据处理'
                        });
                        window.show();
                        CreateAndLoadBatchAddPumpingModelOverlayDataTable(rdata);
                    } else {
                        Ext.MessageBox.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
                    }
                },
                failure: function () {
                    Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.requestFailure);
                    batchAddPumpingModelHandsontableHelper.clearContainer();
                },
                params: {
                    data: JSON.stringify(saveDate),
                    isCheckout: isCheckout
                }
            });
        }

        batchAddPumpingModelHandsontableHelper.clearContainer = function () {
            batchAddPumpingModelHandsontableHelper.AllData = {};
            batchAddPumpingModelHandsontableHelper.updatelist = [];
            batchAddPumpingModelHandsontableHelper.delidslist = [];
            batchAddPumpingModelHandsontableHelper.insertlist = [];
        }

        return batchAddPumpingModelHandsontableHelper;
    }
};