var reservoirPropertyHandsontableHelper=null;
Ext.define('AP.view.reservoirProperty.ReservoirPropertyInfoGridPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.reservoirPropertyInfoGridPanel',
    layout: "fit",
    border: false,
    id: "ReservoirPropertyInfoGridPanelView_Id",
    initComponent: function () {
    	
    	var resStore = new Ext.data.JsonStore({
            pageSize:defaultJhComboxSize,
            fields: [{
                name: 'boxkey',
                type: 'string'
                }, {
                name: 'boxval',
                type: 'string'
                }],
            proxy: {
                url: context + '/reservoirPropertyManagerController/getReservoirListData',
                type: 'ajax',
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
                    var reservoirPropertyName = Ext.getCmp('reservoirPropertyName_Id').getValue();
                    var new_params = {
                    		reservoirPropertyName:reservoirPropertyName
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
    	var resCombo = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: cosog.string.resName,
            id: "reservoirPropertyName_Id",
            labelWidth: 60,
            width:170,
            queryMode: 'remote',
            typeAhead: true,
            store: resStore,
            autoSelect: false,
            editable: true,
            triggerAction: 'all',
            displayField: "boxval",
            emptyText: cosog.string.all,
            blankText: cosog.string.all,
            valueField: "boxkey",
            pageSize:comboxPagingStatus,
            minChars:0,
            multiSelect:false, 
            listeners: {
                expand: function (sm, selections) {
//                	resCombo.clearValue();
//                	resCombo.getStore().loadPage(1); // 加载井下拉框的store
                },
                specialkey: function (field, e) {
                    onEnterKeyDownFN(field, e, 'MonitorPumpingUnit_Id');
                },
                select: function (combo, record, index) {
                	CreateAndLoadReservoirPropertyTable();
                }
            }
        });
        Ext.apply(this, {
            tbar: [resCombo, 
            	{
                xtype: 'button',
                text: cosog.string.search,
                hidden: true,
                pressed: true,
                text_align: 'center',
                iconCls: 'search',
                handler: function () {
                	Ext.getCmp('reservoirPropertyName_Id').setValue("");
                	Ext.getCmp('reservoirPropertyName_Id').setRawValue("");
                	CreateAndLoadReservoirPropertyTable();
                }
            }, '->',{
    			xtype: 'button',
    			text: cosog.string.exportExcel,
                pressed: true,
    			hidden:false,
    			handler: function (v, o) {
    				var fields = "";
    			    var heads = "";
    			    var reservoirPropertyName = Ext.getCmp('reservoirPropertyName_Id').getValue();
    				var url=context + '/reservoirPropertyManagerController/exportReservoirPropertyData';
    				for(var i=0;i<reservoirPropertyHandsontableHelper.colHeaders.length;i++){
    					fields+=reservoirPropertyHandsontableHelper.columns[i].data+",";
    					heads+=reservoirPropertyHandsontableHelper.colHeaders[i]+","
    				}
    				if (isNotVal(fields)) {
    			        fields = fields.substring(0, fields.length - 1);
    			        heads = heads.substring(0, heads.length - 1);
    			    }
    				
    			    var param = "&fields=" + fields +"&heads=" + URLencode(URLencode(heads)) + "&reservoirPropertyName=" + URLencode(URLencode(reservoirPropertyName)) +"&recordCount=10000"+ "&fileName="+URLencode(URLencode("区块数据"))+ "&title="+URLencode(URLencode("区块数据"));
    			    openExcelWindow(url + '?flag=true' + param);
    			}
    		},'-',{
            	xtype: 'button',
            	itemId: 'saveReservoirPropertyGridDataBtnId',
            	id: 'saveReservoirPropertyGridDataBtnId_Id',
            	disabled: false,
            	hidden:false,
            	pressed: true,
            	text: cosog.string.save,
            	iconCls: 'save',
            	handler: function (v, o) {
            		reservoirPropertyHandsontableHelper.saveData();
    			}
            }],
            html:'<div class="ReservoirPropertyContainer" style="width:100%;height:100%;"><div class="con" id="ReservoirPropertyDiv_id"></div></div>',
            listeners: {
                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                	if(reservoirPropertyHandsontableHelper!=null){
                		CreateAndLoadReservoirPropertyTable();
                	}
                }
            }
        });

        this.callParent(arguments);

    }
});

