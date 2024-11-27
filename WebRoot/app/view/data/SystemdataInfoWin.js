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
	initComponent: function () {
		var me = this;
		
		var ModTreeStore=Ext.create('Ext.data.TreeStore', {
            fields: ['id', 'text', 'leaf'],
            autoLoad: false,
            proxy: {
                type: 'ajax',
                url: context + '/moduleMenuController/obtainAddModuleList',
                reader: 'json'
            },
            root: {
                expanded: true,
                text: loginUserLanguageResource.rootNode
            }
        });
        var moduleTree=Ext.create('AP.view.well.TreePicker',{
        	id:'systemdataModule_Id1',
        	labelWidth: 70,
            width: 300,
        	fieldLabel: '隶属模块<font color=red>*</font>',
            emptyText: cosog.string.checkModule,
            blankText: cosog.string.checkModule,
            displayField: 'text',
            allowBlank: false,
            autoScroll:true,
            forceSelection : true,// 只能选择下拉框里面的内容
            rootVisible: false,
            store:ModTreeStore,
            listeners: {
                select: function (picker,record,eOpts) {
                	if(!record.isLeaf()){
                		Ext.Msg.alert('info', "<font color=red>请选择叶子节点模块！</font>");
                		Ext.getCmp("systemdataModule_Id1").setValue(null);
                		Ext.getCmp("sysmodule_Id").setValue(null);
                	}else{
                		Ext.getCmp("sysmodule_Id").setValue(record.data.id);
                	}
                }
            }
        });
		
		Ext.apply(me, {
			items:[{
					region: 'north',
					height:'150px',
					xtype:'form',
					border: false,
		            id: "sysSubFormId",
		            items: [{
		                    xtype: "panel",
		                    layout: "fit",
		                    border: false,
		                    bodyStyle: "padding:10px;",
		                    items: [{
		                            xtype: 'fieldset',
		                            title: cosog.string.msg,
		                            style: "padding:10px;",
		                            collapsed: false,
		                            items: [{
		                                    	xtype:'textfield',
		                                    	id: "syscname_Id",
		                                        name: 'systemdataInfo.cname',
		                                        fieldLabel: '字典名称<font color=red>*</font>',
		                                        allowBlank: false,
		                                        labelWidth: 70,
		                                        width: 300,
		                                        msgTarget: 'side',
		                                        blankText: cosog.string.required
		                                    }, {
		                                    	xtype:'textfield',
		                                    	id: "sysename_Id",
		                                        name: 'systemdataInfo.ename',
		                                        fieldLabel: '字典代码<font color=red>*</font>',
		                                        vtype: "alpha",
		                                        labelWidth: 70,
		                                        width: 300,
		                                        allowBlank: false,
		                                        msgTarget: 'side',
		                                        blankText: cosog.string.required
		                                    },moduleTree,{
		                                        xtype: "hidden",
		                                        fieldLabel: '模块',
		                                        id: 'sysmodule_Id',
		                                        labelWidth: 70,
		                                        width: 300,
		                                        name: 'systemdataInfo.moduleId'
		                                    }, {
		                                    	xtype:'numberfield',
		                                    	id: "syssorts_Id",
		                                        name: 'systemdataInfo.sorts',
		                                        fieldLabel: cosog.string.dataSorts+'<font color=red>*</font>',
		                                        allowBlank: false,
		                                        minValue: 0,
		                                        labelWidth: 70,
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
            	disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
            	handler: function () {
            		var datiswin = Ext.create("AP.view.data.DataitemsInfoWin", {
            			title: cosog.string.dataValue
            		});
            		datiswin.show();
            		return false;
            	}
		    },{
	            id: "sysSDSaveBtnId",
	            text: loginUserLanguageResource.save,
	            iconCls: 'save',
	            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
	            action: 'SystemdataInfoSubmitForm'
	        },{
	            id: "sysSDUpdBtnId",
	            text: loginUserLanguageResource.save,
	            iconCls: 'save',
	            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
	            action: 'SystemdataInfoUpdataForm',
	            hidden: true
	        },{
	            text: loginUserLanguageResource.cancel,
	            closewin: 'SystemdataInfoWinId',
	            iconCls: 'cancel',
	            handler: closeWindow
	        }]
        });
		me.callParent(arguments);
	}
});