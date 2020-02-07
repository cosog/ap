Ext.define('AP.store.diagnosis.ScrewPumpRTAnalysisiWellListStore', {
    extend: 'Ext.data.Store',
    fields: ['jh','jbh', 'gtcjsj','gtbh','gklx', 'gkmc','yhjy','gkjgly','bjbz','rcylbd', 'jsdjrcyl', 'jsdjrcyl1',
		'hsl', 'yy', 'ty', 'jklw', 'dym', 'bg', 'cmd', 'bj', 'yymd',
		'jmb', 'byxccjscl', 'bjxlsl',
         'yytjxs', 'ydfelsl', 'gdfelsl', 'qyx', 
         'yjzdzh', 'yjzxzh', 'yjzdyl', 'yjzxyl', 'yjxyyl', 'yjylbfb',
         'ejzdzh', 'ejzxzh', 'ejzdyl', 'ejzxyl', 'ejxyyl', 'ejylbfb', 
         'sjzdzh', 'sjzxzh', 'sjzdyl', 'sjzxyl','sjxyyl', 'sjylbfb', 
         'sijzdzh', 'sijzxzh', 'sijzdyl', 'sijzxyl','sijxyyl', 'sijylbfb', 
         'gtcc', 'gtcc1','yggl', 'gggl', 'sgl', 'dmxtxl', 'jxxtxl', 'xtxl','dybmhdl', 'ccssxs', 'cygssl',
         'ygssl', 'gxzl', 'ytssxs', 'lsxs', 'cmxs', 'zbx', 'bpckyl', 'bxrkyl', 'brkjdqyb', 'bckjdqyb', 'zsszh',
         'zsxzhscc', 'zsxzhxcc', 'bpckwd', 'bpckytnd', 'bpckyytjxs',
         'tubingpressure','casingpressure','backpressure','wellheadfluidtemperature',
         'currenta','currentb','currentc','voltagea','voltageb','voltagec',
         'activepowerconsumption','reactivepowerconsumption','activepower','reactivepower','reversepower','powerfactor',
         'rpm','gcpl','btc','bjs','zzjmzj'],
    autoLoad: true,
    pageSize: 50,
    proxy: {
    	type: 'ajax',
        url: context + '/diagnosisAnalysisOnlyController/getProductionWellRTAnalysisWellList',
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
        load: function (store, sEops) {
        	var get_rawData = store.proxy.reader.rawData;
            var arrColumns = get_rawData.columns;
            var column = createDiagnosisColumn(arrColumns);
            Ext.getCmp("DiagnosisAnalysisColumnStr_Id").setValue(column);
            Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(get_rawData.AlarmShowStyle));
            var newColumns = Ext.JSON.decode(column);
            var bbar = new Ext.PagingToolbar({
            	store: store,
            	displayInfo: true,
            	displayMsg: '当前 {0}~{1}条  共 {2} 条'
	        });
            var gridPanel = Ext.getCmp("ScrewPumpRTAnalysisWellList_Id");
            if (!isNotVal(gridPanel)) {
            	gridPanel= Ext.create('Ext.grid.Panel', {
                    id: 'ScrewPumpRTAnalysisWellList_Id',
                    border: false,
                    forceFit: false,
                    bbar: bbar,
                    store:store,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    columnLines: true,
                    columns:newColumns,
                    listeners: {
                        selectionchange: function (view, selected, o) {
                            if (selected.length > 0) {
                        		//请求图形数据
                        		Ext.create("AP.store.diagnosis.ScrewPumpRTAnalysiCurveDataStore");
                        		Ext.create("AP.store.diagnosis.ScrewPumpRTAnalysisTableStore");
                            }
                            
                        },
                        itemdblclick: function (view,record,item,index,e,eOpts) {
                        	var jh=Ext.getCmp('ScrewPumpRealtimeAnalysisWellCom_Id').getValue();
                    		if(jh==null||jh==""){
                    			Ext.getCmp("ScrewPumpRealtimeAnalysisWellListPanel_Id").setTitle("历史数据");
                    			Ext.getCmp("ScrewPumpRealtimeAnalysisStartDate_Id").show();
                            	Ext.getCmp("ScrewPumpRealtimeAnalysisEndDate_Id").show();
                            	
                            	Ext.getCmp("ScrewPumpRealtimeAnalysisHisBtn_Id").hide();
                                Ext.getCmp("ScrewPumpRealtimeAnalysisAllBtn_Id").show();
                                
                                var statPanelId=getScrewPumpRealtimeWellListPanelId().replace("WellList","StatGraph");
                            	Ext.getCmp(statPanelId).collapse();
                            	
                    			Ext.getCmp('ScrewPumpRealtimeAnalysisWellCom_Id').setValue(record.data.jh);
                            	Ext.getCmp('ScrewPumpRealtimeAnalysisWellCom_Id').setRawValue(record.data.jh);
                            	Ext.getCmp('ScrewPumpRTAnalysisWellList_Id').getStore().loadPage(1);
                            	
                    		}
                        }
                    }
                })
            	var panelId=getScrewPumpRealtimeWellListPanelId();
            	Ext.getCmp(panelId).add(gridPanel);
            	
            	var length = gridPanel.dockedItems.keys.length;
                var refreshStr= "";
                for (var i = 0; i < length; i++) {
                   if (gridPanel.dockedItems.keys[i].indexOf("pagingtoolbar") !== -1) {
                      refreshStr= gridPanel.dockedItems.keys[i];
                   }
                }
            	gridPanel.dockedItems.get(refreshStr).child('#refresh').setHandler(   
            	        function() {
            	        	Ext.create("AP.store.diagnosis.ScrewPumpRTAnalysisStatStore");
            	         }
            	)
            	
            }else{
            	gridPanel.reconfigure(newColumns);
            }
            var startDate=Ext.getCmp('ScrewPumpRealtimeAnalysisStartDate_Id').rawValue;
            if(startDate==''||null==startDate){
            	Ext.getCmp("ScrewPumpRealtimeAnalysisStartDate_Id").setValue(get_rawData.start_date==undefined?get_rawData.startDate:get_rawData.start_date);
            }
            if(get_rawData.totalCount>0){
            	var SingleAnalysisGridPanel = Ext.getCmp("ScrewPumpRTAnalysisWellList_Id");
                if (isNotVal(SingleAnalysisGridPanel)) {
                	SingleAnalysisGridPanel.getSelectionModel().deselectAll(true);
                	SingleAnalysisGridPanel.getSelectionModel().select(0, true);
                }
            }else{
            	$("#ScrewPumpRTCurveDataChartDiv_Id").html('');
            	Ext.getCmp("ScrewPumpRTAnalysisTableCalDataPanel_Id").removeAll();
            	Ext.getCmp("ScrewPumpRTAnalysisTableAcqDataPanel_Id").removeAll();
            	Ext.getCmp("ScrewPumpRTAnalysisControlVideoPanel_Id").removeAll();
            	Ext.getCmp("ScrewPumpRTAnalysisControlDataPanel_Id").removeAll();
            	if($("#ScrewPumpRTAnalysisControlVideoDiv_Id")!=null){
            		$("#ScrewPumpRTAnalysisControlVideoDiv_Id").html('');
            	}
            }
            
            
        },
        beforeload: function (store, options) {
            var orgId = Ext.getCmp('leftOrg_Id').getValue();
            var jh = Ext.getCmp('ScrewPumpRealtimeAnalysisWellCom_Id').getValue();
            var statValue = Ext.getCmp('ScrewPumpSelectedStatValue_Id').getValue();
            var startDate=Ext.getCmp('DiagnosisAnalysisStartDate_Id').rawValue;
            var endDate=Ext.getCmp('DiagnosisAnalysisEndDate_Id').rawValue;
            var type=getScrewPumpRTStatType();
            var new_params = {
                orgId: orgId,
                jh: jh,
                statValue:statValue,
                startDate:startDate,
                endDate:endDate,
                type:type,
                wellType:400
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        	onStoreSizeChange(v, o, "DiagnosisPumpingUnit_SingleDinagnosisAnalysisTotalCount_Id");
        }
    }
});