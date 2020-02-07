Ext.define('AP.store.diagnosisTotal.ScrewPumpDaillyAnalysisTableStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.dcrewPumpDaillyAnalysisTableStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/diagnosisTotalController/getAnalysisAndAcqAndControlData',
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
    		dataStr+="{\"item\":\"运行时间(h)\",\"itemCode\":\"rgzsj\",\"value\":"+get_rawData.rgzsj+",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"运行时率(%)\",\"itemCode\":\"scsl\",\"value\":"+get_rawData.scsl+",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"产液量(t/d)\",\"itemCode\":\"jsdjrcyld\",\"value\":\""+get_rawData.jsdjrcyld+"/"+get_rawData.jsdjrcyldmax+"/"+get_rawData.jsdjrcyldmin+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"产油量(t/d)\",\"itemCode\":\"jsdjrcyld1\",\"value\":\""+get_rawData.jsdjrcyld1+"/"+get_rawData.jsdjrcyld1max+"/"+get_rawData.jsdjrcyld1min+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"产水量(t/d)\",\"itemCode\":\"jsdjrcsld\",\"value\":\""+get_rawData.jsdjrcsld+"/"+get_rawData.jsdjrcsldmax+"/"+get_rawData.jsdjrcsldmin+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"含水率(%)\",\"itemCode\":\"hsld\",\"value\":\""+get_rawData.hsld+"/"+get_rawData.hsldmax+"/"+get_rawData.hsldmin+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"总泵效(%)\",\"itemCode\":\"zbx\",\"value\":\""+get_rawData.zbx+"/"+get_rawData.zbxmax+"/"+get_rawData.zbxmin+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"系统效率(%)\",\"itemCode\":\"xtxl\",\"value\":\""+get_rawData.xtxl+"/"+get_rawData.xtxlmax+"/"+get_rawData.xtxlmin+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"动液面(m)\",\"itemCode\":\"dym\",\"value\":\""+get_rawData.dym+"/"+get_rawData.dymmax+"/"+get_rawData.dymmin+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"泵挂(m)\",\"itemCode\":\"bg\",\"value\":\""+get_rawData.bg+"/"+get_rawData.bgmax+"/"+get_rawData.bgmin+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"沉没度(m)\",\"itemCode\":\"cmd\",\"value\":\""+get_rawData.cmd+"/"+get_rawData.cmdmax+"/"+get_rawData.cmdmin+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"生产气油比(m^3/t)\",\"itemCode\":\"scqyb\",\"value\":\""+get_rawData.scqyb+"/"+get_rawData.scqybmax+"/"+get_rawData.scqybmin+"\",\"curve\":\"\"}";
    		dataStr+="]}";
    		
    		var acqSataStr="{\"items\":[";
    		acqSataStr+="{\"item\":\"转速(r/min)\",\"itemCode\":\"rpm\",\"value\":\""+get_rawData.rpm+"/"+get_rawData.rpmmax+"/"+get_rawData.rpmmin+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"A相电流(A)\",\"itemCode\":\"currenta\",\"value\":\""+get_rawData.currenta+"/"+get_rawData.currentamax+"/"+get_rawData.currentamin+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"B相电流(A)\",\"itemCode\":\"currentb\",\"value\":\""+get_rawData.currentb+"/"+get_rawData.currentbmax+"/"+get_rawData.currentbmin+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"C相电流(A)\",\"itemCode\":\"currentc\",\"value\":\""+get_rawData.currentc+"/"+get_rawData.currentcmax+"/"+get_rawData.currentcmin+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"A相电压(V)\",\"itemCode\":\"voltagea\",\"value\":\""+get_rawData.voltagea+"/"+get_rawData.voltageamax+"/"+get_rawData.voltageamin+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"B相电压(V)\",\"itemCode\":\"voltageb\",\"value\":\""+get_rawData.voltageb+"/"+get_rawData.voltagebmax+"/"+get_rawData.voltagebmin+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"C相电压(V)\",\"itemCode\":\"voltagec\",\"value\":\""+get_rawData.voltagec+"/"+get_rawData.voltagecmax+"/"+get_rawData.voltagecmin+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"累计有功功耗(kW·h)\",\"itemCode\":\"positiveaptc\",\"value\":\""+get_rawData.positiveaptc+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"累计无功功耗(kVar·h)\",\"itemCode\":\"positiverptc\",\"value\":\""+get_rawData.positiverptc+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"日有功功耗(kW·h)\",\"itemCode\":\"dailypaptc\",\"value\":\""+get_rawData.dailypaptc+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"日无功功耗(kVar·h)\",\"itemCode\":\"dailyprptc\",\"value\":\""+get_rawData.dailyprptc+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"有功功率(kW)\",\"itemCode\":\"activepower\",\"value\":\""+get_rawData.activepower+"/"+get_rawData.activepowermax+"/"+get_rawData.activepowermin+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"无功功率(kVar)\",\"itemCode\":\"reactivepower\",\"value\":\""+get_rawData.reactivepower+"/"+get_rawData.reactivepowermax+"/"+get_rawData.reactivepowermin+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"功率因数\",\"itemCode\":\"powerfactor\",\"value\":\""+get_rawData.powerfactor+"/"+get_rawData.powerfactormax+"/"+get_rawData.powerfactormin+"\",\"curve\":\"\"}";
    		acqSataStr+="]}";
    		
    		var controlSataStr="{\"items\":[";
    		controlSataStr+="{\"item\":\"启/停抽\",\"value\":\"运行\",\"operation\":\"\"},";
    		controlSataStr+="{\"item\":\"运行频率(Hz)\",\"value\":\"60.5\",\"operation\":\"\"}";
    		controlSataStr+="]}";
    		
    		var storeData=Ext.JSON.decode(dataStr);
    		var acqStoreData=Ext.JSON.decode(acqSataStr);
    		var controlStoreData=Ext.JSON.decode(controlSataStr);
    		
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
    		var controlStore=Ext.create('Ext.data.Store', {
			    fields:['item','value','operation'],
			    data:controlStoreData,
			    proxy: {
			        type: 'memory',
			        reader: {
			            type: 'json',
			            root: 'items'
			        }
			    }
			});
    		
    		var GridPanel=Ext.getCmp("ScrewPumpDailyCalDataGridPanel_Id");
    		if(!isNotVal(GridPanel)){
    			GridPanel=Ext.create('Ext.grid.Panel', {
    				id:'ScrewPumpDailyCalDataGridPanel_Id',
    				border: false,
    				columnLines: true,
    				forceFit: false,
    				store: store,
    			    columns: [
    			    	{ 
    			        	header: '名称',  
    			        	dataIndex: 'item',
    			        	align:'left',
    			        	flex:2,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { 
    			        	header: '值', 
    			        	dataIndex: 'value',
    			        	align:'center',
    			        	flex:2,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { header: '曲线', dataIndex: 'curve',align:'center',flex:1,renderer :function(value,e,o){return iconDiagnoseTotalCurve(value,e,o)} }
    			    ]
    			});
    			Ext.getCmp("ScrewPumpDailyAnalysisTableCalDataPanel_Id").add(GridPanel);
    		}else{
    			GridPanel.reconfigure(store);
    		}
    		
    		var acqGridPanel=Ext.getCmp("ScrewPumpDailyAcqDataGridPanel_Id");
    		if(!isNotVal(acqGridPanel)){
    			acqGridPanel=Ext.create('Ext.grid.Panel', {
    				id:'ScrewPumpDailyAcqDataGridPanel_Id',
    				border: false,
    				columnLines: true,
    				forceFit: false,
    				store: acqStore,
    			    columns: [
    			    	{ 
    			        	header: '名称',  
    			        	dataIndex: 'item',
    			        	align:'left',
    			        	flex:2,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { 
    			        	header: '值', 
    			        	dataIndex: 'value',
    			        	align:'center',
    			        	flex:2,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { header: '曲线', dataIndex: 'curve',align:'center',flex:1,renderer :function(value,e,o){return iconDiagnoseTotalCurve(value,e,o)} }
    			    ]
    			});
    			Ext.getCmp("ScrewPumpDailyAnalysisTableAcqDataPanel_Id").add(acqGridPanel);
    		}else{
    			acqGridPanel.reconfigure(acqStore);
    		}
        },
        beforeload: function (store, options) {
        	var jlbh  = Ext.getCmp("ScrewPumpDailyAnalysisWellList_Id").getSelectionModel().getSelection()[0].data.id;
        	var new_params = {
        			jlbh: jlbh
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});