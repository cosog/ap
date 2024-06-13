Ext.define('AP.store.alarmSet.BalanceStatusAlarmSetStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.balanceStatusAlarmSetStore',
    model: 'AP.model.alarmSet.BalanceStatusAlarmSetModel',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/alarmSetManagerController/getBalanceAlarmStatusData',
        actionMethods: {
            read: 'POST'
        },
        start: 0,
        limit: 10000,
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
            var gridPanel = Ext.getCmp("balanceAlarmStatusGrid_Id");
            if (!isNotVal(gridPanel)) {
                var arrColumns = get_rawData.columns;
                var cloums = createBalanceLimitColumn(arrColumns);
                var newColumns = Ext.JSON.decode(cloums);
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "balanceAlarmStatusGrid_Id",
                    requires: [
                           	'Ext.grid.selection.SpreadsheetModel',
                           	'Ext.grid.plugin.Clipboard'
                    ],
                    xtype:'spreadsheet-checked',
                    selModel: {
                    	type: 'spreadsheet',
                    	columnSelect: true,
                    	checkboxSelect: true,
                    	pruneRemoved: false,
                    	extensible: 'xy'
                    },
                    plugins: [
                            'clipboard',
                            'selectionreplicator',
                            new Ext.grid.plugin.CellEditing({
                          	  clicksToEdit:2
                            })
                        ],
                    border: false,
                    stateful: true,
                    autoScroll: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: true,
                    multiSelect: true,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                        selectionchange: function (sm, selections) {
                        },
                        itemdblclick: function () {
                        }
                    }
                });
                var panel = Ext.getCmp("balanceStatusAlarmSetPanel_Id");
                panel.add(gridPanel);
            }
        },
        beforeload: function (store, options) {
        	var type='PHD';
        	var tabPanel = Ext.getCmp("iconSetTabpanel_id");
        	var activeId = tabPanel.getActiveTab().id;
        	if(activeId=="balanceStatusAlarmSetPanel_Id"){
        		type='PHD';
        	}
        	var new_params = {
    			type:type
    		};
    		Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});

createBalanceLimitColumn = function(columnInfo) {
	var myArr = columnInfo;
	var myColumns = "[";
	for (var i = 0; i < myArr.length; i++) {
		var attr = myArr[i];
		var width_="";
		var lock_="";
		var hidden_="";
		if(isNotVal(attr.lock)){
//			lock_=",locked:"+attr.lock;
		}
		if(isNotVal(attr.hidden==true)){
			hidden_=",hidden:"+attr.hidden;
		}
		if(isNotVal(attr.width)){
			width_=",width:"+attr.width;
		}
		myColumns +="{text:'" + attr.header + "'";
		 if (attr.dataIndex=='id'){
		  myColumns +=",width:"+attr.width+",xtype: 'rownumberer',sortable:false,align:'center',locked:false" ;
		}else if(attr.dataIndex=='gkmc'){
			myColumns +=hidden_+lock_+width_+",align:'center',dataIndex:'"+attr.dataIndex+"',"+"editor:{allowBlank:false}";
		}else {
			myColumns +=hidden_+lock_+width_+",align:'center',dataIndex:'"+attr.dataIndex+"',"+"editor:{allowBlank:false}";
		}
		myColumns += "}";
		if (i < myArr.length - 1) {
			myColumns += ",";
		}
	}
	myColumns +="]";
	return myColumns;
}