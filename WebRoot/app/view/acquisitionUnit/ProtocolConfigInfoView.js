var protocolConfigHandsontableHelper=null;
var protocolConfigItemsHandsontableHelper=null;
var kafkaProtocolConfigHandsontableHelper=null;

var acquisitionUnitConfigHandsontableHelper=null;
var acquisitionGroupConfigHandsontableHelper=null;

Ext.define('AP.view.acquisitionUnit.ProtocolConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.protocolConfigInfoView',
    layout: "fit",
    id:'protocolConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	Ext.apply(me, {
    		items: [{
                tbar:['->',{
                	xtype: 'button',
        			pressed: true,
        			text: cosog.string.save,
        			iconCls: 'save',
        			handler: function (v, o) {
        				var tabPanel = Ext.getCmp("ScadaDriverConfigTabPanel_Id");
        				var activeId = tabPanel.getActiveTab().id;
        				if(activeId=="ScadaDriverModbusConfigTabPanel_Id"){
        					SaveModbusProtocolConfigData();
        				}else if(activeId=="ScadaDriverKafkaConfigTabPanel_Id"){
        					SaveScadaKafkaDriverConfigData();
        				}
        			}
                },{
                    id: 'ScadaProtocolModbusConfigSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    id: 'selectedAcquisitionUnitId_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                },{
                    id: 'selectedAcquisitionGroupCode_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }],
                xtype: 'tabpanel',
                id:"ScadaDriverConfigTabPanel_Id",
                activeTab: 0,
                border: false,
                tabPosition: 'top',
                items: [{
                	title:'Modbus',
                	id:"ScadaDriverModbusConfigTabPanel_Id",
                	layout: "border",
                    border: false,
                    items: [{
                    	region: 'center',
                    	layout: "border",
                        border: false,
                        items: [{
                            region: 'center',
                            title:'协议配置',
                            border: false,
                            layout: 'fit',
                            html:'<div class="DriverConfigInfoContainer" style="width:100%;height:100%;"><div class="con" id="DriverConfigInfoInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	CreateProtocolConfigInfoTable(true);
                                }
                            }
                        }, {
                            region: 'east',
                            title:'采控项配置',
                            layout: 'fit',
                            width: '60%',
                            collapsible: true,
                            split: true,
//                            bbar: ['->', {
//                                xtype: 'button',
//                                text: '保存',
//                                iconCls: 'save',
//                                pressed: true,
//                                handler: function () {
//                                	grantAcquisitionItemsPermission();
//                                }
//                    		}, {
//                                xtype: 'tbspacer',
//                                flex: 1
//                    		}],
                            html:'<div class="DriverItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="DriverItemsConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                }
                            }
                        }]
                    },{
                    	region: 'south',
                    	height:'50%',
                    	layout: "border",
                        border: false,
                        header: false,
                        collapsible: true,
                        split: true,
                        items: [{
                            region: 'center',
                            title:'采集单元配置',
                            border: false,
                            layout: 'fit',
                            html:'<div class="AcquisitionUnitConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="AcquisitionUnitConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                }
                            }
                        }, {
                            region: 'east',
                            title:'采集组配置',
                            layout: 'fit',
                            width: '60%',
                            collapsible: true,
                            split: true,
                            html:'<div class="AcquisitionGroupConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="AcquisitionGroupConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                }
                            }
                        }]
                    }]
                },{
                	title:'Kafka',
                	id:"ScadaDriverKafkaConfigTabPanel_Id",
                	layout: 'fit',
                    html:'<div class="ScadaKafkaConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ScadaKafkaConfigTableInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        }
                    }
                }],
                listeners: {
                    tabchange: function (tabPanel, newCard, oldCard, obj) {
                    	if(newCard.id=="ScadaDriverModbusConfigTabPanel_Id"){
//                    		loadFSDiagramAnalysisSingleStatData();
                    	}else if(newCard.id=="ScadaDriverKafkaConfigTabPanel_Id"){
                    		CreateKafkaConfigInfoTable();
                    	}
                    }
                }
    		}],
    		listeners: {
    			beforeclose: function ( panel, eOpts) {},
    			afterrender: function ( panel, eOpts) {}
    		}
        });
        this.callParent(arguments);
    }
});

function CreateProtocolConfigInfoTable(isNew){
	if(isNew&&protocolConfigHandsontableHelper!=null){
        protocolConfigHandsontableHelper.clearContainer();
        protocolConfigHandsontableHelper.hot.destroy();
        protocolConfigHandsontableHelper=null;
	}
	if(isNew&&protocolConfigItemsHandsontableHelper!=null){
		protocolConfigItemsHandsontableHelper.hot.destroy();
		protocolConfigItemsHandsontableHelper=null;
	}
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolConfigData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolConfigHandsontableHelper==null){
				protocolConfigHandsontableHelper = ProtocolConfigHandsontableHelper.createNew("DriverConfigInfoInfoDiv_id");
				var colHeaders="[";
		        var columns="[";
		       
	            for(var i=0;i<result.columns.length;i++){
	            	colHeaders+="'"+result.columns[i].header+"'";
	            	if(result.columns[i].dataIndex.toUpperCase()==="ProtocolType".toUpperCase()){
	            		columns+="{data:'"+result.columns[i].dataIndex+"',type:'dropdown',strict:true,allowInvalid:false,source:['modbus-tcp', 'modbus-rtu','modbus-rtu拓展(主动上传)']}";
	            	}else if(result.columns[i].dataIndex.toUpperCase()==="StoreMode".toUpperCase()){
	            		columns+="{data:'"+result.columns[i].dataIndex+"',type:'dropdown',strict:true,allowInvalid:false,source:['大端', '小端']}";
	            	}else if(result.columns[i].dataIndex.toUpperCase()==="SignInPrefix".toUpperCase() || result.columns[i].dataIndex.toUpperCase()==="SignInSuffix".toUpperCase() || result.columns[i].dataIndex.toUpperCase()==="HeartbeatPrefix".toUpperCase()){
	            		columns+="{data:'"+result.columns[i].dataIndex+"',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_NotNull(val, callback,this.row, this.col,protocolConfigHandsontableHelper);}}";
	            	}else{
	            		columns+="{data:'"+result.columns[i].dataIndex+"'}";
	            	}
	            	if(i<result.columns.length-1){
	            		colHeaders+=",";
	                	columns+=",";
	            	}
	            }
	            colHeaders+="]";
	            
	            columns+=",{data:'dataConfig'}";
	            
	        	columns+="]";
	        	protocolConfigHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
	        	protocolConfigHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolConfigHandsontableHelper.createTable(result.totalRoot);
			}else{
				protocolConfigHandsontableHelper.hot.loadData(result.totalRoot);
			}
			
			var row1=protocolConfigHandsontableHelper.hot.getDataAtRow(0);
			CreateDriverConfigItemsInfoTable(row1[8]);
			CreateAcquisitionUnitConfigInfoTable(true);
			CreateAcquisitionGroupConfigInfoTable(true);
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
            
        }
	});
};

