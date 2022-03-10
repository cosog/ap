//抽油机
var rpcDeviceInfoHandsontableHelper = null;
Ext.define('AP.view.well.RPCDeviceInfoPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.RPCDeviceInfoPanel',
    id: 'RPCDeviceInfoPanel_Id',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var rpcCombStore = new Ext.data.JsonStore({
            pageSize: defaultWellComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/wellInformationManagerController/loadWellComboxList',
                type: "ajax",
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json',
                    rootProperty: 'list',
                    totalProperty: 'totals'
                }
            },
            autoLoad: true,
            listeners: {
                beforeload: function (store, options) {
                    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var wellName = Ext.getCmp('rpcDeviceListComb_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceType: 0,
                        wellName: wellName
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });

        var rpcDeviceCombo = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.wellName,
                id: "rpcDeviceListComb_Id",
                labelWidth: 35,
                width: 145,
                labelAlign: 'left',
                queryMode: 'remote',
                typeAhead: true,
                store: rpcCombStore,
                autoSelect: false,
                editable: true,
                triggerAction: 'all',
                displayField: "boxval",
                valueField: "boxkey",
                pageSize: comboxPagingStatus,
                minChars: 0,
                emptyText: cosog.string.all,
                blankText: cosog.string.all,
                listeners: {
                    expand: function (sm, selections) {
                        rpcDeviceCombo.getStore().loadPage(1); // 加载井下拉框的store
                    },
                    select: function (combo, record, index) {
                        try {
                            CreateAndLoadRPCDeviceInfoTable();
                        } catch (ex) {
                            Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                        }
                    }
                }
            });
        Ext.apply(this, {
            tbar: [rpcDeviceCombo, '-',{
                id: 'RPCDeviceSelectRow_Id',
                xtype: 'textfield',
                value: 0,
                hidden: true
            },{
                id: 'RPCDeviceSelectEndRow_Id',
                xtype: 'textfield',
                value: 0,
                hidden: true
            }, {
                xtype: 'button',
                text: cosog.string.exportExcel,
//                pressed: true,
                iconCls: 'export',
                hidden: false,
                handler: function (v, o) {
                    var fields = "";
                    var heads = "";
                    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var wellInformationName = Ext.getCmp('rpcDeviceListComb_Id').getValue();
                    var url = context + '/wellInformationManagerController/exportWellInformationData';
                    for (var i = 0; i < rpcDeviceInfoHandsontableHelper.colHeaders.length; i++) {
                        fields += rpcDeviceInfoHandsontableHelper.columns[i].data + ",";
                        heads += rpcDeviceInfoHandsontableHelper.colHeaders[i] + ","
                    }
                    if (isNotVal(fields)) {
                        fields = fields.substring(0, fields.length - 1);
                        heads = heads.substring(0, heads.length - 1);
                    }

                    var param = "&fields=" + fields + "&heads=" + URLencode(URLencode(heads)) + "&orgId=" + leftOrg_Id + "&deviceType=101&wellInformationName=" + URLencode(URLencode(wellInformationName)) + "&recordCount=10000" + "&fileName=" + URLencode(URLencode("抽油机设备")) + "&title=" + URLencode(URLencode("抽油机设备"));
                    openExcelWindow(url + '?flag=true' + param);
                }
            }, '-', {
                xtype: 'button',
                iconCls: 'note-refresh',
                text: cosog.string.refresh,
//                pressed: true,
                hidden: false,
                handler: function (v, o) {
                    CreateAndLoadRPCDeviceInfoTable();
                }
            },'-', {
                id: 'RPCDeviceTotalCount_Id',
                xtype: 'component',
                hidden: false,
                tpl: cosog.string.totalCount + ': {count}',
                style: 'margin-right:15px'
            },'->', {
    			xtype: 'button',
                text: '添加设备',
                iconCls: 'add',
                handler: function (v, o) {
                	var selectedOrgName="";
                	var selectedOrgId="";
                	var IframeViewStore = Ext.getCmp("IframeView_Id").getStore();
            		var count=IframeViewStore.getCount();
                	var IframeViewSelection = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
                	if (IframeViewSelection.length > 0) {
                		selectedOrgName=foreachAndSearchOrgAbsolutePath(IframeViewStore.data.items,IframeViewSelection[0].data.orgId);
                		selectedOrgId=IframeViewSelection[0].data.orgId;
                		
                	} else {
                		if(count>0){
                			selectedOrgName=IframeViewStore.getAt(0).data.text;
                			selectedOrgId=IframeViewStore.getAt(0).data.orgId;
                		}
                	}
                	
                	var window = Ext.create("AP.view.well.PumpDeviceInfoWindow", {
                        title: '添加设备'
                    });
                    window.show();
                    Ext.getCmp("pumpDeviceWinOgLabel_Id").setHtml("设备将添加到【<font color=red>"+selectedOrgName+"</font>】下,请确认<br/>&nbsp;");
                    Ext.getCmp("pumpDeviceType_Id").setValue(101);
                    Ext.getCmp("pumpDeviceOrg_Id").setValue(selectedOrgId);
                    Ext.getCmp("addFormPumpDevice_Id").show();
                    Ext.getCmp("updateFormPumpDevice_Id").hide();
                    return false;
    			}
    		}, '-',{
    			xtype: 'button',
    			id: 'deleteRPCDeviceNameBtn_Id',
    			text: '删除设备',
    			iconCls: 'delete',
    			handler: function (v, o) {
    				var startRow= Ext.getCmp("RPCDeviceSelectRow_Id").getValue();
    				var endRow= Ext.getCmp("RPCDeviceSelectEndRow_Id").getValue();
    				var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
    				if(startRow!='' && endRow!=''){
    					startRow=parseInt(startRow);
    					endRow=parseInt(endRow);
    					var deleteInfo='是否删除第'+(startRow+1)+"行~第"+(endRow+1)+"行数据";
    					if(startRow==endRow){
    						deleteInfo='是否删除第'+(startRow+1)+"行数据";
    					}
    					
    					Ext.Msg.confirm(cosog.string.yesdel, deleteInfo, function (btn) {
    			            if (btn == "yes") {
    			            	for(var i=startRow;i<=endRow;i++){
    	    						var rowdata = rpcDeviceInfoHandsontableHelper.hot.getDataAtRow(i);
    	    						if (rowdata[0] != null && parseInt(rowdata[0])>0) {
    	    		                    rpcDeviceInfoHandsontableHelper.delidslist.push(rowdata[0]);
    	    		                }
    	    					}
    	    					var saveData={};
    	    	            	saveData.updatelist=[];
    	    	            	saveData.insertlist=[];
    	    	            	saveData.delidslist=rpcDeviceInfoHandsontableHelper.delidslist;
    	    	            	Ext.Ajax.request({
    	    	                    method: 'POST',
    	    	                    url: context + '/wellInformationManagerController/saveWellHandsontableData',
    	    	                    success: function (response) {
    	    	                        rdata = Ext.JSON.decode(response.responseText);
    	    	                        if (rdata.success) {
    	    	                        	Ext.MessageBox.alert("信息", "删除成功");
    	    	                            //保存以后重置全局容器
    	    	                            rpcDeviceInfoHandsontableHelper.clearContainer();
    	    	                            Ext.getCmp("RPCDeviceSelectRow_Id").setValue(0);
    	    	                        	Ext.getCmp("RPCDeviceSelectEndRow_Id").setValue(0);
    	    	                            CreateAndLoadRPCDeviceInfoTable();
    	    	                        } else {
    	    	                            Ext.MessageBox.alert("信息", "数据保存失败");
    	    	                        }
    	    	                    },
    	    	                    failure: function () {
    	    	                        Ext.MessageBox.alert("信息", "请求失败");
    	    	                        rpcDeviceInfoHandsontableHelper.clearContainer();
    	    	                    },
    	    	                    params: {
    	    	                        data: JSON.stringify(saveData),
    	    	                        orgId: leftOrg_Id,
    	    	                        deviceType: 101
    	    	                    }
    	    	                });
    			            }
    			        });
    				}else{
    					Ext.MessageBox.alert("信息","请先选中要删除的行");
    				}
    			}
    		},"-", {
                xtype: 'button',
                itemId: 'saveRPCDeviceDataBtnId',
                id: 'saveRPCDeviceDataBtn_Id',
                text: cosog.string.save,
                iconCls: 'save',
                handler: function (v, o) {
                    rpcDeviceInfoHandsontableHelper.saveData();
                }
            },"-",{
    			xtype: 'button',
                text: '批量添加',
                iconCls: 'batchAdd',
                hidden: false,
                handler: function (v, o) {
                	var selectedOrgName="";
                	var selectedOrgId="";
                	var IframeViewStore = Ext.getCmp("IframeView_Id").getStore();
            		var count=IframeViewStore.getCount();
                	var IframeViewSelection = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
                	if (IframeViewSelection.length > 0) {
                		selectedOrgName=foreachAndSearchOrgAbsolutePath(IframeViewStore.data.items,IframeViewSelection[0].data.orgId);
                		selectedOrgId=IframeViewSelection[0].data.orgId;
                		
                	} else {
                		if(count>0){
                			selectedOrgName=IframeViewStore.getAt(0).data.text;
                			selectedOrgId=IframeViewStore.getAt(0).data.orgId;
                		}
                	}
                	
                	var window = Ext.create("AP.view.well.BatchAddDeviceWindow", {
                        title: '抽油机批量添加'
                    });
                    Ext.getCmp("batchAddDeviceWinOgLabel_Id").setHtml("设备将添加到【<font color=red>"+selectedOrgName+"</font>】下,请确认");
                    Ext.getCmp("batchAddDeviceType_Id").setValue(101);
                    Ext.getCmp("batchAddDeviceOrg_Id").setValue(selectedOrgId);
                    window.show();
                    return false;
    			}
    		},'-', {
    			xtype: 'button',
    			text:'设备隶属迁移',
    			iconCls: 'move',
    			handler: function (v, o) {
    				var window = Ext.create("AP.view.well.DeviceOrgChangeWindow", {
                        title: '设备隶属迁移'
                    });
                    window.show();
                    Ext.getCmp('DeviceOrgChangeWinDeviceType_Id').setValue(101);
                    Ext.create("AP.store.well.DeviceOrgChangeDeviceListStore");
                    Ext.create("AP.store.well.DeviceOrgChangeOrgListStore");
    			}
    		}],
            layout: 'border',
            items: [{
            	region: 'center',
            	layout: 'border',
            	items: [{
            		region: 'center',
            		title:'抽油机设备列表',
                	html: '<div class="RPCDeviceContainer" style="width:100%;height:100%;"><div class="con" id="RPCDeviceTableDiv_id"></div></div>',
                    listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            if (rpcDeviceInfoHandsontableHelper != null && rpcDeviceInfoHandsontableHelper.hot != null && rpcDeviceInfoHandsontableHelper.hot != undefined) {
//                            	CreateAndLoadRPCDeviceInfoTable();
                            	rpcDeviceInfoHandsontableHelper.hot.refreshDimensions();
                            }
                        }
                    }
            	}]
            }],
            listeners: {
                beforeclose: function (panel, eOpts) {
                    
                }
            }
        })
        this.callParent(arguments);
    }
});

