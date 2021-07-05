Ext.define('AP.controller.alarmSet.AlarmSetInfoController', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'alarmSetInfoGridPanel',
        selector: 'alarmSetInfoGridPanel'
   }],
    init: function () {
        this.control({
            'alarmSetInfoGridPanel > toolbar button[action=addAlarmSetAction]': {
                click: addAlarmSet
            },
            'alarmSetInfoGridPanel > toolbar button[action=editAlarmSetInfoAction]': {
                click: modifyAlarmSet
            },
            'alarmSetInfoGridPanel > toolbar button[action=delAlarmSetAction]': {
                click: delectAlarmSet
            },
            'alarmSetInfoGridPanel': {
                itemdblclick: modifyAlarmSet
            }
        })
    }
});

// 窗体创建按钮事件
var SaveAlarmSetSubmitBtnForm = function () {
    var SaveAlarmSetWindow = Ext.getCmp("AlarmSetWindow_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (SaveAlarmSetWindow.getForm().isValid()) {
        SaveAlarmSetWindow.getForm().submit({
            url: context + '/AlarmSetController/addAlarmSet',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: cosog.string.sendServer,
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('AlarmSetWindow_Id').close();
                Ext.getCmp("AlarmSet_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.success + "</font>】，" + cosog.string.dataInfo + "");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(cosog.string.ts, "<font color=red>SORRY！</font>" + cosog.string.failInfo + "。");

                }
            },
            failure: function () {
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + "</font> 】：" + cosog.string.contactadmin + "！");
            }
        });
    } else {
        Ext.Msg.alert(cosog.string.ts, "<font color=red>SORRY！" + cosog.string.validdata + ".</font>。");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

// 窗体上的修改按钮事件
function UpdateAlarmSetSubmitBtnForm() {
    var UpdateAlarmSetgWindowDataSubmitBtnFormId = Ext
        .getCmp("AlarmSetInfoWindowwin_Id").down('form');
    if (UpdateAlarmSetgWindowDataSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("AlarmSetInfoWindowwin_Id").el.mask(cosog.string.updatewait).show();
        UpdateAlarmSetgWindowDataSubmitBtnFormId.getForm().submit({
            url: context + '/alarmSetManagerController/doAlarmsSetEdit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("AlarmSetInfoWindowwin_Id").getEl().unmask();
                Ext.getCmp('AlarmSetInfoWindowwin_Id').close();
                Ext.getCmp("AlarmSetInfoGridPanel_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.sucupate + "</font>】，" + cosog.string.dataInfo + "。");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(cosog.string.ts,
                        "<font color=red>SORRY！</font>" + cosog.string.updatefail + "。");
                }
            },
            failure: function () {
                Ext.getCmp("AlarmSetInfoWindowwin_Id").getEl().unmask();
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
            }
        });
    }
    return false;
};

// open win
function addAlarmSet() {
        var win_Obj = Ext.getCmp("AlarmSetWindow_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var AlarmSetWindow = Ext.create("AP.view.device.AlarmSetWindow", {
            title: "创建设备管理-抽油泵"
        });
        AlarmSetWindow.show();
        Ext.getCmp("addAlarmSetLabelClassBtn_win_Id").show();
        Ext.getCmp("editAlarmSetLabelClassBtn_win_Id").hide()
            // Ext.Msg.alert("title", "add WellInfoWindow Info!");
    }
    // del to action
function delectAlarmSet() {
    var AlarmSet_panel = Ext.getCmp("AlarmSet_Id");
    var AlarmSet_model = AlarmSet_panel.getSelectionModel();
    var _record = AlarmSet_model.getSelection();
    // var _record = sm.getSelected();
    var delUrl = context + '/AlarmSetController/delectAlarmSet'
    if (_record) {
        // 提示是否删除数据
        Ext.MessageBox.msgButtons['yes'].text = "确定";
        Ext.MessageBox.msgButtons['no'].text = "取消";
        Ext.Msg.confirm("是否要删除？", "是否要删除这些被选择的数据？", function (btn) {
            if (btn == "yes") {
                ExtDel_ObjectInfo("AlarmSet_Id", _record, "jlbh", delUrl); // 第一个参数为面板的Id，选择的记录对象，当前删除对象的Id名称，url
            }
        });
    } else {
        Ext.Msg.alert('删除操作', '您必须选择一行数据以便删除！');
    }
    // Ext.Msg.alert("title", "delete reservoirProperty Info!");
}