var ProtocolConfigHandsontableHelper = {
	    createNew: function (divid) {
	        var protocolConfigHandsontableHelper = {};
	        protocolConfigHandsontableHelper.hot = '';
	        protocolConfigHandsontableHelper.divid = divid;
	        protocolConfigHandsontableHelper.validresult=true;//数据校验
	        protocolConfigHandsontableHelper.colHeaders=[];
	        protocolConfigHandsontableHelper.columns=[];
	        
	        protocolConfigHandsontableHelper.AllData={};
	        protocolConfigHandsontableHelper.updatelist=[];
	        protocolConfigHandsontableHelper.delidslist=[];
	        protocolConfigHandsontableHelper.insertlist=[];
	        
	        protocolConfigHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        protocolConfigHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(184, 184, 184)';
	        }
	        
	        protocolConfigHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolConfigHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolConfigHandsontableHelper.divid);
	        	protocolConfigHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	                hiddenColumns: {
	                    columns: [0,8],
	                    indicators: true
	                },
	                colWidths: [80,80,170,120,120,120,120,120],
	                columns:protocolConfigHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:protocolConfigHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
//	                dropdownMenu: ['filter_by_condition', 'filter_by_value', 'filter_action_bar'],
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    if (visualColIndex ==1) {
							cellProperties.readOnly = true;
							cellProperties.renderer = protocolConfigHandsontableHelper.addBoldBg;
		                }
	                    return cellProperties;
	                },
	                afterSelectionEnd: function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	Ext.getCmp("ScadaProtocolModbusConfigSelectRow_Id").setValue(row);
	                	var row1=protocolConfigHandsontableHelper.hot.getDataAtRow(row);
	                	CreateDriverConfigItemsInfoTable(row1[8]);
	                	CreateAcquisitionUnitConfigInfoTable();
	                	CreateAcquisitionGroupConfigInfoTable();
	                },
	                afterDestroy: function() {
	                    // 移除事件
//	                    Handsontable.Dom.removeEvent(save, 'click', saveData);
//	                    loadDataTable();
	                },
	                beforeRemoveRow: function (index, amount) {
	                    var ids = [];
	                    if (amount != 0) {
	                        for (var i = index; i < amount + index; i++) {
	                            var rowdata = protocolConfigHandsontableHelper.hot.getDataAtRow(i);
	                            ids.push(rowdata[0]);
	                        }
	                        protocolConfigHandsontableHelper.delExpressCount(ids);
	                        protocolConfigHandsontableHelper.screening();
	                    }
	                },
	                afterChange: function (changes, source) {
	                    if (changes != null) {
	                    	for(var i=0;i<changes.length;i++){
	                    		var params = [];
	                    		var index = changes[i][0]; //行号码
		                        var rowdata = protocolConfigHandsontableHelper.hot.getDataAtRow(index);
		                        params.push(rowdata[0]);
		                        params.push(changes[i][1]);
		                        params.push(changes[i][2]);
		                        params.push(changes[i][3]);
		                        
		                        if("edit"==source&&params[1]=="wellName"){//编辑井号单元格
		                        	var data="{\"oldWellName\":\""+params[2]+"\",\"newWellName\":\""+params[3]+"\"}";
		                        	protocolConfigHandsontableHelper.editWellNameList.push(Ext.JSON.decode(data));
		                        }

		                        //仅当单元格发生改变的时候,id!=null,说明是更新
		                        if (params[2] != params[3] && params[0] != null && params[0] >0) {
		                        	var data="{";
		                        	for(var j=0;j<protocolConfigHandsontableHelper.columns.length;j++){
		                        		data+=protocolConfigHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
		                        		if(j<protocolConfigHandsontableHelper.columns.length-1){
		                        			data+=","
		                        		}
		                        	}
		                        	data+="}"
		                            protocolConfigHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
		                        }
	                    	}
	                        
	                    }
	                }
	        	});
	        }
	      //插入的数据的获取
	        protocolConfigHandsontableHelper.insertExpressCount=function() {
	            var idsdata = protocolConfigHandsontableHelper.hot.getDataAtCol(0); //所有的id
	            for (var i = 0; i < idsdata.length; i++) {
	                //id=null时,是插入数据,此时的i正好是行号
	                if (idsdata[i] == null||idsdata[i]<0) {
	                    //获得id=null时的所有数据封装进data
	                    var rowdata = protocolConfigHandsontableHelper.hot.getDataAtRow(i);
	                    //var collength = hot.countCols();
	                    if (rowdata != null) {
	                    	var data="{";
                        	for(var j=0;j<protocolConfigHandsontableHelper.columns.length;j++){
                        		data+=protocolConfigHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
                        		if(j<protocolConfigHandsontableHelper.columns.length-1){
                        			data+=","
                        		}
                        	}
                        	data+="}"
	                        protocolConfigHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
	                    }
	                }
	            }
	            if (protocolConfigHandsontableHelper.insertlist.length != 0) {
	            	protocolConfigHandsontableHelper.AllData.insertlist = protocolConfigHandsontableHelper.insertlist;
	            }
	        }
	        //保存数据
	        protocolConfigHandsontableHelper.saveData = function () {
	        	
	        	var IframeViewSelection  = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
	        	if(IframeViewSelection.length>0&&IframeViewSelection[0].isLeaf()){
	        		//插入的数据的获取
		        	protocolConfigHandsontableHelper.insertExpressCount();
		        	var orgId=IframeViewSelection[0].data.orgId;
		            if (JSON.stringify(protocolConfigHandsontableHelper.AllData) != "{}" && protocolConfigHandsontableHelper.validresult) {
		            	Ext.Ajax.request({
		            		method:'POST',
		            		url:context + '/acquisitionUnitManagerController/saveWellHandsontableData',
		            		success:function(response) {
		            			rdata=Ext.JSON.decode(response.responseText);
		            			if (rdata.success) {
		                        	Ext.MessageBox.alert("信息","保存成功");
		                            //保存以后重置全局容器
		                            protocolConfigHandsontableHelper.clearContainer();
//		                            CreateProtocolConfigInfoTable();
		                        } else {
		                        	Ext.MessageBox.alert("信息","数据保存失败");

		                        }
		            		},
		            		failure:function(){
		            			Ext.MessageBox.alert("信息","请求失败");
		                        protocolConfigHandsontableHelper.clearContainer();
		            		},
		            		params: {
		                    	data: JSON.stringify(protocolConfigHandsontableHelper.AllData),
		                    	orgId:orgId
		                    }
		            	}); 
		            } else {
		                if (!protocolConfigHandsontableHelper.validresult) {
		                	Ext.MessageBox.alert("信息","数据类型错误");
		                } else {
		                	Ext.MessageBox.alert("信息","无数据变化");
		                }
		            }
	        	}else{
	        		Ext.MessageBox.alert("信息","请先选择组织节点");
	        	}
	            
	        }
	        
	      //删除的优先级最高
	        protocolConfigHandsontableHelper.delExpressCount=function(ids) {
	            //传入的ids.length不可能为0
	            $.each(ids, function (index, id) {
	                if (id != null) {
	                	protocolConfigHandsontableHelper.delidslist.push(id);
	                }
	            });
	            protocolConfigHandsontableHelper.AllData.delidslist = protocolConfigHandsontableHelper.delidslist;
	        }

	        //updatelist数据更新
	        protocolConfigHandsontableHelper.screening=function() {
	            if (protocolConfigHandsontableHelper.updatelist.length != 0 && protocolConfigHandsontableHelper.delidslist.lentgh != 0) {
	                for (var i = 0; i < protocolConfigHandsontableHelper.delidslist.length; i++) {
	                    for (var j = 0; j < protocolConfigHandsontableHelper.updatelist.length; j++) {
	                        if (protocolConfigHandsontableHelper.updatelist[j].id == protocolConfigHandsontableHelper.delidslist[i]) {
	                            //更新updatelist
	                        	protocolConfigHandsontableHelper.updatelist.splice(j, 1);
	                        }
	                    }
	                }
	                //把updatelist封装进AllData
	                protocolConfigHandsontableHelper.AllData.updatelist = protocolConfigHandsontableHelper.updatelist;
	            }
	        }
	        
	      //更新数据
	        protocolConfigHandsontableHelper.updateExpressCount=function(data) {
	            if (JSON.stringify(data) != "{}") {
	                var flag = true;
	                //判断记录是否存在,更新数据     
	                $.each(protocolConfigHandsontableHelper.updatelist, function (index, node) {
	                    if (node.id == data.id) {
	                        //此记录已经有了
	                        flag = false;
	                        //用新得到的记录替换原来的,不用新增
	                        protocolConfigHandsontableHelper.updatelist[index] = data;
	                    }
	                });
	                flag && protocolConfigHandsontableHelper.updatelist.push(data);
	                //封装
	                protocolConfigHandsontableHelper.AllData.updatelist = protocolConfigHandsontableHelper.updatelist;
	            }
	        }
	        
	        protocolConfigHandsontableHelper.clearContainer = function () {
	        	protocolConfigHandsontableHelper.AllData = {};
	        	protocolConfigHandsontableHelper.updatelist = [];
	        	protocolConfigHandsontableHelper.delidslist = [];
	        	protocolConfigHandsontableHelper.insertlist = [];
	        	protocolConfigHandsontableHelper.editWellNameList=[];
	        }
	        
	        return protocolConfigHandsontableHelper;
	    }
};

