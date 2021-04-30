var driverConfigHandsontableHelper=null;
var driverConfigItemsHandsontableHelper=null;
var kafkaDriverConfigHandsontableHelper=null;
var tcpServerConfigHandsontableHelper=null;
Ext.define('AP.view.acquisitionUnit.DriverConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.driverConfigInfoView',
    layout: "fit",
    id:'driverConfigInfoViewId',
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
        					SaveModbusDriverConfigData();
        				}else if(activeId=="ScadaDriverKafkaConfigTabPanel_Id"){
        					SaveScadaKafkaDriverConfigData();
        				}
        			}
                },{
                    id: 'ScadaDriverModbusConfigSelectRow_Id', //选择查看曲线的数据项名称
                    xtype: 'textfield',
                    value: 0,
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
                    	region: 'west',
                        title:'TCPServer配置',
                        width: '25%',
                        layout: 'fit',
                        collapsible: true,
                        split: true,
                        html:'<div class="TcpServerConfigInfoContainer" style="width:100%;height:100%;"><div class="con" id="TcpServerConfigInfoInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            	CreateTcpServerConfigInfoTable(true);
                            }
                        }
                    },{
                        region: 'center',
                        title:'协议配置',
                        border: false,
                        layout: 'fit',
                        html:'<div class="DriverConfigInfoContainer" style="width:100%;height:100%;"><div class="con" id="DriverConfigInfoInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            	CreateDriverConfigInfoTable(true);
                            }
                        }
                    }, {
                        region: 'east',
                        title:'采控项配置',
                        layout: 'fit',
                        width: '60%',
                        collapsible: true,
                        split: true,
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

function CreateDriverConfigInfoTable(isNew){
	if(isNew&&driverConfigHandsontableHelper!=null){
        driverConfigHandsontableHelper.clearContainer();
        driverConfigHandsontableHelper.hot.destroy();
        driverConfigHandsontableHelper=null;
	}
	if(isNew&&driverConfigItemsHandsontableHelper!=null){
		driverConfigItemsHandsontableHelper.hot.destroy();
		driverConfigItemsHandsontableHelper=null;
	}
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getDriverConfigData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(driverConfigHandsontableHelper==null){
				driverConfigHandsontableHelper = DriverConfigHandsontableHelper.createNew("DriverConfigInfoInfoDiv_id");
				var colHeaders="[";
		        var columns="[";
		       
	            for(var i=0;i<result.columns.length;i++){
	            	colHeaders+="'"+result.columns[i].header+"'";
	            	if(result.columns[i].dataIndex==="protocol"){
	            		columns+="{data:'"+result.columns[i].dataIndex+"',type:'dropdown',strict:true,allowInvalid:false,source:['Modbus-TCP', 'Modbus-RTU']}";
	            	}else if(result.columns[i].dataIndex==="port"){
	            		columns+="{data:'"+result.columns[i].dataIndex+"',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,driverConfigHandsontableHelper);}}";
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
	        	driverConfigHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
	        	driverConfigHandsontableHelper.columns=Ext.JSON.decode(columns);
				driverConfigHandsontableHelper.createTable(result.totalRoot);
			}else{
				driverConfigHandsontableHelper.hot.loadData(result.totalRoot);
			}
			
			var row1=driverConfigHandsontableHelper.hot.getDataAtRow(0);
			CreateDriverConfigItemsInfoTable(row1[2])
//			if(driverConfigItemsHandsontableHelper==null){
//				CreateDriverConfigItemsInfoTable(result);
//			}else{
//				driverConfigItemsHandsontableHelper.hot.loadData(result.columnRoot);
//			}
			
			
			
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
            
        }
	});
};

