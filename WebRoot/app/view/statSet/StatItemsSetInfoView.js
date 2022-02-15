var statSetHandsontableHelper=null;
Ext.define("AP.view.statSet.StatItemsSetInfoView", {
	extend : 'Ext.panel.Panel',
	alias : 'widget.statItemsSetInfoView',
	layout : 'fit',
	border : false,
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
		items:[{
			xtype: 'tabpanel',
            activeTab: 0,
            id: "statItemsSetTabpanel_id",
            layout: 'fit',
            border: false,
            tabRotation:0,
            tabPosition: 'left',
            tbar:[{
            	xtype:'label',
            	text:'统计配置',
            	margin:'0 10 0 0'
            },'->', {
                xtype: 'button',
                text:'保存',
                iconCls:'save',
                pressed: true,
                handler: function (v, o) {
                	statSetHandsontableHelper.saveData();
                }
            }],
            items:[{
            	title:'产量分布',
            	id:'prodDistStatItemsSetPanel',
            	layout: 'fit',
            	iconCls: 'select',
            	html: '<div id="prodDistStatItemsSetDiv_Id" style="width:100%;height:100%;"></div>'
            },{
            	title:'产量波动',
            	layout: 'fit',
            	id:'prodFlucStatItemsSetPanel',
            	html: '<div id="prodFlucStatItemsSetDiv_Id" style="width:100%;height:100%;"></div>'
            },{
            	title:'功率平衡',
            	layout: 'fit',
            	id:'powerBalanceStatItemsSetPanel',
            	html: '<div id="powerBalanceStatItemsSetDiv_Id" style="width:100%;height:100%;"></div>'
            },{
            	title:'电流平衡',
            	layout: 'fit',
            	id:'currentBalanceStatItemsSetPanel',
            	html: '<div id="currentBalanceStatItemsSetDiv_Id" style="width:100%;height:100%;"></div>'
            },{
            	title:'运行时率',
            	layout: 'fit',
            	id:'timeDistStatItemsSetPanel',
            	html: '<div id="timeDistStatItemsSetDiv_Id" style="width:100%;height:100%;"></div>'
            },{
            	title:'系统效率',
            	layout: 'fit',
            	id:'systemEffStatItemsSetPanel',
            	html: '<div id="systemEffStatItemsSetDiv_Id" style="width:100%;height:100%;"></div>'
            },{
            	title:'地面效率',
            	layout: 'fit',
            	id:'surfaceEffStatItemsSetPanel',
            	html: '<div id="surfaceEffStatItemsSetDiv_Id" style="width:100%;height:100%;"></div>'
            },{
            	title:'井下效率',
            	layout: 'fit',
            	id:'downholeEffStatItemsSetPanel',
            	html: '<div id="downholeEffStatItemsSetDiv_Id" style="width:100%;height:100%;"></div>'
            },{
            	title:'日用电量',
            	layout: 'fit',
            	id:'powerDistStatItemsSetPanel',
            	html: '<div id="powerDistStatItemsSetDiv_Id" style="width:100%;height:100%;"></div>'
            },{
            	title:'在线时率',
            	layout: 'fit',
            	id:'commDistStatItemsSetPanel',
            	html: '<div id="commDistStatItemsSetDiv_Id" style="width:100%;height:100%;"></div>'
            }],
    		listeners: {
                tabchange: function (tabPanel, newCard, oldCard,obj) {
//                	try{
//                		
//                		
//                	}catch(ex){
//                		alert(ex);
//                	}
                	oldCard.setIconCls("");
            		newCard.setIconCls("select");
            		if(statSetHandsontableHelper!=null){
            			statSetHandsontableHelper.clearContainer();
                		statSetHandsontableHelper.hot.destroy();
                		statSetHandsontableHelper=null;
            		}
            		oldCard.removeAll();
            		newCard.removeAll();
            		CreateAndLoadStatSetTable(newCard.id.replace(/Panel/g,"Div_Id"));
                },
                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                	CreateAndLoadStatSetTable("prodDistStatItemsSetDiv_Id");
                }
            }
		}]
		});
		me.callParent(arguments);
	}

});

