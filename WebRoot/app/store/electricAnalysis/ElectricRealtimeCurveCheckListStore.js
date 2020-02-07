Ext.define('AP.store.electricAnalysis.ElectricRealtimeCurveCheckListStore', {
    extend: 'Ext.data.Store',
    fields: ['id','wellName','acquisitionTime'],
    autoLoad: true,
    pageSize: 50,
    proxy: {
    	type: 'ajax',
        url: context + '/PSToFSController/getDiagramDataList',
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
            var column = createRealtimeElecCurveAnalysisCheckTableColumn(arrColumns);
            var newColumns = Ext.JSON.decode(column);
            var bbar = new Ext.PagingToolbar({
            	store: store,
            	displayInfo: true,
            	displayMsg: '当前 {0}~{1}条  共 {2} 条'
//            	displayMsg: ''
	        });
            var gridPanel = Ext.getCmp("ElectricAnalysisRealtimeCurveCheck_Id");
            if (!isNotVal(gridPanel)) {
            	gridPanel= Ext.create('Ext.grid.Panel', {
                    id: 'ElectricAnalysisRealtimeCurveCheck_Id',
                    bbar: bbar,
                    border: false,
                    stateful: true,
                    autoScroll: true,
                    columnLines: true,
//                    layout: "fit",
                    stripeRows: true,
//                    forceFit: true,
                    selType: 'checkboxmodel',
                    multiSelect: true,
                    store:store,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    columns:newColumns,
                    listeners: {
                        selectionchange: function (view, selected, o) {
                            if (selected.length == 1) {
                            	var recordId=selected[0].data.id;
                            	var wellName = Ext.getCmp("electricAnalysisRealtimeCurveCheckWellCom_Id").getValue();
                            	getAndInitInverDiagramCheck(recordId,wellName);
                            }
                        },
                        itemdblclick: function (view,record,item,index,e,eOpts) {
                        	Ext.getCmp("electricAnalysisRealtimeCurveCheckRTBtn_Id").show();
                        	Ext.getCmp("electricAnalysisRealtimeCurveCheckHisBtn_Id").hide();
                            Ext.getCmp("electricAnalysisRealtimeCurveCheckStartDate_Id").show();
                    		Ext.getCmp("electricAnalysisRealtimeCurveCheckEndDate_Id").show();
                			Ext.getCmp('electricAnalysisRealtimeCurveCheckWellCom_Id').setValue(record.data.wellName);
                        	Ext.getCmp('electricAnalysisRealtimeCurveCheckWellCom_Id').setRawValue(record.data.wellName);
                        	Ext.getCmp('ElectricAnalysisRealtimeCurveCheck_Id').getStore().loadPage(1);
                        }
                    }
                })
            	Ext.getCmp("electricAnalysisRealtimeCurveCheckTablePanel_Id").add(gridPanel);
            }
            if(get_rawData.totalCount>0){
                if (isNotVal(gridPanel)) {
                	gridPanel.getSelectionModel().deselectAll(true);
                	gridPanel.getSelectionModel().select(0, true);
                }
            }else{
            	$("#electricAnalysisRealtimeCurveCheckInverDiv1_id").html('');
            	$("#electricAnalysisRealtimeCurveCheckInverDiv2_id").html('');
            	$("#electricAnalysisRealtimeCurveCheckInverDiv3_id").html('');
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
            var wellName = Ext.getCmp("electricAnalysisRealtimeCurveCheckWellCom_Id").getValue();
            var startDate=Ext.getCmp('electricAnalysisRealtimeCurveCheckStartDate_Id').rawValue;
            var endDate=Ext.getCmp('electricAnalysisRealtimeCurveCheckEndDate_Id').rawValue;
            var new_params = {
            	orgId: orgId,
            	wellName: wellName,
                startDate:startDate,
                endDate:endDate,
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});