Ext.define('AP.store.acquisitionUnit.AcquisitionGroupInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.acquisitionGroupInfoStore',
//    model: 'AP.model.acquisitionUnit.AcquisitionUnitInfoModel',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/doAcquisitionGroupShow',
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
            var AcquisitionGroupInfoGridPanel = Ext.getCmp("AcquisitionGroupInfoGridPanel_Id");
            if (!isNotVal(AcquisitionGroupInfoGridPanel)) {
                var arrColumns = get_rawData.columns;
                var cloums = createDiagStatisticsColumn(arrColumns);
                var newColumns = Ext.JSON.decode(cloums);
                AcquisitionGroupInfoGridPanel = Ext.create('Ext.grid.Panel', {
                    id: "AcquisitionGroupInfoGridPanel_Id",
                    border: false,
                    stateful: true,
                    autoScroll: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: true,
                    multiSelect: true,
                    selModel:{
                    	selType:'checkboxmodel',
                    	showHeaderCheckbox:false,
                    	mode:'MULTI'//"SINGLE" / "SIMPLE" / "MULTI" 
                    },
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                        selectionchange: function (sm, selected) {
                        	if(selected.length>0){
                        		Ext.getCmp("acquisitionGroupUpdateBtn_Id").enable();
                        		Ext.getCmp("acquisitionGroupDeleteBtn_Id").enable();
                        		Ext.getCmp("selectedAcquisitionGroupCode_Id").setValue(selected[0].data.id);
//                        		Ext.getCmp("acquisitionItemsTreeGridPanel_Id").getStore().load();
                        		showAcquisitionGroupOwnItems();
                        	}else{
                        		Ext.getCmp("acquisitionGroupUpdateBtn_Id").disable();
                        		Ext.getCmp("acquisitionGroupDeleteBtn_Id").disable();
                        		if(driverConfigItemsHandsontableHelper!=null){
                                	var driverConfigItemsData=driverConfigItemsHandsontableHelper.hot.getData();
                                	for(var j=0;j<driverConfigItemsData.length;j++){
                                		driverConfigItemsHandsontableHelper.hot.setDataAtCell(j, 0, false);
                            		}
                                }
                        	}
                        },
                        itemdblclick: function () {
                            modifyAcquisitionGroupInfo();
                        }
                    }
                });
                var ContainPanel = Ext.getCmp("acquisitionGroupListPanel_Id");
                ContainPanel.add(AcquisitionGroupInfoGridPanel);
            }
            showAcquisitionUnitOwnGroups(store);

            if(driverConfigItemsHandsontableHelper!=null){
            	var driverConfigItemsData=driverConfigItemsHandsontableHelper.hot.getData();
            	for(var j=0;j<driverConfigItemsData.length;j++){
            		driverConfigItemsHandsontableHelper.hot.setDataAtCell(j, 0, false);
        		}
            }
        },
        beforeload: function (store, options) {
            var groupName= Ext.getCmp('acquisitionGroupName_Id').getValue();
            var new_params = {
            		groupName: groupName
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});