// 文件名：CheckboxComboBox.js
//Ext.util.CSS.createStyleSheet(`
//    .checkbox-list .x-combo-list-item {
//        padding: 5px;
//    }
//    .checkbox-list .checkbox-container {
//        display: flex;
//        align-items: center;
//    }
//    .checkbox-list .list-checkbox {
//        margin-right: 8px;
//    }
//`);
//
Ext.define('AP.view.CheckboxComboBox', {
    extend: 'Ext.form.ComboBox',
    xtype: 'checkboxcombobox',
    
    multiSelect: true,      // 启用多选
    editable: false,        // 禁止输入
    forceSelection: true,   // 强制选择列表项
    
    // 自定义下拉列表的模板
    listConfig: {
        cls: 'checkbox-list', // 添加 CSS 类
        itemTpl: new Ext.XTemplate(
            '<div class="x-combo-list-item">',
                '<div class="checkbox-container">',
                    '<input type="checkbox" class="list-checkbox" {[values.checked ? "checked" : ""]}>',
                    '<span>{[values.' + this.displayField + ']}</span>',
                '</div>',
            '</div>'
        )
    },
    
    // 初始化组件时绑定事件
    initComponent: function() {
        this.callParent();
        
        // 监听下拉列表的点击事件
        this.on({
            render: function(combo) {
                var list = combo.getPicker();
                list.on('itemclick', this.onItemClick, this);
            },
            scope: this
        });
    },
    
    // 处理列表项点击事件
    onItemClick: function(list, record) {
        var value = record.get(this.valueField);
        var values = this.getValue() || [];
        
        // 切换选中状态
        if (Ext.Array.contains(values, value)) {
            values = Ext.Array.remove(values, value);
        } else {
            values.push(value);
        }
        
        this.setValue(values); // 更新值
    },
    
    // 重写下拉列表的渲染逻辑，标记已选中的项
    doAutoSelect: function() {
        var store = this.store;
        var valueField = this.valueField;
        var values = this.getValue() || [];
        
        // 标记每个记录是否已选中
        store.each(function(record) {
            record.set('checked', Ext.Array.contains(values, record.get(valueField)));
        });
    }
});

//// 示例使用
//Ext.onReady(function() {
//    Ext.create('Ext.panel.Panel', {
//        renderTo: Ext.getBody(),
//        width: 400,
//        items: [{
//            xtype: 'checkboxcombobox',
//            fieldLabel: '多选（带复选框）',
//            store: { /* ... */ },
//            displayField: 'name',
//            valueField: 'id',
//            value: ['1', '3']
//        }]
//    });
//});