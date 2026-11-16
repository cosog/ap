// 全局变量：控制主界面是否显示"人工消警"标签页
// false: 永久隐藏不显示（即使有数据）； true: 保留当前逻辑（有数据才显示）
var showSuppressedTab = false; 

Ext.define('AP.view.alert.AlarmWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.alarmWindow',
    id: 'AlarmWindow_Id',

    title: loginUserLanguageResource.alarmTitle,
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
                    'alertId', 'status', 'triggerAlarm', 
                    'pendingDelay',
                    'suppressStatus',
                    'suppressReason', 'suppressUser', 'suppressTime', 'suppressIp', 'suppressEndTime'
                ]
            });
        };

        this.storePending = makeStore();      // status = 'NEW'
        this.storeAcked = makeStore();        // status = 'ACK'
        this.storeSuppressed = makeStore();   // status = 'SUPPRESSED'

        this.store = this.storePending;

        // 待确认树
        this.treePending = this.buildTree(this.storePending, {
            title: loginUserLanguageResource.unconfirmed,
            checkable: true,
            withAckAction: true,
            toolbarItems: [{
                xtype: 'tbtext',
                itemId: 'muteTip',
                hidden: true,
                html: '<span style="color:#dc2828;font-weight:bold;cursor:pointer;">⚠️ '+loginUserLanguageResource.muteTip+'</span>',
                listeners: {
                    click: function () {
                        var unlockEvt = new MouseEvent('click', { bubbles: true, cancelable: true, view: window });
                        document.dispatchEvent(unlockEvt);
                        this.hide();
                    }
                }
            }, {
                // 静音复选框
                xtype: 'checkboxfield',
                itemId: 'muteCheckbox',
                boxLabel: loginUserLanguageResource.mute,
                checked: AP.alert.AlarmManager.isMuted,
                listeners: {
                    change: function (cb, newValue) {
                        AP.alert.AlarmManager.isMuted = newValue;
                        if (newValue) {
                            AP.alert.AlarmSoundManager.stop();
                        } else {
                            AP.alert.AlarmManager._refreshSoundAfterAck();
                        }
                    }
                }
            }, {
                xtype: 'tbtext', itemId: 'pendingSelText', text: loginUserLanguageResource.selected+'：0'
            }, '->', {
                text: loginUserLanguageResource.ackSelected,
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
                text: loginUserLanguageResource.ackAll,
                itemId: 'ackAllBtn',
                handler: function () {
                    var keys = self.getAllUnackKeys();
                    if (keys.length === 0) {
                        Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.noPendingAlarm);
                        return;
                    }
                    self.doAck(keys);
                }
            }]
        });

        // 待消警树
        this.treeAcked = this.buildTree(this.storeAcked, {
            title: loginUserLanguageResource.pendingSuppress,
            checkable: true,
            withSuppressAction: true,
            toolbarItems: [{
                xtype: 'tbtext', itemId: 'ackedSelText', text: loginUserLanguageResource.selected+'：0'
            }, {
                xtype: 'tbtext', itemId: 'ackedCountText', text: loginUserLanguageResource.confirmed+'：0'
            }, '->', {
                text: loginUserLanguageResource.suppressSelected,
                itemId: 'suppressBtn',
                disabled: true,
                handler: function () {
                    var keys = self.getCheckedAckedKeys();
                    if (keys.length === 0) {
                        Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.selectAlarmToSuppress);
                        return;
                    }
                    self.openSuppressDialog(keys);
                }
            }, {
                text: loginUserLanguageResource.suppressAll,
                itemId: 'suppressAllBtn',
                handler: function () {
                    var keys = self.getAllAckedKeys();
                    if (keys.length === 0) {
                        Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.noPendingSuppressAlarm);
                        return;
                    }
                    self.openSuppressDialog(keys);
                }
            }, {
                text: loginUserLanguageResource.viewSuppressedRecords,
                itemId: 'viewSuppressedBtn',
                handler: function () {
                    self.openSuppressedListWindow();
                }
            }]
        });

        // 人工消警树
        this.treeSuppressed = this.buildTree(this.storeSuppressed, {
            title: loginUserLanguageResource.manualSuppress,
            checkable: false,
            withSuppressAction: false,
            withAckAction: false,
            toolbarItems: [{
                xtype: 'tbtext', itemId: 'suppressedCountText', text: loginUserLanguageResource.suppressed+'：0'
            }]
        });

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

        // ★ 如果全局变量为 false，则永久隐藏该 tab
        if (!showSuppressedTab) {
            this.treeSuppressed.tab.hide();
        }

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

    buildTree: function (store, opts) {
        var self = this;

        var columns = [{
            xtype: 'treecolumn',
            text: loginUserLanguageResource.deviceAlarmContent,
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
            text: loginUserLanguageResource.level, dataIndex: 'alarmLevel', flex: 1, minWidth: 60,
            renderer: function (v, meta, rec) {
                if (!rec.isLeaf()) return '';
                var s = AP.alert.AlarmManager.levelStyle[v];
                return s ? '<span style="color:' + s.color + ';">' + s.text + '</span>' : '';
            }
        }, {
            text: loginUserLanguageResource.alarmValue, dataIndex: 'value', flex: 1, minWidth: 80,
            renderer: function (v, meta, rec) { return rec.isLeaf() ? v : ''; }
        }, {
            text: loginUserLanguageResource.alarmInformation, dataIndex: 'alarminfo', flex: 1, minWidth: 100,
            renderer: function (v, meta, rec) { return rec.isLeaf() ? (v || '') : ''; }
        }, {
            text: loginUserLanguageResource.occurrenceTime, dataIndex: 'acqTime', flex: 2, minWidth: 140,
            renderer: function (v, meta, rec) { return rec.isLeaf() ? v : ''; }
        }, {
            // ★ 状态列渲染器：区分"持续检测中"和"持续中"
            text: loginUserLanguageResource.alarmProcessStatus, dataIndex: 'status', flex: 2, minWidth: 160, align: 'center',
            renderer: function (v, meta, rec) {
                if (!rec.isLeaf()) return '';
                
                var suppStatus = rec.get('suppressStatus');
                var triggerAlarm = rec.get('triggerAlarm');
                var pendingDelay = rec.get('pendingDelay');
                var ackText = (suppStatus === 1) ? loginUserLanguageResource.unconfirmed : ((v === 'ACK' || v === 'SUPPRESSED') ? loginUserLanguageResource.confirmed : loginUserLanguageResource.unconfirmed);
                
                var suppText = loginUserLanguageResource.pendingSuppress;
                if (suppStatus == 2) {
                    suppText = loginUserLanguageResource.manualSuppress;
                } else if (suppStatus == 1) {
                    suppText = loginUserLanguageResource.autoSuppress;
                }

                var ackColor = (ackText === loginUserLanguageResource.unconfirmed) ? '#dc2828' : '#888';
                var suppColor = '#1677ff';
                if (suppText === loginUserLanguageResource.manualSuppress) suppColor = '#0e7490';
                if (suppText === loginUserLanguageResource.autoSuppress) suppColor = '#52c41a';

                // ★ 状态标记：区分"持续检测中"（pendingDelay）和"持续中"（triggerAlarm=false）
                var persistTag = '';
                if (pendingDelay === true) {
                    persistTag = '<span style="color:#8c8c8c;font-size:11px;font-style:italic;"> ('+loginUserLanguageResource.persistentDetection+')</span>';
                } else if (triggerAlarm === false && rec.get('alarmLevel') > 0) {
                    persistTag = '<span style="color:#f09614;font-size:11px;"> ('+loginUserLanguageResource.triggerInterval+')</span>';
                }

                return '<span style="color:' + ackColor + ';">' + ackText + '</span> ' +
                       '<span style="color:' + suppColor + ';">' + suppText + '</span>' + persistTag;
            }
        }, {
            text: loginUserLanguageResource.suppressEndTime, dataIndex: 'suppressEndTime', flex: 2, minWidth: 140, hidden: true,
            renderer: function (v, meta, rec) {
                if (!rec.isLeaf()) return '';
                return v || '';
            }
        }];

        if (opts.withAckAction) {
            columns.push({
                text: loginUserLanguageResource.operation, flex: 1, minWidth: 70, align: 'center',
                renderer: function (v, meta, rec) {
                    if (!rec.isLeaf()) return '';
                    if (rec.get('status') !== 'NEW') return '';
                    return '<a href="javascript:void(0)" class="ack-one">确认</a>';
                }
            });
        }

        if (opts.withSuppressAction) {
            columns.push({
                text: loginUserLanguageResource.operation, flex: 1, minWidth: 90, align: 'center',
                renderer: function (v, meta, rec) {
                    if (!rec.isLeaf()) return '';
                    if (rec.get('status') !== 'ACK') return '';
                    return '<a href="javascript:void(0)" class="suppress-one">'+loginUserLanguageResource.manualSuppress+'</a>';
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
                    var suppStatus = rec.get('suppressStatus');
                    if (suppStatus == 2 || suppStatus == 1) return 'alarm-row-suppressed';
                    // ★ 新增：持续检测中，灰色斜体弱化
                    if (rec.get('pendingDelay') === true) return 'alarm-row-pending-delay';
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
            text: alarm.deviceName || (loginUserLanguageResource.device + alarm.deviceId),
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

    syncAlarmNode: function (alarm) {
        if (!alarm || !alarm.key) return;
        
        // ★ 如果自动消警（suppressStatus == 1），也放入 SUPPRESSED 树中展示
        // 但根据你的业务需求，待确认的自动消警应该保留在“待确认”列表。
        // 所以，如果 ackStatus 未确认，我们仍将其放在 NEW 树中。
        var targetStatus = 'NEW';
        if (alarm.ackStatus !== 1) {
            targetStatus = 'NEW';
        } else if (alarm.suppressStatus == 2) { 
            // ★ 核心修改：只有人工消警(2)才进人工消警窗口
            targetStatus = 'SUPPRESSED';
        } else if (alarm.suppressStatus == 1) {
            // ★ 核心修改：已确认且是自动消警(1)，直接从界面上彻底抹掉
            this.removeAlarmNode(alarm.key); 
            return;
        } else {
            targetStatus = 'ACK';
        }

        var existing = this.findLeafByKey(alarm.key);

        if (!existing) {
            this.insertLeaf(alarm, targetStatus);
            return;
        }
        if (existing.get('status') !== targetStatus) {
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
            triggerAlarm: alarm.triggerAlarm !== false,
            pendingDelay: alarm.pendingDelay === true,   // ★ 新增
            suppressStatus: alarm.suppressStatus || 0,
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
        
        var targetStatus = 'NEW';
        if (alarm.ackStatus !== 1) {
            targetStatus = 'NEW';
        } else if (alarm.suppressStatus == 2) { 
            // ★ 核心修改：只有人工消警(2)才进人工消警窗口
            targetStatus = 'SUPPRESSED';
        } else if (alarm.suppressStatus == 1) {
            // ★ 核心修改：已确认且是自动消警(1)，直接从界面上彻底抹掉
            this.removeAlarmNode(alarm.key); 
            return;
        } else {
            targetStatus = 'ACK';
        }
        
        node.set('status', targetStatus);
        node.set('triggerAlarm', alarm.triggerAlarm !== false);
        node.set('pendingDelay', alarm.pendingDelay === true);   // ★ 新增
        node.set('suppressStatus', alarm.suppressStatus || 0);
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
            // ★ 只有待确认和待消警都为0时才收缩
            if (ackCount === 0) {
                this.collapse();
                this.stopBlink();
                AP.alert.AlarmSoundManager.stop();
                var tip = this.down('#muteTip');
                if (tip) tip.hide();
            }
        } else {
            AP.alert.AlarmManager._refreshSoundAfterAck();
        }
    },

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
                if (a) {
                    // ★ 如果是自动消警状态（suppressStatus == 1），点击确认后直接从界面彻底移除
                    if (a.suppressStatus == 1) {
                        delete AP.alert.AlarmManager.alarms[key];
                        delete AP.alert.AlarmManager.ackKeys[key];
                        self.removeAlarmNode(key);
                    } else {
                        self.syncAlarmNode(a);
                    }
                }
            });
            self.updateToolbar();
            AP.alert.AlarmManager._refreshSoundAfterAck();
        });
    },

    openSuppressDialog: function (keys) {
        if (!keys || keys.length === 0) return;
        var self = this;
        
        var labelWidth = getLabelWidth(loginUserLanguageResource.suppressReason, loginUserLanguage);
        if (labelWidth < getLabelWidth(loginUserLanguageResource.durationHours+'(h)*', loginUserLanguage)) {
            labelWidth = getLabelWidth(loginUserLanguageResource.durationHours+'(h)*', loginUserLanguage);
        }
        
        var dlg = Ext.create('Ext.window.Window', {
            title: loginUserLanguageResource.manualSuppress+'（' + keys.length + '）',
            modal: true,
            width: 450,
            layout: 'fit',
            bodyStyle: 'padding:5px;background-color:#D9E5F3;',
            items: [{
                xtype: 'form',
                baseCls: 'x-plain',
                bodyPadding: 12,
                items: [{
                    xtype: 'textarea',
                    fieldLabel: loginUserLanguageResource.suppressReason+'<font color=red>*</font>',
                    labelWidth: labelWidth,
                    name: 'reason',
                    allowBlank: false,
                    height: 80,
                    maxLength: 500,
                    anchor: '95%',
                    emptyText: loginUserLanguageResource.suppressReasonEmptyText
                }, {
                    xtype: 'numberfield',
                    fieldLabel: loginUserLanguageResource.durationHours+'(h)<font color=red>*</font>',
                    labelWidth: labelWidth,
                    name: 'durationHours',
                    allowBlank: false,
                    minValue: 0,
                    maxValue: 8760,
                    decimalPrecision: 1,
                    value: 24,
                    anchor: '95%',
                    emptyText: loginUserLanguageResource.durationHoursEmptyText
                }]
            }],
            buttons: [{
                text: loginUserLanguageResource.cancel,
                iconCls: 'cancel',
                handler: function () { dlg.close(); }
            }, {
                text: loginUserLanguageResource.confirm,
                iconCls: 'save',
                handler: function () {
                    var form = dlg.down('form').getForm();
                    if (!form.isValid()) return;
                    
                    var v = form.getValues();
                    var alertIds = [];
                    Ext.Array.each(keys, function (key) {
                        var a = AP.alert.AlarmManager.alarms[key];
                        var aid = a ? a.alertId : null;
                        if (!aid) {
                            var leafNode = self.findLeafByKey(key);
                            if (leafNode) aid = leafNode.get('alertId');
                        }
                        if (aid) alertIds.push(aid);
                    });
                    
                    if (alertIds.length === 0) {
                        Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.noSuppressibleAlarm);
                        return;
                    }
                    AP.alert.AlarmManager.suppressAlarm(alertIds, v.reason, parseFloat(v.durationHours), function (ok, data) {
                        if (!ok) return;
                        Ext.Array.each(keys, function (key) {
                            var a = AP.alert.AlarmManager.alarms[key];
                            if (a) {
                                if (alertIds.indexOf(a.alertId) >= 0 || !a.alertId) {
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
                    
                    AP.alert.AlarmManager.suppressAlarm(alertIds, v.reason, parseFloat(v.durationHours), function (ok, data) {
                        if (!ok) return;
                        var affectedAlarms = []; // ★ 收集受影响的报警
                        Ext.Array.each(keys, function (key) {
                            var a = AP.alert.AlarmManager.alarms[key];
                            if (a) {
                                if (alertIds.indexOf(a.alertId) >= 0 || !a.alertId) {
                                    a.suppressStatus = 2;
                                    a.suppressReason = v.reason;
                                    a.suppressUser = data.suppressUser;
                                    a.suppressTime = data.suppressTime;
                                    a.suppressIp = data.suppressIp;
                                    a.suppressEndTime = data.suppressEndTime;
                                    self.syncAlarmNode(a);

                                    affectedAlarms.push({
                                        deviceId: a.deviceId,
                                        column: a.column,
                                        bitIndex: a.bitIndex
                                    });
                                }
                            }
                        });
                        dlg.close();
                        self.updateToolbar();
                        AP.alert.AlarmManager._refreshSoundAfterAck();
                        
                        // ★ 同步更新概览表和场景模型
                        AP.alert.AlarmManager.applySuppressToUI(affectedAlarms);
                    });
                }
            }]
        });
        dlg.show();
    },
    
    openSuppressedListWindow: function () {
        var self = this;
        var store = this.storeSuppressed; 
        
        if (this.countLeaf(store) === 0) {
            Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.noSuppressedRecords);
            return;
        }

        var columns = [
            { xtype: 'treecolumn', text: loginUserLanguageResource.deviceAlarmContent, dataIndex: 'text', flex: 3, minWidth: 200 },
            { text: loginUserLanguageResource.level, dataIndex: 'alarmLevel', flex: 1, minWidth: 60,
                renderer: function (v, meta, rec) {
                    if (!rec.isLeaf()) return '';
                    var s = AP.alert.AlarmManager.levelStyle[v];
                    return s ? '<span style="color:' + s.color + ';">' + s.text + '</span>' : '';
                }
            },
            { text: loginUserLanguageResource.alarmProcessStatus, dataIndex: 'status', flex: 1.5, minWidth: 110, align: 'center',
                renderer: function (v, meta, rec) {
                    if (!rec.isLeaf()) return '';
                    var suppStatus = rec.get('suppressStatus');
                    var ackText = '<span style="color:#888;">'+loginUserLanguageResource.confirmed+'</span>';
                    var suppText = '';
                    
                    if (suppStatus == 2) {
                        suppText = '<span style="color:#0e7490;">'+loginUserLanguageResource.manualSuppress+'</span>';
                    } else if (suppStatus == 1) {
                        suppText = '<span style="color:#52c41a;">'+loginUserLanguageResource.autoSuppress+'</span>';
                    } else {
                        suppText = '<span style="color:#1677ff;">'+loginUserLanguageResource.pendingSuppress+'</span>';
                    }
                    return ackText + ' ' + suppText;
                }
            },
            { text: loginUserLanguageResource.suppressReason, dataIndex: 'suppressReason', flex: 2, minWidth: 120, renderer: function(v, meta, rec){ return rec.isLeaf() ? v : ''; } },
            { text: loginUserLanguageResource.suppressUser, dataIndex: 'suppressUser', flex: 1, minWidth: 80, renderer: function(v, meta, rec){ return rec.isLeaf() ? v : ''; } },
            { text: loginUserLanguageResource.suppressTime, dataIndex: 'suppressTime', flex: 2, minWidth: 140, renderer: function(v, meta, rec){ return rec.isLeaf() ? v : ''; } },
            { text: loginUserLanguageResource.suppressEndTime, dataIndex: 'suppressEndTime', flex: 2, minWidth: 140, renderer: function(v, meta, rec){ return rec.isLeaf() ? v : ''; } },
            { text: loginUserLanguageResource.operation, width: 120, align: 'center',
                renderer: function (v, meta, rec) {
                    if (!rec.isLeaf()) return '';
                    // 链接之间用 margin-left 隔开，不使用竖线
                    return '<a href="javascript:void(0)" class="cancel-suppress">'+loginUserLanguageResource.cancelSuppress+'</a>' +
                           '<a href="javascript:void(0)" class="edit-suppress" style="margin-left:12px;">'+loginUserLanguageResource.update+'</a>';
                }
            }
        ];

        var win = Ext.create('Ext.window.Window', {
            title: loginUserLanguageResource.manualSuppressRecords+' (' + this.countLeaf(store) + ')',
            width: 1000,
            height: 500,
            modal: true,
            layout: 'fit',
            items: [{
                xtype: 'treepanel',
                store: store,
                rootVisible: false,
                useArrows: true,
                border: false,
                columnLines: false, //关闭表格网格竖线，与其他表格保持一致
                columns: columns,
                viewConfig: {
                    getRowClass: function (rec) {
                        // 保持父节点样式，叶子节点用默认白底黑字
                        if (!rec.isLeaf()) return 'alarm-row-device';
                        return ''; 
                    }
                },
                listeners: {
                    cellclick: function (view, td, cellIndex, record, tr, rowIndex, e) {
                        if (!record.isLeaf()) return;
                        
                        var cancelTarget = e.getTarget('a.cancel-suppress');
                        if (cancelTarget) {
                            self.cancelSuppress(record, win);
                            return;
                        }
                        
                        var editTarget = e.getTarget('a.edit-suppress');
                        if (editTarget) {
                            self.editSuppress(record, win);
                            return;
                        }
                    }
                }
            }]
        });
        
        win.show();
    },

    cancelSuppress: function (record, parentWin) {
        var self = this;
        Ext.Msg.confirm(loginUserLanguageResource.confirm,loginUserLanguageResource.confirmCancelSuppress, function (btn) {
            if (btn === 'yes') {
                var alertId = record.get('alertId');
                if (!alertId) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.alarmIdNotFoundCancel);
                    return;
                }
                
                Ext.Ajax.request({
                    url: context + '/alarmQueryController/cancelSuppress',
                    method: 'POST',
                    jsonData: { alertIds: [alertId] },
                    success: function (resp) {
                        var r;
                        try { r = Ext.JSON.decode(resp.responseText); } catch (e) { r = { success: false }; }
                        if (r.success) {
                            Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.cancelSuppressSuccess);
                            
                            var key = record.get('alarmKey');
                            var alarm = AP.alert.AlarmManager.alarms[key];
                            if (alarm) {
                                alarm.suppressStatus = 0;
                                alarm.suppressReason = '';
                                alarm.suppressUser = '';
                                alarm.suppressIp = '';
                                alarm.suppressTime = '';
                                alarm.suppressEndTime = '';
                                self.syncAlarmNode(alarm);
                            }
                            
                            self.updateToolbar();
                            if (parentWin) {
                                parentWin.setTitle(loginUserLanguageResource.manualSuppressRecords+' (' + self.countLeaf(self.storeSuppressed) + ')');
                            }
                        } else {
                            Ext.Msg.alert(loginUserLanguageResource.tip, r.message || loginUserLanguageResource.cancelSuppressFailed);
                        }
                    },
                    failure: function () {
                        Ext.Msg.alert(loginUserLanguageResource.error, loginUserLanguageResource.networkRequestFailed);
                    }
                });
            }
        });
    },

    editSuppress: function (record, parentWin) {
        var self = this;
        var alertId = record.get('alertId');
        if (!alertId) {
            Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.alarmIdNotFoundEdit);
            return;
        }
        
        var labelWidth = getLabelWidth(loginUserLanguageResource.suppressReason, loginUserLanguage);
        if (labelWidth < getLabelWidth(loginUserLanguageResource.durationHours+'(h)*', loginUserLanguage)) {
            labelWidth = getLabelWidth(loginUserLanguageResource.durationHours+'(h)*', loginUserLanguage);
        }
        
        var dlg = Ext.create('Ext.window.Window', {
            title: loginUserLanguageResource.editManualSuppress,
            modal: true,
            width: 450,
            layout: 'fit',
            bodyStyle: 'padding:5px;background-color:#D9E5F3;',
            items: [{
                xtype: 'form',
                baseCls: 'x-plain',
                bodyPadding: 12,
                items: [{
                    xtype: 'textarea',
                    fieldLabel: loginUserLanguageResource.suppressReason+'<font color=red>*</font>',
                    labelWidth: labelWidth,
                    name: 'reason',
                    allowBlank: false,
                    height: 80,
                    maxLength: 500,
                    anchor: '95%',
                    value: record.get('suppressReason') || ''
                }, {
                    xtype: 'numberfield',
                    fieldLabel: loginUserLanguageResource.durationHours+'(h)<font color=red>*</font>',
                    labelWidth: labelWidth,
                    name: 'durationHours',
                    allowBlank: false,
                    minValue: 0,
                    maxValue: 8760,
                    decimalPrecision: 1,
                    value: 24, 
                    anchor: '95%',
                    emptyText: loginUserLanguageResource.durationHoursEmptyText
                }]
            }],
            buttons: [{
                text: loginUserLanguageResource.cancel,
                iconCls: 'cancel',
                handler: function () { dlg.close(); }
            }, {
                text: loginUserLanguageResource.confirm,
                iconCls: 'save',
                handler: function () {
                    var form = dlg.down('form').getForm();
                    if (!form.isValid()) return;
                    var v = form.getValues();
                    
                    AP.alert.AlarmManager.suppressAlarm([alertId], v.reason, parseFloat(v.durationHours), function (ok, data) {
                        if (!ok) return;
                        
                        record.set('suppressReason', v.reason);
                        record.set('suppressEndTime', data.suppressEndTime);
                        record.set('suppressTime', data.suppressTime);
                        record.set('suppressUser', data.suppressUser);
                        record.commit();
                        
                        var key = record.get('alarmKey');
                        var alarm = AP.alert.AlarmManager.alarms[key];
                        if (alarm) {
                            alarm.suppressReason = v.reason;
                            alarm.suppressEndTime = data.suppressEndTime;
                            alarm.suppressTime = data.suppressTime;
                            alarm.suppressUser = data.suppressUser;
                        }
                        
                        Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>"+loginUserLanguageResource.modifiedSuccessfully+"</font>");
                        dlg.close();
                        self.updateToolbar();
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

//    hasUnacknowledged: function () {
//        return this.getUnackCount() > 0;
//    },
    
    hasUnacknowledged: function () {
        // ★ 只有非 pendingDelay 的未确认报警才触发标题闪烁
        //   pendingDelay=true 的报警（系统重启后重新进入延时检测）虽然未确认，
        //   但当前未真正报警，与概览表/场景模型"不高亮"保持一致，不触发闪烁。
        var has = false;
        this.storePending.getRootNode().eachChild(function (d) {
            d.eachChild(function (leaf) {
                if (leaf.isLeaf()
                        && leaf.get('status') === 'NEW'
                        && leaf.get('pendingDelay') !== true) {
                    has = true;
                }
            });
        });
        return has;
    },

    updateToolbar: function () {
        var unack = this.getUnackCount();
        var acking = this.countLeaf(this.storeAcked);
        var suppressed = this.countLeaf(this.storeSuppressed);

        var pendingSel = this.down('#pendingSelText');
        if (pendingSel) pendingSel.setText(loginUserLanguageResource.selected+'：' + this.getCheckedPendingKeys().length);
        var ackBtn = this.down('#ackBtn');
        if (ackBtn) ackBtn.setDisabled(this.getCheckedPendingKeys().length === 0);
        var ackAllBtn = this.down('#ackAllBtn');
        if (ackAllBtn) ackAllBtn.setDisabled(unack === 0);

        var ackedSel = this.down('#ackedSelText');
        if (ackedSel) ackedSel.setText(loginUserLanguageResource.selected+'：' + this.getCheckedAckedKeys().length);
        var ackedCount = this.down('#ackedCountText');
        if (ackedCount) ackedCount.setText(loginUserLanguageResource.confirmed+'：' + acking);
        var suppressBtn = this.down('#suppressBtn');
        if (suppressBtn) suppressBtn.setDisabled(this.getCheckedAckedKeys().length === 0);
        var suppressAllBtn = this.down('#suppressAllBtn');
        if (suppressAllBtn) suppressAllBtn.setDisabled(acking === 0);

        var suppCount = this.down('#suppressedCountText');
        if (suppCount) suppCount.setText(loginUserLanguageResource.suppressed+'：' + suppressed);

        if (this.treePending) this.treePending.setTitle(loginUserLanguageResource.unconfirmed+' (' + unack + ')');
        if (this.treeAcked) this.treeAcked.setTitle(loginUserLanguageResource.pendingSuppress+' (' + acking + ')');
        
        if (this.treeSuppressed) {
            this.treeSuppressed.setTitle(loginUserLanguageResource.manualSuppress+' (' + suppressed + ')');
            var tab = this.treeSuppressed.tab;
            if (tab) {
                // ★ 如果全局变量控制为不显示，则永久隐藏
                if (!showSuppressedTab) {
                    if (this.tabPanel.getActiveTab() === this.treeSuppressed) {
                        this.tabPanel.setActiveItem(unack > 0 ? this.treePending : this.treeAcked);
                    }
                    tab.hide();
                } else {
                    // 保留原有逻辑
                    if (suppressed > 0) tab.show();
                    else if (this.tabPanel.getActiveTab() === this.treeSuppressed) {
                        this.tabPanel.setActiveItem(unack > 0 ? this.treePending : this.treeAcked);
                        tab.hide();
                    } else {
                        tab.hide();
                    }
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
        var unack = this.getUnackCount();          // 包含 pendingDelay=true，用于数量显示
        var acking = this.countLeaf(this.storeAcked);
        var total = unack + acking;                 // 只统计 待确认 + 待消警

        // ★ 真实未确认数（排除 pendingDelay=true），用于颜色和闪烁判断
        var realUnack = 0;
        this.storePending.getRootNode().eachChild(function (d) {
            d.eachChild(function (leaf) {
                if (leaf.isLeaf()
                        && leaf.get('status') === 'NEW'
                        && leaf.get('pendingDelay') !== true) {
                    realUnack++;
                }
            });
        });

        var text = loginUserLanguageResource.alarmTitle;
        if (total > 0) {
            text = loginUserLanguageResource.alarmTitle+' (' + unack + '/' + total + ')';
        }
        var html;
        if (realUnack > 0 && this.blinkOn) {
            html = '<span style="color:#dc2828;">● ' + text + '</span>';
        } else if (realUnack > 0) {
            html = '<span style="color:#dc2828;">○ ' + text + '</span>';
        } else {
            // ★ 全部是 pendingDelay=true 或全部已确认：普通色，不闪烁
            //   与概览表/场景模型"不高亮"保持一致
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