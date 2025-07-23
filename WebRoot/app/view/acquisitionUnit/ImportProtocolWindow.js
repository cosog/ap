var importProtocolContentHandsontableHelper=null;
var importProtocolExtendedFieldHandsontableHelper=null;
Ext.define("AP.view.acquisitionUnit.ImportProtocolWindow", {
    extend: 'Ext.window.Window',
    id:'ImportProtocolWindow_Id',
    alias: 'widget.ImportProtocolWindow',
    layout: 'fit',
    title:loginUserLanguageResource.importProtocol,
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
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
        		xtype:'form',
        		id:'ProtocolImportForm_Id',
        		width: 400,
        	    bodyPadding: 0,
        	    frame: true,
        	    items: [{
        	    	xtype: 'filefield',
                	id:'ProtocolImportFilefield_Id',
                    name: 'file',
                    fieldLabel: loginUserLanguageResource.uploadFile,
                    labelWidth: getLabelWidth(loginUserLanguageResource.uploadFile,loginUserLanguage),
                    width:'100%',
                    msgTarget: 'side',
                    allowBlank: true,
                    anchor: '100%',
                    draggable:true,
                    buttonText: loginUserLanguageResource.selectUploadFile,
                    accept:'.json',
                    listeners:{
                        change:function(cmp){
                        	submitImportedProtocolFile();
                        }
                    }
        	    },{
                    id: 'ImportProtocolSelectItemType_Id', 
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                },{
                    id: 'ImportProtocolSelectItemId_Id', 
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }]
    		},{
            	xtype: 'label',
            	id: 'ImportProtocolWinTabLabel_Id',
            	hidden:true,
            	html: ''
            },{
				xtype : "hidden",
				id : 'ImportProtocolWinDeviceType_Id',
				value:'0'
			},'->',{
    	    	xtype: 'button',
                text: loginUserLanguageResource.saveAll,
                iconCls: 'save',
                handler: function (v, o) {
                	var treeStore = Ext.getCmp("ImportProtocolContentTreeGridPanel_Id").getStore();
                	var count=treeStore.getCount();
                	var overlayCount=0;
            		var collisionCount=0; 
                	for(var i=0;i<count;i++){
                		if(treeStore.getAt(i).data.classes==1 && treeStore.getAt(i).data.saveSign==1){
                			overlayCount++;
                		}else if(treeStore.getAt(i).data.classes==1 && treeStore.getAt(i).data.saveSign==2){
                			collisionCount++;
                		}
                	}
                	if(overlayCount>0 || collisionCount>0){
                		var info="";
                		if(overlayCount>0){
                			info+=overlayCount+"个协议已存在";
                			if(collisionCount>0){
                				info+="，";
                			}
                		}
                		if(collisionCount>0){
                			info+=overlayCount+"个协议无权限修改";
                		}
                		info+="！是否执行全部保存？";
                		
                		Ext.Msg.confirm(loginUserLanguageResource.tip, info, function (btn) {
                            if (btn == "yes") {
                            	saveAllImportedProtocol();
                            }
                        });
                	}else{
                		saveAllImportedProtocol();
                	}
                }
    	    }],
            layout: 'border',
            items: [{
            	region: 'west',
            	width:'25%',
            	title:loginUserLanguageResource.uploadProtocolList,
            	layout: 'fit',
            	split: true,
                collapsible: true,
            	id:"importProtocolTreePanel_Id"
            },{
            	region: 'center',
            	border: false,
            	header: false,
            	xtype: 'tabpanel',
                id:"importedProtocolRightTabPanel_Id",
                activeTab: 0,
                items:[{
                	id:"importedProtocolItemInfoTablePanel_Id",
                	title:loginUserLanguageResource.config,
                	layout: "fit",
                	iconCls: 'check3',
                	html:'<div class="importedProtocolItemInfoTableContainer" style="width:100%;height:100%;"><div class="con" id="importedProtocolItemInfoTableDiv_Id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	if(importProtocolContentHandsontableHelper!=null && importProtocolContentHandsontableHelper.hot!=undefined){
                        		var newWidth=width;
                        		var newHeight=height;
                        		var header=thisPanel.getHeader();
                        		if(header){
                        			newHeight=newHeight-header.lastBox.height-2;
                        		}
                        		importProtocolContentHandsontableHelper.hot.updateSettings({
                        			width:newWidth,
                        			height:newHeight
                        		});
                        	}
                        }
                    }
                },{
                	id:"importedProtocolExtendedFieldTabPanel_Id",
                	title:loginUserLanguageResource.extendedField,
                	layout: "fit",
                	html:'<div class="importedProtocolExtendedFieldInfoTableContainer" style="width:100%;height:100%;"><div class="con" id="importedProtocolExtendedFieldInfoTableDiv_Id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	if(importProtocolExtendedFieldHandsontableHelper!=null && importProtocolExtendedFieldHandsontableHelper.hot!=undefined){
                        		var newWidth=width;
                        		var newHeight=height;
                        		var header=thisPanel.getHeader();
                        		if(header){
                        			newHeight=newHeight-header.lastBox.height-2;
                        		}
                        		importProtocolExtendedFieldHandsontableHelper.hot.updateSettings({
                        			width:newWidth,
                        			height:newHeight
                        		});
                        	}
                        }
                    }
                }],
                listeners: {
                	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
                		if(oldCard!=undefined){
                			oldCard.setIconCls(null);
                		}
                		if(newCard!=undefined){
                			newCard.setIconCls('check3');
                		}
        			},
        			tabchange: function (tabPanel, newCard, oldCard, obj) {
        				var record= Ext.getCmp("ImportProtocolContentTreeGridPanel_Id").getSelectionModel().getSelection()[0];
                    	if(newCard.id=="importedProtocolItemInfoTablePanel_Id"){
                    		if(record.data.classes==0){
                        		if(importProtocolContentHandsontableHelper!=null){
                					if(importProtocolContentHandsontableHelper.hot!=undefined){
                						importProtocolContentHandsontableHelper.hot.destroy();
                					}
                					importProtocolContentHandsontableHelper=null;
                				}
                        	}else if(record.data.classes==1){
                        		CreateUploadedProtocolContentInfoTable(record.data.text,record.data.classes,record.data.code);
                        	}
                    	}else if(newCard.id=="importedProtocolExtendedFieldTabPanel_Id"){
                    		if(record.data.classes==0){
                        		if(importProtocolExtendedFieldHandsontableHelper!=null){
                					if(importProtocolExtendedFieldHandsontableHelper.hot!=undefined){
                						importProtocolExtendedFieldHandsontableHelper.hot.destroy();
                					}
                					importProtocolExtendedFieldHandsontableHelper=null;
                				}
                        	}else if(record.data.classes==1){
                        		CreateUploadedProtocolExtendedFieldInfoTable(record.data.text,record.data.classes,record.data.code);
                        	}
                    	}
                    }
                }
            	
            	
            	
            	
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	clearImportProtocolHandsontable();
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function clearImportProtocolHandsontable(){
	if(importProtocolContentHandsontableHelper!=null){
		if(importProtocolContentHandsontableHelper.hot!=undefined){
			importProtocolContentHandsontableHelper.hot.destroy();
		}
		importProtocolContentHandsontableHelper=null;
	}
}

