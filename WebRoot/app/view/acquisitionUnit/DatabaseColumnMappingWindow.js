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
    width: 600,
    minWidth: 600,
    height: 600,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	tbar: [{
            	xtype : "combobox",
				fieldLabel : '设备类型',
				labelWidth: 60,
                width: 150,
				id : 'databaseColumnMappingDeviceTypeComb_Id',
				triggerAction : 'all',
				selectOnFocus : true,
			    forceSelection : true,
			    value:0,
				editable : false,
				store : new Ext.data.SimpleStore({
							fields : ['value', 'text'],
							data : [[0, '井设备']]
						}),
				displayField : 'text',
				valueField : 'value',
				queryMode : 'local',
				emptyText : '请选择设备类型',
				blankText : '请选择设备类型',
				listeners : {
					select:function(v,o){
						CreateDatabaseColumnMappingTable();
					}
				}
            }],
            html: '<div id="HistoryQueryDataDetailsDiv_Id" style="width:100%;height:100%;"></div>',
            listeners: {
                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                	if(databaseColumnMappingHandsontableHelper!=null&&databaseColumnMappingHandsontableHelper.hot!=null&&databaseColumnMappingHandsontableHelper.hot!=undefined){
                		databaseColumnMappingHandsontableHelper.hot.refreshDimensions();
                	}else{
              			CreateDatabaseColumnMappingTable();
                	}
                },
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

function CreateDatabaseColumnMappingTable(){
	var deviceType = Ext.getCmp("databaseColumnMappingDeviceTypeComb_Id").getValue();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getDatabaseColumnMappingTable',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			
			if(databaseColumnMappingHandsontableHelper==null || databaseColumnMappingHandsontableHelper.hot==undefined){
				databaseColumnMappingHandsontableHelper = DatabaseColumnMappingHandsontableHelper.createNew("HistoryQueryDataDetailsDiv_Id");
				var colHeaders="['序号','名称','字段']";
				var columns="[" 
						+"{data:'id'}," 
						+"{data:'itemName'}," 
						+"{data:'itemColumn'}"
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
			deviceType: deviceType
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
	        
	        
	        
	        databaseColumnMappingHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = '#DC2828';   
	             td.style.color='#FFFFFF';
	        }
	        
	        databaseColumnMappingHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(184, 184, 184)';
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
	                    indicators: false
	                },
	                columns:databaseColumnMappingHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                rowHeaders: true, //显示行头
	                colHeaders: databaseColumnMappingHandsontableHelper.colHeaders, //显示列头
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {}
	        	});
	        }
	        return databaseColumnMappingHandsontableHelper;
	    }
};