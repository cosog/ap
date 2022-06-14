var pumpingModelInfoHandsontableHelper = null;
var pumpingUnitPTFHandsontableHelper=null;
Ext.define('AP.view.well.PumpingModelInfoPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.pumpingModelInfoPanel',
    id: 'PumpingModelInfoPanel_Id',
    layout: 'fit',
    border: false,
    initComponent: function () {
        Ext.apply(this, {
        	items: [{
                layout: "border",
                border: false,
                items: [{
                	region: 'center',
                    layout: 'fit',
                    title: '抽油机数据',
                    border: false,
                    tbar: [{
                        id: 'PumpingModelSelectRow_Id',
                        xtype: 'textfield',
                        value: 0,
                        hidden: true
                    },{
                        id: 'PumpingModelSelectEndRow_Id',
                        xtype: 'textfield',
                        value: 0,
                        hidden: true
                    },{
                        xtype: "combobox",
                        fieldLabel: '辅件类型',
                        id: 'PumpingModelTypeComb_Id',
                        hidden:true,
                        labelWidth: 60,
                        width: 170,
                        labelAlign: 'left',
                        triggerAction: 'all',
                        displayField: "boxval",
                        valueField: "boxkey",
                        selectOnFocus: true,
                        forceSelection: true,
                        value: '',
                        allowBlank: false,
                        editable: false,
                        emptyText: cosog.string.all,
                        blankText: cosog.string.all,
                        store: new Ext.data.SimpleStore({
                            fields: ['boxkey', 'boxval'],
                            data: [['', '选择全部'], [0, '泵辅件'], [1, '管辅件']]
                        }),
                        queryMode: 'local',
                        listeners: {
                            select: function (v, o) {
                                CreateAndLoadPumpingModelInfoTable();
                            }
                        }
                    }, '-', {
                        xtype: 'button',
                        text: cosog.string.exportExcel,
//                        pressed: true,
                        iconCls: 'export',
                        hidden: false,
                        handler: function (v, o) {
                            var fields = "";
                            var heads = "";
                            var deviceType = Ext.getCmp('PumpingModelTypeComb_Id').getValue();
                            var url = context + '/wellInformationManagerController/exportPumpingModelData';
                            for (var i = 0; i < pumpingModelInfoHandsontableHelper.colHeaders.length; i++) {
                                fields += pumpingModelInfoHandsontableHelper.columns[i].data + ",";
                                heads += pumpingModelInfoHandsontableHelper.colHeaders[i] + ","
                            }
                            if (isNotVal(fields)) {
                                fields = fields.substring(0, fields.length - 1);
                                heads = heads.substring(0, heads.length - 1);
                            }
                            
                            var fileName='抽油机信息';
                            var title='抽油机信息';
                            var param = "&fields=" + fields + "&heads=" + URLencode(URLencode(heads)) 
                            + "&orgId=" + leftOrg_Id + "&deviceType=" + deviceType + "&recordCount=10000" 
                            + "&fileName=" + URLencode(URLencode(fileName)) 
                            + "&title=" + URLencode(URLencode(title));
                            openExcelWindow(url + '?flag=true' + param);
                        }
                    }, '-', {
                        xtype: 'button',
                        iconCls: 'note-refresh',
                        text: cosog.string.refresh,
//                        pressed: true,
                        hidden: false,
                        handler: function (v, o) {
                            CreateAndLoadPumpingModelInfoTable();
                        }

                    },'-', {
                        id: 'PumpingModelTotalCount_Id',
                        xtype: 'component',
                        hidden: false,
                        tpl: cosog.string.totalCount + ': {count}',
                        style: 'margin-right:15px'
                    }, '->',{
            			xtype: 'button',
                        text: '添加设备',
                        iconCls: 'add',
                        handler: function (v, o) {
                        	var window = Ext.create("AP.view.well.PumpingModelInfoWindow", {
                                title: '添加设备'
                            });
                            window.show();
                            Ext.getCmp("addFormPumpingModel_Id").show();
                            Ext.getCmp("updateFormPumpingModel_Id").hide();
                            return false;
            			}
            		}, '-',{
            			xtype: 'button',
            			text: '删除设备',
            			iconCls: 'delete',
            			handler: function (v, o) {
            				var startRow= Ext.getCmp("PumpingModelSelectRow_Id").getValue();
            				var endRow= Ext.getCmp("PumpingModelSelectEndRow_Id").getValue();
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
            	    						var rowdata = pumpingModelInfoHandsontableHelper.hot.getDataAtRow(i);
            	    						if (rowdata[0] != null && parseInt(rowdata[0])>0) {
            	    		                    pumpingModelInfoHandsontableHelper.delidslist.push(rowdata[0]);
            	    		                }
            	    					}
            	    					var saveData={};
            	    	            	saveData.updatelist=[];
            	    	            	saveData.insertlist=[];
            	    	            	saveData.delidslist=pumpingModelInfoHandsontableHelper.delidslist;
            	    	            	Ext.Ajax.request({
            	    	                    method: 'POST',
            	    	                    url: context + '/wellInformationManagerController/savePumpingModelHandsontableData',
            	    	                    success: function (response) {
            	    	                        rdata = Ext.JSON.decode(response.responseText);
            	    	                        if (rdata.success) {
            	    	                        	Ext.MessageBox.alert("信息", "删除成功");
            	    	                        	pumpingModelInfoHandsontableHelper.clearContainer();
            	    	                            CreateAndLoadPumpingModelInfoTable();
            	    	                        } else {
            	    	                            Ext.MessageBox.alert("信息", "数据保存失败");
            	    	                        }
            	    	                    },
            	    	                    failure: function () {
            	    	                        Ext.MessageBox.alert("信息", "请求失败");
            	    	                        pumpingModelInfoHandsontableHelper.clearContainer();
            	    	                    },
            	    	                    params: {
            	    	                        data: JSON.stringify(saveData)
            	    	                    }
            	    	                });
            			            }
            			        });
            				}else{
            					Ext.MessageBox.alert("信息","请先选中要删除的行");
            				}
            			}
            		}, '-', {
                        xtype: 'button',
                        itemId: 'savePumpingModelDataBtnId',
                        id: 'savePumpingModelDataBtn_Id',
                        disabled: false,
                        hidden: false,
                        text: cosog.string.save,
                        iconCls: 'save',
                        handler: function (v, o) {
                            pumpingModelInfoHandsontableHelper.saveData();
                        }
                    },"-",{
            			xtype: 'button',
                        text: '批量添加',
                        iconCls: 'batchAdd',
                        hidden: false,
                        handler: function (v, o) {
                        	var window = Ext.create("AP.view.well.BatchAddPumpingModelWindow", {
                                title: '辅件设备批量添加'
                            });
                            window.show();
                            return false;
            			}
            		}],
                    html: '<div class="PumpingModelContainer" style="width:100%;height:100%;"><div class="con" id="PumpingModelTableDiv_id"></div></div>',
                    listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            if (pumpingModelInfoHandsontableHelper != null && pumpingModelInfoHandsontableHelper.hot != null && pumpingModelInfoHandsontableHelper.hot != undefined) {
                            	pumpingModelInfoHandsontableHelper.hot.refreshDimensions();
                            }
                        }
                    }
                }, {
                    region: 'east',
                    width: '33%',
                    title: '抽油机位置扭矩因数',
                    id: 'PumpingUnitPTFPanel_Id',
                    collapsible: true, // 是否折叠
                    split: true, // 竖折叠条
                    border: false,
                    tbar: [{
                        xtype: "combobox",
                        fieldLabel: '冲程',
                        id: 'PumpingModelPRTFStrokeComb_Id',
                        labelWidth: 30,
                        width: 140,
                        labelAlign: 'left',
                        triggerAction: 'all',
                        displayField: "boxval",
                        valueField: "boxkey",
                        selectOnFocus: true,
                        forceSelection: true,
                        value: '',
                        allowBlank: true,
                        editable: false,
                        emptyText: cosog.string.all,
                        blankText: cosog.string.all,
                        store: new Ext.data.SimpleStore({
                            fields: ['boxkey', 'boxval'],
                            data: [['','']]
                        }),
                        queryMode: 'local',
                        listeners: {
                            select: function (v, o) {
                            	var selectedRow=Ext.getCmp("PumpingModelSelectRow_Id").getValue();
                            	var recordId=pumpingModelInfoHandsontableHelper.hot.getDataAtCell(selectedRow,0);
                            	var manufacturer=pumpingModelInfoHandsontableHelper.hot.getDataAtCell(selectedRow,1);
                            	var model=pumpingModelInfoHandsontableHelper.hot.getDataAtCell(selectedRow,2);
                            	CreateAndLoadPumpingUnitPTFTable(recordId,manufacturer,model,v.rawValue);
                            }
                        }
                    },'->', {
                        xtype: 'button',
                        id: 'savePumpingPRTFDataBtn_Id',
                        disabled: false,
                        hidden: false,
                        text: cosog.string.save,
                        iconCls: 'save',
                        handler: function (v, o) {
                        	pumpingUnitPTFHandsontableHelper.saveData();
                        }
                    }],
                    html: '<div id="PumpingUnitPTFDiv_Id" style="width:100%;height:100%;"></div>',
                    listeners: {
                    	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    		if (pumpingUnitPTFHandsontableHelper != null && pumpingUnitPTFHandsontableHelper.hot != null && pumpingUnitPTFHandsontableHelper.hot != undefined) {
                    			pumpingUnitPTFHandsontableHelper.hot.refreshDimensions();
                            }
                        }
                    }
                }]
            }],
            listeners: {
                beforeclose: function (panel, eOpts) {
                    if (pumpingModelInfoHandsontableHelper != null) {
                        if (pumpingModelInfoHandsontableHelper.hot != undefined) {
                            pumpingModelInfoHandsontableHelper.hot.destroy();
                        }
                        pumpingModelInfoHandsontableHelper = null;
                    }if (pumpingUnitPTFHandsontableHelper != null) {
                        if (pumpingUnitPTFHandsontableHelper.hot != undefined) {
                        	pumpingUnitPTFHandsontableHelper.hot.destroy();
                        }
                        pumpingUnitPTFHandsontableHelper = null;
                    }
                }
            }
        })
        this.callParent(arguments);
    }
});

