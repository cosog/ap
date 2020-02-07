Ext.define('AP.view.wellTrajectory.WellTrajectoryInfoWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.wellTrajectoryInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'welltrajectoryWindow_addwin_Id',
    closeAction: 'destroy',
    width: 330,
    shadow: 'sides',
    resizable: false,
    collapsible: true,
    maximizable: false,
    plain: true,
    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
    modal: true,
    border: false,
    initComponent: function () {
        var me = this;
        var WellNumberWellInfoStore = Ext.create('AP.store.wellTrajectory.WellNumberWellInfoStore');
        var wellWindowEditFrom = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                xtype: 'hidden',
                fieldLabel: '序号',
                id: 'jlbh_Id',
                name: "well.jlbh"
       }, {
                xtype: 'hidden',
                fieldLabel: '井编号',
                id: 'jbh_Id',
                anchor: '95%',
                name: "well.jbh"
       }, {
                xtype: "combobox",
                id: 'wellTrajectoryaddjh_Id',
                fieldLabel: cosog.string.jh,
                displayField: 'jh',
                value: '1',
                name: 'well.jh',
                valueField: 'jh',
                store: WellNumberWellInfoStore,
                triggerAction: 'all',
                queryMode: 'remote',
                selectOnFocus: true,
                anchor: '95%',
                allowBlank: false,
                editable: true,
                emptyText: '',
                blankText: '',
                listeners: {
                    expand: function (sm, selections) {
                        //sm.setValue("");
                    },
                    beforequery: function (qe) {
                        delete qe.combo.lastQuery;
                    },
                    delay: 200
                }
       }, {
                fieldLabel: cosog.string.clsd,
                id: 'clsd_Id',
                anchor: '95%',
                name: "well.clsd"
       }, {
                fieldLabel: cosog.string.czsd,
                id: 'czsd_Id',
                value: '',
                anchor: '95%',
                name: "well.czsd"
       }, {
                fieldLabel: cosog.string.jxj,
                id: 'jxj_Id',
                anchor: '95%',
                value: '',
                name: "well.jxj"
       }, {
                fieldLabel: cosog.string.fwj,
                id: 'fwj_Id',
                anchor: '95%',
                value: '',
                name: "well.fwj"
       }],
            buttons: [{
                xtype: 'button',
                id: 'addFromwelltrajectoryWindowBtn_Id',
                text: cosog.string.save,
                iconCls: 'save',
                handler: SavewelltrajectoryWindowSubmitBtnForm
       }, {
                xtype: 'button',
                id: 'updateFromwelltrajectoryWindowBtn_Id',
                text: cosog.string.update,
                iconCls: 'edit',
                hidden: true,
                handler: UpdatewelltrajectoryWindowSubmitBtnForm
       }, {
                text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("welltrajectoryWindow_addwin_Id").close();
                }
       }]
        });
        Ext.apply(me, {
            items: wellWindowEditFrom
        })
        me.callParent(arguments);
    }
})