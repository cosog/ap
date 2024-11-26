var importDisplayInstanceAcqItemsHandsontableHelper=null;
var importDisplayInstanceCalItemsHandsontableHelper=null;
var importDisplayInstanceCtrlItemsHandsontableHelper=null;
var importDisplayInstanceInputItemsHandsontableHelper=null;
Ext.define("AP.view.acquisitionUnit.ImportDisplayInstanceWindow", {
    extend: 'Ext.window.Window',
    id: 'ImportDisplayInstanceWindow_Id',
    alias: 'widget.ImportDisplayInstanceWindow',
    layout: 'fit',
    title: '显示实例导入',
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader: true, //True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
    //    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 1500,
    minWidth: 1500,
    height: 700,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            tbar: [{
                xtype: 'form',
                id: 'DisplayInstanceImportForm_Id',
                width: 300,
                bodyPadding: 0,
                frame: true,
                items: [{
                    xtype: 'filefield',
                    id: 'DisplayInstanceImportFilefield_Id',
                    name: 'file',
                    fieldLabel: '上传文件',
                    labelWidth: 60,
                    width: '100%',
                    msgTarget: 'side',
                    allowBlank: true,
                    anchor: '100%',
                    draggable: true,
                    buttonText: '请选择上传文件',
                    accept: '.json',
                    listeners: {
                        change: function (cmp) {
                            submitImportedDisplayInstanceFile();
                        }
                    }
        	    }, {
                    id: 'ImportDisplayInstanceSelectItemType_Id',
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }, {
                    id: 'ImportDisplayInstanceSelectItemId_Id',
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }]
    		}, {
                xtype: 'label',
                id: 'ImportDisplayInstanceWinTabLabel_Id',
                hidden: true,
                html: ''
            }, {
                xtype: "hidden",
                id: 'ImportDisplayInstanceWinDeviceType_Id',
                value: '0'
			}, '->', {
                xtype: 'button',
                text: '全部保存',
                iconCls: 'save',
                handler: function (v, o) {
                    var treeStore = Ext.getCmp("ImportDisplayInstanceContentTreeGridPanel_Id").getStore();
                    var count = treeStore.getCount();
                    var overlayCount = 0;
                    var collisionCount = 0;
                    for (var i = 0; i < count; i++) {
                        if (treeStore.getAt(i).data.classes == 1 && treeStore.getAt(i).data.saveSign == 1) {
                            overlayCount++;
                        } else if (treeStore.getAt(i).data.classes == 1 && treeStore.getAt(i).data.saveSign == 2) {
                            collisionCount++;
                        }
                    }
                    if (overlayCount > 0 || collisionCount > 0) {
                        var info = "";
                        if (overlayCount > 0) {
                            info += overlayCount + "个实例已存在";
                            if (collisionCount > 0) {
                                info += "，";
                            }
                        }
                        if (collisionCount > 0) {
                            info += overlayCount + "个实例无权限修改";
                        }
                        info += "！是否执行全部保存？";

                        Ext.Msg.confirm('提示', info, function (btn) {
                            if (btn == "yes") {
                                saveAllImportedDisplayInstance();
                            }
                        });
                    } else {
                        saveAllImportedDisplayInstance();
                    }
                }
    	    }],
            layout: 'border',
            items: [{
                region: 'west',
                width: '25%',
                title: '上传实例列表',
                layout: 'fit',
                split: true,
                collapsible: true,
                id: "importDisplayInstanceTreePanel_Id"
            }, {
            	border: true,
            	region: 'center',
            	layout: "border",
            	items: [{
            		region: 'center',
            		layout: "border",
            		items: [{
                		region: 'center',
                		title:'采集项配置',
                		id:"importDisplayInstanceAcqItemsConfigTableInfoPanel_Id",
                        layout: 'fit',
                        html:'<div class="importDisplayInstanceAcqItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importDisplayInstanceAcqItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(importDisplayInstanceAcqItemsHandsontableHelper!=null && importDisplayInstanceAcqItemsHandsontableHelper.hot!=undefined){
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		importDisplayInstanceAcqItemsHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                	},{
                		region: 'south',
                    	height:'50%',
                    	title:'控制项配置',
                		id:"importDisplayInstanceCtrlItemsConfigTableInfoPanel_Id",
                        layout: 'fit',
                        collapsible: true,
                        split: true,
                        html:'<div class="importDisplayInstanceCtrlItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importDisplayInstanceCtrlItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	
                            }
                        }
                	}]
            	},{
            		region: 'east',
            		width:'50%',
            		layout: "border",
            		header: false,
            		split: true,
                    collapsible: true,
                	items: [{
                		region: 'center',
                    	layout: 'fit',
                    	title:'录入项配置',
                		id:"importDisplayInstanceInputItemsConfigTableInfoPanel_Id",
                        html:'<div class="importDisplayInstanceInputItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importDisplayInstanceInputItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	
                            }
                        }
                	},{
                		region: 'south',
                    	height:'50%',
                    	layout: 'fit',
                        collapsible: true,
                        split: true,
                    	title:'计算项配置',
                    	id:"importDisplayInstanceCalItemsConfigTableInfoPanel_Id",
                        html:'<div class="importDisplayInstanceCalItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importDisplayInstanceCalItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	
                            }
                        }
                	}]
            	}] 
            }],
            listeners: {
                beforeclose: function (panel, eOpts) {
                	clearImportDisplayInstanceHandsontable();
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function clearImportDisplayInstanceHandsontable(){
	if(importDisplayInstanceAcqItemsHandsontableHelper!=null){
		if(importDisplayInstanceAcqItemsHandsontableHelper.hot!=undefined){
			importDisplayInstanceAcqItemsHandsontableHelper.hot.destroy();
		}
		importDisplayInstanceAcqItemsHandsontableHelper=null;
	}
	if(importDisplayInstanceCalItemsHandsontableHelper!=null){
		if(importDisplayInstanceCalItemsHandsontableHelper.hot!=undefined){
			importDisplayInstanceCalItemsHandsontableHelper.hot.destroy();
		}
		importDisplayInstanceCalItemsHandsontableHelper=null;
	}
	if(importDisplayInstanceCtrlItemsHandsontableHelper!=null){
		if(importDisplayInstanceCtrlItemsHandsontableHelper.hot!=undefined){
			importDisplayInstanceCtrlItemsHandsontableHelper.hot.destroy();
		}
		importDisplayInstanceCtrlItemsHandsontableHelper=null;
	}
	if(importDisplayInstanceInputItemsHandsontableHelper!=null){
		if(importDisplayInstanceInputItemsHandsontableHelper.hot!=undefined){
			importDisplayInstanceInputItemsHandsontableHelper.hot.destroy();
		}
		importDisplayInstanceInputItemsHandsontableHelper=null;
	}
}

function submitImportedDisplayInstanceFile() {
	clearImportDisplayInstanceHandsontable();
	var form = Ext.getCmp("DisplayInstanceImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedDisplayInstanceFile',
            timeout: 1000*60*10,
            method:'post',
            waitMsg: '文件上传中...',
            success: function(response, action) {
            	var result = action.result;
            	if (result.flag == true) {
            		Ext.Msg.alert("提示", "加载成功 ");
            	}else{
            		Ext.Msg.alert("提示", "上传数据格式有误！ ");
            	}
            	
            	
            	var importDisplayInstanceContentTreeGridPanel = Ext.getCmp("ImportDisplayInstanceContentTreeGridPanel_Id");
            	if (isNotVal(importDisplayInstanceContentTreeGridPanel)) {
            		importDisplayInstanceContentTreeGridPanel.getStore().load();
            	}else{
            		Ext.create('AP.store.acquisitionUnit.ImportDisplayInstanceContentTreeInfoStore');
            	}
            },
            failure : function() {
            	Ext.Msg.alert("提示", "【<font color=red>上传失败 </font>】");
			}
        });
    }
    return false;
};


