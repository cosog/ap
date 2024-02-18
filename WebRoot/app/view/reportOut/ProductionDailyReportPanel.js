var productionDailyReportHelper=null
Ext.define("AP.view.reportOut.RPCProductionDailyReportPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.RPCProductionDailyReportPanel',
    layout: 'fit',
    id: 'RPCProductionDailyReportPanel_view',
    border: false,
    initComponent: function () {
        var me = this;
        var wellListCombStore = new Ext.data.JsonStore({
        	pageSize:defaultWellComboxSize,
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
                    var wellName = Ext.getCmp('RPCProductionDailyReportPanelWellListCombo_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceType: 0,
                        wellName: wellName
                    };
                    Ext.apply(store.proxy.extraParams,new_params);
                }
            }
        });
        var wellListCombo = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.wellName,
                id: 'RPCProductionDailyReportPanelWellListCombo_Id',
                hidden:true,
                store: wellListCombStore,
                labelWidth: 35,
                width: 145,
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
                pageSize:comboxPagingStatus,
                minChars:0,
                listeners: {
                    expand: function (sm, selections) {
                        wellListCombo.getStore().loadPage(1); // 加载井下拉框的store
                    },
                    specialkey: function (field, e) {
                        onEnterKeyDownFN(field, e, 'RPCProductionDailyReportPanel_Id');
                    },
                    select: function (combo, record, index) {
                    	CreateRPCProductionDailyReportTable();
                    	CreateRPCProductionDailyReportCurve();
                    }
                }
            });
        Ext.apply(me, {
            tbar: [{
                xtype: 'button',
                text: cosog.string.refresh,
                iconCls: 'note-refresh',
                hidden:false,
                handler: function (v, o) {
                	var gridPanel = Ext.getCmp("RPCProductionDailyReportGridPanel_Id");
        			if (isNotVal(gridPanel)) {
        				gridPanel.getStore().load();
        			}else{
        				Ext.create('AP.store.reportOut.RPCProductionDailyReportInstanceListStore');
        			}
                }
    		},'-',wellListCombo, {
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: '日期',
                labelWidth: 36,
                width: 136,
                format: 'Y-m-d',
                id: 'RPCProductionDailyReportStartDate_Id',
//                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                        try {
                        	Ext.getCmp("RPCProductionDailyReportDate_Id").setValue("");
                        	Ext.getCmp("RPCProductionDailyReportDate_Id").setRawValue("");
                        	CreateRPCProductionDailyReportTable();
                        	CreateRPCProductionDailyReportCurve();
                        } catch (ex) {
                            Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                        }
                    }
                }
            },{
                xtype: 'datefield',
                anchor: '100%',
                hidden: false,
                fieldLabel: '至',
                labelWidth: 15,
                width: 115,
                format: 'Y-m-d ',
                id: 'RPCProductionDailyReportEndDate_Id',
                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                        try {
                        	Ext.getCmp("RPCProductionDailyReportDate_Id").setValue("");
                        	Ext.getCmp("RPCProductionDailyReportDate_Id").setRawValue("");
                        	CreateRPCProductionDailyReportTable();
                        	CreateRPCProductionDailyReportCurve();
                        } catch (ex) {
                            Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                        }
                    }
                }
            },'-',{
                xtype: 'button',
                text: cosog.string.search,
                iconCls: 'search',
                hidden:false,
                handler: function (v, o) {
                	Ext.getCmp("RPCProductionDailyReportDate_Id").setValue("");
                	Ext.getCmp("RPCProductionDailyReportDate_Id").setRawValue("");
                	CreateRPCProductionDailyReportTable();
                	CreateRPCProductionDailyReportCurve();
                }
    		},{
            	id: 'RPCProductionDailyReportInstanceListSelectRow_Id',
            	xtype: 'textfield',
                value: -1,
                hidden: true
            }],
            layout: 'border',
            border: false,
            items: [{
            	region: 'west',
            	width: '20%',
            	title: '报表实例列表',
            	id: 'RPCProductionDailyReportInstanceListPanel_Id',
            	collapsible: true, // 是否可折叠
                collapsed:false,//是否折叠
                split: true, // 竖折叠条
            	layout: "fit"
            },{
            	region: 'center',
            	xtype: 'tabpanel',
            	id:"RPCProductionReportTabPanel_Id",
                activeTab: 0,
                border: false,
                tabPosition: 'top',
                items: [{
                	id:'RPCProductionRangeReportTabPanel_id',
                	title:'日报表',
                	layout:'border',
                	border: false,
                	items:[{
                		region:'north',
                		height:'50%',
                		title:'报表曲线',
                		collapsible: true, // 是否可折叠
                        collapsed:false,//是否折叠
                        split: true, // 竖折叠条
                        border: false,
                        id:'RPCProductionDailyReportCurvePanel_id',
                        html: '<div id="RPCProductionDailyReportCurveDiv_Id" style="width:100%;height:100%;"></div>',
                        listeners: {
                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                if ($("#RPCProductionDailyReportCurveDiv_Id").highcharts() != undefined) {
                                	highchartsResize("RPCProductionDailyReportCurveDiv_Id");
                                }
                            }
                        }
                	},{
                		region: 'center',
                		title:'报表数据',
                        layout: "fit",
                    	id:'RPCProductionDailyReportPanel_id',
                        border: false,
                        tbar:[{
                            xtype: 'button',
                            text: '前一天',
                            iconCls: 'forward',
                            id:'RPCProductionDailyReportForwardBtn_Id',
                            handler: function (v, o) {
                            	var str = Ext.getCmp("RPCProductionDailyReportDate_Id").rawValue;
                            	var startDate = new Date(Date.parse(str .replace(/-/g, '/')));
                            	var day=-1;
                            	var value = startDate.getTime();
                            	value += day * (24 * 3600 * 1000);
                            	var endDate = new Date(value);
                            	Ext.getCmp("RPCProductionDailyReportDate_Id").setValue(endDate);
                            	CreateRPCProductionDailyReportTable();
                            }
                        },'-',{
                            xtype: 'datefield',
                            anchor: '100%',
                            hidden: false,
                            editable:false,
                            readOnly:true,
                            width: 90,
                            format: 'Y-m-d ',
                            id: 'RPCProductionDailyReportDate_Id',
                            listeners: {
                            	change ( thisField, newValue, oldValue, eOpts )  {
                            		var startDateStr=Ext.getCmp("RPCProductionDailyReportStartDate_Id").rawValue;
                            		var endDateStr=Ext.getCmp("RPCProductionDailyReportEndDate_Id").rawValue;
                            		var reportDateStr=Ext.getCmp("RPCProductionDailyReportDate_Id").rawValue;
                            		
                            		var startDate = new Date(Date.parse(startDateStr .replace(/-/g, '/'))).getTime();
                            		var endDate = new Date(Date.parse(endDateStr .replace(/-/g, '/'))).getTime();
                            		var reportDate = new Date(Date.parse(reportDateStr .replace(/-/g, '/'))).getTime();
                            		
                            		
                            		if(reportDate>startDate){
                            			Ext.getCmp("RPCProductionDailyReportForwardBtn_Id").enable();
                            		}else{
                            			Ext.getCmp("RPCProductionDailyReportForwardBtn_Id").disable();
                            		}
                            		
                            		if(reportDate<endDate){
                            			Ext.getCmp("RPCProductionDailyReportBackwardsBtn_Id").enable();
                            		}else{
                            			Ext.getCmp("RPCProductionDailyReportBackwardsBtn_Id").disable();
                            		}
                            	}
                            }
                        },'-',{
                            xtype: 'button',
                            text: '后一天',
                            id:'RPCProductionDailyReportBackwardsBtn_Id',
                            iconCls: 'backwards',
                            handler: function (v, o) {
                            	var str = Ext.getCmp("RPCProductionDailyReportDate_Id").rawValue;
                            	var startDate = new Date(Date.parse(str .replace(/-/g, '/')));
                            	var day=1;
                            	var value = startDate .getTime();
                            	value += day * (24 * 3600 * 1000);
                            	var endDate = new Date(value);
                            	Ext.getCmp("RPCProductionDailyReportDate_Id").setValue(endDate);
                            	CreateRPCProductionDailyReportTable();
                            }
                        },'->', {
                            xtype: 'button',
                            text: cosog.string.exportExcel,
                            iconCls: 'export',
                            handler: function (v, o) {
                            	var timestamp=new Date().getTime();
                            	var key='exportRPCProductionDailyReportData'+timestamp;
                            	
                            	var selectedOrgName="";
                            	var selectedOrgId="";
                            	var IframeViewStore = Ext.getCmp("IframeView_Id").getStore();
                            	var count=IframeViewStore.getCount();
                            	var IframeViewSelection = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
                            	if (IframeViewSelection.length > 0) {
                            		selectedOrgName=IframeViewSelection[0].data.text;
                            		selectedOrgId=IframeViewSelection[0].data.orgId;
                            	} else {
                            		if(count>0){
                            			selectedOrgName=IframeViewStore.getAt(0).data.text;
                            			selectedOrgId=IframeViewStore.getAt(0).data.orgId;
                            		}
                            	}
                            	
                            	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                                var startDate = Ext.getCmp('RPCProductionDailyReportStartDate_Id').rawValue;
                                var endDate = Ext.getCmp('RPCProductionDailyReportEndDate_Id').rawValue;
                                var reportDate = Ext.getCmp('RPCProductionDailyReportDate_Id').rawValue;
                                
                                var wellName='';
                                var unitId=0;
                                var instanceCode='';
                                var selectRow= Ext.getCmp("RPCProductionDailyReportInstanceListSelectRow_Id").getValue();
                                if(selectRow>=0){
                                	instanceCode=Ext.getCmp("RPCProductionDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.instanceCode;
                                	unitId=Ext.getCmp("RPCProductionDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.unitId;
                                }
                            	
                            	var url=context + '/reportDataMamagerController/exportProductionDailyReportData?deviceType=0'
                            	+'&reportType=1'
                            	+'&wellName='+URLencode(URLencode(wellName))
                            	+'&selectedOrgName='+URLencode(URLencode(selectedOrgName))
                            	+'&instanceCode='+instanceCode
                            	+'&unitId='+unitId
                            	+'&startDate='+startDate
                            	+'&endDate='+endDate
                            	+'&reportDate='+reportDate
                            	+'&orgId='+orgId
                            	+'&key='+key;
                            	exportDataMask(key,"RPCProductionDailyReportPanel_view",cosog.string.loading);
                            	document.location.href = url;
                            }
                        },'-',{
                            xtype: 'button',
                            text: '批量导出',
                            iconCls: 'export',
                            handler: function (v, o) {
                            	var timestamp=new Date().getTime();
                            	var key='batchExportRPCProductionDailyReportData'+timestamp;
                            	
                            	var selectedOrgName="";
                            	var selectedOrgId="";
                            	var IframeViewStore = Ext.getCmp("IframeView_Id").getStore();
                            	var count=IframeViewStore.getCount();
                            	var IframeViewSelection = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
                            	if (IframeViewSelection.length > 0) {
                            		selectedOrgName=IframeViewSelection[0].data.text;
                            		selectedOrgId=IframeViewSelection[0].data.orgId;
                            	} else {
                            		if(count>0){
                            			selectedOrgName=IframeViewStore.getAt(0).data.text;
                            			selectedOrgId=IframeViewStore.getAt(0).data.orgId;
                            		}
                            	}
                            	
                            	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                                var startDate = Ext.getCmp('RPCProductionDailyReportStartDate_Id').rawValue;
                                var endDate = Ext.getCmp('RPCProductionDailyReportEndDate_Id').rawValue;
                                var reportDate = Ext.getCmp('RPCProductionDailyReportDate_Id').rawValue;
                                
                                var wellName='';
                                var unitId=0;
                                var instanceCode='';
                                var selectRow= Ext.getCmp("RPCProductionDailyReportInstanceListSelectRow_Id").getValue();
                                if(selectRow>=0){
                                	instanceCode=Ext.getCmp("RPCProductionDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.instanceCode;
                                	unitId=Ext.getCmp("RPCProductionDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.unitId;
                                }
                            	
                            	var url=context + '/reportDataMamagerController/batchExportProductionDailyReportData?deviceType=0'
                            	+'&reportType=1'
                            	+'&wellName='+URLencode(URLencode(wellName))
                            	+'&selectedOrgName='+URLencode(URLencode(selectedOrgName))
                            	+'&instanceCode='+instanceCode
                            	+'&unitId='+unitId
                            	+'&startDate='+startDate
                            	+'&endDate='+endDate
                            	+'&reportDate='+reportDate
                            	+'&orgId='+orgId
                            	+'&key='+key;
                            	exportDataMask(key,"RPCProductionDailyReportPanel_view",cosog.string.loading);
                            	document.location.href = url;
                            }
                        },'-', {
                            xtype: 'button',
                            text: cosog.string.save,
                            iconCls: 'save',
                            disabled: loginUserRoleReportEdit!=1,
                            handler: function (v, o) {
                            	productionDailyReportHelper.saveData();
                            }
                        },'-', {
                            id: 'RPCProductionDailyReportTotalCount_Id',
                            xtype: 'component',
                            tpl: cosog.string.totalCount + ': {count}',
                            style: 'margin-right:15px'
                        }],
                        html:'<div class="RPCProductionDailyReportContainer" style="width:100%;height:100%;"><div class="con" id="RPCProductionDailyReportDiv_id"></div></div>',
                        listeners: {
                        	resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        		if(productionDailyReportHelper!=null && productionDailyReportHelper.hot!=undefined){
//                        			productionDailyReportHelper.hot.refreshDimensions();
                        			var newWidth=width;
                            		var newHeight=height-22-1;//减去工具条高度
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		productionDailyReportHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                        	}
                        }
                	}]
                }],
                listeners: {
                    tabchange: function (tabPanel, newCard, oldCard, obj) {
                    	
                    }
                }
            }]

        });
        me.callParent(arguments);
    }
});

