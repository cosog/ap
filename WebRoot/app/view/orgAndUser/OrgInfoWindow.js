var org_Level;
Ext.define("AP.view.orgAndUser.OrgInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.orgInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'org_addwin_Id',
    closeAction: 'destroy',
    width: 330,
    //height : 300,
    constrain: true,
    shadow: 'sides',
    resizable: false,
    collapsible: true,
    maximizable: false,
    layout: 'fit',
    plain: true,
    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
    modal: true,
    border: false,
    initComponent: function () {
        var me = this;

        var postOrgEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            id: 'addOrgForm_Id',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: loginUserLanguageResource.idx,
                id: 'orgOrg_Id',
                value: '1',
                name: "org.orgId"
            }, {
                xtype: "hidden",
                fieldLabel: '单位父ID',
                value: '0',
                name: 'org.orgParent',
                id: 'orgName_Parent_Id'

            },{
                fieldLabel: loginUserLanguageResource.orgName+'<font color=red>*</font>',
                id: 'orgName_zh_CN_Id',
                allowBlank:(loginUserLanguage.toUpperCase()=='ZH_CN'?false:true),
                hidden:(loginUserLanguage.toUpperCase()=='ZH_CN'?false:true),
                anchor: '95%',
                name: "org.orgName_zh_CN"
            },{
                fieldLabel: loginUserLanguageResource.orgName+'<font color=red>*</font>',
                id: 'orgName_en_Id',
                allowBlank:(loginUserLanguage.toUpperCase()=='EN'?false:true),
                hidden:(loginUserLanguage.toUpperCase()=='EN'?false:true),
                anchor: '95%',
                name: "org.orgName_en"
            },{
                fieldLabel: loginUserLanguageResource.orgName+'<font color=red>*</font>',
                id: 'orgName_ru_Id',
                allowBlank:(loginUserLanguage.toUpperCase()=='RU'?false:true),
                hidden:(loginUserLanguage.toUpperCase()=='RU'?false:true),
                anchor: '95%',
                name: "org.orgName_ru"
            }, {
                fieldLabel: loginUserLanguageResource.remark,
                id: 'orgMemo_Id',
                hidden: true,
                anchor: '95%',
                xtype: 'textareafield',
                value: '',
                name: "org.orgMemo"
            },{
            	xtype: 'numberfield',
            	id: "orgSeq_Id",
            	name: "org.orgSeq",
                fieldLabel: loginUserLanguageResource.sortNum,
                allowBlank: true,
                minValue: '',
                anchor: '95%',
                msgTarget: 'side'
            }],
            buttons: [{
                id: 'addFormOrg_Id',
                xtype: 'button',
                text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: SaveOrgDataInfoSubmitBtnForm
            }, {
                xtype: 'button',
                id: 'updateFormOrg_Id',
                text: loginUserLanguageResource.update,
                hidden: true,
                iconCls: 'edit',
                handler: UpdateOrgDataInfoSubmitBtnForm
            }, {
                text: loginUserLanguageResource.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("org_addwin_Id").close();
                }
            }]
        });
        Ext.apply(me, {
            items: postOrgEditForm
        });
        me.callParent(arguments);
    }
});