function CreateAndLoadRPCDeviceInfoTable(isNew) {
	if(isNew&&rpcDeviceInfoHandsontableHelper!=null){
		if (rpcDeviceInfoHandsontableHelper.hot != undefined) {
			rpcDeviceInfoHandsontableHelper.hot.destroy();
		}
		rpcDeviceInfoHandsontableHelper = null;
	}
    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
    var wellInformationName_Id = Ext.getCmp('rpcDeviceListComb_Id').getValue();
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/wellInformationManagerController/doWellInformationShow',
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (rpcDeviceInfoHandsontableHelper == null || rpcDeviceInfoHandsontableHelper.hot == null || rpcDeviceInfoHandsontableHelper.hot == undefined) {
                rpcDeviceInfoHandsontableHelper = RPCDeviceInfoHandsontableHelper.createNew("RPCDeviceTableDiv_id");
                rpcDeviceInfoHandsontableHelper.dataLength=result.totalCount;
                var colHeaders = "[";
                var columns = "[";

                for (var i = 0; i < result.columns.length; i++) {
                    colHeaders += "'" + result.columns[i].header + "'";
                    if (result.columns[i].dataIndex.toUpperCase() === "orgName".toUpperCase()) {
                        columns += "{data:'" + result.columns[i].dataIndex + "',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Org(val, callback,this.row, this.col,rpcDeviceInfoHandsontableHelper);}}";
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
                        columns += "{data:'" + result.columns[i].dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,rpcDeviceInfoHandsontableHelper);}}";
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
                colHeaders += "]";
                columns += "]";
                rpcDeviceInfoHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
                rpcDeviceInfoHandsontableHelper.columns = Ext.JSON.decode(columns);
                rpcDeviceInfoHandsontableHelper.createTable(result.totalRoot);
            } else {
            	rpcDeviceInfoHandsontableHelper.dataLength=result.totalCount;
            	rpcDeviceInfoHandsontableHelper.hot.loadData(result.totalRoot);
            }
            if(result.totalRoot.length==0){
            	Ext.getCmp("RPCDeviceSelectRow_Id").setValue('');
            	Ext.getCmp("RPCDeviceSelectEndRow_Id").setValue('');
            	CreateAndLoadRPCAuxiliaryDeviceInfoTable(0,'');
            	CreateAndLoadRPCAdditionalInfoTable(0,'');
            }else{
            	var selectedRow=Ext.getCmp("RPCDeviceSelectRow_Id").getValue();
            	var rowdata = rpcDeviceInfoHandsontableHelper.hot.getDataAtRow(selectedRow);
            	CreateAndLoadRPCAuxiliaryDeviceInfoTable(rowdata[0],rowdata[1]);
            	CreateAndLoadRPCAdditionalInfoTable(rowdata[0],rowdata[1]);
            }
            Ext.getCmp("RPCDeviceTotalCount_Id").update({
                count: result.totalCount
            });
        },
        failure: function () {
            Ext.MessageBox.alert("错误", "与后台联系的时候出了问题");
        },
        params: {
            wellInformationName: wellInformationName_Id,
            deviceType: 0,
            recordCount: 50,
            orgId: leftOrg_Id,
            page: 1,
            limit: 10000
        }
    });
};

