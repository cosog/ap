var importDisplayUnitContentHandsontableHelper=null;
Ext.define("AP.view.acquisitionUnit.ImportDisplayUnitWindow", {
    extend: 'Ext.window.Window',
    id:'ImportDisplayUnitWindow_Id',
    alias: 'widget.ImportDisplayUnitWindow',
    layout: 'fit',
    title:'采控单元导入',
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
        		id:'DisplayUnitImportForm_Id',
        		width: 300,
        	    bodyPadding: 0,
        	    frame: true,
        	    items: [{
        	    	xtype: 'filefield',
                	id:'DisplayUnitImportFilefield_Id',
                    name: 'file',
                    fieldLabel: '上传文件',
                    labelWidth: 60,
                    width:'100%',
                    msgTarget: 'side',
                    allowBlank: true,
                    anchor: '100%',
                    draggable:true,
                    buttonText: '请选择上传文件',
                    accept:'.json',
                    listeners:{
                        change:function(cmp){
                        	submitImportedDisplayUnitFile();
                        }
                    }
        	    },{
                    id: 'ImportDisplayUnitSelectItemType_Id', 
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                },{
                    id: 'ImportDisplayUnitSelectItemId_Id', 
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }]
    		},{
            	xtype: 'label',
            	id: 'ImportDisplayUnitWinTabLabel_Id',
            	hidden:true,
            	html: ''
            },{
				xtype : "hidden",
				id : 'ImportDisplayUnitWinDeviceType_Id',
				value:'0'
			},'->',{
    	    	xtype: 'button',
                text: '全部保存',
                iconCls: 'save',
                handler: function (v, o) {
                	var treeStore = Ext.getCmp("ImportDisplayUnitContentTreeGridPanel_Id").getStore();
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
                			info+=overlayCount+"个单元已存在";
                			if(collisionCount>0){
                				info+="，";
                			}
                		}
                		if(collisionCount>0){
                			info+=overlayCount+"个单元无权限修改";
                		}
                		info+="！是否执行全部保存？";
                		
                		Ext.Msg.confirm('提示', info, function (btn) {
                            if (btn == "yes") {
                            	saveAllImportedDisplayUnit();
                            }
                        });
                	}else{
                		saveAllImportedDisplayUnit();
                	}
                }
    	    }],
            layout: 'border',
            items: [{
            	region: 'west',
            	width:'25%',
            	title:'上传单元列表',
            	layout: 'fit',
            	split: true,
                collapsible: true,
            	id:"importDisplayUnitTreePanel_Id"
            },{
            	region: 'center',
            	id:"importedDisplayUnitItemInfoTablePanel_Id",
            	title:'采控项',
            	layout: "fit",
            	html:'<div class="ModbusDisplayUnitAddrMappingItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importedDisplayUnitItemInfoTableDiv_Id"></div></div>',
                listeners: {
                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(importDisplayUnitContentHandsontableHelper!=null && importDisplayUnitContentHandsontableHelper.hot!=undefined){
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		importDisplayUnitContentHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}
                    }
                }
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
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
	if(importDisplayUnitContentHandsontableHelper!=null){
		if(importDisplayUnitContentHandsontableHelper.hot!=undefined){
			importDisplayUnitContentHandsontableHelper.hot.destroy();
		}
		importDisplayUnitContentHandsontableHelper=null;
	}
}

function importDisplayUnitContentTreeSelectClear(node){
	var chlidArray = node;
	Ext.Array.each(chlidArray, function (childArrNode, index, fog) {
		childArrNode.set('checked', false);
		if (childArrNode.childNodes != null) {
			importDisplayUnitContentTreeSelectClear(childArrNode.childNodes);
        }
	});
}

