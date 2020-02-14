Ext.define("AP.view.acquisitionUnit.AcquisitionUnitInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.acquisitionUnitInfoView',
    layout: 'border',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        var AcquisitionUnitInfoStore= Ext.create('AP.store.acquisitionUnit.AcquisitionUnitInfoStore');
        var AcquisitionItemsTreeInfoStore= Ext.create('AP.store.acquisitionUnit.AcquisitionItemsTreeInfoStore');
        Ext.apply(me, {
        	items: [{
        		region:'west',
        		id:'acquisitionUnitListPanel_Id',
        		width:'75%',
        		layout: "fit",
        		tbar: [{
                    id: 'acquisitionUnitName_Id',
                    fieldLabel: '类型名称',
                    name: 'unitName',
                    emptyText: '查询采集类型...',
                    labelWidth: 60,
                    width: 165,
                    labelAlign: 'right',
                    xtype: 'textfield'
             }, {
                    xtype: 'button',
                    text: cosog.string.search,
                    pressed: true,
                    iconCls: 'search',
                    handler: function () {
                    	AcquisitionUnitInfoStore.load();
                    }
             },{
                 id: 'selectedAcquisitionUnitCode_Id', // 分配权限时，存放当前选中的角色编码
                 xtype: 'textfield',
                 value: '',
                 hidden: true
             }, '->', {
                    xtype: 'button',
                    id: 'acquisitionUnitAddBtn_Id',
                    text: cosog.string.add,
                    iconCls: 'add',
                    handler: function () {
                    	addAcquisitionUnitInfo();
                    }
             }, "-", {
                    xtype: 'button',
                    id: 'acquisitionUnitUpdateBtn_Id',
                    text: cosog.string.update,
                    disabled: true,
                    iconCls: 'edit',
                    handler: function () {
                    	modifyAcquisitionUnitInfo();
                    }
             }, "-", {
                    xtype: 'button',
                    id: 'acquisitionUnitDeleteBtn_Id',
                    disabled: true,
                    text: cosog.string.del,
                    iconCls: 'delete',
                    handler: function () {
                    	delAcquisitionUnitInfo();
                    }
             }]
        	},{
        		region:'center',
        		id:'acquisitionItemsTreePanel_Id',
        		title:'采控数据配置',
        		layout: "fit",
        		bbar: ['->', {
                    xtype: 'button',
                    id: 'saveAcquisitionUnitContentBtn_Id',
                    text: '保存',
                    iconCls: 'save',
                    pressed: true,
                    handler: function () {
                    	grantAcquisitionItemsPermission();
                    }
        		}, {
                    xtype: 'tbspacer',
                    flex: 1
        		}]
        	}]
        });
        me.callParent(arguments);
    }

});