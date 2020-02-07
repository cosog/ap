Ext.define('AP.store.balanceMonitor.MonitorTorqueMaxValueStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.monitorTorqueMaxValueStore',
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
            var MonitorTorqueMaxValueGrid = Ext.getCmp("MonitorTorqueMaxValueGrid_Id");
            if (!isNotVal(MonitorTorqueMaxValueGrid)) {
                var column = createBalanceMinitorListColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                MonitorTorqueMaxValueGrid = Ext.create('Ext.grid.Panel', {
                    id: "MonitorTorqueMaxValueGrid_Id",
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
//                    		global.wellMap.locateWell(record.data.jh);
                    	},
                    	selectionchange:function(grid, record , eOpts) {
                    		if(record.length>0){
                    			var MonitorTorqueMaxValueAnalysisGrid=Ext.getCmp("MonitorTorqueMaxValueAnalysisGrid_Id");
                    			if(isNotVal(MonitorTorqueMaxValueAnalysisGrid)){
                    				MonitorTorqueMaxValueAnalysisGrid.getStore().load();
                    			}else{
                    				Ext.create("AP.store.balanceMonitor.MonitorTorqueMaxValueAnalysisStore");
                    			}
                    		}
                    	}
                    }
                });
                var TorqueMaxValueBalaceStatusPanel = Ext.getCmp("TorqueMaxValueBalaceStatusPanel_Id");
                TorqueMaxValueBalaceStatusPanel.add(MonitorTorqueMaxValueGrid);
            }
            if(get_rawData.totalCount>0){
            	MonitorTorqueMaxValueGrid.getSelectionModel().select(0,true);//选中第一行
            }else{
            	Ext.getCmp("TorqueMaxValueMonitorDataPanel_Id").removeAll();
            	Ext.get("MonitorTorqueMaxValueGtDiv_Id").dom.innerHTML = "";
            	Ext.get("MonitorTorqueMaxValueTorqueCurve1Div_Id").dom.innerHTML = "";
            	Ext.get("MonitorTorqueMaxValueTorqueCurve2Div_Id").dom.innerHTML = "";
            	Ext.getCmp("TorqueMaxValuePRAndTFPanel_Id").removeAll();
//            	Ext.get("MonitorTorqueMaxValueMotionCurveDiv_Id").dom.innerHTML = "";
            }
            
            var selectedJh=Ext.getCmp('TorqueMaxValueBalanceMonitorjh_Id').getValue();
            
            var selectedGkmc=Ext.getCmp('TorqueMaxValueBalanceMonitorSelectedGkmc_Id').getValue();
            
            if((selectedJh==null||selectedJh==""||selectedJh=="null")&&selectedGkmc==""){
            	var tabPanel = Ext.getCmp("MonitorStatTorqueMaxValueTabpanel_Id");
    			var activeId = tabPanel.getActiveTab().id;
    			if(activeId!="MonitorStatTorqueMaxValuePanel_Id"){
    				Ext.create("AP.store.balanceMonitor.MonitorStatTorqueMaxValueStore");
    			}else{
    				var MonitorStatTorqueMaxValueGrid_Id = Ext.getCmp("MonitorStatTorqueMaxValueGrid_Id");
    				if(isNotVal(MonitorStatTorqueMaxValueGrid_Id)){
    					MonitorStatTorqueMaxValueGrid_Id.getStore().load();
    				}else{
    					Ext.create("AP.store.balanceMonitor.MonitorStatTorqueMaxValueStore");
    				}
    			}
            }
            
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var jh = Ext.getCmp('TorqueMaxValueBalanceMonitorjh_Id').getValue();
        	var gkmc=Ext.getCmp('TorqueMaxValueBalanceMonitorSelectedGkmc_Id').getValue();
        	var new_params = {
                    orgId: leftOrg_Id,
                    jh: jh,
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