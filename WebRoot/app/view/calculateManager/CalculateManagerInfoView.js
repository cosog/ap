var calculateManagerHandsontableHelper=null;
Ext.define("AP.view.calculateManager.CalculateManagerInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.calculateManagerInfoView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var calculateResultStore=Ext.create('AP.store.calculateManager.CalculateManagerDataStore');
        var CalculateManagerWellListStore=Ext.create('AP.store.calculateManager.CalculateManagerWellListStore');
        var bbar = new Ext.toolbar.Paging({
        	id:'pumpingCalculateManagerBbar',
            store: calculateResultStore,
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
        var screwPumpBbar = new Ext.toolbar.Paging({
        	id:'screwPumpCalculateManagerBbar',
        	store: calculateResultStore,
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
        var elecInverBbar = new Ext.toolbar.Paging({
        	id:'elecInverCalculateManagerBbar',
        	store: calculateResultStore,
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
                    var wellName = Ext.getCmp('CalculateManagerWellListComBox_Id').getValue();
                    var wellType=200;
                    var tabPanelId = Ext.getCmp("CalculateManagerTabPanel").getActiveTab().id;
                    if(tabPanelId=="PumpingUnitCalculateManagerPanel"){
                    	wellType=200;
					}else if(tabPanelId=="ScrewPumpCalculateManagerPanel"){
						wellType=400;
					}
                    var new_params = {
                        orgId: leftOrg_Id,
                        wellName: wellName,
                        wellType:wellType
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var wellListComb = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: cosog.string.wellName,
                    id: 'CalculateManagerWellListComBox_Id',
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
                        	calculateResultStore.loadPage(1);
                        	CalculateManagerWellListStore.load();
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
                    var welName=Ext.getCmp('CalculateManagerWellListComBox_Id').getValue();
                    var startDate=Ext.getCmp('CalculateManagerStartDate_Id').rawValue;
                    var endDate=Ext.getCmp('CalculateManagerEndDate_Id').rawValue;
                    var wellType=200;
                    var tabPanelId = Ext.getCmp("CalculateManagerTabPanel").getActiveTab().id;
                    if(tabPanelId=="PumpingUnitCalculateManagerPanel"){
                    	wellType=200;
        			}else if(tabPanelId=="ScrewPumpCalculateManagerPanel"){
        				wellType=400;
        			}
                    var new_params = {
                    		orgId: orgId,
                    		welName: welName,
                            startDate:startDate,
                            endDate:endDate,
                            wellType:wellType
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var calculateSignComb = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: '计算状态',
                    id: 'CalculateManagerCalculateSignComBox_Id',
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
                        	calculateResultStore.loadPage(1);
                        	CalculateManagerWellListStore.load();
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
                id: 'CalculateManagerStartDate_Id',
                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                		calculateSignComb.clearValue();
                		calculateResultStore.loadPage(1);
                		CalculateManagerWellListStore.load();
                    }
                }
            },{
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: '至',
                labelWidth: 15,
                width: 105,
                format: 'Y-m-d ',
                id: 'CalculateManagerEndDate_Id',
                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                		calculateSignComb.clearValue();
                		calculateResultStore.loadPage(1);
                		CalculateManagerWellListStore.load();
                    }
                }
            },"-",calculateSignComb,'-',{
                xtype: 'button',
                iconCls: 'note-refresh',
                text: cosog.string.refresh,
                pressed: true,
                hidden:false,
                handler: function (v, o) {
                	calculateResultStore.loadPage(1);
                	CalculateManagerWellListStore.load();
                }
            
    		},'->',{
                xtype: 'button',
                text: '修改数据计算',
                pressed: true,
                iconCls: 'save',
                handler: function (v, o) {
                	calculateManagerHandsontableHelper.saveData();
                }
            },"-",{
                xtype: 'button',
                text: '关联数据计算',
                pressed: true,
                iconCls: 'save',
                id:'calculateManager_LinkedData_Btn',
                handler: function (v, o) {
                	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                    var wellName=Ext.getCmp('CalculateManagerWellListComBox_Id').getValue();
                    var startDate=Ext.getCmp('CalculateManagerStartDate_Id').rawValue;
                    var endDate=Ext.getCmp('CalculateManagerEndDate_Id').rawValue;
                    var calculateSign=Ext.getCmp('CalculateManagerCalculateSignComBox_Id').getValue();
                    var wellType=200;
                    var tabPanelId = Ext.getCmp("CalculateManagerTabPanel").getActiveTab().id;
                    if(tabPanelId=="PumpingUnitCalculateManagerPanel"){
                    	wellType=200;
        			}else if(tabPanelId=="ScrewPumpCalculateManagerPanel"){
        				wellType=400;
        			}
                    var showWellName=wellName;
                	if(wellName == '' || wellName == null){
                		if(wellType==200){
                			showWellName='所选组织下全部抽油机井';
                		}else if(wellType==400){
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
        	                        	Ext.MessageBox.alert("信息","保存成功，开始重新计算，点击左下角刷新按钮查看计算状态列数值，无0和2时，计算完成。");
        	                            //保存以后重置全局容器
        	                            calculateManagerHandsontableHelper.clearContainer();
        	                            var bbarId="pumpingCalculateManagerBbar";
        	                            var tabPanelId = Ext.getCmp("CalculateManagerTabPanel").getActiveTab().id;
        	                            if(tabPanelId=="PumpingUnitCalculateManagerPanel"){
        	                            	bbarId="pumpingCalculateManagerBbar";
        	        					}else if(tabPanelId=="ScrewPumpCalculateManagerPanel"){
        	        						bbarId="screwPumpCalculateManagerBbar";
        	        					}else if(tabPanelId=="ElectricInversionCalculateManagerPanel"){
        	        						bbarId="elecInverCalculateManagerBbar";
        	        					}
        	                            
        	                            Ext.getCmp(bbarId).getStore().loadPage(1);
        	                        } else {
        	                        	Ext.MessageBox.alert("信息","操作失败");

        	                        }
        	            		},
        	            		failure:function(){
        	            			Ext.MessageBox.alert("信息","请求失败");
        	                        calculateManagerHandsontableHelper.clearContainer();
        	            		},
        	            		params: {
        	            			orgId: orgId,
        	            			wellName: wellName,
        	                        startDate:startDate,
        	                        endDate:endDate,
        	                        calculateSign:calculateSign,
        	                        wellType:wellType
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
                iconCls: 'save',
                id:'calculateManager_ExportData_Btn',
                handler: function (v, o) {
                	if(calculateManagerHandsontableHelper.hot.getSelected()){
                		var row=calculateManagerHandsontableHelper.hot.getSelected()[0];
                		var wellName=calculateManagerHandsontableHelper.hot.getDataAtRow(row)[1];
                		var acqTime=calculateManagerHandsontableHelper.hot.getDataAtRow(row)[2];
                		
                		var calculateType=1;//1-抽油机诊断计产 2-螺杆泵诊断计产 3-抽油机汇总计算  4-螺杆泵汇总计算 5-电参反演地面功图计算
                        var tabPanelId = Ext.getCmp("CalculateManagerTabPanel").getActiveTab().id;
                        if(tabPanelId=="PumpingUnitCalculateManagerPanel"){
                        	calculateType=1;
            			}else if(tabPanelId=="ScrewPumpCalculateManagerPanel"){
            				calculateType=2;
            			}else if(tabPanelId=="ElectricInversionCalculateManagerPanel"){
            				calculateType=5;
            			}
                		
                		var url=context + '/calculateManagerController/exportCalculateRequestData?wellName='+URLencode(URLencode(wellName))+'&acqTime='+acqTime+'&calculateType='+calculateType;
                    	document.location.href = url;
                	}else{
                		Ext.MessageBox.alert("信息","未选择记录");
                	}
                }
            }
            ],
        	items: [{
        		region: 'center',
            	title: '井列表',
            	id: 'CalculateManagerWellListPanel_Id',
            	layout: "fit"
            },{
            	region: 'east',
            	title:'计算数据',
                width: '75%',
                border: false,
                collapsible: true, // 是否可折叠
                collapsed:false,//是否折叠
                split: true, // 竖折叠条
        		xtype: 'tabpanel',
        		id:"CalculateManagerTabPanel",
        		activeTab: 0,
        		tabPosition: 'bottom',
        		items: [{
        				title: '功图诊断计产',
        				layout: "fit",
        				id:'PumpingUnitCalculateManagerPanel',
        				border: false,
        				bbar: bbar,
        				html:'<div class=PumpingUnitCalculateManagerContainer" style="width:100%;height:100%;"><div class="con" id="PumpingUnitCalculateManagerDiv_id"></div></div>',
        			},{
        				title: '转速计产',
        				id:'ScrewPumpCalculateManagerPanel',
        				layout: "fit",
        				hidden:true,
        				border: false,
        				bbar: screwPumpBbar,
        				html:'<div class=ScrewPumpCalculateManagerContainer" style="width:100%;height:100%;"><div class="con" id="ScrewPumpCalculateManagerDiv_id"></div></div>',
        			},{
        				title: '全天评价',
        				id:'PumpingUnitDailyCalculateManagerPanel',
        				layout: "fit",
        				hidden:true,
        				border: false,
        				bbar: screwPumpBbar,
        				html:'<div class=PumpingUnitDailyCalculateManagerContainer" style="width:100%;height:100%;"><div class="con" id="PumpingUnitDailyCalculateManagerDiv_id"></div></div>',
        			},{
        				title: '电参反演',
        				id:'ElectricInversionCalculateManagerPanel',
        				layout: "fit",
        				hidden:false,
        				border: false,
        				bbar: screwPumpBbar,
        				html:'<div class=ElectricInversionCalculateManagerContainer" style="width:100%;height:100%;"><div class="con" id="ElectricInversionCalculateManagerDiv_id"></div></div>',
        			}],
        			listeners: {
        				tabchange: function (tabPanel, newCard,oldCard, obj) {
        					Ext.getCmp("CalculateManagerWellListComBox_Id").setValue("");
        					Ext.getCmp("CalculateManagerWellListComBox_Id").getStore().loadPage(1);
        					Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
        					if(newCard.id=="PumpingUnitCalculateManagerPanel"){
        						$("#ScrewPumpCalculateManagerDiv_id").html('');
        						$("#ElectricInversionCalculateManagerDiv_id").html('');
        						Ext.getCmp("calculateManager_LinkedData_Btn").show();
//        						Ext.getCmp("calculateManager_ExportData_Btn").show();
        					}else if(newCard.id=="ScrewPumpCalculateManagerPanel"){
        						$("#PumpingUnitCalculateManagerDiv_id").html('');
        						$("#ElectricInversionCalculateManagerDiv_id").html('');
        						Ext.getCmp("calculateManager_LinkedData_Btn").show();
//        						Ext.getCmp("calculateManager_ExportData_Btn").show();
        					}else if(newCard.id=="ElectricInversionCalculateManagerPanel"){
        						$("#PumpingUnitCalculateManagerDiv_id").html('');
        						$("#ScrewPumpCalculateManagerDiv_id").html('');
        						Ext.getCmp("calculateManager_LinkedData_Btn").hide();
//        						Ext.getCmp("calculateManager_ExportData_Btn").hide();
        					}
        					calculateResultStore.loadPage(1);
        					CalculateManagerWellListStore.load();
        				}
        			}
            	}]
        });
        me.callParent(arguments);
    }
});


