var protocolImportOverlayHandsontableHelper=null;
var protocolImportErrorHandsontableHelper=null;
Ext.define("AP.view.acquisitionUnit.PrototolImportOverlayDataWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.prototolImportOverlayDataWindow',
    id: 'PrototolImportOverlayDataWindow_Id',
    layout: 'fit',
    title:'冲突窗口',
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 700,
    minWidth: 600,
    height: 600,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    padding:0,
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            layout:'border',
            tbar:[{
                xtype: 'label',
                margin: '0 0 0 5',
                html: '<font color=red>以下导入内容已存在，继续保存将覆盖已有记录</font>'
            },'->',{
                xtype: 'button',
                text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function (v, o) {
                	var treeGridPanel = Ext.getCmp("ImportProtocolContentTreeGridPanel_Id");
                	if(isNotVal(treeGridPanel)){
//                		var _record = treeGridPanel.store.data.items;
                		var _record = treeGridPanel.getChecked();
                		var addContent={};
                		addContent.acqUnitList=[];
                		addContent.displayUnitList=[];
                		addContent.alarmUnitList=[];

                		addContent.acqInstanceList=[];
                		addContent.displayInstanceList=[];
                		addContent.alarmInstanceList=[];
                		
                		Ext.Array.each(_record, function (name, index, countriesItSelf) {
                			var classes=_record[index].data.classes;
                			if(classes==1){
                				var type=_record[index].parentNode.data.type;
                				if(type==0){//采控单元
                					var acqUnit={};
                					acqUnit.id=_record[index].data.id;
                					acqUnit.acqGroupList=[];
                					if(_record[index].childNodes!=null && _record[index].childNodes.length>0){
                						for(var i=0;i<_record[index].childNodes.length;i++){
                							acqUnit.acqGroupList.push(_record[index].childNodes[i].data.id); 
                						}
                					}
                					addContent.acqUnitList.push(acqUnit);
                				}else if(type==1){//显示单元
                					addContent.displayUnitList.push(_record[index].data.id);
                				}else if(type==2){//报警单元
                					addContent.alarmUnitList.push(_record[index].data.id);
                				}else if(type==3){//采控实例
                					addContent.acqInstanceList.push(_record[index].data.id);
                				}else if(type==4){//显示实例
                					addContent.displayInstanceList.push(_record[index].data.id);
                				}else if(type==5){//报警实例
                					addContent.alarmInstanceList.push(_record[index].data.id);
                				}
                			}
                	    });
                		Ext.Ajax.request({
                            method: 'POST',
                            url: context + '/acquisitionUnitManagerController/saveImportProtocolData',
                            success: function (response) {
                            	rdata = Ext.JSON.decode(response.responseText);
                            	if (rdata.success) {
                            		Ext.MessageBox.alert(loginUserLanguageResource.message, "协议及关联内容导入成功！");
                            		Ext.getCmp("PrototolImportOverlayDataWindow_Id").close();
                            	}else{
                            		Ext.MessageBox.alert(loginUserLanguageResource.message, "协议及关联内容导入失败！");
                            	}
                            },
                            failure: function () {
                                Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.requestFailure);
                            },
                            params: {
                            	data: JSON.stringify(addContent),
                            	check: 0
                            }
                        });
                	}
                }
            },'-', {
        	 	xtype: 'button',   
        	 	text: loginUserLanguageResource.cancel,
                iconCls: 'cancel',
                handler: function () {
                	Ext.getCmp("PrototolImportOverlayDataWindow_Id").close();
                }
            }],
        	items:[{
        		region: 'center',
        		layout: 'fit',
        		id: "ProtocolImportOverlayTablePanel_Id"
//        		html: '<div id="ProtocolImportOverlayTableDiv_Id" style="width:100%;height:100%;margin:0 0 0 0;"></div>',
//        		listeners: {
//        			resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
//                    	if(protocolImportOverlayHandsontableHelper!=null&&protocolImportOverlayHandsontableHelper.hot!=null&&protocolImportOverlayHandsontableHelper.hot!=undefined){
//                    		var newWidth=width;
//                    		var newHeight=height;
//                    		var header=thisPanel.getHeader();
//                    		if(header){
//                    			newHeight=newHeight-header.lastBox.height-2;
//                    		}
//                    		protocolImportOverlayHandsontableHelper.hot.updateSettings({
//                    			width:newWidth,
//                    			height:newHeight
//                    		});
//                    	}
//                    }
//        		}
        	},{
        		region: 'south',
        		layout: 'fit',
        		id: "ProtocolImportErrorTablePanel_Id",
        		height: '50%',
        		title: '错误数据',
        		split: true,
                collapsible: true,
        		html: '<div id="ProtocolImportErrorTableDiv_Id" style="width:100%;height:100%;margin:0 0 0 0;"></div>',
        		listeners: {
        			resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
        				if(protocolImportErrorHandsontableHelper!=null&&protocolImportErrorHandsontableHelper.hot!=null&&protocolImportErrorHandsontableHelper.hot!=undefined){
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		protocolImportErrorHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}
        			}
        		}
        	}],
            listeners: {
                beforeclose: function ( panel, eOpts) {
//                	if(protocolImportOverlayHandsontableHelper!=null){
//    					if(protocolImportOverlayHandsontableHelper.hot!=undefined){
//    						protocolImportOverlayHandsontableHelper.hot.destroy();
//    					}
//    					protocolImportOverlayHandsontableHelper=null;
//    				}
                	if(protocolImportErrorHandsontableHelper!=null){
    					if(protocolImportErrorHandsontableHelper.hot!=undefined){
    						protocolImportErrorHandsontableHelper.hot.destroy();
    					}
    					protocolImportErrorHandsontableHelper=null;
    				}
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function CreateProtocolImportOverlayTreeTable(result){
	var data=[];
	
	var protocolParentData={};
	protocolParentData.text='协议';
	protocolParentData.iconCls='protocol';
	protocolParentData.expanded=true;
	protocolParentData.children=[];
	
	var acqUnitParentData={};
	acqUnitParentData.text=loginUserLanguageResource.acqUnit;
	acqUnitParentData.iconCls='protocol';
	acqUnitParentData.expanded=true;
	acqUnitParentData.children=[];
	
	var displayUnitParentData={};
	displayUnitParentData.text=loginUserLanguageResource.displayUnit;
	displayUnitParentData.iconCls='protocol';
	displayUnitParentData.expanded=true;
	displayUnitParentData.children=[];
	
	var alarmUnitParentData={};
	alarmUnitParentData.text=loginUserLanguageResource.alarmUnit;
	alarmUnitParentData.iconCls='protocol';
	alarmUnitParentData.expanded=true;
	alarmUnitParentData.children=[];
	
	var acqInstanceParentData={};
	acqInstanceParentData.text=loginUserLanguageResource.acqInstance;
	acqInstanceParentData.iconCls='protocol';
	acqInstanceParentData.expanded=true;
	acqInstanceParentData.children=[];
	
	var displayInstanceParentData={};
	displayInstanceParentData.text=loginUserLanguageResource.displayInstance;
	displayInstanceParentData.iconCls='protocol';
	displayInstanceParentData.expanded=true;
	displayInstanceParentData.children=[];
	
	var alarmInstanceParentData={};
	alarmInstanceParentData.text=loginUserLanguageResource.alarmInstance;
	alarmInstanceParentData.iconCls='protocol';
	alarmInstanceParentData.expanded=true;
	alarmInstanceParentData.children=[];
	
	
	if(result.overlayList.length>0){
		for(var i=0;i<result.overlayList.length;i++){
			if(result.overlayList[i].classes==0){//协议
				var protocolData={};
				protocolData.text=result.overlayList[i].text;
				protocolData.iconCls='acqUnit';
				protocolData.expanded=true;
				protocolParentData.children.push(protocolData);
			}else if(result.overlayList[i].type==0){
				if(result.overlayList[i].classes==1){
					var acqUnitData={};
					var acqUnitId=result.overlayList[i].id;
//					acqUnitData.id=result.overlayList[i].id;
					acqUnitData.text=result.overlayList[i].text;
					acqUnitData.iconCls='acqUnit';
					acqUnitData.expanded=true;
					acqUnitData.children=[];
					for(var j=0;j<result.overlayList.length;j++){
						if(result.overlayList[j].type==0 && result.overlayList[j].classes==2 && result.overlayList[j].unitId==acqUnitId){
							var acqGroupData={};
//							acqGroupData.id=result.overlayList[j].id;
							acqGroupData.text=result.overlayList[j].text;
							acqGroupData.iconCls='acqGroup';
							acqGroupData.leaf=true;
							acqUnitData.children.push(acqGroupData);
						}
					}
					acqUnitParentData.children.push(acqUnitData);
				}
			}else{
				var otherData={};
//				otherData.id=result.overlayList[i].id;
				otherData.text=result.overlayList[i].text;
				otherData.iconCls='acqUnit';
				otherData.leaf=true;
				if(result.overlayList[i].type==1){
					displayUnitParentData.children.push(otherData);
				}else if(result.overlayList[i].type==2){
					alarmUnitParentData.children.push(otherData);
				}else if(result.overlayList[i].type==3){
					acqInstanceParentData.children.push(otherData);
				}else if(result.overlayList[i].type==4){
					displayInstanceParentData.children.push(otherData);
				}else if(result.overlayList[i].type==5){
					alarmInstanceParentData.children.push(otherData);
				}
			}
		}
	}
	if(protocolParentData.children.length>0){
		data.push(protocolParentData);
	}
	if(acqUnitParentData.children.length>0){
		data.push(acqUnitParentData);
	}
	if(displayUnitParentData.children.length>0){
		data.push(displayUnitParentData);
	}
	if(alarmUnitParentData.children.length>0){
		data.push(alarmUnitParentData);
	}
	if(acqInstanceParentData.children.length>0){
		data.push(acqInstanceParentData);
	}
	if(displayInstanceParentData.children.length>0){
		data.push(displayInstanceParentData);
	}
	if(alarmInstanceParentData.children.length>0){
		data.push(alarmInstanceParentData);
	}
	
	var store = Ext.create('Ext.data.TreeStore', {
	    root: {
	        expanded: true,
	        children: data
	    }
	});
	

	var treeGridPanel = Ext.create('Ext.tree.Panel', {
        id: "ImportProtocolContentOverlayTreeGridPanel_Id",
        border: false,
        animate: true,
        enableDD: false,
        useArrows: false,
        rootVisible: false,
        autoScroll: true,
        forceFit: true,
        viewConfig: {
            emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + loginUserLanguageResource.emptyMsg + "></div>",
            forceFit: true
        },
        store: store,
        columns: [{
        	xtype: 'treecolumn',
        	text: '冲突内容',
            flex: 8,
            align: 'left',
            dataIndex: 'text',
            renderer: function (value) {
                if (isNotVal(value)) {
                    return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                }
            }
        },{
            header: 'id',
            hidden: true,
            dataIndex: 'id'
        }],
        listeners: {
        	rowclick: function( grid, record, element, index, e, eOpts) {
        		
        	},
        	checkchange: function (node, checked) {
        		
            },
            selectionchange ( view, selected, eOpts ){
            	
            },
            select( v, record, index, eOpts ){
            	
            },
            beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {
            	
            },
            checkchange: function (node, checked) {
            	
            }
        }

    });
    var panel = Ext.getCmp("ProtocolImportOverlayTablePanel_Id");
    panel.add(treeGridPanel);

}


function CreateProtocolImportOverlayTable(result){
	var sourceDataObject={};
	var protocolOverlayData={};
	protocolOverlayData.category="协议";
	protocolOverlayData.text=null;
	protocolOverlayData.__children=[];
	
	var acqUnitOverlayData={};
	acqUnitOverlayData.category="采控单元或采控组";
	acqUnitOverlayData.text=null;
	acqUnitOverlayData.__children=[];
	
	var displayUnitOverlayData={};
	displayUnitOverlayData.category="显示单元";
	displayUnitOverlayData.text=null;
	displayUnitOverlayData.__children=[];
	
	var alarmUnitOverlayData={};
	alarmUnitOverlayData.category="报警单元";
	alarmUnitOverlayData.text=null;
	alarmUnitOverlayData.__children=[];
	
	var acqInstanceOverlayData={};
	acqInstanceOverlayData.category="采控实例";
	acqInstanceOverlayData.text=null;
	acqInstanceOverlayData.__children=[];
	
	var displayInstanceOverlayData={};
	displayInstanceOverlayData.category="显示实例";
	displayInstanceOverlayData.text=null;
	displayInstanceOverlayData.__children=[];
	
	var alarmInstanceOverlayData={};
	alarmInstanceOverlayData.category="报警实例";
	alarmInstanceOverlayData.text=null;
	alarmInstanceOverlayData.__children=[];
	
	sourceDataObject.data=[];
	if(result.overlayList.length>0){
		for(var i=0;i<result.overlayList.length;i++){
			var overlayData={};
			overlayData.text=result.overlayList[i].text;
			overlayData.classes=result.overlayList[i].classes;
			overlayData.type=result.overlayList[i].type;
			overlayData.id=result.overlayList[i].id;
			
			if(result.overlayList[i].classes==0){
				protocolOverlayData.__children.push(overlayData);
			}else{
				if(result.overlayList[i].type==0){//采控单元和采控组
					acqUnitOverlayData.__children.push(overlayData);
				}else if(result.overlayList[i].type==1){//显示单元
					displayUnitOverlayData.__children.push(overlayData);
				}else if(result.overlayList[i].type==2){//报警单元
					alarmUnitOverlayData.__children.push(overlayData);
				}else if(result.overlayList[i].type==3){//采控实例
					acqInstanceOverlayData.__children.push(overlayData);
				}else if(result.overlayList[i].type==4){//显示实例
					displayInstanceOverlayData.__children.push(overlayData);
				}else if(result.overlayList[i].type==5){//报警实例
					alarmInstanceOverlayData.__children.push(overlayData);
				}
			}
		}
	}
	if(protocolOverlayData.__children.length>0){
		sourceDataObject.data.push(protocolOverlayData);
	}
	if(acqUnitOverlayData.__children.length>0){
		sourceDataObject.data.push(acqUnitOverlayData);
	}
	if(displayUnitOverlayData.__children.length>0){
		sourceDataObject.data.push(displayUnitOverlayData);
	}
	if(alarmUnitOverlayData.__children.length>0){
		sourceDataObject.data.push(alarmUnitOverlayData);
	}
	if(acqInstanceOverlayData.__children.length>0){
		sourceDataObject.data.push(acqInstanceOverlayData);
	}
	if(displayInstanceOverlayData.__children.length>0){
		sourceDataObject.data.push(displayInstanceOverlayData);
	}
	if(alarmInstanceOverlayData.__children.length>0){
		sourceDataObject.data.push(alarmInstanceOverlayData);
	}
	
	if(protocolImportOverlayHandsontableHelper==null || protocolImportOverlayHandsontableHelper.hot==undefined){
		protocolImportOverlayHandsontableHelper = ProtocolImportOverlayHandsontableHelper.createNew("ProtocolImportOverlayTableDiv_Id");
		var colHeaders="['冲突类别','冲突内容','classes','type','id']";
		var columns="[" 
				+"{data:'category'}," 
				+"{data:'text'}," 
				+"{data:'classes'},"
				+"{data:'type'},"
				+"{data:'id'}"
				+"]";
		protocolImportOverlayHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
		protocolImportOverlayHandsontableHelper.columns=Ext.JSON.decode(columns);
		protocolImportOverlayHandsontableHelper.createTable(sourceDataObject.data);
	}else{
		protocolImportOverlayHandsontableHelper.hot.loadData(sourceDataObject.data);
	}
};

var ProtocolImportOverlayHandsontableHelper = {
		createNew: function (divid) {
	        var protocolImportOverlayHandsontableHelper = {};
	        protocolImportOverlayHandsontableHelper.divid = divid;
	        protocolImportOverlayHandsontableHelper.validresult=true;//数据校验
	        protocolImportOverlayHandsontableHelper.colHeaders=[];
	        protocolImportOverlayHandsontableHelper.columns=[];
	        
	        
	        
	        protocolImportOverlayHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = '#DC2828';   
	             td.style.color='#FFFFFF';
	        }
	        
	        protocolImportOverlayHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolImportOverlayHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	td.style.fontWeight = 'bold';
		        td.style.fontSize = '20px';
		        td.style.fontFamily = 'SimSun';
		        td.style.height = '40px';
	        }
	        
	        protocolImportOverlayHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolImportOverlayHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolImportOverlayHandsontableHelper.divid);
	        	protocolImportOverlayHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [2,3,4],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                columns:protocolImportOverlayHandsontableHelper.columns,
//	                
	                rowHeaders: true,//显示行头
	                colHeaders: protocolImportOverlayHandsontableHelper.colHeaders,
//	                colWidths: [1,1,1,1,1],
//	                
//	                
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                
	                
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                preventOverflow: 'horizontal',
	                colWidths: [1,1,1,1,1],
	                rowHeaders: true,
	                nestedRows: true,
	                contextMenu: true,
	                bindRowsWithHeaders: true,
//	                search: true,
	                renderAllRows: true,
	                columnSorting: false, //允许排序
	                allowInsertRow:false,
	                sortIndicator: true,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    return cellProperties;
	                }
	        	});
	        }
	        return protocolImportOverlayHandsontableHelper;
	    }
};

