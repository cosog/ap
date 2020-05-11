Ext.define('AP.view.electricAnalysis.RealtimeCurveCheckInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.electricAnalysisRealtimeCurveCheckInfoView',
//    id: "SingleWellList_Id",
    layout: "fit",
    border: false,
    initComponent: function () {
    	var store =Ext.create('AP.store.electricAnalysis.ElectricRealtimeCurveCheckListStore');
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
                    var wellName = Ext.getCmp('electricAnalysisRealtimeCurveCheckWellCom_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        wellName: wellName,
                        wellType:200
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var wellComboBox = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.wellName,
                id: 'electricAnalysisRealtimeCurveCheckWellCom_Id',
                store: wellListStore,
                labelWidth: 35,
                width: 135,
                queryMode: 'remote',
                emptyText: cosog.string.all,
                blankText: cosog.string.all,
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
                    	wellComboBox.getStore().loadPage(1);
                    },
                    specialkey: function (field, e) {},
                    select: function (combo, record, index) {
                    	if(combo.value==""){
                    		Ext.getCmp("electricAnalysisRealtimeCurveCheckRTBtn_Id").hide();
                    		Ext.getCmp("electricAnalysisRealtimeCurveCheckHisBtn_Id").show();
                    		Ext.getCmp("electricAnalysisRealtimeCurveCheckStartDate_Id").hide();
                    		Ext.getCmp("electricAnalysisRealtimeCurveCheckEndDate_Id").hide();
                    	}else{
                    		Ext.getCmp("electricAnalysisRealtimeCurveCheckRTBtn_Id").show();
                    		Ext.getCmp("electricAnalysisRealtimeCurveCheckHisBtn_Id").hide();
                    		Ext.getCmp("electricAnalysisRealtimeCurveCheckStartDate_Id").show();
                    		Ext.getCmp("electricAnalysisRealtimeCurveCheckEndDate_Id").show();
                    	}
                    	var electricAnalysisRealtimeCurveCheck=Ext.getCmp("ElectricAnalysisRealtimeCurveCheck_Id");
                		if(isNotVal(electricAnalysisRealtimeCurveCheck)){
                			electricAnalysisRealtimeCurveCheck.getStore().loadPage(1);
                		}else{
                			Ext.create('AP.store.electricAnalysis.ElectricRealtimeCurveCheckListStore');
                		}
                    }
                }
            });
        
        
        Ext.apply(this, {
        	tbar: [ // 定义topbar
        		wellComboBox,"-",
                {
                    xtype: 'datefield',
                    anchor: '100%',
                    hidden: true,
                    fieldLabel: '',
                    labelWidth: 0,
                    width: 90,
                    format: 'Y-m-d ',
                    id: 'electricAnalysisRealtimeCurveCheckStartDate_Id',
                    value: '',
                    listeners: {
                    	select: function (combo, record, index) {
                    		var jh = Ext.getCmp('electricAnalysisRealtimeCurveCheckWellCom_Id').getValue();
                    		if(jh!=null&&jh!=''){
                    			var electricAnalysisRealtimeCurveCheck=Ext.getCmp("ElectricAnalysisRealtimeCurveCheck_Id");
                        		if(isNotVal(electricAnalysisRealtimeCurveCheck)){
                        			electricAnalysisRealtimeCurveCheck.getStore().loadPage(1);
                        		}else{
                        			Ext.create('AP.store.electricAnalysis.ElectricRealtimeCurveCheckListStore');
                        		}
                    		}
                        }
                    }
                },{
                    xtype: 'datefield',
                    anchor: '100%',
                    hidden: true,
                    fieldLabel: '至',
                    labelWidth: 15,
                    width: 105,
                    format: 'Y-m-d ',
                    id: 'electricAnalysisRealtimeCurveCheckEndDate_Id',
                    value: new Date(),
                    listeners: {
                    	select: function (combo, record, index) {
                    		var jh = Ext.getCmp('electricAnalysisRealtimeCurveCheckWellCom_Id').getValue();
                    		if(jh!=null&&jh!=''){
                    			var electricAnalysisRealtimeCurveCheck=Ext.getCmp("ElectricAnalysisRealtimeCurveCheck_Id");
                        		if(isNotVal(electricAnalysisRealtimeCurveCheck)){
                        			electricAnalysisRealtimeCurveCheck.getStore().loadPage(1);
                        		}else{
                        			Ext.create('AP.store.electricAnalysis.ElectricRealtimeCurveCheckListStore');
                        		}
                    		}
                        }
                    }
                }, '->', {
                    xtype: 'button',
                    text:'单井历史',
                    tooltip:'点击按钮或者双击表格，查看单井历史数据',
                    id:'electricAnalysisRealtimeCurveCheckHisBtn_Id',
                    pressed: true,
                    hidden: false,
                    handler: function (v, o) {
                    	if(Ext.getCmp("ElectricAnalysisRealtimeCurveCheck_Id").getSelectionModel().getSelection().length>0){
                			Ext.getCmp("electricAnalysisRealtimeCurveCheckRTBtn_Id").show();
                        	Ext.getCmp("electricAnalysisRealtimeCurveCheckHisBtn_Id").hide();
                            Ext.getCmp("electricAnalysisRealtimeCurveCheckStartDate_Id").show();
                    		Ext.getCmp("electricAnalysisRealtimeCurveCheckEndDate_Id").show();
                    		var record  = Ext.getCmp("ElectricAnalysisRealtimeCurveCheck_Id").getSelectionModel().getSelection()[0];
                    		Ext.getCmp('electricAnalysisRealtimeCurveCheckWellCom_Id').setValue(record.data.jh);
                        	Ext.getCmp('electricAnalysisRealtimeCurveCheckWellCom_Id').setRawValue(record.data.jh);
                        	Ext.getCmp('ElectricAnalysisRealtimeCurveCheck_Id').getStore().loadPage(1);
                		}
                    }
                }, {
                  xtype: 'button',
                  text:'返回实时',
                  id:'electricAnalysisRealtimeCurveCheckRTBtn_Id',
                  pressed: true,
                  hidden: true,
                  handler: function (v, o) {
                	  Ext.getCmp("electricAnalysisRealtimeCurveCheckWellCom_Id").setValue('');
                      Ext.getCmp("electricAnalysisRealtimeCurveCheckWellCom_Id").setRawValue('');
                      Ext.getCmp("electricAnalysisRealtimeCurveCheckRTBtn_Id").hide();
                      Ext.getCmp("electricAnalysisRealtimeCurveCheckHisBtn_Id").show();
                      Ext.getCmp("electricAnalysisRealtimeCurveCheckStartDate_Id").hide();
              		  Ext.getCmp("electricAnalysisRealtimeCurveCheckEndDate_Id").hide();
              		  
              		  Ext.getCmp('ElectricAnalysisRealtimeCurveCheck_Id').getStore().loadPage(1);
                  }
            },"-",{
                xtype: 'button',
                text: '导出Excel',
                pressed: true,
                handler: function (v, o) {
                	if(Ext.getCmp("ElectricAnalysisRealtimeCurveCheck_Id").getSelectionModel().getSelection().length>0){
                		var record  = Ext.getCmp("ElectricAnalysisRealtimeCurveCheck_Id").getSelectionModel().getSelection()[0];
                		var recordId=record.data.id;
                    	var wellName = Ext.getCmp("electricAnalysisRealtimeCurveCheckWellCom_Id").getValue();
                    	var url=context + '/PSToFSController/exportSingleElecInverDiagramCheckData?wellName='+URLencode(URLencode(wellName))+'&recordId='+recordId;
                    	document.location.href = url;
            		}
                }
            }],
            items: {
                xtype: 'tabpanel',
                id:'electricAnalysisRealtimeCurveCheckTabpanel_Id',
                activeTab: 0,
                border: true,
                header: false,
                collapsible: true, // 是否折叠
                split: true, // 竖折叠条
                tabPosition: 'bottom',
                items: [{
                	title:'反演曲线',
                	id: 'electricAnalysisRealtimeCurveCheckPanel_Id',
                    border: false,
                    layout: 'border',
                    items:[{
                    	region: 'west',
                    	width:390,
                        id:'electricAnalysisRealtimeCurveCheckTablePanel_Id',
                        layout: 'fit',
                        border: false,
                        header: false,
                        collapsible: true, // 是否可折叠
                        collapsed:false,//是否折叠
                        split: true // 竖折叠条
                    },{
                    	region: 'center',
                    	layout: {
                            type: 'vbox',
                            pack: 'start',
                            align: 'stretch'
                        },
                        autoScroll:true,
                        scrollable: true,
                        items: [
                        	{
                        		border: false,
                        		margin: '0 0 0 0',
                        		flex: 1,
//                        		height:250,
                        		layout: {
                        	        type: 'hbox',
                        	        pack: 'start',
                        	        align: 'stretch'
                        	    },
                        	    items:[{
                        	    	border: true,
                        	    	margin: '0 0 0 0',
                                    flex: 1,
                                    html: '<div id=\"electricAnalysisRealtimeCurveCheckInverDiv1_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisRealtimeCurveCheckInverDiv1_id").highcharts()!=undefined){
                                    			$("#electricAnalysisRealtimeCurveCheckInverDiv1_id").highcharts().setSize($("#electricAnalysisRealtimeCurveCheckInverDiv1_id").offsetWidth, $("#electricAnalysisRealtimeCurveCheckInverDiv1_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    },{
                        	    	border: true,
                        	    	margin: '0 1 0 0',
                                    flex: 1,
                                    html: '<div id=\"electricAnalysisRealtimeCurveCheckInverDiv2_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisRealtimeCurveCheckInverDiv2_id").highcharts()!=undefined){
                                    			$("#electricAnalysisRealtimeCurveCheckInverDiv2_id").highcharts().setSize($("#electricAnalysisRealtimeCurveCheckInverDiv2_id").offsetWidth, $("#electricAnalysisRealtimeCurveCheckInverDiv2_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    }]
                        	},{
                        		border: false,
                        		flex: 1,
//                        		height:250,
                        		margin: '1 0 0 0',
                        		layout: {
                        	        type: 'hbox',
                        	        pack: 'start',
                        	        align: 'stretch'
                        	    },
                        	    items:[{
                        	    	border: true,
                        	    	margin: '0 1 0 0',
                                    flex: 1,
                                    html: '<div id=\"electricAnalysisRealtimeCurveCheckInverDiv3_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisRealtimeCurveCheckInverDiv3_id").highcharts()!=undefined){
                                    			$("#electricAnalysisRealtimeCurveCheckInverDiv3_id").highcharts().setSize($("#electricAnalysisRealtimeCurveCheckInverDiv3_id").offsetWidth, $("#electricAnalysisRealtimeCurveCheckInverDiv3_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    }]
                        	}
                        ]
                    }]
                }]
            }
        });
        this.callParent(arguments);
        
    }
});

function createRealtimeElecCurveAnalysisCheckTableColumn(columnInfo) {
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
        if (isNotVal(attr.flex)) {
            width_ = ",flex:" + attr.flex;
        }
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' ";
        if (attr.dataIndex == 'id'||attr.dataIndex == 'jlbh') {
            myColumns += ",width:" + attr.width + ",xtype: 'rownumberer',sortable : false,locked:false";
        }else if (attr.dataIndex.toUpperCase()=='acquisitionTime'.toUpperCase()) {
            myColumns += width_ + ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}";
        } else {
            myColumns += hidden_ + lock_ + width_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
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

function getAndInitInverDiagramCheck(recordId,wellName){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/PSToFSController/getSingleElecInverDiagramCheckData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			
//			
			var positionCurveData=result.positionCurveData.split(",");
			if(result.positionCurveData!=undefined && result.positionCurveData!="" && result.positionCurveData.split(",").length>0){
				showFSDiagramWithAtrokeSPM(result,"electricAnalysisRealtimeCurveCheckInverDiv1_id","地面功图-反演");
				showFSDiagram360WithAtrokeSPM(result,"electricAnalysisRealtimeCurveCheckInverDiv2_id","地面功图(360点)-反演");
				showAngleLoadContinuousDiagram(result,"electricAnalysisRealtimeCurveCheckInverDiv3_id");
			}else{
            	$("#electricAnalysisRealtimeCurveCheckInverDiv1_id").html('');
            	$("#electricAnalysisRealtimeCurveCheckInverDiv2_id").html('');
            	$("#electricAnalysisRealtimeCurveCheckInverDiv3_id").html('');
            }
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			recordId: recordId,
			wellName:wellName
        }
	});
}