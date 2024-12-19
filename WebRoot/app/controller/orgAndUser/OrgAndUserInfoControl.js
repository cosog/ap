Ext.define('AP.controller.orgAndUser.OrgAndUserInfoControl', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'orgAndUserInfoView',
        selector: 'orgAndUserInfoView'
   }],
    init: function () {
        this.control({
            'orgAndUserInfoView > toolbar button[action=addOrgAction]': {
                click: addOrgInfo
            },
            'orgAndUserInfoView > toolbar button[action=delOrgAction]': {
                click: delOrgInfo
            },
            'orgAndUserInfoView > toolbar button[action=editOrgInfoAction]': {
                click: modifyOrgInfo
            },
            'orgAndUserInfoView': {
                itemdblclick: modifyOrgInfo
            }
        })
    }
});

// 窗体创建按钮事件
var SaveOrgDataInfoSubmitBtnForm = function () {
    var saveDataAttrInfoWinFormId = Ext.getCmp("org_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (saveDataAttrInfoWinFormId.getForm().isValid()) {
        saveDataAttrInfoWinFormId.getForm().submit({
            url: context + '/orgManagerController/doOrgAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: cosog.string.sendServer,
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('org_addwin_Id').close();
                if (action.result.msg == true) {
                	Ext.getCmp("IframeView_Id").getStore().load();//右侧组织数刷新
//                	Ext.getCmp("OrgInfoTreeGridView_Id").getStore().load();
                	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.addSuccessfully + "</font>");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.addFailure);
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font> 】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

// 窗体上的修改按钮事件
function UpdateOrgDataInfoSubmitBtnForm() {
    var getUpdateDataInfoSubmitBtnFormId = Ext.getCmp("org_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (getUpdateDataInfoSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("org_addwin_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
        getUpdateDataInfoSubmitBtnFormId.getForm().submit({
            url: context + '/orgManagerController/doOrgEdit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("org_addwin_Id").getEl().unmask();
                Ext.getCmp('org_addwin_Id').close();
                Ext.getCmp("IframeView_Id").getStore().load();//右侧组织数刷新
                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.updateSuccessfully + "</font>");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip,loginUserLanguageResource.updateFailure);
                }
            },
            failure: function () {
                Ext.getCmp("org_addwin_Id").getEl().unmask();
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + " </font>】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
    }
    return false;
};

// 赋值
SelectOrgDataAttrInfoGridPanel = function () {
    var dataattr_row = Ext.getCmp("OrgInfoTreeGridView_Id").getSelectionModel().getSelection();
    var orgId = dataattr_row[0].data.orgId;
    var orgName = dataattr_row[0].data.text;
    
    var orgName_zh_CN = dataattr_row[0].data.orgName_zh_CN;
    var orgName_en = dataattr_row[0].data.orgName_en;
    var orgName_ru = dataattr_row[0].data.orgName_ru;
    
    
    var orgMemo = dataattr_row[0].data.orgMemo;
    var orgSeq = dataattr_row[0].data.orgSeq;
    var orgParent = dataattr_row[0].data.orgParent;
    var orgParentName = dataattr_row[0].parentNode.data.text;
    if (orgParent == '0') {
        orgParentName = loginUserLanguageResource.rootNode;
    }
   
    Ext.getCmp('orgOrg_Id').setValue(orgId);
    Ext.getCmp('orgName_Parent_Id').setValue(orgParent);
    Ext.getCmp('orgName_zh_CN_Id').setValue(orgName_zh_CN);
    Ext.getCmp('orgName_en_Id').setValue(orgName_en);
    Ext.getCmp('orgName_ru_Id').setValue(orgName_ru);
    Ext.getCmp('orgMemo_Id').setValue(orgMemo);
    Ext.getCmp('orgSeq_Id').setValue(orgSeq);
};
// open win
function addOrgInfo() {
	var selectedOrgName="";
	var selectedOrgId="";
	var orgTreeStore = Ext.getCmp("OrgInfoTreeGridView_Id").getStore();
	var count=orgTreeStore.getCount();
	var orgTreeSelection = Ext.getCmp("OrgInfoTreeGridView_Id").getSelectionModel().getSelection();
	if (orgTreeSelection.length > 0) {
		selectedOrgName=orgTreeSelection[0].data.text;
		selectedOrgId=orgTreeSelection[0].data.orgId;
	} else {
		if(count>0){
			selectedOrgName=orgTreeStore.getAt(0).data.text;
			selectedOrgId=orgTreeStore.getAt(0).data.orgId;
		}
	}
	var win_Obj = Ext.getCmp("org_addwin_Id")
	if (win_Obj != undefined) {
		win_Obj.destroy();
	}
	var orgInfoWindow = Ext.create("AP.view.orgAndUser.OrgInfoWindow", {
		title: loginUserLanguageResource.addOrg
	});
	orgInfoWindow.show();
	Ext.getCmp("addFormOrg_Id").show();
	Ext.getCmp("updateFormOrg_Id").hide();
	
	Ext.getCmp("orgName_Parent_Id").setValue(selectedOrgId);
	return false;
}
    // del to action
function delOrgInfo() {
    var org_panel = Ext.getCmp("OrgInfoTreeGridView_Id");
    var org_model = org_panel.getSelectionModel();
    var _record = org_model.getSelection();
    if (_record.length>0 && _record[0].get("orgId")>0) {
        // 提示是否删除数据
        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.cancel;
        Ext.Msg.confirm(loginUserLanguageResource.confirmDelete, '是否删除所选组织及其子节点', function (btn) {
            if (btn == "yes") {
        		Ext.Ajax.request({
        			url : context + '/orgManagerController/doOrgBulkDelete',
        			method : "POST",
        			params : {
        				paramsId : _record[0].get("orgId")
        			},
        			success : function(response) {
        				var result = Ext.JSON.decode(response.responseText);
        				if (result.flag == true) {
        					Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=blue>成功删除</font>】"+ result.deleteCount + "条数据信息。");
        				}
        				if (result.flag == false) {
        					Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>删除失败。</font>");
        				}
        				Ext.getCmp("IframeView_Id").getStore().load();//右侧组织数刷新
        			},
        			failure : function() {
        				Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+" </font>】"+loginUserLanguageResource.contactAdmin);
        			}
        		});
            }
        });
    } else {
        Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
    }
};

function modifyOrgInfo() {
	var org_panel = Ext.getCmp("OrgInfoTreeGridView_Id");
    var org_model = org_panel.getSelectionModel();
    var _record = org_model.getSelection();
    if (_record.length>0) {
    	var win_Obj = Ext.getCmp("org_addwin_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var orgUpdateInfoWindow = Ext.create("AP.view.orgAndUser.OrgInfoWindow", {
            title: cosog.string.updateOrg
        });
        orgUpdateInfoWindow.show();
        Ext.getCmp("addFormOrg_Id").hide();
        Ext.getCmp("updateFormOrg_Id").show();
        SelectOrgDataAttrInfoGridPanel();
    }else {
        Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
    }
    
    return false;
};

