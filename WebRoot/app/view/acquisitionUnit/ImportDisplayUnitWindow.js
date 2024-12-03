var importDisplayUnitAcqItemsHandsontableHelper=null;
var importDisplayUnitCtrlItemsHandsontableHelper=null;
var importDisplayUnitCalItemsHandsontableHelper=null;
var importDisplayUnitInputItemsHandsontableHelper=null;
Ext.define("AP.view.acquisitionUnit.ImportDisplayUnitWindow", {
    extend: 'Ext.window.Window',
    id: 'ImportDisplayUnitWindow_Id',
    alias: 'widget.ImportDisplayUnitWindow',
    layout: 'fit',
    title: loginUserLanguageResource.importData,
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
                id: 'DisplayUnitImportForm_Id',
                width: 300,
                bodyPadding: 0,
                frame: true,
                items: [{
                    xtype: 'filefield',
                    id: 'DisplayUnitImportFilefield_Id',
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
                            submitImportedDisplayUnitFile();
                        }
                    }
        	    }, {
                    id: 'ImportDisplayUnitSelectItemType_Id',
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }, {
                    id: 'ImportDisplayUnitSelectItemId_Id',
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }]
    		}, {
                xtype: 'label',
                id: 'ImportDisplayUnitWinTabLabel_Id',
                hidden: true,
                html: ''
            }, {
                xtype: "hidden",
                id: 'ImportDisplayUnitWinDeviceType_Id',
                value: '0'
			}, '->', {
                xtype: 'button',
                text: '全部保存',
                iconCls: 'save',
                handler: function (v, o) {
                    var treeStore = Ext.getCmp("ImportDisplayUnitContentTreeGridPanel_Id").getStore();
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
                            info += overlayCount + "个单元已存在";
                            if (collisionCount > 0) {
                                info += "，";
                            }
                        }
                        if (collisionCount > 0) {
                            info += overlayCount + "个单元无权限修改";
                        }
                        info += "！是否执行全部保存？";

                        Ext.Msg.confirm(loginUserLanguageResource.tip, info, function (btn) {
                            if (btn == "yes") {
                                saveAllImportedDisplayUnit();
                            }
                        });
                    } else {
                        saveAllImportedDisplayUnit();
                    }
                }
    	    }],
            layout: 'border',
            items: [{
                region: 'west',
                width: '25%',
                title: loginUserLanguageResource.uploadUnitList,
                layout: 'fit',
                split: true,
                collapsible: true,
                id: "importDisplayUnitTreePanel_Id"
            }, {
            	border: true,
            	region: 'center',
            	layout: "border",
            	items: [{
            		region: 'center',
            		layout: "border",
            		items: [{
                		region: 'center',
                		title:loginUserLanguageResource.acquisitionItemConfig,
                		id:"importDisplayUnitAcqItemsConfigTableInfoPanel_Id",
                        layout: 'fit',
                        html:'<div class="importDisplayUnitAcqItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importDisplayUnitAcqItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(importDisplayUnitAcqItemsHandsontableHelper!=null && importDisplayUnitAcqItemsHandsontableHelper.hot!=undefined){
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		importDisplayUnitAcqItemsHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                	},{
                		region: 'south',
                    	height:'50%',
                    	title:loginUserLanguageResource.controlItemConfig,
                		id:"importDisplayUnitCtrlItemsConfigTableInfoPanel_Id",
                        layout: 'fit',
                        collapsible: true,
                        split: true,
                        html:'<div class="importDisplayUnitCtrlItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importDisplayUnitCtrlItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(importDisplayUnitCtrlItemsHandsontableHelper!=null && importDisplayUnitCtrlItemsHandsontableHelper.hot!=undefined){
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		importDisplayUnitCtrlItemsHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
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
                    	title:loginUserLanguageResource.inputItemConfig,
                		id:"importDisplayUnitInputItemsConfigTableInfoPanel_Id",
                        html:'<div class="importDisplayUnitInputItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importDisplayUnitInputItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(importDisplayUnitInputItemsHandsontableHelper!=null && importDisplayUnitInputItemsHandsontableHelper.hot!=undefined){
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		importDisplayUnitInputItemsHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                	},{
                		region: 'south',
                    	height:'50%',
                    	layout: 'fit',
                        collapsible: true,
                        split: true,
                    	title:loginUserLanguageResource.calculateItemConfig,
                    	id:"importDisplayUnitCalItemsConfigTableInfoPanel_Id",
                        html:'<div class="importDisplayUnitCalItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importDisplayUnitCalItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(importDisplayUnitCalItemsHandsontableHelper!=null && importDisplayUnitCalItemsHandsontableHelper.hot!=undefined){
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		importDisplayUnitCalItemsHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                	}]
            	}] 
            }],
            listeners: {
                beforeclose: function (panel, eOpts) {
                	clearImportDisplayUnitHandsontable();
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function clearImportDisplayUnitHandsontable(){
	if(importDisplayUnitAcqItemsHandsontableHelper!=null){
		if(importDisplayUnitAcqItemsHandsontableHelper.hot!=undefined){
			importDisplayUnitAcqItemsHandsontableHelper.hot.destroy();
		}
		importDisplayUnitAcqItemsHandsontableHelper=null;
	}
	
	if(importDisplayUnitCtrlItemsHandsontableHelper!=null){
		if(importDisplayUnitCtrlItemsHandsontableHelper.hot!=undefined){
			importDisplayUnitCtrlItemsHandsontableHelper.hot.destroy();
		}
		importDisplayUnitCtrlItemsHandsontableHelper=null;
	}
	
	if(importDisplayUnitCalItemsHandsontableHelper!=null){
		if(importDisplayUnitCalItemsHandsontableHelper.hot!=undefined){
			importDisplayUnitCalItemsHandsontableHelper.hot.destroy();
		}
		importDisplayUnitCalItemsHandsontableHelper=null;
	}
	
	if(importDisplayUnitInputItemsHandsontableHelper!=null){
		if(importDisplayUnitInputItemsHandsontableHelper.hot!=undefined){
			importDisplayUnitInputItemsHandsontableHelper.hot.destroy();
		}
		importDisplayUnitInputItemsHandsontableHelper=null;
	}
}

function submitImportedDisplayUnitFile() {
	clearImportDisplayUnitHandsontable();
	var form = Ext.getCmp("DisplayUnitImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedDisplayUnitFile',
            timeout: 1000*60*10,
            method:'post',
            waitMsg: '文件上传中...',
            success: function(response, action) {
            	var result = action.result;
            	if (result.flag == true) {
            		Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
            	}else{
            		Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
            	}
            	
            	
            	var importDisplayUnitContentTreeGridPanel = Ext.getCmp("ImportDisplayUnitContentTreeGridPanel_Id");
            	if (isNotVal(importDisplayUnitContentTreeGridPanel)) {
            		importDisplayUnitContentTreeGridPanel.getStore().load();
            	}else{
            		Ext.create('AP.store.acquisitionUnit.ImportDisplayUnitContentTreeInfoStore');
            	}
            },
            failure : function() {
            	Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.uploadFail+"</font>】");
			}
        });
    }
    return false;
};


