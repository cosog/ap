//螺杆泵井
var pcpDeviceInfoHandsontableHelper = null;
var pcpProductionHandsontableHelper = null;
Ext.define('AP.view.well.PCPDeviceInfoPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.pcpDeviceInfoPanel',
    id: 'PCPDeviceInfoPanel_Id',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var pcpCombStore = new Ext.data.JsonStore({
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
                    var wellName = Ext.getCmp('pcpDeviceListComb_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceType: 201,
                        wellName: wellName
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });

        var pcpDeviceCombo = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.wellName,
                id: "pcpDeviceListComb_Id",
                labelWidth: 35,
                width: 145,
                labelAlign: 'left',
                queryMode: 'remote',
                typeAhead: true,
                store: pcpCombStore,
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
                        pcpDeviceCombo.getStore().loadPage(1); // 加载井下拉框的store
                    },
                    select: function (combo, record, index) {
                        try {
                            CreateAndLoadPCPDeviceInfoTable();
                        } catch (ex) {
                            Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                        }
                    }
                }
            });
        Ext.apply(this, {
            tbar: [{
                id: 'PCPDeviceSelectRow_Id',
                xtype: 'textfield',
                value: 0,
                hidden: true
            },{
                id: 'PCPDeviceSelectEndRow_Id',
                xtype: 'textfield',
                value: 0,
                hidden: true
            },pcpDeviceCombo, '-', {
                xtype: 'button',
                text: cosog.string.exportExcel,
//                pressed: true,
                iconCls: 'export',
                hidden: false,
                handler: function (v, o) {
                    var fields = "";
                    var heads = "";
                    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var wellInformationName = Ext.getCmp('pcpDeviceListComb_Id').getValue();
                    var url = context + '/wellInformationManagerController/exportWellInformationData';
                    for (var i = 0; i < pcpDeviceInfoHandsontableHelper.colHeaders.length; i++) {
                        fields += pcpDeviceInfoHandsontableHelper.columns[i].data + ",";
                        heads += pcpDeviceInfoHandsontableHelper.colHeaders[i] + ","
                    }
                    if (isNotVal(fields)) {
                        fields = fields.substring(0, fields.length - 1);
                        heads = heads.substring(0, heads.length - 1);
                    }

                    var param = "&fields=" + fields + "&heads=" + URLencode(URLencode(heads)) + "&orgId=" + leftOrg_Id + "&deviceType=201&wellInformationName=" + URLencode(URLencode(wellInformationName)) + "&recordCount=10000" + "&fileName=" + URLencode(URLencode("螺杆泵井")) + "&title=" + URLencode(URLencode("螺杆泵井"));
                    openExcelWindow(url + '?flag=true' + param);
                }
            }, '-', {
                xtype: 'button',
                iconCls: 'note-refresh',
                text: cosog.string.refresh,
//                pressed: true,
                hidden: false,
                handler: function (v, o) {
                    CreateAndLoadPCPDeviceInfoTable();
                }
            },'-', {
                id: 'PCPDeviceTotalCount_Id',
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
                	
                	var window = Ext.create("AP.view.well.PCPDeviceInfoWindow", {
                        title: '添加设备'
                    });
                    window.show();
                    Ext.getCmp("pcpDeviceWinOgLabel_Id").setHtml("设备将添加到【<font color=red>"+selectedOrgName+"</font>】下,请确认<br/>&nbsp;");
                    Ext.getCmp("pcpDeviceType_Id").setValue(201);
                    Ext.getCmp("pcpDeviceOrg_Id").setValue(selectedOrgId);
                    Ext.getCmp("addFormPCPDevice_Id").show();
                    Ext.getCmp("updateFormPCPDevice_Id").hide();
                    return false;
    			}
    		},'-',{
    			xtype: 'button',
    			id: 'deletePCPDeviceNameBtn_Id',
    			text: '删除设备',
    			iconCls: 'delete',
    			handler: function (v, o) {
    				var startRow= Ext.getCmp("PCPDeviceSelectRow_Id").getValue();
    				var endRow= Ext.getCmp("PCPDeviceSelectEndRow_Id").getValue();
    				var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
    				if(startRow!=''&&endRow!=''){
    					startRow=parseInt(startRow);
    					endRow=parseInt(endRow);
    					var deleteInfo='是否删除第'+(startRow+1)+"行~第"+(endRow+1)+"行数据";
    					if(startRow==endRow){
    						deleteInfo='是否删除第'+(startRow+1)+"行数据";
    					}
    					
    					Ext.Msg.confirm(cosog.string.yesdel, deleteInfo, function (btn) {
    			            if (btn == "yes") {
    			            	for(var i=startRow;i<=endRow;i++){
    	    						var rowdata = pcpDeviceInfoHandsontableHelper.hot.getDataAtRow(i);
    	    						if (rowdata[0] != null && parseInt(rowdata[0])>0) {
    	    		                    pcpDeviceInfoHandsontableHelper.delidslist.push(rowdata[0]);
    	    		                }
    	    					}
    	    					var saveData={};
    	    	            	saveData.updatelist=[];
    	    	            	saveData.insertlist=[];
    	    	            	saveData.delidslist=pcpDeviceInfoHandsontableHelper.delidslist;
    	    	            	Ext.Ajax.request({
    	    	                    method: 'POST',
    	    	                    url: context + '/wellInformationManagerController/saveWellHandsontableData',
    	    	                    success: function (response) {
    	    	                        rdata = Ext.JSON.decode(response.responseText);
    	    	                        if (rdata.success) {
    	    	                        	Ext.MessageBox.alert("信息", "删除成功");
    	    	                            //保存以后重置全局容器
    	    	                            pcpDeviceInfoHandsontableHelper.clearContainer();
    	    	                            Ext.getCmp("PCPDeviceSelectRow_Id").setValue(0);
    	    	                        	Ext.getCmp("PCPDeviceSelectEndRow_Id").setValue(0);
    	    	                            CreateAndLoadPCPDeviceInfoTable();
    	    	                        } else {
    	    	                            Ext.MessageBox.alert("信息", "数据保存失败");
    	    	                        }
    	    	                    },
    	    	                    failure: function () {
    	    	                        Ext.MessageBox.alert("信息", "请求失败");
    	    	                        pcpDeviceInfoHandsontableHelper.clearContainer();
    	    	                    },
    	    	                    params: {
    	    	                        data: JSON.stringify(saveData),
    	    	                        orgId: leftOrg_Id,
    	    	                        deviceType: 201
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
                itemId: 'savePCPDeviceDataBtnId',
                id: 'savePCPDeviceDataBtn_Id',
                text: cosog.string.save,
                iconCls: 'save',
                handler: function (v, o) {
                    pcpDeviceInfoHandsontableHelper.saveData();
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
                        title: '螺杆泵井批量添加'
                    });
                	Ext.getCmp("batchAddDeviceWinOgLabel_Id").setHtml("设备将添加到【<font color=red>"+selectedOrgName+"</font>】下,请确认");
                    Ext.getCmp("batchAddDeviceType_Id").setValue(201);
                    Ext.getCmp("batchAddDeviceOrg_Id").setValue(selectedOrgId);
                    window.show();
                    return false;
    			}
    		},'-',{
    			xtype: 'button',
    			text:'设备隶属迁移',
    			iconCls: 'move',
    			handler: function (v, o) {
    				var window = Ext.create("AP.view.well.DeviceOrgChangeWindow", {
                        title: '设备隶属迁移'
                    });
                    window.show();
                    Ext.getCmp('DeviceOrgChangeWinDeviceType_Id').setValue(201);
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
            		title:'螺杆泵井列表',
                	html: '<div class="PCPDeviceContainer" style="width:100%;height:100%;"><div class="con" id="PCPDeviceTableDiv_id"></div></div>',
                    listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            if (pcpDeviceInfoHandsontableHelper != null && pcpDeviceInfoHandsontableHelper.hot != null && pcpDeviceInfoHandsontableHelper.hot != undefined) {
                            	pcpDeviceInfoHandsontableHelper.hot.refreshDimensions();
                            }
                        }
                    }
            	},{
            		region: 'east',
            		width: '30%',
            		title:'生产数据',
                	id:'PCPProductionDataInfoPanel_Id',
                	split: true,
                	collapsible: true,
                	html: '<div class="PCPAdditionalInfoContainer" style="width:100%;height:100%;"><div class="con" id="PCPAdditionalInfoTableDiv_id"></div></div>',
                    listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        	if (pcpProductionHandsontableHelper != null && pcpProductionHandsontableHelper.hot != null && pcpProductionHandsontableHelper.hot != undefined) {
                        		pcpProductionHandsontableHelper.hot.refreshDimensions();
                            }
                        }
                    }
            	}]
            }],
            listeners: {
                beforeclose: function (panel, eOpts) {
                	if (pcpDeviceInfoHandsontableHelper != null) {
                        if (pcpDeviceInfoHandsontableHelper.hot != undefined) {
                            pcpDeviceInfoHandsontableHelper.hot.destroy();
                        }
                        pcpDeviceInfoHandsontableHelper = null;
                    }
                    if (pcpProductionHandsontableHelper != null) {
                        if (pcpProductionHandsontableHelper.hot != undefined) {
                        	pcpProductionHandsontableHelper.hot.destroy();
                        }
                        pcpProductionHandsontableHelper = null;
                    }
                }
            }
        })
        this.callParent(arguments);
    }
});

