var inverOptimizeHandsontableHelper=null;
Ext.define('AP.view.electricAnalysis.InverOptimizeInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.inverOptimizeInfoView',
    id: 'InverOptimizeInfoView_Id',
    layout: 'fit',
    border: false,
    initComponent: function () {
//        var wellStore = Ext.create('AP.store.well.WellInfoStore');
        var wellComboBoxStore = new Ext.data.JsonStore({
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
                    var jh_val = Ext.getCmp('inverOptimizeInfoPanel_Well_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        wellType:200,
                        wellName: wellName
                    };
                    Ext.apply(store.proxy.extraParams,new_params);
                }
            }
        });
        
        
        var wellComboBox = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.wellName,
                id: "inverOptimizeInfoPanel_Well_Id",
                labelWidth: 35,
                width: 145,
                labelAlign: 'left',
                queryMode: 'remote',
                typeAhead: true,
                store: wellComboBoxStore,
                autoSelect: false,
                editable: true,
                triggerAction: 'all',
                displayField: "boxval",
                valueField: "boxkey",
                pageSize:comboxPagingStatus,
                minChars:0,
                emptyText: cosog.string.all,
                blankText: cosog.string.all,
                listeners: {
                    expand: function (sm, selections) {
//                        wellComboBox.clearValue();
                        wellComboBox.getStore().loadPage(1); // 加载井下拉框的store
                    },
                    afterRender: function (combo, o) {
                        if (wellComboBoxStore.getTotalCount() > 0) {
                            var orgId = wellComboBoxStore.data.items[0].data.boxkey;
                            var orgName = wellComboBoxStore.data.items[0].data.boxval;
                            combo.setValue(orgId);
                            combo.setRawValue(orgName);
                        }
                    },
                    specialkey: function (field, e) {
                        onEnterKeyDownFN(field, e, 'wellPanel_Id');
                    },
                    select: function (combo, record, index) {
                        try {
                        	CreateAndLoadInverOptimizeTable();
                        } catch (ex) {
                            Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                        }
                    }
                }
            });
        
        
        Ext.apply(this, {
            tbar: [wellComboBox,'-', {
                		id: 'InverOptimizeWellTotalCount_Id',
                		xtype: 'component',
                		hidden: false,
                		tpl: cosog.string.totalCount + ': {count}',
                		style: 'margin-right:15px'
    				}, '->',{
            			xtype: 'button',
            			itemId: 'saveInverOptimizeClassBtnId',
            			id: 'saveInverOptimizeClassBtn_Id',
            			disabled: false,
            			hidden:false,
            			pressed: true,
            			text: cosog.string.save,
            			iconCls: 'save',
            			handler: function (v, o) {
            				inverOptimizeHandsontableHelper.saveData();
            			}
            		}],
            		html:'<div class="InverOptimizeContainer" style="width:100%;height:100%;"><div class="con" id="InverOptimizeHandsonTableDiv_id"></div></div>',
                    listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        	if(inverOptimizeHandsontableHelper!=null){
                        		CreateAndLoadInverOptimizeTable();
                        	}
                        }
                    }
        })
        this.callParent(arguments);
    }
});
function CreateAndLoadInverOptimizeTable(isNew){
	if(isNew&&inverOptimizeHandsontableHelper!=null){
        inverOptimizeHandsontableHelper.clearContainer();
        inverOptimizeHandsontableHelper.hot.destroy();
        inverOptimizeHandsontableHelper=null;
	}
	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
	var wellInformationName_Id = Ext.getCmp('inverOptimizeInfoPanel_Well_Id').getValue();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/PSToFSController/getInverOptimizeData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(inverOptimizeHandsontableHelper==null){
				inverOptimizeHandsontableHelper = InverOptimizeHandsontableHelper.createNew("InverOptimizeHandsonTableDiv_id");
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
	        	inverOptimizeHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
	        	inverOptimizeHandsontableHelper.columns=Ext.JSON.decode(columns);
				inverOptimizeHandsontableHelper.createTable(result.totalRoot);
			}else{
				inverOptimizeHandsontableHelper.hot.loadData(result.totalRoot);
			}
			Ext.getCmp("InverOptimizeWellTotalCount_Id").update({count: result.totalCount});
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
            wellInformationName: wellInformationName_Id,
            recordCount:50,
            orgId:leftOrg_Id,
            page:1,
            limit:10000
        }
	});
};

