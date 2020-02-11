Ext.define('AP.view.electricAnalysis.RealtimeCurveInfoView', {//曲线分析
    extend: 'Ext.panel.Panel',
    alias: 'widget.electricAnalysisRealtimeCurveInfoView',
//    id: "SingleWellList_Id",
    layout: "fit",
    border: false,
    initComponent: function () {
    	var store =Ext.create('AP.store.electricAnalysis.ElectricRealtimeCurveAnalysisListStore');
        var wellComboBoxStore = new Ext.data.JsonStore({
            pageSize: defaultJhComboxSize,
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
                    var wellName = Ext.getCmp('electricAnalysisRealtimeCurveWellName_Id').getValue();
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
                id: 'electricAnalysisRealtimeCurveWellName_Id',
                store: wellComboBoxStore,
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
                    expand: function (sm, selections) {},
                    specialkey: function (field, e) {},
                    select: function (combo, record, index) {
                    	if(combo.value==""){
                    		Ext.getCmp("electricAnalysisRealtimeCurveRTBtn_Id").hide();
                    		Ext.getCmp("electricAnalysisRealtimeCurveHisBtn_Id").show();
                    		Ext.getCmp("electricAnalysisRealtimeCurveStartDate_Id").hide();
                    		Ext.getCmp("electricAnalysisRealtimeCurveEndDate_Id").hide();
                    	}else{
                    		Ext.getCmp("electricAnalysisRealtimeCurveRTBtn_Id").show();
                    		Ext.getCmp("electricAnalysisRealtimeCurveHisBtn_Id").hide();
                    		Ext.getCmp("electricAnalysisRealtimeCurveStartDate_Id").show();
                    		Ext.getCmp("electricAnalysisRealtimeCurveEndDate_Id").show();
                    	}
                    	var ElectricAnalysisRealtimeCurve=Ext.getCmp("ElectricAnalysisRealtimeCurve_Id");
                		if(isNotVal(ElectricAnalysisRealtimeCurve)){
                			ElectricAnalysisRealtimeCurve.getStore().loadPage(1);
                		}else{
                			Ext.create('AP.store.electricAnalysis.ElectricRealtimeCurveAnalysisListStore');
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
                    id: 'electricAnalysisRealtimeCurveStartDate_Id',
                    value: '',
                    listeners: {
                    	select: function (combo, record, index) {
                    		var wellName = Ext.getCmp('electricAnalysisRealtimeCurveWellName_Id').getValue();
                    		if(wellName!=null&&wellName!=''){
                    			var ElectricAnalysisRealtimeCurve=Ext.getCmp("ElectricAnalysisRealtimeCurve_Id");
                        		if(isNotVal(ElectricAnalysisRealtimeCurve)){
                        			ElectricAnalysisRealtimeCurve.getStore().loadPage(1);
                        		}else{
                        			Ext.create('AP.store.electricAnalysis.ElectricRealtimeCurveAnalysisListStore');
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
                    id: 'electricAnalysisRealtimeCurveEndDate_Id',
                    value: new Date(),
                    listeners: {
                    	select: function (combo, record, index) {
                    		var wellName = Ext.getCmp('electricAnalysisRealtimeCurveWellName_Id').getValue();
                    		if(wellName!=null&&wellName!=''){
                    			var ElectricAnalysisRealtimeCurve=Ext.getCmp("ElectricAnalysisRealtimeCurve_Id");
                        		if(isNotVal(ElectricAnalysisRealtimeCurve)){
                        			ElectricAnalysisRealtimeCurve.getStore().loadPage(1);
                        		}else{
                        			Ext.create('AP.store.electricAnalysis.ElectricRealtimeCurveAnalysisListStore');
                        		}
                    		}
                        }
                    }
                }, '->', {
                    xtype: 'button',
                    text:'查看历史',
                    tooltip:'点击按钮或者双击表格，查看单井历史数据',
                    id:'electricAnalysisRealtimeCurveHisBtn_Id',
                    pressed: true,
                    hidden: false,
                    handler: function (v, o) {
                    	if(Ext.getCmp("ElectricAnalysisRealtimeCurve_Id").getSelectionModel().getSelection().length>0){
                			Ext.getCmp("electricAnalysisRealtimeCurveRTBtn_Id").show();
                        	Ext.getCmp("electricAnalysisRealtimeCurveHisBtn_Id").hide();
                            Ext.getCmp("electricAnalysisRealtimeCurveStartDate_Id").show();
                    		Ext.getCmp("electricAnalysisRealtimeCurveEndDate_Id").show();
                    		var record  = Ext.getCmp("ElectricAnalysisRealtimeCurve_Id").getSelectionModel().getSelection()[0];
                    		Ext.getCmp('electricAnalysisRealtimeCurveWellName_Id').setValue(record.data.jh);
                        	Ext.getCmp('electricAnalysisRealtimeCurveWellName_Id').setRawValue(record.data.jh);
                        	Ext.getCmp('ElectricAnalysisRealtimeCurve_Id').getStore().loadPage(1);
                		}
                    }
                }, {
                  xtype: 'button',
                  text:'返回实时',
                  id:'electricAnalysisRealtimeCurveRTBtn_Id',
                  pressed: true,
                  hidden: true,
                  handler: function (v, o) {
                	  Ext.getCmp("electricAnalysisRealtimeCurveWellName_Id").setValue('');
                      Ext.getCmp("electricAnalysisRealtimeCurveWellName_Id").setRawValue('');
                      Ext.getCmp("electricAnalysisRealtimeCurveRTBtn_Id").hide();
                      Ext.getCmp("electricAnalysisRealtimeCurveHisBtn_Id").show();
                      Ext.getCmp("electricAnalysisRealtimeCurveStartDate_Id").hide();
              		  Ext.getCmp("electricAnalysisRealtimeCurveEndDate_Id").hide();
              		  
              		  Ext.getCmp('ElectricAnalysisRealtimeCurve_Id').getStore().loadPage(1);
                  }
            },"-",{
                xtype: 'button',
                text:'重新计算',
                id:'electricAnalysisRealtimeCurveReInverBtn_Id',
                pressed: true,
                hidden: false,
                handler: function (v, o) {
                	var gridPanel = Ext.getCmp("ElectricAnalysisRealtimeCurve_Id");
                    var model = gridPanel.getSelectionModel();
                    var record = model.getSelection();
                    if (record.length>0) {
                    	
                    	var diagramIdArr = [];
                    	Ext.Array.each(record, function(name, index, countriesItSelf) {
                    		diagramIdArr.push(record[index].get("id"));
                    	});
                    	var diagramIds = "" + diagramIdArr.join(",");
                    	var wellName = Ext.getCmp("electricAnalysisRealtimeCurveWellName_Id").getValue();
                    	Ext.Ajax.request({
                    		url : context + '/PSToFSController/reInverDiagram',
                    		method : "POST",
                    		params : {
                    			diagramIds : diagramIds,
                    			wellName:wellName
                    		},
                    		success : function(response) {
                    			var result = Ext.JSON.decode(response.responseText);
    	            			if (result.success) {
    	                        	Ext.MessageBox.alert("信息","重新计算"+result.totalCount+"条数据，成功："+result.successCount+"，失败："+result.defaultCount);
//    	                        	Ext.getCmp('ElectricAnalysisRealtimeCurve_Id').getStore().loadPage(1);
    	                        	var wellName = Ext.getCmp("electricAnalysisRealtimeCurveJh_Id").getValue();
    	                        	getAndInitInverDiagram(record[0].data.id,wellName);
    	                        } else {
    	                        	Ext.MessageBox.alert("信息","计算失败");

    	                        }
                    		},
                    		failure : function() {
                    			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
                    		}
                    	});
                    	
                    	
                    }else{
                    	Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
                    }
                }
          }],
            items: {
                xtype: 'tabpanel',
                id:'electricAnalysisRealtimeInverDiagramTabpanel_Id',
                activeTab: 0,
                border: true,
                header: false,
                collapsible: true, // 是否折叠
                split: true, // 竖折叠条
                tabPosition: 'bottom',
                items: [{
                	title:'曲线反演',
                	id: 'electricAnalysisRealtimeInverDiagramPanel_Id',
                    border: false,
                    layout: 'border',
                    items:[{
                    	region: 'west',
                    	width:390,
                        id:'electricRealtimeCurveAnalysisListTable_Id',
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
//                        		flex: 1,
                        		height:250,
                        		layout: {
                        	        type: 'hbox',
                        	        pack: 'start',
                        	        align: 'stretch'
                        	    },
                        	    items:[{
                        	    	border: true,
                        	    	margin: '0 0 0 0',
                                    flex: 1,
                                    html: '<div id=\"electricAnalysisRealtimeCurveInverDiv1_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisRealtimeCurveInverDiv1_id").highcharts()!=undefined){
                                    			$("#electricAnalysisRealtimeCurveInverDiv1_id").highcharts().setSize($("#electricAnalysisRealtimeCurveInverDiv1_id").offsetWidth, $("#electricAnalysisRealtimeCurveInverDiv1_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    },{
                        	    	border: true,
                        	    	margin: '0 1 0 0',
                                    flex: 1,
                                    html: '<div id=\"electricAnalysisRealtimeCurveInverDiv2_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisRealtimeCurveInverDiv2_id").highcharts()!=undefined){
                                    			$("#electricAnalysisRealtimeCurveInverDiv2_id").highcharts().setSize($("#electricAnalysisRealtimeCurveInverDiv2_id").offsetWidth, $("#electricAnalysisRealtimeCurveInverDiv2_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    },{
                        	    	border: true,
                        	    	margin: '0 1 0 0',
                                    flex: 1,
                                    html: '<div id=\"electricAnalysisRealtimeCurveInverDiv3_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisRealtimeCurveInverDiv3_id").highcharts()!=undefined){
                                    			$("#electricAnalysisRealtimeCurveInverDiv3_id").highcharts().setSize($("#electricAnalysisRealtimeCurveInverDiv3_id").offsetWidth, $("#electricAnalysisRealtimeCurveInverDiv3_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    }]
                        	},{
                        		border: false,
//                        		flex: 1,
                        		height:250,
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
                                    html: '<div id=\"electricAnalysisRealtimeCurveInverDiv4_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisRealtimeCurveInverDiv4_id").highcharts()!=undefined){
                                    			$("#electricAnalysisRealtimeCurveInverDiv4_id").highcharts().setSize($("#electricAnalysisRealtimeCurveInverDiv4_id").offsetWidth, $("#electricAnalysisRealtimeCurveInverDiv4_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    },{
                        	    	border: true,
                        	    	margin: '0 1 0 0',
                                    flex: 1,
                                    html: '<div id=\"electricAnalysisRealtimeCurveInverDiv5_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisRealtimeCurveInverDiv5_id").highcharts()!=undefined){
                                    			$("#electricAnalysisRealtimeCurveInverDiv5_id").highcharts().setSize($("#electricAnalysisRealtimeCurveInverDiv5_id").offsetWidth, $("#electricAnalysisRealtimeCurveInverDiv5_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    },{
                        	    	border: true,
                                    flex: 1,
                                    html: '<div id=\"electricAnalysisRealtimeCurveInverDiv6_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisRealtimeCurveInverDiv6_id").highcharts()!=undefined){
                                    			$("#electricAnalysisRealtimeCurveInverDiv6_id").highcharts().setSize($("#electricAnalysisRealtimeCurveInverDiv6_id").offsetWidth, $("#electricAnalysisRealtimeCurveInverDiv6_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    }]
                        	},{
                        		border: false,
//                        		flex: 1,
                        		height:250,
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
                                    html: '<div id=\"electricAnalysisRealtimeCurveInverDiv7_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisRealtimeCurveInverDiv7_id").highcharts()!=undefined){
                                    			$("#electricAnalysisRealtimeCurveInverDiv7_id").highcharts().setSize($("#electricAnalysisRealtimeCurveInverDiv7_id").offsetWidth, $("#electricAnalysisRealtimeCurveInverDiv7_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    },{
                        	    	border: true,
                        	    	margin: '0 1 0 0',
                                    flex: 1,
                                    html: '<div id=\"electricAnalysisRealtimeCurveInverDiv8_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisRealtimeCurveInverDiv8_id").highcharts()!=undefined){
                                    			$("#electricAnalysisRealtimeCurveInverDiv8_id").highcharts().setSize($("#electricAnalysisRealtimeCurveInverDiv8_id").offsetWidth, $("#electricAnalysisRealtimeCurveInverDiv8_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    },{
                        	    	border: true,
                                    flex: 1,
                                    html: '<div id=\"electricAnalysisRealtimeCurveInverDiv9_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisRealtimeCurveInverDiv9_id").highcharts()!=undefined){
                                    			$("#electricAnalysisRealtimeCurveInverDiv9_id").highcharts().setSize($("#electricAnalysisRealtimeCurveInverDiv9_id").offsetWidth, $("#electricAnalysisRealtimeCurveInverDiv9_id").offsetHeight,true);
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

function createRealtimeElecCurveAnalysisTableColumn(columnInfo) {
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

function getAndInitInverDiagram(recordId,wellName){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/PSToFSController/getSingleElecCurveData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			
			
			showPContinuousDiagram(result.powerCurveData,"功率过滤后曲线",result.wellName+' ['+result.acquisitionTime+']','<span style="text-align:center;">点数<br />',"有功功率(kW)",'#FF6633',"electricAnalysisRealtimeCurveInverDiv5_id");
			showPContinuousDiagram(result.powerCurveData_raw,"功率过滤前曲线",result.wellName+' ['+result.acquisitionTime+']','<span style="text-align:center;">点数<br />',"有功功率(kW)",'#FF6633',"electricAnalysisRealtimeCurveInverDiv6_id");
			
			showPContinuousDiagram(result.currentCurveData,"电流过滤后曲线",result.wellName+' ['+result.acquisitionTime+']','<span style="text-align:center;">点数<br />',"电流(A)",'#CC0000',"electricAnalysisRealtimeCurveInverDiv8_id");
			showPContinuousDiagram(result.currentCurveData_raw,"电流过滤前曲线",result.wellName+' ['+result.acquisitionTime+']','<span style="text-align:center;">点数<br />',"电流(A)",'#CC0000',"electricAnalysisRealtimeCurveInverDiv9_id");

//			
			showPContinuousDiagram(result.rpmCurveData,"转速过滤后曲线",result.wellName+' ['+result.acquisitionTime+']','<span style="text-align:center;">点数<br />',"转速(r/min)",'#e3cc19',"electricAnalysisRealtimeCurveInverDiv2_id");
			showPContinuousDiagram(result.rpmCurveData_raw,"转速过滤前曲线",result.wellName+' ['+result.acquisitionTime+']','<span style="text-align:center;">点数<br />',"转速(r/min)",'#e3cc19',"electricAnalysisRealtimeCurveInverDiv3_id");
//			
			var positionCurveData=result.positionCurveData.split(",");
			if(result.positionCurveData!=undefined && result.positionCurveData!="" && result.positionCurveData.split(",").length>0){
				showFSDiagramWithAtrokeSPM(result,"electricAnalysisRealtimeCurveInverDiv1_id");
    			showPSDiagram(result,"electricAnalysisRealtimeCurveInverDiv4_id");
    			showASDiagram(result,"electricAnalysisRealtimeCurveInverDiv7_id");
			}else{
            	$("#electricAnalysisRealtimeCurveInverDiv1_id").html('');
            	$("#electricAnalysisRealtimeCurveInverDiv4_id").html('');
            	$("#electricAnalysisRealtimeCurveInverDiv7_id").html('');
            }
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id: recordId,
			wellName:wellName
        }
	});
}