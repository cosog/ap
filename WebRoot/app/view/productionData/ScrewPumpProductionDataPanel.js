var screwPumpProHandsontableHelper=null;
Ext.define("AP.view.productionData.ScrewPumpProductionDataPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.screwPumpProductionDataPanel',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var screwPumpStore = new Ext.data.JsonStore({
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
                	var org_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var wellName = Ext.getCmp('screwPumpProductionDataJhCombo_Id').getValue();
                    var new_params = {
                        wellName:wellName,
                        orgId: org_Id,
                        wellType:400
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var screwPumpCombo = Ext.create( 'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.wellName,
                id: "screwPumpProductionDataJhCombo_Id",
                labelWidth: 35,
                width: 145,
                labelAlign: 'left',
                queryMode: 'remote',
                typeAhead: true,
                store: screwPumpStore,
                autoSelect: false,
                editable: true,
                triggerAction: 'all',
                displayField: "boxval",
                emptyText: cosog.string.all,
                blankText: cosog.string.all,
                valueField: "boxkey",
                pageSize:comboxPagingStatus,
                minChars:0,
                listeners: {
                    select: function (combo, record, index) {
                    	CreateAndLoadScrewPumpProTable();
                    },
                    expand: function (sm, selections) {
                    	screwPumpCombo.getStore().loadPage(1);
                    }
                }
            });
       
        Ext.apply(me, {
            tbar: [screwPumpCombo,'-', {
        		id: 'ScrewPumpProductionDataTotalCount_Id',
        		xtype: 'component',
        		hidden: false,
        		tpl: cosog.string.totalCount + ': {count}',
        		style: 'margin-right:15px'
			},'->',{
    			xtype: 'button',
    			text: cosog.string.exportExcel,
                pressed: true,
    			hidden:false,
    			handler: function (v, o) {
    				var fields = "";
    			    var heads = "";
    			    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
    				var jh = Ext.getCmp('ProductionOutName_Id').getValue();
    				var wellType=400;
    				var url=context + '/productionDataController/exportWellProdInformationData';
    				for(var i=0;i<screwPumpProHandsontableHelper.colHeaders.length;i++){
    					fields+=screwPumpProHandsontableHelper.columns[i].data+",";
    					heads+=screwPumpProHandsontableHelper.colHeaders[i]+","
    				}
    				if (isNotVal(fields)) {
    			        fields = fields.substring(0, fields.length - 1);
    			        heads = heads.substring(0, heads.length - 1);
    			    }
    				
    			    var param = "&fields=" + fields +"&heads=" + URLencode(URLencode(heads)) + "&orgId=" + leftOrg_Id+"&wellType="+wellType  + "&jh=" + URLencode(URLencode(jh)) + "&fileName="+URLencode(URLencode("螺杆泵生产数据"))+ "&title="+URLencode(URLencode("螺杆泵生产数据"));
    			    openExcelWindow(url + '?flag=true' + param);
    			}
    		},'-', {
            	xtype: 'button',
            	disabled: false,
            	hidden:false,
            	pressed: true,
            	text: cosog.string.save,
            	iconCls: 'save',
            	handler: function (v, o) {
            		screwPumpProHandsontableHelper.saveData();
    			}
            }],
            html:'<div class="ScrewPumpProductionContainer" style="width:100%;height:100%;"><div class="con" id="ScrewPumpProductionDataDiv_id"></div></div>',
            listeners: {
                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                		CreateAndLoadScrewPumpProTable();
                }
            }
        });
        me.callParent(arguments);
    }
});

function CreateAndLoadScrewPumpProTable(isNew){
	if(isNew&&screwPumpProHandsontableHelper!=null){
		screwPumpProHandsontableHelper.clearContainer();
		screwPumpProHandsontableHelper.hot.destroy();
		screwPumpProHandsontableHelper=null;
	}
	var org_Id = Ext.getCmp('leftOrg_Id').getValue();
    var wellName = Ext.getCmp('screwPumpProductionDataJhCombo_Id').getValue();
    var wellType=200;
    var tabPanel = Ext.getCmp("ProductionWellProductionPanel");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="PumpingUnitProductionDataPanel"){
		wellType=200;
	}else if(activeId=="ScrewPumpProductionDataPanel"){
		wellType=400;
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/productionDataController/showProductionOutData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(screwPumpProHandsontableHelper==null){
				screwPumpProHandsontableHelper = ScrewPumpProHandsontableHelper.createNew("ScrewPumpProductionDataDiv_id");
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
	        	screwPumpProHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
	        	screwPumpProHandsontableHelper.columns=Ext.JSON.decode(columns);
				screwPumpProHandsontableHelper.createTable(result.totalRoot);
			}else{
				screwPumpProHandsontableHelper.hot.loadData(result.totalRoot);
			}
			Ext.getCmp("ScrewPumpProductionDataTotalCount_Id").update({count: result.totalCount});
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			wellName: wellName,
            recordCount:50,
            orgId:org_Id,
            page:1,
            limit:10000,
            wellType:wellType
        }
	});
};

