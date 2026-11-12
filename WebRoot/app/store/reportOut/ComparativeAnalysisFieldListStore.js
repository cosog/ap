Ext.define('AP.store.reportOut.ComparativeAnalysisFieldListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.dailyDataomparativeAnalysisFieldListStore',
    fields: ['id', 'itemName', 'headerName', 'sort'],
    autoLoad: true,
    proxy: {
        type: 'ajax',
//        url: context + '/reportDataMamagerController/getComparativeAnalysisItemList',
        url: context + '/acquisitionUnitManagerController/getComparativeAnalysisItemList',
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
            var gridPanel = Ext.getCmp("DailyComparativeAnalysisItemListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "DailyComparativeAnalysisItemListGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: false,
                    selModel:{
                    	selType: 'checkboxmodel',
                    	mode: 'MULTI',//"SINGLE" / "SIMPLE" / "MULTI" 
                    	checkOnly: false,
                    	allowDeselect: true
                    },
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'>" + Ext.String.htmlEncode("<" + loginUserLanguageResource.emptyMsg + ">") + "</div>"
                    },
                    store: store,
                    columns: [{
                        text: loginUserLanguageResource.idx,
                        lockable: true,
                        align: 'center',
                        width: 50,
                        xtype: 'rownumberer',
                        sortable: false,
                        locked: false
                    }, {
                        text: loginUserLanguageResource.dataColumn,
                        lockable: true,
                        align: 'center',
                        flex: 2,
                        sortable: false,
                        locked: false,
                        dataIndex: 'headerName',
                        renderer: function (value) {
                        	if(isNotVal(value)){
    			        		return Ext.String.format('<span data-qtip="{0}">{0}</span>', Ext.String.htmlEncode(value));
			        		}
                        }
                    }]
                });
                var deviceListPanel = Ext.getCmp("DailyDataComparativeAnalysisFieldListPanel_Id");
                if(isNotVal(deviceListPanel)){
                	deviceListPanel.add(gridPanel);
                }
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceType=getDeviceTypeFromTabId("AcquisitionDataReportRootTabPanel");
            var new_params = {
            		calculateType: 0,
                    reportType: 0,
                    unitId: 0,
                    templateCode: 'singleWellRangeReport_dachengCBM',
                    classes: 1,
                    unitClasses:2
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        }
    }
});