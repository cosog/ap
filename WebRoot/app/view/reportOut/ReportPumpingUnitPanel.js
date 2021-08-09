//<!-- 报表——采出井——抽油机 -->
var diagnoseDailyReportHelper=null
Ext.define("AP.view.reportOut.ReportPumpingUnitPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.reportPumpingUnitPanel',
    layout: 'fit',
    id: 'ReportPumpingUnitPanel_view',
    border: false,
    initComponent: function () {
        var me = this;
        var RPCDailyReportWellListStore = Ext.create("AP.store.reportOut.RPCDailyReportWellListStore");
        /** 
         * 定义降序的groupingStore 
         */
        var wellListStore = new Ext.data.JsonStore({
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
            autoLoad: false,
            listeners: {
                beforeload: function (store, options) {
                    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var wellName = Ext.getCmp('ReportPumpingUnitPanelWellListCombo_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        wellName:wellName,
                        wellType:200
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var wellListCombo = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.wellName,
                id: 'ReportPumpingUnitPanelWellListCombo_Id',
                store: wellListStore,
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
//                        wellListCombo.clearValue();
                        wellListCombo.getStore().loadPage(1); // 加载井下拉框的store
                    },
                    specialkey: function (field, e) {
                        onEnterKeyDownFN(field, e, 'ReportPumpingUnitDayReport_Id');
                    },
                    select: function (combo, record, index) {
                        try {
//                        	ReportPumpUnitDayStore.load();
                        	CreateDiagnosisDailyReportTable();
                        } catch (ex) {
                            Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                        }
                    }
                }
            });
        Ext.apply(me, {
            tbar: [wellListCombo, {
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: '日期',
                labelWidth: 36,
                width: 126,
                format: 'Y-m-d',
                id: 'ReportPumpingUnitPanelCalculateDate_Id',
                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                        try {
                        	CreateDiagnosisDailyReportTable();
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
                width: 105,
                format: 'Y-m-d ',
                id: 'ReportPumpingUnitPanelCalculateEndDate_Id',
                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                        try {
                        	CreateDiagnosisDailyReportTable();
                        } catch (ex) {
                            Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                        }
                    }
                }
            }, {
                xtype: 'button',
                text: cosog.string.search,
                pressed: true,
                hidden:true,
                iconCls: 'search',
                handler: function (v, o) {
                	CreateDiagnosisDailyReportTable();
                }
            }, {
                xtype: 'button',
                text: cosog.string.exportExcel,
                pressed: true,
                handler: function (v, o) {
                	var leftOrg_Id = obtainParams('leftOrg_Id');
                	var wellName = Ext.getCmp('ReportPumpingUnitPanelWellListCombo_Id').getValue();
                	var calculateDate = Ext.getCmp('ReportPumpingUnitPanelCalculateDate_Id').rawValue;
                	var calculateEndDate = Ext.getCmp('ReportPumpingUnitPanelCalculateEndDate_Id').rawValue;
                	var url=context + '/reportPumpingUnitDataController/exportRPCDailyReportData?wellType=200&wellName='+URLencode(URLencode(wellName))+'&calculateDate='+calculateDate+'&calculateEndDate='+calculateEndDate+'&orgId='+leftOrg_Id;
                	document.location.href = url;
                }
            },'-',{
                xtype: 'button',
                iconCls: 'note-refresh',
                text: cosog.string.refresh,
                pressed: true,
                hidden:false,
                handler: function (v, o) {
                	CreateDiagnosisDailyReportTable();
                }
    		}, '->', {
                id: 'PumpingUnitDailyReportTotalCount_Id',
                xtype: 'component',
                tpl: cosog.string.totalCount + ': {count}',
                style: 'margin-right:15px'
            }],
            layout: 'border',
            border: false,
            items: [{
            	region: 'west',
            	width: '20%',
            	title: '井列表',
            	id: 'RPCDailyReportWellListPanel_Id',
            	collapsible: true, // 是否可折叠
                collapsed:false,//是否折叠
                split: true, // 竖折叠条
            	layout: "fit"
            },{
            	region: 'center',
            	title:'报表数据',
                border: false,
                layout: "fit",
                html:'<div class="DiagnosisDailyReportContainer" style="width:100%;height:100%;"><div class="con" id="DiagnosisDailyReportDiv_id"></div></div>',
                listeners: {
                	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                		CreateDiagnosisDailyReportTable();
                	}
                }
            }]

        });
        me.callParent(arguments);
    }
});