adviceImportDisplayUnitCollisionInfoColor = function(val,o,p,e) {
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

iconImportSingleDisplayUnitAction = function(value, e, record) {
	var resultstring='';
	var unitName=record.data.text;
	var acqUnit=record.data.acqUnit;
	var protocolName=record.data.protocol;

	if( record.data.classes==1 && record.data.saveSign!=2 ){
		resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
		"onclick=saveSingelImportedDisplayUnit(\""+unitName+"\",\""+acqUnit+"\",\""+protocolName+"\")>"+loginUserLanguageResource.save+"...</a>";
	}
	return resultstring;
}

function saveSingelImportedDisplayUnit(unitName,acqUnit,protocolName){
	Ext.Ajax.request({
		url : context + '/acquisitionUnitManagerController/saveSingelImportedDisplayUnit',
		method : "POST",
		params : {
			unitName : unitName,
			acqUnit : acqUnit,
			protocolName : protocolName
		},
		success : function(response) {
			var result = Ext.JSON.decode(response.responseText);
			if (result.success==true) {
				Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
			}else{
				Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
			}
			Ext.getCmp("ImportDisplayUnitContentTreeGridPanel_Id").getStore().load();

			var treeGridPanel = Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id");
            if (isNotVal(treeGridPanel)) {
            	treeGridPanel.getStore().load();
            }else{
            	Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayUnitTreeInfoStore');
            }
		},
		failure : function() {
			Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
		}
	});
}

function saveAllImportedDisplayUnit(){
	var unitNameList=[];
	var treeStore = Ext.getCmp("ImportDisplayUnitContentTreeGridPanel_Id").getStore();
	var count=treeStore.getCount();
	for(var i=0;i<count;i++){
		if(treeStore.getAt(i).data.classes==1 && treeStore.getAt(i).data.saveSign!=2){
			unitNameList.push(treeStore.getAt(i).data.text);
		}
	}
	Ext.Ajax.request({
		url : context + '/acquisitionUnitManagerController/saveAllImportedDisplayUnit',
		method : "POST",
		params : {
			unitName : unitNameList.join(",")
		},
		success : function(response) {
			var result = Ext.JSON.decode(response.responseText);
			if (result.success==true) {
				Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
			}else{
				Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
			}
			Ext.getCmp("ImportDisplayUnitContentTreeGridPanel_Id").getStore().load();

			var treeGridPanel = Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id");
            if (isNotVal(treeGridPanel)) {
            	treeGridPanel.getStore().load();
            }else{
            	Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayUnitTreeInfoStore');
            }
		},
		failure : function() {
			Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
		}
	});
}

function CreateImportDisplayUnitAcqItemsInfoTable(protocolName,acqUnitName,unitName,calculateType){
	Ext.getCmp("importDisplayUnitAcqItemsConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportDisplayUnitItemsConfigData',
		success:function(response) {
			Ext.getCmp("importDisplayUnitAcqItemsConfigTableInfoPanel_Id").getEl().unmask();
			if(unitName!=''){
				Ext.getCmp("importDisplayUnitAcqItemsConfigTableInfoPanel_Id").setTitle(unitName+"/"+loginUserLanguageResource.acquisitionItem);
			}else{
				Ext.getCmp("importDisplayUnitAcqItemsConfigTableInfoPanel_Id").setTitle(loginUserLanguageResource.acquisitionItem);
			}
			
			var result =  Ext.JSON.decode(response.responseText);
			if(importDisplayUnitAcqItemsHandsontableHelper==null || importDisplayUnitAcqItemsHandsontableHelper.hot==undefined){
				importDisplayUnitAcqItemsHandsontableHelper = ImportDisplayUnitAcqItemsHandsontableHelper.createNew("importDisplayUnitAcqItemsConfigTableInfoDiv_id");
				var colHeaders = "[" 
                	+"['','','','',{label: '实时动态数据', colspan: 4},{label: '"+loginUserLanguageResource.historyData+"', colspan: 4}]," 
                	+"['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.unit+"','"+loginUserLanguageResource.showLevel+"'," 
                	+"'顺序','"+loginUserLanguageResource.foregroundColor+"','"+loginUserLanguageResource.backgroundColor+"','曲线'," 
                	+"'顺序','"+loginUserLanguageResource.foregroundColor+"','"+loginUserLanguageResource.backgroundColor+"','曲线']"
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
				importDisplayUnitAcqItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importDisplayUnitAcqItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importDisplayUnitAcqItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayUnitAcqItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importDisplayUnitAcqItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayUnitAcqItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importDisplayUnitAcqItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName: protocolName,
			acqUnitName: acqUnitName,
			unitName: unitName,
			calculateType: calculateType,
			type: 0
        }
	});
};

var ImportDisplayUnitAcqItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importDisplayUnitAcqItemsHandsontableHelper = {};
	        importDisplayUnitAcqItemsHandsontableHelper.hot1 = '';
	        importDisplayUnitAcqItemsHandsontableHelper.divid = divid;
	        importDisplayUnitAcqItemsHandsontableHelper.validresult=true;//数据校验
	        importDisplayUnitAcqItemsHandsontableHelper.colHeaders=[];
	        importDisplayUnitAcqItemsHandsontableHelper.columns=[];
	        importDisplayUnitAcqItemsHandsontableHelper.AllData=[];
	        
	        importDisplayUnitAcqItemsHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            var bg='rgb(245, 245, 245)';
	            if(value!=null && value!=""){
	            	var arr=value.split(';');
	            	if(arr.length==3){
	            		bg = '#'+arr[2];
	            	}
	            }
	            td.style.backgroundColor = bg;
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importDisplayUnitAcqItemsHandsontableHelper.addCellBgColor = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (value != null && value!="") {
	                td.style.backgroundColor = '#' + value;
	            }else{
	            	td.style.backgroundColor = 'rgb(245, 245, 245)';
	            }
	            td.style.whiteSpace = 'nowrap'; //文本不换行
	            td.style.overflow = 'hidden'; //超出部分隐藏
	            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
	        }
	        
	        importDisplayUnitAcqItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(importDisplayUnitAcqItemsHandsontableHelper.columns[col].type=='checkbox'){
	            	td.style.backgroundColor = 'rgb(245, 245, 245)';
	        	}else if(importDisplayUnitAcqItemsHandsontableHelper.columns[col].type=='dropdown'){
		            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
		            td.style.backgroundColor = 'rgb(245, 245, 245)';
		            td.style.whiteSpace='nowrap'; //文本不换行
	            	td.style.overflow='hidden';//超出部分隐藏
	            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
		        }else{
		            Handsontable.renderers.TextRenderer.apply(this, arguments);
		            td.style.backgroundColor = 'rgb(245, 245, 245)';
		            td.style.whiteSpace='nowrap'; //文本不换行
	            	td.style.overflow='hidden';//超出部分隐藏
	            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
		        }
	        }
	        
	        importDisplayUnitAcqItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importDisplayUnitAcqItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importDisplayUnitAcqItemsHandsontableHelper.divid);
	        	importDisplayUnitAcqItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,80,80,80,80,80,80,80,80],
	                columns:importDisplayUnitAcqItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
