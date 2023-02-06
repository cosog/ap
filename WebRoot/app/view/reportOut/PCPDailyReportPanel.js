var pcpDailyReportHelper=null
Ext.define("AP.view.reportOut.PCPDailyReportPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.PCPDailyReportPanel',
    layout: 'fit',
    id: 'PCPDailyReportPanel_view',
    border: false,
    initComponent: function () {
        var me = this;
//        var PCPDailyReportWellListStore = Ext.create("AP.store.reportOut.PCPDailyReportWellListStore");
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
                    var wellName = Ext.getCmp('PCPDailyReportPanelWellListCombo_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceType: 1,
                        wellName: wellName
                    };
                    Ext.apply(store.proxy.extraParams,new_params);
                }
            }
        });
        var wellListCombo = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.wellName,
                id: 'PCPDailyReportPanelWellListCombo_Id',
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
                        onEnterKeyDownFN(field, e, 'PCPDailyReportPanel_Id');
                    },
                    select: function (combo, record, index) {
                    	CreatePCPDailyReportTable();
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
                	var gridPanel = Ext.getCmp("PCPDailyReportGridPanel_Id");
        			if (isNotVal(gridPanel)) {
        				gridPanel.getStore().load();
        			}else{
        				Ext.create('AP.store.reportOut.PCPDailyReportWellListStore');
        			}
                }
    		},'-',wellListCombo, {
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: '日期',
                labelWidth: 36,
                width: 126,
                format: 'Y-m-d',
                id: 'PCPDailyReportStartDate_Id',
                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                        try {
                        	CreatePCPDailyReportTable();
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
                id: 'PCPDailyReportEndDate_Id',
                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                        try {
                        	CreatePCPDailyReportTable();
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
                	CreatePCPDailyReportTable();
                }
    		},'-',{
                xtype: 'button',
                text: cosog.string.exportExcel,
                iconCls: 'export',
                handler: function (v, o) {
                	var leftOrg_Id = obtainParams('leftOrg_Id');
                	var wellName = Ext.getCmp('PCPDailyReportPanelWellListCombo_Id').getValue();
                	var startDate = Ext.getCmp('PCPDailyReportStartDate_Id').rawValue;
                	var endDate = Ext.getCmp('PCPDailyReportEndDate_Id').rawValue;
                	var url=context + '/reportDataMamagerController/exportPCPDailyReportData?wellType=1&wellName='+URLencode(URLencode(wellName))+'&startDate='+startDate+'&endDate='+endDate+'&orgId='+leftOrg_Id;
                	document.location.href = url;
                }
            }, '->', {
                id: 'PCPDailyReportTotalCount_Id',
                xtype: 'component',
                tpl: cosog.string.totalCount + ': {count}',
                style: 'margin-right:15px'
            },{
            	id: 'PCPDailyReportDeviceListSelectRow_Id',
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
            	id: 'PCPDailyReportWellListPanel_Id',
            	collapsible: true, // 是否可折叠
                collapsed:false,//是否折叠
                split: true, // 竖折叠条
            	layout: "fit"
            },{
            	region: 'center',
            	title:'报表数据',
            	id:'PCPDailyReportPanel_id',
                border: false,
                layout: "fit",
                html:'<div class="PCPDailyReportContainer" style="width:100%;height:100%;"><div class="con" id="PCPDailyReportDiv_id"></div></div>',
                listeners: {
                	resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                		if(pcpDailyReportHelper!=null && pcpDailyReportHelper.hot!=undefined){
//                			pcpDailyReportHelper.hot.refreshDimensions();
                			var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		pcpDailyReportHelper.hot.updateSettings({
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

function CreatePCPDailyReportTable(){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var wellName = Ext.getCmp('PCPDailyReportPanelWellListCombo_Id').getValue();
    var startDate = Ext.getCmp('PCPDailyReportStartDate_Id').rawValue;
    var endDate = Ext.getCmp('PCPDailyReportEndDate_Id').rawValue;
    Ext.getCmp("PCPDailyReportPanel_id").el.mask(cosog.string.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reportDataMamagerController/getDailyReportData',
		success:function(response) {
			Ext.getCmp("PCPDailyReportPanel_id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(Ext.getCmp("PCPDailyReportStartDate_Id").getValue()==''||Ext.getCmp("PCPDailyReportStartDate_Id").getValue()==null){
            	Ext.getCmp("PCPDailyReportStartDate_Id").setValue(result.startDate);
            	Ext.getCmp("PCPDailyReportStartDate_Id").setRawValue(result.startDate);
            }
			
			if(Ext.getCmp("PCPDailyReportEndDate_Id").getValue()==''||Ext.getCmp("PCPDailyReportEndDate_Id").getValue()==null){
            	Ext.getCmp("PCPDailyReportEndDate_Id").setValue(result.endDate);
            	Ext.getCmp("PCPDailyReportEndDate_Id").setRawValue(result.endDate);
            }
			
			pcpDailyReportHelper = PCPDailyReportHelper.createNew("PCPDailyReportDiv_id","PCPDailyReportContainer");
			pcpDailyReportHelper.getData(result);
			pcpDailyReportHelper.createTable();
			Ext.getCmp("PCPDailyReportTotalCount_Id").update({count: result.totalCount});
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId: orgId,
			wellName: wellName,
			startDate: startDate,
			endDate: endDate,
            deviceType:1
        }
	});
};


var PCPDailyReportHelper = {
	    createNew: function (divid, containerid) {
	        var pcpDailyReportHelper = {};
	        pcpDailyReportHelper.get_data = {};
	        pcpDailyReportHelper.hot = '';
	        pcpDailyReportHelper.container = document.getElementById(divid);
	        pcpDailyReportHelper.last_index = 0;
	        pcpDailyReportHelper.calculation_type_computer = [];
	        pcpDailyReportHelper.calculation_type_not_computer = [];
	        pcpDailyReportHelper.editable = 0;
	        pcpDailyReportHelper.sum = 0;
	        pcpDailyReportHelper.editRecords = [];
	        var productionUnitStr='t/d';
	        if(productionUnit!=0){
	        	productionUnitStr='m^3/d';
	        }
	        pcpDailyReportHelper.my_data = [
	    ['螺杆泵井生产报表', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
	    ['序号', '井名', '日期','通信','','', '时率', '','', '产量', '','','','','效率','','日用电量(kW·h)', '备注'],
	    ['', '', '','在线时间(h)','在线区间', '在线时率(小数)','运行时间(h)','运行区间', '运行时率(小数)','产液量（'+productionUnitStr+'）', '产油量（'+productionUnitStr+'）','产水量（'+productionUnitStr+'）', '含水率(%)','转速(r/min)','系统效率(%)','吨液百米耗电量(kW·h/100·t)','','']
	  ];
	        pcpDailyReportHelper.updateArray = function () {
	            for (var i = 0; i < pcpDailyReportHelper.sum; i++) {
	                pcpDailyReportHelper.my_data.splice(i + 3, 0, ['', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '']);
	            }
	        }
	        pcpDailyReportHelper.clearArray = function () {
	            pcpDailyReportHelper.hot.loadData(pcpDailyReportHelper.table_header);

	        }

	        pcpDailyReportHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	            if (row <= 2&&row>=1) {
	                td.style.fontWeight = 'bold';
					td.style.fontSize = '13px';
					td.style.color = 'rgb(0, 0, 51)';
					td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	            }
	        }
			
			pcpDailyReportHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (row < 1) {
	                td.style.fontWeight = 'bold';
			        td.style.fontSize = '25px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '50px';   
			    }      
	        }
			
			pcpDailyReportHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
		         if(row < 3){
	                 td.style.fontWeight = 'bold';
			         td.style.fontSize = '5px';
			         td.style.fontFamily = 'SimHei';
	            }      
	        }
			

	        pcpDailyReportHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        pcpDailyReportHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(216, 228, 188)';
	        }
	        
	        pcpDailyReportHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        pcpDailyReportHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        pcpDailyReportHelper.createTable = function () {
	            pcpDailyReportHelper.container.innerHTML = "";
	            pcpDailyReportHelper.hot = new Handsontable(pcpDailyReportHelper.container, {
	                data: pcpDailyReportHelper.my_data,
	                fixedRowsTop:3, //固定顶部多少行不能垂直滚动
	                fixedRowsBottom: 0,//固定底部多少行不能垂直滚动
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: [50],
					colWidths:[50,90,80,80,100,70,80,100,70,80,80,80,80,80,80,120,80,75],
					stretchH: 'all',
	                mergeCells: [
	                    {
	                        "row": 0,
	                        "col": 0,
	                        "rowspan": 1,
	                        "colspan": 18
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
	                        "colspan": 5
	                    },{//效率
	                        "row": 1,
	                        "col": 14,
	                        "rowspan": 1,
	                        "colspan": 2
	                    },{//日用电量
	                        "row": 1,
	                        "col": 16,
	                        "rowspan": 2,
	                        "colspan": 1
	                    },{//备注
	                        "row": 1,
	                        "col": 17,
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
	                        cellProperties.renderer = pcpDailyReportHelper.addBoldBg;
	                    }
						
						if (visualRowIndex < 1 ) {
	                       cellProperties.renderer = pcpDailyReportHelper.addSizeBg;
	                    }
						
						if (visualColIndex === 17&&visualRowIndex>2&&visualRowIndex<pcpDailyReportHelper.last_index) {
							cellProperties.readOnly = false;
		                }
						
						
	                    return cellProperties;
	                },
	                afterChange:function(changes, source){}
	            });
	        }



	        pcpDailyReportHelper.getData = function (data) {
	            pcpDailyReportHelper.get_data = data;
	            pcpDailyReportHelper.editable = +data.Editable;
	            var _daily = data.totalRoot;
	            pcpDailyReportHelper.sum = _daily.length;
	            pcpDailyReportHelper.updateArray();
	            _daily.forEach(function (_day, index) {
	            	if(_day.id=="合计" || _day.id=="平均"){
	            		pcpDailyReportHelper.my_data[index + 3][0] = _day.id;
	            	}else{
	            		pcpDailyReportHelper.my_data[index + 3][0] = index+1;
	            	}
//	                pcpDailyReportHelper.my_data[index + 3][0] = index+1;
	                pcpDailyReportHelper.my_data[index + 3][1] = _day.wellName;
	                pcpDailyReportHelper.my_data[index + 3][2] = _day.calculateDate;
	                
	                pcpDailyReportHelper.my_data[index + 3][3] = _day.commTime;
	                var commRange=_day.commRange;
	                if(commRange.length>12){
	                	commRange=commRange.substring(0, 11)+"...";
	                }
	                var runRange=_day.runRange;
	                if(runRange.length>12){
	                	runRange=runRange.substring(0, 11)+"...";
	                }
	                pcpDailyReportHelper.my_data[index + 3][4] = commRange;
	                pcpDailyReportHelper.my_data[index + 3][5] = _day.commTimeEfficiency;
	                
	                pcpDailyReportHelper.my_data[index + 3][6] = _day.runTime;
	                pcpDailyReportHelper.my_data[index + 3][7] = runRange;
	                pcpDailyReportHelper.my_data[index + 3][8] = _day.runTimeEfficiency;
	                
	                
	                pcpDailyReportHelper.my_data[index + 3][9] = _day.liquidProduction;
					pcpDailyReportHelper.my_data[index + 3][10] = _day.oilProduction;
	                pcpDailyReportHelper.my_data[index + 3][11] = _day.waterProduction;
	                pcpDailyReportHelper.my_data[index + 3][12] = _day.waterCut;
	                pcpDailyReportHelper.my_data[index + 3][13] = _day.rpm;
	                
	                pcpDailyReportHelper.my_data[index + 3][14] = _day.systemEfficiency;
	                pcpDailyReportHelper.my_data[index + 3][15] = _day.energyPer100mLift;
	                
	                pcpDailyReportHelper.my_data[index + 3][16] = _day.todayKWattH;
	                
	                pcpDailyReportHelper.my_data[index + 3][17] = _day.remark;
	            })

	            var _total = data.totalCount;
	            pcpDailyReportHelper.last_index = _daily.length + 3;
	        }

	        var init = function () {
	        }

	        init();
	        return pcpDailyReportHelper;
	    }
	};

function createPCPDailyReportWellListDataColumn(columnInfo) {
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