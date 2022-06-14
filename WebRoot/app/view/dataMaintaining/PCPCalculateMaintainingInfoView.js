var pcpRPMCalculateMaintainingHandsontableHelper=null;
Ext.define("AP.view.dataMaintaining.PCPCalculateMaintainingInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.PCPCalculateMaintainingInfoView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var bbar = new Ext.toolbar.Paging({
        	id:'PCPFESDiagramCalculateMaintainingBbar',
            pageSize: defaultPageSize,
            displayInfo: true,
            displayMsg: '当前 {0}~{1}条  共 {2} 条',
            emptyMsg: "没有记录可显示",
            prevText: "上一页",
            nextText: "下一页",
            refreshText: "刷新",
            lastText: "最后页",
            firstText: "第一页",
            beforePageText: "当前页",
            afterPageText: "共{0}页"
        });
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
                    var wellName = Ext.getCmp('PCPCalculateMaintainingWellListComBox_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        wellName: wellName,
                        deviceType:1
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var wellListComb = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: cosog.string.wellName,
                    id: 'PCPCalculateMaintainingWellListComBox_Id',
                    store: wellListStore,
                    labelWidth: 35,
                    width: 125,
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
                    		wellListComb.getStore().loadPage(1);
                        },
                        select: function (combo, record, index) {
                        	calculateSignComb.clearValue();
                			var activeId = Ext.getCmp("PCPCalculateMaintainingTabPanel").getActiveTab().id;
                			if(activeId=="PCPCalculateMaintainingPanel"){
                				var gridPanel = Ext.getCmp("PCPCalculateMaintainingWellListGridPanel_Id");
                				if (isNotVal(gridPanel)) {
                					gridPanel.getStore().load();
                				}else{
                					Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingWellListStore');
                				}
                				var bbar=Ext.getCmp("PCPFESDiagramCalculateMaintainingBbar");
                				if (isNotVal(bbar)) {
                					if(bbar.getStore().isEmptyStore){
                						var PCPCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
                						bbar.setStore(PCPCalculateMaintainingDataStore);
                					}else{
                						bbar.getStore().loadPage(1);
                					}
                				}else{
                					Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
                				}
                			}else if(activeId=="PCPTotalCalculateMaintainingPanel"){
                				var gridPanel = Ext.getCmp("PCPCalculateMaintainingWellListGridPanel_Id");
                				if (isNotVal(gridPanel)) {
                					gridPanel.getStore().load();
                				}else{
                					Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingWellListStore');
                				}
                				var gridPanel = Ext.getCmp("PCPTotalCalculateMaintainingDataGridPanel_Id");
                	            if (isNotVal(gridPanel)) {
                	            	gridPanel.getStore().loadPage(1);
                	            }else{
                	            	Ext.create("AP.store.dataMaintaining.PCPTotalCalculateMaintainingDataStore");
                	            }
                			}
                        }
                    }
                });
        
        var calculateSignStore = new Ext.data.JsonStore({
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/calculateManagerController/getCalculateStatusList',
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
                	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                    var welName=Ext.getCmp('PCPCalculateMaintainingWellListComBox_Id').getValue();
                    var startDate=Ext.getCmp('PCPCalculateMaintainingStartDate_Id').rawValue;
                    var endDate=Ext.getCmp('PCPCalculateMaintainingEndDate_Id').rawValue;
                    var new_params = {
                    		orgId: orgId,
                    		welName: welName,
                            startDate:startDate,
                            endDate:endDate,
                            deviceType:1
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var calculateSignComb = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: '计算状态',
                    id: 'PCPCalculateMaintainingCalculateSignComBox_Id',
                    store: calculateSignStore,
                    labelWidth: 60,
                    width: 200,
                    queryMode: 'remote',
                    emptyText: cosog.string.all,
                    blankText: cosog.string.all,
                    typeAhead: false,
                    autoSelect: false,
                    allowBlank: true,
                    triggerAction: 'all',
                    editable: false,
                    displayField: "boxval",
                    valueField: "boxkey",
                    minChars: 0,
                    listeners: {
                    	expand: function (sm, selections) {
                    		calculateSignComb.clearValue();
                    		calculateSignComb.getStore().load(); // 加载井下拉框的store
                        },
                        select: function (combo, record, index) {
                        	var gridPanel = Ext.getCmp("PCPCalculateMaintainingWellListGridPanel_Id");
            				if (isNotVal(gridPanel)) {
            					gridPanel.getStore().load();
            				}else{
            					Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingWellListStore');
            				}
            				var bbar=Ext.getCmp("PCPFESDiagramCalculateMaintainingBbar");
            				if (isNotVal(bbar)) {
            					if(bbar.getStore().isEmptyStore){
            						var PCPCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
            						bbar.setStore(PCPCalculateMaintainingDataStore);
            					}else{
            						bbar.getStore().loadPage(1);
            					}
            				}else{
            					Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
            				}
                        }
                    }
        });
        Ext.apply(me, {
        	layout: 'border',
            border: false,
            tbar:[wellListComb
    			,"-",{
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: '',
                labelWidth: 0,
                width: 90,
                format: 'Y-m-d ',
                id: 'PCPCalculateMaintainingStartDate_Id',
                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                		calculateSignComb.clearValue();
                		var activeId = Ext.getCmp("PCPCalculateMaintainingTabPanel").getActiveTab().id;
            			if(activeId=="PCPCalculateMaintainingPanel"){
            				var bbar=Ext.getCmp("PCPFESDiagramCalculateMaintainingBbar");
            				if (isNotVal(bbar)) {
            					if(bbar.getStore().isEmptyStore){
            						var PCPCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
            						bbar.setStore(PCPCalculateMaintainingDataStore);
            					}else{
            						bbar.getStore().loadPage(1);
            					}
            				}else{
            					Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
            				}
            			}else if(activeId=="PCPTotalCalculateMaintainingPanel"){
            				var gridPanel = Ext.getCmp("PCPTotalCalculateMaintainingDataGridPanel_Id");
            	            if (isNotVal(gridPanel)) {
            	            	gridPanel.getStore().loadPage(1);
            	            }else{
            	            	Ext.create("AP.store.dataMaintaining.PCPTotalCalculateMaintainingDataStore");
            	            }
            			}
                    }
                }
            },{
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: '至',
                labelWidth: 15,
                width: 105,
                format: 'Y-m-d ',
                id: 'PCPCalculateMaintainingEndDate_Id',
                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                		calculateSignComb.clearValue();
                		var activeId = Ext.getCmp("PCPCalculateMaintainingTabPanel").getActiveTab().id;
            			if(activeId=="PCPCalculateMaintainingPanel"){
            				var bbar=Ext.getCmp("PCPFESDiagramCalculateMaintainingBbar");
            				if (isNotVal(bbar)) {
            					if(bbar.getStore().isEmptyStore){
            						var PCPCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
            						bbar.setStore(PCPCalculateMaintainingDataStore);
            					}else{
            						bbar.getStore().loadPage(1);
            					}
            				}else{
            					Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
            				}
            			}else if(activeId=="PCPTotalCalculateMaintainingPanel"){
            				var gridPanel = Ext.getCmp("PCPTotalCalculateMaintainingDataGridPanel_Id");
            	            if (isNotVal(gridPanel)) {
            	            	gridPanel.getStore().loadPage(1);
            	            }else{
            	            	Ext.create("AP.store.dataMaintaining.PCPTotalCalculateMaintainingDataStore");
            	            }
            			}
                    }
                }
            },"-",calculateSignComb,'-',{
                xtype: 'button',
                iconCls: 'note-refresh',
                text: cosog.string.refresh,
                pressed: true,
                hidden:false,
                handler: function (v, o) {
                	var activeId = Ext.getCmp("PCPCalculateMaintainingTabPanel").getActiveTab().id;
        			if(activeId=="PCPCalculateMaintainingPanel"){
        				var bbar=Ext.getCmp("PCPFESDiagramCalculateMaintainingBbar");
        				if (isNotVal(bbar)) {
        					if(bbar.getStore().isEmptyStore){
        						var PCPCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
        						bbar.setStore(PCPCalculateMaintainingDataStore);
        					}else{
        						bbar.getStore().loadPage(1);
        					}
        				}else{
        					Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
        				}
        			}else if(activeId=="PCPTotalCalculateMaintainingPanel"){
        				var gridPanel = Ext.getCmp("PCPTotalCalculateMaintainingDataGridPanel_Id");
        	            if (isNotVal(gridPanel)) {
        	            	gridPanel.getStore().loadPage(1);
        	            }else{
        	            	Ext.create("AP.store.dataMaintaining.PCPTotalCalculateMaintainingDataStore");
        	            }
        			}
                }
            
    		},'->',{
                xtype: 'button',
                text: '修改数据计算',
                id:'PCPCalculateMaintainingUpdateDataBtn',
                pressed: true,
                iconCls: 'edit',
                handler: function (v, o) {
                	pcpRPMCalculateMaintainingHandsontableHelper.saveData();
                }
            },"-",{
                xtype: 'button',
                text: '关联数据计算',
                pressed: true,
                iconCls: 'save',
                id:'PCPCalculateMaintainingLinkedDataBtn',
                handler: function (v, o) {
                	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                    var wellName=Ext.getCmp('PCPCalculateMaintainingWellListComBox_Id').getValue();
                    var startDate=Ext.getCmp('PCPCalculateMaintainingStartDate_Id').rawValue;
                    var endDate=Ext.getCmp('PCPCalculateMaintainingEndDate_Id').rawValue;
                    var calculateSign=Ext.getCmp('PCPCalculateMaintainingCalculateSignComBox_Id').getValue();
                    var deviceType=1;
                    var showWellName=wellName;
                	if(wellName == '' || wellName == null){
                		if(deviceType==0){
                			showWellName='所选组织下全部抽油机井';
                		}else if(deviceType==1){
                			showWellName='所选组织下全部螺杆泵井';
                		}
                	}else{
//                		showWellName+='井';
                	}
                	var operaName="生效范围："+showWellName+" "+startDate+"~"+endDate+" </br><font color=red>该操作将导致所选历史数据被当前生产数据覆盖，是否执行！</font>"
                	Ext.Msg.confirm("操作确认", operaName, function (btn) {
                        if (btn == "yes") {
                        	Ext.Ajax.request({
        	            		method:'POST',
        	            		url:context + '/calculateManagerController/recalculateByProductionData',
        	            		success:function(response) {
        	            			var rdata=Ext.JSON.decode(response.responseText);
        	            			if (rdata.success) {
        	                        	Ext.MessageBox.alert("信息","保存成功，开始重新计算，点击左下角刷新按钮查看计算状态列数值，无未计算时，计算完成。");
        	                            //保存以后重置全局容器
        	                            pcpRPMCalculateMaintainingHandsontableHelper.clearContainer();
        	                            Ext.getCmp("PCPFESDiagramCalculateMaintainingBbar").getStore().loadPage(1);
        	                        } else {
        	                        	Ext.MessageBox.alert("信息","操作失败");

        	                        }
        	            		},
        	            		failure:function(){
        	            			Ext.MessageBox.alert("信息","请求失败");
        	                        pcpRPMCalculateMaintainingHandsontableHelper.clearContainer();
        	            		},
        	            		params: {
        	            			orgId: orgId,
        	            			wellName: wellName,
        	                        startDate:startDate,
        	                        endDate:endDate,
        	                        calculateSign:calculateSign,
        	                        deviceType:deviceType
        	                    }
        	            	}); 
                        }
                    });
                }
            },"-",{
                xtype: 'button',
                text: '导出请求数据',
                pressed: true,
                hidden: false,
                iconCls: 'export',
                id:'PCPCalculateMaintainingExportDataBtn',
                handler: function (v, o) {
                	if(pcpRPMCalculateMaintainingHandsontableHelper.hot.getSelected()){
                		var row=pcpRPMCalculateMaintainingHandsontableHelper.hot.getSelected()[0][0];
                		var recordId=pcpRPMCalculateMaintainingHandsontableHelper.hot.getDataAtRow(row)[0];
                		var wellName=pcpRPMCalculateMaintainingHandsontableHelper.hot.getDataAtRow(row)[1];
                		var acqTime=pcpRPMCalculateMaintainingHandsontableHelper.hot.getDataAtRow(row)[2];
                		var calculateType=2;//1-抽油机诊断计产 2-螺杆泵诊断计产 3-抽油机汇总计算  4-螺杆泵汇总计算 5-电参反演地面功图计算
                		var url=context + '/calculateManagerController/exportCalculateRequestData?recordId='+recordId+'&wellName='+URLencode(URLencode(wellName))+'&acqTime='+acqTime+'&calculateType='+calculateType;
                    	document.location.href = url;
                	}else{
                		Ext.MessageBox.alert("信息","未选择记录");
                	}
                }
            },{
                xtype: 'button',
                text: '重新汇总',
                id:'PCPCalculateMaintainingReTotalBtn',
                pressed: true,
                hidden:true,
                iconCls: 'edit',
                handler: function (v, o) {
                	ReTotalRPMData();
                }
            },"-",{
                xtype: 'button',
                text: '导出请求数据',
                pressed: true,
                hidden: true,
                iconCls: 'export',
                id:'PCPTotalCalculateMaintainingExportDataBtn',
                handler: function (v, o) {
                	var gridPanel = Ext.getCmp("PCPTotalCalculateMaintainingDataGridPanel_Id");
                    var selectionModel = gridPanel.getSelectionModel();
                    var _record = selectionModel.getSelection();
                    if (_record.length>0) {
                    	var recordId=_record[0].data.id;
                    	var wellId=_record[0].data.wellId;
                    	var wellName=_record[0].data.wellName;
                    	var calDate=_record[0].data.calDate;
                		var deviceType=1;
                		var url=context + '/calculateManagerController/exportTotalCalculateRequestData?recordId='+recordId
                		+'&wellId='+wellId
                		+'&wellName='+URLencode(URLencode(wellName))
                		+'&calDate='+calDate
                		+'&deviceType='+deviceType;
                    	document.location.href = url;
                    }else{
                    	Ext.MessageBox.alert("信息","未选择记录");
                    }
                }
            }],
        	items: [{
        		region: 'west',
            	width: '25%',
            	title: '井列表',
            	id: 'PCPCalculateMaintainingWellListPanel_Id',
            	collapsible: true, // 是否可折叠
                collapsed:false,//是否折叠
                split: true, // 竖折叠条
            	layout: "fit"
            },{
            	region: 'center',
            	xtype: 'tabpanel',
        		id:"PCPCalculateMaintainingTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'bottom',
        		items: [{
        			title: '单条记录',
    				layout: "fit",
    				id:'PCPCalculateMaintainingPanel',
    				border: false,
    				bbar: bbar,
    				html:'<div class=PCPCalculateMaintainingContainer" style="width:100%;height:100%;"><div class="con" id="PCPCalculateMaintainingDiv_id"></div></div>',
    				listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        	if(pcpRPMCalculateMaintainingHandsontableHelper!=null && pcpRPMCalculateMaintainingHandsontableHelper.hot!=undefined){
                        		pcpRPMCalculateMaintainingHandsontableHelper.hot.refreshDimensions();
                        	}
                        }
                    }
        		},{
        			title: '记录汇总',
    				layout: "fit",
    				id:'PCPTotalCalculateMaintainingPanel',
    				border: false,
    				html:'<div class=PCPTotalCalculateMaintainingContainer" style="width:100%;height:100%;"><div class="con" id="PCPTotalCalculateMaintainingDiv_id"></div></div>',
    				listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
//                        	if(pcpTotalCalculateMaintainingHandsontableHelper!=null && pcpTotalCalculateMaintainingHandsontableHelper.hot!=undefined){
//                        		pcpTotalCalculateMaintainingHandsontableHelper.hot.refreshDimensions();
//                        	}
                        }
                    }
        		}],
        		listeners: {
    				tabchange: function (tabPanel, newCard,oldCard, obj) {
    					if(newCard.id=="PCPCalculateMaintainingPanel"){
    						Ext.getCmp("PCPCalculateMaintainingUpdateDataBtn").show();
    						Ext.getCmp("PCPCalculateMaintainingLinkedDataBtn").show();
    						Ext.getCmp("PCPCalculateMaintainingExportDataBtn").show();
    						Ext.getCmp("PCPCalculateMaintainingCalculateSignComBox_Id").show();
    						Ext.getCmp("PCPCalculateMaintainingReTotalBtn").hide();
    						Ext.getCmp("PCPTotalCalculateMaintainingExportDataBtn").hide();
    					}else if(newCard.id=="PCPTotalCalculateMaintainingPanel"){
    						Ext.getCmp("PCPCalculateMaintainingUpdateDataBtn").hide();
    						Ext.getCmp("PCPCalculateMaintainingLinkedDataBtn").hide();
    						Ext.getCmp("PCPCalculateMaintainingExportDataBtn").hide();
    						Ext.getCmp("PCPCalculateMaintainingCalculateSignComBox_Id").hide();
    						Ext.getCmp("PCPCalculateMaintainingReTotalBtn").show();
    						Ext.getCmp("PCPTotalCalculateMaintainingExportDataBtn").show();
    						var gridPanel = Ext.getCmp("PCPTotalCalculateMaintainingDataGridPanel_Id");
    			            if (isNotVal(gridPanel)) {
    			            	gridPanel.getStore().loadPage(1);
    			            }else{
    			            	Ext.create("AP.store.dataMaintaining.PCPTotalCalculateMaintainingDataStore");
    			            }
    					}
    				}
    			}
            }]
        });
        me.callParent(arguments);
    }
});


