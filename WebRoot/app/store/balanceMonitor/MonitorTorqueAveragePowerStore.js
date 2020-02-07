Ext.define('AP.store.balanceMonitor.MonitorTorqueAveragePowerStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.monitorTorqueAveragePowerStore',
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
            var MonitorTorqueAveragePowerGrid = Ext.getCmp("MonitorTorqueAveragePowerGrid_Id");
            if (!isNotVal(MonitorTorqueAveragePowerGrid)) {
                var column = createBalanceMinitorListColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                MonitorTorqueAveragePowerGrid = Ext.create('Ext.grid.Panel', {
                    id: "MonitorTorqueAveragePowerGrid_Id",
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
                    			var MonitorTorqueAveragePowerAnalysisGrid=Ext.getCmp("MonitorTorqueAveragePowerAnalysisGrid_Id");
                    			if(isNotVal(MonitorTorqueAveragePowerAnalysisGrid)){
                    				MonitorTorqueAveragePowerAnalysisGrid.getStore().load();
                    			}else{
                    				Ext.create("AP.store.balanceMonitor.MonitorTorqueAveragePowerAnalysisStore");
                    			}
                    		}
                    	}
                    }
                });
                var TorqueAveragePowerBalaceStatusPanel = Ext.getCmp("TorqueAveragePowerBalaceStatusPanel_Id");
                TorqueAveragePowerBalaceStatusPanel.add(MonitorTorqueAveragePowerGrid);
            }
            if(get_rawData.totalCount>0){
            	MonitorTorqueAveragePowerGrid.getSelectionModel().select(0,true);//选中第一行
            }else{
            	Ext.getCmp("TorqueAveragePowerMonitorDataPanel_Id").removeAll();
            	Ext.get("MonitorTorqueAveragePowerGtDiv_Id").dom.innerHTML = "";
            	Ext.get("MonitorTorqueAveragePowerTorqueCurve1Div_Id").dom.innerHTML = "";
            	Ext.get("MonitorTorqueAveragePowerTorqueCurve2Div_Id").dom.innerHTML = "";
            	Ext.getCmp("TorqueAveragePowerPRAndTFPanel_Id").removeAll();
            }
            
            var selectedJh=Ext.getCmp('TorqueAveragePowerBalanceMonitorjh_Id').getValue();
            
            var selectedGkmc=Ext.getCmp('TorqueAveragePowerBalanceMonitorSelectedGkmc_Id').getValue();
            
            if((selectedJh==null||selectedJh==""||selectedJh=="null")&&selectedGkmc==""){
            	var tabPanel = Ext.getCmp("MonitorStatTorqueAveragePowerTabpanel_Id");
    			var activeId = tabPanel.getActiveTab().id;
    			if(activeId!="MonitorStatTorqueAveragePowerPanel_Id"){
    				Ext.create("AP.store.balanceMonitor.MonitorStatTorqueAveragePowerStore");
    			}else{
    				var MonitorStatTorqueAveragePowerGrid_Id = Ext.getCmp("MonitorStatTorqueAveragePowerGrid_Id");
    				if(isNotVal(MonitorStatTorqueAveragePowerGrid_Id)){
    					MonitorStatTorqueAveragePowerGrid_Id.getStore().load();
    				}else{
    					Ext.create("AP.store.balanceMonitor.MonitorStatTorqueAveragePowerStore");
    				}
    			}
            }
            
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var jh = Ext.getCmp('TorqueAveragePowerBalanceMonitorjh_Id').getValue();
        	var gkmc=Ext.getCmp('TorqueAveragePowerBalanceMonitorSelectedGkmc_Id').getValue();
        	
        	var new_params = {
                    orgId: leftOrg_Id,
                    jh: jh,
                    type:3,
                    gkmc:gkmc
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});