Ext.define('AP.store.balanceCycle.TorqueAveragePowerCycleBalanceStatusStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.torqueAveragePowerCycleBalanceStatusStore',
    model: 'AP.model.balanceCycle.CycleWellListModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/balanceCycleController/getBalanceStatusData',
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
            var GridPanel = Ext.getCmp("TorqueAveragePowerCycleWellListGrid_Id");
            if (!isNotVal(GridPanel)) {
                var column = createBalanceMinitorListColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                GridPanel = Ext.create('Ext.grid.Panel', {
                    id: "TorqueAveragePowerCycleWellListGrid_Id",
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
                    			Ext.getCmp("TorqueAveragePowerCycleStartDate_Id").setValue(record[0].data.gtkssj);
                    			Ext.getCmp("TorqueAveragePowerCycleEndDate_Id").setValue(record[0].data.gtjssj);
                    			Ext.getCmp("TorqueAveragePowerCycleTextfield_id").setValue(record[0].data.sczq);
                            	var currentbalance=record[0].data.currentbalance;
                            	var optimizationbalance=record[0].data.optimizationbalance;
                            	TorqueAveragePowerCycleBalanceInfoGrid1=Ext.getCmp("TorqueAveragePowerCycleBalanceInfoGrid1_Id");
                            	if (isNotVal(TorqueAveragePowerCycleBalanceInfoGrid1)) {
                            		TorqueAveragePowerCycleBalanceInfoGrid1.getStore().load();
                            	}else{
                            		Ext.create("AP.store.balanceCycle.TorqueAveragePowerCycleDataStore");
                            	}
                    		}
                    	}
                    }
                });
                var TorqueAveragePowerCycleBalanceStatusPanel = Ext.getCmp("TorqueAveragePowerCycleBalanceStatusPanel_Id");
                TorqueAveragePowerCycleBalanceStatusPanel.add(GridPanel);
            }
            if(get_rawData.totalCount>0){
            	GridPanel.getSelectionModel().select(0,true);//选中第一行
            }else{
            	Ext.getCmp("CycleTorqueAveragePowerBalanceInfoPanel1_Id").removeAll();
            	Ext.getCmp("CycleTorqueAveragePowerBalanceInfoPanel2_Id").removeAll();
            	Ext.get("CycleTorqueAveragePowerBalanceCurve1Div_Id").dom.innerHTML = "";
            	Ext.get("CycleTorqueAveragePowerBalanceCurve2Div_Id").dom.innerHTML = "";
            }
            var selectedJh=Ext.getCmp('TorqueAveragePowerCyclejh_Id').getValue();
            var selectedGkmc=Ext.getCmp('TorqueAveragePowerBalanceCycleSelectedGkmc_Id').getValue();
            if((selectedJh==null||selectedJh==""||selectedJh=="null")&&selectedGkmc==""){
            	var tabPanel = Ext.getCmp("CycleStatTorqueAveragePowerTabpanel_Id");
            	var activeId = tabPanel.getActiveTab().id;
            	if(activeId=="CycleStatTorqueAveragePowerPiePanel_Id"){
            		Ext.create("AP.store.balanceCycle.CycleStatTorqueAveragePowerStore");
            	}
            }
            
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var jh = Ext.getCmp('TorqueAveragePowerCyclejh_Id').getValue();
        	var gkmc=Ext.getCmp('TorqueAveragePowerBalanceCycleSelectedGkmc_Id').getValue();;
        	
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
