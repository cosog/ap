Ext.define('AP.store.reportOut.PPCDailyReportWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.PPCDailyReportWellListStore',
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
            var PPCDailyReportGridPanel = Ext.getCmp("PPCDailyReportGridPanel_Id");
            if (!isNotVal(PPCDailyReportGridPanel)) {
                var column = createRPCDailyReportWellListDataColumn(arrColumns);
                var newColumns = Ext.JSON.decode(column);
                PPCDailyReportGridPanel = Ext.create('Ext.grid.Panel', {
                    id: "PPCDailyReportGridPanel_Id",
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
                    			Ext.getCmp('ReportScrewPumpPanelWellListCombo_Id').setValue(selected[0].data.wellName);
                            	Ext.getCmp('ReportScrewPumpPanelWellListCombo_Id').setRawValue(selected[0].data.wellName);
                    		}else{
                    			Ext.getCmp('ReportScrewPumpPanelWellListCombo_Id').setValue('');
                            	Ext.getCmp('ReportScrewPumpPanelWellListCombo_Id').setRawValue('');
//                            	var calculateEndDate = Ext.getCmp('ReportPumpingUnitPanelCalculateEndDate_Id').rawValue;
//                            	Ext.getCmp('ReportPumpingUnitPanelCalculateDate_Id').setValue(calculateEndDate);
//                            	Ext.getCmp('ReportPumpingUnitPanelCalculateDate_Id').setRawValue(calculateEndDate);
                    		}
                    		CreateScrewPumpDailyReportTable();
                    	},
                    	select: function(grid, record, index, eOpts) {
//                        	Ext.getCmp('KafkaConfigWellListSelectRow_Id').setValue(index);
                        }
                    }
                });
                var PPCDailyReportWellListPanel = Ext.getCmp("PPCDailyReportWellListPanel_Id");
                PPCDailyReportWellListPanel.add(PPCDailyReportGridPanel);
            }
            if(get_rawData.totalCount>0){
            	PPCDailyReportGridPanel.getSelectionModel().deselectAll(true);
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
            var new_params = {
                    orgId: orgId,
                    wellType:400
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});