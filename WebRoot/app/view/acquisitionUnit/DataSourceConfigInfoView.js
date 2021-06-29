var dataSourceConfigHandsontableHelper=null;
var dataSourceConfigColumnsHandsontableHelper=null;
Ext.define('AP.view.acquisitionUnit.DataSourceConfigInfoView', {
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
        				SavedDtaSourceConfigData();
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
		url:context + '/acquisitionUnitManagerController/getDataSourceConfigData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(dataSourceConfigHandsontableHelper==null){
				dataSourceConfigHandsontableHelper = DataSourceConfigHandsontableHelper.createNew("DataSourceConfigDBInfoDiv_id");
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
	                    
	                    if(visualRowIndex==2 && visualColIndex ==2){
	                    	cellProperties.type='dropdown';
	                    	cellProperties.strict=true;
	                    	cellProperties.allowInvalid=false;
	                    	cellProperties.source=['oracle','sql server'];
	                    }
	                    
	                    if(visualRowIndex==3 && visualColIndex ==2){
	                    	cellProperties.type='dropdown';
	                    	cellProperties.strict=true;
	                    	cellProperties.allowInvalid=false;
	                    	cellProperties.source=['10g','11g','12c','19c'];
	                    }
	                    return cellProperties;
	                },
	                afterDestroy: function() {
	                    // 移除事件
//	                    Handsontable.Dom.removeEvent(save, 'click', saveData);
//	                    loadDataTable();
	                },
	                beforeRemoveRow: function (index, amount) {},
	                afterChange: function (changes, source) {}
	        	});
	        }
	      //插入的数据的获取
	        dataSourceConfigHandsontableHelper.insertExpressCount=function() {}
	        //保存数据
	        dataSourceConfigHandsontableHelper.saveData = function () {}
	        
	      //删除的优先级最高
	        dataSourceConfigHandsontableHelper.delExpressCount=function(ids) {}

	        //updatelist数据更新
	        dataSourceConfigHandsontableHelper.screening=function() {}
	        
	      //更新数据
	        dataSourceConfigHandsontableHelper.updateExpressCount=function(data) {}
	        
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
	            dataSourceConfigColumnsHandsontableHelper.my_data=columnRoot;
	        }
	        var init = function () {
	        }
	        init();
	        return dataSourceConfigColumnsHandsontableHelper;
	    }
	};