//创建设置组织坐标窗口
function openSetOrgCoordWin() {
	var win_Obj = Ext.getCmp("orgCoordSetWindow_Id")
    if (win_Obj != undefined) {
    	win_Obj.destroy();
    }
	var OrgCoordSetWindow = Ext.create("AP.view.org.OrgCoordSetWindow", {
		title: cosog.string.setCoord
		});
	OrgCoordSetWindow.show();
	$(function () {
        //初始化地图
		var div=Ext.getCmp("orgCoordSetDiv_Id");
		var div2=$("#orgCoordSetDiv_Id");
        mapHelper = MapHelper.createNew("orgCoordSetDiv_Id", m_DefaultPosition, m_DefaultZoomLevel, false, 1000);
        SetMapLocation(39.904, 116.404, 19);
        //获取组织信息
        SaveOrgData();
        //给地图添加单击事件
        mapHelper.addEventListener(mapHelper.getMap(), "click", mapOrgClicked);
    });
};

function addUserInfo() {
	var selectedOrgName="";
	var selectedOrgId="";
	var orgTreeStore = Ext.getCmp("OrgInfoTreeGridView_Id").getStore();
	var count=orgTreeStore.getCount();
	var orgTreeSelection = Ext.getCmp("OrgInfoTreeGridView_Id").getSelectionModel().getSelection();
	if (orgTreeSelection.length > 0) {
		selectedOrgName=foreachAndSearchOrgAbsolutePath(orgTreeStore.data.items,orgTreeSelection[0].data.orgId);
		selectedOrgId=orgTreeSelection[0].data.orgId;
	} else {
		if(count>0){
			selectedOrgName=orgTreeStore.getAt(0).data.text;
			selectedOrgId=orgTreeStore.getAt(0).data.orgId;
		}
	}
	if(selectedOrgId!=""){
		var UserInfoWindow = Ext.create("AP.view.orgAndUser.UserPanelInfoWindow", {
	        title: loginUserLanguageResource.addUser
	    });
	    UserInfoWindow.show();
	    Ext.getCmp("userWinOgLabel_Id").setHtml(loginUserLanguageResource.owningOrg+"【<font color=red>"+selectedOrgName+"</font>】,"+loginUserLanguageResource.pleaseConfirm+"<br/>&nbsp;");
	    Ext.getCmp("userWinOgLabel_Id").show();
	    Ext.getCmp("addFormUser_Id").show();
	    Ext.getCmp("updateFormUser_Id").hide();
	    Ext.getCmp('userOrgid_Id').setValue(selectedOrgId);
	    Ext.getCmp("userPwd_Id").setValue("123456");
	    Ext.getCmp("userPwdAgain_Id").setValue("123456");
	}else{
		Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.addOrgFirst);
	}
	
    return false;
};

