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
            title: '报警项配置'
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
//    if (alarmSign == 0) {
//        Ext.getCmp('alarmSetAlarmSignComb_Id').setRawValue('关');
//    } else {
//        Ext.getCmp('alarmSetAlarmSignComb_Id').setRawValue('开');
//    }
    Ext.getCmp('alarmSetAlarmSignComb_Id').setRawValue(alarmSign);
    Ext.getCmp('alarmSetRemark_Id').setValue(remark);
};

//设置报警级别颜色
 function setAlarmLevelColor(){
	 var overviewNormalBackgroundColor=Ext.getCmp('overviewNormalBackgroundColor_id').getValue();
	 var overviewFirstLevelBackgroundColor=Ext.getCmp('overviewFirstLevelBackgroundColor_id').getValue();
	 var overviewSecondLevelBackgroundColor=Ext.getCmp('overviewSecondLevelBackgroundColor_id').getValue();
	 var overviewThirdLevelBackgroundColor=Ext.getCmp('overviewThirdLevelBackgroundColor_id').getValue();
	 
	 var overviewNormalColor=Ext.getCmp('overviewNormalColor_id').getValue();
	 var overviewFirstLevelColor=Ext.getCmp('overviewFirstLevelColor_id').getValue();
	 var overviewSecondLevelColor=Ext.getCmp('overviewSecondLevelColor_id').getValue();
	 var overviewThirdLevelColor=Ext.getCmp('overviewThirdLevelColor_id').getValue();
	 
	 var overviewNormalOpacity=Ext.getCmp('overviewNormalOpacity_id').getValue();
	 var overviewFirstLevelOpacity=Ext.getCmp('overviewFirstLevelOpacity_id').getValue();
	 var overviewSecondLevelOpacity=Ext.getCmp('overviewSecondLevelOpacity_id').getValue();
	 var overviewThirdLevelOpacity=Ext.getCmp('overviewThirdLevelOpacity_id').getValue();
	 
	 //
	 var detailsNormalBackgroundColor=Ext.getCmp('detailsNormalBackgroundColor_id').getValue();
	 var detailsFirstLevelBackgroundColor=Ext.getCmp('detailsFirstLevelBackgroundColor_id').getValue();
	 var detailsSecondLevelBackgroundColor=Ext.getCmp('detailsSecondLevelBackgroundColor_id').getValue();
	 var detailsThirdLevelBackgroundColor=Ext.getCmp('detailsThirdLevelBackgroundColor_id').getValue();
	 
	 var detailsNormalColor=Ext.getCmp('detailsNormalColor_id').getValue();
	 var detailsFirstLevelColor=Ext.getCmp('detailsFirstLevelColor_id').getValue();
	 var detailsSecondLevelColor=Ext.getCmp('detailsSecondLevelColor_id').getValue();
	 var detailsThirdLevelColor=Ext.getCmp('detailsThirdLevelColor_id').getValue();
	 
	 var detailsNormalOpacity=Ext.getCmp('detailsNormalOpacity_id').getValue();
	 var detailsFirstLevelOpacity=Ext.getCmp('detailsFirstLevelOpacity_id').getValue();
	 var detailsSecondLevelOpacity=Ext.getCmp('detailsSecondLevelOpacity_id').getValue();
	 var detailsThirdLevelOpacity=Ext.getCmp('detailsThirdLevelOpacity_id').getValue();
	 
	 //
	 var statisticsNormalBackgroundColor=Ext.getCmp('statisticsNormalBackgroundColor_id').getValue();
	 var statisticsFirstLevelBackgroundColor=Ext.getCmp('statisticsFirstLevelBackgroundColor_id').getValue();
	 var statisticsSecondLevelBackgroundColor=Ext.getCmp('statisticsSecondLevelBackgroundColor_id').getValue();
	 var statisticsThirdLevelBackgroundColor=Ext.getCmp('statisticsThirdLevelBackgroundColor_id').getValue();
	 
	 var statisticsNormalColor=Ext.getCmp('statisticsNormalColor_id').getValue();
	 var statisticsFirstLevelColor=Ext.getCmp('statisticsFirstLevelColor_id').getValue();
	 var statisticsSecondLevelColor=Ext.getCmp('statisticsSecondLevelColor_id').getValue();
	 var statisticsThirdLevelColor=Ext.getCmp('statisticsThirdLevelColor_id').getValue();
	 
	 var statisticsNormalOpacity=Ext.getCmp('statisticsNormalOpacity_id').getValue();
	 var statisticsFirstLevelOpacity=Ext.getCmp('statisticsFirstLevelOpacity_id').getValue();
	 var statisticsSecondLevelOpacity=Ext.getCmp('statisticsSecondLevelOpacity_id').getValue();
	 var statisticsThirdLevelOpacity=Ext.getCmp('statisticsThirdLevelOpacity_id').getValue();

	 var AlarmShowStyle={};
	 AlarmShowStyle.Overview={};
	 AlarmShowStyle.Overview.Normal={};
	 AlarmShowStyle.Overview.Normal.Value=0;
	 AlarmShowStyle.Overview.Normal.BackgroundColor=overviewNormalBackgroundColor;
	 AlarmShowStyle.Overview.Normal.Color=overviewNormalColor;
	 AlarmShowStyle.Overview.Normal.Opacity=overviewNormalOpacity;
	 AlarmShowStyle.Overview.FirstLevel={};
	 AlarmShowStyle.Overview.FirstLevel.Value=100;
	 AlarmShowStyle.Overview.FirstLevel.BackgroundColor=overviewFirstLevelBackgroundColor;
	 AlarmShowStyle.Overview.FirstLevel.Color=overviewFirstLevelColor;
	 AlarmShowStyle.Overview.FirstLevel.Opacity=overviewFirstLevelOpacity;
	 AlarmShowStyle.Overview.SecondLevel={};
	 AlarmShowStyle.Overview.SecondLevel.Value=200;
	 AlarmShowStyle.Overview.SecondLevel.BackgroundColor=overviewSecondLevelBackgroundColor;
	 AlarmShowStyle.Overview.SecondLevel.Color=overviewSecondLevelColor;
	 AlarmShowStyle.Overview.SecondLevel.Opacity=overviewSecondLevelOpacity;
	 AlarmShowStyle.Overview.ThirdLevel={};
	 AlarmShowStyle.Overview.ThirdLevel.Value=300;
	 AlarmShowStyle.Overview.ThirdLevel.BackgroundColor=overviewThirdLevelBackgroundColor;
	 AlarmShowStyle.Overview.ThirdLevel.Color=overviewThirdLevelColor;
	 AlarmShowStyle.Overview.ThirdLevel.Opacity=overviewThirdLevelOpacity;
	 
	 AlarmShowStyle.Details={};
	 AlarmShowStyle.Details.Normal={};
	 AlarmShowStyle.Details.Normal.Value=0;
	 AlarmShowStyle.Details.Normal.BackgroundColor=detailsNormalBackgroundColor;
	 AlarmShowStyle.Details.Normal.Color=detailsNormalColor;
	 AlarmShowStyle.Details.Normal.Opacity=detailsNormalOpacity;
	 AlarmShowStyle.Details.FirstLevel={};
	 AlarmShowStyle.Details.FirstLevel.Value=100;
	 AlarmShowStyle.Details.FirstLevel.BackgroundColor=detailsFirstLevelBackgroundColor;
	 AlarmShowStyle.Details.FirstLevel.Color=detailsFirstLevelColor;
	 AlarmShowStyle.Details.FirstLevel.Opacity=detailsFirstLevelOpacity;
	 AlarmShowStyle.Details.SecondLevel={};
	 AlarmShowStyle.Details.SecondLevel.Value=200;
	 AlarmShowStyle.Details.SecondLevel.BackgroundColor=detailsSecondLevelBackgroundColor;
	 AlarmShowStyle.Details.SecondLevel.Color=detailsSecondLevelColor;
	 AlarmShowStyle.Details.SecondLevel.Opacity=detailsSecondLevelOpacity;
	 AlarmShowStyle.Details.ThirdLevel={};
	 AlarmShowStyle.Details.ThirdLevel.Value=300;
	 AlarmShowStyle.Details.ThirdLevel.BackgroundColor=detailsThirdLevelBackgroundColor;
	 AlarmShowStyle.Details.ThirdLevel.Color=detailsThirdLevelColor;
	 AlarmShowStyle.Details.ThirdLevel.Opacity=detailsThirdLevelOpacity;
	 
	 AlarmShowStyle.Statistics={};
	 AlarmShowStyle.Statistics.Normal={};
	 AlarmShowStyle.Statistics.Normal.Value=0;
	 AlarmShowStyle.Statistics.Normal.BackgroundColor=statisticsNormalBackgroundColor;
	 AlarmShowStyle.Statistics.Normal.Color=statisticsNormalColor;
	 AlarmShowStyle.Statistics.Normal.Opacity=statisticsNormalOpacity;
	 AlarmShowStyle.Statistics.FirstLevel={};
	 AlarmShowStyle.Statistics.FirstLevel.Value=100;
	 AlarmShowStyle.Statistics.FirstLevel.BackgroundColor=statisticsFirstLevelBackgroundColor;
	 AlarmShowStyle.Statistics.FirstLevel.Color=statisticsFirstLevelColor;
	 AlarmShowStyle.Statistics.FirstLevel.Opacity=statisticsFirstLevelOpacity;
	 AlarmShowStyle.Statistics.SecondLevel={};
	 AlarmShowStyle.Statistics.SecondLevel.Value=200;
	 AlarmShowStyle.Statistics.SecondLevel.BackgroundColor=statisticsSecondLevelBackgroundColor;
	 AlarmShowStyle.Statistics.SecondLevel.Color=statisticsSecondLevelColor;
	 AlarmShowStyle.Statistics.SecondLevel.Opacity=statisticsSecondLevelOpacity;
	 AlarmShowStyle.Statistics.ThirdLevel={};
	 AlarmShowStyle.Statistics.ThirdLevel.Value=300;
	 AlarmShowStyle.Statistics.ThirdLevel.BackgroundColor=statisticsThirdLevelBackgroundColor;
	 AlarmShowStyle.Statistics.ThirdLevel.Color=statisticsThirdLevelColor;
	 AlarmShowStyle.Statistics.ThirdLevel.Opacity=statisticsThirdLevelOpacity;
	 
	 Ext.Ajax.request({
	        url: context + '/alarmSetManagerController/setAlarmLevelColor',
	        method: "POST",
	        params: {
	        	data: JSON.stringify(AlarmShowStyle)
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
	        	var AlarmShowStyle = Ext.decode(response.responseText); // 获取返回数据
	        	
	        	//初始化概览数据报警颜色
	        	Ext.getCmp('overviewNormalBackgroundColor_id').setValue(AlarmShowStyle.Overview.Normal.BackgroundColor);
	        	var BackgroundColor0=Ext.getCmp('overviewNormalBackgroundColor_id').color;
	        	BackgroundColor0.a=AlarmShowStyle.Overview.Normal.Opacity;
            	Ext.getCmp('overviewNormalBackgroundColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Overview.Normal.BackgroundColor,
            		opacity:AlarmShowStyle.Overview.Normal.Opacity
            	});
	        	Ext.getCmp('overviewNormalBackgroundColor_id').setColor(BackgroundColor0);
	        	
	            Ext.getCmp('overviewFirstLevelBackgroundColor_id').setValue(AlarmShowStyle.Overview.FirstLevel.BackgroundColor);
	            var BackgroundColor1=Ext.getCmp('overviewFirstLevelBackgroundColor_id').color;
	        	BackgroundColor1.a=AlarmShowStyle.Overview.FirstLevel.Opacity;
            	Ext.getCmp('overviewFirstLevelBackgroundColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Overview.FirstLevel.BackgroundColor,
            		opacity:AlarmShowStyle.Overview.FirstLevel.Opacity
            	});
	        	Ext.getCmp('overviewFirstLevelBackgroundColor_id').setColor(BackgroundColor1);
	            
	            
	            Ext.getCmp('overviewSecondLevelBackgroundColor_id').setValue(AlarmShowStyle.Overview.SecondLevel.BackgroundColor);
	            var BackgroundColor2=Ext.getCmp('overviewSecondLevelBackgroundColor_id').color;
	        	BackgroundColor2.a=AlarmShowStyle.Overview.SecondLevel.Opacity;
            	Ext.getCmp('overviewSecondLevelBackgroundColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Overview.SecondLevel.BackgroundColor,
            		opacity:AlarmShowStyle.Overview.SecondLevel.Opacity
            	});
	        	Ext.getCmp('overviewSecondLevelBackgroundColor_id').setColor(BackgroundColor2);
	            
	            
	            Ext.getCmp('overviewThirdLevelBackgroundColor_id').setValue(AlarmShowStyle.Overview.ThirdLevel.BackgroundColor);
	            var BackgroundColor3=Ext.getCmp('overviewThirdLevelBackgroundColor_id').color;
	        	BackgroundColor3.a=AlarmShowStyle.Overview.ThirdLevel.Opacity;
            	Ext.getCmp('overviewThirdLevelBackgroundColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Overview.ThirdLevel.BackgroundColor,
            		opacity:AlarmShowStyle.Overview.ThirdLevel.Opacity
            	});
	        	Ext.getCmp('overviewThirdLevelBackgroundColor_id').setColor(BackgroundColor3);
	            

	            
	            Ext.getCmp('overviewNormalColor_id').setValue(AlarmShowStyle.Overview.Normal.Color);
	            var Color0=Ext.getCmp('overviewNormalColor_id').color;
            	Ext.getCmp('overviewNormalColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Overview.Normal.Color,
            	});
	            
	            Ext.getCmp('overviewFirstLevelColor_id').setValue(AlarmShowStyle.Overview.FirstLevel.Color);
	            var Color1=Ext.getCmp('overviewFirstLevelColor_id').color;
            	Ext.getCmp('overviewFirstLevelColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Overview.FirstLevel.Color,
            	});
	            
	            
	            Ext.getCmp('overviewSecondLevelColor_id').setValue(AlarmShowStyle.Overview.SecondLevel.Color);
	            var Color2=Ext.getCmp('overviewSecondLevelColor_id').color;
            	Ext.getCmp('overviewSecondLevelColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Overview.SecondLevel.Color,
            	});
	            
	            Ext.getCmp('overviewThirdLevelColor_id').setValue(AlarmShowStyle.Overview.ThirdLevel.Color);
	            var Color3=Ext.getCmp('overviewThirdLevelColor_id').color;
            	Ext.getCmp('overviewThirdLevelColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Overview.ThirdLevel.Color,
            	});
	            
	            Ext.getCmp('overviewNormalOpacity_id').setValue(AlarmShowStyle.Overview.Normal.Opacity);
	            Ext.getCmp('overviewFirstLevelOpacity_id').setValue(AlarmShowStyle.Overview.FirstLevel.Opacity);
	            Ext.getCmp('overviewSecondLevelOpacity_id').setValue(AlarmShowStyle.Overview.SecondLevel.Opacity);
	            Ext.getCmp('overviewThirdLevelOpacity_id').setValue(AlarmShowStyle.Overview.ThirdLevel.Opacity);
	            
	            //初始化实时/历史表报警颜色
	            Ext.getCmp('detailsNormalBackgroundColor_id').setValue(AlarmShowStyle.Details.Normal.BackgroundColor);
	        	var BackgroundColor0=Ext.getCmp('detailsNormalBackgroundColor_id').color;
	        	BackgroundColor0.a=AlarmShowStyle.Details.Normal.Opacity;
            	Ext.getCmp('detailsNormalBackgroundColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Details.Normal.BackgroundColor,
            		opacity:AlarmShowStyle.Details.Normal.Opacity
            	});
	        	Ext.getCmp('detailsNormalBackgroundColor_id').setColor(BackgroundColor0);
	        	
	            Ext.getCmp('detailsFirstLevelBackgroundColor_id').setValue(AlarmShowStyle.Details.FirstLevel.BackgroundColor);
	            var BackgroundColor1=Ext.getCmp('detailsFirstLevelBackgroundColor_id').color;
	        	BackgroundColor1.a=AlarmShowStyle.Details.FirstLevel.Opacity;
            	Ext.getCmp('detailsFirstLevelBackgroundColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Details.FirstLevel.BackgroundColor,
            		opacity:AlarmShowStyle.Details.FirstLevel.Opacity
            	});
	        	Ext.getCmp('detailsFirstLevelBackgroundColor_id').setColor(BackgroundColor1);
	            
	            
	            Ext.getCmp('detailsSecondLevelBackgroundColor_id').setValue(AlarmShowStyle.Details.SecondLevel.BackgroundColor);
	            var BackgroundColor2=Ext.getCmp('detailsSecondLevelBackgroundColor_id').color;
	        	BackgroundColor2.a=AlarmShowStyle.Details.SecondLevel.Opacity;
            	Ext.getCmp('detailsSecondLevelBackgroundColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Details.SecondLevel.BackgroundColor,
            		opacity:AlarmShowStyle.Details.SecondLevel.Opacity
            	});
	        	Ext.getCmp('detailsSecondLevelBackgroundColor_id').setColor(BackgroundColor2);
	            
	            
	            Ext.getCmp('detailsThirdLevelBackgroundColor_id').setValue(AlarmShowStyle.Details.ThirdLevel.BackgroundColor);
	            var BackgroundColor3=Ext.getCmp('detailsThirdLevelBackgroundColor_id').color;
	        	BackgroundColor3.a=AlarmShowStyle.Details.ThirdLevel.Opacity;
            	Ext.getCmp('detailsThirdLevelBackgroundColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Details.ThirdLevel.BackgroundColor,
            		opacity:AlarmShowStyle.Details.ThirdLevel.Opacity
            	});
	        	Ext.getCmp('detailsThirdLevelBackgroundColor_id').setColor(BackgroundColor3);
	            

	            
	            Ext.getCmp('detailsNormalColor_id').setValue(AlarmShowStyle.Details.Normal.Color);
	            var Color0=Ext.getCmp('detailsNormalColor_id').color;
            	Ext.getCmp('detailsNormalColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Details.Normal.Color,
            	});
	            
	            Ext.getCmp('detailsFirstLevelColor_id').setValue(AlarmShowStyle.Details.FirstLevel.Color);
	            var Color1=Ext.getCmp('detailsFirstLevelColor_id').color;
            	Ext.getCmp('detailsFirstLevelColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Details.FirstLevel.Color,
            	});
	            
	            
	            Ext.getCmp('detailsSecondLevelColor_id').setValue(AlarmShowStyle.Details.SecondLevel.Color);
	            var Color2=Ext.getCmp('detailsSecondLevelColor_id').color;
            	Ext.getCmp('detailsSecondLevelColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Details.SecondLevel.Color,
            	});
	            
	            Ext.getCmp('detailsThirdLevelColor_id').setValue(AlarmShowStyle.Details.ThirdLevel.Color);
	            var Color3=Ext.getCmp('detailsThirdLevelColor_id').color;
            	Ext.getCmp('detailsThirdLevelColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Details.ThirdLevel.Color,
            	});
	            
	            Ext.getCmp('detailsNormalOpacity_id').setValue(AlarmShowStyle.Details.Normal.Opacity);
	            Ext.getCmp('detailsFirstLevelOpacity_id').setValue(AlarmShowStyle.Details.FirstLevel.Opacity);
	            Ext.getCmp('detailsSecondLevelOpacity_id').setValue(AlarmShowStyle.Details.SecondLevel.Opacity);
	            Ext.getCmp('detailsThirdLevelOpacity_id').setValue(AlarmShowStyle.Details.ThirdLevel.Opacity);
	            
	            
	          //初始化概览数据报警颜色
	        	Ext.getCmp('statisticsNormalBackgroundColor_id').setValue(AlarmShowStyle.Statistics.Normal.BackgroundColor);
	        	var BackgroundColor0=Ext.getCmp('statisticsNormalBackgroundColor_id').color;
	        	BackgroundColor0.a=AlarmShowStyle.Statistics.Normal.Opacity;
            	Ext.getCmp('statisticsNormalBackgroundColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Statistics.Normal.BackgroundColor,
            		opacity:AlarmShowStyle.Statistics.Normal.Opacity
            	});
	        	Ext.getCmp('statisticsNormalBackgroundColor_id').setColor(BackgroundColor0);
	        	
	            Ext.getCmp('statisticsFirstLevelBackgroundColor_id').setValue(AlarmShowStyle.Statistics.FirstLevel.BackgroundColor);
	            var BackgroundColor1=Ext.getCmp('statisticsFirstLevelBackgroundColor_id').color;
	        	BackgroundColor1.a=AlarmShowStyle.Statistics.FirstLevel.Opacity;
            	Ext.getCmp('statisticsFirstLevelBackgroundColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Statistics.FirstLevel.BackgroundColor,
            		opacity:AlarmShowStyle.Statistics.FirstLevel.Opacity
            	});
	        	Ext.getCmp('statisticsFirstLevelBackgroundColor_id').setColor(BackgroundColor1);
	            
	            
	            Ext.getCmp('statisticsSecondLevelBackgroundColor_id').setValue(AlarmShowStyle.Statistics.SecondLevel.BackgroundColor);
	            var BackgroundColor2=Ext.getCmp('statisticsSecondLevelBackgroundColor_id').color;
	        	BackgroundColor2.a=AlarmShowStyle.Statistics.SecondLevel.Opacity;
            	Ext.getCmp('statisticsSecondLevelBackgroundColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Statistics.SecondLevel.BackgroundColor,
            		opacity:AlarmShowStyle.Statistics.SecondLevel.Opacity
            	});
	        	Ext.getCmp('statisticsSecondLevelBackgroundColor_id').setColor(BackgroundColor2);
	            
	            
	            Ext.getCmp('statisticsThirdLevelBackgroundColor_id').setValue(AlarmShowStyle.Statistics.ThirdLevel.BackgroundColor);
	            var BackgroundColor3=Ext.getCmp('statisticsThirdLevelBackgroundColor_id').color;
	        	BackgroundColor3.a=AlarmShowStyle.Statistics.ThirdLevel.Opacity;
            	Ext.getCmp('statisticsThirdLevelBackgroundColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Statistics.ThirdLevel.BackgroundColor,
            		opacity:AlarmShowStyle.Statistics.ThirdLevel.Opacity
            	});
	        	Ext.getCmp('statisticsThirdLevelBackgroundColor_id').setColor(BackgroundColor3);
	            

	            
	            Ext.getCmp('statisticsNormalColor_id').setValue(AlarmShowStyle.Statistics.Normal.Color);
	            var Color0=Ext.getCmp('statisticsNormalColor_id').color;
            	Ext.getCmp('statisticsNormalColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Statistics.Normal.Color,
            	});
	            
	            Ext.getCmp('statisticsFirstLevelColor_id').setValue(AlarmShowStyle.Statistics.FirstLevel.Color);
	            var Color1=Ext.getCmp('statisticsFirstLevelColor_id').color;
            	Ext.getCmp('statisticsFirstLevelColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Statistics.FirstLevel.Color,
            	});
	            
	            
	            Ext.getCmp('statisticsSecondLevelColor_id').setValue(AlarmShowStyle.Statistics.SecondLevel.Color);
	            var Color2=Ext.getCmp('statisticsSecondLevelColor_id').color;
            	Ext.getCmp('statisticsSecondLevelColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Statistics.SecondLevel.Color,
            	});
	            
	            Ext.getCmp('statisticsThirdLevelColor_id').setValue(AlarmShowStyle.Statistics.ThirdLevel.Color);
	            var Color3=Ext.getCmp('statisticsThirdLevelColor_id').color;
            	Ext.getCmp('statisticsThirdLevelColor_id').inputEl.applyStyles({
            		background: '#'+AlarmShowStyle.Statistics.ThirdLevel.Color,
            	});
	            
	            Ext.getCmp('statisticsNormalOpacity_id').setValue(AlarmShowStyle.Statistics.Normal.Opacity);
	            Ext.getCmp('statisticsFirstLevelOpacity_id').setValue(AlarmShowStyle.Statistics.FirstLevel.Opacity);
	            Ext.getCmp('statisticsSecondLevelOpacity_id').setValue(AlarmShowStyle.Statistics.SecondLevel.Opacity);
	            Ext.getCmp('statisticsThirdLevelOpacity_id').setValue(AlarmShowStyle.Statistics.ThirdLevel.Opacity);
	        },
	        failure: function () {
	            Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
	        }
	    });
 }