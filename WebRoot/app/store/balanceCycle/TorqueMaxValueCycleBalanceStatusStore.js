Ext.define('AP.store.balanceCycle.TorqueMaxValueCycleBalanceStatusStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.torqueMaxValueCycleBalanceStatusStore',
    model: 'AP.model.balanceCycle.CycleWellListModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/balanceCycleController/getBalanceStatusData',
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
            var GridPanel = Ext.getCmp("TorqueMaxValueCycleWellListGrid_Id");
            if (!isNotVal(GridPanel)) {
                var column = createBalanceMinitorListColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                GridPanel = Ext.create('Ext.grid.Panel', {
                    id: "TorqueMaxValueCycleWellListGrid_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: false,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	itemclick:function( view , record , item , index , e , eOpts ) {
                    		
                    	},
                    	selectionchange:function(grid, record , eOpts) {
                    		if(record.length>0){
                    			Ext.getCmp("TorqueMaxValueCycleStartDate_Id").setValue(record[0].data.gtkssj);
                    			Ext.getCmp("TorqueMaxValueCycleEndDate_Id").setValue(record[0].data.gtjssj);
                    			Ext.getCmp("TorqueMaxValueCycleTextfield_id").setValue(record[0].data.sczq);
                            	var currentbalance=record[0].data.currentbalance;
                            	var optimizationbalance=record[0].data.optimizationbalance;
                            	TorqueMaxValueCycleBalanceInfoGrid1=Ext.getCmp("TorqueMaxValueCycleBalanceInfoGrid1_Id");
                            	if (isNotVal(TorqueMaxValueCycleBalanceInfoGrid1)) {
                            		TorqueMaxValueCycleBalanceInfoGrid1.getStore().load();
                            	}else{
                            		Ext.create("AP.store.balanceCycle.TorqueMaxValueCycleDataStore");
                            	}
                            		

                    		}
                    	}
                    }
                });
                var TorqueMaxValueCycleBalanceStatusPanel = Ext.getCmp("TorqueMaxValueCycleBalanceStatusPanel_Id");
                TorqueMaxValueCycleBalanceStatusPanel.add(GridPanel);
            }
            if(get_rawData.totalCount>0){
            	GridPanel.getSelectionModel().select(0,true);//选中第一行
            }else{
            	Ext.getCmp("CycleTorqueMaxValueBalanceInfoPanel1_Id").removeAll();
            	Ext.getCmp("CycleTorqueMaxValueBalanceInfoPanel2_Id").removeAll();
            	Ext.get("CycleTorqueMaxValueBalanceCurve1Div_Id").dom.innerHTML = "";
            	Ext.get("CycleTorqueMaxValueBalanceCurve2Div_Id").dom.innerHTML = "";
            }
            var selectedJh=Ext.getCmp('TorqueMaxValueCyclejh_Id').getValue();
            var selectedGkmc=Ext.getCmp('TorqueMaxValueBalanceCycleSelectedGkmc_Id').getValue();
            if((selectedJh==null||selectedJh==""||selectedJh=="null")&&selectedGkmc==""){
            	var tabPanel = Ext.getCmp("CycleStatTorqueMaxValueTabpanel_Id");
            	var activeId = tabPanel.getActiveTab().id;
            	if(activeId=="CycleStatTorqueMaxValuePiePanel_Id"){
            		Ext.create("AP.store.balanceCycle.CycleStatTorqueMaxValueStore");
            	}
            }
            
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var jh = Ext.getCmp('TorqueMaxValueCyclejh_Id').getValue();
        	var gkmc=Ext.getCmp('TorqueMaxValueBalanceCycleSelectedGkmc_Id').getValue();;
        	
        	var new_params = {
                    orgId: leftOrg_Id,
                    jh: jh,
                    type:2,
                    gkmc:gkmc
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});

//创建或更新实时监测列表数据
function CreateOrUpdateCycleBalanceInfoGrid(balancedata,gridId,panelId){
	var currentbalanceArr=balancedata.split(";");
	var dataStr="{'items':["
	for(var i=1;i<=currentbalanceArr.length;i++){
		var position=parseFloat(currentbalanceArr[i-1].split(",")[0])*100;
		var weight=parseFloat(currentbalanceArr[i-1].split(",")[1]);
		dataStr+="{'id':"+i+","
		dataStr+="'name':'平衡块"+i+"',"
		dataStr+="'position':"+position+","
		dataStr+="'weight':"+weight+"}"
		if(i<currentbalanceArr.length){
			dataStr+=","
		}
	}
	dataStr+="]}";
	var storeData=Ext.JSON.decode(dataStr);
	var GridPanel=Ext.getCmp(gridId);
	if(!isNotVal(GridPanel)){
		var store=Ext.create('Ext.data.Store', {
		    fields:['id', 'name','position', 'weight'],
		    data:storeData,
		    proxy: {
		        type: 'memory',
		        reader: {
		            type: 'json',
		            root: 'items'
		        }
		    }
		});
		
		GridPanel=Ext.create('Ext.grid.Panel', {
			id:gridId,
			border: false,
			columnLines: true,
			forceFit: true,
			store: store,
		    columns: [
		        { header: '序号',  dataIndex: 'id',align:'center',lockable:true,locked:false,width:50,xtype:'rownumberer' },
		        { header: '平衡块', dataIndex: 'name',align:'center', },
		        { header: '位置(cm)', dataIndex: 'position',align:'center', },
		        { header: '重量(kN)', dataIndex: 'weight',align:'center',width:150 }
		    ],
		    listeners: {
		    	itemdblclick: function (view,record,item,ndex,e,eOpts) {
                },
		    }
		});
		
		Ext.getCmp(panelId).add(GridPanel);
	}else{
		GridPanel.getStore().setData(storeData);
		if(storeData.items.length>0){
			GridPanel.getStore().load();
		}
	}
};