Ext.define('AP.store.balanceTotal.TorqueMaxValueTotalAnalysisDataStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.torqueMaxValueTotalAnalysisDataStore',
    model: 'AP.model.balanceTotal.TotalBalanceStatusDataModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/balanceTotalAnalysisController/getTotalAnalysisData',
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
            var gridPanel = Ext.getCmp("TorqueMaxValueTotalAnalysisiDataGrid_Id");
            if (!isNotVal(gridPanel)) {
                var column = createBalanceMinitorListColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "TorqueMaxValueTotalAnalysisiDataGrid_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: false,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	itemclick:function( view , record , item , index , e , eOpts ) {
                    	},
                    	selectionchange:function(grid, record , eOpts) {
                    		if(record.length>0){
                    			Ext.create("AP.store.balanceTotal.TorqueMaxValueTotalGtStore");
                    		}
                    	}
                    }
                });
                var panel = Ext.getCmp("TotalTorqueMaxValueBalaceStatusPanel_Id");
                panel.add(gridPanel);
            }
            gridPanel.getSelectionModel().select(0,true);//选中第一行
            var selectedJh=Ext.getCmp('TorqueMaxValueTotaljh_Id').getValue();
            var selectedGkmc=Ext.getCmp('TorqueMaxValueBalanceTotalSelectedGkmc_Id').getValue();
            if((selectedJh==null||selectedJh==""||selectedJh=="null")&&selectedGkmc==""){
            	var tabPanel = Ext.getCmp("TotalStatTorqueMaxValueTabpanel_Id");
            	var activeId = tabPanel.getActiveTab().id;
            	if(activeId=="TotalStatTorqueMaxValuePiePanel_Id"){
            		Ext.create("AP.store.balanceTotal.TotalStatTorqueMaxValueStore");
            	}
            }
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
//        	var endDate = Ext.getCmp("TorqueMaxValueTotalEndDate_Id").rawValue;
        	var jh = Ext.getCmp('TorqueMaxValueTotaljh_Id').getValue();
        	var gkmc = Ext.getCmp('TorqueMaxValueBalanceTotalSelectedGkmc_Id').getValue();
        	var new_params = {
                    orgId: leftOrg_Id,
                    jh:jh,
                    type:2,
                    gkmc:gkmc
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});