function CreateAndLoadPumpingModelInfoTable(isNew) {
    if(isNew&&pumpingModelInfoHandsontableHelper!=null){
    	if(pumpingModelInfoHandsontableHelper.hot!=undefined){
    		pumpingModelInfoHandsontableHelper.hot.destroy();
    	}
    	pumpingModelInfoHandsontableHelper=null;
    }
    var deviceType = Ext.getCmp('PumpingModelTypeComb_Id').getValue();
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/wellInformationManagerController/doPumpingModelShow',
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (pumpingModelInfoHandsontableHelper == null || pumpingModelInfoHandsontableHelper.hot == null || pumpingModelInfoHandsontableHelper.hot == undefined) {
                pumpingModelInfoHandsontableHelper = PumpingModelInfoHandsontableHelper.createNew("PumpingModelTableDiv_id");
                var colHeaders="['序号','厂家','型号','冲程(m)','旋转方向','曲柄偏置角(°)','曲柄重心半径(m)','单块曲柄重量(kN)','单块曲柄销重量(kN)','结构不平衡重(kN)','平衡块重量(kN)']";
                var columns="[{data:'id'},{data:'manufacturer'},{data:'model'},{data:'stroke'}," 
                	+"{data:'crankRotationDirection',type:'dropdown',strict:true,allowInvalid:false,source:['顺时针', '逆时针']}," 
                	+"{data:'offsetAngleOfCrank'},{data:'crankGravityRadius'},{data:'singleCrankWeight'},{data:'singleCrankPinWeight'}," 
                	+"{data:'structuralUnbalance'},{data:'balanceWeight'}]";
                pumpingModelInfoHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
                pumpingModelInfoHandsontableHelper.columns = Ext.JSON.decode(columns);
                pumpingModelInfoHandsontableHelper.createTable(result.totalRoot);
            } else {
                pumpingModelInfoHandsontableHelper.hot.loadData(result.totalRoot);
            }
            
            var manufacturer='';
            var model='';
            var recordId=0;
            if(result.totalRoot.length==0){
            	Ext.getCmp("PumpingModelSelectRow_Id").setValue('');
            	Ext.getCmp("PumpingModelSelectEndRow_Id").setValue('');
            }else{
            	Ext.getCmp("PumpingModelSelectRow_Id").setValue(0);
            	Ext.getCmp("PumpingModelSelectEndRow_Id").setValue(0);
            	recordId=pumpingModelInfoHandsontableHelper.hot.getDataAtCell(0,0);
            	manufacturer=pumpingModelInfoHandsontableHelper.hot.getDataAtCell(0,1);
            	model=pumpingModelInfoHandsontableHelper.hot.getDataAtCell(0,2);
            }
            Ext.getCmp("PumpingModelPRTFStrokeComb_Id").setValue('');
			Ext.getCmp("PumpingModelPRTFStrokeComb_Id").setRawValue('');
			CreateAndLoadPumpingUnitPTFTable(recordId,manufacturer,model,'');
			
            Ext.getCmp("PumpingModelTotalCount_Id").update({
                count: result.totalCount
            });
        },
        failure: function () {
            Ext.MessageBox.alert("错误", "与后台联系的时候出了问题");
        },
        params: {
            deviceType: deviceType,
            recordCount: 50,
            page: 1,
            limit: 10000
        }
    });
};

