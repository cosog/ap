Ext.define('AP.store.well.BatchAddDeviceApplicationScenariosListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.batchAddDeviceApplicationScenariosListStore',
    fields: ['id','wellName'],
    autoLoad: true,
    pageSize: 50,
    proxy: {
        type: 'ajax',
        url: context + '/wellInformationManagerController/getApplicationScenariosList',
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
            var gridPanel = Ext.getCmp("BatchAddDeviceApplicationScenariosListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "BatchAddDeviceApplicationScenariosListGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: false,
//                    selType: 'checkboxmodel',
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: [{
                        text: '序号',
                        lockable: true,
                        align: 'center',
                        width: 50,
                        xtype: 'rownumberer',
                        sortable: false,
                        locked: false
                    }, {
                        text: '应用场景',
                        lockable: true,
                        align: 'center',
                        flex: 10,
                        sortable: false,
                        locked: false,
                        dataIndex: 'applicationScenariosName',
                        renderer: function (value) {
                        	if(isNotVal(value)){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
			        		}
                        }
                    }],
                    listeners: {
                    	select: function(grid, record, index, eOpts) {
//                    		alert(record.data.applicationScenarios+":"+record.data.applicationScenariosName);
                    		CreateAndLoadBatchAddDeviceTable(true);
                    		
//                    		 if (batchAddDeviceHandsontableHelper == null || batchAddDeviceHandsontableHelper.hot == null || batchAddDeviceHandsontableHelper.hot == undefined) {
//                    			 CreateAndLoadBatchAddDeviceTable();
//                    		 }else{
//                    			 if(record.data.applicationScenarios==0){
//                    				 const plugin = batchAddDeviceHandsontableHelper.hot.getPlugin('hiddenColumns');
//                    				 plugin.hideColumns([15,18,25]);
//                    				 
//                    				 batchAddDeviceHandsontableHelper.hot.setDataAtCell(-1,19,'煤层中部深度(m)');
//                    				 batchAddDeviceHandsontableHelper.hot.setDataAtCell(-1,20,'煤层中部温度(℃)');
//                    				 
//                    				 batchAddDeviceHandsontableHelper.hot.render();
//                    			 }else{
//                    				 const plugin = batchAddDeviceHandsontableHelper.hot.getPlugin('hiddenColumns');
//                    				 plugin.showColumns([15,18,25]);
//                    				 
//                    				 batchAddDeviceHandsontableHelper.hot.setDataAtCell(-1,19,'油层中部深度(m)');
//                    				 batchAddDeviceHandsontableHelper.hot.setDataAtCell(-1,20,'油层中部温度(℃)');
//                    				 
//                    				 batchAddDeviceHandsontableHelper.hot.render();
//                    			 }
//                    		 }
                    	}
                    }
                });
                var panel = Ext.getCmp("BatchAddDeviceApplicationScenariosInfoPanel_Id");
                panel.add(gridPanel);
            }
            gridPanel.getSelectionModel().select(0, true);
        },
        beforeload: function (store, options) {
        	
        },
        datachanged: function (v, o) {
        }
    }
});