function CreateDiagnosisDailyReportTable(){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var wellName = Ext.getCmp('ReportPumpingUnitPanelWellListCombo_Id').getValue();
    var calculateDate = Ext.getCmp('ReportPumpingUnitPanelCalculateDate_Id').rawValue;
    var calculateEndDate = Ext.getCmp('ReportPumpingUnitPanelCalculateEndDate_Id').rawValue;
    
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reportPumpingUnitDataController/showDiagnosisDailyReportData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(Ext.getCmp("ReportPumpingUnitPanelCalculateDate_Id").getValue()==''||Ext.getCmp("ReportPumpingUnitPanelCalculateDate_Id").getValue()==null){
            	Ext.getCmp("ReportPumpingUnitPanelCalculateDate_Id").setValue(result.calculateDate);
            	Ext.getCmp("ReportPumpingUnitPanelCalculateDate_Id").setRawValue(result.calculateDate);
            }
			diagnoseDailyReportHelper = DiagnoseDailyReportHelper.createNew("DiagnosisDailyReportDiv_id","DiagnosisDailyReportContainer");
			diagnoseDailyReportHelper.getData(result);
			diagnoseDailyReportHelper.createTable();
			Ext.getCmp("PumpingUnitDailyReportTotalCount_Id").update({count: result.totalCount});
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId: orgId,
			wellName: wellName,
			calculateDate: calculateDate,
			calculateEndDate: calculateEndDate,
            wellType:200
        }
	});
};


