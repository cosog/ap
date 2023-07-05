var videoKeyDataHandsontableHelper=null;
Ext.define("AP.view.well.VideoKeyInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.videoKeyInfoWindow',
    layout: 'fit',
    title:'视频密钥',
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 800,
    minWidth: 800,
    height: 600,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	tbar:[{
                id: 'VideoKeyWindowOrgId_Id',
                xtype: 'textfield',
                value: '0',
                hidden: true
            },{
                id: 'VideoKeySelectRow_Id',
                xtype: 'textfield',
                value: 0,
                hidden: true
            },{
                id: 'VideoKeySelectEndRow_Id',
                xtype: 'textfield',
                value: 0,
                hidden: true
            },'->', {
    			xtype: 'button',
                text: '添加密钥',
                iconCls: 'add',
                handler: function (v, o) {
                	var selectedOrgName="";
                	var selectedOrgId="";
                	var IframeViewStore = Ext.getCmp("IframeView_Id").getStore();
            		var count=IframeViewStore.getCount();
                	var IframeViewSelection = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
                	if (IframeViewSelection.length > 0) {
                		selectedOrgName=foreachAndSearchOrgAbsolutePath(IframeViewStore.data.items,IframeViewSelection[0].data.orgId);
                		selectedOrgId=IframeViewSelection[0].data.orgId;
                		
                	} else {
                		if(count>0){
                			selectedOrgName=IframeViewStore.getAt(0).data.text;
                			selectedOrgId=IframeViewStore.getAt(0).data.orgId;
                		}
                	}
                	
                	var window = Ext.create("AP.view.well.VideoKeyAddWindow", {
                        title: '添加视频密钥'
                    });
                    window.show();
                    Ext.getCmp("videoKeyWinOrgLabel_Id").setHtml("密钥将添加到【<font color=red>"+selectedOrgName+"</font>】下,请确认<br/>&nbsp;");
                    Ext.getCmp("videoKeyOrg_Id").setValue(selectedOrgId);
                    return false;
    			}
    		}, '-',{
    			xtype: 'button',
    			text: '删除密钥',
    			iconCls: 'delete',
    			handler: function (v, o) {
    				var startRow= Ext.getCmp("VideoKeySelectRow_Id").getValue();
    				var endRow= Ext.getCmp("VideoKeySelectEndRow_Id").getValue();
    				var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
    				if(startRow!='' && endRow!=''){
    					startRow=parseInt(startRow);
    					endRow=parseInt(endRow);
    					var deleteInfo='是否删除第'+(startRow+1)+"行~第"+(endRow+1)+"行数据";
    					if(startRow==endRow){
    						deleteInfo='是否删除第'+(startRow+1)+"行数据";
    					}
    					
    					Ext.Msg.confirm(cosog.string.yesdel, deleteInfo, function (btn) {
    			            if (btn == "yes") {
    			            	for(var i=startRow;i<=endRow;i++){
    	    						var rowdata = videoKeyDataHandsontableHelper.hot.getDataAtRow(i);
    	    						if (rowdata[0] != null && parseInt(rowdata[0])>0) {
    	    		                    videoKeyDataHandsontableHelper.delidslist.push(rowdata[0]);
    	    		                }
    	    					}
    	    					var saveData={};
    	    	            	saveData.updatelist=[];
    	    	            	saveData.insertlist=[];
    	    	            	saveData.delidslist=videoKeyDataHandsontableHelper.delidslist;
    	    	            	Ext.Ajax.request({
    	    	                    method: 'POST',
    	    	                    url: context + '/wellInformationManagerController/saveVideoKeyHandsontableData',
    	    	                    success: function (response) {
    	    	                        rdata = Ext.JSON.decode(response.responseText);
    	    	                        if (rdata.success) {
    	    	                        	Ext.MessageBox.alert("信息", "删除成功");
    	    	                            //保存以后重置全局容器
    	    	                            videoKeyDataHandsontableHelper.clearContainer();
    	    	                            Ext.getCmp("VideoKeySelectRow_Id").setValue(0);
    	    	                        	Ext.getCmp("VideoKeySelectEndRow_Id").setValue(0);
    	    	                            CreateDeviceKeyDataTable();
    	    	                        } else {
    	    	                            Ext.MessageBox.alert("信息", "数据保存失败");
    	    	                        }
    	    	                    },
    	    	                    failure: function () {
    	    	                        Ext.MessageBox.alert("信息", "请求失败");
    	    	                        videoKeyDataHandsontableHelper.clearContainer();
    	    	                    },
    	    	                    params: {
    	    	                        data: JSON.stringify(saveData)
    	    	                    }
    	    	                });
    			            }
    			        });
    				}else{
    					Ext.MessageBox.alert("信息","请先选中要删除的行");
    				}
    			}
    		},"-", {
                xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                handler: function (v, o) {
                	videoKeyDataHandsontableHelper.saveData();
                }
            }],
            id:'VideoKeyPanel_Id',
        	html: '<div id="VideoKeyDiv_Id" style="width:100%;height:100%;"></div>',
            listeners: {
                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                	if(videoKeyDataHandsontableHelper!=null&&videoKeyDataHandsontableHelper.hot!=null&&videoKeyDataHandsontableHelper.hot!=undefined){
                		var newWidth=width;
                		var newHeight=height;
                		var header=thisPanel.getHeader();
                		if(header){
                			newHeight=newHeight-header.lastBox.height-2;
                		}
                		videoKeyDataHandsontableHelper.hot.updateSettings({
                			width:newWidth,
                			height:newHeight
                		});
                	}else{
              			CreateDeviceKeyDataTable();
                	}
                },
                beforeclose: function ( panel, eOpts) {
                	if(videoKeyDataHandsontableHelper!=null){
    					if(videoKeyDataHandsontableHelper.hot!=undefined){
    						videoKeyDataHandsontableHelper.hot.destroy();
    					}
    					videoKeyDataHandsontableHelper=null;
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


function CreateDeviceKeyDataTable(){
	Ext.getCmp("VideoKeyPanel_Id").el.mask(cosog.string.loading).show();
	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/wellInformationManagerController/getVideoKeyData',
		success:function(response) {
			Ext.getCmp("VideoKeyPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(videoKeyDataHandsontableHelper==null || videoKeyDataHandsontableHelper.hot==undefined){
				videoKeyDataHandsontableHelper = VideoKeyDataHandsontableHelper.createNew("VideoKeyDiv_Id");
				var colHeaders="['序号','名称','appKey','secret']";
				var columns="[" 
					+"{data:'id'}," 	
					+"{data:'account'}," 
					+"{data:'appKey'}," 
					+"{data:'secret'}"
					+"]";
				videoKeyDataHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				videoKeyDataHandsontableHelper.columns=Ext.JSON.decode(columns);
				videoKeyDataHandsontableHelper.CellInfo=result.CellInfo;
				if(result.totalRoot.length==0){
					videoKeyDataHandsontableHelper.hiddenRows = [0];
					videoKeyDataHandsontableHelper.createTable([{}]);
				}else{
					videoKeyDataHandsontableHelper.hiddenRows = [];
					videoKeyDataHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
            		videoKeyDataHandsontableHelper.hiddenRows = [0];
            		videoKeyDataHandsontableHelper.hot.loadData([{}]);
            	}else{
            		videoKeyDataHandsontableHelper.hiddenRows = [];
            		videoKeyDataHandsontableHelper.hot.loadData(result.totalRoot);
            	}
			}
			
			if(videoKeyDataHandsontableHelper.hiddenRows.length>0){
            	const plugin = videoKeyDataHandsontableHelper.hot.getPlugin('hiddenRows');
            	plugin.hideRows(videoKeyDataHandsontableHelper.hiddenRows);
            	videoKeyDataHandsontableHelper.hot.render();
            }
		},
		failure:function(){
			Ext.getCmp("VideoKeyPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId: leftOrg_Id
        }
	});
};

var VideoKeyDataHandsontableHelper = {
		createNew: function (divid) {
	        var videoKeyDataHandsontableHelper = {};
	        videoKeyDataHandsontableHelper.divid = divid;
	        videoKeyDataHandsontableHelper.validresult=true;//数据校验
	        videoKeyDataHandsontableHelper.colHeaders=[];
	        videoKeyDataHandsontableHelper.columns=[];
	        videoKeyDataHandsontableHelper.hiddenRows = [];
	        
	        videoKeyDataHandsontableHelper.AllData = {};
	        videoKeyDataHandsontableHelper.updatelist = [];
	        videoKeyDataHandsontableHelper.delidslist = [];
	        videoKeyDataHandsontableHelper.insertlist = [];
	        videoKeyDataHandsontableHelper.createTable = function (data) {
	        	$('#'+videoKeyDataHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+videoKeyDataHandsontableHelper.divid);
	        	videoKeyDataHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [0],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                hiddenRows: {
	                    rows: [],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	        		colWidths: [1,1,5,5],
	        		columns: videoKeyDataHandsontableHelper.columns,
	                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
//	                autoWrapRow: true,
	                rowHeaders: true, //显示行头
	                colHeaders: videoKeyDataHandsontableHelper.colHeaders, //显示列头
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
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	if(row<0 && row2<0){//只选中表头
	                		Ext.getCmp("VideoKeySelectRow_Id").setValue('');
	                    	Ext.getCmp("VideoKeySelectEndRow_Id").setValue('');
	                    	CreateAndLoadRPCPumoingModelInfoTable(0,'');
	                    	CreateAndLoadRPCProductionDataTable(0,'');
	                    	CreateAndLoadRPCVideoInfoTable(0,'');
	                	}else{
	                		if(row<0){
	                    		row=0;
	                    	}
	                    	if(row2<0){
	                    		row2=0;
	                    	}
	                    	var startRow=row;
	                    	var endRow=row2;
	                    	if(row>row2){
	                    		startRow=row2;
	                        	endRow=row;
	                    	}
	                    	
	                    	var selectedRow=Ext.getCmp("VideoKeySelectRow_Id").getValue();
	                    	if(selectedRow!=startRow){
	                    		var row1=videoKeyDataHandsontableHelper.hot.getDataAtRow(startRow);
	                        	var recordId=0;
	                        	var deviceName='';
	                        	if(isNotVal(row1[0])){
	                        		recordId=row1[0];
	                        	}
	                        	if(isNotVal(row1[1])){
	                        		deviceName=row1[1];
	                        	}
	                        	
	                        	CreateAndLoadRPCPumoingModelInfoTable(recordId,deviceName);
	                        	CreateAndLoadRPCProductionDataTable(recordId,deviceName);
	                        	CreateAndLoadRPCVideoInfoTable(recordId,deviceName);
	                        	
	                        	Ext.getCmp("selectedRPCDeviceId_global").setValue(recordId);
	                    	}
	                    	Ext.getCmp("VideoKeySelectRow_Id").setValue(startRow);
	                    	Ext.getCmp("VideoKeySelectEndRow_Id").setValue(endRow);
	                	}
	                },
	                afterDestroy: function () {
	                },
	                beforeRemoveRow: function (index, amount) {
	                    var ids = [];
	                    //封装id成array传入后台
	                    if (amount != 0) {
	                        for (var i = index; i < amount + index; i++) {
	                            var rowdata = videoKeyDataHandsontableHelper.hot.getDataAtRow(i);
	                            ids.push(rowdata[0]);
	                        }
	                        videoKeyDataHandsontableHelper.delExpressCount(ids);
	                        videoKeyDataHandsontableHelper.screening();
	                    }
	                },
	                afterChange: function (changes, source) {
	                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
	                    if (changes != null) {
	                        for (var i = 0; i < changes.length; i++) {
	                            var params = [];
	                            var index = changes[i][0]; //行号码
	                            var rowdata = videoKeyDataHandsontableHelper.hot.getDataAtRow(index);
	                            params.push(rowdata[0]);
	                            params.push(changes[i][1]);
	                            params.push(changes[i][2]);
	                            params.push(changes[i][3]);

	                            //仅当单元格发生改变的时候,id!=null,说明是更新
	                            if (params[2] != params[3] && params[0] != null && params[0] > 0) {
	                                var data = "{";
	                                for (var j = 0; j < videoKeyDataHandsontableHelper.columns.length; j++) {
	                                    data += videoKeyDataHandsontableHelper.columns[j].data + ":'" + rowdata[j] + "'";
	                                    if (j < videoKeyDataHandsontableHelper.columns.length - 1) {
	                                        data += ","
	                                    }
	                                }
	                                data += "}"
	                                videoKeyDataHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
	                            }
	                        }
	                    }
	                }
	        	});
	        }
	      //插入的数据的获取
	        videoKeyDataHandsontableHelper.insertExpressCount = function () {
	            var idsdata = videoKeyDataHandsontableHelper.hot.getDataAtCol(0); //所有的id
	            for (var i = 0; i < idsdata.length; i++) {
	                //id=null时,是插入数据,此时的i正好是行号
	                if (idsdata[i] == null || idsdata[i] < 0) {
	                    //获得id=null时的所有数据封装进data
	                    var rowdata = videoKeyDataHandsontableHelper.hot.getDataAtRow(i);
	                    //var collength = hot.countCols();
	                    if (rowdata != null) {
	                        var data = "{";
	                        for (var j = 0; j < videoKeyDataHandsontableHelper.columns.length; j++) {
	                            data += videoKeyDataHandsontableHelper.columns[j].data + ":'" + rowdata[j] + "'";
	                            if (j < videoKeyDataHandsontableHelper.columns.length - 1) {
	                                data += ","
	                            }
	                        }
	                        data += "}"
	                        videoKeyDataHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
	                    }
	                }
	            }
	            if (videoKeyDataHandsontableHelper.insertlist.length != 0) {
	                videoKeyDataHandsontableHelper.AllData.insertlist = videoKeyDataHandsontableHelper.insertlist;
	            }
	        }
	        //保存数据
	        videoKeyDataHandsontableHelper.saveData = function () {
	        	var rpcDeviceInfoHandsontableData=videoKeyDataHandsontableHelper.hot.getData();
	        	if(rpcDeviceInfoHandsontableData.length>0){
	            	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
	                //插入的数据的获取
	                videoKeyDataHandsontableHelper.insertExpressCount();
	            	Ext.Ajax.request({
	                    method: 'POST',
	                    url: context + '/wellInformationManagerController/saveVideoKeyHandsontableData',
	                    success: function (response) {
	                        rdata = Ext.JSON.decode(response.responseText);
	                        if (rdata.success) {
	                        	var saveInfo='保存成功';
	                        	Ext.MessageBox.alert("信息", saveInfo);
	                        	videoKeyDataHandsontableHelper.clearContainer();
	                        	CreateDeviceKeyDataTable();
	                        } else {
	                            Ext.MessageBox.alert("信息", "数据保存失败");
	                        }
	                    },
	                    failure: function () {
	                        Ext.MessageBox.alert("信息", "请求失败");
	                        videoKeyDataHandsontableHelper.clearContainer();
	                    },
	                    params: {
	                    	data: JSON.stringify(videoKeyDataHandsontableHelper.AllData),
	                        orgId: leftOrg_Id
	                    }
	                });
	        	}else{
	        		Ext.MessageBox.alert("信息", "无记录保存！");
	        	}
	        }


	        //删除的优先级最高
	        videoKeyDataHandsontableHelper.delExpressCount = function (ids) {
	            //传入的ids.length不可能为0
	            $.each(ids, function (index, id) {
	                if (id != null) {
	                    videoKeyDataHandsontableHelper.delidslist.push(id);
	                }
	            });
	            videoKeyDataHandsontableHelper.AllData.delidslist = videoKeyDataHandsontableHelper.delidslist;
	        }

	        //updatelist数据更新
	        videoKeyDataHandsontableHelper.screening = function () {
	            if (videoKeyDataHandsontableHelper.updatelist.length != 0 && videoKeyDataHandsontableHelper.delidslist.lentgh != 0) {
	                for (var i = 0; i < videoKeyDataHandsontableHelper.delidslist.length; i++) {
	                    for (var j = 0; j < videoKeyDataHandsontableHelper.updatelist.length; j++) {
	                        if (videoKeyDataHandsontableHelper.updatelist[j].id == videoKeyDataHandsontableHelper.delidslist[i]) {
	                            //更新updatelist
	                            videoKeyDataHandsontableHelper.updatelist.splice(j, 1);
	                        }
	                    }
	                }
	                //把updatelist封装进AllData
	                videoKeyDataHandsontableHelper.AllData.updatelist = videoKeyDataHandsontableHelper.updatelist;
	            }
	        }

	        //更新数据
	        videoKeyDataHandsontableHelper.updateExpressCount = function (data) {
	            if (JSON.stringify(data) != "{}") {
	                var flag = true;
	                //判断记录是否存在,更新数据     
	                $.each(videoKeyDataHandsontableHelper.updatelist, function (index, node) {
	                    if (node.id == data.id) {
	                        //此记录已经有了
	                        flag = false;
	                        //用新得到的记录替换原来的,不用新增
	                        videoKeyDataHandsontableHelper.updatelist[index] = data;
	                    }
	                });
	                flag && videoKeyDataHandsontableHelper.updatelist.push(data);
	                //封装
	                videoKeyDataHandsontableHelper.AllData.updatelist = videoKeyDataHandsontableHelper.updatelist;
	            }
	        }

	        videoKeyDataHandsontableHelper.clearContainer = function () {
	            videoKeyDataHandsontableHelper.AllData = {};
	            videoKeyDataHandsontableHelper.updatelist = [];
	            videoKeyDataHandsontableHelper.delidslist = [];
	            videoKeyDataHandsontableHelper.insertlist = [];
	        }
	        return videoKeyDataHandsontableHelper;
	    }
};