function importDisplayUnitContentTreeSelectAll(node){
	var chlidArray = node;
	Ext.Array.each(chlidArray, function (childArrNode, index, fog) {
		childArrNode.set('checked', true);
		if (childArrNode.childNodes != null) {
			importDisplayUnitContentTreeSelectAll(childArrNode.childNodes);
        }
	});
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
            		Ext.Msg.alert("提示", "加载成功 ");
            	}else{
            		Ext.Msg.alert("提示", "上传数据格式有误！ ");
            	}
            	
            	
            	var importDisplayUnitContentTreeGridPanel = Ext.getCmp("ImportDisplayUnitContentTreeGridPanel_Id");
            	if (isNotVal(importDisplayUnitContentTreeGridPanel)) {
            		importDisplayUnitContentTreeGridPanel.getStore().load();
            	}else{
            		Ext.create('AP.store.acquisitionUnit.ImportDisplayUnitContentTreeInfoStore');
            	}
            },
            failure : function() {
            	Ext.Msg.alert("提示", "【<font color=red>上传失败 </font>】");
			}
        });
    }
    return false;
};

function CreateUploadedDisplayUnitContentInfoTable(protocolName,classes,unitName,groupName,groupType){
	clearImportDisplayUnitHandsontable();
	Ext.getCmp("importedDisplayUnitItemInfoTablePanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getUploadedDisplayUnitItemsConfigData',
		success:function(response) {
			Ext.getCmp("importedDisplayUnitItemInfoTablePanel_Id").getEl().unmask();
			Ext.getCmp("importedDisplayUnitItemInfoTablePanel_Id").setTitle(unitName);
			var result =  Ext.JSON.decode(response.responseText);
			if(importDisplayUnitContentHandsontableHelper==null || importDisplayUnitContentHandsontableHelper.hot==undefined){
				importDisplayUnitContentHandsontableHelper = ImportDisplayUnitContentHandsontableHelper.createNew("importedDisplayUnitItemInfoTableDiv_Id");
				var colHeaders=['序号','名称','起始地址','读写类型','单位','解析模式','','日累计计算','日累计字段名称'];
				var columns="[" 
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,importDisplayUnitContentHandsontableHelper);}},"
						+"{data:'RWType',type:'dropdown',strict:true,allowInvalid:false,source:['只读', '读写']}," 
						+"{data:'unit'},"
						+"{data:'resolutionMode',type:'dropdown',strict:true,allowInvalid:false,source:['开关量', '枚举量','数据量']}," 
						+"{data:'bitIndex'}," 
						+"{data:'dailyTotalCalculate',type:'checkbox'},"
						+"{data:'dailyTotalCalculateName'}"
						+"]";
				importDisplayUnitContentHandsontableHelper.colHeaders=colHeaders;
				importDisplayUnitContentHandsontableHelper.columns=Ext.JSON.decode(columns);
				
				if(classes==2 && groupType==0){
					importDisplayUnitContentHandsontableHelper.hiddenColumns=[6];
				}else{
					importDisplayUnitContentHandsontableHelper.hiddenColumns=[6,7,8];
				}
				
				if(result.totalRoot.length==0){
					importDisplayUnitContentHandsontableHelper.Data=[{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}];
					importDisplayUnitContentHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayUnitContentHandsontableHelper.Data=result.totalRoot;
					importDisplayUnitContentHandsontableHelper.createTable(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importedDisplayUnitItemInfoTablePanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			protocolName:protocolName,
			classes:classes,
			unitName:unitName,
			groupName:groupName,
			groupType:groupType
        }
	});
};

