Ext.define('AP.store.reportOut.ComparativeAnalysisDeviceListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.dailyDataComparativeAnalysisDeviceListStore',
    fields: ['id','deviceName'],
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: context + '/reportDataMamagerController/getComparativeAnalysisDeviceList',
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
            var gridPanel = Ext.getCmp("DailyComparativeAnalysisDeviceListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "DailyComparativeAnalysisDeviceListGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: false,
                    selModel:{
                    	selType: 'checkboxmodel',
                    	mode: 'MULTI',//"SINGLE" / "SIMPLE" / "MULTI" 
                    	checkOnly: false,
                    	allowDeselect: false
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
                        text: loginUserLanguageResource.deviceName,
                        lockable: true,
                        align: 'center',
                        flex: 5,
                        sortable: false,
                        locked: false,
                        dataIndex: 'deviceName',
                        renderer: function (value) {
                        	if(isNotVal(value)){
    			        		return Ext.String.format('<span data-qtip="{0}">{0}</span>', Ext.String.htmlEncode(value));
			        		}
                        }
                    }]
                });
                var deviceListPanel = Ext.getCmp("DailyDataComparativeAnalysisDeviceListPanel_Id");
                if(isNotVal(deviceListPanel)){
                	deviceListPanel.add(gridPanel);
                }
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceType=getDeviceTypeFromTabId("AcquisitionDataReportRootTabPanel");
            var new_params = {
                    orgId: orgId,
                    deviceType:deviceType,
                    unitClasses:2
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        }
    }
});