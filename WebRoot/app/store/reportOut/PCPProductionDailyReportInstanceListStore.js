Ext.define('AP.store.reportOut.PCPProductionDailyReportInstanceListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.PCPProductionDailyReportInstanceListStore',
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
            var gridPanel = Ext.getCmp("PCPProductionDailyReportGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var column = createPCPProductionDailyReportTemplateListDataColumn(arrColumns);
                var newColumns = Ext.JSON.decode(column);
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "PCPProductionDailyReportGridPanel_Id",
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
                    		Ext.getCmp("PCPProductionDailyReportInstanceListSelectRow_Id").setValue(index);
                    		CreatePCPProductionDailyReportTable();
                    		CreatePCPProductionDailyReportCurve();
                        }
                    }
                });
                var PCPProductionDailyReportInstanceListPanel = Ext.getCmp("PCPProductionDailyReportInstanceListPanel_Id");
                PCPProductionDailyReportInstanceListPanel.add(gridPanel);
            }
            if(get_rawData.totalCount>0){
            	gridPanel.getSelectionModel().deselectAll(true);
            	gridPanel.getSelectionModel().select(0, true);
            }else{
            	Ext.getCmp("PCPProductionDailyReportInstanceListSelectRow_Id").setValue(-1);
            	if(pcpProductionDailyReportHelper!=null){
    				if(pcpProductionDailyReportHelper.hot!=undefined){
    					pcpProductionDailyReportHelper.hot.destroy();
    				}
    				pcpProductionDailyReportHelper=null;
    			}
            	$("#PCPProductionDailyReportDiv_id").html('');
                $("#PCPProductionDailyReportCurveDiv_Id").html('');
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var wellName = Ext.getCmp('PCPProductionDailyReportPanelWellListCombo_Id').getValue();
            var new_params = {
                    orgId: orgId,
                    reportType:1,
                    deviceType:1
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        }
    }
});