var DriverConfigHandsontableHelper = {
	    createNew: function (divid) {
	        var driverConfigHandsontableHelper = {};
	        driverConfigHandsontableHelper.hot = '';
	        driverConfigHandsontableHelper.divid = divid;
	        driverConfigHandsontableHelper.validresult=true;//数据校验
	        driverConfigHandsontableHelper.colHeaders=[];
	        driverConfigHandsontableHelper.columns=[];
	        
	        driverConfigHandsontableHelper.AllData={};
	        driverConfigHandsontableHelper.updatelist=[];
	        driverConfigHandsontableHelper.delidslist=[];
	        driverConfigHandsontableHelper.insertlist=[];
	        driverConfigHandsontableHelper.editWellNameList=[];
	        
	        driverConfigHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        driverConfigHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(184, 184, 184)';
	        }
	        
	        driverConfigHandsontableHelper.createTable = function (data) {
	        	$('#'+driverConfigHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+driverConfigHandsontableHelper.divid);
	        	driverConfigHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	                hiddenColumns: {
	                    columns: [0,2],
	                    indicators: true
	                },
	                columns:driverConfigHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:driverConfigHandsontableHelper.colHeaders,//显示列头
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
							cellProperties.renderer = driverConfigHandsontableHelper.addBoldBg;
		                }
	                    return cellProperties;
	                },
	                afterSelection: function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	var row1=driverConfigHandsontableHelper.hot.getDataAtRow(row);
	                	CreateDriverConfigItemsInfoTable(row1[2]);
	                	
	                	Ext.getCmp("ScadaDriverModbusConfigSelectRow_Id").setValue(row);
	                },
	                afterDestroy: function() {
	                    // 移除事件
//	                    Handsontable.Dom.removeEvent(save, 'click', saveData);
//	                    loadDataTable();
	                },
	                beforeRemoveRow: function (index, amount) {
	                    var ids = [];
	                    //封装id成array传入后台
	                    if (amount != 0) {
	                        for (var i = index; i < amount + index; i++) {
	                            var rowdata = driverConfigHandsontableHelper.hot.getDataAtRow(i);
	                            ids.push(rowdata[0]);
	                        }
	                        driverConfigHandsontableHelper.delExpressCount(ids);
	                        driverConfigHandsontableHelper.screening();
	                    }
	                },
	                afterChange: function (changes, source) {
	                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
	                    
	                    if (changes != null) {
	                    	for(var i=0;i<changes.length;i++){
	                    		var params = [];
	                    		var index = changes[i][0]; //行号码
		                        var rowdata = driverConfigHandsontableHelper.hot.getDataAtRow(index);
		                        params.push(rowdata[0]);
		                        params.push(changes[i][1]);
		                        params.push(changes[i][2]);
		                        params.push(changes[i][3]);
		                        
		                        if("edit"==source&&params[1]=="wellName"){//编辑井号单元格
		                        	var data="{\"oldWellName\":\""+params[2]+"\",\"newWellName\":\""+params[3]+"\"}";
		                        	driverConfigHandsontableHelper.editWellNameList.push(Ext.JSON.decode(data));
		                        }

		                        //仅当单元格发生改变的时候,id!=null,说明是更新
		                        if (params[2] != params[3] && params[0] != null && params[0] >0) {
		                        	var data="{";
		                        	for(var j=0;j<driverConfigHandsontableHelper.columns.length;j++){
		                        		data+=driverConfigHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
		                        		if(j<driverConfigHandsontableHelper.columns.length-1){
		                        			data+=","
		                        		}
		                        	}
		                        	data+="}"
		                            driverConfigHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
		                        }
	                    	}
	                        
	                    }
	                }
	        	});
	        }
	      //插入的数据的获取
	        driverConfigHandsontableHelper.insertExpressCount=function() {
	            var idsdata = driverConfigHandsontableHelper.hot.getDataAtCol(0); //所有的id
	            for (var i = 0; i < idsdata.length; i++) {
	                //id=null时,是插入数据,此时的i正好是行号
	                if (idsdata[i] == null||idsdata[i]<0) {
	                    //获得id=null时的所有数据封装进data
	                    var rowdata = driverConfigHandsontableHelper.hot.getDataAtRow(i);
	                    //var collength = hot.countCols();
	                    if (rowdata != null) {
	                    	var data="{";
                        	for(var j=0;j<driverConfigHandsontableHelper.columns.length;j++){
                        		data+=driverConfigHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
                        		if(j<driverConfigHandsontableHelper.columns.length-1){
                        			data+=","
                        		}
                        	}
                        	data+="}"
	                        driverConfigHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
	                    }
	                }
	            }
	            if (driverConfigHandsontableHelper.insertlist.length != 0) {
	            	driverConfigHandsontableHelper.AllData.insertlist = driverConfigHandsontableHelper.insertlist;
	            }
	        }
	        //保存数据
	        driverConfigHandsontableHelper.saveData = function () {
	        	
	        	var IframeViewSelection  = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
	        	if(IframeViewSelection.length>0&&IframeViewSelection[0].isLeaf()){
	        		//插入的数据的获取
		        	driverConfigHandsontableHelper.insertExpressCount();
		        	var orgId=IframeViewSelection[0].data.orgId;
		            if (JSON.stringify(driverConfigHandsontableHelper.AllData) != "{}" && driverConfigHandsontableHelper.validresult) {
		            	Ext.Ajax.request({
		            		method:'POST',
		            		url:context + '/wellInformationManagerController/saveWellHandsontableData',
		            		success:function(response) {
		            			rdata=Ext.JSON.decode(response.responseText);
		            			if (rdata.success) {
		                        	Ext.MessageBox.alert("信息","保存成功");
		                            //保存以后重置全局容器
		                            driverConfigHandsontableHelper.clearContainer();
		                            CreateDriverConfigInfoTable();
		                        } else {
		                        	Ext.MessageBox.alert("信息","数据保存失败");

		                        }
		            		},
		            		failure:function(){
		            			Ext.MessageBox.alert("信息","请求失败");
		                        driverConfigHandsontableHelper.clearContainer();
		            		},
		            		params: {
		                    	data: JSON.stringify(driverConfigHandsontableHelper.AllData),
		                    	orgId:orgId
		                    }
		            	}); 
		            } else {
		                if (!driverConfigHandsontableHelper.validresult) {
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
	        driverConfigHandsontableHelper.delExpressCount=function(ids) {
	            //传入的ids.length不可能为0
	            $.each(ids, function (index, id) {
	                if (id != null) {
	                	driverConfigHandsontableHelper.delidslist.push(id);
	                }
	            });
	            driverConfigHandsontableHelper.AllData.delidslist = driverConfigHandsontableHelper.delidslist;
	        }

	        //updatelist数据更新
	        driverConfigHandsontableHelper.screening=function() {
	            if (driverConfigHandsontableHelper.updatelist.length != 0 && driverConfigHandsontableHelper.delidslist.lentgh != 0) {
	                for (var i = 0; i < driverConfigHandsontableHelper.delidslist.length; i++) {
	                    for (var j = 0; j < driverConfigHandsontableHelper.updatelist.length; j++) {
	                        if (driverConfigHandsontableHelper.updatelist[j].id == driverConfigHandsontableHelper.delidslist[i]) {
	                            //更新updatelist
	                        	driverConfigHandsontableHelper.updatelist.splice(j, 1);
	                        }
	                    }
	                }
	                //把updatelist封装进AllData
	                driverConfigHandsontableHelper.AllData.updatelist = driverConfigHandsontableHelper.updatelist;
	            }
	        }
	        
	      //更新数据
	        driverConfigHandsontableHelper.updateExpressCount=function(data) {
	            if (JSON.stringify(data) != "{}") {
	                var flag = true;
	                //判断记录是否存在,更新数据     
	                $.each(driverConfigHandsontableHelper.updatelist, function (index, node) {
	                    if (node.id == data.id) {
	                        //此记录已经有了
	                        flag = false;
	                        //用新得到的记录替换原来的,不用新增
	                        driverConfigHandsontableHelper.updatelist[index] = data;
	                    }
	                });
	                flag && driverConfigHandsontableHelper.updatelist.push(data);
	                //封装
	                driverConfigHandsontableHelper.AllData.updatelist = driverConfigHandsontableHelper.updatelist;
	            }
	        }
	        
	        driverConfigHandsontableHelper.clearContainer = function () {
	        	driverConfigHandsontableHelper.AllData = {};
	        	driverConfigHandsontableHelper.updatelist = [];
	        	driverConfigHandsontableHelper.delidslist = [];
	        	driverConfigHandsontableHelper.insertlist = [];
	        	driverConfigHandsontableHelper.editWellNameList=[];
	        }
	        
	        return driverConfigHandsontableHelper;
	    }
};

function CreateDriverConfigItemsInfoTable(data){
	driverConfigItemsHandsontableHelper = DriverConfigItemsHandsontableHelper.createNew("DriverItemsConfigTableInfoDiv_id");
	var colHeaders="['序号','名称','地址','寄存器长度','数据类型','读写类型','单位','单位换算系数','模式']";
	var columns="[{data:'id'},{data:'item'},"
		 	+"{data:'address',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,driverConfigItemsHandsontableHelper);}},"
			+"{data:'length',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,driverConfigItemsHandsontableHelper);}}," 
			+"{data:'dataType',type:'dropdown',strict:true,allowInvalid:false,source:['整型', '实型','BCD码']}," 
			+"{data:'readonly',type:'dropdown',strict:true,allowInvalid:false,source:['只读', '读写']}," 
			+"{data:'unit'}," 
			+"{data:'zoom',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,driverConfigItemsHandsontableHelper);}}," 
			+"{data:'initiative',type:'dropdown',strict:true,allowInvalid:false,source:['主动轮询', '被动接收']}" 
			+"]";
	driverConfigItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
	driverConfigItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
	if(data==undefined||data==null||data.length==0){
		driverConfigItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
	}else{
		driverConfigItemsHandsontableHelper.createTable(data);
	}
};


var DriverConfigItemsHandsontableHelper = {
		createNew: function (divid) {
	        var driverConfigItemsHandsontableHelper = {};
	        driverConfigItemsHandsontableHelper.hot1 = '';
	        driverConfigItemsHandsontableHelper.divid = divid;
	        driverConfigItemsHandsontableHelper.validresult=true;//数据校验
	        driverConfigItemsHandsontableHelper.colHeaders=[];
	        driverConfigItemsHandsontableHelper.columns=[];
	        driverConfigItemsHandsontableHelper.AllData=[];
	        
	        driverConfigItemsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        driverConfigItemsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(184, 184, 184)';
	        }
	        
	        driverConfigItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+driverConfigItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+driverConfigItemsHandsontableHelper.divid);
	        	driverConfigItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: true
	                },
	                columns:driverConfigItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:driverConfigItemsHandsontableHelper.colHeaders,//显示列头
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
	                    if (visualColIndex ==1) {
							cellProperties.readOnly = true;
							cellProperties.renderer = driverConfigItemsHandsontableHelper.addBoldBg;
		                }
	                    return cellProperties;
	                }
	        	});
	        }
	        //保存数据
	        driverConfigItemsHandsontableHelper.saveData = function () {}
	        driverConfigItemsHandsontableHelper.clearContainer = function () {
	        	driverConfigItemsHandsontableHelper.AllData = [];
	        }
	        return driverConfigItemsHandsontableHelper;
	    }
};

