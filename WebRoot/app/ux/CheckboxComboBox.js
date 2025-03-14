// 带有复选框的下拉框
Ext.define('AP.ux.CheckboxComboBox', {
    extend: 'Ext.form.field.ComboBox',
    alias: 'widget.checkboxcombobox',
    multiSelect: true,
    allSelector: false,
    noData: true,
    noDataText: 'No combo data',
    addAllSelector: false,
    allSelectorHidden: false,
    enableKeyEvents: true,
    afterExpandCheck: true,
    allText: 'All',
    oldValue: '',
    initComponent: function () {
        var me = this;
        me.callParent(arguments);
        me.store.on('load', function (store) {
            me.fireEvent('load', me, store);
        });
    },
    listeners: {
        /*
         * uncomment if you want to reload store on every combo expand
         * beforequery: function(qe) { this.store.removeAll(); delete
         * qe.combo.lastQuery; },
         */
        focus: function (cpt) {
            cpt.oldValue = cpt.getValue();
        }
    },
    createPicker: function () {
        var me = this,
            picker,
            menuCls = Ext.baseCSSPrefix + 'menu',
            opts = Ext.apply({
                pickerField: me,
                selectionModel: me.pickerSelectionModel,
                floating: true,
                hidden: true,
                ownerCt: me.ownerCt,
                cls: me.el.up('.' + menuCls) ? menuCls : '',
                store: me.getPickerStore(),
                displayField: me.displayField,
                preserveScrollOnRefresh: true,
                pageSize: me.pageSize,
                checkedItemCls: 'x-grid-checkcolumn-checked',
                tpl: [
                    '<tpl for=".">',
                    '<div class="x-boundlist-item" role="option"><span class="x-grid-checkcolumn"> </span> {'+ me.displayField + '}</div>', '</tpl>'
                ],
                // 修改checkbox样式
                onItemSelect: function (record) {
                    var node = this.getNode(record);
                    if (node) {
                        Ext.fly(node).addCls(this.selectedItemCls);
                        node.setAttribute('aria-selected', 'true');
                        // 只添加这一句，其他列不变
                        Ext.fly(node).down('span.x-grid-checkcolumn').addCls(this.checkedItemCls);
                    }
                    return node;
                },
                // 修改checkbox样式
                onItemDeselect: function (record) {
                    var node = this.getNode(record);
                    if (node) {
                        Ext.fly(node).removeCls(this.selectedItemCls);
                        node.setAttribute('aria-selected', 'false');
                        // 只添加这一句，其他列不变
                        Ext.fly(node).down('span.x-grid-checkcolumn').removeCls(this.checkedItemCls);
                    }
                    return node;
                }
            }, me.listConfig, me.defaultListConfig);
 
        picker = me.picker = Ext.create('Ext.view.BoundList', opts);
        if (me.pageSize) {
            picker.pagingToolbar.on('beforechange', me.onPageChange, me);
        }
 
        // We limit the height of the picker to fit in the space above
        // or below this field unless the picker has its own ideas about that.
        if (!picker.initialConfig.maxHeight) {
            picker.on({
                beforeshow: me.onBeforePickerShow,
                scope: me
            });
        }
        picker.getSelectionModel().on({
            beforeselect: me.onBeforeSelect,
            beforedeselect: me.onBeforeDeselect,
            scope: me
        });
 
        picker.getNavigationModel().navigateOnSpace = false;
 
        me.store.on('load', function (store) {
            if (store.getTotalCount() == 0) {
                me.allSelectorHidden = true;
                if (typeof me.allSelector === 'object' && me.allSelector)
                    me.allSelector.setStyle('display', 'none');
                if (typeof me.noData === 'object' && me.noData)
                    me.noData.setStyle('display', 'block');
            } else {
                me.allSelectorHidden = false;
                if (typeof me.allSelector === 'object' && me.allSelector)
                    me.allSelector.setStyle('display', 'block');
                if (typeof me.noData === 'object' && me.noData)
                    me.noData.setStyle('display', 'none');
            }
        });
        return picker;
    },
    reset: function () {
        var me = this;
 
        me.setValue('');
    },
    setValue: function (value) {
        this.value = value;
        if (!value) {
            if (typeof this.allSelector === 'object' && this.allSelector) {
                this.allSelector.removeCls(this.picker.selectedItemCls);
                this.allSelector.down('span.x-grid-checkcolumn').removeCls(this.picker.checkedItemCls);
            }
            return this.callParent(arguments);
        }
 
        if (typeof value == 'string') {
            var me = this, records = [], vals = value.split(',');
 
            if (value == '') {
                if (typeof me.allSelector === 'object' && me.allSelector) {
                    me.allSelector.removeCls(this.picker.selectedItemCls);
                    this.allSelector.down('span.x-grid-checkcolumn').removeCls(this.picker.checkedItemCls);
                }
            } else {
                if (vals.length == me.store.getCount() && vals.length != 0) {
                    if (typeof me.allSelector === 'object' && me.allSelector) {
                        me.allSelector.addCls(this.picker.selectedItemCls);
                        this.allSelector.down('span.x-grid-checkcolumn').addCls(this.picker.checkedItemCls);
                    } else {
                        me.afterExpandCheck = true;
                    }
                }
            }
 
            /* Ext.each(vals, function(val) {
                var record = me.store.getById(val);
                if (record)
                    records.push(record);
            }); */
            //  可根据任意字段设置值
            Ext.each(vals, function (val) {
                var record;
                if (me.valueField) {
                    record = me.store.findRecord(me.valueField, val);
                } else {
                    record = me.store.getById(val);
                }
                if (record) records.push(record);
            });
 
            return me.setValue(records);
        } else
            return this.callParent(arguments);
    },
    getValue: function () {
        if (typeof this.value == 'object')
            return this.value.join(',');
        else
            return this.value;
    },
    getSubmitValue: function () {
        return this.getValue();
    },
    // 首次加载后的赋值(只针对当前页或不分页)
    setLoadValue: function (value) {
        var me = this;
        if (value) {
            if (me.store.getCount()) {
                me.setValue(value);
            } else {
                me.store.on('load', function (store) {
                    me.setValue(value);
                });
                me.store.load();
            }
        }
    },
    expand: function () {
        var me = this, bodyEl, picker, doc, collapseIf;
 
        if (me.rendered && !me.isExpanded && !me.isDestroyed) {
            bodyEl = me.bodyEl;
            picker = me.getPicker();
            doc = Ext.getDoc();
            collapseIf = me.collapseIf;
            picker.setMaxHeight(picker.initialConfig.maxHeight);
 
            if (me.matchFieldWidth) {
                picker.width = me.bodyEl.getWidth();
            }
 
            // Show the picker and set isExpanded flag. alignPicker only works
            // if isExpanded.
            picker.show();
            me.isExpanded = true;
            me.alignPicker();
            bodyEl.addCls(me.openCls);
            if (me.noData == false)
                me.noData = picker.getEl().down('.x-boundlist-list-ct')
                    .insertHtml(
                        'beforeBegin',
                        '<div class="x-boundlist-item" role="option">'
                        + me.noDataText + '</div>', true);
            if (me.addAllSelector == true && me.allSelector == false) {
                me.allSelector = picker
                    .getEl()
                    .down('.x-boundlist-list-ct')
                    .insertHtml(
                        'beforeBegin',
                        '<div class="x-boundlist-item" role="option"><span class="x-grid-checkcolumn"> </span> '
                        + me.allText + '</div>', true);
                me.allSelector.on('click', function (e) {
                    if (me.allSelector.hasCls(picker.selectedItemCls)) {
                        me.allSelector.removeCls(picker.selectedItemCls);
                        me.allSelector.down('span.x-grid-checkcolumn').removeCls(picker.checkedItemCls);
                        me.setValue('');
                        me.fireEvent('select', me, []);
                    } else {
                        var records = [];
                        me.store.each(function (record) {
                            records.push(record);
                        });
                        me.allSelector.addCls(picker.selectedItemCls);
                        me.allSelector.down('span.x-grid-checkcolumn').addCls(picker.checkedItemCls);
                        me.select(records);
                        me.fireEvent('select', me, records);
                    }
                });
 
                if (me.allSelectorHidden == true)
                    me.allSelector.hide();
                else
                    me.allSelector.show();
 
                if (me.afterExpandCheck == true) {
                    me.allSelector.addCls(me.selectedItemCls);
                    me.afterExpandCheck = false;
                }
            }
 
            // Collapse on touch outside this component tree.
            // Because touch platforms do not focus document.body on touch
            // so no focusleave would occur to trigger a collapse.
            me.touchListeners = doc.on({
                // Do not translate on non-touch platforms.
                // mousedown will blur the field.
                translate: false,
                touchstart: collapseIf,
                scope: me,
                delegated: false,
                destroyable: true
            });
 
            // Scrolling of anything which causes this field to move should
            // collapse
            me.scrollListeners = Ext.on({
                scroll: me.onGlobalScroll,
                scope: me,
                destroyable: true
            });
 
            // monitor touch and mousewheel
            me.hideListeners = doc.on({
                mousewheel: collapseIf,
                touchstart: collapseIf,
                scope: me,
                delegated: false,
                destroyable: true
            });
 
            // Buffer is used to allow any layouts to complete before we align
            Ext.on('resize', me.alignPicker, me, {
                buffer: 1
            });
            me.fireEvent('expand', me);
            me.onExpand();
        } else {
            me.fireEvent('expand', me);
            me.onExpand();
        }
    },
    onListSelectionChange: function (list, selectedRecords) {
        var me = this, isMulti = me.multiSelect, hasRecords = selectedRecords.length > 0;
        // Only react to selection if it is not called from setValue, and if our
        // list is
        // expanded (ignores changes to the selection model triggered elsewhere)
        if (!me.ignoreSelection && me.isExpanded) {
            if (!isMulti) {
                Ext.defer(me.collapse, 1, me);
            }
            /*
             * Only set the value here if we're in multi selection mode or we
             * have a selection. Otherwise setValue will be called with an empty
             * value which will cause the change event to fire twice.
             */
            if (isMulti || hasRecords) {
                me.setValue(selectedRecords, false);
            }
            if (hasRecords) {
                me.fireEvent('select', me, selectedRecords);
            }
            me.inputEl.focus();
 
            if (me.addAllSelector == true && me.allSelector != false) {
                if (selectedRecords.length == me.store.getTotalCount())
                    me.allSelector.addCls(me.selectedItemCls);
                else
                    me.allSelector.removeCls(me.selectedItemCls);
            }
        }
    }
});