//	                colHeaders:importDisplayUnitAcqItemsHandsontableHelper.colHeaders,//显示列头
	                nestedHeaders:importDisplayUnitAcqItemsHandsontableHelper.colHeaders,//显示列头
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
		                	cellProperties.renderer = importDisplayUnitAcqItemsHandsontableHelper.addCurveBg;
		                }else if (visualColIndex == 5 || visualColIndex == 6 || visualColIndex == 9 || visualColIndex == 10) {
                            cellProperties.renderer = importDisplayUnitAcqItemsHandsontableHelper.addCellBgColor;
                        }else{
		                	cellProperties.renderer = importDisplayUnitAcqItemsHandsontableHelper.addCellStyle;
		                }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(importDisplayUnitAcqItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importDisplayUnitAcqItemsHandsontableHelper!=null
	                		&& importDisplayUnitAcqItemsHandsontableHelper.hot!=''
	                		&& importDisplayUnitAcqItemsHandsontableHelper.hot!=undefined 
	                		&& importDisplayUnitAcqItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importDisplayUnitAcqItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importDisplayUnitAcqItemsHandsontableHelper;
	    }
};

function CreateImportDisplayUnitCtrlItemsInfoTable(protocolName,acqUnitName,unitName,calculateType){
	Ext.getCmp("importDisplayUnitCtrlItemsConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportDisplayUnitItemsConfigData',
		success:function(response) {
			Ext.getCmp("importDisplayUnitCtrlItemsConfigTableInfoPanel_Id").getEl().unmask();
			if(unitName!=''){
				Ext.getCmp("importDisplayUnitCtrlItemsConfigTableInfoPanel_Id").setTitle(unitName+"/"+loginUserLanguageResource.controlItem);
			}else{
				Ext.getCmp("importDisplayUnitCtrlItemsConfigTableInfoPanel_Id").setTitle(loginUserLanguageResource.controlItem);
			}
			var result =  Ext.JSON.decode(response.responseText);
			if(importDisplayUnitCtrlItemsHandsontableHelper==null || importDisplayUnitCtrlItemsHandsontableHelper.hot==undefined){
				importDisplayUnitCtrlItemsHandsontableHelper = ImportDisplayUnitCtrlItemsHandsontableHelper.createNew("importDisplayUnitCtrlItemsConfigTableInfoDiv_id");
				var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.unit+"','"+loginUserLanguageResource.showLevel+"','"+loginUserLanguageResource.columnSort+"']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel'}," 
						+"{data:'realtimeSort'}"
						+"]";
				importDisplayUnitCtrlItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importDisplayUnitCtrlItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importDisplayUnitCtrlItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayUnitCtrlItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importDisplayUnitCtrlItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayUnitCtrlItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importDisplayUnitCtrlItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName: protocolName,
			acqUnitName: acqUnitName,
			unitName: unitName,
			calculateType: calculateType,
			type: 2
        }
	});
};

var ImportDisplayUnitCtrlItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importDisplayUnitCtrlItemsHandsontableHelper = {};
	        importDisplayUnitCtrlItemsHandsontableHelper.hot1 = '';
	        importDisplayUnitCtrlItemsHandsontableHelper.divid = divid;
	        importDisplayUnitCtrlItemsHandsontableHelper.validresult=true;//数据校验
	        importDisplayUnitCtrlItemsHandsontableHelper.colHeaders=[];
	        importDisplayUnitCtrlItemsHandsontableHelper.columns=[];
	        importDisplayUnitCtrlItemsHandsontableHelper.AllData=[];
	        
	        importDisplayUnitCtrlItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importDisplayUnitCtrlItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importDisplayUnitCtrlItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importDisplayUnitCtrlItemsHandsontableHelper.divid);
	        	importDisplayUnitCtrlItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,60],
	                columns:importDisplayUnitCtrlItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importDisplayUnitCtrlItemsHandsontableHelper.colHeaders,//显示列头
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
	                    if(importDisplayUnitCtrlItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& importDisplayUnitCtrlItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = importDisplayUnitCtrlItemsHandsontableHelper.addCellStyle;
	    	            }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(importDisplayUnitCtrlItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importDisplayUnitCtrlItemsHandsontableHelper!=null
	                		&& importDisplayUnitCtrlItemsHandsontableHelper.hot!=''
	                		&& importDisplayUnitCtrlItemsHandsontableHelper.hot!=undefined 
	                		&& importDisplayUnitCtrlItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importDisplayUnitCtrlItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importDisplayUnitCtrlItemsHandsontableHelper;
	    }
};

