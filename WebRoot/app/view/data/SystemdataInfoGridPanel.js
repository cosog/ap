Ext.define('AP.view.data.SystemdataInfoGridPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.systemdataInfoGridPanel',
    id: "SystemdataInfoGridPanelViewId",
    border: false,
    layout: 'fit',
    initComponent: function () {
        var geSystemdataInfoStore = Ext.create("AP.store.data.SystemdataInfoStore");
        
        var sysdatastore = new Ext.data.SimpleStore({
            fields: ['sysdataId', 'sysdataName'],
            data: [[0, loginUserLanguageResource.dataModuleName], [1, loginUserLanguageResource.dataModuleCode]]
        });
        var sysdatacomboxsimp = new Ext.form.ComboBox({
            id: 'sysdatacomboxfield_Id',
            value: 0,
            fieldLabel: loginUserLanguageResource.type,
            allowBlank: false,
            triggerAction: 'all',
            store: sysdatastore,
            labelWidth: getLabelWidth(loginUserLanguageResource.type,loginUserLanguage),
            width: getLabelWidth(loginUserLanguageResource.type,loginUserLanguage)+120,
            displayField: 'sysdataName',
            valueField: 'sysdataId',
            mode: 'local'
        });
        Ext.apply(this, {
            tbar: [{
            	id: 'DataDictionaryManagementModuleViewFlag',
            	xtype: 'textfield',
                value: loginUserDataDictionaryManagementModuleRight.viewFlag,
                hidden: true
             },{
            	id: 'DataDictionaryManagementModuleEditFlag',
            	xtype: 'textfield',
                value: loginUserDataDictionaryManagementModuleRight.editFlag,
                hidden: true
             },{
            	id: 'DataDictionaryManagementModuleControlFlag',
            	xtype: 'textfield',
                value: loginUserDataDictionaryManagementModuleRight.controlFlag,
                hidden: true
            },{
            	id: 'selectedDataDictionaryId',
            	xtype: 'textfield',
                value: '',
                hidden: true
            },{
                xtype: 'button',
                text: loginUserLanguageResource.refresh,
                iconCls: 'note-refresh',
                hidden:false,
                handler: function (v, o) {
                	Ext.getCmp('sysdatacomboxfield_Id').setValue(0);
                	Ext.getCmp('sysdatacomboxfield_Id').setRawValue(loginUserLanguageResource.dataModuleName);
                	Ext.getCmp('sysname_Id').setRawValue('');
                	reFreshg("SystemdataInfoGridPanelId");
                }
    		},'-',sysdatacomboxsimp,'-', {
                xtype: 'textfield',
                fieldLabel: loginUserLanguageResource.name,
                labelWidth: getLabelWidth(loginUserLanguageResource.name,loginUserLanguage),
                width: getLabelWidth(loginUserLanguageResource.name,loginUserLanguage)+120,
                id: 'sysname_Id',
                name: 'sysdata_name',
                action: "findsysdatatextaction"
    		}, {
                xtype: "textfield",
                id: "sys_txt_find_ids",
                hidden: true
    		},'-', {
                xtype: 'button',
                id: "findSystemdataInfoId",
                text: loginUserLanguageResource.search,
                iconCls: 'search',
                handler: function () {
                    reFreshg("SystemdataInfoGridPanelId");
                }
    		}, '->', {
                xtype: 'button',
                itemId: 'addBtnId',
                id: 'addSystemdataInfoAction_Id',
                action: 'addSystemdataInfoAction',
                text: loginUserLanguageResource.add,
                disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                iconCls: 'add'
    		},"-", {
                xtype: 'button',
                text: loginUserLanguageResource.exportData,
                iconCls: 'export',
                disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                hidden: false,
                handler: function (v, o) {
                	exportDataDictionaryCompleteData();
                }
            },"-",{
            	xtype: 'button',
    			text: loginUserLanguageResource.importData,
    			disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
    			iconCls: 'import',
    			handler: function (v, o) {
    				var window = Ext.create("AP.view.data.ImportDataDictionaryWindow");
                    window.show();
    			}
            }
//    		, '-', {
//                xtype: 'button',
//                itemId: 'editBtnId',
//                id: 'editSDBtnId',
//                text: loginUserLanguageResource.update,
//                disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
//                action: 'editSystemdataInfoAction',
//                iconCls: 'edit'
//
//    		}, '-', {
//                xtype: 'button',
//                itemId: 'delBtnId',
//                id: 'delSDBtnId',
//                disabled: false,
//                action: 'delSystemdataInfoAction',
//                text: loginUserLanguageResource.deleteData,
//                disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
//                iconCls: 'delete'
//    		}
    		]
        });
        this.callParent(arguments);
        Ext.getCmp("sysname_Id").focus(true, true);
    }

});

function exportDataDictionaryCompleteData(){
	var url = context + '/systemdataInfoController/exportDataDictionaryCompleteData';
	
	var timestamp=new Date().getTime();
	var key='exportDataDictionaryCompleteData'+'_'+timestamp;
	var maskPanelId='SystemdataInfoGridPanelViewId';
	
	
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.dataDictionaryExportFileName)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
//    openExcelWindow(url + '?flag=true' + param);
    downloadFile(url + '?flag=true' + param);
}