function modifyAlarmSet() {
        var win_Obj = Ext.getCmp("AlarmSetInfoWindowwin_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var wellInfoWindow = Ext.create("AP.view.alarmSet.AlarmSetInfoWindow", {
            title: cosog.string.editAlarm
        });
        wellInfoWindow.show();
        Ext.getCmp("addFormAlarmSet_Id").hide();
        Ext.getCmp("updateFormAlarm_Id").show();
        SelectAlarmSet();
        return false;
    }
    // 复值
SelectAlarmSet = function () {
    var dataattr_row = Ext.getCmp("AlarmSetInfoGridPanel_Id").getSelectionModel().getSelection();
    var recordId = dataattr_row[0].data.id;
    var resultCode = dataattr_row[0].data.resultcode;
    var resultName = dataattr_row[0].data.resultname;
    var alarmType = dataattr_row[0].data.alarmtype;
    var alarmTypeName = dataattr_row[0].data.alarmtypename;
    var alarmLevel = dataattr_row[0].data.alarmlevel;
    var alarmLevelName = dataattr_row[0].data.alarmlevelname;
    var alarmSign = dataattr_row[0].data.alarmsign;
    var remark = dataattr_row[0].data.remark;
    Ext.getCmp('alarmSetRecord_Id').setValue(recordId);
    Ext.getCmp('alarmSetResultCodeComb_Id').setValue(resultCode);
    Ext.getCmp('alarmSetResultCodeComb_Id').setRawValue(resultName);
    Ext.getCmp('alarmSetAlarmType_Id').setValue(alarmType);
    Ext.getCmp('alarmSetAlarmType_Id').setRawValue(alarmTypeName);
    Ext.getCmp('alarmSetAlarmLevel_Id').setValue(alarmLevel);
    Ext.getCmp('alarmSetAlarmLevel_Id').setRawValue(alarmLevelName);
    Ext.getCmp('alarmSetAlarmSignComb_Id').setValue(alarmSign);
    Ext.getCmp('alarmSetAlarmSign_Id').setValue(alarmSign);
    Ext.getCmp("alarmSetResultCodeComb_Id").readOnly = true;
    Ext.getCmp("alarmSetAlarmType_Id").readOnly = true;
    if (bjbz == 0) {
        Ext.getCmp('alarmSetAlarmSignComb_Id').setRawValue('关');
    } else {
        Ext.getCmp('alarmSetAlarmSignComb_Id').setRawValue('开');
    }
    Ext.getCmp('alarmSetRemark_Id').setValue(remark);
};

//设置报警级别颜色
 function setAlarmLevelColor(){
	 var alarmLevelBackgroundColor0=Ext.getCmp('alarmLevelBackgroundColor0_id').getValue();
	 var alarmLevelBackgroundColor1=Ext.getCmp('alarmLevelBackgroundColor1_id').getValue();
	 var alarmLevelBackgroundColor2=Ext.getCmp('alarmLevelBackgroundColor2_id').getValue();
	 var alarmLevelBackgroundColor3=Ext.getCmp('alarmLevelBackgroundColor3_id').getValue();
	 
	 var alarmLevelColor0=Ext.getCmp('alarmLevelColor0_id').getValue();
	 var alarmLevelColor1=Ext.getCmp('alarmLevelColor1_id').getValue();
	 var alarmLevelColor2=Ext.getCmp('alarmLevelColor2_id').getValue();
	 var alarmLevelColor3=Ext.getCmp('alarmLevelColor3_id').getValue();
	 
	 var alarmLevelOpacity0=Ext.getCmp('alarmLevelOpacity0_id').getValue();
	 var alarmLevelOpacity1=Ext.getCmp('alarmLevelOpacity1_id').getValue();
	 var alarmLevelOpacity2=Ext.getCmp('alarmLevelOpacity2_id').getValue();
	 var alarmLevelOpacity3=Ext.getCmp('alarmLevelOpacity3_id').getValue();

	 
	 
//	 var alarmLevelBackgroundColor4=Ext.getCmp('alarmLevelBackgroundColor4_id').getValue();
	 Ext.Ajax.request({
	        url: context + '/alarmSetManagerController/setAlarmLevelColor',
	        method: "POST",
	        params: {
	        	alarmLevelBackgroundColor0: alarmLevelBackgroundColor0,
	        	alarmLevelBackgroundColor1: alarmLevelBackgroundColor1,
	        	alarmLevelBackgroundColor2: alarmLevelBackgroundColor2,
	        	alarmLevelBackgroundColor3: alarmLevelBackgroundColor3,
	        	alarmLevelColor0:alarmLevelColor0,
	        	alarmLevelColor1:alarmLevelColor1,
	        	alarmLevelColor2:alarmLevelColor2,
	        	alarmLevelColor3:alarmLevelColor3,
	        	alarmLevelOpacity0:alarmLevelOpacity0,
	        	alarmLevelOpacity1:alarmLevelOpacity1,
	        	alarmLevelOpacity2:alarmLevelOpacity2,
	        	alarmLevelOpacity3:alarmLevelOpacity3
	        },
	        success: function (response, action) {
	        	if (Ext.decode(response.responseText).msg) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.sucupate + "</font>】，" + cosog.string.dataInfo + "。");
                }
	        	else {
                    Ext.Msg.alert(cosog.string.ts,
                        "<font color=red>SORRY！</font>" + cosog.string.updatefail + "。");
                }
	        },
	        failure: function () {
	            Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
	        }
	    });
 }
 
