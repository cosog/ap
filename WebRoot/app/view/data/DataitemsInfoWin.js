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
        //中文名称
        var sysdata_cname = Ext.create("Ext.form.TextField", {
            id: "sysdatacname_Ids",
            name: 'dataitemsInfo.cname',
            fieldLabel: cosog.string.dataColumnName,
            allowBlank: false,
            width: 350,
            msgTarget: 'side',
            blankText: cosog.string.required
        });
        //英文名称
        var sysdata_ename = Ext.create("Ext.form.TextField", {
            id: "sysdataename_Ids",
            name: 'dataitemsInfo.ename',
            fieldLabel: cosog.string.dataColumnCode,
            allowBlank: false,
            width: 350,
            msgTarget: 'side',
            blankText: cosog.string.required
        });
        //顺序
        var sysdata_sorts = Ext.create("Ext.form.NumberField", {
            id: "sysdatasorts_Ids",
            name: 'dataitemsInfo.sorts',
            fieldLabel: cosog.string.dataSorts,
            allowBlank: false,
            minValue: 0,
            width: 350,
            msgTarget: 'side',
            blankText: cosog.string.required
        });
        //数据项的值
        var sysdata_datavalue = Ext.create("Ext.form.TextArea", {
            id: "sysdatadatavalue_Ids",
            name: 'dataitemsInfo.datavalue',
            fieldLabel: cosog.string.dataColumnParams,
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
            items: [
                {
                    xtype: 'container',
                    height: 51,
                    width: 501,
                    html: '<table width="468" height="42" border="0" cellspacing="0" style="font-size: 12px;color: #999999;"><tr><td width="95" height="21">温馨提示：</td><td width="357">&nbsp;</td></tr><tr><td height="26"></td><td> 编辑数据项值，以 * 号的文本框是必填项！ </td></tr></table><div  class="divider_s"></div>'
                },
        sysdata_cname, sysdata_ename,
                {
                    xtype: 'radiogroup',
                    width: 200,
                    id: "dataitemsInfo_status_id",
                    fieldLabel: cosog.string.dataColumnEnabled,
                    items: [
                        {
                            checked: true,
                            boxLabel: cosog.string.yes,
                            inputValue: 1,
                            name: 'dataitemsInfo.status'
                          },
                        {
                            boxLabel: cosog.string.no,
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
            text: cosog.string.sure,
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