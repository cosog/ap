var protocolConfigItemsHandsontableHelper=null;
var kafkaProtocolConfigHandsontableHelper=null;
var protocolConfigPropertiesHandsontableHelper=null;
Ext.define('AP.view.acquisitionUnit.ProtocolConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.protocolConfigInfoView',
    layout: "fit",
    id:'protocolConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	var ModbusProtocolTreeInfoStore = Ext.create('AP.store.acquisitionUnit.ModbusProtocolTreeInfoStore');
    	Ext.apply(me, {
    		items: [{
    			xtype: 'tabpanel',
                id:"ScadaDriverConfigTabPanel_Id",
                activeTab: 0,
                border: false,
                tabPosition: 'top',
                items: [{
                	title:'Modbus',
                	id:"ScadaDriverModbusConfigTabPanel_Id",
                	tbar: [{
                        id: 'ScadaProtocolModbusConfigSelectRow_Id',
                        xtype: 'textfield',
                        value: 0,
                        hidden: true
                    },'->',{
            			xtype: 'button',
                        text: '添加协议',
                        iconCls: 'add',
                        handler: function (v, o) {
            				addModbusProtocolConfigData();
            			}
            		}, "-",{
            			xtype: 'button',
                        text: '添加采集单元',
                        iconCls: 'add',
                        handler: function (v, o) {
                        	addAcquisitionUnitInfo();
            			}
            		}, "-",{
            			xtype: 'button',
                        text: '添加采集组',
                        iconCls: 'add',
                        handler: function (v, o) {
                        	addAcquisitionGroupInfo();
            			}
            		},"-",{
                    	xtype: 'button',
            			pressed: true,
            			text: cosog.string.save,
            			iconCls: 'save',
            			handler: function (v, o) {
            				SaveModbusProtocolConfigData();
            			}
                    }],
            		layout: {
                        type: 'hbox',
                        pack: 'start',
                        align: 'stretch'
                    },
                    items: [{
                    	border: true,
                        flex: 1,
                        layout: "border",
                        border: false,
                        items: [{
                        	region: 'center',
                        	id:"ModbusProtocolConfigPanel_Id"
                        },{
                        	region: 'south',
                        	height:'40%',
                        	title:'属性',
                        	layout: 'fit',
                            html:'<div class="ProtocolConfigPropertiesTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ProtocolConfigPropertiesTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	
                                }
                            }
                        }]
                    },{
                    	border: true,
                        flex: 5,
                        title:'采控项配置',
                        layout: 'fit',
                        html:'<div class="DriverItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="DriverItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            	
                            }
                        }
                    }]
                },{
                	title:'Kafka',
                	id:"ScadaDriverKafkaConfigTabPanel_Id",
                	layout: 'fit',
                	tbar: ['->',{
                		xtype: 'button',
            			pressed: true,
            			text: cosog.string.save,
            			iconCls: 'save',
            			handler: function (v, o) {
            				SaveScadaKafkaDriverConfigData();
            			}
            		}],
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
    		}]
    	});
        this.callParent(arguments);
    }
});

function CreateProtocolItemsConfigInfoTable(protocolName){
	if(protocolConfigItemsHandsontableHelper!=null){
		if(protocolConfigItemsHandsontableHelper.hot!=undefined){
			protocolConfigItemsHandsontableHelper.hot.destroy();
		}
		protocolConfigItemsHandsontableHelper=null;
	}
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolItemsConfigData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			
			protocolConfigItemsHandsontableHelper = ProtocolConfigItemsHandsontableHelper.createNew("DriverItemsConfigTableInfoDiv_id");
			var colHeaders="['','序号','名称','地址','数量','存储数据类型','接口数据类型','读写类型','单位','换算比例','采集模式']";
			var columns="[{data:'checked',type:'checkbox'},{data:'id'},{data:'title'},"
				 	+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolConfigItemsHandsontableHelper);}},"
					+"{data:'quantity',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolConfigItemsHandsontableHelper);}}," 
					+"{data:'storeDataType',type:'dropdown',strict:true,allowInvalid:false,source:['byte','int16','uint16','float32','bcd']}," 
					+"{data:'IFDataType',type:'dropdown',strict:true,allowInvalid:false,source:['bool','int','float32','float64','string']}," 
					+"{data:'RWType',type:'dropdown',strict:true,allowInvalid:false,source:['只读', '读写']}," 
					+"{data:'unit'}," 
					+"{data:'ratio',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolConfigItemsHandsontableHelper);}}," 
					+"{data:'acqMode',type:'dropdown',strict:true,allowInvalid:false,source:['主动上传', '被动响应']}" 
					+"]";
			protocolConfigItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
			protocolConfigItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
			if(result.totalRoot.length==0){
				protocolConfigItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
			}else{
				protocolConfigItemsHandsontableHelper.createTable(result.totalRoot);
			}
			
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			protocolName:protocolName
        }
	});
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
	        		colWidths: [25,50,120,80,80,80,80,80,80,80,80],
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
	                },//右键菜单展示
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
//	                    if (visualColIndex ==1 || visualColIndex ==2) {
//							cellProperties.readOnly = true;
//							cellProperties.renderer = protocolConfigItemsHandsontableHelper.addBoldBg;
//		                }
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
//	                	var row1=protocolConfigItemsHandsontableHelper.hot.getDataAtRow(row);
//	                	if(row1[0]){
//	                		protocolConfigItemsHandsontableHelper.hot.setDataAtCell(row, 0, false);
//	                	}else{
//	                		protocolConfigItemsHandsontableHelper.hot.setDataAtCell(row, 0, true);
//	                	}
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


