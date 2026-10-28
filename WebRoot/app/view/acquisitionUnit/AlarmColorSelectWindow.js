Ext.define("AP.view.acquisitionUnit.AlarmColorSelectWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.alarmColorSelectWindow',
    layout: 'fit',
    title: loginUserLanguageResource.alarmColorConfig,
    iframe: true,
    id: 'AlarmColorSelectWindow_Id',
    closeAction: 'destroy',
    width: 1000,
    minWidth: 1000,
    height: 480,
    minHeight: 480,
    shadow: 'sides',
    resizable: true,
    collapsible: true,
    constrain: true,
    maximizable: false,
    plain: true,
    bodyStyle: 'padding:5px;background-color:#FFFFFF;',
    modal: true,
    border: false,
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            layout: 'fit',
            tbar: [
                // 原有的透明度隐藏域
                { id: 'goOnlineOpacity_id', xtype: 'textfield', value: 1, hidden: true },
                { id: 'onlineOpacity_id', xtype: 'textfield', value: 1, hidden: true },
                { id: 'offlineOpacity_id', xtype: 'textfield', value: 1, hidden: true },
                { id: 'runOpacity_id', xtype: 'textfield', value: 1, hidden: true },
                { id: 'stopOpacity_id', xtype: 'textfield', value: 1, hidden: true },
                { id: 'noRunStatusDataOpacity_id', xtype: 'textfield', value: 1, hidden: true },
                { id: 'normalOpacity_id', xtype: 'textfield', value: 1, hidden: true },
                { id: 'firstLevelOpacity_id', xtype: 'textfield', value: 1, hidden: true },
                { id: 'secondLevelOpacity_id', xtype: 'textfield', value: 1, hidden: true },
                { id: 'thirdLevelOpacity_id', xtype: 'textfield', value: 1, hidden: true },
                '->', 
                {
                    xtype: 'button',
                    hidden: false,
                    action: '',
                    text: loginUserLanguageResource.save,
                    iconCls: 'save',
                    handler: function () {
                        saveAlarmColor();
                    }
                }
            ],
            items: [{
                xtype: 'form',
                baseCls: 'x-plain',
                border: false,
                defaults: {
                    border: false,
                    baseCls: 'x-plain',
                    flex: 1,
                    defaultType: 'colorfield',
                    layout: 'anchor'
                },
                style: {
                    padding: '10px 10px'
                },
                layout: 'hbox',
                items: [{
                    // ============ 第一列：背景色 ============
                    items: [{
                        xtype: 'displayfield',
                        fieldLabel: '<font color=red >' + loginUserLanguageResource.backgroundColor + '</font>',
                        value: ''
                    }, {
                        id: 'goOnlineBackgroundColor_id',
                        fieldLabel: loginUserLanguageResource.goOnline,
                        labelWidth: 90,
                        anchor: '90%',
                        listeners: {
                            collapse: function (field, eOpts) {
                                if (Ext.getCmp('goOnlineBackgroundColor_id') != undefined) {
                                    Ext.getCmp('goOnlineOpacity_id').setValue(field.color.a);
                                    var opacity = field.color.a;
                                    field.setValue(field.value);
                                    var BackgroundColor0 = field.color;
                                    BackgroundColor0.a = opacity;
                                    field.inputEl.applyStyles({
                                        background: '#' + field.value,
                                        opacity: opacity
                                    });
                                    field.setColor(BackgroundColor0);
                                }
                            }
                        }
                    }, {
                        id: 'onlineBackgroundColor_id',
                        fieldLabel: loginUserLanguageResource.online,
                        labelWidth: 90,
                        anchor: '90%',
                        listeners: {
                            collapse: function (field, eOpts) {
                                if (Ext.getCmp('onlineBackgroundColor_id') != undefined) {
                                    Ext.getCmp('onlineOpacity_id').setValue(field.color.a);
                                    var opacity = field.color.a;
                                    field.setValue(field.value);
                                    var BackgroundColor0 = field.color;
                                    BackgroundColor0.a = opacity;
                                    field.inputEl.applyStyles({
                                        background: '#' + field.value,
                                        opacity: opacity
                                    });
                                    field.setColor(BackgroundColor0);
                                }
                            }
                        }
                    }, {
                        id: 'offlineBackgroundColor_id',
                        fieldLabel: loginUserLanguageResource.offline,
                        labelWidth: 90,
                        anchor: '90%',
                        listeners: {
                            collapse: function (field, eOpts) {
                                if (Ext.getCmp('offlineBackgroundColor_id') != undefined) {
                                    Ext.getCmp('offlineOpacity_id').setValue(field.color.a);
                                    var opacity = field.color.a;
                                    field.setValue(field.value);
                                    var BackgroundColor0 = field.color;
                                    BackgroundColor0.a = opacity;
                                    field.inputEl.applyStyles({
                                        background: '#' + field.value,
                                        opacity: opacity
                                    });
                                    field.setColor(BackgroundColor0);
                                }
                            }
                        }
                    }, {
                        id: 'runBackgroundColor_id',
                        fieldLabel: loginUserLanguageResource.run,
                        labelWidth: 90,
                        anchor: '90%',
                        listeners: {
                            collapse: function (field, eOpts) {
                                if (Ext.getCmp('runBackgroundColor_id') != undefined) {
                                    Ext.getCmp('runOpacity_id').setValue(field.color.a);
                                    var opacity = field.color.a;
                                    field.setValue(field.value);
                                    var BackgroundColor0 = field.color;
                                    BackgroundColor0.a = opacity;
                                    field.inputEl.applyStyles({
                                        background: '#' + field.value,
                                        opacity: opacity
                                    });
                                    field.setColor(BackgroundColor0);
                                }
                            }
                        }
                    }, {
                        id: 'stopBackgroundColor_id',
                        fieldLabel: loginUserLanguageResource.stop,
                        labelWidth: 90,
                        anchor: '90%',
                        listeners: {
                            collapse: function (field, eOpts) {
                                if (Ext.getCmp('stopBackgroundColor_id') != undefined) {
                                    Ext.getCmp('stopOpacity_id').setValue(field.color.a);
                                    var opacity = field.color.a;
                                    field.setValue(field.value);
                                    var BackgroundColor0 = field.color;
                                    BackgroundColor0.a = opacity;
                                    field.inputEl.applyStyles({
                                        background: '#' + field.value,
                                        opacity: opacity
                                    });
                                    field.setColor(BackgroundColor0);
                                }
                            }
                        }
                    }, {
                        id: 'noRunStatusDataBackgroundColor_id',
                        fieldLabel: loginUserLanguageResource.runStatusNoData,
                        labelWidth: 90,
                        anchor: '90%',
                        listeners: {
                            collapse: function (field, eOpts) {
                                if (Ext.getCmp('noRunStatusDataBackgroundColor_id') != undefined) {
                                    Ext.getCmp('noRunStatusDataOpacity_id').setValue(field.color.a);
                                    var opacity = field.color.a;
                                    field.setValue(field.value);
                                    var BackgroundColor0 = field.color;
                                    BackgroundColor0.a = opacity;
                                    field.inputEl.applyStyles({
                                        background: '#' + field.value,
                                        opacity: opacity
                                    });
                                    field.setColor(BackgroundColor0);
                                }
                            }
                        }
                    }, {
                        id: 'firstLevelBackgroundColor_id',
                        fieldLabel: loginUserLanguageResource.alarmLevel1,
                        labelWidth: 90,
                        anchor: '90%',
                        listeners: {
                            collapse: function (field, eOpts) {
                                if (Ext.getCmp('firstLevelBackgroundColor_id') != undefined) {
                                    Ext.getCmp('firstLevelOpacity_id').setValue(field.color.a);
                                    var opacity = field.color.a;
                                    field.setValue(field.value);
                                    var BackgroundColor0 = field.color;
                                    BackgroundColor0.a = opacity;
                                    field.inputEl.applyStyles({
                                        background: '#' + field.value,
                                        opacity: opacity
                                    });
                                    field.setColor(BackgroundColor0);
                                }
                            }
                        }
                    }, {
                        id: 'secondLevelBackgroundColor_id',
                        fieldLabel: loginUserLanguageResource.alarmLevel2,
                        labelWidth: 90,
                        anchor: '90%',
                        listeners: {
                            collapse: function (field, eOpts) {
                                if (Ext.getCmp('secondLevelBackgroundColor_id') != undefined) {
                                    Ext.getCmp('secondLevelOpacity_id').setValue(field.color.a);
                                    var opacity = field.color.a;
                                    field.setValue(field.value);
                                    var BackgroundColor0 = field.color;
                                    BackgroundColor0.a = opacity;
                                    field.inputEl.applyStyles({
                                        background: '#' + field.value,
                                        opacity: opacity
                                    });
                                    field.setColor(BackgroundColor0);
                                }
                            }
                        }
                    }, {
                        id: 'thirdLevelBackgroundColor_id',
                        fieldLabel: loginUserLanguageResource.alarmLevel3,
                        labelWidth: 90,
                        anchor: '90%',
                        listeners: {
                            collapse: function (field, eOpts) {
                                if (Ext.getCmp('thirdLevelBackgroundColor_id') != undefined) {
                                    var opacity = field.color.a;
                                    field.setValue(field.value);
                                    var BackgroundColor0 = field.color;
                                    BackgroundColor0.a = opacity;
                                    field.inputEl.applyStyles({
                                        background: '#' + field.value,
                                        opacity: opacity
                                    });
                                    field.setColor(BackgroundColor0);
                                }
                            }
                        }
                    }, {
                        id: 'normalBackgroundColor_id',
                        fieldLabel: loginUserLanguageResource.normal,
                        labelWidth: 90,
                        anchor: '90%',
                        listeners: {
                            collapse: function (field, eOpts) {
                                if (Ext.getCmp('normalBackgroundColor_id') != undefined) {
                                    Ext.getCmp('normalOpacity_id').setValue(field.color.a);
                                    var opacity = field.color.a;
                                    field.setValue(field.value);
                                    var BackgroundColor0 = field.color;
                                    BackgroundColor0.a = opacity;
                                    field.inputEl.applyStyles({
                                        background: '#' + field.value,
                                        opacity: opacity
                                    });
                                    field.setColor(BackgroundColor0);
                                }
                            }
                        }
                    }]
                }, {
                    // ============ 第二列：前景色 ============
                    items: [{
                        xtype: 'displayfield',
                        fieldLabel: '<font color=red >' + loginUserLanguageResource.foregroundColor + '</font>',
                        value: ''
                    }, {
                        id: 'goOnlineColor_id',
                        fieldLabel: loginUserLanguageResource.goOnline,
                        labelWidth: 90,
                        anchor: '90%',
                        value: '#FFFFFF',
                        listeners: {
                            collapse: function (field, eOpts) {
                                if (Ext.getCmp('goOnlineColor_id') != undefined) {
                                    var opacity = field.color.a;
                                    field.setValue(field.value);
                                    var BackgroundColor0 = field.color;
                                    BackgroundColor0.a = opacity;
                                    field.inputEl.applyStyles({
                                        background: '#' + field.value,
                                        opacity: opacity
                                    });
                                    field.setColor(BackgroundColor0);
                                }
                            }
                        }
                    }, {
                        id: 'onlineColor_id',
                        fieldLabel: loginUserLanguageResource.online,
                        labelWidth: 90,
                        anchor: '90%',
                        value: '#FFFFFF',
                        listeners: {
                            collapse: function (field, eOpts) {
                                if (Ext.getCmp('onlineColor_id') != undefined) {
                                    var opacity = field.color.a;
                                    field.setValue(field.value);
                                    var BackgroundColor0 = field.color;
                                    BackgroundColor0.a = opacity;
                                    field.inputEl.applyStyles({
                                        background: '#' + field.value,
                                        opacity: opacity
                                    });
                                    field.setColor(BackgroundColor0);
                                }
                            }
                        }
                    }, {
                        id: 'offlineColor_id',
                        fieldLabel: loginUserLanguageResource.offline,
                        labelWidth: 90,
                        anchor: '90%',
                        value: '#FFFFFF',
                        listeners: {
                            collapse: function (field, eOpts) {
                                if (Ext.getCmp('offlineColor_id') != undefined) {
                                    var opacity = field.color.a;
                                    field.setValue(field.value);
                                    var BackgroundColor0 = field.color;
                                    BackgroundColor0.a = opacity;
                                    field.inputEl.applyStyles({
                                        background: '#' + field.value,
                                        opacity: opacity
                                    });
                                    field.setColor(BackgroundColor0);
                                }
                            }
                        }
                    }, {
                        id: 'runColor_id',
                        fieldLabel: loginUserLanguageResource.run,
                        labelWidth: 90,
                        anchor: '90%',
                        value: '#FFFFFF',
                        listeners: {
                            collapse: function (field, eOpts) {
                                if (Ext.getCmp('runColor_id') != undefined) {
                                    var opacity = field.color.a;
                                    field.setValue(field.value);
                                    var BackgroundColor0 = field.color;
                                    BackgroundColor0.a = opacity;
                                    field.inputEl.applyStyles({
                                        background: '#' + field.value,
                                        opacity: opacity
                                    });
                                    field.setColor(BackgroundColor0);
                                }
                            }
                        }
                    }, {
                        id: 'stopColor_id',
                        fieldLabel: loginUserLanguageResource.stop,
                        labelWidth: 90,
                        anchor: '90%',
                        value: '#FFFFFF',
                        listeners: {
                            collapse: function (field, eOpts) {
                                if (Ext.getCmp('stopColor_id') != undefined) {
                                    var opacity = field.color.a;
                                    field.setValue(field.value);
                                    var BackgroundColor0 = field.color;
                                    BackgroundColor0.a = opacity;
                                    field.inputEl.applyStyles({
                                        background: '#' + field.value,
                                        opacity: opacity
                                    });
                                    field.setColor(BackgroundColor0);
                                }
                            }
                        }
                    }, {
                        id: 'noRunStatusDataColor_id',
                        fieldLabel: loginUserLanguageResource.runStatusNoData,
                        labelWidth: 90,
                        anchor: '90%',
                        value: '#FFFFFF',
                        listeners: {
                            collapse: function (field, eOpts) {
                                if (Ext.getCmp('noRunStatusDataColor_id') != undefined) {
                                    var opacity = field.color.a;
                                    field.setValue(field.value);
                                    var BackgroundColor0 = field.color;
                                    BackgroundColor0.a = opacity;
                                    field.inputEl.applyStyles({
                                        background: '#' + field.value,
                                        opacity: opacity
                                    });
                                    field.setColor(BackgroundColor0);
                                }
                            }
                        }
                    }, {
                        id: 'firstLevelColor_id',
                        fieldLabel: loginUserLanguageResource.alarmLevel1,
                        labelWidth: 90,
                        anchor: '90%',
                        value: '#FFFFFF',
                        listeners: {
                            collapse: function (field, eOpts) {
                                if (Ext.getCmp('firstLevelColor_id') != undefined) {
                                    var opacity = field.color.a;
                                    field.setValue(field.value);
                                    var BackgroundColor0 = field.color;
                                    BackgroundColor0.a = opacity;
                                    field.inputEl.applyStyles({
                                        background: '#' + field.value,
                                        opacity: opacity
                                    });
                                    field.setColor(BackgroundColor0);
                                }
                            }
                        }
                    }, {
                        id: 'secondLevelColor_id',
                        fieldLabel: loginUserLanguageResource.alarmLevel2,
                        labelWidth: 90,
                        anchor: '90%',
                        value: '#FFFFFF',
                        listeners: {
                            collapse: function (field, eOpts) {
                                if (Ext.getCmp('secondLevelColor_id') != undefined) {
                                    var opacity = field.color.a;
                                    field.setValue(field.value);
                                    var BackgroundColor0 = field.color;
                                    BackgroundColor0.a = opacity;
                                    field.inputEl.applyStyles({
                                        background: '#' + field.value,
                                        opacity: opacity
                                    });
                                    field.setColor(BackgroundColor0);
                                }
                            }
                        }
                    }, {
                        id: 'thirdLevelColor_id',
                        fieldLabel: loginUserLanguageResource.alarmLevel3,
                        labelWidth: 90,
                        anchor: '90%',
                        value: '#FFFFFF',
                        listeners: {
                            collapse: function (field, eOpts) {
                                if (Ext.getCmp('thirdLevelColor_id') != undefined) {
                                    var opacity = field.color.a;
                                    field.setValue(field.value);
                                    var BackgroundColor0 = field.color;
                                    BackgroundColor0.a = opacity;
                                    field.inputEl.applyStyles({
                                        background: '#' + field.value,
                                        opacity: opacity
                                    });
                                    field.setColor(BackgroundColor0);
                                }
                            }
                        }
                    }, {
                        id: 'normalColor_id',
                        fieldLabel: loginUserLanguageResource.normal,
                        labelWidth: 90,
                        anchor: '90%',
                        value: '#FFFFFF',
                        listeners: {
                            collapse: function (field, eOpts) {
                                if (Ext.getCmp('normalColor_id') != undefined) {
                                    var opacity = field.color.a;
                                    field.setValue(field.value);
                                    var BackgroundColor0 = field.color;
                                    BackgroundColor0.a = opacity;
                                    field.inputEl.applyStyles({
                                        background: '#' + field.value,
                                        opacity: opacity
                                    });
                                    field.setColor(BackgroundColor0);
                                }
                            }
                        }
                    }]
                }, {
                    // ============ 第三列：报警声音 ============
                    items: [{
                        xtype: 'displayfield',
                        fieldLabel: '<font color=red >' + loginUserLanguageResource.alarmSound + '</font>',
                        value: ''
                    }, {
                        // 使用 fieldcontainer 来统一组合输入框和按钮，保证与前两列完全对齐
                        xtype: 'fieldcontainer',
                        fieldLabel: loginUserLanguageResource.alarmLevel1,
                        labelWidth: 90,
                        anchor: '90%',
                        layout: 'hbox',
                        items: [{
                            xtype: 'textfield',
                            id: 'soundLevel1_id',
                            flex: 1,
                            value: '',
                            readOnly:true
                        }, {
                            xtype: 'button',
                            text: loginUserLanguageResource.set,
                            margin: '0 0 0 5',
                            handler: function() { chooseAlarmSound('soundLevel1_id'); }
                        }]
                    }, {
                        xtype: 'fieldcontainer',
                        fieldLabel: loginUserLanguageResource.alarmLevel2,
                        labelWidth: 90,
                        anchor: '90%',
                        layout: 'hbox',
                        items: [{
                            xtype: 'textfield',
                            id: 'soundLevel2_id',
                            flex: 1,
                            value: '',
                            readOnly:true
                        }, {
                            xtype: 'button',
                            text: loginUserLanguageResource.set,
                            margin: '0 0 0 5',
                            handler: function() { chooseAlarmSound('soundLevel2_id'); }
                        }]
                    }, {
                        xtype: 'fieldcontainer',
                        fieldLabel: loginUserLanguageResource.alarmLevel3,
                        labelWidth: 90,
                        anchor: '90%',
                        layout: 'hbox',
                        items: [{
                            xtype: 'textfield',
                            id: 'soundLevel3_id',
                            flex: 1,
                            value: '',
                            readOnly:true
                        }, {
                            xtype: 'button',
                            text: loginUserLanguageResource.set,
                            margin: '0 0 0 5',
                            handler: function() { chooseAlarmSound('soundLevel3_id'); }
                        }]
                    }]
                }]
            }]
        });
        me.callParent(arguments);
    }
});

