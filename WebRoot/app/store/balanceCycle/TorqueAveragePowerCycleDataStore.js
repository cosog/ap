Ext.define('AP.store.balanceCycle.TorqueAveragePowerCycleDataStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.torqueAveragePowerCycleDataStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/balanceCycleController/getSimulateBalanceData',
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
        	var get_rawData = store.proxy.reader.rawData;
            var GridPanel = Ext.getCmp("TorqueAveragePowerCycleBalanceInfoGrid1_Id");
            if (!isNotVal(GridPanel)) {
                GridPanel = Ext.create('Ext.grid.Panel', {
                    id: "TorqueAveragePowerCycleBalanceInfoGrid1_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: true,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: [
        		        { header: '序号',  dataIndex: 'id',align:'center',lockable:true,locked:false,width:50,xtype:'rownumberer' },
        		        { header: '平衡块', dataIndex: 'name',align:'center',lockable:true,locked:false },
        		        { header: '位置(cm)', dataIndex: 'position',align:'center',lockable:true,locked:false },
        		        { header: '重量(kN)', dataIndex: 'weight',align:'center' }
        		    ],
                    listeners: {
                    	itemclick:function( view , record , item , index , e , eOpts ) {
                    		
                    	},
                    	selectionchange:function(grid, record , eOpts) {
                    	}
                    }
                });
                var Panel = Ext.getCmp("CycleTorqueAveragePowerBalanceInfoPanel1_Id");
                Panel.add(GridPanel);
            }
            
            CreateOrUpdateCycleBalanceData(get_rawData.CycleBalance,"CycleTorqueAveragePowerBalanceInfoPanel2_Id","TorqueAveragePowerCycleBalanceInfoGrid2_Id");//创建模拟平衡块信息数据列表
            DegreeOfBalanceCurveChartFn(get_rawData.currentBalanceCurve,"CycleTorqueAveragePowerBalanceCurve1Div_Id",get_rawData.wellName,get_rawData.compositeBalance1,"历史平衡度曲线");//绘制当前平衡度曲线
            DegreeOfBalanceCurveChartFn(get_rawData.simulateBalanceCurve,"CycleTorqueAveragePowerBalanceCurve2Div_Id",get_rawData.wellName,get_rawData.compositeBalance2,"预期平衡度曲线");//绘制模拟平衡度曲线
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var jh = "";
        	var gklx="";
        	var startDate=Ext.getCmp('TorqueAveragePowerCycleStartDate_Id').rawValue;
        	var endDate=Ext.getCmp('TorqueAveragePowerCycleEndDate_Id').rawValue;
        	var TorqueAveragePowerCycleWellListGrid = Ext.getCmp("TorqueAveragePowerCycleWellListGrid_Id");
        	if(isNotVal(TorqueAveragePowerCycleWellListGrid)){
        		var record=TorqueAveragePowerCycleWellListGrid.getSelectionModel().getSelection();
        		if(record.length>0){
        			jh=record[0].data.jh;
        		}
        	}
        	var new_params = {
                    orgId: leftOrg_Id,
                    jh: jh,
                    type:3,
                    startDate:startDate,
                    endDate:endDate
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});

