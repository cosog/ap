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
        Ext.apply(me, {
        	items: [{
        		xtype: 'tabpanel',
        		id:"CalculateMaintainingTabPanel",
//        		activeTab: 0,
        		border: false,
        		tabPosition: 'top',
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
        				hidden: !moduleContentConfig.dataMaintaining.FESDiagramResultData,
        				border: false
        			},{
        				title: loginUserLanguageResource.PCPCalculate,
        				id:'PCPCalculateMaintainingInfoPanel_Id',
        				items: [PCPCalculateMaintainingInfoView],
        				layout: "fit",
        				hidden: !moduleContentConfig.dataMaintaining.RPMResultData,
        				border: false
        			}],
        			listeners: {
        				beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
            				oldCard.setIconCls(null);
            				newCard.setIconCls('check3');
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