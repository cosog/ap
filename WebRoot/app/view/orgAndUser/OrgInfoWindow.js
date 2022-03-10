var org_Level;
Ext.define("AP.view.orgAndUser.OrgInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.orgInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'org_addwin_Id',
    closeAction: 'destroy',
    width: 330,
    //height : 300,
    constrain: true,
    shadow: 'sides',
    resizable: false,
    collapsible: true,
    maximizable: false,
    layout: 'fit',
    plain: true,
    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
    modal: true,
    border: false,
    initComponent: function () {
        var me = this;
        /**下拉机构树*/
        var OrgTreeStore=Ext.create('Ext.data.TreeStore', {
            fields: ['orgId', 'text', 'leaf'],
            autoLoad: false,
            proxy: {
                type: 'ajax',
                url: context + '/orgManagerController/loadOrgComboxTreeData',
                reader: 'json'
            },
            root: {
                expanded: true,
                text: 'orgName'
            },
            listeners: {
            	beforeload: function (store, options) {
                	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                    var new_params = {
                    	orgId:orgId
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var orgTreeComb=Ext.create('AP.view.well.TreePicker',{
        	id:'orgName_Parent_Id1',
        	anchor: '95%',
        	fieldLabel: cosog.string.superOrg+'<font color=red>*</font>',
            emptyText: cosog.string.chooseOrg,
            blankText: cosog.string.chooseOrg,
            displayField: 'text',
            autoScroll:true,
            allowBlank: false,
            forceSelection : true,// 只能选择下拉框里面的内容
            rootVisible: false,
            store:OrgTreeStore,
            listeners: {
                select: function (picker,record,eOpts) {
                	Ext.getCmp("org_addwin_Id").down('form').getChildByElement("orgName_Parent_Id").setValue(record.data.id);
                }
            }
        });

        var postOrgEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            id: 'addOrgForm_Id',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: '单位序号',
                id: 'orgOrg_Id',
                value: '1',
                name: "org.orgId"
            }, {
                xtype: "hidden",
                fieldLabel: '单位父ID',
                value: '0',
                name: 'org.orgParent',
                id: 'orgName_Parent_Id'

            }, orgTreeComb,{
                fieldLabel: cosog.string.orgName+'<font color=red>*</font>',
                id: 'orgName_Id',
                allowBlank: false,
                anchor: '95%',
                name: "org.orgName"
            }, {
                fieldLabel: cosog.string.orgMemo,
                id: 'orgMemo_Id',
                anchor: '95%',
                xtype: 'textareafield',
                value: '',
                name: "org.orgMemo"
            },{
            	xtype: 'numberfield',
            	id: "orgSeq_Id",
            	name: "org.orgSeq",
                fieldLabel: '排序编号',
                allowBlank: true,
                minValue: '',
                anchor: '95%',
                msgTarget: 'side'
            }],
            buttons: [{
                id: 'addFormOrg_Id',
                xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                handler: SaveOrgDataInfoSubmitBtnForm
            }, {
                xtype: 'button',
                id: 'updateFormOrg_Id',
                text: cosog.string.update,
                hidden: true,
                iconCls: 'edit',
                handler: UpdateOrgDataInfoSubmitBtnForm
            }, {
                text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("org_addwin_Id").close();
                }
            }]
        });
        Ext.apply(me, {
            items: postOrgEditForm
        });
        me.callParent(arguments);
    }
});