function CreateRPCProductionDailyReportTable(){
	var selectedOrgName="";
	var selectedOrgId="";
	var IframeViewStore = Ext.getCmp("IframeView_Id").getStore();
	var count=IframeViewStore.getCount();
	var IframeViewSelection = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
	if (IframeViewSelection.length > 0) {
//		selectedOrgName=foreachAndSearchOrgAbsolutePath(IframeViewStore.data.items,IframeViewSelection[0].data.orgId);
		selectedOrgName=IframeViewSelection[0].data.text;
		selectedOrgId=IframeViewSelection[0].data.orgId;
	} else {
		if(count>0){
			selectedOrgName=IframeViewStore.getAt(0).data.text;
			selectedOrgId=IframeViewStore.getAt(0).data.orgId;
		}
	}
	
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var startDate = Ext.getCmp('RPCProductionDailyReportStartDate_Id').rawValue;
    var endDate = Ext.getCmp('RPCProductionDailyReportEndDate_Id').rawValue;
    var reportDate = Ext.getCmp('RPCProductionDailyReportDate_Id').rawValue;
    
    var wellName='';
    var unitId=0;
    var instanceCode='';
    var selectRow= Ext.getCmp("RPCProductionDailyReportInstanceListSelectRow_Id").getValue();
    if(selectRow>=0){
    	instanceCode=Ext.getCmp("RPCProductionDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.instanceCode;
    	unitId=Ext.getCmp("RPCProductionDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.unitId;
    }
    
    Ext.getCmp("RPCProductionDailyReportPanel_id").el.mask(cosog.string.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reportDataMamagerController/getProductionDailyReportData',
		success:function(response) {
			Ext.getCmp("RPCProductionDailyReportPanel_id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			
			var startDate=Ext.getCmp('RPCProductionDailyReportStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(result.startDate);
            }
            var endDate=Ext.getCmp('RPCProductionDailyReportEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(result.endDate);
            }
            var reportDate = Ext.getCmp('RPCProductionDailyReportDate_Id');
            if(reportDate.rawValue==''||null==reportDate.rawValue){
            	reportDate.setValue(result.reportDate);
            }
			if(productionDailyReportHelper!=null){
				if(productionDailyReportHelper.hot!=undefined){
					productionDailyReportHelper.hot.destroy();
				}
				productionDailyReportHelper=null;
			}
			if(result.success){
				if(productionDailyReportHelper==null || productionDailyReportHelper.hot==undefined){
					productionDailyReportHelper = ProductionDailyReportHelper.createNew("RPCProductionDailyReportDiv_id","RPCProductionDailyReportContainer",result.template,result.data,result.statData,result.columns);
					productionDailyReportHelper.createTable();
				}
			}else{
				$("#RPCProductionDailyReportDiv_id").html('');
			}
			
			Ext.getCmp("RPCProductionDailyReportTotalCount_Id").update({count: result.data.length});
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId: orgId,
			instanceCode:instanceCode,
			unitId:unitId,
			wellName: wellName,
			selectedOrgName: selectedOrgName,
			startDate: startDate,
			endDate: endDate,
			reportDate: reportDate,
			reportType: 1,
            deviceType:0
        }
	});
};


var ProductionDailyReportHelper = {
	    createNew: function (divId, containerid,templateData,contentData,statData,columns) {
	        var productionDailyReportHelper = {};
	        productionDailyReportHelper.templateData=templateData;
	        productionDailyReportHelper.contentData=contentData;
	        productionDailyReportHelper.statData=statData;
	        productionDailyReportHelper.columns=columns;
	        productionDailyReportHelper.get_data = {};
	        productionDailyReportHelper.data=[];
	        productionDailyReportHelper.sourceData=[];
	        productionDailyReportHelper.hot = '';
	        productionDailyReportHelper.container = document.getElementById(divId);
	        productionDailyReportHelper.columnCount=0;
	        productionDailyReportHelper.editData={};
	        productionDailyReportHelper.contentUpdateList = [];
	        
	        productionDailyReportHelper.initData=function(){
	        	productionDailyReportHelper.data=[];
	        	for(var i=0;i<productionDailyReportHelper.templateData.header.length;i++){
	        		productionDailyReportHelper.templateData.header[i].title.push('');
	        		productionDailyReportHelper.columnCount=productionDailyReportHelper.templateData.header[i].title.length;
	        		
	        		var valueArr=[];
	        		var sourceValueArr=[];
	        		for(var j=0;j<productionDailyReportHelper.templateData.header[i].title.length;j++){
	        			valueArr.push(productionDailyReportHelper.templateData.header[i].title[j]);
	        			sourceValueArr.push(productionDailyReportHelper.templateData.header[i].title[j]);
	        		}
	        		
	        		productionDailyReportHelper.data.push(valueArr);
	        		productionDailyReportHelper.sourceData.push(sourceValueArr);
		        }
	        	for(var i=0;i<productionDailyReportHelper.contentData.length;i++){
	        		var valueArr=[];
	        		var sourceValueArr=[];
	        		for(var j=0;j<productionDailyReportHelper.contentData[i].length;j++){
	        			valueArr.push(productionDailyReportHelper.contentData[i][j]);
	        			sourceValueArr.push(productionDailyReportHelper.contentData[i][j]);
	        		}
	        		
	        		productionDailyReportHelper.data.push(valueArr);
		        	productionDailyReportHelper.sourceData.push(sourceValueArr);
		        }
	        	
	        	for(var i=0;i<productionDailyReportHelper.statData.length;i++){
	        		var valueArr=[];
	        		var sourceValueArr=[];
	        		for(var j=0;j<productionDailyReportHelper.statData[i].length;j++){
	        			valueArr.push(productionDailyReportHelper.statData[i][j]);
	        			sourceValueArr.push(productionDailyReportHelper.statData[i][j]);
	        		}
	        		
	        		productionDailyReportHelper.data.push(valueArr);
		        	productionDailyReportHelper.sourceData.push(sourceValueArr);
		        }
	        	
	        	
	        	
	        	for(var i=productionDailyReportHelper.templateData.header.length;i<productionDailyReportHelper.data.length;i++){
	        		for(var j=0;j<productionDailyReportHelper.data[i].length;j++){
	        			var editable=false
	        			for(var k=0;k<productionDailyReportHelper.templateData.editable.length;k++){
	        				if( i>=productionDailyReportHelper.templateData.editable[k].startRow 
	                				&& i<=productionDailyReportHelper.templateData.editable[k].endRow
	                				&& j>=productionDailyReportHelper.templateData.editable[k].startColumn 
	                				&& j<=productionDailyReportHelper.templateData.editable[k].endColumn
	                		){
	        					editable=true;
	        					break;
	                		}
	        			}
	        			
	        			var value=productionDailyReportHelper.data[i][j];
		                if((!editable)&&value.length>12){
		                	value=value.substring(0, 11)+"...";
		                	productionDailyReportHelper.data[i][j]=value;
		                }
	        		}
	        	}
	        }
	        
	        productionDailyReportHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(productionDailyReportHelper!=null && productionDailyReportHelper.hot!=null){
	        		for(var i=0;i<productionDailyReportHelper.templateData.header.length;i++){
		        		if(row==i){
		        			if(isNotVal(productionDailyReportHelper.templateData.header[i].tdStyle)){
		        				if(isNotVal(productionDailyReportHelper.templateData.header[i].tdStyle.fontWeight)){
		        					td.style.fontWeight = productionDailyReportHelper.templateData.header[i].tdStyle.fontWeight;
		        				}
		        				if(isNotVal(productionDailyReportHelper.templateData.header[i].tdStyle.fontSize)){
		        					td.style.fontSize = productionDailyReportHelper.templateData.header[i].tdStyle.fontSize;
		        				}
		        				if(isNotVal(productionDailyReportHelper.templateData.header[i].tdStyle.fontFamily)){
		        					td.style.fontFamily = productionDailyReportHelper.templateData.header[i].tdStyle.fontFamily;
		        				}
		        				if(isNotVal(productionDailyReportHelper.templateData.header[i].tdStyle.height)){
		        					td.style.height = productionDailyReportHelper.templateData.header[i].tdStyle.height;
		        				}
		        				if(isNotVal(productionDailyReportHelper.templateData.header[i].tdStyle.color)){
		        					td.style.color = productionDailyReportHelper.templateData.header[i].tdStyle.color;
		        				}
		        				if(isNotVal(productionDailyReportHelper.templateData.header[i].tdStyle.backgroundColor)){
		        					td.style.backgroundColor = productionDailyReportHelper.templateData.header[i].tdStyle.backgroundColor;
		        				}
		        				if(isNotVal(productionDailyReportHelper.templateData.header[i].tdStyle.textAlign)){
		        					td.style.textAlign = productionDailyReportHelper.templateData.header[i].tdStyle.textAlign;
		        				}
		        			}
		        			break;
		        		}
		        	}
	        		
	        	}
	        }
	        
	        productionDailyReportHelper.addEditableColor = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.color='#ff0000';    
	        }
	        
	        productionDailyReportHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	            if (row <= 2&&row>=1) {
	                td.style.fontWeight = 'bold';
					td.style.fontSize = '13px';
					td.style.color = 'rgb(0, 0, 51)';
					td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	            }
	        }
			
			productionDailyReportHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (row < 1) {
	                td.style.fontWeight = 'bold';
			        td.style.fontSize = '25px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '50px';   
			    }      
	        }
			
			productionDailyReportHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
		         if(row < 3){
	                 td.style.fontWeight = 'bold';
			         td.style.fontSize = '5px';
			         td.style.fontFamily = 'SimHei';
	            }      
	        }
			

	        productionDailyReportHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        productionDailyReportHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(216, 228, 188)';
	        }
	        
	        productionDailyReportHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        productionDailyReportHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        productionDailyReportHelper.createTable = function () {
	            productionDailyReportHelper.container.innerHTML = "";
	            productionDailyReportHelper.hot = new Handsontable(productionDailyReportHelper.container, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: productionDailyReportHelper.data,
	            	hiddenColumns: {
	                    columns: [productionDailyReportHelper.columnCount-1],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
//	            	columns:productionDailyReportHelper.columns,
	            	fixedRowsTop:productionDailyReportHelper.templateData.fixedRowsTop, 
	                fixedRowsBottom: productionDailyReportHelper.templateData.fixedRowsBottom,
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: productionDailyReportHelper.templateData.rowHeights,
					colWidths: productionDailyReportHelper.templateData.columnWidths,
					rowHeaders: false, //显示行头
//					rowHeaders(index) {
//					    return 'Row ' + (index + 1);
//					},
					colHeaders: false, //显示列头
//					colHeaders(index) {
//					    return 'Col ' + (index + 1);
//					},
					stretchH: 'all',
					columnSorting: true, //允许排序
	                allowInsertRow:false,
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                mergeCells: productionDailyReportHelper.templateData.mergeCells,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.renderer = productionDailyReportHelper.addStyle;
	                    cellProperties.readOnly = true;
	                    if(productionDailyReportHelper.templateData.editable!=null && productionDailyReportHelper.templateData.editable.length>0){
	                    	for(var i=0;i<productionDailyReportHelper.templateData.editable.length;i++){
	                    		if( row>=productionDailyReportHelper.templateData.editable[i].startRow 
	                    				&& row<=productionDailyReportHelper.templateData.editable[i].endRow
	                    				&& col>=productionDailyReportHelper.templateData.editable[i].startColumn 
	                    				&& col<=productionDailyReportHelper.templateData.editable[i].endColumn
	                    				&& row<productionDailyReportHelper.templateData.header.length+productionDailyReportHelper.contentData.length
	                    		){
	                    			cellProperties.readOnly = false;
	                    			cellProperties.renderer = productionDailyReportHelper.addEditableColor;
	                    		}
	                    	}
	                    }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(productionDailyReportHelper!=null&&productionDailyReportHelper.hot!=''&&productionDailyReportHelper.hot!=undefined && productionDailyReportHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=productionDailyReportHelper.sourceData[coords.row][coords.col];
	                		if(coords.row>=productionDailyReportHelper.templateData.header.length){
	                			if(isNotVal(rawValue)){
	                				var showValue=rawValue;
	            					var rowChar=90;
	            					var maxWidth=rowChar*10;
	            					if(rawValue.length>rowChar){
	            						showValue='';
	            						let arr = [];
	            						let index = 0;
	            						while(index<rawValue.length){
	            							arr.push(rawValue.slice(index,index +=rowChar));
	            						}
	            						for(var i=0;i<arr.length;i++){
	            							showValue+=arr[i];
	            							if(i<arr.length-1){
	            								showValue+='<br>';
	            							}
	            						}
	            					}
	                				if(!isNotVal(TD.tip)){
	                					TD.tip = Ext.create('Ext.tip.ToolTip', {
			                			    target: event.target,
			                			    maxWidth:maxWidth,
			                			    html: showValue,
			                			    listeners: {
			                			    	hide: function (thisTip, eOpts) {
			                                	},
			                                	close: function (thisTip, eOpts) {
			                                	}
			                                }
			                			});
	                				}else{
	                					TD.tip.setHtml(showValue);
	                				}
	                			}
	                		}
	                	}
	                },
	                afterOnCellMouseOut: function(event, coords, TD){
	                	if(productionDailyReportHelper!=null&&productionDailyReportHelper.hot!=''&&productionDailyReportHelper.hot!=undefined && productionDailyReportHelper.hot.getDataAtCell!=undefined){
	                		var value=productionDailyReportHelper.sourceData[coords.row][coords.col];
	                		if(coords.row>=productionDailyReportHelper.templateData.header.length){
//	                			TD.outerHTML='<td class="htDimmed">'+TD.innerText+'</td>';
	                		}
	                	}
	                },
	                afterChange: function (changes, source) {
	                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
	                    if (productionDailyReportHelper!=null && productionDailyReportHelper.hot!=undefined && productionDailyReportHelper.hot!='' && changes != null) {
	                        for (var i = 0; i < changes.length; i++) {
	                            var params = [];
	                            var index = changes[i][0]; //行号码
	                            var rowdata = productionDailyReportHelper.hot.getDataAtRow(index);
	                            
	                            var editCellInfo={};
	                            var editRow=changes[i][0];
	                            var editCol=changes[i][1];
	                            var column=productionDailyReportHelper.columns[editCol];
	                            
	                            editCellInfo.editRow=editRow;
	                            editCellInfo.editCol=editCol;
	                            editCellInfo.column=column;
	                            editCellInfo.recordId=rowdata[rowdata.length-1]
	                            editCellInfo.oldValue=changes[i][2];
	                            editCellInfo.newValue=changes[i][3];
	                            editCellInfo.header=false;
	                            if(editCellInfo.editRow<productionDailyReportHelper.templateData.header.length){
	                            	editCellInfo.header=true;
	                            }
	                            
	                            var isExit=false;
	                            for(var j=0;j<productionDailyReportHelper.contentUpdateList.length;j++){
	                            	if(editCellInfo.editRow==productionDailyReportHelper.contentUpdateList[j].editRow && editCellInfo.editCol==productionDailyReportHelper.contentUpdateList[j].editCol){
	                            		productionDailyReportHelper.contentUpdateList[j].newValue=editCellInfo.newValue;
	                            		isExit=true;
	                            		break;
	                            	}
	                            }
	                            if(!isExit){
	                            	productionDailyReportHelper.contentUpdateList.push(editCellInfo);
	                            }
	                        }
	                    }
	                }
	            });
	        }
	        productionDailyReportHelper.getData = function (data) {
	        	
	        }
	        
	        productionDailyReportHelper.saveData = function () {
	        	if(productionDailyReportHelper.contentUpdateList.length>0){
	        		productionDailyReportHelper.editData.contentUpdateList=productionDailyReportHelper.contentUpdateList;
	        		var wellName='';
	        	    var wellId=0;
	        	    var selectRow= Ext.getCmp("RPCProductionDailyReportInstanceListSelectRow_Id").getValue();
	        	    if(selectRow>=0){
	        	    	wellName=Ext.getCmp("RPCProductionDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
	        	    	wellId=Ext.getCmp("RPCProductionDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
	        	    }
//	        		alert(JSON.stringify(productionDailyReportHelper.editData));
	        		Ext.Ajax.request({
	                    method: 'POST',
	                    url: context + '/reportDataMamagerController/saveSingleWellRangeDailyReportData',
	                    success: function (response) {
	                        rdata = Ext.JSON.decode(response.responseText);
	                        if (rdata.success) {
	                        	Ext.MessageBox.alert("信息", '保存成功');
	                        	productionDailyReportHelper.clearContainer();
	                        	CreateRPCProductionDailyReportTable();
	                        	CreateRPCProductionDailyReportCurve();
	                        } else {
	                        	productionDailyReportHelper.clearContainer();
	                        	Ext.MessageBox.alert("信息", "数据保存失败");
	                        }
	                    },
	                    failure: function () {
	                        Ext.MessageBox.alert("信息", "请求失败");
	                    },
	                    params: {
	                    	wellId:wellId,
	                    	wellName:wellName,
	                    	data: JSON.stringify(productionDailyReportHelper.editData),
	                        deviceType: 0
	                    }
	                });
	        	}else{
	        		Ext.MessageBox.alert("信息", "无数据变化！");
	        	}
	        }
	        productionDailyReportHelper.clearContainer = function () {
	        	productionDailyReportHelper.editData={};
            	productionDailyReportHelper.contentUpdateList=[];
	        }

	        var init = function () {
	        	productionDailyReportHelper.initData();
	        }

	        init();
	        return productionDailyReportHelper;
	    }
	};

