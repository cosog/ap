// 全局变量：控制主界面是否显示"人工消警"标签页
// false: 永久隐藏不显示（即使有数据）； true: 保留当前逻辑（有数据才显示）
var showSuppressedTab = false; 

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
                    'alertId', 'status', 'suppressStatus',
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

        // 待消警树
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
            }, {
                text: '查看人工消警记录',
                itemId: 'viewSuppressedBtn',
                handler: function () {
                    self.openSuppressedListWindow();
                }
            }]
        });

        // 人工消警树
        this.treeSuppressed = this.buildTree(this.storeSuppressed, {
            title: '人工消警',
            checkable: false,
            withSuppressAction: false,
            withAckAction: false,
            toolbarItems: [{
                xtype: 'tbtext', itemId: 'suppressedCountText', text: '已消警：0'
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
            text: '状态', dataIndex: 'status', flex: 2, minWidth: 120, align: 'center',
            renderer: function (v, meta, rec) {
                if (!rec.isLeaf()) return '';
                
                var suppStatus = rec.get('suppressStatus');
                // 对于“待确认”状态，如果 suppStatus=1，也显示待确认
                var ackText = (suppStatus === 1) ? '待确认' : ((v === 'ACK' || v === 'SUPPRESSED') ? '已确认' : '待确认');
                
                var suppText = '待消警';
                if (suppStatus == 2) {
                    suppText = '人工消警';
                } else if (suppStatus == 1) {
                    suppText = '自动消警';
                }

                var ackColor = (ackText === '待确认') ? '#dc2828' : '#888';
                var suppColor = '#1677ff';
                if (suppText === '人工消警') suppColor = '#0e7490';
                if (suppText === '自动消警') suppColor = '#52c41a';

                return '<span style="color:' + ackColor + ';">' + ackText + '</span> ' +
                       '<span style="color:' + suppColor + ';">' + suppText + '</span>';
            }
        }, {
            text: '消警到期时间', dataIndex: 'suppressEndTime', flex: 2, minWidth: 140,hidden:true,
            renderer: function (v, meta, rec) {
                if (!rec.isLeaf()) return '';
                return v || '';
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
                    var suppStatus = rec.get('suppressStatus');
                    if (suppStatus == 2 || suppStatus == 1) return 'alarm-row-suppressed';
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

    syncAlarmNode: function (alarm) {
        if (!alarm || !alarm.key) return;
        
        // ★ 如果自动消警（suppressStatus == 1），也放入 SUPPRESSED 树中展示
        // 但根据你的业务需求，待确认的自动消警应该保留在“待确认”列表。
        // 所以，如果 ackStatus 未确认，我们仍将其放在 NEW 树中。
        var targetStatus = 'NEW';
        if (alarm.ackStatus !== 1) {
            targetStatus = 'NEW';
        } else if (alarm.suppressStatus == 2 || alarm.suppressStatus == 1) {
            targetStatus = 'SUPPRESSED';
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
        } else if (alarm.suppressStatus == 2 || alarm.suppressStatus == 1) {
            targetStatus = 'SUPPRESSED';
        } else {
            targetStatus = 'ACK';
        }
        node.set('status', targetStatus);
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
        
        var labelWidth = getLabelWidth('消警原因', loginUserLanguage);
        if (labelWidth < getLabelWidth('持续时长(h)*', loginUserLanguage)) {
            labelWidth = getLabelWidth('持续时长(h)*', loginUserLanguage);
        }
        
        var dlg = Ext.create('Ext.window.Window', {
            title: '人工消警（' + keys.length + ' 条）',
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
                    fieldLabel: '消警原因<font color=red>*</font>',
                    labelWidth: labelWidth,
                    name: 'reason',
                    allowBlank: false,
                    height: 80,
                    maxLength: 500,
                    anchor: '95%',
                    emptyText: '必填，最多 500 字'
                }, {
                    xtype: 'numberfield',
                    fieldLabel: '持续时长(h)<font color=red>*</font>',
                    labelWidth: labelWidth,
                    name: 'durationHours',
                    allowBlank: false,
                    minValue: 0.1,
                    maxValue: 8760,
                    decimalPrecision: 1,
                    value: 24,
                    anchor: '95%',
                    emptyText: '浮点数，如 1.5'
                }]
            }],
            buttons: [{
                text: '取消',
                iconCls: 'cancel',
                handler: function () { dlg.close(); }
            }, {
                text: '确定',
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
                        Ext.Msg.alert('提示', '没有可消警的报警（未同步服务端）');
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
                }
            }]
        });
        dlg.show();
    },
    
    openSuppressedListWindow: function () {
        var self = this;
        var store = this.storeSuppressed; 
        
        if (this.countLeaf(store) === 0) {
            Ext.Msg.alert('提示', '当前没有已消警记录');
            return;
        }

        var columns = [
            { xtype: 'treecolumn', text: '设备 / 报警内容', dataIndex: 'text', flex: 3, minWidth: 200 },
            { text: '级别', dataIndex: 'alarmLevel', flex: 1, minWidth: 60,
                renderer: function (v, meta, rec) {
                    if (!rec.isLeaf()) return '';
                    var s = AP.alert.AlarmManager.levelStyle[v];
                    return s ? '<span style="color:' + s.color + ';">' + s.text + '</span>' : '';
                }
            },
            { text: '状态', dataIndex: 'status', flex: 1.5, minWidth: 110, align: 'center',
                renderer: function (v, meta, rec) {
                    if (!rec.isLeaf()) return '';
                    var suppStatus = rec.get('suppressStatus');
                    var ackText = '<span style="color:#888;">已确认</span>';
                    var suppText = '';
                    
                    if (suppStatus == 2) {
                        suppText = '<span style="color:#0e7490;">人工消警</span>';
                    } else if (suppStatus == 1) {
                        suppText = '<span style="color:#52c41a;">自动消警</span>';
                    } else {
                        suppText = '<span style="color:#1677ff;">待消警</span>';
                    }
                    return ackText + ' ' + suppText;
                }
            },
            { text: '消警原因', dataIndex: 'suppressReason', flex: 2, minWidth: 120, renderer: function(v, meta, rec){ return rec.isLeaf() ? v : ''; } },
            { text: '消警人', dataIndex: 'suppressUser', flex: 1, minWidth: 80, renderer: function(v, meta, rec){ return rec.isLeaf() ? v : ''; } },
            { text: '消警时间', dataIndex: 'suppressTime', flex: 2, minWidth: 140, renderer: function(v, meta, rec){ return rec.isLeaf() ? v : ''; } },
            { text: '到期时间', dataIndex: 'suppressEndTime', flex: 2, minWidth: 140, renderer: function(v, meta, rec){ return rec.isLeaf() ? v : ''; } },
            { text: '操作', width: 120, align: 'center',
                renderer: function (v, meta, rec) {
                    if (!rec.isLeaf()) return '';
                    return '<a href="javascript:void(0)" class="cancel-suppress">取消消警</a> | ' +
                           '<a href="javascript:void(0)" class="edit-suppress">修改</a>';
                }
            }
        ];

        var win = Ext.create('Ext.window.Window', {
            title: '人工消警记录 (' + this.countLeaf(store) + ')',
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
                columnLines: false,
                columns: columns,
                viewConfig: {
                    getRowClass: function (rec) {
                        if (!rec.isLeaf()) return 'alarm-row-device';
                        return 'alarm-row-suppressed';
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
        Ext.Msg.confirm('确认', '确定要取消该项的人工消警吗？', function (btn) {
            if (btn === 'yes') {
                var alertId = record.get('alertId');
                if (!alertId) {
                    Ext.Msg.alert('提示', '找不到该条报警的ID，无法取消');
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
                            Ext.Msg.alert('提示', '取消消警成功');
                            
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
                                parentWin.setTitle('人工消警记录 (' + self.countLeaf(self.storeSuppressed) + ')');
                            }
                        } else {
                            Ext.Msg.alert('提示', r.message || '取消失败');
                        }
                    },
                    failure: function () {
                        Ext.Msg.alert('错误', '网络请求失败');
                    }
                });
            }
        });
    },

    editSuppress: function (record, parentWin) {
        var self = this;
        var alertId = record.get('alertId');
        if (!alertId) {
            Ext.Msg.alert('提示', '找不到该条报警的ID，无法修改');
            return;
        }
        
        var labelWidth = getLabelWidth('消警原因', loginUserLanguage);
        if (labelWidth < getLabelWidth('持续时长(h)*', loginUserLanguage)) {
            labelWidth = getLabelWidth('持续时长(h)*', loginUserLanguage);
        }
        
        var dlg = Ext.create('Ext.window.Window', {
            title: '修改人工消警',
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
                    fieldLabel: '消警原因<font color=red>*</font>',
                    labelWidth: labelWidth,
                    name: 'reason',
                    allowBlank: false,
                    height: 80,
                    maxLength: 500,
                    anchor: '95%',
                    value: record.get('suppressReason') || ''
                }, {
                    xtype: 'numberfield',
                    fieldLabel: '持续时长(h)<font color=red>*</font>',
                    labelWidth: labelWidth,
                    name: 'durationHours',
                    allowBlank: false,
                    minValue: 0.1,
                    maxValue: 8760,
                    decimalPrecision: 1,
                    value: 24, 
                    anchor: '95%',
                    emptyText: '浮点数，如 1.5'
                }]
            }],
            buttons: [{
                text: '取消',
                iconCls: 'cancel',
                handler: function () { dlg.close(); }
            }, {
                text: '确定',
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
                        
                        Ext.Msg.alert('提示', "<font color=blue>修改成功</font>");
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

    hasUnacknowledged: function () {
        return this.getUnackCount() > 0;
    },

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
        var unack = this.getUnackCount();
        var acking = this.countLeaf(this.storeAcked);
        // ★ 只统计 待确认 + 待消警
        var total = unack + acking; 
        
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