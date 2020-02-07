Ext.define('AP.store.balanceMonitor.MonitorTorqueMeanSquareRootStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.monitorTorqueMeanSquareRootStore',
    model: 'AP.model.balanceMonitor.BalanceMonitorModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/balanceMonitorController/getMonitorBalaceStatusList',
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
            var MonitorTorqueMeanSquareRootGrid = Ext.getCmp("MonitorTorqueMeanSquareRootGrid_Id");
            if (!isNotVal(MonitorTorqueMeanSquareRootGrid)) {
                var column = createBalanceMinitorListColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                MonitorTorqueMeanSquareRootGrid = Ext.create('Ext.grid.Panel', {
                    id: "MonitorTorqueMeanSquareRootGrid_Id",
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
                    			var MonitorTorqueMeanSquareRootAnalysisGrid=Ext.getCmp("MonitorTorqueMeanSquareRootAnalysisGrid_Id");
                    			if(isNotVal(MonitorTorqueMeanSquareRootAnalysisGrid)){
                    				MonitorTorqueMeanSquareRootAnalysisGrid.getStore().load();
                    			}else{
                    				Ext.create("AP.store.balanceMonitor.MonitorTorqueMeanSquareRootAnalysisStore");
                    			}
                    		}
                    	}
                    }
                });
                var TorqueMeanSquareRootBalaceStatusPanel = Ext.getCmp("TorqueMeanSquareRootBalaceStatusPanel_Id");
                TorqueMeanSquareRootBalaceStatusPanel.add(MonitorTorqueMeanSquareRootGrid);
            }
            if(get_rawData.totalCount>0){
            	MonitorTorqueMeanSquareRootGrid.getSelectionModel().select(0,true);//选中第一行
            }else{
            	Ext.getCmp("TorqueMeanSquareRootMonitorDataPanel_Id").removeAll();
            	Ext.get("MonitorTorqueMeanSquareRootGtDiv_Id").dom.innerHTML = "";
            	Ext.get("MonitorTorqueMeanSquareRootTorqueCurve1Div_Id").dom.innerHTML = "";
            	Ext.get("MonitorTorqueMeanSquareRootTorqueCurve2Div_Id").dom.innerHTML = "";
            	Ext.getCmp("TorqueMeanSquareRootPRAndTFPanel_Id").removeAll();
            }
            
            var selectedJh=Ext.getCmp('TorqueMeanSquareRootBalanceMonitorjh_Id').getValue();
            
            var selectedGkmc=Ext.getCmp('TorqueMeanSquareRootBalanceMonitorSelectedGkmc_Id').getValue();
            
            if((selectedJh==null||selectedJh==""||selectedJh=="null")&&selectedGkmc==""){
            	var tabPanel = Ext.getCmp("MonitorStatTorqueMeanSquareRootTabpanel_Id");
    			var activeId = tabPanel.getActiveTab().id;
    			if(activeId!="MonitorStatTorqueMeanSquareRootPanel_Id"){
    				Ext.create("AP.store.balanceMonitor.MonitorStatTorqueMeanSquareRootStore");
    			}else{
    				var MonitorStatTorqueMeanSquareRootGrid_Id = Ext.getCmp("MonitorStatTorqueMeanSquareRootGrid_Id");
    				if(isNotVal(MonitorStatTorqueMeanSquareRootGrid_Id)){
    					MonitorStatTorqueMeanSquareRootGrid_Id.getStore().load();
    				}else{
    					Ext.create("AP.store.balanceMonitor.MonitorStatTorqueMeanSquareRootStore");
    				}
    			}
            }
            
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var jh = Ext.getCmp('TorqueMeanSquareRootBalanceMonitorjh_Id').getValue();
        	var gkmc=Ext.getCmp('TorqueMeanSquareRootBalanceMonitorSelectedGkmc_Id').getValue();
        	
        	var new_params = {
                    orgId: leftOrg_Id,
                    jh: jh,
                    type:1,
                    gkmc:gkmc
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});