function SavedDtaSourceConfigData(){
	if(dataSourceConfigHandsontableHelper==null || dataSourceConfigColumnsHandsontableHelper==null){
		return;
	}
	var dataSourceConfig=dataSourceConfigHandsontableHelper.hot.getData();
	var dataSourceColumnsConfig=dataSourceConfigColumnsHandsontableHelper.hot.getData();
	var dataSourceConfigData={};
	
	dataSourceConfigData.IP=dataSourceConfig[0][2];
	dataSourceConfigData.Port=dataSourceConfig[1][2];
	dataSourceConfigData.Type=dataSourceConfig[2][2];
	dataSourceConfigData.Version=dataSourceConfig[3][2];
	dataSourceConfigData.InstanceName=dataSourceConfig[4][2];
	dataSourceConfigData.User=dataSourceConfig[5][2];
	dataSourceConfigData.Password=dataSourceConfig[6][2];
	
	dataSourceConfigData.DiagramTable={};
	dataSourceConfigData.DiagramTable.Name=dataSourceColumnsConfig[0][2];
	dataSourceConfigData.DiagramTable.Columns={};
	dataSourceConfigData.DiagramTable.Columns.WellName={};
	dataSourceConfigData.DiagramTable.Columns.WellName.Column=dataSourceColumnsConfig[2][3];
	dataSourceConfigData.DiagramTable.Columns.WellName.Type=dataSourceColumnsConfig[2][4];
	dataSourceConfigData.DiagramTable.Columns.AcqTime={};
	dataSourceConfigData.DiagramTable.Columns.AcqTime.Column=dataSourceColumnsConfig[3][3];
	dataSourceConfigData.DiagramTable.Columns.AcqTime.Type=dataSourceColumnsConfig[3][4];
	dataSourceConfigData.DiagramTable.Columns.Stroke={};
	dataSourceConfigData.DiagramTable.Columns.Stroke.Column=dataSourceColumnsConfig[4][3];
	dataSourceConfigData.DiagramTable.Columns.Stroke.Type=dataSourceColumnsConfig[4][4];
	dataSourceConfigData.DiagramTable.Columns.SPM={};
	dataSourceConfigData.DiagramTable.Columns.SPM.Column=dataSourceColumnsConfig[5][3];
	dataSourceConfigData.DiagramTable.Columns.SPM.Type=dataSourceColumnsConfig[5][4];
	dataSourceConfigData.DiagramTable.Columns.PointCount={};
	dataSourceConfigData.DiagramTable.Columns.PointCount.Column=dataSourceColumnsConfig[6][3];
	dataSourceConfigData.DiagramTable.Columns.PointCount.Type=dataSourceColumnsConfig[6][4];
	dataSourceConfigData.DiagramTable.Columns.S={};
	dataSourceConfigData.DiagramTable.Columns.S.Column=dataSourceColumnsConfig[7][3];
	dataSourceConfigData.DiagramTable.Columns.S.Type=dataSourceColumnsConfig[7][4];
	dataSourceConfigData.DiagramTable.Columns.F={};
	dataSourceConfigData.DiagramTable.Columns.F.Column=dataSourceColumnsConfig[8][3];
	dataSourceConfigData.DiagramTable.Columns.F.Type=dataSourceColumnsConfig[8][4];
	dataSourceConfigData.DiagramTable.Columns.I={};
	dataSourceConfigData.DiagramTable.Columns.I.Column=dataSourceColumnsConfig[9][3];
	dataSourceConfigData.DiagramTable.Columns.I.Type=dataSourceColumnsConfig[9][4];
	dataSourceConfigData.DiagramTable.Columns.KWatt={};
	dataSourceConfigData.DiagramTable.Columns.KWatt.Column=dataSourceColumnsConfig[10][3];
	dataSourceConfigData.DiagramTable.Columns.KWatt.Type=dataSourceColumnsConfig[10][4];
	
	dataSourceConfigData.ReservoirTable={};
	dataSourceConfigData.ReservoirTable.Name=dataSourceColumnsConfig[11][2];
	dataSourceConfigData.ReservoirTable.Columns={};
	dataSourceConfigData.ReservoirTable.Columns.WellName={};
	dataSourceConfigData.ReservoirTable.Columns.WellName.Column=dataSourceColumnsConfig[13][3];
	dataSourceConfigData.ReservoirTable.Columns.WellName.Type=dataSourceColumnsConfig[13][4];
	dataSourceConfigData.ReservoirTable.Columns.Depth={};
	dataSourceConfigData.ReservoirTable.Columns.Depth.Column=dataSourceColumnsConfig[14][3];
	dataSourceConfigData.ReservoirTable.Columns.Depth.Type=dataSourceColumnsConfig[14][4];
	dataSourceConfigData.ReservoirTable.Columns.Temperature={};
	dataSourceConfigData.ReservoirTable.Columns.Temperature.Column=dataSourceColumnsConfig[15][3];
	dataSourceConfigData.ReservoirTable.Columns.Temperature.Type=dataSourceColumnsConfig[15][4];
	
	dataSourceConfigData.RodStringTable={};
	dataSourceConfigData.RodStringTable.Name=dataSourceColumnsConfig[16][2];
	dataSourceConfigData.RodStringTable.Columns={};
	dataSourceConfigData.RodStringTable.Columns.WellName={};
	dataSourceConfigData.RodStringTable.Columns.WellName.Column=dataSourceColumnsConfig[18][3];
	dataSourceConfigData.RodStringTable.Columns.WellName.Type=dataSourceColumnsConfig[18][4];
	dataSourceConfigData.RodStringTable.Columns.Grade1={};
	dataSourceConfigData.RodStringTable.Columns.Grade1.Column=dataSourceColumnsConfig[19][3];
	dataSourceConfigData.RodStringTable.Columns.Grade1.Type=dataSourceColumnsConfig[19][4];
	dataSourceConfigData.RodStringTable.Columns.OutsideDiameter1={};
	dataSourceConfigData.RodStringTable.Columns.OutsideDiameter1.Column=dataSourceColumnsConfig[20][3];
	dataSourceConfigData.RodStringTable.Columns.OutsideDiameter1.Type=dataSourceColumnsConfig[20][4];
	dataSourceConfigData.RodStringTable.Columns.InsideDiameter1={};
	dataSourceConfigData.RodStringTable.Columns.InsideDiameter1.Column=dataSourceColumnsConfig[21][3];
	dataSourceConfigData.RodStringTable.Columns.InsideDiameter1.Type=dataSourceColumnsConfig[21][4];
	dataSourceConfigData.RodStringTable.Columns.Length1={};
	dataSourceConfigData.RodStringTable.Columns.Length1.Column=dataSourceColumnsConfig[22][3];
	dataSourceConfigData.RodStringTable.Columns.Length1.Type=dataSourceColumnsConfig[22][4];
	dataSourceConfigData.RodStringTable.Columns.Grade2={};
	dataSourceConfigData.RodStringTable.Columns.Grade2.Column=dataSourceColumnsConfig[23][3];
	dataSourceConfigData.RodStringTable.Columns.Grade2.Type=dataSourceColumnsConfig[23][4];
	dataSourceConfigData.RodStringTable.Columns.OutsideDiameter2={};
	dataSourceConfigData.RodStringTable.Columns.OutsideDiameter2.Column=dataSourceColumnsConfig[24][3];
	dataSourceConfigData.RodStringTable.Columns.OutsideDiameter2.Type=dataSourceColumnsConfig[24][4];
	dataSourceConfigData.RodStringTable.Columns.InsideDiameter2={};
	dataSourceConfigData.RodStringTable.Columns.InsideDiameter2.Column=dataSourceColumnsConfig[25][3];
	dataSourceConfigData.RodStringTable.Columns.InsideDiameter2.Type=dataSourceColumnsConfig[25][4];
	dataSourceConfigData.RodStringTable.Columns.Length2={};
	dataSourceConfigData.RodStringTable.Columns.Length2.Column=dataSourceColumnsConfig[26][3];
	dataSourceConfigData.RodStringTable.Columns.Length2.Type=dataSourceColumnsConfig[26][4];
	dataSourceConfigData.RodStringTable.Columns.Grade3={};
	dataSourceConfigData.RodStringTable.Columns.Grade3.Column=dataSourceColumnsConfig[27][3];
	dataSourceConfigData.RodStringTable.Columns.Grade3.Type=dataSourceColumnsConfig[27][4];
	dataSourceConfigData.RodStringTable.Columns.OutsideDiameter3={};
	dataSourceConfigData.RodStringTable.Columns.OutsideDiameter3.Column=dataSourceColumnsConfig[28][3];
	dataSourceConfigData.RodStringTable.Columns.OutsideDiameter3.Type=dataSourceColumnsConfig[28][4];
	dataSourceConfigData.RodStringTable.Columns.InsideDiameter3={};
	dataSourceConfigData.RodStringTable.Columns.InsideDiameter3.Column=dataSourceColumnsConfig[29][3];
	dataSourceConfigData.RodStringTable.Columns.InsideDiameter3.Type=dataSourceColumnsConfig[29][4];
	dataSourceConfigData.RodStringTable.Columns.Length3={};
	dataSourceConfigData.RodStringTable.Columns.Length3.Column=dataSourceColumnsConfig[30][3];
	dataSourceConfigData.RodStringTable.Columns.Length3.Type=dataSourceColumnsConfig[30][4];
	
	dataSourceConfigData.TubingStringTable={};
	dataSourceConfigData.TubingStringTable.Name=dataSourceColumnsConfig[31][2];
	dataSourceConfigData.TubingStringTable.Columns={};
	dataSourceConfigData.TubingStringTable.Columns.WellName={};
	dataSourceConfigData.TubingStringTable.Columns.WellName.Column=dataSourceColumnsConfig[33][3];
	dataSourceConfigData.TubingStringTable.Columns.WellName.Type=dataSourceColumnsConfig[33][4];
	dataSourceConfigData.TubingStringTable.Columns.InsideDiameter={};
	dataSourceConfigData.TubingStringTable.Columns.InsideDiameter.Column=dataSourceColumnsConfig[34][3];
	dataSourceConfigData.TubingStringTable.Columns.InsideDiameter.Type=dataSourceColumnsConfig[34][4];
	
	dataSourceConfigData.CasingStringTable={};
	dataSourceConfigData.CasingStringTable.Name=dataSourceColumnsConfig[35][2];
	dataSourceConfigData.CasingStringTable.Columns={};
	dataSourceConfigData.CasingStringTable.Columns.WellName={};
	dataSourceConfigData.CasingStringTable.Columns.WellName.Column=dataSourceColumnsConfig[37][3];
	dataSourceConfigData.CasingStringTable.Columns.WellName.Type=dataSourceColumnsConfig[37][4];
	dataSourceConfigData.CasingStringTable.Columns.InsideDiameter={};
	dataSourceConfigData.CasingStringTable.Columns.InsideDiameter.Column=dataSourceColumnsConfig[38][3];
	dataSourceConfigData.CasingStringTable.Columns.InsideDiameter.Type=dataSourceColumnsConfig[38][4];
	
	dataSourceConfigData.PumpTable={};
	dataSourceConfigData.PumpTable.Name=dataSourceColumnsConfig[39][2];
	dataSourceConfigData.PumpTable.Columns={};
	dataSourceConfigData.PumpTable.Columns.WellName={};
	dataSourceConfigData.PumpTable.Columns.WellName.Column=dataSourceColumnsConfig[41][3];
	dataSourceConfigData.PumpTable.Columns.WellName.Type=dataSourceColumnsConfig[41][4];
	dataSourceConfigData.PumpTable.Columns.PumpGrade={};
	dataSourceConfigData.PumpTable.Columns.PumpGrade.Column=dataSourceColumnsConfig[42][3];
	dataSourceConfigData.PumpTable.Columns.PumpGrade.Type=dataSourceColumnsConfig[42][4];
	dataSourceConfigData.PumpTable.Columns.PumpBoreDiameter={};
	dataSourceConfigData.PumpTable.Columns.PumpBoreDiameter.Column=dataSourceColumnsConfig[43][3];
	dataSourceConfigData.PumpTable.Columns.PumpBoreDiameter.Type=dataSourceColumnsConfig[43][4];
	dataSourceConfigData.PumpTable.Columns.PlungerLength={};
	dataSourceConfigData.PumpTable.Columns.PlungerLength.Column=dataSourceColumnsConfig[44][3];
	dataSourceConfigData.PumpTable.Columns.PlungerLength.Type=dataSourceColumnsConfig[44][4];
	
	dataSourceConfigData.ProductionTable={};
	dataSourceConfigData.ProductionTable.Name=dataSourceColumnsConfig[45][2];
	dataSourceConfigData.ProductionTable.Columns={};
	dataSourceConfigData.ProductionTable.Columns.WellName={};
	dataSourceConfigData.ProductionTable.Columns.WellName.Column=dataSourceColumnsConfig[47][3];
	dataSourceConfigData.ProductionTable.Columns.WellName.Type=dataSourceColumnsConfig[47][4];
	dataSourceConfigData.ProductionTable.Columns.WaterCut={};
	dataSourceConfigData.ProductionTable.Columns.WaterCut.Column=dataSourceColumnsConfig[48][3];
	dataSourceConfigData.ProductionTable.Columns.WaterCut.Type=dataSourceColumnsConfig[48][4];
	dataSourceConfigData.ProductionTable.Columns.ProductionGasOilRatio={};
	dataSourceConfigData.ProductionTable.Columns.ProductionGasOilRatio.Column=dataSourceColumnsConfig[49][3];
	dataSourceConfigData.ProductionTable.Columns.ProductionGasOilRatio.Type=dataSourceColumnsConfig[49][4];
	dataSourceConfigData.ProductionTable.Columns.TubingPressure={};
	dataSourceConfigData.ProductionTable.Columns.TubingPressure.Column=dataSourceColumnsConfig[50][3];
	dataSourceConfigData.ProductionTable.Columns.TubingPressure.Type=dataSourceColumnsConfig[50][4];
	dataSourceConfigData.ProductionTable.Columns.CasingPressure={};
	dataSourceConfigData.ProductionTable.Columns.CasingPressure.Column=dataSourceColumnsConfig[51][3];
	dataSourceConfigData.ProductionTable.Columns.CasingPressure.Type=dataSourceColumnsConfig[51][4];
	dataSourceConfigData.ProductionTable.Columns.WellHeadFluidTemperature={};
	dataSourceConfigData.ProductionTable.Columns.WellHeadFluidTemperature.Column=dataSourceColumnsConfig[52][3];
	dataSourceConfigData.ProductionTable.Columns.WellHeadFluidTemperature.Type=dataSourceColumnsConfig[52][4];
	dataSourceConfigData.ProductionTable.Columns.ProducingfluidLevel={};
	dataSourceConfigData.ProductionTable.Columns.ProducingfluidLevel.Column=dataSourceColumnsConfig[53][3];
	dataSourceConfigData.ProductionTable.Columns.ProducingfluidLevel.Type=dataSourceColumnsConfig[53][4];
	dataSourceConfigData.ProductionTable.Columns.PumpSettingDepth={};
	dataSourceConfigData.ProductionTable.Columns.PumpSettingDepth.Column=dataSourceColumnsConfig[54][3];
	dataSourceConfigData.ProductionTable.Columns.PumpSettingDepth.Type=dataSourceColumnsConfig[54][4];
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/saveDataSourceConfigData',
		success:function(response) {
			var data=Ext.JSON.decode(response.responseText);
			if (data.success) {
            	Ext.MessageBox.alert("信息","保存成功");
//            	CreateKafkaConfigInfoTable();
            } else {
            	Ext.MessageBox.alert("信息","数据保存失败");

            }
		},
		failure:function(){
			Ext.MessageBox.alert("信息","请求失败");
		},
		params: {
			dataSourceConfigData:JSON.stringify(dataSourceConfigData)
        }
	}); 
}
