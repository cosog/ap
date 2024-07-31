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
        	        		iconCls: i==0?'check1':null,
        	        		items:[],
        	        		listeners: {
        	        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        	        				oldCard.setIconCls(null);
        	        				newCard.setIconCls('check2');
        	        				oldCard.removeAll();
        	        			},
        	        			tabchange: function (tabPanel, newCard,oldCard, obj) {
        	        				var DailyReportPanel = Ext.create('AP.view.reportOut.DailyReportPanel');
        	        				newCard.add(DailyReportPanel);
        	        				
        	        				var deviceType=getDeviceTypeFromTabId("ProductionReportRootTabPanel");
        	        				Ext.getCmp('selectedDeviceType_global').setValue();
        	        				
        	        				var secondActiveId = Ext.getCmp("DailyReportTabPanel").getActiveTab().id;
        	        				if(secondActiveId=="SingleWellDailyReportTabPanel_Id"){
        	        					Ext.getCmp('SingleWellDailyReportPanelWellListCombo_Id').setRawValue('');
        	        					Ext.getCmp('SingleWellDailyReportPanelWellListCombo_Id').setValue('');
        	        					var gridPanel = Ext.getCmp("SingleWellDailyReportGridPanel_Id");
        	        					if (isNotVal(gridPanel)) {
        	        						gridPanel.getStore().load();
        	        					}else{
        	        						Ext.create('AP.store.reportOut.SingleWellDailyReportWellListStore');
        	        					}
        	        				}else if(secondActiveId=="ProductionDailyReportTabPanel_Id"){
        	        					Ext.getCmp('ProductionDailyReportPanelWellListCombo_Id').setRawValue('');
        	        					Ext.getCmp('ProductionDailyReportPanelWellListCombo_Id').setValue('');
        	        					var gridPanel = Ext.getCmp("ProductionDailyReportGridPanel_Id");
        	        	    			if (isNotVal(gridPanel)) {
        	        	    				gridPanel.getStore().load();
        	        	    			}else{
        	        	    				Ext.create('AP.store.reportOut.ProductionDailyReportInstanceListStore');
        	        	    			}
        	        				}
        	        			},
        	        			afterrender: function (panel, eOpts) {
        	        				
        	        			}
        	        		}
        			}
        			var allSecondIds='';
        			for(var j=0;j<tabInfo.children[i].children.length;j++){
        				var secondTabPanel={
        						title: tabInfo.children[i].children[j].text,
        						tpl:tabInfo.children[i].children[j].text,
        						layout: 'fit',
        						iconCls: (panelItem.items.length==1&&j==0)?'check2':null,
        						id: 'ProductionReportRootTabPanel_'+tabInfo.children[i].children[j].deviceTypeId,
        						border: false
        				};
            			if(j==0){
            				allSecondIds+=tabInfo.children[i].children[j].deviceTypeId;
                		}else{
                			allSecondIds+=(','+tabInfo.children[i].children[j].deviceTypeId);
                		}
            			panelItem.items.push(secondTabPanel);
        			}
        			if(panelItem.items.length>1){//添加全部标签
        				var secondTabPanel_all={
        						title: '全部',
        						tpl:'全部',
        						iconCls:'check2',
        						layout: 'fit',
        						id: 'ProductionReportRootTabPanel_'+allSecondIds,
        						border: false
        				};
        				panelItem.items.splice(0, 0, secondTabPanel_all);
        			}
        			if(i==0 && panelItem.items.length>0){
        				panelItem.items[0].items=[];
        				panelItem.items[0].items.push(DailyReportPanel);
    				}
        		}else{
        			panelItem={
        					title: tabInfo.children[i].text,
        					tpl: tabInfo.children[i].text,
        					layout: 'fit',
        					iconCls: i==0?'check1':null,
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
                		oldCard.setIconCls(null);
        				newCard.setIconCls('check1');
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
        				
        				var secondActiveId = Ext.getCmp("DailyReportTabPanel").getActiveTab().id;
        				if(secondActiveId=="SingleWellDailyReportTabPanel_Id"){
        					Ext.getCmp('SingleWellDailyReportPanelWellListCombo_Id').setRawValue('');
        					Ext.getCmp('SingleWellDailyReportPanelWellListCombo_Id').setValue('');
        					var gridPanel = Ext.getCmp("SingleWellDailyReportGridPanel_Id");
        					if (isNotVal(gridPanel)) {
        						gridPanel.getStore().load();
        					}else{
        						Ext.create('AP.store.reportOut.SingleWellDailyReportWellListStore');
        					}
        				}else if(secondActiveId=="ProductionDailyReportTabPanel_Id"){
        					Ext.getCmp('ProductionDailyReportPanelWellListCombo_Id').setRawValue('');
        					Ext.getCmp('ProductionDailyReportPanelWellListCombo_Id').setValue('');
        					var gridPanel = Ext.getCmp("ProductionDailyReportGridPanel_Id");
        	    			if (isNotVal(gridPanel)) {
        	    				gridPanel.getStore().load();
        	    			}else{
        	    				Ext.create('AP.store.reportOut.ProductionDailyReportInstanceListStore');
        	    			}
        				}
                    },
                    delay: 500
                }
       }]
        });
        me.callParent(arguments);
    }
});