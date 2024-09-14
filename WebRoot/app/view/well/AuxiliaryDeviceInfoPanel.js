var auxiliaryDeviceInfoHandsontableHelper = null;
var auxiliaryDeviceDetailsHandsontableHelper=null;
Ext.define('AP.view.well.AuxiliaryDeviceInfoPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.auxiliaryDeviceInfoPanel',
    id: 'AuxiliaryDeviceInfoPanel_Id',
    layout: 'fit',
    border: false,
    initComponent: function () {
        Ext.apply(this, {
            tbar: [{
                id: 'AuxiliaryDeviceSelectRow_Id',
                xtype: 'textfield',
                value: 0,
                hidden: true
            },{
                id: 'AuxiliaryDeviceSelectEndRow_Id',
                xtype: 'textfield',
                value: 0,
                hidden: true
            },{
                xtype: 'button',
                text: cosog.string.exportExcel,
//                pressed: true,
                iconCls: 'export',
                hidden: false,
                handler: function (v, o) {
                    var fields = "";
                    var heads = "";
                    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var deviceType=getDeviceTypeFromTabId("AuxiliaryDeviceManagerTabPanel");
                    var deviceTypeName=getTabPanelActiveName("AuxiliaryDeviceManagerTabPanel");
                    var url = context + '/wellInformationManagerController/exportAuxiliaryDeviceData';
                    for (var i = 0; i < auxiliaryDeviceInfoHandsontableHelper.colHeaders.length; i++) {
                        fields += auxiliaryDeviceInfoHandsontableHelper.columns[i].data + ",";
                        heads += auxiliaryDeviceInfoHandsontableHelper.colHeaders[i] + ","
                    }
                    if (isNotVal(fields)) {
                        fields = fields.substring(0, fields.length - 1);
                        heads = heads.substring(0, heads.length - 1);
                    }
                    
                    var fileName=deviceTypeName+'辅件设备';
                    var title=fileName;

                    var param = "&fields=" + fields 
                    + "&heads=" + URLencode(URLencode(heads)) 
                    + "&orgId=" + leftOrg_Id 
                    + "&deviceType=" + deviceType 
                    + "&recordCount=10000" 
                    + "&fileName=" + URLencode(URLencode(fileName)) 
                    + "&title=" + URLencode(URLencode(title));
                    openExcelWindow(url + '?flag=true' + param);
                }
            }, '-', {
                xtype: 'button',
                iconCls: 'note-refresh',
                text: cosog.string.refresh,
//                pressed: true,
                hidden: false,
                handler: function (v, o) {
                    CreateAndLoadAuxiliaryDeviceInfoTable();
                }

            },'-', {
                id: 'AuxiliaryDeviceTotalCount_Id',
                xtype: 'component',
                hidden: false,
                tpl: cosog.string.totalCount + ': {count}',
                style: 'margin-right:15px'
            }, '->',{
    			xtype: 'button',
                text: '添加设备',
                iconCls: 'add',
                handler: function (v, o) {
                	var window = Ext.create("AP.view.well.AuxiliaryDeviceInfoWindow", {
                        title: '添加设备'
                    });
                    window.show();
                    var deviceType=getDeviceTypeFromTabId("AuxiliaryDeviceManagerTabPanel");
                    Ext.getCmp("auxiliaryDeviceType_Id").setValue(deviceType);
                    Ext.getCmp("addFormAuxiliaryDevice_Id").show();
                    Ext.getCmp("updateFormAuxiliaryDevice_Id").hide();
                    return false;
    			}
    		}, '-',{
    			xtype: 'button',
    			text: '删除设备',
    			iconCls: 'delete',
    			handler: function (v, o) {
    				var startRow= Ext.getCmp("AuxiliaryDeviceSelectRow_Id").getValue();
    				var endRow= Ext.getCmp("AuxiliaryDeviceSelectEndRow_Id").getValue();
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
    	    						var rowdata = auxiliaryDeviceInfoHandsontableHelper.hot.getDataAtRow(i);
    	    						if (rowdata[0] != null && parseInt(rowdata[0])>0) {
    	    		                    auxiliaryDeviceInfoHandsontableHelper.delidslist.push(rowdata[0]);
    	    		                }
    	    					}
    	    					var saveData={};
    	    	            	saveData.updatelist=[];
    	    	            	saveData.insertlist=[];
    	    	            	saveData.delidslist=auxiliaryDeviceInfoHandsontableHelper.delidslist;
    	    	            	Ext.Ajax.request({
    	    	                    method: 'POST',
    	    	                    url: context + '/wellInformationManagerController/saveAuxiliaryDeviceHandsontableData',
    	    	                    success: function (response) {
    	    	                        rdata = Ext.JSON.decode(response.responseText);
    	    	                        if (rdata.success) {
    	    	                        	Ext.MessageBox.alert("信息", "删除成功");
    	    	                        	auxiliaryDeviceInfoHandsontableHelper.clearContainer();
    	    	                            CreateAndLoadAuxiliaryDeviceInfoTable();
    	    	                        } else {
    	    	                            Ext.MessageBox.alert("信息", "数据保存失败");
    	    	                        }
    	    	                    },
    	    	                    failure: function () {
    	    	                        Ext.MessageBox.alert("信息", "请求失败");
    	    	                        auxiliaryDeviceInfoHandsontableHelper.clearContainer();
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
                itemId: 'saveAuxiliaryDeviceDataBtnId',
                id: 'saveAuxiliaryDeviceDataBtn_Id',
                disabled: false,
                hidden: false,
                text: cosog.string.save,
                iconCls: 'save',
                handler: function (v, o) {
                    auxiliaryDeviceInfoHandsontableHelper.saveData();
                }
            },"-",{
    			xtype: 'button',
                text: '批量添加',
                iconCls: 'batchAdd',
                hidden: false,
                handler: function (v, o) {
                	var window = Ext.create("AP.view.well.BatchAddAuxiliaryDeviceWindow", {
                        title: '辅件设备批量添加'
                    });
                    window.show();
                    return false;
    			}
    		}],
    		layout: 'border',
    		items: [{
    			region: 'center',
    			title:'设备列表',
        		header: true,
        		layout: 'fit',
        		id:'AuxiliaryDeviceListPanel_Id',
        		html: '<div class="AuxiliaryDeviceContainer" style="width:100%;height:100%;"><div class="con" id="AuxiliaryDeviceTableDiv_id"></div></div>',
                listeners: {
                	resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if (auxiliaryDeviceInfoHandsontableHelper != null && auxiliaryDeviceInfoHandsontableHelper.hot != null && auxiliaryDeviceInfoHandsontableHelper.hot != undefined) {
                        	var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		auxiliaryDeviceInfoHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                        }
                    }
                }
    		},{
    			region: 'east',
            	width: '50%',
            	split: true,
            	collapsible: true,
            	title:'详细信息',
        		id:'AuxiliaryDeviceDetailsPanel_Id',
        		html: '<div class="AuxiliaryDeviceDetailsContainer" style="width:100%;height:100%;"><div class="con" id="AuxiliaryDeviceDetailsTableDiv_id"></div></div>',
        		tbar:onlyMonitor?null:[{
                    xtype: 'radiogroup',
                    fieldLabel: '指定类型',
                    hidden: onlyMonitor,
                    labelWidth: 60,
                    id: 'AuxiliaryDeviceSpecificType_Id',
                    cls: 'x-check-group-alt',
                    items: [
                        {boxLabel: '抽油机',name: 'auxiliaryDeviceSpecificType',width: 70, inputValue: 1},
                        {boxLabel: '无',name: 'auxiliaryDeviceSpecificType',width: 70, inputValue: 0}
                    ],
                    listeners: {
                    	change: function (radiogroup, newValue, oldValue, eOpts) {
            				var deviceId=0;
            				var specificType=0;
            				var DeviceSelectRow= Ext.getCmp("AuxiliaryDeviceSelectRow_Id").getValue();
            				if(isNotVal(DeviceSelectRow)){
            					var deviceInfoHandsontableData=auxiliaryDeviceInfoHandsontableHelper.hot.getData();
                	        	if(deviceInfoHandsontableData.length>0){
                	        		var rowdata = auxiliaryDeviceInfoHandsontableHelper.hot.getDataAtRow(DeviceSelectRow);
                	        		deviceId=rowdata[0];
                	        		specificType=rowdata[1];
                	        	}
            				}
            				CreateAuxiliaryDeviceDetailsTable(deviceId,specificType);
                      	}
                    }
                }],
                listeners: {
                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if (auxiliaryDeviceDetailsHandsontableHelper != null && auxiliaryDeviceDetailsHandsontableHelper.hot != null && auxiliaryDeviceDetailsHandsontableHelper.hot != undefined) {
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		auxiliaryDeviceDetailsHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                        }
                    }
                }
    		}],
            listeners: {
                beforeclose: function (panel, eOpts) {
                    
                }
            }
        })
        this.callParent(arguments);
    }
});

