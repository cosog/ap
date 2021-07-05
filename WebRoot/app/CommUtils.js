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
						selectReeTextRsult.push("'"+x_node_seId+"'");
						// 递归
						if (childArrNode.childNodes != null) {
							selectEachTreeText(childArrNode.childNodes);
						}
					});
		}
	} else {
		if (isNotVal(chlidArray)) {
			var x_node_seId = chlidArray.data.text;
			selectReeTextRsult.push("'"+x_node_seId+"'");
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
    if(regPos.test(val) && regNeg.test(val)){
        return true;
    }else{
        return false;
    }
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
				deletejson.push(row[index].get(data_id));
			});
	var delparamsId = "" + deletejson.join(",");
	// AJAX提交方式
	Ext.Ajax.request({
		url : action_name,
		method : "POST",
		// 提交参数
		params : {
			paramsId : delparamsId
		},
		success : function(response) {
			var result = Ext.JSON.decode(response.responseText);
			Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'"
					+ context
					+ "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
			if (result.flag == true) {
				//判断是否是后台井、井场、组织模块 清除地图覆盖物重新请求数据
//				if(grid_id=="wellsitePanel_Id"){
//					if(mapHelperWellsite!=null){
//						mapHelperWellsite.clearOverlays();
//						SaveBackMapData(mapHelperWellsite,"wellsite",m_BackDefaultZoomLevel);
//					}
//				}
//				if(grid_id=="wellPanel_Id"){
//					if(mapHelperWell!=null){
//    					mapHelperWell.clearOverlays();
//    					SaveBackMapData(mapHelperWell,"well",m_BackDefaultZoomLevel);
//    				}else{
//    					updateWellMapDataAndShowMap();
//    				}
//				}
//				if(grid_id=="OrgInfoTreeGridView_Id"){
//					if(mapHelperOrg!=null){
//						mapHelperOrg.clearOverlays();
//						SaveBackMapData(mapHelperOrg,"org",m_BackDefaultZoomLevel);
//					}
//				}
				Ext.Msg.alert('提示', "【<font color=blue>成功删除</font>】"+ row.length + "条数据信息。");
			}
			if (result.flag == false) {
				Ext.Msg.alert('提示', "<font color=red>SORRY！删除失败。</font>");
			}
			// 刷新Grid
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

/*
 * 前台通过点击小图标出现图形的函数
 * li 2015-06-18
 */
iconGtsj = function(value, e, o) {
	var getID_ = o.data.id;
	var type='gtsj';
	var resultstring = "<img src='"
			+ context
			+ "/images/icon/SurfaceCard.png' style='cursor:pointer' onclick=callBackGraphical(\""
			+ type + "\",\""+ getID_ + "\") />";
	return resultstring;
}

iconDiagnoseAnalysisCurve = function(value, e, o) {
	var itemCode = o.data.itemCode;
	var item=o.data.item;
	var index=o.internalId%2;
	var resultstring='';
	if(itemCode.toUpperCase() == 'acqTime_d'.toUpperCase()||itemCode.toUpperCase() == 'deviceVer'.toUpperCase()){
//		resultstring=o.data.value;
		resultstring='';
	}else{
		resultstring = "<img src='"
			+ context
			+ "/images/icon/curvetest"+index+".png' style='cursor:pointer;display: inline-block; vertical-align: middle;' onclick=callBackHistoryData(\""+item+"\",\""+itemCode+"\") />";
	}
	
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

iconElectricAnalysisRealtimeCurve = function(value, e, o) {
	var itemCode = o.data.itemCode;
	var item=o.data.item;
	var index=o.internalId%2;
	var resultstring = "<img src='"
			+ context
			+ "/images/icon/curvetest"+index+".png' style='cursor:pointer' onclick=callBackElectricAnalysisRealtimeHistoryData(\""+item+"\",\""+itemCode+"\") />";
	return resultstring;
}

iconElectricAnalysisDailyCurve = function(value, e, o) {
	var itemCode = o.data.itemCode;
	var item=o.data.item;
	var index=o.internalId%2;
	var resultstring = "<img src='"
			+ context
			+ "/images/icon/curvetest"+index+".png' style='cursor:pointer' onclick=callBackElectricAnalysisDailyHistoryData(\""+item+"\",\""+itemCode+"\") />";
	return resultstring;
}

iconBgt = function(value, e, o) {
	var getID_ = o.data.id;
	var type='bgt';
	var resultstring = "<img src='"
			+ context
			+ "/images/icon/PumpCard.png' style='cursor:pointer' onclick=callBackGraphical(\""
			+ type + "\",\""+ getID_ + "\") />";
	return resultstring;
}

iconDlqx = function(value, e, o) {
	var getID_ = o.data.id;
	var type='dlqx';
	var resultstring = "<img src='"
			+ context
			+ "/images/icon/ElectricCurve.png' style='cursor:pointer' onclick=callBackGraphical(\""
			+ type + "\",\""+ getID_ + "\") />";
	return resultstring;
}

iconTxzt = function(value, e, o) {
	var resultstring='';
	var txzt = o.data.txzt;
	if(txzt==1){
		resultstring = "<img src='"+ context+ "/images/icon/on-line.png' style='cursor:pointer' />";
	}else{
		resultstring = "<img src='"+ context+ "/images/icon/off-line.png' style='cursor:pointer' />";
	}
	return resultstring;
}

iconYxzt = function(value, e, o) {
	var resultstring='';
	var txzt = o.data.txzt;
	var yxzt = o.data.yxzt;
	if(txzt==1){
		if(yxzt==1){
			resultstring = "<img src='"+ context+ "/images/icon/running.png' style='cursor:pointer' />";
		}else{
			resultstring = "<img src='"+ context+ "/images/icon/stop.png' style='cursor:pointer' />";
		}
	}
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

var callBackHistoryData = function(item,itemCode) {
    Ext.getCmp('DiagnosisAnalysisCurveItem_Id').setValue(item);
    Ext.getCmp('DiagnosisAnalysisCurveItemCode_Id').setValue(itemCode);
    var HistoryCurveOnclickWindow=Ext.create("AP.view.diagnosis.HistoryCurveOnclickWindow", {
				    html:'<div id="HistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>' // 图形类型+数据id作为div的id
			   });
    HistoryCurveOnclickWindow.show();
}

var callBackTotalHistoryData = function(item,itemCode) {
    Ext.getCmp('DiagnosisTotalCurveItem_Id').setValue(item);
    Ext.getCmp('DiagnosisTotalCurveItemCode_Id').setValue(itemCode);
    var TotalHistoryCurveOnclickWindow=Ext.create("AP.view.diagnosisTotal.TotalHistoryCurveOnclickWindow", {
				    html:'<div id="TotalHistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>' // 图形类型+数据id作为div的id
			   });
    TotalHistoryCurveOnclickWindow.show();
}

function callBackElectricAnalysisRealtimeHistoryData(item,itemCode) {
    Ext.getCmp('ElectricAnalysisRealtimeDetailsCurveItem_Id').setValue(item);
    Ext.getCmp('ElectricAnalysisRealtimeDetailsCurveItemCode_Id').setValue(itemCode);
    var OnclickWindow=Ext.create("AP.view.electricAnalysis.ElectricAnalysisRealtileHistoryCurveOnclickWindow", {
				    html:'<div id="ElectricAnalysisRealtimeHistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>' // 图形类型+数据id作为div的id
			   });
    OnclickWindow.show();
}

function callBackElectricAnalysisDailyHistoryData(item,itemCode) {
    Ext.getCmp('ElectricAnalysisDailyDetailsCurveItem_Id').setValue(item);
    Ext.getCmp('ElectricAnalysisDailyDetailsCurveItemCode_Id').setValue(itemCode);
    var OnclickWindow=Ext.create("AP.view.electricDailyAnalysis.ElectricAnalysisDailyHistoryCurveOnclickWindow", {
				    html:'<div id="ElectricAnalysisDailyHistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>' // 图形类型+数据id作为div的id
			   });
    OnclickWindow.show();
}

// 显示产量计算数据表
function showProductionComputeData(panelId) {
	var productionComputePanel = Ext.getCmp("ProductionCompute");// 获取到显示产量数据表的panel
	if (productionComputePanel != undefined) {
		productionComputePanel.removeAll();// 清除掉以前的grid，
		uss_ = Ext.create(panelId);
		productionComputePanel.add(uss_);// 重新添加一个新的grid
	}
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

function showDiagnosisChart(panelid) {
	var chartPanel = Ext.getCmp("Statisticschart_Id");
	chartPanel.removeAll();
	var uss_ = Ext.create(panelid);
	chartPanel.add(uss_);
}
function showComputeChart(panelid) {
	var chartPanel = Ext.getCmp("ProductionStatistics_Chart_Id");
	chartPanel.removeAll();
	var uss_ = Ext.create(panelid);
	chartPanel.add(uss_);
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
/*******************************************************************************
 * 
 * 获取产量曲线数据
 */
/**
 * Curver chart
 */
CurverVFnChartFn = function(store, divId) {
	var items = store.data.items;
	var tickInterval = 1;
	tickInterval = Math.floor(items.length / 10) + 1;
	var catagories = "[";
	for (var i = 0; i < items.length; i++) {
		catagories += "\"" + getFormatDate(items[i].data.jssj) + "\"";
		if (i < items.length - 1) {
			catagories += ",";
		}
	}
	catagories += "]";
	var legendName = [cosog.string.rcyl,cosog.string.rcyl1, cosog.string.hsl];
	var series = "[";
	for (var i = 0; i < legendName.length; i++) {
		series += "{\"name\":\"" + legendName[i] + "\",";
		if (i == 2) {
			series += "\"yAxis\":1,";
		}
		series += "\"data\":[";
		for (var j = 0; j < items.length; j++) {
			if (i == 0&&isNotVal(items[j].data.jsdjrcyl)) {
				series += items[j].data.jsdjrcyl;
			} else if (i == 1&&isNotVal(items[j].data.jsdjrcyl1)) {
				series += items[j].data.jsdjrcyl1;
			} else if (i == 2&&isNotVal(items[j].data.hsl)) {
				series += items[j].data.hsl;
			}else{
				series+="\"\"";
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
	initCurveChart(cat, ser, tickInterval, divId);
	return false;
}

CurverLoadVFnChartFn = function(store, divId) {
	var items = store.data.items;
	var tickInterval = 1;
	tickInterval = Math.floor(items.length / 10) + 1;
	var catagories = "[";
	for (var i = 0; i < items.length; i++) {
		//alert(items[i].data.gtcjsj);
		catagories += "\"" + items[i].data.gtcjsj + "\"";
		if (i < items.length - 1) {
			catagories += ",";
		}
	}
	catagories += "]";
	var legendName = [cosog.string.fmax, cosog.string.fmin];
	var series = "[";
	for (var i = 0; i < legendName.length; i++) {
		series += "{\"name\":\"" + legendName[i] + "\",";
		if (i == 2) {
			series += "\"yAxis\":1,";
		}
		series += "\"data\":[";
		for (var j = 0; j < items.length; j++) {
			if (i == 0) {
				series += items[j].data.fmax;
			} else if (i == 1) {
				series += items[j].data.fmin;
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
	initLoadCurveChart(cat, ser, tickInterval, divId);
	return false;
}

function initLoadCurveChart(years, values, tickInterval, divId) {
  Highcharts.setOptions({  
		         lang: {  
		              resetZoom : '还原'  
		         }  
		           }); 
		           
	mychart = new Highcharts.Chart({
				chart : {
					renderTo : divId,
					type : 'spline',
					shadow : true,
					borderWidth : 0,
					zoomType : 'xy'
				},
				credits : {
					enabled : false
				},
				title : {
					text :cosog.string.zhqx,
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
						text : cosog.string.time
					}
				},
				yAxis : {
					lineWidth : 1,
					title : {
						text : cosog.string.load,
						style : {
							color : '#000000',
							fontWeight : 'bold'
						}
					},
					labels : {
						formatter : function() {
							return Highcharts.numberFormat(this.value, 2);
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
						return '<b>' + this.series.name + '</b><br/>' + this.x
								+ ': ' + this.y;
					},
					valueSuffix : ''
				},
					plotOptions : {
					 spline: {  
				            lineWidth: 1,  
				            fillOpacity: 0.3,  
				             marker: {  
				             enabled: false,  
				              radius: 2,  //曲线点半径，默认是4
                             //symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
				                states: {  
				                   hover: {  
				                        enabled: true,  
				                        radius:5
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
// 注入井曲线构建函数

function initWaterCurveChart(years, values, tickInterval, divId) {
	var mychart;
	// mychart.destroy();
	mychart = new Highcharts.Chart({
				chart : {
					renderTo : divId,
					type : 'spline',
					shadow : false,
					reflow : true,
					borderWidth : 0
				},
				credits : {
					enabled : false
				},
				title : {
					text : cosog.string.zrqxf,
					x : -20
					// center
				},
				colors : ['#0000FF',// 蓝
						'#008C00',// 绿
						'#FF00FF',// 紫
						'#000000',// 黑
						'#F4BD82'// 黄

				],
				xAxis : {
					categories : years,
					tickInterval : tickInterval,
					title : {
						text :cosog.string.date
					},
					labels : {
	// x:45//调节x偏移
					// y:-35,//调节y偏移
					// rotation:-25//调节倾斜角度偏移
					}
				},
				yAxis : {
					lineWidth : 1,
					title : {
						text :cosog.string.rzsl,
						style : {
							color : '#000000',
							fontWeight : 'bold'
						}
					},
					labels : {
						formatter : function() {
							return Highcharts.numberFormat(this.value, 2);
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
					formatter : function() {
						return '<b>' + this.series.name + '</b><br/>' + this.x
								+ ': ' + this.y;
					},
					valueSuffix : ''
				},
//             exporting: {
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

//总产量曲线画图方法
CurverAllProduceVFnChartFn = function(store, divId) {
	var items = store.data.items;
	var tickInterval = 1;
	tickInterval = Math.floor(items.length / 10) + 1;
	var catagories = "[";
	for (var i = 0; i < items.length; i++) {
		catagories += "\"" + items[i].data.jssj + "\"";
		if (i < items.length - 1) {
			catagories += ",";
		}
	}
	catagories += "]";
	var legendName = [cosog.string.zrcyl,cosog.string.zrcyl1];
	var series = "[";
	for (var i = 0; i < legendName.length; i++) {
		series += "{\"name\":\"" + legendName[i] + "\",";
		if (i == 2) {
			series += "\"yAxis\":1,";
		}
		series += "\"data\":[";
		for (var j = 0; j < items.length; j++) {
			if (i == 0) {
				series += items[j].data.zrcyl;
			} else if (i == 1) {
				series += items[j].data.zrcyl1;
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
	initAllProduceCurveChart(cat, ser, tickInterval, divId);
	return false;
}

function initAllProduceCurveChart(years, values, tickInterval, divId) {
	mychart = new Highcharts.Chart({
				chart : {
					renderTo : divId,
					type : 'spline',
					shadow : true,
					borderWidth : 0,
					zoomType : 'xy'
				},
				credits : {
					enabled : false
				},
				title : {
					text : cosog.string.zclqx,
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
				yAxis : {
					min: 0,
					lineWidth : 1,
					title : {
						text :  cosog.string.zcl,
						style : {
							color : '#000000',
							fontWeight : 'bold'
						}
					},
					labels : {
						formatter : function() {
							return Highcharts.numberFormat(this.value, 2);
						}
					},
					plotLines : [{
								value : 0,
								width : 1,
								zIndex:2,
								color : '#808080'
							}]
				},
//				 exporting: {
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



// 报警信息跟踪饼图统计信息
function initFollowAlarmPieChart(data, tickInterval, w, h) {
	$('#AlarmConPie').highcharts({
				chart : {
					plotBackgroundColor : null,
					plotBorderWidth : null,
					plotShadow : false,
					width : w,
					height : h
				},
				credits : {
					enabled : false
				},
				colors : ['#058DC7','#DDDF00','#6AF9C4','#50B432', '#ED561B', '#24CBE5', '#64E572',
							'#FF9655', '#FFF263'],
				title : {
					text : '处理状态统计图'
				},
				tooltip : {
					pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
				},
//			     exporting: {
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
					pie : {
						allowPointSelect : true,
						cursor : 'pointer',
						dataLabels : {
							enabled : true,
							color : '#000000',
							connectorColor : '#000000',
							format : '<b>{point.name}</b>: {point.percentage:.1f} %'
						},
						showInLegend : true
					}
				},
				series : [{
							type : 'pie',
							name : '处理结果占',
							data : data
						}]
			});

}
function initFollowAlarmPieChart1(data, tickInterval, w, h) {
	$('#AlarmConPie1').highcharts({
				chart : {
					plotBackgroundColor : null,
					plotBorderWidth : null,
					plotShadow : false,
					width : w,
					height : h
				},
				credits : {
					enabled : false
				},
				title : {
					text : '报警等级统计图'
				},
				tooltip : {
					pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
				},
				plotOptions : {
					pie : {
						allowPointSelect : true,
						cursor : 'pointer',
						dataLabels : {
							enabled : true,
							color : '#000000',
							connectorColor : '#000000',
							format : '<b>{point.name}</b>: {point.percentage:.1f} %'
						}
					}
				},
				plotOptions : {
					pie : {
						allowPointSelect : true,
						cursor : 'pointer',
						dataLabels : {
							enabled : true,
							color : '#000000',
							connectorColor : '#000000',
							format : '<b>{point.name}</b>: {point.percentage:.1f} %'
						},
						showInLegend : true
					}
				},
				series : [{
							type : 'pie',
							name : '处理结果占',
							data : data
						}]
			});

}
function initFollowAlarmPieChart2(data, tickInterval, w, h) {
	$('#AlarmConPie2').highcharts({
				chart : {
					plotBackgroundColor : null,
					plotBorderWidth : null,
					plotShadow : false,
					width : w,
					height : h
				},
				credits : {
					enabled : false
				},
				title : {
					text : '报警类型统计图'
				},
				colors : ['#058DC7', '#24CBE5','#50B432', '#ED561B',  '#64E572',
							'#FF9655', '#FFF263', '#6AF9C4', '#DDDF00'],
				tooltip : {
					pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
				},
				plotOptions : {
					pie : {
						allowPointSelect : true,
						cursor : 'pointer',
						dataLabels : {
							enabled : true,
							color : '#000000',
							connectorColor : '#000000',
							format : '<b>{point.name}</b>: {point.percentage:.1f} %'
						}
					}
				},
				plotOptions : {
					pie : {
						allowPointSelect : true,
						cursor : 'pointer',
						dataLabels : {
							enabled : true,
							color : '#000000',
							connectorColor : '#000000',
							format : '<b>{point.name}</b>: {point.percentage:.1f} %'
						},
						showInLegend : true
					}
				},
				series : [{
							type : 'pie',
							name : '处理结果占',
							data : data
						}]
			});

}
function initAlarmColumnChart(values, w, h) {
	$('#AlarmConColumn').highcharts({
		chart : {
			type : 'column',
			width : w,
			height : h
		},
		credits : {
			enabled : false
		},
		title : {
			text : '处理状态统计图',
			font : 'normal 25px Verdana, sans-serif'
		},
		xAxis : {
			categories : ['处理状态统计']
		},
		yAxis : {
			min : 0,
			title : {
				text : '次数'
			}
		},

		tooltip : {
			headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
			pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
					+ '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
			footerFormat : '</table>',
			shared : true,
			useHTML : true
		},
		plotOptions : {
			column : {
				pointPadding : 0.2,
				borderWidth : 0
			}
		},
		series : values
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

// 工况诊断柱状图 共用接口1

function constructColumnChart(title_, categories_, values_) {
	$('#DiagnosisConColumn').highcharts({
		chart : {
			type : 'column'
		},
		credits : {
			enabled : false
		},
		title : {
			text : title_,
			font : 'normal 25px Verdana, sans-serif'
		},
		xAxis : {
			categories : categories_,
			labels : {
				style : {
					color : '#000000',
					margin_top : '4px',
					fontWeight : 'bold'
				}
			}
		},
		yAxis : {
			min : 0,
			title : {
				text : cosog.string.wellNums
			}
		},
		colors : ['#058DC7', '#50B432', '#ED561B', '#24CBE5', '#64E572',
				'#FF9655', '#FFF263', '#6AF9C4', '#DDDF00'],
		tooltip : {
			headerFormat : '<span style="font-size:8px">{point.key}</span><table>',
			pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
					+ '<td style="padding:0"><b>{point.y}</b></td></tr>',
			footerFormat : '</table>',
			shared : true,
			useHTML : true
		},
		plotOptions : {

			column : {
				pointPadding : 0.2,
				allowPointSelect : true,
				borderWidth : 0,
				dataLabels : {
					enabled : true,
					padding : 0.5
				}
			}
		},
		series : values_
	});

}
/**
 * **工况诊断饼图 共用接口 传入参数说明 tilte_ :传入当前饼图的标题 name_:显示的名称 data ：饼图显示所需的数据
 */
function constructPieChart(title_, name_, data) {
	$('#DiagnosisConPie').highcharts({
		chart : {
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false
		},
		credits : {
			enabled : false
		},
		title : {
			text : title_
		},
		colors : ['#058DC7', '#50B432', '#ED561B', '#24CBE5', '#64E572',
				'#FF9655', '#FFF263', '#6AF9C4', '#DDDF00'],
		tooltip : {
			pointFormat : '{series.name} : <b>{point.percentage:.1f}%</b>'
		},
		legend : {
			align : 'right',
			verticalAlign : 'middle',
			layout : 'vertical'
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : true,
					color : '#000000',
					connectorColor : '#000000',
					format : '<b>{point.name}</b>: {point.percentage:.1f} %'
				},
				showInLegend : true
			}
		},
		series : [{
					type : 'pie',
					name : name_,
					data : data
				}]
	});

}
// 初始化柱状图或者饼图
function initchartType(store) {
	var get_rawData = store.proxy.reader.rawData;
	var column_ = get_rawData.chartColumn[0];
	var title_ = column_.title;
	var categories_ = column_.categories;
	var columnData_ = column_.column;
	var data_ = Ext.JSON.decode(columnData_);
	var categories_ss = Ext.JSON.decode(categories_);
	var pie_ = get_rawData.chartPie[0];
	var pietitle_ = pie_.title;
	var piename = pie_.name;
	var pieData_ = pie_.pie;
	var piedatajson_ = Ext.JSON.decode(pieData_);
	var DiagnosisStatisticsChart_TypeId = Ext.getCmp("DiagnosisStatisticsChart_TypeId");
	if (isNotVal(DiagnosisStatisticsChart_TypeId)) {
		DiagnosisStatisticsChart_TypeId = DiagnosisStatisticsChart_TypeId.getValue();
		if (DiagnosisStatisticsChart_TypeId == 'DiagnosisPie_Id') {
			constructPieChart(pietitle_, piename, piedatajson_);
		} else {
			var DiagnosisConColumn = Ext.get("DiagnosisConColumn");
			if(DiagnosisConColumn!=null){
				constructColumnChart(title_, categories_ss, data_);
			}else{
				constructPieChart(pietitle_, piename, piedatajson_);
			}
		}
	}
}

// 产量计算柱状图 共用接口

function constructComputeColumnChart(title_, categories_, values_, w, h) {
	$('#ComputeConColumn').highcharts({
		chart : {
			type : 'column',
			width : w,
			height : h
		},
		credits : {
			enabled : false
		},
		title : {
			text : title_,
			font : 'normal 25px Verdana, sans-serif'
		},
		xAxis : {
			categories : categories_,
			labels : {
				style : {
					color : '#000000',
					margin_top : '4px',
					fontWeight : 'bold'
				}
			}
		},
		yAxis : {
			min : 0,
			title : {
				text : '井数'
			}
		},
		colors : ['#058DC7', '#50B432', '#ED561B', '#24CBE5', '#64E572',
				'#FF9655', '#FFF263', '#6AF9C4', '#DDDF00'],
		tooltip : {
			headerFormat : '<span style="font-size:8px">{point.key}</span><table>',
			pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
					+ '<td style="padding:0"><b>{point.y}</b></td></tr>',
			footerFormat : '</table>',
			shared : true,
			useHTML : true
		},
		plotOptions : {
			column : {
				pointPadding : 0.2,
				allowPointSelect : true,
				borderWidth : 0,
				dataLabels : {
					enabled : true,
					padding : 1
				}
			}
		},
		series : values_
	});

}
/**
 * **产量计算饼图 共用接口 传入参数说明 tilte_ :传入当前饼图的标题 name_:显示的名称 data ：饼图显示所需的数据 w:图形的宽度
 * h:图形显示的高度
 */
function constructComputePieChart(title_, name_, data, w, h) {
	$('#ComputeConPie').highcharts({
		chart : {
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false,
			width : w,
			height : h
		},
		credits : {
			enabled : false
		},
		title : {
			text : title_
		},
		colors : ['#058DC7', '#50B432', '#ED561B', '#24CBE5', '#64E572',
				'#FF9655', '#FFF263', '#6AF9C4', '#DDDF00'],
		tooltip : {
			pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
		},
		legend : {
			align : 'right',
			verticalAlign : 'middle',
			layout : 'vertical'
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : true,
					color : '#000000',
					connectorColor : '#000000',
					format : '<b >{point.name}</b>: {point.percentage:.1f} %'
				},
				showInLegend : true
			}
		},
		series : [{
					type : 'pie',
					name : name_,
					data : data
				}]
	});

}
// 初始化产量计算柱状图或者饼图
function initComputeChartType(obj) {
	var get_rawData = obj;
	var column_ = get_rawData.chartColumn[0];
	var title_ = column_.title;
	var categories_ = column_.categories;
	var columnData_ = column_.column;
	var data_ = Ext.JSON.decode(columnData_);
	var categories_ss = Ext.JSON.decode(categories_);
	var pie_ = get_rawData.chartPie[0];
	var pietitle_ = pie_.title;
	var piename = pie_.name;
	var pieData_ = pie_.pie;
	var piedatajson_ = Ext.JSON.decode(pieData_);
	var ProductionStatisticsChart_TypeId = Ext.getCmp("ProductionStatisticsChart_TypeId");
	if (isNotVal(ProductionStatisticsChart_TypeId)) {
		ProductionStatisticsChart_TypeId = ProductionStatisticsChart_TypeId.getValue();
		var ComputeConPie=Ext.get("ComputeConPie");
		var ComputeConColumn=Ext.get("ComputeConColumn");
		if (ProductionStatisticsChart_TypeId == 'ComputePie_Id') {
			var html_dom = Ext.get("ComputeConPie").dom;
			var _wx = (html_dom.clientWidth);
			var _hx = (html_dom.clientHeight);
			constructComputePieChart(pietitle_, piename, piedatajson_, _wx, _hx);
		} else if("ComputeColumn_Id"==ProductionStatisticsChart_TypeId) {
			if(ComputeConColumn!=null){
				var html_dom = Ext.get("ComputeConColumn").dom;
				var _wx = (html_dom.clientWidth);
				var _hx = (html_dom.clientHeight);
				constructComputeColumnChart(title_, categories_ss, data_, _wx, _hx);
			}else{
				var html_dom = Ext.get("ComputeConPie").dom;
				var _wx = (html_dom.clientWidth);
				var _hx = (html_dom.clientHeight);
				constructComputePieChart(pietitle_, piename, piedatajson_, _wx, _hx);
			}
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

function addWavePanel(data) {
	var rw_g = Ext.getCmp("AlarmWavepanel_Id");
	//alert(data);
	//alert("aa");
	//data="[['最大载荷波动',-29.11],['最小载荷波动',-37.51],['功图充满系数波动',32.23],['日产液量波动',33.94]]";
	var datas = Ext.JSON.decode(data);
	var AlarmWavepanel_Id = Ext.getCmp("AlarmWavepanel_Id");
	//if (!isNotVal(AlarmWavepanel_Id)) {
		var store = Ext.create('Ext.data.ArrayStore', {
					fields : ['bdmc','bdfw'],
					data : datas
				});
		var AlarmWavepanel_Id = Ext.create('Ext.grid.Panel', {
			id : "AlarmWavepanel_Id",
			border : false,
			// bbar : bbar,
			stateful : true,
			autoScroll : true,
			columnLines : true,
			layout : "fit",
			stripeRows : true,
			enableColumnHide : false,
			forceFit : true,
			viewConfig : {
				emptyText : "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
				forceFit : true
			},
			store : store,
			columns : [{
						header : '波动名称',
						align : 'center',
						dataIndex : 'bdmc',
						sortable : false
					}, {
						header : '波动范围(%)',
						align : 'center',
						dataIndex : 'bdfw',
						sortable : false
					}]
		});
		var AlarmWaveInfoPanelView_Id = Ext.getCmp("AlarmWaveInfoPanelView_Id");
		  AlarmWaveInfoPanelView_Id.removeAll();
		AlarmWaveInfoPanelView_Id.add(AlarmWavepanel_Id);
	//}
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
 		BackgroundColor='#'+AlarmShowStyle.Normal.BackgroundColor;
 		Colorr='#'+AlarmShowStyle.Normal.Color;
 		Opacity=AlarmShowStyle.Normal.Opacity
	}else if (alarmLevel == 100) {
		BackgroundColor='#'+AlarmShowStyle.FirstLevel.BackgroundColor;
 		Colorr='#'+AlarmShowStyle.FirstLevel.Color;
 		Opacity=AlarmShowStyle.FirstLevel.Opacity
	}else if (alarmLevel == 200) {
		BackgroundColor='#'+AlarmShowStyle.SecondLevel.BackgroundColor;
 		Colorr='#'+AlarmShowStyle.SecondLevel.Color;
 		Opacity=AlarmShowStyle.SecondLevel.Opacity
	}else if (alarmLevel == 300) {
		BackgroundColor='#'+AlarmShowStyle.ThirdLevel.BackgroundColor;
 		Colorr='#'+AlarmShowStyle.ThirdLevel.Color;
 		Opacity=AlarmShowStyle.ThirdLevel.Opacity
	}
 	var rgba=color16ToRgba(BackgroundColor,Opacity);
 	o.style='background-color:'+rgba+';color:'+Colorr+';';
	return '<span data-qtip="'+tipval+'" data-dismissDelay=10000>'+val+'</span>';
}

 adviceElecResultColor = function(val,o,p,e) {
	if(val==undefined||val=="undefined"){
		val="";
	}
 	var alarmLevel=p.data.resultAlarmLevel_E;
 	if(alarmLevel==undefined){
 		alarmLevel=p.data.resultAlarmLevel_e;
 	}
 	var alarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
 	var tipval=val;
 	if(p.data.resultString_E!=undefined&&p.data.resultString_E!=''){
 		tipval=p.data.resultString_E;
 	}
 	var BackgroundColor='#FFFFFF';
 	var Colorr='#000000';
 	var Opacity=1;
 	if (alarmLevel == 0) {
 		BackgroundColor='#'+alarmShowStyle.Normal.BackgroundColor;
 		Colorr='#'+alarmShowStyle.Normal.Color;
 		Opacity=alarmShowStyle.Normal.Opacity
	}else if (alarmLevel == 100) {
		BackgroundColor='#'+alarmShowStyle.FirstLevel.BackgroundColor;
 		Colorr='#'+alarmShowStyle.FirstLevel.Color;
 		Opacity=alarmShowStyle.FirstLevel.Opacity
	}else if (alarmLevel == 200) {
		BackgroundColor='#'+alarmShowStyle.SecondLevel.BackgroundColor;
 		Colorr='#'+alarmShowStyle.SecondLevel.Color;
 		Opacity=alarmShowStyle.SecondLevel.Opacity
	}else if (alarmLevel == 300) {
		BackgroundColor='#'+alarmShowStyle.ThirdLevel.BackgroundColor;
 		Colorr='#'+alarmShowStyle.ThirdLevel.Color;
 		Opacity=alarmShowStyle.ThirdLevel.Opacity
	}
 	var rgba=color16ToRgba(BackgroundColor,Opacity);
 	o.style='background-color:'+rgba+';color:'+Colorr+';';
	return '<span data-qtip="'+tipval+'">'+val+'</span>';
}
 
 
 
 adviceTimeFormat = function(val,o,p,e) {
	 	var reslut="";
	 	val=val.split(".")[0];
	 	var reslut=val;
//	    if(val==undefined||val=="undefined"||val==""||val==null||val=="null"){
//	    	reslut="";
//		}else{
//			var t = new Date(val);
//		 	var format='yyyy-MM-dd HH:mm:ss';
//		    var tf = function (i) {
//		        return (i < 10 ? '0' : '') + i
//		    };
//		    
//		    reslut= format.replace(/yyyy|MM|dd|HH|mm|ss/g, function (a) {
//		        switch (a) {
//		        case 'yyyy':
//		        return tf(t.getFullYear());
//		        break;
//		        case 'MM':
//		        return tf(t.getMonth() + 1);
//		        break;
//		        case 'mm':
//		        return tf(t.getMinutes());
//		        break;
//		        case 'dd':
//		        return tf(t.getDate());
//		        break;
//		        case 'HH':
//		        return tf(t.getHours());
//		        break;
//		        case 'ss':
//		        return tf(t.getSeconds());
//		        break;
//		        }
//		    })
//		}
	    return '<span data-qtip="'+reslut+'">'+reslut+'</span>';
	}
 
 adviceBalanceStatusColor = function(val,o,p,e,type) {
	 if(val==undefined||val=="undefined"){
		 val="";
	}
	var AlarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
 	var bjbz_=p.data.iDegreeBalanceAlarmLevel;
 	var tipval=val;
 	var BackgroundColor='#FFFFFF';
 	var Colorr='#000000';
 	var Opacity=1;
 	if (bjbz_ == 0) {
 		BackgroundColor='#'+AlarmShowStyle.Normal.BackgroundColor;
 		Colorr='#'+AlarmShowStyle.Normal.Color;
 		Opacity=AlarmShowStyle.Normal.Opacity
	}else if (bjbz_ == 100) {
		BackgroundColor='#'+AlarmShowStyle.FirstLevel.BackgroundColor;
 		Colorr='#'+AlarmShowStyle.FirstLevel.Color;
 		Opacity=AlarmShowStyle.FirstLevel.Opacity
	}else if (bjbz_ == 200) {
		BackgroundColor='#'+AlarmShowStyle.SecondLevel.BackgroundColor;
 		Colorr='#'+AlarmShowStyle.SecondLevel.Color;
 		Opacity=AlarmShowStyle.SecondLevel.Opacity
	}else if (bjbz_ == 300) {
		BackgroundColor='#'+AlarmShowStyle.ThirdLevel.BackgroundColor;
 		Colorr='#'+AlarmShowStyle.ThirdLevel.Color;
 		Opacity=AlarmShowStyle.ThirdLevel.Opacity
	}
 	var rgba=color16ToRgba(BackgroundColor,Opacity);
 	o.style='background-color:'+rgba+';color:'+Colorr+';';
	return '<span data-qtip="'+tipval+'" data-dismissDelay=10000>'+val+'</span>';
}
 advicePowerBalanceStatusColor = function(val,o,p,e,type) {
	 if(val==undefined||val=="undefined"){
		 val="";
	}
	var AlarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
 	var bjbz_=p.data.wattDegreeBalanceAlarmLevel;
 	var tipval=val;
 	var BackgroundColor='#FFFFFF';
 	var Colorr='#000000';
 	var Opacity=1;
 	if (bjbz_ == 0) {
 		BackgroundColor='#'+AlarmShowStyle.Normal.BackgroundColor;
 		Colorr='#'+AlarmShowStyle.Normal.Color;
 		Opacity=AlarmShowStyle.Normal.Opacity
	}else if (bjbz_ == 100) {
		BackgroundColor='#'+AlarmShowStyle.FirstLevel.BackgroundColor;
 		Colorr='#'+AlarmShowStyle.FirstLevel.Color;
 		Opacity=AlarmShowStyle.FirstLevel.Opacity
	}else if (bjbz_ == 200) {
		BackgroundColor='#'+AlarmShowStyle.SecondLevel.BackgroundColor;
 		Colorr='#'+AlarmShowStyle.SecondLevel.Color;
 		Opacity=AlarmShowStyle.SecondLevel.Opacity
	}else if (bjbz_ == 300) {
		BackgroundColor='#'+AlarmShowStyle.ThirdLevel.BackgroundColor;
 		Colorr='#'+AlarmShowStyle.ThirdLevel.Color;
 		Opacity=AlarmShowStyle.ThirdLevel.Opacity
	}
 	var rgba=color16ToRgba(BackgroundColor,Opacity);
 	o.style='background-color:'+rgba+';color:'+Colorr+';';
	return '<span data-qtip="'+tipval+'" data-dismissDelay=10000>'+val+'</span>';
}
 
 adviceCommStatusColor = function(val,o,p,e) {
	 	var commStatus=p.data.commStatus;
	 	var tipval=val;
	 	var alarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
	 	var alarmLevel=p.data.commAlarmLevel;
	 	var backgroundColor='#FFFFFF';
	 	var color='#000000';
	 	var opacity=1;
	 	if (alarmLevel == 0) {
	 		backgroundColor='#'+alarmShowStyle.Normal.BackgroundColor;
	 		color='#'+alarmShowStyle.Normal.Color;
	 		opacity=alarmShowStyle.Normal.Opacity
		}else if (alarmLevel == 100) {
			backgroundColor='#'+alarmShowStyle.FirstLevel.BackgroundColor;
	 		color='#'+alarmShowStyle.FirstLevel.Color;
	 		opacity=alarmShowStyle.FirstLevel.Opacity
		}else if (alarmLevel == 200) {
			backgroundColor='#'+alarmShowStyle.SecondLevel.BackgroundColor;
	 		color='#'+alarmShowStyle.SecondLevel.Color;
	 		opacity=alarmShowStyle.SecondLevel.Opacity
		}else if (alarmLevel == 300) {
			backgroundColor='#'+alarmShowStyle.ThirdLevel.BackgroundColor;
	 		color='#'+alarmShowStyle.ThirdLevel.Color;
	 		opacity=alarmShowStyle.ThirdLevel.Opacity
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
	 	var alarmLevel=p.data.runAlarmLevel;
	 	var alarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
		if (commStatus == 0) {
//			o.css='pendingColor';
			return '';
		} else {
			var backgroundColor='#FFFFFF';
		 	var color='#000000';
		 	var opacity=1;
		 	if (alarmLevel == 0) {
		 		backgroundColor='#'+alarmShowStyle.Normal.BackgroundColor;
		 		color='#'+alarmShowStyle.Normal.Color;
		 		opacity=alarmShowStyle.Normal.Opacity
			}else if (alarmLevel == 100) {
				backgroundColor='#'+alarmShowStyle.FirstLevel.BackgroundColor;
				color='#'+alarmShowStyle.FirstLevel.Color;
				opacity=alarmShowStyle.FirstLevel.Opacity
			}else if (alarmLevel == 200) {
				backgroundColor='#'+alarmShowStyle.SecondLevel.BackgroundColor;
				color='#'+alarmShowStyle.SecondLevel.Color;
				opacity=alarmShowStyle.SecondLevel.Opacity
			}else if (alarmLevel == 300) {
				backgroundColor='#'+alarmShowStyle.ThirdLevel.BackgroundColor;
				color='#'+alarmShowStyle.ThirdLevel.Color;
				opacity=alarmShowStyle.ThirdLevel.Opacity
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
  
  
     /****
      * 总外输饼状图统计表
      * **/
  var CbmAllExportPieVFnChartFn = function(store, w, h) {
	var items = store.data.items;
	var sum=0.0;
	var datajson = [];
   Ext.Array.each(items,
					function(name, index, countriesItSelf) {
					var curVal=name.data.rcql;
					sum+=parseFloat(curVal);
					});
		Ext.Array.each(items, function(name, index, countriesItSelf) {
			var rcql_=parseFloat(items[index].get('rcql'));
			var percentage_=(rcql_/sum)*100;
		    // percentage_=percentage_.toFixed(1);
				datajson.push(percentage_);
			});		
			//var perData=""+datajson.join(","); 
			//var perArray=perData.split(",");
			var pieData_="[";
			if(isNotVal(datajson)){
			Ext.Array.each(datajson, function(name, index, countriesItSelf) {
			  var myVal=datajson[index];
			  var gqmc_=items[index].data.gqmc;
			
			  if(index==2){
			  	pieData_+=" {name:'"+gqmc_+"',y:"+myVal+", sliced: true,selected: true},"
			  }else{
			  	  pieData_+="[ '"+gqmc_+"',"+myVal+"],";
			  }
			});	
			if(pieData_.length>1){
			pieData_=pieData_.substring(0,pieData_.length-1);
			    }
			}
		pieData_	+="]";
	
     var pieData_ = Ext.JSON.decode(pieData_);
    //   alert(pieData_);
   constructAllExportPieChart(pieData_,w,h);
	
}

function constructAllExportPieChart(data, w, h) {
	$('#AllExportPie').highcharts({
		chart : {
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false,
			width : w,
			height : h
		},
		credits : {
			enabled : false
		},
		title : {
			text : '作业区地产气饼状图'
		},
		colors : ['#058DC7', '#50B432', '#ED561B', '#24CBE5', '#64E572',
				'#FF9655', '#FFF263', '#6AF9C4', '#DDDF00'],
		tooltip : {
			pointFormat : '{series.name} : <b>{point.percentage:.1f}%</b>'
		},  
//		exporting: {
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
		legend : {
			align : 'right',
			verticalAlign : 'middle',
			layout : 'vertical'
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : true,
					color : '#000000',
					connectorColor : '#000000',
					format : '<b>{point.name}</b>: {point.percentage:.1f} %'
				},
				showInLegend : true
			}
		},
		series : [{
					type : 'pie',
					name : '日产气量占',
					data: data
				}]
	});

}

      /***
       * '集气站单井地产饼状图日产气量统计方法
       * **/
   var CbmAreaExportPieVFnChartFn = function(store, w, h) {
	var items = store.data.items;
	var sum=0.0;
	var datajson = [];
   Ext.Array.each(items,
					function(name, index, countriesItSelf) {
					var curVal=name.data.rcql;
					sum+=parseFloat(curVal);
					});
		Ext.Array.each(items, function(name, index, countriesItSelf) {
			var rcql_=parseFloat(items[index].get('rcql'));
			var percentage_=(rcql_/sum)*100;
		    // percentage_=percentage_.toFixed(1);
				datajson.push(percentage_);
			});		
			//var perData=""+datajson.join(","); 
			//var perArray=perData.split(",");
			//alert(datajson);
			var pieData_="[";
			Ext.Array.each(datajson, function(name, index, countriesItSelf) {
			  var myVal=datajson[index];
			  var gqmc_=items[index].data.jqzmc;
			
			  if(index==2){
			  	pieData_+=" {name:'"+gqmc_+"',y:"+myVal+", sliced: true,selected: true},"
			  }else{
			  	  pieData_+="[ '"+gqmc_+"',"+myVal+"],";
			  }
			});	
			if(pieData_.length>1){
			pieData_=pieData_.substring(0,pieData_.length-1);
			  }
		pieData_	+="]";
	
     var pieData_ = Ext.JSON.decode(pieData_);
    //   alert(pieData_);
   constructAreaExportPieChart(pieData_,w,h);
	
}

function constructAreaExportPieChart(data, w, h) {
	$('#AreaExportPie').highcharts({
		chart : {
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false,
			width : w,
			height : h
		},
		credits : {
			enabled : false
		},
		title : {
			text : '集气站单井地产饼状图'
		},
		colors : ['#058DC7', '#50B432', '#ED561B', '#24CBE5', '#64E572',
				'#FF9655', '#FFF263', '#6AF9C4', '#DDDF00'],
		tooltip : {
			pointFormat : '{series.name} : <b>{point.percentage:.1f}%</b>'
		},  
//		exporting: {
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
		legend : {
			align : 'right',
			verticalAlign : 'middle',
			layout : 'vertical'
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : true,
					color : '#000000',
					connectorColor : '#000000',
					format : '<b>{point.name}</b>: {point.percentage:.1f} %'
				},
				showInLegend : true
			}
		},
		series : [{
					type : 'pie',
					name : '日产气量占',
					data: data
				}]
	});

}

//刷新grid数据信息
autoRefreshPanelView=function(panelId){
	var jqzRealTimePanel_Id=Ext.getCmp(panelId);
	//var tabPanel = Ext.getCmp("frame_center_ids");
	//var activeId=tabPanel.getActiveTab().id;
	var tree_store=jqzRealTimePanel_Id.getStore();
	//alert("刷新grid数据信息");
	//alert(activeId=="monitor_MonitorPumpingUnit");
	//if((!tree_store.isLoading())&&activeId=="monitor_MonitorPumpingUnit"){
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

// 生成Grid-Fields 动态创建columns数据信息
createCbmCommonGridPanelColumns = function(columnInfo) {
	var myArr = columnInfo;
	var myColumns = "[";
	for (var i = 0; i < myArr.length; i++) {
		var attr = myArr[i];
		var width_="";
		if(isNotVal(attr.flex)){
			width_=",flex:"+attr.flex;
		}
		if(isNotVal(attr.width)){
			width_=",width:"+attr.width;
		}
		myColumns += "{text:'" + attr.header + "'";
		 if (attr.dataIndex == 'id'||attr.dataIndex=="jh as id"||attr.dataIndex=="jlbh") {
			myColumns +=width_+ ",xtype: 'rownumberer',locked:false,sortable : false,align:'center'";
		} else {
			myColumns +=width_+",sortable : false,align:'center',dataIndex:'"+ attr.dataIndex + "'";
		}
		myColumns += "}";
		if (i < myArr.length - 1) {
			myColumns += ",";
		}
	}
	myColumns += "]";
	return myColumns;
}

createCbmCommonColumns = function(columnInfo) {
	var myArr = columnInfo;
	var myColumns = "[";
	for (var i = 0; i < myArr.length; i++) {
		var attr = myArr[i];
		myColumns += "{text:'" + attr.header + "'";
		 if (attr.dataIndex == 'id'||attr.dataIndex=="jh as id"||attr.dataIndex=="jlbh") {
			myColumns += ",width:" + attr.width+ ",xtype: 'rownumberer',sortable : false,align:'center'";
		}else if(attr.dataType=='timestamp'||"updatetime"==attr.dataIndex){
			myColumns +=",width:" + attr.width+ ",sortable : false,align:'center',dataIndex:'"+attr.dataIndex+"'";
		} else {
			myColumns +=",sortable : false,align:'center',dataIndex:'"+ attr.dataIndex + "'";
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
			myColumns +=hidden_+lock_+width_+",align:'center',dataIndex:'"+attr.dataIndex+"'";
		}
		myColumns += "}";
		if (i < myArr.length - 1) {
			myColumns += ",";
		}
	}
	myColumns +="]";
	return myColumns;
}

//生成平衡监测Grid-Fields
function createBalanceMinitorListColumn(columnInfo) {
    var myArr = columnInfo;

    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var width_ = "";
        var lock_ = "";
        var hidden_ = "";
        if (attr.hidden == true) {
            hidden_ = ",hidden:true";
        }
        if (isNotVal(attr.lock)) {
            //lock_ = ",locked:" + attr.lock;
        }
        if (isNotVal(attr.width)) {
            width_ = ",width:" + attr.width;
        }
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' ";
        if (attr.dataIndex == 'gkmc') {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return getBackgroundColor(value,o,p,e);}";
        }else if (attr.dataIndex == 'id') {
            myColumns += ",width:" + attr.width + ",xtype: 'rownumberer',sortable : false,locked:false";
        }else if (attr.dataIndex == 'jhh' || attr.dataIndex == 'jh') {
            myColumns += width_ + ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "'";
        } else {
            myColumns += hidden_ + lock_ + width_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "'";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};


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
		}
		else {
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


createCBMHistoryColumns = function (columnInfo){
	var myArr = columnInfo;
	var myColumns = "[";
	for (var i = 0; i < myArr.length; i++) {
		var attr = myArr[i];
		var hidden_="";
		if(attr.hidden==true){
			hidden_=",hidden:true";
		}
		var locked_="";
		if(attr.locked==true){
//			locked_= ",locked : true";
		}
		var width_ = "";
		if(attr.width>0){
			width_= ",width : "+attr.width;
		}else{
			width_= ",flex : 1";
		}
		myColumns += "{text:'" + attr.header + "'";
		if (attr.dataIndex == 'jlbh'||attr.dataIndex == 'id') {
			myColumns += ",width:" + attr.width
					+ ",xtype: 'rownumberer',locked:false,sortable : false,align:'center'";
		} else {
			myColumns += hidden_+locked_+width_+",sortable : false,align:'center',dataIndex:'"
					+ attr.dataIndex + "'";
		}
		myColumns += "}";
		if (i < myArr.length - 1) {
			myColumns += ",";
		}
	}
	myColumns += "]";
	return myColumns;
}

/**
 * 集气站单外输饼状图外输气量气量统计方法
 * @param {} store
 * @param {} w
 * @param {} h
 */
var CbmJqzOutPutPieChartFn = function(store, w, h) {
	var items = store.data.items;
	var sum = 0.0;
	var datajson = [];
	Ext.Array.each(items, function(name, index, countriesItSelf) {
				var curVal = name.data.wsql;
				sum += parseFloat(curVal);
			});
	Ext.Array.each(items, function(name, index, countriesItSelf) {
				var wsql_ = parseFloat(items[index].get('wsql'));
				var percentage_ = (wsql_ / sum) * 100;
				datajson.push(percentage_);
			});
	var pieData_ = "[";
	if (isNotVal(datajson)) {
		Ext.Array.each(datajson, function(name, index, countriesItSelf) {
					var myVal = datajson[index];
					var lljmc_ = items[index].data.lljmc;

					if (index == 2) {
						pieData_ += " {name:'" + lljmc_ + "',y:" + myVal
								+ ", sliced: true,selected: true},"
					} else {
						pieData_ += "[ '" + lljmc_ + "'," + myVal + "],";
					}
				});
		if (pieData_.length > 1) {
			pieData_ = pieData_.substring(0, pieData_.length - 1);
		}
	}
	pieData_ += "]";

	var pieData_ = Ext.JSON.decode(pieData_);
	// alert(pieData_);
	createJqzOutPutPieChart(pieData_, w, h);

}


var createJqzOutPutPieChart =function (data, w, h) {
	$('#jqzOutPutPieCon').highcharts({
		chart : {
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false,
			width : w,
			height : h
		},
		credits : {
			enabled : false
		},
		title : {
			text : '集气站外输气量饼状图'
		},
		colors : ['#058DC7', '#50B432', '#ED561B', '#24CBE5', '#64E572',
				'#FF9655', '#FFF263', '#6AF9C4', '#DDDF00'],
		tooltip : {
			pointFormat : '{series.name} : <b>{point.percentage:.1f}%</b>'
		},
		legend : {
			align : 'right',
			verticalAlign : 'middle',
			layout : 'vertical'
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : true,
					color : '#000000',
					connectorColor : '#000000',
					format : '<b>{point.name}</b>: {point.percentage:.1f} %'
				},
				showInLegend : true
			}
		},
		series : [{
					type : 'pie',
					name : '外输气量占',
					data : data
				}]
	});

}

/*** 
  * 对 特殊字符进行重新编码 
  * **/ 
function URLencode(sStr){ 
     return encodeURI(sStr).replace(/\+/g, '%2B').replace(/\"/g,'%22').replace(/\'/g, '%27').replace(/\//g,'%2F').replace(/\#/g,'%23'); 
   } 

/* 
 * 拼接光杆功图曲线数据
 */
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

showFSDiagram = function(result, divid) {
	var positionCurveData=result.positionCurveData.split(",");
	var powerCurveData=result.loadCurveData.split(",");
	var xtext='<span style="text-align:center;">'+cosog.string.position+'<br />';
	if(result.fmax!=undefined && result.stroke!=undefined && result.liquidWeightProduction!=undefined){
		xtext+='最大载荷:' + result.fmax + 'kN 冲程:' + result.stroke + 'm 产液:' + result.liquidWeightProduction + 't/d<br />';
	}
	if(result.fmin!=undefined && result.spm!=undefined && result.resultName!=undefined){
		xtext+='最小载荷:' + result.fmin + 'kN 冲次:' + result.spm + '/min 工况:' + result.resultName + '<br /></span>';
	}
	
	
	var data = "["; // 功图data
	var upStrokeData = "["; // 上冲程数据
	var downStrokeData = "["; // 下冲程数据
	var minIndex=0,maxIndex=0;
	var gtcount=positionCurveData.length; // 功图点数
	if(positionCurveData.length>0){
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
	initFSDiagramChart(pointdata,upStrokePointdata,downStrokePointdata, result, divid,"光杆功图",xtext,"载荷(kN)",['#FF6633','#009999']);
	return false;
}

showFSDiagramWithAtrokeSPM = function(result, divid,title) {
	if (!isNotVal(title)){
		title='光杆功图';
	}
	var positionCurveData=result.positionCurveData.split(",");
	var loadCurveData=result.loadCurveData.split(",");
	var xtext='<span style="text-align:center;">'+cosog.string.position+'<br />';
	if(result.stroke!=undefined && result.SPM!=undefined){
		xtext+='冲程:' + result.stroke + 'm 冲次:' + result.SPM + '/min<br />';
	}
	if(result.fmax!=undefined && result.fmax!='' && result.fmax!='0' && result.fmax!=0 && result.fmin!=undefined && result.fmin!='' && result.fmin!='0' && result.fmin!=0){
		xtext+='最大载荷:' + result.fmax + 'kN 最小载荷:' + result.fmin + 'kN<br />';
	}
	xtext+='</span>';
	
	var data = "["; // 功图data
	var upStrokeData = "["; // 上冲程数据
	var downStrokeData = "["; // 下冲程数据
	var minIndex=0,maxIndex=0;
	var gtcount=positionCurveData.length; // 功图点数
	if(positionCurveData.length>0){
		for (var i=0; i <= positionCurveData.length; i++) {
			if(i<positionCurveData.length){
				data += "[" + changeTwoDecimal(positionCurveData[i]) + ","+changeTwoDecimal(loadCurveData[i])+"],";
			}else{
				data += "[" + changeTwoDecimal(positionCurveData[0]) + ","+changeTwoDecimal(loadCurveData[0])+"]";//将图形的第一个点拼到最后面，使图形闭合
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
				upStrokeData += "[" + changeTwoDecimal(positionCurveData[i]) + ","+changeTwoDecimal(loadCurveData[i])+"]";
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
				downStrokeData += "[" + changeTwoDecimal(positionCurveData[index]) + ","+changeTwoDecimal(loadCurveData[index])+"]";
				if(i<downStrokeCount+1){
					downStrokeData+=",";
				}
			}
		}else{//如果最小值索引大于最大值索引
			for(var i=maxIndex;i<=minIndex;i++){
				downStrokeData += "[" + changeTwoDecimal(positionCurveData[i]) + ","+changeTwoDecimal(loadCurveData[i])+"]";
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
				upStrokeData +="[" + changeTwoDecimal(positionCurveData[index]) + ","+changeTwoDecimal(loadCurveData[index])+"]";
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
	initFSDiagramChart(pointdata,upStrokePointdata,downStrokePointdata, result, divid,title,xtext,"载荷(kN)",['#FF6633','#009999']);
	return false;
}

showFSDiagram360WithAtrokeSPM = function(diagramData, divid,title) {
	if (!isNotVal(title)){
		title='光杆功图(360点)';
	}
	var positionCurveData=diagramData.position360CurveData.split(",");
	var loadCurveData=diagramData.load360CurveData.split(",");
	var fmax=0;
	var fmin=0;
	
	var data = "["; // 功图data
	var upStrokeData = "["; // 上冲程数据
	var downStrokeData = "["; // 下冲程数据
	var minIndex=0,maxIndex=0;
	var gtcount=positionCurveData.length; // 功图点数
	if(positionCurveData.length>0){
		fmax=loadCurveData[0];
		fmin=loadCurveData[0];
		for (var i=0; i <= positionCurveData.length; i++) {
			if(i<positionCurveData.length){
				data += "[" + changeTwoDecimal(positionCurveData[i]) + ","+changeTwoDecimal(loadCurveData[i])+"],";
			}else{
				data += "[" + changeTwoDecimal(positionCurveData[0]) + ","+changeTwoDecimal(loadCurveData[0])+"]";//将图形的第一个点拼到最后面，使图形闭合
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
			
			if(parseFloat(loadCurveData[i])<parseFloat(fmin)){
				fmin=parseFloat(loadCurveData[i]);
			}
			if(parseFloat(loadCurveData[i])>parseFloat(fmax)){
				fmax=parseFloat(loadCurveData[i]);
			}
		}
		
		if(minIndex<=maxIndex){//如果最小值索引小于最大值索引
			for(var i=minIndex;i<=maxIndex;i++){
				upStrokeData += "[" + changeTwoDecimal(positionCurveData[i]) + ","+changeTwoDecimal(loadCurveData[i])+"]";
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
				downStrokeData += "[" + changeTwoDecimal(positionCurveData[index]) + ","+changeTwoDecimal(loadCurveData[index])+"]";
				if(i<downStrokeCount+1){
					downStrokeData+=",";
				}
			}
		}else{//如果最小值索引大于最大值索引
			for(var i=maxIndex;i<=minIndex;i++){
				downStrokeData += "[" + changeTwoDecimal(positionCurveData[i]) + ","+changeTwoDecimal(loadCurveData[i])+"]";
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
				upStrokeData +="[" + changeTwoDecimal(positionCurveData[index]) + ","+changeTwoDecimal(loadCurveData[index])+"]";
				if(i<upStrokeCount+1){
					upStrokeData+=",";
				}
			}
		}
		
	}
	data+="]";
	upStrokeData+="]";
	downStrokeData+="]";
	
	var xtext='<span style="text-align:center;">'+cosog.string.position+'<br />';
	if(diagramData.stroke!=undefined && diagramData.SPM!=undefined){
		xtext+='冲程:' + diagramData.stroke + 'm 冲次:' + diagramData.SPM + '/min<br />';
	}
	if(fmax>0 && fmin>0){
		xtext+='最大载荷:' + fmax + 'kN 最小载荷:' + fmin + 'kN<br />';
	}
	xtext+='</span>';
	
	var pointdata = Ext.JSON.decode(data);
	var upStrokePointdata = Ext.JSON.decode(upStrokeData);
	var downStrokePointdata = Ext.JSON.decode(downStrokeData);
	initFSDiagramChart(pointdata,upStrokePointdata,downStrokePointdata, diagramData, divid,title,xtext,"载荷(kN)",['#FF6633','#009999']);
	return false;
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

showPContinuousDiagram = function(diagramData,title,subtitle,xtext,ytext,color, divid) {
	var powerCurveData=diagramData.split(",");
	var data = "["; // 功图data
	if(powerCurveData.length>0){
		for (var i=0; i < powerCurveData.length; i++) {
			data += "[" + (i+1) + ","+changeTwoDecimal(powerCurveData[i])+"]";
			if(i<powerCurveData.length-1){
				data += ",";
			}
		}
	}
	data+="]";
	var pointdata = Ext.JSON.decode(data);
	initContinuousDiagramChart(pointdata, divid,title,subtitle,xtext,ytext,color);
	return false;
}

showPowerByAngleContinuousDiagram = function(diagramData,angleData,title,subtitle,xtext,ytext,color, divid) {
	var powerCurveData=diagramData.split(",");
	var angleCurveData=angleData.split(",");
	var data = "["; // 功图data
	var catagories = "[";
	if(powerCurveData.length>0){
		for (var i=0; i < powerCurveData.length; i++) {
			catagories+=angleCurveData[i];
			data+=changeTwoDecimal(powerCurveData[i]);
			if(i<powerCurveData.length-1){
				catagories += ",";
				data += ",";
			}
		}
	}
	data+="]";
	catagories+="]";
	var pointdata = Ext.JSON.decode(data);
	var cat = Ext.JSON.decode(catagories);
	initAngleContinuousDiagramChart(pointdata,cat, divid,title,subtitle,xtext,ytext,color);
	return false;
}

showThreeTermsContinuousDiagram = function(angle,dataA,dataB,dataC,title,subtitle,xtext,ytext,color, divid) {
	var legendName = ['A相','B相','C相'];
	var catagories1 = "[";
    var series1 = "[";
    var angleArr=angle.split(",");
    var dataAArr=dataA.split(",");
    var dataBArr=dataB.split(",");
    var dataCArr=dataC.split(",");
    if(dataAArr.length>0 && angleArr.length>0){
    	var ATermsData="{\"name\":\"A相\",\"data\":[";
    	var BTermsData="{\"name\":\"B相\",\"data\":[";
    	var CTermsData="{\"name\":\"C相\",\"data\":[";
        for(var i=0;i<dataAArr.length;i++){
        	catagories1+=angleArr[i];
        	ATermsData+=changeTwoDecimal(dataAArr[i]);
        	BTermsData+=changeTwoDecimal(dataAArr[i]);
        	CTermsData+=changeTwoDecimal(dataAArr[i]);
        	if(i<dataAArr.length-1){
        		catagories1+=",";
        		ATermsData+=",";
        		BTermsData+=",";
        		CTermsData+=",";
        	}
        }
        ATermsData+="]}";
        BTermsData+="]}";
        CTermsData+="]}";
    	series1+=ATermsData+","+BTermsData+","+CTermsData;
    }
    catagories1+="]";
    series1+="]";
    
    var cat1 = Ext.JSON.decode(catagories1);
	var ser1 = Ext.JSON.decode(series1);
	initThreeTermsContinuousDiagram(cat1,ser1,divid,title,subtitle,xtext,ytext,color)
	return false;
}

function initThreeTermsContinuousDiagram(catagories,series,divId,title,subtitle,xtext,ytext,color) {
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
					// center
				},
				subtitle: {
		        	text:subtitle                                                  
		        },
				colors :color,
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
				legend : {
					align : 'center',
					verticalAlign : 'bottom',
					layout : 'horizontal' //vertical 竖直 horizontal-水平
				},
				series : series
			});
}

showAContinuousDiagram = function(diagramData,title,subtitle,xtext,ytext,color, divid) {
	var currentCurveData=diagramData.split(",");
	var data = "["; // 功图data
	if(currentCurveData.length>0){
		for (var i=0; i < currentCurveData.length; i++) {
			data += "[" + (i+1) + ","+changeTwoDecimal(currentCurveData[i])+"]";
			if(i<currentCurveData.length-1){
				data += ",";
			}
		}
	}
	data+="]";
	var pointdata = Ext.JSON.decode(data);
	initContinuousDiagramChart(pointdata, divid,title,subtitle,xtext,ytext,color);
	return false;
}

showAngleLoadContinuousDiagram = function(diagramData,divid) {
	var angleCurveData=diagramData.angle360CurveData.split(",");
	var loadCurveData=diagramData.load360CurveData.split(",");
	var title="曲柄角度-悬点载荷曲线(360点)";
	var subtitle=diagramData.wellName+' ['+diagramData.acqTime+']';
	var xtext='曲柄转角(°)';
	var ytext='悬点载荷(kN)';
	var color='#FF6633';
	var XReversed=false;
	var data = "["; // 功图data
	if(angleCurveData.length>0){
		if(angleCurveData[0]!=0){//如果是逆时针 将最后0°的数据补充到最前端360°数据
			data += "[360,"+changeTwoDecimal(loadCurveData[loadCurveData.length-1])+"],";
			XReversed=true;//X轴翻转
		}
		for (var i=0; i < angleCurveData.length; i++) {
			data += "[" + angleCurveData[i] + ","+changeTwoDecimal(loadCurveData[i])+"]";
			if(i<angleCurveData.length-1){
				data += ",";
			}
		}
		if(angleCurveData[0]==0){//如果是顺时针 将最前0°数据补充到最后段360°数据
			data += ",[360,"+changeTwoDecimal(loadCurveData[0])+"]";
			XReversed=false;
		}
	}
	data+="]";
	var pointdata = Ext.JSON.decode(data);
	initAF360ContinuousDiagramChart(pointdata, divid,title,subtitle,xtext,ytext,color,XReversed,30,15);
	return false;
}

showRPMContinuousDiagram = function(diagramData,title,subtitle,xtext,ytext,color, divid) {
	var rpmCurveData=diagramData.split(",");
	var data = "["; // 功图data
	if(rpmCurveData.length>0){
		for (var i=0; i <= rpmCurveData.length; i++) {
			data += "[" + (i+1) + ","+changeTwoDecimal(rpmCurveData[i])+"]";
			if(i<rpmCurveData.length-1){
				data += ",";
			}
		}
	}
	data+="]";
	var pointdata = Ext.JSON.decode(data);
	initContinuousDiagramChart(pointdata, divid,title,subtitle,xtext,ytext,color);
	return false;
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
    	var loadData="{\"name\":\"载荷\",\"data\":[";
    	var crankData="{\"name\":\"曲柄\",\"data\":[";
    	var balanceData="{\"name\":\"平衡块\",\"data\":[";
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
//抽油机运动特性曲线和光杆位置扭矩因数表
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
    
//    xtext+='最大载荷:' + fmax + 'kN 冲程:' + stroke + 'm 产液:' + liquidProduction + productionUnitStr+ '<br />';
//    xtext+='最小载荷:' + fmin + 'kN 冲次:' + spm + '/min 工况:' + resultName + '<br /></span>';
    var upperlimit=parseFloat(fmax)+10;
//    if(parseFloat(upperLoadLine)==0||upperLoadLine==""||parseFloat(fmax)==0||fmax==""){
//    	upperlimit=null
//    }else 
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
//                        style: {
//                            color: '#000',
//                            fontWeight: 'normal'
//                        }
                    },
		            allowDecimals: false,    // 刻度值是否为小数
		            minorTickInterval: '',   // 不显示次刻度线
		            min: 0                  // 最小值
//		            max:upperlimit,
//		            plotLines: [{   //一条延伸到整个绘图区的线，标志着轴中一个特定值。
//	                    color: '#d12',
//	                    dashStyle: 'Dash', //Dash,Dot,Solid,shortdash,默认Solid
//	                    label: {
//	                        text: upperLoadLine,
//	                        align: 'right',
//	                        x: -10
//	                    },
//	                    width: 3,
//	                    value: upperLoadLine,  //y轴显示位置
//	                    zIndex: 10
//	                },{
//	                    color: '#d12',
//	                    dashStyle: 'Dash',
//	                    label: {
//	                        text: lowerLoadLine,
//	                        align: 'right',
//	                        x: -10
//	                    },
//	                    width: 3,
//	                    value: lowerLoadLine,  //y轴显示位置
//	                    zIndex: 10
//	                }]
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

function initTowColorSurfaceCardChart(upStrokePointdata,downStrokePointdata, gtdata, divid) {
	var wellName=gtdata.wellName;         // 井名
	var acqTime=gtdata.acqTime;     // 采集时间
	var upperLoadLine=gtdata.upperLoadLine;   // 理论上载荷
	var lowerLoadLine=gtdata.lowerLoadLine;   // 理论下载荷
	var fmax=gtdata.fmax;     // 最大载荷
	var fmin=gtdata.fmin;     // 最小载荷
	var stroke=gtdata.stroke;       // 冲程
	var spm=gtdata.spm;       // 冲次
	var liquidProduction=gtdata.liquidProduction;     // 日产液量
	var resultName=gtdata.resultName;     // 工况类型
	var xtext='<span style="text-align:center;">'+cosog.string.position+'<br />';
    xtext+='最大载荷:' + fmax + 'kN 冲程:' + stroke + 'm 产液:' + liquidProduction + 't/d<br />';
    xtext+='最小载荷:' + fmin + 'kN 冲次:' + spm + '/min 工况:' + resultName + '<br /></span>';
    var upperlimit=parseFloat(fmax)+10;
    if(parseFloat(upperLoadLine)==0||parseFloat(fmax)==0){
    	upperlimit=null
    }else if(parseFloat(upperLoadLine)>=parseFloat(fmax)){
    	upperlimit=parseFloat(upperLoadLine)+10;
    }
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',     // 散点图   
		            renderTo : divid,
		            zoomType: 'xy',
		            reflow: true
		        },                                                                                   
		        title: {
		        	text: cosog.string.FSDiagram  // 光杆功图                        
		        },                                                                                   
		        subtitle: {
		        	text:wellName+' ['+acqTime+']'                                                
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
//                        style: {
//                            color: '#000',
//                            fontWeight: 'normal'
//                        }
                    },
		            allowDecimals: false,    // 刻度值是否为小数
		            minorTickInterval: '',   // 不显示次刻度线
		            min: 0,                  // 最小值
		            max:upperlimit,
		            plotLines: [{   //一条延伸到整个绘图区的线，标志着轴中一个特定值。
	                    color: '#666666',
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
	                    color: '#666666',
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
		                    pointFormat: '{point.x},{point.y}'                                
		                }                                                                            
		            }                                                                                
		        },
		        series: [{                                                               
		            name: '',                                                                  
		            color: '#CC0000',   
		            lineWidth:3,
		            data:  upStrokePointdata                                                                                  
		        },{                                                               
		            name: '',                                                                  
		            color: '#00FF00',   
		            lineWidth:3,
		            data:  downStrokePointdata                                                                                  
		        }]
	});
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

function initAngleContinuousDiagramChart(pointdata,catagories, divid,title,subtitle,xtext,ytext,color) {
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'spline',     // 散点图   
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
		            tickInterval : 40,
		            categories : catagories,
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
//		        tooltip: {
//					headerFormat: '<b>{series.name}</b><br/>',
//					pointFormat: '曲柄转角:{point.x}, {series.name}:{point.y}'
//				},
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
		        plotOptions: {                                                                       
		        	spline: {
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
		            name: ytext,                                                                  
		            color: color,   
		            lineWidth:3,
		            data:  pointdata                                                                                  
		        }]
	});
}

function initAF360ContinuousDiagramChart(pointdata, divid,title,subtitle,xtext,ytext,color,XReversed,tickInterval,minorTickInterval) {
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',     // 散点图   
		            renderTo : divid,
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
		            reversed:XReversed,
		            startOnTick: false,      //是否强制轴线在标线处开始
		            endOnTick: false,        //是否强制轴线在标线处结束                                                        
		            showLastLabel: true,
		            allowDecimals: false,    // 刻度值是否为小数
//		            min:0,
		            tickInterval : tickInterval,
		            tickPosition:'inside',
		            minorTickInterval: minorTickInterval,    // 最小刻度间隔
		            minorTickPosition:'inside',
		            minorGridLineDashStyle:'Dash',
		            minorTickLength:5,
		            minorTickWidth:1
		        },                                                                                   
		        yAxis: {                                                                             
		            title: {                                                                         
		                text: ytext   // 载荷（kN） 
                    },
		            allowDecimals: false,    // 刻度值是否为小数
		            minorTickInterval: 'auto'   // 不显示次刻度线
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

function initFSDiagramChart(pointdata,upStrokePointdata,downStrokePointdata, gtdata, divid,title,xtext,ytext,color) {
	var wellName=gtdata.wellName;         // 井名
	var acqTime=gtdata.acqTime;     // 采集时间
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',     // 散点图   
		            renderTo : divid,
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
		            min:0,
		            minorTickInterval: ''    // 最小刻度间隔
		        },                                                                                   
		        yAxis: {                                                                             
		            title: {                                                                         
		                text: ytext   // 载荷（kN） 
                    },
		            allowDecimals: false,    // 刻度值是否为小数
		            minorTickInterval: '',   // 不显示次刻度线
		            min: 0                  // 最小值
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
		            color: '#00ff00',
		            lineWidth:3,
		            data:  pointdata                                                                                  
		        }]
	});
}

/* 
 * 拼接泵功图曲线数据
 * li 2015-05-05
 */
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

/* 
 * 初始化多个功图曲线数据
 * li 2015-07-23
 */
function initMultiSurfaceCardChart(series, title, wellName, acqTime, divid,upperLoadLine,lowerLoadLine) {
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
		                text: cosog.string.load    // 载荷（kN）                                                          
		            },
		            allowDecimals: false,    // 刻度值是否为小数
		            //endOnTick: false,        //是否强制轴线在标线处结束   
		            minorTickInterval: '',    // 不显示次刻度线
//		            min:0,
		            plotLines: [{   //一条延伸到整个绘图区的线，标志着轴中一个特定值。
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

/* 
 * 拼接杆柱应力数据
 * li 2015-05-05
 */
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

/* 
 * 初始化杆柱应力数据
 * li 2015-05-05
 */
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

/* 
 * 拼接泵效组成数据
 * li 2015-05-07
 */
showPumpEfficiency = function(bxzcData, divid) {
	var wellName=bxzcData.wellName;           // 井名
	var acqTime=bxzcData.acqTime;       // 时间
	var pumpEff1=bxzcData.pumpEff1;   // 冲程损失系数
	var pumpEff2=bxzcData.pumpEff2;       // 充满系数
	var pumpEff3=bxzcData.pumpEff3;       // 漏失系数
	var pumpEff4=bxzcData.pumpEff4;   // 液体收缩系数
	var ydata="[" + pumpEff1 + "," + pumpEff2 + "," + pumpEff3 + "," + pumpEff4 + "]";  // 拼y轴数据
	if(pumpEff1==0&&pumpEff2==0&&pumpEff3==0&&pumpEff4==0){
		ydata="[]";
	}
	ydata = Ext.JSON.decode(ydata);
	initPumpEfficiencyChart(ydata, wellName, acqTime, divid);
//	initPumpEfficiencyChartTest(divid);
	return false;
}

/* 
 * 初始化泵效组成数据
 * li 2015-05-07
 */
function initPumpEfficiencyChart(ydata, wellName, acqTime, divid, title, yname) {
	$('#'+divid).highcharts({
				chart: {                                                                             
		            type: 'column',                      // 柱状图
//		            renderTo : divid,                    // 图形放置的位置
		            borderWidth : 0,
		            zoomType: 'xy'                      // 沿xy轴放大
		        },                                                                                   
		        title: {                                 // 标题                                                                      
		            text: cosog.string.pumpEff              // 泵效组成  
		        },
		        subtitle: {                              // 子标题                                                                   
		            text: wellName+' ['+acqTime+']'                                                      
		        },
		        colors: ['#66ffcc', '#009999', '#ffcc33', '#ff6633', '#00ffff', '#3366cc', '#ffccff', '#cc0000', '#6AF9C4'],
		        credits: {                               // 版权信息
		            enabled: false
		        },
		        xAxis: { 
		        	categories: [
		        	                cosog.string.pumpEff1, // 'η冲程'
		        	                cosog.string.pumpEff2,   // 'η充满'
		        	                cosog.string.pumpEff3,   // 'η漏失'
		        	                cosog.string.pumpEff4  // 'η收缩'
		        	            ],
		        	gridLineWidth: 0                     // 网格线宽度
		        }, 
		        tooltip: {
		            enabled: false                       // 不显示提示框
		        },
		        yAxis: {    
		        	min: 0,
		            title: {                                                                         
		                text: cosog.string.percent           // 百分数(%)                                                          
		            },
		            //endOnTick: false,                  //是否强制轴线在标线处结束
		            minorTickInterval: ''                // 不显示次刻度线
		        },
//		        exporting: {   // 导出按钮
//		            buttons: {
//		                contextButton: {
//		                    menuItems: [{
//		                        separator: true
//		                    }]
//		                    .concat(Highcharts.getOptions().exporting.buttons.contextButton.menuItems)
//		                    .concat([{
//		                        separator: true
//		                    }
//		                    ])
//		                }
//		            }
//		        },
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
		            dataLabels: {   // 柱子上的数据标签
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
//	SetEveryOnePointColor(mychart);           // 设置每一个数据点的颜色渐变
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

//饼状统计图
function initRealtimeWorkStastPieChart(title, name, data, divid) {
    mychart = new Highcharts.Chart({
        chart: {
            type: 'pie',
            borderWidth: 0,
            backgroundColor: '#ffffff',
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            renderTo: divid,
            options3d: {
                enabled: true,
                alpha: 15,
                beta: 0
            }
        },
        credits: {
            enabled: false
        },
        title: {
            text: null
        },
        tooltip: {
            pointFormat: '{series.name} : <b>{point.percentage:.2f}%</b>'
        },
        colors: ['#058DC7', '#50B432', '#ED561B', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4', '#DDDF00'],
//        exporting: {
//            enabled: false
//        },
        exporting:{    
            enabled:true,    
            filename:'class-booking-chart',    
            url:context + '/exportHighcharsPicController/export'
       },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    enabled: true,
                    //format: '<b>{point.name}</b>: {point.percentage:.2f}%'
                    format: '{point.name}'
                },
                showInLegend: true
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            type: 'pie',
            name: name,
            data: data
    }]
    });
}

showElectricDiagnoseWorkStatusPie = function(store, divid) {
	var groups=store.getGroups().items;
	var series = "[";
	if(groups!=undefined && groups.length>0){
		for(var i=0;i<groups.length;i++){
			series+="['"+groups[i]._groupKey+"',"+groups[i].length+"]";
			if(i!=groups.length-1){
				series+=",";
			}
		}
	}
	series += "]";
	var data = Ext.JSON.decode(series);
	initRealtimeWorkStastPieChart("", "井数占", data, divid);
	return false;
}

/* 
 * 拼接电参相关系数曲线数据信息
 */
showElectricCorrelationCoefficientCurve = function(store, divid) {
	var get_rawData=store.proxy.reader.rawData;
	var currentA = [],
	currentB = [],
	currentC=[],
	voltageA=[],
	voltageB=[],
	voltageC=[];
	var jslx=get_rawData.jslx;
	var title="";
	var tool="";
	if(jslx.indexOf("4")==0){
		tltle="螺杆泵井差值曲线";
		tool="差值";
	}else{
		tltle="相关系数曲线";
		tool="相关系数"
	}
	var cjsjArr =get_rawData.cjsj.split(",");
	var currentAArr = get_rawData.currentA.split(",");
	var currentBArr = get_rawData.currentB.split(",");
	var currentCArr = get_rawData.currentC.split(",");
	var voltageAArr = get_rawData.voltageA.split(",");
	var voltageBArr = get_rawData.voltageB.split(",");
	var voltageCArr = get_rawData.voltageC.split(",");
	
	for(var j=0;j<get_rawData.totalCount;j++){
		if(cjsjArr[j]!=""&&cjsjArr[j]!=null){
			currentA.push([
			               Date.parse(cjsjArr[j].replace(/-/g, '/')),
			               isNaN(currentAArr[j])?null:parseFloat(currentAArr[j])
			]);
			currentB.push([
			               Date.parse(cjsjArr[j].replace(/-/g, '/')),
			               isNaN(currentBArr[j])?null:parseFloat(currentBArr[j])
			]);
			currentC.push([
			               Date.parse(cjsjArr[j].replace(/-/g, '/')),
			               isNaN(currentCArr[j])?null:parseFloat(currentCArr[j])
			]);
			voltageA.push([
			               Date.parse(cjsjArr[j].replace(/-/g, '/')),
			               isNaN(voltageAArr[j])?null:parseFloat(voltageAArr[j])
			]);
			voltageB.push([
			              Date.parse(cjsjArr[j].replace(/-/g, '/')),
			              isNaN(voltageBArr[j])?null:parseFloat(voltageBArr[j])
		    ]);
			voltageC.push([
			               Date.parse(cjsjArr[j].replace(/-/g, '/')),
			               isNaN(voltageCArr[j])?null:parseFloat(voltageCArr[j])
		    ]);
		}
	}
	initElectricCorrelationCoefficientCurve(currentA, currentB,currentC,voltageA,voltageB, voltageC, divid,tltle,tool);
	return false;
}

function initElectricCorrelationCoefficientCurve(currentA, currentB,currentC,voltageA,voltageB, voltageC, divid,tltle,tool) {
	Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
	mychart = new Highcharts.StockChart({
		chart: {
            renderTo : divid,
            //marginLeft: 50,
        }, 
        navigator:{
        	enabled:false
        	//hidden:true
        },
        scrollbar:{
            enabled:false  
         },
//        exporting: {    
//            enabled: false //是否能导出趋势图图片  
//        },
         exporting:{    
             enabled:true,    
             filename:'class-booking-chart',    
             url:context + '/exportHighcharsPicController/export'
        },
        title: {
            text: tltle
        },
        tooltip:{  
            // 日期时间格式化  
            xDateFormat: '%Y-%m-%d %H:%M:%S'
        },
        credits: {
            enabled: false
        },
        plotOptions:{
        	series:{
        		dataGrouping:{
        			groupPixelWidth:0.1
        		}
        	}
        },
        xAxis: {  
            //tickPixelInterval: 200,//x轴上的间隔  
            type: 'datetime', //定义x轴上日期的显示格式  
            labels: {  
            formatter: function() {  
                var vDate=new Date(this.value);  
                //alert(this.value);  
                //return vDate.getFullYear()+"-"+(vDate.getMonth()+1)+"-"+vDate.getDate()+" "+vDate.getDay()+":"+vDate.getMinutes()+":"+vDate.getSeconds();  
                return vDate.getFullYear()+"-"+(vDate.getMonth()+1)+"-"+vDate.getDate(); 
                },  
            align: 'center'  
            }
        },
        yAxis: [{
        	opposite:false,
            labels: {
            	fontWeight:'normal',
                align: 'left',
                x: 0
            },
            title: {
                text: 'A相电流'
            },
            endOnTick: false,
            //min:0,
            height: '15%',
            lineWidth: 1
        },{
        	opposite:false,
            labels: {
                align: 'left',
                x: 0
            },
            title: {
                text: 'B相电流'
            },
            //min:0,
            top: '17%',
            height: '15%',
            offset: 0,
            lineWidth: 1
        }, {
        	opposite:false,
            labels: {
                align: 'left',
                x: 0
            },
            title: {
                text: 'C相电流'
            },
            //max:100,
            //min:0,
            top: '34%',
            height: '15%',
            offset: 0,
            lineWidth: 1
        }, {
        	opposite:false,
            labels: {
                align: 'left',
                x: 0
            },
            title: {
                text: 'A相电压'
            },
            //max:100,
            //min:0,
            top: '51%',
            height: '15%',
            offset: 0,
            lineWidth: 1
        }, {
        	opposite:false,
            labels: {
                align: 'left',
                x: 0
            },
            title: {
                text: 'B相电压'
            },
            //max:100,
            //min:0,
            top: '68%',
            height: '15%',
            offset: 0,
            lineWidth: 1
        }, {
        	opposite:false,
            labels: {
                align: 'left',
                x: 0
            },
            title: {
                text: 'C相电压'
            },
            //max:100,
            //min:0,
            top: '85%',
            height: '15%',
            offset: 0,
            lineWidth: 1
        }],
        rangeSelector: {  
            enabled:false
        },  
        series: [{
            	type: 'spline',
            	name: 'A相电流'+tool,
            	data: currentA,
            	
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 0
            },{
            	type: 'spline',
            	name: 'B相电流'+tool,
            	data: currentB,
            	
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 1
            },{
            	type: 'spline',
            	name: 'C相电流'+tool,
            	data: currentC,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 2
            }
            ,{
            	type: 'spline',
            	name: 'A相电压'+tool,
            	data: voltageA,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 3
            }
            ,{
            	type: 'spline',
            	name: 'B相电压'+tool,
            	data: voltageB,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 4
            },{
            	type: 'spline',
            	name: 'C相电压'+tool,
            	data: voltageC,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 5
            }]
	});
}

/* 
 * 拼接电参相关系数曲线数据信息
 */
showElectricOverlayAnalysisCurve = function(store, divid) {
	var get_rawData=store.proxy.reader.rawData;
	var newCurrentA = [],
	lastCurrentA = [],
	newCurrentB = [],
	lastCurrentB = [],
	newCurrentC = [],
	lastCurrentC = [],
	newVoltageA = [],
	lastVoltageA = [],
	newVoltageB = [],
	lastVoltageB = [],
	newVoltageC = [],
	lastVoltageC = [];
	var newCurrentAArr=null,laseCurrentAArr=null,newCurrentBArr=null,laseCurrentBArr=null,newCurrentCArr=null,laseCurrentCArr=null,
        newVoltageAArr=null,laseVoltageAArr=null,newVoltageBArr=null,laseVoltageBArr=null,newVoltageCArr=null,laseVoltageCArr=null;
    if(get_rawData.totals>0&&get_rawData.currentA.length>0&&get_rawData.currentB.length>0&&get_rawData.currentC.length>0&&get_rawData.voltageA.length>0&&get_rawData.voltageB.length>0&&get_rawData.voltageC.length>0){
        newCurrentAArr=get_rawData.currentA[0].curvedata.split(",");
        if(get_rawData.totals==2)
            laseCurrentAArr=get_rawData.currentA[1].curvedata.split(",");
        
        newCurrentBArr=get_rawData.currentB[0].curvedata.split(",");
        if(get_rawData.totals==2)
            laseCurrentBArr=get_rawData.currentB[1].curvedata.split(",");
        
        newCurrentCArr=get_rawData.currentC[0].curvedata.split(",");
        if(get_rawData.totals==2)
            laseCurrentCArr=get_rawData.currentC[1].curvedata.split(",");
        
        newVoltageAArr=get_rawData.voltageA[0].curvedata.split(",");
        if(get_rawData.totals==2)
            laseVoltageAArr=get_rawData.voltageA[1].curvedata.split(",");
        
        newVoltageBArr=get_rawData.voltageB[0].curvedata.split(",");
        if(get_rawData.totals==2)
            laseVoltageBArr=get_rawData.voltageB[1].curvedata.split(",");
        
        newVoltageCArr=get_rawData.voltageC[0].curvedata.split(",");
        if(get_rawData.totals==2)
            laseVoltageCArr=get_rawData.voltageC[1].curvedata.split(",");
        
        var currentAlength=get_rawData.totals==2?newCurrentAArr.length+laseCurrentAArr.length-get_rawData.currentA[0].endposition+get_rawData.currentA[0].startposition-1:newCurrentAArr.length;
        
        var currentBlength=get_rawData.totals==2?newCurrentBArr.length+laseCurrentBArr.length-get_rawData.currentB[0].endposition+get_rawData.currentB[0].startposition-1:newCurrentBArr.length;
        
        var currentClength=get_rawData.totals==2?newCurrentCArr.length+laseCurrentCArr.length-get_rawData.currentC[0].endposition+get_rawData.currentC[0].startposition-1:newCurrentCArr.length;
        
        var voltageAlength=get_rawData.totals==2?newVoltageAArr.length+laseVoltageAArr.length-get_rawData.voltageA[0].endposition+get_rawData.voltageA[0].startposition-1:newVoltageAArr.length;
        
        var voltageBlength=get_rawData.totals==2?newVoltageBArr.length+laseVoltageBArr.length-get_rawData.voltageB[0].endposition+get_rawData.voltageB[0].startposition-1:newVoltageBArr.length;
        
        var voltageClength=get_rawData.totals==2?newVoltageCArr.length+laseVoltageCArr.length-get_rawData.voltageC[0].endposition+get_rawData.voltageC[0].startposition-1:newVoltageCArr.length;
        
        for(var i=1;i<=currentAlength;i++){
            var newvalue=null;
            var lastvalue=null;
            if(get_rawData.totals==1){
            	newvalue=isNaN(newCurrentAArr[i-1])?null:parseFloat(newCurrentAArr[i-1]);
                newCurrentA.push([i,newvalue]);
            }else{
            	if(!((get_rawData.currentA[0].startposition==1&&i<get_rawData.currentA[0].startposition_last)||(get_rawData.currentA[0].startposition>1&&i>get_rawData.currentA[0].endposition))){
                    if(get_rawData.currentA[0].startposition==1){
                        if(i<get_rawData.currentA[0].startposition_last){
                            newvalue=null
                        }else{
                            newvalue=isNaN(newCurrentAArr[i-get_rawData.currentA[0].startposition_last])?null:parseFloat(newCurrentAArr[i-get_rawData.currentA[0].startposition_last]);
                        }
                        if(i==get_rawData.currentA[0].startposition_last||i==get_rawData.currentA[0].endposition_last){
                            var temp="{x:"+i+",y:"+newvalue+",marker:{enabled:true,fillColor:'red',radius:5}}";
                            newCurrentA.push(eval('(' + temp + ')'));
                        }else{
                            newCurrentA.push([i,newvalue]);
                        }
                    }else{
                        if(i>get_rawData.currentA[0].endposition){
                           newvalue=null 
                        }else{
                            newvalue=isNaN(newCurrentAArr[i-1])?null:parseFloat(newCurrentAArr[i-1]);
                        }
                        if(i==get_rawData.currentA[0].startposition||i==get_rawData.currentA[0].endposition){
                            var temp="{x:"+i+",y:"+newvalue+",marker:{enabled:true,fillColor:'red',radius:5}}";
                            newCurrentA.push(eval('(' + temp + ')'));
                        }else{
                            newCurrentA.push([i,newvalue]);
                        }
                    }
                }else{
                    newCurrentA.push([i,newvalue]);
                }
                
            }
            
            if(get_rawData.totals==2){
                if(!((get_rawData.currentA[0].startposition==1&&i>get_rawData.currentA[0].endposition_last)||(get_rawData.currentA[0].startposition>1&&i<get_rawData.currentA[0].startposition))){
                    if(get_rawData.currentA[0].startposition==1){
                        if(i>get_rawData.currentA[0].endposition_last){
                            lastvalue=null;
                        }else{
                            lastvalue=isNaN(laseCurrentAArr[i-1])?null:parseFloat(laseCurrentAArr[i-1]);
                        }
                        if(i==get_rawData.currentA[0].startposition_last||i==get_rawData.currentA[0].endposition_last){
                            var temp="{x:"+i+",y:"+lastvalue+",marker:{enabled:true,fillColor:'green',radius:5}}";
                            lastCurrentA.push(eval('(' + temp + ')'));
                        }else{
                            lastCurrentA.push([i,lastvalue]);
                        }
                    }else{
                        if(i<get_rawData.currentA[0].startposition){
                            lastvalue=null;
                        }else{
                            lastvalue=isNaN(laseCurrentAArr[i-get_rawData.currentA[0].startposition])?null:parseFloat(laseCurrentAArr[i-get_rawData.currentA[0].startposition]);
                        }
                        if(i==get_rawData.currentA[0].startposition||i==get_rawData.currentA[0].endposition){
                            var temp="{x:"+i+",y:"+lastvalue+",marker:{enabled:true,fillColor:'green',radius:5}}";
                            lastCurrentA.push(eval('(' + temp + ')'));
                        }else{
                            lastCurrentA.push([i,lastvalue]);
                        }
                    }
                }else {
                    lastCurrentA.push([i,lastvalue]);
                }
            }
        }
        
        for(var i=1;i<=currentBlength;i++){
            var newvalue=null;
            var lastvalue=null;
            if(get_rawData.totals==1){
            	newvalue=isNaN(newCurrentBArr[i-1])?null:parseFloat(newCurrentBArr[i-1]);
                newCurrentB.push([i,newvalue]);
            }else{
            	if(!((get_rawData.currentB[0].startposition==1&&i<get_rawData.currentB[0].startposition_last)||(get_rawData.currentB[0].startposition>1&&i>get_rawData.currentB[0].endposition))){
                    if(get_rawData.currentB[0].startposition==1){
                        if(i<get_rawData.currentB[0].startposition_last){
                            newvalue=null
                        }else{
                            newvalue=isNaN(newCurrentBArr[i-get_rawData.currentB[0].startposition_last])?null:parseFloat(newCurrentBArr[i-get_rawData.currentB[0].startposition_last]);
                        }
                        if(i==get_rawData.currentB[0].startposition_last||i==get_rawData.currentB[0].endposition_last){
                            var temp="{x:"+i+",y:"+newvalue+",marker:{enabled:true,fillColor:'red',radius:5}}";
                            newCurrentB.push(eval('(' + temp + ')'));
                        }else{
                            newCurrentB.push([i,newvalue]);
                        }
                    }else{
                        if(i>get_rawData.currentB[0].endposition){
                           newvalue=null 
                        }else{
                            newvalue=isNaN(newCurrentBArr[i-1])?null:parseFloat(newCurrentBArr[i-1]);
                        }
                        if(i==get_rawData.currentB[0].startposition||i==get_rawData.currentB[0].endposition){
                            var temp="{x:"+i+",y:"+newvalue+",marker:{enabled:true,fillColor:'red',radius:5}}";
                            newCurrentB.push(eval('(' + temp + ')'));
                        }else{
                            newCurrentB.push([i,newvalue]);
                        }
                    }
                }else{
                    newCurrentB.push([i,newvalue]);
                }
            }
            
            
            if(get_rawData.totals==2){
                if(!((get_rawData.currentB[0].startposition==1&&i>get_rawData.currentB[0].endposition_last)||(get_rawData.currentB[0].startposition>1&&i<get_rawData.currentB[0].startposition))){
                    if(get_rawData.currentB[0].startposition==1){
                        if(i>get_rawData.currentB[0].endposition_last){
                            lastvalue=null;
                        }else{
                            lastvalue=isNaN(laseCurrentBArr[i-1])?null:parseFloat(laseCurrentBArr[i-1]);
                        }
                        if(i==get_rawData.currentB[0].startposition_last||i==get_rawData.currentB[0].endposition_last){
                            var temp="{x:"+i+",y:"+lastvalue+",marker:{enabled:true,fillColor:'green',radius:5}}";
                            lastCurrentB.push(eval('(' + temp + ')'));
                        }else{
                            lastCurrentB.push([i,lastvalue]);
                        }
                    }else{
                        if(i<get_rawData.currentB[0].startposition){
                            lastvalue=null;
                        }else{
                            lastvalue=isNaN(laseCurrentBArr[i-get_rawData.currentB[0].startposition])?null:parseFloat(laseCurrentBArr[i-get_rawData.currentB[0].startposition]);
                        }
                        if(i==get_rawData.currentB[0].startposition||i==get_rawData.currentB[0].endposition){
                            var temp="{x:"+i+",y:"+lastvalue+",marker:{enabled:true,fillColor:'green',radius:5}}";
                            lastCurrentB.push(eval('(' + temp + ')'));
                        }else{
                            lastCurrentB.push([i,lastvalue]);
                        }
                    }
                }else {
                    lastCurrentB.push([i,lastvalue]);
                }
            }
        }
        
        for(var i=1;i<=currentClength;i++){
            var newvalue=null;
            var lastvalue=null;
            if(get_rawData.totals==1){
            	newvalue=isNaN(newCurrentCArr[i-1])?null:parseFloat(newCurrentCArr[i-1]);
                newCurrentC.push([i,newvalue]);
            }else{
            	if(!((get_rawData.currentC[0].startposition==1&&i<get_rawData.currentC[0].startposition_last)||(get_rawData.currentC[0].startposition>1&&i>get_rawData.currentC[0].endposition))){
                    if(get_rawData.currentC[0].startposition==1){
                        if(i<get_rawData.currentC[0].startposition_last){
                            newvalue=null
                        }else{
                            newvalue=isNaN(newCurrentCArr[i-get_rawData.currentC[0].startposition_last])?null:parseFloat(newCurrentCArr[i-get_rawData.currentC[0].startposition_last]);
                        }
                        if(i==get_rawData.currentC[0].startposition_last||i==get_rawData.currentC[0].endposition_last){
                            var temp="{x:"+i+",y:"+newvalue+",marker:{enabled:true,fillColor:'red',radius:5}}";
                            newCurrentC.push(eval('(' + temp + ')'));
                        }else{
                            newCurrentC.push([i,newvalue]);
                        }
                    }else{
                        if(i>get_rawData.currentC[0].endposition){
                           newvalue=null 
                        }else{
                            newvalue=isNaN(newCurrentCArr[i-1])?null:parseFloat(newCurrentCArr[i-1]);
                        }
                        if(i==get_rawData.currentC[0].startposition||i==get_rawData.currentC[0].endposition){
                            var temp="{x:"+i+",y:"+newvalue+",marker:{enabled:true,fillColor:'red',radius:5}}";
                            newCurrentC.push(eval('(' + temp + ')'));
                        }else{
                            newCurrentC.push([i,newvalue]);
                        }
                    }
                }else{
                    newCurrentC.push([i,newvalue]);
                }
            }
            
            if(get_rawData.totals==2){
                if(!((get_rawData.currentC[0].startposition==1&&i>get_rawData.currentC[0].endposition_last)||(get_rawData.currentC[0].startposition>1&&i<get_rawData.currentC[0].startposition))){
                    if(get_rawData.currentC[0].startposition==1){
                        if(i>get_rawData.currentC[0].endposition_last){
                            lastvalue=null;
                        }else{
                            lastvalue=isNaN(laseCurrentCArr[i-1])?null:parseFloat(laseCurrentCArr[i-1]);
                        }
                        if(i==get_rawData.currentC[0].startposition_last||i==get_rawData.currentC[0].endposition_last){
                            var temp="{x:"+i+",y:"+lastvalue+",marker:{enabled:true,fillColor:'green',radius:5}}";
                            lastCurrentC.push(eval('(' + temp + ')'));
                        }else{
                            lastCurrentC.push([i,lastvalue]);
                        }
                    }else{
                        if(i<get_rawData.currentC[0].startposition){
                            lastvalue=null;
                        }else{
                            lastvalue=isNaN(laseCurrentCArr[i-get_rawData.currentC[0].startposition])?null:parseFloat(laseCurrentCArr[i-get_rawData.currentC[0].startposition]);
                        }
                        if(i==get_rawData.currentC[0].startposition||i==get_rawData.currentC[0].endposition){
                            var temp="{x:"+i+",y:"+lastvalue+",marker:{enabled:true,fillColor:'green',radius:5}}";
                            lastCurrentC.push(eval('(' + temp + ')'));
                        }else{
                            lastCurrentC.push([i,lastvalue]);
                        }
                    }
                }else{
                    lastCurrentC.push([i,lastvalue]);
                }
            }
        }
        
        for(var i=1;i<=voltageAlength;i++){
            var newvalue=null;
            var lastvalue=null;
            if(get_rawData.totals==1){
            	newvalue=isNaN(newVoltageAArr[i-1])?null:parseFloat(newVoltageAArr[i-1]);
                newVoltageA.push([i,newvalue]);
            }else{
            	if(!((get_rawData.voltageA[0].startposition==1&&i<get_rawData.voltageA[0].startposition_last)||(get_rawData.voltageA[0].startposition>1&&i>get_rawData.voltageA[0].endposition))){
                    if(get_rawData.voltageA[0].startposition==1){
                        if(i<get_rawData.voltageA[0].startposition_last){
                            newvalue=null
                        }else{
                            newvalue=isNaN(newVoltageAArr[i-get_rawData.voltageA[0].startposition_last])?null:parseFloat(newVoltageAArr[i-get_rawData.voltageA[0].startposition_last]);
                        }
                        if(i==get_rawData.voltageA[0].startposition_last||i==get_rawData.voltageA[0].endposition_last){
                            var temp="{x:"+i+",y:"+newvalue+",marker:{enabled:true,fillColor:'red',radius:5}}";
                            newVoltageA.push(eval('(' + temp + ')'));
                        }else{
                            newVoltageA.push([i,newvalue]);
                        }
                    }else{
                        if(i>get_rawData.voltageA[0].endposition){
                           newvalue=null 
                        }else{
                            newvalue=isNaN(newVoltageAArr[i-1])?null:parseFloat(newVoltageAArr[i-1]);
                        }
                        if(i==get_rawData.voltageA[0].startposition||i==get_rawData.voltageA[0].endposition){
                            var temp="{x:"+i+",y:"+newvalue+",marker:{enabled:true,fillColor:'red',radius:5}}";
                            newVoltageA.push(eval('(' + temp + ')'));
                        }else{
                            newVoltageA.push([i,newvalue]);
                        }
                    }
                }else{
                    newVoltageA.push([i,newvalue]);
                }
            }
            
            if(get_rawData.totals==2){
                if(!((get_rawData.voltageA[0].startposition==1&&i>get_rawData.voltageA[0].endposition_last)||(get_rawData.voltageA[0].startposition>1&&i<get_rawData.voltageA[0].startposition))){
                    if(get_rawData.voltageA[0].startposition==1){
                        if(i>get_rawData.voltageA[0].endposition_last){
                            lastvalue=null;
                        }else{
                            lastvalue=isNaN(laseVoltageAArr[i-1])?null:parseFloat(laseVoltageAArr[i-1]);
                        }
                        if(i==get_rawData.voltageA[0].startposition_last||i==get_rawData.voltageA[0].endposition_last){
                            var temp="{x:"+i+",y:"+lastvalue+",marker:{enabled:true,fillColor:'green',radius:5}}";
                            lastVoltageA.push(eval('(' + temp + ')'));
                        }else{
                            lastVoltageA.push([i,lastvalue]);
                        }
                    }else{
                        if(i<get_rawData.voltageA[0].startposition){
                            lastvalue=null;
                        }else{
                            lastvalue=isNaN(laseVoltageAArr[i-get_rawData.voltageA[0].startposition])?null:parseFloat(laseVoltageAArr[i-get_rawData.voltageA[0].startposition]);
                        }
                        if(i==get_rawData.voltageA[0].startposition||i==get_rawData.voltageA[0].endposition){
                            var temp="{x:"+i+",y:"+lastvalue+",marker:{enabled:true,fillColor:'green',radius:5}}";
                            lastVoltageA.push(eval('(' + temp + ')'));
                        }else{
                            lastVoltageA.push([i,lastvalue]);
                        }
                    }
                }else{
                    lastVoltageA.push([i,lastvalue]);
                }
            }
        }
        
        for(var i=1;i<=voltageBlength;i++){
            var newvalue=null;
            var lastvalue=null;
            if(get_rawData.totals==1){
            	newvalue=isNaN(newVoltageBArr[i-1])?null:parseFloat(newVoltageBArr[i-1]);
                newVoltageB.push([i,newvalue]);
            }else{
            	if(!((get_rawData.voltageB[0].startposition==1&&i<get_rawData.voltageB[0].startposition_last)||(get_rawData.voltageB[0].startposition>1&&i>get_rawData.voltageB[0].endposition))){
                    if(get_rawData.voltageB[0].startposition==1){
                        if(i<get_rawData.voltageB[0].startposition_last){
                            newvalue=null
                        }else{
                            newvalue=isNaN(newVoltageBArr[i-get_rawData.voltageB[0].startposition_last])?null:parseFloat(newVoltageBArr[i-get_rawData.voltageB[0].startposition_last]);
                        }
                        if(i==get_rawData.voltageB[0].startposition_last||i==get_rawData.voltageB[0].endposition_last){
                            var temp="{x:"+i+",y:"+newvalue+",marker:{enabled:true,fillColor:'red',radius:5}}";
                            newVoltageB.push(eval('(' + temp + ')'));
                        }else{
                            newVoltageB.push([i,newvalue]);
                        }
                    }else{
                        if(i>get_rawData.voltageB[0].endposition){
                           newvalue=null 
                        }else{
                            newvalue=isNaN(newVoltageBArr[i-1])?null:parseFloat(newVoltageBArr[i-1]);
                        }
                        if(i==get_rawData.voltageB[0].startposition||i==get_rawData.voltageB[0].endposition){
                            var temp="{x:"+i+",y:"+newvalue+",marker:{enabled:true,fillColor:'red',radius:5}}";
                            newVoltageB.push(eval('(' + temp + ')'));
                        }else{
                            newVoltageB.push([i,newvalue]);
                        }
                    }
                }else{
                    newVoltageB.push([i,newvalue]);
                }
            }
            
            if(get_rawData.totals==2){
                if(!((get_rawData.voltageB[0].startposition==1&&i>get_rawData.voltageB[0].endposition_last)||(get_rawData.voltageB[0].startposition>1&&i<get_rawData.voltageB[0].startposition))){
                    if(get_rawData.voltageB[0].startposition==1){
                        if(i>get_rawData.voltageB[0].endposition_last){
                            lastvalue=null;
                        }else{
                            lastvalue=isNaN(laseVoltageBArr[i-1])?null:parseFloat(laseVoltageBArr[i-1]);
                        }
                        if(i==get_rawData.voltageB[0].startposition_last||i==get_rawData.voltageB[0].endposition_last){
                            var temp="{x:"+i+",y:"+lastvalue+",marker:{enabled:true,fillColor:'green',radius:5}}";
                            lastVoltageB.push(eval('(' + temp + ')'));
                        }else{
                            lastVoltageB.push([i,lastvalue]);
                        }
                    }else{
                        if(i<get_rawData.voltageB[0].startposition){
                            lastvalue=null;
                        }else{
                            lastvalue=isNaN(laseVoltageBArr[i-get_rawData.voltageB[0].startposition])?null:parseFloat(laseVoltageBArr[i-get_rawData.voltageB[0].startposition]);
                        }
                        if(i==get_rawData.voltageB[0].startposition||i==get_rawData.voltageB[0].endposition){
                            var temp="{x:"+i+",y:"+lastvalue+",marker:{enabled:true,fillColor:'green',radius:5}}";
                            lastVoltageB.push(eval('(' + temp + ')'));
                        }else{
                            lastVoltageB.push([i,lastvalue]);
                        }
                    }
                }else{
                    lastVoltageB.push([i,lastvalue]);
                }
            }
        }
        
        for(var i=1;i<=voltageClength;i++){
            var newvalue=null;
            var lastvalue=null;
            if(get_rawData.totals==1){
            	newvalue=isNaN(newVoltageCArr[i-1])?null:parseFloat(newVoltageCArr[i-1]);
                newVoltageC.push([i,newvalue]);
            }else{
            	if(!((get_rawData.voltageC[0].startposition==1&&i<get_rawData.voltageC[0].startposition_last)||(get_rawData.voltageC[0].startposition>1&&i>get_rawData.voltageC[0].endposition))){
                    if(get_rawData.voltageC[0].startposition==1){
                        if(i<get_rawData.voltageC[0].startposition_last){
                            newvalue=null
                        }else{
                            newvalue=isNaN(newVoltageCArr[i-get_rawData.voltageC[0].startposition_last])?null:parseFloat(newVoltageCArr[i-get_rawData.voltageC[0].startposition_last]);
                        }
                        if(i==get_rawData.voltageC[0].startposition_last||i==get_rawData.voltageC[0].endposition_last){
                            var temp="{x:"+i+",y:"+newvalue+",marker:{enabled:true,fillColor:'red',radius:5}}";
                            newVoltageC.push(eval('(' + temp + ')'));
                        }else{
                            newVoltageC.push([i,newvalue]);
                        }
                    }else{
                        if(i>get_rawData.voltageC[0].endposition){
                           newvalue=null 
                        }else{
                            newvalue=isNaN(newVoltageCArr[i-1])?null:parseFloat(newVoltageCArr[i-1]);
                        }
                        if(i==get_rawData.voltageC[0].startposition||i==get_rawData.voltageC[0].endposition){
                            var temp="{x:"+i+",y:"+newvalue+",marker:{enabled:true,fillColor:'red',radius:5}}";
                            newVoltageC.push(eval('(' + temp + ')'));
                        }else{
                            newVoltageC.push([i,newvalue]);
                        }
                    }
                }else{
                    newVoltageC.push([i,newvalue]);
                }
            }
            
            if(get_rawData.totals==2){
                if(!((get_rawData.voltageC[0].startposition==1&&i>get_rawData.voltageC[0].endposition_last)||(get_rawData.voltageC[0].startposition>1&&i<get_rawData.voltageC[0].startposition))){
                    if(get_rawData.voltageC[0].startposition==1){
                        if(i>get_rawData.voltageC[0].endposition_last){
                            lastvalue=null;
                        }else{
                            lastvalue=isNaN(laseVoltageCArr[i-1])?null:parseFloat(laseVoltageCArr[i-1]);
                        }
                        if(i==get_rawData.voltageC[0].startposition_last||i==get_rawData.voltageC[0].endposition_last){
                            var temp="{x:"+i+",y:"+lastvalue+",marker:{enabled:true,fillColor:'green',radius:5}}";
                            lastVoltageC.push(eval('(' + temp + ')'));
                        }else{
                            lastVoltageC.push([i,lastvalue]);
                        }
                    }else{
                        if(i<get_rawData.voltageC[0].startposition){
                            lastvalue=null;
                        }else{
                            lastvalue=isNaN(laseVoltageCArr[i-get_rawData.voltageC[0].startposition])?null:parseFloat(laseVoltageCArr[i-get_rawData.voltageC[0].startposition]);
                        }
                        if(i==get_rawData.voltageC[0].startposition||i==get_rawData.voltageC[0].endposition){
                            var temp="{x:"+i+",y:"+lastvalue+",marker:{enabled:true,fillColor:'green',radius:5}}";
                            lastVoltageC.push(eval('(' + temp + ')'));
                        }else{
                            lastVoltageC.push([i,lastvalue]);
                        }
                    }
                }else{
                    lastVoltageC.push([i,lastvalue]);
                }
            }
        }
    }
    
	initElectricOverlayAnalysisCurve(newCurrentA,lastCurrentA,newCurrentB, lastCurrentB,newCurrentC,lastCurrentC,newVoltageA,lastVoltageA,newVoltageB,lastVoltageB,newVoltageC,lastVoltageC,divid,"抽油机电参叠加曲线");
	return false;
}

/* 
 * 拼接螺杆泵电参相关系数曲线数据信息
 */
showElectricOverlayAnalysisCurveLgb = function(store, divid) {
	var get_rawData=store.proxy.reader.rawData;
	var newCurrentA = [],
	lastCurrentA = [],
	newCurrentB = [],
	lastCurrentB = [],
	newCurrentC = [],
	lastCurrentC = [],
	newVoltageA = [],
	lastVoltageA = [],
	newVoltageB = [],
	lastVoltageB = [],
	newVoltageC = [],
	lastVoltageC = [];
	var newCurrentAArr=null,laseCurrentAArr=null,newCurrentBArr=null,laseCurrentBArr=null,newCurrentCArr=null,laseCurrentCArr=null,
    newVoltageAArr=null,laseVoltageAArr=null,newVoltageBArr=null,laseVoltageBArr=null,newVoltageCArr=null,laseVoltageCArr=null;
	if(get_rawData.totals>0&&get_rawData.currentA.length>0&&get_rawData.currentB.length>0&&get_rawData.currentC.length>0&&get_rawData.voltageA.length>0&&get_rawData.voltageB.length>0&&get_rawData.voltageC.length>0){
		newCurrentAArr=get_rawData.currentA[0].curvedata.split(",");
        if(get_rawData.totals==2)
            laseCurrentAArr=get_rawData.currentA[1].curvedata.split(",");
        
        newCurrentBArr=get_rawData.currentB[0].curvedata.split(",");
        if(get_rawData.totals==2)
            laseCurrentBArr=get_rawData.currentB[1].curvedata.split(",");
        
        newCurrentCArr=get_rawData.currentC[0].curvedata.split(",");
        if(get_rawData.totals==2)
            laseCurrentCArr=get_rawData.currentC[1].curvedata.split(",");
        
        newVoltageAArr=get_rawData.voltageA[0].curvedata.split(",");
        if(get_rawData.totals==2)
            laseVoltageAArr=get_rawData.voltageA[1].curvedata.split(",");
        
        newVoltageBArr=get_rawData.voltageB[0].curvedata.split(",");
        if(get_rawData.totals==2)
            laseVoltageBArr=get_rawData.voltageB[1].curvedata.split(",");
        
        newVoltageCArr=get_rawData.voltageC[0].curvedata.split(",");
        if(get_rawData.totals==2)
            laseVoltageCArr=get_rawData.voltageC[1].curvedata.split(",");
        for(var i=1;i<=newCurrentAArr.length;i++){
        	var newvalue=isNaN(newVoltageCArr[i-1])?null:parseFloat(parseFloat(newVoltageCArr[i-1]).toFixed(2));
        	newCurrentA.push([i,newvalue]);
        }
        for(var i=1;i<=laseCurrentAArr.length;i++){
        	var newvalue=isNaN(laseCurrentAArr[i-1])?null:parseFloat(parseFloat(laseCurrentAArr[i-1]).toFixed(2));
        	lastCurrentA.push([i,newvalue]);
        }
        
        for(var i=1;i<=newCurrentBArr.length;i++){
        	var value=isNaN(newCurrentBArr[i-1])?null:parseFloat(parseFloat(newCurrentBArr[i-1]).toFixed(2));
        	newCurrentB.push([i,value]);
        }
        for(var i=1;i<=laseCurrentBArr.length;i++){
        	var value=isNaN(laseCurrentBArr[i-1])?null:parseFloat(parseFloat(laseCurrentBArr[i-1]).toFixed(2));
        	lastCurrentB.push([i,value]);
        }
        
        for(var i=1;i<=newCurrentCArr.length;i++){
        	var value=isNaN(newCurrentCArr[i-1])?null:parseFloat(parseFloat(newCurrentCArr[i-1]).toFixed(2));
        	newCurrentC.push([i,value]);
        }
        for(var i=1;i<=laseCurrentCArr.length;i++){
        	var value=isNaN(laseCurrentCArr[i-1])?null:parseFloat(parseFloat(laseCurrentCArr[i-1]).toFixed(2));
        	lastCurrentC.push([i,value]);
        }
        
        for(var i=1;i<=newVoltageAArr.length;i++){
        	var value=isNaN(newVoltageAArr[i-1])?null:parseFloat(parseFloat(newVoltageAArr[i-1]).toFixed(2));
        	newVoltageA.push([i,value]);
        }
        for(var i=1;i<=laseVoltageAArr.length;i++){
        	var value=isNaN(laseVoltageAArr[i-1])?null:parseFloat(parseFloat(laseVoltageAArr[i-1]).toFixed(2));
        	lastVoltageA.push([i,value]);
        }
        
        for(var i=1;i<=newVoltageBArr.length;i++){
        	var value=isNaN(newVoltageBArr[i-1])?null:parseFloat(parseFloat(newVoltageBArr[i-1]).toFixed(2));
        	newVoltageB.push([i,value]);
        }
        for(var i=1;i<=laseVoltageBArr.length;i++){
        	var value=isNaN(laseVoltageBArr[i-1])?null:parseFloat(parseFloat(laseVoltageBArr[i-1]).toFixed(2));
        	lastVoltageB.push([i,value]);
        }
        
        for(var i=1;i<=newVoltageCArr.length;i++){
        	var value=isNaN(newVoltageCArr[i-1])?null:parseFloat(parseFloat(newVoltageCArr[i-1]).toFixed(2));
        	newVoltageC.push([i,value]);
        }
        for(var i=1;i<=laseVoltageCArr.length;i++){
        	var value=isNaN(laseVoltageCArr[i-1])?null:parseFloat(parseFloat(laseVoltageCArr[i-1]).toFixed(2));
        	lastVoltageC.push([i,value]);
        }
	}
    
	initElectricOverlayAnalysisCurve(newCurrentA,lastCurrentA,newCurrentB, lastCurrentB,newCurrentC,lastCurrentC,newVoltageA,lastVoltageA,newVoltageB,lastVoltageB,newVoltageC,lastVoltageC,divid,"螺杆泵电参叠加曲线");
	return false;
}


function initElectricOverlayAnalysisCurve(newCurrentA,lastCurrentA,newCurrentB, lastCurrentB,newCurrentC,lastCurrentC,newVoltageA,lastVoltageA,newVoltageB,lastVoltageB,newVoltageC,lastVoltageC,divid,title) {
	Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
	mychart = new Highcharts.StockChart({
		chart: {
            renderTo : divid,
            //marginLeft: 50,
        }, 
        navigator:{
        	enabled:false
        	//hidden:true
        },
        scrollbar:{
            enabled:false  
         },
//        exporting: {    
//            enabled: false //是否能导出趋势图图片  
//        },
         exporting:{    
             enabled:true,    
             filename:'class-booking-chart',    
             url:context + '/exportHighcharsPicController/export'
        },
        title: {
            text: title
        },
        tooltip:{  
            // 日期时间格式化  
            xDateFormat: '点数:%L',
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.2f}</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        credits: {
            enabled: false
        },
        plotOptions:{
        	series:{
        		dataGrouping:{
        			groupPixelWidth:0.1
        		}
        	}
        },
        xAxis: {  
            //tickPixelInterval: 200,//x轴上的间隔  
            type: 'datetime', //定义x轴上日期的显示格式  
            labels: {  
            formatter: function() {  
                var vDate=new Date(this.value);  
                return vDate.getMilliseconds(); 
                },  
            align: 'center'  
            }
        },
        yAxis: [{
        	opposite:false,
            labels: {
                align: 'left',
                x: 0
            },
            title: {
                text: 'A相电流'
            },
            endOnTick: false,
            min:0,
            height: '15%',
            lineWidth: 1
        },{
        	opposite:false,
            labels: {
                align: 'left',
                x: 0
            },
            title: {
                text: 'B相电流'
            },
            min:0,
            top: '17%',
            height: '15%',
            offset: 0,
            lineWidth: 1
        }, {
        	opposite:false,
            labels: {
                align: 'left',
                x: 0
            },
            title: {
                text: 'C相电流'
            },
            //max:100,
            min:0,
            top: '34%',
            height: '15%',
            offset: 0,
            lineWidth: 1
        }, {
        	opposite:false,
            labels: {
                align: 'left',
                x: 0
            },
            title: {
                text: 'A相电压'
            },
            //max:100,
            min:0,
            top: '51%',
            height: '15%',
            offset: 0,
            lineWidth: 1
        }, {
        	opposite:false,
            labels: {
                align: 'left',
                x: 0
            },
            title: {
                text: 'B相电压'
            },
            //max:100,
            min:0,
            top: '68%',
            height: '15%',
            offset: 0,
            lineWidth: 1
        }, {
        	opposite:false,
            labels: {
                align: 'left',
                x: 0
            },
            title: {
                text: 'C相电压'
            },
            //max:100,
            min:0,
            top: '85%',
            height: '15%',
            offset: 0,
            lineWidth: 1
        }],
        rangeSelector: {  
            enabled:false
        },  
        series: [{
            	type: 'line',
            	name: '当前A相电流',
            	data: newCurrentA,
            	id:'newCurrentA',
//            	marker:{
//            		enabled:true,
//            		radius: 3
//            	},
            	yAxis: 0
            },{
            	type: 'line',
            	name: '上次A相电流',
            	data: lastCurrentA,
            	
//            	marker:{
//            		enabled:true,
//            		radius: 3
//            	},
            	yAxis: 0
            }
            ,{
            	type: 'line',
            	name: '当前B相电流',
            	data: newCurrentB,
            	yAxis: 1
            }
            ,{
            	type: 'line',
            	name: '上次B相电流',
            	data: lastCurrentB,
            	yAxis: 1
            }
            ,{
            	type: 'line',
            	name: '当前C相电流',
            	data: newCurrentC,
            	yAxis: 2
            },{
            	type: 'line',
            	name: '上次C相电流',
            	data: lastCurrentC,
            	yAxis: 2
            }
            ,{
            	type: 'line',
            	name: '当前A相电压',
            	data: newVoltageA,
            	yAxis: 3
            },{
            	type: 'line',
            	name: '上次A相电压',
            	data: lastVoltageA,
            	yAxis: 3
            }
            ,{
            	type: 'line',
            	name: '当前B相电压',
            	data: newVoltageB,
            	yAxis: 4
            },{
            	type: 'line',
            	name: '上次B相电压',
            	data: lastVoltageB,
            	yAxis: 4
            }
            ,{
            	type: 'line',
            	name: '当前C相电压',
            	data: newVoltageC,
            	yAxis: 5
            },{
            	type: 'line',
            	name: '上次C相电压',
            	data: lastVoltageC,
            	yAxis: 5
            }
            ]
	});
}

function ShowWorkStatusColChart(title_, categories_, values_) {
	$('#WorkStatusStatisGraphColDiv_Id').highcharts({
		chart : {
			type : 'column'
		},
		credits : {
			enabled : false
		},
		title : {
			text : title_,
			font : 'normal 25px Verdana, sans-serif'
		},
		xAxis : {
			categories : categories_,
			labels : {
				style : {
					color : '#000000',
					margin_top : '4px',
					fontWeight : 'bold'
				}
			}
		},
		yAxis : {
			min : 0,
			title : {
				text : cosog.string.wellNums
			}
		},
		colors : ['#058DC7', '#50B432', '#ED561B', '#24CBE5', '#64E572',
				'#FF9655', '#FFF263', '#6AF9C4', '#DDDF00'],
		tooltip : {
			headerFormat : '<span style="font-size:8px">{point.key}</span><table>',
			pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
					+ '<td style="padding:0"><b>{point.y}</b></td></tr>',
			footerFormat : '</table>',
			shared : true,
			useHTML : true
		},
		plotOptions : {

			column : {
				pointPadding : 0.2,
				allowPointSelect : true,
				borderWidth : 0,
				dataLabels : {
					enabled : true,
					padding : 0.5
				}
			}
		},
		exporting:{    
            enabled:true,    
            filename:'class-booking-chart',    
            url:context + '/exportHighcharsPicController/export'
       },
		series : values_
	});

};

//自定义地图前台数据 type:平衡状态或诊断算产状态  1-平衡 2-诊断算产
function getCustomMapFrontDataAndShowMap(type,divId) {
	var orgId= Ext.getCmp("leftOrg_Id").getValue();
    // AJAX提交方式
    Ext.Ajax.request({
    	url: context + '/mapDataController/getCustomMapFrontData',
        method: "POST",
        params: {
        	orgId:orgId,
        	type: type
        },
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
    		var imageUrl=context+'/scripts/customMap/images/map.svg';
    		//将div内容清空
    		$("#"+divId).html('');
//    		global.wellMap.imgX = 0;
//    		global.wellMap.imgY = 0;
        	global.wellMap.init(imageUrl,'#'+divId,result);

        },
        failure: function () {
            Ext.Msg.alert("提示", "【<font color=red>获取地图数据失败 </font>】");
        }
    });
}

//井信息自定义地图数据
function getWellMapDataAndShowMap(divId) {
    // AJAX提交方式
    Ext.Ajax.request({
    	url: context + '/mapDataController/getCustomMapWellInfoData',
        method: "POST",
        params: {
        },
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
    		var imageUrl=context+'/scripts/customMap/images/map.svg';
    		//将div内容清空
    		$("#"+divId).html('');
        	global.wellMap.init(imageUrl,'#'+divId,result);

        },
        failure: function () {
            Ext.Msg.alert("提示", "【<font color=red>获取地图数据失败 </font>】");
        }
    });
}

//更新地图井列表
function updateWellMapDataAndShowMap() {
    // AJAX提交方式
    Ext.Ajax.request({
    	url: context + '/mapDataController/getCustomMapWellInfoData',
        method: "POST",
        params: {
        },
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            global.wellMap.updateWellData(result);

        },
        failure: function () {
            Ext.Msg.alert("提示", "【<font color=red>获取地图数据失败 </font>】");
        }
    });
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
/* 
 * 拼接华北油田通信公司光杆功图曲线数据
 */
showHYTXSurfaceCard = function(selectedData, divid) {
    var gt=selectedData.load_data.split(";");
	var data = "["; // 功图data
	if(gt.length>0){
		for (var i=0;i<gt.length-1;i++) {
			var point=gt[i].split(",")
			data += "[" + point[0] + ","+point[1]+"],";
			
		}
	}
	data+="]";
	var pointdata = Ext.JSON.decode(data);
	initHYTXSurfaceCardChart(pointdata, selectedData, divid);
	return false;
};

//平衡度曲线
DegreeOfBalanceCurveChartFn = function(data, divId,wellName,compositeBalance,title) {
	var tickInterval = 1;
	tickInterval = Math.floor(data.length / 10) + 1;
	var catagories = "[";
	for (var i = 0; i < data.length; i++) {
		catagories += "\"" + data[i].cjsj + "\"";
		if (i < data.length - 1) {
			catagories += ",";
		}
	}
	catagories += "]";
	var legendName = ['平衡度(%)'];
	var series = "[";
	for (var i = 0; i < legendName.length; i++) {
		series += "{\"name\":\"" + legendName[i] + "\",";
		if (i == 2) {
			series += "\"yAxis\":1,";
		}
		series += "\"data\":[";
		for (var j = 0; j < data.length; j++) {
			var year=parseInt(data[j].cjsj.split(" ")[0].split("-")[0]);
			var month=parseInt(data[j].cjsj.split(" ")[0].split("-")[1]);
			var day=parseInt(data[j].cjsj.split(" ")[0].split("-")[2]);
			
			var hour=parseInt(data[j].cjsj.split(" ")[1].split(":")[0]);
			var minute=parseInt(data[j].cjsj.split(" ")[1].split(":")[1]);
			var second=parseInt(data[j].cjsj.split(" ")[1].split(":")[2]);
			
			if (i == 0) {
				series +="["+Date.UTC(year,month-1,day,hour,minute,second)+","+ data[j].balance+"]";
			} else if (i == 1) {
				series += data[j].zrcyl1;
			}
			if (j != data.length - 1) {
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
	var color=['#800000', // 红
	      '#008C00', // 绿
	      '#000000', // 黑
	      '#0000FF', // 蓝
	      '#F4BD82', // 黄
	      '#FF00FF' // 紫
	    ];
	initSingleYCurveChartFn(cat, ser, tickInterval, divId, title,wellName+"[综合平衡度:"+compositeBalance+"]", "平衡度(%)", "时间",color)
	return false;
}


function initSingleYCurveChartFn(catagories, series, tickInterval, divId, title,subtitle, ytitle, xtitle,color) {
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
				subtitle: {
		        	text: subtitle                                                     
		        },
				colors:color,
				xAxis : {
//					categories : catagories,
					type:'datetime',
//					tickInterval : 5,
					title : {
						text : xtitle
					},
					labels: {
                        formatter: function () {
                            return Highcharts.dateFormat("%Y-%m-%d",this.value);
                        },  
                        rotation:0,//倾斜度，防止数量过多显示不全  
                        step:2
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
									return Highcharts.numberFormat(this.value,2);
								}
							},
							plotLines : [{
								color: '#d12',
			                    dashStyle: 'Dash', //Dash,Dot,Solid,默认Solid
			                    label: {
			                        text: '100%',
			                        align: 'right',
			                        x: -10
			                    },
			                    width: 3,
			                    value: 100,  //y轴显示位置
			                    zIndex: 10
							}]
						}],
						tooltip: {
							crosshairs : true,//十字准线
							style : {
								color : '#333333',
								fontSize : '12px',
								padding : '8px'
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
				exporting:{    
                    enabled:true,    
                    filename:'class-booking-chart',    
                    url:context + '/exportHighcharsPicController/export'
               },
				plotOptions : {
		            series: {
		                allowPointSelect: true
		            },
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
				            shadow: true,//阴影
				            events:{
				            	click: function(e) {
				            		if(e.point.state=="hover"){
				            			
				            		}
				            		var gridId,startDateId,endDateId,textfieldId="";
				            		var tabPanel = Ext.getCmp("BalanceCycleTab_Id");
	                    			var activeId = tabPanel.getActiveTab().id;
	                    			if(activeId=="CycleTorqueMaxValue_Id"){
	                    				gridId="TorqueMaxValueCycleWellListGrid_Id";
	                    				startDateId="TorqueMaxValueCycleStartDate_Id";
	                    				endDateId="TorqueMaxValueCycleEndDate_Id";
	                    				textfieldId="TorqueMaxValueCycleTextfield_id";
	                    			}else if(activeId=="CycleTorqueMeanSquareRoot_Id"){
	                    				gridId="TorqueMeanSquareRootCycleWellListGrid_Id";
	                    				startDateId="TorqueMeanSquareRootCycleStartDate_Id";
	                    				endDateId="TorqueMeanSquareRootCycleEndDate_Id";
	                    				textfieldId="TorqueMeanSquareRootCycleTextfield_id";
	                    			}else if(activeId=="CycleTorqueAveragePower_Id"){
	                    				gridId="TorqueAveragePowerCycleWellListGrid_Id";
	                    				startDateId="TorqueAveragePowerCycleStartDate_Id";
	                    				endDateId="TorqueAveragePowerCycleEndDate_Id";
	                    				textfieldId="TorqueAveragePowerCycleTextfield_id";
	                    			}
	                    			
				            		var updateDate=true;
				            		var selectedStartDate='',selectedEndDate='';
				            		var chart = $('#'+divId).highcharts();
				            		selectedPoints = chart.getSelectedPoints();
				            		if(selectedPoints.length>0){//如果有选择的点
				            			if(e.point.index<selectedPoints[0].index){//当此次点击的点小于已选择的最小点时，将该点作为起点
				            				selectedStartDate=e.point.key.split(' ')[0];
				            				selectedEndDate=selectedPoints[selectedPoints.length-1].key.split(' ')[0];
				            			}else if(e.point.index>selectedPoints[selectedPoints.length-1].index){//当此次点击的点大于已选择的最大点时，将该点作为结束点
				            				selectedStartDate=selectedPoints[0].key.split(' ')[0];
				            				selectedEndDate=e.point.key.split(' ')[0];
				            			}else{
				            				if(selectedPoints.length==1){
				            					selectedStartDate=Ext.getCmp(gridId).getSelectionModel().getSelection()[0].data.gtkssj;
				            					selectedEndDate=Ext.getCmp(gridId).getSelectionModel().getSelection()[0].data.gtjssj;
				            				}else if(e.point.index==selectedPoints[0].index){
				            					selectedStartDate=selectedPoints[1].key.split(' ')[0];
				            					selectedEndDate=selectedPoints[selectedPoints.length-1].key.split(' ')[0];
				            				}else if(e.point.index==selectedPoints[selectedPoints.length-1].index){
				            					selectedStartDate=selectedPoints[0].key.split(' ')[0];
				            					selectedEndDate=selectedPoints[selectedPoints.length-2].key.split(' ')[0];
				            				}else{
				            					updateDate=false;
				            				}
				            			}
				            		}else{//当没有选择点时，将该次点击的点设为七点
				            			var selectedDate=e.point.key.split(' ')[0];
				            			selectedStartDate=e.point.key.split(' ')[0];
				            			selectedEndDate=e.point.key.split(' ')[0];
				            		}
				            		if(updateDate){
				            			Ext.getCmp(startDateId).setValue(selectedStartDate);
					            		Ext.getCmp(endDateId).setValue(selectedEndDate);
					            		var dateSpan=Date.parse(selectedEndDate.replace(/-/g, '/'))-Date.parse(selectedStartDate.replace(/-/g, '/'));
	                            		if(dateSpan<0){
	                            			dateSpan=0;
	                            		}
	                            		iDays=Math.floor(dateSpan/(24*60*60*1000));
	                            		Ext.getCmp(textfieldId).setValue(iDays+1);
				            		}
								}
				            }
					 } 
				},
				legend : {
					enabled:false,
					layout : 'vertical',//图例数据项的布局。布局类型： "horizontal" 或 "vertical" 即水平布局和垂直布局 默认是：horizontal.
					align : 'center',//设定图例在图表区中的水平对齐方式，合法值有left，center 和 right,默认为center
					verticalAlign : 'middle',//设定图例在图表区中的垂直对齐方式，合法值有 top，middle 和 bottom,默认为bottom
					borderWidth : 1,
					floating:false  //图例是否浮动，设置浮动后，图例将不占位置
				},
				series : series
			});
}

/* 
 * 拼接光杆功图曲线数据
 */
showSurfaceCardUploadChart = function(result, divid) {
    var acqTime=result.acqTime;
    var stroke=result.stroke;
    var spm=result.spm;
    var wellName=result.wellName;
    var sData=result.S;
    var fData=result.F;
    var wattData=result.Watt;
    var iData=result.I;
    var data = "["; // 功图data
//    var diagramData=result.diagramData.split(","); // 功图数据：功图点数， 冲次，冲程 位移1，载荷1，位移2，载荷2...
//    var diagramPoint=diagramData[2]; // 功图点数
//    for (var i=5; i <= (diagramPoint*2+5); i+=2) {
//    	if(i<(diagramPoint*2+5)){
//    		data += "[" + diagramData[i] + ","+diagramData[i+1]+"],";
//    	}else{
//    		data += "[" + diagramData[5] + ","+diagramData[6]+"]";//将图形的第一个点拼到最后面，使图形闭合
//    	}
//    }
    for(var i=0;i<=sData.length;i++){
    	if(i<sData.length){
    		data += "[" + changeTwoDecimal(sData[i]) + ","+changeTwoDecimal(fData[i])+"],";
    	}else{
    		data += "[" + changeTwoDecimal(sData[0]) + ","+changeTwoDecimal(fData[0])+"]";//将图形的第一个点拼到最后面，使图形闭合
    	}
    }
	data+="]";
	var pointdata = Ext.JSON.decode(data);
	//画功图
	initSurfaceCardUploadChart(wellName,pointdata, acqTime,stroke,spm, divid);
	return false;
}



function initSurfaceCardUploadChart(wellName,pointdata, acqTime,stroke,spm, divid) {
	var xtext='<span style="text-align:center;">'+cosog.string.position+'<br/>';
    xtext+='冲程:' + stroke + 'm 冲次:' + spm + '/min<br/>';
    xtext += '</span>';
    $('#'+divid).highcharts({
				chart: {                                                                             
		            type: 'scatter',     // 散点图   
//		            renderTo : divid,
		            zoomType: 'xy',
		            reflow: true
		        },                                                                                   
		        title: {                                                                             
		            text: cosog.string.FSDiagram  // 光杆功图                        
		        },                                                                                   
		        subtitle: {                                                                          
		        	text:wellName+' ['+acqTime+']'                                               
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
		                text: cosog.string.load   // 载荷（kN） 
                    },
		            allowDecimals: false,    // 刻度值是否为小数
		            minorTickInterval: '',   // 不显示次刻度线
		            min: 0                  // 最小值
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
		                    pointFormat: '{point.x},{point.y}'                                
		                }                                                                            
		            }                                                                                
		        }, 
		        series: [{                                                                           
		            name: '',                                                                  
		            color: '#00ff00',   
		            lineWidth:3,
		            data:  pointdata                                                                                  
		        }]
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
			title='光杆功图叠加';
			ytext='载荷(kN)';
			color=new Array("#000000","#00ff00");
			minValue=0;
		}else if(diagramType===1){//电功图
			yData = list[i].powerCurveData.split(",");
			title= "电功图叠加";
			ytext="有功功率(kW)"
			color=new Array("#000000","#CC0000");
		}else if(diagramType===2){//电流图
			yData = list[i].currentCurveData.split(",");
			title= "电流图叠加";
			ytext="电流(A)"
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
    	initFSDiagramOverlayChart(pointdata, title,ytext,get_rawData.wellName, get_rawData.calculateDate, divid,upperLoadLine,lowerLoadLine,upperlimit,underlimit,strokeMax);
	}else {
		initPSDiagramOverlayChart(pointdata, title,ytext,get_rawData.wellName, get_rawData.calculateDate, divid);
	}
	
	return false;
}

showInverFSDiagramOverlayChart = function(get_rawData,divid,visible,diagramType) {
	var color=new Array("#000000","#00ff00"); // 线条颜色
	var list=get_rawData.totalRoot;
	var visiblestr='';
	if(!visible){
		visiblestr='visible:false,';
	};
	var title='';
	var ytext='';
	var color=new Array("#000000","#00ff00"); // 线条颜色
	var series = "[";
	for (var i =0; i < list.length; i++){
		
		var xData = list[i].positionCurveData.split(",");
		var yData;
		if(diagramType===0){//如果是功图
			yData = list[i].loadCurveData.split(",");
			title='光杆功图叠加';
			ytext='载荷(kN)';
			color=new Array("#000000","#00ff00");
		}else if(diagramType===1){//电功图
			yData = list[i].powerCurveData.split(",");
			title= "电功图叠加";
			ytext="有功功率(kW)"
			color=new Array("#000000","#CC0000");
		}else if(diagramType===2){//电流图
			yData = list[i].currentCurveData.split(",");
			title= "电流图叠加";
			ytext="电流(A)"
			color=new Array("#000000","#0033FF");
		}
		var data = "[";
		for (var j=0; j <= xData.length; j++) {
			if(j<(xData.length)){
				data += "[" + xData[j] + ","+yData[j]+"],";
			}else{
				data += "[" + xData[0] + ","+yData[0]+"]";//将图形的第一个点拼到最后面，使图形闭合
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
	series+="]";
	
	var pointdata = Ext.JSON.decode(series);
	initPSDiagramOverlayChart(pointdata, title,ytext,get_rawData.wellName, get_rawData.calculateDate, divid);
	return false;
}

/* 
 * 拼接叠加功率曲线数据
 * li 2018-07-24
 */
showPSDiagramOverlayChart = function(get_rawData,divid,curveType,visible) {
	var color=new Array("#000000","#00ff00"); // 线条颜色
	var list=get_rawData.totalRoot;
	var title="";
	var ytext="";
	var visiblestr='';
	if(!visible){
		visiblestr='visible:false,';
	};
	if(curveType==1){//功率曲线
		title= "电功图叠加";
		ytext="有功功率(kW)"
		color=new Array("#000000","#CC0000");
	}else{//电流曲线
		title= "电流图叠加";
		ytext="电流(A)"
		color=new Array("#000000","#0033FF");
	}
	var series = "[";
	for (var i =0; i < list.length; i++){
		
		var positionCurveData = list[i].positionCurveData.split(",");
		var yData=null;
		if(curveType==1){//功率曲线
			yData=list[i].powerCurveData.split(",");
		}else{//电流曲线
			yData=list[i].currentCurveData.split(",");
		}
		var data = "[";
		for (var j=0; j <= positionCurveData.length; j++) {
			if(j<positionCurveData.length){
				data += "[" + positionCurveData[j] + ","+yData[j]+"],";
			}else{
				data += "[" + positionCurveData[0] + ","+yData[0]+"]";//将图形的第一个点拼到最后面，使图形闭合
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
	series+="]";
	
	var pointdata = Ext.JSON.decode(series);
	
	initPSDiagramOverlayChart(pointdata, title,ytext,get_rawData.jh, get_rawData.jssj, divid);
	return false;
}
//initFSDiagramOverlayChart(pointdata, title,ytext,get_rawData.wellName, get_rawData.calculateDate, divid,upperLoadLine,lowerLoadLine,upperlimit,underlimit);
function initFSDiagramOverlayChart(series, title,ytext, wellName, acqTime, divid,upperLoadLine,lowerLoadLine,upperlimit,underlimit,strokeMax) {
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',      // 散点图   
		            renderTo : divid,
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
//		            max:upperlimit,
		            min:0
//		            plotLines: [{   //一条延伸到整个绘图区的线，标志着轴中一个特定值。
//	                    color: '#d12',
//	                    dashStyle: 'Dash', //Dash,Dot,Solid,默认Solid
//	                    label: {
//	                        text: upperLoadLine,
//	                        align: 'right',
//	                        x: -10
//	                    },
//	                    width: 3,
//	                    value: upperLoadLine,  //y轴显示位置
//	                    zIndex: 10
//	                },{
//	                    color: '#d12',
//	                    dashStyle: 'Dash',
//	                    label: {
//	                        text: lowerLoadLine,
//	                        align: 'right',
//	                        x: -10
//	                    },
//	                    width: 3,
//	                    value: lowerLoadLine,  //y轴显示位置
//	                    zIndex: 10
//	                }]
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

function initPSDiagramOverlayChart(series, title,ytext, wellName, acqTime, divid) {
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',      // 散点图   
		            renderTo : divid,
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
function handsontableDataCheck_Org(val, callback,row,col,handsontableHelper){
	var IframeViewSelection  = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
	var orgNum=Ext.getCmp("IframeView_Id").getStore().data.length;
	var selectOrgName='';
	if(IframeViewSelection.length>0){
		selectOrgName=IframeViewSelection[0].data.text;
	}else if(orgNum===1){
		selectOrgName=Ext.getCmp("IframeView_Id").getStore().data.items[0].data.text;
	}
	
	if(val!=selectOrgName){
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