function CreateDriverConfigItemsInfoTable(data){
	protocolConfigItemsHandsontableHelper = ProtocolConfigItemsHandsontableHelper.createNew("DriverItemsConfigTableInfoDiv_id");
	var colHeaders="['','序号','名称','地址','寄存器数量','源数据类型','目的数据类型','读写类型','单位','单位换算系数','模式']";
	var columns="[{data:'checked',type:'checkbox'},{data:'id'},{data:'item'},"
		 	+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolConfigItemsHandsontableHelper);}},"
			+"{data:'quantity',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolConfigItemsHandsontableHelper);}}," 
			+"{data:'srcDataType',type:'dropdown',strict:true,allowInvalid:false,source:['int16','uint16','float32','bcd','asc']}," 
			+"{data:'destDataType',type:'dropdown',strict:true,allowInvalid:false,source:['int','uint','float32','float64','string','bcd','asc']}," 
			+"{data:'RWType',type:'dropdown',strict:true,allowInvalid:false,source:['只读', '读写']}," 
			+"{data:'unit'}," 
			+"{data:'ratio',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolConfigItemsHandsontableHelper);}}," 
			+"{data:'acqMode',type:'dropdown',strict:true,allowInvalid:false,source:['主动上传', '被动响应']}" 
			+"]";
	protocolConfigItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
	protocolConfigItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
	if(data==undefined||data==null||data.length==0){
		protocolConfigItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
	}else{
		protocolConfigItemsHandsontableHelper.createTable(data);
	}
	
//	$('#'+protocolConfigItemsHandsontableHelper.divid).on('mouseup', 'input.checker', function (event) { 
//	    var current = !$('input.checker').is(':checked'); //returns boolean 
//	     for (var i = 0; i < data.length; i++) { 
//	    	 protocolConfigItemsHandsontableHelper.hot.setDataAtCell(i, 0, current);
//	     }
//	});
};


var ProtocolConfigItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolConfigItemsHandsontableHelper = {};
	        protocolConfigItemsHandsontableHelper.hot1 = '';
	        protocolConfigItemsHandsontableHelper.divid = divid;
	        protocolConfigItemsHandsontableHelper.validresult=true;//数据校验
	        protocolConfigItemsHandsontableHelper.colHeaders=[];
	        protocolConfigItemsHandsontableHelper.columns=[];
	        protocolConfigItemsHandsontableHelper.AllData=[];
	        
	        protocolConfigItemsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        protocolConfigItemsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(184, 184, 184)';
	        }
	        
	        protocolConfigItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolConfigItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolConfigItemsHandsontableHelper.divid);
	        	protocolConfigItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	        		colWidths: [25,50,100,80,80,80,80,80,80,80,80],
//	                hiddenColumns: {
//	                    columns: [0],
//	                    indicators: true
//	                },
	                columns:protocolConfigItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolConfigItemsHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    if (visualColIndex ==1 || visualColIndex ==2) {
							cellProperties.readOnly = true;
							cellProperties.renderer = protocolConfigItemsHandsontableHelper.addBoldBg;
		                }
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	var row1=protocolConfigItemsHandsontableHelper.hot.getDataAtRow(row);
	                	if(row1[0]){
	                		protocolConfigItemsHandsontableHelper.hot.setDataAtCell(row, 0, false);
	                	}else{
	                		protocolConfigItemsHandsontableHelper.hot.setDataAtCell(row, 0, true);
	                	}
	                }
//	        		,colHeaders: function (col) { 
//	                    switch (col) { 
//	                     case 0: 
//	                      var txt = "<input type='checkbox' class='checker' "; 
//	                      txt += isChecked(data) ? 'checked="checked"' : ''; 
//	                      txt += "> 全选"; 
//	                      return txt; 
//	                     default:
//	                    	 return protocolConfigItemsHandsontableHelper.colHeaders[col]; 
//	                    } 
//	                 }
	        	});
	        }
	        //保存数据
	        protocolConfigItemsHandsontableHelper.saveData = function () {}
	        protocolConfigItemsHandsontableHelper.clearContainer = function () {
	        	protocolConfigItemsHandsontableHelper.AllData = [];
	        }
	        return protocolConfigItemsHandsontableHelper;
	    }
};

//function isChecked(data) { 
//    for (var i = 0; i < data.length; i++) { 
//     if (!data.checked) { 
//      return false; 
//     } 
//    } 
//    return true; 
//} 

function CreateKafkaConfigInfoTable(){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getKafkaDriverConfigData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			
			
			if(kafkaProtocolConfigHandsontableHelper==null){
				CreateKafkaConfigItemsInfoTable(result);
			}else{
				kafkaProtocolConfigHandsontableHelper.hot.loadData(result.totalRoot);
			}
			
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
            
        }
	});
};

function CreateKafkaConfigItemsInfoTable(result){
	var columns="[";
	for(var i=0;i<result.columns.length;i++){
		if(result.columns[i].dataIndex==="columnType"){
			columns+="{data:'"+result.columns[i].dataIndex+"',type:'dropdown',strict:true,allowInvalid:false,source:['date','varchar2','number']}";
    	}else if(result.columns[i].dataIndex==="columnName"){
    		columns+="{data:'"+result.columns[i].dataIndex+"',type:'numeric',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_NotNull(val, callback,this.row, this.col,kafkaProtocolConfigHandsontableHelper);}}";
        }else if(result.columns[i].dataIndex==="checked"){
    		columns+="{data:'"+result.columns[i].dataIndex+"',type:'checkbox'}";
    	}else{
    		columns+="{data:'"+result.columns[i].dataIndex+"'}";
    	}
		if(i<result.columns.length-1){
        	columns+=",";
    	}
	}
	columns+="]";
	kafkaProtocolConfigHandsontableHelper = KafkaProtocolConfigHandsontableHelper.createNew("ScadaKafkaConfigTableInfoDiv_id","ScadaKafkaConfigTableInfoContainer");
	kafkaProtocolConfigHandsontableHelper.getData(result);
	kafkaProtocolConfigHandsontableHelper.columns=Ext.JSON.decode(columns);
	kafkaProtocolConfigHandsontableHelper.createTable();
};