var RPCDeviceInfoHandsontableHelper = {
    createNew: function (divid) {
        var rpcDeviceInfoHandsontableHelper = {};
        rpcDeviceInfoHandsontableHelper.hot = '';
        rpcDeviceInfoHandsontableHelper.divid = divid;
        rpcDeviceInfoHandsontableHelper.validresult = true; //数据校验
        rpcDeviceInfoHandsontableHelper.colHeaders = [];
        rpcDeviceInfoHandsontableHelper.columns = [];
        rpcDeviceInfoHandsontableHelper.dataLength = 0;

        rpcDeviceInfoHandsontableHelper.AllData = {};
        rpcDeviceInfoHandsontableHelper.updatelist = [];
        rpcDeviceInfoHandsontableHelper.delidslist = [];
        rpcDeviceInfoHandsontableHelper.insertlist = [];
        rpcDeviceInfoHandsontableHelper.editWellNameList = [];

        rpcDeviceInfoHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
        }

        rpcDeviceInfoHandsontableHelper.createTable = function (data) {
            $('#' + rpcDeviceInfoHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + rpcDeviceInfoHandsontableHelper.divid);
            rpcDeviceInfoHandsontableHelper.hot = new Handsontable(hotElement, {
            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            	data: data,
                hiddenColumns: {
                    columns: [0],
                    indicators: false
                },
                columns: rpcDeviceInfoHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: true, //显示行头
                colHeaders: rpcDeviceInfoHandsontableHelper.colHeaders, //显示列头
                columnSorting: true, //允许排序
                allowInsertRow:false,
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
                	if(row<0 && row2<0){//只选中表头
                		Ext.getCmp("RPCDeviceSelectRow_Id").setValue('');
                    	Ext.getCmp("RPCDeviceSelectEndRow_Id").setValue('');
                    	CreateAndLoadRPCAuxiliaryDeviceInfoTable(0,'');
                    	CreateAndLoadRPCAdditionalInfoTable(0,'');
                	}else{
                		if(row<0){
                    		row=0;
                    	}
                    	if(row2<0){
                    		row2=0;
                    	}
                    	var startRow=row;
                    	var endRow=row2;
                    	if(row>row2){
                    		startRow=row2;
                        	endRow=row;
                    	}
                    	
                    	Ext.getCmp("RPCDeviceSelectRow_Id").setValue(startRow);
                    	Ext.getCmp("RPCDeviceSelectEndRow_Id").setValue(endRow);
                    	
                    	var row1=rpcDeviceInfoHandsontableHelper.hot.getDataAtRow(startRow);
                    	var recordId=0;
                    	var deviceName='';
                    	if(isNotVal(row1[0])){
                    		recordId=row1[0];
                    	}
                    	if(isNotVal(row1[1])){
                    		deviceName=row1[1];
                    	}
                	}
                },
                afterDestroy: function () {
                },
                beforeRemoveRow: function (index, amount) {
                    var ids = [];
                    //封装id成array传入后台
                    if (amount != 0) {
                        for (var i = index; i < amount + index; i++) {
                            var rowdata = rpcDeviceInfoHandsontableHelper.hot.getDataAtRow(i);
                            ids.push(rowdata[0]);
                        }
                        rpcDeviceInfoHandsontableHelper.delExpressCount(ids);
                        rpcDeviceInfoHandsontableHelper.screening();
                    }
                },
                afterChange: function (changes, source) {
                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
                    if (changes != null) {
//                        var IframeViewSelection = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
//                        if (IframeViewSelection.length > 0 && IframeViewSelection[0].isLeaf()) {} else {
//                            Ext.MessageBox.alert("信息", "编辑前，请先在左侧选择对应组织节点");
//                        }

                        for (var i = 0; i < changes.length; i++) {
                            var params = [];
                            var index = changes[i][0]; //行号码
                            var rowdata = rpcDeviceInfoHandsontableHelper.hot.getDataAtRow(index);
                            params.push(rowdata[0]);
                            params.push(changes[i][1]);
                            params.push(changes[i][2]);
                            params.push(changes[i][3]);
                            //仅当单元格发生改变的时候,id!=null,说明是更新
                            if (params[2] != params[3] && params[0] != null && params[0] > 0) {
                                var data = "{";
                                for (var j = 0; j < rpcDeviceInfoHandsontableHelper.columns.length; j++) {
                                    data += rpcDeviceInfoHandsontableHelper.columns[j].data + ":'" + rowdata[j] + "'";
                                    if (j < rpcDeviceInfoHandsontableHelper.columns.length - 1) {
                                        data += ","
                                    }
                                }
                                data += "}"
                                rpcDeviceInfoHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
                            }
                        }
                    
                    }
                }
            });
        }
        //插入的数据的获取
        rpcDeviceInfoHandsontableHelper.insertExpressCount = function () {
            var idsdata = rpcDeviceInfoHandsontableHelper.hot.getDataAtCol(0); //所有的id
            for (var i = 0; i < idsdata.length; i++) {
                //id=null时,是插入数据,此时的i正好是行号
                if (idsdata[i] == null || idsdata[i] < 0) {
                    //获得id=null时的所有数据封装进data
                    var rowdata = rpcDeviceInfoHandsontableHelper.hot.getDataAtRow(i);
                    //var collength = hot.countCols();
                    if (rowdata != null) {
                        var data = "{";
                        for (var j = 0; j < rpcDeviceInfoHandsontableHelper.columns.length; j++) {
                            data += rpcDeviceInfoHandsontableHelper.columns[j].data + ":'" + rowdata[j] + "'";
                            if (j < rpcDeviceInfoHandsontableHelper.columns.length - 1) {
                                data += ","
                            }
                        }
                        data += "}"
                        rpcDeviceInfoHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
                    }
                }
            }
            if (rpcDeviceInfoHandsontableHelper.insertlist.length != 0) {
                rpcDeviceInfoHandsontableHelper.AllData.insertlist = rpcDeviceInfoHandsontableHelper.insertlist;
            }
        }
        //保存数据
        rpcDeviceInfoHandsontableHelper.saveData = function () {
        	var leftOrg_Name=Ext.getCmp("leftOrg_Name").getValue();
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
            //插入的数据的获取
            rpcDeviceInfoHandsontableHelper.insertExpressCount();
        	Ext.Ajax.request({
                method: 'POST',
                url: context + '/wellInformationManagerController/saveWellHandsontableData',
                success: function (response) {
                    rdata = Ext.JSON.decode(response.responseText);
                    if (rdata.success) {
                    	var saveInfo='保存成功';
                    	if(rdata.collisionCount>0){//数据冲突
                    		saveInfo='保存成功'+rdata.successCount+'条记录,保存失败:<font color="red">'+rdata.collisionCount+'</font>条记录';
                    		for(var i=0;i<rdata.list.length;i++){
                    			saveInfo+='<br/><font color="red"> '+rdata.list[i]+'</font>';
                    		}
                    	}
                    	Ext.MessageBox.alert("信息", saveInfo);
                        if(rdata.successCount>0){
                        	rpcDeviceInfoHandsontableHelper.clearContainer();
                        	CreateAndLoadRPCDeviceInfoTable();
                        }
                    } else {
                        Ext.MessageBox.alert("信息", "数据保存失败");
                    }
                },
                failure: function () {
                    Ext.MessageBox.alert("信息", "请求失败");
                    rpcDeviceInfoHandsontableHelper.clearContainer();
                },
                params: {
                    data: JSON.stringify(rpcDeviceInfoHandsontableHelper.AllData),
                    orgId: leftOrg_Id,
                    deviceType: 0
                }
            });
        }

        //修改井名
        rpcDeviceInfoHandsontableHelper.editWellName = function () {
            //插入的数据的获取
            if (rpcDeviceInfoHandsontableHelper.editWellNameList.length > 0 && rpcDeviceInfoHandsontableHelper.validresult) {
                Ext.Ajax.request({
                    method: 'POST',
                    url: context + '/wellInformationManagerController/editWellName',
                    success: function (response) {
                        rdata = Ext.JSON.decode(response.responseText);
                        if (rdata.success) {
                            Ext.MessageBox.alert("信息", "保存成功");
                            rpcDeviceInfoHandsontableHelper.clearContainer();
                            CreateAndLoadRPCDeviceInfoTable();
                        } else {
                            Ext.MessageBox.alert("信息", "数据保存失败");
                        }
                    },
                    failure: function () {
                        Ext.MessageBox.alert("信息", "请求失败");
                        rpcDeviceInfoHandsontableHelper.clearContainer();
                    },
                    params: {
                        data: JSON.stringify(rpcDeviceInfoHandsontableHelper.editWellNameList),
                        deviceType:101
                    }
                });
            } else {
                if (!rpcDeviceInfoHandsontableHelper.validresult) {
                    Ext.MessageBox.alert("信息", "数据类型错误");
                } else {
                    Ext.MessageBox.alert("信息", "无数据变化");
                }
            }
        }


        //删除的优先级最高
        rpcDeviceInfoHandsontableHelper.delExpressCount = function (ids) {
            //传入的ids.length不可能为0
            $.each(ids, function (index, id) {
                if (id != null) {
                    rpcDeviceInfoHandsontableHelper.delidslist.push(id);
                }
            });
            rpcDeviceInfoHandsontableHelper.AllData.delidslist = rpcDeviceInfoHandsontableHelper.delidslist;
        }

        //updatelist数据更新
        rpcDeviceInfoHandsontableHelper.screening = function () {
            if (rpcDeviceInfoHandsontableHelper.updatelist.length != 0 && rpcDeviceInfoHandsontableHelper.delidslist.lentgh != 0) {
                for (var i = 0; i < rpcDeviceInfoHandsontableHelper.delidslist.length; i++) {
                    for (var j = 0; j < rpcDeviceInfoHandsontableHelper.updatelist.length; j++) {
                        if (rpcDeviceInfoHandsontableHelper.updatelist[j].id == rpcDeviceInfoHandsontableHelper.delidslist[i]) {
                            //更新updatelist
                            rpcDeviceInfoHandsontableHelper.updatelist.splice(j, 1);
                        }
                    }
                }
                //把updatelist封装进AllData
                rpcDeviceInfoHandsontableHelper.AllData.updatelist = rpcDeviceInfoHandsontableHelper.updatelist;
            }
        }

        //更新数据
        rpcDeviceInfoHandsontableHelper.updateExpressCount = function (data) {
            if (JSON.stringify(data) != "{}") {
                var flag = true;
                //判断记录是否存在,更新数据     
                $.each(rpcDeviceInfoHandsontableHelper.updatelist, function (index, node) {
                    if (node.id == data.id) {
                        //此记录已经有了
                        flag = false;
                        //用新得到的记录替换原来的,不用新增
                        rpcDeviceInfoHandsontableHelper.updatelist[index] = data;
                    }
                });
                flag && rpcDeviceInfoHandsontableHelper.updatelist.push(data);
                //封装
                rpcDeviceInfoHandsontableHelper.AllData.updatelist = rpcDeviceInfoHandsontableHelper.updatelist;
            }
        }

        rpcDeviceInfoHandsontableHelper.clearContainer = function () {
            rpcDeviceInfoHandsontableHelper.AllData = {};
            rpcDeviceInfoHandsontableHelper.updatelist = [];
            rpcDeviceInfoHandsontableHelper.delidslist = [];
            rpcDeviceInfoHandsontableHelper.insertlist = [];
            rpcDeviceInfoHandsontableHelper.editWellNameList = [];
        }

        return rpcDeviceInfoHandsontableHelper;
    }
};