function importProtocolContentTreeSelectClear(node){
	var chlidArray = node;
	Ext.Array.each(chlidArray, function (childArrNode, index, fog) {
		childArrNode.set('checked', false);
		if (childArrNode.childNodes != null) {
			importProtocolContentTreeSelectClear(childArrNode.childNodes);
        }
	});
}

function importProtocolContentTreeSelectAll(node){
	var chlidArray = node;
	Ext.Array.each(chlidArray, function (childArrNode, index, fog) {
		childArrNode.set('checked', true);
		if (childArrNode.childNodes != null) {
			importProtocolContentTreeSelectAll(childArrNode.childNodes);
        }
	});
}

function submitImportedProtocolFile() {
	clearImportProtocolHandsontable();
	var form = Ext.getCmp("ProtocolImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedProtocolFile',
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
            	
            	
            	var importProtocolContentTreeGridPanel = Ext.getCmp("ImportProtocolContentTreeGridPanel_Id");
            	if (isNotVal(importProtocolContentTreeGridPanel)) {
            		importProtocolContentTreeGridPanel.getStore().load();
            	}else{
            		Ext.create('AP.store.acquisitionUnit.ImportProtocolContentTreeInfoStore');
            	}
            },
            failure : function() {
            	Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.uploadFail+"</font>】");
			}
        });
    }
    return false;
};

