var rpcDailyReportHelper=null
Ext.define("AP.view.reportOut.RPCDailyReportPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.RPCDailyReportPanel',
    layout: 'fit',
    id: 'RPCDailyReportPanel_view',
    border: false,
    initComponent: function () {
        var me = this;
//        var RPCDailyReportWellListStore = Ext.create("AP.store.reportOut.RPCDailyReportWellListStore");
        /** 
         * 定义降序的groupingStore 
         */
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
                    var wellName = Ext.getCmp('RPCDailyReportPanelWellListCombo_Id').getValue();
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
                id: 'RPCDailyReportPanelWellListCombo_Id',
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
                        onEnterKeyDownFN(field, e, 'RPCDailyReportPanel_Id');
                    },
                    select: function (combo, record, index) {
                    	CreateRPCDailyReportTable();
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
                	var gridPanel = Ext.getCmp("RPCDailyReportGridPanel_Id");
        			if (isNotVal(gridPanel)) {
        				gridPanel.getStore().load();
        			}else{
        				Ext.create('AP.store.reportOut.RPCDailyReportWellListStore');
        			}
                }
    		},'-',wellListCombo, {
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: '日期',
                labelWidth: 36,
                width: 126,
                format: 'Y-m-d',
                id: 'RPCDailyReportStartDate_Id',
                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                        try {
                        	CreateRPCDailyReportTable();
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
                id: 'RPCDailyReportEndDate_Id',
                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                        try {
                        	CreateRPCDailyReportTable();
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
                	CreateRPCDailyReportTable();
                }
    		},'-', {
                xtype: 'button',
                text: cosog.string.exportExcel,
                iconCls: 'export',
                handler: function (v, o) {
                	var leftOrg_Id = obtainParams('leftOrg_Id');
                	var wellName = Ext.getCmp('RPCDailyReportPanelWellListCombo_Id').getValue();
                	var startDate = Ext.getCmp('RPCDailyReportStartDate_Id').rawValue;
                	var endDate = Ext.getCmp('RPCDailyReportEndDate_Id').rawValue;
                	var url=context + '/reportDataMamagerController/exportRPCDailyReportData?wellType=0&wellName='+URLencode(URLencode(wellName))+'&startDate='+startDate+'&endDate='+endDate+'&orgId='+leftOrg_Id;
                	document.location.href = url;
                }
            }, '->', {
                id: 'RPCDailyReportTotalCount_Id',
                xtype: 'component',
                tpl: cosog.string.totalCount + ': {count}',
                style: 'margin-right:15px'
            },{
            	id: 'RPCDailyReportDeviceListSelectRow_Id',
            	xtype: 'textfield',
                value: -1,
                hidden: true
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
                layout: "fit",
            	id:'RPCDailyReportPanel_id',
//                border: false,
                html:'<div class="RPCDailyReportContainer" style="width:100%;height:100%;"><div class="con" id="RPCDailyReportDiv_id"></div></div>',
                listeners: {
                	resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                		if(rpcDailyReportHelper!=null && rpcDailyReportHelper.hot!=undefined){
//                			rpcDailyReportHelper.hot.refreshDimensions();
                			var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		rpcDailyReportHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}
                	}
                }
            }]

        });
        me.callParent(arguments);
    }
});

function CreateRPCDailyReportTable(){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var wellName = Ext.getCmp('RPCDailyReportPanelWellListCombo_Id').getValue();
    var startDate = Ext.getCmp('RPCDailyReportStartDate_Id').rawValue;
    var endDate = Ext.getCmp('RPCDailyReportEndDate_Id').rawValue;
    Ext.getCmp("RPCDailyReportPanel_id").el.mask(cosog.string.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reportDataMamagerController/getDailyReportData',
		success:function(response) {
			Ext.getCmp("RPCDailyReportPanel_id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(Ext.getCmp("RPCDailyReportStartDate_Id").getValue()==''||Ext.getCmp("RPCDailyReportStartDate_Id").getValue()==null){
            	Ext.getCmp("RPCDailyReportStartDate_Id").setValue(result.startDate);
            	Ext.getCmp("RPCDailyReportStartDate_Id").setRawValue(result.startDate);
            }
			
			if(Ext.getCmp("RPCDailyReportEndDate_Id").getValue()==''||Ext.getCmp("RPCDailyReportEndDate_Id").getValue()==null){
            	Ext.getCmp("RPCDailyReportEndDate_Id").setValue(result.endDate);
            	Ext.getCmp("RPCDailyReportEndDate_Id").setRawValue(result.endDate);
            }
			
			rpcDailyReportHelper = RPCDailyReportHelper.createNew("RPCDailyReportDiv_id","RPCDailyReportContainer");
			rpcDailyReportHelper.getData(result);
			rpcDailyReportHelper.createTable();
			Ext.getCmp("RPCDailyReportTotalCount_Id").update({count: result.totalCount});
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId: orgId,
			wellName: wellName,
			startDate: startDate,
			endDate: endDate,
            deviceType:0
        }
	});
};