function modifyUserInfo() {
	var user_panel = Ext.getCmp("UserInfoGridPanel_Id");
    var user_model = user_panel.getSelectionModel();
    var _record = user_model.getSelection();
    if (_record.length>0) {
    	var UserEditPasswordWindow = Ext.create("AP.view.orgAndUser.UserEditPasswordWindow");
    	UserEditPasswordWindow.show();
    	SelectedUserDataAttrInfoGridPanel();
    }else {
        Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
    }
    return false;
};

SelectedUserDataAttrInfoGridPanel = function () {
    var dataattr_row = Ext.getCmp("UserInfoGridPanel_Id").getSelectionModel().getLastSelected();
    var userNo = dataattr_row.data.userNo;
    var userName = dataattr_row.data.userName;
    var userId = dataattr_row.data.userId;
    
    Ext.getCmp('userEditPassword_UserNo_Id').setValue(userNo);
    Ext.getCmp('userEditPassword_UserName_Id').setValue(userName);
    Ext.getCmp('userEditPassword_UserId_Id').setValue(userId);
};

SelectedUserDataAttrInfoGridPanel2 = function () {
    var dataattr_row = Ext.getCmp("UserInfoGridPanel_Id").getSelectionModel().getLastSelected();
    var userNo = dataattr_row.data.userNo;
    var userOrgid = dataattr_row.data.userOrgid;
    var orgName = dataattr_row.data.orgName;
    var userName = dataattr_row.data.userName;
    var userId = dataattr_row.data.userId;
    var userPwd = dataattr_row.data.userPwd;
    var userQuickLogin = dataattr_row.data.userQuickLogin;
    var userQuickLoginName = dataattr_row.data.userQuickLoginName;
    var receiveSMS = dataattr_row.data.receiveSMS;
    var receiveMail = dataattr_row.data.receiveMail;
    var userEnable = dataattr_row.data.userEnable;
    var userType = dataattr_row.data.userType;
    var userTypeName = dataattr_row.data.userTypeName;
    var userPhone = dataattr_row.data.userPhone;
    var userInEmail = dataattr_row.data.userInEmail;
    var userRegtime = dataattr_row.data.userRegtime;
    Ext.getCmp('userNo_Id').setValue(userNo);
    Ext.getCmp('userOrgid_Id').setValue(userOrgid);
    Ext.getCmp('userName_Id').setValue(userName);
    Ext.getCmp('userId_Id').setValue(userId);
    
    if(userQuickLogin==1){
    	Ext.getCmp('userQuickLoginRadio1_Id').setValue(true);
    }else{
    	Ext.getCmp('userQuickLoginRadio0_Id').setValue(true);
    }
    
    if(receiveSMS==1){
    	Ext.getCmp('userReceiveSMSRadio1_Id').setValue(true);
    }else{
    	Ext.getCmp('userReceiveSMSRadio0_Id').setValue(true);
    }
    
    if(receiveMail==1){
    	Ext.getCmp('userReceiveMailRadio1_Id').setValue(true);
    }else{
    	Ext.getCmp('userReceiveMailRadio0_Id').setValue(true);
    }
    
    if(userEnable==1){
    	Ext.getCmp('userEnableRadio1_Id').setValue(true);
    }else{
    	Ext.getCmp('userEnableRadio0_Id').setValue(true);
    }
    
//    Ext.getCmp('userQuidkLoginValue_Id').setValue(userQuickLogin);
    Ext.getCmp('userType_Id').setValue(userType);
    Ext.getCmp('userType_Id1').setValue(userType);
    Ext.getCmp('userType_Id1').setRawValue(userTypeName);
    Ext.getCmp('userPhone_Id').setValue(userPhone);
    Ext.getCmp('userInEmail_Id').setValue(userInEmail);
    var userRegTimeInput = Ext.getCmp('userRegTime_Id')
    var traininguserRegtime = new Date(Date.parse(userRegtime.replace(/-/g, "/")));
    userRegTimeInput.format = 'Y-m-d H:i:s';
    userRegTimeInput.setValue(traininguserRegtime);
    if(parseInt(user_)==parseInt(userNo)){//如果是当前用户，不能修改自己的角色和使能状态
    	Ext.getCmp('userType_Id1').disable();
    	Ext.getCmp('userEnableRadioGroup_Id').disable();
    }
};

