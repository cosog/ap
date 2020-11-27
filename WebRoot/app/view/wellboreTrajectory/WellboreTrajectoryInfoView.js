var wellboreTrajectoryHandsontableHelper=null;
var wellboreTrajectoryDetailsHandsontableHelper=null;
Ext.define("AP.view.wellboreTrajectory.WellboreTrajectoryInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.wellboreTrajectoryInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;

        Ext.apply(me, {
            items: [{
                layout: "border",
                border: false,
                tbar: [{
        			xtype: 'textfield',
        			id: 'wellboreTrajectoryTableSelectedRow_Id',
        			value:0,
        			hidden:true
        		},'->', {
                    xtype: 'button',
                    text: cosog.string.save,
                    iconCls: 'save',
                    pressed: true,
                    handler: function (v, o) {
                    	saveWellboreTrajectoryData();
                    }
                }],
                items: [{
                	region: 'west',
                    layout: 'fit',
                    id: 'wellboreTrajectoryInfoPanel_Id',
                    width: '20%',
                    title: '井列表',
                    border: false,
                    collapsible: true, // 是否折叠
                    split: true, // 竖折叠条
                    html: '<div id="WellboreTrajectoryInfoDiv_Id" style="width:100%;height:100%;"></div>',
                    listeners: {
                    	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    		if(wellboreTrajectoryHandsontableHelper!=null){
                    			CreateAndLoadWellboreTrajectoryTable();
                        	}
                        }
                    }
                },{
                	region: 'center',
                    layout: 'fit',
                    id: 'wellboreTrajectoryChartPanel_Id',
                    title: '井身轨迹图',
                    border: false,
                    html: '<div id="WellboreTrajectoryChartDiv_Id" style="width:100%;height:100%;"></div>',
                    listeners: {
                    	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    		if($("#WellboreTrajectoryChartDiv_Id").highcharts()!=undefined){
                    			$("#WellboreTrajectoryChartDiv_Id").highcharts().setSize($("#WellboreTrajectoryChartDiv_Id").offsetWidth, $("#WellboreTrajectoryChartDiv_Id").offsetHeight,true);
                    		}
                    		
//                    		var myChart = echarts.getInstanceByDom(document.getElementById("WellboreTrajectoryChartDiv_Id"));
//                        	if(myChart!=undefined){
//                        		var width=adjWidth;
//                        		var height=adjHeight;
//                        		if(height>width){
//                        			height=width;
//                        		}else{
//                        			width=height;
//                        		}
//                        		myChart.resize({
//                        			width: $("#WellboreTrajectoryChartDiv_Id").offsetWidth,
//                        			height: $("#WellboreTrajectoryChartDiv_Id").offsetHeight,
//                        			silent:true
//                        		});
//                        	}
                        }
                    }
                }, {
                    region: 'east',
                    id: 'wellboreTrajectoryDetailsPanel_Id',
                    width: '30%',
                    title: '井身轨迹数据',
                    collapsible: true, // 是否折叠
                    split: true, // 竖折叠条
                    border: false,
                    html: '<div id="WellboreTrajectoryDetailsDiv_Id" style="width:100%;height:100%;"></div>',
                    listeners: {
                    	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    		
                        }
                    }
                }],
                listeners: {
                	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    	
                    }
                }
            }]
        });
        me.callParent(arguments);
    }

});

function saveWellboreTrajectoryData(){
	var WellboreTrajectoryDataSaveData=[];
	var WellboreTrajectoryData=wellboreTrajectoryDetailsHandsontableHelper.hot.getData();
	var row=parseInt(Ext.getCmp("wellboreTrajectoryTableSelectedRow_Id").getValue());
	var wellName=wellboreTrajectoryHandsontableHelper.hot.getDataAtCell(row,1);
	
	for(var i=0;i<WellboreTrajectoryData.length;i++){
		if(WellboreTrajectoryData[i][1]!=null&&WellboreTrajectoryData[i][1]!=""){
			var data="{";
        	for(var j=0;j<wellboreTrajectoryDetailsHandsontableHelper.columns.length;j++){
        		data+="\""+wellboreTrajectoryDetailsHandsontableHelper.columns[j].data+"\":\""+WellboreTrajectoryData[i][j]+"\"";
        		if(j<wellboreTrajectoryDetailsHandsontableHelper.columns.length-1){
        			data+=","
        		}
        	}
        	data+="}";
        	WellboreTrajectoryDataSaveData.push(Ext.JSON.decode(data));
		}
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/wellboreTrajectoryManagerController/saveWellboreTrajectoryData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			initWellboreTrajectoryCharts(result);
			Ext.MessageBox.alert("信息","保存成功");
		},
		failure:function(){
			Ext.MessageBox.alert("信息","请求失败");
		},
		params: {
			wellName:wellName,
			wellboreTrajectoryData: JSON.stringify(WellboreTrajectoryDataSaveData)
        }
	}); 
