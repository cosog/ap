Ext.define("AP.view.acquisitionUnit.AcquisitionGroupInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.acquisitionGroupInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'acquisitionGroup_editWin_Id',
    closeAction: 'destroy',
    width: 330,
    shadow: 'sides',
    resizable: false,
    collapsible: true,
    constrain: true,
    maximizable: false,
    plain: true,
    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
    modal: true,
    border: false,
    initComponent: function () {
        var me = this;
        var postacquisitionGroupEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: '序号',
                id: 'formAcquisitionGroupJlbh_Id',
                anchor: '100%',
                name: "acquisitionGroup.id"
            }, {
                id: 'formAcquisitionGroupName_Id',
                name: "acquisitionGroup.groupName",
                fieldLabel: '组名称',
                anchor: '100%',
                value: ''
            }, {
                id: 'formAcquisitionGroupCode_Id',
                name: "acquisitionGroup.groupCode",
                fieldLabel: '组编码',
                anchor: '100%',
                value: ''
                
            }, {
            	id: 'acquisitionGroupRemark_Id',
            	name: "acquisitionGroup.remark",
                fieldLabel: '组描述',
                anchor: '100%',
                value: '',
                xtype: 'textareafield',
                
            }],
            buttons: [{
            	xtype: 'button',
            	id: 'addFormAcquisitionGroup_Id',
            	text: cosog.string.save,
                iconCls: 'save',
                handler: function () {
                	SaveAcquisitionGroupSubmitBtnForm();
                }
         }, {
                xtype: 'button',
                id: 'updateFormaAquisitionGroup_Id',
                text: cosog.string.update,
                hidden: true,
                iconCls: 'edit',
                handler: function () {
                	UpdateAcquisitionGroupDataInfoSubmitBtnForm();
                }
         }, {
        	 	xtype: 'button',   
        	 	text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("acquisitionGroup_editWin_Id").close();
                }
         }]
        });
        Ext.apply(me, {
            items: postacquisitionGroupEditForm
        });
        me.callParent(arguments);
    }

});