function CreateImportDisplayUnitCalItemsInfoTable(protocolName,acqUnitName,unitName,calculateType){
	Ext.getCmp("importDisplayUnitCalItemsConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportDisplayUnitItemsConfigData',
		success:function(response) {
			Ext.getCmp("importDisplayUnitCalItemsConfigTableInfoPanel_Id").getEl().unmask();
			if(unitName!=''){
				Ext.getCmp("importDisplayUnitCalItemsConfigTableInfoPanel_Id").setTitle(unitName+"/"+loginUserLanguageResource.calculateItem);
			}else{
				Ext.getCmp("importDisplayUnitCalItemsConfigTableInfoPanel_Id").setTitle(loginUserLanguageResource.calculateItem);
			}
			var result =  Ext.JSON.decode(response.responseText);
			if(importDisplayUnitCalItemsHandsontableHelper==null || importDisplayUnitCalItemsHandsontableHelper.hot==undefined){
				importDisplayUnitCalItemsHandsontableHelper = ImportDisplayUnitCalItemsHandsontableHelper.createNew("importDisplayUnitCalItemsConfigTableInfoDiv_id");
				
				var colHeaders="[" 
					+"['','','','',{label: '实时动态数据', colspan: 4},{label: '"+loginUserLanguageResource.historyData+"', colspan: 4},'']," 
					+"['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.unit+"','"+loginUserLanguageResource.showLevel+"'," 
					+"'顺序','"+loginUserLanguageResource.foregroundColor+"','"+loginUserLanguageResource.backgroundColor+"','曲线'," 
					+"'顺序','"+loginUserLanguageResource.foregroundColor+"','"+loginUserLanguageResource.backgroundColor+"','曲线'," 
					+"'"+loginUserLanguageResource.dataSource+"']" 
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
				importDisplayUnitCalItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importDisplayUnitCalItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importDisplayUnitCalItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayUnitCalItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importDisplayUnitCalItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayUnitCalItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importDisplayUnitCalItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName: protocolName,
			acqUnitName: acqUnitName,
			unitName: unitName,
			calculateType: calculateType,
			type: 1
        }
	});
};

var ImportDisplayUnitCalItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importDisplayUnitCalItemsHandsontableHelper = {};
	        importDisplayUnitCalItemsHandsontableHelper.hot1 = '';
	        importDisplayUnitCalItemsHandsontableHelper.divid = divid;
	        importDisplayUnitCalItemsHandsontableHelper.validresult=true;//数据校验
	        importDisplayUnitCalItemsHandsontableHelper.colHeaders=[];
	        importDisplayUnitCalItemsHandsontableHelper.columns=[];
	        importDisplayUnitCalItemsHandsontableHelper.AllData=[];
	        
	        importDisplayUnitCalItemsHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
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
	        
	        importDisplayUnitCalItemsHandsontableHelper.addCellBgColor = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (value != null) {
	                td.style.backgroundColor = '#' + value;
	            }
	            td.style.whiteSpace = 'nowrap'; //文本不换行
	            td.style.overflow = 'hidden'; //超出部分隐藏
	            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
	        }
	        
	        importDisplayUnitCalItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importDisplayUnitCalItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importDisplayUnitCalItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importDisplayUnitCalItemsHandsontableHelper.divid);
	        	importDisplayUnitCalItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,80,80,80,80,80,80,80,80,80],
	                columns:importDisplayUnitCalItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
//	                colHeaders:importDisplayUnitCalItemsHandsontableHelper.colHeaders,//显示列头
	                nestedHeaders:importDisplayUnitCalItemsHandsontableHelper.colHeaders,//显示列头
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
		                	cellProperties.renderer = importDisplayUnitCalItemsHandsontableHelper.addCurveBg;
		                }else if (visualColIndex == 5 || visualColIndex == 6 || visualColIndex == 9 || visualColIndex == 10) {
                            cellProperties.renderer = importDisplayUnitCalItemsHandsontableHelper.addCellBgColor;
                        }else{
		                	if(importDisplayUnitCalItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    	            	&& importDisplayUnitCalItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		                    	cellProperties.renderer = importDisplayUnitCalItemsHandsontableHelper.addCellStyle;
		    	            }
		                }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(importDisplayUnitCalItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importDisplayUnitCalItemsHandsontableHelper!=null
	                		&& importDisplayUnitCalItemsHandsontableHelper.hot!=''
	                		&& importDisplayUnitCalItemsHandsontableHelper.hot!=undefined 
	                		&& importDisplayUnitCalItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importDisplayUnitCalItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importDisplayUnitCalItemsHandsontableHelper;
	    }
};

