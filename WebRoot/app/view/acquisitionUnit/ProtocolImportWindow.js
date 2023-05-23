var importProtocolContentHandsontableHelper=null;
Ext.define("AP.view.acquisitionUnit.ProtocolImportWindow", {
    extend: 'Ext.window.Window',
    id:'ProtocolImportWindow_Id',
    alias: 'widget.protocolImportWindow',
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
        	    }]
    		},'->',{
    	    	xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                handler: function (v, o) {
                	
                }
    	    }],
            layout: 'border',
            items: [{
            	region: 'west',
            	width:'25%',
            	title:'协议相关内容',
            	layout: 'fit',
            	split: true,
                collapsible: true,
            	id:"importPootocolTreePanel_Id"
            },{
            	region: 'center',
            	id:"importedProtocolItemInfoTablePanel_Id",
            	html: '<div id="importedProtocolItemInfoTableDiv_Id" style="width:100%;height:100%;"></div>',
            	listeners: {
            		resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
            			
            		}
            	}
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function submitImportedProtocolFile() {
	var form = Ext.getCmp("ProtocolImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/getImportedProtocolFile',
            timeout: 1000*60*10,
            method:'post',
            waitMsg: '文件上传中...',
            success: function(response, action) {
            	var result = action.result;
            	if (result.flag == true) {
            		Ext.Msg.alert("提示", "上传成功 ");
            		Ext.getCmp("importPootocolTreePanel_Id").setTitle("<font color=red>"+result.protocolName+"</font>协议相关内容");
            	}else{
            		Ext.Msg.alert("提示", "上传数据格式有误！ ");
            		Ext.getCmp("importPootocolTreePanel_Id").setTitle("协议相关内容");
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

function CreateImportProtocolContentInfoTable(id,classes,type){
	Ext.getCmp("importedProtocolItemInfoTablePanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportProtocolContentData',
		success:function(response) {
			Ext.getCmp("importedProtocolItemInfoTablePanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(importProtocolContentHandsontableHelper==null || importProtocolContentHandsontableHelper.hot==undefined){
				importProtocolContentHandsontableHelper = ImportProtocolContentHandsontableHelper.createNew("importedProtocolItemInfoTableDiv_Id");
//				var colHeaders="['序号','名称','起始地址','存储数据类型','存储数据数量','读写类型','响应模式','接口数据类型','小数位数','换算比例','单位','解析模式']";
				
				
				var colHeaders="[" 
					+"['','',{label: '下位机', colspan: 5},{label: '上位机', colspan: 5}]," 
					+"['序号','名称','起始地址','存储数据类型','存储数据数量','读写类型','响应模式','接口数据类型','小数位数','换算比例','单位','解析模式']" 
					+"]";
				
				var columns="[{data:'id'},{data:'title'},"
				 	+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolConfigAddrMappingItemsHandsontableHelper);}},"
				 	+"{data:'storeDataType',type:'dropdown',strict:true,allowInvalid:false,source:['bit','byte','int16','uint16','float32','bcd']}," 
				 	+"{data:'quantity',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolConfigAddrMappingItemsHandsontableHelper);}}," 
				 	+"{data:'RWType',type:'dropdown',strict:true,allowInvalid:false,source:['只读', '只写', '读写']}," 
				 	+"{data:'acqMode',type:'dropdown',strict:true,allowInvalid:false,source:['主动上传', '被动响应']}," 
					+"{data:'IFDataType',type:'dropdown',strict:true,allowInvalid:false,source:['bool','int','float32','float64','string']}," 
					+"{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolConfigAddrMappingItemsHandsontableHelper);}}," 
					+"{data:'ratio',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolConfigAddrMappingItemsHandsontableHelper);}}," 
					+"{data:'unit'}," 
					+"{data:'resolutionMode',type:'dropdown',strict:true,allowInvalid:false,source:['开关量', '枚举量','数据量']}" 
					+"]";
				importProtocolContentHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importProtocolContentHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importProtocolContentHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolContentHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importProtocolContentHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolContentHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importedProtocolItemInfoTablePanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			type:type,
			classes:classes
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
//	                colHeaders:importProtocolContentHandsontableHelper.colHeaders,//显示列头
	                nestedHeaders:importProtocolContentHandsontableHelper.colHeaders,//显示列头
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
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        return importProtocolContentHandsontableHelper;
	    }
};