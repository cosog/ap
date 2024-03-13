Ext.define("AP.view.dataMaintaining.CalculateMaintainingInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.CalculateMaintainingInfoView',
    layout: 'fit',
    border: false,
    initComponent: function () {
    	var CalculateMaintainingInfoPanel = Ext.create('AP.view.dataMaintaining.CalculateMaintainingInfoPanel');
        
        var items=[];
        if(tabInfo.children!=undefined && tabInfo.children!=null && tabInfo.children.length>0){
        	for(var i=0;i<tabInfo.children.length;i++){
        		var panelItem={};
        		if(tabInfo.children[i].children!=undefined && tabInfo.children[i].children!=null && tabInfo.children[i].children.length>0){
        			panelItem={
        					title: tabInfo.children[i].text,
        					tpl: tabInfo.children[i].text,
        					xtype: 'tabpanel',
        	        		id: 'CalculateMaintainingRootTabPanel_'+tabInfo.children[i].deviceTypeId,
        	        		activeTab: 0,
        	        		border: false,
        	        		tabPosition: 'left',
        	        		items:[],
        	        		listeners: {
        	        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        	        				oldCard.removeAll();
        	        			},
        	        			tabchange: function (tabPanel, newCard,oldCard, obj) {
        	        				var CalculateMaintainingInfoPanel = Ext.create('AP.view.dataMaintaining.CalculateMaintainingInfoPanel');
        	        				newCard.add(CalculateMaintainingInfoPanel);
        	        				
        	        				var tabPanel = Ext.getCmp("CalculateMaintainingTabPanel");
        	        				var activeId = tabPanel.getActiveTab().id;
        	        				if(activeId=="RPCCalculateMaintainingInfoPanel_Id"){
        	        					refreshRPCCalculateMaintainingData();
        	        				}else if(activeId=="PCPCalculateMaintainingInfoPanel_Id"){
        	        					refreshPCPCalculateMaintainingData();
        	        				}
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
        						id: 'CalculateMaintainingRootTabPanel_'+tabInfo.children[i].children[j].deviceTypeId,
        						border: false
        				};
            			if(j==0){
            				secondTabPanel.items=[];
            				secondTabPanel.items.push(CalculateMaintainingInfoPanel);
                		}
            			panelItem.items.push(secondTabPanel);
        			}
        		}else{
        			panelItem={
        					title: tabInfo.children[i].text,
        					tpl: tabInfo.children[i].text,
        					layout: 'fit',
    						id: 'CalculateMaintainingRootTabPanel_'+tabInfo.children[i].deviceTypeId,
    						border: false
        			};
        			if(i==0){
            			panelItem.items=[];
            			panelItem.items.push(CalculateMaintainingInfoPanel);
            		}
        		}
        		items.push(panelItem);
        	}
        }
        
    	Ext.apply(this, {
    		items: [{
        		xtype: 'tabpanel',
        		id:"CalculateMaintainingRootTabPanel",
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
    					
    					var CalculateMaintainingInfoPanel = Ext.create('AP.view.dataMaintaining.CalculateMaintainingInfoPanel');
        				if(newCard.xtype=='tabpanel'){
        					newCard.activeTab.add(CalculateMaintainingInfoPanel);
        				}else{
	        				newCard.add(CalculateMaintainingInfoPanel);
        				}
        				
        				var tabPanel = Ext.getCmp("CalculateMaintainingTabPanel");
        				var activeId = tabPanel.getActiveTab().id;
        				if(activeId=="RPCCalculateMaintainingInfoPanel_Id"){
        					refreshRPCCalculateMaintainingData();
        				}else if(activeId=="PCPCalculateMaintainingInfoPanel_Id"){
        					refreshPCPCalculateMaintainingData();
        				}
    				}
    			}
            }]
        });
        this.callParent(arguments);
    }
});