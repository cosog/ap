Ext.define("AP.view.well.DeviceManagerInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.deviceManagerInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
//        var RPCDeviceInfoPanel = Ext.create('AP.view.well.RPCDeviceInfoPanel');
//        var PCPDeviceInfoPanel = Ext.create('AP.view.well.PCPDeviceInfoPanel');
        var DeviceInfoPanel = Ext.create('AP.view.well.DeviceInfoPanel');
        var items=[];
        if(tabInfo.children!=undefined && tabInfo.children!=null && tabInfo.children.length>0){
        	for(var i=0;i<tabInfo.children.length;i++){
        		var panelItem={};
        		if(tabInfo.children[i].children!=undefined && tabInfo.children[i].children!=null && tabInfo.children[i].children.length>0){
        			panelItem.title=tabInfo.children[i].text;
        			panelItem.id='DeviceManagerPanel_'+tabInfo.children[i].tabId;
        			panelItem.xtype='tabpanel';
        			panelItem.activeTab=0;
        			panelItem.border=false;
        			panelItem.tabPosition='left';
        			panelItem.items=[];
        			
        			for(var j=0;j<tabInfo.children[i].children.length;j++){
        				var secondTabPanel={};
        				secondTabPanel.title='<div style="color:#000000;font-size:11px;font-family:SimSun">'+tabInfo.children[i].children[j].text+'</div>';
        				secondTabPanel.layout='fit';
        				secondTabPanel.id='DeviceManagerPanel_'+tabInfo.children[i].children[j].tabId;
        				secondTabPanel.border=false;
            			if(j==0){
            				secondTabPanel.items=[];
            				secondTabPanel.items.push(DeviceInfoPanel);
                		}
            			panelItem.items.push(secondTabPanel);
        			}
        			

//        			panelItem.on('tabchange',function(tabPanel, newCard,oldCard, obj){
//        				oldCard.removeAll();
//        				newCard.add(DeviceInfoPanel);
//        			});
        			
        		}else{
        			panelItem.title=tabInfo.children[i].text;
        			panelItem.layout='fit';
        			panelItem.id='DeviceManagerPanel_'+tabInfo.children[i].tabId;
        			panelItem.border=false;
        			if(i==0){
            			panelItem.items=[];
            			panelItem.items.push(DeviceInfoPanel);
            		}
        		}
        		items.push(panelItem);
        	}
        }
        
        
        Ext.apply(me, {
        	items: [{
        		xtype: 'tabpanel',
        		id:"DeviceManagerTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'bottom',
//        		items: [{
//        				title: '抽油机井',
//        				layout: "fit",
//        				id:'RPCDeviceManagerPanel',
//        				border: false,
//        				items: [RPCDeviceInfoPanel]
//        			},{
//        				title: '螺杆泵井',
//        				id:'PCPDeviceManagerPanel',
//        				layout: "fit",
//        				border: false,
//        				hidden: pcpHidden,
//        				items: [PCPDeviceInfoPanel]
//        			}],
        		items:items,
        		listeners: {
        			tabchange: function (tabPanel, newCard,oldCard, obj) {
        				alert(newCard.id)
//        					Ext.getCmp("bottomTab_Id").setValue(newCard.id); //
//        					if(newCard.id=="RPCDeviceManagerPanel"){
//        						Ext.getCmp("selectedDeviceType_global").setValue(0); 
//        						CreateAndLoadRPCDeviceInfoTable();
//        					}else if(newCard.id=="PCPDeviceManagerPanel"){
//        						Ext.getCmp("selectedDeviceType_global").setValue(1); 
//        						CreateAndLoadPCPDeviceInfoTable();
//        					}
        			}
        		}
            	}],
            	listeners: {
            		afterrender: function (panel, eOpts) {
            			var aa=Ext.getCmp("DeviceManagerTabPanel");
            			alert(aa);
            		},
        			beforeclose: function ( panel, eOpts) {
        				if(deviceInfoHandsontableHelper!=null){
        					if(deviceInfoHandsontableHelper.hot!=undefined){
        						deviceInfoHandsontableHelper.hot.destroy();
        					}
        					deviceInfoHandsontableHelper=null;
        				}
        				if(pumpingModelHandsontableHelper!=null){
        					if(pumpingModelHandsontableHelper.hot!=undefined){
        						pumpingModelHandsontableHelper.hot.destroy();
        					}
        					pumpingModelHandsontableHelper=null;
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
        			},
        			afterrender: function ( panel, eOpts) {}
        		}
        });
        me.callParent(arguments);
    }

});