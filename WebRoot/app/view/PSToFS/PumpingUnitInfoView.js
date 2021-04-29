var fsToPSPumpingUnitHandsontableHelper=null;
var fsToPSPumpingUnitPTFHandsontableHelper=null;
Ext.define("AP.view.PSToFS.PumpingUnitInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.PSToFSPumpingUnitInfoView', // 定义别名
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
        			id: 'fsToPSPumpingUnitTableSelectedRow_Id',
        			hidden:true
        		},'->', {
                    xtype: 'button',
                    text: cosog.string.save,
                    iconCls: 'save',
                    pressed: true,
                    handler: function (v, o) {
                    	savePSToFSPumpingUnitData();
                    }
                }],
                items: [{
                	region: 'center',
                    layout: 'fit',
                    id: 'FSToPSPumpingUnitInfoPanel_Id',
                    title: '抽油机数据',
                    border: false,
                    html: '<div id="FSToPSPumpingUnitInfoDiv_Id" style="width:100%;height:100%;"></div>',
                    listeners: {
                    	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    		if(fsToPSPumpingUnitHandsontableHelper!=null){
                    			CreateAndLoadFSToPSPumpingUnitTable();
                        	}
                        }
                    }
                }, {
                    region: 'east',
                    id: 'FSToPSPumpingUnitPTFPanel_Id',
                    width: '30%',
                    title: '抽油机位置扭矩因数',
                    collapsible: true, // 是否折叠
                    split: true, // 竖折叠条
                    border: false,
                    html: '<div id="FSToPSPumpingUnitPTFDiv_Id" style="width:100%;height:100%;"></div>',
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

function savePSToFSPumpingUnitData(){
	var PumpingUnitDataSaveData=[];
	var PumpingUnitPTRDataSaveData=[];
	var PumpingUnitData=fsToPSPumpingUnitHandsontableHelper.hot.getData();
	var PumpingUnitPTRData=fsToPSPumpingUnitPTFHandsontableHelper.hot.getData();
	var row=parseInt(Ext.getCmp("fsToPSPumpingUnitTableSelectedRow_Id").getValue());
	var wellName=fsToPSPumpingUnitHandsontableHelper.hot.getDataAtCell(row,1);
	for(var i=0;i<PumpingUnitData.length;i++){
		if(PumpingUnitData[i][1]!=null&&PumpingUnitData[i][1]!=""){
			var data="{";
        	for(var j=0;j<fsToPSPumpingUnitHandsontableHelper.columns.length;j++){
        		data+="\""+fsToPSPumpingUnitHandsontableHelper.columns[j].data+"\":\""+PumpingUnitData[i][j]+"\"";
        		if(j<fsToPSPumpingUnitHandsontableHelper.columns.length-1){
        			data+=","
        		}
        	}
        	data+="}";
        	PumpingUnitDataSaveData.push(Ext.JSON.decode(data));
		}
	}
	
	for(var i=0;i<PumpingUnitPTRData.length;i++){
		if(PumpingUnitPTRData[i][1]!=null&&PumpingUnitPTRData[i][1]!=""){
			var data="{";
        	for(var j=0;j<fsToPSPumpingUnitPTFHandsontableHelper.columns.length;j++){
        		data+="\""+fsToPSPumpingUnitPTFHandsontableHelper.columns[j].data+"\":\""+PumpingUnitPTRData[i][j]+"\"";
        		if(j<fsToPSPumpingUnitPTFHandsontableHelper.columns.length-1){
        			data+=","
        		}
        	}
        	data+="}";
        	PumpingUnitPTRDataSaveData.push(Ext.JSON.decode(data));
		}
	}
	if (JSON.stringify(PumpingUnitDataSaveData) != "[]" ) {
    	Ext.Ajax.request({
    		method:'POST',
    		url:context + '/PSToFSController/savePumpingUnitData',
    		success:function(response) {
    			var data=Ext.JSON.decode(response.responseText);
    			if (data.success) {
                	Ext.MessageBox.alert("信息","保存成功");
                    CreateAndLoadFSToPSPumpingUnitTable();
                } else {
                	Ext.MessageBox.alert("信息","数据保存失败");

                }
    		},
    		failure:function(){
    			Ext.MessageBox.alert("信息","请求失败");
    		},
    		params: {
    			wellName:wellName,
    			PumpingUnitData: JSON.stringify(PumpingUnitDataSaveData),
    			PumpingUnitPTRData: JSON.stringify(PumpingUnitPTRDataSaveData)
            }
    	}); 
    } else {
    	Ext.MessageBox.alert("信息","无有效数据");
    }
}

function CreateAndLoadFSToPSPumpingUnitTable(isNew){
	if(isNew&&fsToPSPumpingUnitHandsontableHelper!=null){
		fsToPSPumpingUnitHandsontableHelper.clearContainer();
		fsToPSPumpingUnitHandsontableHelper.hot.destroy();
		fsToPSPumpingUnitHandsontableHelper=null;
		
		fsToPSPumpingUnitPTFHandsontableHelper.clearContainer();
		fsToPSPumpingUnitPTFHandsontableHelper.hot.destroy();
		fsToPSPumpingUnitPTFHandsontableHelper=null;
	}
	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/PSToFSController/getPSToFSPumpingUnitData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			fsToPSPumpingUnitHandsontableHelper = FSToPSPumpingUnitHandsontableHelper.createNew("FSToPSPumpingUnitInfoDiv_Id");
			var colHeaders="['序号','井名','抽油机厂家','抽油机型号','冲程(m)','旋转方向','曲柄偏置角(°)','曲柄重心半径(m)','单块曲柄重量(kN)','单块曲柄销重量(kN)','结构不平衡重(kN)','平衡块位置(m)','平衡块重量(kN)']";
			var columns="[{data:'id'},{data:'WellName'},{data:'Manufacturer'},{data:'Model'},{data:'Stroke'},{data:'CrankRotationDirection'},{data:'OffsetAngleOfCrank'},{data:'CrankGravityRadius'},{data:'SingleCrankWeight'},{data:'SingleCrankPinWeight'},{data:'StructuralUnbalance'},{data:'BalancePosition'},{data:'BalanceWeight'}," 
				+"{data:'prtf'}]";
			fsToPSPumpingUnitHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
			fsToPSPumpingUnitHandsontableHelper.columns=Ext.JSON.decode(columns);
			fsToPSPumpingUnitHandsontableHelper.createTable(result.totalRoot);
			
			var row1=fsToPSPumpingUnitHandsontableHelper.hot.getDataAtRow(0);
			CreateAndLoadFSToPSPumpingUnitPTFTable(row1[13])
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId:leftOrg_Id
        }
	});
};

