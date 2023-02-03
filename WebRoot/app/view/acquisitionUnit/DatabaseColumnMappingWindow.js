var databaseColumnMappingHandsontableHelper=null;
Ext.define("AP.view.acquisitionUnit.DatabaseColumnMappingWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.databaseColumnMappingWindow',
    layout: 'fit',
    title:'存储字段表',
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 1200,
    minWidth: 1200,
    height: 700,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.create('AP.store.acquisitionUnit.DatabaseColumnProtocolTreeInfoStore');
        Ext.apply(me, {
            layout: "border",
            items: [{
            	region: 'west',
            	width:'25%',
            	layout: 'fit',
            	split: true,
                collapsible: true,
            	id:"DatabaseColumnMappingTableLeftTreePanel_Id"
            },{
            	region: 'center',
            	layout: "border",
            	border: false,
            	items: [{
            		region: 'center',
                	title:'存储字段表',
                	layout: 'fit',
                	header:true,
                	tbar: ['->',{
                    	xtype: 'button',
            			text: cosog.string.save,
            			iconCls: 'save',
            			handler: function (v, o) {
            				databaseColumnMappingHandsontableHelper.saveData();
            			}
                    }],
                	html: '<div id="DatabaseColumnMappingTableDiv_Id" style="width:100%;height:100%;"></div>',
                	listeners: {
                		resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        	if(databaseColumnMappingHandsontableHelper!=null&&databaseColumnMappingHandsontableHelper.hot!=null&&databaseColumnMappingHandsontableHelper.hot!=undefined){
                        		databaseColumnMappingHandsontableHelper.hot.refreshDimensions();
                        	}
                        }
                	}
            	},{
            		region: 'south',
                	height:'33%',
                	title:'运行状态配置',
                	layout: "border",
                	split: true,
                    collapsible: true,
                	tbar: ['->',{
                    	xtype: 'button',
            			text: cosog.string.save,
            			iconCls: 'save',
            			handler: function (v, o) {
            				saveProtocolRunStatusConfig();
            			}
                    }],
                	items: [{
                		region: 'west',
                		width:'50%',
                    	layout: 'fit',
                    	title:'运行状态项列表',
                    	id:"DatabaseColumnMappingTableRunStatusItemsPanel_Id"
                	},{
                		region: 'center',
                		layout: 'fit',
                		title:'运行值配置',
                    	id:"DatabaseColumnMappingTableRunStatusMeaningPanel1_Id"
                	},{
                		region: 'east',
                		width:'25%',
                		layout: 'fit',
                		title:'停止值配置',
                    	id:"DatabaseColumnMappingTableRunStatusMeaningPanel2_Id"
                	}]
            	}]
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	if(databaseColumnMappingHandsontableHelper!=null){
    					if(databaseColumnMappingHandsontableHelper.hot!=undefined){
    						databaseColumnMappingHandsontableHelper.hot.destroy();
    					}
    					databaseColumnMappingHandsontableHelper=null;
    				}
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function CreateDatabaseColumnMappingTable(classes,deviceType,protocolCode){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getDatabaseColumnMappingTable',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			
			if(databaseColumnMappingHandsontableHelper==null || databaseColumnMappingHandsontableHelper.hot==undefined){
				databaseColumnMappingHandsontableHelper = DatabaseColumnMappingHandsontableHelper.createNew("DatabaseColumnMappingTableDiv_Id");
				var colHeaders="['序号','名称','字段','计算字段']";
				var columns="[" 
						+"{data:'id'}," 
						+"{data:'itemName'}," 
						+"{data:'itemColumn'},"
						+"{data:'calColumn'}"
						+"]";
				databaseColumnMappingHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				databaseColumnMappingHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					databaseColumnMappingHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					databaseColumnMappingHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				databaseColumnMappingHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			classes: classes,
			deviceType: deviceType,
			protocolCode: protocolCode
        }
	});
};

var DatabaseColumnMappingHandsontableHelper = {
		createNew: function (divid) {
	        var databaseColumnMappingHandsontableHelper = {};
	        databaseColumnMappingHandsontableHelper.divid = divid;
	        databaseColumnMappingHandsontableHelper.validresult=true;//数据校验
	        databaseColumnMappingHandsontableHelper.colHeaders=[];
	        databaseColumnMappingHandsontableHelper.columns=[];
	        databaseColumnMappingHandsontableHelper.AllData={};
	        databaseColumnMappingHandsontableHelper.updatelist=[];
	        databaseColumnMappingHandsontableHelper.delidslist=[];
	        databaseColumnMappingHandsontableHelper.insertlist=[];
	        
	        
	        databaseColumnMappingHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = '#DC2828';   
	             td.style.color='#FFFFFF';
	        }
	        
	        databaseColumnMappingHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        databaseColumnMappingHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	td.style.fontWeight = 'bold';
		        td.style.fontSize = '20px';
		        td.style.fontFamily = 'SimSun';
		        td.style.height = '40px';
	        }
	        
	        databaseColumnMappingHandsontableHelper.createTable = function (data) {
	        	$('#'+databaseColumnMappingHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+databaseColumnMappingHandsontableHelper.divid);
	        	databaseColumnMappingHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [0],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                columns:databaseColumnMappingHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                rowHeaders: true, //显示行头
	                colHeaders: databaseColumnMappingHandsontableHelper.colHeaders, //显示列头
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    if(visualColIndex<=2){
	                    	cellProperties.readOnly = true;
	                    	cellProperties.renderer = databaseColumnMappingHandsontableHelper.addBoldBg;
	                    }
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	
	                },
	                beforeRemoveRow: function (index, amount) {
	                    var ids = [];
	                    if (amount != 0) {
	                        for (var i = index; i < amount + index; i++) {
	                            var rowdata = databaseColumnMappingHandsontableHelper.hot.getDataAtRow(i);
	                            ids.push(rowdata[0]);
	                        }
	                        databaseColumnMappingHandsontableHelper.delExpressCount(ids);
	                        databaseColumnMappingHandsontableHelper.screening();
	                    }
	                },
	                afterChange: function (changes, source) {
	                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
	    	        	if (changes != null) {
	    	        		for(var i=0;i<changes.length;i++){
	                    		var params = [];
	                    		var index = changes[i][0]; //行号码
		                        var rowdata = databaseColumnMappingHandsontableHelper.hot.getDataAtRow(index);
		                        params.push(rowdata[0]);
		                        params.push(changes[i][1]);
		                        params.push(changes[i][2]);
		                        params.push(changes[i][3]);
		                        //仅当单元格发生改变的时候,id!=null,说明是更新
		                        if (params[2] != params[3] && params[0] != null && params[0] >0) {
		                        	var data="{";
		                        	for(var j=0;j<databaseColumnMappingHandsontableHelper.columns.length;j++){
		                        		data+=databaseColumnMappingHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
		                        		if(j<databaseColumnMappingHandsontableHelper.columns.length-1){
		                        			data+=","
		                        		}
		                        	}
		                        	data+="}"
		                            databaseColumnMappingHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
		                        }
	                    	}
	                    }
	                }
	        	});
	        }
	      //插入的数据的获取
	        databaseColumnMappingHandsontableHelper.insertExpressCount=function() {
	            var idsdata = databaseColumnMappingHandsontableHelper.hot.getDataAtCol(0); //所有的id
	            for (var i = 0; i < idsdata.length; i++) {
	                if (idsdata[i] == null||idsdata[i]<0) {
	                    var rowdata = databaseColumnMappingHandsontableHelper.hot.getDataAtRow(i);
	                    if (rowdata != null) {
	                    	var data="{";
                        	for(var j=0;j<databaseColumnMappingHandsontableHelper.columns.length;j++){
                        		data+=databaseColumnMappingHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
                        		if(j<databaseColumnMappingHandsontableHelper.columns.length-1){
                        			data+=","
                        		}
                        	}
                        	data+="}"
	                        databaseColumnMappingHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
	                    }
	                }
	            }
	            if (databaseColumnMappingHandsontableHelper.insertlist.length != 0) {
	            	databaseColumnMappingHandsontableHelper.AllData.insertlist = databaseColumnMappingHandsontableHelper.insertlist;
	            }
	        }
	        //保存数据
	        databaseColumnMappingHandsontableHelper.saveData = function () {
	        	var protocolTreeSelection=Ext.getCmp("DatabaseColumnProtocolTreeGridPanel_Id").getSelectionModel().getSelection();
	        	if(protocolTreeSelection.length>0){
	        		databaseColumnMappingHandsontableHelper.insertExpressCount();
	        		var selectedProtocol=protocolTreeSelection[0];
	        		var classes=selectedProtocol.data.classes;
	        		var protocolType=selectedProtocol.data.deviceType;
	        		var protocolCode="";
	        		if(classes==1){
	            		protocolCode=selectedProtocol.data.code;
	            	}
		            if (JSON.stringify(databaseColumnMappingHandsontableHelper.AllData) != "{}" && databaseColumnMappingHandsontableHelper.validresult) {
		            	Ext.Ajax.request({
		            		method:'POST',
		            		url:context + '/acquisitionUnitManagerController/saveDatabaseColumnMappingTable',
		            		success:function(response) {
		            			rdata=Ext.JSON.decode(response.responseText);
		            			if (rdata.success) {
		                        	Ext.MessageBox.alert("信息","保存成功");
		                            databaseColumnMappingHandsontableHelper.clearContainer();
		                            CreateDatabaseColumnMappingTable(classes,protocolType,protocolCode);
		                            
		                            var gridPanel = Ext.getCmp("DatabaseColumnMappingTableRunStatusItemsGridPanel_Id");
		                            if (isNotVal(gridPanel)) {
		                            	gridPanel.getSelectionModel().deselectAll(true);
		                            	gridPanel.getStore().load();
		                            }
		                        } else {
		                        	Ext.MessageBox.alert("信息","数据保存失败");
		                        }
		            		},
		            		failure:function(){
		            			Ext.MessageBox.alert("信息","请求失败");
		                        databaseColumnMappingHandsontableHelper.clearContainer();
		            		},
		            		params: {
		                    	data: JSON.stringify(databaseColumnMappingHandsontableHelper.AllData),
		                    	protocolType: protocolType
		                    }
		            	}); 
		            } else {
		                if (!databaseColumnMappingHandsontableHelper.validresult) {
		                	Ext.MessageBox.alert("信息","数据类型错误");
		                } else {
		                	Ext.MessageBox.alert("信息","无数据变化");
		                }
		            }
	        	}
	        }
	        
	        
	      //删除的优先级最高
	        databaseColumnMappingHandsontableHelper.delExpressCount=function(ids) {
	            $.each(ids, function (index, id) {
	                if (id != null) {
	                	databaseColumnMappingHandsontableHelper.delidslist.push(id);
	                }
	            });
	            databaseColumnMappingHandsontableHelper.AllData.delidslist = databaseColumnMappingHandsontableHelper.delidslist;
	        }

	        databaseColumnMappingHandsontableHelper.screening=function() {
	            if (databaseColumnMappingHandsontableHelper.updatelist.length != 0 && databaseColumnMappingHandsontableHelper.delidslist.lentgh != 0) {
	                for (var i = 0; i < databaseColumnMappingHandsontableHelper.delidslist.length; i++) {
	                    for (var j = 0; j < databaseColumnMappingHandsontableHelper.updatelist.length; j++) {
	                        if (databaseColumnMappingHandsontableHelper.updatelist[j].id == databaseColumnMappingHandsontableHelper.delidslist[i]) {
	                        	databaseColumnMappingHandsontableHelper.updatelist.splice(j, 1);
	                        }
	                    }
	                }
	                databaseColumnMappingHandsontableHelper.AllData.updatelist = databaseColumnMappingHandsontableHelper.updatelist;
	            }
	        }
	        
	      //更新数据
	        databaseColumnMappingHandsontableHelper.updateExpressCount=function(data) {
	            if (JSON.stringify(data) != "{}") {
	                var flag = true;
	                //判断记录是否存在,更新数据     
	                $.each(databaseColumnMappingHandsontableHelper.updatelist, function (index, node) {
	                    if (node.id == data.id) {
	                        //此记录已经有了
	                        flag = false;
	                        //用新得到的记录替换原来的,不用新增
	                        databaseColumnMappingHandsontableHelper.updatelist[index] = data;
	                    }
	                });
	                flag && databaseColumnMappingHandsontableHelper.updatelist.push(data);
	                //封装
	                databaseColumnMappingHandsontableHelper.AllData.updatelist = databaseColumnMappingHandsontableHelper.updatelist;
	            }
	        }
	        
	        databaseColumnMappingHandsontableHelper.clearContainer = function () {
	        	databaseColumnMappingHandsontableHelper.AllData = {};
	        	databaseColumnMappingHandsontableHelper.updatelist = [];
	        	databaseColumnMappingHandsontableHelper.delidslist = [];
	        	databaseColumnMappingHandsontableHelper.insertlist = [];
	        }
	        return databaseColumnMappingHandsontableHelper;
	    }
};

