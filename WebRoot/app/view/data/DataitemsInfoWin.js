Ext.define('AP.view.data.DataitemsInfoWin', {
    extend: 'Ext.Window',
    alias: 'widget.dataitemsInfoWin',
    id: "DataitemsInfoWinId",
    width: 450,
    height: 500,
    layout: 'fit',
    closeAction: 'destroy',
    resizable: false,
    constrain: true,
    modal: true,
    bodyStyle: 'padding:10px;background-color:#ffffff;',
    initComponent: function () {
        var me = this;
        //英文名称
        var sysdata_code = Ext.create("Ext.form.TextField", {
            id: "sysDataCode_Ids",
            name: 'dataitemsInfo.code',
            fieldLabel: loginUserLanguageResource.dataColumnCode+'<font color=red>*</font>',
            allowBlank: false,
            anchor : '95%',
            msgTarget: 'side',
            value :'',
            blankText: loginUserLanguageResource.required
        });
        //顺序
        var sysdata_sorts = Ext.create("Ext.form.NumberField", {
            id: "sysdatasorts_Ids",
            name: 'dataitemsInfo.sorts',
            fieldLabel: loginUserLanguageResource.sortNum+'<font color=red>*</font>',
            allowBlank: false,
            minValue: 0,
            anchor : '95%',
            msgTarget: 'side',
            blankText: loginUserLanguageResource.required
        });
        //数据项的值
        var sysdata_datavalue = Ext.create("Ext.form.TextArea", {
            id: "sysdatadatavalue_Ids",
            name: 'dataitemsInfo.datavalue',
            fieldLabel: loginUserLanguageResource.dataColumnParams,
            anchor : '95%',
            height: 100
        });
        
        
        var columnDataSourceStore = new Ext.data.SimpleStore({
        	fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
			proxy : {
				url : context+ '/wellInformationManagerController/loadCodeComboxListWithoutAll',
				type : "ajax",
				actionMethods: {
                    read: 'POST'
                },
                reader: {
                	type: 'json',
                    rootProperty: 'list',
                    totalProperty: 'totals'
                }
			},
			autoLoad : true,
			listeners : {
				beforeload : function(store, options) {
					var new_params = {
							itemCode: 'DICTDATASOURCE'
					};
					Ext.apply(store.proxy.extraParams,new_params);
				}
			}
		});
        
        var columnDataSourceComb = Ext.create(
        		'Ext.form.field.ComboBox', {
					fieldLabel :  loginUserLanguageResource.columnDataSource+'<font color=red>*</font>',
					anchor : '95%',
					id : 'dictItemColumnDataSourceComb_Id',
					store: columnDataSourceStore,
					queryMode : 'remote',
					typeAhead : true,
					autoSelect : false,
					allowBlank : false,
					triggerAction : 'all',
					editable : false,
					displayField : "boxval",
					valueField : "boxkey",
					listeners : {
						select: function (combo, record, eOpts) {
							if(combo.value==1){//字段数据来源为驱动配置
								Ext.getCmp("sysDataCode_Ids").hide();
								Ext.getCmp("dictItemDataSourceComb_Id").show();
								Ext.getCmp("dictItemDataUnit_Id").show();
								
								Ext.getCmp("sysDataCode_Ids").setConfig({
								    allowBlank: true
								});
								Ext.getCmp("dictItemDataSourceComb_Id").setConfig({
								    allowBlank: false
								});
								
							}else if(combo.value==2){//字段数据来源为附加信息
								Ext.getCmp("sysDataCode_Ids").hide();
								Ext.getCmp("dictItemDataSourceComb_Id").hide();
								Ext.getCmp("dictItemDataUnit_Id").hide();
								
								Ext.getCmp("sysDataCode_Ids").setConfig({
								    allowBlank: true
								});
								Ext.getCmp("dictItemDataSourceComb_Id").setConfig({
								    allowBlank: true
								});
							}else{//字段数据来源为基础字段
								Ext.getCmp("sysDataCode_Ids").show();
								Ext.getCmp("dictItemDataSourceComb_Id").hide();
								Ext.getCmp("dictItemDataUnit_Id").hide();
								
								Ext.getCmp("sysDataCode_Ids").setConfig({
								    allowBlank: false
								});
								Ext.getCmp("dictItemDataSourceComb_Id").setConfig({
								    allowBlank: true
								});
							}
							Ext.getCmp("dictItemColumnDataSource_Id").setValue(this.value);
	                    }
					}
				});
        
        var dataSourceStore = new Ext.data.SimpleStore({
        	fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
			proxy : {
				url : context+ '/wellInformationManagerController/loadCodeComboxListWithoutAll',
				type : "ajax",
				actionMethods: {
                    read: 'POST'
                },
                reader: {
                	type: 'json',
                    rootProperty: 'list',
                    totalProperty: 'totals'
                }
			},
			autoLoad : true,
			listeners : {
				beforeload : function(store, options) {
					var new_params = {
							itemCode: 'DATASOURCE',
							values: '0,1,2'
					};
					Ext.apply(store.proxy.extraParams,new_params);
				}
			}
		});
        
        var dataSourceComb = Ext.create(
        		'Ext.form.field.ComboBox', {
					fieldLabel :  loginUserLanguageResource.dataSource,
					anchor : '95%',
					id : 'dictItemDataSourceComb_Id',
					store: dataSourceStore,
					queryMode : 'remote',
					hidden: true,
					typeAhead : true,
					autoSelect : false,
					allowBlank : false,
					triggerAction : 'all',
					editable : false,
					displayField : "boxval",
					valueField : "boxkey",
					listeners : {
						select: function (combo, record, eOpts) {
							Ext.getCmp("dictItemDataSource_Id").setValue(this.value);
	                    }
					}
				});
        
        //表单组件		
        var addtenaorgLevfromname = Ext.create("Ext.form.Panel", {
            id: "addtenaorgLevfromnameId",
//            layout: 'auto',
//            border: false,
//            labelSeparator: ':',
//            bodyStyle: 'padding:20px;',
//            defaultType: 'textfield',
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [
                {
                    xtype: 'container',
                    height: 51,
                    width: 501,
                    html: '<table width="468" height="42" border="0" cellspacing="0" style="font-size: 12px;color: #999999;"><tr><td width="95" height="21">'+loginUserLanguageResource.tip+'：</td><td width="357">&nbsp;</td></tr><tr><td height="26"></td><td> '+loginUserLanguageResource.requiredInfo+' </td></tr></table><div  class="divider_s"></div>'
                },
                {
                    id: "sysDataName_zh_CN_Ids",
                    name: 'dataitemsInfo.name_zh_CN',
                    fieldLabel: loginUserLanguageResource.dataColumnName+'<font color=red>*</font>',
                    allowBlank:(loginUserLanguage.toUpperCase()=='ZH_CN'?false:true),
                    hidden:(loginUserLanguage.toUpperCase()=='ZH_CN'?false:true),
                    anchor : '95%',
                    msgTarget: 'side',
                    blankText: loginUserLanguageResource.required
                },
                {
                    id: "sysDataName_en_Ids",
                    name: 'dataitemsInfo.name_en',
                    fieldLabel: loginUserLanguageResource.dataColumnName+'<font color=red>*</font>',
                    allowBlank:(loginUserLanguage.toUpperCase()=='EN'?false:true),
                    hidden:(loginUserLanguage.toUpperCase()=='EN'?false:true),
                    anchor : '95%',
                    msgTarget: 'side',
                    blankText: loginUserLanguageResource.required
                },
                {
                    id: "sysDataName_ru_Ids",
                    name: 'dataitemsInfo.name_ru',
                    fieldLabel: loginUserLanguageResource.dataColumnName+'<font color=red>*</font>',
                    allowBlank:(loginUserLanguage.toUpperCase()=='RU'?false:true),
                    hidden:(loginUserLanguage.toUpperCase()=='RU'?false:true),
                    anchor : '95%',
                    msgTarget: 'side',
                    blankText: loginUserLanguageResource.required
                },columnDataSourceComb,{
                	xtype: "hidden",
                    id: 'dictItemColumnDataSource_Id',
                    value: '',
                    name: "dataitemsInfo.columnDataSource"
                },{
                	xtype: "hidden",
                    id: 'dictItemDeviceType_Id',
                    value: '',
                    name: "dataitemsInfo.deviceType"
                },dataSourceComb,{
                	xtype: "hidden",
                    id: 'dictItemDataSource_Id',
                    value: '',
                    name: "dataitemsInfo.dataSource"
                }, sysdata_code,{
                    id: 'dictItemDataUnit_Id',
                    value: '',
                    name: "dataitemsInfo.dataUnit",
                    fieldLabel: loginUserLanguageResource.unit,
                    hidden:true,
                    anchor : '95%',
                    msgTarget: 'side',
                },
                {
                    xtype: 'radiogroup',
//                    width: 200,
                    anchor : '95%',
                    id: "dataitemsInfo_status_cn_id",
                    fieldLabel: loginUserLanguageResource.language_zh_CN+'<font color=red>*</font>',
                    items: [
                        {
                            checked: true,
                            boxLabel: loginUserLanguageResource.yes,
                            inputValue: 1,
                            name: 'dataitemsInfo.status_cn'
                          },
                        {
                            boxLabel: loginUserLanguageResource.no,
                            inputValue: 0,
                            name: 'dataitemsInfo.status_cn'
                          }
                      ]
                },
                {
                    xtype: 'radiogroup',
                    anchor : '95%',
                    id: "dataitemsInfo_status_en_id",
                    fieldLabel: loginUserLanguageResource.language_en+'<font color=red>*</font>',
                    items: [
                        {
                            checked: true,
                            boxLabel: loginUserLanguageResource.yes,
                            inputValue: 1,
                            name: 'dataitemsInfo.status_en'
                          },
                        {
                            boxLabel: loginUserLanguageResource.no,
                            inputValue: 0,
                            name: 'dataitemsInfo.status_en'
                          }
                      ]
                },
                {
                    xtype: 'radiogroup',
                    anchor : '95%',
                    id: "dataitemsInfo_status_ru_id",
                    fieldLabel: loginUserLanguageResource.language_ru+'<font color=red>*</font>',
                    items: [
                        {
                            checked: true,
                            boxLabel: loginUserLanguageResource.yes,
                            inputValue: 1,
                            name: 'dataitemsInfo.status_ru'
                          },
                        {
                            boxLabel: loginUserLanguageResource.no,
                            inputValue: 0,
                            name: 'dataitemsInfo.status_ru'
                          }
                      ]
                },
                {
                    xtype: 'radiogroup',
                    anchor : '95%',
                    id: "dataitemsInfo_status_id",
                    fieldLabel: loginUserLanguageResource.enable+'<font color=red>*</font>',
                    items: [
                        {
                            checked: true,
                            boxLabel: loginUserLanguageResource.yes,
                            inputValue: 1,
                            name: 'dataitemsInfo.status'
                          },
                        {
                            boxLabel: loginUserLanguageResource.no,
                            inputValue: 0,
                            name: 'dataitemsInfo.status'
                          }
                      ]
                },
                sysdata_sorts, sysdata_datavalue,
                {
                    xtype: 'textfield',
                    name: 'dataitemsInfo.sysdataid',
                    id: 'hide_addsys_Id',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'dataitemsInfo.dataitemid',
                    id: 'hide_dataitemids',
                    hidden: true
                }]
        	});
        Ext.apply(me, {
            items: [addtenaorgLevfromname]
        });
        me.callParent(arguments);
    },
    buttons: [
        {
            id: "oktosysfordataFormBtnId",
            text: loginUserLanguageResource.confirm,
            iconCls: 'save',
            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
            action: 'oktosysfordataAction'
        },
        {
            id: "savettosysfordataFormBtnId",
            text: loginUserLanguageResource.save,
            iconCls: 'save',
            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
            action: 'savetosysfordatasAction',
            hidden: true
        },
        {
            id: "editttosysfordataFormBtnId",
            text: loginUserLanguageResource.save,
            iconCls: 'save',
            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
            action: 'edittosysfordatasAction',
            hidden: true
        },
        {
            id: "cancltosysfordataBtnId",
            text: loginUserLanguageResource.cancel,
            iconCls: 'cancel',
            closewin: 'DataitemsInfoWinId',
            handler: closeWindow
        }
              ]
});