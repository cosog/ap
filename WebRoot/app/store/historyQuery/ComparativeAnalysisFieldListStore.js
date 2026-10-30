Ext.define('AP.store.historyQuery.ComparativeAnalysisFieldListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.comparativeAnalysisFieldListStore',
    fields: ['id','itemName'],
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: context + '/historyQueryController/getComparativeAnalysisItemList',
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
            var gridPanel = Ext.getCmp("ComparativeAnalysisItemListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "ComparativeAnalysisItemListGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: false,
                    selModel:{
                    	selType: 'checkboxmodel',
                    	mode: 'SINGLE',//"SINGLE" / "SIMPLE" / "MULTI" 
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
                        text: loginUserLanguageResource.dataColumn,
                        lockable: true,
                        align: 'center',
                        flex: 2,
                        sortable: false,
                        locked: false,
                        dataIndex: 'itemName',
                        renderer: function (value) {
                        	if(isNotVal(value)){
    			        		return Ext.String.format('<span data-qtip="{0}">{0}</span>', Ext.String.htmlEncode(value));
			        		}
                        }
                    }, {
                        header: loginUserLanguageResource.unit,
                        align: 'center',
                        dataIndex: 'itemUnit',
                        width: 80
                    }, {
                        header: loginUserLanguageResource.columnDataSource,
                        align: 'center',
                        dataIndex: 'dataSourceName',
                        flex: 1
                    }]
                });
                var deviceListPanel = Ext.getCmp("HistoryDataComparativeAnalysisFieldListPanel_Id");
                if(isNotVal(deviceListPanel)){
                	deviceListPanel.add(gridPanel);
                }
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
            var new_params = {
                    orgId: orgId,
                    deviceType:deviceType
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        }
    }
});