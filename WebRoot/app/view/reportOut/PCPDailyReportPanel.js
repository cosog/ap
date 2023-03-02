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
                        onEnterKeyDownFN(field, e, 'PCPDailyReportPanel_Id');
                    },
                    select: function (combo, record, index) {
                    	CreatePCPDailyReportTable();
                    	CreatePCPDailyReportCurve();
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
                width: 136,
                format: 'Y-m-d',
                id: 'PCPDailyReportStartDate_Id',
//                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                        try {
                        	CreatePCPDailyReportTable();
                        	CreatePCPDailyReportCurve();
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
                id: 'PCPDailyReportEndDate_Id',
                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                        try {
                        	CreatePCPDailyReportTable();
                        	CreatePCPDailyReportCurve();
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
                	CreatePCPDailyReportCurve();
                }
    		},'-', {
                xtype: 'button',
                text: cosog.string.exportExcel,
                iconCls: 'export',
                handler: function (v, o) {
                	var leftOrg_Id = obtainParams('leftOrg_Id');
                	var wellName = Ext.getCmp('PCPDailyReportPanelWellListCombo_Id').getValue();
                	var startDate = Ext.getCmp('PCPDailyReportStartDate_Id').rawValue;
                	var endDate = Ext.getCmp('PCPDailyReportEndDate_Id').rawValue;
                	
                	var wellName='';
                    var wellId=0;
                    var selectRow= Ext.getCmp("PCPDailyReportDeviceListSelectRow_Id").getValue();
                    if(selectRow>=0){
                    	wellName=Ext.getCmp("PCPDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
                    	wellId=Ext.getCmp("PCPDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
                    }
                	
                	var url=context + '/reportDataMamagerController/exportSingleWellDailyReportData?deviceType=1&wellName='+URLencode(URLencode(wellName))+'&wellId='+wellId+'&startDate='+startDate+'&endDate='+endDate+'&orgId='+leftOrg_Id;
                	document.location.href = url;
                }
            }, '->',{
                xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                disabled: loginUserRoleReportEdit!=1,
                handler: function (v, o) {
                	pcpDailyReportHelper.saveData();
                }
            },'-', {
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
            	title: '设备列表',
            	id: 'PCPDailyReportWellListPanel_Id',
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
                    id:'PCPDailyReportCurvePanel_id',
                    html: '<div id="PCPDailyReportCurveDiv_Id" style="width:100%;height:100%;"></div>',
                    listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            if ($("#PCPDailyReportCurveDiv_Id").highcharts() != undefined) {
                            	highchartsResize("PCPDailyReportCurveDiv_Id");
                            }
                        }
                    }
            	},{
            		region: 'center',
            		title:'报表数据',
                    layout: "fit",
                	id:'PCPDailyReportPanel_id',
//                    border: false,
                    html:'<div class="PCPDailyReportContainer" style="width:100%;height:100%;"><div class="con" id="PCPDailyReportDiv_id"></div></div>',
                    listeners: {
                    	resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    		if(pcpDailyReportHelper!=null && pcpDailyReportHelper.hot!=undefined){
//                    			pcpDailyReportHelper.hot.refreshDimensions();
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
            }]

        });
        me.callParent(arguments);
    }
});