var ScrewPumpProHandsontableHelper = {
	    createNew: function (divid) {
	        var screwPumpProHandsontableHelper = {};
	        screwPumpProHandsontableHelper.hot = '';
	        screwPumpProHandsontableHelper.divid = divid;
	        screwPumpProHandsontableHelper.validresult=true;//数据校验
	        screwPumpProHandsontableHelper.colHeaders=[];
	        screwPumpProHandsontableHelper.columns=[];
	        
	        screwPumpProHandsontableHelper.AllData={};
	        screwPumpProHandsontableHelper.updatelist=[];
	        screwPumpProHandsontableHelper.delidslist=[];
	        screwPumpProHandsontableHelper.insertlist=[];
	        
	        screwPumpProHandsontableHelper.createTable = function (data) {
	        	$('#'+screwPumpProHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+screwPumpProHandsontableHelper.divid);
	        	screwPumpProHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: true
	                },
	                columns:screwPumpProHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:screwPumpProHandsontableHelper.colHeaders,//显示列头
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
//	                cells: function (row, col, prop) {
//	                	var cellProperties = {};
//	                    var visualRowIndex = this.instance.toVisualRow(row);
//	                    var visualColIndex = this.instance.toVisualColumn(col);
//	                    if (col === 2) {
//	                        this.type = 'dropdown';
//	                        this.source = ['抽油机', '电潜泵', '螺杆泵'];
//	                        this.strict = true;
//	                        this.allowInvalid = false;
//	                    }
//	                },
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
	                            var rowdata = screwPumpProHandsontableHelper.hot.getDataAtRow(i);
	                            ids.push(rowdata[0]);
	                        }
	                        screwPumpProHandsontableHelper.delExpressCount(ids);
	                        screwPumpProHandsontableHelper.screening();
	                    }
	                },
	                afterChange: function (changes, source) {
	                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
	                    
	                    if (changes != null) {
	                    	for(var i=0;i<changes.length;i++){
	                    		var params = [];
	                    		var index = changes[i][0]; //行号码
		                        var rowdata = screwPumpProHandsontableHelper.hot.getDataAtRow(index);
		                        params.push(rowdata[0]);
		                        params.push(changes[i][1]);
		                        params.push(changes[i][2]);
		                        params.push(changes[i][3]);

		                        //仅当单元格发生改变的时候,id!=null,说明是更新
		                        if (params[2] != params[3] && params[0] != null && params[0] >0) {
		                        	var data="{";
		                        	for(var j=0;j<screwPumpProHandsontableHelper.columns.length;j++){
		                        		data+=screwPumpProHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
		                        		if(j<screwPumpProHandsontableHelper.columns.length-1){
		                        			data+=","
		                        		}
		                        	}
		                        	data+="}"
		                            screwPumpProHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
		                        }
	                    	}
	                        
	                    }
	                }
	        	});
	        }
	      //插入的数据的获取
	        screwPumpProHandsontableHelper.insertExpressCount=function() {
	            var idsdata = screwPumpProHandsontableHelper.hot.getDataAtCol(0); //所有的id
	            for (var i = 0; i < idsdata.length; i++) {
	                //id=null时,是插入数据,此时的i正好是行号
	                if (idsdata[i] == null || idsdata[i]<0) {
	                    //获得id=null时的所有数据封装进data
	                    var rowdata = screwPumpProHandsontableHelper.hot.getDataAtRow(i);
	                    //var collength = hot.countCols();
	                    if (rowdata != null) {
	                    	var data="{";
                        	for(var j=0;j<screwPumpProHandsontableHelper.columns.length;j++){
                        		data+=screwPumpProHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
                        		if(j<screwPumpProHandsontableHelper.columns.length-1){
                        			data+=","
                        		}
                        	}
                        	data+="}"
	                        screwPumpProHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
	                    }
	                }
	            }
	            if (screwPumpProHandsontableHelper.insertlist.length != 0) {
	            	screwPumpProHandsontableHelper.AllData.insertlist = screwPumpProHandsontableHelper.insertlist;
	            }
	        }
	        //保存数据
	        screwPumpProHandsontableHelper.saveData = function () {
	            //插入的数据的获取
	        	screwPumpProHandsontableHelper.insertExpressCount();
	            if (JSON.stringify(screwPumpProHandsontableHelper.AllData) != "{}" && screwPumpProHandsontableHelper.validresult) {
	            	var wellType=200;
	                var tabPanel = Ext.getCmp("ProductionWellProductionPanel");
	            	var activeId = tabPanel.getActiveTab().id;
	            	if(activeId=="PumpingUnitProductionDataPanel"){
	            		wellType=200;
	            	}else if(activeId=="ScrewPumpProductionDataPanel"){
	            		wellType=400;
	            	}
	            	Ext.Ajax.request({
	            		method:'POST',
	            		url:context + '/productionDataController/saveWellProHandsontableData',
	            		success:function(response) {
	            			rdata=Ext.JSON.decode(response.responseText);
	            			if (rdata.success) {
	                        	Ext.MessageBox.alert("信息","保存成功");
	                            //保存以后重置全局容器
	                            screwPumpProHandsontableHelper.clearContainer();
	                            //销毁
//	                            screwPumpProHandsontableHelper.hot.destroy();
	                            CreateAndLoadScrewPumpProTable();
	                        } else {
	                        	Ext.MessageBox.alert("信息","数据保存失败");

	                        }
	            		},
	            		failure:function(){
	            			Ext.MessageBox.alert("信息","请求失败");
	                        screwPumpProHandsontableHelper.clearContainer();
	            		},
	            		params: {
	                    	data: JSON.stringify(screwPumpProHandsontableHelper.AllData),
	                    	wellType:wellType
	                    }
	            	}); 
	            } else {
	                if (!screwPumpProHandsontableHelper.validresult) {
	                	Ext.MessageBox.alert("信息","数据类型错误");
	                } else {
	                	Ext.MessageBox.alert("信息","无数据变化");
	                }
	            }
	        }
	      //删除的优先级最高
	        screwPumpProHandsontableHelper.delExpressCount=function(ids) {
	            //传入的ids.length不可能为0
	            $.each(ids, function (index, id) {
	                if (id != null) {
	                	screwPumpProHandsontableHelper.delidslist.push(id);
	                }
	            });
	            screwPumpProHandsontableHelper.AllData.delidslist = screwPumpProHandsontableHelper.delidslist;
	        }

	        //updatelist数据更新
	        screwPumpProHandsontableHelper.screening=function() {
	            if (screwPumpProHandsontableHelper.updatelist.length != 0 && screwPumpProHandsontableHelper.delidslist.lentgh != 0) {
	                for (var i = 0; i < screwPumpProHandsontableHelper.delidslist.length; i++) {
	                    for (var j = 0; j < screwPumpProHandsontableHelper.updatelist.length; j++) {
	                        if (screwPumpProHandsontableHelper.updatelist[j].id == screwPumpProHandsontableHelper.delidslist[i]) {
	                            //更新updatelist
	                        	screwPumpProHandsontableHelper.updatelist.splice(j, 1);
	                        }
	                    }
	                }
	                //把updatelist封装进AllData
	                screwPumpProHandsontableHelper.AllData.updatelist = screwPumpProHandsontableHelper.updatelist;
	            }
	        }
	        
	      //更新数据
	        screwPumpProHandsontableHelper.updateExpressCount=function(data) {
	            if (JSON.stringify(data) != "{}") {
	                var flag = true;
	                //判断记录是否存在,更新数据     
	                $.each(screwPumpProHandsontableHelper.updatelist, function (index, node) {
	                    if (node.id == data.id) {
	                        //此记录已经有了
	                        flag = false;
	                        //用新得到的记录替换原来的,不用新增
	                        screwPumpProHandsontableHelper.updatelist[index] = data;
	                    }
	                });
	                flag && screwPumpProHandsontableHelper.updatelist.push(data);
	                //封装
	                screwPumpProHandsontableHelper.AllData.updatelist = screwPumpProHandsontableHelper.updatelist;
	            }
	        }
	        
	        screwPumpProHandsontableHelper.clearContainer = function () {
	        	screwPumpProHandsontableHelper.AllData = {};
	        	screwPumpProHandsontableHelper.updatelist = [];
	        	screwPumpProHandsontableHelper.delidslist = [];
	        	screwPumpProHandsontableHelper.insertlist = [];
	        }
	        
	        return screwPumpProHandsontableHelper;
	    }
};