//获取报警级别颜色
 function getAlarmLevelColor(){
	 Ext.Ajax.request({
	        url: context + '/alarmSetManagerController/getAlarmLevelColor',
	        method: "POST",
	        success: function (response) {
	        	var get_rawData = Ext.decode(response.responseText); // 获取返回数据
	        	
	        	Ext.getCmp('alarmLevelBackgroundColor0_id').setValue(get_rawData.BackgroundColor.Level0);
	        	var BackgroundColor0=Ext.getCmp('alarmLevelBackgroundColor0_id').color;
	        	BackgroundColor0.a=get_rawData.Opacity.Level0;
            	Ext.getCmp('alarmLevelBackgroundColor0_id').inputEl.applyStyles({
            		background: '#'+get_rawData.BackgroundColor.Level0,
            		opacity:get_rawData.Opacity.Level0
            	});
	        	Ext.getCmp('alarmLevelBackgroundColor0_id').setColor(BackgroundColor0);
	        	
	            Ext.getCmp('alarmLevelBackgroundColor1_id').setValue(get_rawData.BackgroundColor.Level1);
	            var BackgroundColor1=Ext.getCmp('alarmLevelBackgroundColor1_id').color;
	        	BackgroundColor1.a=get_rawData.Opacity.Level1;
            	Ext.getCmp('alarmLevelBackgroundColor1_id').inputEl.applyStyles({
            		background: '#'+get_rawData.BackgroundColor.Level1,
            		opacity:get_rawData.Opacity.Level1
            	});
	        	Ext.getCmp('alarmLevelBackgroundColor1_id').setColor(BackgroundColor1);
	            
	            
	            Ext.getCmp('alarmLevelBackgroundColor2_id').setValue(get_rawData.BackgroundColor.Level2);
	            var BackgroundColor2=Ext.getCmp('alarmLevelBackgroundColor2_id').color;
	        	BackgroundColor2.a=get_rawData.Opacity.Level2;
            	Ext.getCmp('alarmLevelBackgroundColor2_id').inputEl.applyStyles({
            		background: '#'+get_rawData.BackgroundColor.Level2,
            		opacity:get_rawData.Opacity.Level2
            	});
	        	Ext.getCmp('alarmLevelBackgroundColor2_id').setColor(BackgroundColor2);
	            
	            
	            Ext.getCmp('alarmLevelBackgroundColor3_id').setValue(get_rawData.BackgroundColor.Level3);
	            var BackgroundColor3=Ext.getCmp('alarmLevelBackgroundColor3_id').color;
	        	BackgroundColor3.a=get_rawData.Opacity.Level3;
            	Ext.getCmp('alarmLevelBackgroundColor3_id').inputEl.applyStyles({
            		background: '#'+get_rawData.BackgroundColor.Level3,
            		opacity:get_rawData.Opacity.Level3
            	});
	        	Ext.getCmp('alarmLevelBackgroundColor3_id').setColor(BackgroundColor3);
	            

	            
	            Ext.getCmp('alarmLevelColor0_id').setValue(get_rawData.Color.Level0);
	            var Color0=Ext.getCmp('alarmLevelColor0_id').color;
            	Ext.getCmp('alarmLevelColor0_id').inputEl.applyStyles({
            		background: '#'+get_rawData.Color.Level0,
            	});
	            
	            Ext.getCmp('alarmLevelColor1_id').setValue(get_rawData.Color.Level1);
	            var Color1=Ext.getCmp('alarmLevelColor1_id').color;
            	Ext.getCmp('alarmLevelColor1_id').inputEl.applyStyles({
            		background: '#'+get_rawData.Color.Level1,
            	});
	            
	            
	            Ext.getCmp('alarmLevelColor2_id').setValue(get_rawData.Color.Level2);
	            var Color2=Ext.getCmp('alarmLevelColor2_id').color;
            	Ext.getCmp('alarmLevelColor2_id').inputEl.applyStyles({
            		background: '#'+get_rawData.Color.Level2,
            	});
	            
	            Ext.getCmp('alarmLevelColor3_id').setValue(get_rawData.Color.Level3);
	            var Color3=Ext.getCmp('alarmLevelColor3_id').color;
            	Ext.getCmp('alarmLevelColor3_id').inputEl.applyStyles({
            		background: '#'+get_rawData.Color.Level3,
            	});
	            
	            Ext.getCmp('alarmLevelOpacity0_id').setValue(get_rawData.Opacity.Level0);
	            Ext.getCmp('alarmLevelOpacity1_id').setValue(get_rawData.Opacity.Level1);
	            Ext.getCmp('alarmLevelOpacity2_id').setValue(get_rawData.Opacity.Level2);
	            Ext.getCmp('alarmLevelOpacity3_id').setValue(get_rawData.Opacity.Level3);
	        },
	        failure: function () {
	            Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
	        }
	    });
 }
 
