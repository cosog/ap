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
            'orgAndUserInfoView > toolbar button[action=saveOrgCoordAction]': {
                click: saveOrgCoordInfo
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
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (saveDataAttrInfoWinFormId.getForm().isValid()) {
        saveDataAttrInfoWinFormId.getForm().submit({
            url: context + '/orgManagerController/doOrgAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: cosog.string.sendServer,
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('org_addwin_Id').close();
                Ext.getCmp("IframeView_Id").getStore().load();//右侧组织数刷新
                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.success + "</font>】," + cosog.string.dataInfo + "");
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
    	Ext.Msg.alert(cosog.string.ts, "<font color=red>*为必填项，请检查数据有效性.</font>");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

// 窗体上的修改按钮事件
function UpdateOrgDataInfoSubmitBtnForm() {
    var getUpdateDataInfoSubmitBtnFormId = Ext.getCmp("org_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (getUpdateDataInfoSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("org_addwin_Id").el.mask(cosog.string.updatewait).show();
        getUpdateDataInfoSubmitBtnFormId.getForm().submit({
            url: context + '/orgManagerController/doOrgEdit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("org_addwin_Id").getEl().unmask();
                Ext.getCmp('org_addwin_Id').close();
                Ext.getCmp("IframeView_Id").getStore().load();//右侧组织数刷新
                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.sucupate + "</font>】，" + cosog.string.dataInfo + "。");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(cosog.string.ts,
                        "<font color=red>SORRY！</font>" + cosog.string.updatefail + "");
                }
            },
            failure: function () {
                Ext.getCmp("org_addwin_Id").getEl().unmask();
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
            }
        });
    } else {
    	Ext.Msg.alert(cosog.string.ts, "<font color=red>*为必填项，请检查数据有效性.</font>");
    }
    return false;
};

// 赋值
SelectOrgDataAttrInfoGridPanel = function () {
    var dataattr_row = Ext.getCmp("OrgInfoTreeGridView_Id").getSelectionModel().getSelection();
    var orgId = dataattr_row[0].data.orgId;
    var orgName = dataattr_row[0].data.text;
    var orgMemo = dataattr_row[0].data.orgMemo;
    var orgSeq = dataattr_row[0].data.orgSeq;
    var orgParent = dataattr_row[0].data.orgParent;
    var orgParentName = dataattr_row[0].parentNode.data.text;
    if (orgParent == '0') {
        orgParentName = cosog.string.root;
    }
   
    Ext.getCmp('orgOrg_Id').setValue(orgId);
    var orgName_Parent_Id = Ext.getCmp('orgName_Parent_Id1');
    orgName_Parent_Id.setValue(orgParent);
    orgName_Parent_Id.setRawValue(orgParentName);
    Ext.getCmp('orgName_Parent_Id').setValue(orgParent);
    Ext.getCmp('orgName_Id').setValue(orgName);
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
		title: cosog.string.addOrg
	});
	orgInfoWindow.show();
	Ext.getCmp("addFormOrg_Id").show();
	Ext.getCmp("updateFormOrg_Id").hide();
	
	Ext.getCmp("orgName_Parent_Id").setValue(selectedOrgId);
	Ext.getCmp("orgName_Parent_Id1").setValue(selectedOrgId);
	Ext.getCmp("orgName_Parent_Id1").setRawValue(selectedOrgName);
	return false;
}
    // del to action
function delOrgInfo() {
    var org_panel = Ext.getCmp("OrgInfoTreeGridView_Id");
    var org_model = org_panel.getSelectionModel();
    var _record = org_model.getSelection();
    if (_record.length>0 && _record[0].get("orgId")>0) {
        // 提示是否删除数据
        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
        Ext.Msg.confirm(cosog.string.yesdel, '是否删除所选组织及其子节点', function (btn) {
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
        					Ext.Msg.alert('提示', "【<font color=blue>成功删除</font>】"+ result.deleteCount + "条数据信息。");
        				}
        				if (result.flag == false) {
        					Ext.Msg.alert('提示', "<font color=red>SORRY！删除失败。</font>");
        				}
        				Ext.getCmp("IframeView_Id").getStore().load();//右侧组织数刷新
        			},
        			failure : function() {
        				Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
        			}
        		});
            }
        });
    } else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
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
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
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