function CreateKafkaConfigInfoTable(){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getKafkaDriverConfigData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			
			
			if(kafkaDriverConfigHandsontableHelper==null){
				CreateKafkaConfigItemsInfoTable(result);
			}else{
				kafkaDriverConfigHandsontableHelper.hot.loadData(result.totalRoot);
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
    		columns+="{data:'"+result.columns[i].dataIndex+"',type:'numeric',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_NotNull(val, callback,this.row, this.col,kafkaDriverConfigHandsontableHelper);}}";
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
	kafkaDriverConfigHandsontableHelper = KafkaDriverConfigHandsontableHelper.createNew("ScadaKafkaConfigTableInfoDiv_id","ScadaKafkaConfigTableInfoContainer");
	kafkaDriverConfigHandsontableHelper.getData(result);
	kafkaDriverConfigHandsontableHelper.columns=Ext.JSON.decode(columns);
	kafkaDriverConfigHandsontableHelper.createTable();
};


var KafkaDriverConfigHandsontableHelper = {
	    createNew: function (divid, containerid) {
	        var kafkaDriverConfigHandsontableHelper = {};
	        kafkaDriverConfigHandsontableHelper.get_data = {};
	        kafkaDriverConfigHandsontableHelper.hot = '';
	        kafkaDriverConfigHandsontableHelper.container = document.getElementById(divid);
	        kafkaDriverConfigHandsontableHelper.last_index = 0;
	        kafkaDriverConfigHandsontableHelper.calculation_type_computer = [];
	        kafkaDriverConfigHandsontableHelper.calculation_type_not_computer = [];
	        kafkaDriverConfigHandsontableHelper.editable = 0;
	        kafkaDriverConfigHandsontableHelper.sum = 0;
	        kafkaDriverConfigHandsontableHelper.editRecords = [];
	        kafkaDriverConfigHandsontableHelper.columns=[];
	        kafkaDriverConfigHandsontableHelper.my_data = [

	        ];
	        kafkaDriverConfigHandsontableHelper.updateArray = function () {
	            for (var i = 0; i < kafkaDriverConfigHandsontableHelper.sum; i++) {
	                kafkaDriverConfigHandsontableHelper.my_data.splice(i+1, 0, ['', '', '']);
	            }
	        }
	        kafkaDriverConfigHandsontableHelper.clearArray = function () {
	            kafkaDriverConfigHandsontableHelper.hot.loadData(kafkaDriverConfigHandsontableHelper.table_header);

	        }

	        kafkaDriverConfigHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
                td.style.fontWeight = 'bold';
//				td.style.fontSize = '13px';
				td.style.color = 'rgb(0, 0, 51)';
				td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	        }
			
			kafkaDriverConfigHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
				Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.fontWeight = 'bold';
		        td.style.fontSize = '13px';
		        td.style.fontFamily = 'SimSun';
//		        td.style.height = '50px';  
	        }
			
			kafkaDriverConfigHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
	             td.style.fontWeight = 'bold';
		         td.style.fontSize = '5px';
		         td.style.fontFamily = 'SimHei';
	        }
			

	        kafkaDriverConfigHandsontableHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        kafkaDriverConfigHandsontableHelper.addContentReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(184, 184, 184)';
	        }
	        
	        
	        
	        kafkaDriverConfigHandsontableHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        kafkaDriverConfigHandsontableHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        kafkaDriverConfigHandsontableHelper.createTable = function () {
//	            kafkaDriverConfigHandsontableHelper.container.innerHTML = "";
	            kafkaDriverConfigHandsontableHelper.hot = new Handsontable(kafkaDriverConfigHandsontableHelper.container, {
	                data: kafkaDriverConfigHandsontableHelper.my_data,
//	                fixedRowsTop:0, //固定顶部多少行不能垂直滚动
//	                fixedRowsBottom: 0,//固定底部多少行不能垂直滚动
//	                fixedColumnsLeft:0, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
//					rowHeights: [50],
					colWidths:[1,6,10],
					stretchH: 'all',
					columns:kafkaDriverConfigHandsontableHelper.columns,
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
							cellProperties.renderer = kafkaDriverConfigHandsontableHelper.addContentReadOnlyBg;
							cellProperties.readOnly = true;
		                }
	                    return cellProperties;
	                },
	                afterChange:function(changes, source){}
	            });
	        }
	        kafkaDriverConfigHandsontableHelper.getData = function (data) {
	            kafkaDriverConfigHandsontableHelper.get_data = data;
	            
	            
	            var totalRoot = data.totalRoot;
	            kafkaDriverConfigHandsontableHelper.sum = totalRoot.length;
	            kafkaDriverConfigHandsontableHelper.updateArray();
	            kafkaDriverConfigHandsontableHelper.my_data=totalRoot;
	            
	        }
	        var init = function () {
	        }
	        init();
	        return kafkaDriverConfigHandsontableHelper;
	    }
	};