adviceImportDisplayInstanceCollisionInfoColor = function(val,o,p,e) {
	var saveSign=p.data.saveSign;
	var tipval=val;
	var backgroundColor='#FFFFFF';
 	var color='#DC2828';
 	if(saveSign==0){
 		color='#000000';
 	}
 	var opacity=0;
 	var rgba=color16ToRgba(backgroundColor,opacity);
 	o.style='background-color:'+rgba+';color:'+color+';';
 	if(isNotVal(tipval)){
 		return '<span data-qtip="'+tipval+'" data-dismissDelay=10000>'+val+'</span>';
 	}
}

iconImportSingleDisplayInstanceAction = function(value, e, record) {
	var resultstring='';
	var instanceName=record.data.text;
	var displayUnitName=record.data.displayUnitName;
	var acqUnitName=record.data.acqUnitName;
	var protocolName=record.data.protocol;

	if( record.data.classes==1 && record.data.saveSign!=2 ){
		resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
		"onclick=saveSingelImportedAcqInstance(\""+instanceName+"\",\""+displayUnitName+"\,\""+acqUnitName+"\,\""+protocolName+"\")>保存...</a>";
	}
	return resultstring;
}

function saveSingelImportedDisplayInstance(instanceName,displayUnitName,acqUnitName,protocolName){
	Ext.Ajax.request({
		url : context + '/acquisitionUnitManagerController/saveSingelImportedDisplayInstance',
		method : "POST",
		params : {
			instanceName : instanceName,
			displayUnitName : displayUnitName,
			acqUnitName:acqUnitName,
			protocolName : protocolName
		},
		success : function(response) {
			var result = Ext.JSON.decode(response.responseText);
			if (result.success==true) {
				Ext.Msg.alert('提示', "<font color=blue>保存成功。</font>");
			}else{
				Ext.Msg.alert('提示', "<font color=red>保存失败。</font>");
			}
			Ext.getCmp("ImportDisplayInstanceContentTreeGridPanel_Id").getStore().load();

			var treeGridPanel = Ext.getCmp("ModbusProtocolDisplayInstanceConfigTreeGridPanel_Id");
            if (isNotVal(treeGridPanel)) {
            	treeGridPanel.getStore().load();
            }else{
            	Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayInstanceTreeInfoStore');
            }
		},
		failure : function() {
			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
		}
	});
}