var KafkaProtocolConfigHandsontableHelper = {
	    createNew: function (divid, containerid) {
	        var kafkaProtocolConfigHandsontableHelper = {};
	        kafkaProtocolConfigHandsontableHelper.get_data = {};
	        kafkaProtocolConfigHandsontableHelper.hot = '';
	        kafkaProtocolConfigHandsontableHelper.container = document.getElementById(divid);
	        kafkaProtocolConfigHandsontableHelper.last_index = 0;
	        kafkaProtocolConfigHandsontableHelper.calculation_type_computer = [];
	        kafkaProtocolConfigHandsontableHelper.calculation_type_not_computer = [];
	        kafkaProtocolConfigHandsontableHelper.editable = 0;
	        kafkaProtocolConfigHandsontableHelper.sum = 0;
	        kafkaProtocolConfigHandsontableHelper.editRecords = [];
	        kafkaProtocolConfigHandsontableHelper.columns=[];
	        kafkaProtocolConfigHandsontableHelper.my_data = [

	        ];
	        kafkaProtocolConfigHandsontableHelper.updateArray = function () {
	            for (var i = 0; i < kafkaProtocolConfigHandsontableHelper.sum; i++) {
	                kafkaProtocolConfigHandsontableHelper.my_data.splice(i+1, 0, ['', '', '']);
	            }
	        }
	        kafkaProtocolConfigHandsontableHelper.clearArray = function () {
	            kafkaProtocolConfigHandsontableHelper.hot.loadData(kafkaProtocolConfigHandsontableHelper.table_header);

	        }

	        kafkaProtocolConfigHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
                td.style.fontWeight = 'bold';
//				td.style.fontSize = '13px';
				td.style.color = 'rgb(0, 0, 51)';
				td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	        }
			
			kafkaProtocolConfigHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
				Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.fontWeight = 'bold';
		        td.style.fontSize = '13px';
		        td.style.fontFamily = 'SimSun';
//		        td.style.height = '50px';  
	        }
			
			kafkaProtocolConfigHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
	             td.style.fontWeight = 'bold';
		         td.style.fontSize = '5px';
		         td.style.fontFamily = 'SimHei';
	        }
			

	        kafkaProtocolConfigHandsontableHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        kafkaProtocolConfigHandsontableHelper.addContentReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(184, 184, 184)';
	        }
	        
	        kafkaProtocolConfigHandsontableHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        kafkaProtocolConfigHandsontableHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        kafkaProtocolConfigHandsontableHelper.createTable = function () {
//	            kafkaProtocolConfigHandsontableHelper.container.innerHTML = "";
	            kafkaProtocolConfigHandsontableHelper.hot = new Handsontable(kafkaProtocolConfigHandsontableHelper.container, {
	                data: kafkaProtocolConfigHandsontableHelper.my_data,
//	                fixedRowsTop:0, //固定顶部多少行不能垂直滚动
//	                fixedRowsBottom: 0,//固定底部多少行不能垂直滚动
//	                fixedColumnsLeft:0, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
//					rowHeights: [50],
					colWidths:[1,6,10],
					stretchH: 'all',
					columns:kafkaProtocolConfigHandsontableHelper.columns,
	                mergeCells: [
	                    {
	                        "row": 1,
	                        "col": 0,
	                        "rowspan": 1,
	                        "colspan": 3
	                    },{
	                        "row": 4,
	                        "col": 0,
	                        "rowspan": 1,
	                        "colspan": 3
	                    },{
	                        "row": 13,
	                        "col": 0,
	                        "rowspan": 1,
	                        "colspan": 3
	                    }],
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    if (visualColIndex ==0) {
	                    	cellProperties.readOnly = true;
		                }
	                    if (visualRowIndex ==0 || visualRowIndex ==1 || visualRowIndex ==4 || visualRowIndex ==11) {
	                    	cellProperties.readOnly = true;
	                    }
						
						if (visualColIndex==1
								&&( (visualRowIndex>=2&&visualRowIndex<=3) 
										|| (visualRowIndex>=5&&visualRowIndex<=12)
										|| (visualRowIndex>=14&&visualRowIndex<=37)
							)) {
							cellProperties.renderer = kafkaProtocolConfigHandsontableHelper.addContentReadOnlyBg;
							cellProperties.readOnly = true;
		                }
	                    return cellProperties;
	                },
	                afterChange:function(changes, source){}
	            });
	        }
	        kafkaProtocolConfigHandsontableHelper.getData = function (data) {
	            kafkaProtocolConfigHandsontableHelper.get_data = data;
	            
	            
	            var totalRoot = data.totalRoot;
	            kafkaProtocolConfigHandsontableHelper.sum = totalRoot.length;
	            kafkaProtocolConfigHandsontableHelper.updateArray();
	            kafkaProtocolConfigHandsontableHelper.my_data=totalRoot;
	            
	        }
	        var init = function () {
	        }
	        init();
	        return kafkaProtocolConfigHandsontableHelper;
	    }
	};

function SaveModbusProtocolConfigData(){
	var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ScadaProtocolModbusConfigSelectRow_Id").getValue();
	if(ScadaDriverModbusConfigSelectRow!=''){
		var driverConfigSaveData=[];
		var driverConfigItemsSaveData=[];
		var protocolConfigData=protocolConfigHandsontableHelper.hot.getDataAtRow(ScadaDriverModbusConfigSelectRow);
		var driverConfigItemsData=protocolConfigItemsHandsontableHelper.hot.getData();
		var configInfo={};
		configInfo.ProtocolName=protocolConfigData[1];
		configInfo.ProtocolType=protocolConfigData[2];
		configInfo.StoreMode=protocolConfigData[3];
		configInfo.SignInPrefix=protocolConfigData[4];
		configInfo.SignInSuffix=protocolConfigData[5];
		configInfo.HeartbeatPrefix=protocolConfigData[6];
		configInfo.HeartbeatSuffix=protocolConfigData[7];
		configInfo.DataConfig=[];
		for(var i=0;i<driverConfigItemsData.length;i++){
			var item={};
			item.Name=driverConfigItemsData[i][2];
			item.Addr=parseInt(driverConfigItemsData[i][3]);
			item.Quantity=parseInt(driverConfigItemsData[i][4]);
			item.SrcDataType=driverConfigItemsData[i][5];
			item.DestDataType=driverConfigItemsData[i][6];
			item.RWType=driverConfigItemsData[i][7];
			item.Unit=driverConfigItemsData[i][8];
			item.Ratio=parseFloat(driverConfigItemsData[i][9]);
			item.AcqMode=driverConfigItemsData[i][10];
			configInfo.DataConfig.push(item);
		}
		
		Ext.Ajax.request({
    		method:'POST',
    		url:context + '/acquisitionUnitManagerController/saveModbusProtocolConfigData',
    		success:function(response) {
    			var data=Ext.JSON.decode(response.responseText);
    			if (data.success) {
                	Ext.MessageBox.alert("信息","保存成功");
//                	CreateProtocolConfigInfoTable();
                } else {
                	Ext.MessageBox.alert("信息","数据保存失败");
                }
    		},
    		failure:function(){
    			Ext.MessageBox.alert("信息","请求失败");
    		},
    		params: {
    			data:JSON.stringify(configInfo)
            }
    	}); 
		
		acquisitionUnitConfigHandsontableHelper.saveData(configInfo.ProtocolName);
		acquisitionGroupConfigHandsontableHelper.saveData(configInfo.ProtocolName);
		
		grantAcquisitionItemsPermission();
		grantAcquisitionGroupsPermission();
	}
};

