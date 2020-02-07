Ext.define('AP.store.balanceInfo.BalanceInformationStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.balanceInformationStore',
    model: 'AP.model.balanceInfo.BalanceInformationModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/balanceInformationController/getBalanceInformationData',
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
            var gridPanel = Ext.getCmp("balanceInformationGrid_Id");
            if (!isNotVal(gridPanel)) {
                var arrColumns = get_rawData.columns;
                var cloums = createPumpingUintTableColumn(arrColumns);
                var newColumns = Ext.JSON.decode(cloums);
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "balanceInformationGrid_Id",
                    requires: [
                           	'Ext.grid.selection.SpreadsheetModel',
                           	'Ext.grid.plugin.Clipboard'
                    ],
                    xtype:'spreadsheet-checked',
                    selModel: {
                    	type: 'spreadsheet',
                    	columnSelect: false,
                    	checkboxSelect: false,
                    	pruneRemoved: false,
                    	multiSelect: false,
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
//                    selType: 'checkboxmodel',
                    
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                        selectionchange: function (grid, record , eOpts) {
                        	
                        },
                        itemclick:function( view , record , item , index , e , eOpts ) {
                        	Ext.getCmp("balanceInformationSelectedJh_Id").setValue(record.data.jh);
                        	updateBalanceSetFormData(record);
                        }
                        	
                    }
                });
                var panel = Ext.getCmp("BalanceInformationTablePanel_Id");
                panel.add(gridPanel);
            }
            var record=store.getAt(0);
            if(record!=null){
            	Ext.getCmp("balanceInformationSelectedJh_Id").setValue(record.data.jh);
                updateBalanceSetFormData(record);
                gridPanel.getSelectionModel().select(0,true);//选中第一行
            }else{
            	Ext.getCmp("balanceInformationSelectedJh_Id").setValue("");
            	clearBalanceSetFormData();
            }
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
            var sccj = Ext.getCmp('balancePumpingunitSccj_Id').getValue();
            var cyjxh = Ext.getCmp('balancePumpingunitCyjxh_Id').getValue();
            var jh = Ext.getCmp('balancePumpingunitJh_Id').getValue();
            //alert(pumpingunitPanel_cyjxh_Id);
            var new_params = {
                sccj: sccj,
                cyjxh: cyjxh,
                jh:jh,
                recordCount:recordCount,
                orgId:leftOrg_Id
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
			myColumns +=hidden_+lock_+width_+",align:'center',dataIndex:'"+attr.dataIndex+"'";
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