function CreateAndLoadAuxiliaryDeviceInfoTable(isNew) {
    if(isNew&&auxiliaryDeviceInfoHandsontableHelper!=null){
    	if(auxiliaryDeviceInfoHandsontableHelper.hot!=undefined){
    		auxiliaryDeviceInfoHandsontableHelper.hot.destroy();
    	}
    	auxiliaryDeviceInfoHandsontableHelper=null;
    }
    var deviceType=getDeviceTypeFromTabId("AuxiliaryDeviceManagerTabPanel");
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/wellInformationManagerController/doAuxiliaryDeviceShow',
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (auxiliaryDeviceInfoHandsontableHelper == null || auxiliaryDeviceInfoHandsontableHelper.hot == null || auxiliaryDeviceInfoHandsontableHelper.hot == undefined) {
                auxiliaryDeviceInfoHandsontableHelper = AuxiliaryDeviceInfoHandsontableHelper.createNew("AuxiliaryDeviceTableDiv_id");
                var colHeaders="['序号','类型','设备名称','厂家','规格型号','备注','排序编号']";
                var columns="[{data:'id'}," 
                		+"{data:'specificType'}," 
                		+"{data:'name'}," 
                		+"{data:'manufacturer'}," 
                		+"{data:'model'},"
                		+"{data:'remark'}," 
                		+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,auxiliaryDeviceInfoHandsontableHelper);}}" 
                		+"]";
                auxiliaryDeviceInfoHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
                auxiliaryDeviceInfoHandsontableHelper.columns = Ext.JSON.decode(columns);
                auxiliaryDeviceInfoHandsontableHelper.createTable(result.totalRoot);
            } else {
                auxiliaryDeviceInfoHandsontableHelper.hot.loadData(result.totalRoot);
            }
            if(result.totalRoot.length==0){
            	Ext.getCmp("AuxiliaryDeviceSelectRow_Id").setValue('');
            	Ext.getCmp("AuxiliaryDeviceSelectEndRow_Id").setValue('');
            	
            	CreateAndLoadAuxiliaryDeviceDetailsTable(0,0);
            }else{
            	Ext.getCmp("AuxiliaryDeviceSelectRow_Id").setValue(0);
            	Ext.getCmp("AuxiliaryDeviceSelectEndRow_Id").setValue(0);
            	
            	var rowdata = auxiliaryDeviceInfoHandsontableHelper.hot.getDataAtRow(0);
            	CreateAndLoadAuxiliaryDeviceDetailsTable(rowdata[0],rowdata[1]);
            }
            Ext.getCmp("AuxiliaryDeviceTotalCount_Id").update({
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

var AuxiliaryDeviceInfoHandsontableHelper = {
    createNew: function (divid) {
        var auxiliaryDeviceInfoHandsontableHelper = {};
        auxiliaryDeviceInfoHandsontableHelper.hot = '';
        auxiliaryDeviceInfoHandsontableHelper.divid = divid;
        auxiliaryDeviceInfoHandsontableHelper.validresult = true; //数据校验
        auxiliaryDeviceInfoHandsontableHelper.colHeaders = [];
        auxiliaryDeviceInfoHandsontableHelper.columns = [];

        auxiliaryDeviceInfoHandsontableHelper.AllData = {};
        auxiliaryDeviceInfoHandsontableHelper.updatelist = [];
        auxiliaryDeviceInfoHandsontableHelper.delidslist = [];
        auxiliaryDeviceInfoHandsontableHelper.insertlist = [];
        auxiliaryDeviceInfoHandsontableHelper.editNameList = [];

        auxiliaryDeviceInfoHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.whiteSpace='nowrap'; //文本不换行
        	td.style.overflow='hidden';//超出部分隐藏
        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
        }

        auxiliaryDeviceInfoHandsontableHelper.createTable = function (data) {
            $('#' + auxiliaryDeviceInfoHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + auxiliaryDeviceInfoHandsontableHelper.divid);
            auxiliaryDeviceInfoHandsontableHelper.hot = new Handsontable(hotElement, {
            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            	data: data,
                hiddenColumns: {
                    columns: [0,1],
                    indicators: false
                },
                columns: auxiliaryDeviceInfoHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: true, //显示行头
                colHeaders: auxiliaryDeviceInfoHandsontableHelper.colHeaders, //显示列头
                columnSorting: true, //允许排序
                allowInsertRow:false,
                sortIndicator: true,
                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                //	                dropdownMenu: ['filter_by_condition', 'filter_by_value', 'filter_action_bar'],
                filters: true,
                renderAllRows: true,
                search: true,
//                outsideClickDeselects:false,
                cells: function (row, col, prop) {
                    var cellProperties = {};
                    var visualRowIndex = this.instance.toVisualRow(row);
                    var visualColIndex = this.instance.toVisualColumn(col);
                    
                    var AuxiliaryDeviceManagerModuleEditFlag=parseInt(Ext.getCmp("AuxiliaryDeviceManagerModuleEditFlag").getValue());
                    if(AuxiliaryDeviceManagerModuleEditFlag!=1){
                    	cellProperties.readOnly = true;
                    }
                    cellProperties.renderer = auxiliaryDeviceInfoHandsontableHelper.addCellStyle;
                    return cellProperties;
                },
                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
                	if(row<0 && row2<0){//只选中表头
                		Ext.getCmp("AuxiliaryDeviceSelectRow_Id").setValue('');
                    	Ext.getCmp("AuxiliaryDeviceSelectEndRow_Id").setValue('');
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
                    	
                    	var selectedRow=Ext.getCmp("AuxiliaryDeviceSelectRow_Id").getValue();
                    	if(selectedRow!=startRow){
                    		Ext.getCmp("AuxiliaryDeviceSelectRow_Id").setValue(startRow);
                        	Ext.getCmp("AuxiliaryDeviceSelectEndRow_Id").setValue(endRow);
                    		
                    		var row1=auxiliaryDeviceInfoHandsontableHelper.hot.getDataAtRow(startRow);
                        	var recordId=0;
                        	var specificType=0;
                        	if(isNotVal(row1[0])){
                        		recordId=row1[0];
                        		specificType=row1[1];
                        	}
                        	
                        	CreateAndLoadAuxiliaryDeviceDetailsTable(recordId,specificType);
                        	
                    	}else{
                    		Ext.getCmp("AuxiliaryDeviceSelectRow_Id").setValue(startRow);
                        	Ext.getCmp("AuxiliaryDeviceSelectEndRow_Id").setValue(endRow);
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
                            var rowdata = auxiliaryDeviceInfoHandsontableHelper.hot.getDataAtRow(i);
                            ids.push(rowdata[0]);
                        }
                        auxiliaryDeviceInfoHandsontableHelper.delExpressCount(ids);
                        auxiliaryDeviceInfoHandsontableHelper.screening();
                    }
                },
                afterChange: function (changes, source) {
                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
                    if (changes != null) {
                        for (var i = 0; i < changes.length; i++) {
                            var params = [];
                            var index = changes[i][0]; //行号码
                            var rowdata = auxiliaryDeviceInfoHandsontableHelper.hot.getDataAtRow(index);
                            params.push(rowdata[0]);
                            params.push(changes[i][1]);
                            params.push(changes[i][2]);
                            params.push(changes[i][3]);
                            if ("edit" == source && params[1] == "name") { //编辑井名单元格
                                var data = "{\"oldName\":\"" + params[2] + "\",\"newName\":\"" + params[3] + "\"}";
                                auxiliaryDeviceInfoHandsontableHelper.editNameList.push(Ext.JSON.decode(data));
                            }

                            //仅当单元格发生改变的时候,id!=null,说明是更新
                            if (params[2] != params[3] && params[0] != null && params[0] > 0) {
                                var data = "{";
                                for (var j = 0; j < auxiliaryDeviceInfoHandsontableHelper.columns.length; j++) {
                                    data += auxiliaryDeviceInfoHandsontableHelper.columns[j].data + ":'" + rowdata[j] + "'";
                                    if (j < auxiliaryDeviceInfoHandsontableHelper.columns.length - 1) {
                                        data += ","
                                    }
                                }
                                data += "}"
                                auxiliaryDeviceInfoHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
                            }
                        }
                    }
                },
                afterOnCellMouseOver: function(event, coords, TD){
                	if(auxiliaryDeviceInfoHandsontableHelper!=null&&auxiliaryDeviceInfoHandsontableHelper.hot!=''&&auxiliaryDeviceInfoHandsontableHelper.hot!=undefined && auxiliaryDeviceInfoHandsontableHelper.hot.getDataAtCell!=undefined){
                		var rawValue=auxiliaryDeviceInfoHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
        //插入的数据的获取
        auxiliaryDeviceInfoHandsontableHelper.insertExpressCount = function () {
            var idsdata = auxiliaryDeviceInfoHandsontableHelper.hot.getDataAtCol(0); //所有的id
            for (var i = 0; i < idsdata.length; i++) {
                //id=null时,是插入数据,此时的i正好是行号
                if (idsdata[i] == null || idsdata[i] < 0) {
                    //获得id=null时的所有数据封装进data
                    var rowdata = auxiliaryDeviceInfoHandsontableHelper.hot.getDataAtRow(i);
                    //var collength = hot.countCols();
                    if (rowdata != null) {
                        var data = "{";
                        for (var j = 0; j < auxiliaryDeviceInfoHandsontableHelper.columns.length; j++) {
                            data += auxiliaryDeviceInfoHandsontableHelper.columns[j].data + ":'" + rowdata[j] + "'";
                            if (j < auxiliaryDeviceInfoHandsontableHelper.columns.length - 1) {
                                data += ","
                            }
                        }
                        data += "}"
                        auxiliaryDeviceInfoHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
                    }
                }
            }
            if (auxiliaryDeviceInfoHandsontableHelper.insertlist.length != 0) {
                auxiliaryDeviceInfoHandsontableHelper.AllData.insertlist = auxiliaryDeviceInfoHandsontableHelper.insertlist;
            }
        }
        //保存数据
        auxiliaryDeviceInfoHandsontableHelper.saveData = function () {
        	var auxiliaryDeviceInfoHandsontableData=auxiliaryDeviceInfoHandsontableHelper.hot.getData();
        	if(auxiliaryDeviceInfoHandsontableData.length>0){
        		auxiliaryDeviceInfoHandsontableHelper.insertExpressCount();
        		var auxiliaryDeviceSpecificType=0;
        		if(Ext.getCmp("AuxiliaryDeviceSpecificType_Id")!=undefined){
        			auxiliaryDeviceSpecificType=Ext.getCmp("AuxiliaryDeviceSpecificType_Id").getValue().auxiliaryDeviceSpecificType;
        		}
                
                var DeviceSelectRow= Ext.getCmp("AuxiliaryDeviceSelectRow_Id").getValue();
                var rowdata = auxiliaryDeviceInfoHandsontableHelper.hot.getDataAtRow(DeviceSelectRow);
            	var deviceId=rowdata[0];
            	var manufacturer=rowdata[3];
            	var model=rowdata[4];
                
            	var auxiliaryDeviceDetailsSaveData={};
            	auxiliaryDeviceDetailsSaveData.deviceId=deviceId;
            	auxiliaryDeviceDetailsSaveData.auxiliaryDeviceSpecificType=auxiliaryDeviceSpecificType;
            	auxiliaryDeviceDetailsSaveData.auxiliaryDeviceDetailsList=[];
            	

        		if(auxiliaryDeviceDetailsHandsontableHelper!=null && auxiliaryDeviceDetailsHandsontableHelper.hot!=undefined){
            		var auxiliaryDeviceDetailsData=auxiliaryDeviceDetailsHandsontableHelper.hot.getData();
                	Ext.Array.each(auxiliaryDeviceDetailsData, function (name, index, countriesItSelf) {
                        if (isNotVal(auxiliaryDeviceDetailsData[index][1])) {
                        	var auxiliaryDeviceDetails={};
                        	auxiliaryDeviceDetails.deviceId=deviceId;
                        	
                        	var itemName=auxiliaryDeviceDetailsData[index][1];
                        	var itemValue=isNotVal(auxiliaryDeviceDetailsData[index][2])?auxiliaryDeviceDetailsData[index][2]:"";
                        	var itemUnit=isNotVal(auxiliaryDeviceDetailsData[index][3])?auxiliaryDeviceDetailsData[index][3]:"";
                        	if(auxiliaryDeviceSpecificType==1 && itemName=='旋转方向'){
                        		if(itemValue=='顺时针'){
                        			itemValue='Clockwise';
                        		}else if(itemValue=='逆时针'){
                        			itemValue='Anticlockwise';
                        		}else{
                        			itemValue='';
                        		}
                        	}
                        	auxiliaryDeviceDetails.itemName=itemName;
                        	auxiliaryDeviceDetails.itemValue=itemValue;
                        	auxiliaryDeviceDetails.itemUnit=itemUnit;
                        	auxiliaryDeviceDetailsSaveData.auxiliaryDeviceDetailsList.push(auxiliaryDeviceDetails);
                        }
                    });
            	}
                Ext.Ajax.request({
                    method: 'POST',
                    url: context + '/wellInformationManagerController/saveAuxiliaryDeviceHandsontableData',
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
                        	auxiliaryDeviceInfoHandsontableHelper.clearContainer();
                            CreateAndLoadAuxiliaryDeviceInfoTable();
                        } else {
                            Ext.MessageBox.alert("信息", "数据保存失败");
                        }
                    },
                    failure: function () {
                        Ext.MessageBox.alert("信息", "请求失败");
                        auxiliaryDeviceInfoHandsontableHelper.clearContainer();
                    },
                    params: {
                    	deviceId:deviceId,
                    	auxiliaryDeviceSpecificType:auxiliaryDeviceSpecificType,
                    	data: JSON.stringify(auxiliaryDeviceInfoHandsontableHelper.AllData),
                    	deviceType: getDeviceTypeFromTabId("AuxiliaryDeviceManagerTabPanel"),
                    	auxiliaryDeviceDetailsSaveData: JSON.stringify(auxiliaryDeviceDetailsSaveData)
                    }
                });
        	}else{
        		Ext.MessageBox.alert("信息", "无记录保存！");
            }
        }

        //修改设备名称
        auxiliaryDeviceInfoHandsontableHelper.editWellName = function () {
            if (auxiliaryDeviceInfoHandsontableHelper.editNameList.length > 0 && auxiliaryDeviceInfoHandsontableHelper.validresult) {
                Ext.Ajax.request({
                    method: 'POST',
                    url: context + '/wellInformationManagerController/editAuxiliaryDeviceName',
                    success: function (response) {
                        rdata = Ext.JSON.decode(response.responseText);
                        if (rdata.success) {
                            Ext.MessageBox.alert("信息", "保存成功");
                            auxiliaryDeviceInfoHandsontableHelper.clearContainer();
                            CreateAndLoadAuxiliaryDeviceInfoTable();
                        } else {
                            Ext.MessageBox.alert("信息", "数据保存失败");
                        }
                    },
                    failure: function () {
                        Ext.MessageBox.alert("信息", "请求失败");
                        auxiliaryDeviceInfoHandsontableHelper.clearContainer();
                    },
                    params: {
                        data: JSON.stringify(auxiliaryDeviceInfoHandsontableHelper.editNameList)
                    }
                });
            } else {
                if (!auxiliaryDeviceInfoHandsontableHelper.validresult) {
                    Ext.MessageBox.alert("信息", "数据类型错误");
                } else {
                    Ext.MessageBox.alert("信息", "无数据变化");
                }
            }
        }


        //删除的优先级最高
        auxiliaryDeviceInfoHandsontableHelper.delExpressCount = function (ids) {
            //传入的ids.length不可能为0
            $.each(ids, function (index, id) {
                if (id != null) {
                    auxiliaryDeviceInfoHandsontableHelper.delidslist.push(id);
                }
            });
            auxiliaryDeviceInfoHandsontableHelper.AllData.delidslist = auxiliaryDeviceInfoHandsontableHelper.delidslist;
        }

        //updatelist数据更新
        auxiliaryDeviceInfoHandsontableHelper.screening = function () {
            if (auxiliaryDeviceInfoHandsontableHelper.updatelist.length != 0 && auxiliaryDeviceInfoHandsontableHelper.delidslist.lentgh != 0) {
                for (var i = 0; i < auxiliaryDeviceInfoHandsontableHelper.delidslist.length; i++) {
                    for (var j = 0; j < auxiliaryDeviceInfoHandsontableHelper.updatelist.length; j++) {
                        if (auxiliaryDeviceInfoHandsontableHelper.updatelist[j].id == auxiliaryDeviceInfoHandsontableHelper.delidslist[i]) {
                            //更新updatelist
                            auxiliaryDeviceInfoHandsontableHelper.updatelist.splice(j, 1);
                        }
                    }
                }
                //把updatelist封装进AllData
                auxiliaryDeviceInfoHandsontableHelper.AllData.updatelist = auxiliaryDeviceInfoHandsontableHelper.updatelist;
            }
        }

        //更新数据
        auxiliaryDeviceInfoHandsontableHelper.updateExpressCount = function (data) {
            if (JSON.stringify(data) != "{}") {
                var flag = true;
                //判断记录是否存在,更新数据     
                $.each(auxiliaryDeviceInfoHandsontableHelper.updatelist, function (index, node) {
                    if (node.id == data.id) {
                        //此记录已经有了
                        flag = false;
                        //用新得到的记录替换原来的,不用新增
                        auxiliaryDeviceInfoHandsontableHelper.updatelist[index] = data;
                    }
                });
                flag && auxiliaryDeviceInfoHandsontableHelper.updatelist.push(data);
                //封装
                auxiliaryDeviceInfoHandsontableHelper.AllData.updatelist = auxiliaryDeviceInfoHandsontableHelper.updatelist;
            }
        }

        auxiliaryDeviceInfoHandsontableHelper.clearContainer = function () {
            auxiliaryDeviceInfoHandsontableHelper.AllData = {};
            auxiliaryDeviceInfoHandsontableHelper.updatelist = [];
            auxiliaryDeviceInfoHandsontableHelper.delidslist = [];
            auxiliaryDeviceInfoHandsontableHelper.insertlist = [];
            auxiliaryDeviceInfoHandsontableHelper.editNameList = [];
        }

        return auxiliaryDeviceInfoHandsontableHelper;
    }
};

function CreateAndLoadAuxiliaryDeviceDetailsTable(deviceId,specificType){
	var auxiliaryDeviceSpecificType=0;
	if(Ext.getCmp("AuxiliaryDeviceSpecificType_Id")!=undefined){
		auxiliaryDeviceSpecificType=Ext.getCmp("AuxiliaryDeviceSpecificType_Id").getValue().auxiliaryDeviceSpecificType;
	}
	if(specificType!=auxiliaryDeviceSpecificType){
		if(Ext.getCmp("AuxiliaryDeviceSpecificType_Id")!=undefined){
			Ext.getCmp('AuxiliaryDeviceSpecificType_Id').setValue({auxiliaryDeviceSpecificType:specificType});
		}
	}else{
		CreateAuxiliaryDeviceDetailsTable(deviceId,specificType);
	}
}

function CreateAuxiliaryDeviceDetailsTable(deviceId,specificType){
	if(auxiliaryDeviceDetailsHandsontableHelper!=null){
		if(auxiliaryDeviceDetailsHandsontableHelper.hot!=undefined){
			auxiliaryDeviceDetailsHandsontableHelper.hot.destroy();
		}
		auxiliaryDeviceDetailsHandsontableHelper=null;
	}
	
	var auxiliaryDeviceSpecificType=0;
	if(Ext.getCmp("AuxiliaryDeviceSpecificType_Id")!=undefined){
		auxiliaryDeviceSpecificType=Ext.getCmp("AuxiliaryDeviceSpecificType_Id").getValue().auxiliaryDeviceSpecificType;
	}

	Ext.Ajax.request({
		method:'POST',
		url:context + '/wellInformationManagerController/getAuxiliaryDeviceDetailsInfo',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(auxiliaryDeviceDetailsHandsontableHelper==null || auxiliaryDeviceDetailsHandsontableHelper.hot==undefined){
				auxiliaryDeviceDetailsHandsontableHelper = AuxiliaryDeviceDetailsHandsontableHelper.createNew("AuxiliaryDeviceDetailsTableDiv_id");
				var colHeaders="['序号','名称','变量','单位']";
				var columns="[{data:'id'},{data:'itemName'},{data:'itemValue'},{data:'itemUnit'}]";
				
				auxiliaryDeviceDetailsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				auxiliaryDeviceDetailsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					auxiliaryDeviceDetailsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					auxiliaryDeviceDetailsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					auxiliaryDeviceDetailsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					auxiliaryDeviceDetailsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			deviceId:deviceId,
			auxiliaryDeviceSpecificType: auxiliaryDeviceSpecificType
        }
	});
};

var AuxiliaryDeviceDetailsHandsontableHelper = {
	    createNew: function (divid) {
	        var auxiliaryDeviceDetailsHandsontableHelper = {};
	        auxiliaryDeviceDetailsHandsontableHelper.hot = '';
	        auxiliaryDeviceDetailsHandsontableHelper.divid = divid;
	        auxiliaryDeviceDetailsHandsontableHelper.colHeaders = [];
	        auxiliaryDeviceDetailsHandsontableHelper.columns = [];
	        auxiliaryDeviceDetailsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	        }

	        auxiliaryDeviceDetailsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        auxiliaryDeviceDetailsHandsontableHelper.createTable = function (data) {
	            $('#' + auxiliaryDeviceDetailsHandsontableHelper.divid).empty();
	            var hotElement = document.querySelector('#' + auxiliaryDeviceDetailsHandsontableHelper.divid);
	            auxiliaryDeviceDetailsHandsontableHelper.hot = new Handsontable(hotElement, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: data,
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: false
	                },
	                columns: auxiliaryDeviceDetailsHandsontableHelper.columns,
	                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true, //显示行头
	                colHeaders: auxiliaryDeviceDetailsHandsontableHelper.colHeaders, //显示列头
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
	                    var AuxiliaryDeviceManagerModuleEditFlag=parseInt(Ext.getCmp("AuxiliaryDeviceManagerModuleEditFlag").getValue());
	                    if(AuxiliaryDeviceManagerModuleEditFlag!=1){
	                    	cellProperties.readOnly = true;
	                    }else{
	                    	var auxiliaryDeviceSpecificType=0;
	                    	if(Ext.getCmp("AuxiliaryDeviceSpecificType_Id")!=undefined){
	                    		auxiliaryDeviceSpecificType=Ext.getCmp("AuxiliaryDeviceSpecificType_Id").getValue().auxiliaryDeviceSpecificType;
	                    	}
	                    	if(auxiliaryDeviceSpecificType==1){
	                    		if(visualColIndex!=2){
	                    			cellProperties.readOnly = true;
	                    			cellProperties.renderer = auxiliaryDeviceDetailsHandsontableHelper.addBoldBg;
	                    		}
	                    		
	                    		if (visualColIndex === 2 && visualRowIndex===1) {
			                    	this.type = 'dropdown';
			                    	this.source = ['顺时针','逆时针'];
			                    	this.strict = true;
			                    	this.allowInvalid = false;
			                    }
	                    	}
	                    }
	                    return cellProperties;
	                }
	            });
	        }
	        return auxiliaryDeviceDetailsHandsontableHelper;
	    }
	};