//保存坐标
function saveOrgCoordInfo() {
	var org_panel = Ext.getCmp("OrgInfoTreeGridView_Id");
	var m = org_panel.getStore().getUpdatedRecords();
	var store =org_panel.getStore();
	var jsonArray = [];
    if (m.length>0) {
    	Ext.each(m,function(item){
    		jsonArray.push(item.data);
    	});
    	Ext.Ajax.request({
    		method:'POST',
    		url:context + '/orgManagerController/doOrgUpdateCoord',
    		success:function(response) {
    			Ext.MessageBox.alert("信息","组织坐标更新成功",function(){
    				store.proxy.extraParams.tid = 0;
                    store.load();
                    store.modified = []; 
                    if(mapHelperOrg!=null){
						mapHelperOrg.clearOverlays();
						SaveBackMapData(mapHelperOrg,"org",m_BackDefaultZoomLevel);
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
	        title: cosog.string.addUser
	    });
	    UserInfoWindow.show();
	    Ext.getCmp("userWinOgLabel_Id").setHtml("用户将添加到【<font color=red>"+selectedOrgName+"</font>】下,请确认<br/>&nbsp;");
	    Ext.getCmp("userWinOgLabel_Id").show();
	    Ext.getCmp("addFormUser_Id").show();
	    Ext.getCmp("updateFormUser_Id").hide();
	    Ext.getCmp('userOrgid_Id').setValue(selectedOrgId);
	    Ext.getCmp("userPwd_Id").setValue("123456");
	    Ext.getCmp("userPwdAgain_Id").setValue("123456");
	}else{
		Ext.MessageBox.alert("信息","请先添加组织");
	}
	
    return false;
};

function modifyUserInfo() {
	var user_panel = Ext.getCmp("UserInfoGridPanel_Id");
    var user_model = user_panel.getSelectionModel();
    var _record = user_model.getSelection();
    if (_record.length>0) {
    	var UserUpdateInfoWindow = Ext.create("AP.view.orgAndUser.UserPanelInfoWindow", {
            title: cosog.string.editUser
        });
        UserUpdateInfoWindow.show();
        Ext.getCmp("addFormUser_Id").hide();
        Ext.getCmp("updateFormUser_Id").show();
        SelectedUserDataAttrInfoGridPanel();
    }else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
    return false;
};

SelectedUserDataAttrInfoGridPanel = function () {
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
//    	Ext.getCmp('userType_Id1').setReadOnly(true);
//    	Ext.getCmp('userEnableRadio1_Id').setReadOnly(true);
//    	Ext.getCmp('userEnableRadio0_Id').setReadOnly(true);
    	
    	Ext.getCmp('userType_Id1').disable();
    	Ext.getCmp('userEnableRadioGroup_Id').disable();
//    	Ext.getCmp('userEnableRadio1_Id').disable();
//    	Ext.getCmp('userEnableRadio0_Id').disable();
    }
};

function delUserInfo() {
    var user_panel = Ext.getCmp("UserInfoGridPanel_Id");
    var user_model = user_panel.getSelectionModel();
    var _record = user_model.getSelection();
    if (_record.length>0) {
        // 提示是否删除数据
        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
        Ext.Msg.confirm(cosog.string.yesdel, cosog.string.yesdeldata, function (btn) {
            if (btn == "yes") {
//                ExtDel_ObjectInfo("UserInfoGridPanel_Id", _record,"userNo", delUrl);
                
//                grid_id, row, data_id, action_name
                var deletejson = [];
                var noDelete=[];
            	Ext.Array.each(_record, function(name, index, countriesItSelf) {
            		if(_record[index].get("userNo")>0 && parseInt(_record[index].get("userNo"))!=parseInt(user_)){
            			deletejson.push(_record[index].get("userNo"));
            		}else if(_record[index].get("userNo")>0 && parseInt(_record[index].get("userNo"))==parseInt(user_)){
            			noDelete.push(_record[index].get("userNo"));
            		}		
            	});
            	if(deletejson.length>0){
            		var delparamsId = "" + deletejson.join(",");
            		Ext.Ajax.request({
            			url : context + '/userManagerController/doUserBulkDelete',
            			method : "POST",
            			params : {
            				paramsId : delparamsId
            			},
            			success : function(response) {
            				var result = Ext.JSON.decode(response.responseText);
            				if (result.flag == true) {
            					Ext.Msg.alert('提示', "【<font color=blue>成功删除</font>】"+ deletejson.length + "条数据信息。");
            				}
            				if (result.flag == false) {
            					Ext.Msg.alert('提示', "<font color=red>SORRY！删除失败。</font>");
            				}
            				Ext.getCmp("UserInfoGridPanel_Id").getStore().load();
            			},
            			failure : function() {
            				Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
            			}
            		});
            	}else if(noDelete.length>0){
            		Ext.Msg.alert('提示', "<font color=red>不能删除当前登录用户。</font>");
            	}else{
            		Ext.Msg.alert('提示', "<font color=red>所选属性无效，删除失败。</font>");
            	}
                
                
            }
        });
    } else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
    return false;
};

//窗体创建按钮事件
var SaveUserDataInfoSubmitBtnForm = function () {
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
    } else {
    	Ext.Msg.alert(cosog.string.ts, "<font color=red>*为必填项，请检查数据有效性.</font>");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

//窗体上的修改按钮事件
function UpdateUserDataInfoSubmitBtnForm() {
    var getUpdateDataInfoSubmitBtnFormId = Ext.getCmp("user_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (getUpdateDataInfoSubmitBtnFormId.getForm().isValid()) {
        getUpdateDataInfoSubmitBtnFormId.getForm().submit({
            url: context + '/userManagerController/doUserEdit',
            clientValidation: false, // 进行客户端验证
            method: "POST",
            waitMsg: cosog.string.updatewait,
            waitTitle: 'Please Wait...',
            success: function (response, action) {
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
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
            }
        });
    } else {
    	Ext.Msg.alert(cosog.string.ts, "<font color=red>*为必填项，请检查数据有效性.</font>");
    }
    return false;
};