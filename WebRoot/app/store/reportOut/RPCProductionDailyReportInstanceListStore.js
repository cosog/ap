Ext.define('AP.store.reportOut.RPCProductionDailyReportInstanceListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.RPCProductionDailyReportInstanceListStore',
    fields: ['id','wellName'],
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/reportDataMamagerController/getReportInstanceList',
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
            var gridPanel = Ext.getCmp("RPCProductionDailyReportGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var column = createRPCProductionDailyReportTemplateListDataColumn(arrColumns);
                var newColumns = Ext.JSON.decode(column);
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "RPCProductionDailyReportGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: true,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	selectionchange: function (view, selected, o) {
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("RPCProductionDailyReportInstanceListSelectRow_Id").setValue(index);
                    		CreateRPCProductionDailyReportTable();
//                    		CreateRPCProductionDailyReportCurve();
                        }
                    }
                });
                var RPCProductionDailyReportInstanceListPanel = Ext.getCmp("RPCProductionDailyReportInstanceListPanel_Id");
                RPCProductionDailyReportInstanceListPanel.add(gridPanel);
            }
            if(get_rawData.totalCount>0){
            	gridPanel.getSelectionModel().deselectAll(true);
            	gridPanel.getSelectionModel().select(0, true);
            }else{
            	Ext.getCmp("RPCProductionDailyReportInstanceListSelectRow_Id").setValue(-1);
            	if(rpcProductionDailyReportHelper!=null){
    				if(rpcProductionDailyReportHelper.hot!=undefined){
    					rpcProductionDailyReportHelper.hot.destroy();
    				}
    				rpcProductionDailyReportHelper=null;
    			}
            	$("#RPCProductionDailyReportDiv_id").html('');
                $("#RPCProductionDailyReportCurveDiv_Id").html('');
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var wellName = Ext.getCmp('RPCProductionDailyReportPanelWellListCombo_Id').getValue();
            var new_params = {
                    orgId: orgId,
                    reportType:1,
                    deviceType:0
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        }
    }
});