function CreateAndLoadPCPDeviceInfoTable(isNew) {
	if(isNew&&pcpDeviceInfoHandsontableHelper!=null){
		if (pcpDeviceInfoHandsontableHelper.hot != undefined) {
			pcpDeviceInfoHandsontableHelper.hot.destroy();
		}
		pcpDeviceInfoHandsontableHelper = null;
	}
    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
    var wellInformationName_Id = Ext.getCmp('pcpDeviceListComb_Id').getValue();
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/wellInformationManagerController/doWellInformationShow',
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (pcpDeviceInfoHandsontableHelper == null || pcpDeviceInfoHandsontableHelper.hot == null || pcpDeviceInfoHandsontableHelper.hot == undefined) {
                pcpDeviceInfoHandsontableHelper = PCPDeviceInfoHandsontableHelper.createNew("PCPDeviceTableDiv_id");
                pcpDeviceInfoHandsontableHelper.dataLength=result.totalCount;
                var colHeaders = "[";
                var columns = "[";

                for (var i = 0; i < result.columns.length; i++) {
                    colHeaders += "'" + result.columns[i].header + "'";
                    if (result.columns[i].dataIndex.toUpperCase() === "orgName".toUpperCase()) {
                        columns += "{data:'" + result.columns[i].dataIndex + "',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Org(val, callback,this.row, this.col,pcpDeviceInfoHandsontableHelper);}}";
                    } else if (result.columns[i].dataIndex.toUpperCase() === "liftingTypeName".toUpperCase()) {
                        if (pcpHidden) {
                            columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['抽油机井']}";
                        } else {
                            columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['抽油机井', '螺杆泵井']}";
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
                        columns += "{data:'" + result.columns[i].dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,pcpDeviceInfoHandsontableHelper);}}";
                    } else if (result.columns[i].dataIndex.toUpperCase() === "statusName".toUpperCase()) {
                    	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['使能', '失效']}";
                    } else if (result.columns[i].dataIndex.toUpperCase() === "tcpType".toUpperCase()) {
                    	columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['TCP Server', 'TCP Client']}";
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
                pcpDeviceInfoHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
                pcpDeviceInfoHandsontableHelper.columns = Ext.JSON.decode(columns);
                pcpDeviceInfoHandsontableHelper.createTable(result.totalRoot);
            } else {
            	pcpDeviceInfoHandsontableHelper.dataLength=result.totalCount;
            	pcpDeviceInfoHandsontableHelper.hot.loadData(result.totalRoot);
            }
            if(result.totalRoot.length==0){
            	Ext.getCmp("PCPDeviceSelectRow_Id").setValue('');
            	Ext.getCmp("PCPDeviceSelectEndRow_Id").setValue('');
            	CreateAndLoadPCPProductionDataTable(0,'');
            }else{
            	var selectedRow=Ext.getCmp("PCPDeviceSelectRow_Id").getValue();
            	var rowdata = pcpDeviceInfoHandsontableHelper.hot.getDataAtRow(selectedRow);
            	CreateAndLoadPCPProductionDataTable(rowdata[0],rowdata[1]);
            }
            Ext.getCmp("PCPDeviceTotalCount_Id").update({
                count: result.totalCount
            });
        },
        failure: function () {
            Ext.MessageBox.alert("错误", "与后台联系的时候出了问题");
        },
        params: {
            wellInformationName: wellInformationName_Id,
            deviceType: 201,
            recordCount: 50,
            orgId: leftOrg_Id,
            page: 1,
            limit: 10000
        }
    });
};

