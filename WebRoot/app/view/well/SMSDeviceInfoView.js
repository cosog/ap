//短信设备
var smsDeviceInfoHandsontableHelper = null;
Ext.define('AP.view.well.SMSDeviceInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.SMSDeviceInfoView',
    id: 'SMSDeviceInfoView_Id',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var SMSCombStore = new Ext.data.JsonStore({
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
                    var wellName = Ext.getCmp('SMSDeviceListComb_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceType: 300,
                        wellName: wellName
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });

        var SMSDeviceCombo = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: '设备名称',
                id: "SMSDeviceListComb_Id",
                labelWidth: 55,
                width: 165,
                labelAlign: 'left',
                queryMode: 'remote',
                typeAhead: true,
                store: SMSCombStore,
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
                        SMSDeviceCombo.getStore().loadPage(1); // 加载井下拉框的store
                    },
                    select: function (combo, record, index) {
                        try {
                            CreateAndLoadSMSDeviceInfoTable();
                        } catch (ex) {
                            Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                        }
                    }
                }
            });
        Ext.apply(this, {
            tbar: [{
                id: 'SMSDeviceSelectRow_Id',
                xtype: 'textfield',
                value: 0,
                hidden: true
            }, {
                id: 'SMSDeviceSelectEndRow_Id',
                xtype: 'textfield',
                value: 0,
                hidden: true
            },{
                xtype: 'button',
                text: cosog.string.refresh,
                iconCls: 'note-refresh',
                hidden:false,
                handler: function (v, o) {
                	CreateAndLoadSMSDeviceInfoTable();
                }
    		},'-', SMSDeviceCombo, '-', {
                xtype: 'button',
                text: cosog.string.search,
                iconCls: 'search',
                hidden: false,
                handler: function (v, o) {
                    CreateAndLoadSMSDeviceInfoTable();
                }
    		}, '-', {
                xtype: 'button',
                text: cosog.string.exportExcel,
                iconCls: 'export',
                hidden: false,
                handler: function (v, o) {
                    var fields = "";
                    var heads = "";
                    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var wellInformationName = Ext.getCmp('SMSDeviceListComb_Id').getValue();
                    var url = context + '/wellInformationManagerController/exportWellInformationData';
                    for (var i = 0; i < smsDeviceInfoHandsontableHelper.colHeaders.length; i++) {
                        fields += smsDeviceInfoHandsontableHelper.columns[i].data + ",";
                        heads += smsDeviceInfoHandsontableHelper.colHeaders[i] + ","
                    }
                    if (isNotVal(fields)) {
                        fields = fields.substring(0, fields.length - 1);
                        heads = heads.substring(0, heads.length - 1);
                    }

                    var param = "&fields=" + fields + "&heads=" + URLencode(URLencode(heads)) + "&orgId=" + leftOrg_Id + "&deviceType=300&wellInformationName=" + URLencode(URLencode(wellInformationName)) + "&recordCount=10000" + "&fileName=" + URLencode(URLencode("短信设备")) + "&title=" + URLencode(URLencode("短信设备"));
                    openExcelWindow(url + '?flag=true' + param);
                }
    		}, '-', {
                id: 'SMSDeviceTotalCount_Id',
                xtype: 'component',
                hidden: false,
                tpl: cosog.string.totalCount + ': {count}',
                style: 'margin-right:15px'
    		}, '->', {
                xtype: 'button',
                text: '添加设备',
                iconCls: 'add',
                handler: function (v, o) {
                    var selectedOrgName = "";
                    var selectedOrgId = "";
                    var IframeViewStore = Ext.getCmp("IframeView_Id").getStore();
                    var count = IframeViewStore.getCount();
                    var IframeViewSelection = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
                    if (IframeViewSelection.length > 0) {
                        selectedOrgName = foreachAndSearchOrgAbsolutePath(IframeViewStore.data.items, IframeViewSelection[0].data.orgId);
                        selectedOrgId = IframeViewSelection[0].data.orgId;
                    } else {
                        if (count > 0) {
                            selectedOrgName = IframeViewStore.getAt(0).data.text;
                            selectedOrgId = IframeViewStore.getAt(0).data.orgId;
                        }
                    }
                    var window = Ext.create("AP.view.well.SMSDeviceInfoWindow", {
                        title: '添加设备'
                    });
                    window.show();
                    Ext.getCmp("smsDeviceWinOgLabel_Id").setHtml("设备将添加到【<font color=red>" + selectedOrgName + "</font>】下,请确认<br/>&nbsp;");
                    Ext.getCmp("smsDeviceOrg_Id").setValue(selectedOrgId);
                    Ext.getCmp("addFormSMSDevice_Id").show();
                    Ext.getCmp("updateFormSMSDevice_Id").hide();
                    return false;
                }
            }, '-', {
                xtype: 'button',
                id: 'deleteSMSDeviceNameBtn_Id',
                text: '删除设备',
                iconCls: 'delete',
                handler: function (v, o) {
                    var startRow = Ext.getCmp("SMSDeviceSelectRow_Id").getValue();
                    var endRow = Ext.getCmp("SMSDeviceSelectEndRow_Id").getValue();
                    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
                    if (startRow != '' && endRow != '') {
                        startRow = parseInt(startRow);
                        endRow = parseInt(endRow);
                        var deleteInfo = '是否删除第' + (startRow + 1) + "行~第" + (endRow + 1) + "行数据";
                        if (startRow == endRow) {
                            deleteInfo = '是否删除第' + (startRow + 1) + "行数据";
                        }

                        Ext.Msg.confirm(cosog.string.yesdel, deleteInfo, function (btn) {
                            if (btn == "yes") {
                                for (var i = startRow; i <= endRow; i++) {
                                    var rowdata = smsDeviceInfoHandsontableHelper.hot.getDataAtRow(i);
                                    if (rowdata[0] != null && parseInt(rowdata[0]) > 0) {
                                        smsDeviceInfoHandsontableHelper.delidslist.push(rowdata[0]);
                                    }
                                }
                                var saveData = {};
                                saveData.updatelist = [];
                                saveData.insertlist = [];
                                saveData.delidslist = smsDeviceInfoHandsontableHelper.delidslist;
                                Ext.Ajax.request({
                                    method: 'POST',
                                    url: context + '/wellInformationManagerController/saveWellHandsontableData',
                                    success: function (response) {
                                        rdata = Ext.JSON.decode(response.responseText);
                                        if (rdata.success) {
                                            Ext.MessageBox.alert("信息", "删除成功");
                                            //保存以后重置全局容器
                                            smsDeviceInfoHandsontableHelper.clearContainer();
                                            CreateAndLoadSMSDeviceInfoTable();
                                        } else {
                                            Ext.MessageBox.alert("信息", "数据保存失败");
                                        }
                                    },
                                    failure: function () {
                                        Ext.MessageBox.alert("信息", "请求失败");
                                        smsDeviceInfoHandsontableHelper.clearContainer();
                                    },
                                    params: {
                                        data: JSON.stringify(saveData),
                                        orgId: leftOrg_Id,
                                        deviceType: 300
                                    }
                                });
                            }
                        });
                    } else {
                        Ext.MessageBox.alert("信息", "请先选中要删除的行");
                    }
                }
            }, '-', {
                xtype: 'button',
                itemId: 'saveSMSDeviceDataBtnId',
                id: 'saveSMSDeviceDataBtn_Id',
                disabled: false,
                hidden: false,
                text: cosog.string.save,
                iconCls: 'save',
                handler: function (v, o) {
                    smsDeviceInfoHandsontableHelper.saveData();
                }
            }],
            items:[{
            	id: 'SMSDeviceTablePanel_id',
                layout: 'fit',
                html: '<div class="SMSDeviceContainer" style="width:100%;height:100%;"><div class="con" id="SMSDeviceTableDiv_id"></div></div>',
                listeners: {
                	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        if (smsDeviceInfoHandsontableHelper != null && smsDeviceInfoHandsontableHelper.hot != null && smsDeviceInfoHandsontableHelper.hot != undefined) {
                            smsDeviceInfoHandsontableHelper.hot.refreshDimensions();
                        }
                    }
                }
            }],
            listeners: {
                beforeclose: function (panel, eOpts) {
                    if (smsDeviceInfoHandsontableHelper != null) {
                        if (smsDeviceInfoHandsontableHelper.hot != undefined) {
                            smsDeviceInfoHandsontableHelper.hot.destroy();
                        }
                        smsDeviceInfoHandsontableHelper = null;
                    }
                }
            }
        })
        this.callParent(arguments);
    }
});

