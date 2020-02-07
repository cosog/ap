Ext.define('AP.controller.well.WellInfoController', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'wellInfoPanel',
        selector: 'wellInfoPanel'
   }],
    init: function () {
        this.control({
            'wellInfoPanel > toolbar button[action=addreWellInfoAction]': {
                click: addwellInfo
            },
            'wellInfoPanel > toolbar button[action=editreWellInfoInfoAction]': {
                click: modifywellInfo
            },
            'wellInfoPanel > toolbar button[action=delreWellInfoAction]': {
                click: delectwellInfo
            },
            'wellInfoPanel > toolbar button[action=saveWellEditerGridDataAction]': {
                click: saveWellEditerGridDataInfo
            },
            'wellInfoPanel': {
                itemdblclick: modifywellInfo
            }
        })
    }
});

// 窗体创建按钮事件
var SavewellInfoSubmitBtnForm = function () {
    var SavewellInfoWindow = Ext.getCmp("wellInfo_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (SavewellInfoWindow.getForm().isValid()) {
        SavewellInfoWindow.getForm().submit({
            url: context + '/wellInformationManagerController/doWellInformationAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: cosog.string.sendServer,
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('wellInfo_addwin_Id').close();
                Ext.getCmp("wellPanel_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.success, "【<font color=blue>" + cosog.string.success + "</font>】，" + cosog.string.dataInfo + "");
                    if(mapHelperWell!=null){
    					mapHelperWell.clearOverlays();
    					SaveBackMapData(mapHelperWell,"well",m_BackDefaultZoomLevel);
    				}
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(cosog.string.success, "<font color=red>SORRY！</font>" + cosog.string.failInfo + "。");

                }
            },
            failure: function () {
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + "</font> 】：" + cosog.string.contactadmin + "！");
            }
        });
    } else {
        Ext.Msg.alert(cosog.string.success, "<font color=red>SORRY！" + cosog.string.validdata + ".</font>。");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

// 窗体上的修改按钮事件
function UpdatewellInfoSubmitBtnForm() {
    var getUpdateDataSubmitBtnFormId = Ext.getCmp("wellInfo_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (getUpdateDataSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("wellInfo_addwin_Id").el.mask(cosog.string.updatewait).show();
        getUpdateDataSubmitBtnFormId.getForm().submit({
            url: context + '/wellInformationManagerController/doWellInformationEdit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("wellInfo_addwin_Id").getEl().unmask();
                Ext.getCmp('wellInfo_addwin_Id').close();
                Ext.getCmp("wellPanel_Id").getStore().load();

                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.success, "【<font color=blue>" + cosog.string.sucupate + "</font>】，" + cosog.string.dataInfo + "。");
                    if(mapHelperWell!=null){
    					mapHelperWell.clearOverlays();
    					SaveBackMapData(mapHelperWell,"well",m_BackDefaultZoomLevel);
    				}
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(cosog.string.success,
                        "<font color=red>SORRY！</font>" + cosog.string.updatefail + "。");
                }
            },
            failure: function () {
                Ext.getCmp("wellInfo_addwin_Id").getEl().unmask();
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
            }
        });
    }
    return false;
};

// open win
function addwellInfo() {
        var win_Obj = Ext.getCmp("wellInfo_addwin_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var WellInfoWindow = Ext.create("AP.view.well.WellInfoWindow", {
            title: cosog.string.addWell
        });
        WellInfoWindow.show();
        var save_btn = Ext.getCmp("addFromwellBtn_Id");
        save_btn.show();
        var save_update_Btn = Ext.getCmp("updateFromwellBtn_Id");
        save_update_Btn.hide()
            // Ext.Msg.alert("title", "add WellInfoWindow Info!");
    }
    // del to action
function delectwellInfo() {
    var wellPanel_panel = Ext.getCmp("wellPanel_Id");
//    var wellPanel_model = wellPanel_panel.getSelectionModel();
//    var _record = wellPanel_model.getSelection();
//    var delUrl = context + '/wellInformationManagerController/doWellInformationBulkDelete'
//    if (_record.length>0) {
//        // 提示是否删除数据
//        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
//        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
//        Ext.Msg.confirm(cosog.string.yesdel, cosog.string.yesdeldata, function (btn) {
//            if (btn == "yes") {
//                ExtDel_ObjectInfo("wellPanel_Id", _record, "jlbh",delUrl); // 第一个参数为面板的Id，选择的记录对象，当前删除对象的Id名称，url
//            }
//        });
//    } else {
//        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
//    }
}

function modifywellInfo() {
		var wellPanel_panel = Ext.getCmp("wellPanel_Id");
		var wellPanel_model = wellPanel_panel.getSelectionModel();
		var _record = wellPanel_model.getSelection();
		if(_record.length>0){
			var win_Obj = Ext.getCmp("wellInfo_addwin_Id")
	        if (win_Obj != undefined) {
	            win_Obj.destroy();
	        }
	        var wellInfoWindow = Ext.create("AP.view.well.WellInfoWindow", {
	            title: cosog.string.editWell
	        });
	        wellInfoWindow.show();
	        Ext.getCmp("addFromwellBtn_Id").hide();
	        Ext.getCmp("updateFromwellBtn_Id").show();
	        SelectwellInfoPanel();
	        //setTimeout('SelectwellInfoPanel()',1000);
		}else{
	        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
	    }
        
    }
    // 赋值