function SaveScadaKafkaDriverConfigData(){

	var protocolConfigData=kafkaProtocolConfigHandsontableHelper.hot.getData();
	var configInfo={};
	var KafkaData={};
	KafkaData.Server={};
	KafkaData.Server.IP=protocolConfigData[2][2];
	KafkaData.Server.Port=parseInt(protocolConfigData[3][2]);
	
	KafkaData.Topic={};
	KafkaData.Topic.Up={};
	KafkaData.Topic.Up.NormData=protocolConfigData[5][2];
	KafkaData.Topic.Up.RawData=protocolConfigData[6][2];
	KafkaData.Topic.Up.Config=protocolConfigData[7][2];
	KafkaData.Topic.Up.Model=protocolConfigData[8][2];
	KafkaData.Topic.Up.Freq=protocolConfigData[9][2];
	KafkaData.Topic.Up.RTC=protocolConfigData[10][2];
	KafkaData.Topic.Up.Online=protocolConfigData[11][2];
	KafkaData.Topic.Up.RunStatus=protocolConfigData[12][2];
	
	KafkaData.Topic.Down={};
	KafkaData.Topic.Down.Model=protocolConfigData[14][2];
	KafkaData.Topic.Down.Model_FluidPVT=protocolConfigData[15][2];
	KafkaData.Topic.Down.Model_Reservoir=protocolConfigData[16][2];
	KafkaData.Topic.Down.Model_WellboreTrajectory=protocolConfigData[17][2];
	KafkaData.Topic.Down.Model_RodString=protocolConfigData[18][2];
	KafkaData.Topic.Down.Model_TubingString=protocolConfigData[19][2];
	KafkaData.Topic.Down.Model_Pump=protocolConfigData[20][2];
	KafkaData.Topic.Down.Model_TailtubingString=protocolConfigData[21][2];
	KafkaData.Topic.Down.Model_CasingString=protocolConfigData[22][2];
	KafkaData.Topic.Down.Model_PumpingUnit=protocolConfigData[23][2];
	KafkaData.Topic.Down.Model_SystemEfficiency=protocolConfigData[24][2];
	KafkaData.Topic.Down.Model_Production=protocolConfigData[25][2];
	KafkaData.Topic.Down.Model_FeatureDB=protocolConfigData[26][2];
	KafkaData.Topic.Down.Model_CalculationMethod=protocolConfigData[27][2];
	KafkaData.Topic.Down.Model_ManualIntervention=protocolConfigData[28][2];
	KafkaData.Topic.Down.Config=protocolConfigData[29][2];
	KafkaData.Topic.Down.StartRPC=protocolConfigData[30][2];
	KafkaData.Topic.Down.StopRPC=protocolConfigData[31][2];
	KafkaData.Topic.Down.DogRestart=protocolConfigData[32][2];
	KafkaData.Topic.Down.Freq=protocolConfigData[33][2];
	KafkaData.Topic.Down.RTC=protocolConfigData[34][2];
	KafkaData.Topic.Down.Req=protocolConfigData[35][2];
	KafkaData.Topic.Down.A9=protocolConfigData[36][2];
	KafkaData.Topic.Down.AC=protocolConfigData[37][2];

	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/saveKafkaDriverConfigData',
		success:function(response) {
			var data=Ext.JSON.decode(response.responseText);
			if (data.success) {
            	Ext.MessageBox.alert("信息","保存成功");
            	CreateKafkaConfigInfoTable();
            } else {
            	Ext.MessageBox.alert("信息","数据保存失败");

            }
		},
		failure:function(){
			Ext.MessageBox.alert("信息","请求失败");
		},
		params: {
			KafkaData:JSON.stringify(KafkaData)
        }
	}); 
};

function CreateAcquisitionUnitConfigInfoTable(isNew){
	if(isNew&&acquisitionUnitConfigHandsontableHelper!=null){
        acquisitionUnitConfigHandsontableHelper.clearContainer();
        acquisitionUnitConfigHandsontableHelper.hot.destroy();
        acquisitionUnitConfigHandsontableHelper=null;
	}
	var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ScadaProtocolModbusConfigSelectRow_Id").getValue();
	if(ScadaDriverModbusConfigSelectRow!=''){
		var protocolConfigData=protocolConfigHandsontableHelper.hot.getDataAtRow(ScadaDriverModbusConfigSelectRow);
		var protocolName=protocolConfigData[1];
		Ext.Ajax.request({
			method:'POST',
			url: context + '/acquisitionUnitManagerController/doAcquisitionUnitShow',
			success:function(response) {
				var result =  Ext.JSON.decode(response.responseText);
				if(result.totalRoot.length===0){
					result.totalRoot=[{},{},{},{},{},{},{},{},{},{}];
				}
				
				if(acquisitionUnitConfigHandsontableHelper==null){
					acquisitionUnitConfigHandsontableHelper = AcquisitionUnitConfigHandsontableHelper.createNew("AcquisitionUnitConfigTableInfoDiv_id");
					var colHeaders="[";
			        var columns="[";
			       
		            for(var i=0;i<result.columns.length;i++){
		            	colHeaders+="'"+result.columns[i].header+"'";
		            	if(result.columns[i].dataIndex.toUpperCase()==="unitName".toUpperCase()){
		            		columns+="{data:'"+result.columns[i].dataIndex+"',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_NotNull(val, callback,this.row, this.col,acquisitionUnitConfigHandsontableHelper);}}";
		            	}else{
		            		columns+="{data:'"+result.columns[i].dataIndex+"'}";
		            	}
		            	if(i<result.columns.length-1){
		            		colHeaders+=",";
		                	columns+=",";
		            	}
		            }
		            colHeaders+="]";
		        	columns+="]";
		        	acquisitionUnitConfigHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
		        	acquisitionUnitConfigHandsontableHelper.columns=Ext.JSON.decode(columns);
					acquisitionUnitConfigHandsontableHelper.createTable(result.totalRoot);
				}else{
					acquisitionUnitConfigHandsontableHelper.hot.loadData(result.totalRoot);
				}
			},
			failure:function(){
				Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
			},
			params: {
				protocolName:protocolName
	        }
		});
	}
	
	
};

