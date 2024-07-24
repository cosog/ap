var importReportUnitContentHandsontableHelper=null;
Ext.define("AP.view.acquisitionUnit.ImportReportUnitWindow", {
    extend: 'Ext.window.Window',
    id:'ImportReportUnitWindow_Id',
    alias: 'widget.ImportReportUnitWindow',
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
        		id:'ReportUnitImportForm_Id',
        		width: 300,
        	    bodyPadding: 0,
        	    frame: true,
        	    items: [{
        	    	xtype: 'filefield',
                	id:'ReportUnitImportFilefield_Id',
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
                        	submitImportedReportUnitFile();
                        }
                    }
        	    },{
                    id: 'ImportReportUnitSelectItemType_Id', 
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                },{
                    id: 'ImportReportUnitSelectItemId_Id', 
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }]
    		},{
            	xtype: 'label',
            	id: 'ImportReportUnitWinTabLabel_Id',
            	hidden:true,
            	html: ''
            },{
				xtype : "hidden",
				id : 'ImportReportUnitWinDeviceType_Id',
				value:'0'
			},'->',{
    	    	xtype: 'button',
                text: '全部保存',
                iconCls: 'save',
                handler: function (v, o) {
                	var treeStore = Ext.getCmp("ImportReportUnitContentTreeGridPanel_Id").getStore();
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
                            	saveAllImportedReportUnit();
                            }
                        });
                	}else{
                		saveAllImportedReportUnit();
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
            	id:"importReportUnitTreePanel_Id"
            },{
            	region: 'center',
            	id:"importedReportUnitItemInfoTablePanel_Id",
            	title:'采控项',
            	layout: "fit",
            	html:'<div class="ModbusReportUnitAddrMappingItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importedReportUnitItemInfoTableDiv_Id"></div></div>',
                listeners: {
                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(importReportUnitContentHandsontableHelper!=null && importReportUnitContentHandsontableHelper.hot!=undefined){
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		importReportUnitContentHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}
                    }
                }
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	clearImportReportUnitHandsontable();
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function clearImportReportUnitHandsontable(){
	if(importReportUnitContentHandsontableHelper!=null){
		if(importReportUnitContentHandsontableHelper.hot!=undefined){
			importReportUnitContentHandsontableHelper.hot.destroy();
		}
		importReportUnitContentHandsontableHelper=null;
	}
}

function importReportUnitContentTreeSelectClear(node){
	var chlidArray = node;
	Ext.Array.each(chlidArray, function (childArrNode, index, fog) {
		childArrNode.set('checked', false);
		if (childArrNode.childNodes != null) {
			importReportUnitContentTreeSelectClear(childArrNode.childNodes);
        }
	});
}

function importReportUnitContentTreeSelectAll(node){
	var chlidArray = node;
	Ext.Array.each(chlidArray, function (childArrNode, index, fog) {
		childArrNode.set('checked', true);
		if (childArrNode.childNodes != null) {
			importReportUnitContentTreeSelectAll(childArrNode.childNodes);
        }
	});
}

function submitImportedReportUnitFile() {
	clearImportReportUnitHandsontable();
	var form = Ext.getCmp("ReportUnitImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedReportUnitFile',
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
            	
            	
            	var importReportUnitContentTreeGridPanel = Ext.getCmp("ImportReportUnitContentTreeGridPanel_Id");
            	if (isNotVal(importReportUnitContentTreeGridPanel)) {
            		importReportUnitContentTreeGridPanel.getStore().load();
            	}else{
            		Ext.create('AP.store.acquisitionUnit.ImportReportUnitContentTreeInfoStore');
            	}
            },
            failure : function() {
            	Ext.Msg.alert("提示", "【<font color=red>上传失败 </font>】");
			}
        });
    }
    return false;
};