function CreateProtocolConfigPropertiesInfoTable(data){
	var root=[];
	if(data.classes==1){
		var item={};
		item.id=1;
		item.title='类型';
		item.value=data.type;
		root.push(item);
		
		var item2={};
		item2.id=2;
		item2.title='注册包前缀(HEX)';
		item2.value=data.signInPrefix;
		root.push(item2);
		
		var item3={};
		item3.id=3;
		item3.title='注册包后缀(HEX)';
		item3.value=data.signInSuffix;
		root.push(item3);
		
		var item4={};
		item4.id=4;
		item4.title='心跳包前缀(HEX)';
		item4.value=data.heartbeatPrefix;
		root.push(item4);
		
		var item5={};
		item5.id=5;
		item5.title='心跳包后缀(HEX)';
		item5.value=data.heartbeatSuffix;
		root.push(item5);
		
		var item6={};
		item6.id=6;
		item6.title='排序序号';
		item6.value=data.sort;
		root.push(item6);
	}else if(data.classes==2){
		var item={};
		item.id=1;
		item.title='备注';
		item.value=data.remark;
		root.push(item);
	}else if(data.classes==3){
		var item={};
		item.id=1;
		item.title='采集周期(s)';
		item.value=data.acq_cycle;
		root.push(item);
		
		var item2={};
		item2.id=2;
		item2.title='保存周期(s)';
		item2.value=data.save_cycle;
		root.push(item2);
		
		var item3={};
		item3.id=3;
		item3.title='备注';
		item3.value=data.remark;
		root.push(item3);
	}
	
	if(protocolConfigPropertiesHandsontableHelper!=null){
		if(protocolConfigPropertiesHandsontableHelper.hot!=undefined){
			protocolConfigPropertiesHandsontableHelper.hot.destroy();
		}
		protocolConfigPropertiesHandsontableHelper=null;
	}
	protocolConfigPropertiesHandsontableHelper = ProtocolConfigPropertiesHandsontableHelper.createNew("ProtocolConfigPropertiesTableInfoDiv_id");
	var colHeaders="['序号','名称','值']";
	var columns="[{data:'id'},{data:'title'},{data:'value'}]";
	protocolConfigPropertiesHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
	protocolConfigPropertiesHandsontableHelper.columns=Ext.JSON.decode(columns);
	if(root==undefined || root==null || root.length==0){
		protocolConfigPropertiesHandsontableHelper.createTable([{},{},{},{},{}]);
	}else{
		protocolConfigPropertiesHandsontableHelper.createTable(root);
	}
};

var ProtocolConfigPropertiesHandsontableHelper = {
		createNew: function (divid) {
	        var protocolConfigPropertiesHandsontableHelper = {};
	        protocolConfigPropertiesHandsontableHelper.hot = '';
	        protocolConfigPropertiesHandsontableHelper.divid = divid;
	        protocolConfigPropertiesHandsontableHelper.validresult=true;//数据校验
	        protocolConfigPropertiesHandsontableHelper.colHeaders=[];
	        protocolConfigPropertiesHandsontableHelper.columns=[];
	        protocolConfigPropertiesHandsontableHelper.AllData=[];
	        
	        protocolConfigPropertiesHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        protocolConfigPropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(184, 184, 184)';
	        }
	        
	        protocolConfigPropertiesHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolConfigPropertiesHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolConfigPropertiesHandsontableHelper.divid);
	        	protocolConfigPropertiesHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
