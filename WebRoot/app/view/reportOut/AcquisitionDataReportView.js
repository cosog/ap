Ext.define("AP.view.reportOut.AcquisitionDataReportView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.acquisitionDataReportView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var AcquisitionDataReportPanel = Ext.create('AP.view.reportOut.AcquisitionDataReportPanel');
        
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
        	        		id: 'AcquisitionDataReportRootTabPanel_'+tabInfo.children[i].deviceTypeId,
        	        		activeTab: i==firstActiveTab?secondActiveTab:0,
        	        		border: false,
        	        		tabPosition: 'left',
        	        		iconCls: i==firstActiveTab?'check1':null,
        	        		items:[],
        	        		listeners: {
        	        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        	        				if(oldCard!=undefined){
        	        					oldCard.setIconCls(null);
        	        					oldCard.removeAll();
        	                	    }
        	                	    if(newCard!=undefined){
        	                	    	newCard.setIconCls('check2');		
        	                	    }
        	        			},
        	        			tabchange: function (tabPanel, newCard,oldCard, obj) {
        	        				var AcquisitionDataReportPanel = Ext.create('AP.view.reportOut.AcquisitionDataReportPanel');
        	        				newCard.add(AcquisitionDataReportPanel);
        	        				
        	        				acquisitionDataReportRefresh();
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
//        						iconCls: (panelItem.items.length==1&&j==0)?'check2':null,
        						id: 'AcquisitionDataReportRootTabPanel_'+tabInfo.children[i].children[j].deviceTypeId,
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
        						id: 'AcquisitionDataReportRootTabPanel_'+allSecondIds,
        						border: false
        				};
        				panelItem.items.splice(0, 0, secondTabPanel_all);
        			}
        			if(i==firstActiveTab && panelItem.items.length>secondActiveTab){
        				panelItem.items[secondActiveTab].iconCls='check2';
        			}
        			if(i==firstActiveTab && panelItem.items.length>secondActiveTab){
        				panelItem.items[secondActiveTab].items=[];
        				panelItem.items[secondActiveTab].items.push(AcquisitionDataReportPanel);
    				}
        		}else{
        			panelItem={
        					title: tabInfo.children[i].text,
        					tpl: tabInfo.children[i].text,
        					layout: 'fit',
        					iconCls: i==firstActiveTab?'check1':null,
    						id: 'AcquisitionDataReportRootTabPanel_'+tabInfo.children[i].deviceTypeId,
    						border: false
        			};
        			if(i==firstActiveTab){
            			panelItem.items=[];
            			panelItem.items.push(AcquisitionDataReportPanel);
            		}
        		}
        		items.push(panelItem);
        	}
        }
        
        
        Ext.apply(me, {
            items: [{
                xtype: 'tabpanel',
                id:'AcquisitionDataReportRootTabPanel',
                activeTab: firstActiveTab,
                border: false,
                tabPosition: 'bottom',
                items: items,
                listeners: {
                	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
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
    					
    					var AcquisitionDataReportPanel = Ext.create('AP.view.reportOut.AcquisitionDataReportPanel');
        				if(newCard.xtype=='tabpanel'){
        					newCard.activeTab.add(AcquisitionDataReportPanel);
        				}else{
	        				newCard.add(AcquisitionDataReportPanel);
        				}
        				
        				acquisitionDataReportRefresh();
                    },
                    delay: 500
                }
       }]
        });
        me.callParent(arguments);
    }
});

function acquisitionDataReportRefresh(){
	var deviceType=getDeviceTypeFromTabId("AcquisitionDataReportRootTabPanel");
	Ext.getCmp('selectedDeviceType_global').setValue(deviceType);
	
	var secondActiveId = Ext.getCmp("AcquisitionDataReportTabPanel").getActiveTab().id;
	if(secondActiveId=="SingleWellAcquisitionDataReportTabPanel_Id"){
		Ext.getCmp('AcquisitionDataReportPanelDeviceListCombo_Id').setRawValue('');
		Ext.getCmp('AcquisitionDataReportPanelDeviceListCombo_Id').setValue('');
		var gridPanel = Ext.getCmp("SingleWellAcquisitionDataReportDeviceListGridPanel_Id");
		if (isNotVal(gridPanel)) {
			gridPanel.getStore().load();
		}else{
			Ext.create('AP.store.reportOut.SingleWellAcquisitionDataReportWellListStore');
		}
	}else if(secondActiveId=="ProductionAcquisitionDataReportTabPanel_Id"){
		CreateProductionAcquisitionDataReportTable();
		CreateProductionAcquisitionDataReportCurve();
	}
}

function createSingleWellAcquisitionDataReportWellListDataColumn(columnInfo) {
    var myArr = columnInfo;

    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var width_ = "";
        var lock_ = "";
        var hidden_ = "";
        if (attr.hidden == true) {
            hidden_ = ",hidden:true";
        }
        if (isNotVal(attr.lock)) {
            //lock_ = ",locked:" + attr.lock;
        }
        if (isNotVal(attr.width)) {
            width_ = ",width:" + attr.width;
        }
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' "+width_;
        if (attr.dataIndex == 'id') {
            myColumns += ",xtype: 'rownumberer',sortable : false,locked:false";
        }else if (attr.dataIndex.toUpperCase()=='commStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceCommStatusColor(value,o,p,e);}";
        }else if (attr.dataIndex.toUpperCase()=='slave'.toUpperCase()) {
            myColumns += ",sortable : false,locked:true,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
        } else if (attr.dataIndex.toUpperCase() == 'acqTime'.toUpperCase()) {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}";
        } else {
            myColumns += hidden_ + lock_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};