function getStatSetType() {
	var type='prodDist';
	var tabPanel = Ext.getCmp("statItemsSetTabpanel_id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="prodDistStatItemsSetPanel"){//产量分布
		type='prodDist';
	}else if(activeId=="prodFlucStatItemsSetPanel"){//产量波动
		type='prodFluc';
	}else if(activeId=="powerBalanceStatItemsSetPanel"){//功率平衡
		type='powerBalance';
	}else if(activeId=="currentBalanceStatItemsSetPanel"){//电流平衡
		type='currentBalance';
	}else if(activeId=="timeDistStatItemsSetPanel"){//运行时率
		type='timeDist';
	}else if(activeId=="systemEffStatItemsSetPanel"){//系统效率
		type='systemEff';
	}else if(activeId=="surfaceEffStatItemsSetPanel"){//地面效率
		type='surfaceEff';
	}else if(activeId=="downholeEffStatItemsSetPanel"){//井下效率
		type='downholeEff';
	}else if(activeId=="powerDistStatItemsSetPanel"){//日用电量
		type='powerDist';
	}else if(activeId=="commDistStatItemsSetPanel"){//在线时率
		type='commDist';
	}else{
		type='prodDist';
	}
	return type;
};

function CreateAndLoadStatSetTable(divid){
	var type=getStatSetType();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/alarmSetManagerController/doStatItemsSetShow',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(divid==null||divid==undefined){
				var tabPanel = Ext.getCmp("statItemsSetTabpanel_id");
				divid = tabPanel.getActiveTab().id.replace(/Panel/g,"Div_Id");
			}
			statSetHandsontableHelper = StatSetHandsontableHelper.createNew(divid);
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
        	statSetHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
        	statSetHandsontableHelper.columns=Ext.JSON.decode(columns);
			statSetHandsontableHelper.createTable(result.totalRoot);
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			type: type,
            recordCount:30
        }
	});
};


var StatSetHandsontableHelper = {
	    createNew: function (divid) {
	        var statSetHandsontableHelper = {};
	        statSetHandsontableHelper.hot = '';
	        statSetHandsontableHelper.divid = divid;
	        statSetHandsontableHelper.validresult=true;//数据校验
	        statSetHandsontableHelper.colHeaders=[];
	        statSetHandsontableHelper.columns=[];
	        
	        statSetHandsontableHelper.AllData=[];
	        
	        statSetHandsontableHelper.createTable = function (data) {
	        	$('#'+statSetHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+statSetHandsontableHelper.divid);
	        	statSetHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: false
	                },
	                columns:statSetHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:statSetHandsontableHelper.colHeaders,//显示列头
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
	                	    	  
	                	      },
	                	      callback: function() {
	                	    	  
	                	      }
	                	    }
	                	}
	                },//右键菜单展示
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                	
	                },
	                afterDestroy: function() {
	                	
	                },
	                beforeRemoveRow: function (index, amount) {
	                	
	                },
	                afterChange: function (changes, source) {
	                	
	                }
	        	});
	        }
	        //保存数据
	        statSetHandsontableHelper.saveData = function () {
	        	var data=statSetHandsontableHelper.hot.getData();
	        	for(var i=0;i<data.length;i++){
	        		if(data[i][1]!=null&&data[i][2]!=null&&data[i][3]!=null){
	        			var ediedata="{\"statitem\":\""+data[i][1]+"\",\"downlimit\":\""+data[i][2]+"\",\"uplimit\":\""+data[i][3]+"\"}";
	        			statSetHandsontableHelper.AllData.push(Ext.JSON.decode(ediedata));
	        		}
	        	}
	        	var type=getStatSetType();
	        	if (JSON.stringify(statSetHandsontableHelper.AllData) != "[]" && statSetHandsontableHelper.validresult) {
	            	Ext.Ajax.request({
	            		method:'POST',
	            		url:context + '/alarmSetManagerController/doStatItemsSetSave',
	            		success:function(response) {
	            			rdata=Ext.JSON.decode(response.responseText);
	            			if (rdata.success) {
	                        	Ext.MessageBox.alert("信息","保存成功");
	                        	if(statSetHandsontableHelper!=null){
	                    			statSetHandsontableHelper.clearContainer();
	                        		statSetHandsontableHelper.hot.destroy();
	                        		statSetHandsontableHelper=null;
	                    		}
	                            CreateAndLoadStatSetTable();
	                        } else {
	                        	Ext.MessageBox.alert("信息","数据保存失败");

	                        }
	            		},
	            		failure:function(){
	            			Ext.MessageBox.alert("信息","请求失败");
	                        statSetHandsontableHelper.clearContainer();
	            		},
	            		params: {
	            			type:type,
	                    	data: JSON.stringify(statSetHandsontableHelper.AllData)
	                    }
	            	}); 
	            } else {
	                if (!statSetHandsontableHelper.validresult) {
	                	Ext.MessageBox.alert("信息","数据类型错误");
	                } else {
	                	Ext.MessageBox.alert("信息","无数据变化");
	                }
	            }
	        }
	        statSetHandsontableHelper.clearContainer = function () {
	        	statSetHandsontableHelper.AllData = [];
	        }
	        
	        return statSetHandsontableHelper;
	    }
};