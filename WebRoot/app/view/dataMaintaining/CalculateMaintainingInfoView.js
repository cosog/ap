var loginUserCalculateMaintainingModuleRight=getRoleModuleRight('CalculateMaintaining');
Ext.define("AP.view.dataMaintaining.CalculateMaintainingInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.CalculateMaintainingInfoView',
    layout: 'fit',
    border: false,
    initComponent: function () {
    	var CalculateMaintainingInfoPanel = Ext.create('AP.view.dataMaintaining.CalculateMaintainingInfoPanel');
        
        var items=[];
        var deviceTypeActiveId=getDeviceTypeActiveId();
        var firstActiveTab=deviceTypeActiveId.firstActiveTab;
        var secondActiveTab=deviceTypeActiveId.secondActiveTab;
        if(tabInfo.children!=undefined && tabInfo.children!=null && tabInfo.children.length>0){
        	for(var i=0;i<tabInfo.children.length;i++){
        		var panelItem={};
        		if(tabInfo.children[i].children!=undefined && tabInfo.children[i].children!=null && tabInfo.children[i].children.length>0){
        			panelItem={
        					title: tabInfo.children[i].text,
        					tpl: tabInfo.children[i].text,
        					xtype: 'tabpanel',
        	        		id: 'CalculateMaintainingRootTabPanel_'+tabInfo.children[i].deviceTypeId,
        	        		activeTab: i==firstActiveTab?secondActiveTab:0,
        	        				iconCls: i==firstActiveTab?'check1':null,
        	        		border: false,
        	        		tabPosition: 'left',
        	        		items:[],
        	        		listeners: {
        	        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        	        				Ext.getCmp("CalculateMaintainingRootTabPanel").el.mask(loginUserLanguageResource.loading).show();
        	        				
        	        				if(oldCard!=undefined){
        	        					oldCard.setIconCls(null);
        	        					oldCard.removeAll();
        	                	    }
        	                	    if(newCard!=undefined){
        	                	    	newCard.setIconCls('check2');				
        	                	    }
        	        			},
        	        			tabchange: function (tabPanel, newCard,oldCard, obj) {
        	        				var CalculateMaintainingInfoPanel = Ext.create('AP.view.dataMaintaining.CalculateMaintainingInfoPanel');
        	        				newCard.add(CalculateMaintainingInfoPanel);
        	        				
        	        				refreshCalculateMaintainingData();
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
        						id: 'CalculateMaintainingRootTabPanel_'+tabInfo.children[i].children[j].deviceTypeId,
//        						iconCls: (panelItem.items.length==1&&j==0)?'check2':null,
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
        						id: 'CalculateMaintainingRootTabPanel_'+allSecondIds,
        						border: false
        				};
        				panelItem.items.splice(0, 0, secondTabPanel_all);
        			}
        			if(i==firstActiveTab && panelItem.items.length>secondActiveTab){
        				panelItem.items[secondActiveTab].iconCls='check2';
        			}
        			if(i==firstActiveTab && panelItem.items.length>secondActiveTab){
        				panelItem.items[secondActiveTab].items=[];
        				panelItem.items[secondActiveTab].items.push(CalculateMaintainingInfoPanel);
    				}
        		}else{
        			panelItem={
        					title: tabInfo.children[i].text,
        					tpl: tabInfo.children[i].text,
        					layout: 'fit',
    						id: 'CalculateMaintainingRootTabPanel_'+tabInfo.children[i].deviceTypeId,
    						iconCls: i==firstActiveTab?'check1':null,
    						border: false
        			};
        			if(i==firstActiveTab){
            			panelItem.items=[];
            			panelItem.items.push(CalculateMaintainingInfoPanel);
            		}
        		}
        		items.push(panelItem);
        	}
        }
        
    	Ext.apply(this, {
    		tbar:[{
            	id: 'CalculateMaintainingModuleViewFlag',
            	xtype: 'textfield',
                value: loginUserCalculateMaintainingModuleRight.viewFlag,
                hidden: true
             },{
            	id: 'CalculateMaintainingModuleEditFlag',
            	xtype: 'textfield',
                value: loginUserCalculateMaintainingModuleRight.editFlag,
                hidden: true
             },{
            	id: 'CalculateMaintainingModuleControlFlag',
            	xtype: 'textfield',
                value: loginUserCalculateMaintainingModuleRight.controlFlag,
                hidden: true
            }],
    		items: [{
        		xtype: 'tabpanel',
        		id:"CalculateMaintainingRootTabPanel",
        		activeTab: firstActiveTab,
        		border: false,
        		tabPosition: 'bottom',
        		items: items,
        		listeners: {
    				beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
    					Ext.getCmp("CalculateMaintainingRootTabPanel").el.mask(loginUserLanguageResource.loading).show();
    					
    					if(oldCard!=undefined){
    						oldCard.setIconCls(null);
    						if(oldCard.xtype=='tabpanel'){
            					oldCard.activeTab.removeAll();
            				}else{
            					oldCard.removeAll();
            				}
                	    }
                	    if(newCard!=undefined){
                	    	newCard.setIconCls('check1');		
                	    }
        			},
        			tabchange: function (tabPanel, newCard,oldCard, obj) {
    					Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
    					
    					var CalculateMaintainingInfoPanel = Ext.create('AP.view.dataMaintaining.CalculateMaintainingInfoPanel');
        				if(newCard.xtype=='tabpanel'){
        					newCard.activeTab.add(CalculateMaintainingInfoPanel);
        				}else{
	        				newCard.add(CalculateMaintainingInfoPanel);
        				}
        				
        				refreshCalculateMaintainingData();
    				}
    			}
            }]
        });
        this.callParent(arguments);
    }
});

function refreshCalculateMaintainingData(){
	var deviceType=getDeviceTypeFromTabId("CalculateMaintainingRootTabPanel");
	Ext.getCmp("selectedDeviceType_global").setValue(deviceType); 
	var firstDeviceType=getDeviceTypeFromTabId_first("CalculateMaintainingRootTabPanel");
	Ext.getCmp("selectedFirstDeviceType_global").setValue(firstDeviceType); 
	
	var gridPanel = Ext.getCmp("DataMaintainingDeviceListGridPanel_Id");
	if (isNotVal(gridPanel)) {
		gridPanel.getStore().load();
	}else{
		Ext.create('AP.store.dataMaintaining.DataMaintainingDevcieListStore');
	}
	

//	var tabPanel = Ext.getCmp("CalculateMaintainingTabPanel");
//	var activeId = tabPanel.getActiveTab().id;
//	if(activeId=="AcquisitionDataMaintainingInfoPanel_Id"){
//		refreshAcquisitionDataMaintainingData();
//	}else if(activeId=="SRPCalculateMaintainingInfoPanel_Id"){
//		refreshSRPCalculateMaintainingData();
//	}else if(activeId=="PCPCalculateMaintainingInfoPanel_Id"){
//		refreshPCPCalculateMaintainingData();
//	}
}