function delUserInfo() {
    var user_panel = Ext.getCmp("UserInfoGridPanel_Id");
    var user_model = user_panel.getSelectionModel();
    var _record = user_model.getSelection();
    if (_record.length>0) {
        // 提示是否删除数据
        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.cancel;
        Ext.Msg.confirm(loginUserLanguageResource.confirmDelete, loginUserLanguageResource.confirmDeleteData, function (btn) {
            if (btn == "yes") {
//                ExtDel_ObjectInfo("UserInfoGridPanel_Id", _record,"userNo", delUrl);
                
//                grid_id, row, data_id, action_name
                var deletejson = [];
                var noDelete=[];
                var deleteUserId=[];
            	Ext.Array.each(_record, function(name, index, countriesItSelf) {
            		if(_record[index].get("userNo")>0 && parseInt(_record[index].get("userNo"))!=parseInt(user_)){
            			deletejson.push(_record[index].get("userNo"));
            			deleteUserId.push(_record[index].get("userId"));
            		}else if(_record[index].get("userNo")>0 && parseInt(_record[index].get("userNo"))==parseInt(user_)){
            			noDelete.push(_record[index].get("userNo"));
            		}		
            	});
            	if(deletejson.length>0){
            		var delparamsId = "" + deletejson.join(",");
            		var delUserId = "" + deleteUserId.join(",");
            		Ext.Ajax.request({
            			url : context + '/userManagerController/doUserBulkDelete',
            			method : "POST",
            			params : {
            				paramsId : delparamsId,
            				delUserId : delUserId
            			},
            			success : function(response) {
            				var result = Ext.JSON.decode(response.responseText);
            				if (result.flag == true) {
            					Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=blue>成功删除</font>】"+ deletejson.length + "条数据信息。");
            				}
            				if (result.flag == false) {
            					Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>删除失败。</font>");
            				}
            				Ext.getCmp("UserInfoGridPanel_Id").getStore().load();
            			},
            			failure : function() {
            				Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
            			}
            		});
            	}else if(noDelete.length>0){
            		Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>不能删除当前登录用户。</font>");
            	}else{
            		Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>所选属性无效，删除失败。</font>");
            	}
                
                
            }
        });
    } else {
        Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
    }
    return false;
};