var AcquisitionUnitConfigHandsontableHelper = {
	    createNew: function (divid) {
	        var acquisitionUnitConfigHandsontableHelper = {};
	        acquisitionUnitConfigHandsontableHelper.hot = '';
	        acquisitionUnitConfigHandsontableHelper.divid = divid;
	        acquisitionUnitConfigHandsontableHelper.validresult=true;//数据校验
	        acquisitionUnitConfigHandsontableHelper.colHeaders=[];
	        acquisitionUnitConfigHandsontableHelper.columns=[];
	        
	        acquisitionUnitConfigHandsontableHelper.AllData={};
	        acquisitionUnitConfigHandsontableHelper.updatelist=[];
	        acquisitionUnitConfigHandsontableHelper.delidslist=[];
	        acquisitionUnitConfigHandsontableHelper.insertlist=[];
	        acquisitionUnitConfigHandsontableHelper.editWellNameList=[];
	        
	        acquisitionUnitConfigHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        acquisitionUnitConfigHandsontableHelper.createTable = function (data) {
	        	$('#'+acquisitionUnitConfigHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+acquisitionUnitConfigHandsontableHelper.divid);
	        	acquisitionUnitConfigHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	                hiddenColumns: {
	                    columns: [0,2],
	                    indicators: true
	                },
	                columns:acquisitionUnitConfigHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:acquisitionUnitConfigHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
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
	                	      disabled: function() {
//	                	        return self.clipboardCache.length === 0;
	                	      },
	                	      callback: function() {
//	                	        var plugin = this.getPlugin('copyPaste');
//	                	        this.listen();
//	                	        plugin.paste(self.clipboardCache);
	                	      }
	                	    }
	                	}
	                },//右键菜单展示
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
//	                dropdownMenu: ['filter_by_condition', 'filter_by_value', 'filter_action_bar'],
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                },
	                afterSelectionEnd: function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	var row1=acquisitionUnitConfigHandsontableHelper.hot.getDataAtRow(row);
	                	Ext.getCmp("selectedAcquisitionUnitId_Id").setValue(row1[0]);
	                	showAcquisitionUnitOwnGroups(row1[0]);
	                },
	                afterDestroy: function() {
	                },
	                beforeRemoveRow: function (index, amount) {
	                	if(!confirm("确定要删除选中行吗？"))
	                		return false;
	                    var ids = [];
	                    if (amount != 0) {
	                        for (var i = index; i < amount + index; i++) {
	                            var rowdata = acquisitionUnitConfigHandsontableHelper.hot.getDataAtRow(i);
	                            ids.push(rowdata[0]);
	                        }
	                        acquisitionUnitConfigHandsontableHelper.delExpressCount(ids);
	                        acquisitionUnitConfigHandsontableHelper.screening();
	                    }
	                },
	                afterChange: function (changes, source) {
	                    if (changes != null) {
	                    	for(var i=0;i<changes.length;i++){
	                    		var params = [];
	                    		var index = changes[i][0]; 
		                        var rowdata = acquisitionUnitConfigHandsontableHelper.hot.getDataAtRow(index);
		                        params.push(rowdata[0]);
		                        params.push(changes[i][1]);
		                        params.push(changes[i][2]);
		                        params.push(changes[i][3]);
		                        if (params[2] != params[3] && params[0] != null && params[0] >0) {
		                        	var data="{";
		                        	for(var j=0;j<acquisitionUnitConfigHandsontableHelper.columns.length;j++){
		                        		data+=acquisitionUnitConfigHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
		                        		if(j<acquisitionUnitConfigHandsontableHelper.columns.length-1){
		                        			data+=","
		                        		}
		                        	}
		                        	data+="}"
		                            acquisitionUnitConfigHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
		                        }
	                    	}
	                    }
	                }
	        	});
	        }
	        acquisitionUnitConfigHandsontableHelper.insertExpressCount=function() {
//	            var idsdata = acquisitionUnitConfigHandsontableHelper.hot.getDataAtCol(0);
	            var acquisitionUnitData = acquisitionUnitConfigHandsontableHelper.hot.getData();
	            for (var i = 0; i < acquisitionUnitData.length; i++) {
	                if ((acquisitionUnitData[i][0] == null||acquisitionUnitData[i][0]<0) && acquisitionUnitData[i][1]!=null) {
	                    var rowdata = acquisitionUnitConfigHandsontableHelper.hot.getDataAtRow(i);
	                    if (rowdata != null) {
	                    	var data="{";
                        	for(var j=0;j<acquisitionUnitConfigHandsontableHelper.columns.length;j++){
                        		data+=acquisitionUnitConfigHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
                        		if(j<acquisitionUnitConfigHandsontableHelper.columns.length-1){
                        			data+=","
                        		}
                        	}
                        	data+="}"
	                        acquisitionUnitConfigHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
	                    }
	                }
	            }
	            if (acquisitionUnitConfigHandsontableHelper.insertlist.length != 0) {
	            	acquisitionUnitConfigHandsontableHelper.AllData.insertlist = acquisitionUnitConfigHandsontableHelper.insertlist;
	            }
	        }
	        acquisitionUnitConfigHandsontableHelper.saveData = function (protocol) {
	        	acquisitionUnitConfigHandsontableHelper.insertExpressCount();
//	        	alert(JSON.stringify(acquisitionUnitConfigHandsontableHelper.AllData));
	            if (JSON.stringify(acquisitionUnitConfigHandsontableHelper.AllData) != "{}" && acquisitionUnitConfigHandsontableHelper.validresult) {
	            	Ext.Ajax.request({
	            		method:'POST',
	            		url:context + '/acquisitionUnitManagerController/saveAcquisitionUnitHandsontableData',
	            		success:function(response) {
	            			rdata=Ext.JSON.decode(response.responseText);
	            			if (rdata.success) {
	                        	Ext.MessageBox.alert("信息","保存成功");
	                            acquisitionUnitConfigHandsontableHelper.clearContainer();
//	                            CreateAcquisitionUnitConfigInfoTable();
	                        } else {
	                        	Ext.MessageBox.alert("信息","数据保存失败");
	                        }
	            		},
	            		failure:function(){
	            			Ext.MessageBox.alert("信息","请求失败");
	                        acquisitionUnitConfigHandsontableHelper.clearContainer();
	            		},
	            		params: {
	                    	data: JSON.stringify(acquisitionUnitConfigHandsontableHelper.AllData),
	                    	protocol: protocol
	                    }
	            	}); 
	            } else {
	                if (!acquisitionUnitConfigHandsontableHelper.validresult) {
	                	Ext.MessageBox.alert("信息","数据类型错误");
	                } else {
	                	Ext.MessageBox.alert("信息","采集单元无数据变化");
	                }
	            }
	        }
	        acquisitionUnitConfigHandsontableHelper.delExpressCount=function(ids) {
	            $.each(ids, function (index, id) {
	                if (id != null) {
	                	acquisitionUnitConfigHandsontableHelper.delidslist.push(id);
	                }
	            });
	            acquisitionUnitConfigHandsontableHelper.AllData.delidslist = acquisitionUnitConfigHandsontableHelper.delidslist;
	        }
	        acquisitionUnitConfigHandsontableHelper.screening=function() {
	            if (acquisitionUnitConfigHandsontableHelper.updatelist.length != 0 && acquisitionUnitConfigHandsontableHelper.delidslist.lentgh != 0) {
	                for (var i = 0; i < acquisitionUnitConfigHandsontableHelper.delidslist.length; i++) {
	                    for (var j = 0; j < acquisitionUnitConfigHandsontableHelper.updatelist.length; j++) {
	                        if (acquisitionUnitConfigHandsontableHelper.updatelist[j].id == acquisitionUnitConfigHandsontableHelper.delidslist[i]) {
	                        	acquisitionUnitConfigHandsontableHelper.updatelist.splice(j, 1);
	                        }
	                    }
	                }
	                acquisitionUnitConfigHandsontableHelper.AllData.updatelist = acquisitionUnitConfigHandsontableHelper.updatelist;
	            }
	        }
	        acquisitionUnitConfigHandsontableHelper.updateExpressCount=function(data) {
	            if (JSON.stringify(data) != "{}") {
	                var flag = true;
	                $.each(acquisitionUnitConfigHandsontableHelper.updatelist, function (index, node) {
	                    if (node.id == data.id) {
	                        flag = false;
	                        acquisitionUnitConfigHandsontableHelper.updatelist[index] = data;
	                    }
	                });
	                flag && acquisitionUnitConfigHandsontableHelper.updatelist.push(data);
	                acquisitionUnitConfigHandsontableHelper.AllData.updatelist = acquisitionUnitConfigHandsontableHelper.updatelist;
	            }
	        }
	        
	        acquisitionUnitConfigHandsontableHelper.clearContainer = function () {
	        	acquisitionUnitConfigHandsontableHelper.AllData = {};
	        	acquisitionUnitConfigHandsontableHelper.updatelist = [];
	        	acquisitionUnitConfigHandsontableHelper.delidslist = [];
	        	acquisitionUnitConfigHandsontableHelper.insertlist = [];
	        }
	        
	        return acquisitionUnitConfigHandsontableHelper;
	    }
};

