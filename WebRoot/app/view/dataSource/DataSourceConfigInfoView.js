var dataSourceConfigHandsontableHelper=null;
var dataSourceConfigColumnsHandsontableHelper=null;
Ext.define('AP.view.dataSource.DataSourceConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.dataSourceConfigInfoView',
    layout: "fit",
    id:'dataSourceConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	Ext.apply(me, {
    		items: [{
                layout: "border",
                border: false,
                tbar:['->',{
                	xtype: 'button',
        			pressed: true,
        			text: cosog.string.save,
        			iconCls: 'save',
        			handler: function (v, o) {
        			}
                }],
                items: [{
                    region: 'west',
                    title:'数据库信息',
                    border: false,
                    layout: 'fit',
                    width: '35%',
                    collapsible: true,
                    split: true,
                    html:'<div class="DataSourceConfigDBInfoContainer" style="width:100%;height:100%;"><div class="con" id="DataSourceConfigDBInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        	CreateDataSourceConfigDBInfoTable(true);
                        }
                    }
                }, {
                    region: 'center',
                    title:'数据表信息',
                    layout: 'fit',
                    html:'<div class="DataSourceConfigDiagramTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="DataSourceConfigDiagramTableInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        }
                    }
                }]
    		}],
    		listeners: {
    			beforeclose: function ( panel, eOpts) {
//    				if(dataSourceConfigHandsontableHelper!=null){
//    			        dataSourceConfigHandsontableHelper.clearContainer();
//    			        dataSourceConfigHandsontableHelper.hot.destroy();
//    			        dataSourceConfigHandsontableHelper=null;
//    				}
//    				if(dataSourceConfigColumnsHandsontableHelper!=null){
//    					dataSourceConfigColumnsHandsontableHelper.clearContainer();
//    					dataSourceConfigColumnsHandsontableHelper.hot.destroy();
//    					dataSourceConfigColumnsHandsontableHelper=null;
//    				}
    			},
    			afterrender: function ( panel, eOpts) {}
    		}
        });
        this.callParent(arguments);

    }
});

function CreateDataSourceConfigDBInfoTable(isNew){
	if(isNew&&dataSourceConfigHandsontableHelper!=null){
        dataSourceConfigHandsontableHelper.clearContainer();
        dataSourceConfigHandsontableHelper.hot.destroy();
        dataSourceConfigHandsontableHelper=null;
	}
	if(isNew&&dataSourceConfigColumnsHandsontableHelper!=null){
		dataSourceConfigColumnsHandsontableHelper.hot.destroy();
		dataSourceConfigColumnsHandsontableHelper=null;
	}
	
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/dataSourceConfigController/getDataSourceConfigData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(dataSourceConfigHandsontableHelper==null){
				dataSourceConfigHandsontableHelper = DataSourceConfigHandsontableHelper.createNew("DataSourceConfigDBInfoDiv_id");
				var colHeaders="[";
		        var columns="[";
		       
	            for(var i=0;i<result.columns.length;i++){
	            	 colHeaders+="'"+result.columns[i].header+"'";
	            	if(result.columns[i].dataIndex.toUpperCase()==="orgName".toUpperCase()){
	            		columns+="{data:'"+result.columns[i].dataIndex+"',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Org(val, callback,this.row, this.col,dataSourceConfigHandsontableHelper);}}";
	            	}else if(result.columns[i].dataIndex==="liftingTypeName"){
	            		if(pcpHidden){
	            			columns+="{data:'"+result.columns[i].dataIndex+"',type:'dropdown',strict:true,allowInvalid:false,source:['抽油机']}";
	            		}else{
	            			columns+="{data:'"+result.columns[i].dataIndex+"',type:'dropdown',strict:true,allowInvalid:false,source:['抽油机', '螺杆泵']}";
	            		}
	            	}else if(result.columns[i].dataIndex==="runtimeEfficiencySource"){
	            		if(pcpHidden){
	            			columns+="{data:'"+result.columns[i].dataIndex+"',type:'dropdown',strict:true,allowInvalid:false,source:['人工录入','DI信号', '电参计算']}";
	            		}else{
	            			columns+="{data:'"+result.columns[i].dataIndex+"',type:'dropdown',strict:true,allowInvalid:false,source:['人工录入','DI信号', '电参计算','转速计算']}";
	            		}
	            	}else if(result.columns[i].dataIndex==="driverName"){
	            		var source="[";
	            		for(var j=0;j<result.driverDropdownData.length;j++){
	            			source+="\'"+result.driverDropdownData[j]+"\'";
	            			if(j<result.driverDropdownData.length-1){
	            				source+=",";
	            			}
	            		}
	            		source+="]";
	            		columns+="{data:'"+result.columns[i].dataIndex+"',type:'dropdown',strict:true,allowInvalid:false,source:"+source+"}";
	            	}else if(result.columns[i].dataIndex==="acquisitionUnit"){
	            		var source="[";
	            		for(var j=0;j<result.unitDropdownData.length;j++){
	            			source+="\'"+result.unitDropdownData[j]+"\'";
	            			if(j<result.unitDropdownData.length-1){
	            				source+=",";
	            			}
	            		}
	            		source+="]";
	            		columns+="{data:'"+result.columns[i].dataIndex+"',type:'dropdown',strict:true,allowInvalid:false,source:"+source+"}";
	            	}else if(result.columns[i].dataIndex.toUpperCase()==="sortNum".toUpperCase()){
	            		columns+="{data:'"+result.columns[i].dataIndex+"',type:'numeric',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,dataSourceConfigHandsontableHelper);}}";
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
	        	dataSourceConfigHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
	        	dataSourceConfigHandsontableHelper.columns=Ext.JSON.decode(columns);
				dataSourceConfigHandsontableHelper.createTable(result.totalRoot);
			}else{
				dataSourceConfigHandsontableHelper.hot.loadData(result.totalRoot);
			}
			if(dataSourceConfigColumnsHandsontableHelper==null){
				CreateDataSourceConfigColumnsInfoTable(result);
			}else{
				dataSourceConfigColumnsHandsontableHelper.hot.loadData(result.columnRoot);
			}
			
			
			
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
            
        }
	});
};

