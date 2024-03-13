Ext.define("AP.view.reportOut.ReportOutDailyReportView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.reportOutDayReportView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var DailyReportPanel = Ext.create('AP.view.reportOut.DailyReportPanel');
        
        var items=[];
        if(tabInfo.children!=undefined && tabInfo.children!=null && tabInfo.children.length>0){
        	for(var i=0;i<tabInfo.children.length;i++){
        		var panelItem={};
        		if(tabInfo.children[i].children!=undefined && tabInfo.children[i].children!=null && tabInfo.children[i].children.length>0){
        			panelItem={
        					title: tabInfo.children[i].text,
        					tpl: tabInfo.children[i].text,
        					xtype: 'tabpanel',
        	        		id: 'DailyReportPanel_'+tabInfo.children[i].deviceTypeId,
        	        		activeTab: 0,
        	        		border: false,
        	        		tabPosition: 'left',
        	        		items:[],
        	        		listeners: {
        	        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        	        				oldCard.removeAll();
        	        			},
        	        			tabchange: function (tabPanel, newCard,oldCard, obj) {
//        	        				var DailyReportPanel = Ext.create('AP.view.reportOut.DailyReportPanel');
//        	        				newCard.add(DailyReportPanel);
//        	        				Ext.getCmp("selectedDeviceType_global").setValue(newCard.id.split('_')[1]); 
//        	        				
//        	        				Ext.getCmp("DeviceSelectRow_Id").setValue(0);
//        	        		    	Ext.getCmp("DeviceSelectEndRow_Id").setValue(0);
//        	        				CreateAndLoadDeviceInfoTable(true);
        	        			},
        	        			afterrender: function (panel, eOpts) {
        	        				
        	        			}
        	        		}
        			}
        			
        			for(var j=0;j<tabInfo.children[i].children.length;j++){
        				var secondTabPanel={
        						title: '<div style="color:#000000;font-size:11px;font-family:SimSun">'+tabInfo.children[i].children[j].text+'</div>',
        						tpl:tabInfo.children[i].children[j].text,
        						layout: 'fit',
        						id: 'DailyReportPanel_'+tabInfo.children[i].children[j].deviceTypeId,
        						border: false
        				};
            			if(j==0){
            				secondTabPanel.items=[];
            				secondTabPanel.items.push(DailyReportPanel);
                		}
            			panelItem.items.push(secondTabPanel);
        			}
        		}else{
        			panelItem={
        					title: tabInfo.children[i].text,
        					tpl: tabInfo.children[i].text,
        					layout: 'fit',
    						id: 'DailyReportPanel_'+tabInfo.children[i].deviceTypeId,
    						border: false
        			};
        			if(i==0){
            			panelItem.items=[];
            			panelItem.items.push(DailyReportPanel);
            		}
        		}
        		items.push(panelItem);
        	}
        }
        
        
        Ext.apply(me, {
            items: [{
                xtype: 'tabpanel',
                id:'ProductionWellDailyReportPanel_Id',
                activeTab: 0,
                border: false,
                tabPosition: 'bottom',
                items: items,
                listeners: {
                    tabchange: function (tabPanel, newCard,oldCard, obj) {
//                    	if(newCard.id=="RPCDailyReportPanel_Id"){
//                    		Ext.getCmp("selectedDeviceType_global").setValue(0); 
//                    		var secondActiveId = Ext.getCmp("RPCDailyReportTabPanel").getActiveTab().id;
//                			if(secondActiveId=="RPCSingleWellDailyReportTabPanel_Id"){
//                				Ext.getCmp('RPCSingleWellDailyReportPanelWellListCombo_Id').setRawValue('');
//                				Ext.getCmp('RPCSingleWellDailyReportPanelWellListCombo_Id').setValue('');
//                				var gridPanel = Ext.getCmp("RPCSingleWellDailyReportGridPanel_Id");
//                				if (isNotVal(gridPanel)) {
//                					gridPanel.getStore().load();
//                				}else{
//                					Ext.create('AP.store.reportOut.RPCSingleWellDailyReportWellListStore');
//                				}
//                			}else if(secondActiveId=="RPCProductionDailyReportTabPanel_Id"){
//                				Ext.getCmp('RPCProductionDailyReportPanelWellListCombo_Id').setRawValue('');
//                				Ext.getCmp('RPCProductionDailyReportPanelWellListCombo_Id').setValue('');
//                				var gridPanel = Ext.getCmp("RPCProductionDailyReportGridPanel_Id");
//                    			if (isNotVal(gridPanel)) {
//                    				gridPanel.getStore().load();
//                    			}else{
//                    				Ext.create('AP.store.reportOut.RPCProductionDailyReportInstanceListStore');
//                    			}
//                			}
//                    	}else if(newCard.id=="PCPDailyReportPanel_Id"){
//                    		Ext.getCmp("selectedDeviceType_global").setValue(1); 
//                    		var secondActiveId = Ext.getCmp("PCPDailyReportTabPanel").getActiveTab().id;
//                			if(secondActiveId=="PCPSingleWellDailyReportTabPanel_Id"){
//                				Ext.getCmp('PCPSingleWellDailyReportPanelWellListCombo_Id').setRawValue('');
//                				Ext.getCmp('PCPSingleWellDailyReportPanelWellListCombo_Id').setValue('');
//                				var gridPanel = Ext.getCmp("PCPSingleWellDailyReportGridPanel_Id");
//                				if (isNotVal(gridPanel)) {
//                					gridPanel.getStore().load();
//                				}else{
//                					Ext.create('AP.store.reportOut.PCPSingleWellDailyReportWellListStore');
//                				}
//                			}else if(secondActiveId=="PCPProductionDailyReportTabPanel_Id"){
//                				Ext.getCmp('PCPProductionDailyReportPanelWellListCombo_Id').setRawValue('');
//                				Ext.getCmp('PCPProductionDailyReportPanelWellListCombo_Id').setValue('');
//                				var gridPanel = Ext.getCmp("PCPProductionDailyReportGridPanel_Id");
//                    			if (isNotVal(gridPanel)) {
//                    				gridPanel.getStore().load();
//                    			}else{
//                    				Ext.create('AP.store.reportOut.PCPProductionDailyReportInstanceListStore');
//                    			}
//                			}
//                    	}
                        Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
                    },
                    delay: 500
                }
       }]
        });
        me.callParent(arguments);
    }
});