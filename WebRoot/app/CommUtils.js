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
					 	    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.deleteSuccessfully);
						}
					 	if(result.flag ==false){		 		 
					 		Ext.Msg.msg(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.deleteFailure+"</font>");
					 	}
					 	  
					},
					failure : function() {
						    Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);			    
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
	if (val == undefined || val == null 
			||val==""||val=="null"||val=="undefined"
			||val.length==0
			|| (val.replace!=undefined&&val.replace(/\s+/g,"") == "") 
			|| (val.replace!=undefined&&val.replace(/\s+/g,"") == "null" )
			|| (val.replace!=undefined&&val.replace(/\s+/g,"") == "undefined")
			
	
	
	) {
		result = false;
	} else {
		result = true;
	}
	return result;
}

isEquals = function(v1,v2) {
	var result = false;
	if( (!isNotVal(v1)) && (!isNotVal(v2)) ){
		result = true;
		return result;
	}
	if(v1==v2){
		result = true;
		return result;
	}
	if(JSON.stringify(v1)==JSON.stringify(v2)){
		result = true;
		return result;
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
		Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.exception+"</font> "+loginUserLanguageResource.noExist+"")
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
				msg : '<div style="padding-top:20px">' + msg + ','+loginUserLanguageResource.loading+'</div>',
				progressText : loginUserLanguageResource.loading,
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
						+ "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm+"";
				if (result.flag == true) {
					Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.deleteSuccessfully);
				}
				if (result.flag == false) {
					Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.deleteFailure+"</font>");
				}
				if(grid_id=="OrgInfoTreeGridView_Id"){
					var store=Ext.getCmp(grid_id).getStore()
	                store.proxy.extraParams.tid = 0;
	                store.load();
				}
				Ext.getCmp(grid_id).getStore().load();
			},
			failure : function() {
				Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
			}
		});
	}else{
		Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.deleteFailure+"</font>");
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
	var deviceName=o.data.deviceName;
	var deviceId=o.data.deviceId;
	var details=o.data.details;
	var calculateType=o.data.calculateType;
	var resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
			"onclick=callBackHistoryData(\""+recordId+"\",\""+deviceId+"\",\""+deviceName+"\",\""+calculateType+"\")>"+details+"...</a>";
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

var callBackHistoryData = function(recordId,deviceId,deviceName,calculateType) {
	var HistoryQueryDataDetailsWindow = Ext.create("AP.view.historyQuery.HistoryQueryDataDetailsWindow");
	Ext.getCmp("HistoryQueryDataDetailsWindowRecord_Id").setValue(recordId);
	Ext.getCmp("HistoryQueryDataDetailsWindowDeviceId_Id").setValue(deviceId);
	Ext.getCmp("HistoryQueryDataDetailsWindowDeviceName_Id").setValue(deviceName);
	Ext.getCmp("HistoryQueryDataDetailsWindowDeviceCalculateType_Id").setValue(calculateType);
	HistoryQueryDataDetailsWindow.show();
}

