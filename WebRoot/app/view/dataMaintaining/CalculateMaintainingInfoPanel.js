Ext.define("AP.view.dataMaintaining.CalculateMaintainingInfoPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.CalculateMaintainingInfoPanel',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var SRPCalculateMaintainingInfoView = Ext.create('AP.view.dataMaintaining.SRPCalculateMaintainingInfoView');
        var PCPCalculateMaintainingInfoView = Ext.create('AP.view.dataMaintaining.PCPCalculateMaintainingInfoView');
        var AcquisitionDataMaintainingInfoView = Ext.create('AP.view.dataMaintaining.AcquisitionDataMaintainingInfoView');
        
        var wellListStore = new Ext.data.JsonStore({
            pageSize: defaultWellComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/wellInformationManagerController/loadWellComboxList',
                type: "ajax",
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json',
                    rootProperty: 'list',
                    totalProperty: 'totals'
                }
            },
            autoLoad: true,
            listeners: {
                beforeload: function (store, options) {
                    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var deviceName = Ext.getCmp('DataMaintainingDeviceListComBox_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceName: deviceName,
                        deviceType: getDeviceTypeFromTabId("CalculateMaintainingRootTabPanel")
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var wellListComb = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: loginUserLanguageResource.deviceName,
                    id: 'DataMaintainingDeviceListComBox_Id',
                    store: wellListStore,
                    labelWidth: getLabelWidth(loginUserLanguageResource.deviceName,loginUserLanguage),
                    width: (getLabelWidth(loginUserLanguageResource.deviceName,loginUserLanguage)+110),
                    queryMode: 'remote',
                    emptyText: '--'+loginUserLanguageResource.all+'--',
                    blankText: '--'+loginUserLanguageResource.all+'--',
                    typeAhead: true,
                    autoSelect: false,
                    allowBlank: true,
                    triggerAction: 'all',
                    editable: true,
                    displayField: "boxval",
                    valueField: "boxkey",
                    pageSize: comboxPagingStatus,
                    minChars: 0,
                    listeners: {
                    	expand: function (sm, selections) {
                    		wellListComb.getStore().loadPage(1);
                        },
                        select: function (combo, record, index) {
                			var gridPanel = Ext.getCmp("AcquisitionDataMaintainingDeviceListGridPanel_Id");
            				if (isNotVal(gridPanel)) {
            					gridPanel.getStore().load();
            				}else{
            					Ext.create('AP.store.dataMaintaining.AcquisitionDataMaintainingWellListStore');
            				}
                        }
                    }
                });
        
        Ext.apply(me, {
        	layout: 'border',
            border: false,
        	items: [{
        		region: 'west',
            	width: '30%',
            	title: loginUserLanguageResource.deviceList,
            	id: 'DataMaintainingDeviceListPanel_Id',
            	collapsible: true, // 是否可折叠
                collapsed:false,//是否折叠
                split: true, // 竖折叠条
            	layout: "fit",
            	tbar:[{
                    id: 'DataMaintainingDeviceListSelectRow_Id',
                    xtype: 'textfield',
                    value: -1,
                    hidden: true
                },{
                    xtype: 'button',
                    text: loginUserLanguageResource.refresh,
                    iconCls: 'note-refresh',
                    hidden:false,
                    handler: function (v, o) {
                    	refreshCalculateMaintainingData();
                    }
        		},'-',wellListComb]
        	},{
        		region: 'center',
        		xtype: 'tabpanel',
        		id:"CalculateMaintainingTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'bottom',
        		items: [{
        			title: loginUserLanguageResource.acquisitionData,
        			id:'AcquisitionDataMaintainingInfoPanel_Id',
        			items: [AcquisitionDataMaintainingInfoView],
        			iconCls: 'check3',
        			layout: "fit",
        			border: false
        		},{
        			title: loginUserLanguageResource.SRPCalculate,
        			id:'SRPCalculateMaintainingInfoPanel_Id',
        			items: [SRPCalculateMaintainingInfoView],
        			layout: "fit",
//        			hidden: !moduleContentConfig.dataMaintaining.FESDiagramResultData,
        			border: false
        		},{
        			title: loginUserLanguageResource.PCPCalculate,
        			id:'PCPCalculateMaintainingInfoPanel_Id',
        			items: [PCPCalculateMaintainingInfoView],
        			layout: "fit",
//        			hidden: !moduleContentConfig.dataMaintaining.RPMResultData,
        			border: false
        		}],
        		listeners: {
        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        				if(oldCard!=undefined){
                    		oldCard.setIconCls(null);
                    	}
        				if(newCard!=undefined){
        					newCard.setIconCls('check3');				
                    	}
            		},
            		tabchange: function (tabPanel, newCard,oldCard, obj) {
        				Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
        				if(newCard.id=="AcquisitionDataMaintainingInfoPanel_Id"){
        					refreshAcquisitionDataMaintainingData();
        				}else if(newCard.id=="SRPCalculateMaintainingInfoPanel_Id"){
        					refreshSRPCalculateMaintainingData();
        				}else if(newCard.id=="PCPCalculateMaintainingInfoPanel_Id"){
        					refreshPCPCalculateMaintainingData();
        				}
        			}
        		}
            }]
        });
        me.callParent(arguments);
    }
});

function createCalculateManagerWellListColumn(columnInfo) {
    var myArr = columnInfo;

    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var width_ = "";
        var flex_ = "";
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
        if (isNotVal(attr.flex)) {
        	flex_ = ",flex:" + attr.flex;
        }
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' "+width_+flex_;
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
            //        	myColumns += hidden_ + lock_ + width_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "'";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};

function createTotalCalculateMaintainingDataColumn(columnInfo) {
    var myArr = columnInfo;

    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var width_ = "";
        var lock_ = "";
        var hidden_ = "";
        var flex_="";
        if (attr.hidden == true) {
            hidden_ = ",hidden:true";
        }
        if (isNotVal(attr.lock)) {
            //lock_ = ",locked:" + attr.lock;
        }
        if (isNotVal(attr.width)) {
            width_ = ",width:" + attr.width;
        }
        if (isNotVal(attr.flex_)) {
        	flex_ = ",flex:" + attr.flex_;
        }else{
        	if (!isNotVal(attr.width)) {
        		flex_ = ",flex:1";
            }
        }
        
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' "+width_+flex_;
        if (attr.dataIndex == 'id') {
            myColumns += ",xtype: 'rownumberer',sortable : false,locked:false";
        }else if (attr.dataIndex.toUpperCase()=='commStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceCommStatusColor(value,o,p,e);}";
        }else if (attr.dataIndex.toUpperCase()=='slave'.toUpperCase()) {
            myColumns += ",sortable : false,locked:true,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
        } else if (attr.dataIndex.toUpperCase() == 'calDate'.toUpperCase()) {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}";
        } else {
            myColumns += hidden_ + lock_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
            //        	myColumns += hidden_ + lock_ + width_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "'";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};