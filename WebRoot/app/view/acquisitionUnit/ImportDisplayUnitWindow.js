var importDisplayUnitAcqItemsHandsontableHelper=null;
var importDisplayUnitCtrlItemsHandsontableHelper=null;
Ext.define("AP.view.acquisitionUnit.ImportDisplayUnitWindow", {
    extend: 'Ext.window.Window',
    id: 'ImportDisplayUnitWindow_Id',
    alias: 'widget.ImportDisplayUnitWindow',
    layout: 'fit',
    title: loginUserLanguageResource.importDisplayUnit,
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
                    fieldLabel: loginUserLanguageResource.uploadFile,
                    labelWidth: 60,
                    width: '100%',
                    msgTarget: 'side',
                    allowBlank: true,
                    anchor: '100%',
                    draggable: true,
                    buttonText: loginUserLanguageResource.selectUploadFile,
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
                text: loginUserLanguageResource.saveAll,
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
}

function submitImportedDisplayUnitFile() {
	clearImportDisplayUnitHandsontable();
	var form = Ext.getCmp("DisplayUnitImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedDisplayUnitFile',
            timeout: 1000*60*10,
            method:'post',
            waitMsg: loginUserLanguageResource.uploadingFile+'...',
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
	if( record.data.classes==1 && record.data.saveSign!=2 ){
		var unitName=record.data.text;
		var acqUnit=record.data.acqUnit;
		var protocolName=record.data.protocol;
		var saveSign=record.data.saveSign;
		var msg=record.data.msg;
		
		unitName = encodeURIComponent(unitName || '');
		acqUnit = encodeURIComponent(acqUnit || '');
		protocolName = encodeURIComponent(protocolName || '');
		saveSign = encodeURIComponent(saveSign || '');
		msg = encodeURIComponent(msg || '');
		resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
		"onclick=saveSingelImportedDisplayUnit('"+unitName+"','"+acqUnit+"','"+protocolName+"','"+saveSign+"','"+msg+"')>"+loginUserLanguageResource.save+"...</a>";
	}
	return resultstring;
}

function saveSingelImportedDisplayUnit(unitName,acqUnit,protocolName,saveSign,msg){
	unitName = decodeURIComponent(unitName);
	acqUnit = decodeURIComponent(acqUnit);
	protocolName = decodeURIComponent(protocolName);
	saveSign = decodeURIComponent(saveSign);
	msg = decodeURIComponent(msg);
	if(parseInt(saveSign)>0){
		Ext.Msg.confirm(loginUserLanguageResource.tip, msg,function (btn) {
			if (btn == "yes") {
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

			    		var treePanel=Ext.getCmp("DisplayUnitProtocolTreeGridPanel_Id");
			    		if(isNotVal(treePanel)){
			    			treePanel.getStore().load();
			    		}else{
			    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayUnitProtocolTreeInfoStore');
			    		}
					},
					failure : function() {
						Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
					}
				});
			}
		});
	}else{
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

	    		var treePanel=Ext.getCmp("DisplayUnitProtocolTreeGridPanel_Id");
	    		if(isNotVal(treePanel)){
	    			treePanel.getStore().load();
	    		}else{
	    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayUnitProtocolTreeInfoStore');
	    		}
			},
			failure : function() {
				Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
			}
		});
	}
	
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

    		var treePanel=Ext.getCmp("DisplayUnitProtocolTreeGridPanel_Id");
    		if(isNotVal(treePanel)){
    			treePanel.getStore().load();
    		}else{
    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayUnitProtocolTreeInfoStore');
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
                	+"['','','','','','',{label: '"+loginUserLanguageResource.realtimeMonitoring+"', colspan: 7},{label: '"+loginUserLanguageResource.historyQuery+"', colspan: 7},'','','','','','','']," 
                	+"['','','','','','',{label: '"+loginUserLanguageResource.deviceOverview+"', colspan: 2},{label: '"+loginUserLanguageResource.dynamicData+"', colspan: 4},'"+loginUserLanguageResource.trendCurve+"',{label: '"+loginUserLanguageResource.deviceOverview+"', colspan: 2},{label: '"+loginUserLanguageResource.historyData+"', colspan: 4},'"+loginUserLanguageResource.trendCurve+"','','','','','','','']," 
                	+"['','"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.dataSource+"','"+loginUserLanguageResource.unit+"','"+loginUserLanguageResource.showLevel+"'," 
                	
                	+"'"+loginUserLanguageResource.deviceOverview+"','"+loginUserLanguageResource.columnSort+"',"
                	+"'"+loginUserLanguageResource.dynamicData+"','"+loginUserLanguageResource.columnSort+"','"+loginUserLanguageResource.foregroundColor+"','"+loginUserLanguageResource.backgroundColor+"','"+loginUserLanguageResource.curveConfig+"'," 
                	
                	
                	+"'"+loginUserLanguageResource.deviceOverview+"','"+loginUserLanguageResource.columnSort+"',"
                	+"'"+loginUserLanguageResource.historyData+"','"+loginUserLanguageResource.columnSort+"','"+loginUserLanguageResource.foregroundColor+"','"+loginUserLanguageResource.backgroundColor+"','"+loginUserLanguageResource.curveConfig+"'," 
                	+"'','','','','','','']"
                	+"]";
                var columns = "[" 
                    +"{data:'checked',type:'checkbox'}," 
                    +"{data:'id'}," 
                    +"{data:'title'}," 
                    +"{data:'dataSource'}," 
                    +"{data:'unit'}," 
                    +"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importDisplayUnitAcqItemsHandsontableHelper);}}," 
                    
                    +"{data:'realtimeOverview',type:'checkbox'}," 
                    +"{data:'realtimeOverviewSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importDisplayUnitAcqItemsHandsontableHelper);}}," 
                    
                    +"{data:'realtimeData',type:'checkbox'}," 
                    +"{data:'realtimeSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importDisplayUnitAcqItemsHandsontableHelper);}}," 
                    +"{data:'realtimeColor'}," 
                    +"{data:'realtimeBgColor'}," 
                    +"{data:'realtimeCurveConfShowValue'}," //12
                    
                    +"{data:'historyOverview',type:'checkbox'}," 
                    +"{data:'historyOverviewSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importDisplayUnitAcqItemsHandsontableHelper);}}," 
                    +"{data:'historyData',type:'checkbox'}," 
                    +"{data:'historySort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importDisplayUnitAcqItemsHandsontableHelper);}}," 
                    +"{data:'historyColor'}," 
                    +"{data:'historyBgColor'}," 
                    +"{data:'historyCurveConfShowValue'}," //19
                    +"{data:'realtimeCurveConf'}," 
                    +"{data:'historyCurveConf'}," 
                    +"{data:'resolutionMode',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.switchingValue+"', '"+loginUserLanguageResource.enumValue+"','"+loginUserLanguageResource.numericValue+"']}," 
                    +"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,importDisplayUnitAcqItemsHandsontableHelper);}}," 
                    +"{data:'bitIndex'}," 
                    +"{data:'type'}," 
                    +"{data:'code'}" 
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
	        importDisplayUnitAcqItemsHandsontableHelper.validresult = true; //数据校验
	        importDisplayUnitAcqItemsHandsontableHelper.colHeaders = [];
	        importDisplayUnitAcqItemsHandsontableHelper.columns = [];
	        importDisplayUnitAcqItemsHandsontableHelper.AllData = [];

	        importDisplayUnitAcqItemsHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (value!=null && value!="") {
	                var arr = (value+"").split(';');
	                if (arr.length == 3) {
	                    td.style.backgroundColor = '#' + arr[2];
	                }
	            }
	            td.style.whiteSpace = 'nowrap'; //文本不换行
	            td.style.overflow = 'hidden'; //超出部分隐藏
	            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
	        }
	        
	        importDisplayUnitAcqItemsHandsontableHelper.addReadOnlyCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            var bg='rgb(245, 245, 245)';
	            if (value!=null && value!="") {
	                var arr = (value+"").split(';');
	                if (arr.length == 3) {
	                	bg = '#' + arr[2];
	                }
	            }
	            td.style.backgroundColor = bg;
	            td.style.whiteSpace = 'nowrap'; //文本不换行
	            td.style.overflow = 'hidden'; //超出部分隐藏
	            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
	        }

	        importDisplayUnitAcqItemsHandsontableHelper.addCellBgColor = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (value!=null && value!="") {
	                td.style.backgroundColor = '#' + value;
	            }
	            td.style.whiteSpace = 'nowrap'; //文本不换行
	            td.style.overflow = 'hidden'; //超出部分隐藏
	            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
	        }
	        
	        importDisplayUnitAcqItemsHandsontableHelper.addReadOnlyCellBgColor = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (value!=null && value!="") {
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
	            td.style.whiteSpace = 'nowrap'; //文本不换行
	            td.style.overflow = 'hidden'; //超出部分隐藏
	            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
	        }
	        
	        importDisplayUnitAcqItemsHandsontableHelper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(importDisplayUnitAcqItemsHandsontableHelper.columns[col].type=='checkbox'){
	        		importDisplayUnitAcqItemsHandsontableHelper.addCheckboxReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else if(importDisplayUnitAcqItemsHandsontableHelper.columns[col].type=='dropdown'){
	        		importDisplayUnitAcqItemsHandsontableHelper.addDropdownReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else{
	        		importDisplayUnitAcqItemsHandsontableHelper.addTextReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}
	        }
	        
	        importDisplayUnitAcqItemsHandsontableHelper.addCheckboxReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        importDisplayUnitAcqItemsHandsontableHelper.addDropdownReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
	        	td.style.overflow='hidden';//超出部分隐藏
	        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importDisplayUnitAcqItemsHandsontableHelper.addTextReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
	        	td.style.overflow='hidden';//超出部分隐藏
	        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }

	        importDisplayUnitAcqItemsHandsontableHelper.createTable = function (data) {
	            $('#' + importDisplayUnitAcqItemsHandsontableHelper.divid).empty();
	            var hotElement = document.querySelector('#' + importDisplayUnitAcqItemsHandsontableHelper.divid);
	            importDisplayUnitAcqItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	                licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	                data: data,
	                hiddenColumns: {
	                    columns: [0,6,7,13,14,20, 21, 22, 23, 24, 25, 26],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [25, 50, 140, 80, 80, 80, 80, 80, 60, 80, 80, 80, 80, 80, 80, 100, 80, 80, 80, 100],
	                columns: importDisplayUnitAcqItemsHandsontableHelper.columns,
	                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false, //显示行头
//	                colHeaders: importDisplayUnitAcqItemsHandsontableHelper.colHeaders, //显示列头
	                nestedHeaders:importDisplayUnitAcqItemsHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true, //允许排序
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);

                        cellProperties.readOnly = true;
                        if (visualColIndex == 12 || visualColIndex == 19) {
                            cellProperties.renderer = importDisplayUnitAcqItemsHandsontableHelper.addReadOnlyCurveBg;
                        } else if (visualColIndex == 10 || visualColIndex == 11 || visualColIndex == 17 || visualColIndex == 18) {
                            cellProperties.renderer = importDisplayUnitAcqItemsHandsontableHelper.addReadOnlyCellBgColor;
                        } else {
                        	cellProperties.renderer=importDisplayUnitAcqItemsHandsontableHelper.addReadOnlyBg;
                        }

	                    return cellProperties;
	                },
	                afterBeginEditing: function (row, column) {
	                	
	                },
	                afterOnCellMouseOver: function (event, coords, TD) {
	                    if (coords.col>=0 && coords.row>=0 && importDisplayUnitAcqItemsHandsontableHelper.columns[coords.col].type != 'checkbox' &&
	                        importDisplayUnitAcqItemsHandsontableHelper != null &&
	                        importDisplayUnitAcqItemsHandsontableHelper.hot != '' &&
	                        importDisplayUnitAcqItemsHandsontableHelper.hot != undefined &&
	                        importDisplayUnitAcqItemsHandsontableHelper.hot.getDataAtCell != undefined) {
	                        var rawValue = importDisplayUnitAcqItemsHandsontableHelper.hot.getDataAtCell(coords.row, coords.col);
	                        if (isNotVal(rawValue)) {
	                            var showValue = rawValue;
	                            var rowChar = 90;
	                            var maxWidth = rowChar * 10;
	                            if (rawValue.length > rowChar) {
	                                showValue = '';
	                                let arr = [];
	                                let index = 0;
	                                while (index < rawValue.length) {
	                                    arr.push(rawValue.slice(index, index += rowChar));
	                                }
	                                for (var i = 0; i < arr.length; i++) {
	                                    showValue += arr[i];
	                                    if (i < arr.length - 1) {
	                                        showValue += '<br>';
	                                    }
	                                }
	                            }
	                            if (!isNotVal(TD.tip)) {
	                                var height = 28;
	                                TD.tip = Ext.create('Ext.tip.ToolTip', {
	                                    target: event.target,
	                                    maxWidth: maxWidth,
	                                    html: showValue,
	                                    listeners: {
	                                        hide: function (thisTip, eOpts) {},
	                                        close: function (thisTip, eOpts) {}
	                                    }
	                                });
	                            } else {
	                                TD.tip.setHtml(showValue);
	                            }
	                        }
	                    }
	                }
	            });
	        }
	        //保存数据
	        importDisplayUnitAcqItemsHandsontableHelper.saveData = function () {}
	        importDisplayUnitAcqItemsHandsontableHelper.clearContainer = function () {
	            importDisplayUnitAcqItemsHandsontableHelper.AllData = [];
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
	        
	        importDisplayUnitCtrlItemsHandsontableHelper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(importDisplayUnitCtrlItemsHandsontableHelper.columns[col].type=='checkbox'){
	        		importDisplayUnitCtrlItemsHandsontableHelper.addCheckboxReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else if(importDisplayUnitCtrlItemsHandsontableHelper.columns[col].type=='dropdown'){
	        		importDisplayUnitCtrlItemsHandsontableHelper.addDropdownReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else{
	        		importDisplayUnitCtrlItemsHandsontableHelper.addTextReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}
	        }
	        
	        importDisplayUnitCtrlItemsHandsontableHelper.addCheckboxReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        importDisplayUnitCtrlItemsHandsontableHelper.addDropdownReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
	        	td.style.overflow='hidden';//超出部分隐藏
	        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importDisplayUnitCtrlItemsHandsontableHelper.addTextReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
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
	                    cellProperties.renderer = importDisplayUnitCtrlItemsHandsontableHelper.addReadOnlyBg;
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 
			                	&& importDisplayUnitCtrlItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
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