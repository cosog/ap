Ext.define('AP.view.alert.AlarmWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.alarmWindow',
    id: 'AlarmWindow_Id',

    title: '报警',
    width: 900,
    height: 540,
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

    expandedWidth: 900,
    expandedHeight: 540,
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
                    'alertId', 'status',
                    'suppressReason', 'suppressUser', 'suppressTime', 'suppressIp', 'suppressEndTime'
                ]
            });
        };

        this.storePending = makeStore();      // status = 'NEW'
        this.storeAcked = makeStore();        // status = 'ACK'
        this.storeSuppressed = makeStore();   // status = 'SUPPRESSED'

        // 兼容旧引用
        this.store = this.storePending;
        this.tree = null;

        // 待确认树：确认操作
        this.treePending = this.buildTree(this.storePending, {
            title: '待确认',
            checkable: true,
            withAckAction: true,
            toolbarItems: [{
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
                xtype: 'tbtext', itemId: 'pendingSelText', text: '已选：0'
            }, '->', {
                text: '确认选中',
                itemId: 'ackBtn',
                disabled: true,
                handler: function () {
                    var keys = self.getCheckedPendingKeys();
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
            }]
        });

        // 待消警树：人工消警操作
        this.treeAcked = this.buildTree(this.storeAcked, {
            title: '待消警',
            checkable: true,
            withSuppressAction: true,
            toolbarItems: [{
                xtype: 'tbtext', itemId: 'ackedSelText', text: '已选：0'
            }, {
                xtype: 'tbtext', itemId: 'ackedCountText', text: '已确认：0'
            }, '->', {
                text: '人工消警选中',
                itemId: 'suppressBtn',
                disabled: true,
                handler: function () {
                    var keys = self.getCheckedAckedKeys();
                    if (keys.length === 0) {
                        Ext.Msg.alert(loginUserLanguageResource.tip, '请勾选要人工消警的报警');
                        return;
                    }
                    self.openSuppressDialog(keys);
                }
            }, {
                text: '全部人工消警',
                itemId: 'suppressAllBtn',
                handler: function () {
                    var keys = self.getAllAckedKeys();
                    if (keys.length === 0) {
                        Ext.Msg.alert(loginUserLanguageResource.tip, '当前没有待消警的报警');
                        return;
                    }
                    self.openSuppressDialog(keys);
                }
            }]
        });

        // 人工消警树：只读
        this.treeSuppressed = this.buildTree(this.storeSuppressed, {
            title: '人工消警',
            checkable: false,
            withSuppressAction: false,
            withAckAction: false,
            toolbarItems: [{
                xtype: 'tbtext', itemId: 'suppressedCountText', text: '已消警：0'
            }]
        });

        // 兼容旧引用
        this.tree = this.treePending;

        this.tabPanel = Ext.create('Ext.tab.Panel', {
            layout: 'fit',
            border: false,
            items: [this.treePending, this.treeAcked, this.treeSuppressed],
            listeners: {
                tabchange: function () {
                    self.updateToolbar();
                }
            }
        });

        this.items = [this.tabPanel];
        this.callParent(arguments);

        this.on('headerclick', function () {
            var evt = new MouseEvent('click', { bubbles: true, cancelable: true, view: window });
            document.dispatchEvent(evt);
        });

        this.on('beforecollapse', function () {
            this.isMinimized = true;
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
                me.setSize(me.expandedWidth, me.expandedHeight);
                me.alignToBottomRight(me.expandedWidth, me.expandedHeight);
                if (me.updateLayout) me.updateLayout();
                if (me.treePending && me.treePending.updateLayout) me.treePending.updateLayout();
                if (me.treeAcked && me.treeAcked.updateLayout) me.treeAcked.updateLayout();
                if (me.treeSuppressed && me.treeSuppressed.updateLayout) me.treeSuppressed.updateLayout();
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
                        if (me.isMinimized) me.alignToBottomRight(me.collapsedWidth, me.collapsedHeight);
                        else me.alignToBottomRight(me.expandedWidth, me.expandedHeight);
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
                    if (s) return '<span style="color:' + s.color + ';font-weight:bold;">' + v + '</span>';
                }
                return v;
            }
        }, {
            text: '级别', dataIndex: 'alarmLevel', flex: 1, minWidth: 60,
            renderer: function (v, meta, rec) {
                if (!rec.isLeaf()) return '';
                var s = AP.alert.AlarmManager.levelStyle[v];
                return s ? '<span style="color:' + s.color + ';">' + s.text + '</span>' : '';
            }
        }, {
            text: '报警值', dataIndex: 'value', flex: 1, minWidth: 80,
            renderer: function (v, meta, rec) { return rec.isLeaf() ? v : ''; }
        }, {
            text: '报警信息', dataIndex: 'alarminfo', flex: 1, minWidth: 100,
            renderer: function (v, meta, rec) { return rec.isLeaf() ? (v || '') : ''; }
        }, {
            text: '发生时间', dataIndex: 'acqTime', flex: 2, minWidth: 140,
            renderer: function (v, meta, rec) { return rec.isLeaf() ? v : ''; }
        }, {
            text: '状态', dataIndex: 'status', flex: 1, minWidth: 80, align: 'center',
            renderer: function (v, meta, rec) {
                if (!rec.isLeaf()) return '';
                if (v === 'SUPPRESSED') return '<span style="color:#0e7490;">已消警</span>';
                if (v === 'ACK') return '<span style="color:#888;">已确认</span>';
                return '<span style="color:#dc2828;">待确认</span>';
            }
        }];

        if (opts.withAckAction) {
            columns.push({
                text: '操作', flex: 1, minWidth: 70, align: 'center',
                renderer: function (v, meta, rec) {
                    if (!rec.isLeaf()) return '';
                    if (rec.get('status') !== 'NEW') return '';
                    return '<a href="javascript:void(0)" class="ack-one">确认</a>';
                }
            });
        }

        if (opts.withSuppressAction) {
            columns.push({
                text: '操作', flex: 1, minWidth: 90, align: 'center',
                renderer: function (v, meta, rec) {
                    if (!rec.isLeaf()) return '';
                    if (rec.get('status') !== 'ACK') return '';
                    return '<a href="javascript:void(0)" class="suppress-one">人工消警</a>';
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
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'top',
                items: opts.toolbarItems || []
            }],
            viewConfig: {
                getRowClass: function (rec) {
                    if (!rec.isLeaf()) return 'alarm-row-device';
                    if (rec.get('status') === 'SUPPRESSED') return 'alarm-row-suppressed';
                    if (rec.get('status') === 'ACK') return 'alarm-row-ack';
                    return 'alarm-row-unack';
                }
            },
            listeners: {
                cellclick: function (view, td, cellIndex, record, tr, rowIndex, e) {
                    if (!record.isLeaf()) return;
                    var ackTarget = e.getTarget('a.ack-one');
                    if (ackTarget && opts.withAckAction && record.get('status') === 'NEW') {
                        self.doAck([record.get('alarmKey')]);
                        return;
                    }
                    var suppTarget = e.getTarget('a.suppress-one');
                    if (suppTarget && opts.withSuppressAction && record.get('status') === 'ACK') {
                        self.openSuppressDialog([record.get('alarmKey')]);
                        return;
                    }
                },
                checkchange: function () {
                    self.updateToolbar();
                }
            }
        });
    },

    // ============================================================
    // store 路由 / 查找
    // ============================================================
    getStoreByStatus: function (status) {
        if (status === 'SUPPRESSED') return this.storeSuppressed;
        if (status === 'ACK') return this.storeAcked;
        return this.storePending;
    },

    isPendingTabActive: function () {
        var active = this.tabPanel ? this.tabPanel.getActiveTab() : null;
        return active === this.treePending;
    },

    focusPendingTab: function () {
        if (!this.tabPanel) return;
        if (this.tabPanel.getActiveTab() !== this.treePending) {
            this.tabPanel.setActiveItem(this.treePending);
        }
    },

    findLeafByKey: function (key) {
        var stores = [this.storePending, this.storeAcked, this.storeSuppressed];
        for (var i = 0; i < stores.length; i++) {
            var n = this.findByKey(stores[i].getRootNode(), key);
            if (n) return n;
        }
        return null;
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

    moveDeviceToTop: function (store, deviceNode) {
        if (!store || !deviceNode || !deviceNode.parentNode) return;
        var root = store.getRootNode();
        if (deviceNode.parentNode !== root) return;
        if (root.firstChild === deviceNode) return;
        try { root.insertBefore(deviceNode, root.firstChild); } catch (e) {}
    },

    moveLeafToTop: function (deviceNode, leafNode) {
        if (!deviceNode || !leafNode) return;
        if (deviceNode.firstChild === leafNode) return;
        try { deviceNode.insertBefore(leafNode, deviceNode.firstChild); } catch (e) {}
    },

    // ============================================================
    // 视图同步核心入口
    // ============================================================
    syncAlarmNode: function (alarm) {
        if (!alarm || !alarm.key) return;
        var targetStatus = (alarm.suppressStatus == 2) ? 'SUPPRESSED' : (alarm.ackStatus == 1 ? 'ACK' : 'NEW');
        var existing = this.findLeafByKey(alarm.key);

        if (!existing) {
            this.insertLeaf(alarm, targetStatus);
            return;
        }
        if (existing.get('status') !== targetStatus) {
            // 迁移
            this.insertLeaf(alarm, targetStatus);
            this.removeLeafNode(existing);
            this.updateToolbar();
            return;
        }
        this.applyAlarmToNode(existing, alarm, this.getStoreByStatus(targetStatus));
    },

    insertLeaf: function (alarm, status) {
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
            checked: false,
            suppressReason: alarm.suppressReason || '',
            suppressUser: alarm.suppressUser || '',
            suppressTime: alarm.suppressTime || '',
            suppressIp: alarm.suppressIp || '',
            suppressEndTime: alarm.suppressEndTime || ''
        });

        this.moveLeafToTop(deviceNode, leafNode);
        this.moveDeviceToTop(store, deviceNode);

        deviceNode.expand();
        this.updateToolbar();
    },

    applyAlarmToNode: function (node, alarm, store) {
        node.set('text', this.buildDisplayText(alarm));
        node.set('value', alarm.value);
        node.set('alarminfo', alarm.alarminfo || '');
        node.set('alarmLevel', alarm.alarmLevel);
        node.set('acqTime', alarm.acqTime);
        node.set('suppressReason', alarm.suppressReason || '');
        node.set('suppressUser', alarm.suppressUser || '');
        node.set('suppressTime', alarm.suppressTime || '');
        node.set('suppressIp', alarm.suppressIp || '');
        node.set('suppressEndTime', alarm.suppressEndTime || '');
        if (alarm.alertId) node.set('alertId', alarm.alertId);
        node.commit();

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
        var stores = [this.storePending, this.storeAcked, this.storeSuppressed];
        for (var i = 0; i < stores.length; i++) {
            var n = this.findByKey(stores[i].getRootNode(), key);
            if (n) this.removeLeafNode(n);
        }
    },

    removeAlarmNode: function (key) {
        this.removeLeafEverywhere(key);
        this.updateToolbar();

        if (this.getUnackCount() === 0) {
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
    // 确认 / 消警
    // ============================================================
    getCheckedPendingKeys: function () {
        var keys = [];
        Ext.Array.each(this.treePending.getChecked(), function (node) {
            if (node.isLeaf() && node.get('status') === 'NEW') keys.push(node.get('alarmKey'));
        });
        return keys;
    },

    getCheckedAckedKeys: function () {
        var keys = [];
        Ext.Array.each(this.treeAcked.getChecked(), function (node) {
            if (node.isLeaf() && node.get('status') === 'ACK') keys.push(node.get('alarmKey'));
        });
        return keys;
    },

    getAllUnackKeys: function () {
        var keys = [];
        this.storePending.getRootNode().eachChild(function (d) {
            d.eachChild(function (leaf) {
                if (leaf.isLeaf() && leaf.get('status') === 'NEW') keys.push(leaf.get('alarmKey'));
            });
        });
        return keys;
    },

    getAllAckedKeys: function () {
        var keys = [];
        this.storeAcked.getRootNode().eachChild(function (d) {
            d.eachChild(function (leaf) {
                if (leaf.isLeaf() && leaf.get('status') === 'ACK') keys.push(leaf.get('alarmKey'));
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
                var a = AP.alert.AlarmManager.alarms[key];
                if (a) self.syncAlarmNode(a);
            });
            self.updateToolbar();
            AP.alert.AlarmManager._refreshSoundAfterAck();
        });
    },

    openSuppressDialog: function (keys) {
        if (!keys || keys.length === 0) return;
        var self = this;
        var dlg = Ext.create('Ext.window.Window', {
            title: '人工消警（' + keys.length + ' 条）',
            modal: true,
            width: 420,
            layout: 'fit',
            items: [{
                xtype: 'form',
                bodyPadding: 12,
                items: [{
                    xtype: 'textarea',
                    fieldLabel: '消警原因',
                    name: 'reason',
                    allowBlank: false,
                    height: 80,
                    maxLength: 500,
                    emptyText: '必填，最多 500 字'
                }, {
                    xtype: 'numberfield',
                    fieldLabel: '持续时长（小时）',
                    name: 'durationHours',
                    allowBlank: false,
                    minValue: 0.1,
                    maxValue: 8760,
                    decimalPrecision: 1,
                    value: 24,
                    emptyText: '浮点数，如 1.5'
                }]
            }],
            buttons: [{
                text: '取消',
                handler: function () { dlg.close(); }
            }, {
                text: '确定',
                handler: function () {
                    var form = dlg.down('form').getForm();
                    if (!form.isValid()) return;
                    var v = form.getValues();
                    var alertIds = [];
                    Ext.Array.each(keys, function (key) {
                        var a = AP.alert.AlarmManager.alarms[key];
                        if (a && a.alertId) alertIds.push(a.alertId);
                    });
                    if (alertIds.length === 0) {
                        Ext.Msg.alert(loginUserLanguageResource.tip, '没有可消警的报警（未同步服务端）');
                        return;
                    }
                    AP.alert.AlarmManager.suppressAlarm(alertIds, v.reason, parseFloat(v.durationHours), function (ok, data) {
                        if (!ok) return;
                        Ext.Array.each(keys, function (key) {
                            var a = AP.alert.AlarmManager.alarms[key];
                            if (a && alertIds.indexOf(a.alertId) >= 0 || (a && !a.alertId)) {
                                // 已消警的本地更新
                                if (a) {
                                    a.suppressStatus = 2;
                                    a.suppressReason = v.reason;
                                    a.suppressUser = data.suppressUser;
                                    a.suppressTime = data.suppressTime;
                                    a.suppressIp = data.suppressIp;
                                    a.suppressEndTime = data.suppressEndTime;
                                    self.syncAlarmNode(a);
                                }
                            }
                        });
                        dlg.close();
                        self.updateToolbar();
                        AP.alert.AlarmManager._refreshSoundAfterAck();
                    });
                }
            }]
        });
        dlg.show();
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
        return this.countLeaf(this.storePending) + this.countLeaf(this.storeAcked) + this.countLeaf(this.storeSuppressed);
    },

    getUnackCount: function () {
        return this.countLeaf(this.storePending);
    },

    hasUnacknowledged: function () {
        return this.getUnackCount() > 0;
    },

    // ============================================================
    // 工具条 / 标题 / 闪烁
    // ============================================================
    updateToolbar: function () {
        var unack = this.getUnackCount();
        var acking = this.countLeaf(this.storeAcked);
        var suppressed = this.countLeaf(this.storeSuppressed);

        var pendingSel = this.down('#pendingSelText');
        if (pendingSel) pendingSel.setText('已选：' + this.getCheckedPendingKeys().length);
        var ackBtn = this.down('#ackBtn');
        if (ackBtn) ackBtn.setDisabled(this.getCheckedPendingKeys().length === 0);
        var ackAllBtn = this.down('#ackAllBtn');
        if (ackAllBtn) ackAllBtn.setDisabled(unack === 0);

        var ackedSel = this.down('#ackedSelText');
        if (ackedSel) ackedSel.setText('已选：' + this.getCheckedAckedKeys().length);
        var ackedCount = this.down('#ackedCountText');
        if (ackedCount) ackedCount.setText('已确认：' + acking);
        var suppressBtn = this.down('#suppressBtn');
        if (suppressBtn) suppressBtn.setDisabled(this.getCheckedAckedKeys().length === 0);
        var suppressAllBtn = this.down('#suppressAllBtn');
        if (suppressAllBtn) suppressAllBtn.setDisabled(acking === 0);

        var suppCount = this.down('#suppressedCountText');
        if (suppCount) suppCount.setText('已消警：' + suppressed);

        if (this.treePending) this.treePending.setTitle('待确认 (' + unack + ')');
        if (this.treeAcked) this.treeAcked.setTitle('待消警 (' + acking + ')');
        if (this.treeSuppressed) {
            this.treeSuppressed.setTitle('人工消警 (' + suppressed + ')');
            var tab = this.treeSuppressed.tab;
            if (tab) {
                if (suppressed > 0) tab.show();
                else if (this.tabPanel.getActiveTab() === this.treeSuppressed) {
                    // 如果正在人工消警 Tab 且清空了，切回待消警或待确认
                    this.tabPanel.setActiveItem(unack > 0 ? this.treePending : this.treeAcked);
                    tab.hide();
                } else {
                    tab.hide();
                }
            }
        }

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