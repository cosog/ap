Ext.define('AP.view.data.SystemdataInfoWin', {
    extend: 'Ext.window.Window',
    alias: 'widget.systemdataInfoWin',
    id: "SystemdataInfoWinId",
    width: 780,
    height: 470,
    closeAction: 'destroy',
    layout: 'fit',
    iframe:true,
    shadow: 'sides',
    resizable: true,
    collapsible:false,
    constrain: true,
    plain:true,
    bodyStyle: 'background-color:#ffffff;',
    modal: true,
    initComponent: function () {
        var me = this;
        //中文名称
        var sys_cname = Ext.create("Ext.form.TextField", {
            id: "syscname_Id",
            name: 'systemdataInfo.cname',
            fieldLabel: cosog.string.dataModuleName,
            allowBlank: false,
            width: 300,
            msgTarget: 'side',
            blankText: cosog.string.required
        });
        //英文名称
        var sys_ename = Ext.create("Ext.form.TextField", {
            id: "sysename_Id",
            name: 'systemdataInfo.ename',
            fieldLabel: cosog.string.dataModuleCode,
            vtype: "alpha",
            width: 300,
            allowBlank: false,
            msgTarget: 'side',
            blankText: cosog.string.required
        });
        //顺序
        var sys_sorts = Ext.create("Ext.form.NumberField", {
            id: "syssorts_Id",
            name: 'systemdataInfo.sorts',
            fieldLabel: cosog.string.dataSorts,
            allowBlank: false,
            minValue: 0,
            width: 300,
            msgTarget: 'side',
            blankText: cosog.string.required
        });
        //添加参数Panel
        var sysadditems = Ext.create("AP.view.data.DataitemsInfoAddGridPanel");
        //修改参数Panel
        var sysedititems = Ext.create("AP.view.data.DataitemsInfoEditGridPanel");
        var sysSubFormName = Ext.create("Ext.form.Panel", {
            border: false,
            id: "sysSubFormId",
            layout: 'auto',
            labelSeparator: ':',
            layout: 'anchor',
            items: [
                {
                    xtype: "panel",
                    layout: "fit",
                    border: false,
                    bodyStyle: "padding:10px;",
                    items: [
                        {
                            xtype: 'fieldset',
                            title: cosog.string.msg,
                            style: "padding:10px;",
                            collapsed: false,
                            items: [
                                    sys_cname, sys_ename, sys_sorts,
                                {
                                    xtype: 'textfield',
                                    name: 'systemdataInfo.sysdataid',
                                    id: 'hidesysdata_Id',
                                    hidden: true
                                },
                                {
                                    xtype: 'textfield',
                                    name: 'hideSysDataValName',
                                    id: 'hideSysDataValName_Id',
                                    width: 300,
                                    hidden: true
                                },
                                {
                                    xtype: 'textfield',
                                    name: 'systemdataInfo.status',
                                    id: 'hidesysstatus_Id',
                                    hidden: true
                                }
                            ]
                        }
                    ]
                },
                sysadditems, sysedititems
            ]
        });

        Ext.apply(me, {
            items: [
                 sysSubFormName
             ],
             listeners: {
                 resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                	 var gridPanel=Ext.getCmp("DataitemsInfoEditGridPanelId");
                	 if(isNotVal(gridPanel)){
                		 gridPanel.getView().refresh();
                	 }
                 }
             }
        });
        me.callParent(arguments);

    },
    buttons: [{
            	id: "systaddtodataitemsBtnId",
            	text: cosog.string.dataShowParams,
            	action: 'zpsystemdataitemsSubmitForm',
            	handler: function () {
            		var datiswin = Ext.create("AP.view.data.DataitemsInfoWin", {
            			title: cosog.string.dataValue
            		});
            		datiswin.show();
            		return false;
            	}
            },
        {
            id: "sysSDSaveBtnId",
            text: cosog.string.save,
            iconCls: 'save',
            action: 'SystemdataInfoSubmitForm'
        },
        {
            id: "sysSDUpdBtnId",
            text: cosog.string.update,
            iconCls: 'edit',
            action: 'SystemdataInfoUpdataForm',
            hidden: true
        },
        {
            text: cosog.string.cancel,
            closewin: 'SystemdataInfoWinId',
            iconCls: 'cancel',
            handler: closeWindow
        }]

});