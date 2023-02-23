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
                        onEnterKeyDownFN(field, e, 'RPCDailyReportPanel_Id');
                    },
                    select: function (combo, record, index) {
                    	CreateRPCDailyReportTable();
                    	CreateRPCDailyReportCurve();
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
                width: 136,
                format: 'Y-m-d',
                id: 'RPCDailyReportStartDate_Id',
//                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                        try {
                        	CreateRPCDailyReportTable();
                        	CreateRPCDailyReportCurve();
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
                id: 'RPCDailyReportEndDate_Id',
                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                        try {
                        	CreateRPCDailyReportTable();
                        	CreateRPCDailyReportCurve();
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
                	CreateRPCDailyReportCurve();
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
                	
                	var wellName='';
                    var wellId=0;
                    var selectRow= Ext.getCmp("RPCDailyReportDeviceListSelectRow_Id").getValue();
                    if(selectRow>=0){
                    	wellName=Ext.getCmp("RPCDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
                    	wellId=Ext.getCmp("RPCDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
                    }
                	
                	var url=context + '/reportDataMamagerController/exportSingleWellDailyReportData?deviceType=0&wellName='+URLencode(URLencode(wellName))+'&wellId='+wellId+'&startDate='+startDate+'&endDate='+endDate+'&orgId='+leftOrg_Id;
                	document.location.href = url;
                }
            }, '->',{
                xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                disabled: loginUserRoleReportEdit!=1,
                handler: function (v, o) {
                	rpcDailyReportHelper.saveData();
                }
            },'-', {
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
            	layout:'border',
            	border: false,
            	items:[{
            		region:'north',
            		height:'50%',
            		title:'报表曲线',
            		collapsible: true, // 是否可折叠
                    collapsed:false,//是否折叠
                    split: true, // 竖折叠条
                    id:'RPCDailyReportCurvePanel_id',
                    html: '<div id="RPCDailyReportCurveDiv_Id" style="width:100%;height:100%;"></div>',
                    listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            if ($("#RPCDailyReportCurveDiv_Id").highcharts() != undefined) {
                            	highchartsResize("RPCDailyReportCurveDiv_Id");
                            }
                        }
                    }
            	},{
            		region: 'center',
            		title:'报表数据',
                    layout: "fit",
                	id:'RPCDailyReportPanel_id',
//                    border: false,
                    html:'<div class="RPCDailyReportContainer" style="width:100%;height:100%;"><div class="con" id="RPCDailyReportDiv_id"></div></div>',
                    listeners: {
                    	resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    		if(rpcDailyReportHelper!=null && rpcDailyReportHelper.hot!=undefined){
//                    			rpcDailyReportHelper.hot.refreshDimensions();
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
            }]

        });
        me.callParent(arguments);
    }
});