//	if (JSON.stringify(WellboreTrajectoryDataSaveData) != "[]" ) {
//    	
//    } else {
//    	Ext.MessageBox.alert("信息","无有效数据");
//    }
}

function CreateAndLoadWellboreTrajectoryTable(isNew){
	if(isNew&&wellboreTrajectoryHandsontableHelper!=null){
		wellboreTrajectoryHandsontableHelper.clearContainer();
		wellboreTrajectoryHandsontableHelper.hot.destroy();
		wellboreTrajectoryHandsontableHelper=null;
		
		if(wellboreTrajectoryDetailsHandsontableHelper!=null){
			wellboreTrajectoryDetailsHandsontableHelper.clearContainer();
			if(wellboreTrajectoryDetailsHandsontableHelper.hot!=null){
				wellboreTrajectoryDetailsHandsontableHelper.hot.destroy();
			}
			wellboreTrajectoryDetailsHandsontableHelper=null;
		}
	}
	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/wellboreTrajectoryManagerController/getWellboreTrajectoryList',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			wellboreTrajectoryHandsontableHelper = WellboreTrajectoryHandsontableHelper.createNew("WellboreTrajectoryInfoDiv_Id");
			var colHeaders="['序号','井名']";
			var columns="[{data:'id'},{data:'WellName'}]";
			wellboreTrajectoryHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
			wellboreTrajectoryHandsontableHelper.columns=Ext.JSON.decode(columns);
			wellboreTrajectoryHandsontableHelper.createTable(result.totalRoot);
			var row1=wellboreTrajectoryHandsontableHelper.hot.getDataAtRow(0);
			CreateAndLoadWellboreTrajectoryDetailsTable(row1)
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId:leftOrg_Id
        }
	});
};

var WellboreTrajectoryHandsontableHelper = {
	    createNew: function (divid) {
	        var wellboreTrajectoryHandsontableHelper = {};
	        wellboreTrajectoryHandsontableHelper.hot1 = '';
	        wellboreTrajectoryHandsontableHelper.divid = divid;
	        wellboreTrajectoryHandsontableHelper.validresult=true;//数据校验
	        wellboreTrajectoryHandsontableHelper.colHeaders=[];
	        wellboreTrajectoryHandsontableHelper.columns=[];
	        wellboreTrajectoryHandsontableHelper.AllData=[];
	        
	        wellboreTrajectoryHandsontableHelper.createTable = function (data) {
	        	$('#'+wellboreTrajectoryHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+wellboreTrajectoryHandsontableHelper.divid);
	        	wellboreTrajectoryHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	                hiddenColumns: {
	                    columns: [0,12],
	                    indicators: true
	                },
	                columns:wellboreTrajectoryHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:wellboreTrajectoryHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                afterSelection: function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	var jh=wellboreTrajectoryHandsontableHelper.hot.getDataAtCell(row,1);
	                	var row1=wellboreTrajectoryHandsontableHelper.hot.getDataAtRow(row);
	                	Ext.getCmp("wellboreTrajectoryTableSelectedRow_Id").setValue(row);

	                	CreateAndLoadWellboreTrajectoryDetailsTable(row1);
	                }
	        	});
	        }
	        //保存数据
	        wellboreTrajectoryHandsontableHelper.saveData = function () {}
	        wellboreTrajectoryHandsontableHelper.clearContainer = function () {
	        	wellboreTrajectoryHandsontableHelper.AllData = [];
	        }
	        return wellboreTrajectoryHandsontableHelper;
	    }
};


