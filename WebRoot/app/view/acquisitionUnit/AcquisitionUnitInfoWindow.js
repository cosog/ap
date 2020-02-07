Ext.define("AP.view.acquisitionUnit.AcquisitionUnitInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.acquisitionUnitInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'acquisitionUnit_editWin_Id',
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
        var postacquisitionUnitEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: '序号',
                id: 'formAcquisitionUnitJlbh_Id',
                anchor: '100%',
                name: "acquisitionUnit.id"
            }, {
                id: 'formAcquisitionUnitName_Id',
                name: "acquisitionUnit.unitName",
                fieldLabel: '类型名称',
                anchor: '100%',
                value: ''
            }, {
                id: 'formAcquisitionUnitCode_Id',
                name: "acquisitionUnit.unitCode",
                fieldLabel: '类型编码',
                anchor: '100%',
                value: ''
                
            }, {
            	id: 'acquisitionUnitRemark_Id',
            	name: "acquisitionUnit.remark",
                fieldLabel: '类型描述',
                anchor: '100%',
                value: '',
                xtype: 'textareafield',
                
            }],
            buttons: [{
            	xtype: 'button',
            	id: 'addFormAcquisitionUnit_Id',
            	text: cosog.string.save,
                iconCls: 'save',
                handler: function () {
                	SaveAcquisitionUnitSubmitBtnForm();
                }
         }, {
                xtype: 'button',
                id: 'updateFormaAquisitionUnit_Id',
                text: cosog.string.update,
                hidden: true,
                iconCls: 'edit',
                handler: function () {
                	UpdateAcquisitionUnitDataInfoSubmitBtnForm();
                }
         }, {
        	 	xtype: 'button',   
        	 	text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("acquisitionUnit_editWin_Id").close();
                }
         }]
        });
        Ext.apply(me, {
            items: postacquisitionUnitEditForm
        });
        me.callParent(arguments);
    }

});