function CreateAndLoadSMSDeviceInfoTable(isNew) {
    if (isNew && smsDeviceInfoHandsontableHelper != null) {
        smsDeviceInfoHandsontableHelper.clearContainer();
        smsDeviceInfoHandsontableHelper.hot.destroy();
        smsDeviceInfoHandsontableHelper = null;
    }
    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
    var deviceName = Ext.getCmp('SMSDeviceListComb_Id').getValue();
    Ext.getCmp("SMSDeviceTablePanel_id").el.mask(cosog.string.loading).show();
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/wellInformationManagerController/doWellInformationShow',
        success: function (response) {
            Ext.getCmp("SMSDeviceTablePanel_id").getEl().unmask();
            var result = Ext.JSON.decode(response.responseText);
            if (smsDeviceInfoHandsontableHelper == null || smsDeviceInfoHandsontableHelper.hot == null || smsDeviceInfoHandsontableHelper.hot == undefined) {
                smsDeviceInfoHandsontableHelper = SMSDeviceInfoHandsontableHelper.createNew("SMSDeviceTableDiv_id");
                var colHeaders = "[";
                var columns = "[";

                for (var i = 0; i < result.columns.length; i++) {
                    colHeaders += "'" + result.columns[i].header + "'";
                    if (result.columns[i].dataIndex.toUpperCase() === "instanceName".toUpperCase()) {
                        var source = "[";
                        for (var j = 0; j < result.SMSInstanceDropdownData.length; j++) {
                            source += "\'" + result.SMSInstanceDropdownData[j] + "\'";
                            if (j < result.SMSInstanceDropdownData.length - 1) {
                                source += ",";
                            }
                        }
                        source += "]";
                        columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
                    } else if (result.columns[i].dataIndex.toUpperCase() === "sortNum".toUpperCase()) {
                        columns += "{data:'" + result.columns[i].dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,smsDeviceInfoHandsontableHelper);}}";
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
                smsDeviceInfoHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
                smsDeviceInfoHandsontableHelper.columns = Ext.JSON.decode(columns);
                if(result.totalRoot.length==0){
                	smsDeviceInfoHandsontableHelper.hiddenRows = [0];
                	smsDeviceInfoHandsontableHelper.createTable([{}]);
                }else{
                	smsDeviceInfoHandsontableHelper.hiddenRows = [];
                	smsDeviceInfoHandsontableHelper.createTable(result.totalRoot);
                }
            } else {
            	if(result.totalRoot.length==0){
            		smsDeviceInfoHandsontableHelper.hiddenRows = [0];
            		smsDeviceInfoHandsontableHelper.hot.loadData([{}]);
            	}else{
            		smsDeviceInfoHandsontableHelper.hiddenRows = [];
            		smsDeviceInfoHandsontableHelper.hot.loadData(result.totalRoot);
            	}
            }
            if (result.totalRoot.length == 0) {
                Ext.getCmp("SMSDeviceSelectRow_Id").setValue('');
                Ext.getCmp("SMSDeviceSelectEndRow_Id").setValue('');
            } else {
                Ext.getCmp("SMSDeviceSelectRow_Id").setValue(0);
                Ext.getCmp("SMSDeviceSelectEndRow_Id").setValue(0);
            }
            Ext.getCmp("SMSDeviceTotalCount_Id").update({
                count: result.totalCount
            });
        },
        failure: function () {
        	Ext.getCmp("SMSDeviceTablePanel_id").getEl().unmask();
            Ext.MessageBox.alert("错误", "与后台联系的时候出了问题");
        },
        params: {
            wellInformationName: deviceName,
            deviceType: 300,
            recordCount: 50,
            orgId: leftOrg_Id,
            page: 1,
            limit: 10000
        }
    });
};