function CreateAndLoadCalculateManagerTable(isNew,result,divid){
	if(isNew&&calculateManagerHandsontableHelper!=null){
        calculateManagerHandsontableHelper.clearContainer();
        calculateManagerHandsontableHelper.hot.destroy();
        calculateManagerHandsontableHelper=null;
	}
	
	if(calculateManagerHandsontableHelper==null){
		calculateManagerHandsontableHelper = CalculateManagerHandsontableHelper.createNew(divid);
		var colHeaders="[";
        var columns="[";
        for(var i=0;i<result.columns.length;i++){
        	colHeaders+="'"+result.columns[i].header+"'";
        	columns+="{data:'"+result.columns[i].dataIndex+"'";
        	if(result.columns[i].dataIndex.toUpperCase()=="id".toUpperCase()){
        		columns+=",type: 'checkbox'";
        	}else if(result.columns[i].dataIndex.toUpperCase()==="wellName".toUpperCase()||result.columns[i].dataIndex.toUpperCase()==="acqTime".toUpperCase()||result.columns[i].dataIndex.toUpperCase()==="workingConditionName".toUpperCase()){
    			
    		}else if(result.columns[i].dataIndex==="anchoringStateName"){
        		columns+=",type:'dropdown',strict:true,allowInvalid:false,source:['锚定', '未锚定']";
        	}else if(result.columns[i].dataIndex.toUpperCase()==="pumpGrade".toUpperCase()){
        		columns+=",type:'numeric',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_PumpGrade(val, callback,this.row, this.col,calculateManagerHandsontableHelper);}";
        	}else if(result.columns[i].dataIndex.toUpperCase()==="rodGrade1".toUpperCase() || result.columns[i].dataIndex.toUpperCase()==="rodGrade2".toUpperCase() || result.columns[i].dataIndex.toUpperCase()==="rodGrade3".toUpperCase() || result.columns[i].dataIndex.toUpperCase()==="rodGrade4".toUpperCase()){
        		columns+=",type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_RodGrade(val, callback,this.row, this.col,calculateManagerHandsontableHelper);}";
        	}else{
    			columns+=",type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,calculateManagerHandsontableHelper);}";
    		}
        	columns+="}";
        	if(i<result.columns.length-1){
        		colHeaders+=",";
            	columns+=",";
        	}
        }
        colHeaders+="]";
    	columns+="]";
    	calculateManagerHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
    	calculateManagerHandsontableHelper.columns=Ext.JSON.decode(columns);
		calculateManagerHandsontableHelper.createTable(result.totalRoot);
	}else{
		calculateManagerHandsontableHelper.hot.loadData(result.totalRoot);
	}
};


