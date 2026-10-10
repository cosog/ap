Ext.define('AP.view.sceneModel.DataSourceWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.sceneDataSourceWindow',
    id: 'sceneDataSourceWindow',

    title: '配置数据源',
    width: 900,
    height: 560,
    modal: true,
    layout: 'fit',
    border: false,
    resizable: true,
    closeAction: 'destroy',

    // ============================================================
    // 外部传入
    // ============================================================
    targetUuid: null,
    targetOrgId: null,
    currentBinding: null,
    onConfirm: null,

    // ============================================================
    // 布局
    // ============================================================
    initComponent: function() {
        var me = this;

        me.items = [{
            xtype: 'panel',
            layout: 'border',
            border: false,
            items: [
                {
                    region: 'west',
                    width: 340,
                    split: true,
                    layout: 'fit',
                    title: '设备列表',
                    id: 'SceneDeviceContainer_Id'
                },
                {
                    region: 'center',
                    layout: 'fit',
                    title: '数据项',
                    id: 'SceneDeviceFieldContainer_Id'
                }
            ]
        }];

        me.buttons = [
            { text: '取消', handler: function() { me.close(); } },
            { text: '确定', cls: 'x-btn-primary', handler: function() { me.doConfirm(); } }
        ];

        me.callParent(arguments);

        me.on('afterrender', function() {
            me.loadDeviceList();
        });
    },

    // ============================================================
    // 加载设备列表
    // ============================================================
    loadDeviceList: function() {
        var me = this;
        var gridPanel = Ext.getCmp('SceneDeviceGrid_Id');

        if (!isNotVal(gridPanel)) {
            me.deviceStore = Ext.create('AP.store.sceneModel.SceneDeviceStore');
            me.deviceStore.getProxy().setExtraParams({
                orgId: me.targetOrgId || ''
            });
            me.deviceStore.load();
        } else {
            me.deviceStore = gridPanel.getStore();
            me.deviceStore.getProxy().setExtraParams({
                orgId: me.targetOrgId || ''
            });
            me.deviceStore.load();
        }
    },

    // ============================================================
    // 选中设备 → 加载右侧字段
    // ============================================================
    onDeviceSelected: function(deviceRecord) {
        var me = this;
        var deviceId      = deviceRecord.get('deviceId');
        var calculateType = deviceRecord.get('calculateType');
        var protocolCode  = deviceRecord.get('protocolCode');

        var fieldGrid = Ext.getCmp('SceneDeviceFieldGrid_Id');

        if (!isNotVal(fieldGrid)) {
            me.fieldStore = Ext.create('AP.store.sceneModel.SceneDeviceFieldStore');
            me.fieldStore.getProxy().setExtraParams({
                deviceId:      deviceId,
                calculateType: calculateType,
                protocolCode:  protocolCode
            });
            me.fieldStore.load();
        } else {
            me.fieldStore = fieldGrid.getStore();
            me.fieldStore.getProxy().setExtraParams({
                deviceId:      deviceId,
                calculateType: calculateType,
                protocolCode:  protocolCode
            });
            me.fieldStore.load();
        }
    },

    // ============================================================
    // 确定
    // ★ fieldName 取 itemCode，fieldLabel 取 itemName
    // ============================================================
    doConfirm: function() {
        var me = this;

        var deviceGrid = Ext.getCmp('SceneDeviceGrid_Id');
        var fieldGrid  = Ext.getCmp('SceneDeviceFieldGrid_Id');

        if (!isNotVal(deviceGrid)) { Ext.Msg.alert('提示', '请选择设备'); return; }
        if (!isNotVal(fieldGrid))  { Ext.Msg.alert('提示', '请选择数据项'); return; }

        var deviceRec = deviceGrid.getSelectionModel().getSelection()[0];
        var fieldRec  = fieldGrid.getSelectionModel().getSelection()[0];

        if (!deviceRec) { Ext.Msg.alert('提示', '请选择设备'); return; }
        if (!fieldRec)  { Ext.Msg.alert('提示', '请选择数据项'); return; }

        var binding = {
            deviceId:   deviceRec.get('deviceId'),
            deviceName: deviceRec.get('deviceName'),
            fieldName:  fieldRec.get('itemCode'),      // ★ 字段编码
            fieldLabel: fieldRec.get('itemName'),      // ★ 字段名称
            unit:       fieldRec.get('unit') || '',
            dataSource: fieldRec.get('dataSource') || ''
        };

        if (Ext.isFunction(me.onConfirm)) {
            me.onConfirm(binding, me.targetUuid);
        }
        me.close();
    },

    // ============================================================
    // 销毁时清理
    // ============================================================
    beforeDestroy: function() {
        if (this.deviceStore) { this.deviceStore.destroy(); this.deviceStore = null; }
        if (this.fieldStore)  { this.fieldStore.destroy();  this.fieldStore  = null; }
        this.callParent(arguments);
    }
});