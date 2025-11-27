Ext.define("AP.view.module.ModuleInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.moduleInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'module_addwin_Id',
    closeAction: 'destroy',
    width: 320,
    shadow: 'sides',
    resizable: true,
    collapsible: true,
    constrain: true,
    maximizable: false,
    layout: 'fit',
    plain: true,
    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
    modal: true,
    border: false,
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
        var xltree=Ext.create('AP.view.well.TreePicker',{
        	id:'mdName_Parent_Id1',
        	anchor: '95%',
        	fieldLabel: loginUserLanguageResource.superModule+'<font color=red>*</font>',
            emptyText: '--'+loginUserLanguageResource.checkModule+'--',
            blankText: '--'+loginUserLanguageResource.checkModule+'--',
            displayField: 'text',
            allowBlank: false,
            autoScroll:true,
            forceSelection : true,// 只能选择下拉框里面的内容
            rootVisible: false,
            store:ModTreeStore,
            listeners: {
                select: function (picker,record,eOpts) {
                	Ext.getCmp("module_addwin_Id").down('form').getChildByElement("mdName_Parent_Id").setValue(record.data.id);
                }
            }
        });
        var ModuleTypeStore = new Ext.data.SimpleStore({
            fields: [{
                name: "boxkey",
                type: "string"
         }, {
                name: "boxval",
                type: "string"
         }],
            proxy: {
                url: context + '/moduleManagerController/loadModuleType',
                type: "ajax",
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json'
                }
            },
            autoLoad: true,
            listeners: {
                beforeload: function (store, options) {
                    var new_params = {
                        type: 'mklx'
                    };
                    Ext.apply(store.proxy.extraParams,
                        new_params);
                }
            }
        });

        // Simple ComboBox using the data store
        var moduleTypeCombox = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: loginUserLanguageResource.moduleType+'<font color=red>*</font>',
                id: 'mdType_Id',
                name: 'module.mdType',
                anchor: '95%',
                value: '0',
                store: ModuleTypeStore,
                emptyText: '--'+loginUserLanguageResource.all+'--',
                blankText: '--'+loginUserLanguageResource.all+'--',
                typeAhead: true,
                allowBlank: false,
                triggerAction: 'all',
                displayField: "boxval",
                valueField: "boxkey",
                listeners: {
                    blur: function (v, o) {
                        if (this.value == null) {
                            Ext.getCmp("mdSeq_Id").setValue("0");
                        }
                    }
                }
            });

        var postmoduleEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: loginUserLanguageResource.idx,
                id: 'md_Id',
                name: "module.mdId"
     }, {
                xtype: "hidden",
                fieldLabel: '模块父ID',
                id: 'mdName_Parent_Id',
                anchor: '95%',
                name: 'module.mdParentid'
     }, xltree, {
                fieldLabel: loginUserLanguageResource.moduleName+'<font color=red>*</font>',
                id: 'mdName_zh_CN_Id',
                allowBlank:(loginUserLanguage.toUpperCase()=='ZH_CN'?false:true),
                hidden:(loginUserLanguage.toUpperCase()=='ZH_CN'?false:true),
                anchor: '95%',
                name: "module.mdName_zh_CN"
     }, {
         		fieldLabel: loginUserLanguageResource.moduleName+'<font color=red>*</font>',
         		id: 'mdName_en_Id',
         		allowBlank:(loginUserLanguage.toUpperCase()=='EN'?false:true),
         		hidden:(loginUserLanguage.toUpperCase()=='EN'?false:true),
         		anchor: '95%',
         		name: "module.mdName_en"
     }, {
    	 		fieldLabel: loginUserLanguageResource.moduleName+'<font color=red>*</font>',
    	 		id: 'mdName_ru_Id',
    	 		allowBlank:(loginUserLanguage.toUpperCase()=='RU'?false:true),
    	 		hidden:(loginUserLanguage.toUpperCase()=='RU'?false:true),
    	 		anchor: '95%',
    	 		name: "module.mdName_ru"
     }, {
                fieldLabel: loginUserLanguageResource.moduleMemo,
                id: 'mdShowname_zh_CN_Id',
                hidden:(loginUserLanguage.toUpperCase()=='ZH_CN'?false:true),
                value: '',
                anchor: '95%',
                name: "module.mdShowname_zh_CN"
     }, {
         		fieldLabel: loginUserLanguageResource.moduleMemo,
         		id: 'mdShowname_en_Id',
         		hidden:(loginUserLanguage.toUpperCase()=='EN'?false:true),
         		value: '',
         		anchor: '95%',
         		name: "module.mdShowname_en"
     }, {
    	 		fieldLabel: loginUserLanguageResource.moduleMemo,
    	 		id: 'mdShowname_ru_Id',
    	 		hidden:(loginUserLanguage.toUpperCase()=='RU'?false:true),
    	 		value: '',
    	 		anchor: '95%',
    	 		name: "module.mdShowname_ru"
     }, {
                id: 'mdCode_Id',
                fieldLabel: loginUserLanguageResource.moduleCode+'<font color=red>*</font>',
                allowBlank: false,
                hidden:true,
                value: '',
                anchor: '95%',
                name: "module.mdCode"
     }, {
                xtype: "textfield",
                fieldLabel: loginUserLanguageResource.moduleView+'<font color=red>*</font>',
                allowBlank: false,
                hidden:true,
                id: 'mdUrl_Id',
                anchor: '95%',
                name: "module.mdUrl"

     }, {
                xtype: "textfield",
                fieldLabel: loginUserLanguageResource.moduleControlller+'<font color=red>*</font>',
                allowBlank: false,
                hidden:true,
                anchor: '95%',
                id: 'mdControl_Id',
                name: "module.mdControl"

     }, {
                xtype: "textfield",
                fieldLabel: loginUserLanguageResource.moduleIcon,
                anchor: '95%',
                id: 'mdIcon_Id',
                name: "module.mdIcon"

     }, moduleTypeCombox, {
                fieldLabel: loginUserLanguageResource.moduleSort,
                id: 'mdSeq_Id',
                anchor: '95%',
                xtype: 'numberfield',
                value: '1',
                name: "module.mdSeq"
     }],
            buttons: [{
                id: 'addFormmodule_Id',
                xtype: 'button',
                text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: SavemoduleDataInfoSubmitBtnForm
     }, {
                xtype: 'button',
                id: 'updateFormmodule_Id',
                text: loginUserLanguageResource.update,
                hidden: true,
                iconCls: 'edit',
                handler: UpdatemoduleDataInfoSubmitBtnForm
     }, {
                text: loginUserLanguageResource.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("module_addwin_Id").close();
                }
     }]
        });
        Ext.apply(me, {
            items: postmoduleEditForm
        });
        me.callParent(arguments);
    }

});