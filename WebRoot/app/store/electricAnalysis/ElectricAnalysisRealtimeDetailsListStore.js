Ext.define('AP.store.electricAnalysis.ElectricAnalysisRealtimeDetailsListStore', {
    extend: 'Ext.data.Store',
    fields: ['id','wellName', 'acquisitionTime','commStatusName','runStatusName', 'workingConditionName_Elec','runTime','runTimeEfficiency','runRange',
    	'Ia', 'Ib', 'Ic','IAvg','IStr','Va', 'Vb', 'Vc', 'VAvg','VStr',
    	'WattA','WattB','WattC','WattSum','WattStr',
    	'VarA','VarB','VarC','VarSum','VarStr',
    	'PFA','PFB','PFC','PFSum','PFStr',
    	'totalWattEnergy', 'totalPWattEnergy','totalNWattEnergy', 'totalVarEnergy','totalPVarEnergy', 'totalNVarEnergy','totalVAEnergy',
    	'todayWattEnergy', 'todayPWattEnergy','todayNWattEnergy', 'todayVarEnergy','todayPVarEnergy', 'todayNVarEnergy','todayVAEnergy',
    	'frequencyRunValue','signal','interval','deviceVer'],
    autoLoad: true,
    pageSize: 50,
    proxy: {
    	type: 'ajax',
        url: context + '/PSToFSController/getElectricAnalysisRealtimeDetailsList',
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
            var column = createElecAnalysisRealtimeDetailsTableColumn(arrColumns);
//            Ext.getCmp("DiagnosisAnalysisColumnStr_Id").setValue(column);
            Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(get_rawData.AlarmShowStyle));
            var newColumns = Ext.JSON.decode(column);
            var bbar = new Ext.PagingToolbar({
            	store: store,
            	displayInfo: true,
            	displayMsg: '当前 {0}~{1}条  共 {2} 条'
	        });
            var gridPanel = Ext.getCmp("ElectricAnalysisRealtimeDiscreteDetails_Id");
            if (!isNotVal(gridPanel)) {
            	gridPanel= Ext.create('Ext.grid.Panel', {
                    id: 'ElectricAnalysisRealtimeDiscreteDetails_Id',
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
                            	Ext.create("AP.store.electricAnalysis.ElectricAnalysisRealtimeDiscreteDetailsRightTableStore");
                            }
                            
                        },
                        itemdblclick: function (view,record,item,index,e,eOpts) {
                        	Ext.getCmp("electricAnalysisRealtimeDetailsRTBtn_Id").show();
                        	Ext.getCmp("electricAnalysisRealtimeDetailsHisBtn_Id").hide();
                            Ext.getCmp("electricAnalysisRealtimeDetailsStartDate_Id").show();
                    		Ext.getCmp("electricAnalysisRealtimeDetailsEndDate_Id").show();
                    		
                			Ext.getCmp('electricAnalysisRealtimeDetailsWellCom_Id').setValue(record.data.wellName);
                        	Ext.getCmp('electricAnalysisRealtimeDetailsWellCom_Id').setRawValue(record.data.wellName);
                        	Ext.getCmp('ElectricAnalysisRealtimeDiscreteDetails_Id').getStore().loadPage(1);
                        }
                    }
                })
            	Ext.getCmp("electricAnalysisRealtimeDiscreteDetailsTable_Id").add(gridPanel);
            	
            }else{
            	gridPanel.reconfigure(newColumns);
            }
            var startDate=Ext.getCmp('electricAnalysisRealtimeDetailsStartDate_Id').rawValue;
            if(startDate==''||null==startDate){
            	Ext.getCmp("electricAnalysisRealtimeDetailsStartDate_Id").setValue(get_rawData.start_date==undefined?get_rawData.startDate:get_rawData.start_date);
            }
            if(get_rawData.totalCount>0){
            	gridPanel.getSelectionModel().select(0,true);//选中第一行
            }else{
            	Ext.getCmp("ElectricAnalysisRealtimeDiscreteDetailsRightAnalysisPanel_Id").removeAll();
            	Ext.getCmp("ElectricAnalysisRealtimeDiscreteDetailsRightAcqPanel_Id").removeAll();
            	Ext.getCmp("ElectricAnalysisRealtimeDiscreteDetailsRightRunRangeTextArea_Id").setValue("");
        		Ext.getCmp("ElectricAnalysisRealtimeDiscreteDetailsRightResultCodeTextArea_Id").setValue("");
            }
        },
        beforeload: function (store, options) {
            var orgId = Ext.getCmp('leftOrg_Id').getValue();
            var wellName = Ext.getCmp('electricAnalysisRealtimeDetailsWellCom_Id').getValue();
            var startDate=Ext.getCmp('electricAnalysisRealtimeDetailsStartDate_Id').rawValue;
            var endDate=Ext.getCmp('electricAnalysisRealtimeDetailsEndDate_Id').rawValue;
            var new_params = {
                orgId: orgId,
                wellName: wellName,
                startDate:startDate,
                endDate:endDate,
                wellType:200
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        	onStoreSizeChange(v, o, "DiagnosisPumpingUnit_SingleDinagnosisAnalysisTotalCount_Id");
        }
    }
});