var InverOptimizeHandsontableHelper = {
	    createNew: function (divid) {
	        var inverOptimizeHandsontableHelper = {};
	        inverOptimizeHandsontableHelper.hot = '';
	        inverOptimizeHandsontableHelper.divid = divid;
	        inverOptimizeHandsontableHelper.validresult=true;//数据校验
	        inverOptimizeHandsontableHelper.colHeaders=[];
	        inverOptimizeHandsontableHelper.columns=[];
	        
	        inverOptimizeHandsontableHelper.AllData={};
	        inverOptimizeHandsontableHelper.updatelist=[];
	        inverOptimizeHandsontableHelper.delidslist=[];
	        inverOptimizeHandsontableHelper.insertlist=[];
	        inverOptimizeHandsontableHelper.editWellNameList=[];
	        
	        inverOptimizeHandsontableHelper.createTable = function (data) {
	        	$('#'+inverOptimizeHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+inverOptimizeHandsontableHelper.divid);
	        	inverOptimizeHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: false
	                },
	                columns:inverOptimizeHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:inverOptimizeHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
//	                contextMenu: {
//	                	items: {
//	                	    "row_above": {
//	                	      name: '向上插入一行',
//	                	    },
//	                	    "row_below": {
//	                	      name: '向下插入一行',
//	                	    },
//	                	    "col_left": {
//	                	      name: '向左插入一列',
//	                	    },
//	                	    "col_right": {
//	                	      name: '向右插入一列',
//	                	    },
//	                	    "remove_row": {
//	                	      name: '删除行',
//	                	    },
//	                	    "remove_col": {
//	                	      name: '删除列',
//	                	    },
//	                	    "merge_cell": {
//	                	      name: '合并单元格',
//	                	    },
//	                	    "copy": {
//	                	      name: '复制',
//	                	    },
//	                	    "cut": {
//	                	      name: '剪切',
//	                	    },
//	                	    "paste": {
//	                	      name: '粘贴',
//	                	      disabled: function() {
////	                	        return self.clipboardCache.length === 0;
//	                	      },
//	                	      callback: function() {
////	                	        var plugin = this.getPlugin('copyPaste');
////	                	        this.listen();
////	                	        plugin.paste(self.clipboardCache);
//	                	      }
//	                	    }
//	                	}
//	                },//右键菜单展示
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
//	                dropdownMenu: ['filter_by_condition', 'filter_by_value', 'filter_action_bar'],
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
//	                    if (col === 12) {
//	                        this.type = 'dropdown';
//	                        this.source = ['人工录入','DI信号', '电参计算','转速计算' ];
//	                        this.strict = true;
//	                        this.allowInvalid = false;
//	                    }
//	                    if (col === 6) {
//	                        this.type = 'dropdown';
//	                        this.source = ['抽油机', '螺杆泵'];
//	                        this.strict = true;
//	                        this.allowInvalid = false;
//	                    }
	                },
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
	                            var rowdata = inverOptimizeHandsontableHelper.hot.getDataAtRow(i);
	                            ids.push(rowdata[0]);
	                        }
	                        inverOptimizeHandsontableHelper.delExpressCount(ids);
	                        inverOptimizeHandsontableHelper.screening();
	                    }
	                },
	                afterChange: function (changes, source) {
	                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
	                    
	                    if (changes != null) {
	                    	for(var i=0;i<changes.length;i++){
	                    		var params = [];
	                    		var index = changes[i][0]; //行号码
		                        var rowdata = inverOptimizeHandsontableHelper.hot.getDataAtRow(index);
		                        params.push(rowdata[0]);
		                        params.push(changes[i][1]);
		                        params.push(changes[i][2]);
		                        params.push(changes[i][3]);
		                        
		                        if("edit"==source&&params[1]=="jh"){//编辑井号单元格
		                        	var data="{\"oldWellName\":\""+params[2]+"\",\"newWellName\":\""+params[3]+"\"}";
		                        	inverOptimizeHandsontableHelper.editWellNameList.push(Ext.JSON.decode(data));
		                        }

		                        //仅当单元格发生改变的时候,id!=null,说明是更新
		                        if (params[2] != params[3] && params[0] != null && params[0] >0) {
		                        	var data="{";
		                        	for(var j=0;j<inverOptimizeHandsontableHelper.columns.length;j++){
		                        		data+=inverOptimizeHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
		                        		if(j<inverOptimizeHandsontableHelper.columns.length-1){
		                        			data+=","
		                        		}
		                        	}
		                        	data+="}"
		                            inverOptimizeHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
		                        }
	                    	}
	                        
	                    }
	                }
	        	});
	        }
	      //插入的数据的获取
	        inverOptimizeHandsontableHelper.insertExpressCount=function() {
	            var idsdata = inverOptimizeHandsontableHelper.hot.getDataAtCol(0); //所有的id
	            for (var i = 0; i < idsdata.length; i++) {
	                //id=null时,是插入数据,此时的i正好是行号
	                if (idsdata[i] == null||idsdata[i]<0) {
	                    //获得id=null时的所有数据封装进data
	                    var rowdata = inverOptimizeHandsontableHelper.hot.getDataAtRow(i);
	                    //var collength = hot.countCols();
	                    if (rowdata != null) {
	                    	var data="{";
                        	for(var j=0;j<inverOptimizeHandsontableHelper.columns.length;j++){
                        		data+=inverOptimizeHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
                        		if(j<inverOptimizeHandsontableHelper.columns.length-1){
                        			data+=","
                        		}
                        	}
                        	data+="}"
	                        inverOptimizeHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
	                    }
	                }
	            }
	            if (inverOptimizeHandsontableHelper.insertlist.length != 0) {
	            	inverOptimizeHandsontableHelper.AllData.insertlist = inverOptimizeHandsontableHelper.insertlist;
	            }
	        }
	        //保存数据
	        inverOptimizeHandsontableHelper.saveData = function () {
	        	
	        	var IframeViewSelection  = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();

        		//插入的数据的获取
	        	inverOptimizeHandsontableHelper.insertExpressCount();
	            if (JSON.stringify(inverOptimizeHandsontableHelper.AllData) != "{}" && inverOptimizeHandsontableHelper.validresult) {
	            	Ext.Ajax.request({
	            		method:'POST',
	            		url:context + '/PSToFSController/saveInverOptimizeHandsontableData',
	            		success:function(response) {
	            			rdata=Ext.JSON.decode(response.responseText);
	            			if (rdata.success) {
	                        	Ext.MessageBox.alert("信息","保存成功");
	                            //保存以后重置全局容器
	                            inverOptimizeHandsontableHelper.clearContainer();
	                            CreateAndLoadInverOptimizeTable();
	                        } else {
	                        	Ext.MessageBox.alert("信息","数据保存失败");

	                        }
	            		},
	            		failure:function(){
	            			Ext.MessageBox.alert("信息","请求失败");
	                        inverOptimizeHandsontableHelper.clearContainer();
	            		},
	            		params: {
	                    	data: JSON.stringify(inverOptimizeHandsontableHelper.AllData)
	                    }
	            	}); 
	            } else {
	                if (!inverOptimizeHandsontableHelper.validresult) {
	                	Ext.MessageBox.alert("信息","数据类型错误");
	                } else {
	                	Ext.MessageBox.alert("信息","无数据变化");
	                }
	            }
	            
	        }
	        
	        
	      //删除的优先级最高
	        inverOptimizeHandsontableHelper.delExpressCount=function(ids) {
	            //传入的ids.length不可能为0
	            $.each(ids, function (index, id) {
	                if (id != null) {
	                	inverOptimizeHandsontableHelper.delidslist.push(id);
	                }
	            });
	            inverOptimizeHandsontableHelper.AllData.delidslist = inverOptimizeHandsontableHelper.delidslist;
	        }

	        //updatelist数据更新
	        inverOptimizeHandsontableHelper.screening=function() {
	            if (inverOptimizeHandsontableHelper.updatelist.length != 0 && inverOptimizeHandsontableHelper.delidslist.lentgh != 0) {
	                for (var i = 0; i < inverOptimizeHandsontableHelper.delidslist.length; i++) {
	                    for (var j = 0; j < inverOptimizeHandsontableHelper.updatelist.length; j++) {
	                        if (inverOptimizeHandsontableHelper.updatelist[j].id == inverOptimizeHandsontableHelper.delidslist[i]) {
	                            //更新updatelist
	                        	inverOptimizeHandsontableHelper.updatelist.splice(j, 1);
	                        }
	                    }
	                }
	                //把updatelist封装进AllData
	                inverOptimizeHandsontableHelper.AllData.updatelist = inverOptimizeHandsontableHelper.updatelist;
	            }
	        }
	        
	      //更新数据
	        inverOptimizeHandsontableHelper.updateExpressCount=function(data) {
	            if (JSON.stringify(data) != "{}") {
	                var flag = true;
	                //判断记录是否存在,更新数据     
	                $.each(inverOptimizeHandsontableHelper.updatelist, function (index, node) {
	                    if (node.id == data.id) {
	                        //此记录已经有了
	                        flag = false;
	                        //用新得到的记录替换原来的,不用新增
	                        inverOptimizeHandsontableHelper.updatelist[index] = data;
	                    }
	                });
	                flag && inverOptimizeHandsontableHelper.updatelist.push(data);
	                //封装
	                inverOptimizeHandsontableHelper.AllData.updatelist = inverOptimizeHandsontableHelper.updatelist;
	            }
	        }
	        
	        inverOptimizeHandsontableHelper.clearContainer = function () {
	        	inverOptimizeHandsontableHelper.AllData = {};
	        	inverOptimizeHandsontableHelper.updatelist = [];
	        	inverOptimizeHandsontableHelper.delidslist = [];
	        	inverOptimizeHandsontableHelper.insertlist = [];
	        	inverOptimizeHandsontableHelper.editWellNameList=[];
	        }
	        
	        return inverOptimizeHandsontableHelper;
	    }
};