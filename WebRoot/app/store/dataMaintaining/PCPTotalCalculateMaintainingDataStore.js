Ext.define('AP.store.dataMaintaining.PCPTotalCalculateMaintainingDataStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.PCPTotalCalculateMaintainingDataStore',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/calculateManagerController/getTotalCalculateResultData',
        actionMethods: {
            read: 'POST'
        },
        start: 0,
        limit: defaultPageSize,
        reader: {
            type: 'json',
            rootProperty: 'totalRoot',
            totalProperty: 'totalCount',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, options, eOpts) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var gridPanel = Ext.getCmp("PCPTotalCalculateMaintainingDataGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var arrColumns = get_rawData.columns;
                var cloums = createTotalCalculateMaintainingDataColumn(arrColumns);
                var newColumns = Ext.JSON.decode(cloums);
                //分页工具栏
                var bbar = new Ext.PagingToolbar({
                	store: store,
                	displayInfo: true
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "PCPTotalCalculateMaintainingDataGridPanel_Id",
                    border: false,
                    stateful: true,
                    autoScroll: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: false,
                    selModel:{
                    	selType:(loginUserCalculateMaintainingModuleRight.editFlag==1?'checkboxmodel':''),
                    	showHeaderCheckbox:true,
                    	mode:'MULTI'//"SINGLE" / "SIMPLE" / "MULTI" 
                    },
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>",
                        forceFit: false
                    },
                    bbar: bbar,
                    store: store,
                    columns: newColumns,
                    listeners: {
                        selectionchange: function (sm, selected) {
                        	
                        },
                        itemdblclick: function ( grid, record, item, index, e, eOpts) {
                        	
                        }
                    }
                });
                var panel = Ext.getCmp("PCPTotalCalculateMaintainingPanel");
                panel.add(gridPanel);
            }
            gridPanel.getSelectionModel().deselectAll(true);
            
            var startDate=Ext.getCmp('PCPCalculateMaintainingStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(get_rawData.startDate);
            }
            var endDate=Ext.getCmp('PCPCalculateMaintainingEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(get_rawData.endDate);
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceName='';
        	var deviceId=0;
        	var selectRow= Ext.getCmp("PCPCalculateMaintainingDeviceListSelectRow_Id").getValue();
        	if(Ext.getCmp("PCPCalculateMaintainingWellListGridPanel_Id").getSelectionModel().getSelection().length>0){
        		deviceName = Ext.getCmp("PCPCalculateMaintainingWellListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
        		deviceId=Ext.getCmp("PCPCalculateMaintainingWellListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        	}
        	
        	var startDate=Ext.getCmp('PCPCalculateMaintainingStartDate_Id').rawValue;
            var endDate=Ext.getCmp('PCPCalculateMaintainingEndDate_Id').rawValue;
        	
            var deviceType=getDeviceTypeFromTabId("CalculateMaintainingRootTabPanel");
            var dictDeviceType=deviceType;
        	if(dictDeviceType.includes(",")){
        		dictDeviceType=getDeviceTypeFromTabId_first("CalculateMaintainingRootTabPanel");
        	}
            var calculateType=4;//1-抽油机井诊断计产 2-螺杆泵井诊断计产 3-抽油机井汇总计算  4-螺杆泵井汇总计算 5-电参反演地面功图计算
            var new_params = {
            		orgId: orgId,
            		deviceName: deviceName,
            		deviceId:deviceId,
                    startDate:startDate,
                    endDate:endDate,
                    deviceType:deviceType,
                    dictDeviceType:dictDeviceType,
                    calculateType:calculateType
                };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});