/* ============================================================
 *  颜色及声音配置：读取
 * ============================================================ */
function getAlarmLevelSetColor() {
    Ext.Ajax.request({
        url: context + '/alarmSetManagerController/getAlarmLevelColor',
        method: "POST",
        success: function (response) {
            var AlarmShowStyle = Ext.decode(response.responseText);

            /* ---------- 背景色 ---------- */
            Ext.getCmp('goOnlineBackgroundColor_id').setValue(AlarmShowStyle.Comm.goOnline.BackgroundColor);
            var BackgroundColor0 = Ext.getCmp('goOnlineBackgroundColor_id').color;
            BackgroundColor0.a = AlarmShowStyle.Comm.goOnline.Opacity;
            Ext.getCmp('goOnlineBackgroundColor_id').inputEl.applyStyles({
                background: '#' + AlarmShowStyle.Comm.goOnline.BackgroundColor,
                opacity: AlarmShowStyle.Comm.goOnline.Opacity
            });
            Ext.getCmp('goOnlineBackgroundColor_id').setColor(BackgroundColor0);

            Ext.getCmp('onlineBackgroundColor_id').setValue(AlarmShowStyle.Comm.online.BackgroundColor);
            var BackgroundColor0 = Ext.getCmp('onlineBackgroundColor_id').color;
            BackgroundColor0.a = AlarmShowStyle.Comm.online.Opacity;
            Ext.getCmp('onlineBackgroundColor_id').inputEl.applyStyles({
                background: '#' + AlarmShowStyle.Comm.online.BackgroundColor,
                opacity: AlarmShowStyle.Comm.online.Opacity
            });
            Ext.getCmp('onlineBackgroundColor_id').setColor(BackgroundColor0);

            Ext.getCmp('offlineBackgroundColor_id').setValue(AlarmShowStyle.Comm.offline.BackgroundColor);
            var BackgroundColor0 = Ext.getCmp('offlineBackgroundColor_id').color;
            BackgroundColor0.a = AlarmShowStyle.Comm.offline.Opacity;
            Ext.getCmp('offlineBackgroundColor_id').inputEl.applyStyles({
                background: '#' + AlarmShowStyle.Comm.offline.BackgroundColor,
                opacity: AlarmShowStyle.Comm.offline.Opacity
            });
            Ext.getCmp('offlineBackgroundColor_id').setColor(BackgroundColor0);

            Ext.getCmp('runBackgroundColor_id').setValue(AlarmShowStyle.Run.run.BackgroundColor);
            var BackgroundColor0 = Ext.getCmp('runBackgroundColor_id').color;
            BackgroundColor0.a = AlarmShowStyle.Run.run.Opacity;
            Ext.getCmp('runBackgroundColor_id').inputEl.applyStyles({
                background: '#' + AlarmShowStyle.Run.run.BackgroundColor,
                opacity: AlarmShowStyle.Run.run.Opacity
            });
            Ext.getCmp('runBackgroundColor_id').setColor(BackgroundColor0);

            Ext.getCmp('stopBackgroundColor_id').setValue(AlarmShowStyle.Run.stop.BackgroundColor);
            var BackgroundColor0 = Ext.getCmp('stopBackgroundColor_id').color;
            BackgroundColor0.a = AlarmShowStyle.Run.stop.Opacity;
            Ext.getCmp('stopBackgroundColor_id').inputEl.applyStyles({
                background: '#' + AlarmShowStyle.Run.stop.BackgroundColor,
                opacity: AlarmShowStyle.Run.stop.Opacity
            });
            Ext.getCmp('stopBackgroundColor_id').setColor(BackgroundColor0);

            Ext.getCmp('noRunStatusDataBackgroundColor_id').setValue(AlarmShowStyle.Run.noData.BackgroundColor);
            var BackgroundColor0 = Ext.getCmp('noRunStatusDataBackgroundColor_id').color;
            BackgroundColor0.a = AlarmShowStyle.Run.noData.Opacity;
            Ext.getCmp('noRunStatusDataBackgroundColor_id').inputEl.applyStyles({
                background: '#' + AlarmShowStyle.Run.noData.BackgroundColor,
                opacity: AlarmShowStyle.Run.noData.Opacity
            });
            Ext.getCmp('noRunStatusDataBackgroundColor_id').setColor(BackgroundColor0);

            Ext.getCmp('normalBackgroundColor_id').setValue(AlarmShowStyle.Data.Normal.BackgroundColor);
            var BackgroundColor0 = Ext.getCmp('normalBackgroundColor_id').color;
            BackgroundColor0.a = AlarmShowStyle.Data.Normal.Opacity;
            Ext.getCmp('normalBackgroundColor_id').inputEl.applyStyles({
                background: '#' + AlarmShowStyle.Data.Normal.BackgroundColor,
                opacity: AlarmShowStyle.Data.Normal.Opacity
            });
            Ext.getCmp('normalBackgroundColor_id').setColor(BackgroundColor0);

            Ext.getCmp('firstLevelBackgroundColor_id').setValue(AlarmShowStyle.Data.FirstLevel.BackgroundColor);
            var BackgroundColor1 = Ext.getCmp('firstLevelBackgroundColor_id').color;
            BackgroundColor1.a = AlarmShowStyle.Data.FirstLevel.Opacity;
            Ext.getCmp('firstLevelBackgroundColor_id').inputEl.applyStyles({
                background: '#' + AlarmShowStyle.Data.FirstLevel.BackgroundColor,
                opacity: AlarmShowStyle.Data.FirstLevel.Opacity
            });
            Ext.getCmp('firstLevelBackgroundColor_id').setColor(BackgroundColor1);

            Ext.getCmp('secondLevelBackgroundColor_id').setValue(AlarmShowStyle.Data.SecondLevel.BackgroundColor);
            var BackgroundColor2 = Ext.getCmp('secondLevelBackgroundColor_id').color;
            BackgroundColor2.a = AlarmShowStyle.Data.SecondLevel.Opacity;
            Ext.getCmp('secondLevelBackgroundColor_id').inputEl.applyStyles({
                background: '#' + AlarmShowStyle.Data.SecondLevel.BackgroundColor,
                opacity: AlarmShowStyle.Data.SecondLevel.Opacity
            });
            Ext.getCmp('secondLevelBackgroundColor_id').setColor(BackgroundColor2);

            Ext.getCmp('thirdLevelBackgroundColor_id').setValue(AlarmShowStyle.Data.ThirdLevel.BackgroundColor);
            var BackgroundColor3 = Ext.getCmp('thirdLevelBackgroundColor_id').color;
            BackgroundColor3.a = AlarmShowStyle.Data.ThirdLevel.Opacity;
            Ext.getCmp('thirdLevelBackgroundColor_id').inputEl.applyStyles({
                background: '#' + AlarmShowStyle.Data.ThirdLevel.BackgroundColor,
                opacity: AlarmShowStyle.Data.ThirdLevel.Opacity
            });
            Ext.getCmp('thirdLevelBackgroundColor_id').setColor(BackgroundColor3);

            /* ---------- 前景色 ---------- */
            Ext.getCmp('goOnlineColor_id').setValue(AlarmShowStyle.Comm.goOnline.Color);
            Ext.getCmp('goOnlineColor_id').inputEl.applyStyles({ background: '#' + AlarmShowStyle.Comm.goOnline.Color });

            Ext.getCmp('onlineColor_id').setValue(AlarmShowStyle.Comm.online.Color);
            Ext.getCmp('onlineColor_id').inputEl.applyStyles({ background: '#' + AlarmShowStyle.Comm.online.Color });

            Ext.getCmp('offlineColor_id').setValue(AlarmShowStyle.Comm.offline.Color);
            Ext.getCmp('offlineColor_id').inputEl.applyStyles({ background: '#' + AlarmShowStyle.Comm.offline.Color });

            Ext.getCmp('runColor_id').setValue(AlarmShowStyle.Run.run.Color);
            Ext.getCmp('runColor_id').inputEl.applyStyles({ background: '#' + AlarmShowStyle.Run.run.Color });

            Ext.getCmp('stopColor_id').setValue(AlarmShowStyle.Run.stop.Color);
            Ext.getCmp('stopColor_id').inputEl.applyStyles({ background: '#' + AlarmShowStyle.Run.stop.Color });

            Ext.getCmp('noRunStatusDataColor_id').setValue(AlarmShowStyle.Run.noData.Color);
            Ext.getCmp('noRunStatusDataColor_id').inputEl.applyStyles({ background: '#' + AlarmShowStyle.Run.noData.Color });

            Ext.getCmp('normalColor_id').setValue(AlarmShowStyle.Data.Normal.Color);
            Ext.getCmp('normalColor_id').inputEl.applyStyles({ background: '#' + AlarmShowStyle.Data.Normal.Color });

            Ext.getCmp('firstLevelColor_id').setValue(AlarmShowStyle.Data.FirstLevel.Color);
            Ext.getCmp('firstLevelColor_id').inputEl.applyStyles({ background: '#' + AlarmShowStyle.Data.FirstLevel.Color });

            Ext.getCmp('secondLevelColor_id').setValue(AlarmShowStyle.Data.SecondLevel.Color);
            Ext.getCmp('secondLevelColor_id').inputEl.applyStyles({ background: '#' + AlarmShowStyle.Data.SecondLevel.Color });

            Ext.getCmp('thirdLevelColor_id').setValue(AlarmShowStyle.Data.ThirdLevel.Color);
            Ext.getCmp('thirdLevelColor_id').inputEl.applyStyles({ background: '#' + AlarmShowStyle.Data.ThirdLevel.Color });

            /* ---------- 透明度 ---------- */
            Ext.getCmp('goOnlineOpacity_id').setValue(AlarmShowStyle.Comm.goOnline.Opacity);
            Ext.getCmp('onlineOpacity_id').setValue(AlarmShowStyle.Comm.online.Opacity);
            Ext.getCmp('offlineOpacity_id').setValue(AlarmShowStyle.Comm.offline.Opacity);
            Ext.getCmp('runOpacity_id').setValue(AlarmShowStyle.Run.run.Opacity);
            Ext.getCmp('stopOpacity_id').setValue(AlarmShowStyle.Run.stop.Opacity);
            Ext.getCmp('normalOpacity_id').setValue(AlarmShowStyle.Data.Normal.Opacity);
            Ext.getCmp('firstLevelOpacity_id').setValue(AlarmShowStyle.Data.FirstLevel.Opacity);
            Ext.getCmp('secondLevelOpacity_id').setValue(AlarmShowStyle.Data.SecondLevel.Opacity);
            Ext.getCmp('thirdLevelOpacity_id').setValue(AlarmShowStyle.Data.ThirdLevel.Opacity);

            /* ---------- 声音文件 ---------- */
            var f1 = AlarmShowStyle.Data.FirstLevel ? AlarmShowStyle.Data.FirstLevel.SoundFile : '';
            var f2 = AlarmShowStyle.Data.SecondLevel ? AlarmShowStyle.Data.SecondLevel.SoundFile : '';
            var f3 = AlarmShowStyle.Data.ThirdLevel ? AlarmShowStyle.Data.ThirdLevel.SoundFile : '';
            
            Ext.getCmp('soundLevel1_id').setValue(f1 || '未配置');
            Ext.getCmp('soundLevel2_id').setValue(f2 || '未配置');
            Ext.getCmp('soundLevel3_id').setValue(f3 || '未配置');
        },
        failure: function () {
            Ext.Msg.alert(loginUserLanguageResource.tip,
                "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】:" + loginUserLanguageResource.contactAdmin);
        }
    });
}


