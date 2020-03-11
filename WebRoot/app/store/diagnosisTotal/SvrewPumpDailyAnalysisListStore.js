Ext.define('AP.store.diagnosisTotal.SvrewPumpDailyAnalysisListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.svrewPumpDailyAnalysisListStore',
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
            var DiagnosisTotalData = Ext.getCmp("ScrewPumpDailyAnalysisWellList_Id");
            if (!isNotVal(DiagnosisTotalData)) {
                var bbar = new Ext.PagingToolbar({
                	store: store,
                	displayInfo: true,
                	displayMsg: '当前 {0}~{1}条  共 {2} 条'
    	        });
                DiagnosisTotalData = Ext.create('Ext.grid.Panel', {
                    id: "ScrewPumpDailyAnalysisWellList_Id",
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
                    			Ext.create("AP.store.diagnosisTotal.ScrewPumpDailyAnalysiCurveDataStore");
                    			Ext.create("AP.store.diagnosisTotal.ScrewPumpDaillyAnalysisTableStore");
                    		}
                    	},
                    	itemdblclick: function (view,record,item,index,e,eOpts) {
                    		var wellName=Ext.getCmp('ScrewPumpDailyAnalysisWellCom_Id').getValue();
                    		if(wellName==null||wellName==""){
                    			Ext.getCmp("ScrewPumpDailyAnalysisWellListPanel_Id").setTitle("历史数据");
                    			Ext.getCmp("ScrewPumpDailyAnalysisDate_Id").hide();
                    			Ext.getCmp("ScrewPumpDailyAnalysisStartDate_Id").show();
                            	Ext.getCmp("ScrewPumpDailyAnalysisEndDate_Id").show();
                            	Ext.getCmp("ScrewPumpDailyAnalysisHisBtn_Id").hide();
                                Ext.getCmp("ScrewPumpDailyAnalysisAllBtn_Id").show();
                    			
                    			Ext.getCmp('ScrewPumpDailyAnalysisWellCom_Id').setValue(record.data.wellName);
                            	Ext.getCmp('ScrewPumpDailyAnalysisWellCom_Id').setRawValue(record.data.wellName);
                            	var statPanelId=getPCPSelectDailyStatType().pieDivId;
                            	Ext.getCmp(statPanelId).collapse();
                                
                                Ext.getCmp('ScrewPumpDailyAnalysisWellList_Id').getStore().loadPage(1);
                    		}
                        }
                    }
                });
                var panelId=getPCPSelectDailyStatType().panelId;
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
            	        	Ext.create("AP.store.diagnosisTotal.ScrewPumpDailyAnalysisStatStore");
            	         }
            	)
            }else{
            	DiagnosisTotalData.reconfigure(newColumns);
            }
            var startDate=Ext.getCmp('ScrewPumpDailyAnalysisStartDate_Id').rawValue;
            if(startDate==''||null==startDate){
            	Ext.getCmp("ScrewPumpDailyAnalysisStartDate_Id").setValue(get_rawData.startDate);
            }
            if(get_rawData.totalCount>0){
            	DiagnosisTotalData.getSelectionModel().select(0,true);//选中第一行
            }else{
            	$("#ScrewPumpDailyCurveDataChartDiv_Id").html('');
            	Ext.getCmp("ScrewPumpDailyAnalysisTableCalDataPanel_Id").removeAll();
            	Ext.getCmp("ScrewPumpDailyAnalysisTableAcqDataPanel_Id").removeAll();
            }
            
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var jh = Ext.getCmp('ScrewPumpDailyAnalysisWellCom_Id').getValue();
        	var totalDate=Ext.getCmp('ScrewPumpDailyAnalysisDate_Id').rawValue;
        	var statValue=Ext.getCmp('ScrewPumpDailyStatSelectedValue_Id').getValue();
        	var startDate=Ext.getCmp('ScrewPumpDailyAnalysisStartDate_Id').rawValue;
        	var endDate=Ext.getCmp('ScrewPumpDailyAnalysisEndDate_Id').rawValue;
        	var type=getPCPSelectDailyStatType().type;
        	var new_params = {
                    orgId: leftOrg_Id,
                    jh: jh,
                    totalDate:totalDate,
                    startDate:startDate,
                    endDate:endDate,
                    type:type,
                    statValue:statValue,
                    wellType:400
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});