function CreateImportDisplayUnitInputItemsInfoTable(protocolName,acqUnitName,unitName,calculateType){
	Ext.getCmp("importDisplayUnitInputItemsConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportDisplayUnitItemsConfigData',
		success:function(response) {
			Ext.getCmp("importDisplayUnitInputItemsConfigTableInfoPanel_Id").getEl().unmask();
			if(unitName!=''){
				Ext.getCmp("importDisplayUnitInputItemsConfigTableInfoPanel_Id").setTitle(unitName+"/"+loginUserLanguageResource.inputItem);
			}else{
				Ext.getCmp("importDisplayUnitInputItemsConfigTableInfoPanel_Id").setTitle(loginUserLanguageResource.inputItem);
			}
			var result =  Ext.JSON.decode(response.responseText);
			if(importDisplayUnitInputItemsHandsontableHelper==null || importDisplayUnitInputItemsHandsontableHelper.hot==undefined){
				importDisplayUnitInputItemsHandsontableHelper = ImportDisplayUnitInputItemsHandsontableHelper.createNew("importDisplayUnitInputItemsConfigTableInfoDiv_id");
				var colHeaders="[" 
					+"['','','','',{label: '实时动态数据', colspan: 4},{label: '"+loginUserLanguageResource.historyData+"', colspan: 4}]," 
					+"['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.unit+"','"+loginUserLanguageResource.showLevel+"'," 
					+"'顺序','"+loginUserLanguageResource.foregroundColor+"','"+loginUserLanguageResource.backgroundColor+"','曲线'," 
					+"'顺序','"+loginUserLanguageResource.foregroundColor+"','"+loginUserLanguageResource.backgroundColor+"','曲线']" 
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
				importDisplayUnitInputItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importDisplayUnitInputItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importDisplayUnitInputItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayUnitInputItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importDisplayUnitInputItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayUnitInputItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importDisplayUnitInputItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName: protocolName,
			acqUnitName: acqUnitName,
			unitName: unitName,
			calculateType: calculateType,
			type: 3
        }
	});
};

var ImportDisplayUnitInputItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importDisplayUnitInputItemsHandsontableHelper = {};
	        importDisplayUnitInputItemsHandsontableHelper.hot1 = '';
	        importDisplayUnitInputItemsHandsontableHelper.divid = divid;
	        importDisplayUnitInputItemsHandsontableHelper.validresult=true;//数据校验
	        importDisplayUnitInputItemsHandsontableHelper.colHeaders=[];
	        importDisplayUnitInputItemsHandsontableHelper.columns=[];
	        importDisplayUnitInputItemsHandsontableHelper.AllData=[];
	        
	        importDisplayUnitInputItemsHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
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
	        
	        importDisplayUnitInputItemsHandsontableHelper.addCellBgColor = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (value != null) {
	                td.style.backgroundColor = '#' + value;
	            }
	            td.style.whiteSpace = 'nowrap'; //文本不换行
	            td.style.overflow = 'hidden'; //超出部分隐藏
	            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
	        }
	        
	        importDisplayUnitInputItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importDisplayUnitInputItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importDisplayUnitInputItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importDisplayUnitInputItemsHandsontableHelper.divid);
	        	importDisplayUnitInputItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,80,80,80,80,80,80,80,80],
	                columns:importDisplayUnitInputItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
//	                colHeaders:importDisplayUnitInputItemsHandsontableHelper.colHeaders,//显示列头
	                nestedHeaders:importDisplayUnitInputItemsHandsontableHelper.colHeaders,//显示列头
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
		                	cellProperties.renderer = importDisplayUnitInputItemsHandsontableHelper.addCurveBg;
		                }else if (visualColIndex == 5 || visualColIndex == 6 || visualColIndex == 9 || visualColIndex == 10) {
                            cellProperties.renderer = importDisplayUnitInputItemsHandsontableHelper.addCellBgColor;
                        }else{
		                	if(importDisplayUnitInputItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    	            	&& importDisplayUnitInputItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		                    	cellProperties.renderer = importDisplayUnitInputItemsHandsontableHelper.addCellStyle;
		    	            }
		                }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(importDisplayUnitInputItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importDisplayUnitInputItemsHandsontableHelper!=null
	                		&& importDisplayUnitInputItemsHandsontableHelper.hot!=''
	                		&& importDisplayUnitInputItemsHandsontableHelper.hot!=undefined 
	                		&& importDisplayUnitInputItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importDisplayUnitInputItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importDisplayUnitInputItemsHandsontableHelper;
	    }
};