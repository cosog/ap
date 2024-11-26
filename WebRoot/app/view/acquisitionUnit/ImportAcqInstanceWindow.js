var importAcqInstanceConfigItemsHandsontableHelper=null;
Ext.define("AP.view.acquisitionUnit.ImportAcqInstanceWindow", {
    extend: 'Ext.window.Window',
    id:'ImportAcqInstanceWindow_Id',
    alias: 'widget.ImportAcqInstanceWindow',
    layout: 'fit',
    title:'采控实例导入',
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
        		id:'AcqInstanceImportForm_Id',
        		width: 300,
        	    bodyPadding: 0,
        	    frame: true,
        	    items: [{
        	    	xtype: 'filefield',
                	id:'AcqInstanceImportFilefield_Id',
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
                        	submitImportedAcqInstanceFile();
                        }
                    }
        	    },{
                    id: 'ImportAcqInstanceSelectItemType_Id', 
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                },{
                    id: 'ImportAcqInstanceSelectItemId_Id', 
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }]
    		},{
            	xtype: 'label',
            	id: 'ImportAcqInstanceWinTabLabel_Id',
            	hidden:true,
            	html: ''
            },{
				xtype : "hidden",
				id : 'ImportAcqInstanceWinDeviceType_Id',
				value:'0'
			},'->',{
    	    	xtype: 'button',
                text: '全部保存',
                iconCls: 'save',
                handler: function (v, o) {
                	var treeStore = Ext.getCmp("ImportAcqInstanceContentTreeGridPanel_Id").getStore();
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
                			info+=overlayCount+"个实例已存在";
                			if(collisionCount>0){
                				info+="，";
                			}
                		}
                		if(collisionCount>0){
                			info+=overlayCount+"个实例无权限修改";
                		}
                		info+="！是否执行全部保存？";
                		
                		Ext.Msg.confirm('提示', info, function (btn) {
                            if (btn == "yes") {
                            	saveAllImportedAcqInstance();
                            }
                        });
                	}else{
                		saveAllImportedAcqInstance();
                	}
                }
    	    }],
            layout: 'border',
            items: [{
            	region: 'west',
            	width:'25%',
            	title:'上传实例列表',
            	layout: 'fit',
            	split: true,
                collapsible: true,
            	id:"importAcqInstanceTreePanel_Id"
            },{
            	region: 'center',
            	id:"importedAcqInstanceItemInfoTablePanel_Id",
            	title:'采控项',
            	layout: "fit",
            	html:'<div class="importedAcqInstanceItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importedAcqInstanceItemInfoTableDiv_Id"></div></div>',
                listeners: {
                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	
                    }
                }
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	clearImportAcqInstanceHandsontable();
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function clearImportAcqInstanceHandsontable(){
	if(importAcqInstanceConfigItemsHandsontableHelper!=null){
		if(importAcqInstanceConfigItemsHandsontableHelper.hot!=undefined){
			importAcqInstanceConfigItemsHandsontableHelper.hot.destroy();
		}
		importAcqInstanceConfigItemsHandsontableHelper=null;
	}
}

function submitImportedAcqInstanceFile() {
	clearImportAcqInstanceHandsontable();
	var form = Ext.getCmp("AcqInstanceImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedAcqInstanceFile',
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
            	
            	var importAcqInstanceContentTreeGridPanel = Ext.getCmp("ImportAcqInstanceContentTreeGridPanel_Id");
            	if (isNotVal(importAcqInstanceContentTreeGridPanel)) {
            		importAcqInstanceContentTreeGridPanel.getStore().load();
            	}else{
            		Ext.create('AP.store.acquisitionUnit.ImportAcqInstanceContentTreeInfoStore');
            	}
            },
            failure : function() {
            	Ext.Msg.alert("提示", "【<font color=red>上传失败 </font>】");
			}
        });
    }
    return false;
};

adviceImportAcqInstanceCollisionInfoColor = function(val,o,p,e) {
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

function saveSingelImportedAcqInstance(instanceName,unitName,protocolName){
	Ext.Ajax.request({
		url : context + '/acquisitionUnitManagerController/saveSingelImportedAcqInstance',
		method : "POST",
		params : {
			instanceName : instanceName,
			unitName : unitName,
			protocolName : protocolName
		},
		success : function(response) {
			var result = Ext.JSON.decode(response.responseText);
			if (result.success==true) {
				Ext.Msg.alert('提示', "<font color=blue>保存成功。</font>");
			}else{
				Ext.Msg.alert('提示', "<font color=red>保存失败。</font>");
			}
			Ext.getCmp("ImportAcqInstanceContentTreeGridPanel_Id").getStore().load();

			var treeGridPanel = Ext.getCmp("ModbusProtocolInstanceConfigTreeGridPanel_Id");
            if (isNotVal(treeGridPanel)) {
            	treeGridPanel.getStore().load();
            }else{
            	Ext.create('AP.store.acquisitionUnit.ModbusProtocolInstanceTreeInfoStore');
            }
		},
		failure : function() {
			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
		}
	});
}

function saveAllImportedAcqInstance(){
	var unitNameList=[];
	var treeStore = Ext.getCmp("ImportAcqInstanceContentTreeGridPanel_Id").getStore();
	var count=treeStore.getCount();
	for(var i=0;i<count;i++){
		if(treeStore.getAt(i).data.classes==1 && treeStore.getAt(i).data.saveSign!=2){
			unitNameList.push(treeStore.getAt(i).data.text);
		}
	}
	if(unitNameList.length>0){
		Ext.Ajax.request({
			url : context + '/acquisitionUnitManagerController/saveAllImportedAcqInstance',
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
				Ext.getCmp("ImportAcqInstanceContentTreeGridPanel_Id").getStore().load();
				
				var treeGridPanel = Ext.getCmp("ModbusProtocolInstanceConfigTreeGridPanel_Id");
                if (isNotVal(treeGridPanel)) {
                	treeGridPanel.getStore().load();
                }else{
                	Ext.create('AP.store.acquisitionUnit.ModbusProtocolInstanceTreeInfoStore');
                }
			},
			failure : function() {
				Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
			}
		});
	}else{
		Ext.Msg.alert('提示', "<font color=blue>没有可保存的实例。</font>");
	}
}

