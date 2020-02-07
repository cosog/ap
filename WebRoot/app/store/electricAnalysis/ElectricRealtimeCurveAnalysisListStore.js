Ext.define('AP.store.electricAnalysis.ElectricRealtimeCurveAnalysisListStore', {
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
            var column = createRealtimeElecCurveAnalysisTableColumn(arrColumns);
            var newColumns = Ext.JSON.decode(column);
            var bbar = new Ext.PagingToolbar({
            	store: store,
            	displayInfo: true,
            	displayMsg: '当前 {0}~{1}条  共 {2} 条'
//            	displayMsg: ''
	        });
            var gridPanel = Ext.getCmp("ElectricAnalysisRealtimeCurve_Id");
            if (!isNotVal(gridPanel)) {
            	gridPanel= Ext.create('Ext.grid.Panel', {
                    id: 'ElectricAnalysisRealtimeCurve_Id',
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
                            	var id=selected[0].data.id;
                            	var wellName = Ext.getCmp("electricAnalysisRealtimeCurveJh_Id").getValue();
                            	getAndInitInverDiagram(id,wellName);
                            }
                        },
                        itemdblclick: function (view,record,item,index,e,eOpts) {
                        	Ext.getCmp("electricAnalysisRealtimeCurveRTBtn_Id").show();
                        	Ext.getCmp("electricAnalysisRealtimeCurveHisBtn_Id").hide();
                            Ext.getCmp("electricAnalysisRealtimeCurveStartDate_Id").show();
                    		Ext.getCmp("electricAnalysisRealtimeCurveEndDate_Id").show();
                			Ext.getCmp('electricAnalysisRealtimeCurveJh_Id').setValue(record.data.wellName);
                        	Ext.getCmp('electricAnalysisRealtimeCurveJh_Id').setRawValue(record.data.wellName);
                        	Ext.getCmp('ElectricAnalysisRealtimeCurve_Id').getStore().loadPage(1);
                        }
                    }
                })
            	Ext.getCmp("electricRealtimeCurveAnalysisListTable_Id").add(gridPanel);
            }
            if(get_rawData.totalCount>0){
                if (isNotVal(gridPanel)) {
                	gridPanel.getSelectionModel().deselectAll(true);
                	gridPanel.getSelectionModel().select(0, true);
                }
            }else{
            	$("#electricAnalysisRealtimeCurveInverDiv1_id").html('');
            	$("#electricAnalysisRealtimeCurveInverDiv2_id").html('');
            	$("#electricAnalysisRealtimeCurveInverDiv3_id").html('');
            	$("#electricAnalysisRealtimeCurveInverDiv4_id").html('');
            	$("#electricAnalysisRealtimeCurveInverDiv5_id").html('');
            	$("#electricAnalysisRealtimeCurveInverDiv6_id").html('');
            	$("#electricAnalysisRealtimeCurveInverDiv7_id").html('');
            	$("#electricAnalysisRealtimeCurveInverDiv8_id").html('');
            	$("#electricAnalysisRealtimeCurveInverDiv9_id").html('');
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
            var wellName = Ext.getCmp("electricAnalysisRealtimeCurveJh_Id").getValue();
            var startDate=Ext.getCmp('electricAnalysisRealtimeCurveStartDate_Id').rawValue;
            var endDate=Ext.getCmp('electricAnalysisRealtimeCurveEndDate_Id').rawValue;
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