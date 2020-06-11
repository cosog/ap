Ext.define('AP.store.electricAnalysis.ElectricAnalysisRealtimeDiagramDetailsRightTableStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.electricAnalysisRealtimeDiagramDetailsRightTableStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/PSToFSController/getRealtimeDiagramAnalysisAndAcqData',
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
        	
        	var dataStr="{\"items\":[";
//    		dataStr+="{\"item\":\"上冲程最大电流(A)\",\"itemCode\":\"upStrokeIMax\",\"value\":"+get_rawData.upStrokeIMax+",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"下冲程最大电流(A)\",\"itemCode\":\"downStrokeIMax\",\"value\":"+get_rawData.downStrokeIMax+",\"curve\":\"\"},";
        	dataStr+="{\"item\":\"电流比\",\"itemCode\":\"downAndUpStrokeIMax\",\"value\":\""+get_rawData.downStrokeIMax+"/"+get_rawData.upStrokeIMax+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"电流平衡度(%)\",\"itemCode\":\"iDegreeBalance\",\"value\":\""+get_rawData.iDegreeBalance+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"上冲程最大功率(kW)\",\"itemCode\":\"upStrokeWattMax\",\"value\":"+get_rawData.upStrokeWattMax+",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"下冲程最大功率(kW)\",\"itemCode\":\"downStrokeWattMax\",\"value\":"+get_rawData.downStrokeWattMax+",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"功率比\",\"itemCode\":\"downAndUpStrokeWattMax\",\"value\":\""+get_rawData.downStrokeWattMax+"/"+get_rawData.upStrokeWattMax+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"功率平衡度(%)\",\"itemCode\":\"wattDegreeBalance\",\"value\":\""+get_rawData.wattDegreeBalance+"\",\"curve\":\"\"}";
    		dataStr+="]}";
    		
    		var acqSataStr="{\"items\":[";
    		acqSataStr+="{\"item\":\"冲程(m)\",\"itemCode\":\"stroke\",\"value\":\""+get_rawData.stroke+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"冲次(1/min)\",\"itemCode\":\"spm\",\"value\":\""+get_rawData.spm+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"最大载荷(kN)\",\"itemCode\":\"fmax\",\"value\":\""+get_rawData.fmax+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"最小载荷(kN)\",\"itemCode\":\"fmin\",\"value\":\""+get_rawData.fmin+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"信号强度\",\"itemCode\":\"signal\",\"value\":\""+get_rawData.signal+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"传输间隔(min)\",\"itemCode\":\"interval\",\"value\":\""+get_rawData.interval+"\",\"curve\":\"\"}";
    		acqSataStr+="]}";
    		
    		
    		var storeData=Ext.JSON.decode(dataStr);
    		var acqStoreData=Ext.JSON.decode(acqSataStr);
    		
    		var store=Ext.create('Ext.data.Store', {
			    fields:['item', 'itemCode','value', 'curve'],
			    data:storeData,
			    proxy: {
			        type: 'memory',
			        reader: {
			            type: 'json',
			            root: 'items'
			        }
			    }
			});
    		var acqStore=Ext.create('Ext.data.Store', {
			    fields:['item', 'itemCode','value', 'curve'],
			    data:acqStoreData,
			    proxy: {
			        type: 'memory',
			        reader: {
			            type: 'json',
			            root: 'items'
			        }
			    }
			});
    		
    		var GridPanel=Ext.getCmp("ElectricAnalysisRealtimeDiagramDetailsRightGridPanel_Id");
    		if(!isNotVal(GridPanel)){
    			GridPanel=Ext.create('Ext.grid.Panel', {
    				id:'ElectricAnalysisRealtimeDiagramDetailsRightGridPanel_Id',
    				border: false,
    				columnLines: true,
    				forceFit: false,
    				store: store,
    			    columns: [
    			    	{ 
    			    		header: '名称',  
    			        	dataIndex: 'item',
    			        	align:'left',//内容靠左
    			        	style:{
    			        		'text-align':'center'//表头居中
    			        	},
    			        	flex:3,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { 
    			        	header: '变量', 
    			        	dataIndex: 'value',
    			        	align:'center',
    			        	flex:2,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { header: '曲线', dataIndex: 'curve',align:'center',flex:1,renderer :function(value,e,o){return iconElectricAnalysisRealtimeCurve(value,e,o)} }
    			    ]
    			});
    			Ext.getCmp("ElectricAnalysisRealtimeDiagramDetailsRightAnalysisPanel_Id").add(GridPanel);
    		}else{
    			GridPanel.reconfigure(store);
    		}
    		
    		var acqGridPanel=Ext.getCmp("ElectricAnalysisRealtimeDiagramDetailsRightAcqGridPanel_Id");
    		if(!isNotVal(acqGridPanel)){
    			acqGridPanel=Ext.create('Ext.grid.Panel', {
    				id:'ElectricAnalysisRealtimeDiagramDetailsRightAcqGridPanel_Id',
    				border: false,
    				columnLines: true,
    				forceFit: false,
    				store: acqStore,
    			    columns: [
    			    	{ 
    			    		header: '名称',  
    			        	dataIndex: 'item',
    			        	align:'left',//内容靠左
    			        	style:{
    			        		'text-align':'center'//表头居中
    			        	},
    			        	flex:3,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { 
    			        	header: '变量', 
    			        	dataIndex: 'value',
    			        	align:'center',
    			        	flex:2,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { header: '曲线', dataIndex: 'curve',align:'center',flex:1,renderer :function(value,e,o){return iconElectricAnalysisRealtimeCurve(value,e,o)} }
    			    ]
    			});
    			Ext.getCmp("ElectricAnalysisRealtimeDiagramDetailsRightAcqPanel_Id").add(acqGridPanel);
    		}else{
    			acqGridPanel.reconfigure(acqStore);
    		}
        },
        beforeload: function (store, options) {
        	var id  = Ext.getCmp("ElectricAnalysisRealtimeDiagramDetails_Id").getSelectionModel().getSelection()[0].data.id;
        	var wellName = Ext.getCmp('electricAnalysisRealtimeDetailsWellCom_Id').getValue();
        	var new_params = {
        			id: id,
        			wellName:wellName
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});