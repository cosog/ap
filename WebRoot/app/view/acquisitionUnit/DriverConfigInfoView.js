var driverConfigHandsontableHelper=null;
var driverConfigItemsHandsontableHelper=null;
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
                    title:'驱动配置',
                    border: false,
                    layout: 'fit',
                    width: '40%',
                    collapsible: true,
                    split: true,
                    html:'<div class="DriverConfigInfoContainer" style="width:100%;height:100%;"><div class="con" id="DriverConfigInfoInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        	CreateDataSourceConfigDBInfoTable(true);
                        }
                    }
                }, {
                    region: 'center',
                    title:'数据项配置',
                    layout: 'fit',
                    html:'<div class="DriverItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="DriverItemsConfigTableInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        }
                    }
                }]
    		}],
    		listeners: {
    			beforeclose: function ( panel, eOpts) {},
    			afterrender: function ( panel, eOpts) {}
    		}
        });
        this.callParent(arguments);

    }
});

function CreateDataSourceConfigDBInfoTable(isNew){
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
			CreateDataSourceConfigColumnsInfoTable(row1[5])
//			if(driverConfigItemsHandsontableHelper==null){
//				CreateDataSourceConfigColumnsInfoTable(result);
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
	                    columns: [0,5],
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
	                	CreateDataSourceConfigColumnsInfoTable(row1[5]);
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
		                            CreateDataSourceConfigDBInfoTable();
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

function CreateDataSourceConfigColumnsInfoTable(data){
	driverConfigItemsHandsontableHelper = DriverConfigItemsHandsontableHelper.createNew("DriverItemsConfigTableInfoDiv_id");
	var colHeaders="['序号','名称','地址','寄存器长度','数据类型','单位换算系数']";
	var columns="[{data:'id'},{data:'item'},"
		 	+"{data:'address',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,driverConfigItemsHandsontableHelper);}},"
			+"{data:'length',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,driverConfigItemsHandsontableHelper);}}," 
			+"{data:'dataType',type:'dropdown',strict:true,allowInvalid:false,source:['整型', '实型','BCD码']}," 
			+"{data:'zoom',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,driverConfigItemsHandsontableHelper);}}]";
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