var RPCDailyReportHelper = {
	    createNew: function (divid, containerid) {
	        var rpcDailyReportHelper = {};
	        rpcDailyReportHelper.get_data = {};
	        rpcDailyReportHelper.hot = '';
	        rpcDailyReportHelper.container = document.getElementById(divid);
	        rpcDailyReportHelper.last_index = 0;
	        rpcDailyReportHelper.calculation_type_computer = [];
	        rpcDailyReportHelper.calculation_type_not_computer = [];
	        rpcDailyReportHelper.editable = 0;
	        rpcDailyReportHelper.sum = 0;
	        rpcDailyReportHelper.editRecords = [];
	        var productionUnitStr='t/d';
	        if(productionUnit!=0){
	        	productionUnitStr='m^3/d';
	        }
	        rpcDailyReportHelper.my_data = [
	    ['抽油机井生产报表', '', '', '', '', '', '', '', '', '',  '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
	    ['序号', '井名', '日期','通信','','', '时率', '','', '工况','', '产量', '','','','','平衡','','','效率', '', '','','日用电量(kW·h)', '备注'],
	    ['', '', '','在线时间(h)','在线区间', '在线时率(小数)','运行时间(h)','运行区间', '运行时率(小数)','功图工况','优化建议','产液量（'+productionUnitStr+'）', '产油量（'+productionUnitStr+'）','产水量（'+productionUnitStr+'）', '含水率(%)','充满系数(小数)','功率平衡度(%)','电流平衡度(%)','移动距离(cm)', '系统效率(%)', '地面效率(%)', '井下效率(%)','吨液百米耗电量(kW·h/100·t)','','']
//	    ['合计', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
//	    ['平均', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '']
	  ];
	        rpcDailyReportHelper.updateArray = function () {
	            for (var i = 0; i < rpcDailyReportHelper.sum; i++) {
	                rpcDailyReportHelper.my_data.splice(i + 3, 0, ['', '', '', '', '', '', '', '', '', '', '', '', '', '']);
	            }
	        }
	        rpcDailyReportHelper.clearArray = function () {
	            rpcDailyReportHelper.hot.loadData(rpcDailyReportHelper.table_header);

	        }

	        rpcDailyReportHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	            if (row <= 2&&row>=1) {
	                td.style.fontWeight = 'bold';
					td.style.fontSize = '13px';
					td.style.color = 'rgb(0, 0, 51)';
					td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	            }
	        }
			
			rpcDailyReportHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (row < 1) {
	                td.style.fontWeight = 'bold';
			        td.style.fontSize = '25px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '50px';   
			    }      
	        }
			
			rpcDailyReportHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
		         if(row < 3){
	                 td.style.fontWeight = 'bold';
			         td.style.fontSize = '5px';
			         td.style.fontFamily = 'SimHei';
	            }      
	        }
			

	        rpcDailyReportHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        rpcDailyReportHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(216, 228, 188)';
	        }
	        
	        rpcDailyReportHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        rpcDailyReportHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        rpcDailyReportHelper.createTable = function () {
	            rpcDailyReportHelper.container.innerHTML = "";
	            rpcDailyReportHelper.hot = new Handsontable(rpcDailyReportHelper.container, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: rpcDailyReportHelper.my_data,
	                fixedRowsTop:3, //固定顶部多少行不能垂直滚动
	                fixedRowsBottom: 0,//固定底部多少行不能垂直滚动
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: [50],
					colWidths:[50,90,80, 80,100,70, 80,100,70, 140,120, 80,80,80,80,80, 80,80,80,  80,80,80,120, 80, 75],
					stretchH: 'all',
					columnSorting: true, //允许排序
	                allowInsertRow:false,
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                mergeCells: [
	                    {
	                        "row": 0,
	                        "col": 0,
	                        "rowspan": 1,
	                        "colspan": 25
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
	                        "colspan": 3
	                    },{//效率
	                        "row": 1,
	                        "col": 19,
	                        "rowspan": 1,
	                        "colspan": 4
	                    },{//日用电量
	                        "row": 1,
	                        "col": 23,
	                        "rowspan": 2,
	                        "colspan": 1
	                    },{//备注
	                        "row": 1,
	                        "col": 24,
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
	                        cellProperties.renderer = rpcDailyReportHelper.addBoldBg;
	                    }
	                    
	                    // 合计
//	                    if (visualRowIndex ==rpcDailyReportHelper.last_index) {
//	                        cellProperties.renderer = rpcDailyReportHelper.addBoldBg;
//	                    }
						
						if (visualRowIndex < 1 ) {
	                       cellProperties.renderer = rpcDailyReportHelper.addSizeBg;
	                    }
						
						if (visualColIndex === 26&&visualRowIndex>2&&visualRowIndex<rpcDailyReportHelper.last_index) {
							cellProperties.readOnly = false;
		                }
						
						
	                    return cellProperties;
	                },
	                afterChange:function(changes, source){}
	            });
	        }
	        rpcDailyReportHelper.getData = function (data) {
	            rpcDailyReportHelper.get_data = data;
	            rpcDailyReportHelper.editable = +data.Editable;
	            var _daily = data.totalRoot;
	            rpcDailyReportHelper.sum = _daily.length;
	            rpcDailyReportHelper.updateArray();
	            _daily.forEach(function (_day, index) {

	            	if(_day.id=="合计" || _day.id=="平均"){
	            		rpcDailyReportHelper.my_data[index + 3][0] = _day.id;
	            	}else{
	            		rpcDailyReportHelper.my_data[index + 3][0] = index+1;
	            	}
	                
	                rpcDailyReportHelper.my_data[index + 3][1] = _day.wellName;
	                rpcDailyReportHelper.my_data[index + 3][2] = _day.calculateDate;
	                
	                rpcDailyReportHelper.my_data[index + 3][3] = _day.commTime;
	                var commRange=_day.commRange;
	                if(commRange.length>12){
	                	commRange=commRange.substring(0, 11)+"...";
	                }
	                var runRange=_day.runRange;
	                if(runRange.length>12){
	                	runRange=runRange.substring(0, 11)+"...";
	                }
	                rpcDailyReportHelper.my_data[index + 3][4] = commRange;
	                rpcDailyReportHelper.my_data[index + 3][5] = _day.commTimeEfficiency;
	                
	                rpcDailyReportHelper.my_data[index + 3][6] = _day.runTime;
	                rpcDailyReportHelper.my_data[index + 3][7] = runRange;
	                rpcDailyReportHelper.my_data[index + 3][8] = _day.runTimeEfficiency;
	                
	                rpcDailyReportHelper.my_data[index + 3][9] = _day.resultName;
	                rpcDailyReportHelper.my_data[index + 3][10] = _day.optimizationSuggestion;
	                
	                rpcDailyReportHelper.my_data[index + 3][11] = _day.liquidProduction;
					rpcDailyReportHelper.my_data[index + 3][12] = _day.oilProduction;
	                rpcDailyReportHelper.my_data[index + 3][13] = _day.waterProduction;
	                rpcDailyReportHelper.my_data[index + 3][14] = _day.waterCut;
	                rpcDailyReportHelper.my_data[index + 3][15] = _day.fullnesscoEfficient;
	                
	                rpcDailyReportHelper.my_data[index + 3][16] = _day.wattDegreeBalance;
	                rpcDailyReportHelper.my_data[index + 3][17] = _day.iDegreeBalance;
	                rpcDailyReportHelper.my_data[index + 3][18] = _day.deltaRadius;
	                
	                rpcDailyReportHelper.my_data[index + 3][19] = _day.systemEfficiency;
	                rpcDailyReportHelper.my_data[index + 3][20] = _day.surfaceSystemEfficiency;
	                rpcDailyReportHelper.my_data[index + 3][21] = _day.welldownSystemEfficiency;
	                rpcDailyReportHelper.my_data[index + 3][22] = _day.energyPer100mLift;
	                
	                rpcDailyReportHelper.my_data[index + 3][23] = _day.todayKWattH;
	                
	                rpcDailyReportHelper.my_data[index + 3][24] = _day.remark;
	            })

	            var _total = data.totalCount;
	            rpcDailyReportHelper.last_index = _daily.length + 3;
	        }

	        var init = function () {
	        }

	        init();
	        return rpcDailyReportHelper;
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