var wellInfoHandsontableHelper=null;
Ext.define('AP.view.well.WellInfoPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.wellInfoPanel',
    id: 'WellInfoPanelView_Id',
    layout: 'fit',
    border: false,
    initComponent: function () {
//        var wellStore = Ext.create('AP.store.well.WellInfoStore');
        var jhStore = new Ext.data.JsonStore({
        	pageSize:defaultJhComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
            	url: context + '/monitorPumpingUnitParamsManagerController/queryMonitorPUJhh',
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
                    var jh_val = Ext.getCmp('wellInfoPanel_jh_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        jh: jh_val,
                        type: 'jh'
                    };
                    Ext.apply(store.proxy.extraParams,new_params);
                }
            }
        });
        
        var jslxStore = new Ext.data.JsonStore({
            fields: ['boxkey', 'boxval'],
            data : [
            	{"boxkey":'', "boxval":"选择全部"},
            	{"boxkey":'200', "boxval":"抽油机"},
                {"boxkey":'400', "boxval":"螺杆泵"}
            ]
        });
        
        var simpleCombo = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.jh,
                id: "wellInfoPanel_jh_Id",
                labelWidth: 35,
                width: 145,
                labelAlign: 'left',
                queryMode: 'remote',
                typeAhead: true,
                store: jhStore,
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
//                        simpleCombo.clearValue();
//                        simpleCombo.getStore().loadPage(1); // 加载井下拉框的store
                    },
                    afterRender: function (combo, o) {
                        if (jhStore.getTotalCount() > 0) {
                            var orgId = jhStore.data.items[0].data.boxkey;
                            var orgName = jhStore.data.items[0].data.boxval;
                            combo.setValue(orgId);
                            combo.setRawValue(orgName);
                        }
                    },
                    specialkey: function (field, e) {
                        onEnterKeyDownFN(field, e, 'wellPanel_Id');
                    },
                    select: function (combo, record, index) {
                        try {
                        	CreateAndLoadWellInfoTable();
                        } catch (ex) {
                            Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                        }
                    }
                }
            });
        
        var jslxCombo = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: '举升类型',
                    id: "wellInfoPanel_jslx_Id",
                    labelWidth: 70,
                    width: 180,
                    labelAlign: 'left',
                    queryMode: 'local',
                    store: jslxStore,
                    autoSelect: false,
                    editable: false,
                    triggerAction: 'all',
                    displayField: "boxval",
                    valueField: "boxkey",
                    pageSize:comboxPagingStatus,
                    emptyText: cosog.string.all,
                    blankText: cosog.string.all,
                    listeners: {
                        select: function (combo, record, index) {
                        	CreateAndLoadWellInfoTable();
                        }
                    }
                });
        
        Ext.apply(this, {
            tbar: [simpleCombo,'-',jslxCombo,'-', {
                		id: 'ProductionWellTotalCount_Id',
                		xtype: 'component',
                		hidden: false,
                		tpl: cosog.string.totalCount + ': {count}',
                		style: 'margin-right:15px'
    				},{
            			xtype: 'textfield',
            			id: 'wellInformationSelectedJh_Id',
            			hidden:true
            		}, '->', {
            			xtype: 'button',
            			text: cosog.string.exportExcel,
                        pressed: true,
            			hidden:false,
            			handler: function (v, o) {
            				var fields = "";
            			    var heads = "";
            			    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
            				var jh = Ext.getCmp('wellInfoPanel_jh_Id').getValue();
            				var url=context + '/wellInformationManagerController/exportWellInformationData';
            				for(var i=0;i<wellInfoHandsontableHelper.colHeaders.length;i++){
            					fields+=wellInfoHandsontableHelper.columns[i].data+",";
            					heads+=wellInfoHandsontableHelper.colHeaders[i]+","
            				}
            				if (isNotVal(fields)) {
            			        fields = fields.substring(0, fields.length - 1);
            			        heads = heads.substring(0, heads.length - 1);
            			    }
            				
            			    var param = "&fields=" + fields +"&heads=" + URLencode(URLencode(heads)) + "&orgId=" + leftOrg_Id  + "&jh=" + URLencode(URLencode(jh)) +"&recordCount=10000"+ "&fileName="+URLencode(URLencode("井名基本信息"))+ "&title="+URLencode(URLencode("井名基本信息"));
            			    openExcelWindow(url + '?flag=true' + param);
            			}
            		},'-', {
            			xtype: 'button',
            			itemId: 'saveWellInformationClassBtnId',
            			id: 'saveWellInformationClassBtn_Id',
            			disabled: false,
            			hidden:false,
            			pressed: true,
            			text: cosog.string.save,
            			iconCls: 'save',
            			handler: function (v, o) {
            				wellInfoHandsontableHelper.saveData();
            			}
            		},'-', {
            			xtype: 'button',
            			itemId: 'editWellNameClassBtnId',
            			id: 'editWellNameClassBtn_Id',
            			disabled: false,
            			hidden:false,
            			pressed: true,
            			text: '修改井名',
            			iconCls: 'edit',
            			handler: function (v, o) {
            				wellInfoHandsontableHelper.editWellName();
            			}
            		}],
            		html:'<div class="WellInformatonContainer" style="width:100%;height:100%;"><div class="con" id="WellInformatonDiv_id"></div></div>',
                    listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        	if(wellInfoHandsontableHelper!=null){
                        		CreateAndLoadWellInfoTable();
                        	}
                        }
                    }
        })
        this.callParent(arguments);
    }
});
function CreateAndLoadWellInfoTable(isNew){
	if(isNew&&wellInfoHandsontableHelper!=null){
        wellInfoHandsontableHelper.clearContainer();
        wellInfoHandsontableHelper.hot.destroy();
        wellInfoHandsontableHelper=null;
	}
	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
	var wellInformationName_Id = Ext.getCmp('wellInfoPanel_jh_Id').getValue();
	var liftingType = Ext.getCmp('wellInfoPanel_jslx_Id').getValue();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/wellInformationManagerController/doWellInformationShow',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(wellInfoHandsontableHelper==null){
				wellInfoHandsontableHelper = WellInfoHandsontableHelper.createNew("WellInformatonDiv_id");
				var colHeaders="[";
		        var columns="[";
	            for(var i=0;i<result.columns.length;i++){
	            	colHeaders+="'"+result.columns[i].header+"'";
	            	if(result.columns[i].dataIndex==="liftingTypeName"){
	            		columns+="{data:'"+result.columns[i].dataIndex+"',type:'dropdown',strict:true,allowInvalid:false,source:['抽油机', '螺杆泵']}";
	            	}else if(result.columns[i].dataIndex==="runtimeEfficiencySource"){
	            		columns+="{data:'"+result.columns[i].dataIndex+"',type:'dropdown',strict:true,allowInvalid:false,source:['人工录入','DI信号', '电参计算','转速计算']}";
	            	}else if(result.columns[i].dataIndex==="driverName"){
	            		var source="[";
	            		for(var j=0;j<result.driverDropdownData.length;j++){
	            			source+="\'"+result.driverDropdownData[j]+"\'";
	            			if(j<result.driverDropdownData.length-1){
	            				source+=",";
	            			}
	            		}
	            		source+="]";
	            		columns+="{data:'"+result.columns[i].dataIndex+"',type:'dropdown',strict:true,allowInvalid:false,source:"+source+"}";
	            	}else if(result.columns[i].dataIndex==="acquisitionUnit"){
	            		var source="[";
	            		for(var j=0;j<result.unitDropdownData.length;j++){
	            			source+="\'"+result.unitDropdownData[j]+"\'";
	            			if(j<result.unitDropdownData.length-1){
	            				source+=",";
	            			}
	            		}
	            		source+="]";
	            		columns+="{data:'"+result.columns[i].dataIndex+"',type:'dropdown',strict:true,allowInvalid:false,source:"+source+"}";
	            	}else{
	            		columns+="{data:'"+result.columns[i].dataIndex+"'}";
	            	}
	            	if(i<result.columns.length-1){
	            		colHeaders+=",";
	                	columns+=",";
	            	}
	            }
	            colHeaders+="]";
	        	columns+="]";
	        	wellInfoHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
	        	wellInfoHandsontableHelper.columns=Ext.JSON.decode(columns);
				wellInfoHandsontableHelper.createTable(result.totalRoot);
			}else{
				wellInfoHandsontableHelper.hot.loadData(result.totalRoot);
			}
			Ext.getCmp("ProductionWellTotalCount_Id").update({count: result.totalCount});
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
            wellInformationName: wellInformationName_Id,
            liftingType:liftingType,
            recordCount:50,
            orgId:leftOrg_Id,
            page:1,
            limit:10000
        }
	});
};

