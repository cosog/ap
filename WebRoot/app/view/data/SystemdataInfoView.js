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
        		width:'55%',
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
        					newCard.activeTab.add(DictItemGridPanel);
        				}else{
	        				newCard.add(DictItemGridPanel);
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

function createDictItemSourceConfigGridPanelColumn(columnInfo) {
    var myArr = columnInfo;
    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var width_ = "";
        var lock_ = "";
        var hidden_ = "";
        var flex_ = "";
        if (attr.hidden == true) {
            hidden_ = ",hidden:true";
        }
        if (isNotVal(attr.lock)) {
            //lock_ = ",locked:" + attr.lock;
        }
        if (isNotVal(attr.width)) {
            width_ = ",width:" + attr.width;
        }
        if (isNotVal(attr.flex)) {
        	flex_ = ",flex:" + attr.flex;
        }
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' "+width_+flex_;
        if (attr.dataIndex.toUpperCase() == 'id'.toUpperCase()) {
            myColumns += ",xtype: 'rownumberer',sortable : false,locked:false";
        }else {
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

iconDictItemConfig = function(value, e, record) {
	var resultstring="";
	if(record.data.columnDataSource!=0){
		var dataitemid=record.data.dataitemid;
		var name=record.data.name;
		var code=record.data.code;
		var datavalue=record.data.datavalue;
		var sorts=record.data.sorts;
		var columnDataSource=record.data.columnDataSource;
		var deviceType=record.data.deviceType;
		var dataSource=record.data.dataSource;
		var dataUnit=record.data.dataUnit;
		var status=record.data.status;
		var status_cn=record.data.status_cn;
		var status_en=record.data.status_en;
		var status_ru=record.data.status_ru;
		var configItemName=record.data.configItemName;
		var configItemBitIndex=record.data.configItemBitIndex;
		
		
		dataitemid = encodeURIComponent(dataitemid || '');
		name = encodeURIComponent(name || '');
		code = encodeURIComponent(code || '');
		datavalue = encodeURIComponent(datavalue || '');
		sorts = encodeURIComponent(sorts || '');
		columnDataSource = encodeURIComponent(columnDataSource || '');
		deviceType = encodeURIComponent(deviceType || '');
		dataSource = encodeURIComponent(dataSource || '');
		dataUnit = encodeURIComponent(dataUnit || '');
		status = encodeURIComponent(status || '');
		status_cn = encodeURIComponent(status_cn || '');
		status_en = encodeURIComponent(status_en || '');
		status_ru = encodeURIComponent(status_ru || '');
		configItemName = encodeURIComponent(configItemName || '');
		configItemBitIndex = encodeURIComponent(configItemBitIndex || '');
		
		var resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " 
			+"onclick=callBackDictItemConfig(" 
			+"'"+dataitemid+"','"+name+"','"+code+"','"+datavalue+"','"+sorts+"'," 
			+"'"+columnDataSource+"','"+deviceType+"','"+dataSource+"','"+dataUnit+"'," 
			+"'"+status+"','"+status_cn+"','"+status_en+"','"+status_ru+"','"+configItemName+"','"+configItemBitIndex+"')>"
			+loginUserLanguageResource.config+"...</a>";
	}
	return resultstring;
}

var callBackDictItemConfig = function(dataitemid,name,code,datavalue,sorts,columnDataSource,deviceType,dataSource,dataUnit,status,status_cn,status_en,status_ru,configItemName,configItemBitIndex) {
	dataitemid = decodeURIComponent(dataitemid);
	name = decodeURIComponent(name);
	code = decodeURIComponent(code);
	datavalue = decodeURIComponent(datavalue);
	sorts = decodeURIComponent(sorts);
	columnDataSource = decodeURIComponent(columnDataSource);
	deviceType = decodeURIComponent(deviceType);
	dataSource = decodeURIComponent(dataSource);
	dataUnit = decodeURIComponent(dataUnit);
	status = decodeURIComponent(status);
	status_cn = decodeURIComponent(status_cn);
	status_en = decodeURIComponent(status_en);
	status_ru = decodeURIComponent(status_ru);
	configItemName = decodeURIComponent(configItemName);
	configItemBitIndex = decodeURIComponent(configItemBitIndex);
	
	var adddataitemwin = Ext.create("AP.view.data.DataitemsInfoWin", {
        title: loginUserLanguageResource.editDataItem
    });
    adddataitemwin.show();
    adddataitemwin.down('form').getForm().reset();
    
    Ext.getCmp("savettosysfordataFormBtnId").hide();
    Ext.getCmp("oktosysfordataFormBtnId").hide();
    Ext.getCmp("editttosysfordataFormBtnId").show();
    
    Ext.getCmp("dictItemAddOrUpdate_Id").setValue(1);
    
    Ext.getCmp("dictItemDataItemId_Id").setValue(dataitemid);
    Ext.getCmp("sysDataName_zh_CN_Ids").setValue(name);
    Ext.getCmp("sysDataName_en_Ids").setValue(name);
    Ext.getCmp("sysDataName_ru_Ids").setValue(name);
    
    
    
    
    
    Ext.getCmp("sysDataCode_Ids").setValue(code);
    Ext.getCmp("dictItemConfigItemName_Id").setValue(configItemName);
    Ext.getCmp("dictItemConfigItemBitIndex_Id").setValue(configItemBitIndex);
    
    
    
    Ext.getCmp("dictItemColumnDataSourceComb_Id").setValue(columnDataSource);
    Ext.getCmp("dictItemColumnDataSource_Id").setValue(columnDataSource);
    
    Ext.getCmp("dictItemDeviceType_Id").setValue(deviceType);
    
    Ext.getCmp("dictItemDataSourceComb_Id").setValue(dataSource);
    Ext.getCmp("dictItemDataSource_Id").setValue(dataSource);

    Ext.getCmp("dictItemDataUnit_Id").setValue(dataUnit);
    
    Ext.getCmp('dataitemsInfo_status_cn_id').setValue({'dataitemsInfo.status_cn':(status_cn=='true'?1:0)});
    Ext.getCmp('dataitemsInfo_status_en_id').setValue({'dataitemsInfo.status_en':(status_en=='true'?1:0)});
    Ext.getCmp('dataitemsInfo_status_ru_id').setValue({'dataitemsInfo.status_ru':(status_ru=='true'?1:0)});
    
    Ext.getCmp('dataitemsInfo_status_id').setValue({'dataitemsInfo.status':(status=='true'?1:0)});
    
	Ext.getCmp("sysdatasorts_Ids").setValue(sorts);
	Ext.getCmp("sysdatadatavalue_Ids").setValue(datavalue);

	
	if(columnDataSource==1){
        Ext.getCmp("sysDataCode_Ids").hide();
        Ext.getCmp("dictItemDataSourceComb_Id").show();
//        Ext.getCmp("dictItemDataUnit_Id").show();

        Ext.getCmp("sysDataCode_Ids").setConfig({
            allowBlank: true
        });
        Ext.getCmp("dictItemDataSourceComb_Id").setConfig({
            allowBlank: false
        });
        Ext.getCmp("dictItemSelectPanel_Id").enable();
    } else if (columnDataSource == 2) { //字段数据来源为附加信息
        Ext.getCmp("sysDataCode_Ids").hide();
        Ext.getCmp("dictItemDataSourceComb_Id").hide();
//        Ext.getCmp("dictItemDataUnit_Id").hide();

        Ext.getCmp("sysDataCode_Ids").setConfig({
            allowBlank: true
        });
        Ext.getCmp("dictItemDataSourceComb_Id").setConfig({
            allowBlank: true
        });
        Ext.getCmp("dictItemSelectPanel_Id").enable();
    }else{
    	Ext.getCmp("sysDataCode_Ids").show();
        Ext.getCmp("dictItemDataSourceComb_Id").hide();
//        Ext.getCmp("dictItemDataUnit_Id").hide();

        Ext.getCmp("sysDataCode_Ids").setConfig({
            allowBlank: false
        });
        Ext.getCmp("dictItemDataSourceComb_Id").setConfig({
            allowBlank: true
        });
        Ext.getCmp("dictItemSelectPanel_Id").disable();
    }
	
	if(columnDataSource!=0){
		Ext.create('AP.store.data.DictItemSourceStore');
	}
	
    return false;
}