Ext.define('AP.store.reportOut.RPCDailyReportWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.RPCDailyReportWellListStore',
    fields: ['id','wellName'],
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/reportPumpingUnitDataController/getWellList',
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
            var RPCDailyReportGridPanel = Ext.getCmp("RPCDailyReportGridPanel_Id");
            if (!isNotVal(RPCDailyReportGridPanel)) {
                var column = createRPCDailyReportWellListDataColumn(arrColumns);
                var newColumns = Ext.JSON.decode(column);
                RPCDailyReportGridPanel = Ext.create('Ext.grid.Panel', {
                    id: "RPCDailyReportGridPanel_Id",
                    border: false,
                    autoLoad: true,
                    columnLines: true,
                    forceFit: true,
//                    selType: 'checkboxmodel',
//                    multiSelect: false,
                    selModel:{
                    	selType: 'checkboxmodel',
                    	mode:'SINGLE',//"SINGLE" / "SIMPLE" / "MULTI" 
                    	checkOnly:false,
                    	allowDeselect:true,
                    	onHdMouseDown:function(e,t){
                    		alert("全选/全不选");
                    	}
                    },
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	selectionchange: function (view, selected, o) {
                    		if(selected.length>0){
                    			Ext.getCmp('ReportPumpingUnitPanelWellListCombo_Id').setValue(selected[0].data.wellName);
                            	Ext.getCmp('ReportPumpingUnitPanelWellListCombo_Id').setRawValue(selected[0].data.wellName);
                    		}else{
                    			Ext.getCmp('ReportPumpingUnitPanelWellListCombo_Id').setValue('');
                            	Ext.getCmp('ReportPumpingUnitPanelWellListCombo_Id').setRawValue('');
//                            	var calculateEndDate = Ext.getCmp('ReportPumpingUnitPanelCalculateEndDate_Id').rawValue;
//                            	Ext.getCmp('ReportPumpingUnitPanelCalculateDate_Id').setValue(calculateEndDate);
//                            	Ext.getCmp('ReportPumpingUnitPanelCalculateDate_Id').setRawValue(calculateEndDate);
                    		}
                    		CreateDiagnosisDailyReportTable();
                    	},
                    	select: function(grid, record, index, eOpts) {
//                        	Ext.getCmp('KafkaConfigWellListSelectRow_Id').setValue(index);
                        }
                    }
                });
                var RPCDailyReportWellListPanel = Ext.getCmp("RPCDailyReportWellListPanel_Id");
                RPCDailyReportWellListPanel.add(RPCDailyReportGridPanel);
            }
            if(get_rawData.totalCount>0){
            	RPCDailyReportGridPanel.getSelectionModel().deselectAll(true);
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
            var new_params = {
                    orgId: orgId,
                    wellType:200
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});