//保存离散报警数据
// function UpdateDiscreteAlarmSetFormSubmit() {
//     var DistreteAlarmSetFormPanel = Ext.getCmp("DistreteAlarmSetFormPanel").down('form').getForm();
//     if (DistreteAlarmSetFormPanel.isValid()) {
//    	 
//     }
//     return false;
// };
//更新离散数据报警限值界面显示值
 function updateDiatreteAlarmLimitValue(data) {
     Ext.getCmp("distreteJbh_Id").setValue(data.id);
     Ext.getCmp("distreteAlarm_Id").setValue(data.alarmid);
     Ext.getCmp("currentAMax_Id").setValue(data.currentamax);
     Ext.getCmp("currentAMin_Id").setValue(data.currentamin);
     Ext.getCmp("voltageAMax_Id").setValue(data.voltageamax);
     Ext.getCmp("voltageAMin_Id").setValue(data.voltageamin);
     Ext.getCmp("activePowerAMax_Id").setValue(data.activepoweramax);
     Ext.getCmp("activePowerAMin_Id").setValue(data.activepoweramin);
     
     Ext.getCmp("currentBMax_Id").setValue(data.currentbmax);
     Ext.getCmp("currentBMin_Id").setValue(data.currentbmin);
     Ext.getCmp("voltageBMax_Id").setValue(data.voltagebmax);
     Ext.getCmp("voltageBMin_Id").setValue(data.voltagebmin);
     Ext.getCmp("activePowerBMax_Id").setValue(data.activepowerbmax);
     Ext.getCmp("activePowerBMin_Id").setValue(data.activepowerbmin);
     
     Ext.getCmp("currentCMax_Id").setValue(data.currentcmax);
     Ext.getCmp("currentCMin_Id").setValue(data.currentcmin);
     Ext.getCmp("voltageCMax_Id").setValue(data.voltagecmax);
     Ext.getCmp("voltageCMin_Id").setValue(data.voltagecmin);
     Ext.getCmp("activePowerCMax_Id").setValue(data.activepowercmax);
     Ext.getCmp("activePowerCMin_Id").setValue(data.activepowercmin);
     
     Ext.getCmp("currentAvgMax_Id").setValue(data.currentavgmax);
     Ext.getCmp("currentAvgMin_Id").setValue(data.currentavgmin);
     Ext.getCmp("voltageAvgMax_Id").setValue(data.voltageavgmax);
     Ext.getCmp("voltageAvgMin_Id").setValue(data.voltageavgmin);
     Ext.getCmp("activePowerSumMax_Id").setValue(data.activepowersummax);
     Ext.getCmp("activePowerSumMin_Id").setValue(data.activepowersummin);
     return false;
 };
 