function createDatabaseColumnMappingTableRunStatusItemsColumn(columnInfo) {
    var myArr = columnInfo;

    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var width_ = "";
        var lock_ = "";
        var hidden_ = "";
        var flex_ = "";
        if (attr.hidden == true) {
            hidden_ = ",hidden:true";
        }
        if (isNotVal(attr.lock)) {
            //lock_ = ",locked:" + attr.lock;
        }
        if (isNotVal(attr.width)) {
            width_ = ",width:" + attr.width;
        }
        if (isNotVal(attr.flex)) {
        	flex_ = ",flex:" + attr.flex;
        }
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' "+width_+flex_;
        if (attr.dataIndex.toUpperCase() == 'id'.toUpperCase()) {
            myColumns += ",xtype: 'rownumberer',sortable : false,locked:false";
        }
        else if (attr.dataIndex.toUpperCase()=='wellName'.toUpperCase()) {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
        }
        else if (attr.dataIndex.toUpperCase()=='commStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceCommStatusColor(value,o,p,e);}";
        }
        else if (attr.dataIndex.toUpperCase()=='runStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceRunStatusColor(value,o,p,e);}";
        }
        else {
            myColumns += hidden_ + lock_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
            //        	myColumns += hidden_ + lock_ + width_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "'";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};

function saveProtocolRunStatusConfig(){
	var runStatusItemsSelection=Ext.getCmp("DatabaseColumnMappingTableRunStatusItemsGridPanel_Id").getSelectionModel().getSelection();
	if(runStatusItemsSelection.length>0){
		var selectedRunStatusItem=runStatusItemsSelection[0];
		protocolCode=selectedRunStatusItem.data.protocolCode;
		itemName=selectedRunStatusItem.data.itemName;
		itemColumn=selectedRunStatusItem.data.itemColumn;
		deviceType=selectedRunStatusItem.data.deviceType;
		
		var runValue="";
		var stopValue="";
		var runValueSelection=Ext.getCmp("DatabaseColumnMappingTableRunStatusMeaningGridPanel1_Id").getSelectionModel().getSelection();
		var stopValueSelection=Ext.getCmp("DatabaseColumnMappingTableRunStatusMeaningGridPanel2_Id").getSelectionModel().getSelection();
		if(runValueSelection.length>0){
			for(var i=0;i<runValueSelection.length;i++){
				runValue+=runValueSelection[i].data.value;
				if(i<runValueSelection.length-1){
					runValue+=",";
				}
			}
		}
		if(stopValueSelection.length>0){
			for(var i=0;i<stopValueSelection.length;i++){
				stopValue+=stopValueSelection[i].data.value;
				if(i<stopValueSelection.length-1){
					stopValue+=",";
				}
			}
		}
		
		Ext.Ajax.request({
			method:'POST',
			url:context + '/acquisitionUnitManagerController/saveProtocolRunStatusConfig',
			success:function(response) {
				Ext.MessageBox.alert("信息","保存成功");
				var gridPanel = Ext.getCmp("DatabaseColumnMappingTableRunStatusMeaningGridPanel1_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getSelectionModel().deselectAll(true);
                	gridPanel.getStore().load();
                }else{
                	Ext.create('AP.store.acquisitionUnit.DatabaseColumnMappingTableRunItemsStore');
                }
                
                var gridPanel2 = Ext.getCmp("DatabaseColumnMappingTableRunStatusMeaningGridPanel2_Id");
                if (isNotVal(gridPanel2)) {
                	gridPanel2.getSelectionModel().deselectAll(true);
                	gridPanel2.getStore().load();
                }else{
                	Ext.create('AP.store.acquisitionUnit.DatabaseColumnMappingTableStopItemsStore');
                }
			},
			failure:function(){
				Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
			},
			params: {
				protocolCode: protocolCode,
				itemName: itemName,
				itemColumn: itemColumn,
				deviceType: deviceType,
				runValue: runValue,
				stopValue: stopValue
	        }
		});
		
		
	}
}