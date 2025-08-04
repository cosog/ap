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
        	        		id: 'DeviceManagerPanel_'+tabInfo.children[i].deviceTypeId,
        	        		activeTab: i==firstActiveTab?secondActiveTab:0,
        	        		border: false,
        	        		tabPosition: 'left',
        	        		iconCls: i==firstActiveTab?'check1':null,
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
        	        				deviceManagerDataRefresh();
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
//        						iconCls: (panelItem.items.length==1&&j==0)?'check2':null,
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
        						title: loginUserLanguageResource.all,
        						tpl:loginUserLanguageResource.all,
//        						iconCls:'check2',
        						layout: 'fit',
        						id: 'DeviceManagerPanel_'+allSecondIds,
        						border: false
        				};
        				panelItem.items.splice(0, 0, secondTabPanel_all);
        			}
        			if(i==firstActiveTab && panelItem.items.length>secondActiveTab){
        				panelItem.items[secondActiveTab].iconCls='check2';
        			}
        			if(i==firstActiveTab && panelItem.items.length>secondActiveTab){
        				panelItem.items[secondActiveTab].items=[];
        				panelItem.items[secondActiveTab].items.push(DeviceInfoPanel);
    				}
        		}else{
        			panelItem={
        					title: tabInfo.children[i].text,
        					tpl: tabInfo.children[i].text,
        					layout: 'fit',
    						id: 'DeviceManagerPanel_'+tabInfo.children[i].deviceTypeId,
    						iconCls: i==firstActiveTab?'check1':null,
    						border: false
        			};
        			if(i==firstActiveTab){
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
        		activeTab: firstActiveTab,
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
        				
        				if(newCard.xtype=='tabpanel'){
        					newCard.activeTab.add(DeviceInfoPanel);
        				}else{
	        				newCard.add(DeviceInfoPanel);
        				}
        				deviceManagerDataRefresh();
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

function deviceManagerDataRefresh(){
	var deviceType=getDeviceTypeFromTabId("DeviceManagerTabPanel");
	Ext.getCmp("selectedDeviceType_global").setValue(deviceType); 
	var firstDeviceType=getDeviceTypeFromTabId_first("DeviceManagerTabPanel");
	Ext.getCmp("selectedFirstDeviceType_global").setValue(firstDeviceType); 
	
	Ext.getCmp("DeviceSelectRow_Id").setValue(0);
	Ext.getCmp("DeviceSelectEndRow_Id").setValue(0);
	CreateAndLoadDeviceInfoTable(true);
}

function exportDeviceCompleteData(){
	var url = context + '/wellInformationManagerController/exportDeviceCompleteData';
	
	var timestamp=new Date().getTime();
	var key='exportWellInformationData'+'_'+timestamp;
	var maskPanelId='DeviceTablePanel_id';
	
	
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.primaryDdeviceExportFileName)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
//    openExcelWindow(url + '?flag=true' + param);
    downloadFile(url + '?flag=true' + param);
}