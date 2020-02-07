var fsToPSMotorHandsontableHelper=null;
var fsToPSMotorCharaCurveHandsontableHelper=null;
Ext.define("AP.view.PSToFS.MotorInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.PSToFSMotorInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;

        Ext.apply(me, {
            items: [{
                layout: "border",
                border: false,
                tbar:[{
        			xtype: 'textfield',
        			id: 'fsToPSMotorTableSelectedRow_Id',
        			hidden:true
        		},'->',{
                    xtype: 'button',
                    text: cosog.string.save,
                    iconCls:'save',
                    pressed: true,
                    handler: function (v, o) {
                    	savePSToFSMotorData();
                    }
                }],
                items: [{
                        	region: 'center',
                        	layout: 'fit',
                        	id:'FSToPSMotorInfoPanel_Id',
                        	title: '电机数据',
                        	border: false,
                        	html: '<div id="FSToPSMotorInfoDiv_Id" style="width:100%;height:100%;"></div>',
                        	listeners: {
                            	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            		if(fsToPSMotorHandsontableHelper!=null){
                            			CreateAndLoadFSToPSMotorTable();
                            		}
//                            		else{
//                            			CreateAndLoadFSToPSMotorTable(fsToPSMotorHandsontableHelper.hot.getData());
//                            		}
                                }
                            }
                    	},{
                    		region: 'east',
                    		id: 'FSToPSMotorCharaCurvePanel_Id',
                    		width: '55%',
                    		title: '电机负载特性曲线',
                    		collapsible: true, // 是否折叠
                    		split: true, // 竖折叠条
                    		border: false,
                    		html: '<div id="FSToPSMotorCharaCurveDiv_Id" style="width:100%;height:100%;"></div>',
                    		listeners: {
                            	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
//                                	if(fsToPSMotorCharaCurveHandsontableHelper==null){
//                                		CreateAndLoadFSToPSMotorCharaCurveTable();
//                            		}else{
//                            			CreateAndLoadFSToPSMotorCharaCurveTable(fsToPSMotorCharaCurveHandsontableHelper.hot.getData());
//                            		}
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

function savePSToFSMotorData(){
	var MotorDataSaveData=[];
	var MotorPerformanceCurverDataSaveData=[];
	var MotorData=fsToPSMotorHandsontableHelper.hot.getData();
	var MotorPerformanceCurverData=fsToPSMotorCharaCurveHandsontableHelper.hot.getData();
	var row=parseInt(Ext.getCmp("fsToPSMotorTableSelectedRow_Id").getValue());
	var wellName=fsToPSMotorHandsontableHelper.hot.getDataAtCell(row,1);
	for(var i=0;i<MotorData.length;i++){
		if(MotorData[i][1]!=null&&MotorData[i][1]!=""){
			var data="{";
        	for(var j=0;j<fsToPSMotorHandsontableHelper.columns.length;j++){
        		data+="\""+fsToPSMotorHandsontableHelper.columns[j].data+"\":\""+MotorData[i][j]+"\"";
        		if(j<fsToPSMotorHandsontableHelper.columns.length-1){
        			data+=","
        		}
        	}
        	data+="}";
        	MotorDataSaveData.push(Ext.JSON.decode(data));
		}
	}
	
	for(var i=0;i<MotorPerformanceCurverData.length;i++){
		if(MotorPerformanceCurverData[i][1]!=null&&MotorPerformanceCurverData[i][1]!=""){
			var data="{";
        	for(var j=0;j<fsToPSMotorCharaCurveHandsontableHelper.columns.length;j++){
        		data+="\""+fsToPSMotorCharaCurveHandsontableHelper.columns[j].data+"\":\""+MotorPerformanceCurverData[i][j]+"\"";
        		if(j<fsToPSMotorCharaCurveHandsontableHelper.columns.length-1){
        			data+=","
        		}
        	}
        	data+="}";
        	MotorPerformanceCurverDataSaveData.push(Ext.JSON.decode(data));
		}
	}
	if (JSON.stringify(MotorDataSaveData) != "[]" ) {
    	Ext.Ajax.request({
    		method:'POST',
    		url:context + '/PSToFSController/saveMotorData',
    		success:function(response) {
    			var data=Ext.JSON.decode(response.responseText);
    			if (data.success) {
                	Ext.MessageBox.alert("信息","保存成功");
                	CreateAndLoadFSToPSMotorTable();
                } else {
                	Ext.MessageBox.alert("信息","数据保存失败");

                }
    		},
    		failure:function(){
    			Ext.MessageBox.alert("信息","请求失败");
    		},
    		params: {
    			wellName:wellName,
    			MotorData: JSON.stringify(MotorDataSaveData),
    			MotorPerformanceCurverData: JSON.stringify(MotorPerformanceCurverDataSaveData)
            }
    	}); 
    } else {
    	Ext.MessageBox.alert("信息","无有效数据");
    }
}

function CreateAndLoadFSToPSMotorTable(isNew){
	if(isNew&&fsToPSMotorHandsontableHelper!=null){
		fsToPSMotorHandsontableHelper.clearContainer();
		fsToPSMotorHandsontableHelper.hot.destroy();
		fsToPSMotorHandsontableHelper=null;
		
		fsToPSMotorCharaCurveHandsontableHelper.clearContainer();
		fsToPSMotorCharaCurveHandsontableHelper.hot.destroy();
		fsToPSMotorCharaCurveHandsontableHelper=null;
	}
	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/PSToFSController/getPSToFSMotorData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			fsToPSMotorHandsontableHelper = FSToPSMotorHandsontableHelper.createNew("FSToPSMotorInfoDiv_Id");
			var colHeaders="['序号','井名','电机厂家','电机型号','皮带轮直径(m)','同步转速(r/min)']";
			var columns="[{data:'id'},{data:'WellName'},{data:'Manufacturer'},{data:'Model'},{data:'BeltPulleyDiameter'},{data:'SynchroSpeed'},{data:'PerformanceCurver'}]";
			fsToPSMotorHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
			fsToPSMotorHandsontableHelper.columns=Ext.JSON.decode(columns);
			fsToPSMotorHandsontableHelper.createTable(result.totalRoot);
			
			var row1=fsToPSMotorHandsontableHelper.hot.getDataAtRow(0);
			CreateAndLoadFSToPSMotorCharaCurveTable(row1[6])
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId:leftOrg_Id
        }
	});
	
//	fsToPSMotorHandsontableHelper = FSToPSMotorHandsontableHelper.createNew("FSToPSMotorInfoDiv_Id");
//	var colHeaders="['序号','井名','电机厂家','电机型号','皮带轮直径(m)','同步转速(r/min)']";
//	var columns="[{data:'id'},{data:'WellName'},{data:'Manufacturer'},{data:'Model'},{data:'BeltPulleyDiameter'},{data:'SynchroSpeed'}]";
//	fsToPSMotorHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
//	fsToPSMotorHandsontableHelper.columns=Ext.JSON.decode(columns);
//	if(data==undefined||data==null){
//		fsToPSMotorHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
//	}else{
//		fsToPSMotorHandsontableHelper.createTable(data);
//	}
};

var FSToPSMotorHandsontableHelper = {
	    createNew: function (divid) {
	        var fsToPSMotorHandsontableHelper = {};
	        fsToPSMotorHandsontableHelper.hot1 = '';
	        fsToPSMotorHandsontableHelper.divid = divid;
	        fsToPSMotorHandsontableHelper.validresult=true;//数据校验
	        fsToPSMotorHandsontableHelper.colHeaders=[];
	        fsToPSMotorHandsontableHelper.columns=[];
	        fsToPSMotorHandsontableHelper.AllData=[];
	        
	        fsToPSMotorHandsontableHelper.createTable = function (data) {
	        	$('#'+fsToPSMotorHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+fsToPSMotorHandsontableHelper.divid);
	        	fsToPSMotorHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	                hiddenColumns: {
	                    columns: [0,6],
	                    indicators: true
	                },
	                columns:fsToPSMotorHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:fsToPSMotorHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                afterSelection: function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	var jh=fsToPSMotorHandsontableHelper.hot.getDataAtCell(row,1);
	                	var row1=fsToPSMotorHandsontableHelper.hot.getDataAtRow(row);
	                	Ext.getCmp("fsToPSMotorTableSelectedRow_Id").setValue(row);
	                	if(jh!=null){
		                	if(row1[6]!=null&&row1[6]!=undefined){
		                		CreateAndLoadFSToPSMotorCharaCurveTable(row1[6]);
		                	}
	                	}else{
	                		CreateAndLoadFSToPSMotorCharaCurveTable(row1[6]);
	                	}
	                }
	        	});
	        }
	        //保存数据
	        fsToPSMotorHandsontableHelper.saveData = function () {}
	        fsToPSMotorHandsontableHelper.clearContainer = function () {
	        	fsToPSMotorHandsontableHelper.AllData = [];
	        }
	        return fsToPSMotorHandsontableHelper;
	    }
};


function CreateAndLoadFSToPSMotorCharaCurveTable(data){
	
	fsToPSMotorCharaCurveHandsontableHelper = FSToPSMotorCharaCurveHandsontableHelper.createNew("FSToPSMotorCharaCurveDiv_Id");
//	var colHeaders="['序号','电压U1(V)','电流I1(A)','输入功率P1(kW)','功率因数cosφ(小数)','效率Eff(小数)','转差率S(%)','输出功率P2(kW)']";
//	var columns="[{data:'id'},{data:'Voltage'},{data:'Current'},{data:'InputActivePower'},{data:'PowerFactor'},{data:'Efficiency'},{data:'SlipRatio'},{data:'OutputPower'}]";

	var colHeaders="['序号','输入功率P1(kW)','输出轴扭矩(kN·m)','输出轴转速(rpm)']";
	var columns="[{data:'id'},{data:'InputActivePower'},{data:'OutputTorque'},{data:'OutputRPM'}]";
	
	fsToPSMotorCharaCurveHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
	fsToPSMotorCharaCurveHandsontableHelper.columns=Ext.JSON.decode(columns);
	if(data==undefined||data==null||data.length==0){
		fsToPSMotorCharaCurveHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
	}else{
		fsToPSMotorCharaCurveHandsontableHelper.createTable(data);
	}
};

var FSToPSMotorCharaCurveHandsontableHelper = {
	    createNew: function (divid) {
	        var fsToPSMotorCharaCurveHandsontableHelper = {};
	        fsToPSMotorCharaCurveHandsontableHelper.hot1 = '';
	        fsToPSMotorCharaCurveHandsontableHelper.divid = divid;
	        fsToPSMotorCharaCurveHandsontableHelper.validresult=true;//数据校验
	        fsToPSMotorCharaCurveHandsontableHelper.colHeaders=[];
	        fsToPSMotorCharaCurveHandsontableHelper.columns=[];
	        fsToPSMotorCharaCurveHandsontableHelper.AllData=[];
	        
	        fsToPSMotorCharaCurveHandsontableHelper.createTable = function (data) {
	        	$('#'+fsToPSMotorCharaCurveHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+fsToPSMotorCharaCurveHandsontableHelper.divid);
	        	fsToPSMotorCharaCurveHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: true
	                },
	                columns:fsToPSMotorCharaCurveHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:fsToPSMotorCharaCurveHandsontableHelper.colHeaders,//显示列头
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
	        fsToPSMotorCharaCurveHandsontableHelper.saveData = function () {}
	        fsToPSMotorCharaCurveHandsontableHelper.clearContainer = function () {
	        	fsToPSMotorCharaCurveHandsontableHelper.AllData = [];
	        }
	        return fsToPSMotorCharaCurveHandsontableHelper;
	    }
};