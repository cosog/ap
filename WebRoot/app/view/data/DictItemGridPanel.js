Ext.define('AP.view.data.DictItemGridPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.dictItemGridPanel',
    id: "DictItemGridPanelId",
    border: false,
    layout: 'fit',
    initComponent: function () {
        Ext.apply(this, {
        	layout: "border",
            items: [{
                region: 'center',
                layout: 'fit',
                title: '',
    			id:"dataDictionaryItemPanel_Id",
                tbar: [{
        			xtype : "combobox",
        			id : 'dataDictionaryItemSearchTypeComb_Id',
        			fieldLabel: loginUserLanguageResource.type,
    	            labelWidth: getLabelWidth(loginUserLanguageResource.type,loginUserLanguage),
    	            width: getLabelWidth(loginUserLanguageResource.type,loginUserLanguage)+120,
    				triggerAction : 'all',
    				selectOnFocus : false,
    			    forceSelection : true,
    			    value:0,
    			    allowBlank: false,
    				editable : false,
    				store : new Ext.data.SimpleStore({fields : ['value', 'text'],data: [[0, loginUserLanguageResource.dataColumnCode], [1, loginUserLanguageResource.dataColumnName]]}),
    				displayField : 'text',
    				valueField : 'value',
    				queryMode : 'local',
    				listeners : {
    					select:function(v,o){
    						
    					}
    				}
                },'-',{
                	xtype: 'textfield',
                    id: 'dataDictionaryItemSearchValue_Id',
                    fieldLabel: loginUserLanguageResource.name,
                    labelWidth: getLabelWidth(loginUserLanguageResource.name,loginUserLanguage),
                    width: getLabelWidth(loginUserLanguageResource.name,loginUserLanguage)+120
                },'-',{
                	xtype: 'button',
                    text: loginUserLanguageResource.search,
                    iconCls: 'search',
                    handler: function () {
                    	var dataDictionaryItemGridPanel = Ext.getCmp("dataDictionaryItemGridPanel_Id"); 
                    	if (isNotVal(dataDictionaryItemGridPanel)) {
                    		dataDictionaryItemGridPanel.getStore().load();
                    	}else{
                    		Ext.create("AP.store.data.DataDictionaryItemInfoStore");
                    	}
                    }
                },'->',{
                	xtype: 'button',
                    text: loginUserLanguageResource.add,
                    iconCls: 'add',
                    disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                    tooltip: loginUserLanguageResource.addDataItem,
                    id:'addDictionaryItemBtn_Id',
                    handler: function () {
                    	addfindtattxtInfo();
                    }
                },'-',{
                	xtype: 'button',
                    text: loginUserLanguageResource.batchDeleteData,
                    iconCls: 'delete',
                    disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                    handler: function () {
                    	delfindtattxtInfo();
                    }
                }]
            }]
        });
        this.callParent(arguments);
    }

});