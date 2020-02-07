Ext.define('AP.view.data.SystemdataInfoWin', {
    extend: 'Ext.window.Window',
    alias: 'widget.systemdataInfoWin',
    id: "SystemdataInfoWinId",
    width: 1100,
    height: 600,
    closeAction: 'destroy',
//    layout: 'fit',
    iframe:true,
    shadow: 'sides',
    resizable: true,
    collapsible:false,
    constrain: true,
    plain:true,
    bodyStyle: 'background-color:#ffffff;',
    modal: true,
	layout: 'border',
	items:[
		{
			region: 'north',
			height:'150px',
			xtype:'form',
			border: false,
            id: "sysSubFormId",
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
                                    {
                                    	xtype:'textfield',
                                    	id: "syscname_Id",
                                        name: 'systemdataInfo.cname',
                                        fieldLabel: cosog.string.dataModuleName,
                                        allowBlank: false,
                                        width: 300,
                                        msgTarget: 'side',
                                        blankText: cosog.string.required
                                    }, {
                                    	xtype:'textfield',
                                    	id: "sysename_Id",
                                        name: 'systemdataInfo.ename',
                                        fieldLabel: cosog.string.dataModuleCode,
                                        vtype: "alpha",
                                        width: 300,
                                        allowBlank: false,
                                        msgTarget: 'side',
                                        blankText: cosog.string.required
                                    }, {
                                    	xtype:'numberfield',
                                    	id: "syssorts_Id",
                                        name: 'systemdataInfo.sorts',
                                        fieldLabel: cosog.string.dataSorts,
                                        allowBlank: false,
                                        minValue: 0,
                                        width: 300,
                                        msgTarget: 'side',
                                        blankText: cosog.string.required
                                    },
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
                }
            ]
			
		},{
			region: 'center',
			border:false,
			id:'DataItemsListPanel_Id',
			layout: 'fit'
		}
	],
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
            text: cosog.string.save,
            iconCls: 'save',
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