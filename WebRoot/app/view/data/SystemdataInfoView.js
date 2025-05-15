var loginUserDataDictionaryManagementModuleRight=getRoleModuleRight('DataDictionaryManagement');
Ext.define("AP.view.data.SystemdataInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.systemdataInfoView',
    layout: 'border',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        var SystemdataInfoGridPanel = Ext.create('AP.view.data.SystemdataInfoGridPanel');
        var DictItemGridPanel = Ext.create('AP.view.data.DictItemGridPanel');
        
        
        var items=[];
        var firstActiveTab=0;
        var secondActiveTab=0;
        if(tabInfo.children!=undefined && tabInfo.children!=null && tabInfo.children.length>0){
        	for(var i=0;i<tabInfo.children.length;i++){
        		var panelItem={};
        		if(tabInfo.children[i].children!=undefined && tabInfo.children[i].children!=null && tabInfo.children[i].children.length>0){
        			panelItem={
        					title: tabInfo.children[i].text,
        					tpl: tabInfo.children[i].text,
        					xtype: 'tabpanel',
        	        		id: 'DictItemRootTabPanel_'+tabInfo.children[i].deviceTypeId,
        	        		activeTab: i==firstActiveTab?secondActiveTab:0,
        	        		iconCls: i==firstActiveTab?'check1':null,
        	        		border: false,
        	        		tabPosition: 'left',
        	        		items:[],
        	        		listeners: {
        	        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        	        				Ext.getCmp("DictItemRootTabPanel").el.mask(loginUserLanguageResource.loading).show();
        	        				oldCard.setIconCls(null);
        	        				newCard.setIconCls('check2');
        	        				oldCard.removeAll();
        	        			},
        	        			tabchange: function (tabPanel, newCard,oldCard, obj) {
        	        				var DictItemGridPanel = Ext.create('AP.view.data.DictItemGridPanel');
        	        				newCard.add(DictItemGridPanel);
        	        				
        	        				var dataDictionaryItemGridPanel = Ext.getCmp("dataDictionaryItemGridPanel_Id"); 
                                	if (isNotVal(dataDictionaryItemGridPanel)) {
                                		dataDictionaryItemGridPanel.getStore().load();
                                	}else{
                                		Ext.create("AP.store.data.DataDictionaryItemInfoStore");
                                	}
                                	
                                	Ext.getCmp("DictItemRootTabPanel").getEl().unmask();
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
        						id: 'DictItemRootTabPanel_'+tabInfo.children[i].children[j].deviceTypeId,
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
        						title: loginUserLanguageResource.all,
        						tpl:loginUserLanguageResource.all,
//        						iconCls:'check2',
        						layout: 'fit',
        						id: 'DictItemRootTabPanel_'+allSecondIds,
        						border: false
        				};
        				panelItem.items.splice(0, 0, secondTabPanel_all);
        			}
        			if(i==firstActiveTab && panelItem.items.length>secondActiveTab){
        				panelItem.items[secondActiveTab].iconCls='check2';
        			}
        			if(i==firstActiveTab && panelItem.items.length>secondActiveTab){
        				panelItem.items[secondActiveTab].items=[];
        				panelItem.items[secondActiveTab].items.push(DictItemGridPanel);
    				}
        		}else{
        			panelItem={
        					title: tabInfo.children[i].text,
        					tpl: tabInfo.children[i].text,
        					layout: 'fit',
    						id: 'DictItemRootTabPanel_'+tabInfo.children[i].deviceTypeId,
    						iconCls: i==firstActiveTab?'check1':null,
    						border: false
        			};
        			if(i==firstActiveTab){
            			panelItem.items=[];
            			panelItem.items.push(DictItemGridPanel);
            		}
        		}
        		items.push(panelItem);
        	}
        }
        
        Ext.apply(me, {
        	items:[{
        		region:'center',
        		layout: "fit",
        		header:false,
        		items:SystemdataInfoGridPanel
        	},{
        		region:'east',
        		width:'45%',
        		xtype: 'tabpanel',
        		id:"DictItemRootTabPanel",
        		activeTab: firstActiveTab,
        		border: false,
        		tabPosition: 'bottom',
        		items: items,
        		listeners: {
    				beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
    					Ext.getCmp("DictItemRootTabPanel").el.mask(loginUserLanguageResource.loading).show();
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
    					
    					var DictItemGridPanel = Ext.create('AP.view.data.DictItemGridPanel');
        				if(newCard.xtype=='tabpanel'){
        					newCard.activeTab.add(DeviceOperationLogInfoPanel);
        				}else{
	        				newCard.add(DeviceOperationLogInfoPanel);
        				}
        				
        				var dataDictionaryItemGridPanel = Ext.getCmp("dataDictionaryItemGridPanel_Id"); 
                    	if (isNotVal(dataDictionaryItemGridPanel)) {
                    		dataDictionaryItemGridPanel.getStore().load();
                    	}else{
                    		Ext.create("AP.store.data.DataDictionaryItemInfoStore");
                    	}
                    	
                    	Ext.getCmp("DictItemRootTabPanel").getEl().unmask();
    				}
    			}
        	}]
        });
        me.callParent(arguments);
    }

});