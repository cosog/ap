Ext.define('AP.store.balanceTotal.TorqueMeanSquareRootTotalAnalysisDataStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.torqueMeanSquareRootTotalAnalysisDataStore',
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
            var gridPanel = Ext.getCmp("TorqueMeanSquareRootTotalAnalysisiDataGrid_Id");
            if (!isNotVal(gridPanel)) {
                var column = createBalanceMinitorListColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "TorqueMeanSquareRootTotalAnalysisiDataGrid_Id",
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
                    			Ext.create("AP.store.balanceTotal.TorqueMeanSquareRootTotalGtStore");
                    		}
                    	}
                    }
                });
                var panel = Ext.getCmp("TotalTorqueMeanSquareRootBalaceStatusPanel_Id");
                panel.add(gridPanel);
            }
            gridPanel.getSelectionModel().select(0,true);//选中第一行
            var selectedJh=Ext.getCmp('TorqueMeanSquareRootTotaljh_Id').getValue();
            var selectedGkmc=Ext.getCmp('TorqueMeanSquareRootBalanceTotalSelectedGkmc_Id').getValue();
            if((selectedJh==null||selectedJh==""||selectedJh=="null")&&selectedGkmc==""){
            	var tabPanel = Ext.getCmp("TotalStatTorqueMeanSquareRootTabpanel_Id");
            	var activeId = tabPanel.getActiveTab().id;
            	if(activeId=="TotalStatTorqueMeanSquareRootPiePanel_Id"){
            		Ext.create("AP.store.balanceTotal.TotalStatTorqueMeanSquareRootStore");
            	}
            }
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var jh = Ext.getCmp('TorqueMeanSquareRootTotaljh_Id').getValue();
        	var gkmc = Ext.getCmp('TorqueMeanSquareRootBalanceTotalSelectedGkmc_Id').getValue();
        	var new_params = {
                    orgId: leftOrg_Id,
                    jh:jh,
                    type:1,
                    gkmc:gkmc
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        	
        }
    }
});