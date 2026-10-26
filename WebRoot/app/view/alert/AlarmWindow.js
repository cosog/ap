Ext.define('AP.view.alert.AlarmWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.alarmWindow',
    id: 'AlarmWindow_Id',

    title: '报警',
    width: 820,
    height: 520,
    layout: 'fit',
    modal: false,
    closeAction: 'hide',        
    closable: false,            
    collapsible: true,          // ★ 恢复原生折叠按钮
    animateCollapse: false,     // ★ 禁用动画，防止动画结束后坐标和尺寸被内部覆盖
    maximizable: false,
    resizable: true,
    draggable: true,            // ★ 允许拖动
    bodyPadding: 0,

    expandedWidth: 820,
    expandedHeight: 520,
    collapsedWidth: 260,
    collapsedHeight: 32,

    isMinimized: false,
    blinkTask: null,
    blinkOn: false,

    initComponent: function () {
        var self = this;

        this.store = Ext.create('Ext.data.TreeStore', {
            root: { expanded: true, children: [] },
            fields: [
                'text', 'alarmKey', 'alarmLevel', 'deviceId', 'deviceName',
                'orgId', 'column', 'columnName', 'value', 'acqTime',
                'alertId', 'status'
            ]
        });

        this.tree = Ext.create('Ext.tree.Panel', {
            store: this.store,
            rootVisible: false,
            useArrows: true,
            border: false,
            columns: [{
                xtype: 'treecolumn',
                text: '设备 / 报警内容',
                dataIndex: 'text',
                width: 300,
                renderer: function (v, meta, rec) {
                    if (rec.isLeaf() && rec.get('status') === 'NEW') {
                        var s = AP.alert.AlarmManager.levelStyle[rec.get('alarmLevel')];
                        if (s) {
                            return '<span style="color:' + s.color + ';font-weight:bold;">' + v + '</span>';
                        }
                    }
                    return v;
                }
            }, {
                text: '级别',
                dataIndex: 'alarmLevel',
                width: 70,
                renderer: function (v) {
                    var s = AP.alert.AlarmManager.levelStyle[v];
                    return s ? '<span style="color:' + s.color + ';">' + s.text + '</span>' : '';
                }
            }, {
                text: '当前值',
                dataIndex: 'value',
                width: 100
            }, {
                text: '发生时间',
                dataIndex: 'acqTime',
                width: 150
            }, {
                text: '状态',
                dataIndex: 'status',
                width: 70,
                renderer: function (v) {
                    return v === 'ACK' ? '<span style="color:#888;">已确认</span>' : '待确认';
                }
            }, {
                text: '操作',
                width: 80,
                renderer: function (v, meta, rec) {
                    if (!rec.isLeaf()) return '';
                    if (rec.get('status') === 'ACK') return '';
                    return '<a href="javascript:void(0)" class="ack-one">确认</a>';
                }
            }],
            selModel: {
                type: 'checkboxmodel',
                checkOnly: true,
                showHeaderCheckbox: false,
                listeners: {
                    selectionchange: function () {
                        self.updateToolbar();
                    }
                }
            },
            viewConfig: {
                getRowClass: function (rec) {
                    if (!rec.isLeaf()) return 'alarm-row-device';
                    return rec.get('status') === 'ACK' ? 'alarm-row-ack' : 'alarm-row-unack';
                }
            },
            listeners: {
                cellclick: function (view, td, cellIndex, record, tr, rowIndex, e) {
                    var target = e.getTarget('a.ack-one');
                    if (target && record.isLeaf() && record.get('status') === 'NEW') {
                        self.doAck([record.get('alarmKey')]);
                    }
                }
            }
        });

        this.tbar = [{
            xtype: 'tbtext',
            itemId: 'statText',
            text: '待确认：0'
        }];

        this.bbar = [{
            xtype: 'tbtext',
            itemId: 'selText',
            text: '已选：0'
        }, '->', {
            text: '确认选中',
            itemId: 'ackBtn',
            disabled: true,
            handler: function () {
                self.doAck(self.getCheckedUnackKeys());
            }
        }, {
            text: '全部确认',
            handler: function () {
                self.doAck(self.getAllUnackKeys());
            }
        }];

        this.items = [this.tree];
        this.callParent(arguments);

        // ============ 核心事件接管 ============

        // 1. 折叠前：直接操作 DOM 隐藏组件，防止内部重绘导致工具栏露出
        this.on('beforecollapse', function () {
            this.isMinimized = true;
            if (this.tbar && this.tbar.rendered) this.tbar.getEl().hide();
            if (this.bbar && this.bbar.rendered) this.bbar.getEl().hide();
            if (this.tree && this.tree.rendered) this.tree.getEl().hide();
        });

        // 2. 折叠后：强制设置小尺寸并绝对定位
        this.on('collapse', function () {
            var me = this;
            // 延迟 20ms 避免被 ExtJS 默认的折叠处理覆盖
            Ext.defer(function () {
                me.setSize(me.collapsedWidth, me.collapsedHeight);
                me.alignToBottomRight(me.collapsedWidth, me.collapsedHeight);
                me.updateTitle();
            }, 20);
        });

        // 3. 展开前：提前重置大尺寸，防止拿 260 宽度去展开
        this.on('beforeexpand', function () {
            this.isMinimized = false;
            this.setSize(this.expandedWidth, this.expandedHeight);
        });

        // 4. 展开后：恢复 DOM 显示，强制刷新布局并定位
        this.on('expand', function () {
            var me = this;
            Ext.defer(function () {
                if (me.tbar && me.tbar.rendered) me.tbar.getEl().show();
                if (me.bbar && me.bbar.rendered) me.bbar.getEl().show();
                if (me.tree && me.tree.rendered) me.tree.getEl().show();
                
                me.setSize(me.expandedWidth, me.expandedHeight);
                me.alignToBottomRight(me.expandedWidth, me.expandedHeight);
                
                me.doLayout();
                if (me.tree) me.tree.doLayout();
                me.updateTitle();
            }, 20);
        });

        // 首次显示贴右下角
        this.on('show', function () {
            if (!this._positioned) {
                this.alignToBottomRight(this.expandedWidth, this.expandedHeight);
                this._positioned = true;
            }
            this.updateTitle();
        });
        
        // 浏览器窗口尺寸变化时，重新对齐
        this.on('afterrender', function () {
            var me = this;
            var resizeTask = null;
            Ext.on('resize', function () {
                if (resizeTask) clearTimeout(resizeTask);
                resizeTask = setTimeout(function () {
                    if (me.isVisible()) {
                        if (me.isMinimized) {
                            me.alignToBottomRight(me.collapsedWidth, me.collapsedHeight);
                        } else {
                            me.alignToBottomRight(me.expandedWidth, me.expandedHeight);
                        }
                    }
                }, 200);
            });
        });
    },

    // ============ 绝对定位方法 ============
    alignToBottomRight: function (w, h) {
        var docW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
        var docH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
        var x = Math.max(0, docW - w - 20);
        var y = Math.max(0, docH - h - 20);
        this.setPosition(x, y);
    },

    // ============ 节点操作 ============
    addAlarmNode: function (alarm) {
        var deviceNode = this.getOrCreateDeviceNode(alarm);
        var exists = deviceNode.findChild('alarmKey', alarm.key, true);
        if (exists) {
            this.updateNode(exists, alarm);
            return;
        }
        deviceNode.appendChild({
            text: alarm.columnName + '：' + alarm.value,
            leaf: true,
            alarmKey: alarm.key,
            alarmLevel: alarm.alarmLevel,
            deviceId: alarm.deviceId,
            deviceName: alarm.deviceName,
            orgId: alarm.orgId,
            column: alarm.column,
            columnName: alarm.columnName,
            value: alarm.value,
            acqTime: alarm.acqTime,
            alertId: alarm.alertId,
            status: 'NEW',
            checked: false 
        });
        deviceNode.expand();
        this.updateToolbar();
    },

    updateAlarmNode: function (alarm) {
        var deviceNode = this.findDeviceNode(alarm.deviceId);
        if (!deviceNode) return;
        var node = deviceNode.findChild('alarmKey', alarm.key, true);
        if (node) this.updateNode(node, alarm);
    },

    updateNode: function (node, alarm) {
        node.set('text', alarm.columnName + '：' + alarm.value);
        node.set('value', alarm.value);
        node.set('alarmLevel', alarm.alarmLevel);
        node.set('acqTime', alarm.acqTime);
        if (alarm.alertId) node.set('alertId', alarm.alertId);

        var key = node.get('alarmKey');
        if (key && !AP.alert.AlarmManager.ackKeys[key] && node.get('status') === 'ACK') {
            node.set('status', 'NEW');
            node.set('checked', false);
        }
        this.updateToolbar();
    },

    removeAlarmNode: function (key) {
        var root = this.store.getRootNode();
        var node = this.findByKey(root, key);
        if (node && node.parentNode) {
            var parent = node.parentNode;
            parent.removeChild(node);
            if (parent.childNodes.length === 0 && !parent.isRoot()) {
                parent.remove();
            }
        }
        this.updateToolbar();
        if (this.getTotalLeafCount() === 0) {
            this.collapse();   // 原生折叠
            this.stopBlink();
        }
    },

    findByKey: function (node, key) {
        if (node.get('alarmKey') === key) return node;
        for (var i = 0; i < node.childNodes.length; i++) {
            var r = this.findByKey(node.childNodes[i], key);
            if (r) return r;
        }
        return null;
    },

    getOrCreateDeviceNode: function (alarm) {
        var node = this.findDeviceNode(alarm.deviceId);
        if (node) return node;
        return this.store.getRootNode().appendChild({
            text: alarm.deviceName || ('设备' + alarm.deviceId),
            deviceId: alarm.deviceId,
            deviceName: alarm.deviceName,
            orgId: alarm.orgId,
            expanded: true,
            leaf: false
        });
    },

    findDeviceNode: function (deviceId) {
        var root = this.store.getRootNode();
        for (var i = 0; i < root.childNodes.length; i++) {
            if (root.childNodes[i].get('deviceId') == deviceId) return root.childNodes[i];
        }
        return null;
    },

    // ============ 选择与确认 ============
    getCheckedUnackKeys: function () {
        var keys = [];
        var root = this.store.getRootNode();
        root.eachChild(function (deviceNode) {
            deviceNode.eachChild(function (leaf) {
                if (leaf.isLeaf()
                    && leaf.get('alarmKey')
                    && leaf.get('status') === 'NEW'
                    && leaf.get('checked') === true) {
                    keys.push(leaf.get('alarmKey'));
                }
            });
        });
        return keys;
    },

    getAllUnackKeys: function () {
        var keys = [];
        this.store.getRootNode().eachChild(function (deviceNode) {
            deviceNode.eachChild(function (leaf) {
                if (leaf.isLeaf() && leaf.get('status') === 'NEW') {
                    keys.push(leaf.get('alarmKey'));
                }
            });
        });
        return keys;
    },

    doAck: function (keys) {
        if (!keys || keys.length === 0) return;
        var self = this;

        AP.alert.AlarmManager.ack(keys, function (ok) {
            if (!ok) return;

            Ext.Array.each(keys, function (key) {
                var node = self.findByKey(self.store.getRootNode(), key);
                if (node) {
                    node.set('status', 'ACK');
                    node.set('checked', false);
                }
            });
            self.updateToolbar();

            var playing = AP.alert.AlarmSoundManager.currentLevel;
            if (playing !== null && !self.hasUnackAtLevel(playing)) {
                AP.alert.AlarmSoundManager.stop();
            }

            if (!self.hasUnacknowledged()) {
                self.stopBlink();
                self.collapse();   // 原生折叠
            }
        });
    },

    // ============ 统计与标题 ============
    getTotalLeafCount: function () {
        var n = 0;
        this.store.getRootNode().eachChild(function (d) {
            d.eachChild(function (leaf) { if (leaf.isLeaf()) n++; });
        });
        return n;
    },

    getUnackCount: function () {
        var n = 0;
        this.store.getRootNode().eachChild(function (d) {
            d.eachChild(function (leaf) {
                if (leaf.isLeaf() && leaf.get('status') === 'NEW') n++;
            });
        });
        return n;
    },

    hasUnacknowledged: function () {
        return this.getUnackCount() > 0;
    },

    hasUnackAtLevel: function (level) {
        var found = false;
        this.store.getRootNode().eachChild(function (d) {
            d.eachChild(function (leaf) {
                if (leaf.isLeaf() && leaf.get('status') === 'NEW' && leaf.get('alarmLevel') == level) found = true;
            });
        });
        return found;
    },

    updateToolbar: function () {
        var unack = this.getUnackCount();
        var checked = this.getCheckedUnackKeys().length;
        var stat = this.down('#statText');
        if (stat) stat.setText('待确认：' + unack);
        var sel = this.down('#selText');
        if (sel) sel.setText('已选：' + checked);
        var btn = this.down('#ackBtn');
        if (btn) btn.setDisabled(checked === 0);
        this.updateTitle();
    },

    // ============ 闪烁 ============
    startBlink: function () {
        var self = this;
        if (this.blinkTask) return;
        this.blinkOn = false;
        this.blinkTask = setInterval(function () {
            if (!self.hasUnacknowledged()) {
                self.stopBlink();
                return;
            }
            self.blinkOn = !self.blinkOn;
            self.updateTitle();
        }, 800);
    },

    stopBlink: function () {
        if (this.blinkTask) {
            clearInterval(this.blinkTask);
            this.blinkTask = null;
        }
        this.blinkOn = false;
        this.updateTitle();
    },

    updateTitle: function () {
        var unack = this.getUnackCount();
        var total = this.getTotalLeafCount();
        var text = '报警';
        if (total > 0) {
            text = '报警 (' + unack + '/' + total + ')';
        }
        var html;
        if (unack > 0 && this.blinkOn) {
            html = '<span style="color:#dc2828;">● ' + text + '</span>';
        } else if (unack > 0) {
            html = '<span style="color:#dc2828;">○ ' + text + '</span>';
        } else {
            html = text;
        }
        if (html === this._lastTitleHtml) return;
        this._lastTitleHtml = html;
        this.setTitle(html);
    }
});