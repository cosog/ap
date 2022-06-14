// ////// 操作公用方法//////////////
var sfycjhh=true;
copyright=cosog.string.copy+"&nbsp;<a href='"+cosog.string.linkaddress+"' target='_blank'>"+cosog.string.linkshow+"</a> ";
graghMinWidth = 300;
defaultWellComboxSize=10000;
comboxPagingStatus=0;//0-不分页  大于0分页
isShowMap=true;//是否显示地图 true-显示   false-不显示
recordCount=1000;//后台电子表格总行数
//定时器
var realtimeInterval,realtimeGraphicalInterval;
var MonitorRATast=Ext.TaskManager.newTask({
	interval: 1000,
	run: function () {
		var activeId = Ext.getCmp("frame_center_ids").getActiveTab().id;
		if (activeId == "monitorRA_MonitorRAPanel") {
			Ext.create('AP.store.monitorRA.MonitorRAAnalysisDataStore');
		}
	}
});
createPageBbar=function(store){
	     //分页工具栏
 var bbar = new Ext.PageNumberToolbar({
					    store:store,
						pageSize :defaultPageSize,	
						displayInfo : true,
						displayMsg : cosog.string.currentRecord,
						emptyMsg : cosog.string.nodataDisplay,
						prevText : cosog.string.lastPage,
						nextText : cosog.string.nextPage,
						refreshText : cosog.string.refresh,
						lastText : cosog.string.finalPage,
						firstText : cosog.string.firstPage,
						beforePageText : cosog.string.currentPage,
						afterPageText :cosog.string.gong
					});	
			return  bbar;
}
/**
 * xx.trim()
 */
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
/**
 * text:specialkey 回车事件 e:文本对象 name:刷新对象
 */
RefreshEnter = function(e, name) {
	if (e.getKey() == Ext.EventObject.ENTER) {
		reFreshg(name);
	}
	return false;
};

// 处理Tree树递归取值
var selectresult = [];
function selectEachTreeFn(chlidArray) {
	var ch_length;
	var ch_node = chlidArray.childNodes;
	if (isNotVal(ch_node)) {
		ch_length = ch_node.length;
	} else {
		ch_length = chlidArray.length;
	}
	if (ch_length > 0) {
		if (!Ext.isEmpty(chlidArray)) {
			Ext.Array.each(chlidArray, function(childArrNode, index, fog) {
						var x_node_seId = fog[index].data.orgId;
						selectresult.push(x_node_seId);
						// 递归
						if (childArrNode.childNodes != null) {
							selectEachTreeFn(childArrNode.childNodes);
						}
					});
		}
	} else {
		if (isNotVal(chlidArray)) {
			var x_node_seId = chlidArray.data.orgId;
			selectresult.push(x_node_seId);
		}
	}
	return selectresult.join(",");
};

//处理Tree树text递归取值
var selectReeTextRsult = [];
function selectEachTreeText(chlidArray) {
	var ch_length;
	var ch_node = chlidArray.childNodes;
	if (isNotVal(ch_node)) {
		ch_length = ch_node.length;
	} else {
		ch_length = chlidArray.length;
	}
	if (ch_length > 0) {
		if (!Ext.isEmpty(chlidArray)) {
			Ext.Array.each(chlidArray, function(childArrNode, index, fog) {
						var x_node_seId = fog[index].data.text;
						selectReeTextRsult.push(""+x_node_seId+"");
						// 递归
						if (childArrNode.childNodes != null) {
							selectEachTreeText(childArrNode.childNodes);
						}
					});
		}
	} else {
		if (isNotVal(chlidArray)) {
			var x_node_seId = chlidArray.data.text;
			selectReeTextRsult.push(""+x_node_seId+"");
		}
	}
	return selectReeTextRsult.join(",");
};

getNoilTabId = function() {
	var tab_ = Ext.getCmp("frame_center")
	var tab_s = tab_.getActiveTab();
	return tab_s.id;
}
ExtDelspace_ObjectInfo=function(space,grid_id,row,data_id,action_name){	
           // 删除条件
        var deletejson = [];
      
		Ext.Array.each(row, function(name, index, countriesItSelf) {
			deletejson.push(row[index].get(data_id));
		}) 
		var delparamsId=""+deletejson.join(","); 
		 
        // AJAX提交方式
		Ext.Ajax.request({
					url : context+'/'+space+'/'+action_name+'',					
					method : "POST",
					// 提交参数
					params : {
						paramsId : delparamsId
					},
					success :  function(response) {					 
						var result =  Ext.JSON.decode(response.responseText);
						if(result.flag ==true){ 
							//刷新Grid
							var g_spl=grid_id.split(",");
							if(isNotBank(g_spl)){
							 for(var g=0;g<g_spl.length;g++){
							    Ext.getCmp(g_spl[g]).getStore().load();
							 } 
							} 
					 	    Ext.Msg.alert('提示', "【<font color=blue>成功删除</font>】，" + row.length + "条数据信息。");
						}
					 	if(result.flag ==false){		 		 
					 		Ext.Msg.msg('提示', "<font color=red>SORRY！删除失败。</font>");
					 	}
					 	  
					},
					failure : function() {
						    Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");			    
					}
				});
	  return false;
    };
/**
 * 判断是否为空值
 */
isNullVal = function(val) {
	var result = "";
	if (val == "" || val == "null" || val == "undefined" || val == undefined || val == null) {
		result = '';
	} else {
		result = val;
	}
	return result;
}
/**
 * 是否为空值
 */
isNotVal = function(val) {
	var result = false;
	if (val == "" || val == "null" || val == "undefined" || val == undefined || val == null) {
		result = false;
	} else {
		result = true;
	}
	return result;
}

/**
 * 是否为数值
 */
isNumber = function(val) {
	var regPos = /^\d+(\.\d+)?$/; //非负浮点数
    var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
    if(regPos.test(val) || regNeg.test(val)){
        return true;
    }else{
        return false;
    }
}
function isNumber2(val) {
	return !isNaN(Number(val))
}
/**
 * 判断字符串是否已特定字符结尾
 */
function stringEndWith(sourceStr,endStr){
    var d=sourceStr.length-endStr.length;
    return (d>=0&&sourceStr.lastIndexOf(endStr)==d);
}

/**
 * object 转 stirng
 * 
 * @param o
 * @return
 */
function obj2str(o) {
	var r = [];
	if (typeof o == "string" || o == null) {
		return o;
	}
	if (typeof o == "object") {
		if (!o.sort) {
			r[0] = "{"
			for (var i in o) {
				r[r.length] = i;
				r[r.length] = ":";
				r[r.length] = obj2str(o[i]);
				r[r.length] = ",";
			}
			r[r.length - 1] = "}"
		} else {
			r[0] = "["
			for (var i = 0; i < o.length; i++) {
				r[r.length] = obj2str(o[i]);
				r[r.length] = ",";
			}
			r[r.length - 1] = "]"
		}
		return r.join("");
	}
	return o.toString();
}
/**
 * string 转 object
 * 
 * @param json
 * @return
 */
function strToObj(json) {
	return eval("(" + json + ")");
}

/**
 * 对复选框的树，返回选中的节点
 * 
 * @param {Object}
 *            tree 选择的树
 * @param {Object}
 *            from 返回值的节点属性
 * @param {Object}
 *            split 分割字符串
 * @return {TypeName}
 */
function getChecked(tree, from, split) {
	var records = tree.getView().getChecked();
	names = [];
	Ext.Array.each(records, function(rec) {
				names.push(rec.get(from));
			});
	return names.join(split);
}
/**
 * 得到选中的节点数
 * 
 * @param {Object}
 *            tree
 * @return {TypeName}
 */
function getCheckedCount(tree) {
	var returnValue = 0;
	var records = tree.getView().getChecked();
	Ext.Array.each(records, function(rec) {
				returnValue++;
			});
	return returnValue;
}
/**
 * 判断是否为叶子节点
 * 
 * @param {Object}
 *            rec
 * @return {TypeName}
 */
function isLeaf(rec) {
	return rec.get('leaf');
}
/**
 * 得到父节点
 * 
 * @param {Object}
 *            rec
 * @param {Object}
 *            from
 * @return {TypeName}
 */
function getParentNode(rec, from) {
	return rec.parentNode.get(from);
}
/**
 * 得到选中节点的一级节点
 * 
 * @param {Object}
 *            rec
 * @param {Object}
 *            from
 * @param {Object}
 *            split
 * @return {TypeName}
 */
function getSonNode(rec, from, split) {
	var records = rec.childNodes;
	results = [];
	Ext.Array.each(records, function(rec) {
				results.push(rec.get(from));
			});
	return results.join(split);
}

// 验证对象不能空值
// 解决对象信息空值不存等情况
isNotBank = function(val) {
	if (val != null && val != "" && val != "null") {
		return true;
	} else {
		return false;
	}
};

// 将时间转化为 2011-08-20 00:00:00 格式
// 解决Ext4的formPanel通过grid的store查询问题
function dateFormat(value) {
	var val = "";
	// value='2013-11-04 08:40:01';
	if (value != null) {
		if (Ext.isIE) {
			return value;
		} else {
			return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
		}
	} else {
		return val;
	}
};
function dateTimeFormat(value) {
	var val = "";
	// value='2013-11-04 08:40:01';
	if (value != null) {
		if (Ext.isIE) {
		return	Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			//return value;
		} else {
			return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
		}
	} else {
		return val;
	}
};
function dateFormatNotDa(value) {
	if (value != null) {
		return Ext.Date.format(new Date(value), 'Y-m-d');
	} else {
		return null;
	}
};
// 验证规则
Ext.form.VTypes["loginnum_z"] = /^[A-Za-z]{1,}$/;
Ext.form.VTypes["loginnum_x"] = /^[0-9]{1,}$/;
Ext.form.VTypes["loginnum_Val"] = /^[A-Za-z0-9]{6,}$/;
// 提示信息
Ext.form.VTypes["loginnum_Text"] = "密码必须是：数字和字符的组合,长度大于6~位。";
// 做验证时要执行的函数，根据函数的返回值来判断验证成功与否
Ext.form.VTypes["loginnum_"] = function(v) {
	if (!Ext.form.VTypes["loginnum_Val"].test(v)) {
		return false;
	}
	if (Ext.form.VTypes["loginnum_z"].test(v)) {
		return false;
	}
	if (Ext.form.VTypes["loginnum_x"].test(v)) {
		return false;
	}
	return true;
};
/**
 * 时间格式处理-不同浏览器时间显示问题
 * 
 * @param value
 * @returns {String}
 */
function getFormatDate(value) {
	var result = "";
	if (value != null && value != "" && value != "null") {

		if (Ext.isIE) {
			var ie_val = value.split(" ");
			result = ie_val[0];

		} else {
			var ie_val = dateFormatNotDa(value).split(" ");
			result = ie_val[0];

		}
	}
	return result;
}
// Window 公共关闭方法
closeWindow = function(o) {
	var winobj = Ext.getCmp(o.closewin);
	if (isNotBank(winobj)) {
		winobj.close();
	} else {
		Ext.Msg.alert("提示", "<font color=red>异常</font>：关闭WINDOW窗体,给出的对象不存在。")
	}
};

// GridPanel 公共刷新方法
// params :gridpanel_id 名称
reFreshg = function(grid_id) {
	Ext.getCmp(grid_id).getStore().load();
};

// 等待信息框
LoadingWin = function(msg) {
	Ext.MessageBox.show({
				msg : '<div style="padding-top:20px">' + msg + ', 请稍后...</div>',
				progressText : '加载中...',
				width : 300,
				wait : true,
				waitConfig : {
					interval : 200
				},
				icon : 'ext-mb-download'
			});
	setTimeout(function() {
				Ext.MessageBox.close();
			}, 3000);
}

// GridPanel 公共删除方法
// params :grid的Id,row 选中行,data_id 删除对象的Id,刷新的action 名称
ExtDel_ObjectInfo = function(grid_id, row, data_id, action_name) {
	// 删除条件
	var deletejson = [];
	Ext.Array.each(row, function(name, index, countriesItSelf) {
		if(row[index].get(data_id)>0){
			deletejson.push(row[index].get(data_id));
		}		
	});
	if(deletejson.length>0){
		var delparamsId = "" + deletejson.join(",");
		Ext.Ajax.request({
			url : action_name,
			method : "POST",
			params : {
				paramsId : delparamsId
			},
			success : function(response) {
				var result = Ext.JSON.decode(response.responseText);
				Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'"
						+ context
						+ "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
				if (result.flag == true) {
					Ext.Msg.alert('提示', "【<font color=blue>成功删除</font>】"+ row.length + "条数据信息。");
				}
				if (result.flag == false) {
					Ext.Msg.alert('提示', "<font color=red>SORRY！删除失败。</font>");
				}
				if(grid_id=="OrgInfoTreeGridView_Id"){
					var store=Ext.getCmp(grid_id).getStore()
	                store.proxy.extraParams.tid = 0;
	                store.load();
				}
				Ext.getCmp(grid_id).getStore().load();
			},
			failure : function() {
				Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
			}
		});
	}else{
		Ext.Msg.alert('提示', "<font color=red>所选属性无效，删除失败。</font>");
	}
	return false;
};

var clearOrgHiddenValue = function() {
	var obj = Ext.getCmp("orgName_Parent_Id");
	if (obj != undefined) {
		obj.setValue("0");
	}
	var obj_module = Ext.getCmp("mdName_Parent_Id");
	if (obj_module != undefined) {
		obj_module.setValue("0");
	}
}

function createNewPanel(panelUrl) {
	var panelUrl = Ext.create(panelUrl);
	return panelUrl;
}

function showPanel(o) {
	var tabPanel = Ext.getCmp("frame_center_ids");// 获取到中央面板对象
	tabPanel.removeAll();// 清除掉以前的grid，
	var uss_ = Ext.create(o);
	tabPanel.add(uss_);// 重新添加一个新的grid
}

iconHistoryQueryDetailsData = function(value, e, o) {
	var recordId=o.data.id;
	var wellName=o.data.wellName;
	var deviceId=o.data.deviceId;
	var resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" onclick=callBackHistoryData(\""+recordId+"\",\""+deviceId+"\",\""+wellName+"\")>详细...</a>";
	return resultstring;
}

iconDiagnoseTotalCurve = function(value, e, o) {
	var itemCode = o.data.itemCode;
	var item=o.data.item;
	var index=o.internalId%2;
	var resultstring = "<img src='"
			+ context
			+ "/images/icon/curvetest"+index+".png' style='cursor:pointer' onclick=callBackTotalHistoryData(\""+item+"\",\""+itemCode+"\") />";
	return resultstring;
}

var callBackGraphical = function(type,id) {
    Ext.getCmp('graphicalOnclickType_Id').setValue(type);
    Ext.getCmp('graphicalOnclick_Id').setValue(id);
    var GraphicalOnclickWindow=Ext.create("AP.view.graphicalQuery.GraphicalOnclickWindow", {
				    html:'<div id='+type+id+' style="width:100%;height:100%;"></div>' // 图形类型+数据id作为div的id
			   });
    GraphicalOnclickWindow.show();
}

var callBackHistoryData = function(recordId,deviceId,wellName) {
	var HistoryQueryDataDetailsWindow = Ext.create("AP.view.historyQuery.HistoryQueryDataDetailsWindow");
	Ext.getCmp("HistoryQueryDataDetailsWindowRecord_Id").setValue(recordId);
	Ext.getCmp("HistoryQueryDataDetailsWindowDeviceId_Id").setValue(deviceId);
	Ext.getCmp("HistoryQueryDataDetailsWindowDeviceName_Id").setValue(wellName);
	HistoryQueryDataDetailsWindow.show();
}

function openExcelWindow(url) {
	// window.navigate(url);
	document.location.href = url;
}
function exportExcelWindow(url) {
	var appWindow = window.open(url); // 调action得到数据生成execl格式的数据，response发往前台
	appWindow.focus();
}

