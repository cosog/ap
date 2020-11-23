Ext.define('AP.store.diagnosisTotal.DiagnosisTotalDataStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.diagnosisTotalDataStore',
//    model: 'AP.model.balanceMonitor.BalanceMonitorModel',
    autoLoad: true,
    pageSize: 50,
    proxy: {
        type: 'ajax',
        url: context + '/diagnosisTotalController/getDiagnosisTotalData',
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
        load: function (store, record, f, op, o) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var arrColumns = get_rawData.columns;
            var column = createDiagnosisTotalColumn(arrColumns)
            Ext.getCmp("DiagnosisTotalColumnStr_Id").setValue(column);
            Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(get_rawData.AlarmShowStyle));
            var newColumns = Ext.JSON.decode(column);
            var DiagnosisTotalData = Ext.getCmp("DiagnosisTotalData_Id");
            if (!isNotVal(DiagnosisTotalData)) {
                var bbar = new Ext.PagingToolbar({
                	store: store,
                	displayInfo: true,
                	displayMsg: '当前 {0}~{1}条  共 {2} 条'
    	        });
                DiagnosisTotalData = Ext.create('Ext.grid.Panel', {
                    id: "DiagnosisTotalData_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: false,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    bbar:bbar,
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	itemclick:function( view , record , item , index , e , eOpts ) {
                    	},
                    	selectionchange:function(grid, record , eOpts) {
                    		if(record.length>0){
                                var tabPanel = Ext.getCmp("DiagnosisTotalCenterTabPanel_Id");
                                var activeId = tabPanel.getActiveTab().id;
                                if(activeId=="DiagnosisTotalCenterPanel1_Id"){//井筒分析
                                	var DiagnosisTotalFSdiagramOverlayGrid = Ext.getCmp("DiagnosisTotalFSdiagramOverlayGrid_Id");
                        			if(isNotVal(DiagnosisTotalFSdiagramOverlayGrid)){
                        				DiagnosisTotalFSdiagramOverlayGrid.getStore().load();
                        			}else{
                        				Ext.create("AP.store.diagnosisTotal.DiagnosisTotalFSDiagramOverlayStore");
                        			}
                                }else if(activeId=="DiagnosisTotalCenterPanel2_Id"){//地面分析
                                	Ext.create("AP.store.diagnosisTotal.DiagnosisTotalDynamicCurveStore");
                                }
                    			
                    			
                    			
                    			
                    			Ext.create("AP.store.diagnosisTotal.DiagnosisTotalAnalysisTableStore");
                    		}
                    	},
                    	itemdblclick: function (view,record,item,index,e,eOpts) {
                    		var wellName=Ext.getCmp('FSDiagramAnalysisDailyDetailsWellCom_Id').getValue();
                    		if(wellName==null||wellName==""){
                    			Ext.getCmp("DiagnosisTotalWellListPanel_Id").setTitle("单井历史");
                    			Ext.getCmp("TotalDiagnosisDate_Id").hide();
                            	Ext.getCmp("DiagnosisTotalStartDate_Id").show();
                            	Ext.getCmp("DiagnosisTotalEndDate_Id").show();
                            	Ext.getCmp("DiagnosisTotalHisBtn_Id").hide();
                                Ext.getCmp("DiagnosisTotalAllBtn_Id").show();
                    			
                    			Ext.getCmp('FSDiagramAnalysisDailyDetailsWellCom_Id').setValue(record.data.wellName);
                            	Ext.getCmp('FSDiagramAnalysisDailyDetailsWellCom_Id').setRawValue(record.data.wellName);
                            	var statPanelId=getSelectTotalStatType().piePanelId;
                            	Ext.getCmp(statPanelId).collapse();
                                
                                Ext.getCmp('DiagnosisTotalData_Id').getStore().loadPage(1);
                    		}
                        }
                    }
                });
                var panelId=getSelectTotalStatType().panelId;
                var DiagnosisTotalDataPanel = Ext.getCmp(panelId);
                DiagnosisTotalDataPanel.add(DiagnosisTotalData);
                
                var length = DiagnosisTotalData.dockedItems.keys.length;
                var refreshStr= "";
                for (var i = 0; i < length; i++) {
                   if (DiagnosisTotalData.dockedItems.keys[i].indexOf("pagingtoolbar") !== -1) {
                      refreshStr= DiagnosisTotalData.dockedItems.keys[i];
                   }
                }
            	DiagnosisTotalData.dockedItems.get(refreshStr).child('#refresh').setHandler(   
            	        function() {
            	        	Ext.create("AP.store.diagnosisTotal.DiagnosisTotalStatStore");
            	         }
            	)
            }else{
            	DiagnosisTotalData.reconfigure(newColumns);
            }
            var startDate=Ext.getCmp('DiagnosisTotalStartDate_Id').rawValue;
            if(startDate==''||null==startDate){
            	Ext.getCmp("DiagnosisTotalStartDate_Id").setValue(get_rawData.start_date);
            }
            if(get_rawData.totalCount>0){
            	DiagnosisTotalData.getSelectionModel().select(0,true);//选中第一行
            }else{
            	$("#DiagnosisTotalOverlayDiv_Id").html('');
            	$("#DiagnosisTotalPowerOverlayDiv_Id").html('');
            	$("#DiagnosisTotalCurrentOverlayDiv_Id").html('');
            	Ext.getCmp("FSDiagramAnalysisDailyDetailsRightRunRangeTextArea_Id").setValue("");
        		Ext.getCmp("FSDiagramAnalysisDailyDetailsRightResultCodeTextArea_Id").setValue("");
            	Ext.getCmp("DiagnosisTotalFSdiagramOverlayTable_Id").removeAll();
            	Ext.getCmp("TotalAnalysisDataPanel_Id").removeAll();
            	Ext.getCmp("TotalAcqDataPanel_Id").removeAll();
            }
            
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var wellName = Ext.getCmp('FSDiagramAnalysisDailyDetailsWellCom_Id').getValue();
        	var totalDate=Ext.getCmp('TotalDiagnosisDate_Id').rawValue;
        	var statValue=Ext.getCmp('PumpingDailyStatSelectedItems_Id').getValue();
        	var startDate=Ext.getCmp('DiagnosisTotalStartDate_Id').rawValue;
        	var endDate=Ext.getCmp('DiagnosisTotalEndDate_Id').rawValue;
        	var type=getSelectTotalStatType().type;
        	var new_params = {
                    orgId: leftOrg_Id,
                    wellName: wellName,
                    totalDate:totalDate,
                    startDate:startDate,
                    endDate:endDate,
                    type:type,
                    statValue:statValue,
                    wellType:200
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});