function CreateAndLoadPCPCalculateMaintainingTable(isNew,result,divid){
	if(isNew&&pcpRPMCalculateMaintainingHandsontableHelper!=null){
        pcpRPMCalculateMaintainingHandsontableHelper.clearContainer();
        if(pcpRPMCalculateMaintainingHandsontableHelper.hot!=undefined){
        	pcpRPMCalculateMaintainingHandsontableHelper.hot.destroy();
        }
        pcpRPMCalculateMaintainingHandsontableHelper=null;
	}
	
	if(pcpRPMCalculateMaintainingHandsontableHelper==null){
		pcpRPMCalculateMaintainingHandsontableHelper = PCPRPMCalculateMaintainingHandsontableHelper.createNew(divid);
		var colHeaders="[";
        var columns="[";
        for(var i=0;i<result.columns.length;i++){
        	colHeaders+="'"+result.columns[i].header+"'";
        	columns+="{data:'"+result.columns[i].dataIndex+"'";
        	if(result.columns[i].dataIndex.toUpperCase()=="id".toUpperCase()){
        		columns+=",type: 'checkbox'";
        	}else if(result.columns[i].dataIndex.toUpperCase()==="wellName".toUpperCase()||result.columns[i].dataIndex.toUpperCase()==="acqTime".toUpperCase()||result.columns[i].dataIndex.toUpperCase()==="resultName".toUpperCase()){
    			
    		}else if(result.columns[i].dataIndex==="anchoringStateName"){
        		columns+=",type:'dropdown',strict:true,allowInvalid:false,source:['锚定', '未锚定']";
        	}else if(result.columns[i].dataIndex.toUpperCase()==="barrelTypeName".toUpperCase()){
        		columns+=",type:'dropdown',strict:true,allowInvalid:false,source:['组合泵', '整筒泵']";
        	}else if(result.columns[i].dataIndex.toUpperCase()==="pumpTypeName".toUpperCase()){
        		columns+=",type:'dropdown',strict:true,allowInvalid:false,source:['杆式泵', '管式泵']";
        	}else if(result.columns[i].dataIndex.toUpperCase()==="pumpGrade".toUpperCase()){
        		columns+=",type:'dropdown',strict:true,allowInvalid:false,source:['1', '2','3', '4','5']";
        	}
//        	else if(result.columns[i].dataIndex.toUpperCase()==="pumpGrade".toUpperCase()){
//        		columns+=",type:'numeric',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_PumpGrade(val, callback,this.row, this.col,pcpRPMCalculateMaintainingHandsontableHelper);}";
//        	}
        	else if(result.columns[i].dataIndex.toUpperCase()==="rodGrade1".toUpperCase() || result.columns[i].dataIndex.toUpperCase()==="rodGrade2".toUpperCase() || result.columns[i].dataIndex.toUpperCase()==="rodGrade3".toUpperCase() || result.columns[i].dataIndex.toUpperCase()==="rodGrade4".toUpperCase()){
        		columns+=",type:'dropdown',strict:true,allowInvalid:false,source:['','A','B','C','D','K','KD','HL','HY'], validator: function(val, callback){return handsontableDataCheck_RodGrade(val, callback,this.row, this.col,pcpRPMCalculateMaintainingHandsontableHelper);}";
        	}else{
    			columns+=",type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,pcpRPMCalculateMaintainingHandsontableHelper);}";
    		}
        	columns+="}";
        	if(i<result.columns.length-1){
        		colHeaders+=",";
            	columns+=",";
        	}
        }
        colHeaders+="]";
    	columns+="]";
    	pcpRPMCalculateMaintainingHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
    	pcpRPMCalculateMaintainingHandsontableHelper.columns=Ext.JSON.decode(columns);
		pcpRPMCalculateMaintainingHandsontableHelper.createTable(result.totalRoot);
	}else{
		pcpRPMCalculateMaintainingHandsontableHelper.hot.loadData(result.totalRoot);
	}
};