function createRPCProductionDailyReportTemplateListDataColumn(columnInfo) {
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

function CreateRPCProductionDailyReportCurve(){
	var selectedOrgName="";
	var selectedOrgId="";
	var IframeViewStore = Ext.getCmp("IframeView_Id").getStore();
	var count=IframeViewStore.getCount();
	var IframeViewSelection = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
	if (IframeViewSelection.length > 0) {
//		selectedOrgName=foreachAndSearchOrgAbsolutePath(IframeViewStore.data.items,IframeViewSelection[0].data.orgId);
		selectedOrgName=IframeViewSelection[0].data.text;
		selectedOrgId=IframeViewSelection[0].data.orgId;
	} else {
		if(count>0){
			selectedOrgName=IframeViewStore.getAt(0).data.text;
			selectedOrgId=IframeViewStore.getAt(0).data.orgId;
		}
	}
	
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var startDate = Ext.getCmp('RPCProductionDailyReportStartDate_Id').rawValue;
    var endDate = Ext.getCmp('RPCProductionDailyReportEndDate_Id').rawValue;
    
    var wellName='';
    var unitId=0;
    var instanceCode='';
    var selectRow= Ext.getCmp("RPCProductionDailyReportInstanceListSelectRow_Id").getValue();
    if(selectRow>=0){
    	instanceCode=Ext.getCmp("RPCProductionDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.instanceCode;
    	unitId=Ext.getCmp("RPCProductionDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.unitId;
    }
    
    Ext.getCmp("RPCProductionDailyReportCurvePanel_id").el.mask(cosog.string.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reportDataMamagerController/getProductionDailyReportCurveData',
		success:function(response) {
			Ext.getCmp("RPCProductionDailyReportCurvePanel_id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			
			var startDate=Ext.getCmp('RPCProductionDailyReportStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(result.startDate);
            }
            var endDate=Ext.getCmp('RPCProductionDailyReportEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(result.endDate);
            }
            
		    var data = result.list;
		    var graphicSet=result.graphicSet;
		    var timeFormat='%m-%d';
		    var defaultColors=["#7cb5ec", "#434348", "#90ed7d", "#f7a35c", "#8085e9", "#f15c80", "#e4d354", "#2b908f", "#f45b5b", "#91e8e1"];
		    var tickInterval = 1;
		    tickInterval = Math.floor(data.length / 10) + 1;
		    if(tickInterval<100){
		    	tickInterval=100;
		    }
		    var title = result.selectedOrgName + "日报表曲线";
		    var xTitle='日期';
		    var legendName =result.curveItems;
		    var curveConf=result.curveConf;
		    var color=[];
		    var color_l=[];
		    var color_r=[];
		    var color_all=[];
		    for(var i=0;i<curveConf.length;i++){
		    	var singleColor=defaultColors[i%defaultColors.length];
		    	if(curveConf[i].color!=''){
		    		singleColor='#'+curveConf[i].color;
		    	}
		    	color.push(singleColor);
		    	
		    	if(curveConf[i].yAxisOpposite){
		    		color_r.push(singleColor);
		    	}else{
		    		color_l.push(singleColor);
		    	}
		    }
		    
		    var series = [];
		    var series_l=[];
		    var series_r=[];
		    var yAxis= [];
		    var yAxis_l= [];
		    var yAxis_r= [];
		   		    
		    for (var i = 0; i < legendName.length; i++) {
		        var maxValue=null;
		        var minValue=null;
		        var allPositive=true;//全部是非负数
		        var allNegative=true;//全部是负值
		        
		        var singleSeries={};
		        singleSeries.name=legendName[i];
		        singleSeries.type='spline';
		        singleSeries.lineWidth=curveConf[i].lineWidth;
		        singleSeries.dashStyle=curveConf[i].dashStyle;
		        singleSeries.marker={enabled: false};
		        singleSeries.yAxis=i;
		        singleSeries.data=[];
		        for (var j = 0; j < data.length; j++) {
		        	var pointData=[];
		        	pointData.push(Date.parse(data[j].calDate.replace(/-/g, '/')));
		        	pointData.push(data[j].data[i]);
		        	singleSeries.data.push(pointData);
		        	if(parseFloat(data[j].data[i])<0){
		            	allPositive=false;
		            }else if(parseFloat(data[j].data[i])>=0){
		            	allNegative=false;
		            }
		        }
		        if(curveConf[i].yAxisOpposite){
		        	series_r.push(singleSeries);
		        }else{
		        	series_l.push(singleSeries);
		        }
		        
		        var opposite=curveConf[i].yAxisOpposite;
		        if(allNegative){
		        	maxValue=0;
		        }else if(allPositive){
		        	minValue=0;
		        }
		        if(JSON.stringify(graphicSet) != "{}" && isNotVal(graphicSet.History) ){
			    	for(var j=0;j<graphicSet.History.length;j++){
			    		if(graphicSet.History[j].itemCode!=undefined && graphicSet.History[j].itemCode.toUpperCase()==result.curveItemCodes[i].toUpperCase()){
			    			if(isNotVal(graphicSet.History[j].yAxisMaxValue)){
					    		maxValue=parseFloat(graphicSet.History[j].yAxisMaxValue);
					    	}
					    	if(isNotVal(graphicSet.History[j].yAxisMinValue)){
					    		minValue=parseFloat(graphicSet.History[j].yAxisMinValue);
					    	}
					    	break;
			    		}
			    	}
			    }
		        
		        var singleAxis={
		        		max:maxValue,
		        		min:minValue,
		        		title: {
		                    text: legendName[i],
		                    style: {
		                        color: color[i],
		                    }
		                },
		                labels: {
		                	style: {
		                        color: color[i],
		                    }
		                },
		                opposite:opposite
		          };
		        if(curveConf[i].yAxisOpposite){
		        	yAxis_r.push(singleAxis);
		        }else{
		        	yAxis_l.push(singleAxis);
		        }
		        
		    }
		    for(var i=yAxis_l.length-1;i>=0;i--){
		    	yAxis.push(yAxis_l[i]);
		    }
		    for(var i=0;i<yAxis_r.length;i++){
		    	yAxis.push(yAxis_r[i]);
		    }
		    
		    for(var i=0;i<series_l.length;i++){
		    	series_l[i].yAxis=series_l.length-1-i;
		    	series.push(series_l[i]);
		    }
		    for(var i=0;i<series_r.length;i++){
		    	series_r[i].yAxis=series_l.length+i;
		    	series.push(series_r[i]);
		    }
		    
		    for(var i=0;i<color_l.length;i++){
		    	color_all.push(color_l[i]);
		    }
		    for(var i=0;i<color_r.length;i++){
		    	color_all.push(color_r[i]);
		    }
		    initRPCProductionDailyReportCurveChartFn(series, tickInterval, 'RPCProductionDailyReportCurveDiv_Id', title, '', '', yAxis, color_all,true,timeFormat);
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId: orgId,
			instanceCode:instanceCode,
			unitId:unitId,
			wellName: wellName,
			selectedOrgName: selectedOrgName,
			startDate: startDate,
			endDate: endDate,
			reportType: 1,
            deviceType:0
        }
	});
};

function initRPCProductionDailyReportCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, yAxis, color,legend,timeFormat) {
	var dafaultMenuItem = Highcharts.getOptions().exporting.buttons.contextButton.menuItems;
	Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });

    var mychart = new Highcharts.Chart({
        chart: {
            renderTo: divId,
            type: 'spline',
            shadow: true,
            borderWidth: 0,
            zoomType: 'xy'
        },
        credits: {
            enabled: false
        },
        title: {
            text: title
        },
        subtitle: {
            text: subtitle
        },
        colors: color,
        xAxis: {
            type: 'datetime',
            title: {
                text: xtitle
            },
//            tickInterval: tickInterval,
            tickPixelInterval:tickInterval,
            labels: {
                formatter: function () {
                    return Highcharts.dateFormat(timeFormat, this.value);
                },
                autoRotation:true,//自动旋转
                rotation: -45 //倾斜度，防止数量过多显示不全  
//                step: 2
            }
        },
        yAxis: yAxis,
        tooltip: {
            crosshairs: true, //十字准线
            shared: true,
            style: {
                color: '#333333',
                fontSize: '12px',
                padding: '8px'
            },
            dateTimeLabelFormats: {
                millisecond: '%Y-%m-%d %H:%M:%S.%L',
                second: '%Y-%m-%d %H:%M:%S',
                minute: '%Y-%m-%d %H:%M',
                hour: '%Y-%m-%d %H',
                day: '%Y-%m-%d',
                week: '%m-%d',
                month: '%Y-%m',
                year: '%Y'
            }
        },
        exporting: {
            enabled: true,
            filename: title,
            sourceWidth: $("#"+divId)[0].offsetWidth,
            sourceHeight: $("#"+divId)[0].offsetHeight,
            buttons: {
            	contextButton: {
            		menuItems:[dafaultMenuItem[0],dafaultMenuItem[1],dafaultMenuItem[2],dafaultMenuItem[3],dafaultMenuItem[4],dafaultMenuItem[5],dafaultMenuItem[6],dafaultMenuItem[7],
//            			,dafaultMenuItem[2],{
//            				text: '图形设置',
//            				onclick: function() {
//            					var window = Ext.create("AP.view.reportOut.ReportCurveSetWindow", {
//                                    title: '报表曲线设置'
//                                });
//                                window.show();
//            				}
//            			}
            		]
            	}
            }
        },
        plotOptions: {
            spline: {
//                lineWidth: 1,
                fillOpacity: 0.3,
                marker: {
                    enabled: true,
                    radius: 3, //曲线点半径，默认是4
                    //                            symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
                    states: {
                        hover: {
                            enabled: true,
                            radius: 6
                        }
                    }
                },
                shadow: true,
                events: {
                	legendItemClick: function(e){
//                		alert("第"+this.index+"个图例被点击，是否可见："+!this.visible);
//                		return true;
                	}
                }
            }
        },
        legend: {
            layout: 'horizontal',//horizontal水平 vertical 垂直
            align: 'center',  //left，center 和 right
            verticalAlign: 'bottom',//top，middle 和 bottom
            enabled: legend,
            borderWidth: 0
        },
        series: series
    });
};