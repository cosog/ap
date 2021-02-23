Ext.define('AP.store.calculateManager.CalculateManagerWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.CalculateManagerWellListStore',
    fields: ['id','wellName'],
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/calculateManagerController/getWellList',
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
            var CalculateManagerWellListGridPanel = Ext.getCmp("CalculateManagerWellListGridPanel_Id");
            if (!isNotVal(CalculateManagerWellListGridPanel)) {
                var column = createCalculateManagerWellListWellListDataColumn(arrColumns);
                var newColumns = Ext.JSON.decode(column);
                CalculateManagerWellListGridPanel = Ext.create('Ext.grid.Panel', {
                    id: "CalculateManagerWellListGridPanel_Id",
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
                    			Ext.getCmp('CalculateManagerWellListComBox_Id').setValue(selected[0].data.wellName);
                            	Ext.getCmp('CalculateManagerWellListComBox_Id').setRawValue(selected[0].data.wellName);
                    		}else{
                    			Ext.getCmp('CalculateManagerWellListComBox_Id').setValue('');
                            	Ext.getCmp('CalculateManagerWellListComBox_Id').setRawValue('');
                    		}
                    		Ext.create('AP.store.calculateManager.CalculateManagerDataStore');
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		
                        }
                    }
                });
                var CalculateManagerWellListPanel = Ext.getCmp("CalculateManagerWellListPanel_Id");
                CalculateManagerWellListPanel.add(CalculateManagerWellListGridPanel);
            }
            if(get_rawData.totalCount>0){
            	CalculateManagerWellListGridPanel.getSelectionModel().deselectAll(true);
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var wellName = Ext.getCmp('CalculateManagerWellListComBox_Id').getValue();
        	
        	var startDate=Ext.getCmp('CalculateManagerStartDate_Id').rawValue;
            var endDate=Ext.getCmp('CalculateManagerEndDate_Id').rawValue;
            var calculateSign=Ext.getCmp('CalculateManagerCalculateSignComBox_Id').getValue();
        	
        	var wellType=200;
            var calculateType=1;//1-抽油机诊断计产 2-螺杆泵诊断计产 3-抽油机汇总计算  4-螺杆泵汇总计算 5-电参反演地面功图计算
            var tabPanelId = Ext.getCmp("CalculateManagerTabPanel").getActiveTab().id;
            if(tabPanelId=="PumpingUnitCalculateManagerPanel"){
            	wellType=200;
            	calculateType=1;
			}else if(tabPanelId=="ScrewPumpCalculateManagerPanel"){
				wellType=400;
				calculateType=2;
			}else if(tabPanelId=="ElectricInversionCalculateManagerPanel"){
				calculateType=5;
			}
            var new_params = {
            		orgId: orgId,
            		wellName: wellName,
                    startDate:startDate,
                    endDate:endDate,
                    calculateSign:calculateSign,
                    wellType:wellType,
                    calculateType:calculateType
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});