Ext.define('AP.store.dataMaintaining.DataMaintainingDevcieListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.DataMaintainingDevcieListStore',
    fields: ['id','deviceName'],
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/calculateManagerController/getDeviceList',
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
            var gridPanel = Ext.getCmp("DataMaintainingDeviceListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var column = createCalculateManagerWellListColumn(arrColumns);
                var newColumns = Ext.JSON.decode(column);
                var bbar = new Ext.PagingToolbar({
                	id:'DataMaintainingDeviceListGridPagingToolbar',
                	store: store,
                	displayInfo: true
    	        });
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "DataMaintainingDeviceListGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    bbar: bbar,
                    columnLines: true,
                    forceFit: false,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	selectionchange: function (view, selected, o) {
                    	},
                    	rowclick: function( grid, record, element, index, e, eOpts) {
                    		var deviceId=record.data.id;
                    		Ext.getCmp("selectedDeviceId_global").setValue(deviceId);
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("DataMaintainingDeviceListSelectRow_Id").setValue(index);
                    		var calculateType=record.data.calculateType;
                    		
                    		var combDeviceName=Ext.getCmp('DataMaintainingDeviceListComBox_Id').getValue();
                    		if(combDeviceName!=''){
                        		Ext.getCmp("selectedDeviceId_global").setValue(record.data.id);
                    		}
                    		
                    		
                    		var tabPanel = Ext.getCmp("CalculateMaintainingTabPanel");
                    		var activeId = tabPanel.getActiveTab().id;
                    		var tabChange=false;
                    		var SRPCalculateMaintainingInfoPanel = tabPanel.getComponent("SRPCalculateMaintainingInfoPanel_Id");
                    		var PCPCalculateMaintainingInfoPanel = tabPanel.getComponent("PCPCalculateMaintainingInfoPanel_Id");
                    		if(calculateType==1){
                    			if(activeId=='PCPCalculateMaintainingInfoPanel_Id'){
                    				tabPanel.setActiveTab('AcquisitionDataMaintainingInfoPanel_Id');
                    				tabChange=true;
                    			}
                    			if(SRPCalculateMaintainingInfoPanel==undefined){
                    				var SRPCalculateMaintainingInfoView = Ext.create('AP.view.dataMaintaining.SRPCalculateMaintainingInfoView');
                    				tabPanel.insert(1,{
                            			title: loginUserLanguageResource.SRPCalculate,
                            			id:'SRPCalculateMaintainingInfoPanel_Id',
                            			items: [SRPCalculateMaintainingInfoView],
                            			layout: "fit",
//                            			hidden: !moduleContentConfig.dataMaintaining.FESDiagramResultData,
                            			border: false
                            		});
                    			}
                    			
                    			if(PCPCalculateMaintainingInfoPanel!=undefined){
                    				tabPanel.remove(PCPCalculateMaintainingInfoPanel);
                    			}
                    		}else if(calculateType==2){
                    			if(activeId=='SRPCalculateMaintainingInfoPanel_Id'){
                    				tabPanel.setActiveTab('AcquisitionDataMaintainingInfoPanel_Id');
                    				tabChange=true;
                    			}
                    			if(PCPCalculateMaintainingInfoPanel==undefined){
                    				var PCPCalculateMaintainingInfoView = Ext.create('AP.view.dataMaintaining.PCPCalculateMaintainingInfoView');
                    				tabPanel.insert(2,{
                            			title: loginUserLanguageResource.PCPCalculate,
                            			id:'PCPCalculateMaintainingInfoPanel_Id',
                            			items: [PCPCalculateMaintainingInfoView],
                            			layout: "fit",
//                            			hidden: !moduleContentConfig.dataMaintaining.RPMResultData,
                            			border: false
                            		});
                    			}
                    			
                    			if(SRPCalculateMaintainingInfoPanel!=undefined){
                    				tabPanel.remove(SRPCalculateMaintainingInfoPanel);
                    			}
                    		}else{
                    			if(activeId!='AcquisitionDataMaintainingInfoPanel_Id'){
                    				tabPanel.setActiveTab('AcquisitionDataMaintainingInfoPanel_Id');
                    				tabChange=true;
                    			}
                    			
                    			if(SRPCalculateMaintainingInfoPanel!=undefined){
                    				tabPanel.remove(SRPCalculateMaintainingInfoPanel);
                    			}
                    			if(PCPCalculateMaintainingInfoPanel!=undefined){
                    				tabPanel.remove(PCPCalculateMaintainingInfoPanel);
                    			}
                    		}
                    		
                    		
                    		if(!tabChange){
                    			if(activeId=="AcquisitionDataMaintainingInfoPanel_Id"){
                        			refreshAcquisitionDataMaintainingData();
                        		}else if(activeId=="SRPCalculateMaintainingInfoPanel_Id"){
                        			refreshSRPCalculateMaintainingData();
                        		}else if(activeId=="PCPCalculateMaintainingInfoPanel_Id"){
                        			refreshPCPCalculateMaintainingData();
                        		}
                    		}
                    		
                        }
                    }
                });
                var deviceListPanel = Ext.getCmp("DataMaintainingDeviceListPanel_Id");
                deviceListPanel.add(gridPanel);
            }
            if(get_rawData.totalCount>0){
            	var selectRow=0;
            	var selectedDeviceId=parseInt(Ext.getCmp("selectedDeviceId_global").getValue());
    			if(selectedDeviceId>0){
    				for(var i=0;i<store.data.items.length;i++){
            			if(selectedDeviceId==store.data.items[i].data.id){
            				selectRow=i;
            				break;
            			}
            		}
    			}
            	gridPanel.getSelectionModel().deselectAll(true);
            	gridPanel.getSelectionModel().select(selectRow, true);
            }else{
            	Ext.getCmp("DataMaintainingDeviceListSelectRow_Id").setValue(-1);
            	
//            	var tabPanel = Ext.getCmp("CalculateMaintainingTabPanel");
//        		var activeId = tabPanel.getActiveTab().id;
//        		if(activeId=="AcquisitionDataMaintainingInfoPanel_Id"){
//        			refreshAcquisitionDataMaintainingData();
//        		}else if(activeId=="SRPCalculateMaintainingInfoPanel_Id"){
//        			refreshSRPCalculateMaintainingData();
//        		}else if(activeId=="PCPCalculateMaintainingInfoPanel_Id"){
//        			refreshPCPCalculateMaintainingData();
//        		}
            	
            	var calculateType=0;
            	var tabPanel = Ext.getCmp("CalculateMaintainingTabPanel");
        		var activeId = tabPanel.getActiveTab().id;
        		var tabChange=false;
        		var SRPCalculateMaintainingInfoPanel = tabPanel.getComponent("SRPCalculateMaintainingInfoPanel_Id");
        		var PCPCalculateMaintainingInfoPanel = tabPanel.getComponent("PCPCalculateMaintainingInfoPanel_Id");


    			if(activeId!='AcquisitionDataMaintainingInfoPanel_Id'){
    				tabPanel.setActiveTab('AcquisitionDataMaintainingInfoPanel_Id');
    				tabChange=true;
    			}
    			
    			if(SRPCalculateMaintainingInfoPanel!=undefined){
    				tabPanel.remove(SRPCalculateMaintainingInfoPanel);
    			}
    			if(PCPCalculateMaintainingInfoPanel!=undefined){
    				tabPanel.remove(PCPCalculateMaintainingInfoPanel);
    			}
    		
        		
        		if(!tabChange){
        			if(activeId=="AcquisitionDataMaintainingInfoPanel_Id"){
            			refreshAcquisitionDataMaintainingData();
            		}else if(activeId=="SRPCalculateMaintainingInfoPanel_Id"){
            			refreshSRPCalculateMaintainingData();
            		}else if(activeId=="PCPCalculateMaintainingInfoPanel_Id"){
            			refreshPCPCalculateMaintainingData();
            		}
        		}
            	
            }
            Ext.getCmp("CalculateMaintainingRootTabPanel").getEl().unmask();
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceName = Ext.getCmp('DataMaintainingDeviceListComBox_Id').getValue();
        	var deviceType=getDeviceTypeFromTabId("CalculateMaintainingRootTabPanel");
            var new_params = {
            		orgId: orgId,
            		deviceName: deviceName,
                    deviceType:deviceType
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});