var FSToPSPumpingUnitHandsontableHelper = {
	    createNew: function (divid) {
	        var fsToPSPumpingUnitHandsontableHelper = {};
	        fsToPSPumpingUnitHandsontableHelper.hot1 = '';
	        fsToPSPumpingUnitHandsontableHelper.divid = divid;
	        fsToPSPumpingUnitHandsontableHelper.validresult=true;//数据校验
	        fsToPSPumpingUnitHandsontableHelper.colHeaders=[];
	        fsToPSPumpingUnitHandsontableHelper.columns=[];
	        fsToPSPumpingUnitHandsontableHelper.AllData=[];
	        
	        fsToPSPumpingUnitHandsontableHelper.createTable = function (data) {
	        	$('#'+fsToPSPumpingUnitHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+fsToPSPumpingUnitHandsontableHelper.divid);
	        	fsToPSPumpingUnitHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	                hiddenColumns: {
	                    columns: [0,13],
	                    indicators: true
	                },
	                columns:fsToPSPumpingUnitHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:fsToPSPumpingUnitHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                afterSelection: function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	var wellName=fsToPSPumpingUnitHandsontableHelper.hot.getDataAtCell(row,1);
	                	var row1=fsToPSPumpingUnitHandsontableHelper.hot.getDataAtRow(row);
	                	Ext.getCmp("fsToPSPumpingUnitTableSelectedRow_Id").setValue(row);
	                	if(wellName!=null){
		                	if(row1[13]!=null&&row1[13]!=undefined){
		                		CreateAndLoadFSToPSPumpingUnitPTFTable(row1[13]);
		                	}
	                	}else{
	                		CreateAndLoadFSToPSPumpingUnitPTFTable(row1[13]);
	                	}
	                }
	        	});
	        }
	        //保存数据
	        fsToPSPumpingUnitHandsontableHelper.saveData = function () {}
	        fsToPSPumpingUnitHandsontableHelper.clearContainer = function () {
	        	fsToPSPumpingUnitHandsontableHelper.AllData = [];
	        }
	        return fsToPSPumpingUnitHandsontableHelper;
	    }
};


function CreateAndLoadFSToPSPumpingUnitPTFTable(data){
	
	fsToPSPumpingUnitPTFHandsontableHelper = FSToPSPumpingUnitPTFHandsontableHelper.createNew("FSToPSPumpingUnitPTFDiv_Id");
	var colHeaders="['序号','曲柄转角(°)','光杆位置因数(%)','扭矩因数(m)']";
	var columns="[{data:'id'},{data:'CrankAngle'},{data:'PR'},{data:'TF'}]";
	fsToPSPumpingUnitPTFHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
	fsToPSPumpingUnitPTFHandsontableHelper.columns=Ext.JSON.decode(columns);
	if(data==undefined||data==null||data.length==0){
		fsToPSPumpingUnitPTFHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
	}else{
		fsToPSPumpingUnitPTFHandsontableHelper.createTable(data);
	}
	
	
};

var FSToPSPumpingUnitPTFHandsontableHelper = {
	    createNew: function (divid) {
	        var fsToPSPumpingUnitPTFHandsontableHelper = {};
	        fsToPSPumpingUnitPTFHandsontableHelper.hot1 = '';
	        fsToPSPumpingUnitPTFHandsontableHelper.divid = divid;
	        fsToPSPumpingUnitPTFHandsontableHelper.validresult=true;//数据校验
	        fsToPSPumpingUnitPTFHandsontableHelper.colHeaders=[];
	        fsToPSPumpingUnitPTFHandsontableHelper.columns=[];
	        fsToPSPumpingUnitPTFHandsontableHelper.AllData=[];
	        
	        fsToPSPumpingUnitPTFHandsontableHelper.createTable = function (data) {
	        	$('#'+fsToPSPumpingUnitPTFHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+fsToPSPumpingUnitPTFHandsontableHelper.divid);
	        	fsToPSPumpingUnitPTFHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: true
	                },
	                columns:fsToPSPumpingUnitPTFHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:fsToPSPumpingUnitPTFHandsontableHelper.colHeaders,//显示列头
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
	        fsToPSPumpingUnitPTFHandsontableHelper.saveData = function () {}
	        fsToPSPumpingUnitPTFHandsontableHelper.clearContainer = function () {
	        	fsToPSPumpingUnitPTFHandsontableHelper.AllData = [];
	        }
	        return fsToPSPumpingUnitPTFHandsontableHelper;
	    }
};