var ImportDisplayUnitContentHandsontableHelper = {
		createNew: function (divid) {
	        var importDisplayUnitContentHandsontableHelper = {};
	        importDisplayUnitContentHandsontableHelper.hot1 = '';
	        importDisplayUnitContentHandsontableHelper.divid = divid;
	        importDisplayUnitContentHandsontableHelper.validresult=true;//数据校验
	        importDisplayUnitContentHandsontableHelper.colHeaders=[];
	        importDisplayUnitContentHandsontableHelper.columns=[];
	        importDisplayUnitContentHandsontableHelper.AllData=[];
	        importDisplayUnitContentHandsontableHelper.Data=[];
	        
	        importDisplayUnitContentHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        importDisplayUnitContentHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        importDisplayUnitContentHandsontableHelper.createTable = function (data) {
	        	$('#'+importDisplayUnitContentHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importDisplayUnitContentHandsontableHelper.divid);
	        	importDisplayUnitContentHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: importDisplayUnitContentHandsontableHelper.hiddenColumns,
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	        		colWidths: [50,140,60,80,80,80,80,80,80],
	                columns:importDisplayUnitContentHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importDisplayUnitContentHandsontableHelper.colHeaders,//显示列头
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
	                afterSelectionEnd : function (row, column, row2, column2, selectionLayerLevel) {
	                	
	                }
	        	});
	        }
	        importDisplayUnitContentHandsontableHelper.saveData = function () {}
	        importDisplayUnitContentHandsontableHelper.clearContainer = function () {
	        	importDisplayUnitContentHandsontableHelper.AllData = [];
	        }
	        return importDisplayUnitContentHandsontableHelper;
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

function saveSingelImportedDisplayUnit(protocolName,deviceType){
	Ext.Ajax.request({
		url : context + '/acquisitionUnitManagerController/saveSingelImportedDisplayUnit',
		method : "POST",
		params : {
			protocolName : protocolName,
			deviceType : deviceType
		},
		success : function(response) {
			var result = Ext.JSON.decode(response.responseText);
			if (result.success==true) {
				Ext.Msg.alert('提示', "<font color=blue>保存成功。</font>");
			}else{
				Ext.Msg.alert('提示', "<font color=red>保存失败。</font>");
			}
			Ext.getCmp("ImportDisplayUnitContentTreeGridPanel_Id").getStore().load();
			Ext.getCmp("ModbusDisplayUnitAddrMappingConfigTreeGridPanel_Id").getStore().load();
		},
		failure : function() {
			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
		}
	});
}

function saveAllImportedDisplayUnit(){
	var protocolNameList=[];
	
	
	var treeStore = Ext.getCmp("ImportDisplayUnitContentTreeGridPanel_Id").getStore();
	var count=treeStore.getCount();
	for(var i=0;i<count;i++){
		if(treeStore.getAt(i).data.classes==1 && treeStore.getAt(i).data.saveSign!=2){
			protocolNameList.push(treeStore.getAt(i).data.text);
		}
	}
	if(protocolNameList.length>0){
		var deviceType=Ext.getCmp("ImportDisplayUnitWinDeviceType_Id").getValue();
		Ext.Ajax.request({
			url : context + '/acquisitionUnitManagerController/saveAllImportedDisplayUnit',
			method : "POST",
			params : {
				protocolName : protocolNameList.join(","),
				deviceType : deviceType
			},
			success : function(response) {
				var result = Ext.JSON.decode(response.responseText);
				if (result.success==true) {
					Ext.Msg.alert('提示', "<font color=blue>保存成功。</font>");
				}else{
					Ext.Msg.alert('提示', "<font color=red>保存失败。</font>");
				}
				Ext.getCmp("ImportDisplayUnitContentTreeGridPanel_Id").getStore().load();
				Ext.getCmp("ModbusDisplayUnitAddrMappingConfigTreeGridPanel_Id").getStore().load();
			},
			failure : function() {
				Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
			}
		});
	}else{
		Ext.Msg.alert('提示', "<font color=blue>没有可保存的单元。</font>");
	}
	
}

iconImportSingleDisplayUnitAction = function(value, e, record) {
	var resultstring='';
	var protocolName=record.data.text;
	var deviceType=Ext.getCmp("ImportDisplayUnitWinDeviceType_Id").getValue();
	if( record.data.classes==1 && record.data.saveSign!=2 ){
		resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
		"onclick=saveSingelImportedDisplayUnit(\""+protocolName+"\",\""+deviceType+"\")>保存...</a>";
	}
	
	
	return resultstring;
}