var PumpingModelInfoHandsontableHelper = {
    createNew: function (divid) {
        var pumpingModelInfoHandsontableHelper = {};
        pumpingModelInfoHandsontableHelper.hot = '';
        pumpingModelInfoHandsontableHelper.divid = divid;
        pumpingModelInfoHandsontableHelper.validresult = true; //数据校验
        pumpingModelInfoHandsontableHelper.colHeaders = [];
        pumpingModelInfoHandsontableHelper.columns = [];

        pumpingModelInfoHandsontableHelper.AllData = {};
        pumpingModelInfoHandsontableHelper.updatelist = [];
        pumpingModelInfoHandsontableHelper.delidslist = [];
        pumpingModelInfoHandsontableHelper.insertlist = [];
        pumpingModelInfoHandsontableHelper.editNameList = [];

        pumpingModelInfoHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
        }

        pumpingModelInfoHandsontableHelper.createTable = function (data) {
            $('#' + pumpingModelInfoHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + pumpingModelInfoHandsontableHelper.divid);
            pumpingModelInfoHandsontableHelper.hot = new Handsontable(hotElement, {
            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            	data: data,
                hiddenColumns: {
                    columns: [0],
                    indicators: false
                },
                columns: pumpingModelInfoHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: true, //显示行头
                colHeaders: pumpingModelInfoHandsontableHelper.colHeaders, //显示列头
                columnSorting: true, //允许排序
                allowInsertRow:false,
                sortIndicator: true,
                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                //	                dropdownMenu: ['filter_by_condition', 'filter_by_value', 'filter_action_bar'],
                filters: true,
                renderAllRows: true,
                search: true,
                cells: function (row, col, prop) {
                    var cellProperties = {};
                    var visualRowIndex = this.instance.toVisualRow(row);
                    var visualColIndex = this.instance.toVisualColumn(col);
                },
                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
                	if(row<0 && row2<0){//只选中表头
                		Ext.getCmp("PumpingModelSelectRow_Id").setValue('');
                    	Ext.getCmp("PumpingModelSelectEndRow_Id").setValue('');
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
                    	Ext.getCmp("PumpingModelSelectRow_Id").setValue(startRow);
                    	Ext.getCmp("PumpingModelSelectEndRow_Id").setValue(endRow);
                    	Ext.getCmp("PumpingModelPRTFStrokeComb_Id").setValue('');
            			Ext.getCmp("PumpingModelPRTFStrokeComb_Id").setRawValue('');
                    	var recordId=pumpingModelInfoHandsontableHelper.hot.getDataAtCell(startRow,0);
                    	var manufacturer=pumpingModelInfoHandsontableHelper.hot.getDataAtCell(startRow,1);
                    	var model=pumpingModelInfoHandsontableHelper.hot.getDataAtCell(startRow,2);
                    	var stroke=Ext.getCmp("PumpingModelPRTFStrokeComb_Id").rawValue;
                    	
                    	CreateAndLoadPumpingUnitPTFTable(recordId,manufacturer,model,stroke);
                	}
                },
                afterDestroy: function () {
                },
                beforeRemoveRow: function (index, amount) {
                    var ids = [];
                    //封装id成array传入后台
                    if (amount != 0) {
                        for (var i = index; i < amount + index; i++) {
                            var rowdata = pumpingModelInfoHandsontableHelper.hot.getDataAtRow(i);
                            ids.push(rowdata[0]);
                        }
                        pumpingModelInfoHandsontableHelper.delExpressCount(ids);
                        pumpingModelInfoHandsontableHelper.screening();
                    }
                },
                afterChange: function (changes, source) {
                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
                    if (changes != null) {
                        for (var i = 0; i < changes.length; i++) {
                            var params = [];
                            var index = changes[i][0]; //行号码
                            var rowdata = pumpingModelInfoHandsontableHelper.hot.getDataAtRow(index);
                            params.push(rowdata[0]);
                            params.push(changes[i][1]);
                            params.push(changes[i][2]);
                            params.push(changes[i][3]);
                            if ("edit" == source && params[1] == "name") { //编辑井名单元格
                                var data = "{\"oldName\":\"" + params[2] + "\",\"newName\":\"" + params[3] + "\"}";
                                pumpingModelInfoHandsontableHelper.editNameList.push(Ext.JSON.decode(data));
                            }

                            //仅当单元格发生改变的时候,id!=null,说明是更新
                            if (params[2] != params[3] && params[0] != null && params[0] > 0) {
                                var data = "{";
                                for (var j = 0; j < pumpingModelInfoHandsontableHelper.columns.length; j++) {
                                    data += pumpingModelInfoHandsontableHelper.columns[j].data + ":'" + rowdata[j] + "'";
                                    if (j < pumpingModelInfoHandsontableHelper.columns.length - 1) {
                                        data += ","
                                    }
                                }
                                data += "}"
                                pumpingModelInfoHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
                            }
                        }
                    }
                }
            });
        }
        //插入的数据的获取
        pumpingModelInfoHandsontableHelper.insertExpressCount = function () {
            var idsdata = pumpingModelInfoHandsontableHelper.hot.getDataAtCol(0); //所有的id
            for (var i = 0; i < idsdata.length; i++) {
                //id=null时,是插入数据,此时的i正好是行号
                if (idsdata[i] == null || idsdata[i] < 0) {
                    //获得id=null时的所有数据封装进data
                    var rowdata = pumpingModelInfoHandsontableHelper.hot.getDataAtRow(i);
                    //var collength = hot.countCols();
                    if (rowdata != null) {
                        var data = "{";
                        for (var j = 0; j < pumpingModelInfoHandsontableHelper.columns.length; j++) {
                            data += pumpingModelInfoHandsontableHelper.columns[j].data + ":'" + rowdata[j] + "'";
                            if (j < pumpingModelInfoHandsontableHelper.columns.length - 1) {
                                data += ","
                            }
                        }
                        data += "}"
                        pumpingModelInfoHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
                    }
                }
            }
            if (pumpingModelInfoHandsontableHelper.insertlist.length != 0) {
                pumpingModelInfoHandsontableHelper.AllData.insertlist = pumpingModelInfoHandsontableHelper.insertlist;
            }
        }
        //保存数据
        pumpingModelInfoHandsontableHelper.saveData = function () {
            var IframeViewSelection = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
            var selectedRecordId=0;
            var row=parseInt(Ext.getCmp("PumpingModelSelectRow_Id").getValue());
            if(Ext.getCmp("PumpingModelSelectRow_Id").getValue()!=''){
            	selectedRecordId=pumpingModelInfoHandsontableHelper.hot.getDataAtCell(row,0);
            }
            
            //插入的数据的获取 
            pumpingModelInfoHandsontableHelper.insertExpressCount();
            if (pumpingModelInfoHandsontableHelper.validresult) {
                Ext.Ajax.request({
                    method: 'POST',
                    url: context + '/wellInformationManagerController/savePumpingModelHandsontableData',
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
                            	pumpingModelInfoHandsontableHelper.clearContainer();
                                CreateAndLoadPumpingModelInfoTable();
                            }
                        } else {
                            Ext.MessageBox.alert("信息", "数据保存失败");
                        }
                    },
                    failure: function () {
                        Ext.MessageBox.alert("信息", "请求失败");
                        pumpingModelInfoHandsontableHelper.clearContainer();
                    },
                    params: {
                        data: JSON.stringify(pumpingModelInfoHandsontableHelper.AllData),
                        selectedRecordId:selectedRecordId
                    }
                });
            } else {
                if (!pumpingModelInfoHandsontableHelper.validresult) {
                    Ext.MessageBox.alert("信息", "数据类型错误");
                } else {
                    Ext.MessageBox.alert("信息", "无数据变化");
                }
            }

        }

        //修改设备名称
        pumpingModelInfoHandsontableHelper.editWellName = function () {
            if (pumpingModelInfoHandsontableHelper.editNameList.length > 0 && pumpingModelInfoHandsontableHelper.validresult) {
                Ext.Ajax.request({
                    method: 'POST',
                    url: context + '/wellInformationManagerController/editPumpingModelName',
                    success: function (response) {
                        rdata = Ext.JSON.decode(response.responseText);
                        if (rdata.success) {
                            Ext.MessageBox.alert("信息", "保存成功");
                            pumpingModelInfoHandsontableHelper.clearContainer();
                            CreateAndLoadPumpingModelInfoTable();
                        } else {
                            Ext.MessageBox.alert("信息", "数据保存失败");
                        }
                    },
                    failure: function () {
                        Ext.MessageBox.alert("信息", "请求失败");
                        pumpingModelInfoHandsontableHelper.clearContainer();
                    },
                    params: {
                        data: JSON.stringify(pumpingModelInfoHandsontableHelper.editNameList)
                    }
                });
            } else {
                if (!pumpingModelInfoHandsontableHelper.validresult) {
                    Ext.MessageBox.alert("信息", "数据类型错误");
                } else {
                    Ext.MessageBox.alert("信息", "无数据变化");
                }
            }
        }


        //删除的优先级最高
        pumpingModelInfoHandsontableHelper.delExpressCount = function (ids) {
            //传入的ids.length不可能为0
            $.each(ids, function (index, id) {
                if (id != null) {
                    pumpingModelInfoHandsontableHelper.delidslist.push(id);
                }
            });
            pumpingModelInfoHandsontableHelper.AllData.delidslist = pumpingModelInfoHandsontableHelper.delidslist;
        }

        //updatelist数据更新
        pumpingModelInfoHandsontableHelper.screening = function () {
            if (pumpingModelInfoHandsontableHelper.updatelist.length != 0 && pumpingModelInfoHandsontableHelper.delidslist.lentgh != 0) {
                for (var i = 0; i < pumpingModelInfoHandsontableHelper.delidslist.length; i++) {
                    for (var j = 0; j < pumpingModelInfoHandsontableHelper.updatelist.length; j++) {
                        if (pumpingModelInfoHandsontableHelper.updatelist[j].id == pumpingModelInfoHandsontableHelper.delidslist[i]) {
                            //更新updatelist
                            pumpingModelInfoHandsontableHelper.updatelist.splice(j, 1);
                        }
                    }
                }
                //把updatelist封装进AllData
                pumpingModelInfoHandsontableHelper.AllData.updatelist = pumpingModelInfoHandsontableHelper.updatelist;
            }
        }

        //更新数据
        pumpingModelInfoHandsontableHelper.updateExpressCount = function (data) {
            if (JSON.stringify(data) != "{}") {
                var flag = true;
                //判断记录是否存在,更新数据     
                $.each(pumpingModelInfoHandsontableHelper.updatelist, function (index, node) {
                    if (node.id == data.id) {
                        //此记录已经有了
                        flag = false;
                        //用新得到的记录替换原来的,不用新增
                        pumpingModelInfoHandsontableHelper.updatelist[index] = data;
                    }
                });
                flag && pumpingModelInfoHandsontableHelper.updatelist.push(data);
                //封装
                pumpingModelInfoHandsontableHelper.AllData.updatelist = pumpingModelInfoHandsontableHelper.updatelist;
            }
        }

        pumpingModelInfoHandsontableHelper.clearContainer = function () {
            pumpingModelInfoHandsontableHelper.AllData = {};
            pumpingModelInfoHandsontableHelper.updatelist = [];
            pumpingModelInfoHandsontableHelper.delidslist = [];
            pumpingModelInfoHandsontableHelper.insertlist = [];
            pumpingModelInfoHandsontableHelper.editNameList = [];
        }

        return pumpingModelInfoHandsontableHelper;
    }
};

