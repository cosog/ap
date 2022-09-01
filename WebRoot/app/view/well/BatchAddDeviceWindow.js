var batchAddDeviceHandsontableHelper=null;
Ext.define("AP.view.well.BatchAddDeviceWindow", {
    extend: 'Ext.window.Window',
    id:'BatchAddDeviceWindow_Id',
    alias: 'widget.batchAddDeviceWindow',
    layout: 'fit',
    title:'设备批量添加',
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
                id: 'batchAddDeviceWinOgLabel_Id',
                margin: '0 0 0 0',
                html: ''
            },{
                xtype: "hidden",
                fieldLabel: '设备类型',
                id: 'batchAddDeviceType_Id',
                value: ''
            },{
                xtype: "hidden",
                fieldLabel: '设备类型',
                id: 'batchAddDeviceOrg_Id',
                value: ''
            },'->',{
                xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                handler: function (v, o) {
                	batchAddDeviceHandsontableHelper.saveData();
                }
            }],
            layout: 'border',
            items: [{
            	region: 'center',
            	html: '<div id="BatchAddDeviceTableDiv_Id" style="width:100%;height:100%;"></div>',
            	listeners: {
            		resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    	if(batchAddDeviceHandsontableHelper!=null&&batchAddDeviceHandsontableHelper.hot!=null&&batchAddDeviceHandsontableHelper.hot!=undefined){
                    		batchAddDeviceHandsontableHelper.hot.refreshDimensions();
                    	}else{
                    		CreateAndLoadBatchAddDeviceTable();
                    	}
                    }
            	}
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	if(batchAddDeviceHandsontableHelper!=null){
    					if(batchAddDeviceHandsontableHelper.hot!=undefined){
    						batchAddDeviceHandsontableHelper.hot.destroy();
    					}
    					batchAddDeviceHandsontableHelper=null;
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
function CreateAndLoadBatchAddDeviceTable(isNew) {
	if(isNew&&batchAddDeviceHandsontableHelper!=null){
		if (batchAddDeviceHandsontableHelper.hot != undefined) {
			batchAddDeviceHandsontableHelper.hot.destroy();
		}
		batchAddDeviceHandsontableHelper = null;
	}
    var orgId = Ext.getCmp('batchAddDeviceOrg_Id').getValue();
    var deviceType = Ext.getCmp('batchAddDeviceType_Id').getValue();
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/wellInformationManagerController/getBatchAddDeviceTableInfo',
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (batchAddDeviceHandsontableHelper == null || batchAddDeviceHandsontableHelper.hot == null || batchAddDeviceHandsontableHelper.hot == undefined) {
                batchAddDeviceHandsontableHelper = BatchAddDeviceHandsontableHelper.createNew("BatchAddDeviceTableDiv_Id");
                var colHeaders = "[";
                var columns = "[";

                for (var i = 0; i < result.columns.length; i++) {
                    colHeaders += "'" + result.columns[i].header + "'";
                    if (result.columns[i].dataIndex.toUpperCase() === "orgName".toUpperCase()) {
                        columns += "{data:'" + result.columns[i].dataIndex + "',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Org(val, callback,this.row, this.col,batchAddDeviceHandsontableHelper);}}";
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
                        columns += "{data:'" + result.columns[i].dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,batchAddDeviceHandsontableHelper);}}";
                    } else if (result.columns[i].dataIndex.toUpperCase() === "statusName".toUpperCase()) {
                    	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['使能', '失效']}";
                    } else if (result.columns[i].dataIndex.toUpperCase() === "tcpType".toUpperCase()) {
                    	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['TCP Server', 'TCP Client']}";
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
                batchAddDeviceHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
                batchAddDeviceHandsontableHelper.columns = Ext.JSON.decode(columns);
                batchAddDeviceHandsontableHelper.createTable(result.totalRoot);
            } else {
            	batchAddDeviceHandsontableHelper.hot.loadData(result.totalRoot);
            }
        },
        failure: function () {
            Ext.MessageBox.alert("错误", "与后台联系的时候出了问题");
        },
        params: {
        	orgId: orgId,
        	deviceType: deviceType,
            recordCount: 50,
            page: 1,
            limit: 10000
        }
    });
};

