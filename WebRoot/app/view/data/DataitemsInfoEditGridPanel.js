Ext.define('AP.view.data.DataitemsInfoEditGridPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.dataitemsInfoEditGridPanel',
    id: "DataitemsInfoEditGridPanelId",
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_ditmaeditsId'><" + loginUserLanguageResource.emptyMsg + "></div>",
        forceFit: true
    },
    columnLines: true,
    border:false,
    selType: (loginUserDataDictionaryManagementModuleRight.editFlag==1?'checkboxmodel':''),
    multiSelect: true,
    initComponent: function () {
        var appEditDataItesmStore = Ext.create("AP.store.data.DataitemsInfoStore");
        var findtatimsstore = new Ext.data.SimpleStore({
            fields: ['findtatId', 'findtatName'],
            data: [[0, loginUserLanguageResource.dataColumnCode], [1, loginUserLanguageResource.dataColumnName]]
        });
        var findtatsimp = new Ext.form.ComboBox({
            id: 'findtattxtcobmoxfield_Id',
            value: 0,
            fieldLabel: cosog.string.type,
            allowBlank: false,
            triggerAction: 'all',
            store: findtatimsstore,
            labelWidth: 35,
            width: 155,
            displayField: 'findtatName',
            valueField: 'findtatId'
        });
        
        var bbar = new Ext.PagingToolbar({
        	store: appEditDataItesmStore,
//        	displayMsg: '当前 {0}~{1}条  共 {2} 条',
        	displayInfo: true
        });
        
        var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit: 1
        });
        Ext.apply(this, {
            plugins: [cellEditing],
            store: appEditDataItesmStore,
            tbar: [findtatsimp,'-',{
                    xtype: 'textfield',
                    id: 'findtattxtnames_Id',
                    fieldLabel: loginUserLanguageResource.name,
                    labelWidth: 35,
                    width: 155,
                    listeners: {
                        specialkey: function (field, e) {
                            RefreachEnter(e, "DataitemsInfoEditGridPanelId");
                        }
                    }
                },'-',{
                    xtype: 'button',
                    id: "findtattxtInfoBtnId",
                    text: loginUserLanguageResource.search,
                    iconCls: 'search',
                    action: 'findtattxtInfoBtnAction',
                    handler: function () {
                        reFreshg("DataitemsInfoEditGridPanelId");
                    }
                },'->',{
                    xtype: 'button',
                    itemId: 'addfindtattxtBtnId',
                    id: 'addfindtattxtBtn_Id',
                    action: 'addfindtattxtInfoAction',
                    text: loginUserLanguageResource.add,
                    iconCls: 'add',
                    disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                    tooltip: loginUserLanguageResource.addDataItem
                }, '-',{
                    xtype: 'button',
                    itemId: 'editfindtattxtBtnId',
                    id: 'editfindtattxtBtn_Id',
                    text: loginUserLanguageResource.update,
                    action: 'editfindtattxtInfoBtnAction',
                    disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                    iconCls: 'edit',
                    tooltip: loginUserLanguageResource.editDataItem
                }, '-',{
                    xtype: 'button',
                    itemId: 'delfindtattxtBtnId',
                    id: 'delfindtattxtBtn_Id',
                    disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                    action: 'delfindtattxtInfoBtnAction',
                    text: loginUserLanguageResource.deleteData,
                    iconCls: 'delete'
               }],
            bbar: bbar,
            columns: [
                {
                    header: loginUserLanguageResource.dataColumnName,
                    flex: 1,
                    hidden:(loginUserLanguage.toUpperCase()=='ZH_CN'?false:true),
                    dataIndex: 'name_zh_CN'
                },{
                    header: loginUserLanguageResource.dataColumnName,
                    flex: 1,
                    hidden:(loginUserLanguage.toUpperCase()=='EN'?false:true),
                    dataIndex: 'name_en'
                },{
                    header: loginUserLanguageResource.dataColumnName,
                    flex: 1,
                    hidden:(loginUserLanguage.toUpperCase()=='RU'?false:true),
                    dataIndex: 'name_ru'
                },
                {
                    header: loginUserLanguageResource.dataColumnCode,
                    flex: 1,
                    dataIndex: 'code'
                },
                {
                    header: loginUserLanguageResource.dataColumnParams,
                    flex: 1,
                    dataIndex: 'datavalue'
                },
                {
                    header: loginUserLanguageResource.sortNum,
                    width: 40,
                    dataIndex: 'sorts'
                },
                {
                    xtype: 'checkcolumn',
                    header: loginUserLanguageResource.enable,
                    disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                    dataIndex: 'status',
                    width: 65,
                    editor: {
                        xtype: 'checkbox',
                        cls: 'x-grid-checkheader-editor'
                    },
                    listeners: {
                        checkchange: function (sm, e, ival, o, n) {
                            var items_ = appEditDataItesmStore.data.items;
                            if (items_.length > 0) {
                            	if (ival) {
                                    ival = 1;
                                } else {
                                    ival = 0;
                                }
                                Ext.Array.each(items_, function (items_n, index, fog) {
                                    if (e == index) {
                                        var hide_obj_ = Ext.getCmp("hideSysDataValName_Id");
                                        var hide_val_ = hide_obj_.getValue();
                                        var status_ = items_[index].data.status;
                                        var obj_id = items_[index].data.dataitemid;
                                        var resultstring = obj_id + ":" + ival + ",";
                                        if (null != hide_val_ && hide_val_ != "") {
                                        	hide_obj_.setValue(hide_val_ + resultstring);
                                        } else {
                                        	hide_obj_.setValue(resultstring);
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            ]
        });

        this.callParent(arguments);
    },
    listeners: {
        selectionchange: function (sm, selections) {
        	
        }
    }
});