function CreateUploadedProtocolContentInfoTable(protocolName,classes,code){
	Ext.getCmp("importedProtocolItemInfoTablePanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getUploadedProtocolItemsConfigData',
		success:function(response) {
			Ext.getCmp("importedProtocolItemInfoTablePanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(importProtocolContentHandsontableHelper==null || importProtocolContentHandsontableHelper.hot==undefined){
				importProtocolContentHandsontableHelper = ImportProtocolContentHandsontableHelper.createNew("importedProtocolItemInfoTableDiv_Id");
				var colHeaders="[" 
					+"['','',{label: '"+loginUserLanguageResource.lowerComputer+"', colspan: 5},{label: '"+loginUserLanguageResource.upperComputer+"', colspan: 5}]," 
					+"['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.startAddress+"','"+loginUserLanguageResource.storeDataType+"','"+loginUserLanguageResource.quantity+"','"+loginUserLanguageResource.RWType+"','"+loginUserLanguageResource.acqMode+"','"+loginUserLanguageResource.IFDataType+"','"+loginUserLanguageResource.prec+"','"+loginUserLanguageResource.ratio+"','"+loginUserLanguageResource.unit+"','"+loginUserLanguageResource.resolutionMode+"']" 
					+"]";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,importProtocolContentHandsontableHelper);}},"
					 	+"{data:'storeDataType',type:'dropdown',strict:true,allowInvalid:false,source:['bit','byte','int16','uint16','float32','bcd']}," 
					 	+"{data:'quantity',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,importProtocolContentHandsontableHelper);}}," 
					 	+"{data:'RWType',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.readOnly+"', '"+loginUserLanguageResource.writeOnly+"', '"+loginUserLanguageResource.readWrite+"']}," 
					 	+"{data:'acqMode',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.activeAcqModel+"', '"+loginUserLanguageResource.passiveAcqModel+"']}," 
						+"{data:'IFDataType',type:'dropdown',strict:true,allowInvalid:false,source:['bool','int','float32','float64','string']}," 
						+"{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importProtocolContentHandsontableHelper);}}," 
						+"{data:'ratio',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importProtocolContentHandsontableHelper);}}," 
						+"{data:'unit'}," 
						+"{data:'resolutionMode',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.switchingValue+"', '"+loginUserLanguageResource.enumValue+"','"+loginUserLanguageResource.numericValue+"']}" 
						+"]";
				importProtocolContentHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importProtocolContentHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importProtocolContentHandsontableHelper.Data=[{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}];
					importProtocolContentHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolContentHandsontableHelper.Data=result.totalRoot;
					importProtocolContentHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importProtocolContentHandsontableHelper.Data=[{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}];
					importProtocolContentHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolContentHandsontableHelper.Data=result.totalRoot;
					importProtocolContentHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importedProtocolItemInfoTablePanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			classes:classes,
			code:code
        }
	});
};

var ImportProtocolContentHandsontableHelper = {
		createNew: function (divid) {
	        var importProtocolContentHandsontableHelper = {};
	        importProtocolContentHandsontableHelper.hot1 = '';
	        importProtocolContentHandsontableHelper.divid = divid;
	        importProtocolContentHandsontableHelper.validresult=true;//数据校验
	        importProtocolContentHandsontableHelper.colHeaders=[];
	        importProtocolContentHandsontableHelper.columns=[];
	        importProtocolContentHandsontableHelper.AllData=[];
	        importProtocolContentHandsontableHelper.Data=[];
	        
	        importProtocolContentHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        importProtocolContentHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        importProtocolContentHandsontableHelper.createTable = function (data) {
	        	$('#'+importProtocolContentHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importProtocolContentHandsontableHelper.divid);
	        	importProtocolContentHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,130,80,90,90,80,80,90,80,80,80,80],
	                columns:importProtocolContentHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                nestedHeaders:importProtocolContentHandsontableHelper.colHeaders,//显示列头
	                nestedRows:true,
	                columnHeaderHeight: 28,
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                outsideClickDeselects:false,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 && importProtocolContentHandsontableHelper!=null&&importProtocolContentHandsontableHelper.hot!=''&&importProtocolContentHandsontableHelper.hot!=undefined && importProtocolContentHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importProtocolContentHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        importProtocolContentHandsontableHelper.saveData = function () {}
	        importProtocolContentHandsontableHelper.clearContainer = function () {
	        	importProtocolContentHandsontableHelper.AllData = [];
	        }
	        return importProtocolContentHandsontableHelper;
	    }
};

