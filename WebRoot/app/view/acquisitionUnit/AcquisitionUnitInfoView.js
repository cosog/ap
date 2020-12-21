Ext.define("AP.view.acquisitionUnit.AcquisitionUnitInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.acquisitionUnitInfoView',
    layout: 'border',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	items: [{
        		region:'west',
        		title:'采集单元配置',
        		id:'acquisitionUnitListPanel_Id',
        		width:'40%',
        		layout: "fit",
        		tbar: [{
                    id: 'acquisitionUnitName_Id',
                    fieldLabel: '单元名称',
                    name: 'unitName',
                    emptyText: '查询采集单元...',
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
        		title:'采集组配置',
        		id:'acquisitionGroupListPanel_Id',
        		width:'40%',
        		layout: "fit",
        		tbar: [{
                    id: 'acquisitionGroupName_Id',
                    fieldLabel: '组名称',
                    name: 'groupName',
                    emptyText: '查询采集组...',
                    labelWidth: 45,
                    width: 150,
                    labelAlign: 'right',
                    xtype: 'textfield'
             }, {
                    xtype: 'button',
                    text: cosog.string.search,
                    pressed: true,
                    iconCls: 'search',
                    handler: function () {
//                    	AcquisitionUnitInfoStore.load();
                    }
             },{
                 id: 'selectedAcquisitionGroupCode_Id', // 分配权限时，存放当前选中的角色编码
                 xtype: 'textfield',
                 value: '',
                 hidden: true
             }, '->', {
                    xtype: 'button',
                    id: 'acquisitionGroupAddBtn_Id',
                    text: cosog.string.add,
                    iconCls: 'add',
                    handler: function () {
                    	addAcquisitionGroupInfo();
                    }
             }, "-", {
                    xtype: 'button',
                    id: 'acquisitionGroupUpdateBtn_Id',
                    text: cosog.string.update,
                    disabled: true,
                    iconCls: 'edit',
                    handler: function () {
//                    	modifyAcquisitionGroupInfo();
                    }
             }, "-", {
                    xtype: 'button',
                    id: 'acquisitionGroupDeleteBtn_Id',
                    disabled: true,
                    text: cosog.string.del,
                    iconCls: 'delete',
                    handler: function () {
//                    	delAcquisitionGroupInfo();
                    }
             }],
             bbar: ['->', {
                 xtype: 'button',
                 id: 'saveAcquisitionGroupToUnitBtn_Id',
                 text: '保存',
                 iconCls: 'save',
                 pressed: true,
                 handler: function () {
                 	grantAcquisitionGroupsPermission();
                 }
     		}, {
                 xtype: 'tbspacer',
                 flex: 1
     		}]
        	},{
        		region:'east',
        		width:'20%',
        		id:'acquisitionItemsTreePanel_Id',
        		title:'采集项配置',
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