/* ============================================================
 *  颜色配置：保存（统一保存颜色和声音）
 * ============================================================ */
function saveAlarmColor() {
    var goOnlineBackgroundColor = Ext.getCmp('goOnlineBackgroundColor_id').getValue();
    var onlineBackgroundColor = Ext.getCmp('onlineBackgroundColor_id').getValue();
    var offlineBackgroundColor = Ext.getCmp('offlineBackgroundColor_id').getValue();
    var runBackgroundColor = Ext.getCmp('runBackgroundColor_id').getValue();
    var stopBackgroundColor = Ext.getCmp('stopBackgroundColor_id').getValue();
    var noRunStatusDataBackgroundColor = Ext.getCmp('noRunStatusDataBackgroundColor_id').getValue();
    var normalBackgroundColor = Ext.getCmp('normalBackgroundColor_id').getValue();
    var firstLevelBackgroundColor = Ext.getCmp('firstLevelBackgroundColor_id').getValue();
    var secondLevelBackgroundColor = Ext.getCmp('secondLevelBackgroundColor_id').getValue();
    var thirdLevelBackgroundColor = Ext.getCmp('thirdLevelBackgroundColor_id').getValue();

    var goOnlineColor = Ext.getCmp('goOnlineColor_id').getValue();
    var onlineColor = Ext.getCmp('onlineColor_id').getValue();
    var offlineColor = Ext.getCmp('offlineColor_id').getValue();
    var runColor = Ext.getCmp('runColor_id').getValue();
    var stopColor = Ext.getCmp('stopColor_id').getValue();
    var noRunStatusDataColor = Ext.getCmp('noRunStatusDataColor_id').getValue();
    var normalColor = Ext.getCmp('normalColor_id').getValue();
    var firstLevelColor = Ext.getCmp('firstLevelColor_id').getValue();
    var secondLevelColor = Ext.getCmp('secondLevelColor_id').getValue();
    var thirdLevelColor = Ext.getCmp('thirdLevelColor_id').getValue();

    var goOnlineOpacity = Ext.getCmp('goOnlineOpacity_id').getValue();
    var onlineOpacity = Ext.getCmp('onlineOpacity_id').getValue();
    var offlineOpacity = Ext.getCmp('offlineOpacity_id').getValue();
    var runOpacity = Ext.getCmp('runOpacity_id').getValue();
    var stopOpacity = Ext.getCmp('stopOpacity_id').getValue();
    var noRunStatusDataOpacity = Ext.getCmp('noRunStatusDataOpacity_id').getValue();
    var normalOpacity = Ext.getCmp('normalOpacity_id').getValue();
    var firstLevelOpacity = Ext.getCmp('firstLevelOpacity_id').getValue();
    var secondLevelOpacity = Ext.getCmp('secondLevelOpacity_id').getValue();
    var thirdLevelOpacity = Ext.getCmp('thirdLevelOpacity_id').getValue();

    var AlarmShowStyle = {};
    AlarmShowStyle.Data = {};
    AlarmShowStyle.Data.Normal = { Value: 0, BackgroundColor: normalBackgroundColor, Color: normalColor, Opacity: normalOpacity };
    AlarmShowStyle.Data.FirstLevel = { Value: 100, BackgroundColor: firstLevelBackgroundColor, Color: firstLevelColor, Opacity: firstLevelOpacity };
    AlarmShowStyle.Data.SecondLevel = { Value: 200, BackgroundColor: secondLevelBackgroundColor, Color: secondLevelColor, Opacity: secondLevelOpacity };
    AlarmShowStyle.Data.ThirdLevel = { Value: 300, BackgroundColor: thirdLevelBackgroundColor, Color: thirdLevelColor, Opacity: thirdLevelOpacity };

    AlarmShowStyle.Comm = {
        goOnline: { Value: 2, BackgroundColor: goOnlineBackgroundColor, Color: goOnlineColor, Opacity: goOnlineOpacity },
        online: { Value: 1, BackgroundColor: onlineBackgroundColor, Color: onlineColor, Opacity: onlineOpacity },
        offline: { Value: 0, BackgroundColor: offlineBackgroundColor, Color: offlineColor, Opacity: offlineOpacity }
    };

    AlarmShowStyle.Run = {
        run: { Value: 1, BackgroundColor: runBackgroundColor, Color: runColor, Opacity: runOpacity },
        stop: { Value: 0, BackgroundColor: stopBackgroundColor, Color: stopColor, Opacity: stopOpacity },
        noData: { Value: 2, BackgroundColor: noRunStatusDataBackgroundColor, Color: noRunStatusDataColor, Opacity: noRunStatusDataOpacity }
    };

    // 1. 提交颜色
    Ext.Ajax.request({
        url: context + '/alarmSetManagerController/setAlarmColor',
        method: "POST",
        params: {
            data: JSON.stringify(AlarmShowStyle)
        },
        success: function (response, action) {
            if (Ext.decode(response.responseText).msg) {
                // 2. 颜色保存成功后，提交声音配置
                saveAlarmSoundConfig(function (success) {
                    if (success) {
                        Ext.getCmp("AlarmColorSelectWindow_Id").close();
                        Ext.Msg.alert(loginUserLanguageResource.tip,
                            "<font color=blue>" + loginUserLanguageResource.updateSuccessfully + "</font>");
                        // 通知 AlaramSoundManager 刷新配置
                        if (AP.alert && AP.alert.AlarmSoundManager) {
                            AP.alert.AlarmSoundManager.refresh();
                        }
                    } else {
                        Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>颜色已保存，但声音配置保存失败</font>");
                    }
                });
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip,
                    "<font color=red>" + loginUserLanguageResource.updatefail + "</font>");
            }
        },
        failure: function () {
            Ext.Msg.alert(loginUserLanguageResource.tip,
                "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】:" + loginUserLanguageResource.contactAdmin);
        }
    });
}