function CreateTcpServerConfigInfoTable(isNew){
	if(isNew&&tcpServerConfigHandsontableHelper!=null){
		tcpServerConfigHandsontableHelper.clearContainer();
		tcpServerConfigHandsontableHelper.hot.destroy();
		tcpServerConfigHandsontableHelper=null;
	}
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getTcpServerConfigData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(tcpServerConfigHandsontableHelper==null){
				tcpServerConfigHandsontableHelper = TcpServerConfigHandsontableHelper.createNew("TcpServerConfigInfoInfoDiv_id");
				var colHeaders="[";
		        var columns="[";
		       
	            for(var i=0;i<result.columns.length;i++){
	            	colHeaders+="'"+result.columns[i].header+"'";
	            	columns+="{data:'"+result.columns[i].dataIndex+"'}";
	            	if(i<result.columns.length-1){
	            		colHeaders+=",";
	                	columns+=",";
	            	}
	            }
	            colHeaders+="]";
	        	columns+="]";
	        	tcpServerConfigHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
	        	tcpServerConfigHandsontableHelper.columns=Ext.JSON.decode(columns);
	        	tcpServerConfigHandsontableHelper.createTable(result.totalRoot);
			}else{
				tcpServerConfigHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
            
        }
	});
};

var TcpServerConfigHandsontableHelper = {
	    createNew: function (divid) {
	        var tcpServerConfigHandsontableHelper = {};
	        tcpServerConfigHandsontableHelper.hot = '';
	        tcpServerConfigHandsontableHelper.divid = divid;
	        tcpServerConfigHandsontableHelper.validresult=true;//数据校验
	        tcpServerConfigHandsontableHelper.colHeaders=[];
	        tcpServerConfigHandsontableHelper.columns=[];
	        
	        tcpServerConfigHandsontableHelper.AllData={};
	        tcpServerConfigHandsontableHelper.updatelist=[];
	        tcpServerConfigHandsontableHelper.delidslist=[];
	        tcpServerConfigHandsontableHelper.insertlist=[];
	        tcpServerConfigHandsontableHelper.editWellNameList=[];
	        
	        tcpServerConfigHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        tcpServerConfigHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(184, 184, 184)';
	        }
	        
	        tcpServerConfigHandsontableHelper.createTable = function (data) {
	        	$('#'+tcpServerConfigHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+tcpServerConfigHandsontableHelper.divid);
	        	tcpServerConfigHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: true
	                },
	                columns:tcpServerConfigHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:tcpServerConfigHandsontableHelper.colHeaders,//显示列头
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
							cellProperties.renderer = tcpServerConfigHandsontableHelper.addBoldBg;
		                }
	                    return cellProperties;
	                },
	                afterSelection: function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	
	                },
	                afterDestroy: function() {
	                	
	                },
	                beforeRemoveRow: function (index, amount) {
	                	
	                },
	                afterChange: function (changes, source) {
	                	
	                }
	        	});
	        }
	      //插入的数据的获取
	        tcpServerConfigHandsontableHelper.insertExpressCount=function() {}
	        //保存数据
	        tcpServerConfigHandsontableHelper.saveData = function () {}
	        
	      //删除的优先级最高
	        tcpServerConfigHandsontableHelper.delExpressCount=function(ids) {}

	        //updatelist数据更新
	        tcpServerConfigHandsontableHelper.screening=function() {}
	        
	      //更新数据
	        tcpServerConfigHandsontableHelper.updateExpressCount=function(data) {}
	        
	        tcpServerConfigHandsontableHelper.clearContainer = function () {
	        	tcpServerConfigHandsontableHelper.AllData = {};
	        	tcpServerConfigHandsontableHelper.updatelist = [];
	        	tcpServerConfigHandsontableHelper.delidslist = [];
	        	tcpServerConfigHandsontableHelper.insertlist = [];
	        	tcpServerConfigHandsontableHelper.editWellNameList=[];
	        }
	        
	        return tcpServerConfigHandsontableHelper;
	    }
};