//	        		colWidths: [50,120,120],
//	                hiddenColumns: {
//	                    columns: [0],
//	                    indicators: true
//	                },
	                columns:protocolConfigPropertiesHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolConfigPropertiesHandsontableHelper.colHeaders,//显示列头
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
	                    if (visualColIndex ==0 || visualColIndex ==1) {
							cellProperties.readOnly = true;
							cellProperties.renderer = protocolConfigPropertiesHandsontableHelper.addBoldBg;
		                }
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        protocolConfigPropertiesHandsontableHelper.saveData = function () {}
	        protocolConfigPropertiesHandsontableHelper.clearContainer = function () {
	        	protocolConfigPropertiesHandsontableHelper.AllData = [];
	        }
	        return protocolConfigPropertiesHandsontableHelper;
	    }
};

function CreateKafkaConfigInfoTable(){
	if(kafkaProtocolConfigHandsontableHelper!=null){
//		kafkaProtocolConfigHandsontableHelper.clearContainer();
		kafkaProtocolConfigHandsontableHelper.hot.destroy();
		kafkaProtocolConfigHandsontableHelper=null;
	}
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
	                        "row": 7,
	                        "col": 0,
	                        "rowspan": 1,
	                        "colspan": 3
	                    },{
	                        "row": 17,
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
	                    if (visualRowIndex ==0 || visualRowIndex ==1 || visualRowIndex ==4 || visualRowIndex ==7 || visualRowIndex ==17) {
	                    	cellProperties.readOnly = true;
	                    }
						
						if (visualColIndex==1
								&&( (visualRowIndex>=2&&visualRowIndex<=3) 
										|| (visualRowIndex>=5&&visualRowIndex<=6) 
										|| (visualRowIndex>=8&&visualRowIndex<=16)
										|| (visualRowIndex>=18&&visualRowIndex<=42)
							)) {
							cellProperties.renderer = kafkaProtocolConfigHandsontableHelper.addContentReadOnlyBg;
							cellProperties.readOnly = true;
		                }
						
	                    if (visualColIndex === 2 && visualRowIndex===3) {
	                    	this.type = 'dropdown';
	                    	this.source = ['kafka-2.7','kafka-2.6', 'kafka-2.5','kafka-2.4','kafka-2.3','kafka-2.2', 'kafka-2.1','kafka-2.0','kafka-1.1','kafka-1.0','kafka-0.11'];
	                    	this.strict = true;
	                    	this.allowInvalid = false;
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

function SaveScadaKafkaDriverConfigData(){
	var protocolConfigData=kafkaProtocolConfigHandsontableHelper.hot.getData();
	var configInfo={};
	var KafkaData={};
	KafkaData.ProtocolName=protocolConfigData[2][2];
	KafkaData.Version=protocolConfigData[3][2];
	KafkaData.Server={};
	KafkaData.Server.IP=protocolConfigData[5][2];
	KafkaData.Server.Port=parseInt(protocolConfigData[6][2]);
	
	KafkaData.Topic={};
	KafkaData.Topic.Up={};
	KafkaData.Topic.Up.NormData=protocolConfigData[8][2];
	KafkaData.Topic.Up.RawData=protocolConfigData[9][2];
	KafkaData.Topic.Up.RawWaterCut=protocolConfigData[10][2];
	KafkaData.Topic.Up.Config=protocolConfigData[11][2];
	KafkaData.Topic.Up.Model=protocolConfigData[12][2];
	KafkaData.Topic.Up.Freq=protocolConfigData[13][2];
	KafkaData.Topic.Up.RTC=protocolConfigData[14][2];
	KafkaData.Topic.Up.Online=protocolConfigData[15][2];
	KafkaData.Topic.Up.RunStatus=protocolConfigData[16][2];
	
	KafkaData.Topic.Down={};
	KafkaData.Topic.Down.Model=protocolConfigData[18][2];
	KafkaData.Topic.Down.Model_FluidPVT=protocolConfigData[19][2];
	KafkaData.Topic.Down.Model_Reservoir=protocolConfigData[20][2];
	KafkaData.Topic.Down.Model_WellboreTrajectory=protocolConfigData[21][2];
	KafkaData.Topic.Down.Model_RodString=protocolConfigData[22][2];
	KafkaData.Topic.Down.Model_TubingString=protocolConfigData[23][2];
	KafkaData.Topic.Down.Model_Pump=protocolConfigData[24][2];
	KafkaData.Topic.Down.Model_TailtubingString=protocolConfigData[25][2];
	KafkaData.Topic.Down.Model_CasingString=protocolConfigData[26][2];
	KafkaData.Topic.Down.Model_PumpingUnit=protocolConfigData[27][2];
	KafkaData.Topic.Down.Model_SystemEfficiency=protocolConfigData[28][2];
	KafkaData.Topic.Down.Model_Production=protocolConfigData[29][2];
	KafkaData.Topic.Down.Model_FeatureDB=protocolConfigData[30][2];
	KafkaData.Topic.Down.Model_CalculationMethod=protocolConfigData[31][2];
	KafkaData.Topic.Down.Model_ManualIntervention=protocolConfigData[32][2];
	KafkaData.Topic.Down.Config=protocolConfigData[33][2];
	KafkaData.Topic.Down.StartRPC=protocolConfigData[34][2];
	KafkaData.Topic.Down.StopRPC=protocolConfigData[35][2];
	KafkaData.Topic.Down.DogRestart=protocolConfigData[36][2];
	KafkaData.Topic.Down.Freq=protocolConfigData[37][2];
	KafkaData.Topic.Down.RTC=protocolConfigData[38][2];
	KafkaData.Topic.Down.Req=protocolConfigData[39][2];
	KafkaData.Topic.Down.Probe=protocolConfigData[40][2];
	KafkaData.Topic.Down.A9=protocolConfigData[41][2];
	KafkaData.Topic.Down.AC=protocolConfigData[42][2];

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


function SaveModbusProtocolConfigData(){
	var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ScadaProtocolModbusConfigSelectRow_Id").getValue();
	
	if(ScadaDriverModbusConfigSelectRow!=''){
		var selectedItem=Ext.getCmp("ModbusProtocolConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
		var driverConfigSaveData=[];
		var driverConfigItemsSaveData=[];
		var protocolConfigData={};
		if(selectedItem.data.classes==1){//选中的是协议
			protocolConfigData=selectedItem.data;
		}else if(selectedItem.data.classes==2){//选中的是采集单元
			protocolConfigData=selectedItem.parentNode.data;
		}else if(selectedItem.data.classes==3){//选中的是采集单元组
			protocolConfigData=selectedItem.parentNode.parentNode.data;
		}
		
		var driverConfigItemsData=protocolConfigItemsHandsontableHelper.hot.getData();
		
		if(isNotVal(protocolConfigData.text)){
			var configInfo={};
			configInfo.ProtocolName=protocolConfigData.text;
			configInfo.ProtocolType=protocolConfigData.type;
			configInfo.SignInPrefix=protocolConfigData.signInPrefix;
			configInfo.SignInSuffix=protocolConfigData.signInSuffix;
			configInfo.HeartbeatPrefix=protocolConfigData.heartbeatPrefix;
			configInfo.HeartbeatSuffix=protocolConfigData.heartbeatSuffix;
			configInfo.Sort=protocolConfigData.sort;
			configInfo.DataConfig=[];
			for(var i=0;i<driverConfigItemsData.length;i++){
				if(isNotVal(driverConfigItemsData[i][2])){
					var item={};
					item.Title=driverConfigItemsData[i][2];
					item.Addr=parseInt(driverConfigItemsData[i][3]);
					item.Quantity=parseInt(driverConfigItemsData[i][4]);
					item.StoreDataType=driverConfigItemsData[i][5];
					item.IFDataType=driverConfigItemsData[i][6];
					item.RWType=driverConfigItemsData[i][7];
					item.Unit=driverConfigItemsData[i][8];
					item.Ratio=parseFloat(driverConfigItemsData[i][9]);
					item.AcqMode=driverConfigItemsData[i][10];
					configInfo.DataConfig.push(item);
				}
			}
			
			Ext.Ajax.request({
	    		method:'POST',
	    		url:context + '/acquisitionUnitManagerController/saveModbusProtocolConfigData',
	    		success:function(response) {
	    			var data=Ext.JSON.decode(response.responseText);
	    			protocolConfigHandsontableHelper.clearContainer();
	    			if (data.success) {
	                	Ext.MessageBox.alert("信息","保存成功");
//	                	CreateProtocolConfigInfoTable();
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
//			acquisitionUnitConfigHandsontableHelper.saveData(configInfo.ProtocolName);
//			acquisitionGroupConfigHandsontableHelper.saveData(configInfo.ProtocolName);
//			
//			grantAcquisitionItemsPermission();
//			grantAcquisitionGroupsPermission();
			if(selectedItem.data.classes==3){//选中的是采集单元组
				grantAcquisitionItemsPermission();
			}
		}else{
			Ext.MessageBox.alert("提示","协议名称不能为空！");
		}
	}
};