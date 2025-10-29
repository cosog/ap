Ext.define("AP.view.acquisitionUnit.DisplayUnitInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.displayUnitInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'displayUnit_editWin_Id',
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
		        	var protocolList=[];
		    		var protocolTreeGridPanelSelection= Ext.getCmp("DisplayUnitProtocolTreeGridPanel_Id").getSelectionModel().getSelection();
		    		if(protocolTreeGridPanelSelection.length>0){
		    			if(protocolTreeGridPanelSelection[0].data.classes==1){
		    				protocolList.push(protocolTreeGridPanelSelection[0].data.code);
		    			}else{
		    				if(isNotVal(protocolTreeGridPanelSelection[0].data.children)){
		    					for(var i=0;i<protocolTreeGridPanelSelection[0].data.children.length;i++){
		    						protocolList.push(protocolTreeGridPanelSelection[0].data.children[i].code);
		    					}
		    				}
		    			}
		    		}
					var new_params = {
							deviceTypeIds: deviceTypeIds,
							protocol: protocolList.join(",")
					};
					Ext.apply(store.proxy.extraParams,new_params);
				}
			}
		});
        
        var modbusProtocolComb = Ext.create(
				'Ext.form.field.ComboBox', {
					fieldLabel :  loginUserLanguageResource.protocolName+'<font color=red>*</font>',
					id : 'formDisplayUnitProtocolComb_Id',
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
							Ext.getCmp("formDisplayUnitProtocol_Id").setValue(this.value);
							
							Ext.getCmp("formDisplayUnitAcqUnitComb_Id").setValue('');
							Ext.getCmp("formDisplayUnitAcqUnitComb_Id").setRawValue('');
							Ext.getCmp("formDisplayUnitAcqUnit_Id").setValue('');
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
					var protocol=Ext.getCmp('formDisplayUnitProtocolComb_Id').getValue();
					var deviceTypeIds='';
		        	var tabTreeGridPanelSelection= Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getSelectionModel().getSelection();
		        	if(tabTreeGridPanelSelection.length>0){
		        		deviceTypeIds=foreachAndSearchTabChildId(tabTreeGridPanelSelection[0]);
		        	}
		        	var protocolList=[];
		    		var protocolTreeGridPanelSelection= Ext.getCmp("DisplayUnitProtocolTreeGridPanel_Id").getSelectionModel().getSelection();
		    		if(protocolTreeGridPanelSelection.length>0){
		    			if(protocolTreeGridPanelSelection[0].data.classes==1){
		    				protocolList.push(protocolTreeGridPanelSelection[0].data.code);
		    			}else{
		    				if(isNotVal(protocolTreeGridPanelSelection[0].data.children)){
		    					for(var i=0;i<protocolTreeGridPanelSelection[0].data.children.length;i++){
		    						protocolList.push(protocolTreeGridPanelSelection[0].data.children[i].code);
		    					}
		    				}
		    			}
		    		}
					var new_params = {
						protocol:protocol,
						deviceTypeIds:deviceTypeIds,
						selectedProtocol: protocolList.join(",")
					};
					Ext.apply(store.proxy.extraParams,new_params);
				}
			}
		});
        
        var acqUnitComb = Ext.create(
        		'Ext.form.field.ComboBox', {
					fieldLabel :  loginUserLanguageResource.acqUnit+'<font color=red>*</font>',
					id : 'formDisplayUnitAcqUnitComb_Id',
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
							Ext.getCmp("formDisplayUnitAcqUnit_Id").setValue(this.value);
	                    }
					}
				});
        
        var postDisplayUnitEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: loginUserLanguageResource.idx,
                id: 'formDisplayUnit_Id',
                anchor: '100%',
                name: "displayUnit.id"
            },{
				xtype : "hidden",
				id : 'formDisplayUnitProtocol_Id',
				value:'',
				name : "displayUnit.protocol"
			},modbusProtocolComb,acqUnitComb, {
                id: 'formDisplayUnitName_Id',
                name: "displayUnit.unitName",
                fieldLabel: loginUserLanguageResource.unitName+'<font color=red>*</font>',
                allowBlank: false,
                anchor: '100%',
                value: '',
                listeners: {
                    blur: function (t, e) {
                        var value_ = t.getValue();
                        if(value_!=''){
                        	var protocolCode=Ext.getCmp("formDisplayUnitProtocol_Id").getValue();
                        	Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                	protocolCode:protocolCode,
                                	unitName: t.value
                                },
                                url: context + '/acquisitionUnitManagerController/judgeDisplayUnitExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color='red'>"+loginUserLanguageResource.acqUnitExist+"</font>,"+loginUserLanguageResource.pleaseConfirm, function(btn, text){
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
				id : 'formDisplayUnitAcqUnit_Id',
				value:'',
				name : "displayUnit.acqUnitId"
			}, {
                id: 'formDisplayUnitCode_Id',
                name: "displayUnit.unitCode",
                hidden:true,
                anchor: '100%',
                value: ''
            },{
				xtype : "hidden",
				id : 'formDisplayUnitCalculateType_Id',
				value: 0,
				name : "displayUnit.calculateType"
			},{
            	xtype : "combobox",
				fieldLabel : loginUserLanguageResource.calculateType+'<font color=red>*</font>',
				id : 'formDisplayUnitCalculateTypeComb_Id',
				anchor : '100%',
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
						Ext.getCmp("formDisplayUnitCalculateType_Id").setValue(this.value);
						
					}
				}
            },{
            	xtype: 'numberfield',
            	id: "displayUnitSort_Id",
                name: 'displayUnit.sort',
                fieldLabel: loginUserLanguageResource.sortNum,
                allowBlank: true,
                minValue: 1,
                anchor: '100%',
                msgTarget: 'side'
            }, {
            	id: 'displayUnitRemark_Id',
            	name: "displayUnit.remark",
                fieldLabel: loginUserLanguageResource.unitDescription,
                anchor: '100%',
                value: '',
                xtype: 'textareafield'
            }],
            buttons: [{
            	xtype: 'button',
            	id: 'addFormDisplayUnit_Id',
            	text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function () {
                	SaveDisplayUnitSubmitBtnForm();
                }
            }, {
                xtype: 'button',
                id: 'updateFormaAquisitionUnit_Id',
                text: loginUserLanguageResource.update,
                hidden: true,
                iconCls: 'edit',
                handler: function () {
                	UpdateDisplayUnitDataInfoSubmitBtnForm();
                }
            }, {
        	 	xtype: 'button',   
        	 	text: loginUserLanguageResource.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("displayUnit_editWin_Id").close();
                }
            }]
        });
        Ext.apply(me, {
            items: postDisplayUnitEditForm
        });
        me.callParent(arguments);
    }

});