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
        	        		id: 'ProductionReportRootTabPanel_'+tabInfo.children[i].deviceTypeId,
        	        		activeTab: 0,
        	        		border: false,
        	        		tabPosition: 'left',
        	        		items:[],
        	        		listeners: {
        	        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        	        				oldCard.removeAll();
        	        			},
        	        			tabchange: function (tabPanel, newCard,oldCard, obj) {
        	        				var DailyReportPanel = Ext.create('AP.view.reportOut.DailyReportPanel');
        	        				newCard.add(DailyReportPanel);
        	        				
        	        				var deviceType=getDeviceTypeFromTabId("ProductionReportRootTabPanel");
        	        				Ext.getCmp('selectedDeviceType_global').setValue();
        	        			},
        	        			afterrender: function (panel, eOpts) {
        	        				
        	        			}
        	        		}
        			}
        			var allSecondIds='';
        			for(var j=0;j<tabInfo.children[i].children.length;j++){
        				var secondTabPanel={
        						title: '<div style="color:#000000;font-size:11px;font-family:SimSun">'+tabInfo.children[i].children[j].text+'</div>',
        						tpl:tabInfo.children[i].children[j].text,
        						layout: 'fit',
        						id: 'ProductionReportRootTabPanel_'+tabInfo.children[i].children[j].deviceTypeId,
        						border: false
        				};
            			if(j==0){
            				secondTabPanel.items=[];
            				secondTabPanel.items.push(DailyReportPanel);
            				allSecondIds+=tabInfo.children[i].children[j].deviceTypeId;
                		}else{
                			allSecondIds+=(','+tabInfo.children[i].children[j].deviceTypeId);
                		}
            			panelItem.items.push(secondTabPanel);
        			}
        			if(panelItem.items.length>1){//添加全部标签
        				var secondTabPanel_all={
        						title: '<div style="color:#000000;font-size:11px;font-family:SimSun">全部</div>',
        						tpl:'全部',
        						layout: 'fit',
        						id: 'ProductionReportRootTabPanel_'+allSecondIds,
        						border: false
        				};
        				panelItem.items.push(secondTabPanel_all);
        			}
        		}else{
        			panelItem={
        					title: tabInfo.children[i].text,
        					tpl: tabInfo.children[i].text,
        					layout: 'fit',
    						id: 'ProductionReportRootTabPanel_'+tabInfo.children[i].deviceTypeId,
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
                id:'ProductionReportRootTabPanel',
                activeTab: 0,
                border: false,
                tabPosition: 'bottom',
                items: items,
                listeners: {
                	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        				if(oldCard.xtype=='tabpanel'){
        					oldCard.activeTab.removeAll();
        				}else{
        					oldCard.removeAll();
        				}
        			},
        			tabchange: function (tabPanel, newCard,oldCard, obj) {
        				Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
    					
    					var DailyReportPanel = Ext.create('AP.view.reportOut.DailyReportPanel');
        				if(newCard.xtype=='tabpanel'){
        					newCard.activeTab.add(DailyReportPanel);
        				}else{
	        				newCard.add(DailyReportPanel);
        				}
        				
        				var deviceType=getDeviceTypeFromTabId("ProductionReportRootTabPanel");
        				Ext.getCmp('selectedDeviceType_global').setValue();
        				
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
                    },
                    delay: 500
                }
       }]
        });
        me.callParent(arguments);
    }
});