Ext.define('AP.store.device.PumpingunitStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.pumpingunitStore',
    model: 'AP.model.device.PumpingunitModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/pumpingunitController/findAllList',
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
            var gridPanel = Ext.getCmp("pumpingunitGrid_Id");
            if (!isNotVal(gridPanel)) {
                var arrColumns = get_rawData.columns;
                var cloums = createPumpingUintTableColumn(arrColumns);
                var newColumns = Ext.JSON.decode(cloums);
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "pumpingunitGrid_Id",
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
                    forceFit: false,
//                    selType: 'checkboxmodel',
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
                var panel = Ext.getCmp("PumpingunitPanel_Id");
                panel.add(gridPanel);
            }
            for(var i=1;i<=500-get_rawData.totalCount;i++){
            	gridPanel.getStore().add(new Ext.data.Record({}));
            }
        },
        beforeload: function (store, options) {
            var pumpingunitPanel_sccj_Id = Ext.getCmp('pumpingunitPanel_sccj_Id');
            var pumpingunitPanel_cyjxh_Id = Ext.getCmp('pumpingunitPanel_cyjxh_Id');

            if (!Ext.isEmpty(pumpingunitPanel_sccj_Id)) {
                pumpingunitPanel_sccj_Id = pumpingunitPanel_sccj_Id.getValue();
            }
            if (!Ext.isEmpty(pumpingunitPanel_cyjxh_Id)) {
                pumpingunitPanel_cyjxh_Id = pumpingunitPanel_cyjxh_Id.getValue();
            }
            //alert(pumpingunitPanel_cyjxh_Id);
            var new_params = {
                sccj: pumpingunitPanel_sccj_Id,
                cyjxh: pumpingunitPanel_cyjxh_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});

createPumpingUintTableColumn = function(columnInfo) {
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
		}else if(attr.dataIndex=='xzfxmc'){
			myColumns +=hidden_+lock_+width_+",align:'center',dataIndex:'"+attr.dataIndex+"',"+"editor:{" +
					"xtype:'combobox'," +
					"typeAhead:true," +
					"triggerAction:'all'," +
					"store:[['顺时针','顺时针'],['逆时针','逆时针']]," +
					"forceSelection :true," +
					"selectOnFocus:true," +
					" allowBlank:false}," +
					"renderer:function(value,o,p,e){return getXzfxValue(value,o,p,e);}";
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

getXzfxValue = function(val,o,p,e) {
	 return val;
	}