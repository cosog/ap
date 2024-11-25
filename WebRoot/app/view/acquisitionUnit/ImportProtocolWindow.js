var importProtocolContentHandsontableHelper=null;
Ext.define("AP.view.acquisitionUnit.ImportProtocolWindow", {
    extend: 'Ext.window.Window',
    id:'ImportProtocolWindow_Id',
    alias: 'widget.ImportProtocolWindow',
    layout: 'fit',
    title:'协议导入',
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
        		width: 300,
        	    bodyPadding: 0,
        	    frame: true,
        	    items: [{
        	    	xtype: 'filefield',
                	id:'ProtocolImportFilefield_Id',
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
                text: '全部保存',
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
                		
                		Ext.Msg.confirm('提示', info, function (btn) {
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
            	title:'上传协议列表',
            	layout: 'fit',
            	split: true,
                collapsible: true,
            	id:"importProtocolTreePanel_Id"
            },{
            	region: 'center',
            	id:"importedProtocolItemInfoTablePanel_Id",
            	title:'采控项',
            	layout: "fit",
            	html:'<div class="ModbusProtocolAddrMappingItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importedProtocolItemInfoTableDiv_Id"></div></div>',
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
            waitMsg: '文件上传中...',
            success: function(response, action) {
            	var result = action.result;
            	if (result.flag == true) {
            		Ext.Msg.alert("提示", "加载成功 ");
            	}else{
            		Ext.Msg.alert("提示", "上传数据格式有误！ ");
            	}
            	
            	
            	var importProtocolContentTreeGridPanel = Ext.getCmp("ImportProtocolContentTreeGridPanel_Id");
            	if (isNotVal(importProtocolContentTreeGridPanel)) {
            		importProtocolContentTreeGridPanel.getStore().load();
            	}else{
            		Ext.create('AP.store.acquisitionUnit.ImportProtocolContentTreeInfoStore');
            	}
            },
            failure : function() {
            	Ext.Msg.alert("提示", "【<font color=red>上传失败 </font>】");
			}
        });
    }
    return false;
};

function CreateUploadedProtocolContentInfoTable(protocolName,classes,code){
	Ext.getCmp("importedProtocolItemInfoTablePanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getUploadedProtocolItemsConfigData',
		success:function(response) {
			Ext.getCmp("importedProtocolItemInfoTablePanel_Id").getEl().unmask();
			Ext.getCmp("importedProtocolItemInfoTablePanel_Id").setTitle(protocolName);
			var result =  Ext.JSON.decode(response.responseText);
			if(importProtocolContentHandsontableHelper==null || importProtocolContentHandsontableHelper.hot==undefined){
				importProtocolContentHandsontableHelper = ImportProtocolContentHandsontableHelper.createNew("importedProtocolItemInfoTableDiv_Id");
				var colHeaders="[" 
					+"['','',{label: '下位机', colspan: 5},{label: '上位机', colspan: 5}]," 
					+"['序号','名称','起始地址(十进制)','存储数据类型','存储数据数量','读写类型','响应模式','接口数据类型','小数位数','换算比例','单位','解析模式']" 
					+"]";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,importProtocolContentHandsontableHelper);}},"
					 	+"{data:'storeDataType',type:'dropdown',strict:true,allowInvalid:false,source:['bit','byte','int16','uint16','float32','bcd']}," 
					 	+"{data:'quantity',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,importProtocolContentHandsontableHelper);}}," 
					 	+"{data:'RWType',type:'dropdown',strict:true,allowInvalid:false,source:['只读', '只写', '读写']}," 
					 	+"{data:'acqMode',type:'dropdown',strict:true,allowInvalid:false,source:['主动上传', '被动响应']}," 
						+"{data:'IFDataType',type:'dropdown',strict:true,allowInvalid:false,source:['bool','int','float32','float64','string']}," 
						+"{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importProtocolContentHandsontableHelper);}}," 
						+"{data:'ratio',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importProtocolContentHandsontableHelper);}}," 
						+"{data:'unit'}," 
						+"{data:'resolutionMode',type:'dropdown',strict:true,allowInvalid:false,source:['开关量', '枚举量','数据量']}" 
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

function saveSingelImportedProtocol(protocolName,deviceType){
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
				Ext.Msg.alert('提示', "<font color=blue>保存成功。</font>");
			}else{
				Ext.Msg.alert('提示', "<font color=red>保存失败。</font>");
			}
			Ext.getCmp("ImportProtocolContentTreeGridPanel_Id").getStore().load();
			Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id").getStore().load();
		},
		failure : function() {
			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
		}
	});
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
					Ext.Msg.alert('提示', "<font color=blue>保存成功。</font>");
				}else{
					Ext.Msg.alert('提示', "<font color=red>保存失败。</font>");
				}
				Ext.getCmp("ImportProtocolContentTreeGridPanel_Id").getStore().load();
				Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id").getStore().load();
			},
			failure : function() {
				Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
			}
		});
	}else{
		Ext.Msg.alert('提示', "<font color=blue>没有可保存的协议。</font>");
	}
	
}

iconImportSingleProtocolAction = function(value, e, record) {
	var resultstring='';
	var protocolName=record.data.text;
	var deviceType=Ext.getCmp("ImportProtocolWinDeviceType_Id").getValue();
	if( record.data.classes==1 && record.data.saveSign!=2 ){
		resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
		"onclick=saveSingelImportedProtocol(\""+protocolName+"\",\""+deviceType+"\")>保存...</a>";
	}
	
	
	return resultstring;
}