function CreateAcquisitionGroupConfigInfoTable(isNew){
	if(isNew&&acquisitionGroupConfigHandsontableHelper!=null){
        acquisitionGroupConfigHandsontableHelper.clearContainer();
        acquisitionGroupConfigHandsontableHelper.hot.destroy();
        acquisitionGroupConfigHandsontableHelper=null;
	}
	var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ScadaProtocolModbusConfigSelectRow_Id").getValue();
	if(ScadaDriverModbusConfigSelectRow!=''){
		var protocolConfigData=protocolConfigHandsontableHelper.hot.getDataAtRow(ScadaDriverModbusConfigSelectRow);
		var protocolName=protocolConfigData[1];
		Ext.Ajax.request({
			method:'POST',
			url: context + '/acquisitionUnitManagerController/doAcquisitionGroupShow',
			success:function(response) {
				var result =  Ext.JSON.decode(response.responseText);
				if(result.totalRoot.length===0){
					result.totalRoot=[{},{},{},{},{},{},{},{},{},{}];
				}
				
				if(acquisitionGroupConfigHandsontableHelper==null){
					acquisitionGroupConfigHandsontableHelper = AcquisitionGroupConfigHandsontableHelper.createNew("AcquisitionGroupConfigTableInfoDiv_id");
					var colHeaders="[";
			        var columns="[";
		            for(var i=0;i<result.columns.length;i++){
		            	colHeaders+="'"+result.columns[i].header+"'";
		            	if(result.columns[i].dataIndex.toUpperCase()==="checked".toUpperCase()){
		            		columns+="{data:'"+result.columns[i].dataIndex+"',type:'checkbox'}";
		            	}else if(result.columns[i].dataIndex.toUpperCase()==="groupName".toUpperCase()){
		            		columns+="{data:'"+result.columns[i].dataIndex+"',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_NotNull(val, callback,this.row, this.col,acquisitionGroupConfigHandsontableHelper);}}";
		            	}else if(result.columns[i].dataIndex.toUpperCase()==="acqCycle".toUpperCase() || result.columns[i].dataIndex.toUpperCase()==="saveCycle".toUpperCase()){
		            		columns+="{data:'"+result.columns[i].dataIndex+"',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,acquisitionGroupConfigHandsontableHelper);}}";
		            	}else{
		            		columns+="{data:'"+result.columns[i].dataIndex+"'}";
		            	}
		            	if(i<result.columns.length-1){
		            		colHeaders+=",";
		                	columns+=",";
		            	}
		            }
		            colHeaders+="]";
		        	columns+="]";
		        	acquisitionGroupConfigHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
		        	acquisitionGroupConfigHandsontableHelper.columns=Ext.JSON.decode(columns);
					acquisitionGroupConfigHandsontableHelper.createTable(result.totalRoot);
				}else{
					acquisitionGroupConfigHandsontableHelper.hot.loadData(result.totalRoot);
				}
			},
			failure:function(){
				Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
			},
			params: {
				protocolName:protocolName
	        }
		});
	}
};