function CreateAndLoadWellboreTrajectoryDetailsTable(selectedRow){
	
	wellboreTrajectoryDetailsHandsontableHelper = WellboreTrajectoryDetailsHandsontableHelper.createNew("WellboreTrajectoryDetailsDiv_Id");
	var colHeaders="['序号','测量深度(m)','井斜角(°)','方位角(°)']";
	var columns="[" +
			"{data:'id'},"
			+"{data:'measuringDepth',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,wellboreTrajectoryDetailsHandsontableHelper);}},"
			+"{data:'deviationAngle',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,wellboreTrajectoryDetailsHandsontableHelper);}},"
			+"{data:'azimuthAngle',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,wellboreTrajectoryDetailsHandsontableHelper);}}"
//			"{data:'measuringDepth'}," +
//			"{data:'deviationAngle'}," +
//			"{data:'azimuthAngle'}" +
			+"]";
	wellboreTrajectoryDetailsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
	wellboreTrajectoryDetailsHandsontableHelper.columns=Ext.JSON.decode(columns);
	
	
	if(selectedRow[1]==undefined||selectedRow[1]==null||selectedRow[1]==''){
		wellboreTrajectoryDetailsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
		var myChart = echarts.init(document.getElementById('WellboreTrajectoryChartDiv_Id'));
		if(myChart!=undefined){
			myChart.clear();
		}
	}else{
		Ext.Ajax.request({
			method:'POST',
			url:context + '/wellboreTrajectoryManagerController/getWellboreTrajectoryDetailsData',
			success:function(response) {
				var result =  Ext.JSON.decode(response.responseText);
				wellboreTrajectoryDetailsHandsontableHelper.createTable(result.totalRoot);
				initWellboreTrajectoryCharts(result);
//				if(result.isHaveData){
//					initWellboreTrajectoryCharts(result);
//				}else{
//					$("#WellboreTrajectoryChartDiv_Id").html('');
//				}
				
			},
			failure:function(){
				Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
			},
			params: {
	        	wellName: selectedRow[1]
	        }
		});
	}
};

var WellboreTrajectoryDetailsHandsontableHelper = {
	    createNew: function (divid) {
	        var wellboreTrajectoryDetailsHandsontableHelper = {};
	        wellboreTrajectoryDetailsHandsontableHelper.hot1 = '';
	        wellboreTrajectoryDetailsHandsontableHelper.divid = divid;
	        wellboreTrajectoryDetailsHandsontableHelper.validresult=true;//数据校验
	        wellboreTrajectoryDetailsHandsontableHelper.colHeaders=[];
	        wellboreTrajectoryDetailsHandsontableHelper.columns=[];
	        wellboreTrajectoryDetailsHandsontableHelper.AllData=[];
	        
	        wellboreTrajectoryDetailsHandsontableHelper.createTable = function (data) {
	        	$('#'+wellboreTrajectoryDetailsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+wellboreTrajectoryDetailsHandsontableHelper.divid);
	        	wellboreTrajectoryDetailsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: true
	                },
	                columns:wellboreTrajectoryDetailsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:wellboreTrajectoryDetailsHandsontableHelper.colHeaders,//显示列头
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
	        wellboreTrajectoryDetailsHandsontableHelper.saveData = function () {}
	        wellboreTrajectoryDetailsHandsontableHelper.clearContainer = function () {
	        	wellboreTrajectoryDetailsHandsontableHelper.AllData = [];
	        }
	        return wellboreTrajectoryDetailsHandsontableHelper;
	    }
};
function initWellboreTrajectoryCharts(result){
	var data = [];
	var yMaxValue=0;
	var wellName=result.wellName;
	var zMin=null,zMax=null,xMin=null,xMax=null,yMin=null,yMax=null;
	for(var i=0;i<result.totalRoot.length;i++){
		data.push([result.totalRoot[i].X,result.totalRoot[i].Z,result.totalRoot[i].Y]);
		yMaxValue=result.totalRoot[i].Z;
	}
	if(yMaxValue!=0 && yMaxValue!=undefined){
		yMax=Math.ceil(yMaxValue/100)*100;
		xMin=zMin=0-yMax/2;
		xMax=zMax=yMax/2;
		yMin=0;
	}
	var chart = new Highcharts.Chart({
		chart: {
			renderTo: 'WellboreTrajectoryChartDiv_Id',
//			margin: 100,
			type: 'scatter',
			options3d: {
				enabled: true,
				alpha: 10,
				beta: 10,
				depth: 250,
				viewDistance: 100,//视图距离，它对于计算角度影响在柱图和散列图非常重要。此值不能用于 3D 的饼图 默认是：100
				frame: {
					bottom: { size: 1, color: 'rgba(0,0,0,1)' },
					back: { size: 1, color: 'rgba(0,0,0,0.04)' },
					side: { size: 1, color: 'rgba(0,0,0,0.06)' }
				}
			}
		},
		credits : {
			enabled : false
		},
		title: {
			text: '井身轨迹'
		},
		subtitle: {
			text: wellName
		},
		plotOptions: {
			scatter: {
				width: 10,
				height: 10,
				depth: 10
			}
		},
		yAxis: {
			min: yMin,
			max: yMax,
			reversed:true,
			title: null
		},
		xAxis: {
			min: xMin,
			max: xMax,
			gridLineWidth: 1
		},
		zAxis: {
			min: zMin,
			max: zMax
		},
		legend: {
			enabled: false
		},
		series: [{
			name: '井身轨迹',
			colorByPoint: false,
			color: '#0000FF',
			lineWidth: 3,
			marker:{
				enabled: true,
				radius: 2
			},
			data:data
		}]
	});
	// Add mouse events for rotation
	$(chart.container).bind('mousedown.hc touchstart.hc', function (e) {
		e = chart.pointer.normalize(e);
		var posX = e.pageX,
			posY = e.pageY,
			alpha = chart.options.chart.options3d.alpha,
			beta = chart.options.chart.options3d.beta,
			newAlpha,
			newBeta,
			sensitivity = 5; // lower is more sensitive
		$(document).bind({
			'mousemove.hc touchdrag.hc': function (e) {
				// Run beta
				newBeta = beta + (posX - e.pageX) / sensitivity;
				newBeta = Math.min(100, Math.max(-100, newBeta));
				chart.options.chart.options3d.beta = newBeta;
				// Run alpha
				newAlpha = alpha + (e.pageY - posY) / sensitivity;
				newAlpha = Math.min(100, Math.max(-100, newAlpha));
				chart.options.chart.options3d.alpha = newAlpha;
				chart.redraw(false);
			},
			'mouseup touchend': function () {
				$(document).unbind('.hc');
			}
		});
	});
};