function saveAllImportedDisplayInstance(){
	var unitNameList=[];
	var treeStore = Ext.getCmp("ImportDisplayInstanceContentTreeGridPanel_Id").getStore();
	var count=treeStore.getCount();
	for(var i=0;i<count;i++){
		if(treeStore.getAt(i).data.classes==1 && treeStore.getAt(i).data.saveSign!=2){
			unitNameList.push(treeStore.getAt(i).data.text);
		}
	}
	Ext.Ajax.request({
		url : context + '/acquisitionUnitManagerController/saveAllImportedDisplayInstance',
		method : "POST",
		params : {
			unitName : unitNameList.join(",")
		},
		success : function(response) {
			var result = Ext.JSON.decode(response.responseText);
			if (result.success==true) {
				Ext.Msg.alert('提示', "<font color=blue>保存成功。</font>");
			}else{
				Ext.Msg.alert('提示', "<font color=red>保存失败。</font>");
			}
			Ext.getCmp("ImportDisplayInstanceContentTreeGridPanel_Id").getStore().load();

			var treeGridPanel = Ext.getCmp("ModbusProtocolDisplayInstanceConfigTreeGridPanel_Id");
            if (isNotVal(treeGridPanel)) {
            	treeGridPanel.getStore().load();
            }else{
            	Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayInstanceTreeInfoStore');
            }
		},
		failure : function() {
			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
		}
	});
}