function CreatePCPDailyReportTable(){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
//    var wellName = Ext.getCmp('PCPDailyReportPanelWellListCombo_Id').getValue();
    var startDate = Ext.getCmp('PCPDailyReportStartDate_Id').rawValue;
    var endDate = Ext.getCmp('PCPDailyReportEndDate_Id').rawValue;
    
    var wellName='';
    var wellId=0;
    var selectRow= Ext.getCmp("PCPDailyReportDeviceListSelectRow_Id").getValue();
    if(selectRow>=0){
    	wellName=Ext.getCmp("PCPDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
    	wellId=Ext.getCmp("PCPDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
    }
    
    Ext.getCmp("PCPDailyReportPanel_id").el.mask(cosog.string.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reportDataMamagerController/getSingleWellDailyReportData',
		success:function(response) {
			Ext.getCmp("PCPDailyReportPanel_id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			
			var startDate=Ext.getCmp('PCPDailyReportStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(result.startDate);
            }
            var endDate=Ext.getCmp('PCPDailyReportEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(result.endDate);
            }
            
			if(pcpDailyReportHelper!=null){
				if(pcpDailyReportHelper.hot!=undefined){
					pcpDailyReportHelper.hot.destroy();
				}
				pcpDailyReportHelper=null;
			}
			if(result.success){
				if(pcpDailyReportHelper==null || pcpDailyReportHelper.hot==undefined){
					pcpDailyReportHelper = PCPDailyReportHelper.createNew("PCPDailyReportDiv_id","PCPDailyReportContainer",result.template,result.data,result.columns);
					pcpDailyReportHelper.createTable();
				}
			}else{
				$("#PCPDailyReportDiv_id").html('');
			}
			
			Ext.getCmp("PCPDailyReportTotalCount_Id").update({count: result.data.length});
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
            deviceType:1
        }
	});
};


var PCPDailyReportHelper = {
	    createNew: function (divid, containerid,templateData,contentData,columns) {
	        var pcpDailyReportHelper = {};
	        pcpDailyReportHelper.templateData=templateData;
	        pcpDailyReportHelper.contentData=contentData;
	        pcpDailyReportHelper.columns=columns;
	        pcpDailyReportHelper.get_data = {};
	        pcpDailyReportHelper.data=[];
	        pcpDailyReportHelper.sourceData=[];
	        pcpDailyReportHelper.hot = '';
	        pcpDailyReportHelper.container = document.getElementById(divid);
	        pcpDailyReportHelper.columnCount=0;
	        pcpDailyReportHelper.editData={};
	        pcpDailyReportHelper.contentUpdateList = [];
	        
	        pcpDailyReportHelper.initData=function(){
	        	pcpDailyReportHelper.data=[];
	        	for(var i=0;i<pcpDailyReportHelper.templateData.header.length;i++){
	        		pcpDailyReportHelper.templateData.header[i].title.push('');
	        		pcpDailyReportHelper.columnCount=pcpDailyReportHelper.templateData.header[i].title.length;
	        		
	        		var valueArr=[];
	        		var sourceValueArr=[];
	        		for(var j=0;j<pcpDailyReportHelper.templateData.header[i].title.length;j++){
	        			valueArr.push(pcpDailyReportHelper.templateData.header[i].title[j]);
	        			sourceValueArr.push(pcpDailyReportHelper.templateData.header[i].title[j]);
	        		}
	        		
	        		pcpDailyReportHelper.data.push(valueArr);
	        		pcpDailyReportHelper.sourceData.push(sourceValueArr);
		        }
	        	for(var i=0;i<pcpDailyReportHelper.contentData.length;i++){
	        		var valueArr=[];
	        		var sourceValueArr=[];
	        		for(var j=0;j<pcpDailyReportHelper.contentData[i].length;j++){
	        			valueArr.push(pcpDailyReportHelper.contentData[i][j]);
	        			sourceValueArr.push(pcpDailyReportHelper.contentData[i][j]);
	        		}
	        		
	        		pcpDailyReportHelper.data.push(valueArr);
		        	pcpDailyReportHelper.sourceData.push(sourceValueArr);
		        }
	        	for(var i=pcpDailyReportHelper.templateData.header.length;i<pcpDailyReportHelper.data.length;i++){
	        		for(var j=0;j<pcpDailyReportHelper.data[i].length;j++){
	        			var value=pcpDailyReportHelper.data[i][j];
		                if(value.length>12){
		                	value=value.substring(0, 11)+"...";
		                	pcpDailyReportHelper.data[i][j]=value;
		                }
	        		}
	        	}
	        }
	        
	        pcpDailyReportHelper.addEditableColor = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.color='#ff0000';    
	        }
	        
	        pcpDailyReportHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(pcpDailyReportHelper!=null && pcpDailyReportHelper.hot!=null){
	        		for(var i=0;i<pcpDailyReportHelper.templateData.header.length;i++){
		        		if(row==i){
		        			if(isNotVal(pcpDailyReportHelper.templateData.header[i].tdStyle)){
		        				if(isNotVal(pcpDailyReportHelper.templateData.header[i].tdStyle.fontWeight)){
		        					td.style.fontWeight = pcpDailyReportHelper.templateData.header[i].tdStyle.fontWeight;
		        				}
		        				if(isNotVal(pcpDailyReportHelper.templateData.header[i].tdStyle.fontSize)){
		        					td.style.fontSize = pcpDailyReportHelper.templateData.header[i].tdStyle.fontSize;
		        				}
		        				if(isNotVal(pcpDailyReportHelper.templateData.header[i].tdStyle.fontFamily)){
		        					td.style.fontFamily = pcpDailyReportHelper.templateData.header[i].tdStyle.fontFamily;
		        				}
		        				if(isNotVal(pcpDailyReportHelper.templateData.header[i].tdStyle.height)){
		        					td.style.height = pcpDailyReportHelper.templateData.header[i].tdStyle.height;
		        				}
		        				if(isNotVal(pcpDailyReportHelper.templateData.header[i].tdStyle.color)){
		        					td.style.color = pcpDailyReportHelper.templateData.header[i].tdStyle.color;
		        				}
		        				if(isNotVal(pcpDailyReportHelper.templateData.header[i].tdStyle.backgroundColor)){
		        					td.style.backgroundColor = pcpDailyReportHelper.templateData.header[i].tdStyle.backgroundColor;
		        				}
		        			}
		        			break;
		        		}
		        	}
	        	}
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
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: pcpDailyReportHelper.data,
	            	hiddenColumns: {
	                    columns: [pcpDailyReportHelper.columnCount-1],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
//	            	columns:pcpDailyReportHelper.columns,
	            	fixedRowsTop:pcpDailyReportHelper.templateData.fixedRowsTop, 
	                fixedRowsBottom: pcpDailyReportHelper.templateData.fixedRowsBottom,
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: pcpDailyReportHelper.templateData.rowHeights,
					colWidths: pcpDailyReportHelper.templateData.columnWidths,
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
	                mergeCells: pcpDailyReportHelper.templateData.mergeCells,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.renderer = pcpDailyReportHelper.addStyle;
	                    cellProperties.readOnly = true;
	                    if(pcpDailyReportHelper.templateData.editable!=null && pcpDailyReportHelper.templateData.editable.length>0){
	                    	for(var i=0;i<pcpDailyReportHelper.templateData.editable.length;i++){
	                    		if( row>=pcpDailyReportHelper.templateData.editable[i].startRow 
	                    				&& row<=pcpDailyReportHelper.templateData.editable[i].endRow
	                    				&& col>=pcpDailyReportHelper.templateData.editable[i].startColumn 
	                    				&& col<=pcpDailyReportHelper.templateData.editable[i].endColumn
	                    		){
	                    			cellProperties.readOnly = false;
	                    			cellProperties.renderer = pcpDailyReportHelper.addEditableColor;
	                    		}
	                    	}
	                    }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(pcpDailyReportHelper!=null&&pcpDailyReportHelper.hot!=''&&pcpDailyReportHelper.hot!=undefined && pcpDailyReportHelper.hot.getDataAtCell!=undefined){
	                		var rawVvalue=pcpDailyReportHelper.sourceData[coords.row][coords.col];
	                		if(coords.row>=pcpDailyReportHelper.templateData.header.length){
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
	                					TD.tip.update({
	                						html: rawVvalue
	                					});
	                				}
	                			}
	                		}
	                	}
	                },
	                afterOnCellMouseOut: function(event, coords, TD){
	                	if(pcpDailyReportHelper!=null&&pcpDailyReportHelper.hot!=''&&pcpDailyReportHelper.hot!=undefined && pcpDailyReportHelper.hot.getDataAtCell!=undefined){
	                		var value=pcpDailyReportHelper.sourceData[coords.row][coords.col];
	                		if(coords.row>=pcpDailyReportHelper.templateData.header.length){
//	                			TD.outerHTML='<td class="htDimmed">'+TD.innerText+'</td>';
	                		}
	                	}
	                },
	                afterChange: function (changes, source) {
	                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
	                    if (pcpDailyReportHelper!=null && pcpDailyReportHelper.hot!=undefined && pcpDailyReportHelper.hot!='' && changes != null) {
	                        for (var i = 0; i < changes.length; i++) {
	                            var params = [];
	                            var index = changes[i][0]; //行号码
	                            var rowdata = pcpDailyReportHelper.hot.getDataAtRow(index);
	                            
	                            var editCellInfo={};
	                            var editRow=changes[i][0];
	                            var editCol=changes[i][1];
	                            var column=pcpDailyReportHelper.columns[editCol];
	                            
	                            editCellInfo.editRow=editRow;
	                            editCellInfo.editCol=editCol;
	                            editCellInfo.column=column;
	                            editCellInfo.recordId=rowdata[rowdata.length-1]
	                            editCellInfo.oldValue=changes[i][2];
	                            editCellInfo.newValue=changes[i][3];
	                            
	                            var isExit=false;
	                            for(var j=0;j<pcpDailyReportHelper.contentUpdateList.length;j++){
	                            	if(editCellInfo.editRow==pcpDailyReportHelper.contentUpdateList[j].editRow && editCellInfo.editCol==pcpDailyReportHelper.contentUpdateList[j].editCol){
	                            		pcpDailyReportHelper.contentUpdateList[j].newValue=editCellInfo.newValue;
	                            		isExit=true;
	                            		break;
	                            	}
	                            }
	                            if(!isExit){
	                            	pcpDailyReportHelper.contentUpdateList.push(editCellInfo);
	                            }
	                        }
	                    }
	                }
	            });
	        }
	        pcpDailyReportHelper.getData = function (data) {
//	            pcpDailyReportHelper.get_data = data;
//	            pcpDailyReportHelper.editable = +data.Editable;
//	            var _daily = data.totalRoot;
//	            pcpDailyReportHelper.sum = _daily.length;
//	            pcpDailyReportHelper.updateArray();
//	            _daily.forEach(function (_day, index) {
//
//	            	if(_day.id=="合计" || _day.id=="平均"){
//	            		pcpDailyReportHelper.my_data[index + 3][0] = _day.id;
//	            	}else{
//	            		pcpDailyReportHelper.my_data[index + 3][0] = index+1;
//	            	}
//	                
//	                pcpDailyReportHelper.my_data[index + 3][1] = _day.wellName;
//	                pcpDailyReportHelper.my_data[index + 3][2] = _day.calculateDate;
//	                
//	                pcpDailyReportHelper.my_data[index + 3][3] = _day.commTime;
//	                var commRange=_day.commRange;
//	                if(commRange.length>12){
//	                	commRange=commRange.substring(0, 11)+"...";
//	                }
//	                var runRange=_day.runRange;
//	                if(runRange.length>12){
//	                	runRange=runRange.substring(0, 11)+"...";
//	                }
//	                pcpDailyReportHelper.my_data[index + 3][4] = commRange;
//	                pcpDailyReportHelper.my_data[index + 3][5] = _day.commTimeEfficiency;
//	                
//	                pcpDailyReportHelper.my_data[index + 3][6] = _day.runTime;
//	                pcpDailyReportHelper.my_data[index + 3][7] = runRange;
//	                pcpDailyReportHelper.my_data[index + 3][8] = _day.runTimeEfficiency;
//	                
//	                pcpDailyReportHelper.my_data[index + 3][9] = _day.resultName;
//	                pcpDailyReportHelper.my_data[index + 3][10] = _day.optimizationSuggestion;
//	                
//	                pcpDailyReportHelper.my_data[index + 3][11] = _day.liquidProduction;
//					pcpDailyReportHelper.my_data[index + 3][12] = _day.oilProduction;
//	                pcpDailyReportHelper.my_data[index + 3][13] = _day.waterProduction;
//	                pcpDailyReportHelper.my_data[index + 3][14] = _day.waterCut;
//	                pcpDailyReportHelper.my_data[index + 3][15] = _day.fullnesscoEfficient;
//	                
//	                pcpDailyReportHelper.my_data[index + 3][16] = _day.wattDegreeBalance;
//	                pcpDailyReportHelper.my_data[index + 3][17] = _day.iDegreeBalance;
//	                pcpDailyReportHelper.my_data[index + 3][18] = _day.deltaRadius;
//	                
//	                pcpDailyReportHelper.my_data[index + 3][19] = _day.systemEfficiency;
//	                pcpDailyReportHelper.my_data[index + 3][20] = _day.surfaceSystemEfficiency;
//	                pcpDailyReportHelper.my_data[index + 3][21] = _day.welldownSystemEfficiency;
//	                pcpDailyReportHelper.my_data[index + 3][22] = _day.energyPer100mLift;
//	                
//	                pcpDailyReportHelper.my_data[index + 3][23] = _day.todayKWattH;
//	                
//	                pcpDailyReportHelper.my_data[index + 3][24] = _day.remark;
//	            })
//
//	            var _total = data.totalCount;
//	            pcpDailyReportHelper.last_index = _daily.length + 3;
	        }
	        
	        pcpDailyReportHelper.saveData = function () {
	        	if(pcpDailyReportHelper.contentUpdateList.length>0){
	        		pcpDailyReportHelper.editData.contentUpdateList=pcpDailyReportHelper.contentUpdateList;
//	        		alert(JSON.stringify(pcpDailyReportHelper.editData));
	        		Ext.Ajax.request({
	                    method: 'POST',
	                    url: context + '/reportDataMamagerController/saveDailyReportData',
	                    success: function (response) {
	                        rdata = Ext.JSON.decode(response.responseText);
	                        if (rdata.success) {
	                        	Ext.MessageBox.alert("信息", '保存成功');
	                        	pcpDailyReportHelper.clearContainer();
	                        	CreatePCPDailyReportCurve();
	                        	CreatePCPDailyReportTable();
	                        } else {
	                        	pcpDailyReportHelper.clearContainer();
	                        	Ext.MessageBox.alert("信息", "数据保存失败");
	                        }
	                    },
	                    failure: function () {
	                        Ext.MessageBox.alert("信息", "请求失败");
	                    },
	                    params: {
	                    	data: JSON.stringify(pcpDailyReportHelper.editData),
	                        deviceType: 1
	                    }
	                });
	        	}else{
	        		Ext.MessageBox.alert("信息", "无数据变化！");
	        	}
	        }
	        pcpDailyReportHelper.clearContainer = function () {
	        	pcpDailyReportHelper.editData={};
            	pcpDailyReportHelper.contentUpdateList=[];
	        }

	        var init = function () {
	        	pcpDailyReportHelper.initData();
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

function CreatePCPDailyReportCurve(){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
//    var wellName = Ext.getCmp('PCPDailyReportPanelWellListCombo_Id').getValue();
    var startDate = Ext.getCmp('PCPDailyReportStartDate_Id').rawValue;
    var endDate = Ext.getCmp('PCPDailyReportEndDate_Id').rawValue;
    
    var wellName='';
    var wellId=0;
    var selectRow= Ext.getCmp("PCPDailyReportDeviceListSelectRow_Id").getValue();
    if(selectRow>=0){
    	wellName=Ext.getCmp("PCPDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
    	wellId=Ext.getCmp("PCPDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
    }
    
    Ext.getCmp("PCPDailyReportCurvePanel_id").el.mask(cosog.string.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reportDataMamagerController/getSingleWellDailyReportCurveData',
		success:function(response) {
			Ext.getCmp("PCPDailyReportCurvePanel_id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			
			var startDate=Ext.getCmp('PCPDailyReportStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(result.startDate);
            }
            var endDate=Ext.getCmp('PCPDailyReportEndDate_Id');
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
		    initPCPDailyReportCurveChartFn(ser, tickInterval, 'PCPDailyReportCurveDiv_Id', title, '', '', yAxis, color,true,timeFormat);
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
            deviceType:1
        }
	});
};

function initPCPDailyReportCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, yAxis, color,legend,timeFormat) {
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