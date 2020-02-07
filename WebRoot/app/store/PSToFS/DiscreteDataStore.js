Ext.define('AP.store.PSToFS.DiscreteDataStore', {
    extend: 'Ext.data.Store',
    fields: ['jlbh','cjsj',
    	'yxzt', 'egklx', 'egkmc', 'egklxstr', 'yxsj', 'yxsl', 'yxsjd', 
		'currenta', 'currentb','currentc', 'voltagea', 'voltageb','voltagec',
		'activepowerconsumption', 'reactivepowerconsumption', 'activepower', 'reactivepower', 'powerfactor',
		'rydl'],
    autoLoad: true,
    pageSize: 50,
    proxy: {
    	type: 'ajax',
        url: context + '/PSToFSController/getDiscreteData',
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
            var column = createElectricInverDiscreteColumn(arrColumns);
            var newColumns = Ext.JSON.decode(column);
            var bbar = new Ext.PagingToolbar({
            	store: store,
            	displayInfo: true,
            	displayMsg: '当前 {0}~{1}条  共 {2} 条'
	        });
            var gridPanel = Ext.getCmp("ElectricAcqAndInverDistreteGrid_Id");
            if (!isNotVal(gridPanel)) {
            	gridPanel= Ext.create('Ext.grid.Panel', {
                    id: 'ElectricAcqAndInverDistreteGrid_Id',
                    bbar: bbar,
                    border: false,
                    stateful: true,
                    autoScroll: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: false,
                    store:store,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    columns:newColumns,
                    listeners: {
                        selectionchange: function (view, selected, o) {
                            if (selected.length > 0) {
                            }
                        }
                    }
                })
            	Ext.getCmp("ElectricAcqAndInverDiscreteDataPanel").add(gridPanel);
            }
            var startDate=Ext.getCmp('PSToFSAcqAndInversionStartDate_Id').rawValue;
            if(startDate==''||null==startDate){
            	Ext.getCmp("PSToFSAcqAndInversionStartDate_Id").setValue(get_rawData.start_date==undefined?get_rawData.startDate:get_rawData.start_date);
            }
        },
        beforeload: function (store, options) {
            var wellName = Ext.getCmp("ElectricAcqAndInverInfo_Id").getSelectionModel().getSelection()[0].data.wellName;
            var startDate=Ext.getCmp('PSToFSAcqAndInversionStartDate_Id').rawValue;
            var endDate=Ext.getCmp('PSToFSAcqAndInversionEndDate_Id').rawValue;
            var new_params = {
                wellName: wellName,
                startDate:startDate,
                endDate:endDate,
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});