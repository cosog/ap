var batchAddAuxiliaryDeviceOverlayDataHandsontableHelper=null;
Ext.define("AP.view.well.BatchAddAuxiliaryDeviceCollisionDataWindow", {
    extend: 'Ext.window.Window',
    id:'BatchAddAuxiliaryDeviceCollisionDataWindow_Id',
    alias: 'widget.batchAddAuxiliaryDeviceCollisionDataWindow',
    layout: 'fit',
    title:loginUserLanguageResource.dataCollision,
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
                text: loginUserLanguageResource.save,
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
                            	Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.saveSuccessfully);
                            }else if(rdata.success&&rdata.overlayCount>0){
                            	CreateAndLoadBatchAddAuxiliaryDeviceOverlayDataTable(rdata);
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
        
        batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.color = '#ff0000';
            td.style.whiteSpace='nowrap'; //文本不换行
        	td.style.overflow='hidden';//超出部分隐藏
        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
        }
        
        batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.whiteSpace='nowrap'; //文本不换行
        	td.style.overflow='hidden';//超出部分隐藏
        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
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
                    if(batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.columns[visualColIndex].data.toUpperCase()=='dataInfo'.toUpperCase()){
                    	cellProperties.readOnly = true;
                    	cellProperties.renderer = batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.addBoldBg;
                    }else{
                    	if(batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.columns[visualColIndex].type == undefined || batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.columns[visualColIndex].type!='dropdown'){
                    		cellProperties.renderer = batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.addCellStyle;
                    	}
                    }
                    return cellProperties;
                },
                afterOnCellMouseOver: function(event, coords, TD){
                	if(coords.col>=0 && coords.row>=0 && batchAddAuxiliaryDeviceOverlayDataHandsontableHelper!=null&&batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot!=''&&batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot!=undefined && batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot.getDataAtCell!=undefined){
                		var rawValue=batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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