function CreateAndLoadReservoirPropertyTable(isNew){
	if(isNew&&reservoirPropertyHandsontableHelper!=null){
		reservoirPropertyHandsontableHelper.clearContainer();
		reservoirPropertyHandsontableHelper.hot.destroy();
		reservoirPropertyHandsontableHelper=null;
	}
	var reservoirPropertyName = Ext.getCmp('reservoirPropertyName_Id').getValue();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reservoirPropertyManagerController/doReservoirPropertyShow',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(reservoirPropertyHandsontableHelper==null){
				reservoirPropertyHandsontableHelper = ReservoirPropertyHandsontableHelper.createNew("ReservoirPropertyDiv_id");
	            var colHeaders="[";
		        var columns="[";
	            for(var i=0;i<result.columns.length;i++){
	            	colHeaders+="'"+result.columns[i].header+"'";
	            	columns+="{data:'"+result.columns[i].dataIndex+"'}";
	            	if(i<result.columns.length-1){
	            		colHeaders+=",";
	                	columns+=",";
	            	}
	            }
	            colHeaders+="]";
	        	columns+="]";
	        	reservoirPropertyHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
	        	reservoirPropertyHandsontableHelper.columns=Ext.JSON.decode(columns);
				reservoirPropertyHandsontableHelper.createTable(result.totalRoot);
			}else{
				reservoirPropertyHandsontableHelper.hot.loadData(result.totalRoot);
			}
			
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			reservoirPropertyName: reservoirPropertyName,
            recordCount:50,
            page:1,
            limit:10000
        }
	});
};

