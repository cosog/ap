var protocolImportOverlayHandsontableHelper=null;
Ext.define("AP.view.acquisitionUnit.PrototolImportOverlayDataWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.prototolImportOverlayDataWindow',
    id: 'PrototolImportOverlayDataWindow_Id',
    layout: 'fit',
    title:'覆盖内容',
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
    height: 400,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    padding:0,
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            loyout:'border',
            tbar:[{
                xtype: 'label',
                margin: '0 0 0 0',
                html: '<font color=red>以下导入内容已存在，继续保存将覆盖已有记录</font>'
            },'->',{
                xtype: 'button',
                text: '继续保存',
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
                            		Ext.MessageBox.alert("信息", "协议及关联内容导入成功！");
                            		Ext.getCmp("PrototolImportOverlayDataWindow_Id").close();
                            	}else{
                            		Ext.MessageBox.alert("信息", "协议及关联内容导入失败！");
                            	}
                            },
                            failure: function () {
                                Ext.MessageBox.alert("信息", "请求失败");
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
        	 	text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                	Ext.getCmp("PrototolImportOverlayDataWindow_Id").close();
                }
         }],
        	items:[{
        		region: 'center',
        		layout: 'fit',
        		padding:0,
//                autoScroll: true,
        		html: '<div id="ProtocolImportOverlayTableDiv_Id" style="width:100%;height:100%;margin:0 0 0 0;"></div>',
        		listeners: {
        			resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(protocolImportOverlayHandsontableHelper!=null&&protocolImportOverlayHandsontableHelper.hot!=null&&protocolImportOverlayHandsontableHelper.hot!=undefined){
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		protocolImportOverlayHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}else{
//                  			CreateProtocolImportOverlayTable();
                    	}
                    }
        		}
        	}],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	if(protocolImportOverlayHandsontableHelper!=null){
    					if(protocolImportOverlayHandsontableHelper.hot!=undefined){
    						protocolImportOverlayHandsontableHelper.hot.destroy();
    					}
    					protocolImportOverlayHandsontableHelper=null;
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


function CreateProtocolImportOverlayTable(result){
	if(protocolImportOverlayHandsontableHelper==null || protocolImportOverlayHandsontableHelper.hot==undefined){
		protocolImportOverlayHandsontableHelper = ProtocolImportOverlayHandsontableHelper.createNew("ProtocolImportOverlayTableDiv_Id");
		var colHeaders="['覆盖内容','名称','classes','type','id']";
		var columns="[" 
				+"{data:'typeName'}," 
				+"{data:'text'}," 
				+"{data:'classes'},"
				+"{data:'type'},"
				+"{data:'id'}"
				+"]";
		protocolImportOverlayHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
		protocolImportOverlayHandsontableHelper.columns=Ext.JSON.decode(columns);
		protocolImportOverlayHandsontableHelper.createTable(result.overlayList);
	}else{
		protocolImportOverlayHandsontableHelper.hot.loadData(result.overlayList);
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
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                rowHeaders: true,//显示行头
	                colHeaders: protocolImportOverlayHandsontableHelper.colHeaders,
	                colWidths: [1,1,1,1,1],
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
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {}
	        	});
	        }
	        return protocolImportOverlayHandsontableHelper;
	    }
};