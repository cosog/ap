var loginUserAuxiliaryDeviceManagerModuleRight=getRoleModuleRight('AuxiliaryDeviceManager');
Ext.define("AP.view.well.AuxiliaryDeviceInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.auxiliaryDeviceInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var AuxiliaryDeviceInfoPanel = Ext.create('AP.view.well.AuxiliaryDeviceInfoPanel');
        var items=[];
        if(tabInfo.children!=undefined && tabInfo.children!=null && tabInfo.children.length>0){
        	for(var i=0;i<tabInfo.children.length;i++){
        		if(tabInfo.children[i].parentId==1){
        			var panelItem={};
        			panelItem={
        					title: tabInfo.children[i].text,
        					tpl: tabInfo.children[i].text,
        					layout: 'fit',
    						id: 'AuxiliaryDeviceManagerTabPanel_'+tabInfo.children[i].deviceTypeId,
    						border: false
        			};
        			if(i==0){
        				panelItem.iconCls='check2';
        				panelItem.items=[];
            			panelItem.items.push(AuxiliaryDeviceInfoPanel);
            		}
        			items.push(panelItem);
        		}
        	}
        }
        
        Ext.apply(me, {
        	tbar:[{
            	id: 'AuxiliaryDeviceManagerModuleViewFlag',
            	xtype: 'textfield',
                value: loginUserAuxiliaryDeviceManagerModuleRight.viewFlag,
                hidden: true
             },{
            	id: 'AuxiliaryDeviceManagerModuleEditFlag',
            	xtype: 'textfield',
                value: loginUserAuxiliaryDeviceManagerModuleRight.editFlag,
                hidden: true
             },{
            	id: 'AuxiliaryDeviceManagerModuleControlFlag',
            	xtype: 'textfield',
                value: loginUserAuxiliaryDeviceManagerModuleRight.controlFlag,
                hidden: true
            }],
            items: [{
        		xtype: 'tabpanel',
        		id:"AuxiliaryDeviceManagerTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'bottom',
        		items:items,
        		listeners: {
        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        				oldCard.setIconCls(null);
        				newCard.setIconCls('check2');
        				if(oldCard.xtype=='tabpanel'){
        					oldCard.activeTab.removeAll();
        				}else{
        					oldCard.removeAll();
        				}
        			},
        			tabchange: function (tabPanel, newCard,oldCard, obj) {
        				Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
        				var AuxiliaryDeviceInfoPanel = Ext.create('AP.view.well.AuxiliaryDeviceInfoPanel');
        				newCard.add(AuxiliaryDeviceInfoPanel);
        				
        				
        				var firstDeviceType=getDeviceTypeFromTabId_first("AuxiliaryDeviceManagerTabPanel");
        				Ext.getCmp("selectedFirstDeviceType_global").setValue(firstDeviceType); 
        				CreateAndLoadAuxiliaryDeviceInfoTable(true);
        			}
        		}
            	}],
            	listeners: {
            		afterrender: function (panel, eOpts) {
            			
            		},
        			beforeclose: function ( panel, eOpts) {
        				if (auxiliaryDeviceInfoHandsontableHelper != null) {
                            if (auxiliaryDeviceInfoHandsontableHelper.hot != undefined) {
                                auxiliaryDeviceInfoHandsontableHelper.hot.destroy();
                            }
                            auxiliaryDeviceInfoHandsontableHelper = null;
                        }
        			}
        		}
        });
        me.callParent(arguments);
    }
});

function exportAuxiliaryDeviceCompleteData(){
	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=getDeviceTypeFromTabId("AuxiliaryDeviceManagerTabPanel");
    var deviceTypeName=getTabPanelActiveName("AuxiliaryDeviceManagerTabPanel");
    
    var fileName=loginUserLanguageResource.auxiliaryDdeviceExportFileName;
    var title=fileName;
	
	var url = context + '/wellInformationManagerController/exportAuxiliaryDeviceCompleteData';
	
	var timestamp=new Date().getTime();
	var key='exportAuxiliaryDeviceCompleteData'+deviceType+'_'+timestamp;
	var maskPanelId='AuxiliaryDeviceInfoPanel_Id';
	
	
	var param = "&orgId=" + leftOrg_Id 
    + "&deviceType="+deviceType
    + "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(fileName)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
//    openExcelWindow(url + '?flag=true' + param);
    downloadFile(url + '?flag=true' + param);
}