var ReservoirPropertyHandsontableHelper = {
	    createNew: function (divid) {
	        var reservoirPropertyHandsontableHelper = {};
	        reservoirPropertyHandsontableHelper.hot = '';
	        reservoirPropertyHandsontableHelper.divid = divid;
	        reservoirPropertyHandsontableHelper.validresult=true;//数据校验
	        reservoirPropertyHandsontableHelper.colHeaders=[];
	        reservoirPropertyHandsontableHelper.columns=[];
	        
	        reservoirPropertyHandsontableHelper.AllData={};
	        reservoirPropertyHandsontableHelper.updatelist=[];
	        reservoirPropertyHandsontableHelper.delidslist=[];
	        reservoirPropertyHandsontableHelper.insertlist=[];
	        
	        reservoirPropertyHandsontableHelper.createTable = function (data) {
	        	$('#'+reservoirPropertyHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+reservoirPropertyHandsontableHelper.divid);
	        	reservoirPropertyHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: true
	                },
	                columns:reservoirPropertyHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:reservoirPropertyHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                contextMenu: {
	                	items: {
	                	    "row_above": {
	                	      name: '向上插入一行',
	                	    },
	                	    "row_below": {
	                	      name: '向下插入一行',
	                	    },
	                	    "col_left": {
	                	      name: '向左插入一列',
	                	    },
	                	    "col_right": {
	                	      name: '向右插入一列',
	                	    },
	                	    "remove_row": {
	                	      name: '删除行',
	                	    },
	                	    "remove_col": {
	                	      name: '删除列',
	                	    },
	                	    "merge_cell": {
	                	      name: '合并单元格',
	                	    },
	                	    "copy": {
	                	      name: '复制',
	                	    },
	                	    "cut": {
	                	      name: '剪切',
	                	    },
	                	    "paste": {
	                	      name: '粘贴',
	                	      disabled: function() {
//	                	        return self.clipboardCache.length === 0;
	                	      },
	                	      callback: function() {
//	                	        var plugin = this.getPlugin('copyPaste');
//	                	        this.listen();
//	                	        plugin.paste(self.clipboardCache);
	                	      }
	                	    }
	                	}
	                },//右键菜单展示
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
//	                dropdownMenu: ['filter_by_condition', 'filter_by_value', 'filter_action_bar'],
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                afterDestroy: function() {
	                    // 移除事件
//	                    Handsontable.Dom.removeEvent(save, 'click', saveData);
//	                    loadDataTable();
	                },
	                beforeRemoveRow: function (index, amount) {
	                    var ids = [];
	                    //封装id成array传入后台
	                    if (amount != 0) {
	                        for (var i = index; i < amount + index; i++) {
	                            var rowdata = reservoirPropertyHandsontableHelper.hot.getDataAtRow(i);
	                            ids.push(rowdata[0]);
	                        }
	                        reservoirPropertyHandsontableHelper.delExpressCount(ids);
	                        reservoirPropertyHandsontableHelper.screening();
	                    }
	                },
	                afterChange: function (changes, source) {
	                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
	                    
	                    if (changes != null) {
	                    	for(var i=0;i<changes.length;i++){
	                    		var params = [];
	                    		var index = changes[i][0]; //行号码
		                        var rowdata = reservoirPropertyHandsontableHelper.hot.getDataAtRow(index);
		                        params.push(rowdata[i]);
		                        params.push(changes[i][1]);
		                        params.push(changes[i][2]);
		                        params.push(changes[i][3]);

		                        //仅当单元格发生改变的时候,id!=null,说明是更新
		                        if (params[2] != params[3] && params[0] != null && params[0] >0) {
		                        	var data="{";
		                        	for(var j=0;j<reservoirPropertyHandsontableHelper.columns.length;j++){
		                        		data+=reservoirPropertyHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
		                        		if(j<reservoirPropertyHandsontableHelper.columns.length-1){
		                        			data+=","
		                        		}
		                        	}
		                        	data+="}"
		                            reservoirPropertyHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
		                        }
	                    	}
	                        
	                    }
	                }
	        	});
	        }
	      //插入的数据的获取
	        reservoirPropertyHandsontableHelper.insertExpressCount=function() {
	            var idsdata = reservoirPropertyHandsontableHelper.hot.getDataAtCol(0); //所有的id
	            for (var i = 0; i < idsdata.length; i++) {
	                //id=null时,是插入数据,此时的i正好是行号
	                if (idsdata[i] == null||idsdata[i]<0) {
	                    //获得id=null时的所有数据封装进data
	                    var rowdata = reservoirPropertyHandsontableHelper.hot.getDataAtRow(i);
	                    //var collength = hot.countCols();
	                    if (rowdata != null&&rowdata[1]!=null) {
	                    	var data="{";
                        	for(var j=0;j<reservoirPropertyHandsontableHelper.columns.length;j++){
                        		data+=reservoirPropertyHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
                        		if(j<reservoirPropertyHandsontableHelper.columns.length-1){
                        			data+=","
                        		}
                        	}
                        	data+="}"
	                        reservoirPropertyHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
	                    }
	                }
	            }
	            if (reservoirPropertyHandsontableHelper.insertlist.length != 0) {
	            	reservoirPropertyHandsontableHelper.AllData.insertlist = reservoirPropertyHandsontableHelper.insertlist;
	            }
	        }
	        //保存数据
	        reservoirPropertyHandsontableHelper.saveData = function () {
	            //插入的数据的获取
	        	reservoirPropertyHandsontableHelper.insertExpressCount();
	            if (JSON.stringify(reservoirPropertyHandsontableHelper.AllData) != "{}" && reservoirPropertyHandsontableHelper.validresult) {
	            	Ext.Ajax.request({
	            		method:'POST',
	            		url:context + '/reservoirPropertyManagerController/saveReservoirPropertyHandsontableData',
	            		success:function(response) {
	            			rdata=Ext.JSON.decode(response.responseText);
	            			if (rdata.success) {
	                        	Ext.MessageBox.alert("信息","保存成功");
	                            //保存以后重置全局容器
	                            reservoirPropertyHandsontableHelper.clearContainer();
	                            //销毁
//	                            reservoirPropertyHandsontableHelper.hot.destroy();
	                            CreateAndLoadReservoirPropertyTable();
	                        } else {
	                        	Ext.MessageBox.alert("信息","数据保存失败");

	                        }
	            		},
	            		failure:function(){
	            			Ext.MessageBox.alert("信息","请求失败");
	                        reservoirPropertyHandsontableHelper.clearContainer();
	            		},
	            		params: {
	                    	data: JSON.stringify(reservoirPropertyHandsontableHelper.AllData)
	                    }
	            	}); 
	            } else {
	                if (!reservoirPropertyHandsontableHelper.validresult) {
	                	Ext.MessageBox.alert("信息","数据类型错误");
	                } else {
	                	Ext.MessageBox.alert("信息","无数据变化");
	                }
	            }
	        }
	      //删除的优先级最高
	        reservoirPropertyHandsontableHelper.delExpressCount=function(ids) {
	            //传入的ids.length不可能为0
	            $.each(ids, function (index, id) {
	                if (id != null) {
	                	reservoirPropertyHandsontableHelper.delidslist.push(id);
	                }
	            });
	            reservoirPropertyHandsontableHelper.AllData.delidslist = reservoirPropertyHandsontableHelper.delidslist;
	        }

	        //updatelist数据更新
	        reservoirPropertyHandsontableHelper.screening=function() {
	            if (reservoirPropertyHandsontableHelper.updatelist.length != 0 && reservoirPropertyHandsontableHelper.delidslist.lentgh != 0) {
	                for (var i = 0; i < reservoirPropertyHandsontableHelper.delidslist.length; i++) {
	                    for (var j = 0; j < reservoirPropertyHandsontableHelper.updatelist.length; j++) {
	                        if (reservoirPropertyHandsontableHelper.updatelist[j].id == reservoirPropertyHandsontableHelper.delidslist[i]) {
	                            //更新updatelist
	                        	reservoirPropertyHandsontableHelper.updatelist.splice(j, 1);
	                        }
	                    }
	                }
	                //把updatelist封装进AllData
	                reservoirPropertyHandsontableHelper.AllData.updatelist = reservoirPropertyHandsontableHelper.updatelist;
	            }
	        }
	        
	      //更新数据
	        reservoirPropertyHandsontableHelper.updateExpressCount=function(data) {
	            if (JSON.stringify(data) != "{}") {
	                var flag = true;
	                //判断记录是否存在,更新数据     
	                $.each(reservoirPropertyHandsontableHelper.updatelist, function (index, node) {
	                    if (node.id == data.id&&node.jlbh == data.jlbh) {
	                        //此记录已经有了
	                        flag = false;
	                        //用新得到的记录替换原来的,不用新增
	                        reservoirPropertyHandsontableHelper.updatelist[index] = data;
	                    }
	                });
	                flag && reservoirPropertyHandsontableHelper.updatelist.push(data);
	                //封装
	                reservoirPropertyHandsontableHelper.AllData.updatelist = reservoirPropertyHandsontableHelper.updatelist;
	            }
	        }
	        
	        reservoirPropertyHandsontableHelper.clearContainer = function () {
	        	reservoirPropertyHandsontableHelper.AllData = {};
	        	reservoirPropertyHandsontableHelper.updatelist = [];
	        	reservoirPropertyHandsontableHelper.delidslist = [];
	        	reservoirPropertyHandsontableHelper.insertlist = [];
	        }
	        
	        return reservoirPropertyHandsontableHelper;
	    }
};