function CreateProtocolImportErrorTable(result){
	if(protocolImportErrorHandsontableHelper==null || protocolImportErrorHandsontableHelper.hot==undefined){
		protocolImportErrorHandsontableHelper = ProtocolImportErrorHandsontableHelper.createNew("ProtocolImportErrorTableDiv_Id");
		var colHeaders="['错误类别','错误内容','错误信息','classes','type','id']";
		var columns="[" 
				+"{data:'typeName'}," 
				+"{data:'text'}," 
				+"{data:'errorInfo'}," 
				+"{data:'classes'},"
				+"{data:'type'},"
				+"{data:'id'}"
				+"]";
		protocolImportErrorHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
		protocolImportErrorHandsontableHelper.columns=Ext.JSON.decode(columns);
		protocolImportErrorHandsontableHelper.createTable(result.errorDataList);
	}else{
		protocolImportErrorHandsontableHelper.hot.loadData(result.errorDataList);
	}
};

var ProtocolImportErrorHandsontableHelper = {
		createNew: function (divid) {
	        var protocolImportErrorHandsontableHelper = {};
	        protocolImportErrorHandsontableHelper.divid = divid;
	        protocolImportErrorHandsontableHelper.validresult=true;//数据校验
	        protocolImportErrorHandsontableHelper.colHeaders=[];
	        protocolImportErrorHandsontableHelper.columns=[];
	        
	        
	        
	        protocolImportErrorHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = '#DC2828';   
	             td.style.color='#FFFFFF';
	        }
	        
	        protocolImportErrorHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.color = '#ff0000';
	        }
	        
	        protocolImportErrorHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	td.style.fontWeight = 'bold';
		        td.style.fontSize = '20px';
		        td.style.fontFamily = 'SimSun';
		        td.style.height = '40px';
	        }
	        
	        protocolImportErrorHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolImportErrorHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolImportErrorHandsontableHelper.divid);
	        	protocolImportErrorHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [3,4,5],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                columns:protocolImportErrorHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                rowHeaders: true,//显示行头
	                colHeaders: protocolImportErrorHandsontableHelper.colHeaders,
	                colWidths: [1,1,2,1,1,1],
	                columnSorting: true, //允许排序
	                allowInsertRow:false,
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    if(prop.toUpperCase()=='errorInfo'.toUpperCase()){
	                    	cellProperties.renderer = protocolImportErrorHandsontableHelper.addBoldBg;
	                    }
	                    return cellProperties;
	                }
	        	});
	        }
	        return protocolImportErrorHandsontableHelper;
	    }
};