function CreateImportDisplayInstanceAcqItemsInfoTable(protocolName,acqUnitName,displayUnitName,instanceName){
	Ext.getCmp("importDisplayInstanceAcqItemsConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportDisplayInstanceItemsConfigData',
		success:function(response) {
			Ext.getCmp("importDisplayInstanceAcqItemsConfigTableInfoPanel_Id").getEl().unmask();
			if(instanceName!=''){
				Ext.getCmp("importDisplayInstanceAcqItemsConfigTableInfoPanel_Id").setTitle(instanceName+"/采集项");
			}else{
				Ext.getCmp("importDisplayInstanceAcqItemsConfigTableInfoPanel_Id").setTitle("采集项");
			}
			
			var result =  Ext.JSON.decode(response.responseText);
			if(importDisplayInstanceAcqItemsHandsontableHelper==null || importDisplayInstanceAcqItemsHandsontableHelper.hot==undefined){
				importDisplayInstanceAcqItemsHandsontableHelper = ImportDisplayInstanceAcqItemsHandsontableHelper.createNew("importDisplayInstanceAcqItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','单位','显示级别','实时字段顺序','历史字段顺序','实时曲线','历史曲线']";
				var colHeaders = "[" 
                	+"['','','','',{label: '实时动态数据', colspan: 4},{label: '历史数据', colspan: 4}]," 
                	+"['序号','名称','单位','显示级别'," 
                	+"'顺序','前景色','背景色','曲线'," 
                	+"'顺序','前景色','背景色','曲线']"
                	+"]";
				var columns="["
					+"{data:'id'}," 
					+"{data:'title'},"
					+"{data:'unit'},"
					+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
					+"{data:'realtimeSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}},"
					+"{data:'realtimeColor'}," 
                    +"{data:'realtimeBgColor'}," 
					+"{data:'realtimeCurveConfShowValue'},"
					+"{data:'historySort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
					+"{data:'historyColor'}," 
                    +"{data:'historyBgColor'}," 
					+"{data:'historyCurveConfShowValue'}"
					+"]";
				importDisplayInstanceAcqItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importDisplayInstanceAcqItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importDisplayInstanceAcqItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayInstanceAcqItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importDisplayInstanceAcqItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayInstanceAcqItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importDisplayInstanceAcqItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			acqUnitName:acqUnitName,
			displayUnitName:displayUnitName,
			instanceName:instanceName,
			type:0
        }
	});
};

var ImportDisplayInstanceAcqItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importDisplayInstanceAcqItemsHandsontableHelper = {};
	        importDisplayInstanceAcqItemsHandsontableHelper.hot1 = '';
	        importDisplayInstanceAcqItemsHandsontableHelper.divid = divid;
	        importDisplayInstanceAcqItemsHandsontableHelper.validresult=true;//数据校验
	        importDisplayInstanceAcqItemsHandsontableHelper.colHeaders=[];
	        importDisplayInstanceAcqItemsHandsontableHelper.columns=[];
	        importDisplayInstanceAcqItemsHandsontableHelper.AllData=[];
	        
	        importDisplayInstanceAcqItemsHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==3){
	            		td.style.backgroundColor = '#'+arr[2];
	            	}
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importDisplayInstanceAcqItemsHandsontableHelper.addCellBgColor = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (value != null) {
	                td.style.backgroundColor = '#' + value;
	            }
	            td.style.whiteSpace = 'nowrap'; //文本不换行
	            td.style.overflow = 'hidden'; //超出部分隐藏
	            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
	        }
	        
	        importDisplayInstanceAcqItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importDisplayInstanceAcqItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importDisplayInstanceAcqItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importDisplayInstanceAcqItemsHandsontableHelper.divid);
	        	importDisplayInstanceAcqItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,80,80,80,80,80,80,80,80],
	                columns:importDisplayInstanceAcqItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
//	                colHeaders:importDisplayInstanceAcqItemsHandsontableHelper.colHeaders,//显示列头
	                nestedHeaders:importDisplayInstanceAcqItemsHandsontableHelper.colHeaders,//显示列头
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

	                    cellProperties.readOnly = true;
	                    if(visualColIndex==7||visualColIndex==11){
		                	cellProperties.renderer = importDisplayInstanceAcqItemsHandsontableHelper.addCurveBg;
		                }else if (visualColIndex == 5 || visualColIndex == 6 || visualColIndex == 9 || visualColIndex == 10) {
                            cellProperties.renderer = importDisplayInstanceAcqItemsHandsontableHelper.addCellBgColor;
                        }else{
		                	cellProperties.renderer = importDisplayInstanceAcqItemsHandsontableHelper.addCellStyle;
		                }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(importDisplayInstanceAcqItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importDisplayInstanceAcqItemsHandsontableHelper!=null
	                		&& importDisplayInstanceAcqItemsHandsontableHelper.hot!=''
	                		&& importDisplayInstanceAcqItemsHandsontableHelper.hot!=undefined 
	                		&& importDisplayInstanceAcqItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importDisplayInstanceAcqItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	        	});
	        }
	        return importDisplayInstanceAcqItemsHandsontableHelper;
	    }
};

