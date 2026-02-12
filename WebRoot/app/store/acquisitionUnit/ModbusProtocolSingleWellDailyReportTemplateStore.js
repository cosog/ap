Ext.define('AP.store.acquisitionUnit.ModbusProtocolSingleWellDailyReportTemplateStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.ModbusProtocolSingleWellDailyReportTemplateStore',
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
        	var gridPanel = Ext.getCmp("ReportUnitSingleWellDailyReportTemplateListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
            	gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "ReportUnitSingleWellDailyReportTemplateListGridPanel_Id",
//                    layout: "fit",
                    columnLines: true,
                    forceFit: true,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + loginUserLanguageResource.emptyMsg + "></div>",
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
                    	text: loginUserLanguageResource.deviceHourlyReportTemplateList,
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
                        	CreateSingleWellDailyReportTemplateInfoTable(record.data.templateName,record.data.calculateType,record.data.templateCode);
                        	CreateSingleWellDailyReportTotalItemsInfoTable();
                        }
                    }

                });
                var panel = Ext.getCmp("ReportUnitSingleWellDailyReportTemplateListPanel_Id");
                panel.add(gridPanel);
            }
            
            gridPanel.getSelectionModel().deselectAll(true);
            var selectRow= Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue();
        	if(selectRow>=0){
        		var selectUnitReportTemplateCode='';
        		var selectUnitName='';
        		var selectUnit = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getSelectionModel().getSelection()[0].data;
        		
            	if(selectUnit.classes==1){
            		selectUnitReportTemplateCode=selectUnit.singleWellDailyReportTemplate;
            		selectUnitName=selectUnit.text;
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
            		if(singleWellDailyReportTemplateHandsontableHelper!=null){
            			if(singleWellDailyReportTemplateHandsontableHelper.hot!=undefined){
            				singleWellDailyReportTemplateHandsontableHelper.hot.destroy();
            			}
            			singleWellDailyReportTemplateHandsontableHelper=null;
            		}
            		
            		if(singleWellDailyReportTemplateContentHandsontableHelper!=null){
            			if(singleWellDailyReportTemplateContentHandsontableHelper.hot!=undefined){
            				singleWellDailyReportTemplateContentHandsontableHelper.hot.destroy();
            			}
            			singleWellDailyReportTemplateContentHandsontableHelper=null;
            		}
            		if(selectUnit.classes==1){
            			Ext.getCmp("ReportUnitSingleWellDailyReportTemplateTableInfoPanel_Id").setTitle(selectUnitName + '/'+loginUserLanguageResource.deviceHourlyReportTemplate);
                		Ext.getCmp("ReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id").setTitle(selectUnitName + '/'+loginUserLanguageResource.deviceHourlyReportContentConfig);
            		}else{
            			Ext.getCmp("ReportUnitSingleWellDailyReportTemplateTableInfoPanel_Id").setTitle(loginUserLanguageResource.deviceHourlyReportTemplate);
                		Ext.getCmp("ReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id").setTitle(loginUserLanguageResource.deviceHourlyReportContentConfig);
            		}
            		
            	}
        	}
        },
        beforeload: function (store, options) {
        	var reportType=2;
        	var new_params = {
        			reportType: reportType
                };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});