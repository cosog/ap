Ext.define('AP.view.data.DataitemsInfoWin', {
    extend: 'Ext.Window',
    alias: 'widget.dataitemsInfoWin',
    id: "DataitemsInfoWinId",
    width: 510,
    height: 420,
    layout: 'fit',
    closeAction: 'destroy',
    resizable: false,
    constrain: true,
    modal: true,
    bodyStyle: 'background-color:#ffffff;',
    initComponent: function () {
        var me = this;
        //英文名称
        var sysdata_code = Ext.create("Ext.form.TextField", {
            id: "sysDataCode_Ids",
            name: 'dataitemsInfo.code',
            fieldLabel: loginUserLanguageResource.dataColumnCode+'<font color=red>*</font>',
            allowBlank: false,
            width: 350,
            msgTarget: 'side',
            blankText: loginUserLanguageResource.required
        });
        //顺序
        var sysdata_sorts = Ext.create("Ext.form.NumberField", {
            id: "sysdatasorts_Ids",
            name: 'dataitemsInfo.sorts',
            fieldLabel: loginUserLanguageResource.sortNum+'<font color=red>*</font>',
            allowBlank: false,
            minValue: 0,
            width: 350,
            msgTarget: 'side',
            blankText: loginUserLanguageResource.required
        });
        //数据项的值
        var sysdata_datavalue = Ext.create("Ext.form.TextArea", {
            id: "sysdatadatavalue_Ids",
            name: 'dataitemsInfo.datavalue',
            fieldLabel: loginUserLanguageResource.dataColumnParams,
            width: 450,
            height: 100
        });
        //表单组件		
        var addtenaorgLevfromname = Ext.create("Ext.form.Panel", {
            id: "addtenaorgLevfromnameId",
            layout: 'auto',
            border: false,
            labelSeparator: ':',
            bodyStyle: 'padding:20px;',
            defaultType: 'textfield',
            items: [
                {
                    xtype: 'container',
                    height: 51,
                    width: 501,
                    html: '<table width="468" height="42" border="0" cellspacing="0" style="font-size: 12px;color: #999999;"><tr><td width="95" height="21">'+loginUserLanguageResource.tip+'：</td><td width="357">&nbsp;</td></tr><tr><td height="26"></td><td> '+loginUserLanguageResource.requiredInfo+' </td></tr></table><div  class="divider_s"></div>'
                },
                {
                    id: "sysDataName_zh_CN_Ids",
                    name: 'dataitemsInfo.name_zh_CN',
                    fieldLabel: loginUserLanguageResource.dataColumnName+'<font color=red>*</font>',
                    allowBlank:(loginUserLanguage.toUpperCase()=='ZH_CN'?false:true),
                    hidden:(loginUserLanguage.toUpperCase()=='ZH_CN'?false:true),
                    width: 350,
                    msgTarget: 'side',
                    blankText: loginUserLanguageResource.required
                },
                {
                    id: "sysDataName_en_Ids",
                    name: 'dataitemsInfo.name_en',
                    fieldLabel: loginUserLanguageResource.dataColumnName+'<font color=red>*</font>',
                    allowBlank:(loginUserLanguage.toUpperCase()=='EN'?false:true),
                    hidden:(loginUserLanguage.toUpperCase()=='EN'?false:true),
                    width: 350,
                    msgTarget: 'side',
                    blankText: loginUserLanguageResource.required
                },
                {
                    id: "sysDataName_ru_Ids",
                    name: 'dataitemsInfo.name_ru',
                    fieldLabel: loginUserLanguageResource.dataColumnName+'<font color=red>*</font>',
                    allowBlank:(loginUserLanguage.toUpperCase()=='RU'?false:true),
                    hidden:(loginUserLanguage.toUpperCase()=='RU'?false:true),
                    width: 350,
                    msgTarget: 'side',
                    blankText: loginUserLanguageResource.required
                }, sysdata_code,
                {
                    xtype: 'radiogroup',
                    width: 200,
                    id: "dataitemsInfo_status_id",
                    fieldLabel: loginUserLanguageResource.enable,
                    items: [
                        {
                            checked: true,
                            boxLabel: loginUserLanguageResource.yes,
                            inputValue: 1,
                            name: 'dataitemsInfo.status'
                          },
                        {
                            boxLabel: loginUserLanguageResource.no,
                            inputValue: 0,
                            name: 'dataitemsInfo.status'
                          }
                      ]
           },
        sysdata_sorts, sysdata_datavalue,
                {
                    xtype: 'textfield',
                    name: 'dataitemsInfo.sysdataid',
                    id: 'hide_addsys_Id',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'dataitemsInfo.dataitemid',
                    id: 'hide_dataitemids',
                    hidden: true
                }
    ]
        });

        Ext.apply(me, {
            items: [addtenaorgLevfromname]
        });
        me.callParent(arguments);

    },
    buttons: [
        {
            id: "oktosysfordataFormBtnId",
            text: loginUserLanguageResource.confirm,
            iconCls: 'save',
            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
            action: 'oktosysfordataAction'
        },
        {
            id: "savettosysfordataFormBtnId",
            text: loginUserLanguageResource.save,
            iconCls: 'save',
            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
            action: 'savetosysfordatasAction',
            hidden: true
        },
        {
            id: "editttosysfordataFormBtnId",
            text: loginUserLanguageResource.save,
            iconCls: 'save',
            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
            action: 'edittosysfordatasAction',
            hidden: true
        },
        {
            id: "cancltosysfordataBtnId",
            text: loginUserLanguageResource.cancel,
            iconCls: 'cancel',
            closewin: 'DataitemsInfoWinId',
            handler: closeWindow
        }
              ]
});