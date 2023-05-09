var exportDeviceInfoHandsontableHelper=null;
Ext.define("AP.view.well.ExportDeviceInfoWindow", {
    extend: 'Ext.window.Window',
    id:'ExportDeviceInfoWindow_Id',
    alias: 'widget.exportDeviceInfoWindow',
    layout: 'fit',
    title:'设备信息导出',
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 1300,
    minWidth: 1200,
    height: 600,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.create('AP.store.well.ExportDeviceInfoApplicationScenariosListStore');
        Ext.apply(me, {
        	tbar: [{
                xtype: "hidden",
                fieldLabel: '设备类型',
                id: 'ExportDeviceInfoDeviceType_Id',
                value: ''
            },'->',{
                xtype: 'button',
                text: cosog.string.exportExcel,
                iconCls: 'export',
                handler: function (v, o) {
                  var fields = "";
                  var heads = "";
                  var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
                  var deviceType = Ext.getCmp('ExportDeviceInfoDeviceType_Id').getValue();
                  var wellInformationName='';
                  var applicationScenarios=0;
                  var gridPanel = Ext.getCmp("ExportDeviceInfoApplicationScenariosListGridPanel_Id");
                  if (isNotVal(gridPanel)) {
                  	applicationScenarios = Ext.getCmp("ExportDeviceInfoApplicationScenariosListGridPanel_Id").getSelectionModel().getSelection()[0].data.applicationScenarios;
                  }
                  var deviceTypeName='抽油机井';
                  var applicationScenariosName='油井';
                  if(parseInt(deviceType)>=200 && parseInt(deviceType)<300){
                	  deviceTypeName='螺杆泵井';
                  }
                  if(parseInt(applicationScenarios)==0){
                	  applicationScenariosName='煤层气井';
                  }
                  var url = context + '/wellInformationManagerController/exportWellInformationDetailsData';
                  for (var i = 0; i < exportDeviceInfoHandsontableHelper.colHeaders.length; i++) {
                      fields += exportDeviceInfoHandsontableHelper.columns[i].data + ",";
                      heads += exportDeviceInfoHandsontableHelper.colHeaders[i] + ","
                  }
                  if (isNotVal(fields)) {
                      fields = fields.substring(0, fields.length - 1);
                      heads = heads.substring(0, heads.length - 1);
                  }

                  var param = "&fields=" + fields 
                  + "&heads=" + URLencode(URLencode(heads)) 
                  + "&orgId=" + leftOrg_Id 
                  + "&deviceType="+deviceType
                  + "&applicationScenarios="+applicationScenarios
                  + "&wellInformationName=" + URLencode(URLencode(wellInformationName)) 
                  + "&recordCount=10000" 
                  + "&fileName=" + URLencode(URLencode(deviceTypeName+'-'+applicationScenariosName)) + "&title=" + URLencode(URLencode(deviceTypeName+'-'+applicationScenariosName));
                  openExcelWindow(url + '?flag=true' + param);
                }
            }],
            layout: 'border',
            items: [{
            	title:'应用场景',
            	region: 'west',
            	width:'15%',
            	layout: 'fit',
            	header:false,
        		split: true,
                collapsible: true,
        		id:'ExportDeviceInfoApplicationScenariosInfoPanel_Id'
            },{
            	region: 'center',
            	html: '<div id="ExportDeviceInfoTableDiv_Id" style="width:100%;height:100%;"></div>',
            	listeners: {
            		resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(exportDeviceInfoHandsontableHelper!=null&&exportDeviceInfoHandsontableHelper.hot!=null&&exportDeviceInfoHandsontableHelper.hot!=undefined){
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		exportDeviceInfoHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}
                    }
            	}
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	if(exportDeviceInfoHandsontableHelper!=null){
    					if(exportDeviceInfoHandsontableHelper.hot!=undefined){
    						exportDeviceInfoHandsontableHelper.hot.destroy();
    					}
    					exportDeviceInfoHandsontableHelper=null;
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
function CreateAndLoadExportDeviceInfoTable(isNew) {
	if(isNew&&exportDeviceInfoHandsontableHelper!=null){
		if (exportDeviceInfoHandsontableHelper.hot != undefined) {
			exportDeviceInfoHandsontableHelper.hot.destroy();
		}
		exportDeviceInfoHandsontableHelper = null;
	}
    var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var deviceType = Ext.getCmp('ExportDeviceInfoDeviceType_Id').getValue();
    
    var applicationScenarios=0;
    var gridPanel = Ext.getCmp("ExportDeviceInfoApplicationScenariosListGridPanel_Id");
    if (isNotVal(gridPanel)) {
    	applicationScenarios = Ext.getCmp("ExportDeviceInfoApplicationScenariosListGridPanel_Id").getSelectionModel().getSelection()[0].data.applicationScenarios;
    }
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/wellInformationManagerController/getExportDeviceInfo',
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            applicationScenarios=result.applicationScenarios;
            if (exportDeviceInfoHandsontableHelper == null || exportDeviceInfoHandsontableHelper.hot == null || exportDeviceInfoHandsontableHelper.hot == undefined) {
                exportDeviceInfoHandsontableHelper = ExportDeviceInfoHandsontableHelper.createNew("ExportDeviceInfoTableDiv_Id");
                var colHeaders = "[";
                var columns = "[";

                for (var i = 0; i < result.columns.length; i++) {
                    var colHeader="'" + result.columns[i].header + "'";
                    var dataIndex=result.columns[i].dataIndex;
                    
                    if(applicationScenarios==0){
                    	if(dataIndex.toUpperCase() === "crudeOilDensity".toUpperCase() 
                    			|| dataIndex.toUpperCase() === "saturationPressure".toUpperCase() 
                    			|| dataIndex.toUpperCase() === "waterCut".toUpperCase() 
                    			|| dataIndex.toUpperCase() === "weightWaterCut".toUpperCase() 
                    			|| dataIndex.toUpperCase() === "productionGasOilRatio".toUpperCase() ){
                    		continue;
                    	}else if(dataIndex.toUpperCase() === "reservoirDepth".toUpperCase() || dataIndex.toUpperCase() === "reservoirTemperature".toUpperCase()){
                    		colHeader=colHeader.replace('油层','煤层');
                    	}
                    }
                    
                	colHeaders += colHeader;
                	columns += "{data:'" + dataIndex + "'}";
                    if (i < result.columns.length - 1) {
                        colHeaders += ",";
                        columns += ",";
                    }
                }
                colHeaders += "]";
                columns += "]";
                exportDeviceInfoHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
                exportDeviceInfoHandsontableHelper.columns = Ext.JSON.decode(columns);
                exportDeviceInfoHandsontableHelper.createTable(result.totalRoot);
            } else {
            	exportDeviceInfoHandsontableHelper.hot.loadData(result.totalRoot);
            }
        },
        failure: function () {
            Ext.MessageBox.alert("错误", "与后台联系的时候出了问题");
        },
        params: {
        	orgId: orgId,
        	deviceType: deviceType,
            recordCount: 50,
            applicationScenarios:applicationScenarios,
            page: 1,
            limit: 10000
        }
    });
};

var ExportDeviceInfoHandsontableHelper = {
    createNew: function (divid) {
        var exportDeviceInfoHandsontableHelper = {};
        exportDeviceInfoHandsontableHelper.hot = '';
        exportDeviceInfoHandsontableHelper.divid = divid;
        exportDeviceInfoHandsontableHelper.validresult = true; //数据校验
        exportDeviceInfoHandsontableHelper.colHeaders = [];
        exportDeviceInfoHandsontableHelper.columns = [];

        exportDeviceInfoHandsontableHelper.AllData = {};
        exportDeviceInfoHandsontableHelper.updatelist = [];
        exportDeviceInfoHandsontableHelper.delidslist = [];
        exportDeviceInfoHandsontableHelper.insertlist = [];
        
        exportDeviceInfoHandsontableHelper.pumpingModelInfo = {};

        exportDeviceInfoHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
        }

        exportDeviceInfoHandsontableHelper.createTable = function (data) {
            $('#' + exportDeviceInfoHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + exportDeviceInfoHandsontableHelper.divid);
            exportDeviceInfoHandsontableHelper.hot = new Handsontable(hotElement, {
            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            	data: data,
                hiddenColumns: {
                    columns: [0],
                    indicators: false,
                    copyPasteEnabled: false
                },
                columns: exportDeviceInfoHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: true, //显示行头
                colHeaders: exportDeviceInfoHandsontableHelper.colHeaders, //显示列头
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
                    return cellProperties;
                },
                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
                	
                },
                afterDestroy: function () {
                },
                beforeRemoveRow: function (index, amount) {
                	
                },
                afterChange: function (changes, source) {
                	
                }
            });
        }

        return exportDeviceInfoHandsontableHelper;
    }
};