var AcquisitionGroupConfigHandsontableHelper = {
	    createNew: function (divid) {
	        var acquisitionGroupConfigHandsontableHelper = {};
	        acquisitionGroupConfigHandsontableHelper.hot = '';
	        acquisitionGroupConfigHandsontableHelper.divid = divid;
	        acquisitionGroupConfigHandsontableHelper.validresult=true;//数据校验
	        acquisitionGroupConfigHandsontableHelper.colHeaders=[];
	        acquisitionGroupConfigHandsontableHelper.columns=[];
	        
	        acquisitionGroupConfigHandsontableHelper.AllData={};
	        acquisitionGroupConfigHandsontableHelper.updatelist=[];
	        acquisitionGroupConfigHandsontableHelper.delidslist=[];
	        acquisitionGroupConfigHandsontableHelper.insertlist=[];
	        
	        
	        acquisitionGroupConfigHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(184, 184, 184)';
	        }
	        
	        acquisitionGroupConfigHandsontableHelper.createTable = function (data) {
	        	$('#'+acquisitionGroupConfigHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+acquisitionGroupConfigHandsontableHelper.divid);
	        	acquisitionGroupConfigHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	                hiddenColumns: {
	                    columns: [1,3],
	                    indicators: true
	                },
	        		colWidths: [5,50,50,50,50,50,100],
	                columns:acquisitionGroupConfigHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:acquisitionGroupConfigHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
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
	                	      disabled: function() {
	                	      },
	                	      callback: function() {
	                	      }
	                	    }
	                	}
	                },
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
//	                    if (visualColIndex ==1 || visualColIndex ==2) {
//							cellProperties.readOnly = true;
//							cellProperties.renderer = acquisitionGroupConfigHandsontableHelper.addBoldBg;
//		                }
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	var row1=acquisitionGroupConfigHandsontableHelper.hot.getDataAtRow(row);
	                	
	                	if(row==row2 && column==column2 && isNotVal(row1[3]) && row1[1]!=null){
	                		if(row1[0]){
		                		acquisitionGroupConfigHandsontableHelper.hot.setDataAtCell(row, 0, false);
		                	}else{
		                		acquisitionGroupConfigHandsontableHelper.hot.setDataAtCell(row, 0, true);
		                	}
	                		
	                		var acquisitionGroupData = acquisitionGroupConfigHandsontableHelper.hot.getData();
		                	for(var i=0;i<acquisitionGroupData.length;i++){
		                		if(acquisitionGroupData[i][0]){
		                			Ext.getCmp("selectedAcquisitionGroupCode_Id").setValue(acquisitionGroupData[i][3]);
		    	                	showAcquisitionGroupOwnItems(acquisitionGroupData[i][3]);
		    	                	break;
		                    	}
		                	}
	                	}
	                	
	                },
	                afterDestroy: function() {
	                },
	                beforeRemoveRow: function (index, amount) {
	                    var ids = [];
	                    if (amount != 0) {
	                        for (var i = index; i < amount + index; i++) {
	                            var rowdata = acquisitionGroupConfigHandsontableHelper.hot.getDataAtRow(i);
	                            ids.push(rowdata[1]);
	                        }
	                        acquisitionGroupConfigHandsontableHelper.delExpressCount(ids);
	                        acquisitionGroupConfigHandsontableHelper.screening();
	                    }
	                },
	                afterChange: function (changes, source) {
	                    if (changes != null) {
	                    	for(var i=0;i<changes.length;i++){
	                    		if(changes[i][1]!="checked"){
	                    			var params = [];
		                    		var index = changes[i][0]; 
			                        var rowdata = acquisitionGroupConfigHandsontableHelper.hot.getDataAtRow(index);
			                        params.push(rowdata[1]);
			                        params.push(changes[i][1]);
			                        params.push(changes[i][2]);
			                        params.push(changes[i][3]);
			                        if (params[2] != params[3] && params[0] != null && params[0] >0) {
			                        	var data="{";
			                        	for(var j=0;j<acquisitionGroupConfigHandsontableHelper.columns.length;j++){
			                        		data+=acquisitionGroupConfigHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
			                        		if(j<acquisitionGroupConfigHandsontableHelper.columns.length-1){
			                        			data+=","
			                        		}
			                        	}
			                        	data+="}"
			                            acquisitionGroupConfigHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
			                        }
	                    		}
	                    	}
	                    }
	                }
	        	});
	        }
	      //插入的数据的获取
	        acquisitionGroupConfigHandsontableHelper.insertExpressCount=function() {
//	            var idsdata = acquisitionGroupConfigHandsontableHelper.hot.getDataAtCol(0); //所有的id
	            var acquisitionGroupData = acquisitionGroupConfigHandsontableHelper.hot.getData();
	            for (var i = 0; i < acquisitionGroupData.length; i++) {
	                if ((acquisitionGroupData[i][1] == null||acquisitionGroupData[i][1]<1) && acquisitionGroupData[i][2]!=null) {
	                    var rowdata = acquisitionGroupConfigHandsontableHelper.hot.getDataAtRow(i);
	                    if (rowdata != null) {
	                    	var data="{";
                        	for(var j=0;j<acquisitionGroupConfigHandsontableHelper.columns.length;j++){
                        		data+=acquisitionGroupConfigHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
                        		if(j<acquisitionGroupConfigHandsontableHelper.columns.length-1){
                        			data+=","
                        		}
                        	}
                        	data+="}"
	                        acquisitionGroupConfigHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
	                    }
	                }
	            }
	            if (acquisitionGroupConfigHandsontableHelper.insertlist.length != 0) {
	            	acquisitionGroupConfigHandsontableHelper.AllData.insertlist = acquisitionGroupConfigHandsontableHelper.insertlist;
	            }
	        }
	        //保存数据
	        acquisitionGroupConfigHandsontableHelper.saveData = function (protocol) {
	        	acquisitionGroupConfigHandsontableHelper.insertExpressCount();
	        	var unitId=Ext.getCmp("selectedAcquisitionUnitId_Id").getValue();
//	        	alert(JSON.stringify(acquisitionGroupConfigHandsontableHelper.AllData));
	            if (JSON.stringify(acquisitionGroupConfigHandsontableHelper.AllData) != "{}" && acquisitionGroupConfigHandsontableHelper.validresult) {
	            	Ext.Ajax.request({
	            		method:'POST',
	            		url:context + '/acquisitionUnitManagerController/saveAcquisitionGroupHandsontableData',
	            		success:function(response) {
	            			rdata=Ext.JSON.decode(response.responseText);
	            			if (rdata.success) {
	                        	Ext.MessageBox.alert("信息","保存成功");
	                            acquisitionGroupConfigHandsontableHelper.clearContainer();
	                        } else {
	                        	Ext.MessageBox.alert("信息","数据保存失败");
	                        }
	            		},
	            		failure:function(){
	            			Ext.MessageBox.alert("信息","请求失败");
	                        acquisitionGroupConfigHandsontableHelper.clearContainer();
	            		},
	            		params: {
	                    	data: JSON.stringify(acquisitionGroupConfigHandsontableHelper.AllData),
	                    	protocol: protocol,
	                    	unitId: unitId
	                    }
	            	}); 
	            } else {
	                if (!acquisitionGroupConfigHandsontableHelper.validresult) {
	                	Ext.MessageBox.alert("信息","数据类型错误");
	                } else {
	                	Ext.MessageBox.alert("信息","无数据变化");
	                }
	            }
	        }
	        
	        acquisitionGroupConfigHandsontableHelper.delExpressCount=function(ids) {
	            $.each(ids, function (index, id) {
	                if (id != null) {
	                	acquisitionGroupConfigHandsontableHelper.delidslist.push(id);
	                }
	            });
	            acquisitionGroupConfigHandsontableHelper.AllData.delidslist = acquisitionGroupConfigHandsontableHelper.delidslist;
	        }

	        acquisitionGroupConfigHandsontableHelper.screening=function() {
	            if (acquisitionGroupConfigHandsontableHelper.updatelist.length != 0 && acquisitionGroupConfigHandsontableHelper.delidslist.lentgh != 0) {
	                for (var i = 0; i < acquisitionGroupConfigHandsontableHelper.delidslist.length; i++) {
	                    for (var j = 0; j < acquisitionGroupConfigHandsontableHelper.updatelist.length; j++) {
	                        if (acquisitionGroupConfigHandsontableHelper.updatelist[j].id == acquisitionGroupConfigHandsontableHelper.delidslist[i]) {
	                        	acquisitionGroupConfigHandsontableHelper.updatelist.splice(j, 1);
	                        }
	                    }
	                }
	                acquisitionGroupConfigHandsontableHelper.AllData.updatelist = acquisitionGroupConfigHandsontableHelper.updatelist;
	            }
	        }
	        acquisitionGroupConfigHandsontableHelper.updateExpressCount=function(data) {
	            if (JSON.stringify(data) != "{}") {
	                var flag = true;
	                $.each(acquisitionGroupConfigHandsontableHelper.updatelist, function (index, node) {
	                    if (node.id == data.id) {
	                        flag = false;
	                        acquisitionGroupConfigHandsontableHelper.updatelist[index] = data;
	                    }
	                });
	                flag && acquisitionGroupConfigHandsontableHelper.updatelist.push(data);
	                acquisitionGroupConfigHandsontableHelper.AllData.updatelist = acquisitionGroupConfigHandsontableHelper.updatelist;
	            }
	        }
	        acquisitionGroupConfigHandsontableHelper.clearContainer = function () {
	        	acquisitionGroupConfigHandsontableHelper.AllData = {};
	        	acquisitionGroupConfigHandsontableHelper.updatelist = [];
	        	acquisitionGroupConfigHandsontableHelper.delidslist = [];
	        	acquisitionGroupConfigHandsontableHelper.insertlist = [];
	        }
	        return acquisitionGroupConfigHandsontableHelper;
	    }
};