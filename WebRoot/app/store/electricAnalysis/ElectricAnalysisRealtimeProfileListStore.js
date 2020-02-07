Ext.define('AP.store.electricAnalysis.ElectricAnalysisRealtimeProfileListStore', {
    extend: 'Ext.data.Store',
    fields: ['id','item', 'count'],
    autoLoad: true,
    pageSize: 50,
    proxy: {
    	type: 'ajax',
        url: context + '/PSToFSController/getElectricAnalysisRealtimeProfilePieData',
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
            var column = createElecAnalysisRealtimeProfileTableColumn(arrColumns);
            var newColumns = Ext.JSON.decode(column);
            var gridPanel = Ext.getCmp("electricAanlysisRealtimeProfileTablePanel_Id");
            if (!isNotVal(gridPanel)) {
            	gridPanel= Ext.create('Ext.grid.Panel', {
                    id: 'electricAanlysisRealtimeProfileTablePanel_Id',
                    border: false,
    				columnLines: true,
    				forceFit: true,
    				store: store,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    columns:newColumns,
                    listeners: {
                        selectionchange: function (view, selected, o) {
                            if (selected.length > 0) {}
                        },
                        itemdblclick: function (view,record,item,index,e,eOpts) {
                        }
                    }
                });
            	var tabPanel = Ext.getCmp("electricAanlysisRealtimeProfileTabPanel_Id");
            	var panelId = tabPanel.getActiveTab().id;
            	Ext.getCmp(panelId).add(gridPanel);
            }else{
//            	gridPanel.reconfigure(newColumns);
            }
        },
        beforeload: function (store, options) {
            var orgId = Ext.getCmp('leftOrg_Id').getValue();
            var type = getElectricAnalysisRealtimeProfileType();
            var new_params = {
            	orgId:orgId,
        		type:type
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        	onStoreSizeChange(v, o, "DiagnosisPumpingUnit_SingleDinagnosisAnalysisTotalCount_Id");
        }
    }
});