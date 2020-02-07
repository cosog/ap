Ext.define('AP.store.acquisitionUnit.AcquisitionUnitInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.acquisitionUnitInfoStore',
//    model: 'AP.model.acquisitionUnit.AcquisitionUnitInfoModel',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/doAcquisitionUnitShow',
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
            var AcquisitionUnitInfoGridPanel = Ext.getCmp("AcquisitionUnitInfoGridPanel_Id");
            if (!isNotVal(AcquisitionUnitInfoGridPanel)) {
                var arrColumns = get_rawData.columns;
                var cloums = createDiagStatisticsColumn(arrColumns);
                var newColumns = Ext.JSON.decode(cloums);
                //分页工具栏
                var bbar = new Ext.PageNumberToolbar({
                    store: store,
                    pageSize: defaultPageSize,
                    displayInfo: true,
//                    displayMsg: '当前记录 {0} -- {1} 条 共 {2} 条记录',
                    displayMsg: '当前 {0}~{1}条  共 {2} 条',
                    emptyMsg: "没有记录可显示",
                    prevText: "上一页",
                    nextText: "下一页",
                    refreshText: "刷新",
                    lastText: "最后页",
                    firstText: "第一页",
                    beforePageText: "当前页",
                    afterPageText: "共{0}页"
                });
                AcquisitionUnitInfoGridPanel = Ext.create('Ext.grid.Panel', {
                    id: "AcquisitionUnitInfoGridPanel_Id",
                    border: false,
                    stateful: true,
                    autoScroll: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: true,
//                    selType: 'checkboxmodel',
//                    multiSelect: true,
                    selModel:{
                    	selType:'checkboxmodel',
                    	showHeaderCheckbox:false,
                    	mode:'SINGLE'
                    },
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    bbar: bbar,
                    store: store,
                    columns: newColumns,
                    listeners: {
                        selectionchange: function (sm, selected) {
                        	if(selected.length>0){
                        		Ext.getCmp("acquisitionUnitUpdateBtn_Id").enable();
                        		Ext.getCmp("acquisitionUnitDeleteBtn_Id").enable();
                        		Ext.getCmp("selectedAcquisitionUnitCode_Id").setValue(selected[0].data.id);
                        		Ext.getCmp("acquisitionItemsTreeGridPanel_Id").getStore().load();
                        	}else{
                        		Ext.getCmp("acquisitionUnitUpdateBtn_Id").disable();
                        		Ext.getCmp("acquisitionUnitDeleteBtn_Id").disable();
                        	}
                        },
                        itemdblclick: function () {
                            modifyAcquisitionUnitInfo();
                        }
                    }
                });
                var ContainPanel = Ext.getCmp("acquisitionUnitListPanel_Id");
                ContainPanel.add(AcquisitionUnitInfoGridPanel);
            }

        },
        beforeload: function (store, options) {
            var unitName= Ext.getCmp('acquisitionUnitName_Id').getValue();
            var new_params = {
            		unitName: unitName
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});