var DiagnoseDailyReportHelper = {
	    createNew: function (divid, containerid) {
	        var diagnoseDailyReportHelper = {};
	        diagnoseDailyReportHelper.get_data = {};
	        diagnoseDailyReportHelper.hot = '';
	        diagnoseDailyReportHelper.container = document.getElementById(divid);
	        diagnoseDailyReportHelper.last_index = 0;
	        diagnoseDailyReportHelper.calculation_type_computer = [];
	        diagnoseDailyReportHelper.calculation_type_not_computer = [];
	        diagnoseDailyReportHelper.editable = 0;
	        diagnoseDailyReportHelper.sum = 0;
	        diagnoseDailyReportHelper.editRecords = [];
	        var productionUnitStr='t/d';
	        if(productionUnit!=0){
	        	productionUnitStr='m^3/d';
	        }
	        diagnoseDailyReportHelper.my_data = [
	    ['抽油机井生产报表', '', '', '', '', '', '', '', '', '',  '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
	    ['序号', '井名', '日期','通信','','', '时率', '','', '工况','', '产量', '','','','','平衡','','','','','效率', '', '','','日用电量(kW·h)', '备注'],
	    ['', '', '','在线时间(h)','在线区间', '在线时率(小数)','运行时间(h)','运行区间', '运行时率(小数)','功图工况','优化建议','产液量（'+productionUnitStr+'）', '产油量（'+productionUnitStr+'）','产水量（'+productionUnitStr+'）', '含水率(%)','充满系数(小数)','功率平衡状态','功率平衡度(%)','电流平衡状态','电流平衡度(%)','移动距离(cm)', '系统效率(%)', '地面效率(%)', '井下效率(%)','吨液百米耗电量(kW·h/100·t)','','']
//	    ['合计', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
//	    ['平均', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '']
	  ];
	        diagnoseDailyReportHelper.updateArray = function () {
	            for (var i = 0; i < diagnoseDailyReportHelper.sum; i++) {
	                diagnoseDailyReportHelper.my_data.splice(i + 3, 0, ['', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '']);
	            }
	        }
	        diagnoseDailyReportHelper.clearArray = function () {
	            diagnoseDailyReportHelper.hot.loadData(diagnoseDailyReportHelper.table_header);

	        }

	        diagnoseDailyReportHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	            if (row <= 2&&row>=1) {
	                td.style.fontWeight = 'bold';
					td.style.fontSize = '13px';
					td.style.color = 'rgb(0, 0, 51)';
					td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	            }
	        }
			
			diagnoseDailyReportHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (row < 1) {
	                td.style.fontWeight = 'bold';
			        td.style.fontSize = '25px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '50px';   
			    }      
	        }
			
			diagnoseDailyReportHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
		         if(row < 3){
	                 td.style.fontWeight = 'bold';
			         td.style.fontSize = '5px';
			         td.style.fontFamily = 'SimHei';
	            }      
	        }
			

	        diagnoseDailyReportHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        diagnoseDailyReportHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(216, 228, 188)';
	        }
	        
	        diagnoseDailyReportHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        diagnoseDailyReportHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        diagnoseDailyReportHelper.createTable = function () {
	            diagnoseDailyReportHelper.container.innerHTML = "";
	            diagnoseDailyReportHelper.hot = new Handsontable(diagnoseDailyReportHelper.container, {
	                data: diagnoseDailyReportHelper.my_data,
	                fixedRowsTop:3, //固定顶部多少行不能垂直滚动
	                fixedRowsBottom: 2,//固定底部多少行不能垂直滚动
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: [50],
					colWidths:[50,90,75, 80,100,70, 80,100,70, 140,120, 80,80,80,80,80, 80,80,80,80,80,  80,80,80,120, 80, 75],
					stretchH: 'all',
	                mergeCells: [
	                    {
	                        "row": 0,
	                        "col": 0,
	                        "rowspan": 1,
	                        "colspan": 27
	                    },{
	                        "row": 1,
	                        "col": 0,
	                        "rowspan": 2,
	                        "colspan": 1
	                    },{
	                        "row": 1,
	                        "col": 1,
	                        "rowspan": 2,
	                        "colspan": 1
	                    },{
	                        "row": 1,
	                        "col": 2,
	                        "rowspan": 2,
	                        "colspan": 1
	                    },{//通信
	                        "row": 1,
	                        "col": 3,
	                        "rowspan": 1,
	                        "colspan": 3
	                    },{//时率
	                        "row": 1,
	                        "col": 6,
	                        "rowspan": 1,
	                        "colspan": 3
	                    },{//工况
	                        "row": 1,
	                        "col": 9,
	                        "rowspan": 1,
	                        "colspan": 2
	                    },{//产量
	                        "row": 1,
	                        "col": 11,
	                        "rowspan": 1,
	                        "colspan": 5
	                    },{//平衡
	                        "row": 1,
	                        "col": 16,
	                        "rowspan": 1,
	                        "colspan": 5
	                    },{//效率
	                        "row": 1,
	                        "col": 21,
	                        "rowspan": 1,
	                        "colspan": 4
	                    },{//日用电量
	                        "row": 1,
	                        "col": 25,
	                        "rowspan": 2,
	                        "colspan": 1
	                    },{//备注
	                        "row": 1,
	                        "col": 26,
	                        "rowspan": 2,
	                        "colspan": 1
	                    }],
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);

	                    cellProperties.readOnly = true;
	                    // 表头
	                    if (visualRowIndex <= 2 && visualRowIndex >= 1) {
	                        cellProperties.renderer = diagnoseDailyReportHelper.addBoldBg;
	                    }
	                    
	                    // 合计
//	                    if (visualRowIndex ==diagnoseDailyReportHelper.last_index) {
//	                        cellProperties.renderer = diagnoseDailyReportHelper.addBoldBg;
//	                    }
						
						if (visualRowIndex < 1 ) {
	                       cellProperties.renderer = diagnoseDailyReportHelper.addSizeBg;
	                    }
						
						if (visualColIndex === 26&&visualRowIndex>2&&visualRowIndex<diagnoseDailyReportHelper.last_index) {
							cellProperties.readOnly = false;
		                }
						
						
	                    return cellProperties;
	                },
//	                columnSummary: [
//	                    {
//	                        destinationRow: diagnoseDailyReportHelper.last_index,
//	                        destinationColumn: 3,
//	                        type: 'sum',
//	                        forceNumeric: true
//	                    },
//	                    {
//	                        destinationRow: diagnoseDailyReportHelper.last_index,
//	                        destinationColumn: 6,
//	                        type: 'sum',
//	                        forceNumeric: true
//	                    },
//	                    {
//	                        destinationRow: diagnoseDailyReportHelper.last_index,
//	                        destinationColumn: 11,
//	                        type: 'sum',
//	                        forceNumeric: true
//	                    },
//						{
//	                        destinationRow: diagnoseDailyReportHelper.last_index,
//	                        destinationColumn: 12,
//	                        type: 'sum',
//	                        forceNumeric: true
//						},
//						{
//	                        destinationRow: diagnoseDailyReportHelper.last_index,
//	                        destinationColumn: 13,
//	                        type: 'sum',
//	                        forceNumeric: true
//						},
//						{
//	                        destinationRow: diagnoseDailyReportHelper.last_index+1,
//	                        destinationColumn: 3,
//	                        type: 'average',
//	                        
//	                        roundFloat:true,
//	                        forceNumeric: true,
////	                        reversedRowCoords: true,
//	                        suppressDataTypeErrors: true,
//	                        readOnly: true
//	                    },{
//	                        destinationRow: diagnoseDailyReportHelper.last_index+1,
//	                        destinationColumn: 6,
//	                        type: 'average',
//	                        roundFloat:true,
//	                        forceNumeric: true,
////	                        reversedRowCoords: true,
//	                        suppressDataTypeErrors: true,
//	                        readOnly: true
//	                    },
//	                    {
//	                        destinationRow: diagnoseDailyReportHelper.last_index+1,
//	                        destinationColumn: 11,
//	                        type: 'average',
//	                        roundFloat:true,
//	                        forceNumeric: true,
////	                        reversedRowCoords: true,
//	                        suppressDataTypeErrors: true,
//	                        readOnly: true
//	                    },
//						{
//	                        destinationRow: diagnoseDailyReportHelper.last_index+1,
//	                        destinationColumn: 12,
//	                        type: 'average',
//	                        roundFloat:true,
//	                        forceNumeric: true,
////	                        reversedRowCoords: true,
//	                        suppressDataTypeErrors: true,
//	                        readOnly: true
//						},
//						{
//	                        destinationRow: diagnoseDailyReportHelper.last_index+1,
//	                        destinationColumn: 13,
//	                        type: 'average',
//	                        roundFloat:true,
//	                        forceNumeric: true,
////	                        reversedRowCoords: true,
//	                        suppressDataTypeErrors: true,
//	                        readOnly: true
//						}
//	                ],
	                afterChange:function(changes, source){}
	            });
	        }



	        diagnoseDailyReportHelper.getData = function (data) {
	            diagnoseDailyReportHelper.get_data = data;
	            diagnoseDailyReportHelper.editable = +data.Editable;
	            var _daily = data.totalRoot;
	            diagnoseDailyReportHelper.sum = _daily.length;
	            diagnoseDailyReportHelper.updateArray();
	            _daily.forEach(function (_day, index) {

	            	if(_day.id=="合计" || _day.id=="平均"){
	            		diagnoseDailyReportHelper.my_data[index + 3][0] = _day.id;
	            	}else{
	            		diagnoseDailyReportHelper.my_data[index + 3][0] = index+1;
	            	}
	                
	                diagnoseDailyReportHelper.my_data[index + 3][1] = _day.wellName;
	                diagnoseDailyReportHelper.my_data[index + 3][2] = _day.calculateDate;
	                
	                diagnoseDailyReportHelper.my_data[index + 3][3] = _day.commTime;
	                var commRange=_day.commRange;
	                if(commRange.length>12){
	                	commRange=commRange.substring(0, 11)+"...";
	                }
	                var runRange=_day.runRange;
	                if(runRange.length>12){
	                	runRange=runRange.substring(0, 11)+"...";
	                }
	                diagnoseDailyReportHelper.my_data[index + 3][4] = commRange;
	                diagnoseDailyReportHelper.my_data[index + 3][5] = _day.commTimeEfficiency;
	                
	                diagnoseDailyReportHelper.my_data[index + 3][6] = _day.runTime;
	                diagnoseDailyReportHelper.my_data[index + 3][7] = runRange;
	                diagnoseDailyReportHelper.my_data[index + 3][8] = _day.runTimeEfficiency;
	                
	                diagnoseDailyReportHelper.my_data[index + 3][9] = _day.resultName;
	                diagnoseDailyReportHelper.my_data[index + 3][10] = _day.optimizationSuggestion;
	                
	                diagnoseDailyReportHelper.my_data[index + 3][11] = _day.liquidProduction;
					diagnoseDailyReportHelper.my_data[index + 3][12] = _day.oilProduction;
	                diagnoseDailyReportHelper.my_data[index + 3][13] = _day.waterProduction;
	                diagnoseDailyReportHelper.my_data[index + 3][14] = _day.waterCut;
	                diagnoseDailyReportHelper.my_data[index + 3][15] = _day.fullnesscoEfficient;
	                
	                diagnoseDailyReportHelper.my_data[index + 3][16] = _day.wattDegreeBalanceLevel;
	                diagnoseDailyReportHelper.my_data[index + 3][17] = _day.wattDegreeBalance;
	                diagnoseDailyReportHelper.my_data[index + 3][18] = _day.iDegreeBalanceLevel;
	                diagnoseDailyReportHelper.my_data[index + 3][19] = _day.iDegreeBalance;
	                diagnoseDailyReportHelper.my_data[index + 3][20] = _day.deltaRadius;
	                
	                diagnoseDailyReportHelper.my_data[index + 3][21] = _day.systemEfficiency;
	                diagnoseDailyReportHelper.my_data[index + 3][22] = _day.surfaceSystemEfficiency;
	                diagnoseDailyReportHelper.my_data[index + 3][23] = _day.welldownSystemEfficiency;
	                diagnoseDailyReportHelper.my_data[index + 3][24] = _day.energyPer100mLift;
	                
	                diagnoseDailyReportHelper.my_data[index + 3][25] = _day.todayKWattH;
	                
	                diagnoseDailyReportHelper.my_data[index + 3][26] = _day.remark;
	            })

	            var _total = data.totalCount;
	            diagnoseDailyReportHelper.last_index = _daily.length + 3;
	        }

	        var init = function () {
	        }

	        init();
	        return diagnoseDailyReportHelper;
	    }
	};

function createRPCDailyReportWellListDataColumn(columnInfo) {
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