function CreateAndLoadPumpingUnitPTFTable(recordId,manufacturer,model,stroke){
	if(isNotVal(manufacturer)){
		Ext.getCmp("PumpingUnitPTFPanel_Id").setTitle("<font color=red>"+manufacturer+"/"+model+"</font>位置扭矩因数");
	}else{
		Ext.getCmp("PumpingUnitPTFPanel_Id").setTitle("抽油机位置扭矩因数");
	}
	Ext.Ajax.request({
        method: 'POST',
        url: context + '/wellInformationManagerController/getPumpingPRTFData',
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            
            
        	Ext.getCmp("PumpingModelPRTFStrokeComb_Id").getStore().loadData(result.strokeList);
			if(result.strokeList.length>0){
				var pumpingModelPRTFStrokeCombValeu=Ext.getCmp("PumpingModelPRTFStrokeComb_Id").getValue();
				if(!isNotVal(pumpingModelPRTFStrokeCombValeu)){
					Ext.getCmp("PumpingModelPRTFStrokeComb_Id").setValue(result.strokeList[0][0]);
					Ext.getCmp("PumpingModelPRTFStrokeComb_Id").setRawValue(result.strokeList[0][0]);
				}
			}else{
				Ext.getCmp("PumpingModelPRTFStrokeComb_Id").setValue('');
				Ext.getCmp("PumpingModelPRTFStrokeComb_Id").setRawValue('');
			}
            
            if (pumpingUnitPTFHandsontableHelper == null || pumpingUnitPTFHandsontableHelper.hot == null || pumpingUnitPTFHandsontableHelper.hot == undefined) {
            	pumpingUnitPTFHandsontableHelper = PumpingUnitPTFHandsontableHelper.createNew("PumpingUnitPTFDiv_Id");
            	var colHeaders="['曲柄转角(°)','光杆位置因数(%)','扭矩因数(m)']";
        		var columns="[{data:'CrankAngle'},{data:'PR'},{data:'TF'}]";
        		pumpingUnitPTFHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
        		pumpingUnitPTFHandsontableHelper.columns = Ext.JSON.decode(columns);
        		pumpingUnitPTFHandsontableHelper.createTable(result.totalRoot);
            }else {
                if(result.totalRoot==0){
                	pumpingUnitPTFHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
        		}else{
        			pumpingUnitPTFHandsontableHelper.hot.loadData(result.totalRoot);
        		}
            }
        },
        failure: function () {
            Ext.MessageBox.alert("错误", "与后台联系的时候出了问题");
        },
        params: {
        	recordId: recordId,
        	stroke:stroke
        }
    });
};

