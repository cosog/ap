Ext.define('AP.controller.org.OrgInfoControl', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'orgInfoTreeGridView',
        selector: 'orgInfoTreeGridView'
   }],
    init: function () {
        this.control({
            'orgInfoTreeGridView > toolbar button[action=addOrgAction]': {
                click: addOrgInfo
            },
            'orgInfoTreeGridView > toolbar button[action=delOrgAction]': {
                click: delOrgInfo
            },
            'orgInfoTreeGridView > toolbar button[action=editOrgInfoAction]': {
                click: modifyOrgInfo
            },
            'orgInfoTreeGridView > toolbar button[action=saveOrgCoordAction]': {
                click: saveOrgCoordInfo
            },
            'orgInfoTreeGridView': {
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
//              Ext.getCmp("OrgInfoTreeGridView_Id").getStore().load();
                
                
//                store.proxy.extraParams.tid = 0;
//                store.load();
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
        Ext.Msg.alert(cosog.string.ts, "<font color=red>SORRY！" + cosog.string.validdata + ".</font>。");
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
//                var store=Ext.getCmp("OrgInfoTreeGridView_Id").getStore();
//                store.proxy.extraParams.tid = 0;
//                store.load();
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
    }
    return false;
};

// 赋值
SelectOrgDataAttrInfoGridPanel = function () {
    var dataattr_row = Ext.getCmp("OrgInfoTreeGridView_Id").getSelectionModel().getSelection();
    var orgId = dataattr_row[0].data.orgId;
    var orgName = dataattr_row[0].data.text;
    var orgMemo = dataattr_row[0].data.orgMemo;
    var orgLevel = dataattr_row[0].data.orgLevel;
    var orgCode = dataattr_row[0].data.orgCode;
    var orgParent = dataattr_row[0].data.orgParent;
    var orgParentName = dataattr_row[0].parentNode.data.text;
    if (orgParent == '0') {
        orgParentName = cosog.string.root;
    }
    var orgType = dataattr_row[0].data.orgType;
    var orgTypeName = dataattr_row[0].data.orgTypeName;
    var orgCoordX = dataattr_row[0].data.orgCoordX;
    var orgCoordY = dataattr_row[0].data.orgCoordY;
    var showLevel = dataattr_row[0].data.showLevel;
    Ext.getCmp('orgOrg_Id').setValue(orgId);
    Ext.getCmp('orgLevel_Id').setValue(orgLevel);
    var orgName_Parent_Id = Ext.getCmp('orgName_Parent_Id1');
    orgName_Parent_Id.setValue(orgParent);
    orgName_Parent_Id.setRawValue(orgParentName);
    Ext.getCmp('orgName_Parent_Id').setValue(orgParent);
    Ext.getCmp('orgName_Id').setValue(orgName);
    Ext.getCmp('orgCode_Id').setValue(orgCode);
    Ext.getCmp('orgMemo_Id').setValue(orgMemo);
    Ext.getCmp('org_Type_Id').setValue(orgType);
    Ext.getCmp("org_Type_Id").setRawValue(orgTypeName);
    Ext.getCmp("orgCoordX_Id").setRawValue(orgCoordX);
    Ext.getCmp("orgCoordY_Id").setRawValue(orgCoordY);
    Ext.getCmp("orgShowLevel_Id").setRawValue(showLevel);
};
// open win
function addOrgInfo() {
        var win_Obj = Ext.getCmp("org_addwin_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var orgInfoWindow = Ext.create("AP.view.org.OrgInfoWindow", {
            title: cosog.string.addOrg
        });
        orgInfoWindow.show();
//        var org=Ext.getCmp("IframeView_Id").getStore();
//        if(userOrg_Id!=0&&org.data.items.length>0){
//        	var parentOrgId=org.data.items[0].data.orgId;
//        	var parentOrgName=org.data.items[0].data.text;
//        	var orgName_Parent_Id = Ext.getCmp('orgName_Parent_Id1');
//            orgName_Parent_Id.setValue(parentOrgId);
//            orgName_Parent_Id.setRawValue(parentOrgName);
//            Ext.getCmp('orgName_Parent_Id').setValue(parentOrgId);
//        }
        var orgName_Parent_Id = Ext.getCmp('orgName_Parent_Id1').getValue();
        if (orgName_Parent_Id == null) {
            orgName_Parent_Id = "0";
        }
        var org_code_text = Ext.getCmp("orgCode_Id");
        org_code_text.setValue("NO" + Math.floor(((Math.random(10000000) * 10000) + 1)));
        Ext.getCmp("addOrgForm_Id").getForm().reset();
        Ext.getCmp("addFormOrg_Id").show();
        Ext.getCmp("updateFormOrg_Id").hide();
        Ext.getCmp('orgName_Parent_Id').setValue(userOrg_Id);
        return false;
    }
    // del to action
function delOrgInfo() {
    var org_panel = Ext.getCmp("OrgInfoTreeGridView_Id");
    var org_model = org_panel.getSelectionModel();
    var _record = org_model.getSelection();
    var delUrl = context + '/orgManagerController/doOrgBulkDelete'
    if (_record.length>0) {
        // 提示是否删除数据
        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
        Ext.Msg.confirm(cosog.string.yesdel, cosog.string.yesdeldata, function (btn) {
            if (btn == "yes") {
                ExtDel_ObjectInfo("OrgInfoTreeGridView_Id", _record,"orgId", delUrl);
                Ext.getCmp("IframeView_Id").getStore().load();//右侧组织数刷新
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
        var orgUpdateInfoWindow = Ext.create("AP.view.org.OrgInfoWindow", {
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