var PCPDeviceInfoHandsontableHelper = {
    createNew: function (divid) {
        var pcpDeviceInfoHandsontableHelper = {};
        pcpDeviceInfoHandsontableHelper.hot = '';
        pcpDeviceInfoHandsontableHelper.divid = divid;
        pcpDeviceInfoHandsontableHelper.validresult = true; //数据校验
        pcpDeviceInfoHandsontableHelper.colHeaders = [];
        pcpDeviceInfoHandsontableHelper.columns = [];
        pcpDeviceInfoHandsontableHelper.dataLength = 0;

        pcpDeviceInfoHandsontableHelper.AllData = {};
        pcpDeviceInfoHandsontableHelper.updatelist = [];
        pcpDeviceInfoHandsontableHelper.delidslist = [];
        pcpDeviceInfoHandsontableHelper.insertlist = [];
        pcpDeviceInfoHandsontableHelper.editWellNameList = [];

        pcpDeviceInfoHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
        }

        pcpDeviceInfoHandsontableHelper.createTable = function (data) {
            $('#' + pcpDeviceInfoHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + pcpDeviceInfoHandsontableHelper.divid);
            pcpDeviceInfoHandsontableHelper.hot = new Handsontable(hotElement, {
            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            	data: data,
                hiddenColumns: {
                    columns: [0],
                    indicators: false
                },
                columns: pcpDeviceInfoHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: true, //显示行头
                colHeaders: pcpDeviceInfoHandsontableHelper.colHeaders, //显示列头
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
                		Ext.getCmp("PCPDeviceSelectRow_Id").setValue('');
                    	Ext.getCmp("PCPDeviceSelectEndRow_Id").setValue('');
                    	CreateAndLoadPCPProductionDataTable(0,'');
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
                    	
                    	Ext.getCmp("PCPDeviceSelectRow_Id").setValue(startRow);
                    	Ext.getCmp("PCPDeviceSelectEndRow_Id").setValue(endRow);
                    	
                    	var row1=pcpDeviceInfoHandsontableHelper.hot.getDataAtRow(startRow);
                    	var recordId=0;
                    	var deviceName='';
                    	if(isNotVal(row1[0])){
                    		recordId=row1[0];
                    	}
                    	if(isNotVal(row1[1])){
                    		deviceName=row1[1];
                    	}
                    	CreateAndLoadPCPProductionDataTable(recordId,deviceName);
                	}
                },
                afterDestroy: function () {
                },
                beforeRemoveRow: function (index, amount) {
                    var ids = [];
                    //封装id成array传入后台
                    if (amount != 0) {
                        for (var i = index; i < amount + index; i++) {
                            var rowdata = pcpDeviceInfoHandsontableHelper.hot.getDataAtRow(i);
                            ids.push(rowdata[0]);
                        }
                        pcpDeviceInfoHandsontableHelper.delExpressCount(ids);
                        pcpDeviceInfoHandsontableHelper.screening();
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
                            var rowdata = pcpDeviceInfoHandsontableHelper.hot.getDataAtRow(index);
                            params.push(rowdata[0]);
                            params.push(changes[i][1]);
                            params.push(changes[i][2]);
                            params.push(changes[i][3]);

                            if ("edit" == source && params[1] == "wellName") { //编辑井名单元格
                                var data = "{\"oldWellName\":\"" + params[2] + "\",\"newWellName\":\"" + params[3] + "\"}";
                                pcpDeviceInfoHandsontableHelper.editWellNameList.push(Ext.JSON.decode(data));
                            }

                            if (params[1] == "protocolName" && params[3] == "Kafka协议") {
                                pcpDeviceInfoHandsontableHelper.hot.getCell(index, 6).source = ['modbus-tcp', 'modbus-rtu'];
                            }

                            //仅当单元格发生改变的时候,id!=null,说明是更新
                            if (params[2] != params[3] && params[0] != null && params[0] > 0) {
                                var data = "{";
                                for (var j = 0; j < pcpDeviceInfoHandsontableHelper.columns.length; j++) {
                                    data += pcpDeviceInfoHandsontableHelper.columns[j].data + ":'" + rowdata[j] + "'";
                                    if (j < pcpDeviceInfoHandsontableHelper.columns.length - 1) {
                                        data += ","
                                    }
                                }
                                data += "}"
                                pcpDeviceInfoHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
                            }
                        }
                    
                    }
                }
            });
        }
        //插入的数据的获取
        pcpDeviceInfoHandsontableHelper.insertExpressCount = function () {
            var idsdata = pcpDeviceInfoHandsontableHelper.hot.getDataAtCol(0); //所有的id
            for (var i = 0; i < idsdata.length; i++) {
                //id=null时,是插入数据,此时的i正好是行号
                if (idsdata[i] == null || idsdata[i] < 0) {
                    //获得id=null时的所有数据封装进data
                    var rowdata = pcpDeviceInfoHandsontableHelper.hot.getDataAtRow(i);
                    //var collength = hot.countCols();
                    if (rowdata != null) {
                        var data = "{";
                        for (var j = 0; j < pcpDeviceInfoHandsontableHelper.columns.length; j++) {
                            data += pcpDeviceInfoHandsontableHelper.columns[j].data + ":'" + rowdata[j] + "'";
                            if (j < pcpDeviceInfoHandsontableHelper.columns.length - 1) {
                                data += ","
                            }
                        }
                        data += "}"
                        pcpDeviceInfoHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
                    }
                }
            }
            if (pcpDeviceInfoHandsontableHelper.insertlist.length != 0) {
                pcpDeviceInfoHandsontableHelper.AllData.insertlist = pcpDeviceInfoHandsontableHelper.insertlist;
            }
        }
        //保存数据
        pcpDeviceInfoHandsontableHelper.saveData = function () {
        	var leftOrg_Name=Ext.getCmp("leftOrg_Name").getValue();
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
            //插入的数据的获取
            pcpDeviceInfoHandsontableHelper.insertExpressCount();
            //获取设备ID
            var PCPDeviceSelectRow= Ext.getCmp("PCPDeviceSelectRow_Id").getValue();
            var rowdata = pcpDeviceInfoHandsontableHelper.hot.getDataAtRow(PCPDeviceSelectRow);
        	var deviceId=rowdata[0];
            //生产数据
            var deviceProductionData={};
            if(pcpProductionHandsontableHelper!=null && pcpProductionHandsontableHelper.hot!=undefined){
        		var productionHandsontableData=pcpProductionHandsontableHelper.hot.getData();
        		deviceProductionData.FluidPVT={};
        		if(isNumber(parseFloat(productionHandsontableData[0][2]))){
        			deviceProductionData.FluidPVT.CrudeOilDensity=parseFloat(productionHandsontableData[0][2]);
        		}
        		if(isNumber(parseFloat(productionHandsontableData[1][2]))){
        			deviceProductionData.FluidPVT.WaterDensity=parseFloat(productionHandsontableData[1][2]);
        		}
        		if(isNumber(parseFloat(productionHandsontableData[2][2]))){
        			deviceProductionData.FluidPVT.NaturalGasRelativeDensity=parseFloat(productionHandsontableData[2][2]);
        		}
        		if(isNumber(parseFloat(productionHandsontableData[3][2]))){
        			deviceProductionData.FluidPVT.SaturationPressure=parseFloat(productionHandsontableData[3][2]);
        		}
        		
        		deviceProductionData.Reservoir={};
        		if(isNumber(parseFloat(productionHandsontableData[4][2]))){
        			deviceProductionData.Reservoir.Depth=parseFloat(productionHandsontableData[4][2]);
        		}
        		if(isNumber(parseFloat(productionHandsontableData[5][2]))){
        			deviceProductionData.Reservoir.Temperature=parseFloat(productionHandsontableData[5][2]);
        		}
        		
        		deviceProductionData.Production={};
        		if(isNumber(parseFloat(productionHandsontableData[6][2]))){
        			deviceProductionData.Production.TubingPressure=parseFloat(productionHandsontableData[6][2]);
        		}
        		if(isNumber(parseFloat(productionHandsontableData[7][2]))){
        			deviceProductionData.Production.CasingPressure=parseFloat(productionHandsontableData[7][2]);
        		}
        		if(isNumber(parseFloat(productionHandsontableData[8][2]))){
        			deviceProductionData.Production.WellHeadTemperature=parseFloat(productionHandsontableData[8][2]);
        		}
        		if(isNumber(parseFloat(productionHandsontableData[9][2]))){
        			deviceProductionData.Production.WaterCut=parseFloat(productionHandsontableData[9][2]);
        		}
        		if(isNumber(parseFloat(productionHandsontableData[10][2]))){
        			deviceProductionData.Production.ProductionGasOilRatio=parseFloat(productionHandsontableData[10][2]);
        		}
        		if(isNumber(parseFloat(productionHandsontableData[11][2]))){
        			deviceProductionData.Production.ProducingfluidLevel=parseFloat(productionHandsontableData[11][2]);
        		}
        		if(isNumber(parseFloat(productionHandsontableData[12][2]))){
        			deviceProductionData.Production.PumpSettingDepth=parseFloat(productionHandsontableData[12][2]);
        		}
        		
        		deviceProductionData.Pump={};
        		if(isNumber(parseFloat(productionHandsontableData[13][2]))){
        			deviceProductionData.Pump.BarrelLength=parseFloat(productionHandsontableData[13][2]);
        		}
        		if(isNumber(parseInt(productionHandsontableData[14][2]))){
        			deviceProductionData.Pump.BarrelSeries=parseInt(productionHandsontableData[14][2]);
        		}
        		if(isNumber(parseFloat(productionHandsontableData[15][2]))){
        			deviceProductionData.Pump.RotorDiameter=parseFloat(productionHandsontableData[15][2])*0.001;
        		}
        		if(isNumber(parseFloat(productionHandsontableData[16][2]))){
        			deviceProductionData.Pump.QPR=parseFloat(productionHandsontableData[16][2])*0.001*0.001;
        		}
        		
        		
        		
        		deviceProductionData.TubingString={};
        		deviceProductionData.TubingString.EveryTubing=[];
        		var EveryTubing={};
        		if(isNumber(parseInt(productionHandsontableData[17][2]))){
        			EveryTubing.InsideDiameter=parseInt(productionHandsontableData[17][2])*0.001;
        		}
        		deviceProductionData.TubingString.EveryTubing.push(EveryTubing);
        		
        		deviceProductionData.CasingString={};
        		deviceProductionData.CasingString.EveryCasing=[];
        		var EveryCasing={};
        		if(isNumber(parseInt(productionHandsontableData[18][2]))){
        			EveryCasing.InsideDiameter=parseInt(productionHandsontableData[18][2])*0.001;
        		}
        		deviceProductionData.CasingString.EveryCasing.push(EveryCasing);
        		
        		deviceProductionData.RodString={};
        		deviceProductionData.RodString.EveryRod=[];
        		
        		if(isNotVal(productionHandsontableData[19][2]) && isNumber(parseInt(productionHandsontableData[20][2])) && isNumber(parseInt(productionHandsontableData[21][2])) && isNumber(parseInt(productionHandsontableData[22][2]))){
        			var Rod1={};
            		if(isNotVal(productionHandsontableData[19][2])){
            			Rod1.Grade=productionHandsontableData[19][2];
            		}
            		if(isNumber(parseInt(productionHandsontableData[20][2]))){
            			Rod1.OutsideDiameter=parseInt(productionHandsontableData[20][2])*0.001;
            		}
            		if(isNumber(parseInt(productionHandsontableData[21][2]))){
            			Rod1.InsideDiameter=parseInt(productionHandsontableData[21][2])*0.001;
            		}
            		if(isNumber(parseInt(productionHandsontableData[22][2]))){
            			Rod1.Length=parseInt(productionHandsontableData[22][2]);
            		}
            		deviceProductionData.RodString.EveryRod.push(Rod1);
        		}
        		
        		if(isNotVal(productionHandsontableData[23][2]) && isNumber(parseInt(productionHandsontableData[24][2])) && isNumber(parseInt(productionHandsontableData[25][2])) && isNumber(parseInt(productionHandsontableData[26][2]))){
        			var Rod2={};
            		if(isNotVal(productionHandsontableData[23][2])){
            			Rod2.Grade=productionHandsontableData[23][2];
            		}
            		if(isNumber(parseInt(productionHandsontableData[24][2]))){
            			Rod2.OutsideDiameter=parseInt(productionHandsontableData[24][2])*0.001;
            		}
            		if(isNumber(parseInt(productionHandsontableData[25][2]))){
            			Rod2.InsideDiameter=parseInt(productionHandsontableData[25][2])*0.001;
            		}
            		if(isNumber(parseInt(productionHandsontableData[26][2]))){
            			Rod2.Length=parseInt(productionHandsontableData[26][2]);
            		}
            		deviceProductionData.RodString.EveryRod.push(Rod2);
        		}
        		
        		if(isNotVal(productionHandsontableData[27][2]) && isNumber(parseInt(productionHandsontableData[28][2])) && isNumber(parseInt(productionHandsontableData[29][2])) && isNumber(parseInt(productionHandsontableData[30][2]))){
        			var Rod3={};
            		if(isNotVal(productionHandsontableData[27][2])){
            			Rod3.Grade=productionHandsontableData[27][2];
            		}
            		if(isNumber(parseInt(productionHandsontableData[28][2]))){
            			Rod3.OutsideDiameter=parseInt(productionHandsontableData[28][2])*0.001;
            		}
            		if(isNumber(parseInt(productionHandsontableData[29][2]))){
            			Rod3.InsideDiameter=parseInt(productionHandsontableData[29][2])*0.001;
            		}
            		if(isNumber(parseInt(productionHandsontableData[30][2]))){
            			Rod3.Length=parseInt(productionHandsontableData[30][2]);
            		}
            		deviceProductionData.RodString.EveryRod.push(Rod3);
        		}
        		
        		if(isNotVal(productionHandsontableData[31][2]) && isNumber(parseInt(productionHandsontableData[32][2])) && isNumber(parseInt(productionHandsontableData[33][2])) && isNumber(parseInt(productionHandsontableData[34][2]))){
        			var Rod4={};
            		if(isNotVal(productionHandsontableData[31][2])){
            			Rod4.Grade=productionHandsontableData[31][2];
            		}
            		if(isNumber(parseInt(productionHandsontableData[32][2]))){
            			Rod4.OutsideDiameter=parseInt(productionHandsontableData[32][2])*0.001;
            		}
            		if(isNumber(parseInt(productionHandsontableData[33][2]))){
            			Rod4.InsideDiameter=parseInt(productionHandsontableData[33][2])*0.001;
            		}
            		if(isNumber(parseInt(productionHandsontableData[34][2]))){
            			Rod4.Length=parseInt(productionHandsontableData[34][2]);
            		}
            		deviceProductionData.RodString.EveryRod.push(Rod4);
        		}
        		deviceProductionData.ManualIntervention={};
        		if(isNumber(parseFloat(productionHandsontableData[35][2]))){
        			deviceProductionData.ManualIntervention.NetGrossRatio=parseFloat(productionHandsontableData[35][2]);
        		}
        		if(isNumber(parseFloat(isNumber(parseFloat(productionHandsontableData[36][2]))))){
        			deviceProductionData.ManualIntervention.NetGrossValue=parseFloat(productionHandsontableData[36][2]);
        		}
        	}
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
                        //保存以后重置全局容器
                        if(rdata.successCount>0){
                        	pcpDeviceInfoHandsontableHelper.clearContainer();
                            CreateAndLoadPCPDeviceInfoTable();
                        }
                    } else {
                        Ext.MessageBox.alert("信息", "数据保存失败");
                    }
                },
                failure: function () {
                    Ext.MessageBox.alert("信息", "请求失败");
                    pcpDeviceInfoHandsontableHelper.clearContainer();
                },
                params: {
                	deviceId: deviceId,
                	data: JSON.stringify(pcpDeviceInfoHandsontableHelper.AllData),
                    deviceProductionData: JSON.stringify(deviceProductionData),
                    orgId: leftOrg_Id,
                    deviceType: 201
                }
            });
        }

        //修改井名
        pcpDeviceInfoHandsontableHelper.editWellName = function () {
            //插入的数据的获取
            if (pcpDeviceInfoHandsontableHelper.editWellNameList.length > 0 && pcpDeviceInfoHandsontableHelper.validresult) {
                Ext.Ajax.request({
                    method: 'POST',
                    url: context + '/wellInformationManagerController/editWellName',
                    success: function (response) {
                        rdata = Ext.JSON.decode(response.responseText);
                        if (rdata.success) {
                            Ext.MessageBox.alert("信息", "保存成功");
                            pcpDeviceInfoHandsontableHelper.clearContainer();
                            CreateAndLoadPCPDeviceInfoTable();
                        } else {
                            Ext.MessageBox.alert("信息", "数据保存失败");
                        }
                    },
                    failure: function () {
                        Ext.MessageBox.alert("信息", "请求失败");
                        pcpDeviceInfoHandsontableHelper.clearContainer();
                    },
                    params: {
                        data: JSON.stringify(pcpDeviceInfoHandsontableHelper.editWellNameList),
                        deviceType:201
                    }
                });
            } else {
                if (!pcpDeviceInfoHandsontableHelper.validresult) {
                    Ext.MessageBox.alert("信息", "数据类型错误");
                } else {
                    Ext.MessageBox.alert("信息", "无数据变化");
                }
            }
        }


        //删除的优先级最高
        pcpDeviceInfoHandsontableHelper.delExpressCount = function (ids) {
            //传入的ids.length不可能为0
            $.each(ids, function (index, id) {
                if (id != null) {
                    pcpDeviceInfoHandsontableHelper.delidslist.push(id);
                }
            });
            pcpDeviceInfoHandsontableHelper.AllData.delidslist = pcpDeviceInfoHandsontableHelper.delidslist;
        }

        //updatelist数据更新
        pcpDeviceInfoHandsontableHelper.screening = function () {
            if (pcpDeviceInfoHandsontableHelper.updatelist.length != 0 && pcpDeviceInfoHandsontableHelper.delidslist.lentgh != 0) {
                for (var i = 0; i < pcpDeviceInfoHandsontableHelper.delidslist.length; i++) {
                    for (var j = 0; j < pcpDeviceInfoHandsontableHelper.updatelist.length; j++) {
                        if (pcpDeviceInfoHandsontableHelper.updatelist[j].id == pcpDeviceInfoHandsontableHelper.delidslist[i]) {
                            //更新updatelist
                            pcpDeviceInfoHandsontableHelper.updatelist.splice(j, 1);
                        }
                    }
                }
                //把updatelist封装进AllData
                pcpDeviceInfoHandsontableHelper.AllData.updatelist = pcpDeviceInfoHandsontableHelper.updatelist;
            }
        }

        //更新数据
        pcpDeviceInfoHandsontableHelper.updateExpressCount = function (data) {
            if (JSON.stringify(data) != "{}") {
                var flag = true;
                //判断记录是否存在,更新数据     
                $.each(pcpDeviceInfoHandsontableHelper.updatelist, function (index, node) {
                    if (node.id == data.id) {
                        //此记录已经有了
                        flag = false;
                        //用新得到的记录替换原来的,不用新增
                        pcpDeviceInfoHandsontableHelper.updatelist[index] = data;
                    }
                });
                flag && pcpDeviceInfoHandsontableHelper.updatelist.push(data);
                //封装
                pcpDeviceInfoHandsontableHelper.AllData.updatelist = pcpDeviceInfoHandsontableHelper.updatelist;
            }
        }

        pcpDeviceInfoHandsontableHelper.clearContainer = function () {
            pcpDeviceInfoHandsontableHelper.AllData = {};
            pcpDeviceInfoHandsontableHelper.updatelist = [];
            pcpDeviceInfoHandsontableHelper.delidslist = [];
            pcpDeviceInfoHandsontableHelper.insertlist = [];
            pcpDeviceInfoHandsontableHelper.editWellNameList = [];
        }

        return pcpDeviceInfoHandsontableHelper;
    }
};