function CreateRPCDailyReportTable(){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
//    var wellName = Ext.getCmp('RPCDailyReportPanelWellListCombo_Id').getValue();
    var startDate = Ext.getCmp('RPCDailyReportStartDate_Id').rawValue;
    var endDate = Ext.getCmp('RPCDailyReportEndDate_Id').rawValue;
    
    var wellName='';
    var wellId=0;
    var selectRow= Ext.getCmp("RPCDailyReportDeviceListSelectRow_Id").getValue();
    if(selectRow>=0){
    	wellName=Ext.getCmp("RPCDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
    	wellId=Ext.getCmp("RPCDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
    }
    
    Ext.getCmp("RPCDailyReportPanel_id").el.mask(cosog.string.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reportDataMamagerController/getSingleWellDailyReportData',
		success:function(response) {
			Ext.getCmp("RPCDailyReportPanel_id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			
			var startDate=Ext.getCmp('RPCDailyReportStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(result.startDate);
            }
            var endDate=Ext.getCmp('RPCDailyReportEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(result.endDate);
            }
            
			if(rpcDailyReportHelper!=null){
				if(rpcDailyReportHelper.hot!=undefined){
					rpcDailyReportHelper.hot.destroy();
				}
				rpcDailyReportHelper=null;
			}
			if(result.success){
				if(rpcDailyReportHelper==null || rpcDailyReportHelper.hot==undefined){
					rpcDailyReportHelper = RPCDailyReportHelper.createNew("RPCDailyReportDiv_id","RPCDailyReportContainer",result.template,result.data,result.columns);
					rpcDailyReportHelper.createTable();
				}
			}else{
				$("#RPCDailyReportDiv_id").html('');
			}
			
			Ext.getCmp("RPCDailyReportTotalCount_Id").update({count: result.data.length});
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId: orgId,
			wellId:wellId,
			wellName: wellName,
			startDate: startDate,
			endDate: endDate,
            deviceType:0
        }
	});
};


var RPCDailyReportHelper = {
	    createNew: function (divid, containerid,templateData,contentData,columns) {
	        var rpcDailyReportHelper = {};
	        rpcDailyReportHelper.templateData=templateData;
	        rpcDailyReportHelper.contentData=contentData;
	        rpcDailyReportHelper.columns=columns;
	        rpcDailyReportHelper.get_data = {};
	        rpcDailyReportHelper.data=[];
	        rpcDailyReportHelper.sourceData=[];
	        rpcDailyReportHelper.hot = '';
	        rpcDailyReportHelper.container = document.getElementById(divid);
	        rpcDailyReportHelper.columnCount=0;
	        rpcDailyReportHelper.editData={};
	        rpcDailyReportHelper.contentUpdateList = [];
	        
	        rpcDailyReportHelper.initData=function(){
	        	rpcDailyReportHelper.data=[];
	        	for(var i=0;i<rpcDailyReportHelper.templateData.header.length;i++){
	        		rpcDailyReportHelper.templateData.header[i].title.push('');
	        		rpcDailyReportHelper.columnCount=rpcDailyReportHelper.templateData.header[i].title.length;
	        		
	        		var valueArr=[];
	        		var sourceValueArr=[];
	        		for(var j=0;j<rpcDailyReportHelper.templateData.header[i].title.length;j++){
	        			valueArr.push(rpcDailyReportHelper.templateData.header[i].title[j]);
	        			sourceValueArr.push(rpcDailyReportHelper.templateData.header[i].title[j]);
	        		}
	        		
	        		rpcDailyReportHelper.data.push(valueArr);
	        		rpcDailyReportHelper.sourceData.push(sourceValueArr);
		        }
	        	for(var i=0;i<rpcDailyReportHelper.contentData.length;i++){
	        		var valueArr=[];
	        		var sourceValueArr=[];
	        		for(var j=0;j<rpcDailyReportHelper.contentData[i].length;j++){
	        			valueArr.push(rpcDailyReportHelper.contentData[i][j]);
	        			sourceValueArr.push(rpcDailyReportHelper.contentData[i][j]);
	        		}
	        		
	        		rpcDailyReportHelper.data.push(valueArr);
		        	rpcDailyReportHelper.sourceData.push(sourceValueArr);
		        }
	        	for(var i=rpcDailyReportHelper.templateData.header.length;i<rpcDailyReportHelper.data.length;i++){
	        		for(var j=0;j<rpcDailyReportHelper.data[i].length;j++){
	        			
	        			var editable=false
	        			
	        			for(var k=0;k<rpcDailyReportHelper.templateData.editable.length;k++){
	        				if( i>=rpcDailyReportHelper.templateData.editable[k].startRow 
	                				&& i<=rpcDailyReportHelper.templateData.editable[k].endRow
	                				&& j>=rpcDailyReportHelper.templateData.editable[k].startColumn 
	                				&& j<=rpcDailyReportHelper.templateData.editable[k].endColumn
	                		){
	        					editable=true;
	        					break;
	                		}
	        			}
	        			
	        			var value=rpcDailyReportHelper.data[i][j];
		                if((!editable)&&value.length>12){
		                	value=value.substring(0, 11)+"...";
		                	rpcDailyReportHelper.data[i][j]=value;
		                }
	        		}
	        	}
	        }
	        
	        rpcDailyReportHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(rpcDailyReportHelper!=null && rpcDailyReportHelper.hot!=null){
	        		for(var i=0;i<rpcDailyReportHelper.templateData.header.length;i++){
		        		if(row==i){
		        			if(isNotVal(rpcDailyReportHelper.templateData.header[i].tdStyle)){
		        				if(isNotVal(rpcDailyReportHelper.templateData.header[i].tdStyle.fontWeight)){
		        					td.style.fontWeight = rpcDailyReportHelper.templateData.header[i].tdStyle.fontWeight;
		        				}
		        				if(isNotVal(rpcDailyReportHelper.templateData.header[i].tdStyle.fontSize)){
		        					td.style.fontSize = rpcDailyReportHelper.templateData.header[i].tdStyle.fontSize;
		        				}
		        				if(isNotVal(rpcDailyReportHelper.templateData.header[i].tdStyle.fontFamily)){
		        					td.style.fontFamily = rpcDailyReportHelper.templateData.header[i].tdStyle.fontFamily;
		        				}
		        				if(isNotVal(rpcDailyReportHelper.templateData.header[i].tdStyle.height)){
		        					td.style.height = rpcDailyReportHelper.templateData.header[i].tdStyle.height;
		        				}
		        				if(isNotVal(rpcDailyReportHelper.templateData.header[i].tdStyle.color)){
		        					td.style.color = rpcDailyReportHelper.templateData.header[i].tdStyle.color;
		        				}
		        				if(isNotVal(rpcDailyReportHelper.templateData.header[i].tdStyle.backgroundColor)){
		        					td.style.backgroundColor = rpcDailyReportHelper.templateData.header[i].tdStyle.backgroundColor;
		        				}
		        				if(isNotVal(rpcDailyReportHelper.templateData.header[i].tdStyle.textAlign)){
		        					td.style.textAlign = rpcDailyReportHelper.templateData.header[i].tdStyle.textAlign;
		        				}
		        			}
		        			break;
		        		}
		        	}
	        	}
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
	            	data: rpcDailyReportHelper.data,
	            	hiddenColumns: {
	                    columns: [rpcDailyReportHelper.columnCount-1],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
//	            	columns:rpcDailyReportHelper.columns,
	            	fixedRowsTop:rpcDailyReportHelper.templateData.fixedRowsTop, 
	                fixedRowsBottom: rpcDailyReportHelper.templateData.fixedRowsBottom,
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: rpcDailyReportHelper.templateData.rowHeights,
					colWidths: rpcDailyReportHelper.templateData.columnWidths,
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
	                mergeCells: rpcDailyReportHelper.templateData.mergeCells,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.renderer = rpcDailyReportHelper.addStyle;
	                    cellProperties.readOnly = true;
	                    if(rpcDailyReportHelper.templateData.editable!=null && rpcDailyReportHelper.templateData.editable.length>0){
	                    	for(var i=0;i<rpcDailyReportHelper.templateData.editable.length;i++){
	                    		if( row>=rpcDailyReportHelper.templateData.editable[i].startRow 
	                    				&& row<=rpcDailyReportHelper.templateData.editable[i].endRow
	                    				&& col>=rpcDailyReportHelper.templateData.editable[i].startColumn 
	                    				&& col<=rpcDailyReportHelper.templateData.editable[i].endColumn
	                    		){
	                    			cellProperties.readOnly = false;
	                    		}
	                    	}
	                    }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(rpcDailyReportHelper!=null&&rpcDailyReportHelper.hot!=''&&rpcDailyReportHelper.hot!=undefined && rpcDailyReportHelper.hot.getDataAtCell!=undefined){
	                		var rawVvalue=rpcDailyReportHelper.sourceData[coords.row][coords.col];
	                		if(coords.row>=rpcDailyReportHelper.templateData.header.length){
//	                			TD.outerHTML='<span data-qtip="'+value+'" data-dismissDelay=10000>'+TD.innerText+'</span>';
//	                			alert(value);
	                			if(isNotVal(rawVvalue)){
	                				if(!isNotVal(TD.tip)){
	                					TD.tip = Ext.create('Ext.tip.ToolTip', {
			                			    target: event.target,
			                			    html: rawVvalue,
			                			    listeners: {
			                			    	hide: function (thisTip, eOpts) {
//			                			    		thisTip.destroy();
			                                	},
			                                	close: function (thisTip, eOpts) {
//			                			    		thisTip.destroy();
			                                	}
			                                }
			                			});
	                				}else{
	                					TD.tip.setHtml(rawVvalue);
	                				}
	                			}
	                		}
	                	}
	                },
	                afterOnCellMouseOut: function(event, coords, TD){
	                	if(rpcDailyReportHelper!=null&&rpcDailyReportHelper.hot!=''&&rpcDailyReportHelper.hot!=undefined && rpcDailyReportHelper.hot.getDataAtCell!=undefined){
	                		var value=rpcDailyReportHelper.sourceData[coords.row][coords.col];
	                		if(coords.row>=rpcDailyReportHelper.templateData.header.length){
//	                			TD.outerHTML='<td class="htDimmed">'+TD.innerText+'</td>';
	                		}
	                	}
	                },
	                afterChange: function (changes, source) {
	                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
	                    if (rpcDailyReportHelper!=null && rpcDailyReportHelper.hot!=undefined && rpcDailyReportHelper.hot!='' && changes != null) {
	                        for (var i = 0; i < changes.length; i++) {
	                            var params = [];
	                            var index = changes[i][0]; //行号码
	                            var rowdata = rpcDailyReportHelper.hot.getDataAtRow(index);
	                            
	                            var editCellInfo={};
	                            var editRow=changes[i][0];
	                            var editCol=changes[i][1];
	                            var column=rpcDailyReportHelper.columns[editCol];
	                            
	                            editCellInfo.editRow=editRow;
	                            editCellInfo.editCol=editCol;
	                            editCellInfo.column=column;
	                            editCellInfo.recordId=rowdata[rowdata.length-1]
	                            editCellInfo.oldValue=changes[i][2];
	                            editCellInfo.newValue=changes[i][3];
	                            editCellInfo.header=false;
	                            if(editCellInfo.editRow<rpcDailyReportHelper.templateData.header.length){
	                            	editCellInfo.header=true;
	                            }
	                            
	                            var isExit=false;
	                            for(var j=0;j<rpcDailyReportHelper.contentUpdateList.length;j++){
	                            	if(editCellInfo.editRow==rpcDailyReportHelper.contentUpdateList[j].editRow && editCellInfo.editCol==rpcDailyReportHelper.contentUpdateList[j].editCol){
	                            		rpcDailyReportHelper.contentUpdateList[j].newValue=editCellInfo.newValue;
	                            		isExit=true;
	                            		break;
	                            	}
	                            }
	                            if(!isExit){
	                            	rpcDailyReportHelper.contentUpdateList.push(editCellInfo);
	                            }
	                        }
	                    }
	                }
	            });
	        }
	        rpcDailyReportHelper.getData = function (data) {
//	            rpcDailyReportHelper.get_data = data;
//	            rpcDailyReportHelper.editable = +data.Editable;
//	            var _daily = data.totalRoot;
//	            rpcDailyReportHelper.sum = _daily.length;
//	            rpcDailyReportHelper.updateArray();
//	            _daily.forEach(function (_day, index) {
//
//	            	if(_day.id=="合计" || _day.id=="平均"){
//	            		rpcDailyReportHelper.my_data[index + 3][0] = _day.id;
//	            	}else{
//	            		rpcDailyReportHelper.my_data[index + 3][0] = index+1;
//	            	}
//	                
//	                rpcDailyReportHelper.my_data[index + 3][1] = _day.wellName;
//	                rpcDailyReportHelper.my_data[index + 3][2] = _day.calculateDate;
//	                
//	                rpcDailyReportHelper.my_data[index + 3][3] = _day.commTime;
//	                var commRange=_day.commRange;
//	                if(commRange.length>12){
//	                	commRange=commRange.substring(0, 11)+"...";
//	                }
//	                var runRange=_day.runRange;
//	                if(runRange.length>12){
//	                	runRange=runRange.substring(0, 11)+"...";
//	                }
//	                rpcDailyReportHelper.my_data[index + 3][4] = commRange;
//	                rpcDailyReportHelper.my_data[index + 3][5] = _day.commTimeEfficiency;
//	                
//	                rpcDailyReportHelper.my_data[index + 3][6] = _day.runTime;
//	                rpcDailyReportHelper.my_data[index + 3][7] = runRange;
//	                rpcDailyReportHelper.my_data[index + 3][8] = _day.runTimeEfficiency;
//	                
//	                rpcDailyReportHelper.my_data[index + 3][9] = _day.resultName;
//	                rpcDailyReportHelper.my_data[index + 3][10] = _day.optimizationSuggestion;
//	                
//	                rpcDailyReportHelper.my_data[index + 3][11] = _day.liquidProduction;
//					rpcDailyReportHelper.my_data[index + 3][12] = _day.oilProduction;
//	                rpcDailyReportHelper.my_data[index + 3][13] = _day.waterProduction;
//	                rpcDailyReportHelper.my_data[index + 3][14] = _day.waterCut;
//	                rpcDailyReportHelper.my_data[index + 3][15] = _day.fullnesscoEfficient;
//	                
//	                rpcDailyReportHelper.my_data[index + 3][16] = _day.wattDegreeBalance;
//	                rpcDailyReportHelper.my_data[index + 3][17] = _day.iDegreeBalance;
//	                rpcDailyReportHelper.my_data[index + 3][18] = _day.deltaRadius;
//	                
//	                rpcDailyReportHelper.my_data[index + 3][19] = _day.systemEfficiency;
//	                rpcDailyReportHelper.my_data[index + 3][20] = _day.surfaceSystemEfficiency;
//	                rpcDailyReportHelper.my_data[index + 3][21] = _day.welldownSystemEfficiency;
//	                rpcDailyReportHelper.my_data[index + 3][22] = _day.energyPer100mLift;
//	                
//	                rpcDailyReportHelper.my_data[index + 3][23] = _day.todayKWattH;
//	                
//	                rpcDailyReportHelper.my_data[index + 3][24] = _day.remark;
//	            })
//
//	            var _total = data.totalCount;
//	            rpcDailyReportHelper.last_index = _daily.length + 3;
	        }
	        
	        rpcDailyReportHelper.saveData = function () {
	        	if(rpcDailyReportHelper.contentUpdateList.length>0){
	        		rpcDailyReportHelper.editData.contentUpdateList=rpcDailyReportHelper.contentUpdateList;
	        		var wellName='';
	        	    var wellId=0;
	        	    var selectRow= Ext.getCmp("RPCDailyReportDeviceListSelectRow_Id").getValue();
	        	    if(selectRow>=0){
	        	    	wellName=Ext.getCmp("RPCDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
	        	    	wellId=Ext.getCmp("RPCDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
	        	    }
//	        		alert(JSON.stringify(rpcDailyReportHelper.editData));
	        		Ext.Ajax.request({
	                    method: 'POST',
	                    url: context + '/reportDataMamagerController/saveDailyReportData',
	                    success: function (response) {
	                        rdata = Ext.JSON.decode(response.responseText);
	                        if (rdata.success) {
	                        	Ext.MessageBox.alert("信息", '保存成功');
	                        	rpcDailyReportHelper.clearContainer();
	                        	CreateRPCDailyReportTable();
	                        } else {
	                        	rpcDailyReportHelper.clearContainer();
	                        	Ext.MessageBox.alert("信息", "数据保存失败");
	                        }
	                    },
	                    failure: function () {
	                        Ext.MessageBox.alert("信息", "请求失败");
	                    },
	                    params: {
	                    	wellId:wellId,
	                    	wellName:wellName,
	                    	data: JSON.stringify(rpcDailyReportHelper.editData),
	                        deviceType: 0
	                    }
	                });
	        	}else{
	        		Ext.MessageBox.alert("信息", "无数据变化！");
	        	}
	        }
	        rpcDailyReportHelper.clearContainer = function () {
	        	rpcDailyReportHelper.editData={};
            	rpcDailyReportHelper.contentUpdateList=[];
	        }

	        var init = function () {
	        	rpcDailyReportHelper.initData();
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

function CreateRPCDailyReportCurve(){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
//    var wellName = Ext.getCmp('RPCDailyReportPanelWellListCombo_Id').getValue();
    var startDate = Ext.getCmp('RPCDailyReportStartDate_Id').rawValue;
    var endDate = Ext.getCmp('RPCDailyReportEndDate_Id').rawValue;
    
    var wellName='';
    var wellId=0;
    var selectRow= Ext.getCmp("RPCDailyReportDeviceListSelectRow_Id").getValue();
    if(selectRow>=0){
    	wellName=Ext.getCmp("RPCDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
    	wellId=Ext.getCmp("RPCDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
    }
    
    Ext.getCmp("RPCDailyReportCurvePanel_id").el.mask(cosog.string.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reportDataMamagerController/getSingleWellDailyReportCurveData',
		success:function(response) {
			Ext.getCmp("RPCDailyReportCurvePanel_id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			
			var startDate=Ext.getCmp('RPCDailyReportStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(result.startDate);
            }
            var endDate=Ext.getCmp('RPCDailyReportEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(result.endDate);
            }
            
		    var data = result.list;
		    var graphicSet=result.graphicSet;
		    
		    var defaultColors=["#7cb5ec", "#434348", "#90ed7d", "#f7a35c", "#8085e9", "#f15c80", "#e4d354", "#2b908f", "#f45b5b", "#91e8e1"];
		    var tickInterval = 1;
		    tickInterval = Math.floor(data.length / 10) + 1;
		    if(tickInterval<100){
		    	tickInterval=100;
		    }
		    var title = result.wellName + "报表曲线";
		    var xTitle='日期';
		    var legendName =result.curveItems;
		    
		    var color=result.curveColors;
		    for(var i=0;i<color.length;i++){
		    	if(color[i]==''){
		    		color[i]=defaultColors[i%10];
		    	}else{
		    		color[i]='#'+color[i];
		    	}
		    }
		    
		    var yTitle=legendName[0];
		    
		    var series = "[";
		    var yAxis= [];
		    for (var i = 0; i < legendName.length; i++) {
		        var maxValue=null;
		        var minValue=null;
		        var allPositive=true;//全部是非负数
		        var allNegative=true;//全部是负值
		    	series += "{\"name\":\"" + legendName[i] + "\",marker:{enabled: false},"+"\"yAxis\":"+i+",";
		        series += "\"data\":[";
		        for (var j = 0; j < data.length; j++) {
		        	series += "[" + Date.parse(data[j].calDate.replace(/-/g, '/')) + "," + data[j].data[i] + "]";
		            if (j != data.length - 1) {
		                series += ",";
		            }
		            if(parseFloat(data[j].data[i])<0){
		            	allPositive=false;
		            }else if(parseFloat(data[j].data[i])>=0){
		            	allNegative=false;
		            }
		        }
		        series += "]}";
		        if (i != legendName.length - 1) {
		            series += ",";
		        }
		        var opposite=false;
		        if(i>0){
		        	opposite=true;
		        }
		        if(allNegative){
		        	maxValue=0;
		        }else if(allPositive){
		        	minValue=0;
		        }
		        if(JSON.stringify(graphicSet) != "{}" && isNotVal(graphicSet.Report) ){
			    	for(var j=0;j<graphicSet.Report.length;j++){
			    		if(graphicSet.Report[j].itemCode!=undefined && graphicSet.Report[j].itemCode.toUpperCase()==result.curveItemCodes[i].toUpperCase()){
			    			if(isNotVal(graphicSet.Report[j].yAxisMaxValue)){
					    		maxValue=parseFloat(graphicSet.Report[j].yAxisMaxValue);
					    	}
					    	if(isNotVal(graphicSet.Report[j].yAxisMinValue)){
					    		minValue=parseFloat(graphicSet.Report[j].yAxisMinValue);
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
		        yAxis.push(singleAxis);
		        
		    }
		    series += "]";
		    
		    var ser = Ext.JSON.decode(series);
		    var timeFormat='%m-%d';
//		    timeFormat='%H:%M';
		    initRPCDailyReportCurveChartFn(ser, tickInterval, 'RPCDailyReportCurveDiv_Id', title, '', '', yAxis, color,true,timeFormat);
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId: orgId,
			wellId:wellId,
			wellName: wellName,
			startDate: startDate,
			endDate: endDate,
            deviceType:0
        }
	});
};

function initRPCDailyReportCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, yAxis, color,legend,timeFormat) {
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
            filename: 'class-booking-chart',
            url: context + '/exportHighcharsPicController/export',
            buttons: {
            	contextButton: {
            		menuItems:[dafaultMenuItem[0],dafaultMenuItem[1],dafaultMenuItem[2],dafaultMenuItem[3],dafaultMenuItem[4],dafaultMenuItem[5],dafaultMenuItem[6],dafaultMenuItem[7],
            			,dafaultMenuItem[2],{
            				text: '图形设置',
            				onclick: function() {
            					var window = Ext.create("AP.view.reportOut.ReportCurveSetWindow", {
                                    title: '报表曲线设置'
                                });
                                window.show();
            				}
            			}]
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