var PumpingUnitPTFHandsontableHelper = {
	    createNew: function (divid) {
	        var pumpingUnitPTFHandsontableHelper = {};
	        pumpingUnitPTFHandsontableHelper.hot1 = '';
	        pumpingUnitPTFHandsontableHelper.divid = divid;
	        pumpingUnitPTFHandsontableHelper.validresult=true;//数据校验
	        pumpingUnitPTFHandsontableHelper.colHeaders=[];
	        pumpingUnitPTFHandsontableHelper.columns=[];
	        pumpingUnitPTFHandsontableHelper.AllData=[];
	        
	        pumpingUnitPTFHandsontableHelper.createTable = function (data) {
	        	$('#'+pumpingUnitPTFHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+pumpingUnitPTFHandsontableHelper.divid);
	        	pumpingUnitPTFHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
//	                hiddenColumns: {
//	                    columns: [0],
//	                    indicators: false
//	                },
	                columns:pumpingUnitPTFHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:pumpingUnitPTFHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true
	        	});
	        }
	        //保存数据
	        pumpingUnitPTFHandsontableHelper.saveData = function () {
	            var selectedRecordId=0;
	            var stroke=Ext.getCmp("PumpingModelPRTFStrokeComb_Id").rawValue;
	            var row=parseInt(Ext.getCmp("PumpingModelSelectRow_Id").getValue());
	            if(Ext.getCmp("PumpingModelSelectRow_Id").getValue()!=''){
	            	selectedRecordId=pumpingModelInfoHandsontableHelper.hot.getDataAtCell(row,0);
	            }
	            var strokePRTFData={};
	            strokePRTFData.Stroke=stroke;
	            strokePRTFData.PRTF=[];
	            var PRTFData=pumpingUnitPTFHandsontableHelper.hot.getData();
	            for(var i=0;i<PRTFData.length;i++){
	            	if(isNumber(PRTFData[i][0]) && isNumber(PRTFData[i][1]) && isNumber(PRTFData[i][2])){
	            		var PRTF={};
		            	PRTF.CrankAngle=parseFloat(PRTFData[i][0]);
		            	PRTF.PR=parseFloat(PRTFData[i][1]);
		            	PRTF.TF=parseFloat(PRTFData[i][2]);
		            	strokePRTFData.PRTF.push(PRTF);
	            	}
	            }
	            
                Ext.Ajax.request({
                    method: 'POST',
                    url: context + '/wellInformationManagerController/savePumpingPRTFData',
                    success: function (response) {
                    	rdata = Ext.JSON.decode(response.responseText);
                        if (rdata.success) {
                        	var saveInfo='保存成功';
                        	Ext.MessageBox.alert("信息", saveInfo);
                        } else {
                            Ext.MessageBox.alert("信息", "数据保存失败");
                        }
                    },
                    failure: function () {
                        Ext.MessageBox.alert("信息", "请求失败");
                        pumpingUnitPTFHandsontableHelper.clearContainer();
                    },
                    params: {
                        data: JSON.stringify(strokePRTFData),
                        recordId:selectedRecordId
                    }
                });
	        }
	        pumpingUnitPTFHandsontableHelper.clearContainer = function () {
	        	pumpingUnitPTFHandsontableHelper.AllData = [];
	        }
	        return pumpingUnitPTFHandsontableHelper;
	    }
};