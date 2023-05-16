var pcpSingleWellDailyReportHelper=null
Ext.define("AP.view.reportOut.PCPSingleWellDailyReportPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.PCPSingleWellDailyReportPanel',
    layout: 'fit',
    id: 'PCPSingleWellDailyReportPanel_view',
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
                    var wellName = Ext.getCmp('PCPSingleWellDailyReportPanelWellListCombo_Id').getValue();
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
                id: 'PCPSingleWellDailyReportPanelWellListCombo_Id',
                hidden:false,
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
                        onEnterKeyDownFN(field, e, 'PCPSingleWellDailyReportPanel_Id');
                    },
                    select: function (combo, record, index) {
                    	Ext.getCmp("PCPSingleWellDailyReportDeviceListSelectRow_Id").setValue(-1);
                    	Ext.getCmp("PCPSingleWellDailyReportGridPanel_Id").getStore().load();
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
                	var gridPanel = Ext.getCmp("PCPSingleWellDailyReportGridPanel_Id");
        			if (isNotVal(gridPanel)) {
        				gridPanel.getStore().load();
        			}else{
        				Ext.create('AP.store.reportOut.PCPSingleWellDailyReportWellListStore');
        			}
                }
    		},'-',wellListCombo,'-', {
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: '日期',
                labelWidth: 36,
                width: 136,
                format: 'Y-m-d',
                id: 'PCPSingleWellDailyReportStartDate_Id',
//                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                        try {
                        	CreatePCPSingleWellDailyReportTable();
                        	CreatePCPSingleWellDailyReportCurve();
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
                id: 'PCPSingleWellDailyReportEndDate_Id',
                value: new Date(),
                listeners: {
                	select: function (combo, record, index) {
                        try {
                        	CreatePCPSingleWellDailyReportTable();
                        	CreatePCPSingleWellDailyReportCurve();
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
                	CreatePCPSingleWellDailyReportTable();
                	CreatePCPSingleWellDailyReportCurve();
                }
    		},'-', {
                xtype: 'button',
                text: cosog.string.exportExcel,
                iconCls: 'export',
                handler: function (v, o) {
                	var leftOrg_Id = obtainParams('leftOrg_Id');
                	var wellName = Ext.getCmp('PCPSingleWellDailyReportPanelWellListCombo_Id').getValue();
                	var startDate = Ext.getCmp('PCPSingleWellDailyReportStartDate_Id').rawValue;
                	var endDate = Ext.getCmp('PCPSingleWellDailyReportEndDate_Id').rawValue;
                	
                	var wellName='';
                    var wellId=0;
                    var selectRow= Ext.getCmp("PCPSingleWellDailyReportDeviceListSelectRow_Id").getValue();
                    if(selectRow>=0){
                    	wellName=Ext.getCmp("PCPSingleWellDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
                    	wellId=Ext.getCmp("PCPSingleWellDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
                    }
                	
                	var url=context + '/reportDataMamagerController/exportSingleWellDailyReportData?deviceType=1&reportType=0&wellName='+URLencode(URLencode(wellName))+'&wellId='+wellId+'&startDate='+startDate+'&endDate='+endDate+'&orgId='+leftOrg_Id;
                	document.location.href = url;
                }
            }, '->',{
                xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                disabled: loginUserRoleReportEdit!=1,
                handler: function (v, o) {
                	pcpSingleWellDailyReportHelper.saveData();
                }
            },'-', {
                id: 'PCPSingleWellDailyReportTotalCount_Id',
                xtype: 'component',
                tpl: cosog.string.totalCount + ': {count}',
                style: 'margin-right:15px'
            },{
            	id: 'PCPSingleWellDailyReportDeviceListSelectRow_Id',
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
            	id: 'PCPSingleWellDailyReportWellListPanel_Id',
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
                    id:'PCPSingleWellDailyReportCurvePanel_id',
                    html: '<div id="PCPSingleWellDailyReportCurveDiv_Id" style="width:100%;height:100%;"></div>',
                    listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            if ($("#PCPSingleWellDailyReportCurveDiv_Id").highcharts() != undefined) {
                            	highchartsResize("PCPSingleWellDailyReportCurveDiv_Id");
                            }
                        }
                    }
            	},{
            		region: 'center',
            		title:'报表数据',
                    layout: "fit",
                	id:'PCPSingleWellDailyReportPanel_id',
//                    border: false,
                    html:'<div class="PCPSingleWellDailyReportContainer" style="width:100%;height:100%;"><div class="con" id="PCPSingleWellDailyReportDiv_id"></div></div>',
                    listeners: {
                    	resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    		if(pcpSingleWellDailyReportHelper!=null && pcpSingleWellDailyReportHelper.hot!=undefined){
//                    			pcpSingleWellDailyReportHelper.hot.refreshDimensions();
                    			var newWidth=width;
                        		var newHeight=height;
                        		var header=thisPanel.getHeader();
                        		if(header){
                        			newHeight=newHeight-header.lastBox.height-2;
                        		}
                        		pcpSingleWellDailyReportHelper.hot.updateSettings({
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

function CreatePCPSingleWellDailyReportTable(){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
//    var wellName = Ext.getCmp('PCPSingleWellDailyReportPanelWellListCombo_Id').getValue();
    var startDate = Ext.getCmp('PCPSingleWellDailyReportStartDate_Id').rawValue;
    var endDate = Ext.getCmp('PCPSingleWellDailyReportEndDate_Id').rawValue;
    
    var wellName='';
    var wellId=0;
    var selectRow= Ext.getCmp("PCPSingleWellDailyReportDeviceListSelectRow_Id").getValue();
    if(selectRow>=0){
    	wellName=Ext.getCmp("PCPSingleWellDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
    	wellId=Ext.getCmp("PCPSingleWellDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
    }
    
    Ext.getCmp("PCPSingleWellDailyReportPanel_id").el.mask(cosog.string.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reportDataMamagerController/getSingleWellDailyReportData',
		success:function(response) {
			Ext.getCmp("PCPSingleWellDailyReportPanel_id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			
			var startDate=Ext.getCmp('PCPSingleWellDailyReportStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(result.startDate);
            }
            var endDate=Ext.getCmp('PCPSingleWellDailyReportEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(result.endDate);
            }
            
			if(pcpSingleWellDailyReportHelper!=null){
				if(pcpSingleWellDailyReportHelper.hot!=undefined){
					pcpSingleWellDailyReportHelper.hot.destroy();
				}
				pcpSingleWellDailyReportHelper=null;
			}
			if(result.success){
				if(pcpSingleWellDailyReportHelper==null || pcpSingleWellDailyReportHelper.hot==undefined){
					pcpSingleWellDailyReportHelper = PCPSingleWellDailyReportHelper.createNew("PCPSingleWellDailyReportDiv_id","PCPSingleWellDailyReportContainer",result.template,result.data,result.columns);
					pcpSingleWellDailyReportHelper.createTable();
				}
			}else{
				$("#PCPSingleWellDailyReportDiv_id").html('');
			}
			
			Ext.getCmp("PCPSingleWellDailyReportTotalCount_Id").update({count: result.data.length});
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
			reportType: 0,
            deviceType:1
        }
	});
};


var PCPSingleWellDailyReportHelper = {
	    createNew: function (divId, containerid,templateData,contentData,columns) {
	        var pcpSingleWellDailyReportHelper = {};
	        pcpSingleWellDailyReportHelper.templateData=templateData;
	        pcpSingleWellDailyReportHelper.contentData=contentData;
	        pcpSingleWellDailyReportHelper.columns=columns;
	        pcpSingleWellDailyReportHelper.get_data = {};
	        pcpSingleWellDailyReportHelper.data=[];
	        pcpSingleWellDailyReportHelper.sourceData=[];
	        pcpSingleWellDailyReportHelper.hot = '';
	        pcpSingleWellDailyReportHelper.container = document.getElementById(divId);
	        pcpSingleWellDailyReportHelper.columnCount=0;
	        pcpSingleWellDailyReportHelper.editData={};
	        pcpSingleWellDailyReportHelper.contentUpdateList = [];
	        
	        pcpSingleWellDailyReportHelper.initData=function(){
	        	pcpSingleWellDailyReportHelper.data=[];
	        	for(var i=0;i<pcpSingleWellDailyReportHelper.templateData.header.length;i++){
	        		pcpSingleWellDailyReportHelper.templateData.header[i].title.push('');
	        		pcpSingleWellDailyReportHelper.columnCount=pcpSingleWellDailyReportHelper.templateData.header[i].title.length;
	        		
	        		var valueArr=[];
	        		var sourceValueArr=[];
	        		for(var j=0;j<pcpSingleWellDailyReportHelper.templateData.header[i].title.length;j++){
	        			valueArr.push(pcpSingleWellDailyReportHelper.templateData.header[i].title[j]);
	        			sourceValueArr.push(pcpSingleWellDailyReportHelper.templateData.header[i].title[j]);
	        		}
	        		
	        		pcpSingleWellDailyReportHelper.data.push(valueArr);
	        		pcpSingleWellDailyReportHelper.sourceData.push(sourceValueArr);
		        }
	        	for(var i=0;i<pcpSingleWellDailyReportHelper.contentData.length;i++){
	        		var valueArr=[];
	        		var sourceValueArr=[];
	        		for(var j=0;j<pcpSingleWellDailyReportHelper.contentData[i].length;j++){
	        			valueArr.push(pcpSingleWellDailyReportHelper.contentData[i][j]);
	        			sourceValueArr.push(pcpSingleWellDailyReportHelper.contentData[i][j]);
	        		}
	        		
	        		pcpSingleWellDailyReportHelper.data.push(valueArr);
		        	pcpSingleWellDailyReportHelper.sourceData.push(sourceValueArr);
		        }
	        	for(var i=pcpSingleWellDailyReportHelper.templateData.header.length;i<pcpSingleWellDailyReportHelper.data.length;i++){
	        		for(var j=0;j<pcpSingleWellDailyReportHelper.data[i].length;j++){
	        			var editable=false
	        			for(var k=0;k<pcpSingleWellDailyReportHelper.templateData.editable.length;k++){
	        				if( i>=pcpSingleWellDailyReportHelper.templateData.editable[k].startRow 
	                				&& i<=pcpSingleWellDailyReportHelper.templateData.editable[k].endRow
	                				&& j>=pcpSingleWellDailyReportHelper.templateData.editable[k].startColumn 
	                				&& j<=pcpSingleWellDailyReportHelper.templateData.editable[k].endColumn
	                		){
	        					editable=true;
	        					break;
	                		}
	        			}
	        			
	        			var value=pcpSingleWellDailyReportHelper.data[i][j];
		                if((!editable)&&value.length>12){
		                	value=value.substring(0, 11)+"...";
		                	pcpSingleWellDailyReportHelper.data[i][j]=value;
		                }
	        		}
	        	}
	        }
	        
	        pcpSingleWellDailyReportHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(pcpSingleWellDailyReportHelper!=null && pcpSingleWellDailyReportHelper.hot!=null){
	        		for(var i=0;i<pcpSingleWellDailyReportHelper.templateData.header.length;i++){
		        		if(row==i){
		        			if(isNotVal(pcpSingleWellDailyReportHelper.templateData.header[i].tdStyle)){
		        				if(isNotVal(pcpSingleWellDailyReportHelper.templateData.header[i].tdStyle.fontWeight)){
		        					td.style.fontWeight = pcpSingleWellDailyReportHelper.templateData.header[i].tdStyle.fontWeight;
		        				}
		        				if(isNotVal(pcpSingleWellDailyReportHelper.templateData.header[i].tdStyle.fontSize)){
		        					td.style.fontSize = pcpSingleWellDailyReportHelper.templateData.header[i].tdStyle.fontSize;
		        				}
		        				if(isNotVal(pcpSingleWellDailyReportHelper.templateData.header[i].tdStyle.fontFamily)){
		        					td.style.fontFamily = pcpSingleWellDailyReportHelper.templateData.header[i].tdStyle.fontFamily;
		        				}
		        				if(isNotVal(pcpSingleWellDailyReportHelper.templateData.header[i].tdStyle.height)){
		        					td.style.height = pcpSingleWellDailyReportHelper.templateData.header[i].tdStyle.height;
		        				}
		        				if(isNotVal(pcpSingleWellDailyReportHelper.templateData.header[i].tdStyle.color)){
		        					td.style.color = pcpSingleWellDailyReportHelper.templateData.header[i].tdStyle.color;
		        				}
		        				if(isNotVal(pcpSingleWellDailyReportHelper.templateData.header[i].tdStyle.backgroundColor)){
		        					td.style.backgroundColor = pcpSingleWellDailyReportHelper.templateData.header[i].tdStyle.backgroundColor;
		        				}
		        				if(isNotVal(pcpSingleWellDailyReportHelper.templateData.header[i].tdStyle.textAlign)){
		        					td.style.textAlign = pcpSingleWellDailyReportHelper.templateData.header[i].tdStyle.textAlign;
		        				}
		        			}
		        			break;
		        		}
		        	}
	        		
	        	}
	        }
	        
	        pcpSingleWellDailyReportHelper.addEditableColor = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.color='#ff0000';    
	        }
	        
	        pcpSingleWellDailyReportHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	            if (row <= 2&&row>=1) {
	                td.style.fontWeight = 'bold';
					td.style.fontSize = '13px';
					td.style.color = 'rgb(0, 0, 51)';
					td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	            }
	        }
			
			pcpSingleWellDailyReportHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (row < 1) {
	                td.style.fontWeight = 'bold';
			        td.style.fontSize = '25px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '50px';   
			    }      
	        }
			
			pcpSingleWellDailyReportHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
		         if(row < 3){
	                 td.style.fontWeight = 'bold';
			         td.style.fontSize = '5px';
			         td.style.fontFamily = 'SimHei';
	            }      
	        }
			

	        pcpSingleWellDailyReportHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        pcpSingleWellDailyReportHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(216, 228, 188)';
	        }
	        
	        pcpSingleWellDailyReportHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        pcpSingleWellDailyReportHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        pcpSingleWellDailyReportHelper.createTable = function () {
	            pcpSingleWellDailyReportHelper.container.innerHTML = "";
	            pcpSingleWellDailyReportHelper.hot = new Handsontable(pcpSingleWellDailyReportHelper.container, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: pcpSingleWellDailyReportHelper.data,
	            	hiddenColumns: {
	                    columns: [pcpSingleWellDailyReportHelper.columnCount-1],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
//	            	columns:pcpSingleWellDailyReportHelper.columns,
	            	fixedRowsTop:pcpSingleWellDailyReportHelper.templateData.fixedRowsTop, 
	                fixedRowsBottom: pcpSingleWellDailyReportHelper.templateData.fixedRowsBottom,
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: pcpSingleWellDailyReportHelper.templateData.rowHeights,
					colWidths: pcpSingleWellDailyReportHelper.templateData.columnWidths,
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
	                mergeCells: pcpSingleWellDailyReportHelper.templateData.mergeCells,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.renderer = pcpSingleWellDailyReportHelper.addStyle;
	                    cellProperties.readOnly = true;
	                    if(pcpSingleWellDailyReportHelper.templateData.editable!=null && pcpSingleWellDailyReportHelper.templateData.editable.length>0){
	                    	for(var i=0;i<pcpSingleWellDailyReportHelper.templateData.editable.length;i++){
	                    		if( row>=pcpSingleWellDailyReportHelper.templateData.editable[i].startRow 
	                    				&& row<=pcpSingleWellDailyReportHelper.templateData.editable[i].endRow
	                    				&& col>=pcpSingleWellDailyReportHelper.templateData.editable[i].startColumn 
	                    				&& col<=pcpSingleWellDailyReportHelper.templateData.editable[i].endColumn
	                    		){
	                    			cellProperties.readOnly = false;
	                    			cellProperties.renderer = pcpSingleWellDailyReportHelper.addEditableColor;
	                    		}
	                    	}
	                    }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(pcpSingleWellDailyReportHelper!=null&&pcpSingleWellDailyReportHelper.hot!=''&&pcpSingleWellDailyReportHelper.hot!=undefined && pcpSingleWellDailyReportHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=pcpSingleWellDailyReportHelper.sourceData[coords.row][coords.col];
	                		if(coords.row>=pcpSingleWellDailyReportHelper.templateData.header.length){
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
	                	if(pcpSingleWellDailyReportHelper!=null&&pcpSingleWellDailyReportHelper.hot!=''&&pcpSingleWellDailyReportHelper.hot!=undefined && pcpSingleWellDailyReportHelper.hot.getDataAtCell!=undefined){
	                		var value=pcpSingleWellDailyReportHelper.sourceData[coords.row][coords.col];
	                		if(coords.row>=pcpSingleWellDailyReportHelper.templateData.header.length){
//	                			TD.outerHTML='<td class="htDimmed">'+TD.innerText+'</td>';
	                		}
	                	}
	                },
	                afterChange: function (changes, source) {
	                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
	                    if (pcpSingleWellDailyReportHelper!=null && pcpSingleWellDailyReportHelper.hot!=undefined && pcpSingleWellDailyReportHelper.hot!='' && changes != null) {
	                        for (var i = 0; i < changes.length; i++) {
	                            var params = [];
	                            var index = changes[i][0]; //行号码
	                            var rowdata = pcpSingleWellDailyReportHelper.hot.getDataAtRow(index);
	                            
	                            var editCellInfo={};
	                            var editRow=changes[i][0];
	                            var editCol=changes[i][1];
	                            var column=pcpSingleWellDailyReportHelper.columns[editCol];
	                            
	                            editCellInfo.editRow=editRow;
	                            editCellInfo.editCol=editCol;
	                            editCellInfo.column=column;
	                            editCellInfo.recordId=rowdata[rowdata.length-1]
	                            editCellInfo.oldValue=changes[i][2];
	                            editCellInfo.newValue=changes[i][3];
	                            editCellInfo.header=false;
	                            if(editCellInfo.editRow<pcpSingleWellDailyReportHelper.templateData.header.length){
	                            	editCellInfo.header=true;
	                            }
	                            
	                            var isExit=false;
	                            for(var j=0;j<pcpSingleWellDailyReportHelper.contentUpdateList.length;j++){
	                            	if(editCellInfo.editRow==pcpSingleWellDailyReportHelper.contentUpdateList[j].editRow && editCellInfo.editCol==pcpSingleWellDailyReportHelper.contentUpdateList[j].editCol){
	                            		pcpSingleWellDailyReportHelper.contentUpdateList[j].newValue=editCellInfo.newValue;
	                            		isExit=true;
	                            		break;
	                            	}
	                            }
	                            if(!isExit){
	                            	pcpSingleWellDailyReportHelper.contentUpdateList.push(editCellInfo);
	                            }
	                        }
	                    }
	                }
	            });
	        }
	        pcpSingleWellDailyReportHelper.getData = function (data) {
	        	
	        }
	        
	        pcpSingleWellDailyReportHelper.saveData = function () {
	        	if(pcpSingleWellDailyReportHelper.contentUpdateList.length>0){
	        		pcpSingleWellDailyReportHelper.editData.contentUpdateList=pcpSingleWellDailyReportHelper.contentUpdateList;
	        		var wellName='';
	        	    var wellId=0;
	        	    var selectRow= Ext.getCmp("PCPSingleWellDailyReportDeviceListSelectRow_Id").getValue();
	        	    if(selectRow>=0){
	        	    	wellName=Ext.getCmp("PCPSingleWellDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
	        	    	wellId=Ext.getCmp("PCPSingleWellDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
	        	    }
//	        		alert(JSON.stringify(pcpSingleWellDailyReportHelper.editData));
	        		Ext.Ajax.request({
	                    method: 'POST',
	                    url: context + '/reportDataMamagerController/saveDailyReportData',
	                    success: function (response) {
	                        rdata = Ext.JSON.decode(response.responseText);
	                        if (rdata.success) {
	                        	Ext.MessageBox.alert("信息", '保存成功');
	                        	pcpSingleWellDailyReportHelper.clearContainer();
	                        	CreatePCPSingleWellDailyReportTable();
	                        	CreatePCPSingleWellDailyReportCurve();
	                        } else {
	                        	pcpSingleWellDailyReportHelper.clearContainer();
	                        	Ext.MessageBox.alert("信息", "数据保存失败");
	                        }
	                    },
	                    failure: function () {
	                        Ext.MessageBox.alert("信息", "请求失败");
	                    },
	                    params: {
	                    	wellId:wellId,
	                    	wellName:wellName,
	                    	data: JSON.stringify(pcpSingleWellDailyReportHelper.editData),
	                        deviceType: 1
	                    }
	                });
	        	}else{
	        		Ext.MessageBox.alert("信息", "无数据变化！");
	        	}
	        }
	        pcpSingleWellDailyReportHelper.clearContainer = function () {
	        	pcpSingleWellDailyReportHelper.editData={};
            	pcpSingleWellDailyReportHelper.contentUpdateList=[];
	        }

	        var init = function () {
	        	pcpSingleWellDailyReportHelper.initData();
	        }

	        init();
	        return pcpSingleWellDailyReportHelper;
	    }
	};

function createPCPSingleWellDailyReportWellListDataColumn(columnInfo) {
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

function CreatePCPSingleWellDailyReportCurve(){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
//  var wellName = Ext.getCmp('PCPSingleWellDailyReportPanelWellListCombo_Id').getValue();
  var startDate = Ext.getCmp('PCPSingleWellDailyReportStartDate_Id').rawValue;
  var endDate = Ext.getCmp('PCPSingleWellDailyReportEndDate_Id').rawValue;
  
  var wellName='';
  var wellId=0;
  var selectRow= Ext.getCmp("PCPSingleWellDailyReportDeviceListSelectRow_Id").getValue();
  if(selectRow>=0){
  	wellName=Ext.getCmp("PCPSingleWellDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
  	wellId=Ext.getCmp("PCPSingleWellDailyReportGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
  }
  
  Ext.getCmp("PCPSingleWellDailyReportCurvePanel_id").el.mask(cosog.string.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reportDataMamagerController/getSingleWellDailyReportCurveData',
		success:function(response) {
			Ext.getCmp("PCPSingleWellDailyReportCurvePanel_id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			
			var startDate=Ext.getCmp('PCPSingleWellDailyReportStartDate_Id');
          if(startDate.rawValue==''||null==startDate.rawValue){
          	startDate.setValue(result.startDate);
          }
          var endDate=Ext.getCmp('PCPSingleWellDailyReportEndDate_Id');
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
		    var title = result.wellName + "报表曲线";
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
		    initPCPSingleWellDailyReportCurveChartFn(series, tickInterval, 'PCPSingleWellDailyReportCurveDiv_Id', title, '', '', yAxis, color_all,true,timeFormat);
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
			reportType: 0,
            deviceType:1
		}
	});
};

function initPCPSingleWellDailyReportCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, yAxis, color,legend,timeFormat) {
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