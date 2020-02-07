Ext.define("AP.view.PSToFS.ElectricAcqAndInverInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.electricAcqAndInverInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var PSToFSWellListStore=Ext.create('AP.store.PSToFS.PSToFSWellListStore');
        var jhStore_A = new Ext.data.JsonStore({
            pageSize: defaultJhComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/monitorPumpingUnitParamsManagerController/queryMonitorPUJhh',
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
                    var jh_val = Ext.getCmp('PSToFSAcqAndInversionJHCombo_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        jh: jh_val,
                        type: 'jh',
                        wellType:200
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        
        var simpleCombo_A = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: cosog.string.jh,
                    id: 'PSToFSAcqAndInversionJHCombo_Id',
                    store: jhStore_A,
                    labelWidth: 35,
                    width: 145,
                    queryMode: 'remote',
                    emptyText: cosog.string.all,
                    blankText: cosog.string.all,
                    typeAhead: true,
                    autoSelect: true,
                    allowBlank: true,
                    triggerAction: 'all',
                    editable: true,
                    displayField: "boxval",
                    valueField: "boxkey",
                    pageSize: comboxPagingStatus,
                    minChars: 0,
                    listeners: {
                        select: function (combo, record, index) {
                        	PSToFSWellListStore.load();
                        }
                    }
                });
        
        Ext.apply(me, {
            items: [{
                layout: "border",
                border: false,
                tbar:[simpleCombo_A, "-",{
                    xtype: 'datefield',
                    anchor: '100%',
                    fieldLabel: '',
                    labelWidth: 0,
                    width: 100,
                    format: 'Y-m-d ',
                    id: 'PSToFSAcqAndInversionStartDate_Id',
                    value: '',
                    listeners: {
                    	select: function (combo, record, index) {
                    		var gridPanel = Ext.getCmp("ElectricAcqAndInverDistreteGrid_Id");
                            if (isNotVal(gridPanel)) {
                            	gridPanel.getStore().load();
                            }else{
                            	Ext.create('AP.store.PSToFS.DiscreteDataStore')
                            }
                            
                            var diagramListGridPanel = Ext.getCmp("ElectricAcqAndInverDiagramListGrid_Id");
                            if (isNotVal(diagramListGridPanel)) {
                            	diagramListGridPanel.getStore().load();
                            }else{
                            	Ext.create('AP.store.PSToFS.DiagramDataListStore')
                            }
                        }
                    }
                },{
                    xtype: 'datefield',
                    anchor: '100%',
                    fieldLabel: '至',
                    labelWidth: 15,
                    width: 115,
                    format: 'Y-m-d ',
                    id: 'PSToFSAcqAndInversionEndDate_Id',
                    value: new Date(),
                    listeners: {
                    	select: function (combo, record, index) {
                    		var gridPanel = Ext.getCmp("ElectricAcqAndInverDistreteGrid_Id");
                            if (isNotVal(gridPanel)) {
                            	gridPanel.getStore().load();
                            }else{
                            	Ext.create('AP.store.PSToFS.DiscreteDataStore')
                            }
                            
                            var diagramListGridPanel = Ext.getCmp("ElectricAcqAndInverDiagramListGrid_Id");
                            if (isNotVal(diagramListGridPanel)) {
                            	diagramListGridPanel.getStore().load();
                            }else{
                            	Ext.create('AP.store.PSToFS.DiagramDataListStore')
                            }
                        }
                    }
                }],
                items: [{
                        	region: 'west',
                        	id:'ElectricAcqAndInverWellListPanel',
                        	width:'15%',
                        	layout: 'fit',
                        	title: '井列表',
                        	collapsible: true, // 是否折叠
                    		split: true, // 竖折叠条
                        	border: false,
                        	autoScroll:true,
                            scrollable: true
                    	},{
                    		region: 'center',
                    		id:'ElectricAcqAndInverDiscreteDataPanel',
                    		title: '离散数据',
                    		layout: 'fit',
                    		border: false
                    	},{
                    		region: 'east',
                    		width:'45%',
                    		title: '曲线数据',
                    		collapsible: true, // 是否折叠
                    		split: true, // 竖折叠条
                    		border: false,
                    		layout: "border",
                    		items: [{
                    			region: 'center',
                        		id:'ElectricAcqAndInverDiagramDataPanel',
                        		layout: 'fit',
                        		border: false
                    		},{
                    			region: 'east',
                        		width:'60%',
                        		collapsible: false, // 是否折叠
                        		split: true, // 竖折叠条
                        		border: false,
                        		autoScroll:true,
                                scrollable: true,
                        		layout: {
                                    type: 'vbox',
                                    pack: 'start',
                                    align: 'stretch'
                                },
                                items: [
                                	{
                                		border: false,
                                		margin: '0 0 1 0',
                                		layout: {
                                	        type: 'hbox',
                                	        pack: 'start',
                                	        align: 'stretch'
                                	    },
                                	    items:[{
                                	    	border: true,
                                	    	margin: '0 1 0 0',
                                            flex: 1,
                                            height:300,
                                            html: '<div id=\"inverDiagramDiv1_id\" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    var gridPanel = Ext.getCmp("ElectricAcqAndInverDiagramListGrid_Id");
                                                    if (isNotVal(gridPanel)) {
                                                    	if($("#inverDiagramDiv1_id").highcharts()!=undefined){
                                                			$("#inverDiagramDiv1_id").highcharts().setSize($("#inverDiagramDiv1_id").offsetWidth, $("#inverDiagramDiv1_id").offsetHeight,true);
                                                		}
                                                    }
                                                }
                                            }
                                	    }]
                                	},{
                                		border: false,
                                		layout: {
                                	        type: 'hbox',
                                	        pack: 'start',
                                	        align: 'stretch'
                                	    },
                                	    items:[{
                                	    	border: true, // 泵功图
                                	    	margin: '0 1 0 0',
                                            flex: 1,
                                            height:300,
                                            html: '<div id=\"inverDiagramDiv2_id\" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	var gridPanel = Ext.getCmp("ElectricAcqAndInverDiagramListGrid_Id");
                                                    if (isNotVal(gridPanel)) {
                                                    	if($("#inverDiagramDiv2_id").highcharts()!=undefined){
                                                			$("#inverDiagramDiv2_id").highcharts().setSize($("#inverDiagramDiv2_id").offsetWidth, $("#inverDiagramDiv2_id").offsetHeight,true);
                                                		}
                                                    }
                                                }
                                            }
                                	    }]
                                	},{
                                		border: false,
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
                                            height:300,
                                            html: '<div id=\"inverDiagramDiv3_id\" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	var gridPanel = Ext.getCmp("ElectricAcqAndInverDiagramListGrid_Id");
                                                    if (isNotVal(gridPanel)) {
                                                    	if($("#inverDiagramDiv3_id").highcharts()!=undefined){
                                                			$("#inverDiagramDiv3_id").highcharts().setSize($("#inverDiagramDiv3_id").offsetWidth, $("#inverDiagramDiv3_id").offsetHeight,true);
                                                		}
                                                    }
                                                }
                                            }
                                	    }]
                                	},{
                                		border: false,
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
                                            height:300,
                                            html: '<div id=\"inverDiagramDiv4_id\" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	var gridPanel = Ext.getCmp("ElectricAcqAndInverDiagramListGrid_Id");
                                                    if (isNotVal(gridPanel)) {
                                                    	if($("#inverDiagramDiv4_id").highcharts()!=undefined){
                                                			$("#inverDiagramDiv4_id").highcharts().setSize($("#inverDiagramDiv4_id").offsetWidth, $("#inverDiagramDiv4_id").offsetHeight,true);
                                                		}
                                                    }
                                                }
                                            }
                                	    }]
                                	},{
                                		border: false,
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
                                            height:300,
                                            html: '<div id=\"inverDiagramDiv5_id\" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	var gridPanel = Ext.getCmp("ElectricAcqAndInverDiagramListGrid_Id");
                                                    if (isNotVal(gridPanel)) {
                                                    	if($("#inverDiagramDiv5_id").highcharts()!=undefined){
                                                			$("#inverDiagramDiv5_id").highcharts().setSize($("#inverDiagramDiv5_id").offsetWidth, $("#inverDiagramDiv5_id").offsetHeight,true);
                                                		}
                                                    }
                                                }
                                            }
                                	    }]
                                	}
                                ]
                    		}],
                            listeners: {
                                beforeCollapse: function (panel, eOpts) {
                                    $("#inverDiagramDiv1_id").hide();
                                    $("#inverDiagramDiv2_id").hide();
                                    $("#inverDiagramDiv3_id").hide();
                                    $("#inverDiagramDiv4_id").hide();
                                    $("#inverDiagramDiv5_id").hide();
                                },
                                expand: function (panel, eOpts) {
                                    $("#inverDiagramDiv1_id").show();
                                    $("#inverDiagramDiv2_id").show();
                                    $("#inverDiagramDiv3_id").show();
                                    $("#inverDiagramDiv4_id").show();
                                    $("#inverDiagramDiv5_id").show();
                                }
                            }
                    	}]
                	}]
        });
        me.callParent(arguments);
    }

});

function createElectricAcqAndInverColumn(columnInfo) {
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
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' ";
        if (attr.dataIndex == 'id'||attr.dataIndex == 'jlbh') {
            myColumns += ",width:" + attr.width + ",xtype: 'rownumberer',sortable : false,locked:false";
        }else if (attr.dataIndex == 'cjsj' || attr.dataIndex == 'gtcjsj') {
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

function createElectricInverDiscreteColumn(columnInfo) {
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
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' ";
        if (attr.dataIndex == 'id'||attr.dataIndex == 'jlbh') {
            myColumns += ",width:" + attr.width + ",xtype: 'rownumberer',sortable : false,locked:true";
        }else if (attr.dataIndex == 'cjsj' || attr.dataIndex == 'gtcjsj') {
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