function exportGridPanelExcelData(gridId,url,fileName,title){
	var store=Ext.getCmp(gridId).getStore();
	var total=store.getCount();
	var jsonArray = [];
	for(var i=0;i<total;i++){
		jsonArray.push(store.getAt(i).data);
	}
	var data=JSON.stringify(jsonArray);
    var fields = "";
    var heads = "";
    var gridPanel = Ext.getCmp(gridId);
    var items_ = gridPanel.items.items;
    if(items_.length==1){//无锁定列时
    	var columns_ = gridPanel.columns;
    	Ext.Array.each(columns_, function (name,
                index, countriesItSelf) {
                var locks = columns_[index];
                if (index > 0 && locks.hidden == false) {
                    fields += locks.dataIndex + ",";
                    heads += locks.text + ",";
                }
            });
    }else{
    	Ext.Array.each(items_, function (name, index,
                countriesItSelf) {
                var datas = items_[index];
                var columns_ = datas.columns;
                if (index == 0) {
                    Ext.Array.each(columns_, function (name,
                        index, countriesItSelf) {
                        var locks = columns_[index];
                        if (index > 0 && locks.hidden == false) {
                            fields += locks.dataIndex + ",";
                            heads += locks.text + ",";
                        }
                    });
                } else {
                    Ext.Array.each(columns_, function (name,
                        index, countriesItSelf) {
                        var headers_ = columns_[index];
                        if (headers_.hidden == false) {
                            fields += headers_.dataIndex + ",";
                            heads += headers_.text + ",";
                        }
                    });
                }
            });
    }
    if (isNotVal(fields)) {
        fields = fields.substring(0, fields.length - 1);
        heads = heads.substring(0, heads.length - 1);
    }
    fields = "id," + fields;
    heads = "序号," + heads;
    var param = "&heads=" + heads +"&fields=" + fields+"&data=" + data+"&fileName=" + fileName+"&title=" + title;
    param=param.replace(/#/g,"%23").replace(/%/g,"%25");
    openExcelWindow(url+'?flag=true&' + param);
}

onEnterKeyDownFN = function(field, e, panelId) {
	if (e.getKey() == e.ENTER) {
		// var filed_ = field.rawValue;
		Ext.getCmp(panelId).getStore().load();
		field.collapse();
	}
}
onTreeEnterKeyDownFN = function(field, e, panelId) {
	if (e.getKey() == e.ENTER) {
		// var filed_ = field.rawValue;
		Ext.getCmp(panelId).store.load();
		// field.collapse();
	}
}

var mychart = "";
function initCurveChartFn(catagories, series, tickInterval, divId, title, ytitle, ytitle1) {
	mychart = new Highcharts.Chart({
				chart : {
					type : 'spline',
					renderTo : divId,
					shadow : true,
					borderWidth : 0,
					zoomType : 'xy'
				},
				credits : {
					enabled : false
				},
				title : {
					text : title,
					x : -20
				},
				xAxis : {
					categories : catagories,
					tickInterval : tickInterval,
					title : {
						text : cosog.string.date
					}
				},
				yAxis : [{
							lineWidth : 1,
							min:0,
							title : {
								text : ytitle,
								style : {
									color : '#000000',
									fontWeight : 'bold'
								}
							},
							labels : {
								formatter : function() {
									return Highcharts.numberFormat(this.value,
											2);
								}
							},
							plotLines : [{
										value : 0,
										width : 1,
										zIndex:2,
										color : '#808080'
									}]
						}, {
							lineWidth : 1,
							min:0,
							max:100,
							opposite : true,
							labels : {
								formatter : function() {
									return Highcharts.numberFormat(this.value,
											2);
								}
							},
							title : {
								text : ytitle1,
								style : {
									color : '#000000',
									fontWeight : 'bold'
								}
							}
						}],
				tooltip : {
					crosshairs : true,
					enabled : true,
					style : {
						color : '#333333',
						fontSize : '12px',
						padding : '8px'
					},
					formatter : function() {
						return '<b>' + this.series.name + '</b><br/>' + this.x
								+ ': ' + this.y;
					},
					valueSuffix : ''
				},
//				  exporting: {
//			            buttons: {
//			                contextButton: {
//			                    menuItems: [{
//			                        separator: true
//			                    }]
//			                    .concat(Highcharts.getOptions().exporting.buttons.contextButton.menuItems)
//			                    .concat([{
//			                        separator: true
//			                    }
//			                    ])
//			                }
//			            }
//			        },
				exporting:{    
                    enabled:true,    
                    filename:'class-booking-chart',    
                    url:context + '/exportHighcharsPicController/export'
               },
				plotOptions : {
					 spline: {  
				            lineWidth: 1,  
				            fillOpacity: 0.3,  
				             marker: {  
				             enabled: true,  
				              radius: 3,  //曲线点半径，默认是4
				                states: {  
				                   hover: {  
				                        enabled: true,  
				                        radius: 6
				                    }  
				                }  
			            },  
			            shadow: true  
			        } 
				},
				legend : {
					layout : 'vertical',
					align : 'right',
					verticalAlign : 'middle',
					borderWidth : 1
				},
				series : series
			});
}
function initCurveChartFn1(catagories, series, tickInterval, divId, title, ytitle, ytitle1) {
	mychart = new Highcharts.Chart({
				chart : {
					type : 'spline',
					renderTo : divId,
					shadow : true,
					borderWidth : 0,
					zoomType : 'xy'
				},
				credits : {
					enabled : false
				},
				title : {
					text : title,
					x : -20
				},
				xAxis : {
					categories : catagories,
					tickInterval : tickInterval,
					title : {
						text : cosog.string.date
					}
				},
				yAxis : [{
							lineWidth : 1,
							min:0,
							title : {
								text : ytitle,
								style : {
									color : '#000000',
									fontWeight : 'bold'
								}
							},
							labels : {
								formatter : function() {
									return Highcharts.numberFormat(this.value,
											2);
								}
							},
							plotLines : [{
										value : 0,
										width : 1,
										zIndex:2,
										color : '#808080'
									}]
						}, {
							lineWidth : 1,
							min:0,
							max:1,
							opposite : true,
							labels : {
								formatter : function() {
									return Highcharts.numberFormat(this.value,
											2);
								}
							},
							title : {
								text : ytitle1,
								style : {
									color : '#000000',
									fontWeight : 'bold'
								}
							}
						}],
				tooltip : {
					crosshairs : true,
					enabled : true,
					style : {
						color : '#333333',
						fontSize : '12px',
						padding : '8px'
					},
					formatter : function() {
						return '<b>' + this.series.name + '</b><br/>' + this.x
								+ ': ' + this.y;
					},
					valueSuffix : ''
				},
//				  exporting: {
//			            buttons: {
//			                contextButton: {
//			                    menuItems: [{
//			                        separator: true
//			                    }]
//			                    .concat(Highcharts.getOptions().exporting.buttons.contextButton.menuItems)
//			                    .concat([{
//			                        separator: true
//			                    }
//			                    ])
//			                }
//			            }
//			        },
				exporting:{    
                    enabled:true,    
                    filename:'class-booking-chart',    
                    url:context + '/exportHighcharsPicController/export'
               },
				plotOptions : {
					 spline: {  
				            lineWidth: 1,  
				            fillOpacity: 0.3,  
				             marker: {  
				             enabled: true,  
				              radius: 3,  //曲线点半径，默认是4
				                states: {  
				                   hover: {  
				                        enabled: true,  
				                        radius: 6
				                    }  
				                }  
			            },  
			            shadow: true  
			        } 
				},
				legend : {
					layout : 'vertical',
					align : 'right',
					verticalAlign : 'middle',
					borderWidth : 1
				},
				series : series
			});
}

// 生产曲线初始化函数
function initCurveChart(years, values, tickInterval, divId) {
	mychart = new Highcharts.Chart({
				chart : {
					renderTo : divId,
					type : 'spline',
					shadow : true,
					//alignTicks: false,
					borderWidth : 0,
					zoomType : 'xy'
				},
				credits : {
					enabled : false
				},
				title : {
					text :cosog.string.clqx,
					x : -20
					// center
				},
				colors : ['#800000',// 红
						'#008C00',// 绿
						'#000000',// 黑
						'#0000FF',// 蓝
						'#F4BD82',// 黄
						'#FF00FF'// 紫
				],
				xAxis : {
					categories : years,
					tickInterval : tickInterval,
					title : {
						text : cosog.string.date
					}
				},
				yAxis : [{
							lineWidth : 1,
							min:0,
							//max:200,
							title : {
								text : cosog.string.cl,
								style : {
									color : '#000000',
									fontWeight : 'bold'
								}
							},
							labels : {
								formatter : function() {
									return Highcharts.numberFormat(this.value,
											2);
								}
							},
							plotLines : [{
										value : 0,
										width : 1,
										zIndex:2,
										color : '#808080'
									}]
						}, {
							lineWidth : 1,
							min:0,
							max:100,
							opposite : true,
							labels : {
								formatter : function() {
									return Highcharts.numberFormat(this.value,
											2);
								}
							},
							title : {
								text : cosog.string.hsl,
								style : {
									color : '#000000',
									fontWeight : 'bold'
								}
							}
						}],
				tooltip : {
					crosshairs : true,
					enabled : true,
					style : {
						color : '#333333',
						fontSize : '12px',
						padding : '8px'
					},
					formatter : function() {
						return '<b>' + this.series.name + '</b><br/>' + this.x
								+ ': ' + this.y;
					},
					valueSuffix : ''
				},
//				  exporting: {
//			            buttons: {
//			                contextButton: {
//			                    menuItems: [{
//			                        separator: true
//			                    }]
//			                    .concat(Highcharts.getOptions().exporting.buttons.contextButton.menuItems)
//			                    .concat([{
//			                        separator: true
//			                    }
//			                    ])
//			                }
//			            }
//			        },
				exporting:{    
                    enabled:true,    
                    filename:'class-booking-chart',    
                    url:context + '/exportHighcharsPicController/export'
               },
				plotOptions : {
					 spline: {  
				            lineWidth: 1,  
				            fillOpacity: 0.3,  
				             marker: {  
				             enabled: true,  
				              radius: 3,  //曲线点半径，默认是4
                             //symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
				                states: {  
				                   hover: {  
				                        enabled: true,  
				                        radius: 6
				                    }  
				                }  
			            },  
			            shadow: true  
			        } 
				},
				legend : {
					layout : 'vertical',
					align : 'right',
					verticalAlign : 'middle',
					borderWidth : 1
				},
				series : values
			});
}
/**
 * Curve chart
 */
CurveVFnChartFn = function(store, divId) {
	var items = store.data.items;
	var tickInterval = 1;
	tickInterval = Math.floor(items.length / 4) + 1;
	var catagories = "[";
	for (var i = 0; i < items.length; i++) {
		catagories += "\"" + getFormatDate(items[i].data.jssj) + "\"";
		if (i < items.length - 1) {
			catagories += ",";
		}
	}
	catagories += "]";
	var legendName = [cosog.string.rpzsl, cosog.string.sjrzsl];
	var series = "[";
	for (var i = 0; i < legendName.length; i++) {
		series += "{\"name\":\"" + legendName[i] + "\",";
		series += "\"data\":[";
		for (var j = 0; j < items.length; j++) {
			if (i == 0) {
				series += items[j].data.rpzrl;
			} else if (i == 1) {
				series += items[j].data.sjrzrl;
			}
			if (j != items.length - 1) {
				series += ",";
			}
		}
		series += "]}";
		if (i != legendName.length - 1) {
			series += ",";
		}
	}
	series += "]";
	var cat = Ext.JSON.decode(catagories);
	var ser = Ext.JSON.decode(series);
	initWaterCurveChart(cat, ser, tickInterval, divId);
}

// 后台退出函数
function backLoginOut() {
	Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'"
			+ context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
	Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'"
			+ context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
	Ext.Msg.confirm("提示", "是否确定退出后台管理系统？", function(btn) {
		if (btn == "yes") {
			LoadingWin("正在退出");
			// 动态返回当前用户拥有哪些角色信息
			// 动态返回当前用户拥有哪些角色信息
			Ext.Ajax.request({
						method : 'POST',
						url : context
								+ '/userLoginManagerController/userExit',
						success : function(response, opts) {
							// 处理后
							var obj = Ext.decode(response.responseText);
							if (isNotVal(obj)) {
								if (obj.flag) {
									window.location.href = context
											+ "/login/toBackLogin";
								}

							}
						},
						failure : function(response, opts) {
							Ext.Msg.alert("信息提示", "后台获取数据失败！");
						}
					});
		}
	});
}

var userLoginOut = function() {
	Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'"
			+ context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
	Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'"
			+ context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
	Ext.Msg.confirm("提示", "是否确定退出本系统？", function(btn) {
		if (btn == "yes") {
			LoadingWin("正在退出");
			// 动态返回当前用户拥有哪些角色信息
			Ext.Ajax.request({
						method : 'POST',
						url : context+ '/userLoginManagerController/userExit',
						success : function(response, opts) {
							// 处理后
							var obj = Ext.decode(response.responseText);
							if (isNotVal(obj)) {
								if (obj.flag) {
									window.location.href = context+ "/login/toLogin";
								}

							}
						},
						failure : function(response, opts) {
							Ext.Msg.alert("信息提示", "后台获取数据失败！");
						}
					});
		}
	});
}


function senfe(o, a, b, c, d) {
	var tab = document.getElementById(o);
	var t = tab.getElementsByTagName("tr");
	for (var i = 0; i < t.length; i++) {
		t[i].style.backgroundColor = (t[i].sectionRowIndex % 2 == 0) ? a : b;
		t[i].onclick = function() {
			if (this.x != "1") {
				this.x = "1";// 本来打算直接用背景色判断，FF获取到的背景是RGB值，不好判断
				this.style.backgroundColor = d;
			} else {
				this.x = "0";
				this.style.backgroundColor = (this.sectionRowIndex % 2 == 0)
						? a
						: b;
			}
		}
		t[i].onmouseover = function() {
			if (this.x != "1")
				this.style.backgroundColor = c;
		}
		t[i].onmouseout = function() {
			if (this.x != "1")
				this.style.backgroundColor = (this.sectionRowIndex % 2 == 0)
						? a
						: b;
		}
	}
}

// 报警设置铃铛的切换
alarmSetType = function(val) {
	if (val == 0) {
		return '<img src="' + context
				+ '/images/icon/alarm-greed.png" style="cursor:pointer"/>';
	} else {
		return '<img src="' + context
				+ '/images/icon/alarm-red.png" style="cursor:pointer"/>';
	}
}
alarmType = function(val) {
	// alert(val);
	if (val == 0) {
		return "<img src='" + context
				+ "/images/icon/normal.png' style='cursor:pointer'/>";
	} else {
		return "<img src='" + context
				+ "/images/icon/exception.png' style='cursor:pointer'/>";
	}
}

alarmLevelColor = function(val) {
	// alert(val);
	if (val == 0) {
		return "<img src='" + context
				+ "/images/icon/alarmcolor/exception0.png' style='cursor:pointer'/>";
	}else if(val==100){
		return "<img src='" + context
		+ "/images/icon/alarmcolor/exception1.png' style='cursor:pointer'/>";
	}else if(val==200){
		return "<img src='" + context
		+ "/images/icon/alarmcolor/exception2.png' style='cursor:pointer'/>";
	}else if(val==300){
		return "<img src='" + context
		+ "/images/icon/alarmcolor/exception3.png' style='cursor:pointer'/>";
	} 
	else {
		return "<img src='" + context
		+ "/images/icon/alarmcolor/exception4.png' style='cursor:pointer'/>";
	}
}

// 保留2为小数
function changeTwoDecimal(x) {
	var f_x = x;
	if (isNaN(f_x)) {
		// alert('function:changeTwoDecimal->parameter error');
		return "";
	}
	if (f_x === "") {
		f_x = "";
	} else {
		var f_x = Math.round(x * 100) / 100;
	}
	return f_x;
}
// 取值判空后返回值
function obtainParams(id) {
	var result = Ext.getCmp(id);
	if (!Ext.isEmpty(result)) {
		result = result.getValue();
	}
	return result;
}
// 拼接标准功图及功图id
function constructLayerIDs(idStr_, bzgtbhs_) {
	var ids_ = "";
	var firstId_ = idStr_.split(",");
	if (isNotVal(bzgtbhs_) && bzgtbhs_[0] != firstId_[0]) {
		ids_ = bzgtbhs_[0] + ',' + idStr_;
	} else {
		ids_ = idStr_;
	}
	return ids_;
}

// 字符串去重
function distinctGTBHStr(val) {
	var datas = val.split(",");
	var str = datas[0];
	for (var i = 1; i < datas.length; i++) {
		if (datas[i] != datas[0]) {
			str += "," + datas[i];
		}
	}
	return str;
}
// 级联
treeComBox = function(node, checked) {
	if (!checked) {

		// 展开选中的节点
		node.expand();
		// 设置节点选中
		node.checked = false;
		// 设置选择节点下所有子节点
		node.eachChild(function(child) {
					child.set('checked', false);
					var childArray = child.childNodes;
					childTree(childArray, false);
				});
		// 如果所有的子节点被选中，父节点也应该被选中
		parentTree(node, false);

	} else {
		// 展开选中的节点
		node.expand();
		// 设置节点选中
		node.checked = true;
		// 设置选择节点下所有子节点
		node.eachChild(function(child) {
					child.set('checked', true);
					var childArray = child.childNodes;
					childTree(childArray, true);
				});
		// 如果所有的子节点被选中，父节点也应该被选中
		parentTree(node, true);

	};
};
// 递归子节点
function childTree(chlidArray, checked) {
	if (!Ext.isEmpty(chlidArray)) {
		Ext.Array.each(chlidArray, function(childArrNode, index, fog) {
					childArrNode.set('checked', checked);
					if (childArrNode.childNodes != null) {
						childTree(childArrNode.childNodes, checked);
					}
				});
	}
};
// 递归父节点
function parentTree(rootTree, checked) {
	var node = rootTree.parentNode;
	if (node.raw != null && node.raw != null) {
		if (!Ext.isEmpty(node) && node.data.mdId != "0") {
			// 当前节点是否选择状态，响效父节点
			if (checked) {
				node.data.checked = checked;
				// node.set('checked', checked);
				// node('checked', true);
				// node.set({checked:checked});
				// node.set({checked:checked});
				// node.set('checked', true);
				node.set('loaded', true);
				if (node != null) {
					parentTree(node, checked);
				}
			} else {
				var falsestr = [];
				var arr = node.childNodes;
				Ext.Array.each(arr, function(arrNode, index, fog) {
							var chfalse = arr[index].data.checked;
							if (!chfalse) {
								falsestr.push(chfalse);
							}
						});
				if (falsestr.length == arr.length) {
					node.data.checked = checked;
					// node.set('checked', checked);
					// node('checked', true);
					// node.set('checked', true);
					// node.set({checked:checked});
					node.set('loaded', true);
					if (node != null) {
						parentTree(node, checked);
					}
				}
			}
		}
	}
};

function checkedNode(node, checked) {
	node.expand();
	node.checked = checked;
	node.eachChild(function(child) {
				child.set('checked', checked);
				checkedNode(child, checked);
				child.fireEvent('checkchange', child, checked);
			});
}

// 添加监听 设置树的节点选择的级联关系
var listenerCheck = function(node, checked) {
	childHasChecked(node, checked);
	var parentNode = node.parentNode;
	if (parentNode != null) {
//		parentCheck(parentNode, checked);
	}
};
// 级联选中父节点
var parentCheck = function(node, checked) {
	var childNodes = node.childNodes;
	for (var i = 0; i < childNodes.length; i++) {
		if (childNodes[i].get('checked')) {
			node.set('checked', checked);
			continue;
		} else {
			node.set('checked', false);
			break;
		}
	};
	var parentNode = node.parentNode;
	if (parentNode != null) {
		parentCheck(parentNode, checked);
	}
}
// 级联选择子节点
var childHasChecked = function(node, checked) {
	node.cascadeBy(function(child) {
				child.set("checked", checked)
			});
}

color16ToRgba = function(sColor,Opacity){
    var sColor = sColor.toLowerCase();
    //十六进制颜色值的正则表达式
    var reg = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/;
    // 如果是16进制颜色
    if (sColor && reg.test(sColor)) {
        if (sColor.length === 4) {
            var sColorNew = "#";
            for (var i=1; i<4; i+=1) {
                sColorNew += sColor.slice(i, i+1).concat(sColor.slice(i, i+1));    
            }
            sColor = sColorNew;
        }
        //处理六位的颜色值
        var sColorChange = [];
        for (var i=1; i<7; i+=2) {
            sColorChange.push(parseInt("0x"+sColor.slice(i, i+2)));    
        }
        return "rgba(" + sColorChange.join(",") + ","+Opacity+")";
    }
    return sColor;
};


 adviceColor = function(val,o,p,e) {
// 	alert(p.data.bjbz);o.style='background-color:#FF0000;color:#FFFFFF;';
	if(val==undefined||val=="undefined"){
		 val="";
	}
	var AlarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
 	var alarmLevel=p.data.resultAlarmLevel;
 	var tipval=val;
 	if(p.data.resultString!=undefined&&p.data.resultString!=''){
 		tipval=p.data.resultString;
 	}
 	var BackgroundColor='#FFFFFF';
 	var Colorr='#000000';
 	var Opacity=1;
 	if (alarmLevel == 0) {
 		BackgroundColor='#'+AlarmShowStyle.Data.Normal.BackgroundColor;
 		Colorr='#'+AlarmShowStyle.Data.Normal.Color;
 		Opacity=AlarmShowStyle.Data.Normal.Opacity;
	}else if (alarmLevel == 100) {
		BackgroundColor='#'+AlarmShowStyle.Data.FirstLevel.BackgroundColor;
 		Colorr='#'+AlarmShowStyle.Data.FirstLevel.Color;
 		Opacity=AlarmShowStyle.Data.FirstLevel.Opacity;
	}else if (alarmLevel == 200) {
		BackgroundColor='#'+AlarmShowStyle.Data.SecondLevel.BackgroundColor;
 		Colorr='#'+AlarmShowStyle.Data.SecondLevel.Color;
 		Opacity=AlarmShowStyle.Data.SecondLevel.Opacity;
	}else if (alarmLevel == 300) {
		BackgroundColor='#'+AlarmShowStyle.Data.ThirdLevel.BackgroundColor;
 		Colorr='#'+AlarmShowStyle.Data.ThirdLevel.Color;
 		Opacity=AlarmShowStyle.Data.ThirdLevel.Opacity;
	}
 	var rgba=color16ToRgba(BackgroundColor,Opacity);
 	o.style='background-color:'+rgba+';color:'+Colorr+';';
	return '<span data-qtip="'+tipval+'" data-dismissDelay=10000>'+val+'</span>';
}
 
 adviceTimeFormat = function(val,o,p,e) {
	 	var reslut="";
	 	val=val.split(".")[0];
	 	var reslut=val;
	    return '<span data-qtip="'+reslut+'">'+reslut+'</span>';
	}
 
 adviceStatTableCommStatusColor = function(val,o,p,e) {
	 	var itemCode=p.data.itemCode;
	 	var tipval=val;
	 	var alarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
	 	var backgroundColor='#FFFFFF';
	 	var color='#000000';
	 	var opacity=1;
	 	var alarmLevel=0;
	 	if(itemCode.toUpperCase()=='offline'.toUpperCase()){
	 		alarmLevel=100;
	 	}
	 	if (alarmLevel == 100) {
	 		backgroundColor='#'+alarmShowStyle.Statistics.FirstLevel.BackgroundColor;
	 		color='#'+alarmShowStyle.Statistics.FirstLevel.Color;
	 		opacity=alarmShowStyle.Statistics.FirstLevel.Opacity
		}else if (alarmLevel == 200) {
	 		backgroundColor='#'+alarmShowStyle.Statistics.SecondLevel.BackgroundColor;
	 		color='#'+alarmShowStyle.Statistics.SecondLevel.Color;
	 		opacity=alarmShowStyle.Statistics.SecondLevel.Opacity
		}else if (alarmLevel == 300) {
	 		backgroundColor='#'+alarmShowStyle.Statistics.ThirdLevel.BackgroundColor;
	 		color='#'+alarmShowStyle.Statistics.ThirdLevel.Color;
	 		opacity=alarmShowStyle.Statistics.ThirdLevel.Opacity
		}
	 	var rgba=color16ToRgba(backgroundColor,opacity);
	 	o.style='background-color:'+rgba+';color:'+color+';';
	 	return '<span data-qtip="'+tipval+'" data-dismissDelay=10000>'+val+'</span>';
	}
 
 adviceCommStatusColor = function(val,o,p,e) {
	 	var commStatus=p.data.commStatus;
	 	var tipval=val;
	 	var alarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
	 	var alarmLevel=p.data.commAlarmLevel==undefined?0:p.data.commAlarmLevel;
	 	var backgroundColor='#FFFFFF';
	 	var color='#000000';
	 	var opacity=1;
	 	if (commStatus == 0) {
	 		backgroundColor='#'+alarmShowStyle.Comm.offline.BackgroundColor;
	 		color='#'+alarmShowStyle.Comm.offline.Color;
	 		opacity=alarmShowStyle.Comm.offline.Opacity
		}else if (commStatus == 1) {
			backgroundColor='#'+alarmShowStyle.Comm.online.BackgroundColor;
	 		color='#'+alarmShowStyle.Comm.online.Color;
	 		opacity=alarmShowStyle.Comm.online.Opacity
		}
	 	var rgba=color16ToRgba(backgroundColor,opacity);
	 	o.style='background-color:'+rgba+';color:'+color+';';
//		if (commStatus == 1) {
//			return '<span data-qtip="在线">在线</span>';
//		} else {
//			return '<span data-qtip="离线">离线</span>';
//		}
	 	return '<span data-qtip="'+tipval+'" data-dismissDelay=10000>'+val+'</span>';
	}
 
 adviceRunStatusColor = function(val,o,p,e) {
	 	var commStatus=p.data.commStatus;
	 	var runStatus=p.data.runStatus;
	 	var tipval=val;
	 	var alarmLevel=p.data.runAlarmLevel==undefined?0:p.data.runAlarmLevel;
	 	var alarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
		if (commStatus == 0) {
//			o.css='pendingColor';
			return '';
		} else {
			var backgroundColor='#FFFFFF';
		 	var color='#000000';
		 	var opacity=1;
		 	if (runStatus == 0) {
		 		backgroundColor='#'+alarmShowStyle.Run.stop.BackgroundColor;
		 		color='#'+alarmShowStyle.Run.stop.Color;
		 		opacity=alarmShowStyle.Run.stop.Opacity
			}else if (runStatus == 1) {
				backgroundColor='#'+alarmShowStyle.Run.run.BackgroundColor;
		 		color='#'+alarmShowStyle.Run.run.Color;
		 		opacity=alarmShowStyle.Run.run.Opacity
			}
		 	var rgba=color16ToRgba(backgroundColor,opacity);
		 	o.style='background-color:'+rgba+';color:'+color+';';
//			if(runStatus==1){
//				return '<span data-qtip="运行">运行</span>';
//			}else{
//				return '<span data-qtip="停止">停止</span>';
//			}
		 	return '<span data-qtip="'+tipval+'" data-dismissDelay=10000>'+val+'</span>';
		}
	}
 adviceResultStatusColor = function(val,o,p,e) {
	 	var tipval=val;
	 	var alarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
	 	var alarmLevel=p.data.resultAlarmLevel==undefined?0:p.data.resultAlarmLevel;
	 	var backgroundColor='#FFFFFF';
	 	var color='#000000';
	 	var opacity=1;
	 	if (alarmLevel == 0) {
		 		backgroundColor='#'+alarmShowStyle.Data.Normal.BackgroundColor;
		 		color='#'+alarmShowStyle.Data.Normal.Color;
		 		opacity=alarmShowStyle.Data.Normal.Opacity
			}else if (alarmLevel == 100) {
				backgroundColor='#'+alarmShowStyle.Data.FirstLevel.BackgroundColor;
				color='#'+alarmShowStyle.Data.FirstLevel.Color;
				opacity=alarmShowStyle.Data.FirstLevel.Opacity
			}else if (alarmLevel == 200) {
				backgroundColor='#'+alarmShowStyle.Data.SecondLevel.BackgroundColor;
				color='#'+alarmShowStyle.Data.SecondLevel.Color;
				opacity=alarmShowStyle.Data.SecondLevel.Opacity
			}else if (alarmLevel == 300) {
				backgroundColor='#'+alarmShowStyle.Data.ThirdLevel.BackgroundColor;
				color='#'+alarmShowStyle.Data.ThirdLevel.Color;
				opacity=alarmShowStyle.Data.ThirdLevel.Opacity
			}
	 	if(alarmLevel>0){
	 		var rgba=color16ToRgba(backgroundColor,opacity);
		 	o.style='background-color:'+rgba+';color:'+color+';';
	 	}
	 	return '<span data-qtip="'+tipval+'" data-dismissDelay=10000>'+val+'</span>';
	}
 
 adviceDataColor = function(val,o,p,e) {
	 	var alarmInfo=p.data.alarmInfo;
	 	if(val==undefined){
	 		val='';
	 	}
	 	var tipval=val;
	 	var alarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
	 	var column=o.column.dataIndex;
	 	var alarmLevel=0;
	 	if(isNotVal(alarmInfo)&&alarmInfo.length>0){
	 		for(var i=0;i<alarmInfo.length;i++){
	 			if(column.toUpperCase()==alarmInfo[i].item.toUpperCase()){
	 				var backgroundColor='#FFFFFF';
	 			 	var color='#000000';
	 			 	var opacity=1;
	 			 	alarmLevel=alarmInfo[i].alarmLevel;
	 			 	if (alarmLevel == 0) {
	 			 		backgroundColor='#'+alarmShowStyle.Data.Normal.BackgroundColor;
	 			 		color='#'+alarmShowStyle.Data.Normal.Color;
	 			 		opacity=alarmShowStyle.Data.Normal.Opacity
	 				}else if (alarmLevel == 100) {
	 					backgroundColor='#'+alarmShowStyle.Data.FirstLevel.BackgroundColor;
	 					color='#'+alarmShowStyle.Data.FirstLevel.Color;
	 					opacity=alarmShowStyle.Data.FirstLevel.Opacity
	 				}else if (alarmLevel == 200) {
	 					backgroundColor='#'+alarmShowStyle.Data.SecondLevel.BackgroundColor;
	 					color='#'+alarmShowStyle.Data.SecondLevel.Color;
	 					opacity=alarmShowStyle.Data.SecondLevel.Opacity
	 				}else if (alarmLevel == 300) {
	 					backgroundColor='#'+alarmShowStyle.Data.ThirdLevel.BackgroundColor;
	 					color='#'+alarmShowStyle.Data.ThirdLevel.Color;
	 					opacity=alarmShowStyle.Data.ThirdLevel.Opacity
	 				}
	 			 	if(alarmLevel>0){
	 			 		var rgba=color16ToRgba(backgroundColor,opacity);
		 			 	o.style='background-color:'+rgba+';color:'+color+';';
	 			 	}
	 				break;
	 			}
	 		}
	 	}
	 	if(alarmLevel==0){
	    	var backgroundColor = '#' + alarmShowStyle.Data.Normal.BackgroundColor;
	    	var color = '#' + alarmShowStyle.Data.Normal.Color;
	    	var opacity = alarmShowStyle.Data.Normal.Opacity;
	    	var rgba = color16ToRgba(backgroundColor, opacity);
            o.style = 'background-color:' + rgba + ';color:' + color + ';';
	    }
	 	return '<span data-qtip="'+tipval+'" data-dismissDelay=10000>'+val+'</span>';
	}
 
 adviceRealtimeMonitoringDataColor = function (val, o, p, e) {
	    var alarmInfo = p.data.alarmInfo;
	    var alarmLevel=0;
	    var alarmShowStyle = Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
	    var column = o.column.dataIndex;
	    if (isNotVal(alarmInfo) && alarmInfo.length > 0) {
	        for (var i = 0; i < alarmInfo.length; i++) {
	            if (column.toUpperCase() == alarmInfo[i].item.toUpperCase()) {
	                if(alarmInfo[i].alarmType==2 || alarmInfo[i].alarmType==5){//数据量报警
	                	if(val!=undefined){
	                		var backgroundColor = '#FFFFFF';
	    	                var color = '#000000';
	    	                var opacity = 1;
	    	                alarmLevel=0;
	    	                if(parseFloat(val)>(parseFloat(alarmInfo[i].upperLimit)+parseFloat(alarmInfo[i].hystersis))
	    	                		|| parseFloat(val)<(parseFloat(alarmInfo[i].lowerLimit)-parseFloat(alarmInfo[i].hystersis))){
	    	                	alarmLevel = alarmInfo[i].alarmLevel;
	    	                }
	    	                if (alarmLevel == 0) {
	    	                    backgroundColor = '#' + alarmShowStyle.Data.Normal.BackgroundColor;
	    	                    color = '#' + alarmShowStyle.Data.Normal.Color;
	    	                    opacity = alarmShowStyle.Data.Normal.Opacity;
	    	                } else if (alarmLevel == 100) {
	    	                    backgroundColor = '#' + alarmShowStyle.Data.FirstLevel.BackgroundColor;
	    	                    color = '#' + alarmShowStyle.Data.FirstLevel.Color;
	    	                    opacity = alarmShowStyle.Data.FirstLevel.Opacity;
	    	                } else if (alarmLevel == 200) {
	    	                    backgroundColor = '#' + alarmShowStyle.Data.SecondLevel.BackgroundColor;
	    	                    color = '#' + alarmShowStyle.Data.SecondLevel.Color;
	    	                    opacity = alarmShowStyle.Data.SecondLevel.Opacity;
	    	                } else if (alarmLevel == 300) {
	    	                    backgroundColor = '#' + alarmShowStyle.Data.ThirdLevel.BackgroundColor;
	    	                    color = '#' + alarmShowStyle.Data.ThirdLevel.Color;
	    	                    opacity = alarmShowStyle.Data.ThirdLevel.Opacity;
	    	                }
	    	                if(alarmLevel>0){
	    	 			 		var rgba=color16ToRgba(backgroundColor,opacity);
	    		 			 	o.style='background-color:'+rgba+';color:'+color+';';
	    	 			 	}
	                	}
	                	break;
	                }else if(alarmInfo[i].alarmType==1){//枚举量报警
	                	if(val!=undefined){
	                		var backgroundColor = '#FFFFFF';
	    	                var color = '#000000';
	    	                var opacity = 1;
	    	                alarmLevel=0;
	    	                if(val==alarmInfo[i].alarmValue || val==alarmInfo[i].alarmValueMeaning){
	    	                	alarmLevel = alarmInfo[i].alarmLevel;
	    	                	if (alarmLevel == 0) {
		    	                    backgroundColor = '#' + alarmShowStyle.Data.Normal.BackgroundColor;
		    	                    color = '#' + alarmShowStyle.Data.Normal.Color;
		    	                    opacity = alarmShowStyle.Data.Normal.Opacity;
		    	                } else if (alarmLevel == 100) {
		    	                    backgroundColor = '#' + alarmShowStyle.Data.FirstLevel.BackgroundColor;
		    	                    color = '#' + alarmShowStyle.Data.FirstLevel.Color;
		    	                    opacity = alarmShowStyle.Data.FirstLevel.Opacity;
		    	                } else if (alarmLevel == 200) {
		    	                    backgroundColor = '#' + alarmShowStyle.Data.SecondLevel.BackgroundColor;
		    	                    color = '#' + alarmShowStyle.Data.SecondLevel.Color;
		    	                    opacity = alarmShowStyle.Data.SecondLevel.Opacity;
		    	                } else if (alarmLevel == 300) {
		    	                    backgroundColor = '#' + alarmShowStyle.Data.ThirdLevel.BackgroundColor;
		    	                    color = '#' + alarmShowStyle.Data.ThirdLevel.Color;
		    	                    opacity = alarmShowStyle.Data.ThirdLevel.Opacity;
		    	                }
	    	                	if(alarmLevel>0){
	    		 			 		var rgba=color16ToRgba(backgroundColor,opacity);
	    			 			 	o.style='background-color:'+rgba+';color:'+color+';';
	    		 			 	}
		    	                break;
	    	                }
	                	}
	                }
	            }
	        }
	    }
	    if(alarmLevel==0){
	    	var backgroundColor = '#' + alarmShowStyle.Data.Normal.BackgroundColor;
	    	var color = '#' + alarmShowStyle.Data.Normal.Color;
	    	var opacity = alarmShowStyle.Data.Normal.Opacity;
	    	var rgba = color16ToRgba(backgroundColor, opacity);
            o.style = 'background-color:' + rgba + ';color:' + color + ';';
	    }
	    if (val == undefined) {
	        val = '';
	    }
	    var tipval = val;
	    return '<span data-qtip="' + tipval + '" data-dismissDelay=10000>' + val + '</span>';
	}

 adviceHistoryWellDataColor = function(val,o,p,e) {
	 	//alert(val);
	 	var bjjb=p.data.bjjb;
		if (bjjb != 0) {
			return '<font style="color:red;">'+val+'</font>';
		} else {
			return '<font style="color:black;">'+val+'</font>';
		}
	}
	
 getBackgroundColor = function(val,o,p,e) {
	 return '<div style="background:'+p.data.bjys+';">'+val+'</div>';
	}

  var onStoreSizeChange=function (v,o,conId) {
	  var cmp=Ext.getCmp(conId);
	  if(isNotVal(cmp)){
		  cmp.update({count: v.getTotalCount()});
	  }
  }
  
  var updateTotalRecords=function (total,conId) {
	  var cmp=Ext.getCmp(conId);
	  if(isNotVal(cmp)){
		  cmp.update({count: total});
	  }
  }
      
  
  var onLabelSizeChange=function (v,o,conId) {
      //Ext.getCmp(conId).update({count: v.getTotalCount()});
      document.getElementById(conId).innerText=v.getTotalCount();
     
    }

//刷新grid数据信息
autoRefreshPanelView=function(panelId){
	var jqzRealTimePanel_Id=Ext.getCmp(panelId);
	var tree_store=jqzRealTimePanel_Id.getStore();
	if(!tree_store.isLoading()){
		tree_store.load();
	}
}

// 生成Grid-Fields
createGridPanelColumn = function(columnInfo) {
	var myArr = columnInfo;
	var myColumns = "[";
	for (var i = 0; i < myArr.length; i++) {
		var attr = myArr[i];
		var width_="";
		var lock_="";
		var hidden_="";
		if(isNotVal(attr.lock)){
//			lock_=",locked:"+attr.lock;
		}
		if(isNotVal(attr.hidden==true)){
			hidden_=",hidden:"+attr.hidden;
		}
		if(isNotVal(attr.width)){
			width_=",width:"+attr.width;
		}
		myColumns += "{text:'" + attr.header + "'";
		if (attr.dataType == 'timestamp') {
			myColumns +=lock_+width_+ ",sortable : false,align:'center'" + ",dataIndex:'" + attr.dataIndex+ "',renderer:function(value){return dateFormat(value);}";
		} else if (attr.dataIndex == 'id'||attr.dataIndex=="jh as id") {
			myColumns += lock_+width_+",xtype: 'rownumberer',sortable : false,align:'center'";
		} else {
			myColumns +=hidden_+lock_+width_+ ",sortable : false,align:'center',dataIndex:'"+ attr.dataIndex + "'";
		}
		myColumns += "}";
		if (i < myArr.length - 1) {
			myColumns += ",";
		}
	}
	myColumns += "]";
	return myColumns;
}
//生成Grid-Fields 创建grid 的columns信息
createDiagStatisticsColumn = function(columnInfo) {
	var myArr = columnInfo;
	var myColumns = "[";
	for (var i = 0; i < myArr.length; i++) {
		var attr = myArr[i];
		var width_="";
		var flex_ = "";
		var lock_="";
		var hidden_="";
		if(isNotVal(attr.lock)){
//			lock_=",locked:"+attr.lock;
		}
		if(isNotVal(attr.hidden==true)){
			hidden_=",hidden:"+attr.hidden;
		}
		if(isNotVal(attr.width)){
			width_=",width:"+attr.width;
		}
		if (isNotVal(attr.flex)) {
        	flex_ = ",flex:" + attr.flex;
        }
		myColumns +="{text:'" + attr.header + "'"+width_+flex_;
		 if (attr.dataIndex=='id'){
		  myColumns +=",xtype: 'rownumberer',sortable:false,align:'center',locked:false" ;
		}else if(attr.dataIndex=='gtcjsj'||"updatetime"==attr.dataIndex){
			myColumns +=hidden_+lock_+",sortable : false,align:'center',dataIndex:'"+attr.dataIndex+"',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')";
		}else if(attr.dataIndex=='gxrq'){
			myColumns +=hidden_+lock_+",sortable : false,align:'center',dataIndex:'"+attr.dataIndex+"',renderer:Ext.util.Format.dateRenderer('Y-m-d')";
		}else {
			myColumns +=hidden_+lock_+",align:'center',dataIndex:'"+attr.dataIndex+"'";
		}
		myColumns += "}";
		if (i < myArr.length - 1) {
			myColumns += ",";
		}
	}
	myColumns +="]";
	return myColumns;
}

//生成可编辑表格表头
createEditGridColumn = function(columnInfo) {
	var myArr = columnInfo;
	var myColumns = "[";
	for (var i = 0; i < myArr.length; i++) {
		var attr = myArr[i];
		var width_="";
		var lock_="";
		var hidden_="";
		if(isNotVal(attr.lock)){
//			lock_=",locked:"+attr.lock;
		}
		if(isNotVal(attr.hidden==true)){
			hidden_=",hidden:"+attr.hidden;
		}
		if(isNotVal(attr.width)){
			width_=",width:"+attr.width;
		}
		myColumns +="{text:'" + attr.header + "'";
		 if (attr.dataIndex=='id'){
		  myColumns +=",width:"+attr.width+",xtype: 'rownumberer',sortable:false,align:'center',locked:false" ;
		}else if(attr.dataIndex=='gtcjsj'||"updatetime"==attr.dataIndex){
			myColumns +=hidden_+lock_+width_+",sortable : false,align:'center',dataIndex:'"+attr.dataIndex+"',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')";
		}else if(attr.dataIndex=='gxrq'){
			myColumns +=hidden_+lock_+width_+",sortable : false,align:'center',dataIndex:'"+attr.dataIndex+"',renderer:Ext.util.Format.dateRenderer('Y-m-d')";
		}else {
			myColumns +=hidden_+lock_+width_+",align:'center',dataIndex:'"+attr.dataIndex+"',"+"editor:{allowBlank:false}";
		}
		myColumns += "}";
		if (i < myArr.length - 1) {
			myColumns += ",";
		}
	}
	myColumns +="]";
	return myColumns;
}

//生成树形treepanel 的Grid-Fields columns数据信息
createTreeHeadColumns = function(columnInfo) {
	var myArr = columnInfo;
	var myColumns ="[";
	for (var i = 0; i < myArr.length; i++) {
		var attr = myArr[i];
		myColumns += "{ header:'" + attr.header + "'";
		if (attr.dataIndex=='text'){
			myColumns +=",xtype: 'treecolumn',dataIndex:'"+attr.dataIndex+"'";
		}else {
			myColumns +=",dataIndex:'"+attr.dataIndex+"'";
		}
		myColumns +="}";
		if (i < myArr.length - 1) {
			myColumns += ",";
		}
	}
	myColumns +="]";
	
	return myColumns;
};

function trim(str){   
    str = str.replace(/^(\s|\u00A0)+/,'');   
    for(var i=str.length-1; i>=0; i--){   
        if(/\S/.test(str.charAt(i))){   
            str = str.substring(0, i+1);   
            break;   
        }   
    }   
    return str;   
}
/*** 
  * 对 特殊字符进行重新编码 
  * **/ 
function URLencode(sStr){
	return encodeURI(sStr).replace(/\+/g, '%2B').replace(/\"/g,'%22').replace(/\'/g, '%27').replace(/\//g,'%2F').replace(/\#/g,'%23'); 
}

function initContinuousDiagramChart(pointdata, divid,title,subtitle,xtext,ytext,color) {
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',     // 散点图   
		            renderTo : divid,
		            borderWidth : 0,
		            zoomType: 'xy',
		            reflow: true
		        },                                                                                   
		        title: {
		        	text: title          
		        },                                                                                   
		        subtitle: {
		        	text: subtitle                                                      
		        },
		        credits: {
		            enabled: false
		        },
		        xAxis: {                                                                             
		            title: {                                                                         
		                text: xtext,    // 坐标+显示文字
		                useHTML: false,
		                margin:5,
                        style: {
                        	fontSize: '12px',
                            padding: '5px'
                        }
		            }, 
		            startOnTick: false,      //是否强制轴线在标线处开始
		            endOnTick: false,        //是否强制轴线在标线处结束                                                        
		            showLastLabel: true,
		            allowDecimals: false,    // 刻度值是否为小数
//		            min:0,
		            minorTickInterval: ''    // 最小刻度间隔
		        },                                                                                   
		        yAxis: {                                                                             
		            title: {                                                                         
		                text: ytext   // 载荷（kN） 
                    },
		            allowDecimals: false,    // 刻度值是否为小数
		            minorTickInterval: ''   // 不显示次刻度线
//		            min: 0                  // 最小值
		        },
		        exporting:{
                    enabled:true,    
                    filename:'class-booking-chart',    
                    url:context + '/exportHighcharsPicController/export'
               },
		        legend: {
		        	itemStyle:{
		        		fontSize: '8px'
		        	},
		            enabled: false,
		            layout: 'vertical',
					align: 'right',
					verticalAlign: 'top',
					x: 0,
					y: 55,
					floating: true
		        },                                                                                   
		        plotOptions: {                                                                       
		            scatter: {                                                                       
		                marker: {                                                                    
		                    radius: 0,                                                               
		                    states: {                                                                
		                        hover: {                                                             
		                            enabled: true,                                                   
		                            lineColor: '#646464'                                    
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                states: {                                                                    
		                    hover: {                                                                 
		                        marker: {                                                            
		                            enabled: false                                                   
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                tooltip: {                                                                   
		                    headerFormat: '',                                
		                    pointFormat: '{point.x},{point.y}'                                
		                }                                                                            
		            }                                                                                
		        },
		        series: [{                                                                           
		            name: '',                                                                  
		            color: color,   
		            lineWidth:3,
		            data:  pointdata                                                                                  
		        }]
	});
}

function SetEveryOnePointColor(chart) {      // 设置每一个数据点的颜色横向渐变
	var colors = chart.options.colors;
	var pointsList = chart.series[0].points;         //获得第一个序列的所有数据点
    for (var i = 0; i < pointsList.length; i++) {    //遍历设置每一个数据点颜色
        chart.series[0].points[i].update({
            color: {
                linearGradient: { x1: 0, y1: 0, x2: 1, y2: 0 },     //横向渐变效果 如果将x2和y2值交换将会变成纵向渐变效果
                stops: [
                            [0, Highcharts.Color(colors[i*2]).setOpacity(1).get('rgba')],
//                            [0.5, 'rgb(255, 255, 255)'],
//                            [0.5, Highcharts.Color(colors[i*2]).setOpacity(1).get('rgba')],
                            [1, Highcharts.Color(colors[i*2+1]).setOpacity(1).get('rgba')]
                        ]  
            }
        });
    }
}

function getScrollWidth() { // 获取系统滚动条的宽度
	var noScroll, scroll, oDiv = document.createElement("DIV");
	oDiv.style.cssText = "margin:0px;padding:0px;border:0px;position:absolute; top:-1000px; width:100px; height:100px; overflow:hidden;";
	noScroll = document.body.appendChild(oDiv).clientWidth;
	oDiv.style.overflowY = "scroll";
	scroll = oDiv.clientWidth;
	document.body.removeChild(oDiv);
	return noScroll-scroll;
}

//显示历史查询-图形查询
function showHistoryGraphicalQueryView(panelId) {
	var HistoryDiagramQuery = Ext.getCmp("HistoryDiagramQuery_Id");// 获取到显示产量数据表的panel
	if (HistoryDiagramQuery != undefined) {
		HistoryDiagramQuery.removeAll();// 清除掉以前的grid，
		uss_ = Ext.create(panelId);
		HistoryDiagramQuery.add(uss_);// 重新添加一个新的grid
	}
}


function loadElectricAnalysisData() {
	var tabpanel=Ext.getCmp("ElectricWellDataAnalysisPanel_Id");
	if(!Ext.isEmpty(tabpanel)){
		if(tabpanel.activeTab.id=="ElectricCurveDataAnalysisPanel_Id"){
			var timelistobj=Ext.getCmp("ElectricWorkStatusTimeListGrid_Id");
            if(isNotVal(timelistobj)){
            	timelistobj.getStore().load();
            }else{
            	Ext.create('AP.store.diagnose.ElectricWorkStatusTimeListStore');
            }
            Ext.create("AP.store.diagnose.CorrelationCoefficientCurveStore");
		}else if(tabpanel.activeTab.id="ElectricDiscreteDataAnalysisPanel_Id"){
			//alert("离散数据分析");
			var gridobj=Ext.getCmp("ElectricDiscreteDataAnalysisTableGrid_Id");
            if(isNotVal(gridobj)){
            	gridobj.getStore().load();
            }else{
            	Ext.create('AP.store.diagnose.ElectricDiscreteDataAnalysisTableStore');
            }
		}
	}
}

showRealtimeWorkStastPieChart = function(store, divid,title,name) {
	var list=store.proxy.reader.rawData.list;
	var series = "[";
	if(list!=undefined && list.length>0){
		for(var i=0;i<list.length;i++){
			if(i==0){
				series+="{name:'"+list[i].gkmc+"',y:"+list[i].total+",sliced:true,selected:true}";
			}else{
				series+="['"+list[i].gkmc+"',"+list[i].total+"]";
			}
			if(i!=list.length-1){
				series+=",";
			}
		}
	}
	series += "]";
	var data = Ext.JSON.decode(series);
	initRealtimeWorkStastPieChart(title, name, data, divid);
	return false;
}

//将grid数据导出到Excel
function exportGridExcelData(gridId,url,fileName,title){
	var store=Ext.getCmp(gridId).getStore();
	var total=store.getCount();
	var jsonArray = [];
	for(var i=0;i<total;i++){
		jsonArray.push(store.getAt(i).data);
	}
	var data=JSON.stringify(jsonArray);
    var fields = "";
    var heads = "";
    var gridPanel = Ext.getCmp(gridId);
    var items_ = gridPanel.items.items;
    if(items_.length==1){//无锁定列时
    	var columns_ = gridPanel.columns;
    	Ext.Array.each(columns_, function (name,
                index, countriesItSelf) {
                var locks = columns_[index];
                if (index > 0 && locks.hidden == false) {
                    fields += locks.dataIndex + ",";
                    heads += locks.text + ",";
                }
            });
    }else{
    	Ext.Array.each(items_, function (name, index,
                countriesItSelf) {
                var datas = items_[index];
                var columns_ = datas.columns;
                if (index == 0) {
                    Ext.Array.each(columns_, function (name,
                        index, countriesItSelf) {
                        var locks = columns_[index];
                        if (index > 0 && locks.hidden == false) {
                            fields += locks.dataIndex + ",";
                            heads += locks.text + ",";
                        }
                    });
                } else {
                    Ext.Array.each(columns_, function (name,
                        index, countriesItSelf) {
                        var headers_ = columns_[index];
                        if (headers_.hidden == false) {
                            fields += headers_.dataIndex + ",";
                            heads += headers_.text + ",";
                        }
                    });
                }
            });
    }
    if (isNotVal(fields)) {
        fields = fields.substring(0, fields.length - 1);
        heads = heads.substring(0, heads.length - 1);
    }
    fields = "id," + fields;
    heads = "序号," + heads;
    var param = "&heads=" + heads +"&fields=" + fields+"&data=" + data+"&fileName=" + fileName+"&title=" + title;
    openExcelWindow(url+'?flag=true&' + param);
};

function handsontableDataCheck_Org(val, callback,row,col,handsontableHelper){
	var leftOrg_Name=Ext.getCmp("leftOrg_Name").getValue();
	var orgArr=leftOrg_Name.split(",");
	var orgCount=isExist(orgArr,val);
	if(orgCount!=1){
		var cell = handsontableHelper.hot.getCell(row, col);  
        cell.style.background = "#f09614";
		return callback(false);
	}else{
		return callback(true);
	}
};

function handsontableDataCheck_Num(val, callback,row,col,handsontableHelper){
	var pattern=/^[0-9]*$/;
	if(isNotVal(val) && !isNaN(val)){
		return callback(true);
	}else{
		var cell = handsontableHelper.hot.getCell(row, col);  
        cell.style.background = "#f09614";
		return callback(false);
	}
};

function handsontableDataCheck_Num_Nullable(val, callback,row,col,handsontableHelper){
	var pattern=/^[0-9]*$/;
	if(val==='' || !isNaN(val)){
		return callback(true);
	}else{
		var cell = handsontableHelper.hot.getCell(row, col);  
        cell.style.background = "#f09614";
		return callback(false);
	}
};

function handsontableDataCheck_NotNull(val, callback,row,col,handsontableHelper){
	if(isNotVal(val)){
		return callback(true);
	}else{
		var cell = handsontableHelper.hot.getCell(row, col);  
        cell.style.background = "#f09614";
		return callback(false);
	}
};

function handsontableDataCheck_PumpGrade(val, callback,row,col,handsontableHelper){
	if(val==1 || val==2 || val==3 || val==4 || val==5){
		return callback(true);
	}else{
		var cell = handsontableHelper.hot.getCell(row, col);  
        cell.style.background = "#f09614";
		return callback(false);
	}
};

function handsontableDataCheck_RodGrade(val, callback,row,col,handsontableHelper){
	if(val==''
		||val.toUpperCase()=='A'||val.toUpperCase()=='B'
		||val.toUpperCase()=='C'||val.toUpperCase()=='K'
		||val.toUpperCase()=='D'||val.toUpperCase()=='KD'
		||val.toUpperCase()=='HL'||val.toUpperCase()=='HY'){
		return callback(true);
	}else{
		var cell = handsontableHelper.hot.getCell(row, col);  
        cell.style.background = "#f09614";
		return callback(false);
	}
};

function getBaseUrl(){
	var curWwwPath=window.document.location.href;
	//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName=window.document.location.pathname;
	var pos=curWwwPath.indexOf(pathName);
	//获取主机地址，如： http://localhost:8083
	var localhostPaht=curWwwPath.substring(0,pos);
	//获取带"/"的项目名，如：/uimcardprj
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	var baseRoot = localhostPaht+projectName;
	return baseRoot;
};

function initTimeAndDataCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, ytitle, color,legend,timeFormat) {
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
            tickPixelInterval: tickInterval,
            labels: {
                formatter: function () {
                    return Highcharts.dateFormat(timeFormat, this.value);
                },
                rotation: 0, //倾斜度，防止数量过多显示不全  
                step: 2
            }
        },
        yAxis: [{
            lineWidth: 1,
            title: {
                text: ytitle,
                style: {
                    color: '#000000',
                    fontWeight: 'bold'
                }
            },
            labels: {
                formatter: function () {
                    return Highcharts.numberFormat(this.value, 2);
                }
            }
      }],
        tooltip: {
            crosshairs: true, //十字准线
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
            url: context + '/exportHighcharsPicController/export'
        },
        plotOptions: {
            spline: {
                lineWidth: 1,
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
                shadow: true
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            enabled: legend,
            borderWidth: 0
        },
        series: series
    });
};

function isExist(arr,data){
	var r=0;
	if(isNotVal(arr) && arr.length>0){
		for(var i=0;i<arr.length;i++){
			if(arr[i]===data){
				r+=1;
			}
		}
	}
	return r;
};
function foreachAndSearchOrgAbsolutePath(orgStoreData, orgId) {
	var rtnArr=[];
	var rtnStr="";
	const foreachAndSearchOrgAbsolutePathname=function(storeData, id) {
		if(storeData){
			for(let record of storeData){
				if(record.data.orgId===id){
					if(record.parentNode){
						foreachAndSearchOrgAbsolutePathname(storeData,record.parentNode.data.orgId);
					}
					rtnArr.push(record.data.text);
				}else{
//					if(record.childNodes){
//						foreachAndSearchOrgAbsolutePathname(record.childNodes,orgId);
//					}
				}
			}
		}
	};
	foreachAndSearchOrgAbsolutePathname(orgStoreData, orgId);
	for(var i=0;i<rtnArr.length;i++){
		rtnStr+=rtnArr[i];
		if(i<rtnArr.length-1){
			rtnStr+="/";
		}
	}
	return rtnStr;
}

function foreachAndSearchOrgAbsolutePathId(orgStoreData, orgId) {
	var rtnArr=[];
	var rtnStr="";
	const foreachAndSearchOrgAbsolutePathId=function(storeData, id) {
		if(storeData){
			for(let record of storeData){
				if(record.data.orgId===id){
					if(record.parentNode){
						foreachAndSearchOrgAbsolutePathId(storeData,record.parentNode.data.orgId);
					}
					rtnArr.push(record.data.orgId);
				}else{
				}
			}
		}
	};
	foreachAndSearchOrgAbsolutePathId(orgStoreData, orgId);
	rtnStr = "" + rtnArr.join(",");
	return rtnStr;
}

function foreachAndSearchOrgChildId(rec) {
	var rtnArr=[];
	const recursionOrgChildId=function(chlidArray) {
		var ch_length;
		var ch_node = chlidArray.childNodes;
		if (isNotVal(ch_node)) {
			ch_length = ch_node.length;
		} else {
			ch_length = chlidArray.length;
		}
		if (ch_length > 0) {
			if (!Ext.isEmpty(chlidArray)) {
				Ext.Array.each(chlidArray, function(childArrNode, index, fog) {
							var x_node_seId = fog[index].data.orgId;
							rtnArr.push(x_node_seId);
							// 递归
							if (childArrNode.childNodes != null) {
								recursionOrgChildId(childArrNode.childNodes);
							}
						});
			}
		} else {
			if (isNotVal(chlidArray)) {
				var x_node_seId = chlidArray.data.orgId;
				rtnArr.push(x_node_seId);
			}
		}
	};
	recursionOrgChildId(rec);
	return rtnArr.join(",");
};

function getDateAndTime(dateStr,h,m,s){
	if(!isNotVal(dateStr)){
		return '';
	}
	if(!isNotVal(h)){
		h=0
	}
	if(!isNotVal(m)){
		m=0
	}
	if(!isNotVal(s)){
		s=0
	}
	var hStr=h+'';
	var mStr=m+'';
	var sStr=s+'';
	if(hStr.length==1){
		hStr='0'+hStr;
	}
	if(mStr.length==1){
		mStr='0'+mStr;
	}if(sStr.length==1){
		sStr='0'+sStr;
	}
	return dateStr+' '+hStr+":"+mStr+':'+sStr;
};

showSurfaceCard = function(result, divid) {
    var positionCurveData=result.positionCurveData.split(","); 
    var loadCurveData=result.loadCurveData.split(",");
	var data = "["; // 功图data
	for (var i=0; i <= positionCurveData.length; i++) {
		if(i<positionCurveData.length){
			data += "[" + changeTwoDecimal(positionCurveData[i]) + ","+changeTwoDecimal(loadCurveData[i])+"],";
		}else{
			data += "[" + changeTwoDecimal(positionCurveData[1]) + ","+changeTwoDecimal(loadCurveData[1])+"]";//将图形的第一个点拼到最后面，使图形闭合
		}
	}
	data+="]";
	var pointdata = Ext.JSON.decode(data);
//	var pointdata = JSON.parse(data);
	initSurfaceCardChart(pointdata, result, divid);
	return false;
}

/* 
 * 在泵功图中提取光杆功图
 */
showFSDiagramFromPumpcard = function(result, divid) {
	var pumpFSDiagramData=result.pumpFSDiagramData.split("#")[0];
    var gt=pumpFSDiagramData.split(","); // 功图数据：功图点数，位移1，载荷1，位移2，载荷2...
    var gtcount=(gt.length)/2; // 功图点数
	var data = "["; // 功图data
	var upStrokeData = "["; // 上冲程数据
	var downStrokeData = "["; // 下冲程数据
	var minIndex=0,maxIndex=0;
	if(gt.length>0){
		for (var i=0; i <= gt.length; i+=2) {
			if(i<gt.length){
				data += "[" + changeTwoDecimal(gt[i]) + ","+changeTwoDecimal(gt[i+1])+"],";
			}else{
				data += "[" + changeTwoDecimal(gt[0]) + ","+changeTwoDecimal(gt[1])+"]";//将图形的第一个点拼到最后面，使图形闭合
			}
		}
		
		var minPos=100,maxPos=0;
		for (var i=0; i < gtcount; i++) {
			if(parseFloat(gt[i*2])<parseFloat(minPos)){
				minPos=changeTwoDecimal(gt[i*2]);
				minIndex=i;
			}
			if(parseFloat(gt[i*2])>parseFloat(maxPos)){
				maxPos=changeTwoDecimal(gt[i*2]);
				maxIndex=i;
			}
		}
		if(minIndex<=maxIndex){//如果最小值索引小于最大值索引
			for(var i=minIndex;i<=maxIndex;i++){
				upStrokeData += "[" + changeTwoDecimal(gt[i*2]) + ","+changeTwoDecimal(gt[i*2+1])+"]";
				if(i<maxIndex){
					upStrokeData+=",";
				}
			}
			var upStrokeCount=maxIndex-minIndex+1;//上冲程点数
			var downStrokeCount=gtcount-upStrokeCount;
			for(var i=0;i<downStrokeCount+2;i++){
				var index=i+maxIndex;
				if(index>(gtcount-1)){
					index=index-gtcount;
				}
				downStrokeData += "[" + changeTwoDecimal(gt[index*2]) + ","+changeTwoDecimal(gt[index*2+1])+"]";
				if(i<downStrokeCount+1){
					downStrokeData+=",";
				}
			}
		}else{//如果最小值索引大于最大值索引
			for(var i=maxIndex;i<=minIndex;i++){
				downStrokeData += "[" + changeTwoDecimal(gt[i*2]) + ","+changeTwoDecimal(gt[i*2+1])+"]";
				if(i<minIndex){
					downStrokeData+=",";
				}
			}
			var downStrokeCount=minIndex-maxIndex+1;//下冲程点数
			var upStrokeCount=gtcount-downStrokeCount;
			for(var i=0;i<upStrokeCount+2;i++){
				var index=i+minIndex;
				if(index>(gtcount-1)){
					index=index-gtcount;
				}
				upStrokeData += "[" + changeTwoDecimal(gt[index*2]) + ","+(gt[index*2+1])+"]";
				if(i<upStrokeCount+1){
					upStrokeData+=",";
				}
			}
		}
	}
	data+="]";
	upStrokeData+="]";
	downStrokeData+="]";
	
	
	var pointdata = Ext.JSON.decode(data);
	var upStrokePointdata = Ext.JSON.decode(upStrokeData);
	var downStrokePointdata = Ext.JSON.decode(downStrokeData);
	initSurfaceCardChart(pointdata,result, divid);
	return false;
}

function initSurfaceCardChart(pointdata, gtdata, divid) {
	var wellName=gtdata.wellName;         // 井名
	var acqTime=gtdata.acqTime;     // 采集时间
	var upperLoadLine=gtdata.upperLoadLine;   // 理论上载荷
	var lowerLoadLine=gtdata.lowerLoadLine;   // 理论下载荷
	var pointCount=gtdata.pointCount;//曲线点数
	var fmax=gtdata.fmax;     // 最大载荷
	var fmin=gtdata.fmin;     // 最小载荷
	var stroke=gtdata.stroke;       // 冲程
	var spm=gtdata.spm;       // 冲次
	var liquidProduction=gtdata.liquidProduction;     // 日产液量
	var resultName=gtdata.resultName;     // 工况类型
	var xtext='<span style="text-align:center;">'+cosog.string.position+'<br />';
	var productionUnitStr='t/d';
    if(productionUnit!=0){
    	productionUnitStr='m^3/d';
    }
    xtext+='点数:'+pointCount+"";
    xtext+=' 最大载荷:'+fmax+'kN';
    xtext+=' 最小载荷:'+fmin+'kN';
    xtext+=' 冲程:'+stroke+'m';
    xtext+=' 冲次:'+spm+'/min';
    xtext+=' 产液:'+liquidProduction+productionUnitStr;
    xtext+=' 工况:'+resultName;
    var upperlimit=parseFloat(fmax)+10;
    if(parseFloat(upperLoadLine)>=parseFloat(fmax)){
    	upperlimit=parseFloat(upperLoadLine)+10;
    }
    if(isNaN(upperlimit)){
    	upperlimit=null;
    }
	mychart = new Highcharts.Chart({
				chart: {
		            renderTo : divid,
		            zoomType: 'xy',
		            borderWidth : 0,
		            reflow: true
		        },                                                                                   
		        title: {
		        	text: cosog.string.FSDiagram  // 光杆功图                        
		        },                                                                                   
		        subtitle: {
		        	text: wellName+' ['+acqTime+']'                                                      
		        },
		        credits: {
		            enabled: false
		        },
		        xAxis: {                                                                           
		            title: {                                                                         
		                text: xtext,    // 坐标+显示文字
//		                align:'low',
		                useHTML: false,
		                margin:5,
//                        offset: 10,
                        style: {
//                            color: '#000',
//                            fontWeight: 'normal',
                        	fontSize: '12px',
                            padding: '5px'
                        }
		            },                                                                               
		            startOnTick: false,      //是否强制轴线在标线处开始
		            endOnTick: false,        //是否强制轴线在标线处结束                                                        
		            showLastLabel: true,
		            allowDecimals: false,    // 刻度值是否为小数
//		            min:0,
		            minorTickInterval: ''    // 最小刻度间隔
		        },                                                                                   
		        yAxis: {                                                                             
		            title: {                                                                         
		                text: cosog.string.load   // 载荷（kN） 
                    },
		            allowDecimals: false,    // 刻度值是否为小数
		            minorTickInterval: '',   // 不显示次刻度线
		            min: 0                  // 最小值
		        },
		        exporting:{
                    enabled:true,    
                    filename:wellName+'-'+acqTime+'-光杆功图',    
                    url:context + '/exportHighcharsPicController/export'
               },
		        legend: {                                                                            
		            layout: 'vertical',                                                              
		            align: 'left',                                                                   
		            verticalAlign: 'top',                                                            
		            x: 100,                                                                          
		            y: 70,                                                                           
		            floating: true,                                                                  
		            backgroundColor: '#FFFFFF',                                                      
		            borderWidth: 1  ,
		            enabled: false
		        },                                                                                   
		        plotOptions: {                                                                       
		            scatter: {                                                                       
		                marker: {                                                                    
		                    radius: 0,                                                               
		                    states: {                                                                
		                        hover: {                                                             
		                            enabled: true,                                                   
		                            lineColor: '#646464'                                    
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                states: {                                                                    
		                    hover: {                                                                 
		                        marker: {                                                            
		                            enabled: false                                                   
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                tooltip: {                                                                   
		                    headerFormat: '',                                
		                    pointFormat: '{point.x},{point.y}'                                
		                }                                                                            
		            }                                                                                
		        }, 
		        series: [{
		    		type: 'line',
		    		color: '#d12',
		    		dashStyle: 'Dash', //Dash,Dot,Solid,shortdash,默认Solid
		    		lineWidth:2,
		    		name: '理论上载荷线',
		    		data: [[0, parseFloat(upperLoadLine)], [parseFloat(stroke), parseFloat(upperLoadLine)]],
		    		marker: {
		    			enabled: false
		    		},
		    		states: {
		    			hover: {
		    				lineWidth: 0
		    			}
		    		},
		    		enableMouseTracking: true
		    	},{
		    		type: 'line',
		    		color: '#d12',
		    		dashStyle: 'Dash', //Dash,Dot,Solid,shortdash,默认Solid
		    		lineWidth:2,
		    		name: '理论下载荷线',
		    		data: [[0, parseFloat(lowerLoadLine)], [parseFloat(stroke), parseFloat(lowerLoadLine)]],
		    		marker: {
		    			enabled: false
		    		},
		    		states: {
		    			hover: {
		    				lineWidth: 0
		    			}
		    		},
		    		enableMouseTracking: true
		    	},{                                                                           
		            name: '',   
		            type: 'scatter',     // 散点图   
		            color: '#00ff00',   
		            lineWidth:3,
		            data:  pointdata                                                                                  
		        }]
	});
}

showRodPress = function(result, divid) {
	var wellName=result.wellName;                        // 井名
	var acqTime=result.acqTime;                    // 时间
	var rodStressRatio1=changeTwoDecimal(parseFloat(result.rodStressRatio1)*100);              // 一级应力百分比
	var rodStressRatio2=changeTwoDecimal(parseFloat(result.rodStressRatio2)*100);              // 二级应力百分比
	var rodStressRatio3=changeTwoDecimal(parseFloat(result.rodStressRatio3)*100);              // 三级应力百分比
	var rodStressRatio4=changeTwoDecimal(parseFloat(result.rodStressRatio4)*100);              // 四级应力百分比
	var yjg=cosog.string.rod1;   // 一级杆
	var ejg=cosog.string.rod2;   // 二级杆
	var sjg=cosog.string.rod3;   // 三级杆
	var sijg=cosog.string.rod4;   // 四级杆
	var xdata = "[";
	var ydata = "[";
	if(rodStressRatio1>0){
		xdata +="'" + yjg + "'" ;
		ydata +=rodStressRatio1 ;
		if(rodStressRatio2>0){
			xdata +=",'" + ejg + "'" ;
			ydata +="," + rodStressRatio2 ;
			if(rodStressRatio3>0){
				xdata +=",'" + sjg + "'" ;
				ydata +="," + rodStressRatio3 ;
				if(rodStressRatio4>0){
					xdata +=",'" + sijg + "'" ;
					ydata +="," + rodStressRatio4 ;
				}
			}
		}
	}
	xdata+="]";
	ydata+="]";
	var xdata2 = Ext.JSON.decode(xdata);
	var ydata2 = Ext.JSON.decode(ydata);
	initRodPressChart(xdata2, ydata2, wellName, acqTime, divid);
	return false;
}

function initRodPressChart(xdata, ydata, wellName, acqTime, divid) {
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'column',                      // 柱状图
		            renderTo : divid,                    // 图形放置的位置
		            zoomType: 'xy',                    // 沿xy轴放大
		            borderWidth : 0,
		            options3d: {                         // 3D效果
		                enabled: false,                   // 是否显示3D效果
		                alpha: 0,                        // 内旋角度
		                beta: 0,                         // 外旋角度
		                depth: 100,                       // 图形的全深比
		                frame: {
		                	back: {                      // X与Y形成的背面面板
		                		color: 'transparent',    // 面板颜色
		                		size: 1                  // 面板厚度
		                	},
		                	bottom: {                    // X与Z形成的底部面板
		                		color: '#fdfdfd',        // 面板颜色
		                		size: 0                  // 面板厚度
		                	},
		                	side: {                      // Y与Z形成的侧面面板
		                		color: '#fdfdfd',        // 面板颜色
		                		size: 2                  // 面板厚度
		                	}
		                },
		                viewDistance: 10                 // 图形前面看图的距离
		            }
		        },                                                                                   
		        title: {                                                                             
		            text: cosog.string.rodStress,              // 杆柱应力      
		            style: {
		            	fontSize: '13px'
		            }
		        },                                                                                   
		        subtitle: {                                                                          
		            text: wellName+' ['+acqTime+']'
		        },
		        colors: ['#00bc00','#006837', '#00FF00','#006837', '#00FF00','#006837'],
		        credits: {
		            enabled: false
		        },
		        xAxis: { 
		        	categories: xdata,
		            labels: {
		                rotation: 0,
		                align: 'center',
		                style: {
		                    fontSize: '12px',
		                    fontFamily: 'Verdana, sans-serif'
		                }
		            },
		            gridLineWidth: 0          // 网格线宽度
		        },                                                                                   
		        yAxis: {    
		        	min: 0,
		        	max:100,
		            title: {                                                                         
		                text: cosog.string.rodStressRatio  // 应力百分比(%)                                                          
		            },
		            allowDecimals: false,    // 刻度值是否为小数
		            minorTickInterval: ''    // 不显示次刻度线
		        },
		        exporting:{    
                    enabled:true,    
                    filename:'class-booking-chart',    
                    url:context + '/exportHighcharsPicController/export'
               },
		        legend: {                                                                            
		            enabled: false
		        }, 
		        plotOptions : {
		        	column: {  
		        		pointWidth: 40,                     // 柱子宽度
		        		borderWidth: 2
//		        		color: '#000000'
			        } 
				},
		        series: [{
		            name: cosog.string.rodStressRatio,  // 应力百分比(%)
		            data: ydata,
		            dataLabels: {
		                enabled: true,
		                rotation: 0,
		                color: '#0066cc',
		                align: 'center',
		                x: 0,
		                y: 0,
//		                zIndex:0,
		                style: {
		                    fontSize: '13px',
		                    fontFamily: 'SimSun'
		                }
		            }
		        }] 
	});
	SetEveryOnePointColor(mychart);           //设置每一个数据点的颜色值
}

function SetEveryOnePointColor(chart) {      // 设置每一个数据点的颜色横向渐变
	var colors = chart.options.colors;
	var pointsList = chart.series[0].points;         //获得第一个序列的所有数据点
    for (var i = 0; i < pointsList.length; i++) {    //遍历设置每一个数据点颜色
        chart.series[0].points[i].update({
            color: {
                linearGradient: { x1: 0, y1: 0, x2: 1, y2: 0 },     //横向渐变效果 如果将x2和y2值交换将会变成纵向渐变效果
                stops: [
                            [0, Highcharts.Color(colors[i*2]).setOpacity(1).get('rgba')],
                            [1, Highcharts.Color(colors[i*2+1]).setOpacity(1).get('rgba')]
                        ]  
            }
        });
    }
}

showPumpCard = function(result,divid) {
	var color=new Array("#00ff00","#ff0000","#ff8000","#ff06c5","#0000ff"); // 线条颜色
	var wellName=result.wellName;                        // 井名
	var acqTime=result.acqTime;                    // 时间
	var resultCode=result.resultCode;
	var pumpFSDiagramData=result.pumpFSDiagramData.split("#");       // 图形数据
	var series = "[";
	if(pumpFSDiagramData.length>0&&resultCode!=1232){
		series+="{";
		for (var i =0; i < pumpFSDiagramData.length; i++){
			var everyDiagramData = pumpFSDiagramData[i].split(",");
			var data = "[";
			for (var j=0; j <= everyDiagramData.length; j+=2) {
				if(j<everyDiagramData.length){
					data += "[" + everyDiagramData[j] + ","+everyDiagramData[j+1]+"],";
				}else{
					data += "[" + everyDiagramData[0] + ","+everyDiagramData[1]+"]";//将图形的第一个点拼到最后面，使图形闭合
				}
			}
			data+="]";
			if(i<(pumpFSDiagramData.length-1)){
				series+="name: ''," + "color: '" + color[i] + " ' , " + "lineWidth:3," + "data:" + data + "},{";
			}else{
				series+="name: ''," + "color: '" + color[i] + " ' , " + "lineWidth:3," + "data:" + data + "}";
			}
		}
	}
	series+="]";
	var pointdata = Ext.JSON.decode(series);
	title = cosog.string.pumpFSDiagram;  // 泵功图
	initMultiSurfaceCardChart(pointdata, title, wellName, acqTime, divid);
	return false;
}

function initMultiSurfaceCardChart(series, title, wellName, acqTime, divid,upperLoadLine,lowerLoadLine) {
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',
		            renderTo : divid,
		            borderWidth : 0,
		            zoomType: 'xy'
		        },                                                                                   
		        title: {  
		        	text: title
		        },                                                                                   
		        subtitle: {                                                                          
		            text: wellName+' ['+acqTime+']'                                                      
		        },
		        credits: {
		            enabled: false
		        },
		        xAxis: {                                                                             
		            title: {                                                                         
		                enabled: true,                                                               
		                text: cosog.string.position,    // 位移（m）
		                align:'middle',//"low"，"middle" 和 "high"，分别表示于最小值对齐、居中对齐、与最大值对齐
		                style: {
		                	fontSize: '12px',
		                	padding: '5px'
                      }
		            },  
		            startOnTick: false,      //是否强制轴线在标线处开始
		            endOnTick: false,        //是否强制轴线在标线处结束                                                                  
		            showLastLabel: true,
		            minorTickInterval: ''    // 最小刻度间隔
		            //min:0                                                            
		        },                                                                                   
		        yAxis: {                                                                             
		            title: {                                                                         
		                text: cosog.string.load                                    
		            },
		            allowDecimals: false, 
		            minorTickInterval: '',
//		            min:0,
		            plotLines: [{
	                    color: '#d12',
	                    dashStyle: 'Dash', //Dash,Dot,Solid,默认Solid
	                    label: {
	                        text: upperLoadLine,
	                        align: 'right',
	                        x: -10
	                    },
	                    width: 3,
	                    value: upperLoadLine,  //y轴显示位置
	                    zIndex: 10
	                },{
	                    color: '#d12',
	                    dashStyle: 'Dash',
	                    label: {
	                        text: lowerLoadLine,
	                        align: 'right',
	                        x: -10
	                    },
	                    width: 3,
	                    value: lowerLoadLine,  //y轴显示位置
	                    zIndex: 10
	                }]
		        },
		        exporting:{    
                    enabled:true,    
                    filename:'class-booking-chart',    
                    url:context + '/exportHighcharsPicController/export'
               },
		        legend: {                                                                            
		            layout: 'vertical',                                                              
		            align: 'left',                                                                   
		            verticalAlign: 'top',                                                            
		            x: 100,                                                                          
		            y: 70,                                                                           
		            floating: true,                                                                  
		            backgroundColor: '#FFFFFF',                                                      
		            borderWidth: 1  ,
		            enabled: false
		        },                                                                                   
		        plotOptions: {                                                                       
		            scatter: {                                                                       
		                marker: {                                                                    
		                    radius: 0,                                                               
		                    states: {                                                                
		                        hover: {                                                             
		                            enabled: true,                                                   
		                            lineColor: '#646464'                                    
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                states: {                                                                    
		                    hover: {                                                                 
		                        marker: {                                                            
		                            enabled: false                                                   
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                tooltip: {                                                                   
		                    headerFormat: '',                                
		                    pointFormat: '{point.x}, {point.y}'                                
		                }                                                                            
		            }                                                                                
		        }, 
		        series: series 
	});
}

showPumpEfficiency = function(bxzcData, divid) {
	var wellName=bxzcData.wellName;           // 井名
	var acqTime=bxzcData.acqTime;       // 时间
	var pumpEff1=bxzcData.pumpEff1;   // 冲程损失系数
	var pumpEff2=bxzcData.pumpEff2;       // 充满系数
	var pumpEff3=bxzcData.pumpEff3;       // 漏失系数
	var pumpEff4=bxzcData.pumpEff4;   // 液体收缩系数
	var ydata="[" + pumpEff1 + "," + pumpEff2 + "," + pumpEff3 + "," + pumpEff4 + "]";
	if(pumpEff1==0&&pumpEff2==0&&pumpEff3==0&&pumpEff4==0){
		ydata="[]";
	}
	ydata = Ext.JSON.decode(ydata);
	initPumpEfficiencyChart(ydata, wellName, acqTime, divid);
	return false;
}

function initPumpEfficiencyChart(ydata, wellName, acqTime, divid, title, yname) {
	$('#'+divid).highcharts({
				chart: {                                                                             
		            type: 'column',      
		            borderWidth : 0,
		            zoomType: 'xy'                   
		        },                                                                                   
		        title: {                                                                                      
		            text: cosog.string.pumpEff             
		        },
		        subtitle: {                                                                                   
		            text: wellName+' ['+acqTime+']'                                                      
		        },
		        colors: ['#66ffcc', '#009999', '#ffcc33', '#ff6633', '#00ffff', '#3366cc', '#ffccff', '#cc0000', '#6AF9C4'],
		        credits: {            
		            enabled: false
		        },
		        xAxis: { 
		        	categories: [
		        	                cosog.string.pumpEff1,
		        	                cosog.string.pumpEff2,
		        	                cosog.string.pumpEff3,
		        	                cosog.string.pumpEff4
		        	            ],
		        	gridLineWidth: 0
		        }, 
		        tooltip: {
		            enabled: false
		        },
		        yAxis: {    
		        	min: 0,
		            title: {                                                                         
		                text: cosog.string.percent                                           
		            },
		            minorTickInterval: ''
		        },
		        exporting:{    
                    enabled:true,    
                    filename:'class-booking-chart',    
                    url:context + '/exportHighcharsPicController/export'
               },
		        legend: {                                                                            
		            enabled: false
		        },  
		        series: [{
		            data: ydata,
		            dataLabels: {
		                enabled: true,
		                rotation: 0,
		                color: '#0066cc',
		                align: 'center',
		                x: 0,
		                y: 0,
		                style: {
		                    fontSize: '13px',
		                    fontFamily: 'SimSun'
		                }
		            }
		        }]
		        
	}, function (chart) {
        SetEveryOnePointColor(chart);
    });
}

showPSDiagram = function(result, divid,title) {
	if (!isNotVal(title)){
		title='电功图';
	}
	var positionCurveData=result.positionCurveData.split(",");
	var powerCurveData=result.powerCurveData.split(",");
	var xtext='<span style="text-align:center;">'+cosog.string.position+'<br />';
	if(result.upStrokeWattMax!=undefined && result.downStrokeWattMax!=undefined){
		xtext+='上冲程最大值:' + result.upStrokeWattMax + 'kW 下冲程最大值:'  + result.downStrokeWattMax + 'kW<br />';
	}
	if(result.wattDegreeBalance!=undefined){
		xtext+='平衡度:' + result.wattDegreeBalance + '%<br /></span>';
	}
	var data = "["; // 功图data
	var upStrokeData = "["; // 上冲程数据
	var downStrokeData = "["; // 下冲程数据
	var minIndex=0,maxIndex=0;
	var gtcount=positionCurveData.length; // 功图点数
	if(positionCurveData.length>0 && result.powerCurveData!="" && powerCurveData.length>1){
		for (var i=0; i <= positionCurveData.length; i++) {
			if(i<positionCurveData.length){
				data += "[" + changeTwoDecimal(positionCurveData[i]) + ","+changeTwoDecimal(powerCurveData[i])+"],";
			}else{
				data += "[" + changeTwoDecimal(positionCurveData[0]) + ","+changeTwoDecimal(powerCurveData[0])+"]";//将图形的第一个点拼到最后面，使图形闭合
			}
		}
		//获取最大位移和最小位移点数索引
		var minPos=100,maxPos=0;
		for (var i=0; i < gtcount; i++) {
			if(parseFloat(positionCurveData[i])<parseFloat(minPos)){
				minPos=parseFloat(positionCurveData[i]);
				minIndex=i;
			}
			if(parseFloat(positionCurveData[i])>parseFloat(maxPos)){
				maxPos=parseFloat(positionCurveData[i]);
				maxIndex=i;
			}
		}
		
		if(minIndex<=maxIndex){//如果最小值索引小于最大值索引
			for(var i=minIndex;i<=maxIndex;i++){
				upStrokeData += "[" + changeTwoDecimal(positionCurveData[i]) + ","+changeTwoDecimal(powerCurveData[i])+"]";
				if(i<maxIndex){
					upStrokeData+=",";
				}
			}
			var upStrokeCount=maxIndex-minIndex+1;//上冲程点数
			var downStrokeCount=gtcount-upStrokeCount;
			for(var i=0;i<downStrokeCount+2;i++){
				var index=i+maxIndex;
				if(index>(gtcount-1)){
					index=index-gtcount;
				}
				downStrokeData += "[" + changeTwoDecimal(positionCurveData[index]) + ","+changeTwoDecimal(powerCurveData[index])+"]";
				if(i<downStrokeCount+1){
					downStrokeData+=",";
				}
			}
		}else{//如果最小值索引大于最大值索引
			for(var i=maxIndex;i<=minIndex;i++){
				downStrokeData += "[" + changeTwoDecimal(positionCurveData[i]) + ","+changeTwoDecimal(powerCurveData[i])+"]";
				if(i<minIndex){
					downStrokeData+=",";
				}
			}
			var downStrokeCount=minIndex-maxIndex+1;//下冲程点数
			var upStrokeCount=gtcount-downStrokeCount;
			for(var i=0;i<upStrokeCount+2;i++){
				var index=i+minIndex;
				if(index>(gtcount-1)){
					index=index-gtcount;
				}
				upStrokeData +="[" + changeTwoDecimal(positionCurveData[index]) + ","+changeTwoDecimal(powerCurveData[index])+"]";
				if(i<upStrokeCount+1){
					upStrokeData+=",";
				}
			}
		}
		
	}
	data+="]";
	upStrokeData+="]";
	downStrokeData+="]";
	var pointdata = Ext.JSON.decode(data);
	var upStrokePointdata = Ext.JSON.decode(upStrokeData);
	var downStrokePointdata = Ext.JSON.decode(downStrokeData);
	initPSDiagramChart(upStrokePointdata,downStrokePointdata, result, divid,title,xtext,"有功功率(kW)",['#FF6633','#009999']);
	return false;
}

function initPSDiagramChart(upStrokePointdata,downStrokePointdata, gtdata, divid,title,xtext,ytext,color) {
	var wellName=gtdata.wellName;         // 井名
	var acqTime=gtdata.acqTime;     // 采集时间
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',     // 散点图   
		            renderTo : divid,
		            borderWidth : 0,
		            zoomType: 'xy',
		            reflow: true
		        },                                                                                   
		        title: {
		        	text: title          
		        },                                                                                   
		        subtitle: {
		        	text: wellName+' ['+acqTime+']'                                                      
		        },
		        credits: {
		            enabled: false
		        },
		        xAxis: {                                                                             
		            title: {                                                                         
		                text: xtext,    // 坐标+显示文字
		                useHTML: false,
		                margin:5,
                        style: {
                        	fontSize: '12px',
                            padding: '5px'
                        }
		            }, 
		            startOnTick: false,      //是否强制轴线在标线处开始
		            endOnTick: false,        //是否强制轴线在标线处结束                                                        
		            showLastLabel: true,
		            allowDecimals: false,    // 刻度值是否为小数
//		            min:0,
		            minorTickInterval: ''    // 最小刻度间隔
		        },                                                                                   
		        yAxis: {                                                                             
		            title: {                                                                         
		                text: ytext   // 载荷（kN） 
                    },
		            allowDecimals: false,    // 刻度值是否为小数
		            minorTickInterval: ''   // 不显示次刻度线
//		            min: 0                  // 最小值
		        },
		        exporting:{
                    enabled:true,    
                    filename:'class-booking-chart',    
                    url:context + '/exportHighcharsPicController/export'
               },
		        legend: {
		        	itemStyle:{
		        		fontSize: '8px'
		        	},
		            enabled: true,
		            layout: 'vertical',
					align: 'right',
					verticalAlign: 'top',
					x: 0,
					y: 55,
					floating: true
		        },                                                                                   
		        plotOptions: {                                                                       
		            scatter: {                                                                       
		                marker: {                                                                    
		                    radius: 0,                                                               
		                    states: {                                                                
		                        hover: {                                                             
		                            enabled: true,                                                   
		                            lineColor: '#646464'                                    
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                states: {                                                                    
		                    hover: {                                                                 
		                        marker: {                                                            
		                            enabled: false                                                   
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                tooltip: {                                                                   
		                    headerFormat: '',                                
		                    pointFormat: '{point.x},{point.y}'                                
		                }                                                                            
		            }                                                                                
		        },
		        series: [{                                                                           
		            name: '上冲程',                                                                  
		            color: color[0],   
		            lineWidth:3,
		            data:  upStrokePointdata                                                                                  
		        },{                                                                           
		            name: '下冲程',                                                                  
		            color: color[1],   
		            lineWidth:3,
		            data:  downStrokePointdata                                                                                  
		        }]
	});
}

showASDiagram = function(result, divid,title) {
	if (!isNotVal(title)){
		title='电流图';
	}
	var positionCurveData=result.positionCurveData.split(",");
	var currentCurveData=result.currentCurveData.split(",");
	
	var xtext='<span style="text-align:center;">'+cosog.string.position+'<br />';
    
	if(result.upStrokeIMax!=undefined && result.downStrokeIMax!=undefined){
		xtext+='上冲程最大值:' + result.upStrokeIMax + 'A 下冲程最大值:'  + result.downStrokeIMax + 'A<br />';
	}
	if(result.iDegreeBalance!=undefined){
		xtext+='平衡度:' + result.iDegreeBalance + '%<br /></span>';
	}
	var data = "["; // 功图data
	var upStrokeData = "["; // 上冲程数据
	var downStrokeData = "["; // 下冲程数据
	var minIndex=0,maxIndex=0;
	var gtcount=positionCurveData.length; // 功图点数
	if(positionCurveData.length>0 && result.currentCurveData!="" && currentCurveData.length>1){
		for (var i=0; i <= positionCurveData.length; i++) {
			if(i<positionCurveData.length){
				data += "[" + changeTwoDecimal(positionCurveData[i]) + ","+changeTwoDecimal(currentCurveData[i])+"],";
			}else{
				data += "[" + changeTwoDecimal(positionCurveData[0]) + ","+changeTwoDecimal(currentCurveData[0])+"]";//将图形的第一个点拼到最后面，使图形闭合
			}
		}
		
		//获取最大位移和最小位移点数索引
		var minPos=100,maxPos=0;
		for (var i=0; i < gtcount; i++) {
			if(parseFloat(positionCurveData[i])<parseFloat(minPos)){
				minPos=positionCurveData[i];
				minIndex=i;
			}
			if(positionCurveData[i]>parseFloat(maxPos)){
				maxPos=parseFloat(positionCurveData[i]);
				maxIndex=i;
			}
		}
		
		if(minIndex<=maxIndex){//如果最小值索引小于最大值索引
			for(var i=minIndex;i<=maxIndex;i++){
				upStrokeData += "[" + changeTwoDecimal(positionCurveData[i]) + ","+changeTwoDecimal(currentCurveData[i])+"]";
				if(i<maxIndex){
					upStrokeData+=",";
				}
			}
			var upStrokeCount=maxIndex-minIndex+1;//上冲程点数
			var downStrokeCount=gtcount-upStrokeCount;
			for(var i=0;i<downStrokeCount+2;i++){
				var index=i+maxIndex;
				if(index>(gtcount-1)){
					index=index-gtcount;
				}
				downStrokeData += "[" + changeTwoDecimal(positionCurveData[index]) + ","+changeTwoDecimal(currentCurveData[index])+"]";
				if(i<downStrokeCount+1){
					downStrokeData+=",";
				}
			}
		}else{//如果最小值索引大于最大值索引
			for(var i=maxIndex;i<=minIndex;i++){
				downStrokeData += "[" + changeTwoDecimal(positionCurveData[i]) + ","+changeTwoDecimal(currentCurveData[i])+"]";
				if(i<minIndex){
					downStrokeData+=",";
				}
			}
			var downStrokeCount=minIndex-maxIndex+1;//下冲程点数
			var upStrokeCount=gtcount-downStrokeCount;
			for(var i=0;i<upStrokeCount+2;i++){
				var index=i+minIndex;
				if(index>(gtcount-1)){
					index=index-gtcount;
				}
				upStrokeData +="[" + changeTwoDecimal(positionCurveData[index]) + ","+changeTwoDecimal(currentCurveData[index])+"]";
				if(i<upStrokeCount+1){
					upStrokeData+=",";
				}
			}
		}
	}
	data+="]";
	upStrokeData+="]";
	downStrokeData+="]";
	var pointdata = Ext.JSON.decode(data);
	var upStrokePointdata = Ext.JSON.decode(upStrokeData);
	var downStrokePointdata = Ext.JSON.decode(downStrokeData);
	initPSDiagramChart(upStrokePointdata,downStrokePointdata, result, divid,title,xtext,"电流(A)",['#CC0000','#0033FF']);
	return false;
}

function initPSDiagramChart(upStrokePointdata,downStrokePointdata, gtdata, divid,title,xtext,ytext,color) {
	var wellName=gtdata.wellName;         // 井名
	var acqTime=gtdata.acqTime;     // 采集时间
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',     // 散点图   
		            renderTo : divid,
		            borderWidth : 0,
		            zoomType: 'xy',
		            reflow: true
		        },                                                                                   
		        title: {
		        	text: title          
		        },                                                                                   
		        subtitle: {
		        	text: wellName+' ['+acqTime+']'                                                      
		        },
		        credits: {
		            enabled: false
		        },
		        xAxis: {                                                                             
		            title: {                                                                         
		                text: xtext,    // 坐标+显示文字
		                useHTML: false,
		                margin:5,
                        style: {
                        	fontSize: '12px',
                            padding: '5px'
                        }
		            }, 
		            startOnTick: false,      //是否强制轴线在标线处开始
		            endOnTick: false,        //是否强制轴线在标线处结束                                                        
		            showLastLabel: true,
		            allowDecimals: false,    // 刻度值是否为小数
//		            min:0,
		            minorTickInterval: ''    // 最小刻度间隔
		        },                                                                                   
		        yAxis: {                                                                             
		            title: {                                                                         
		                text: ytext   // 载荷（kN） 
                    },
		            allowDecimals: false,    // 刻度值是否为小数
		            minorTickInterval: ''   // 不显示次刻度线
//		            min: 0                  // 最小值
		        },
		        exporting:{
                    enabled:true,    
                    filename:'class-booking-chart',    
                    url:context + '/exportHighcharsPicController/export'
               },
		        legend: {
		        	itemStyle:{
		        		fontSize: '8px'
		        	},
		            enabled: true,
		            layout: 'vertical',
					align: 'right',
					verticalAlign: 'top',
					x: 0,
					y: 55,
					floating: true
		        },                                                                                   
		        plotOptions: {                                                                       
		            scatter: {                                                                       
		                marker: {                                                                    
		                    radius: 0,                                                               
		                    states: {                                                                
		                        hover: {                                                             
		                            enabled: true,                                                   
		                            lineColor: '#646464'                                    
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                states: {                                                                    
		                    hover: {                                                                 
		                        marker: {                                                            
		                            enabled: false                                                   
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                tooltip: {                                                                   
		                    headerFormat: '',                                
		                    pointFormat: '{point.x},{point.y}'                                
		                }                                                                            
		            }                                                                                
		        },
		        series: [{                                                                           
		            name: '上冲程',                                                                  
		            color: color[0],   
		            lineWidth:3,
		            data:  upStrokePointdata                                                                                  
		        },{                                                                           
		            name: '下冲程',                                                                  
		            color: color[1],   
		            lineWidth:3,
		            data:  downStrokePointdata                                                                                  
		        }]
	});
}

showBalanceAnalysisCurveChart = function(crankAngle,loadRorque,crankTorque,balanceTorque,netTorque,title,subtitle,divId) {
	var crankAngleArr=crankAngle.split(",");
	var loadRorqueArr=loadRorque.split(",");
	var crankTorqueArr=crankTorque.split(",");
	var balanceTorqueArr=balanceTorque.split(",");
	var netTorqueArr=netTorque.split(",");
	
	var legendName = ['载荷','曲柄','平衡块','净扭矩'];
	var catagories1 = "[";
    var series1 = "[";
    if(crankAngleArr.length>0){
    	var loadData="{\"name\":\"载荷\",\"visible\":false,\"data\":[";
    	var crankData="{\"name\":\"曲柄\",\"visible\":false,\"data\":[";
    	var balanceData="{\"name\":\"平衡块\",\"visible\":false,\"data\":[";
    	var netData="{\"name\":\"净扭矩\",\"data\":[";
        for(var i=0;i<crankAngleArr.length;i++){
        	catagories1+=crankAngleArr[i];
        	loadData+=changeTwoDecimal(loadRorqueArr[i]);
        	crankData+=changeTwoDecimal(crankTorqueArr[i]);
        	balanceData+=changeTwoDecimal(balanceTorqueArr[i]);
        	netData+=changeTwoDecimal(netTorqueArr[i]);
        	if(i<crankAngleArr.length-1){
        		catagories1+=",";
            	loadData+=",";
            	crankData+=",";
            	balanceData+=",";
            	netData+=",";
        	}
        }
    	loadData+="]}";
    	crankData+="]}";
    	balanceData+="]}";
    	netData+="]}";
    	series1+=loadData+","+crankData+","+balanceData+","+netData;
    }
    
    catagories1+="]";
    series1+="]";
    
    var cat1 = Ext.JSON.decode(catagories1);
	var ser1 = Ext.JSON.decode(series1);
	initBalanceCurveChart(cat1,ser1, divId,title,subtitle,"扭矩(kN*m)","曲柄转角(°)");
	return false;
}

function initBalanceCurveChart(catagories,series,divId,title,subtitle,ytext,xtext) {
	$('#'+divId).highcharts({
				chart : {
//					renderTo : divId,
					type : 'spline',
					shadow : false,
					borderWidth : 0,
					zoomType : 'xy'
				},
				credits : {
					enabled : false
				},
				title : {
					text : title
				},
				subtitle: {
		        	text: subtitle                                                      
		        },
				colors : ['#000000',// 黑
						'#0000FF',// 蓝
						'#008C00',// 绿
						'#800000',// 红
						'#F4BD82',// 黄
						'#FF00FF'// 紫
				],
				xAxis : {
					categories : catagories,
					tickInterval : 40,
					title : {
						text :xtext
					}
				},
				yAxis : {
//					min: 0,
					lineWidth : 1,
					title : {
						text :ytext,
						style : {
							color : '#000000',
							fontWeight : 'bold'
						}
					},
					labels : {
						formatter : function() {
//							return Highcharts.numberFormat(this.value, 2);
							return this.value;
						}
					},
					plotLines : [{
								value : 0,
								width : 1,
								zIndex:2,
								color : '#808080'
							}]
				},
				tooltip : {
					crosshairs : true,
					enabled : true,
					style : {
						color : '#333333',
						fontSize : '12px',
						padding : '8px'
					},
					formatter : function() {
						var seriesName=this.series.name;
						if(seriesName.indexOf("扭矩")==-1){
							seriesName=seriesName+"扭矩";
						}
						return '<b>' + seriesName + '</b><br/>' + this.x
								+ ': ' + this.y;
					},
					valueSuffix : ''
				},
				plotOptions : {
					 spline: {
						 lineWidth: 3,  
				         fillOpacity: 0.3,  
				         marker: {
				        	 enabled: true,
				        	 radius: 0,  //曲线点半径，默认是4
				            //symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
				             states: {
				            	 hover: {
				            		 enabled: true,  
				                     radius: 6
				                }  
				            }  
				        },  
				        shadow: true  
			        } 
				},
//				legend : {
//					layout : 'vertical',
//					align : 'right',
//					verticalAlign : 'middle',
//					borderWidth : 1
//				},
				
				legend : {
					itemDistance:10,
					align : 'center',
					verticalAlign : 'bottom',
					layout : 'horizontal' //vertical 竖直 horizontal-水平
				},
//				legend: {
//		        	itemStyle:{
//		        		fontSize: '8px'
//		        	},
//		            enabled: true,
//		            layout: 'vertical',
//					align: 'right',
//					verticalAlign: 'top',
//					x: 0,
//					y: 55,
//					floating: true
//		        },  
				series : series
			});
}

showBalanceAnalysisMotionCurveChart = function(crankAngle,position,polishrodV,polishrodA,title,subtitle,divId,type) {
	var crankAngleArr=crankAngle.split(","); 
    var positionArr=position.split(","); 
    var polishrodVArr=polishrodV.split(","); 
    var polishrodAArr=polishrodA.split(","); 
	var catagories = "[";
    var series = "[";
    var sData="{\"name\":\"位移\",\"yAxis\":0,\"data\":[";
	var vData="{\"name\":\"速度\",\"yAxis\":1,\"data\":[";
	var aData="{\"name\":\"加速度\",\"yAxis\":2,\"data\":[";
    if(crankAngleArr.length>0){
        for(var i=0;i<crankAngleArr.length;i++){
        	catagories+=crankAngleArr[i];
        	sData+=changeTwoDecimal(positionArr[i]);
        	vData+=changeTwoDecimal(polishrodVArr[i]);
        	aData+=changeTwoDecimal(polishrodAArr[i]);
        	if(i<crankAngleArr.length-1){
        		catagories+=",";
        		sData+=",";
        		vData+=",";
        		aData+=",";
        	}
        }
    }
    sData+="]}";
	vData+="]}";
	aData+="]}";
	if(type===1){
		series+=sData+","+vData+","+aData;
	}else{
		series+=sData+","+vData;
	}
    catagories+="]";
    series+="]";
    var cat = Ext.JSON.decode(catagories);
	var ser = Ext.JSON.decode(series);
	if(type===1){
		initBalanceCurveChartThreeY(cat,ser, divId,title,subtitle,"值","曲柄转角(°)");
	}else{
		initBalanceCurveChartTowY(cat,ser, divId,title,subtitle,"值","曲柄转角(°)");
	}
	
	return false;
}

function initBalanceCurveChartThreeY(catagories,series,divId,titletext,subtitle,ytext,xtext) {
	$('#'+divId).highcharts({
				chart : {
//					renderTo : divId,
					type : 'spline',
					shadow : false,
					borderWidth : 0,
					zoomType : 'xy'
				},
				credits : {
					enabled : false
				},
				title : {
					text : titletext
					// center
				},
				subtitle: {
		        	text: subtitle                                                   
		        },
				colors : ['#000000',// 黑
						'#0000FF',// 蓝
						'#008C00',// 绿
						'#800000',// 红
						'#F4BD82',// 黄
						'#FF00FF'// 紫
				],
				xAxis : {
					categories : catagories,
					tickInterval : 100,
					title : {
						text :xtext
					}
				},
				yAxis : [{
//					min: 0,
					lineWidth : 1,
					tickPosition:'inside',
					title : {
						text :'位移(m)',
						style : {
							color : '#000000',
							fontWeight : 'bold'
						}
					},
					labels : {
						formatter : function() {
//							return Highcharts.numberFormat(this.value, 2);
							return this.value;
						}
					},
					plotLines : [{
								value : 0,
								width : 1,
								zIndex:2,
								color : '#808080'
							}]
				},{
//					min: 0,
					lineWidth : 1,
					tickPosition:'inside',
					title : {
						text :'速度(m/s)',
						style : {
							color : '#000000',
							fontWeight : 'bold'
						}
					},
					labels : {
						formatter : function() {
//							return Highcharts.numberFormat(this.value, 2);
							return this.value;
						}
					},
					plotLines : [{
								value : 0,
								width : 1,
								zIndex:2,
								color : '#808080'
							}]
				},{
//					min: 0,
					lineWidth : 1,
					tickPosition:'inside',
					title : {
						text :'加速度(m/s²)',
						style : {
							color : '#000000',
							fontWeight : 'bold'
						}
					},
					labels : {
						formatter : function() {
//							return Highcharts.numberFormat(this.value, 2);
							return this.value;
						}
					},
					plotLines : [{
								value : 0,
								width : 1,
								zIndex:2,
								color : '#808080'
							}]
				}],
				tooltip : {
					crosshairs : true,
					enabled : true,
					style : {
						color : '#333333',
						fontSize : '12px',
						padding : '8px'
					},
					formatter : function() {
						return '<b>' + this.series.name + '</b><br/>' + this.x
								+ ': ' + this.y;
					},
					valueSuffix : ''
				},
				plotOptions : {
					 spline: {  
				            lineWidth: 3,  
				            fillOpacity: 0.3,  
				             marker: {  
				             enabled: true,  
				              radius: 0,  //曲线点半径，默认是4
                             //symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
				                states: {  
				                   hover: {  
				                        enabled: true,  
				                        radius: 6
				                    }  
				                }  
			            },  
			            shadow: true  
			        } 
				},
//				legend : {
//					layout : 'vertical',
//					align : 'right',
//					verticalAlign : 'middle',
//					borderWidth : 1
//				},
				legend : {
					align : 'center',
					verticalAlign : 'bottom',
					layout : 'horizontal' //vertical 竖直 horizontal-水平
				},
				series : series
			});
}

function initBalanceCurveChartTowY(catagories,series,divId,titletext,subtitle,ytext,xtext) {
	$('#'+divId).highcharts({
				chart : {
//					renderTo : divId,
					type : 'spline',
					shadow : false,
					borderWidth : 0,
					zoomType : 'xy'
				},
				credits : {
					enabled : false
				},
				title : {
					text : titletext
					// center
				},
				subtitle: {
		        	text: subtitle                                                   
		        },
				colors : ['#000000',// 黑
						'#0000FF',// 蓝
						'#008C00',// 绿
						'#800000',// 红
						'#F4BD82',// 黄
						'#FF00FF'// 紫
				],
				xAxis : {
					categories : catagories,
					tickInterval : 100,
					title : {
						text :xtext
					}
				},
				yAxis : [{
//					min: 0,
					lineWidth : 1,
//					tickPosition:'inside',
					title : {
						text :'位移(m)',
						style : {
							color : '#000000',
							fontWeight : 'bold'
						}
					},
					labels : {
						formatter : function() {
//							return Highcharts.numberFormat(this.value, 2);
							return this.value;
						}
					},
					plotLines : [{
								value : 0,
								width : 1,
								zIndex:2,
								color : '#808080'
							}]
				},{
//					min: 0,
					lineWidth : 1,
//					tickPosition:'inside',
					opposite:true,
					title : {
						text :'速度(m/s)',
						style : {
							color : '#000000',
							fontWeight : 'bold'
						}
					},
					labels : {
						formatter : function() {
//							return Highcharts.numberFormat(this.value, 2);
							return this.value;
						}
					},
					plotLines : [{
								value : 0,
								width : 1,
								zIndex:2,
								color : '#808080'
							}]
				}],
				tooltip : {
					crosshairs : true,
					enabled : true,
					style : {
						color : '#333333',
						fontSize : '12px',
						padding : '8px'
					},
					formatter : function() {
						return '<b>' + this.series.name + '</b><br/>' + this.x
								+ ': ' + this.y;
					},
					valueSuffix : ''
				},
				plotOptions : {
					 spline: {  
				            lineWidth: 3,  
				            fillOpacity: 0.3,  
				             marker: {  
				             enabled: true,  
				              radius: 0,  //曲线点半径，默认是4
                             //symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
				                states: {  
				                   hover: {  
				                        enabled: true,  
				                        radius: 6
				                    }  
				                }  
			            },  
			            shadow: true  
			        } 
				},
//				legend : {
//					layout : 'vertical',
//					align : 'right',
//					verticalAlign : 'middle',
//					borderWidth : 1
//				},
				legend : {
					align : 'center',
					verticalAlign : 'bottom',
					layout : 'horizontal' //vertical 竖直 horizontal-水平
				},
				series : series
			});
}

/* 
 * 拼接叠加功图曲线数据
 */
showFSDiagramOverlayChart = function(get_rawData,divid,visible,diagramType) {
	var color=new Array("#000000","#00ff00"); // 线条颜色
	var list=get_rawData.totalRoot;
	var upperLoadLine=null;
	var lowerLoadLine=null;
	var fmax=null;
	var fmin=null;
	var strokeMax=0;
	var visiblestr='';
	if(!visible){
		visiblestr='visible:false,';
	};
	if(list.length>0){
		fmax=list[0].fmax;
		fmin=list[0].fmin;
	}
	var title='';
	var ytext='';
	var color=new Array("#000000","#00ff00"); // 线条颜色
	var subtitle=get_rawData.wellName+"["+get_rawData.start_date+"~"+get_rawData.end_date+"]";
	if(diagramType===0){//如果是功图
		title='光杆功图叠加';
		ytext='载荷(kN)';
	}else if(diagramType===1){//电功图
		title= "电功图叠加";
		ytext="有功功率(kW)"
	}else if(diagramType===2){//电流图
		title= "电流图叠加";
		ytext="电流(A)"
	}
	
	var minValue=null;
	var series = "[";
	for (var i =0; i < list.length; i++){
		if(list[i].upperLoadLine!="" && parseFloat(list[i].upperLoadLine)>0){
			upperLoadLine=list[i].upperLoadLine;
		}
		if(list[i].lowerLoadLine!="" && parseFloat(list[i].lowerLoadLine)>0){
			lowerLoadLine=list[i].lowerLoadLine;
		}
		
		
		if(parseFloat(list[i].fmax)>fmax){
			fmax=parseFloat(list[i].fmax);
		}
		if(parseFloat(list[i].fmin)<fmin){
			fmin=parseFloat(list[i].fmin);
		}
		if(parseFloat(list[i].stroke)>strokeMax){
			strokeMax=parseFloat(list[i].stroke);
		}
		var xData = list[i].positionCurveData.split(",");
		var yData;
		if(diagramType===0){//如果是功图
			yData = list[i].loadCurveData.split(",");
//			var hour=list[i].acqTime.split(' ')[1].split(':')[0]
//			if(parseInt(hour)<6 ){
//				color=new Array("#000000","#000000");
//			}else if(parseInt(hour)>=6 && parseInt(hour)<12){
//				color=new Array("#000000","#dd1212");
//			}else if(parseInt(hour)>=12 && parseInt(hour)<18){
//				color=new Array("#000000","#507fea");
//			}else{
//				color=new Array("#000000","#00ff00");
//			}
			color=new Array("#000000","#00ff00");
			minValue=0;
		}else if(diagramType===1){//电功图
			yData = list[i].powerCurveData.split(",");
			color=new Array("#000000","#CC0000");
		}else if(diagramType===2){//电流图
			yData = list[i].currentCurveData.split(",");
			color=new Array("#000000","#0033FF");
		}
		var data = "[";
		for (var j=0; j <= xData.length; j++) {
			if(j<(xData.length)){
				data += "[" + changeTwoDecimal(xData[j]) + ","+changeTwoDecimal(yData[j])+"],";
			}else{
				data += "[" + changeTwoDecimal(xData[0]) + ","+changeTwoDecimal(yData[0])+"]";//将图形的第一个点拼到最后面，使图形闭合
			}
		}
		data+="]";
		if(list.length==1){
			    series+="{name: '"+list[i].id+"',visible:"+visible + ",color: '" + color[1] + " ' , " + "lineWidth:2," + "data:" + data + "}";
		}else{
			if(i==0){
				series+="{name: '"+list[i].id+"',visible:"+visible + ",color: '" + color[1] + " ' , " + "lineWidth:2," + "data:" + data + "},";
			}else if((i>0)&&(i<(list.length-1))){
	            series+="{name: '"+list[i].id+"',visible:"+visible + ",color: '" + color[1] + " ' , " + "lineWidth:2," + "data:" + data + "},";
	        }else{
				series+="{name: '"+list[i].id+"',visible:"+visible + ",color: '" + color[1] + " ' , " + "lineWidth:2," + "data:" + data + "}";
			}
		}
	}
	
	if(strokeMax>0 && diagramType===0){//如果是功图
		series+=",{type: 'line',color: '#d12',dashStyle: 'Dash',lineWidth:2,name: '理论上载荷线',data: [[0," +parseFloat(upperLoadLine)+"], ["+parseFloat(strokeMax)+", "+parseFloat(upperLoadLine)+"]],marker: {enabled: false},states: {hover: {lineWidth: 0}},enableMouseTracking: true}";
		series+=",{type: 'line',color: '#d12',dashStyle: 'Dash',lineWidth:2,name: '理论下载荷线',data: [[0," +parseFloat(lowerLoadLine)+"], ["+parseFloat(strokeMax)+", "+parseFloat(lowerLoadLine)+"]],marker: {enabled: false},states: {hover: {lineWidth: 0}},enableMouseTracking: true}";
	}
	
	series+="]";
	
	var pointdata = Ext.JSON.decode(series);
	
	var upperlimit=parseFloat(fmax)+5;
    if(parseFloat(upperLoadLine)==0||parseFloat(fmax)==0){
    	upperlimit=null;
    }else if(parseFloat(upperLoadLine)>=parseFloat(fmax)){
    	upperlimit=parseFloat(upperLoadLine)+5;
    }
    var underlimit=parseFloat(fmin)-5;
    if(parseFloat(lowerLoadLine)==0||parseFloat(fmin)==0){
    	underlimit=null;
    }else if(parseFloat(lowerLoadLine)<=parseFloat(fmin)){
    	underlimit=parseFloat(lowerLoadLine)-5;
    }
    if(underlimit<0){
    	underlimit=0;
    }
    underlimit=0;
    if(isNaN(upperlimit)){
    	upperlimit=null;
    }
    if(diagramType===0){//如果是功图
    	initFSDiagramOverlayChart(pointdata, title,subtitle,ytext,get_rawData.wellName, get_rawData.calculateDate, divid,upperLoadLine,lowerLoadLine,upperlimit,underlimit,strokeMax);
	}else {
		initPSDiagramOverlayChart(pointdata, title,subtitle,ytext,get_rawData.wellName, get_rawData.calculateDate, divid);
	}
	
	return false;
}

function initFSDiagramOverlayChart(series, title,subtitle,ytext, wellName, acqTime, divid,upperLoadLine,lowerLoadLine,upperlimit,underlimit,strokeMax) {
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',      // 散点图   
		            renderTo : divid,
		            borderWidth : 0,
		            zoomType: 'xy'
		        },                                                                                   
		        title: {  
		        	text: title
		        },                                                                                   
		        subtitle: {                                                                          
		            text: subtitle//+' ['+acqTime+']'                                                      
		        },
		        credits: {
		            enabled: false
		        },
		        xAxis: {                                                                             
		            title: {                                                                         
		                enabled: true,                                                               
		                text: cosog.string.position,    // 位移（m）
		                align:'middle',//"low"，"middle" 和 "high"，分别表示于最小值对齐、居中对齐、与最大值对齐
		                style: {
//                          color: '#000',
//                          fontWeight: 'normal',
		                	fontSize: '12px',
		                	padding: '5px'
                      }
		            },  
		            startOnTick: false,      //是否强制轴线在标线处开始
		            endOnTick: false,        //是否强制轴线在标线处结束                                                                  
		            showLastLabel: true,
		            minorTickInterval: ''    // 最小刻度间隔
		            //min:0                                                            
		        },                                                                                   
		        yAxis: {                                                                             
		            title: {                                                                         
		                text: ytext    // 载荷（kN）                                                          
		            },
		            allowDecimals: false,    // 刻度值是否为小数
		            //endOnTick: false,        //是否强制轴线在标线处结束   
		            minorTickInterval: '',    // 不显示次刻度线
		            min:0
		        },
		        exporting:{    
                    enabled:true,    
                    filename:'class-booking-chart',    
                    url:context + '/exportHighcharsPicController/export'
               },
		        legend: {                                                                            
		            layout: 'vertical',                                                              
		            align: 'left',                                                                   
		            verticalAlign: 'top',                                                            
		            x: 100,                                                                          
		            y: 70,                                                                           
		            floating: true,                                                                  
		            backgroundColor: '#FFFFFF',                                                      
		            borderWidth: 1  ,
		            enabled: false
		        },                                                                                   
		        plotOptions: {                                                                       
		            scatter: {                                                                       
		                marker: {                                                                    
		                    radius: 0,                                                               
		                    states: {                                                                
		                        hover: {                                                             
		                            enabled: true,                                                   
		                            lineColor: '#646464'                                    
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                states: {                                                                    
		                    hover: {                                                                 
		                        marker: {                                                            
		                            enabled: false                                                   
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                tooltip: {                                                                   
		                    headerFormat: '',                                
		                    pointFormat: '{point.x}, {point.y}'                                
		                }                                                                            
		            }                                                                                
		        }, 
		        series: series 
	});
}

function initPSDiagramOverlayChart(series, title,subtitle,ytext, wellName, acqTime, divid) {
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',      // 散点图   
		            renderTo : divid,
		            borderWidth : 0,
		            zoomType: 'xy'
		        },                                                                                   
		        title: {  
		        	text: title
		        },                                                                                   
		        subtitle: {                                                                          
		            text: subtitle//+' ['+acqTime+']'                                                      
		        },
		        credits: {
		            enabled: false
		        },
		        xAxis: {                                                                             
		            title: {                                                                         
		                enabled: true,                                                               
		                text: cosog.string.position,    // 位移（m）
		                align:'middle',//"low"，"middle" 和 "high"，分别表示于最小值对齐、居中对齐、与最大值对齐
		                style: {
		                	fontSize: '12px',
		                	padding: '5px'
                      }
		            },  
		            startOnTick: false,      //是否强制轴线在标线处开始
		            endOnTick: false,        //是否强制轴线在标线处结束                                                                  
		            showLastLabel: true,
		            minorTickInterval: ''    // 最小刻度间隔
		            //min:0                                                            
		        },                                                                                   
		        yAxis: {                                                                             
		            title: {                                                                         
		                text: ytext                                             
		            },
		            allowDecimals: false,    // 刻度值是否为小数
		            //endOnTick: false,        //是否强制轴线在标线处结束   
		            minorTickInterval: ''    // 不显示次刻度线
		        },
		        exporting:{    
                    enabled:true,    
                    filename:'class-booking-chart',    
                    url:context + '/exportHighcharsPicController/export'
               },
		        legend: {
		            enabled: false
		        },                                                                                   
		        plotOptions: {                                                                       
		            scatter: {                                                                       
		                marker: {                                                                    
		                    radius: 0,                                                               
		                    states: {                                                                
		                        hover: {                                                             
		                            enabled: true,                                                   
		                            lineColor: '#646464'                                    
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                states: {                                                                    
		                    hover: {                                                                 
		                        marker: {                                                            
		                            enabled: false                                                   
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                tooltip: {                                                                   
		                    headerFormat: '',                                
		                    pointFormat: '{point.x}, {point.y}'                                
		                }                                                                            
		            }                                                                                
		        }, 
		        series: series 
	});
};