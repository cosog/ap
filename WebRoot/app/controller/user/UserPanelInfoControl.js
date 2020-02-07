Ext.define('AP.controller.user.UserPanelInfoControl', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'userInfoGridViewPanel',
        selector: 'userInfoGridViewPanel'
   }],
    init: function () {
        this.control({
            'userInfoGridViewPanel > toolbar button[action=addUserAction]': {
                click: addUserInfo
            },
            'userInfoGridViewPanel > toolbar button[action=delUserAction]': {
                click: delUserInfo
            },
            'userInfoGridViewPanel > toolbar button[action=editUserInfoAction]': {
                click: modifyUserInfo
            },
            'userInfoGridViewPanel': {
                itemdblclick: modifyUserInfo
            }
        })
    }
});

// 窗体创建按钮事件
var SaveDataInfoSubmitBtnForm = function () {
    var saveDataAttrInfoWinFormId = Ext.getCmp("user_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (saveDataAttrInfoWinFormId.getForm().isValid()) {
        saveDataAttrInfoWinFormId.getForm().submit({
            url: context + '/userManagerController/doUserAdd',
            clientValidation: false, // 进行客户端验证
            method: "POST",
            waitMsg: cosog.string.sendServer,
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('user_addwin_Id').close();
                Ext.getCmp("UserInfoGridPanel_Id").getStore().load();
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
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

// 窗体上的修改按钮事件
function UpdateDataInfoSubmitBtnForm() {
    var getUpdateDataInfoSubmitBtnFormId = Ext.getCmp("user_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (getUpdateDataInfoSubmitBtnFormId.getForm().isValid()) {
//        Ext.getCmp("user_addwin_Id").el.mask(cosog.string.updatewait).show();
        getUpdateDataInfoSubmitBtnFormId.getForm().submit({
            url: context + '/userManagerController/doUserEdit',
            clientValidation: false, // 进行客户端验证
            method: "POST",
            waitMsg: cosog.string.updatewait,
            waitTitle: 'Please Wait...',
            success: function (response, action) {
//                Ext.getCmp("user_addwin_Id").getEl().unmask();
                Ext.getCmp('user_addwin_Id').close();
                Ext.getCmp("UserInfoGridPanel_Id").getStore().load();

                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.sucupate + "</font>】，" + cosog.string.dataInfo + "。");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(cosog.string.ts,
                        "<font color=red>SORRY！</font>" + cosog.string.updatefail + "。");
                }
            },
            failure: function () {
//                Ext.getCmp("user_addwin_Id").getEl().unmask();
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
            }
        });
    }
    return false;
};

// 复值
SelectDataAttrInfoGridPanel = function () {
    Ext.getCmp("UserInfoGridPanel_Id").getStore().load();
    var UserInfoGridPanel_Id = Ext.getCmp("UserInfoGridPanel_Id");
    var model_ = UserInfoGridPanel_Id.getSelectionModel();
    var dataattr_row = model_.getLastSelected();
    var userNo = dataattr_row.data.userNo;
    var userOrgid = dataattr_row.data.userOrgid;
    var orgName = dataattr_row.data.orgName;
    var userName = dataattr_row.data.userName;
    var userId = dataattr_row.data.userId;
    var userPwd = dataattr_row.data.userPwd;
    var userQuickLogin = dataattr_row.data.userQuickLogin;
    var userQuickLoginName = dataattr_row.data.userQuickLoginName;
    var userType = dataattr_row.data.userType;
    var userTypeName = dataattr_row.data.userTypeName;
    var userTitle = dataattr_row.data.userTitle;
    var userTitleName = dataattr_row.data.userTitleName;
    var userPhone = dataattr_row.data.userPhone;
    var userInEmail = dataattr_row.data.userInEmail;
    var userRegtime = dataattr_row.data.userRegtime;
    Ext.getCmp('userNo_Id').setValue(userNo);
    var userOrgid_Id1 = Ext.getCmp('userOrgid_Id1');
    var userOrgid_Id = Ext.getCmp('userOrgid_Id');
    userOrgid_Id1.setValue(userOrgid);
    userOrgid_Id1.setRawValue(orgName);
    userOrgid_Id.setValue(userOrgid);
    Ext.getCmp('userName_Id').setValue(userName);
    Ext.getCmp('userId_Id').setValue(userId);
    Ext.getCmp('userPwd_Id').setValue(userPwd);
    Ext.getCmp('userQuidkLoginComboxfield_Id').setValue(userQuickLogin);
    Ext.getCmp('userQuidkLoginComboxfield_Id').setRawValue(userQuickLoginName);
    Ext.getCmp('userQuidkLoginValue_Id').setValue(userQuickLogin);
    Ext.getCmp('userHiddenPwd_Id').setValue(userPwd);
    Ext.getCmp('userTitle_Id').setValue(userTitle);
    Ext.getCmp('userTitle_Id1').setValue(userTitle);
    Ext.getCmp('userTitle_Id1').setRawValue(userTitleName);
    var user_type = Ext.getCmp('userType_Id');
    var user_type1 = Ext.getCmp('userType_Id1');
    user_type.setValue(userType);
    user_type1.setValue(userType);
    user_type1.setRawValue(userTypeName);
    Ext.getCmp('userPhone_Id').setValue(userPhone);
    Ext.getCmp('userInEmail_Id').setValue(userInEmail);
    var userRegTimeInput = Ext.getCmp('userRegTime_Id')
    var traininguserRegtime = new Date(Date.parse(userRegtime.replace(/-/g, "/")));
    userRegTimeInput.format = 'Y-m-d H:i:s';
    userRegTimeInput.setValue(traininguserRegtime);
};

// open win
function addUserInfo() {
        var UserInfoWindow = Ext.create("AP.view.user.UserPanelInfoWindow", {
            title: cosog.string.addUser
        });
        UserInfoWindow.show();
        Ext.getCmp("addFormUser_Id").show();
        Ext.getCmp("updateFormUser_Id").hide();
        Ext.getCmp('userOrgid_Id').setValue(userOrg_Id);
        return false;
    }
    // del to action
function delUserInfo() {
    var user_panel = Ext.getCmp("UserInfoGridPanel_Id");
    var user_model = user_panel.getSelectionModel();
    var _record = user_model.getSelection();
    var delUrl = context + '/userManagerController/doUserBulkDelete'
    if (_record.length>0) {
        // 提示是否删除数据
        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
        Ext.Msg.confirm(cosog.string.yesdel, cosog.string.yesdeldata, function (btn) {
            if (btn == "yes") {
                ExtDel_ObjectInfo("UserInfoGridPanel_Id", _record,"userNo", delUrl);
            }
        });
    } else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
    return false;
}

function modifyUserInfo() {
	var user_panel = Ext.getCmp("UserInfoGridPanel_Id");
    var user_model = user_panel.getSelectionModel();
    var _record = user_model.getSelection();
    if (_record.length>0) {
    	var UserUpdateInfoWindow = Ext.create("AP.view.user.UserPanelInfoWindow", {
            title: cosog.string.editUser
        });
        UserUpdateInfoWindow.show();
        Ext.getCmp("addFormUser_Id").hide();
        Ext.getCmp("updateFormUser_Id").show();
        SelectDataAttrInfoGridPanel();
    }else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
    return false;
}