function CreateAndLoadPCPProductionDataTable(deviceId,deviceName,isNew){
	if(isNew&&pcpProductionHandsontableHelper!=null){
		if(pcpProductionHandsontableHelper.hot!=undefined){
			pcpProductionHandsontableHelper.hot.destroy();
		}
		pcpProductionHandsontableHelper=null;
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/wellInformationManagerController/getDeviceProductionDataInfo',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(!isNotVal(deviceName)){
				deviceName='';
			}
			Ext.getCmp("PCPProductionDataInfoPanel_Id").setTitle(deviceName+"生产数据");
			if(pcpProductionHandsontableHelper==null || pcpProductionHandsontableHelper.hot==undefined){
				pcpProductionHandsontableHelper = PCPProductionHandsontableHelper.createNew("PCPAdditionalInfoTableDiv_id");
				var colHeaders="['序号','名称','值']";
				var columns="[{data:'id'},{data:'itemName'},{data:'itemValue'}]";
				
				pcpProductionHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				pcpProductionHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					pcpProductionHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					pcpProductionHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					pcpProductionHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					pcpProductionHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			deviceId:deviceId,
			deviceType:201
        }
	});
};

var PCPProductionHandsontableHelper = {
	    createNew: function (divid) {
	        var pcpProductionHandsontableHelper = {};
	        pcpProductionHandsontableHelper.hot = '';
	        pcpProductionHandsontableHelper.divid = divid;
	        pcpProductionHandsontableHelper.colHeaders = [];
	        pcpProductionHandsontableHelper.columns = [];
	        pcpProductionHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	        }
	        
	        pcpProductionHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }

	        pcpProductionHandsontableHelper.createTable = function (data) {
	            $('#' + pcpProductionHandsontableHelper.divid).empty();
	            var hotElement = document.querySelector('#' + pcpProductionHandsontableHelper.divid);
	            pcpProductionHandsontableHelper.hot = new Handsontable(hotElement, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: data,
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: false
	                },
	                columns: pcpProductionHandsontableHelper.columns,
	                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true, //显示行头
	                colHeaders: pcpProductionHandsontableHelper.colHeaders, //显示列头
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
	                }, 
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
	                    if (visualColIndex !=2) {
							cellProperties.readOnly = true;
							cellProperties.renderer = pcpProductionHandsontableHelper.addBoldBg;
		                }else if(visualRowIndex==37 && visualColIndex==2){
	                    	cellProperties.readOnly = true;
	                    }
	                    if (visualColIndex === 2 && (visualRowIndex===19 || visualRowIndex===23||visualRowIndex===27||visualRowIndex===31)) {
	                    	this.type = 'dropdown';
	                    	this.source = ['A','B','C','K','D','KD','HL','HY'];
	                    	this.strict = true;
	                    	this.allowInvalid = false;
	                    }
	                    return cellProperties;
	                }
	            });
	        }
	        return pcpProductionHandsontableHelper;
	    }
	};