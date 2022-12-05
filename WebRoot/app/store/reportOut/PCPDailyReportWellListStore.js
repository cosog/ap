Ext.define('AP.store.reportOut.PCPDailyReportWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.PCPDailyReportWellListStore',
    fields: ['id','wellName'],
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/reportDataMamagerController/getWellList',
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
            var gridPanel = Ext.getCmp("PCPDailyReportGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var column = createPCPDailyReportWellListDataColumn(arrColumns);
                var newColumns = Ext.JSON.decode(column);
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "PCPDailyReportGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: true,
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
                    			Ext.getCmp('PCPDailyReportPanelWellListCombo_Id').setValue(selected[0].data.wellName);
                            	Ext.getCmp('PCPDailyReportPanelWellListCombo_Id').setRawValue(selected[0].data.wellName);
                    		}else{
                    			Ext.getCmp('PCPDailyReportPanelWellListCombo_Id').setValue('');
                            	Ext.getCmp('PCPDailyReportPanelWellListCombo_Id').setRawValue('');
                    		}
                    		CreatePCPDailyReportTable();
                    	},
                    	select: function(grid, record, index, eOpts) {
                        }
                    }
                });
                var PCPDailyReportWellListPanel = Ext.getCmp("PCPDailyReportWellListPanel_Id");
                PCPDailyReportWellListPanel.add(gridPanel);
            }
            if(get_rawData.totalCount>0){
            	gridPanel.getSelectionModel().deselectAll(true);
            }
            Ext.getCmp("PCPDailyReportDeviceListSelectRow_Id").setValue(-1);
            CreatePCPDailyReportTable();
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var wellName = Ext.getCmp('PCPDailyReportPanelWellListCombo_Id').getValue();
            var new_params = {
                    orgId: orgId,
                    deviceType:1
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        }
    }
});