Ext.define('AP.store.graphicalQuery.GraphicalQueryWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.GraphicalQueryWellListStore',
    fields: ['id','wellName'],
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/surfaceCardQueryController/getWellList',
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
            var GraphicalQueryWellListGridPanel = Ext.getCmp("GraphicalQueryWellListGridPanel_Id");
            if (!isNotVal(GraphicalQueryWellListGridPanel)) {
                var column = createGraphicalQueryWellListWellListDataColumn(arrColumns);
                var newColumns = Ext.JSON.decode(column);
                GraphicalQueryWellListGridPanel = Ext.create('Ext.grid.Panel', {
                    id: "GraphicalQueryWellListGridPanel_Id",
                    border: false,
                    autoLoad: true,
                    columnLines: true,
                    forceFit: false,
//                    selType: 'checkboxmodel',
//                    multiSelect: false,
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
                    			Ext.getCmp('FSDiagramAnalysisGraphicalQueryWellName_Id').setValue(selected[0].data.wellName);
                            	Ext.getCmp('FSDiagramAnalysisGraphicalQueryWellName_Id').setRawValue(selected[0].data.wellName);
                            	Ext.getCmp("SurfaceCard_from_date_Id").show();
                        		Ext.getCmp("SurfaceCard_to_date_Id").show();
                    		}else{
                    			Ext.getCmp('FSDiagramAnalysisGraphicalQueryWellName_Id').setValue('');
                            	Ext.getCmp('FSDiagramAnalysisGraphicalQueryWellName_Id').setRawValue('');
                            	Ext.getCmp("SurfaceCard_from_date_Id").hide();
                        		Ext.getCmp("SurfaceCard_to_date_Id").hide();
                    		}
                    		loadSurfaceCardList(1);
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		
                        }
                    }
                });
                var GraphicalQueryWellListPanel = Ext.getCmp("GraphicalQueryWellListPanel_Id");
                GraphicalQueryWellListPanel.add(GraphicalQueryWellListGridPanel);
            }
            if(get_rawData.totalCount>0){
            	GraphicalQueryWellListGridPanel.getSelectionModel().deselectAll(true);
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var wellName = Ext.getCmp('FSDiagramAnalysisGraphicalQueryWellName_Id').getValue();
            var new_params = {
                    orgId: orgId,
                    wellName: wellName,
                    wellType:200
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});