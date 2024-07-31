var loginUserCalculateMaintainingModuleRight=getRoleModuleRight('CalculateMaintaining');
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
        	        		iconCls: i==0?'check1':null,
        	        		border: false,
        	        		tabPosition: 'left',
        	        		items:[],
        	        		listeners: {
        	        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        	        				oldCard.setIconCls(null);
        	        				newCard.setIconCls('check2');
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
        			var allSecondIds='';
        			for(var j=0;j<tabInfo.children[i].children.length;j++){
        				var secondTabPanel={
        						title: tabInfo.children[i].children[j].text,
        						tpl:tabInfo.children[i].children[j].text,
        						layout: 'fit',
        						id: 'CalculateMaintainingRootTabPanel_'+tabInfo.children[i].children[j].deviceTypeId,
        						iconCls: (panelItem.items.length==1&&j==0)?'check2':null,
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
        						id: 'CalculateMaintainingRootTabPanel_'+allSecondIds,
        						border: false
        				};
        				panelItem.items.splice(0, 0, secondTabPanel_all);
        			}
        			if(i==0 && panelItem.items.length>0){
        				panelItem.items[0].items=[];
        				panelItem.items[0].items.push(CalculateMaintainingInfoPanel);
    				}
        		}else{
        			panelItem={
        					title: tabInfo.children[i].text,
        					tpl: tabInfo.children[i].text,
        					layout: 'fit',
    						id: 'CalculateMaintainingRootTabPanel_'+tabInfo.children[i].deviceTypeId,
    						iconCls: i==0?'check1':null,
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