function delUserInfoByGridBtn(record) {
//	alert(record.data.userNo);
//  record.drop();
	Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
  Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.cancel;
  Ext.Msg.confirm(loginUserLanguageResource.confirmDelete, '是否删除用户'+record.get("userId"), function (btn) {
      if (btn == "yes") {
          var deletejson = [];
          var noDelete=[];
          var deleteUserId=[];
      	
          if(record.get("userNo")>0 && parseInt(record.get("userNo"))!=parseInt(user_)){
  			deletejson.push(record.get("userNo"));
  			deleteUserId.push(record.get("userId"));
  		}else if(record.get("userNo")>0 && parseInt(record.get("userNo"))==parseInt(user_)){
  			noDelete.push(record.get("userNo"));
  		}
          
      	if(deletejson.length>0){
      		var delparamsId = "" + deletejson.join(",");
      		var delUserId = "" + deleteUserId.join(",");
      		Ext.Ajax.request({
      			url : context + '/userManagerController/doUserBulkDelete',
      			method : "POST",
      			params : {
      				paramsId : delparamsId,
      				delUserId : delUserId
      			},
      			success : function(response) {
      				var result = Ext.JSON.decode(response.responseText);
      				if (result.flag == true) {
      					Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=blue>成功删除</font>】"+ deletejson.length + "条数据信息。");
      				}
      				if (result.flag == false) {
      					Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>删除失败。</font>");
      				}
      				Ext.getCmp("UserInfoGridPanel_Id").getStore().load();
      			},
      			failure : function() {
      				Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
      			}
      		});
      	}else if(noDelete.length>0){
      		Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>不能删除当前登录用户。</font>");
      	}else{
      		Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>所选属性无效，删除失败。</font>");
      	}
      }
  });
}

function updateUserInfoByGridBtn(record) {

    var userNo=record.get("userNo");
    var userName=record.get("userName");
    var userId=record.get("userId");
    var userTypeName=record.get("userTypeName");
    var userPhone=record.get("userPhone");
    var userInEmail=record.get("userInEmail");
    var userQuickLoginName=record.get("userQuickLoginName");
    var receiveSMSName=record.get("receiveSMSName");
    var receiveMailName=record.get("receiveMailName");
    var userEnableName=record.get("userEnableName");
    var userLanguageName=record.get("userLanguageName");
	
    Ext.Ajax.request({
		url : context + '/userManagerController/updateUserInfo',
		method : "POST",
		params : {
			userNo : userNo,
			userName : userName,
			userId : userId,
			userTypeName : userTypeName,
			userPhone : userPhone,
			userInEmail : userInEmail,
			userQuickLoginName : userQuickLoginName,
			receiveSMSName : receiveSMSName,
			receiveMailName : receiveMailName,
			userEnableName : userEnableName,
			userLanguageName : userLanguageName
		},
		success : function(response) {
			var result = Ext.JSON.decode(response.responseText);
			if (result.success==true && result.flag == true) {
				Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
			}else if (result.success==true && result.flag == false) {
				Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
			}else {
				Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
			}
			Ext.getCmp("UserInfoGridPanel_Id").getStore().load();
		},
		failure : function() {
			Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
		}
	});
}

//窗体创建按钮事件
var SaveUserDataInfoSubmitBtnForm = function () {
    var saveDataAttrInfoWinFormId = Ext.getCmp("user_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
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
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.addSuccessfully + "</font>");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.addFailure);

                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font> 】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

//窗体上的修改按钮事件
function UpdateUserDataInfoSubmitBtnForm() {
    var getUpdateDataInfoSubmitBtnFormId = Ext.getCmp("user_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (getUpdateDataInfoSubmitBtnFormId.getForm().isValid()) {
        getUpdateDataInfoSubmitBtnFormId.getForm().submit({
            url: context + '/userManagerController/doUserEdit',
            clientValidation: false, // 进行客户端验证
            method: "POST",
            waitMsg: loginUserLanguageResource.updateWait+'...',
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('user_addwin_Id').close();
                Ext.getCmp("UserInfoGridPanel_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.updateSuccessfully + "</font>");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.updateFailure);
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + " </font>】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
    }
    return false;
};

//修改密码窗体上的修改按钮事件
function EditUserPasswordSubmitBtnForm() {
    var getUpdateDataInfoSubmitBtnFormId = Ext.getCmp("userEditPasswordWindow_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (getUpdateDataInfoSubmitBtnFormId.getForm().isValid()) {
        getUpdateDataInfoSubmitBtnFormId.getForm().submit({
            url: context + '/userManagerController/doUserEditPassword',
            clientValidation: false, // 进行客户端验证
            method: "POST",
            waitMsg: loginUserLanguageResource.updateWait+'...',
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('userEditPasswordWindow_Id').close();
                if (action.result.flag == true) {
                	Ext.getCmp("UserInfoGridPanel_Id").getStore().load();
                	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.updateSuccessfully + "</font>");
                }else if (action.result.flag == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip,loginUserLanguageResource.updateFailure);
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + " </font>】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
    }
    return false;
};