Ext.define("AP.view.operationMaintenance.FunctionTabInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.functionTabInfoWindow',
    id: 'functionTabInfoWindow_Id',
    layout: 'fit',
    iframe: true,
    closeAction: 'destroy',
    width: 400,
    shadow: 'sides',
    resizable: true,
    collapsible: true,
    constrain: true,
    maximizable: false,
    plain: true,
    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
    modal: true,
    border: false,
    initComponent: function () {
        var me = this;
        
        
        var labelWidth=getLabelWidth(loginUserLanguageResource.name,loginUserLanguage);
        if(labelWidth<getLabelWidth(loginUserLanguageResource.name,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.name,loginUserLanguage);
        }
        if(labelWidth<getLabelWidth(loginUserLanguageResource.calculateType,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.calculateType,loginUserLanguage);
        }
        if(labelWidth<getLabelWidth(loginUserLanguageResource.sortNum,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.sortNum,loginUserLanguage);
        }
        
        

        var deviceEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            id: 'addFunctionTabManagerInstanceForm_Id',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: 'id',
                labelWidth: labelWidth,
                id: 'addFunctionTabManagerInstance_Id',
                value: '',
                name: "calculationModel.id"
            },{
                fieldLabel: loginUserLanguageResource.name+'<font color=red>*</font>',
                labelWidth: labelWidth,
                id: 'addFunctionTabManagerInstanceName_Id',
                allowBlank: false,
                anchor: '95%',
                name: "calculationModel.name",
                listeners: {
                	
                }
            },{
            	xtype : "combobox",
				fieldLabel : loginUserLanguageResource.calculateType,
				labelWidth: labelWidth,
				id : 'addFunctionTabManagerInstanceCalculateTypeComb_Id',
				anchor : '95%',
				triggerAction : 'all',
				selectOnFocus : false,
			    forceSelection : true,
			    value:0,
			    allowBlank: false,
				editable : false,
				store : new Ext.data.SimpleStore({
							fields : ['value', 'text'],
							data : [[0, loginUserLanguageResource.nothing],[1, loginUserLanguageResource.SRPCalculate],[2, loginUserLanguageResource.PCPCalculate]]
						}),
				displayField : 'text',
				valueField : 'value',
				queryMode : 'local',
				emptyText : loginUserLanguageResource.selectCalculateType,
				blankText : loginUserLanguageResource.selectCalculateType,
				listeners : {
					select:function(v,o){
						Ext.getCmp("deviceCalculateType_Id").setValue(this.value);
						
					}
				}
            },{
            	xtype: "hidden",
                fieldLabel: loginUserLanguageResource.calculateType,
                labelWidth: labelWidth,
                id: 'addFunctionTabManagerInstanceCalculateType_Id',
                value: '0',
                name: "calculationModel.calculateType"
            },{
            	xtype: 'numberfield',
            	id: "addFunctionTabManagerInstanceSortNum_Id",
            	name: "calculationModel.sort",
                fieldLabel: loginUserLanguageResource.sortNum,
                labelWidth: labelWidth,
                allowBlank: true,
                minValue: 1,
                anchor: '95%',
                msgTarget: 'side'
            }],
            buttons: [{
                xtype: 'button',
                text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function (v, o) {
                	
                }
            },{
                text: loginUserLanguageResource.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("functionTabInfoWindow_Id").close();
                }
            }]
        });
        Ext.apply(me, {
            items: deviceEditForm
        });
        me.callParent(arguments);
    }
});