SelectwellInfoPanel = function () {
    var dataattr_row = Ext.getCmp("wellPanel_Id").getSelectionModel().getSelection();
    var jlbh = dataattr_row[0].data.jlbh;
    var dwbh = dataattr_row[0].data.dwbh;
    var yqcbh = dataattr_row[0].data.yqcbh;
    var orgName = dataattr_row[0].data.orgName;
    var resName = dataattr_row[0].data.resName;
    var jc = dataattr_row[0].data.jc;
    var jhh = dataattr_row[0].data.jhh;
    var jh = dataattr_row[0].data.jh;
    var value = dataattr_row[0].data.jlx;
    var jlxName = dataattr_row[0].data.jlxName;
    var jslx = dataattr_row[0].data.jslx;
    var jslxName = dataattr_row[0].data.jslxName;
    var ssjwName = dataattr_row[0].data.ssjwName;
    var sszcdyName = dataattr_row[0].data.sszcdyName;
    var ssjw = dataattr_row[0].data.ssjw;
    var sszcdy = dataattr_row[0].data.sszcdy;
    var rgzsjd = dataattr_row[0].data.rgzsjd;
    var rgzsj = dataattr_row[0].data.rgzsj;
    var dmx = dataattr_row[0].data.dmx;
    var dmy = dataattr_row[0].data.dmy;
    var showLevel = dataattr_row[0].data.showLevel;
    Ext.getCmp('jlbh_Id').setValue(jlbh);
    Ext.getCmp('dwbh_Id').setValue(dwbh);
    Ext.getCmp('dwbh_Id1').setValue(dwbh);
    Ext.getCmp('dwbh_Id1').setRawValue(orgName);
    Ext.getCmp('yqcbh_Id').setValue(yqcbh);
    Ext.getCmp('yqcbh_Id1').setValue(yqcbh);
    Ext.getCmp('yqcbh_Id1').setRawValue(resName);
    Ext.getCmp('jc_Id').setValue(jc);
    Ext.getCmp('jc_Id').setRawValue(jc);
    Ext.getCmp('jhh_Id').setValue(jhh);
    Ext.getCmp('jh_Id').setValue(jh);
    Ext.getCmp('jlx_Id1').setValue(value);
    Ext.getCmp('jlx_Id1').setRawValue(jlxName);
    Ext.getCmp('jlx_Id').setValue(value);
    Ext.getCmp('jslx_Id1').setValue(jslx);
    Ext.getCmp('jslx_Id1').setRawValue(jslxName);
    Ext.getCmp('jslx_Id').setValue(jslx);
    Ext.getCmp('ssjw_Id1').setValue(ssjw);
    Ext.getCmp('ssjw_Id').setValue(ssjw);
    Ext.getCmp('ssjw_Id1').setRawValue(ssjwName);
    Ext.getCmp('sszcdy_Id1').setValue(sszcdy);
    Ext.getCmp('sszcdy_Id1').setRawValue(sszcdyName);
    Ext.getCmp('sszcdy_Id').setValue(sszcdy);
    Ext.getCmp('rgzsjd_Id').setValue(rgzsjd);
    Ext.getCmp('rgzsj_Id').setValue(rgzsj);
    Ext.getCmp('coordX_Id').setValue(dmx);
    Ext.getCmp('coordY_Id').setValue(dmy);
    Ext.getCmp('wellShowLevel_Id').setValue(showLevel);
};

//创建设置井坐标窗口
function openSetWellCoordWin() {
	var win_Obj = Ext.getCmp("wellCoordSetWindow_Id")
    if (win_Obj != undefined) {
    	win_Obj.destroy();
    }
	var wellInfoWindow = Ext.create("AP.view.well.WellCoordSetWindow", {
		title: cosog.string.setCoord
		});
	wellInfoWindow.show();
	$(function () {
        //初始化地图
		var div=Ext.getCmp("wellCoordSetDiv_Id");
		var div2=$("#wellCoordSetDiv_Id");
        mapHelper = MapHelper.createNew("wellCoordSetDiv_Id", m_DefaultPosition, m_DefaultZoomLevel, false, 1000);
        SetMapLocation(39.904, 116.404, 19);
        //获取后台油井数据
        SaveBackgroundWellData();
        //给地图添加单击事件
        mapHelper.addEventListener(mapHelper.getMap(), "click", mapClicked);
    });
};

//保存表格变化数据
function saveWellEditerGridDataInfo() {
	var well_panel = Ext.getCmp("wellPanel_Id");
	var store=Ext.getCmp("wellPanel_Id").getStore();
	var m = store.getUpdatedRecords();
	var total=store.getCount();
	var jsonArray = [];
	for(var i=0;i<total;i++){
		var model=store.getAt(i);
		if(model.dirty){
			jsonArray.push(model.data);
		}
	}
    if (jsonArray.length>0) {
    	Ext.Ajax.request({
    		method:'POST',
    		url:context + '/wellInformationManagerController/saveWellEditerGridData',
    		success:function(response) {
    			Ext.MessageBox.alert("信息","更新成功",function(){
    				well_panel.getStore().load();
    				well_panel.getStore().modified = []; 
    				if(mapHelperWell!=null){
    					mapHelperWell.clearOverlays();
    					SaveBackMapData(mapHelperWell,"well",m_BackDefaultZoomLevel);
    				}else{
    					updateWellMapDataAndShowMap();
    				}
    			});
    		},
    		failure:function(){
    			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
    		},
    		params: {
            	data: JSON.stringify(jsonArray)
            }
    	});   
    }else {
        //Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.noDataChange);
    }
    
    return false;
};



function updateWellCoordCellValue(wellname,arcX,arcY){
	var store=Ext.getCmp("wellPanel_Id").getStore();
	var total=store.getCount();
	for(var i=0;i<total;i++){
		if(store.getAt(i).data.jh==wellname){
			store.getAt(i).set("dmx",arcX);
			store.getAt(i).set("dmy",arcY);
			break;
		}
	}
	return false;
}