var SMSDeviceInfoHandsontableHelper = {
    createNew: function (divid) {
        var smsDeviceInfoHandsontableHelper = {};
        smsDeviceInfoHandsontableHelper.hot = '';
        smsDeviceInfoHandsontableHelper.divid = divid;
        smsDeviceInfoHandsontableHelper.validresult = true; //数据校验
        smsDeviceInfoHandsontableHelper.colHeaders = [];
        smsDeviceInfoHandsontableHelper.columns = [];
        smsDeviceInfoHandsontableHelper.hiddenRows = [];

        smsDeviceInfoHandsontableHelper.AllData = {};
        smsDeviceInfoHandsontableHelper.updatelist = [];
        smsDeviceInfoHandsontableHelper.delidslist = [];
        smsDeviceInfoHandsontableHelper.insertlist = [];
        smsDeviceInfoHandsontableHelper.editWellNameList = [];

        smsDeviceInfoHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
        }

        smsDeviceInfoHandsontableHelper.createTable = function (data) {
            $('#' + smsDeviceInfoHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + smsDeviceInfoHandsontableHelper.divid);
            smsDeviceInfoHandsontableHelper.hot = new Handsontable(hotElement, {
                licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                data: data,
                hiddenColumns: {
                    columns: [0],
                    indicators: false
                },
                hiddenRows: {
                    rows: smsDeviceInfoHandsontableHelper.hiddenRows,
                    indicators: false
                },
                columns: smsDeviceInfoHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: true, //显示行头
                colHeaders: smsDeviceInfoHandsontableHelper.colHeaders, //显示列头
                columnSorting: true, //允许排序
                allowInsertRow: false,
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
                },
                afterSelectionEnd: function (row, column, row2, column2, preventScrolling, selectionLayerLevel) {
                    if (row < 0 && row2 < 0) { //只选中表头
                        Ext.getCmp("SMSDeviceSelectRow_Id").setValue('');
                        Ext.getCmp("SMSDeviceSelectEndRow_Id").setValue('');
                    } else {
                        if (row < 0) {
                            row = 0;
                        }
                        if (row2 < 0) {
                            row2 = 0;
                        }
                        var startRow = row;
                        var endRow = row2;
                        if (row > row2) {
                            startRow = row2;
                            endRow = row;
                        }
                        Ext.getCmp("SMSDeviceSelectRow_Id").setValue(startRow);
                        Ext.getCmp("SMSDeviceSelectEndRow_Id").setValue(endRow);
                    }
                },
                afterDestroy: function () {
                    // 移除事件
                    //	                    Handsontable.Dom.removeEvent(save, 'click', saveData);
                    //	                    loadDataTable();
                },
                beforeRemoveRow: function (index, amount) {
                    var ids = [];
                    //封装id成array传入后台
                    if (amount != 0) {
                        for (var i = index; i < amount + index; i++) {
                            var rowdata = smsDeviceInfoHandsontableHelper.hot.getDataAtRow(i);
                            ids.push(rowdata[0]);
                        }
                        smsDeviceInfoHandsontableHelper.delExpressCount(ids);
                        smsDeviceInfoHandsontableHelper.screening();
                    }
                },
                afterChange: function (changes, source) {
                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
                    if (changes != null) {
                        for (var i = 0; i < changes.length; i++) {
                            var params = [];
                            var index = changes[i][0]; //行号码
                            var rowdata = smsDeviceInfoHandsontableHelper.hot.getDataAtRow(index);
                            params.push(rowdata[0]);
                            params.push(changes[i][1]);
                            params.push(changes[i][2]);
                            params.push(changes[i][3]);

                            if ("edit" == source && params[1] == "wellName") { //编辑井名单元格
                                var data = "{\"oldWellName\":\"" + params[2] + "\",\"newWellName\":\"" + params[3] + "\"}";
                                smsDeviceInfoHandsontableHelper.editWellNameList.push(Ext.JSON.decode(data));
                            }

                            if (params[1] == "protocolName" && params[3] == "Kafka协议") {
                                smsDeviceInfoHandsontableHelper.hot.getCell(index, 6).source = ['modbus-tcp', 'modbus-rtu'];
                            }

                            //仅当单元格发生改变的时候,id!=null,说明是更新
                            if (params[2] != params[3] && params[0] != null && params[0] > 0) {
                                var data = "{";
                                for (var j = 0; j < smsDeviceInfoHandsontableHelper.columns.length; j++) {
                                    data += smsDeviceInfoHandsontableHelper.columns[j].data + ":'" + rowdata[j] + "'";
                                    if (j < smsDeviceInfoHandsontableHelper.columns.length - 1) {
                                        data += ","
                                    }
                                }
                                data += "}"
                                smsDeviceInfoHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
                            }
                        }
                    }
                }
            });
        }
        //插入的数据的获取
        smsDeviceInfoHandsontableHelper.insertExpressCount = function () {
            var idsdata = smsDeviceInfoHandsontableHelper.hot.getDataAtCol(0); //所有的id
            for (var i = 0; i < idsdata.length; i++) {
                //id=null时,是插入数据,此时的i正好是行号
                if (idsdata[i] == null || idsdata[i] < 0) {
                    //获得id=null时的所有数据封装进data
                    var rowdata = smsDeviceInfoHandsontableHelper.hot.getDataAtRow(i);
                    //var collength = hot.countCols();
                    if (rowdata != null) {
                        var data = "{";
                        for (var j = 0; j < smsDeviceInfoHandsontableHelper.columns.length; j++) {
                            data += smsDeviceInfoHandsontableHelper.columns[j].data + ":'" + rowdata[j] + "'";
                            if (j < smsDeviceInfoHandsontableHelper.columns.length - 1) {
                                data += ","
                            }
                        }
                        data += "}"
                        smsDeviceInfoHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
                    }
                }
            }
            if (smsDeviceInfoHandsontableHelper.insertlist.length != 0) {
                smsDeviceInfoHandsontableHelper.AllData.insertlist = smsDeviceInfoHandsontableHelper.insertlist;
            }
        }
        //保存数据
        smsDeviceInfoHandsontableHelper.saveData = function () {
            var leftOrg_Name = Ext.getCmp("leftOrg_Name").getValue();
            var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
            //插入的数据的获取
            smsDeviceInfoHandsontableHelper.insertExpressCount();
            if (JSON.stringify(smsDeviceInfoHandsontableHelper.AllData) != "{}" && smsDeviceInfoHandsontableHelper.validresult) {
                Ext.Ajax.request({
                    method: 'POST',
                    url: context + '/wellInformationManagerController/saveWellHandsontableData',
                    success: function (response) {
                        rdata = Ext.JSON.decode(response.responseText);
                        if (rdata.success) {
                            Ext.MessageBox.alert("信息", "保存成功");
                            //保存以后重置全局容器
                            smsDeviceInfoHandsontableHelper.clearContainer();
                            CreateAndLoadSMSDeviceInfoTable();
                        } else {
                            Ext.MessageBox.alert("信息", "数据保存失败");
                        }
                    },
                    failure: function () {
                        Ext.MessageBox.alert("信息", "请求失败");
                        smsDeviceInfoHandsontableHelper.clearContainer();
                    },
                    params: {
                        data: JSON.stringify(smsDeviceInfoHandsontableHelper.AllData),
                        orgId: leftOrg_Id,
                        deviceType: 300
                    }
                });
            } else {
                if (!smsDeviceInfoHandsontableHelper.validresult) {
                    Ext.MessageBox.alert("信息", "数据类型错误");
                } else {
                    Ext.MessageBox.alert("信息", "无数据变化");
                }
            }
        }

        //修改井名
        smsDeviceInfoHandsontableHelper.editWellName = function () {
            //插入的数据的获取

            if (smsDeviceInfoHandsontableHelper.editWellNameList.length > 0 && smsDeviceInfoHandsontableHelper.validresult) {
                //	            	alert(JSON.stringify(smsDeviceInfoHandsontableHelper.editWellNameList));
                Ext.Ajax.request({
                    method: 'POST',
                    url: context + '/wellInformationManagerController/editWellName',
                    success: function (response) {
                        rdata = Ext.JSON.decode(response.responseText);
                        if (rdata.success) {
                            Ext.MessageBox.alert("信息", "保存成功");
                            smsDeviceInfoHandsontableHelper.clearContainer();
                            CreateAndLoadSMSDeviceInfoTable();
                        } else {
                            Ext.MessageBox.alert("信息", "数据保存失败");

                        }
                    },
                    failure: function () {
                        Ext.MessageBox.alert("信息", "请求失败");
                        smsDeviceInfoHandsontableHelper.clearContainer();
                    },
                    params: {
                        data: JSON.stringify(smsDeviceInfoHandsontableHelper.editWellNameList),
                        deviceType: 300
                    }
                });
            } else {
                if (!smsDeviceInfoHandsontableHelper.validresult) {
                    Ext.MessageBox.alert("信息", "数据类型错误");
                } else {
                    Ext.MessageBox.alert("信息", "无数据变化");
                }
            }
        }


        //删除的优先级最高
        smsDeviceInfoHandsontableHelper.delExpressCount = function (ids) {
            //传入的ids.length不可能为0
            $.each(ids, function (index, id) {
                if (id != null) {
                    smsDeviceInfoHandsontableHelper.delidslist.push(id);
                }
            });
            smsDeviceInfoHandsontableHelper.AllData.delidslist = smsDeviceInfoHandsontableHelper.delidslist;
        }

        //updatelist数据更新
        smsDeviceInfoHandsontableHelper.screening = function () {
            if (smsDeviceInfoHandsontableHelper.updatelist.length != 0 && smsDeviceInfoHandsontableHelper.delidslist.lentgh != 0) {
                for (var i = 0; i < smsDeviceInfoHandsontableHelper.delidslist.length; i++) {
                    for (var j = 0; j < smsDeviceInfoHandsontableHelper.updatelist.length; j++) {
                        if (smsDeviceInfoHandsontableHelper.updatelist[j].id == smsDeviceInfoHandsontableHelper.delidslist[i]) {
                            //更新updatelist
                            smsDeviceInfoHandsontableHelper.updatelist.splice(j, 1);
                        }
                    }
                }
                //把updatelist封装进AllData
                smsDeviceInfoHandsontableHelper.AllData.updatelist = smsDeviceInfoHandsontableHelper.updatelist;
            }
        }

        //更新数据
        smsDeviceInfoHandsontableHelper.updateExpressCount = function (data) {
            if (JSON.stringify(data) != "{}") {
                var flag = true;
                //判断记录是否存在,更新数据     
                $.each(smsDeviceInfoHandsontableHelper.updatelist, function (index, node) {
                    if (node.id == data.id) {
                        //此记录已经有了
                        flag = false;
                        //用新得到的记录替换原来的,不用新增
                        smsDeviceInfoHandsontableHelper.updatelist[index] = data;
                    }
                });
                flag && smsDeviceInfoHandsontableHelper.updatelist.push(data);
                //封装
                smsDeviceInfoHandsontableHelper.AllData.updatelist = smsDeviceInfoHandsontableHelper.updatelist;
            }
        }

        smsDeviceInfoHandsontableHelper.clearContainer = function () {
            smsDeviceInfoHandsontableHelper.AllData = {};
            smsDeviceInfoHandsontableHelper.updatelist = [];
            smsDeviceInfoHandsontableHelper.delidslist = [];
            smsDeviceInfoHandsontableHelper.insertlist = [];
            smsDeviceInfoHandsontableHelper.editWellNameList = [];
        }

        return smsDeviceInfoHandsontableHelper;
    }
};