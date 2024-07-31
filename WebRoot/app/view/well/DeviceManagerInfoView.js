var loginUserDeviceManagerModuleRight=getRoleModuleRight('WellInformation');
Ext.define("AP.view.well.DeviceManagerInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.deviceManagerInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var DeviceInfoPanel = Ext.create('AP.view.well.DeviceInfoPanel');
        var items=[];
        if(tabInfo.children!=undefined && tabInfo.children!=null && tabInfo.children.length>0){
        	for(var i=0;i<tabInfo.children.length;i++){
        		var panelItem={};
        		if(tabInfo.children[i].children!=undefined && tabInfo.children[i].children!=null && tabInfo.children[i].children.length>0){
        			panelItem={
        					title: tabInfo.children[i].text,
        					tpl: tabInfo.children[i].text,
        					xtype: 'tabpanel',
        	        		id: 'DeviceManagerPanel_'+tabInfo.children[i].deviceTypeId,
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
        	        				var DeviceInfoPanel = Ext.create('AP.view.well.DeviceInfoPanel');
        	        				newCard.add(DeviceInfoPanel);
        	        				Ext.getCmp("selectedDeviceType_global").setValue(newCard.id.split('_')[1]); 
        	        				
        	        				Ext.getCmp("DeviceSelectRow_Id").setValue(0);
        	        		    	Ext.getCmp("DeviceSelectEndRow_Id").setValue(0);
        	        				CreateAndLoadDeviceInfoTable(true);
        	        			},
        	        			afterrender: function (panel, eOpts) {
        	        				
        	        			}
        	        		}
        			}
        			
        			var allSecondIds='';
        			for(var j=0;j<tabInfo.children[i].children.length;j++){
        				var secondTabPanel={
        						title:tabInfo.children[i].children[j].text,
        						tpl:tabInfo.children[i].children[j].text,
        						layout: 'fit',
        						iconCls: (panelItem.items.length==1&&j==0)?'check2':null,
        						id: 'DeviceManagerPanel_'+tabInfo.children[i].children[j].deviceTypeId,
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
//        						title: '<div style="color:#000000;font-size:11px;font-family:SimSun">全部</div>',
        						title: '全部',
        						tpl:'全部',
        						iconCls:'check2',
        						layout: 'fit',
        						id: 'RealTimeMonitoringTabPanel_'+allSecondIds,
        						border: false
        				};
//        				panelItem.items.push(secondTabPanel_all);
        				panelItem.items.splice(0, 0, secondTabPanel_all);
        			}
        			if(i==0 && panelItem.items.length>0){
        				panelItem.items[0].items=[];
        				panelItem.items[0].items.push(DeviceInfoPanel);
    				}
        		}else{
        			panelItem={
        					title: tabInfo.children[i].text,
        					tpl: tabInfo.children[i].text,
        					layout: 'fit',
    						id: 'DeviceManagerPanel_'+tabInfo.children[i].deviceTypeId,
    						iconCls: i==0?'check1':null,
    						border: false
        			};
        			if(i==0){
            			panelItem.items=[];
            			panelItem.items.push(DeviceInfoPanel);
            		}
        		}
        		items.push(panelItem);
        	}
        }
        
        
        Ext.apply(me, {
        	tbar:[{
            	id: 'DeviceManagerModuleViewFlag',
            	xtype: 'textfield',
                value: loginUserDeviceManagerModuleRight.viewFlag,
                hidden: true
             },{
            	id: 'DeviceManagerModuleEditFlag',
            	xtype: 'textfield',
                value: loginUserDeviceManagerModuleRight.editFlag,
                hidden: true
             },{
            	id: 'DeviceManagerModuleControlFlag',
            	xtype: 'textfield',
                value: loginUserDeviceManagerModuleRight.controlFlag,
                hidden: true
            }],
            items: [{
        		xtype: 'tabpanel',
        		id:"DeviceManagerTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'bottom',
        		items:items,
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
        				var DeviceInfoPanel = Ext.create('AP.view.well.DeviceInfoPanel');
        				var deviceTypeId=0;
        				if(newCard.xtype=='tabpanel'){
        					newCard.activeTab.add(DeviceInfoPanel);
        					deviceTypeId=newCard.activeTab.id.split('_')[1];
        				}else{
	        				newCard.add(DeviceInfoPanel);
	        				deviceTypeId=newCard.id.split('_')[1];
        				}
        				Ext.getCmp("selectedDeviceType_global").setValue(deviceTypeId); 
        				
        				Ext.getCmp("DeviceSelectRow_Id").setValue(0);
        		    	Ext.getCmp("DeviceSelectEndRow_Id").setValue(0);
						CreateAndLoadDeviceInfoTable(true);
        			}
        		}
            	}],
            	listeners: {
            		afterrender: function (panel, eOpts) {
            			
            		},
        			beforeclose: function ( panel, eOpts) {
        				if(deviceInfoHandsontableHelper!=null){
        					if(deviceInfoHandsontableHelper.hot!=undefined){
        						deviceInfoHandsontableHelper.hot.destroy();
        					}
        					deviceInfoHandsontableHelper=null;
        				}
        				if(productionHandsontableHelper!=null){
        					if(productionHandsontableHelper.hot!=undefined){
        						productionHandsontableHelper.hot.destroy();
        					}
        					productionHandsontableHelper=null;
        				}
        				if(pumpingInfoHandsontableHelper!=null){
        					if(pumpingInfoHandsontableHelper.hot!=undefined){
        						pumpingInfoHandsontableHelper.hot.destroy();
        					}
        					pumpingInfoHandsontableHelper=null;
        				}
        				if (videoInfoHandsontableHelper != null) {
                            if (videoInfoHandsontableHelper.hot != undefined) {
                            	videoInfoHandsontableHelper.hot.destroy();
                            }
                            videoInfoHandsontableHelper = null;
                        }
        				if (deviceAuxiliaryDeviceInfoHandsontableHelper != null) {
                            if (deviceAuxiliaryDeviceInfoHandsontableHelper.hot != undefined) {
                            	deviceAuxiliaryDeviceInfoHandsontableHelper.hot.destroy();
                            }
                            deviceAuxiliaryDeviceInfoHandsontableHelper = null;
                        }
        				if (deviceAdditionalInfoHandsontableHelper != null) {
                            if (deviceAdditionalInfoHandsontableHelper.hot != undefined) {
                            	deviceAdditionalInfoHandsontableHelper.hot.destroy();
                            }
                            deviceAdditionalInfoHandsontableHelper = null;
                        }
        			}
        		}
        });
        me.callParent(arguments);
    }

});