var loginUserDataDictionaryManagementModuleRight=getRoleModuleRight('DataDictionaryManagement');
Ext.define("AP.view.data.SystemdataInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.systemdataInfoView',
    layout: 'border',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        var SystemdataInfoGridPanel = Ext.create('AP.view.data.SystemdataInfoGridPanel');
        Ext.apply(me, {
        	items:[{
        		region:'center',
        		layout: "fit",
        		header:false,
        		items:SystemdataInfoGridPanel
        	},{
        		region:'east',
        		width:'45%',
        		layout: "fit",
        		header:false,
        		id:'dataDictionaryItemPanel_Id',
        		tbar:[{
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
                    handler: function () {
                    	addfindtattxtInfo();
                    }
                }
//                ,'-',{
//                	xtype: 'button',
//                    text: loginUserLanguageResource.update,
//                    iconCls: 'edit',
//                    disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
//                    tooltip: loginUserLanguageResource.editDataItem,
//                    handler: function () {
//                    	
//                    }
//                }
                ,'-',{
                	xtype: 'button',
                    text: loginUserLanguageResource.batchDeleteData,
                    iconCls: 'delete',
                    disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                    handler: function () {
                    	delfindtattxtInfo();
                    }
                }
                ]
        	}]
        });
        me.callParent(arguments);
    }

});