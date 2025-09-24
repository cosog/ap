Ext.define("AP.view.acquisitionUnit.AcquisitionGroupInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.acquisitionGroupInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'acquisitionGroup_editWin_Id',
    closeAction: 'destroy',
    width: 330,
    shadow: 'sides',
    resizable: false,
    collapsible: true,
    constrain: true,
    maximizable: false,
    plain: true,
    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
    modal: true,
    border: false,
    initComponent: function () {
        var me = this;
        var modbusProtocolStore = new Ext.data.SimpleStore({
        	fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
			proxy : {
				url : context+ '/acquisitionUnitManagerController/getModbusProtoclCombList',
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
					var deviceTypeIds='';
		        	var tabTreeGridPanelSelection= Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getSelectionModel().getSelection();
		        	if(tabTreeGridPanelSelection.length>0){
		        		deviceTypeIds=foreachAndSearchTabChildId(tabTreeGridPanelSelection[0]);
		        	}
					var new_params = {
							deviceTypeIds: deviceTypeIds
					};
					Ext.apply(store.proxy.extraParams,new_params);
				}
			}
		});
        
        var modbusProtocolComb = Ext.create(
				'Ext.form.field.ComboBox', {
					fieldLabel :  loginUserLanguageResource.protocolName+'<font color=red>*</font>',
					id : 'formAcquisitionGroupProtocolComb_Id',
					anchor : '100%',
					store: modbusProtocolStore,
					queryMode : 'remote',
					typeAhead : true,
					autoSelect : false,
					allowBlank : false,
					triggerAction : 'all',
					editable : false,
					displayField : "boxval",
					valueField : "boxkey",
					listeners : {
						select: function (v,o) {
							Ext.getCmp("formAcquisitionGroupProtocol_Id").setValue(this.value);
	                    }
					}
				});
        var acqUnitStore = new Ext.data.SimpleStore({
        	fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
			proxy : {
				url : context+ '/acquisitionUnitManagerController/getAcquisitionUnitCombList',
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
					var protocol=Ext.getCmp('formAcquisitionGroupProtocolComb_Id').getValue();
					var deviceTypeIds='';
		        	var tabTreeGridPanelSelection= Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getSelectionModel().getSelection();
		        	if(tabTreeGridPanelSelection.length>0){
		        		deviceTypeIds=foreachAndSearchTabChildId(tabTreeGridPanelSelection[0]);
		        	}
					var new_params = {
						protocol:protocol,
						deviceTypeIds:deviceTypeIds
					};
					Ext.apply(store.proxy.extraParams,new_params);
				}
			}
		});
        
        var acqUnitComb = Ext.create(
        		'Ext.form.field.ComboBox', {
					fieldLabel :  loginUserLanguageResource.unitName+'<font color=red>*</font>',
					id : 'formAcquisitionGroupAcqUnitComb_Id',
					anchor : '100%',
					store: acqUnitStore,
					queryMode : 'remote',
					typeAhead : true,
					autoSelect : false,
					allowBlank : false,
					triggerAction : 'all',
					editable : false,
					displayField : "boxval",
					valueField : "boxkey",
					listeners : {
						expand: function (sm, selections) {
							acqUnitComb.getStore().load();
		                },
						select: function (v,o) {
							Ext.getCmp("formAcquisitionGroupAcqUnit_Id").setValue(this.value);
	                    }
					}
				});
        
        var postAcquisitionGroupEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: loginUserLanguageResource.idx,
                id: 'formAcquisitionGroupJlbh_Id',
                anchor: '100%',
                name: "acquisitionGroup.id"
            },{
				xtype : "hidden",
				id : 'formAcquisitionGroupProtocol_Id',
				value:'',
				name : "acquisitionGroup.protocol"
			},modbusProtocolComb,{
				xtype : "hidden",
				id : 'formAcquisitionGroupAcqUnit_Id',
				value:'',
				name : "acquisitionGroup.acqUnit"
			},acqUnitComb, {
                id: 'formAcquisitionGroupName_Id',
                name: "acquisitionGroup.groupName",
                fieldLabel: loginUserLanguageResource.groupName+'<font color=red>*</font>',
                allowBlank: false,
                anchor: '100%',
                value: '',
                listeners: {
                    blur: function (t, e) {
                        var value_ = t.getValue();
                        if(value_!=''){
                        	var protocolName=Ext.getCmp("formAcquisitionGroupProtocolComb_Id").rawValue;
                        	var unitName=Ext.getCmp("formAcquisitionGroupAcqUnitComb_Id").rawValue;
                        	Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                	protocolName:protocolName,
                                	unitName:unitName,
                                	groupName: t.value
                                },
                                url: context + '/acquisitionUnitManagerController/judgeAcqGroupExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color='red'>【"+loginUserLanguageResource.groupCollisionInfo+"】</font>,"+loginUserLanguageResource.pleaseConfirm, function(btn, text){
                                    	    if (btn == 'ok'){
                                    	    	t.focus(true, 100);
                                    	    }
                                    	});
                                    }
                                },
                                failure: function (response, opts) {
                                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.dataQueryFailure);
                                }
                            });
                        }
                    }
                }
            },{
				xtype : "hidden",
				id : 'formAcquisitionGroupType_Id',
				value:'0',
				name : "acquisitionGroup.type"
			},{
            	xtype : "combobox",
				fieldLabel : loginUserLanguageResource.groupType+'<font color=red>*</font>',
				id : 'formAcquisitionGroupTypeComb_Id',
				anchor : '100%',
				triggerAction : 'all',
				selectOnFocus : false,
			    forceSelection : true,
			    value:0,
			    allowBlank: false,
				editable : false,
				store : new Ext.data.SimpleStore({
							fields : ['value', 'text'],
							data : [[0, loginUserLanguageResource.acqGroup],[1, loginUserLanguageResource.controlGroup]]
						}),
				displayField : 'text',
				valueField : 'value',
				queryMode : 'local',
				emptyText : loginUserLanguageResource.selectGroupType,
				blankText : loginUserLanguageResource.selectGroupType,
				listeners : {
					select:function(v,o){
						Ext.getCmp("formAcquisitionGroupType_Id").setValue(o.data.value);
						if(o.data.value==1){
							Ext.getCmp('formAcquisitionGroupGroupTimingInterval_Id').hide();
							Ext.getCmp('formAcquisitionGroupGroupSavingInterval_Id').hide();
						}else{
							Ext.getCmp('formAcquisitionGroupGroupTimingInterval_Id').show();
							Ext.getCmp('formAcquisitionGroupGroupSavingInterval_Id').show();
						}
					}
				}
            }, {
                id: 'formAcquisitionGroupCode_Id',
                name: "acquisitionGroup.groupCode",
                fieldLabel: '组编码',
                hidden:true,
                anchor: '100%',
                value: ''
                
            }, {
                id: 'formAcquisitionGroupGroupTimingInterval_Id',
                name: "acquisitionGroup.groupTimingInterval",
                fieldLabel: loginUserLanguageResource.groupTimingInterval+'(s)',
                anchor: '100%',
                hidden: false,
                value: ''
                
            }, {
                id: 'formAcquisitionGroupGroupSavingInterval_Id',
                name: "acquisitionGroup.groupSavingInterval",
                fieldLabel: loginUserLanguageResource.groupSavingInterval+'(s)',
                anchor: '100%',
                value: ''
                
            }, {
            	id: 'acquisitionGroupRemark_Id',
            	name: "acquisitionGroup.remark",
                fieldLabel: loginUserLanguageResource.groupDescription,
                anchor: '100%',
                value: '',
                xtype: 'textareafield',
                
            }],
            buttons: [{
            	xtype: 'button',
            	id: 'addFormAcquisitionGroup_Id',
            	text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function () {
                	SaveAcquisitionGroupSubmitBtnForm();
                }
         }, {
                xtype: 'button',
                id: 'updateFormaAquisitionGroup_Id',
                text: loginUserLanguageResource.update,
                hidden: true,
                iconCls: 'edit',
                handler: function () {
                	UpdateAcquisitionGroupDataInfoSubmitBtnForm();
                }
         }, {
        	 	xtype: 'button',   
        	 	text: loginUserLanguageResource.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("acquisitionGroup_editWin_Id").close();
                }
         }]
        });
        Ext.apply(me, {
            items: postAcquisitionGroupEditForm
        });
        me.callParent(arguments);
    }

});