function CreateUploadedProtocolExtendedFieldInfoTable(protocolName,classes,code){
	Ext.getCmp("importedProtocolExtendedFieldTabPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	if(importProtocolExtendedFieldHandsontableHelper!=null){
		if(importProtocolExtendedFieldHandsontableHelper.hot!=undefined){
			importProtocolExtendedFieldHandsontableHelper.hot.destroy();
		}
		importProtocolExtendedFieldHandsontableHelper=null;
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getUploadedProtocolExtendedFieldsConfigData',
		success:function(response) {
			Ext.getCmp("importedProtocolExtendedFieldTabPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(importProtocolExtendedFieldHandsontableHelper==null || importProtocolExtendedFieldHandsontableHelper.hot==undefined){
				importProtocolExtendedFieldHandsontableHelper = ImportProtocolExtendedFieldHandsontableHelper.createNew("importedProtocolExtendedFieldInfoTableDiv_Id");
				var operationList=result.operationList;
				var additionalConditionsList=result.additionalConditionsList;
				var colHeaders=[
					loginUserLanguageResource.idx,
					loginUserLanguageResource.name,
					loginUserLanguageResource.dataColumn+'1',
					loginUserLanguageResource.fourOperation,
					loginUserLanguageResource.dataColumn+'2',
					loginUserLanguageResource.prec,
					loginUserLanguageResource.ratio,
					loginUserLanguageResource.unit,
					loginUserLanguageResource.additionalConditions
					];
				
				var columns=[{
				    data: 'id'
				}, {
				    data: 'title'
				}, {
				    data: 'title1',
				    renderer: importProtocolExtendedFieldHandsontableHelper.placeholderRenderer
				}, {
				    data: 'operation',
				    type: 'dropdown',
				    strict: true,
				    allowInvalid: false,
				    source: operationList
				}, {
				    data: 'title2',
				    renderer: importProtocolExtendedFieldHandsontableHelper.placeholderRenderer
				}, {
				    data: 'prec',
				    type: 'text',
				    allowInvalid: true,
				    validator: function (val, callback) {
				        return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, importProtocolExtendedFieldHandsontableHelper);
				    }
				}, {
					data: 'ratio',
				    type: 'text',
				    allowInvalid: true,
				    validator: function (val, callback) {
				        return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, importProtocolExtendedFieldHandsontableHelper);
				    }
				}, {
				    data: 'unit'
				}, {
				    data: 'additionalConditions',
				    type: 'dropdown',
				    strict: true,
				    allowInvalid: false,
				    source: additionalConditionsList
				}];
				
				importProtocolExtendedFieldHandsontableHelper.colHeaders=colHeaders;
				importProtocolExtendedFieldHandsontableHelper.columns=columns;
				if(result.totalRoot.length==0){
					importProtocolExtendedFieldHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolExtendedFieldHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importProtocolExtendedFieldHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolExtendedFieldHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importedProtocolExtendedFieldTabPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			classes:classes,
			code:code
        }
	});
};

var ImportProtocolExtendedFieldHandsontableHelper = {
		createNew: function (divid) {
	        var importProtocolExtendedFieldHandsontableHelper = {};
	        importProtocolExtendedFieldHandsontableHelper.hot1 = '';
	        importProtocolExtendedFieldHandsontableHelper.divid = divid;
	        importProtocolExtendedFieldHandsontableHelper.validresult=true;//数据校验
	        importProtocolExtendedFieldHandsontableHelper.colHeaders=[];
	        importProtocolExtendedFieldHandsontableHelper.columns=[];
	        importProtocolExtendedFieldHandsontableHelper.AllData=[];
	        
	        importProtocolExtendedFieldHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importProtocolExtendedFieldHandsontableHelper.placeholderRenderer = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (value === null || value === '') {
	                td.style.color = 'gray'; // 设置灰色文本作为占位符样式
	                td.style.fontStyle = 'italic'; // 可以添加其他样式如斜体
	                td.innerHTML = loginUserLanguageResource.doubleClickCellTip+'...'; // 显示占位符文本
	            }
	        }
	        
	        importProtocolExtendedFieldHandsontableHelper.createTable = function (data) {
	        	$('#'+importProtocolExtendedFieldHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importProtocolExtendedFieldHandsontableHelper.divid);
	        	importProtocolExtendedFieldHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,200,200,80,200,80,80,80,150],
	                columns:importProtocolExtendedFieldHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importProtocolExtendedFieldHandsontableHelper.colHeaders,//显示列头
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
	                    return cellProperties;
	                },
	                afterBeginEditing: function (row, column) {
	                	
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 && importProtocolExtendedFieldHandsontableHelper!=null&&importProtocolExtendedFieldHandsontableHelper.hot!=''&&importProtocolExtendedFieldHandsontableHelper.hot!=undefined && importProtocolExtendedFieldHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importProtocolExtendedFieldHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        //保存数据
	        importProtocolExtendedFieldHandsontableHelper.saveData = function () {}
	        importProtocolExtendedFieldHandsontableHelper.clearContainer = function () {
	        	importProtocolExtendedFieldHandsontableHelper.AllData = [];
	        }
	        return importProtocolExtendedFieldHandsontableHelper;
	    }
};