var CalculateManagerHandsontableHelper = {
	    createNew: function (divid) {
	        var calculateManagerHandsontableHelper = {};
	        calculateManagerHandsontableHelper.hot = '';
	        calculateManagerHandsontableHelper.divid = divid;
	        calculateManagerHandsontableHelper.validresult=true;//数据校验
	        calculateManagerHandsontableHelper.colHeaders=[];
	        calculateManagerHandsontableHelper.columns=[];
	        
	        calculateManagerHandsontableHelper.AllData={};
	        calculateManagerHandsontableHelper.updatelist=[];
	        calculateManagerHandsontableHelper.delidslist=[];
	        calculateManagerHandsontableHelper.insertlist=[];
	        
	        calculateManagerHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(184, 184, 184)';
	        }
	        
	        
	        calculateManagerHandsontableHelper.createTable = function (data) {
	        	$('#'+calculateManagerHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+calculateManagerHandsontableHelper.divid);
	        	calculateManagerHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	        		fixedColumnsLeft:4, //固定左侧多少列不能水平滚动
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: true
	                },
	                columns:calculateManagerHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:calculateManagerHandsontableHelper.colHeaders,//显示列头
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
	                    var tabPanelId = Ext.getCmp("CalculateManagerTabPanel").getActiveTab().id;
	                    if(tabPanelId=="PumpingUnitCalculateManagerPanel"){
	                    	if (visualColIndex >= 1 && visualColIndex <= 6) {
								cellProperties.readOnly = true;
								cellProperties.renderer = calculateManagerHandsontableHelper.addBoldBg;
			                }
						}else if(tabPanelId=="ScrewPumpCalculateManagerPanel"){
							if (visualColIndex >= 1 && visualColIndex <= 6) {
								cellProperties.readOnly = true;
								cellProperties.renderer = calculateManagerHandsontableHelper.addBoldBg;
			                }
						}else if(tabPanelId=="ElectricInversionCalculateManagerPanel"){
							if (visualColIndex >= 1 && visualColIndex <= 3) {
								cellProperties.readOnly = true;
								cellProperties.renderer = calculateManagerHandsontableHelper.addBoldBg;
			                }
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
	                            var rowdata = calculateManagerHandsontableHelper.hot.getDataAtRow(i);
	                            ids.push(rowdata[0]);
	                        }
	                        calculateManagerHandsontableHelper.delExpressCount(ids);
	                        calculateManagerHandsontableHelper.screening();
	                    }
	                },
	                afterChange: function (changes, source) {
	                    if (changes != null) {
	                    	for(var i=0;i<changes.length;i++){
	                    		var params = [];
	                    		var index = changes[i][0]; //行号码
		                        var rowdata = calculateManagerHandsontableHelper.hot.getDataAtRow(index);
		                        params.push(rowdata[0]);
		                        params.push(changes[i][1]);
		                        params.push(changes[i][2]);
		                        params.push(changes[i][3]);

		                        //仅当单元格发生改变的时候,id!=null,说明是更新
		                        if (params[2] != params[3] && params[0] != null && params[0] >0) {
		                        	var data="{";
		                        	for(var j=0;j<calculateManagerHandsontableHelper.columns.length;j++){
		                        		data+=calculateManagerHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
		                        		if(j<calculateManagerHandsontableHelper.columns.length-1){
		                        			data+=","
		                        		}
		                        	}
		                        	data+="}"
		                            calculateManagerHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
		                        }
	                    	}
	                        
	                    }
	                }
	        	});
	        }
	      //插入的数据的获取
	        calculateManagerHandsontableHelper.insertExpressCount=function() {
	            var idsdata = calculateManagerHandsontableHelper.hot.getDataAtCol(0); //所有的id
	            for (var i = 0; i < idsdata.length; i++) {
	                //id=null时,是插入数据,此时的i正好是行号
	                if (idsdata[i] == null||idsdata[i]<0) {
	                    //获得id=null时的所有数据封装进data
	                    var rowdata = calculateManagerHandsontableHelper.hot.getDataAtRow(i);
	                    //var collength = hot.countCols();
	                    if (rowdata != null) {
	                    	var data="{";
                        	for(var j=0;j<calculateManagerHandsontableHelper.columns.length;j++){
                        		data+=calculateManagerHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
                        		if(j<calculateManagerHandsontableHelper.columns.length-1){
                        			data+=","
                        		}
                        	}
                        	data+="}"
	                        calculateManagerHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
	                    }
	                }
	            }
	            if (calculateManagerHandsontableHelper.insertlist.length != 0) {
	            	calculateManagerHandsontableHelper.AllData.insertlist = calculateManagerHandsontableHelper.insertlist;
	            }
	        }
	        //保存数据
	        calculateManagerHandsontableHelper.saveData = function () {
        		//插入的数据的获取
	        	calculateManagerHandsontableHelper.insertExpressCount();
	            if (JSON.stringify(calculateManagerHandsontableHelper.AllData) != "{}" && calculateManagerHandsontableHelper.validresult) {
	            	var bbarId="pumpingCalculateManagerBbar";
	            	var wellType=200;
	            	var calculateType=1;//1-抽油机诊断计产 2-螺杆泵诊断计产 3-抽油机汇总计算  4-螺杆泵汇总计算 5-电参反演地面功图计算
                    var tabPanelId = Ext.getCmp("CalculateManagerTabPanel").getActiveTab().id;
                    if(tabPanelId=="PumpingUnitCalculateManagerPanel"){
                    	bbarId="pumpingCalculateManagerBbar";
                    	wellType=200;
                    	calculateType=1;
					}else if(tabPanelId=="ScrewPumpCalculateManagerPanel"){
						bbarId="screwPumpCalculateManagerBbar";
						wellType=400;
						calculateType=2;
					}else if(tabPanelId=="ElectricInversionCalculateManagerPanel"){
						bbarId="elecInverCalculateManagerBbar";
						calculateType=5;
					}
	            	Ext.Ajax.request({
	            		method:'POST',
	            		url:context + '/calculateManagerController/saveRecalculateData',
	            		success:function(response) {
	            			var rdata=Ext.JSON.decode(response.responseText);
	            			if (rdata.success) {
	                        	var successInfo='保存成功，开始重新计算，点击左下角刷新按钮查看计算状态列数值，无0和2时，计算完成。';
	                            //保存以后重置全局容器
	                            calculateManagerHandsontableHelper.clearContainer();
	                            var bbarId="pumpingCalculateManagerBbar";
	                            var tabPanelId = Ext.getCmp("CalculateManagerTabPanel").getActiveTab().id;
	                            if(tabPanelId=="PumpingUnitCalculateManagerPanel"){
	                            	bbarId="pumpingCalculateManagerBbar";
	        					}else if(tabPanelId=="ScrewPumpCalculateManagerPanel"){
	        						bbarId="screwPumpCalculateManagerBbar";
	        					}else if(tabPanelId=="ElectricInversionCalculateManagerPanel"){
	        						bbarId="elecInverCalculateManagerBbar";
	        					}
	                            Ext.MessageBox.alert("信息",successInfo);
	                            Ext.getCmp(bbarId).getStore().loadPage(1);
	                        } else {
	                        	Ext.MessageBox.alert("信息","数据保存失败");

	                        }
	            		},
	            		failure:function(){
	            			Ext.MessageBox.alert("信息","请求失败");
	                        calculateManagerHandsontableHelper.clearContainer();
	            		},
	            		params: {
	                    	data: JSON.stringify(calculateManagerHandsontableHelper.AllData),
	                    	wellType: wellType,
	                    	calculateType: calculateType
	                    }
	            	}); 
	            } else {
	                if (!calculateManagerHandsontableHelper.validresult) {
	                	Ext.MessageBox.alert("信息","数据类型错误");
	                } else {
	                	Ext.MessageBox.alert("信息","无数据变化");
	                }
	            }
	        }
	        
	        
	      //删除的优先级最高
	        calculateManagerHandsontableHelper.delExpressCount=function(ids) {
	            //传入的ids.length不可能为0
	            $.each(ids, function (index, id) {
	                if (id != null) {
	                	calculateManagerHandsontableHelper.delidslist.push(id);
	                }
	            });
	            calculateManagerHandsontableHelper.AllData.delidslist = calculateManagerHandsontableHelper.delidslist;
	        }

	        //updatelist数据更新
	        calculateManagerHandsontableHelper.screening=function() {
	            if (calculateManagerHandsontableHelper.updatelist.length != 0 && calculateManagerHandsontableHelper.delidslist.lentgh != 0) {
	                for (var i = 0; i < calculateManagerHandsontableHelper.delidslist.length; i++) {
	                    for (var j = 0; j < calculateManagerHandsontableHelper.updatelist.length; j++) {
	                        if (calculateManagerHandsontableHelper.updatelist[j].id == calculateManagerHandsontableHelper.delidslist[i]) {
	                            //更新updatelist
	                        	calculateManagerHandsontableHelper.updatelist.splice(j, 1);
	                        }
	                    }
	                }
	                //把updatelist封装进AllData
	                calculateManagerHandsontableHelper.AllData.updatelist = calculateManagerHandsontableHelper.updatelist;
	            }
	        }
	        
	      //更新数据
	        calculateManagerHandsontableHelper.updateExpressCount=function(data) {
	            if (JSON.stringify(data) != "{}") {
	                var flag = true;
	                //判断记录是否存在,更新数据     
	                $.each(calculateManagerHandsontableHelper.updatelist, function (index, node) {
	                    if (node.id == data.id) {
	                        //此记录已经有了
	                        flag = false;
	                        //用新得到的记录替换原来的,不用新增
	                        calculateManagerHandsontableHelper.updatelist[index] = data;
	                    }
	                });
	                flag && calculateManagerHandsontableHelper.updatelist.push(data);
	                //封装
	                calculateManagerHandsontableHelper.AllData.updatelist = calculateManagerHandsontableHelper.updatelist;
	            }
	        }
	        
	        calculateManagerHandsontableHelper.clearContainer = function () {
	        	calculateManagerHandsontableHelper.AllData = {};
	        	calculateManagerHandsontableHelper.updatelist = [];
	        	calculateManagerHandsontableHelper.delidslist = [];
	        	calculateManagerHandsontableHelper.insertlist = [];
	        }
	        
	        return calculateManagerHandsontableHelper;
	    }
};

function createCalculateManagerWellListWellListDataColumn(columnInfo) {
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
        }else if (attr.dataIndex.toUpperCase()=='deviceId'.toUpperCase()) {
            myColumns += ",sortable : false,locked:true,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
        } else if (attr.dataIndex.toUpperCase() == 'acqTime'.toUpperCase()) {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}";
        } else {
            myColumns += hidden_ + lock_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
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