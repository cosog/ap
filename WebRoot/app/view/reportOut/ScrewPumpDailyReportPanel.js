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
        /** 
         * 定义降序的groupingStore 
         */
        var jhStore_A = new Ext.data.JsonStore({
        	pageSize:defaultJhComboxSize,
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
            autoLoad: false,
            listeners: {
                beforeload: function (store, options) {
                    var leftOrg_Id = Ext.getCmp('leftOrg_Id');
                    if (!Ext.isEmpty(leftOrg_Id)) {
                        leftOrg_Id = leftOrg_Id.getValue();
                    }
                    var jh_tobj = Ext.getCmp('ScrewPumpDailyReportPaneljh_Id');
					var jh_val = "";
					if (!Ext.isEmpty(jh_tobj)) {
						jh_val = jh_tobj.getValue();
					}
                    var new_params = {
                        orgId: leftOrg_Id,
                        jh:jh_val,
                        type:'jh',
                        wellType:400
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var simpleCombo_A = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.jh,
                id: 'ScrewPumpDailyReportPaneljh_Id',
                store: jhStore_A,
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
                        simpleCombo_A.getStore().loadPage(1); // 加载井下拉框的store
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
            tbar: [simpleCombo_A, {
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: cosog.string.date,
                labelWidth: 36,
                width: 156,
                format: 'Y-m-d',
                id: 'ScrewPumpDailyReportPanelJssj_Id',
                value:'',
                listeners: {
                	select: function (combo, record, index) {
                        try {
//                        	ReportPumpUnitDayStore.load();
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
                	var jh = obtainParams('ScrewPumpDailyReportPaneljh_Id');
                	var jssj = Ext.getCmp('ScrewPumpDailyReportPanelJssj_Id').rawValue;
                	var url=context + '/reportPumpingUnitDataController/exportScrewPumpDailyReportExcelData?wellType=400&jh='+URLencode(URLencode(jh))+'&jssj='+jssj+'&orgId='+leftOrg_Id;
                	document.location.href = url;
                }
            }, '->', {
                id: 'ScrewPumpDailyReportTotalCount_Id',
                xtype: 'component',
                tpl: cosog.string.totalCount + ': {count}',
                style: 'margin-right:15px'
            }],
            html:'<div class="ScrewPumpDailyReportContainer" style="width:100%;height:100%;"><div class="con" id="ScrewPumpDailyReportDiv_id"></div></div>',
            listeners: {
                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                	CreateScrewPumpDailyReportTable();
                }
            }
        });
        me.callParent(arguments);
    }
});

function CreateScrewPumpDailyReportTable(){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var jh = Ext.getCmp('ScrewPumpDailyReportPaneljh_Id').getValue();
    var jssj = Ext.getCmp('ScrewPumpDailyReportPanelJssj_Id').rawValue;
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reportPumpingUnitDataController/showDiagnosisDailyReportData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(Ext.getCmp("ScrewPumpDailyReportPanelJssj_Id").getValue()==''||Ext.getCmp("ScrewPumpDailyReportPanelJssj_Id").getValue()==null){
            	Ext.getCmp("ScrewPumpDailyReportPanelJssj_Id").setValue(result.jssj);
            	Ext.getCmp("ScrewPumpDailyReportPanelJssj_Id").setRawValue(result.jssj);
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
            jh: jh,
            jssj: jssj,
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
	        
	        screwPumpDailyReportHelper.my_data = [
	    ['螺杆泵井生产报表', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
	    ['序号', '井名', '日期','通信','','', '时率', '','', '工况','', '产量', '','','','','','','','','效率','','日用电量(kW·h)', '备注'],
	    ['', '', '','在线时间(h)','在线区间', '在线时率(%)','运行时间(h)','运行区间', '运行时率(%)','电参工况','优化建议','产液量（t/d）', '产油量（t/d）','产水量（t/d）', '含水率(%)','产量波动(%)','转速(r/min)','泵挂(m)','动液面(m)','沉没度(m)','系统效率(%)','吨液百米耗电量(kW·h/100·t)','',''],
	    ['合计', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
	    ['平均', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '']
	  ];
	        screwPumpDailyReportHelper.updateArray = function () {
	            for (var i = 0; i < screwPumpDailyReportHelper.sum; i++) {
	                screwPumpDailyReportHelper.my_data.splice(i + 3, 0, ['', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '']);
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
					colWidths:[50,90,75, 80,100,70, 80,100,70, 120,120, 80,80,80,80,80,80, 80,80,80,  80,120, 80, 75],
					stretchH: 'all',
	                mergeCells: [
	                    {
	                        "row": 0,
	                        "col": 0,
	                        "rowspan": 1,
	                        "colspan": 24
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
	                        "colspan": 9
	                    },{//效率
	                        "row": 1,
	                        "col": 20,
	                        "rowspan": 1,
	                        "colspan": 2
	                    },{//日用电量
	                        "row": 1,
	                        "col": 22,
	                        "rowspan": 2,
	                        "colspan": 1
	                    },{//备注
	                        "row": 1,
	                        "col": 23,
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
						
						if (visualColIndex === 28&&visualRowIndex>2&&visualRowIndex<screwPumpDailyReportHelper.last_index) {
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
	                        destinationColumn: 11,
	                        type: 'sum',
	                        forceNumeric: true
	                    },
						{
	                        destinationRow: screwPumpDailyReportHelper.last_index,
	                        destinationColumn: 12,
	                        type: 'sum',
	                        forceNumeric: true
						},
						{
	                        destinationRow: screwPumpDailyReportHelper.last_index,
	                        destinationColumn: 13,
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
	                        destinationColumn: 11,
	                        type: 'average',
	                        roundFloat:true,
	                        forceNumeric: true
	                    },
						{
	                        destinationRow: screwPumpDailyReportHelper.last_index+1,
	                        destinationColumn: 12,
	                        type: 'average',
	                        roundFloat:true,
	                        forceNumeric: true
						},
						{
	                        destinationRow: screwPumpDailyReportHelper.last_index+1,
	                        destinationColumn: 13,
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
	                screwPumpDailyReportHelper.my_data[index + 3][1] = _day.jh;
	                screwPumpDailyReportHelper.my_data[index + 3][2] = _day.jssj;
	                
	                screwPumpDailyReportHelper.my_data[index + 3][3] = _day.tzsj;
	                var txqj=_day.txqj;
	                if(txqj.length>12){
	                	txqj=txqj.substring(0, 11)+"...";
	                }
	                var yxqj=_day.yxqj;
	                if(yxqj.length>12){
	                	yxqj=yxqj.substring(0, 11)+"...";
	                }
	                screwPumpDailyReportHelper.my_data[index + 3][4] = txqj;
	                screwPumpDailyReportHelper.my_data[index + 3][5] = _day.txsl;
	                
	                screwPumpDailyReportHelper.my_data[index + 3][6] = _day.rgzsj;
	                screwPumpDailyReportHelper.my_data[index + 3][7] = yxqj;
	                screwPumpDailyReportHelper.my_data[index + 3][8] = _day.scsl;
	                
	                screwPumpDailyReportHelper.my_data[index + 3][9] = _day.egkmc;
	                screwPumpDailyReportHelper.my_data[index + 3][10] = _day.yhjy;
	                
	                screwPumpDailyReportHelper.my_data[index + 3][11] = _day.jsdjrcyl;
					screwPumpDailyReportHelper.my_data[index + 3][12] = _day.jsdjrcyl1;
	                screwPumpDailyReportHelper.my_data[index + 3][13] = _day.jsdjrcsl;
	                screwPumpDailyReportHelper.my_data[index + 3][14] = _day.hsld;
	                screwPumpDailyReportHelper.my_data[index + 3][15] = _day.jsdjrcylbd;
	                screwPumpDailyReportHelper.my_data[index + 3][16] = _day.rpm;
	                
	                screwPumpDailyReportHelper.my_data[index + 3][17] = _day.bg;
	                screwPumpDailyReportHelper.my_data[index + 3][18] = _day.dym;
	                screwPumpDailyReportHelper.my_data[index + 3][19] = _day.cmd;
	                
	                screwPumpDailyReportHelper.my_data[index + 3][20] = _day.xtxl;
	                screwPumpDailyReportHelper.my_data[index + 3][21] = _day.dybmhdl;
	                
	                screwPumpDailyReportHelper.my_data[index + 3][22] = _day.rydl;
	                
	                screwPumpDailyReportHelper.my_data[index + 3][23] = _day.bz;
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