function initWellboreTrajectoryCharts2(result){
	var data = [];
	var zMaxValue=0;
	var zMin=null,zMax=null,xMin=null,xMax=null,yMin=null,yMax=null;
	for(var i=0;i<result.totalRoot.length;i++){
		data.push([result.totalRoot[i].X,result.totalRoot[i].Y,0-result.totalRoot[i].Z]);
		zMaxValue=result.totalRoot[i].Z;
	}
	if(zMaxValue!=0 && zMaxValue!=undefined){
		zMax=Math.ceil(zMaxValue/100)*100;
		xMin=yMin=0-zMax/2;
		xMax=yMax=zMax/2;
		zMin=0-zMax;
	}
	var myChart = echarts.init(document.getElementById('WellboreTrajectoryChartDiv_Id'),'macarons');
	var option = {
		    tooltip: {},
		    backgroundColor: '#fff',
		    title: {
		        left: 'center',
		        text: '井身轨迹',
		    },
		    visualMap: {
		        show: false,
		        dimension: 2,
		        min: 0,
		        max: 30,
		        inRange: {
		            color: ['#313695']
//		    		color: ['#313695', '#4575b4', '#74add1', '#abd9e9', '#e0f3f8', '#ffffbf', '#fee090', '#fdae61', '#f46d43', '#d73027', '#a50026']
		        }
		    },
		    toolbox: {
                show: true,
                feature: {
                    dataZoom: {
                        yAxisIndex: 'none'
                    }, //区域缩放，区域缩放还原
                    dataView: {
                        readOnly: false
                    }, //数据视图
                    restore: {},  //还原
                    saveAsImage: {}   //保存为图片
                }
            },
		    xAxis3D: {
		        type: 'value',
		        min:xMin,
		        max:xMax
		    },
		    yAxis3D: {
		        type: 'value',
		        min:yMin,
		        max:yMax
		    },
		    zAxis3D: {
		        type: 'value',
		        min:zMin,
		        max:0,
		        inverse: true //反转坐标轴
		    },
		    grid3D: {
		        viewControl: {
		            projection: 'orthographic'
		        },
		        boxWidth: 90, //图件宽
		        boxHeight: 90, //图件高
		        boxDepth: 90 //图件长
		    },
		    series: [{
		        type: 'line3D',
		        data: data,
		        zoom: 0.5, //当前视角的缩放比例
		        roam: true, //是否开启平游或缩放
		        lineStyle: {
		            width: 4
		        }
		    }]
		};
	// 使用刚指定的配置项和数据显示图表。
	myChart.clear();
    myChart.setOption(option);
//    setTimeout(function (){
//	    window.onresize = function () {
//	    	myChart.resize();
//	    }
//	},200)

};