function SaveModbusDriverConfigData(){
	var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ScadaDriverModbusConfigSelectRow_Id").getValue();
	if(ScadaDriverModbusConfigSelectRow!=''){
		var driverConfigSaveData=[];
		var driverConfigItemsSaveData=[];
		var driverConfigData=driverConfigHandsontableHelper.hot.getDataAtRow(ScadaDriverModbusConfigSelectRow);
		var driverConfigItemsData=driverConfigItemsHandsontableHelper.hot.getData();
		var tcpServerConfigItemsData=tcpServerConfigHandsontableHelper.hot.getData();
		var configInfo={};
		configInfo.TcpServerPort=tcpServerConfigItemsData[0][2];
		configInfo.TcpServerHeartbeatPrefix=tcpServerConfigItemsData[1][2];
		configInfo.TcpServerHeartbeatSuffix=tcpServerConfigItemsData[2][2];
		configInfo.DriverName=driverConfigData[1];
		configInfo.DataConfig=[];
		for(var i=0;i<driverConfigItemsData.length;i++){
			var item={};
			item.Name=driverConfigItemsData[i][1];
			item.Address=parseInt(driverConfigItemsData[i][2]);
			item.Length=parseInt(driverConfigItemsData[i][3]);
			item.DataType=driverConfigItemsData[i][4];
			item.Readonly=driverConfigItemsData[i][5];
			item.Unit=driverConfigItemsData[i][6];
			item.Zoom=parseFloat(driverConfigItemsData[i][7]);
			item.Initiative=driverConfigItemsData[i][8];
			configInfo.DataConfig.push(item);
		}
//		alert(JSON.stringify(configInfo));
//		alert(configInfo.DriverName);
		
		Ext.Ajax.request({
    		method:'POST',
    		url:context + '/acquisitionUnitManagerController/saveModbusDriverConfigData',
    		success:function(response) {
    			var data=Ext.JSON.decode(response.responseText);
    			if (data.success) {
                	Ext.MessageBox.alert("信息","保存成功");
                	CreateDriverConfigInfoTable();
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
	}
};

function SaveScadaKafkaDriverConfigData(){

	var driverConfigData=kafkaDriverConfigHandsontableHelper.hot.getData();
	var configInfo={};
	var KafkaData={};
	KafkaData.Server={};
	KafkaData.Server.IP=driverConfigData[2][2];
	KafkaData.Server.Port=parseInt(driverConfigData[3][2]);
	
	KafkaData.Topic={};
	KafkaData.Topic.Up={};
	KafkaData.Topic.Up.NormData=driverConfigData[5][2];
	KafkaData.Topic.Up.RawData=driverConfigData[6][2];
	KafkaData.Topic.Up.Config=driverConfigData[7][2];
	KafkaData.Topic.Up.Model=driverConfigData[8][2];
	KafkaData.Topic.Up.Freq=driverConfigData[9][2];
	KafkaData.Topic.Up.RTC=driverConfigData[10][2];
	KafkaData.Topic.Up.Online=driverConfigData[11][2];
	KafkaData.Topic.Up.RunStatus=driverConfigData[12][2];
	
	KafkaData.Topic.Down={};
	KafkaData.Topic.Down.Model=driverConfigData[14][2];
	KafkaData.Topic.Down.Model_FluidPVT=driverConfigData[15][2];
	KafkaData.Topic.Down.Model_Reservoir=driverConfigData[16][2];
	KafkaData.Topic.Down.Model_WellboreTrajectory=driverConfigData[17][2];
	KafkaData.Topic.Down.Model_RodString=driverConfigData[18][2];
	KafkaData.Topic.Down.Model_TubingString=driverConfigData[19][2];
	KafkaData.Topic.Down.Model_Pump=driverConfigData[20][2];
	KafkaData.Topic.Down.Model_TailtubingString=driverConfigData[21][2];
	KafkaData.Topic.Down.Model_CasingString=driverConfigData[22][2];
	KafkaData.Topic.Down.Model_PumpingUnit=driverConfigData[23][2];
	KafkaData.Topic.Down.Model_SystemEfficiency=driverConfigData[24][2];
	KafkaData.Topic.Down.Model_Production=driverConfigData[25][2];
	KafkaData.Topic.Down.Model_FeatureDB=driverConfigData[26][2];
	KafkaData.Topic.Down.Model_CalculationMethod=driverConfigData[27][2];
	KafkaData.Topic.Down.Model_ManualIntervention=driverConfigData[28][2];
	KafkaData.Topic.Down.Config=driverConfigData[29][2];
	KafkaData.Topic.Down.StartRPC=driverConfigData[30][2];
	KafkaData.Topic.Down.StopRPC=driverConfigData[31][2];
	KafkaData.Topic.Down.DogRestart=driverConfigData[32][2];
	KafkaData.Topic.Down.Freq=driverConfigData[33][2];
	KafkaData.Topic.Down.RTC=driverConfigData[34][2];
	KafkaData.Topic.Down.Req=driverConfigData[35][2];
	KafkaData.Topic.Down.A9=driverConfigData[36][2];
	KafkaData.Topic.Down.AC=driverConfigData[37][2];

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