function CreateImportDisplayInstanceCalItemsInfoTable(protocolName,acqUnitName,displayUnitName,instanceName){
	Ext.getCmp("importDisplayInstanceCalItemsConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportDisplayInstanceItemsConfigData',
		success:function(response) {
			Ext.getCmp("importDisplayInstanceCalItemsConfigTableInfoPanel_Id").getEl().unmask();
			if(instanceName!=''){
				Ext.getCmp("importDisplayInstanceCalItemsConfigTableInfoPanel_Id").setTitle(instanceName+"/计算项");
			}else{
				Ext.getCmp("importDisplayInstanceCalItemsConfigTableInfoPanel_Id").setTitle("计算项");
			}
			var result =  Ext.JSON.decode(response.responseText);
			if(importDisplayInstanceCalItemsHandsontableHelper==null || importDisplayInstanceCalItemsHandsontableHelper.hot==undefined){
				importDisplayInstanceCalItemsHandsontableHelper = ImportDisplayInstanceCalItemsHandsontableHelper.createNew("importDisplayInstanceCalItemsConfigTableInfoDiv_id");
				var colHeaders="[" 
					+"['','','','',{label: '实时动态数据', colspan: 4},{label: '历史数据', colspan: 4},'']," 
					+"['序号','名称','单位','显示级别'," 
					+"'顺序','前景色','背景色','曲线'," 
					+"'顺序','前景色','背景色','曲线'," 
					+"'数据来源']" 
					+"]";
				var columns="["
					+"{data:'id'}," 
					+"{data:'title'},"
					+"{data:'unit'},"
					+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayInstanceCalItemsHandsontableHelper);}}," 
					+"{data:'realtimeSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayInstanceCalItemsHandsontableHelper);}}," 
					+"{data:'realtimeColor'}," 
                    +"{data:'realtimeBgColor'}," 
					+"{data:'realtimeCurveConfShowValue'},"
					+"{data:'historySort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayInstanceCalItemsHandsontableHelper);}}," 
					+"{data:'historyColor'}," 
                    +"{data:'historyBgColor'}," 
					+"{data:'historyCurveConfShowValue'},"
					+"{data:'dataSource'}"
					+"]";
				importDisplayInstanceCalItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importDisplayInstanceCalItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importDisplayInstanceCalItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayInstanceCalItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importDisplayInstanceCalItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayInstanceCalItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importDisplayInstanceCalItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			acqUnitName:acqUnitName,
			displayUnitName:displayUnitName,
			instanceName:instanceName,
			type:1
        }
	});
};

var ImportDisplayInstanceCalItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importDisplayInstanceCalItemsHandsontableHelper = {};
	        importDisplayInstanceCalItemsHandsontableHelper.hot1 = '';
	        importDisplayInstanceCalItemsHandsontableHelper.divid = divid;
	        importDisplayInstanceCalItemsHandsontableHelper.validresult=true;//数据校验
	        importDisplayInstanceCalItemsHandsontableHelper.colHeaders=[];
	        importDisplayInstanceCalItemsHandsontableHelper.columns=[];
	        importDisplayInstanceCalItemsHandsontableHelper.AllData=[];
	        
	        importDisplayInstanceCalItemsHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==3){
	            		td.style.backgroundColor = '#'+arr[2];
	            	}
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importDisplayInstanceCalItemsHandsontableHelper.addCellBgColor = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (value != null) {
	                td.style.backgroundColor = '#' + value;
	            }
	            td.style.whiteSpace = 'nowrap'; //文本不换行
	            td.style.overflow = 'hidden'; //超出部分隐藏
	            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
	        }
	        
	        importDisplayInstanceCalItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importDisplayInstanceCalItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importDisplayInstanceCalItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importDisplayInstanceCalItemsHandsontableHelper.divid);
	        	importDisplayInstanceCalItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,80,80,80,80,80,80,80,80,80],
	                columns:importDisplayInstanceCalItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
//	                colHeaders:importDisplayInstanceCalItemsHandsontableHelper.colHeaders,//显示列头
	                nestedHeaders:importDisplayInstanceCalItemsHandsontableHelper.colHeaders,//显示列头
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

	                    cellProperties.readOnly = true;
	                    if(visualColIndex==7||visualColIndex==11){
		                	cellProperties.renderer = importDisplayInstanceCalItemsHandsontableHelper.addCurveBg;
		                }else if (visualColIndex == 5 || visualColIndex == 6 || visualColIndex == 9 || visualColIndex == 10) {
                            cellProperties.renderer = importDisplayInstanceCalItemsHandsontableHelper.addCellBgColor;
                        }else{
		                	if(importDisplayInstanceCalItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    	            	&& importDisplayInstanceCalItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		                    	cellProperties.renderer = importDisplayInstanceCalItemsHandsontableHelper.addCellStyle;
		    	            }
		                }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(importDisplayInstanceCalItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importDisplayInstanceCalItemsHandsontableHelper!=null
	                		&& importDisplayInstanceCalItemsHandsontableHelper.hot!=''
	                		&& importDisplayInstanceCalItemsHandsontableHelper.hot!=undefined 
	                		&& importDisplayInstanceCalItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importDisplayInstanceCalItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	        	});
	        }
	        return importDisplayInstanceCalItemsHandsontableHelper;
	    }
};