//设置报警级别颜色
 function UpdateDiscreteAlarmSet(){
	 var jbh=Ext.getCmp("distreteJbh_Id").getValue();
     var jlbh=Ext.getCmp("distreteAlarm_Id").getValue();
     
     var currentamax=Ext.getCmp("currentAMax_Id").getValue();
     var currentamin=Ext.getCmp("currentAMin_Id").getValue();
     var voltageamax=Ext.getCmp("voltageAMax_Id").getValue();
     var voltageamin=Ext.getCmp("voltageAMin_Id").getValue();
     var activepoweramax=Ext.getCmp("activePowerAMax_Id").getValue();
     var activepoweramin=Ext.getCmp("activePowerAMin_Id").getValue();
     
     var currentbmax=Ext.getCmp("currentBMax_Id").getValue();
     var currentbmin=Ext.getCmp("currentBMin_Id").getValue();
     var voltagebmax=Ext.getCmp("voltageBMax_Id").getValue();
     var voltagebmin=Ext.getCmp("voltageBMin_Id").getValue();
     var activepowerbmax=Ext.getCmp("activePowerBMax_Id").getValue();
     var activepowerbmin=Ext.getCmp("activePowerBMin_Id").getValue();
     
     var currentcmax=Ext.getCmp("currentCMax_Id").getValue();
     var currentcmin=Ext.getCmp("currentCMin_Id").getValue();
     var voltagecmax=Ext.getCmp("voltageCMax_Id").getValue();
     var voltagecmin=Ext.getCmp("voltageCMin_Id").getValue();
     var activepowercmax=Ext.getCmp("activePowerCMax_Id").getValue();
     var activepowercmin=Ext.getCmp("activePowerCMin_Id").getValue();
     
     var currentavgmax=Ext.getCmp("currentAvgMax_Id").getValue();
     var currentavgmin=Ext.getCmp("currentAvgMin_Id").getValue();
     var voltageavgmax=Ext.getCmp("voltageAvgMax_Id").getValue();
     var voltageavgmin=Ext.getCmp("voltageAvgMin_Id").getValue();
     var activepowersummax=Ext.getCmp("activePowerSumMax_Id").getValue();
     var activepowersummin=Ext.getCmp("activePowerSumMin_Id").getValue();
	 Ext.Ajax.request({
	        url: context + '/alarmSetManagerController/updateDiscreteAlarmSet',
	        method: "POST",
	        params: {
	        	jlbh: jlbh,
	        	jbh: jbh,
	        	currentamax: currentamax,
	        	currentamin: currentamin,
	        	voltageamax: voltageamax,
	        	voltageamin: voltageamin,
	        	activepoweramax: activepoweramax,
	        	activepoweramin: activepoweramin,
	        	currentbmax: currentbmax,
	        	currentbmin: currentbmin,
	        	voltagebmax: voltagebmax,
	        	voltagebmin: voltagebmin,
	        	activepowerbmax: activepowerbmax,
	        	activepowerbmin: activepowerbmin,
	        	currentcmax: currentcmax,
	        	currentcmin: currentcmin,
	        	voltagecmax: voltagecmax,
	        	voltagecmin: voltagecmin,
	        	activepowercmax: activepowercmax,
	        	activepowercmin: activepowercmin,
	        	currentavgmax: currentavgmax,
	        	currentavgmin: currentavgmin,
	        	voltageavgmax: voltageavgmax,
	        	voltageavgmin: voltageavgmin,
	        	activepowersummax: activepowersummax,
	        	activepowersummin: activepowersummin,
	        },
	        success: function (response, action) {
	        	if (Ext.decode(response.responseText).msg) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.sucupate + "</font>】，" + cosog.string.dataInfo + "。");
                    var AlarmSetSingleWellListGrid = Ext.getCmp("AlarmSetSingleWellListGrid_Id");
            		if(isNotVal(AlarmSetSingleWellListGrid)){
            			AlarmSetSingleWellListGrid.getStore().load();
            		}else{
            			Ext.create('AP.store.alarmSet.AlarmSetSingleWellListStore');
            		}
                }
	        	else {
                    Ext.Msg.alert(cosog.string.ts,
                        "<font color=red>SORRY！</font>" + cosog.string.updatefail + "。");
                }
	        },
	        failure: function () {
	            Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
	        }
	    });
 };