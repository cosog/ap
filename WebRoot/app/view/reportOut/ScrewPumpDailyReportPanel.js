//<!-- 报表——采出井——螺杆泵 -->
var screwPumpDailyReportHelper=null
Ext.define("AP.view.reportOut.ScrewPumpDailyReportPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.screwPumpDailyReportPanel',
    layout: 'fit',
    id: 'ScrewPumpDailyReportPanel_Id',
    border: false,
    initComponent: function () {
        var me = this;
        var PPCDailyReportWellListStore = Ext.create("AP.store.reportOut.PPCDailyReportWellListStore");
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
                    var wellName = Ext.getCmp('ReportScrewPumpPanelWellListCombo_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        wellName:wellName,
                        wellType:400
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var wellListCombo = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.wellName,
                id: 'ReportScrewPumpPanelWellListCombo_Id',
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
                        wellListCombo.getStore().loadPage(1); // 加载井下拉框的store
                    },
                    select: function (combo, record, index) {
                        try {
                        	CreateScrewPumpDailyReportTable();
                        } catch (ex) {
                            Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                        }
                    }
                }
            });
        Ext.apply(me, {
            tbar: [wellListCombo,{
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: '日期',
                labelWidth: 36,
                width: 126,
                format: 'Y-m-d',
                id: 'ReportScrewPumpPanelCalculateDate_Id',
                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                        try {
                        	CreateScrewPumpDailyReportTable();
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
                id: 'ReportScrewPumpPanelCalculateEndDate_Id',
                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                        try {
                        	CreateScrewPumpDailyReportTable();
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
                	CreateScrewPumpDailyReportTable();
                }
            }, {
                xtype: 'button',
                text: cosog.string.exportExcel,
                pressed: true,
                handler: function (v, o) {
                	var leftOrg_Id = obtainParams('leftOrg_Id');
                	var wellName = obtainParams('ReportScrewPumpPanelWellListCombo_Id');
                	var calculateDate = Ext.getCmp('ReportScrewPumpPanelCalculateDate_Id').rawValue;
                	var url=context + '/reportPumpingUnitDataController/exportPCPDailyReportData?wellType=400&wellName='+URLencode(URLencode(wellName))+'&calculateDate='+calculateDate+'&orgId='+leftOrg_Id;
                	document.location.href = url;
                }
            },'-',{
                xtype: 'button',
                iconCls: 'note-refresh',
                text: cosog.string.refresh,
                pressed: true,
                hidden:false,
                handler: function (v, o) {
                	CreateScrewPumpDailyReportTable();
                }
            
    		}, '->', {
                id: 'ScrewPumpDailyReportTotalCount_Id',
                xtype: 'component',
                tpl: cosog.string.totalCount + ': {count}',
                style: 'margin-right:15px'
            }],
            layout: 'border',
            border: false,
            items: [{
            	region: 'center',
            	title: '井列表',
            	id: 'PPCDailyReportWellListPanel_Id',
            	layout: "fit"
            },{
            	region: 'east',
            	title:'报表数据',
                width: '80%',
                border: false,
                collapsible: true, // 是否可折叠
                collapsed:false,//是否折叠
                split: true, // 竖折叠条
                layout: "fit",
                html:'<div class="ScrewPumpDailyReportContainer" style="width:100%;height:100%;"><div class="con" id="ScrewPumpDailyReportDiv_id"></div></div>',
                listeners: {
                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    	CreateScrewPumpDailyReportTable();
                    }
                }
            }]
        });
        me.callParent(arguments);
    }
});

function CreateScrewPumpDailyReportTable(){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var wellName = Ext.getCmp('ReportScrewPumpPanelWellListCombo_Id').getValue();
    var calculateDate = Ext.getCmp('ReportScrewPumpPanelCalculateDate_Id').rawValue;
    var calculateEndDate = Ext.getCmp('ReportScrewPumpPanelCalculateEndDate_Id').rawValue;
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reportPumpingUnitDataController/showDiagnosisDailyReportData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(Ext.getCmp("ReportScrewPumpPanelCalculateDate_Id").getValue()==''||Ext.getCmp("ReportScrewPumpPanelCalculateDate_Id").getValue()==null){
            	Ext.getCmp("ReportScrewPumpPanelCalculateDate_Id").setValue(result.calculateDate);
            	Ext.getCmp("ReportScrewPumpPanelCalculateDate_Id").setRawValue(result.calculateDate);
            }
			screwPumpDailyReportHelper = ScrewPumpDailyReportHelper.createNew("ScrewPumpDailyReportDiv_id","ScrewPumpDailyReportContainer");
			screwPumpDailyReportHelper.getData(result);
			screwPumpDailyReportHelper.createTable();
			Ext.getCmp("ScrewPumpDailyReportTotalCount_Id").update({count: result.totalCount});
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId: orgId,
            wellName: wellName,
            calculateDate: calculateDate,
            calculateEndDate: calculateEndDate,
            wellType:400
        }
	});
};


