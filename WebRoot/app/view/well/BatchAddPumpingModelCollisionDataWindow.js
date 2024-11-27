var batchAddPumpingModelOverlayDataHandsontableHelper=null;
Ext.define("AP.view.well.BatchAddPumpingModelCollisionDataWindow", {
    extend: 'Ext.window.Window',
    id:'BatchAddPumpingModelCollisionDataWindow_Id',
    alias: 'widget.batchAddPumpingModelCollisionDataWindow',
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
                id: 'batchAddPumpingModelCollisionInfoLabel_Id',
                margin: '0 0 0 0',
                html: ''
            },'->',{
                xtype: 'button',
                text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function (v, o) {
                    var isCheckout=0;
                    var saveDate={};
                    saveDate.updatelist=[];
                    if(batchAddPumpingModelOverlayDataHandsontableHelper!=null&&batchAddPumpingModelOverlayDataHandsontableHelper.hot!=null&&batchAddPumpingModelOverlayDataHandsontableHelper.hot!=undefined){
                    	var batchAddData=batchAddPumpingModelOverlayDataHandsontableHelper.hot.getData();
                        for(var i=0;i<batchAddData.length;i++){
                        	if(isNotVal(batchAddData[i][1])){
                        		var data = "{";
                                for (var j = 0; j < batchAddPumpingModelOverlayDataHandsontableHelper.columns.length; j++) {
                                    data += batchAddPumpingModelOverlayDataHandsontableHelper.columns[j].data + ":'" + batchAddData[i][j] + "'";
                                    if (j < batchAddPumpingModelOverlayDataHandsontableHelper.columns.length - 1) {
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
                        url: context + '/wellInformationManagerController/batchAddPumpingModel',
                        success: function (response) {
                        	CreateAndLoadPumpingModelInfoTable();
                        	rdata = Ext.JSON.decode(response.responseText);
                            if (rdata.success&&rdata.overlayCount==0) {
                            	Ext.getCmp("BatchAddPumpingModelCollisionDataWindow_Id").close();
                            	Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.saveSuccessfully);
                            }else if(rdata.success&&rdata.overlayCount>0){
                            	CreateAndLoadBatchAddPumpingModelOverlayDataTable(rdata);
                            } else {
                                Ext.MessageBox.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
                            }
                        },
                        failure: function () {
                            Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.requestFailure);
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
            	id:'BatchAddPumpingModelOverlayDataPanel_Id',
            	title: '已有记录(<font color=red>继续保存，表中数据将覆盖已有记录</font>)',
            	html: '<div id="BatchAddPumpingModelOverlayDataTableDiv_Id" style="width:100%;height:100%;"></div>',
            	listeners: {
            		resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(batchAddPumpingModelOverlayDataHandsontableHelper!=null&&batchAddPumpingModelOverlayDataHandsontableHelper.hot!=null&&batchAddPumpingModelOverlayDataHandsontableHelper.hot!=undefined){
//                    		batchAddPumpingModelOverlayDataHandsontableHelper.hot.refreshDimensions();
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		batchAddPumpingModelOverlayDataHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}
                    }
            	}
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	if(batchAddPumpingModelOverlayDataHandsontableHelper!=null){
    					if(batchAddPumpingModelOverlayDataHandsontableHelper.hot!=undefined){
    						batchAddPumpingModelOverlayDataHandsontableHelper.hot.destroy();
    					}
    					batchAddPumpingModelOverlayDataHandsontableHelper=null;
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

function CreateAndLoadBatchAddPumpingModelOverlayDataTable(result) {
	if (batchAddPumpingModelOverlayDataHandsontableHelper == null || batchAddPumpingModelOverlayDataHandsontableHelper.hot == null || batchAddPumpingModelOverlayDataHandsontableHelper.hot == undefined) {
        batchAddPumpingModelOverlayDataHandsontableHelper = BatchAddPumpingModelOverlayDataHandsontableHelper.createNew("BatchAddPumpingModelOverlayDataTableDiv_Id");
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
        batchAddPumpingModelOverlayDataHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
        batchAddPumpingModelOverlayDataHandsontableHelper.columns = Ext.JSON.decode(columns);
        batchAddPumpingModelOverlayDataHandsontableHelper.createTable(result.overlayList);
    } else {
    	batchAddPumpingModelOverlayDataHandsontableHelper.hot.loadData(result.overlayList);
    }
};

var BatchAddPumpingModelOverlayDataHandsontableHelper = {
    createNew: function (divid) {
        var batchAddPumpingModelOverlayDataHandsontableHelper = {};
        batchAddPumpingModelOverlayDataHandsontableHelper.hot = '';
        batchAddPumpingModelOverlayDataHandsontableHelper.divid = divid;
        batchAddPumpingModelOverlayDataHandsontableHelper.validresult = true; //数据校验
        batchAddPumpingModelOverlayDataHandsontableHelper.colHeaders = [];
        batchAddPumpingModelOverlayDataHandsontableHelper.columns = [];

        batchAddPumpingModelOverlayDataHandsontableHelper.AllData = {};
        batchAddPumpingModelOverlayDataHandsontableHelper.updatelist = [];
        batchAddPumpingModelOverlayDataHandsontableHelper.delidslist = [];
        batchAddPumpingModelOverlayDataHandsontableHelper.insertlist = [];

        batchAddPumpingModelOverlayDataHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
        }
        
        batchAddPumpingModelOverlayDataHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.color = '#ff0000';
        }

        batchAddPumpingModelOverlayDataHandsontableHelper.createTable = function (data) {
            $('#' + batchAddPumpingModelOverlayDataHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + batchAddPumpingModelOverlayDataHandsontableHelper.divid);
            batchAddPumpingModelOverlayDataHandsontableHelper.hot = new Handsontable(hotElement, {
            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            	data: data,
                hiddenColumns: {
                    columns: [0],
                    indicators: false,
                    copyPasteEnabled: false
                },
                columns: batchAddPumpingModelOverlayDataHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: true, //显示行头
                colHeaders: batchAddPumpingModelOverlayDataHandsontableHelper.colHeaders, //显示列头
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
                    if(batchAddPumpingModelOverlayDataHandsontableHelper.columns[visualColIndex].data.toUpperCase()=='dataInfo'.toUpperCase()){
                    	cellProperties.readOnly = true;
                    	cellProperties.renderer = batchAddPumpingModelOverlayDataHandsontableHelper.addBoldBg;
                    }
                    return cellProperties;
                }
            });
        }
        //保存数据
        batchAddPumpingModelOverlayDataHandsontableHelper.saveData = function () {}

        batchAddPumpingModelOverlayDataHandsontableHelper.clearContainer = function () {
            batchAddPumpingModelOverlayDataHandsontableHelper.AllData = {};
            batchAddPumpingModelOverlayDataHandsontableHelper.updatelist = [];
            batchAddPumpingModelOverlayDataHandsontableHelper.delidslist = [];
            batchAddPumpingModelOverlayDataHandsontableHelper.insertlist = [];
        }

        return batchAddPumpingModelOverlayDataHandsontableHelper;
    }
};