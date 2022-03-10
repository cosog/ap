Ext.define('AP.view.orgAndUser.Password', {
    extend: 'Ext.form.field.Text',
    xtype: 'passFile',
    requires: ['Ext.form.trigger.Component'],
    //禁止自动填充
    autoComplete: 'off',
    inputType: 'password',
    //自定义样式
    cls: 'password',
    triggers: {
        cutoverButton: {
            type: 'component',
            //只读时隐藏，此功能未测试
            hideOnReadOnly: true,
            preventMouseDown: false
        }
    },
    /**
 * @private 创建切换按钮
 */
    applyTriggers: function(triggers) {
        var me = this,
        triggerCfg = triggers.cutoverButton;
        //增加切换按钮
        if (triggerCfg) {
            triggerCfg.component = Ext.apply({
                xtype: 'button',
                ownerCt: me,
                //加入小图标
                iconCls: 'x-fa fa-eye',
                id: me.id + '-triggerButton',
                ui: me.ui,
                listeners: {
                    scope: me,
                    click: me.onCutoverClick
                }
            });
            return me.callParent([triggers]);
        }
            // <debug>
        else {
            Ext.raise(me.$className + ' requires a valid trigger config containing "button" specification');
        }
        // </debug>
    },
    onCutoverClick: function(t) {
        var type = 'password',
        iconCls = 'x-fa fa-eye';
        if (!t.isText) {
            type = 'text';
            iconCls = 'x-fa fa-lock';
        }
        t.isText = !t.isText;
        //切换图标
        t.setIconCls(iconCls);
        //切换输入框类型
        this.inputType = type;
        this.inputEl.dom.type = type;
    }
});