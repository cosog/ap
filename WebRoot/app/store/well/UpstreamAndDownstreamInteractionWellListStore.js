Ext.define('AP.store.well.UpstreamAndDownstreamInteractionWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.UpstreamAndDownstreamInteractionWellListStore',
    fields: ['id','commStatus','commStatusName','wellName'],
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/wellInformationManagerController/getUpstreamAndDownstreamInteractionDeviceList',
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
            var column = createUpstreamAndDownstreamInteractionDeviceListColumn(arrColumns);
            var gridPanel = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = Ext.JSON.decode(column);
                var bbar = new Ext.PagingToolbar({
                	store: store,
                	displayInfo: true,
                	displayMsg: '共 {2}条'
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "UpstreamAndDownstreamInteractionDeviceListGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    bbar: bbar,
                    columnLines: true,
                    forceFit: false,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	selectionchange: function (view, selected, o) {},
                    	itemdblclick: function (view,record,item,index,e,eOpts) {
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListSelectRow_Id").setValue(record.data.id);
               			 	var commStatus = record.data.commStatus;
               			 	var tabPanel = Ext.getCmp("UpstreamAndDownstreamInteractionConfigTabpanel_Id");
               			 	var activeId = tabPanel.getActiveTab().id;
               			 	if(activeId=="UpstreamAndDownstreamInteractionConfigPanel1_Id"){
               			 		if(parseInt(commStatus)==0){
               			 			Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").disable();
               			 			Ext.getCmp('UpstreamAndDownstreamInteractionConfigDataTextArea_Id').setValue('');
               			 		}else{
               			 			Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").enable();
               			 			requestConfigData();
               			 		}
               			 	}else if(activeId=="UpstreamAndDownstreamInteractionConfigPanel2_Id"){
               			 		Ext.getCmp("UpstreamAndDownstreamInteractionWaterCutRawDataPanel_Id").removeAll();
               			 		$("#UpstreamAndDownstreamInteractionWaterCutRawDataCurveDiv_Id").html('');
               			 		Ext.getCmp("UpstreamAndDownstreamInteractionExportWaterCutBtn_Id").disable();
               			 		if(parseInt(commStatus)==0){
               			 			Ext.getCmp("UpstreamAndDownstreamInteractionReadWaterCutBtn_Id").disable();
               			 		}else{
               			 			Ext.getCmp("UpstreamAndDownstreamInteractionReadWaterCutBtn_Id").enable();
               			 		}
               			 	}
                    	}
                    }
                });
                var panel = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListPanel_Id");
                panel.add(gridPanel);
            }
            if(get_rawData.totalCount>0){
            	gridPanel.getSelectionModel().deselectAll(true);
            	gridPanel.getSelectionModel().select(0, true);
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceName=Ext.getCmp('UpstreamAndDownstreamInteractionDeviceListComb_Id').getValue();
            var new_params = {
                    orgId: orgId,
                    deviceType:0,
                    deviceName:deviceName
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});