function CreateUploadedReportUnitContentInfoTable(protocolName,classes,unitName,groupName,groupType){
	clearImportReportUnitHandsontable();
	Ext.getCmp("importedReportUnitItemInfoTablePanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getUploadedReportUnitItemsConfigData',
		success:function(response) {
			Ext.getCmp("importedReportUnitItemInfoTablePanel_Id").getEl().unmask();
			Ext.getCmp("importedReportUnitItemInfoTablePanel_Id").setTitle(unitName);
			var result =  Ext.JSON.decode(response.responseText);
			if(importReportUnitContentHandsontableHelper==null || importReportUnitContentHandsontableHelper.hot==undefined){
				importReportUnitContentHandsontableHelper = ImportReportUnitContentHandsontableHelper.createNew("importedReportUnitItemInfoTableDiv_Id");
				var colHeaders=['序号','名称','起始地址','读写类型','单位','解析模式','','日累计计算','日累计字段名称'];
				var columns="[" 
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,importReportUnitContentHandsontableHelper);}},"
						+"{data:'RWType',type:'dropdown',strict:true,allowInvalid:false,source:['只读', '读写']}," 
						+"{data:'unit'},"
						+"{data:'resolutionMode',type:'dropdown',strict:true,allowInvalid:false,source:['开关量', '枚举量','数据量']}," 
						+"{data:'bitIndex'}," 
						+"{data:'dailyTotalCalculate',type:'checkbox'},"
						+"{data:'dailyTotalCalculateName'}"
						+"]";
				importReportUnitContentHandsontableHelper.colHeaders=colHeaders;
				importReportUnitContentHandsontableHelper.columns=Ext.JSON.decode(columns);
				
				if(classes==2 && groupType==0){
					importReportUnitContentHandsontableHelper.hiddenColumns=[6];
				}else{
					importReportUnitContentHandsontableHelper.hiddenColumns=[6,7,8];
				}
				
				if(result.totalRoot.length==0){
					importReportUnitContentHandsontableHelper.Data=[{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}];
					importReportUnitContentHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importReportUnitContentHandsontableHelper.Data=result.totalRoot;
					importReportUnitContentHandsontableHelper.createTable(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importedReportUnitItemInfoTablePanel_Id").getEl().unmask();
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

var ImportReportUnitContentHandsontableHelper = {
		createNew: function (divid) {
	        var importReportUnitContentHandsontableHelper = {};
	        importReportUnitContentHandsontableHelper.hot1 = '';
	        importReportUnitContentHandsontableHelper.divid = divid;
	        importReportUnitContentHandsontableHelper.validresult=true;//数据校验
	        importReportUnitContentHandsontableHelper.colHeaders=[];
	        importReportUnitContentHandsontableHelper.columns=[];
	        importReportUnitContentHandsontableHelper.AllData=[];
	        importReportUnitContentHandsontableHelper.Data=[];
	        
	        importReportUnitContentHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        importReportUnitContentHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        importReportUnitContentHandsontableHelper.createTable = function (data) {
	        	$('#'+importReportUnitContentHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importReportUnitContentHandsontableHelper.divid);
	        	importReportUnitContentHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: importReportUnitContentHandsontableHelper.hiddenColumns,
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	        		colWidths: [50,140,60,80,80,80,80,80,80],
	                columns:importReportUnitContentHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importReportUnitContentHandsontableHelper.colHeaders,//显示列头
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
	        importReportUnitContentHandsontableHelper.saveData = function () {}
	        importReportUnitContentHandsontableHelper.clearContainer = function () {
	        	importReportUnitContentHandsontableHelper.AllData = [];
	        }
	        return importReportUnitContentHandsontableHelper;
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

function saveSingelImportedReportUnit(protocolName,deviceType){
	Ext.Ajax.request({
		url : context + '/acquisitionUnitManagerController/saveSingelImportedReportUnit',
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
			Ext.getCmp("ImportReportUnitContentTreeGridPanel_Id").getStore().load();
			Ext.getCmp("ModbusReportUnitAddrMappingConfigTreeGridPanel_Id").getStore().load();
		},
		failure : function() {
			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
		}
	});
}

function saveAllImportedReportUnit(){
	var protocolNameList=[];
	
	
	var treeStore = Ext.getCmp("ImportReportUnitContentTreeGridPanel_Id").getStore();
	var count=treeStore.getCount();
	for(var i=0;i<count;i++){
		if(treeStore.getAt(i).data.classes==1 && treeStore.getAt(i).data.saveSign!=2){
			protocolNameList.push(treeStore.getAt(i).data.text);
		}
	}
	if(protocolNameList.length>0){
		var deviceType=Ext.getCmp("ImportReportUnitWinDeviceType_Id").getValue();
		Ext.Ajax.request({
			url : context + '/acquisitionUnitManagerController/saveAllImportedReportUnit',
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
				Ext.getCmp("ImportReportUnitContentTreeGridPanel_Id").getStore().load();
				Ext.getCmp("ModbusReportUnitAddrMappingConfigTreeGridPanel_Id").getStore().load();
			},
			failure : function() {
				Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
			}
		});
	}else{
		Ext.Msg.alert('提示', "<font color=blue>没有可保存的单元。</font>");
	}
	
}

iconImportSingleReportUnitAction = function(value, e, record) {
	var resultstring='';
	var protocolName=record.data.text;
	var deviceType=Ext.getCmp("ImportReportUnitWinDeviceType_Id").getValue();
	if( record.data.classes==1 && record.data.saveSign!=2 ){
		resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
		"onclick=saveSingelImportedReportUnit(\""+protocolName+"\",\""+deviceType+"\")>保存...</a>";
	}
	
	
	return resultstring;
}