var PCPRPMCalculateMaintainingHandsontableHelper = {
	    createNew: function (divid) {
	        var pcpRPMCalculateMaintainingHandsontableHelper = {};
	        pcpRPMCalculateMaintainingHandsontableHelper.hot = '';
	        pcpRPMCalculateMaintainingHandsontableHelper.divid = divid;
	        pcpRPMCalculateMaintainingHandsontableHelper.validresult=true;//数据校验
	        pcpRPMCalculateMaintainingHandsontableHelper.colHeaders=[];
	        pcpRPMCalculateMaintainingHandsontableHelper.columns=[];
	        
	        pcpRPMCalculateMaintainingHandsontableHelper.AllData={};
	        pcpRPMCalculateMaintainingHandsontableHelper.updatelist=[];
	        pcpRPMCalculateMaintainingHandsontableHelper.delidslist=[];
	        pcpRPMCalculateMaintainingHandsontableHelper.insertlist=[];
	        
	        pcpRPMCalculateMaintainingHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        
	        pcpRPMCalculateMaintainingHandsontableHelper.createTable = function (data) {
	        	$('#'+pcpRPMCalculateMaintainingHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+pcpRPMCalculateMaintainingHandsontableHelper.divid);
	        	pcpRPMCalculateMaintainingHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		fixedColumnsLeft:4, //固定左侧多少列不能水平滚动
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: false
	                },
	                columns:pcpRPMCalculateMaintainingHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:pcpRPMCalculateMaintainingHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                outsideClickDeselects: false, // 点击到表格以外，表格就失去focus
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    if (visualColIndex >= 1 && visualColIndex <= 6) {
							cellProperties.readOnly = true;
							cellProperties.renderer = pcpRPMCalculateMaintainingHandsontableHelper.addBoldBg;
		                }
	                    return cellProperties;
	                },
	                afterDestroy: function() {
	                },
	                beforeRemoveRow: function (index, amount) {
	                    var ids = [];
	                    //封装id成array传入后台
	                    if (amount != 0) {
	                        for (var i = index; i < amount + index; i++) {
	                            var rowdata = pcpRPMCalculateMaintainingHandsontableHelper.hot.getDataAtRow(i);
	                            ids.push(rowdata[0]);
	                        }
	                        pcpRPMCalculateMaintainingHandsontableHelper.delExpressCount(ids);
	                        pcpRPMCalculateMaintainingHandsontableHelper.screening();
	                    }
	                },
	                afterChange: function (changes, source) {
	                    if (changes != null) {
	                    	for(var i=0;i<changes.length;i++){
	                    		var params = [];
	                    		var index = changes[i][0]; //行号码
		                        var rowdata = pcpRPMCalculateMaintainingHandsontableHelper.hot.getDataAtRow(index);
		                        params.push(rowdata[0]);
		                        params.push(changes[i][1]);
		                        params.push(changes[i][2]);
		                        params.push(changes[i][3]);

		                        //仅当单元格发生改变的时候,id!=null,说明是更新
		                        if (params[2] != params[3] && params[0] != null && params[0] >0) {
		                        	var data="{";
		                        	for(var j=0;j<pcpRPMCalculateMaintainingHandsontableHelper.columns.length;j++){
		                        		data+=pcpRPMCalculateMaintainingHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
		                        		if(j<pcpRPMCalculateMaintainingHandsontableHelper.columns.length-1){
		                        			data+=","
		                        		}
		                        	}
		                        	data+="}"
		                            pcpRPMCalculateMaintainingHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
		                        }
	                    	}
	                        
	                    }
	                }
	        	});
	        }
	      //插入的数据的获取
	        pcpRPMCalculateMaintainingHandsontableHelper.insertExpressCount=function() {
	            var idsdata = pcpRPMCalculateMaintainingHandsontableHelper.hot.getDataAtCol(0); //所有的id
	            for (var i = 0; i < idsdata.length; i++) {
	                //id=null时,是插入数据,此时的i正好是行号
	                if (idsdata[i] == null||idsdata[i]<0) {
	                    //获得id=null时的所有数据封装进data
	                    var rowdata = pcpRPMCalculateMaintainingHandsontableHelper.hot.getDataAtRow(i);
	                    //var collength = hot.countCols();
	                    if (rowdata != null) {
	                    	var data="{";
                        	for(var j=0;j<pcpRPMCalculateMaintainingHandsontableHelper.columns.length;j++){
                        		data+=pcpRPMCalculateMaintainingHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
                        		if(j<pcpRPMCalculateMaintainingHandsontableHelper.columns.length-1){
                        			data+=","
                        		}
                        	}
                        	data+="}"
	                        pcpRPMCalculateMaintainingHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
	                    }
	                }
	            }
	            if (pcpRPMCalculateMaintainingHandsontableHelper.insertlist.length != 0) {
	            	pcpRPMCalculateMaintainingHandsontableHelper.AllData.insertlist = pcpRPMCalculateMaintainingHandsontableHelper.insertlist;
	            }
	        }
	        //保存数据
	        pcpRPMCalculateMaintainingHandsontableHelper.saveData = function () {
        		//插入的数据的获取
	        	pcpRPMCalculateMaintainingHandsontableHelper.insertExpressCount();
	            if (JSON.stringify(pcpRPMCalculateMaintainingHandsontableHelper.AllData) != "{}" && pcpRPMCalculateMaintainingHandsontableHelper.validresult) {
	            	var bbarId="PCPFESDiagramCalculateMaintainingBbar";
	            	var deviceType=1;
	            	var calculateType=2;//1-抽油机诊断计产 2-螺杆泵诊断计产 3-抽油机汇总计算  4-螺杆泵汇总计算 5-电参反演地面功图计算
	            	Ext.Ajax.request({
	            		method:'POST',
	            		url:context + '/calculateManagerController/saveRecalculateData',
	            		success:function(response) {
	            			var rdata=Ext.JSON.decode(response.responseText);
	            			if (rdata.success) {
	                        	var successInfo='保存成功，开始重新计算，点击左下角刷新按钮查看计算状态列，无未计算记录时，计算完成。';
	                            //保存以后重置全局容器
	                            pcpRPMCalculateMaintainingHandsontableHelper.clearContainer();
	                            Ext.MessageBox.alert("信息",successInfo);
	                            Ext.getCmp("PCPFESDiagramCalculateMaintainingBbar").getStore().loadPage(1);
	                        } else {
	                        	Ext.MessageBox.alert("信息","数据保存失败");
	                        }
	            		},
	            		failure:function(){
	            			Ext.MessageBox.alert("信息","请求失败");
	                        pcpRPMCalculateMaintainingHandsontableHelper.clearContainer();
	            		},
	            		params: {
	                    	data: JSON.stringify(pcpRPMCalculateMaintainingHandsontableHelper.AllData),
	                    	deviceType: deviceType,
	                    	calculateType: calculateType
	                    }
	            	}); 
	            } else {
	                if (!pcpRPMCalculateMaintainingHandsontableHelper.validresult) {
	                	Ext.MessageBox.alert("信息","数据类型错误");
	                } else {
	                	Ext.MessageBox.alert("信息","无数据变化");
	                }
	            }
	        }
	        
	        
	      //删除的优先级最高
	        pcpRPMCalculateMaintainingHandsontableHelper.delExpressCount=function(ids) {
	            //传入的ids.length不可能为0
	            $.each(ids, function (index, id) {
	                if (id != null) {
	                	pcpRPMCalculateMaintainingHandsontableHelper.delidslist.push(id);
	                }
	            });
	            pcpRPMCalculateMaintainingHandsontableHelper.AllData.delidslist = pcpRPMCalculateMaintainingHandsontableHelper.delidslist;
	        }

	        //updatelist数据更新
	        pcpRPMCalculateMaintainingHandsontableHelper.screening=function() {
	            if (pcpRPMCalculateMaintainingHandsontableHelper.updatelist.length != 0 && pcpRPMCalculateMaintainingHandsontableHelper.delidslist.lentgh != 0) {
	                for (var i = 0; i < pcpRPMCalculateMaintainingHandsontableHelper.delidslist.length; i++) {
	                    for (var j = 0; j < pcpRPMCalculateMaintainingHandsontableHelper.updatelist.length; j++) {
	                        if (pcpRPMCalculateMaintainingHandsontableHelper.updatelist[j].id == pcpRPMCalculateMaintainingHandsontableHelper.delidslist[i]) {
	                            //更新updatelist
	                        	pcpRPMCalculateMaintainingHandsontableHelper.updatelist.splice(j, 1);
	                        }
	                    }
	                }
	                //把updatelist封装进AllData
	                pcpRPMCalculateMaintainingHandsontableHelper.AllData.updatelist = pcpRPMCalculateMaintainingHandsontableHelper.updatelist;
	            }
	        }
	        
	      //更新数据
	        pcpRPMCalculateMaintainingHandsontableHelper.updateExpressCount=function(data) {
	            if (JSON.stringify(data) != "{}") {
	                var flag = true;
	                //判断记录是否存在,更新数据     
	                $.each(pcpRPMCalculateMaintainingHandsontableHelper.updatelist, function (index, node) {
	                    if (node.id == data.id) {
	                        //此记录已经有了
	                        flag = false;
	                        //用新得到的记录替换原来的,不用新增
	                        pcpRPMCalculateMaintainingHandsontableHelper.updatelist[index] = data;
	                    }
	                });
	                flag && pcpRPMCalculateMaintainingHandsontableHelper.updatelist.push(data);
	                //封装
	                pcpRPMCalculateMaintainingHandsontableHelper.AllData.updatelist = pcpRPMCalculateMaintainingHandsontableHelper.updatelist;
	            }
	        }
	        
	        pcpRPMCalculateMaintainingHandsontableHelper.clearContainer = function () {
	        	pcpRPMCalculateMaintainingHandsontableHelper.AllData = {};
	        	pcpRPMCalculateMaintainingHandsontableHelper.updatelist = [];
	        	pcpRPMCalculateMaintainingHandsontableHelper.delidslist = [];
	        	pcpRPMCalculateMaintainingHandsontableHelper.insertlist = [];
	        }
	        
	        return pcpRPMCalculateMaintainingHandsontableHelper;
	    }
};