var BatchAddDeviceHandsontableHelper = {
    createNew: function (divid) {
        var batchAddDeviceHandsontableHelper = {};
        batchAddDeviceHandsontableHelper.hot = '';
        batchAddDeviceHandsontableHelper.divid = divid;
        batchAddDeviceHandsontableHelper.validresult = true; //数据校验
        batchAddDeviceHandsontableHelper.colHeaders = [];
        batchAddDeviceHandsontableHelper.columns = [];

        batchAddDeviceHandsontableHelper.AllData = {};
        batchAddDeviceHandsontableHelper.updatelist = [];
        batchAddDeviceHandsontableHelper.delidslist = [];
        batchAddDeviceHandsontableHelper.insertlist = [];

        batchAddDeviceHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
        }

        batchAddDeviceHandsontableHelper.createTable = function (data) {
            $('#' + batchAddDeviceHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + batchAddDeviceHandsontableHelper.divid);
            batchAddDeviceHandsontableHelper.hot = new Handsontable(hotElement, {
            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            	data: data,
                hiddenColumns: {
                    columns: [0],
                    indicators: false
                },
                columns: batchAddDeviceHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: true, //显示行头
                colHeaders: batchAddDeviceHandsontableHelper.colHeaders, //显示列头
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
        batchAddDeviceHandsontableHelper.saveData = function () {
        	var orgId = Ext.getCmp('batchAddDeviceOrg_Id').getValue();
            var deviceType = Ext.getCmp('batchAddDeviceType_Id').getValue();
            var isCheckout=1;
            var saveDate={};
            saveDate.updatelist=[];
            var batchAddData=batchAddDeviceHandsontableHelper.hot.getData();
            for(var i=0;i<batchAddData.length;i++){
            	if(isNotVal(batchAddData[i][1])){
            		var data = "{";
                    for (var j = 0; j < batchAddDeviceHandsontableHelper.columns.length; j++) {
                        data += batchAddDeviceHandsontableHelper.columns[j].data + ":'" + batchAddData[i][j] + "'";
                        if (j < batchAddDeviceHandsontableHelper.columns.length - 1) {
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
                url: context + '/wellInformationManagerController/batchAddDevice',
                success: function (response) {
                    Ext.getCmp("BatchAddDeviceWindow_Id").close();
                    if(parseInt(deviceType)==101){
                		CreateAndLoadRPCDeviceInfoTable();
                	}else if(parseInt(deviceType)==201){
                		CreateAndLoadPCPDeviceInfoTable();
                	}
                	rdata = Ext.JSON.decode(response.responseText);
                	if (rdata.success&&rdata.collisionCount==0&&rdata.overlayCount==0) {
                    	Ext.MessageBox.alert("信息", "保存成功");
                        //保存以后重置全局容器
                        batchAddDeviceHandsontableHelper.clearContainer();
                    }else if(rdata.success&&(rdata.collisionCount>0 || rdata.overlayCount>0)){
                    	var window = Ext.create("AP.view.well.BatchAddDeviceCollisionDataWindow", {
                            title: '异常数据处理'
                        });
                        Ext.getCmp("batchAddCollisionDeviceType_Id").setValue(deviceType);
                        Ext.getCmp("batchAddCollisionDeviceOrg_Id").setValue(orgId);
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

        batchAddDeviceHandsontableHelper.clearContainer = function () {
            batchAddDeviceHandsontableHelper.AllData = {};
            batchAddDeviceHandsontableHelper.updatelist = [];
            batchAddDeviceHandsontableHelper.delidslist = [];
            batchAddDeviceHandsontableHelper.insertlist = [];
        }

        return batchAddDeviceHandsontableHelper;
    }
};