function openExcelWindow(url) {
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
    heads = loginUserLanguageResource.idx+"," + heads;
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
                    filename:title,    
                    sourceWidth: $("#"+divId)[0].offsetWidth,
                    sourceHeight: $("#"+divId)[0].offsetHeight
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
                    filename:title,    
                    sourceWidth: $("#"+divId)[0].offsetWidth,
                    sourceHeight: $("#"+divId)[0].offsetHeight
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
                    filename:title,    
                    sourceWidth: $("#"+divId)[0].offsetWidth,
                    sourceHeight: $("#"+divId)[0].offsetHeight
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
			+ context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
	Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'"
			+ context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.cancel;
	Ext.Msg.confirm(loginUserLanguageResource.tip, "是否确定退出后台管理系统？", function(btn) {
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
			+ context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
	Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'"
			+ context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.cancel;
	Ext.Msg.confirm(loginUserLanguageResource.tip, loginUserLanguageResource.exitConfirm, function(btn) {
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
									window.location.href = context+ "/login";
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

//添加监听 设置树的节点选择的级联关系
var listenerTreeCheckAssociatedParentNode = function(node, checked) {
	childHasChecked(node, checked);
	var parentNode = node.parentNode;
	if (parentNode != null) {
		parentCheck(parentNode, checked);
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
 	if(isNotVal(AlarmShowStyle) && AlarmShowStyle!={}){
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
 	}
 	if(isNotVal(tipval)){
 		return '<span data-qtip="'+tipval+'" data-dismissDelay=10000>'+val+'</span>';
 	}
}
 
 adviceTimeFormat = function(val,o,p,e) {
	 	var reslut="";
	 	val=val.split(".")[0];
	 	var reslut=val;
	 	if(isNotVal(reslut)){
	 		return '<span data-qtip="'+reslut+'">'+reslut+'</span>';
	 	}
	    
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
	 	if(isNotVal(alarmShowStyle) && alarmShowStyle!={}){
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
	 	}
	 	
	 	if(isNotVal(tipval)){
		 	return '<span data-qtip="'+tipval+'" data-dismissDelay=10000>'+val+'</span>';
	 	}
	}
 
 adviceCommStatusColor = function(val,o,p,e) {
	 	var commStatus=p.data.commStatus;
	 	var tipval=val;
	 	var alarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
	 	var alarmLevel=p.data.commAlarmLevel==undefined?0:p.data.commAlarmLevel;
	 	var backgroundColor='#FFFFFF';
	 	var color='#000000';
	 	var opacity=1;
	 	if(isNotVal(alarmShowStyle) && alarmShowStyle!={}){
	 		if (commStatus == 0) {
		 		backgroundColor='#'+alarmShowStyle.Comm.offline.BackgroundColor;
		 		color='#'+alarmShowStyle.Comm.offline.Color;
		 		opacity=alarmShowStyle.Comm.offline.Opacity
			}else if (commStatus == 1) {
				backgroundColor='#'+alarmShowStyle.Comm.online.BackgroundColor;
		 		color='#'+alarmShowStyle.Comm.online.Color;
		 		opacity=alarmShowStyle.Comm.online.Opacity
			}else if (commStatus == 2) {
				backgroundColor='#'+alarmShowStyle.Comm.goOnline.BackgroundColor;
		 		color='#'+alarmShowStyle.Comm.goOnline.Color;
		 		opacity=alarmShowStyle.Comm.goOnline.Opacity
			}
		 	var rgba=color16ToRgba(backgroundColor,opacity);
		 	o.style='background-color:'+rgba+';color:'+color+';';
	 	}
	 	if(isNotVal(tipval)){
	 		return '<span data-qtip="'+tipval+'" data-dismissDelay=10000>'+val+'</span>';
	 	}
	}
 
 adviceUpCommStatusColor = function(val,o,p,e) {
	 	var commStatus=p.data.upCommStatus;
	 	var tipval=val;
	 	var alarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
	 	var alarmLevel=p.data.commAlarmLevel==undefined?0:p.data.commAlarmLevel;
	 	var backgroundColor='#FFFFFF';
	 	var color='#000000';
	 	var opacity=1;
	 	if(isNotVal(alarmShowStyle) && alarmShowStyle!={}){
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
	 	}
	 	if(isNotVal(tipval)){
	 		return '<span data-qtip="'+tipval+'" data-dismissDelay=10000>'+val+'</span>';
	 	}
	}
 
 adviceDownCommStatusColor = function(val,o,p,e) {
	 	var commStatus=p.data.downCommStatus;
	 	var tipval=val;
	 	var alarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
	 	var alarmLevel=p.data.commAlarmLevel==undefined?0:p.data.commAlarmLevel;
	 	var backgroundColor='#FFFFFF';
	 	var color='#000000';
	 	var opacity=1;
	 	if(isNotVal(alarmShowStyle) && alarmShowStyle!={}){
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
	 	}
	 	if(isNotVal(tipval)){
	 		return '<span data-qtip="'+tipval+'" data-dismissDelay=10000>'+val+'</span>';
	 	}
	}
 
 adviceRunStatusColor = function(val,o,p,e) {
	 	var commStatus=p.data.commStatus;
	 	var runStatus=p.data.runStatus;
	 	var tipval=val;
	 	var alarmLevel=p.data.runAlarmLevel==undefined?0:p.data.runAlarmLevel;
	 	var alarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
		if (commStatus == 0 || commStatus == 2 || val=='') {
//			o.css='pendingColor';
			return '';
		} else {
			var backgroundColor='#FFFFFF';
		 	var color='#000000';
		 	var opacity=1;
		 	if(isNotVal(alarmShowStyle) && alarmShowStyle!={}){
		 		if (runStatus == 0) {
			 		backgroundColor='#'+alarmShowStyle.Run.stop.BackgroundColor;
			 		color='#'+alarmShowStyle.Run.stop.Color;
			 		opacity=alarmShowStyle.Run.stop.Opacity;
			 		val=loginUserLanguageResource.stop;
				}else if (runStatus == 1) {
					backgroundColor='#'+alarmShowStyle.Run.run.BackgroundColor;
			 		color='#'+alarmShowStyle.Run.run.Color;
			 		opacity=alarmShowStyle.Run.run.Opacity;
			 		val=loginUserLanguageResource.run;
				}else if (runStatus == 2) {
					backgroundColor='#'+alarmShowStyle.Run.noData.BackgroundColor;
			 		color='#'+alarmShowStyle.Run.noData.Color;
			 		opacity=alarmShowStyle.Run.noData.Opacity;
			 		val=loginUserLanguageResource.emptyMsg;
				}
			 	tipval=val;
			 	var rgba=color16ToRgba(backgroundColor,opacity);
			 	o.style='background-color:'+rgba+';color:'+color+';';
		 	}
		 	if(isNotVal(tipval)){
			 	return '<span data-qtip="'+tipval+'" data-dismissDelay=10000>'+val+'</span>';
		 	}
		}
	}
 adviceResultStatusColor = function(val,o,p,e) {
	 	var tipval=val;
	 	var alarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
	 	var alarmLevel=p.data.resultAlarmLevel==undefined?0:p.data.resultAlarmLevel;
	 	var backgroundColor='#FFFFFF';
	 	var color='#000000';
	 	var opacity=1;
	 	if(isNotVal(alarmShowStyle) && alarmShowStyle!={}){
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
	 	}
	 	if(isNotVal(tipval)){
		 	return '<span data-qtip="'+tipval+'" data-dismissDelay=10000>'+val+'</span>';
	 	}
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
	 	if(isNotVal(alarmShowStyle) && alarmShowStyle!={}){
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
	 	}
	 	
	 	if(isNotVal(tipval)){
	 		return '<span data-qtip="'+tipval+'" data-dismissDelay=10000>'+val+'</span>';
	 	}
	}
 
 adviceRealtimeMonitoringDataColor = function (val, o, p, e) {
	    var alarmInfo = p.data.alarmInfo;
	    var alarmLevel=0;
	    var alarmShowStyle = Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
	    var column = o.column.dataIndex;
	    if(isNotVal(alarmShowStyle) && alarmShowStyle!={}){
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
	    }
	    if (val == undefined) {
	        val = '';
	    }
	    var tipval = val;
	    if(isNotVal(tipval)){
		    return '<span data-qtip="' + tipval + '" data-dismissDelay=10000>' + val + '</span>';
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
		} else if (attr.dataIndex == 'id') {
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
		if (isNotVal(attr.flex)) {
			myColumns+=",flex:" + attr.flex;
		}
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

function initContinuousDiagramChart(pointdata, divId,title,subtitle,xtext,ytext,color) {
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',     // 散点图   
		            renderTo : divId,
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
                    filename:title,    
                    sourceWidth: $("#"+divId)[0].offsetWidth,
                    sourceHeight: $("#"+divId)[0].offsetHeight
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

showRealtimeWorkStastPieChart = function(store, divId,title,name) {
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
	initRealtimeWorkStastPieChart(title, name, data, divId);
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
    heads = loginUserLanguageResource.idx+"," + heads;
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

function handsontableDataCheck_RodType(val, callback,row,col,handsontableHelper){
	if(val==''
		||val.toUpperCase()=='钢杆'
		||val.toUpperCase()=='玻璃钢杆'
		||val.toUpperCase()=='空心抽油杆'){
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

function handsontableDataCheck_IpPort_Nullable(val, callback,row,col,handsontableHelper){
	var ipPattern=/^(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])$/;
	var ipv6Pattern=/^\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:)))(%.+)?\s*$/;
	var portPattern=/^([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-4]\d{4}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/;
	var ipPortPattern=/^(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\:([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-4]\d{4}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/;
	var ipv6PortPattern=/^\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:)))(%.+)?\s*\:([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-4]\d{4}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/;
	
	if(val==='' || val==null || ipPortPattern.test(val) || ipv6PortPattern.test(val)){
		return callback(true);
	}else{
		var cell = handsontableHelper.hot.getCell(row, col);  
        cell.style.background = "#f09614";
		return callback(false);
	}
};

function handsontableDataCheck_HexStr_Nullable(val, callback,row,col,handsontableHelper){
	var pattern=/^(0x|0X)?[a-fA-F0-9]+$/;
	
	if(val==='' || val==null || pattern.test(val) ){
		return callback(true);
	}else{
		var cell = handsontableHelper.hot.getCell(row, col);  
        cell.style.background = "#f09614";
		return callback(false);
	}
};

function handsontableDataCheck_Cancel(val, callback,row,col,handsontableHelper){
	var pattern=/^(0x|0X)?[a-fA-F0-9]+$/;
	return callback(true);
};

/^(0x|0X)?[a-fA-F0-9]+$/

function handsontableDataCheck_IdAndIpPort(val, callback,row,col,handsontableHelper){
	if(val==''|| val==null){
		return callback(true);
	}else{
		var columns=handsontableHelper.columns;
		var tcpTypeColIndex=-1;
		for(var i=0;i<columns.length;i++){
			if(columns[i].data.toUpperCase() === "tcpType".toUpperCase()){
				tcpTypeColIndex=i;
				break;
	    	}
		}
		if(tcpTypeColIndex>=0){
			var tcpType=handsontableHelper.hot.getDataAtCell(row,tcpTypeColIndex);
			var cell = handsontableHelper.hot.getCell(row, col);  
			var prop=columns[col].data;
			if(tcpType==''|| tcpType==null){
				return callback(true);
			}else{
				if(prop.toUpperCase() === "signInId".toUpperCase()){
					if(tcpType.toUpperCase() === "TCP Client".toUpperCase() || tcpType.toUpperCase() === "TCPClient".toUpperCase()){
						return callback(true);
					}else{
						cell.style.background = "#f5f5f5";
						return callback(false);
					}
				}else if(prop.toUpperCase() === "ipPort".toUpperCase()){
					if(tcpType.toUpperCase() === "TCP Server".toUpperCase() || tcpType.toUpperCase() === "TCPServer".toUpperCase()){
						var ipPortPattern=/^(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\:([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-4]\d{4}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/;
						var ipv6PortPattern=/^\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:)))(%.+)?\s*\:([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-4]\d{4}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/;
						
						if(ipPortPattern.test(val) || ipv6PortPattern.test(val)){
							return callback(true);
						}else{
					        cell.style.background = "#f09614";
							return callback(false);
						}
					}else{
						cell.style.background = "#f5f5f5";
						return callback(false);
					}
				}else if(prop.toUpperCase() === "tcpType".toUpperCase()){
					return callback(true);
				}
			}
		}else{
			return callback(true);
		}
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
            filename: title,
            sourceWidth: $("#"+divId)[0].offsetWidth,
            sourceHeight: $("#"+divId)[0].offsetHeight
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

function foreachAndSearchTabAbsolutePath(tabStoreData, deviceTypeId) {
	var rtnArr=[];
	var rtnStr="";
	const foreachAndSearchTabAbsolutePathname=function(storeData, id) {
		if(storeData){
			for(let record of storeData){
				if(record.data.deviceTypeId===id){
					if(record.parentNode){
						foreachAndSearchTabAbsolutePathname(storeData,record.parentNode.data.deviceTypeId);
					}
					rtnArr.push(record.data.text);
				}else{
//					if(record.childNodes){
//						foreachAndSearchOrgAbsolutePathname(record.childNodes,deviceTypeId);
//					}
				}
			}
		}
	};
	foreachAndSearchTabAbsolutePathname(tabStoreData, deviceTypeId);
	for(var i=0;i<rtnArr.length;i++){
		rtnStr+=rtnArr[i];
		if(i<rtnArr.length-1){
			rtnStr+="/";
		}
	}
	return rtnStr;
}

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
	}
	if(sStr.length==1){
		sStr='0'+sStr;
	}
	return dateStr+' '+hStr+":"+mStr+':'+sStr;
};

showSurfaceCard = function(result, divId) {
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
	initSurfaceCardChart(pointdata, result, divId);
	return false;
}

/* 
 * 在泵功图中提取光杆功图
 */
showFSDiagramFromPumpcard = function(result, divId) {
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
	initSurfaceCardChart(pointdata,result, divId);
	return false;
}

function initSurfaceCardChart(pointdata, gtdata, divId) {
	var deviceName=gtdata.deviceName;         // 井名
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
	var optimizationSuggestion=gtdata.optimizationSuggestion;     // 优化建议
	var xtext='<span style="text-align:center;">'+loginUserLanguageResource.displacement+'(m)'+'<br />';
	var productionUnitStr='t/d';
    if(productionUnit!=0){
    	productionUnitStr='m^3/d';
    }
    xtext+=loginUserLanguageResource.pointCount+':'+pointCount+" ";
    xtext+=loginUserLanguageResource.fMax+':'+fmax+'kN ';
    xtext+=loginUserLanguageResource.fMin+':'+fmin+'kN ';
    xtext+=loginUserLanguageResource.stroke+':'+stroke+'m ';
    xtext+=loginUserLanguageResource.SPM+':'+spm+'/min ';
    xtext+=loginUserLanguageResource.liquidProduction+':'+liquidProduction+productionUnitStr+' ';
    xtext+=loginUserLanguageResource.FSDiagramWorkType+':'+resultName+' ';
    xtext+=loginUserLanguageResource.optimizationSuggestion+':'+optimizationSuggestion;
    var upperlimit=parseFloat(fmax)+10;
    if(parseFloat(upperLoadLine)>=parseFloat(fmax)){
    	upperlimit=parseFloat(upperLoadLine)+10;
    }
    if(isNaN(upperlimit)){
    	upperlimit=null;
    }
	mychart = new Highcharts.Chart({
				chart: {
		            renderTo : divId,
		            zoomType: 'xy',
		            borderWidth : 0,
		            reflow: true
		        },                                                                                   
		        title: {
		        	text: loginUserLanguageResource.FSDiagram  // 光杆功图                        
		        },                                                                                   
		        subtitle: {
		        	text: deviceName+' ['+acqTime+']'                                                      
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
		            minorTickInterval: ''    // 最小刻度间隔
		        },                                                                                   
		        yAxis: {                                                                             
		            title: {                                                                         
		                text: loginUserLanguageResource.load+'(kN)'   // 载荷（kN） 
                    },
		            allowDecimals: false,    // 刻度值是否为小数
		            minorTickInterval: '',   // 不显示次刻度线
		            min: 0                  // 最小值
		        },
		        exporting:{
                    enabled:true,    
                    filename:deviceName+loginUserLanguageResource.FSDiagram+'-'+acqTime,    
                    sourceWidth: $("#"+divId)[0].offsetWidth,
                    sourceHeight: $("#"+divId)[0].offsetHeight
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
		    		name: loginUserLanguageResource.upperLoadLine,
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
		    		name: loginUserLanguageResource.lowerLoadLine,
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

showRodPress = function(result, divId) {
	var deviceName=result.deviceName;                        // 井名
	var acqTime=result.acqTime;                    // 时间
	var rodStressRatio1=changeTwoDecimal(parseFloat(result.rodStressRatio1)*100);              // 一级应力百分比
	var rodStressRatio2=changeTwoDecimal(parseFloat(result.rodStressRatio2)*100);              // 二级应力百分比
	var rodStressRatio3=changeTwoDecimal(parseFloat(result.rodStressRatio3)*100);              // 三级应力百分比
	var rodStressRatio4=changeTwoDecimal(parseFloat(result.rodStressRatio4)*100);              // 四级应力百分比
	var rod1=loginUserLanguageResource.rod1;   // 一级杆
	var rod2=loginUserLanguageResource.rod2;   // 二级杆
	var rod3=loginUserLanguageResource.rod3;   // 三级杆
	var rod4=loginUserLanguageResource.rod4;   // 四级杆
	var xdata = "[";
	var ydata = "[";
	if(rodStressRatio1>0){
		xdata +="'" + rod1 + "'" ;
		ydata +=rodStressRatio1 ;
		if(rodStressRatio2>0){
			xdata +=",'" + rod2 + "'" ;
			ydata +="," + rodStressRatio2 ;
			if(rodStressRatio3>0){
				xdata +=",'" + rod3 + "'" ;
				ydata +="," + rodStressRatio3 ;
				if(rodStressRatio4>0){
					xdata +=",'" + rod4 + "'" ;
					ydata +="," + rodStressRatio4 ;
				}
			}
		}
	}
	xdata+="]";
	ydata+="]";
	var xdata2 = Ext.JSON.decode(xdata);
	var ydata2 = Ext.JSON.decode(ydata);
	initRodPressChart(xdata2, ydata2, deviceName, acqTime, divId);
	return false;
}

function initRodPressChart(xdata, ydata, deviceName, acqTime, divId) {
	var rodStressChart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'column',                      // 柱状图
		            renderTo : divId,                    // 图形放置的位置
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
		            text: loginUserLanguageResource.rodStress,              // 杆柱应力      
		            style: {
		            	fontSize: '13px'
		            }
		        },                                                                                   
		        subtitle: {                                                                          
		            text: deviceName+' ['+acqTime+']'
		        },
		        colors: ['#00bc00','#006837', '#00FF00','#006837', '#00FF00','#006837', '#00FF00','#006837'],
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
		                text: loginUserLanguageResource.rodStressRatio+'(%)'  // 应力百分比(%)                                                          
		            },
		            allowDecimals: false,    // 刻度值是否为小数
		            minorTickInterval: ''    // 不显示次刻度线
		        },
		        exporting:{    
                    enabled:true,    
                    filename:deviceName+loginUserLanguageResource.rodStress+"-"+acqTime,    
                    sourceWidth: $("#"+divId)[0].offsetWidth,
                    sourceHeight: $("#"+divId)[0].offsetHeight
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
		            name: loginUserLanguageResource.rodStressRatio+'(%)',  // 应力百分比(%)
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
	SetEveryOnePointColor(rodStressChart);           //设置每一个数据点的颜色值
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

showPumpCard = function(result,divId) {
	var color=new Array("#00ff00","#ff0000","#ff8000","#ff06c5","#0000ff"); // 线条颜色
	var deviceName=result.deviceName;                        // 井名
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
	title = loginUserLanguageResource.pumpFSDiagram;  // 泵功图
	initMultiSurfaceCardChart(pointdata, title, deviceName, acqTime, divId);
	return false;
}

function initMultiSurfaceCardChart(series, title, deviceName, acqTime, divId,upperLoadLine,lowerLoadLine) {
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',
		            renderTo : divId,
		            borderWidth : 0,
		            zoomType: 'xy'
		        },                                                                                   
		        title: {  
		        	text: title
		        },                                                                                   
		        subtitle: {                                                                          
		            text: deviceName+' ['+acqTime+']'                                                      
		        },
		        credits: {
		            enabled: false
		        },
		        xAxis: {                                                                             
		            title: {                                                                         
		                enabled: true,                                                               
		                text: loginUserLanguageResource.displacement+'(m)',    // 位移（m）
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
		                text: loginUserLanguageResource.load+'(kN)'
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
                    filename:deviceName+loginUserLanguageResource.pumpFSDiagram+"-"+acqTime,    
                    sourceWidth: $("#"+divId)[0].offsetWidth,
                    sourceHeight: $("#"+divId)[0].offsetHeight
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

showPumpEfficiency = function(bxzcData, divId) {
	var deviceName=bxzcData.deviceName;           // 井名
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
	initPumpEfficiencyChart(ydata, deviceName, acqTime, divId);
	return false;
}

function initPumpEfficiencyChart(ydata, deviceName, acqTime, divId, title, yname) {
	$('#'+divId).highcharts({
				chart: {                                                                             
		            type: 'column',      
		            borderWidth : 0,
		            zoomType: 'xy'                   
		        },                                                                                   
		        title: {                                                                                      
		            text: loginUserLanguageResource.pumpEfficiencyComposition
		        },
		        subtitle: {                                                                                   
		            text: deviceName+' ['+acqTime+']'                                                      
		        },
		        colors: ['#66ffcc', '#009999', '#ffcc33', '#ff6633', '#00ffff', '#3366cc', '#ffccff', '#cc0000', '#6AF9C4'],
		        credits: {            
		            enabled: false
		        },
		        xAxis: { 
		        	categories: [
		        		loginUserLanguageResource.pumpEff1,
		        		loginUserLanguageResource.pumpEff2,
		        		loginUserLanguageResource.pumpEff3,
		        		loginUserLanguageResource.pumpEff4
		        	],
		        	gridLineWidth: 0
		        }, 
		        tooltip: {
		            enabled: false
		        },
		        yAxis: {    
		        	min: 0,
		            title: {                                                                         
		                text: loginUserLanguageResource.percent+'(%)'                                          
		            },
		            minorTickInterval: ''
		        },
		        exporting:{    
                    enabled:true,    
                    filename:deviceName+loginUserLanguageResource.pumpEfficiencyComposition+"-"+acqTime,    
                    sourceWidth: $("#"+divId)[0].offsetWidth,
                    sourceHeight: $("#"+divId)[0].offsetHeight
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

showPSDiagram = function(result, divId,title) {
	if (!isNotVal(title)){
		title=loginUserLanguageResource.FWattDiagram;
	}
	var positionCurveData=result.positionCurveData.split(",");
	var powerCurveData=result.powerCurveData.split(",");
	var xtext='<span style="text-align:center;">'+loginUserLanguageResource.displacement+'(m)'+'<br />';
	if(result.upStrokeWattMax!=undefined && result.downStrokeWattMax!=undefined){
		xtext+=loginUserLanguageResource.upStrokeMaxValue+':' + result.upStrokeWattMax + 'kW '+loginUserLanguageResource.downStrokeMaxValue+':'  + result.downStrokeWattMax + 'kW<br />';
	}
	if(result.wattDegreeBalance!=undefined){
		xtext+=loginUserLanguageResource.degreeBalance+':' + result.wattDegreeBalance + '%<br /></span>';
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
	initPSDiagramChart(upStrokePointdata,downStrokePointdata, result, divId,title,xtext,loginUserLanguageResource.activePower+"(kW)",['#FF6633','#009999']);
	return false;
}

function initPSDiagramChart(upStrokePointdata,downStrokePointdata, gtdata, divId,title,xtext,ytext,color) {
	var deviceName=gtdata.deviceName;         // 井名
	var acqTime=gtdata.acqTime;     // 采集时间
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',     // 散点图   
		            renderTo : divId,
		            borderWidth : 0,
		            zoomType: 'xy',
		            reflow: true
		        },                                                                                   
		        title: {
		        	text: title          
		        },                                                                                   
		        subtitle: {
		        	text: deviceName+' ['+acqTime+']'                                                      
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
                    filename: deviceName+''+title+'-'+acqTime,
                    sourceWidth: $("#"+divId)[0].offsetWidth,
                    sourceHeight: $("#"+divId)[0].offsetHeight
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
		            name: loginUserLanguageResource.upStroke,                                                                  
		            color: color[0],   
		            lineWidth:3,
		            data:  upStrokePointdata                                                                                  
		        },{                                                                           
		            name: loginUserLanguageResource.downStroke,                                                                  
		            color: color[1],   
		            lineWidth:3,
		            data:  downStrokePointdata                                                                                  
		        }]
	});
}

showASDiagram = function(result, divId,title) {
	if (!isNotVal(title)){
		title=loginUserLanguageResource.FIDiagram;
	}
	var positionCurveData=result.positionCurveData.split(",");
	var currentCurveData=result.currentCurveData.split(",");
	
	var xtext='<span style="text-align:center;">'+loginUserLanguageResource.displacement+'(m)'+'<br />';
    
	if(result.upStrokeIMax!=undefined && result.downStrokeIMax!=undefined){
		xtext+=loginUserLanguageResource.upStrokeMaxValue+':' + result.upStrokeIMax + 'A '
		+loginUserLanguageResource.downStrokeMaxValue+':'  + result.downStrokeIMax + 'A<br />';
	}
	if(result.iDegreeBalance!=undefined){
		xtext+=loginUserLanguageResource.degreeBalance+':' + result.iDegreeBalance + '%<br /></span>';
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
	initASDiagramChart(upStrokePointdata,downStrokePointdata, result, divId,title,xtext,loginUserLanguageResource.electricity+"(A)",['#CC0000','#0033FF']);
	return false;
}

function initASDiagramChart(upStrokePointdata,downStrokePointdata, gtdata, divId,title,xtext,ytext,color) {
	var deviceName=gtdata.deviceName;         // 井名
	var acqTime=gtdata.acqTime;     // 采集时间
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',     // 散点图   
		            renderTo : divId,
		            borderWidth : 0,
		            zoomType: 'xy',
		            reflow: true
		        },                                                                                   
		        title: {
		        	text: title          
		        },                                                                                   
		        subtitle: {
		        	text: deviceName+' ['+acqTime+']'                                                      
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
                    filename: deviceName+''+title+'-'+acqTime,   
                    sourceWidth: $("#"+divId)[0].offsetWidth,
                    sourceHeight: $("#"+divId)[0].offsetHeight
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
		            name: loginUserLanguageResource.upStroke,                                                                  
		            color: color[0],   
		            lineWidth:3,
		            data:  upStrokePointdata                                                                                  
		        },{                                                                           
		            name: loginUserLanguageResource.downStroke,                                                                  
		            color: color[1],   
		            lineWidth:3,
		            data:  downStrokePointdata                                                                                  
		        }]
	});
}

showBalanceAnalysisCurveChart = function(crankAngle,loadRorque,crankTorque,balanceTorque,netTorque,title,deviceName,acqTime,divId) {
	var crankAngleArr=crankAngle.split(",");
	var loadRorqueArr=loadRorque.split(",");
	var crankTorqueArr=crankTorque.split(",");
	var balanceTorqueArr=balanceTorque.split(",");
	var netTorqueArr=netTorque.split(",");
	
	var legendName = [loginUserLanguageResource.load,loginUserLanguageResource.crankTorque,loginUserLanguageResource.balanceTorque,loginUserLanguageResource.netTorque];
	var catagories1 = "[";
    var series1 = "[";
    if(crankAngleArr.length>0){
    	var loadData="{\"name\":\""+loginUserLanguageResource.load+"\",\"visible\":false,\"data\":[";
    	var crankData="{\"name\":\""+loginUserLanguageResource.crankTorque+"\",\"visible\":false,\"data\":[";
    	var balanceData="{\"name\":\""+loginUserLanguageResource.balanceTorque+"\",\"visible\":false,\"data\":[";
    	var netData="{\"name\":\""+loginUserLanguageResource.netTorque+"\",\"data\":[";
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
	initBalanceCurveChart(cat1,ser1, divId,title,deviceName,acqTime,loginUserLanguageResource.torque+"(kN*m)",loginUserLanguageResource.crankAngle+"(°)");
	return false;
}

function initBalanceCurveChart(catagories,series,divId,title,deviceName,acqTime,ytext,xtext) {
	$('#'+divId).highcharts({
				chart : {
//					renderTo : divId,
					type : 'spline',
					shadow : false,
					borderWidth : 0,
					zoomType : 'xy'
				},
				exporting:{ 
		            enabled:true,    
		            filename: deviceName+''+title+'-'+acqTime,   
		            sourceWidth: $("#"+divId)[0].offsetWidth,
		            sourceHeight: $("#"+divId)[0].offsetHeight
				},
				credits : {
					enabled : false
				},
				title : {
					text : title
				},
				subtitle: {
					text: deviceName+' ['+acqTime+']'                                                  
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
showFSDiagramOverlayChart = function(get_rawData,divId,visible,diagramType) {
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
	var subtitle=get_rawData.deviceName+"["+get_rawData.start_date+"~"+get_rawData.end_date+"]";
	if(diagramType===0){//如果是功图
		title=loginUserLanguageResource.FSDiagramOverlay;
		ytext=loginUserLanguageResource.load+'(kN)';
	}else if(diagramType===1){//电功图
		title= loginUserLanguageResource.WSDiagramOverlay;
		ytext=loginUserLanguageResource.activePower+"(kW)"
	}else if(diagramType===2){//电流图
		title= loginUserLanguageResource.ISDiagramOverlay;
		ytext=loginUserLanguageResource.electricity+"(A)"
	}
	
	var minValue=null;
	var series = "[";
	for (var i =0; i < list.length; i++){
		if(i==0){
			if(list[i].upperLoadLine!="" && parseFloat(list[i].upperLoadLine)>0){
				upperLoadLine=list[i].upperLoadLine;
			}
			if(list[i].lowerLoadLine!="" && parseFloat(list[i].lowerLoadLine)>0){
				lowerLoadLine=list[i].lowerLoadLine;
			}
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
		var diagramPoint=xData.length;
		if(diagramType===0){//如果是功图
			yData = list[i].loadCurveData.split(",");
			color=new Array("#000000","#00ff00");
			minValue=0;
		}else if(diagramType===1){//电功图
			yData = list[i].powerCurveData.split(",");
			color=new Array("#000000","#CC0000");
		}else if(diagramType===2){//电流图
			yData = list[i].currentCurveData.split(",");
			color=new Array("#000000","#0033FF");
		}
		if(diagramPoint>yData.length){
			diagramPoint=yData.length;
		}
		var data = "[";
		for (var j=0; j <= diagramPoint; j++) {
			if(j<diagramPoint){
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
    	initFSDiagramOverlayChart(pointdata, title,subtitle,ytext,get_rawData.deviceName, get_rawData.calculateDate, divId,upperLoadLine,lowerLoadLine,upperlimit,underlimit,strokeMax);
	}else {
		initPSDiagramOverlayChart(pointdata, title,subtitle,ytext,get_rawData.deviceName, get_rawData.calculateDate, divId);
	}
	
	return false;
}

function initFSDiagramOverlayChart(series, title,subtitle,ytext, deviceName, acqTime, divId,upperLoadLine,lowerLoadLine,upperlimit,underlimit,strokeMax) {
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',      // 散点图   
		            renderTo : divId,
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
		                text: loginUserLanguageResource.displacement+'(m)',    // 位移（m）
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
                    filename:title,    
                    sourceWidth: $("#"+divId)[0].offsetWidth,
                    sourceHeight: $("#"+divId)[0].offsetHeight
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

function initPSDiagramOverlayChart(series, title,subtitle,ytext, deviceName, acqTime, divId) {
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',      // 散点图   
		            renderTo : divId,
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
		                text: loginUserLanguageResource.displacement+'(m)',    // 位移（m）
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
                    filename:title,    
                    sourceWidth: $("#"+divId)[0].offsetWidth,
                    sourceHeight: $("#"+divId)[0].offsetHeight
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

function jsonFormat(txt, compress /*是否为压缩模式*/ ) {
    /* 格式化JSON源码(对象转换为JSON文本) */
    var indentChar = '    ';
    if (/^\s*$/.test(txt)) {
        //数据为空,无法格式化
        return;
    }
    // 替换\r\n 换行
    txt = txt.replace(/\\r/g, "CRAPAPI_R");
    txt = txt.replace(/\\n/g, "CRAPAPI_N");
    txt = txt.replace(/\\t/g, "CRAPAPI_T");
    var data;
    try {
        data = $.parseJSON(txt);
    } catch (e) {
        //数据源语法错误,格式化失败
        return;
    };
    var draw = [],
        last = false,
        This = this,
        line = compress ? '' : '\n',
        nodeCount = 0,
        maxDepth = 0;

    var notify = function (name, value, isLast, indent /*缩进*/ , formObj) {
        nodeCount++; /*节点计数*/
        for (var i = 0, tab = ''; i < indent; i++)
            tab += indentChar; /* 缩进HTML */
        tab = compress ? '' : tab; /*压缩模式忽略缩进*/
        maxDepth = ++indent; /*缩进递增并记录*/
        if (value && value.constructor == Array) {
            /*处理数组*/
            draw.push(tab + (formObj ? ('"' + name + '":') : '') + '[' + line); /*缩进'[' 然后换行*/
            for (var i = 0; i < value.length; i++)
                notify(i, value[i], i == value.length - 1, indent, false);
            draw.push(tab + ']' + (isLast ? line : (',' + line))); /*缩进']'换行,若非尾元素则添加逗号*/
        } else if (value && typeof value == 'object') {
            /*处理对象*/
            draw.push(tab + (formObj ? ('"' + name + '":') : '') + '{' + line); /*缩进'{' 然后换行*/
            var len = 0,
                i = 0;
            for (var key in value)
                len++;
            for (var key in value)
                notify(key, value[key], ++i == len, indent, true);
            draw.push(tab + '}' + (isLast ? line : (',' + line))); /*缩进'}'换行,若非尾元素则添加逗号*/
        } else {
            if (typeof value == 'string') {
                value = value.replace(/\"/gm, '\\"');
                // 替换\r\n 换行
                value = value.replace(/CRAPAPI_R/g, "\\r");
                value = value.replace(/CRAPAPI_N/g, "\\n");
                value = value.replace(/CRAPAPI_T/g, "\\t");

                value = '"' + value + '"';
            }
            draw.push(tab + (formObj ? ('"' + name + '":') : '') + value +
                (isLast ? '' : ',') + line);
        };
    };
    var isLast = true,
        indent = 0;
    notify('', data, isLast, indent, false);
    return draw.join('');
};

function timeStr2TimeStamp(timeStr,format) {
	timeStr=timeStr.replace(/-/g, '/');
	var timeStamp= Date.parse(timeStr);
	return timeStamp;
};

function timestamp2Str (timestamp) {
    let time = new Date(timestamp);
    let year = time.getFullYear();
    let month = time.getMonth() + 1;
    let date = time.getDate();
    let hours = time.getHours();
    let minute = time.getMinutes();
    let second = time.getSeconds();
    let millisecond=time.getMilliseconds();
    
    if (month < 10) {
     month = '0' + month 
    }

    if (date < 10) {
     date = '0' + date 
    }

    if (hours < 10) {
    	hours = '0' + hours 
     }

    if (minute < 10) {
    	minute = '0' + minute 
    }

    if (second < 10) {
    	second = '0' + second 
    }
    
    if (millisecond < 10) {
    	millisecond = '00' + millisecond 
    }else if (millisecond >= 10 && millisecond < 100) {
    	millisecond = '0' + millisecond 
    }

    return year + '-' + month + '-' + date + ' ' + hours + ':' + minute + ':' + second+'.'+millisecond;
}

function getBrowserType(){
	const explorer = window.navigator.userAgent;
	if(explorer.indexOf("MSIE")>=0){
		return 1;//IE
	}else if(explorer.indexOf("Firefox")>=0){
		return 2;//Firefox
	}else if(explorer.indexOf("Chrome")>=0){
		return 3;//Chrome
	}else if(explorer.indexOf("Opera")>=0){
		return 4;//Opera
	}else if(explorer.indexOf("Safari")>=0){
		return 5;//Safari
	}else{
		return 6;
	}
}

function getBrowserType2(){
	if(window.ActiveXObject){
		return 1;//IE
	}else if(document.getBoxObjectFor){
		return 2;//Firefox
	}else if(document.MessageEvent && !document.getBoxObjectFor){
		return 3;//Chrome
	}else if(window.opera){
		return 4;//Opera
	}else if(window.openDatabase){
		return 5;//safair
	}else{
		return 6;
	}
}

function isBrowserFullScreen(){
	return document.fullScreen || document.mozFullScreen || document.webkitIsFullScreen;
}

function highchartsResize(divId){
	if(isNotVal($("#"+divId))){
		var charts=$("#"+divId).highcharts();
		if(charts!=undefined){
			var isFullScreen = isBrowserFullScreen();
			var browserType=getBrowserType();
			if(browserType==5||(!isFullScreen)){
//				charts.setSize($("#"+divId).offsetWidth, $("#"+divId).offsetHeight,true);
				charts.reflow();
			}
		}
	}
}

function refreshRealtimeDeviceListDataByPage(selectedDeviceId,deviceType,gridPanel,storeName){
	var loadPage=1;
	var pageSize=50;
	var gridStore=null;
	
	if (isNotVal(gridPanel)) {
		gridStore=gridPanel.getStore();
		gridPanel.getSelectionModel().deselectAll(true);
	}else{
		gridStore=Ext.create(storeName);
	}
	pageSize=gridStore.getPageSize();
	if(selectedDeviceId>0){
		loadPage=getDeviceRealTimeOverviewDataPage(selectedDeviceId,deviceType,pageSize);
	}
	if(gridStore!=null){
		gridStore.loadPage(loadPage);
	}
}

function refreshHistoryDeviceListDataByPage(selectedDeviceId,deviceType,gridPanel,storeName){
	var loadPage=1;
	var pageSize=50;
	var gridStore=null;
	
	if (isNotVal(gridPanel)) {
		gridStore=gridPanel.getStore();
	}else{
		gridStore=Ext.create(storeName);
	}
	pageSize=gridStore.getPageSize();
	if(selectedDeviceId>0){
		loadPage=getHistoryQueryDeviceListDataPage(selectedDeviceId,deviceType,pageSize);
	}
	if(gridStore!=null){
		gridStore.loadPage(loadPage);
	}
}

function exportDataMask(key,panelId,info){
	var flagUrl=context + '/reportDataMamagerController/getSessionFlag?key='+key;
	Ext.getCmp(panelId).el.mask(info).show();
	var exportFlatTime = setInterval(function () {
	    $.post(flagUrl, null, function (result) {
	            var flag = result.flag;
	            if (flag == "1") {
	                clearInterval(exportFlatTime);
	            	Ext.getCmp(panelId).getEl().unmask();
	            }
	        },"json");
	}, 1000);
}

function getTabPanelActiveId(tabTanelId){
	var activeId='';
	var tabPanel = Ext.getCmp(tabTanelId);
	if(isNotVal(tabPanel)){
		if(tabPanel.getActiveTab().xtype=='tabpanel'){
			activeId=tabPanel.getActiveTab().activeTab.id;
		}else{
			activeId=tabPanel.getActiveTab().id;
		}
	}
	return activeId;
}

function getTabPanelActiveId_first(tabTanelId){
	var activeId='';
	var tabPanel = Ext.getCmp(tabTanelId);
	if(isNotVal(tabPanel)){
		activeId=tabPanel.getActiveTab().id;
	}
	return activeId;
}

function getDeviceTypeFromTabId(tabTanelId){
	var deviceType=0;
	var activeId=getTabPanelActiveId(tabTanelId);
	if(isNotVal(activeId)){
		if(activeId.split('_').length==2){
			deviceType=activeId.split('_')[1];
		}
	}
	return deviceType;
}

function getDeviceTypeFromTabId_first(tabTanelId){
	var deviceType=0;
	var activeId=getTabPanelActiveId_first(tabTanelId);
	if(isNotVal(activeId)){
		if(activeId.split('_').length==2){
			deviceType=activeId.split('_')[1];
		}
	}
	return deviceType;
}

function getTabPanelActiveName(tabTanelId){
	var activeName='';
	var tabPanel = Ext.getCmp(tabTanelId);
	if(isNotVal(tabPanel)){
		if(tabPanel.getActiveTab().xtype=='tabpanel'){
			if(isNotVal(tabPanel.getActiveTab().activeTab.tpl) && isNotVal(tabPanel.getActiveTab().activeTab.tpl.html) ){
				activeName=tabPanel.getActiveTab().activeTab.tpl.html;
			}else{
				activeName=tabPanel.getActiveTab().activeTab.title;
			}
		}else{
			if(isNotVal(activeName=tabPanel.getActiveTab().title) && isNotVal(activeName=tabPanel.getActiveTab().title.html) ){
				activeName=activeName=tabPanel.getActiveTab().tpl.html;
			}else{
				activeName=activeName=tabPanel.getActiveTab().title;
			}
		}
	}
	return activeName;
}

function getDeviceTypeActiveId(){
	var globalDeviceType=Ext.getCmp("selectedDeviceType_global").getValue();
    var firstActiveTab=0;
    var secondActiveTab=0;
    
    if(globalDeviceType!=0 && globalDeviceType!='' && globalDeviceType!='0'){
    	if(tabInfo.children!=undefined && tabInfo.children!=null && tabInfo.children.length>0){
    		for(var i=0;i<tabInfo.children.length;i++){
    			var exit=false;
    			
    			if(tabInfo.children[i].children!=undefined && tabInfo.children[i].children!=null && tabInfo.children[i].children.length>0){
    				var allSecondIds='';
    				var childrenLength=tabInfo.children[i].children.length;
    				for(var j=0;j<tabInfo.children[i].children.length;j++){
    					if(j==0){
            				allSecondIds+=tabInfo.children[i].children[j].deviceTypeId;
                		}else{
                			allSecondIds+=(','+tabInfo.children[i].children[j].deviceTypeId);
                		}
    					if(isNumber(globalDeviceType) && parseInt(globalDeviceType) ==tabInfo.children[i].children[j].deviceTypeId){
        					firstActiveTab=i;
        					secondActiveTab=childrenLength>1?j+1:j;
        					exit=true;
        				}
    				}
    				//判断是否选中的是全部
    				if(!exit){
    					if(globalDeviceType==allSecondIds){
        					firstActiveTab=i;
        					secondActiveTab=0;
        					exit=true;
        				}
    				}
    			}else{
    				if( isNumber(globalDeviceType) && parseInt(globalDeviceType) ==tabInfo.children[i].deviceTypeId ){
    					firstActiveTab=i;
    					secondActiveTab=0;
    					exit=true;
    				}
    			}
    			
    			if(exit){
    				break;
    			}
    		}
    	}
    }
	
	
	var deviceTypeActiveId={};
	deviceTypeActiveId.firstActiveTab=firstActiveTab;
	deviceTypeActiveId.secondActiveTab=secondActiveTab;

	return deviceTypeActiveId;
}



function getCalculateTypeDeviceCount(orgId,deviceType,calculateType){
	var deviceCount=0;
	Ext.Ajax.request({
		method:'POST',
		async :  false,
		url:context + '/realTimeMonitoringController/getCalculateTypeDeviceCount',
		success:function(response) {
			deviceCount = Ext.JSON.decode(response.responseText).deviceCount;
		},
		failure:function(){
		},
		params: {
			orgId: orgId,
			deviceType:deviceType,
			calculateType:calculateType
        }
	});
	return deviceCount;
}

function getRoleModuleRight(moduleCode){
	var moduleRight=null;
	Ext.Ajax.request({
		method:'POST',
		async :  false,
		url:context + '/roleManagerController/getRoleModuleRight',
		success:function(response) {
			moduleRight = Ext.JSON.decode(response.responseText);
		},
		failure:function(){
		},
		params: {
			moduleCode: moduleCode
        }
	});
	return moduleRight;
}

function getStringLength(str) {
    var realLength = 0, len = str.length, charCode = -1;
    for (var i = 0; i < len; i++) {
      charCode = str.charCodeAt(i);
      if (charCode >= 0 && charCode <= 128)
        realLength += 1;
      else
        realLength += 2;
    }
    return realLength;
  }