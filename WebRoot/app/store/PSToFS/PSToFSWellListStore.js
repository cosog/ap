Ext.define('AP.store.PSToFS.PSToFSWellListStore', {
    extend: 'Ext.data.Store',
    fields: ['jlbh','wellName'],
    autoLoad: true,
    pageSize: 50,
    proxy: {
    	type: 'ajax',
        url: context + '/PSToFSController/getWellList',
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
        load: function (store, sEops) {
        	var get_rawData = store.proxy.reader.rawData;
            var arrColumns = get_rawData.columns;
            var column = createElectricAcqAndInverColumn(arrColumns);
            var newColumns = Ext.JSON.decode(column);
            var gridPanel = Ext.getCmp("ElectricAcqAndInverInfo_Id");
            if (!isNotVal(gridPanel)) {
            	gridPanel= Ext.create('Ext.grid.Panel', {
                    id: 'ElectricAcqAndInverInfo_Id',
                    border: false,
                    stateful: true,
                    autoScroll: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: true,
                    store:store,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    columns:newColumns,
                    listeners: {
                        selectionchange: function (view, selected, o) {
                            if (selected.length > 0) {
                            	var gridPanel = Ext.getCmp("ElectricAcqAndInverDistreteGrid_Id");
                                if (isNotVal(gridPanel)) {
                                	gridPanel.getStore().load();
                                }else{
                                	Ext.create('AP.store.PSToFS.DiscreteDataStore')
                                }
                                
                                var diagramListGridPanel = Ext.getCmp("ElectricAcqAndInverDiagramListGrid_Id");
                                if (isNotVal(diagramListGridPanel)) {
                                	diagramListGridPanel.getStore().load();
                                }else{
                                	Ext.create('AP.store.PSToFS.DiagramDataListStore')
                                }
                            }
                        }
                    }
                })
            	Ext.getCmp("ElectricAcqAndInverWellListPanel").add(gridPanel);
            }
            if(get_rawData.totalCount>0){
                if (isNotVal(gridPanel)) {
                	gridPanel.getSelectionModel().deselectAll(true);
                	gridPanel.getSelectionModel().select(0, true);
                }
            }else{
            	Ext.getCmp("ElectricAcqAndInverDiscreteDataPanel").removeAll();
            	Ext.getCmp("ElectricAcqAndInverDiagramDataPanel").removeAll();
            	$("#inverDiagramDiv1_id").html('');
            	$("#inverDiagramDiv2_id").html('');
            	$("#inverDiagramDiv3_id").html('');
            }
        },
        beforeload: function (store, options) {
            var orgId = Ext.getCmp('leftOrg_Id').getValue();
            var wellName = Ext.getCmp('PSToFSAcqAndInversionJHCombo_Id').getValue();
            var new_params = {
                orgId: orgId,
                wellName: wellName
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        	onStoreSizeChange(v, o, "DiagnosisPumpingUnit_SingleDinagnosisAnalysisTotalCount_Id");
        }
    }
});