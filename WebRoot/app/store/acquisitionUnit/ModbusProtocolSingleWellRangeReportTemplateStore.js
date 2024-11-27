Ext.define('AP.store.acquisitionUnit.ModbusProtocolSingleWellRangeReportTemplateStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.ModbusProtocolSingleWellRangeReportTemplateStore',
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
        	var gridPanel = Ext.getCmp("ReportUnitSingleWellRangeReportTemplateListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
            	gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "ReportUnitSingleWellRangeReportTemplateListGridPanel_Id",
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
                    	text: loginUserLanguageResource.deviceDailyReportTemplateList,
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
                        	CreateSingleWellRangeReportTemplateInfoTable(record.data.templateName,record.data.calculateType,record.data.templateCode);
                        	CreateSingleWellRangeReportTotalItemsInfoTable();
                        }
                    }

                });
                var panel = Ext.getCmp("ReportUnitSingleWellRangeReportTemplateListPanel_Id");
                panel.add(gridPanel);
            }
            
            gridPanel.getSelectionModel().deselectAll(true);
            var selectRow= Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue();
        	if(selectRow>=0){
        		var selectUnitReportTemplateCode='';
        		var selectUnit = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getSelectionModel().getSelection()[0].data;
            	if(selectUnit.classes==0){
            		if(isNotVal(selectUnit.children) && selectUnit.children.length>0){
            			selectUnitReportTemplateCode=selectUnit.children[0].singleWellRangeReportTemplate;
            		}else{
            			
            		}
            	}else if(selectUnit.classes==1){
            		selectUnitReportTemplateCode=selectUnit.singleWellRangeReportTemplate;
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
            		if(singleWellRangeReportTemplateContentHandsontableHelper!=null){
            			if(singleWellRangeReportTemplateContentHandsontableHelper.hot!=undefined){
            				singleWellRangeReportTemplateContentHandsontableHelper.hot.destroy();
            			}
            			singleWellRangeReportTemplateContentHandsontableHelper=null;
            		}
            		Ext.getCmp("ReportUnitSingleWellRangeReportTemplateTableInfoPanel_Id").setTitle(loginUserLanguageResource.deviceDailyReportTemplate);
            	}
        	}
        },
        beforeload: function (store, options) {
        	var reportType=0;
        	var new_params = {
        			reportType: reportType
                };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});