function ReTotalRPMData(){
	var gridPanel = Ext.getCmp("PCPTotalCalculateMaintainingDataGridPanel_Id");
    var selectionModel = gridPanel.getSelectionModel();
    var _record = selectionModel.getSelection();
    if (_record.length>0) {
    	var reCalculateData='';
    	Ext.Array.each(_record, function (name, index, countriesItSelf) {
    		reCalculateData+=_record[index].data.id+","+_record[index].data.wellId+","+_record[index].data.wellName+","+_record[index].data.calDate+";"
    	});
    	reCalculateData = reCalculateData.substring(0, reCalculateData.length - 1);
    	Ext.getCmp("PCPTotalCalculateMaintainingPanel").el.mask('重新计算中，请稍后...').show();
    	Ext.Ajax.request({
    		method:'POST',
    		url:context + '/calculateManagerController/reTotalCalculate',
    		success:function(response) {
    			Ext.getCmp("PCPTotalCalculateMaintainingPanel").getEl().unmask();
    			Ext.MessageBox.alert("信息","重新计算完成。");
                Ext.getCmp("RPCTotalCalculateMaintainingDataGridPanel_Id").getStore().loadPage(1);
    		},
    		failure:function(){
    			Ext.MessageBox.alert("信息","请求失败");
    		},
    		params: {
    			deviceType: 1,
    			reCalculateDate: reCalculateData
            }
    	}); 
    }else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
}