var ScrewPumpDailyReportHelper = {
	    createNew: function (divid, containerid) {
	        var screwPumpDailyReportHelper = {};
	        screwPumpDailyReportHelper.get_data = {};
	        screwPumpDailyReportHelper.hot = '';
	        screwPumpDailyReportHelper.container = document.getElementById(divid);
	        screwPumpDailyReportHelper.last_index = 0;
	        screwPumpDailyReportHelper.calculation_type_computer = [];
	        screwPumpDailyReportHelper.calculation_type_not_computer = [];
	        screwPumpDailyReportHelper.editable = 0;
	        screwPumpDailyReportHelper.sum = 0;
	        screwPumpDailyReportHelper.editRecords = [];
	        var productionUnitStr='t/d';
	        if(productionUnit!=0){
	        	productionUnitStr='m^3/d';
	        }
	        screwPumpDailyReportHelper.my_data = [
	    ['螺杆泵井生产报表', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
	    ['序号', '井名', '日期','通信','','', '时率', '','', '产量', '','','','','','','','效率','','日用电量(kW·h)', '备注'],
	    ['', '', '','在线时间(h)','在线区间', '在线时率(小数)','运行时间(h)','运行区间', '运行时率(小数)','产液量（'+productionUnitStr+'）', '产油量（'+productionUnitStr+'）','产水量（'+productionUnitStr+'）', '含水率(%)','转速(r/min)','泵挂(m)','动液面(m)','沉没度(m)','系统效率(%)','吨液百米耗电量(kW·h/100·t)','',''],
	    ['合计', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
	    ['平均', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '']
	  ];
	        screwPumpDailyReportHelper.updateArray = function () {
	            for (var i = 0; i < screwPumpDailyReportHelper.sum; i++) {
	                screwPumpDailyReportHelper.my_data.splice(i + 3, 0, ['', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '']);
	            }
	        }
	        screwPumpDailyReportHelper.clearArray = function () {
	            screwPumpDailyReportHelper.hot.loadData(screwPumpDailyReportHelper.table_header);

	        }

	        screwPumpDailyReportHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	            if (row <= 2&&row>=1) {
	                td.style.fontWeight = 'bold';
					td.style.fontSize = '13px';
					td.style.color = 'rgb(0, 0, 51)';
					td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	            }
	        }
			
			screwPumpDailyReportHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (row < 1) {
	                td.style.fontWeight = 'bold';
			        td.style.fontSize = '25px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '50px';   
			    }      
	        }
			
			screwPumpDailyReportHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
		         if(row < 3){
	                 td.style.fontWeight = 'bold';
			         td.style.fontSize = '5px';
			         td.style.fontFamily = 'SimHei';
	            }      
	        }
			

	        screwPumpDailyReportHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        screwPumpDailyReportHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(216, 228, 188)';
	        }
	        
	        screwPumpDailyReportHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        screwPumpDailyReportHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        screwPumpDailyReportHelper.createTable = function () {
	            screwPumpDailyReportHelper.container.innerHTML = "";
	            screwPumpDailyReportHelper.hot = new Handsontable(screwPumpDailyReportHelper.container, {
	                data: screwPumpDailyReportHelper.my_data,
	                fixedRowsTop:3, //固定顶部多少行不能垂直滚动
	                fixedRowsBottom: 2,//固定底部多少行不能垂直滚动
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: [50],
					colWidths:[50,90,75,80,100,70,80,100,70,80,80,80,80,80,80,80,80,80,120,80,75],
					stretchH: 'all',
	                mergeCells: [
	                    {
	                        "row": 0,
	                        "col": 0,
	                        "rowspan": 1,
	                        "colspan": 21
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
	                    },{//产量
	                        "row": 1,
	                        "col": 9,
	                        "rowspan": 1,
	                        "colspan": 8
	                    },{//效率
	                        "row": 1,
	                        "col": 17,
	                        "rowspan": 1,
	                        "colspan": 2
	                    },{//日用电量
	                        "row": 1,
	                        "col": 19,
	                        "rowspan": 2,
	                        "colspan": 1
	                    },{//备注
	                        "row": 1,
	                        "col": 20,
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
	                        cellProperties.renderer = screwPumpDailyReportHelper.addBoldBg;
	                    }
	                    
	                    // 合计
//	                    if (visualRowIndex ==screwPumpDailyReportHelper.last_index) {
//	                        cellProperties.renderer = screwPumpDailyReportHelper.addBoldBg;
//	                    }
						
						if (visualRowIndex < 1 ) {
	                       cellProperties.renderer = screwPumpDailyReportHelper.addSizeBg;
	                    }
						
						if (visualColIndex === 20&&visualRowIndex>2&&visualRowIndex<screwPumpDailyReportHelper.last_index) {
							cellProperties.readOnly = false;
		                }
						
						
	                    return cellProperties;
	                },
	                columnSummary: [
	                    {
	                        destinationRow: screwPumpDailyReportHelper.last_index,
	                        destinationColumn: 3,
	                        type: 'sum',
	                        forceNumeric: true
	                    },
	                    {
	                        destinationRow: screwPumpDailyReportHelper.last_index,
	                        destinationColumn: 6,
	                        type: 'sum',
	                        forceNumeric: true
	                    },
	                    {
	                        destinationRow: screwPumpDailyReportHelper.last_index,
	                        destinationColumn: 9,
	                        type: 'sum',
	                        forceNumeric: true
	                    },
						{
	                        destinationRow: screwPumpDailyReportHelper.last_index,
	                        destinationColumn: 10,
	                        type: 'sum',
	                        forceNumeric: true
						},
						{
	                        destinationRow: screwPumpDailyReportHelper.last_index,
	                        destinationColumn: 11,
	                        type: 'sum',
	                        forceNumeric: true
						},
						{
	                        destinationRow: screwPumpDailyReportHelper.last_index+1,
	                        destinationColumn: 3,
	                        type: 'average',
	                        roundFloat:true,
	                        forceNumeric: true
	                    },
	                    {
	                        destinationRow: screwPumpDailyReportHelper.last_index+1,
	                        destinationColumn: 6,
	                        type: 'average',
	                        roundFloat:true,
	                        forceNumeric: true
	                    },
	                    {
	                        destinationRow: screwPumpDailyReportHelper.last_index+1,
	                        destinationColumn: 9,
	                        type: 'average',
	                        roundFloat:true,
	                        forceNumeric: true
	                    },
						{
	                        destinationRow: screwPumpDailyReportHelper.last_index+1,
	                        destinationColumn: 10,
	                        type: 'average',
	                        roundFloat:true,
	                        forceNumeric: true
						},
						{
	                        destinationRow: screwPumpDailyReportHelper.last_index+1,
	                        destinationColumn: 11,
	                        type: 'average',
	                        roundFloat:true,
	                        forceNumeric: true
						}
	                ],
	                afterChange:function(changes, source){}
	            });
	        }



	        screwPumpDailyReportHelper.getData = function (data) {
	            screwPumpDailyReportHelper.get_data = data;
	            screwPumpDailyReportHelper.editable = +data.Editable;
	            var _daily = data.totalRoot;
	            screwPumpDailyReportHelper.sum = _daily.length;
	            screwPumpDailyReportHelper.updateArray();
	            _daily.forEach(function (_day, index) {

	                screwPumpDailyReportHelper.my_data[index + 3][0] = index+1;
	                screwPumpDailyReportHelper.my_data[index + 3][1] = _day.wellName;
	                screwPumpDailyReportHelper.my_data[index + 3][2] = _day.calculateDate;
	                
	                screwPumpDailyReportHelper.my_data[index + 3][3] = _day.commTime;
	                var commRange=_day.commRange;
	                if(commRange.length>12){
	                	commRange=commRange.substring(0, 11)+"...";
	                }
	                var runRange=_day.runRange;
	                if(runRange.length>12){
	                	runRange=runRange.substring(0, 11)+"...";
	                }
	                screwPumpDailyReportHelper.my_data[index + 3][4] = commRange;
	                screwPumpDailyReportHelper.my_data[index + 3][5] = _day.commTimeEfficiency;
	                
	                screwPumpDailyReportHelper.my_data[index + 3][6] = _day.runTime;
	                screwPumpDailyReportHelper.my_data[index + 3][7] = runRange;
	                screwPumpDailyReportHelper.my_data[index + 3][8] = _day.runTimeEfficiency;
	                
	                
	                screwPumpDailyReportHelper.my_data[index + 3][9] = _day.liquidProduction;
					screwPumpDailyReportHelper.my_data[index + 3][10] = _day.oilProduction;
	                screwPumpDailyReportHelper.my_data[index + 3][11] = _day.waterProduction;
	                screwPumpDailyReportHelper.my_data[index + 3][12] = _day.waterCut;
	                screwPumpDailyReportHelper.my_data[index + 3][13] = _day.rpm;
	                screwPumpDailyReportHelper.my_data[index + 3][14] = _day.pumpSettingDepth;
	                screwPumpDailyReportHelper.my_data[index + 3][15] = _day.producingFluidLevel;
	                screwPumpDailyReportHelper.my_data[index + 3][16] = _day.submergence;
	                
	                screwPumpDailyReportHelper.my_data[index + 3][17] = _day.systemEfficiency;
	                screwPumpDailyReportHelper.my_data[index + 3][18] = _day.powerConsumptionPerthm;
	                
	                screwPumpDailyReportHelper.my_data[index + 3][19] = _day.todayKWattH;
	                
	                screwPumpDailyReportHelper.my_data[index + 3][20] = _day.remark;
	            })

	            var _total = data.totalCount;
	            screwPumpDailyReportHelper.last_index = _daily.length + 3;
	        }

	        var init = function () {
	        }

	        init();
	        return screwPumpDailyReportHelper;
	    }
	};