function CreateImportDisplayInstanceCtrlItemsInfoTable(protocolName,acqUnitName,displayUnitName,instanceName){
	Ext.getCmp("importDisplayInstanceCtrlItemsConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportDisplayInstanceItemsConfigData',
		success:function(response) {
			Ext.getCmp("importDisplayInstanceCtrlItemsConfigTableInfoPanel_Id").getEl().unmask();
			if(instanceName!=''){
				Ext.getCmp("importDisplayInstanceCtrlItemsConfigTableInfoPanel_Id").setTitle(instanceName+"/控制项");
			}else{
				Ext.getCmp("importDisplayInstanceCtrlItemsConfigTableInfoPanel_Id").setTitle("控制项");
			}
			var result =  Ext.JSON.decode(response.responseText);
			if(importDisplayInstanceCtrlItemsHandsontableHelper==null || importDisplayInstanceCtrlItemsHandsontableHelper.hot==undefined){
				importDisplayInstanceCtrlItemsHandsontableHelper = ImportDisplayInstanceCtrlItemsHandsontableHelper.createNew("importDisplayInstanceCtrlItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','单位','显示级别','字段顺序']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel'}," 
						+"{data:'realtimeSort'}"
						+"]";
				importDisplayInstanceCtrlItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importDisplayInstanceCtrlItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importDisplayInstanceCtrlItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayInstanceCtrlItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importDisplayInstanceCtrlItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayInstanceCtrlItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importDisplayInstanceCtrlItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			acqUnitName:acqUnitName,
			displayUnitName:displayUnitName,
			instanceName:instanceName,
			type:2
        }
	});
};

var ImportDisplayInstanceCtrlItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importDisplayInstanceCtrlItemsHandsontableHelper = {};
	        importDisplayInstanceCtrlItemsHandsontableHelper.hot1 = '';
	        importDisplayInstanceCtrlItemsHandsontableHelper.divid = divid;
	        importDisplayInstanceCtrlItemsHandsontableHelper.validresult=true;//数据校验
	        importDisplayInstanceCtrlItemsHandsontableHelper.colHeaders=[];
	        importDisplayInstanceCtrlItemsHandsontableHelper.columns=[];
	        importDisplayInstanceCtrlItemsHandsontableHelper.AllData=[];
	        
	        importDisplayInstanceCtrlItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importDisplayInstanceCtrlItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importDisplayInstanceCtrlItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importDisplayInstanceCtrlItemsHandsontableHelper.divid);
	        	importDisplayInstanceCtrlItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,60],
	                columns:importDisplayInstanceCtrlItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importDisplayInstanceCtrlItemsHandsontableHelper.colHeaders,//显示列头
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
	                    cellProperties.readOnly = true;
	                    if(importDisplayInstanceCtrlItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& importDisplayInstanceCtrlItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = importDisplayInstanceCtrlItemsHandsontableHelper.addCellStyle;
	    	            }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(importDisplayInstanceCtrlItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importDisplayInstanceCtrlItemsHandsontableHelper!=null
	                		&& importDisplayInstanceCtrlItemsHandsontableHelper.hot!=''
	                		&& importDisplayInstanceCtrlItemsHandsontableHelper.hot!=undefined 
	                		&& importDisplayInstanceCtrlItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importDisplayInstanceCtrlItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	        	});
	        }
	        return importDisplayInstanceCtrlItemsHandsontableHelper;
	    }
};