var DataSourceConfigHandsontableHelper = {
	    createNew: function (divid) {
	        var dataSourceConfigHandsontableHelper = {};
	        dataSourceConfigHandsontableHelper.hot = '';
	        dataSourceConfigHandsontableHelper.divid = divid;
	        dataSourceConfigHandsontableHelper.validresult=true;//数据校验
	        dataSourceConfigHandsontableHelper.colHeaders=[];
	        dataSourceConfigHandsontableHelper.columns=[];
	        
	        dataSourceConfigHandsontableHelper.AllData={};
	        dataSourceConfigHandsontableHelper.updatelist=[];
	        dataSourceConfigHandsontableHelper.delidslist=[];
	        dataSourceConfigHandsontableHelper.insertlist=[];
	        dataSourceConfigHandsontableHelper.editWellNameList=[];
	        
	        dataSourceConfigHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        dataSourceConfigHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(184, 184, 184)';
	        }
	        
	        dataSourceConfigHandsontableHelper.createTable = function (data) {
	        	$('#'+dataSourceConfigHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+dataSourceConfigHandsontableHelper.divid);
	        	dataSourceConfigHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: true
	                },
	                columns:dataSourceConfigHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:dataSourceConfigHandsontableHelper.colHeaders,//显示列头
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
							cellProperties.renderer = dataSourceConfigHandsontableHelper.addBoldBg;
		                }
	                    return cellProperties;
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
	                            var rowdata = dataSourceConfigHandsontableHelper.hot.getDataAtRow(i);
	                            ids.push(rowdata[0]);
	                        }
	                        dataSourceConfigHandsontableHelper.delExpressCount(ids);
	                        dataSourceConfigHandsontableHelper.screening();
	                    }
	                },
	                afterChange: function (changes, source) {
	                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
	                    
	                    if (changes != null) {
	                    	for(var i=0;i<changes.length;i++){
	                    		var params = [];
	                    		var index = changes[i][0]; //行号码
		                        var rowdata = dataSourceConfigHandsontableHelper.hot.getDataAtRow(index);
		                        params.push(rowdata[0]);
		                        params.push(changes[i][1]);
		                        params.push(changes[i][2]);
		                        params.push(changes[i][3]);
		                        
		                        if("edit"==source&&params[1]=="wellName"){//编辑井号单元格
		                        	var data="{\"oldWellName\":\""+params[2]+"\",\"newWellName\":\""+params[3]+"\"}";
		                        	dataSourceConfigHandsontableHelper.editWellNameList.push(Ext.JSON.decode(data));
		                        }

		                        //仅当单元格发生改变的时候,id!=null,说明是更新
		                        if (params[2] != params[3] && params[0] != null && params[0] >0) {
		                        	var data="{";
		                        	for(var j=0;j<dataSourceConfigHandsontableHelper.columns.length;j++){
		                        		data+=dataSourceConfigHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
		                        		if(j<dataSourceConfigHandsontableHelper.columns.length-1){
		                        			data+=","
		                        		}
		                        	}
		                        	data+="}"
		                            dataSourceConfigHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
		                        }
	                    	}
	                        
	                    }
	                }
	        	});
	        }
	      //插入的数据的获取
	        dataSourceConfigHandsontableHelper.insertExpressCount=function() {
	            var idsdata = dataSourceConfigHandsontableHelper.hot.getDataAtCol(0); //所有的id
	            for (var i = 0; i < idsdata.length; i++) {
	                //id=null时,是插入数据,此时的i正好是行号
	                if (idsdata[i] == null||idsdata[i]<0) {
	                    //获得id=null时的所有数据封装进data
	                    var rowdata = dataSourceConfigHandsontableHelper.hot.getDataAtRow(i);
	                    //var collength = hot.countCols();
	                    if (rowdata != null) {
	                    	var data="{";
                        	for(var j=0;j<dataSourceConfigHandsontableHelper.columns.length;j++){
                        		data+=dataSourceConfigHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
                        		if(j<dataSourceConfigHandsontableHelper.columns.length-1){
                        			data+=","
                        		}
                        	}
                        	data+="}"
	                        dataSourceConfigHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
	                    }
	                }
	            }
	            if (dataSourceConfigHandsontableHelper.insertlist.length != 0) {
	            	dataSourceConfigHandsontableHelper.AllData.insertlist = dataSourceConfigHandsontableHelper.insertlist;
	            }
	        }
	        //保存数据
	        dataSourceConfigHandsontableHelper.saveData = function () {
	        	
	        	var IframeViewSelection  = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
	        	if(IframeViewSelection.length>0&&IframeViewSelection[0].isLeaf()){
	        		//插入的数据的获取
		        	dataSourceConfigHandsontableHelper.insertExpressCount();
		        	var orgId=IframeViewSelection[0].data.orgId;
		            if (JSON.stringify(dataSourceConfigHandsontableHelper.AllData) != "{}" && dataSourceConfigHandsontableHelper.validresult) {
		            	Ext.Ajax.request({
		            		method:'POST',
		            		url:context + '/wellInformationManagerController/saveWellHandsontableData',
		            		success:function(response) {
		            			rdata=Ext.JSON.decode(response.responseText);
		            			if (rdata.success) {
		                        	Ext.MessageBox.alert("信息","保存成功");
		                            //保存以后重置全局容器
		                            dataSourceConfigHandsontableHelper.clearContainer();
		                            CreateDataSourceConfigDBInfoTable();
		                        } else {
		                        	Ext.MessageBox.alert("信息","数据保存失败");

		                        }
		            		},
		            		failure:function(){
		            			Ext.MessageBox.alert("信息","请求失败");
		                        dataSourceConfigHandsontableHelper.clearContainer();
		            		},
		            		params: {
		                    	data: JSON.stringify(dataSourceConfigHandsontableHelper.AllData),
		                    	orgId:orgId
		                    }
		            	}); 
		            } else {
		                if (!dataSourceConfigHandsontableHelper.validresult) {
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
	        dataSourceConfigHandsontableHelper.delExpressCount=function(ids) {
	            //传入的ids.length不可能为0
	            $.each(ids, function (index, id) {
	                if (id != null) {
	                	dataSourceConfigHandsontableHelper.delidslist.push(id);
	                }
	            });
	            dataSourceConfigHandsontableHelper.AllData.delidslist = dataSourceConfigHandsontableHelper.delidslist;
	        }

	        //updatelist数据更新
	        dataSourceConfigHandsontableHelper.screening=function() {
	            if (dataSourceConfigHandsontableHelper.updatelist.length != 0 && dataSourceConfigHandsontableHelper.delidslist.lentgh != 0) {
	                for (var i = 0; i < dataSourceConfigHandsontableHelper.delidslist.length; i++) {
	                    for (var j = 0; j < dataSourceConfigHandsontableHelper.updatelist.length; j++) {
	                        if (dataSourceConfigHandsontableHelper.updatelist[j].id == dataSourceConfigHandsontableHelper.delidslist[i]) {
	                            //更新updatelist
	                        	dataSourceConfigHandsontableHelper.updatelist.splice(j, 1);
	                        }
	                    }
	                }
	                //把updatelist封装进AllData
	                dataSourceConfigHandsontableHelper.AllData.updatelist = dataSourceConfigHandsontableHelper.updatelist;
	            }
	        }
	        
	      //更新数据
	        dataSourceConfigHandsontableHelper.updateExpressCount=function(data) {
	            if (JSON.stringify(data) != "{}") {
	                var flag = true;
	                //判断记录是否存在,更新数据     
	                $.each(dataSourceConfigHandsontableHelper.updatelist, function (index, node) {
	                    if (node.id == data.id) {
	                        //此记录已经有了
	                        flag = false;
	                        //用新得到的记录替换原来的,不用新增
	                        dataSourceConfigHandsontableHelper.updatelist[index] = data;
	                    }
	                });
	                flag && dataSourceConfigHandsontableHelper.updatelist.push(data);
	                //封装
	                dataSourceConfigHandsontableHelper.AllData.updatelist = dataSourceConfigHandsontableHelper.updatelist;
	            }
	        }
	        
	        dataSourceConfigHandsontableHelper.clearContainer = function () {
	        	dataSourceConfigHandsontableHelper.AllData = {};
	        	dataSourceConfigHandsontableHelper.updatelist = [];
	        	dataSourceConfigHandsontableHelper.delidslist = [];
	        	dataSourceConfigHandsontableHelper.insertlist = [];
	        	dataSourceConfigHandsontableHelper.editWellNameList=[];
	        }
	        
	        return dataSourceConfigHandsontableHelper;
	    }
};

function CreateDataSourceConfigColumnsInfoTable(result){
	var columns="[";
	for(var i=0;i<result.diagramTableColumns.length;i++){
		if(result.diagramTableColumns[i].dataIndex==="columnType"){
			columns+="{data:'"+result.diagramTableColumns[i].dataIndex+"',type:'dropdown',strict:true,allowInvalid:false,source:['date','varchar2','number']}";
    	}else if(result.diagramTableColumns[i].dataIndex==="columnName"){
    		columns+="{data:'"+result.diagramTableColumns[i].dataIndex+"',type:'numeric',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_NotNull(val, callback,this.row, this.col,dataSourceConfigColumnsHandsontableHelper);}}";
        }else if(result.diagramTableColumns[i].dataIndex==="checked"){
    		columns+="{data:'"+result.diagramTableColumns[i].dataIndex+"',type:'checkbox'}";
    	}else{
    		columns+="{data:'"+result.diagramTableColumns[i].dataIndex+"'}";
    	}
		if(i<result.diagramTableColumns.length-1){
        	columns+=",";
    	}
	}
	columns+="]";
	dataSourceConfigColumnsHandsontableHelper = DataSourceConfigColumnsHandsontableHelper.createNew("DataSourceConfigDiagramTableInfoDiv_id","DataSourceConfigDiagramTableInfoContainer");
	dataSourceConfigColumnsHandsontableHelper.getData(result);
	dataSourceConfigColumnsHandsontableHelper.columns=Ext.JSON.decode(columns);
	dataSourceConfigColumnsHandsontableHelper.createTable();
};


var DataSourceConfigColumnsHandsontableHelper = {
	    createNew: function (divid, containerid) {
	        var dataSourceConfigColumnsHandsontableHelper = {};
	        dataSourceConfigColumnsHandsontableHelper.get_data = {};
	        dataSourceConfigColumnsHandsontableHelper.hot = '';
	        dataSourceConfigColumnsHandsontableHelper.container = document.getElementById(divid);
	        dataSourceConfigColumnsHandsontableHelper.last_index = 0;
	        dataSourceConfigColumnsHandsontableHelper.calculation_type_computer = [];
	        dataSourceConfigColumnsHandsontableHelper.calculation_type_not_computer = [];
	        dataSourceConfigColumnsHandsontableHelper.editable = 0;
	        dataSourceConfigColumnsHandsontableHelper.sum = 0;
	        dataSourceConfigColumnsHandsontableHelper.editRecords = [];
	        dataSourceConfigColumnsHandsontableHelper.columns=[];
	        dataSourceConfigColumnsHandsontableHelper.my_data = [
//	        	['功图数据表', '', '', ''],
//	        	['序号', '项', '字段名称','字段类型']
	        ];
	        dataSourceConfigColumnsHandsontableHelper.updateArray = function () {
	            for (var i = 0; i < dataSourceConfigColumnsHandsontableHelper.sum; i++) {
	                dataSourceConfigColumnsHandsontableHelper.my_data.splice(i + 2, 0, ['', '', '', '']);
	            }
	        }
	        dataSourceConfigColumnsHandsontableHelper.clearArray = function () {
	            dataSourceConfigColumnsHandsontableHelper.hot.loadData(dataSourceConfigColumnsHandsontableHelper.table_header);

	        }

	        dataSourceConfigColumnsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
                td.style.fontWeight = 'bold';
//				td.style.fontSize = '13px';
				td.style.color = 'rgb(0, 0, 51)';
				td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	        }
			
			dataSourceConfigColumnsHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
				Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.fontWeight = 'bold';
		        td.style.fontSize = '13px';
		        td.style.fontFamily = 'SimSun';
//		        td.style.height = '50px';  
	        }
			
			dataSourceConfigColumnsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
	             td.style.fontWeight = 'bold';
		         td.style.fontSize = '5px';
		         td.style.fontFamily = 'SimHei';
	        }
			

	        dataSourceConfigColumnsHandsontableHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        dataSourceConfigColumnsHandsontableHelper.addContentReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(184, 184, 184)';
	        }
	        
	        
	        
	        dataSourceConfigColumnsHandsontableHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        dataSourceConfigColumnsHandsontableHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        dataSourceConfigColumnsHandsontableHelper.createTable = function () {
	            dataSourceConfigColumnsHandsontableHelper.container.innerHTML = "";
	            dataSourceConfigColumnsHandsontableHelper.hot = new Handsontable(dataSourceConfigColumnsHandsontableHelper.container, {
	                data: dataSourceConfigColumnsHandsontableHelper.my_data,
//	                fixedRowsTop:0, //固定顶部多少行不能垂直滚动
//	                fixedRowsBottom: 0,//固定底部多少行不能垂直滚动
//	                fixedColumnsLeft:0, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
//					rowHeights: [50],
					colWidths:[2,6,10,10,10],
					stretchH: 'all',
					columns:dataSourceConfigColumnsHandsontableHelper.columns,
	                mergeCells: [
	                    {
	                        "row": 0,
	                        "col": 2,
	                        "rowspan": 1,
	                        "colspan": 3
	                    },{
	                        "row": 11,
	                        "col": 2,
	                        "rowspan": 1,
	                        "colspan": 3
	                    },{
	                        "row": 16,
	                        "col": 2,
	                        "rowspan": 1,
	                        "colspan": 3
	                    },{
	                        "row": 31,
	                        "col": 2,
	                        "rowspan": 1,
	                        "colspan": 3
	                    },{
	                        "row": 35,
	                        "col": 2,
	                        "rowspan": 1,
	                        "colspan": 3
	                    },{
	                        "row": 39,
	                        "col": 2,
	                        "rowspan": 1,
	                        "colspan": 3
	                    },{
	                        "row": 45,
	                        "col": 2,
	                        "rowspan": 1,
	                        "colspan": 3
	                    }],
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    // 表头
	                    if (visualRowIndex ==0 || visualRowIndex ==11 || visualRowIndex ==16
	                    		|| visualRowIndex ==31|| visualRowIndex ==35 || visualRowIndex ==39
	                    		|| visualRowIndex ==45) {
//	                    	cellProperties.renderer = dataSourceConfigColumnsHandsontableHelper.addSizeBg;
	                    	if(visualColIndex==1){
	                    		cellProperties.readOnly = true;
	                    	}
		                }
	                    if (visualColIndex ==1) {
	                    	cellProperties.readOnly = true;
		                }
	                    if (visualRowIndex ==1 || visualRowIndex ==12 || visualRowIndex ==17 
	                    		|| visualRowIndex ==32 || visualRowIndex ==36 || visualRowIndex ==40
	                    		|| visualRowIndex ==46) {
	                    	if(visualColIndex > 0 ){
	                    		cellProperties.readOnly = true;
	                    	}
//	                    	cellProperties.renderer = dataSourceConfigColumnsHandsontableHelper.addBoldBg;
	                    }
						
						if (visualColIndex==2
								&&( (visualRowIndex>=2&&visualRowIndex<=10) 
										|| (visualRowIndex>=13&&visualRowIndex<=15)
										|| (visualRowIndex>=18&&visualRowIndex<=30)  
										|| (visualRowIndex>=33&&visualRowIndex<=34) 
										|| (visualRowIndex>=37&&visualRowIndex<=38)
										|| (visualRowIndex>=41&&visualRowIndex<=44)
										|| (visualRowIndex>=47&&visualRowIndex<=54)
							)) {
							cellProperties.renderer = dataSourceConfigColumnsHandsontableHelper.addContentReadOnlyBg;
							cellProperties.readOnly = true;
		                }
	                    return cellProperties;
	                },
	                afterChange:function(changes, source){}
	            });
	        }
	        dataSourceConfigColumnsHandsontableHelper.getData = function (data) {
	            dataSourceConfigColumnsHandsontableHelper.get_data = data;
	            
	            
	            var columnRoot = data.columnRoot;
	            dataSourceConfigColumnsHandsontableHelper.sum = columnRoot.length;
	            dataSourceConfigColumnsHandsontableHelper.updateArray();
//	            for(var i=0;i<columnRoot.length;i++){
//	            	var columnInfo=[columnRoot[i].id,columnRoot[i].item,columnRoot[i].columnName,columnRoot[i].columnType];
//	            	dataSourceConfigColumnsHandsontableHelper.my_data.splice(i, 0, columnInfo);
//	            	
//	            	
////	            	var columnInfo=columnRoot[i];
////	            	dataSourceConfigColumnsHandsontableHelper.my_data[i + 2][0] = columnInfo.id;
////	                dataSourceConfigColumnsHandsontableHelper.my_data[i + 2][1] = columnInfo.item;
////	                dataSourceConfigColumnsHandsontableHelper.my_data[i + 2][2] = columnInfo.columnName;
////	                dataSourceConfigColumnsHandsontableHelper.my_data[i + 2][3] = columnInfo.columnType;
//	            }
	            dataSourceConfigColumnsHandsontableHelper.my_data=columnRoot;
//	            dataSourceConfigColumnsHandsontableHelper.my_data.splice(0, 0, ['功图数据', 'pc_fd_pumpjack_dyna_dia_t', '', ''],['序号', '字段名称', '字段代码','字段类型']);
//	            dataSourceConfigColumnsHandsontableHelper.my_data.splice(11, 0, ['油层数据', 'tbl_reservoir', '', ''],['序号', '字段名称', '字段代码','字段类型']);
//	            dataSourceConfigColumnsHandsontableHelper.my_data.splice(16, 0, ['杆柱组合数据', 'tbl_rodstring', '', ''],['序号', '字段名称', '字段代码','字段类型']);
//	            dataSourceConfigColumnsHandsontableHelper.my_data.splice(31, 0, ['油管数据', 'tbl_tubingstringr', '', ''],['序号', '字段名称', '字段代码','字段类型']);
//	            dataSourceConfigColumnsHandsontableHelper.my_data.splice(35, 0, ['套管数据', 'tbl_casingstring', '', ''],['序号', '字段名称', '字段代码','字段类型']);
//	            dataSourceConfigColumnsHandsontableHelper.my_data.splice(39, 0, ['泵数据', 'tbl_pump', '', ''],['序号', '字段名称', '字段代码','字段类型']);
//	            dataSourceConfigColumnsHandsontableHelper.my_data.splice(45, 0, ['动态数据', 'tbl_production', '', ''],['序号', '字段名称', '字段代码','字段类型']);
	            
	            
	            
//	            columnRoot.forEach(function (columnInfo, index) { 
//	            })
	        }
	        var init = function () {
	        }
	        init();
	        return dataSourceConfigColumnsHandsontableHelper;
	    }
	};