iconImportSingleAcqInstanceAction = function(value, e, record) {
	var resultstring='';
	var instanceName=record.data.text;
	var unitName=record.data.unitName;
	var protocolName=record.data.protocol;

	if( record.data.classes==1 && record.data.saveSign!=2 ){
		resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
		"onclick=saveSingelImportedAcqInstance(\""+instanceName+"\",\""+unitName+"\,\""+protocolName+"\")>保存...</a>";
	}
	return resultstring;
}

function CreateImportAcqInstanceItemsInfoTable(protocolName,unitName,instanceName){
	Ext.getCmp("importedAcqInstanceItemInfoTablePanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportAcqInstanceItemsData',
		success:function(response) {
			Ext.getCmp("importedAcqInstanceItemInfoTablePanel_Id").getEl().unmask();
			Ext.getCmp("importedAcqInstanceItemInfoTablePanel_Id").setTitle(instanceName+"/采控项");
			var result =  Ext.JSON.decode(response.responseText);
			if(importAcqInstanceConfigItemsHandsontableHelper==null || importAcqInstanceConfigItemsHandsontableHelper.hot==undefined){
				importAcqInstanceConfigItemsHandsontableHelper = ImportAcqInstanceConfigItemsHandsontableHelper.createNew("importedAcqInstanceItemInfoTableDiv_Id");
				var colHeaders="[" 
					+"['','',{label: '下位机', colspan: 5},{label: '上位机', colspan: 5}]," 
					+"['序号','名称','起始地址(十进制)','存储数据类型','存储数据数量','读写类型','响应模式','接口数据类型','小数位数','换算比例','单位','解析模式']" 
					+"]";
				
				var columns="[{data:'id'},{data:'title'},"
				 	+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,importAcqInstanceConfigItemsHandsontableHelper);}},"
				 	+"{data:'storeDataType',type:'dropdown',strict:true,allowInvalid:false,source:['bit','byte','int16','uint16','float32','bcd']}," 
				 	+"{data:'quantity',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,importAcqInstanceConfigItemsHandsontableHelper);}}," 
				 	+"{data:'RWType',type:'dropdown',strict:true,allowInvalid:false,source:['只读', '只写', '读写']}," 
				 	+"{data:'acqMode',type:'dropdown',strict:true,allowInvalid:false,source:['主动上传', '被动响应']}," 
					+"{data:'IFDataType',type:'dropdown',strict:true,allowInvalid:false,source:['bool','int','float32','float64','string']}," 
					+"{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importAcqInstanceConfigItemsHandsontableHelper);}}," 
					+"{data:'ratio',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,importAcqInstanceConfigItemsHandsontableHelper);}}," 
					+"{data:'unit'}," 
					+"{data:'resolutionMode',type:'dropdown',strict:true,allowInvalid:false,source:['开关量', '枚举量','数据量']}" 
					+"]";
				importAcqInstanceConfigItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importAcqInstanceConfigItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importAcqInstanceConfigItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAcqInstanceConfigItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importAcqInstanceConfigItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAcqInstanceConfigItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importedAcqInstanceItemInfoTablePanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			unitName:unitName,
			instanceName:instanceName
        }
	});
};

var ImportAcqInstanceConfigItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importAcqInstanceConfigItemsHandsontableHelper = {};
	        importAcqInstanceConfigItemsHandsontableHelper.hot1 = '';
	        importAcqInstanceConfigItemsHandsontableHelper.divid = divid;
	        importAcqInstanceConfigItemsHandsontableHelper.validresult=true;//数据校验
	        importAcqInstanceConfigItemsHandsontableHelper.colHeaders=[];
	        importAcqInstanceConfigItemsHandsontableHelper.columns=[];
	        importAcqInstanceConfigItemsHandsontableHelper.AllData=[];
	        
	        importAcqInstanceConfigItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importAcqInstanceConfigItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importAcqInstanceConfigItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importAcqInstanceConfigItemsHandsontableHelper.divid);
	        	importAcqInstanceConfigItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,130,80,90,90,80,80,90,80,80,80,80],
	                columns:importAcqInstanceConfigItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
//	                colHeaders:importAcqInstanceConfigItemsHandsontableHelper.colHeaders,//显示列头
	                nestedHeaders:importAcqInstanceConfigItemsHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    if(importAcqInstanceConfigItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& importAcqInstanceConfigItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = importAcqInstanceConfigItemsHandsontableHelper.addCellStyle;
	    	            }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(importAcqInstanceConfigItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importAcqInstanceConfigItemsHandsontableHelper!=null
	                		&& importAcqInstanceConfigItemsHandsontableHelper.hot!=''
	                		&& importAcqInstanceConfigItemsHandsontableHelper.hot!=undefined 
	                		&& importAcqInstanceConfigItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importAcqInstanceConfigItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importAcqInstanceConfigItemsHandsontableHelper;
	    }
};