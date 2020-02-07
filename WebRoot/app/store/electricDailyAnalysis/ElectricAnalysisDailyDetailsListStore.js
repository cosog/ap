Ext.define('AP.store.electricDailyAnalysis.ElectricAnalysisDailyDetailsListStore', {
    extend: 'Ext.data.Store',
    autoLoad: true,
    pageSize: 50,
    proxy: {
    	type: 'ajax',
        url: context + '/PSToFSController/getElectricAnalysisDailyDatailsList',
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
            var column = createElecAnalysisDailyDetailsTableColumn(arrColumns);
            Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(get_rawData.AlarmShowStyle));
            var newColumns = Ext.JSON.decode(column);
            var bbar = new Ext.PagingToolbar({
            	store: store,
            	displayInfo: true,
            	displayMsg: '当前 {0}~{1}条  共 {2} 条'
	        });
            var gridPanel = Ext.getCmp("ElectricAnalysisDailyDetails_Id");
            if (!isNotVal(gridPanel)) {
            	gridPanel= Ext.create('Ext.grid.Panel', {
                    id: 'ElectricAnalysisDailyDetails_Id',
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
                        selectionchange: function (grid, record , eOpts) {
                        	if(record.length>0){
                    			var ElectricAnalysisDailyFSdiagramOverlayGrid = Ext.getCmp("ElectricAnalysisDailyFSdiagramOverlayGrid_Id");
                    			if(isNotVal(ElectricAnalysisDailyFSdiagramOverlayGrid)){
                    				ElectricAnalysisDailyFSdiagramOverlayGrid.getStore().load();
                    			}else{
                    				Ext.create("AP.store.electricDailyAnalysis.ElectricAnalysisDailyFSDiagramOverlayStore");
                    			}
                    			Ext.create("AP.store.electricDailyAnalysis.ElectricAnalysisDailyDetailsRightTableStore");
                    		}
                            
                        },
                        itemdblclick: function (view,record,item,index,e,eOpts) {
                        	Ext.getCmp("ElectricAnalysisDailyDetailsAllBtn_Id").show();
                    		Ext.getCmp("ElectricAnalysisDailyDetailsHisBtn_Id").hide();
                    		Ext.getCmp("ElectricAnalysisDailyDetailsDate_Id").hide();
                    		Ext.getCmp("ElectricAnalysisDailyDetailsStartDate_Id").show();
                    		Ext.getCmp("ElectricAnalysisDailyDetailsEndDate_Id").show();
                    		
                			Ext.getCmp('electricAnalysisDailyDetailsWellCom_Id').setValue(record.data.wellName);
                        	Ext.getCmp('electricAnalysisDailyDetailsWellCom_Id').setRawValue(record.data.wellName);
                        	
                        	Ext.getCmp('ElectricAnalysisDailyDetails_Id').getStore().loadPage(1);
                        }
                    }
                })
            	Ext.getCmp("ElectricAnalysisDailyDetailsDataListPanel_Id").add(gridPanel);
            	
            }else{
            }
            var startDate=Ext.getCmp('ElectricAnalysisDailyDetailsStartDate_Id').rawValue;
            if(startDate==''||null==startDate){
            	Ext.getCmp("ElectricAnalysisDailyDetailsStartDate_Id").setValue(get_rawData.start_date==undefined?get_rawData.startDate:get_rawData.start_date);
            }
            if(get_rawData.totalCount>0){
            	gridPanel.getSelectionModel().select(0,true);//选中第一行
            }else{
            	$("#ElectricAnalysisDailyFSDiagramOverlayDiv_Id").html('');
            	$("#ElectricAnalysisDailyPSDiagramOverlayDiv_Id").html('');
            	$("#ElectricAnalysisDailyASDiagramOverlayDiv_Id").html('');
            	Ext.getCmp("ElectricAnalysisDailyFSDiagramOverlayTable_Id").removeAll();
            	Ext.getCmp("ElectricAnalysisDailyDetailsRightAnalysisPanel_Id").removeAll();
            	Ext.getCmp("ElectricAnalysisDailyDetailsRightAcqPanel_Id").removeAll();
            }
        },
        beforeload: function (store, options) {
            var orgId = Ext.getCmp('leftOrg_Id').getValue();
            var wellName = Ext.getCmp('electricAnalysisDailyDetailsWellCom_Id').getValue();
            var date=Ext.getCmp('ElectricAnalysisDailyDetailsDate_Id').rawValue;
            var startDate=Ext.getCmp('ElectricAnalysisDailyDetailsStartDate_Id').rawValue;
            var endDate=Ext.getCmp('ElectricAnalysisDailyDetailsEndDate_Id').rawValue;
            
            var new_params = {
                orgId: orgId,
                wellName: wellName,
                date:date,
                startDate:startDate,
                endDate:endDate,
                wellType:200
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
//        	onStoreSizeChange(v, o, "DiagnosisPumpingUnit_SingleDinagnosisAnalysisTotalCount_Id");
        }
    }
});