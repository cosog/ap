Ext.define('AP.store.reportOut.ProductionDailyReportInstanceListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.ProductionDailyReportInstanceListStore',
    fields: ['id','wellName'],
    autoLoad: true,
    pageSize: defaultPageSize,
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
            var gridPanel = Ext.getCmp("ProductionDailyReportGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var column = createProductionDailyReportTemplateListDataColumn(arrColumns);
                var newColumns = Ext.JSON.decode(column);
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "ProductionDailyReportGridPanel_Id",
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
                    		Ext.getCmp("ProductionDailyReportInstanceListSelectRow_Id").setValue(index);
                    		CreateProductionDailyReportTable();
                    		CreateProductionDailyReportCurve();
                        }
                    }
                });
                var ProductionDailyReportInstanceListPanel = Ext.getCmp("ProductionDailyReportInstanceListPanel_Id");
                ProductionDailyReportInstanceListPanel.add(gridPanel);
            }
            if(get_rawData.totalCount>0){
            	gridPanel.getSelectionModel().deselectAll(true);
            	gridPanel.getSelectionModel().select(0, true);
            	if(get_rawData.totalCount==1){
            		Ext.getCmp("ProductionDailyReportInstanceListPanel_Id").hide();
            	}else{
            		Ext.getCmp("ProductionDailyReportInstanceListPanel_Id").show();
            	}
            }else{
            	Ext.getCmp("ProductionDailyReportInstanceListSelectRow_Id").setValue(-1);
            	if(productionDailyReportHelper!=null){
        			if(productionDailyReportHelper.hot!=undefined){
        				productionDailyReportHelper.hot.destroy();
        			}
        			productionDailyReportHelper=null;
        		}
            	$("#ProductionDailyReportDiv_id").html('');
                $("#ProductionDailyReportCurveDiv_Id").html('');
            }
        },
        beforeload: function (store, options) {
        	Ext.getCmp("ProductionDailyReportInstanceListPanel_Id").show();
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var wellName = Ext.getCmp('ProductionDailyReportPanelWellListCombo_Id').getValue();
        	var deviceType=getDeviceTypeFromTabId("ProductionReportRootTabPanel");
            var new_params = {
                    orgId: orgId,
                    reportType:1,
                    deviceType:deviceType
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        }
    }
});