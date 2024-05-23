Ext.define('AP.store.acquisitionUnit.ModbusProtocolProductionReportTemplateStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.ModbusProtocolProductionReportTemplateStore',
    fields: ['templateName','templateCode','calculateType'],
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/getReportDataTemplateList',
        actionMethods: {
            read: 'POST'
        },
        reader: {
            type: 'json',
            rootProperty: 'totalRoot',
            totalProperty: 'totalCount',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, options, eOpts) {
        	var gridPanel = Ext.getCmp("ReportUnitProductionReportTemplateListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
            	gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "ReportUnitProductionReportTemplateListGridPanel_Id",
//                    layout: "fit",
                    columnLines: true,
                    forceFit: true,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    selModel:{
                    	selType: 'checkboxmodel',
                    	mode:'SINGLE',//"SINGLE" / "SIMPLE" / "MULTI" 
                    	checkOnly:false,
                    	allowDeselect:false
                    },
                    store: store,
                    columns: [{
                    	text: '区域日报模板列表',
                        flex: 8,
                        align: 'left',
                        dataIndex: 'templateName',
                        renderer: function (value) {
                            if (isNotVal(value)) {
                                return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                            }
                        }
                    },{
                        header: 'templateCode',
                        hidden: true,
                        dataIndex: 'templateCode'
                    },{
                        header: 'calculateType',
                        hidden: true,
                        dataIndex: 'calculateType'
                    }],
                    listeners: {
                    	checkchange: function (node, checked) {
                    		
                        },
                        selectionchange ( view, selected, eOpts ){
                        	
                        },select( v, record, index, eOpts ){
                        	CreateProductionReportTemplateInfoTable(record.data.templateName,record.data.calculateType,record.data.templateCode);
                        	CreateproductionReportTotalItemsInfoTable();
                        }
                    }

                });
                var panel = Ext.getCmp("ReportUnitProductionReportTemplateListPanel_Id");
                panel.add(gridPanel);
            }
            
            gridPanel.getSelectionModel().deselectAll(true);
            var selectRow= Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue();
        	if(selectRow>=0){
        		var selectUnitReportTemplateCode='';
        		var selectUnit = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getSelectionModel().getSelection()[0].data;
            	if(selectUnit.classes==0){
            		if(isNotVal(selectUnit.children) && selectUnit.children.length>0){
            			selectUnitReportTemplateCode=selectUnit.children[0].productionReportTemplate;
            		}else{
            			
            		}
            	}else if(selectUnit.classes==1){
            		selectUnitReportTemplateCode=selectUnit.productionReportTemplate;
            	}
            	
            	var store = gridPanel.getStore();
            	var selected=false;
            	for(var i=0;i<store.getCount();i++){
					var record=store.getAt(i);
					if(record.data.templateCode==selectUnitReportTemplateCode){
						gridPanel.getSelectionModel().select(i, true);
						selected=true;
						break;
					}
				}
            	if(!selected){
            		if(productionReportTemplateHandsontableHelper!=null){
    					if(productionReportTemplateHandsontableHelper.hot!=undefined){
    						productionReportTemplateHandsontableHelper.hot.destroy();
    					}
    					productionReportTemplateHandsontableHelper=null;
    				}
            		Ext.getCmp("ModbusProtocolReportUnitProductionTemplateTableInfoPanel_Id").setTitle('区域报表模板：');
            	}
        	}
//            gridPanel.getSelectionModel().deselectAll(true);
//            gridPanel.getSelectionModel().select(0, true);
        },
        beforeload: function (store, options) {
        	var reportType=1;
        	var new_params = {
        			reportType: reportType
                };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});