adviceDataInfoColor = function(val,o,p,e) {
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

function saveSingelImportedProtocol(protocolName,deviceType,saveSign,msg){
	protocolName = decodeURIComponent(protocolName);
	deviceType = decodeURIComponent(deviceType);
	saveSign = decodeURIComponent(saveSign);
	msg = decodeURIComponent(msg);
	if(parseInt(saveSign)>0){
		Ext.Msg.confirm(loginUserLanguageResource.tip, msg,function (btn) {
			if (btn == "yes") {
				Ext.Ajax.request({
					url : context + '/acquisitionUnitManagerController/saveSingelImportedProtocol',
					method : "POST",
					params : {
						protocolName : protocolName,
						deviceType : deviceType
					},
					success : function(response) {
						var result = Ext.JSON.decode(response.responseText);
						if (result.success==true) {
							Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
						}else{
							Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
						}
						Ext.getCmp("ImportProtocolContentTreeGridPanel_Id").getStore().load();
						Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id").getStore().load();
					},
					failure : function() {
						Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
					}
				});
			}
		});
	}else{
		Ext.Ajax.request({
			url : context + '/acquisitionUnitManagerController/saveSingelImportedProtocol',
			method : "POST",
			params : {
				protocolName : protocolName,
				deviceType : deviceType
			},
			success : function(response) {
				var result = Ext.JSON.decode(response.responseText);
				if (result.success==true) {
					Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
				}else{
					Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
				}
				Ext.getCmp("ImportProtocolContentTreeGridPanel_Id").getStore().load();
				Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id").getStore().load();
			},
			failure : function() {
				Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
			}
		});
	}
}

function saveAllImportedProtocol(){
	var protocolNameList=[];
	
	
	var treeStore = Ext.getCmp("ImportProtocolContentTreeGridPanel_Id").getStore();
	var count=treeStore.getCount();
	for(var i=0;i<count;i++){
		if(treeStore.getAt(i).data.classes==1 && treeStore.getAt(i).data.saveSign!=2){
			protocolNameList.push(treeStore.getAt(i).data.text);
		}
	}
	if(protocolNameList.length>0){
		var deviceType=Ext.getCmp("ImportProtocolWinDeviceType_Id").getValue();
		Ext.Ajax.request({
			url : context + '/acquisitionUnitManagerController/saveAllImportedProtocol',
			method : "POST",
			params : {
				protocolName : protocolNameList.join(","),
				deviceType : deviceType
			},
			success : function(response) {
				var result = Ext.JSON.decode(response.responseText);
				if (result.success==true) {
					Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
				}else{
					Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
				}
				Ext.getCmp("ImportProtocolContentTreeGridPanel_Id").getStore().load();
				Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id").getStore().load();
			},
			failure : function() {
				Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
			}
		});
	}else{
		Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>"+loginUserLanguageResource.noDataCanBeSaved+"</font>");
	}
	
}

iconImportSingleProtocolAction = function(value, e, record) {
	var resultstring='';
	if( record.data.classes==1 && record.data.saveSign!=2 ){
		var protocolName=record.data.text;
		var saveSign=record.data.saveSign;
		var msg=record.data.msg;
		var deviceType=Ext.getCmp("ImportProtocolWinDeviceType_Id").getValue();
		
		protocolName = encodeURIComponent(protocolName || '');
		saveSign = encodeURIComponent(saveSign || '');
		msg = encodeURIComponent(msg || '');
		deviceType = encodeURIComponent(deviceType || '');
		resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
		"onclick=saveSingelImportedProtocol('"+protocolName+"','"+deviceType+"','"+saveSign+"','"+msg+"')>"+loginUserLanguageResource.save+"...</a>";
	}
	
	
	return resultstring;
}