function CreateImportDisplayInstanceInputItemsInfoTable(protocolName,acqUnitName,displayUnitName,instanceName){
	Ext.getCmp("importDisplayInstanceInputItemsConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportDisplayInstanceItemsConfigData',
		success:function(response) {
			Ext.getCmp("importDisplayInstanceInputItemsConfigTableInfoPanel_Id").getEl().unmask();
			if(instanceName!=''){
				Ext.getCmp("importDisplayInstanceInputItemsConfigTableInfoPanel_Id").setTitle(instanceName+"/录入项");
			}else{
				Ext.getCmp("importDisplayInstanceInputItemsConfigTableInfoPanel_Id").setTitle("录入项");
			}
			var result =  Ext.JSON.decode(response.responseText);
			if(importDisplayInstanceInputItemsHandsontableHelper==null || importDisplayInstanceInputItemsHandsontableHelper.hot==undefined){
				importDisplayInstanceInputItemsHandsontableHelper = ImportDisplayInstanceInputItemsHandsontableHelper.createNew("importDisplayInstanceInputItemsConfigTableInfoDiv_id");
				var colHeaders="[" 
					+"['','','','',{label: '实时动态数据', colspan: 4},{label: '历史数据', colspan: 4}]," 
					+"['序号','名称','单位','显示级别'," 
					+"'顺序','前景色','背景色','曲线'," 
					+"'顺序','前景色','背景色','曲线']" 
					+"]";
				var columns="["
					+"{data:'id'}," 
					+"{data:'title'},"
					+"{data:'unit'},"
					+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayInstanceInputItemsHandsontableHelper);}}," 
					+"{data:'realtimeSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayInstanceInputItemsHandsontableHelper);}}," 
					+"{data:'realtimeColor'}," 
                    +"{data:'realtimeBgColor'}," 
					+"{data:'realtimeCurveConfShowValue'},"
					+"{data:'historySort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayInstanceInputItemsHandsontableHelper);}}," 
					+"{data:'historyColor'}," 
                    +"{data:'historyBgColor'}," 
					+"{data:'historyCurveConfShowValue'}"
					+"]";
				importDisplayInstanceInputItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importDisplayInstanceInputItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importDisplayInstanceInputItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayInstanceInputItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importDisplayInstanceInputItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayInstanceInputItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importDisplayInstanceInputItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			acqUnitName:acqUnitName,
			displayUnitName:displayUnitName,
			instanceName:instanceName,
			type:3
        }
	});
};

var ImportDisplayInstanceInputItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importDisplayInstanceInputItemsHandsontableHelper = {};
	        importDisplayInstanceInputItemsHandsontableHelper.hot1 = '';
	        importDisplayInstanceInputItemsHandsontableHelper.divid = divid;
	        importDisplayInstanceInputItemsHandsontableHelper.validresult=true;//数据校验
	        importDisplayInstanceInputItemsHandsontableHelper.colHeaders=[];
	        importDisplayInstanceInputItemsHandsontableHelper.columns=[];
	        importDisplayInstanceInputItemsHandsontableHelper.AllData=[];
	        
	        importDisplayInstanceInputItemsHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==3){
	            		td.style.backgroundColor = '#'+arr[2];
	            	}
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importDisplayInstanceInputItemsHandsontableHelper.addCellBgColor = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (value != null) {
	                td.style.backgroundColor = '#' + value;
	            }
	            td.style.whiteSpace = 'nowrap'; //文本不换行
	            td.style.overflow = 'hidden'; //超出部分隐藏
	            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
	        }
	        
	        importDisplayInstanceInputItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importDisplayInstanceInputItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importDisplayInstanceInputItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importDisplayInstanceInputItemsHandsontableHelper.divid);
	        	importDisplayInstanceInputItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,80,80,80,80,80,80,80,80],
	                columns:importDisplayInstanceInputItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
//	                colHeaders:importDisplayInstanceInputItemsHandsontableHelper.colHeaders,//显示列头
	                nestedHeaders:importDisplayInstanceInputItemsHandsontableHelper.colHeaders,//显示列头
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

	                    cellProperties.readOnly = true;
	                    if(visualColIndex==7||visualColIndex==11){
		                	cellProperties.renderer = importDisplayInstanceInputItemsHandsontableHelper.addCurveBg;
		                }else if (visualColIndex == 5 || visualColIndex == 6 || visualColIndex == 9 || visualColIndex == 10) {
                            cellProperties.renderer = importDisplayInstanceInputItemsHandsontableHelper.addCellBgColor;
                        }else{
		                	if(importDisplayInstanceInputItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    	            	&& importDisplayInstanceInputItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		                    	cellProperties.renderer = importDisplayInstanceInputItemsHandsontableHelper.addCellStyle;
		    	            }
		                }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(importDisplayInstanceInputItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importDisplayInstanceInputItemsHandsontableHelper!=null
	                		&& importDisplayInstanceInputItemsHandsontableHelper.hot!=''
	                		&& importDisplayInstanceInputItemsHandsontableHelper.hot!=undefined 
	                		&& importDisplayInstanceInputItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importDisplayInstanceInputItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	        	});
	        }
	        return importDisplayInstanceInputItemsHandsontableHelper;
	    }
};