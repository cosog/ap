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
    collapsible: true,
    animateCollapse: false,
    maximizable: true,
    resizable: true,
    draggable: true,
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

        var makeStore = function () {
            return Ext.create('Ext.data.TreeStore', {
                root: { expanded: true, children: [] },
                fields: [
                    'text', 'alarmKey', 'alarmLevel', 'deviceId', 'deviceName',
                    'orgId', 'column', 'columnName', 'value', 'alarminfo', 'acqTime',
                    'alertId', 'status'
                ]
            });
        };

        this.storePending = makeStore();   // status = 'NEW'
        this.storeAcked   = makeStore();   // status = 'ACK'

        // 兼容旧引用（外部可能访问 win.tree / win.store）
        this.store = this.storePending;

        this.treePending = this.buildTree(this.storePending, {
            title: '待确认',
            checkable: true,
            withActions: true
        });
        this.treeAcked = this.buildTree(this.storeAcked, {
            title: '待消警',
            checkable: false,
            withActions: false
        });

        // 兼容旧引用
        this.tree = this.treePending;

        this.tabPanel = Ext.create('Ext.tab.Panel', {
            layout: 'fit',
            border: false,
            items: [this.treePending, this.treeAcked],
            listeners: {
                tabchange: function () {
                    self.updateToolbar();
                }
            }
        });

        this.tbar = [{
            xtype: 'tbtext',
            itemId: 'muteTip',
            hidden: true,
            html: '<span style="color:#dc2828;font-weight:bold;cursor:pointer;">⚠️ 当前浏览器已静音，请点击此处开启声音提醒！</span>',
            listeners: {
                click: function () {
                    var unlockEvt = new MouseEvent('click', { bubbles: true, cancelable: true, view: window });
                    document.dispatchEvent(unlockEvt);
                    this.hide();
                }
            }
        }, {
            xtype: 'tbtext',
            itemId: 'selText',
            text: '已选：0'
        }, '->', {
            text: '确认选中',
            itemId: 'ackBtn',
            disabled: true,
            handler: function () {
                var keys = self.getCheckedUnackKeys();
                if (keys.length === 0) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.checkOne);
                    return;
                }
                self.doAck(keys);
            }
        }, {
            text: '全部确认',
            itemId: 'ackAllBtn',
            handler: function () {
                var keys = self.getAllUnackKeys();
                if (keys.length === 0) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, '当前没有待确认的报警');
                    return;
                }
                self.doAck(keys);
            }
        }];

        this.items = [this.tabPanel];
        this.callParent(arguments);

        this.on('headerclick', function () {
            var evt = new MouseEvent('click', { bubbles: true, cancelable: true, view: window });
            document.dispatchEvent(evt);
        });

        this.on('beforecollapse', function () {
            this.isMinimized = true;
            if (this.tbar && this.tbar.rendered) this.tbar.getEl().hide();
            if (this.bbar && this.bbar.rendered) this.bbar.getEl().hide();
            if (this.tabPanel && this.tabPanel.rendered) this.tabPanel.getEl().hide();
        });

        this.on('collapse', function () {
            var me = this;
            Ext.defer(function () {
                me.setSize(me.collapsedWidth, me.collapsedHeight);
                me.alignToBottomRight(me.collapsedWidth, me.collapsedHeight);
                me.updateTitle();
            }, 20);
        });

        this.on('beforeexpand', function () {
            this.isMinimized = false;
            this.setSize(this.expandedWidth, this.expandedHeight);
        });

        this.on('expand', function () {
            var me = this;
            Ext.defer(function () {
                if (me.tbar && me.tbar.rendered) me.tbar.getEl().show();
                if (me.bbar && me.bbar.rendered) me.bbar.getEl().show();
                if (me.tabPanel && me.tabPanel.rendered) me.tabPanel.getEl().show();

                me.setSize(me.expandedWidth, me.expandedHeight);
                me.alignToBottomRight(me.expandedWidth, me.expandedHeight);

                if (me.updateLayout) me.updateLayout();
                if (me.treePending && me.treePending.updateLayout) me.treePending.updateLayout();
                if (me.treeAcked && me.treeAcked.updateLayout) me.treeAcked.updateLayout();

                // ★ 展开时若停在空的待确认 Tab、而待消警有内容 → 自动切到待消警
                if (me.getUnackCount() === 0 && me.countLeaf(me.storeAcked) > 0 && me.isPendingTabActive()) {
                    me.tabPanel.setActiveItem(me.treeAcked);
                }

                me.updateTitle();
            }, 20);
        });

        this.on('show', function () {
            if (!this._positioned) {
                this.alignToBottomRight(this.expandedWidth, this.expandedHeight);
                this._positioned = true;
            }
            this.updateTitle();
        });

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

    // ============================================================
    // 构建一个 TreePanel
    // ============================================================
    buildTree: function (store, opts) {
        var self = this;

        var columns = [{
            xtype: 'treecolumn',
            text: '设备 / 报警内容',
            dataIndex: 'text',
            flex: 3,
            minWidth: 200,
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
            flex: 1,
            minWidth: 60,
            renderer: function (v, meta, rec) {
                if (!rec.isLeaf()) return '';
                var s = AP.alert.AlarmManager.levelStyle[v];
                return s ? '<span style="color:' + s.color + ';">' + s.text + '</span>' : '';
            }
        }, {
            text: '报警值',
            dataIndex: 'value',
            flex: 1,
            minWidth: 80,
            renderer: function (v, meta, rec) {
                if (!rec.isLeaf()) return '';
                return v;
            }
        }, {
            text: '报警信息',
            dataIndex: 'alarminfo',
            flex: 1,
            minWidth: 100,
            renderer: function (v, meta, rec) {
                if (!rec.isLeaf()) return '';
                return v || '';
            }
        }, {
            text: '发生时间',
            dataIndex: 'acqTime',
            flex: 2,
            minWidth: 140,
            renderer: function (v, meta, rec) {
                if (!rec.isLeaf()) return '';
                return v;
            }
        }, {
            text: '状态',
            dataIndex: 'status',
            flex: 1,
            minWidth: 70,
            align: 'center',
            renderer: function (v, meta, rec) {
                if (!rec.isLeaf()) return '';
                return v === 'ACK'
                    ? '<span style="color:#888;">已确认</span>'
                    : '<span style="color:#dc2828;">待确认</span>';
            }
        }];

        if (opts.withActions) {
            columns.push({
                text: '操作',
                flex: 1,
                minWidth: 70,
                align: 'center',
                renderer: function (v, meta, rec) {
                    if (!rec.isLeaf()) return '';
                    if (rec.get('status') === 'ACK') return '';
                    return '<a href="javascript:void(0)" class="ack-one">确认</a>';
                }
            });
        }

        return Ext.create('Ext.tree.Panel', {
            title: opts.title,
            store: store,
            rootVisible: false,
            useArrows: true,
            border: false,
            columnLines: false,
            checkable: !!opts.checkable,
            columns: columns,
            viewConfig: {
                getRowClass: function (rec) {
                    if (!rec.isLeaf()) return 'alarm-row-device';
                    return rec.get('status') === 'ACK' ? 'alarm-row-ack' : 'alarm-row-unack';
                }
            },
            listeners: {
                cellclick: function (view, td, cellIndex, record, tr, rowIndex, e) {
                    if (!opts.withActions) return;
                    var target = e.getTarget('a.ack-one');
                    if (target && record.isLeaf() && record.get('status') === 'NEW') {
                        self.doAck([record.get('alarmKey')]);
                    }
                },
                checkchange: function () {
                    if (self.isPendingTabActive() || !self.tabPanel.getActiveTab()) {
                        self.updateToolbar();
                    }
                }
            }
        });
    },

    // ============================================================
    // store 路由 / 查找
    // ============================================================
    getStoreByStatus: function (status) {
        return status === 'ACK' ? this.storeAcked : this.storePending;
    },

    getTreeByStatus: function (status) {
        return status === 'ACK' ? this.treeAcked : this.treePending;
    },

    isPendingTabActive: function () {
        var active = this.tabPanel ? this.tabPanel.getActiveTab() : null;
        return active === this.treePending;
    },

    // ★ 切到「待确认」Tab（新报警 / 升级迁回时强制用户看到）
    focusPendingTab: function () {
        if (!this.tabPanel) return;
        if (this.tabPanel.getActiveTab() !== this.treePending) {
            this.tabPanel.setActiveItem(this.treePending);
        }
    },

    findLeafByKey: function (key) {
        var root = this.storePending.getRootNode();
        var n = this.findByKey(root, key);
        if (n) return n;
        root = this.storeAcked.getRootNode();
        return this.findByKey(root, key);
    },

    findByKey: function (node, key) {
        if (node.get('alarmKey') === key) return node;
        for (var i = 0; i < node.childNodes.length; i++) {
            var r = this.findByKey(node.childNodes[i], key);
            if (r) return r;
        }
        return null;
    },

    findDeviceNode: function (store, deviceId) {
        var root = store.getRootNode();
        for (var i = 0; i < root.childNodes.length; i++) {
            if (root.childNodes[i].get('deviceId') == deviceId) return root.childNodes[i];
        }
        return null;
    },

    getOrCreateDeviceNode: function (store, alarm) {
        var node = this.findDeviceNode(store, alarm.deviceId);
        if (node) return node;
        return store.getRootNode().appendChild({
            text: alarm.deviceName || ('设备' + alarm.deviceId),
            deviceId: alarm.deviceId,
            deviceName: alarm.deviceName,
            orgId: alarm.orgId,
            expanded: true,
            leaf: false
        });
    },

    buildDisplayText: function (alarm) {
        var displayText = alarm.columnName + '：' + alarm.value;
        if ((alarm.alarmType == 2 || alarm.alarmType == 5) && alarm.alarminfo) {
            displayText = alarm.columnName + '（' + alarm.alarminfo + '）：' + alarm.value;
        }
        return displayText;
    },

    // ============================================================
    // 顺序辅助：新/更新的项尽量靠前
    // ============================================================
    moveDeviceToTop: function (store, deviceNode) {
        if (!store || !deviceNode || !deviceNode.parentNode) return;
        var root = store.getRootNode();
        if (deviceNode.parentNode !== root) return;   // 只在根下移动
        if (root.firstChild === deviceNode) return;   // 已是最前
        try {
            root.insertBefore(deviceNode, root.firstChild);
        } catch (e) {}
    },

    moveLeafToTop: function (deviceNode, leafNode) {
        if (!deviceNode || !leafNode) return;
        if (deviceNode.firstChild === leafNode) return;   // 已是第一个
        try {
            deviceNode.insertBefore(leafNode, deviceNode.firstChild);
        } catch (e) {}
    },

    // ============================================================
    // 视图同步核心入口
    // ============================================================
    syncAlarmNode: function (alarm) {
        if (!alarm || !alarm.key) return;
        var targetStatus = (alarm.ackStatus == 1) ? 'ACK' : 'NEW';
        var existing = this.findLeafByKey(alarm.key);

        if (!existing) {
            this.insertLeaf(alarm, targetStatus);
            return;
        }
        if (existing.get('status') !== targetStatus) {
            // 迁移：insertLeaf 内部会把设备+叶子都挪到最前；再删源
            this.insertLeaf(alarm, targetStatus);
            this.removeLeafNode(existing);
            this.updateToolbar();
            return;
        }
        // 同状态原地更新：更新后同样挪前
        this.applyAlarmToNode(existing, alarm, this.getStoreByStatus(targetStatus));
    },

    insertLeaf: function (alarm, status) {
        // 防脏：两 store 同 key 全清（正常情况下此处另一 store 不会有该 key）
        this.removeLeafEverywhere(alarm.key);

        var store = this.getStoreByStatus(status);
        var deviceNode = this.getOrCreateDeviceNode(store, alarm);

        var leafNode = deviceNode.appendChild({
            text: this.buildDisplayText(alarm),
            leaf: true,
            alarmKey: alarm.key,
            alarmLevel: alarm.alarmLevel,
            deviceId: alarm.deviceId,
            deviceName: alarm.deviceName,
            orgId: alarm.orgId,
            column: alarm.column,
            columnName: alarm.columnName,
            value: alarm.value,
            alarminfo: alarm.alarminfo || '',
            acqTime: alarm.acqTime,
            alertId: alarm.alertId,
            status: status,
            checked: false
        });

        // ★ 新项排到设备下第一个；设备挪到根下最前
        this.moveLeafToTop(deviceNode, leafNode);
        this.moveDeviceToTop(store, deviceNode);

        deviceNode.expand();
        this.updateToolbar();

        if (status === 'NEW' && AP.alert.AlarmSoundManager && !AP.alert.AlarmSoundManager.unlocked) {
            var tip = this.down('#muteTip');
            if (tip) tip.show();
        }
    },

    applyAlarmToNode: function (node, alarm, store) {
        node.set('text', this.buildDisplayText(alarm));
        node.set('value', alarm.value);
        node.set('alarminfo', alarm.alarminfo || '');
        node.set('alarmLevel', alarm.alarmLevel);
        node.set('acqTime', alarm.acqTime);
        if (alarm.alertId) node.set('alertId', alarm.alertId);
        node.commit();

        // ★ 更新项排到设备下第一个；设备挪到根下最前
        var deviceNode = node.parentNode;
        if (deviceNode) {
            this.moveLeafToTop(deviceNode, node);
            if (store) this.moveDeviceToTop(store, deviceNode);
        }

        this.updateToolbar();
    },

    removeLeafNode: function (node) {
        var parent = node.parentNode;
        if (!parent) return;
        parent.removeChild(node);
        if (parent.childNodes.length === 0 && !parent.isRoot()) {
            parent.remove();
        }
    },

    removeLeafEverywhere: function (key) {
        var n = this.findByKey(this.storePending.getRootNode(), key);
        if (n) this.removeLeafNode(n);
        n = this.findByKey(this.storeAcked.getRootNode(), key);
        if (n) this.removeLeafNode(n);
    },

    removeAlarmNode: function (key) {
        this.removeLeafEverywhere(key);
        this.updateToolbar();

        // ★ 待确认为 0 就折叠（不管待消警是否有项）
        if (this.getUnackCount() === 0) {
            // 折叠前若停在空的待确认 Tab、而待消警有内容 → 切到待消警
            var ackCount = this.countLeaf(this.storeAcked);
            if (ackCount > 0 && this.tabPanel) {
                this.tabPanel.setActiveItem(this.treeAcked);
            }
            this.collapse();
            this.stopBlink();
            AP.alert.AlarmSoundManager.stop();
            var tip = this.down('#muteTip');
            if (tip) tip.hide();
        } else {
            AP.alert.AlarmManager._refreshSoundAfterAck();
        }
    },

    // ============================================================
    // 确认
    // ============================================================
    getCheckedUnackKeys: function () {
        var keys = [];
        var checkedNodes = this.treePending.getChecked();
        Ext.Array.each(checkedNodes, function (node) {
            if (node.isLeaf() && node.get('alarmKey') && node.get('status') === 'NEW') {
                keys.push(node.get('alarmKey'));
            }
        });
        return keys;
    },

    getAllUnackKeys: function () {
        var keys = [];
        this.storePending.getRootNode().eachChild(function (deviceNode) {
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
            // ack 已把 alarm.ackStatus 置 1；这里只负责把视图同步过去
            Ext.Array.each(keys, function (key) {
                var a = AP.alert.AlarmManager.alarms[key];
                if (a) self.syncAlarmNode(a);
            });
            self.updateToolbar();
            AP.alert.AlarmManager._refreshSoundAfterAck();
        });
    },

    markRemoteAck: function (ackSet) {
        var self = this;
        Ext.Object.each(AP.alert.AlarmManager.alarms, function (key, alarm) {
            if (alarm.alertId && ackSet[alarm.alertId]) {
                alarm.ackStatus = 1;
                self.syncAlarmNode(alarm);
            }
        });
        this.updateToolbar();
        AP.alert.AlarmManager._refreshSoundAfterAck();
    },

    // ============================================================
    // 统计
    // ============================================================
    countLeaf: function (store) {
        var n = 0;
        store.getRootNode().eachChild(function (d) {
            d.eachChild(function (leaf) { if (leaf.isLeaf()) n++; });
        });
        return n;
    },

    getTotalLeafCount: function () {
        return this.countLeaf(this.storePending) + this.countLeaf(this.storeAcked);
    },

    getUnackCount: function () {
        return this.countLeaf(this.storePending);
    },

    hasUnacknowledged: function () {
        return this.getUnackCount() > 0;
    },

    hasUnackAtLevel: function (level) {
        var found = false;
        this.storePending.getRootNode().eachChild(function (d) {
            d.eachChild(function (leaf) {
                if (leaf.isLeaf() && leaf.get('status') === 'NEW' && leaf.get('alarmLevel') == level) found = true;
            });
        });
        return found;
    },

    // ============================================================
    // 工具条 / 标题 / 闪烁
    // ============================================================
    updateToolbar: function () {
        var unack = this.getUnackCount();
        var acking = this.countLeaf(this.storeAcked);

        var sel = this.down('#selText');
        var ackBtn = this.down('#ackBtn');
        var ackAllBtn = this.down('#ackAllBtn');
        var pendingActive = this.isPendingTabActive();

        if (sel) {
            sel.setText(pendingActive ? ('已选：' + this.getCheckedUnackKeys().length) : ('待消警：' + acking));
        }
        if (ackBtn) {
            ackBtn.setDisabled(!pendingActive || this.getCheckedUnackKeys().length === 0);
        }
        if (ackAllBtn) {
            ackAllBtn.setDisabled(!pendingActive || unack === 0);
        }

        if (this.treePending) this.treePending.setTitle('待确认 (' + unack + ')');
        if (this.treeAcked)   this.treeAcked.setTitle('待消警 (' + acking + ')');

        this.updateTitle();
    },

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
    },

    alignToBottomRight: function (w, h) {
        var docW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
        var docH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
        var x = Math.max(0, docW - w - 20);
        var y = Math.max(0, docH - h - 40);
        this.setPosition(x, y);
    }
});