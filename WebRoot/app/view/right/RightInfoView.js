Ext.define("AP.view.right.RightInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.rightInfoView',
    layout: 'anchor',
    border: false,
    initComponent: function () {
        var me = this;
        var RightOrgInfoView = Ext.create('AP.view.right.RightOrgInfoView');
        var RightUserInfoGridPanel = Ext.create('AP.view.right.RightUserInfoGridPanel');
        var RightRoleInfoGridPanel = Ext.create('AP.view.right.RightRoleInfoGridPanel');
        var RightBottomRoleInfoGridPanel = Ext.create('AP.view.right.RightBottomRoleInfoGridPanel');
        var RightModuleInfoGridPanel = Ext.create('AP.view.right.RightModuleInfoTreeGridView');
        Ext.apply(me, {
            items: [{
                border: false,
                anchor: '100% 40%',
                layout: 'border',
                items: [{
                    region: 'west',
                    title: cosog.string.pleaseCheckOrg,
                    items: [RightOrgInfoView],
                    split: true,
                    collapsible: true,
                    layout: 'fit',
                    width: '30%'
//                    id:'RightOrgInfoViewPanel_Id'
                }, {
                    region: 'center',
                    title: cosog.string.peopleList,
                    //split: true,
                    //collapsible: true,
                    width: '35%',
                    items: [RightUserInfoGridPanel],
                    layout: 'fit'
                    //autoScroll : true,

                }, {
                    region: 'east',
                    split: true,
                    collapsible: true,
                    layout: 'fit',
                    //autoScroll : true,
                    width: '35%',
                    items: [RightRoleInfoGridPanel],
                    title: cosog.string.grantRole
                }],
                bbar: ['->', {
                    xtype: 'button',
                    itemId: 'addRightRoleLableClassBtnId',
                    id: 'addRightRoleLableClassBtn_Id',
                    action: 'addRightRoleAction',
                    text: cosog.string.sureDo,
                    pressed: true,
                    iconCls: 'save',
                    handler: addRoleInfo
                }, {
                    xtype: 'tbspacer',
                    flex: 1
                }]
            }, {
                anchor: '100% 60%',
                border: false,
                layout: 'border',
                items: [{
                    region: 'west',
                    title: cosog.string.pleaseCheckRole,
                    split: true,
                    collapsible: true,
                    layout: 'fit',
                    items: [RightBottomRoleInfoGridPanel],
                    //autoScroll : true,
                    width: '30%'
           }, {
                    region: 'center',
                    split: true,
                    collapsible: true,
                    layout: 'fit',
                    //autoScroll : true,
                    title: cosog.string.grantRight,
                    items: [RightModuleInfoGridPanel],
                    width:'70%'
           }],
                bbar: ['->', {
                    xtype: 'button',
                    itemId: 'addRightModuleLableClassBtnId',
                    id: 'addRightModuleLableClassBtn_Id',
                    action: 'addRightModuleAction',
                    text: cosog.string.sureGive,
                    iconCls: 'save',
                    pressed: true,
                    handler: addModuleInfo
   }, {
                    xtype: 'tbspacer',
                    flex: 1
  }]
        }]
        });
        me.callParent(arguments);
    }

});