/* ★ 辅助方法：统一保存声音 */
function saveAlarmSoundConfig(callback) {
    var soundData = {
        "100": Ext.getCmp('soundLevel1_id').getValue() === '未配置' ? '' : Ext.getCmp('soundLevel1_id').getValue(),
        "200": Ext.getCmp('soundLevel2_id').getValue() === '未配置' ? '' : Ext.getCmp('soundLevel2_id').getValue(),
        "300": Ext.getCmp('soundLevel3_id').getValue() === '未配置' ? '' : Ext.getCmp('soundLevel3_id').getValue()
    };
    
    Ext.Ajax.request({
        url: context + '/alarmSetManagerController/setAlarmSound',
        method: "POST",
        params: {
            data: JSON.stringify(soundData)
        },
        success: function (response) {
            var r = Ext.decode(response.responseText);
            callback(r && r.success);
        },
        failure: function () {
            callback(false);
        }
    });
}

/* ★ 辅助方法：打开弹窗选择声音 */
function chooseAlarmSound(fieldId) {
    var currentVal = Ext.getCmp(fieldId).getValue();
    if (currentVal === '未配置') currentVal = ''; // 处理默认文案
    
    Ext.create('AP.view.acquisitionUnit.AlarmSoundSelectWindow', {
        currentFile: currentVal,
        onConfirm: function (fileName) {
            Ext.getCmp(fieldId).setValue(fileName || '未配置');
        }
    }).show();
}