var WellInfoHandsontableHelper = {
	    createNew: function (divid) {
	        var wellInfoHandsontableHelper = {};
	        wellInfoHandsontableHelper.hot = '';
	        wellInfoHandsontableHelper.divid = divid;
	        wellInfoHandsontableHelper.validresult=true;//数据校验
	        wellInfoHandsontableHelper.colHeaders=[];
	        wellInfoHandsontableHelper.columns=[];
	        
	        wellInfoHandsontableHelper.AllData={};
	        wellInfoHandsontableHelper.updatelist=[];
	        wellInfoHandsontableHelper.delidslist=[];
	        wellInfoHandsontableHelper.insertlist=[];
	        wellInfoHandsontableHelper.editWellNameList=[];
	        
	        wellInfoHandsontableHelper.createTable = function (data) {
	        	$('#'+wellInfoHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+wellInfoHandsontableHelper.divid);
	        	wellInfoHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: true
	                },
	                columns:wellInfoHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:wellInfoHandsontableHelper.colHeaders,//显示列头
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
	                            var rowdata = wellInfoHandsontableHelper.hot.getDataAtRow(i);
	                            ids.push(rowdata[0]);
	                        }
	                        wellInfoHandsontableHelper.delExpressCount(ids);
	                        wellInfoHandsontableHelper.screening();
	                    }
	                },
	                afterChange: function (changes, source) {
	                    //params 参数 1.column num , 2,id, 3,oldvalue , 4.newvalue
	                    
	                    if (changes != null) {
	                    	for(var i=0;i<changes.length;i++){
	                    		var params = [];
	                    		var index = changes[i][0]; //行号码
		                        var rowdata = wellInfoHandsontableHelper.hot.getDataAtRow(index);
		                        params.push(rowdata[0]);
		                        params.push(changes[i][1]);
		                        params.push(changes[i][2]);
		                        params.push(changes[i][3]);
		                        
		                        if("edit"==source&&params[1]=="wellName"){//编辑井号单元格
		                        	var data="{\"oldWellName\":\""+params[2]+"\",\"newWellName\":\""+params[3]+"\"}";
		                        	wellInfoHandsontableHelper.editWellNameList.push(Ext.JSON.decode(data));
		                        }

		                        //仅当单元格发生改变的时候,id!=null,说明是更新
		                        if (params[2] != params[3] && params[0] != null && params[0] >0) {
		                        	var data="{";
		                        	for(var j=0;j<wellInfoHandsontableHelper.columns.length;j++){
		                        		data+=wellInfoHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
		                        		if(j<wellInfoHandsontableHelper.columns.length-1){
		                        			data+=","
		                        		}
		                        	}
		                        	data+="}"
		                            wellInfoHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
		                        }
	                    	}
	                        
	                    }
	                }
	        	});
	        }
	      //插入的数据的获取
	        wellInfoHandsontableHelper.insertExpressCount=function() {
	            var idsdata = wellInfoHandsontableHelper.hot.getDataAtCol(0); //所有的id
	            for (var i = 0; i < idsdata.length; i++) {
	                //id=null时,是插入数据,此时的i正好是行号
	                if (idsdata[i] == null||idsdata[i]<0) {
	                    //获得id=null时的所有数据封装进data
	                    var rowdata = wellInfoHandsontableHelper.hot.getDataAtRow(i);
	                    //var collength = hot.countCols();
	                    if (rowdata != null) {
	                    	var data="{";
                        	for(var j=0;j<wellInfoHandsontableHelper.columns.length;j++){
                        		data+=wellInfoHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
                        		if(j<wellInfoHandsontableHelper.columns.length-1){
                        			data+=","
                        		}
                        	}
                        	data+="}"
	                        wellInfoHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
	                    }
	                }
	            }
	            if (wellInfoHandsontableHelper.insertlist.length != 0) {
	            	wellInfoHandsontableHelper.AllData.insertlist = wellInfoHandsontableHelper.insertlist;
	            }
	        }
	        //保存数据
	        wellInfoHandsontableHelper.saveData = function () {
	        	
	        	var IframeViewSelection  = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
	        	if(IframeViewSelection.length>0&&IframeViewSelection[0].isLeaf()){
	        		//插入的数据的获取
		        	wellInfoHandsontableHelper.insertExpressCount();
		        	var orgId=IframeViewSelection[0].data.orgId;
		            if (JSON.stringify(wellInfoHandsontableHelper.AllData) != "{}" && wellInfoHandsontableHelper.validresult) {
		            	Ext.Ajax.request({
		            		method:'POST',
		            		url:context + '/wellInformationManagerController/saveWellHandsontableData',
		            		success:function(response) {
		            			rdata=Ext.JSON.decode(response.responseText);
		            			if (rdata.success) {
		                        	Ext.MessageBox.alert("信息","保存成功");
		                            //保存以后重置全局容器
		                            wellInfoHandsontableHelper.clearContainer();
		                            CreateAndLoadWellInfoTable();
		                        } else {
		                        	Ext.MessageBox.alert("信息","数据保存失败");

		                        }
		            		},
		            		failure:function(){
		            			Ext.MessageBox.alert("信息","请求失败");
		                        wellInfoHandsontableHelper.clearContainer();
		            		},
		            		params: {
		                    	data: JSON.stringify(wellInfoHandsontableHelper.AllData),
		                    	orgId:orgId
		                    }
		            	}); 
		            } else {
		                if (!wellInfoHandsontableHelper.validresult) {
		                	Ext.MessageBox.alert("信息","数据类型错误");
		                } else {
		                	Ext.MessageBox.alert("信息","无数据变化");
		                }
		            }
	        	}else{
	        		Ext.MessageBox.alert("信息","请先选择组织节点");
	        	}
	            
	        }
	        
	      //修改井名
	        wellInfoHandsontableHelper.editWellName = function () {
	            //插入的数据的获取
	            if (wellInfoHandsontableHelper.editWellNameList.length>0 && wellInfoHandsontableHelper.validresult) {
	            	Ext.Ajax.request({
	            		method:'POST',
	            		url:context + '/wellInformationManagerController/editWellName',
	            		success:function(response) {
	            			rdata=Ext.JSON.decode(response.responseText);
	            			if (rdata.success) {
	                        	Ext.MessageBox.alert("信息","保存成功");
	                            wellInfoHandsontableHelper.clearContainer();
	                            CreateAndLoadWellInfoTable();
	                        } else {
	                        	Ext.MessageBox.alert("信息","数据保存失败");

	                        }
	            		},
	            		failure:function(){
	            			Ext.MessageBox.alert("信息","请求失败");
	                        wellInfoHandsontableHelper.clearContainer();
	            		},
	            		params: {
	                    	data: JSON.stringify(wellInfoHandsontableHelper.editWellNameList)
	                    }
	            	}); 
	            } else {
	                if (!wellInfoHandsontableHelper.validresult) {
	                	Ext.MessageBox.alert("信息","数据类型错误");
	                } else {
	                	Ext.MessageBox.alert("信息","无数据变化");
	                }
	            }
	        }
	        
	        
	      //删除的优先级最高
	        wellInfoHandsontableHelper.delExpressCount=function(ids) {
	            //传入的ids.length不可能为0
	            $.each(ids, function (index, id) {
	                if (id != null) {
	                	wellInfoHandsontableHelper.delidslist.push(id);
	                }
	            });
	            wellInfoHandsontableHelper.AllData.delidslist = wellInfoHandsontableHelper.delidslist;
	        }

	        //updatelist数据更新
	        wellInfoHandsontableHelper.screening=function() {
	            if (wellInfoHandsontableHelper.updatelist.length != 0 && wellInfoHandsontableHelper.delidslist.lentgh != 0) {
	                for (var i = 0; i < wellInfoHandsontableHelper.delidslist.length; i++) {
	                    for (var j = 0; j < wellInfoHandsontableHelper.updatelist.length; j++) {
	                        if (wellInfoHandsontableHelper.updatelist[j].id == wellInfoHandsontableHelper.delidslist[i]) {
	                            //更新updatelist
	                        	wellInfoHandsontableHelper.updatelist.splice(j, 1);
	                        }
	                    }
	                }
	                //把updatelist封装进AllData
	                wellInfoHandsontableHelper.AllData.updatelist = wellInfoHandsontableHelper.updatelist;
	            }
	        }
	        
	      //更新数据
	        wellInfoHandsontableHelper.updateExpressCount=function(data) {
	            if (JSON.stringify(data) != "{}") {
	                var flag = true;
	                //判断记录是否存在,更新数据     
	                $.each(wellInfoHandsontableHelper.updatelist, function (index, node) {
	                    if (node.id == data.id) {
	                        //此记录已经有了
	                        flag = false;
	                        //用新得到的记录替换原来的,不用新增
	                        wellInfoHandsontableHelper.updatelist[index] = data;
	                    }
	                });
	                flag && wellInfoHandsontableHelper.updatelist.push(data);
	                //封装
	                wellInfoHandsontableHelper.AllData.updatelist = wellInfoHandsontableHelper.updatelist;
	            }
	        }
	        
	        wellInfoHandsontableHelper.clearContainer = function () {
	        	wellInfoHandsontableHelper.AllData = {};
	        	wellInfoHandsontableHelper.updatelist = [];
	        	wellInfoHandsontableHelper.delidslist = [];
	        	wellInfoHandsontableHelper.insertlist = [];
	        	wellInfoHandsontableHelper.editWellNameList=[];
	        }
	        
	        return wellInfoHandsontableHelper;
	    }
};