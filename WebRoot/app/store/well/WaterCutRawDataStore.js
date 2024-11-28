Ext.define('AP.store.well.WaterCutRawDataStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.waterCutRawDataStore',
    fields: ['id','interval','waterCut','tubingPressure','position'],
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/wellInformationManagerController/getWaterCutRawData',
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
        	Ext.getCmp("UpstreamAndDownstreamInteractionConfigPanel2_Id").getEl().unmask();
            var get_rawData = store.proxy.reader.rawData;
            var arrColumns = get_rawData.columns;
            var column = createUpstreamAndDownstreamInteractionDeviceListColumn(arrColumns);
            Ext.getCmp("UpstreamAndDownstreamInteractionWaterCutRawDataColumnStr_Id").setValue(column);
            Ext.getCmp("UpstreamAndDownstreamInteractionExportWaterCutBtn_Id").enable();
            var gridPanel = Ext.getCmp("UpstreamAndDownstreamInteractionWaterCutRawDataGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = Ext.JSON.decode(column);
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "UpstreamAndDownstreamInteractionWaterCutRawDataGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: false,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	selectionchange: function (view, selected, o) {},
                    	itemdblclick: function (view,record,item,index,e,eOpts) {},
                    	select: function(grid, record, index, eOpts) {}
                    }
                });
                var panel = Ext.getCmp("UpstreamAndDownstreamInteractionWaterCutRawDataPanel_Id");
                panel.add(gridPanel);
            }
            showWaterCutRawDataCurve(get_rawData);
        },
        beforeload: function (store, options) {
        	Ext.getCmp("UpstreamAndDownstreamInteractionConfigPanel2_Id").el.mask("含水数据读取中...").show();
        	var wellName ='';
    		var wellId = '';
    		var signinId ='';
    		var slave = '';
        	var _record = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id").getSelectionModel().getSelection();
        	if(_record.length>0){
        		wellName = _record[0].data.wellName;
        		wellId = _record[0].data.id;
        		signinId = _record[0].data.signinId;
        		slave = _record[0].data.slave;
        	}
            var new_params = {
            		signinId: signinId,
            		slave:slave
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});